package event.batch;

import java.io.Serializable;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.dom4j.Element;

import com.selman.calcfocus.factory.CalcFocusRequestBuilder;
import com.selman.calcfocus.factory.VenusValues;
import com.selman.calcfocus.request.PointInTime;
import com.selman.calcfocus.service.CalculateRequestService;

import batch.business.*;
import billing.BillSchedule;
import contract.Bucket;
import contract.CommissionPhase;
import contract.Life;
import contract.PremiumDue;
import contract.Segment;
import contract.dm.composer.SegmentComposer;
import contract.dm.dao.SegmentDAO;
import edit.common.EDITBigDecimal;
import edit.common.EDITDate;
import edit.common.EDITMap;
import edit.common.vo.BillScheduleVO;
import edit.common.vo.BucketVO;
import edit.common.vo.ClientAddressVO;
import edit.common.vo.ClientDetailVO;
import edit.common.vo.ClientRoleVO;
import edit.common.vo.ContractClientVO;
import edit.common.vo.EDITTrxHistoryVO;
import edit.common.vo.EDITTrxVO;
import edit.common.vo.FinancialHistoryVO;
import edit.common.vo.InvestmentVO;
import edit.common.vo.LifeVO;
import edit.common.vo.PremiumDueVO;
import edit.common.vo.ProductStructureVO;
import edit.common.vo.SegmentVO;
import edit.services.EditServiceLocator;
import edit.services.db.hibernate.SessionHelper;
import engine.Company;
import engine.sp.InstOneOperand;
import engine.sp.SPException;
import event.BucketHistory;
import event.EDITTrx;
import event.EDITTrxHistory;
import event.TransactionPriority;
import fission.utility.DOMUtil;
import fission.utility.Util;
import group.ContractGroup;
import logging.Log;

public class MonthlyValuationProcessor implements Serializable {

	private static final long serialVersionUID = -3171109911731150614L;

	private static final String[] valuationStatuses = new String[] { "Active", "DeathPending", "Extension",
			"LapsePending", "LTC", "PayorPremiumWaiver", "Transition", "Waiver", "WaiverMonthlyDed" };

	private static final String[] terminationStatuses = new String[] { "Death", "Lapse", "Expired", "Matured",
			"Surrendered", "Terminated" };

	private static final String[] editTrxTerminationTransactionTypeCT = new String[] { "DE", "LA", "MA", "FS", "CPO" };

	public MonthlyValuationProcessor() {
		super();
	}

	public void runMonthlyValuation(EDITDate extractDate, String isAsync, String isYev, String mevContractNumber)
			throws Exception {

		EditServiceLocator.getSingleton().getBatchAgent().getBatchStat(Batch.BATCH_JOB_MONTHLY_VALUATION)
				.tagBatchStart(Batch.BATCH_JOB_MONTHLY_VALUATION, "Update UnitValues");
		System.out.println("monthlyValutation");

		EDITDate cycleDate = extractDate;

		Map<String, Object> map = new HashMap<>();

		SegmentVO segmentVO = null;
		SegmentVO[] riderVOs;
		BillScheduleVO billScheduleVO;
		LifeVO lifeVO;
		PremiumDue premiumDue;
		ContractClientVO[] contractClientVOs;
		List<ClientRoleVO> clientRoleVOs;
		List<Object> clientVOInclusionList;
		ClientRoleVO clientRoleVO;
		ProductStructureVO productStructureVO;
		Company company;
		EDITTrxVO[] mvTrx;
		FinancialHistoryVO financialHistoryVO;
		EDITTrxVO[] editTrxVOs = null;
		CalcFocusRequestBuilder builder;
		Connection connection;
		Object responseObj;

		int priority = 0;
		TransactionPriority[] transactionPriority = TransactionPriority
				.findBy_TrxType(EDITTrx.TRANSACTIONTYPECT_MONTHLIVERSARY);
		if (transactionPriority != null && transactionPriority.length > 0) {
			priority = transactionPriority[0].getPriority();
		}
		List<Long> segmentPKs = new ArrayList<>();

		// for one offs
		if ((mevContractNumber != null) && !mevContractNumber.trim().isEmpty()) {
			segmentPKs.add(Segment.findBy_ContractNumberAndRiderNumber(mevContractNumber, 0).getSegmentPK());
		} else {

			segmentPKs = new SegmentDAO()
					.findPKsBy_GTE_TerminationDate_SegmentNameCT_SegmentStatusCT_AND_SegmentFKISNULL("UL",
							valuationStatuses, new EDITDate(extractDate.getYear() + "/01/01"));

			if (isYev == null || !isYev.equals("on")) {
				List<Long> terminatedSegmentPKs = new SegmentDAO()

						.findPKsBy_GTE_TerminationDate_SegmentNameCT_SegmentStatusCT_AND_SegmentFKISNULL("UL",
								terminationStatuses, new EDITDate(extractDate.getYear() + "/01/01"));

				for (Long terminatedSegmentPK : terminatedSegmentPKs) {
					// EDITTrx editTrx =
					// EDITTrx.findBy_Segment_EffectiveDateLTE_PendingStatus_TransactionTypeCT(
					// terminatedSegmentPK, new EDITDate(extractDate.getYear() + "/01/01"));
					// if (editTrx != null) {
					segmentPKs.add(terminatedSegmentPK);
					// }
				}
			}
		}

		int totalPKs = segmentPKs.size();

		List inclusionList = new ArrayList();
		inclusionList.add(SegmentVO.class);
		inclusionList.add(BillScheduleVO.class);
		inclusionList.add(LifeVO.class);

		SegmentComposer segmentComposer = new SegmentComposer(inclusionList);

		Random rand = new Random(); // instance of random class
		int upperbound = 99999;
		int randomNumber = rand.nextInt(upperbound);

		if (segmentPKs != null) {
			int index = 0;
			for (Long segmentPK : segmentPKs) {
				try {
					map.clear();
					map.put("isAsync", isAsync);
					map.put("isYev", isYev);
					map.put("CFoutputInstruction", "MonthEndValuation");
					map.put("allowMEC", "true");
					map.put("transactionType", "Month End Valuation");

					segmentVO = null; // setting to null so logger doesn't capture wrong segment if there's an issue
										// building the segment for any reason
					segmentVO = segmentComposer.compose(segmentPK);

					map.put("mevContractNumber", mevContractNumber);

					map.put("SegmentVO", segmentVO);

					if (segmentVO.getSegmentFK() == 0) {
						map.put("baseIndicator", "true"); // is a base-coverage trx
					} else {
						map.put("baseIndicator", "false"); // is a rider trx
					}
					map.put("benefitIncreaseIndicator", "false");
					map.put("dboSwitch", "false");
					map.put("unnecessaryPremiumIndicator", "false");

					riderVOs = new SegmentDAO().findRidersBySegmentPK(segmentVO.getSegmentPK());
					map.put("RiderVOs", riderVOs);

					billScheduleVO = (BillScheduleVO) segmentVO.getParentVO(BillScheduleVO.class);
					map.put("BillScheduleVO", billScheduleVO);

					ContractGroup contractGroup = ContractGroup
							.findBy_BillSchedulePK(billScheduleVO.getBillSchedulePK());
					if (contractGroup != null) {
						map.put("groupNumber", contractGroup.getContractGroupNumber());
					} else {
						map.put("groupNumber", "DIR");
					}

					lifeVO = segmentVO.getLifeVO(0);
					map.put("LifeVO", lifeVO);

					// Add a year to cycle date for premiumDues for mevs and yevs per Carrie
					premiumDue = PremiumDue.findActiveSeparateBySegmentPK(segmentVO.getSegmentPK(),
							cycleDate.addYears(1))[0];
					map.put("PremiumDue", premiumDue);

					// ContractClients
					contractClientVOs = contract.dm.dao.DAOFactory.getContractClientDAO()
							.findBySegmentFK(segmentVO.getSegmentPK(), false, null);
					map.put("ContractClientVOs", contractClientVOs);
					ContractClientVO firstContractClientVO = contractClientVOs[0];
					map.put("ContractClientVO", firstContractClientVO);
					map.put("tobaccoUse", firstContractClientVO.getClassCT());

					// ClientRole
					clientRoleVOs = new ArrayList<>();
					;

					role.business.Lookup roleLookup = new role.component.LookupComponent();
					clientVOInclusionList = new ArrayList<>();
					clientVOInclusionList.add(ClientDetailVO.class);
					clientVOInclusionList.add(ClientAddressVO.class);

					for (ContractClientVO contractClientVO : contractClientVOs) {
						clientRoleVO = roleLookup.composeClientRoleVO(contractClientVO.getClientRoleFK(),
								clientVOInclusionList);
						clientRoleVOs.add(clientRoleVO);
					}

					map.put("ClientRoleVOs", clientRoleVOs.toArray(new ClientRoleVO[0]));

					// ProductStructure
					engine.business.Lookup engineLookup = new engine.component.LookupComponent();
					productStructureVO = engineLookup.getByProductStructureId(segmentVO.getProductStructureFK())[0];

					map.put("ProductStructureVO", productStructureVO);

					company = Company.findByPK(productStructureVO.getCompanyFK());
					map.put("Company", company);

					/*
					 * if (new EDITDate(segmentVO.getTerminationDate()).beforeOREqual(cycleDate)) {
					 * mvTrx = event.dm.dao.DAOFactory.getEDITTrxDAO()
					 * .findActiveBySegment_TransactionTypeCT_BeforeEffectiveDate_AND_PendingStatus(
					 * segmentVO.getSegmentPK(), EDITTrx.TRANSACTIONTYPECT_MONTHLIVERSARY,
					 * segmentVO.getTerminationDate(), EDITTrx.PENDINGSTATUS_HISTORY); } else {
					 * 
					 * }
					 */
					mvTrx = event.dm.dao.DAOFactory.getEDITTrxDAO()
							.findActiveBySegment_TransactionTypeCT_BeforeEffectiveDate_AND_PendingStatus(
									segmentVO.getSegmentPK(), EDITTrx.TRANSACTIONTYPECT_MONTHLIVERSARY,
									cycleDate.getFormattedDate(), EDITTrx.PENDINGSTATUS_HISTORY);
					EDITTrxVO[] lcTrx = event.dm.dao.DAOFactory.getEDITTrxDAO()
							.findActiveBySegment_TransactionTypeCT_BeforeEffectiveDate_AND_PendingStatus(
									segmentVO.getSegmentPK(), EDITTrx.TRANSACTIONTYPECT_LOAN_CAPITALIZATION,
									extractDate.addDays(1).getFormattedDate(), EDITTrx.PENDINGSTATUS_HISTORY);

					if (lcTrx != null) {
						EDITTrxHistoryVO[] lcEditTrxHistoryVOs = event.dm.dao.DAOFactory.getEDITTrxHistoryDAO()
								.findByEditTrxPK(lcTrx[0].getEDITTrxPK());
						FinancialHistoryVO lcFinancialHistoryVO = event.dm.dao.DAOFactory.getFinancialHistoryDAO()
								.findByEDITTrxHistoryPK(lcEditTrxHistoryVOs[0].getEDITTrxHistoryPK())[0];
						Bucket[] buckets = Bucket.findBy_SegmentPK_(segmentVO.getSegmentPK());
						EDITBigDecimal cumLoanDollars = buckets[0].getCumDollars();
						map.put("lcFinancialHistoryVO", lcFinancialHistoryVO);
						map.put("lcEDITTrxVO", lcTrx[0]);
						map.put("cumLoanDollars", cumLoanDollars.getBigDecimal().toString());
					}

					financialHistoryVO = null;
					if (mvTrx != null && mvTrx.length > 0) {
						EDITTrxHistoryVO[] editTrxHistoryVOs = event.dm.dao.DAOFactory.getEDITTrxHistoryDAO()
								.findByEditTrxPK(mvTrx[0].getEDITTrxPK());
						financialHistoryVO = event.dm.dao.DAOFactory.getFinancialHistoryDAO()
								.findByEDITTrxHistoryPK(editTrxHistoryVOs[0].getEDITTrxHistoryPK())[0];
						map.put("mvEditTrxVO", mvTrx[0]);
					}

					map.put("FinancialHistoryVO", financialHistoryVO);

					if (mvTrx != null && mvTrx.length > 0) {
						// send all trx run since that MV
						editTrxVOs = event.dm.dao.DAOFactory.getEDITTrxDAO()
								.findAll_MonthlyValTrx(segmentVO.getSegmentPK(), mvTrx[0].getEffectiveDate(), priority);
						// segmentVO.getSegmentPK(), cycleDate.getFormattedDate(), priority);
					}
					if (editTrxVOs == null) {
						// send all trx
						editTrxVOs = event.dm.dao.DAOFactory.getEDITTrxDAO()
								.findAllBySegmentPKAndProcessedOrderByEffectiveDateDesc(segmentVO.getSegmentPK());
					}

					map.put("EDITTrxVOs", editTrxVOs);
					map.put("EDITTrxVO", editTrxVOs[0]);
					map.put("isYev", isYev);

					builder = new CalcFocusRequestBuilder(extractDate, map, randomNumber);
					connection = SessionHelper.getSession(SessionHelper.EDITSOLUTIONS).connection();
					responseObj = null;
					boolean failed = false;
					try {
						responseObj = CalculateRequestService.calcFocusRequest(connection, builder.getReq(),
								builder.isYev());
					} catch (Exception e) {
						System.out.println(e.toString());
						EditServiceLocator.getSingleton().getBatchAgent()
								.getBatchStat(Batch.BATCH_JOB_MONTHLY_VALUATION).updateFailure();
						failed = true;
					}
					if (!failed) {
						index = index + 1;
						EditServiceLocator.getSingleton().getBatchAgent()
								.getBatchStat(Batch.BATCH_JOB_MONTHLY_VALUATION)
								.setBatchMessage("Executing MEVs - Segment " + (index) + " out of " + totalPKs);
						EditServiceLocator.getSingleton().getBatchAgent()
								.getBatchStat(Batch.BATCH_JOB_MONTHLY_VALUATION).updateSuccess();
					}

				} catch (Exception e) {
					System.out.println("Monthly Valuation exception: ContractNumber: " + segmentVO.getContractNumber()
							+ " - " + e.toString());
					EditServiceLocator.getSingleton().getBatchAgent().getBatchStat(Batch.BATCH_JOB_MONTHLY_VALUATION)
							.updateFailure();
					;
					// log error and move on to next pk

					String message = "Month End Valuation Error On Segment #" + segmentPK + ": " + e.getMessage();

					String contractNumber = "";
					String contractStatus = "";

					if (segmentVO != null) {
						contractNumber = segmentVO.getContractNumber();
						contractStatus = segmentVO.getSegmentStatusCT();
					}

					EDITMap columnInfo = new EDITMap();
					columnInfo.put("ContractNumber", contractNumber);
					columnInfo.put("ContractStatus", contractStatus);
					columnInfo.put("Operator", "Batch");
					columnInfo.put("ProcessDate", cycleDate.getFormattedDate());

					Log.logToDatabase(Log.EXECUTE_TRANSACTION, message, columnInfo);

					System.out.println(message);

					e.printStackTrace();
				}
			}
			EditServiceLocator.getSingleton().getBatchAgent().getBatchStat(Batch.BATCH_JOB_MONTHLY_VALUATION)
					.tagBatchStop();

		}
	}

}
