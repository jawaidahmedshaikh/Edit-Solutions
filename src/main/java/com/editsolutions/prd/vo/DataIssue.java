package com.editsolutions.prd.vo;

import java.io.Serializable;
import java.sql.Date;

public class DataIssue implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long dataIssuePK;
	private Long dataHeaderFK;
	private Long dataFK;
	private String issueLookupCT;
	private boolean isResolved;
	private Date addDate;
	private String addUser;
	private Date modDate;
	private String modUser;
	private PayrollDeductionData payrollDeductionData;
	public static final String CHANGE = "C";
	public static final String TERMINATED = "T";
	public static final String TRUSTMARK_TERMINATED = "D";
	public static final String NEW = "N";
	public static final String TRUSTMARK_NEW = "A";
	public static final String UNCHANGED = "U";
	public static final String ERROR = "E";
	

	public DataIssue() {
	}

	public Long getDataIssuePK() {
		return dataIssuePK;
	}

	public void setDataIssuePK(Long dataIssuePK) {
		this.dataIssuePK = dataIssuePK;
	}

	public Long getDataFK() {
		return dataFK;
	}

	public void setDataFK(Long dataFK) {
		this.dataFK = dataFK;
	}

	public boolean getIsResolved() {
		return isResolved;
	}

	public void setIsResolved(boolean isResolved) {
		this.isResolved = isResolved;
	}

	public String getIssueLookupCT() {
		return issueLookupCT;
	}

	public void setIssueLookupCT(String issueLookupCT) {
		this.issueLookupCT = issueLookupCT;
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

	public PayrollDeductionData getPayrollDeductionData() {
		return payrollDeductionData;
	}

	public void setPayrollDeductionData(PayrollDeductionData payrollDeductionData) {
		this.payrollDeductionData = payrollDeductionData;
	}

	public Long getDataHeaderFK() {
		return dataHeaderFK;
	}

	public void setDataHeaderFK(Long dataHeaderFK) {
		this.dataHeaderFK = dataHeaderFK;
	}
	
	

	
	

}
