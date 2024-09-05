<html>
<head>
<meta http-equiv="REFRESH" content="5; URL=/PORTAL/common/jsp/portal.jsp">

<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta http-equiv="Cache-Control" content="no-store">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Expires" content="0">


<title>EDITSolutions - Expired Session</title>
<link href="/PORTAL/common/css/EDITSolutions.css" rel="stylesheet" type="text/css">


<script language="JavaScript">
<!--
<%--The following code determines if the current window is anything other than the main window.  If so, it redirects this --%>
<%--page to the main window.  The error is then displayed in the main window for 5 seconds and then redirected to the--%>
<%--main startup page (see the REFRESH statement above).--%>
<!--
        if (top == self)                // i.e. If this is not part of a frameset
        {
            if (opener)                 // is a dialog
            {
                opener.top.location = "/PORTAL/security/jsp/expiredSessionError.jsp";

                window.close();         // close the dialog
            }
        }
        else                            // is a tab content
        {
            top.location = "/PORTAL/security/jsp/expiredSessionError.jsp";
        }
//-->
//-->
</script>


</head>

<body bgColor="#30548E">

<table bgColor="#99BBBB" cellspacing="0" cellpadding="5" border="0" width="100%" height="100%" style="border-style:solid; border-width:1; border-color:black; position:relative; top:0; left:0;">
<tr align="center" valign="middle">
    <td valign="bottom">
        <img src="/PORTAL/common/images/SEG_Software_Logo.jpg"
             alt="SEG Software" height="112" width="415"/>
    </td>
</tr>
    <tr>
    <td colspan="2" nowrap align="middle">
        <font face="" size="3">
        Expired Session
        </font>

        <br>
        <hr noshade color="black" size="1" width="300">
        <br>

        You will be returned to the main screen shortly.
    </td>
    </tr>
</table>
</body>
</html>