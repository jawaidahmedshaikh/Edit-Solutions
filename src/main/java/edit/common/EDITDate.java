/*
 * User: sdorman
 * Date: Jul 15, 2005
 * Time: 5:34:09 PM
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement. 
 */

package edit.common;

import java.util.GregorianCalendar;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.io.Serializable;

import logging.LogEvent;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import edit.services.logging.Logging;
import electric.util.holder.booleanInOut;
import businesscalendar.BusinessCalendar;
import businesscalendar.BusinessDay;

import org.joda.time.DateTime;
import org.joda.time.Days;


/**
 * The EDITDate class provides all the date functionality for the EDITSolutions system.  It specifies the standard
 * format for all dates, including a minimum and maximum date.
 * <P>
 * If an invalid date is used to instantiate this class, a RuntimeException is thrown.  The dates are NOT lenient, this
 * means that strict adherence to proper dates is used.  For example, September 31 is not valid, it will not be rolled
 * over as October 1.
 * <P>
 * This class is immutable, which means that you can't change the values once you have created an instance.  EDITDate
 * uses the GregorianCalendar which is not immutable.  GregorianCalendar is, admittedly by Sun, badly designed and
 * written.  The lack of immutability is one of many problems.  EDITDate tries to isolate those problems from our
 * system.
 */
public final class EDITDate implements Serializable, Cloneable
{
    public static final String DATE_DELIMITER = "/";

    public static final String DAY_FORMAT = "dd";
    public static final String MONTH_FORMAT = "MM";
    public static final String YEAR_FORMAT = "yyyy";
    public static final String YY_YEAR_FORMAT = "yy";

    public static final String PERIOD_FORMAT = YEAR_FORMAT + DATE_DELIMITER + MONTH_FORMAT;
    public static final String DATE_FORMAT = YEAR_FORMAT + DATE_DELIMITER + MONTH_FORMAT + DATE_DELIMITER + DAY_FORMAT;
    public static final String MMDDYYYY_DATE_FORMAT = MONTH_FORMAT + DATE_DELIMITER + DAY_FORMAT + DATE_DELIMITER + YEAR_FORMAT;
    public static final String MMDDYYYY_DATE_FORMAT_NO_DELIMITER = MONTH_FORMAT + DAY_FORMAT + YEAR_FORMAT;
    public static final String YYYYMMDD_DATE_FORMAT_NO_DELIMITER = YEAR_FORMAT + MONTH_FORMAT + DAY_FORMAT;

    public static final String DEFAULT_MAX_DAY = "31";
    public static final String DEFAULT_MAX_MONTH = "12";
    public static final String DEFAULT_MAX_YEAR = "9999";

    public static final String DEFAULT_MIN_DAY = "01";
    public static final String DEFAULT_MIN_MONTH = "01";
    public static final String DEFAULT_MIN_YEAR = "1800";

    public static final String DEFAULT_MAX_DATE = DEFAULT_MAX_YEAR + DATE_DELIMITER + DEFAULT_MAX_MONTH + DATE_DELIMITER + DEFAULT_MAX_DAY;
    public static final String DEFAULT_MIN_DATE = DEFAULT_MIN_YEAR + DATE_DELIMITER + DEFAULT_MIN_MONTH + DATE_DELIMITER + DEFAULT_MIN_DAY;

    public static final int FIRST_QUARTER  = 1;
    public static final int SECOND_QUARTER = 2;
    public static final int THIRD_QUARTER  = 3;
    public static final int FOURTH_QUARTER = 4;

    public static final int FIRST_QUARTER_START_MONTH  = 1;
    public static final int SECOND_QUARTER_START_MONTH = 4;
    public static final int THIRD_QUARTER_START_MONTH  = 7;
    public static final int FOURTH_QUARTER_START_MONTH = 10;

    public static final int FIRST_QUARTER_END_MONTH  = 3;
    public static final int SECOND_QUARTER_END_MONTH = 6;
    public static final int THIRD_QUARTER_END_MONTH  = 9;
    public static final int FOURTH_QUARTER_END_MONTH = 12;

    public static final int FIRST_QUARTER_START_DAY = 1;
    public static final int SECOND_QUARTER_START_DAY = 1;
    public static final int THIRD_QUARTER_START_DAY = 1;
    public static final int FOURTH_QUARTER_START_DAY = 1;

    public static final int FIRST_QUARTER_END_DAY = 31;
    public static final int SECOND_QUARTER_END_DAY = 30;
    public static final int THIRD_QUARTER_END_DAY = 30;
    public static final int FOURTH_QUARTER_END_DAY = 31;

    public static final String DAILY_MODE = "Daily";
    public static final String WEEKLY_MODE = "Weekly";
    public static final String MONTHLY_MODE = "Monthly";
    public static final String QUARTERLY_MODE = "Quarterly";
    public static final String SEMI_ANNUAL_MODE = "SemiAnnual";
    public static final String ANNUAL_MODE = "Annual";
    public static final String BIWEEKLY_MODE = "BiWeekly";
    public static final String BIMONTHLY_MODE = "BiMonthly";
    public static final String THIRTEENTHLY_MODE = "13thly";
    public static final String SEMI_MONTHLY_MODE = "SemiMonthly";

    public static final String SUNDAY = "Sunday";
    public static final String MONDAY = "Monday";
    public static final String TUESDAY = "Tuesday";
    public static final String WEDNESDAY = "Wednesday";
    public static final String THURSDAY = "Thursday";
    public static final String FRIDAY = "Friday";
    public static final String SATURDAY = "Saturday";

    public static final String[] MONTH_NAMES = { "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December" };

    private static final Pattern REG_EXPR_YYYYMMDD_DATE_FORMAT = Pattern.compile("(\\d\\d\\d\\d" + EDITDate.DATE_DELIMITER + "\\d\\d" + EDITDate.DATE_DELIMITER + "\\d\\d)");
    private static final Pattern REG_EXPR_DDMMYYYY_DATE_FORMAT = Pattern.compile("(\\d\\d" + EDITDate.DATE_DELIMITER + "\\d\\d" + EDITDate.DATE_DELIMITER + "\\d\\d\\d\\d)");

    private final GregorianCalendar gregorianCalendar;

    /**
     * Constructor with no arguments defaults to today's date
     *
     * @throws RuntimeException if the date is not valid
     */
    public EDITDate() throws RuntimeException
    {
        // Note: When the GregorianCalendar is instantiated with no parameters
        // the GregorianCalendar takes current time also, but for EDITDate we 
        // do not need time portion of the date.
        GregorianCalendar gc = new GregorianCalendar();
        // Need not deduct 1 from month since we are getting month from GregorianCalendar 
        this.gregorianCalendar = new GregorianCalendar(gc.get(GregorianCalendar.YEAR), 
                                                       gc.get(GregorianCalendar.MONTH),
                                                       gc.get(GregorianCalendar.DATE));
        this.gregorianCalendar.setLenient(false);
    }

    /**
     * Constructor taking ints as arguments
     *
     * @param year      year value to set for this date
     * @param month     month value to set for this date
     * @param day       day value to set for this date
     *
     * @throws RuntimeException if the date is not valid
     */
    public EDITDate(int year, int month, int day) throws RuntimeException
    {
        // GregorianCalendar uses 0-11 for months
        this.gregorianCalendar = new GregorianCalendar(year, month-1, day);
        this.gregorianCalendar.setLenient(false);

        if (! isAValidDate())
        {
            throw new RuntimeException("Invalid date trying to be created: year = " + year + ", month = " + month + ", day = " + day);
        }
    }

    /**
     * Constructor taking strings as arguments
     *
     * @param year      year value to set for this date
     * @param month     month value to set for this date
     * @param day       day value to set for this date
     *
     * @throws RuntimeException if the date is not valid
     */
    public EDITDate(Date dt) throws RuntimeException
    {
    	@SuppressWarnings("deprecation")
		String year=""+new Date().getYear();
    	String month=""+new Date().getMonth();
    	String day=""+new Date().getDay();
    	this.gregorianCalendar = new GregorianCalendar(Integer.parseInt(year), Integer.parseInt(month)-1, Integer.parseInt(day));
        this.gregorianCalendar.setLenient(false);
        System.out.println("the current date is "+gregorianCalendar.toString());
        
    }
    public EDITDate(String year, String month, String day) throws RuntimeException
    {
        // GregorianCalendar uses 0-11 for months
        this.gregorianCalendar = new GregorianCalendar(Integer.parseInt(year), Integer.parseInt(month)-1, Integer.parseInt(day));
        this.gregorianCalendar.setLenient(false);

        if (! isAValidDate())
        {
            throw new RuntimeException("Invalid date trying to be created: year = " + year + ", month = " + month + ", day = " + day);
        }
    }

    /**
     * Constructor taking another EDITDate as an argument.
     *
     * @param editDate      EDITDate whose values will be used to create new object
     *
     * @throws RuntimeException if the date is not valid
     */
    public EDITDate(EDITDate editDate) throws RuntimeException
    {
        this.gregorianCalendar = new GregorianCalendar(editDate.getYear(), editDate.getMonth()-1, editDate.getDay());
        this.gregorianCalendar.setLenient(false);

        if (! isAValidDate())
        {
            throw new RuntimeException("Invalid date trying to be created: year = " + editDate.getYear() + ", month = " + (editDate.getMonth()-1) + ", day = " + editDate.getDay());
        }
    }

    /**
     * Constructor taking the number of milliseconds in time
     *
     * @param milliseconds
     *
     * @throws RuntimeException if the date is not valid
     */
    public EDITDate(long milliseconds) throws RuntimeException
    {
        this.gregorianCalendar = new GregorianCalendar();
        this.gregorianCalendar.setTimeInMillis(milliseconds);
        this.gregorianCalendar.setLenient(false);

        if (! isAValidDate())
        {
            throw new RuntimeException("Invalid date trying to be created: milliseconds = " + milliseconds);
        }
    }

    /**
     * Constructor taking a full date string formatted as EDITDate.DATE_FORMAT.
     *
     * @param formattedDateString      string containing a formatted date.
     *
     * @throws RuntimeException if the date is not valid
     */
    public EDITDate(String formattedDateString) throws RuntimeException
    {
    	/*
    	 * deck:
    	 * Dates from the ui are not always being converted from MM/dd/yyyy to yyyy/MM/dd.  
    	 * This appears to be a javascript/browser version problem.  Best solution until ui is 
    	 * updated to work with latest browsers is to catch and correct here.
    	*/
        this.gregorianCalendar = new GregorianCalendar();
        this.gregorianCalendar.setLenient(false);
    	if (formattedDateString != null) {

            Date dateObject = null;
    	    SimpleDateFormat dateFormat = null;
            Matcher m = REG_EXPR_DDMMYYYY_DATE_FORMAT.matcher(formattedDateString);
            if (m.matches()) {
           	    //System.out.println("Date format is dd/MM/yyyy: " + formattedDateString);
                dateFormat = new SimpleDateFormat(EDITDate.MMDDYYYY_DATE_FORMAT);
            } else {
        	    // default to expected format of yyyy/MM/dd
                dateFormat = new SimpleDateFormat(EDITDate.DATE_FORMAT);
            }

            try {
                dateObject = dateFormat.parse(formattedDateString);
            } catch (ParseException e) {
                LogEvent event = new LogEvent("Error parsing formatted date in EDITDate constructor: " + formattedDateString, e);
                Logger logger = LogManager.getLogger(Logging.GENERAL_EXCEPTION);
                logger.error(event);
            }

            this.gregorianCalendar.setTime(dateObject);
    	}

        if (!isAValidDate()) {
            throw new RuntimeException("Invalid date trying to be created: " + formattedDateString);
        }
    }
    
    /**
     * Copies this EDITDate object and returns a new object
     *
     * @return  new EDITDate object with the same values as this object
     */
    public final EDITDate copy()
    {
        EDITDate clone = new EDITDate(this);

        return clone;
    }

    /**
     * Compares 2 dates for equality.
     *
     * @param compareTo     date to compare to this object
     *
     * @return  true if the dates are equal, false otherwise
     */
    public final boolean equals(EDITDate compareTo)
    {
        return this.gregorianCalendar.equals(compareTo.gregorianCalendar);
    }
    
    @Override
    public boolean equals(Object obj)
    {
        boolean datesAreEqual = false;
 
        if (obj != null)
        {
            datesAreEqual = equals((EDITDate) obj);
        }

        return datesAreEqual;
    }  

    /**
     * Compares 2 dates to see which date is earlier
     *
     * @param compareTo     date to compare to this object
     *
     * @return  true if this date comes before the compareTo date, false otherwise
     */
    public final boolean before(EDITDate compareTo)
    {
        return this.gregorianCalendar.before(compareTo.gregorianCalendar);
    }

    /**
     * Compares 2 dates to see which date is later
     *
     * @param compareTo     date to compare to this object
     *
     * @return  true if this date comes after the compareTo date, false otherwise
     */
    public final boolean after(EDITDate compareTo)
    {
        return this.gregorianCalendar.after(compareTo.gregorianCalendar);
    }

    /**
     * Compares 2 Dates to see if date later or equal
     * @param compareTo
     * @return
     */
    public final boolean afterOREqual(EDITDate compareTo)
    {
        boolean dateGreaterOrEqual = false;

        if (this.gregorianCalendar.after(compareTo.gregorianCalendar) ||
            this.gregorianCalendar.equals(compareTo.gregorianCalendar))
        {
            dateGreaterOrEqual = true;
        }

        return dateGreaterOrEqual;
    }

    /**
     * Compares 2 Dates to see if date later or equal
     * @param compareTo
     * @return
     */
    public final boolean beforeOREqual(EDITDate compareTo)
    {
        boolean dateLessOrEqual = false;

        if (this.gregorianCalendar.before(compareTo.gregorianCalendar) ||
            this.gregorianCalendar.equals(compareTo.gregorianCalendar))
        {
            dateLessOrEqual = true;
        }

        return dateLessOrEqual;
    }

    /**
     * Returns the day unit of this date
     *
     * @return  the value of the day unit
     */
    public final int getDay()
    {
        return this.gregorianCalendar.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * Returns the month unit of this date
     *
     * @return  the value of the month unit
     */
    public final int getMonth()
    {
        return this.gregorianCalendar.get(Calendar.MONTH) + 1;
    }

    /**
     * Returns the year unit of this date
     *
     * @return  the value of the year unit
     */
    public final int getYear()
    {
        return this.gregorianCalendar.get(Calendar.YEAR);
    }

    /**
     * Returns the date as a string formatted according to the standard EDITDate format
     *
     * @return  formatted string
     */
    public final String getFormattedDate()
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat(EDITDate.DATE_FORMAT);
        
        return dateFormat.format(this.gregorianCalendar.getTime());
    }
    
    /**
     * Returns the date as a string formatted as MM/dd/yyyy
     *
     * @return  formatted string
     */
    public final String getMMDDYYYYDate()
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat(EDITDate.MMDDYYYY_DATE_FORMAT);
        
        return dateFormat.format(this.gregorianCalendar.getTime());
    }

    /**
     * Returns the date as a string formatted as MMddyyyy
     *
     * @return  formatted string
     */
    public final String getMMDDYYYYDateNoDelimiter()
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat(EDITDate.MMDDYYYY_DATE_FORMAT_NO_DELIMITER);
        
        return dateFormat.format(this.gregorianCalendar.getTime());
    }

    public final String getYYYYMMDDDateNoDelimiter()
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat(EDITDate.YYYYMMDD_DATE_FORMAT_NO_DELIMITER);
        
        return dateFormat.format(this.gregorianCalendar.getTime());
    }

    /**
     * Returns the date as a string formatted as yyyy/MM
     *
     * @return  formatted string
     */
    public final String getYYYYMMDate()
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat(EDITDate.PERIOD_FORMAT);
        
        return dateFormat.format(this.gregorianCalendar.getTime());
    }
   
    /**
     * Overrides the Object class's toString to provide the formatted date.  THIS METHOD SHOULD NOT BE USED
     * DIRECTLY!  It only exists because the toString method is automatically called when pushing/popping items on the
     * stack in script processing.  It is also automatically called by Hibernate.
     * <P>
     * Use the getFormattedDate() method instead (results are identical);
     *
     * @return  formatted string
     * 
     * @see #getFormattedDate()
     */
    public final String toString()
    {
        return getFormattedDate();
    }

    /**
     * Returns the day unit of the date as a string formatted according to the standard EDITDate format
     *
     * @return  formatted string
     */
    public final String getFormattedDay()
    {
        SimpleDateFormat dayFormat = new SimpleDateFormat(EDITDate.DAY_FORMAT);
        
        return dayFormat.format(this.gregorianCalendar.getTime());
    }

    /**
     * Returns the month unit of the date as a string formatted according to the standard EDITDate format
     *
     * @return  formatted string
     */
    public final String getFormattedMonth()
    {
        SimpleDateFormat monthFormat = new SimpleDateFormat(EDITDate.MONTH_FORMAT);
        
        return monthFormat.format(this.gregorianCalendar.getTime());
    }

    /**
     * Returns the year unit of the date as a string formatted according to the standard EDITDate format
     *
     * @return  formatted string
     */
    public final String getFormattedYear()
    {
        SimpleDateFormat yearFormat = new SimpleDateFormat(EDITDate.YEAR_FORMAT);
        
        return yearFormat.format(this.gregorianCalendar.getTime());
    }

    /**
     * Returns the combined year and month units of the date as a string formatted according to the standard EDITDate format
     * @return
     */
    public final String getFormattedYearAndMonth()
    {
        return this.getFormattedYear() + EDITDate.DATE_DELIMITER + this.getFormattedMonth();
    }

    /**
     * Returns the combined month and day units of the date as a string formatted according to the standard EDITDate format
     * @return
     */
    public final String getFormattedMonthAndDay()
    {
        return this.getFormattedMonth() + EDITDate.DATE_DELIMITER + this.getFormattedDay();
    }

    /**
     * Returns the year unit of the date as a string containing a 2 character year (i.e. 06 instead of 2006)
     * @return
     */
    public final String getYearAsYY()
    {
        SimpleDateFormat yyYearFormat = new SimpleDateFormat(EDITDate.YY_YEAR_FORMAT);
        
        return yyYearFormat.format(this.gregorianCalendar.getTime());
    }

    /**
     * Returns the full name of the month in mixed case for this date
     *
     * @return  full name of the month
     */
    public final String getMonthName()
    {
        return MONTH_NAMES[this.getMonth()-1];
    }

    /**
     * Returns the full name of the month in mixed case for the specified month
     *
     * @param monthString       string containing the number of the month (ex. "07" or "7" for July)
     *
     * @return  full name of the month
     */
    public static final String getMonthName(String monthString)
    {
        int month = Integer.parseInt(monthString);

        return MONTH_NAMES[month-1];
    }

    /**
     * Returns the numeric value of the day within the year
     *
     * @return  integer value of the day of the year
     */
    public final int getDayOfYear()
    {
        return this.gregorianCalendar.get(Calendar.DAY_OF_YEAR);
    }

    /**
     * Returns the day of the week for the current date
     *
     * @return  full name of the day of the week in mixed case
     */
    public String getDayOfWeek()
    {
        String dayOfWeekAsString = null;

        int dayOfWeek = this.gregorianCalendar.get(Calendar.DAY_OF_WEEK);

        switch(dayOfWeek)
        {
            case Calendar.SUNDAY:
                dayOfWeekAsString = EDITDate.SUNDAY;
                break;
            case Calendar.MONDAY:
                dayOfWeekAsString = EDITDate.MONDAY;
                break;
            case Calendar.TUESDAY:
                dayOfWeekAsString = EDITDate.TUESDAY;
                break;
            case Calendar.WEDNESDAY:
                dayOfWeekAsString = EDITDate.WEDNESDAY;
                break;
            case Calendar.THURSDAY:
                dayOfWeekAsString = EDITDate.THURSDAY;
                break;
            case Calendar.FRIDAY:
                dayOfWeekAsString = EDITDate.FRIDAY;
                break;
            case Calendar.SATURDAY:
                dayOfWeekAsString = EDITDate.SATURDAY;
                break;
        }

        return dayOfWeekAsString;
    }

    /**
     * Returns the number of days in this date's month
     *
     * @return  number of days in the month
     */
    public final int getNumberOfDaysInMonth()
    {
        return (this.gregorianCalendar.getActualMaximum(Calendar.DAY_OF_MONTH));
    }

    /**
     * Determines if this date's year is a leap year
     *
     * @return  true if it is a leap year, false otherwise
     */
    public final boolean isLeapYear()
    {
        return this.gregorianCalendar.isLeapYear(this.gregorianCalendar.get(Calendar.YEAR));
    }

    /**
     * Determines if this date falls on a leap day or not
     *
     * @return  true if is a leap day, false otherwise
     */
    public final boolean isLeapDay()
    {
        boolean isLeapDay = false;

        if (this.isLeapYear())
        {
            if (this.getMonth() == 2 && this.getDay() == 29)
            {
                isLeapDay = true;
            }
        }

        return isLeapDay;
    }


    /**
     * Determines if this date is a valid date or not.  Does this by trying to access information from the
     * GregorianCalendar (because GregorianCalendar instantiates ok but doesn't throw an exception until it is
     * accessed).
     *
     * @return  true if this date is valid, false otherwise
     */
    public final boolean isAValidDate()
    {
        boolean isAValidDate = true;

        try
        {
            this.getYear();
        }
        catch (Exception e)
        {
            isAValidDate = false;
        }

        return isAValidDate;
    }

    /**
     * Determines if supplied string is a valid date.  Parsing the date based on the date format includes checking
     * for valid date entries.
     * <P>
     * NOTE: this is an expensive function to use.  Check isACandidateDate and the instance isAValidDate to be sure you
     * are using the right function.
     *
     * @param   formattedDateString         string containing a fully formatted date
     *
     * @return  true if string is a valid date, false otherwise
     */
    public static final boolean isAValidDate(String formattedDateString)
    {
        boolean isAValidDate = true;

        SimpleDateFormat dateFormat = new SimpleDateFormat(EDITDate.DATE_FORMAT);
        
        dateFormat.setLenient(false);

        try
        {
            Date dateObject = dateFormat.parse(formattedDateString);
        }
        catch (ParseException e)
        {
            isAValidDate = false;
        }

        return isAValidDate;
    }

    /**
     * Checks to see if the string is in the same format as a date.  DOES NOT check for a valid date.  It only checks
     * that the string may be a date based on the format.
     *
     * @param formattedDateString       string to compare to the date format
     *
     * @return  true if the formattedDateString is formatted as a date, false otherwise
     */
    public static final boolean isACandidateDate(String formattedDateString)
    {
        boolean isADate = false;

        Matcher m = REG_EXPR_YYYYMMDD_DATE_FORMAT.matcher(formattedDateString);

        isADate = m.matches();

        return isADate;
    }

    /**
     * Determines if the date is at the end of the month or not
     *
     * @return  true if this date's day component is at the end of the month, false otherwise
     */
    public boolean isAtEndOfMonth()
    {
        boolean isAtEndOfMonth = false;

        if (this.getDay() == this.getNumberOfDaysInMonth())
        {
            isAtEndOfMonth = true;
        }

        return isAtEndOfMonth;
    }


    /**
     * Adds the specified number of days to the current date and returns a new date.
     *
     * @param numberOfDays      number of days to be added
     *
     * @return  new EDITDate containing the date with the specified number of days added to this object's date
     */
    public final EDITDate addDays(int numberOfDays)
    {
        EDITDate copy = this.copy();

        copy.gregorianCalendar.add(Calendar.DAY_OF_YEAR, numberOfDays);

        return copy;
    }

    /**
     * Adds the specified number of months to the current date and returns a new date.
     *
     * @param numberOfMonths      number of months to be added
     *
     * @return  new EDITDate containing the date with the specified number of months added to this object's date
     */
    public final EDITDate addMonths(int numberOfMonths)
    {
        EDITDate copy = this.copy();

        copy.gregorianCalendar.add(Calendar.MONTH, numberOfMonths);

        return copy;
    }

    /**
     * Adds the specified number of years to the current date and returns a new date.
     *
     * @param numberOfYears      number of years to be added
     *
     * @return  new EDITDate containing the date with the specified number of years added to this object's date
     */
    public final EDITDate addYears(int numberOfYears)
    {
        EDITDate copy = this.copy();

        copy.gregorianCalendar.add(Calendar.YEAR, numberOfYears);

        return copy;
    }

    /**
     * Subtracts the specified number of days from the current date and returns a new date.
     *
     * @param numberOfDays      number of days to be subtracted
     *
     * @return  new EDITDate containing the date with the specified number of days subtracted from this object's date
     */
    public final EDITDate subtractDays(int numberOfDays)
    {
        return this.addDays(-numberOfDays);
    }

    /**
     * Subtracts the specified number of months from the current date and returns a new date.
     *
     * @param numberOfMonths      number of months to be subtracted
     *
     * @return  new EDITDate containing the date with the specified number of months subtracted from this object's date
     */
    public final EDITDate subtractMonths(int numberOfMonths)
    {
        return this.addMonths(-numberOfMonths);
    }

    /**
     * Subtracts the specified number of years from the current date and returns a new date.
     *
     * @param numberOfYears      number of years to be subtracted
     *
     * @return  new EDITDate containing the date with the specified number of years subtracted from this object's date
     */
    public final EDITDate subtractYears(int numberOfYears)
    {
        return this.addYears(-numberOfYears);
    }

//    /**
//     * Returns a String representation of the date
//     *<p>
//     * @return String containing the selected/entered date in
//     *         "MM/DD/YYYY" format
//     */
//    public String toString() {
//
//        StringBuffer sb = new StringBuffer();
//        sb.append(String.valueOf(getMonth()));
//        sb.append("/");
//        sb.append(String.valueOf(getDay()));
//        sb.append("/");
//        sb.append(String.valueOf(getYear()));
//        return sb.toString();
//    }

    /**
     * Returns the current quarter for this date
     *
     * @return  quarter number
     */
    public final int getQuarter()
    {
        int month = this.getMonth();

        if (month >= EDITDate.FIRST_QUARTER_START_MONTH && month <= EDITDate.FIRST_QUARTER_END_MONTH)
        {
            return EDITDate.FIRST_QUARTER;
        }
        else if (month >= EDITDate.SECOND_QUARTER_START_MONTH && month <= EDITDate.SECOND_QUARTER_END_MONTH)
        {
            return EDITDate.SECOND_QUARTER;
        }
        else if (month >= EDITDate.THIRD_QUARTER_START_MONTH && month <= EDITDate.THIRD_QUARTER_END_MONTH)
        {
            return EDITDate.THIRD_QUARTER;
        }
        else if (month >= EDITDate.FOURTH_QUARTER_START_MONTH && month <= EDITDate.FOURTH_QUARTER_END_MONTH)
        {
            return EDITDate.FOURTH_QUARTER;
        }
        else
        {
            return 0;
        }
    }

    /**
     * Returns the beginning month of the this date's quarter
     *
     * @return  month of the start of the quarter
     */
    public final int getStartOfQuarterMonth()
    {
        int startOfQuarterMonth = 0;

        int quarter = this.getQuarter();

        if (quarter == EDITDate.FIRST_QUARTER)
        {
            startOfQuarterMonth = EDITDate.FIRST_QUARTER_START_MONTH;
        }
        else if (quarter == EDITDate.SECOND_QUARTER)
        {
            startOfQuarterMonth = EDITDate.SECOND_QUARTER_START_MONTH;
        }
        else if (quarter == EDITDate.THIRD_QUARTER)
        {
            startOfQuarterMonth = EDITDate.THIRD_QUARTER_START_MONTH;
        }
        else if (quarter == EDITDate.FOURTH_QUARTER)
        {
            startOfQuarterMonth = EDITDate.FOURTH_QUARTER_START_MONTH;
        }

        return startOfQuarterMonth;
    }

    /**
     * Returns the ending month of this date's quarter
     *
     * @return  month of the end of the quarter
     */
    public final int getEndOfQuarterMonth()
    {
        int endOfQuarterMonth = 0;

        int quarter = this.getQuarter();

        if (quarter == EDITDate.FIRST_QUARTER)
        {
            endOfQuarterMonth = EDITDate.FIRST_QUARTER_END_MONTH;
        }
        else if (quarter == EDITDate.SECOND_QUARTER)
        {
            endOfQuarterMonth = EDITDate.SECOND_QUARTER_END_MONTH;
        }
        else if (quarter == EDITDate.THIRD_QUARTER)
        {
            endOfQuarterMonth = EDITDate.THIRD_QUARTER_END_MONTH;
        }
        else if (quarter == EDITDate.FOURTH_QUARTER)
        {
            endOfQuarterMonth = EDITDate.FOURTH_QUARTER_END_MONTH;
        }

        return endOfQuarterMonth;
    }

    /**
     * Returns the end date for the current date's quarter
     *
     * @return  date at end of quarter
     */
    public final EDITDate getEndOfQuarterDate()
    {
        int endOfQuarterMonth = this.getMonth();
        int endOfQuarterDay = this.getDay();

        int quarter = this.getQuarter();

        if (quarter == EDITDate.FIRST_QUARTER)
        {
            endOfQuarterMonth = EDITDate.FIRST_QUARTER_END_MONTH;
            endOfQuarterDay = EDITDate.FIRST_QUARTER_END_DAY;
        }
        else if (quarter == EDITDate.SECOND_QUARTER)
        {
            endOfQuarterMonth = EDITDate.SECOND_QUARTER_END_MONTH;
            endOfQuarterDay = EDITDate.SECOND_QUARTER_END_DAY;
        }
        else if (quarter == EDITDate.THIRD_QUARTER)
        {
            endOfQuarterMonth = EDITDate.THIRD_QUARTER_END_MONTH;
            endOfQuarterDay = EDITDate.THIRD_QUARTER_END_DAY;
        }
        else if (quarter == EDITDate.FOURTH_QUARTER)
        {
            endOfQuarterMonth = EDITDate.FOURTH_QUARTER_END_MONTH;
            endOfQuarterDay = EDITDate.FOURTH_QUARTER_END_DAY;
        }

        return new EDITDate(this.getYear(), endOfQuarterMonth, endOfQuarterDay);
    }

    /**
     * Returns the end date for the current date's month
     *
     * @return  date at end of the month
     */
    public final EDITDate getEndOfMonthDate()
    {
        return new EDITDate(this.getYear(), this.getMonth(), this.getNumberOfDaysInMonth());
    }

    /**
     * Returns the last date of the current year
     *
     * @return  date at end of the year
     */
    public final EDITDate getEndOfYearDate()
    {
        return new EDITDate(this.getYear(), EDITDate.FOURTH_QUARTER_END_MONTH, EDITDate.FOURTH_QUARTER_END_DAY);
    }

    /**
     * Returns the first date of the current year
     *
     * @return  date at the start of the year
     */
    public final EDITDate getStartOfYearDate()
    {
        return new EDITDate(this.getYear(), EDITDate.FIRST_QUARTER_START_MONTH, EDITDate.FIRST_QUARTER_START_DAY);
    }

    /**
     * Returns the last day of the current half of the year (either first half or second half)
     *
     * @return  date at end of the half of the year
     */
    public final EDITDate getEndOfSemiAnnualDate()
    {
        int quarter = this.getQuarter();

        if (quarter <= 2)
        {
            return new EDITDate(this.getYear(), EDITDate.SECOND_QUARTER_END_MONTH, EDITDate.SECOND_QUARTER_END_DAY);
        }
        else
        {
            return new EDITDate(this.getYear(), EDITDate.FOURTH_QUARTER_END_MONTH, EDITDate.FOURTH_QUARTER_END_DAY);
        }
    }

    public EDITDate getEndOfModeDate(String mode)
    {
        EDITDate endOfModeDate = null;

        if (mode.equalsIgnoreCase(EDITDate.MONTHLY_MODE))
        {
            endOfModeDate = this.getEndOfMonthDate();
        }
        else if (mode.equalsIgnoreCase(EDITDate.QUARTERLY_MODE))
        {
            endOfModeDate = this.getEndOfQuarterDate();
        }
        else if (mode.equalsIgnoreCase(EDITDate.SEMI_ANNUAL_MODE))
        {
            endOfModeDate = this.getEndOfSemiAnnualDate();
        }
        else if (mode.equalsIgnoreCase(EDITDate.ANNUAL_MODE))
        {
            endOfModeDate = this.getEndOfYearDate();
        }

        return endOfModeDate;
    }

    public final EDITDate addMode(String mode)
    {
        EDITDate addedModeDate = null;

        if (mode.equalsIgnoreCase(EDITDate.DAILY_MODE))
        {
            addedModeDate = this.addDays(1);
        }
        else if (mode.equalsIgnoreCase(EDITDate.WEEKLY_MODE))
        {
            addedModeDate = this.addDays(7);
        }
        else if (mode.equalsIgnoreCase(EDITDate.BIWEEKLY_MODE))
        {
            addedModeDate = this.addDays(14);
        }
        else if (mode.equalsIgnoreCase(EDITDate.MONTHLY_MODE))
        {
            addedModeDate = this.addMonths(1);
        }
        else if (mode.equalsIgnoreCase(EDITDate.BIMONTHLY_MODE))
        {
            addedModeDate = this.addMonths(2);
        }
        else if (mode.equalsIgnoreCase(EDITDate.QUARTERLY_MODE))
        {
            addedModeDate = this.addMonths(3);
        }
        else if (mode.equalsIgnoreCase(EDITDate.SEMI_ANNUAL_MODE))
        {
            addedModeDate = this.addMonths(6);
        }
        else if (mode.equalsIgnoreCase(EDITDate.ANNUAL_MODE))
        {
            addedModeDate = this.addYears(1);
        }
        else if (mode.equalsIgnoreCase(EDITDate.THIRTEENTHLY_MODE))
        {
            addedModeDate = this.addDays(28);
        }
        else if (mode.equalsIgnoreCase(EDITDate.SEMI_MONTHLY_MODE))
        {
            EDITDate endOfMonthDate = this.getEndOfMonthDate();
            int day = this.getDay();
            if (this.equals(endOfMonthDate))
            {
                addedModeDate = this.addMonths(1);
                day = 15;
                addedModeDate = new EDITDate(addedModeDate.getYear(), addedModeDate.getMonth(), day);
            }
            else
            {
                if (day > 15 || day == 15)
                {
                    addedModeDate = this.getEndOfMonthDate();
                }
                else
                {
                    day = 15;

                    addedModeDate = new EDITDate(this.getYear(), this.getMonth(), day);
                }
            }

            try
            {
                BusinessCalendar bc = new BusinessCalendar();
                BusinessDay bd = bc.getBestBusinessDay(addedModeDate);
                addedModeDate = bd.getBusinessDate();
            }
            catch(Exception e)
            {
                System.out.println(e);
                
                e.printStackTrace();
            }
        }

        return addedModeDate;
    }

    public final EDITDate subtractMode(String mode)
    {
        EDITDate subtractedModeDate = null;

        if (mode.equalsIgnoreCase(EDITDate.DAILY_MODE))
        {
            subtractedModeDate = this.subtractDays(1);
        }
        else if (mode.equalsIgnoreCase(EDITDate.WEEKLY_MODE))
        {
            subtractedModeDate = this.subtractDays(7);
        }
        else if (mode.equalsIgnoreCase(EDITDate.BIWEEKLY_MODE))
        {
            subtractedModeDate = this.subtractDays(14);
        }
        else if (mode.equalsIgnoreCase(EDITDate.MONTHLY_MODE))
        {
            subtractedModeDate = this.subtractMonths(1);
        }
        else if (mode.equalsIgnoreCase(EDITDate.BIMONTHLY_MODE))
        {
            subtractedModeDate = this.subtractMonths(2);
        }
        else if (mode.equalsIgnoreCase(EDITDate.QUARTERLY_MODE))
        {
            subtractedModeDate = this.subtractMonths(3);
        }
        else if (mode.equalsIgnoreCase(EDITDate.SEMI_ANNUAL_MODE))
        {
            subtractedModeDate = this.subtractMonths(6);
        }
        else if (mode.equalsIgnoreCase(EDITDate.ANNUAL_MODE))
        {
            subtractedModeDate = this.subtractYears(1);
        }

        return subtractedModeDate;
    }

    /**
     * Returns the number of milliseconds for this date
     *
     * @return  number of milliseconds
     */
    public long getTimeInMilliseconds()
    {
        return this.gregorianCalendar.getTimeInMillis();
    }

    /**
     * Calculates the number of days between 2 dates.
     * <P>
     * This method was obtained from JavaWorld and modified for class specifics.  To calculate a time unit of change
     * (in this case, days) between 2 dates:
     * <P>
     * <UL> Make copies of the two dates.</UL>
     * <BR> Using the copies of the dates, set all fields that are smaller than the unit of change to each field's
     * minimum value.  Use the clear() method to set time fields to their lowest value.
     * <BR> Take the earlier date and add one to the field you are counting, repeating until the two dates are equal.
     * The number of times you add one is the answer.
     *
     * @param editDate      date from which to find elapsed months with the current object
     *
     * @return number of days between 2 dates
     */
    public final int getElapsedDays(EDITDate editDate)
    {
        DateTime dt1 = new DateTime(this.gregorianCalendar.getTimeInMillis());
        DateTime dt2 = new DateTime(editDate.gregorianCalendar.getTimeInMillis());
        
        Days d = Days.daysBetween(dt1, dt2);
        
        int days = Math.abs(d.getDays());        

        return days;
    }

    /**
     * Calculates the number of months between 2 dates.  If there is a partial month difference, the number is rounded down.
     * <P>
     * For example, the difference in months between 7/15/2006 and 10/25/2006 is 3 months, not 4 months.  It is "rounded
     * down" to a complete month and does not include the partial month (since the 25th comes after the 15th).
     *
     * @param editDate      date from which to find elapsed months with the current object
     *
     * @return number of months between 2 dates
     */
    public final int getElapsedMonths(EDITDate editDate)
    {
        int elapsed = this.elapsedMonths(editDate);

        //  If the days of the month are not the same, a partial month was added to the elapsed
        //  value, must reduce it by 1.
        if (this.getDay() != editDate.getDay())
        {
            elapsed--;
        }

        return elapsed;
    }

    /**
     * Calculates the number of full years between 2 dates.
     * <P>
     * For example, the difference in years between 7/15/2004 and 10/25/2006 is 1 full year, not 2 years.  It is "rounded
     * down" to a complete year and does not include the partial year (since 10/25 comes after 7/15).
     *
     * @param editDate      date from which to find elapsed years with the current object
     *
     * @return number of years between 2 dates
     */
    public final int getElapsedFullYears(EDITDate editDate)
    {
        int elapsed = 0;
        
        int elapsedMonths = getElapsedMonths(editDate);
        elapsed = elapsedMonths/12;

    	return elapsed;
    }
    
    /**
     * Calculates the number of months between 2 dates.  If there is a partial month difference, the number is rounded up.
     * <P>
     * For example, the difference in months between 7/15/2006 and 10/25/2006 is 4 months.  It is "rounded
     * up" to a complete month which includes the partial month (since the 25th comes after the 15th).
     * <P>
     * The ordering of the specified dates is also taken into consideration. If this object's date is younger than
     * the specified date, the result will be negative, otherwise, it will be positive.
     * <P>
     * For example:
     * this object's date = 2004/01/31, specified date = 2004/03/01; 2004/01/31 - 2004/03/01 = 2.
     * this object's date = 2004/03/01, specified date = 2004/01/31; 2004/03/01 - 2004/01/31 = -2.
     *
     * @param editDate      date from which to find elapsed months with the current object
     *
     * @return number of months between 2 dates
     */
    public final int getElapsedMonthsRoundedUp(EDITDate editDate)
    {
        int elapsed = this.elapsedMonths(editDate);

        if (this.after(editDate))
        {
            elapsed = -1 * elapsed;
        }

        return elapsed;
    }

    /**
     * Determine the age at the last birthday using this date as the date of birth.  Calculates the age at today's date.
     *
     * @return  age at the last birthday
     */
     public final int getAgeAtLastBirthday()
    {
        int age = getAge();

        EDITDate thisYearsBirthDate = getThisYearsBirthDate();

        // If this year's birthday has not happened yet, subtract one from age
        if (this.before(thisYearsBirthDate))
        {
            age--;
        }

        return age;
    }

    /**
     * Determine the age at the last birthday using this date as the date of birth.  Calculates the age at the specified
     * date.
     *
     * @return  age at the last birthday
     */
    public final int getAgeAtLastBirthday(EDITDate dateToCalculateAgeFor)
    {
        int age = getAge(dateToCalculateAgeFor);

        EDITDate thisYearsBirthDate = getThisYearsBirthDate(dateToCalculateAgeFor);

        // If this year's birthday has not happened yet, subtract one from age
        if (dateToCalculateAgeFor.before(thisYearsBirthDate))
        {
            age--;
        }

        return age;
    }

    /**
     * Determine the age at the next birthday using this date as the date of birth and today's date as the date to
     * calculate for.
     *
     * @return  age at the next birthday
     */
    public final int getAgeAtNextBirthday()
    {
        int age = getAge();

        EDITDate thisYearsBirthDate = getThisYearsBirthDate();

        // If this year's birthday has already happened, add one to the age for the next birthday
        if (this.after(thisYearsBirthDate))
        {
            age++;
        }

        return age;
    }

    /**
     * Determine the age at the next birthday using this date as the date of birth and the specified date as the date
     * to calculate for
     *
     * @return  age at the next birthday
     */
    public final int getAgeAtNextBirthday(EDITDate dateToCalculateFor)
    {
        int age = getAge(dateToCalculateFor);

        EDITDate thisYearsBirthDate = getThisYearsBirthDate(dateToCalculateFor);

        // If this year's birthday has already happened, add one to the age for the next birthday
        if (dateToCalculateFor.after(thisYearsBirthDate))
        {
            age++;
        }

        return age;
    }

    /**
     * Determines the age at the nearest birthday using this date as the date of birth and today's date as the date
     * to calculate for.
     * <P>
     * The nearest birthday is determined by comparing the number of days between the last birthday and today and the
     * number of days between the next birthday and today.  Whichever is lower, that's the nearest birthday.
     *
     * @return  age at the nearest birthday for today's date
     */
    public final int getAgeAtNearestBirthday()
    {
        EDITDate thisYearsBirthDate = getThisYearsBirthDate();

        EDITDate lastBirthDate = null;
        EDITDate nextBirthDate = null;

        EDITDate today = new EDITDate();

        if (today.before(thisYearsBirthDate))
        {
            //  Birthday hasn't happened yet - next birthday = this year's, last = last year's
            lastBirthDate = thisYearsBirthDate.subtractYears(1);
            nextBirthDate = new EDITDate(thisYearsBirthDate);
        }
        else
        {
            //  Birthday already past - last birthday = this year's, next = next year's
            nextBirthDate = thisYearsBirthDate.addYears(1);
            lastBirthDate = new EDITDate(thisYearsBirthDate);
        }

        long numberOfDaysSinceLastBirthday = today.getElapsedDays(lastBirthDate);
        long numberOfDaysTillNextBirthday = today.getElapsedDays(nextBirthDate);

        if (numberOfDaysTillNextBirthday < numberOfDaysSinceLastBirthday)
        {
            //  Return age at next birthday
            return getAgeAtBirthday(nextBirthDate);
        }
        else
        {
            //  Return age at last birthday
            return getAgeAtBirthday(lastBirthDate);
        }
    }

    /**
     * Determines the age at the nearest birthday using this date as the date of birth and the specified date as the
     * date to calculate for.
     * <P>
     * The nearest birthday is determined by comparing the number of days between the last birthday and the specified
     * date and the number of days between the next birthday and specified date.  Whichever is lower, that's the
     * nearest birthday.
     *
     * @return  age at the nearest birthday for the specified date
     */
    public final int getAgeAtNearestBirthday(EDITDate dateToCalculateFor)
    {
        EDITDate thisYearsBirthDate = getThisYearsBirthDate(dateToCalculateFor);

        EDITDate lastBirthDate = null;
        EDITDate nextBirthDate = null;

        if (dateToCalculateFor.before(thisYearsBirthDate))
        {
            //  Birthday hasn't happened yet - next birthday = this year's, last = last year's
            lastBirthDate = thisYearsBirthDate.subtractYears(1);
            nextBirthDate = new EDITDate(thisYearsBirthDate);
        }
        else
        {
            //  Birthday already past - last birthday = this year's, next = next year's
            nextBirthDate = thisYearsBirthDate.addYears(1);
            lastBirthDate = new EDITDate(thisYearsBirthDate);
        }

        long numberOfDaysSinceLastBirthday = dateToCalculateFor.getElapsedDays(lastBirthDate);
        long numberOfDaysTillNextBirthday = dateToCalculateFor.getElapsedDays(nextBirthDate);

        if (numberOfDaysTillNextBirthday < numberOfDaysSinceLastBirthday)
        {
            //  Return age at next birthday
            return getAgeAtBirthday(nextBirthDate);
        }
        else
        {
            //  Return age at last birthday
            return getAgeAtBirthday(lastBirthDate);
        }
    }

    /**
     * Determines the date 70 and 1/2 years from "this" date.  Should not fall on a leap day
     *
     * @return
     */
    public final EDITDate getSeventyHalfDate()
    {
        EDITDate dateAtSeventyHalf = this;

        dateAtSeventyHalf = dateAtSeventyHalf.addYears(70);
        dateAtSeventyHalf = dateAtSeventyHalf.addMonths(6);

        //  Don't let it fall on a leap day, subtract one day
        if (dateAtSeventyHalf.isLeapDay())
        {
            dateAtSeventyHalf = dateAtSeventyHalf.subtractDays(1);
        }

        return dateAtSeventyHalf;
    }


    //  PRIVATE METHODS

    /**
     * Calculate the age from the this object's date to today.
     *
     * @return  age at today
     */
    private final int getAge()
    {
        EDITDate today = new EDITDate();

        int age = today.gregorianCalendar.get(Calendar.YEAR) - this.gregorianCalendar.get(Calendar.YEAR);

        return age;
    }

    /**
     * Calculate the age at the specified date using this object's date as the date of birth.
     *
     * @return  age at the specified date
     */
    private final int getAge(EDITDate dateToCalculateAgeFor)
    {
        int age = dateToCalculateAgeFor.gregorianCalendar.get(Calendar.YEAR) - this.gregorianCalendar.get(Calendar.YEAR);

        return age;
    }

    /**
     * Determines this year's birth date based on the this object's date (the date of birth) and the current age.
     * <P>
     * Example: If the birthdate is 12/5/1961 and today's date is 10/15/2006, "this year's" birth date will be
     * 12/5/2006.
     *
     * @return  the EDITDate for this year's birthday
     */
    private final EDITDate getThisYearsBirthDate()
    {
        int age = this.getAge();

        EDITDate thisYearsBirthDate = this.addYears(age);

        return thisYearsBirthDate;
    }

    /**
     * Determines this year's birth date for the specified date using this object's date (the date of birth) and the
     * age at the specified date.
     * <P>
     * Example: If the birthdate is 12/5/1961 and the specified date is 10/15/2005, "this year's" birth date will be
     * 12/5/2005.
     *
     * @return  the EDITDate for this year's birthday at the specifed date
     */
    private final EDITDate getThisYearsBirthDate(EDITDate dateToCalculateAgeFor)
    {
        int age = this.getAge(dateToCalculateAgeFor);

        EDITDate thisYearsBirthDate = this.addYears(age);

        return thisYearsBirthDate;
    }

    /**
     * Determines the age at a given birthday.  Basically, just determines the number of years between this date
     * and the birthday date.
     *
     * @param birthdayDate      birthday date at which to calculate the age.
     *
     * @return  age at the specified birthday
     */
    private final int getAgeAtBirthday(EDITDate birthdayDate)
    {
        int age = birthdayDate.gregorianCalendar.get(Calendar.YEAR) - this.gregorianCalendar.get(Calendar.YEAR);

        return age;
    }

    /*
     * Calculates the number of months between 2 dates.  Partial months (remainder days) are considered a whole month.
     * <P>
     * This method was obtained from JavaWorld and modified for class specifics.  To calculate a time unit of change
     * (in this case, months) between 2 dates:
     * <P>
     * <UL> Make copies of the two dates.
     * <BR> Using the copies of the dates, set all fields that are smaller than the unit of change to each field's
     * minimum value.  Use the clear() method to set time fields to their lowest value.
     * <BR> Take the earlier date and add one to the field you are counting, repeating until the two dates are equal.
     * The number of times you add one is the answer.
     *
     * @param editDate      date from which to find elapsed months with the current object
     *
     * @return number of months between 2 dates
     */
    private int elapsedMonths(EDITDate editDate)
    {
        int elapsed = 0;

        EDITDate gc1, gc2;

        if (this.after(editDate))
        {
            gc2 = this.copy();
            gc1 = editDate.copy();
        }
        else
        {
            gc2 = editDate.copy();
            gc1 = this.copy();
        }

        gc1.gregorianCalendar.clear(Calendar.MILLISECOND);
        gc1.gregorianCalendar.clear(Calendar.SECOND);
        gc1.gregorianCalendar.clear(Calendar.MINUTE);
        gc1.gregorianCalendar.clear(Calendar.HOUR_OF_DAY);
        gc1.gregorianCalendar.clear(Calendar.DATE);

        gc2.gregorianCalendar.clear(Calendar.MILLISECOND);
        gc2.gregorianCalendar.clear(Calendar.SECOND);
        gc2.gregorianCalendar.clear(Calendar.MINUTE);
        gc2.gregorianCalendar.clear(Calendar.HOUR_OF_DAY);
        gc2.gregorianCalendar.clear(Calendar.DATE);

        //  Add 1 month to gc1 until it is equal to or after gc2
        while (gc1.before(gc2))
        {
             gc1.gregorianCalendar.add(Calendar.MONTH, 1);
             elapsed++;
        }

        return elapsed;
    }
    
    /**
     *  Returns the next specified dayOfWeek for this EDITDate.
     *  
     *  For example, if this EDITDate is Thursday January 14th, and the
     *  specfied dayOfWeek is Tuesday, then the desired EDITDate would
     *  be Tuesday January 19th.
     *  
     *  If the specified dayOfWeek is Friday, while this EDITDate is
     *  also a Friday, expect the returned EDITDate to be + 7 days from 
     *  this EDITDate.
     * @param dayOfWeek as determined by EDITDate.MONDAY, EDITDate.Tuesday, etc.
     * @return the EDITDate that represents the "next" specified day of week
     */
    public EDITDate getNextDayOfWeek(String dayOfWeek)
    {
        EDITDate nextDate = this;
    
        int DAYS_IN_WEEK = 7;
        
        for (int i = 0; i < DAYS_IN_WEEK; i++)
        {
            nextDate = nextDate.addDays(1);
            
            String nextDayOfWeek = nextDate.getDayOfWeek();
            
            if (nextDayOfWeek.equals(dayOfWeek))
            {
                break;
            }
        }
        
        return nextDate;
    }

    /**
     * Finds the first specified weekday of the specified year/month.
     * 
     * e.g.
     * 
     * If the year is 2008, the month is 10 (Oct), and the weekday is Tuesday, then
     * the resulting date would be 2008/10/07 (the first Tuesday of the month).
     * @param year
     * @param month
     * @param dayOfWeek MONDAY, TUESDAY, etc as defined by EDITDate.MONDAY, EDITDate.TUESDAY, etc.
     * @return
     */
    public static EDITDate getFirstWeekdayOfMonth(int year, int month, String weekday)
    {
        // The existing method we are about to use is not inclusive of the specified weekday, so
        // we will start minus 1 day.
        EDITDate seedDate = new EDITDate(year, month, 1).subtractDays(1);
        
        EDITDate firstWeekdayofMonth = seedDate.getNextDayOfWeek(weekday);
        
        return firstWeekdayofMonth;
    }

    /**
     * Determines the next monthly anniversary date from this object's date and after the given startingDate.
     * <P>
     * The monthly anniversary date is the same day each month from this object's date.  For example, if the date
     * is 2006/08/15, the 15th of every month is the monthly anniversary date.
     * <P>
     * The "next" monthly anniversary is the normal monthly anniversary AFTER the given starting date.  For example,
     * if the date is 2006/08/15 and the starting date is 2009/02/25, the next monthly anniversary is 2009/03/15.  If
     * the starting date was 2009/02/01, the next monthiversary would be 2009/02/15.
     * <P>
     * Things get complicated when this object's date is at the end of the month because some months may not have that
     * day.  For example, if the date is 2006/08/31 and the startingDate is 2009/02/14, the monthiversary can't be
     * 2009/02/31.  Must determine the appropriate day in this case.
     *
     * @param startingDate              starting date to determine the monthiversary (the monthiversary must be after
     *                                  or equal to the startingDate)
     *
     * @return  monthly anniversary for this object's date that comes AFTER the startingDate
     */
    public EDITDate getNextMonthiversaryDate(EDITDate startingDate)
    {
        EDITDate monthiversary = null;

        //  First, create a date as a starting point.  Use startingDate's year and month.  The day is more difficult
        //  since it may not be a valid day in startingDate's month.  If it is not valid, use the startingDate's end of
        //  month date
        if (EDITDate.isAValidDate(startingDate.getFormattedYearAndMonth() + EDITDate.DATE_DELIMITER + this.getFormattedDay()))
        {
            monthiversary = new EDITDate(startingDate.getYear(), startingDate.getMonth(), this.getDay());
        }
        else
        {
            monthiversary = startingDate.getEndOfMonthDate();
        }

        //  If the monthiversary is before or equal to the startingDate, go to the next month
        while (monthiversary.beforeOREqual(startingDate))
        {
            monthiversary = monthiversary.addMonths(1);
        }

        //  If the startingDate is at the end of the month, we may need to manipulate the day again because a month
        //  was added and "this" date's day may be valid for this new month.
        if (startingDate.equals(startingDate.getEndOfMonthDate()))
        {
            if (monthiversary.getDay() < this.getDay())
            {
                if (EDITDate.isAValidDate(monthiversary.getFormattedYearAndMonth() + EDITDate.DATE_DELIMITER + this.getFormattedDay()))
                {
                    monthiversary = new EDITDate(monthiversary.getYear(), monthiversary.getMonth(), this.getDay());
                }
            }
        }

        return monthiversary;
    }

    /**
     * Determines the prior monthly anniversary date from this date and before the given startingDate
     * <P>
     * The monthly anniversary date is the same day each month from this object's date.  For example, if the date
     * is 2006/08/15, the 15th of every month is the monthly anniversary date.
     * <P>
     * The "prior" monthly anniversary is the normal monthly anniversary BEFORE the given starting date.  For example,
     * if the date is 2006/08/15 and the starting date is 2009/02/25, the prior monthly anniversary is 2009/02/15.  If
     * the starting date was 2009/02/01, the prior monthiversary would be 2009/01/15.
     * <P>
     * Things get complicated when this object's date is at the end of the month because some months may not have that
     * day.  For example, if the date is 2006/08/31 and the startingDate is 2009/03/14, the monthiversary can't be
     * 2009/02/31.  Must determine the appropriate day in this case.
     *
     * @param startingDate              starting date to determine the monthiversary (the monthiversary must be before
     *                                  the startingDate)
     *
     * @return  monthly anniversary for this object's date that comes BEFORE the startingDate
     */
    public EDITDate getPriorMonthiversaryDate(EDITDate startingDate)
    {
        EDITDate monthiversary = null;

        boolean usedEndOfMonth = false;     // keep track of whether the end of the month was used as a starting point for the monthiversary

        //  First, create a date as a starting point.  Use startingDate's year and month.  The day is more difficult
        //  since it may not be a valid day in startingDate's month.  If it is not valid, use the startingDate's end of
        //  month date
        if (EDITDate.isAValidDate(startingDate.getFormattedYearAndMonth() + EDITDate.DATE_DELIMITER + this.getFormattedDay()))
        {
            monthiversary = new EDITDate(startingDate.getYear(), startingDate.getMonth(), this.getDay());
        }
        else
        {
            monthiversary = startingDate.getEndOfMonthDate();

            usedEndOfMonth = true;
        }

        //  If the monthiversary is after to the startingDate, go to the previous month. 
        while (monthiversary.after(startingDate))
        {
            monthiversary = monthiversary.subtractMonths(1);
        }

        //  If the the end of the month was used in the original creation of the monthiversary, we may need to
        //  manipulate the day again because a month was subtracted and "this" date's day may be valid for this new month.
        if (usedEndOfMonth)
        {
            if (monthiversary.getDay() < this.getDay())
            {
                if (EDITDate.isAValidDate(monthiversary.getFormattedYearAndMonth() + EDITDate.DATE_DELIMITER + this.getFormattedDay()))
                {
                    monthiversary = new EDITDate(monthiversary.getYear(), monthiversary.getMonth(), this.getDay());
                }
            }
        }

        return monthiversary;
    }
    
    /**
     * Determines the next anniversary date from this object's date and after the given startingDate.
     * 
     * @param startingDate              starting date to determine the anniversary (the anniversary must be after
     *                                  or equal to the startingDate)
     *
     * @return  anniversary for this object's date that comes AFTER the startingDate
     */
    public EDITDate getNextAnniversaryDate(EDITDate startingDate)
    {
        EDITDate anniversary = null;

        //  First, create a date as a starting point.  Use startingDate's year.  The day is more difficult
        //  since it may not be a valid day in startingDate's month.  If it is not valid, use this
        if (EDITDate.isAValidDate(startingDate.getFormattedYear() + EDITDate.DATE_DELIMITER + this.getFormattedMonth() + EDITDate.DATE_DELIMITER + 
        		this.getFormattedDay())) {
        	anniversary = new EDITDate(startingDate.getYear(), this.getMonth(), this.getDay());
        } else {
        	anniversary = this;
        }

        //  If the anniversary is before or equal to the startingDate, go to the next month
        while (anniversary.beforeOREqual(startingDate))
        {
        	anniversary = anniversary.addYears(1);
        }

        return anniversary;
    }
    
    /**
     * Determines the prior anniversary date from this date and before the given startingDate
     * 
     * @param startingDate              starting date to determine the anniversary (the anniversary must be before
     *                                  the startingDate)
     *
     * @return  anniversary for this object's date that comes BEFORE the startingDate
     */
    public EDITDate getPriorAnniversaryDate(EDITDate startingDate)
    {
        EDITDate anniversary = null;

        //  First, create a date as a starting point.  Use startingDate's year and month.  The day is more difficult
        //  since it may not be a valid day in startingDate's month.  If it is not valid, use this
        if (EDITDate.isAValidDate(startingDate.getFormattedYear() + EDITDate.DATE_DELIMITER + this.getFormattedMonth() + EDITDate.DATE_DELIMITER + 
        		this.getFormattedDay()))
        {
        	anniversary = new EDITDate(startingDate.getYear(), this.getMonth(), this.getDay());
        } else {
        	anniversary = this;
        }

        //  If the anniversary is after the startingDate, go to the previous year. 
        while (anniversary.after(startingDate))
        {
        	anniversary = anniversary.subtractYears(1);
        }

        return anniversary;
    }

    /**
     * Determines the next date that occurs after "this" date for the given newMonth and newDay
     * <P>
     * For example, if this date is 3/15/2008, the newMonth is 9 and the newDay is 20, the returned date
     * is 9/20/2008.  If this object's date is 12/15/2008, the newMonth is 1 and the newDay is 20, the returned
     * date is 1/20/2009.
     * <P>
     * This isn't as trivial as it seems since we run the risk of invalid days for a given month.  And even the
     * year may be a problem for leap years.
     * <P>
     * NOTE: To simplify the calculation, Tom decided that the newDay can never be greater than 28.  The restriction of
     * 28 will be controlled by the caller.  This means that the day does not need to be calculated, just set.  This
     * eliminates the problem of advancing the day which may also advance the month.
     *
     * @param newMonth              the value of the month unit for the next date
     * @param newDay                the value of the day unit for the next date
     *
     * @return valid date that occurs after this object's date with the newMonth and newDay
     */
    public EDITDate getNextDate(int newMonth, int newDay)
    {
        int numberOfForwardMonths = getNumberOfForwardMonths(this, newMonth);
//        int numberOfForwardDays = getNumberOfForwardDays(this, newDay);

        EDITDate nextDate = this.addMonths(numberOfForwardMonths);
//        nextDate = nextDate.addDays(numberOfForwardDays);
        nextDate = new EDITDate(nextDate.getYear(), nextDate.getMonth(), newDay);

        return nextDate;
    }

    /**
     * Gets the month number for a given month name (ex. September returns 9)
     *
     * @param monthName
     *
     * @return  the integer month number that corresponds to the month name
     */
    public static int getMonthNumber(String monthName)
    {
        int monthNumber = 0;

        for (int i = 0; i < EDITDate.MONTH_NAMES.length; i++)
        {
            String month = EDITDate.MONTH_NAMES[i];

            if (month.equalsIgnoreCase(monthName))
            {
                monthNumber = i + 1;
                break;
            }
        }

        return monthNumber;
    }

    /**
     * Determines the number of months to go forward from startingDate's month to the endingMonth
     * <P>
     * Examples: startingMonth = 2, endingMonth = 9; number of forward months = 7
     *           startingMonth = 9, endingMonth = 2; number of forward months = 5
     *           startingMonth = 12, endingMonth = 1; number of forward months = 1
     *
     * @param startingDate          date whose month begins the count
     * @param endingMonth           month ending the count (which may be in the next year)
     *
     * @return  difference between the months going forward in time
     */
    private int getNumberOfForwardMonths(EDITDate startingDate, int endingMonth)
    {
        int startingMonth = startingDate.getMonth();

        int numberOfForwardMonths = endingMonth - startingMonth;

        if (numberOfForwardMonths < 0)
        {
            // Negative number, must be going forward into next year.
            // Find the difference between the December (12) and the starting month, then add the endingMonth
            numberOfForwardMonths = 12 - startingMonth + endingMonth; // 12-9+2 = 5; 12-12+1 = 1
        }

        return numberOfForwardMonths;
    }

    /**
     * Determines the number of days to go forward from startingDate's day to get to the endingDay
     * <P>
     * Examples: startingDay = 2, endingDay = 28; number of forward days = 26
     *           startingDay = 28, endingDay = 2; number of forward days = ?  depends on how many days there are in the starting month
     *           startingDay = 31, endingDay = 1; number of forward days = 1
     *
     * @param startingDate        date whose day begins the count
     * @param endingDay           day of the month ending the count (which may be in the next month)
     *
     * @return  difference between the days going forward in time
     */
    private int getNumberOfForwardDays(EDITDate startingDate, int endingDay)
    {
        int startingDay = startingDate.getDay();

        int numberOfDaysInStartingMonth = startingDate.getNumberOfDaysInMonth();

        int numberOfForwardDays = endingDay - startingDay;

        if (numberOfForwardDays < 0)
        {
            // Negative number, must be going forward into next month.
            // Find the difference between the number of days in the start month and the starting day, then add the endingDay
            numberOfForwardDays = numberOfDaysInStartingMonth - startingDay + endingDay; // 31-31+1 = 1
        }

        return numberOfForwardDays;
    }


    //                 MAIN - for testing purposes only

//    public static void main(String[] args) throws Exception
//    {
        //  Testing performance of different functs that determine whether a date or not
//
        //EDITDate ed = null;
//
 //       try
//        {
//           String str = EDITDate.convertYYYYMMDDToMMDDYYYYifNeeded("11/22/2004");
//            System.out.println(str);
//        }
//        catch (RuntimeException e)
//       {
//            System.out.println("bad date");
//        }
//    }
//
//        long startTime = System.currentTimeMillis();
//
//        for (int i = 0; i < 10000; i ++)
//        {
//                ed.isAValidDate();        // this takes longer if false, but have to comment out check in constructor to get here
//        }
//
//        long stopTime = System.currentTimeMillis();
//
//        System.out.println("EDITDate.main : init " + "startTime = " + startTime + ", stopTime = " + stopTime +
//                ", elapsed time = " + (stopTime - startTime));
//
//        // ----------------------------------
//
//        startTime = System.currentTimeMillis();
//
//        for (int i = 0; i < 10000; i ++)
//        {
//            EDITDate.isAValidDate("2005/22/35");
//        }
//
//        stopTime = System.currentTimeMillis();
//
//        System.out.println("EDITDate.main : static " + "startTime = " + startTime + ", stopTime = " + stopTime +
//                ", elapsed time = " + (stopTime - startTime));
//
//        // ----------------------------------
//
//        startTime = System.currentTimeMillis();
//
//        for (int i = 0; i < 10000; i ++)
//        {
//            EDITDate.isACandidateDate("2005/22/35");
//        }
//
//        stopTime = System.currentTimeMillis();
//
//        System.out.println("EDITDate.main : candidate " + "startTime = " + startTime + ", stopTime = " + stopTime +
//                ", elapsed time = " + (stopTime - startTime));
//    }
}
