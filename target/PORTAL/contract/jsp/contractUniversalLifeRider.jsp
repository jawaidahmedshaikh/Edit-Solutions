 <!--
 * User: unknown
 * Date: Jan 1, 2000
 * Time: unknown
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 -->
<!-- contractUniversalLifeRider.jsp //-->

<%@ page  errorPage="/common/jsp/error.jsp" autoFlush="true" %>
<%@ page import="java.util.*,
                 edit.common.*,
                 edit.common.vo.*,
                 edit.common.vo.user.*,
                 fission.beans.*,
                 edit.portal.exceptions.*,
                 edit.common.exceptions.VOEditException,
                 fission.utility.Util,
                 fission.utility.*,
                 edit.portal.common.session.UserSession,
                 engine.*,
                 group.*" %>

<jsp:useBean id="contractMainSessionBean"
    class="fission.beans.SessionBean" scope="session"/>

<jsp:useBean id="contractRiders"
    class="fission.beans.SessionBean" scope="session"/>

<jsp:useBean id="statusBean"
    class="fission.beans.PageBean" scope="request"/>

<%

    PortalEditingException editingException = (PortalEditingException) session.getAttribute("portalEditingException");
    String editingExceptionExists = "false";
    if (editingException != null){

        editingExceptionExists = "true";
    }

    String pageFunction = Util.initString((String)request.getAttribute("pageFunction"), "");
    VOEditException voEditException = (VOEditException) session.getAttribute("VOEditException");
    String voEditExceptionExists = "false";
    if (voEditException != null){

        voEditExceptionExists = "true";
    }

    CodeTableWrapper codeTableWrapper = CodeTableWrapper.getSingleton();

    PageBean formBean       = contractMainSessionBean.getPageBean("formBean");

    String riderBeanKey = Util.initString((String) request.getAttribute("riderBeanKey"), "");

    CaseProductUnderwriting[] caseProductUnderwritings = null;
    String optionCodePK = formBean.getValue("selectedCoveragePK");
    String riderOption = Util.initString(formBean.getValue("riderOption"), "");
    String riderCode = Util.initString(codeTableWrapper.getCodeByCodeTableNameAndCodeDesc("RIDERNAME", riderOption), "");

    String increaseOptionStatus = formBean.getValue("increaseOptionStatus");
    if (increaseOptionStatus.equals("display"))
    {
        caseProductUnderwritings = (CaseProductUnderwriting[])request.getAttribute("caseProductUnderwriting");
    }

    PageBean riderBean      = null;
    String riderSegmentPK   = null;
    String riderLifePK = null;
    String segmentStatus = formBean.getValue("segmentStatus");
    String optionId = formBean.getValue("optionId");

    String companyStructureId = Util.initString(formBean.getValue("companyStructureId"), "0");

    String optionKey = "";
    if (optionId != null && !optionId.equals(""))
    {
        optionKey = codeTableWrapper.getCodeTablePKByCodeTableNameAndCode("OPTIONCODE", optionId) + "";
    }
    
    String terminateDate  = "";

    if (!riderBeanKey.equals("") && !riderBeanKey.equals("base"))
    {
        riderBean = contractRiders.getPageBean(riderBeanKey);
        
        riderSegmentPK = riderBean.getValue("riderSegmentPK");
        riderLifePK = riderBean.getValue("lifePK");
        
        terminateDate   = riderBean.getValue("terminateDate");
    }
    else
    {
        riderBean = new PageBean();
    }
    
    String riderTerminationDate  = terminateDate;

    String segmentPK        = formBean.getValue("segmentPK");
    String payoutPK         = formBean.getValue("payoutPK");
    String lifePK           = formBean.getValue("lifePK");
    String riderType        = Util.initString(formBean.getValue("riderType"), "");

    String amount           = formBean.getValue("purchaseAmount");
    String savingsPercent   = formBean.getValue("savingsPercent");
    String dismembermentPercent = formBean.getValue("dismembermentPercent");
    String startDate       = formBean.getValue("startDate");

    String appSignedDate  = formBean.getValue("appSignedDate");

    String appReceivedDate  = formBean.getValue("appReceivedDate");

    String issueDate        = formBean.getValue("issueDate");
	String paidToDate 		= formBean.getValue("paidToDate");
    String dateInEffectDate    = formBean.getValue("dateInEffectDate");
    String quoteDate      = formBean.getValue("quoteDate");
    String exchangeInd    = formBean.getValue("exchangeInd");
    String annualPremium  = Util.initString(riderBean.getValue("annualPremium"), "0");
    String postIssueStatus      = formBean.getValue("postIssueStatus");
    String issueStateORInd = Util.initString(formBean.getValue("issueStateORInd"), "");
    String originalStateCT = Util.initString(formBean.getValue("originalStateCT"), "");

    String commitmentIndicatorStatus  = formBean.getValue("commitmentIndicatorStatus");
    String commitmentAmount     = formBean.getValue("commitmentAmount");
    String policyDeliveryDate  = formBean.getValue("policyDeliveryDate");
    String chargeDeductDivisionIndStatus = formBean.getValue("chargeDeductDivisionIndStatus");
    String pointInScaleIndStatus = formBean.getValue("pointInScaleIndStatus");
    String chargeDeductAmount  = formBean.getValue("chargeDeductAmount");

    String creationDate = Util.initString(riderBean.getValue("creationDate"), "");
    String creationOperator = Util.initString(riderBean.getValue("creationOperator"), "");
    String waiverInEffectStatus = formBean.getValue("waiverInEffectStatus");
    String dialableSalesLoadPct                = formBean.getValue("dialableSalesLoadPct");

    CodeTableVO[] states    = codeTableWrapper.getCodeTableEntries("STATE", Long.parseLong(companyStructureId));
    String   areaId	         = formBean.getValue("areaId");

    CodeTableVO[] statusChanges = codeTableWrapper.getCodeTableEntries("STATUSCHANGE", Long.parseLong(companyStructureId));
    String statusChangeId   = formBean.getValue("statusChange");

    CodeTableVO[] frequencies = codeTableWrapper.getCodeTableEntries("FREQUENCY", Long.parseLong(companyStructureId));
    String   frequencyId     = formBean.getValue("frequencyId");

    CodeTableVO[] qualNonQuals = codeTableWrapper.getCodeTableEntries("QUALNONQUAL", Long.parseLong(companyStructureId));
    String qualNonQual       = formBean.getValue("qualNonQual");

    CodeTableVO[] qualifiedTypes = codeTableWrapper.getCodeTableEntries("QUALIFIEDTYPE", Long.parseLong(companyStructureId));
    String qualifiedType     = formBean.getValue("frequencyId");

    String certainDuration     = formBean.getValue("certainDuration");

    String reduce1           = formBean.getValue("reduce1");
    String reduce2           = formBean.getValue("reduce2");

    String financialIndStatus                = formBean.getValue("financialIndStatus");
    String dateIndStatus                     = formBean.getValue("dateIndStatus");
    String notesIndStatus                    = formBean.getValue("notesIndStatus");
    String taxesIndStatus                    = formBean.getValue("taxesIndStatus");
    String depositsIndStatus                 = formBean.getValue("depositsIndStatus");

    String purchaseAmount            = formBean.getValue("purchaseAmount");
    String costBasis                 = formBean.getValue("costBasis");
    String exclusionRatio            = formBean.getValue("exclusionRatio");
    String amountPaidToDate          = formBean.getValue("amountPaidToDate");
    String accumulatedLoads          = formBean.getValue("accumulatedLoads");
    String withdrawlYrToDate         = formBean.getValue("withdrawlYrToDate");
    String yearlyTaxableBenefit      = formBean.getValue("yearlyTaxableBenefit");
    String lumpSumPaidToDate         = formBean.getValue("lumpSumPaidToDate");
    String paymentAmount             = formBean.getValue("paymentAmount");
    String totalExpectedReturn       = formBean.getValue("totalExpectedReturn");
    String recoveredCostBasis        = formBean.getValue("recoveredCostBasis");
    String finalDistributionAmount   = formBean.getValue("finalDistributionAmount");
    String numberOfRemainingPayments = formBean.getValue("numberOfRemainingPayments");
    String lumpSumYrToDate        	 = formBean.getValue("lumpSumYrToDate");
    String accumulatedFees        	 = formBean.getValue("accumulatedFees");
    String amountPaidYrToDate     	 = formBean.getValue("amountPaidYrToDate");
    String terminateDateBase         = formBean.getValue("terminationDate");
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
    String MAPEndDate                = formBean.getValue("MAPEndDate");
    String mecGuidelineSinglePremium = formBean.getValue("mecGuidelineSinglePremium");
    String mecGuidelineLevelPremium  = formBean.getValue("mecGuidelineLevelPremium");
    String cumGuidelineLevelPremium  = formBean.getValue("cumGuidelineLevelPremium");
    String freeLookEndDate          = formBean.getValue("freeLookEndDate");
    String maxNetAmountAtRisk        = formBean.getValue("maxNetAmountAtRisk");
    String effDateBase               = formBean.getValue("effectiveDate");

    String waiveFreeLookIndicator    = formBean.getValue("waiveFreeLookIndicator");
    String freeLookDaysOverride      = formBean.getValue("freeLookDaysOverride");
    String segmentName               = formBean.getValue("segmentName");
    String ageAtIssueBase           = formBean.getValue("ageAtIssue");
    
    CodeTableVO[] ratedGenders = codeTableWrapper.getCodeTableEntries("RATEDGENDER", Long.parseLong(companyStructureId));
    String ratedGenderCT     = formBean.getValue("ratedGenderCT"); 
    
    CodeTableVO[] groupPlans = codeTableWrapper.getCodeTableEntries("GROUPPLAN", Long.parseLong(companyStructureId));
    String groupPlan     = formBean.getValue("groupPlan");
    
    CodeTableVO[] underwritingClasses = codeTableWrapper.getCodeTableEntries("UNDERWRITINGCLASS", Long.parseLong(companyStructureId));
    String underwritingClass = formBean.getValue("underwritingClass");
    
    CodeTableVO[] deathBenefitOptions = codeTableWrapper.getCodeTableEntries("DEATHBENOPT", Long.parseLong(companyStructureId));

    String sequenceNumber = (String) request.getAttribute("riderNumber");

    String rowToMatchBase = riderBeanKey;

    String effectiveDate = "";
    String riderEffectiveDate = "";

    String riderCoverage    = "";
    String faceAmount       = "0.00";
    String segmentAmount    = "0.00";
//    String startNew7PayIndicatorStatus = "";
    String riderStatus      = "";
    String riderNumber      = "";
    String units   = "";
    String unitsOnLoad   = "";
    String segmentAmountOnLoad = "";
	String unitsChangeEffectiveDate = "";
    String claimStopDate    = "";
    String multiple = "";
    String gioOption = "";
    String insuredName = "";
    String valueAtIssue = "";
    String ageAtIssue  = "";
    String commissionPhaseID = "";
    String commissionPhaseOverride = "";

    if (pageFunction.equalsIgnoreCase("add"))
    {
//        startNew7PayIndicatorStatus = formBean.getValue("startNew7PayIndicatorStatus");
        riderStatus = "Pending";
        issueDate = "";
        effectiveDate = "";
        ageAtIssue = "";
        creationDate = "";
        creationOperator = "";
    }
    else if (riderBean != null)
    {
        issueDate = riderBean.getValue("issueDate");

        effectiveDate     	 = riderBean.getValue("effectiveDate");
        riderEffectiveDate	 = riderBean.getValue("effectiveDate");
        riderCoverage = riderBean.getValue("coverage");
        faceAmount = Util.initString(riderBean.getValue("faceAmount"), "0.00");
        segmentAmount = Util.initString(riderBean.getValue("segmentAmount"), "0.00");
        segmentAmountOnLoad = segmentAmount;
//        startNew7PayIndicatorStatus = riderBean.getValue("startNew7PayIndicatorStatus");
        riderStatus    = riderBean.getValue("riderStatus");
        riderNumber    = riderBean.getValue("riderNumber");
        units = riderBean.getValue("units");
        unitsOnLoad = units;
        unitsChangeEffectiveDate = riderBean.getValue("unitsChangeEffectiveDate");
        claimStopDate    = riderBean.getValue("claimStopDate");
        insuredName = Util.initString(riderBean.getValue("insuredName"), "");
        gioOption = Util.initString(riderBean.getValue("gioOption"), "");
        multiple = Util.initString(riderBean.getValue("multiple"), "");
	    valueAtIssue   = Util.initString((String)riderBean.getValue("valueAtIssue"), "unchecked");
        ageAtIssue = riderBean.getValue("ageAtIssue");
        ratedGenderCT = riderBean.getValue("ratedGenderCT");
        groupPlan = riderBean.getValue("groupPlan");
        underwritingClass = riderBean.getValue("underwritingClass");
        commissionPhaseID         = riderBean.getValue("commissionPhaseID");
        commissionPhaseOverride   = riderBean.getValue("commissionPhaseOverride");
    }
    else
    {
        issueDate = formBean.getValue("issueDate");

        effectiveDate = formBean.getValue("effectiveDate");
        
        faceAmount = Util.initString(formBean.getValue("faceAmount"), "0.00");
        segmentAmount = Util.initString(formBean.getValue("segmentAmount"), "0.00");
//        startNew7PayIndicatorStatus = formBean.getValue("startNew7PayIndicatorStatus");
        riderStatus = "Pending";
        commissionPhaseID         = formBean.getValue("commissionPhaseID");
        commissionPhaseOverride   = formBean.getValue("commissionPhaseOverride");    }

    String errorMessage = (String) request.getAttribute("errorMessage");
    if (errorMessage == null)
    {
        errorMessage = "";
    }

    UserSession userSession = (UserSession) session.getAttribute("userSession");

    AreaValue[] areaValues = AreaValue.findBy_ProductStructurePK_Grouping_Field(Long.parseLong(companyStructureId), "CASERIDERS", "GIOOPTDATES");
    // AreaValue[] multipleAreaValues = AreaValue.findBy_ProductStructurePK_Grouping_Field(Long.parseLong(companyStructureId), "CASERIDERS", "EOBMULTIPLE");

%>

<html>

<script src="/PORTAL/common/javascript/commonJavascriptFunctions.js"></script>
<script src="/PORTAL/common/javascript/formatDateFunctions.js"></script>
<script src="/PORTAL/common/javascript/calendarSelector.js"></script>
<link rel="stylesheet"
	href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
<script src="//code.jquery.com/jquery-1.10.2.js"></script>
<script src="//code.jquery.com/ui/1.11.4/jquery-ui.js"></script>
<script src="/PORTAL/common/javascript/jquery.maskedinput.js"></script>
<link rel="stylesheet" href="/resources/demos/style.css">

<script>
  $('document').ready(function() {
    $( ".datepicker" ).datepicker({
	       dateFormat: "mm/dd/yy",
	       altFormat: "yy/mm/dd",
	       showOn: "button",
	       buttonImage: "/PORTAL/common/images/calendarIcon.gif" });
    $( ".datepicker" ).mask("99/99/9999", {placedholder:"mm/dd/yyyy"});
  });
</script>


<script language="Javascript1.2">

    var editingExceptionExists = "<%= editingExceptionExists %>";
    var voEditExceptionExists = "<%= voEditExceptionExists %>";
    var errorMessage = "<%= errorMessage %>";
    var dialog = null;
    var f = null;
    var segmentPK = "<%= segmentPK %>";
    var origOptionId = "<%= optionKey %>";
    var riderName = "<%= riderType %>";
    var segmentStatus = "<%= riderStatus %>";
    var riderType = "<%= riderBeanKey %>";
    var pageFunction = "<%= pageFunction %>";
    var contractIsLocked = "<%= userSession.getSegmentIsLocked()%>";
    var segmentStatus = "<%= segmentStatus %>";
    var increaseOptionStatus = "<%= increaseOptionStatus %>";
    var shouldShowLockAlert = true;
    var editableContractStatus = true;

    function init()
    {
        f = document.contractCommitRidersForm;

        top.frames["main"].setActiveTab("mainTab");

        if (errorMessage != "")
        {
            alert(errorMessage);
        }

        var username = "<%= userSession.getUsername() %>";
        var elementPK = "<%= userSession.getSegmentPK() %>";
        top.frames["header"].updateLockState(contractIsLocked, username, elementPK);

        shouldShowLockAlert = <%= ! userSession.getSegmentIsLocked() %>;
        
     	// check for terminated status to disallow contract updates
        editableContractStatus = <%= userSession.getEditableSegmentStatus()%>;

        for (var i = 0; i < f.elements.length; i++)
        {
            var elementType = f.elements[i].type;

            if ((elementType == "text" || elementType == "button" || elementType == "select-one") && (shouldShowLockAlert == true ||
            		editableContractStatus == false))
            {
                f.elements[i].onclick = showLockAlert;
                f.elements[i].onkeydown = showLockAlert;
            }
        }

        if (increaseOptionStatus == "")
        {
            document.getElementById("gioOption").disabled = true;
        }

        formatCurrency();

        disableFields();
        
    	top.frames["header"].document.getElementById("btnProposal").src = "/PORTAL/common/images/btnProposalDis.gif";
    	top.frames["header"].document.getElementById("btnProposal").disabled = true;
            
    }
    
    function disableFields()
    {
        if (pageFunction == "add")
        {
            f.effectiveDate.disabled = false;
            f.issueDate.disabled = false;
            if (riderType != "base") {
                f.segmentAmount.disabled = true;
            } else {
            	f.segmentAmount.disabled = false;
            }
            f.units.disabled = false;
        }
        else if (riderType != "base" &&
            riderType != "" &&
            segmentStatus != "Pending")
        {
            f.effectiveDate.disabled = true;
            f.issueDate.disabled = true;
            f.terminateDate.disabled = true;
            f.segmentAmount.disabled = true;
            f.riderOption.disabled = true;
            f.units.disabled = true;

        }
        
        if(contractIsLocked == "true")
        {
            f.claimStopDate.disabled = false;
            f.units.disabled = false;
            f.terminateDate.disabled = false;
            f.ratedGenderCT.disabled = true;
        }
        else
        {
            f.claimStopDate.disabled = true;
            f.units.disabled = true;
            f.terminateDate.disabled = true;
            f.ratedGenderCT.disabled = true;
        }

        //  Do not allow the user to change the claim stop date for segmentStatus other than LTC and Waiver
        if (segmentStatus != "LTC" && segmentStatus != "Waiver" && segmentStatus != "Extension")
        {
            f.claimStopDate.disabled = true;
        }

        changeImageState();
    }

    function checkIfUnitsChangeEffectiveDateNeededAndEmpty() {
        if (checkIfUnitsChangeEffectiveDateNeeded() && document.getElementById('unitsChangeEffectiveDate').value == "") {
           	return true; 
        }
        return false;
    }
    
    function changeImageState()
    {
        var calendarImageElement1 = document.getElementById('calendarImg1');

        if (f.effectiveDate.disabled == true)
        {
            calendarImageElement1.style.visibility = "hidden";
        }
        else
        {
            calendarImageElement1.style.visibility = "visible";
        }

        var calendarImageElement2 = document.getElementById('calendarImg2');

        if (f.issueDate.disabled == true)
        {
            calendarImageElement2.style.visibility = "hidden";
        }
        else
        {
            calendarImageElement2.style.visibility = "visible";
        }

        var calendarImageElement3 = document.getElementById('calendarImg3');

        if (f.terminateDate.disabled == true)
        {
            calendarImageElement3.style.visibility = "hidden";
        }
        else
        {
            calendarImageElement3.style.visibility = "visible";
        }

        var calendarImageElement4 = document.getElementById('calendarImg4');

        if (f.claimStopDate.disabled == true)
        {
            calendarImageElement4.style.visibility = "hidden";
        }
        else
        {
            calendarImageElement4.style.visibility = "visible";
        }
    }


    function showDepositDialog()
    {
        var width = .95 * screen.width;
        var height = .55 * screen.height;

        openDialog("depositDialog","top=0,left=0,resizable=no", width, height);

        prepareToSendTransactionAction("ContractDetailTran", "showDepositDialog", "depositDialog");
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

    function saveLifeRider()
    {
    	var formEffectiveDate;
    	var formTerminationDate;
    	var paidToDate = new Date("<%= paidToDate %>");
    			
    	// Determine if effective date has been updated:
    	var effectiveDateChange = false;

    	if (f.effectiveDate.value !== undefined) {
			formEffectiveDate = new Date(f.effectiveDate.value);
			var existingEffectiveDate = new Date("<%= riderEffectiveDate %>");
			
			if (existingEffectiveDate.getTime() !== formEffectiveDate.getTime()) {
				effectiveDateChange = true;
			}
    	} 
		
    	// Determine if terminationDate has been updated:
		var terminationDateChange = false;
				
		if (f.terminateDate.value !== undefined) {
			formTerminationDate = new Date(f.terminateDate.value);
			var existingTerminationDate = new Date("<%= riderTerminationDate %>");

			if (existingTerminationDate.getTime() !== formTerminationDate.getTime()) {
				terminationDateChange = true;
			}
		}
		
        if (f.riderOption.value == "" || f.riderOption.value == "Please Select") {
            alert("Select coverage for rider");
        } else if (f.effectiveDate.value == "" || f.effectiveDate.value == undefined) {
            alert("Select effective date for rider");

        } else if (effectiveDateChange && (formEffectiveDate.getTime() < paidToDate.getTime())) {
        	alert("Effective Date Must Be After Paid to Date!!");
        } else if (checkIfUnitsChangeEffectiveDateNeededAndEmpty()) {
			alert("Units Changed Effective Date required when the Units have been modified.");   
        } else if (terminationDateChange && (formTerminationDate.getTime() < paidToDate.getTime())) {
        	alert("Termination Date Must Be After Paid to Date!!");
        
        } else {

            var width = 0.32 * screen.width;
            var height = 0.20 * screen.height;
            
        	openDialog("saveDialog", "top=10,left=10,resizable=yes", width, height);

        	var contentIFrame = top.frames["main"].window.frames["contentIFrame"];

        	contentIFrame.prepareToSendTransactionAction("ContractDetailTran", "saveLifeRider", "saveDialog");
        }
    }

    function addLifeRider()
    {
       clearForm();

        var width = .30 * screen.width;
        var height = .15 * screen.height;

		openDialog("contractRiderCoverageSelectionDialog","top=0,left=0,resizable=no", width, height);

        prepareToSendTransactionAction("ContractDetailTran", "showRiderCoverageSelectionDialog", "contractRiderCoverageSelectionDialog");
    }

    function clearForm()
    {
        f.effectiveDate.value    = "";
        f.issueDate.value    = "";
        f.terminateDate.value    = "";
        f.segmentAmount.value = 0;
        f.ratedGenderCT.value = "";
        
<%--        f.riderName.options[0].selected = true;--%>
        f.claimStopDate.value    = "";
        f.multiple.value = "";
        f.gioOption.value = "";
        f.insuredName.value = "";
        f.appReceivedDate.value = "";
        f.appSignedDate.value = "";
        f.riderOption.value = "";
    }

    function prepareToSendTransactionAction(transaction, action, target)
    {
        // Certain fields have been disabled, and as such, their values will not
        // be submitted. We will enable them for a moment in time so that
        // they are submitted.
        f.segmentAmount.disabled = false;
        f.effectiveDate.disabled = false;
        f.issueDate.disabled = false;
        f.units.disabled = false;
        f.claimStopDate.disabled = false;
        f.terminateDate.disabled = false;

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

        openDialog("financialValues", "left=0,top=0,resizable=no", width, height);

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

        openDialog("taxesDialog", "top=0,left=0,resizable=no", width, height);

        prepareToSendTransactionAction("ContractDetailTran", "showTaxesDialog", "taxesDialog");
    }

    function showUnitsChangeEffectiveDate() 
    {
    	if (pageFunction != "add") 
        {
        	document.getElementById('unitsChangeEffectiveDateCell').style.visibility = "visible";
        }

    }

    function checkIfUnitsChangeEffectiveDateNeeded()
    {
    	var unitsOnLoad = "<%=unitsOnLoad%>";
    	var segmentAmountOnLoad = "<%=segmentAmountOnLoad%>";
    	var currentUnits = document.getElementById('units').value;

		document.getElementById('segmentAmount').value = formatAsCurrency(currentUnits * 1000);
    	
    	if (pageFunction == "add")
        {
    		document.getElementById('unitsChangeEffectiveDateCell').style.visibility = "hidden";
    		document.getElementById('unitsChangeEffectiveDate').value = "";
            return false;
        }

    	if (unitsOnLoad == currentUnits)
       	{
    		document.getElementById('unitsChangeEffectiveDateCell').style.visibility = "hidden";
    		document.getElementById('unitsChangeEffectiveDate').value = "";
    		document.getElementById('segmentAmount').value = segmentAmountOnLoad;
    		return false;
        }
        return true;
    }

    function showValueAtIssue()
    {
        var width = .55 * screen.width;
        var height = .32 * screen.height;

        openDialog("valueAtIssueDialog", "top=0,left=0,resizable=no", width, height);

        prepareToSendTransactionAction("ContractDetailTran", "showValueAtIssueDialog", "valueAtIssueDialog");
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

    function showPUADialog()
    {
        var width = .55 * screen.width;
        var height = .15 * screen.height;

		openDialog("puaDialog","top=0,left=0,resizable=no", width, height);

        prepareToSendTransactionAction("ContractDetailTran", "showPUADialog", "puaDialog");
    }

</script>

<head>
<title>Contract Main</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta http-equiv="Cache-Control" content="no-store">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Expires" content="0">

<link href="/PORTAL/common/css/EDITSolutions.css" rel="stylesheet" type="text/css">
</head>

<body class="mainTheme" onLoad="init(); checkForEditingException(); checkForVOEditException()" style="border-style:solid; border-width:1;">
<form name= "contractCommitRidersForm" method="post" action="/PORTAL/servlet/RequestManager">

<input type="hidden" name="page" value="">
<jsp:include page="contractInfoHeader.jsp" flush="true"/>

  <table width="100%" height="48%" border="0" cellspacing="0" cellpadding="5">
	<tr>
	  <td align="left" nowrap>Effective Date:&nbsp;
            <input type="text" name="effectiveDate" value="<%= effectiveDate %>" size='10' maxlength="10" onKeyUp="DateFormat(this,this.value,event,false)" onBlur="DateFormat(this,this.value,event,true)">
            <a href="javascript:show_calendar('f.effectiveDate', f.effectiveDate.value);"><img src="/PORTAL/common/images/calendarIcon.gif" id="calendarImg1" width="16" height="16" border="0" alt="Select a date from the calendar"></a>
     </td>
      <td align="left" nowrap>Issue Date:&nbsp;
            <input type="text" name="issueDate" value="<%= issueDate %>" size='10' maxlength="10" onKeyUp="DateFormat(this,this.value,event,false)" onBlur="DateFormat(this,this.value,event,true)">
            <a href="javascript:show_calendar('f.issueDate', f.issueDate.value);"><img src="/PORTAL/common/images/calendarIcon.gif" id="calendarImg2" width="16" height="16" border="0" alt="Select a date from the calendar"></a>
        </td>
      <td align="left" nowrap>Termination Date:&nbsp;
            <input type="text" name="terminateDate" value="<%= terminateDate %>" size='10' maxlength="10" onKeyUp="DateFormat(this,this.value,event,false)" onBlur="DateFormat(this,this.value,event,true)">
            <a href="javascript:show_calendar('f.terminateDate', f.terminateDate.value);"><img src="/PORTAL/common/images/calendarIcon.gif" id="calendarImg3" width="16" height="16" border="0" alt="Select a date from the calendar"></a>
	  </td>
    </tr>
    <tr>
      <td align="left" nowrap>Application Signed Date:&nbsp;
      <input  disabled type="text" name="appReceivedDate" value="<%= appReceivedDate %>" size='10' maxlength="10">
      </td>
      <td align="left" nowrap>Application Received Date:&nbsp;
      <input  disabled type="text" name="appSignedDate" value="<%= appSignedDate %>" size='10' maxlength="10">
	  </td>
      <td align="left" nowrap>Term:&nbsp;
		<input disabled type="text" name="certainDuration" maxlength="2" size="2" value="<%= certainDuration %>">
      </td>
    </tr>
    <tr>
      <td align="left" nowrap>Amount:&nbsp;
<%--        <input disabled type="text" name="faceAmount" maxlength="13" size="13" value="<%= faceAmount %>">--%>
        <input type="text" name="segmentAmount" maxlength="13" size="13" value="<%= segmentAmount %>" CURRENCY>
      </td>
      <td align="left" nowrap>Units:&nbsp;
        <input type="text" name="units" id="units" maxlength="15" size="6" value="<%= units %>" onfocus="showUnitsChangeEffectiveDate()" onblur="checkIfUnitsChangeEffectiveDateNeeded()">
      </td>
      <td style="visibility:hidden;" name="unitsChangeEffectiveDateCell" id="unitsChangeEffectiveDateCell" align="left" nowrap>Units Change Effective Date:&nbsp;
        <input class="datepicker" type="text"
			id="unitsChangeEffectiveDate"
			value="<%=unitsChangeEffectiveDate%>"
			name="unitsChangeEffectiveDate" size="10">
      </td>
    </tr>
    <tr>
	  <td align="left" nowrap>Coverage:&nbsp;
        <input type="text" name="riderOption" value="<%= riderOption %>" size="50" maxlength="50">
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
      <td align="left" nowrap>Claim Stop Date:&nbsp;
      <input type="text" name="claimStopDate" maxlength="10" size="10" value="<%= claimStopDate %>" onKeyUp="DateFormat(this,this.value,event,false)" onBlur="DateFormat(this,this.value,event,true)">
      <a href="javascript:show_calendar('f.claimStopDate', f.claimStopDate.value);"><img src="/PORTAL/common/images/calendarIcon.gif" id="calendarImg4" width="16" height="16" border="0" alt="Select a date from the calendar"></a>
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
      <td align="left" nowrap>Age At Issue:&nbsp;
        <input disabled type="text" name="ageAtIssue" maxlength="10" size="10" value="<%= ageAtIssue %>">
      </td>
      <td align="left" nowrap>&nbsp;
      </td>
	</tr>
    <tr>
      <td align="left" nowrap>Commission Phase ID:&nbsp;
        <input disabled type="text" name="commissionPhaseID" value="<%= commissionPhaseID %>" maxlength="5">
      </td>
      <td align="left" nowrap>Commission Phase Override:&nbsp;
        <input type="text" name="commissionPhaseOverride" value="<%= commissionPhaseOverride %>" maxlength="20">
      </td>
      <td align="left" nowrap>Rated Gender:&nbsp;
          <select name="ratedGenderCT" disabled>
	      <option value="null">Please Select</option>
	        <%
                for(int i = 0; i < ratedGenders.length; i++)
                {
                    String codeTablePK = ratedGenders[i].getCodeTablePK() + "";
                    String codeDesc    = ratedGenders[i].getCodeDesc();
                    String code        = ratedGenders[i].getCode();

                    if (ratedGenderCT.equalsIgnoreCase(code))
                    {
                        out.println("<option selected name=\"id\" value=\"" + code+ "\">" + codeDesc + "</option>");
                    }
                    else
                    {
                        out.println("<option name=\"id\" value=\"" + code + "\">" + codeDesc + "</option>");
                    }
                }
	       %>
      </td>
    </tr>
    <tr>
	   <td nowrap>Increase Option:
	    <select name="gioOption">
	      <option selected value="null"> Please Select </option>
	        <%
                if (caseProductUnderwritings != null)
                {
                    for(int i = 0; i < caseProductUnderwritings.length; i++)
                    {

                        String value = caseProductUnderwritings[i].getValue();
                        String valuePK = caseProductUnderwritings[i].getCaseProductUnderwritingPK().toString();

                        if (gioOption.equalsIgnoreCase(value))
                        {

                            out.println("<option selected name=\"id\" value=\"" + value + "\">" +value + "</option>");
                        }
                        else
                        {

                            out.println("<option name=\"id\" value=\"" + value + "\">" + value + "</option>");
                        }
                    }
                }
            %>
	    </select>
	  </td>
      <td align="left" nowrap>Creation Date:&nbsp;
        <input disabled type="text" name="creationDate" maxlength="10" size="10" value="<%= creationDate %>">
      </td>
	  <td nowrap >Creation Operator:&nbsp;
        <input disabled type="text" name="creationOperator" maxlength="15" size="15" value="<%= creationOperator %>">
      </td>
	  <td align="left" nowrap >&nbsp;
      </td>
    </tr>
    <tr>
	  <td nowrap >Insured Name:&nbsp;
        <input disabled type="text" name="insuredName" maxlength="30" size="20" value="<%= insuredName %>">
      </td>
	  <td align="left" nowrap>&nbsp;
	    <input disabled type="checkbox" name="valueAtIssue" <%=valueAtIssue %>><a href ="javascript:showValueAtIssue()">Values At Issue</a>
      </td>
      <td align="left" nowrap>Underwriting Class:&nbsp;
        <select id="uwClass" name="underwritingClass" tabindex="7">
	      <option value="null">Please Select</option>
	        <%
                 for(int i = 0; i < underwritingClasses.length; i++)
                    {
                        String codeTablePK = underwritingClasses[i].getCodeTablePK() + "";
                        String codeDesc    = underwritingClasses[i].getCodeDesc();
                        String code        = underwritingClasses[i].getCode();
    
                        if (underwritingClass.equalsIgnoreCase(code))
                        {
                            out.println("<option selected name=\"id\" value=\"" + code + "\">" + codeDesc + "</option>");
                        }
                        else
                        {
                            out.println("<option name=\"id\" value=\"" + code + "\">" + codeDesc + "</option>");
                        }
                    }

	       %>
      </td>
      
    </tr>
    <tr>
        <td align="left" nowrap>Group Plan:&nbsp;
        <select  name="groupPlan">
	      <option value="null">Please Select</option>
	        <%
                for(int i = 0; i < groupPlans.length; i++)
                {
                    String codeTablePK = groupPlans[i].getCodeTablePK() + "";
                    String codeDesc    = groupPlans[i].getCodeDesc();
                    String code        = groupPlans[i].getCode();

                    if (groupPlan.equalsIgnoreCase(code))
                    {
                        out.println("<option selected name=\"id\" value=\"" + code+ "\">" + codeDesc + "</option>");
                    }
                    else
                    {
                        out.println("<option name=\"id\" value=\"" + code + "\">" + codeDesc + "</option>");
                    }
                }
	       %>
      </td>
    </tr>
    <tr>
      <!-- Next lines to fill space.  If uncomment pua link, remove these 2 lines -->
 	  <td align="left" nowrap >&nbsp;
      </td>
      <!-- COMMENTED OUT - DON'T WANT TO DELIVER TO VISION YET
	  <td align="left" nowrap colspan="10">
        <a href ="javascript:showPUADialog()">PUA</a>
	  </td>
      -->
    </tr>
    <tr>
 	  <td align="left" nowrap>&nbsp;
      </td>
      <td align="left" nowrap >&nbsp;
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
	  </td>
	</tr>
  </table>
  <table class="summary" width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr class="heading">
      <th width="12%" align="left">Coverage</th>
      <th width="12%" align="center">Seq Number</th>
      <th width="12%" align="center">Status</th>
      <th width="12%" align="center">Effective Date</th>
      <th width="12%" align="center">Amount</th>
      <th width="12%" align="center">Termination Date</th>
      <th width="12%" align="center">Age At Issue</th>
      <th width="12%" align="center">Insured</th>
    </tr>
  </table>
  <span class="scrollableContent" style="border-style:solid; border-width:1;  position:relative; width:100%; height:30%; top:0; left:0; background-color:#BBBBBB">
    <table class="summary" id="contractRiderSummary" width="100%" style="border-style:solid; border-width:0;" cellspacing="0" cellpadding="0">
      <%
          int rowId   = 0;

          String rowToMatch = "";
          String trClass = "";
          boolean selected = false;
          String endDate = terminateDateBase;
          String insuredNameT = "";

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
          //faceAmount = Util.initString(formBean.getValue("faceAmount"), "0.00");
          faceAmount = Util.initString(formBean.getValue("segmentAmount"), "0.00");

      %>
        <tr class="<%= trClass %>" isSelected="<%= selected %>" id="rowId_<%= rowId %>"  onClick="selectRow()"
            onMouseOver="highlightRow()" onMouseOut="unhighlightRow()" sequenceNumber="0" key="base">
          <td width="12%" nowrap id="optionId_<%= rowId %>" align="left">
            <%= optionId %>
          </td>
          <td width="12%" nowrap id="sequenceNumberId_<%= rowId %>" align="center">
             <%= "0" %>
          </td>
          <td width="12%" nowrap id="riderStatus_<%= rowId %>" align="center">
            <%= segmentStatus %>
          </td>
          <td width="12%" nowrap id="effectiveDateId_<%= rowId %>" align="center">
            <%= effDateBase %>
          </td>
          <td width="12%" nowrap id="amountId_<%= rowId %>" align="center">
            <script>document.write(formatAsCurrency(<%= faceAmount %>))</script>
          </td>
          <td width="12%" nowrap id="endDateId_<%= rowId %>" align="center">
            <%= endDate %>
          </td>
          <td width="12%" nowrap id="ageAtIssueId_<%= rowId %>" align="center">
            <%= ageAtIssueBase %>
          </td>
          <td width="12%" nowrap align="center">
            <%= insuredNameT %>
          </td>
        </tr>
      <%

          Map allRiders = contractRiders.getPageBeans();

          Map sortedRidersByRiderNumber = new TreeMap();

          Iterator itrKeys = allRiders.keySet().iterator();

          while(itrKeys.hasNext())
          {
              String key = (String) itrKeys.next();
              if (!key.equals(""))
              {
                  String sortKey = key.substring(0, key.indexOf("_"));
                  if (sortKey.length() == 1)
                  {
                      sortKey = "0" + sortKey;
                  }

                  sortedRidersByRiderNumber.put(sortKey, allRiders.get(key));
              }
          }

          String riderSequenceNumber = "";
          String riderOptionId = "";
          String riderFaceAmount = "";
          String riderAmount = "";
          String ageAtIssueT = "";

          Iterator it = sortedRidersByRiderNumber.values().iterator();

          while (it.hasNext())  {

              rowId++;

              PageBean rider = (PageBean)it.next();

              String riderOptionCodePK = rider.getValue("optionCodePK");
              if (Util.isANumber(riderOptionCodePK))
              {
                  riderOptionId = CodeTableWrapper.getSingleton().getCodeTableEntry(Long.parseLong(riderOptionCodePK)).getCode();
              }
              String riderEffDate = rider.getValue("effectiveDate");

             // riderFaceAmount    = Util.initString(rider.getValue("faceAmount"), "");//??
              riderFaceAmount    = Util.initString(rider.getValue("segmentAmount"), "");
              insuredNameT  = rider.getValue("insuredName");
              String riderEndDate = rider.getValue("terminateDate");
              ageAtIssueT = rider.getValue("ageAtIssue");

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
        <tr class="<%= trClass %>" isSelected="<%= selected %>" id="rowId_<%= rowId %>" optionCodePK="<%= riderOptionCodePK %>"
            onClick="selectRow()" onMouseOver="highlightRow()" onMouseOut="unhighlightRow()" key="<%= rowToMatch%>"
            sequenceNumber="<%= riderSequenceNumber %>">
          <td width="12%" nowrap id="optionId_<%= rowId %>" align="left">
            <%= riderOptionId %>
          </td>
          <td width="12%" nowrap id="sequenceNumberId_<%= rowId %>" align="center">
            <%= riderSequenceNumber %>
          </td>
          <td width="12%" nowrap id="riderStatus_<%= rowId %>" align="center">
            <%= riderStatus %>
          </td>
          <td width="12%" nowrap id="effectiveDateId_<%= rowId %>" align="center">
            <%= riderEffDate %>
          </td>
          <td width="12%" nowrap id="amountId_<%= rowId %>" align="center">
          <%
              if (Util.isANumber(riderFaceAmount))
              {
          %>
                <script>document.write(formatAsCurrency(<%= riderFaceAmount %>))</script>
          <%
              }
              else
              {
          %>
                <%= riderFaceAmount %>
          <%
              }
          %>
          </td>
          <td width="12%" nowrap id="endDateId_<%= rowId %>" align="center">
            <%= riderEndDate %>
          </td>
          <td width="12%" nowrap id="ageAtIssueId_<%= rowId %>" align="center">
            <%= ageAtIssueT %>
          </td>
          <td width="12%" nowrap>
            <%= insuredNameT %>
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
 <input type="hidden" name="optionCodePK" value="<%= optionCodePK %>">

 <input type="hidden" name="financialIndStatus" value="<%= financialIndStatus %>">
 <input type="hidden" name="investmentIndStatus" value="">
 <input type="hidden" name="dateIndStatus" value="<%= dateIndStatus %>">
 <input type="hidden" name="notesIndStatus" value="<%= notesIndStatus %>">
 <input type="hidden" name="taxesIndStatus" value=<%= taxesIndStatus %>"">
 <input type="hidden" name="depositsIndStatus" value="<%= depositsIndStatus %>">


 <input type="hidden" name="segmentPK" value="<%= segmentPK %>">
 <input type="hidden" name="payoutPK" value="<%= payoutPK %>">
 <input type="hidden" name="segmentStatus" value = "<%= segmentStatus %>">

 <!--- *****Dialog Values for Financial*****//-->
 <input type="hidden" name="purchaseAmount" value="<%=purchaseAmount %>">
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
<%-- <input type="hidden" name="policyGroupFK" value="<%= policyGroupFK %>">--%>
 <input type="hidden" name="tamraStartDate" value="<%= tamraStartDate %>">
 <input type="hidden" name="MAPEndDate" value="<%= MAPEndDate %>">
 <input type="hidden" name="mecGuidelineLevelPremium" value="<%= mecGuidelineLevelPremium %>">
 <input type="hidden" name="mecGuidelineSinglePremium" value="<%= mecGuidelineSinglePremium %>">
 <input type="hidden" name="cumGuidelineLevelPremium" value="<%= cumGuidelineLevelPremium %>">
 <input type="hidden" name="pendingDBOChangeIndStatus" value="<%= pendingDBOChangeIndStatus %>">
 <input type="hidden" name="deathBeneOption" value="<%= deathBeneOption %>">

 <!--- *****Dialog Values for Dates*****//-->
 <%--<input type="hidden" name="terminateDate" value="<%= terminateDate %>">--%>

 <input type="hidden" name="finalPayDate" value="<%=finalPayDate %>">
 <input type="hidden" name="statusChangeDate" value="<%=statusChangeDate %>">
 <input type="hidden" name="lastCheckDate" value="<%=lastCheckDate %>">
 <input type="hidden" name="nextPaymentDate" value="<%=nextPaymentDate %>">
 <input type="hidden" name="certainPeriodEndDate" value="<%= certainPeriodEndDate %>">
 <input type="hidden" name="lastAnnivDate" value="<%= lastAnnivDate %>">
 <!--<input type="hidden" name="appSignedDate" value="<%= appSignedDate %>"> -->

<%-- <input type="hidden" name="effectiveDate" value="<%= effectiveDate %>">--%>

<%-- <input type="hidden" name="issueDate" value="<%= issueDate %>">--%>
 <input type="hidden" name="appReceivedDate" value="<%= appReceivedDate %>">
 <input type="hidden" name="ignoreEditWarnings" value="">
 <input type="hidden" name="creationDate" value="<%= creationDate %>">
 <input type="hidden" name="creationOperator" value="<%= creationOperator %>">
 <input type="hidden" name="startDate" value="<%= startDate %>">
 <input type="hidden" name="certainDuration" value="<%= certainDuration %>">
 <input type="hidden" name="option7702" value="<%= option7702 %>">
 <input type="hidden" name="lifePK" value="<%= lifePK %>">
 <input type="hidden" name="freeLookEndDate" value="<%= freeLookEndDate %>">
 <input type="hidden" name="maxNetAmountAtRisk" value="<%= maxNetAmountAtRisk %>">
 <input type="hidden" name="policyDeliveryDate" value="<%= policyDeliveryDate %>">
 <input type="hidden" name="chargeDeductDivisionIndStatus" value="<%= chargeDeductDivisionIndStatus %>">
 <input type="hidden" name="commitmentIndicatorStatus" value="<%= commitmentIndicatorStatus %>">
 <input type="hidden" name="commitmentAmount" value="<%= commitmentAmount %>">
 <input type="hidden" name="creationDate" value="<%= creationDate %>">
 <input type="hidden" name="MECDate" value="<%= mecDate %>">                                   
 <input type="hidden" name="waiveFreeLookIndicator" value="<%= waiveFreeLookIndicator %>">
 <input type="hidden" name="freeLookDaysOverride" value="<%= freeLookDaysOverride %>">
 <input type="hidden" name="freeLookEndDate" value="<%= freeLookEndDate %>">
 <input type="hidden" name="dialableSalesLoadPct" value="<%= dialableSalesLoadPct %>">
 <input type="hidden" name="segmentName" value="<%= segmentName %>">
 <input type="hidden" name="chargeDeductAmount" value="<%= chargeDeductAmount %>">
 <input type="hidden" name="optionId" value="<%= optionId %>">
 <input type="hidden" name="riderBeanKey" value="<%= riderBeanKey %>">
 <input type="hidden" name="riderNumber" value="<%= sequenceNumber %>">
 <input type="hidden" name="riderStatus" value="<%= riderStatus %>">
 <input type="hidden" name="riderSegmentPK" value="<%= riderSegmentPK %>">
 <input type="hidden" name="riderLifePK" value="<%= riderLifePK %>">
 <input type="hidden" name="ageAtIssue" value="<%= ageAtIssue %>">
 <input type="hidden" name="ratedGenderCT" value="<%= ratedGenderCT %>">
 <input type="hidden" name="underwritingClass" value="<%= underwritingClass %>">
 <input type="hidden" name="groupPlan" value="<%= groupPlan %>">
 <input type="hidden" name="dateInEffectDate" value="<%= dateInEffectDate %>">
 <input type="hidden" name="quoteDate" value="<%= quoteDate %>">
 <input type="hidden" name="exchangeInd" value="<%= exchangeInd %>">
 <input type="hidden" name="annualPremium" value="<%= annualPremium %>">
 <input type="hidden" name="postIssueStatus" value="<%= postIssueStatus %>">
 <input type="hidden" name="issueStateORInd" value="<%= issueStateORInd %>">
 <input type="hidden" name="originalStateCT" value="<%= originalStateCT %>">
 <input type="hidden" name="commissionPhaseID" value="<%= commissionPhaseID %>">

 <!-- recordPRASEEvents is set by the toolbar -->
 <input type="hidden" name="recordPRASEEvents" value="false">

</form>

</body>
</html>
