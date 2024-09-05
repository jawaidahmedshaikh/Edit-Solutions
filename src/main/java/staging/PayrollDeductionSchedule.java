/**
 * User: dlataill
 * Date: Oct 2, 2007
 * Time: 10:37:30 PM
 * <p/>
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement
 */
package staging;

import edit.services.db.hibernate.HibernateEntity;
import edit.services.db.hibernate.SessionHelper;
import edit.common.EDITDate;

import java.util.Set;
import java.util.HashSet;

public class PayrollDeductionSchedule extends HibernateEntity
{
    private Long payrollDeductionSchedulePK;
    private Long groupFK;
    private Group group;
    private Long stagingFK;
    private Staging staging;
    private String prdType;
    private int initialLeadDays;
    private int subsequentLeadDays;
    private EDITDate effectiveDate;
    private EDITDate terminationDate;
    private EDITDate lastPRDExtractDate;
    private EDITDate nextPRDExtractDate;
    private EDITDate nextPRDDueDate;
    private EDITDate currentDateThru;
    private String prdConsolidation;
    private String reportType;
    private String sortOption;
    private String summary;
    private String outputType;
    private String creationOperator;
    private EDITDate creationDate;

    private Set<PayrollDeduction> payrollDeductions;

    /**
     * The set of dates for which the deduction will occur.
     */
    public static final String DATABASE = SessionHelper.STAGING;


    public PayrollDeductionSchedule()
    {
        // Set defaults
        terminationDate = new EDITDate(EDITDate.DEFAULT_MAX_DATE);
        this.payrollDeductions = new HashSet<PayrollDeduction>();
    }

    /**
     * Get PayrollDeductionSchedulePK
     * @return
     */
    public Long getPayrollDeductionSchedulePK()
    {
        return payrollDeductionSchedulePK;
    }

    /**
     * Set PayrollDeductionSchedulePK
     * @param payrollDeductionSchedulePK the PK
     */
    public void setPayrollDeductionSchedulePK(Long payrollDeductionSchedulePK)
    {
        this.payrollDeductionSchedulePK = payrollDeductionSchedulePK;
    }

    /**
     * Get GroupFK
     * @return
     */
    public Long getGroupFK()
    {
        return groupFK;
    }

    /**
     * Set GroupFK
     * @param groupFK
     */
    public void setGroupFK(Long groupFK)
    {
        this.groupFK = groupFK;
    }

    /**
     * Get the Group (one of the parents of PayrollDeductionSchedule)
     * @return
     */
    public Group getGroup()
    {
        return group;
    }

    /**
     * Set Group (one of the parents of PayrollDeductionSchedule)
     * @param group
     */
    public void setGroup(Group group)
    {
        this.group = group;
    }

    /**
     * Get StagingFK
     * @return
     */
    public Long getStagingFK()
    {
        return stagingFK;
    }

    /**
     * Set StagingFK
     * @param stagingFK
     */
    public void setStagingFK(Long stagingFK)
    {
        this.stagingFK = stagingFK;
    }

    /**
     * Get the Staging (one of the parents of PayrollDeductionSchedule)
     * @return
     */
    public Staging getStaging()
    {
        return staging;
    }

    /**
     * Set Staging (one of the parents of PayrollDeductionSchedule)
     * @param staging
     */
    public void setStaging(Staging staging)
    {
        this.staging = staging;
    }

    /**
     * Get PRDType
     * @return
     */
    public String getPRDType()
    {
        return prdType;
    }

    /**
     * Set PRDType
     * @param prdType
     */
    public void setPRDType(String prdType)
    {
        this.prdType = prdType;
    }

    /**
     * Get InitialLeadDays
     * @return
     */
    public int getInitialLeadDays()
    {
        return initialLeadDays;
    }

    /**
     * Set InitialLeadDays
     * @param initialLeadDays
     */
    public void setInitialLeadDays(int initialLeadDays)
    {
        this.initialLeadDays = initialLeadDays;
    }

    /**
     * Get SubsequentLeadDays
     * @return
     */
    public int getSubsequentLeadDays()
    {
        return subsequentLeadDays;
    }

    /**
     * Set SubsequentLeadDays
     * @param subsequentLeadDays
     */
    public void setSubsequentLeadDays(int subsequentLeadDays)
    {
        this.subsequentLeadDays = subsequentLeadDays;
    }

    /**
     * Get EffectiveDate
     * @return
     */
    public EDITDate getEffectiveDate()
    {
        return effectiveDate;
    }

    /**
     * Set EffectiveDate
     * @param effectiveDate
     */
    public void setEffectiveDate(EDITDate effectiveDate)
    {
        this.effectiveDate = effectiveDate;
    }

    /**
     * Get TerminationDate
     * @return
     */
    public EDITDate getTerminationDate()
    {
        return terminationDate;
    }

    /**
     * Set TerminationDate
     * @param terminationDate
     */
    public void setTerminationDate(EDITDate terminationDate)
    {
        this.terminationDate = terminationDate;
    }

    /**
     * Get LastPRDExtractDate
     * @return
     */
    public EDITDate getLastPRDExtractDate()
    {
        return lastPRDExtractDate;
    }

    /**
     * Set LastPRDExtractDate
     * @param lastPRDExtractDate
     */
    public void setLastPRDExtractDate(EDITDate lastPRDExtractDate)
    {
        this.lastPRDExtractDate = lastPRDExtractDate;
    }

    /**
     * Get NextPRDExtractDate
     * @return
     */
    public EDITDate getNextPRDExtractDate()
    {
        return nextPRDExtractDate;
    }

    /**
     * Set NextPRDExtractDate
     * @param nextPRDExtractDate
     */
    public void setNextPRDExtractDate(EDITDate nextPRDExtractDate)
    {
        this.nextPRDExtractDate = nextPRDExtractDate;
    }

    /**
     * Get NextPRDDueDate
     * @return
     */
    public EDITDate getNextPRDDueDate()
    {
        return nextPRDDueDate;
    }

    /**
     * Set NextPRDDueDate
     * @param nextPRDDueDate
     */
    public void setNextPRDDueDate(EDITDate nextPRDDueDate)
    {
        this.nextPRDDueDate = nextPRDDueDate;
    }

    /**
     * Get currentDateThru
     * @return
     */
    public EDITDate getCurrentDateThru()
    {
        return currentDateThru;
    }

    /**
     * Set currentDateThru
     * @param currentDateThru
     */
    public void setCurrentDateThru(EDITDate currentDateThru)
    {
        this.currentDateThru = currentDateThru;
    }

    /**
     * Get PRDConsolidation
     * @return
     */
    public String getPRDConsolidation()
    {
        return prdConsolidation;
    }

    /**
     * Set PRDConsolidation
     * @param prdConsolidation
     */
    public void setPRDConsolidation(String prdConsolidation)
    {
        this.prdConsolidation = prdConsolidation;
    }

    /**
     * Get ReportType
     * @return
     */
    public String getReportType()
    {
        return reportType;
    }

    /**
     * Set ReportType
     * @param reportType
     */
    public void setReportType(String reportType)
    {
        this.reportType = reportType;
    }

    /**
     * Get SortOption
     * @param sortOption
     * @return
     */
    public String getSortOption()
    {
        return sortOption;
    }

    /**
     * Set SortOption
     * @param sortOption
     */
    public void setSortOption(String sortOption)
    {
        this.sortOption = sortOption;
    }

    /**
     * Get Summary
     * @return
     */
    public String getSummary()
    {
        return summary;
    }

    /**
     * Set Summary
     * @param summary
     */
    public void setSummary(String summary)
    {
        this.summary = summary;
    }

    /**
     * Get OutputType
     * @return
     */
    public String getOutputType()
    {
        return outputType;
    }

    /**
     * Set OutputType
     * @param outputType
     */
    public void setOutputType(String outputType)
    {
        this.outputType = outputType;
    }

    /**
     * Get CreationOperator
     * @return
     */
    public String getCreationOperator()
    {
        return creationOperator;
    }

    /**
     * Set CreationOperator
     * @param creationOperator
     */
    public void setCreationOperator(String creationOperator)
    {
        this.creationOperator = creationOperator;
    }

    /**
     * Get CreationDate
     * @return
     */
    public EDITDate getCreationDate()
    {
        return creationDate;
    }

    /**
     * Set CreationDate
     * @param creationDate
     */
    public void setCreationDate(EDITDate creationDate)
    {
        this.creationDate = creationDate;
    }

    /**
     * Return the set of PayrollDeduction records associated with this PayrollDeductionSchedule
     * @return
     */
    public Set<PayrollDeduction> getPayrollDeductions()
    {
        return payrollDeductions;
    }

    /**
     * @see #payrollDeductions
     * @param payrollDeductions
     */
    public void setPayrollDeductions(Set<PayrollDeduction> payrollDeductions)
    {
        this.payrollDeductions = payrollDeductions;
    }

    public void addPayrollDeduction(PayrollDeduction payrollDeduction)
    {
        this.payrollDeductions.add(payrollDeduction);
    }

    public String getDatabase()
    {
        return PayrollDeductionSchedule.DATABASE;
    }
}
