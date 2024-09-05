/*
 * User: dlataill
 * Date: Jun 14, 2007
 * Time: 8:50:30 AM
 * 
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement
 */
package group;

import edit.common.EDITDate;
import edit.common.EDITBigDecimal;
import edit.common.EDITMap;
import edit.services.db.hibernate.*;
import contract.*;

import java.util.HashSet;
import java.util.Set;
import java.util.List;
import java.util.ArrayList;

import staging.IStaging;
import staging.StagingContext;

public class PayrollDeduction extends HibernateEntity implements IStaging
{
    /**
     * The PK.
     */ 
    private Long payrollDeductionPK;
    
    /**
     * The foreign key associating PayrollDeductionSchedule.
     */
    private Long payrollDeductionScheduleFK;

    /**
     * The associated PayrollDeductionSchedule.
     */
    private PayrollDeductionSchedule payrollDeductionSchedule;

    /**
     * The foreign key associating Segment
     */ 
    private Long segmentFK;

    /**
     * The associated Segment.
     */
    private Segment segment;

    /**
     * The amount of the payroll deduction for the associated contract.
     */
    private EDITBigDecimal deductionAmount;
    
    /**
     * The date this record was included in the prd extract.
     */
    private EDITDate prdExtractDate;
    
    /**
     * The date this deduction was first included in the payroll
     */ 
    private EDITDate firstPayrollDate;
    
    /**
     * 
     */
    private String typeOfChange;

    public static final String DATABASE = SessionHelper.EDITSOLUTIONS;



    /** Creates a new instance of PayrollDeduction */
    public PayrollDeduction()
    {
    }

    /**
     * @see #payrollDeductionPK
     * @return Long
     */
    public Long getPayrollDeductionPK()
    {
        return payrollDeductionPK;
    }

    /**
     * @see #payrollDeductionPK
     * @param payrollDeductionPK
     */
    public void setPayrollDeductionPK(Long payrollDeductionPK)
    {
        this.payrollDeductionPK = payrollDeductionPK;
    }

    /**
     * @see #payrollDeductionScheduleFK
     * @return Long
     */    
    public Long getPayrollDeductionScheduleFK()
    {
        return payrollDeductionScheduleFK;
    }

    /**
     * @see #payrollDeductionScheduleFK
     * @param payrollDeductionScheduleFK
     */    
    public void setPayrollDeductionScheduleFK(Long payrollDeductionScheduleFK)
    {
        this.payrollDeductionScheduleFK = payrollDeductionScheduleFK;
    }

    /**
     * Get the PayrollDeductionSchedule
     * @return
     */
    public PayrollDeductionSchedule getPayrollDeductionSchedule()
    {
        return payrollDeductionSchedule;
    }

    /**
     * Set PayrollDeductionSchedule
     * @param payrollDeductionSchedule
     */
    public void setPayrollDeductionSchedule(PayrollDeductionSchedule payrollDeductionSchedule)
    {
        this.payrollDeductionSchedule = payrollDeductionSchedule;
    }

    /**
     * @see #segmentFK
     * @return Long
     */
    public Long getSegmentFK()
    {
        return segmentFK;
    }

    /**
     * @see #segmentFK
     * @param segmentFK
     */
    public void setSegmentFK(Long segmentFK)
    {
        this.segmentFK = segmentFK;
    }

    /**
     * Get the Segment
     * @return
     */
    public Segment getSegment()
    {
        return segment;
    }

    /**
     * Set the Segment
     * @param segment
     */
    public void setSegment(Segment segment)
    {
        this.segment = segment;
    }

    /**
     * @see #deductionAmount
     * @return EDITBigDecimal
     */    
    public EDITBigDecimal getDeductionAmount()
    {
        return deductionAmount;
    }

    /**
     * @see #deductionAmount
     * @param deductionAmount
     */    
    public void setDeductionAmount(EDITBigDecimal deductionAmount)
    {
        this.deductionAmount = deductionAmount;
    }

    /**
     * @see #prdExtractDate
     * @return EDITDate
     */
    public EDITDate getPRDExtractDate()
    {
        return prdExtractDate;
    }

    /**
     * @see #prdExtractDate
     * @param prdExtractDate
     */
    public void setPRDExtractDate(EDITDate prdExtractDate)
    {
        this.prdExtractDate = prdExtractDate;
    }

    /**
     * @see #firstPayrollDate
     * @return Long
     */    
    public EDITDate getFirstPayrollDate()
    {
        return firstPayrollDate;
    }

    /**
     * @see #firstPayrollDate
     * @param firstPayrollDate
     */    
    public void setFirstPayrollDate(EDITDate firstPayrollDate)
    {
        this.firstPayrollDate = firstPayrollDate;
    }

    /**
     * @see #typeOfChange
     * @return
     */
    public String getTypeOfChange()
    {
        return this.typeOfChange;
    }

    /**
     * @see #typeOfChange
     * @param typeOfChange
     */
    public void setTypeOfChange(String typeOfChange)
    {
        this.typeOfChange = typeOfChange;
    }

    public String getDatabase()
    {
        return PayrollDeduction.DATABASE;
    }

    /**
     * Finds the PayrollDeduction for the given PayrollDeductionPK
     * @param contractGroupPK
     * @return
     */
    public static PayrollDeduction findByPK(Long payrollDeductionPK)
    {
        String hql = " select payrollDeduction from PayrollDeduction payrollDeduction" +
                     " where payrollDeduction.PayrollDeductionPK = :payrollDeductionPK";

        EDITMap params = new EDITMap();

        params.put("payrollDeductionPK", payrollDeductionPK);

        List<PayrollDeduction> results = SessionHelper.executeHQL(hql, params, PayrollDeduction.DATABASE);

        if (results.size() > 0)
        {
            return (PayrollDeduction) results.get(0);
        }
        else
        {
            return null;
        }
    }

    public static PayrollDeduction[] findByExtractDate(EDITDate extractDate)
    {
        String hql = " select payrollDeduction from PayrollDeduction payrollDeduction" +
                     " where payrollDeduction.PRDExtractDate = :extractDate" +
                     " order by payrollDeduction.PayrollDeductionScheduleFK";

        EDITMap params = new EDITMap();

        params.put("extractDate", extractDate);

        List<PayrollDeduction> results = SessionHelper.executeHQL(hql, params, PayrollDeduction.DATABASE);

        return (PayrollDeduction[]) results.toArray(new PayrollDeduction[results.size()]);
    }

    public static PayrollDeduction[] findByExtractDate_prdScheduleFK(EDITDate extractDate, Long prdSchedFK)
    {
        String hql = " select payrollDeduction from PayrollDeduction payrollDeduction" +
                     " where payrollDeduction.PRDExtractDate = :extractDate" +
                     " and payrollDeduction.PayrollDeductionScheduleFK = :payrollDeductionScheduleFK";

        EDITMap params = new EDITMap();

        params.put("extractDate", extractDate);
        params.put("payrollDeductionScheduleFK", prdSchedFK);

        List<PayrollDeduction> results = SessionHelper.executeHQL(hql, params, PayrollDeduction.DATABASE);

        return (PayrollDeduction[]) results.toArray(new PayrollDeduction[results.size()]);
    }

    public StagingContext stage(StagingContext stagingContext, String database)
    {
        staging.PayrollDeduction stagingPRD = new staging.PayrollDeduction();
        stagingPRD.setPayrollDeductionSchedule(stagingContext.getCurrentPRDSchedule());
        stagingPRD.setSegmentBase(stagingContext.getCurrentSegmentBase());
        stagingPRD.setDeductionAmount(this.deductionAmount);
        stagingPRD.setFirstPayrollDate(this.firstPayrollDate);
        stagingPRD.setTypeOfChange(this.typeOfChange);

        stagingContext.getCurrentSegmentBase().addPayrollDeduction(stagingPRD);
        stagingContext.getCurrentPRDSchedule().addPayrollDeduction(stagingPRD);

        SessionHelper.saveOrUpdate(stagingPRD, database);

        return stagingContext;
    }
}
