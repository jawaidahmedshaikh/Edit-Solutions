package com.editsolutions.prd.vo;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

public class ChangeLog implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long changeLogPK;
	private String tableName;
	private Long pkValue1;
	private Long pkValue2;
	private String fieldName;
	private String fieldType;
	private String oldValue;
	private String newValue;
	private Date modDate;
	private String modUser;

	public ChangeLog() {
	}

	public Long getChangeLogPK() {
		return changeLogPK;
	}

	public void setChangeLogPK(Long changeLogPK) {
		this.changeLogPK = changeLogPK;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public Long getPkValue1() {
		return pkValue1;
	}

	public void setPkValue1(Long pkValue1) {
		this.pkValue1 = pkValue1;
	}

	public Long getPkValue2() {
		return pkValue2;
	}

	public void setPkValue2(Long pkValue2) {
		this.pkValue2 = pkValue2;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getFieldType() {
		return fieldType;
	}

	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}

	public String getOldValue() {
		return oldValue;
	}

	public void setOldValue(String oldValue) {
		this.oldValue = oldValue;
	}

	public String getNewValue() {
		return newValue;
	}

	public void setNewValue(String newValue) {
		this.newValue = newValue;
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
