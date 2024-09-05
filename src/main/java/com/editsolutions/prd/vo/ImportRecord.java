package com.editsolutions.prd.vo;

import java.io.Serializable;
import java.sql.Date;

public class ImportRecord implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;

	private Long importRecordPK;
	private String accountNumber;
	private String groupNumber;
	private Date deductionDate;
	private Date applicationDate;
	private Date terminationDate;
	private Double billiblePremium;
	private String billingCompany;
	private Integer billingFrequency;
	private Integer currentAge;
	private Double deductionAmount;
	private Double currentFaceAmount;
	private String departmentLocation;
	private String employeeID;
	private String employeeLastName;
	private String employeeFirstName;
	private String employeeMI;
	private String employeeGender;
	private String employeeName;
	private String employerName;
	private String insuredLastName;
	private String insuredFirstName;
	private String insuredMI;
	private String insuredGender;
	private String insuredName;
	private Integer issueAge;
	private String street1;
	private String street2;
	private String city;
	private String state;
	private String zip;
	private Date insuredDOB;
	private String relationshipCode;
	private String ssn;
	private String smoker;
	private Date policyCoverageEffectiveDate;
	private String policyNumber;
	private Date policyTerminationEffectiveDate;
	private String policyProductCode;
	private String productType;
	private String status;
	private String policyStatus;
	private String billMode;
	private String system;
	private String FILLER;
	private String changeReason;
	private String trustmarkChangeReason;
	private String coverageTier;
	private String billingForm;
	private String ablacInd;
	private String changeType;
	private String lntCode;
	private String instypeShortLabel;
	private Date policyEffectiveDate;
	private Double modePremium;
	private boolean test;
	

	public boolean isTest() {
		return test;
	}

	public void setTest(boolean test) {
		this.test = test;
	}

	public Long getImportRecordPK() {
		return importRecordPK;
	}

	public void setImportRecordPK(Long importRecordPK) {
		this.importRecordPK = importRecordPK;
	}
	
	public Double getModePremium() {
		return modePremium;
	}

	public void setModePremium(Double modePremium) {
		this.modePremium = modePremium;
	}

	public String getPolicyProductCode() {
		return policyProductCode;
	}

	public void setPolicyProductCode(String policyProductCode) {
		this.policyProductCode = policyProductCode;
	}

	public Date getPolicyEffectiveDate() {
		return policyEffectiveDate;
	}

	public void setPolicyEffectiveDate(Date policyEffectiveDate) {
		this.policyEffectiveDate = policyEffectiveDate;
	}

	/**
	 * @return the accountNumber
	 */
	public String getAccountNumber() {
		return accountNumber;
	}

	/**
	 * @param accountNumber the accountNumber to set
	 */
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public Integer getCurrentAge() {
		return currentAge;
	}

	public void setCurrentAge(Integer currentAge) {
		this.currentAge = currentAge;
	}

	public Double getCurrentFaceAmount() {
		return currentFaceAmount;
	}

	public void setCurrentFaceAmount(Double currentFaceAmount) {
		this.currentFaceAmount = currentFaceAmount;
	}

	public Date getApplicationDate() {
		return applicationDate;
	}

	public void setApplicationDate(Date applicationDate) {
		this.applicationDate = applicationDate;
	}

	public String getEmployerName() {
		return employerName;
	}

	public void setEmployerName(String employerName) {
		this.employerName = employerName;
	}

	public String getPolicyStatus() {
		return policyStatus;
	}

	public void setPolicyStatus(String policyStatus) {
		this.policyStatus = policyStatus;
	}

	public String getBillMode() {
		return billMode;
	}

	public void setBillMode(String billMode) {
		this.billMode = billMode;
	}

	public String getGroupNumber() {
		return groupNumber;
	}

	public void setGroupNumber(String groupNumber) {
		this.groupNumber = groupNumber;
	}

	public Date getDeductionDate() {
		return deductionDate;
	}

	public void setDeductionDate(Date deductionDate) {
		this.deductionDate = deductionDate;
	}

	public Date getTerminationDate() {
		return terminationDate;
	}

	public void setTerminationDate(Date terminationDate) {
		this.terminationDate = terminationDate;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Date getInsuredDOB() {
		return insuredDOB;
	}

	public void setInsuredDOB(Date insuredDOB) {
		this.insuredDOB = insuredDOB;
	}

	public Integer getIssueAge() {
		return issueAge;
	}

	public void setIssueAge(Integer issueAge) {
		this.issueAge = issueAge;
	}

	public Double getBilliblePremium() {
		return billiblePremium;
	}

	public void setBilliblePremium(Double billiblePremium) {
		this.billiblePremium = billiblePremium;
	}

	public String getBillingCompany() {
		return billingCompany;
	}

	public void setBillingCompany(String billingCompany) {
		this.billingCompany = billingCompany;
	}

	public Integer getBillingFrequency() {
		return billingFrequency;
	}

	public void setBillingFrequency(Integer billingFrequency) {
		this.billingFrequency = billingFrequency;
	}

	public Double getDeductionAmount() {
		return deductionAmount;
	}

	public void setDeductionAmount(Double deductionAmount) {
		this.deductionAmount = deductionAmount;
	}

	public String getDepartmentLocation() {
		return departmentLocation;
	}

	public void setDepartmentLocation(String departmentLocation) {
		this.departmentLocation = departmentLocation;
	}

	public String getEmployeeID() {
		return employeeID;
	}

	public void setEmployeeID(String employeeID) {
		this.employeeID = employeeID;
	}

	public String getEmployeeLastName() {
		return employeeLastName;
	}

	public void setEmployeeLastName(String employeeLastName) {
		this.employeeLastName = employeeLastName;
	}

	public String getEmployeeFirstName() {
		return employeeFirstName;
	}

	public void setEmployeeFirstName(String employeeFirstName) {
		this.employeeFirstName = employeeFirstName;
	}

	public String getEmployeeMI() {
		return employeeMI;
	}

	public void setEmployeeMI(String employeeMI) {
		this.employeeMI = employeeMI;
	}

	public String getEmployeeGender() {
		return employeeGender;
	}

	public void setEmployeeGender(String employeeGender) {
		this.employeeGender = employeeGender;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public String getStreet1() {
		return street1;
	}

	public void setStreet1(String street1) {
		this.street1 = street1;
	}

	public String getStreet2() {
		return street2;
	}

	public void setStreet2(String street2) {
		this.street2 = street2;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getInsuredName() {
		return insuredName;
	}

	public void setInsuredName(String insuredName) {
		this.insuredName = insuredName;
	}

	public String getRelationshipCode() {
		return relationshipCode;
	}

	public void setRelationshipCode(String relationshipCode) {
		this.relationshipCode = relationshipCode;
	}

	public String getSsn() {
		return ssn;
	}

	public void setSsn(String ssn) {
		this.ssn = ssn;
	}

	public String getSmoker() {
		return smoker;
	}

	public void setSmoker(String smoker) {
		this.smoker = smoker;
	}

	public Date getPolicyCoverageEffectiveDate() {
		return policyCoverageEffectiveDate;
	}

	public void setPolicyCoverageEffectiveDate(Date policyCoverageEffectiveDate) {
		this.policyCoverageEffectiveDate = policyCoverageEffectiveDate;
	}

	public String getPolicyNumber() {
		return policyNumber;
	}

	public void setPolicyNumber(String policyNumber) {
		this.policyNumber = policyNumber;
	}

	public Date getPolicyTerminationEffectiveDate() {
		return policyTerminationEffectiveDate;
	}

	public void setPolicyTerminationEffectiveDate(
			Date policyTerminationEffectiveDate) {
		this.policyTerminationEffectiveDate = policyTerminationEffectiveDate;
	}
	
	

	public String getInsuredLastName() {
		return insuredLastName;
	}

	public void setInsuredLastName(String insuredLastName) {
		this.insuredLastName = insuredLastName;
	}

	public String getInsuredFirstName() {
		return insuredFirstName;
	}

	public void setInsuredFirstName(String insuredFirstName) {
		this.insuredFirstName = insuredFirstName;
	}

	public String getInsuredMI() {
		return insuredMI;
	}

	public void setInsuredMI(String insuredMI) {
		this.insuredMI = insuredMI;
	}

	public String getInsuredGender() {
		return insuredGender;
	}

	public void setInsuredGender(String insuredGender) {
		this.insuredGender = insuredGender;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCoverageTier() {
		return coverageTier;
	}

	public void setCoverageTier(String coverageTier) {
		this.coverageTier = coverageTier;
	}

	public String getSystem() {
		return system;
	}

	public void setSystem(String system) {
		this.system = system;
	}
	
	public String getTrustmarkChangeReason() {
		return trustmarkChangeReason;
	}

	public void setTrustmarkChangeReason(String trustmarkChangeReason) {
		this.trustmarkChangeReason = trustmarkChangeReason;
	}

	public String getChangeReason() {
		return changeReason;
	}

	public void setChangeReason(String changeReason) {
		this.changeReason = changeReason;
	}

	public String getBillingForm() {
		return billingForm;
	}

	public void setBillingForm(String billingForm) {
		this.billingForm = billingForm;
	}

	public String getAblacInd() {
		return ablacInd;
	}

	public void setAblacInd(String ablacInd) {
		this.ablacInd = ablacInd;
	}

	public String getChangeType() {
		return changeType;
	}

	public void setChangeType(String changeType) {
		this.changeType = changeType;
	}

	public String getLntCode() {
		return lntCode;
	}

	public void setLntCode(String lntCode) {
		this.lntCode = lntCode;
	}

	public String getInstypeShortLabel() {
		return instypeShortLabel;
	}

	public void setInstypeShortLabel(String instypeShortLabel) {
		this.instypeShortLabel = instypeShortLabel;
	}

	public String getFILLER() {
		return FILLER;
	}

	public void setFILLER(String fILLER) {
		FILLER = fILLER;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((billiblePremium == null) ? 0 : billiblePremium.hashCode());
		result = prime * result
				+ ((billingCompany == null) ? 0 : billingCompany.hashCode());
		result = prime
				* result
				+ ((billingFrequency == null) ? 0 : billingFrequency.hashCode());
		result = prime * result
				+ ((deductionAmount == null) ? 0 : deductionAmount.hashCode());
		/*
		result = prime * result
				+ ((deductionDate == null) ? 0 : deductionDate.hashCode());
		*/
		result = prime
				* result
				+ ((departmentLocation == null) ? 0 : departmentLocation
						.hashCode());
		result = prime
				* result
				+ ((employeeFirstName == null) ? 0 : employeeFirstName
						.hashCode());
		result = prime * result
				+ ((employeeGender == null) ? 0 : employeeGender.hashCode());
		result = prime * result
				+ ((employeeID == null) ? 0 : employeeID.hashCode());
		result = prime
				* result
				+ ((employeeLastName == null) ? 0 : employeeLastName.hashCode());
		result = prime * result
				+ ((employeeMI == null) ? 0 : employeeMI.hashCode());
		result = prime * result
				+ ((employeeName == null) ? 0 : employeeName.hashCode());
		result = prime * result
				+ ((insuredName == null) ? 0 : insuredName.hashCode());
		result = prime
				* result
				+ ((policyCoverageEffectiveDate == null) ? 0
						: policyCoverageEffectiveDate.hashCode());
		result = prime * result
				+ ((policyNumber == null) ? 0 : policyNumber.hashCode());
		result = prime
				* result
				+ ((policyTerminationEffectiveDate == null) ? 0
						: policyTerminationEffectiveDate.hashCode());
		result = prime * result
				+ ((productType == null) ? 0 : productType.hashCode());
		result = prime
				* result
				+ ((relationshipCode == null) ? 0 : relationshipCode.hashCode());
		result = prime * result + ((smoker == null) ? 0 : smoker.hashCode());
		result = prime * result + ((ssn == null) ? 0 : ssn.hashCode());
		result = prime * result + ((state == null) ? 0 : state.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((street1 == null) ? 0 : street1.hashCode());
		result = prime * result + ((street2 == null) ? 0 : street2.hashCode());
		result = prime * result + ((system == null) ? 0 : system.hashCode());
		result = prime * result + ((zip == null) ? 0 : zip.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		ImportRecord other = (ImportRecord) obj;
		if (billiblePremium == null) {
			if (other.billiblePremium != null) {
				return false;
			}
		} else if (!billiblePremium.equals(other.billiblePremium)) {
			return false;
		}
		if (billingCompany == null) {
			if (other.billingCompany != null) {
				return false;
			}
		} else if (!billingCompany.equals(other.billingCompany)) {
			return false;
		}
		if (billingFrequency == null) {
			if (other.billingFrequency != null) {
				return false;
			}
		} else if (!billingFrequency.equals(other.billingFrequency)) {
			return false;
		}
		if (deductionAmount == null) {
			if (other.deductionAmount != null) {
				return false;
			}
		} else if (!deductionAmount.equals(other.deductionAmount)) {
			return false;
		}
		/*
		 * remove as we are comparing two different dates
		if (deductionDate == null) {
			if (other.deductionDate != null) {
				return false;
			}
		} else if (!deductionDate.equals(other.deductionDate)) {
			return false;
		}
		*/
		if (departmentLocation == null) {
			if (other.departmentLocation != null) {
				return false;
			}
		} else if (!departmentLocation.equals(other.departmentLocation)) {
			return false;
		}
		if (employeeFirstName == null) {
			if (other.employeeFirstName != null) {
				return false;
			}
		} else if (!employeeFirstName.equals(other.employeeFirstName)) {
			return false;
		}
		if (employeeGender == null) {
			if (other.employeeGender != null) {
				return false;
			}
		} else if (!employeeGender.equals(other.employeeGender)) {
			return false;
		}
		if (employeeID == null) {
			if (other.employeeID != null) {
				return false;
			}
		} else if (!employeeID.equals(other.employeeID)) {
			return false;
		}
		if (employeeLastName == null) {
			if (other.employeeLastName != null) {
				return false;
			}
		} else if (!employeeLastName.equals(other.employeeLastName)) {
			return false;
		}
		if (employeeMI == null) {
			if (other.employeeMI != null) {
				return false;
			}
		} else if (!employeeMI.equals(other.employeeMI)) {
			return false;
		}
		if (employeeName == null) {
			if (other.employeeName != null) {
				return false;
			}
		} else if (!employeeName.equals(other.employeeName)) {
			return false;
		}
		if (insuredName == null) {
			if (other.insuredName != null) {
				return false;
			}
		} else if (!insuredName.equals(other.insuredName)) {
			return false;
		}
		if (policyCoverageEffectiveDate == null) {
			if (other.policyCoverageEffectiveDate != null) {
				return false;
			}
		} else if (!policyCoverageEffectiveDate
				.equals(other.policyCoverageEffectiveDate)) {
			return false;
		}
		if (policyNumber == null) {
			if (other.policyNumber != null) {
				return false;
			}
		} else if (!policyNumber.equals(other.policyNumber)) {
			return false;
		}
		if (policyTerminationEffectiveDate == null) {
			if (other.policyTerminationEffectiveDate != null) {
				return false;
			}
		} else if (!policyTerminationEffectiveDate
				.equals(other.policyTerminationEffectiveDate)) {
			return false;
		}
		if (productType == null) {
			if (other.productType != null) {
				return false;
			}
		} else if (!productType.equals(other.productType)) {
			return false;
		}
		if (relationshipCode == null) {
			if (other.relationshipCode != null) {
				return false;
			}
		} else if (!relationshipCode.equals(other.relationshipCode)) {
			return false;
		}
		if (smoker == null) {
			if (other.smoker != null) {
				return false;
			}
		} else if (!smoker.equals(other.smoker)) {
			return false;
		}
		if (ssn == null) {
			if (other.ssn != null) {
				return false;
			}
		} else if (!ssn.equals(other.ssn)) {
			return false;
		}
		if (state == null) {
			if (other.state != null) {
				return false;
			}
		} else if (!state.equals(other.state)) {
			return false;
		}
		if (status == null) {
			if (other.status != null) {
				return false;
			}
		} else if (!status.equals(other.status)) {
			return false;
		}
		if (street1 == null) {
			if (other.street1 != null) {
				return false;
			}
		} else if (!street1.equals(other.street1)) {
			return false;
		}
		if (street2 == null) {
			if (other.street2 != null) {
				return false;
			}
		} else if (!street2.equals(other.street2)) {
			return false;
		}
		if (system == null) {
			if (other.system != null) {
				return false;
			}
		} else if (!system.equals(other.system)) {
			return false;
		}
		if (zip == null) {
			if (other.zip != null) {
				return false;
			}
		} else if (!zip.equals(other.zip)) {
			return false;
		}
		return true;
	}

	
}
