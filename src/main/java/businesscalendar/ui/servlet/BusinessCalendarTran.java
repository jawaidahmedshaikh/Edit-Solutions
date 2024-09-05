/*
 * User: gfrosti
 * Date: Dec 14, 2004
 * Time: 3:37:53 PM
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package businesscalendar.ui.servlet;

import edit.portal.common.transactions.Transaction;
import edit.common.vo.BusinessDayVO;
import edit.common.*;
import edit.common.exceptions.EDITBusinessDayException;
import fission.global.AppReqBlock;
import fission.utility.Util;
import businesscalendar.business.BusinessCalendar;
import businesscalendar.component.BusinessCalendarComponent;


public class BusinessCalendarTran extends Transaction
{
    // Actions
    private static final String SHOW_BUSINESS_CALENDAR = "showBusinessCalendar";
    private static final String GENERATE_DEFAULT_BUSINESS_DAYS = "generateDefaultBusinessDays";
    private static final String SHOW_GENERATE_DEFAULT_BUSINESS_DAYS_DIALOG = "showGenerateDefaultBusinessDaysDialog";
    private static final String ADD_AS_BUSINESS_DAY = "addAsBusinessDay";
    private static final String REMOVE_AS_BUSINESS_DAY = "removeAsBusinessDay";
    private static final String SHOW_NEXT_YEARS_BUSINESS_DAYS = "showNextYearsBusinessDays";
    private static final String SHOW_PREVIOUS_YEARS_BUSINESS_DAYS = "showPreviousYearsBusinessDays";

    // Pages
    private static final String BUSINESS_CALENDAR = "/businesscalendar/jsp/businessCalendar.jsp";
    private static final String GENERATE_DEFAULT_BUSINESS_DAYS_DIALOG = "/businesscalendar/jsp/generateDefaultBusinessDaysDialog.jsp";


    /**
     * @param appReqBlock
     * @return
     * @throws Throwable
     * @see Transaction
     */
    public String execute(AppReqBlock appReqBlock) throws Throwable
    {
        String action = appReqBlock.getReqParm("action");

        String returnPage = null;

        if (action.equals(SHOW_BUSINESS_CALENDAR))
        {
            returnPage = showBusinessCalendar(appReqBlock);
        }
        else if (action.equals(GENERATE_DEFAULT_BUSINESS_DAYS))
        {
            returnPage = generateDefaultBusinessDays(appReqBlock);
        }
        else if (action.equals(SHOW_GENERATE_DEFAULT_BUSINESS_DAYS_DIALOG))
        {
            return showGenerateDefaultBusinessDaysDialog(appReqBlock);
        }
        else if (action.equals(ADD_AS_BUSINESS_DAY))
        {
            returnPage = addAsBusinessDay(appReqBlock);
        }
        else if (action.equals(REMOVE_AS_BUSINESS_DAY))
        {
            returnPage = removeAsBusinessDay(appReqBlock);
        }
        else if (action.equals(SHOW_NEXT_YEARS_BUSINESS_DAYS))
        {
            returnPage = showNextYearsBusinessDays(appReqBlock);
        }
        else if (action.equals(SHOW_PREVIOUS_YEARS_BUSINESS_DAYS))
        {
            returnPage = showPreviousYearsBusinessDays(appReqBlock);
        }
        else
        {
            String transaction = appReqBlock.getReqParm("transaction");

            throw new RuntimeException("Unrecognized Transaction/Action [" + transaction + " / " + action + "]");
        }

        return returnPage;
    }

    /**
     * Shows the previous year's BusinessDays (if any).
     * @param appReqBlock
     * @return
     */
    private String showPreviousYearsBusinessDays(AppReqBlock appReqBlock)
    {
        String activeYear = Util.initString(appReqBlock.getReqParm("activeYear"), null);

        activeYear = String.valueOf(Integer.parseInt(activeYear) - 1); // activeYear - 1

        appReqBlock.setReqParm("activeYear", activeYear);

        return showBusinessCalendar(appReqBlock);
    }

    /**
     * Shows the next year's BusinessDays (if any).
     * @param appReqBlock
     * @return
     */
    private String showNextYearsBusinessDays(AppReqBlock appReqBlock)
    {
        String activeYear = Util.initString(appReqBlock.getReqParm("activeYear"), null);

        activeYear = String.valueOf(Integer.parseInt(activeYear) + 1); // activeYear + 1

        appReqBlock.setReqParm("activeYear", activeYear);

        return showBusinessCalendar(appReqBlock);
    }

    /**
     * Marks the selected BusinessDays as inactive.
     *
     * @param appReqBlock
     * @return
     */
    private String removeAsBusinessDay(AppReqBlock appReqBlock)
    {
        String selectedBusinessDayPKs = Util.initString(appReqBlock.getReqParm("selectedBusinessDayPKs"), null);

        BusinessCalendar businessCalendar = new BusinessCalendarComponent();

        String[] businessDayPKs = Util.fastTokenizer(selectedBusinessDayPKs, ",");

        String responseMessage = null;

        for (int i = 0; i < businessDayPKs.length; i++)
        {
            businessCalendar.removeAsBusinessDay(Long.parseLong(businessDayPKs[i]));
        }

        responseMessage = "BusinessDay(s) Successfully Made Inactive";

        appReqBlock.getHttpServletRequest().setAttribute("responseMessage", responseMessage);

        return showBusinessCalendar(appReqBlock);
    }

    /**
     * Marks the selected BusinessDays as active.
     *
     * @param appReqBlock
     * @return
     */
    private String addAsBusinessDay(AppReqBlock appReqBlock)
    {
        String selectedBusinessDayPKs = Util.initString(appReqBlock.getReqParm("selectedBusinessDayPKs"), null);

        BusinessCalendar businessCalendar = new BusinessCalendarComponent();

        String[] businessDayPKs = Util.fastTokenizer(selectedBusinessDayPKs, ",");

        String responseMessage = null;

        try
        {
            for (int i = 0; i < businessDayPKs.length; i++)
            {
                businessCalendar.addAsBusinessDay(Long.parseLong(businessDayPKs[i]));
            }

            responseMessage = "BusinessDay(s) Successfully Made Active";
        }
        catch (EDITBusinessDayException e)
        {
            responseMessage = e.getMessage();
        }

        appReqBlock.getHttpServletRequest().setAttribute("responseMessage", responseMessage);

        return showBusinessCalendar(appReqBlock);
    }

    /**
     * Pops up the generate business days dialog.
     *
     * @param appReqBlock
     * @return
     */
    private String showGenerateDefaultBusinessDaysDialog(AppReqBlock appReqBlock)
    {
        String activeYear = Util.initString(appReqBlock.getReqParm("activeYear"), null);

        appReqBlock.getHttpServletRequest().setAttribute("year", activeYear);

        return GENERATE_DEFAULT_BUSINESS_DAYS_DIALOG;
    }

    /**
     * Generates default BusinessDays for the specified year.
     *
     * @param appReqBlock
     * @return
     */
    private String generateDefaultBusinessDays(AppReqBlock appReqBlock)
    {
        String year = Util.initString(appReqBlock.getReqParm("year"), null);

        BusinessCalendar businessCalendar = new BusinessCalendarComponent();

        businessCalendar.generateDefaultBusinessDays(Integer.parseInt(year));

        appReqBlock.setReqParm("activeYear", year);

        appReqBlock.getHttpServletRequest().setAttribute("responseMessage", "Default Business Days Successfully Generated");

        return showBusinessCalendar(appReqBlock);
    }

    /**
     * Returns the businss calendar page with all user-defined Business Days.
     *
     * @param appReqBlock
     * @return
     */
    private String showBusinessCalendar(AppReqBlock appReqBlock)
    {
        BusinessCalendar businessCalendar = new BusinessCalendarComponent();

        String activeYear = Util.initString(appReqBlock.getReqParm("activeYear"), null);

        int year = 0;

        if (activeYear == null)
        {
            year = new EDITDate().getYear();

            activeYear = String.valueOf(year);
        }
        else
        {
            year = Integer.parseInt(activeYear);
        }

        BusinessDayVO[] businessDayVOs = businessCalendar.findBusinessDaysInYear(year);

        if (businessDayVOs == null)
        {
            appReqBlock.getHttpServletRequest().setAttribute("responseMessage", "No Business Days Defined For Year [" + activeYear + "]");
        }

        appReqBlock.getHttpServletRequest().setAttribute("businessDayVOs", businessDayVOs);

        appReqBlock.getHttpServletRequest().setAttribute("activeYear", activeYear);

        return BUSINESS_CALENDAR;
    }
}
