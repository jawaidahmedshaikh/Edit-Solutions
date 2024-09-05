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

public class SubBucket extends HibernateEntity
{
    public static final String DATABASE = SessionHelper.STAGING;

    private Long subBucketPK;
    private Long investmentBucketFK;
    private EDITDate effectiveDate;     
    private EDITBigDecimal currentRate; 
    private EDITDate currentEndDate;
    private EDITDate baseRateBaseEndDate;
    private EDITBigDecimal guarMinRate1;
    private EDITDate guarMinEndDate1;
    private EDITBigDecimal guarMinRate2;
    private EDITDate guarMinEndDate2;
    private int numberBuckets;   
    private EDITBigDecimal changeInFundValue;
    private EDITBigDecimal baseRate;
    private EDITBigDecimal fundValue;

    private InvestmentBucket investmentBucket;

    /**
     * Setter.
     * @param subBucketPK
     */
    public void setSubBucketPK(Long subBucketPK)
    {
        this.subBucketPK = subBucketPK;
    }

    /**
     * Getter.
     * @return
     */
    public Long getSubBucketPK()
    {
        return subBucketPK;
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
    public EDITDate getEffectiveDate()
    {
        return effectiveDate;
    }

    /**
     * Setter.
     * @param currentRate
     */
    public void setCurrentRate(EDITBigDecimal currentRate)
    {
        this.currentRate = currentRate;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getCurrentRate()
    {
        return currentRate;
    }

    /**
     * Setter.
     * @param currentEndDate
     */
    public void setCurrentEndDate(EDITDate currentEndDate)
    {
        this.currentEndDate = currentEndDate;
    }

    /**
     * Getter.
     * @return
     */
    public EDITDate getCurrentEndDate()
    {
        return currentEndDate;
    }

    /**
     * Setter.
     * @param baseRateBaseEndDate
     */
    public void setBaseRateBaseEndDate(EDITDate baseRateBaseEndDate)
    {
        this.baseRateBaseEndDate = baseRateBaseEndDate;
    }

    /**
     * Getter.
     * @return
     */
    public EDITDate getBaseRateBaseEndDate()
    {
        return baseRateBaseEndDate;
    }

    /**
     * Setter.
     * @param guarMinRate1
     */
    public void setGuarMinRate1(EDITBigDecimal guarMinRate1)
    {
        this.guarMinRate1 = guarMinRate1;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getGuarMinRate1()
    {
        return guarMinRate1;
    }

    /**
     * Setter.
     * @param guarMinEndDate1
     */
    public void setGuarMinEndDate1(EDITDate guarMinEndDate1)
    {
        this.guarMinEndDate1 = guarMinEndDate1;
    }

    /**
     * Getter.
     * @return
     */
    public EDITDate getGuarMinEndDate1()
    {
        return guarMinEndDate1;
    }

    /**
     * Setter.
     * @param guarMinRate2
     */
    public void setGuarMinRate2(EDITBigDecimal guarMinRate2)
    {
        this.guarMinRate2 = guarMinRate2;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getGuarMinRate2()
    {
        return guarMinRate2;
    }

    /**
     * Setter.
     * @param guarMinEndDate2
     */
    public void setGuarMinEndDate2(EDITDate guarMinEndDate2)
    {
        this.guarMinEndDate2 = guarMinEndDate2;
    }

    /**
     * Getter.
     * @return
     */
    public EDITDate getGuarMinEndDate2()
    {
        return guarMinEndDate2;
    }

    /**
     * Setter.
     * @param numberBuckets
     */
    public void setNumberBuckets(int numberBuckets)
    {
        this.numberBuckets = numberBuckets;
    }

    /**
     * Getter.
     * @return
     */
    public int getNumberBuckets()
    {
        return numberBuckets;
    }

    /**
     * Setter.
     * @param changeInFundValue
     */
    public void setChangeInFundValue(EDITBigDecimal changeInFundValue)
    {
        this.changeInFundValue = changeInFundValue;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getChangeInFundValue()
    {
        return changeInFundValue;
    }

    /**
     * Setter.
     * @param baseRate
     */
    public void setBaseRate(EDITBigDecimal baseRate)
    {
        this.baseRate = baseRate;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getBaseRate()
    {
        return baseRate;
    }

    /**
     * Setter.
     * @param fundValue
     */
    public void setFundValue(EDITBigDecimal fundValue)
    {
        this.fundValue = fundValue;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getFundValue()
    {
        return fundValue;
    }
    
    /**
     * Setter.
     * @param investmentBucket
     */
    public void setInvestmentBucket(InvestmentBucket investmentBucket)
    {
        this.investmentBucket = investmentBucket;
    }

    /**
     * Getter.
     * @return
     */
    public InvestmentBucket getInvestmentBucket()
    {
        return investmentBucket;
    }

    /**
     * Setter.
     * @param investmentBucketFK
     */
    public void setInvestmentBucketFK(Long investmentBucketFK)
    {
        this.investmentBucketFK = investmentBucketFK;
    }

    /**
     * Getter.
     * @return
     */
    public Long getInvestmentBucketFK()
    {
        return investmentBucketFK;
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
