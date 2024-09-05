<%@ page import="edit.common.vo.FeeVO,
                 edit.common.vo.FeeDescriptionVO,
                 edit.common.EDITBigDecimal,
                 edit.common.vo.ChargeCodeVO,
                 edit.common.EDITDate,
                 engine.Company,
                 security.Operator,
                 edit.portal.common.session.UserSession,
                 engine.ProductStructure,
                 java.util.*,
                 fission.utility.*"%>
<!--
 * User: dlataill
 * Date: Jul 19, 2005
 * Time: 1:3511:20 PM
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 -->
 <%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%-- ****************************** BEGIN Java Code ****************************** --%>
<%
    String batchDate = DateTimeUtil.formatEDITDateAsMMDDYYYY(new EDITDate());
    String dueDate = DateTimeUtil.formatEDITDateAsMMDDYYYY(new EDITDate());

    String batchTotal = "";
    String companyName = "";
    String groupNumber = "";

    // Getting CashBatch Filter values
    String status = (String) request.getAttribute("status");
//    String month = (String) request.getAttribute("month");
//    String day = (String) request.getAttribute("day");
//    String year = (String) request.getAttribute("year");
    String filterAmount = (String) request.getAttribute("filterAmount");
    String filterOperator = (String) request.getAttribute("filterOperator");

    String[] companies = (String[]) request.getAttribute("companies");
%>
<%-- ****************************** End Java Code ****************************** --%>

<html>
<head>
<title>Cash Batch Entry</title>
<link href="/PORTAL/common/css/EDITSolutions.css" rel="stylesheet" type="text/css">

<%-- ****************************** BEGIN JavaScript ****************************** --%>
<script src="/PORTAL/common/javascript/commonJavascriptFunctions.js"></script>
<script src="/PORTAL/common/javascript/formatDateFunctions.js"></script>
<script src="/PORTAL/common/javascript/calendarSelector.js"></script>

<script>

    var f = null;

    /**
     * Called after the body of the page is loaded.
     */
    function init()
    {
        f = document.cashBatchEntryForm;

        f.batchDate.focus();

        formatCurrency();
    }

    function createCashBatch()
    {
        try
        {
            if (!valueIsEmpty(f.amount.value) && !valueIsZero(f.amount.value))
            {
                if (!selectElementIsEmpty(f.company))
                {
                    sendTransactionAction("EventAdminTran", "createCashBatch", "main");
                    closeWindow();
                }
                else
                {
                    alert ("Company Must Be Selected");
                }
            }
            else
            {
                alert("Batch Total Required");
            }
        }
        catch (e)
        {
            alert("Batch Date Required");
        }
    }

</script>
<%-- ****************************** END JavaScript ****************************** --%>

</head>
<body class="mainTheme" onLoad="init()">
<form name="cashBatchEntryForm" method="post" action="/PORTAL/servlet/RequestManager">

<%-- ****************************** BEGIN Form Data ****************************** --%>
<table class="formData" width="100%" border="0" cellspacing="0" cellpadding="5">
<%--    BEGIN Form Content --%>
    <tr>
        <td align="right" nowrap>Batch Date:&nbsp;</td>
        <td align="left" nowrap>
          <input type="text" name="batchDate" size='10' maxlength="10" value="<%= batchDate %>" onKeyUp="DateFormat(this,this.value,event,false)" onBlur="DateFormat(this,this.value,event,true)">
          <a href="javascript:show_calendar('f.batchDate', f.batchDate.value);"><img src="/PORTAL/common/images/calendarIcon.gif" width="16" height="16" border="0" alt="Select a date from the calendar"></a>
        </td>
    </tr>
    <tr>
        <td align="right" nowrap>Batch Total:&nbsp;</td>
        <td align="left" nowrap>
            <input type="text" name="amount" size="20" maxlength="20" value="<%= batchTotal %>" CURRENCY>
        </td>
    </tr>
    <tr>
        <td align="right" nowrap>Company:&nbsp;</td>
        <td align="left" nowrap>
          <select name="company">
           <option>Please Select</option>
            <%
                if (companies != null)
                {
                  for(int i = 0; i < companies.length; i++)
                  {
                      String company = companies[i];

                      if (companyName.equals(company)) {

                         out.println("<option selected name=\"id\" value=\"" + company + "\">" + company + "</option>");
                      }
                      else  {

                         out.println("<option name=\"id\" value=\"" + company + "\">" + company + "</option>");
                      }
                  }
                }
            %>
          </select>
        </td>
    </tr>
    <tr>
        <td align="right" nowrap>Group Number:&nbsp;</td>
        <td align="left" nowrap>
            <input type="text" name="groupNumber" size="20" maxlength="20" value="<%= groupNumber %>">
        </td>
    </tr>
    <tr>
        <td align="right" nowrap>Due Date:&nbsp;</td>
        <td align="left" nowrap>
          <input type="text" name="dueDate" size='10' maxlength="10" value="<%= dueDate %>" onKeyUp="DateFormat(this,this.value,event,false)" onBlur="DateFormat(this,this.value,event,true)">
          <a href="javascript:show_calendar('f.dueDate', f.dueDate.value);"><img src="/PORTAL/common/images/calendarIcon.gif" width="16" height="16" border="0" alt="Select a date from the calendar"></a>
        </td>
    </tr>
<%--    END Form Content --%>

    <tr class="filler">
        <td colspan="4">
            &nbsp; <!--Filler Row -->
        </td>
    </tr>
</table>
<%-- ****************************** END Form Data ****************************** --%>

<br>

<%-- Buttons --%>

<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
        <td align="right" width="33%">
            <input type="button" value="Create" onClick="createCashBatch()">
            <input type="button" value="Cancel" onClick="closeWindow()">
        </td>
    </tr>
</table>
<%-- ****************************** END Summary Area ****************************** --%>

<%-- ****************************** BEGIN Hidden Variables ****************************** --%>
    <input type="hidden" name="transaction">
    <input type="hidden" name="action">

    <%-- values for CashBatch filtering --%>
    <input type="hidden" name="status" value="<%= status %>">
<%--    <input type="hidden" name="month" value="<%= month %>">--%>
<%--    <input type="hidden" name="day" value="<%= day %>">--%>
<%--    <input type="hidden" name="year" value="<%= year %>">--%>
    <input type="hidden" name="filterAmount" value="<%= filterAmount %>">
    <input type="hidden" name="filterOperator" value="<%= filterOperator %>">
<%-- ****************************** END Hidden Variables ****************************** --%>

</form>
</body>
</html>