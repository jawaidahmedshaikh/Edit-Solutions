<%@ page  errorPage="/common/jsp/error.jsp" autoFlush="true" %>
<%@ page import="java.util.*,
                 edit.common.*,
                 edit.common.vo.*,
                 edit.common.vo.user.*,
                 fission.beans.*,
                 edit.portal.exceptions.*,
                 edit.common.exceptions.VOEditException,
                 fission.utility.*,
                 edit.portal.common.session.UserSession,
                 role.ClientRole,
                 client.ClientDetail" %>

<%
    ClientRole clientRole = (ClientRole) request.getAttribute("clientRole");
    ClientDetail clientDetail = (ClientDetail) request.getAttribute("clientDetail");
    String clientRolePK = "";
    String clientDetailFK = "";

    String redirectError = (String) request.getAttribute("redirectError");
    if (redirectError == null)
    {
        redirectError = "";
    }

    String redirectType = Util.initString((String) request.getAttribute("redirectType"), "");
    String agentClientName = "";
    String redirectStartDate = Util.initString((String) request.getAttribute("startDate"), "");
    String redirectStartMonth = "";
    String redirectStartDay = "";
    String redirectStartYear = "";
    if (!redirectStartDate.equals(""))
    {
        String[] redirectStartDateArray = Util.fastTokenizer(redirectStartDate, "/");
        redirectStartYear = redirectStartDateArray[0];
        redirectStartMonth = redirectStartDateArray[1];
        redirectStartDay = redirectStartDateArray[2];
    }
    String redirectStopDate = Util.initString((String) request.getAttribute("stopDate"), "");
    String redirectStopMonth = "";
    String redirectStopDay = "";
    String redirectStopYear = "";
    if (!redirectStopDate.equals(""))
    {
        String[] redirectStopDateArray = Util.fastTokenizer(redirectStopDate, "/");
        redirectStopYear = redirectStopDateArray[0];
        redirectStopMonth = redirectStopDateArray[1];
        redirectStopDay = redirectStopDateArray[2];
    }
    String agentPK = "0";
    String redirectPK = (String) request.getAttribute("redirectPK");
    if (redirectPK == null) {

        redirectPK = "";
    }

    RedirectVO[] redirectVOs = null;
    AgentVO agentVO = (AgentVO) session.getAttribute("agentVO");
    if (agentVO != null) {

        agentPK = agentVO.getAgentPK() + "";
        redirectVOs = agentVO.getRedirectVO();
    }

    if (redirectVOs != null)
    {
        for (int r = 0; r < redirectVOs.length; r++)
        {
            String pk = redirectVOs[r].getRedirectPK() + "";

            if (pk.equals(redirectPK))
            {
                redirectType = redirectVOs[r].getRedirectTypeCT();
                redirectStartDate = redirectVOs[r].getStartDate();
                if (!redirectStartDate.equals(""))
                {
                    String[] redirectStartDateArray = DateTimeUtil.initDate(redirectStartDate);
                    redirectStartYear = redirectStartDateArray[0];
                    redirectStartMonth = redirectStartDateArray[1];
                    redirectStartDay = redirectStartDateArray[2];
                }
                redirectStopDate = redirectVOs[r].getStopDate();
                if (!redirectStopDate.equals(""))
                {
                    String[] redirectStopDateArray = DateTimeUtil.initDate(redirectStopDate);
                    redirectStopYear = redirectStopDateArray[0];
                    redirectStopMonth = redirectStopDateArray[1];
                    redirectStopDay = redirectStopDateArray[2];
                }
                if (redirectVOs[r].getClientRoleFK() > 0)
                {
                    ClientRoleVO clientRoleVO = (ClientRoleVO) redirectVOs[r].getParentVO(ClientRoleVO.class);
                    if (clientRoleVO != null)
                    {
                        ClientDetailVO clientDetailVO = (ClientDetailVO) clientRoleVO.getParentVO(ClientDetailVO.class);
                        if (clientDetailVO.getLastName() == null)
                        {
                            agentClientName = clientDetailVO.getCorporateName();
                        }
                        else
                        {
                            agentClientName = clientDetailVO.getLastName() + ", " + clientDetailVO.getFirstName();
                        }
                    }
                }
            }
        }

        if (clientDetail != null)
        {
            if (clientRole != null)
            {
                clientRolePK = clientRole.getClientRolePK().toString();
            }

            clientDetailFK = clientDetail.getClientDetailPK().toString();

            if (clientDetail.getLastName() == null)
            {
                agentClientName = clientDetail.getCorporateName();
            }
            else
            {
                agentClientName = clientDetail.getLastName() + ", " + clientDetail.getFirstName();
            }
        }
    }
    else if (clientDetail != null)
    {
        if (clientRole != null)
        {
            clientRolePK = clientRole.getClientRolePK().toString();
        }

        clientDetailFK = clientDetail.getClientDetailPK().toString();

        if (clientDetail.getLastName() == null)
        {
            agentClientName = clientDetail.getCorporateName();
        }
        else
        {
            agentClientName = clientDetail.getLastName() + ", " + clientDetail.getFirstName();
        }
    }

    String rowToMatchBase = redirectType;

    UserSession userSession = (UserSession) session.getAttribute("userSession");
%>

<html>

<script src="/PORTAL/common/javascript/commonJavascriptFunctions.js"></script>

<script language="Javascript1.2">

	var f = null;

    var redirectError = "<%= redirectError %>";
    var shouldShowLockAlert = true;;

    var colorMouseOver = "#00BB00";
    var colorHighlighted = "#FFFFCC";
    var colorMainEntry   = "#BBBBBB";

    function highlightRow() {

        var tdElement = window.event.srcElement;
        var currentRow = tdElement.parentElement;

        if (currentRow.tagName != "TR") {

            currentRow = currentRow.parentElement;
        }

        currentRow.style.backgroundColor = colorMouseOver;
    }

    function unhighlightRow() {

        var tdElement = window.event.srcElement;
        var currentRow = tdElement.parentElement;

        if (currentRow.tagName != "TR") {

            currentRow = currentRow.parentElement;
        }

        var className = currentRow.className;

        if (currentRow.isSelected != "true") {

            if (className == "highLighted") {

                currentRow.style.backgroundColor = colorHighlighted;
            }

            else {

                currentRow.style.backgroundColor = colorMainEntry;
            }
        }
        else {

            currentRow.style.backgroundColor = colorHighlighted;
        }
    }

	function init() {

		f = document.agentRedirectForm;

		top.frames["main"].setActiveTab("redirectTab");

        if (redirectError != "") {

            alert(redirectError);
        }

        var agentIsLocked = <%= userSession.getAgentIsLocked()%>;
        var username = "<%= userSession.getUsername() %>";
        var elementPK = "<%= userSession.getAgentPK() %>";
		top.frames["header"].updateLockState(agentIsLocked, username, elementPK);

        shouldShowLockAlert = !agentIsLocked;

        for (var i = 0; i < f.elements.length; i++)
        {
            var elementType = f.elements[i].type;

            if ( (elementType == "text" || elementType == "button") && (shouldShowLockAlert == true) )
            {
                f.elements[i].onclick = showLockAlert;
                f.elements[i].onkeydown = showLockAlert;
            }
        }
	}

    function showLockAlert(){

        if (shouldShowLockAlert == true){

            alert("The Agent can not be edited.");

            return false;
        }
    }

	function selectRow() {

		var tdElement = window.event.srcElement;
		var trElement = tdElement.parentElement;

		var key = trElement.id;

		f.redirectPK.value = key;

		sendTransactionAction("AgentDetailTran", "showRedirectDetailSummary", "_self");
	}

    function addRedirect()
    {
        var width = .75 * screen.width;
        var height = .50 * screen.height;

		openDialog("","redirectSearch","left=0,top=0,resizable=no,width=" + width + ",height=" + height, "AgentDetailTran", "showRedirectSearchDialog", "redirectSearch");
    }

    function cancelRedirect() {

        sendTransactionAction("AgentDetailTran","clearFormForAddOrCancel","_self");
    }

    function saveRedirect()
    {
        if (textElementIsEmpty(f.agentClientName))
        {
            alert("Please Select Agent/Client Name");
            return;
        }

        sendTransactionAction("AgentDetailTran","saveRedirectToSummary","_self");
    }

    function deleteRedirect() {

        sendTransactionAction("AgentDetailTran","deleteRedirect","_self");
    }

	function openDialog(theURL,winName,features,transaction,action)
    {
	  dialog = window.open(theURL,winName,features);

	  sendTransactionAction(transaction, action, winName);
	}

	function sendTransactionAction(transaction, action, target) {

    	f.transaction.value=transaction;
    	f.action.value=action;

    	f.target = target;

    	f.submit();
	}

    function showRedirectAccumsDialog() {

        var width = .30 * screen.width;
        var height = .40 * screen.height;

		openDialog("","accumulations","left=0,top=0,resizable=no,width=" + width + ",height=" + height, "AgentDetailTran", "showRedirectAccumsDialog", "accumulations");
    }
</script>

<head>
<title>Agent Redirect</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta http-equiv="Cache-Control" content="no-store">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Expires" content="0">

<link href="/PORTAL/common/css/PORTAL.css" rel="stylesheet" type="text/css">
</head>

<body class="mainTheme" onLoad="init();" style="border-style:solid; border-width:1;">
<form name= "agentRedirectForm" method="post" action="/PORTAL/servlet/RequestManager">

<input type="hidden" name="page" value="">
<jsp:include page="agentInfoHeader.jsp" flush="true"/>

<span id="mainContent" style="border-style:solid; border-width:1;  position:relative; width:100%; height:100%; top:0; left:0; z-index:0; overflow:visible">
  <table width="100%" height="40%">
    <tr>
      <td align="left" nowrap>Redirect Type:&nbsp;
        <input disabled type="text" name="redirectTypeCT" maxlength="20" size="20" value="<%= redirectType %>">
      </td>
      <td align="left" nowrap>Agent/Client Name:&nbsp;
        <input disabled type="text" name="agentClientName" maxlength="50" size="50" value="<%= agentClientName %>">
      </td>
    </tr>
    <tr>
      <td align="left" nowrap>Start Date:&nbsp;
        <input type="text" name="redirectStartMonth" maxlength="2" size="2" value="<%= redirectStartMonth %>">
        /
        <input type="text" name="redirectStartDay" maxlength="2" size="2" value="<%= redirectStartDay %>">
        /
        <input type="text" name="redirectStartYear" maxlength="4" size="4" value="<%= redirectStartYear %>">
      </td>
      <td align="left" nowrap>Stop Date:&nbsp;
        <input type="text" name="redirectStopMonth" maxlength="2" size="2" value="<%= redirectStopMonth %>">
        /
        <input type="text" name="redirectStopDay" maxlength="2" size="2" value="<%= redirectStopDay %>">
        /
        <input type="text" name="redirectStopYear" maxlength="4" size="4" value="<%= redirectStopYear %>">
      </td>
    </tr>
  </table>
  <table width="100%" height="2%" border="0" cellspacing="0" cellpadding="0">
	<tr>
	  <td align="left">
		<input type="button" name="add" value="   Add   " onClick="addRedirect()">
		<input type="button" name="save" value=" Save " onClick="saveRedirect()">
		<input type="button" name="cancel" value="Cancel" onClick="cancelRedirect()">
		<input type="button" name="delete" value="Delete" onClick="deleteRedirect()">
	  </td>
	</tr>
  </table>
  <table class="summaryArea" id="summaryTable" width="100%" height="30%" border="0" cellspacing="0" cellpadding="0">
    <tr height="1%">
      <th width="25%" align="left">Type</th>
      <th width="25%" align="left">To Client</th>
	  <th width="25%" align="left">Start Date</th>
      <th width="25%" align="left">Stop Date</th>
    </tr>
    <tr width="100%" height="99%">
      <td colspan="10">
        <span class="scrollableContent">
          <table class="scrollableArea" id="redirectSummary" width="100%" border="0" cellspacing="0" cellpadding="0">
            <%
                String rowToMatch = "";
                String trClass = "";
                String selected = "";

                if (redirectVOs != null)
                {
                    for (int r = 0; r < redirectVOs.length; r++)
                    {
                    if (!redirectVOs[r].getVoShouldBeDeleted())
                    {
                        String sRedirectPK = redirectVOs[r].getRedirectPK() + "";
                        String sRedirectType = redirectVOs[r].getRedirectTypeCT();
                        String sStartDate = DateTimeUtil.formatYYYYMMDDToMMDDYYYY(redirectVOs[r].getStartDate());
                        String sStopDate = DateTimeUtil.formatYYYYMMDDToMMDDYYYY(redirectVOs[r].getStopDate());
                        String toClient = "";
                        ClientRoleVO sClientRoleVO = (ClientRoleVO) redirectVOs[r].getParentVO(ClientRoleVO.class);
                        if (sClientRoleVO != null)
                        {
                            ClientDetailVO sClientDetailVO = (ClientDetailVO) sClientRoleVO.getParentVO(ClientDetailVO.class);
                            if (sClientDetailVO.getLastName() == null)
                            {
                                toClient = Util.initString(sClientDetailVO.getCorporateName(), "");
                            }
                            else
                            {
                                toClient = sClientDetailVO.getLastName();
                            }
                        }

                        rowToMatch =  sRedirectType;

                        if (rowToMatch.equals(rowToMatchBase))
                        {
                            trClass = "highLighted";
                            selected = "true";
                        }
                        else
                        {
                            trClass = "mainEntry";
                            selected="false";
                        }
			%>
			<tr class="<%= trClass %>" isSelected="<%= selected %>" id="<%= sRedirectPK %>"
                onClick="selectRow()" onMouseOver="highlightRow()" onMouseOut="unhighlightRow()">
			  <td width="25%" nowrap>
				<%= sRedirectType %>
			  </td>
              <td width="25%" nowrap>
                <%= toClient %>
              </td>
			  <td width="25%" nowrap>
			 	<%= sStartDate %>
			  </td>
			  <td width="25%" nowrap>
				<%= sStopDate %>
			  </td>
			</tr>
            <%
                        }//end if
                    }// end for
                } // end if
            %>
          </table>
        </span>
      </td>
    </tr>
  </table>
</span>


<!-- ****** HIDDEN FIELDS ***** //-->
 <input type="hidden" name="transaction"  value="">
 <input type="hidden" name="action"       value="">

 <input type="hidden" name="agentPK" value="<%= agentPK %>">
 <input type="hidden" name="redirectPK" value="<%= redirectPK %>">

 <input type="hidden" name="redirectType" value="<%= redirectType %>">
 <input type="hidden" name="clientRolePK" value="<%= clientRolePK %>">
 <input type="hidden" name="clientDetailFK" value="<%= clientDetailFK %>">

</body>
</html>