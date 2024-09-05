package com.selman.calcfocus.factory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.selman.calcfocus.request.AnnualStatementAdminData;

import contract.ContractClient;
import contract.PremiumDue;
import edit.common.vo.BillScheduleVO;
import edit.common.vo.ClientDetailVO;
import edit.common.vo.ClientRoleVO;
import edit.common.vo.ContractClientVO;
import edit.common.vo.EDITTrxVO;
import edit.common.vo.FinancialHistoryVO;
import edit.common.vo.LifeVO;
import edit.common.vo.ProductStructureVO;
import edit.common.vo.SegmentHistoryVO;
import edit.common.vo.SegmentVO;
import engine.Company;
import event.FinancialHistory;
import event.SegmentHistory;

public class VenusValues {

	EDITTrxVO editTrxVO;
	EDITTrxVO lcEditTrxVO;
	EDITTrxVO[] editTrxVOs;
	SegmentVO segmentVO;
	SegmentVO[] riderSegmentVOs;
	SegmentHistory[] segmentHistories;
	ContractClientVO[] contractClientVOs;
	ClientRoleVO[] clientRoleVOs;
	BillScheduleVO billScheduleVO;
	ClientDetailVO clientDetailVO;
	ClientRoleVO clientRoleVO;
	ProductStructureVO productStructureVO;
	LifeVO lifeVO;
	FinancialHistory financialHistory;
	FinancialHistory mvFinancialHistory;
	FinancialHistoryVO mvFinancialHistoryVO;
	FinancialHistoryVO lcFinancialHistoryVO;
	AnnualStatementAdminData annualStatementAdminData;
	boolean baseIndicator;

	// Fields with default values when not in working storage
	String carrierAdminSystem;
	boolean allowMEC;
	String loanInterestAssumption;
	Double lastMonthaversaryCharge;
	boolean dboSwitch;
	Double sumOfPremiumPaidTAMRADurationOne;
	Double sumOfPremiumPaidTAMRADurationTwo;
	Double sumOfPremiumPaidTAMRADurationThree;
	Double sumOfPremiumPaidTAMRADurationFour;
	Double sumOfPremiumPaidTAMRADurationFive;
	Double sumOfPremiumPaidTAMRADurationSix;
	Double sumOfPremiumPaidTAMRADurationSeven;
	Double sumOfPremiumRequiredToKeepNoLapseGuaranteeInforce;
    Double CFTrxRequestAmount;
	boolean unnecessaryPremiumIndicator;
	boolean benefitIncreaseIndicator;

	// Fields that send NULL when not in working storage
	String CFprocessType;
	String CFoutputInstruction;
	String CFtransactionType;
	String transactionType;
	Double cfMapAccum;
	String policyStatus;
	String fundType;
	String subType;
	Double principal;
	Date lastPrincipalChangeDate;
	Double currentYearUncapitalizedInterest;
	Double currentYearUncapitalizedCredit;
	Double faceAmount;
	String legacyPlanCode;
	Integer issueAge;
	String tobaccoUse;
	String partyGUID;
	String roleType;
	Double interestAssumption;
	String mortalityAssumption;
	String financialEventType;
	Double amount;
	String mode;
	String historicalIndicator;
	String groupNumber;
	Date startDate;
	Date endDate;
	Date policyAccumulationValueAtStartDate;
	Date policyLoanedAccountValueAtStartDate;
	Date faceAmountAtStartDate;
	Date policyAccumulationValueAtEndDate;
	Date policyLoanInterestAccruedAtEndDate;
	Date surrenderChargeAtEndDate;
	Date deathBenefitAtEndDate;
	Date faceAmountAtEndDate;
	Double sumOfPremiumsPaid;
	Double sumOfLoans;
	Double cumLoanDollars;
	Double sumOfLoanRepayments;
	Double sumOfWithdrawals;
	Double sumOfRiderCOI;
	Double sumOfCOI;
	Double interestEarned;
	Double mortalityChargeAndRiderCOI;
	Double baseCOI;
	Double riderCOI;
	Double sumOfAdminFees;
	Double interestRate;
	Double sumOfPremiumLoad;
	Double faceIncrease;
	Date processDate;
	Long drivingSegmentPK;
	String complexChangeType;
	String insuranceCompany;
	Integer gapInCoverageMonths;
	PremiumDue premiumDue;
	Company company;
	EDITTrxVO mvEditTrxVO;

//	push val:Base Illustration Scenario
//	push param:&Segment.TerminationDate
//	push val:Premium
	Double specifiedAmount;
	Double goalAmount;
	String scenarioName;
	String transactionRequestType;
	String startDateType;
	String faceSolveBasedOnPremium;
	String faceAmountRule;
	String facePremiumSolve;
	String solveGoalType;
	String facePremiumSolveType;
	String goalAmountExpressedAs;
	String solveEnd;
	String goalYear;
	Date transactionRequestEffectiveDate;
	Date specifiedDate;
	Date trxEffectiveDate;
	Date maturityDate;
	Date goalEndDate;
	Double annualPremium;
	Double goalPercent;
	Integer subdays;
	Integer goalAge;
	Integer newRiderUnits;
	Double newFaceAmount;
	Double totalFaceAmount;
	String isAsync;
	String isYev;
	String mevContractNumber;
	String calcFocusPlanCode;

	public VenusValues() {
	}

	public VenusValues(Map<String, Object> v) throws ParseException {
		this.specifiedAmount = ((v.get("specifiedAmount") == null) ? null
				: Double.valueOf((String) v.get("specifiedAmount")));
		this.goalAmount = ((v.get("goalAmount") == null) ? null : Double.valueOf((String) v.get("goalAmount")));
		this.newFaceAmount = ((v.get("newFaceAmount") == null) ? null : Double.valueOf((String) v.get("newFaceAmount")));
		this.newRiderUnits = ((v.get("newRiderUnits") == null) ? null : Integer.valueOf((String) v.get("newRiderUnits")));
		this.totalFaceAmount = ((v.get("totalFaceAmount") == null) ? null : Double.valueOf((String) v.get("totalFaceAmount")));
		this.CFTrxRequestAmount = ((v.get("CFTrxRequestAmount") == null) ? null : Double.valueOf((String) v.get("CFTrxRequestAmount")));
		this.annualPremium = ((v.get("annualPremium") == null) ? null
				: Double.valueOf((String) v.get("annualPremium")));
		this.goalPercent = ((v.get("goalPercent") == null) ? null : Double.valueOf((String) v.get("goalPercent")));
		this.scenarioName = ((v.get("scenarioName") == null) ? null : (String) v.get("scenarioName"));
		this.transactionRequestType = ((v.get("transactionRequestType") == null) ? null
				: (String) v.get("transactionRequestType"));
		this.startDateType = ((v.get("startDateType") == null) ? null : (String) v.get("startDateType"));
		this.faceSolveBasedOnPremium = ((v.get("faceSolveBasedOnPremium") == null) ? null
				: (String) v.get("faceSolveBasedOnPremium"));
		this.faceAmountRule = ((v.get("faceAmountRule") == null) ? null : (String) v.get("faceAmountRule"));
		this.facePremiumSolve = ((v.get("facePremiumSolve") == null) ? null : (String) v.get("facePremiumSolve"));
		this.solveGoalType = ((v.get("solveGoalType") == null) ? null : (String) v.get("solveGoalType"));
		this.facePremiumSolveType = ((v.get("facePremiumSolveType") == null) ? null
				: (String) v.get("facePremiumSolveType"));
		this.goalAmountExpressedAs = ((v.get("sgoalAmountExpressedAs") == null) ? null
				: (String) v.get("sgoalAmountExpressedAs"));
		this.solveEnd = ((v.get("solveEnd") == null) ? null : (String) v.get("solveEnd"));
		this.goalYear = ((v.get("goalYear") == null) ? null : (String) v.get("goalYear"));
		this.transactionRequestEffectiveDate = ((v.get("transactionRequestEffectiveDate") == null) ? null
				: (Date) v.get("transactionRequestEffectiveDate"));
		this.specifiedDate = ((v.get("specifiedDate") == null) ? null : (Date) v.get("specifiedDate"));
		this.trxEffectiveDate = ((v.get("trxEffectiveDate") == null) ? null : (Date) v.get("trxEffectiveDate"));
		this.maturityDate = ((v.get("maturityDate") == null) ? null : (Date) v.get("maturityDate"));
		this.goalEndDate = ((v.get("goalEndDate") == null) ? null : (Date) v.get("goalEndDate"));
		this.subdays = ((v.get("subdays") == null) ? null : Integer.valueOf((String) v.get("subdays")));
		this.goalAge = ((v.get("goalAge") == null) ? null : Integer.valueOf((String) v.get("goalAge")));

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy/mm/dd");
		this.drivingSegmentPK = ((v.get("drivingSegmentPK") == null) ? null : (Long) v.get("drivingSegmentPK"));
		this.gapInCoverageMonths = ((v.get("gapInCoverageMonths") == null) ? null
				: Integer.valueOf((String) v.get("gapInCoverageMonths")));
		this.complexChangeType = ((v.get("complexChangeType") == null) ? null : (String) v.get("complexChangeType"));
		this.insuranceCompany = ((v.get("insuranceCompany") == null) ? null : (String) v.get("insuranceCompany"));
		this.allowMEC = ((v.get("allowMEC") == null) ? null : Boolean.valueOf((String) v.get("allowMEC")));
		this.amount = ((v.get("amount") == null) ? null : Double.valueOf((String) v.get("amount")));
		this.baseCOI = ((v.get("baseCOI") == null) ? null : Double.valueOf((String) v.get("baseCOI")));
		this.baseIndicator = ((v.get("baseIndicator") == null) ? null
				: Boolean.valueOf((String) v.get("baseIndicator")));
		this.benefitIncreaseIndicator = ((v.get("benefitIncreaseIndicator") == null) ? null
				: (Boolean.valueOf((String) v.get("benefitIncreaseIndicator"))));
		this.billScheduleVO = (BillScheduleVO) v.get("BillScheduleVO");
		this.carrierAdminSystem = ((v.get("carrierAdminSystem") == null) ? null : (String) v.get("carrierAdminSystem"));
		this.isAsync = ((v.get("isAsync") == null) ? "off" : (String) v.get("isAsync"));
		this.isYev = ((v.get("isYev") == null) ? "off" : (String) v.get("isYev"));
		this.mevContractNumber = ((v.get("mevContractNumber") == null) ? null : (String) v.get("mevContractNumber"));
		this.CFoutputInstruction = ((v.get("CFoutputInstruction") == null) ? null
				: (String) v.get("CFoutputInstruction"));
		this.CFtransactionType = ((v.get("CFtransactionType") == null) ? null : (String) v.get("CFtransactionType"));

		this.CFprocessType = ((v.get("cFprocessType") == null) ? null : (String) v.get("cFprocessType"));
		this.groupNumber = ((v.get("groupNumber") == null) ? null : (String) v.get("groupNumber"));
		this.cfMapAccum = ((v.get("CFMAPAccum") == null) ? null : Double.valueOf((String) v.get("CFMAPAccum")));
		this.clientDetailVO = (ClientDetailVO) v.get("clientDetailVO");
		this.clientRoleVO = (ClientRoleVO) v.get("clientRoleVO");
		this.currentYearUncapitalizedCredit = ((v.get("currentYearUncapitalizedCredit") == null) ? null
				: Double.valueOf((String) v.get("currentYearUncapitalizedCredit")));
		this.currentYearUncapitalizedInterest = ((v.get("currentYearUncapitalizedInterest") == null) ? null
				: Double.valueOf((String) v.get("currentYearUncapitalizedInterest")));
		this.dboSwitch = ((v.get("dboSwitch") == null) ? null : Boolean.valueOf((String) v.get("dboSwith")));
		this.deathBenefitAtEndDate = ((v.get("deathBenefitAtEndDate") == null) ? null
				: formatter.parse((String) v.get("deathBenefitAtEndDate")));
		this.editTrxVO = ((v.get("EDITTrxVO") == null) ? null : (EDITTrxVO) v.get("EDITTrxVO"));
		this.lcEditTrxVO = ((v.get("lcEDITTrxVO") == null) ? null : (EDITTrxVO) v.get("lcEDITTrxVO"));
		this.financialHistory = ((v.get("financialHistory") == null) ? null
				: (FinancialHistory) v.get("financialHistory"));
		this.mvFinancialHistory = ((v.get("mvFinancialHistory") == null) ? null
				: (FinancialHistory) v.get("mvFinancialHistory"));
		this.mvFinancialHistoryVO = ((v.get("mvFinancialHistoryVO") == null) ? null
				: (FinancialHistoryVO) v.get("mvFinancialHistoryVO"));
		this.lcFinancialHistoryVO = ((v.get("lcFinancialHistoryVO") == null) ? null
				: (FinancialHistoryVO) v.get("lcFinancialHistoryVO"));
		this.lifeVO = (LifeVO) v.get("LifeVO");
		this.premiumDue = (PremiumDue) v.get("PremiumDue");
		this.company = (Company) v.get("Company");
		this.annualStatementAdminData = (AnnualStatementAdminData) v.get("AnnualStatementAdminData");
		this.contractClientVOs = (ContractClientVO[]) v.get("ContractClientVOs");
		this.clientRoleVOs = (ClientRoleVO[]) v.get("ClientRoleVOs");
		this.endDate = ((v.get("endDate") == null) ? null : formatter.parse((String) v.get("endDate")));
		this.processDate = ((v.get("processDate") == null) ? null : formatter.parse((String) v.get("processDate")));
		this.faceAmount = ((v.get("faceAmount") == null) ? null : Double.valueOf((String) v.get("faceAmount")));
		this.faceAmountAtEndDate = ((v.get("faceAmountAtEndDate") == null) ? null
				: formatter.parse((String) v.get("faceAmountAtEndDate")));
		this.faceAmountAtStartDate = ((v.get("faceAmountAtStartDate") == null) ? null
				: formatter.parse((String) v.get("faceAmountAtStartDate")));
		this.financialEventType = ((v.get("financialEventType") == null) ? null : (String) v.get("financialEventType"));
		this.fundType = ((v.get("fundType") == null) ? null : (String) v.get("fundType"));
		this.historicalIndicator = ((v.get("historicalIndicator") == null) ? null
				: (String) v.get("historicalIndicator"));
		this.interestAssumption = ((v.get("interestAssumption") == null) ? null
				: Double.valueOf((String) v.get("interestAssumption")));
		this.mode = ((v.get("mode") == null) ? null : (String) v.get("mode"));
		this.mortalityAssumption = ((v.get("mortalityAssumption") == null) ? null
				: (String) v.get("mortalityAssumption"));
		this.mortalityChargeAndRiderCOI = ((v.get("mortalityChargeAndRiderCOI") == null) ? null
				: Double.valueOf((String) v.get("mortalityChargeAndRiderCOI")));
		this.partyGUID = ((v.get("partyGUID") == null) ? null : (String) v.get("partyGUID"));
		this.policyAccumulationValueAtEndDate = ((v.get("policyAccumulationValueAtEndDate") == null) ? null
				: formatter.parse((String) v.get("policyAccumulationValueAtEndDate")));
		this.policyAccumulationValueAtStartDate = ((v.get("policyAccumulationValueAtStartDate") == null) ? null
				: formatter.parse((String) v.get("policyAccumulationValueAtStartDate")));
		this.policyLoanedAccountValueAtStartDate = ((v.get("policyLoanedAccountAtStartDate") == null) ? null
				: formatter.parse((String) v.get("policyLoanedAccountAtStartDate")));
		this.policyLoanInterestAccruedAtEndDate = ((v.get("policyLoanInterestAccruedAtEndDate") == null) ? null
				: formatter.parse((String) v.get("policyLoanedInterestAccruedAtEndDate")));
		this.policyStatus = ((v.get("policyStatus") == null) ? null : (String) v.get("policyStatus"));
		this.legacyPlanCode = ((v.get("legacyPlanCode") == null) ? null : (String) v.get("legacyPlanCode"));
		this.principal = ((v.get("principle") == null) ? null : (Double) v.get("principle"));
		this.productStructureVO = (ProductStructureVO) v.get("ProductStructureVO");
		this.riderCOI = ((v.get("riderCOI") == null) ? null : Double.valueOf((String) v.get("riderCOI")));
		this.riderSegmentVOs = (SegmentVO[]) v.get("RiderVOs");
		this.segmentHistories = (SegmentHistory[]) v.get("SegmentHistories");
		this.editTrxVOs = (EDITTrxVO[]) v.get("EDITTrxVOs");
		this.mvEditTrxVO = (EDITTrxVO) v.get("mvEditTrxVO");
		this.roleType = ((v.get("roleType") == null) ? null : (String) v.get("roleType"));
		this.segmentVO = (SegmentVO) v.get("SegmentVO");
		this.startDate = ((v.get("startDate") == null) ? null : formatter.parse((String) v.get("startDate")));
		this.subType = ((v.get("subType") == null) ? null : (String) v.get("subType"));
		this.faceIncrease = ((v.get("faceIncrease") == null) ? null : Double.valueOf((String) v.get("faceIncrease")));
		this.sumOfAdminFees = ((v.get("sumOfAdminFees") == null) ? null
				: Double.valueOf((String) v.get("sumOfAdminFees")));
		this.sumOfCOI = ((v.get("sumOfCOI") == null) ? null : Double.valueOf((String) v.get("sumOfCOI")));
		this.transactionType = ((v.get("transactionType") == null) ? null : (String) v.get("transactionType"));
		this.sumOfLoanRepayments = ((v.get("sumOfLoanRepayments") == null) ? null
				: Double.valueOf((String) v.get("sumOfLoanRepayments")));
		this.sumOfLoans = ((v.get("sumOfLoans") == null) ? null : Double.valueOf((String) v.get("sumOfLoans")));
		this.cumLoanDollars = ((v.get("cumLoanDollars") == null) ? null : Double.valueOf((String) v.get("cumLoanDollars")));
		this.sumOfPremiumLoad = ((v.get("sumOfPremiumLoad") == null) ? null
				: Double.valueOf((String) v.get("sumOfPremiumLoad")));

		this.sumOfPremiumRequiredToKeepNoLapseGuaranteeInforce = ((v
				.get("sumOfPremiumRequiredToKeepNoLapseGuaranteeInforce") == null) ? null
						: Double.valueOf((String) v.get("sumOfPremiumRequiredToKeepNoLapseGuaranteeInforce")));
		this.sumOfPremiumsPaid = ((v.get("sumOfPremiumsPaid") == null) ? null
				: Double.valueOf((String) v.get("sumOfPremiumPaid")));
		this.sumOfRiderCOI = ((v.get("sumOfRiderCOI") == null) ? null
				: Double.valueOf((String) v.get("sumOfRiderCOI")));
		this.sumOfWithdrawals = ((v.get("sumOfWithdrawals") == null) ? null
				: Double.valueOf((String) v.get("sumOfWithdrawals")));
		this.surrenderChargeAtEndDate = ((v.get("surrenderChargeAtEndDate") == null) ? null
				: formatter.parse((String) v.get("surrenderChargeAtEndDate")));
		this.tobaccoUse = ((v.get("tobaccoUse") == null) ? null : (String) v.get("tobaccoUse"));
		this.unnecessaryPremiumIndicator = ((v.get("unnecessaryPremiumIndicator") == null) ? null
				: Boolean.valueOf((String) v.get("unnecessaryPremiumIndicator")));

	}

	
	
	
	
	public String getCalcFocusPlanCode() {
		return calcFocusPlanCode;
	}

	public void setCalcFocusPlanCode(String calcFocusPlanCode) {
		this.calcFocusPlanCode = calcFocusPlanCode;
	}

	public EDITTrxVO getLcEditTrxVO() {
		return lcEditTrxVO;
	}

	public void setLcEditTrxVO(EDITTrxVO lcEditTrxVO) {
		this.lcEditTrxVO = lcEditTrxVO;
	}

	public Double getCFTrxRequestAmount() {
		return CFTrxRequestAmount;
	}

	public void setCFTrxRequestAmount(Double cFTrxRequestAmount) {
		CFTrxRequestAmount = cFTrxRequestAmount;
	}

	public Integer getNewRiderUnits() {
		return newRiderUnits;
	}

	public void setNewRiderUnits(Integer newRiderUnits) {
		this.newRiderUnits = newRiderUnits;
	}

	public Double getTotalFaceAmount() {
		return totalFaceAmount;
	}

	public void setTotalFaceAmount(Double totalFaceAmount) {
		this.totalFaceAmount = totalFaceAmount;
	}

	public void setNewFaceAmount(Double newFaceAmount) {
		this.newFaceAmount = newFaceAmount;
	}

	public Double getNewFaceAmount() {
		return newFaceAmount;
	}

	public Double getSpecifiedAmount() {
		return specifiedAmount;
	}

	public void setSpecifiedAmount(Double specifiedAmount) {
		this.specifiedAmount = specifiedAmount;
	}

	public Double getGoalAmount() {
		return goalAmount;
	}

	public void setGoalAmount(Double goalAmount) {
		this.goalAmount = goalAmount;
	}

	public String getScenarioName() {
		return scenarioName;
	}

	public void setScenarioName(String scenarioName) {
		this.scenarioName = scenarioName;
	}

	public String getTransactionRequestType() {
		return transactionRequestType;
	}

	public void setTransactionRequestType(String transactionRequestType) {
		this.transactionRequestType = transactionRequestType;
	}

	public String getStartDateType() {
		return startDateType;
	}

	public void setStartDateType(String startDateType) {
		this.startDateType = startDateType;
	}

	public String getFaceSolveBasedOnPremium() {
		return faceSolveBasedOnPremium;
	}

	public void setFaceSolveBasedOnPremium(String faceSolveBasedOnPremium) {
		this.faceSolveBasedOnPremium = faceSolveBasedOnPremium;
	}

	public String getFaceAmountRule() {
		return faceAmountRule;
	}

	public void setFaceAmountRule(String faceAmountRule) {
		this.faceAmountRule = faceAmountRule;
	}

	public String getFacePremiumSolve() {
		return facePremiumSolve;
	}

	public void setFacePremiumSolve(String facePremiumSolve) {
		this.facePremiumSolve = facePremiumSolve;
	}

	public String getSolveGoalType() {
		return solveGoalType;
	}

	public void setSolveGoalType(String solveGoalType) {
		this.solveGoalType = solveGoalType;
	}

	public String getFacePremiumSolveType() {
		return facePremiumSolveType;
	}

	public void setFacePremiumSolveType(String facePremiumSolveType) {
		this.facePremiumSolveType = facePremiumSolveType;
	}

	public String getGoalAmountExpressedAs() {
		return goalAmountExpressedAs;
	}

	public void setGoalAmountExpressedAs(String goalAmountExpressedAs) {
		this.goalAmountExpressedAs = goalAmountExpressedAs;
	}

	public String getSolveEnd() {
		return solveEnd;
	}

	public void setSolveEnd(String solveEnd) {
		this.solveEnd = solveEnd;
	}

	public String getGoalYear() {
		return goalYear;
	}

	public void setGoalYear(String goalYear) {
		this.goalYear = goalYear;
	}

	public Date getTransactionRequestEffectiveDate() {
		return transactionRequestEffectiveDate;
	}

	public void setTransactionRequestEffectiveDate(Date transactionRequestEffectiveDate) {
		this.transactionRequestEffectiveDate = transactionRequestEffectiveDate;
	}

	public Date getSpecifiedDate() {
		return specifiedDate;
	}

	public void setSpecifiedDate(Date specifiedDate) {
		this.specifiedDate = specifiedDate;
	}

	public Date getTrxEffectiveDate() {
		return trxEffectiveDate;
	}

	public void setTrxEffectiveDate(Date trxEffectiveDate) {
		this.trxEffectiveDate = trxEffectiveDate;
	}

	public Date getMaturityDate() {
		return maturityDate;
	}

	public void setMaturityDate(Date maturityDate) {
		this.maturityDate = maturityDate;
	}

	public Date getGoalEndDate() {
		return goalEndDate;
	}

	public void setGoalEndDate(Date goalEndDate) {
		this.goalEndDate = goalEndDate;
	}

	public Double getAnnualPremium() {
		return annualPremium;
	}

	public void setAnnualPremium(Double annualPremium) {
		this.annualPremium = annualPremium;
	}

	public Double getGoalPercent() {
		return goalPercent;
	}

	public void setGoalPercent(Double goalPercent) {
		this.goalPercent = goalPercent;
	}

	public Integer getSubdays() {
		return subdays;
	}

	public void setSubdays(Integer subdays) {
		this.subdays = subdays;
	}

	public Integer getGoalAge() {
		return goalAge;
	}

	public void setGoalAge(Integer goalAge) {
		this.goalAge = goalAge;
	}

	public AnnualStatementAdminData getAnnualStatementAdminData() {
		return annualStatementAdminData;
	}

	public void setAnnualStatementAdminData(AnnualStatementAdminData annualStatementAdminData) {
		this.annualStatementAdminData = annualStatementAdminData;
	}

	public FinancialHistory getMvFinancialHistory() {
		return mvFinancialHistory;
	}

	public void setMvFinancialHistory(FinancialHistory mvFinancialHistory) {
		this.mvFinancialHistory = mvFinancialHistory;
	}

	public FinancialHistoryVO getLcFinancialHistoryVO() {
		return lcFinancialHistoryVO;
	}

	public void setLcFinancialHistoryVO(FinancialHistoryVO lcFinancialHistoryVO) {
		this.lcFinancialHistoryVO = lcFinancialHistoryVO;
	}


	public FinancialHistoryVO getMvFinancialHistoryVO() {
		return mvFinancialHistoryVO;
	}

	public void setMvFinancialHistoryVO(FinancialHistoryVO mvFinancialHistoryVO) {
		this.mvFinancialHistoryVO = mvFinancialHistoryVO;
	}

	public EDITTrxVO getMvEditTrxVO() {
		return mvEditTrxVO;
	}

	public void setMvEditTrxVO(EDITTrxVO mvEditTrxVO) {
		this.mvEditTrxVO = mvEditTrxVO;
	}

	public String getGroupNumber() {
		return groupNumber;
	}

	public void setGroupNumber(String groupNumber) {
		this.groupNumber = groupNumber;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public EDITTrxVO[] getEditTrxVOs() {
		return editTrxVOs;
	}

	public void setEditTrxVOs(EDITTrxVO[] editTrxVOs) {
		this.editTrxVOs = editTrxVOs;
	}

	public Double getFaceIncrease() {
		return faceIncrease;
	}

	public void setFaceIncrease(Double faceIncrease) {
		this.faceIncrease = faceIncrease;
	}

	public SegmentHistory[] getSegmentHistories() {
		return segmentHistories;
	}

	public void setSegmentHistories(SegmentHistory[] segmentHistories) {
		this.segmentHistories = segmentHistories;
	}

	public FinancialHistory getFinancialHistory() {
		return financialHistory;
	}

	public void setFinancialHistory(FinancialHistory financialHistory) {
		this.financialHistory = financialHistory;
	}

	public Double getCfMapAccum() {
		return cfMapAccum;
	}

	public void setCfMapAccum(Double cfMapAccum) {
		this.cfMapAccum = cfMapAccum;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public String getInsuranceCompany() {
		return insuranceCompany;
	}

	public void setInsuranceCompany(String insuranceCompany) {
		this.insuranceCompany = insuranceCompany;
	}

	public ClientRoleVO[] getClientRoleVOs() {
		return clientRoleVOs;
	}

	public void setClientRoleVOs(ClientRoleVO[] clientRoleVOs) {
		this.clientRoleVOs = clientRoleVOs;
	}

	public Integer getGapInCoverageMonths() {
		return gapInCoverageMonths;
	}

	public void setGapInCoverageMonths(Integer gapInCoverageMonths) {
		this.gapInCoverageMonths = gapInCoverageMonths;
	}

	public Date getProcessDate() {
		return processDate;
	}

	public void setProcessDate(Date processDate) {
		this.processDate = processDate;
	}

	public Long getDrivingSegmentPK() {
		return drivingSegmentPK;
	}

	public void setDrivingSegmentPK(Long drivingSegmentPK) {
		this.drivingSegmentPK = drivingSegmentPK;
	}

	public String getComplexChangeType() {
		return complexChangeType;
	}

	public void setComplexChangeType(String complexChangeType) {
		this.complexChangeType = complexChangeType;
	}

	public EDITTrxVO getEditTrxVO() {
		return editTrxVO;
	}

	public void setEditTrxVO(EDITTrxVO editTrxVO) {
		this.editTrxVO = editTrxVO;
	}

	public SegmentVO getSegmentVO() {
		return segmentVO;
	}

	public void setSegmentVO(SegmentVO segmentVO) {
		this.segmentVO = segmentVO;
	}

	public SegmentVO[] getRiderSegmentVOs() {
		return riderSegmentVOs;
	}

	public void setRiderSegmentVOs(SegmentVO[] riderSegmentVOs) {
		this.riderSegmentVOs = riderSegmentVOs;
	}

	public BillScheduleVO getBillScheduleVO() {
		return billScheduleVO;
	}

	public void setBillScheduleVO(BillScheduleVO billcheduleVO) {
		this.billScheduleVO = billcheduleVO;
	}

	public ClientDetailVO getClientDetailVO() {
		return clientDetailVO;
	}

	public void setClientDetailVO(ClientDetailVO clientDetailVO) {
		this.clientDetailVO = clientDetailVO;
	}

	public ClientRoleVO getClientRoleVO() {
		return clientRoleVO;
	}

	public void setClientRoleVO(ClientRoleVO clientRoleVO) {
		this.clientRoleVO = clientRoleVO;
	}

	public ProductStructureVO getProductStructureVO() {
		return productStructureVO;
	}

	public void setProductStructureVO(ProductStructureVO productStructureVO) {
		this.productStructureVO = productStructureVO;
	}

	public boolean isBaseIndicator() {
		return baseIndicator;
	}

	public void setBaseIndicator(boolean baseIndicator) {
		this.baseIndicator = baseIndicator;
	}

	
	
	public String getMevContractNumber() {
		return mevContractNumber;
	}

	public void setMevContractNumber(String mevContractNumber) {
		this.mevContractNumber = mevContractNumber;
	}

	public String getIsAsync() {
		return isAsync;
	}

	public void setIsAsync(String isAsync) {
		this.isAsync = isAsync;
	}

	public String getIsYev() {
		return isYev;
	}

	public void setIsYev(String isYev) {
		this.isYev = isYev;
	}


	public String getCarrierAdminSystem() {
		return carrierAdminSystem;
	}

	public void setCarrierAdminSystem(String carrierAdminSystem) {
		this.carrierAdminSystem = carrierAdminSystem;
	}

	public boolean isAllowMEC() {
		return allowMEC;
	}

	public void setAllowMEC(boolean allowMEC) {
		this.allowMEC = allowMEC;
	}

	public String getLoanInterestAssumption() {
		return loanInterestAssumption;
	}

	public void setLoanInterestAssumption(String loanInterestAssumption) {
		this.loanInterestAssumption = loanInterestAssumption;
	}

	public Double getLastMonthaversaryCharge() {
		return lastMonthaversaryCharge;
	}

	public void setLastMonthaversaryCharge(Double lastMonthaversaryCharge) {
		this.lastMonthaversaryCharge = lastMonthaversaryCharge;
	}

	public boolean isDboSwitch() {
		return dboSwitch;
	}

	public void setDboSwitch(boolean dboSwitch) {
		this.dboSwitch = dboSwitch;
	}

	public Double getSumOfPremiumPaidTAMRADurationOne() {
		return sumOfPremiumPaidTAMRADurationOne;
	}

	public void setSumOfPremiumPaidTAMRADurationOne(Double sumOfPremiumPaidTAMRADurationOne) {
		this.sumOfPremiumPaidTAMRADurationOne = sumOfPremiumPaidTAMRADurationOne;
	}

	public Double getSumOfPremiumPaidTAMRADurationTwo() {
		return sumOfPremiumPaidTAMRADurationTwo;
	}

	public void setSumOfPremiumPaidTAMRADurationTwo(Double sumOfPremiumPaidTAMRADurationTwo) {
		this.sumOfPremiumPaidTAMRADurationTwo = sumOfPremiumPaidTAMRADurationTwo;
	}

	public Double getSumOfPremiumPaidTAMRADurationThree() {
		return sumOfPremiumPaidTAMRADurationThree;
	}

	public void setSumOfPremiumPaidTAMRADurationThree(Double sumOfPremiumPaidTAMRADurationThree) {
		this.sumOfPremiumPaidTAMRADurationThree = sumOfPremiumPaidTAMRADurationThree;
	}

	public Double getSumOfPremiumPaidTAMRADurationFour() {
		return sumOfPremiumPaidTAMRADurationFour;
	}

	public void setSumOfPremiumPaidTAMRADurationFour(Double sumOfPremiumPaidTAMRADurationFour) {
		this.sumOfPremiumPaidTAMRADurationFour = sumOfPremiumPaidTAMRADurationFour;
	}

	public Double getSumOfPremiumPaidTAMRADurationFive() {
		return sumOfPremiumPaidTAMRADurationFive;
	}

	public void setSumOfPremiumPaidTAMRADurationFive(Double sumOfPremiumPaidTAMRADurationFive) {
		this.sumOfPremiumPaidTAMRADurationFive = sumOfPremiumPaidTAMRADurationFive;
	}

	public Double getSumOfPremiumPaidTAMRADurationSix() {
		return sumOfPremiumPaidTAMRADurationSix;
	}

	public void setSumOfPremiumPaidTAMRADurationSix(Double sumOfPremiumPaidTAMRADurationSix) {
		this.sumOfPremiumPaidTAMRADurationSix = sumOfPremiumPaidTAMRADurationSix;
	}

	public Double getSumOfPremiumPaidTAMRADurationSeven() {
		return sumOfPremiumPaidTAMRADurationSeven;
	}

	public void setSumOfPremiumPaidTAMRADurationSeven(Double sumOfPremiumPaidTAMRADurationSeven) {
		this.sumOfPremiumPaidTAMRADurationSeven = sumOfPremiumPaidTAMRADurationSeven;
	}

	public Double getSumOfPremiumRequiredToKeepNoLapseGuaranteeInforce() {
		return sumOfPremiumRequiredToKeepNoLapseGuaranteeInforce;
	}

	public void setSumOfPremiumRequiredToKeepNoLapseGuaranteeInforce(
			Double sumOfPremiumRequiredToKeepNoLapseGuaranteeInforce) {
		this.sumOfPremiumRequiredToKeepNoLapseGuaranteeInforce = sumOfPremiumRequiredToKeepNoLapseGuaranteeInforce;
	}

	public boolean isUnnecessaryPremiumIndicator() {
		return unnecessaryPremiumIndicator;
	}

	public void setUnnecessaryPremiumIndicator(boolean unnecessaryPremiumIndicator) {
		this.unnecessaryPremiumIndicator = unnecessaryPremiumIndicator;
	}

	public boolean isBenefitIncreaseIndicator() {
		return benefitIncreaseIndicator;
	}

	public void setBenefitIncreaseIndicator(boolean benefitIncreaseIndicator) {
		this.benefitIncreaseIndicator = benefitIncreaseIndicator;
	}

	public String getCFtransactionType() {
		return CFtransactionType;
	}

	public void setCFtransactionType(String cFtransactionType) {
		CFtransactionType = cFtransactionType;
	}

	public String getCFprocessType() {
		return CFprocessType;
	}

	public void setCFprocessType(String cFprocessType) {
		CFprocessType = cFprocessType;
	}

	public String getCFoutputInstruction() {
		return CFoutputInstruction;
	}

	public void setCFoutputInstruction(String cFoutputInstruction) {
		CFoutputInstruction = cFoutputInstruction;
	}

	public String getPolicyStatus() {
		return policyStatus;
	}

	public void setPolicyStatus(String policyStatus) {
		this.policyStatus = policyStatus;
	}

	public String getFundType() {
		return fundType;
	}

	public void setFundType(String fundType) {
		this.fundType = fundType;
	}

	public String getSubType() {
		return subType;
	}

	public void setSubType(String subType) {
		this.subType = subType;
	}

	public Double getPrincipal() {
		return principal;
	}

	public void setPrincipal(Double principal) {
		this.principal = principal;
	}

	public Date getLastPrincipalChangeDate() {
		return lastPrincipalChangeDate;
	}

	public void setLastPrincipalChangeDate(Date lastPrincipalChangeDate) {
		this.lastPrincipalChangeDate = lastPrincipalChangeDate;
	}

	public Double getCurrentYearUncapitalizedInterest() {
		return currentYearUncapitalizedInterest;
	}

	public void setCurrentYearUncapitalizedInterest(Double currentYearUncapitalizedInterest) {
		this.currentYearUncapitalizedInterest = currentYearUncapitalizedInterest;
	}

	public Double getCurrentYearUncapitalizedCredit() {
		return currentYearUncapitalizedCredit;
	}

	public void setCurrentYearUncapitalizedCredit(Double currentYearUncapitalizedCredit) {
		this.currentYearUncapitalizedCredit = currentYearUncapitalizedCredit;
	}

	public Double getFaceAmount() {
		return faceAmount;
	}

	public void setFaceAmount(Double faceAmount) {
		this.faceAmount = faceAmount;
	}

	public String getLegacyPlanCode() {
		return legacyPlanCode;
	}

	public void setLegacyPlanCode(String legacyPlanCode) {
		this.legacyPlanCode = legacyPlanCode;
	}

	public Integer getIssueAge() {
		return issueAge;
	}

	public void setIssueAge(Integer issueAge) {
		this.issueAge = issueAge;
	}

	public String getTobaccoUse() {
		return tobaccoUse;
	}

	public void setTobaccoUse(String tobaccoUse) {
		this.tobaccoUse = tobaccoUse;
	}

	public String getPartyGUID() {
		return partyGUID;
	}

	public void setPartyGUID(String partyGUID) {
		this.partyGUID = partyGUID;
	}

	public String getRoleType() {
		return roleType;
	}

	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}

	public Double getInterestAssumption() {
		return interestAssumption;
	}

	public void setInterestAssumption(Double interestAssumption) {
		this.interestAssumption = interestAssumption;
	}

	public String getMortalityAssumption() {
		return mortalityAssumption;
	}

	public void setMortalityAssumption(String mortalityAssumption) {
		this.mortalityAssumption = mortalityAssumption;
	}

	public String getFinancialEventType() {
		return financialEventType;
	}

	public void setFinancialEventType(String financialEventType) {
		this.financialEventType = financialEventType;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public String getHistoricalIndicator() {
		return historicalIndicator;
	}

	public void setHistoricalIndicator(String historicalIndicator) {
		this.historicalIndicator = historicalIndicator;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Date getPolicyAccumulationValueAtStartDate() {
		return policyAccumulationValueAtStartDate;
	}

	public void setPolicyAccumulationValueAtStartDate(Date policyAccumulationValueAtStartDate) {
		this.policyAccumulationValueAtStartDate = policyAccumulationValueAtStartDate;
	}

	public Date getPolicyLoanedAccountValueAtStartDate() {
		return policyLoanedAccountValueAtStartDate;
	}

	public void setPolicyLoanedAccountValueAtStartDate(Date policyLoanedAccountValueAtStartDate) {
		this.policyLoanedAccountValueAtStartDate = policyLoanedAccountValueAtStartDate;
	}

	public Date getFaceAmountAtStartDate() {
		return faceAmountAtStartDate;
	}

	public void setFaceAmountAtStartDate(Date faceAmountAtStartDate) {
		this.faceAmountAtStartDate = faceAmountAtStartDate;
	}

	public Date getPolicyAccumulationValueAtEndDate() {
		return policyAccumulationValueAtEndDate;
	}

	public void setPolicyAccumulationValueAtEndDate(Date policyAccumulationValueAtEndDate) {
		this.policyAccumulationValueAtEndDate = policyAccumulationValueAtEndDate;
	}

	public Date getPolicyLoanInterestAccruedAtEndDate() {
		return policyLoanInterestAccruedAtEndDate;
	}

	public void setPolicyLoanInterestAccruedAtEndDate(Date policyLoanInterestAccruedAtEndDate) {
		this.policyLoanInterestAccruedAtEndDate = policyLoanInterestAccruedAtEndDate;
	}

	public Date getSurrenderChargeAtEndDate() {
		return surrenderChargeAtEndDate;
	}

	public void setSurrenderChargeAtEndDate(Date surrenderChargeAtEndDate) {
		this.surrenderChargeAtEndDate = surrenderChargeAtEndDate;
	}

	public Date getDeathBenefitAtEndDate() {
		return deathBenefitAtEndDate;
	}

	public void setDeathBenefitAtEndDate(Date deathBenefitAtEndDate) {
		this.deathBenefitAtEndDate = deathBenefitAtEndDate;
	}

	public Date getFaceAmountAtEndDate() {
		return faceAmountAtEndDate;
	}

	public void setFaceAmountAtEndDate(Date faceAmountAtEndDate) {
		this.faceAmountAtEndDate = faceAmountAtEndDate;
	}

	public Double getSumOfPremiumsPaid() {
		return sumOfPremiumsPaid;
	}

	public void setSumOfPremiumsPaid(Double sumOfPremiumsPaid) {
		this.sumOfPremiumsPaid = sumOfPremiumsPaid;
	}

	
	public Double getCumLoanDollars() {
		return cumLoanDollars;
	}

	public void setCumLoanDollars(Double cumLoanDollars) {
		this.cumLoanDollars = cumLoanDollars;
	}

	public Double getSumOfLoans() {
		return sumOfLoans;
	}

	public void setSumOfLoans(Double sumOfLoans) {
		this.sumOfLoans = sumOfLoans;
	}

	public Double getSumOfLoanRepayments() {
		return sumOfLoanRepayments;
	}

	public void setSumOfLoanRepayments(Double sumOfLoanRepayments) {
		this.sumOfLoanRepayments = sumOfLoanRepayments;
	}

	public Double getSumOfWithdrawals() {
		return sumOfWithdrawals;
	}

	public void setSumOfWithdrawals(Double sumOfWithdrawals) {
		this.sumOfWithdrawals = sumOfWithdrawals;
	}

	public Double getSumOfRiderCOI() {
		return sumOfRiderCOI;
	}

	public void setSumOfRiderCOI(Double sumOfRiderCOI) {
		this.sumOfRiderCOI = sumOfRiderCOI;
	}

	public Double getSumOfCOI() {
		return sumOfCOI;
	}

	public void setSumOfCOI(Double sumOfCOI) {
		this.sumOfCOI = sumOfCOI;
	}

	public Double getInterestEarned() {
		return interestEarned;
	}

	public void setInterestEarned(Double interestEarned) {
		this.interestEarned = interestEarned;
	}

	public Double getMortalityChargeAndRiderCOI() {
		return mortalityChargeAndRiderCOI;
	}

	public void setMortalityChargeAndRiderCOI(Double mortalityChargeAndRiderCOI) {
		this.mortalityChargeAndRiderCOI = mortalityChargeAndRiderCOI;
	}

	public Double getBaseCOI() {
		return baseCOI;
	}

	public void setBaseCOI(Double baseCOI) {
		this.baseCOI = baseCOI;
	}

	public Double getRiderCOI() {
		return riderCOI;
	}

	public void setRiderCOI(Double riderCOI) {
		this.riderCOI = riderCOI;
	}

	public Double getSumOfAdminFees() {
		return sumOfAdminFees;
	}

	public void setSumOfAdminFees(Double sumOfAdminFees) {
		this.sumOfAdminFees = sumOfAdminFees;
	}

	public Double getInterestRate() {
		return interestRate;
	}

	public void setInterestRate(Double interestRate) {
		this.interestRate = interestRate;
	}

	public Double getSumOfPremiumLoad() {
		return sumOfPremiumLoad;
	}

	public void setSumOfPremiumLoad(Double sumOfPremiumLoad) {
		this.sumOfPremiumLoad = sumOfPremiumLoad;
	}

	public PremiumDue getPremiumDue() {
		return premiumDue;
	}

	public void setPremiumDue(PremiumDue premiumDue) {
		this.premiumDue = premiumDue;
	}

	public LifeVO getLifeVO() {
		return lifeVO;
	}

	public void setLifeVO(LifeVO lifeVO) {
		this.lifeVO = lifeVO;
	}

	public ContractClientVO[] getContractClientVOs() {
		return contractClientVOs;
	}

	public void setContractClientVOs(ContractClientVO[] contractClientVOs) {
		this.contractClientVOs = contractClientVOs;
	}

}
