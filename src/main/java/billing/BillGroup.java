/*
 * User: sdorman
 * Date: Jun 15, 2007
 * Time: 10:29:44 AM
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement. 
 */

package billing;

import edit.common.*;
import edit.services.db.hibernate.*;

import java.util.*;

import group.*;
import staging.StagingContext;
import staging.IStaging;


public class BillGroup extends HibernateEntity implements IStaging
{
    private Long billGroupPK;
    private Long billScheduleFK;
    private EDITDate extractDate;
    private EDITDate dueDate;
    private EDITBigDecimal totalBilledAmount;
    private EDITBigDecimal totalPaidAmount;
    private EDITDate releaseDate;
    private String stopReasonCT;
    private EDITBigDecimal overageFundsAmount;
    private EDITBigDecimal shortageFundsAmount;
    private EDITBigDecimal creditFundsAmount;


    //  Parents
    private BillSchedule billSchedule;

    //  Children
    private Set bills;

    public static final String DATABASE = SessionHelper.EDITSOLUTIONS;


    public BillGroup()
    {
        init();
    }

    private void init()
    {
        if (bills == null)
        {
            bills = new HashSet();
        }
    }

    public Long getBillGroupPK()
    {
        return billGroupPK;
    }

    public void setBillGroupPK(Long billGroupPK)
    {
        this.billGroupPK = billGroupPK;
    }

    public BillSchedule getBillSchedule()
    {
        return billSchedule;
    }

    public void setBillSchedule(BillSchedule billSchedule)
    {
        this.billSchedule = billSchedule;
    }

    public Long getBillScheduleFK()
    {
        return billSchedule.getBillSchedulePK();
    }

    public void setBillScheduleFK(Long billScheduleFK)
    {
        this.billScheduleFK = billScheduleFK;
    }


    public EDITDate getExtractDate()
    {
        return extractDate;
    }

    public void setExtractDate(EDITDate extractDate)
    {
        this.extractDate = extractDate;
    }

    public EDITDate getDueDate()
    {
        return dueDate;
    }

    public void setDueDate(EDITDate dueDate)
    {
        this.dueDate = dueDate;
    }

    public EDITBigDecimal getTotalBilledAmount()
    {
        return totalBilledAmount;
    }

    public void setTotalBilledAmount(EDITBigDecimal totalBilledAmount)
    {
        this.totalBilledAmount = totalBilledAmount;
    }

    public EDITBigDecimal getTotalPaidAmount()
    {
        return totalPaidAmount;
    }

    public void setTotalPaidAmount(EDITBigDecimal totalPaidAmount)
    {
        this.totalPaidAmount = totalPaidAmount;
    }

    public EDITDate getReleaseDate()
    {
        return releaseDate;
    }

    public void setReleaseDate(EDITDate releaseDate)
    {
        this.releaseDate = releaseDate;
    }

    public String getStopReasonCT()
    {
        return stopReasonCT;
    }

    public void setStopReasonCT(String stopReasonCT)
    {
        this.stopReasonCT = stopReasonCT;
    }

    public EDITBigDecimal getOverageFundsAmount()
    {
        return overageFundsAmount;
    }

    public void setOverageFundsAmount(EDITBigDecimal overageFundsAmount)
    {
        this.overageFundsAmount = overageFundsAmount;
    }

    public EDITBigDecimal getShortageFundsAmount()
    {
        return shortageFundsAmount;
    }

    public void setShortageFundsAmount(EDITBigDecimal shortageFundsAmount)
    {
        this.shortageFundsAmount = shortageFundsAmount;
    }

    public EDITBigDecimal getCreditFundsAmount()
    {
        return creditFundsAmount;
    }

    public void setCreditFundsAmount(EDITBigDecimal creditFundsAmount)
    {
        this.creditFundsAmount = creditFundsAmount;
    }

    public Set getBills()
    {
        return bills;
    }

    public void setBills(Set bills)
    {
        this.bills = bills;
    }

    public void addBill(Bill bill)
    {
        getBills().add(bill);

        bill.setBillGroup(this);

        increaseTotals(bill);
        
        SessionHelper.saveOrUpdate(this, getDatabase());
    }

    public void removeBill(Bill bill)
    {
        this.bills.remove(bill);

        bill.setBillGroup(null);

        decreaseTotals(bill);
    }

  /**
      * Get a single Bill
      * @return
      */
     public Bill getBill()
     {
         Bill bill =getBills().isEmpty() ? null : (Bill) getBills().iterator().next();

         return bill;
     }

    public String getDatabase()
    {
        return BillGroup.DATABASE;
    }

    /**
     * Finds the BillGroup with the given primary key
     *
     * @param billGroupPK           primary key
     *
     * @return
     */
    public static BillGroup findByPK(Long billGroupPK)
    {
        String hql = " select billGroup from BillGroup billGroup" +
                     " where billGroup.BillGroupPK = :billGroupPK";

        EDITMap params = new EDITMap();

        params.put("billGroupPK", billGroupPK);

        List<BillGroup> results = SessionHelper.executeHQL(hql, params, BillGroup.DATABASE);

        if (results.size() > 0)
        {
            return (BillGroup) results.get(0);
        }
        else
        {
            return null;
        }
    }

    /**
     * Finds all BillGroups that were not paid.  Not paid is determined by not having a ReleaseDate.
     *
     * @return  BillGroups not paid
     */
    public static BillGroup[] findAllNotPaid()
    {
        String hql = " from BillGroup billGroup" +
                     " join fetch billGroup.BillSchedule billSchedule" +
                     " join fetch billSchedule.ContractGroups" +
                     " where billGroup.ReleaseDate is null" +
                     " and billSchedule.BillMethodCT = 'List'";

        EDITMap params = new EDITMap();

        List<BillGroup> results = SessionHelper.executeHQL(hql, params, BillGroup.DATABASE);

        return results.toArray(new BillGroup[results.size()]);
    }

    public static BillGroup findByDueDate_GroupNumber(EDITDate dueDate, String groupNumber)
    {
        String hql = " select billGroup from BillGroup billGroup" +
                     " join billGroup.BillSchedule billSchedule " +
                     " join billSchedule.ContractGroups contractGroup " +
                     " where contractGroup.ContractGroupTypeCT = :contractGroupTypeCT " +
                     " and contractGroup.ContractGroupNumber = :groupNumber" +
                     " and billGroup.DueDate = :dueDate";

        EDITMap params = new EDITMap();
        params.put("contractGroupTypeCT", ContractGroup.CONTRACTGROUPTYPECT_GROUP);
        params.put("groupNumber", groupNumber);
        params.put("dueDate", dueDate);

        List<BillGroup> results = SessionHelper.executeHQL(hql, params, BillGroup.DATABASE);

        if (results.size() > 0)
        {
            return (BillGroup) results.get(0);
        }
        else
        {
            return null;
        }
    }

    public static BillGroup[] findByExtractDate_billSchedule(EDITDate extractDate, BillSchedule billSched)
    {
        String hql = " select billGroup from BillGroup billGroup" +
                     " where billGroup.ExtractDate = :extractDate" +
                     " and billGroup.BillSchedule = :billSchedule";

        EDITMap params = new EDITMap();

        params.put("extractDate", extractDate);
        params.put("billSchedule", billSched);

        List<BillGroup> results = SessionHelper.executeHQL(hql, params, BillGroup.DATABASE);

        return (BillGroup[]) results.toArray(new BillGroup[results.size()]);
    }

    private void increaseTotals(Bill bill)
    {
        this.totalBilledAmount = this.totalBilledAmount.addEditBigDecimal(bill.getBilledAmount());
        this.totalPaidAmount= this.totalPaidAmount.addEditBigDecimal(bill.getPaidAmount());
    }

    private void decreaseTotals(Bill bill)
    {
        this.totalBilledAmount = this.totalBilledAmount.subtractEditBigDecimal(bill.getBilledAmount());
        this.totalPaidAmount= this.totalPaidAmount.subtractEditBigDecimal(bill.getPaidAmount());
    }

    public void hSave()
    {
        SessionHelper.saveOrUpdate(this, getDatabase());
    }

    public StagingContext stage(StagingContext stagingContext, String database)
    {
        staging.BillGroup stagingBillGroup = new staging.BillGroup();
        stagingBillGroup.setExtractDate(this.extractDate);
        stagingBillGroup.setDueDate(this.dueDate);
        stagingBillGroup.setTotalBilledAmount(this.totalBilledAmount);
        stagingBillGroup.setTotalPaidAmount(this.totalPaidAmount);
        stagingBillGroup.setReleaseDate(this.releaseDate);
        stagingBillGroup.setStopReason(this.stopReasonCT);

        stagingContext.getCurrentBillSchedule().addBillGroup(stagingBillGroup);
        stagingContext.setCurrentBillGroup(stagingBillGroup);

        SessionHelper.saveOrUpdate(stagingBillGroup, database);

        return stagingContext;
    }
}
