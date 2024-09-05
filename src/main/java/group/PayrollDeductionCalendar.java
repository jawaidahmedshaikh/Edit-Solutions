package group;

import edit.common.EDITDate;

import edit.common.EDITMap;

import edit.services.config.ServicesConfig;
import edit.services.db.hibernate.HibernateEntity;
import edit.services.db.hibernate.SessionHelper;

import java.util.List;

/**
 * Deductions to payroll are entered manually, per day, per deduction.
 * 
 * The "date" and "code" of the deduction are captured (along with the
 * associated PayrollDeduction).
 * 
 */
public class PayrollDeductionCalendar extends HibernateEntity
{
    /**
     * The PK.
     */
    private Long payrollDeductionCalendarPK;
    
    /**
     * The associated PayrollDeductionSchedule.
     */
    private PayrollDeductionSchedule payrollDeductionSchedule;
    
    /**
     * For CRUD Compatability.
     */
    private Long payrollDeductionScheduleFK;
    
    /**
     * The date in which the deduction is the occur.
     */
    private EDITDate payrollDeductionDate;
    
    /**
     * An identifier code to clarify the type of deduction (probably
     * a two character code).
     */
    private String payrollDeductionCodeCT;
    
    public String getDatabase()
    {
        return SessionHelper.EDITSOLUTIONS;
    }

    /**
     * @see #payrollDeductionCalendarPK
     * @param payrollDeductionCalendarPK
     */
    public void setPayrollDeductionCalendarPK(Long payrollDeductionCalendarPK)
    {
        this.payrollDeductionCalendarPK = payrollDeductionCalendarPK;
    }

    /**
     * @see #payrollDeductionCalendarPK
     * @return the PK
     */
    public Long getPayrollDeductionCalendarPK()
    {
        return payrollDeductionCalendarPK;
    }

    /**
     * @see #payrollDeductionSchedule
     * @param payrollDeductionSchedule
     */
    public void setPayrollDeduction(PayrollDeductionSchedule payrollDeductionSchedule)
    {
        this.payrollDeductionSchedule = payrollDeductionSchedule;
    }

    /**
     * @see #payrollDeductionSchedule
     * @return the associated PayrollDeductionSchedule
     */
    public PayrollDeductionSchedule getPayrollDeductionSchedule()
    {
        return payrollDeductionSchedule;
    }

    /**
     * @see #payrollDeductionDate
     * @param payrollDeductionDate
     */
    public void setPayrollDeductionDate(EDITDate payrollDeductionDate)
    {
        this.payrollDeductionDate = payrollDeductionDate;
    }

    /**
     * @see #payrollDeductionDate
     * @return the payrollDeductionDate
     */
    public EDITDate getPayrollDeductionDate()
    {
        return payrollDeductionDate;
    }

    /**
     * @see #payrollDeductionCodeCT
     * @param payrollDeductionCodeCT
     */
    public void setPayrollDeductionCodeCT(String payrollDeductionCodeCT)
    {
        this.payrollDeductionCodeCT = payrollDeductionCodeCT;
    }

    /**
     * @see #payrollDeductionCodeCT
     * @return payrollDeductionCodeCT
     */
    public String getPayrollDeductionCodeCT()
    {
        return payrollDeductionCodeCT;
    }
    
    /**
     * Finder. Sorted by PayrollDeductionDate ascending.
     * @param payrollDeductionSchedulePK
     * @param year
     * @return
     */
    public static PayrollDeductionCalendar[] findBy_PayrollDeductionSchedulePK_Year(Long payrollDeductionSchedulePK, int year)
    {
        // Establish a date range from which to search.
        EDITDate startDate = new EDITDate(year, 1, 1);
        
        EDITDate stopDate = new EDITDate(year, 12, 31);
    
        String hql = " select payrollDeductionCalendar" +
                     " from PayrollDeductionCalendar payrollDeductionCalendar" +
                     " where payrollDeductionCalendar.PayrollDeductionScheduleFK = :payrollDeductionSchedulePK" +
                     " and payrollDeductionCalendar.PayrollDeductionDate >= :startDate" +
                     " and payrollDeductionCalendar.PayrollDeductionDate <= :stopDate" +
                     " order by payrollDeductionCalendar.PayrollDeductionDate asc";
                     
        EDITMap params = new EDITMap("payrollDeductionSchedulePK", payrollDeductionSchedulePK).
                        put("startDate", startDate).
                        put("stopDate", stopDate);
                     
        List<PayrollDeductionCalendar> results = SessionHelper.executeHQL(hql, params, SessionHelper.EDITSOLUTIONS);                     
    
        return results.toArray(new PayrollDeductionCalendar[results.size()]);
    }

    /**
     * Finder.
     * @param payrollDeductionCalendarPK
     * @return
     */
    public static PayrollDeductionCalendar findBy_PK(Long payrollDeductionCalendarPK)
    {
        return (PayrollDeductionCalendar)SessionHelper.get(PayrollDeductionCalendar.class, payrollDeductionCalendarPK, SessionHelper.EDITSOLUTIONS);
    }

    /**
     * Removes itself from the DB.
     * NOTE: This purposely performs a "shallow" delete in that this child PayrollDeductionCalendar
     * does not remove itself from its parent PayrollDeduction. Instead, it simply nulls its parent 
     * PayrollDeduction. It is assumed that the parent PayrollDeduction is [not]
     * in session (preventing a resave of the child).This is a performance decision and satisfies the 
     * needs of this method as of this writing.
     */
    public void delete()
    {
        setPayrollDeduction(null);
    
        SessionHelper.delete(this, getDatabase());
    }

    public void setPayrollDeductionSchedule(PayrollDeductionSchedule payrollDeductionSchedule)
    {
        this.payrollDeductionSchedule = payrollDeductionSchedule;
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
     * @see #payrollDeductionScheduleFK
     */
    public Long getPayrollDeductionScheduleFK()
    {
        return payrollDeductionScheduleFK;
    }
    
    /**
     * Finder. Finds the next PayrollDeductionCalendar entry (ascending) relative to the specified 
     * PayrollDeductionDate for the specified PayrollDeductionSchedulePK.
     * @param payrollDeductionSchedulePK
     * @param payrollDeductionDate
     * @return PayrollDeductionCalendar or null if none found
     */
    public static PayrollDeductionCalendar findNextPayrollDeductionCalendarBy_PayrollDeductionSchedulePK_PayrollDeductionDate(Long payrollDeductionSchedulePK, EDITDate payrollDeductionDate)
    {
        PayrollDeductionCalendar payrollDeductionCalendar = null;
    
        String hql = " from PayrollDeductionCalendar payrollDeductionCalendar" +
                     " where payrollDeductionCalendar.PayrollDeductionScheduleFK = :payrollDeductionSchedulePK" +
                     " and payrollDeductionCalendar.PayrollDeductionDate = " +
                     " (" +
                     " select min(payrollDeductionCalendar.PayrollDeductionDate)" +
                     " from PayrollDeductionCalendar payrollDeductionCalendar" +
                     " where payrollDeductionCalendar.PayrollDeductionScheduleFK = :payrollDeductionSchedulePK" +
                     " and payrollDeductionCalendar.PayrollDeductionDate > :payrollDeductionDate" +
                     " )";
                     
        EDITMap params = new EDITMap("payrollDeductionSchedulePK", payrollDeductionSchedulePK)
                        .put("payrollDeductionDate", payrollDeductionDate);
                     
        List<PayrollDeductionCalendar> results = SessionHelper.executeHQL(hql, params, SessionHelper.EDITSOLUTIONS);                     
        
        if (!results.isEmpty())
        {
            payrollDeductionCalendar = results.get(0);
        }
                
        return payrollDeductionCalendar;                
    }

    public void hSave()
    {
        SessionHelper.saveOrUpdate(this, getDatabase());
    }
}


