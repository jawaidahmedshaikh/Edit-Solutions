/*
 * User: dlataille
 * Date: Oct 9, 2007
 * Time: 8:14:44 AM

 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */

package staging;

import edit.services.db.hibernate.HibernateEntity;
import edit.services.db.hibernate.SessionHelper;
import edit.common.EDITDate;

public class Requirement extends HibernateEntity
{
    private Long requirementPK;
    private Long segmentBaseFK;
    private String requirementDescription;
    private String requirementStatus;
    private EDITDate effectiveDate;
    private EDITDate followupDate;
    private EDITDate receivedDate;
    private String freeFormReqDescription;

    private SegmentBase segmentBase;

    /**
     * Target database to be used for lookups, etc.
     */
    public static String DATABASE = SessionHelper.STAGING;

    public Requirement()
    {

    }

    /**
     * Getter.
     * @return
     */
    public Long getRequirementPK()
    {
        return requirementPK;
    }

    /**
     * Setter.
     * @param requirementPK
     */
    public void setRequirementPK(Long requirementPK)
    {
        this.requirementPK = requirementPK;
    }

    /**
     * Getter.
     * @return
     */
    public Long getSegmentBaseFK()
    {
        return segmentBaseFK;
    }

    /**
     * Setter.
     * @param segmentBaseFK
     */
    public void setSegmentBaseFK(Long segmentBaseFK)
    {
        this.segmentBaseFK = segmentBaseFK;
    }

    /**
     * Getter.
     * @return
     */
    public SegmentBase getSegmentBase()
    {
        return segmentBase;
    }

    /**
     * Setter.
     * @param segmentBase
     */
    public void setSegmentBase(SegmentBase segmentBase)
    {
        this.segmentBase = segmentBase;
    }

    /**
     * Getter.
     * @return
     */
    public String getRequirementDescription()
    {
        return requirementDescription;
    }

    /**
     * Setter.
     * @param requirementDescription
     */
    public void setRequirementDescription(String requirementDescription)
    {
        this.requirementDescription = requirementDescription;
    }

    /**
     * Getter.
     * @return
     */
    public String getRequirementStatus()
    {
        return requirementStatus;
    }

    /**
     * Setter.
     * @param requirementStatus
     */
    public void setRequirementStatus(String requirementStatus)
    {
        this.requirementStatus = requirementStatus;
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
    public EDITDate getFollowupDate()
    {
        return followupDate;
    }

    /**
     * Setter.
     * @param followupDate
     */
    public void setFollowupDate(EDITDate followupDate)
    {
        this.followupDate = followupDate;
    }

    /**
     * Getter.
     * @return
     */
    public EDITDate getReceivedDate()
    {
        return receivedDate;
    }

    /**
     * Setter.
     * @param receivedDate
     */
    public void setReceivedDate(EDITDate receivedDate)
    {
        this.receivedDate = receivedDate;
    }

    /**
     * Getter.
     * @return
     */
    public String getFreeFormReqDescription()
    {
        return freeFormReqDescription;
    }

    /**
     * Setter.
     * @param freeFormReqDescription
     */
    public void setFreeFormReqDescription(String freeFormReqDescription)
    {
        this.freeFormReqDescription = freeFormReqDescription;
    }

    public String getDatabase()
    {
        return Requirement.DATABASE;
    }
}


