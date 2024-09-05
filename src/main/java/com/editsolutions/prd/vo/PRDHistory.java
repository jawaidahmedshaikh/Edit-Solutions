package com.editsolutions.prd.vo;

import java.io.Serializable;
import java.sql.Date;

public class PRDHistory implements Serializable {

	private static final long serialVersionUID = 1L;
	private long prdSettingsFK;
	private Long dataHeaderFK;
	private Date prdDate;
	private Long recordFK;
	private String account;
	private String eid;
	private String name;
	private String deduction;
	private String date;
	private String productType; 

	public PRDHistory() {
	}
/*
	public long getPrdSettingsFK() {
		return prdSettingsFK;
	}

	public void setPrdSettingsFK(long prdSettingsFK) {
		this.prdSettingsFK = prdSettingsFK;
	}

	public Long getDataHeaderFK() {
		return dataHeaderFK;
	}

	public void setDataHeaderFK(Long dataHeaderFK) {
		this.dataHeaderFK = dataHeaderFK;
	}

	public Date getPrdDate() {
		return prdDate;
	}

	public void setPrdDate(Date prdDate) {
		this.prdDate = prdDate;
	}

	public Long getRecordFK() {
		return recordFK;
	}

	public void setRecordFK(Long recordFK) {
		this.recordFK = recordFK;
	}

*/
	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getEid() {
		return eid;
	}

	public void setEid(String eid) {
		this.eid = eid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDeduction() {
		return deduction;
	}

	public void setDeduction(String deduction) {
		this.deduction = deduction;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	

}
