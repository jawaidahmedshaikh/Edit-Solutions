/**
 * User: dlataill
 * Date: Jul 18, 2007
 * Time: 12:33:30 PM
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement
 */
package group;

import edit.services.db.hibernate.HibernateEntity;
import edit.services.db.hibernate.SessionHelper;
import edit.common.EDITDate;
import edit.common.EDITMap;
import edit.common.EDITDateTime;

import java.util.List;
import java.util.Set;
import java.util.Iterator;

import staging.IStaging;
import staging.StagingContext;
import client.ClientDetail;
import client.ClientAddress;


public class CaseStatusChangeHistory extends HibernateEntity implements IStaging
{
    private Long caseStatusChangeHistoryPK;
    private Long contractGroupFK;
    private ContractGroup contractGroup;
    private String statusCT;
    private EDITDate changeEffectiveDate;
    private String operator;
    private EDITDateTime maintDateTime;
    private String priorStatusCT;

    public static final String DATABASE = SessionHelper.EDITSOLUTIONS;


    public CaseStatusChangeHistory()
    {
    }

    /**
     * Get CaseStatusChangeHistoryPK
     * @return
     */
    public Long getCaseStatusChangeHistoryPK()
    {
        return caseStatusChangeHistoryPK;
    }

    /**
     * Set CaseStatusChangeHistoryPK
     * @param caseStatusChangeHistoryPK the PK
     */
    public void setCaseStatusChangeHistoryPK(Long caseStatusChangeHistoryPK)
    {
        this.caseStatusChangeHistoryPK = caseStatusChangeHistoryPK;
    }

    /**
     * Get ContractGroupFK
     * @return
     */
    public Long getContractGroupFK()
    {
        return contractGroupFK;
    }

    /**
     * Set ContractGroupFK
     * @param contractGroupFK
     */
    public void setContractGroupFK(Long contractGroupFK)
    {
        this.contractGroupFK = contractGroupFK;
    }

    /**
     * Get the ContractGroup (the parent of PayrollDeductionSchedule)
     * @return
     */
    public ContractGroup getContractGroup()
    {
        return contractGroup;
    }

    /**
     * Set ContractGroup (the parent of PayrollDeductionSchedule)
     * @param contractGroup
     */
    public void setContractGroup(ContractGroup contractGroup)
    {
        this.contractGroup = contractGroup;
    }

    /**
     * Get StatusCT
     * @return
     */
    public String getStatusCT()
    {
        return statusCT;
    }

    /**
     * Set StatusCT
     * @param statusCT
     */
    public void setStatusCT(String statusCT)
    {
        this.statusCT = statusCT;
    }

    /**
     * Get ChangeEffectiveDate
     * @return
     */
    public EDITDate getChangeEffectiveDate()
    {
        return changeEffectiveDate;
    }

    /**
     * Set ChangeEffectiveDate
     * @param changeEffectiveDate
     */
    public void setChangeEffectiveDate(EDITDate changeEffectiveDate)
    {
        this.changeEffectiveDate = changeEffectiveDate;
    }

    /**
     * Get Operator
     * @return
     */
    public String getOperator()
    {
        return operator;
    }

    /**
     * Set Operator
     * @param operator
     */
    public void setOperator(String operator)
    {
        this.operator = operator;
    }

    /**
     * Get MaintDateTime
     * @return
     */
    public EDITDateTime getMaintDateTime()
    {
        return maintDateTime;
    }

    /**
     * Set MaintDateTime
     * @param maintDateTime
     */
    public void setMaintDateTime(EDITDateTime maintDateTime)
    {
        this.maintDateTime = maintDateTime;
    }

    /**
     * Get PriorStatusCT
     * @return
     */
    public String getPriorStatusCT()
    {
        return priorStatusCT;
    }

    /**
     * Set PriorStatusCT
     * @param priorStatusCT
     */
    public void setPriorStatusCT(String priorStatusCT)
    {
        this.priorStatusCT = priorStatusCT;
    }

    /**
     * Finder.
     * @param contractGroupFK
     * @return
     */
    public static CaseStatusChangeHistory findByCaseStatusChangeHistoryPK(Long caseStatusChangeHistoryPK)
    {
        return (CaseStatusChangeHistory) SessionHelper.get(CaseStatusChangeHistory.class, caseStatusChangeHistoryPK, CaseStatusChangeHistory.DATABASE);
    }

    /**
     * Finder.
     * @param contractGroup
     * @return
     */
    public static CaseStatusChangeHistory[] findByContractGroup(Long contractGroupFK)
    {
        String hql = " from CaseStatusChangeHistory caseStatusChangeHistory" +
                    " where caseStatusChangeHistory.ContractGroupFK = :contractGroupFK";

        EDITMap params = new EDITMap("contractGroupFK", contractGroupFK);

        List<CaseStatusChangeHistory> results = SessionHelper.executeHQL(hql, params, CaseStatusChangeHistory.DATABASE);

        return (CaseStatusChangeHistory[]) results.toArray(new CaseStatusChangeHistory[results.size()]);
    }

    public String getDatabase()
    {
        return CaseStatusChangeHistory.DATABASE;
    }

    public static void buildHistoryRecord(ContractGroup contractGroup, EDITDate changeEffectiveDate,
                                          String origCaseStatus, String operator)
    {
        CaseStatusChangeHistory caseStatusChangeHistory = new CaseStatusChangeHistory();
        caseStatusChangeHistory.setStatusCT(contractGroup.getCaseStatusCT());
        caseStatusChangeHistory.setPriorStatusCT(origCaseStatus);
        caseStatusChangeHistory.setChangeEffectiveDate(changeEffectiveDate);
        caseStatusChangeHistory.setOperator(operator);
        caseStatusChangeHistory.setMaintDateTime(new EDITDateTime());

        contractGroup.addCaseStatusChangeHistory(caseStatusChangeHistory);
    }

    public StagingContext stage(StagingContext stagingContext, String database)
    {
        staging.CaseStatusHistory caseStatusHistory = new staging.CaseStatusHistory();

        caseStatusHistory.setStagingCase(stagingContext.getCurrentCase());
        caseStatusHistory.setEffectiveDate(this.getChangeEffectiveDate());
        caseStatusHistory.setStatus(this.getStatusCT());
        caseStatusHistory.setOperator(this.getOperator());
        caseStatusHistory.setMaintDateTime(this.getMaintDateTime());
        caseStatusHistory.setPriorStatus(this.priorStatusCT);

        stagingContext.getCurrentCase().addCaseStatusHistory(caseStatusHistory);

        SessionHelper.saveOrUpdate(caseStatusHistory, database);

        return stagingContext;
    }
}
