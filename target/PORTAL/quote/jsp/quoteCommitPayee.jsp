<!-- quoteCommitPayee.jsp  //-->

<!-- ****** JAVA CODE ***** //-->

<%@ page  errorPage="/common/jsp/error.jsp" autoFlush="true" %>
<%@ page import="java.util.*,
                 edit.common.*,
                 edit.common.vo.*,
                 edit.common.vo.user.*,
                 fission.beans.*,
                 fission.utility.Util,
                 fission.utility.*,
                 edit.portal.common.session.UserSession" %>

<jsp:useBean id="quoteMainSessionBean"
    class="fission.beans.SessionBean" scope="session"/>

<jsp:useBean id="quoteClients"
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

    PageBean mainBean = quoteMainSessionBean.getPageBean("formBean");
    String companyStructureId = Util.initString(mainBean.getValue("companyStructureId"), "0");

	PageBean formBean     = quoteMainSessionBean.getPageBean("clientFormBean");

    String contractClientPK = formBean.getValue("contractClientPK");
    String contractClientAllocationPK = formBean.getValue("contractClientAllocationPK");
    String clientRoleFK    = formBean.getValue("clientRoleFK");
	String taxId    	   = formBean.getValue("taxId");
	String employeeIdentification = formBean.getValue("employeeIdentification");
	String lastName   	   = formBean.getValue("lastName");
	String firstName  	   = formBean.getValue("firstName");
	String middleName 	   = formBean.getValue("middleName");
    String corporateName   = formBean.getValue("corporateName");

    String dob	= formBean.getValue("dob");
    
	String issueAge        = formBean.getValue("issueAge");
    String genderId        = formBean.getValue("genderId");
    String prefix         = formBean.getValue("prefix");
    String suffix         = formBean.getValue("suffix");
    String operator            = formBean.getValue("operator");
    String maintDateTime            = formBean.getValue("maintDateTime");
    String riderNumber = formBean.getValue("riderNumber");

	String effectiveDate    = formBean.getValue("effectiveDate");

	String terminateDate    = formBean.getValue("terminationDate");

    String disbAddressType = Util.initString(formBean.getValue("disbAddressType"), "");
    String corrAddressType = Util.initString(formBean.getValue("corrAddressType"), "");

	String allocationPercent = formBean.getValue("allocationPercent");
    String allocationDollars = Util.initString(formBean.getValue("allocationDollars"), "0");
    String disbursementSource = formBean.getValue("disbursementSource");
    if (disbursementSource == null) {

        disbursementSource = "";
    }
    String relationToIns   = formBean.getValue("relationToIns");
    String phoneAuth       = formBean.getValue("phoneAuth");

    CodeTableVO[] phoneAuths = codeTableWrapper.getCodeTableEntries("PHONEAUTHORIZATION", Long.parseLong(companyStructureId));
    CodeTableVO[] relToIns = codeTableWrapper.getCodeTableEntries("RELATIONTOINSURED", Long.parseLong(companyStructureId));
    CodeTableVO[] addressTypes = codeTableWrapper.getCodeTableEntries("ADDRESSTYPE", Long.parseLong(companyStructureId));

	String clientFound     = (String) request.getAttribute("clientFound");

	String deletionWarning = (String) request.getAttribute("deletionWarning");

	String optionId 	   = formBean.getValue("optionId");
	String relationship    = formBean.getValue("relationshipInd");
	String usCitizenIndStatus  = formBean.getValue("usCitizenIndStatus");
    String overrideStatus = formBean.getValue("overrideStatus");

    String printAs                     = formBean.getValue("printAs");
    if (printAs == null) {

        printAs = "";
    }
    String printAs2                    = formBean.getValue("printAs2");
    if (printAs2 == null) {

        printAs2 = "";
    }

    String rowToMatchBase  = taxId + relationship + optionId + contractClientPK + clientRoleFK;

    String oldOptionId = formBean.getValue("oldOptionId");
    String oldRelationship = formBean.getValue("oldRelationship");

    UserSession userSession = (UserSession) session.getAttribute("userSession");

    String selectedTaxId = (String)request.getAttribute("selectedTaxId");
    String selectedOptionId = (String)request.getAttribute("selectedOptionId");
    String selectedRelationship = (String)request.getAttribute("selectedRelationship");
    String selectedClientRoleFK = (String)request.getAttribute("selectedClientRoleFK");
    String selectedContractClientPK = (String)request.getAttribute("selectedContractClientPK");
    String selectedRiderNumber = (String)request.getAttribute("selectedRiderNumber");

%>

<%!
    private TreeMap sortClientsByRole(SessionBean clients) {

        Map clientBeans = clients.getPageBeans();

		TreeMap sortedClients = new TreeMap();

		Iterator enumer  = clientBeans.values().iterator();

		while (enumer.hasNext()) {

			PageBean clientBean = (PageBean) enumer.next();

			String role = clientBean.getValue("relationshipInd");
            String contractClientPK = clientBean.getValue("contractClientPK");
            String clientRoleFK = clientBean.getValue("clientRoleFK");

            sortedClients.put(role + contractClientPK + clientRoleFK, clientBean);
        }

		return sortedClients;
    }
%>
<html>

<script src="/PORTAL/common/javascript/commonJavascriptFunctions.js"></script>
<script src="/PORTAL/common/javascript/formatDateFunctions.js"></script>
<script src="/PORTAL/common/javascript/calendarSelector.js"></script>

<script language="Javascript1.2">

    var clientMessage = "<%= clientMessage %>";
    var payeeMessage = "<%= payeeMessage %>";

	var dialog = null;

	var f = null;

    var shouldShowLockAlert = true;
    var editableContractStatus = true;
    
    var relationship    = "<%= relationship %>";
    var taxId           = "<%= taxId %>";
	var clientFound     = "<%= clientFound %>";
	var deletionWarning = "<%= deletionWarning %>";
    var clientRoleFK    = "<%= clientRoleFK %>";
    var contractClientPK = "<%= contractClientPK %>";

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

		f = document.quoteCommitPayeeForm;

		top.frames["main"].setActiveTab("clientTab");

        if (payeeMessage != "" &&
            payeeMessage != null) {

            alert(payeeMessage);
        }

        if (clientMessage != "")
        {
            alert(clientMessage);
        }

        shouldShowLockAlert = <%= ! userSession.getSegmentIsLocked() %>;

        // check for terminated status to disallow contract updates
        editableContractStatus = <%= userSession.getEditableSegmentStatus()%>;
        
        for (var i = 0; i < f.elements.length; i++)
        {
            var elementType = f.elements[i].type;

            if ( (elementType == "text" || elementType == "button") && (shouldShowLockAlert == true || editableContractStatus == false) )
            {
                f.elements[i].onclick = showLockAlert;
                f.elements[i].onkeydown = showLockAlert;
            }
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

    function checkForRequiredFields()
    {
		return true;
    }

	function checkForDeletionWarning() {

		if (deletionWarning == "true") {

			alert("Can Not Delete This Entry");
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

        f.dob.value       = "";

		f.issueAge.value = "";

		f.relationshipInd.value  = "";
		f.optionId.value      = "";
                f.maintDateTime.value = "";
                f.operator.value      = "";
                
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

		sendTransactionAction("QuoteDetailTran", "showClientDetailSummary", "contentIFrame");
	}

	//----- The Pages Toolbar functions

	function updateClientInfo() {

        if (relationship == "")
        {
            alert ("Client Must Be Selected For Save");
        }
        else if (f.allocationPercent.value == "")
        {
            alert("Allocation Percent Must Be Entered");
        }
        else
        {
            sendTransactionAction("QuoteDetailTran", "updateClientInfo","contentIFrame");
            clearFormFields();
        }
	}

	function showContractClientAddDialog() {

		clearFormFields();

        var width = screen.width;
        var height = .85 * screen.height;

		openDialog("","contractClientAddDialog","top=0,left=0,resizable=no,width=" + width + ",height=" + height,"QuoteDetailTran", "showContractClientAddDialog");
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

            openDialog("","preferenceDialog","left=0,top=0,resizable=no,width=" + width + ",height=" + height, "QuoteDetailTran", "showPreferences", "preferenceDialog");
       }
	}

	function cancelClientAdd() {

		clearFormFields();
		sendTransactionAction("QuoteDetailTran", "cancelQuoteNonPayeeOrPayee", "contentIFrame");
	}

	function deleteSelectedClient() {

		clearFormFields();

		sendTransactionAction("QuoteDetailTran", "deleteSelectedClient", "contentIFrame");
	}

    function prepareToSendTransactionAction(transaction, action, target)
    {
        sendTransactionAction(transaction, action, target)
    }

	function showQuestionnaireResponse()
    {
        f.selectedContractClientPK.value = contractClientPK;
        var width = .95 * screen.width;
        var height = .35 * screen.height;
        openDialog("","QuestionnaireResponse","top=0,left=0,resizable=no,width=" + width + ",height=" + height, "QuoteDetailTran", "showQuestionnaireResponseDialog");
	}

</script>

<head>
<title>New Business Payee</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta http-equiv="Cache-Control" content="no-store">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Expires" content="0">

<link href="/PORTAL/common/css/PORTAL.css" rel="stylesheet" type="text/css">
</head>

<body class="mainTheme" onLoad="init(); checkForDeletionWarning()" style="border-style:solid; border-width:1;">
<form name= "quoteCommitPayeeForm" method="post" action="/PORTAL/servlet/RequestManager">

<input type="hidden" name="page" value="">
<jsp:include page="quoteInfoHeader.jsp" flush="true"/>

<span id="mainContent" style="border-style:solid; border-width:1;  position:relative; width:100%; height:100%; top:0; left:0; z-index:0; overflow:visible">
  <table width="100%" height="25%" border="0" cellspacing="0" cellpadding="5">
    <tr>
      <td align="left" nowrap width="18%">Tax Id:&nbsp;
        <input disabled type="text" name="taxId" maxlength="11" size="11" value="<%= taxId %>" >
      </td>
      <td align="left" nowrap width="23%">Last Name:&nbsp;
        <input disabled type="text" name="lastName" maxlength="30" size="30" value="<%= lastName %>">
      </td>
      <td align="left" nowrap>First Name:&nbsp;
        <input disabled type="text" name="firstName" maxlength="15" size="15" value="<%= firstName %>">
      </td>
      <td align="left" nowrap width="19%">Middle Name:&nbsp;
        <input disabled type="text" name="middleName" maxlength="15"  size="15" value="<%= middleName %>">
      </td>
    </tr>
    <tr>
      <td align="left" nowrap>DOB:&nbsp;
        <input disabled type="text" name="dob" value="<%= dob %>" size='10' maxlength="10">
        </td>
      
      <td align="left" nowrap width="23%">Gender Type:&nbsp;
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
      <td align="left" nowrap colspan="2">Corporate Name:&nbsp;
        <input disabled type="text" name="corporateName" maxlength="60" size="60" value="<%= corporateName %>" >
      </td>
      <td align="left" nowrap width="18%">Employee ID:&nbsp;
        <input type="text" name="employeeIdentification" maxlength="11" size="11" value="<%= employeeIdentification %>" >
      </td>
    </tr>
    <tr>
      <td align="left" nowrap>Issue Age:&nbsp;
        <input disabled type="text" name="issueAge" maxlength="4" size="2" value="<%= issueAge %>">
      </td>
  	  <td align="left" nowrap>Disbursement Source:&nbsp;
      	<input disabled type="text" name="disbursementSource" maxlength="20" size="20" value="<%= disbursementSource %>">
      </td>
      <td align="left" nowrap width="23%" colspan="2">
        <input disabled type="checkbox" name="usCitizenInd" <%=usCitizenIndStatus %>>
        US Citizen Indicator
      </td>
    </tr>
    <tr>
      <td align="left" align="left" nowrap>Telephone Authorization:&nbsp;
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
      <td align="left" align="left" nowrap>Relationship to Insured:&nbsp;
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

      <td align="left" colspan="3" nowrap>Termination Date:&nbsp;
           <input type="text" name="terminateDate" value="<%= terminateDate %>" size='10' maxlength="10" onKeyUp="DateFormat(this,this.value,event,false)" onBlur="DateFormat(this,this.value,event,true)">
           <a href="javascript:show_calendar('f.terminateDate', f.terminateDate.value);"><img src="/PORTAL/common/images/calendarIcon.gif" width="16" height="16" border="0" alt="Select a date from the calendar"></a>
      </td>

    </tr>
    <tr>
      <td align="left" nowrap width="19%" colspan="4"> Allocation %:
        <input type="text" name="allocationPercent" maxlength="5" size="5" tabindex="11" value="<%= allocationPercent %>">
      </td>
    </tr>
    <tr>
      <td align="left" nowrap width="19%">Allocation Amount:
        <input type="text" name="allocationDollars" maxlength="5" size="5" value="<%= allocationDollars  %>" CURRENCY>
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
      <td align="left">
        <input type="button" name="add" value= " Add  " onClick="showContractClientAddDialog()">
	    <input type="button" name="save" value=" Save " onClick="updateClientInfo()">
	    <input type="button" name="cancel" value="Cancel" onClick="cancelClientAdd()">
	    <input type="button" name="delete" value="Delete" onClick="deleteSelectedClient()">
      </td>
	</tr>
  </table>
  <table class="summaryArea" id="summaryTable" width="100%" height="30%" border="0" cellspacing="0" cellpadding="0">
    <tr height="1%">
        <th width="25%" align="left">Name</th>
        <th width="25%" align="left">Client ID</th>
        <th width="25%" align="left">Relationship</th>
        <th width="25%" align="left">Coverage</th>
    </tr>
    <tr width="100%" height="75%">
      <td colspan="4">
        <span class="scrollableContent">
          <table class="scrollableArea" id="clientSummary" width="100%" border="0" cellspacing="0" cellpadding="0">
            <%
              int rowId   = 0;
              int columnId = 0;

              String rowToMatch = "";
              String trClass = "";
              String selected = "";

              String name          = null;
              String clientRole	   = null;
              String clientOption  = null;
              String clientTaxId   = null;
              String sClientRoleFK = null;
              String sContractClientPK = null;
              String sRiderNumber = "";

              Map sortedClientBeans = sortClientsByRole(quoteClients);

              Iterator it = sortedClientBeans.values().iterator();

              while (it.hasNext()) {

                  PageBean clientBean = (PageBean) it.next();

                  String sOverrideStatus = clientBean.getValue("overrideStatus");
                  if (!sOverrideStatus.equalsIgnoreCase("D"))
                  {
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
                      sRiderNumber = clientBean.getValue("riderNumber");

                      rowToMatch = clientTaxId + clientRole + clientOption + sContractClientPK + sClientRoleFK;

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
                onClick="selectRow()" onMouseOver="highlightRow()" onMouseOut="unhighlightRow()"
                clientRoleFK="<%= sClientRoleFK %>" riderNumber="<%= sRiderNumber %>" contractClientPK="<%= sContractClientPK %>">
              <td width="25%" nowrap id="nameId_<%= columnId %>">
                <%= name %>
              </td>
              <td width="25%" nowrap id="taxId_<%= columnId %>">
                <%= clientTaxId %>
              </td>
              <td width="25%" nowrap id="relationshipId_<%= columnId %>">
                <%= clientRole %>
              </td>
              <td width="25%" nowrap id="optionId_<%= columnId++ %>">
                <%= clientOption %>
              </td>
            </tr>
            <%
                  }
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

 <input type="hidden" name="selectedTaxId" value="<%= selectedTaxId %>">
 <input type="hidden" name="selectedOptionId"     value="<%= selectedOptionId %>">
 <input type="hidden" name="selectedRelationship" value="<%= selectedRelationship %>">
 <input type="hidden" name="selectedClientRoleFK" value="<%= selectedClientRoleFK %>">
 <input type="hidden" name="selectedContractClientPK" value="<%= selectedContractClientPK %>">
 <input type="hidden" name="selectedRiderNumber" value="<%= selectedRiderNumber %>">
 
 <input type="hidden" name="oldOptionId" value="<%= oldOptionId %>">
 <input type="hidden" name="oldRelationship" value="<%= oldRelationship %>">

 <input type="hidden" name="optionId"     value="<%= optionId %>">
 <input type="hidden" name="relationshipInd" value="<%= relationship %>">
 <input type="hidden" name="disbursementSource" value="<%= disbursementSource %>">
 <input type="hidden" name="genderId" value="<%= genderId %>">
 <input type="hidden" name="issueAge" value="<%= issueAge %>">
 <input type="hidden" name="taxId" value="<%= taxId %>">
 <input type="hidden" name="employeeIdentification" value="<%= employeeIdentification %>">
 <input type="hidden" name="lastName" value="<%= lastName %>">
 <input type="hidden" name="firstName" value="<%= firstName %>">
 <input type="hidden" name="middleName" value="<%= middleName %>">
 <input type="hiddne" name="corporateName" value="<%= corporateName %>">
 <input type="hidden" name="dob" value="<%= dob %>">
 <input type="hidden" name="contractClientPK" value="<%= contractClientPK %>">
 <input type="hidden" name="contractClientAllocationPK" value="<%= contractClientAllocationPK %>">
 <input type="hidden" name="clientRoleFK" value="<%= clientRoleFK %>">
 <input type="hidden" name="usCitizenIndStatus" value="">
 <input type="hidden" name="overrideStatus" value="<%= overrideStatus %>">
 <input type="hidden" name="prefix" value="<%= prefix %>">
 <input type="hidden" name="suffix" value="<%= suffix %>">
 <input type="hidden" name="operator" value="<%= operator %>">
 <input type="hidden" name="maintDateTime" value="<%= maintDateTime %>">
 <input type="hidden" name="riderNumber" value="<%= riderNumber %>">

 <!-- recordPRASEEvents is set by the toolbar when saving the client -->
 <input type="hidden" name="recordPRASEEvents" value="false">

</form>
</body>
</html>
