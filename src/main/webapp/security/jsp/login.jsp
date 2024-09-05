<!--
 * User: unknown
 * Date: Jan 1, 2000
 * Time: unknown
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 -->
<%@ page import="fission.utility.Util"%>
<%
    String username = Util.initString((String) request.getAttribute("username"), "");
    String password = Util.initString((String) request.getAttribute("password"), "");

    String targetTransaction = (String) request.getAttribute("targetTransaction");
    String targetAction = (String) request.getAttribute("targetAction");

    String message = Util.initString((String) request.getAttribute("message"), "");
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta http-equiv="Cache-Control" content="no-store">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Expires" content="0">

<link href="/PORTAL/common/css/EDITSolutions.css" rel="stylesheet" type="text/css">


<title>Login</title>
<script language="JavaScript">
<!--
    var f = null;

    var message = "<%= message %>";

    function init()
    {
        f = document.theForm;

        f.username.focus();
    }

    function login()
    {
        var username = f.username.value;

        var password = f.password.value;

        if (username.length == 0 | password.length == 0)
        {
            alert("Username and Password Required");
        }
        else
        {
            sendTransactionAction("SecurityAdminTran", "login", "_top");
        }
    }

    function checkForEnter(){

        if (window.event.keyCode == 13){

            login();
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
<body bgColor="#99BBBB" onLoad="init()">
<form name="theForm" method="post" action="/PORTAL/servlet/RequestManager">

<table class="loginTable" cellspacing="0" cellpadding="5" border="0" width="100%" height="100%" style="border-style:solid; border-width:0; border-color:black; position:relative; top:0; left:0;">
<tr height="300">
    <td nowrap align="center" valign="middle">
        <IMG SRC="/PORTAL/common/images/Vision_Logo.JPG" BORDER="0" ALT="" width="113" height="152">
    </td>
</tr>
<tr>
    <td nowrap>
<table align="center" width="90%" border="0" height="100%" style="border:0px solid #000000; position:relative; top:0; left:0;">
<tr height="40%" valign="bottom"> <!-- A dummy row to help with sizing -->
    <td colspan="4" align="middle">
        <font face="" size="3">
        &nbsp;<%= message %>&nbsp;
        </font>
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
        Password:
    </td>
    <td nowrap valign="middle" align="left">
        <input type="password" size="15" name="password" onkeypress="checkForEnter()">
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
        <input type="button" value="Login" onClick="login()">
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

</table>

<input type="hidden" name="transaction" value="">
<input type="hidden" name="action" value="">
<input type="hidden" name="targetTransaction" value="<%= targetTransaction %>">
<input type="hidden" name="targetAction" value="<%= targetAction %>">

</form>

</body>
</html>