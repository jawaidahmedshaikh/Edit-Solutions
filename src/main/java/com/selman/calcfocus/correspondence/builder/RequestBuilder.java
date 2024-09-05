package com.selman.calcfocus.correspondence.builder;

import javax.xml.datatype.DatatypeConfigurationException;

public interface RequestBuilder {

	/*
    "header",
    "context",
    "policy",
    "party",
    "address",
    "annualStatementAdminData",
    "lifetimeFinancialAdviceProjection"
    */
	
	void setHeader(PolicyAndBaseCoverageValues values);
	void setContext(PolicyAndBaseCoverageValues values) throws DatatypeConfigurationException ;
	void setPolicy(PolicyAndBaseCoverageValues values) throws DatatypeConfigurationException ;
	public void setParty(CoverageValues values) throws DatatypeConfigurationException;
	public void setAddress(CoverageValues values);
	public void setAnnualStatementAdminData();
	public void setLifetimeFinancialAdviceProjection();
}