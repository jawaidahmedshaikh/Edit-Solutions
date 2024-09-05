package edit.common.vo.user;

import edit.services.db.*;

/**
 * Created by IntelliJ IDEA.
 * User: gfrosti
 * Date: Apr 04, 2003
 * Time: 1:28:14 PM
 * To change this template use Options | File Templates.
 */
public class ValuationVO {

    private String effectiveYear;
    private String planId;
    private String contractNumber;
    private String numberOfContracts;
    private String companyName;
    private String issueState;
    private String qualifiedIndicator;
    private String status;
    private String kindOfAnnuity;
    private String issueDate;
    private String totalGrossConsideration;
    private String considerationPayMode;
    private String fundsHeldOnDeposit;
    private String numberOfLives;
    private String deathStatus;
    private String firstPayIssueAge;
    private String firstPaySex;
    private String firstPaySubStd;
    private String firstPayDateOfDeath;
    private String secondPayIssueAge;
    private String secondPaySex;
    private String secondPaySubStd;
    private String secondPayDateOfDeath;
    private String paymentAmount;
    private String paymentStartDate;
    private String terminationDate;
    private String paymentMode;
    private String certainPeriodEndDate;
    private String reducePercent1;
    private String reducePercent2;
    private String lastProcessingDate;

    public static final int DELETED = 0;
    public static final int ADDED   = 1;
    public static final int CHANGED = 2;

    public void setEffectiveYear(String effectiveYear) {

        this.effectiveYear = effectiveYear;
    }

    public String getEffectiveYear() {

        return this.effectiveYear;
    }

    public void setPlanId(String planId)
    {
        this.planId = planId;
    }

    public String getPlanId() {

        return this.planId;
    }

    public void setContractNumber(String contractNumber) {

        this.contractNumber = contractNumber;
    }

    public String getContractNumber() {

        return this.contractNumber;
    }

    public void setNumberOfContracts(String numberOfContracts) {

        this.numberOfContracts = numberOfContracts;
    }

    public String getNumberOfContracts() {

        return this.numberOfContracts;
    }

    public void setCompanyName(String companyName) {

        this.companyName = companyName;
    }

    public String getCompanyName() {

        return this.companyName;
    }

    public void setIssueState(String issueState) {

        this.issueState = issueState;
    }

    public String getIssueState() {

        return this.issueState;
    }

    public void setQualifiedIndicator(String qualifiedIndicator) {

        this.qualifiedIndicator = qualifiedIndicator;
    }

    public String getQualifiedIndicator() {

        return this.qualifiedIndicator;
    }

    public void setStatus(String status) {

        this.status = status;
    }

    public String getStatus() {

        return this.status;
    }

    public void setKindOfAnnuity(String kindOfAnnuity) {

        this.kindOfAnnuity = kindOfAnnuity;
    }

    public String getKindOfAnnuity() {

        return this.kindOfAnnuity;
    }

    public void setIssueDate(String issueDate) {

        this.issueDate = issueDate;
    }

    public String getIssueDate() {

        return this.issueDate;
    }

    public void setTotalGrossConsideration(String totalGrossConsideration) {

        this.totalGrossConsideration = totalGrossConsideration;
    }

    public String getTotalGrossConsideration() {

        return this.totalGrossConsideration;
    }

    public void setConsiderationPayMode(String considerationPayMode) {

        this.considerationPayMode = considerationPayMode;
    }

    public String getConsiderationPayMode() {

        return this.considerationPayMode;
    }

    public void setFundsHeldOnDeposit(String fundsHeldOnDeposit) {

        this.fundsHeldOnDeposit = fundsHeldOnDeposit;
    }

    public String getFundsHeldOnDeposit() {

        return this.fundsHeldOnDeposit;
    }

    public void setNumberOfLives(String numberOfLives) {

        this.numberOfLives = numberOfLives;
    }

    public String getNumberOfLives() {

        return this.numberOfLives;
    }

    public void setDeathStatus(String deathStatus) {

        this.deathStatus = deathStatus;
    }

    public String getDeathStatus() {

        return this.deathStatus;
    }

    public void setFirstPayIssueAge(String firstPayIssueAge) {

        this.firstPayIssueAge = firstPayIssueAge;
    }

    public String getFirstPayIssueAge() {

        return this.firstPayIssueAge;
    }

    public void setFirstPaySex(String firstPaySex) {

        this.firstPaySex = firstPaySex;
    }

    public String getFirstPaySex() {

        return this.firstPaySex;
    }

    public void setFirstPaySubStd(String firstPaySubStd) {

        this.firstPaySubStd = firstPaySubStd;
    }

    public String getFirstPaySubStd() {

        return this.firstPaySubStd;
    }

    public void setFirstPayDateOfDeath(String firstPayDateOfDeath) {

        this.firstPayDateOfDeath = firstPayDateOfDeath;
    }

    public String getFirstPayDateOfDeath() {

        return this.firstPayDateOfDeath;
    }

    public void setSecondPayIssueAge(String secondPayIssueAge) {

        this.secondPayIssueAge = secondPayIssueAge;
    }

    public String getSecondPayIssueAge() {

        return this.secondPayIssueAge;
    }

    public void setSecondPaySex(String secondPaySex) {

        this.secondPaySex = secondPaySex;
    }

    public String getSecondPaySex() {

        return this.secondPaySex;
    }

    public void setSecondPaySubStd(String secondPaySubStd) {

        this.secondPaySubStd = secondPaySubStd;
    }

    public String getSecondPaySubStd() {

        return this.secondPaySubStd;
    }

    public void setSecondPayDateOfDeath(String secondPayDateOfDeath) {

        this.secondPayDateOfDeath = secondPayDateOfDeath;
    }

    public String getSecondPayDateOfDeath() {

        return this.secondPayDateOfDeath;
    }

    public void setPaymentAmount(String paymentAmount) {

        this.paymentAmount = paymentAmount;
    }

    public String getPaymentAmount() {

        return this.paymentAmount;
    }

    public void setPaymentStartDate(String paymentStartDate) {

        this.paymentStartDate = paymentStartDate;
    }

    public String getPaymentStartDate() {

        return this.paymentStartDate;
    }

    public void setTerminationDate(String terminationDate) {

        this.terminationDate = terminationDate;
    }

    public String getTerminationDate() {

        return this.terminationDate;
    }

    public void setPaymentMode(String paymentMode) {

        this.paymentMode = paymentMode;
    }

    public String getPaymentMode() {

        return this.paymentMode;
    }

    public void setCertainPeriodEndDate(String certainPeriodEndDate) {

        this.certainPeriodEndDate = certainPeriodEndDate;
    }

    public String getCertainPeriodEndDate() {

        return this.certainPeriodEndDate;
    }

    public void setReducePercent1(String reducePercent1) {

        this.reducePercent1 = reducePercent1;
    }

    public String getReducePercent1() {

        return this.reducePercent1;
    }

    public void setReducePercent2(String reducePercent2) {

        this.reducePercent2 = reducePercent2;
    }

    public String getReducePercent2() {

        return this.reducePercent2;
    }

    public void setLastProcessingDate(String lastProcessingDate) {

        this.lastProcessingDate = lastProcessingDate;
    }

    public String getLastProcessingDate() {

        return this.lastProcessingDate;
    }
}
