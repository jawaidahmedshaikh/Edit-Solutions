/*
 * User: dlataille
 * Date: Oct 2, 2007
 * Time: 9:41:05 AM
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */

package staging;

import edit.common.EDITDate;
import edit.common.EDITMap;
import edit.services.db.hibernate.HibernateEntity;
import edit.services.db.hibernate.SessionHelper;

import java.math.BigDecimal;
import java.util.Set;
import java.util.List;
import java.util.HashSet;

public class Case extends HibernateEntity
{
    private Long casePK;
    private Long stagingFK;
    private String contractGroupNumber;
    private String contractGroupType;
    private String caseStatus;
    private String caseType;
    private EDITDate creationDate;
    private String domicileState;
    private String corporateName;
    private String addressLine1;
    private String addressLine2;
    private String addressLine3;
    private String addressLine4;
    private String city;
    private String state;
    private String zip;
    private String sicCode;
    private String groupTrustState;

    /** The number of hours that an employee must work per week to be considered an 'active' employee */
    private BigDecimal activeEligibilityHours;
    
    /** A number of months or days after initial hire than an active employee is not eligible for benefits */
    private BigDecimal ineligibilityPeriodUnits;
    
    /** Indicates whether ineligibilityPeriodUnits represents a duration of days or months */
    private String ineligibilityPeriodType;
    
    private Staging staging;

    private Set<Group> groups;
    private Set<Enrollment> enrollments;
    private Set<CaseStatusHistory> caseStatusHistories;
    private Set<FilteredProduct> filteredProducts;

    /**
     * Target database to be used for lookups, etc.
     */
    public static String DATABASE = SessionHelper.STAGING;

    public Case()
    {
        this.groups = new HashSet<Group>();
        this.enrollments = new HashSet<Enrollment>();
        this.caseStatusHistories = new HashSet<CaseStatusHistory>();
        this.filteredProducts = new HashSet<FilteredProduct>();
    }

    /**
     * Getter.
     * @return
     */
    public Long getCasePK()
    {
        return casePK;
    }

    /**
     * Setter.
     * @param casePK
     */
    public void setCasePK(Long casePK)
    {
        this.casePK = casePK;
    }

    /**
     * Getter.
     * @return
     */
    public Long getStagingFK()
    {
        return stagingFK;
    }

    /**
     * Setter.
     * @param stagingFK
     */
    public void setStagingFK(Long stagingFK)
    {
        this.stagingFK = stagingFK;
    }

    /**
     * Returns the parent staging
     * @return
     */
    public Staging getStaging()
    {
        return staging;
    }

    /**
     * Sets the parent Staging
     * @param staging
     */
    public void setStaging(Staging staging)
    {
        this.staging = staging;
    }

    /**
     * Getter.
     * @return
     */
    public String getContractGroupNumber()
    {
        return contractGroupNumber;
    }

    /**
     * Setter.
     * @param contractGroupNumber
     */
    public void setContractGroupNumber(String contractGroupNumber)
    {
        this.contractGroupNumber = contractGroupNumber;
    }

    /**
     * Getter.
     * @return
     */
    public String getContractGroupType()
    {
        return contractGroupType;
    }

    /**
     * Setter.
     * @param contractGroupType
     */
    public void setContractGroupType(String contractGroupType)
    {
        this.contractGroupType = contractGroupType;
    }

    /**
     * Getter.
     * @return
     */
    public String getCaseStatus()
    {
        return caseStatus;
    }

    /**
     * Setter.
     * @param caseStatus
     */
    public void setCaseStatus(String caseStatus)
    {
        this.caseStatus = caseStatus;
    }

    /**
     * Getter.
     * @return
     */
    public String getCaseType()
    {
        return caseType;
    }

    /**
     * Setter.
     * @param caseType
     */
    public void setCaseType(String caseType)
    {
        this.caseType = caseType;
    }

    /**
     * Getter.
     * @return
     */
    public EDITDate getCreationDate()
    {
        return creationDate;
    }

    /**
     * Setter.
     * @param creationDate
     */
    public void setCreationDate(EDITDate creationDate)
    {
        this.creationDate = creationDate;
    }

    /**
     * Getter.
     * @return
     */
    public String getDomicileState()
    {
        return domicileState;
    }

    /**
     * Setter.
     * @param domicileState
     */
    public void setDomicileState(String domicileState)
    {
        this.domicileState = domicileState;
    }

    /**
     * Getter.
     * @return
     */
    public String getCorporateName()
    {
        return corporateName;
    }

    /**
     * Setter.
     * @param corporateName
     */
    public void setCorporateName(String corporateName)
    {
        this.corporateName = corporateName;
    }

    /**
     * Getter.
     * @return
     */
    public String getAddressLine1()
    {
        return addressLine1;
    }

    /**
     * Setter.
     * @param addressLine1
     */
    public void setAddressLine1(String addressLine1)
    {
        this.addressLine1 = addressLine1;
    }

    /**
     * Getter.
     * @return
     */
    public String getAddressLine2()
    {
        return addressLine2;
    }

    /**
     * Setter.
     * @param addressLine2
     */
    public void setAddressLine2(String addressLine2)
    {
        this.addressLine2 = addressLine2;
    }

    /**
     * Getter.
     * @return
     */
    public String getAddressLine3()
    {
        return addressLine3;
    }

    /**
     * Setter.
     * @param addressLine3
     */
    public void setAddressLine3(String addressLine3)
    {
        this.addressLine3 = addressLine3;
    }

    /**
     * Getter.
     * @return
     */
    public String getAddressLine4()
    {
        return addressLine4;
    }

    /**
     * Setter.
     * @param addressLine4
     */
    public void setAddressLine4(String addressLine4)
    {
        this.addressLine4 = addressLine4;
    }

    /**
     * Getter.
     * @return
     */
    public String getCity()
    {
        return city;
    }

    /**
     * Setter.
     * @param city
     */
    public void setCity(String city)
    {
        this.city = city;
    }

    /**
     * Getter.
     * @return
     */
    public String getState()
    {
        return state;
    }

    /**
     * Setter.
     * @param state
     */
    public void setState(String state)
    {
        this.state = state;
    }

    /**
     * Getter.
     * @return
     */
    public String getZip()
    {
        return zip;
    }

    /**
     * Setter.
     * @param zip
     */
    public void setZip(String zip)
    {
        this.zip = zip;
    }


    /**
     * Getter.
     * @return
     */
    public String getSicCode()
    {
        return sicCode;
    }

    /**
     * Setter.
     * @param sicCode
     */
    public void setSicCode(String sicCode)
    {
        this.sicCode = sicCode;
    }

    /**
     * Getter.
     * @return
     */
    public String getGroupTrustState()
    {
        return groupTrustState;
    }

    /**
     * Setter.
     * @param groupTrustState
     */
    public void setGroupTrustState(String groupTrustState)
    {
        this.groupTrustState = groupTrustState;
    }

    /**
     * @see #groups
     * @param groups
     */
    public void setGroups(Set<Group> groups)
    {
        this.groups = groups;
    }

    /**
     * @see #groups
     * @return
     */
    public Set<Group> getGroups()
    {
        return groups;
    }

    public void addGroup(Group group)
    {
        this.groups.add(group);
    }

    /**
     * @see #enrollments
     * @param enrollments
     */
    public void setEnrollments(Set<Enrollment> enrollments)
    {
        this.enrollments = enrollments;
    }

    /**
     * @see #enrollments
     * @return
     */
    public Set<Enrollment> getEnrollments()
    {
        return enrollments;
    }

    public void addEnrollment(Enrollment enrollment)
    {
        this.enrollments.add(enrollment);
    }

    /**
     * Getter.
     * @return
     */
    public Set<CaseStatusHistory> getCaseStatusHistories()
    {
        return caseStatusHistories;
    }

    /**
     * Setter.
     * @param caseStatusHistories
     */
    public void setCaseStatusHistories(Set<CaseStatusHistory> caseStatusHistories)
    {
        this.caseStatusHistories = caseStatusHistories;
    }

    /**
     * Add another caseStatusHistory to the current mapped caseStatusHistories.
     * @param caseStatusHistory
     */
    public void addCaseStatusHistory(CaseStatusHistory caseStatusHistory)
    {
        this.caseStatusHistories.add(caseStatusHistory);
    }

    /**
     * Getter.
     * @return
     */
    public Set<FilteredProduct> getFilteredProducts()
    {
        return filteredProducts;
    }

    /**
     * Setter.
     * @param filteredProducts
     */
    public void setFilteredProducts(Set<FilteredProduct> filteredProducts)
    {
        this.filteredProducts = filteredProducts;
    }

    /**
     * Add another filteredProduct to the current mapped filteredProducts.
     * @param filteredProduct
     */
    public void addFilteredProduct(FilteredProduct filteredProduct)
    {
        this.filteredProducts.add(filteredProduct);
    }

	public BigDecimal getIneligibilityPeriodUnits() {
		return ineligibilityPeriodUnits;
	}

	public void setIneligibilityPeriodUnits(BigDecimal ineligibilityPeriodUnits) {
		this.ineligibilityPeriodUnits = ineligibilityPeriodUnits;
	}

	public String getIneligibilityPeriodType() {
		return ineligibilityPeriodType;
	}

	public void setIneligibilityPeriodType(String ineligibilityPeriodType) {
		this.ineligibilityPeriodType = ineligibilityPeriodType;
	}

	public BigDecimal getActiveEligibilityHours() {
		return activeEligibilityHours;
	}

	public void setActiveEligibilityHours(BigDecimal activeEligibilityHours) {
		this.activeEligibilityHours = activeEligibilityHours;
	}
    
    public String getDatabase()
    {
        return Case.DATABASE;
    }

    public static Case findByStagingFK_CaseNumber(Long stagingFK, String caseNumber, String database)
    {
        String hql = " select c from Case c" +
                    " where c.StagingFK = :stagingFK" +
                    " and c.ContractGroupNumber = :caseNumber";

        EDITMap params = new EDITMap();
        params.put("stagingFK", stagingFK);
        params.put("caseNumber", caseNumber);

        List<Case> results = SessionHelper.executeHQL(hql, params, database);

        if (results.size() > 0)
        {
            return (Case) results.get(0);
        }
        else
        {
            return null;
        }
    }
}
