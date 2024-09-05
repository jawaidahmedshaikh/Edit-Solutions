/*
 * User: sdorman
 * Date: Aug 23, 2005
 * Time: 9:56:41 AM
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */

package fission.utility;

import edit.common.*;
import edit.common.vo.*;
import edit.services.db.*;

import java.lang.reflect.*;
import java.util.*;


/**
 * This class provides utility methods to simplify date and time display manipulation.
 * <P>
 * This class is TEMPORARY!  It provides front end to back end manipulation of date formats.  When we change the back
 * end to use the same format as the front end, this class will disappear!
 * <P>
 * Do NOT use this class to do any kind of date calculations.  Do that in EDITDate (most calculations already exist).
 */
public class DateTimeUtil
{
    /**
     * Dates are system-stored as YYYY/MM/DD, but need to be displayed as MM/DD/YYYY
     *
     * @param yyyymmDD
     * @return
     */
    public static String formatYYYYMMDDToMMDDYYYY(String yyyymmDD)
    {
        String date = "";

        if ((yyyymmDD != null) && (!yyyymmDD.equals("")))
        {
            EDITDate ed = new EDITDate(yyyymmDD);

            date = ed.getFormattedMonth() + EDITDate.DATE_DELIMITER + ed.getFormattedDay() + EDITDate.DATE_DELIMITER + ed.getFormattedYear();
        }

        return date;
    }

    /**
     * Format the date from the front end's MM/dd/yyyy format to the back end's yyyy/MM/dd format and return as a string
     *
     * @param mmDDyyyy
     * @return  String containing the date in the format yyyy/mm/DD
     */
    public static String formatMMDDYYYYToYYYYMMDD(String mmddyyyy)
    {
        String date = null;

        if ((mmddyyyy != null) && (!mmddyyyy.equals("")))
        {
            EDITDate ed = DateTimeUtil.getEDITDateFromMMDDYYYY(mmddyyyy);

            date = ed.getFormattedDate();
        }

        return date;
    }


    /**
     * Returns the month, day, and year as a date formatted as MM/DD/YYYY
     *
     * @param month
     * @param day
     * @param year
     *
     * @return  date formatted as MM/dd/yyyy
     */
    public static String formatDateAsMMDDYYYY(String month, String day, String year)
    {
        String date = "";

        if (!month.equals("") && !day.equals("") && !year.equals(""))
        {
            EDITDate ed = new EDITDate(year, month, day);

            date = ed.getFormattedMonth() + EDITDate.DATE_DELIMITER + ed.getFormattedDay() + EDITDate.DATE_DELIMITER + ed.getFormattedYear();
        }

        return date;
    }

    /**
     * Returns a date formatted as yyyy/mm/dd without validating the date first.
     * @param year
     * @param month
     * @param day
     * @return
     */
//    public static String formatDate(String year, String month, String day)
//    {
//        return year + EDITDate.DATE_DELIMITER + month + EDITDate.DATE_DELIMITER + day;
//    }

    /**
     * Returns the EDITDate object as a date string formatted as MM/DD/YYYY
     *
     * @param editDate
     *
     * @return  date formatted as MM/dd/yyyy
     */
    public static String formatEDITDateAsMMDDYYYY(EDITDate editDate)
    {
        if (editDate != null)
        {
            return editDate.getFormattedMonth() + EDITDate.DATE_DELIMITER + editDate.getFormattedDay() + EDITDate.DATE_DELIMITER + editDate.getFormattedYear();
        }
        else
        {
            return null;
        }
    }

    /**
     * Returns the EDITDateTime object as a string formatted with the date portion as MM/DD/YYYY and the time portion as usual
     *
     * @param editDateTime
     *
     * @return  datetime with a formatted date as MM/dd/yyyy
     */
    public static String formatEDITDateTimeAsMMDDYYYY(EDITDateTime editDateTime)
    {
        return formatEDITDateAsMMDDYYYY(editDateTime.getEDITDate()) + EDITDateTime.DATE_TIME_DELIMITER + editDateTime.getFormattedTime();
    }

    /**
     * Converts a string in MM/dd/yyyy format to an EDITDate
     *
     * @param mmddyyyy              string containing a date in the format of MM/dd/yyyy
     *
     * @return EDITDate object. Null if the mmddyyyy string was empty or null
     */
    public static EDITDate getEDITDateFromMMDDYYYY(String mmddyyyy)
    {
        EDITDate editDate = null;

        if (mmddyyyy != null && ! mmddyyyy.equals(""))
        {
            String[] tokens = Util.fastTokenizer(mmddyyyy, EDITDate.DATE_DELIMITER);

            String month = tokens[0];
            String day = tokens[1];
            String year = tokens[2];

            editDate = new EDITDate(year, month, day);
        }

        return editDate;
    }

    /**
     * Converts a string in MM/dd/yyyy hh:mm:ss format to an EDITDateTime object
     *
     * @param mmddyyyyhhmmss              string containing a date in the format of MM/dd/yyyy hh:mm:ss
     *
     * @return EDITDateTime object. Null if the mmddyyyyhhmmss string was empty or null
     */
    public static EDITDateTime getEDITDateTimeFromMMDDYYYY(String mmddyyyyhhmmss)
    {
        EDITDateTime editDateTime = null;

        if (mmddyyyyhhmmss != null && ! mmddyyyyhhmmss.equals(""))
        {
            String[] tokens = Util.fastTokenizer(mmddyyyyhhmmss, EDITDateTime.DATE_TIME_DELIMITER);

            String date = tokens[0];
            String time = tokens[1];

            String[] dateTokens = Util.fastTokenizer(date, EDITDate.DATE_DELIMITER);

            String month = dateTokens[0];
            String day = dateTokens[1];
            String year = dateTokens[2];

            String[] timeTokens = Util.fastTokenizer(time, EDITDateTime.TIME_DELIMITER);

            String hour = timeTokens[0];
            String minute = timeTokens[1];
            String second = timeTokens[2];

            editDateTime = new EDITDateTime(year, month, day, hour, minute, second);
        }

        return editDateTime;
    }

    /**
     * Convience method only for refactor to new EDITDate.  Creates and EDITDate object out of specified month, day,
     * and year.
     *
     * @param month              string containing the month
     * @param day                string containing the day
     * @param year               string containing the year
     *
     * @return EDITDate object
     */
//    public static EDITDate getEDITDateFromMonthDayYear(String month, String day, String year)
//    {
//        return new EDITDate(year, month, day);
//    }

    /**
     * Uses reflection to get the month, day, year values of the specified date String. This method helps the ui layer
     * by initialize dates for the pages.  If the VO is null, the result value is an array of empty Strings.
     *
     * @param vo
     * @param property     name of the VO date (String) property in java-proper case.
     * @return
     */
    public static String[] initDate(VOObject vo, String property)
    {
        String[] date = { "", "", "" };

        try
        {
            if (vo != null)
            {
                property = Util.convertFirstCharacterCase(property, Util.UPPER_CASE);

                String getterName = "get" + property;

                Method getter = vo.getClass().getMethod(getterName, null);

                String dateAsStr = (String) getter.invoke(vo, null);

                if (dateAsStr != null)
                {
                    EDITDate ed = new EDITDate(dateAsStr);

                    date[0] = ed.getFormattedMonth();
                    date[1] = ed.getFormattedDay();
                    date[2] = ed.getFormattedYear();
                }
            }
        }
        catch (Exception e)
        {
            System.out.println(e);

            e.printStackTrace();

            throw new RuntimeException(e);
        }

        return date;
    }

    /**
     * Returns an arry of month, day, year values for the given dateString. If the dateString is null or an empty
     * string, the result is an array of empty Strings.  This method helps the ui layer by initialize dates for the pages.
     *
     * @param dateString     date in the format of yyyymmdd to be parsed
     *
     * @return array of strings containing month, day, year (in that order)
     */
    public static String[] initDate(String dateString)
    {
        String[] dateArray = { "", "", "" };

        try
        {
            if (dateString == null || dateString.equals(""))
            {
                return dateArray;
            }
            else
            {
                EDITDate ed = new EDITDate(dateString);

                dateArray[0] = ed.getFormattedMonth();
                dateArray[1] = ed.getFormattedDay();
                dateArray[2] = ed.getFormattedYear();

                return dateArray;
            }
        }
        catch (Exception e)
        {
            System.out.println(e);

            e.printStackTrace();

            throw new RuntimeException(e);
        }
    }

    /**
     * Returns an arry of month, day, year values for the given EDITDate object. If the EDITDate object is null, the
     * result is an array of empty Strings.  This method helps the ui layer by initialize dates for the pages.
     *
     * @param editDate     EDITDate object
     *
     * @return array of strings containing month, day, year (in that order)
     */
    public static String[] initDate(EDITDate editDate)
    {
        String[] dateArray = { "", "", "" };

        try
        {
            if (editDate == null)
            {
                return dateArray;
            }
            else
            {
                dateArray[0] = editDate.getFormattedMonth();
                dateArray[1] = editDate.getFormattedDay();
                dateArray[2] = editDate.getFormattedYear();

                return dateArray;
            }
        }
        catch (Exception e)
        {
            System.out.println(e);

            e.printStackTrace();

            throw new RuntimeException(e);
        }
    }

    /**
     * Returns a fully formatted date string for the given month, day, and year.  If the month, day, or year is null or
     * an empty string, the specified default value is returned. This method is used by the ui layer to get month, day, year values
     * from the page and put into a valid date string.
     *
     * @param month             month unit of the date
     * @param day               day unit of the date
     * @param year              year unit of the date
     * @param defaultValue      default value that will be returned if any of the specified parameters are null or empty
     *
     * @return  fully formatted date string (yyyymmdd) or default value
     */
    public static String initDate(String month, String day, String year, String defaultValue)
    {
        String dateString = defaultValue;

        try
        {
            if ((month != null && day != null && year != null) && (!month.equals("") && !day.equals("") && !year.equals("")))
            {
                EDITDate editDate = new EDITDate(year, month, day);

                dateString = editDate.getFormattedDate();
            }
        }
        catch (Exception e)
        {
            System.out.println(e);

            e.printStackTrace();

            throw new RuntimeException(e);
        }

        return dateString;
    }


    /**
     * Returns an EDITDate object the given month, day, and year.  If the month, day, or year is null or
     * an empty string, a null EDITDate object is returned. This method is used by the ui layer to get month, day, year values
     * from the page and put into a valid date object.
     *
     * @param month             month unit of the date
     * @param day               day unit of the date
     * @param year              year unit of the date
     *
     * @return  EDITDate object.  Null if any of the specified parameters are null or empty.
     */
    public static EDITDate initDate(String month, String day, String year)
    {
        EDITDate editDate = null;

        try
        {
            if ((month != null && day != null && year != null) && (!month.equals("") && !day.equals("") && !year.equals("")))
            {
                editDate = new EDITDate(year, month, day);
            }
        }
        catch (Exception e)
        {
            System.out.println(e);

            e.printStackTrace();

            throw new RuntimeException(e);
        }

        return editDate;
    }

    /**
     * Initializes an EDITDate object with the given formattedDateString.  The string is expected to be formatted
     * using the standard format in EDITDate.  If formattedDateString is null or empty, this method returns a null
     *
     * @param formattedDateString
     *
     * @return EDITDate object, null if formattedDateString is null or empty
     */
    public static EDITDate initEDITDate(String formattedDateString)
    {
        EDITDate editDate = null;

        if (formattedDateString != null && !formattedDateString.equals(""))
        {
            editDate = new EDITDate(formattedDateString);
        }

        return editDate;
    }

    /**
     * Initializes an EDITDateTime object with the given formattedDateTimeString.  The string is expected to be formatted
     * using the standard format in EDITDateTime.  If formattedDateTimeString is null or empty, this method returns a null
     *
     * @param formattedDateTimeString
     *
     * @return EDITDateTime object, null if formattedDateTimeString is null or empty
     */
    public static EDITDateTime initEDITDateTime(String formattedDateTimeString)
    {
        EDITDateTime editDateTime = null;

        if (formattedDateTimeString != null && !formattedDateTimeString.equals(""))
        {
            if (formattedDateTimeString.length() == 10) // We are allowing yyyy/MM/dd to be a valid date/time - we simply default the time.
            {                                           // This is considered the "fastest" check - not rigorous - but sufficient.
                formattedDateTimeString = formattedDateTimeString + " " + EDITDateTime.DEFAULT_MIN_TIME;
            }
            
            editDateTime = new EDITDateTime(formattedDateTimeString);
        }

        return editDateTime;
    }

    /**
     * Uses reflection to get the month, day, year values of the specified date String. If the VO is null,
     * the result value is an array of empty Strings.
     *
     * @param vo
     * @param property     name of the VO date (String) property in java-proper case.
     * @return
     */
    public static String[] initDateTime(VOObject vo, String property)
    {
        String[] dateTime = { "", "", "", "" };

        try
        {
            if (vo != null)
            {
                property = Util.convertFirstCharacterCase(property, Util.UPPER_CASE);

                String getterName = "get" + property;

                Method getter = vo.getClass().getMethod(getterName, null);

                String dateAsStr = (String) getter.invoke(vo, null);

                if (dateAsStr != null)
                {
                    EDITDateTime editDateTime = new EDITDateTime(dateAsStr);

                    EDITDate editDate = editDateTime.getEDITDate();

                    String timeString = editDateTime.getFormattedTime();

                    dateTime[0] = editDate.getFormattedYear();
                    dateTime[1] = editDate.getFormattedMonth();
                    dateTime[2] = editDate.getFormattedDay();

                    dateTime[3] = timeString;
                }
            }
        }
        catch (Exception e)
        {
            System.out.println(e);

            e.printStackTrace();

            throw new RuntimeException(e);
        }

        return dateTime;
    }

    public static void convertDatesToYYYYMMDD(VOObject voObject)
    {
        try
        {
            VOClass voClass = new VOClass(voObject.getClass());

            DBTable dbTable = DBTable.getDBTableForTable(voClass.getTableName());

            DBColumn[] dbColumns = dbTable.getDBColumns();

            for (int i = 0; i < dbColumns.length; i++)
            {
                if (dbColumns[i].isDateOrDateTime())
                {
                    String columnName = dbColumns[i].getColumnName();

                    VOMethod getterMethod = voClass.getSimpleGetter(columnName);
                    VOMethod setterMethod = voClass.getSimpleSetter(columnName);

                    String dateAsMMDDYYYY = (String) getterMethod.getMethod().invoke(voObject, null);

                    String dateAsYYYYMMDD = null;

                    if (dbColumns[i].isDateTime())
                    {
                        EDITDateTime editDateTime = DateTimeUtil.getEDITDateTimeFromMMDDYYYY(dateAsMMDDYYYY);

                        if (editDateTime != null)
                        {
                            dateAsYYYYMMDD = editDateTime.getFormattedDateTime();
                        }
                    }
                    else
                    {
                        //  It's a Date, use EDITDate to convert
                        EDITDate editDate = DateTimeUtil.getEDITDateFromMMDDYYYY(dateAsMMDDYYYY);

                        if (editDate != null)
                        {
                            dateAsYYYYMMDD = editDate.getFormattedDate();
                        }
                    }

                    setterMethod.getMethod().invoke(voObject, new String[] { dateAsYYYYMMDD });
                }
            }
        }
        catch (Exception e)
        {
            System.out.println(e);

            e.printStackTrace();

            throw new RuntimeException(e);
        }
    }

    /**
     * Returns the year portion of an accounting period.  Accounting periods are in the format of yyyy/mm
     *
     * @param accountingPeriod          string in the format of yyyy/mm
     *
     * @return  string containing yyyy
     */
    public static String getYearFromAccountingPeriod(String accountingPeriod)
    {
        String[] tokens = Util.fastTokenizer(accountingPeriod, EDITDate.DATE_DELIMITER);

        return tokens[0];
    }

    /**
     * Returns the month portion of an accounting period.  Accounting periods are in the format of yyyy/mm
     *
     * @param accountingPeriod      string in the format of yyyy/mm
     *
     * @return  string containing mm
     */
    public static String getMonthFromAccountingPeriod(String accountingPeriod)
    {
        String[] tokens = Util.fastTokenizer(accountingPeriod, EDITDate.DATE_DELIMITER);

        return tokens[1];
    }

    /**
     * Builds the accounting period string by concatenating the year and month separated by the date delimiter.
     * @param year          year in the format of yyyy
     * @param month         month in the format of mm
     * @return  concatenated year and month separated by date delimiter
     */
    public static String buildAccountingPeriod(String year, String month)
    {
        return year + EDITDate.DATE_DELIMITER + month;
    }

    /**
     * Builds the accounting period string by concatenating the year and month of the specified EDITDate separated by the date delimiter.
     * @param editDate          EDITDate object containing the date
     * @return  concatenated year and month separated by date delimiter
     */
    public static String buildAccountingPeriod(EDITDate editDate)
    {
        return editDate.getFormattedYear() + EDITDate.DATE_DELIMITER + editDate.getFormattedMonth();
    }

    /**
     * Builds the accounting period string by concatenating the month and year separate by the date delimiter.
     * @param month
     * @param year
     * @return  concatenated month and year separated by date delimiter
     */
    public static String buildAccountingPeriodAsMMYYYY(String month, String year)
    {
        return month + EDITDate.DATE_DELIMITER + year;
    }

    /**
     * Pretty formats time in millis to HH:MM:SS:ss
     * @param timeMillis
     * @return
     */
    public static String convertTimeToHHMMSSss(long timeMillis)
    {
        long duration = timeMillis;

        long millis = duration % 1000;
        duration = duration / 1000;

        duration = timeMillis / 1000;

        long secs = duration % 60;
        duration = duration / 60;

        long mins = duration % 60;
        duration = duration / 60;

        long hours = duration % 60;

        return Util.padWithZero(String.valueOf(hours), 2) + ":" + Util.padWithZero(String.valueOf(mins), 2) + ":" + Util.padWithZero(String.valueOf(secs), 2) + "." + Util.padWithZero(String.valueOf(Math.round(millis / 10.0)), 2);
    }

    /**
     * Returns the year unit of the date string in yyyymmdd format WITHOUT validating the date.  This is needed for
     * possible zero dates
     * @param yyyymmdd
     * @return year (yyyy) portion of date string
     */
    public static String getYearFromYYYYMMDD(String yyyymmdd)
    {
        StringTokenizer tokens = DateTimeUtil.tokenize(yyyymmdd);
        String year = tokens.nextToken();
        String month = tokens.nextToken();
        String day = tokens.nextToken();

        return year;
    }

    /**
     * Returns the month unit of the date string in yyyymmdd format WITHOUT validating the date.  This is needed for
     * possible zero dates
     * @param yyyymmdd
     * @return month (mm) portion of date string
     */
    public static String getMonthFromYYYYMMDD(String yyyymmdd)
    {
        StringTokenizer tokens = DateTimeUtil.tokenize(yyyymmdd);
        String year = tokens.nextToken();
        String month = tokens.nextToken();
        String day = tokens.nextToken();

        return month;
    }

    /**
     * Returns the day unit of the date string in yyyymmdd format WITHOUT validating the date.  This is needed for
     * possible zero dates
     * @param yyyymmdd
     * @return day (dd) portion of date string
     */
    public static String getDayFromYYYYMMDD(String yyyymmdd)
    {
        StringTokenizer tokens = DateTimeUtil.tokenize(yyyymmdd);
        String year = tokens.nextToken();
        String month = tokens.nextToken();
        String day = tokens.nextToken();

        return day;
    }

    /**
     * Returns the year unit of the date string in mmddyyyy format WITHOUT validating the date.  This is needed for
     * possible zero dates
     * @param mmddyyyy
     * @return year (yyyy) portion of date string
     */
    public static String getYearFromMMDDYYYY(String mmddyyyy)
    {
        StringTokenizer tokens = DateTimeUtil.tokenize(mmddyyyy);
        String month = tokens.nextToken();
        String day = tokens.nextToken();
        String year = tokens.nextToken();

        return year;
    }

    /**
     * Returns the month unit of the date string in mmddyyyy format WITHOUT validating the date.  This is needed for
     * possible zero dates
     * @param mmddyyyy
     * @return month (mm) portion of date string
     */
    public static String getMonthFromMMDDYYYY(String mmddyyyy)
    {
        StringTokenizer tokens = DateTimeUtil.tokenize(mmddyyyy);
        String month = tokens.nextToken();
        String day = tokens.nextToken();
        String year = tokens.nextToken();

        return month;
    }

    /**
     * Returns the day unit of the date string in mmddyyyy format WITHOUT validating the date.  This is needed for
     * possible zero dates
     * @param mmddyyyy
     * @return day (dd) portion of date string
     */
    public static String getDayFromMMDDYYYY(String mmddyyyy)
    {
        StringTokenizer tokens = DateTimeUtil.tokenize(mmddyyyy);
        String month = tokens.nextToken();
        String day = tokens.nextToken();
        String year = tokens.nextToken();

        return day;
    }

    /**
     * Inserts the date delimiter for a date string in the format of mmddyyyy (without delimiters).
     * This is mainly used when importing data which has dates with no delimiters.
     *                                                                                            01234567
     * @param mmddyyyy              date string in the format of mmddyyyy with no delimiters (ex. 01132008).
     *
     * @return  mmddyyyy string but with delimiters separating the fields (ex. 01/13/2008 depending on the system's delimiter)
     */
    public static String insertDateDelimiterForMMDDYYYY(String mmddyyyy)
    {
        String delimitedDate = null;

        String month = mmddyyyy.substring(0, 2);
        String day   = mmddyyyy.substring(2, 4);
        String year  = mmddyyyy.substring(4, 8);

        delimitedDate = month + EDITDate.DATE_DELIMITER + day + EDITDate.DATE_DELIMITER + year;

        return delimitedDate;
    }

    private static StringTokenizer tokenize(String date)
    {
        StringTokenizer tokens = new StringTokenizer(date, EDITDate.DATE_DELIMITER);

        return tokens;
    }
    
    public static void main(String[] args)
    {
        String mmddyyyy = "10/27/2006";

        EDITDate editDate = DateTimeUtil.getEDITDateFromMMDDYYYY(mmddyyyy);

        System.out.println("editDate.getFormattedDate() = " + editDate.getFormattedDate());
    }
}