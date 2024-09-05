/*
 * User: dlataille
 * Date: June 25, 2007
 * Time: 2:24:42 PM
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package group;

import edit.common.*;
import edit.services.db.hibernate.*;

import java.util.*;


public class ContractGroupInvestment extends HibernateEntity
{
    private Long contractGroupInvestmentPK;

    private EDITDate effectiveDate;
    private EDITDate processDate;
    private EDITBigDecimal accumulatedUnits;
    private Long filteredFundFK;
    private Long chargeCodeFK;

    // parent
    private ContractGroup contractGroup;

    private Long contractGroupFK;

    public static final String DATABASE = SessionHelper.EDITSOLUTIONS;


    public ContractGroupInvestment()
    {

    }

    /**
     * Getter
     * @return
     */
    public Long getContractGroupInvestmentPK()
    {
        return contractGroupInvestmentPK;
    }

    /**
     * Setter
     * @param contractGroupInvestmentPK
     */
    public void setContractGroupInvestmentPK(Long contractGroupInvestmentPK)
    {
        this.contractGroupInvestmentPK = contractGroupInvestmentPK;
    }

    /**
     * Getter
     * @return
     */
    public EDITDate getEffectiveDate()
    {
        return effectiveDate;
    }

    /**
     * Setter
     * @param effectiveDate
     */
    public void setEffectiveDate(EDITDate effectiveDate)
    {
        this.effectiveDate = effectiveDate;
    }

    /**
     * Getter
     * @return
     */
    public EDITDate getProcessDate()
    {
        return processDate;
    }

    /**
     * Setter
     * @param processDate
     */
    public void setProcessDate(EDITDate processDate)
    {
        this.processDate = processDate;
    }

    /**
     * Getter
     * @return
     */
    public EDITBigDecimal getAccumulatedUnits()
    {
        return accumulatedUnits;
    }

    /**
     * Setter
     * @param accumulatedUnits
     */
    public void setAccumulatedUnits(EDITBigDecimal accumulatedUnits)
    {
        this.accumulatedUnits = accumulatedUnits;
    }

    /**
     * Getter
     * @return
     */
    public Long getFilteredFundFK()
    {
        return filteredFundFK;
    }

    /**
     * Setter
     * @param filteredFundFK
     */
    public void setFilteredFundFK(Long filteredFundFK)
    {
        this.filteredFundFK = filteredFundFK;
    }

    /**
     * Getter
     * @return
     */
    public Long getChargeCodeFK()
    {
        return chargeCodeFK;
    }

    /**
     * Setter
     * @param chargeCodeFK
     */
    public void setChargeCodeFK(Long chargeCodeFK)
    {
        this.chargeCodeFK = chargeCodeFK;
    }

    /**
     * Getter
     * @return
     */
    public ContractGroup getContractGroup()
    {
        return contractGroup;
    }

    /**
     * Setter
     * @param contractGroup
     */
    public void setContractGroup(ContractGroup contractGroup)
    {
        this.contractGroup = contractGroup;
    }

    public Long getContractGroupFK()
    {
        return this.contractGroupFK;
    }

    public void setContractGroupFK(Long contractGroupFK)
    {
        this.contractGroupFK = contractGroupFK;
    }

    /**
     * Save the entity using Hibernate
     */
    public void hSave()
    {
        SessionHelper.saveOrUpdate(this, ContractGroupInvestment.DATABASE);
    }

    /**
     * Delete the entity using Hibernate
     */
    public void hDelete()
    {
        SessionHelper.delete(this, ContractGroupInvestment.DATABASE);
    }

    /**
     * Find by primary key
     *
     * @param contractGroupInvestmentPK
     *
     * @return  single ContractGroupInvestment
     */
    public static ContractGroupInvestment findByPK(Long contractGroupInvestmentPK)
    {
        return (ContractGroupInvestment) SessionHelper.get(ContractGroupInvestment.class, contractGroupInvestmentPK, ContractGroupInvestment.DATABASE);
    }

    public static ContractGroupInvestment[] findByContractGroupFK(Long contractGroupFK)
    {
        String hql = "select contractGroupInvestment from ContractGroupInvestment contractGroupInvestment " +
                     " where contractGroupInvestment.ContractGroupFK = :contractGroupFK";

        Map params = new HashMap();
        params.put("contractGroupFK", contractGroupFK);

        List results = SessionHelper.executeHQL(hql, params, ContractGroupInvestment.DATABASE);

        return (ContractGroupInvestment[]) results.toArray(new ContractGroupInvestment[results.size()]);
    }

    public static ContractGroupInvestment findBy_ContractGroupFK_FilteredFundFK_ChargeCodeFK_EffectiveDate_ProcessDate(
            Long contractGroupFK, Long filteredFundFK, Long chargeCodeFK, EDITDate effectiveDate, EDITDate processDate)
    {
        ContractGroupInvestment contractGroupInvestment = null;

        String hql = "select contractGroupInvestment from ContractGroupInvestment contractGroupInvestment " +
                     " where contractGroupInvestment.ContractGroupFK = :contractGroupFK" +
                     " and contractGroupInvestment.FilteredFundFK = :filteredFundFK" +
                     " and contractGroupInvestment.ChargeCodeFK = :chargeCodeFK" +
                     " and contractGroupInvestment.EffectiveDate = :effectiveDate" +
                     " and contractGroupInvestment.ProcessDate = :processDate";

        Map params = new HashMap();
        params.put("contractGroupFK", contractGroupFK);
        params.put("filteredFundFK", filteredFundFK);
        params.put("chargeCodeFK", chargeCodeFK);
        params.put("effectiveDate", effectiveDate);
        params.put("processDate", processDate);

        List results = SessionHelper.executeHQL(hql, params, ContractGroupInvestment.DATABASE);

        if (! results.isEmpty())
        {
            contractGroupInvestment = (ContractGroupInvestment) results.get(0);
        }

        return contractGroupInvestment;
    }

    public void increaseAccumulatedUnits(EDITBigDecimal numberOfUnits)
    {
        this.accumulatedUnits = this.accumulatedUnits.addEditBigDecimal(numberOfUnits);
    }

    public void decreaseAccumulatedUnits(EDITBigDecimal numberOfUnits)
    {
        this.accumulatedUnits = this.accumulatedUnits.subtractEditBigDecimal(numberOfUnits);
    }

    public String getDatabase()
    {
        return ContractGroupInvestment.DATABASE;
    }
}
