/*
 * User: sdorman
 * Date: Jul 26, 2005
 * Time: 9:45:01 AM
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */

package unittest.edit;

import edit.common.*;

import junit.framework.TestCase;

import java.util.GregorianCalendar;
import java.util.Calendar;




public class TestEDITDate extends TestCase
{
    protected void setUp() throws Exception
    {
        super.setUp(); 
    }

    protected void tearDown() throws Exception
    {
        super.tearDown(); 
    }

    public void testConstructors()
    {
        Calendar gc = new GregorianCalendar(2005, 7-1, 15);
        long milliseconds = gc.getTimeInMillis();

        EDITDate edInt = new EDITDate(2005, 7, 15);
        EDITDate edString = new EDITDate("2005", "7", "15");
        EDITDate edEDITDate = new EDITDate(new EDITDate(2005, 7, 15));
        EDITDate edMilliseconds = new EDITDate(milliseconds);
        EDITDate edFormattedString = new EDITDate("2005/07/15");

        assertEquals(edInt.getFormattedDate(), "2005/07/15");
        assertEquals(edString.getFormattedDate(), "2005/07/15");
        assertEquals(edEDITDate.getFormattedDate(), "2005/07/15");
        assertEquals(edMilliseconds.getFormattedDate(), "2005/07/15");
        assertEquals(edFormattedString.getFormattedDate(), "2005/07/15");
    }

    public void testAddDays()
    {
        EDITDate ed = new EDITDate(2005, 7, 15);

        EDITDate newDate = ed.addDays(3);

        assertEquals(newDate.getFormattedDate(), "2005/07/18");
    }
    
    public void testAddMonths()
    {
        EDITDate ed = new EDITDate(2005, 7, 15);

        EDITDate newDate = ed.addMonths(7);

        assertEquals(newDate.getFormattedDate(), "2006/02/15");
    }
    
    public void testAddYears()
    {
        EDITDate ed = new EDITDate(2005, 7, 15);

        EDITDate newDate = ed.addYears(8);

        assertEquals(newDate.getFormattedDate(), "2013/07/15");
    }

    public void testSubtractDays()
    {
        EDITDate ed = new EDITDate(2005, 7, 15);

        EDITDate newDate = ed.subtractDays(3);

        assertEquals(newDate.getFormattedDate(), "2005/07/12");
    }

    public void testSubtractMonths()
    {
        EDITDate ed = new EDITDate(2005, 7, 15);

        EDITDate newDate = ed.subtractMonths(7);

        assertEquals(newDate.getFormattedDate(), "2004/12/15");
    }

    public void testSubtractYears()
    {
        EDITDate ed = new EDITDate(2005, 7, 15);

        EDITDate newDate = ed.subtractYears(8);

        assertEquals(newDate.getFormattedDate(), "1997/07/15");
    }

    public void testGetDay()
    {
        EDITDate ed = new EDITDate(2005, 7, 15);

        int day = ed.getDay();

        assertEquals(day, 15);
    }

    public void testGetMonth()
    {
        EDITDate ed = new EDITDate(2005, 7, 15);

        int month = ed.getMonth();

        assertEquals(month, 7);
    }

    public void testGetYear()
    {
        EDITDate ed = new EDITDate(2005, 7, 15);

        int year = ed.getYear();

        assertEquals(year, 2005);
    }

    public void testGetFormattedDay()
    {
        EDITDate ed = new EDITDate(2005, 7, 15);

        String formattedDay = ed.getFormattedDay();

        assertEquals(formattedDay, "15");
    }

    public void testGetFormattedMonth()
    {
        EDITDate ed = new EDITDate(2005, 7, 15);

        String formattedMonth = ed.getFormattedMonth();

        assertEquals(formattedMonth, "07");
    }

    public void testGetFormattedYear()
    {
        EDITDate ed = new EDITDate(2005, 7, 15);

        String formattedYear = ed.getFormattedYear();

        assertEquals(formattedYear, "2005");
    }

    public void testGetYearAsYY()
    {
        EDITDate ed = new EDITDate(2005, 7, 15);

        String yyYear = ed.getYearAsYY();

        assertEquals(yyYear, "05");
    }

    public void testGetFormattedDate()
    {
        EDITDate ed = new EDITDate(2005, 7, 15);

        String formattedDate = ed.getFormattedDate();

        assertEquals(formattedDate, "2005/07/15");
    }

    public void testToString()
    {
        EDITDate ed = new EDITDate(2005, 7, 15);

        String formattedDate = ed.toString();

        assertEquals(formattedDate, "2005/07/15");
    }

    public void testGetDayOfYear()
    {
        EDITDate ed = new EDITDate(2005, 7, 15);

        int dayOfYear = ed.getDayOfYear();

        assertEquals(dayOfYear, 196);
    }

    public void testGetNumberOfDaysInMonth()
    {
        EDITDate ed = new EDITDate(2005, 7, 15);

        int numberOfDaysInMonth = ed.getNumberOfDaysInMonth();

        assertEquals(numberOfDaysInMonth, 31);
    }

    public void testGetQuarter()
    {
        EDITDate ed1 = new EDITDate(2005, 2, 15);
        EDITDate ed2 = new EDITDate(2005, 5, 15);
        EDITDate ed3 = new EDITDate(2005, 7, 15);
        EDITDate ed4 = new EDITDate(2005, 11, 15);

        int firstQuarter = ed1.getQuarter();
        int secondQuarter = ed2.getQuarter();
        int thirdQuarter = ed3.getQuarter();
        int fourthQuarter = ed4.getQuarter();

        assertEquals(firstQuarter, 1);
        assertEquals(secondQuarter, 2);
        assertEquals(thirdQuarter, 3);
        assertEquals(fourthQuarter, 4);
    }


    public void testIsLeapYear()
    {
        EDITDate ed2005 = new EDITDate(2005, 7, 15);

        boolean isLeapYear2005 = ed2005.isLeapYear();

        EDITDate ed1968 =  new EDITDate(1968, 10, 10);

        boolean isLeapYear1968 = ed1968.isLeapYear();

        assertFalse(isLeapYear2005);
        assertTrue(isLeapYear1968);
    }

    public void testIsAtEndOfMonth()
    {
        EDITDate ed28 = new EDITDate(2007, 2, 28);

        boolean isAtEndOfMonth28 = ed28.isAtEndOfMonth();

        EDITDate ed15 = new EDITDate(2007, 2, 15);

        boolean isAtEndOfMonth15 = ed15.isAtEndOfMonth();

        assertTrue(isAtEndOfMonth28);
        assertFalse(isAtEndOfMonth15);
    }


    public void testGetElapsedDays()
    {
        EDITDate ed = new EDITDate(2005, 7, 15);
        EDITDate newDate = new EDITDate(2006, 10, 25);

        long elapsedDays = ed.getElapsedDays(newDate);

        assertEquals(elapsedDays, 467);
    }

    public void testGetElapsedMonths()
    {
        EDITDate ed = new EDITDate(2005, 7, 15);
        EDITDate newDate = new EDITDate(2006, 10, 25);

        long elapsedMonths = ed.getElapsedMonths(newDate);

        assertEquals(elapsedMonths, 15);
    }

    public void testGetElapsedMonthsWithNoRemainderDays()
    {
        EDITDate ed = new EDITDate(2005, 7, 15);
        EDITDate newDate = new EDITDate(2006, 10, 15);

        long elapsedMonths = ed.getElapsedMonths(newDate);

        assertEquals(elapsedMonths, 15);
    }

    public void testGetElapsedMonthsRoundedUp()
    {
        EDITDate ed = new EDITDate(2005, 7, 15);
        EDITDate newDate = new EDITDate(2006, 10, 25);

        long elapsedMonths = ed.getElapsedMonthsRoundedUp(newDate);

        assertEquals(elapsedMonths, 16);
    }

    public void testGetElapsedMonthsRoundedUpWithOlderDate()
    {
        EDITDate ed = new EDITDate(2006, 10, 25);
        EDITDate newDate = new EDITDate(2005, 7, 15);

        long elapsedMonths = ed.getElapsedMonthsRoundedUp(newDate);

        assertEquals(elapsedMonths, -16);
    }

    public void testGetAgeAtLastBirthday()
    {
        EDITDate ed = new EDITDate(1961, 12, 5);

        int age = ed.getAgeAtLastBirthday();

        assertEquals(age,53);       // example: in 2006, answer should be 44 (before 12/5), else 45
    }

    public void testGetAgeAtNextBirthday()
    {
        EDITDate ed = new EDITDate(1961, 12, 5);

        int age = ed.getAgeAtNextBirthday();

        assertEquals(age, 54);
    }

    public void testGetAgeAtNearestBirthday()
    {
        //  Note:  this method's results will vary depending on the day it is executed since it is relative to
        //  "today's" date
        int ageFor19680101 = new EDITDate(1968, 1, 1).getAgeAtNearestBirthday();
        int ageFor19680115 = new EDITDate(1968, 1, 15).getAgeAtNearestBirthday();
        int ageFor19680116 = new EDITDate(1968, 1, 16).getAgeAtNearestBirthday();
        int ageFor19680125 = new EDITDate(1968, 1, 25).getAgeAtNearestBirthday();
        int ageFor19680126 = new EDITDate(1968, 1, 26).getAgeAtNearestBirthday();
        int ageFor19680221 = new EDITDate(1968, 2, 21).getAgeAtNearestBirthday();
        int ageFor19680521 = new EDITDate(1968, 5, 21).getAgeAtNearestBirthday();
        int ageFor19680721 = new EDITDate(1968, 7, 21).getAgeAtNearestBirthday();
        int ageFor19680921 = new EDITDate(1968, 9, 21).getAgeAtNearestBirthday();
        int ageFor19680229 = new EDITDate(1968, 2, 29).getAgeAtNearestBirthday();

        assertEquals(ageFor19680101, 38);
        assertEquals(ageFor19680115, 38);
        assertEquals(ageFor19680116, 38);
        assertEquals(ageFor19680125, 38);
        assertEquals(ageFor19680126, 38);
        assertEquals(ageFor19680221, 37);
        assertEquals(ageFor19680521, 37);
        assertEquals(ageFor19680721, 37);
        assertEquals(ageFor19680921, 37);
        assertEquals(ageFor19680229, 37);
    }

    public void testBefore()
    {
        EDITDate ed = new EDITDate(2005, 7, 15);

        boolean before20041025 = ed.before(new EDITDate(2004, 10, 25));
        boolean before20051025 = ed.before(new EDITDate(2005, 10, 25));
        boolean before20050715 = ed.before(new EDITDate(2005, 7, 15));

        assertFalse(before20041025);
        assertTrue(before20051025);
        assertFalse(before20050715);
    }

    public void testAfter()
    {
        EDITDate ed = new EDITDate(2005, 7, 15);

        boolean after20041025 = ed.after(new EDITDate(2004, 10, 25));
        boolean after20051025 = ed.after(new EDITDate(2005, 10, 25));
        boolean after20050715 = ed.after(new EDITDate(2005, 7, 15));

        assertTrue(after20041025);
        assertFalse(after20051025);
        assertFalse(after20050715);
    }

    public void testEquals()
    {
        EDITDate ed = new EDITDate(2005, 7, 15);

        boolean equals20050715 = ed.equals(new EDITDate(2005, 7, 15));
        boolean equals20051025 = ed.equals(new EDITDate(2005, 10, 25));

        assertTrue(equals20050715);
        assertFalse(equals20051025);
    }

    public void testGetMonthName()
    {
        EDITDate ed = new EDITDate(2005, 7, 15);

        String monthName = ed.getMonthName();

        assertEquals(monthName, "July");
    }

    public void testStaticGetMonthName()
    {
        String monthName = EDITDate.getMonthName("07");   // or "7" will work

        assertEquals(monthName, "July");
    }

    public void testGetDayOfWeek()
    {
        EDITDate ed = new EDITDate(2005, 7, 15);

        String dayOfWeek = ed.getDayOfWeek();

        assertEquals(dayOfWeek, "Friday");
    }

    public void testGetEndOfQuarterDate()
    {
        EDITDate ed = new EDITDate(2005, 7, 15);

        EDITDate endOfQuarter = ed.getEndOfQuarterDate();

        assertEquals(endOfQuarter.getMonth(), 9);
        assertEquals(endOfQuarter.getDay(), 30);
        assertEquals(endOfQuarter.getYear(), ed.getYear());
    }

    public void testGetEndOfMonthDate()
    {
        EDITDate ed = new EDITDate(2005, 7, 15);

        EDITDate endOfMonth = ed.getEndOfMonthDate();

        assertEquals(endOfMonth.getMonth(), ed.getMonth());
        assertEquals(endOfMonth.getDay(), 31);
        assertEquals(endOfMonth.getYear(), ed.getYear());
    }

    public void testGetEndOfYearDate()
    {
        EDITDate ed = new EDITDate(2005, 7, 15);

        EDITDate endOfYear = ed.getEndOfYearDate();

        assertEquals(endOfYear.getMonth(), 12);
        assertEquals(endOfYear.getDay(), 31);
        assertEquals(endOfYear.getYear(), ed.getYear());
    }

    public void testGetEndOfSemiAnnualDate()
    {
        EDITDate ed = new EDITDate(2005, 7, 15);

        EDITDate endOfSemiAnnual = ed.getEndOfSemiAnnualDate();

        assertEquals(endOfSemiAnnual.getMonth(), 12);
        assertEquals(endOfSemiAnnual.getDay(), 31);
        assertEquals(endOfSemiAnnual.getYear(), ed.getYear());

        ed = new EDITDate(2005, 3, 15);

        endOfSemiAnnual = ed.getEndOfSemiAnnualDate();

        assertEquals(endOfSemiAnnual.getMonth(), 6);
        assertEquals(endOfSemiAnnual.getDay(), 30);
        assertEquals(endOfSemiAnnual.getYear(), ed.getYear());
    }

    public void testGetEndOfModeDate()
    {
        EDITDate ed = new EDITDate(2005, 7, 15);

        EDITDate monthly = ed.getEndOfModeDate("Monthly");

        assertEquals(monthly.getMonth(), 7);
        assertEquals(monthly.getDay(), 31);
        assertEquals(monthly.getYear(), ed.getYear());

        EDITDate quarterly = ed.getEndOfModeDate("Quarterly");
        
        assertEquals(quarterly.getMonth(), 9);
        assertEquals(quarterly.getDay(), 30);
        assertEquals(quarterly.getYear(), ed.getYear());

        EDITDate semiAnnual = ed.getEndOfModeDate("SemiAnnual");

        assertEquals(semiAnnual.getMonth(), 12);
        assertEquals(semiAnnual.getDay(), 31);
        assertEquals(semiAnnual.getYear(), ed.getYear());

        EDITDate yearly = ed.getEndOfModeDate("Annual");

        assertEquals(yearly.getMonth(), 12);
        assertEquals(yearly.getDay(), 31);
        assertEquals(yearly.getYear(), ed.getYear());
    }

    public void testGetStartOfYearDate()
    {
        EDITDate ed = new EDITDate(2005, 7, 15);

        EDITDate startOfYear = ed.getStartOfYearDate();

        assertEquals(startOfYear.getMonth(), 1);
        assertEquals(startOfYear.getDay(), 1);
        assertEquals(startOfYear.getYear(), ed.getYear());
    }

    public void testIsADate()
    {
        boolean isADate = EDITDate.isAValidDate("2005/07/15");

        assertTrue(isADate);

        isADate = EDITDate.isAValidDate("2005-07-15");

        assertFalse(isADate);

        isADate = EDITDate.isAValidDate("2005/22/55");

        assertFalse(isADate);
    }

    public void testIsACandidateDate()
    {
        boolean isADate = EDITDate.isACandidateDate("2005/07/15");

        assertTrue(isADate);

        isADate = EDITDate.isACandidateDate("2005-07-15");

        assertFalse(isADate);

        isADate = EDITDate.isACandidateDate("2005/22/55");

        assertTrue(isADate);
    }

    public void testGetSeventyHalfDate()
    {
        EDITDate dateOfBirth = new EDITDate("1933/08/29");

        EDITDate seventyHalfDate = dateOfBirth.getSeventyHalfDate();

        assertEquals(seventyHalfDate.getFormattedDate(), "2004/02/28");
    }

    public void testIsLeapDay()
    {
        EDITDate editDate = new EDITDate("2004/02/29");

        boolean isLeapDay = editDate.isLeapDay();

        assertEquals(isLeapDay, true);
    }

    public void testGetNextMonthiversary()
    {
        String[] startDate =         {"2009/02/14", "2009/02/14", "2009/02/14", "2009/02/14", "2009/10/31", "2009/02/28", "2009/02/14", "2009/09/30"};
        String[] annivDate =         {"2006/08/31", "2006/08/14", "2006/08/01", "2006/09/30", "2006/09/30", "2006/09/30", "2006/09/29", "2006/08/31"};
        String[] monthiversaryDate = {"2009/02/28", "2009/03/14", "2009/03/01", "2009/02/28", "2009/11/30", "2009/03/30", "2009/02/28", "2009/10/31"};

        for (int i = 0; i < startDate.length; i++)
        {
            EDITDate monthiversary = new EDITDate(annivDate[i]).getNextMonthiversaryDate(new EDITDate(startDate[i]));

            assertEquals(monthiversaryDate[i], monthiversary.getFormattedDate());
        }
    }

    public void testGetPriorMonthiversary()
    {
        String[] startDate =         {"2009/02/14", "2009/02/14", "2009/02/14", "2009/02/14", "2009/10/31", "2009/02/28", "2009/02/14", "2009/09/30"};
        String[] annivDate =         {"2006/08/31", "2006/08/14", "2006/08/01", "2006/09/30", "2006/09/30", "2006/09/30", "2006/09/29", "2006/08/31"};
        String[] monthiversaryDate = {"2009/01/31", "2009/02/14", "2009/02/01", "2009/01/30", "2009/10/30", "2009/02/28", "2009/01/29", "2009/09/30"};

        for (int i = 0; i < startDate.length; i++)
        {
            EDITDate monthiversary = new EDITDate(annivDate[i]).getPriorMonthiversaryDate(new EDITDate(startDate[i]));

            assertEquals(monthiversaryDate[i], monthiversary.getFormattedDate());
        }
    }

    public void getMonthNumber()
    {
        assertEquals(EDITDate.getMonthNumber("January"), 1);
        assertEquals(EDITDate.getMonthNumber("September"), 9);
        assertEquals(EDITDate.getMonthNumber("December"), 12);
    }

    public void getNextDate()
    {
        EDITDate existingDate = new EDITDate(2008, 3, 15);

        assertEquals(existingDate.getNextDate(9, 28), new EDITDate(2008, 9, 28));
        assertEquals(existingDate.getNextDate(1, 28), new EDITDate(2009, 1, 28));

        EDITDate decDate = new EDITDate(2008, 12, 15);
        assertEquals(decDate.getNextDate(1, 1), new EDITDate(2009, 1, 1));
    }
}
