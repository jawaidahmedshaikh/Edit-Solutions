<%@ page import="fission.utility.Util,
                 edit.common.*"%>
<%
    String username = Util.initString((String) request.getAttribute("username"), "");

    String password = Util.initString((String) request.getAttribute("password"), "");

    String targetTransaction = (String) request.getAttribute("targetTransaction");
    String targetAction = (String) request.getAttribute("targetAction");

    String message = Util.initString((String) request.getAttribute("message"), "");

    EDITDate currentDate = new EDITDate();
%>


<html>
<head>
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
<!--
    var f = null;

    var message = "<%= message %>";

    function init()
    {
        f = document.theForm;

        f.newPassword.focus();

        f.username.contentEditable = false;
    }

    function checkForEnter(){

        if (window.event.keyCode == 13){

            updateExpiredPassword();
        }
    }

    function updateExpiredPassword()
    {
        var username = f.username.value;

        var newPassword = f.newPassword.value;

        var confirmNewPassword = f.confirmNewPassword.value;

        if (username.length == 0 || newPassword.length == 0 || confirmNewPassword == 0)
        {
            alert("Username and Password Required");
        }
        if (newPassword != confirmNewPassword)
        {
            alert("Passwords Are Not The Same");
        }
        else
        {
            sendTransactionAction("SecurityAdminTran", "updateExpiredPassword", "_top");
        }
    }

    function sendTransactionAction(transaction, action, target) {

        f.transaction.value = transaction;
        f.action.value = action;
        f.target = target;
        f.submit();
    }
//-->
</script>
</head>
<body bgColor="#30548E" onLoad="init()">
<form name="theForm" method="post" action="/PORTAL/servlet/RequestManager">


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
    <td colspan="4" align="middle">
        <font face="" size="3">
        &nbsp;<%= message %>&nbsp;
        </font>

        <br>
        <hr noshade color="black" size="1" width="300">
        <br>

    </td>
</tr>
<tr>
    <td width="50%">
        &nbsp;
    </td>
    <td nowrap valign="middle" align="right">
        Username:
    </td>
    <td nowrap valign="middle" align="left">
        <input type="text" size="15" name="username" value="<%= username %>">
    </td>
    <td width="50%">
        &nbsp;
    </td>
</tr>
<tr>
    <td>
        &nbsp;
    </td>
    <td nowrap valign="middle" align="right">
        New Password:
    </td>
    <td nowrap valign="middle" align="left">
        <input type="password" size="15" name="newPassword">
    </td>
    <td>
        &nbsp;
    </td>
</tr>
<tr>
    <td>
        &nbsp;
    </td>
    <td nowrap valign="middle" align="right">
        Confirm Password:
    </td>
    <td nowrap valign="middle" align="left">
        <input type="password" size="15" name="confirmNewPassword" onkeypress="checkForEnter()">
    </td>
    <td>
        &nbsp;
    </td>
</tr>

<tr>
    <td>
        &nbsp;
    </td>
    <td nowrap valign="middle" align="right">
        &nbsp;
    </td>
    <td nowrap valign="middle" align="right">
        <input type="button" value="Login" onClick="updateExpiredPassword()">
    </td>
    <td>
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

<input type="hidden" name="transaction" value="">
<input type="hidden" name="action" value="">
<input type="hidden" name="targetTransaction" value="<%= targetTransaction %>">
<input type="hidden" name="targetAction" value="<%= targetAction %>">
<input type="hidden" name="password" value="<%= password %>">

</form>

</body>
</html>