<jsp:include page="/common/jsp/template/template-toolbar.jsp" flush="true">
    <jsp:param name="pageTitle" value="Export"/>
</jsp:include>

<!--
 *
 * User: cgleason
 * Date: Jul 11, 2006
 * Time: 10:29:53 AM
 *
 * (c) 2000 - 2006 Systems Engineering Group, LLC.  All rights Reserved
 *  Systems Engineering Group, LLC Propietary and confidential.  Any use
 *  is subject to the license agreement.
 -->
<%--<%@ page import="edit.portal.common.session.UserSession"%>--%>
<%--<!-- ****** JAVA CODE ***** //-->--%>
<%--<%--%>
<%--    UserSession userSession = (UserSession) session.getAttribute("userSession");--%>
<%--%>--%>
<%----%>
<%--<html>--%>
<%--<head>--%>
<%--<title>EDIT SOLUTIONS - File Export</title>--%>
<%--<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">--%>
<%--<meta http-equiv="Cache-Control" content="no-store">--%>
<%--<meta http-equiv="Pragma" content="no-cache">--%>
<%--<meta http-equiv="Expires" content="0">--%>
<%----%>
<%--<script src="/PORTAL/common/javascript/commonJavascriptFunctions.js"></script>--%>
<%--<script language="JavaScript">--%>
<%--<!----%>
<%--<!--******* JAVASCRIPT ******//-->--%>
<%----%>
<%--function init()--%>
<%--{--%>
<%--	f = document.theForm;--%>
<%--}--%>
<%----%>
<%--function getHelp()--%>
<%--{--%>
<%--    alert("To be implemented.");--%>
<%--}--%>
<%----%>
<%--function jumpTo(jumpToTarget)--%>
<%--{--%>
<%--        var action = null;--%>
<%----%>
<%--        if (jumpToTarget == "Main")--%>
<%--        {--%>
<%--            action = "goToMain";--%>
<%--        }--%>
<%--        else if (jumpToTarget == "Logout")--%>
<%--        {--%>
<%--            action = "doLogOut";--%>
<%--        }--%>
<%----%>
<%--        sendTransactionAction("PortalLoginTran",action, "_top");--%>
<%--}--%>
<%----%>
<%----%>
<%--function swapImage(id, state)--%>
<%--{--%>
<%--	var imgElement = document.getElementById(id);--%>
<%--	var imageBase = "/PORTAL/common/images/";--%>
<%----%>
<%--	if(state == "over")--%>
<%--	{--%>
<%--		imgElement.src = imageBase + imgElement.id + "Over.gif";--%>
<%--	}--%>
<%--	else if(state == "out")--%>
<%--	{--%>
<%--		imgElement.src = imageBase + imgElement.id + ".gif";--%>
<%--	}--%>
<%--}--%>
<%--//-->--%>
<%--</script>--%>
<%--</head>--%>
<%----%>
<%--<!-- ****** HTML CODE ***** //-->--%>
<%--<body  bgColor="#99BBBB" onLoad="init()">--%>
<%--<span style="border-style:solid; border-width:1;  position:relative; width:100%; height:100%; top:0; left:0; z-index:0; overflow:visible">--%>
<%----%>
<%--<form name="theForm" method="post" action="/PORTAL/servlet/RequestManager">--%>
<%--  <table width="100%" border="0" cellspacing="0" cellpadding="3" height="2%">--%>
<%--    <tr>--%>
<%--      <td colspan="3" bgcolor="#30548E">--%>
<%--        &nbsp;--%>
<%--      </td>--%>
<%--      <td colspan="4" align="center" bgcolor="#30548E">--%>
<%--        <b><i>--%>
<%--            <font face="Arial, Helvetica, sans-serif">--%>
<%--               <font color="#FFFFFF" size="2">EDIT<font size="1">SOLUTIONS- </FONT>Export</font>--%>
<%--           </FONT>--%>
<%--        </i></b>--%>
<%--        </td>--%>
<%--      <td colspan="3" bgcolor="#30548E" id="lockMessage" align="right">--%>
<%--        &nbsp;--%>
<%--      </td>--%>
<%--    </tr>--%>
<%----%>
<%----%>
<%--    <tr bgcolor="#30548E">--%>
<%--      <td colspan="6">--%>
<%--        &nbsp;--%>
<%--      </td>--%>
<%--      <td colspan="4" align="right">--%>
<%--        <font color="#FFFFFF" size="2">--%>
<%--            [<u>--%>
<%--                <span onClick="jumpTo('Main')" onmouseover="this.style.fontWeight='bold'" onmouseout="this.style.fontWeight='normal'">Main</span>--%>
<%--           </u>] &nbsp;--%>
<%--            [<u>--%>
<%--                <span onClick="getHelp()" onmouseover="this.style.fontWeight='bold'" onmouseout="this.style.fontWeight='normal'">Help</span>--%>
<%--           </u>] &nbsp;--%>
<%--            <%--%>
<%--                if (userSession.userLoggedIn())--%>
<%--                {--%>
<%--            %>--%>
<%--            [<u>--%>
<%--                <span onClick="jumpTo('Logout')" onmouseover="this.style.fontWeight='bold'" onmouseout="this.style.fontWeight='normal'">Logout</span>--%>
<%--            </u>]--%>
<%--            <%--%>
<%--                }--%>
<%--            %>--%>
<%--        </font>--%>
<%--      </td>--%>
<%--    </tr>--%>
<%--  </table>--%>
<%----%>
<%--<!-- ****** HIDDEN FIELDS ***** //-->--%>
<%--  	<input type="hidden" name="transaction" value="">--%>
<%--    <input type="hidden" name="action"      value="">--%>
<%--    <input type="hidden" name="jumpToTarget" value="">--%>
<%----%>
<%--</form>--%>
<%----%>
<%--</span>--%>
<%--</body>--%>
<%--</html>--%>
