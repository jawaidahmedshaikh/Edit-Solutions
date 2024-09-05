<%--
  Created by IntelliJ IDEA.
  User: gfrosti
  Date: Dec 8, 2004
  Time: 12:37:08 PM
  To change this template use File | Settings | File Templates.
--%>
<%-- ****************************** BEGIN Java Code ****************************** --%>
<%
    String responseMessage = (String) request.getAttribute("responseMessage");
%>
<%-- ****************************** End Java Code ****************************** --%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
<title>Stage Tables</title>
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
     * Triggers the process to update all reinsurance balances.
     */
    function stageTables()
    {
        try
        {
            f.processDate.value = formatDate(f.processDateMonth.value, f.processDateDay.value, f.processDateYear.value, true);

            sendTransactionAction("ReportingDetailTran", "stageTables", "main");
        }
        catch (e)
        {
            alert(e);
        }
    }

    /**
     * Returns to the Reporting Main screeen.
     */
    function showReportingMain()
    {
        sendTransactionAction("ReportingDetailTran", "showReportingMain", "main");
    }
</script>
<%-- ****************************** END JavaScript ****************************** --%>

</head>
<body class="mainTheme" onLoad="init()">
<form name="theForm" method="post" action="/PORTAL/servlet/RequestManager">

<%-- ****************************** BEGIN Form Data ****************************** --%>
<table class="formData" width="100%" border="0" cellspacing="0" cellpadding="5">
    <tr height="50%">
        <td colspan="2" nowrap>
            &nbsp; <!--Filler Row -->
        </td>
    </tr>

<%--    BEGIN Form Content --%>
    <tr>
        <td align="left" nowrap colspan="2">
            Process Date:&nbsp;

            <input type="text" name="processDateMonth" size="2" maxlength="2" value=""> /
            <input type="text" name="processDateDay" size="2" maxlength="2" value=""> /
            <input type="text" name="processDateYear" size="4" maxlength="4" value="">
        </td>
    </tr>
    <tr>
        <td align="right" nowrap width="50%">
            Run Stage Tables Or Cancel: &nbsp;<input type="button" value="Enter" onClick="stageTables()">
        </td>
        <td align="left" nowrap width="50%">
            <input type="button" value="Cancel" onClick="showReportingMain()">
        </td>
    </tr>
<%--    END Form Content --%>

    <tr height="50%">
        <td colspan="2">
            &nbsp; <!--Filler Row -->
        </td>
    </tr>
</table>
<%-- ****************************** END Form Data ****************************** --%>

<%-- ****************************** BEGIN Hidden Variables ****************************** --%>
<input type="hidden" name="transaction">
<input type="hidden" name="action">
<input type="hidden" name="processDate">
<%-- ****************************** END Hidden Variables ****************************** --%>

</form>
</body>
</html>