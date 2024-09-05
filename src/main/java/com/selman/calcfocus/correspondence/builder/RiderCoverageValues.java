package com.selman.calcfocus.correspondence.builder;

import java.util.Date;
import java.util.List;

public class RiderCoverageValues implements CoverageValues { 

	boolean baseIndicator;

	// Fields with default values when not in working storage
	Date effectiveDate;
	Long segmentRiderPK;
	Long segmentBaseFK;
	String issueState;
	Integer ageAtIssue;
	Double faceAmount;
	Double annualPremium;
	String underwritingClass;
	String tobaccoUse;
	String legacyPlanCode;
	
	PartyAddressValues partyAddressValues;

	List<RoleValues> roleValues;

	public RiderCoverageValues() {
	}
	
	

	public Long getSegmentRiderPK() {
		return segmentRiderPK;
	}



	public void setSegmentRiderPK(Long segmentRiderPK) {
		this.segmentRiderPK = segmentRiderPK;
	}



	public Long getSegmentBaseFK() {
		return segmentBaseFK;
	}



	public void setSegmentBaseFK(Long segmentBaseFK) {
		this.segmentBaseFK = segmentBaseFK;
	}


	

	public Double getAnnualPremium() {
		return annualPremium;
	}



	public void setAnnualPremium(Double annualPremium) {
		this.annualPremium = annualPremium;
	}



	public String getLegacyPlanCode() {
		return legacyPlanCode;
	}

	public void setLegacyPlanCode(String legacyPlanCode) {
		this.legacyPlanCode = legacyPlanCode;
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

	public List<RoleValues> getRoleValues() {
		return roleValues;
	}

	@Override
	public void setRoleValues(List<RoleValues> roleValues) {
		this.roleValues = roleValues;
	}


	public String getUnderwritingClass() {
		return underwritingClass;
	}

	public void setUnderwritingClass(String underwritingClass) {
		this.underwritingClass = underwritingClass;
	}

	public Integer getAgeAtIssue() {
		return ageAtIssue;
	}

	public void setAgeAtIssue(Integer ageAtIssue) {
		this.ageAtIssue = ageAtIssue;
	}

	public String getIssueState() {
		return issueState;
	}

	public void setIssueState(String issueState) {
		this.issueState = issueState;
	}

	public Date getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}




}
