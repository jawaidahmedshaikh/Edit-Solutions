package businesscalendar;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;


/**
 * Created by IntelliJ IDEA.
 * User: gfrosti
 * Date: Dec 13, 2004
 * Time: 1:23:26 PM
 * To change this template use File | Settings | File Templates.
 */
public class BusinessMonth implements Comparable
{
    private static Set businessDays;
    private int year;
    private int month;

    public BusinessMonth(int year, int month)
    {
        this.year = year;

        this.month = month;

        businessDays = new HashSet();
    }

    /**
     * Adder.
     * @param businessDay
     */
    public void addBusinessDay(BusinessDay businessDay)
    {
        businessDays.add(businessDay);
    }

    /**
     * Returns true if the year/month of this BusinessMonth equal the year/month of the specified BusinessMonth.
     * @param o
     * @return
     */
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }

        if (!(o instanceof BusinessMonth))
        {
            return false;
        }

        final BusinessMonth businessMonth = (BusinessMonth) o;

        if (month != businessMonth.month)
        {
            return false;
        }

        if (year != businessMonth.year)
        {
            return false;
        }

        return true;
    }

    /**
     * @see Object#hashCode()
     * @return
     */
    public int hashCode()
    {
        int result;
        result = year;
        result = (29 * result) + month;

        return result;
    }

    /**
     * Getter.
     * @return
     */
    public int getYear()
    {
        return year;
    }

    /**
     * Getter.
     * @return
     */
    public int getMonth()
    {
        return month;
    }

    /**
     * Returns the last user-defined BusinessDay of the month, or null.
     * @return
     */
    public BusinessDay getLastBusinessDayOfMonth()
    {
        BusinessDay lastBusinessDay = null;

        // Uses a linear search - this should be a sufficient search algorithm.
        for (Iterator iterator = businessDays.iterator(); iterator.hasNext();)
        {
            BusinessDay currentBusinessDay = (BusinessDay) iterator.next();

            int currentYear = currentBusinessDay.getBusinessDate().getYear();

            int currentMonth = currentBusinessDay.getBusinessDate().getMonth();

            if ((currentYear == year) && (currentMonth == month)) // A direct match.
            {
                lastBusinessDay = currentBusinessDay;
            }
            else // The business days are ordered - a match won't be possible once we pass the specified year/month.
            {
                if ((currentYear > year) || ((currentYear == year) && (currentMonth > month)))
                {
                    break;
                }
            }
        }

        if (lastBusinessDay == null)
        {
            throw new RuntimeException("A Last Business Day With Year:Month [" + year + ":" + month + "] Does Not Exist");
        }

        return lastBusinessDay;
    }

    /**
     * Returns the set of all Business Days defined within this month.
     * @return
     */
    public BusinessDay[] getBusinessDays()
    {
        return (BusinessDay[]) businessDays.toArray(new BusinessDay[businessDays.size()]);
    }

    /**
     * @see Comparable#compareTo(Object)
     * @param o
     * @return
     */
    public int compareTo(Object o)
    {
        int compareValue = 0;

        if (!(o instanceof BusinessMonth))
        {
            throw new RuntimeException("The Specified Object Must Be Of Type [BusinessMonth] To Do A Comparison");
        }

        BusinessMonth specifiedBusinessMonth = (BusinessMonth) o;

        int specifiedYear = specifiedBusinessMonth.getYear();

        int specifiedMonth = specifiedBusinessMonth.getMonth();

        if (getYear() < specifiedYear)
        {
            compareValue = -1;
        }
        else if (getYear() == specifiedYear)
        {
            if (getMonth() < specifiedMonth)
            {
                compareValue = -1;
            }
            else if (getMonth() == specifiedMonth)
            {
                compareValue = 0;
            }
            else if (getMonth() > specifiedMonth)
            {
                compareValue = 1;
            }
        }
        else if (getYear() > specifiedYear)
        {
            compareValue = 1;
        }

        return compareValue;
    }
}
