<%@ page import="edit.common.vo.LogEntryCollectionVO,
                 edit.common.vo.LogEntryVO,
                 edit.common.vo.LogContextVO,
                 edit.common.vo.LogContextEntryVO"%>
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
    LogEntryCollectionVO logEntryCollectionVO = (LogEntryCollectionVO) request.getAttribute("logEntryCollectionVO");

    String logName = (String) request.getAttribute("logName");
%>
<%!
    /**
     * Searches the array for the contextValue associated witht the specified contextName.
     * @param logContextEntryVOs
     * @param entryName
     * @return
     */
    private String getContextValue(LogContextEntryVO[] logContextEntryVOs, String entryName)
    {
        String contextValue = null;

        for (int i = 0; i < logContextEntryVOs.length; i++)
        {
            LogContextEntryVO currentLogContextEntryVO = logContextEntryVOs[i];

            String currentEntryName = currentLogContextEntryVO.getEntryName();

            if (currentEntryName.equalsIgnoreCase(entryName))
            {
                contextValue = currentLogContextEntryVO.getEntryValue();

                break;
            }
        }

        return contextValue;
    }
%>
<%-- ****************************** End Java Code ****************************** --%>

<html>
<head>
<title><%= logName %></title>
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
        f = document.theForm;
    }

    /**
     * Opens a dialog to show Stack/Message traces for the log entry.
     */
    function showTrace()
    {
        var width = getScreenWidth();

        var height = getScreenHeight() * 0.90;

        openDialog("stackTraceDialog", null, width, height);

        sendTransactionAction("LoggingAdminTran", "showTrace", "stackTraceDialog");
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
            &nbsp;
        </td>
        <td width="33%">
            <span class="tableHeading"><%= logName %></span>
        </td>
        <td align="right" width="33%">
            <a href="javascript:window.print()"><img src="../common/images/print.gif" alt="Print Report" border="0"></img></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        </td>
    </tr>
</table>

<%-- Column Headings --%>
<table class="summary" width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr class="heading">
        <td width="15%" nowrap>
            Operator
        </td>
        <td width="15%" nowrap>
            ContractNumber
        </td>
        <td width="15%" nowrap>
            PolicyEffDate
        </td>
        <td width="15%" nowrap>
            Severity
        </td>
        <td width="40%" nowrap>
            Error
        </td>
    </tr>
</table>

<%--Summary--%>
<span class="scrollableContent" style="border-style:solid; border-width:1; position:relative; width:100%; height:90%; top:0; left:0">
    <table id="tableSummary" class="summary" width="100%" height="100%" border="0" cellspacing="0" cellpadding="5">
<%
    if (logEntryCollectionVO != null) // Test for the existence of the target VOs.
    {
        LogEntryVO[] logEntryVOs = logEntryCollectionVO.getLogEntryVO();

        for (int i = 0; i < logEntryVOs.length; i++) // Loop through the target VOs.
        {
            LogEntryVO currentLogEntryVO = logEntryVOs[i];

            LogContextEntryVO[] currentLogContextEntryVOs = currentLogEntryVO.getLogContextEntryVO();

            String operator = getContextValue(currentLogContextEntryVOs, "Operator");

            String contractNumber = getContextValue(currentLogContextEntryVOs, "ContractNumber");

            String policyEffDate = getContextValue(currentLogContextEntryVOs, "EffectiveDate");

            String severity = getContextValue(currentLogContextEntryVOs, "Severity");

            String error = currentLogEntryVO.getMessage();

            String className = "default";
%>
        <tr class="<%= className %>">
            <td nowrap width="15%" style="border-bottom:1px solid #A9A9A9">
                <%= operator %>
            </td>
            <td nowrap width="15%" style="border-bottom:1px solid #A9A9A9">
                <%= contractNumber %>
            </td>
            <td nowrap width="15%" style="border-bottom:1px solid #A9A9A9">
                <%= policyEffDate %>
            </td>
            <td nowrap width="15%" style="border-bottom:1px solid #A9A9A9">
                <%= severity %>
            </td>
            <td width="40%" style="border-bottom:1px solid #A9A9A9">
                <%= error %>
            </td>
        </tr>
<%
        }// end for
    } // end if
%>
        <tr class="filler">
            <td colspan="5">
                &nbsp;
            </td>
        </tr>
    </table>
</span>

<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
        <td align="right">
            <input type="button" value="Close" onClick="closeWindow()">
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