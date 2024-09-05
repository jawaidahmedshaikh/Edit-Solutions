package com.selman.calcfocus.correspondence.builder;

/*
 * [CalcFocusPlanCodePK]
      ,[RatedGender]
      ,[UnderwritingClass]
      ,[GroupPlan]
      ,[CalcFocusProductCode]
      ,[Company]
      ,[OptionCodeCT]
      ,[LegacyPlanCode]
      ,[LegacyValuationType]
 */

public class PlanCode {
	Long calcFocusPlanCodePK;
	String ratedGender;
	String underwritingClass;
	String groupPlan;
	String calcFocusProductCode;
	String company;
	String optionCodeCT;
	String legacyPlanCode;
	String legacyValuationType;
	String genderBlend;
	
	public PlanCode() {
		
	}

	public PlanCode(Long calcFocusPlanCodePK, String ratedGender, String underwritingClass, String groupPlan, String calcFocusProductCode,
			String company, String optionCodeCT, String legacyPlanCode, String legacyValuationType, String genderBlend) {
		super();
		this.calcFocusPlanCodePK = calcFocusPlanCodePK;
		this.ratedGender = ratedGender;
		this.groupPlan = groupPlan;
		this.calcFocusProductCode = calcFocusProductCode;
		this.company = company;
		this.optionCodeCT = optionCodeCT;
		this.legacyPlanCode = legacyPlanCode;
		this.underwritingClass = underwritingClass;
		this.legacyValuationType = legacyValuationType;
		this.genderBlend = genderBlend;
	}

	
	
	public String getGenderBlend() {
		return genderBlend;
	}



	public void setGenderBlend(String genderBlend) {
		this.genderBlend = genderBlend;
	}



	public String getUnderwritingClass() {
		return underwritingClass;
	}


	public void setUnderwritingClass(String underwritingClass) {
		this.underwritingClass = underwritingClass;
	}


	public Long getCalcFocusPlanCodePK() {
		return calcFocusPlanCodePK;
	}

	public void setCalcFocusPlanCodePK(Long calcFocusPlanCodePK) {
		this.calcFocusPlanCodePK = calcFocusPlanCodePK;
	}

	public String getRatedGender() {
		return ratedGender;
	}

	public void setRatedGender(String ratedGender) {
		this.ratedGender = ratedGender;
	}

	public String getGroupPlan() {
		return groupPlan;
	}

	public void setGroupPlan(String groupPlan) {
		this.groupPlan = groupPlan;
	}

	public String getCalcFocusProductCode() {
		return calcFocusProductCode;
	}

	public void setCalcFocusProductCode(String calcFocusProductCode) {
		this.calcFocusProductCode = calcFocusProductCode;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getOptionCodeCT() {
		return optionCodeCT;
	}

	public void setOptionCodeCT(String optionCodeCT) {
		this.optionCodeCT = optionCodeCT;
	}

	public String getLegacyPlanCode() {
		return legacyPlanCode;
	}

	public void setLegacyPlanCode(String legacyPlanCode) {
		this.legacyPlanCode = legacyPlanCode;
	}

	public String getLegacyValuationType() {
		return legacyValuationType;
	}

	public void setLegacyValuationType(String legacyValuationType) {
		this.legacyValuationType = legacyValuationType;
	}

}
