package edit.common.vo.user;

import edit.services.db.*;

/**
 * Created by IntelliJ IDEA.
 * User: gfrosti
 * Date: Apr 04, 2003
 * Time: 1:28:14 PM
 * To change this template use Options | File Templates.
 */
public class BenefitExtractVO {

    private String effectiveYear;
    private String planId;
    private String contractNumber;
    private String numberOfLives;
    private String firstPayIssueAge;
    private String firstPaySex;
    private String firstPaySubStdRatio;
    private String firstPaySubStdRatioPeriod;
    private String firstPaySubStdConstant;
    private String firstPaySubStdConstantPeriod;
    private String firstPaySubStdAge;
    private String secondPayIssueAge;
    private String secondPaySex;
    private String secondPaySubStdRatio;
    private String secondPaySubStdRatioPeriod;
    private String secondPaySubStdConstant;
    private String secondPaySubStdConstantPeriod;
    private String secondPaySubStdAge;
    private String firstPayDateOfDeath;
    private String secondPayDateOfDeath;
    private String deathStatus;
    private String benefitAmount;
    private String benefitStartDate;
    private String benefitEndDate;
    private String benefitMode;
    private String certainPeriodEndDate;

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

    public void setNumberOfLives(String numberOfLives) {

        this.numberOfLives = numberOfLives;
    }

    public String getNumberOfLives() {

        return this.numberOfLives;
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

    public void setFirstPaySubStdRatio(String firstPaySubStdRatio) {

        this.firstPaySubStdRatio = firstPaySubStdRatio;
    }

    public String getFirstPaySubStdRatio() {

        return this.firstPaySubStdRatio;
    }

    public void setFirstPaySubStdRatioPeriod(String firstPaySubStdRatioPeriod) {

        this.firstPaySubStdRatioPeriod = firstPaySubStdRatioPeriod;
    }

    public String getFirstPaySubStdRatioPeriod() {

        return this.firstPaySubStdRatioPeriod;
    }

    public void setFirstPaySubStdConstant(String firstPaySubStdConstant) {

        this.firstPaySubStdConstant = firstPaySubStdConstant;
    }

    public String getFirstPaySubStdConstant() {

        return this.firstPaySubStdConstant;
    }

    public void setFirstPaySubStdConstantPeriod(String firstPaySubStdConstantPeriod) {

        this.firstPaySubStdConstantPeriod = firstPaySubStdConstantPeriod;
    }

    public String getFirstPaySubStdConstantPeriod() {

        return this.firstPaySubStdConstantPeriod;
    }

    public void setFirstPaySubStdAge(String firstPaySubStdAge) {

        this.firstPaySubStdAge = firstPaySubStdAge;
    }

    public String getFirstPaySubStdAge() {

        return this.firstPaySubStdAge;
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

    public void setSecondPaySubStdRatio(String secondPaySubStdRatio) {

        this.secondPaySubStdRatio = secondPaySubStdRatio;
    }

    public String getSecondPaySubStdRatio() {

        return this.secondPaySubStdRatio;
    }

    public void setSecondPaySubStdRatioPeriod(String secondPaySubStdRatioPeriod) {

        this.secondPaySubStdRatioPeriod = secondPaySubStdRatioPeriod;
    }

    public String getSecondPaySubStdRatioPeriod() {

        return this.secondPaySubStdRatioPeriod;
    }

    public void setSecondPaySubStdConstant(String secondPaySubStdConstant) {

        this.secondPaySubStdConstant = secondPaySubStdConstant;
    }

    public String getSecondPaySubStdConstant() {

        return this.secondPaySubStdConstant;
    }

    public void setSecondPaySubStdConstantPeriod(String secondPaySubStdConstantPeriod) {

        this.secondPaySubStdConstantPeriod = secondPaySubStdConstantPeriod;
    }

    public String getSecondPaySubStdConstantPeriod() {

        return this.secondPaySubStdConstantPeriod;
    }

    public void setSecondPaySubStdAge(String secondPaySubStdAge) {

        this.secondPaySubStdAge = secondPaySubStdAge;
    }

    public String getSecondPaySubStdAge() {

        return this.secondPaySubStdAge;
    }

    public void setFirstPayDateOfDeath(String firstPayDateOfDeath) {

        this.firstPayDateOfDeath = firstPayDateOfDeath;
    }

    public String getFirstPayDateOfDeath() {

        return this.firstPayDateOfDeath;
    }

    public void setSecondPayDateOfDeath(String secondPayDateOfDeath) {

        this.secondPayDateOfDeath = secondPayDateOfDeath;
    }

    public String getSecondPayDateOfDeath() {

        return this.secondPayDateOfDeath;
    }

    public void setDeathStatus(String deathStatus) {

        this.deathStatus = deathStatus;
    }

    public String getDeathStatus() {

        return this.deathStatus;
    }

    public void setBenefitAmount(String benefitAmount) {

        this.benefitAmount = benefitAmount;
    }

    public String getBenefitAmount() {

        return this.benefitAmount;
    }

    public void setBenefitStartDate(String benefitStartDate) {

        this.benefitStartDate = benefitStartDate;
    }

    public String getBenefitStartDate() {

        return this.benefitStartDate;
    }

    public void setBenefitEndDate(String benefitEndDate) {

        this.benefitEndDate = benefitEndDate;
    }

    public String getBenefitEndDate() {

        return this.benefitEndDate;
    }

    public void setBenefitMode(String benefitMode) {

        this.benefitMode = benefitMode;
    }

    public String getBenefitMode() {

        return this.benefitMode;
    }

    public void setCertainPeriodEndDate(String certainPeriodEndDate) {

        this.certainPeriodEndDate = certainPeriodEndDate;
    }

    public String getCertainPeriodEndDate() {

        return this.certainPeriodEndDate;
    }
}
