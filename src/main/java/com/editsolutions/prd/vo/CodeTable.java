package com.editsolutions.prd.vo;

import java.io.Serializable;

public class CodeTable implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	Long codeTableDefPK;
	Long CodeTablePK;
	String codeTableName;
	String code;
	String codeDescription;

	public CodeTable(Long codeTableDefPK, Long codeTablePK, String codeTableName,
			String code, String codeDescription) {
		super();
		this.codeTableDefPK = codeTableDefPK;
		CodeTablePK = codeTablePK;
		this.codeTableName = codeTableName;
		this.code = code;
		this.codeDescription = codeDescription;
	}

	public Long getCodeTableDefPK() {
		return codeTableDefPK;
	}

	public void setCodeTableDefPK(Long codeTableDefPK) {
		this.codeTableDefPK = codeTableDefPK;
	}

	public Long getCodeTablePK() {
		return CodeTablePK;
	}

	public void setCodeTablePK(Long codeTablePK) {
		CodeTablePK = codeTablePK;
	}

	public String getCodeTableName() {
		return codeTableName;
	}

	public void setCodeTableName(String codeTableName) {
		this.codeTableName = codeTableName;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCodeDescription() {
		return codeDescription;
	}

	public void setCodeDescription(String codeDescription) {
		this.codeDescription = codeDescription;
	}
	
	

}
