/*
 * User: dlataill
 * Date: Oct 9, 2007
 * Time: 11:52:59 AM
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package staging;

import edit.common.*;
import edit.services.db.hibernate.*;

public class ContractStatusHistory extends HibernateEntity
{
    private Long contractStatusHistoryPK;
    private Long segmentBaseFK;
    private EDITDateTime processDate;
    private EDITDate effectiveDate;
    private EDITDate endDate;
    private String status;
    private String priorStatus;

    private SegmentBase segmentBase;

    public static final String DATABASE = SessionHelper.STAGING;

    public ContractStatusHistory()
    {
    }

    /**
     * Getter.
     * @return
     */
    public Long getContractStatusHistoryPK()
    {
        return contractStatusHistoryPK;
    }

    /**
     * Setter.
     * @param contractStatusHistoryPK
     */
    public void setContractStatusHistoryPK(Long contractStatusHistoryPK)
    {
        this.contractStatusHistoryPK = contractStatusHistoryPK;
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
    public EDITDateTime getProcessDate()
    {
        return processDate;
    }

    /**
     * Setter.
     * @param processDate
     */
    public void setProcessDate(EDITDateTime processDate)
    {
        this.processDate = processDate;
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
    public EDITDate getEndDate()
    {
        return endDate;
    }

    /**
     * Setter.
     * @param endDate
     */
    public void setEndDate(EDITDate endDate)
    {
        this.endDate = endDate;
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
    public String getPriorStatus()
    {
        return priorStatus;
    }

    /**
     * Setter.
     * @param priorStatus
     */
    public void setPriorStatus(String priorStatus)
    {
        this.priorStatus = priorStatus;
    }

    public String getDatabase()
    {
        return ContractStatusHistory.DATABASE;
    }
}
