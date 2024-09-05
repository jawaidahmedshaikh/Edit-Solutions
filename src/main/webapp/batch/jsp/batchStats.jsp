<%--
  Created by IntelliJ IDEA.
  User: gfrosti
  Date: Nov 12, 2004
  Time: 3:08:17 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%-- ****************************** BEGIN Java Code ****************************** --%>
<%@ page import="batch.*,
                 edit.services.*,
                 batch.business.*,
                 edit.common.*,
                 fission.utility.*,
                 java.text.*,
                 java.util.*"%>
<%
    BatchStat[] batchStats = EditServiceLocator.getSingleton().getBatchAgent().getBatchStats();
%>
<%!
//    /**
//     * @param timeMillis
//     * @return
//     */
//    private String formatDateTime(long timeMillis)
//    {
//        String startTime = null;
//
//        DateFormat dateFormat = new SimpleDateFormat("hh:mm:ss a 'on' EEE MMM d");
//
//        startTime = dateFormat.format(new Date(timeMillis));
//
//        return startTime;
//    }

    /**
     * Formats the date and time for screen display
     * @param dateTime
     * @return  formatted string
     */
     private String formatDateTime(EDITDateTime dateTime)
     {
        String time = null;

        EDITDate date = dateTime.getEDITDate();

        String month = date.getMonthName();
        String day = date.getFormattedDay();
        String dayOfWeek = date.getDayOfWeek();

        time = dateTime.getFormattedTime() + " on " + dayOfWeek + " " + month + " " + day;

        return time;
     }


    /**
     * Formats the BatchRate to 3 decimals.
     *
     * @param batchRateSeconds
     * @return
     */
    private String formatBatchRate(double batchRateSeconds)
    {
        String batchRate = new EDITBigDecimal(String.valueOf(batchRateSeconds), 3).toString();

        return batchRate;
    }

    /**
     * Renders a graphic of successes to failures for easy visual analysis.
     *
     * @param batchStat
     * @return
     */
    private String generateSuccessFailureBar(BatchStat batchStat)
    {
        String table = "<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">";

        table += "<tr><td colspan=\"100\" height=\"10\" align=\"center\"><font face='arial' size='2'>Graph: Success/Failure</font></td></tr>";

        table += "<tr>";

        table += getRowContents(batchStat);

        table += "</tr>";

        table += "</table>";

        return table;
    }

    /**
     * Builds a green/red bar (success/failure), or a NA message if the successes is ambiguous.
     *
     * @param batchStat
     * @return
     */
    private String getRowContents(BatchStat batchStat)
    {
        String rowContents = "";

        if (!batchStat.getBatchStatus().equals(Batch.PROCESSING_NO_STATS_AVAILABLE) && !batchStat.getBatchStatus().equals(Batch.PROCESSED_BUT_NO_ELEMENTS_FOUND))
        {

            String border = "";

            if (!batchStat.getJobCompleted())
            {
                border = "border-left: 1px solid #BBBBBB; border-right: 1px solid #BBBBBB;";
            }

            long successes = scaleSuccesses(batchStat);
            
            long skips = scaleSkips(batchStat);
            
            long combo = successes + skips;

            String cellColor = null;

            for (int i = 0; i < 100; i++)
            {
                if (i < successes)
                {
                    cellColor = "green";
                }
                else if (i >= successes && i < combo)
                {
                	cellColor = "yellow";
                }
                else
                {
                	if (batchStat.getFailureCount()>0) {
                    	cellColor = "red";
                	}
                	else {
                		cellColor = "gray";
                	}
                }

                rowContents += "<td style=\"" + border + "\" bgColor=\"" + cellColor + "\">";

                rowContents += "&nbsp;";

                rowContents += "</td>";
            }
        }
        else
        {
            String cellChar = ".";

            if (batchStat.getBatchStatus().equals(Batch.PROCESSING_NO_STATS_AVAILABLE))
            {
                cellChar = "?";
            }

            for (int i = 0; i < 100; i++)
            {
                rowContents += "<td align='middle'>";

                rowContents += cellChar;

                rowContents += "</td>";
            }
        }

        return rowContents;
    }
    
    /**
     * Scales the success count to its failure/skip count from 1 - 100.
     *
     * @param batchStat
     * @return the scales success
     */
    private long scaleSuccesses(BatchStat batchStat)
    {
        long successes = batchStat.getSuccessCount();

        long failures = batchStat.getFailureCount();
        
        long skips = batchStat.getSkipCount();

        successes = (long) (successes * 100) / (successes + failures + skips);

        return successes;
    }
    
    /**
     * Scales the skip count to its success/failure count from 1 - 100.
     *
     * @param batchStat
     * @return the scales skipRate
     */
    private long scaleSkips(BatchStat batchStat)
    {
        long successes = batchStat.getSuccessCount();

        long failures = batchStat.getFailureCount();
        
        long skips = batchStat.getSkipCount();

        skips = (long) (skips * 100) / (successes + failures + skips);

        return skips;
    }
%>
<%-- ****************************** End Java Code ****************************** --%>

<html>
<head>
<title>Single Summary JSP</title>

<%-- ****************************** BEGIN JavaScript ****************************** --%>


<%-- ****************************** END JavaScript ****************************** --%>
<STYLE>
table.summary
{
	border: 1px solid Black;
	background: #BBBBBB;
	padding : 0px 0px 0px 0px;
}

table.summary tr
{
    color: #000000;
	height: 15px;
	text-align : left;
}

table.summary tr.heading
{
	font:bold 12pt Arial;
	color: #FFFFFF;
	background-color: #30548E;
}

td.borderBottom
{
    border-bottom: 1px;
    border-bottom-color: Gray;
    border-bottom-style: solid;
}

body
{
	font-family: Arial;
	font-size: 10pt;
    background: #99BBBB;
}

span.counter
{
    font-family: Arial;
    font-size: 10pt;
    color: black;
}

</STYLE>

<script>

/**
 * Refreshes this dialog.
 */
function refreshDialog()
{
    window.location.href="/PORTAL/batch/jsp/batchStats.jsp";
}

</script>

</head>
<body>

<form name="theForm" method="post" action="/PORTAL/servlet/RequestManager">

<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
        <td align="middle">
            <font face="" size="5">Batch Stats</font> <font face="" size="2">(sorted by start time descending)</font>
        </td>
    </tr>
    <tr>
        <td align="right">
            <input type="button" value="Refresh" onClick="refreshDialog()">
            <input type="button" value=" Close " onClick="window.close()">
        </td>
    </tr>
</table>

<%
    if (batchStats.length == 0)
    {
        out.println("<hr><h3 align='middle'>No Jobs In Progress</h3>");
    }

    for (int i = 0; i < batchStats.length; i++)
    {
        BatchStat batchStat = batchStats[i];
        EDITDateTime startTime = new EDITDateTime(batchStat.getStartTime());
        EDITDateTime stopTime = new EDITDateTime(batchStat.getStopTime());

        out.println("<div align='center'><span class='counter'>" + (i + 1) + " of " + batchStats.length + "</span></div>");
%>
        <table class="summary" width="100%" border="0" cellspacing="0" cellpadding="5">
            <tr class="heading">
                <td width="33%" nowrap>
                    Job: <%= batchStat.getProcessName() %>
                </td>
                <td width="33%" nowrap>
                    Status: <%= batchStat.getBatchStatus() %>
                </td>
                <td width="34%" nowrap>
                    &nbsp;
                </td>
            </tr>
            <tr>
                <td class="borderBottom">
                    <font face='' color='navy'>Start Time</font>: <%= formatDateTime(startTime) %>
                </td>
                <td class="borderBottom">
                    <font face='' color='navy'>Stop Time</font>: <%= !batchStat.getJobCompleted() ? "---" : formatDateTime(stopTime) %>
                </td>
                <td class="borderBottom">
                    <font face='' color='navy'>Total Time</font>: <%= DateTimeUtil.convertTimeToHHMMSSss(batchStat.getTotalBatchTime()) + " <font face='' size='2'>(hh:mm:ss.sss)</font>" %>
                </td>
            </tr>
            <tr>
                <td class="borderBottom">
                    <font face='' color='navy'>Successes</font>: <%= batchStat.getSuccessCount() %> <font face='' size='2'> <%= batchStat.getElementName() %>(s)</font>
                </td class="borderBottom">
                <td class="borderBottom">
                    <font face='' color='navy'>Failures</font>: <%= batchStat.getFailureCount() %> <font face='' size='2'> <%= batchStat.getElementName() %>(s)</font>
                </td>
                <td class="borderBottom">
                    <font face='' color='navy'>Skips</font>: <%= batchStat.getSkipCount() %> <font face='' size='2'> <%= batchStat.getElementName() %>(s)</font>
                </td>
            </tr>
            <tr>
                <td colspan="3" class="borderBottom">
                    <font face='' color='navy'>Total</font>: <%= batchStat.getTotalCount() %> <font face='' size='2'> <%= batchStat.getElementName() %>(s)</font>
                </td>
            </tr>
            <tr>
                <td colspan="1" class="borderBottom">
                    <font face='' color='navy'>Rate</font>: <%= batchStat.getBatchRate() > 0 ?formatBatchRate(batchStat.getBatchRate()):"---" %> <font face='' size='2'><%= batchStat.getElementName() %>(s)/sec</font>
                </td>
                <td colspan="2" class="borderBottom">
                    <font face='' color='navy'>Status Detail</font>: <font face='' size='2'><%= batchStat.getBatchMessage() %></font>
                </td>
            </tr>
            <tr>
                <td colspan="3">
                    <%= generateSuccessFailureBar(batchStat) %>
                </td>
            </tr>
        </table>

        <br>
        <%
    }
        %>


<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
        <td align="right">
            <input type="button" value="Refresh" onClick="refreshDialog()">
            <input type="button" value=" Close " onClick="window.close()">
        </td>
    </tr>
</table>

</body>
</html>