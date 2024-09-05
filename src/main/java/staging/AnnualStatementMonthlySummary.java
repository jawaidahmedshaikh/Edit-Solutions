package staging;

import edit.common.EDITBigDecimal;
import edit.common.EDITDate;
import edit.services.db.hibernate.HibernateEntity;
import edit.services.db.hibernate.SessionHelper;

public class AnnualStatementMonthlySummary extends HibernateEntity {

	private static final long serialVersionUID = 1L;
	
	private Long annualStatementMonthlySummaryPK;
	private Long annualStatementFK;
	private AnnualStatement annualStatement;
    private EDITDate endDate;
	private EDITBigDecimal accumulationValueAtEndDate;
	private EDITBigDecimal sumOfPremiumsPaid;
	private EDITBigDecimal sumOfWithdrawals;
	private EDITBigDecimal interestEarned;
	private EDITBigDecimal baseCOI;
	private EDITBigDecimal riderCOI;
	private EDITBigDecimal mortalityChargeAndRiderCOI;
	private EDITBigDecimal sumOfAdminFees;
	private EDITBigDecimal interestRate;
	private EDITBigDecimal sumOfLoans;
	private EDITBigDecimal sumOfLoanRepayments;
	private EDITBigDecimal sumOfPremiumLoad;
	
	/**
     * Target database to be used for lookups, etc.
     */
    public static String DATABASE = SessionHelper.STAGING;

    public AnnualStatementMonthlySummary() {}
    
    public Long getAnnualStatementMonthlySummaryPK() {
        return annualStatementMonthlySummaryPK;
    }
    
    public void setAnnualStatementMonthlySummaryPK(Long annualStatementMonthlySummaryPK) {
        this.annualStatementMonthlySummaryPK = annualStatementMonthlySummaryPK;
    }
    
    public Long getAnnualStatementFK() {
        return annualStatementFK;
    }
    
    public void setAnnualStatementFK(Long annualStatementFK) {
        this.annualStatementFK = annualStatementFK;
    }
    
    public AnnualStatement getAnnualStatement() {
        return this.annualStatement;
    }
    
    public void setAnnualStatement(AnnualStatement annualStatement) {
        this.annualStatement = annualStatement;
    }
    
    public EDITDate getEndDate() {
        return endDate;
    }
    
    public void setEndDate(EDITDate endDate) {
        this.endDate = endDate;
    }
    
    public EDITBigDecimal getAccumulationValueAtEndDate() {
        return accumulationValueAtEndDate;
    }
    
    public void setAccumulationValueAtEndDate(EDITBigDecimal accumulationValueAtEndDate) {
        this.accumulationValueAtEndDate = accumulationValueAtEndDate;
    }
	 
    public EDITBigDecimal getSumOfPremiumsPaid() {
        return sumOfPremiumsPaid;
    }
    
    public void setSumOfPremiumsPaid(EDITBigDecimal sumOfPremiumsPaid) {
        this.sumOfPremiumsPaid = sumOfPremiumsPaid;
    }
    
    public EDITBigDecimal getSumOfWithdrawals() {
        return sumOfWithdrawals;
    }
    
    public void setSumOfWithdrawals(EDITBigDecimal sumOfWithdrawals) {
        this.sumOfWithdrawals = sumOfWithdrawals;
    }
    
    public EDITBigDecimal getInterestEarned() {
        return interestEarned;
    }
    
    public void setInterestEarned(EDITBigDecimal interestEarned) {
        this.interestEarned = interestEarned;
    }
    
    public EDITBigDecimal getBaseCOI() {
        return baseCOI;
    }
    
    public void setBaseCOI(EDITBigDecimal baseCOI) {
        this.baseCOI = baseCOI;
    }
    
    public EDITBigDecimal getRiderCOI() {
        return riderCOI;
    }
    
    public void setRiderCOI(EDITBigDecimal riderCOI) {
        this.riderCOI = riderCOI;
    }
    
    public EDITBigDecimal getMortalityChargeAndRiderCOI() {
        return mortalityChargeAndRiderCOI;
    }
    
    public void setMortalityChargeAndRiderCOI(EDITBigDecimal mortalityChargeAndRiderCOI) {
        this.mortalityChargeAndRiderCOI = mortalityChargeAndRiderCOI;
    }
    
    public EDITBigDecimal getSumOfAdminFees() {
        return sumOfAdminFees;
    }
    
    public void setSumOfAdminFees(EDITBigDecimal sumOfAdminFees) {
        this.sumOfAdminFees = sumOfAdminFees;
    }
    
    public EDITBigDecimal getInterestRate() {
        return interestRate;
    }
    
    public void setInterestRate(EDITBigDecimal interestRate) {
        this.interestRate = interestRate;
    }
    
    public EDITBigDecimal getSumOfLoans() {
        return sumOfLoans;
    }
    
    public void setSumOfLoans(EDITBigDecimal sumOfLoans) {
        this.sumOfLoans = sumOfLoans;
    }
    
    public EDITBigDecimal getSumOfLoanRepayments() {
        return sumOfLoanRepayments;
    }
    
    public void setSumOfLoanRepayments(EDITBigDecimal sumOfLoanRepayments) {
        this.sumOfLoanRepayments = sumOfLoanRepayments;
    }
    
    public EDITBigDecimal getSumOfPremiumLoad() {
        return sumOfPremiumLoad;
    }
    
    public void setSumOfPremiumLoad(EDITBigDecimal sumOfPremiumLoad) {
        this.sumOfPremiumLoad = sumOfPremiumLoad;
    }
}
