package com.editsolutions.prd.vo;

import java.io.Serializable;
import java.sql.Date;

public class BusinessDay implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long businessDayPK;
	private Date businessDate;
	private boolean activeIndicator;

	public Long getBusinessDayPK() {
		return businessDayPK;
	}

	public void setBusinessDayPK(Long businessDayPK) {
		this.businessDayPK = businessDayPK;
	}

	public Date getBusinessDate() {
		return businessDate;
	}

	public void setBusinessDate(Date businessDate) {
		this.businessDate = businessDate;
	}

	public boolean isActiveIndicator() {
		return activeIndicator;
	}

	public void setActiveIndicator(boolean activeIndicator) {
		this.activeIndicator = activeIndicator;
	}

}
