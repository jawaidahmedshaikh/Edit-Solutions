/*
 * User: dlataille
 * Date: Oct 1, 2007
 * Time: 3:49:59 PM
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package staging;

import edit.common.*;
import edit.services.db.hibernate.*;

import java.util.Set;
import java.util.HashSet;
import java.util.List;

@SuppressWarnings("unused")
public class SegmentBase extends HibernateEntity
{
    private Long segmentBasePK;
    private Long groupFK;
    private Long stagingFK;
    private Long billScheduleFK;
    private Long batchContractSetupFK;
    private String contractNumber;
    private String companyName;
    private String marketingPackage;
    private String groupName;
    private String area;
    private String businessContract;
    private EDITDate effectiveDate;
    private EDITDate terminationDate;
    private String segmentStatus;
    private String optionCode;
    private String ratedGenderCT;
    private String underwritingClassCT;
    private String groupPlan;
    private String issueState;
    private String issueStateORInd;
    private String originalState;
    private EDITDate issueDate;
    private EDITBigDecimal units;
    private EDITBigDecimal faceAmount;
    private EDITDate paidToDate;
    private String deathBenefitOption;
    private String deptLocName;
    private EDITBigDecimal annualPremium;
    private EDITBigDecimal guarPaidUpTerm;
    private EDITBigDecimal nonGuarPaidUpTerm;
    private EDITBigDecimal oneYearTerm;
    private EDITDate originalIssueDate;
    private EDITBigDecimal originalUnits;
    private String majorLine;
    private EDITBigDecimal originalFace;
    private EDITDate paidDate;
    private int ageAtIssue;
    private EDITDate lapseDate;
    private String batchID;
    private String billingCompanyNumber;
    private String groupType;
    private EDITDate conversionDate;
    private String worksheetType;
    private String creationOperator;
    private String clientUpdate;
    private EDITDate requirementEffectiveDate;
    private EDITDate firstNotifyDate;
    private EDITDate previousNotifyDate;
    private EDITDate finalNotifyDate;
    private EDITDate incomeDate;
    private EDITDate nextAnniversaryDate;
    private int policyDuration;
    private String qualifiedIndicator;
    private String qualifiedType;
    private String segmentName;
    private EDITDate submitDate;
    
    private EDITDate incomeStartDate;
    private int incomeStartAge;
    private EDITDate extendedIncomePeriodDate;
    private EDITBigDecimal benefitBase;
    private EDITDate benefitBaseLastValDate;
    private EDITBigDecimal incomeWdAmount;
    private EDITBigDecimal remainingIncomeWdAmount;
    private EDITDate applicationSignedDate;
    private String nonForfeitureOption;

    private String masterContractNumber;
    private String masterContractName;
    private String stateCT;
    private boolean noInterimCoverage;
    private EDITDate masterContractEffectiveDate;
    private String deptLocNumber;
    private EDITDate lastAnniversaryDate;
    private String estateOfTheInsured;
    private String brandingCompanyCT;

    private Group group;
    private BillSchedule billSchedule;
    private Staging staging;
    private BatchContractSetup batchContractSetup;

    private Set<SegmentRider> segmentRiders;
    private Set<ContractClient> contractClients;
    private Set<PayrollDeduction> payrollDeductions;
    private Set<PremiumDue> premiumDues;
    private Set<Bill> bills;
    private Set<Requirement> requirements;
    private Set<ProjectionArray> projectionArrays;
    private Set<ContractStatusHistory> contractStatusHistories;
    private Set<FinancialActivity> financialActivities;
    private Set<Agent> agents;
    private Set<CalculatedValues> calculatedValues;
    private Set<ErrorLog> errorLogs;
    private Set<GIOOptionValue> gioOptionValues;
    private Set<Investment> investments;
    private Set<AnnualStatement> annualStatements;

    public static final String DATABASE = SessionHelper.STAGING;

    public SegmentBase()
    {
        this.segmentRiders = new HashSet<SegmentRider>();
        this.contractClients = new HashSet<ContractClient>();
        this.payrollDeductions = new HashSet<PayrollDeduction>();
        this.premiumDues = new HashSet<PremiumDue>();
        this.bills = new HashSet<Bill>();
        this.requirements = new HashSet<Requirement>();
        this.projectionArrays = new HashSet<ProjectionArray>();
        this.contractStatusHistories = new HashSet<ContractStatusHistory>();
        this.financialActivities = new HashSet<FinancialActivity>();
        this.agents = new HashSet<Agent>();
        this.calculatedValues = new HashSet<CalculatedValues>();
        this.errorLogs = new HashSet<ErrorLog>();
        this.gioOptionValues = new HashSet<GIOOptionValue>();
        this.investments = new HashSet<Investment>();
        this.annualStatements = new HashSet<AnnualStatement>();
    }

    /**
     * Getter.
     * @return
     */
    public Long getSegmentBasePK()
    {
        return segmentBasePK;
    }

    /**
     * Setter.
     * @param segmentBasePK
     */
    public void setSegmentBasePK(Long segmentBasePK)
    {
        this.segmentBasePK = segmentBasePK;
    }

    /**
     * Getter.
     * @return
     */
    public Long getGroupFK()
    {
        return groupFK;
    }

    /**
     * Setter.
     * @param groupFK
     */
    public void setGroupFK(Long groupFK)
    {
        this.groupFK = groupFK;
    }

    /**
     * Getter.
     * @return
     */
    public Group getGroup()
    {
        return group;
    }

    /**
     * Setter.
     * @param group
     */
    public void setGroup(Group group)
    {
        this.group = group;
    }

    /**
     * Getter.
     * @return
     */
    public Long getStagingFK()
    {
        return stagingFK;
    }

    /**
     * Setter.
     * @param stagingFK
     */
    public void setStagingFK(Long stagingFK)
    {
        this.stagingFK = stagingFK;
    }

    /**
     * Getter.
     * @return
     */
    public Staging getStaging()
    {
        return staging;
    }

    /**
     * Setter.
     * @param staging
     */
    public void setStaging(Staging staging)
    {
        this.staging = staging;
    }

    /**
     * Getter.
     * @return
     */
    public Long getBillScheduleFK()
    {
        return billScheduleFK;
    }

    /**
     * Setter.
     * @param billScheduleFK
     */
    public void setBillScheduleFK(Long billScheduleFK)
    {
        this.billScheduleFK = billScheduleFK;
    }

    /**
     * Getter.
     * @return
     */
    public BillSchedule getBillSchedule()
    {
        return billSchedule;
    }

    /**
     * Setter.
     * @param billSchedule
     */
    public void setBillSchedule(BillSchedule billSchedule)
    {
        this.billSchedule = billSchedule;
    }

    /**
     * Getter.
     * @return
     */
    public Long getBatchContractSetupFK()
    {
        return batchContractSetupFK;
    }

    /**
     * Setter.
     * @param batchContractSetupFK
     */
    public void setBatchContractSetupFK(Long batchContractSetupFK)
    {
        this.batchContractSetupFK = batchContractSetupFK;
    }

    /**
     * Getter.
     * @return
     */
    public BatchContractSetup getBatchContractSetup()
    {
        return batchContractSetup;
    }

    /**
     * Setter.
     * @param batchContractSetup
     */
    public void setBatchContractSetup(BatchContractSetup batchContractSetup)
    {
        this.batchContractSetup = batchContractSetup;
    }

    /**
     * Getter.
     * @return
     */
    public String getContractNumber()
    {
        return contractNumber;
    }

    /**
     * Setter.
     * @param contractNumber
     */
    public void setContractNumber(String contractNumber)
    {
        this.contractNumber = contractNumber;
    }

    /**
     * Getter.
     * @return
     */
    public String getCompanyName()
    {
        return companyName;
    }

    /**
     * Setter.
     * @param companyName
     */
    public void setCompanyName(String companyName)
    {
        this.companyName = companyName;
    }

    /**
     * Getter.
     * @return
     */
    public String getMarketingPackage()
    {
        return marketingPackage;
    }

    /**
     * Setter.
     * @param marketingPackage
     */
    public void setMarketingPackage(String marketingPackage)
    {
        this.marketingPackage = marketingPackage;
    }

    /**
     * Getter.
     * @return
     */
    public String getGroupName()
    {
        return groupName;
    }

    /**
     * Setter.
     * @param groupName
     */
    public void setGroupName(String groupName)
    {
        this.groupName = groupName;
    }

    /**
     * Getter.
     * @return
     */
    public String getArea()
    {
        return area;
    }

    /**
     * Setter.
     * @param area
     */
    public void setArea(String area)
    {
        this.area = area;
    }

    /**
     * Getter.
     * @return
     */
    public String getBusinessContract()
    {
        return businessContract;
    }

    /**
     * Setter.
     * @param businessContract
     */
    public void setBusinessContract(String businessContract)
    {
        this.businessContract = businessContract;
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
    public EDITDate getTerminationDate()
    {
        return terminationDate;
    }

    /**
     * Setter.
     * @param terminationDate
     */
    public void setTerminationDate(EDITDate terminationDate)
    {
        this.terminationDate = terminationDate;
    }

    /**
     * Getter.
     * @return
     */
    public String getSegmentStatus()
    {
        return segmentStatus;
    }

    /**
     * Setter.
     * @param segmentStatus
     */
    public void setSegmentStatus(String segmentStatus)
    {
        this.segmentStatus = segmentStatus;
    }

    /**
     * Getter.
     * @return
     */
    public String getOptionCode()
    {
        return optionCode;
    }

    /**
     * Setter.
     * @param optionCode
     */
    public void setOptionCode(String optionCode)
    {
        this.optionCode = optionCode;
    }
    
    /**
     * Getter.
     * @return
     */
    public String getRatedGenderCT()
    {
        return ratedGenderCT;
    }

    /**
     * Setter.
     * @param ratedGenderCT
     */
    public void setRatedGenderCT(String ratedGenderCT)
    {
        this.ratedGenderCT = ratedGenderCT;
    }
    /**
     * Getter.
     * @return
     */
    public String getUnderwritingClassCT()
    {
        return underwritingClassCT;
    }

    /**
     * Setter.
     * @param underwritingClassCt
     */
    public void setUnderwritingClassCT(String underwritingClassCT)
    {
        this.underwritingClassCT = underwritingClassCT;
    }
     /**
     * Getter.
     * @return
     */
    public String getGroupPlan()
    {
        return groupPlan;
    }

    /**
     * Setter.
     * @param groupPlan
     */
    public void setGroupPlan(String groupPlan)
    {
        this.groupPlan = groupPlan;
    }
    /**
     * Getter.
     * @return
     */
    public String getIssueState()
    {
        return issueState;
    }

    /**
     * Setter.
     * @param issueState
     */
    public void setIssueState(String issueState)
    {
        this.issueState = issueState;
    }

    /**
     * Getter.
     * @return
     */
    public String getOriginalState()
    {
        return originalState;
    }


    /**
     * Getter.
     * @return
     */
    public String getIssueStateORInd()
    {
        return issueStateORInd;
    }

    /**
     * Setter.
     * @param originalState
     */
    public void setOriginalState(String originalState)
    {
        this.originalState = originalState;
    }

    /**
     * Setter.
     * @param issueState
     */
    public void setIssueStateORInd(String issueStateORInd)
    {
        this.issueStateORInd = issueStateORInd;
    }

    /**
     * Getter.
     * @return
     */
    public EDITDate getIssueDate()
    {
        return issueDate;
    }

    /**
     * Setter.
     * @param issueDate
     */
    public void setIssueDate(EDITDate issueDate)
    {
        this.issueDate = issueDate;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getUnits()
    {
        return units;
    }

    /**
     * Setter.
     * @param units
     */
    public void setUnits(EDITBigDecimal units)
    {
        this.units = units;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getFaceAmount()
    {
        return faceAmount;
    }

    /**
     * Setter.
     * @param faceAmount
     */
    public void setFaceAmount(EDITBigDecimal faceAmount)
    {
        this.faceAmount = faceAmount;
    }

    /**
     * Getter.
     * @return
     */
    public EDITDate getPaidToDate()
    {
        return paidToDate;
    }

    /**
     * Setter.
     * @param paidToDate
     */
    public void setPaidToDate(EDITDate paidToDate)
    {
        this.paidToDate = paidToDate;
    }

    /**
     * Getter.
     * @return
     */
    public String getDeathBenefitOption()
    {
        return deathBenefitOption;
    }

    /**
     * Setter.
     * @param deathBenefitOption
     */
    public void setDeathBenefitOption(String deathBenefitOption)
    {
        this.deathBenefitOption = deathBenefitOption;
    }

    /**
     * Getter.
     * @return
     */
    public String getDeptLocName()
    {
        return deptLocName;
    }

    /**
     * Setter.
     * @param deptLocName
     */
    public void setDeptLocName(String deptLocName)
    {
        this.deptLocName = deptLocName;
    }

    /**
     * Getter.
     * @return
     */
    public String getDeptLocNumber()
    {
        return deptLocNumber;
    }

    /**
     * Setter.
     * @param deptLocNumber
     */
    public void setDeptLocNumber(String deptLocNumber)
    {
        this.deptLocNumber = deptLocNumber;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getAnnualPremium()
    {
        return annualPremium;
    }

    /**
     * Setter.
     * @param annualPremium
     */
    public void setAnnualPremium(EDITBigDecimal annualPremium)
    {
        this.annualPremium = annualPremium;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getGuarPaidUpTerm()
    {
        return guarPaidUpTerm;
    }

    /**
     * Setter.
     * @param guarPaidUpTerm
     */
    public void setGuarPaidUpTerm(EDITBigDecimal guarPaidUpTerm)
    {
        this.guarPaidUpTerm = guarPaidUpTerm;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getNonGuarPaidUpTerm()
    {
        return nonGuarPaidUpTerm;
    }

    /**
     * Setter.
     * @param nonGuarPdUpTerm
     */
    public void setNonGuarPaidUpTerm(EDITBigDecimal nonGuarPdUpTerm)
    {
        this.nonGuarPaidUpTerm = nonGuarPdUpTerm;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getOneYearTerm()
    {
        return oneYearTerm;
    }

    /**
     * Setter.
     * @param oneYearTerm
     */
    public void setOneYearTerm(EDITBigDecimal oneYearTerm)
    {
        this.oneYearTerm = oneYearTerm;
    }

    /**
     * Getter.
     * @return
     */
    public String getBatchID()
    {
        return batchID;
    }

    /**
     * Setter.
     * @param batchID
     */
    public void setBatchID(String batchID)
    {
        this.batchID = batchID;
    }

    /**
     * Getter.
     * @return
     */
    public String getBillingCompanyNumber()
    {
        return billingCompanyNumber;
    }

    /**
     * Setter.
     * @param billingCompanyNumber
     */
    public void setBillingCompanyNumber(String billingCompanyNumber)
    {
        this.billingCompanyNumber = billingCompanyNumber;
    }

    /**
     * Getter.
     * @return
     */
    public String getGroupType()
    {
        return groupType;
    }

    /**
     * Setter.
     * @param groupType
     */
    public void setGroupType(String groupType)
    {
        this.groupType = groupType;
    }

    /**
     * Getter.
     * @return
     */
    public EDITDate getConversionDate()
    {
        return conversionDate;
    }

    /**
     * Setter.
     * @param conversionDate
     */
    public void setConversionDate(EDITDate conversionDate)
    {
        this.conversionDate = conversionDate;
    }

    /**
     * Getter.
     * @return
     */
    public String getWorksheetType()
    {
        return worksheetType;
    }

    /**
     * @see #masterContractNumber
     * @return the masterContractNumber
     */
    public String getMasterContractNumber()
    {
        return masterContractNumber;
    }

    /**
     * @param masterContractNumber
     * @see #masterContractNumber
     */
    public void setMasterContractNumber(String masterContractNumber)
    {
        this.masterContractNumber = masterContractNumber;
    }
    /**
     * Getter.
     * @return
     */
    public String getMasterContractName()
    {
        return masterContractName;
    }

    /**
     * Setter.
     * @param masterContractName
     */
    public void setMasterContractName(String masterContractName)
    {
        this.masterContractName = masterContractName;
    }

    /**
     * @see #StateCT
     * @return
     */
    public String getStateCT()
    {
        return stateCT;
    }

	 /**
     * @see # StateCT
     * @param  StateCT
     */
    public void setStateCT(String stateCT)
    {
        this.stateCT = stateCT;
    }


/**
     * @see #effectiveDate
     * @return
     */
    public EDITDate getMasterContractEffectiveDate()
    {
        return masterContractEffectiveDate;
    }

    /**
     * @see #effectiveDate
     * @param effectiveDate
     */
    public void setMasterContractEffectiveDate(EDITDate masterContractEffectiveDate)
    {
        this.masterContractEffectiveDate = masterContractEffectiveDate;
    }

    /**
     * Setter.
     * @param worksheetType
     */
    public void setWorksheetType(String worksheetType)
    {
        this.worksheetType = worksheetType;
    }

    /**
     * Getter.
     * @return
     */
    public String getCreationOperator()
    {
        return creationOperator;
    }

    /**
     * Setter.
     * @param creationOperator
     */
    public void setCreationOperator(String creationOperator)
    {
        this.creationOperator = creationOperator;
    }

    /**
     * Getter.
     * @return
     */
    public String getClientUpdate()
    {
        return clientUpdate;
    }

    /**
     * Setter.
     * @param clientUpdate
     */
    public void setClientUpdate(String clientUpdate)
    {
        this.clientUpdate = clientUpdate;
    }

    /**
     * Getter.
     * @return
     */
    public EDITDate getRequirementEffectiveDate()
    {
        return requirementEffectiveDate;
    }

    /**
     * Setter.
     * @param requirementEffectiveDate
     */
    public void setRequirementEffectiveDate(EDITDate requirementEffectiveDate)
    {
        this.requirementEffectiveDate = requirementEffectiveDate;
    }

    /**
     * Getter.
     * @return
     */
    public EDITDate getFirstNotifyDate()
    {
        return firstNotifyDate;
    }

    /**
     * Setter.
     * @param firstNotifyDate
     */
    public void setFirstNotifyDate(EDITDate firstNotifyDate)
    {
        this.firstNotifyDate = firstNotifyDate;
    }

    /**
     * Getter.
     * @return
     */
    public EDITDate getPreviousNotifyDate()
    {
        return previousNotifyDate;
    }

    /**
     * Setter.
     * @param previousNotifyDate
     */
    public void setPreviousNotifyDate(EDITDate previousNotifyDate)
    {
        this.previousNotifyDate = previousNotifyDate;
    }

    /**
     * Getter.
     * @return
     */
    public EDITDate getFinalNotifyDate()
    {
        return finalNotifyDate;
    }

    /**
     * Setter.
     * @param finalNotifyDate
     */
    public void setFinalNotifyDate(EDITDate finalNotifyDate)
    {
        this.finalNotifyDate = finalNotifyDate;
    }

    /**
     * Getter.
     * @return
     */
    public Set<SegmentRider> getSegmentRiders()
    {
        return segmentRiders;
    }

    /**
     * Setter.
     * @param segmentRiders
     */
    public void setSegmentRiders(Set<SegmentRider> segmentRiders)
    {
        this.segmentRiders = segmentRiders;
    }

    /**
     * Add another segmentRider to the current mapped segmentRiders.
     * @param segmentRider
     */
    public void addSegmentRider(SegmentRider segmentRider)
    {
        this.segmentRiders.add(segmentRider);
    }

    /**
     * Getter.
     * @return
     */
    public Set<ContractClient> getContractClients()
    {
        return contractClients;
    }

    /**
     * Setter.
     * @param contractClients
     */
    public void setContractClients(Set<ContractClient> contractClients)
    {
        this.contractClients = contractClients;
    }

    /**
     * Add another contractClient to the current mapped contractClients.
     * @param contractClient
     */
    public void addContractClient(ContractClient contractClient)
    {
        this.contractClients.add(contractClient);
    }

    /**
     * Getter.
     * @return
     */
    public Set<PayrollDeduction> getPayrollDeductions()
    {
        return payrollDeductions;
    }

    /**
     * Setter.
     * @param payrollDeductions
     */
    public void setPayrollDeductions(Set<PayrollDeduction> payrollDeductions)
    {
        this.payrollDeductions = payrollDeductions;
    }

    /**
     * Add another payrollDeduction to the current mapped payrollDeductions.
     * @param payrollDeduction
     */
    public void addPayrollDeduction(PayrollDeduction payrollDeduction)
    {
        this.payrollDeductions.add(payrollDeduction);
    }

    /**
     * Getter.
     * @return
     */
    public Set<PremiumDue> getPremiumDues()
    {
        return premiumDues;
    }

    /**
     * Setter.
     * @param premiumDues
     */
    public void setPremiumDues(Set<PremiumDue> premiumDues)
    {
        this.premiumDues = premiumDues;
    }

    /**
     * Add another premiumDue to the current mapped premiumDues.
     * @param premiumDue
     */
    public void addPremiumDue(PremiumDue premiumDue)
    {
        this.premiumDues.add(premiumDue);
    }

    /**
     * Getter.
     * @return
     */
    public Set<Bill> getBills()
    {
        return bills;
    }

    /**
     * Setter.
     * @param bills
     */
    public void setBills(Set<Bill> bills)
    {
        this.bills = bills;
    }

    /**
     * Add another bill to the current mapped bills.
     * @param bill
     */
    public void addBill(Bill bill)
    {
        this.bills.add(bill);
    }

    /**
     * Getter.
     * @return
     */
    public Set<Requirement> getRequirements()
    {
        return requirements;
    }

    /**
     * Setter.
     * @param requirements
     */
    public void setRequirements(Set<Requirement> requirements)
    {
        this.requirements = requirements;
    }

    /**
     * Add another requirement to the current mapped requirements.
     * @param requirement
     */
    public void addRequirement(Requirement requirement)
    {
        this.requirements.add(requirement);
    }

    /**
     * Getter.
     * @return
     */
    public Set<ProjectionArray> getProjectionArrays()
    {
        return projectionArrays;
    }

    /**
     * Setter.
     * @param projectionArrays
     */
    public void setProjectionArrays(Set<ProjectionArray> projectionArrays)
    {
        this.projectionArrays = projectionArrays;
    }

    /**
     * Add another projectionArray to the current mapped projectionArrays.
     * @param projectionArray
     */
    public void addProjectionArray(ProjectionArray projectionArray)
    {
        this.projectionArrays.add(projectionArray);
    }

    /**
     * Getter.
     * @return
     */
    public Set<ContractStatusHistory> getContractStatusHistories()
    {
        return contractStatusHistories;
    }

    /**
     * Setter.
     * @param contractStatusHistories
     */
    public void setContractStatusHistories(Set<ContractStatusHistory> contractStatusHistories)
    {
        this.contractStatusHistories = contractStatusHistories;
    }

    /**
     * Add another contractStatusHistory to the current mapped contractStatusHistories.
     * @param contractStatusHistory
     */
    public void addContractStatusHistory(ContractStatusHistory contractStatusHistory)
    {
        this.contractStatusHistories.add(contractStatusHistory);
    }

    /**
     * Getter.
     * @return
     */
    public Set<FinancialActivity> getFinancialActivities()
    {
        return financialActivities;
    }

    /**
     * Setter.
     * @param financialActivities
     */
    public void setFinancialActivities(Set<FinancialActivity> financialActivities)
    {
        this.financialActivities = financialActivities;
    }

    /**
     * Add another financialActivity to the current mapped financialActivities.
     * @param financialActivity
     */
    public void addFinancialActivity(FinancialActivity financialActivity)
    {
        this.financialActivities.add(financialActivity);
    }
    
    /**
     * Getter.
     * @return
     */
    public Set<Agent> getAgents()
    {
        return agents;
    }
    
    /**
     * Setter.
     * @param agents
     */
    public void setAgents(Set<Agent> agents)
    {
        this.agents = agents;
    }

    /**
     * Add another agent the current mapped agents.
     * @param agent
     */
    public void addAgent(Agent agent)
    {
        this.agents.add(agent);
    }
    
    /**
     * Getter.
     * @return
     */
    public Set<AnnualStatement> getAnnualStatements()
    {
        return annualStatements;
    }
    
    /**
     * Setter.
     * @param annualStatements
     */
    public void setAnnualStatements(Set<AnnualStatement> annualStatements)
    {
        this.annualStatements = annualStatements;
    }

    /**
     * Add another annualStatement the current mapped annualStatements.
     * @param annualStatement
     */
    public void addAnnualStatement(AnnualStatement annualStatement)
    {
        this.annualStatements.add(annualStatement);
    }

    /**
     * Getter.
     * @return
     */
    public Set<CalculatedValues> getCalculatedValues()
    {
        return calculatedValues;
    }

    /**
     * Setter.
     * @param calculatedValues
     */
    public void setCalculatedValues(Set<CalculatedValues> calculatedValues)
    {
        this.calculatedValues = calculatedValues;
    }

    /**
     * Add another calculatedValues record the current mapped calculatedValues.
     * @param calculatedValues
     */
    public void addCalculatedValues(CalculatedValues calculatedValues)
    {
        this.calculatedValues.add(calculatedValues);
    }

    /**
     * Setter.
     * @param originalIssueDate
     */
    public void setOriginalIssueDate(EDITDate originalIssueDate)
    {
        this.originalIssueDate = originalIssueDate;
    }

    /**
     * Getter.
     * @return
     */
    public EDITDate getOriginalIssueDate()
    {
        return originalIssueDate;
    }

    /**
     * Setter.
     * @param originalUnits
     */
    public void setOriginalUnits(EDITBigDecimal originalUnits)
    {
        this.originalUnits = originalUnits;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getOriginalUnits()
    {
        return originalUnits;
    }

    /**
     * Setter.
     * @param majorLine
     */
    public void setMajorLine(String majorLine)
    {
        this.majorLine = majorLine;
    }

    /**
     * Getter.
     * @return
     */
    public String getMajorLine()
    {
        return majorLine;
    }

    /**
     * Setter.
     * @param originalFace
     */
    public void setOriginalFace(EDITBigDecimal originalFace)
    {
        this.originalFace = originalFace;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getOriginalFace()
    {
        return originalFace;
    }

    /**
     * Setter.
     * @param paidDate
     */
    public void setPaidDate(EDITDate paidDate)
    {
        this.paidDate = paidDate;
    }

    /**
     * Getter.
     * @return
     */
    public EDITDate getPaidDate()
    {
        return paidDate;
    }

    /**
     * Setter.
     * @param agetAtIssue
     */
    public void setAgeAtIssue(int ageAtIssue)
    {
        this.ageAtIssue = ageAtIssue;
    }

    /**
     * Getter.
     * @return
     */
    public int getAgeAtIssue()
    {
        return ageAtIssue;
    }

    /**
     * Setter.
     * @param lapseDate
     */
    public void setLapseDate(EDITDate lapseDate)
    {
        this.lapseDate = lapseDate;
    }

    /**
     * Getter.
     * @return
     */
    public EDITDate getLapseDate()
    {
        return lapseDate;
    }

    /**
     * Getter.
     * @return
     */
    public Set<ErrorLog> getErrorLogs()
    {
        return errorLogs;
    }

    /**
     * Setter.
     * @param Set<errorLog>
     */
    public void setErrorLogs(Set<ErrorLog> errorLogs)
    {
        this.errorLogs = errorLogs;
    }

    /**
     * Add another errorLog record the current mapped errorLogs.
     * @param errorLog
     */
    public void addErrorLog(ErrorLog errorLog)
    {
        this.errorLogs.add(errorLog);
    }

    public Set<GIOOptionValue> getGIOOptionValues()
    {
        return gioOptionValues;
    }

    public void setGIOOptionValues(Set<GIOOptionValue> gioOptionValues)
    {
        this.gioOptionValues = gioOptionValues;
    }

    /**
     * Getter.
     * @return
     */
    public Set<Investment> getInvestments()
    {
        return investments;
    }

    /**
     * Setter.
     * @param investments
     */
    public void setInvestments(Set<Investment> investments)
    {
        this.investments = investments;
    }

    /**
     * Add another investment record to the current mapped investments.
     * @param investment
     */
    public void addInvestment(Investment investment)
    {
        this.investments.add(investment);
    }

    public String getDatabase()
    {
        return SegmentBase.DATABASE;
    }

    /**
     * Setter.
     * @param incomeDate
     */
    public void setIncomeDate(EDITDate incomeDate)
    {
        this.incomeDate = incomeDate;
    }

    /**
     * Getter.
     * @return
     */
    public EDITDate getIncomeDate()
    {
        return incomeDate;
    }

    /**
     * Setter.
     * @param nextAnniversaryDate
     */
    public void setNextAnniversaryDate(EDITDate nextAnniversaryDate)
    {
        this.nextAnniversaryDate = nextAnniversaryDate;
    }

    /**
     * Getter.
     * @return
     */
    public EDITDate getNextAnniversaryDate()
    {
        return nextAnniversaryDate;
    }

    /**
     * Setter.
     * @param lastAnniversaryDate
     */
    public void setLastAnniversaryDate(EDITDate lastAnniversaryDate)
    {
        this.lastAnniversaryDate = lastAnniversaryDate;
    }

    /**
     * Getter.
     * @return
     */
    public EDITDate getLastAnniversaryDate()
    {
        return lastAnniversaryDate;
    }

    /**
     * Setter.
     * @param policyDuration
     */
    public void setPolicyDuration(int policyDuration)
    {
        this.policyDuration = policyDuration;
    }

    /**
     * Getter.
     * @return
     */
    public int getPolicyDuration()
    {
        return policyDuration;
    }

    /**
     * Setter.
     * @param qualifiedIndicator
     */
    public void setQualifiedIndicator(String qualifiedIndicator)
    {
        this.qualifiedIndicator = qualifiedIndicator;
    }

    /**
     * Getter.
     * @return
     */
    public String getQualifiedIndicator()
    {
        return qualifiedIndicator;
    }

    /**
     * Setter.
     * @param qualifiedType
     */
    public void setQualifiedType(String qualifiedType)
    {
        this.qualifiedType = qualifiedType;
    }

    /**
     * Getter.
     * @return
     */
    public String getQualifiedType()
    {
        return qualifiedType;
    }

    /**
     * Setter.
     * @param segmentName
     */
    public void setSegmentName(String segmentName)
    {
        this.segmentName = segmentName;
    }

    /**
     * Getter.
     * @return
     */
    public String getSegmentName()
    {
        return segmentName;
    }

    /**
     * Setter.
     * @param submitDate
     */
    public void setSubmitDate(EDITDate submitDate)
    {
        this.submitDate = submitDate;
    }

    /**
     * Getter.
     * @return
     */
    public EDITDate getSubmitDate()
    {
        return submitDate;
    }

    /**
     * Setter.
     * @param estateOfTheInsured
     */
    public void setEstateOfTheInsured(String estateOfTheInsured)
    {
        this.estateOfTheInsured = estateOfTheInsured;
    }

    /**
     * Getter.
     * @return
     */
    public String getEstateOfTheInsured()
    {
        return estateOfTheInsured;
    }
    
    public void setBrandingCompanyCT(String brandingCompanyCT)
    {
        this.brandingCompanyCT = brandingCompanyCT;
    }

    public String getBrandingCompanyCT()
    {
        return brandingCompanyCT;
    }

    public static SegmentBase findByStagingFK_ContractNumber(Long stagingFK, String contractNumber)
    {
        String hql = " select sb from SegmentBase sb" +
                    " where sb.StagingFK = :stagingFK" +
                    " and sb.ContractNumber = :contractNumber";

        EDITMap params = new EDITMap();
        params.put("stagingFK", stagingFK);
        params.put("contractNumber", contractNumber);

        List<SegmentBase> results = SessionHelper.executeHQL(hql, params, SegmentBase.DATABASE);

        if (results.size() > 0)
        {
            return (SegmentBase) results.get(0);
        }
        else
        {
            return null;
        }
    }

    @SuppressWarnings("unchecked")
	public static SegmentBase findBy_SegmentBasePK(Long segmentBasePK)
    {
        String hql = " select sb from SegmentBase sb" +
                    " where sb.SegmentBasePK = :segmentBasePK";

        EDITMap params = new EDITMap();
        params.put("segmentBasePK", segmentBasePK);

        List<SegmentBase> results = SessionHelper.executeHQL(hql, params, SegmentBase.DATABASE);

        if (results.size() > 0)
        {
            return (SegmentBase) results.get(0);
        }
        else
        {
            return null;
        }
    }
    public static SegmentBase findBy_ContractNumber(String contractNumber)
    {
        String hql = " select sb from SegmentBase sb" +
                    " where sb.ContractNumber = :contractNumber";

        EDITMap params = new EDITMap();
        params.put("contractNumber", contractNumber);

        List<SegmentBase> results = SessionHelper.executeHQL(hql, params, SegmentBase.DATABASE);

        if (results.size() > 0)
        {
            return (SegmentBase) results.get(0);
        }
        else
        {
            return null;
        }
    }
    
    public void setIncomeStartDate(EDITDate incomeStartDate)
    {
        this.incomeStartDate = incomeStartDate;
    }

    public EDITDate getIncomeStartDate()
    {
        return incomeStartDate;
    }

    public void setIncomeStartAge(int incomeStartAge)
    {
        this.incomeStartAge = incomeStartAge;
    }

    public int getIncomeStartAge()
    {
        return incomeStartAge;
    }

    public void setExtendedIncomePeriodDate(EDITDate extendedIncomePeriodDate)
    {
        this.extendedIncomePeriodDate = extendedIncomePeriodDate;
    }

    public EDITDate getExtendedIncomePeriodDate()
    {
        return extendedIncomePeriodDate;
    }

    public void setBenefitBase(EDITBigDecimal benefitBase)
    {
        this.benefitBase = benefitBase;
    }

    public EDITBigDecimal getBenefitBase()
    {
        return benefitBase;
    }

    public void setBenefitBaseLastValDate(EDITDate benefitBaseLastValDate)
    {
        this.benefitBaseLastValDate = benefitBaseLastValDate;
    }

    public EDITDate getBenefitBaseLastValDate()
    {
        return benefitBaseLastValDate;
    }

    public void setIncomeWdAmount(EDITBigDecimal incomeWdAmount)
    {
        this.incomeWdAmount = incomeWdAmount;
    }

    public EDITBigDecimal getIncomeWdAmount()
    {
        return incomeWdAmount;
    }

    public void setRemainingIncomeWdAmount(EDITBigDecimal remainingIncomeWdAmount)
    {
        this.remainingIncomeWdAmount = remainingIncomeWdAmount;
    }

    public EDITBigDecimal getRemainingIncomeWdAmount()
    {
        return remainingIncomeWdAmount;
    }

    public EDITDate getApplicationSignedDate()
    {
        return applicationSignedDate;
    }

    public void setApplicationSignedDate(EDITDate applicationSignedDate)
    {
        this.applicationSignedDate = applicationSignedDate;
    }

    public String getNonForfeitureOption()
    {
        return nonForfeitureOption;
    }

    public void setNonForfeitureOption(String nonForfeitureOption)
    {
        this.nonForfeitureOption = nonForfeitureOption;
    }

	public boolean isNoInterimCoverage() {
		return noInterimCoverage;
	}

	public void setNoInterimCoverage(boolean noInterimCoverage) {
		this.noInterimCoverage = noInterimCoverage;
	}
}
