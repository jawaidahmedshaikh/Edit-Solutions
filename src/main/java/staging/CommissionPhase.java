/*
 * User: dlataille
 * Date: Dec 24, 2007
 * Time: 9:28:32 AM
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */

package staging;

import edit.common.*;
import edit.services.db.hibernate.HibernateEntity;
import edit.services.db.hibernate.SessionHelper;

/**
 * Tracks the financial changes of the associated Billing entity over time as
 * the associated EDITTrxs are processed.
 *
 * @author gfrosti
 */
@SuppressWarnings("serial")
public class CommissionPhase extends HibernateEntity
{
    private Long commissionPhasePK;
    private Long premiumDueFK;
    private PremiumDue premiumDue;
    private int commissionPhaseId;
    private EDITBigDecimal expectedMonthlyPremium;
    private EDITBigDecimal prevCumExpectedMonthlyPrem;
    private EDITDate effectiveDate;

    public static String DATABASE = SessionHelper.STAGING;

    /**
     * Creates a new instance of CommissionPhase
     */
    public CommissionPhase()
    {
    }

    /**
     * Getter.
     * @return
     */
    public Long getCommissionPhasePK()
    {
        return commissionPhasePK;
    }

    /**
     * Getter.
     * @param premiumDuePK
     */
    public void setCommissionPhasePK(Long commissionPhasePK)
    {
        this.commissionPhasePK = commissionPhasePK;
    }

    /**
     * Getter.
     * @return
     */
    public Long getPremiumDueFK()
    {
        return premiumDueFK;
    }

    /**
     * Setter.
     * @param premiumDueFK
     */
    public void setPremiumDueFK(Long premiumDueFK)
    {
        this.premiumDueFK = premiumDueFK;
    }

    public PremiumDue getPremiumDue()
    {
        return this.premiumDue;
    }

    public void setPremiumDue(PremiumDue premiumDue)
    {
        this.premiumDue = premiumDue;
    }

    /**
     * Getter.
     * @return
     */
    public int getCommissionPhaseId()
    {
        return commissionPhaseId;
    }

    /**
     * Setter.
     * @param commissionPhaseId
     */
    public void setCommissionPhaseId(int commissionPhaseId)
    {
        this.commissionPhaseId = commissionPhaseId;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getExpectedMonthlyPremium()
    {
        return expectedMonthlyPremium;
    }

    /**
     * Setter.
     * @param expectedMonthlyPremium
     */
    public void setExpectedMonthlyPremium(EDITBigDecimal expectedMonthlyPremium)
    {
        this.expectedMonthlyPremium = expectedMonthlyPremium;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getPrevCumExpectedMonthlyPrem()
    {
        return prevCumExpectedMonthlyPrem;
    }

    /**
     * Setter.
     * @param prevCumExpectedMonthlyPrem
     */
    public void setPrevCumExpectedMonthlyPrem(EDITBigDecimal prevCumExpectedMonthlyPrem)
    {
        this.prevCumExpectedMonthlyPrem = prevCumExpectedMonthlyPrem;
    }

    /**
     * Getter.
     * @return
     */
    public EDITDate getEffectiveDate()
    {
        return this.effectiveDate;
    }

    /**
     * Setter.
     * @param effectiveDate
     */
    public void setEffectiveDate(EDITDate effectiveDate)
    {
        this.effectiveDate = effectiveDate;
    }

    /**
     * Gets the database this object belongs to
     *
     * @return  string containing the hibernate database name for this object
     */
    public String getDatabase()
    {
        return CommissionPhase.DATABASE;
    }

}
