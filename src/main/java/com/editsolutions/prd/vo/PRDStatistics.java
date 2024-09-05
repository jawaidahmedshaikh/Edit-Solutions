package com.editsolutions.prd.vo;

import java.sql.Timestamp;
import java.sql.Date;


public class PRDStatistics {
	private Long prdStatsPK;
	private Date prdDate;
	private int total;
	private int standardCount;
	private int customCount;
	private int onHoldCount;
	private int issuesCount;
	private int totalGroups;
	private int activeGroups;
	private Double prdSumTotal;
	private int prdCount;
	private int changeCount;
	private int newCount;
	private int terminatedCount;
	private Timestamp asOf;
	
	
	public Long getPrdStatsPK() {
		return prdStatsPK;
	}

	public void setPrdStatsPK(Long prdStatsPK) {
		this.prdStatsPK = prdStatsPK;
	}

	public Timestamp getAsOf() {
		return asOf;
	}

	public void setAsOf(Timestamp asOf) {
		this.asOf = asOf;
	}

	public Date getPrdDate() {
		return prdDate;
	}

	public void setPrdDate(Date prdDate) {
		this.prdDate = prdDate;
	}

	public int getChangeCount() {
		return changeCount;
	}

	public void setChangeCount(int changeCount) {
		this.changeCount = changeCount;
	}

	public int getNewCount() {
		return newCount;
	}

	public void setNewCount(int newCount) {
		this.newCount = newCount;
	}

	public int getTerminatedCount() {
		return terminatedCount;
	}

	public void setTerminatedCount(int terminatedCount) {
		this.terminatedCount = terminatedCount;
	}

	public Double getPrdSumTotal() {
		return prdSumTotal;
	}

	public void setPrdSumTotal(Double prdSumTotal) {
		this.prdSumTotal = prdSumTotal;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}
	

	public int getPrdCount() {
		return prdCount;
	}

	public void setPrdCount(int prdCount) {
		this.prdCount = prdCount;
	}

	public int getStandardCount() {
		return standardCount;
	}

	public void setStandardCount(int standardCount) {
		this.standardCount = standardCount;
	}

	public int getCustomCount() {
		return customCount;
	}

	public void setCustomCount(int customCount) {
		this.customCount = customCount;
	}

	public int getOnHoldCount() {
		return onHoldCount;
	}

	public void setOnHoldCount(int onHoldCount) {
		this.onHoldCount = onHoldCount;
	}

	public int getIssuesCount() {
		return issuesCount;
	}

	public void setIssuesCount(int issuesCount) {
		this.issuesCount = issuesCount;
	}

	public int getTotalGroups() {
		return totalGroups;
	}

	public void setTotalGroups(int totalGroups) {
		this.totalGroups = totalGroups;
	}

	public int getActiveGroups() {
		return activeGroups;
	}

	public void setActiveGroups(int activeGroups) {
		this.activeGroups = activeGroups;
	}

}
