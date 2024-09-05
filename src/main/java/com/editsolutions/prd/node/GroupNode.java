package com.editsolutions.prd.node;

import java.io.Serializable;
import java.util.List;


public class GroupNode implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long groupSetupPK;
	private String groupName;
	private String groupNumber;
	private PRDNode prdNode;
	private List<NotificationNode> children;

	public Long getGroupSetupPK() {
		return groupSetupPK;
	}

	public void setGroupSetupPK(Long groupSetupPK) {
		this.groupSetupPK = groupSetupPK;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getGroupNumber() {
		return groupNumber;
	}

	public void setGroupNumber(String groupNumber) {
		this.groupNumber = groupNumber;
	}

	public PRDNode getPrdNode() {
		return prdNode;
	}

	public void setPrdNode(PRDNode prdNode) {
		this.prdNode = prdNode;
	}

	public List<NotificationNode> getChildren() {
		return children;
	}

	public void setChildren(List<NotificationNode> children) {
		this.children = children;
	}

	
}
