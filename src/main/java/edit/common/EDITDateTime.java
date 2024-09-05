/*
 * User: sdorman
 * Date: Aug 5, 2005
 * Time: 11:03:55 AM
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement. 
 */

package edit.common;

 
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


import edit.services.logging.Logging;
import logging.LogEvent;
import org.apache.logging.log4j.Logger;


/**
 * The EDITDateTime class provides all the date/time functionality for the EDITSolutions system.  It specifies the
 * standard format for all time and date/time fields, including a minimum and maximum times.
 * <P>
 * EDITDateTime uses EDITDate to handle the date portions of the date/time.  It uses GregorianCalendar to verify the
 * times.
 * <P>
 * This class is immutable, which means that you can't change the values once you have created an instance.  EDITDateTime
 * uses the GregorianCalendar which is not immutable.  GregorianCalendar is, admittedly by Sun, badly designed and
 * written.  The lack of immutability is one of many problems.  EDITDateTime tries to isolate those problems from our
 * system.
 */
public final class EDITDateTime implements Serializable 
{
	private static final long serialVersionUID = 1L;
	
	public static final String TIME_DELIMITER = ":";
    public static final String DATE_TIME_DELIMITER = " ";

    public static final String HOUR_FORMAT = "HH";
    public static final String MINUTE_FORMAT = "mm";
    public static final String SECOND_FORMAT = "ss";

    public static final String TIME_FORMAT = HOUR_FORMAT + TIME_DELIMITER + MINUTE_FORMAT + TIME_DELIMITER + SECOND_FORMAT;

    public static final String DATETIME_FORMAT = EDITDate.DATE_FORMAT + DATE_TIME_DELIMITER + TIME_FORMAT;

    public static final String DEFAULT_MIN_TIME = "00:00:00";
    public static final String DEFAULT_MAX_TIME = "23:59:59";

    public static final String DEFAULT_MAX_DATETIME = EDITDate.DEFAULT_MAX_DATE + DATE_TIME_DELIMITER + DEFAULT_MAX_TIME;
    public static final String DEFAULT_MIN_DATETIME = EDITDate.DEFAULT_MIN_DATE + DATE_TIME_DELIMITER + DEFAULT_MIN_TIME;

    private final EDITDate editDate;

    private final int hour;         // this is the hour of the day, from 0 to 23
    private final int minute;
    private final int second;


    /**
     * Constructor with no arguments defaults to today's date and time
     */
    public EDITDateTime()
    {
        Calendar gc = new GregorianCalendar();

        this.editDate = new EDITDate(gc.get(Calendar.YEAR), gc.get(Calendar.MONTH)+1, gc.get(Calendar.DATE));

        this.hour = gc.get(Calendar.HOUR_OF_DAY);
        this.minute = gc.get(Calendar.MINUTE);
        this.second = gc.get(Calendar.SECOND);
    }

    /**
     * Constructor taking ints as arguments
     *
     * @param year      year value to set for date
     * @param month     month value to set for date
     * @param day       day value to set for date
     * @param hour      hour value to set for time
     * @param minute    minute value to set for time
     * @param second    second value to set for time
     */
    public EDITDateTime(int year, int month, int day, int hour, int minute, int second)
    {
        Calendar gc = getCalendar(year, month, day, hour, minute, second);

        this.editDate = new EDITDate(year, month, day);

        this.hour = gc.get(Calendar.HOUR_OF_DAY);
        this.minute = gc.get(Calendar.MINUTE);
        this.second = gc.get(Calendar.SECOND);
    }

    /**
     * Constructor taking Strings as arguments
     *
     * @param year      year value to set for date
     * @param month     month value to set for date
     * @param day       day value to set for date
     * @param hour      hour value to set for time
     * @param minute    minute value to set for time
     * @param second    second value to set for time
     */
    public EDITDateTime(String year, String month, String day, String hour, String minute, String second)
    {
        Calendar gc = getCalendar(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day),
                Integer.parseInt(hour), Integer.parseInt(minute), Integer.parseInt(second));


        this.editDate = new EDITDate(year, month, day);

        this.hour = gc.get(Calendar.HOUR_OF_DAY);
        this.minute = gc.get(Calendar.MINUTE);
        this.second = gc.get(Calendar.SECOND);
    }

    /**
     * Constructor taking an EDITDate object for the date portion and a fully formatted string for the time portion.
     *
     * @param editDate          EDITDate object representing the date portion
     * @param timeString        properly formatted string containing the time portion
     */
    public EDITDateTime(EDITDate editDate, String timeString)
    {
        Calendar gc = new GregorianCalendar();

        this.editDate = editDate;

        try
        {
            SimpleDateFormat timeFormat     = new SimpleDateFormat(EDITDateTime.TIME_FORMAT);
            
            Date date = timeFormat.parse(timeString);
            
            gc.setTime(date);
        }
        catch(ParseException e)
        {
            LogEvent event = new LogEvent("Error parsing formatted time in EDITDateTime constructor: " + timeString, e);

            Logger logger = Logging.getLogger(Logging.GENERAL_EXCEPTION);

            logger.error(event);
        }

        this.hour = gc.get(Calendar.HOUR_OF_DAY);
        this.minute = gc.get(Calendar.MINUTE);
        this.second = gc.get(Calendar.SECOND);
    }


    /**
     * Constructor taking milliseconds as arg
     *
     * @param milliseconds
     */
    public EDITDateTime(long milliseconds)
    {
        Calendar gc = new GregorianCalendar();
        gc.setTimeInMillis(milliseconds);

        this.editDate = new EDITDate(gc.get(Calendar.YEAR), gc.get(Calendar.MONTH)+1, gc.get(Calendar.DATE));

        this.hour = gc.get(Calendar.HOUR_OF_DAY);
        this.minute = gc.get(Calendar.MINUTE);
        this.second = gc.get(Calendar.SECOND);
    }

    /**
     * Constructor taking another EDITDateTime object as arg
     *
     * @param editDateTime          EDITDateTime whose values will be used to create new object
     */
    public EDITDateTime(EDITDateTime editDateTime)
    {
        this.editDate = new EDITDate(editDateTime.getEDITDate());

        this.hour = editDateTime.getHour();
        this.minute = editDateTime.getMinute();
        this.second = editDateTime.getSecond();
    }

    /**
     * Constructor taking a full date string formatted as EDITDate.DATE_FORMAT.
     *
     * @param formattedDateTimeString      string containing a formatted datetime.
     */
    public EDITDateTime(String formattedDateTimeString)
    {
        Calendar gc = new GregorianCalendar();

        try
        {
            SimpleDateFormat dateTimeFormat = new SimpleDateFormat(EDITDateTime.DATETIME_FORMAT);
            
            Date date = dateTimeFormat.parse(formattedDateTimeString);
            
            gc.setTime(date);
        }
        catch(ParseException e)
        {
            LogEvent event = new LogEvent("Error parsing formatted date/time in EDITDateTime constructor: " + formattedDateTimeString, e);

            Logger logger = Logging.getLogger(Logging.GENERAL_EXCEPTION);

            logger.error(event);
        }

        this.editDate = new EDITDate(gc.get(Calendar.YEAR), gc.get(Calendar.MONTH)+1, gc.get(Calendar.DATE));

        this.hour = gc.get(Calendar.HOUR_OF_DAY);
        this.minute = gc.get(Calendar.MINUTE);
        this.second = gc.get(Calendar.SECOND);
    }

    /**
     * Copies this EDITDateTime object and returns a new object
     *
     * @return  new EDITDateTime object with the same values as this object
     */
    public final EDITDateTime copy()
    {
       EDITDateTime clone = new EDITDateTime(this);

       return clone;
    }


    /**
     * Returns the EDITDate portion of this object
     *
     * @return  EDITDate object which contains only date information, not time.
     */
    public final EDITDate getEDITDate()
    {
        return editDate;
    }

    /**
     * Returns the hour of day unit of the time (0-23 hours)
     *
     * @return  hour of day for this time object
     */
    public final int getHour()
    {
        return this.hour;
    }

    /**
     * Returns the minute unit of time
     *
     * @return minute for this time object
     */
    public final int getMinute()
    {
        return this.minute;
    }

    /**
     * Returns the second unit of time
     *
     * @return second for this time object
     */
    public final int getSecond()
    {
        return this.second;
    }

    /**
     * Returns the hour of day unit of the time as a string formatted according to the standard EDITDateTime format

     * @return  formatted hour of day
     */
    public final String getFormattedHour()
    {
        Calendar gc = getCalendar();
        

        SimpleDateFormat hourFormat = new SimpleDateFormat(EDITDateTime.HOUR_FORMAT);

        return hourFormat.format(gc.getTime());
    }

    /**
     * Returns the minute unit of the time as a string formatted according to the standard EDITDateTime format
     *
     * @return  formatted minute
     */
    public final String getFormattedMinute()
    {
        Calendar gc = getCalendar();
        

        SimpleDateFormat minuteFormat = new SimpleDateFormat(EDITDateTime.MINUTE_FORMAT);

        return minuteFormat.format(gc.getTime());
    }

    /**
     * Returns the second unit of the time as a string formatted according to the standard EDITDateTime format
     *
     * @return  formatted second
     */
    public final String getFormattedSecond()
    {
        Calendar gc = getCalendar();
        

        SimpleDateFormat secondFormat = new SimpleDateFormat(EDITDateTime.SECOND_FORMAT);

        return secondFormat.format(gc.getTime());
    }

    /**
     * Returns the time as a string formatted according to the standard EDITDateTime format
     *
     * @return  formatted time string
     */
    public final String getFormattedTime()
    {
        Calendar gc = getCalendar();
        
        SimpleDateFormat timeFormat = new SimpleDateFormat(EDITDateTime.TIME_FORMAT);

        return timeFormat.format(gc.getTime());
    }

    /**
     * Returns the date and time as a string formatted according to the standard EDITDateTime format
     *
     * @return  formatted date and time string
     */
    public final String getFormattedDateTime()
    {
        Calendar gc = getCalendar();
        

        SimpleDateFormat dateTimeFormat = new SimpleDateFormat(EDITDateTime.DATETIME_FORMAT);

        return dateTimeFormat.format(gc.getTime());
    }

    /**
     * Overrides the Object class's toString to provide the formatted date and time.  THIS METHOD SHOULD NOT BE USED
     * DIRECTLY!  It only exists because the toString method is automatically called when pushing/popping items on the
     * stack in script processing.  It is also automatically called by Hibernate.
     * <P>
     * Use the getFormattedDateTime() method instead (results are identical);
     *
     * @return  formatted date and time string
     *
     * @see #getFormattedDateTime()
     */
    public final String toString()
    {
        return getFormattedDateTime();
    }

    /**
     * Returns the number of milliseconds for this object's time
     *
     * @return  number of milliseconds
     */
    public final long getTimeInMilliseconds()
    {
        Calendar gc = getCalendar();

        return gc.getTimeInMillis();
    }


    //      PRIVATE METHODS

    /**
     * Creates a GregorianCalendar with this object's data members
     *
     * @return  GregorianCalendar reflecting the date and time of this object
     */
    private Calendar getCalendar()
    {
        Calendar gc = new GregorianCalendar(this.editDate.getYear(), this.editDate.getMonth()-1, this.editDate.getDay(),
                this.hour, this.minute, this.second);

        return gc;
    }

    /**
     * Creates a GregorianCalendar with the args provided
     *
     * @param year      year value to set for date
     * @param month     month value to set for date
     * @param day       day value to set for date
     * @param hour      hour value to set for time
     * @param minute    minute value to set for time
     * @param second    second value to set for time
     *
     * @return  GregorianCalendar reflecting the date and time provided via the method args
     */
    private Calendar getCalendar(int year, int month, int day, int hour, int minute, int second)
    {
        Calendar gc = new GregorianCalendar(year, month-1, day, hour, minute, second);

        return gc;
    }
    
    @Override
    public boolean equals(Object obj)
    {
        boolean dateTimesAreEqual = false;

        if (obj != null)
        {
            long thisTimeInMillis = getTimeInMilliseconds();
            
            EDITDateTime visitingEDITDateTime = (EDITDateTime) obj;
            
            long visitingTimeInMillis = visitingEDITDateTime.getTimeInMilliseconds();
            
            dateTimesAreEqual = (thisTimeInMillis == visitingTimeInMillis);
        }

        return dateTimesAreEqual;
    }    
}
