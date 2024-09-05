/*
 * User: cgleason
 * Date: Feb 7, 2007
 * Time: 3:47:13 PM
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All rights Reserved
 * Systems Engineering Group, LLC Propietary and confidential.  Any use is
 * subject to the license agreement.
 */
package engine;

import edit.services.db.hibernate.*;
import edit.common.*;

import java.util.Set;


public class ControlBalance extends HibernateEntity
{
    private Long controlBalancePK;
    private Long productFilteredFundStructureFK;
    private EDITDate endingBalanceCycleDate;
    private EDITBigDecimal endingDollarBalance;
    private EDITBigDecimal endingUnitBalance;
    private EDITBigDecimal endingShareBalance;
    private EDITBigDecimal DFCASHEndingShareBalance;
    private Long chargeCodeFK;
    private ProductFilteredFundStructure productFilteredFundStructure;

    private Set controlBalanceDetails;

    /**
     * Target database to be used for lookups, etc.
     */
    public static String DATABASE = SessionHelper.ENGINE;


    public ControlBalance()
    {
    }

    public Long getControlBalancePK()
    {
        return controlBalancePK;
    }

    public void setControlBalancePK(Long controlBalancePK)
    {
        this.controlBalancePK = controlBalancePK;
    }

    public Long getProductFilteredFundStructureFK()
    {
        return productFilteredFundStructureFK;
    }

    public void setProductFilteredFundStructureFK(Long productFilteredFundStructureFK)
    {
        this.productFilteredFundStructureFK = productFilteredFundStructureFK;
    }


    public EDITBigDecimal getEndingUnitBalance()
    {
        return endingUnitBalance;
    }

    public void setEndingUnitBalance(EDITBigDecimal endingUnitBalance)
    {
        this.endingUnitBalance = endingUnitBalance;
    }


    public EDITDate getEndingBalanceCycleDate()
    {
        return endingBalanceCycleDate;
    }

    public void setEndingBalanceCycleDate(EDITDate endingBalanceCycleDate)
    {
        this.endingBalanceCycleDate = endingBalanceCycleDate;
    }


    public EDITBigDecimal getDFCASHEndingShareBalance()
    {
        return DFCASHEndingShareBalance;
    }

    public void setDFCASHEndingShareBalance(EDITBigDecimal DFCASHEndingShareBalance)
    {
        this.DFCASHEndingShareBalance = DFCASHEndingShareBalance;
    }

    public EDITBigDecimal getEndingShareBalance()
    {
        return endingShareBalance;
    }

    public void setEndingShareBalance(EDITBigDecimal endingShareBalance)
    {
        this.endingShareBalance = endingShareBalance;
    }

    public Long getChargeCodeFK()
    {
        return chargeCodeFK;
    }

    public void setChargeCodeFK(Long chargeCodeFK)
    {
        this.chargeCodeFK = chargeCodeFK;
    }


    public EDITBigDecimal getEndingDollarBalance()
    {
        return endingDollarBalance;
    }

    public void setEndingDollarBalance(EDITBigDecimal endingDollarBalance)
    {
        this.endingDollarBalance = endingDollarBalance;
    }

    /**
     * Getter
     * @return
     */
    public ProductFilteredFundStructure getProductFilteredFundStructure()
    {
        return productFilteredFundStructure;
    }

    /**
     * setter
     */
    public void setProductFilteredFundStructure(ProductFilteredFundStructure productFilteredFundStructure)
    {
        this.productFilteredFundStructure = productFilteredFundStructure;
    }

    /**
     * Getter.
     * @return
     */
    public Set getControlBalanceDetails()
    {
        return controlBalanceDetails;
    }

    /**
     * Setter.
     * @param controlBalanceDetails
     */
    public void setControlBalanceDetails(Set controlBalanceDetails)
    {
        this.controlBalanceDetails = controlBalanceDetails;
    }

    /**
     * @see edit.services.db.hibernate.HibernateEntity#hSave()
     */
    public void hSave()
    {
    }

    /**
     * @see edit.services.db.hibernate.HibernateEntity#hDelete()
     */
    public void hDelete()
    {
    }

    /**
     * Gets the database this object belongs to
     *
     * @return  string containing the hibernate database name for this object
     */
    public String getDatabase()
    {
        return ControlBalance.DATABASE;
    }

    /**
     * Finder by PK.
     * @param scriptPK
     * @return
     */
    public static final ControlBalance findByPK(Long scriptPK)
    {
        return (ControlBalance) SessionHelper.get(ControlBalance.class, scriptPK, ControlBalance.DATABASE);
    }
}