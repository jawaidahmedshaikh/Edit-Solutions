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

import java.util.Set;

public class InvestmentBucket extends HibernateEntity 
{
    public static String DATABASE = SessionHelper.STAGING;

    private Long investmentBucketPK;

    private EDITBigDecimal accruedLoanInterest;
    private EDITBigDecimal previousLoanInterestRate;
    private EDITBigDecimal loanInterestRate;
    private EDITBigDecimal loanPrincipalRemaining;
    private EDITBigDecimal loanCumDollars;
    private String bucketSource;
    private EDITDate lockupEndDate;
    private int bonusIntRateDur;
    private EDITBigDecimal bonusIntRate;
    private EDITBigDecimal unearnedInterest;
    private EDITBigDecimal depositAmountGain;
    private EDITBigDecimal rebalanceAmount;
    private EDITBigDecimal indexCapRate;
    private EDITBigDecimal bonusAmount;
    private EDITBigDecimal guarCumValue;
    private EDITDate lastRenewalDate;
    private EDITDate renewalDate;
    private EDITBigDecimal payoutDollars;
    private EDITBigDecimal payoutUnits;
    private int durationOverride;
    private EDITBigDecimal priorBucketRate;
    private EDITBigDecimal bucketInterestRate;
    private EDITBigDecimal interestRateOverride;
    private EDITDate lastValuationDate;
    private EDITBigDecimal depositAmount;
    private EDITBigDecimal cumDollars;
    private EDITBigDecimal cumUnits;
    private EDITDate depositDate;
    private EDITBigDecimal unearnedLoanInterest;
    private EDITDate interestPaidThroughDate;
    private EDITBigDecimal billedLoanInterest;

    private Long investmentFK;
    
    private Investment investment;
    
    private Set<SubBucket> subBuckets;
    
    /**
     * Setter.
     * @param investmentBucketPK
     */
    public void setInvestmentBucketPK(Long investmentBucketPK)
    {
        this.investmentBucketPK = investmentBucketPK;
    }

    /**
     * Getter.
     * @return
     */
    public Long getInvestmentBucketPK()
    {
        return investmentBucketPK;
    }

    /**
     * Setter.
     * @param accruedLoanInterest
     */
    public void setAccruedLoanInterest(EDITBigDecimal accruedLoanInterest)
    {
        this.accruedLoanInterest = accruedLoanInterest;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getAccruedLoanInterest()
    {
        return accruedLoanInterest;
    }

    /**
     * Setter.
     * @param previousLoanInterestRate
     */
    public void setPreviousLoanInterestRate(EDITBigDecimal previousLoanInterestRate)
    {
        this.previousLoanInterestRate = previousLoanInterestRate;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getPreviousLoanInterestRate()
    {
        return previousLoanInterestRate;
    }

    /**
     * Setter.
     * @param loanInterestRate
     */
    public void setLoanInterestRate(EDITBigDecimal loanInterestRate)
    {
        this.loanInterestRate = loanInterestRate;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getLoanInterestRate()
    {
        return loanInterestRate;
    }

    /**
     * Setter.
     * @param loanPrincipalRemaining
     */
    public void setLoanPrincipalRemaining(EDITBigDecimal loanPrincipalRemaining)
    {
        this.loanPrincipalRemaining = loanPrincipalRemaining;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getLoanPrincipalRemaining()
    {
        return loanPrincipalRemaining;
    }

    /**
     * Setter.
     * @param loanCumDollars
     */
    public void setLoanCumDollars(EDITBigDecimal loanCumDollars)
    {
        this.loanCumDollars = loanCumDollars;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getLoanCumDollars()
    {
        return loanCumDollars;
    }

    /**
     * Setter.
     * @param bucketSource
     */
    public void setBucketSource(String bucketSource)
    {
        this.bucketSource = bucketSource;
    }

    /**
     * Getter.
     * @return
     */
    public String getBucketSource()
    {
        return bucketSource;
    }

    /**
     * Setter.
     * @param lockupEndDate
     */
    public void setLockupEndDate(EDITDate lockupEndDate)
    {
        this.lockupEndDate = lockupEndDate;
    }

    /**
     * Getter.
     * @return
     */
    public EDITDate getLockupEndDate()
    {
        return lockupEndDate;
    }

    /**
     * Setter.
     * @param bonusIntRateDur
     */
    public void setBonusIntRateDur(int bonusIntRateDur)
    {
        this.bonusIntRateDur = bonusIntRateDur;
    }

    /**
     * Getter.
     * @return
     */
    public int getBonusIntRateDur()
    {
        return bonusIntRateDur;
    }

    /**
     * Setter.
     * @param bonusIntRate
     */
    public void setBonusIntRate(EDITBigDecimal bonusIntRate)
    {
        this.bonusIntRate = bonusIntRate;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getBonusIntRate()
    {
        return bonusIntRate;
    }

    /**
     * Setter.
     * @param unearnedInterest
     */
    public void setUnearnedInterest(EDITBigDecimal unearnedInterest)
    {
        this.unearnedInterest = unearnedInterest;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getUnearnedInterest()
    {
        return unearnedInterest;
    }

    /**
     * Setter.
     * @param depositAmountGain
     */
    public void setDepositAmountGain(EDITBigDecimal depositAmountGain)
    {
        this.depositAmountGain = depositAmountGain;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getDepositAmountGain()
    {
        return depositAmountGain;
    }

    /**
     * Setter.
     * @param rebalanceAmount
     */
    public void setRebalanceAmount(EDITBigDecimal rebalanceAmount)
    {
        this.rebalanceAmount = rebalanceAmount;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getRebalanceAmount()
    {
        return rebalanceAmount;
    }

    /**
     * Setter.
     * @param indexCapRate
     */
    public void setIndexCapRate(EDITBigDecimal indexCapRate)
    {
        this.indexCapRate = indexCapRate;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getIndexCapRate()
    {
        return indexCapRate;
    }

    /**
     * Setter.
     * @param bonusAmount
     */
    public void setBonusAmount(EDITBigDecimal bonusAmount)
    {
        this.bonusAmount = bonusAmount;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getBonusAmount()
    {
        return bonusAmount;
    }

    /**
     * Setter.
     * @param guarCumValue
     */
    public void setGuarCumValue(EDITBigDecimal guarCumValue)
    {
        this.guarCumValue = guarCumValue;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getGuarCumValue()
    {
        return guarCumValue;
    }

    /**
     * Setter.
     * @param lastRenewalDate
     */
    public void setLastRenewalDate(EDITDate lastRenewalDate)
    {
        this.lastRenewalDate = lastRenewalDate;
    }

    /**
     * Getter.
     * @return
     */
    public EDITDate getLastRenewalDate()
    {
        return lastRenewalDate;
    }

    /**
     * Setter.
     * @param renewalDate
     */
    public void setRenewalDate(EDITDate renewalDate)
    {
        this.renewalDate = renewalDate;
    }

    /**
     * Getter.
     * @return
     */
    public EDITDate getRenewalDate()
    {
        return renewalDate;
    }

    /**
     * Setter.
     * @param payoutDollars
     */
    public void setPayoutDollars(EDITBigDecimal payoutDollars)
    {
        this.payoutDollars = payoutDollars;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getPayoutDollars()
    {
        return payoutDollars;
    }

    /**
     * Setter.
     * @param payoutUnits
     */
    public void setPayoutUnits(EDITBigDecimal payoutUnits)
    {
        this.payoutUnits = payoutUnits;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getPayoutUnits()
    {
        return payoutUnits;
    }

    /**
     * Setter.
     * @param durationOverride
     */
    public void setDurationOverride(int durationOverride)
    {
        this.durationOverride = durationOverride;
    }

    /**
     * Getter.
     * @return
     */
    public int getDurationOverride()
    {
        return durationOverride;
    }

    /**
     * Setter.
     * @param priorBucketRate
     */
    public void setPriorBucketRate(EDITBigDecimal priorBucketRate)
    {
        this.priorBucketRate = priorBucketRate;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getPriorBucketRate()
    {
        return priorBucketRate;
    }

    /**
     * Setter.
     * @param bucketInterestRate
     */
    public void setBucketInterestRate(EDITBigDecimal bucketInterestRate)
    {
        this.bucketInterestRate = bucketInterestRate;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getBucketInterestRate()
    {
        return bucketInterestRate;
    }

    /**
     * Setter.
     * @param interestRateOverride
     */
    public void setInterestRateOverride(EDITBigDecimal interestRateOverride)
    {
        this.interestRateOverride = interestRateOverride;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getInterestRateOverride()
    {
        return interestRateOverride;
    }

    /**
     * Setter.
     * @param lastValuationDate
     */
    public void setLastValuationDate(EDITDate lastValuationDate)
    {
        this.lastValuationDate = lastValuationDate;
    }

    /**
     * Getter.
     * @return
     */
    public EDITDate getLastValuationDate()
    {
        return lastValuationDate;
    }

    /**
     * Setter.
     * @param depositAmount
     */
    public void setDepositAmount(EDITBigDecimal depositAmount)
    {
        this.depositAmount = depositAmount;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getDepositAmount()
    {
        return depositAmount;
    }

    /**
     * Setter.
     * @param cumDollars
     */
    public void setCumDollars(EDITBigDecimal cumDollars)
    {
        this.cumDollars = cumDollars;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getCumDollars()
    {
        return cumDollars;
    }

    /**
     * Setter.
     * @param cumUnits
     */
    public void setCumUnits(EDITBigDecimal cumUnits)
    {
        this.cumUnits = cumUnits;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getCumUnits()
    {
        return cumUnits;
    }

    /**
     * Setter.
     * @param depositDate
     */
    public void setDepositDate(EDITDate depositDate)
    {
        this.depositDate = depositDate;
    }

    /**
     * Getter.
     * @return
     */
    public EDITDate getDepositDate()
    {
        return depositDate;
    }

    /**
     * Setter.
     * @param unearnedLoanInterest
     */
    public void setUnearnedLoanInterest(EDITBigDecimal unearnedLoanInterest)
    {
        this.unearnedLoanInterest = unearnedLoanInterest;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getUnearnedLoanInterest()
    {
        return unearnedLoanInterest;
    }

    /**
     * Setter.
     * @param interestPaidThroughDate
     */
    public void setInterestPaidThroughDate(EDITDate interestPaidThroughDate)
    {
        this.interestPaidThroughDate = interestPaidThroughDate;
    }

    /**
     * Getter.
     * @return
     */
    public EDITDate getInterestPaidThroughDate()
    {
        return interestPaidThroughDate;
    }

    /**
     * Setter.
     * @param billedLoanInterest
     */
    public void setBilledLoanInterest(EDITBigDecimal billedLoanInterest)
    {
        this.billedLoanInterest = billedLoanInterest;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getBilledLoanInterest()
    {
        return billedLoanInterest;
    }

    /**
     * Setter.
     * @param investmentFK
     */
    public void setInvestmentFK(Long investmentFK)
    {
        this.investmentFK = investmentFK;
    }

    /**
     * Getter.
     * @return
     */
    public Long getInvestmentFK()
    {
        return investmentFK;
    }
    
    /**
     * Setter.
     * @param investment
     */
    public void setInvestment(Investment investment)
    {
        this.investment = investment;
    }

    /**
     * Getter.
     * @return
     */
    public Investment getInvestment()
    {
        return investment;
    }

    /**
     * Setter.
     * @param subBuckets
     */
    public void setSubBuckets(Set<SubBucket> subBuckets)
    {
        this.subBuckets = subBuckets;
    }

    /**
     * Getter.
     * @return
     */
    public Set<SubBucket> getSubBuckets()
    {
        return subBuckets;
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
