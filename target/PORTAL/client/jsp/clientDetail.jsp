<!-- ****** JAVA CODE ***** //-->

<%@ page  errorPage="/common/jsp/error.jsp" autoFlush="true" %>
<%@ page import="java.util.*,
                 edit.common.*,
                 edit.common.vo.*,
                 edit.common.vo.user.*,
                 fission.beans.*,
                 edit.common.exceptions.VOEditException,
                 edit.portal.exceptions.PortalEditingException,
                  edit.portal.common.session.UserSession,
                 edit.services.config.ServicesConfig,
                 fission.utility.*,
                 engine.*" %>

<jsp:useBean id="clientDetailSessionBean"
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

//    String quickAddLogout = (String) request.getAttribute("quickAddLogout");
//    if (quickAddLogout == null) {
//
//        quickAddLogout = "";
//    }

    String clientMessage  = (String) request.getAttribute("clientMessage");
    if (clientMessage == null) {

        clientMessage = "";
    }

    String errorMessage = (String) request.getAttribute("errorMessage");
    if (errorMessage == null) {

        errorMessage = "";
    }

    CodeTableWrapper codeTableWrapper = CodeTableWrapper.getSingleton();

    CodeTableVO[] genders           = codeTableWrapper.getCodeTableEntries("GENDER");
    CodeTableVO[] trustTypes        = codeTableWrapper.getCodeTableEntries("TRUSTTYPE");
    CodeTableVO[] clientStatuses    = codeTableWrapper.getCodeTableEntries("CLIENTSTATUS");
    CodeTableVO[] taxTypes          = codeTableWrapper.getCodeTableEntries("TAXIDTYPE");
    CodeTableVO[] states            = codeTableWrapper.getCodeTableEntries("STATE");
    CodeTableVO[] yesNo             = codeTableWrapper.getCodeTableEntries("YESNO");
    CodeTableVO[] sicCodes          = codeTableWrapper.getCodeTableEntries("SICCODE");

    Company[] companies = Company.find_All_CompaniesForProductType();

    PageBean pageBean = clientDetailSessionBean.getPageBean("pageBean");

    String stateOfDeathId	    = pageBean.getValue("stateOfDeathId");
    String residentStateAtDeathId	= pageBean.getValue("residentStateAtDeathId");
	String genderId  	    = pageBean.getValue("genderId");
	String trustTypeId    	= pageBean.getValue("trustTypeId");
    String clientDetailPK   = pageBean.getValue("clientDetailPK");
	String clientId         = pageBean.getValue("clientId");
    String contactInfoIndStatus = pageBean.getValue("contactInfoIndStatus");

	String taxId            = Util.initString(pageBean.getValue("taxId"), "");
    String taxTypeId        = pageBean.getValue("taxTypeId");
	String namePrefix     	= pageBean.getValue("namePrefix");
	String nameSuffix     	= pageBean.getValue("nameSuffix");
	String firstName      	= pageBean.getValue("firstName");
	String middleName     	= pageBean.getValue("middleName");
	String lastName       	= pageBean.getValue("lastName");
	String corporateName  	= pageBean.getValue("corporateName");
    String dateOfBirth      = pageBean.getValue("dateOfBirth");
    
    String privacyIndStatus = pageBean.getValue("privacyIndStatus");

    String sicCode          = pageBean.getValue("sicCode");
    String sicCodeDescription = "";
    if (sicCode != null)
    {
        for (int i = 0; i < sicCodes.length; i++)
        {
            if (sicCodes[i].getCode().equals(sicCode))
            {
                sicCodeDescription = sicCodes[i].getCodeDesc();
                break;
            }
        }
    }


    String status               = pageBean.getValue("status");
	String lastChangeDateTime   = pageBean.getValue("lastChangeDateTime");
	String lastChangeOperator   = pageBean.getValue("lastChangeOperator");
	String mothersMaidenName    = pageBean.getValue("mothersMaidenName");
	String occupation           = pageBean.getValue("occupation");
    String dateOfDeath          = Util.initString(pageBean.getValue("dateOfDeath"), DateTimeUtil.formatYYYYMMDDToMMDDYYYY(EDITDate.DEFAULT_MAX_DATE));
        
    String proofOfDeath         = Util.initString(pageBean.getValue("proofOfDeath"), DateTimeUtil.formatYYYYMMDDToMMDDYYYY(EDITDate.DEFAULT_MAX_DATE));
    String companyFK            = Util.initString(pageBean.getValue("companyFK"), "0");

    ClientRoleVO clientRoleVO = (ClientRoleVO)session.getAttribute("clientRoleVO");

    String newIssuesEligibilityStatusId = "";
    String eligibilityStartDate      = "";

    if (clientRoleVO != null)
    {
        newIssuesEligibilityStatusId = Util.initString(clientRoleVO.getNewIssuesEligibilityStatusCT(), "");
        eligibilityStartDate = DateTimeUtil.formatYYYYMMDDToMMDDYYYY(clientRoleVO.getNewIssuesEligibilityStartDate());
    }
    else
    {
        newIssuesEligibilityStatusId = Util.initString(pageBean.getValue("eligibilityStatusId"), "");
        eligibilityStartDate = pageBean.getValue("eligibilityStartDate");
    }

    String lastOFACCheckDate = pageBean.getValue("lastOFACCheckDate");

    UserSession userSession = (UserSession) session.getAttribute("userSession");

     String bypassOFAC  = getOFACValue(pageBean);
%>
<%!
    /**
     * Defaults to the configured value of OFACValue, unless the user has overridden this value.
     * @param pageBean
     * @return
     */
    private String getOFACValue(PageBean pageBean)
    {
        String bypassOFAC = Util.initString(pageBean.getValue("bypassOFAC"), null);

        if (bypassOFAC == null)
        {
            String configuredBypassOFAC = ServicesConfig.getOFACConfig().getOFACCheck();

            if (configuredBypassOFAC.equals("true"))
            {
                bypassOFAC = "unchecked";
            }
            if (configuredBypassOFAC.equals("false"))
            {
                bypassOFAC = "checked";
            }
        }

        return bypassOFAC;
    }
%>

<html>

<script src="/PORTAL/common/javascript/formatDateFunctions.js"></script>
<script src="/PORTAL/common/javascript/calendarSelector.js"></script>
<script src="/PORTAL/common/javascript/commonJavascriptFunctions.js"></script>

<script language="JavaScript1.2">

<!--******* JAVASCRIPT ******//-->

var f = null;

var shouldShowLockAlert = true;

<%--var quickAddLogout = "<%= quickAddLogout %>";--%>
var errorMessage = "<%= errorMessage %>";
var clientMessage = "<%= clientMessage %>";
var tabLayers = new Array();
var isDragging = false;
var voEditExceptionExists = "<%= voEditExceptionExists %>";
var editingExceptionExists = "<%= editingExceptionExists %>";
var sicCodes = new Array(<%= sicCodes.length %>);
var status = "<%= status %>";
var dateOfDeath = "<%= dateOfDeath %>";


function init() {

	top.frames["main"].setActiveTab("mainTab");

	f = document.clientDetailForm;

    initializeSICCodes();

<%--    if (quickAddLogout == "true") {--%>
<%----%>
<%--        top.frames["header"].closeQuickAdd();--%>
<%--    }--%>
<%----%>
<%--    else {--%>

        if (clientMessage != "")
        {
            alert(clientMessage);
        }

        if (errorMessage != "")
        {
            alert(errorMessage);
        }

        var clientIsLocked = <%= userSession.getClientDetailIsLocked()%>;
        var username = "<%= userSession.getUsername() %>";
        var elementPK = "<%= userSession.getClientDetailPK() %>";
        top.frames["header"].updateLockState(clientIsLocked, username, elementPK);

        shouldShowLockAlert = !clientIsLocked;

        for (var i = 0; i < f.elements.length; i++) {

            var elementType = f.elements[i].type;

            if ( (elementType == "text" || elementType == "button" || elementType == "select-one") &&
                 (shouldShowLockAlert == true) ) {

                f.elements[i].onclick = showLockAlert;
                f.elements[i].onkeydown = showLockAlert;
            }
        }

        if (status == "Deceased" || status == "DeceasedPending")
        {
            f.status.disabled = false;
        }
        else
        {
            f.status.disabled = true;
        }
<%-- } --%>
}

function initializeSICCodes()
{
    <%
        for (int i = 0; i < sicCodes.length; i++)
        {
    %>
            sicCodes[<%= i %>] = new Object();
            sicCodes[<%= i %>].code = "<%= sicCodes[i].getCode() %>";
            sicCodes[<%= i %>].desc = "<%= sicCodes[i].getCodeDesc() %>";
    <%
        }
    %>
}

function showLockAlert(){

    if (shouldShowLockAlert == true) {

        alert("The Client can not be edited.");

        return false;
    }
}

function contactInfo()
{
    var width = .90 * screen.width;
    var height = .55 * screen.height;

    openDialog("contactInfoDialog", "top=10,left=10,resizable=yes", width, height);

    sendTransactionAction("ClientDetailTran", "showContactInfoDialog", "contactInfoDialog");
}

function showDOBGenderChangeDialog()
{
    if (shouldShowLockAlert == true)
    {
        alert("The Client can not be edited.");

        return;
    }
    else
    {
        var width = 0.50 * screen.width;
        var height = 0.20 * screen.height;

        openDialog("dobGenderChangeDialog", "top=0,left=0,resizable=no", width, height);

        sendTransactionAction("ClientDetailTran", "showDOBGenderChangeDialog", "dobGenderChangeDialog");
    }
}

	function showClientTaxInformation()
    {

        var width = 0.80 * screen.width;
        var height = 0.40 * screen.height;

        openDialog("clientTaxInformation", "top=0,left=0,resizable=no", width, height);

		sendTransactionAction("ClientDetailTran", "showClientTaxInformation", "clientTaxInformation");
    }

	function showClientPreferenceInformation()
    {

        var width = 0.80 * screen.width;
        var height = 0.90 * screen.height;

        openDialog("preferences", "top=0,left=0,resizable=no", width, height);
		sendTransactionAction("ClientDetailTran", "showClientPreferences", "preferences");
	}


<%--function openDialog(theURL,winName,features) {--%>
<%----%>
<%--	dialog = window.open(theURL,winName,features);--%>
<%--}--%>

function checkForEditingException(){

    if (editingExceptionExists == "true"){

        openDialog("exceptionDialog", "top=0,left=0,resizable=no,width=" + .75 * screen.width + ",height=" + screen.height/3);
	    sendTransactionAction("ClientDetailTran", "showEditingExceptionDialog", "exceptionDialog");
    }
}

function checkForVOEditException(){

    if (voEditExceptionExists == "true") {

        openDialog("voEditExceptionDialog", "top=0,left=0,resizable=no,width=" + .75 * screen.width + ",height=" + screen.height/3);
	    sendTransactionAction("ClientDetailTran", "showVOEditExceptionDialog", "voEditExceptionDialog");
    }
}

function continueTransactionAction(transaction, action){

    f.ignoreEditWarnings.value = "true";
    sendTransactionAction(transaction, action, "contentIFrame");
}

function sendTransactionAction(transaction, action, target) {

    if (f.privacyInd.checked == true) {

        f.privacyIndStatus.value = "checked";
    }

	f.transaction.value=transaction;
	f.action.value=action;

	f.target = target;
    setByPassValue();
    f.submit();
}

function setByPassValue()
{
  if ( f.bypassOFAC.checked == true)
  {
     f.bypassOFAC.value = "checked";
  }
  else
  {
        f.bypassOFAC.value = "unchecked";
  }
}

function setSICCodeDescription()
{
    selectedCode = f.sicCode.options[f.sicCode.selectedIndex].text;

    if (selectedCode == "Please Select")
    {
        f.sicCodeDescription.value = "";
    }
    else
    {
        for (var i = 0; i < sicCodes.length; i++)
        {
            if (sicCodes[i].code == selectedCode)
            {
                f.sicCodeDescription.value = sicCodes[i].desc;
            }
        }
    }
}

</script>
<head>
<title>Client Detail</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta http-equiv="Cache-Control" content="no-store">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Expires" content="0">

<link href="/PORTAL/common/css/PORTAL.css" rel="stylesheet" type="text/css">
</head>

<!-- ****** HTML CODE ***** //-->

<body class="mainTheme" onLoad="init(); checkForEditingException(); checkForVOEditException()" style="border-style:solid; border-width:1;">
<form name="clientDetailForm" method="post" action="/PORTAL/servlet/RequestManager">

<span id="mainContent" style="position:relative; width:100%; height:100%; top:0; left:0; z-index:0; overflow:visible">
<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td align="right" nowrap>Type:&nbsp;</td>
    <td align="left" nowrap>
      <select name="trustTypeId">
        <option>Please Select</option>
          <%
            for(int i = 0; i < trustTypes.length; i++) {

                String codeTablePK = trustTypes[i].getCodeTablePK() + "";
                String codeDesc    = trustTypes[i].getCodeDesc();
                String code        = trustTypes[i].getCode();

                if (trustTypeId.equalsIgnoreCase(code)) {

                    out.println("<option selected name=\"id\" value=\"" + codeTablePK + "\">" + codeDesc + "</option>");
                }
                else  {

                    out.println("<option name=\"id\" value=\"" + codeTablePK + "\">" + codeDesc + "</option>");
                }
            }
          %>
      </select>
    </td>
    <td align="right" nowrap colspan="2">&nbsp;</td>
  </tr>


  <tr>
    <td align="right" nowrap>Tax Id Type:&nbsp;</td>
    <td align="left" nowrap colspan="2">
      <select name="taxTypeId">
        <option>Please Select</option>
          <%
              for(int i = 0; i < taxTypes .length; i++) {

                  String codeTablePK = taxTypes[i].getCodeTablePK() + "";
                  String codeDesc    = taxTypes[i].getCodeDesc();
                  String code        = taxTypes[i].getCode();

                  if (taxTypeId.equalsIgnoreCase(code)) {

                      out.println("<option selected name=\"id\" value=\"" + codeTablePK + "\">" + codeDesc + "</option>");
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
    <td align="right" nowrap>Tax Identification:&nbsp;</td>
    <td align="left" nowrap>
      <input type="text" name="taxId" maxlength="11" size="11" value="<%= taxId %>">
    </td>
    </td>
    <td align="right" nowrap>
      <a name="contactLink" href ="javascript:contactInfo()">Contact Information&nbsp;</a>
      <input type="checkbox" name="contactInfoIndStatus" <%= contactInfoIndStatus %>>
    </td>
    <td align="left" nowrap>&nbsp;</td>
  </tr>
  <tr>
  </tr>

  <tr>
    <td align="right" nowrap>Name Prefix:&nbsp;</td>
    <td align="left" nowrap>
      <input type="text" name="namePrefix" maxlength="5"  size="5" value="<%= namePrefix %>">
    </td>
    <td align="right" nowrap>Name Suffix:&nbsp;</td>
    <td align="left" nowrap colspan="2">
      <input type="text" name="nameSuffix" maxlength="4" size="4" value="<%= nameSuffix %>">
    </td>
  </tr>

  <tr>
    <td align="right" nowrap>First Name:&nbsp;</td>
    <td align="left" nowrap>
      <input type="text" name="firstName" maxlength="15" size="15" value="<%= firstName %>">
    </td>
    <td align="right" nowrap>MiddleName:&nbsp;</td>
    <td align="left" nowrap colspan="2">
      <input type="text" name="middleName" maxlength="15" size="15"	value="<%=middleName%>">
    </td>
  </tr>

  <tr>
    <td align="right" nowrap>Last Name:&nbsp;</td>
    <td align="left" nowrap>
      <input type="text" name="lastName" maxlength="30" size="20" value="<%= lastName%>">
    </td>
    <td align="right" nowrap>Privacy Indicator
      <input type="checkbox" name="privacyInd" <%= privacyIndStatus %>>
    </td>
    <td align="left" nowrap colspan="2">
      &nbsp;
    </td>
  </tr>

  <tr>
    <td align="right" nowrap>Corporate Name:&nbsp;</td>
    <td align="left" nowrap>
      <input type="text" name="corporateName" maxlength="60" size="70" value="<%= corporateName%>">
    </td>
    <td align="right" nowrap>Company Name:&nbsp;</td>
    <td align="left" nowrap>
          <select name="companyFK">
            <option value="null">Please Select</option>
            <%
                for(int i=0; i< companies.length; i++)
                {
                    Long currentCompanyPK = companies[i].getCompanyPK();
                    String companyName = companies[i].getCompanyName();

                    if (currentCompanyPK.longValue() == Long.parseLong(companyFK))
                    {
                        out.println("<option selected value=\""+ currentCompanyPK + "\">" + companyName + "</option>");
                    }
                    else
                    {
                        out.println("<option value=\""+ currentCompanyPK + "\">" + companyName + "</option>");
                    }
                }
            %>
          </select>
     </td>
    <td align="left" nowrap colspan="2">
      &nbsp;
    </td>
  </tr>
  <tr>
    <td align="right" nowrap>SIC Code:&nbsp;</td>
    <td align="left" nowrap colspan="3">
      <select name="sicCode" onchange="setSICCodeDescription()">
        <option>Please Select</option>
          <%
              for(int i = 0; i < sicCodes .length; i++) {

                  String codeTablePK = sicCodes[i].getCodeTablePK() + "";
                  String code        = sicCodes[i].getCode();

                  if (sicCode.equalsIgnoreCase(code)) {

                      out.println("<option selected name=\"id\" value=\"" + codeTablePK + "\">" + code + "</option>");
                  }
                  else  {

                      out.println("<option name=\"id\" value=\"" + codeTablePK + "\">" + code + "</option>");
                  }
              }
          %>
      </select>
      &nbsp;&nbsp;&nbsp;
      <input disabled type="text" name="sicCodeDescription" maxlength="75" size="75" value="<%= sicCodeDescription%>">
    </td>
  </tr>
  <tr>
    <td align="right" nowrap>Date of Birth:&nbsp;</td>
    <td align="left" nowrap>
            <input disabled type="text" name="dateOfBirth" value="<%= dateOfBirth %>" size='10' maxlength="10" onBlur="DateFormat(this,this.value,event,true)">
    </td>
    <td align="right" nowrap>Date of Death:&nbsp;</td>
    <td align="left" nowrap>
            <input type="text" name="dateOfDeath" value="<%= dateOfDeath %>" size='10' maxlength="10" onKeyUp="DateFormat(this,this.value,event,false)" onBlur="DateFormat(this,this.value,event,true)">
            <a href="javascript:show_calendar('f.dateOfDeath', f.dateOfDeath.value);"><img src="/PORTAL/common/images/calendarIcon.gif" width="16" height="16" border="0" alt="Select a date from the calendar"></a>
    </td>
  </tr>

  <tr>
    <td align="right" nowrap>Proof Of Death Received Date:&nbsp;</td>
    <td align="left" nowrap>
            <input type="text" name="proofOfDeath" value="<%= proofOfDeath %>" size='10' maxlength="10" onKeyUp="DateFormat(this,this.value,event,false)" onBlur="DateFormat(this,this.value,event,true)">
            <a href="javascript:show_calendar('f.proofOfDeath', f.proofOfDeath.value);"><img src="/PORTAL/common/images/calendarIcon.gif" width="16" height="16" border="0" alt="Select a date from the calendar"></a>
    </td>
    <td align="right" nowrap colspan="2">&nbsp;</td>
  </tr>

  <tr>
    <td align="right" nowrap>Gender Type:&nbsp;</td>
    <td align="left" nowrap disabled>
      <select name="genderId">
        <option>Please Select</option>
          <%
            for(int i = 0; i < genders.length; i++) {

                String codeTablePK = genders[i].getCodeTablePK() + "";
                String codeDesc    = genders[i].getCodeDesc();
                String code        = genders[i].getCode();

                if (genderId.equalsIgnoreCase(code)) {

                    out.println("<option selected name=\"id\" value=\"" + code + "\">" + codeDesc + "</option>");
                }
                else  {

                    out.println("<option name=\"id\" value=\"" + code + "\">" + codeDesc + "</option>");
                }
            }
          %>
      </select>
    </td>
    <td align="right" nowrap>Status:&nbsp;</td>
    <td align="left" nowrap colspan="2">
      <select name="status">
        <option>Please Select</option>
          <%
            for(int i = 0; i < clientStatuses.length; i++) {

                String codeTablePK = clientStatuses[i].getCodeTablePK() + "";
                String codeDesc    = clientStatuses[i].getCodeDesc();
                String code        = clientStatuses[i].getCode();

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
  </tr>

  <tr>
    <td align="right" nowrap>Occupation:&nbsp;</td>
    <td align="left" nowrap>
      <input type="text" name="occupation" maxlength="20" size="20" value="<%= occupation %>">
    </td>
    <td align="right" nowrap>Mother's Maiden Name:&nbsp;</td>
    <td align="left" nowrap colspan="2">
      <input type="text" name="mothersMaidenName" maxlength="20" size="20" value="<%= mothersMaidenName %>">
    </td>
  </tr>

  <tr>
    <td align="right" nowrap>State of Death:&nbsp;</td>
    <td disabled align="left" nowrap>
      <select name="stateOfDeathId">
        <option>Please Select</option>
          <%

            for(int i = 0; i < states.length; i++) {

                String codeTablePK = states[i].getCodeTablePK() + "";
                String codeDesc    = states[i].getCodeDesc();
                String code        = states[i].getCode();

                if (stateOfDeathId.equalsIgnoreCase(code)) {

                    out.println("<option selected name=\"id\" value=\"" + codeTablePK+ "\">" + codeDesc + "</option>");
                }
                else  {

                    out.println("<option name=\"id\" value=\"" + codeTablePK + "\">" + codeDesc + "</option>");
                }
            }
          %>
      </select>
    </td>
    <td align="right" nowrap>Resident State At Death:&nbsp;</td>
    <td disabled align="left" nowrap>
      <select name="residentStateAtDeathId">
        <option>Please Select</option>
          <%

            for(int i = 0; i < states.length; i++) {

                String codeTablePK = states[i].getCodeTablePK() + "";
                String codeDesc    = states[i].getCodeDesc();
                String code        = states[i].getCode();

                if (residentStateAtDeathId.equalsIgnoreCase(code)) {

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
    <td align="right" nowrap>Last Change Time &amp; Date:&nbsp;</td>
    <td align="left" nowrap>
      <input type="text" disabled name="lastChangeDateTime" maxlength="35" size="35" value="<%=lastChangeDateTime %>">
    </td>
    <td align="right" nowrap>Last Change Operator:&nbsp;</td>
    <td align="left" nowrap colspan="2">
      <input type="text" disabled name="lastChangeOperator" size="10" maxlength="10" value="<%= lastChangeOperator %>">
    </td>
  </tr>
  <tr>
    <td align="right" nowrap>Last OFAC Check Date:&nbsp;</td>
    <td align="left" nowrap>
      <input type="text" disabled name="lastOFACCheckDate" maxlength="10" size="10" value="<%= lastOFACCheckDate %>">
    </td>
    <td align="right" nowrap>Bypass OFAC Check
  <input type="checkbox" name="bypassOFAC" value="<%= bypassOFAC %>" <%= bypassOFAC %> onClick="setByPassValue();">
    </td>
    <td align="left" nowrap colspan="2">
      &nbsp;
    </td>
  </tr>
  <tr>
    <td align="right" nowrap>New Issues Eligibility Status:&nbsp;</td>
    <td align="left" nowrap>
      <select name="eligibilityStatusId">
        <option>Please Select</option>
          <%

            for(int i = 0; i < yesNo.length; i++) {

                String codeTablePK = yesNo[i].getCodeTablePK() + "";
                String codeDesc    = yesNo[i].getCodeDesc();
                String code        = yesNo[i].getCode();

                if (newIssuesEligibilityStatusId.equalsIgnoreCase(code)) {

                    out.println("<option selected name=\"id\" value=\"" + code + "\">" + codeDesc + "</option>");
                }
                else  {

                    out.println("<option name=\"id\" value=\"" + code + "\">" + codeDesc + "</option>");
                }
            }
          %>
      </select>
    </td>
    <td align="right" nowrap>New Issues Eligibility Start Date:&nbsp;</td>
    <td align="left" nowrap>
      <input type="text" name="newIssueEligibilityStartDate" size='10' maxlength="10" value="<%= eligibilityStartDate %>" onKeyUp="DateFormat(this,this.value,event,false)" onBlur="DateFormat(this,this.value,event,true)">
      <a href="javascript:show_calendar('f.newIssueEligibilityStartDate', f.newIssueEligibilityStartDate.value);"><img src="/PORTAL/common/images/calendarIcon.gif" width="16" height="16" border="0" alt="Select a date from the calendar"></a>
    </td>
   </tr>
   <tr>
        <td align="right" nowrap>
<%--            <input disabled type="checkbox" name="taxInformationStatus">&nbsp;--%>
            <a href ="javascript:showClientTaxInformation()">Tax Information &nbsp;&nbsp;&nbsp;</a>
        </td>
        <td align="left" nowrap>
<%--            <input disabled type="checkbox" name="preferenceStatus">&nbsp;--%>
            <a href ="javascript:showClientPreferenceInformation()">Preference</a>
        </td>
        <td  align="left" nowrap>
            <a href ="javascript:showDOBGenderChangeDialog()">DOB/Gender Change</a>
        </td>

   </tr>
</table>
</span>
<!-- ****** HIDDEN FIELDS ***** //-->
  	<input type="hidden" name="transaction" value="">
    <input type="hidden" name="action"      value="">
    <input type="hidden" name="clientDetailPK" value="<%= clientDetailPK %>">
    <input type="hidden" name="privacyIndStatus" value="">
    <input type="hidden" name="ignoreEditWarnings" value="">
    <input type="hidden" name="clientId" value="<%= clientId %>">
    <input type="hidden" name="eligibilityStartDate" value="<%= eligibilityStartDate %>">
    <input type="hidden" name="genderId" value="<%= genderId %>">
    <input type="hidden" name="dateOfBirth" value="<%= dateOfBirth %>">

<!-- recordPRASEEvents is set by the toolbar when saving the client -->
    <input type="hidden" name="recordPRASEEvents" value="false">
    
</form>
</body>

</html>
