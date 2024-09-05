<%@ page import="edit.common.vo.BusinessDayVO,
                 fission.utility.Util,
                 java.text.SimpleDateFormat,
                 java.util.Date,
                 edit.common.EDITDate,
                 java.util.Calendar"%>
 <%--
  Created by IntelliJ IDEA.
  User: gfrosti
  Date: Nov 12, 2004
  Time: 3:08:17 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%-- ****************************** BEGIN Java Code ****************************** --%>
<%
    String responseMessage = (String) request.getAttribute("responseMessage");

    BusinessDayVO[] businessDayVOs = (BusinessDayVO[]) request.getAttribute("businessDayVOs");

    String activeYear = (String) request.getAttribute("activeYear");
%>
<%!
    /**
     * Sorts the VOs in their natural ascending order.
     * @param businessDayVOs
     */
    private BusinessDayVO[] sortBusinessDays(BusinessDayVO[] businessDayVOs)
    {
        BusinessDayVO[] sortedDays = null;

        sortedDays = (BusinessDayVO[]) Util.sortObjects(businessDayVOs, new String[]{"getBusinessDate"});

        return sortedDays;
    }

    /**
     * Gets the text representation of a month.
     * @param editDate
     * @return
     */
    private String getMonth(EDITDate editDate)
    {
        String monthAsString = null;
        
        int month = editDate.getMonth();
        
        switch(month)
        {
            case 1:
                monthAsString = "January";
                break;
            case 2:
                monthAsString = "February";
                break;
            case 3:
                monthAsString = "March";
                break;
            case 4:
                monthAsString = "April";
                break;
            case 5:
                monthAsString = "May";
                break;
            case 6:
                monthAsString = "June";
                break;
            case 7:
                monthAsString = "July";
                break;
            case 8:
                monthAsString = "August";
                break;
            case 9:
                monthAsString = "September";
                break;
            case 10:
                monthAsString = "October";
                break;
            case 11:
                monthAsString = "November";
                break;
            case 12:
                monthAsString = "December";
                break;
        }

        return monthAsString;
    }

    /**
     * Gets the text representation of the day.
     * @param editDate
     * @return
     */
    private String getDayOfWeek(EDITDate editDate)
    {
        String dayOfWeekAsString = null;

        Calendar c = Calendar.getInstance();

        int year = editDate.getYear();

        int month = editDate.getMonth() - 1;

        int day = editDate.getDay();

        c.set(year, month, day);

        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);

        switch(dayOfWeek)
        {
            case Calendar.SUNDAY:
                dayOfWeekAsString = "Sunday";
                break;
            case Calendar.MONDAY:
                dayOfWeekAsString = "Monday";
                break;
            case Calendar.TUESDAY:
                dayOfWeekAsString = "Tuesday";
                break;
            case Calendar.WEDNESDAY:
                dayOfWeekAsString = "Wednesday";
                break;
            case Calendar.THURSDAY:
                dayOfWeekAsString = "Thursday";
                break;
            case Calendar.FRIDAY:
                dayOfWeekAsString = "Friday";
                break;
            case Calendar.SATURDAY:
                dayOfWeekAsString = "Saturday";
                break;
        }

        return dayOfWeekAsString;
    }


    /**
     * True if the specified date is a Friday.
     * @param editDate
     * @return
     */
    private boolean isFriday(EDITDate editDate)
    {
        boolean isFriday = false;

        Calendar c = Calendar.getInstance();
        
        c.set(editDate.getYear(), editDate.getMonth() - 1, editDate.getDay());
        
        if (c.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY)
        {
            isFriday = true;
        }

        return isFriday;
    }
    
    /**
     * True if this is the last date of the month.
     * @param previousEDITDate
     * @param currentEDITDate
     * @return
     */ 
    private boolean isNextMonth(EDITDate previousEDITDate, EDITDate currentEDITDate)
    {
        boolean isNextMonth = false;

        int previousMonth = previousEDITDate.getMonth();

        int currentMonth = currentEDITDate.getMonth();

        if (previousMonth != currentMonth)
        {
            isNextMonth = true;
        }

        return isNextMonth;
    }
%>
<%-- ****************************** End Java Code ****************************** --%>

<html>
<head>
<title>Business Calendar</title>
<link href="/PORTAL/common/css/EDITSolutions.css" rel="stylesheet" type="text/css">

<%-- ****************************** BEGIN JavaScript ****************************** --%>
<script src="/PORTAL/common/javascript/commonJavascriptFunctions.js"></script>

<script>

    var f = null;

    var responseMessage = "<%= responseMessage %>";

    /**
     * Called after the body of the page is loaded.
     */
    function init()
    {
        f = document.theForm;

        checkForResponseMessage();
    }

    /**
     * Opens dialog for which to generate default Business Days.
     */
    function showGenerateDefaultBusinessDaysDialog()
    {
        var width = getScreenWidth() * 0.20;

        var height = getScreenHeight() * 0.20;

        openDialog("defaultBusinessDaysDialog", null, width, height);

        sendTransactionAction("BusinessCalendarTran", "showGenerateDefaultBusinessDaysDialog", "defaultBusinessDaysDialog");
    }

    /**
     * Marks the selected BusinessDays as active.
     */
    function addAsBusinessDay()
    {
        var selectedRowIds = getSelectedRowIds("businessDaySummary");

        if (valueIsEmpty(selectedRowIds))
        {
            alert("Business Day(s) Required");
        }
        else
        {
            f.selectedBusinessDayPKs.value = selectedRowIds;

            sendTransactionAction("BusinessCalendarTran", "addAsBusinessDay", "main");
        }
    }

    /**
     * Marks the selected BusinessDays as inactive.
     */
    function removeAsBusinessDay()
    {
        var selectedRowIds = getSelectedRowIds("businessDaySummary");

        if (valueIsEmpty(selectedRowIds))
        {
            alert("Business Day(s) Required");
        }
        else
        {
            f.selectedBusinessDayPKs.value = selectedRowIds;

            sendTransactionAction("BusinessCalendarTran", "removeAsBusinessDay", "main");
        }
    }

    /**
     * Shows the next year's BusinessDays (if any).
     */
    function showNextYearsBusinessDays()
    {
        sendTransactionAction("BusinessCalendarTran", "showNextYearsBusinessDays", "main");
    }

    /**
     * Shows the previous year's BusinessDays (if any).
     */
    function showPreviousYearsBusinessDays()
    {
        sendTransactionAction("BusinessCalendarTran", "showPreviousYearsBusinessDays", "main");
    }

</script>
<%-- ****************************** END JavaScript ****************************** --%>

</head>
<body class="mainTheme" onLoad="init()">
<form name="theForm" method="post" action="/PORTAL/servlet/RequestManager">

<%-- ****************************** BEGIN Summary Area ****************************** --%>
<%--Buttons--%>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
        <td align="left" width="33%">
            <input type="button" value="  Add  " onClick="addAsBusinessDay()">
            <input type="button" value=" Delete" onClick="removeAsBusinessDay()">
            <input type="button" value="Generate" onClick="showGenerateDefaultBusinessDaysDialog()">
        </td>
        <td width="33%">
            <span class="tableHeading">Business Days</span>
        </td>
        <td align="right" width="33%">
            Current Year:&nbsp;<%= activeYear %>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <input type="button" value="<-- Year" onClick="showPreviousYearsBusinessDays()">
            <input type="button" value="Year -->" onClick="showNextYearsBusinessDays()">
        </td>
    </tr>
</table>

<%-- Column Headings --%>
<table class="summary" width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr class="heading">
        <td width="25%" nowrap>
            Year
        </td>
        <td width="25%" nowrap>
            Month
        </td>
        <td width="25%" nowrap>
            Day
        </td>
        <td width="25%" nowrap>
            Day Of Week
        </td>
    </tr>
</table>

<%--Summary--%>
<span class="scrollableContent" style="border-style:solid; border-width:1;  position:relative; width:100%; height:90%; top:0; left:0;">
    <table id="businessDaySummary" class="summary" width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
<%
    if (businessDayVOs != null) // Test for the existence of the target VOs.
    {
        businessDayVOs = sortBusinessDays(businessDayVOs);

        EDITDate previousEDITDate = null;

        for (int i = 0; i < businessDayVOs.length; i++) // Loop through the target VOs.
        {
            BusinessDayVO currentBusinessDayVO = businessDayVOs[i];

            long currentBusinessDayPK = currentBusinessDayVO.getBusinessDayPK();

            String businessDate = currentBusinessDayVO.getBusinessDate();

            EDITDate currentEDITDate = new EDITDate(businessDate);

            if (previousEDITDate == null)
            {
                previousEDITDate = currentEDITDate;
            }

            int currentYear = currentEDITDate.getYear();
            
            String currentMonth = getMonth(currentEDITDate);
            
            int currentDay = currentEDITDate.getDay();
            
            String currentDayOfWeek = getDayOfWeek(currentEDITDate);

            String activeInd = currentBusinessDayVO.getActiveInd();

            boolean isActive = activeInd.equals("Y")?true:false;

            boolean isSelected = false;

            boolean isAssociated = false;

            String className = null;

            String borderStyle = "";

            if (isFriday(currentEDITDate))
            {
                borderStyle = "border-bottom:1px solid #008000";
            }

            if (isNextMonth(previousEDITDate, currentEDITDate))
            {
                borderStyle = "border-top:5px solid #000000";
            }

            if (isActive)
            {
                isAssociated = true;

                className = "associated";
            }
            else
            {
                className = "default";
            }
%>
        <tr  style="border-bottom:1px solid #000000" class="<%= className %>" id="<%= currentBusinessDayPK %>" isAssociated="<%= isAssociated %>" isSelected="<%= isSelected %>" onMouseOver="highlightRow()" onMouseOut="unhighlightRow()" onClick="selectRow(true)">
            <td height="25px" width="25%" nowrap style="<%= borderStyle %>">
                <font face="" size="4"><%= currentYear %> </font>
            </td>
            <td height="25px" width="25%" nowrap style="<%= borderStyle %>">
                <font face="" size="4"><%= currentMonth %> </font>
            </td>
            <td height="25px" width="25%" nowrap style="<%= borderStyle %>">
                <font face="" size="4"><%= currentDay %> </font>
            </td>
            <td height="25px" width="25%" nowrap style="<%= borderStyle %>">
                <font face="" size="4"><%= currentDayOfWeek %> </font>
            </td>
        </tr>
<%
            previousEDITDate = currentEDITDate;
        }// end for
    } // end if
%>
        <tr class="filler">
            <td colspan="4">
                &nbsp;
            </td>
        </tr>
    </table>
</span>
<%-- ****************************** END Summary Area ****************************** --%>

<%-- ****************************** BEGIN Hidden Variables ****************************** --%>
<input type="hidden" name="transaction">
<input type="hidden" name="action">
<input type="hidden" name="activeYear" value="<%= activeYear %>">
<input type="hidden" name="selectedBusinessDayPKs">
<%-- ****************************** END Hidden Variables ****************************** --%>




</form>
</body>
</html>