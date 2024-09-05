<!-- ****** JAVA CODE ***** //-->

<%@ page  errorPage="/common/jsp/error.jsp" autoFlush="true" %>
<%@ page import="java.util.*,
                 edit.common.*,
                 edit.common.vo.*,
                 edit.common.vo.user.*,
                 fission.beans.*,
                 edit.common.exceptions.VOEditException,
                 edit.portal.exceptions.PortalEditingException,
                 fission.utility.Util,
                 edit.portal.common.session.UserSession" %>

<jsp:useBean id="pageBean"
    class="fission.beans.PageBean" scope="request"/>

<jsp:useBean id="preferenceSessionBean"
    class="fission.beans.SessionBean" scope="session"/>

<jsp:useBean id="taxInformationSessionBean"
    class="fission.beans.SessionBean" scope="session"/>

<jsp:useBean id="roleSessionBean"
    class="fission.beans.SessionBean" scope="session"/>

<%

    PortalEditingException editingException = (PortalEditingException) session.getAttribute("portalEditingException");
    String editingExceptionExists = "false";
    if (editingException != null){

        editingExceptionExists = "true";
    }

    VOEditException voEditException = (VOEditException) session.getAttribute("VOEditException");
    String voEditExceptionExists = "false";
    if (voEditException != null){

        voEditExceptionExists = "true";
    }

    String clientMessage  = (String) request.getAttribute("clientMessage");
    if (clientMessage == null) {

        clientMessage = "";
    }

    String errorMessage = (String) request.getAttribute("errorMessage");
    if (errorMessage == null) {

        errorMessage = "";
    }

    String roleStatus     = pageBean.getValue("roleStatus");
    String rolePK         = pageBean.getValue("rolePK");
    String roleType       = pageBean.getValue("roleType");
    String clientDetailPK = pageBean.getValue("clientDetailFK");
    String clientId       = pageBean.getValue("clientId");
    String roles          = pageBean.getValue("roles");
    String[] rolesST      = Util.fastTokenizer(roles, ",");
    String origPreferenceFK = pageBean.getValue("origPreferenceFK");
    String origTaxProfileFK = pageBean.getValue("origTaxProfileFK");

    String preferenceFK   = pageBean.getValue("preferenceFK");
    if (preferenceFK == null) {

        preferenceFK = "";
    }

    String taxProfileFK   = pageBean.getValue("taxProfileFK");
    if (taxProfileFK == null) {

        taxProfileFK = "";
    }

    Map preferenceBeansHT = preferenceSessionBean.getPageBeans();
    Map taxProfileBeansHT = taxInformationSessionBean.getPageBeans();

    CodeTableWrapper codeTableWrapper = CodeTableWrapper.getSingleton();

    CodeTableVO[] lifeRoles = codeTableWrapper.getCodeTableEntries("LIFERELATIONTYPE");
    CodeTableVO[] agentRoles = codeTableWrapper.getCodeTableEntries("AGENTRELATIONTYPE");

    String rowToMatchBase = "";

    if (rolesST.length == 1) {

        rowToMatchBase = rolesST[0] + rolePK + preferenceFK + taxProfileFK;
    }

    UserSession userSession = (UserSession) session.getAttribute("userSession");
%>

<%!
    private TreeMap sortRoles(SessionBean roles) {

		Map roleBeans = roles.getPageBeans();

		TreeMap sortedRoles = new TreeMap();

		Iterator enumer  = roleBeans.values().iterator();

		while (enumer.hasNext()) {

			PageBean roleBean = (PageBean) enumer.next();

			String rolePK     = roleBean.getValue("rolePK");
            String roleType   = roleBean.getValue("roleType");
            String preferenceFK = roleBean.getValue("preferenceFK");

            sortedRoles.put(roleType + rolePK + preferenceFK, roleBean);
		}

		return sortedRoles;
	}
%>

<%!
    private TreeMap sortPreferences(SessionBean preferences) {

		Map preferenceBeans = preferences.getPageBeans();

		TreeMap sortedPreferences = new TreeMap();

		Iterator enumer  = preferenceBeans.values().iterator();

		while (enumer.hasNext()) {

			PageBean preferenceBean = (PageBean) enumer.next();

			String disbursementSource = preferenceBean.getValue("disbursementSource");
            String paymentMode        = preferenceBean.getValue("paymentMode");
            String minimumCheck       = preferenceBean.getValue("minimumCheck");
            String preferencePK       = preferenceBean.getValue("preferencePK");

            sortedPreferences.put(preferencePK + paymentMode +
                                   disbursementSource + minimumCheck, preferenceBean);
		}

		return sortedPreferences;
	}
%>

<%!
    private TreeMap sortTaxProfiles(SessionBean taxProfiles) {

		Map taxProfileBeans = taxProfiles.getPageBeans();

		TreeMap sortedTaxProfiles = new TreeMap();

		Iterator enumer  = taxProfileBeans.values().iterator();

		while (enumer.hasNext()) {

			PageBean taxProfileBean = (PageBean) enumer.next();

            String taxFilingStatus    = taxProfileBean.getValue("filingStatusId");
            String exemptions         = taxProfileBean.getValue("exemptions");
            String taxProfilePK       = taxProfileBean.getValue("taxProfilePK");
            String taxIndicator       = taxProfileBean.getValue("taxIndicator");
            String ficaIndicator      = taxProfileBean.getValue("ficaIndicator");

            sortedTaxProfiles.put(taxProfilePK + taxFilingStatus +
                                   exemptions + taxIndicator + ficaIndicator, taxProfileBean);
		}

		return sortedTaxProfiles;
	}
%>

<html>

<script language="JavaScript1.2">

<!--******* JAVASCRIPT ******//-->

var f = null;

var shouldShowLockAlert = true;

var errorMessage = "<%= errorMessage %>";
var clientMessage = "<%= clientMessage %>";
var tabLayers = new Array();
var isDragging = false;
var voEditExceptionExists = "<%= voEditExceptionExists %>";
var editingExceptionExists = "<%= editingExceptionExists %>";
var currentRolePK = "<%= rolePK %>";
var roleStatus = "<%= roleStatus %>";

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

function highlightRoleRow() {

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

function unhighlightRoleRow() {

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

	f = document.roleForm;

    f.clientId.focus();

    f.clientId.onkeydown = getClientInfo;

    if (clientMessage != "" &&
        clientMessage != null) {

        alert(clientMessage);
    }

    if (errorMessage != "" &&
        errorMessage != null) {

        alert("<%= errorMessage %>");
    }

    var clientIsLocked = <%= userSession.getAgentIsLocked()%>;
    var username = "<%= userSession.getUsername() %>";
    var elementPK = "<%= userSession.getAgentPK() %>";
    top.frames["header"].updateLockState(clientIsLocked, username, elementPK);

    shouldShowLockAlert = !clientIsLocked;

    if (shouldShowLockAlert == true) {

        f.clientId.disabled = false;
    }

    else {

        f.clientId.disabled = true;
    }

    for (var i = 0; i < f.elements.length; i++) {

        var elementType = f.elements[i].type;

        if (elementType == "button" &&
            shouldShowLockAlert == true) {

            f.elements[i].onclick = showLockAlert;
        }
    }
}

function getClientInfo() {

    // If TAB key
    if (window.event.keyCode == 9) {

        sendTransactionAction("RoleTran", "showRolesForSelectedClient", "contentIFrame");
    }
}

function showLockAlert(){

    if (shouldShowLockAlert == true) {

        alert("The Role can not be edited.");

        return false;
    }
}

function selectRoleRow() {

    var tdElement = window.event.srcElement;
    var currentRow = tdElement.parentElement;
    var className = currentRow.className;

    if (roleStatus == "new") {

        if (currentRow.isSelected == "false") {

            currentRow.style.backgroundColor = "#FFFFCC";
            currentRow.isSelected = "true";
        }
        else {

            if (className == "mainEntry") {

                currentRow.style.backgroundColor = "#BBBBBB";
            }

            currentRow.isSelected = "false";
        }
    }
    else {

        alert("Cannot Modify Role Type.");
    }
}

function openDialog(theURL,winName,features) {

	dialog = window.open(theURL,winName,features);
}

function checkForEditingException(){

    if (editingExceptionExists == "true"){

        openDialog("", "exceptionDialog", "resizable=no,width=" + .75 * screen.width + ",height=" + screen.height/3);
	    sendTransactionAction("RoleTran", "showEditingExceptionDialog", "exceptionDialog");
    }
}

function checkForVOEditException(){

    if (voEditExceptionExists == "true") {

        openDialog("", "voEditExceptionDialog", "resizable=no,width=" + .75 * screen.width + ",height=" + screen.height/3);
	    sendTransactionAction("RoleTran", "showVOEditExceptionDialog", "voEditExceptionDialog");
    }
}

function addNewRole() {

	sendTransactionAction("RoleTran", "showRoles", "contentIFrame");
}

function cancelRole() {

	sendTransactionAction("RoleTran", "showRoles", "contentIFrame");
}

function saveRole() {

    f.selectedRolePK.value = currentRolePK;
    var selectedRoles = getSelectedRoles();
    f.selectedRoles.value = selectedRoles;
	sendTransactionAction("RoleTran", "saveRoleToSummary", "_self");
}

function getSelectedRoles(){

    var table=document.all.roleSummaryTable;

    var selectedRoles = "";

    for (var i = 0; i < table.rows.length; i++) {

        if (table.rows[i].isSelected == "true") {

            selectedRoles += table.rows[i].roleId + ",";
        }
    }

    return selectedRoles;
}

function selectPreferenceInformation() {

    if (shouldShowLockAlert) {

        showLockAlert();
    }

    else {

        var tdElement = window.event.srcElement;
        var trElement = tdElement.parentElement;

        var preferenceFK = trElement.id;

        f.selectedPreferenceFK.value = preferenceFK;
        var selectedRoles = getSelectedRoles();
        f.selectedRoles.value = selectedRoles;
        sendTransactionAction("RoleTran", "selectValueForClientRole", "_self");
    }
}

function selectTaxProfileInformation() {

    if (shouldShowLockAlert) {

        showLockAlert();
    }

    else {

        var tdElement = window.event.srcElement;
        var trElement = tdElement.parentElement;

        var taxProfileFK = trElement.id;

        f.selectedTaxProfileFK.value = taxProfileFK;
        var selectedRoles = getSelectedRoles();
        f.selectedRoles.value = selectedRoles;
        sendTransactionAction("RoleTran", "selectValueForClientRole", "_self");
    }
}

function selectRole() {

	var tdElement = window.event.srcElement;
	var trElement = tdElement.parentElement;

    var rolePK        = trElement.id;
    var bankFK        = trElement.bankId;
    var preferenceFK  = trElement.preferenceId;
    var taxProfileFK  = trElement.taxProfileId;
    var roleType      = trElement.roleType;

	f.selectedRolePK.value        = rolePK;
    f.selectedRoleType.value      = roleType;
    f.selectedBankFK.value        = bankFK;
	f.selectedPreferenceFK.value  = preferenceFK;
    f.selectedTaxProfileFK.value  = taxProfileFK;

	sendTransactionAction("RoleTran", "showRoleDetailSummary", "_self");
}

function sendTransactionAction(transaction, action, target) {

	f.transaction.value=transaction;
	f.action.value=action;

	f.target = target;

	f.submit();
}

</script>
<head>
<title>Client Roles</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta http-equiv="Cache-Control" content="no-store">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Expires" content="0">

<link href="/PORTAL/common/css/PORTAL.css" rel="stylesheet" type="text/css">
</head>

<!-- ****** HTML CODE ***** //-->

<body class="mainTheme" onLoad="init(); checkForEditingException(); checkForVOEditException()" style="border-style:solid; border-width:1">
<form name="roleForm" method="post" action="/PORTAL/servlet/RequestManager">

<span id="mainContent" style="position:relative; width:100%; height:100%; top:0; left:0; z-index:0; overflow:visible">
  <table width="100%" height="80%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      <td align="center" colspan="3" nowrap>Client Id:&nbsp;
        <input type="text" name="clientId" tabindex="1" maxlength="15" size="15" value="<%= clientId %>">
      </td>
    </tr>
    <tr>
      <td align="left" nowrap width="25%" height="98%">
        Roles:
        <br>
        <span class="summaryTable" style="border-style:solid; border-width:1; position:relative; width:99%; height:99%; top:0; left:0; z-index:0; overflow:scroll">
        <table id="roleSummaryTable" width="100%" border="0" cellspacing="0" cellpadding="0">
        <%
            int i = 0;
            for(i = 0; i < lifeRoles.length; i++) {

                String className = "mainEntry";
                String roleSelected = "false";

                String codeTablePK = lifeRoles[i].getCodeTablePK() + "";
                String codeDesc    = lifeRoles[i].getCodeDesc();
                String code        = lifeRoles[i].getCode();

                for (int r = 0; r < rolesST.length; r++) {

                    if (rolesST[r].equalsIgnoreCase(code)) {

                        className = "highLighted";
                        roleSelected = "true";
                        r = rolesST.length;
                    }
                }
        %>
        <tr class="<%= className %>" roleId="<%= codeTablePK %>" onMouseOver="highlightRoleRow()"
            onMouseOut="unhighlightRoleRow()" onClick="selectRoleRow()" isSelected="<%= roleSelected %>">
          <td>
            <%= codeDesc %>
          </td>
        </tr>
        <%
            }

            for(i = 0; i < agentRoles.length; i++) {

                String className = "mainEntry";
                String roleSelected = "false";
                String codeTablePK = agentRoles[i].getCodeTablePK() + "";
                String codeDesc    = agentRoles[i].getCodeDesc();
                String code        = agentRoles[i].getCode();

                for (int r = 0; r < rolesST.length; r++) {

                    if (rolesST[r].equalsIgnoreCase(code)) {

                        className = "highLighted";
                        roleSelected = "true";
                        r = rolesST.length;
                    }
                }
        %>
        <tr class="<%= className %>" roleId="<%= codeTablePK %>" onMouseOver="highlightRoleRow()"
            onMouseOut="unhighlightRoleRow()" onClick="selectRoleRow()" isSelected="<%= roleSelected %>">
          <td>
            <%= codeDesc %>
          </td>
        </tr>
        <%
            }
        %>
        </table>
        </span>
      </td>
      <td height="98%">
        &nbsp;
      </td>
      <td align="left" nowrap width="75%" height="98%">
        <span>
          Preference Information:
          <table class="summaryArea" id="preferenceSummaryTable" width="100%" height="25%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <th align="left" width="14%">Disb Source</th>
              <th align="left" width="14%">Payment Mode</th>
              <th align="left" width="14%">Minimum Check</th>
              <th align="left" width="14%">Print As</th>
              <th align="left" width="14%">Acct Number</th>
              <th align="left" width="14%">Routing Number</th>
              <th align="left" width="14%">Account Type</th>
            </tr>
            <tr width="100%" height="99%">
              <td colspan="4" width="100%" height="100%">
                <span class="scrollableContent">
                <table class="scrollableArea" border="0" width="100%" cellspacing="0">
                <%

                  String sPreferencePK        = "";
                  String preferenceRowToMatch = "";
                  String preferenceSelected   = "";
                  String sDisbursementSource  = "";
                  String sPrintAs             = "";
                  String sPaymentMode         = "";
                  String sMinimumCheck        = "";
                  String bankClass            = "";
                  String bankSelected         = "";
                  String sAccountNumber       = "";
                  String sRoutingNumber       = "";
                  String sAccountType         = "";

                  Map sortedPreferences = sortPreferences(preferenceSessionBean);

                  Iterator preferencesIT = sortedPreferences.values().iterator();

                  while (preferencesIT.hasNext())  {

                      String className = "mainEntry";
                      PageBean preferenceBean = (PageBean)preferencesIT.next();

                      sPreferencePK       = preferenceBean.getValue("preferencePK");
                      sDisbursementSource = preferenceBean.getValue("disbursementSource");
                      sPaymentMode        = preferenceBean.getValue("paymentMode");
                      sMinimumCheck       = preferenceBean.getValue("minimumCheck");
                      sPrintAs            = preferenceBean.getValue("printAs");
                      if (sPrintAs.length() > 20) {

                          sPrintAs = sPrintAs.substring(0, 20);
                      }
                      sAccountNumber = preferenceBean.getValue("bankAccountNumber");
                      sRoutingNumber = preferenceBean.getValue("bankRoutingNumber");
                      sAccountType   = preferenceBean.getValue("bankAccountType");

                      if (sPreferencePK.equals(preferenceFK)) {

                          className = "highLighted";

                          preferenceSelected = "true";
                      }
                      else {

                          className = "dummy";

                          preferenceSelected="false";
                      }
                %>

                <tr class="<%= className %>" isSelected="<%= preferenceSelected %>" id="<%= sPreferencePK %>"
                    onMouseOver="highlightRow()" onMouseOut="unhighlightRow()" onClick="selectPreferenceInformation()">
                  <td width="14%">
                    <%= sDisbursementSource %>
                  </td>
                  <td width="14%">
                    <%= sPaymentMode %>
                  </td>
                  <td width="14%">
                    <%= sMinimumCheck %>
                  </td>
                  <td width="14%">
                    <%= sPrintAs %>
                  </td>
                  <td width="14%">
                    <%= sAccountNumber %>
                  </td>
                  <td width="14%">
                    <%= sRoutingNumber %>
                  </td>
                  <td width="14%">
                    <%= sAccountType %>
                  </td>
                </tr>
                <%
                  }
                %>
                </table>
                </span>
              </td>
            </tr>
          </table>
          <br>
          Tax Profile Information:
          <table class="summaryArea" id="taxProfileSummaryTable" width="100%" height="25%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <th align="left" width="25%">Filing Status</th>
              <th align="left" width="25%">Exemptions</th>
              <th align="left" width="25%">Tax Indicator</th>
              <th align="left" width="25%">FICA Indicator</th>
            </tr>
            <tr width="100%" height="99%">
              <td colspan="4" width="100%" height="100%">
                <span class="scrollableContent">
                <table class="scrollableArea" border="0" width="100%" cellspacing="0">
                <%

                    String sTaxProfilePK      = "";
                    String taxRowToMatch      = "";
                    String taxProfileSelected = "";
                    String sFilingStatus      = "";
                    String sExemptions        = "";
                    String sTaxIndicator      = "";
                    String sFicaIndicator     = "";

                    Map sortedTaxProfiles = sortTaxProfiles(taxInformationSessionBean);

                    Iterator taxProfilesIT = sortedTaxProfiles.values().iterator();

                    while (taxProfilesIT.hasNext())  {

                        String className = "mainEntry";
                        PageBean taxProfileBean = (PageBean)taxProfilesIT.next();

                        sTaxProfilePK  = taxProfileBean.getValue("taxProfilePK");
                        sFilingStatus  = taxProfileBean.getValue("filingStatusId");
                        sExemptions    = taxProfileBean.getValue("exemptions");
                        sTaxIndicator  = taxProfileBean.getValue("taxIndicator");
                        sFicaIndicator = taxProfileBean.getValue("ficaIndicator");

                        if (sTaxProfilePK.equals(taxProfileFK)) {

                            className = "highLighted";

                            taxProfileSelected = "true";
                        }
                        else {

                            className = "dummy";

                            taxProfileSelected="false";
                        }
                %>

                <tr class="<%= className %>" isSelected="<%= taxProfileSelected %>" id="<%= sTaxProfilePK %>"
                    onMouseOver="highlightRow()" onMouseOut="unhighlightRow()" onClick="selectTaxProfileInformation()">
                  <td width="25%">
                    <%= sFilingStatus %>
                  </td>
                  <td width="25%">
                    <%= sExemptions %>
                  </td>
                  <td width="25%">
                    <%= sTaxIndicator %>
                  </td>
                  <td width="25%">
                    <%= sFicaIndicator %>
                  </td>
                </tr>
                <%
                    }
                %>
                </table>
                </span>
              </td>
            </tr>
          </table>
        </span>
      </td>
    </tr>
  </table>
  <br>
  <table width="100%" height="2%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      <td nowrap align="left">
        <input type="button" value="   Add   " style="background-color:#DEDEDE" onClick="addNewRole()">
        <input type="button" value="   Save  " style="background-color:#DEDEDE" onClick="saveRole()">
        <input type="button" value="  Cancel " style="background-color:#DEDEDE" onClick="cancelRole()">
      </td>
    </tr>
  </table>
  <table class="summaryArea" id="roleTable" width="100%" height="18%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      <th align="left" width="25%">Role Type</th>
      <th align="left" width="25%">Acct Number</th>
      <th align="left" width="25%">Disb Source</th>
      <th align="left" width="25%">Filing Status</th>
    </tr>
    <tr width="100%" height="99%">
      <td colspan="4">
        <span class="scrollableContent">
          <table class="scrollableArea" id="roleSummary" width="100%" border="0" cellspacing="0" cellpadding="0">
            <%

              Map sortedRoles = sortRoles(roleSessionBean);

              Iterator rolesIT = sortedRoles.values().iterator();

              while (rolesIT.hasNext())  {

                  String rRolePK = "";
                  String rBankFK = "";
                  String rPreferenceFK = "";
                  String rTaxProfileFK = "";
                  String rowToMatch = "";
                  String rowSelected = "";
                  String rRoleType = "";
                  String rAccountNumber = "";
                  String rDisbursementSource = "";
                  String rFilingStatus = "";

                  String className = "mainEntry";
                  PageBean roleBean = (PageBean)rolesIT.next();

                  rRolePK             = roleBean.getValue("rolePK");
                  rBankFK             = roleBean.getValue("bankAccountInformationFK");
                  if (rBankFK == null) {

                      rBankFK = "";
                  }
                  rPreferenceFK       = roleBean.getValue("preferenceFK");
                  if (rPreferenceFK == null) {

                      rPreferenceFK = "";
                  }
                  rTaxProfileFK       = roleBean.getValue("taxProfileFK");
                  if (rTaxProfileFK == null) {

                      rTaxProfileFK = "";
                  }
                  rRoleType           = roleBean.getValue("roleType");

                  Iterator preferenceEnum = preferenceBeansHT.values().iterator();
                  while (preferenceEnum.hasNext()) {

                      PageBean pb = (PageBean) preferenceEnum.next();

                      String preferencePBPK = pb.getValue("preferencePK");
                      if (preferencePBPK.equals(rPreferenceFK)) {

                          rDisbursementSource = pb.getValue("disbursementSource");
                      }
                  }

                  Iterator taxProfileEnum = taxProfileBeansHT.values().iterator();
                  while (taxProfileEnum.hasNext()) {

                      PageBean pb = (PageBean) taxProfileEnum.next();

                      String taxProfilePBPK = pb.getValue("taxProfilePK");
                      if (taxProfilePBPK.equals(rTaxProfileFK)) {

                          rFilingStatus = pb.getValue("filingStatusId");
                      }
                  }

                  rowToMatch = rRoleType + rRolePK + rBankFK + rPreferenceFK + rTaxProfileFK;

                  if (rowToMatch.equals(rowToMatchBase)) {

                      className = "highLighted";

                      rowSelected = "true";
                  }
                  else {

                      className = "mainEntry";

                      rowSelected="false";
                  }
            %>

            <tr class="<%= className %>" isSelected="<%= rowSelected %>" id="<%= rRolePK %>"
                bankId="<%= rBankFK %>" preferenceId="<%= rPreferenceFK %>"
                taxProfileId="<%= rTaxProfileFK %>" roleType="<%= rRoleType %>"
                onMouseOver="highlightRow()" onMouseOut="unhighlightRow()" onClick="selectRole()">
              <td width="25%">
                <%= rRoleType %>
              </td>
              <td width="25%">
                <%= rAccountNumber %>
              </td>
              <td width="25%">
                <%= rDisbursementSource %>
              </td>
              <td width="25%">
                <%= rFilingStatus %>
              </td>
            </tr>
            <%
              }
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
    <input type="hidden" name="roleStatus"  value="<%= roleStatus %>">
    <input type="hidden" name="clientDetailPK" value="<%= clientDetailPK %>">
    <input type="hidden" name="rolePK" value="<%= rolePK %>">
    <input type="hidden" name="selectedRolePK" value="<%= rolePK %>">
    <input type="hidden" name="selectedRoleType" value="<%= roleType %>">
    <input type="hidden" name="selectedRoles" value="">
    <input type="hidden" name="origPreferenceFK" value="<%= origPreferenceFK %>">
    <inpur type="hidden" name="origTaxProfileFK" value="<%= origTaxProfileFK %>">
    <input type="hidden" name="selectedPreferenceFK" value="<%= preferenceFK %>">
    <input type="hidden" name="selectedTaxProfileFK" value="<%= taxProfileFK %>">

</body>
</form>
</html>
