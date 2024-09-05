package com.editsolutions.prd.vo;

import java.io.Serializable;
import java.util.Date;


public class PolicyHolder implements Serializable {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PolicyHolder() {
		// TODO Auto-generated constructor stub
	}

	private Long policyHolderID;
	private String companyName;
	private String groupNumber;
	private String agency;
	private String department;
	private String insuredFirstName;
	private String insuredLastName;
	private String insuredMiddleName;
	private String relation;
	private String ownerFirstName;
	private String ownerLastName;
	private String ownerMiddleName;
	private String ownerSSN;
	private String policyStatus;
	private String policyNumber;
	private String billNode;
	private String productCode;
	private String billMode;
	private Date issueDate;
	private Integer issueAge;
	private String smoker;
	private Double currentFaceAmount;
	private Double surrenderValue;
	private String issueState;
	private Double modePremium;
	private boolean wop;
	private boolean adb;
	private boolean ltc;
	private boolean ltx;
	private Date terminationDate;
	private String residentState;
	private String entryCode;
	private String exitCode;
	private String employeeId;
	private String employerName;
	private Date paidToDate;
	private Double annualPremium;
	private String status;
	private Double depositTrailerInfo;
	private boolean ltr;
	private boolean pwop;
	private boolean gio;
	private String address;
	private String address2;
	private String city;
	private String Zip;
	private String dob;
	private Float cTRUnits;
	private String product;
	private Date effectiveDate;
	private Date applicationDate;
	private Date LastTransactionDate;
	private int annualDeductions;
	private Double deductionAmount;
	private Date importDate;
	private String ratedGender;
	private String sourceSystem;
	private Date coverageStatusDate;


	public Long getPolicyHolderID() {
		return policyHolderID;
	}

	public void setPolicyHolderID(Long policyHolderID) {
		this.policyHolderID = policyHolderID;
	}
	
	

	public String getPolicyNumber() {
		return policyNumber;
	}

	public void setPolicyNumber(String policyNumber) {
		this.policyNumber = policyNumber;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	

	public String getBillMode() {
		return billMode;
	}

	public void setBillMode(String billMode) {
		this.billMode = billMode;
	}

	public String getEmployerName() {
		return employerName;
	}

	public void setEmployerName(String employerName) {
		this.employerName = employerName;
	}

	public String getGroupNumber() {
		return groupNumber;
	}

	public void setGroupNumber(String groupNumber) {
		this.groupNumber = groupNumber;
	}

	public String getAgency() {
		return agency;
	}

	public void setAgency(String agency) {
		this.agency = agency;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getInsuredFirstName() {
		return insuredFirstName;
	}

	public void setInsuredFirstName(String insuredFirstName) {
		this.insuredFirstName = insuredFirstName;
	}

	public String getInsuredLastName() {
		return insuredLastName;
	}

	public void setInsuredLastName(String insuredLastName) {
		this.insuredLastName = insuredLastName;
	}

	public String getInsuredMiddleName() {
		return insuredMiddleName;
	}

	public void setInsuredMiddleName(String insuredMiddleName) {
		this.insuredMiddleName = insuredMiddleName;
	}

	public String getRelation() {
		return relation;
	}

	public void setRelation(String relation) {
		this.relation = relation;
	}

	public String getOwnerFirstName() {
		return ownerFirstName;
	}

	public void setOwnerFirstName(String ownerFirstName) {
		this.ownerFirstName = ownerFirstName;
	}

	public String getOwnerLastName() {
		return ownerLastName;
	}

	public void setOwnerLastName(String ownerLastName) {
		this.ownerLastName = ownerLastName;
	}

	public String getOwnerMiddleName() {
		return ownerMiddleName;
	}

	public void setOwnerMiddleName(String ownerMiddleName) {
		this.ownerMiddleName = ownerMiddleName;
	}

	public String getOwnerSSN() {
		return ownerSSN;
	}

	public void setOwnerSSN(String ownerSSN) {
		this.ownerSSN = ownerSSN;
	}

	public String getPolicyStatus() {
		return policyStatus;
	}

	public void setPolicyStatus(String policyStatus) {
		this.policyStatus = policyStatus;
	}

	public String getBillNode() {
		return billNode;
	}

	public void setBillNode(String billNode) {
		this.billNode = billNode;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public Date getIssueDate() {
		return issueDate;
	}

	public void setIssueDate(Date issueDate) {
		this.issueDate = issueDate;
	}

	public Integer getIssueAge() {
		return issueAge;
	}

	public void setIssueAge(Integer issueAge) {
		this.issueAge = issueAge;
	}

	public String getSmoker() {
		return smoker;
	}

	public void setSmoker(String smoker) {
		this.smoker = smoker;
	}

	public Double getCurrentFaceAmount() {
		return currentFaceAmount;
	}

	public void setCurrentFaceAmount(Double currentFaceAmount) {
		this.currentFaceAmount = currentFaceAmount;
	}

	public Double getSurrenderValue() {
		return surrenderValue;
	}

	public void setSurrenderValue(Double surrenderValue) {
		this.surrenderValue = surrenderValue;
	}

	public String getIssueState() {
		return issueState;
	}

	public void setIssueState(String issueState) {
		this.issueState = issueState;
	}

	public Double getModePremium() {
		return modePremium;
	}

	public void setModePremium(Double modePremium) {
		this.modePremium = modePremium;
	}

	public boolean isWop() {
		return wop;
	}

	public void setWop(boolean wop) {
		this.wop = wop;
	}

	public boolean isAdb() {
		return adb;
	}

	public void setAdb(boolean adb) {
		this.adb = adb;
	}

	public boolean isLtc() {
		return ltc;
	}

	public void setLtc(boolean ltc) {
		this.ltc = ltc;
	}

	public boolean isLtx() {
		return ltx;
	}

	public void setLtx(boolean ltx) {
		this.ltx = ltx;
	}

	public Date getTerminationDate() {
		return terminationDate;
	}

	public void setTerminationDate(Date terminationDate) {
		this.terminationDate = terminationDate;
	}

	public String getResidentState() {
		return residentState;
	}

	public void setResidentState(String residentState) {
		this.residentState = residentState;
	}

	public String getEntryCode() {
		return entryCode;
	}

	public void setEntryCode(String entryCode) {
		this.entryCode = entryCode;
	}

	public String getExitCode() {
		return exitCode;
	}

	public void setExitCode(String exitCode) {
		this.exitCode = exitCode;
	}

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public Date getPaidToDate() {
		return paidToDate;
	}

	public void setPaidToDate(Date paidToDate) {
		this.paidToDate = paidToDate;
	}

	public Double getAnnualPremium() {
		return annualPremium;
	}

	public void setAnnualPremium(Double annualPremium) {
		this.annualPremium = annualPremium;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Double getDepositTrailerInfo() {
		return depositTrailerInfo;
	}

	public void setDepositTrailerInfo(Double depositTrailerInfo) {
		this.depositTrailerInfo = depositTrailerInfo;
	}

	public boolean isLtr() {
		return ltr;
	}

	public void setLtr(boolean ltr) {
		this.ltr = ltr;
	}

	public boolean isPwop() {
		return pwop;
	}

	public void setPwop(boolean pwop) {
		this.pwop = pwop;
	}

	public boolean isGio() {
		return gio;
	}

	public void setGio(boolean gio) {
		this.gio = gio;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getZip() {
		return Zip;
	}

	public void setZip(String zip) {
		Zip = zip;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public Float getcTRUnits() {
		return cTRUnits;
	}

	public void setcTRUnits(Float cTRUnits) {
		this.cTRUnits = cTRUnits;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public Date getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public Date getApplicationDate() {
		return applicationDate;
	}

	public void setApplicationDate(Date applicationDate) {
		this.applicationDate = applicationDate;
	}

	public Date getLastTransactionDate() {
		return LastTransactionDate;
	}

	public void setLastTransactionDate(Date lastTransactionDate) {
		LastTransactionDate = lastTransactionDate;
	}

	public int getAnnualDeductions() {
		return annualDeductions;
	}

	public void setAnnualDeductions(int annualDeductions) {
		this.annualDeductions = annualDeductions;
	}

	public Double getDeductionAmount() {
		return deductionAmount;
	}

	public void setDeductionAmount(Double deductionAmount) {
		this.deductionAmount = deductionAmount;
	}

	public Date getImportDate() {
		return importDate;
	}

	public void setImportDate(Date importDate) {
		this.importDate = importDate;
	}

	public String getRatedGender() {
		return ratedGender;
	}

	public void setRatedGender(String ratedGender) {
		this.ratedGender = ratedGender;
	}

	public String getSourceSystem() {
		return sourceSystem;
	}

	public void setSourceSystem(String sourceSystem) {
		this.sourceSystem = sourceSystem;
	}

	public Date getCoverageStatusDate() {
		return coverageStatusDate;
	}

	public void setCoverageStatusDate(Date coverageStatusDate) {
		this.coverageStatusDate = coverageStatusDate;
	}

}
