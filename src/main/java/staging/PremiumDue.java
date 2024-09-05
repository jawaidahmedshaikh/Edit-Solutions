/*
 * User: dlataille
 * Date: Oct 2, 2007
 * Time: 11:18:32 AM
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */

package staging;

import edit.common.*;
import edit.services.db.hibernate.HibernateEntity;
import edit.services.db.hibernate.SessionHelper;

import java.util.Set;
import java.util.HashSet;

/**
 * Tracks the financial changes of the associated Billing entity over time as
 * the associated EDITTrxs are processed.
 *
 * @author gfrosti
 */
public class PremiumDue extends HibernateEntity
{
    private Long premiumDuePK;
    private Long segmentBaseFK;
    private SegmentBase segmentBase;
    private EDITBigDecimal billAmount;
    private EDITBigDecimal deductionAmount;
    private int numberOfDeductions;
    private EDITDate effectiveDate;
    private String pendingExtractIndicator;
    private EDITBigDecimal priorBillAmount;
    private EDITBigDecimal priorDeductionAmount;
    private EDITBigDecimal specialFrequencyPremium;
    private EDITBigDecimal deductionAmountOverride;
    private EDITBigDecimal billAmountOverride;

    private Set<CommissionPhase> commissionPhases;

    public static String DATABASE = SessionHelper.STAGING;

    /**
     * Creates a new instance of PremiumDue
     */
    public PremiumDue()
    {
        this.commissionPhases = new HashSet<CommissionPhase>();
    }

    /**
     * Getter.
     * @return
     */
    public Long getPremiumDuePK()
    {
        return premiumDuePK;
    }

    /**
     * Getter.
     * @param premiumDuePK
     */
    public void setPremiumDuePK(Long premiumDuePK)
    {
        this.premiumDuePK = premiumDuePK;
    }

    /**
     * Getter.
     * @return
     */
    public Long getSegmentBaseFK()
    {
        return segmentBaseFK;
    }

    /**
     * Setter.
     * @param segmentBaseFK
     */
    public void setSegmentBaseFK(Long segmentBaseFK)
    {
        this.segmentBaseFK = segmentBaseFK;
    }

    public SegmentBase getSegmentBase()
    {
        return this.segmentBase;
    }

    public void setSegmentBase(SegmentBase segmentBase)
    {
        this.segmentBase = segmentBase;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getBillAmount()
    {
        return billAmount;
    }

    /**
     * Setter.
     * @param billAmount
     */
    public void setBillAmount(EDITBigDecimal billAmount)
    {
        this.billAmount = billAmount;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getDeductionAmount()
    {
        return deductionAmount;
    }

    /**
     * Setter.
     * @param deductionAmount
     */
    public void setDeductionAmount(EDITBigDecimal deductionAmount)
    {
        this.deductionAmount = deductionAmount;
    }

    /**
     * Getter.
     * @return
     */
    public int getNumberOfDeductions()
    {
        return numberOfDeductions;
    }

    /**
     * Setter.
     * @param numberOfDeductions
     */
    public void setNumberOfDeductions(int numberOfDeductions)
    {
        this.numberOfDeductions = numberOfDeductions;
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
     * Getter.
     * @return
     */
    public String getPendingExtractIndicator()
    {
        return pendingExtractIndicator;
    }

    /**
     * Setter.
     * @param pendingExtractIndicator
     */
    public void setPendingExtractIndicator(String pendingExtractIndicator)
    {
        this.pendingExtractIndicator = pendingExtractIndicator;
    }

    /**
     * Setter.
     * @param priorBillAmount
     */
    public void setPriorBillAmount(EDITBigDecimal priorBillAmount)
    {
        this.priorBillAmount = priorBillAmount;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getPriorBillAmount()
    {
        return priorBillAmount;
    }

    /**
     * Setter.
     * @param priorDeductionAmount
     */
    public void setPriorDeductionAmount(EDITBigDecimal priorDeductionAmount)
    {
        this.priorDeductionAmount = priorDeductionAmount;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getPriorDeductionAmount()
    {
        return priorDeductionAmount;
    }

    /**
     * Setter.
     * @param specialFrequencyPremium
     */
    public void setSpecialFrequencyPremium(EDITBigDecimal specialFrequencyPremium)
    {
        this.specialFrequencyPremium = specialFrequencyPremium;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getSpecialFrequencyPremium()
    {
        return specialFrequencyPremium;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getBillAmountOverride()
    {
        return billAmountOverride;
    }

    /**
     * Setter.
     * @param billAmountOverride
     */
    public void setBillAmountOverride(EDITBigDecimal billAmountOverride)
    {
        this.billAmountOverride = billAmountOverride;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getDeductionAmountOverride()
    {
        return deductionAmountOverride;
    }

    /**
     * Setter.
     * @param deductionAmountOverride
     */
    public void setDeductionAmountOverride(EDITBigDecimal deductionAmountOverride)
    {
        this.deductionAmountOverride = deductionAmountOverride;
    }

    /**
     * Getter.
     * @return
     */
    public Set<CommissionPhase> getCommissionPhases()
    {
        return commissionPhases;
    }

    /**
     * Setter.
     * @param commissionPhases
     */
    public void setCommissionPhases(Set<CommissionPhase> commissionPhases)
    {
        this.commissionPhases = commissionPhases;
    }

    /**
     * Add another commissionPhase to the current mapped commissionPhases.
     * @param commissionPhase
     */
    public void addCommissionPhase(CommissionPhase commissionPhase)
    {
        this.commissionPhases.add(commissionPhase);
    }

    /**
     * Gets the database this object belongs to
     *
     * @return  string containing the hibernate database name for this object
     */
    public String getDatabase()
    {
        return PremiumDue.DATABASE;
    }
}
