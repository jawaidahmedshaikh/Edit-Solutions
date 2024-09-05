/*
 * User: gfrosti
 * Date: Dec 15, 2004
 * Time: 12:42:00 PM
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package businesscalendar.component;

import businesscalendar.business.BusinessCalendar;
import businesscalendar.BusinessDay;
import edit.common.EDITDate;
import edit.common.vo.BusinessDayVO;
import edit.common.exceptions.EDITBusinessDayException;
import edit.services.db.CRUDEntityImpl;


public class BusinessCalendarComponent implements BusinessCalendar
{
    /**
     * @see businesscalendar.business.BusinessCalendar#addAsBusinessDay(long)
     * @param businessDayPK
     */
    public void addAsBusinessDay(long businessDayPK) throws EDITBusinessDayException
    {
        BusinessDay businessDay = new BusinessDay(businessDayPK);

        new businesscalendar.BusinessCalendar().addAsBusinessDay(businessDay);
    }

    /**
     * @see businesscalendar.business.BusinessCalendar#removeAsBusinessDay(long)
     * @param businessDayPK
     */
    public void removeAsBusinessDay(long businessDayPK)
    {
        BusinessDay businessDay = new BusinessDay(businessDayPK);

        new businesscalendar.BusinessCalendar().removeAsBusinessDay(businessDay);
    }

    /**
     * @see businesscalendar.business.BusinessCalendar#generateDefaultBusinessDays(int)
     * @param year
     */
    public void generateDefaultBusinessDays(int year)
    {
        new businesscalendar.BusinessCalendar().generateDefaultBusinessDays(year);
    }

    public boolean isBusinessDay(EDITDate date)
    {
        boolean isBusinessDay = false;

        BusinessDay businessDay = BusinessDay.findBy_BusinessDate_ActiveInd(date.getFormattedDate());

        if (businessDay != null)
        {
            isBusinessDay = true;
        }

        return isBusinessDay;
    }

    /**
     * @see businesscalendar.business.BusinessCalendar#findBusinessDaysInYear(int)
     * @param year
     * @return
     */
    public BusinessDayVO[] findBusinessDaysInYear(int year)
    {
        BusinessDay[] businessDays = BusinessDay.findBy_Year(year);

        return (BusinessDayVO[]) CRUDEntityImpl.mapEntityToVO(businessDays, BusinessDayVO.class);
    }
}
