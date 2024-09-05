<%@ page import="edit.common.vo.FeeVO,
                 fission.utility.Util,
                 edit.common.vo.FeeDescriptionVO,
                 edit.common.EDITBigDecimal,
                 java.util.Map,
                 java.util.HashMap,
                 edit.common.vo.ChargeCodeVO,
                 edit.common.EDITDate"%>
<!--
 * User: dlataill
 * Date: Sep 2, 2005
 * Time: 1:3511:20 PM
 *
 * (c) 2000-2005 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 -->
 <%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
<title>Cash Batch Filter</title>
<link href="/PORTAL/common/css/EDITSolutions.css" rel="stylesheet" type="text/css">

<%-- ****************************** BEGIN JavaScript ****************************** --%>
<script src="/PORTAL/common/javascript/commonJavascriptFunctions.js"></script>

<script>

    var f = null;

    /**
     * Called after the body of the page is loaded.
     */
    function init()
    {
        f = document.cashBatchFilterForm;

        formatCurrency();
    }

    function filterCashBatch()
    {
        try
        {
            if (!valueIsEmpty(f.month.value) || !valueIsEmpty(f.day.value) || !valueIsEmpty(f.year.value))
            {
                formatDate(f.month.value, f.day.value, f.year.value, true);
            }
        }
        catch (e)
        {
            alert("Batch Date Entered Is Invalid - Please Re-enter");
        }

        if (valueIsEmpty(f.status.value) &&
            valueIsEmpty(f.year.value) &&
            (valueIsEmpty(f.filterAmount.value) ||
             valueIsZero(f.filterAmount.value)) &&
            valueIsEmpty(f.filterOperator.value))
        {
            alert("Please Select A Value for Cash Batch Filter");
        }
        else if (!valueIsEmpty(f.status.value) &&
                 (!valueIsEmpty(f.year.value) ||
                  (!valueIsEmpty(f.filterAmount.value) &&
                   !valueIsZero(f.filterAmount.value)) ||
                  !valueIsEmpty(f.filterOperator.value)))
        {
            alert("Only One Filter Value Can Be Selected");
        }
        else if (!valueIsEmpty(f.year.value) &&
                 (!valueIsEmpty(f.status.value) ||
                  (!valueIsEmpty(f.filterAmount.value) &&
                   !valueIsZero(f.filterAmount.value)) ||
                  !valueIsEmpty(f.filterOperator.value)))
        {
            alert("Only One Filter Value Can Be Selected");
        }
        else if ((!valueIsEmpty(f.filterAmount.value) &&
                  !valueIsZero(f.filterAmount.value)) &&
                 (!valueIsEmpty(f.year.value) ||
                  !valueIsEmpty(f.status.value) ||
                  !valueIsEmpty(f.filterOperator.value)))
        {
            alert("Only One Filter Value Can Be Selected");
        }
        else if (!valueIsEmpty(f.filterOperator.value) &&
                 (!valueIsEmpty(f.status.value) ||
                  (!valueIsEmpty(f.filterAmount.value) &&
                   !valueIsZero(f.filterAmount.value)) ||
                  !valueIsEmpty(f.year.value)))
        {
            alert("Only One Filter Value Can Be Selected");
        }
        else
        {
            sendTransactionAction("EventAdminTran", "filterCashBatch", "main");
            window.close();
        }
    }

</script>
<%-- ****************************** END JavaScript ****************************** --%>

</head>
<body class="mainTheme" onLoad="init()">
<form name="cashBatchFilterForm" method="post" action="/PORTAL/servlet/RequestManager">

<%-- ****************************** BEGIN Form Data ****************************** --%>
<table class="formData" width="100%" border="0" cellspacing="0" cellpadding="5">
<%--    BEGIN Form Content --%>
    <tr>
      <td align="right" nowrap>Status:&nbsp;</td>
      <td align="left" nowrap>
        <select name="status">
        <%
          out.println("<option name=\"id\" value=\"\">" + "Please Select" + "</option>");
          out.println("<option name=\"id\" value=\"All\">" + "All" + "</option>");
          out.println("<option name=\"id\" value=\"Pending\">" + "Pending" + "</option>");
          out.println("<option name=\"id\" value=\"Released\">" + "Released" + "</option>");
        %>
        </select>
      </td>
    </tr>
    <tr>
        <td align="right" nowrap>Date:&nbsp;</td>
        <td align="left" nowrap>
            <input name="month" type="text" size="2" maxlength="2">
            /
            <input name="day" type="text" size="2" maxlength="2">
            /
            <input name="year" type="text" size="4" maxlength="4">
        </td>
    </tr>
    <tr>
        <td align="right" nowrap>Amount:&nbsp;</td>
        <td align="left" nowrap>
            <input type="text" name="filterAmount" size="20" maxlength="20" value="" CURRENCY>
        </td>
    </tr>
    <tr>
        <td align="right" nowrap>Operator:&nbsp;</td>
        <td align="left" nowrap>
            <input type="text" name="filterOperator" size="15" maxlength="15" value="">
        </td>
    </tr>
<%--    END Form Content --%>

    <tr class="filler">
        <td colspan="2">
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
            <input type="button" value="Filter" onClick="filterCashBatch()">
            <input type="button" value="Cancel" onClick="closeWindow()">
        </td>
    </tr>
</table>
<%-- ****************************** END Summary Area ****************************** --%>

<%-- ****************************** BEGIN Hidden Variables ****************************** --%>
<input type="hidden" name="transaction">
<input type="hidden" name="action">
<%-- ****************************** END Hidden Variables ****************************** --%>

</form>
</body>
</html>