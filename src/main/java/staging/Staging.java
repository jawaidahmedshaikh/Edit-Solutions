/*
 * User: dlataille
 * Date: Oct 1, 2007
 * Time: 3:49:59 PM
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package staging;

import edit.common.*;
import edit.services.db.hibernate.*;

import java.util.Set;
import java.util.List;
import java.util.HashSet;

import fission.utility.DateTimeUtil;

public class Staging extends HibernateEntity implements IStaging
{
    private Long stagingPK;
    private EDITDateTime stagingDate;
    private String eventType;
    private String caseNumber;
    private String groupNumber;
    private String contractNumber;
    private String correspondenceType;
    private int recordCount;
    private EDITBigDecimal controlTotal;
    private String companyName;
    private EDITDateTime runDate;
    
    private Set<Case> cases;
    private Set<SegmentBase> segmentBases;
    private Set<PayrollDeductionSchedule> payrollDeductionSchedules;
    private Set<BillSchedule> billSchedules;
    private Set<Accounting> accountings;
    private Set<Agent> agents;

    private String DATABASE;

    public Staging()
    {
        this.DATABASE = SessionHelper.STAGING;

        this.stagingDate = new EDITDateTime();
        this.cases = new HashSet<Case>();
        this.segmentBases = new HashSet<SegmentBase>();
        this.payrollDeductionSchedules = new HashSet<PayrollDeductionSchedule>();
        this.billSchedules = new HashSet<BillSchedule>();
        this.accountings = new HashSet<Accounting>();
        this.agents = new HashSet<Agent>();
    }

    public Staging(String database)
    {
        this.DATABASE = database;

        this.stagingDate = new EDITDateTime();
        this.cases = new HashSet<Case>();
        this.segmentBases = new HashSet<SegmentBase>();
        this.payrollDeductionSchedules = new HashSet<PayrollDeductionSchedule>();
        this.billSchedules = new HashSet<BillSchedule>();
        this.accountings = new HashSet<Accounting>();
        this.agents = new HashSet<Agent>();
    }

    /**
     * Getter.
     * @return
     */
    public Long getStagingPK()
    {
        return stagingPK;
    }

    /**
     * Setter.
     * @param stagingPK
     */
    public void setStagingPK(Long stagingPK)
    {
        this.stagingPK = stagingPK;
    }

    /**
     * Getter.
     * @return
     */
    public EDITDateTime getStagingDate()
    {
        return stagingDate;
    }

    /**
     * Setter.
     * @param stagingDate
     */
    public void setStagingDate(EDITDateTime stagingDate)
    {
        this.stagingDate = stagingDate;
    }

    /**
     * Getter.
     * @return
     */
    public String getEventType()
    {
        return eventType;
    }

    /**
     * Setter.
     * @param eventType
     */
    public void setEventType(String eventType)
    {
        this.eventType = eventType;
    }

    /**
     * Getter.
     * @return
     */
    public String getCaseNumber()
    {
        return caseNumber;
    }

    /**
     * Setter.
     * @param caseNumber
     */
    public void setCaseNumber(String caseNumber)
    {
        this.caseNumber = caseNumber;
    }

    /**
     * Getter.
     * @return
     */
    public String getGroupNumber()
    {
        return groupNumber;
    }

    /**
     * Setter.
     * @param groupNumber
     */
    public void setGroupNumber(String groupNumber)
    {
        this.groupNumber = groupNumber;
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
    public String getCorrespondenceType()
    {
        return correspondenceType;
    }

    /**
     * Setter.
     * @param correspondenceType
     */
    public void setCorrespondenceType(String correspondenceType)
    {
        this.correspondenceType = correspondenceType;
    }

    /**
     * Getter.
     * @return
     */
    public int getRecordCount()
    {
        return recordCount;
    }

    /**
     * Setter.
     * @param recordCount
     */
    public void setRecordCount(int recordCount)
    {
        this.recordCount = recordCount;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getControlTotal()
    {
        return controlTotal;
    }

    /**
     * Setter.
     * @param controlTotal
     */
    public void setControlTotal(EDITBigDecimal controlTotal)
    {
        this.controlTotal = controlTotal;
    }

    /**
     * Setter
     * @param companyName
     */
    public void setCompanyName(String companyName)
    {
        this.companyName = companyName;
    }

    /**
     * Getter
     * @return
     */
    public String getCompanyName()
    {
        return companyName;
    }

    /**
     * Getter.
     * @return
     */
    public Set<Case> getCases()
    {
        return cases;
    }

    /**
     * Setter.
     * @param cases
     */
    public void setCases(Set<Case> cases)
    {
        this.cases = cases;
    }

    /**
     * Add another case to the current mapped cases.
     * @param case
     */
    public void addCase(Case contractGroupCase)
    {
        this.cases.add(contractGroupCase);
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

    /**
     * Getter.
     * @return
     */
    public Set<PayrollDeductionSchedule> getPayrollDeductionSchedules()
    {
        return payrollDeductionSchedules;
    }

    /**
     * Setter.
     * @param payrollDeductionSchedules
     */
    public void setPayrollDeductionSchedules(Set<PayrollDeductionSchedule> payrollDeductionSchedules)
    {
        this.payrollDeductionSchedules = payrollDeductionSchedules;
    }

    /**
     * Add another payrollDeductionSchedule to the current mapped payrollDeductionSchedules.
     * @param payrollDeductionSchedule
     */
    public void addPayrollDeductionSchedule(PayrollDeductionSchedule payrollDeductionSchedule)
    {
        this.payrollDeductionSchedules.add(payrollDeductionSchedule);
    }

    /**
     * Getter.
     * @return
     */
    public Set<BillSchedule> getBillSchedules()
    {
        return billSchedules;
    }

    /**
     * Setter.
     * @param billSchedules
     */
    public void setBillSchedules(Set<BillSchedule> billSchedules)
    {
        this.billSchedules = billSchedules;
    }

    /**
     * Add another billSchedule to the current mapped billSchedules.
     * @param billSchedule
     */
    public void addBillSchedule(BillSchedule billSchedule)
    {
        this.billSchedules.add(billSchedule);
    }

    /**
     * Getter.
     * @return
     */
    public Set<Accounting> getAccountings()
    {
        return accountings;
    }

    /**
     * Setter.
     * @param accountings
     */
    public void setAccountings(Set<Accounting> accountings)
    {
        this.accountings = accountings;
    }

    /**
     * Add another accounting to the current mapped accountings.
     * @param accounting
     */
    public void addAccounting(Accounting accounting)
    {
        this.accountings.add(accounting);
    }

    /**
     * Getter.
     * @return
     */
    public Set<Agent> getAgents()
    {
        return agents;
    }

    /**
     * Setter.
     * @param agents
     */
    public void setAgents(Set<Agent> agents)
    {
        this.agents = agents;
    }

    /**
     * Add another agent to the current mapped agents.
     * @param agent
     */
    public void addAgent(Agent agent)
    {
        this.agents.add(agent);
    }

    public String getDatabase()
    {
        return this.DATABASE;
    }

    public StagingContext stage(StagingContext stagingContext, String database)
    {
        this.setStagingDate(new EDITDateTime(stagingContext.getStagingDate() + EDITDateTime.DATE_TIME_DELIMITER + new EDITDateTime().getFormattedTime()));
        this.setEventType(stagingContext.getEventType());
        this.setRecordCount(stagingContext.getRecordCount());
        this.setControlTotal(stagingContext.getControlTotal());
        this.setCorrespondenceType(stagingContext.getCorrespondenceType());
        this.setContractNumber(stagingContext.getContractNumber());
        this.setCompanyName(stagingContext.getCompanyName());
        this.setCaseNumber(stagingContext.getCaseNumber());
        this.setGroupNumber(stagingContext.getGroupNumber());

        stagingContext.setStaging(this);

        SessionHelper.saveOrUpdate(this, this.DATABASE);

        return stagingContext;
    }

    public static Staging findStagingByDate_EventType(EDITDateTime stagingDate, String eventType)
    {
        String hql = " select staging from Staging staging" +
                    " where staging.StagingDate = :stagingDate" +
                    " and staging.EventType = :eventType";

        EDITMap params = new EDITMap();
        params.put("stagingDate", stagingDate);
        params.put("eventType", eventType);

        List<Staging> results = SessionHelper.executeHQL(hql, params, SessionHelper.STAGING);

        if (results.size() > 0)
        {
            return (Staging) results.get(0);
        }
        else
        {
            return null;
        }
    }

    public static List<Staging> findStagingByEventType(String eventType)
    {
        String hql = " select staging from Staging staging" +
                    " where staging.EventType = :eventType";

        EDITMap params = new EDITMap();
        params.put("eventType", eventType);

        List<Staging> results = SessionHelper.executeHQL(hql, params, SessionHelper.STAGING);

        return results;
       /* if (results.size() > 0)
        {
            return (Staging) results.get(0);
        }
        else
        {
            return null;
        }*/
    }
    
    public static long getCount_AttachedSegmentBaseRecords (Long stagingPK)
    {
    	String hql = " select count(sb) from SegmentBase sb " +
                " where sb.StagingFK = :stagingPK ";
    	
    	EDITMap params = new EDITMap();
        params.put("stagingPK", stagingPK);
        
        List<Long> results = SessionHelper.executeHQL(hql, params, SessionHelper.STAGING);

        if (results.size() > 0)
        {
            return (Long) results.get(0);
        }
        else
        {
            return 0;
        }
    }

    public static Staging findStagingByDate_EventType_CorrType(EDITDateTime stagingDate, String eventType, String corrType)
    {
        String hql = " select staging from Staging staging" +
                    " where staging.StagingDate = :stagingDate" +
                    " and staging.EventType = :eventType" +
                    " and staging.CorrespondenceType = :correspondenceType";

        EDITMap params = new EDITMap();
        params.put("stagingDate", stagingDate);
        params.put("eventType", eventType);
        params.put("correspondenceType", corrType);

        List<Staging> results = SessionHelper.executeHQL(hql, params, SessionHelper.STAGING);

        if (results.size() > 0)
        {
            return (Staging) results.get(0);
        }
        else
        {
            return null;
        }
    }

    public static Staging findStagingByDate_EventType_Company_Contract(EDITDateTime stagingDate, String eventType,
                                                                       String companyName, String contractNumber)
    {
        String hql = " select staging from Staging staging" +
                     " where staging.StagingDate = :stagingDate" +
                     " and staging.EventType = :eventType" +
                     " and staging.CompanyName = :companyName";

        EDITMap params = new EDITMap();
        params.put("stagingDate", stagingDate);
        params.put("eventType", eventType);
        params.put("companyName", companyName);

        if (contractNumber != null)
        {
            hql = hql + " and staging.ContractNumber = :contractNumber";

            params.put("contractNumber", contractNumber);
        }

        List<Staging> results = SessionHelper.executeHQL(hql, params, SessionHelper.STAGING);

        if (results.size() > 0)
        {
            return (Staging) results.get(0);
        }
        else
        {
            return null;
        }
    }
    
    public static Staging findBy_StagingPK(Long stagingPK)
    {
        String hql = " select s from Staging s" +
                    " where s.StagingPK = :stagingPK";

        EDITMap params = new EDITMap();
        params.put("stagingPK", stagingPK);

        List<Staging> results = SessionHelper.executeHQL(hql, params, SessionHelper.STAGING);

        if (results.size() > 0)
        {
            return (Staging) results.get(0);
        }
        else
        {
            return null;
        }
    }
    

    public static Staging findDataWarehouseByDate_EventType_Company_Case_Group(EDITDateTime stagingDate, String eventType,
                                                                               String companyName, String caseNumber,
                                                                               String groupNumber)
    {
        String hql = " select staging from Staging staging" +
                     " where staging.StagingDate = :stagingDate" +
                     " and staging.EventType = :eventType" +
                     " and staging.CompanyName = :companyName";

        EDITMap params = new EDITMap();
        params.put("stagingDate", stagingDate);
        params.put("eventType", eventType);
        params.put("companyName", companyName);

        if (caseNumber != null)
        {
            hql = hql + " and staging.CaseNumber = :caseNumber";

            params.put("caseNumber", caseNumber);
        }

        if (groupNumber != null)
        {
            hql = hql + " and staging.GroupNumber = :groupNumber";

            params.put("groupNumber", groupNumber);
        }

        List<Staging> results = SessionHelper.executeHQL(hql, params, SessionHelper.DATAWAREHOUSE);

        if (results.size() > 0)
        {
            return (Staging) results.get(0);
        }
        else
        {
            return null;
        }
    }

    public void setRunDate(EDITDateTime runDate)
    {
        this.runDate = runDate;
    }

    public EDITDateTime getRunDate()
    {
        return runDate;
    }
}
