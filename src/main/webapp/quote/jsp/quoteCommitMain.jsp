<!--
 * User: unknown
 * Date: Jan 1, 2000
 * Time: unknown
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 -->

<%@ page  errorPage="/common/jsp/error.jsp" autoFlush="true" %>
<%@ page import="java.util.*,
                 edit.common.*,
                 edit.common.vo.*,
                 edit.common.vo.user.*,
                 fission.beans.*,
                 edit.portal.exceptions.*,
                 edit.common.exceptions.VOEditException,
                 edit.portal.common.session.UserSession,
                 group.CaseProductUnderwriting,
                 fission.utility.*" %>

<jsp:useBean id="quoteMainSessionBean"
    class="fission.beans.SessionBean" scope="session"/>
<%
    DepositsVO[] depositsVOs = (DepositsVO[]) session.getAttribute("depositsVOs");
    SuspenseVO[] contractSuspenseVOs = (SuspenseVO[]) session.getAttribute("contractSuspenseVOs");
    String quoteMessage = (String) quoteMainSessionBean.getValue("quoteMessage");
    String agentMessage = (String) request.getAttribute("agentMessage");
    if (agentMessage == null)
    {
        agentMessage = "";
    }

    if (quoteMessage == null) {

        quoteMessage = "";
    }
    quoteMainSessionBean.putValue("quoteMessage", "");

    String suspenseMessage = (String) request.getAttribute("suspenseMessage");
    if (suspenseMessage == null)
    {
        suspenseMessage = "";
    }

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

    CodeTableWrapper codeTableWrapper = CodeTableWrapper.getSingleton();

    String message = (String) request.getAttribute("contractNumberMessage");

    if (message == null) {

        message = "";
    }

	PageBean formBean = quoteMainSessionBean.getPageBean("formBean");

    String companyStructureId = Util.initString(formBean.getValue("companyStructureId"), "0");
    String   companyStructure    = formBean.getValue("companyStructure");

    CodeTableVO[]  qualifiedTypes = codeTableWrapper.getCodeTableEntries("QUALIFIEDTYPE", Long.parseLong(companyStructureId));
    CodeTableVO[]  qualNonQuals = codeTableWrapper.getCodeTableEntries("QUALNONQUAL", Long.parseLong(companyStructureId));


    String segmentPK         = formBean.getValue("segmentPK");
    String contractNumber    = formBean.getValue("contractNumber");
    String payoutPK          = formBean.getValue("payoutPK");
//    String policyGroupFK          = formBean.getValue("policyGroupFK");

    String qualifiedType     = formBean.getValue("qualifiedType");
    String qualNonQual       = formBean.getValue("qualNonQual");

	String reduce1       	 = formBean.getValue("reduce1");
	String reduce2       	 = formBean.getValue("reduce2");
	String costBasis     	 = formBean.getValue("costBasis");

    String quoteDate     	 = formBean.getValue("quoteDate");
    String effectiveDate     = formBean.getValue("effectiveDate");

    String applicationSignedDate   	 = formBean.getValue("applicationSignedDate");

    String applicationReceivedDate   	 = formBean.getValue("applicationReceivedDate");

    String startDate     	 = formBean.getValue("startDate");


    String creationDate = formBean.getValue("creationDate");
    String creationOperator = formBean.getValue("creationOperator");

    CodeTableVO[] states = codeTableWrapper.getCodeTableEntries("STATE", Long.parseLong(companyStructureId));
	String   areaId	     = formBean.getValue("areaId");
    String premiumTaxSitusOverride = formBean.getValue("premiumTaxSitusOverride");	

    CodeTableVO[] contractTypes = codeTableWrapper.getCodeTableEntries("CONTRACTTYPE");
    String contractType = formBean.getValue("contractType");

    CodeTableVO[] options = codeTableWrapper.getCodeTableEntries("OPTIONCODE", Long.parseLong(companyStructureId));
	String   optionId	  = formBean.getValue("optionId");

    CodeTableVO[] frequencies = codeTableWrapper.getCodeTableEntries("FREQUENCY", Long.parseLong(companyStructureId));
	String   frequencyId     = formBean.getValue("frequencyId");
        
    
    CodeTableVO[] ratedGenders = codeTableWrapper.getCodeTableEntries("RATEDGENDER", Long.parseLong(companyStructureId));
    String ratedGenderCT     = formBean.getValue("ratedGenderCT");
    
    CaseProductUnderwriting[] ratedGenderValues = null;

    ratedGenderValues = (CaseProductUnderwriting[]) request.getAttribute("ratedGenders");


    String statusCode                          = formBean.getValue("statusCode");
	String scheduledEventsStatus               = formBean.getValue("scheduledEvents");
	String calculatedValuesIndStatus           = formBean.getValue("calculatedValuesIndStatus");
    String notesIndStatus                      = formBean.getValue("notesIndStatus");
	String postJune1986InvestmentIndStatus	   = Util.initString(formBean.getValue("postJune1986InvestmentIndStatus"), "checked");
    String depositsIndStatus                   = "unchecked";
    if (depositsVOs != null && depositsVOs.length > 0)
    {
        depositsIndStatus = "checked";
    }
    String lastDayOfMonthIndStatus             = formBean.getValue("lastDayOfMonthIndStatus");
    String taxesIndStatus                      = formBean.getValue("taxesIndStatus");
    String certainDuration                     = formBean.getValue("certainDuration");
	String finalDistributionAmount             = formBean.getValue("finalDistributionAmount");
	String premiumTaxes                        = formBean.getValue("premiumTaxes");
	String frontEndLoads                       = formBean.getValue("frontEndLoads");
	String totalProjectedAnnuity               = formBean.getValue("totalProjectedAnnuity");
	String exclusionRatio                      = formBean.getValue("exclusionRatio");
	String excessInterest                      = formBean.getValue("excessInterest");
	String yearlyTaxableBenefit                = formBean.getValue("yearlyTaxableBenefit");
	String fees                                = formBean.getValue("fees");
//	String mrdAmount                           = formBean.getValue("mrdAmount");
	String paymentAmount				       = formBean.getValue("paymentAmount");
	String purchaseAmount                      = formBean.getValue("purchaseAmount");
    String faceAmount                          = formBean.getValue("faceAmount");
    String savingsPercent                      = formBean.getValue("savingsPercent");
    String dismembermentPercent                = formBean.getValue("dismembermentPercent");
    String nextPaymentDate                     = formBean.getValue("nextPaymentDate");

    String issueDate                           = formBean.getValue("issueDate");
    String terminatedStatus                    = formBean.getValue("terminatedStatus");
    String ageAtIssue                          = formBean.getValue("ageAtIssue");
    String location 				= formBean.getValue("location");
    String sequence 				= formBean.getValue("sequence");
    String indivAnnPremium = formBean.getValue("indivAnnPremium");    
    String originalStateCT                     = formBean.getValue("originalStateCT");
    String worksheetTypeCT                     = formBean.getValue("worksheetTypeCT");

    String suppOriginalContractNumber = formBean.getValue("suppOriginalContractNumber");
    String casetrackingOptionCT = formBean.getValue("casetrackingOptionCT");

	String showQuoteResults = (String) request.getAttribute("showQuoteResults");
    String analyzeQuote     = (String) request.getAttribute("analyzeQuote");

    UserSession userSession = (UserSession) session.getAttribute("userSession");

    String errorMessage = (String) request.getAttribute("errorMessage");
    if (errorMessage == null)
    {
        errorMessage = "";
    }
%>

<html>

<script src="/PORTAL/common/javascript/commonJavascriptFunctions.js"></script>
<script src="/PORTAL/common/javascript/formatDateFunctions.js"></script>
<script src="/PORTAL/common/javascript/calendarSelector.js"></script>


<script language="Javascript1.2">

    var quoteMessage = "<%= quoteMessage %>";
    var message = "<%= message %>";
    var errorMessage = "<%= errorMessage %>";
    var agentMessage = "<%= agentMessage %>";
    var suspenseMessage = "<%= suspenseMessage %>";
	var showQuoteResults = "<%= showQuoteResults %>";
    var analyzeQuote     = "<%= analyzeQuote %>";
	var finalDistributionAmount = "<%= finalDistributionAmount %>";
	var premiumTaxes = "<%= premiumTaxes %>";
	var frontEndLoads = "<%= frontEndLoads %>";
	var totalProjectedAnnuity = "<%= totalProjectedAnnuity %>";
    var exclusionRatio = "<%= exclusionRatio %>";
	var excessInterest = "<%= excessInterest %>";
	var yearlyTaxableBenefit = "<%= yearlyTaxableBenefit %>";
	var fees = "<%= fees %>";
<%--	var mrdAmount = "<%= mrdAmount %>";--%>
	var paymentAmount	= "<%= paymentAmount %>";
	var purchaseAmount = "<%= purchaseAmount %>";
    var editingExceptionExists = "<%= editingExceptionExists %>";
    var voEditExceptionExists = "<%= voEditExceptionExists %>";
    var segmentPK = "<%= segmentPK %>";

	var dialog = null;
    var height = screen.height;
    var width  = screen.width;

	var f = null;

    var shouldShowLockAlert = true;
    var editableContractStatus = true;
    
	function init()
    {
		f = document.quoteCommitMainForm;

		top.frames["main"].setActiveTab("mainTab");
		
		if(segmentPK == "" || segmentPK==null){
        	top.frames["header"].showSearchDialog();
        }

        if (quoteMessage != "" &&
            quoteMessage != null) {

            alert(quoteMessage);
        }

        if (message != "" &&
            message != null) {

            alert(message);
        }

        if (agentMessage != "" &&
            agentMessage != null) {

            alert(agentMessage);
        }

        if (suspenseMessage != "")
        {
            alert(suspenseMessage);
        }

        if (errorMessage != "")
        {
            alert("<%= errorMessage %>");
        }

		checkForQuoteReturnValues();

        var contractIsLocked = <%= userSession.getSegmentIsLocked()%>;
        var username = "<%= userSession.getUsername() %>";
        var elementPK = "<%= userSession.getSegmentPK() %>";
		top.frames["header"].updateLockState(contractIsLocked, username, elementPK);

        shouldShowLockAlert = !contractIsLocked;

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


    function showLockAlert()
    {
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

    function checkForEditingException()
    {
        if (editingExceptionExists == "true")
        {
            var width = .75 * screen.width;
            var height = screen.height/3;

            openDialog("exceptionDialog", "resizable=no", width, height);

            prepareToSendTransactionAction("QuoteDetailTran", "showEditingExceptionDialog", "exceptionDialog");
        }
    }

    function checkForVOEditException()
    {
        if (voEditExceptionExists == "true")
        {
            var width = .75 * screen.width;
            var height = screen.height/3;

            openDialog("voEditExceptionDialog", "resizable=no", width, height);

            prepareToSendTransactionAction("QuoteDetailTran", "showVOEditExceptionDialog", "voEditExceptionDialog");
        }
    }

	function checkForQuoteReturnValues()
    {
		if (showQuoteResults == "true")
        {
			calculatedValuesAdd();
		}
        else if (analyzeQuote == "true")
        {
            showAnalyzer();
        }
		else
        {
			return;
		}
	}

	function checkForRequiredFields()
    {
		if (f.optionId.value == "Please Select")
        {
			alert("Please select an Annuity Option.");

			return false;
		}

		return true;
	}

    function continueTransactionAction(transaction, action)
    {
        f.ignoreEditWarnings.value = "true";
        prepareToSendTransactionAction(transaction, action, "contentIFrame");
    }

	function prepareToSendTransactionAction(transaction, action, target)
    {
		if (f.scheduledEvents.checked == true) {
			f.scheduledEventsStatus.value = "checked";
		}

		if (f.calculatedValuesInd.checked == true) {
			f.calculatedValuesIndStatus.value = "checked";
		}

		if (f.notesInd.checked == true) {
			f.notesIndStatus.value = "checked";
		}

		if (f.postJune1986InvestmentInd.checked == true) {
			f.postJune1986InvestmentIndStatus.value = "checked";
		}

        if (f.lastDayOfMonthInd.checked == true) {
            f.lastDayOfMonthIndStatus.value = "checked";
        }

        if (f.taxesInd.checked == true) {
            f.taxesIndStatus.value = "checked";
        }

		f.finalDistributionAmount.value = finalDistributionAmount;
		f.premiumTaxes.value = premiumTaxes;
		f.frontEndLoads.value = frontEndLoads;
		f.totalProjectedAnnuity.value = totalProjectedAnnuity;
        f.exclusionRatio.value = exclusionRatio;
		f.excessInterest.value = excessInterest;
		f.yearlyTaxableBenefit.value = yearlyTaxableBenefit;
		f.fees.value = fees;
<%--		f.mrdAmount.value = mrdAmount;--%>
		f.paymentAmount.value = paymentAmount;
		f.purchaseAmount.value = purchaseAmount;

        testForDates();

            sendTransactionAction(transaction, action, target);
        }

    function testForDates()
    {
        if (f.applicationSignedDate.value == "")
        {
            alert("Application Signed Date Not Entered");
        }

        if (f.applicationReceivedDate.value == "")
        {
            alert("Application Received Date Not Entered");
        }
	}

    function showContractSuspenseCreation()
    {
        var width = .99 * screen.width;
        var height = .70 * screen.height;

        testForDates();

            openDialog("contractSuspenseCreation", "top=0,left=0,resizable=no", width, height);

            prepareToSendTransactionAction("QuoteDetailTran", "showContractSuspenseCreation", "contractSuspenseCreation");
        }

	function notesAdd()
    {
        var width = .99 * screen.width;
        var height = .90 * screen.height;

        testForDates();

            openDialog("notesAdd", "top=0,left=0,resizable=no", width, height);

            prepareToSendTransactionAction("QuoteDetailTran", "showNotesDialog", "notesAdd");
        }

	function calculatedValuesAdd()
    {
        var width = .65 * screen.width;
        var height = .50 * screen.height;

        testForDates();

            openDialog("calculatedValuesAdd", "top=0,left=0,resizable=no", width, height);

            prepareToSendTransactionAction("QuoteDetailTran", "showCalculatedValuesAdd", "calculatedValuesAdd");
        }

	function showTaxesDialog()
    {
        var width = .50 * screen.width;
        var height = .32 * screen.height;

        testForDates();

            openDialog("taxesDialog", "resizable=no", width, height);

            prepareToSendTransactionAction("QuoteDetailTran", "showTaxesDialog", "taxesDialog");
        }

	function showDepositDialog()
    {
        var width = .60 * screen.width;
        var height = .90 * screen.height;

        testForDates();

            openDialog("depositDialog", "top=0,left=0,resizable=yes", width, height);

            prepareToSendTransactionAction("QuoteDetailTran", "showDepositDialog", "depositDialog");
        }

	function showAnalyzer()
    {
        var width = screen.width;
        var height = screen.height;

        testForDates();

            openDialog("analyzeQuote", "left=0,top=0,resizable=yes", width, height);

            prepareToSendTransactionAction("QuoteDetailTran", "showAnalyzer", "analyzeQuote");
        }

</script>

<head>
<title>New Business Main</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta http-equiv="Cache-Control" content="no-store">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Expires" content="0">

<link href="/PORTAL/common/css/EDITSolutions.css" rel="stylesheet" type="text/css">
</head>

<!-- ****** HTML CODE ***** //-->

<body class="mainTheme" onLoad="init(); checkForEditingException(); checkForVOEditException()" style="border-style:solid; border-width:1;">
<form name= "quoteCommitMainForm" method="post" action="/PORTAL/servlet/RequestManager">

<input type="hidden" name="page" value="">
<jsp:include page="quoteInfoHeader.jsp" flush="true"/>

<span id="mainContent" style="position:relative; width:100%; height:80%; top:0; left:0; z-index:0; overflow:visible">
  <table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>

     <td align="left" nowrap>Key:&nbsp;
		<input type="text" id="companyStructure" name="companyStructure" value="<%= companyStructure %>" size="40" maxlength="75" disabled>
      </td>

      <td align="left" nowrap>Annuity Option:&nbsp;
         <select name="optionId" tabindex="1">
          <option selected value="Please Select">Please Select</option>
            <%
                  for(int i = 0; i < options.length; i++) {

                      String codeTablePK = options[i].getCodeTablePK() + "";
                      String codeDesc    = options[i].getCodeDesc();
                      String code        = options[i].getCode();

                      if (optionId.equalsIgnoreCase(code)) {

                          out.println("<option selected name=\"id\" value=\"" + codeTablePK+ "\">" + codeDesc + "</option>");
                      }
                      else  {

                          out.println("<option name=\"id\" value=\"" + codeTablePK + "\">" + codeDesc + "</option>");
                      }
                   }

            %>
        </select>
      </td>
      <td align="left" nowrap>Contract Type:&nbsp;
        <select name="contractType" tabindex="2">
          <option value="null">Please Select</option>
          <%
              for(int i = 0; i < contractTypes.length; i++) {

                  String codeDesc    = contractTypes[i].getCodeDesc();
                  String code        = contractTypes[i].getCode();

                 if (contractType.equalsIgnoreCase(code)) {

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
      <td nowrap align="left">Issue State:&nbsp;
        <select name="areaId" tabindex="3">
          <option> Please Select </option>
          <%

              for(int i = 0; i < states.length; i++) {

                  String codeDesc    = states[i].getCodeDesc();
                  String code        = states[i].getCode();

                 if (areaId.equalsIgnoreCase(code)) {

                     out.println("<option selected name=\"id\" value=\"" + code + "\">" + codeDesc + "</option>");
                 }
                 else  {

                     out.println("<option name=\"id\" value=\"" + code + "\">" + codeDesc + "</option>");
                 }
              }

		   %>
        </select>
      </td>
      <td nowrap align="left">Reduce %1:&nbsp;
        <input type="text" name="reduce1" size="7" tabindex="4" value="<%= reduce1%>" maxlength="9">
      </td>
      <td nowrap align="left">Reduce%2:&nbsp;
        <input type="text" name="reduce2" size="7" tabindex="5" value="<%= reduce2%>" maxlength="9">
      </td>
    </tr>
    <tr>
      <td nowrap align="left">Cost Basis $:&nbsp;
        <input type="text" name="costBasis" size="9" tabindex="6" value="<%= costBasis%>" maxlength="20" CURRENCY>
      </td>
      <td nowrap align="left">Certain Period:&nbsp;
        <input type="text" name="certainDuration" size="2" tabindex="7" value="<%= certainDuration %>" maxlength="2">
      </td>
      <td nowrap align="left">Frequency:&nbsp;
        <select name="frequencyId" tabindex="8">
          <option> Please Select </option>
          <%
              for(int i = 0; i < frequencies.length; i++) {

                  String codeTablePK = frequencies[i].getCodeTablePK() + "";
                  String codeDesc    = frequencies[i].getCodeDesc();
                  String code        = frequencies[i].getCode();

                 if (frequencyId.equalsIgnoreCase(code)) {

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
      <td nowrap align="left">Purchase Amount:&nbsp;
        <input type="text" name="purchaseAmount" tabindex="9" value="<%= purchaseAmount%>" maxlength="20" CURRENCY>
      </td>
      <td nowrap align="left" colspan="2">Payment Amount:&nbsp;
        <input type="text" name="paymentAmount" tabindex="10" value="<%= paymentAmount%>" maxlength="20" CURRENCY>
      </td>
      <td align="left" colspan="2">Rated Gender:&nbsp;
          <select  name="ratedGenderCT">
              <option value="Please Select">Please Select</option>
              <%
                  if (ratedGenderValues != null && ratedGenderValues.length > 0) {
                      
                      for (int i = 0; i < ratedGenderValues.length; i++) {
                          String value = ratedGenderValues[i].getValue();
                            out.println("<option selected name=\"id\" value=\"" + value + "\">" + value + "</option>");
                      }
                  } else {
                      for (int i = 0; i < ratedGenders.length; i++) {
                          String codeTablePK = ratedGenders[i].getCodeTablePK() + "";
                          String codeDesc = ratedGenders[i].getCodeDesc();
                          String code = ratedGenders[i].getCode();
                              
                          if (ratedGenderCT.equalsIgnoreCase(code)) {
                              out.println("<option selected name=\"id\" value=\"" + code + "\">" + codeDesc + "</option>");
                          } else {
                              out.println("<option name=\"id\" value=\"" + code + "\">" + codeDesc + "</option>");
                          }
                      }
                  }
              %>
      </td>

    </tr>
    <tr>
      <td nowrap align="left">Qual/NonQual:&nbsp;
        <select name="qualNonQual" tabindex="11">
          <option> Please Select </option>
          <%
              for(int i = 0; i < qualNonQuals.length; i++) {

                  String codeTablePK = qualNonQuals[i].getCodeTablePK() + "";
                  String codeDesc    = qualNonQuals[i].getCodeDesc();
                  String code        = qualNonQuals[i].getCode();

                 if (qualNonQual.equalsIgnoreCase(code)) {

                     out.println("<option selected name=\"id\" value=\"" + codeTablePK+ "\">" + codeDesc + "</option>");
                 }
                 else  {

                     out.println("<option name=\"id\" value=\"" + codeTablePK + "\">" + codeDesc + "</option>");
                 }
              }
          %>
        </select>
      </td>
      <td nowrap align="left" colspan="2">Qualified Type:&nbsp;
        <select name="qualifiedType" tabindex="12">
          <option> Please Select </option>
          <%
              for(int i = 0; i < qualifiedTypes.length; i++) {

                  String codeTablePK = qualifiedTypes[i].getCodeTablePK() + "";
                  String codeDesc    = qualifiedTypes[i].getCodeDesc();
                  String code        = qualifiedTypes[i].getCode();

                 if (qualifiedType.equalsIgnoreCase(code)) {

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
      <td align="left" nowrap>Application Signed Date:&nbsp;
        <input type="text" name="applicationSignedDate" value="<%= applicationSignedDate %>" size='10' maxlength="10" onKeyUp="DateFormat(this,this.value,event,false)" onBlur="DateFormat(this,this.value,event,true)">
        <a href="javascript:show_calendar('f.applicationSignedDate', f.applicationSignedDate.value);"><img src="/PORTAL/common/images/calendarIcon.gif" width="16" height="16" border="0" alt="Select a date from the calendar"></a>
      </td>
      <td align="left" nowrap>Application Received Date:&nbsp;
        <input type="text" name="applicationReceivedDate" value="<%= applicationReceivedDate %>" size='10' maxlength="10" onKeyUp="DateFormat(this,this.value,event,false)" onBlur="DateFormat(this,this.value,event,true)">
        <a href="javascript:show_calendar('f.applicationReceivedDate', f.applicationReceivedDate.value);"><img src="/PORTAL/common/images/calendarIcon.gif" width="16" height="16" border="0" alt="Select a date from the calendar"></a>
      </td>
      <td align="left" nowrap>Quote Date:&nbsp;
        <input type="text" name="quoteDate" value="<%= quoteDate %>" size='10' maxlength="10" onKeyUp="DateFormat(this,this.value,event,false)" onBlur="DateFormat(this,this.value,event,true)">
        <a href="javascript:show_calendar('f.quoteDate', f.quoteDate.value);"><img src="/PORTAL/common/images/calendarIcon.gif" width="16" height="16" border="0" alt="Select a date from the calendar"></a>
      </td>
    </tr>
    <tr>
      <td align="left" nowrap>Effective Date:&nbsp;
        <input type="text" name="effectiveDate" value="<%= effectiveDate %>" size='10' maxlength="10" onKeyUp="DateFormat(this,this.value,event,false)" onBlur="DateFormat(this,this.value,event,true)">
        <a href="javascript:show_calendar('f.effectiveDate', f.effectiveDate.value);"><img src="/PORTAL/common/images/calendarIcon.gif" width="16" height="16" border="0" alt="Select a date from the calendar"></a>
      </td>
      <td align="left" nowrap colspan="2">Start Date:&nbsp;
        <input type="text" name="startDate" value="<%= startDate %>" size='10' maxlength="10" onKeyUp="DateFormat(this,this.value,event,false)" onBlur="DateFormat(this,this.value,event,true)">
        <a href="javascript:show_calendar('f.startDate', f.startDate.value);"><img src="/PORTAL/common/images/calendarIcon.gif" width="16" height="16" border="0" alt="Select a date from the calendar"></a>
      </td>
    </tr>
    <tr>
      <td nowrap>Creation Date:&nbsp;
        <input disabled type="text" name="creationDate" maxlength="10" size="10" value="<%= creationDate %>">
      </td>
	  <td nowrap colspan="2">Creation Operator:&nbsp;
        <input disabled type="text" name="creationOperator" maxlength="15" size="15" value="<%= creationOperator %>">
      </td>
    </tr>
    <tr align="center">
      <td colspan="3"> <b>Overrides / Indicators:</b> </td>
    </tr>
    <tr>
      <td nowrap colspan="3" align="center">
        <input disabled type="checkbox" name="scheduledEvents" <%= scheduledEventsStatus %> >
        Scheduled Event
        <input disabled type="checkbox" name="notesInd" <%=notesIndStatus%> >
        <a href ="javascript:notesAdd()">Notes</a>
        <input disabled type="checkbox" name="taxesInd" <%=taxesIndStatus%> >
        <a href = "javascript:showTaxesDialog()">Taxes</a>
      </td>
    </tr>
    <tr>
      <td nowrap colspan="3" align="center">
        <input type="checkbox" name="lastDayOfMonthInd" <%=lastDayOfMonthIndStatus%> >Last Day Of Month
        <input disabled type="checkbox" name="calculatedValuesInd" <%=calculatedValuesIndStatus %> disabled>
        <a href ="javascript:calculatedValuesAdd()">Calculated Values</a>
        <input disabled type="checkbox" name="depositsIndStatus" <%= depositsIndStatus %> >
        <a href ="javascript:showDepositDialog()">Deposits</a>
        <input type="checkbox" name="postJune1986InvestmentInd" <%=postJune1986InvestmentIndStatus %> >
        Post June 1986 Investment<br>
	  </td>
	</tr>
  </table>

<!-- ****** HIDDEN FIELDS ***** //-->
 <input type="hidden" name="transaction" value="">
 <input type="hidden" name="action"      value="">
 <input type="hidden" name="optionId" value="<%= optionId %>">
 <input type="hidden" name="nextPage" value="">
 <input type="hidden" name="segmentPK" value="<%= segmentPK %>">
 <input type="hidden" name="payoutPK" value="<%= payoutPK %>">
 <input type="hidden" name="contractNumber" value="<%= contractNumber %>">
 <input type="hidden" name="companyStructure" value="<%= companyStructure %>">
 <input type="hidden" name="companyStructureId" value="<%= companyStructureId %>">
<%-- <input type="hidden" name="policyGroupFK" value="<%= policyGroupFK %>">--%>

 <input type="hidden" name="statusCode" value="<%= statusCode %>">
 <input type="hidden" name="scheduledEvents" value="">
 <input type="hidden" name="calculatedValuesIndStatus" value="">
 <input type="hidden" name="notesIndStatus" value="">
 <input type="hidden" name="postJune1986InvestmentIndStatus" value="">
 <input type="hidden" name="lastDayOfMonthIndStatus" value="">
 <input type="hidden" name="taxesIndStatus" value="">
 <input type="hidden" name="finalDistributionAmount" value="">
 <input type="hidden" name="premiumTaxes" value="">
 <input type="hidden" name="frontEndLoads" value="">
 <input type="hidden" name="totalProjectedAnnuity" value="">
 <input type="hidden" name="exclusionRatio" value="">
 <input type="hidden" name="excessInterest" value="">
 <input type="hidden" name="yearlyTaxableBenefit" value="">
 <input type="hidden" name="fees" value="">
<%-- <input type="hidden" name="mrdAmount" value="">--%>
 <input type="hidden" name="paymentAmount" value="">
 <input type="hidden" name="purchaseAmount" value="">
 <input type="hidden" name="faceAmount" value="<%= faceAmount %>">
 <input type="hidden" name="savingsPercent" value="<%= savingsPercent %>">
 <input type="hidden" name="dismembermentPercent" value="<%= dismembermentPercent %>">
 <input type="hidden" name="nextPaymentDate" value="<%= nextPaymentDate %>">
 <input type="hidden" name="ignoreEditWarnings" value="">
 <input type="hidden" name="creationDate" value="<%= creationDate %>">
 <input type="hidden" name="creationOperator" value="<%= creationOperator %>">
 <input type="hidden" name="issueDate" value="<%= issueDate %>">
 <input type="hidden" name="termStatus" value="<%= terminatedStatus %>">
 <input type="hidden" name="premiumTaxSitusOverride" value="<%= premiumTaxSitusOverride %>"> 
 <input type="hidden" name="suppOriginalContractNumber" value="<%= suppOriginalContractNumber %>">
 <input type="hidden" name="casetrackingOptionCT" value="<%= casetrackingOptionCT %>">
 <input type="hidden" name="ageAtIssue" value="<%= ageAtIssue %>">
 <input type="hidden" name="originalStateCT" value="<%= originalStateCT %>">
 <input type="hidden" name="location" value="<%= location %>">
 <input type="hidden" name="sequence" value="<%= sequence %>">
 <input type="hidden" name="indivAnnPremium" value="<%= indivAnnPremium %>">
 
 <input type="hidden" name="worksheetTypeCT" value="<%= worksheetTypeCT %>">
 <input type="hidden" name="ratedGenderCT" value="<%= ratedGenderCT %>"> 

 <!-- recordPRASEEvents is set by the toolbar -->
 <input type="hidden" name="recordPRASEEvents" value="false">

</span>

</form>
</body>
</html>
