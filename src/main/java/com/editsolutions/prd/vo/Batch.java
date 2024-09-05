package com.editsolutions.prd.vo;

import java.io.Serializable;
import java.sql.Date;
import java.util.Iterator;
import java.util.List;

public class Batch implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long batchPK;
	private String batchName;
	private String batchDescription;
	private String addUser;
	private Date addDate;
	private String modUser;
	private Date modDate;
	private List<BatchDetail> batchDetails;
	private PRDSettings defaultPRDSettings;

	public Long getBatchPK() {
		return batchPK;
	}

	public void setBatchPK(Long batchPK) {
		this.batchPK = batchPK;
	}

	public String getBatchName() {
		return batchName;
	}

	public void setBatchName(String batchName) {
		this.batchName = batchName;
	}

	public String getBatchDescription() {
		return batchDescription;
	}

	public void setBatchDescription(String batchDescription) {
		this.batchDescription = batchDescription;
	}

	public String getAddUser() {
		return addUser;
	}

	public void setAddUser(String addUser) {
		this.addUser = addUser;
	}

	public Date getAddDate() {
		return addDate;
	}

	public void setAddDate(Date addDate) {
		this.addDate = addDate;
	}

	public String getModUser() {
		return modUser;
	}

	public void setModUser(String modUser) {
		this.modUser = modUser;
	}

	public Date getModDate() {
		return modDate;
	}

	public void setModDate(Date modDate) {
		this.modDate = modDate;
	}

	public List<BatchDetail> getBatchDetails() {
		return batchDetails;
	}

	public void setBatchDetails(List<BatchDetail> batchDetails) {
		this.batchDetails = batchDetails;
	}
	
	public PRDSettings getDefaultPRDSettings() {
		Iterator<BatchDetail> it = batchDetails.iterator();
		while (it.hasNext()) {
			BatchDetail bd = it.next();
			if (bd.isDefaultGroup()) {
				defaultPRDSettings = bd.getPrdSettings();
				return defaultPRDSettings;
			}
		}
		return null;
	}

	
}
