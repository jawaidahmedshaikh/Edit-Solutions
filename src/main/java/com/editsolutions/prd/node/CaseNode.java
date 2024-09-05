package com.editsolutions.prd.node;

import java.io.Serializable;
import java.util.List;
import com.editsolutions.prd.vo.GroupSetup;

public class CaseNode implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long caseSetupPK;
	private String caseName;
	private String caseNumber;
	private List<GroupNode> children;

	public Long getCaseSetupPK() {
		return caseSetupPK;
	}

	public void setCaseSetupPK(Long caseSetupPK) {
		this.caseSetupPK = caseSetupPK;
	}

	public String getCaseName() {
		return caseName;
	}

	public void setCaseName(String caseName) {
		this.caseName = caseName;
	}

	public String getCaseNumber() {
		return caseNumber;
	}

	public void setCaseNumber(String caseNumber) {
		this.caseNumber = caseNumber;
	}

	public List<GroupNode> getChildren() {
		return children;
	}

	public void setChildren(List<GroupNode> children) {
		this.children = children;
	}

}
