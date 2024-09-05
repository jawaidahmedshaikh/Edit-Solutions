/*
 * User: dlataill
 * Date: June 14, 2006
 * Time: 12:19:00 AM
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */

package event;

import edit.common.*;
import edit.services.db.hibernate.*;

public class OverdueChargeSettled extends HibernateEntity
{
    private Long overdueChargeSettledPK;

    private EDITTrx editTrx;
    private OverdueCharge overdueCharge;

    private Long editTrxFK;
    private Long overdueChargeFK;

    private EDITBigDecimal settledCoi;
    private EDITBigDecimal settledAdmin;
    private EDITBigDecimal settledExpense;

    /**
     * Target database to be used for lookups, etc.
     */
    public static String DATABASE = SessionHelper.EDITSOLUTIONS;



    public void hSave()
    {
        SessionHelper.saveOrUpdate(this, OverdueChargeSettled.DATABASE);
    }

    public void hDelete()
    {
        SessionHelper.delete(this, OverdueChargeSettled.DATABASE);
    }

    /**
     * Getter.
     * @return
     */
    public Long getOverdueChargeSettledPK()
    {
        return this.overdueChargeSettledPK;
    }

    /**
     * Setter.
     * @param overdueChargeSettledPK
     */
    public void setOverdueChargeSettledPK(Long overdueChargeSettledPK)
    {
        this.overdueChargeSettledPK = overdueChargeSettledPK;
    }

    public EDITTrx getEDITTrx()
    {
        return editTrx;
    }

    public void setEDITTrx(EDITTrx editTrx)
    {
        this.editTrx = editTrx;
    }

    public Long getEDITTrxFK()
    {
        return this.editTrxFK;
    }

    /**
     * Setter.
     * @param editTrxFK
     */
    public void setEDITTrxFK(Long editTrxFK)
    {
        this.editTrxFK = editTrxFK;
    }

    /**
     * Getter.
     * @return
     */
    public OverdueCharge getOverdueCharge()
    {
        return overdueCharge;
    }

    /**
     * Setter.
     * @param overdueCharge
     */
    public void setOverdueCharge(OverdueCharge overdueCharge)
    {
        this.overdueCharge = overdueCharge;
    }

    /**
     * Getter.
     * @return
     */
    public Long getOverdueChargeFK()
    {
        return overdueChargeFK;
    }

    /**
     * Setter.
     * @param overdueChargeFK
     */
    public void setOverdueChargeFK(Long overdueChargeFK)
    {
        this.overdueChargeFK = overdueChargeFK;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getSettledCoi()
    {
        return settledCoi;
    }

    /**
     * Setter.
     * @param settledCoi
     */
    public void setSettledCoi(EDITBigDecimal settledCoi)
    {
        this.settledCoi = settledCoi;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getSettledAdmin()
    {
        return settledAdmin;
    }

    /**
     * Setter.
     * @param settledAdmin
     */
    public void setSettledAdmin(EDITBigDecimal settledAdmin)
    {
        this.settledAdmin = settledAdmin;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getSettledExpense()
    {
        return settledExpense;
    }

    /**
     * Setter.
     * @param settledExpense
     */
    public void setSettledExpense(EDITBigDecimal settledExpense)
    {
        this.settledExpense = settledExpense;
    }

    /**
     * Gets the database this object belongs to
     *
     * @return  string containing the hibernate database name for this object
     */
    public String getDatabase()
    {
        return OverdueChargeSettled.DATABASE;
    }
}
