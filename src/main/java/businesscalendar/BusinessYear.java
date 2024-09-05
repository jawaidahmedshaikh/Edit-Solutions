/*
 * User: gfrosti
 * Date: Dec 13, 2004
 * Time: 1:23:26 PM
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package businesscalendar;

import edit.common.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;



public class BusinessYear
{
    private int year;
    private static final int[] WEEKDAYS = 
    {
        Calendar.MONDAY,
        Calendar.TUESDAY,
        Calendar.WEDNESDAY,
        Calendar.THURSDAY,
        Calendar.FRIDAY
    };

    public BusinessYear(int year)
    {
        this.year = year;
    }

    /**
     * Generates Business Days for every weekday of the calendar year. Previously defined BusinessDays are deleted.
     */
    public void generateDefaultBusinessDays()
    {
        deleletePreviousBusinessDays();

        BusinessDay[] businessDaysInYear = generateDefaultBusinessDaysInYear();

        for (int i = 0; i < businessDaysInYear.length; i++)
        {
            BusinessDay businessDay = businessDaysInYear[i];

            businessDay.save();
        }
    }

    /**
     * Cycles through this year defaulting any weekday to a BusinessDay.
     * @return
     */
    private BusinessDay[] generateDefaultBusinessDaysInYear()
    {
        Calendar c = Calendar.getInstance();

        List businessDays = new ArrayList();

        int currentYear = year;

        int currentMonth = 0;

        int currentDay = 1;

        c.set(year, currentMonth, currentDay);

        while (c.get(Calendar.YEAR) == year)
        {
            if (isWeekday(c))
            {
                BusinessDay businessDay = new BusinessDay(new EDITDate(currentYear, currentMonth + 1, currentDay));

                businessDays.add(businessDay);
            }

            c.add(Calendar.DATE, 1);

            currentYear = c.get(Calendar.YEAR);

            currentMonth = c.get(Calendar.MONTH);

            currentDay = c.get(Calendar.DATE);
        }

        return (BusinessDay[]) businessDays.toArray(new BusinessDay[businessDays.size()]);
    }

    private boolean isWeekday(Calendar c)
    {
        boolean isWeekday = false;

        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);

        for (int i = 0; i < WEEKDAYS.length; i++)
        {
            int weekday = WEEKDAYS[i];

            if (dayOfWeek == weekday)
            {
                isWeekday = true;

                break;
            }
        }

        return isWeekday;
    }

    /**
     * Deletes any previously defined BusinessDays for this calendar year.
     */
    private void deleletePreviousBusinessDays()
    {
        EDITDate beginDate = new EDITDate(year + "", EDITDate.DEFAULT_MIN_MONTH, EDITDate.DEFAULT_MIN_DAY);

        EDITDate endDate = new EDITDate(year + "", EDITDate.DEFAULT_MAX_MONTH, EDITDate.DEFAULT_MAX_DAY);

        BusinessDay[] businessDays = BusinessDay.findBy_Range_Inclusive(beginDate, endDate);

        if (businessDays != null)
        {
            for (int i = 0; i < businessDays.length; i++)
            {
                BusinessDay businessDay = businessDays[i];

                businessDay.delete();
            }
        }
    }
}
