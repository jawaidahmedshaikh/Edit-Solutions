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

	String[]  companyNames = (String[]) request.getAttribute("companyNames");
%>
<%-- ****************************** End Java Code ****************************** --%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
<title>Reinsurance Update Balances</title>
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
    function updateReinsuranceBalances()
    {
        sendTransactionAction("ReinsuranceTran", "updateReinsuranceBalances", "main");
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
<%--        <td align="right" nowrap width="25%">--%>
<%--            Company:&nbsp;--%>
<%--        </td>--%>
<%--        <td align="left" nowrap width="25%">--%>
<%--              <select name="companyName">--%>
<%--              <option selected value="">--%>
<%--                 Please Select:&nbsp;--%>
<%--              </option>--%>
<%--            <option value="All"> All </option>--%>
<%--              <%--%>
<%--                  if (companyNames != null)--%>
<%--                  {--%>
<%--                      for(int i = 0; i < companyNames.length; i++)--%>
<%--                      {--%>
<%--                          out.println("<option name=\"id\" value=\"" + companyNames[i] + "\">" + companyNames[i] + "</option>");--%>
<%--                      }--%>
<%--                  }--%>
<%--              %>--%>
<%--              </select>--%>
<%--        </td>--%>
        <td align="right" nowrap width="50%">
            Run Reinsurance Balances Or Cancel:&nbsp;<input type="button" value="Enter" onClick="updateReinsuranceBalances()">
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
<%-- ****************************** END Hidden Variables ****************************** --%>

</form>
</body>
</html>