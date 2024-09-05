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
                 engine.*,
                 contract.*,
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
    String suspenseMessage = (String) request.getAttribute("suspenseMessage");
    if (suspenseMessage == null)
    {
        suspenseMessage = "";
    }

    quoteMainSessionBean.putValue("quoteMessage", "");

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

    String message = (String) request.getAttribute("contractNumberMessage");

    if (message == null) {

        message = "";
    }

	PageBean formBean = quoteMainSessionBean.getPageBean("formBean");

    String companyStructureId = Util.initString(formBean.getValue("companyStructureId"), "0");

    //Check for Case companyStructure, the groupNumber entry must display for Cases
    boolean caseCompanyStructure = false;

    if (!companyStructureId.equals("0"))
    {
        caseCompanyStructure = ProductStructure.isCaseStructure(new Long(companyStructureId));
    }

    //Case company structures will allow entry of the GroupNumber
    String contractGroupFK     = Util.initString(formBean.getValue("contractGroupFK"), null);
    String groupNumber = Util.initString(formBean.getValue("groupNumber"), "");

    String billScheduleFK = Util.initString(formBean.getValue("billScheduleFK"), null);
    String batchContractSetupFK = Util.initString(formBean.getValue("batchContractSetupFK"), null);

    String   companyStructure    = formBean.getValue("companyStructure");

    CodeTableWrapper codeTableWrapper = CodeTableWrapper.getSingleton();

    CodeTableVO[] option7702s = codeTableWrapper.getCodeTableEntries("OPTION7702", Long.parseLong(companyStructureId));
    CodeTableVO[] deathBenefitOptions = codeTableWrapper.getCodeTableEntries("DEATHBENOPT", Long.parseLong(companyStructureId));

    String option7702        = formBean.getValue("option7702");
    String deathBeneOption   = formBean.getValue("deathBeneOption");

    String segmentPK         = formBean.getValue("segmentPK");
    String contractNumber    = formBean.getValue("contractNumber");
    String payoutPK          = formBean.getValue("payoutPK");

	String effectiveDate  	 = formBean.getValue("effectiveDate");

	String terminationDate  	 = formBean.getValue("terminationDate");

    String applicationSignedDate      = formBean.getValue("applicationSignedDate");

    String applicationReceivedDate      = formBean.getValue("applicationReceivedDate");

    String dateInEffectDate    = formBean.getValue("dateInEffectDate");

    String creationDate = formBean.getValue("creationDate");
    String creationOperator = formBean.getValue("creationOperator");

    String waiverInEffectStatus = formBean.getValue("waiverInEffectStatus");

    CodeTableVO[] states = codeTableWrapper.getCodeTableEntries("STATE", Long.parseLong(companyStructureId));
	String areaId = formBean.getValue("areaId");
    String premiumTaxSitusOverride = formBean.getValue("premiumTaxSitusOverride");	

    CodeTableVO[] contractTypes = codeTableWrapper.getCodeTableEntries("CONTRACTTYPE");
    String contractType = formBean.getValue("contractType");

    CodeTableVO[] options = codeTableWrapper.getCodeTableEntries("OPTIONCODE", Long.parseLong(companyStructureId));
	String optionId	= formBean.getValue("optionId");

    String statusCode                          = formBean.getValue("statusCode");
	String scheduledEventsStatus               = formBean.getValue("scheduledEvents");
	String calculatedValuesIndStatus           = formBean.getValue("calculatedValuesIndStatus");
    String depositsIndStatus                   = "unchecked";
    if (depositsVOs != null && depositsVOs.length > 0)
    {
        for (int d = 0; d < depositsVOs.length; d++)
        {
            if (!depositsVOs[d].getVoShouldBeDeleted())
            {
                depositsIndStatus = "checked";
                break;
            }
        }
    }

    String notesIndStatus                      = formBean.getValue("notesIndStatus");
    String taxesIndStatus                      = formBean.getValue("taxesIndStatus");
    String term                                = formBean.getValue("term");
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
    String guidelineSinglePrem                 = formBean.getValue("guidelineSinglePrem");
    String guidelineLevelPrem                  = formBean.getValue("guidelineLevelPrem");
    String tamra                               = formBean.getValue("tamra");
    String ageAtIssue                          = formBean.getValue("ageAtIssue");
    String originalStateCT                     = formBean.getValue("originalStateCT");
    String location = formBean.getValue("location");
    String sequence = formBean.getValue("sequence");
    String indivAnnPremium = formBean.getValue("indivAnnPremium");
    String worksheetTypeCT                     = formBean.getValue("worksheetTypeCT");

    String waiveFreeLookIndicatorStatus        = formBean.getValue("waiveFreeLookIndicatorStatus");
    String freeLookDaysOverride                = formBean.getValue("freeLookDaysOverride");
    String dialableSalesLoadPct                = formBean.getValue("dialableSalesLoadPct");
    String chargeDeductDivisionIndStatus       = formBean.getValue("chargeDeductDivisionIndStatus");
    String pointInScaleIndStatus               = formBean.getValue("pointInScaleIndStatus");
    String commitmentIndicatorStatus           = formBean.getValue("commitmentIndicatorStatus");
    String commitmentAmount                    = formBean.getValue("commitmentAmount");
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
    var faceAmount = "<%= faceAmount %>";
    var savingsPercent = "<%= savingsPercent %>";
    var editingExceptionExists = "<%= editingExceptionExists %>";
    var voEditExceptionExists = "<%= voEditExceptionExists %>";

	var dialog = null;
    var height = screen.height;
    var width  = screen.width;

	var f = null;
    var enteredOption = true;
    var shouldShowLockAlert = true;;
    var editableContractStatus = true;
    
	function init()
    {
		f = document.quoteCommitMainForm;

		top.frames["main"].setActiveTab("mainTab");

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
            alert(errorMessage);
        }

		checkForQuoteReturnValues();

<%--		setCompanyStructure();--%>

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

    function checkForEditingException()
    {
        if (editingExceptionExists == "true")
        {
            var width = .75 * screen.width;
            var height = screen.height/3;

            prepareToOpenDialog("exceptionDialog", "resizable=no", width, height, "QuoteDetailTran", "showEditingExceptionDialog");
        }
    }

    function checkForVOEditException()
    {
        if (voEditExceptionExists == "true")
        {
            var width = .75 * screen.width;
            var height = screen.height/3;

            prepareToOpenDialog("voEditExceptionDialog", "resizable=no", width, height, "QuoteDetailTran", "showVOEditExceptionDialog");
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

	function prepareToOpenDialog(winName, features, width, height, transaction,action)
    {
        enteredOption = checkForRequiredFields();

        if (enteredOption == true)
        {
		    openDialog(winName, features, width, height);

	  	    prepareToSendTransactionAction(transaction, action, winName);
        }
	}

	function checkForRequiredFields()
    {
		if (f.optionId.value == "")
        {
			alert("Please select a Coverage.");

			return enteredOption = false ;
		}

		return enteredOption = true;
	}

    function continueTransactionAction(transaction, action)
    {
        f.ignoreEditWarnings.value = "true";
        prepareToSendTransactionAction(transaction, action, "contentIFrame");
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

            prepareToOpenDialog("contractSuspenseCreation", "top=0,left=0,resizable=no", width, height, "QuoteDetailTran", "showContractSuspenseCreation");
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

        if (f.taxesInd.checked == true) {
            f.taxesIndStatus.value = "checked";
        }

        if (f.waiverInEffect.checked == true)
        {
			f.waiverInEffectStatus.value = "checked";
		}
        else
        {
			f.waiverInEffectStatus.value = "";
        }

        if (f.waiveFreeLookIndicator.checked == true)
        {
            f.waiveFreeLookIndicatorStatus.value = "checked";
        }
        else
        {
            f.waiveFreeLookIndicatorStatus.value = "";
        }

            if (f.commitmentIndicator.checked == true)
            {
                f.commitmentIndicatorStatus.value = "checked";
            }
            else
            {
                f.commitmentIndicatorStatus.value = "";
            }

            if (f.chargeDeductDivisionInd.checked == true)
            {
                f.chargeDeductDivisionIndStatus.value = "checked";
            }
            else
            {
                 f.chargeDeductDivisionIndStatus.value = "";
            }

            if (f.pointInScaleInd.checked == true)
            {
                f.pointInScaleIndStatus.value = "checked";
            }
            else
            {
                 f.pointInScaleIndStatus.value = "";
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
        f.faceAmount.value = faceAmount;
        f.savingsPercent.value = savingsPercent;

        testForDates();

            sendTransactionAction(transaction, action, target);
        }

	function notesAdd()
    {
        var width = .99 * screen.width;
        var height = .90 * screen.height;

        testForDates();

    		prepareToOpenDialog("notesAdd", "top=0,left=0,resizable=no", width, height, "QuoteDetailTran", "showNotesDialog");
        }

	function calculatedValuesAdd()
    {
        var width = .65 * screen.width;
        var height = .50 * screen.height;

        testForDates();

    		prepareToOpenDialog("calculatedValuesAdd", "top=0,left=0,resizable=no", width, height, "QuoteDetailTran", "showCalculatedValuesAdd");
        }

	function showTaxesDialog()
    {
        var width = .50 * screen.width;
        var height = .32 * screen.height;

        testForDates();

    		prepareToOpenDialog("taxesDialog", "resizable=no", width, height, "QuoteDetailTran", "showTaxesDialog");
        }

	function showDepositDialog()
    {
        var width = .99 * screen.width;
        var height = .90 * screen.height;

        testForDates();
    		prepareToOpenDialog("depositDialog", "top=0,left=0,resizable=no", width, height, "QuoteDetailTran", "showDepositDialog");
        }

	function showAnalyzer()
    {
        var width = screen.width;
        var height = screen.height;

        testForDates();

    		prepareToOpenDialog("analyzeQuote", "left=0,top=0,resizable=yes", width, height, "QuoteDetailTran", "showAnalyzer");
        }

<%--	function setCompanyStructure() {--%>
<%----%>
<%--		f.companyStructure.value = f.companyStructureId.options[f.companyStructureId.selectedIndex].text;--%>
<%--	}--%>

</script>

<head>
<title>New Business Deferred Annuity Main</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta http-equiv="Cache-Control" content="no-store">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Expires" content="0">

<link href="/PORTAL/common/css/EDITSolutions.css" rel="stylesheet" type="text/css">
</head>

<body class="mainTheme" onLoad="init(); checkForEditingException(); checkForVOEditException()" style="border-style:solid; border-width:1;">
<form name= "quoteCommitMainForm" method="post" action="/PORTAL/servlet/RequestManager">

<input type="hidden" name="page" value="">
<jsp:include page="quoteInfoHeader.jsp" flush="true"/>

  <table width="100%" height="80%" border="0" cellspacing="0" cellpadding="0">
    <tr>
     <td align="left" nowrap>Key:&nbsp;
		<input disabled type="text" id="companyStructure" name="companyStructure" value="<%= companyStructure %>" size="40" maxlength="75">
      </td>
      <td align="left" nowrap>Coverage:&nbsp;
        <select tabindex="1" name="optionId">
          <option>Please Select</option>
            <%
              for(int i = 0; i < options.length; i++)
              {
                  String codeTablePK = options[i].getCodeTablePK() + "";
                  String codeDesc    = options[i].getCodeDesc();
                  String code        = options[i].getCode();

                  if (optionId.equalsIgnoreCase(code))
                  {
                      out.println("<option selected name=\"id\" value=\"" + codeTablePK+ "\">" + codeDesc + "</option>");
                  }
                  else
                  {
                      out.println("<option name=\"id\" value=\"" + codeTablePK + "\">" + codeDesc + "</option>");
                  }
               }
            %>
      </td>
      <td align="left" colspan="4" nowrap>Contract Type:&nbsp;
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
      <td align="left" nowrap>Issue State:&nbsp;
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
      <td align="left" nowrap>Face Amount:&nbsp;
        <input type="text" name="faceAmount" tabindex="4" value="<%= faceAmount%>" maxlength="20" CURRENCY>
      </td>
      <td align="left" nowrap colspan="4">Savings Percent:&nbsp;
        <input type="text" name="savingsPercent" tabindex="5" value="<%= savingsPercent %>" size="6" maxlength="6">
      </td>
    </tr>
    <tr>
      <td nowrap align="left">Death Benefit Option:&nbsp;
        <select name="deathBeneOption" tabindex="6">
          <option> Please Select </option>
          <%
              for(int i = 0; i < deathBenefitOptions.length; i++)
              {
                  String codeTablePK = deathBenefitOptions[i].getCodeTablePK() + "";
                  String codeDesc    = deathBenefitOptions[i].getCodeDesc();
                  String code        = deathBenefitOptions[i].getCode();

                 if (deathBeneOption.equalsIgnoreCase(code))
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
      <td nowrap align="left" colspan="5">7702 Option:&nbsp;
        <select name="option7702" tabindex="7">
          <option> Please Select </option>
          <%
              for(int i = 0; i < option7702s.length; i++)
              {
                  String codeTablePK = option7702s[i].getCodeTablePK() + "";
                  String codeDesc    = option7702s[i].getCodeDesc();
                  String code        = option7702s[i].getCode();

                 if (option7702.equalsIgnoreCase(code))
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
    </tr>
    <tr>
      <td align="left" nowrap>Application Signed Date:&nbsp;
           <input type="text" name="applicationSignedDate" value="<%= applicationSignedDate %>" size="10" maxlength="10" tabindex="8" onKeyUp="DateFormat(this,this.value,event,false)" onBlur="DateFormat(this,this.value,event,true)">
           <a href="javascript:show_calendar('f.applicationSignedDate', f.applicationSignedDate.value);"><img src="/PORTAL/common/images/calendarIcon.gif" width="16" height="16" border="0" alt="Select a date from the calendar"></a>
      </td>

      <td align="left" nowrap >Application Received Date:&nbsp;
            <input type="text" name="applicationReceivedDate" value="<%= applicationReceivedDate %>" size="10" tabindex="9" maxlength="10" onKeyUp="DateFormat(this,this.value,event,false)" onBlur="DateFormat(this,this.value,event,true)">
           <a href="javascript:show_calendar('f.applicationReceivedDate', f.applicationReceivedDate.value);"><img src="/PORTAL/common/images/calendarIcon.gif" width="16" height="16" border="0" alt="Select a date from the calendar"></a>
      </td>

      <td align="left" nowrap colspan="4">Effective Date:&nbsp;
           <input type="text" name="effectiveDate" value="<%= effectiveDate %>" size='10' maxlength="10" tabindex="10" onKeyUp="DateFormat(this,this.value,event,false)" onBlur="DateFormat(this,this.value,event,true)">
           <a href="javascript:show_calendar('f.effectiveDate', f.effectiveDate.value);"><img src="/PORTAL/common/images/calendarIcon.gif" width="16" height="16" border="0" alt="Select a date from the calendar"></a>
      </td>
    </tr>
    <tr>
      <td align="left" nowrap>Date In Effect:&nbsp;
           <input type="text" name="dateInEffectDate" value="<%= dateInEffectDate %>" size='10' maxlength="10" tabindex="11" onKeyUp="DateFormat(this,this.value,event,false)" onBlur="DateFormat(this,this.value,event,true)">
           <a href="javascript:show_calendar('f.dateInEffectDate', f.dateInEffectDate.value);"><img src="/PORTAL/common/images/calendarIcon.gif" width="16" height="16" border="0" alt="Select a date from the calendar"></a>
      </td>

      <td align="left" nowrap>Termination Date:&nbsp;
            <input type="text" name="terminationDate" value="<%= terminationDate %>" size='10' maxlength="10" tabindex="12" onKeyUp="DateFormat(this,this.value,event,false)" onBlur="DateFormat(this,this.value,event,true)">
           <a href="javascript:show_calendar('f.terminationDate', f.terminationDate.value);"><img src="/PORTAL/common/images/calendarIcon.gif" width="16" height="16" border="0" alt="Select a date from the calendar"></a>
      </td>

      <td align="left" nowrap colspan="4">Waive FreeLook Indicator:&nbsp;
        <input type="checkbox" tabindex="13" name="waiveFreeLookIndicator" <%= waiveFreeLookIndicatorStatus %> >
      </td>
    </tr>
    <tr>
      <td align="left" nowrap>Term:&nbsp;
		<input type="text" name="term" maxlength="2" size="2" tabindex="14" value="<%= term %>">
      </td>
	  <td align="left" nowrap>Waiver In Effect&nbsp;
        <input type="checkbox" tabindex="15" name="waiverInEffect" <%=waiverInEffectStatus%> >
      </td>
      <td align="left" nowrap colspan="4">Freelook Days Override:&nbsp;
        <input type="text" name="freeLookDaysOverride" maxlength="2" size="2" tabindex="16" value="<%= freeLookDaysOverride %>">
      </td>
    </tr>
    <tr>
      <td align="left" nowrap >Dialable Sales Load PCT:&nbsp;
        <input type="text" name="dialableSalesLoadPct" maxlength="19" size="19" tabindex="17" value="<%= dialableSalesLoadPct %>">
      </td>
 	  <td align="left" nowrap >Point In Scale Indicator:&nbsp;
        <input type="checkbox" name="pointInScaleInd" tabindex="18" <%=pointInScaleIndStatus%> >
      </td>
	  <td align="left" nowrap >Charge Deduct Division Ind:&nbsp;
        <input type="checkbox" name="chargeDeductDivisionInd" tabindex="19" <%=chargeDeductDivisionIndStatus%> >
      </td>
    </tr>
    <tr>
	  <td align="left" nowrap >Commitment Indicator:&nbsp;
        <input type="checkbox" name="commitmentIndicator" tabindex="20" <%=commitmentIndicatorStatus%> >
      </td>
	  <td align="left" nowrap >Commitment Amt:&nbsp;
        <input type="text" name="commitmentAmount" size="19" maxlength="19" tabindex="21" value="<%=commitmentAmount%>" >
      </td>
      <td align="left" nowrap colspan="4">Premium Tax Situs Override:&nbsp;
        <select name="premiumTaxSitusOverride" tabindex="31">
          <option> Please Select </option>
          <%
              for(int i = 0; i < states.length; i++)
              {
                  String codeTablePK = states[i].getCodeTablePK() + "";
                  String codeDesc    = states[i].getCodeDesc();
                  String code        = states[i].getCode();

                 if (premiumTaxSitusOverride.equalsIgnoreCase(code))
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
    </tr>
    <tr>
      <td align="left" nowrap>Creation Date:&nbsp;
        <input disabled type="text" name="creationDate" maxlength="10" size="10" value="<%= creationDate %>">
      </td>
<%--	  <td align="left" nowrap colspan="5">Creation Operator:&nbsp;--%>
	   <td align="left" nowrap >Creation Operator:&nbsp;
        <input disabled type="text" name="creationOperator" maxlength="15" size="15" value="<%= creationOperator %>">
      </td>
    </tr>
    <tr>
      <td nowrap colspan="6">&nbsp;</td>
    </tr>
    <br>
    <tr align="center">
      <td colspan="6"> <b>Overrides / Indicators:</b> </td>
    </tr>
    <tr>
      <td nowrap colspan="7" align="center">
        <input disabled type="checkbox" name="scheduledEvents" <%= scheduledEventsStatus %> >
        Scheduled Event
        <input disabled type="checkbox" name="notesInd" <%=notesIndStatus%> >
        <a href ="javascript:notesAdd()">Notes</a>
        <input disabled type="checkbox" name="taxesInd" <%=taxesIndStatus%> >
        <a href = "javascript:showTaxesDialog()">Taxes</a>
        <input disabled type="checkbox" name="calculatedValuesInd" <%=calculatedValuesIndStatus %> disabled>
        <a href ="javascript:calculatedValuesAdd()">Calculated Values</a>
        <input disabled type="checkbox" name="depositsIndStatus" <%= depositsIndStatus %> disabled>
        <a href ="javascript:showDepositDialog()">Deposits</a>
      </td>
    </tr>
<%--    <tr>--%>
<%--      <td nowrap colspan="6" align="center">--%>
<%----%>
<%--	  </td>--%>
<%--	</tr>--%>
  </table>
</span>

<!-- ****** HIDDEN FIELDS ***** //-->
 <input type="hidden" name="transaction" value="">
 <input type="hidden" name="action"      value="">
 <input type="hidden" name="nextPage" value="">
 <input type="hidden" name="relationship" value="ANO">
 <input type="hidden" name="companyStructure" value="<%= companyStructure %>">
 <input type="hidden" name="companyStructureId" value="<%= companyStructureId %>">
 <input type="hidden" name="segmentPK" value="<%= segmentPK %>">
 <input type="hidden" name="payoutPK" value="<%= payoutPK %>">
 <input type="hidden" name="contractNumber" value="<%= contractNumber %>">
 <input type="hidden" name="contractGroupFK" value="<%= contractGroupFK %>">
 <input type="hidden" name="billScheduleFK" value="<%= billScheduleFK %>">
 <input type="hidden" name="batchContractSetupFK" value="<%= batchContractSetupFK %>">
 
 <input type="hidden" name="statusCode" value="<%= statusCode %>">
 <input type="hidden" name="scheduledEvents" value="">
 <input type="hidden" name="calculatedValuesIndStatus" value="">
 <input type="hidden" name="notesIndStatus" value="">
 <input type="hidden" name="taxesIndStatus" value="">
 <input type="hidden" name="finalDistributionAmount" value="">
 <input type="hidden" name="premiumTaxes" value="">
 <input type="hidden" name="frontEndLoads" value="">
 <input type="hidden" name="totalProjectedAnnuity" value="">
 <input type="hidden" name="exclusionRatio" value="">
 <input type="hidden" name="excessInterest" value="">
 <input type="hidden" name="yearlyTaxableBenefit" value="">
 <input type="hidden" name="fees" value="">
 <input type="hidden" name="paymentAmount" value="">
 <input type="hidden" name="purchaseAmount" value="<%= purchaseAmount %>">
 <input type="hidden" name="faceAmount" value="">
 <input type="hidden" name="savingsPercent" value="">
 <input type="hidden" name="guidelineSinglePrem" value="<%= guidelineSinglePrem %>">
 <input type="hidden" name="guidelineLevelPrem" value="<%= guidelineLevelPrem %>">
 <input type="hidden" name="tamra" value="<%= tamra %>">
 <input type="hidden" name="nextPaymentDate" value="<%= nextPaymentDate %>">
 <input type="hidden" name="ignoreEditWarnings" value="">
 <input type="hidden" name="waiverInEffectStatus" value="<%= waiverInEffectStatus %>">
 <input type="hidden" name="creationDate" value="<%= creationDate %>">
 <input type="hidden" name="creationOperator" value="<%= creationOperator %>">
 <input type="hidden" name="dismembermentPercent" value="<%= dismembermentPercent %>">
 <input type="hidden" name="waiveFreeLookIndicatorStatus" value="<%= waiveFreeLookIndicatorStatus %>">
 <input type="hidden" name="dialableSalesLoadPct" value="<%= dialableSalesLoadPct %>">
 <input type="hidden" name="chargeDeductDivisionIndStatus" value="<%= chargeDeductDivisionIndStatus %>">
 <input type="hidden" name="pointInScaleIndStatus" value="<%= pointInScaleIndStatus %>">
 <input type="hidden" name="commitmentIndicatorStatus" value="<%= commitmentIndicatorStatus %>">
 <input type="hidden" name="commitmentAmount" value="<%= commitmentAmount %>">
 <input type="hidden" name="suppOriginalContractNumber" value="<%= suppOriginalContractNumber %>">
 <input type="hidden" name="casetrackingOptionCT" value="<%= casetrackingOptionCT %>">
 <input type="hidden" name="ageAtIssue" value="<%= ageAtIssue %>">
 <input type="hidden" name="originalStateCT" value="<%= originalStateCT %>">
 <input type="hidden" name="worksheetTypeCT" value="<%= worksheetTypeCT %>">
  <input type="hidden" name="location" value="<%= location %>">
 <input type="hidden" name="sequence" value="<%= sequence %>">
 <input type="hidden" name="indivAnnPremium" value="<%= indivAnnPremium %>">


 <!-- recordPRASEEvents is set by the toolbar -->
 <input type="hidden" name="recordPRASEEvents" value="false">

</form>
</body>
</html>
