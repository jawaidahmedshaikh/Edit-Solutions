package engine.sp;

import businesscalendar.BusinessCalendar;
import businesscalendar.BusinessDay;
import edit.common.EDITDate;
import fission.utility.Util;

/**
 * Gets the best business date minus leadDays.  
 */
public class Getbusinessdateprior extends InstOneOperand
{

	private static final long serialVersionUID = 1L;

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
        System.out.println("Getbusinessdateprior: 1: " + calendarDate);

	    String leadDays = Util.initString((String) sp.getWSEntry("EFTLEADDAYS"), null);
	    if (leadDays == null) {
	        leadDays = Util.initString((String) sp.getWSEntry("LEADDAYS"), null);
	    }

        sp.push(getBusinessDate(calendarDate, leadDays));

        sp.incrementInstPtr();
    }

    /**
     * Helper method to get the best business date for given date.
     * @param date string format: yyyy/mm/dd
     * @return date string format: yyyy/mm/dd
     */
    private String getBusinessDate(String date, String leadDays)
    {

        BusinessDay[] prevBusinessDays = null;

        prevBusinessDays = BusinessDay.findBy_MaxRows_BusinessDate_LT_ActiveInd(new EDITDate(date), Integer.parseInt(leadDays), "Y" );

        return prevBusinessDays[Integer.parseInt(leadDays) - 1].getBusinessDate().getFormattedDate();
    }

}
