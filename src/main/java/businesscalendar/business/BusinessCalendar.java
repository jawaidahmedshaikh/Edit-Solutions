/*
 * User: gfrosti
 * Date: Dec 15, 2004
 * Time: 12:40:34 PM
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package businesscalendar.business;

import edit.common.vo.BusinessDayVO;
import edit.common.exceptions.EDITBusinessDayException;
import edit.common.EDITDate;
import businesscalendar.BusinessDay;


public interface BusinessCalendar
{
    /**
     * Adds the specified date as a new BusinessDay.
     * @see businesscalendar.BusinessCalendar#addAsBusinessDay(BusinessDay)
     * @param businessDayPK
     * @throws EDITBusinessDayException
     */
    void addAsBusinessDay(long businessDayPK) throws EDITBusinessDayException;

    /**
     * Removes the specified BusinessDay. The entry still exists, but is flagged as inactive.
     * @param businessDayPK
     */
    void removeAsBusinessDay(long businessDayPK);

    /**
     * Generates a default set of Business for Mon - Fri of the specified year.
     * @param year
     */
    void generateDefaultBusinessDays(int year);

    /**
     * Returns true if given Date is is BusinessDay
     * @param date
     * @return 
     */
    boolean isBusinessDay(EDITDate date);

    /**
     * Finds the set of all user-defined BusinessDays in the specified year.
     * @param year
     * @return
     */
    BusinessDayVO[] findBusinessDaysInYear(int year);
}
