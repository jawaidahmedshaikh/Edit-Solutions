package com.editsolutions.prd.vo;

import java.io.Serializable;
import java.sql.Date;

public class PRDExclusion implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long prdExclusionPK;
	private Long prdSetupFK;
	private String exclusionType;
	private String exclusionCode;
	private Date addDate;
	private String addUser;
	private Date modDate;
	private String modUser;

	public Long getPrdExclusionPK() {
		return prdExclusionPK;
	}

	public void setPrdExclusionPK(Long prdExclusionPK) {
		this.prdExclusionPK = prdExclusionPK;
	}

	public Long getPrdSetupFK() {
		return prdSetupFK;
	}

	public void setPrdSetupFK(Long prdSetupFK) {
		this.prdSetupFK = prdSetupFK;
	}

	public String getExclusionType() {
		return exclusionType;
	}

	public void setExclusionType(String exclusionType) {
		this.exclusionType = exclusionType;
	}

	public String getExclusionCode() {
		return exclusionCode;
	}

	public void setExclusionCode(String exclusionCode) {
		this.exclusionCode = exclusionCode;
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

}
