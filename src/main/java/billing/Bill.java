/*
 * User: sdorman
 * Date: Jun 15, 2007
 * Time: 10:01:07 AM
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement. 
 */

package billing;

import edit.common.*;
import edit.services.db.hibernate.*;
import contract.*;
import fission.utility.Util;

import java.util.*;

import org.dom4j.*;
import staging.StagingContext;
import staging.IStaging;

public class Bill extends HibernateEntity implements IStaging
{
    private Long billPK;

    private EDITBigDecimal billedAmount;
    private EDITBigDecimal paidAmount;
    private EDITBigDecimal adjustmentAmount;

    // Parents
    private BillGroup billGroup;
    private Segment segment;
    Long billGroupFK;
    Long segmentFK;

    public static final String DATABASE = SessionHelper.EDITSOLUTIONS;


    public Bill()
    {

    }

    public Long getBillPK()
    {
        return billPK;
    }

    public void setBillPK(Long billPK)
    {
        this.billPK = billPK;
    }

    public BillGroup getBillGroup()
    {
        return this.billGroup;
    }

    public void setBillGroup(BillGroup billGroup)
    {
        this.billGroup = billGroup;
    }

    public Long getBillGroupFK()
    {
        return this.billGroupFK;
    }

    public void setBillGroupFK(Long billGroupFK)
    {
        this.billGroupFK = billGroupFK;
    }

    public Segment getSegment()
    {
        return this.segment;
    }

    public void setSegment(Segment segment)
    {
        this.segment = segment;
    }

    public Long getSegmentFK()
    {
        return this.segmentFK;
    }

    public void setSegmentFK(Long segmentFK)
    {
        this.segmentFK = segmentFK;
    }

    public EDITBigDecimal getBilledAmount()
    {
        return billedAmount;
    }

    public void setBilledAmount(EDITBigDecimal billedAmount)
    {
        this.billedAmount = billedAmount;
    }

    public EDITBigDecimal getPaidAmount()
    {
        return paidAmount;
    }

    public void setPaidAmount(EDITBigDecimal paidAmount)
    {
        this.paidAmount = paidAmount;
    }

   public EDITBigDecimal getAdjustmentAmount()
    {
        return adjustmentAmount;
    }

    public void setAdjustmentAmount(EDITBigDecimal adjustmentAmount)
    {
        this.adjustmentAmount = adjustmentAmount;
    }
    
    /**
     * Changes the paidAmount to the new value.  Updates the BillGroup's totalPaidAmount to reflect the new value.
     * Does this by calculating the delta between the original paid amount and the new paid amount and adding it to
     * the BillGroup's total.
     *
     * @param paidAmount                    new paid amount
     */
    public void updatePaidAmount(EDITBigDecimal paidAmount)
    {
        EDITBigDecimal originalPaidAmount = this.paidAmount;

        EDITBigDecimal delta = paidAmount.subtractEditBigDecimal(originalPaidAmount);

        this.billGroup.setTotalPaidAmount(this.billGroup.getTotalPaidAmount().addEditBigDecimal(delta));

        this.paidAmount = paidAmount;
    }
    
    /**
     * Changes the billedAmount to the new value.  Updates the BillGroup's totalBilledAmount to reflect the new value.
     * Does this by calculating the delta between the original billed amount and the new billed amount and adding it to
     * the BillGroup's total.
     *
     * @param billedAmount                    new billed amount
     */
    public void updateBilledAmount(EDITBigDecimal billedAmount)
    {
        EDITBigDecimal originalBilledAmount = this.billedAmount;

        EDITBigDecimal delta = billedAmount.subtractEditBigDecimal(originalBilledAmount);

        this.billGroup.setTotalBilledAmount(this.billGroup.getTotalBilledAmount().addEditBigDecimal(delta));

        this.billedAmount = billedAmount;
    }    

    public String getDatabase()
    {
        return Bill.DATABASE;
    }

    public static Bill findByPK(Long billPK)
    {
        String hql = " select bill from Bill bill" +
                     " where bill.BillPK = :billPK";

        EDITMap params = new EDITMap();
        params.put("billPK", billPK);

        List<Bill> results = SessionHelper.executeHQL(hql, params, Bill.DATABASE);

        if (results.size() > 0)
        {
            return (Bill) results.get(0);
        }
        else
        {
            return null;
        }
    }

    public static Bill[] findByBillGroup(BillGroup billGroup)
    {
        String hql = " from Bill bill" +
                    " where bill.BillGroup = :billGroup";

        EDITMap params = new EDITMap();
        params.put("billGroup", billGroup);

        List<Bill> results = SessionHelper.executeHQL(hql, params, Bill.DATABASE);

        return results.toArray(new Bill[results.size()]);
    }

    /**
     * Finds all Bills for a given BillGroup and ClientDetail whose role is Payor
     *
     * @param clientDetailPK
     * @param billGroupPK
     * 
     * @return
     */
    public static Bill[] findAllBillsForABillGroupByPayorClientDetail(Long clientDetailPK, Long billGroupPK)
    {
        String hql = " select bill from Bill bill" +
                    " join fetch bill.Segment segment " +
                    " join segment.ContractClients contractClient " +
                    " join contractClient.ClientRole clientRole " +
                    " join clientRole.ClientDetail clientDetail " +
                    " where clientDetail.ClientDetailPK = :clientDetailPK " +
                    " and clientRole.RoleTypeCT = :roleTypeCT" +
                    " and bill.BillGroupFK = :billGroupPK";

        EDITMap params = new EDITMap();
        params.put("clientDetailPK", clientDetailPK);
        params.put("roleTypeCT", "POR");
        params.put("billGroupPK", billGroupPK);

        List<Bill> results = SessionHelper.executeHQL(hql, params, Bill.DATABASE);

        return results.toArray(new Bill[results.size()]);
    }

    /**
     * Return objects are Bills ... For display purposes needs to be sorted by BillGroup.DueDate
     * In HQL, a BillGroup entity is selected then sorted and Bills are returned by the method.
     * @param segmentFK
     * @return
     */
    public static final Bill[] findBillHistoryBySegmentFK(Long segmentFK)
    {
        String hql = " select billGroup " +
                     " from BillGroup billGroup" +
                     " join fetch billGroup.Bills bill" +
                     " join fetch billGroup.BillSchedule" +
                     " where bill.SegmentFK  = :SegmentFK)";

        Map params = new HashMap();
        params.put("SegmentFK", segmentFK);

        List<BillGroup> results = SessionHelper.executeHQL(hql, params, SessionHelper.EDITSOLUTIONS);

        BillGroup[] billGroups = results.toArray(new BillGroup[results.size()]);

        billGroups = (billing.BillGroup[]) Util.sortObjects(billGroups, new String[] {"getDueDate"});

        List<billing.Bill> billsList = new ArrayList<billing.Bill>();

        for (int i = 0; i < billGroups.length; i++)
        {
            Set<billing.Bill> bills = billGroups[i].getBills();

            for (billing.Bill bill : bills)
            {
                billsList.add(bill);
            }
        }

        return billsList.toArray(new Bill[billsList.size()]);
    }
    
    /**
     * Finds all Bills for a given BillGroup sorted by ContractNumber asc.
     *
     * @param billGroupPK
     * 
     * @return
     */
    public static Bill[] findAllBillsForABillGroup(Long billGroupPK)
    {
        String hql = " select bill from Bill bill" +
                    " join fetch bill.Segment segment " +
                    " where bill.BillGroupFK = :billGroupPK" +
                    " order by segment.ContractNumber asc";
                    

        EDITMap params = new EDITMap();
        params.put("billGroupPK", billGroupPK);

        List<Bill> results = SessionHelper.executeHQL(hql, params, Bill.DATABASE);

        return results.toArray(new Bill[results.size()]);
    }    

    public StagingContext stage(StagingContext stagingContext,  String database)
    {
        staging.Bill stagingBill = new staging.Bill();
        stagingBill.setBillGroup(stagingContext.getCurrentBillGroup());
        stagingBill.setSegmentBase(stagingContext.getCurrentSegmentBase());
        stagingBill.setBilledAmount(this.billedAmount);
        stagingBill.setPaidAmount(this.paidAmount);

        stagingContext.getCurrentBillGroup().addBill(stagingBill);

        stagingContext.getCurrentSegmentBase().addBill(stagingBill);

        SessionHelper.saveOrUpdate(stagingBill, database);

        return stagingContext;
    }
}
