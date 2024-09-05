<%@ page  errorPage="/common/jsp/error.jsp" autoFlush="true" %>
<%@ page import="edit.common.vo.*,
                 fission.utility.Util,
                 java.io.*,
                 java.util.*,
                 java.text.*,
                 edit.common.*,
                 logging.*,
                 edit.services.config.*"%>
<%
    File[] logFiles = (File[]) request.getAttribute("logFiles");

//    logFiles = (File[]) Util.sortObjects(logFiles, new String[]{"toString"});
%>
<%!
    /**
     * Convenience method to find the logName given the log's fileName.
     * @param fileName
     * @return
     */
    private String getLogName(String fileName)
    {
        String logName = null;

        String[] fileTokens = Util.fastTokenizer(fileName, ".");

        String lastToken = null;

        lastToken = fileTokens[fileTokens.length - 1];

        if (Util.isANumber(lastToken))
        {
            int lastDotIndex = fileName.lastIndexOf(".");

            fileName = fileName.substring(0, lastDotIndex);
        }

        EDITLog editLog = ServicesConfig.getEDITLogByFileName(fileName);

        if (editLog != null)
        {
            logName = editLog.getLogName();
        }

        return logName;
    }
%>

<html>

    <head>

        <title>EDITSOLUTIONS - Log Summary</title>

        <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
        <meta http-equiv="Cache-Control" content="no-store">
        <meta http-equiv="Pragma" content="no-cache">
        <meta http-equiv="Expires" content="0">

        <link href="/PORTAL/common/css/EDITSolutions.css" rel="stylesheet" type="text/css">

        <script language="javascript1.2">

            var f = null;

            function openDialog(theURL, winName, features, transaction, action, target) {

                dialog = window.open(theURL,winName,features);

                sendTransactionAction(transaction, action, target);
            }

            function showLogDetail(logName)
            {
                f.logName.value = logName;

                var width   = 1.00 * screen.width;
                var height  = 0.90 * screen.height;

                openDialog("","logDetail","top=0,left=0,resizable=yes,scrollbars=yes,width=" + width + ",height=" + height,"LoggingAdminTran", "showLogDetail", "logDetail");
            }

            function refreshLogs()
            {
                sendTransactionAction("LoggingAdminTran", "showLogSummary", "main");
            }

            function init(){

                f = document.theForm;
            }

            function sendTransactionAction(transaction, action, target) {

                f.transaction.value = transaction;
                f.action.value = action;

                f.target = target;

                f.submit();
            }

        </script>

    </head>

    <body class="mainTheme" onLoad="init()">

    <form name="theForm" method="post" action="/PORTAL/servlet/RequestManager">

        <span class="tableHeading">Log Summary</span>

            <table class="summary" id="logSummaryTableHeader" width="100%" height="92%" border="0" cellspacing="0" cellpadding="0">
                <tr class="heading">

                    <th nowrap width="28%">
                        Log Name
                    </th>
                    <th nowrap width="16%">
                        Last Modified
                    </th>
                    <th nowrap width="16%">
                        Mod. < 24 Hours (<font face="" color="Red">&nbsp;<b>&#8730;</b>&nbsp;</font>)
                    </th>
                    <th nowrap width="16%">
                        Size (bytes)
                    </th>
                    <th nowrap width="16%">
                        Report
                    </th>

                </tr>
            <tr>
                <td colspan="5" height="99%">
                    <span class="scrollableContent" style="border-style:solid; border-width:1; position:relative; width:100%; height:100%; top:0; left:0; z-index:0;">

                        <table isSelected="false" class="summary" id="logSummaryTable" height="100%" width="100%" border="0" cellspacing="0" cellpadding="2">
            <%
//                            SimpleDateFormat formatter = new SimpleDateFormat (LogEvent.DATE_TIME_FORMAT);

                            for (int i = 0; i < logFiles.length; i++)
                            {
                                String fileName =  logFiles[i].getName();
                                String fullFileName = logFiles[i].getAbsolutePath();
                                String logName = getLogName(fullFileName);

                                if (logName == null)
                                {
                                    continue; // There is a miss-match between the configuration file and the logs files actually found.
                                }

                                EDITDateTime lastModifiedDateTime = new EDITDateTime(logFiles[i].lastModified());
//                                Date lastModifiedDate = new Date(logFiles[i].lastModified());
//                                String date = formatter.format(lastModifiedDate);

                                String dateTime = lastModifiedDateTime.getFormattedDateTime();

                                long size = logFiles[i].length();

                                EDITDate eLastModifiedDate = lastModifiedDateTime.getEDITDate();

                                EDITDate todaysDate = new EDITDate();

                                boolean modifiedWithin24Hours = ((todaysDate.getElapsedDays(eLastModifiedDate) == 0) && (size > 0));
//                                boolean modifiedWithin24Hours = ((todaysDate.dateDiff(eLastModifiedDate) == 0) && (size > 0));

                                String modifiedChar = "&nbsp;";

                                if (modifiedWithin24Hours)
                                {
                                    modifiedChar = "<font face=\"\" color=\"Red\">&#8730;</font>";
                                }

                                String paddingBegin = "";
                                String paddingEnd = "";

                                int index = fileName.lastIndexOf(".");

                                // indent all backed-up logs by seeing if they end in ".number";
                                if (fileName.charAt(index + 1) != '\u0000'  && Util.isANumber(fileName.charAt(index + 1) + ""))
                                {
                                    paddingBegin = ".&nbsp;.&nbsp;.&nbsp;.&nbsp;.&nbsp;.&nbsp;";
                                    paddingEnd = "<font face='' size='1'>(archived)</font>";
                                }
            %>
                                    <tr className="" id="<%= fullFileName %>">
                                        <td nowrap width="28%" style="border-bottom:1px solid #A9A9A9">
                                             <%= paddingBegin + logName + paddingEnd %>
                                        </td>
                                        <td nowrap width="16%" style="border-bottom:1px solid #A9A9A9">
                                             <%= dateTime %>
                                        </td>
                                        <td nowrap width="16%" style="border-bottom:1px solid #A9A9A9">
                                            <%= modifiedChar %>
                                        </td>
                                        <td nowrap width="16%" style="border-bottom:1px solid #A9A9A9">
                                             <%= size %>
                                        </td>
                                        <td nowrap width="16%" style="border-bottom:1px solid #A9A9A9">
                                            <a href="javascript:showLogDetail('<%= logName %>')"><img src="../logging/images/report.gif" border="0"></img></a>
                                        </td>
                                    </tr>
            <%
                            }
            %>
                        <tr class="filler"> <!-- A dummy row to help with sizing -->
                            <td colspan="5">
                                &nbsp;
                            </td>
                        </tr>

                        </table>

                    </span>

                </td>
            </tr>
        </table>

        <table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
                <td align="right">
                    <input type="button" value="Refresh" onClick="refreshLogs()">
                </td>
            </tr>
        </table>

        <input type="hidden" name="transaction" value="">
        <input type="hidden" name="action"      value="">
        <input type="hidden" name="logName"    value="">

    </form>

    </body>

</html>



