/*
 * User: dlataille
 * Date: Oct 5, 2007
 * Time: 9:43:44 AM

 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */

package staging;

import edit.common.EDITDate;
import edit.common.EDITBigDecimal;
import edit.services.db.hibernate.*;

import java.util.*;

public class BillGroup extends HibernateEntity
{
    private Long billGroupPK;
    private Long billScheduleFK;
    private EDITDate extractDate;
    private EDITDate dueDate;
    private EDITBigDecimal totalBilledAmount;
    private EDITBigDecimal totalPaidAmount;
    private EDITDate releaseDate;
    private String stopReason;

    private BillSchedule billSchedule;

    //  Children
    private Set<Bill> bills;

    /**
     * Target database to be used for lookups, etc.
     */
    public static String DATABASE = SessionHelper.STAGING;

    /**
     * Instantiates a BillGroup entity.
     */
    public BillGroup()
    {
        init();
    }

    /**
     * Guarantees that a VO and CRUDEntityImpl are properly instantiated.
     */
    private final void init()
    {
        this.bills = new HashSet<Bill>();
    }

    /**
     * Save the entity using Hibernate
     */
    public void hSave()
    {
        SessionHelper.saveOrUpdate(this, BillGroup.DATABASE);
    }

    /**
     * Delete the entity using Hibernate
     */
    public void hDelete()
    {
        SessionHelper.delete(this, BillGroup.DATABASE);
    }

    /**
     * Getter.
     * @return
     */
    public Long getBillGroupPK()
    {
        return this.billGroupPK;
    }

    /**
     * Setter.
     * @param billGroupPK
     */
    public void setBillGroupPK(Long billGroupPK)
    {
        this.billGroupPK = billGroupPK;
    }

    /**
     * Getter.
     * @return
     */
    public Long getBillScheduleFK()
    {
        return billScheduleFK;
    }

    /**
     * Setter.
     * @param billScheduleFK
     */
    public void setBillScheduleFK(Long billScheduleFK)
    {
        this.billScheduleFK = billScheduleFK;
    }

    /**
     * Getter.
     * @return
     */
    public BillSchedule getBillSchedule()
    {
        return billSchedule;
    }

    /**
     * Setter.
     * @param billSchedule
     */
    public void setBillSchedule(BillSchedule billSchedule)
    {
        this.billSchedule = billSchedule;
    }

    /**
     * Getter.
     * @return
     */
    public EDITDate getExtractDate()
    {
        return extractDate;
    }

    /**
     * Setter.
     * @param extractDate
     */
    public void setExtractDate(EDITDate extractDate)
    {
        this.extractDate = extractDate;
    }

    /**
     * Getter.
     * @return
     */
    public EDITDate getDueDate()
    {
        return dueDate;
    }

    /**
     * Setter.
     * @param dueDate
     */
    public void setDueDate(EDITDate dueDate)
    {
        this.dueDate = dueDate;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getTotalBilledAmount()
    {
        return totalBilledAmount;
    }

    /**
     * Setter.
     * @param totalBilledAmount
     */
    public void setTotalBilledAmount(EDITBigDecimal totalBilledAmount)
    {
        this.totalBilledAmount = totalBilledAmount;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getTotalPaidAmount()
    {
        return totalPaidAmount;
    }

    /**
     * Setter.
     * @param totalPaidAmount
     */
    public void setTotalPaidAmount(EDITBigDecimal totalPaidAmount)
    {
        this.totalPaidAmount = totalPaidAmount;
    }

    /**
     * Getter.
     * @return
     */
    public EDITDate getReleaseDate()
    {
        return releaseDate;
    }

    /**
     * Setter.
     * @param releaseDate
     */
    public void setReleaseDate(EDITDate releaseDate)
    {
        this.releaseDate = releaseDate;
    }

    /**
     * Getter.
     * @return
     */
    public String getStopReason()
    {
        return stopReason;
    }

    /**
     * Setter.
     * @param stopReason
     */
    public void setStopReason(String stopReason)
    {
        this.stopReason = stopReason;
    }

    /**
     * Getter.
     * @return
     */
    public Set<Bill> getBills()
    {
        return bills;
    }

    /**
     * Setter.
     * @param bills
     */
    public void setBills(Set<Bill> bills)
    {
        this.bills = bills;
    }

    public void addBill(Bill bill)
    {
        this.bills.add(bill);

        bill.setBillGroup(this);

        SessionHelper.saveOrUpdate(bill, BillGroup.DATABASE);
    }

    public void removeBill(Bill bill)
    {
        this.bills.remove(bill);

        bill.setBillGroup(null);
    }

    public String getDatabase()
    {
        return BillGroup.DATABASE;
    }
}

