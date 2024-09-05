<!--
 * (c) 2000 - 2008 Systems Engineering Group, LLC.  All rights Reserved
 *  Systems Engineering Group, LLC Propietary and confidential.  Any use
 *  subject to the license agreement.
 -->

<!-- ****** JAVA CODE ***** //-->

<%@ page  errorPage="/common/jsp/error.jsp" autoFlush="true" %>
<%@ page import="java.util.*,
                 edit.common.*,
                 edit.common.vo.*,
                 edit.common.vo.user.*,
                 fission.beans.*,
                 edit.portal.common.session.UserSession,
                 fission.utility.Util,
                 role.ClientRole,
                 group.CaseProductUnderwriting" %>

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

    CodeTableWrapper codeTableWrapper = CodeTableWrapper.getSingleton();
    String companyStructureId = contractMainSessionBean.getPageBean("formBean").getValue("companyStructureId");

    String segmentStatus = contractMainSessionBean.getPageBean("formBean").getValue("segmentStatus");

	PageBean formBean     = contractMainSessionBean.getPageBean("clientFormBean");

    String contractClientPK = formBean.getValue("contractClientPK");
    String contractClientAllocationPK = formBean.getValue("contractClientAllocationPK");
    String clientRoleFK     = formBean.getValue("clientRoleFK");
    String segmentFK        = formBean.getValue("segmentFK");
	String taxId     	    = formBean.getValue("taxId");
	String employeeIdentification = formBean.getValue("employeeIdentification");
	String lastName   	    = formBean.getValue("lastName");
	String firstName  	    = formBean.getValue("firstName");
	String middleName 	    = formBean.getValue("middleName");
    String corporateName    = formBean.getValue("corporateName");
	String dob   	        = formBean.getValue("dob");
	String issueAge         = formBean.getValue("issueAge");
	String genderId         = formBean.getValue("genderId");
    String prefix         = formBean.getValue("prefix");
    String suffix         = formBean.getValue("suffix");
    String operator            = formBean.getValue("operator");
    String maintDateTime            = formBean.getValue("maintDateTime");
    String phoneAuth        = formBean.getValue("phoneAuth");
    String classType        = formBean.getValue("classType");
    String disbAddressType  = formBean.getValue("disbAddressType");
    String corrAddressType  = formBean.getValue("corrAddressType");
    String effectiveDate    = formBean.getValue("effectiveDate");
    String terminationReason = formBean.getValue("terminationReason");
    String pendingClassChangeIndStatus = formBean.getValue(("pendingClassChangeIndStatus"));
    String ratedGender     = formBean.getValue("ratedGender");
    String underwritingClass = formBean.getValue("underwritingClass");
    String relationToEmp   = formBean.getValue("relationToEmp");
    String riderNumber = formBean.getValue("riderNumber");
	String terminateDate   = formBean.getValue("terminationDate");
	String originalClassCT   = formBean.getValue("originalClassCT");

    CodeTableVO[] phoneAuths = codeTableWrapper.getCodeTableEntries("PHONEAUTHORIZATION", Long.parseLong(companyStructureId));
    CodeTableVO[] classTypes = codeTableWrapper.getCodeTableEntries("CLASS", Long.parseLong(companyStructureId));
    CodeTableVO[] tableRatings = codeTableWrapper.getCodeTableEntries("TABLERATING", Long.parseLong(companyStructureId));
    CodeTableVO[] addressTypes = codeTableWrapper.getCodeTableEntries("ADDRESSTYPE", Long.parseLong(companyStructureId));
    CodeTableVO[] ratedGenders = codeTableWrapper.getCodeTableEntries("RATEDGENDER", Long.parseLong(companyStructureId));
    CodeTableVO[] underwritingClasses = codeTableWrapper.getCodeTableEntries("UNDERWRITINGCLASS", Long.parseLong(companyStructureId));
//    CodeTableVO[] relToIns = codeTableWrapper.getCodeTableEntries("RELATIONTOINSURED", Long.parseLong(companyStructureId));
    CodeTableVO[] relToEmp = codeTableWrapper.getCodeTableEntries("RELATIONTOEMPLOYEE", Long.parseLong(companyStructureId));
    CodeTableVO[] yesNo = codeTableWrapper.getCodeTableEntries("YESNO");

    CaseProductUnderwriting[] underwritingClassValues = null;
    CaseProductUnderwriting[] ratedGenderValues = null;

	String deletionWarning = (String) request.getAttribute("deletionWarning");
	String optionValue 	   = formBean.getValue("optionId");
	String relationship    = formBean.getValue("relationshipInd");
    String usCitizenIndStatus  = formBean.getValue("usCitizenIndStatus");
    String contractClientOverrideStatus = formBean.getValue("contractClientOverrideStatus");
    String authorizedSignatureCT = formBean.getValue("authorizedSignatureCT");

	String rowToMatchBase  = taxId + relationship + optionValue + clientRoleFK + contractClientPK;

    UserSession userSession = (UserSession) session.getAttribute("userSession");

    String roleType = "";
    if (clientRoleFK != null)
    {
        roleType = ClientRole.findByPK(new Long(clientRoleFK)).getRoleTypeCT();
    }

    if (roleType.equalsIgnoreCase("TermInsured"))
    {
        ratedGenderValues = (CaseProductUnderwriting[]) request.getAttribute("ratedGenders");
        underwritingClassValues = (CaseProductUnderwriting[]) request.getAttribute("underwritingClasses");
    }

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

<script src="/PORTAL/common/javascript/commonJavascriptFunctions.js"></script>
<script src="/PORTAL/common/javascript/formatDateFunctions.js"></script>
<script src="/PORTAL/common/javascript/calendarSelector.js"></script>

<script language="Javascript1.2">

	var dialog = null;

	var f = null;

    var shouldShowLockAlert = true;
    var editableContractStatus = true;
    var segmentStatus = "<%= segmentStatus %>";

	var deletionWarning = "<%= deletionWarning %>";
    var relationship = "<%= relationship %>";
    var taxId = "<%= taxId %>";
    var clientMessage = "<%= clientMessage %>";
    var contractClientPK = "<%= contractClientPK %>";
    var clientRoleFK = "<%= clientRoleFK %>";
    var roleType        = "<%= roleType %>";

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

		f = document.contractCommitInsuredForm;

		top.frames["main"].setActiveTab("clientTab");

        shouldShowLockAlert = <%= !userSession.getSegmentIsLocked() %>;

        // check for terminated status to disallow contract updates
        if (segmentStatus != "ReducedPaidUp") {
        	editableContractStatus = <%= userSession.getEditableSegmentStatus()%>;
        }
        
        for (var i = 0; i < f.elements.length; i++) {

            var elementType = f.elements[i].type;

            if ((elementType == "text" || elementType == "button" || elementType == "select-one") &&
            		(shouldShowLockAlert == true || editableContractStatus == false)) {

                f.elements[i].onclick = showLockAlert;
                f.elements[i].onkeydown = showLockAlert;
            }
        }

        if (clientMessage != "")
        {
            alert(clientMessage);
        }

        if (roleType == "Insured")
        {
            document.getElementById("type").disabled       = true;
            document.getElementById("rtdGender").disabled  = true;
            document.getElementById("uwClass").disabled    = true;
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

	function checkForDeletionWarning() {

		if (deletionWarning == "true") {

			alert("Cannot Delete This Entry");
		}
	}

	function showContractClientAddDialog() {

		clearFormFields();

        var width = screen.width;
        var height = .85 * screen.height;

		openDialog("contractClientAddDialog","top=0,left=0,resizable=no,width=" + width + ",height=" + height,"ContractDetailTran", "showContractClientAddDialog");
	}

<%--	function openDialog(theURL,winName,features,transaction,action) {--%>
<%----%>
<%--	  dialog = window.open(theURL,winName,features);--%>
<%----%>
<%--	  sendTransactionAction(transaction, action, winName);--%>
<%--	}--%>

	function sendTransactionAction(transaction, action, target) {

		if (f.usCitizenInd.checked == true) {

			f.usCitizenIndStatus.value = "checked";
		}

        if (f.pendingClassChangeInd.checked == true)
        {
            f.pendingClassChangeIndStatus.value = "checked";
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
		f.dob.value      = "";

		f.issueAge.value      = "";

		f.relationship.value  = "";
		f.optionId.value      = "";

		f.genderId.selectedIndex = 0;
		f.usCitizenInd.checked   = false;

        f.classType.selectedIndex = 0;
        f.ratedGender.value     = "";
        f.underwritingClass.value = "";
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
        else if ((relationship == "PBE" ||
                  relationship == "CBE") &&
                 f.allocationPercent.value == "")
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

		f.reset();
		sendTransactionAction("ContractDetailTran", "cancelContractNonPayeeOrPayee", "contentIFrame");
	}

	function deleteSelectedClient() {

        alert("Can Not Delete This Entry");

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
        openDialog("QuestionnaireResponse","top=0,left=0,resizable=no,width=" + width + ",height=" + height, "ContractDetailTran", "showQuestionnaireResponseDialog");
	}

    function showClassGenderRatingsDialog()
    {

        var width = 0.80 * screen.width;
        var height = 0.30 * screen.height;

        openDialog("classGenderRatingsDialog","top=0,left=0,resizable=no", width, height);
<%--		openDialog("classGenderRatingsDialog","top=0,left=0,resizable=no,width=" + width + ",height=" + height,"ContractDetailTran", "showClassGenderRatingsDialog");--%>

        sendTransactionAction("ContractDetailTran", "showClassGenderRatingsDialog", "classGenderRatingsDialog");
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

            openDialog("preferenceDialog", "top=0,left=0,resizable=no", width, height);

            sendTransactionAction("ContractDetailTran", "showPreferences", "preferenceDialog");
       }
	}

</script>

<head>
<title>Contract Non-Payee</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta http-equiv="Cache-Control" content="no-store">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Expires" content="0">

<link href="/PORTAL/common/css/PORTAL.css" rel="stylesheet" type="text/css">
</head>

<body class="mainTheme" onLoad="init(); checkForDeletionWarning()" style="border-style:solid; border-width:1;">
<form name= "contractCommitInsuredForm" method="post" action="/PORTAL/servlet/RequestManager">

<input type="hidden" name="page" value="">
<jsp:include page="contractInfoHeader.jsp" flush="true"/>

<span id="mainContent" style="border-style:solid; border-width:1; position:relative; width:100%; height:100%; top:0; left:0; z-index:0; overflow:visible">
  <table width="100%" height="48%" border="0" cellspacing="0" cellpadding="4">
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
          <td align="left" nowrap>Middle Name:&nbsp;
	    <input disabled type="text" name="middleName" maxlength="15" size="15" value="<%= middleName%>">
	  </td>
	</tr>
	<tr>
	  <td align="left" nowrap>DOB:&nbsp;
	    <input disabled type="text" name="dob" maxlength ="10" size="10" value="<%= dob %>">
	  </td>
	  
	  <td align="left" nowrap >Gender Type:&nbsp;
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
	    <input type="text" name="employeeIdentification" maxlength="11" size="11" value="<%= employeeIdentification %>">
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
	  <td align="left" nowrap>
	    <input disabled type="checkbox" name="usCitizenInd" <%=usCitizenIndStatus %>>
	    US Citizen Indicator
      </td>
	</tr>
    <tr>
      <td align="left" nowrap colspan="2">Termination Date:&nbsp;
           <input type="text" name="terminateDate" value="<%= terminateDate %>" size='10' maxlength="10" onKeyUp="DateFormat(this,this.value,event,false)" onBlur="DateFormat(this,this.value,event,true)">
           <a href="javascript:show_calendar('f.terminateDate', f.terminateDate.value);"><img src="/PORTAL/common/images/calendarIcon.gif" width="16" height="16" border="0" alt="Select a date from the calendar"></a>
       </td>

      <td align="left" nowrap colspan="2">Termination Reason:&nbsp;
        <input type="text" name="terminationReason" maxlength="50" size="50" tabindex="4" value="<%= terminationReason %>">
      </td>
    </tr>
    <tr>
      <td align="left" nowrap colspan="2">Pending Class Change Ind
        <input disabled tabindex="5" type="checkbox" name="pendingClassChangeInd" <%=pendingClassChangeIndStatus %> >
      </td>

      <td valign="top" align="left" nowrap rowspan="3" colspan="2">
        <b>Address Types:&nbsp;</b>
        <br>
        <br>
        <span style="border-style:solid; border-width:1; position:relative; width:75%; height:35% top:0; left:0; z-index:0; overflow:visible">
          <table width="100%" height="100%" border="0" cellspacing="1" cellpadding="1">
            <tr>
              <td align="right" nowrap>Disbursement:&nbsp;</td>
              <td align="left" nowrap>
                <select name="disbAddressType" tabindex="6">
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
                <select name="corrAddressType" tabindex="7">
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
      <td align="left" nowrap>Class:&nbsp;
        <select id="type" name="classType">
	      <option value="Please Select">Please Select</option>
	        <%
                for(int i = 0; i < classTypes.length; i++)
                {
                    String codeTablePK = classTypes[i].getCodeTablePK() + "";
                    String codeDesc    = classTypes[i].getCodeDesc();
                    String code        = classTypes[i].getCode();

                    if (classType.equalsIgnoreCase(code))
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
<!--       <td align="left" nowrap>Rated Gender:&nbsp;
        <select id="rtdGender" name="ratedGender">
	      <option value="Please Select">Please Select</option>
	        <%
                if (roleType.equalsIgnoreCase("TermInsured"))
                {
                    for (int i = 0; i < ratedGenderValues.length; i++)
                    {
                        String value = ratedGenderValues[i].getValue();

                        if (ratedGender.equalsIgnoreCase(value))
                        {
                            out.println("<option selected name=\"id\" value=\"" + value + "\">" +value + "</option>");
                        }
                        else
                        {
                            out.println("<option name=\"id\" value=\"" + value + "\">" + value + "</option>");
                        }
                    }
                }
                else
                {
                    for(int i = 0; i < ratedGenders.length; i++)
                    {
                        String codeTablePK = ratedGenders[i].getCodeTablePK() + "";
                        String codeDesc    = ratedGenders[i].getCodeDesc();
                        String code        = ratedGenders[i].getCode();

                        if (ratedGender.equalsIgnoreCase(code))
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
      </td>-->
      </tr>
      <tr>
      <td align="left" colspan="2" nowrap>Underwriting Class:&nbsp;
        <select id="uwClass" name="underwritingClass">
	      <option value="Please Select">Please Select</option>
	        <%
                if (roleType.equalsIgnoreCase("TermInsured"))
                {
                    for (int i = 0; i < underwritingClassValues.length; i++)
                    {
                        String value = underwritingClassValues[i].getValue();

                        if (underwritingClass.equalsIgnoreCase(value))
                        {
                            out.println("<option selected name=\"id\" value=\"" + value + "\">" +value + "</option>");
                        }
                        else
                        {
                            out.println("<option name=\"id\" value=\"" + value + "\">" + value + "</option>");
                        }
                    }
                }
                else
                {
                    for(int i = 0; i < underwritingClasses.length; i++)
                    {
                        String codeTablePK = underwritingClasses[i].getCodeTablePK() + "";
                        String codeDesc    = underwritingClasses[i].getCodeDesc();
                        String code        = underwritingClasses[i].getCode();

                        if (underwritingClass.equalsIgnoreCase(code))
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
      </td>    

    </tr>
    <tr>
      <td align="left" nowrap colspan="2">Relationship to Employee:&nbsp;
        <select name="relationToEmp" tabindex="9">
	        <option value="Please Select">Please Select</option>
	        <%
                for(int i = 0; i < relToEmp.length; i++) {

                    String codeTablePK = relToEmp[i].getCodeTablePK() + "";
                    String codeDesc    = relToEmp[i].getCodeDesc();
                    String code        = relToEmp[i].getCode();

                    if (relationToEmp.equalsIgnoreCase(code)) {

                        out.println("<option selected name=\"id\" value=\"" + codeTablePK+ "\">" + codeDesc + "</option>");
                    }
                    else  {

                        out.println("<option name=\"id\" value=\"" + codeTablePK + "\">" + codeDesc + "</option>");
                    }
                }
	       %>

        </select>
      </td>
      <td align="left" nowrap>Authorized Signature:&nbsp;
        <select name="authorizedSignatureCT" tabindex="8">
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
      <td align="left" nowrap colspan="4">&nbsp;</td>
    </tr>
    <tr>
     <td align="left" nowrap>
        <a href ="javascript:showClassGenderRatingsDialog()">Class Ratings</a>
      </td>
      <td align="left" nowrap>
        <a href ="javascript:showPreferences()">Preference</a>
      </td>
      <td align="left" nowrap>
        <a href ="javascript:showQuestionnaireResponse()">Questionnaire Responses</a>
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
	  <td colspan="4" nowrap>&nbsp;</td>
	</tr>
  </table>
  <table width="100%" height="2%" border="0" cellspacing="0" cellpadding="0">
	<tr height="5%">
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
    <tr width="100%" height="99%">
      <td colspan="4">
        <span class="scrollableContent">
          <table class="summary" id="clientSummary" width="100%" border="0" cellspacing="0" cellpadding="0">
            <%
              int rowId   = 0;
              int columnId = 0;

              String rowToMatch = "";
              String trClass = "";
              String selected = "";

              String name = null;
              String clientRole = "";
              String clientOptionId = "";
              String clientTaxId = "";
              String sClientRoleFK = "";
              String sContractClientPK = "";
              String sRiderNumber = "";
              String sTerminationDate = "";

              Map sortedClientBeans = sortClientsByRole(contractClients);

              Iterator it = sortedClientBeans.values().iterator();

              while (it.hasNext())  {

                  PageBean client = (PageBean) it.next();

                  String overrideStatus = client.getValue("contractClientOverrideStatus");
                  EDITDate terminationDate = new EDITDate(client.getValue("terminationDate"));
                  EDITDate currentDate = new EDITDate();                  

                  // This will show all active or terminated clients, but not those that have been deleted
                  if (overrideStatus.equalsIgnoreCase("P") || (overrideStatus.equalsIgnoreCase("D") && terminationDate.beforeOREqual(currentDate.addYears(2)))) {

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
                      sTerminationDate = client.getValue("terminationDate");
                      sRiderNumber = client.getValue("riderNumber");

                      rowToMatch = clientTaxId + clientRole + clientOptionId + sClientRoleFK + sContractClientPK;

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
                <%= clientOptionId %>
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
 <input type="hidden" name="segmentFK" value="<%= segmentFK %>">
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
 <input type="hidden" name="contractClientAllocationPK" value="<%= contractClientAllocationPK %>">
 <input type="hidden" name="clientRoleFK" value="<%= clientRoleFK %>">
 <input type="hidden" name="usCitizenIndStatus" value="<%=usCitizenIndStatus %>">
 <input type="hidden" name="contractClientOverrideStatus" value="<%= contractClientOverrideStatus %>">
 <input type="hidden" name="effectiveDate" value="<%= effectiveDate %>">
 <input type="hidden" name="pendingClassChangeIndStatus" value="<%= pendingClassChangeIndStatus %>">
 <input type="hidden" name="classType" value="<%= classType %>">
 <input type="hidden" name="underwritingClass" value="<%= underwritingClass %>">
 <input type="hidden" name="ratedGender" value="<%= ratedGender %>">
 <input type="hidden" name="operator" value="<%= operator %>">
 <input type="hidden" name="maintDateTime" value="<%= maintDateTime %>">
 <input type="hidden" name="riderNumber" value="<%= riderNumber %>">
 <input type="hidden" name="originalClassCT" value="<%= originalClassCT %>">

 <!-- recordPRASEEvents is set by the toolbar -->
 <input type="hidden" name="recordPRASEEvents" value="false">
</form>

</body>
</html>