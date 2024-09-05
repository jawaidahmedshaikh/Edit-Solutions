package staging;

import edit.common.EDITBigDecimal;
import edit.common.EDITDate;
import edit.services.db.hibernate.HibernateEntity;
import edit.services.db.hibernate.SessionHelper;
import java.util.Set;
import java.util.HashSet;

public class AnnualStatement extends HibernateEntity {

	private static final long serialVersionUID = 1L;
	
	private Long annualStatementPK;
    private EDITDate startDate;
    private EDITDate endDate;
	private EDITBigDecimal accumulationValueAtStartDate;
	private EDITBigDecimal surrenderValueAtStartDate;
	private EDITBigDecimal surrenderValueAtEndDate;
	private EDITBigDecimal loanedValueAtStartDate;
	private EDITBigDecimal faceAmountAtStartDate;
	private EDITBigDecimal accumulationValueAtEndDate;
	private EDITBigDecimal loanInterestAccruedAtEndDate;
	private EDITBigDecimal surrenderChargeAtEndDate;
	private EDITBigDecimal deathBenefitAtEndDate;
	private EDITBigDecimal faceAmountAtEndDate;
	private EDITBigDecimal sumOfPremiumsPaid;
	private EDITBigDecimal sumOfLoans;
	private EDITBigDecimal sumOfLoanRepayments;
	private EDITBigDecimal sumOfWithdrawals;
	private EDITBigDecimal sumOfRiderCOI;
	private EDITBigDecimal sumOfCOI;
	
    private SegmentBase segmentBase;
    private Set<AnnualStatementMonthlySummary> annualStatementMonthlySummaries;
    
    /**
     * Target database to be used for lookups, etc.
     */
    public static String DATABASE = SessionHelper.STAGING;

    public AnnualStatement() {
        this.annualStatementMonthlySummaries = new HashSet<AnnualStatementMonthlySummary>();
    }
    
    public Long getAnnualStatementPK() {
        return annualStatementPK;
    }
    
    public void setAnnualStatementPK(Long annualStatementPK) {
        this.annualStatementPK = annualStatementPK;
    }
    
    public EDITDate getStartDate() {
        return startDate;
    }
    
    public void setStartDate(EDITDate startDate) {
        this.startDate = startDate;
    }
    
    public EDITDate getEndDate() {
        return endDate;
    }
    
    public void setEndDate(EDITDate endDate) {
        this.endDate = endDate;
    }
    
    public EDITBigDecimal getAccumulationValueAtStartDate() {
        return accumulationValueAtStartDate;
    }
    
    public void setAccumulationValueAtStartDate(EDITBigDecimal accumulationValueAtStartDate) {
        this.accumulationValueAtStartDate = accumulationValueAtStartDate;
    }
    
    public EDITBigDecimal getSurrenderValueAtStartDate() {
        return surrenderValueAtStartDate;
    }
    
    public void setSurrenderValueAtStartDate(EDITBigDecimal surrenderValueAtStartDate) {
        this.surrenderValueAtStartDate = surrenderValueAtStartDate;
    }
    
    public EDITBigDecimal getSurrenderValueAtEndDate() {
        return surrenderValueAtEndDate;
    }
    
    public void setSurrenderValueAtEndDate(EDITBigDecimal surrenderValueAtEndDate) {
        this.surrenderValueAtEndDate = surrenderValueAtEndDate;
    }
    
    public EDITBigDecimal getLoanedValueAtStartDate() {
        return loanedValueAtStartDate;
    }
    
    public void setLoanedValueAtStartDate(EDITBigDecimal loanedValueAtStartDate) {
        this.loanedValueAtStartDate = loanedValueAtStartDate;
    }
    
    public EDITBigDecimal getFaceAmountAtStartDate() {
        return faceAmountAtStartDate;
    }
    
    public void setFaceAmountAtStartDate(EDITBigDecimal faceAmountAtStartDate) {
        this.faceAmountAtStartDate = faceAmountAtStartDate;
    }
    
    public EDITBigDecimal getAccumulationValueAtEndDate() {
        return accumulationValueAtEndDate;
    }
    
    public void setAccumulationValueAtEndDate(EDITBigDecimal accumulationValueAtEndDate) {
        this.accumulationValueAtEndDate = accumulationValueAtEndDate;
    }
    
    public EDITBigDecimal getLoanInterestAccruedAtEndDate() {
        return loanInterestAccruedAtEndDate;
    }
    
    public void setLoanInterestAccruedAtEndDate(EDITBigDecimal loanInterestAccruedAtEndDate) {
        this.loanInterestAccruedAtEndDate = loanInterestAccruedAtEndDate;
    }
    
    public EDITBigDecimal getSurrenderChargeAtEndDate() {
        return surrenderChargeAtEndDate;
    }
    
    public void setSurrenderChargeAtEndDate(EDITBigDecimal surrenderChargeAtEndDate) {
        this.surrenderChargeAtEndDate = surrenderChargeAtEndDate;
    }
    
    public EDITBigDecimal getDeathBenefitAtEndDate() {
        return deathBenefitAtEndDate;
    }
    
    public void setDeathBenefitAtEndDate(EDITBigDecimal deathBenefitAtEndDate) {
        this.deathBenefitAtEndDate = deathBenefitAtEndDate;
    }
    
    public EDITBigDecimal getFaceAmountAtEndDate() {
        return faceAmountAtEndDate;
    }
    
    public void setFaceAmountAtEndDate(EDITBigDecimal faceAmountAtEndDate) {
        this.faceAmountAtEndDate = faceAmountAtEndDate;
    }
    
    public EDITBigDecimal getSumOfPremiumsPaid() {
        return sumOfPremiumsPaid;
    }
    
    public void setSumOfPremiumsPaid(EDITBigDecimal sumOfPremiumsPaid) {
        this.sumOfPremiumsPaid = sumOfPremiumsPaid;
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
    
    public EDITBigDecimal getSumOfWithdrawals() {
        return sumOfWithdrawals;
    }
    
    public void setSumOfWithdrawals(EDITBigDecimal sumOfWithdrawals) {
        this.sumOfWithdrawals = sumOfWithdrawals;
    }
    
    public EDITBigDecimal getSumOfRiderCOI() {
        return sumOfRiderCOI;
    }
    
    public void setSumOfRiderCOI(EDITBigDecimal sumOfRiderCOI) {
        this.sumOfRiderCOI = sumOfRiderCOI;
    }
    
    public EDITBigDecimal getSumOfCOI() {
        return sumOfCOI;
    }
    
    public void setSumOfCOI(EDITBigDecimal sumOfCOI) {
        this.sumOfCOI = sumOfCOI;
    }
    
    public SegmentBase getSegmentBase() {
        return segmentBase;
    }
    
    public void setSegmentBase(SegmentBase segmentBase) {
        this.segmentBase = segmentBase;
    }
    
    public Set<AnnualStatementMonthlySummary> getAnnualStatementMonthlySummaries() {
        return annualStatementMonthlySummaries;
    }
    
    public void setAnnualStatementMonthlySummaries(Set<AnnualStatementMonthlySummary> annualStatementMonthlySummaries) {
        this.annualStatementMonthlySummaries = annualStatementMonthlySummaries;
    }
    
    public void addAnnualStatementMonthlySummary(AnnualStatementMonthlySummary annualStatementMonthlySummary)
    {
        this.annualStatementMonthlySummaries.add(annualStatementMonthlySummary);
    }
}
