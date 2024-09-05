<!-- ****** JAVA CODE ***** //-->

<%@ page  errorPage="/common/jsp/error.jsp" autoFlush="true" %>
<%@ page import="java.util.*,
                 edit.common.*,
                 edit.common.vo.*,
                 fission.beans.*,
                 edit.portal.common.session.UserSession,
                 fission.utility.*" %>

<%
    UIAgentHierarchyVO[] uiAgentHierarchyVOs = (UIAgentHierarchyVO[]) session.getAttribute("uiAgentHierarchyVOs");
    CommissionProfileVO[] commissionProfileVOs = (CommissionProfileVO[]) session.getAttribute("commissionProfileVOs");

    String agentId   	= "";
    String agentType    = "";
    String lastName     = "";
    String firstName    = "";
    String middleName   = "";
    String name         = "";
    String contractCode = "";
    String commProcess  = "";
    String selectedAgentHierarchyPK = (String) request.getAttribute("selectedAgentHierarchyPK");
    String selectedAgentSnapshotPK = (String) request.getAttribute("selectedAgentSnapshotPK");
    if (selectedAgentSnapshotPK == null) {

        selectedAgentSnapshotPK = "";
    }
    String selectedCommProfileFK = (String) request.getAttribute("selectedCommProfileFK");
    if (selectedCommProfileFK == null) {

        selectedCommProfileFK = "";
    }

    boolean snapshotFound = false;
    if (uiAgentHierarchyVOs != null) {

        for (int h = 0; h < uiAgentHierarchyVOs.length; h++) {

            AgentHierarchyVO agentHierarchyVO = uiAgentHierarchyVOs[h].getAgentHierarchyVO();
            AgentSnapshotVO[] agentSnapshotVOs = agentHierarchyVO.getAgentSnapshotVO();
            for (int s = 0; s < agentSnapshotVOs.length; s++) {

                String agentSnapshotPK = agentSnapshotVOs[s].getAgentSnapshotPK() + "";

                if (agentSnapshotPK.equals(selectedAgentSnapshotPK)) {

                    PlacedAgentVO placedAgentVO = (PlacedAgentVO) agentSnapshotVOs[s].getParentVO(PlacedAgentVO.class);
                    AgentContractVO agentContractVO = (AgentContractVO) placedAgentVO.getParentVO(AgentContractVO.class);
                    AgentVO agentVO = (AgentVO) agentContractVO.getParentVO(AgentVO.class);
                    ClientRoleVO clientRoleVO = (ClientRoleVO) agentVO.getParentVO(ClientRoleVO.class);
                    ClientDetailVO clientDetailVO = (ClientDetailVO) clientRoleVO.getParentVO(ClientDetailVO.class);

                    CommissionProfileVO commissionProfileVO = (CommissionProfileVO) agentSnapshotVOs[s].getParentVO(CommissionProfileVO.class);

                    agentId = agentVO.getAgentNumber();
                    agentType = agentVO.getAgentTypeCT();
                    name = getClientDetailName(clientDetailVO);
                    contractCode = commissionProfileVO.getContractCodeCT();
                    commProcess = agentContractVO.getCommissionProcessCT();

                    snapshotFound = true;
                    break;
                }
            }

            if (snapshotFound) {

                break;
            }
        }
    }

    String rowToMatchBase = selectedAgentSnapshotPK;
    String cRowToMatchBase = selectedCommProfileFK;

    UserSession userSession = (UserSession) session.getAttribute("userSession");
%>

<%!
    private String getClientDetailName(ClientDetailVO clientDetailVO)
    {
        String name = null;

        if (clientDetailVO.getTrustTypeCT().equalsIgnoreCase("CorporateTrust") ||
            clientDetailVO.getTrustTypeCT().equalsIgnoreCase("Corporate") ||
            clientDetailVO.getTrustTypeCT().equalsIgnoreCase("LLC"))
        {
            name = clientDetailVO.getCorporateName();
        }
        else
        {
            name = Util.initString(clientDetailVO.getLastName(), "") + ", " +
                   Util.initString(clientDetailVO.getFirstName(), "") +
                   Util.initString(clientDetailVO.getMiddleName(), "");
        }

        return name;
    }

%>

<html>

<!-- ***** JAVASCRIPT *****  -->

<script language="Javascript1.2">

    var f = null;

    var colorMouseOver = "#00BB00";
    var colorHighlighted = "#FFFFCC";
    var colorMainEntry   = "#BBBBBB";

    var shouldShowLockAlert = true;
    var editableContractStatus = true;

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

        if (className == "highLighted") {

            currentRow.style.backgroundColor = colorHighlighted;
        }

        else {

            currentRow.style.backgroundColor = colorMainEntry;
        }
    }

    function init() {

	    f = document.agentHierarchyForm;

        shouldShowLockAlert = <%= ! userSession.getSegmentIsLocked() %>;

        // check for terminated status to disallow contract updates
        editableContractStatus = <%= userSession.getEditableSegmentStatus()%>;
        
        for (var i = 0; i < f.elements.length; i++)
        {
            var elementType = f.elements[i].type;

            if (f.elements[i].name != "close") {

                if (elementType == "button" && (shouldShowLockAlert == true || editableContractStatus == false))
                {
                    f.elements[i].onclick = showLockAlert;
                }
            }
        }
	}

    function showLockAlert(){

    	if (shouldShowLockAlert == true)
        {
            alert("The Contract Cannot Be Edited.");

            return false;
            
        } else if (editableContractStatus == false) {
        	
        	alert("This Contract Cannot Be Edited Due to Terminated Status.");

            return false;
        }
    }

    function selectHierarchyRow() {

        var tdElement = window.event.srcElement;
        var trElement = tdElement.parentElement;

        var agentSnapshotPK = trElement.snapshotPK;
        f.selectedAgentSnapshotPK.value = agentSnapshotPK;

        sendTransactionAction("ContractDetailTran", "showSelectedHierarchyRow", "_self");
    }

    function selectProfileRow() {

        var tdElement = window.event.srcElement;
        var trElement = tdElement.parentElement;

        var commProfilePK = trElement.commProfilePK;
        f.selectedCommProfileFK.value = commProfilePK;

        sendTransactionAction("ContractDetailTran", "selectCommProfileForAgent", "_self");
    }

    function showCommissionOverrides(){

        var width = .40 * screen.width;
        var height= .20 * screen.height;

        if (f.selectedAgentSnapshotPK.value == "") {

            alert("Please Select Agent To View Commission Overrides");
        }
        else {

            openDialog("","commissionOverrides","top=0,left=0,width=" + width + ",height=" + height,"ContractDetailTran","showCommissionOverrides");
        }
    }

	function openDialog(theURL,winName,features,transaction,action) {

	  dialog = window.open(theURL,winName,features);

	  sendTransactionAction(transaction, action, winName);
	}

    function checkForEnter(){

        var eventObj = window.event;

        if (eventObj.keyCode == 13){

            doSearch();
        }
    }

    function saveHierarchy() {

        sendTransactionAction("ContractDetailTran","saveAgentHierarchy","_self");
    }

    function closeHierarchyDialog(){

        sendTransactionAction("ContractDetailTran","closeAgentHierarchy","contentIFrame");
        window.close();
    }

    function sendTransactionAction(transaction, action, target) {

		f.transaction.value = transaction;
    	f.action.value = action;

    	f.target = target;

    	f.submit();
    }

</script>

<head>
<title>Hierarchy</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta http-equiv="Cache-Control" content="no-store">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Expires" content="0">

<link href="/PORTAL/common/css/PORTAL.css" rel="stylesheet" type="text/css">
</head>

<body class="dialog" onLoad="init()" style="border-style:solid; border-width:1">

<form  name="agentHierarchyForm" method="post" action="/PORTAL/servlet/RequestManager">

<span id="span1" style="border-style:solid; border-width:1;  position:relative; width:100%; height:100%; top:0; left:0; z-index:0; overflow:visible">
  <table width="100%" height="38%" border="0" cellspacing="0" cellpadding="4">
    <tr height="5%">
	  <td align="left" nowrap>Agent Number:&nbsp;
		<input disabled type="text" name="agentId" maxlength="11" size="11" value="<%= agentId %>">
	  </td>
	  <td align="left" nowrap>Agent Type:&nbsp;
		<input disabled type="text" name="agentType" maxlength="20" size="20" value="<%= agentType %>">
	  </td>
	</tr>
	<tr height="5%">
	  <td align="left" nowrap colspan="2">Name:&nbsp;
	    <input disabled type="text" name="name" size="40" value="<%= name %>">
	  </td>
	</tr>
    <tr>
	  <td align="left" nowrap>Contract Code:&nbsp;
		<input disabled type="text" name="contractCode" maxlength="11" size="11" value="<%= contractCode %>">
	  </td>
	  <td align="left" nowrap rowspan="2">Commission Profiles:&nbsp;
        <table class="summaryArea" id="summaryTable" width="90%" height="100%" border="0" cellspacing="0" cellpadding="0">
          <tr height="1%">
            <th width="50%" align="left">Commission Level</th>
            <th width="50%" align="left">Commission Option</th>
          </tr>
          <tr width="100%" height="99%">
            <td colspan="2">
              <span class="scrollableContent" style="border-style:solid; border-width:1; position:relative; width:100%; height:100%; top:0; left:0; z-index:0; overflow:scroll">
                <table class="scrollableArea" id="investmentSummary" width="100%" border="0" cellspacing="0" cellpadding="0">
                  <%
                    String cRowToMatch = "";
                    String cClassName = "";
                    String cSelected = "";

                    String cCommProfilePK = "";
                    String cCommLevel = "";
                    String cCommOption = "";

                    if (commissionProfileVOs != null) {

                        for (int c = 0; c < commissionProfileVOs.length; c++) {

                            cCommProfilePK = commissionProfileVOs[c].getCommissionProfilePK() + "";
                            cCommOption = commissionProfileVOs[c].getCommissionOptionCT();
                            cCommLevel = commissionProfileVOs[c].getCommissionLevelCT();

                            cRowToMatch = cCommProfilePK;
                            if (cRowToMatch.equals(cRowToMatchBase)) {

                                cClassName = "highLighted";
                                cSelected = "true";
                            }
                            else {

                                cClassName = "mainEntry";
                                cSelected = "false";
                            }
                  %>
                  <tr class="<%= cClassName %>" isSelected="<%= cSelected %>" commProfilePK="<%= cCommProfilePK %>"
                      onClick="selectProfileRow()" onMouseOver="highlightRow()" onMouseOut="unhighlightRow()">
                    <td width="50%" nowrap>
                      <%= cCommLevel %>
                    </td>
                    <td width="50%" nowrap>
                      <%= cCommOption %>
                    </td>
                  </tr>
                  <%
                        } //end for
                    } // end if
                  %>
                </table>
              </span>
            </td>
          </tr>
        </table>
	  </td>
	</tr>
    <tr>
	  <td align="left" nowrap>Comm Process:&nbsp;
		<input disabled type="text" name="commProcess" maxlength="20" size="20" value="<%= commProcess %>">
	  </td>
    </tr>
    <tr>
      <td align="left" nowrap>
        <a href ="javascript:showCommissionOverrides()">Commission Overrides</a>
      </td>
    </tr>
  </table>
  <br>
  <br>
  <table width="100%" height="2%" border="0" cellspacing="0" cellpadding="0">
	<tr height="100%">
	  <td align="left">
	    <input type="button" name="save" value=" Save " onClick="saveHierarchy()">
	    <input type="button" name="cancel" value="Cancel" onClick="cancelHierarchy()">
	  </td>
	</tr>
  </table>
  <table class="summaryArea" width="100%" height="38%" border="0" cellspacing="0" cellpadding="0">
    <tr height="1%">
      <th align="left" width="14%">Name</th>
      <th align="left" width="14%">Agent Number</th>
      <th align="left" width="14%">Situation</th>
      <th align="left" width="14%">Agent Type</th>
      <th align="left" width="14%">Comm Process</th>
      <th align="left" width="14%">Comm Level</th>
      <th align="left" width="14%">Comm Option</th>
    </tr>
    <tr width="100%" height="99%">
      <td colspan="7">
        <span class="scrollableContent">
          <table class="scrollableArea" id="investmentSummary" width="100%" border="0" cellspacing="0" cellpadding="0">
            <%
                String hClassName = "mainEntry";
                String hSelected = "false";
                String hRowToMatch = "";

                String sAgentSnapshotPK = "";
                String sAgentId = "";
                String sAgentType = "";
                String sLastName = "";
                String sFirstName = "";
                String sName = "";
                String sCommLevel = "";
                String sCommOption = "";
                String sCommProcess = "";
                String sSituation = "";

                if (uiAgentHierarchyVOs != null) {

                    for (int h = 0; h < uiAgentHierarchyVOs.length; h++) {

                        AgentHierarchyVO agentHierarchyVO = uiAgentHierarchyVOs[h].getAgentHierarchyVO();
                        if ((agentHierarchyVO.getAgentHierarchyPK() + "").equals(selectedAgentHierarchyPK))
                        {
                            AgentSnapshotVO[] agentSnapshotVOs = agentHierarchyVO.getAgentSnapshotVO();
                            for (int s = 0; s < agentSnapshotVOs.length; s++) {

                                PlacedAgentVO placedAgentVO = (PlacedAgentVO) agentSnapshotVOs[s].getParentVO(PlacedAgentVO.class);
                                AgentContractVO agentContractVO = (AgentContractVO) placedAgentVO.getParentVO(AgentContractVO.class);
                                AgentVO agentVO = (AgentVO) agentContractVO.getParentVO(AgentVO.class);
                                ClientRoleVO clientRoleVO = (ClientRoleVO) agentVO.getParentVO(ClientRoleVO.class);
                                ClientDetailVO clientDetailVO = (ClientDetailVO) clientRoleVO.getParentVO(ClientDetailVO.class);

                                CommissionProfileVO commProfileVO = (CommissionProfileVO) agentSnapshotVOs[s].getParentVO(CommissionProfileVO.class);

                                sAgentSnapshotPK = agentSnapshotVOs[s].getAgentSnapshotPK() + "";
                                sAgentId = agentVO.getAgentNumber();
                                sAgentType = agentVO.getAgentTypeCT();
                                sName = getClientDetailName(clientDetailVO);
                                sCommLevel = commProfileVO.getCommissionLevelCT();
                                sCommOption = commProfileVO.getCommissionOptionCT();
                                sCommProcess = agentContractVO.getCommissionProcessCT();
                                sSituation = Util.initString(placedAgentVO.getSituationCode(), "&nbsp;");

                                hRowToMatch = sAgentSnapshotPK;

                                if (hRowToMatch.equals(rowToMatchBase)) {

                                    hClassName = "highLighted";
                                    hSelected = "true";
                                }
                                else {

                                    hClassName = "mainEntry";
                                    hSelected = "false";
                                }
            %>
            <tr class="<%= hClassName %>" isSelected="<%= hSelected %>" snapshotPK="<%= sAgentSnapshotPK %>"
                onClick="selectHierarchyRow()" onMouseOver="highlightRow()" onMouseOut="unhighlightRow()">
              <td nowrap width="14%">
                <%= sName %>
              </td>
              <td nowrap width="14%">
                <%= sAgentId %>
              </td>
              <td nowrap width="14%">
                <%= sSituation %>
              </td>
              <td nowrap width="14%">
                <%= sAgentType %>
              </td>
              <td nowrap width="14%">
                <%= sCommProcess %>
              </td>
              <td nowrap width="14%">
                <%= sCommLevel %>
              </td>
              <td nowrap width="14%">
                <%= sCommOption %>
              </td>
            </tr>
            <%
                            } // end snapshot for
                        } //end hierarchy if
                    } //end uiagenthierarchy for
                } //end if
            %>
            <tr class="filler">
                <td colspan="7">
                     &nbsp;
                </td>
            </tr>
          </table>
        </span>
      </td>
    </tr>
  </table>
  <table width="100%" height="2%">
    <tr>
      <td align="right">
        <input type="button" name="close" value="Close" onClick="closeHierarchyDialog()">
      </td>
    </tr>
  </table>
</span>

<!-- ****** HIDDEN FIELDS ***** //-->
<input type="hidden" name="transaction" value="">
<input type="hidden" name="action"      value="">
<input type="hidden" name="selectedAgentHierarchyPK" value="<%= selectedAgentHierarchyPK %>">
<input type="hidden" name="selectedAgentSnapshotPK" value="<%= selectedAgentSnapshotPK %>">
<input type="hidden" name="selectedCommProfileFK" value="<%= selectedCommProfileFK %>">

</form>
</body>
</html>