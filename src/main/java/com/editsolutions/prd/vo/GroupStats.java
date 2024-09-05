package com.editsolutions.prd.vo;

import java.io.Serializable;
import java.sql.Date;

public 	class GroupStats implements Serializable {
	private static final long serialVersionUID = 1L;
	Date prdDate;
	int issueCount;
	int allCount;
	String issuePercent;
	Long GroupContractPk;
	String caseNumber;
	String groupNumber;
	String groupName;

	public Date getPrdDate() {
		return prdDate;
	}
	public void setPrdDate(Date prdDate) {
		this.prdDate = prdDate;
	}
	public int getIssueCount() {
		return issueCount;
	}
	public void setIssueCount(int issueCount) {
		this.issueCount = issueCount;
	}
	public int getAllCount() {
		return allCount;
	}
	public void setAllCount(int allCount) {
		this.allCount = allCount;
	}
	public String getIssuePercent() {
		return issuePercent;
	}
	public void setIssuePercent(String issuePercent) {
		this.issuePercent = issuePercent;
	}
	public Long getGroupContractPk() {
		return GroupContractPk;
	}
	public void setGroupContractPk(Long groupContractPk) {
		GroupContractPk = groupContractPk;
	}
	public String getCaseNumber() {
		return caseNumber;
	}
	public void setCaseNumber(String caseNumber) {
		this.caseNumber = caseNumber;
	}
	public String getGroupNumber() {
		return groupNumber;
	}
	public void setGroupNumber(String groupNumber) {
		this.groupNumber = groupNumber;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	
	
	
	
}

