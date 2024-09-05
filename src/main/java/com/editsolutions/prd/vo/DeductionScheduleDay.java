package com.editsolutions.prd.vo;

import java.io.Serializable;
import java.sql.Date;

public class DeductionScheduleDay implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long deductionScheduleDayPK;
	private Long prdSetupFK;
	private Date prdDueDate;
	private String payrollDeductionCodeCT;
	private Long payrollDeductionScheduleFK;
	private Date addDate;
	private String addUser;
	private Date modDate;
	private String modUser;
	

	public DeductionScheduleDay() {
	}


	public Long getDeductionScheduleDayPK() {
		return deductionScheduleDayPK;
	}

	

	public Long getPayrollDeductionScheduleFK() {
		return payrollDeductionScheduleFK;
	}


	public void setPayrollDeductionScheduleFK(Long payrollDeductionScheduleFK) {
		this.payrollDeductionScheduleFK = payrollDeductionScheduleFK;
	}


	public void setDeductionScheduleDayPK(Long deductionScheduleDayPK) {
		this.deductionScheduleDayPK = deductionScheduleDayPK;
	}


	public Long getPrdSetupFK() {
		return prdSetupFK;
	}


	public void setPrdSetupFK(Long prdSetupFK) {
		this.prdSetupFK = prdSetupFK;
	}


	public Date getPrdDueDate() {
		return prdDueDate;
	}


	public void setPrdDueDate(Date prdDueDate) {
		this.prdDueDate = prdDueDate;
	}


	public String getPayrollDeductionCodeCT() {
		return payrollDeductionCodeCT;
	}


	public void setPayrollDeductionCodeCT(String payrollDeductionCodeCT) {
		this.payrollDeductionCodeCT = payrollDeductionCodeCT;
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
