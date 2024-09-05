package com.editsolutions.prd.vo;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

public class SourceField implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long sourceFieldPK;
	private String sqlFieldName;
	private String odsFieldName;
	private String javaFieldName;
	private String friendlyFieldName;
	private String fieldType;
	private String javaFieldType;
	private int displayOrder;
	private Date addDate;
	private String addUser;
	private Date modDate;
	private String modUser;
	private String fileTemplateTypeCT;
	//private List<FileTemplateField> fileTemplateFields;

	public SourceField() {
	}

	public String getFileTemplateTypeCT() {
		return fileTemplateTypeCT;
	}

	public void setFileTemplateTypeCT(String fileTemplateTypeCT) {
		this.fileTemplateTypeCT = fileTemplateTypeCT;
	}

	public Long getSourceFieldPK() {
		return sourceFieldPK;
	}

	public void setSourceFieldPK(Long sourceFieldPK) {
		this.sourceFieldPK = sourceFieldPK;
	}

	public String getSqlFieldName() {
		return sqlFieldName;
	}

	public void setSqlFieldName(String sqlFieldName) {
		this.sqlFieldName = sqlFieldName;
	}

	public String getFriendlyFieldName() {
		return friendlyFieldName;
	}

	public void setFriendlyFieldName(String friendlyFieldName) {
		this.friendlyFieldName = friendlyFieldName;
	}

	public String getFieldType() {
		return fieldType;
	}

	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
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

	public String getJavaFieldName() {
		return javaFieldName;
	}

	public void setJavaFieldName(String javaFieldName) {
		this.javaFieldName = javaFieldName;
	}

	public String getJavaFieldType() {
		return javaFieldType;
	}

	public void setJavaFieldType(String javaFieldType) {
		this.javaFieldType = javaFieldType;
	}

	public String getOdsFieldName() {
		return odsFieldName;
	}

	public void setOdsFieldName(String odsFieldName) {
		this.odsFieldName = odsFieldName;
	}
	
	

	//public List<FileTemplateField> getFileTemplateFields() {
	//	return fileTemplateFields;
	//}

	//public void setFileTemplateFields(List<FileTemplateField> fileTemplateFields) {
	//	this.fileTemplateFields = fileTemplateFields;
	//}
	
	

}
