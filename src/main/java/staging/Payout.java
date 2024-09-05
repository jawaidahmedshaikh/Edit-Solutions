/*
 * User: sprasad
 * Date: Apr 17, 2008
 * Time: 11:34:12 AM
 * 
 * (c) 2000-2008 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement
 */

package staging;

import edit.common.EDITBigDecimal;
import edit.common.EDITDate;

import edit.services.db.hibernate.HibernateEntity;
import edit.services.db.hibernate.SessionHelper;

import staging.SegmentBase;


public class Payout extends HibernateEntity
{
    public static final String DATABASE = SessionHelper.STAGING;

    private Long payoutPK;
    private Long segmentBaseFK;
    private EDITDate paymentStartDate;
    private int certainDuration;
    private String postJune1986Investment;
    private EDITBigDecimal paymentAmount;
    private EDITBigDecimal reducePercent1;
    private EDITBigDecimal reducePercent2;
    private EDITBigDecimal totalExpectedReturnAmount;
    private EDITBigDecimal finalDistributionAmount;
    private EDITBigDecimal exclusionRatio;
    private EDITBigDecimal yearlyTaxableBenefit;
    private EDITDate finalPaymentDate;
    private EDITDate lastCheckDate;
    private EDITDate nextPaymentDate;
    private EDITBigDecimal exclusionAmount;
    private EDITDate certainPeriodEndDate;
    private String paymentFrequency;
    private String lastDayOfMonthInd;

    private SegmentBase segmentBase;

    /**
     * Setter.
     * @param payoutPK
     */
    public void setPayoutPK(Long payoutPK)
    {
        this.payoutPK = payoutPK;
    }

    /**
     * Getter.
     * @return
     */
    public Long getPayoutPK()
    {
        return payoutPK;
    }

    /**
     * Setter.
     * @param segmentBaseFK
     */
    public void setSegmentBaseFK(Long segmentBaseFK)
    {
        this.segmentBaseFK = segmentBaseFK;
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
     * @param paymentStartDate
     */
    public void setPaymentStartDate(EDITDate paymentStartDate)
    {
        this.paymentStartDate = paymentStartDate;
    }

    /**
     * Getter.
     * @return
     */
    public EDITDate getPaymentStartDate()
    {
        return paymentStartDate;
    }

    /**
     * Setter.
     * @param certainDuration
     */
    public void setCertainDuration(int certainDuration)
    {
        this.certainDuration = certainDuration;
    }

    /**
     * Getter.
     * @return
     */
    public int getCertainDuration()
    {
        return certainDuration;
    }

    /**
     * Setter.
     * @param postJune1986Investment
     */
    public void setPostJune1986Investment(String postJune1986Investment)
    {
        this.postJune1986Investment = postJune1986Investment;
    }

    /**
     * Getter.
     * @return
     */
    public String getPostJune1986Investment()
    {
        return postJune1986Investment;
    }

    /**
     * Setter.
     * @param paymentAmount
     */
    public void setPaymentAmount(EDITBigDecimal paymentAmount)
    {
        this.paymentAmount = paymentAmount;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getPaymentAmount()
    {
        return paymentAmount;
    }

    
    /**
     * Setter.
     * @param reducePercent1
     */
    public void setReducePercent1(EDITBigDecimal reducePercent1)
    {
        this.reducePercent1 = reducePercent1;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getReducePercent1()
    {
        return reducePercent1;
    }

    /**
     * Setter.
     * @param reducePercent2
     */
    public void setReducePercent2(EDITBigDecimal reducePercent2)
    {
        this.reducePercent2 = reducePercent2;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getReducePercent2()
    {
        return reducePercent2;
    }

    /**
     * Setter.
     * @param totalExpectedReturnAmount
     */
    public void setTotalExpectedReturnAmount(EDITBigDecimal totalExpectedReturnAmount)
    {
        this.totalExpectedReturnAmount = totalExpectedReturnAmount;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getTotalExpectedReturnAmount()
    {
        return totalExpectedReturnAmount;
    }

    /**
     * Setter.
     * @param finalDistributionAmount
     */
    public void setFinalDistributionAmount(EDITBigDecimal finalDistributionAmount)
    {
        this.finalDistributionAmount = finalDistributionAmount;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getFinalDistributionAmount()
    {
        return finalDistributionAmount;
    }

    /**
     * Setter.
     * @param exclusionRatio
     */
    public void setExclusionRatio(EDITBigDecimal exclusionRatio)
    {
        this.exclusionRatio = exclusionRatio;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getExclusionRatio()
    {
        return exclusionRatio;
    }

    /**
     * Setter.
     * @param yearlyTaxableBenefit
     */
    public void setYearlyTaxableBenefit(EDITBigDecimal yearlyTaxableBenefit)
    {
        this.yearlyTaxableBenefit = yearlyTaxableBenefit;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getYearlyTaxableBenefit()
    {
        return yearlyTaxableBenefit;
    }

    /**
     * Setter.
     * @param finalPaymentDate
     */
    public void setFinalPaymentDate(EDITDate finalPaymentDate)
    {
        this.finalPaymentDate = finalPaymentDate;
    }

    /**
     * Getter.
     * @return
     */
    public EDITDate getFinalPaymentDate()
    {
        return finalPaymentDate;
    }

    /**
     * Setter.
     * @param lastCheckDate
     */
    public void setLastCheckDate(EDITDate lastCheckDate)
    {
        this.lastCheckDate = lastCheckDate;
    }

    /**
     * Getter.
     * @return
     */
    public EDITDate getLastCheckDate()
    {
        return lastCheckDate;
    }
    
    /**
     * Setter.
     * @param nextPaymentDate
     */
    public void setNextPaymentDate(EDITDate nextPaymentDate)
    {
        this.nextPaymentDate = nextPaymentDate;
    }

    /**
     * Getter.
     * @return
     */
    public EDITDate getNextPaymentDate()
    {
        return nextPaymentDate;
    }

    /**
     * Setter.
     * @param exclusionAmount
     */
    public void setExclusionAmount(EDITBigDecimal exclusionAmount)
    {
        this.exclusionAmount = exclusionAmount;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getExclusionAmount()
    {
        return exclusionAmount;
    }

    /**
     * Setter.
     * @param certainPeriodEndDate
     */
    public void setCertainPeriodEndDate(EDITDate certainPeriodEndDate)
    {
        this.certainPeriodEndDate = certainPeriodEndDate;
    }

    /**
     * Getter.
     * @return
     */
    public EDITDate getCertainPeriodEndDate()
    {
        return certainPeriodEndDate;
    }

    /**
     * Setter.
     * @param paymentFrequency
     */
    public void setPaymentFrequency(String paymentFrequency)
    {
        this.paymentFrequency = paymentFrequency;
    }

    /**
     * Getter.
     * @return
     */
    public String getPaymentFrequency()
    {
        return paymentFrequency;
    }

    /**
     * Setter.
     * @param lastDayOfMonthInd
     */
    public void setLastDayOfMonthInd(String lastDayOfMonthInd)
    {
        this.lastDayOfMonthInd = lastDayOfMonthInd;
    }

    /**
     * Getter.
     * @return
     */
    public String getLastDayOfMonthInd()
    {
        return lastDayOfMonthInd;
    }

    /**
     * Setter.
     * @param segmentBase
     */
    public void setSegmentBase(SegmentBase segmentBase)
    {
        this.segmentBase = segmentBase;
    }

    /**
     * Getter.
     * @return
     */
    public SegmentBase getSegmentBase()
    {
        return segmentBase;
    }
    
    /**
     * Getter.
     * @return
     */
    public String getDatabase()
    {
        return this.DATABASE;
    }
}
