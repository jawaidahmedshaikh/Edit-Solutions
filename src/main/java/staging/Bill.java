/*
 * User: dlataille
 * Date: Oct 5, 2007
 * Time: 9:51:44 AM

 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */

package staging;

import edit.common.EDITBigDecimal;
import edit.services.db.hibernate.*;

public class Bill extends HibernateEntity
{
    private Long billPK;
    private Long billGroupFK;
    private Long segmentBaseFK;
    private EDITBigDecimal billedAmount;
    private EDITBigDecimal paidAmount;

    private BillGroup billGroup;
    private SegmentBase segmentBase;

    /**
     * Target database to be used for lookups, etc.
     */
    public static String DATABASE = SessionHelper.STAGING;

    /**
     * Instantiates a Bill entity.
     */
    public Bill()
    {
    }

    /**
     * Save the entity using Hibernate
     */
    public void hSave()
    {
        SessionHelper.saveOrUpdate(this, Bill.DATABASE);
    }

    /**
     * Delete the entity using Hibernate
     */
    public void hDelete()
    {
        SessionHelper.delete(this, Bill.DATABASE);
    }

    /**
     * Getter.
     * @return
     */
    public Long getBillPK()
    {
        return this.billPK;
    }

    /**
     * Setter.
     * @param billPK
     */
    public void setBillPK(Long billPK)
    {
        this.billPK = billPK;
    }

    /**
     * Getter.
     * @return
     */
    public Long getBillGroupFK()
    {
        return billGroupFK;
    }

    /**
     * Setter.
     * @param billGroupFK
     */
    public void setBillGroupFK(Long billGroupFK)
    {
        this.billGroupFK = billGroupFK;
    }

    /**
     * Getter.
     * @return
     */
    public BillGroup getBillGroup()
    {
        return billGroup;
    }

    /**
     * Setter.
     * @param billGroup
     */
    public void setBillGroup(BillGroup billGroup)
    {
        this.billGroup = billGroup;
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
    public EDITBigDecimal getBilledAmount()
    {
        return billedAmount;
    }

    /**
     * Setter.
     * @param billedAmount
     */
    public void setBilledAmount(EDITBigDecimal billedAmount)
    {
        this.billedAmount = billedAmount;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getPaidAmount()
    {
        return paidAmount;
    }

    /**
     * Setter.
     * @param paidAmount
     */
    public void setPaidAmount(EDITBigDecimal paidAmount)
    {
        this.paidAmount = paidAmount;
    }

    public String getDatabase()
    {
        return Bill.DATABASE;
    }
}

