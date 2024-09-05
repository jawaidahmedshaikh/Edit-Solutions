package com.editsolutions.prd.vo;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

public class Rep implements Serializable {

	private static final long serialVersionUID = 1L;

	String repName;
	String repPhoneNumber;

	public String getRepName() {
		return repName;
	}

	public void setRepName(String repName) {
		this.repName = repName;
	}

	public String getRepPhoneNumber() {
		return repPhoneNumber;
	}

	public void setRepPhoneNumber(String repPhoneNumber) {
		this.repPhoneNumber = repPhoneNumber;
	}

}
