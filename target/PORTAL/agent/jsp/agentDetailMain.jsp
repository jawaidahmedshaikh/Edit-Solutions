<%@ page  errorPage="/common/jsp/error.jsp" autoFlush="true" %>
<%@ page import="java.util.*,
                 edit.common.*,
                 edit.common.vo.*,
                 edit.common.vo.user.*,
                 fission.beans.*,
                 edit.portal.exceptions.*,
                 edit.common.exceptions.VOEditException,
                 edit.portal.exceptions.PortalEditingException,
                 fission.utility.*,
                 edit.portal.common.session.UserSession,
                 engine.Company" %>
<%
    PortalEditingException editingException = (PortalEditingException) session.getAttribute("portalEditingException");
    String editingExceptionExists = "false";
    if (editingException != null){

        editingExceptionExists = "true";
    }

    String agentMessage = (String) request.getAttribute("errorMessage");
    if (agentMessage == null) {

        agentMessage = "";
    }

    String[] companies = (String[]) request.getAttribute("companies");

    CodeTableWrapper codeTableWrapper = CodeTableWrapper.getSingleton();

    CodeTableVO[] agentStatuses = codeTableWrapper.getCodeTableEntries("AGENTSTATUS");
    CodeTableVO[] agentTypes = codeTableWrapper.getCodeTableEntries("AGENTTYPE");
    CodeTableVO[] addressTypes = codeTableWrapper.getCodeTableEntries("ADDRESSTYPE");

    String status = "";
    String department = "";
    String region = "";
    String branch = "";
    String hireMonth = "";
    String hireDay = "";
    String hireYear = "";
    String terminationMonth = "";
    String terminationDay = "";
    String terminationYear = "";
    String agentType = "";
    String withholdingStatus = "unchecked";
    String holdCommStatus = "unchecked";
    String vestingIndStatus = "unchecked";
    String paymentRoutingInfoStatus = "unchecked";
    String taxInfoStatus = "unchecked";
    String notesStatus = "unchecked";
    String agentNumber = "";
    String agentLastName = "";
    String agentFirstName = "";
    String agentMiddleName = "";
    String corporateName = "";
    String disbAddressType = "";
    String corrAddressType = "";
    String taxId = "";
    String operator = "";
    String maintDate = "";
    String agentPK = "0";
    String companyName = "";
    long companyFK = 0;
    AgentVO agentVO = (AgentVO) session.getAttribute("agentVO");
    if (agentVO != null)
    {
        agentPK = agentVO.getAgentPK() + "";
        agentNumber = Util.initString(agentVO.getAgentNumber(), "");
        companyFK = agentVO.getCompanyFK();
        companyName = Company.findByPK(new Long(companyFK)).getCompanyName();
        status = Util.initString(agentVO.getAgentStatusCT(), "");
        department = Util.initString(agentVO.getDepartment(), "");
        region = Util.initString(agentVO.getRegion(), "");
        branch = Util.initString(agentVO.getBranch(), "");
        String hireDate = Util.initString(agentVO.getHireDate(), "");
        String[] hireDateArray = DateTimeUtil.initDate(hireDate);
        hireMonth = hireDateArray[0];
        hireDay = hireDateArray[1];
        hireYear = hireDateArray[2];

        String terminationDate = agentVO.getTerminationDate();
        String[] termDateArray = DateTimeUtil.initDate(terminationDate);
        terminationMonth = termDateArray[0];
        terminationDay = termDateArray[1];
        terminationYear = termDateArray[2];

        agentType = Util.initString(agentVO.getAgentTypeCT(), "");
        String withholding = agentVO.getWithholdingStatus();
        if (withholding.equals("Y")) {

            withholdingStatus = "checked";
        }
        String holdCommissions = agentVO.getHoldCommStatus();
        if (holdCommissions.equals("Y")) {

            holdCommStatus = "checked";
        }
        disbAddressType = Util.initString(agentVO.getDisbursementAddressTypeCT(), "");
        corrAddressType = Util.initString(agentVO.getCorrespondenceAddressTypeCT(), "");
        VestingVO[] vestingVOs = agentVO.getVestingVO();
        if (vestingVOs != null && vestingVOs.length > 0) {

            vestingIndStatus = "checked";
        }

        ClientDetailVO clientDetailVO = (ClientDetailVO) session.getAttribute("clientDetailVO");
        if (clientDetailVO != null) {

            agentLastName = Util.initString(clientDetailVO.getLastName(), "");
            agentFirstName = Util.initString(clientDetailVO.getFirstName(), "");
            agentMiddleName = Util.initString(clientDetailVO.getMiddleName(), "");
            corporateName = Util.initString(clientDetailVO.getCorporateName(), "");
            taxId = Util.initString(clientDetailVO.getTaxIdentification(), "");

            PreferenceVO[] preferenceVOs = clientDetailVO.getPreferenceVO();
            if (preferenceVOs != null)
            {
                for (int p = 0; p < preferenceVOs.length; p++) {

                    String overrideStatus = preferenceVOs[p].getOverrideStatus();
                    if (overrideStatus.equalsIgnoreCase("P")) {

                        paymentRoutingInfoStatus = "checked";
                        break;
                    }
                }
            }

            TaxInformationVO[] taxInformationVO = clientDetailVO.getTaxInformationVO();
            if (taxInformationVO != null && taxInformationVO.length > 0) {

                TaxProfileVO[] taxProfileVOs = taxInformationVO[0].getTaxProfileVO();
                if (taxProfileVOs != null && taxProfileVOs.length > 0) {

                    for (int t = 0; t < taxProfileVOs.length; t++) {

                        String overrideStatus = taxProfileVOs[t].getOverrideStatus();
                        if (overrideStatus.equalsIgnoreCase("P")) {

                            String taxIndicator = taxProfileVOs[t].getTaxIndicatorCT();
                            String fica = taxProfileVOs[t].getFicaIndicator();
                            if ((taxIndicator != null && !taxIndicator.equals("")) ||
                                 fica != null && !fica.equals("") && !fica.equals("N")) {

                                taxInfoStatus = "checked";
                            }
                            break;
                        }
                    }
                }
            }
        }

        AgentNoteVO[] agentNoteVOs = agentVO.getAgentNoteVO();
        if (agentNoteVOs != null)
        {
            for (int i = 0; i < agentNoteVOs.length; i++)
            {
                if (!agentNoteVOs[i].getVoShouldBeDeleted())
                {
                    notesStatus = "checked";
                }
            }
        }

        operator = Util.initString(agentVO.getOperator(), "");
        maintDate = Util.initString(agentVO.getMaintDateTime(), "");
    }
    else {

        ClientDetailVO clientDetailVO = (ClientDetailVO) session.getAttribute("clientDetailVO");
        if (clientDetailVO != null) {

            agentLastName = Util.initString(clientDetailVO.getLastName(), "");
            agentFirstName = Util.initString(clientDetailVO.getFirstName(), "");
            agentMiddleName = Util.initString(clientDetailVO.getMiddleName(), "");
            corporateName = Util.initString(clientDetailVO.getCorporateName(), "");
            taxId = Util.initString(clientDetailVO.getTaxIdentification(), "");

            PreferenceVO[] preferenceVOs = clientDetailVO.getPreferenceVO();
            for (int p = 0; p < preferenceVOs.length; p++) {

                String overrideStatus = preferenceVOs[p].getOverrideStatus();
                if (overrideStatus.equalsIgnoreCase("P")) {

                    paymentRoutingInfoStatus = "checked";
                    break;
                }
            }

            TaxInformationVO[] taxInformationVO = clientDetailVO.getTaxInformationVO();
            if (taxInformationVO != null && taxInformationVO.length > 0) {

                TaxProfileVO[] taxProfileVOs = taxInformationVO[0].getTaxProfileVO();

                if (taxProfileVOs != null && taxProfileVOs.length > 0) {

                    for (int t = 0; t < taxProfileVOs.length; t++) {

                        String overrideStatus = taxProfileVOs[t].getOverrideStatus();
                        if (overrideStatus.equalsIgnoreCase("P")) {

                            String taxIndicator = taxProfileVOs[t].getTaxIndicatorCT();
                            String fica = taxProfileVOs[t].getFicaIndicator();
                            if ((taxIndicator != null && !taxIndicator.equals("")) ||
                                 fica != null && !fica.equals("") && !fica.equals("N")) {

                                taxInfoStatus = "checked";
                            }
                            break;
                        }
                    }
                }
            }
        }
    }

    UserSession userSession = (UserSession) session.getAttribute("userSession");
%>

<html>

<script language="Javascript1.2">

	var f = null;

    var agentMessage = "<%= agentMessage %>";
    var shouldShowLockAlert = true;

    var editingExceptionExists = "<%= editingExceptionExists %>";

	function init() {

		f = document.agentDetailMainForm;

		top.frames["main"].setActiveTab("mainTab");

        var agentIsLocked = <%= userSession.getAgentIsLocked()%>;
        var username = "<%= userSession.getUsername() %>";
        var elementPK = "<%= userSession.getAgentPK() %>";
		top.frames["header"].updateLockState(agentIsLocked, username, elementPK);

        shouldShowLockAlert = !agentIsLocked;

        for (var i = 0; i < f.elements.length; i++) {

            var elementType = f.elements[i].type;

            if ((elementType == "text" ||
                 elementType == "button" ||
                 elementType == "select-one" ||
                 elementType == "checkbox") &&
                (shouldShowLockAlert == true)) {

                f.elements[i].onclick = showLockAlert;
                f.elements[i].onkeydown = showLockAlert;
            }
        }

        if (agentMessage != "") {

            alert(agentMessage);
        }
	}

    function showLockAlert(){

        if (shouldShowLockAlert == true){

            alert("The Agent can not be edited.");

            return false;
        }
    }

	function openDialog(theURL,winName,features,transaction,action) {

      if (f.agentType.selectedIndex == 0 &&
            action != "showCancelAgentConfirmationDialog") {

            alert("Please Select Agent Type");
      }

      else {

            dialog = window.open(theURL,winName,features);

            //alert("The agent Pk is: " + <%= agentPK %>);

            sendTransactionAction(transaction, action, winName);
      }
	}

    function checkForEditingException() {

        if (editingExceptionExists == "true"){

            openDialog("", "exceptionDialog", "resizable=no,width=" + .75 * screen.width + ",height=" + screen.height/3, "AgentDetailTran", "showEditingExceptionDialog", "exceptionDialog");
            // sendTransactionAction("AgentDetailTran", "showEditingExceptionDialog", "exceptionDialog");
        }
    }

    function continueTransactionAction(transaction, action) {

        f.ignoreEditWarnings.value = "true";
        sendTransactionAction(transaction, action, "contentIFrame");
    }

	function sendTransactionAction(transaction, action, target) {

		if (f.withholdingStatus.checked == true) {

			f.withholdingStatus.value = "checked";
		}
        else {

            f.withholdingStatus.value = "unchecked";
        }

		if (f.holdCommStatus.checked == true) {

			f.holdCommStatus.value = "checked";
		}
        else {

            f.holdCommStatus.value = "unchecked";
        }
        if (f.company.selectedIndex == 0 &&
            action != "showCancelAgentConfirmationDialog")
        {
            alert ("Please Select a Company");
        }
        else {

            f.transaction.value=transaction;
            f.action.value=action;

            f.target = target;

            f.submit();
        }
	}

	function showVestingDialog() {

        var width = .30 * screen.width;
        var height = .40 * screen.height;

		openDialog("","vesting","left=0,top=0,resizable=no,width=" + width + ",height=" + height, "AgentDetailTran", "showVestingDialog", "vesting");
	}

	function showPaymentRoutingInfoDialog() {

        var width = .60 * screen.width;
        var height = .85 * screen.height;

		openDialog("","paymentRoutingInfo","left=0,top=0,resizable=no,width=" + width + ",height=" + height, "AgentDetailTran", "showPaymentRoutingInfoDialog", "paymentRoutingInfo");
	}

	function showTaxInfoDialog() {

        var width = .35 * screen.width;
        var height = .25 * screen.height;

		openDialog("","taxInfo","left=0,top=0,resizable=no,width=" + width + ",height=" + height, "AgentDetailTran", "showTaxInfoDialog", "taxInfo");
	}

	function showNotesDialog() {

        var width = .99 * screen.width;
        var height = .90 * screen.height;

		openDialog("","notes","left=0,top=0,resizable=no,width=" + width + ",height=" + height, "AgentDetailTran", "showNotesDialog", "notes");
	}
</script>

<head>
<title>Agent Detail Main</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta http-equiv="Cache-Control" content="no-store">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Expires" content="0">

<link href="/PORTAL/common/css/PORTAL.css" rel="stylesheet" type="text/css">
</head>

<body class="mainTheme" onLoad="init(); checkForEditingException();" style="border-style:solid; border-width:1;">
<form name= "agentDetailMainForm" method="post" action="/PORTAL/servlet/RequestManager">

<input type="hidden" name="page" value="">
<jsp:include page="agentInfoHeader.jsp" flush="true"/>

<span id="mainContent" style="border-style:solid; border-width:1;  position:relative; width:100%; height:100%; top:0; left:0; z-index:0; overflow:visible">
  <table width="100%" height="100%" border="0">
    <tr>
      <td align="left" nowrap>Company:&nbsp;
          <select name="company">
           <option>Please Select</option>
            <%
                if (companies != null)
                {
                  for(int i = 0; i < companies.length; i++)
                  {
                      String company = companies[i];

                      if (companyName.equals(company)) {

                         out.println("<option selected name=\"id\" value=\"" + company + "\">" + company + "</option>");
                      }
                      else  {

                         out.println("<option name=\"id\" value=\"" + company + "\">" + company + "</option>");
                      }
                  }
                }
            %>
          </select>
      </td>
      <td align="left" nowrap>Agent #:&nbsp;
        <input type="text" name="agentNumber" maxlength="11" size="11" value="<%= agentNumber %>">
      </td>
      <td align="left" nowrap colspan="2">Tax ID:&nbsp;
        <input disabled type="text" name="taxId" maxlength="11" size="11" value="<%= taxId %>">
      </td>
    </tr>
    <tr>
      <td align="left" nowrap>Last Name:&nbsp;
        <input disabled type="text" name="agentLastName" maxlength="30" size="30" value="<%= agentLastName %>">
      </td>
      <td align="left" nowrap>First Name:&nbsp;
        <input disabled type="text" name="agentFirstName" maxlength="15" size="15" value="<%= agentFirstName %>">
      </td>
      <td align="left" nowrap>Middle Name:&nbsp;
        <input disabled type="text" name="agentMiddleName" maxlength="15" size="15" value="<%= agentMiddleName %>">
      </td>
    </tr>
    <tr>
      <td align="left" nowrap colspan="3">Corporate Name:&nbsp;
        <input disabled type="text" name="corporateName" maxlength="60" size="60" value="<%= corporateName %>">
      </td>
    </tr>
    <tr>
      <td align="left" nowrap>Status:&nbsp;
        <select name="status">
          <option> Please Select </option>
          <%
              for(int i = 0; i < agentStatuses.length; i++) {

                  String codeTablePK = agentStatuses[i].getCodeTablePK() + "";
                  String codeDesc    = agentStatuses[i].getCodeDesc();
                  String code        = agentStatuses[i].getCode();

                 if (status.equalsIgnoreCase(code)) {

                     out.println("<option selected name=\"id\" value=\"" + codeTablePK + "\">" + codeDesc + "</option>");
                 }
                 else  {

                     out.println("<option name=\"id\" value=\"" + codeTablePK + "\">" + codeDesc + "</option>");
                 }
              }
          %>
        </select>
      </td>
      <td align="left" nowrap colspan="2">Agent Type:&nbsp;
        <select name="agentType">
          <option> Please Select </option>
          <%
              for(int i = 0; i < agentTypes.length; i++) {

                  String codeTablePK = agentTypes[i].getCodeTablePK() + "";
                  String codeDesc    = agentTypes[i].getCodeDesc();
                  String code        = agentTypes[i].getCode();

                 if (agentType.equalsIgnoreCase(code)) {

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
      <td align="left" nowrap>Hire Date:&nbsp;
        <input type="text" name="hireMonth" maxlength="2" size="2" value="<%= hireMonth %>">
        /
        <input type="text" name="hireDay" maxlength="2" size="2" value="<%= hireDay %>">
        /
        <input type="text" name="hireYear" maxlength="4" size="4" value="<%= hireYear %>">
      </td>
      <td align="left" nowrap colspan="2">Termination Date:&nbsp;
        <input type="text" name="terminationMonth" maxlength="2" size="2" value="<%= terminationMonth %>">
        /
        <input type="text" name="terminationDay" maxlength="2" size="2" value="<%= terminationDay %>">
        /
        <input type="text" name="terminationYear" maxlength="4" size="4" value="<%= terminationYear %>">
      </td>
    </tr>
    <tr>
      <td align="left" nowrap>Department:&nbsp;
        <input type="text" name="department" maxlength="20" size="20" value="<%= department %>">
      </td>
      <td align="left" nowrap>Region:&nbsp;
        <input type="text" name="region" maxlength="20" size="20" value="<%= region %>">
      </td>
      <td align="left" nowrap>Branch:&nbsp;
        <input type="text" name="branch" maxlength="20" size="20" value="<%= branch %>">
      </td>
    </tr>
    <tr>
      <td align="left" nowrap>
        Withhold
        <input type="checkbox" name="withholdingStatus" <%= withholdingStatus %> >
      </td>
      <td valign="top" align="left" nowrap rowspan="3" colspan="2">
        <span style="position:relative; width:65%; height:35% top:0; left:0; z-index:0; overflow:visible">
          <fieldset style="border-style:solid; border-width:1px; border-color:gray">
          <legend><font color="black">Address Types</font></legend>
          <table width="100%" height="100%" border="0" cellspacing="0" cellpadding="5">
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
          </fieldset>
        </span>
      </td>
    </tr>
    <tr>
      <td align="left" nowrap>
        Hold Commissions
        <input type="checkbox" name="holdCommStatus" <%= holdCommStatus %> >
      </td>
    </tr>
    <tr>
      <td align="left" colspan="3" nowrap>
        <input disabled type="checkbox" name="vestingIndStatus" <%= vestingIndStatus%> >
        <a href ="javascript:showVestingDialog()">Vesting</a>
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        <input disabled type="checkbox" name="paymentRoutingInfoStatus" <%= paymentRoutingInfoStatus%> >
        <a href ="javascript:showPaymentRoutingInfoDialog()">Payment/Routing Info</a>
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        <input disabled type="checkbox" name="taxInfoStatus" <%= taxInfoStatus%> >
        <a href ="javascript:showTaxInfoDialog()">Tax Info</a>
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        <input disabled type="checkbox" name="notesStatus" <%= notesStatus%> >
        <a href ="javascript:showNotesDialog()">Notes</a>
      </td>
    </tr>
    <tr>
      <td align="left" nowrap>Operator:&nbsp;
        <input disabled type="text" name="operator" maxlength="15" size="15" value="<%= operator %>">
      </td>
      <td align="left" nowrap colspan="2">Maint Date/Time:&nbsp;
        <input disabled type="text" name="maintDate" maxlength="35" size="35" value="<%= maintDate %>">
      </td>
    </tr>
  </table>
</span>

<!-- ****** HIDDEN FIELDS ***** //-->
 <input type="hidden" name="transaction"  value="">
 <input type="hidden" name="action"       value="">
 <input type="hidden" name="agentPK" value="<%= agentPK %>">
 <input type="hidden" name="agentId" value="<%= agentNumber %>">
 <input type="hidden" name="ignoreEditWarnings" value="">

</body>
</html>