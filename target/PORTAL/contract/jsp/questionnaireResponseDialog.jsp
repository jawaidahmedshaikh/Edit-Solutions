<!-- ****** JAVA CODE ***** //-->

<%@ page  errorPage="/common/jsp/error.jsp" autoFlush="true" %>
<%@ page import="java.util.*,
                 edit.common.*,
                 edit.common.vo.*,
                 edit.common.vo.user.*,
                 fission.beans.*,
                 fission.utility.*,
                 edit.portal.common.session.UserSession,
                 edit.services.db.hibernate.SessionHelper" %>

<jsp:useBean id="contractMainSessionBean"
    class="fission.beans.SessionBean" scope="session"/>
<%
    ContractRequirementVO[] contractRequirementVOs = (ContractRequirementVO[]) session.getAttribute("contractRequirementVO");
    ContractRequirementVO contractRequirementVO = (ContractRequirementVO) session.getAttribute("selectedContractRequirementVO");

    String companyStructureId = Util.initString(contractMainSessionBean.getValue("companyStructureId"), "0");

    CodeTableWrapper codeTableWrapper = CodeTableWrapper.getSingleton();

    String requirementId = "";
    String description = "";
    String updatePolicyDeliveryDateInd = "";
    String status = "";
    String requirementPK = "";
    String filteredRequirementPK = "";
    String effectiveDate = "";
    String followupDate = "";
    String receivedDate = "";
    String executedDate = "";

    if (contractRequirementVO != null)
    {
        status = contractRequirementVO.getRequirementStatusCT();
        effectiveDate = DateTimeUtil.formatYYYYMMDDToMMDDYYYY(contractRequirementVO.getEffectiveDate());

        followupDate = DateTimeUtil.formatYYYYMMDDToMMDDYYYY(contractRequirementVO.getFollowupDate());

        receivedDate = DateTimeUtil.formatYYYYMMDDToMMDDYYYY(contractRequirementVO.getReceivedDate());
        executedDate = DateTimeUtil.formatYYYYMMDDToMMDDYYYY(contractRequirementVO.getExecutedDate());

        filteredRequirementPK = contractRequirementVO.getFilteredRequirementFK() + "";
        FilteredRequirementVO filteredRequirementVO = (FilteredRequirementVO)contractRequirementVO.getParentVO(FilteredRequirementVO.class);
        RequirementVO requirementVO = (RequirementVO) filteredRequirementVO.getParentVO(RequirementVO.class);
        requirementId = requirementVO.getRequirementId();
        description = requirementVO.getRequirementDescription();
        requirementPK = requirementVO.getRequirementPK() + "";
        updatePolicyDeliveryDateInd = requirementVO.getUpdatePolicyDeliveryDateInd();
    }

    CodeTableVO[] requirementStatuses = codeTableWrapper.getCodeTableEntries("REQUIREMENTSTATUS", Long.parseLong(companyStructureId));

	String rowToMatchBase = requirementId + requirementPK + filteredRequirementPK;

    UserSession userSession = (UserSession) session.getAttribute("userSession");
%>

<html>

<script src="/PORTAL/common/javascript/commonJavascriptFunctions.js"></script>
<script src="/PORTAL/common/javascript/formatDateFunctions.js"></script>
<script src="/PORTAL/common/javascript/calendarSelector.js"></script>

<script language="Javascript1.2">

	var f = null;

    var requirementId = "<%= requirementId %>";
    var requirementPK = "<%= requirementPK %>";
    var filteredRequirementPK = "<%= filteredRequirementPK %>";
    var updatePolicyDeliveryDateInd = "<%= updatePolicyDeliveryDateInd %>";

    var shouldShowLockAlert = true;

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

            currentRow = currentRow.parentName;
        }

        var className = currentRow.className;

        if (className == "highLighted") {

            currentRow.style.backgroundColor = colorHighlighted;
        }

        else {

            currentRow.style.backgroundColor = colorMainEntry;
        }
    }

	function init()
    {
		f = document.contractRequirementsForm;

		top.frames["main"].setActiveTab("requirementsTab");

        shouldShowLockAlert = <%= ! userSession.getSegmentIsLocked() %>;

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

            alert("The Contract can not be edited.");

            return false;
        }
    }

	function addNewRequirement() {

        var width = .65 * screen.width;
        var height = .25 * screen.height;

        openDialog("","manualRequirementSelection","top=0,left=0,resizable=no,width="+width+",height="+height,"ContractDetailTran","showManualRequirementSelectionDialog");
	 }

	function cancelRequirement() {

        sendTransactionAction("ContractDetailTran", "cancelRequirement", "contentIFrame");
	}

	function saveRequirementToSummary() {

        if (requirementId == "")
        {
            alert("Requirement Must Be Selected For Save");
        }
        else
        {
            if (isReceivedDateEnteredForReceivedStatus() && isStatusReceivedIfReceivedDateEntered(f.receivedDate.value) &&
                    verifyIfExecutedDateRequired())
            {
                sendTransactionAction("ContractDetailTran", "saveRequirementToSummary", "contentIFrame");
            }
        }
	}

    /**
     For Status = 'Received' the ReceivedDate must be entered.
     */
    function isReceivedDateEnteredForReceivedStatus()
    {
        if (f.status.options[f.status.selectedIndex].text == 'Received')
        {
            if (valueIsEmpty(f.receivedDate.value))
            {
                alert('Status = Received. Received Date Required.');
                return false;
            }
        }

        return true;
    }

    /**
     If ReceivedDate is entered the status should be 'Received
     */
    function isStatusReceivedIfReceivedDateEntered(receivedDate)
    {
        if (!valueIsEmpty(receivedDate) && (f.status.options[f.status.selectedIndex].text != 'Received'))
        {
            alert('Received Date is Entered. Status Invalid.');
            return false;
        }

        return true;
    }

    function verifyIfExecutedDateRequired()
    {
        if (f.status.options[f.status.selectedIndex].text == 'Received' && updatePolicyDeliveryDateInd == "Y")
        {
            if (valueIsEmpty(f.executedDate.value))
            {
                alert('Executed Date Required. [Status = Received] and [UpdatePolicyDeliveryDate = Yes]');
                return false;
            }
        }

        return true;
    }

	function deleteRequirement()
    {
		f.selectedRequirementId.value = requirementId;
		f.selectedRequirementPK.value = requirementPK;
        f.selectedFilteredRequirementPK.value = filteredRequirementPK;

		sendTransactionAction("ContractDetailTran", "deleteSelectedRequirement", "contentIFrame");
	}


	function selectRow() {

		var tdElement = window.event.srcElement;
		var trElement = tdElement.parentElement;

		var rowId     = trElement.id;
		var parsedRowId = (rowId.split("_"))[1];

        var requirementId  = trElement.requirementId;
        var filteredRequirementPK  = trElement.filteredRequirementPK;

		f.selectedFilteredRequirementPK.value = filteredRequirementPK;
		f.selectedRequirementId.value = requirementId;

		sendTransactionAction("ContractDetailTran", "showRequirementDetail", "contentIFrame");
	}

    function openDialog(theURL,winName,features,transaction,action) {

		dialog = window.open(theURL,winName,features);

	  	sendTransactionAction(transaction, action, winName);
	}

	function sendTransactionAction(transaction, action, target) {

		f.transaction.value = transaction;
		f.action.value = action;

		f.target = target;

		f.submit();
	}

    function prepareToSendTransactionAction(transaction, action, target)
    {
        sendTransactionAction(transaction, action, target);
    }

</script>

<head>
<title>New Business Requirements</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta http-equiv="Cache-Control" content="no-store">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Expires" content="0">

<link href="/PORTAL/common/css/PORTAL.css" rel="stylesheet" type="text/css">
</head>

<body class="mainTheme" onLoad="init()" style="border-style:solid; border-width:1;">
<form name= "contractRequirementsForm" method="post" action="/PORTAL/servlet/RequestManager">

<input type="hidden" name="page" value="">
<jsp:include page="contractInfoHeader.jsp" flush="true"/>

<span id="mainContent" style="border-style:solid; border-width:1; position:relative; width:100%; height:100%; top:0; left:0; z-index:0; overflow:visible">
  <table width="100%" height="48%" border="0" cellspacing="0" cellpadding="3">
    <tr>
      <td align="right" nowrap>Requirement Id:&nbsp;</td>
	  <td align="left" colspan="3" nowrap>
        <input disabled type="text" name="requirementId" maxlength="5" size="5" value="<%= requirementId %>">
	  </td>
    </tr>
    <tr>
      <td align="right" nowrap>Requirement Description:&nbsp;</td>
      <td align="left" colspan="3" nowrap>
        <input disabled type="text" name="requirementDescription" maxlength="50" size="50" value="<%= description %>">
      </td>
    </tr>
    <tr>
      <td align="right" nowrap>Status:&nbsp;</td>
      <td align="left" nowrap>
		<select name="status" tabindex="1" value="<%= status %>">
		  <option> Please Select </option>
			<%
               if (requirementStatuses != null) {

                   for(int s = 0; s < requirementStatuses.length; s++) {

                       String codeTablePK = requirementStatuses[s].getCodeTablePK() + "";
                       String codeDesc    = requirementStatuses[s].getCodeDesc();
                       String code        = requirementStatuses[s].getCode();

                       if (status.equalsIgnoreCase(code)) {

                           out.println("<option selected name=\"id\" value=\"" + codeTablePK+ "\">" + codeDesc + "</option>");
                       }
                       else  {

                           out.println("<option name=\"id\" value=\"" + codeTablePK + "\">" + codeDesc + "</option>");
                       }
                   }
               }
			%>
		  </select>
		</td>
        <td align="right" nowrap>Received Date:&nbsp;</td>
        <td align="left" nowrap>
           <input type="text" name="receivedDate" value="<%= receivedDate %>" size='10' maxlength="10" onKeyUp="DateFormat(this,this.value,event,false)" onBlur="DateFormat(this,this.value,event,true)">
           <a href="javascript:show_calendar('f.receivedDate', f.receivedDate.value);"><img src="/PORTAL/common/images/calendarIcon.gif" width="16" height="16" border="0" alt="Select a date from the calendar"></a>
        </td>
      </tr>
      <tr>
        <td align="right" nowrap>Executed Date:&nbsp;</td>
        <td align="left" colspan="3" nowrap>
           <input type="text" name="executedDate" value="<%= executedDate %>" size='10' maxlength="10" onKeyUp="DateFormat(this,this.value,event,false)" onBlur="DateFormat(this,this.value,event,true)">
           <a href="javascript:show_calendar('f.executedDate', f.executedDate.value);"><img src="/PORTAL/common/images/calendarIcon.gif" width="16" height="16" border="0" alt="Select a date from the calendar"></a>
        </td>
      </tr>
      <tr>
      <td align="right" nowrap>Effective Date:&nbsp;</td>
      <td align="left" nowrap>
           <input type="text" name="effectiveDate" value="<%= effectiveDate %>" size='10' maxlength="10" onKeyUp="DateFormat(this,this.value,event,false)" onBlur="DateFormat(this,this.value,event,true)">
           <a href="javascript:show_calendar('f.effectiveDate', f.effectiveDate.value);"><img src="/PORTAL/common/images/calendarIcon.gif" width="16" height="16" border="0" alt="Select a date from the calendar"></a>
       </td>
      <td align="right" nowrap>Followup Date:&nbsp;</td>
      <td align="left" nowrap>
           <input type="text" name="followupDate" value="<%= followupDate %>" size='10' maxlength="10" onKeyUp="DateFormat(this,this.value,event,false)" onBlur="DateFormat(this,this.value,event,true)">
           <a href="javascript:show_calendar('f.followupDate', f.followupDate.value);"><img src="/PORTAL/common/images/calendarIcon.gif" width="16" height="16" border="0" alt="Select a date from the calendar"></a>
       </td>
    </tr>
  </table>
  <table width="100%" height="2%" border="0" cellspacing="0" cellpadding="0">
	<tr>
	  <td nowrap align="left">
        <input type="button" value="   Add   " style="background-color:#DEDEDE" onClick="addNewRequirement()">
		<input type="button" value="   Save  " style="background-color:#DEDEDE" onClick="saveRequirementToSummary()">
		<input type="button" value="  Cancel " style="background-color:#DEDEDE" onClick="cancelRequirement()">
		<input type="button" value="  Delete " style="background-color:#DEDEDE" onClick="deleteRequirement()">
	  </td>
	</tr>
  </table>
  <table class="summaryArea" id="summaryTable" width="100%" height="40%" border="0" cellspacing="0" cellpadding="0">
    <tr height="1%">
      <th width="20%" align="left">Requirement Id</th>
      <th width="43%" align="left">Requirement Description</th>
      <th width="12%" align="left">Status</th>
      <th width="25%" align="left">Effective Date</th>
    </tr>
    <tr width="100%" height="99%">
      <td colspan="4">
        <span class="scrollableContent">
          <table class="scrollableArea" id="addressSummary" width="100%" border="0" cellspacing="0" cellpadding="0">
	        <%
                String rowToMatch = "";
                String trClass = "";
                String selected = "";
                String sFilteredRequirementPK = "";
                if (contractRequirementVOs != null) {

                    for (int r = 0; r < contractRequirementVOs.length; r++)
                    {
                        if (!contractRequirementVOs[r].getVoShouldBeDeleted())
                        {
                            String sStatus = contractRequirementVOs[r].getRequirementStatusCT();
                            String sEffDate = DateTimeUtil.formatYYYYMMDDToMMDDYYYY(contractRequirementVOs[r].getEffectiveDate());
                            FilteredRequirementVO sFilteredRequirementVO = (FilteredRequirementVO) contractRequirementVOs[r].getParentVO(FilteredRequirementVO.class);
                            sFilteredRequirementPK = sFilteredRequirementVO.getFilteredRequirementPK() + "";
                            RequirementVO sRequirementVO = (RequirementVO) sFilteredRequirementVO.getParentVO(RequirementVO.class);
                            String sRequirementId = sRequirementVO.getRequirementId();
                            String sDescription = sRequirementVO.getRequirementDescription();
                            String sContractRequirementPK = sRequirementVO.getRequirementPK() + "";

                            rowToMatch =  sRequirementId + sContractRequirementPK + sFilteredRequirementPK;

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
                filteredRequirementPK="<%= sFilteredRequirementPK %>"
                onMouseOver="highlightRow()" onMouseOut="unhighlightRow()" onClick="selectRow()">
              <td width="20%" nowrap>
                <%= sRequirementId %>
              </td>
              <td width="43%" nowrap>
                <%= sDescription %>
              </td>
              <td width="12%" nowrap>
                <%= sStatus %>
              </td>
              <td width="25%" nowrap>
                <%= sEffDate %>
              </td>
            </tr>
            <%
                        } // end if (!voShouldBeDeleted)
                    } // end for
                } // end if (contractRequirementVOs != null)
            %>
          </table>
        </span>
      </td>
    </tr>
  </table>
</span>

<!-- ****** HIDDEN FIELDS ***** //-->
 <input type="hidden" name="transaction" value="">
 <input type="hidden" name="action"      value="">

 <input type="hidden" name="selectedFilteredRequirementPK" value="<%= filteredRequirementPK %>">
 <input type="hidden" name="selectedRequirementPK" value="">
 <input type="hidden" name="selectedRequirementId" value="">
 <input type="hidden" name="receivedDate" value="">
 <input type="hidden" name="executedDate" value="">

</form>
</body>
</html>






