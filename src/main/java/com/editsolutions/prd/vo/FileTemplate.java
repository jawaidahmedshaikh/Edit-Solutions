package com.editsolutions.prd.vo;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

import com.editsolutions.prd.vo.FileTemplateField;

import edu.emory.mathcs.backport.java.util.Collections;
import electric.util.holder.booleanInOut;

public class FileTemplate implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long fileTemplatePK;
	private String name;
	private String description;
	private String outputTypeCT;
	private String fileTemplateTypeCT;
	private String delimiterCT;
	private Date addDate;
	private String addUser;
	private Date modDate;
	private String modUser;
	private List<FileTemplateField> fileTemplateFields;
	private boolean includeTitles;

	public FileTemplate() {
	}

	public Long getFileTemplatePK() {
		return fileTemplatePK;
	}

	public void setFileTemplatePK(Long fileTemplatePK) {
		this.fileTemplatePK = fileTemplatePK;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getFileTemplateTypeCT() {
		return fileTemplateTypeCT;
	}

	public void setFileTemplateTypeCT(String fileTemplateTypeCT) {
		this.fileTemplateTypeCT = fileTemplateTypeCT;
	}

	public String getOutputTypeCT() {
		return outputTypeCT;
	}

	public void setOutputTypeCT(String outputTypeCT) {
		this.outputTypeCT = outputTypeCT;
	}

	public String getDelimiterCT() {
		return delimiterCT;
	}

	public void setDelimiterCT(String delimiterCT) {
		this.delimiterCT = delimiterCT;
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

	public List<FileTemplateField> getFileTemplateFields() {
		Collections.sort(fileTemplateFields);
		return fileTemplateFields;
	}

	public void setFileTemplateFields(List<FileTemplateField> fileTemplateFields) {
		this.fileTemplateFields = fileTemplateFields;
	}

	public boolean isIncludeTitles() {
		return includeTitles;
	}

	public void setIncludeTitles(boolean includeTitles) {
		this.includeTitles = includeTitles;
	}


}
