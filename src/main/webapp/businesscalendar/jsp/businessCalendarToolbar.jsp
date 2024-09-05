<%@ page import="edit.portal.common.session.UserSession"%><!-- ****** JAVA CODE ***** //-->
<%
    UserSession userSession = (UserSession) session.getAttribute("userSession");
%>

<html>
<head>
<title>Treaty Relations</title>
<link href="/PORTAL/common/css/EDITSolutions.css" rel="stylesheet" type="text/css">
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">

<script src="/PORTAL/common/javascript/commonJavascriptFunctions.js"></script>
<script src="/PORTAL/common/javascript/help.js"></script>

<script language="JavaScript">
<!--
<!--******* JAVASCRIPT ******//-->

var tmpTransaction = "";
var tmpAction = "";
var f = null;

function init() {

	f = document.theForm;
}

function jumpTo(jumpToTarget){

        var action = null;

        if (jumpToTarget == "CommissionContract"){

            action = "showCommissionContract";
        }
        else if (jumpToTarget == "Client"){

            action="showIndividuals";
        }
        else if (jumpToTarget == "Roles"){

            action="showRoleAdmin";
        }
        else if (jumpToTarget == "AgentDetail"){

            action = "showAgentDetail";
        }
        else if (jumpToTarget == "AgentHierarchy"){

            action = "showAgentHierarchy";
        }
        else if (jumpToTarget == "Main")
        {
            action = "goToMain";
        }
        else if (jumpToTarget == "Logout")
        {
            action = "doLogOut";
        }

        sendTransactionAction("PortalLoginTran",action, "_top");
}
//-->
</script>
</head>

<!-- ****** HTML CODE ***** //-->
<body  class="mainTheme" onLoad="init()">
<span style="border-style:solid; border-width:1;  position:relative; width:100%; height:100%; top:0; left:0; z-index:0; overflow:visible">

<form name="theForm" method="post" action="/PORTAL/servlet/RequestManager">
  <table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      <td colspan="1" bgcolor="#30548E">
        &nbsp;
      </td>
      <td colspan="1" align="center" bgcolor="#30548E">
        <b><i>
            <font face="Arial, Helvetica, sans-serif">
               <font color="#FFFFFF" size="2">EDIT<font size="1">SOLUTIONS- </FONT>Business Calendar</font>
           </FONT>
        </i></b>
        </td>
      <td colspan="1" bgcolor="#30548E" id="lockMessage" align="right">
        &nbsp;
      </td>
    </tr>


    <tr bgcolor="#30548E">
      <td colspan="2" NOWRAP>
        &nbsp;
      </td>
      <td colspan="1" align="right" NOWRAP>
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
  </table>

<!-- ****** HIDDEN FIELDS ***** //-->
  	<input type="hidden" name="transaction" value="">
    <input type="hidden" name="action"      value="">

</form>

</span>
</body>
</html>