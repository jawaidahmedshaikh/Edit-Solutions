/*
 * User: sdorman
 * Date: Nov 10, 2005
 * Time: 12:56:04 PM
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement. 
 */

package contract;

import edit.services.db.hibernate.*;
import edit.common.*;
import edit.common.vo.BucketAllocationVO;
import edit.services.db.hibernate.*;



public class BucketAllocation extends HibernateEntity
{
    private Long bucketAllocationPK;
    private Long bucketFK;
    private EDITBigDecimal allocationPercent;
    private EDITBigDecimal dollars;
    private EDITBigDecimal units;
    private Bucket bucket;
    private BucketAllocationVO bucketAllocationVO;

    /**
     * Target database to be used for lookups, etc.
     */
    public static String DATABASE = SessionHelper.EDITSOLUTIONS;


    /**
     * Instantiates a BucketAllocation entity with a default BucketAllocationVO.
     */
    public BucketAllocation()
    {
    }

    public BucketAllocation(BucketAllocationVO bucketAllocationVO)
    {
        this.bucketAllocationVO = bucketAllocationVO;
    }

    /**
     * @see HibernateEntity#hSave()
     */
    public void hSave()
    {
        SessionHelper.saveOrUpdate(this, BucketAllocation.DATABASE);
    }

    /**
     * @see HibernateEntity#hDelete()
     */
    public void hDelete()
    {
        SessionHelper.delete(this, BucketAllocation.DATABASE);
    }


    /**
     * @return
     *
     * @see edit.services.db.CRUDEntity#getPK()
     */
    public Long getBucketAllocationPK()
    {
        return bucketAllocationPK;
    }

    public Long getBucketFK()
    {
        return bucketFK;
    }

    public EDITBigDecimal getAllocationPercent()
    {
        return allocationPercent;
    }

    public EDITBigDecimal getDollars()
    {
        return dollars;
    }

    public EDITBigDecimal getUnits()
    {
        return units;
    }

    public void setBucketAllocationPK(Long bucketAllocationPK)
    {
        this.bucketAllocationPK = bucketAllocationPK;
    }

    public void setBucketFK(Long bucketFK)
    {
        this.bucketFK = bucketFK;
    }

    public void setAllocationPercent(EDITBigDecimal allocationPercent)
    {
        this.allocationPercent = allocationPercent;
    }

    public void setDollars(EDITBigDecimal dollars)
    {
        this.dollars = dollars;
    }

    public void setUnits(EDITBigDecimal units)
    {
        this.units = units;
    }

    public Bucket getBucket()
    {
        return bucket;
    }

    public void setBucket(Bucket bucket)
    {
        this.bucket = bucket;
    }

    /**
     * Gets the database this object belongs to
     *
     * @return  string containing the hibernate database name for this object
     */
    public String getDatabase()
    {
        return BucketAllocation.DATABASE;
    }

    /**
     * Finder.
     *
     * @param bucketAllocationPK
     */
    public static BucketAllocation findByPK(Long bucketAllocationPK)
    {
        return (BucketAllocation) SessionHelper.get(BucketAllocation.class, bucketAllocationPK, BucketAllocation.DATABASE);
    }
}
