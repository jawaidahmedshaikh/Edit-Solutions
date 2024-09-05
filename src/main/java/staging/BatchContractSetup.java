/*
 * User: dlataill
 * Date: Oct 9, 2007
 * Time: 10:48:46 AM
 *
 * (c) 2000-2008 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement. 
 */

package staging;

import edit.common.*;
import edit.services.db.hibernate.*;

import java.util.List;
import java.util.Set;
import java.util.HashSet;

public class BatchContractSetup extends HibernateEntity
{
    /**
     * Primary key
     */
    private Long batchContractSetupPK;
    private Long groupFK;
    private Long enrollmentFK;
    private String batchID;
    private String status;
    private int numberOfContracts;
    private EDITDate receiptDate;
    private int numberOfWorkdays;
    private String autoNumber;
    private String applicationType;
    private String batchEvent;
    private EDITDate effectiveDate;
    private EDITDate applicationSignedDate;
    private String applicationSignedState;
    private String issueState;
    private String deathBenefitOption;
    private String nonForfeitureOption;
    private String creationOperator;
    private EDITDate creationDate;
    private String enrollmentMethod;
    private EDITBigDecimal advancePercent;
    private EDITDate applicationReceivedDate;
    private EDITBigDecimal recoveryPercent;

    private Set<SegmentBase> segmentBases;

    //  Parents
    private Group group;
    private Enrollment enrollment;

    /**
     * Target database for lookups
     */
    public static final String DATABASE = SessionHelper.STAGING;

    /**
     * Constructor
     */
    public BatchContractSetup()
    {
        this.segmentBases = new HashSet<SegmentBase>();
    }

    /**
     * Getter.
     * @return
     */
    public Long getBatchContractSetupPK()
    {
        return batchContractSetupPK;
    }

    /**
     * Setter.
     * @param batchContractSetupPK
     */
    public void setBatchContractSetupPK(Long batchContractSetupPK)
    {
        this.batchContractSetupPK = batchContractSetupPK;
    }

    /**
     * Getter.
     * @return
     */
    public Long getGroupFK()
    {
        return groupFK;
    }

    /**
     * Setter.
     * @param groupFK
     */
    public void setGroupFK(Long groupFK)
    {
        this.groupFK = groupFK;
    }

    /**
     * Getter.
     * @return
     */
    public Group getGroup()
    {
        return group;
    }

    /**
     * Setter.
     * @param group
     */
    public void setGroup(Group group)
    {
        this.group = group;
    }

    /**
     * Getter.
     * @return
     */
    public Long getEnrollmentFK()
    {
        return this.enrollmentFK;
    }

    /**
     * Setter.
     * @param enrollmentFK
     */
    public void setEnrollmentFK(Long enrollmentFK)
    {
        this.enrollmentFK = enrollmentFK;
    }

    /**
     * Getter.
     * @return
     */
    public Enrollment getEnrollment()
    {
        return enrollment;
    }

    /**
     * Setter.
     * @param enrollment
     */
    public void setEnrollment(Enrollment enrollment)
    {
        this.enrollment = enrollment;
    }

    /**
     * Getter.
     * @return
     */
    public String getBatchID()
    {
        return batchID;
    }

    /**
     * Setter.
     * @param batchID
     */
    public void setBatchID(String batchID)
    {
        this.batchID = batchID;
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
    public int getNumberOfContracts()
    {
        return this.numberOfContracts;
    }

    /**
     * Setter.
     * @param numberOfContracts
     */
    public void setNumberOfContracts(int numberOfContracts)
    {
        this.numberOfContracts = numberOfContracts;
    }

    /**
     * Getter.
     * @return
     */
    public EDITDate getReceiptDate()
    {
        return receiptDate;
    }

    /**
     * Setter.
     * @param receiptDate
     */
    public void setReceiptDate(EDITDate receiptDate)
    {
        this.receiptDate = receiptDate;
    }

    /**
     * Getter.
     * @return
     */
    public int getNumberOfWorkdays()
    {
        return numberOfWorkdays;
    }

    /**
     * Setter.
     * @param numberOfWorkdays
     */
    public void setNumberOfWorkdays(int numberOfWorkdays)
    {
        this.numberOfWorkdays = numberOfWorkdays;
    }

    /**
     * Getter.
     * @return
     */
    public String getAutoNumber()
    {
        return autoNumber;
    }

    /**
     * Setter.
     * @param autoNumber
     */
    public void setAutoNumber(String autoNumber)
    {
        this.autoNumber = autoNumber;
    }

    /**
     * Getter.
     * @return
     */
    public String getApplicationType()
    {
        return applicationType;
    }

    /**
     * Setter.
     * @param applicationType
     */
    public void setApplicationType(String applicationType)
    {
        this.applicationType = applicationType;
    }

    /**
     * Getter.
     * @return
     */
    public String getBatchEvent()
    {
        return batchEvent;
    }

    /**
     * Setter.
     * @param batchEvent
     */
    public void setBatchEvent(String batchEvent)
    {
        this.batchEvent = batchEvent;
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
    public EDITDate getApplicationSignedDate()
    {
        return applicationSignedDate;
    }

    /**
     * Setter.
     * @param applicationSignedDate
     */
    public void setApplicationSignedDate(EDITDate applicationSignedDate)
    {
        this.applicationSignedDate = applicationSignedDate;
    }

    /**
     * Getter.
     * @return
     */
    public String getApplicationSignedState()
    {
        return applicationSignedState;
    }

    /**
     * Setter.
     * @param applicationSignedState
     */
    public void setApplicationSignedState(String applicationSignedState)
    {
        this.applicationSignedState = applicationSignedState;
    }

    /**
     * Getter.
     * @return
     */
    public String getIssueState()
    {
        return issueState;
    }

    /**
     * Setter.
     * @param issueState
     */
    public void setIssueState(String issueState)
    {
        this.issueState = issueState;
    }

    /**
     * Getter.
     * @return
     */
    public String getDeathBenefitOption()
    {
        return deathBenefitOption;
    }

    /**
     * Setter.
     * @param deathBenefitOption
     */
    public void setDeathBenefitOption(String deathBenefitOption)
    {
        this.deathBenefitOption = deathBenefitOption;
    }

    /**
     * Getter.
     * @return
     */
    public String getNonForfeitureOption()
    {
        return nonForfeitureOption;
    }

    /**
     * Setter.
     * @param nonForfeitureOption
     */
    public void setNonForfeitureOption(String nonForfeitureOption)
    {
        this.nonForfeitureOption = nonForfeitureOption;
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
    public String getEnrollmentMethod()
    {
        return enrollmentMethod;
    }

    /**
     * Setter.
     * @param enrollmentMethod
     */
    public void setEnrollmentMethod(String enrollmentMethod)
    {
        this.enrollmentMethod = enrollmentMethod;
    }

    public String getDatabase()
    {
        return BatchContractSetup.DATABASE;
    }
    
    /**
     * Setter.
     * @param advancePercent
     */
    public void setAdvancePercent(EDITBigDecimal advancePercent)
    {
        this.advancePercent = advancePercent;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getAdvancePercent()
    {
        return advancePercent;
    }

    /**
     * Setter.
     * @param applicationReceivedDate
     */
    public void setApplicationReceivedDate(EDITDate applicationReceivedDate)
    {
        this.applicationReceivedDate = applicationReceivedDate;
    }

    /**
     * Getter.
     * @return
     */
    public EDITDate getApplicationReceivedDate()
    {
        return applicationReceivedDate;
    }

    /**
     * Setter.
     * @param recoveryPercent
     */
    public void setRecoveryPercent(EDITBigDecimal recoveryPercent)
    {
        this.recoveryPercent = recoveryPercent;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getRecoveryPercent()
    {
        return recoveryPercent;
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

    /**
     * Add another segmentBase to the current mapped segmentBases.
     * @param segmentBase
     */
    public void addSegmentBase(SegmentBase segmentBase)
    {
        this.segmentBases.add(segmentBase);
    }

    public static BatchContractSetup findByGroupFK_BatchID(Long groupFK, String batchID, String database)
    {
        String hql = " select bcs from BatchContractSetup bcs" +
                    " where bcs.GroupFK = :groupFK" +
                    " and bcs.BatchID = :batchID";

        EDITMap params = new EDITMap();
        params.put("groupFK", groupFK);
        params.put("batchID", batchID);

        List<BatchContractSetup> results = SessionHelper.executeHQL(hql, params, database);

        if (results.size() > 0)
        {
            return (BatchContractSetup) results.get(0);
        }
        else
        {
            return null;
        }
    }
}
