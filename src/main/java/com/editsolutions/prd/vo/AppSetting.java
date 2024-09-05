package com.editsolutions.prd.vo;

import java.io.Serializable;
import java.sql.Date;

public class AppSetting implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long appSettingPK;
	private String name;
	private String value;
	private String type;
	private String description;
	private int displayOrder;
	private Date addDate;
	private String addUser;
	private Date modDate;
	private String modUser;

	public Long getAppSettingPK() {
		return appSettingPK;
	}

	public void setAppSettingPK(Long appSettingPK) {
		this.appSettingPK = appSettingPK;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getDisplayOrder() {
		return displayOrder;
	}

	public void setDisplayOrder(int displayOrder) {
		this.displayOrder = displayOrder;
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
