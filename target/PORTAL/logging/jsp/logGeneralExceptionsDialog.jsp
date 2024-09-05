<%@ page import="logging.*,
                 java.util.*,
                 edit.common.*,
                 fission.utility.*"%>
<!--
 * User: sldorman
 * Date: Jun 29, 2006
 * Time: 10:43:38 AM
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 -->

 <%
     Log log = Log.findBy_LogName_V1(Log.GENERAL_EXCEPTION);    
     
     LogEntry[] logEntries = LogEntry.findBy_LogFK(log.getLogPK());
 %>

<script type="text/javascript">

    function init()
    {
       initScrollTable(document.getElementById("generalExceptionsScrollTable"));
    }

</script>

 <div class="scrollTable" id="generalExceptionsScrollTable" style="padding:0px; margin:0px; border-style: solid; border-width:1; background:#BBBBBB; position:relative; width:100%; height:90%; top:0; left:0;">
    <span class="scrollTableHead">
        <table class="widgetSummary" width="100%" height="2%" border="0" cellspacing="0" cellpadding="0">

        <tr class="heading">
            <td width="15%"> Creation Date/Time </td>
            <td width="20%"> Message </td>
            <td width="65%"> Stack Trace </td>
        </tr>
        </table>
    </span>

<%--<div class="scrollableContent" style="padding:0px; margin:0px; border-style: solid; border-width:1; background:#BBBBBB; position:relative; width:100%; height:90%; top:0; left:0;">--%>
    <span class="scrollTableBody">
    <table style="position:relative; left:0; top:0;" id="generalExceptionsBody" class="widgetSummary" width="100%" border="0" cellspacing="0" cellpadding="0">
        <%
            for (int i = 0; i < logEntries.length; i++)
            {
                String creationDateTime = logEntries[i].getCreationDateTime().getFormattedDateTime();
                Set logColumnEntries = logEntries[i].getLogColumnEntries();
        %>
                <tr class="default">
                    <td width="15%" nowrap> <%= creationDateTime %> </td>
                    <td width="20%" nowrap> <%= logEntries[i].getLogMessage() %> </td>
        <%
                int rowSpan = 0;

                for (Iterator iterator = logColumnEntries.iterator(); iterator.hasNext();)
                {
                    LogColumnEntry logColumnEntry = (LogColumnEntry) iterator.next();
                    
                    String columnValue = logColumnEntry.getLogColumnValue();

                    String stackTrace = convertStackTraceToHTML(columnValue);

                    rowSpan = getRowSpan(stackTrace);
        %>
                    <td width="65%" rowspan="<%= rowSpan %>" valign="top"> <%= stackTrace %> </td>
        <%
                }
        %>
                </tr>
        <%
                //  Create dummy cells to fill the rows of the rowspan
                for (int j = 0; j < rowSpan-1; j++) // reduce by 1 because the first row has real data
                {
        %>
                        <tr>
                            <td></td>
                            <td></td>
                        </tr>
        <%
                }
        %>
<%--                Add a horizontal line to separate logEntries--%>
                <tr>
                    <td> <hr noshade> </td>
                    <td> <hr noshade> </td>
                    <td> <hr noshade> </td>
                </tr>
        <%
            }
        %>
        </table>
    </span>
</div>

<span style="position:relative; width:100%; height:15%; top:0; left:0; z-index:0">
    <table width="100%" border="0" cellspacing="6" cellpadding="0">
        <tr>
            <td align="right" nowrap>
                <input type="button" name="close" value="Close" onClick="closeWindow()">
            </td>
        </tr>
    </table>
</span>

<%!
    private String convertStackTraceToHTML(String longString)
    {
        if (longString == null)
        {
            longString = "NULL";
        }

        longString = longString.replaceAll("\n", "<BR>");

        return longString;
    }

    private int getRowSpan(String longString)
    {
        String[] tokens = Util.fastTokenizer(longString, "<BR>");

        return tokens.length - 1;
    }
%>