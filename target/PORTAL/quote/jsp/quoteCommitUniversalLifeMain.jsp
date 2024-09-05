<!--
 * User: Deanna Lataille
 * Date: Jan 1, 2007
 * Time: 1:16 PM
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
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
                 fission.utility.*,
                 group.*" %>


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
    String masterContractFK = Util.initString(formBean.getValue("masterContractFK"), null);

    String   companyStructure    = formBean.getValue("companyStructure");

    CodeTableWrapper codeTableWrapper = CodeTableWrapper.getSingleton();

    CodeTableVO[] deathBenefitOptions = codeTableWrapper.getCodeTableEntries("DEATHBENOPT", Long.parseLong(companyStructureId));
    
   
    String deathBeneOption   = formBean.getValue("deathBeneOption");
    String nonForfeitureOption   = formBean.getValue("nonForfeitureOption");
    
    String segmentPK         = formBean.getValue("segmentPK");
    String contractNumber    = formBean.getValue("contractNumber");
    String payoutPK          = formBean.getValue("payoutPK");
    String lifePK            = formBean.getValue("lifePK");

    String effectiveDate = formBean.getValue("effectiveDate");

    String terminationDate = formBean.getValue("terminationDate");

    String applicationSignedDate = formBean.getValue("applicationSignedDate");

    String applicationReceivedDate = formBean.getValue("applicationReceivedDate");

    String creationDate = formBean.getValue("creationDate");

    String creationOperator = formBean.getValue("creationOperator");
    String applicationState = formBean.getValue("applicationState");

    String dateInEffectDate = formBean.getValue("dateInEffect");
    String ratedGenderCT     = formBean.getValue("ratedGenderCT");
    String underwritingClass = formBean.getValue("underwritingClass");
    
    CodeTableVO[] groupPlans = codeTableWrapper.getCodeTableEntries("GROUPPLAN", Long.parseLong(companyStructureId));
    String groupPlan     = formBean.getValue("groupPlan");
    
    CodeTableVO[] ratedGenders = codeTableWrapper.getCodeTableEntries("RATEDGENDER", Long.parseLong(companyStructureId));
    CodeTableVO[] underwritingClasses = codeTableWrapper.getCodeTableEntries("UNDERWRITINGCLASS", Long.parseLong(companyStructureId));
    
    CaseProductUnderwriting[] underwritingClassValues = null;
    CaseProductUnderwriting[] ratedGenderValues = null;
    
    ratedGenderValues = (CaseProductUnderwriting[]) request.getAttribute("ratedGenders");
    underwritingClassValues = (CaseProductUnderwriting[]) request.getAttribute("underwritingClasses");
    
    CodeTableVO[] states = codeTableWrapper.getCodeTableEntries("STATE", Long.parseLong(companyStructureId));
	String areaId = formBean.getValue("areaId");
//    String stateDesc = Util.initString(codeTableWrapper.getCodeDescByCodeTableNameAndCode("STATE", areaId), "");

    String issueStateORIndStatus = formBean.getValue("issueStateORIndStatus");

    CodeTableVO[] contractTypes = codeTableWrapper.getCodeTableEntries("CONTRACTTYPE");
    String contractType = formBean.getValue("contractType");

    CodeTableVO[] options = codeTableWrapper.getCodeTableEntries("OPTIONCODE", Long.parseLong(companyStructureId));
	String optionId	= formBean.getValue("optionId");

    CodeTableVO[] mecStatusOptions = codeTableWrapper.getCodeTableEntries("MECSTATUS", Long.parseLong(companyStructureId));
    String mecStatus = formBean.getValue("mecStatus");

    CodeTableVO[] dividendOptions = codeTableWrapper.getCodeTableEntries("DIVIDENDOPTION", Long.parseLong(companyStructureId));
    String dividendOptionCT = formBean.getValue("dividendOptionCT");


    String statusCode                          = formBean.getValue("statusCode");
	String scheduledEventsStatus               = formBean.getValue("scheduledEvents");
	String calculatedValuesIndStatus           = formBean.getValue("calculatedValuesIndStatus");
    String depositsIndStatus                   = "unchecked";
    String billingIndStatus                    = formBean.getValue("billingIndStatus");
    String contractBillsStatus                    = formBean.getValue("contractBillsInd");
    String estateOfTheInsuredIndStatus         = formBean.getValue("estateOfTheInsuredIndStatus");
    String premiumDueHistoryStatus             = formBean.getValue("premiumDueHistory");

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
    String datesIndStatus                      = formBean.getValue("datesIndStatus");
    String term                                = formBean.getValue("term");
	String finalDistributionAmount             = formBean.getValue("finalDistributionAmount");
	String premiumTaxes                        = formBean.getValue("premiumTaxes");
	String frontEndLoads                       = formBean.getValue("frontEndLoads");
	String totalProjectedAnnuity               = formBean.getValue("totalProjectedAnnuity");
	String exclusionRatio                      = formBean.getValue("exclusionRatio");
	String excessInterest                      = formBean.getValue("excessInterest");
	String yearlyTaxableBenefit                = formBean.getValue("yearlyTaxableBenefit");
	String fees                                = formBean.getValue("fees");
	String paymentAmount				       = formBean.getValue("paymentAmount");
	String purchaseAmount                      = formBean.getValue("purchaseAmount");
    String faceAmount                          = formBean.getValue("faceAmount");
    String costBasis                           = formBean.getValue("costBasis");
    String units                               = formBean.getValue("units");
    String commissionPhaseID                   = formBean.getValue("commissionPhaseID");
    String commissionPhaseOverride             = formBean.getValue("commissionPhaseOverride");
    String savingsPercent                      = formBean.getValue("savingsPercent");
    String dismembermentPercent                = formBean.getValue("dismembermentPercent");
    String nextPaymentDate                     = formBean.getValue("nextPaymentDate");
    String guidelineSinglePrem                 = formBean.getValue("guidelineSinglePrem");
    String guidelineLevelPrem                  = formBean.getValue("guidelineLevelPrem");
    String tamra                               = formBean.getValue("tamra");
    String MAPEndDate                          = formBean.getValue("MAPEndDate");
    String tamraInitAdjValue                   = formBean.getValue("tamraInitAdjValue");
    
    String MECDate = formBean.getValue("MECDate");
    String pendingDBOChangeIndStatus = formBean.getValue("pendingDBOChangeIndStatus");
    String tamraStartDate = formBean.getValue("tamraStartDate");
    String mecGuidelineSinglePremium = formBean.getValue("mecGuidelineSinglePremium");
    String mecGuidelineLevelPremium = formBean.getValue("mecGuidelineLevelPremium");
    String cumGuidelineLevelPremium = formBean.getValue("cumGuidelineLevelPremium");
    String startNew7PayIndicatorStatus = formBean.getValue("startNew7PayIndicatorStatus");
    String maxNetAmountAtRisk = formBean.getValue("maxNetAmountAtRisk");
    String paidToDate = formBean.getValue("paidToDate");
    String lapsePendingDate = formBean.getValue("lapsePendingDate");
    String lapseDate = formBean.getValue("lapseDate");
    String currentDeathBenefit = formBean.getValue("currentDeathBenefit");
    String guarPaidUpTerm = formBean.getValue("guarPaidUpTerm");
    String nonGuarPaidUpTerm = formBean.getValue("nonGuarPaidUpTerm");
    String mortalityCredit = formBean.getValue("mortalityCredit");
    String endowmentCredit = formBean.getValue("endowmentCredit");
    String excessInterestCredit = formBean.getValue("excessInterestCredit");
    String option7702 = formBean.getValue("option7702");

    String waiveFreeLookIndicatorStatus        = formBean.getValue("waiveFreeLookIndicatorStatus");
    String freeLookDaysOverride                = formBean.getValue("freeLookDaysOverride");
    String suppOriginalContractNumber = formBean.getValue("suppOriginalContractNumber");
    String casetrackingOptionCT = formBean.getValue("casetrackingOptionCT");

    String totalFaceAmount = formBean.getValue("totalFaceAmount");
    String annualPremium = formBean.getValue("annualPremium");
    String applicationSignedState = Util.initString(formBean.getValue("applicationSignedState"), "");

	String showQuoteResults = (String) request.getAttribute("showQuoteResults");
    String analyzeQuote     = (String) request.getAttribute("analyzeQuote");
    String firstNotifyDate = formBean.getValue("firstNotifyDate");
    String previousNotifyDate = formBean.getValue("previousNotifyDate");
    String finalNotifyDate = formBean.getValue("finalNotifyDate");
    String advanceFinalNotify = formBean.getValue("advanceFinalNotify");
    String departmentLocationFK = Util.initString(formBean.getValue("departmentLocationFK"), "0");
    String ageAtIssue = formBean.getValue("ageAtIssue");
    String originalStateCT = formBean.getValue("originalStateCT");
    String location = formBean.getValue("location");
    String sequence = formBean.getValue("sequence");
    String indivAnnPremium = formBean.getValue("indivAnnPremium");
    String worksheetTypeCT = formBean.getValue("worksheetTypeCT");
    String requirementEffectiveDate = formBean.getValue("requirementEffectiveDate");
    String scheduledPremiumAmount = formBean.getValue("scheduledPremiumAmount");
    BatchContractSetup batchContractSetup = (BatchContractSetup) session.getAttribute("batchContractSetup");
    

    String batchID = "";
    String enrollmentMethod = "";
    if (batchContractSetup != null)
    {
        batchID = batchContractSetup.getBatchID();
        enrollmentMethod = batchContractSetup.getEnrollmentMethodCT();
    }

    DepartmentLocation[] departmentLocations = null;
    if (contractGroupFK != null)
    {
       departmentLocations = DepartmentLocation.findBy_ContractGroupFK(new Long(contractGroupFK));
    }

    CodeTableVO[] nbStatuses = codeTableWrapper.getCodeTableEntries("NEWBUSINESSSTATUS", Long.parseLong(companyStructureId));

    UserSession userSession = (UserSession) session.getAttribute("userSession");

    String errorMessage = (String) request.getAttribute("errorMessage");
    if (errorMessage == null)
    {
        errorMessage = "";
    }
    
    String leadServicingAgentInfoStatus = getLeadServicingAgentInfoStatus(segmentPK);

    BillScheduleVO billScheduleVO = (BillScheduleVO) session.getAttribute("BillScheduleVO");

    String billingModeCT = "";
    String deductionFrequency = "";
    String dynamicModeDescription = "";
    if (billScheduleVO != null)
    {
        billingModeCT = billScheduleVO.getBillingModeCT();
        deductionFrequency = billScheduleVO.getDeductionFrequencyCT();
        
        if (!Util.isStringNullOrEmpty(billingModeCT)) {
        	if (billingModeCT.equalsIgnoreCase("Annual")) { dynamicModeDescription = "Annual"; } 
        	else if (billingModeCT.equalsIgnoreCase("SemiAnn")) { dynamicModeDescription = "Semi-Annual"; } 
        	else if (billingModeCT.equalsIgnoreCase("Quart")) { dynamicModeDescription = "Quarterly"; } 
        	else if (!Util.isStringNullOrEmpty(deductionFrequency)) {
        		if (billingModeCT.equalsIgnoreCase("Month")) {
        			if (deductionFrequency.equalsIgnoreCase("09")) { dynamicModeDescription = "Ninethly, once per month"; }
        			else if (deductionFrequency.equalsIgnoreCase("10")) { dynamicModeDescription = "Tenthly, once per month"; }
        			else if (deductionFrequency.equalsIgnoreCase("12")) { dynamicModeDescription = "Monthly, once per month"; }
        			else if (deductionFrequency.equalsIgnoreCase("18")) { dynamicModeDescription = "Ninethly, twice per month"; }
        			else if (deductionFrequency.equalsIgnoreCase("20")) { dynamicModeDescription = "Tenthly, twice per month"; }
        			else if (deductionFrequency.equalsIgnoreCase("24")) { dynamicModeDescription = "Monthly, twice per month"; }
        			else if (deductionFrequency.equalsIgnoreCase("36")) { dynamicModeDescription = "Ninethly, four times per month"; }
        			else if (deductionFrequency.equalsIgnoreCase("40")) { dynamicModeDescription = "Tenthly, four times per month"; }
					else if (deductionFrequency.equalsIgnoreCase("48")) { dynamicModeDescription = "Monthly, four times per month"; }
        		} else if (billingModeCT.equalsIgnoreCase("VarMonth")) {
        			if (deductionFrequency.equalsIgnoreCase("26")) { dynamicModeDescription = "Bi-weekly"; }
        			else if (deductionFrequency.equalsIgnoreCase("52")) { dynamicModeDescription = "Weekly"; }
        		} else if (billingModeCT.equalsIgnoreCase("13thly")) {
        			if (deductionFrequency.equalsIgnoreCase("26")) { dynamicModeDescription = "Thirteenthly, twice per month"; }
        			else if (deductionFrequency.equalsIgnoreCase("52")) { dynamicModeDescription = "Thirteenthly, four times per month"; }
        		}
        	}
        	if (!Util.isStringNullOrEmpty(dynamicModeDescription)) {
        		dynamicModeDescription = "(" + dynamicModeDescription + ")";
        	}
        }
    }

%>
<%!
    /**
    * Sees if there are any active EnrollmentLeadServiceAgents for this represented
    * Segment. If so, then "CHECKED" is returned (since this is targted for
    * a checkbox). Otherwise "" are returned.
    */
    private String getLeadServicingAgentInfoStatus(String segmentPK)
    {
        String status = "";
        
        if (Util.initString(segmentPK, null) != null)
        {
            Segment segment = Segment.findByPK(new Long(segmentPK));
            
            if (segment.activeEnrollmentLeadServicingAgentsExist())
            {
                status = "CHECKED";
            }
        }
        
        return status;
    }
%>

<html>

<script src="/PORTAL/common/javascript/commonJavascriptFunctions.js"></script>
<script src="/PORTAL/common/javascript/formatDateFunctions.js"></script>
<script src="/PORTAL/common/javascript/calendarSelector.js"></script>

<!-- ***** JAVASCRIPT *****  quote.jsp.quoteCommitTradMain.jsp -->

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
    var costBasis = "<%= costBasis %>";
    var savingsPercent = "<%= savingsPercent %>";
    var editingExceptionExists = "<%= editingExceptionExists %>";
    var voEditExceptionExists = "<%= voEditExceptionExists %>";

	var dialog = null;
    var height = screen.height;
    var width  = screen.width;

	var f = null;
    var enteredOption = true;
    var shouldShowLockAlert = true;
    var editableContractStatus = true;
    
    var billingModeCT = "<%= billingModeCT %>";
    var statusCode = "<%= statusCode %>";

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

            if ( (elementType == "text" || elementType == "button" || elementType == "select-one") && (shouldShowLockAlert == true || editableContractStatus == false) )
            {
                f.elements[i].onclick = showLockAlert;
                f.elements[i].onkeydown = showLockAlert;
            }
        }

        formatCurrency();
    }

    /**
     * Saves the selected batch ID in the changeIDDialog.  Does it here so this page's form will be submitted instead
     * of the dialog's.  This preserves all of the hidden fields on this page.
     */
    function saveBatchIDChange(selectedBatchContractSetupPK, contractGroupNumber)
    {
        f.selectedBatchContractSetupPK.value = selectedBatchContractSetupPK;
        f.contractGroupNumber.value = contractGroupNumber;

        sendTransactionAction("QuoteDetailTran", "saveBatchIDChange", "contentIFrame");
    }

    function showContractBillingDialog()
    {
        var width = .99 * screen.width;
        var height = .90 * screen.height;

		openDialog("quoteBillingDialog","top=0,left=0,resizable=no", width, height);

        prepareToSendTransactionAction("QuoteDetailTran", "showQuoteBillingDialog", "quoteBillingDialog");
	}

    function showScheduledPremiumChangeDialog()
    {
        document.getElementById("scheduledPremiumAmount").blur();

        if (shouldShowLockAlert == true || editableContractStatus == false)
        {
            showLockAlert();
            
        } else if(statusCode === "Submitted") {
            alert("Scheduled Premium Cannot Be Changed on Submitted Contracts");
            
        } else {
            var width = .99 * screen.width;
            var height = .90 * screen.height;

    		openDialog("quoteScheduledPremiumDialog","top=0,left=0,resizable=yes", width, height);

            prepareToSendTransactionAction("QuoteDetailTran", "showQuoteScheduledPremiumDialog", "quoteScheduledPremiumDialog");
            
        }
 	}

    function showContractBillHistoryDialog()
    {
        var widths = .99 * screen.width;
        var heights = .50 * screen.height;

        openDialog("contractBillHistoryDialog","top=0,left=0,resizable=no", widths, heights);

        prepareToSendTransactionAction("QuoteDetailTran", "showContractBillHistoryDialog", "contractBillHistoryDialog");
    }
    
    /**
     * Shows the EnrollmentLeadServiceAgent info for the specified Segment via
     * EnrollmentLeadServiceAgent.Enrollment.BatchContractSetup.Segment.
     */
    function showEnrollmentLeadServiceAgentInfo()
    {
        var width = 0.75 * screen.width;
        var height = 0.33 * screen.height;

        openDialog("enrollmentLeadServiceAgentInfoDialog","top=0,left=0,resizable=no", width, height);

        sendTransactionAction("QuoteDetailTran", "showEnrollmentLeadServiceAgentInfo", "enrollmentLeadServiceAgentInfoDialog");
    }

    function showPremiumDueHistoryDialog()
    {
        var width = .99 * screen.width;
        var height = .50 * screen.height;

        openDialog("premiumDueHistoryDialog","top=0,left=0,resizable=no", width, height);

        prepareToSendTransactionAction("QuoteDetailTran", "showPremiumDueHistoryDialog", "premiumDueHistoryDialog");
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

    function showContractSuspenseCreation()
    {
        var width = .99 * screen.width;
        var height = .70 * screen.height;

        if (f.applicationSignedDate.value == "" || f.applicationSignedDate.value == "")
        {
            alert("App Signed/Received Date Invalid");
        }

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

        if (f.datesInd.checked == true) {
            f.datesIndStatus.value = "checked";
        }

        if (f.billingInd.checked == true)
        {
            f.billingIndStatus.value = "checked";
        }

        
        
        if (f.estateOfTheInsuredInd.checked == true)
        {
            f.estateOfTheInsuredIndStatus.value = "checked";
        }
        else
        {
            f.estateOfTheInsuredIndStatus.value = "unchecked";
        }

<%--        if (f.waiverInEffect.checked == true)--%>
<%--        {--%>
<%--			f.waiverInEffectStatus.value = "checked";--%>
<%--		}--%>
<%--        else--%>
<%--        {--%>
<%--			f.waiverInEffectStatus.value = "";--%>
<%--        }--%>

        if (f.waiveFreeLookIndicator.checked == true)
        {
            f.waiveFreeLookIndicatorStatus.value = "checked";
        }
        else
        {
            f.waiveFreeLookIndicatorStatus.value = "";
        }

        if (f.issueStateORInd.checked == true)
        {
            f.issueStateORIndStatus.value = "checked";
        }
        else
        {
            f.issueStateORIndStatus.value = "";
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
        f.costBasis.value = costBasis;
        f.savingsPercent.value = savingsPercent;

        if (f.applicationSignedDate.value == "" || f.applicationSignedDate.value == "")
        {
            alert("App Signed/Received Date Invalid");
        }

        sendTransactionAction(transaction, action, target);
	}

    function testForDates()
    {
        if (f.applicationSignedDate.value == "" || f.applicationReceivedDate.value == "")
        {
            alert("App Signed/Received Date Not Entered");
        }
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

  		prepareToOpenDialog("calculatedValuesAdd", "top=0,left=0,resizable=no", width, height, "QuoteDetailTran", "showUniversalLifeCalculatedValuesAdd");
    }

	function showDatesDialog()
    {
        var width = .50 * screen.width;
        var height = .32 * screen.height;

        testForDates();

  		prepareToOpenDialog("datesDialog", "resizable=no", width, height, "QuoteDetailTran", "showDatesDialog");
    }

	function showDepositDialog()
    {
        var width = .99 * screen.width;
        var height = .90 * screen.height;

        testForDates();

   		prepareToOpenDialog("depositDialog", "top=0,left=0,resizable=no", width, height, "QuoteDetailTran", "showDepositDialog");
    }

    function showChangeBatchIDDialog()
    {
        if (shouldShowLockAlert)
        {
            showLockAlert();
        }
        else
        {
            var width = .5 * screen.width;
            var height = .5 * screen.height;

            testForDates();

            prepareToOpenDialog("changeBatchIDDialog", "top=0,left=0,resizable=no", width, height, "QuoteDetailTran", "showChangeBatchIDDialog");
        }
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
            </select>
      </td>
      <td>
        <a href ="javascript:showChangeBatchIDDialog()" name="batchIDLink" >BatchID:&nbsp</a>
		<input disabled type="text" name="batchID" value="<%= batchID %>" size="20" maxlength="35">
      </td>

    </tr>
    <tr>
      <td align="left" nowrap>Issue State:&nbsp;
        <select name="areaId">
          <option>Please Select</option>
            <%
              for(int i = 0; i < states.length; i++)
              {
                  String codeDesc    = states[i].getCodeDesc();
                  String code        = states[i].getCode();

                  if (areaId.equalsIgnoreCase(code))
                  {
                      out.println("<option selected name=\"id\" value=\"" + code + "\">" + codeDesc + "</option>");
                  }
                  else
                  {
                      out.println("<option name=\"id\" value=\"" + code + "\">" + codeDesc + "</option>");
                  }
               }
            %>
            </select>
      </td>
    </tr>
    <tr>
      <td align="left" nowrap>Issue State O/R:&nbsp;
        <input type="checkbox" name="issueStateORInd" <%= issueStateORIndStatus %> >
      </td>
      <td align="left" nowrap>Face Amount:&nbsp;
        <input type="text" name="faceAmount" tabindex="2" value="<%= faceAmount%>" maxlength="20" CURRENCY>
      </td>
      <td align="left" colspan="4" nowrap>Cost Basis:&nbsp;
        <input type="text" name="costBasis" tabindex="3" value="<%= costBasis%>" maxlength="20" CURRENCY disabled>
      </td>
    </tr>
    <tr>
      <td align="left" nowrap>Units:&nbsp;
        <input type="text" name="units" tabindex="4" value="<%= units %>" maxlength="20">
      </td>
<%--      <td align="left" nowrap>&nbsp;</td>--%>
      <td nowrap align="left">MEC Status:&nbsp;
        <select name="mecStatus" tabindex="5">
          <option> Please Select </option>
          <%

              for(int i = 0; i < mecStatusOptions.length; i++)
              {
                  String codeDesc    = mecStatusOptions[i].getCodeDesc();
                  String code        = mecStatusOptions[i].getCode();

                 if (mecStatus.equalsIgnoreCase(code))
                 {
                     out.println("<option selected name=\"id\" value=\"" + code + "\">" + codeDesc + "</option>");
                 }
                 else
                 {
                     out.println("<option name=\"id\" value=\"" + code + "\">" + codeDesc + "</option>");
                 }
              }
		   %>
        </select>
      </td>
      <td nowrap align="left" colspan="4">Death Benefit Option:&nbsp;
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
    </tr>
    <tr>
      <td align="left" nowrap>Commission Phase ID:&nbsp;
        <input type="text" name="commissionPhaseID" value="<%= commissionPhaseID %>" maxlength="5" disabled>
      </td>
      <td align="left" nowrap>Commission Phase Override:&nbsp;
        <input type="text" name="commissionPhaseOverride" tabindex="9" value="<%= commissionPhaseOverride %>" maxlength="20">
      </td>
      <td align="left" nowrap>Department/Location:&nbsp;
          <select name="departmentLocationFK">
            <option selected value="null"> Please Select </option>
              <%
                  if (departmentLocations != null)
                  {
                      for(int i = 0; i < departmentLocations.length; i++)
                      {
                          String departmentLocationName = departmentLocations[i].getDeptLocName();
                          String departmentLocationCode = departmentLocations[i].getDeptLocCode();
                          Long departmentLocationPK = departmentLocations[i].getDepartmentLocationPK();

                          if (departmentLocationPK.equals(new Long(departmentLocationFK)))
                          {
                              out.println("<option selected name=\"departmentLocationPK\" value=\"" + departmentLocationPK + "\">" + departmentLocationCode + " - " + departmentLocationName + "</option>");
                          }
                          else
                          {
                              out.println("<option name=\"departmentLocationPK\" value=\"" + departmentLocationPK + "\">" + departmentLocationCode + " - " + departmentLocationName + "</option>");
                          }
                      }
                  }
              %>
          </select>
      </td>
    </tr>
    <tr>
      <td align="left" nowrap>Application Signed Date:&nbsp;
        <input type="text" name="applicationSignedDate" size='10' maxlength="10" value="<%= applicationSignedDate %>" onKeyUp="DateFormat(this,this.value,event,false)" onBlur="DateFormat(this,this.value,event,true)">
        <a href="javascript:show_calendar('f.applicationSignedDate', f.applicationSignedDate.value);"><img src="/PORTAL/common/images/calendarIcon.gif" width="16" height="16" border="0" alt="Select a date from the calendar"></a>
      </td>
      <td align="left" nowrap >Application Received Date:&nbsp;
        <input type="text" name="applicationReceivedDate" size='10' maxlength="10" value="<%= applicationReceivedDate %>" onKeyUp="DateFormat(this,this.value,event,false)" onBlur="DateFormat(this,this.value,event,true)">
        <a href="javascript:show_calendar('f.applicationReceivedDate', f.applicationReceivedDate.value);"><img src="/PORTAL/common/images/calendarIcon.gif" width="16" height="16" border="0" alt="Select a date from the calendar"></a>
      </td>
        <td align="left" nowrap>Rated Gender:&nbsp;
        <select name="ratedGenderCT" tabindex="6">
	      <option value="null">Please Select</option>
	        <%
                    for(int i = 0; i < ratedGenders.length; i++)
                    {
                        String codeTablePK = ratedGenders[i].getCodeTablePK() + "";
                        String codeDesc    = ratedGenders[i].getCodeDesc();
                        String code        = ratedGenders[i].getCode();

                        if (ratedGenderCT.equalsIgnoreCase(code))
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
      <!-- COMMENTED OUT BECAUSE WE DON'T WANT TO DELIVER THIS TO VISION YET
      <td align="left" nowrap>Dividend Option:&nbsp;
	    <select name="dividendOptionCT">
          <option>Please Select</option>
         	<%
                 /*
              for(int i = 0; i < dividendOptions.length; i++)
              {
                  String codeTablePK = dividendOptions[i].getCodeTablePK() + "";
                  String codeDesc    = dividendOptions[i].getCodeDesc();
                  String code        = dividendOptions[i].getCode();

                  if (dividendOptionCT.equalsIgnoreCase(code))
                  {
                      out.println("<option selected name=\"id\" value=\"" + code + "\">" + codeDesc + "</option>");
                  }
                  else
                  {
                      out.println("<option name=\"id\" value=\"" + code + "\">" + codeDesc + "</option>");
                  }
              }
              */
         	%>
        </select>
      </td>
      -->
    </tr>
    <tr>
      <td align="left" nowrap >Effective Date:&nbsp;
        <input type="text" name="effectiveDate" size='10' maxlength="10" value="<%= effectiveDate %>" onKeyUp="DateFormat(this,this.value,event,false)" onBlur="DateFormat(this,this.value,event,true)">
        <a href="javascript:show_calendar('f.effectiveDate', f.effectiveDate.value);"><img src="/PORTAL/common/images/calendarIcon.gif" width="16" height="16" border="0" alt="Select a date from the calendar"></a>
      </td>
      <td align="left" nowrap>Termination Date:&nbsp;
        <input type="text" name="terminationDate" size='10' maxlength="10" value="<%= terminationDate %>" onKeyUp="DateFormat(this,this.value,event,false)" onBlur="DateFormat(this,this.value,event,true)">
        <a href="javascript:show_calendar('f.terminationDate', f.terminationDate.value);"><img src="/PORTAL/common/images/calendarIcon.gif" width="16" height="16" border="0" alt="Select a date from the calendar"></a>
      </td>
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
      <td align="left" nowrap >Annual Premium:&nbsp;
        <input type="text" name="annualPremium" maxlength="20" size="20" value="<%= annualPremium %>" disabled CURRENCY>
      </td>
      <td align="left" nowrap >Scheduled Premium:&nbsp;
        <input type="text" name="scheduledPremiumAmount" maxlength="20" size="20" value="<%= scheduledPremiumAmount %>" onfocus="showScheduledPremiumChangeDialog()" CURRENCY> <%= dynamicModeDescription %>
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
      <td align="left" nowrap>Term:&nbsp;
        <input type="text" name="term" maxlength="2" size="2" tabindex="22" value="<%= term %>">
      </td>
      <td align="left" nowrap>Waive FreeLook Indicator:&nbsp;
        <input type="checkbox" tabindex="23" name="waiveFreeLookIndicator" <%= waiveFreeLookIndicatorStatus %> >
      </td>
      <td align="left" nowrap colspan="4">Freelook Days Override:&nbsp;
        <input type="text" name="freeLookDaysOverride" maxlength="2" size="2" tabindex="24" value="<%= freeLookDaysOverride %>">
      </td>
    </tr>
    <tr>
      <td align="left" nowrap >Enrollment Method:&nbsp;
        <input disabled type="text" name="enrollmentMethod" maxlength="20" size="20" tabindex="25" value="<%= enrollmentMethod %>">
      </td>
      <td nowrap align="left">Status Change:&nbsp;
        <select name="segmentStatus" tabindex="26">
          <option> Please Select </option>
          <%
              for(int i = 0; i < nbStatuses.length; i++)
              {
                  String codeDesc = nbStatuses[i].getCodeDesc();
                  String code = nbStatuses[i].getCode();

                 if (statusCode.equalsIgnoreCase(code))
                 {
                     out.println("<option selected name=\"id\" value=\"" + code + "\">" + codeDesc + "</option>");
                 }
                 else
                 {
                     out.println("<option name=\"id\" value=\"" + code + "\">" + codeDesc + "</option>");
                 }
              }
		   %>
        </select>
      </td>
    </tr>
    <tr>
      <td align="left" nowrap>Creation Date:&nbsp;
        <input disabled type="text" name="creationDateDisplay" maxlength="10" size="10" value="<%= creationDate %>">
      </td>
<%--	  <td align="left" nowrap colspan="5">Creation Operator:&nbsp;--%>
	   <td align="left" nowrap >Creation Operator:&nbsp;
        <input disabled type="text" name="creationOperator" maxlength="15" size="15" value="<%= creationOperator %>">
      </td>
      <td>
      <input type="checkbox" name="estateOfTheInsuredInd" <%= estateOfTheInsuredIndStatus %> > Estate Of The Insured
      </td>
    </tr>
    <tr>
      <td nowrap colspan="6" height="18">&nbsp;</td>
    </tr>
    <tr align="center">
      <td colspan="6"> <b>Overrides / Indicators:</b> </td>
    </tr>
    <tr>
      <td nowrap colspan="8" align="center">
        <input disabled type="checkbox" name="scheduledEvents" <%= scheduledEventsStatus %> > Scheduled Event
        <input disabled type="checkbox" name="notesInd" <%=notesIndStatus%> >
        <a href ="javascript:notesAdd()">Notes</a>
        <input disabled type="checkbox" name="datesInd" <%=datesIndStatus%> >
        <a href = "javascript:showDatesDialog()">Dates</a>
        <input disabled type="checkbox" name="calculatedValuesInd" <%=calculatedValuesIndStatus %> >
        <a href ="javascript:calculatedValuesAdd()">Calculated Values</a>
        <input disabled type="checkbox" name="depositsIndStatus" <%= depositsIndStatus %> >
        <a href ="javascript:showDepositDialog()">Deposits</a>
        <input disabled type="checkbox" name="billingInd" <%= billingIndStatus %> >
        <a href ="javascript:showContractBillingDialog()">Bill Schedule</a>
        <input disabled type="checkbox" name="contractBillsInd" <%= contractBillsStatus %> >
        <a href ="javascript:showContractBillHistoryDialog()">Contract Bills</a>
        <input disabled type="checkbox" name="premiumDueHistory" <%= premiumDueHistoryStatus %> >
        <a href ="javascript:showPremiumDueHistoryDialog()">Premium Due History</a>
        <input disabled type="checkbox" name="leadServicingAgentInfo" <%= leadServicingAgentInfoStatus %> >
        <a href ="javascript:showEnrollmentLeadServiceAgentInfo()">Lead/Servicing Agent Info</a>        
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
 <input type="hidden" name="lifePK" value="<%= lifePK %>">
 <input type="hidden" name="contractNumber" value="<%= contractNumber %>">
 <input type="hidden" name="contractGroupFK" value="<%= contractGroupFK %>">
 <input type="hidden" name="billScheduleFK" value="<%= billScheduleFK %>">
 <input type="hidden" name="batchContractSetupFK" value="<%= batchContractSetupFK %>">
 <input type="hidden" name="masterContractFK" value="<%= masterContractFK %>">

 <input type="hidden" name="statusCode" value="<%= statusCode %>">
 <input type="hidden" name="scheduledEvents" value="">
 <input type="hidden" name="calculatedValuesIndStatus" value="">
 <input type="hidden" name="notesIndStatus" value="">
 <input type="hidden" name="datesIndStatus" value="">
  <input type="hidden" name="billingIndStatus" value="<%= billingIndStatus %>">
  
  <input type="hidden" name="estateOfTheInsuredIndStatus" value="<%= estateOfTheInsuredIndStatus %>">
  <input type="hidden" name="premiumDueHistory" value="<%= premiumDueHistoryStatus %>">
  <input type="hidden" name="contractBillsInd" value="<%= contractBillsStatus %>">

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
 <input type="hidden" name="costBasis" value="">
 <input type="hidden" name="savingsPercent" value="">
 <input type="hidden" name="guidelineSinglePrem" value="<%= guidelineSinglePrem %>">
 <input type="hidden" name="guidelineLevelPrem" value="<%= guidelineLevelPrem %>">
 <input type="hidden" name="tamra" value="<%= tamra %>">
 <input type="hidden" name="MAPEndDate" value="<%= MAPEndDate %>">
 <input type="hidden" name="tamraInitAdjValue" value="<%= tamraInitAdjValue %>">
 <input type="hidden" name="mecStatus" value="<%= mecStatus %>">
 <input type="hidden" name="option7702" value="<%= option7702 %>">
 
  <input type="hidden" name="MECDate" value="<%= MECDate %>">
  <input type="hidden" name="pendingDBOChangeIndStatus" value="<%= pendingDBOChangeIndStatus %>">
  <input type="hidden" name="tamraStartDate" value="<%= tamraStartDate %>">
  <input type="hidden" name="mecGuidelineSinglePremium" value="<%= mecGuidelineSinglePremium %>">
  <input type="hidden" name="mecGuidelineLevelPremium" value="<%= mecGuidelineLevelPremium %>">
  <input type="hidden" name="cumGuidelineLevelPremium" value="<%= cumGuidelineLevelPremium %>">
  <input type="hidden" name="startNew7PayIndicatorStatus" value="<%= startNew7PayIndicatorStatus %>">
  <input type="hidden" name="maxNetAmountAtRisk" value="<%= maxNetAmountAtRisk %>">
  <input type="hidden" name="paidToDate" value="<%= paidToDate %>">
  <input type="hidden" name="lapsePendingDate" value="<%= lapsePendingDate %>">
  <input type="hidden" name="lapseDate" value="<%= lapseDate %>">
  <input type="hidden" name="currentDeathBenefit" value="<%= currentDeathBenefit %>">
  <input type="hidden" name="guarPaidUpTerm" value="<%= guarPaidUpTerm %>">
 <input type="hidden" name="nonGuarPaidUpTerm" value="<%= nonGuarPaidUpTerm %>">
 <input type="hidden" name="mortalityCredit" value="<%= mortalityCredit %>">
 <input type="hidden" name="endowmentCredit" value="<%= endowmentCredit %>">
 <input type="hidden" name="excessInterestCredit" value="<%= excessInterestCredit %>">
 <input type="hidden" name="nextPaymentDate" value="<%= nextPaymentDate %>">
 <input type="hidden" name="ignoreEditWarnings" value="">
 <input type="hidden" name="creationDate" value="<%= creationDate %>">
 <input type="hidden" name="creationOperator" value="<%= creationOperator %>">
 <input type="hidden" name="dismembermentPercent" value="<%= dismembermentPercent %>">
 <input type="hidden" name="waiveFreeLookIndicatorStatus" value="<%= waiveFreeLookIndicatorStatus %>">
 <input type="hidden" name="issueStateORIndStatus" value="<%= issueStateORIndStatus %>">
 <input type="hidden" name="suppOriginalContractNumber" value="<%= suppOriginalContractNumber %>">
 <input type="hidden" name="casetrackingOptionCT" value="<%= casetrackingOptionCT %>">
 <input type="hidden" name="totalFaceAmount" value="<%= totalFaceAmount %>">
 <input type="hidden" name="scheduledPremiumAmount" value="<%= scheduledPremiumAmount %>">
 <input type="hidden" name="annualPremium" value="<%= annualPremium %>">
 <input type="hidden" name="applicationState" value="<%= applicationState %>">
 <input type="hidden" name="firstNotifyDate" value="<%= firstNotifyDate %>">
 <input type="hidden" name="previousNotifyDate" value="<%= previousNotifyDate %>">
 <input type="hidden" name="finalNotifyDate" value="<%= finalNotifyDate %>">
 <input type="hidden" name="advanceFinalNotify" value="<%= advanceFinalNotify %>">
 <input type="hidden" name="areaId" value="<%= areaId %>">
 <input type="hidden" name="ratedGenderCT" value="<%= ratedGenderCT %>"> 
 <input type="hidden" name="groupPlan" value="<%= groupPlan %>">
 <input type="hidden" name="underwritingClass" value="<%= underwritingClass %>">
 <input type="hidden" name="dateInEffect" value="<%= dateInEffectDate %>">
 <input type="hidden" name="departmentLocationPK" value="<%= departmentLocationFK %>">
 <input type="hidden" name="ageAtIssue" value="<%= ageAtIssue %>">
 <input type="hidden" name="originalStateCT" value="<%= originalStateCT %>">
 <input type="hidden" name="location" value="<%= location %>">
 <input type="hidden" name="sequence" value="<%= sequence %>">
 <input type="hidden" name="indivAnnPremium" value="<%= indivAnnPremium %>">
 <input type="hidden" name="deathBeneOption" value="<%= deathBeneOption %>">
 <input type="hidden" name="worksheetTypeCT" value="<%= worksheetTypeCT %>">
 <input type="hidden" name="requirementEffectiveDate" value="<%= requirementEffectiveDate %>">
<%-- <input type="hidden" name="applicationSignedDate" value="<%= applicationSignedDate %>">--%>
<%-- <input type="hidden" name="applicationReceivedDate" value="<%= applicationReceivedDate %>">--%>
<%-- <input type="hidden" name="effectiveDate" value="<%= effectiveDate %>">--%>
<%-- <input type="hidden" name="terminationDate" value="<%= terminationDate %>">--%>
 <input type="hidden" name="dividendOptionCT" value="<%= dividendOptionCT %>">
 <input type="hidden" name="commissionPhaseID" value="<%= commissionPhaseID %>">

 <!-- Variables from changeBatchIDDialog -->
  <input type="hidden" name="selectedBatchContractSetupPK" value="">
  <input type="hidden" name="contractGroupNumber" value="">

 <!-- recordPRASEEvents is set by the toolbar -->
 <input type="hidden" name="recordPRASEEvents" value="false">

</form>
</body>
</html>
