package com.selman.calcfocus.factory;

import java.sql.SQLException;
import java.text.ParseException;

import javax.xml.datatype.DatatypeConfigurationException;

import com.selman.calcfocus.util.BadDataException;

import edit.common.vo.SegmentVO;

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
	
	public void setHeader();
	public void setContext() throws DatatypeConfigurationException;
	public void setPolicy() throws BadDataException, ParseException, DatatypeConfigurationException, SQLException;
	public void setParty();
	public void setAddress();
	public void setAnnualStatementAdminData();
	public void setLifetimeFinancialAdviceProjection();
}