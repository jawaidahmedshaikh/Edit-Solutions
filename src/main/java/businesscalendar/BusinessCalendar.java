/*
 * User: gfrosti
 * Date: Dec 13, 2004
 * Time: 10:31:55 AM
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package businesscalendar;

import edit.common.*;

import edit.common.exceptions.EDITBusinessDayException;

import edit.services.config.ServicesConfig;

import java.util.Calendar;



public class BusinessCalendar
{
    /**
     * Finds the "best" Business Day using the following rules in the following order:
     * 1) Direct Hit: Finds a Business Day defined for the specified date.
     * 2) Next Hit: Finds the nearest Business Day on or after the given date.
     * 3) Not Hit: Throws an exception.
     * @param date
     * @return
     */
    public BusinessDay getBestUnrestrictedBusinessDayAfter(EDITDate date)
    {
    	System.out.println("Getbusinessdateprior: 2: " + date.getFormattedDate());
        BusinessDay bestBusinessDay = null;

        bestBusinessDay = BusinessDay.findBy_BusinessDate_ActiveInd(date.getFormattedDate());

        if (bestBusinessDay == null)
        {
            EDITDate maximumDate = new EDITDate("9999/12/31");

            bestBusinessDay = BusinessDay.findBy_BusinessDate1_MIN_GT_BusinessDate2(date.getFormattedDate(), maximumDate.getFormattedDate());
        }

        if (bestBusinessDay == null)
        {
            throw new RuntimeException("A 'Best' Business Day Can Not Be Found For [" + date.getFormattedDate() + "]");
        }

        return bestBusinessDay;
    }
    
    /**
     * Finds the "best" Business Day using the following rules in the following order:
     * 1) Direct Hit: Finds a Business Day defined for the specified date.
     * 2) Prior Hit: Finds the nearest Business Day prior to the specified date within the same month.
     * 3) Next Hit: Finds the nearest Business Day after the specified date within the same month.
     * 4) Not Hit: Throws an exception.
     * @param date
     * @return
     */
    public BusinessDay getBestBusinessDay(EDITDate date)
    {
        BusinessDay bestBusinessDay = null;

        bestBusinessDay = BusinessDay.findBy_BusinessDate_ActiveInd(date.getFormattedDate());

        if (bestBusinessDay == null)
        {
            EDITDate minimumDate = getMinimumDate(date);

            bestBusinessDay = BusinessDay.findBy_BusinessDate1_MAX_LT_BusinessDate2_ActiveInd(minimumDate.getFormattedDate(), date.getFormattedDate());
        }

        if (bestBusinessDay == null)
        {
            EDITDate maximumDate = getMaximumDate(date);

            bestBusinessDay = BusinessDay.findBy_BusinessDate1_MIN_GT_BusinessDate2(date.getFormattedDate(), maximumDate.getFormattedDate());
        }

        if (bestBusinessDay == null)
        {
            throw new RuntimeException("A 'Best' Business Day Can Not Be Found For [" + date.getFormattedDate() + "]");
        }

        return bestBusinessDay;
    }
    
    /**
     * This is similar to its cousin method "getBestBusinessDay()" except
     * there is no limitation as to how far back in time the search can go
     * to find the best match. The best match is "unrestricted" by time. 
     * The rules are as follows:
     * 
     * 1) Direct Hit: Finds a Business Day defined for the specified date.
     * 2) Prior Hit: Finds the nearest Business Day prior to the specified date regardless of month or year.
     * 3) Not Hit: Throws an exception.
     * @param date
     * @return
     */
    public BusinessDay getBestUnrestictedBusinessDay(EDITDate date)
    {
        BusinessDay bestBusinessDay = null;

        bestBusinessDay = BusinessDay.findBy_BusinessDate_ActiveInd(date.getFormattedDate());

        if (bestBusinessDay == null)
        {
            EDITDate minimumDate = new EDITDate(EDITDate.DEFAULT_MIN_DATE);

            bestBusinessDay = BusinessDay.findBy_BusinessDate1_MAX_LT_BusinessDate2_ActiveInd(minimumDate.getFormattedDate(), date.getFormattedDate());
        }

        if (bestBusinessDay == null)
        {
            throw new RuntimeException("A 'Best' Business Day Can Not Be Found For [" + date.getFormattedDate() + "]");
        }

        return bestBusinessDay;
    }    
  
    /**
     * Finds the last business day of a week using the following rules:
     * 
     * 1. If the specified date is a Friday, and it is a business day, then 
     * it is a direct hit. If not, then consider # 2.
     * 
     * 2. The specified date is a Friday, but it wasn't configured to be
     * a business day. Starting from the specified date (a Friday) move
     * backward in time until the first business day is found, even if
     * it crosses a month.
     * 
     * 3. Otherwise if the day is [not] a Friday (e.g. a Mon), then find then next 
     * Calendar Friday and see if it is a hit. If not, then consider # 4.
     * 
     * 4. Starting from the Friday from # 3, move backward in time until the first
     * business day is found, even if it crosses a month.
     * @param date
     * @return
     */
    public BusinessDay getLastBusinessDayOfWeek(EDITDate date)
    {
        BusinessDay lastBusinessDayOfWeek = null;
        
        // #1
        if (isFriday(date) && isBusinessDay(date))
        {
            lastBusinessDayOfWeek = new BusinessDay(date);
        }
        
        // #2
        else if (isFriday(date) && !isBusinessDay(date))
        {
            // To use the findBy_BusinessDate1_MAX_LT_BusinessDate2_ActiveInd method, we need a starting date - something "reasonably" before the nextFriday.
            EDITDate startingEDITDate = date.subtractMonths(1);
            
            lastBusinessDayOfWeek = BusinessDay.findBy_BusinessDate1_MAX_LT_BusinessDate2_ActiveInd(startingEDITDate.getFormattedDate(), date.getFormattedDate());            
        }
        
        // #3 (the date was some other day than a Friday)
        else
        {
            EDITDate nextFriday = getNextFriday(date);
            
            if (isBusinessDay(nextFriday))
            {
                lastBusinessDayOfWeek =  new BusinessDay(nextFriday);
            }
            
            // #4
            else
            {
                // To use the findBy_BusinessDate1_MAX_LT_BusinessDate2_ActiveInd method, we need a starting date - something "reasonably" before the nextFriday.
                EDITDate startingEDITDate = nextFriday.subtractMonths(1);
                
                lastBusinessDayOfWeek = BusinessDay.findBy_BusinessDate1_MAX_LT_BusinessDate2_ActiveInd(startingEDITDate.getFormattedDate(), nextFriday.getFormattedDate());
            }
        }

        return lastBusinessDayOfWeek;
    }

    /**
     * Adds the specified date as a BusinessDay.
     * @param businessDay
     * @throws EDITBusinessDayException
     */
    public void addAsBusinessDay(BusinessDay businessDay) throws EDITBusinessDayException
    {
        try
        {
            businessDay.setAsActive();

            businessDay.save();
        }
        catch (IllegalArgumentException e)
        {
            throw new EDITBusinessDayException(e.getMessage());
        }
    }

    /**
     * Removes the specified BusinessDay.
     * @param businessDay
     */
    public void removeAsBusinessDay(BusinessDay businessDay)
    {
        businessDay.setAsInactive();

        businessDay.save();
    }

    /**
     * Finds the nth Business Day after the specified date. The date, itself, does not have to be a Business Day.
     * @param date
     * @return
     */
    public BusinessDay findNextBusinessDay(EDITDate date, int noBusinessDays)
    {
        BusinessDay businessDay = null;

        BusinessDay[] businessDays = BusinessDay.findBy_MaxRows_BusinessDate_GT(date, noBusinessDays);

        if ((businessDays == null) || (businessDays.length < noBusinessDays))
        {
            throw new RuntimeException("Adding Business Days Can Not Be Done - There Are Not Enough Business Days Defined");
        }
        else
        {
            businessDay = businessDays[noBusinessDays - 1];
        }

        return businessDay;
    }

    /**
     * Finds the nth Business Day before the specified date. The date, itself, does not have to be a Business Day.
     * @param date
     * @return
     */
    public BusinessDay findPreviousBusinessDay(EDITDate date, int noBusinessDays)
    {
        BusinessDay businessDay = null;

        BusinessDay[] businessDays = BusinessDay.findBy_MaxRows_BusinessDate_LT(date, noBusinessDays);

        if ((businessDays == null) || (businessDays.length < noBusinessDays))
        {
            throw new RuntimeException("Adding Business Days Can Not Be Done - There Are Not Enough Business Days Defined");
        }
        else
        {
            businessDay = businessDays[noBusinessDays - 1];
        }

        return businessDay;
    }
    
    /**
     * Finds the nth Business Day before the specified date. The date, itself, does not have to be a Business Day.
     * @param date
     * @param noBusinessDays the number of days going back in time from the specified date
     * @activeInd BusinessDate.ActiveInd
     * @return
     */
    public BusinessDay findPreviousBusinessDay(EDITDate date, int noBusinessDays, String activeInd)
    {
        BusinessDay businessDay = null;

        BusinessDay[] businessDays = BusinessDay.findBy_MaxRows_BusinessDate_LT(date, noBusinessDays);

        if ((businessDays == null) || (businessDays.length < noBusinessDays))
        {
            throw new RuntimeException("Adding Business Days Can Not Be Done - There Are Not Enough Business Days Defined");
        }
        else
        {
            businessDay = businessDays[noBusinessDays - 1];
        }

        return businessDay;
    }    

    /**
     * Generates default BusinessDays for the supplied calendar year. Any previously defined BusinessDays are
     * deleted.
     * @param year
     */
    public void generateDefaultBusinessDays(int year)
    {
        BusinessYear businessYear = new BusinessYear(year);

        businessYear.generateDefaultBusinessDays();
    }

    /**
     * Returns the maximum date within the month of the specified date.
     * @param date
     * @return
     */
    public EDITDate getMaximumDate(EDITDate date)
    {
        int year = date.getYear();

        int month = date.getMonth();

        int day = date.getDay();

        Calendar c = Calendar.getInstance();

        c.set(year, month - 1, day);

        int maximumDayOfMonth = c.getActualMaximum(Calendar.DAY_OF_MONTH);

        EDITDate maximumDate = new EDITDate(year, month, maximumDayOfMonth);

        return maximumDate;
    }

    /**
     * Returns the minimum date within the month of the specified date.
     * @param date
     * @return
     */
    private EDITDate getMinimumDate(EDITDate date)
    {
        int year = date.getYear();

        int month = date.getMonth();

        int day = date.getDay();

        Calendar c = Calendar.getInstance();

        c.set(year, month, day);

        int minimumDayOfMonth = c.getActualMinimum(Calendar.DAY_OF_MONTH);

        EDITDate minimumDate = new EDITDate(year, month, minimumDayOfMonth);

        return minimumDate;
    }

    /**
     * Gets the next available Friday from the specified date. If the specified
     * date is a Friday itself, that fact is ignored and the next Friday is found.
     * @param date
     * @return
     */
    public EDITDate getNextFriday(EDITDate date)
    {
        boolean isFriday = false;
        
        EDITDate currentDate = date;
        
        while (!isFriday)
        {
            currentDate = currentDate.addDays(1);
            
            String dayOfWeek = currentDate.getDayOfWeek();
            
            isFriday = dayOfWeek.equals(EDITDate.FRIDAY);
        }
        
        return currentDate;
    }
    
    /**
     * True if the specified date is valid business day within the DB.
     * @param date
     * @return
     */
    private boolean isBusinessDay(EDITDate date)
    {
        BusinessDay businessDay = BusinessDay.findBy_BusinessDate_ActiveInd(date.getFormattedDate());
        
        return (businessDay != null);
    }
    
    /**
     * True if the specified date represents a Friday.
     * @param date
     * @return
     */
    private boolean isFriday(EDITDate date)
    {
        return date.getDayOfWeek().equals(EDITDate.FRIDAY);
    }
    
    public static void main(String[] args)
    {
        ServicesConfig.setEditServicesConfig("C:\\Projects\\JDeveloper\\EDITSolutions\\VisionDevelopment\\webapps\\WEB-INF\\EDITServicesConfig.xml");
        
        BusinessDay businessDay = new BusinessCalendar().getLastBusinessDayOfWeek(new EDITDate("2007/06/01"));
        
        System.out.println("");
    }

}
