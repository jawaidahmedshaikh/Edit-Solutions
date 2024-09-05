/*
 * User: sdorman
 * Date: Jul 15, 2005
 * Time: 5:34:09 PM
 *
 * (c) 2000-2005 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement. 
 */

package acord;

import java.util.GregorianCalendar;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.io.Serializable;

import logging.LogEvent;
import org.apache.logging.log4j.Logger;
import edit.services.logging.Logging;

// ******** NOTE: This class is the new EDITDate that can not be put into EQDevelopment yet.  However, I needed it
// for ACORDDate so I placed it in the acord package.  THIS IS TEMPORARY!  This class has also been restricted to
// package level access to avoid confustion with edit.common.EDITDate


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
public final class EDITDate implements Serializable, Cloneable         // package level restriction
{
    public static final String DATE_DELIMITER = "/";

    public static final String DAY_FORMAT = "dd";
    public static final String MONTH_FORMAT = "MM";
    public static final String YEAR_FORMAT = "yyyy";

    public static final String DATE_FORMAT = YEAR_FORMAT + DATE_DELIMITER + MONTH_FORMAT + DATE_DELIMITER + DAY_FORMAT;

    public static final String DEFAULT_MAX_DAY = "31";
    public static final String DEFAULT_MAX_MONTH = "12";
    public static final String DEFAULT_MAX_YEAR = "9999";

    public static final String DEFAULT_MIN_DAY = "01";
    public static final String DEFAULT_MIN_MONTH = "01";
    public static final String DEFAULT_MIN_YEAR = "1800";

    public static final String DEFAULT_MAX_DATE = DEFAULT_MAX_YEAR + DATE_DELIMITER + DEFAULT_MAX_MONTH + DATE_DELIMITER + DEFAULT_MAX_DAY;
    public static final String DEFAULT_MIN_DATE = DEFAULT_MIN_YEAR + DATE_DELIMITER + DEFAULT_MIN_MONTH + DATE_DELIMITER + DEFAULT_MIN_DAY;

    // DO NOT USE DEFAULT_ZERO_DATE!!!!!  IT IS HERE TEMPORARILY FOR CRUD WHILE WE TRANSITION ALL DATES TO PROPER DATES
    public static final String DEFAULT_ZERO_DATE = "0000" + DATE_DELIMITER + "00" + DATE_DELIMITER + "00";

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

    public static final int FIRST_QUARTER_END_DAY = 31;
    public static final int SECOND_QUARTER_END_DAY = 30;
    public static final int THIRD_QUARTER_END_DAY = 30;
    public static final int FOURTH_QUARTER_END_DAY = 31;

    private static final String[] MONTH_NAMES = { "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December" };

    private static final Pattern REGULAR_EXPRESSION_DATE_FORMAT = Pattern.compile("(\\d\\d\\d\\d" + EDITDate.DATE_DELIMITER + "\\d\\d" + EDITDate.DATE_DELIMITER + "\\d\\d)");

    private final SimpleDateFormat dateFormat = new SimpleDateFormat(EDITDate.DATE_FORMAT);

    private final SimpleDateFormat dayFormat = new SimpleDateFormat(EDITDate.DAY_FORMAT);
    private final SimpleDateFormat monthFormat = new SimpleDateFormat(EDITDate.MONTH_FORMAT);
    private final SimpleDateFormat yearFormat = new SimpleDateFormat(EDITDate.YEAR_FORMAT);

    private final GregorianCalendar gregorianCalendar;



    /**
     * Constructor with no arguments defaults to today's date
     *
     * @throws RuntimeException if the date is not valid
     */
    public EDITDate() throws RuntimeException
    {
        this.gregorianCalendar = new GregorianCalendar();
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
        Date dateObject = null;

        try
        {
            dateObject = this.dateFormat.parse(formattedDateString);
        }
        catch (ParseException e)
        {
            LogEvent event = new LogEvent("Error parsing formatted date in EDITDate constructor: " + formattedDateString, e);

            Logger logger = Logging.getLogger(Logging.GENERAL_EXCEPTION);

            logger.error(event);
        }

        this.gregorianCalendar = new GregorianCalendar();
        this.gregorianCalendar.setLenient(false);
        this.gregorianCalendar.setTime(dateObject);

        if (! isAValidDate())
        {
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
        return dateFormat.format(this.gregorianCalendar.getTime());
    }

    /**
     * This method overrides the Object class's toString to provide the formatted date.  This is necessary because
     * the toString method is automatically called when pushing/popping items on the stack in script processing.
     * This method is identical to getFormattedDate();
     *
     * @return  formatted string
     * 
     * @see getFormattedDate()
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
        return dayFormat.format(this.gregorianCalendar.getTime());
    }

    /**
     * Returns the month unit of the date as a string formatted according to the standard EDITDate format
     *
     * @return  formatted string
     */
    public final String getFormattedMonth()
    {
        return monthFormat.format(this.gregorianCalendar.getTime());
    }

    /**
     * Returns the year unit of the date as a string formatted according to the standard EDITDate format
     *
     * @return  formatted string
     */
    public final String getFormattedYear()
    {
        return yearFormat.format(this.gregorianCalendar.getTime());
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
                dayOfWeekAsString = "Sunday";
                break;
            case Calendar.MONDAY:
                dayOfWeekAsString = "Monday";
                break;
            case Calendar.TUESDAY:
                dayOfWeekAsString = "Tuesday";
                break;
            case Calendar.WEDNESDAY:
                dayOfWeekAsString = "Wednesday";
                break;
            case Calendar.THURSDAY:
                dayOfWeekAsString = "Thursday";
                break;
            case Calendar.FRIDAY:
                dayOfWeekAsString = "Friday";
                break;
            case Calendar.SATURDAY:
                dayOfWeekAsString = "Saturday";
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

        Matcher m = REGULAR_EXPRESSION_DATE_FORMAT.matcher(formattedDateString);

        isADate = m.matches();

        return isADate;
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
     * Returns the last day of the current year
     *
     * @return  date at end of the year
     */
    public final EDITDate getEndOfYearDate()
    {
        return new EDITDate(this.getYear(), EDITDate.FOURTH_QUARTER_END_MONTH, EDITDate.FOURTH_QUARTER_END_DAY);
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

        if (mode.equalsIgnoreCase("Monthly"))
        {
            endOfModeDate = this.getEndOfMonthDate();
        }
        else if (mode.equalsIgnoreCase("Quarterly"))
        {
            endOfModeDate = this.getEndOfQuarterDate();
        }
        else if (mode.equalsIgnoreCase("SemiAnnual"))
        {
            endOfModeDate = this.getEndOfSemiAnnualDate();
        }
        else if (mode.equalsIgnoreCase("Yearly"))
        {
            endOfModeDate = this.getEndOfYearDate();
        }

        return endOfModeDate;
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
    public final int getElapsedDays(EDITDate editDate)
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

        gc2.gregorianCalendar.clear(Calendar.MILLISECOND);
        gc2.gregorianCalendar.clear(Calendar.SECOND);
        gc2.gregorianCalendar.clear(Calendar.MINUTE);
        gc2.gregorianCalendar.clear(Calendar.HOUR_OF_DAY);

        while (gc1.before(gc2))
        {
            gc1.gregorianCalendar.add(Calendar.DATE, 1);
            elapsed++;
        }

        return elapsed;
    }

    /**
     * Calculates the number of months between 2 dates.
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
    public final int getElapsedMonths(EDITDate editDate)
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

        while (gc1.before(gc2))
        {
             gc1.gregorianCalendar.add(Calendar.MONTH, 1);
             elapsed++;
        }

        return elapsed;
    }

    /**
     * Determine the age at the last birthday using this date as the date of birth
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
     * Determine the age at the next birthday using this date as the date of birth
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
     * Determines the age at the nearest birthday using this date as the date of birth.  The nearest birthday is
     * determined by comparing the number of days between the last birthday and today and the number of days between
     * the next birthday and today.  Whichever is lower, that's the nearest birthday.
     *
     * @return  age at the nearest birthday.
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
     * Determines this year's birth date based on the this object's date (the date of birth) and the current age.
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


    //                 MAIN - for testing purposes only

    public static void main(String[] args) throws Exception
    {
        //  Testing performance of different functs that determine whether a date or not

        EDITDate ed = null;

        try
        {
            ed = new EDITDate(2005, 2, 3);
        }
        catch (RuntimeException e)
        {
            System.out.println("bad date");
        }

        long startTime = System.currentTimeMillis();

        for (int i = 0; i < 10000; i ++)
        {
                ed.isAValidDate();        // this takes longer if false, but have to comment out check in constructor to get here
        }

        long stopTime = System.currentTimeMillis();

        System.out.println("EDITDate.main : init " + "startTime = " + startTime + ", stopTime = " + stopTime +
                ", elapsed time = " + (stopTime - startTime));

        // ----------------------------------

        startTime = System.currentTimeMillis();

        for (int i = 0; i < 10000; i ++)
        {
            EDITDate.isAValidDate("2005/22/35");
        }

        stopTime = System.currentTimeMillis();

        System.out.println("EDITDate.main : static " + "startTime = " + startTime + ", stopTime = " + stopTime +
                ", elapsed time = " + (stopTime - startTime));

        // ----------------------------------

        startTime = System.currentTimeMillis();

        for (int i = 0; i < 10000; i ++)
        {
            EDITDate.isACandidateDate("2005/22/35");
        }

        stopTime = System.currentTimeMillis();

        System.out.println("EDITDate.main : candidate " + "startTime = " + startTime + ", stopTime = " + stopTime +
                ", elapsed time = " + (stopTime - startTime));
    }
}
