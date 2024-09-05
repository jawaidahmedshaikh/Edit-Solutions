/*
 * User: dlataill
 * Date: Feb 28, 2007
 * Time: 7:29:18 AM
 *
 * (c) 2000-2008 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package staging;

import edit.services.db.hibernate.HibernateEntity;
import edit.services.db.hibernate.SessionHelper;
import event.BucketHistory;
import event.FinancialHistory;
import edit.common.EDITDate;
import edit.common.EDITMap;

import java.util.List;
import java.util.Map;

import org.hibernate.Session;

import edit.common.EDITBigDecimal;


public class Bucket extends HibernateEntity
{
    private Long bucketPK;
    private Long financialActivityFK;
    private EDITDate depositDate;
    private EDITBigDecimal depositAmount;
    private EDITBigDecimal bucketInterestRate;
    private EDITBigDecimal unearnedInterest;
    private EDITBigDecimal loanPrincipalRemaining;
    private String bucketSource;
    private EDITBigDecimal loanInterestRate;
    private EDITBigDecimal accruedLoanInterest;
    private EDITBigDecimal loanPrincipalDollars;
    private EDITBigDecimal loanInterestDollars;
    private EDITBigDecimal unearnedLoanInterest;
    private EDITDate interestPaidThrougDate;
    private EDITBigDecimal billedLoanInterest;

    private FinancialActivity financialActivity;

    /**
     * Target database to be used for lookups, etc.
     */
    public static String DATABASE = SessionHelper.STAGING;


    public Bucket()
    {
    }

    /**
     * Getter.
     * @return
     */
    public Long getBucketPK()
    {
        return bucketPK;
    }

    /**
     * Setter.
     * @param bucketPK
     */
    public void setBucketPK(Long bucketPK)
    {
        this.bucketPK = bucketPK;
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
    public EDITDate getDepositDate()
    {
        return depositDate;
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
    public EDITBigDecimal getDepositAmount()
    {
        return depositAmount;
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
    public EDITBigDecimal getBucketInterestRate()
    {
        return bucketInterestRate;
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
    public EDITBigDecimal getUnearnedInterest()
    {
        return unearnedInterest;
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
    public EDITBigDecimal getLoanPrincipalRemaining()
    {
        return loanPrincipalRemaining;
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
    public String getBucketSource()
    {
        return bucketSource;
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
    public EDITBigDecimal getLoanInterestRate()
    {
        return loanInterestRate;
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
    public EDITBigDecimal getAccruedLoanInterest()
    {
        return accruedLoanInterest;
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
    public EDITBigDecimal getLoanPrincipalDollars()
    {
        return loanPrincipalDollars;
    }

    /**
     * Setter.
     * @param loanPrincipalDollars
     */
    public void setLoanPrincipalDollars(EDITBigDecimal loanPrincipalDollars)
    {
        this.loanPrincipalDollars = loanPrincipalDollars;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getLoanInterestDollars()
    {
        return loanInterestDollars;
    }

    /**
     * Setter.
     * @param loanInterestDollars
     */
    public void setLoanInterestDollars(EDITBigDecimal loanInterestDollars)
    {
        this.loanInterestDollars = loanInterestDollars;
    }

    public EDITBigDecimal getUnearnedLoanInterest()
    {
        return unearnedLoanInterest;
    }

    public void setUnearnedLoanInterest(EDITBigDecimal unearnedLoanInterest)
    {
        this.unearnedLoanInterest = unearnedLoanInterest;
    }

    public EDITDate getInterestPaidThroughDate()
    {
        return interestPaidThrougDate;
    }

    public void setInterestPaidThroughDate(EDITDate interestPaidThroughDate)
    {
        this.interestPaidThrougDate = interestPaidThroughDate;
    }

    public EDITBigDecimal getBilledLoanInterest()
    {
        return billedLoanInterest;
    }

    public void setBilledLoanInterest(EDITBigDecimal billedLoanInterest)
    {
        this.billedLoanInterest = billedLoanInterest;
    }

    public String getDatabase()
    {
        return Bucket.DATABASE;
    }    
}
