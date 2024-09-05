<%@ page import="edit.services.logging.*,
                 org.apache.logging.log4j.Logger,
                 logging.*,
                 fission.utility.*"%>


<html>
<head>
<%@ page isErrorPage="true" %>
<title>EDITSolutions Error</title>
<link href="/PORTAL/common/css/EDITSolutions.css" rel="stylesheet" type="text/css">

<script language="JavaScript">
<!--
    // Removes frames if target frame is not main or viewer
    if (top != self) {
        // Get main and viewer frame objects if they exist
        var main = null;
        var viewer = null;
        for (var i=0; i < top.frames.length; i++) {
            if (top.frames(i).name == "viewer") {
                main = top.frames(i);
            }
        }
        for (var i=0; i < parent.frames.length; i++) {
            if (parent.frames(i).name == "viewer") {
                viewer = parent.frames(i);
            }
        }

        // If the target frame is not main on viewer then
        // Change location to main
        if ((main != null) && (main.location != window.location)
                (viewer != null) && (viewer.location != window.location)) {

            top.frames("main").location = window.location;
        }
    }

    function init()
    {
        hideDetails();
    }

    function showDetails()
    {
        document.all.detailsTable.style.display = "block";
    }

    function hideDetails()
    {
        document.all.detailsTable.style.display = "none";
    }

//-->
</script>
</head>
<body class="mainTheme" onLoad="init()">

<p><b>
We are sorry, an error has occurred
while processing your request.
</b>
</p>

<hr align="left" size="3">

<table class="summary" width="100%" height="35%" border="0" cellspacing="0" cellpadding="0">
    <tr class="tableHeading">
        Reason:
    </tr>
    <tr>
        <td height="99%" colspan="4">
            <span class="scrollableContent">
 <%
                Throwable except = (Throwable) request.getAttribute("except");

                if( except == null){
                   except =  exception;
                }

                if (except != null)
                {
                    String message = except.toString();
                    String[] messageLines = Util.fastTokenizer(message, "\n");  //  look for line breaks

                    for (int i = 0; i < messageLines.length; i++)
                    {
                        String messageLine = messageLines[i];
                        out.println(messageLine);
%>
                        <br>
<%
                    }
                }
                else
                {
                    out.println("Unavailable - Check Log Files");
                }
 %>
            </span>
        </td>
    </tr>
</table>

<hr align="left" size="3">

<tr>
    <td>
<%--        <input type="button" id="btnDetails" name="details" value=" Details >> " onClick="detailsTable.style.display='none'">--%>
        <input type="button" id="btnDetails" name="details" value=" Show Details " onClick="showDetails()">
        <input type="button" id="btnHideDetails" name="hideDetails" value=" Hide Details " onClick="hideDetails()">
    </td>
</tr>


<table id="detailsTable" class="summary" width="100%" height="35%" border="0" cellspacing="0" cellpadding="0">
    <tr class="tableHeading">
        Details:
    </tr>
    <tr>
        <td height="99%" colspan="4">
            <span id="detailsSpan" class="scrollableContent">
 <%
                if (except != null)
                {
                    StackTraceElement[] trace = except.getStackTrace();
                    for (int i=0; i < trace.length; i++)
                    {
 %>
                        &nbsp;&nbsp;&nbsp;&nbsp
 <%
                        out.println("at " + trace[i]);
 %>
                        <br>
 <%
                    }

                    Throwable cause = except.getCause();

                    if (cause != null)
                    {
                        out.println(except.getCause());
                    }
                }
 %>
            </span>
        </td>
    </tr>
</table>

<%
    if (except != null)
    {
        System.out.println(except);
        
        except.printStackTrace();
        
        LogEvent logEvent = new LogEvent(except.getMessage(), except);

        Logger logger = Logging.getLogger(Logging.GENERAL_EXCEPTION);

        logger.error(logEvent);

        Log.logGeneralExceptionToDatabase(null, except);
    }
%>


<hr align="left" size="3">

<p>
<b><i><a name="contact">Tech Support<br>Contact Information:</a></i></b><br>
Systems Engineering Group, LLC 860-652-0254
</p>


</body>
</html>