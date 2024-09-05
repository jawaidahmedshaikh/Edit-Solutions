package com.editsolutions.prd.vo;

import java.io.Serializable;
import java.sql.Date;

public class MessageTemplateField implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long messageTemplateFieldPK;
	private String tableName;
	private String fieldName;
	private String fieldTitle;
	private Long transformationFK;
	private Date addDate;
	private String addUser;
	private Date modDate;
	private String modUser;
	private Transformation transformation;

	public Long getMessageTemplateFieldPK() {
		return messageTemplateFieldPK;
	}

	public void setMessageTemplateFieldPK(Long messageTemplateFieldPK) {
		this.messageTemplateFieldPK = messageTemplateFieldPK;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getFieldTitle() {
		return fieldTitle;
	}

	public void setFieldTitle(String fieldTitle) {
		this.fieldTitle = fieldTitle;
	}

	public Long getTransformationFK() {
		return transformationFK;
	}

	public void setTransformationFK(Long transformationFK) {
		this.transformationFK = transformationFK;
	}

	public Transformation getTransformation() {
		return transformation;
	}

	public void setTransformation(Transformation transformation) {
		this.transformation = transformation;
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
