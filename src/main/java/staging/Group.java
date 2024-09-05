/*
 * User: dlataille
 * Date: Oct 2, 2007
 * Time: 10:22:05 AM
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */

package staging;

import edit.common.EDITBigDecimal;
import edit.common.EDITMap;
import edit.services.db.hibernate.HibernateEntity;
import edit.services.db.hibernate.SessionHelper;

import java.math.BigDecimal;
import java.util.Set;
import java.util.HashSet;
import java.util.List;

public class Group extends HibernateEntity
{
    private Long groupPK;
    private Long stagingFK;
    private Long caseFK;
    private Long billScheduleFK;
    private String contractGroupNumber;
    private String contractGroupType;
    private String groupStatus;
    private String corporateName;
    private String addressLine1;
    private String addressLine2;
    private String addressLine3;
    private String addressLine4;
    private String city;
    private String state;
    private String zip;
    
    /** The number of hours that an employee must work per week to be considered an 'active' employee */
    private BigDecimal activeEligibilityHours;
    
    /** A number of months or days after initial hire than an active employee is not eligible for benefits */
    private BigDecimal ineligibilityPeriodUnits;
    
    /** Indicates whether ineligibilityPeriodUnits represents a duration of days or months */
    private String ineligibilityPeriodType;

    private Case contractGroupCase;
    private BillSchedule billSchedule;

    private Set<PayrollDeductionSchedule> payrollDeductionSchedules;
    private Set<SegmentBase> segmentBases;
    private Set<BatchContractSetup> batchContractSetups;

    /**
     * Target database to be used for lookups, etc.
     */
    public static String DATABASE = SessionHelper.STAGING;

    public Group()
    {
        this.payrollDeductionSchedules = new HashSet<PayrollDeductionSchedule>();
        this.segmentBases = new HashSet<SegmentBase>();
        this.batchContractSetups = new HashSet<BatchContractSetup>();
    }

    /**
     * Getter.
     * @return
     */
    public Long getGroupPK()
    {
        return groupPK;
    }

    /**
     * Setter.
     * @param groupPK
     */
    public void setGroupPK(Long groupPK)
    {
        this.groupPK = groupPK;
    }

    /**
     * Getter.
     * @return
     */
    public Long getCaseFK()
    {
        return caseFK;
    }

    /**
     * Setter.
     * @param caseFK
     */
    public void setCaseFK(Long caseFK)
    {
        this.caseFK = caseFK;
    }

    /**
     * Returns the parent Case
     * @return
     */
    public Case getContractGroupCase()
    {
        return contractGroupCase;
    }

    /**
     * Sets the parent Case
     * @param staging
     */
    public void setContractGroupCase(Case contractGroupCase)
    {
        this.contractGroupCase = contractGroupCase;
    }

    /**
     * Getter.
     * @return
     */
    public Long getBillScheduleFK()
    {
        return billScheduleFK;
    }

    /**
     * Setter.
     * @param caseFK
     */
    public void setBillScheduleFK(Long billScheduleFK)
    {
        this.billScheduleFK = billScheduleFK;
    }

    public BillSchedule getBillSchedule()
    {
        return billSchedule;
    }

    public void setBillSchedule(BillSchedule billSchedule)
    {
        this.billSchedule = billSchedule;
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
    public String getGroupStatus()
    {
        return groupStatus;
    }

    /**
     * Setter.
     * @param groupStatus
     */
    public void setGroupStatus(String groupStatus)
    {
        this.groupStatus = groupStatus;
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
     * @see #segmentBases
     * @param segmentBases
     */
    public void setSegmentBases(Set<SegmentBase> segmentBases)
    {
        this.segmentBases = segmentBases;
    }

    /**
     * @see #segmentBases
     * @return
     */
    public Set<SegmentBase> getSegmentBases()
    {
        return segmentBases;
    }

    public void addSegmentBase(SegmentBase segmentBase)
    {
        this.segmentBases.add(segmentBase);
    }

    /**
     * @see #payrollDeductionSchedules
     * @param payrollDeductionSchedules
     */
    public void setPayrollDeductionSchedules(Set<PayrollDeductionSchedule> payrollDeductionSchedules)
    {
        this.payrollDeductionSchedules = payrollDeductionSchedules;
    }

    /**
     * @see #payrollDeductionSchedules
     * @return
     */
    public Set<PayrollDeductionSchedule> getPayrollDeductionSchedules()
    {
        return payrollDeductionSchedules;
    }

    public void addPayrollDeductionSchedule(PayrollDeductionSchedule prdSched)
    {
        this.payrollDeductionSchedules.add(prdSched);
    }

    public Set<BatchContractSetup> getBatchContractSetups()
    {
        return this.batchContractSetups;
    }

    public void setBatchContractSetups(Set<BatchContractSetup> batchContractSetups)
    {
        this.batchContractSetups = batchContractSetups;
    }

    public void addBatchContractSetups(BatchContractSetup batchContractSetup)
    {
        this.batchContractSetups.add(batchContractSetup);

        batchContractSetup.setGroup(this);
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
        return Group.DATABASE;
    }

    public static Group findByCaseFK_ContractGroupNumber(Long caseFK, String contractGroupNumber, String database)
    {
        String hql = " select g from Group g" +
                    " where g.CaseFK = :caseFK" +
                    " and g.ContractGroupNumber = :contractGroupNumber";

        EDITMap params = new EDITMap();
        params.put("caseFK", caseFK);
        params.put("contractGroupNumber", contractGroupNumber);

        List<Group> results = SessionHelper.executeHQL(hql, params, database);

        if (results.size() > 0)
        {
            return (Group) results.get(0);
        }
        else
        {
            return null;
        }
    }
}
