/*
 * User: dlataill
 * Date: Feb 27, 2007
 * Time: 12:40:18 PM
 *
 * (c) 2000-2008 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package staging;

import edit.services.db.hibernate.HibernateEntity;
import edit.services.db.hibernate.SessionHelper;
import edit.common.EDITDate;
import edit.common.EDITBigDecimal;
import edit.common.EDITDateTime;

import java.util.Set;
import java.util.HashSet;


public class AdjustmentHistory extends HibernateEntity
{
    private Long adjustmentHistoryPK;
    private Long financialActivityFK;
    private EDITBigDecimal chargeAmount;
    private String chargeType;

    private FinancialActivity financialActivity;

    /**
     * Target database to be used for lookups, etc.
     */
    public static String DATABASE = SessionHelper.STAGING;


    public AdjustmentHistory()
    {
    }

    /**
     * Getter.
     * @return
     */
    public Long getAdjustmentHistoryPK()
    {
        return adjustmentHistoryPK;
    }

    /**
     * Setter.
     * @param adjustmentHistoryPK
     */
    public void setAdjustmentHistoryPK(Long adjustmentHistoryPK)
    {
        this.adjustmentHistoryPK = adjustmentHistoryPK;
    }

    /**
     * Getter.
     * @return
     */
    public Long getFinancialActivityFK()
    {
        return financialActivityFK;
    }

    /**
     * Setter.
     * @param financialActivityFK
     */
    public void setFinancialActivityFK(Long financialActivityFK)
    {
        this.financialActivityFK = financialActivityFK;
    }

    /**
     * Getter.
     * @return
     */
    public FinancialActivity getFinancialActivity()
    {
        return financialActivity;
    }

    /**
     * Setter.
     * @param financialActivity
     */
    public void setFinancialActivity(FinancialActivity financialActivity)
    {
        this.financialActivity = financialActivity;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getChargeAmount()
    {
        return chargeAmount;
    }

    /**
     * Setter.
     * @param chargeAmount
     */
    public void setChargeAmount(EDITBigDecimal chargeAmount)
    {
        this.chargeAmount = chargeAmount;
    }

    /**
    * Getter.
    * @return
    */
    public String getChargeType()
    {
        return chargeType;
    }

    /**
     * Setter.
     * @param chargeType
     */
    public void setChargeType(String chargeType)
    {
        this.chargeType = chargeType;
    }

    public String getDatabase()
    {
        return AdjustmentHistory.DATABASE;
    }
}
