/*
 * User: dlataill
 * Date: Oct 9, 2007
 * Time: 12:26:59 PM
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package staging;

import edit.common.*;
import edit.services.db.hibernate.*;

public class CaseStatusHistory extends HibernateEntity
{
    private Long caseStatusHistoryPK;
    private Long caseFK;
    private EDITDate effectiveDate;
    private String status;
    private String operator;
    private EDITDateTime maintDateTime;
    private String priorStatus;

    private Case stagingCase;

    public static final String DATABASE = SessionHelper.STAGING;

    public CaseStatusHistory()
    {
    }

    /**
     * Getter.
     * @return
     */
    public Long getCaseStatusHistoryPK()
    {
        return caseStatusHistoryPK;
    }

    /**
     * Setter.
     * @param caseStatusHistoryPK
     */
    public void setCaseStatusHistoryPK(Long caseStatusHistoryPK)
    {
        this.caseStatusHistoryPK = caseStatusHistoryPK;
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
     * Getter.
     * @return
     */
    public Case getStagingCase()
    {
        return stagingCase;
    }

    /**
     * Setter.
     * @param stagingCase
     */
    public void setStagingCase(Case stagingCase)
    {
        this.stagingCase = stagingCase;
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
    public String getOperator()
    {
        return operator;
    }

    /**
     * Setter.
     * @param operator
     */
    public void setOperator(String operator)
    {
        this.operator = operator;
    }

    /**
     * Getter.
     * @return
     */
    public EDITDateTime getMaintDateTime()
    {
        return maintDateTime;
    }

    /**
     * Setter.
     * @param maintDateTime
     */
    public void setMaintDateTime(EDITDateTime maintDateTime)
    {
        this.maintDateTime = maintDateTime;
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
        return CaseStatusHistory.DATABASE;
    }
}
