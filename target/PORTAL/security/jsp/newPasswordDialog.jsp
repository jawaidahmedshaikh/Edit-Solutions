<%@ page import="edit.common.vo.*,
                 fission.utility.*"%>

<%
    String newPasswordMessage = (String) request.getAttribute("newPasswordMessage");
%>

<html>
<head>

<script language="Javascript1.2">

    function closeWindow()
    {
        window.close();
    }

</script>

<title>New Password Dialog</title>

<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta http-equiv="Cache-Control" content="no-store">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Expires" content="0">

<link href="/PORTAL/common/css/EDITSolutions.css" rel="stylesheet" type="text/css">

</head>

<body class="mainTheme">

    <table width="100%" height="70%" border="0" cellspacing="0" cellpadding="0">
        <tr>
            <td>
                <span>
                    <%= newPasswordMessage %>
                </span>
            </td>
        </tr>
    </table>

    <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
            <td align="right">
                <input type="button" value="Close" onClick="closeWindow()">
            </td>
        </tr>
    </table>

</body>
</html>