<%@ page import="edit.common.vo.LogEntryCollectionVO,
                 edit.common.vo.LogEntryVO,
                 edit.common.vo.LogContextVO,
                 edit.common.vo.LogContextEntryVO,
                 fission.utility.Util,
                 java.util.StringTokenizer,
                 java.util.Iterator,
                 java.util.List,
                 java.util.ArrayList"%>
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

    String logEntryId = (String) request.getAttribute("logEntryId");
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

    /**
     * Finds the targeted LogEntry by id.
     * @param logEntryCollectionVO
     * @param logEntryId
     * @return
     */
    private LogEntryVO getLogEntryVO(LogEntryCollectionVO logEntryCollectionVO, long logEntryId)
    {
        LogEntryVO logEntryVO = null;

        LogEntryVO[] logEntryVOs = logEntryCollectionVO.getLogEntryVO();

        for (int i = 0; i < logEntryVOs.length; i++)
        {
            LogEntryVO currentLogEntryVO = logEntryVOs[i];

            long currentLogEntryId = currentLogEntryVO.getId();

            if (currentLogEntryId == logEntryId)
            {
                logEntryVO = currentLogEntryVO;

                break;
            }
        }

        return logEntryVO;
    }

    /**
     * Tokenizes the script on || to find the set of script tokens.
     * @param logEntryVO
     * @return
     */
    private String[] getScriptTokens(LogEntryVO logEntryVO)
    {
        String[] scriptTokens = null;

        LogContextEntryVO[] logContextEntryVOs = logEntryVO.getLogContextEntryVO();

        String script = getContextValue(logContextEntryVOs, "Script");

        scriptTokens = script.split("::");

        return scriptTokens;
    }

    /**
     * A script token is in the form of "lineNumber,,instruction,,errored". This returns a 3 element array with these elements.
     * @param scriptToken
     * @return
     */
    private String[] getInstructionElements(String scriptToken)
    {
        String[] instructionElements = null;

        instructionElements = scriptToken.split(",,");

        return instructionElements;
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

        scrollRowIntoView();
    }

    /**
     * Scrolls the errored row into view.
     */
    function scrollRowIntoView()
    {
        var clientRow = document.getElementById("highlighted");

        if (clientRow != null) {

            clientRow.scrollIntoView(true);
        }
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
        <td width="5%" nowrap>
            Line #
        </td>
        <td width="95%" nowrap>
            Instruction
        </td>
    </tr>
</table>

<%--Summary--%>
<span class="scrollableContent" style="border-style:solid; border-width:1; position:relative; width:100%; height:90%; top:0; left:0">
    <table id="tableSummary" class="summary" width="100%" height="100%" border="0" cellspacing="0" cellpadding="5">
<%
    if (logEntryCollectionVO != null) // Test for the existence of the target VOs.
    {
        LogEntryVO logEntryVO = getLogEntryVO(logEntryCollectionVO, Long.parseLong(logEntryId));

        String[] scriptTokens = getScriptTokens(logEntryVO);

        for (int i = 0; i < scriptTokens.length; i++) // Loop through the target VOs.
        {
            String currentScriptToken = scriptTokens[i];

            String[] instructionElements = getInstructionElements(currentScriptToken);

            String lineNumber = instructionElements[0];

            String instruction = instructionElements[1];

            String errored = instructionElements[2];

            String className = "default";

//            System.out.println(errored);

            if (errored.equals("true"))
            {
                className = "highlighted";
            }
            else
            {
                className = "default";
            }
%>
        <tr class="<%= className %>" id="<%= className %>">
            <td nowrap width="5%" style="border-bottom:1px solid #A9A9A9">
                <%= lineNumber %>
            </td>
            <td nowrap width="95%" style="border-bottom:1px solid #A9A9A9">
                <%= instruction %>
            </td>
        </tr>
<%
        }// end for
    } // end if
%>
        <tr class="filler">
            <td colspan="2">
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