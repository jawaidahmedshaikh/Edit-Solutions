<!-- ****** JAVA CODE ***** //-->

<%@ page  errorPage="/common/jsp/error.jsp" autoFlush="true" %>
<%@ page import="java.util.*,
                 edit.common.*,
                 edit.common.vo.*,
                 edit.common.vo.user.*,
                 fission.beans.*,
                 edit.portal.common.session.UserSession,
                 fission.utility.Util" %>

<jsp:useBean id="pageBean"
    class="fission.beans.PageBean" scope="request"/>

<jsp:useBean id="preferenceSessionBean"
    class="fission.beans.SessionBean" scope="session"/>

<%
    String preferenceMessage  = (String) request.getAttribute("preferenceMessage");
    if (preferenceMessage == null) {

        preferenceMessage = "";
    }

    String selectedClientRoleFK = (String) request.getAttribute("selectedClientRoleFK");
    String returnPage = (String) request.getAttribute("returnPage");
    
    CodeTableWrapper codeTableWrapper = CodeTableWrapper.getSingleton();

    CodeTableVO[] disbursementSources  = codeTableWrapper.getCodeTableEntries("DISBURSESOURCE");
    CodeTableVO[] paymentModes = codeTableWrapper.getCodeTableEntries("PAYMENTMODE");
    CodeTableVO[] bankAccountTypes  = codeTableWrapper.getCodeTableEntries("BANKACCTTYPE");
    CodeTableVO[] stateCodes = codeTableWrapper.getCodeTableEntries("STATE");
    String[] overrideStatuses = new String[] {"O", "P"};
    CodeTableVO[] preferenceTypes = codeTableWrapper.getCodeTableEntries("PREFERENCETYPE");

    String clientDetailPK     = pageBean.getValue("clientDetailFK");
	String disbursementSource = pageBean.getValue("disbursementSource");
    String preferencePK       = pageBean.getValue("preferencePK");
	String printAs            = pageBean.getValue("printAs");
	String printAs2           = pageBean.getValue("printAs2");
    String paymentMode        = pageBean.getValue("paymentMode");
    String minimumCheck       = pageBean.getValue("minimumCheck");
    String overrideStatus     = pageBean.getValue("overrideStatus");
    String preferenceType     = pageBean.getValue("preferenceType");

	String bankRoutingNumber    = pageBean.getValue("bankRoutingNumber");
	String bankAccountNumber    = pageBean.getValue("bankAccountNumber");
    String bankAccountType      = pageBean.getValue("bankAccountType");
    String bankName = pageBean.getValue("bankName");
    String bankAddressLine1 = pageBean.getValue("bankAddressLine1");
    String bankAddressLine2 = pageBean.getValue("bankAddressLine2");
    String bankCity = pageBean.getValue("bankCity");
    String bankState = pageBean.getValue("bankState");
    String bankZipCode = pageBean.getValue("bankZipCode");

    UserSession userSession = (UserSession) session.getAttribute("userSession");
%>

<%!
    private TreeMap sortPreferences(SessionBean preferenceSessionBean)
    {
		Map preferenceBeans = preferenceSessionBean.getPageBeans();

		TreeMap sortedPreferences = new TreeMap();

		Iterator it  = preferenceBeans.values().iterator();

		while (it.hasNext())
        {
			PageBean preferenceBean = (PageBean) it.next();

			String preferencePK = preferenceBean.getValue("preferencePK");
            sortedPreferences.put(preferencePK, preferenceBean);
		}

		return sortedPreferences;
	}
%>

<html>

<script src="/PORTAL/common/javascript/commonJavascriptFunctions.js"></script>

<script language="JavaScript1.2">

<!--******* JAVASCRIPT ******//-->

var f = null;

var shouldShowLockAlert = true;
var editableContractStatus = true;

var preferenceMessage = "<%= preferenceMessage %>";
var tabLayers = new Array();
var isDragging = false;

var preferencePK = "<%= preferencePK %>";

function init()
{
	f = document.preferenceForm;

    if (preferenceMessage != "")
    {
        alert(preferenceMessage);
    }

    var segmentIsLocked = <%= userSession.getSegmentIsLocked()%>;
    var username = "<%= userSession.getUsername() %>";

    shouldShowLockAlert = !segmentIsLocked;

    // check for terminated status to disallow contract updates
    editableContractStatus = <%= userSession.getEditableSegmentStatus()%>;
    
    for (var i = 0; i < f.elements.length; i++) {

        var elementType = f.elements[i].type;

        if ( (elementType == "text" || elementType == "select-one") &&
             (shouldShowLockAlert == true || editableContractStatus == false) ) {
            f.elements[i].onclick = showLockAlert;
            f.elements[i].onkeydown = showLockAlert;
        }
    }

    formatCurrency();
    if (preferencePK != "")
    {
        f.overrideStatus.disabled = true;
        f.paymentMode.disabled = true;
        f.minimumCheck.disabled = true;
        f.disbursementSource.disabled = true;
        f.preferenceType.disabled = true;
        f.bankAccountType.disabled = true;
        f.bankRoutingNumber.disabled = true;
        f.bankAccountNumber.disabled = true;
        f.bankName.disabled = true;
        f.bankAddressLine1.disabled = true;
        f.bankAddressLine2.disabled = true;
        f.bankCity.disabled = true;
        f.bankState.disabled = true;
        f.bankZipCode.disabled = true;
        f.printAs.disabled = true;
        f.printAs2.disabled = true;
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

function showContractAgentInformation()
{
    if (preferencePK != "")
    {
        var width = 0.80 * screen.width;
        var height = 0.30 * screen.height;

        openDialog("contractAgentInfo", "top=0,left=0,resizable=no", width, height);
        sendTransactionAction("ContractDetailTran", "showContractAgentInfo", "contractAgentInfo");
    }
    else
    {
        alert ("Please Select Preference For Contract/Agent Information");
    }
}

function savePreferenceInformation()
{
    if (shouldShowLockAlert == true)
    {
        alert("The Agent not be edited.");

        return;
    }
    else if (preferencePK != "")
    {
        alert("Please Edit Preference Values At The Client Level");
    }
    else if (f.overrideStatus.value == "")
    {
        alert("Please Select Override Status");
    }
    else
    {
        sendTransactionAction("ContractDetailTran", "savePreference", "_self");
    }
}

function selectPreferenceForClient()
{
    sendTransactionAction("ContractDetailTran", "selectPreferenceForClient", "contentIFrame");
    window.close();
}

function addPreference()
{
    sendTransactionAction("ContractDetailTran", "clearPreferenceForAdd", "_self");
}

function closeAndShowAgentFinancial()
{
    window.close();
}

function selectRow()
{
    var tdElement = window.event.srcElement;
    var trElement = tdElement.parentElement;

    f.preferencePK.value = trElement.id;
	sendTransactionAction("ContractDetailTran", "showSelectedPreference", "_self");
}


</script>
<head>
<title>Client Preferences</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta http-equiv="Cache-Control" content="no-store">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Expires" content="0">

<link href="/PORTAL/common/css/PORTAL.css" rel="stylesheet" type="text/css">
</head>

<!-- ****** HTML CODE ***** //-->

<body class="mainTheme" onLoad="init()" style="border-style:solid; border-width:1">
<form name="preferenceForm" method="post" action="/PORTAL/servlet/RequestManager">

<span class="mainContent" style="position:relative; width:100%; height:100%; top:0; left:0; z-index:0; overflow:visible">
<table width="100%" height="50%" border="0" cellspacing="0" cellpadding="0">

    <td align="right" nowrap>Preference Type:&nbsp;</td>
    <td align="left" nowrap>
      <select name="preferenceType">
        <option value="null">Please Select</option>
          <%
              for(int i = 0; i < preferenceTypes.length; i++) {

                  String codeDesc    = preferenceTypes[i].getCodeDesc();
                  String code        = preferenceTypes[i].getCode();

                  if (preferenceType.equalsIgnoreCase(code)) {

                      out.println("<option selected name=\"id\" value=\"" + code + "\">" + codeDesc + "</option>");
                  }
                  else  {

                      out.println("<option name=\"id\" value=\"" + code + "\">" + codeDesc + "</option>");
                  }
              }
          %>
      </select>
    </td>
  <tr>
    <td align="right" nowrap>Override Status:&nbsp;</td>
    <td align="left" nowrap colspan="3">
      <select name="overrideStatus">
       <option>Please Select</option>
        <%
          for(int i = 0; i < overrideStatuses.length; i++)
          {
              if (overrideStatuses[i].equals(overrideStatus))
              {
                 out.println("<option selected name=\"id\" value=\"" + overrideStatuses[i] + "\">" + overrideStatuses[i] + "</option>");
              }
              else  {
                 out.println("<option name=\"id\" value=\"" + overrideStatuses[i] + "\">" + overrideStatuses[i] + "</option>");
              }
          }
        %>
      </select>
    </td>
  </tr>
  <tr>
    <td align="right" nowrap>Payment Mode:&nbsp;</td>
    <td align="left" nowrap>
      <select name="paymentMode">
        <option value="null">Please Select</option>
          <%
              for(int i = 0; i < paymentModes.length; i++) {

                  String codeDesc    = paymentModes[i].getCodeDesc();
                  String code        = paymentModes[i].getCode();

                  if (paymentMode.equalsIgnoreCase(code)) {

                      out.println("<option selected name=\"id\" value=\"" + code + "\">" + codeDesc + "</option>");
                  }
                  else  {

                      out.println("<option name=\"id\" value=\"" + code + "\">" + codeDesc + "</option>");
                  }
              }
          %>
      </select>
    </td>
    <td align="right" nowrap>Minimum Check:&nbsp;</td>
    <td align="left" nowrap>
      <input type="text" name="minimumCheck" maxlength="11" size="11" value="<%= minimumCheck %>" CURRENCY>
    </td>
  </tr>
  <tr>
    <td align="right" nowrap>Source:&nbsp;</td>
    <td align="left" nowrap>
      <select name="disbursementSource">
        <option value="null">Please Select</option>
          <%
            for(int i = 0; i < disbursementSources.length; i++) {

                String codeDesc    = disbursementSources[i].getCodeDesc();
                String code        = disbursementSources[i].getCode();

                if (disbursementSource.equalsIgnoreCase(code)) {

                    out.println("<option selected name=\"id\" value=\"" + code+ "\">" + codeDesc + "</option>");
                }
                else  {

                    out.println("<option name=\"id\" value=\"" + code + "\">" + codeDesc + "</option>");
                }
            }
          %>
      </select>
    </td>
    <td align="right" nowrap>Bank Account Type:&nbsp;</td>
    <td align="left" nowrap>
      <select name="bankAccountType">
        <option value="null">Please Select</option>
          <%
            for(int i = 0; i < bankAccountTypes.length; i++) {

                String codeDesc    = bankAccountTypes[i].getCodeDesc();
                String code        = bankAccountTypes[i].getCode();

                if (bankAccountType.equalsIgnoreCase(code)) {

                    out.println("<option selected name=\"id\" value=\"" + code + "\">" + codeDesc + "</option>");
                }
                else  {

                    out.println("<option name=\"id\" value=\"" + code + "\">" + codeDesc + "</option>");
                }
            }
          %>
      </select>
    </td>
  </tr>
  <tr>
    <td align="right" nowrap>Bank Routing Number:&nbsp;</td>
    <td align="left" nowrap>
      <input type="text" name="bankRoutingNumber" maxlength="9" size="9" value="<%= bankRoutingNumber %>">
    </td>
    <td align="right" nowrap>Bank Account Number:&nbsp;</td>
    <td align="left" nowrap>
      <input type="text" name="bankAccountNumber" maxlength="17" size="17" value="<%= bankAccountNumber %>">
    </td>
  </tr>
  <tr>
    <td align="right" nowrap>Bank Name:&nbsp;</td>
    <td align="left" nowrap colspan="3">
      <input type="text" name="bankName" maxlength="60" size="60" value="<%= bankName %>">
    </td>
  </tr>
  <tr>
    <td align="right" nowrap>Bank Address (line 1):&nbsp;</td>
    <td align="left" nowrap colspan="3">
      <input type="text" name="bankAddressLine1" maxlength="35" size="35" value="<%= bankAddressLine1 %>">
    </td>
  </tr>
  <tr>
    <td align="right" nowrap>(line 2):&nbsp;</td>
    <td align="left" nowrap colspan="3">
      <input type="text" name="bankAddressLine2" maxlength="35" size="35" value="<%= bankAddressLine2 %>">
    </td>
  </tr>
  <tr>
    <td align="right" nowrap>Bank City:&nbsp;</td>
    <td align="left" nowrap colspan="3">
      <input type="text" name="bankCity" maxlength="35" size="35" value="<%= bankCity %>">
    </td>
  </tr>
  <tr>
    <td align="right" nowrap>Bank State:&nbsp;</td>
    <td align="left" nowrap>
      <select name="bankState">
        <option value="null">Please Select</option>
          <%
            for(int i = 0; i < stateCodes.length; i++) {

                String codeDesc    = stateCodes[i].getCodeDesc();
                String code        = stateCodes[i].getCode();

                if (bankState.equalsIgnoreCase(code)) {

                    out.println("<option selected name=\"id\" value=\"" + code + "\">" + codeDesc + "</option>");
                }
                else  {

                    out.println("<option name=\"id\" value=\"" + code + "\">" + codeDesc + "</option>");
                }
            }
          %>
      </select>
    </td>
    <td align="right" nowrap>Bank Zip Code:&nbsp;</td>
    <td align="left" nowrap>
      <input type="text" name="bankZipCode" maxlength="15" size="15" value="<%= bankZipCode %>">
    </td>
  </tr>
  <tr bordercolor="#000000">
    <td align="right" nowrap>Print As:&nbsp;</td>
    <td align="left" nowrap colspan="3">
      <input type="text" name="printAs" maxlength="70" size="70" value="<%= printAs %>">
    </td>
  </tr>
  <br>
  <tr bordercolor="#000000">
    <td align="right" nowrap>Print As 2:&nbsp;</td>
    <td align="left" nowrap colspan="3">
      <input type="text" name="printAs2" maxlength="70" size="70" value="<%= printAs2 %>">
    </td>
  </tr>
  <tr>
    <td align="right" nowrap>
        <a href ="javascript:showContractAgentInformation()">Contract/Agent Information</a>
    </td>
    <td nowrap colspan="3">&nbsp;</td>
  </tr>
    <tr>
        <td align="left" colspan="3">
            &nbsp;
        </td>
        <td align="right">
        </td>
    </tr>
</table>
<table width="100%" height="2%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td nowrap align="left">
      <input type="button" value="   Add   " style="background-color:#DEDEDE" onClick="addPreference()">
      <input type="button" value="   Save  " style="background-color:#DEDEDE" onClick="savePreferenceInformation()">
    </td>
  </tr>
</table>
<table class="summaryArea" id="summaryTable" width="100%" height="35%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <th align="left" width="16%">Pay Mode</th>
    <th align="left" width="16%">Min Check</th>
    <th align="left" width="16%">Source</th>
    <th align="left" width="16%">Account Type</th>
    <th align="left" width="16%">Routing Number</th>
    <th align="left" width="16%">Account Number</th>
    <th align="left" width="4%">O/R</th>
  </tr>
  <tr width="100%" height="99%">
    <td colspan="7">
      <span class="scrollableContent">
        <table class="scrollableArea" id="preferenceSummary" width="100%" border="0" cellspacing="0" cellpadding="0">
            <%
              String className = "";
              String selected = "";
              String payMode = "";
              String minCheck = "";
              String disbSource = "";
              String acctType = "";
              String routingNumber = "";
              String accountNumber = "";
              String override = "";
              String preferenceBeanKey = "";

              Map sortedPreferences = sortPreferences(preferenceSessionBean);

              Iterator it = sortedPreferences.values().iterator();

              while (it.hasNext())  {

                  PageBean preferenceBean = (PageBean)it.next();

                  payMode = Util.initString(preferenceBean.getValue("paymentMode"), "");
                  minCheck = Util.initString(preferenceBean.getValue("minimumCheck"), "");
                  disbSource = Util.initString(preferenceBean.getValue("disbursementSource"), "");
                  acctType = Util.initString(preferenceBean.getValue("bankAccountType"), "");
                  routingNumber = Util.initString(preferenceBean.getValue("bankRoutingNumber"), "");
                  accountNumber = Util.initString(preferenceBean.getValue("bankAccountNumber"), "");
                  override = preferenceBean.getValue("overrideStatus");
                  preferenceBeanKey = preferenceBean.getValue("preferencePK");

                  if (preferenceBeanKey.equals(preferencePK))
                  {
                      className = "highlighted";

                      selected = "true";
                  }
                  else {

                      className = "default";

                      selected="false";
                  }
            %>

            <tr class="<%= className %>" isSelected="<%= selected %>"
                id="<%= preferenceBeanKey %>" onMouseOver="highlightRow()"
                onMouseOut="unhighlightRow()" onClick="selectRow()">
              <td width="16%" align="left" nowrap>
                <%= payMode %>
              </td>
              <td width="16%" align="left" nowrap>
                <script>document.write(formatAsCurrency(<%= minCheck %>))</script>
              </td>
              <td width="16%" align="left" nowrap>
                <%= disbSource %>
              </td>
              <td width="16%" align="left" nowrap>
                <%= acctType %>
              </td>
              <td width="16%" align="left" nowrap>
                <%= routingNumber %>
              </td>
              <td width="16%" align="left" nowrap>
                <%= accountNumber %>
              </td>
              <td width="4" align="left" nowrap>
                <%= override %>
              </td>
            </tr>
            <%
              }// end while
            %>
        </table>
      </span>
    </td>
  </tr>
</table>
<table id="table3" width="100%" border="0" cellspacing="6" cellpadding="0">
  <tr>
    <td align="right"colspan="4" nowrap>
      <input id="btnSelect" type="button" value=" Select " onClick="selectPreferenceForClient()">
      <input id="btnCancel" type="button" value=" Close " onClick="closeAndShowAgentFinancial()">
    </td>
  </tr>
</table>
</span>
<!-- ****** HIDDEN FIELDS ***** //-->
  	<input type="hidden" name="transaction" value="">
    <input type="hidden" name="action"      value="">
    <input type="hidden" name="clientDetailPK" value="<%= clientDetailPK %>">
    <input type="hidden" name="preferencePK" value="<%= preferencePK %>">
    <input type="hidden" name="selectedClientRoleFK" value="<%= selectedClientRoleFK %>">
    <input type="hidden" name="returnPage" value="<%= returnPage %>">

</body>
</form>
</html>
