package com.editsolutions.prd.vo;

import java.io.Serializable;
import java.sql.Date;

import electric.util.holder.booleanInOut;
import electric.util.holder.byteInOut;
import electric.util.holder.longInOut;

public class BatchDetail implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long batchDetailPK;
	private Long batchFK;
	private Long prdSetupFK;
	private Long groupFK;
	private Long caseFK;
	private String addUser;
	private Date addDate;
	private String modUser;
	private Date modDate;
	private boolean defaultGroup;
	private Batch batch;
	private PRDSettings prdSettings;

	
	
	public PRDSettings getPrdSettings() {
		return prdSettings;
	}

	public void setPrdSettings(PRDSettings prdSettings) {
		this.prdSettings = prdSettings;
	}

	public boolean isDefaultGroup() {
		return defaultGroup;
	}

	public void setDefaultGroup(boolean defaultGroup) {
		this.defaultGroup = defaultGroup;
	}

	public Long getBatchDetailPK() {
		return batchDetailPK;
	}

	public void setBatchDetailPK(Long batchDetailPK) {
		this.batchDetailPK = batchDetailPK;
	}

	public Long getBatchFK() {
		return batchFK;
	}

	public void setBatchFK(Long batchFK) {
		this.batchFK = batchFK;
	}

	
	public Long getPrdSetupFK() {
		return prdSetupFK;
	}

	public void setPrdSetupFK(Long prdSetupFK) {
		this.prdSetupFK = prdSetupFK;
	}

	public Long getGroupFK() {
		return groupFK;
	}

	public void setGroupFK(Long groupFK) {
		this.groupFK = groupFK;
	}

	public Long getCaseFK() {
		return caseFK;
	}

	public void setCaseFK(Long caseFK) {
		this.caseFK = caseFK;
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

	public Batch getBatch() {
		return batch;
	}

	public void setBatch(Batch batch) {
		this.batch = batch;
	}


	
	
	

	
}
