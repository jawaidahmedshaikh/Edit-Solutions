<!-- ****** JAVA CODE ***** //-->

<%@ page  errorPage="/common/jsp/error.jsp" autoFlush="true" %>
<%@ page import="java.util.*,
                 edit.common.*,
                 edit.common.vo.*,
                 edit.common.vo.user.*,
                 fission.beans.*,
                 edit.portal.common.session.UserSession,
                 role.ClientRole,
                 edit.services.db.hibernate.SessionHelper,
                 fission.utility.*" %>

<jsp:useBean id="preferenceSessionBean"
    class="fission.beans.SessionBean" scope="session"/>
<%

    String preferencePK = (String) request.getAttribute("preferencePK");
%>

<%!
    private TreeMap sortContractClientRoles(ClientRole[] clientRoles)
    {
		TreeMap sortedContractClientRoles = new TreeMap();

		for (int i = 0; i < clientRoles.length; i++)
        {
            if (!clientRoles[i].getRoleTypeCT().equals(ClientRole.ROLETYPECT_AGENT))
            {
                if (clientRoles[i].getReferenceID() != null)
                {
                    sortedContractClientRoles.put(clientRoles[i].getReferenceID(), clientRoles[i].getReferenceID());
                }
            }
		}

		return sortedContractClientRoles;
	}

    private TreeMap sortAgentClientRoles(ClientRole[] clientRoles)
    {
		TreeMap sortedAgentClientRoles = new TreeMap();

		for (int i = 0; i < clientRoles.length; i++)
        {
            if (clientRoles[i].getRoleTypeCT().equals(ClientRole.ROLETYPECT_AGENT))
            {
                if (clientRoles[i].getReferenceID() != null)
                {
                    sortedAgentClientRoles.put(clientRoles[i].getReferenceID(), clientRoles[i].getReferenceID());
                }
            }
		}

		return sortedAgentClientRoles;
	}
%>

<html>

<script src="/PORTAL/common/javascript/commonJavascriptFunctions.js"></script>

<script language="JavaScript1.2">

<!--******* JAVASCRIPT ******//-->

var f = null;

var shouldShowLockAlert = true;

var tabLayers = new Array();
var isDragging = false;

function init() {

	f = document.theForm;
}

</script>
<head>
<title>Contract/Agent Information</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta http-equiv="Cache-Control" content="no-store">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Expires" content="0">

<link href="/PORTAL/common/css/PORTAL.css" rel="stylesheet" type="text/css">
</head>

<!-- ****** HTML CODE ***** //-->

<body class="mainTheme" onLoad="init()" style="border-style:solid; border-width:1">
<form name="theForm" method="post" action="/PORTAL/servlet/RequestManager">

<span class="mainContent" style="position:relative; width:100%; height:100%; top:0; left:0; z-index:0; overflow:visible">
<table class="summaryArea" id="summaryTable" width="100%" height="80%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <th align="left" width="20%">Preference Type</th>
    <th align="left" width="20%">Source</th>
    <th align="left" width="20%">Contract #</th>
    <th align="left" width="20%">Agent #</th>
    <th align="left" width="20%">O/R</th>
  </tr>
  <tr width="100%" height="99%">
    <td colspan="7">
      <span class="scrollableContent">
        <table class="scrollableArea" id="preferenceSummary" width="100%" border="0" cellspacing="0" cellpadding="0">
            <%
              PageBean pageBean = preferenceSessionBean.getPageBean(preferencePK);
              String disbursementSource = pageBean.getValue("disbursementSource");
              String overrideStatus = pageBean.getValue("overrideStatus");
              String preferenceType = Util.initString(pageBean.getValue("preferenceType"), "");

              ClientRole[] clientRoles = ClientRole.findByPreferenceFK(new Long(preferencePK));
              Map sortedContractRoles = sortContractClientRoles(clientRoles);
              Map sortedAgentRoles = sortAgentClientRoles(clientRoles);

              Iterator it = sortedContractRoles.values().iterator();
              Iterator it2 = sortedAgentRoles.values().iterator();

              while (it.hasNext())
              {
                  String referenceID = (String) it.next();
            %>

            <tr>
              <td width="20%" align="left" nowrap>
                <%= preferenceType %>
              </td>
              <td width="20%" align="left" nowrap>
                <%= disbursementSource %>
              </td>
              <td width="20%" align="left" nowrap>
                <%= referenceID %>
              </td>
              <td width="20%" align="left" nowrap>
                &nbsp;
              </td>
              <td width="20%" align="left" nowrap>
                <%= overrideStatus %>
              </td>
            </tr>
            <%
              }// end while

              while (it2.hasNext())
              {
                  String referenceID = (String) it2.next();
            %>

            <tr>
              <td width="20%" align="left" nowrap>
                <%= preferenceType %>
              </td>
              <td width="20%" align="left" nowrap>
                <%= disbursementSource %>
              </td>
              <td width="20%" align="left" nowrap>
                &nbsp;
              </td>
              <td width="20%" align="left" nowrap>
                <%= referenceID %>
              </td>
              <td width="20%" align="left" nowrap>
                <%= overrideStatus %>
              </td>
            </tr>
            <%
              }// end while
            %>
        </table>
      </span>
    </td>
  </tr>
</table>
<table id="table3" width="100%" border="0" cellspacing="6" cellpadding="0">
  <tr>
    <td align="right"colspan="4" nowrap>
      <input id="btnCancel" type="button" value=" Cancel " onClick="window.close()">
    </td>
  </tr>
</table>
</span>
<!-- ****** HIDDEN FIELDS ***** //-->
  	<input type="hidden" name="transaction" value="">
    <input type="hidden" name="action"      value="">

</body>
</form>
</html>
