/*
 * User: dlataill
 * Date: Oct 9, 2007
 * Time: 12:40:18 PM
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package staging;

import edit.services.db.hibernate.HibernateEntity;
import edit.services.db.hibernate.SessionHelper;
import edit.common.EDITDate;
import edit.common.EDITBigDecimal;
import edit.common.EDITDateTime;

import java.util.Set;
import java.util.HashSet;


public class FinancialActivity extends HibernateEntity
{
    private Long financialActivityPK;
    private Long segmentBaseFK;
    private EDITDate effectiveDate;
    private EDITDateTime processDate;
    private int taxYear;
    private EDITBigDecimal trxAmount;
    private EDITDate dueDate;
    private String transactionType;
    private String noCorrespondenceInd;
    private String noAccountingInd;
    private String noCommissionInd;
    private EDITDateTime maintDateTime;
    private String operator;
    private EDITBigDecimal grossAmount;
    private EDITBigDecimal netAmount;
    private EDITBigDecimal checkAmount;
    private EDITBigDecimal freeAmount;
    private EDITBigDecimal taxableBenefit;
    private String disbursementSource;
    private EDITBigDecimal commissionableAmount;
    private EDITBigDecimal maxCommissionAmount;
    private EDITBigDecimal costBasis;
    private EDITBigDecimal accumulatedValue;
    private EDITBigDecimal surrenderValue;
    private String complexChangeType;

    private SegmentBase segmentBase;

    private Set<CommissionActivity> commissionActivities;
    private Set<AdjustmentHistory> adjustmentHistories;
    private Set<Bucket> buckets;

    /**
     * Target database to be used for lookups, etc.
     */
    public static String DATABASE = SessionHelper.STAGING;


    public FinancialActivity()
    {
        this.commissionActivities = new HashSet<CommissionActivity>();
        this.adjustmentHistories = new HashSet<AdjustmentHistory>();
        this.buckets = new HashSet<Bucket>();
    }

    /**
     * Getter.
     * @return
     */
    public Long getFinancialActivityPK()
    {
        return financialActivityPK;
    }

    /**
     * Setter.
     * @param financialActivityPK
     */
    public void setFinancialActivityPK(Long financialActivityPK)
    {
        this.financialActivityPK = financialActivityPK;
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

    /**
     * Getter.
     * @return
     */
    public SegmentBase getSegmentBase()
    {
        return segmentBase;
    }

    public void setSegmentBase(SegmentBase segmentBase)
    {
        this.segmentBase = segmentBase;
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
    public EDITDateTime getProcessDate()
    {
        return processDate;
    }

    /**
     * Setter.
     * @param processDate
     */
    public void setProcessDate(EDITDateTime processDate)
    {
        this.processDate = processDate;
    }

    /**
    * Getter.
    * @return
    */
    public int getTaxYear()
    {
        return taxYear;
    }

    public void setTaxYear(int taxYear)
    {
        this.taxYear = taxYear;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getTrxAmount()
    {
        return trxAmount;
    }

    /**
     * Setter.
     * @param trxAmount
     */
    public void setTrxAmount(EDITBigDecimal trxAmount)
    {
        this.trxAmount = trxAmount;
    }

    /**
    * Getter.
    * @return
    */
    public EDITDate getDueDate()
    {
        return dueDate;
    }

     public void setDueDate(EDITDate dueDate)
     {
         this.dueDate = dueDate;
     }

    /**
    * Getter.
    * @return
    */
    public String getTransactionType()
    {
        return transactionType;
    }

    /**
     * Setter.
     * @param transactionType
     */
    public void setTransactionType(String transactionType)
    {
        this.transactionType = transactionType;
    }

    /**
    * Getter.
    * @return
    */
    public String getNoCorrespondenceInd()
    {
        return noCorrespondenceInd;
    }

    /**
    * Setter.
    * @param noCorrespondenceInd
    */
    public void setNoCorrespondenceInd(String noCorrespondenceInd)
    {
        this.noCorrespondenceInd = noCorrespondenceInd;
    }

    /**
    * Getter.
    * @return
    */
    public String getNoAccountingInd()
    {
        return noAccountingInd;
    }

    /**
    * Setter.
    * @param noAccountingInd
    */
    public void setNoAccountingInd(String noAccountingInd)
    {
        this.noAccountingInd = noAccountingInd;
    }

    /**
    * Getter.
    * @return
    */
    public String getNoCommissionInd()
    {
        return noCommissionInd;
    }

    /**
    * Setter.
    * @param noCommissionInd
    */
    public void setNoCommissionInd(String noCommissionInd)
    {
        this.noCommissionInd = noCommissionInd;
    }

    /**
    * Getter.
    * @return
    */
    public EDITDateTime getMaintDateTime()
    {
        return maintDateTime;
    }

    /**
    * Setter.
    * @param maintDateTime
    */
    public void setMaintDateTime(EDITDateTime maintDateTime)
    {
        this.maintDateTime = maintDateTime;
    }

    /**
    * Getter.
    * @return
    */
    public String getOperator()
    {
        return operator;
    }

    /**
    * Setter.
    * @param operator
    */
    public void setOperator(String operator)
    {
        this.operator = operator;
    }

    /**
    * Getter.
    * @return
    */
    public EDITBigDecimal getGrossAmount()
    {
        return grossAmount;
    }

    /**
     * Setter.
     * @param grossAmount
     */
    public void setGrossAmount(EDITBigDecimal grossAmount)
    {
        this.grossAmount = grossAmount;
    }

    /**
    * Getter.
    * @return
    */
    public EDITBigDecimal getNetAmount()
    {
        return netAmount;
    }

    /**
     * Setter.
     * @param netAmount
     */
    public void setNetAmount(EDITBigDecimal netAmount)
    {
        this.netAmount = netAmount;
    }

    /**
    * Getter.
    * @return
    */
    public EDITBigDecimal getCheckAmount()
    {
        return checkAmount;
    }

    /**
     * Setter.
     * @param checkAmount
     */
    public void setCheckAmount(EDITBigDecimal checkAmount)
    {
        this.checkAmount = checkAmount;
    }

    /**
    * Getter.
    * @return
    */
    public EDITBigDecimal getFreeAmount()
    {
        return freeAmount;
    }

    /**
     * Setter.
     * @param freeAmount
     */
    public void setFreeAmount(EDITBigDecimal freeAmount)
    {
        this.freeAmount = freeAmount;
    }

    /**
    * Getter.
    * @return
    */
    public EDITBigDecimal getTaxableBenefit()
    {
        return taxableBenefit;
    }

    /**
     * Setter.
     * @param taxableBenefit
     */
    public void setTaxableBenefit(EDITBigDecimal taxableBenefit)
    {
        this.taxableBenefit = taxableBenefit;
    }

    /**
    * Getter.
    * @return
    */
    public String getDisbursementSource()
    {
        return disbursementSource;
    }

    /**
     * Setter.
     * @param disbursementSource
     */
    public void setDisbursementSource(String disbursementSource)
    {
        this.disbursementSource = disbursementSource;
    }

    /**
    * Getter.
    * @return
    */
    public EDITBigDecimal getCommissionableAmount()
    {
        return commissionableAmount;
    }

    /**
     * Setter.
     * @param commissionableAmount
     */
    public void setCommissionableAmount(EDITBigDecimal commissionableAmount)
    {
        this.commissionableAmount = commissionableAmount;
    }

    /**
    * Getter.
    * @return
    */
    public EDITBigDecimal getMaxCommissionAmount()
    {
        return maxCommissionAmount;
    }

    /**
     * Setter.
     * @param maxCommissionAmount
     */
    public void setMaxCommissionAmount(EDITBigDecimal maxCommissionAmount)
    {
        this.maxCommissionAmount = maxCommissionAmount;
    }

	/**
     * Getter
     * @return
     */
    public EDITBigDecimal getCostBasis()
    {
        return costBasis;
    }

    /**
     * Setter.
     * @param costBasis
     */
    public void setCostBasis(EDITBigDecimal costBasis)
    {
        this.costBasis = costBasis;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getAccumulatedValue()
    {
        return accumulatedValue;
    }

    /**
    * Setter.
    * @param accumulatedValue
    */
    public void setAccumulatedValue(EDITBigDecimal accumulatedValue)
    {
        this.accumulatedValue = accumulatedValue;
    }

    /**
    * Getter.
    * @return
    */
    public EDITBigDecimal getSurrenderValue()
    {
        return surrenderValue;
    }

    /**
    * Setter.
    * @param surrenderValue
    */
    public void setSurrenderValue(EDITBigDecimal surrenderValue)
    {
        this.surrenderValue = surrenderValue;
    }

    /**
     * Getter.
     * @return
     */
    public String getComplexChangeType()
    {
        return complexChangeType;
    }

    /**
     * Setter.
     * @param complexChangeType
     */
    public void setComplexChangeType(String complexChangeType)
    {
        this.complexChangeType = complexChangeType;
    }

    /**
     * Getter.
     * @return
     */
    public Set<CommissionActivity> getCommissionActivities()
    {
        return commissionActivities;
    }

    /**
     * Setter.
     * @param commissionActivities
     */
    public void setCommissionActivities(Set<CommissionActivity> commissionActivities)
    {
        this.commissionActivities = commissionActivities;
    }

    /**
     * Add another commissionActivity to the current mapped commissionActivities.
     * @param commissionActivity
     */
    public void addCommissionActivity(CommissionActivity commissionActivity)
    {
        this.commissionActivities.add(commissionActivity);
    }

    /**
     * Getter.
     * @return
     */
    public Set<AdjustmentHistory> getAdjustmentHistories()
    {
        return adjustmentHistories;
    }

    /**
     * Setter.
     * @param adjustmentHistories
     */
    public void setAdjustmentHistories(Set<AdjustmentHistory> adjustmentHistories)
    {
        this.adjustmentHistories = adjustmentHistories;
    }

    /**
     * Add another adjustmentHistory to the current mapped adjustmentHistories.
     * @param adjustmentHistory
     */
    public void addAdjustmentHistory(AdjustmentHistory adjustmentHistory)
    {
        this.adjustmentHistories.add(adjustmentHistory);
    }

    /**
     * Getter.
     * @return
     */
    public Set<Bucket> getBuckets()
    {
        return buckets;
    }

    /**
     * Setter.
     * @param buckets
     */
    public void setBuckets(Set<Bucket> buckets)
    {
        this.buckets = buckets;
    }

    /**
     * Add another bucket to the current mapped buckets.
     * @param bucket
     */
    public void addBucket(Bucket bucket)
    {
        this.buckets.add(bucket);
    }

    public String getDatabase()
    {
        return FinancialActivity.DATABASE;
    }
}
