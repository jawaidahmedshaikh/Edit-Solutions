/*
 * User: sprasad
 * Date: Nov 27, 2006
 * Time: 11:34:11 AM
 *
 * (c) 2000-2008 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package engine.sp;

import businesscalendar.BusinessCalendar;
import businesscalendar.BusinessDay;
import edit.common.EDITDate;
import fission.utility.Util;

/**
 * Gets the best business date.  If working storage does not contain a value for the name Restriction or it does and
 * its value is "Restricted", the best business day is determined using restricted criteria.  If the Restriction is
 * set to "Unrestricted", it is determined without the restricted criteria.
 */
public class Getbusinessdate extends InstOneOperand
{
    private static final String RESTRICTION_RESTRICTED = "Restricted";
    private static final String RESTRICTION_UNRESTRICTED = "Unrestricted";

    public void compile(ScriptProcessor scriptProcessor)
    {
        sp = scriptProcessor;
    }

    /**
     * Pre Requisite: An entry with key "CalendarDate" should exist in working storage.
     * Accepted input format: YYYY/MM/DD
     * Output format: YYYY/MM/DD
     * Looks for entry "CalendarDate" in working storage
     * Puts the best business date in stack
     * @param scriptProcessor
     */
    public void exec(ScriptProcessor scriptProcessor)
    {
        sp = scriptProcessor;

        String calendarDate = (String) sp.getWSEntry("CalendarDate");

        String restriction = (String) sp.getWSEntry("Restriction");

	String eftLeadDays = Util.initString((String) sp.getWSEntry("EFTLEADDAYS"), null);

        sp.push(getBusinessDate(calendarDate, restriction, eftLeadDays));

        sp.incrementInstPtr();
    }

    /**
     * Helper method to get the best business date for given date.
     * @param date string format: yyyy/mm/dd
     * @return date string format: yyyy/mm/dd
     */
    private String getBusinessDate(String date, String restriction, String eftLeadDays)
    {
        BusinessCalendar businessCalendar = new BusinessCalendar();

        BusinessDay businessDay = null;

        //  Allow null in this check for backward compatibility.  This instruction originally took no arguments and
        //  always performed the restricted check.
        if (restriction == null || restriction.equals(RESTRICTION_RESTRICTED))
        {
            businessDay = businessCalendar.getBestBusinessDay(new EDITDate(date));
        }
        else if (restriction.equals(RESTRICTION_UNRESTRICTED))
        {
            businessDay = businessCalendar.getBestUnrestictedBusinessDay(new EDITDate(date));
        }

        EDITDate bestBusinessDate = businessDay.getBusinessDateNew();

        if (eftLeadDays != null)
        {
            EDITDate newBestBusinessDate =  checkDayOfWeek(bestBusinessDate, Integer.parseInt(eftLeadDays));
            if (newBestBusinessDate != null)
            {
                bestBusinessDate = newBestBusinessDate;
            }
        }

        return bestBusinessDate.getFormattedDate();
    }

    private EDITDate checkDayOfWeek(EDITDate bestBusinessDate, int leadDays)
    {
        String dayOfWeek = bestBusinessDate.getDayOfWeek();
        EDITDate newBestBusinessDate = null;

        if (dayOfWeek.equalsIgnoreCase(EDITDate.FRIDAY))
        {
            newBestBusinessDate = bestBusinessDate.subtractDays(leadDays - 1);
        }

        return newBestBusinessDate;
    }
}
