/*
 * User: sprasad
 * Date: Jun 2, 2005
 * Time: 2:58:13 PM
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package casetracking;

import client.*;

import edit.common.*;
import edit.services.db.hibernate.*;

import edit.common.*;

import java.util.*;
import java.math.*;

public class CasetrackingLog extends HibernateEntity
{
    private Long casetrackingLogPK;
    private String caseTrackingProcess;
    private String caseTrackingEvent;
    private String contractNumber;
    private EDITDate effectiveDate;
    private EDITDate processDate;
    private String clientName;
    private BigDecimal allocationPercent;
    private String newContractNumber;
    private String operator;
    private Long clientDetailFK;
    private ClientDetail clientDetail;

    /**
     * Target database to be used for lookups, etc.
     */
    public static String DATABASE = SessionHelper.EDITSOLUTIONS;


    /**
     * Default Constructor
     */
    public CasetrackingLog()
    {
    }

    /**
     * Constructor.
     * @param clientDetail
     * @param filteredRequirement
     */
    public CasetrackingLog(ClientDetail clientDetail)
    {
        this.clientDetail = clientDetail;
    }

    public Long getClientDetailFK()
    {
        return clientDetailFK;
    }

    public void setClientDetailFK(Long clientDetailFK)
    {
        this.clientDetailFK = clientDetailFK;
    }

    /**
     * Getter.
     * @return
     */
    public Long getCasetrackingLogPK()
    {
        return casetrackingLogPK;
    }

    /**
     * Setter.
     * @param casetrackingLogPK
     */
    public void setCasetrackingLogPK(Long casetrackingLogPK)
    {
        this.casetrackingLogPK = casetrackingLogPK;
    }

    /**
     * Getter.
     * @return
     */
    public String getCaseTrackingProcess()
    {
        return caseTrackingProcess;
    }

    /**
     * Setter.
     * @param caseTrackingProcess
     */
    public void setCaseTrackingProcess(String caseTrackingProcess)
    {
        this.caseTrackingProcess = caseTrackingProcess;
    }

    /**
     * Getter.
     * @return
     */
    public String getCaseTrackingEvent()
    {
        return caseTrackingEvent;
    }

    /**
     * Setter.
     * @param caseTrackingEvent
     */
    public void setCaseTrackingEvent(String caseTrackingEvent)
    {
        this.caseTrackingEvent = caseTrackingEvent;
    }

    /**
      * Getter.
      * @return
      */
    public String getContractNumber()
    {
        return contractNumber;
    }

    /**
     * Setter.
     * @param contractNumber
     */
    public void setContractNumber(String contractNumber)
    {
        this.contractNumber = contractNumber;
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
    public EDITDate getProcessDate()
    {
        return processDate;
    }

    /**
     * Setter.
     * @param processDate
     */
    public void setProcessDate(EDITDate processDate)
    {
        this.processDate = processDate;
    }

    /**
        * Getter.
        * @return
        */
    public String getClientName()
    {
        return clientName;
    }

    /**
     * Setter.
     * @param newClientName
     */
    public void setClientName(String clientName)
    {
        this.clientName = clientName;
    }

    /**
      * Getter.
      * @return
      */
    public EDITBigDecimal getAllocationPercent()
    {
        return SessionHelper.getEDITBigDecimal(allocationPercent);
    }

    /**
      * Setter.
      * @param allocationPercent
      */
    public void setAllocationPercent(EDITBigDecimal allocationPercent)
    {
        this.allocationPercent = (SessionHelper.getEDITBigDecimal(allocationPercent));
    }

    /**
       * Getter.
       * @return
       */
    public String getNewContractNumber()
    {
        return newContractNumber;
    }

    /**
     * Setter.
     * @param newContractNumber
     */
    public void setNewContractNumber(String newContractNumber)
    {
        this.newContractNumber = newContractNumber;
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
    public ClientDetail getClientDetail()
    {
        return clientDetail;
    }

    /**
     * Setter.
     * @param clientDetail
     */
    public void setClientDetail(ClientDetail clientDetail)
    {
        this.clientDetail = clientDetail;
    }

    /************************************** Static Methods **************************************/
    /**
     * Finder by PK.
     * @param casetrackingLogPK
     * @return
     */
    public static final CasetrackingLog findByPK(Long casetrackingLogPK)
    {
        return (CasetrackingLog) SessionHelper.get(CasetrackingLog.class, casetrackingLogPK, CasetrackingLog.DATABASE);
    }

    /**
     * Finder.
     * @param clientDetail
     * @return
     */
    public static final CasetrackingLog[] findBy_ClientDetail(ClientDetail clientDetail)
    {
        String hql = "select cl from CasetrackingLog cl where cl.ClientDetail = :clientDetail";

        Map params = new HashMap();

        params.put("clientDetail", clientDetail);

        List results = SessionHelper.executeHQL(hql, params, CasetrackingLog.DATABASE);

        return (CasetrackingLog[]) results.toArray(new CasetrackingLog[results.size()]);
    }

    /**
     * Save the entity using Hibernate
     */
    public void hSave()
    {
        SessionHelper.saveOrUpdate(this, CasetrackingLog.DATABASE);
    }

    /**
     * Delete the entity using Hibernate
     */
    public void hDelete()
    {
        SessionHelper.delete(this, CasetrackingLog.DATABASE);
    }

    public void onCreate()
    {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Gets the database this object belongs to
     *
     * @return  string containing the hibernate database name for this object
     */
    public String getDatabase()
    {
        return CasetrackingLog.DATABASE;
    }
}
