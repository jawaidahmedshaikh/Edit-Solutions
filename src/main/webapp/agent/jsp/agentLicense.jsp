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

<jsp:useBean id="statusBean"
    class="fission.beans.PageBean" scope="request"/>

<%
    CodeTableWrapper codeTableWrapper = CodeTableWrapper.getSingleton();

    CodeTableVO[] stateCodes = codeTableWrapper.getCodeTableEntries("STATE");
    CodeTableVO[] productTypes = codeTableWrapper.getCodeTableEntries("AGENTPRODUCTTYPE");
    CodeTableVO[] licenseStatuses = codeTableWrapper.getCodeTableEntries("LICENSESTATUS");
    CodeTableVO[] licenseTypes = codeTableWrapper.getCodeTableEntries("LICENSETYPE");
    CodeTableVO[] renewTermStatuses = codeTableWrapper.getCodeTableEntries("RENEWTERMSTATUS");
    CodeTableVO[] residentStatuses = codeTableWrapper.getCodeTableEntries("RESIDENT");

    String nasd = "";
    String nasdEffMonth = "";
    String nasdEffDay = "";
    String nasdEffYear = "";
    String nasdRenewalMonth = "";
    String nasdRenewalDay = "";
    String nasdRenewalYear = "";
    String state = "";
    String resident = "";
    String stateNASDStatus = "unchecked";
    String productType = "";
    String status = "";
    String licenseNumber = "";
    String licEffMonth = "";
    String licEffDay = "";
    String licEffYear = "";
    String eoStatus = "unchecked";
    String eoExpMonth = "";
    String eoExpDay = "";
    String eoExpYear = "";
    String licenseType = "";
    String licExpMonth = "";
    String licExpDay = "";
    String licExpYear = "";
    String renewTermStatus = "";
    String licTermMonth = "";
    String licTermDay = "";
    String licTermYear = "";
    String agentPK = "0";
    String agentLicensePK = Util.initString((String) request.getAttribute("agentLicensePK"), "");

    AgentLicenseVO[] agentLicenseVOs = null;
    AgentVO agentVO = (AgentVO) session.getAttribute("agentVO");
    if (agentVO != null) {

        agentPK = agentVO.getAgentPK() + "";
        agentLicenseVOs = agentVO.getAgentLicenseVO();
    }

    if (agentLicenseVOs != null && !agentLicensePK.equals("")) {

        for (int l = 0; l < agentLicenseVOs.length; l++) {

            String licensePK = agentLicenseVOs[l].getAgentLicensePK() + "";
            if (!agentLicensePK.equals("") && agentLicensePK.equals(licensePK)) {

                nasd = Util.initString(agentLicenseVOs[l].getNASD(), "");

                String nasdEffectiveDate = agentLicenseVOs[l].getNASDEffectiveDate();
                String[] nasdEffDateArray = DateTimeUtil.initDate(nasdEffectiveDate);
                nasdEffMonth = nasdEffDateArray[0];
                nasdEffDay = nasdEffDateArray[1];
                nasdEffYear = nasdEffDateArray[2];


                String nasdRenewalDate = agentLicenseVOs[l].getNASDRenewalDate();
                String[] nasdRenewalDateArray = DateTimeUtil.initDate(nasdRenewalDate);
                nasdRenewalMonth = nasdRenewalDateArray[0];
                nasdRenewalDay = nasdRenewalDateArray[1];
                nasdRenewalYear = nasdRenewalDateArray[2];

                state = Util.initString(agentLicenseVOs[l].getStateCT(), "");
                resident = Util.initString(agentLicenseVOs[l].getResidentCT(), "");
                String stateNASD = Util.initString(agentLicenseVOs[l].getStateNASD(), "");
                if (stateNASD.equals("Y")) {

                    stateNASDStatus = "checked";
                }
                productType = Util.initString(agentLicenseVOs[l].getProductTypeCT(), "");
                status = Util.initString(agentLicenseVOs[l].getStatusCT(), "");
                licenseNumber = Util.initString(agentLicenseVOs[l].getLicenseNumber(), "");
                String licEffDate = agentLicenseVOs[l].getLicEffDate();
                String[] licEffDateArray = DateTimeUtil.initDate(licEffDate);
                licEffMonth = licEffDateArray[0];
                licEffDay = licEffDateArray[1];
                licEffYear = licEffDateArray[2];

                String errorOmissStatus = agentLicenseVOs[l].getErrorOmmissStatus();
                if (errorOmissStatus.equals("Y")) {

                    eoStatus = "checked";
                }
                licenseType = Util.initString(agentLicenseVOs[l].getLicenseTypeCT(), "");
                String licExpDate = agentLicenseVOs[l].getLicExpDate();
                String[] licExpDateArray = DateTimeUtil.initDate(licExpDate);
                licExpMonth = licExpDateArray[0];
                licExpDay = licExpDateArray[1];
                licExpYear = licExpDateArray[2];

                String eoExpDate = agentLicenseVOs[l].getErrorOmmissExpDate();
                String[] eoExpDateArray = DateTimeUtil.initDate(eoExpDate);
                eoExpMonth = eoExpDateArray[0];
                eoExpDay = eoExpDateArray[1];
                eoExpYear = eoExpDateArray[2];

                renewTermStatus = Util.initString(agentLicenseVOs[l].getRenewTermStatusCT(), "");
                String licTermDate = agentLicenseVOs[l].getLicTermDate();
                String[] licTermDateArray = DateTimeUtil.initDate(licTermDate);
                licTermMonth = licTermDateArray[0];
                licTermDay = licTermDateArray[1];
                licTermYear = licTermDateArray[2];
            }
        }
    }

    String rowToMatchBase = licenseNumber;

    UserSession userSession = (UserSession) session.getAttribute("userSession");
%>

<html>

<script language="Javascript1.2">

	var f = null;

    var shouldShowLockAlert = true;;

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

		f = document.agentLicenseForm;

		top.frames["main"].setActiveTab("licenseTab");

        var agentIsLocked = <%= userSession.getAgentIsLocked()%>;
        var username = "<%= userSession.getUsername() %>";
        var elementPK = "<%= userSession.getAgentPK() %>";
		top.frames["header"].updateLockState(agentIsLocked, username, elementPK);

        shouldShowLockAlert = !agentIsLocked;

        for (var i = 0; i < f.elements.length; i++)
        {
            var elementType = f.elements[i].type;

            if ((elementType == "text" ||
                 elementType == "button" ||
                 elementType == "select-one") &&
                (shouldShowLockAlert == true))
            {
                f.elements[i].onclick = showLockAlert;
                f.elements[i].onkeydown = showLockAlert;
            }
        }
	}

    function showLockAlert(){

        if (shouldShowLockAlert == true){

            alert("The Agent can not be edited.");

            return false;
        }
    }

    function saveLicense() {

		if (f.nasdStatus.checked == true) {

			f.nasdStatus.value = "checked";
		}
        else {

            f.nasdStatus.value = "unchecked";
        }

		if (f.eoStatus.checked == true) {

			f.eoStatus.value = "checked";
		}
        else {

            f.eoStatus.value = "unchecked";
        }

        sendTransactionAction("AgentDetailTran","saveLicenseToSummary","_self");
    }

	function selectRow() {

		var tdElement = window.event.srcElement;
		var trElement = tdElement.parentElement;

		var key = trElement.id;

		f.agentLicensePK.value = key;

		sendTransactionAction("AgentDetailTran", "showLicenseDetailSummary", "_self");
	}

    function addLicense() {

        sendTransactionAction("AgentDetailTran", "clearFormForAddOrCancel", "_self");
    }

    function cancelLicense() {

        sendTransactionAction("AgentDetailTran", "clearFormForAddOrCancel", "_self");
    }

    function deleteLicense() {

        sendTransactionAction("AgentDetailTran", "deleteLicense", "_self");
    }

	function sendTransactionAction(transaction, action, target) {

    	f.transaction.value=transaction;
    	f.action.value=action;

    	f.target = target;

    	f.submit();
	}
</script>

<head>
<title>Agent License</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta http-equiv="Cache-Control" content="no-store">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Expires" content="0">

<link href="/PORTAL/common/css/PORTAL.css" rel="stylesheet" type="text/css">
</head>

<body class="mainTheme" onLoad="init();" style="border-style:solid; border-width:1;">
<form name= "agentLicenseForm" method="post" action="/PORTAL/servlet/RequestManager">

<input type="hidden" name="page" value="">
<jsp:include page="agentInfoHeader.jsp" flush="true"/>

<span id="mainContent" style="border-style:solid; border-width:1;  position:relative; width:100%; height:100%; top:0; left:0; z-index:0; overflow:visible">
  <table width="100%" height="60%">
    <tr>
      <td align="left" nowrap>NASD:&nbsp;
        <input type="text" name="nasd" maxlength="20" size="20" value="<%= nasd %>">
      </td>
      <td align="left" nowrap>Eff Date:&nbsp;
        <input type="text" name="nasdEffMonth" maxlength="2" size="2" value="<%= nasdEffMonth %>">
        /
        <input type="text" name="nasdEffDay" maxlength="2" size="2" value="<%= nasdEffDay %>">
        /
        <input type="text" name="nasdEffYear" maxlength="4" size="4" value="<%= nasdEffYear %>">
      </td>
      <td align="left" nowrap colspan="2">Renewal Date:&nbsp;
        <input type="text" name="nasdRenewalMonth" maxlength="2" size="2" value="<%= nasdRenewalMonth %>">
        /
        <input type="text" name="nasdRenewalDay" maxlength="2" size="2" value="<%= nasdRenewalDay %>">
        /
        <input type="text" name="nasdRenewalYear" maxlength="4" size="4" value="<%= nasdRenewalYear %>">
      </td>
    </tr>
    <tr>
      <td colspan="4">
        <hr size="1" width="150%">
      </td>
    </tr>
    <tr>
      <td align="right" nowrap>Resident:&nbsp;</td>
      <td align="left" nowrap>
        <select name="residentStatus">
          <option>Please Select</option>
          <%
              for(int i = 0; i < residentStatuses.length; i++) {

                  String codeTablePK = residentStatuses[i].getCodeTablePK() + "";
                  String codeDesc    = residentStatuses[i].getCodeDesc();
                  String code        = residentStatuses[i].getCode();

                 if (resident.equalsIgnoreCase(code)) {

                     out.println("<option selected name=\"id\" value=\"" + codeTablePK+ "\">" + codeDesc + "</option>");
                 }
                 else  {

                     out.println("<option name=\"id\" value=\"" + codeTablePK + "\">" + codeDesc + "</option>");
                 }
              }
          %>
        </select>
      </td>
      <td align="right" nowrap>NASD
        <input type="checkbox" name="nasdStatus" <%= stateNASDStatus %> >
      </td>
    </tr>
    <tr>
      <td align="right" nowrap>State:&nbsp;</td>
      <td align="left" nowrap>
        <select name="state">
          <option> Please Select </option>
          <%
              for(int i = 0; i < stateCodes.length; i++) {

                  String codeTablePK = stateCodes[i].getCodeTablePK() + "";
                  String codeDesc    = stateCodes[i].getCodeDesc();
                  String code        = stateCodes[i].getCode();

                 if (state.equalsIgnoreCase(code)) {

                     out.println("<option selected name=\"id\" value=\"" + codeTablePK+ "\">" + codeDesc + "</option>");
                 }
                 else  {

                     out.println("<option name=\"id\" value=\"" + codeTablePK + "\">" + codeDesc + "</option>");
                 }
              }
          %>
        </select>
      </td>
      <td align="right" nowrap>Status:&nbsp;</td>
      <td align="left" nowrap>
        <select name="status">
          <option> Please Select </option>
          <%
              for(int i = 0; i < licenseStatuses.length; i++) {

                  String codeTablePK = licenseStatuses[i].getCodeTablePK() + "";
                  String codeDesc    = licenseStatuses[i].getCodeDesc();
                  String code        = licenseStatuses[i].getCode();

                 if (status.equalsIgnoreCase(code)) {

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
      <td align="right" nowrap>Product:&nbsp;</td>
      <td align="left" nowrap>
        <select name="productType">
          <option> Please Select </option>
          <%
              for(int i = 0; i < productTypes.length; i++) {

                  String codeTablePK = productTypes[i].getCodeTablePK() + "";
                  String codeDesc    = productTypes[i].getCodeDesc();
                  String code        = productTypes[i].getCode();

                 if (productType.equalsIgnoreCase(code)) {

                     out.println("<option selected name=\"id\" value=\"" + codeTablePK+ "\">" + codeDesc + "</option>");
                 }
                 else  {

                     out.println("<option name=\"id\" value=\"" + codeTablePK + "\">" + codeDesc + "</option>");
                 }
              }
          %>
        </select>
      </td>
      <td align="right" nowrap>EffDate:&nbsp;</td>
      <td align="left" nowrap>
        <input type="text" name="licEffMonth" maxlength="2" size="2" value="<%= licEffMonth %>">
        /
        <input type="text" name="licEffDay" maxlength="2" size="2" value="<%= licEffDay %>">
        /
        <input type="text" name="licEffYear" maxlength="4" size="4" value="<%= licEffYear %>">
      </td>
    </tr>
    <tr>
      <td align="right" nowrap>License Number:&nbsp;</td>
      <td align="left" nowrap>
        <input type="text" name="licenseNumber" maxlength="20" size="20" value="<%= licenseNumber %>">
      </td>
      <td align="right" nowrap>Exp Date:&nbsp;</td>
      <td align="left" nowrap>
        <input type="text" name="licExpMonth" maxlength="2" size="2" value="<%= licExpMonth %>">
        /
        <input type="text" name="licExpDay" maxlength="2" size="2" value="<%= licExpDay %>">
        /
        <input type="text" name="licExpYear" maxlength="4" size="4" value="<%= licExpYear %>">
      </td>
    </tr>
    <tr>
      <td align="right" nowrap>Type:&nbsp;</td>
      <td align="left" nowrap>
        <select name="licenseType">
          <option> Please Select </option>
          <%
              for(int i = 0; i < licenseTypes.length; i++) {

                  String codeTablePK = licenseTypes[i].getCodeTablePK() + "";
                  String codeDesc    = licenseTypes[i].getCodeDesc();
                  String code        = licenseTypes[i].getCode();

                 if (licenseType.equalsIgnoreCase(code)) {

                     out.println("<option selected name=\"id\" value=\"" + codeTablePK+ "\">" + codeDesc + "</option>");
                 }
                 else  {

                     out.println("<option name=\"id\" value=\"" + codeTablePK + "\">" + codeDesc + "</option>");
                 }
              }
          %>
        </select>
      </td>
      <td align="right" nowrap>Term Date:&nbsp;</td>
      <td align="left" nowrap>
        <input type="text" name="licTermMonth" maxlength="2" size="2" value="<%= licTermMonth %>">
        /
        <input type="text" name="licTermDay" maxlength="2" size="2" value="<%= licTermDay %>">
        /
        <input type="text" name="licTermYear" maxlength="4" size="4" value="<%= licTermYear %>">
      </td>
    </tr>
    <tr>
      <td align="right" nowrap>Renew/Term:&nbsp;</td>
      <td align="left" nowrap colspan="3">
        <select name="renewTermStatus">
          <option> Please Select </option>
          <%
              for(int i = 0; i < renewTermStatuses.length; i++) {

                  String codeTablePK = renewTermStatuses[i].getCodeTablePK() + "";
                  String codeDesc    = renewTermStatuses[i].getCodeDesc();
                  String code        = renewTermStatuses[i].getCode();

                 if (renewTermStatus.equalsIgnoreCase(code)) {

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
      <td align="right" nowrap>E&O&nbsp;</td>
      <td align="left" nowrap>
        <input type="checkbox" name="eoStatus" <%= eoStatus %> >
      </td>
      <td align="right" nowrap>E&O Exp Date:&nbsp;</td>
      <td align="left" nowrap>
        <input type="text" name="eoExpMonth" maxlength="2" size="2" value="<%= eoExpMonth %>">
        /
        <input type="text" name="eoExpDay" maxlength="2" size="2" value="<%= eoExpDay %>">
        /
        <input type="text" name="eoExpYear" maxlength="4" size="4" value="<%= eoExpYear %>">
      </td>
   </tr>
  </table>
  <table width="100%" height="2%" border="0" cellspacing="0" cellpadding="0">
	<tr>
	  <td align="left">
		<input type="button" name="add" value="   Add   " onClick="addLicense()">
		<input type="button" name="save" value=" Save " onClick="saveLicense()">
		<input type="button" name="cancel" value="Cancel" onClick="cancelLicense()">
		<input type="button" name="delete" value="Delete" onClick="deleteLicense()">
	  </td>
	</tr>
  </table>
  <table class="summaryArea" id="summaryTable" width="100%" height="30%" border="0" cellspacing="0" cellpadding="0">
    <tr height="1%">
      <th width="20%" align="left">State</th>
      <th width="20%" align="left">Prod</th>
      <th width="20%" align="left">Lic #</th>
	  <th width="20%" align="left">Type</th>
      <th width="20%" align="left">Status</th>
    </tr>
    <tr width="100%" height="99%">
      <td colspan="10">
        <span class="scrollableContent">
          <table class="scrollableArea" id="licenseSummary" width="100%" border="0" cellspacing="0" cellpadding="0">
            <%
                String rowToMatch = "";
                String trClass = "";
                String selected = "";

                if (agentLicenseVOs != null) {

                    for (int a = 0; a < agentLicenseVOs.length; a++) {

                        if (!agentLicenseVOs[a].getVoShouldBeDeleted())
                        {
                            String sLicensePK = agentLicenseVOs[a].getAgentLicensePK() + "";
                            String sState = Util.initString(agentLicenseVOs[a].getStateCT(), "");
                            String sProduct = Util.initString(agentLicenseVOs[a].getProductTypeCT(), "");
                            String sLicenseNumber = Util.initString(agentLicenseVOs[a].getLicenseNumber(), "");
                            String sLicenseType = Util.initString(agentLicenseVOs[a].getLicenseTypeCT(), "");
                            String sStatus = Util.initString(agentLicenseVOs[a].getStatusCT(), "");

                            rowToMatch =  sLicenseNumber;

                            if (rowToMatch.equals(rowToMatchBase)) {

                                trClass = "highLighted";
                                selected = "true";
                            }
                            else {

                                trClass = "dummy";
                                selected="false";
                            }
			%>
			<tr class="<%= trClass %>" isSelected="<%= selected %>" id="<%= sLicensePK %>"
                onClick="selectRow()" onMouseOver="highlightRow()" onMouseOut="unhighlightRow()">
			  <td width="20%" nowrap>
				<%= sState %>
			  </td>
			  <td width="20%" nowrap>
				<%= sProduct %>
			  </td>
			  <td width="20%" nowrap>
				<%= sLicenseNumber %>
			  </td>
			  <td width="20%" nowrap>
			 	<%= sLicenseType %>
			  </td>
			  <td width="20%" nowrap>
				<%= sStatus %>
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
 <input type="hidden" name="agentLicensePK" value="<%= agentLicensePK %>">

</body>
</html>