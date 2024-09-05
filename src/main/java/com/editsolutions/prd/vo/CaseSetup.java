package com.editsolutions.prd.vo;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;


public class CaseSetup implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Long caseContractPk;
	String caseNumber;
    Date effectiveDate;	
    Date terminationDate;
    String requirementNotifyDayCT;
    String caseTypeCT;
    String domicileStateCT;
    String groupTrustStateCT;
    Long clientRolePK;
    String referenceID;
    Long clientDetailPK;
    String caseName;
    String addressLine1;
    String addressLine2;
    String addressLine3;
    String addressLine4;
    String city;
    String stateCT;
    String zipCode;
    Date changeEffectiveDate;
    String systemCT;
    Date addDate;
    String addUser;
    Date modDate;
    String modUser;
    List<GroupSetup> groupSetups = new ArrayList<>();
    
    
    public CaseSetup() {
    	
    }
    
	public List<GroupSetup> getGroupSetups() {
		return groupSetups;
	}

	public void setGroupSetups(List<GroupSetup> groupSetups) {
		this.groupSetups = groupSetups;
	}

	public Long getCaseContractPk() {
		return caseContractPk;
	}
	
	public void setCaseContractPk(Long caseContractPk) {
	    this.caseContractPk = caseContractPk;
	}

	public String getCaseNumber() {
		return caseNumber;
	}

	public void setCaseNumber(String caseNumber) {
		this.caseNumber = caseNumber;
	}

	public Date getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public Date getTerminationDate() {
		return terminationDate;
	}

	public void setTerminationDate(Date terminationDate) {
		this.terminationDate = terminationDate;
	}

	public String getRequirementNotifyDayCT() {
		return requirementNotifyDayCT;
	}

	public void setRequirementNotifyDayCT(String requirementNotifyDayCT) {
		this.requirementNotifyDayCT = requirementNotifyDayCT;
	}

	public String getCaseTypeCT() {
		return caseTypeCT;
	}

	public void setCaseTypeCT(String caseTypeCT) {
		this.caseTypeCT = caseTypeCT;
	}

	public String getDomicileStateCT() {
		return domicileStateCT;
	}

	public void setDomicileStateCT(String domicileStateCT) {
		this.domicileStateCT = domicileStateCT;
	}

	public String getGroupTrustStateCT() {
		return groupTrustStateCT;
	}

	public void setGroupTrustStateCT(String groupTrustStateCT) {
		this.groupTrustStateCT = groupTrustStateCT;
	}

	public Long getClientRolePK() {
		return clientRolePK;
	}

	public void setClientRolePK(Long clientRolePK) {
		this.clientRolePK = clientRolePK;
	}

	public String getReferenceID() {
		return referenceID;
	}

	public void setReferenceID(String referenceID) {
		this.referenceID = referenceID;
	}

	public Long getClientDetailPK() {
		return clientDetailPK;
	}

	public void setClientDetailPK(Long clientDetailPK) {
		    this.clientDetailPK = clientDetailPK;
	}

	public String getCaseName() {
		return caseName;
	}

	public void setCaseName(String caseName) {
		this.caseName = caseName;
	}

	public String getAddressLine1() {
		return addressLine1;
	}

	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}

	public String getAddressLine2() {
		return addressLine2;
	}

	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}

	public String getAddressLine3() {
		return addressLine3;
	}

	public void setAddressLine3(String addressLine3) {
		this.addressLine3 = addressLine3;
	}

	public String getAddressLine4() {
		return addressLine4;
	}

	public void setAddressLine4(String addressLine4) {
		this.addressLine4 = addressLine4;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getStateCT() {
		return stateCT;
	}

	public void setStateCT(String stateCT) {
		this.stateCT = stateCT;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public Date getChangeEffectiveDate() {
		return changeEffectiveDate;
	}

	public void setChangeEffectiveDate(Date changeEffectiveDate) {
		this.changeEffectiveDate = changeEffectiveDate;
	}

	public String getSystemCT() {
		return systemCT;
	}

	public void setSystemCT(String systemCT) {
		this.systemCT = systemCT;
	}

	public Date getAddDate() {
		return addDate;
	}

	public void setAddDate(Date addDate) {
		this.addDate = addDate;
	}

	public String getAddUser() {
		return addUser;
	}

	public void setAddUser(String addUser) {
		this.addUser = addUser;
	}

	public Date getModDate() {
		return modDate;
	}

	public void setModDate(Date modDate) {
		this.modDate = modDate;
	}

	public String getModUser() {
		return modUser;
	}

	public void setModUser(String modUser) {
		this.modUser = modUser;
	}

}
