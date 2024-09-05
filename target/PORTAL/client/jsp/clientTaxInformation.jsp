<!-- ****** JAVA CODE ***** //-->

<%@ page  errorPage="/common/jsp/error.jsp" autoFlush="true" %>
<%@ page import="java.util.*,
                 edit.common.*,
                 edit.common.vo.*,
                 edit.common.vo.user.*,
                 fission.beans.*,
                 edit.common.exceptions.VOEditException,
                 edit.portal.exceptions.PortalEditingException,
                 edit.portal.common.session.UserSession" %>

<jsp:useBean id="taxInformationSessionBean"
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

    CodeTableWrapper codeTableWrapper = CodeTableWrapper.getSingleton();

    CodeTableVO[] states            = codeTableWrapper.getCodeTableEntries("STATE");
    CodeTableVO[] trustTypes        = codeTableWrapper.getCodeTableEntries("TRUSTTYPE");
    CodeTableVO[] proofOfAges       = codeTableWrapper.getCodeTableEntries("YESNO");
    CodeTableVO[] maritalStatuses   = codeTableWrapper.getCodeTableEntries("MARITALSTATUS");
    CodeTableVO[] usCitizenTypes    = codeTableWrapper.getCodeTableEntries("YESNO");
    CodeTableVO[] countries         = codeTableWrapper.getCodeTableEntries("COUNTRY");
    CodeTableVO[] taxFilingStatuses = codeTableWrapper.getCodeTableEntries("TAXFILINGSTATUS");
    CodeTableVO[] taxIndicators     = codeTableWrapper.getCodeTableEntries("TAXINDICATOR");

    PageBean pageBean = taxInformationSessionBean.getPageBean("pageBean");

    String clientDetailPK       = pageBean.getValue("clientDetailFK");
    String taxInformationPK     = pageBean.getValue("taxInformationPK");
    String taxProfilePK         = pageBean.getValue("taxProfilePK");
    String areaId	            = pageBean.getValue("areaId");
	String taxTypeId            = pageBean.getValue("taxTypeId");
	String trustTypeId    	    = pageBean.getValue("trustTypeId");
	String proofOfAgeId         = pageBean.getValue("proofOfAgeId");
	String maritalStatusId      = pageBean.getValue("maritalStatusId");
    String usCitizenId          = pageBean.getValue("usCitizenId");
	String stateOfBirthId       = pageBean.getValue("stateOfBirthId");
	String countryOfBirthId     = pageBean.getValue("countryOfBirthId");
    String taxFilingStatus      = pageBean.getValue("filingStatusId");
    String exemptions           = pageBean.getValue("exemptions");
    String taxIndicator         = pageBean.getValue("taxIndicator");
    String ficaIndicatorStatus  = pageBean.getValue("ficaIndicatorStatus");

    UserSession userSession = (UserSession) session.getAttribute("userSession");
%>

<html>

<script src="/PORTAL/common/javascript/commonJavascriptFunctions.js"></script>

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

function init() {

	f = document.taxInformationForm;

    if (clientMessage != "" &&
        clientMessage != null) {

        alert(clientMessage);
    }

    if (errorMessage != "" &&
        errorMessage != null) {

        alert("<%= errorMessage %>");
    }

    var clientIsLocked = <%= userSession.getClientDetailIsLocked()%>;
    var username = "<%= userSession.getUsername() %>";
    var elementPK = "<%= userSession.getClientDetailIsLocked() %>";

    shouldShowLockAlert = !clientIsLocked;

    for (var i = 0; i < f.elements.length; i++) {

        var elementType = f.elements[i].type;

        if ( (elementType == "text") &&
             (shouldShowLockAlert == true) ) {

            f.elements[i].onclick = showLockAlert;
            f.elements[i].onkeydown = showLockAlert;
        }
    }
}

function showLockAlert(){

    if (shouldShowLockAlert == true) {

        alert("The Client can not be edited.");

        return false;
    }
}

    function saveTaxInformation()
    {
        if (shouldShowLockAlert == true)
        {

            alert("The Client can not be edited.");

            return;
        }

        sendTransactionAction("ClientDetailTran", "saveClientTaxPage", "contentIFrame");
        window.close();
    }

    function closeAndShowClientPage()
    {

        window.close();
    }


</script>
<head>
<title>Client Tax Information</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta http-equiv="Cache-Control" content="no-store">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Expires" content="0">

<link href="/PORTAL/common/css/PORTAL.css" rel="stylesheet" type="text/css">
</head>

<!-- ****** HTML CODE ***** //-->

<body class="mainTheme" onLoad="init(); checkForEditingException(); checkForVOEditException()" style="border-style:solid; border-width:1;">
<form name="taxInformationForm" method="post" action="/PORTAL/servlet/RequestManager">

<span id="mainContent" style="position:relative; width:100%; height:100%; top:0; left:0; z-index:0; overflow:visible">
<table width="100%" height="50%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td align="right" nowrap>Tax Id Type:&nbsp;</td>
    <td align="left" nowrap>
      <input disabled type="text" name="taxTypeId" size="20" value="<%= taxTypeId %>">
    </td>
  </tr>
  <tr>
    <td align="right" nowrap>Proof of Age:&nbsp;</td>
    <td align="left" nowrap>
      <select name="proofOfAgeId">
        <option value="null">Please Select</option>
          <%
            for(int i = 0; i < proofOfAges.length; i++) {

                String codeTablePK = proofOfAges[i].getCodeTablePK() + "";
                String codeDesc    = proofOfAges[i].getCodeDesc();
                String code        = proofOfAges[i].getCode();

                if (proofOfAgeId.equalsIgnoreCase(code)) {

                    out.println("<option selected name=\"id\" value=\"" + code + "\">" + codeDesc + "</option>");
                }
                else  {

                    out.println("<option name=\"id\" value=\"" + code + "\">" + codeDesc + "</option>");
                }
            }
          %>
      </select>
    </td>
    <td align="right" nowrap>Marital Status:&nbsp;</td>
    <td align="left" nowrap>
      <select name="maritalStatusId">
        <option value="null">Please Select</option>
          <%
            for(int i = 0; i < maritalStatuses.length; i++) {

                String codeTablePK = maritalStatuses[i].getCodeTablePK() + "";
                String codeDesc    = maritalStatuses[i].getCodeDesc();
                String code        = maritalStatuses[i].getCode();

                if (maritalStatusId.equalsIgnoreCase(code)) {

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
    <td align="right" nowrap>Filing Status:&nbsp;</td>
    <td align="left" nowrap>
      <select name="filingStatusId">
        <option value="null">Please Select</option>
          <%
              for(int i = 0; i < taxFilingStatuses.length; i++) {

                  String codeTablePK = taxFilingStatuses[i].getCodeTablePK() + "";
                  String codeDesc    = taxFilingStatuses[i].getCodeDesc();
                  String code        = taxFilingStatuses[i].getCode();

                  if (taxFilingStatus.equalsIgnoreCase(code)) {

                      out.println("<option selected name=\"id\" value=\"" + code + "\">" + codeDesc + "</option>");
                  }
                  else  {

                      out.println("<option name=\"id\" value=\"" + code + "\">" + codeDesc + "</option>");
                  }
              }
          %>
      </select>
    </td>
    <td align="right" nowrap>Exemptions:&nbsp;</td>
    <td align="left" nowrap>
      <input type="text" name="exemptions" maxlength="2" size="2" value="<%= exemptions %>">
    </td>
  </tr>
  <tr>
    <td align="right" nowrap>Tax Indicator:&nbsp;</td>
    <td align="left" nowrap>
      <select name="taxIndicator">
        <option value="null">Please Select</option>
          <%
              for(int i = 0; i < taxIndicators.length; i++) {

                  String codeTablePK = taxIndicators[i].getCodeTablePK() + "";
                  String codeDesc    = taxIndicators[i].getCodeDesc();
                  String code        = taxIndicators[i].getCode();

                  if (taxIndicator.equalsIgnoreCase(code)) {

                      out.println("<option selected name=\"id\" value=\"" + code + "\">" + codeDesc + "</option>");
                  }
                  else  {

                      out.println("<option name=\"id\" value=\"" + code + "\">" + codeDesc + "</option>");
                  }
              }
          %>
      </select>
    </td>
    <td align="right" nowrap>FICA Indicator:&nbsp;</td>
    <td align="left" nowrap>
      <input type="checkbox" name="ficaIndicator" <%= ficaIndicatorStatus %>>
    </td>
  </tr>
  <tr>
    <td align="right" nowrap>Country of Birth:&nbsp;</td>
    <td align="left" nowrap>
      <select name="countryOfBirthId">
        <option value="null">Please Select</option>
          <%
            for(int i = 0; i < countries.length; i++) {

                String codeTablePK = countries[i].getCodeTablePK() + "";
                String codeDesc    = countries[i].getCodeDesc();
                String code        = countries[i].getCode();

                if (countryOfBirthId.equalsIgnoreCase(code)) {

                    out.println("<option selected name=\"id\" value=\"" + code + "\">" + codeDesc + "</option>");
                }
                else  {

                    out.println("<option name=\"id\" value=\"" + code + "\">" + codeDesc + "</option>");
                }
            }
          %>
      </select>
    </td>
    <td align="right" nowrap>State of Birth:&nbsp;</td>
    <td align="left" nowrap>
      <select name="stateOfBirthId">
        <option value="null">Please Select</option>
          <%

            for(int i = 0; i < states.length; i++) {

                String codeTablePK = states[i].getCodeTablePK() + "";
                String codeDesc    = states[i].getCodeDesc();
                String code        = states[i].getCode();

                if (stateOfBirthId.equalsIgnoreCase(code)) {

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
    <td align="right" nowrap>U.S. Citizen:&nbsp;</td>
    <td align="left" nowrap colspan="3">
      <select name="usCitizenId">
        <option value="null">Please Select</option>
          <%
            for(int i = 0; i < usCitizenTypes.length; i++) {

                String codeTablePK = usCitizenTypes[i].getCodeTablePK() + "";
                String codeDesc    = usCitizenTypes[i].getCodeDesc();
                String code        = usCitizenTypes[i].getCode();

                if (usCitizenId.equalsIgnoreCase(code)) {

                    out.println("<option selected name=\"id\" value=\"" + code+ "\">" + codeDesc + "</option>");
                }
                else {

                    out.println("<option name=\"id\" value=\"" + code + "\">" + codeDesc + "</option>");
                }
            }
          %>
      </select>
    </td>
  </tr>
    <tr>
        <td align="left" colspan="3">
            &nbsp;
        </td>
        <td align="right">
            <input id="btnSave" type="button" value=" Save " onClick="saveTaxInformation()">
            <input id="btnCancel" type="button" value=" Cancel " onClick="closeAndShowClientPage()">
        </td>
    </tr>
</table>
</span>
<!-- ****** HIDDEN FIELDS ***** //-->
  	<input type="hidden" name="transaction" value="">
    <input type="hidden" name="action"      value="">
    <input type="hidden" name="clientDetailPK" value="<%= clientDetailPK %>">
    <input type="hidden" name="taxInformationPK" value="<%= taxInformationPK %>">
    <input type="hidden" name="taxProfilePK" value="<%= taxProfilePK %>">
    <input type="hidden" name="ficaIndicatorStatus" value="">
    <input type="hidden" name="taxTypeId" value="<%= taxTypeId %>">

</body>
</form>
</html>
