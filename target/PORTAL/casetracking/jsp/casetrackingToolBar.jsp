<!--
 * User: sprasad
 * Date: Mar 17, 2005
 * Time: 11:25:02 AM
 *
 * (c) 2000-2005 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement. 
 -->
 
<%@ page import="edit.portal.common.session.UserSession"%>
<!-- ****** JAVA CODE ***** //-->
<%
    UserSession userSession = (UserSession) session.getAttribute("userSession");
%>

<html>
<head>
<title>EDIT SOLUTIONS - Case Tracking</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta http-equiv="Cache-Control" content="no-store">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Expires" content="0">

<script src="/PORTAL/common/javascript/commonJavascriptFunctions.js"></script>
<script src="/PORTAL/common/javascript/help.js"></script>

<script language="JavaScript">
<!--
<!--******* JAVASCRIPT ******//-->

function init()
{
	f = document.theForm;
}

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

        sendTransactionAction("PortalLoginTran",action, "_top");
}

function showSearchDialog()
{
    var width = 0.99 * screen.width;
    var height = 0.60 * screen.height;

	openDialog("searchDialog", "top=0,left=0,resizable=no", width,  height);

	sendTransactionAction("CaseTrackingTran", "showClientSearchDialog", "searchDialog");
}

function swapImage(id, state)
{
	var imgElement = document.getElementById(id);
	var imageBase = "/PORTAL/common/images/";

	if(state == "over")
	{
		imgElement.src = imageBase + imgElement.id + "Over.gif";
	}
	else if(state == "out")
	{
		imgElement.src = imageBase + imgElement.id + ".gif";
	}
}
//-->
</script>
</head>

<!-- ****** HTML CODE ***** //-->
<body  bgColor="#99BBBB" onLoad="init()">
<span style="border-style:solid; border-width:1;  position:relative; width:100%; height:100%; top:0; left:0; z-index:0; overflow:visible">

<form name="theForm" method="post" action="/PORTAL/servlet/RequestManager">
  <table width="100%" border="0" cellspacing="0" cellpadding="3" height="2%">
    <tr>
      <td colspan="3" bgcolor="#30548E">
        &nbsp;
      </td>
      <td colspan="4" align="center" bgcolor="#30548E">
        <b><i>
            <font face="Arial, Helvetica, sans-serif">
               <font color="#FFFFFF" size="2">EDIT<font size="1">SOLUTIONS- </FONT>Case Tracking</font>
           </FONT>
        </i></b>
        </td>
      <td colspan="3" bgcolor="#30548E" id="lockMessage" align="right">
        &nbsp;
      </td>
    </tr>


    <tr bgcolor="#30548E">
      <td colspan="6">
        &nbsp;
      </td>
      <td colspan="4" align="right">
        <font color="#FFFFFF" size="2">
            [<u>
                <span onClick="jumpTo('Main')" onmouseover="this.style.fontWeight='bold'" onmouseout="this.style.fontWeight='normal'">Main</span>
           </u>] &nbsp;
            [<u>
                <span onClick="getHelp()" onmouseover="this.style.fontWeight='bold'" onmouseout="this.style.fontWeight='normal'">Help</span>
           </u>] &nbsp;
            <%
                if (userSession.userLoggedIn())
                {
            %>
            [<u>
                <span onClick="jumpTo('Logout')" onmouseover="this.style.fontWeight='bold'" onmouseout="this.style.fontWeight='normal'">Logout</span>
            </u>]
            <%
                }
            %>
        </font>
      </td>
    </tr>
    <tr bgcolor="#99BBBB">
      <td nowrap valign="bottom" align="center" colspan="10" nowrap>
            <!-- <img src="../../common/images/btnSearch.gif" id="btnSearch" width="77" height="17" name="search" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('search','','../../common/images/btnSearchOver.gif',1)" onClick="showSearchDialog()"> -->
            <img src="/PORTAL/common/images/btnSearch.gif" id="btnSearch" width="77" height="17" name="search" onMouseOut="swapImage('btnSearch', 'out')" onMouseOver="swapImage('btnSearch', 'over')" onClick="showSearchDialog()">
      </td>
    </tr>
  </table>

<!-- ****** HIDDEN FIELDS ***** //-->
  	<input type="hidden" name="transaction" value="">
    <input type="hidden" name="action"      value="">
    <input type="hidden" name="jumpToTarget" value="">

</form>

</span>
</body>
</html>
