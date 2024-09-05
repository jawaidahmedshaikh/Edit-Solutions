package event.batch;

import java.io.Serializable;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.dom4j.Element;

import com.selman.calcfocus.correspondence.db.Query;
import com.selman.calcfocus.factory.CalcFocusRequestBuilder;
import com.selman.calcfocus.factory.VenusValues;
import com.selman.calcfocus.request.PointInTime;
import com.selman.calcfocus.service.CalculateRequestService;

import agent.Agent;
import batch.business.*;
import billing.BillSchedule;
import contract.Bucket;
import contract.CommissionPhase;
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
import edit.services.db.ConnectionFactory;
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
import group.EnrollmentLeadServiceAgent;
import logging.Log;
import role.dm.dao.ClientRoleDAO;
import staging.SegmentBase;

public class AnnualStatementProcessor implements Serializable {

	private static final long serialVersionUID = -3171109911731150614L;

	private static final String[] valuationStatuses = new String[] { "Active", "DeathPending", "Extension",
			"LapsePending", "LTC", "PayorWaiverPremium", "Transition", "Waiver" };

	public AnnualStatementProcessor() {
		super();
	}

	public void runAnnualStatements(EDITDate extractDate) throws Exception {

		EditServiceLocator.getSingleton().getBatchAgent().getBatchStat(Batch.BATCH_JOB_ANNUAL_STATEMENTS)
				.tagBatchStart(Batch.BATCH_JOB_ANNUAL_STATEMENTS, "Run Annual Statements");
		
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
		connection = SessionHelper.getSession(SessionHelper.STAGING).connection();
		long[] segmentBasePKs = Query.getCorrepondencePKs(connection, "Stmt");

		List inclusionList = new ArrayList();
		inclusionList.add(SegmentVO.class);
		inclusionList.add(BillScheduleVO.class);
		inclusionList.add(LifeVO.class);

		SegmentComposer segmentComposer = new SegmentComposer(inclusionList);

		if (segmentBasePKs != null) {
			int count = 0;
			int qty = segmentBasePKs.length;
			
			for (Long segmentBasePK : segmentBasePKs) {

				count++;
				EditServiceLocator.getSingleton().getBatchAgent().getBatchStat(Batch.BATCH_JOB_ANNUAL_STATEMENTS)
					.setBatchMessage("Executing Staging SegmentBase " + count + " of " + qty);
				
				SegmentBase segmentBase = SegmentBase.findBy_SegmentBasePK(segmentBasePK);
				Segment segment = Segment.findByContractNumber(segmentBase.getContractNumber());

				try {
					map.clear();

					map.put("processType", "IllustrateLife");
					map.put("reportType", "PDF");
					map.put("resultsWithReport", "true");
					
					map.put("CFoutputInstruction", "AnnualStatement");
					map.put("allowMEC", "true");
					map.put("transactionType", "AnnualStatement");

					segmentVO = null; // setting to null so logger doesn't capture wrong segment if there's an issue
										// building the segment for any reason
					segmentVO = segmentComposer.compose(segment.getSegmentPK());
//					if (segmentVO.getContractNumber().equals("06U022527U") ) { 
					map.put("SegmentVO", segmentVO);

					if (segmentVO.getSegmentFK() == 0) {
						map.put("baseIndicator", "true"); // is a base-coverage trx
					} else {
						map.put("baseIndicator", "false"); // is a rider trx
					}
					map.put("benefitIncreaseIndicator", "false");
					map.put("dboSwitch", "false");
					map.put("unnecessaryPremiumIndicator", "false");

					// riderVOs = segmentVO.getSegmentVO();
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

					premiumDue = PremiumDue.findActiveSeparateBySegmentPK(segmentVO.getSegmentPK(), extractDate)[0];
					map.put("PremiumDue", premiumDue);

					// ContractClients
					contractClientVOs = contract.dm.dao.DAOFactory.getContractClientDAO()
							.findBySegmentFK(segmentVO.getSegmentPK(), false, null);
					map.put("ContractClientVOs", contractClientVOs);
					ContractClientVO firstContractClientVO = contractClientVOs[0];
					map.put("ContractClientVO", firstContractClientVO);
					map.put("tobaccoUse", firstContractClientVO.getClassCT());
					
					

		            EnrollmentLeadServiceAgent[] enrollmentLeadServiceAgents = 
		            		group.EnrollmentLeadServiceAgent.findBy_Segment_EffectiveDate(segment, new EDITDate()); 

		            ClientRoleVO[] agentRoleVOs = new ClientRoleDAO().findByClientRolePK(enrollmentLeadServiceAgents[0].getClientRoleFK(), true, null);
		            ClientRoleVO agentRoleVO = agentRoleVOs[0];

		           // Agent currentAgent = agent.Agent.findBy_PK(agentRoleVO.getAgentFK());
		            
		            

					// ClientRole
					clientRoleVOs = new ArrayList<>();

					role.business.Lookup roleLookup = new role.component.LookupComponent();
					clientVOInclusionList = new ArrayList<>();
					clientVOInclusionList.add(ClientDetailVO.class);
					clientVOInclusionList.add(ClientAddressVO.class);

					for (ContractClientVO contractClientVO : contractClientVOs) {
						clientRoleVO = roleLookup.composeClientRoleVO(contractClientVO.getClientRoleFK(),
								clientVOInclusionList);
						clientRoleVOs.add(clientRoleVO);
					}
					clientRoleVOs.add(agentRoleVO);

					map.put("ClientRoleVOs", clientRoleVOs.toArray(new ClientRoleVO[0]));

					// ProductStructure
					engine.business.Lookup engineLookup = new engine.component.LookupComponent();
					productStructureVO = engineLookup.getByProductStructureId(segmentVO.getProductStructureFK())[0];

					map.put("ProductStructureVO", productStructureVO);

					company = Company.findByPK(productStructureVO.getCompanyFK());
					map.put("Company", company);

					mvTrx = event.dm.dao.DAOFactory.getEDITTrxDAO()
							.findActiveBySegment_TransactionTypeCT_BeforeEffectiveDate_AND_PendingStatus(
									segmentVO.getSegmentPK(), EDITTrx.TRANSACTIONTYPECT_MONTHLIVERSARY,
									extractDate.addDays(1).getFormattedDate(), EDITTrx.PENDINGSTATUS_HISTORY);

					EDITTrxVO[] lcTrx = event.dm.dao.DAOFactory.getEDITTrxDAO()
							.findActiveBySegment_TransactionTypeCT_BeforeEffectiveDate_AND_PendingStatus(
									segmentVO.getSegmentPK(), EDITTrx.TRANSACTIONTYPECT_LOAN_CAPITALIZATION,
									extractDate.addDays(1).getFormattedDate(), EDITTrx.PENDINGSTATUS_HISTORY);

					if (lcTrx != null) {
					    EDITTrxHistoryVO[] lcEditTrxHistoryVOs = event.dm.dao.DAOFactory.getEDITTrxHistoryDAO()
								.findByEditTrxPK(lcTrx[0].getEDITTrxPK());
						FinancialHistoryVO lcFinancialHistoryVO = event.dm.dao.DAOFactory.getFinancialHistoryDAO()
								.findByEDITTrxHistoryPK(lcEditTrxHistoryVOs[0].getEDITTrxHistoryPK())[0];
						BucketHistory[] bucketHistories = BucketHistory.findBy_EDITTrxHistoryPK(lcEditTrxHistoryVOs[0].getEDITTrxHistoryPK());
						Bucket[] buckets = Bucket.findBy_SegmentPK_(segmentVO.getSegmentPK());
						EDITBigDecimal bhCumLoanDollars = bucketHistories[0].getCumLoanDollars();
						EDITBigDecimal cumLoanDollars = buckets[0].getCumDollars();
						EDITBigDecimal lcBucketSum = BucketHistory.findLoanPrincipleSum_ByTrxType(segmentVO.getSegmentPK(), extractDate, "LC");
					    map.put("lcFinancialHistoryVO", lcFinancialHistoryVO);
						map.put("lcEditTrxVO", lcTrx[0]);
						map.put("cumLoanDollars", cumLoanDollars.getBigDecimal().toString());
					}

					financialHistoryVO = null;
					if (mvTrx != null && mvTrx.length > 0) {
						// financialHistoryVO =
						// mvTrx[0].getEDITTrxHistoryVO()[0].getFinancialHistoryVO()[0];
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
					}
					if (editTrxVOs == null) {
						// send all trx
						editTrxVOs = event.dm.dao.DAOFactory.getEDITTrxDAO()
								.findAllBySegmentPKAndProcessedOrderByEffectiveDateDesc(segmentVO.getSegmentPK());
					}

					map.put("EDITTrxVOs", editTrxVOs);
					map.put("EDITTrxVO", editTrxVOs[0]);
					map.put("AnnualStatementAdminData", Query.getAnnualStatementAdminData(connection, segmentBasePK)); // segment.getContractNumber()));

					builder = new CalcFocusRequestBuilder(extractDate, map);
					responseObj = null;
					try {
						responseObj = CalculateRequestService.calcFocusRequest(SessionHelper.getSession(SessionHelper.EDITSOLUTIONS).connection(), builder.getReq());
					} catch (Exception e) {
						System.out.println(e.toString());
						EditServiceLocator.getSingleton().getBatchAgent()
								.getBatchStat(Batch.BATCH_JOB_ANNUAL_STATEMENTS).updateFailure();
					}

					EditServiceLocator.getSingleton().getBatchAgent().getBatchStat(Batch.BATCH_JOB_ANNUAL_STATEMENTS)
							.updateSuccess();
//					}

				} catch (Exception e) {
					System.out.println("Monthly Valuation exception: " + e.toString());
					EditServiceLocator.getSingleton().getBatchAgent().getBatchStat(Batch.BATCH_JOB_ANNUAL_STATEMENTS)
							.updateFailure();

					// log error and move on to next pk
					String message = "Month End Valuation Error On Segment #" + segment.getSegmentPK() + ": " + e.getMessage();

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
					columnInfo.put("ProcessDate", extractDate.getFormattedDate());

					Log.logToDatabase(Log.EXECUTE_TRANSACTION, message, columnInfo);

					System.out.println(message);

					e.printStackTrace();
				}
			}
			
			EditServiceLocator.getSingleton().getBatchAgent().getBatchStat(Batch.BATCH_JOB_ANNUAL_STATEMENTS).setBatchMessage("Statement Processing Complete!");
	        EditServiceLocator.getSingleton().getBatchAgent().getBatchStat(Batch.BATCH_JOB_ANNUAL_STATEMENTS).tagBatchStop();

		}
	}

}
