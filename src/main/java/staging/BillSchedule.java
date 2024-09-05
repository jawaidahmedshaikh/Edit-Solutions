/*
 * User: dlataille
 * Date: Oct 2, 2007
 * Time: 11:31:44 AM

 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */

package staging;

import edit.common.EDITDate;
import edit.common.EDITMap;
import edit.common.EDITBigDecimal;
import edit.services.db.hibernate.*;

import java.util.*;

public class BillSchedule extends HibernateEntity
{
    private Long billSchedulePK;
    private Long stagingFK;
    private String billingCompany;
    private EDITDate firstBillDueDate;
    private int leadDaysOR;
    private String weekDay;
    private EDITDate effectiveDate;
    private EDITDate terminationDate;
    private String status;
    private String sortOption;
    private String billingConsolidation;
    private String socialSecurityMask;
    private int numberOfCopiesGroup;
    private int numberOfCopiesAgent;
    private EDITDate nextBillExtractDate;
    private EDITDate nextBillDueDate;
    private EDITDate lastBillDueDate;
    private String billingMode;
    private String billType;
    private String creationOperator;
    private EDITDate creationDate;
    private String repName;
    private String repPhoneNumber;
    private String billMethod;
    private String skipMonthStart1;
    private String skipNumberOfMonths1;
    private String skipMonthStart2;
    private String skipNumberOfMonths2;
    private String skipMonthStart3;
    private String skipNumberOfMonths3;
    private EDITDate firstDeductionDate;
    private EDITDate lastDeductionDate;
    private String deductionFrequency;
    private int eftDraftDay;
    private String eftDraftDayStartMonth;
    private EDITBigDecimal requiredPremiumAmount;
    private EDITDate transitionPeriodEndDate;
    private Long esBillScheduleKey;

    private Staging staging;

    //  Children
    private Set<SegmentBase> segmentBases;
    private Set<Group> groups;
    private Set<BillGroup> billGroups;

    /**
     * Target database to be used for lookups, etc.
     */
    public static String DATABASE = SessionHelper.STAGING;

    /**
     * Instantiates a BillSchedule entity.
     */
    public BillSchedule()
    {
        init();
    }

    /**
     * Guarantees that a VO and CRUDEntityImpl are properly instantiated.
     */
    private final void init()
    {
        this.segmentBases = new HashSet<SegmentBase>();
        this.groups = new HashSet<Group>();
        this.billGroups = new HashSet<BillGroup>();
    }

    /**
     * Save the entity using Hibernate
     */
    public void hSave()
    {
        SessionHelper.saveOrUpdate(this, BillSchedule.DATABASE);
    }

    /**
     * Delete the entity using Hibernate
     */
    public void hDelete()
    {
        SessionHelper.delete(this, BillSchedule.DATABASE);
    }

    /**
     * Getter.
     * @return
     */
    public Long getBillSchedulePK()
    {
        return this.billSchedulePK;
    }

    /**
     * Setter.
     * @param billSchedulePK
     */
    public void setBillSchedulePK(Long billSchedulePK)
    {
        this.billSchedulePK = billSchedulePK;
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
     * Getter.
     * @return
     */
    public Staging getStaging()
    {
        return staging;
    }

    /**
     * Setter.
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
    public String getBillingCompany()
    {
        return billingCompany;
    }

    /**
     * Setter.
     * @param billingCompany
     */
    public void setBillingCompany(String billingCompany)
    {
        this.billingCompany = billingCompany;
    }

    /**
     * Getter.
     * @return
     */
    public EDITDate getFirstBillDueDate()
    {
        return firstBillDueDate;
    }

    /**
     * Setter.
     * @param firstBillDueDate
     */
    public void setFirstBillDueDate(EDITDate firstBillDueDate)
    {
        this.firstBillDueDate = firstBillDueDate;
    }

    /**
     * Getter.
     * @return
     */
    public int getLeadDaysOR()
    {
        return leadDaysOR;
    }

    /**
     * Setter.
     * @param leadDaysOR
     */
    public void setLeadDaysOR(int leadDaysOR)
    {
        this.leadDaysOR = leadDaysOR;
    }

    /**
     * Getter.
     * @return
     */
    public String getWeekDay()
    {
        return weekDay;
    }

    /**
     * Setter.
     * @param weekDay
     */
    public void setWeekDay(String weekDay)
    {
        this.weekDay = weekDay;
    }

    /**
     * Getter.
     * @return
     */
    public EDITDate getEffectiveDate()
    {
        return effectiveDate;
    }

    /**
     * Setter.
     * @param effectiveDate
     */
    public void setEffectiveDate(EDITDate effectiveDate)
    {
        this.effectiveDate = effectiveDate;
    }

    /**
     * Getter.
     * @return
     */
    public EDITDate getTerminationDate()
    {
        return terminationDate;
    }

    /**
     * Setter.
     * @param terminationDate
     */
    public void setTerminationDate(EDITDate terminationDate)
    {
        this.terminationDate = terminationDate;
    }

    /**
     * Getter.
     * @return
     */
    public String getStatus()
    {
        return status;
    }

    /**
     * Setter.
     * @param status
     */
    public void setStatus(String status)
    {
        this.status = status;
    }

    /**
     * Getter.
     * @return
     */
    public String getSortOption()
    {
        return sortOption;
    }

    /**
     * Setter.
     * @param sortOption
     */
    public void setSortOption(String sortOption)
    {
        this.sortOption = sortOption;
    }

    /**
     * Getter.
     * @return
     */
    public String getBillingConsolidation()
    {
        return billingConsolidation;
    }

    /**
     * Setter.
     * @param billingConsolidation
     */
    public void setBillingConsolidation(String billingConsolidation)
    {
        this.billingConsolidation = billingConsolidation;
    }

    /**
     * Getter.
     * @return
     */
    public String getSocialSecurityMask()
    {
        return socialSecurityMask;
    }

    /**
     * Setter.
     * @param socialSecurityMask
     */
    public void setSocialSecurityMask(String socialSecurityMask)
    {
        this.socialSecurityMask = socialSecurityMask;
    }

    /**
     * Getter.
     * @return
     */
    public int getNumberOfCopiesGroup()
    {
        return numberOfCopiesGroup;
    }

    /**
     * Setter.
     * @param numberOfCopiesGroup
     */
    public void setNumberOfCopiesGroup(int numberOfCopiesGroup)
    {
        this.numberOfCopiesGroup = numberOfCopiesGroup;
    }

    /**
     * Getter.
     * @return
     */
    public int getNumberOfCopiesAgent()
    {
        return numberOfCopiesAgent;
    }

    /**
     * Setter.
     * @param numberOfCopiesAgent
     */
    public void setNumberOfCopiesAgent(int numberOfCopiesAgent)
    {
        this.numberOfCopiesAgent = numberOfCopiesAgent;
    }

    /**
     * Getter.
     * @return
     */
    public String getBillingMode()
    {
        return billingMode;
    }

    /**
     * Setter.
     * @param billingMode
     */
    public void setBillingMode(String billingMode)
    {
        this.billingMode = billingMode;
    }

    /**
     * Getter.
     * @return
     */
    public String getBillType()
    {
        return billType;
    }

    /**
     * Setter.
     * @param billType
     */
    public void setBillType(String billType)
    {
        this.billType = billType;
    }

    /**
     * Getter.
     * @return
     */
    public String getCreationOperator()
    {
        return creationOperator;
    }

    /**
     * Setter.
     * @param creationOperator
     */
    public void setCreationOperator(String creationOperator)
    {
        this.creationOperator = creationOperator;
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
    public String getRepName()
    {
        return repName;
    }

    /**
     * Setter.
     * @param repName
     */
    public void setRepName(String repName)
    {
        this.repName = repName;
    }

    /**
     * Getter.
     * @return
     */
    public String getRepPhoneNumber()
    {
        return repPhoneNumber;
    }

    /**
     * Setter.
     * @param repPhoneNumber
     */
    public void setRepPhoneNumber(String repPhoneNumber)
    {
        this.repPhoneNumber = repPhoneNumber;
    }

    /**
     * Getter.
     * @return
     */
    public String getBillMethod()
    {
        return billMethod;
    }

    /**
     * Setter.
     * @param billMethod
     */
    public void setBillMethod(String billMethod)
    {
        this.billMethod = billMethod;
    }

    /**
     * Getter.
     * @return
     */
    public String getSkipMonthStart1()
    {
        return skipMonthStart1;
    }

    /**
     * Setter.
     * @param skipMonthStart1CT
     */
    public void setSkipMonthStart1(String skipMonthStart1)
    {
        this.skipMonthStart1 = skipMonthStart1;
    }

    /**
     * Getter.
     * @return
     */
    public String getSkipNumberOfMonths1()
    {
        return skipNumberOfMonths1;
    }

    /**
     * Setter.
     * @param skipNumberOfMonths1
     */
    public void setSkipNumberOfMonths1(String skipNumberOfMonths1)
    {
        this.skipNumberOfMonths1 = skipNumberOfMonths1;
    }

    /**
     * Getter.
     * @return
     */
    public String getSkipMonthStart2()
    {
        return skipMonthStart2;
    }

    /**
     * Setter.
     * @param skipMonthStart2
     */
    public void setSkipMonthStart2(String skipMonthStart2)
    {
        this.skipMonthStart2 = skipMonthStart2;
    }

    /**
     * Getter.
     * @return
     */
    public String getSkipNumberOfMonths2()
    {
        return skipNumberOfMonths2;
    }

    /**
     * Setter.
     * @param skipNumberOfMonths2
     */
    public void setSkipNumberOfMonths2(String skipNumberOfMonths2)
    {
        this.skipNumberOfMonths2 = skipNumberOfMonths2;
    }

    /**
     * Getter.
     * @return
     */
    public String getSkipMonthStart3()
    {
        return skipMonthStart3;
    }

    /**
     * Setter.
     * @param skipMonthStart3
     */
    public void setSkipMonthStart3(String skipMonthStart3)
    {
        this.skipMonthStart3 = skipMonthStart3;
    }

    /**
     * Getter.
     * @return
     */
    public String getSkipNumberOfMonths3()
    {
        return skipNumberOfMonths3;
    }

    /**
     * Setter.
     * @param skipNumberOfMonths3
     */
    public void setSkipNumberOfMonths3(String skipNumberOfMonths3)
    {
        this.skipNumberOfMonths3 = skipNumberOfMonths3;
    }

    /**
     * Getter.
     * @return
     */
    public EDITDate getNextBillExtractDate()
    {
        return nextBillExtractDate;
    }

    /**
     * Setter.
     * @param nextBillExtractDate
     */
    public void setNextBillExtractDate(EDITDate nextBillExtractDate)
    {
        this.nextBillExtractDate = nextBillExtractDate;
    }

    /**
     * Getter.
     * @return
     */
    public EDITDate getNextBillDueDate()
    {
        return nextBillDueDate;
    }

    /**
     * Setter.
     * @param nextBillDueDate
     */
    public void setNextBillDueDate(EDITDate nextBillDueDate)
    {
        this.nextBillDueDate = nextBillDueDate;
    }

    /**
     * Getter.
     * @return
     */
    public EDITDate getLastBillDueDate()
    {
        return lastBillDueDate;
    }

    /**
     * Setter.
     * @param lastBillDueDate
     */
    public void setLastBillDueDate(EDITDate lastBillDueDate)
    {
        this.lastBillDueDate = lastBillDueDate;
    }

    /**
     * Getter.
     * @return
     */
    public EDITDate getFirstDeductionDate()
    {
        return firstDeductionDate;
    }

    /**
     * Setter.
     * @param firstDeductionDate
     */
    public void setFirstDeductionDate(EDITDate firstDeductionDate)
    {
        this.firstDeductionDate = firstDeductionDate;
    }

    /**
     * Getter.
     * @return
     */
    public EDITDate getLastDeductionDate()
    {
        return lastDeductionDate;
    }

    /**
     * Setter.
     * @param lastDeductionDate
     */
    public void setLastDeductionDate(EDITDate lastDeductionDate)
    {
        this.lastDeductionDate = lastDeductionDate;
    }

    /**
     * Getter.
     * @return
     */
    public String getDeductionFrequency()
    {
        return deductionFrequency;
    }

    /**
     * Setter.
     * @param deductionFrequency
     */
    public void setDeductionFrequency(String deductionFrequency)
    {
        this.deductionFrequency = deductionFrequency;
    }

    /**
     * Getter.
     * @return
     */
    public int getEFTDraftDay()
    {
        return eftDraftDay;
    }

    /**
     * Setter.
     * @param eftDraftDay
     */
    public void setEFTDraftDay(int eftDraftDay)
    {
        this.eftDraftDay = eftDraftDay;
    }

    /**
     * Getter.
     * @return
     */
    public String getEFTDraftDayStartMonth()
    {
        return eftDraftDayStartMonth;
    }

    /**
     * Setter.
     * @param eftDraftDayStartMonth
     */
    public void setEFTDraftDayStartMonth(String eftDraftDayStartMonth)
    {
        this.eftDraftDayStartMonth = eftDraftDayStartMonth;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getRequiredPremiumAmount()
    {
        return requiredPremiumAmount;
    }

    /**
     * Setter.
     * @param requiredPremiumAmount
     */
    public void setRequiredPremiumAmount(EDITBigDecimal requiredPremiumAmount)
    {
        this.requiredPremiumAmount = requiredPremiumAmount;
    }

    /**
     * Getter.
     * @return
     */
    public EDITDate getTransitionPeriodEndDate()
    {
        return transitionPeriodEndDate;
    }

    /**
     * Setter.
     * @param transitionPeriodEndDate
     */
    public void setTransitionPeriodEndDate(EDITDate transitionPeriodEndDate)
    {
        this.transitionPeriodEndDate = transitionPeriodEndDate;
    }

    /**
     * Getter.
     * @return
     */
    public Long getESBillScheduleKey()
    {
        return this.esBillScheduleKey;
    }

    /**
     * Setter.
     * @param esBillScheduleKey
     */
    public void setESBillScheduleKey(Long esBillScheduleKey)
    {
        this.esBillScheduleKey = esBillScheduleKey;
    }

    /**
     * Getter.
     * @return
     */
    public Set<SegmentBase> getSegmentBases()
    {
        return segmentBases;
    }

    /**
     * Setter.
     * @param segmentBases
     */
    public void setSegmentBases(Set<SegmentBase> segmentBases)
    {
        this.segmentBases = segmentBases;
    }

    public void addSegmentBase(SegmentBase segmentBase)
    {
        this.segmentBases.add(segmentBase);

        segmentBase.setBillSchedule(this);
    }

    public void removeSegmentBase(SegmentBase segmentBase)
    {
        this.segmentBases.remove(segmentBase);

        segmentBase.setBillSchedule(null);
    }

    /**
     * Getter.
     * @return
     */
    public Set<Group> getGroups()
    {
        return groups;
    }

    /**
     * Setter.
     * @param groups
     */
    public void setGroups(Set<Group> groups)
    {
        this.groups = groups;
    }

    public void addGroup(Group group)
    {
        this.groups.add(group);

        group.setBillSchedule(this);

        SessionHelper.saveOrUpdate(group, BillSchedule.DATABASE);
    }

    public void removeGroup(Group group)
    {
        this.groups.remove(group);

        group.setBillSchedule(null);
    }

    /**
     * Getter.
     * @return
     */
    public Set<BillGroup> getBillGroups()
    {
        return billGroups;
    }

    /**
     * Setter.
     * @param billGroups
     */
    public void setBillGroups(Set<BillGroup> billGroups)
    {
        this.billGroups = billGroups;
    }

    public void addBillGroup(BillGroup billGroup)
    {
        this.billGroups.add(billGroup);

        billGroup.setBillSchedule(this);

        SessionHelper.saveOrUpdate(billGroup, BillSchedule.DATABASE);
    }

    public void removeBillGroup(BillGroup billGroup)
    {
        this.billGroups.remove(billGroup);

        billGroup.setBillSchedule(null);
    }

    public String getDatabase()
    {
        return BillSchedule.DATABASE;
    }

    public static BillSchedule findByStagingFK(Long stagingFK, Long esBillScheduleKey, String database)
    {
        String hql = " select billSchedule from BillSchedule billSchedule" +
                    " where billSchedule.StagingFK = :stagingFK" +
                    " and billSchedule.ESBillScheduleKey = :esBillScheduleKey";

        EDITMap params = new EDITMap();
        params.put("stagingFK", stagingFK);
        params.put("esBillScheduleKey", esBillScheduleKey);

        List<BillSchedule> results = SessionHelper.executeHQL(hql, params, database);

        if (results.size() > 0)
        {
            return (BillSchedule) results.get(0);
        }
        else
        {
            return null;
        }
    }
}

