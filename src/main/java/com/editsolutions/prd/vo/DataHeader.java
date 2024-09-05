package com.editsolutions.prd.vo;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;


public class DataHeader implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long dataHeaderPK;
	private Long setupFK;
	private Date prdDate;
	private String comments;
	private Date addDate;
	private String addUser;
	private Date modDate;
	private String modUser;
	private String statusCT;
	private List<DataIssue> dataIssues;
	private List<DataIssue> unresolvedDataIssues;
	private List<PayrollDeductionData> payrollDeductionDatas;
	private PRDSettings prdSettings;
	private int prdCount = 0;
	private int zeroCount = 0;
	private int issueCount = 0;
	private Double deductionTotal = 0.0;
	private Boolean testPrd = false;
	private Date transmissionDate;
	
	public static String RELEASED = "R";
	public static String PENDING = "P";
	public static String ON_HOLD = "H";
	public static String DELIVERED = "D";
	public static String HAS_ISSUES = "I";
	public static String NO_CHANGES = "N";
	public static String VOID = "V";

	public DataHeader() {
	}

	
	public Date getTransmissionDate() {
		return transmissionDate;
	}


	public void setTransmissionDate(Date transmissionDate) {
		this.transmissionDate = transmissionDate;
	}


	public String getStatusCT() {
		return statusCT;
	}

	public void setStatusCT(String statusCT) {
		this.statusCT = statusCT;
	}

	public Boolean isTestPrd() {
		return testPrd;
	}

	public void setTestPrd(Boolean testPrd) {
		this.testPrd = testPrd;
	}

	public PRDSettings getPrdSettings() {
		return prdSettings;
	}

	public void setPrdSettings(PRDSettings prdSettings) {
		this.prdSettings = prdSettings;
	}

	public Long getDataHeaderPK() {
		return dataHeaderPK;
	}

	public void setDataHeaderPK(Long dataHeaderPK) {
		this.dataHeaderPK = dataHeaderPK;
	}

	public Long getSetupFK() {
		return setupFK;
	}

	public void setSetupFK(Long setupFK) {
		this.setupFK = setupFK;
	}

	public Date getPrdDate() {
		return prdDate;
	}

	public void setPrdDate(Date prdDate) {
		this.prdDate = prdDate;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
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

	public List<DataIssue> getDataIssues() {
		return dataIssues;
	}

	public void setDataIssues(List<DataIssue> dataIssues) {
		this.dataIssues = dataIssues;
	}
	

	public List<DataIssue> getUnresolvedDataIssues() {
		return unresolvedDataIssues;
	}


	public void setUnresolvedDataIssues(List<DataIssue> unresolvedDataIssues) {
		this.unresolvedDataIssues = unresolvedDataIssues;
	}


	public List<PayrollDeductionData> getPayrollDeductionDatas() {
		return payrollDeductionDatas;
	}

	public void setPayrollDeductionDatas(
			List<PayrollDeductionData> payrollDeductionDatas) {
		this.payrollDeductionDatas = payrollDeductionDatas;
	}

	public int getPrdCount() {
		return prdCount;
	}


	public void setPrdCount(int prdCount) {
		this.prdCount = prdCount;
	}


	public int getZeroCount() {
		return zeroCount;
	}


	public void setZeroCount(int zeroCount) {
		this.zeroCount = zeroCount;
	}


	public int getIssueCount() {
		return issueCount;
	}


	public void setIssueCount(int issueCount) {
		this.issueCount = issueCount;
	}


	public Double getDeductionTotal() {
		return deductionTotal;
	}


	public void setDeductionTotal(Double deductionTotal) {
		this.deductionTotal = deductionTotal;
	}
	
	
	

}
