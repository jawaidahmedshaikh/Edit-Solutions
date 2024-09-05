package com.selman.calcfocus.correspondence.builder;

public class RoleValues {

	Long contractClientFK;
    Long segmentBaseFK;
    String role;
    
    
	public RoleValues(Long contractClientFK, Long segmentBaseFK, String role) {
		super();
		this.contractClientFK = contractClientFK;
		this.segmentBaseFK = segmentBaseFK;
		this.role = role;
	}

	public Long getSegmentBaseFK() {
		return segmentBaseFK;
	}

	public void setSegmentBaseFK(Long segmentBaseFK) {
		this.segmentBaseFK = segmentBaseFK;
	}

	public Long getContractClientFK() {
		return contractClientFK;
	}
	public void setContractClientFK(Long contractClientFK) {
		this.contractClientFK = contractClientFK;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
    
    

}
