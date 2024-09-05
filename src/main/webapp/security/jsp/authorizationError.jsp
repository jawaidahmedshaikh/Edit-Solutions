<%@ page import="fission.utility.Util,
                 edit.common.*"%>
<%
    String message = Util.initString((String) request.getAttribute("message"), "");

    EDITDate currentDate = new EDITDate();
%>


<html>
<head>
<meta http-equiv="REFRESH" content="5; URL=/PORTAL/common/jsp/portal.jsp">
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta http-equiv="Cache-Control" content="no-store">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Expires" content="0">

<link href="/PORTAL/common/css/EDITSolutions.css" rel="stylesheet" type="text/css">


<title>Login</title>

<style type="text/css">
span.copyright
{
    font:bold 8pt verdana,arial,sans-serif;color="white";
}
</style>

<script language="JavaScript">

        if (top == self)                // i.e. If this is not part of a frameset
        {
            if (opener)                 // is a dialog
            {
                opener.top.location = "/PORTAL/security/jsp/authorizationError.jsp";
                window.close();         // close the dialog
            }
        }
        else                            // is a tab content
        {
            top.location = "/PORTAL/security/jsp/authorizationError.jsp";
        }

    function forwardAfterPause()
    {
        //  alert("forwardAfterPause");
        //  setTimeout("pauseThenExit()", 5000);
    }

  /*  function pauseThenExit()
    {
        if (top == self) // i.e. If this is not part of a frameset
        {
            if (opener)  // is a dialog
            {
                opener.top.location = "/PORTAL/security/jsp/authorizationError.jsp";
                window.close();
            }
            else        // is not a dialog (ex. if pick something off top toolbar)
            {
                top.location = "/PORTAL/security/jsp/authorizationError";
            }
        }
        else
        {
            top.location = "/PORTAL/security/jsp/authorizationError";
        }
    }
    */
    var f = null;

<%--    var message = "<%= message %>";--%>

    function init()
    {
        f = document.theForm;
    }

    function login()
    {
        sendTransactionAction("SecurityAdminTran", "login", "_top");
    }

    function sendTransactionAction(transaction, action, target) {

        f.transaction.value = transaction;
        f.action.value = action;
        f.target = target;
        f.submit();
    }

</script>
</head>
<body bgColor="#30548E" onLoad="init();forwardAfterPause()">
<form name="theForm">


<table bgColor="#99BBBB" cellspacing="0" cellpadding="5" border="0" width="100%" height="100%" style="border-style:solid; border-width:1; border-color:black; position:relative; top:0; left:0;">
<tr align="center" valign="middle">
    <td valign="bottom">
        <img src="/PORTAL/common/images/SEG_Software_Logo.jpg"
             alt="SEG Software" height="112" width="415"/>
    </td>
</tr>
<tr>
    <td nowrap colspan="2">
<table align="center" width="90%" border="0" height="100%" style="border:1px solid #000000; position:relative; top:0; left:0;">
<tr height="40%" valign="bottom"> <!-- A dummy row to help with sizing -->
    <td width="40%">
        &nbsp;
    </td>
    <td colspan="2" nowrap align="middle">
        <font face="" size="3">
        No Authorization
<%--        <%= message %>--%>
        </font>

        <br>
        <hr noshade color="black" size="1" width="300">
        <br>

        You will be returned to the main screen shortly.
    </td>
    <td width="40%">
        &nbsp;
    </td>
</tr>
<tr>
    <td width="100%" colspan="4">
        &nbsp;
    </td>
</tr>
<tr>
    <td width="100%" colspan="4">
        &nbsp;
    </td>
</tr>
<tr>
    <td width="100%" colspan="4">
        &nbsp;
    </td>
</tr>
<tr height="40%"> <!-- A dummy row to help with sizing -->
    <td colspan="4">
        &nbsp;
    </td>
</tr>
</table>
    </td>
</tr>
<tr>
    <td align="center" valign="bottom" >
        <span class="copyright">Copyright 2000-<%= currentDate.getFormattedYear()%>; Systems Engineering Group, LLC. All Rights Reserved.</span>
    </td>
</tr>
</td>

</table>

</form>

</body>
</html>