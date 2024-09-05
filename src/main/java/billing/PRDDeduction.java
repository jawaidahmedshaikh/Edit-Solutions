package billing;

import edit.common.EDITDate;

import fission.utility.DateTimeUtil;

/**
 * Finds the number of deductions
 * in a specified month. The calculation requires the number of deductions
 * for the specified month, and the next BillShedule.LastDeductionDate 
 * justifying this class.
 */
public class PRDDeduction
{
    /**
     * The date which serves as the starting point from which
     * to determine the number of deductions with the month
     * of the lastDeductionDate's month.
     */
    private EDITDate lastDeductionDate;
    
    /**
     * The frequency of the deductions. Likely 7 or 14 days.
     * @see #PRD_FREQUENCY_7
     * @see #PRD_FREQUENCY_14
     */
    private int deductionFrequency;
    
    /**
     * The next BillSchedule.LastDeductionDate generated after
     * performing the deduction calculation.
     */
    private EDITDate nextLastDeductionDate;
    
    /**
     * Once calculated, the number of deductions
     * with the month of the specified date using the
     * specified deduction frequency.
     */
    private int numberOfDeductions;
 
    /**
     * Helps determine if the 2-2-3 algorithm for 
     * calculating number of deductions should apply.
     */
    public static final int PRD_FREQUENCY_14 = 14;

    /**
     * Helps determine if the 4-4-5 algorithm for 
     * calculating number of deductions should apply.
     */
    public static final int PRD_FREQUENCY_7 = 7;

    /**
     * Constructor.
     * @param lastDeductionDate
     * @param deductionFrequency
     * @see #lastDeductionDate
     * @see #deductionFrequency
     */
    public PRDDeduction(EDITDate lastDeductionDate, int deductionFrequency)
    {
        this.lastDeductionDate = lastDeductionDate;

        if (deductionFrequency == 26)
        {
            this.deductionFrequency = 14;
        }
        else if (deductionFrequency == 52)
        {
            this.deductionFrequency = 7;
        }
        else
        {
            this.deductionFrequency = deductionFrequency;
        }
    }
    
    /**
     * Determines the number of deductions based on the following rules:
     * 
     * 1. If PRD Frequency = 26 (2-2-3):
     *      a. Start with the BillSchedule.LastDeductionDate
     *      b. Get the last day of the month of the BillSchedule.LastDeductionDate
     *      c. Subtract the two numbers, mod 14 (make note of the remainder) and add 1 to get the number of deductions.
     *      
     *      e.g.
     *      i.      BillSchedule.LastDeductionDate = 08/02/07
     *      ii.     LastDayOfMonth = 08/31/07
     *      iii.    (08/31/07 - 08/02/07) % 14 + 1 = (29 % 14) + 1 = 2R1 + 1 = 3R1 (3 deductions with a remainder of 1 day from the % calculation)
     *      
     *      d. To calculate the next PRD date:
     *      a. Take the last day of the month (as in b.)
     *      b. Subtract the remainder from c.
     *      c. Add 14 days.
     *      
     *      e.g. (from above calculation)
     *      i.  (08/31/07 - R1 days) + 14 days = 8/30/07 + 14 days = 09/13/07
     *      
     *      
     *  2.  If PRD Frequency = 52, use the exact same approach as in 1., but use mod 7 instead of mod 14.
     *  
     *      e.g. (# deductions)
     *      i.      BillSchedule.LastDeductionDate = 08/05/07
     *      ii.     LastDayOfMonth = 08/31/07
     *      iii.    (08/31/07 - 08/05/07) % 7 + 1 = (26 % 7) + 1 = 3R5 + 1 = 4R1 (4 deductions R5 days)
     *      
     *      e.g. (next BillSchedule.LastDeductionDate)
     *      i.  (08/31/07 - R5 days) + 7 days = 08/26/07 + 7 days = 09/02/07
     */    
    public void calculate()
    {
        setNumberOfDeductions(calculateNumberOfDeductions());
        
        setNextLastDeductionDate(calculateNextLastDeductionDate());
    }
    
    /**
     * @see #calculate()
     * @return
     */
    private int calculateNumberOfDeductions()
    {
        int currentDay = getLastDeductionDate().getDay();
        
        int lastDayOfMonth = getLastDeductionDate().getEndOfMonthDate().getDay();

        int numberOfDeductions = ((lastDayOfMonth - currentDay) / getDeductionFrequency()) + 1;
        
        return numberOfDeductions;
    }
    
    /**
     * @see #calculate()
     * @return
     */    
    private EDITDate calculateNextLastDeductionDate()
    {
        int jumpDays = getNumberOfDeductions() * getDeductionFrequency();        

        EDITDate nextLastDeductionDate = getLastDeductionDate().addDays(jumpDays);

        return nextLastDeductionDate;
    }    

    /**
     * @see #lastDeductionDate
     * @return
     */
    public EDITDate getLastDeductionDate()
    {
        return lastDeductionDate;
    }

    /**
     * @see #deductionFrequency
     * @return
     */
    public int getDeductionFrequency()
    {
        return deductionFrequency;
    }

    /**
     * @see #nextLastDeductionDate
     * @return
     */
    public EDITDate getNextLastDeductionDate()
    {
        return nextLastDeductionDate;
    }

    /**
     * @see #lastDeductionDate
     */
    private void setLastDeductionDate(EDITDate lastDeductionDate)
    {
        this.lastDeductionDate = lastDeductionDate;
    }

    /**
     * @see #nextLastDeductionDate
     */
    private void setNextLastDeductionDate(EDITDate nextLastDeductionDate)
    {
        this.nextLastDeductionDate = nextLastDeductionDate;
    }

    /**
     * @see #numberOfDeductions
     */
    private void setNumberOfDeductions(int numberOfDeductions)
    {
        this.numberOfDeductions = numberOfDeductions;
    }

    /**
     * @see #numberOfDeductions
     * @return
     */
    public int getNumberOfDeductions()
    {
        return numberOfDeductions;
    }
}
