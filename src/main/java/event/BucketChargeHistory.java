/*
 * User: gfrosti
 * Date: Jun 30, 2005
 * Time: 2:47:47 PM
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package event;

import edit.common.*;
import edit.services.db.hibernate.*;


public class BucketChargeHistory extends HibernateEntity
{
    private Long bucketChargeHistoryPK;
    private EDITBigDecimal chargeAmount;
    private String chargeTypeCT;
    private BucketHistory bucketHistory;
    private Long bucketHistoryFK;

    /**
     * Target database to be used for lookups, etc.
     */
    public static String DATABASE = SessionHelper.EDITSOLUTIONS;
    

    public BucketChargeHistory()
    {
    }

    public Long getBucketHistoryFK()
    {
        return bucketHistoryFK;
    }

    public void setBucketHistoryFK(Long bucketHistoryFK)
    {
        this.bucketHistoryFK = bucketHistoryFK;
    }

    public BucketHistory getBucketHistory()
    {
        return bucketHistory;
    }

    public void setBucketHistory(BucketHistory bucketHistory)
    {
        this.bucketHistory = bucketHistory;
    }

    public Long getBucketChargeHistoryPK()
    {
        return bucketChargeHistoryPK;
    }

    public void setBucketChargeHistoryPK(Long bucketChargeHistoryPK)
    {
        this.bucketChargeHistoryPK = bucketChargeHistoryPK;
    }

    public EDITBigDecimal getChargeAmount()
    {
        return chargeAmount;
    }

    public void setChargeAmount(EDITBigDecimal chargeAmount)
    {
        this.chargeAmount = chargeAmount;
    }

    public String getChargeTypeCT()
    {
        return chargeTypeCT;
    }

    public void setChargeTypeCT(String chargeTypeCT)
    {
        this.chargeTypeCT = chargeTypeCT;
    }

    /**
     * Save the entity using Hibernate
     */
    public void hSave()
    {
        SessionHelper.saveOrUpdate(this, BucketChargeHistory.DATABASE);
    }

    /**
     * Delete the entity using Hibernate
     */
    public void hDelete()
    {
        SessionHelper.delete(this, BucketChargeHistory.DATABASE);
    }

    /**
     * Gets the database this object belongs to
     *
     * @return  string containing the hibernate database name for this object
     */
    public String getDatabase()
    {
        return BucketChargeHistory.DATABASE;
    }
}
