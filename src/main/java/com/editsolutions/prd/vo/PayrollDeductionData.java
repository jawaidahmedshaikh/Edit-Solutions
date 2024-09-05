package com.editsolutions.prd.vo;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Transient;

import electric.util.holder.booleanInOut;
import electric.util.holder.intInOut;

public class PayrollDeductionData implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long dataPK;
	private Long dataHeaderFK;
	private Long recordFK;
	private Long fileTemplateFieldFK;
	private String fieldValue;
	private Date addDate;
	private String addUser;
	private Date modDate;
	private String modUser;
	private DataHeader dataHeader;
	FileTemplateField fileTemplateField;


	public PayrollDeductionData() {
	}
	
	public Long getRecordFK() {
		return recordFK;
	}

	public void setRecordFK(Long recordFK) {
		this.recordFK = recordFK;
	}
	
	public Long getDataPK() {
		return dataPK;
	}

	public void setDataPK(Long dataPK) {
		this.dataPK = dataPK;
	}

	public Long getDataHeaderFK() {
		return dataHeaderFK;
	}

	public void setDataHeaderFK(Long dataHeaderFK) {
		this.dataHeaderFK = dataHeaderFK;
	}

	public Long getFileTemplateFieldFK() {
		return fileTemplateFieldFK;
	}

	public void setFileTemplateFieldFK(Long fileTemplateFieldFK) {
		this.fileTemplateFieldFK = fileTemplateFieldFK;
	}

	public String getFieldValue() {
		return fieldValue;
	}

	public void setFieldValue(String fieldValue) {
		this.fieldValue = fieldValue;
	}
	
	public DataHeader getDataHeader() {
		return dataHeader;
	}

	public void setDataHeader(DataHeader dataHeader) {
		this.dataHeader = dataHeader;
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

	public FileTemplateField getFileTemplateField() {
		return fileTemplateField;
	}

	public void setFileTemplateField(FileTemplateField fileTemplateField) {
		this.fileTemplateField = fileTemplateField;
	}


}
