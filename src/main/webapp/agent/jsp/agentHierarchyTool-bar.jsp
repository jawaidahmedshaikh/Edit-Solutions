<%@ page import="edit.portal.common.session.UserSession"%>
<script type="text/javascript">

/**
 * A set of links for navigation.
 */
function jumpTo(jumpToTarget){

        var action = null;

        if (jumpToTarget == "CommissionProfile"){

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
        else if (jumpToTarget == "AgentBonusProgram"){

            action = "showAgentBonusProgram";
        }
        else if (jumpToTarget == "Main")
        {
            action = "goToMain";
        }
        else if (jumpToTarget == "Logout")
        {
            action = "doLogOut";
        }

        sendTransactionAction("PortalLoginTran",action, "_self");
}
</script>
<table width="100%" border="0" cellspacing="0" cellpadding="0" height="75">
  <tr bgcolor="#30548E" height="25">
    <td colspan="3" nowrap="nowrap">&nbsp;</td>
    <td colspan="4" align="center" bgcolor="#30548E" nowrap="nowrap">
      <b><i>
           
          <font face="Arial, Helvetica, sans-serif">
            <font color="#FFFFFF" size="2">
              EDIT
              <font size="1">SOLUTIONS- </font>
              Agent Hierarchy
            </font>
          </font>
           </i></b>
    </td>
    <td colspan="3" nowrap="nowrap">&nbsp;</td>
  </tr>
  <tr bgcolor="#30548E" height="25">
    <td colspan="6" nowrap="nowrap">
      <font color="#FFFFFF" size="2">
        [
        <u>
          <span onclick="jumpTo('CommissionProfile')"  style="font-weight: normal" onmouseover="this.style.fontWeight='bold'" onmouseout="this.style.fontWeight='normal'">Commission Profile</span>
        </u>
        ] &nbsp; [
        <u>
          <span onclick="jumpTo('AgentDetail')"  style="font-weight: normal" onmouseover="this.style.fontWeight='bold'" onmouseout="this.style.fontWeight='normal'">Agent Detail</span>
        </u>
        ] &nbsp; [
        <u>
          <span onclick="jumpTo('AgentBonusProgram')"  style="font-weight: normal" onmouseover="this.style.fontWeight='bold'" onmouseout="this.style.fontWeight='normal'">Agent Bonus Program</span>
        </u>
        ] &nbsp; [
        <u>
          <span onclick="jumpTo('Client')" style="font-weight: normal" onmouseover="this.style.fontWeight='bold'" onmouseout="this.style.fontWeight='normal'">Client</span>
        </u>
        ] &nbsp; [
        <u>
          <span onclick="jumpTo('Roles')"  style="font-weight: normal" onmouseover="this.style.fontWeight='bold'" onmouseout="this.style.fontWeight='normal'">Roles</span>
        </u>
        ]
      </font>
    </td>
    <td colspan="4" align="right" nowrap="nowrap">
      <font color="#FFFFFF" size="2">
        [
        <u>
          <span onclick="jumpTo('Main')"  style="font-weight: normal" onmouseover="this.style.fontWeight='bold'" onmouseout="this.style.fontWeight='normal'">Main</span>
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
  <tr bgcolor="#99BBBB" height="25">
    <td colspan="10" nowrap="nowrap">&nbsp;</td>
  </tr>
</table>