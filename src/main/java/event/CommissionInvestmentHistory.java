/*
 * User: sdorman
 * Date: Nov 29, 2005
 * Time: 12:17:56 PM
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package event;


import edit.common.*;

import edit.services.db.hibernate.*;

import contract.*;


public class CommissionInvestmentHistory extends HibernateEntity
{
    private Long commissionInvestmentHistoryPK;

    // parents
    private CommissionHistory commissionHistory;
    private Long commissionHistoryFK;
    private Investment investment;
    private Long investmentFK;

    // fields
    private EDITBigDecimal commissionAmount;

    /**
     * Target database to be used for lookups, etc.
     */
    public static String DATABASE = SessionHelper.EDITSOLUTIONS;


    /**
     * Save the entity using Hibernate
     */
    public void hSave()
    {
        SessionHelper.saveOrUpdate(this, CommissionInvestmentHistory.DATABASE);
    }

    /**
     * Delete the entity using Hibernate
     */
    public void hDelete()
    {
        SessionHelper.delete(this, CommissionInvestmentHistory.DATABASE);
    }

    public Long getCommissionInvestmentHistoryPK()
    {
        return this.commissionInvestmentHistoryPK;
    }

    public void setCommissionInvestmentHistoryPK(Long commissionInvestmentHistoryPK)
    {
        this.commissionInvestmentHistoryPK = commissionInvestmentHistoryPK;
    }

    public CommissionHistory getCommissionHistory()
    {
        return this.commissionHistory;
    }

    public void setCommissionHistory(CommissionHistory commissionHistory)
    {
        this.commissionHistory = commissionHistory;
    }

    public Long getCommissionHistoryFK()
    {
        return this.commissionHistoryFK;
    }

    public void setCommissionHistoryFK(Long commissionHistoryFK)
    {
        this.commissionHistoryFK = commissionHistoryFK;
    }

    public Investment getInvestment()
    {
        return this.investment;
    }

    public void setInvestment(Investment investment)
    {
        this.investment = investment;
    }

    public Long getInvestmentFK()
    {
        return this.investmentFK;
    }

    public void setInvestmentFK(Long investmentFK)
    {
        this.investmentFK = investmentFK;
    }

    public EDITBigDecimal getCommissionAmount()
    {
        return this.commissionAmount;
    }

    public void setCommissionAmount(EDITBigDecimal commissionAmount)
    {
        this.commissionAmount = commissionAmount;
    }

    /**
     * Gets the database this object belongs to
     *
     * @return  string containing the hibernate database name for this object
     */
    public String getDatabase()
    {
        return CommissionInvestmentHistory.DATABASE;
    }
}
