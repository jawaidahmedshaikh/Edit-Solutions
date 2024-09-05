<%@ page  errorPage="/common/jsp/error.jsp" autoFlush="true" %>
<%@ page import="java.util.*,
                 edit.common.*,
                 edit.common.vo.*,
                 edit.common.vo.user.*,
                 fission.beans.*,
                 edit.portal.exceptions.*,
                 edit.common.exceptions.VOEditException,
                 fission.utility.*,
                 edit.portal.common.session.UserSession" %>

<%
    String contractMessage = (String) request.getAttribute("contractMessage");
    if (contractMessage == null)
    {
        contractMessage = "";
    }

    CodeTableWrapper codeTableWrapper = CodeTableWrapper.getSingleton();

    CodeTableVO[] commissionProcesses = codeTableWrapper.getCodeTableEntries("COMMISSIONPROCESS");

    CodeTableVO[] contractCodeCTs = codeTableWrapper.getCodeTableEntries("CONTRACTCODE");

    String effectiveMonth = (String) request.getAttribute("effMonth");
    if (effectiveMonth == null) {

        effectiveMonth = "";
    }
    String effectiveDay = (String) request.getAttribute("effDay");
    if (effectiveDay == null) {

        effectiveDay = "";
    }
    String effectiveYear = (String) request.getAttribute("effYear");
    if (effectiveYear == null) {

        effectiveYear = "";
    }
    String stopMonth = (String) request.getAttribute("stopMonth");
    if (stopMonth == null) {

        stopMonth = "";
    }
    String stopDay = (String) request.getAttribute("stopDay");
    if (stopDay == null) {

        stopDay = "";
    }
    String stopYear = (String) request.getAttribute("stopYear");
    if (stopYear == null) {

        stopYear = "";
    }
    String commissionProcess = (String) request.getAttribute("commissionProcess");
    if (commissionProcess == null) {

        commissionProcess = "";
    }
    String additionalCompensationStatus = "unchecked";
    String annualizedStatus = "unchecked";
    String contractCodeCT = (String) request.getAttribute("contractCodeCT");
    if (contractCodeCT == null) {

        contractCodeCT = "0";
    }
    String agentPK = "0";
    String agentContractPK = (String) request.getAttribute("agentContractPK");
    if (agentContractPK == null) {

        agentContractPK = "0";
    }

    AgentContractVO[] agentContractVOs = null;
    AgentVO agentVO = (AgentVO) session.getAttribute("agentVO");
    if (agentVO != null) {

        agentPK = agentVO.getAgentPK() + "";
        agentContractVOs = agentVO.getAgentContractVO();
    }

    if (agentContractVOs != null) {

        for (int c = 0; c < agentContractVOs.length; c++) {

            String pk = agentContractVOs[c].getAgentContractPK() + "";
            String currentContractCodeCT = agentContractVOs[c].getContractCodeCT();
            String cp = agentContractVOs[c].getCommissionProcessCT();

            if ((pk.equals(agentContractPK) &&
                 !agentContractPK.equals("0")) ||
                (agentContractPK.equals("0") &&
                 contractCodeCT.equals(currentContractCodeCT) &&
                 commissionProcess.equals(cp))) {

                String effectiveDate = agentContractVOs[c].getContractEffectiveDate();
                String[] effectiveDateArray = DateTimeUtil.initDate(effectiveDate);
                effectiveMonth = effectiveDateArray[0];
                effectiveDay = effectiveDateArray[1];
                effectiveYear = effectiveDateArray[2];

                String stopDate = agentContractVOs[c].getContractStopDate();
                String[] stopDateArray = DateTimeUtil.initDate(stopDate);
                stopMonth = stopDateArray[0];
                stopDay = stopDateArray[1];
                stopYear = stopDateArray[2];

                commissionProcess = cp;

                AdditionalCompensationVO[] addtnlCompVOs = (AdditionalCompensationVO[]) agentContractVOs[c].getAdditionalCompensationVO();
                if (addtnlCompVOs != null && addtnlCompVOs.length > 0) {

                    additionalCompensationStatus = "checked";
                }
            }
        }
    }

    String rowToMatchBase = agentContractPK + contractCodeCT + commissionProcess;
    String contractRowToMatchBase = contractCodeCT;

    UserSession userSession = (UserSession) session.getAttribute("userSession");

    // Additional Compensation fields
    String adaType = (String) request.getAttribute("adaType");
    if (adaType == null) {

        adaType = "";
    }
    String annualizedMax = (String) request.getAttribute("annualizedMax");
    if (annualizedMax == null) {

        annualizedMax = "";
    }
    String serviceFeeStatus = (String) request.getAttribute("serviceFeeStatus");
    if (serviceFeeStatus == null) {

        serviceFeeStatus = "unchecked";
    }
    String bonusCommissionStatus = (String) request.getAttribute("bonusCommissionStatus");
    if (bonusCommissionStatus == null) {

        bonusCommissionStatus = "unchecked";
    }
    String ny91PctStatus = (String) request.getAttribute("ny91PctStatus");
    if (ny91PctStatus == null) {

        ny91PctStatus = "unchecked";
    }
    String additionalCompPK = (String) request.getAttribute("additionalCompensationPK");
    if (additionalCompPK == null) {

        additionalCompPK = "0";
    }
%>

<html>

<script src="/PORTAL/common/javascript/commonJavascriptFunctions.js"></script>

<script language="Javascript1.2">

	var f = null;

    var shouldShowLockAlert = true;;
    var contractCodeCT = "<%= contractCodeCT %>";
    var agentContractPK = "<%= agentContractPK %>";
    var contractMessage = "<%= contractMessage %>";

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

		f = document.agentContractForm;

		top.frames["main"].setActiveTab("contractTab");

        var agentIsLocked = <%= userSession.getAgentIsLocked()%>;
        var username = "<%= userSession.getUsername() %>";
        var elementPK = "<%= userSession.getAgentPK() %>";
		top.frames["header"].updateLockState(agentIsLocked, username, elementPK);

        if (contractMessage != "")
        {
            alert(contractMessage);
        }

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

	function selectContractRow() {

		var tdElement = window.event.srcElement;
		var trElement = tdElement.parentElement;

		var key = trElement.contractCodeCT;

		f.contractCodeCT.value = key;

		sendTransactionAction("AgentDetailTran", "selectCommissionContractForAgent", "_self");
	}

	function selectRow() {

		var tdElement = window.event.srcElement;
		var trElement = tdElement.parentElement;

		var key = trElement.agentContractPK;
        var contractCodeCTKey = trElement.selectedContractCodeCT;

		f.agentContractPK.value = key;
        f.selectedContractCodeCT.value = contractCodeCTKey;

		sendTransactionAction("AgentDetailTran", "showContractDetailSummary", "_self");
	}

    function showLockAlert(){

        if (shouldShowLockAlert == true){

            alert("The Agent can not be edited.");

            return false;
        }
    }

    function addContract() {

        sendTransactionAction("AgentDetailTran","clearFormForAddOrCancel","_self");
    }

    function cancelContract() {

        sendTransactionAction("AgentDetailTran","clearFormForAddOrCancel","_self");
    }

    function deleteContract() {

        f.contractCodeCT.value = contractCodeCT;
        f.agentContractPK.value = agentContractPK;
        sendTransactionAction("AgentDetailTran","deleteContract","_self");
    }

    function saveContract() {

        if (textElementIsEmpty(f.effMonth)
                || textElementIsEmpty (f.effDay)
                    || textElementIsEmpty (f.effYear))
        {
            alert("Please Enter EffectiveDate");
            return;
        }

        f.contractCodeCT.value = contractCodeCT;
        f.agentContractPK.value = agentContractPK;
        if (f.contractCodeCT.value == "0" ||
            f.contractCodeCT.value == "")
        {
            alert("Contract is Required.");
        }
        else
        {
            try
            {
                formatDate(f.effMonth.value, f.effDay.value, f.effYear.value, true);

                sendTransactionAction("AgentDetailTran","saveContractToSummary","_self");
            }
            catch (e)
            {
                alert("Agent Contract Effective Date Invalid");
            }
        }
    }

	function openDialog(theURL,winName,features,transaction,action) {

	  dialog = window.open(theURL,winName,features);

	  sendTransactionAction(transaction, action, winName);
	}

	function sendTransactionAction(transaction, action, target) {

    	f.transaction.value=transaction;
    	f.action.value=action;

    	f.target = target;

    	f.submit();
	}

    function showAdditionalCompensationDialog() {

        var width = .35 * screen.width;
        var height = .40 * screen.height;

        f.contractCodeCT.value = contractCodeCT;
        f.agentContractPK.value = agentContractPK;

		openDialog("","additionalCompensation","left=0,top=0,resizable=no,width=" + width + ",height=" + height, "AgentDetailTran", "showAdditionalCompensationDialog", "additionalCompensation");
    }

    function showAnnualizedDialog() {

        var width = .30 * screen.width;
        var height = .40 * screen.height;

		openDialog("","annualized","left=0,top=0,resizable=no,width=" + width + ",height=" + height, "AgentDetailTran", "showAnnualizedDialog", "annualized");
    }
</script>

<head>
<title>Agent Contract</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta http-equiv="Cache-Control" content="no-store">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Expires" content="0">

<link href="/PORTAL/common/css/PORTAL.css" rel="stylesheet" type="text/css">
</head>

<body class="mainTheme" onLoad="init();" style="border-style:solid; border-width:1;">
<form name= "agentContractForm" method="post" action="/PORTAL/servlet/RequestManager">

<input type="hidden" name="page" value="">
<jsp:include page="agentInfoHeader.jsp" flush="true"/>

<span id="mainContent" style="border-style:solid; border-width:1;  position:relative; width:100%; height:100%; top:0; left:0; z-index:0; overflow:visible">
  <table width="100%" height="30%" border="0" cellpadding="0" cellspacing="0">
    <tr width="100%" height="1%">
      <td align="center" nowrap>Available Contracts</td>
    </tr>
    <tr width="100%" height="99%">
      <td align="center" nowrap>
        <table class="summaryArea" id="commissioncContractSummaryTable" width="30%" height="100%" border="0" cellspacing="0" cellpadding="0">
          <tr width="100%" height="99%">
            <td align="center">
              <span class="scrollableContent">
                <table class="scrollableArea" id="commissionContractSummary" width="100%" border="0" cellspacing="0" cellpadding="0">
                <%
                    String contractRowToMatch = "";
                    String contractClass = "";
                    String contractSelected = "";

                    if (contractCodeCTs != null) {

                        for (int c = 0; c < contractCodeCTs.length; c++) {

                            String currentContractCodeCT = contractCodeCTs[c].getCode();

                            contractRowToMatch =  currentContractCodeCT;

                            if (contractRowToMatch.equals(contractRowToMatchBase)) {

                                contractClass = "highLighted";
                                contractSelected = "true";
                            }
                            else {

                                contractClass = "mainEntry";
                                contractSelected="false";
                            }
                %>
                <tr class="<%= contractClass %>" isSelected="<%= contractSelected %>"
                    contractCodeCT="<%= currentContractCodeCT %>" onClick="selectContractRow()"
                    onMouseOver="highlightRow()" onMouseOut="unhighlightRow()">
                  <td width="100%" nowrap>
                    <%= currentContractCodeCT %>
                  </td>
                </tr>
                <%
                        }// end for
                    } // end if
                %>
                </table>
              </span>
            </td>
          </tr>
        </table>
      </td>
    </tr>
  </table>
  <table width="100%" height="20%">
    <tr>
      <td align="left" nowrap>Commission Process:&nbsp;
        <select name="commissionProcess">
          <option> Please Select </option>
          <%
              for(int i = 0; i < commissionProcesses.length; i++) {

                  String codeTablePK = commissionProcesses[i].getCodeTablePK() + "";
                  String codeDesc    = commissionProcesses[i].getCodeDesc();
                  String code        = commissionProcesses[i].getCode();

                 if (commissionProcess.equalsIgnoreCase(code)) {

                     out.println("<option selected name=\"id\" value=\"" + codeTablePK+ "\">" + codeDesc + "</option>");
                 }
                 else  {

                     out.println("<option name=\"id\" value=\"" + codeTablePK + "\">" + codeDesc + "</option>");
                 }
              }
          %>
        </select>
      </td>
      <td align="left" nowrap>Eff Date:&nbsp;
        <input type="text" name="effMonth" maxlength="2" size="2" value="<%= effectiveMonth %>">
        /
        <input type="text" name="effDay" maxlength="2" size="2" value="<%= effectiveDay %>">
        /
        <input type="text" name="effYear" maxlength="4" size="4" value="<%= effectiveYear %>">
      </td>
      <td align="left" nowrap>Stop Date:&nbsp;
        <input type="text" name="stopMonth" maxlength="2" size="2" value="<%= stopMonth %>">
        /
        <input type="text" name="stopDay" maxlength="2" size="2" value="<%= stopDay %>">
        /
        <input type="text" name="stopYear" maxlength="4" size="4" value="<%= stopYear %>">
      </td>
    </tr>
    <tr>
      <td align="left" nowrap colspan="3">
        <input disabled type="checkbox" name="additionalCompensationStatus" <%= additionalCompensationStatus %> >
        <a href ="javascript:showAdditionalCompensationDialog()">Additional Comp</a>
        <!-- Comment out - references a jsp file (annualizedDialog.jsp) that doesn't exist.  Comment out the link for now.
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        <input disabled type="checkbox" name="annualizedStatus" <%= annualizedStatus %>   >
        <a href ="javascript:showAnnualizedDialog()">Annualized</a>
        -->
      </td>
   </tr>
  </table>
  <table width="100%" height="2%" border="0" cellspacing="0" cellpadding="0">
	<tr>
	  <td align="left">
		<input type="button" name="add" value="   Add   " onClick="addContract()">
		<input type="button" name="save" value=" Save " onClick="saveContract()">
		<input type="button" name="cancel" value="Cancel" onClick="cancelContract()">
		<input type="button" name="delete" value="Delete" onClick="deleteContract()">
	  </td>
	</tr>
  </table>
  <table class="summaryArea" id="summaryTable" width="100%" height="30%" border="0" cellspacing="0" cellpadding="0">
    <tr height="1%">
      <th width="33%" align="left">Contract Code</th>
      <th width="33%" align="left">Comm Process</th>
	  <th width="33%" align="left">Eff Date</th>
    </tr>
    <tr width="100%" height="99%">
      <td colspan="3">
        <span class="scrollableContent">
          <table class="scrollableArea" id="contractSummary" width="100%" border="0" cellspacing="0" cellpadding="0">
            <%
                String rowToMatch = "";
                String trClass = "";
                String selected = "";

                if (agentContractVOs != null) {

                    for (int c = 0; c < agentContractVOs.length; c++)
                    {
                        if (!agentContractVOs[c].getVoShouldBeDeleted())
                        {
                            String sEffDate = DateTimeUtil.formatYYYYMMDDToMMDDYYYY(agentContractVOs[c].getContractEffectiveDate());
                            String sCommProcess = agentContractVOs[c].getCommissionProcessCT();
                            String sAgentContractPK = agentContractVOs[c].getAgentContractPK() + "";
                            String currentContractCodeCT = agentContractVOs[c].getContractCodeCT();

                            rowToMatch =  sAgentContractPK + currentContractCodeCT + sCommProcess;

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
			<tr class="<%= trClass %>" isSelected="<%= selected %>"
                agentContractPK="<%= sAgentContractPK %>" selectedContractCodeCT="<%= currentContractCodeCT %>"
                onClick="selectRow()" onMouseOver="highlightRow()" onMouseOut="unhighlightRow()">
			  <td width="20%" nowrap>
				<%= currentContractCodeCT %>
			  </td>
              <td width="20%" nowrap>
                <%= sCommProcess %>
              </td>
			  <td width="20%" nowrap>
				<%= sEffDate %>
			  </td>
			</tr>
            <%
                        }// end if
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
 <input type="hidden" name="agentContractPK" value="">
 <input type="hidden" name="commissionContractFK" value="">

 <input type="hidden" name="adaType" value="<%= adaType %>">
 <input type="hidden" name="annualizedMax" value="<%= annualizedMax %>">
 <input type="hidden" name="serviceFeeStatus" value="<%= serviceFeeStatus %>">
 <input type="hidden" name="bonusCommissionStatus" value="<%= bonusCommissionStatus %>">
 <input type="hidden" name="ny91PctStatus" value="<%= ny91PctStatus %>">
 <input type="hidden" name="additionalCompensationPK" value="<%= additionalCompPK %>">
 <input type="hidden" name="selectedContractCodeCT" value="">
 <input type="hidden" name="contractCodeCT" value="">

</body>
</html>