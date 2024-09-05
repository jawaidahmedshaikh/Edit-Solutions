<!-- quoteCommitNonPayee.jsp  //-->

<!-- ****** JAVA CODE ***** //-->

<%@ page  errorPage="/common/jsp/error.jsp" autoFlush="true" %>
<%@ page import="java.util.*,
                 edit.common.*,
                 edit.common.vo.*,
                 edit.common.vo.user.*,
                 fission.beans.*,
                 edit.portal.common.session.UserSession,
                 fission.utility.*" %>


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

    String beneMessage = (String) request.getAttribute("beneMessage");
    if (beneMessage == null)
    {
        beneMessage = "";
    }

    CodeTableWrapper codeTableWrapper = CodeTableWrapper.getSingleton();

    PageBean mainBean = quoteMainSessionBean.getPageBean("formBean");
    String companyStructureId = Util.initString(mainBean.getValue("companyStructureId"), "0");

	PageBean formBean     = quoteMainSessionBean.getPageBean("clientFormBean");

    String contractClientPK = formBean.getValue("contractClientPK");
    String contractClientAllocationPK = formBean.getValue("contractClientAllocationPK");
    String clientRoleFK    = formBean.getValue("clientRoleFK");
	String taxId     	   = formBean.getValue("taxId");
	String employeeIdentification = formBean.getValue("employeeIdentification");
	String lastName   	   = formBean.getValue("lastName");
	String firstName  	   = formBean.getValue("firstName");
	String middleName 	   = formBean.getValue("middleName");
    String corporateName   = formBean.getValue("corporateName");
	String dob   	   = formBean.getValue("dob");
	String issueAge        = formBean.getValue("issueAge");
	String genderId        = formBean.getValue("genderId");
    String prefix         = formBean.getValue("prefix");
    String suffix         = formBean.getValue("suffix");
    String operator            = formBean.getValue("operator");
    String maintDateTime            = formBean.getValue("maintDateTime");
    String riderNumber = formBean.getValue("riderNumber");

    String relationToIns   = formBean.getValue("relationToIns");
    String phoneAuth       = formBean.getValue("phoneAuth");
    String allocationPercent = formBean.getValue("allocationPercent");
    String allocationDollars = Util.initString(formBean.getValue("allocationDollars"), "0.0");
    String splitEqualIndStatus = formBean.getValue("splitEqualInd");
    if (splitEqualIndStatus.equalsIgnoreCase("Y"))
    {
        splitEqualIndStatus = "checked";
    }
    else
    {
        splitEqualIndStatus = "unchecked";
    }

    String disbAddressType = Util.initString(formBean.getValue("disbAddressType"), "");
    String corrAddressType = Util.initString(formBean.getValue("corrAddressType"), "");
    String effectiveDate   = formBean.getValue("effectiveDate");
    String payorOf         = formBean.getValue("payorOf");
    String overrideStatus  = formBean.getValue("overrideStatus");
    String newIssuesEligibilityStatus = Util.initString(formBean.getValue("newIssuesEligibilityStatus"), "");
    String newIssEligStatusDisplay = "";
    if (!newIssuesEligibilityStatus.equals(""))
    {
        newIssEligStatusDisplay = codeTableWrapper.getCodeDescByCodeTableNameAndCode("YESNO", newIssuesEligibilityStatus);
    }

    String newIssuesStartDate = formBean.getValue("newIssuesStartDate");

    String beneRelationshipToIns = formBean.getValue("beneRelationshipToIns");

    CodeTableVO[] phoneAuths = codeTableWrapper.getCodeTableEntries("PHONEAUTHORIZATION", Long.parseLong(companyStructureId));
    CodeTableVO[] relToIns = codeTableWrapper.getCodeTableEntries("RELATIONTOINSURED", Long.parseLong(companyStructureId));
    CodeTableVO[] addressTypes = codeTableWrapper.getCodeTableEntries("ADDRESSTYPE", Long.parseLong(companyStructureId));
    CodeTableVO[] payorOfCT = codeTableWrapper.getCodeTableEntries("PAYOROF", Long.parseLong(companyStructureId));

	String clientFound     = (String) request.getAttribute("clientFound");

	String deletionWarning = (String) request.getAttribute("deletionWarning");

	String optionValue 	   = formBean.getValue("optionId");

	String relationship    = formBean.getValue("relationshipInd");
 	String rowToMatchBase  = taxId + relationship + optionValue + contractClientPK + clientRoleFK;

	String usCitizenIndStatus  = formBean.getValue("usCitizenIndStatus");

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

<script language="Javascript1.2">

	var dialog = null;

	var f = null;

    var shouldShowLockAlert = true;
    var editableContractStatus = true;
    
    var clientMessage   = "<%= clientMessage %>";
    var beneMessage     = "<%= beneMessage %>";
    var relationship    = "<%= relationship %>";
    var taxId           = "<%= taxId %>";
	var clientFound     = "<%= clientFound %>";
	var deletionWarning = "<%= deletionWarning %>";
    var clientRoleFK    = "<%= clientRoleFK %>";
    var contractClientPK = "<%= contractClientPK %>";

	function init() {

		f = document.quoteCommitNonPayeeForm;

		top.frames["main"].setActiveTab("clientTab");

        if (beneMessage != "" &&
            beneMessage != null) {

            alert(beneMessage);
        }

        if (clientMessage != "")
        {
            alert(clientMessage);
        }

        shouldShowLockAlert = <%= ! userSession.getSegmentIsLocked() %>;

        // check for terminated status to disallow contract updates
        editableContractStatus = <%= userSession.getEditableSegmentStatus()%>;
        
        for (var i = 0; i < f.elements.length; i++) {

            var elementType = f.elements[i].type;

            if ((elementType == "text" || elementType == "button") &&
            	(shouldShowLockAlert == true || editableContractStatus == false)) {

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

	function showContractClientAddDialog() {

		clearFormFields();

        var width = screen.width;
        var height = .85 * screen.height;

		openDialog("","contractClientAddDialog","top=0,left=0,resizable=no,width=" + width + ",height=" + height,"QuoteDetailTran", "showContractClientAddDialog");
	}

	function openDialog(theURL,winName,features,transaction,action)
    {

	    dialog = window.open(theURL,winName,features);

	    prepareToSendTransactionAction(transaction, action, winName);
	}

	function sendTransactionAction(transaction, action, target)
    {
        unformatCurrency(f);

		if (f.usCitizenInd.checked == true)
        {
			f.usCitizenIndStatus.value = "checked";
		}

		f.transaction.value=transaction;
		f.action.value=action;

		f.target = target;

		f.submit();
	}

	function clearFormFields() {

		f.taxId.value      = "";

		f.lastName.value      = "";
		f.firstName.value     = "";
		f.middleName.value    = "";
                f.prefix.value       = "";
                f.suffix.value       = "";

        f.dob.value       = "";

		f.issueAge.value      = "";

		f.relationshipInd.value  = "";
		f.optionId.value      = "";

		f.usCitizenInd.checked   = false;
                f.maintDateTime.value = "";
                f.operator.value      = "";

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

		prepareToSendTransactionAction("QuoteDetailTran", "showClientDetailSummary", "contentIFrame");
	}

	//----- The Pages Toolbar functions

	function updateClientInfo() {
        //sramamurthy added relationship check
        if (relationship == "")
        {
            alert ("Client Must Be Selected For Save");
        }
        else if ((relationship == "PBE" ||
                  relationship == "CBE") &&
                 (valueIsEmpty(f.allocationPercent.value) ||
                  valueIsZero(f.allocationPercent.value)) &&
                 (valueIsEmpty(f.allocationDollars.value) ||
                  valueIsZero(f.allocationDollars.value)) &&
                  f.splitEqual.checked == false)
        {
            alert("Allocation Dollars or Percent Must Be Entered");
        }
        else
        {

            prepareToSendTransactionAction("QuoteDetailTran", "updateClientInfo","contentIFrame");
            clearFormFields();
        }
	}

	function cancelClientAdd() {

		clearFormFields();

		prepareToSendTransactionAction("QuoteDetailTran", "cancelQuoteNonPayeeOrPayee", "contentIFrame");
	}

	function deleteSelectedClient() {

		clearFormFields();

		prepareToSendTransactionAction("QuoteDetailTran", "deleteSelectedClient", "contentIFrame");
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

    function prepareToSendTransactionAction(transaction, action, target)
    {
        if (f.splitEqual.checked == true)
        {
            f.splitEqualInd.value = "Y";
        }
        else
        {
            f.splitEqualInd.value = "N";
        }

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
<title>New Business Non-Payee</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta http-equiv="Cache-Control" content="no-store">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Expires" content="0">

<link href="/PORTAL/common/css/PORTAL.css" rel="stylesheet" type="text/css">
</head>

<body class="mainTheme" onLoad="init(); checkForDeletionWarning()" style="border-style:solid; border-width:1;">
<form name= "quoteCommitNonPayeeForm" method="post" action="/PORTAL/servlet/RequestManager">

<input type="hidden" name="page" value="">
<jsp:include page="quoteInfoHeader.jsp" flush="true"/>

<span id="mainContent" style="border-style:solid; border-width:1; position:relative; width:100%; height:40%; top:0; left:0; z-index:0; overflow:visible">
  <table width="100%" height="20%" border="0" cellspacing="0" cellpadding="4">
	<tr>
	  <td align="left" nowrap>Tax Id:&nbsp;
	    <input disabled type="text" name="taxId" maxlength="11" size="11" value="<%= taxId%>">
	  </td>
	  <td align="left" nowrap>Last Name:&nbsp;
	    <input disabled type="text" name="lastName" maxlength="30" size="30" value="<%= lastName%>">
	  </td>
	  <td align="left" nowrap>First Name:&nbsp;
	    <input disabled type="text" name="firstName" maxlength="15" size="15" value="<%= firstName%>">
	  </td>
          <td align="left" nowrap>Middle Name:&nbsp;
	    <input disabled type="text" name="middleName" maxlength="15" size="15" value="<%= middleName%>">
	  </td>
	</tr>
	<tr>
	  <td align="left" nowrap>DOB:&nbsp;
	    <input disabled type="text" name="dob" value="<%= dob %>" size='10' maxlength="10">
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
	  <td align="left" nowrap colspan="2">Corporate Name:&nbsp;
	    <input disabled type="text" name="corporateName" maxlength="60" size="60" value="<%= corporateName %>">
	  </td>
      <td align="left" nowrap>Employee ID:&nbsp;
	    <input type="text" name="employeeIdentification" maxlength="11" size="11" value="<%= employeeIdentification%>">
	  </td>
	</tr>
	<tr>
	  <td align="left" nowrap>Issue Age:&nbsp;
	    <input disabled type="text" name="issueAge" disabled maxlength="4" size="2" value="<%= issueAge %>">
      </td>
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
	  <td colspan="2" align="left" nowrap>
	    <input disabled type="checkbox" name="usCitizenInd" <%=usCitizenIndStatus %>>
	    US Citizen Indicator
      </td>
	</tr>
    <tr>
      <td align="left" nowrap width="19%">Allocation %:
        <input type="text" name="allocationPercent" maxlength="5" size="5" value="<%= allocationPercent %>">
      </td>
      <td align="left" nowrap>Split Equal
        <input type="checkbox" name="splitEqual" <%= splitEqualIndStatus %> >
      </td>
      <td align="left" nowrap width="19%" colspan="2">Payor Of:&nbsp;
        <select name="payorOf">
	        <option value="Please Select">Please Select</option>
	        <%
                for(int i = 0; i < payorOfCT.length; i++) {

                    String codeTablePK = payorOfCT[i].getCodeTablePK() + "";
                    String codeDesc    = payorOfCT[i].getCodeDesc();
                    String code        = payorOfCT[i].getCode();

                    if (payorOf.equalsIgnoreCase(code)) {

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
      <td align="left" nowrap width="19%">Allocation Amount:
        <input type="text" name="allocationDollars" maxlength="5" size="5" value="<%= allocationDollars  %>" CURRENCY>
      </td>
      <td align="left" nowrap colspan="4" width="19%">Bene Relationship to Insured:
        <input type="text" name="beneRelationshipToIns" maxlength="50" size="50" value="<%= beneRelationshipToIns  %>">
      </td>
    </tr>
    <%
        if (relationship.equalsIgnoreCase("OWN"))
        {
    %>
    <tr>
      <td align="left" nowrap colspan="2">New Issues Eligibility:&nbsp;
        <input disabled type="text" name="newIssEligStatus" maxlength="5" size="5" value="<%= newIssEligStatusDisplay %>">
      </td>
      <td align="left" nowrap colspan="2">New Issues Start Date:&nbsp;
        <input disabled type="text" name="newIssuesStartDate" maxlength="10" size="10" value="<%= newIssuesStartDate %>">
      </td>
    </tr>
    <%
        }
    %>
    <tr>
      <td align="left" nowrap colspan="2">Relationship to Insured:&nbsp;
        <select name="relationToIns" tabindex="3">
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
      <td valign="top" align="left" nowrap rowspan="4" colspan="4">
        <b>Address Types:&nbsp;</b>
        <br>
        <br>
        <span style="border-style:solid; border-width:1; position:relative; width:75%; height:35% top:0; left:0; z-index:0; overflow:visible">
          <table width="100%" height="100%" border="0" cellspacing="1" cellpadding="1">
            <tr>
              <td align="right" nowrap>Disbursement:&nbsp;</td>
              <td align="left" nowrap>
                <select name="disbAddressType" tabindex="4">
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
                <select name="corrAddressType" tabindex="5">
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
	  <td align="left" nowrap colspan="2">
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
	  <td align="left" colspan="4" nowrap>&nbsp;
        <a href ="javascript:showPreferences()">Preference</a>
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        <a href ="javascript:showQuestionnaireResponse()">Questionnaire Responses</a>
      </td>
    </tr>
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
	<tr>
	  <td align="left" colspan="4" nowrap>&nbsp;</td>
	</tr>
  </table>
</span>
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
<table class="summaryArea" id="summaryTable" width="100%" height="2%" border="0" cellspacing="0" cellpadding="0">
  <tr height="1%">
    <th width="25%" align="left">Name</th>
    <th width="25%" align="left">Client ID</th>
    <th width="25%" align="left">Relationship</th>
    <th width="25%" align="left">Coverage</th>
  </tr>
</table>
<span class="scrollableContent" style="border-style:solid; border-width:1;  position:relative; width:100%; height:30%; top:0; left:0; background-color:#BBBBBB">
  <table class="summary" id="clientSummary" width="100%" style="border-style:solid; border-width:0;" border="0" cellspacing="0" cellpadding="0">
    <%
        int rowId   = 0;
        int columnId = 0;

        String rowToMatch = "";
        String trClass = "";
        boolean selected = false;

        String name = null;
        String clientRole = "";
        String clientOptionId = "";
        String clientTaxId = "";
        String sClientRoleFK = "";
        String sContractClientPK = "";
        String sRiderNumber = "";

        Map sortedClientBeans = sortClientsByRole(quoteClients);

        Iterator it = sortedClientBeans.values().iterator();

        while (it.hasNext())  {

            PageBean client = (PageBean) it.next();

            String sOverrideStatus = client.getValue("overrideStatus");
            if (!sOverrideStatus.equalsIgnoreCase("D"))
            {
                String summaryMiddleName = client.getValue("middleName");
                String summaryLastName = client.getValue("lastName");
                if (summaryLastName.equals(""))
                {
                    name = client.getValue("corporateName");
                }
                else if (summaryMiddleName.length() > 0)
                {
                    name = client.getValue("firstName") + " " +
                           summaryMiddleName.substring(0,1) +  " " +
                           client.getValue("lastName");
                }
                else
                {
                    name=client.getValue("firstName") + " " + client.getValue("lastName");
                }
                
                name = client.getValue("prefix") + " " + name + " " + client.getValue("suffix");

                clientTaxId = client.getValue("taxId");
                clientRole  = client.getValue("relationshipInd");
                clientOptionId = client.getValue("optionId");
                sClientRoleFK = client.getValue("clientRoleFK");
                sContractClientPK = client.getValue("contractClientPK");
                sRiderNumber = client.getValue("riderNumber");

                rowToMatch = clientTaxId + clientRole + clientOptionId + sContractClientPK + sClientRoleFK;

                if (rowToMatch.equals(rowToMatchBase))
                {
                    trClass = "highlighted";
                    selected = true;
                }
                else
                {
                    trClass = "default";
                    selected = false;
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
        <%= clientOptionId %>
      </td>
    </tr>
    <%
            } //end if
        } //end while
    %>
  </table>
</span>

<!-- ****** HIDDEN FIELDS ***** //-->
 <input type="hidden" name="transaction" value="">
 <input type="hidden" name="action"      value="">

 <input type="hidden" name="contractClientPK" value="<%= contractClientPK %>">
 <input type="hidden" name="clientRoleFK" value="<%= clientRoleFK %>">

 <input type="hidden" name="selectedTaxId" value="<%= selectedTaxId %>">
 <input type="hidden" name="selectedOptionId"     value="<%= selectedOptionId %>">
 <input type="hidden" name="selectedRelationship" value="<%= selectedRelationship %>">
 <input type="hidden" name="selectedClientRoleFK" value="<%= selectedClientRoleFK %>">
 <input type="hidden" name="selectedContractClientPK" value="<%= selectedContractClientPK %>">
 <input type="hidden" name="selectedRiderNumber" value="<%= selectedRiderNumber %>">


 <input type="hidden" name="oldOptionId" value="<%= oldOptionId %>">
 <input type="hidden" name="oldRelationship" value="<%= oldRelationship %>">

 <input type="hidden" name="optionId"     value="<%= optionValue %>">
 <input type="hidden" name="relationshipInd" value="<%= relationship %>">
 <input type="hidden" name="genderId" value="<%= genderId %>">
 <input type="hidden" name="issueAge" value="<%= issueAge %>">
 <input type="hidden" name="taxId" value="<%= taxId %>">
 <input type="hidden" name="employeeIdentification" value="<%= employeeIdentification %>">
 <input type="hidden" name="lastName" value="<%= lastName %>">
 <input type="hidden" name="firstName" value="<%= firstName %>">
 <input type="hidden" name="middleName" value="<%= middleName %>">
 <input type="hidden" name="corporateName" value="<%= corporateName %>">
 <input type="hidden" name="dob" value="<%= dob %>">
 <input type="hidden" name="usCitizenIndStatus" value="">
 <input type="hidden" name="effectiveDate" value="<%= effectiveDate %>">
 <input type="hidden" name="overrideStatus" value="<%= overrideStatus %>">
 <input type="hidden" name="newIssuesEligibilityStatus" value="<%= newIssuesEligibilityStatus %>">
 <input type="hidden" name="newIssuesStartDate" value="<%= newIssuesStartDate %>">
 <input type="hidden" name="splitEqualInd" value="">
 <input type="hidden" name="prefix" value="<%= prefix %>">
 <input type="hidden" name="suffix" value="<%= suffix %>">
 <input type="hidden" name="operator" value="<%= operator %>">
 <input type="hidden" name="maintDateTime" value="<%= maintDateTime %>">
 <input type="hidden" name="contractClientAllocationPK" value="<%= contractClientAllocationPK %>">
 <input type="hidden" name="riderNumber" value="<%= riderNumber %>">

 <!-- recordPRASEEvents is set by the toolbar when saving the client -->
 <input type="hidden" name="recordPRASEEvents" value="false">

</form>

</body>
</html>
