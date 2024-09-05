<!--
 * User: unknown
 * Date: Jul 11, 2008
 * Time: 11:11:11
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 --> 
 
 <!-- contractNonTradLifeRider.jsp //-->
 
<%@ page  errorPage="/common/jsp/error.jsp" autoFlush="true" %>
<%@ page import="java.util.*,
                 edit.common.*,
                 edit.common.vo.*,
                 edit.common.vo.user.*,
                 fission.beans.*,
                 edit.portal.exceptions.*,
                 edit.common.exceptions.VOEditException,
                 fission.utility.Util,
                 edit.portal.common.session.UserSession,
                 contract.*,
                 engine.*" %>

<jsp:useBean id="contractMainSessionBean"
    class="fission.beans.SessionBean" scope="session"/>

<jsp:useBean id="contractRiders"
    class="fission.beans.SessionBean" scope="session"/>

<jsp:useBean id="statusBean"
    class="fission.beans.PageBean" scope="request"/>

<%
    PortalEditingException editingException = (PortalEditingException) session.getAttribute("portalEditingException");
    String editingExceptionExists = "false";
    if (editingException != null)
    {
        editingExceptionExists = "true";
    }

    VOEditException voEditException = (VOEditException) session.getAttribute("VOEditException");
    String voEditExceptionExists = "false";
    if (voEditException != null)
    {
        voEditExceptionExists = "true";
    }

    CodeTableWrapper codeTableWrapper = CodeTableWrapper.getSingleton();

    PageBean formBean       = contractMainSessionBean.getPageBean("formBean");
    
    String riderBeanKey = Util.initString((String) request.getAttribute("riderBeanKey"), "");
    
    PageBean riderBean      = null;
    String riderSegmentPK   = null;
    String riderLifePK = null;
    
    if (!riderBeanKey.equals("") && !riderBeanKey.equals("base"))
    {
        riderBean = contractRiders.getPageBean(riderBeanKey);
        riderSegmentPK = riderBean.getValue("riderSegmentPK");
        riderLifePK = riderBean.getValue("lifePK");
    }

    String segmentPK        = formBean.getValue("segmentPK");
    String payoutPK         = formBean.getValue("payoutPK");
    String lifePK           = formBean.getValue("lifePK");
    String segmentStatus    = formBean.getValue("segmentStatus");
    String riderType        = Util.initString(formBean.getValue("riderType"), "");
    String companyStructureId = Util.initString(formBean.getValue("companyStructureId"), "0");

    //Check for Case companyStructure, the groupNumber entry must display for Cases
    boolean caseCompanyStructure = false;

    if (!companyStructureId.equals("0"))
    {
        caseCompanyStructure = ProductStructure.isCaseStructure(new Long(companyStructureId));
    }
    
    String contractGroupFK     = Util.initString(formBean.getValue("contractGroupFK"), null);
    String billScheduleFK = Util.initString(formBean.getValue("billScheduleFK"), null);
    String batchContractSetupFK = Util.initString(formBean.getValue("batchContractSetupFK"), null);
    String groupNumber = Util.initString(formBean.getValue("groupNumber"), "");

    String amount           = formBean.getValue("purchaseAmount");
    String totalFaceAmount  = formBean.getValue("totalFaceAmount");
    String savingsPercent   = formBean.getValue("savingsPercent");
    String dismembermentPercent = formBean.getValue("dismembermentPercent");

    String startDate       = formBean.getValue("startDate");

    String appSignedDate  = formBean.getValue("appSignedDate");

    String appReceivedDate  = formBean.getValue("appReceivedDate");

    String terminationDate = formBean.getValue("terminationDate");

    String dateInEffectDate    = formBean.getValue("dateInEffectDate");

    String commitmentIndicatorStatus  = formBean.getValue("commitmentIndicatorStatus");
    String commitmentAmount     = formBean.getValue("commitmentAmount");
    String policyDeliveryDate  = formBean.getValue("policyDeliveryDate");
    String chargeDeductDivisionIndStatus = formBean.getValue("chargeDeductDivisionIndStatus");
    String pointInScaleIndStatus = formBean.getValue("pointInScaleIndStatus");
    String chargeDeductAmount  = formBean.getValue("chargeDeductAmount");

    String creationDate = formBean.getValue("creationDate");
    String creationOperator = formBean.getValue("creationOperator");
    String waiverInEffectStatus = formBean.getValue("waiverInEffectStatus");
    String dialableSalesLoadPct                = formBean.getValue("dialableSalesLoadPct");

    CodeTableVO[] options   = codeTableWrapper.getCodeTableEntries("OPTIONCODE", Long.parseLong(companyStructureId));
    String   optionId	    = formBean.getValue("optionId");
    String optionKey = "";
    if (optionId != null && !optionId.equals(""))
    {
        optionKey = codeTableWrapper.getCodeTablePKByCodeTableNameAndCode("OPTIONCODE", optionId) + "";
    }

    CodeTableVO[] states    = codeTableWrapper.getCodeTableEntries("STATE", Long.parseLong(companyStructureId));
    String   areaId	         = formBean.getValue("areaId");

    CodeTableVO[] statusChanges = codeTableWrapper.getCodeTableEntries("STATUSCHANGE", Long.parseLong(companyStructureId));
    String statusChangeId   = formBean.getValue("statusChange");

    CodeTableVO[] contractTypes = codeTableWrapper.getCodeTableEntries("CONTRACTTYPE");
    String contractType = formBean.getValue("contractType");

    CodeTableVO[] frequencies = codeTableWrapper.getCodeTableEntries("FREQUENCY", Long.parseLong(companyStructureId));
    String   frequencyId     = formBean.getValue("frequencyId");

    CodeTableVO[] qualNonQuals = codeTableWrapper.getCodeTableEntries("QUALNONQUAL", Long.parseLong(companyStructureId));
    String qualNonQual       = formBean.getValue("qualNonQual");

    for(int i = 0; i < qualNonQuals.length; i++)
    {
        String codeTablePK = qualNonQuals[i].getCodeTablePK() + "";
        String code        = qualNonQuals[i].getCode();

        if (qualNonQual.equalsIgnoreCase(code))
        {
            qualNonQual = codeTablePK;
        }
    }

    CodeTableVO[] mecStatuses = codeTableWrapper.getCodeTableEntries("MECSTATUS", Long.parseLong(companyStructureId));
    String mecStatus = formBean.getValue("mecStatus");

    CodeTableVO[] qualifiedTypes = codeTableWrapper.getCodeTableEntries("QUALIFIEDTYPE", Long.parseLong(companyStructureId));
    String qualifiedType     = formBean.getValue("frequencyId");

    CodeTableVO[] riderNames = codeTableWrapper.getCodeTableEntries("RIDERNAME", Long.parseLong(companyStructureId));

    String term = formBean.getValue("term");
    String lastDayOfMonthInd = formBean.getValue("lastDayOfMonthInd");

    String reduce1           = formBean.getValue("reduce1");
    String reduce2           = formBean.getValue("reduce2");

    String financialIndStatus        = formBean.getValue("financialIndStatus");
    String dateIndStatus             = formBean.getValue("dateIndStatus");
    String notesIndStatus            = formBean.getValue("notesIndStatus");
    String taxesIndStatus            = formBean.getValue("taxesIndStatus");
    String depositsIndStatus         = formBean.getValue("depositsIndStatus");

    String purchaseAmount            = formBean.getValue("purchaseAmount");
    String costBasis                 = formBean.getValue("costBasis");
    String exclusionRatio            = formBean.getValue("exclusionRatio");
    String amountPaidToDate          = formBean.getValue("amountPaidToDate");
    String accumulatedLoads          = formBean.getValue("accumulatedLoads");
    String withdrawlYrToDate         = formBean.getValue("withdrawlYrToDate");
    String yearlyTaxableBenefit      = formBean.getValue("yearlyTaxableBenefit");
//	String mrdAmount                 = formBean.getValue("mrdAmount");
    String lumpSumPaidToDate         = formBean.getValue("lumpSumPaidToDate");
    String paymentAmount             = formBean.getValue("paymentAmount");
    String totalExpectedReturn       = formBean.getValue("totalExpectedReturn");
    String recoveredCostBasis        = formBean.getValue("recoveredCostBasis");
    String finalDistributionAmount   = formBean.getValue("finalDistributionAmount");
    String numberOfRemainingPayments = formBean.getValue("numberOfRemainingPayments");
    String lumpSumYrToDate           = formBean.getValue("lumpSumYrToDate");
    String accumulatedFees           = formBean.getValue("accumulatedFees");
    String amountPaidYrToDate        = formBean.getValue("amountPaidYrToDate");
    String freeLookEndDate           = formBean.getValue("freeLookEndDate");
    String finalPayDate              = formBean.getValue("finalPayDate");
    String statusChangeDate          = formBean.getValue("statusChangeDate");
    String lastCheckDate             = formBean.getValue("lastCheckDate");
    String nextPaymentDate           = formBean.getValue("nextPaymentDate");
    String ownerId                   = formBean.getValue("ownerId");
    String certainPeriodEndDate      = formBean.getValue("certainPeriodEndDate");
    String lastAnnivDate             = formBean.getValue("lastAnnivDate");
    String freeAmount                = formBean.getValue("freeAmount");
    String freeAmountRemaining       = formBean.getValue("freeAmountRemaining");
    String guidelineSinglePrem       = formBean.getValue("guidelineSinglePrem");
    String guidelineLevelPrem        = formBean.getValue("guidelineLevelPrem");
    String tamra                     = formBean.getValue("tamra");
    String deathBeneOption           = formBean.getValue("deathBeneOption");
    String option7702                = formBean.getValue("option7702");
    String mecDate                   = formBean.getValue("MECDate");
    String pendingDBOChangeIndStatus = formBean.getValue("pendingDBOChangeIndStatus");
    String tamraStartDate            = formBean.getValue("tamraStartDate");
    String MAPEndDate            = formBean.getValue("MAPEndDate");
    String mecGuidelineSinglePremium = formBean.getValue("mecGuidelineSinglePremium");
    String mecGuidelineLevelPremium  = formBean.getValue("mecGuidelineLevelPremium");
    String cumGuidelineLevelPremium  = formBean.getValue("cumGuidelineLevelPremium");
    String maxNetAmountAtRisk        = formBean.getValue("maxNetAmountAtRisk");

    String paidToDate                = formBean.getValue("paidToDate");
    String lapsePendingDate          = formBean.getValue("lapsePendingDate");
    String lapseDate                 = formBean.getValue("lapseDate");

    String waiveFreeLookIndicator    = formBean.getValue("waiveFreeLookIndicator");
    String freeLookDaysOverride      = formBean.getValue("freeLookDaysOverride");
    String segmentName               = formBean.getValue("segmentName");
    String annuitizationValue        = formBean.getValue("annuitizationValue");
    String dateOfDeathValue          = formBean.getValue("dateOfDeathValue");
    String suppOriginalContractNumber = formBean.getValue("suppOriginalContractNumber");
    String openClaimEndDate          = formBean.getValue("openClaimEndDate");
    String chargeCodeStatus          = formBean.getValue("chargeCodeStatus");
    String ageAtIssue                = formBean.getValue("ageAtIssue");
    String originalStateCT                = formBean.getValue("originalStateCT");
    String worksheetTypeCT           = formBean.getValue("worksheetTypeCT");
    String riderNumber = formBean.getValue("riderNumber");

    CodeTableVO[] deathBenefitOptions = codeTableWrapper.getCodeTableEntries("DEATHBENOPT", Long.parseLong(companyStructureId));

    String rowToMatchBase            = riderBeanKey;
    
    String effectiveDate   = "";
    String issueDate       = "";
    String riderCoverage    = "";
    String faceAmount       = "0.00";
    String startNew7PayIndicatorStatus = "";
    String riderStatus      = "";
    
    if (riderBean != null)
    {
        issueDate = riderBean.getValue("issueDate");
        effectiveDate = riderBean.getValue("effectiveDate");
        riderCoverage = riderBean.getValue("coverage");
        faceAmount = Util.initString(riderBean.getValue("faceAmount"), "0.00");
        startNew7PayIndicatorStatus = riderBean.getValue("startNew7PayIndicatorStatus");
        riderStatus    = riderBean.getValue("riderStatus");
    }
    else
    {
        issueDate = formBean.getValue("issueDate");
        effectiveDate = formBean.getValue("effectiveDate");
        faceAmount = Util.initString(formBean.getValue("faceAmount"), "0.00");
        startNew7PayIndicatorStatus = formBean.getValue("startNew7PayIndicatorStatus");
        riderStatus = "Pending";
    }

    String errorMessage = (String) request.getAttribute("errorMessage");
    if (errorMessage == null)
    {
        errorMessage = "";
    }

    String sequenceNumber = (String) request.getAttribute("sequenceNumber");

    UserSession userSession = (UserSession) session.getAttribute("userSession");
%>

<html>

<head>
<title>Contract Life Rider</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta http-equiv="Cache-Control" content="no-store">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Expires" content="0">

<link href="/PORTAL/common/css/EDITSolutions.css" rel="stylesheet" type="text/css">

<script type="text/javascript" src="/PORTAL/common/javascript/commonJavascriptFunctions.js"></script>
<script type="text/javascript" src="/PORTAL/common/javascript/formatDateFunctions.js"></script>
<script type="text/javascript" src="/PORTAL/common/javascript/calendarSelector.js"></script>

<script type="text/javascript">

    var editingExceptionExists = "<%= editingExceptionExists %>";
    var voEditExceptionExists = "<%= voEditExceptionExists %>";
    var errorMessage = "<%= errorMessage %>";
    var dialog = null;
    var f = null;
    var segmentPK = "<%= segmentPK %>";
    var origOptionId = "<%= optionKey %>";
    var riderName = "<%= riderType %>";

    var shouldShowLockAlert = true;;

    function init()
    {
        f = document.contractCommitRidersForm;

        top.frames["main"].setActiveTab("mainTab");

        if (errorMessage != "")
        {
            alert(errorMessage);
        }

        var contractIsLocked = <%= userSession.getSegmentIsLocked()%>;
        var username = "<%= userSession.getUsername() %>";
        var elementPK = "<%= userSession.getSegmentPK() %>";
        top.frames["header"].updateLockState(contractIsLocked, username, elementPK);

        shouldShowLockAlert = !contractIsLocked;

        for (var i = 0; i < f.elements.length; i++)
        {
            var elementType = f.elements[i].type;

            if ( (elementType == "text" || elementType == "button") && (shouldShowLockAlert == true) )
            {
                f.elements[i].onclick = showLockAlert;
                f.elements[i].onkeydown = showLockAlert;
            }
        }

        formatCurrency();
    }

    function showDepositDialog()
    {
        var width = .99 * screen.width;
        var height = .55 * screen.height;

        openDialog("depositDialog","top=0,left=0,resizable=no", width, height);

        prepareToSendTransactionAction("ContractDetailTran", "showDepositDialog", "depositDialog");
    }

    function showLockAlert()
    {
        if (shouldShowLockAlert == true)
        {
            alert("The Contract can not be edited.");

            return false;
        }
    }

    function addLifeRider()
    {
        prepareToSendTransactionAction("ContractDetailTran", "addLifeRider", "contentIFrame");
    }

    function saveLifeRider()
    {
        if (f.riderName.value == "" || f.riderName.value == "Please Select")
        {
            alert("Select coverage for rider");
            return;
        }
        else
        {
            var answer = confirm("A face amount increase requires proper authorization. Please see your manager for approval prior to processing this transaction");

            if(answer)
            {
                prepareToSendTransactionAction("ContractDetailTran", "saveLifeRider", "contentIFrame");
            }
            else
            {
                cancelLifeRider();
            }
        }
    }

    function clearForm()
    {
        f.effectiveDate.value           = "";
        f.issueDate[0].value            = "";
        f.faceAmount[0].value           = 0;
        f.riderName.options[0].selected = true;
        f.startNew7PayInd.checked       = false;
        f.startNew7PayIndicatorStatus.value = "";
    }

    function prepareToSendTransactionAction(transaction, action, target)
    {
        if (f.startNew7PayInd.checked == true)
        {
             f.startNew7PayIndicatorStatus.value = "checked";
        }
        else
        {
             f.startNew7PayIndicatorStatus.value = "";
        }

        sendTransactionAction(transaction, action, target);
    }

    function selectRow()
    {
        var tdElement = window.event.srcElement;
        var trElement = tdElement.parentElement;

        var optionCodePK = trElement.optionCodePK;
        var rowId     = trElement.id;
        var parsedRowId = (rowId.split("_"))[1];

        var optionId = optionCodePK;
        var sequenceNumber = trElement.sequenceNumber;
        var riderBeanKey = trElement.key;

        f.selectedOptionId.value = optionId;

        f.riderNumber.value = sequenceNumber;
        f.riderBeanKey.value = riderBeanKey;
        
        prepareToSendTransactionAction("ContractDetailTran", "showLifeRiderDetailSummary", "contentIFrame");
    }

    function cancelLifeRider()
    {
        clearForm();
    }

    function deleteLifeRider()
    {
        prepareToSendTransactionAction("ContractDetailTran", "deleteLifeRider", "contentIFrame");
        clearForm();
    }

    function financialValues()
    {
        var width = screen.width * .80;
        var height = screen.height * .70;

        openDialog("financialValues","left=0,top=0,resizable=no", width, height);

        prepareToSendTransactionAction("ContractDetailTran", "showFinancialValues", "financialValues");
    }

    function dateValues()
    {
        var width = screen.width * .40;
        var height = screen.height * .50;

        openDialog("dateValues","top=0,left=0,resizable=no", width, height);

        prepareToSendTransactionAction("ContractDetailTran", "showDateValues", "dateValues");
    }

    function notes()
    {
        var width = .99 * screen.width;
        var height = .90 * screen.height;

        openDialog("notes","top=0,left=0,resizable=no", width, height);

        prepareToSendTransactionAction("ContractDetailTran", "showNotesDialog", "notes");
    }

    function showTaxesDialog()
    {
        var width = .50 * screen.width;
        var height = .32 * screen.height;

        openDialog("taxesDialog","top=0,left=0,resizable=no", width, height);

        prepareToSendTransactionAction("ContractDetailTran", "showTaxesDialog", "taxesDialog");
    }

    function checkForEditingException()
    {
        if (editingExceptionExists == "true")
        {
            var width = .75 * screen.width;
            var height = screen.height/3;

            openDialog("exceptionDialog", "resizable=no", width, height);

            prepareToSendTransactionAction("ContractDetailTran", "showEditingExceptionDialog", "exceptionDialog");
        }
    }

    function checkForVOEditException()
    {
        if (voEditExceptionExists == "true")
        {
            var width = .75 * screen.width;
            var height = screen.height/3;

            openDialog("voEditExceptionDialog", "resizable=no", width, height);

            prepareToSendTransactionAction("ContractDetailTran", "showVOEditExceptionDialog", "voEditExceptionDialog");
        }
    }

    function continueTransactionAction(transaction, action)
    {
        f.ignoreEditWarnings.value = "true";
        prepareToSendTransactionAction(transaction, action, "contentIFrame");
    }

</script>

</head>

<body class="mainTheme" onLoad="init(); checkForEditingException(); checkForVOEditException()" style="border-style:solid; border-width:1;">
<form name= "contractCommitRidersForm" method="post" action="/PORTAL/servlet/RequestManager">

<input type="hidden" name="page" value="">
<jsp:include page="contractInfoHeader.jsp" flush="true"/>

  <table width="100%" height="48%" border="0" cellspacing="0" cellpadding="5">
    <tr>
<%
            if (caseCompanyStructure)
            {
%>
            <td align="left" nowrap>GroupNumber:&nbsp;
		      <input disabled type="text" id="groupNumber" name="groupNumber" value="<%= groupNumber %>" size="15" maxlength="15">
            </td>
<%
            } // close your if
%>
    </tr>
	<tr>
	  <td align="left" nowrap>EffectiveDate:&nbsp;
	    <input type="text" name="effectiveDate" maxlength="10" size="10" value="<%= effectiveDate%>" onKeyUp="DateFormat(this,this.value,event,false)" onBlur="DateFormat(this,this.value,event,true)">
            <a href="javascript:show_calendar('f.effectiveDate', f.effectiveDate.value);"><img src="/PORTAL/common/images/calendarIcon.gif" width="16" height="16" border="0" alt="Select a date from the calendar"></a>
      </td>
      <td align="left" nowrap>Issue Date:&nbsp;
	    <input type="text" name="issueDate" maxlength="10" size="10" value="<%= issueDate %>" onKeyUp="DateFormat(this,this.value,event,false)" onBlur="DateFormat(this,this.value,event,true)">
            <a href="javascript:show_calendar('f.issueDate', f.issueDate.value);"><img src="/PORTAL/common/images/calendarIcon.gif" width="16" height="16" border="0" alt="Select a date from the calendar"></a>
      </td>
      <td align="left" nowrap>Termination Date:&nbsp;
	    <input disabled type="text" name="terminationDate" maxlength="10" size="10" value= "<%= terminationDate %>">
	  </td>
    </tr>
    <tr>
      <td align="left" nowrap>Application Signed Date:&nbsp;
	    <input disabled type="text" name="appSignedDate" maxlength="10" size="10" value="<%= appSignedDate%>">
      </td>
      <td align="left" nowrap>Application Received Date:&nbsp;
	    <input disabled type="text" name="appReceivedDate" maxlength="10" size="10" value= "<%= appReceivedDate %>">
	  </td>
      <td align="left" nowrap>MEC Date:&nbsp;
	    <input disabled type="text" name="MECDate" maxlength="10" size="10" value= "<%= mecDate %>">
	  </td>
    </tr>
    <tr>
      <td align="left" nowrap>Face Amount:&nbsp;
        <input type="text" name="faceAmount" maxlength="13" size="13" value="<%= faceAmount %>" CURRENCY>
      </td>
      <td align="left" nowrap>Total Face Amount:&nbsp;
        <input disabled type="text" name="totalFaceAmount" maxlength="13" size="13" value="<%= totalFaceAmount %>" CURRENCY>
      </td>
	  <td align="left" nowrap>MEC Status:&nbsp;
	    <select disabled name="mecStatus">
          <option> Please Select </option>
         	<%
              for(int i = 0; i < mecStatuses.length; i++)
              {
                  String codeTablePK = mecStatuses[i].getCodeTablePK() + "";
                  String codeDesc    = mecStatuses[i].getCodeDesc();
                  String code        = mecStatuses[i].getCode();

                  if (mecStatus.equalsIgnoreCase(code))
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
      <td align="left" nowrap>Savings Percent:&nbsp;
        <input disabled type="text" name="savingsPercent" maxlength="6" size="6" value="<%= savingsPercent %>">
      </td>
      <td align="left" nowrap colspan="2">Term:&nbsp;
		<input disabled type="text" name="term" maxlength="2" size="2" value="<%= term %>">
    </tr>
    <tr>
	  <td align="left" nowrap>Coverage:&nbsp;
	    <select name="riderName">
          <option>Please Select</option>
         	<%
              for(int i = 0; i < riderNames.length; i++)
              {
                  String codeTablePK = riderNames[i].getCodeTablePK() + "";
                  String codeDesc    = riderNames[i].getCodeDesc();
                  String code        = riderNames[i].getCode();

                  if (riderCoverage.equalsIgnoreCase(code))
                  {
                      out.println("<option selected name=\"id\" value=\"" + codeTablePK + "\">" + codeDesc + "</option>");
                  }
                  else
                  {
                      out.println("<option name=\"id\" value=\"" + codeTablePK + "\">" + codeDesc + "</option>");
                  }
              }
         	%>
        </select>
      </td>
      <td align="left" nowrap>Death Benefit Option:&nbsp;
	    <select disabled name="deathBeneOption">
          <option>Please Select</option>
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
     <td  align="left" nowrap>Pending DBO Change Ind:&nbsp;
	    <input disabled type="checkbox" name="pendingDBOChangeInd" <%=pendingDBOChangeIndStatus %> >
     </td>
	</tr>
	<tr>
      <td align="left" nowrap>Issue State:&nbsp;
        <select disabled name="areaId">
          <option>Please Select</option>
            <%
              for(int i = 0; i < states.length; i++)
              {
                  String codeTablePK = states[i].getCodeTablePK() + "";
                  String codeDesc    = states[i].getCodeDesc();
                  String code        = states[i].getCode();

                  if (areaId.equalsIgnoreCase(code))
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
      <td align="left" nowrap>Status Change:&nbsp;
        <select disabled name="statusChange">
          <option>Please Select</option>
            <%
              for(int i = 0; i < statusChanges.length; i++)
              {
                  String codeTablePK = statusChanges[i].getCodeTablePK() + "";
                  String codeDesc    = statusChanges[i].getCodeDesc();
                  String code        = statusChanges[i].getCode();

                  if (statusChangeId.equalsIgnoreCase(code))
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
      <td align="left" nowrap>7702 Option:&nbsp;
        <input disabled type="text" name="option7702" maxlength="13" size="13" value="<%= option7702 %>">
      </td>
	</tr>
    <tr>
	  <td align="left" nowrap >Charge Deduct Division Ind:&nbsp;
        <input disabled type="checkbox" name="chargeDeductDivisionInd" <%=chargeDeductDivisionIndStatus%> >
      </td>
	  <td align="left" nowrap >Commitment Indicator:&nbsp;
        <input disabled type="checkbox" name="commitmentIndicator" <%=commitmentIndicatorStatus%> >
      </td>
	  <td align="left" nowrap >Commitment Amt:&nbsp;
        <input disabled type="text" name="commitmentAmount" size="19" maxlength="19" value="<%=commitmentAmount%>" CURRENCY>
      </td>
    </tr>
    <tr>
       <td align="left" nowrap>Policy Delivery Date:&nbsp;
           <input disabled type="text" name="policyDeliveryDate" value="<%= policyDeliveryDate %>" size='10' maxlength="10">
       </td>
       <td align="left" nowrap>Date In Effect:&nbsp;
           <input disabled type="text" name="dateInEffectDate" value="<%= dateInEffectDate %>" size='10' maxlength="10">
       </td>

	  <td align="left" nowrap >Waiver In Effect:&nbsp;
        <input disabled type="checkbox" name="waiverInEffect" <%=waiverInEffectStatus%> >
      </td>
    </tr>
    <tr>
      <td align="left" nowrap>Creation Date:&nbsp;
        <input disabled type="text" name="creationDate" maxlength="10" size="10" value="<%= creationDate %>">
      </td>
	  <td nowrap >Creation Operator:&nbsp;
        <input disabled type="text" name="creationOperator" maxlength="15" size="15" value="<%= creationOperator %>">
      </td>
	  <td align="left" nowrap >Start New 7Pay Indicator:&nbsp;
        <input type="checkbox" name="startNew7PayInd" <%=startNew7PayIndicatorStatus%> >
      </td>
    </tr>
    <tr>
 	  <td align="left" nowrap >Point In Scale Indicator:&nbsp;
        <input disabled type="checkbox" name="pointInScaleInd" <%=pointInScaleIndStatus%> >
      </td>
      <td align="left" nowrap >Waive Free Look Indicator:&nbsp;
        <input disabled type="checkbox" name="waiveFreeLookIndicator" <%= waiveFreeLookIndicator %>>
      </td>
      <td align="left" nowrap >Free Look Days Override:&nbsp;
        <input disabled type="text" name="freeLookDaysOverride" maxlength="2" size="2" value="<%= freeLookDaysOverride %>" >
      </td>
    </tr>
    <tr>
 	  <td align="left" nowrap>Free Look End Date:&nbsp;
        <input disabled type="text" name="freeLookEndDate" maxlength="10" size="10" value="<%= freeLookEndDate %>">
      </td>
      <td align="left" nowrap >Dialable Sales Load Percentage:&nbsp;
        <input disabled type="text" name="dialableSalesLoadPct" maxlength="19" size="19" value="<%= dialableSalesLoadPct %>">
      </td>
      <td align="left" nowrap>Contract Type:&nbsp;
        <select disabled name="contractType">
          <option value="null">Please Select</option>
          <%
              if (contractTypes != null)
              {
                  for(int i = 0; i < contractTypes.length; i++)
                  {

                      String codeDesc    = contractTypes[i].getCodeDesc();
                      String code        = contractTypes[i].getCode();

                     if (contractType.equalsIgnoreCase(code)) {

                         out.println("<option selected name=\"id\" value=\"" + code + "\">" + codeDesc + "</option>");
                     }
                     else  {

                         out.println("<option name=\"id\" value=\"" + code + "\">" + codeDesc + "</option>");
                     }
                  }
              }
          %>
        </select>
      </td>
    </tr>
	<tr>
	  <td align="left" nowrap colspan="5">
	    <input disabled type="checkbox" name="financialInd" <%=financialIndStatus %>><a href ="javascript:financialValues()">Financial</a>
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<input disabled type="checkbox" name="dateInd" <%=dateIndStatus %>><a href ="javascript:dateValues()">Dates</a>
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	 	<input disabled type="checkbox" name="notesInd" <%=notesIndStatus %>><a href ="javascript:notes()">Notes</a>
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	 	<input disabled type="checkbox" name="taxesInd" <%=taxesIndStatus %>><a href ="javascript:showTaxesDialog()">Taxes</a>
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        <input disabled type="checkbox" name="depositsInd" <%= depositsIndStatus %> disabled>
        <a href ="javascript:showDepositDialog()">Deposits</a>
	  </td>
	</tr>
	<tr>
	  <td nowrap colspan="3">
	  </td>
	</tr>
  </table>
  <table width="100%" height="2%" border="0" cellspacing="0" cellpadding="0">
	<tr>
	  <td align="left">
		<input type="button" name="add" value="   Add   " onClick="addLifeRider()">
		<input type="button" name="save" value=" Save " onClick="saveLifeRider()">
		<input type="button" name="cancel" value="Cancel" onClick="cancelLifeRider()">
		<input type="button" name="Delete" value="Delete" onClick="deleteLifeRider()">
	  </td>
	</tr>
  </table>
  <table class="summary" width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr class="heading">
      <th width="20%" align="left">Coverage</th>
      <th width="20%" align="left">Rider Seq #</th>
      <th width="20%" align="left">Effective Date</th>
      <th width="20%" align="left">Amount</th>
	  <th width="20%" align="left">End Date</th>
    </tr>
  </table>
  <span class="scrollableContent" style="border-style:solid; border-width:1;  position:relative; width:100%; height:30%; top:0; left:0; background-color:#BBBBBB">
    <table class="summary" id="contractMainSummary" width="100%" style="border-style:solid; border-width:0;" cellspacing="0" cellpadding="0">
      <%
          int rowId   = 0;

          String rowToMatch = "";
          String trClass = "";
          boolean selected = false;
          String endDate = "";

          rowToMatch =  optionId;

          if (rowToMatch.equals(rowToMatchBase) &&
              !rowToMatchBase.equals(""))
          {
              trClass = "highlighted";
              selected = true;
          }
          else
          {
              trClass = "default";
              selected = false;
          }
          
          faceAmount = Util.initString(formBean.getValue("faceAmount"), "0.00");
      %>
        <tr class="<%= trClass %>" isSelected="<%= selected %>" id="rowId_<%= rowId %>"  onClick="selectRow()"
            onMouseOver="highlightRow()" onMouseOut="unhighlightRow()" sequenceNumber="0" key="base">
          <td width="20%" nowrap id="optionId_<%= rowId %>">
            <%= optionId %>
          </td>
          <td width="20%" nowrap id="optionId_<%= rowId %>">
            <%= "0" %>
          </td>
          <td width="20%" nowrap id="effectiveDateId_<%= rowId %>">
            <%= effectiveDate %>
          </td>
          <td width="20%" nowrap id="amountId_<%= rowId %>">
            <script>document.write(formatAsCurrency(<%= faceAmount %>))</script>
          </td>
          <td width="20%" nowrap id="endDateId_<%= rowId %>">
            <%= endDate %>
          </td>
        </tr>
      <%

          Map allRiders = contractRiders.getPageBeans();

          Map sortedRidersByRiderNumber = new TreeMap();

          Iterator itrKeys = allRiders.keySet().iterator();

          while(itrKeys.hasNext())
          {
              String key = (String) itrKeys.next();
              sortedRidersByRiderNumber.put(key, allRiders.get(key));
          }

          String riderSequenceNumber = "";

          Iterator it = sortedRidersByRiderNumber.values().iterator();

          while (it.hasNext())  
          {
              rowId++;

              PageBean rider = (PageBean)it.next();

              String optionCodePK = rider.getValue("optionCodePK");
              optionId = CodeTableWrapper.getSingleton().getCodeTableEntry(Long.parseLong(optionCodePK)).getCode();
              String riderEffDate = rider.getValue("effectiveDate");
              faceAmount    = Util.initString(rider.getValue("faceAmount"), "0.00");
              String riderEndDate = rider.getValue("terminateDate");
              
              rowToMatch =  rider.getValue("riderBeanKey");

              if (rowToMatch.equals(rowToMatchBase) &&
                  !rowToMatchBase.equals(""))
              {
                  trClass = "highlighted";
                  selected = true;
              }
              else
              {
                  trClass = "default";
                  selected = false;
              }

              riderSequenceNumber = rider.getValue("riderNumber");
      %>
        <tr class="<%= trClass %>" isSelected="<%= selected %>" id="rowId_<%= rowId %>" optionCodePK="<%= optionCodePK %>"
            onClick="selectRow()" onMouseOver="highlightRow()" onMouseOut="unhighlightRow()"  key="<%= rowToMatch%>"
            sequenceNumber="<%= riderSequenceNumber %>">
          <td width="20%" nowrap id="optionId_<%= rowId %>">
            <%= optionId %>
          </td>
          <td width="20%" nowrap id="optionId_<%= rowId %>">
            <%= riderSequenceNumber %>
          </td>
          <td width="20%" nowrap id="effectiveDateId_<%= rowId %>">
            <%= riderEffDate %>
          </td>
          <td width="20%" nowrap id="amountId_<%= rowId %>">
            <script>document.write(formatAsCurrency(<%= faceAmount %>))</script>
          </td>
          <td width="20%" nowrap id="endDateId_<%= rowId %>">
            <%= riderEndDate %>
          </td>
        </tr>
      <%
          }// end while
      %>
    </table>
  </span>

<!-- ****** HIDDEN FIELDS ***** //-->
 <input type="hidden" name="transaction"  value="">
 <input type="hidden" name="action"       value="">
 <input type="hidden" name="selectedOptionId" value="">
 <input type="hidden" name="companyStructureId" value="<%= companyStructureId %>">

 <input type="hidden" name="financialIndStatus" value="<%= financialIndStatus %>">
 <input type="hidden" name="investmentIndStatus" value="">
 <input type="hidden" name="dateIndStatus" value="<%= dateIndStatus %>">
 <input type="hidden" name="notesIndStatus" value="<%= notesIndStatus %>">
 <input type="hidden" name="taxesIndStatus" value="<%= taxesIndStatus %>">
 <input type="hidden" name="depositsIndStatus" value="<%= depositsIndStatus %>">


 <input type="hidden" name="segmentPK" value="<%= segmentPK %>">
 <input type="hidden" name="payoutPK" value="<%= payoutPK %>">
 <input type="hidden" name="segmentStatus" value = "<%= segmentStatus %>">

 <!--- *****Dialog Values for Financial*****//-->
 <input type="hidden" name="purchaseAmount" value="<%=purchaseAmount %>">
 <input type="hidden" name="faceAmount" value="<%= faceAmount %>">
 <input type="hidden" name="savingsPercent" value="<%= savingsPercent %>">
 <input type="hidden" name="dismembermentPercent" value="<%= dismembermentPercent %>">
 <input type="hidden" name="costBasis" value="<%=costBasis %>">
 <input type="hidden" name="exclusionRatio" value="<%=exclusionRatio %>">
 <input type="hidden" name="amountPaidToDate" value="<%=amountPaidToDate %>">
 <input type="hidden" name="accumulatedLoads" value="<%=accumulatedLoads %>">
 <input type="hidden" name="withdrawlYrToDate" value="<%=withdrawlYrToDate %>">
 <input type="hidden" name="yearlyTaxableBenefit" value="<%=yearlyTaxableBenefit %>">
<%-- <input type="hidden" name="mrdAmount" value="<%=mrdAmount %>">--%>
 <input type="hidden" name="lumpSumPaidToDate" value="<%=lumpSumPaidToDate %>">

 <input type="hidden" name="paymentAmount" value="<%=paymentAmount %>">
 <input type="hidden" name="totalExpectedReturn" value="<%=totalExpectedReturn %>">
 <input type="hidden" name="recoveredCostBasis" value="<%=recoveredCostBasis %>">
 <input type="hidden" name="finalDistributionAmount" value="<%=finalDistributionAmount %>">
 <input type="hidden" name="numberOfRemainingPayments" value="<%=numberOfRemainingPayments %>">
 <input type="hidden" name="lumpSumYrToDate" value="<%=lumpSumYrToDate %>">
 <input type="hidden" name="accumulatedFees" value="<%=accumulatedFees %>">
 <input type="hidden" name="amountPaidYrToDate" value="<%=amountPaidYrToDate %>">
 <input type="hidden" name="ownerId" value="<%= ownerId %>">
 <input type="hidden" name="freeAmount" value="<%= freeAmount %>">
 <input type="hidden" name="freeAmountRemaining" value="<%= freeAmountRemaining %>">
 <input type="hidden" name="guidelineSinglePrem" value="<%= guidelineSinglePrem %>">
 <input type="hidden" name="guidelineLevelPrem" value="<%= guidelineLevelPrem %>">
 <input type="hidden" name="tamra" value="<%= tamra %>">
 <input type="hidden" name="contractGroupFK" value="<%= contractGroupFK %>">
 <input type="hidden" name="billScheduleFK" value="<%= billScheduleFK %>">
 <input type="hidden" name="batchContractSetupFK" value="<%= batchContractSetupFK %>">
 <input type="hidden" name="tamraStartDate" value="<%= tamraStartDate %>">
 <input type="hidden" name="MAPEndDate" value="<%= MAPEndDate %>">
 <input type="hidden" name="mecGuidelineLevelPremium" value="<%= mecGuidelineLevelPremium %>">
 <input type="hidden" name="mecGuidelineSinglePremium" value="<%= mecGuidelineSinglePremium %>">
 <input type="hidden" name="cumGuidelineLevelPremium" value="<%= cumGuidelineLevelPremium %>">
 <input type="hidden" name="startNew7PayIndicatorStatus" value="<%= startNew7PayIndicatorStatus %>">
 <input type="hidden" name="pendingDBOChangeIndStatus" value="<%= pendingDBOChangeIndStatus %>">
 <input type="hidden" name="deathBeneOption" value="<%= deathBeneOption %>">

 <!--- *****Dialog Values for Dates*****//-->
 <input type="hidden" name="effectiveDate" value="<%= effectiveDate %>">
 <input type="hidden" name="terminationDate" value="<%= terminationDate %>">
 <input type="hidden" name="finalPayDate" value="<%=finalPayDate %>">
 <input type="hidden" name="statusChangeDate" value="<%=statusChangeDate %>">
 <input type="hidden" name="lastCheckDate" value="<%=lastCheckDate %>">
 <input type="hidden" name="nextPaymentDate" value="<%=nextPaymentDate %>">
 <input type="hidden" name="certainPeriodEndDate" value="<%= certainPeriodEndDate %>">
 <input type="hidden" name="lastAnnivDate" value="<%= lastAnnivDate %>">
 <input type="hidden" name="appSignedDate" value="<%= appSignedDate %>">
 <input type="hidden" name="appReceivedDate" value="<%= appReceivedDate %>">
 <input type="hidden" name="issueDate" value="<%= issueDate %>">
 <input type="hidden" name="ignoreEditWarnings" value="">
 <input type="hidden" name="creationDate" value="<%= creationDate %>">
 <input type="hidden" name="creationOperator" value="<%= creationOperator %>">
 <input type="hidden" name="startDate" value="<%= startDate %>">
 <input type="hidden" name="option7702" value="<%= option7702 %>">
 <input type="hidden" name="lifePK" value="<%= lifePK %>">
 <input type="hidden" name="freeLookEndDate" value="<%= freeLookEndDate %>">
 <input type="hidden" name="maxNetAmountAtRisk" value="<%= maxNetAmountAtRisk %>">
 <input type="hidden" name="policyDeliveryDate" value="<%= policyDeliveryDate %>">
 <input type="hidden" name="chargeDeductDivisionIndStatus" value="<%= chargeDeductDivisionIndStatus %>">
 <input type="hidden" name="pointInScaleIndStatus" value="<%= pointInScaleIndStatus %>">
 <input type="hidden" name="commitmentIndicatorStatus" value="<%= commitmentIndicatorStatus %>">
 <input type="hidden" name="commitmentAmount" value="<%= commitmentAmount %>">
 <input type="hidden" name="creationDate" value="<%= creationDate %>">
 <input type="hidden" name="MECDate" value="<%= mecDate %>">
 <input type="hidden" name="waiveFreeLookIndicator" value="<%= waiveFreeLookIndicator %>">
 <input type="hidden" name="freeLookDaysOverride" value="<%= freeLookDaysOverride %>">
 <input type="hidden" name="dialableSalesLoadPct" value="<%= dialableSalesLoadPct %>">
 <input type="hidden" name="segmentName" value="<%= segmentName %>">
 <input type="hidden" name="chargeDeductAmount" value="<%= chargeDeductAmount %>">
 <input type="hidden" name="sequenceNumber" value="<%= sequenceNumber %>">
 <input type="hidden" name="riderBeanKey" value="<%= riderBeanKey %>">
 <input type="hidden" name="paidToDate" value="<%= paidToDate %>">
 <input type="hidden" name="lapsePendingDate" value="<%= lapsePendingDate %>">
 <input type="hidden" name="lapseDate" value="<%= lapseDate %>">
 <input type="hidden" name="dateOfDeathValue" value="<%= dateOfDeathValue %>">
 <input type="hidden" name="annuitizationValue" value="<%= annuitizationValue %>">
 <input type="hidden" name="suppOriginalContractNumber" value="<%= suppOriginalContractNumber %>">
 <input type="hidden" name="openClaimEndDate" value="<%= openClaimEndDate %>">
 <input type="hidden" name="chargeCodeStatus" value="<%= chargeCodeStatus %>">
 <input type="hidden" name="qualNonQual" value="<%= qualNonQual %>">
 <input type="hidden" name="totalFaceAmount" value="<%= totalFaceAmount %>">
 <input type="hidden" name="lastDayOfMonthInd" value="<%= lastDayOfMonthInd %>">
 <input type="hidden" name="ageAtIssue" value="<%= ageAtIssue %>">
 <input type="hidden" name="originalStateCT" value="<%= originalStateCT %>">
 <input type="hidden" name="worksheetTypeCT" value="<%= worksheetTypeCT %>">
 <input type="hidden" name="riderNumber" value="<%= riderNumber %>">

 <!-- recordPRASEEvents is set by the toolbar -->
 <input type="hidden" name="recordPRASEEvents" value="false">
</form>

</body>
</html>
