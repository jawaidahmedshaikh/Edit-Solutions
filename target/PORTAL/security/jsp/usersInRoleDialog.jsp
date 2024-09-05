<%@ page import="fission.utility.Util,
                 security.Operator,
                 security.Role"%>

<%
    Operator[] operatorsInRoles = (Operator[]) request.getAttribute("operatorsInRoles");

    if (operatorsInRoles != null)
    {
        operatorsInRoles = (Operator[]) Util.sortObjects(operatorsInRoles, new String[]{"getName"});
    }

    Role role = (Role) request.getAttribute("role");

    String roleName = "";

    if (role != null)
    {
        roleName = Util.initString(role.getName(), "");
    }

%>

<html>
<head>

<script language="Javascript1.2">

    function closeWindow()
    {
        window.close();
    }

</script>

<title>Users In Role</title>

<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta http-equiv="Cache-Control" content="no-store">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Expires" content="0">

<link href="/PORTAL/common/css/EDITSolutions.css" rel="stylesheet" type="text/css">

</head>

<body class="mainTheme" onLoad="init()">

<form name="theForm" method="post" action="/PORTAL/servlet/RequestManager">


    <table class="summary" width="100%" height="90%" border="0" cellspacing="0" cellpadding="0">
        <tr>
            <span class="tableHeading">
                Uses In <i><%= roleName %></i>
            </span>
        </tr>
        <tr class="heading">
            <th width="100%">
                Username
            </th>
        </tr>
        <tr>
            <td height="99%" width="100%" colspan="3" nowrap>
                <span class="scrollableContent">

                <table id="componentMethodsTable" class="summary" width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">

<%
        if (operatorsInRoles != null)
        {
            for (int i = 0; i < operatorsInRoles.length; i++)
            {
                Operator currentOperator = operatorsInRoles[i];

                String username = currentOperator.getName();
%>
                <tr class="default">
                    <td width="100%" NOWRAP>
                        <%= username %>
                    </td>
                </tr>
<%
            }
        }
%>
                    <tr class="filler"> <!-- A dummy row to help with sizing -->
                        <td NOWRAP>
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
                <input type="button" value="Close" onClick="closeWindow()">
            </td>
        </tr>
    </table>

</form>
</body>
</html>