/*
 * User: sdorman
 * Date: Aug 9, 2005
 * Time: 9:12:15 AM
 *
 * (c) 2000-2005 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement. 
 */

package unittest.edit;

import edit.common.EDITDateTime;
import edit.common.EDITDate;

import junit.framework.TestCase;

import java.util.GregorianCalendar;
import java.util.Calendar;


public class TestEDITDateTime extends TestCase
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
        Calendar gc = new GregorianCalendar(2005, 7-1, 15, 13, 4, 37);
        long milliseconds = gc.getTimeInMillis();

        EDITDateTime edInt = new EDITDateTime(2005, 7, 15, 13, 4, 37);
        EDITDateTime edString = new EDITDateTime("2005", "7", "15", "13", "4", "37");
        EDITDateTime edEDITDate = new EDITDateTime(new EDITDateTime(2005, 7, 15, 13, 4, 37));
        EDITDateTime edMilliseconds = new EDITDateTime(milliseconds);
        EDITDateTime edFormattedString = new EDITDateTime("2005/07/15 13:04:37");
        EDITDateTime edEDITDateTimeString = new EDITDateTime(new EDITDate(2005, 7, 15), "13:04:37");

        assertEquals(edInt.getFormattedTime(), "13:04:37");
        assertEquals(edString.getFormattedTime(), "13:04:37");
        assertEquals(edEDITDate.getFormattedTime(), "13:04:37");
        assertEquals(edMilliseconds.getFormattedTime(), "13:04:37");
        assertEquals(edFormattedString.getFormattedTime(), "13:04:37");
        assertEquals(edEDITDateTimeString.getFormattedTime(), "13:04:37");

        assertEquals(edInt.getFormattedDateTime(), "2005/07/15 13:04:37");
        assertEquals(edString.getFormattedDateTime(), "2005/07/15 13:04:37");
        assertEquals(edEDITDate.getFormattedDateTime(), "2005/07/15 13:04:37");
        assertEquals(edMilliseconds.getFormattedDateTime(), "2005/07/15 13:04:37");
        assertEquals(edFormattedString.getFormattedDateTime(), "2005/07/15 13:04:37");
        assertEquals(edEDITDateTimeString.getFormattedDateTime(), "2005/07/15 13:04:37");
    }

    public void testGetEDITDate()
    {
        EDITDateTime ed = new EDITDateTime(2005, 7, 15, 13, 4, 37);

        EDITDate edDate = ed.getEDITDate();

        assertEquals(edDate.getFormattedDate(), "2005/07/15");
    }

    public void testGetHour()
    {
        EDITDateTime ed = new EDITDateTime(2005, 7, 15, 13, 4, 37);

        int hour = ed.getHour();

        assertEquals(hour, 13);
    }

    public void testGetMinute()
    {
        EDITDateTime ed = new EDITDateTime(2005, 7, 15, 13, 4, 37);

        int minute = ed.getMinute();

        assertEquals(minute, 4);
    }

    public void testGetSecond()
    {
        EDITDateTime ed = new EDITDateTime(2005, 7, 15, 13, 4, 37);

        int second = ed.getSecond();

        assertEquals(second, 37);
    }

    public void testGetFormattedHour()
    {
        EDITDateTime ed = new EDITDateTime(2005, 7, 15, 13, 4, 37);

        String formattedHour = ed.getFormattedHour();

        assertEquals(formattedHour, "13");
    }

    public void testGetFormattedMinute()
    {
        EDITDateTime ed = new EDITDateTime(2005, 7, 15, 13, 4, 37);

        String formattedMinute = ed.getFormattedMinute();

        assertEquals(formattedMinute, "04");
    }

    public void testGetFormattedSecond()
    {
        EDITDateTime ed = new EDITDateTime(2005, 7, 15, 13, 4, 37);

        String formattedSecond = ed.getFormattedSecond();

        assertEquals(formattedSecond, "37");
    }

    public void testGetFormattedTime()
    {
        EDITDateTime ed = new EDITDateTime(2005, 7, 15, 13, 4, 37);

        String formattedTime = ed.getFormattedTime();

        assertEquals(formattedTime, "13:04:37");
    }

    public void testGetFormattedDateTime()
    {
        EDITDateTime ed = new EDITDateTime(2005, 7, 15, 13, 4, 37);

        String formattedDateTime = ed.getFormattedDateTime();

        assertEquals(formattedDateTime, "2005/07/15 13:04:37");
    }

    public void testToString()
    {
        EDITDateTime ed = new EDITDateTime(2005, 7, 15, 13, 4, 37);

        String formattedDateTime = ed.toString();

        assertEquals(formattedDateTime, "2005/07/15 13:04:37");
    }

    public void testGetTimeInMilliseconds()
    {
        EDITDateTime ed = new EDITDateTime(2005, 7, 15, 13, 4, 37);

        long milliseconds = ed.getTimeInMilliseconds();

        assertEquals(milliseconds, 1121447077000L);
    }

    public void testNow()
    {
        EDITDateTime ed = new EDITDateTime();

        System.out.println("now = " + ed.getFormattedDateTime());
    }
}