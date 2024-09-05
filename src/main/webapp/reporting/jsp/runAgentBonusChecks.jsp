
<%--
  Created by IntelliJ IDEA.
  User: gfrosti
  Date: Dec 8, 2004
  Time: 12:37:08 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="/WEB-INF/taglibs-input.tld" prefix="input" %>
<%@ page import="edit.common.vo.CodeTableVO" %>
<%@ page import="edit.common.CodeTableWrapper" %>

<%-- ****************************** BEGIN Java Code ****************************** --%>
<%
    String responseMessage = (String) request.getAttribute("responseMessage");

    CodeTableVO[] frequencyCTs = CodeTableWrapper.getSingleton().getCodeTableEntries("BONUSFREQUENCY");
%>
<%-- ****************************** End Java Code ****************************** --%>

<html>
<head>
    <title>Run Agent Bonus Checks</title>
    <link href="/PORTAL/common/css/EDITSolutions.css" rel="stylesheet" type="text/css">

    <%-- ****************************** BEGIN JavaScript ****************************** --%>
    <script src="/PORTAL/common/javascript/commonJavascriptFunctions.js"></script>
    <script src="/PORTAL/common/javascript/formatDateFunctions.js" type="text/javascript"></script>
    <script src="/PORTAL/common/javascript/calendarSelector.js" type="text/javascript"></script>

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
         * Triggers the process create and execute the Agent Bonus Checks.
         */
        function runAgentBonusChecks()
        {
            try
            {
<%--                f.processDate.value = formatDate(f.processDateMonth.value, f.processDateDay.value, f.processDateYear.value, true);--%>

                sendTransactionAction("ReportingDetailTran", "runAgentBonusChecks", "main");
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
            <td nowrap>
                &nbsp; <!--Filler Row -->
            </td>
        </tr>

        <%--    BEGIN Form Content --%>
        <tr>
      <td nowrap align="right">Process Date:&nbsp;</td>
      <td nowrap align="left">
        <input:text name="processDate"
              attributesText="id='processDate' onBlur='DateFormat(this, this.value, event, true)' onKeyUp='DateFormat(this, this.value, event, false)' maxlength='10' size='10'"/>
        <a href="javascript:show_calendar('f.processDate', f.processDate.value);"><img
               src="/PORTAL/common/images/calendarIcon.gif" width="16" height="16" border="0"
               alt="Select a date from the calendar"></a>
      </td>
      <td width="65%" height="24">&nbsp;</td>
      </tr>
        <tr>
            <td align="right" nowrap >
                Mode:&nbsp;
            </td>
            <td nowrap align="left">
                <select name="frequencyCT">
                    <option value="">Please Select</option>
                    <%
                        if (frequencyCTs != null)
                        {
                            for (int i = 0; i < frequencyCTs.length; i++)
                            {
                                String currentCodeDesc = frequencyCTs[i].getCodeDesc();
                                String currentCode = frequencyCTs[i].getCode();

                                out.println("<option name=\"id\" value=\"" + currentCode + "\">" + currentCodeDesc + "</option>");
                            }
                        }
                    %>
                </select>
            </td>
            <td width="65%" height="24">&nbsp;</td>
        </tr>
        <tr>
            <td align="right" nowrap>
                Run Agent Bonus Checks Or Cancel:&nbsp;
                <input type="button" value="Enter" onClick="runAgentBonusChecks()">
            </td>

            <td align="left" nowrap>
                <input type="button" value="Cancel" onClick="showReportingMain()">
            </td>
        </tr>
        <%--    END Form Content --%>

        <tr height="50%">
            <td>
                &nbsp; <!--Filler Row -->
            </td>
        </tr>
    </table>
    <%-- ****************************** END Form Data ****************************** --%>

    <%-- ****************************** BEGIN Hidden Variables ****************************** --%>
    <input type="hidden" name="transaction">
    <input type="hidden" name="action">
<%--    <input type="hidden" name="processDate">--%>
    <%-- ****************************** END Hidden Variables ****************************** --%>

</form>
</body>
</html>