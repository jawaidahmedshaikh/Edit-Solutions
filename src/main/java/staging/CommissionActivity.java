/*
 * User: dlataill
 * Date: Oct 9, 2007
 * Time: 4:09:18 PM
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package staging;

import edit.services.db.hibernate.HibernateEntity;
import edit.services.db.hibernate.SessionHelper;
import edit.common.EDITBigDecimal;


public class CommissionActivity extends HibernateEntity
{
    private Long commissionActivityPK;
    private Long financialActivityFK;
    private Long agentFK;
    private EDITBigDecimal commissionAmount;
    private EDITBigDecimal commissionTaxable;
    private EDITBigDecimal commissionNonTaxable;
    private EDITBigDecimal commissionRate;
    
    private EDITBigDecimal advancePercent;
    private EDITBigDecimal recoveryPercent;
    private EDITBigDecimal allocationPercent;
    
    private String commissionType;
    private String statementInd;
    private String reduceTaxable;
    private EDITBigDecimal debitBalanceAmount;
    private String includedInDebitBalInd;
    private String agentNumber;
    private String forceoutMinBalInd;

    private FinancialActivity financialActivity;
    private Agent agent;

    /**
     * Target database to be used for lookups, etc.
     */
    public static String DATABASE = SessionHelper.STAGING;


    public CommissionActivity()
    {
    }

    /**
     * Getter.
     * @return
     */
    public Long getCommissionActivityPK()
    {
        return commissionActivityPK;
    }

    /**
     * Setter.
     * @param commissionActivityPK
     */
    public void setCommissionActivityPK(Long commissionActivityPK)
    {
        this.commissionActivityPK = commissionActivityPK;
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
    public Long getAgentFK()
    {
        return agentFK;
    }

    /**
     * Setter.
     * @param agentFK
     */
    public void setAgentFK(Long agentFK)
    {
        this.agentFK = agentFK;
    }

    /**
     * Getter.
     * @return
     */
    public Agent getAgent()
    {
        return agent;
    }

    /**
     * Setter.
     * @param agent
     */
    public void setAgent(Agent agent)
    {
        this.agent = agent;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getCommissionAmount()
    {
        return commissionAmount;
    }

    /**
     * Setter.
     * @param commissionAmount
     */
    public void setCommissionAmount(EDITBigDecimal commissionAmount)
    {
        this.commissionAmount = commissionAmount;
    }

    /**
    * Getter.
    * @return
    */
    public EDITBigDecimal getCommissionTaxable()
    {
        return commissionTaxable;
    }

    /**
     * Setter.
     * @param commissionTaxable
     */
    public void setCommissionTaxable(EDITBigDecimal commissionTaxable)
    {
        this.commissionTaxable = commissionTaxable;
    }

    /**
    * Getter.
    * @return
    */
    public EDITBigDecimal getCommissionNonTaxable()
    {
        return commissionNonTaxable;
    }

    /**
     * Setter.
     * @param commissionNonTaxable
     */
    public void setCommissionNonTaxable(EDITBigDecimal commissionNonTaxable)
    {
        this.commissionNonTaxable = commissionNonTaxable;
    }

    /**
    * Getter.
    * @return
    */
    public EDITBigDecimal getCommissionRate()
    {
        return commissionRate;
    }

    /**
     * Setter.
     * @param commissionRate
     */
    public void setCommissionRate(EDITBigDecimal commissionRate)
    {
        this.commissionRate = commissionRate;
    }

    /**
    * Getter.
    * @return
    */
    public String getCommissionType()
    {
        return commissionType;
    }

    /**
     * Setter.
     * @param commissionType
     */
    public void setCommissionType(String commissionType)
    {
        this.commissionType = commissionType;
    }

    /**
    * Getter.
    * @return
    */
    public String getStatementInd()
    {
        return statementInd;
    }

    /**
     * Setter.
     * @param statementInd
     */
    public void setStatementInd(String statementInd)
    {
        this.statementInd = statementInd;
    }

    /**
    * Getter.
    * @return
    */
    public String getReduceTaxable()
    {
        return reduceTaxable;
    }

    /**
     * Setter.
     * @param reduceTaxable
     */
    public void setReduceTaxable(String reduceTaxable)
    {
        this.reduceTaxable = reduceTaxable;
    }

    /**
    * Getter.
    * @return
    */
    public EDITBigDecimal getDebitBalanceAmount()
    {
        return debitBalanceAmount;
    }

    /**
     * Setter.
     * @param debitBalanceAmount
     */
    public void setDebitBalanceAmount(EDITBigDecimal debitBalanceAmount)
    {
        this.debitBalanceAmount = debitBalanceAmount;
    }

    /**
    * Getter.
    * @return
    */
    public String getIncludedInDebitBalInd()
    {
        return includedInDebitBalInd;
    }

    /**
     * Setter.
     * @param includedInDebitBalInd
     */
    public void setIncludedInDebitBalInd(String includedInDebitBalInd)
    {
        this.includedInDebitBalInd = includedInDebitBalInd;
    }

    /**
     * Getter.
     * @return
     */
    public String getAgentNumber()
    {
        return agentNumber;
    }

    /**
     * Setter.
     * @param agentNumber
     */
    public void setAgentNumber(String agentNumber)
    {
        this.agentNumber = agentNumber;
    }

    /**
     * Setter.
     * @param forceoutMinBalInd
     */
    public void setForceoutMinBalInd(String forceoutMinBalInd)
    {
        this.forceoutMinBalInd = forceoutMinBalInd;
    }

    /**
     * Getter.
     * @return
     */
    public String getForceoutMinBalInd()
    {
        return forceoutMinBalInd;
    }

    public EDITBigDecimal getAdvancePercent() {
		return advancePercent;
	}

	public void setAdvancePercent(EDITBigDecimal advancePercent) {
		this.advancePercent = advancePercent;
	}

	public EDITBigDecimal getRecoveryPercent() {
		return recoveryPercent;
	}

	public void setRecoveryPercent(EDITBigDecimal recoveryPercent) {
		this.recoveryPercent = recoveryPercent;
	}

	public EDITBigDecimal getAllocationPercent() {
		return allocationPercent;
	}

	public void setAllocationPercent(EDITBigDecimal allocationPercent) {
		this.allocationPercent = allocationPercent;
	}

	public String getDatabase()
    {
        return CommissionActivity.DATABASE;
    }
}
