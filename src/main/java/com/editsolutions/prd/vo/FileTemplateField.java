package com.editsolutions.prd.vo;

import java.io.Serializable;
import java.sql.Date;


public class FileTemplateField implements Serializable, Comparable<FileTemplateField> {

	private static final long serialVersionUID = 1L;
	private Long fileTemplateFieldPK;
	private Long fileTemplateFK;
	private Long sourceFieldFK;
	private String fieldTitle;
	private Integer fieldOrder;
	private String defaultValue;
	private Long transformationFK;
	private Integer fieldLength;
	private Date addDate;
	private String addUser;
	private Date modDate;
	private String modUser;
	private boolean doCompare;
	private boolean sortBy;
	
	private SourceField sourceField;
	private Transformation transformation;

	public FileTemplateField() {
	}

	public Long getFileTemplateFieldPK() {
		return fileTemplateFieldPK;
	}

	public void setFileTemplateFieldPK(Long fileTemplateFieldPK) {
		this.fileTemplateFieldPK = fileTemplateFieldPK;
	}

	public Long getFileTemplateFK() {
		return fileTemplateFK;
	}

	public void setFileTemplateFK(Long fileTemplateFK) {
		this.fileTemplateFK = fileTemplateFK;
	}

	public Long getSourceFieldFK() {
		return sourceFieldFK;
	}

	public void setSourceFieldFK(Long sourceFieldFK) {
		this.sourceFieldFK = sourceFieldFK;
	}

	public String getFieldTitle() {
		return fieldTitle;
	}

	public void setFieldTitle(String fieldTitle) {
		this.fieldTitle = fieldTitle;
	}

	public Integer getFieldOrder() {
		return fieldOrder;
	}

	public void setFieldOrder(Integer fieldOrder) {
		this.fieldOrder = fieldOrder;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public Long getTransformationFK() {
		return transformationFK;
	}

	public void setTransformationFK(Long transformationFK) {
		this.transformationFK = transformationFK;
	}

	public Integer getFieldLength() {
		return fieldLength;
	}

	public void setFieldLength(Integer fieldLength) {
		this.fieldLength = fieldLength;
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

	public SourceField getSourceField() {
		return sourceField;
	}

	public void setSourceField(SourceField sourceField) {
		this.sourceField = sourceField;
	}

	public Transformation getTransformation() {
		return transformation;
	}

	public void setTransformation(Transformation transformation) {
		this.transformation = transformation;
	}

	public boolean isDoCompare() {
		return doCompare;
	}

	public void setDoCompare(boolean doCompare) {
		this.doCompare = doCompare;
	}

	public boolean isSortBy() {
		return sortBy;
	}

	public void setSortBy(boolean sortBy) {
		this.sortBy = sortBy;
	}

	@Override
	public int compareTo(FileTemplateField another) {
		if (this.getFieldOrder() < another.getFieldOrder()) {
			return -1;
		} else {
		    return 1;
		}
	}


}
