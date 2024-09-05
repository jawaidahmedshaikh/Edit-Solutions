<%@ page import="edit.common.vo.*,
                 fission.utility.*,
                 security.Role"%>

<%
    BIZRoleVO[] impliedRoleVOs = (BIZRoleVO[]) request.getAttribute("impliedRoleVOs");

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
                Implied Roles For <i><%= roleName %></i>
            </span>
        </tr>
        <tr class="heading">
            <th width="50%">
                Implies
            </th>
            <th width="50%">
                Mapping
            </th>
        </tr>
        <tr>
            <td height="99%" width="100%" colspan="3" nowrap>
                <span class="scrollableContent">

                <table id="componentMethodsTable" class="summary" width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">

<%
        if (impliedRoleVOs != null)
        {
            for (int i = 0; i < impliedRoleVOs.length; i++)
            {
                RoleVO currentRoleVO = impliedRoleVOs[i].getRoleVO();

                String currentRoleName = currentRoleVO.getName();

                String implication = impliedRoleVOs[i].getImplication();
%>
                <tr class="default">
                    <td width="50%" NOWRAP>
                        <%= currentRoleName %>
                    </td>
                    <td width="50%" NOWRAP>
                        <%= implication %>
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