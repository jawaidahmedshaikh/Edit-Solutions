package com.editsolutions.prd.node;

import java.io.Serializable;
import java.util.List;

public class PRDNode implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long prdSetupPK;
	
	private List<NotificationNode> children;

	public Long getPrdSetupPK() {
		return prdSetupPK;
	}

	public void setPrdSetupPK(Long prdSetupPK) {
		this.prdSetupPK = prdSetupPK;
	}

	public List<NotificationNode> getChildren() {
		return children;
	}

	public void setChildren(List<NotificationNode> children) {
		this.children = children;
	}
	
	

}
