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
import java.util.HashSet;

public class Investment extends HibernateEntity
{
    public static final String DATABASE = SessionHelper.STAGING;

    private Long investmentPK;
    private Long SegmentBaseFK;
    private String fundType;
    private String fundNumber;
    private EDITBigDecimal plannedAllocation;
    private EDITBigDecimal guarMinCashValueInterestRate;
    private EDITBigDecimal currentInterestRate;
    private EDITDate currentFixedInterestEndDate;
    private EDITBigDecimal startingMVAIndexRate;
    private EDITBigDecimal priorAnnivIndexValue;
    private EDITBigDecimal priorAnnivAccountValue;
    private EDITBigDecimal withdrawalsSincePriorAnniv;
    private EDITBigDecimal currentAccountValue;    
    private EDITBigDecimal currentMinCashSurrenderValue;
    private EDITBigDecimal indexCapRate;
    private int indexCapRateGuarTerm;
    private EDITBigDecimal indexCapMinimum;
    private EDITBigDecimal participationRate;
    private EDITBigDecimal indexMargin;
    private EDITBigDecimal interestRateAsOf1231;   
    private EDITBigDecimal currentSurrenderCharge;   
    private EDITBigDecimal premiumBonusAmount;   
    private EDITBigDecimal firstYearBonus;
    private EDITBigDecimal grossPremiumCollected;   
    private EDITBigDecimal bonusInterestRate;   
    private int bonusRateDuration;   
    private EDITBigDecimal rebalanceAmount;
    private EDITBigDecimal minGuarRate;
    private EDITBigDecimal freeAmountRemaining;
    private int fundGuaranteedDuration;
    private String indexingMethod;
    
    private SegmentBase segmentBase;

    private Set<InvestmentBucket> investmentBuckets;

    public Investment()
    {
        this.investmentBuckets = new HashSet<InvestmentBucket>();
    }

    /**
     * Setter.
     * @param investmentPK
     */
    public void setInvestmentPK(Long investmentPK)
    {
        this.investmentPK = investmentPK;
    }

    /**
     * Getter.
     * @return
     */
    public Long getInvestmentPK()
    {
        return investmentPK;
    }

    /**
     * Setter.
     * @param fundType
     */
    public void setFundType(String fundType)
    {
        this.fundType = fundType;
    }

    /**
     * Getter.
     * @return
     */
    public String getFundType()
    {
        return fundType;
    }

    /**
     * Setter.
     * @param fundNumber
     */
    public void setFundNumber(String fundNumber)
    {
        this.fundNumber = fundNumber;
    }

    /**
     * Getter.
     * @return
     */
    public String getFundNumber()
    {
        return fundNumber;
    }

    /**
     * Setter.
     * @param plannedAllocation
     */
    public void setPlannedAllocation(EDITBigDecimal plannedAllocation)
    {
        this.plannedAllocation = plannedAllocation;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getPlannedAllocation()
    {
        return plannedAllocation;
    }

    /**
     * Setter.
     * @param guarMinCashValueInterestRate
     */
    public void setGuarMinCashValueInterestRate(EDITBigDecimal guarMinCashValueInterestRate)
    {
        this.guarMinCashValueInterestRate = guarMinCashValueInterestRate;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getGuarMinCashValueInterestRate()
    {
        return guarMinCashValueInterestRate;
    }

    /**
     * Setter.
     * @param currentInterestRate
     */
    public void setCurrentInterestRate(EDITBigDecimal currentInterestRate)
    {
        this.currentInterestRate = currentInterestRate;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getCurrentInterestRate()
    {
        return currentInterestRate;
    }

    /**
     * Setter.
     * @param currentFixedInterestEndDate
     */
    public void setCurrentFixedInterestEndDate(EDITDate currentFixedInterestEndDate)
    {
        this.currentFixedInterestEndDate = currentFixedInterestEndDate;
    }

    /**
     * Getter.
     * @return
     */
    public EDITDate getCurrentFixedInterestEndDate()
    {
        return currentFixedInterestEndDate;
    }

    /**
     * Setter.
     * @param startingMVAIndexRate
     */
    public void setStartingMVAIndexRate(EDITBigDecimal startingMVAIndexRate)
    {
        this.startingMVAIndexRate = startingMVAIndexRate;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getStartingMVAIndexRate()
    {
        return startingMVAIndexRate;
    }

    /**
     * Setter.
     * @param priorAnnivIndexValue
     */
    public void setPriorAnnivIndexValue(EDITBigDecimal priorAnnivIndexValue)
    {
        this.priorAnnivIndexValue = priorAnnivIndexValue;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getPriorAnnivIndexValue()
    {
        return priorAnnivIndexValue;
    }

    /**
     * Setter.
     * @param priorAnnivAccountValue
     */
    public void setPriorAnnivAccountValue(EDITBigDecimal priorAnnivAccountValue)
    {
        this.priorAnnivAccountValue = priorAnnivAccountValue;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getPriorAnnivAccountValue()
    {
        return priorAnnivAccountValue;
    }

    /**
     * Setter.
     * @param withdrawalsSincePriorAnniv
     */
    public void setWithdrawalsSincePriorAnniv(EDITBigDecimal withdrawalsSincePriorAnniv)
    {
        this.withdrawalsSincePriorAnniv = withdrawalsSincePriorAnniv;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getWithdrawalsSincePriorAnniv()
    {
        return withdrawalsSincePriorAnniv;
    }

    /**
     * Setter.
     * @param currentAccountValue
     */
    public void setCurrentAccountValue(EDITBigDecimal currentAccountValue)
    {
        this.currentAccountValue = currentAccountValue;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getCurrentAccountValue()
    {
        return currentAccountValue;
    }

    /**
     * Setter.
     * @param currentMinCashSurrenderValue
     */
    public void setCurrentMinCashSurrenderValue(EDITBigDecimal currentMinCashSurrenderValue)
    {
        this.currentMinCashSurrenderValue = currentMinCashSurrenderValue;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getCurrentMinCashSurrenderValue()
    {
        return currentMinCashSurrenderValue;
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
     * @param indexCapRateGuarTerm
     */
    public void setIndexCapRateGuarTerm(int indexCapRateGuarTerm)
    {
        this.indexCapRateGuarTerm = indexCapRateGuarTerm;
    }

    /**
     * Getter.
     * @return
     */
    public int getIndexCapRateGuarTerm()
    {
        return indexCapRateGuarTerm;
    }

    /**
     * Setter.
     * @param indexCapMinimum
     */
    public void setIndexCapMinimum(EDITBigDecimal indexCapMinimum)
    {
        this.indexCapMinimum = indexCapMinimum;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getIndexCapMinimum()
    {
        return indexCapMinimum;
    }

    /**
     * Setter.
     * @param participationRate
     */
    public void setParticipationRate(EDITBigDecimal participationRate)
    {
        this.participationRate = participationRate;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getParticipationRate()
    {
        return participationRate;
    }

    /**
     * Setter.
     * @param indexMargin
     */
    public void setIndexMargin(EDITBigDecimal indexMargin)
    {
        this.indexMargin = indexMargin;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getIndexMargin()
    {
        return indexMargin;
    }

    /**
     * Setter.
     * @param interestRateAsOf1231
     */
    public void setInterestRateAsOf1231(EDITBigDecimal interestRateAsOf1231)
    {
        this.interestRateAsOf1231 = interestRateAsOf1231;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getInterestRateAsOf1231()
    {
        return interestRateAsOf1231;
    }

    /**
     * Setter.
     * @param currentSurrenderCharge
     */
    public void setCurrentSurrenderCharge(EDITBigDecimal currentSurrenderCharge)
    {
        this.currentSurrenderCharge = currentSurrenderCharge;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getCurrentSurrenderCharge()
    {
        return currentSurrenderCharge;
    }

    /**
     * Setter.
     * @param premiumBonusAmount
     */
    public void setPremiumBonusAmount(EDITBigDecimal premiumBonusAmount)
    {
        this.premiumBonusAmount = premiumBonusAmount;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getPremiumBonusAmount()
    {
        return premiumBonusAmount;
    }

    /**
     * Setter.
     * @param firstYearBonus
     */
    public void setFirstYearBonus(EDITBigDecimal firstYearBonus)
    {
        this.firstYearBonus = firstYearBonus;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getFirstYearBonus()
    {
        return firstYearBonus;
    }

    /**
     * Setter.
     * @param grossPremiumCollected
     */
    public void setGrossPremiumCollected(EDITBigDecimal grossPremiumCollected)
    {
        this.grossPremiumCollected = grossPremiumCollected;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getGrossPremiumCollected()
    {
        return grossPremiumCollected;
    }

    /**
     * Setter.
     * @param bonusInterestRate
     */
    public void setBonusInterestRate(EDITBigDecimal bonusInterestRate)
    {
        this.bonusInterestRate = bonusInterestRate;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getBonusInterestRate()
    {
        return bonusInterestRate;
    }

    /**
     * Setter.
     * @param bonusRateDuration
     */
    public void setBonusRateDuration(int bonusRateDuration)
    {
        this.bonusRateDuration = bonusRateDuration;
    }

    /**
     * Getter.
     * @return
     */
    public int getBonusRateDuration()
    {
        return bonusRateDuration;
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
     * @param minGuarRate
     */
    public void setMinGuarRate(EDITBigDecimal minGuarRate)
    {
        this.minGuarRate = minGuarRate;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getMinGuarRate()
    {
        return minGuarRate;
    }

    /**
     * Setter.
     * @param freeAmountRemaining
     */
    public void setFreeAmountRemaining(EDITBigDecimal freeAmountRemaining)
    {
        this.freeAmountRemaining = freeAmountRemaining;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getFreeAmountRemaining()
    {
        return freeAmountRemaining;
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
     * Setter.
     * @param segmentBaseFK
     */
    public void setSegmentBaseFK(Long segmentBaseFK)
    {
        this.SegmentBaseFK = segmentBaseFK;
    }

    /**
     * Getter.
     * @return
     */
    public Long getSegmentBaseFK()
    {
        return SegmentBaseFK;
    }
    
    /**
     * Setter.
     * @param investmentBuckets
     */
    public void setInvestmentBuckets(Set<InvestmentBucket> investmentBuckets)
    {
        this.investmentBuckets = investmentBuckets;
    }

    /**
     * Getter.
     * @return
     */
    public Set<InvestmentBucket> getInvestmentBuckets()
    {
        return investmentBuckets;
    }

    /**
     * Add another investmentBucket record to the current mapped investmentBuckets.
     * @param investmentBucket
     */
    public void addInvestmentBucket(InvestmentBucket investmentBucket)
    {
        this.investmentBuckets.add(investmentBucket);


    }

    /**
     * Setter.
     * @param indexingMethod
     */
    public void setIndexingMethod(String indexingMethod)
    {
        this.indexingMethod = indexingMethod;
    }

    /**
     * Getter.
     * @return
     */
    public String getIndexingMethod()
    {
        return indexingMethod;
    }

    /**
     * Setter.
     * @param fundGuaranteedDuration
     */
    public void setFundGuaranteedDuration(int fundGuaranteedDuration)
    {
        this.fundGuaranteedDuration = fundGuaranteedDuration;
    }

    /**
     * Getter.
     * @return
     */
    public int getFundGuaranteedDuration()
    {
        return fundGuaranteedDuration;
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
