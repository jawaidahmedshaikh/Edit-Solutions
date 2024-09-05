<!-- contractCommitNonPayee.jsp  //-->

<!-- ****** JAVA CODE ***** //-->

<%@ page  errorPage="/common/jsp/error.jsp" autoFlush="true" %>
<%@ page import="java.util.*,
                 edit.common.*,
                 edit.common.vo.*,
                 edit.common.vo.user.*,
                 fission.beans.*,
                 edit.portal.common.session.UserSession,
                 fission.utility.*" %>

<jsp:useBean id="contractMainSessionBean"
    class="fission.beans.SessionBean" scope="session"/>

<jsp:useBean id="contractClients"
    class="fission.beans.SessionBean" scope="session"/>

<%
    String clientMessage = (String) request.getAttribute("clientMessage");
    if (clientMessage == null)
    {
        clientMessage = "";
    }

    String payeeMessage = (String) request.getAttribute("payeeMessage");
    if (payeeMessage == null)
    {
        payeeMessage = "";
    }

    CodeTableWrapper codeTableWrapper = CodeTableWrapper.getSingleton();

    String companyStructureId = contractMainSessionBean.getPageBean("formBean").getValue("companyStructureId");

    String segmentStatus = contractMainSessionBean.getPageBean("formBean").getValue("segmentStatus");

	PageBean formBean      = contractMainSessionBean.getPageBean("clientFormBean");

    String contractClientPK = formBean.getValue("contractClientPK");
    String contractClientAllocationPK = formBean.getValue("contractClientAllocationPK");
    String clientRoleFK     = formBean.getValue("clientRoleFK");
    String segmentFK        = formBean.getValue("segmentFK");
	String taxId            = formBean.getValue("taxId");
	String employeeIdentification = formBean.getValue("employeeIdentification");
	String lastName   	    = formBean.getValue("lastName");
	String firstName        = formBean.getValue("firstName");
	String middleName       = formBean.getValue("middleName");
    String corporateName    = formBean.getValue("corporateName");

	String dob         = formBean.getValue("dob");

	String issueAge         = formBean.getValue("issueAge");

	String effectiveDate   = formBean.getValue("effectiveDate");

	String terminateDate   = formBean.getValue("terminationDate");

    String disbAddressType  = formBean.getValue("disbAddressType");
    String corrAddressType  = formBean.getValue("corrAddressType");

	String allocationPercent = formBean.getValue("allocationPercent");
    String allocationDollar = Util.initString(formBean.getValue("allocationDollar"), "0");
	String genderId        = formBean.getValue("genderId");
    String prefix         = formBean.getValue("prefix");
    String suffix         = formBean.getValue("suffix");
    String operator       = formBean.getValue("operator");
    String maintDateTime  = formBean.getValue("maintDateTime");
    String relationToEmp   = formBean.getValue("relationToEmp");
	String disbursementSource  = formBean.getValue("disbursementSource");

	String clientFound     = (String) request.getAttribute("clientFound");
	String deletionWarning = (String) request.getAttribute("deletionWarning");

	String optionValue	   = formBean.getValue("optionId");
	String relationship    = formBean.getValue("relationshipInd");
	String exemptions      = formBean.getValue("exemptions");
	String usCitizenIndStatus  = formBean.getValue("usCitizenIndStatus");
    String contractClientOverrideStatus = formBean.getValue("contractClientOverrideStatus");
    String riderNumber = formBean.getValue("riderNumber");

    String amtPaidToDate               = formBean.getValue("amtPaidToDate");
    String amtPaidYTD                  = formBean.getValue("amtPaidYTD");
    String fedWithholdingYTD           = formBean.getValue("fedWithholdingYTD");
    String stateWithholdingYTD         = formBean.getValue("stateWithholdingYTD");
    String cityWithholdingYTD          = formBean.getValue("cityWithholdingYTD");
    String countyWithholdingYTD        = formBean.getValue("countyWithholdingYTD");
    String printAs                     = formBean.getValue("printAs");
    if (printAs == null) {

        printAs = "";
    }
    String printAs2                    = formBean.getValue("printAs2");
    if (printAs2 == null) {

        printAs2 = "";
    }
    String relationToIns   = formBean.getValue("relationToIns");
    String phoneAuth       = formBean.getValue("phoneAuth");
    String terminationReason = formBean.getValue("terminationReason");

    String authorizedSignatureCT = formBean.getValue("authorizedSignatureCT");

    String accumulationIndStatus       = formBean.getValue("accumulationIndStatus");
    String rowToMatchBase  = taxId + relationship + optionValue + clientRoleFK + contractClientPK;

    CodeTableVO[] phoneAuths = codeTableWrapper.getCodeTableEntries("PHONEAUTHORIZATION", Long.parseLong(companyStructureId));
    CodeTableVO[] relToIns = codeTableWrapper.getCodeTableEntries("RELATIONTOINSURED", Long.parseLong(companyStructureId));
    CodeTableVO[] addressTypes = codeTableWrapper.getCodeTableEntries("ADDRESSTYPE", Long.parseLong(companyStructureId));
    CodeTableVO[] terminationReasons = codeTableWrapper.getCodeTableEntries("TERMINATIONREASON", Long.parseLong(companyStructureId));
    CodeTableVO[] yesNo = codeTableWrapper.getCodeTableEntries("YESNO");

    UserSession userSession = (UserSession) session.getAttribute("userSession");
%>

<%!
    private TreeMap sortClientsByRole(SessionBean clients) {

        Map clientBeans = clients.getPageBeans();

		TreeMap sortedClients = new TreeMap();

		Iterator enumer  = clientBeans.values().iterator();

        int clientCount = 0;

		while (enumer.hasNext()) {

			PageBean clientBean = (PageBean) enumer.next();

			String role = clientBean.getValue("relationshipInd");
            String contractClientPK = clientBean.getValue("contractClientPK");

            clientCount++;

            sortedClients.put(role + contractClientPK + clientCount, clientBean);
        }

		return sortedClients;
    }
%>

<html>

<script src="/PORTAL/common/javascript/formatDateFunctions.js"></script>
<script src="/PORTAL/common/javascript/calendarSelector.js"></script>

<script language="Javascript1.2">

    var payeeMessage = "<%= payeeMessage %>";

	var dialog = null;

	var f = null;

    var shouldShowLockAlert = true;
    var editableContractStatus = true;
    var segmentStatus = "<%= segmentStatus %>";

	var clientFound     = "<%= clientFound %>";
	var deletionWarning = "<%= deletionWarning %>";
    var taxId = "<%= taxId %>";
    var clientMessage = "<%= clientMessage %>";
    var clientRoleFK = "<%= clientRoleFK %>";

    var colorMouseOver   = "#00BB00";
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

        if (className == "highLighted") {

            currentRow.style.backgroundColor = colorHighlighted;
        }

        else {

            currentRow.style.backgroundColor = colorMainEntry;
        }
    }

	function init() {

		f = document.contractCommitPayeeForm;

		top.frames["main"].setActiveTab("clientTab");

        if (payeeMessage != "" &&
            payeeMessage != null) {

            alert(payeeMessage);
        }

        shouldShowLockAlert = <%= ! userSession.getSegmentIsLocked() %>;

        // check for terminated status to disallow contract updates
        if (segmentStatus != "ReducedPaidUp") {
        	editableContractStatus = <%= userSession.getEditableSegmentStatus()%>;
        }
        
        for (var i = 0; i < f.elements.length; i++) {

            var elementType = f.elements[i].type;

            if ((elementType == "text" || elementType == "button") &&
            		(shouldShowLockAlert == true ||
                    editableContractStatus == false)) {

                f.elements[i].onclick = showLockAlert;
                f.elements[i].onkeydown = showLockAlert;
            }
        }

        if (clientMessage != "")
        {
            alert(clientMessage);
        }

        formatCurrency();
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

	function checkForDeletionWarning() {

		if (deletionWarning == "true") {

			alert("Can Not Delete This Entry");
		}
	}

	function showContractClientAddDialog() {

		clearFormFields();

        var width = screen.width;
        var height = .85 * screen.height;

		openDialog("","contractClientAddDialog","top=0,left=0,resizable=no,width=" + width + ",height=" + height,"ContractDetailTran", "showContractClientAddDialog");
	}

	function showPreferences()
    {
        if (clientRoleFK == "")
        {
            alert("Please Select Client");
        }
        else
        {
            f.selectedClientRoleFK.value = clientRoleFK;
            var width = 0.80 * screen.width;
            var height = 0.90 * screen.height;

            openDialog("","preferenceDialog","left=0,top=0,resizable=no,width=" + width + ",height=" + height, "ContractDetailTran", "showPreferences", "preferenceDialog");
       }
	}

	function openDialog(theURL,winName,features,transaction,action) {

	  dialog = window.open(theURL,winName,features);

	  sendTransactionAction(transaction, action, winName);
	}

	function sendTransactionAction(transaction, action, target) {

		if (f.usCitizenInd.checked == true) {

			f.usCitizenIndStatus.value = "checked";
		}

		f.transaction.value=transaction;
		f.action.value=action;

		f.target = target;

		f.submit();
	}

	function clearFormFields() {

		f.taxId.value         = "";

		f.lastName.value      = "";
		f.firstName.value     = "";
		f.middleName.value    = "";
                f.prefix.value       = "";
                f.suffix.value       = "";
		f.dob.value      = "";

		f.issueAge.value      = "";

		f.relationship.value  = "";
		f.optionId.value      = "";
                f.maintDateTime.value = "";
                f.operator.value      = "";
		f.genderId.selectedIndex = 0;
		f.usCitizenInd.checked   = false;
	}

	function selectRow() {

		var tdElement = window.event.srcElement;
		var trElement = tdElement.parentElement;

		var rowId     = trElement.id;
		var parsedRowId = (rowId.split("_"))[1];
        var clientRoleFK = trElement.clientRoleFK;
        var contractClientPK = trElement.contractClientPK;
        var riderNumber = trElement.riderNumber;

		var taxTd  = document.getElementById("taxId_" + parsedRowId);
		var taxId  = taxTd.innerText;

		var optionTd  = document.getElementById("optionId_" + parsedRowId);
		var optionId  = optionTd.innerText;

		var relationshipTd = document.getElementById("relationshipId_" + parsedRowId);
		var relationship   = relationshipTd.innerText;

		f.selectedTaxId.value = taxId;
		f.selectedOptionId.value = optionId;
		f.selectedRelationship.value = relationship;
        f.selectedClientRoleFK.value = clientRoleFK;
        f.selectedContractClientPK.value = contractClientPK;
        f.selectedRiderNumber.value = riderNumber;

		sendTransactionAction("ContractDetailTran", "showClientDetailSummary", "contentIFrame");
	}

	//----- The Pages Toolbar functions

	function updateClientInfo() {

        if (relationship == "")
        {
            alert("Client Must Be Selected For Save");
        }
        else if (f.allocationPercent.value == "")
        {
            alert("Allocation Percent Must Be Entered");
        }
        else
        {
            sendTransactionAction("ContractDetailTran", "updateClientInfo","contentIFrame");
            clearFormFields();
        }
	}

	function cancelClientAdd() {

		clearFormFields();
		sendTransactionAction("ContractDetailTran", "cancelContractNonPayeeOrPayee", "contentIFrame");
	}

	function deleteSelectedClient() {

        alert("Can Not Delete This Entry");

	}

//------------------

	function accumulationInfoDisplay() {

        var height  = 0.50 * screen.height;
        var width   = 0.40 * screen.width;

        openDialog("","accumulationDialog","left=50,top=50,resizable=yes,width=" + width + ",height=" + height,"ContractDetailTran", "showAccumulationDialog", "accumulationDialog");

<%--		openDialog("","accumulationDialog","resizable=no,width=" + width + ",height=" + height);--%>

<%--        sendTransactionAction("ContractDetailTran", "showAccumulationDialog", "accumulationDialog");--%>
	}

    function prepareToSendTransactionAction(transaction, action, target)
    {
        sendTransactionAction(transaction, action, target);
    }

	function showQuestionnaireResponse()
    {
        f.selectedContractClientPK.value = contractClientPK;
        var width = .95 * screen.width;
        var height = .35 * screen.height;
        openDialog("","QuestionnaireResponse","top=0,left=0,resizable=no,width=" + width + ",height=" + height, "ContractDetailTran", "showQuestionnaireResponseDialog");
	}

</script>

<head>
<title>Contract Payee</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta http-equiv="Cache-Control" content="no-store">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Expires" content="0">

<link href="/PORTAL/common/css/PORTAL.css" rel="stylesheet" type="text/css">
</head>

<body class="mainTheme" onLoad="init(); checkForDeletionWarning()" style="border-style:solid; border-width:1;">
<form name= "contractCommitPayeeForm" method="post" action="/PORTAL/servlet/RequestManager">

<input type="hidden" name="page" value="">
<jsp:include page="contractInfoHeader.jsp" flush="true"/>

<span id="mainContent" style="border-style:solid; border-width:1;  position:relative; width:100%; height:100%; top:0; left:0; z-index:0; overflow:visible">
  <table width="100%" height="48%" border="0" cellspacing="0" cellpadding="5">
	<tr>
	  <td align="left" nowrap>Tax Id:&nbsp;
	    <input disabled type="text" name="taxId" maxlength="11" size="11" value="<%= taxId %>">
	  </td>
	  <td align="left" nowrap>Last Name:&nbsp;
	    <input disabled type="text" name="lastName" maxlength="30" size="30" value="<%= lastName %>">
	  </td>
	  <td align="left" nowrap>First Name:&nbsp;
	    <input disabled type="text" name="firstName" maxlength="15" size="15" value="<%= firstName %>">
	  </td>
          <td align="left" nowrap width="19%" colspan="1">Middle Name:&nbsp;
        <input disabled type="text" name="middleName" maxlength="15"  size="15" value="<%= middleName %>">
      </td>
	</tr>
    <tr>
      <td align="left" nowrap>DOB:&nbsp;
        <input disabled type="text" name="dob" maxlength="10" size="10" value="<%= dob %>">
      </td>
      
      <td align="left" nowrap>Gender Type:&nbsp;
        <input disabled type="text" name="genderId" maxlength="20" size="20" value="<%= genderId %>">
      </td>
        <td align="left" nowrap>Prefix:&nbsp;
	    <input disabled type="text" name="prefix" maxlength="15" size="15" value="<%= prefix%>">
      </td>
        <td align="left" nowrap>Suffix:&nbsp;
	    <input disabled type="text" name="suffix" maxlength="15" size="15" value="<%= suffix%>">
      </td>
    </tr>
    <tr>
      <td align="left" nowrap colspan="3">Corporate Name:&nbsp;
        <input disabled type="text" name="corporateName" maxlength="60" size="60" value="<%= corporateName %>">
      </td>
      <td align="left" nowrap>Employee ID:&nbsp;
        <input type="text" name="employeeIdentification" maxlength="11" size="11" value="<%= employeeIdentification %>">
      </td>
    </tr>
    <tr>
      <td align="left" nowrap>Issue Age:&nbsp;
        <input disabled type="text" disabled name="issueAge" maxlength="4" size="2" value="<%= issueAge %>">
      </td>
	  <td align="left" nowrap>Disbursement Source:&nbsp;
      	<input disabled type="text" name="disbursementSource" maxlength="20" size="20" value="<%= disbursementSource %>">
      </td>
      <td align="left" nowrap>
        <input disabled type="checkbox" name="usCitizenInd" <%=usCitizenIndStatus %>>
        US Citizen Indicator
      </td>
    </tr>
    <tr>
      <td align="left" nowrap>Telephone Authorization:&nbsp;
        <select name="phoneAuth" tabindex="1">
	        <option value="Please Select">Please Select</option>
	        <%
                for(int i = 0; i < phoneAuths.length; i++) {

                    String codeTablePK = phoneAuths[i].getCodeTablePK() + "";
                    String codeDesc    = phoneAuths[i].getCodeDesc();
                    String code        = phoneAuths[i].getCode();

                    if (phoneAuth.equalsIgnoreCase(code)) {

                        out.println("<option selected name=\"id\" value=\"" + codeTablePK+ "\">" + codeDesc + "</option>");
                    }
                    else  {

                        out.println("<option name=\"id\" value=\"" + codeTablePK + "\">" + codeDesc + "</option>");
                    }
                }
	       %>

        </select>
      </td>
      <td align="left" nowrap>Relationship to Insured:&nbsp;
        <select name="relationToIns" tabindex="2">
	        <option value="Please Select">Please Select</option>
	        <%
                for(int i = 0; i < relToIns.length; i++) {

                    String codeTablePK = relToIns[i].getCodeTablePK() + "";
                    String codeDesc    = relToIns[i].getCodeDesc();
                    String code        = relToIns[i].getCode();

                    if (relationToIns.equalsIgnoreCase(code)) {

                        out.println("<option selected name=\"id\" value=\"" + codeTablePK+ "\">" + codeDesc + "</option>");
                    }
                    else  {

                        out.println("<option name=\"id\" value=\"" + codeTablePK + "\">" + codeDesc + "</option>");
                    }
                }
	       %>

        </select>
      </td>
      <td valign="top" align="left" nowrap rowspan="4" colspan="2">
        <b>Address Types:&nbsp;</b>
        <br>
        <br>
        <span style="border-style:solid; border-width:1; position:relative; width:75%; height:35% top:0; left:0; z-index:0; overflow:visible">
          <table width="100%" height="100%" border="0" cellspacing="1" cellpadding="1">
            <tr>
              <td align="right" nowrap>Disbursement:&nbsp;</td>
              <td align="left" nowrap>
                <select name="disbAddressType" tabindex="9">
	              <option value="Please Select">Please Select</option>
	              <%
                    for(int i = 0; i < addressTypes.length; i++)
                    {
                        String codeTablePK = addressTypes[i].getCodeTablePK() + "";
                        String codeDesc    = addressTypes[i].getCodeDesc();
                        String code        = addressTypes[i].getCode();
                        if (!code.equalsIgnoreCase("SecondaryAddress"))
                        {
                            if (disbAddressType.equalsIgnoreCase(code))
                            {
                                out.println("<option selected name=\"id\" value=\"" + codeTablePK+ "\">" + codeDesc + "</option>");
                            }
                            else
                            {
                                out.println("<option name=\"id\" value=\"" + codeTablePK + "\">" + codeDesc + "</option>");
                            }
                        }
                    }
	              %>
                </select>
              </td>
            </tr>
            <tr>
              <td align="right" nowrap>Correspondence:&nbsp;</td>
              <td align="left" nowrap>
                <select name="corrAddressType" tabindex="10">
	              <option value="Please Select">Please Select</option>
	              <%
                    for(int i = 0; i < addressTypes.length; i++)
                    {
                        String codeTablePK = addressTypes[i].getCodeTablePK() + "";
                        String codeDesc    = addressTypes[i].getCodeDesc();
                        String code        = addressTypes[i].getCode();
                        if (!code.equalsIgnoreCase("SecondaryAddress"))
                        {
                            if (corrAddressType.equalsIgnoreCase(code))
                            {
                                out.println("<option selected name=\"id\" value=\"" + codeTablePK+ "\">" + codeDesc + "</option>");
                            }
                            else
                            {
                                out.println("<option name=\"id\" value=\"" + codeTablePK + "\">" + codeDesc + "</option>");
                            }
                        }
                    }
	              %>
                </select>
              </td>
            </tr>
          </table>
        </span>
      </td>
    </tr>
    <tr>
      <td align="left" nowrap>Effective Date:&nbsp;
           <input type="text" name="effectiveDate" value="<%= effectiveDate %>" size='10' maxlength="10" onKeyUp="DateFormat(this,this.value,event,false)" onBlur="DateFormat(this,this.value,event,true)">
           <a href="javascript:show_calendar('f.effectiveDate', f.effectiveDate.value);"><img src="/PORTAL/common/images/calendarIcon.gif" width="16" height="16" border="0" alt="Select a date from the calendar"></a>
      </td>

      <td align="left" nowrap>Termination Date:&nbsp;
           <input type="text" name="terminateDate" value="<%= terminateDate %>" size='10' maxlength="10" onKeyUp="DateFormat(this,this.value,event,false)" onBlur="DateFormat(this,this.value,event,true)">
           <a href="javascript:show_calendar('f.terminateDate', f.terminateDate.value);"><img src="/PORTAL/common/images/calendarIcon.gif" width="16" height="16" border="0" alt="Select a date from the calendar"></a>
      </td>

      <td nowrap width="23%" colspan="2">Indicators:</td>
    </tr>
    <tr>
      <td align="left" nowrap colspan="4">Termination Reason:&nbsp;
        <select name="terminationReason" tabindex="11">
          <option value="Please Select">Please Select</option>
          <%
            for(int i = 0; i < terminationReasons.length; i++)
            {
                String codeTablePK = terminationReasons[i].getCodeTablePK() + "";
                String codeDesc    = terminationReasons[i].getCodeDesc();
                String code        = terminationReasons[i].getCode();

                if (code.equalsIgnoreCase(terminationReason))
                {
                    out.println("<option selected name=\"id\" value=\"" + codeTablePK+ "\">" + codeDesc + "</option>");
                }
                else
                {
                    out.println("<option name=\"id\" value=\"" + codeTablePK + "\">" + codeDesc + "</option>");
                }

            }
          %>
        </select>
      </td>
    </tr>
    <tr>
      <td align="left" nowrap> Allocation %:
        <input type="text" name="allocationPercent" maxlength="5" size="5" tabindex="12" value="<%= allocationPercent %>">
      </td>
      <td align="left" nowrap>
	    <input type="checkbox" name="accumulationInd" <%= accumulationIndStatus %> disabled>
	    <a href = "javascript:accumulationInfoDisplay()">Accumulation</a><br>
    </tr>
    <tr>
      <td align="left" nowrap width="19%">Allocation Amount:
        <input type="text" name="allocationDollar" maxlength="5" size="5" value="<%= allocationDollar  %>" CURRENCY>
      </td>
    </tr>
    <tr>
      <td nowrap width="18%" colspan="1">Exemptions:
        <input disabled type="text" name="exemptions" maxlength="10" size="10" value="<%= exemptions %>">
      </td>
    </tr>
    <tr>
      <td nowrap colspan="4">Print As:
        <input disabled type="text" name="printAs" maxlength="70" size="70" value="<%= printAs %>">
      </td>
    </tr>
    <tr>
      <td nowrap colspan="4">Print As 2:
        <input disabled type="text" name="printAs2" maxlength="70" size="70" value="<%= printAs2 %>">
      </td>
    </tr>
    <tr>
      <td align="left" nowrap>
        <a href ="javascript:showPreferences()">Preference</a>
      </td>
      <td align="left" nowrap colspan="3">
        <a href ="javascript:showQuestionnaireResponse()">Questionnaire Responses</a>
      </td>
    </tr>
    <tr>
      <td nowrap width="19%" colspan="4">&nbsp;</td>
    </tr>
    <tr>
      <td align="left" nowrap colspan="4">Authorized Signature:&nbsp;
        <select name="authorizedSignatureCT" tabindex="18">
	        <option value="Please Select">Please Select</option>
	        <%
                for(int i = 0; i < yesNo.length; i++) {

                    String codeTablePK = yesNo[i].getCodeTablePK() + "";
                    String codeDesc    = yesNo[i].getCodeDesc();
                    String code        = yesNo[i].getCode();

                    if (authorizedSignatureCT.equalsIgnoreCase(code)) {

                        out.println("<option selected name=\"id\" value=\"" + codeTablePK+ "\">" + codeDesc + "</option>");
                    }
                    else  {

                        out.println("<option name=\"id\" value=\"" + codeTablePK + "\">" + codeDesc + "</option>");
                    }
                }
	       %>

        </select>
      </td>
    </tr>
    <tr>
	  <td nowrap colspan="4">
	  	<div style="width:1%">
		  <table>
			<tr>
		  	  <td>
				Relationship:
			  </td>
			  <td>
				<%= relationship %>
			  </td>
			</tr>
			<tr>
			  <td>
				Coverage:
			  </td>
			  <td>
				<%= optionValue %>
			  </td>
			</tr>
		  </table>
		</div>
	  </td>
    </tr>
     <tr>
      <td align="left" nowrap >Operator:&nbsp;
        <input disabled type="text" name="operator" maxlength="15" size="15" value="<%= operator  %>">
      </td>
      <td align="left" nowrap >Maint Date Time:&nbsp;
        <input disabled type="text" name="maintDateTime" maxlength="30" size="30" value="<%= maintDateTime  %>">
      </td>
      <td align="left" nowrap >Rider Number:&nbsp;
        <input disabled type="text" name="riderNumber" maxlength="4" size="4" value="<%= riderNumber  %>">
      </td>
        </tr>
  </table>
  <table width="100%" height="2%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      <td align="left" height="14">
        <input type="button" name="add" value= " Add  " onClick="showContractClientAddDialog()">
	    <input type="button" name="save" value=" Save " onClick="updateClientInfo()">
	    <input type="button" name="cancel" value="Cancel" onClick="cancelClientAdd()">
	    <input type="button" name="delete" value="Delete" onClick="deleteSelectedClient()">
	  </td>
	</tr>
  </table>
  <table class="summaryArea" id="summaryTable" width="100%" height="30%" border="0" cellspacing="0" cellpadding="0">
    <tr height="1%">
      <th width="20%" align="left">Name</th>
      <th width="20%" align="left">Client ID</th>
      <th width="20%" align="left">Relationship</th>
      <th width="20%" align="left">Termination Date</th>
      <th width="20%" align="left">Coverage</th>
    </tr>
    <tr width="100%" height="99%">
      <td colspan="5">
        <span class="scrollableContent">
          <table class="scrollableArea" id="clientSummary" width="100%" border="0" cellspacing="0" cellpadding="0">
            <%
              int rowId   = 0;
              int columnId = 0;

              String rowToMatch = "";
              String trClass = "";
              String selected = "";

              String name          = null;
              String clientRole    = null;
              String clientOption  = null;
              String clientTaxId   = null;
              String sClientRoleFK = null;
              String sContractClientPK = null;
              String sTerminationDate = null;
              String sRiderNumber = "";

              Map sortedClientBeans = sortClientsByRole(contractClients);

              Iterator it = sortedClientBeans.values().iterator();

              while (it.hasNext()) {

                  PageBean clientBean = (PageBean) it.next();

                  String overrideStatus = clientBean.getValue("contractClientOverrideStatus");
                  EDITDate terminationDate = new EDITDate(clientBean.getValue("terminationDate"));
                  EDITDate currentDate = new EDITDate();                  

               	  // This will show all active or terminated clients, but not those that have been deleted
                  if (overrideStatus.equalsIgnoreCase("P") || (overrideStatus.equalsIgnoreCase("D") && terminationDate.beforeOREqual(currentDate.addYears(2)))) {

                      String summaryMiddleName = clientBean.getValue("middleName");
                      String summaryLastName = clientBean.getValue("lastName");
                      if (summaryLastName.equals(""))
                      {
                          name = clientBean.getValue("corporateName");
                      }
                      else if (summaryMiddleName.length() > 0)
                      {
                          name = clientBean.getValue("firstName") + " " +
                                 summaryMiddleName.substring(0,1) +  " " +
                                 clientBean.getValue("lastName");
                      }
                      else
                      {
                          name=clientBean.getValue("firstName") + " " + clientBean.getValue("lastName");
                      }

                    name = clientBean.getValue("prefix") + " " + name + " " + clientBean.getValue("suffix");
                      clientTaxId = clientBean.getValue("taxId");

                      clientRole = clientBean.getValue("relationshipInd");
                      clientOption = clientBean.getValue("optionId");
                      sClientRoleFK = clientBean.getValue("clientRoleFK");
                      sContractClientPK = clientBean.getValue("contractClientPK");
                      sTerminationDate = clientBean.getValue("terminationDate");
                      sRiderNumber = clientBean.getValue("riderNumber");

                      rowToMatch = clientTaxId + clientRole + clientOption + sClientRoleFK + sContractClientPK;

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
            <tr class="<%= trClass %>" isSelected="<%= selected %>" id="rowId_<%= rowId++ %>"
                onMouseOver="highlightRow()" onMouseOut="unhighlightRow()" onClick="selectRow()"
                clientRoleFK="<%= sClientRoleFK %>" riderNumber="<%= sRiderNumber %>" contractClientPK="<%= sContractClientPK %>">
              <td width="20%" nowrap id="nameId_<%= columnId %>">
                <%= name %>
              </td>
              <td width="20%" nowrap id="taxId_<%= columnId %>">
                <%= clientTaxId %>
              </td>
              <td width="20%" nowrap id="relationshipId_<%= columnId %>">
                <%= clientRole %>
              </td>
              <td width="20%" nowrap id="terminationDate_<%= columnId %>">
                <%= sTerminationDate %>
              </td>
              <td width="20%" nowrap id="optionId_<%= columnId++ %>">
                <%= clientOption %>
              </td>
            </tr>
            <%
                  } // end if
              } // end while
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

 <input type="hidden" name="selectedTaxId" value="">
 <input type="hidden" name="selectedOptionId"     value="">
 <input type="hidden" name="selectedRelationship" value="">
 <input type="hidden" name="selectedClientRoleFK" value="">
 <input type="hidden" name="selectedContractClientPK" value="">
  <input type="hidden" name="selectedRiderNumber" value="">

 <input type="hidden" name="optionId"     value="<%= optionValue %>">
 <input type="hidden" name="relationship" value="<%= relationship %>">
 <input type="hidden" name="contractClientPK" value="<%= contractClientPK %>">
 <input type="hidden" name="contractClientAllocationPK" value="<%= contractClientAllocationPK %>">
 <input type="hidden" name="clientRoleFK" value="<%= clientRoleFK %>">
 <input type="hidden" name="segmentFK" value="<%= segmentFK %>">
 <input type="hidden" name="disbursementSource" value="<%= disbursementSource %>">
 <input type="hidden" name="genderId" value="<%= genderId %>">
 <input type="hidden" name="prefix" value="<%= prefix %>">
 <input type="hidden" name="suffix" value="<%= suffix %>">
 <input type="hidden" name="issueAge" value="<%= issueAge %>">
 <input type="hidden" name="taxId" value="<%= taxId %>">
 <input type="hidden" name="employeeIdentification" value="<%= employeeIdentification %>">
 <input type="hidden" name="lastName" value="<%= lastName %>">
 <input type="hidden" name="firstName" value="<%= firstName %>">
 <input type="hidden" name="middleName" value="<%= middleName %>">
 <input type="hidden" name="corporateName" value="<%= corporateName %>">
 <input type="hidden" name="dob" value="<%= dob %>">
 <input type="hidden" name="usCitizenIndStatus" value="">
 <input type="hidden" name="contractClientOverrideStatus" value="<%= contractClientOverrideStatus %>">
 <input type="hidden" name="operator" value="<%= operator %>">
 <input type="hidden" name="maintDateTime" value="<%= maintDateTime %>">

 <!-- ***** FOR DIALOG WINDOWS *****//-->
 <input type="hidden" name="usCitizenIndStatus" value="">
 <input type="hidden" name="accumulationIndStatus" value="<%=accumulationIndStatus %>">

 <input type="hidden" name="amtPaidToDate" value="<%= amtPaidToDate %>">
 <input type="hidden" name="amtPaidYTD" value="<%= amtPaidYTD %>">
 <input type="hidden" name="fedWithholdingYTD" value="<%= fedWithholdingYTD %>">
 <input type="hidden" name="stateWithholdingYTD" value="<%= stateWithholdingYTD %>">
 <input type="hidden" name="cityWithholdingYTD" value="<%= cityWithholdingYTD %>">
 <input type="hidden" name="countyWithholdingYTD" value="<%= countyWithholdingYTD %>">
 <input type="hidden" name="relationToEmp" value="<%= relationToEmp %>">
 <input type="hidden" name="riderNumber" value="<%= riderNumber %>">

 <!-- recordPRASEEvents is set by the toolbar -->
 <input type="hidden" name="recordPRASEEvents" value="false">
</form>
</body>
</html>
