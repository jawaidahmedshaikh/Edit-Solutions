package com.selman.calcfocus.factory;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.commons.lang3.time.DateUtils;
import org.hibernate.HibernateException;
import org.joda.time.LocalDate;

import com.selman.calcfocus.conversion.ValueConversion;
import com.selman.calcfocus.correspondence.builder.PlanCode;
import com.selman.calcfocus.correspondence.db.Query;
import com.selman.calcfocus.request.Address;
import com.selman.calcfocus.request.AddressAssociation;
import com.selman.calcfocus.request.AddressType;
import com.selman.calcfocus.request.AnnualStatementAdminData;
import com.selman.calcfocus.request.BaseIndicator;
import com.selman.calcfocus.request.CalculateRequest;
import com.selman.calcfocus.request.Context;
import com.selman.calcfocus.request.Coverage;
import com.selman.calcfocus.request.CoverageUpdates;
import com.selman.calcfocus.request.DeathBenefitOption;
import com.selman.calcfocus.request.EndDateType;
import com.selman.calcfocus.request.FacePremiumSolveType;
import com.selman.calcfocus.request.Fund;
import com.selman.calcfocus.request.FundSubtype;
import com.selman.calcfocus.request.FundType;
import com.selman.calcfocus.request.Gender;
import com.selman.calcfocus.request.GenderBlend;
import com.selman.calcfocus.request.GoalAmountExpressedAs;
import com.selman.calcfocus.request.Header;
import com.selman.calcfocus.request.InterestAssumption;
import com.selman.calcfocus.request.LifetimeFinancialAdviceProjection;
import com.selman.calcfocus.request.LoanInterestAssumption;
import com.selman.calcfocus.request.MonthEndValues;
import com.selman.calcfocus.request.MortalityAssumption;
import com.selman.calcfocus.request.OutputInstruction;
import com.selman.calcfocus.request.Party;
import com.selman.calcfocus.request.PaymentMethod;
import com.selman.calcfocus.request.PaymentMode;
import com.selman.calcfocus.request.PointInTime;
import com.selman.calcfocus.request.Policy;
import com.selman.calcfocus.request.PolicyAdminStatus;
import com.selman.calcfocus.request.ReportType;
import com.selman.calcfocus.request.Role;
import com.selman.calcfocus.request.RoleType;
import com.selman.calcfocus.request.Scenario;
import com.selman.calcfocus.request.Solve;
import com.selman.calcfocus.request.SolveGoalType;
import com.selman.calcfocus.request.StartDateType;
import com.selman.calcfocus.request.TobaccoUse;
import com.selman.calcfocus.request.TransactionRequest;
import com.selman.calcfocus.request.TransactionRequestAmountRule;
import com.selman.calcfocus.request.TransactionType;
import com.selman.calcfocus.request.YearValue;
import com.selman.calcfocus.request.YesNo;
import com.selman.calcfocus.response.CalculateResponse;
import com.selman.calcfocus.service.CalcFocusLoggingService;
import com.selman.calcfocus.util.BadDataException;
import com.selman.calcfocus.util.CalcFocusUtils;

import agent.Agent;

import com.selman.calcfocus.util.CalcFocusBuilderUtils;

import antlr.Utils;
import billing.BillSchedule;
import client.ClientAddress;
import client.ClientDetail;
import contract.Bucket;
import contract.ContractClient;
import contract.Life;
import contract.MasterContract;
import contract.PremiumDue;
import contract.Segment;
import contract.YearEndValues;
import edit.common.EDITBigDecimal;
import edit.common.EDITDate;
import edit.common.vo.BillScheduleVO;
import edit.common.vo.BucketHistoryVO;
import edit.common.vo.ClientAddressVO;
import edit.common.vo.ClientDetailVO;
import edit.common.vo.ClientRoleVO;
import edit.common.vo.ContractClientVO;
import edit.common.vo.ContractSetupVO;
import edit.common.vo.EDITTrxHistoryVO;
import edit.common.vo.EDITTrxVO;
import edit.common.vo.FinancialHistoryVO;
import edit.common.vo.LifeVO;
import edit.common.vo.PremiumDueVO;
import edit.common.vo.SegmentVO;
import edit.services.db.hibernate.SessionHelper;
import engine.ProductStructure;
import event.BucketHistory;
import event.ContractSetup;
import event.EDITTrx;
import event.EDITTrxHistory;
import event.FinancialHistory;
import event.SegmentHistory;
import role.ClientRole;

public class CalcFocusRequestBuilder implements RequestBuilder {

	Header header = new Header();
	Context context = new Context();
	Policy policy = new Policy();
	Party party = new Party();
	Address address = new Address();
	CalculateRequest req = new CalculateRequest();
	AnnualStatementAdminData annualStatementAdminData = new AnnualStatementAdminData();
	LifetimeFinancialAdviceProjection lifetimeFinancialAdviceProjection = new LifetimeFinancialAdviceProjection();
	EDITTrx mvEditTrx;
	Segment primaryInsuredSegment;
	XMLGregorianCalendar now = null;
	Date currentDate;
	Date pointInTimeEffectiveDate;
	EDITDate extractDate;
	VenusValues vv;
	int randomNumber;

	public CalcFocusRequestBuilder(EDITDate extractDate, Map<String, Object> values) {
		this(extractDate, values, 0);

	}

	public CalcFocusRequestBuilder(EDITDate extractDate, Map<String, Object> values, int randomNumber) {
		super();
		try {
			this.currentDate = new Date(System.currentTimeMillis());
			if (extractDate != null) {
				this.extractDate = extractDate;
			} else {
				this.extractDate = new EDITDate(currentDate.getTime());
			}
			GregorianCalendar gc = new GregorianCalendar();
			gc.setTime(currentDate);
			this.now = DatatypeFactory.newInstance().newXMLGregorianCalendar(gc);
			this.vv = new VenusValues(values);
			this.randomNumber = randomNumber;
			setHeader();
			setContext();
			setPolicy();
			setAnnualStatementAdminData();

		} catch (DatatypeConfigurationException | ParseException | BadDataException | SQLException e) {
			try {
				CalcFocusLoggingService.writeLogEntry(
						SessionHelper.getSession(SessionHelper.EDITSOLUTIONS).connection(), policy.getPolicyNumber(),
						policy.getCompanyCode(), Long.valueOf(Long.parseLong(policy.getPolicyGUID())),
						policy.getPolicyAdminStatus().toString(), "Data Error", null, "calcFocus", 0);
			} catch (HibernateException e1) {
				System.out.println(e1.toString());
			} catch (NumberFormatException e1) {
				System.out.println(e1.toString());
			} catch (SQLException e1) {
				System.out.println(e1.toString());
			}
		}

	}

	@Override
	public void setHeader() {
		System.out.println("Policy number: " + vv.getSegmentVO().getContractNumber());
		header.setDateTime(now);
		header.setRefGUID(CalcFocusUtils.getUniqueRequestID(vv.getSegmentVO().getContractNumber(),
				vv.getSegmentVO().getSegmentPK(), currentDate));
		if (vv.getCFoutputInstruction().equals("MonthEndValuation")) {
			header.setBatchRefGUID("BATCH_MEV_" + extractDate.getFormattedYear() + extractDate.getFormattedMonth()
					+ extractDate.getFormattedDay() + "_" + randomNumber);
			if (vv.getIsAsync().equals("on")) {
				header.setAsync(Boolean.TRUE);
			} else {
				header.setAsync(Boolean.FALSE);
			}
		} else if (vv.getCFoutputInstruction().equals("AnnualStatement")) {
			header.setAsync(Boolean.TRUE);
		} else {
			header.setAsync(Boolean.FALSE);
		}
		req.setHeader(header);
	}

	/*
	 * processDate is cycleDate. if process date is null, current datetime will be
	 * used. processType should come from ws.CFprocessType
	 */
	@Override
	public void setContext() throws DatatypeConfigurationException {

		context.setProcessType(vv.getCFoutputInstruction());
		EDITDate effectiveDate;
		if (vv.getCFoutputInstruction().equals("MonthEndValuation")
				|| vv.getCFoutputInstruction().equals("AnnualStatement")) {
			effectiveDate = extractDate;

		} else if (new EDITDate(vv.getSegmentVO().getEffectiveDate()).after(new EDITDate(currentDate.getTime()))) {
			effectiveDate = new EDITDate(vv.getSegmentVO().getEffectiveDate());
		} else {
			effectiveDate = new EDITDate(vv.getEditTrxVO().getEffectiveDate());
		}

		if (vv.getCFoutputInstruction().equals("AnnualStatement")) {
			context.setProcessType("IllustrateLife");
			context.setReportName(Arrays.asList("Annual Statement"));
			context.setReportType(ReportType.PDF);
			context.setResultsWithReport(true);
		}

		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTime(new Date(effectiveDate.getTimeInMilliseconds()));
		context.setEffectiveDate(DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar));

		req.setContext(context);

	}

	@Override
	public void setPolicy() throws BadDataException, ParseException, DatatypeConfigurationException, SQLException {
		List<String> errors = new ArrayList<>();
		try {

			if (vv.getMvEditTrxVO() != null) {
				mvEditTrx = EDITTrx.findBy_PK(vv.getMvEditTrxVO().getEDITTrxPK());
			}
			// PointInTimeEffectiveDate from MV EDITTrx.
			if (vv.getSegmentVO().getSegmentStatusCT().equals(Segment.SEGMENTSTATUSCT_DEATH)
					|| vv.getSegmentVO().getSegmentStatusCT().equals(Segment.SEGMENTSTATUSCT_LAPSE)
					|| vv.getSegmentVO().getSegmentStatusCT().equals(Segment.SEGMENTSTATUSCT_MATURE)
					|| vv.getSegmentVO().getSegmentStatusCT().equals(Segment.SEGMENTSTATUSCT_SURRENDERED)
					|| vv.getSegmentVO().getSegmentStatusCT().equals("Expired")
					|| vv.getSegmentVO().getSegmentStatusCT().equals(Segment.TERMINATED)

			) {
				if (vv.getSegmentVO().getSegmentStatusCT().equals("Expired")
						|| vv.getSegmentVO().getSegmentStatusCT().equals(Segment.TERMINATED)) {
					pointInTimeEffectiveDate = new Date(
							new EDITDate(vv.getSegmentVO().getEffectiveDate()).getTimeInMilliseconds());
				} else {
					EDITTrxVO[] terminatedTrx = event.dm.dao.DAOFactory.getEDITTrxDAO()
							.findAllBySegmentPKAndTrxType(vv.getSegmentVO().getSegmentPK(),
									new String[] { EDITTrx.TRANSACTIONTYPECT_DEATH, EDITTrx.TRANSACTIONTYPECT_LAPSE,
											EDITTrx.TRANSACTIONTYPECT_MATURITY,
											EDITTrx.TRANSACTIONTYPECT_FULLSURRENDER });
					pointInTimeEffectiveDate = new Date(
							new EDITDate(terminatedTrx[0].getEffectiveDate()).getTimeInMilliseconds());
				}
			} else if (mvEditTrx != null) {
				pointInTimeEffectiveDate = new Date(mvEditTrx.getEffectiveDate().getTimeInMilliseconds());
			} else {
				pointInTimeEffectiveDate = new Date(
						new EDITDate(vv.getSegmentVO().getEffectiveDate()).getTimeInMilliseconds());
			}

			policy.setPolicyGUID(Long.toString(vv.getSegmentVO().getSegmentPK()));
			if (vv.getGroupNumber() != null) {
				policy.setGroupCode(vv.getGroupNumber());
			}
			String policyAdminStatus = CalcFocusUtils.getCFStatus(vv.getSegmentVO().getSegmentStatusCT());
			policy.setPolicyAdminStatus(PolicyAdminStatus.fromValue(policyAdminStatus));
			policy.setOutputInstruction(OutputInstruction.fromValue(vv.getCFoutputInstruction()));
			policy.setPolicyNumber(vv.getSegmentVO().getContractNumber());
			policy.setCarrierAdminSystem("VENUS");
			policy.setCompanyCode(vv.getCompany().getCompanyName());
			policy.setIssueState(vv.getSegmentVO().getIssueStateCT());
			policy.setPaymentMode(ValueConversion.convertToPaymentMode(vv.getBillScheduleVO().getBillingModeCT(),
					vv.getBillScheduleVO().getDeductionFrequencyCT()));

			if (vv.getBillScheduleVO().getBillMethodCT().toLowerCase().equals("list")) {
				policy.setPaymentMethod(PaymentMethod.LIST_BILL);
			} else if (vv.getBillScheduleVO().getBillMethodCT().toLowerCase().equals("direct")) {
				policy.setPaymentMethod(PaymentMethod.DIRECT_BILL);
			} else if (vv.getBillScheduleVO().getBillMethodCT().toLowerCase().equals("eft")) {
				policy.setPaymentMethod(PaymentMethod.ELECTRONIC_FUND_TRANSFER);
			}

			Date premiumDueEffectiveDate = new Date(vv.getPremiumDue().getEffectiveDate().getTimeInMilliseconds());
			Date segmentEffectiveDate = new SimpleDateFormat("yyyy/MM/dd").parse(vv.getSegmentVO().getEffectiveDate());
			Date editTrxEffectiveDate = new SimpleDateFormat("yyyy/MM/dd").parse(vv.getEditTrxVO().getEffectiveDate());

			// per Carrie, use extractDate instead of EDITTrx.effectiveDate when it's an MEV
			// run.
			if (vv.getCFoutputInstruction().equals("MonthEndValuation")) {
				editTrxEffectiveDate = new Date(extractDate.getTimeInMilliseconds());
			}

			policy.setModalPremium(
					ValueConversion.calculateModelPremium(vv.getBillScheduleVO().getBillTypeCT(), editTrxEffectiveDate,
							premiumDueEffectiveDate, vv.getPremiumDue().getDeductionAmount().doubleValue(),
							vv.getPremiumDue().getBillAmount().doubleValue()));

			GregorianCalendar calendar = new GregorianCalendar();

			if (vv.getLifeVO().getLapseDate() != null) {
				EDITDate lapseDate = new EDITDate(vv.getLifeVO().getLapseDate());
				calendar.setTime(new Date(lapseDate.getTimeInMilliseconds()));
				policy.setLapseStartDate(DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar));
			}
			EDITDate preLapseDate = new EDITDate(vv.getLifeVO().getLapsePendingDate());

			// User DB version of LifeVO for MEC values
			LifeVO mecLifeVO = (LifeVO) Life.findBy_SegmentPK(vv.getSegmentVO().getSegmentPK()).getVO();
			EDITDate mecStartDate = new EDITDate(mecLifeVO.getMECDate());
			calendar.setTime(segmentEffectiveDate);
			policy.setEffectiveDate(DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar));

			// only needed if in PreLapse
			if (policyAdminStatus.equals("PreLapse")) {
				calendar.setTime(new Date(preLapseDate.getTimeInMilliseconds()));
				policy.setPreLapseStartDate(DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar));
			}

			if (!vv.getSegmentVO().getSegmentStatusCT().equals("SubmitPending")
					&& ((mecLifeVO.getMECStatusCT() != null) && mecLifeVO.getMECStatusCT().equals("MEC"))) {
				calendar.setTime(new Date(mecStartDate.getTimeInMilliseconds()));
				policy.setMECStartDate(DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar));
			}
			policy.setAllowMEC(Boolean.TRUE);
			policy.setLoanInterestAssumption(LoanInterestAssumption.CAPITALIZE);

			/*
			 * ******************* Lst Monthanivsary Charge **************************
			 * GroupSetupDocVO.GroupSetupVO.ContractSetupVO.ClientSetupVO.EDITTrxVO.
			 * EDITTrxHistoryVO.FinancialHistoryVO.GrossAmount from the last active MV
			 * transaction.
			 */
			if (vv.getMvFinancialHistory() != null) {
				policy.setLastMonthaversaryCharge(vv.getMvFinancialHistory().getGrossAmount().doubleValue());
			}

			if (new EDITDate(segmentEffectiveDate).before(new EDITDate(vv.getEditTrxVO().getEffectiveDate()))) {
				policy.getCoverage().add(getCoverage(vv.getSegmentVO(), true, true));
				boolean isDrivingSegment = false;
				if (vv.getRiderSegmentVOs() != null) {
					List<SegmentVO> ulIncreaseSegmentVOs = getULIncreaseSegmentVOs(vv.getRiderSegmentVOs());
					int riderNumberCount = 0;
					for (SegmentVO riderSegmentVO : vv.getRiderSegmentVOs()) {
						// ULIncreases don't get added to coverage. Not sure about other transaction
						// types yet. 5/6/21
						System.out.println("Looking at coverage: " + riderSegmentVO.getOptionCodeCT());
						if (new EDITDate(riderSegmentVO.getTerminationDate()).after(new EDITDate(extractDate))) {
							if (!riderSegmentVO.getSegmentStatusCT().equals("Terminated")
									|| (!riderSegmentVO.getOptionCodeCT().equals("ULIncrease")
											|| (!riderSegmentVO.getOptionCodeCT().equals("RiderAdd")
													&& (riderNumberCount + 1 < vv.getRiderSegmentVOs().length)))) {
								System.out.println("Add coverage: " + riderSegmentVO.getOptionCodeCT());
								policy.getCoverage().add(getCoverage(riderSegmentVO, false, isDrivingSegment));
								// LTC, EOB and WMD
								if (riderSegmentVO.getOptionCodeCT().equals("LTC")
										|| riderSegmentVO.getOptionCodeCT().equals("EOB")
										|| riderSegmentVO.getOptionCodeCT().equals("WMD")) {
									for (SegmentVO s : ulIncreaseSegmentVOs) {
										riderSegmentVO.setAgeAtIssue(s.getAgeAtIssue());
										riderSegmentVO.setIssueDate(s.getIssueDate());
										riderSegmentVO.setAmount(s.getAmount());
										riderSegmentVO.setEffectiveDate(s.getEffectiveDate());
										policy.getCoverage().add(getCoverage(riderSegmentVO, false, isDrivingSegment));
									}

								}
							}
						}
						riderNumberCount++;
					}
				}
			}
		} catch (Exception e) {
			errors.add("BadDataException");
			BadDataException bda = new BadDataException(errors);
			bda.setPolicy(policy);
			throw bda;
		}
		// default to TRUE but might change with illustrations.

		if ((!vv.getIsYev().equals("on") && (vv.getCFoutputInstruction().equals("MonthEndValuation")
				|| vv.getCFoutputInstruction().equals("AnnualStatement")))
				&& (getPreviousYearEndValues(vv.getSegmentVO()) != null)) {
			policy.getPreviousYearEndValues().add(getPreviousYearEndValues(vv.getSegmentVO()));
		}
		req.getPolicy().add(policy);

	}

	private Coverage getCoverage(SegmentVO segmentVO, boolean isBaseSegment, boolean isDrivingSegment)
			throws DatatypeConfigurationException, SQLException, ParseException {

		Coverage coverage = new Coverage();
		GregorianCalendar calendar = new GregorianCalendar();
		if (isBaseSegment || segmentVO.getOptionCodeCT().equals("ULIncrease")) {
			coverage.setBaseIndicator(BaseIndicator.BASE);
		} else {
			coverage.setBaseIndicator(BaseIndicator.RIDER);
		}

		if (segmentVO.getOptionCodeCT().equals("UL")) {
			PlanCode planCode = CalcFocusUtils.getCalcFocusPlanCode(
					SessionHelper.getSession(SessionHelper.ENGINE).connection(), segmentVO.getRatedGenderCT(),
					segmentVO.getUnderwritingClassCT(), segmentVO.getOptionCodeCT(), segmentVO.getGroupPlan(),
					vv.getCompany().getCompanyName());

			// add to VenusValues for use later
			vv.setCalcFocusPlanCode(planCode.getCalcFocusProductCode());

			coverage.setProductCode(planCode.getCalcFocusProductCode());
			coverage.setLegacyPlanCode(planCode.getLegacyPlanCode());
			// now we can set policy genderBlend
			if ((policy.getGenderBlend() == null) && (planCode.getGenderBlend() != null)
					&& (!planCode.getGenderBlend().trim().isEmpty())) {
				policy.setGenderBlend(GenderBlend.fromValue(planCode.getGenderBlend()));
			}
		} else {
			coverage.setLegacyPlanCode(segmentVO.getOptionCodeCT());
		}

		coverage.setSourceCoverageID(Long.toString(segmentVO.getSegmentPK()));

		String fvTransactionType;
		if (vv.getEditTrxVOs() != null) {
			fvTransactionType = vv.getEditTrxVOs()[vv.getEditTrxVOs().length - 1].getTransactionTypeCT();
		} else {
			fvTransactionType = "na";
		}
		if (vv.getMvEditTrxVO() != null) {
        EDITTrxHistory editTrxHistory =  EDITTrxHistory.findBy_EDITTrxFK(vv.getMvEditTrxVO().getEDITTrxPK());
            Double prevFaceAmount = SegmentHistory.getPrevFaceAmount(segmentVO.getSegmentPK(), editTrxHistory.getEDITTrxHistoryPK());
            coverage.setFaceAmount(prevFaceAmount);
		} else {
            coverage.setFaceAmount(segmentVO.getUnits().multiply(new BigDecimal(1000)).doubleValue());
		}
        /*
		if (!segmentVO.getOptionCodeCT().equals("ULIncrease")) { 
			coverage.setFaceAmount(
					ValueConversion.calculateFaceValue(segmentVO.getOptionCodeCT(), segmentVO.getOriginalUnits(),
							segmentVO.getUnits(), fvTransactionType, vv.getComplexChangeType(), isDrivingSegment));
		} else {
			 if (new EDITDate(segmentVO.getEffectiveDate()).before(new EDITDate(pointInTimeEffectiveDate.getTime()))) {
		         coverage.setFaceAmount(segmentVO.getUnits().multiply(new BigDecimal(1000)).doubleValue());

			 } else {
			    // if a ULIncrease and on or after pointInTime set to zero - 
			     coverage.setFaceAmount(0.00);
			 }
		}
		*/

		// initialFaceValue = OriginalUnits x 1000
		Double initialFaceValue = segmentVO.getOriginalUnits().multiply(new BigDecimal(1000)).doubleValue();

		// Does a Pending have it's own InitialFaceAmount?
		if (policy.getPolicyAdminStatus().equals(PolicyAdminStatus.PENDING)) {
			coverage.setInitialFaceAmount(coverage.getFaceAmount());
		}

		coverage.setInitialFaceAmount(initialFaceValue);

		// per Dan G - Use primaryInsured issue age for CTR
		if (segmentVO.getOptionCodeCT().equals("CTR")) {
			ContractClient contractClientPI[] = ContractClient
					.findBy_SegmentPK_And_RoleTypeCT(vv.getSegmentVO().getSegmentPK(), ClientRole.ROLETYPECT_INSURED);
			coverage.setIssueAge(contractClientPI[0].getIssueAge());
		} else {
			coverage.setIssueAge(segmentVO.getAgeAtIssue());
		}

		if ((vv.getTobaccoUse() != null) && vv.getTobaccoUse().toLowerCase().equals("nonsmoker")) {
			coverage.setTobaccoUse(TobaccoUse.NONSMOKER);
		} else {
			coverage.setTobaccoUse(TobaccoUse.SMOKER);
		}

		if (isBaseSegment) {
			String deathBenefitOption;
			if (vv.getLifeVO().getDeathBenefitOptionCT().toLowerCase().equals("level")) {
				deathBenefitOption = "Level";
			} else {
				deathBenefitOption = "Increasing";
			}
			coverage.setDeathBenefitOption(DeathBenefitOption.fromValue(deathBenefitOption));
		}

		calendar.setTime(new Date(new EDITDate(segmentVO.getEffectiveDate()).getTimeInMilliseconds()));
		coverage.setEffectiveDate(DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar));

		EDITDate terminationDate = new EDITDate(segmentVO.getTerminationDate());
		EDITDate editTrxEffectiveDate = new EDITDate(vv.getEditTrxVO().getEffectiveDate());
		if (terminationDate.beforeOREqual(editTrxEffectiveDate)) {
			calendar.setTime(new Date(new EDITDate(segmentVO.getTerminationDate()).getTimeInMilliseconds()));
			coverage.setTerminationDate(DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar));
		}

		// needs to me added to Map - peobably not relevant for a SubmitPending
		coverage.setGapInCoverageMonths(vv.getGapInCoverageMonths());
		coverage.setDboSwitch(vv.isDboSwitch());
		if (segmentVO.getOptionCodeCT().equals("FL")) {
			coverage.setGuaranteedCoverageOption(true);
		} else {
			coverage.setGuaranteedCoverageOption(false);
		}
		coverage.getRole().addAll(getRoles(coverage, segmentVO, isBaseSegment));

		if (isBaseSegment && !vv.getEditTrxVO().getTransactionTypeCT().equals("SB")) {
			PointInTime pointInTime = new PointInTime();
			/*
			 * ********** CostBasis ************* Find the FinancialHistory record connected
			 * to EDITTrx(MV), via EDITTrxHistory. FinancialHistory.CostBasis. If there is
			 * no such MV trx, then use SegmentDocVO.SegmentVO.CostBasis.
			 */
			Double costBasis = new Double(0.00);

			if (mvEditTrx != null) {
				vv.setMvFinancialHistory(mvEditTrx.getEDITTrxHistory().getFinancialHistory());
				if (vv.getMvFinancialHistory() != null) {
					costBasis = vv.getMvFinancialHistory().getCostBasis().doubleValue();
				}
			} else {
				costBasis = segmentVO.getCostBasis().doubleValue();
			}
			pointInTime.setCostBasis(costBasis);

			calendar.setTime(pointInTimeEffectiveDate);
			pointInTime.setEffectiveDate(DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar));

			// BilledToDate
			// If BillSchedule.LastBillDueDate > ET.EffectiveDate, use BS.LastBillDueDate,
			// else use BS.NextBillDueDate
			if (vv.getBillScheduleVO() != null) {
				if ((mvEditTrx != null) && (vv.getBillScheduleVO().getLastBillDueDate() != null)
						&& (new EDITDate(vv.getBillScheduleVO().getLastBillDueDate())
								.after(mvEditTrx.getEffectiveDate()))) {
					calendar.setTime(new Date(
							new EDITDate(vv.getBillScheduleVO().getLastBillDueDate()).getTimeInMilliseconds()));
					pointInTime.setBilledToDate(DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar));
				} else if (vv.getBillScheduleVO().getNextBillDueDate() != null) {
					calendar.setTime(new Date(
							new EDITDate(vv.getBillScheduleVO().getNextBillDueDate()).getTimeInMilliseconds()));
					pointInTime.setBilledToDate(DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar));
				} else {
					System.out.println("Request issue: Incomplete or missing billSchedule"
							+ vv.getSegmentVO().getContractNumber());
				}
			} else {
				System.out.println(
						"Request issue: billSchedule is null: SegmentPK: " + vv.getSegmentVO().getContractNumber());
			}

			// N/A according to Carrie
			// pointInTime.setPaidToDate(DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar));

			// AllowMEC

			// Get MEC values form DB, not WS.
			// using FinancialHistory instead. Carrie - 2022-02-28
			/*
			 * LifeVO mecLifeVO = (LifeVO)
			 * Life.findBy_SegmentPK(vv.getSegmentVO().getSegmentPK()).getVO(); if
			 * (!vv.getSegmentVO().getSegmentStatusCT().equals("SubmitPending") &&
			 * ((mecLifeVO.getMECStatusCT() != null) &&
			 * mecLifeVO.getMECStatusCT().equals("MEC"))) { pointInTime.setIsMEC(YesNo.YES);
			 * } else { pointInTime.setIsMEC(YesNo.NO); }
			 */

			if (vv.getMvFinancialHistory() != null) {
				if (vv.getMvFinancialHistory().getPrevTamraStartDate() != null) {
					calendar.setTime(new Date(
							new EDITDate(vv.getMvFinancialHistory().getPrevTamraStartDate()).getTimeInMilliseconds()));
					pointInTime.setSevenPayStartDate(DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar));
				} else {
					System.out.println("Request issue: missing vv.getMvFinancialHistory().getPrevTamraStartDate(): "
							+ vv.getSegmentVO().getContractNumber());
				}
				if (vv.getMvFinancialHistory().getPrevMECStatusCT().toLowerCase().equals("nonmec")) {
					pointInTime.setIsMEC(YesNo.NO);
				} else {
					pointInTime.setIsMEC(YesNo.YES);
				}
				if ((vv.getMvFinancialHistory().getUnnecessaryPremiumInd() != null)
						&& (vv.getMvFinancialHistory().getUnnecessaryPremiumInd().equals("Y"))) {
					pointInTime.setUnnecessaryPremiumIndicator(Boolean.TRUE);
					pointInTime.setBenefitIncreaseIndicator(Boolean.TRUE);
				} else {
					pointInTime.setUnnecessaryPremiumIndicator(Boolean.FALSE);
					pointInTime.setBenefitIncreaseIndicator(Boolean.FALSE);
				}
				pointInTime.setSevenPayPremium(vv.getMvFinancialHistory().getPrevTamra().doubleValue());
				pointInTime.setGuidelineSinglePremium(
						vv.getMvFinancialHistory().getPrevGuidelineSinglePremium().doubleValue());
				pointInTime.setGuidelineLevelPremium(
						vv.getMvFinancialHistory().getPrevGuidelineLevelPremium().doubleValue());
				pointInTime.setGuidelineLevelPremiumSum(vv.getMvFinancialHistory().getPrevCumGLP().doubleValue());
			} else {
				System.out.println(
						"Request issue: missing vv.getMvFinancialHistory(): " + vv.getSegmentVO().getContractNumber());

			}

			pointInTime.setSumOfPremiumPaidTAMRA(CalcFocusUtils.getSumOfPremiumPaidTAMRA(
					SessionHelper.getSession(SessionHelper.EDITSOLUTIONS).connection(),
					vv.getSegmentVO().getContractNumber(),
					new Date(new EDITDate(vv.getLifeVO().getTamraStartDate()).getTimeInMilliseconds()),
					CalcFocusUtils.addToDate(
							new Date(new EDITDate(vv.getLifeVO().getTamraStartDate()).getTimeInMilliseconds()), 7, 0),
					pointInTimeEffectiveDate));
			pointInTime.setSumOfPremiumPaidDEFRA(CalcFocusUtils.getSumOfPremiumPaidDEFRA(
					SessionHelper.getSession(SessionHelper.EDITSOLUTIONS).connection(),
					vv.getSegmentVO().getContractNumber(),
					new Date(new EDITDate(vv.getSegmentVO().getEffectiveDate()).getTimeInMilliseconds()),
					pointInTimeEffectiveDate));

			pointInTime.setSumOfPremiumPaidTAMRADurationOne(CalcFocusUtils.getSumOfPremiumPaidTAMRA(
					SessionHelper.getSession(SessionHelper.EDITSOLUTIONS).connection(),
					vv.getSegmentVO().getContractNumber(),
					new Date(new EDITDate(vv.getLifeVO().getTamraStartDate()).getTimeInMilliseconds()),
					CalcFocusUtils.addToDate(
							new Date(new EDITDate(vv.getLifeVO().getTamraStartDate()).getTimeInMilliseconds()), 1, 0),
					pointInTimeEffectiveDate));

			pointInTime.setSumOfPremiumPaidTAMRADurationTwo(CalcFocusUtils.getSumOfPremiumPaidTAMRA(
					SessionHelper.getSession(SessionHelper.EDITSOLUTIONS).connection(),
					vv.getSegmentVO().getContractNumber(),
					CalcFocusUtils.addToDate(
							new Date(new EDITDate(vv.getLifeVO().getTamraStartDate()).getTimeInMilliseconds()), 1, 0),
					CalcFocusUtils.addToDate(
							new Date(new EDITDate(vv.getLifeVO().getTamraStartDate()).getTimeInMilliseconds()), 2, 0),
					pointInTimeEffectiveDate));

			pointInTime.setSumOfPremiumPaidTAMRADurationThree(CalcFocusUtils.getSumOfPremiumPaidTAMRA(
					SessionHelper.getSession(SessionHelper.EDITSOLUTIONS).connection(),
					vv.getSegmentVO().getContractNumber(),
					CalcFocusUtils.addToDate(
							new Date(new EDITDate(vv.getLifeVO().getTamraStartDate()).getTimeInMilliseconds()), 2, 0),
					CalcFocusUtils.addToDate(
							new Date(new EDITDate(vv.getLifeVO().getTamraStartDate()).getTimeInMilliseconds()), 3, 0),
					pointInTimeEffectiveDate));

			pointInTime.setSumOfPremiumPaidTAMRADurationFour(CalcFocusUtils.getSumOfPremiumPaidTAMRA(
					SessionHelper.getSession(SessionHelper.EDITSOLUTIONS).connection(),
					vv.getSegmentVO().getContractNumber(),
					CalcFocusUtils.addToDate(
							new Date(new EDITDate(vv.getLifeVO().getTamraStartDate()).getTimeInMilliseconds()), 3, 0),
					CalcFocusUtils.addToDate(
							new Date(new EDITDate(vv.getLifeVO().getTamraStartDate()).getTimeInMilliseconds()), 4, 0),
					pointInTimeEffectiveDate));

			pointInTime.setSumOfPremiumPaidTAMRADurationFive(CalcFocusUtils.getSumOfPremiumPaidTAMRA(
					SessionHelper.getSession(SessionHelper.EDITSOLUTIONS).connection(),
					vv.getSegmentVO().getContractNumber(),
					CalcFocusUtils.addToDate(
							new Date(new EDITDate(vv.getLifeVO().getTamraStartDate()).getTimeInMilliseconds()), 4, 0),
					CalcFocusUtils.addToDate(
							new Date(new EDITDate(vv.getLifeVO().getTamraStartDate()).getTimeInMilliseconds()), 5, 0),
					pointInTimeEffectiveDate));

			pointInTime.setSumOfPremiumPaidTAMRADurationSix(CalcFocusUtils.getSumOfPremiumPaidTAMRA(
					SessionHelper.getSession(SessionHelper.EDITSOLUTIONS).connection(),
					vv.getSegmentVO().getContractNumber(),
					CalcFocusUtils.addToDate(
							new Date(new EDITDate(vv.getLifeVO().getTamraStartDate()).getTimeInMilliseconds()), 5, 0),
					CalcFocusUtils.addToDate(
							new Date(new EDITDate(vv.getLifeVO().getTamraStartDate()).getTimeInMilliseconds()), 6, 0),
					pointInTimeEffectiveDate));

			pointInTime.setSumOfPremiumPaidTAMRADurationSeven(CalcFocusUtils.getSumOfPremiumPaidTAMRA(
					SessionHelper.getSession(SessionHelper.EDITSOLUTIONS).connection(),
					vv.getSegmentVO().getContractNumber(),
					CalcFocusUtils.addToDate(
							new Date(new EDITDate(vv.getLifeVO().getTamraStartDate()).getTimeInMilliseconds()), 6, 0),
					CalcFocusUtils.addToDate(
							new Date(new EDITDate(vv.getLifeVO().getTamraStartDate()).getTimeInMilliseconds()), 7, 0),
					pointInTimeEffectiveDate));

			/*
			 * Carrie - 2022-02-28 - the answer, I believe, is rather than getting these
			 * values from the Life table, we need instead to get them from the same
			 * Financial History record that we are using for <pointInTime><fund><principal>
			 * 
			 * From this same FinancialHistory record, we can find:
			 * <pointInTime><fund><principal> = AccumulatedValue
			 * <pointInTime><sevenPayPremium> = PrevTamra
			 * <pointInTime><guidelineSinglePremium> = PrevGuidelineSinglePremium
			 * <pointInTime><guidelineLevelPremium> = PrevGuidelineLevelPremium
			 * <pointInTime><guidelineLevelPremiumSum> = PrevCumGLP
			 * 
			 * <coverage><deathBenefitOption> = FinancialHistory.PriorDeathBenefitOption
			 * <pointInTime><sevenPayStartDate> = FinancialHistory.PrevTamraStartDate
			 * <pointInTime><costBasis> = FinancialHistory.CostBasis
			 * <pointInTime><noLapseGuaranteeExpiryDate> = FinancialHistory.PrevMAPEndDate
			 * <pointInTime><isMEC> = FinancialHistory.PrevMECStatusCT
			 * 
			 * 
			 */
			/*
			 * Carrie - 2022-3-07 - So, since we are not guaranteed any Financial History
			 * records after the last MV and we are not guaranteed the Life record is the
			 * latest and greatest, I suggest using the following logic:
			 * <guidelineLevePremiumSum> = FinancialHistory.PrevCumGLP from the MV at
			 * pointInTime [so far the same as the current definition. The next part is the
			 * new part:] + if and only if this MV is an anniversary of the policy i.e. if
			 * and only if month(<policy><effectiveDate>) =
			 * month(<pointInTime><effectiveDate>) then add:
			 * FinancialHistory.GuidelineLevelPremium from the MV at the pointInTime (i.e.
			 * the same fh record that PrevCumGLP comes from)
			 * 
			 */
			if (vv.getMvFinancialHistory() != null) {
				pointInTime.setSevenPayPremium(vv.getMvFinancialHistory().getPrevTamra().doubleValue());
				pointInTime.setGuidelineSinglePremium(
						vv.getMvFinancialHistory().getPrevGuidelineSinglePremium().doubleValue());
				pointInTime.setGuidelineLevelPremium(
						vv.getMvFinancialHistory().getPrevGuidelineLevelPremium().doubleValue());
				if (policy.getEffectiveDate().getMonth() == pointInTime.getEffectiveDate().getMonth()) {
					EDITBigDecimal sum = vv.getMvFinancialHistory().getPrevCumGLP()
							.addEditBigDecimal(vv.getMvFinancialHistory().getPrevGuidelineLevelPremium());
					pointInTime.setGuidelineLevelPremiumSum(sum.doubleValue());
				} else {
					pointInTime.setGuidelineLevelPremiumSum(vv.getMvFinancialHistory().getPrevCumGLP().doubleValue());
				}
			} else {
				pointInTime.setSevenPayPremium(vv.getLifeVO().getTamra().doubleValue());
				pointInTime.setGuidelineSinglePremium(vv.getLifeVO().getGuidelineSinglePremium().doubleValue());
				pointInTime.setGuidelineLevelPremium(vv.getLifeVO().getGuidelineLevelPremium().doubleValue());
				pointInTime.setGuidelineLevelPremiumSum(vv.getLifeVO().getCumGuidelineLevelPremium().doubleValue());
			}

			pointInTime.setSevenPayInitialAdjustmentValue(vv.getLifeVO().getTamraInitAdjValue().doubleValue());
			pointInTime.setLowestDeathBenefitDuringSevenPayPeriod(vv.getLifeVO().getFaceAmount().doubleValue());
			pointInTime.setSumOfPremiumRequiredToKeepNoLapseGuaranteeInforce(
					CalcFocusBuilderUtils.getSumOfPremiumRequiredToKeepNoLapseGuaranteeInforce(vv.getPremiumDue(),
							pointInTimeEffectiveDate));

			pointInTime.setSumOfPremiumRequiredToKeepNoLapseGuaranteeInforce(Query.getSumOfPremiumToKeepActive(
					SessionHelper.getSession(SessionHelper.EDITSOLUTIONS).connection(),
					vv.getPremiumDue().getPremiumDuePK(), new EDITDate(vv.getPremiumDue().getEffectiveDate()),
					new EDITDate(vv.getEditTrxVO().getEffectiveDate()), new EDITDate(vv.getLifeVO().getMAPEndDate()),
					new EDITDate(vv.getSegmentVO().getEffectiveDate()),
					new EDITDate(pointInTimeEffectiveDate.getTime())));

			if ((vv.getMvFinancialHistory() == null) || (vv.getMvFinancialHistory().getPrevMAPEndDate() == null)) {
				System.out.println("Error: " + vv.getSegmentVO().getContractNumber());
			}
			calendar.setTime(
					new Date(new EDITDate(vv.getMvFinancialHistory().getPrevMAPEndDate()).getTimeInMilliseconds()));
			pointInTime.setNoLapseGuaranteeExpiryDate(DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar));

			// create first day of year date based on pointInTimeEffectiveDate
			LocalDate pit = LocalDate.fromDateFields(pointInTimeEffectiveDate);
			String firstDayOfYearString = pit.getYear() + "-01-01";
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-mm", Locale.ENGLISH);
			Date firstDayOfYear = formatter.parse(firstDayOfYearString);

			// Reporting values for Month/Year End
			if (vv.getCFoutputInstruction().equals("MonthEndValuation")) {
				pointInTime.setReportingPeriodSumOfPremiumsPaid(CalcFocusUtils.getReportingPeriodSumOfPremiumsPaid(
						SessionHelper.getSession(SessionHelper.EDITSOLUTIONS).connection(),
						vv.getSegmentVO().getContractNumber(), firstDayOfYear, pointInTimeEffectiveDate));

				pointInTime.setReportingPeriodSumOfPremiumChargePaid(
						CalcFocusUtils.getReportingPeriodSumOfPremiumChargePaid(
								SessionHelper.getSession(SessionHelper.EDITSOLUTIONS).connection(),
								vv.getSegmentVO().getContractNumber(), firstDayOfYear, pointInTimeEffectiveDate));

				pointInTime.setReportingPeriodSumOfExpenseCharge(CalcFocusUtils.getReportingPeriodSumOfExpenseCharge(
						SessionHelper.getSession(SessionHelper.EDITSOLUTIONS).connection(),
						vv.getSegmentVO().getContractNumber(), firstDayOfYear, pointInTimeEffectiveDate));

				pointInTime.setReportingPeriodSumOfCOI(CalcFocusUtils.getReportingPeriodSumOfCOI(
						SessionHelper.getSession(SessionHelper.EDITSOLUTIONS).connection(),
						vv.getSegmentVO().getContractNumber(), firstDayOfYear, pointInTimeEffectiveDate));

				pointInTime.setReportingPeriodSumOfAVInterest(CalcFocusUtils.getReportingPeriodSumOfAVInterest(
						SessionHelper.getSession(SessionHelper.EDITSOLUTIONS).connection(),
						vv.getSegmentVO().getContractNumber(), firstDayOfYear, pointInTimeEffectiveDate));

				if (new EDITDate(vv.getSegmentVO().getTerminationDate()).beforeOREqual(extractDate)) {
					Double avInterest = pointInTime.getReportingPeriodSumOfAVInterest();
					Double deathInterest = CalcFocusUtils.getReportingPeriodSumOfAVInterestAddForTerminated(
							SessionHelper.getSession(SessionHelper.EDITSOLUTIONS).connection(),
							vv.getSegmentVO().getContractNumber(), pointInTimeEffectiveDate,
							new Date(mvEditTrx.getEffectiveDate().getTimeInMilliseconds()));

					pointInTime.setReportingPeriodSumOfAVInterest(avInterest + deathInterest);
				}

				pointInTime
						.setReportingPeriodReservesReleasedDeath(CalcFocusUtils.getReportingPeriodReservesReleasedDeath(
								SessionHelper.getSession(SessionHelper.EDITSOLUTIONS).connection(),
								vv.getSegmentVO().getContractNumber(), firstDayOfYear, pointInTimeEffectiveDate));

				pointInTime
						.setReportingPeriodReservesReleasedOther(CalcFocusUtils.getReportingPeriodReservesReleasedOther(
								SessionHelper.getSession(SessionHelper.EDITSOLUTIONS).connection(),
								vv.getSegmentVO().getContractNumber(), firstDayOfYear, pointInTimeEffectiveDate));
			}

			List<String> transactionTypes = new ArrayList<>();
			EDITBigDecimal sumOfPremiumsPaid = null;
			transactionTypes.add("PY");
			transactionTypes.add("WP");
			transactionTypes.add("PW");
			transactionTypes.add("WMD");

			sumOfPremiumsPaid = FinancialHistory.sumTransaction_MultipleTrxTypes(vv.getSegmentVO().getSegmentPK(),
					new EDITDate(vv.getSegmentVO().getEffectiveDate()),
					new EDITDate(pointInTimeEffectiveDate.getTime()), "GrossAmount", transactionTypes);

			pointInTime.setSumOfPremiumPaid(sumOfPremiumsPaid.doubleValue());

			pointInTime.setSumOfPremiumPolicyholderPaidForNoLapseGuaranteeTest(CalcFocusUtils.getSumNoLapsePremiums(
					SessionHelper.getSession(SessionHelper.EDITSOLUTIONS).connection(),
					vv.getSegmentVO().getContractNumber(),
					new Date(new EDITDate(vv.getSegmentVO().getEffectiveDate()).getTimeInMilliseconds()),
					pointInTimeEffectiveDate));

			Double loanAmount = new Double(0.00);
			Double loanInterest = new Double(0.00);
			Double interumInterest = new Double(0.00);
			Double previousInterest = new Double(0.00);
			Integer daysInterest = new Integer(0);
			Double previousCumDollars = new Double(0.00);
			EDITDate previousLoanValuationDate;
			EDITDate interestPaidThroughDate;
			EDITDate loanValuationDate;
			Integer previousDaysInterest = new Integer(0);
			Double currentInterest = new Double(0.0);
			Double currentYearUncapitalizedInterest = new Double(0.00);
			// always zero for AHL
			Double currentYearAdvanceInterestUnearned = new Double(0.00);
			if (vv.getLcFinancialHistoryVO() != null) {

				System.out.println("====================Loan Values=========================");
				System.out.println(vv.getSegmentVO().getContractNumber());
				System.out.println("EditTrxPK: " + vv.getEditTrxVO().getEDITTrxPK());
				BucketHistory bucketHistory = new BucketHistory();
				Bucket bucket = new Bucket();
				EDITTrxHistory fundEditTrxHistory = new EDITTrxHistory();
				EDITTrx fundEditTrx = new EDITTrx();
				bucketHistory = Query.getBucketHistoryForFunds(
						SessionHelper.getSession(SessionHelper.EDITSOLUTIONS).connection(),
						vv.getSegmentVO().getSegmentPK(), new java.sql.Date(pointInTimeEffectiveDate.getTime()));
				bucket = Query.getBucketForFunds(SessionHelper.getSession(SessionHelper.EDITSOLUTIONS).connection(),
						vv.getSegmentVO().getSegmentPK());
				fundEditTrxHistory = EDITTrxHistory.findByPK(bucketHistory.getEDITTrxHistoryFK());
				fundEditTrx = EDITTrx.findByPK(fundEditTrxHistory.getEDITTrxFK());
				Fund fund = new Fund();
				fund.setType(FundType.LOAN);
				fund.setSubtype(FundSubtype.STANDARD);

				loanAmount = bucketHistory.getCumLoanDollars().doubleValue();
				System.out.println("loanAmount: " + loanAmount);

				// Let PrevInterest = BucketHistory.PrevLoanInterestDollars
				// If EDITTrx.TransactionTypeCT= LC, then 0, else Let PrevInterest =
				// BucketHistory.PreviousLoanInterestDollars
				if (!fundEditTrx.getTransactionTypeCT().equals("LC")) {
					previousInterest = bucketHistory.getPreviousLoanInterestDollars().doubleValue();
				}

				// Let PrevLoanCumDollars = BucketHistory.PreviousLoanCumDollars
				previousCumDollars = bucketHistory.getPreviousLoanCumDollars().doubleValue();

				// Let Let PrevLoanValuationDate = Max(BucketHistory.PreviousValuationDate,
				previousLoanValuationDate = bucketHistory.getPreviousValuationDate();
				interestPaidThroughDate = bucket.getInterestPaidThroughDate();
				if (interestPaidThroughDate.after(previousLoanValuationDate)) {
					previousLoanValuationDate = interestPaidThroughDate;
				}

				// Let LoanValuationDate = EDITtrx.EffectiveDate of the EDITTrx transaction
				// related to the BucketHistory chosen above.
				loanValuationDate = fundEditTrx.getEffectiveDate();

				// Calculate PrevDaysInterest = LoanValuationDate - PrevLoanValuationDate
				previousDaysInterest = loanValuationDate.getElapsedDays(previousLoanValuationDate);

				loanInterest = bucket.getLoanInterestRate().doubleValue();

				// Calculate CurrInterest = PrevLoanCumDollars x Bucket.LoanInterestRate x
				// PrevDaysInterest / 365 , rounded to 2 decimals
				currentInterest = (previousCumDollars
						* (loanInterest * (new Double(previousDaysInterest) / new Double(365))));

				// Calculate DaysInterest = <pointInTime><effectiveDate> - LoanValuationDate
				daysInterest = new EDITDate(pointInTimeEffectiveDate.getTime()).getElapsedDays(loanValuationDate);

				// Calculate InterimInterest = principal x Bucket.LoanInterestRate x
				// DaysInterest / 365, rounded to 2 decimals
				interumInterest = (loanAmount * (loanInterest * (new Double(daysInterest) / new Double(365.00))));

				// Calculate currentYearUncapitalizedInterest = PrevInterest + CurrInterest +
				// InterimInterest
				currentYearUncapitalizedInterest = previousInterest + currentInterest + interumInterest;

				fund.setPrincipal(loanAmount);

				calendar.setTime(pointInTimeEffectiveDate);
				fund.setLastPrincipalChangeDate(DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar));
				// currentYearUncapitalizedInterest =
				// CalcFocusUtils.calculateUnearedLoanInterest(SessionHelper.getSession(SessionHelper.EDITSOLUTIONS).connection(),
				// vv.getSegmentVO().getContractNumber(), fund.getPrincipal(),
				// pointInTimeEffectiveDate, lastPrincipleDate);
				fund.setCurrentYearUncapitalizedInterest(currentYearUncapitalizedInterest);
				fund.setCurrentYearUncapitalizedCredit(new Double(0.00));
				fund.setCurrentYearAdvanceInterestUnearned(currentYearAdvanceInterestUnearned);

				pointInTime.getFund().add(fund);
			}

			if (vv.getMvFinancialHistory() != null) {
				Fund fund = new Fund();
				fund.setType(FundType.FIXED);
				fund.setSubtype(FundSubtype.INVESTMENT);

//				FinancialHistory financialHistory = FinancialHistory.findBy_EDITTrxPK_UsingCRUD(mvEditTrx.getEDITTrxPK();
				EDITBigDecimal accumulatedValue;
				accumulatedValue = vv.getMvFinancialHistory().getAccumulatedValue();
				System.out.println("mvFinancialHistoryPK: " + vv.getMvFinancialHistory().getFinancialHistoryPK());

				fund.setPrincipal(accumulatedValue.doubleValue() - loanAmount);

				calendar.setTime(pointInTimeEffectiveDate);
				fund.setLastPrincipalChangeDate(DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar));

				pointInTime.getFund().add(fund);
			}

			if (isBaseSegment) {
				policy.setPointInTime(pointInTime);
				policy.getScenario().add(getScenario(segmentVO, pointInTimeEffectiveDate, coverage));
				if (!vv.getCFoutputInstruction().equals("AnnualStatement")) {
			        addTransactionRequests(coverage);
				}
			}
		}

		return coverage;

	}

	@Override
	public void setParty() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setAddress() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setAnnualStatementAdminData() {
		req.setAnnualStatementAdminData(vv.getAnnualStatementAdminData());
	}

	@Override
	public void setLifetimeFinancialAdviceProjection() {
		// TODO Auto-generated method stub

	}

	public CalculateRequest getReq() {
		return req;
	}

	public void setReq(CalculateRequest req) {
		this.req = req;
	}

	private MonthEndValues getPreviousYearEndValues(SegmentVO segmentVO) throws DatatypeConfigurationException {
		MonthEndValues monthEndValues = new MonthEndValues();
		YearEndValues yearEndValues = YearEndValues.findBySegmentPK_maxYearEndDate(segmentVO.getSegmentPK());
		if ((yearEndValues != null)
				&& (!yearEndValues.getPolicyAdminStatus().equals(PolicyAdminStatus.TERMINATED.toString())
						&& !yearEndValues.getPolicyAdminStatus().equals(PolicyAdminStatus.LAPSED.toString()))) {
			monthEndValues.setLoanedPlusUnloanedAccountValue(yearEndValues.getAccumulatedValue().doubleValue());
			monthEndValues.setInterimAccountValueInterest(yearEndValues.getInterimInterest().doubleValue());
			monthEndValues.setAnnualPremium(yearEndValues.getAnnualPremium().doubleValue());
			Date yearEndDate = new Date(yearEndValues.getYearEndDate().getTimeInMilliseconds());
			GregorianCalendar calendar = new GregorianCalendar();
			calendar.setTime(yearEndDate);
			monthEndValues.setAsOfDate(DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar));
			monthEndValues.setFaceAmount(yearEndValues.getULFaceAmount().doubleValue());
			if (yearEndValues.getPaymentMethod().toLowerCase().equals("list")) {
				monthEndValues.setPaymentMethod(PaymentMethod.LIST_BILL);
			} else if (yearEndValues.getPaymentMethod().toLowerCase().equals("direct")) {
				monthEndValues.setPaymentMethod(PaymentMethod.DIRECT_BILL);
			} else if (yearEndValues.getPaymentMethod().toLowerCase().equals("eft")) {
				monthEndValues.setPaymentMethod(PaymentMethod.ELECTRONIC_FUND_TRANSFER);
			}

			// YEVs shouldn't be null - look into.
			if (yearEndValues.getPaymentMode() != null) {
				monthEndValues.setPaymentMode(PaymentMode.valueOf(yearEndValues.getPaymentMode()));
			} else {
				monthEndValues.setPaymentMode(PaymentMode.QUARTERLY);
			}
			String status = CalcFocusUtils.getCFStatus(yearEndValues.getPolicyAdminStatus());
			monthEndValues.setPolicyAdminStatus(PolicyAdminStatus.fromValue(status));
			monthEndValues.setResidenceState(yearEndValues.getResidentState().toString());
			monthEndValues.setTermRiderFaceAmount(yearEndValues.getTermRiderFace().doubleValue());
			monthEndValues.setTermRiderLives(BigInteger.valueOf(yearEndValues.getTermRiderCount()));
		} else {
			monthEndValues.setLoanedPlusUnloanedAccountValue(0.00);
			monthEndValues.setInterimAccountValueInterest(0.00);
		}

		return monthEndValues;
	}

	private Scenario getScenario(SegmentVO segmentVO, Date pointInTimeEffectiveDate, Coverage coverage)
			throws DatatypeConfigurationException {
		GregorianCalendar calendar = new GregorianCalendar();
		Scenario scenario = new Scenario();
		scenario.setScenarioName("Base Illustration Scenario");
		scenario.setInterestAssumption(InterestAssumption.CURRENT);
		scenario.setMortalityAssumption(MortalityAssumption.CURRENT);
		scenario.setSupplementalScenarioIndicator(Boolean.FALSE);
		scenario.setDeathBenefitOption(DeathBenefitOption.LEVEL);
		scenario.setAllowMEC(Boolean.FALSE);
		scenario.setDisplayWarnings(Boolean.TRUE);

		return scenario;
	}

	private void addTransactionRequests(Coverage coverage) throws DatatypeConfigurationException {
		if (vv.getEditTrxVOs() != null) {
			for (EDITTrxVO editTrxVO : vv.getEditTrxVOs()) {
				// PY, WI, LO, LR, CC, RCL, PW, WP, WMD, CPO, FI, LP
				if (new EDITDate(editTrxVO.getEffectiveDate()).after(new EDITDate(pointInTimeEffectiveDate.getTime()))
						&& (editTrxVO.getTransactionTypeCT().equals("PY")
								|| editTrxVO.getTransactionTypeCT().equals("WI")
								|| editTrxVO.getTransactionTypeCT().equals("LO")
								|| editTrxVO.getTransactionTypeCT().equals("CC")
								|| editTrxVO.getTransactionTypeCT().equals("PW")
								|| editTrxVO.getTransactionTypeCT().equals("WP")
								|| editTrxVO.getTransactionTypeCT().equals("WMD")
								|| editTrxVO.getTransactionTypeCT().equals("FI")
								|| editTrxVO.getTransactionTypeCT().equals("LP")
								|| editTrxVO.getTransactionTypeCT().equals("WP")
								|| editTrxVO.getTransactionTypeCT().equals("CPO")
								|| editTrxVO.getTransactionTypeCT().equals("LR"))) {

					TransactionRequest transactionRequest = new TransactionRequest();
					ContractSetupVO[] contractSetupVO = event.dm.dao.DAOFactory.getContractSetupDAO()
							.findByEDITTrxPK(editTrxVO.getEDITTrxPK());

					Segment editTrxSegment = null;

					if ((editTrxVO.getSelectedRiderPK() != null) && (editTrxVO.getSelectedRiderPK() > 0)) {
						editTrxSegment = Segment.findByPK(editTrxVO.getSelectedRiderPK());
					}
					if ((editTrxSegment != null) && (contractSetupVO[0].getComplexChangeTypeCT() != null)
							&& (contractSetupVO[0].getComplexChangeTypeCT().equals("FaceIncrease")
									|| contractSetupVO[0].getComplexChangeTypeCT().equals("RiderChange")
									|| contractSetupVO[0].getComplexChangeTypeCT().equals("RiderAdd")
											&& !contractSetupVO[0].getComplexChangeTypeCT().equals("RiderTerm"))) {
						policy.setCoverageUpdates(getCoverageUpdates(editTrxSegment, editTrxVO));
					} else {
						Segment baseSegment = Segment.findBy_EDITTrxPK(editTrxVO.getEDITTrxPK());

						Life life = Life.findBy_SegmentPK(baseSegment.getSegmentPK());

						// don't use method, use value in WS
						transactionRequest.setType(TransactionType.fromValue(CalcFocusUtils.getCFTransactionType(
								editTrxVO.getTransactionTypeCT(), contractSetupVO[0].getComplexChangeTypeCT())));
						transactionRequest.setStartDateType(StartDateType.SPECIFIED_DATE);
						Date effectiveDate = new Date(
								new EDITDate(editTrxVO.getEffectiveDate()).getTimeInMilliseconds());
						GregorianCalendar calendar = new GregorianCalendar();
						calendar.setTime(effectiveDate);
						transactionRequest
								.setEffectiveDate(DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar));
						transactionRequest.setMode(PaymentMode.SINGLE_PAY);
						if (editTrxSegment == null)  {
							editTrxSegment = baseSegment;
						}
						/*
			            PlanCode planCode = CalcFocusUtils.getCalcFocusPlanCode(
					    SessionHelper.getSession(SessionHelper.ENGINE).connection(), editTrxSegment.getRatedGenderCT(),
					        editTrxSegment.getUnderwritingClassCT(), editTrxSegment.getOptionCodeCT(), 
					        editTrxSegment.getGroupPlan(),
					        vv.getCompany().getCompanyName());
						transactionRequest.setLegacyPlanCode(planCode.getLegacyPlanCode());
						*/
						transactionRequest.setLegacyPlanCode(coverage.getLegacyPlanCode());

						// If and only if EDITTrx.TransactionTypeCT = CC and
						// ContractSetup.ComplexChangeTypeCT = DBOption,
						// set to Life.DeathBenefitOptionCT.
						if (editTrxVO.getTransactionTypeCT().equals("CC")) {
							if (contractSetupVO[0].getComplexChangeTypeCT().equals("DBOption")) {
								String deathBenefitOption;
								deathBenefitOption = vv.getFinancialHistory().getPriorDeathBenefitOption();
								transactionRequest
										.setNewDeathBenefitOption(DeathBenefitOption.fromValue(deathBenefitOption));
							}
							if (contractSetupVO[0].getComplexChangeTypeCT().equals("ClassChange")) {
								transactionRequest.setTransactionAmountRule(TransactionRequestAmountRule.NONE);
								transactionRequest.setProductCode(vv.getCalcFocusPlanCode());
								if (contractSetupVO[0].getComplexChangeNewValue().equals("Smoker")) {
									transactionRequest.setNewTobaccoUse(TobaccoUse.SMOKER);
								} else {
									transactionRequest.setNewTobaccoUse(TobaccoUse.NONSMOKER);
								}
							}
							// If and only if EDITTrx.TransactionTypeCT = CC
							// and ContractSetup.ComplexChangeTypeCT = RateClass,
							// set to ContractClient.ClassCT, as connected to the coverage being changed.
							// Coverage is determined via EDITTrx.SelectedRiderPK
							if (contractSetupVO[0].getComplexChangeTypeCT().equals("RateClass")) {
								transactionRequest.setNewTobaccoUse(
										TobaccoUse.fromValue(editTrxSegment.getContractClient().getClassCT()));
							}
						}

						// Find the SegmentHistory records connected to the first (if multiple) WI
						// transaction in transactionResults.
						// SegmentHistory is connected to EDITTrx via EDITTrxHistory.
						// For each UL, ULIncrease or FI coverage, i.e. <coverage><baseIndicator> =
						// Base,
						// use SegmentHistory.PrevFaceAmount from the SegmentHistory as found above and
						// also connected to the given Segment
						// via SegmentHistory.SegmentFK.

						if (transactionRequest.getType().equals(TransactionType.PARTIAL_SURRENDER)
								&& (vv.getMvFinancialHistory() != null)) {
							if (vv.getCFoutputInstruction().equals("MonthEndValuation")) {
								EDITTrxHistory partialWithdrawalEditTrxHistory = EDITTrxHistory
										.findBy_EDITTrxFK(editTrxVO.getEDITTrxPK());
								FinancialHistory partialWithdrawalFinancialHistory = FinancialHistory
										.findBy_EDITTrxHistoryPK(
												partialWithdrawalEditTrxHistory.getEDITTrxHistoryPK())[0];

								List<Coverage> coverages = policy.getCoverage();
								for (Coverage c : coverages) {
									if (c.getBaseIndicator().equals(BaseIndicator.BASE)) {
										SegmentHistory[] segmentHistories = SegmentHistory.findBy_EDITTrxHistoryFK(
												partialWithdrawalEditTrxHistory.getEDITTrxHistoryPK());
										c.setFaceAmount(segmentHistories[0].getPrevFaceAmount().doubleValue());

									}
								}

								transactionRequest
										.setAmount(partialWithdrawalFinancialHistory.getNetAmount().doubleValue());

							} else {
								transactionRequest.setAmount(vv.getCFTrxRequestAmount());
							}
						} else if (transactionRequest.getType().equals(TransactionType.BENEFITS_DECREASE)) {
							transactionRequest.setAmount(vv.getFaceAmount().doubleValue());
						} else {
							transactionRequest.setAmount(editTrxVO.getTrxAmount().doubleValue());
						}

						// If and only if EDITTrx.TransactionTypeCT = CC
						// and ContractSetup.ComplexChangeTypeCT = RateClass,
						// set to ContractClient.ClassCT, as connected to the coverage being changed.
						// Coverage is determined via EDITTrx.SelectedRiderPK
						if (editTrxVO.getTransactionTypeCT().equals("CC")
								&& contractSetupVO[0].getComplexChangeTypeCT().equals("RateClass")) {
							transactionRequest.setNewTobaccoUse(
									TobaccoUse.fromValue(editTrxSegment.getContractClient().getClassCT()));
						}

						transactionRequest.setPartyGUIDAssoicatedWithChange(String.valueOf(editTrxVO.getEDITTrxPK()));
						policy.getScenario().get(0).getTransactionRequest().add(transactionRequest);
					}

				}
			}
		}

	}

	private CoverageUpdates getCoverageUpdates(Segment segment, EDITTrxVO editTrxVO)
			throws HibernateException, DatatypeConfigurationException {
		GregorianCalendar calendar = new GregorianCalendar();
		Coverage coverageUpdate = new Coverage();
//		coverageUpdate.setInitialFaceAmount(vv.getSegmentVO().getAmount().doubleValue());
		coverageUpdate.setInitialFaceAmount(vv.getLifeVO().getFaceAmount().doubleValue());
		coverageUpdate.setAdminSpecifiedCOICharge(0.00);

		System.out.println(segment.getAmount()); // ECK
		if ((segment.getSegmentFK() == null)
				|| ((segment.getSegmentFK() != null) && (segment.getOptionCodeCT().equals("ULIncrease")))) {
			coverageUpdate.setFaceAmount(vv.getTotalFaceAmount().doubleValue());
			PlanCode planCode = CalcFocusUtils.getCalcFocusPlanCode(
					SessionHelper.getSession(SessionHelper.ENGINE).connection(), segment.getRatedGenderCT(),
					segment.getUnderwritingClassCT(), segment.getOptionCodeCT(), segment.getGroupPlan(),
					vv.getCompany().getCompanyName());
			coverageUpdate.setProductCode(planCode.getCalcFocusProductCode());
			coverageUpdate.setLegacyPlanCode(planCode.getLegacyPlanCode());
		} else {
			coverageUpdate.setLegacyPlanCode(segment.getOptionCodeCT());
			coverageUpdate.setFaceAmount(segment.getAmount().doubleValue());
		}
		coverageUpdate.setSourceCoverageID(Long.toString(segment.getSegmentPK()));
		// use Primary Insured age at issue for CTR - per Dan G.
		if (segment.getOptionCodeCT().equals("CTR")) {
			ContractClient contractClientPI[] = ContractClient
					.findBy_SegmentPK_And_RoleTypeCT(vv.getSegmentVO().getSegmentPK(), ClientRole.ROLETYPECT_INSURED);
			coverageUpdate.setIssueAge(contractClientPI[0].getIssueAge());
		} else {
			coverageUpdate.setIssueAge(segment.getAgeAtIssue());
		}
		coverageUpdate.setIssueAgeSecondary(0);
		coverageUpdate.setTableRating(1.0);
		coverageUpdate.setTableRatingSecondary(0.0);
		calendar.setTime(new Date(new EDITDate(editTrxVO.getEffectiveDate()).getTimeInMilliseconds()));
		coverageUpdate.setEffectiveDate(DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar));
		calendar.setTime(new Date(new EDITDate(segment.getTerminationDate()).getTimeInMilliseconds()));
		coverageUpdate.setTerminationDate(DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar));
		coverageUpdate.setCoverageNumber(null);
		coverageUpdate.setStartingStatus(null);
		coverageUpdate.setStartingProductCodeDescription(null);
		coverageUpdate.setModalPremium(0.00);
		coverageUpdate.setAnnualPremium(vv.getAnnualPremium());
		coverageUpdate.setGuidelineLevelPremium(0.00);
		coverageUpdate.setGuidelineSinglePremium(0.00);
		coverageUpdate.setGapInCoverageMonths(vv.getGapInCoverageMonths());

		// when the legacyPlanCode is not in (‘UL’, ‘ULIncrease’, ‘FI’), then the
		// <baseIndicator> should be Rider instead of Base.
		if (segment.getOptionCodeCT().equals("ULIncrease") || segment.getOptionCodeCT().equals("UL")
				|| segment.getOptionCodeCT().equals("FI")) {
			coverageUpdate.setBaseIndicator(BaseIndicator.BASE);
		} else {
			coverageUpdate.setBaseIndicator(BaseIndicator.RIDER);
		}
		coverageUpdate.setReducePremiumWhenExpired(false);

		if (vv.getTransactionType().equals("FI")) {
			Solve solve = new Solve();
			solve.setSolveGoalType(SolveGoalType.FACE_PREMIUM_SOLVE);
			solve.setFacePremiumSolveType(FacePremiumSolveType.ANNUAL_PREMIUM);
			solve.setGoalAmountExpressedAs(GoalAmountExpressedAs.SPECIFIED_AMOUNT);
			solve.setGoalAmount(vv.getGoalAmount());
			solve.setGoalPercent(vv.getGoalPercent());
			if (vv.getSolveEnd() != null) {
				solve.setSolveEnd(EndDateType.fromValue(vv.getSolveEnd()));
			}
			solve.setGoalEndDate(DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar));
			solve.setGoalYear(0);
			solve.setGoalAge(0);
			coverageUpdate.setSolve(solve);
		}
		coverageUpdate.setRetainedAmount(0.00);
		coverageUpdate.setMRCFactor(0.00);

		CoverageUpdates coverageUpdates = new CoverageUpdates();
		coverageUpdates.getCoverage().add(coverageUpdate);
		return coverageUpdates;

	}

	HashSet<Long> partyPKs = new HashSet<>();
	HashSet<Long> addressPKs = new HashSet<>();

	private List<Role> getRoles(Coverage coverage, SegmentVO segmentVO, boolean isBaseSegment)
			throws DatatypeConfigurationException {
		List<Role> roles = new ArrayList<>();
		boolean agentFound = false;

		for (ContractClientVO contractClientVO : vv.getContractClientVOs()) {
			for (ClientRoleVO clientRoleVO : vv.getClientRoleVOs()) {
				if (((clientRoleVO.getClientRolePK() == contractClientVO.getClientRoleFK())
						&& !clientRoleVO.getRoleTypeCT().equals("SADD"))
						|| (clientRoleVO.getRoleTypeCT().equals("AgentServicing"))) {
					ClientDetailVO clientDetailVO = null;
					if (!agentFound && clientRoleVO.getRoleTypeCT().equals("AgentServicing")) {
						ClientDetail clientDetail = ClientDetail
								.findBy_ClientDetailPK(clientRoleVO.getClientDetailFK());
						clientDetailVO = (ClientDetailVO) clientDetail.getVO();
						agentFound = true;
					} else {
						if (!clientRoleVO.getRoleTypeCT().equals("AgentServicing")) {
							clientDetailVO = (ClientDetailVO) clientRoleVO.getParentVO(ClientDetailVO.class);
						}
					}
					if (clientDetailVO != null) {
						Role role = new Role();
						role.setPartyGUID(Long.toString(clientDetailVO.getClientDetailPK()));
						System.out.println(clientRoleVO.getRoleTypeCT());
						role.setRoleType(CalcFocusBuilderUtils.getRoleType(clientRoleVO.getRoleTypeCT()));
						if (!partyPKs.contains(clientDetailVO.getClientDetailPK())) {
							req.getParty().add(this.getParty(clientDetailVO, clientRoleVO));
							partyPKs.add(clientDetailVO.getClientDetailPK());
							ClientAddress[] clientAddresses = ClientAddress
									.findAllActiveByClientDetailPK(clientDetailVO.getClientDetailPK());
							if (clientAddresses != null) {
								for (ClientAddress clientAddress : clientAddresses) {
									ClientAddressVO clientAddressVO = (ClientAddressVO) clientAddress.getVO();
									if (!addressPKs.contains(clientAddressVO.getClientAddressPK())) {
										Address address = this.getAddress(clientAddressVO);
										req.getAddress().add(address);
										addressPKs.add(clientAddressVO.getClientAddressPK());
									}
								}
							}
						}
						if ((role.getRoleType() != null) && (role.getRoleType().equals(RoleType.PRIMARY_INSURED))) {
							coverage.setTobaccoUse(CalcFocusBuilderUtils.getTobaccoUse(contractClientVO));
						}
						roles.add(role);
					}
				}
			}
		}
		return roles;
	}

	public Party getParty(ClientDetailVO clientDetailVO, ClientRoleVO clientRoleVO)
			throws DatatypeConfigurationException {
		Party party = new Party();
		party.setGUID(Long.toString(clientDetailVO.getClientDetailPK()));
		EDITDate dob = new EDITDate(clientDetailVO.getBirthDate());
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTime(new Date(dob.getTimeInMilliseconds()));

		if ((clientDetailVO != null) && (clientDetailVO.getCorporateName() != null)) {
			party.setFullName(clientDetailVO.getCorporateName());
		} else if (clientDetailVO != null) {
			if (clientDetailVO.getFirstName() != null) {
				party.setFullName(clientDetailVO.getFirstName());
				party.setFirstName(clientDetailVO.getFirstName());
			} else {
				System.out.println("Request issue: ClientDetail.getFirstName issue: ClientDetailPK: "
						+ clientDetailVO.getClientDetailPK());
			}
			if (clientDetailVO.getLastName() != null) {
				party.setFullName(party.getFullName() + " " + clientDetailVO.getLastName());
				party.setLastName(clientDetailVO.getLastName());
			} else {
				System.out.println("Request issue: ClientDetail.getLastName issue: ClientDetailPK: "
						+ clientDetailVO.getClientDetailPK());
			}
			try {
				party.setGender(Gender.fromValue(clientDetailVO.getGenderCT()));
			} catch (Exception e) {
				System.out.println("Request issue: ClientDetail.getGenderCT issue: ClientDetailPK: "
						+ clientDetailVO.getClientDetailPK());
			}
			party.setDateOfBirth(DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar));
		}
		// else {
		// System.out.println("Request issue: ClientDetail or ClientDetail.getGenderCT
		// is null: "
		// + vv.getSegmentVO().getContractNumber());
		// }
		if (clientRoleVO.getRoleTypeCT().equals("AgentServicing")) {
			party.setExternalId(clientRoleVO.getReferenceID());
		}

		party.getAddressAssociation().addAll(this.getAddressAssociation(clientDetailVO));
		return party;
	}

	public List<AddressAssociation> getAddressAssociation(ClientDetailVO clientDetailVO) {
		List<AddressAssociation> addressAssociations = new ArrayList<>();
		// ClientAddressVO[] clientAddressVOs = clientDetailVO.getClientAddressVO();
		ClientAddress[] clientAddresses = ClientAddress
				.findAllActiveByClientDetailPK(clientDetailVO.getClientDetailPK());
		if (clientAddresses != null) {
			for (ClientAddress clientAddress : clientAddresses) {
				ClientAddressVO clientAddressVO = (ClientAddressVO) clientAddress.getVO();
				AddressAssociation addressAssociation = new AddressAssociation();
				addressAssociation.setAddressGUID(Long.toString(clientAddressVO.getClientAddressPK()));
				if (clientAddressVO.getAddressTypeCT().equals("Mailing")) {
					addressAssociation.setAddressType(AddressType.MAILING);
				} else if (clientAddressVO.getAddressTypeCT().equals("Residence")) {
					addressAssociation.setAddressType(AddressType.RESIDENCE);
				} else if (clientAddressVO.getAddressTypeCT().equals("PrimaryAddress")) {
					addressAssociation.setAddressType(AddressType.MAILING);
				}
				if (addressAssociation.getAddressType() != null) {
					addressAssociations.add(addressAssociation);
				}
			}
		}
		return addressAssociations;
	}

	public Address getAddress(ClientAddressVO clientAddressVO) throws DatatypeConfigurationException {
		// get Party from Owner

		Address address = new Address();
		address.setAddressGUID(Long.toString(clientAddressVO.getClientAddressPK()));
		address.setLine1(clientAddressVO.getAddressLine1());
		address.setLine2(clientAddressVO.getAddressLine2());
		address.setLine3(clientAddressVO.getAddressLine3());
		address.setCity(clientAddressVO.getCity());

		address.setState(clientAddressVO.getStateCT());
		address.setZip(clientAddressVO.getZipCode());

		addressPKs.add(clientAddressVO.getClientAddressPK());
		return address;

	}

	private List<SegmentVO> getULIncreaseSegmentVOs(SegmentVO[] ulIncreaseSegments) {
		List<SegmentVO> uliSegments = new ArrayList<>();
		for (SegmentVO riderSegmentVO : vv.getRiderSegmentVOs()) {
			if (riderSegmentVO.getOptionCodeCT().equals("ULIncrease")) {
				uliSegments.add(riderSegmentVO);
			}
		}
		return uliSegments;

	}

	public String isYev() {
		return vv.getIsYev();
	}
}
