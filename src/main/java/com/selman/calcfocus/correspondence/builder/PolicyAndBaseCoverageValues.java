package com.selman.calcfocus.correspondence.builder;

import java.util.Date;
import java.util.List;

public class PolicyAndBaseCoverageValues implements CoverageValues {

	boolean baseIndicator;

	// Fields with default values when not in working storage
	String contractNumber;
	Date effectiveDate;
	String segmentStatus;
	Long segmentBasePK;
	String companyName;
	String issueState;
	String masterContractNumber;
	Date masterContractEffectiveDate;
	String ratedGenderCT;
	String optionCodeCT;
	Integer ageAtIssue;
	String deathBenefitOption;
	String groupNumber;
	String corporateName;
	String billingMode;
	String billType;
	String deductionFrequency;
	Double units;
	Double originalUnits;
	Double faceAmount;
	Double billAmount;
	Double deductionAmount;
	Date premiumDueEffectiveDate;
	String groupPlan;
	String underwritingClass;
	String tobaccoUse;
	String legacyPlanCode;
	String productCode;
	String planCode;
	String reportType;
	String reportName;
	Boolean resultsWithReport;
	
	PartyAddressValues partyAddressValues;

	List<RoleValues> roleValues;
	List<RiderCoverageValues> riderCoverageValues;

	String CFprocessType;
	String CFoutputInstruction;

	public PolicyAndBaseCoverageValues() {
	}
	
	
	

	public String getOptionCodeCT() {
		return optionCodeCT;
	}




	public void setOptionCodeCT(String optionCodeCT) {
		this.optionCodeCT = optionCodeCT;
	}




	public List<RiderCoverageValues> getRiderCoverageValues() {
		return riderCoverageValues;
	}



	public void setRiderCoverageValues(List<RiderCoverageValues> riderCoverageValues) {
		this.riderCoverageValues = riderCoverageValues;
	}



	public String getProductCode() {
		return productCode;
	}



	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}



	public String getReportName() {
		return reportName;
	}



	public void setReportName(String reportName) {
		this.reportName = reportName;
	}



	public String getReportType() {
		return reportType;
	}



	public void setReportType(String reportType) {
		this.reportType = reportType;
	}



	public Boolean getResultsWithReport() {
		return resultsWithReport;
	}



	public void setResultsWithReport(Boolean resultsWithReport) {
		this.resultsWithReport = resultsWithReport;
	}



	public String getLegacyPlanCode() {
		return legacyPlanCode;
	}



	public void setLegacyPlanCode(String legacyPlanCode) {
		this.legacyPlanCode = legacyPlanCode;
	}



	public String getPlanCode() {
		return planCode;
	}



	public void setPlanCode(String planCode) {
		this.planCode = planCode;
	}



	public PartyAddressValues getPartyAddressValues() {
		return partyAddressValues;
	}



	public void setPartyAddressValues(PartyAddressValues partyAddressValues) {
		this.partyAddressValues = partyAddressValues;
	}



	public String getTobaccoUse() {
		return tobaccoUse;
	}

	public void setTobaccoUse(String tobaccoUse) {
		this.tobaccoUse = tobaccoUse;
	}

	public Double getFaceAmount() {
		return faceAmount;
	}



	public void setFaceAmount(Double faceAmount) {
		this.faceAmount = faceAmount;
	}



	public boolean isBaseIndicator() {
		return baseIndicator;
	}



	public void setBaseIndicator(boolean baseIndicator) {
		this.baseIndicator = baseIndicator;
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



	public List<RoleValues> getRoleValues() {
		return roleValues;
	}

	public void setRoleValues(List<RoleValues> roleValues) {
		this.roleValues = roleValues;
	}

	public String getGroupPlan() {
		return groupPlan;
	}

	public void setGroupPlan(String groupPlan) {
		this.groupPlan = groupPlan;
	}

	public String getUnderwritingClass() {
		return underwritingClass;
	}

	public void setUnderwritingClass(String underwritingClass) {
		this.underwritingClass = underwritingClass;
	}

	public Double getBillAmount() {
		return billAmount;
	}

	public void setBillAmount(Double billAmount) {
		this.billAmount = billAmount;
	}

	public Double getDeductionAmount() {
		return deductionAmount;
	}

	public void setDeductionAmount(Double deductionAmount) {
		this.deductionAmount = deductionAmount;
	}

	public Date getPremiumDueEffectiveDate() {
		return premiumDueEffectiveDate;
	}

	public void setPremiumDueEffectiveDate(Date premiumDueEffectiveDate) {
		this.premiumDueEffectiveDate = premiumDueEffectiveDate;
	}

	public Double getUnits() {
		return units;
	}

	public void setUnits(Double units) {
		this.units = units;
	}

	public Double getOriginalUnits() {
		return originalUnits;
	}

	public void setOriginalUnits(Double originalUnits) {
		this.originalUnits = originalUnits;
	}

	public String getBillingMode() {
		return billingMode;
	}

	public void setBillingMode(String billingMode) {
		this.billingMode = billingMode;
	}

	public String getBillType() {
		return billType;
	}

	public void setBillType(String billType) {
		this.billType = billType;
	}

	public String getDeductionFrequency() {
		return deductionFrequency;
	}

	public void setDeductionFrequency(String deductionFrequency) {
		this.deductionFrequency = deductionFrequency;
	}

	public String getDeathBenefitOption() {
		return deathBenefitOption;
	}

	public void setDeathBenefitOption(String deathBenefitOption) {
		this.deathBenefitOption = deathBenefitOption;
	}

	public String getGroupNumber() {
		return groupNumber;
	}

	public void setGroupNumber(String groupNumber) {
		this.groupNumber = groupNumber;
	}

	public String getCorporateName() {
		return corporateName;
	}

	public void setCorporateName(String corporateName) {
		this.corporateName = corporateName;
	}

	public Integer getAgeAtIssue() {
		return ageAtIssue;
	}

	public void setAgeAtIssue(Integer ageAtIssue) {
		this.ageAtIssue = ageAtIssue;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getIssueState() {
		return issueState;
	}

	public void setIssueState(String issueState) {
		this.issueState = issueState;
	}

	public String getMasterContractNumber() {
		return masterContractNumber;
	}

	public void setMasterContractNumber(String masterContractNumber) {
		this.masterContractNumber = masterContractNumber;
	}

	public Date getMasterContractEffectiveDate() {
		return masterContractEffectiveDate;
	}

	public void setMasterContractEffectiveDate(Date masterContractEffectiveDate) {
		this.masterContractEffectiveDate = masterContractEffectiveDate;
	}

	public String getRatedGenderCT() {
		return ratedGenderCT;
	}

	public void setRatedGenderCT(String ratedGenderCT) {
		this.ratedGenderCT = ratedGenderCT;
	}

	public Long getSegmentBasePK() {
		return segmentBasePK;
	}

	public void setSegmentBasePK(Long segmentBasePK) {
		this.segmentBasePK = segmentBasePK;
	}

	public String getSegmentStatus() {
		return segmentStatus;
	}

	public void setSegmentStatus(String segmentStatus) {
		this.segmentStatus = segmentStatus;
	}

	public Date getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public String getContractNumber() {
		return contractNumber;
	}

	public void setContractNumber(String contractNumber) {
		this.contractNumber = contractNumber;
	}

}
