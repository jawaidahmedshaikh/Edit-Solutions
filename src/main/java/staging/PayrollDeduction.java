/*
 * User: dlataill
 * Date: Oct 2, 2007
 * Time: 11:05:30 AM
 * 
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement
 */
package staging;

import edit.common.EDITDate;
import edit.common.EDITBigDecimal;
import edit.services.db.hibernate.*;

public class PayrollDeduction extends HibernateEntity
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
     * The foreign key associating SegmentBase
     */ 
    private Long segmentBaseFK;

    /**
     * The associated SegmentBase.
     */
    private SegmentBase segmentBase;

    /**
     * The amount of the payroll deduction for the associated contract.
     */
    private EDITBigDecimal deductionAmount;

    /**
     * The date this deduction was first included in the payroll
     */ 
    private EDITDate firstPayrollDate;
    
    /**
     * 
     */
    private String typeOfChange;

    public static final String DATABASE = SessionHelper.STAGING;


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
     * @see #segmentBaseFK
     * @return Long
     */
    public Long getSegmentBaseFK()
    {
        return segmentBaseFK;
    }

    /**
     * @see #segmentBaseFK
     * @param segmentBaseFK
     */
    public void setSegmentBaseFK(Long segmentBaseFK)
    {
        this.segmentBaseFK = segmentBaseFK;
    }

    /**
     * Get the SegmentBase
     * @return
     */
    public SegmentBase getSegmentBase()
    {
        return segmentBase;
    }

    /**
     * Set the SegmentBase
     * @param segmentBase
     */
    public void setSegmentBase(SegmentBase segmentBase)
    {
        this.segmentBase = segmentBase;
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
     * Setter.
     * @param typeOfChange
     */
    public void setTypeOfChange(String typeOfChange)
    {
        this.typeOfChange = typeOfChange;
    }

    /**
     * Getter.
     * @return
     */
    public String getTypeOfChange()
    {
        return typeOfChange;
    }
    
    public String getDatabase()
    {
        return PayrollDeduction.DATABASE;
    }
}
