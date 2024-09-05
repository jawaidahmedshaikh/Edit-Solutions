<style type="text/css">
table.toolbar {
	border-left: 1px solid Black;
	border-top: 1px solid Black;
    border-right: 1px solid Black;
    border-bottom: 1px solid Black;
    margin-bottom:5px;
}
</style>

<%@ page import="edit.portal.common.session.UserSession,
                 fission.utility.Util,
                 java.util.Enumeration,
                 java.util.List,
                 java.util.ArrayList" %>
<%
    String pageTitle = Util.initString((String) request.getParameter("pageTitle"), "");

    Enumeration parameterNames = request.getParameterNames();

    List toolbarItems = new ArrayList();

    while (parameterNames.hasMoreElements())
    {
        String parameterName = (String) parameterNames.nextElement();

        if (parameterName.startsWith("toolbar-item:"))
        {
            toolbarItems.add(parameterName);
        }
    }
%>

<%!
    private String getToolbarText(String str)
    {
        return str.substring(0, str.indexOf(":"));
    }

    private String getAction(String str)
    {
        return str.substring(str.indexOf(":") + 1);
    }
%>

<script type="text/javascript">

    /**
    * A set of links for navigation.
    */
    function jumpTo(jumpToTarget)
    {

        var action = null;

        if (jumpToTarget == "Main")
        {
            action = "goToMain";
        }
        else if (jumpToTarget == "Logout")
        {
            action = "doLogOut";
        }
                <%
                    for (int i = 0; i < toolbarItems.size(); i++)
                    {
                        String toolbarItem = (String) toolbarItems.get(i);
                %>
        else if (jumpToTarget == "<%= toolbarItem.replaceAll("toolbar-item:", "") %>")
        {
            action = "<%= getAction(request.getParameter((String) toolbarItems.get(i))) %>";
        }
    <%
        }
    %>

        sendTransactionAction("PortalLoginTran", action, "_self");
    }
</script>
<table width="100%" class="toolbar" cellspacing="0" cellpadding="0" height="75px">
    <tr bgcolor="#30548E" height="25px">
        <td colspan="3" nowrap="nowrap">&nbsp;</td>
        <td colspan="4" align="center" bgcolor="#30548E" nowrap="nowrap">
            <b><i>

                <font face="Arial, Helvetica, sans-serif">
                    <font color="#FFFFFF" size="2">
                        EDIT
                        <font size="1">SOLUTIONS<%= pageTitle.equals("") ? "" : " - " %></font>
                        <%= pageTitle %>
                    </font>
                </font>
            </i></b>
        </td>
        <td colspan="3" nowrap="nowrap">&nbsp;</td>
    </tr>
    <tr bgcolor="#30548E" height="25px">
        <td colspan="6" nowrap="nowrap">
            <font color="#FFFFFF" size="2">
                <% for (int i = 0; i < toolbarItems.size(); i++)
                {
                    String toolbarItem = (String) toolbarItems.get(i);
                %>
                [
                <u>
                    <span onclick="jumpTo('<%= toolbarItem.replaceAll("toolbar-item:", "") %>')" style="font-weight: normal" onmouseover="this.style.fontWeight='bold'" onmouseout="this.style.fontWeight='normal'"><%= getToolbarText(request.getParameter(toolbarItem)) %></span>
                </u>
                ] &nbsp;
                <% } %>
            </font>
        </td>
        <td colspan="4" align="right" nowrap="nowrap">
            <font color="#FFFFFF" size="2">
                [
                <u>
                    <span onclick="jumpTo('Main')" style="font-weight: normal" onmouseover="this.style.fontWeight='bold'" onmouseout="this.style.fontWeight='normal'">Main</span>
                </u>
                ] &nbsp;
                <!-- [ -->
                <!-- <u> -->
                <!--  Formally the Help Link -->
                <!--</u> -->
                <!-- ] -->
                &nbsp;
                <%
                    UserSession userSession = (UserSession) session.getAttribute("userSession");

                    if ((userSession != null) && (userSession.userLoggedIn()))
                    {
                %>
                [
                <u>
                    <span onclick="jumpTo('Logout')" onmouseover="this.style.fontWeight='bold'" onmouseout="this.style.fontWeight='normal'">Logout</span>
                </u>
                ]
                <%
                    }
                %>
            </font>
        </td>
    </tr>
    <tr bgcolor="#99BBBB" height="25px">
        <td colspan="10" nowrap="nowrap">&nbsp;</td>
    </tr>
</table>
 