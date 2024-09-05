/*
 * User: gfrosti
 * Date: Jun 21, 2005
 * Time: 4:36:12 PM
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package event;

import edit.common.*;
import edit.common.vo.*;
import edit.services.db.hibernate.*;
import edit.services.db.*;
import edit.services.db.hibernate.*;


public class Charge extends HibernateEntity
{
    private Long chargePK;
    private Long groupSetupFK;
    private EDITBigDecimal chargeAmount;
    private String chargeTypeCT;
    private EDITBigDecimal chargePercent;
    private String oneTimeOnlyInd;
    private EDITDate oneTimeOnlyDate;
    private GroupSetup groupSetup;

    /**
     * Target database to be used for lookups, etc.
     */
    public static String DATABASE = SessionHelper.EDITSOLUTIONS;
    public static String CHARGE_TYPE_SUSPENSE_REFUND = "SuspenseRefund";


    public Charge()
    {
    }

    /**
     * Getter.
     * @return
     */
    public Long getChargePK()
    {
        return chargePK;
    }

    /**
     * Setter.
     * @param chargePK
     */
    public void setChargePK(Long chargePK)
    {
        this.chargePK = chargePK;
    }

    /**
     * Getter.
     * @return
     */
    public Long getGroupSetupFK()
    {
        return groupSetupFK;
    }

    /**
     * Setter.
     * @param groupSetupFK
     */
    public void setGroupSetupFK(Long groupSetupFK)
    {
        this.groupSetupFK = groupSetupFK;
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
    public String getChargeTypeCT()
    {
        return chargeTypeCT;
    }

    /**
     * Setter.
     * @param chargeTypeCT
     */
    public void setChargeTypeCT(String chargeTypeCT)
    {
        this.chargeTypeCT = chargeTypeCT;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getChargePercent()
    {
        return chargePercent;
    }

    /**
     * Setter.
     * @param chargePercent
     */
    public void setChargePercent(EDITBigDecimal chargePercent)
    {
        this.chargePercent = chargePercent;
    }

    /**
     * Getter.
     * @return
     */
    public String getOneTimeOnlyInd()
    {
        return oneTimeOnlyInd;
    }

    /**
     * Setter.
     * @param oneTimeOnlyInd
     */
    public void setOneTimeOnlyInd(String oneTimeOnlyInd)
    {
        this.oneTimeOnlyInd = oneTimeOnlyInd;
    }

    /**
     * Getter.
     * @return
     */
    public EDITDate getOneTimeOnlyDate()
    {
        return oneTimeOnlyDate;
    }

    /**
     * Setter.
     * @param oneTimeOnlyDate
     */
    public void setOneTimeOnlyDate(EDITDate oneTimeOnlyDate)
    {
        this.oneTimeOnlyDate = oneTimeOnlyDate;
    }

    /**
     * Getter.
     * @return
     */
    public GroupSetup getGroupSetup()
    {
        return groupSetup;
    }

    /**
     * Setter.
     * @param groupSetup
     */
    public void setGroupSetup(GroupSetup groupSetup)
    {
        this.groupSetup = groupSetup;
    }

    /**
     * Need CRUD delete method to remove charges from pending tranactions
     * @param chargePK
     * @return
     * @throws Exception
     */
    public int delete() throws Exception
    {
        CRUD crud = null;

        int deleteCount = 0;

        try
        {
            crud = CRUDFactory.getSingleton().getCRUD(ConnectionFactory.EDITSOLUTIONS_POOL);

            crud.deleteVOFromDB(ChargeVO.class, chargePK.longValue());

            deleteCount = 1;
        }
        catch (Exception e)
        {
            if (crud != null)
            {
                crud.close();
            }

            crud = null;
        }

        return deleteCount;
    }

//    public boolean equals(Object o)
//    {
//        if (this == o) return true;
//        if (!(o instanceof Charge)) return false;
//
//        final Charge charge = (Charge) o;
//
//        if (!getChargePK().equals(charge.getChargePK())) return false;
//
//        return true;
//    }

//    public int hashCode()
//    {
//        return getChargePK().hashCode();
//    }

    public void hSave()
    {
        SessionHelper.saveOrUpdate(this, Charge.DATABASE);
    }

    public void hDelete()
    {
        SessionHelper.delete(this, Charge.DATABASE);
    }

    /**
     * Gets the database this object belongs to
     *
     * @return  string containing the hibernate database name for this object
     */
    public String getDatabase()
    {
        return Charge.DATABASE;
    }
}
