/*
 * User: sdorman
 * Date: Sep 5, 2006
 * Time: 11:16:18 AM
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement. 
 */

package acord.model.common;

import java.text.*;
import java.util.*;

import logging.*;
import org.apache.logging.log4j.Logger;
import edit.services.logging.*;
import edit.common.*;

/**
 * ACORD dates have a different format than EDITDate.  The format is YYYY-MM-dd.  This class uses EDITDate to get
 * date functionality without duplicating the EDITDate class.
 */
public class ACORDDate
{
    public static final String DATE_DELIMITER = "-";

    public static final String DATE_FORMAT = EDITDate.YEAR_FORMAT + DATE_DELIMITER + EDITDate.MONTH_FORMAT + DATE_DELIMITER + EDITDate.DAY_FORMAT;

    private final SimpleDateFormat dateFormat = new SimpleDateFormat(ACORDDate.DATE_FORMAT);

    private EDITDate editDate;

    /**
     * Constructor with no arguments defaults to today's date
     *
     * @throws RuntimeException if the date is not valid
     */
    public ACORDDate() throws RuntimeException
    {
        this.editDate = new EDITDate();
    }

    /**
     * Constructor taking ints as arguments
     *
     * @param year  year value to set for this date
     * @param month month value to set for this date
     * @param day   day value to set for this date
     *
     * @throws RuntimeException if the date is not valid
     */
    public ACORDDate(int year, int month, int day) throws RuntimeException
    {
        this.editDate = new EDITDate(year, month, day);
    }

    /**
     * Constructor taking strings as arguments
     *
     * @param year  year value to set for this date
     * @param month month value to set for this date
     * @param day   day value to set for this date
     *
     * @throws RuntimeException if the date is not valid
     */
    public ACORDDate(String year, String month, String day) throws RuntimeException
    {
        this.editDate = new EDITDate(year, month, day);
    }

    /**
     * Constructor taking an EDITDate as an argument.
     *
     * @param editDate EDITDate whose values will be used to create new object
     *
     * @throws RuntimeException if the date is not valid
     */
    public ACORDDate(EDITDate editDate) throws RuntimeException
    {
        this.editDate = editDate;
    }

    /**
     * Constructor taking a full date string formatted as EDITDate.DATE_FORMAT.
     *
     * @param formattedDateString string containing a formatted date.
     *
     * @throws RuntimeException if the date is not valid
     */
    public ACORDDate(String formattedDateString) throws RuntimeException
    {
        Date dateObject = null;

        try
        {
            dateObject = this.dateFormat.parse(formattedDateString);
        }
        catch (ParseException e)
        {
            LogEvent event = new LogEvent("Error parsing formatted date in ACORDDate constructor: " + formattedDateString, e);

            Logger logger = Logging.getLogger(Logging.GENERAL_EXCEPTION);

            logger.error(event);
        }

        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        gregorianCalendar.setLenient(false);
        gregorianCalendar.setTime(dateObject);
        
        gregorianCalendar.get(Calendar.DAY_OF_MONTH);
        
        this.editDate = new EDITDate(gregorianCalendar.get(Calendar.YEAR), gregorianCalendar.get(Calendar.MONTH) + 1 , gregorianCalendar.get(Calendar.DAY_OF_MONTH));
    }

    /**
     * Returns the date as a string formatted according to the standard EDITDate format
     *
     * @return formatted string
     */
    public final String getFormattedDate()
    {
        return dateFormat.format(new Date(this.editDate.getTimeInMilliseconds()));
    }

    /**
     * Returns the EDITDate equivalent to this date
     *
     * @return  EDITDate object
     */
    public EDITDate getEDITDate()
    {
        return this.editDate;
    }



    //           MAIN - for testing purposes only
    public static void main(String[] args)
    {
        EDITDate ed = new EDITDate();

        System.out.println("ed.getFormattedDate() = " + ed.getFormattedDate());

        ACORDDate ad = new ACORDDate();

        System.out.println("ad.getFormattedDate() = " + ad.getFormattedDate());
        
        ACORDDate ad2 = new ACORDDate(ad.getFormattedDate());
        
        System.out.println("ad2.getFormattedDate() = " + ad2.getFormattedDate());
         
        EDITDate ed2 = ad2.getEDITDate();
        
        System.out.println("ed2.getFormattedDate() = " + ed2.getFormattedDate());
    }
}