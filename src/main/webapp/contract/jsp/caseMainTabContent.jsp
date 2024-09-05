<!--
 * 
 * User: dlataille
 * Date: April 30, 2007
 * Time: 8:51:30 AM
 * 
 * (c) 2000 - 2007 Systems Engineering Group, LLC.  All rights Reserved
 *  Systems Engineering Group, LLC Propietary and confidential.  Any use
 *  subject to the license agreement.
 -->

<table style="position:relative; top:0; left:0" cellspacing="0" cellpadding="0"
       border="0" width="100%">
  <tr>
    <td>
      <jsp:include page="/contract/jsp/caseCommandHeader.jsp" flush="true"></jsp:include>
    </td>
  </tr>
  <tr valign="bottom">
    <td>
      <span id="imageCollection">
        <img id="case" src="/PORTAL/contract/images/caseTag.gif"
             style="position:relative; top:4; left:0" onclick="showCase()"
             name="case" width="60" height="26"></img>
        <img id="group" src="/PORTAL/contract/images/groupTab.gif"
             style="position:relative; top:4; left:0" onclick="showGroup()"
             name="group" width="60" height="26"></img>
        <img id="requirements" src="/PORTAL/contract/images/requirementsTab.gif"
             style="position:relative; top:4; left:0"
             onclick="showRequirements()" name="requirements" width="60"
             height="26"></img>
        <img id="agents" src="/PORTAL/contract/images/agentsTab.gif"
             style="position:relative; top:4; left:0" onclick="showAgents()"
             name="agents" width="60" height="26"></img>
        <img id="history" src="/PORTAL/contract/images/historyTab.gif"
             style="position:relative; top:4; left:0" onclick="showHistory()"
             name="caseHistory" width="60" height="26"></img>
      </span>
    </td>
  </tr>
  <tr bgcolor="#30548E" height="14">
    <td>&nbsp;</td>
  </tr>
  <tr bgcolor="#99BBBB" height="10">
    <td>&nbsp;</td>
  </tr>
</table>