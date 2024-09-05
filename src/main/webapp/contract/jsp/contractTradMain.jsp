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
                 fission.utility.Util,
                 fission.utility.*,
                 edit.portal.common.session.UserSession,
                 contract.*,
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

    VOEditException voEditException = (VOEditException) session.getAttribute("VOEditException");
    String voEditExceptionExists = "false";
    if (voEditException != null){

        voEditExceptionExists = "true";
    }

    CodeTableWrapper codeTableWrapper = CodeTableWrapper.getSingleton();

	PageBean formBean       = contractMainSessionBean.getPageBean("formBean");

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
    String priorContractGroupFK     = Util.initString(formBean.getValue("priorContractGroupFK"), null);
    String groupNumber = Util.initString(formBean.getValue("groupNumber"), "");

    String billScheduleFK = Util.initString(formBean.getValue("billScheduleFK"), null);
    String batchContractSetupFK = Util.initString(formBean.getValue("batchContractSetupFK"), null);

    String faceAmount       = formBean.getValue("faceAmount");
    String totalFaceAmount  = formBean.getValue("totalFaceAmount");
    String savingsPercent   = formBean.getValue("savingsPercent");
    String dismembermentPercent = formBean.getValue("dismembermentPercent");

    String effectiveDate = formBean.getValue("effectiveDate");
        
    String appSignedDate  = formBean.getValue("appSignedDate");

    String appReceivedDate  = formBean.getValue("appReceivedDate");

    String issueDate        = formBean.getValue("issueDate");

    String terminateDate    = formBean.getValue("terminationDate");
    
    String postIssueStatus      = formBean.getValue("postIssueStatus");

    String commitmentIndicatorStatus  = formBean.getValue("commitmentIndicatorStatus");
    String commitmentAmount     = formBean.getValue("commitmentAmount");
    String policyDeliveryDate   = formBean.getValue("policyDeliveryDate");
    
    String chargeDeductDivisionIndStatus = formBean.getValue("chargeDeductDivisionIndStatus");
    String pointInScaleIndStatus = formBean.getValue("pointInScaleIndStatus");

    String creationDate = formBean.getValue("creationDate");

    String creationOperator = formBean.getValue("creationOperator");

    String authorizedSignatureCT = formBean.getValue("authorizedSignatureCT");

    String dateOfDeathValue = formBean.getValue("dateOfDeathValue");

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

    CodeTableVO[] qualifiedTypes = codeTableWrapper.getCodeTableEntries("QUALIFIEDTYPE", Long.parseLong(companyStructureId));
	String qualifiedType     = formBean.getValue("frequencyId");
    String lastDayOfMonthInd = formBean.getValue("lastDayOfMonthInd");
	String financialIndStatus                = formBean.getValue("financialIndStatus");
	String dateIndStatus                     = formBean.getValue("dateIndStatus");
    String notesIndStatus                    = formBean.getValue("notesIndStatus");
	String taxesIndStatus                    = formBean.getValue("taxesIndStatus");
    String depositsIndStatus                 = formBean.getValue("depositsIndStatus");
    String billingIndStatus                  = formBean.getValue("billingIndStatus");
    String contractBillsStatus                    = formBean.getValue("contractBillsInd");
    String estateOfTheInsuredIndStatus       = formBean.getValue("estateOfTheInsuredIndStatus");
    String suspenseHistoryStatus             = formBean.getValue("suspenseHistoryStatus");
    String premiumDueHistoryStatus           = formBean.getValue("premiumDueHistory");
    String financialHistoryStatus           = "checked";

    String freeLookEndDate           = formBean.getValue("freeLookEndDate");
    String lastAnnivDate             = formBean.getValue("lastAnnivDate");
	String finalPayDate              = formBean.getValue("finalPayDate");
	String statusChangeDate          = formBean.getValue("statusChangeDate");
	String lastCheckDate             = formBean.getValue("lastCheckDate");
	String nextPaymentDate           = formBean.getValue("nextPaymentDate");
    String ownerId                   = formBean.getValue("ownerId");
    String certainPeriodEndDate      = formBean.getValue("certainPeriodEndDate");

    String deathBeneOption           = formBean.getValue("deathBeneOption");
    String waiveFreeLookIndicator    = formBean.getValue("waiveFreeLookIndicator");
    String freeLookDaysOverride      = formBean.getValue("freeLookDaysOverride");
    String segmentName               = formBean.getValue("segmentName");
    String annuitizationValue        = formBean.getValue("annuitizationValue");
    String suppOriginalContractNumber = formBean.getValue("suppOriginalContractNumber");
	String chargeCodeStatus = formBean.getValue("chargeCodeStatus");
    String dividendOptionCT          = formBean.getValue("dividendOptionCT");
    String currentDeathBenefit       = formBean.getValue("currentDeathBenefit");
    String guarPaidUpTerm            = formBean.getValue("guarPaidUpTerm");
    String nonGuarPaidUpTerm         = formBean.getValue("nonGuarPaidUpTerm");
    String mortalityCredit           = formBean.getValue("mortalityCredit");
    String endowmentCredit           = formBean.getValue("endowmentCredit");
    String excessInterestCredit      = formBean.getValue("excessInterestCredit");

    String priorPRDDue = formBean.getValue("priorPRDDue");
    String units                               = formBean.getValue("units");
    String originalUnits                       = formBean.getValue("originalUnits");
    String commissionPhaseID                   = formBean.getValue("commissionPhaseID");
    String commissionPhaseOverride             = formBean.getValue("commissionPhaseOverride");
    String applicationState                    = formBean.getValue("applicationState");
    String applicationNumber                   = formBean.getValue("applicationNumber");
    String memberOfContractGroup               = formBean.getValue("memberOfContractGroup");
    String departmentLocationFK                = Util.initString(formBean.getValue("departmentLocationFK"), "0");
    String annualPremium                       = formBean.getValue("annualPremium");
    String consecutiveAPLCount = formBean.getValue("consecutiveAPLCount");
    String ageAtIssue = formBean.getValue("ageAtIssue");
    String originalStateCT = formBean.getValue("originalStateCT");
    String worksheetTypeCT = formBean.getValue("worksheetTypeCT");
    String originalContractGroupFK = formBean.getValue("originalContractGroupFK");
    String scheduledTerminationDate = formBean.getValue("scheduledTerminationDate");
    String lapseDate = Util.initString(formBean.getValue("lapseDate"), "");
    String lapsePendingDate = Util.initString(formBean.getValue("lapsePendingDate"), "");
    String paidToDate = Util.initString(formBean.getValue("paidToDate"), "");
    String issueStateORInd = Util.initString(formBean.getValue("issueStateORInd"), "");
    String dateInEffectDate = Util.initString(formBean.getValue("dateInEffectDate"), "");
    String deductionAmountOverride = Util.initString(formBean.getValue("deductionAmountOverride"), "0");
    String deductionAmountEffectiveDate = formBean.getValue("deductionAmountEffectiveDate");
    String expiryDate = formBean.getValue("expiryDate");
    
    String amount = formBean.getValue("amount");
    String exchangeInd = formBean.getValue("exchangeInd");
    String cashWithAppInd = formBean.getValue("cashWithAppInd");
    String waiverInEffect = formBean.getValue("waiverInEffect");
    String estateOfTheInsured = formBean.getValue("estateOfTheInsured");
    String sequence = formBean.getValue("sequence");
    String location = formBean.getValue("location");
    String indivAnnPremium = formBean.getValue("indivAnnPremium");
    String issueStateCT = formBean.getValue("issueStateCT");
    String applicationSignedDate = formBean.getValue("applicationSignedDate");
    String applicationReceivedDate = formBean.getValue("applicationReceivedDate");
    String postIssueStatusCT = formBean.getValue("postIssueStatusCT");

    DepartmentLocation[] departmentLocations = null;
    //  Load all DepartmentLocations for the contractGroup
    String contractGroupPK = Util.initString(formBean.getValue("contractGroupFK"), "0");
    if (!contractGroupPK.equals("0"))
    {
       departmentLocations = DepartmentLocation.findBy_ContractGroupFK(new Long(contractGroupPK));
    }

    CodeTableVO[] nfoOptions = codeTableWrapper.getCodeTableEntries("NONFORFEITUREOPTION", Long.parseLong(companyStructureId));
    String nfoOption = formBean.getValue("nonForfeitureOption");
    
    CodeTableVO[] ratedGenders = codeTableWrapper.getCodeTableEntries("RATEDGENDER", Long.parseLong(companyStructureId));
    String ratedGenderCT     = formBean.getValue("ratedGenderCT");
    
    CodeTableVO[] groupPlans = codeTableWrapper.getCodeTableEntries("GROUPPLAN", Long.parseLong(companyStructureId));
    String groupPlan     = formBean.getValue("groupPlan");
    
    CodeTableVO[] underwritingClasses = codeTableWrapper.getCodeTableEntries("UNDERWRITINGCLASS", Long.parseLong(companyStructureId));
    String underwritingClass = formBean.getValue("underwritingClass");
    
    CodeTableVO[] deathBenefitOptions = codeTableWrapper.getCodeTableEntries("DEATHBENOPT", Long.parseLong(companyStructureId));

    CodeTableVO[] dividendOptions = codeTableWrapper.getCodeTableEntries("DIVIDENDOPTION", Long.parseLong(companyStructureId));

	String rowToMatchBase            = optionId;

    String errorMessage = (String) request.getAttribute("errorMessage");
    if (errorMessage == null)
    {
        errorMessage = "";
    }

    String sequenceNumber = (String) request.getAttribute("sequenceNumber");

    CodeTableVO[] yesNo = codeTableWrapper.getCodeTableEntries("YESNO");

    String riderBeanKey = (String) request.getAttribute("riderBeanKey");

    UserSession userSession = (UserSession) session.getAttribute("userSession");
    
    String leadServicingAgentInfoStatus = getLeadServicingAgentInfoStatus(segmentPK);

    BillScheduleVO billScheduleVO = (BillScheduleVO) session.getAttribute("BillScheduleVO");

    String billingModeCT = billScheduleVO.getBillingModeCT();

    String nextBillDueDate = billScheduleVO.getNextBillDueDate();
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

<!-- ***** JAVASCRIPT *****  contract.jsp.contractTradMain.jsp -->

<script language="Javascript1.2">

    var editingExceptionExists = "<%= editingExceptionExists %>";
    var voEditExceptionExists = "<%= voEditExceptionExists %>";
    var errorMessage = "<%= errorMessage %>";
	var dialog = null;
	var f = null;
    var segmentPK = "<%= segmentPK %>";
    var origOptionId = "<%= optionKey %>";
    var riderName = "<%= riderType %>";
    var billingModeCT = "<%= billingModeCT %>";
    var nextBillDueDate = "<%= nextBillDueDate %>";

    var shouldShowLockAlert = true;
    var editableContractStatus = true;
    var disableBillScheduleChanges;
    var disableContractFieldChanges;

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

        shouldShowLockAlert = !<%= userSession.getSegmentIsLocked()%>;
        disableBillScheduleChanges = false;
        disableContractFieldChanges = false;
        
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
            
            if ((elementType == "text" || elementType == "select-one") && shouldShowLockAlert == false &&
            		f.elements[i].name != "billingInd")
            {
                f.elements[i].onclick = showLockAlert;
                f.elements[i].onkeydown = showLockAlert;
            }
        }
        
        formatCurrency();
	}

    function showPUADialog()
    {
        var width = .30 * screen.width;
        var height = .20 * screen.height;

		openDialog("puaDialog","top=20,left=20,resizable=yes", width, height);

        prepareToSendTransactionAction("ContractDetailTran", "showPUADialog", "puaDialog");
    }

	function showDepositDialog()
    {
		if (disableContractFieldChanges == false) {

			if (shouldShowLockAlert == false) {
				disableBillScheduleChanges = true;	
			}
			
	        var width = .99 * screen.width;
	        var height = .55 * screen.height;
	
			openDialog("depositDialog","top=20,left=20,resizable=yes", width, height);
	
	        prepareToSendTransactionAction("ContractDetailTran", "showDepositDialog", "depositDialog");
	        
		} else {
        	alert("Deposits Cannot Be Updated While Bill Schedule Change Is In Progress.");
		}
	}

	function showContractBillingDialog()
    {
        var width = .99 * screen.width;
        var height = .90 * screen.height;

		if (disableBillScheduleChanges == false) {

			if (shouldShowLockAlert == false) {
				disableContractFieldChanges = true;	
			}
			
			openDialog("contractBillingDialog","top=20,left=20,resizable=yes", width, height);

			top.frames["header"].document.getElementById("btnSave").src = "/PORTAL/common/images/btnSaveDis.gif";
			top.frames["header"].document.getElementById("btnSave").disabled = true;

        	prepareToSendTransactionAction("ContractDetailTran", "showContractBillingDialog", "contractBillingDialog");
		
		} else {
			alert("Please Save Contract Changes Before Updating Bill Schedule.");
		}
	}
	
    function showContractBillHistoryDialog()
    {
        var width = .99 * screen.width;
        var height = .50 * screen.height;

        openDialog("contractBillHistoryDialog","top=20,left=20,resizable=yes", width, height);

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

        openDialog("enrollmentLeadServiceAgentInfoDialog","top=20,left=20,resizable=yes", width, height);

        sendTransactionAction("ContractDetailTran", "showEnrollmentLeadServiceAgentInfo", "enrollmentLeadServiceAgentInfoDialog");
    }

    function showNFODBOChangeDialog()
    {
        if (shouldShowLockAlert == true)
        {
            alert("The Contract Cannot Be Edited.");

            return;
        }

        if (disableContractFieldChanges == false) {

			if (shouldShowLockAlert == false) {
				disableBillScheduleChanges = true;	
			}

	        var width = 0.50 * screen.width;
	        var height = 0.25 * screen.height;
	
			openDialog("nfoDBOChangeDialog","top=20,left=20,resizable=yes", width, height);
	
	        prepareToSendTransactionAction("ContractDetailTran", "showNFODBOChangeDialog", "nfoDBOChangeDialog");
	       
        } else {
        	alert("NFO/DBO Cannot Be Updated While Bill Schedule Change Is In Progress.");
        }
    }

    function showSuspenseHistoryDialog()
    {
        var width = .95 * screen.width;
        var height = .50 * screen.height;

		openDialog("suspenseHistoryDialog","top=20,left=20,resizable=yes", width, height);

        prepareToSendTransactionAction("ContractDetailTran", "showSuspenseHistoryDialog", "suspenseHistoryDialog");
    }

    function showPremiumDueHistoryDialog()
    {
        var width = .95 * screen.width;
        var height = .50 * screen.height;

		openDialog("premiumDueHistoryDialog","top=20,left=20,resizable=yes", width, height);

        prepareToSendTransactionAction("ContractDetailTran", "showPremiumDueHistoryDialog", "premiumDueHistoryDialog");
    }

    function showFinancialHistoryDialog()
    {
        var width = .95 * screen.width;
        var height = .80 * screen.height;

		openDialog("financialHistoryDialog","top=20,left=20,resizable=yes", width, height);

        prepareToSendTransactionAction("ContractDetailTran", "showFinancialHistoryDialog", "financialHistoryDialog");
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
            
        } else if (disableContractFieldChanges == true) {
        	
        	alert("Contract Fields Cannot Be Updated While Bill Schedule Change Is In Progress.");
        	
        	return false;
        	
        } else if (shouldShowLockAlert == false && disableContractFieldChanges == false) {
        	
        	disableBillScheduleChanges = true;
        	return true;
        	
        } else {
        	return true;
        }
    }

    function saveBaseRider()
    {
    	if (disableContractFieldChanges == false) {

			if (shouldShowLockAlert == false) {
				disableBillScheduleChanges = true;	
			}
			
	        var areDeductionAmountFieldsValid = validateDeductionAmountFields();
	
	        if (areDeductionAmountFieldsValid)
	        {
	            prepareToSendTransactionAction("ContractDetailTran", "saveBaseRider", "contentIFrame");
	        }
	        
    	} else {
        	alert("Contract Fields Cannot Be Updated While Bill Schedule Change Is In Progress.");
    	}
    }

    function addRider()
    {
    	if (disableContractFieldChanges == false) {

			if (shouldShowLockAlert == false) {
				disableBillScheduleChanges = true;	
			}
			
	        clearForm();
	        	
	        var width = .30 * screen.width;
	        var height = .15 * screen.height;
	
			openDialog("contractRiderCoverageSelectionDialog","top=20,left=20,resizable=yes", width, height);
	
	        prepareToSendTransactionAction("ContractDetailTran", "showRiderCoverageSelectionDialog", "contractRiderCoverageSelectionDialog");
    	} else {
        	alert("Contract Fields Cannot Be Updated While Bill Schedule Change Is In Progress.");
    	}
    }

	function clearForm()
    {
        f.faceAmount.value = 0;
        f.totalFaceAmount.value = 0;

        f.effectiveDate.value    = "";

		f.financialInd.checked     = false;
		f.financialIndStatus.value = "";
		f.dateInd.checked          = false;
		f.dateIndStatus.value      = "";
        f.notesInd.checked         = false;
        f.notesIndStatus.value     = "";
	    f.taxesInd.checked         = false;
		f.taxesIndStatus.value     = "";
        f.depositsInd.checked      = false;
        f.depositsIndStatus.value  = "";
        f.billingInd.checked       = false;
        f.billingIndStatus.value   = "";
        f.estateOfTheInsuredInd.checked       = false;
        f.estateOfTheInsuredIndStatus.value   = "";
        f.suspenseHistoryStatus.value   = "";
        f.suspenseHistoryInd.value   = false;
        f.premiumDueHistory.value    = false;
        f.premiumDueHistoryStatus.value = "";
		f.terminationDate.value    = "";

	}

	function prepareToSendTransactionAction(transaction, action, target)
        {
<%--        if ((segmentPK != "0" &&--%>
<%--             segmentPK != "") &&--%>
<%--            f.optionId.value != origOptionId)--%>
<%--        {--%>
<%--            alert("Cannot Change Coverage on Active Contract");--%>
<%--        }--%>
<%--        else--%>
<%--        {--%>

            if (f.financialInd.checked == true) {
                f.financialIndStatus.value = "checked";
            }

            if (f.dateInd.checked == true) {
                f.dateIndStatus.value = "checked";
            }

            if (f.notesInd.checked == true) {
                f.notesIndStatus.value = "checked";
            }

            if (f.taxesInd.checked == true) {
                f.taxesIndStatus.value = "checked";
            }

            if (f.depositsInd.checked == true) {

                f.depositsIndStatus.value = "checked";
            }

            if (f.billingInd.checked == true) {

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

            if (f.suspenseHistoryInd.checked == true)
            {
                f.suspenseHistoryStatus.value = "checked";
            }
            else
            {
                f.suspenseHistoryStatus.value = "unchecked";
            }

            if (f.premiumDueHistory.checked == true)
            {
                f.premiumDueHistoryStatus.value = "checked";
            }
            else
            {
                f.premiumDueHistoryStatus.value = "unchecked";
            }

            if (f.financialHistory.checked == true)
            {
                f.financialHistoryStatus.value = "checked";
            }
            else
            {
                f.financialHistoryStatus.value = "unchecked";
            }


            sendTransactionAction(transaction, action, target);
<%--        }--%>
	}

        function validateDeductionAmountFields()
        {
            var hasDeductionAmountEffectiveDate = true;

            if (!valueIsZero(f.deductionAmountOverride.value))
            {
                if (valueIsEmpty(f.deductionAmountEffectiveDate.value))
                {
                    alert("Deduction Amount Effective Date is required if Deduction Amount is Entered");

                    return hasDeductionAmountEffectiveDate = false;
                }
                else
                {
                    return hasDeductionAmountEffectiveDate = validateDeductionAmountEffectiveDate();
                }
            }

            return hasDeductionAmountEffectiveDate;
	}

        function validateDeductionAmountEffectiveDate()
        {
            var contractEffectiveDate = f.effectiveDate[0].value;
            var contractEffectiveDateDay = contractEffectiveDate.substring(3, 5);

            var deductionAmountEffectiveDate = f.deductionAmountEffectiveDate.value;
            var deductionAmountEffectiveDateDay = deductionAmountEffectiveDate.substring(3, 5);

            if (contractEffectiveDateDay != deductionAmountEffectiveDateDay)
            {
                alert("Deduction Amount Effective Date must be a Monthiversary Date");

                return false;
            }

            var nextBillDueDateMonth = nextBillDueDate.substring(5, 7);
            var nextBillDueDateYear = nextBillDueDate.substring(0, 4);
            var nextBillDueDateMonthYear = nextBillDueDateYear + "/" + nextBillDueDateMonth;

            var deductionAmountEffectiveDateMonth = deductionAmountEffectiveDate.substring(0, 2);
            var deductionAmountEffectiveDateYear = deductionAmountEffectiveDate.substring(6);
            var deductionAmountEffectiveDateMonthYear = deductionAmountEffectiveDateYear + "/" + deductionAmountEffectiveDateMonth;

            if (deductionAmountEffectiveDateMonthYear < nextBillDueDateMonthYear)
            {
                alert("Deduction Amount Effective Date Must be >= Next Bill Due Date");

                return false;
            }

            return true;
        }

	function selectRow()
    {
		if (disableContractFieldChanges == false) {

			if (shouldShowLockAlert == false) {
				disableBillScheduleChanges = true;	
			}
			
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
			
		} else {
        	alert("Riders Cannot Be Updated While Bill Schedule Change Is In Progress.");
		}
	}

    function cancelRiderForm()
    {
    	if (disableContractFieldChanges == false) {

			if (shouldShowLockAlert == false) {
				disableBillScheduleChanges = true;	
			}
			
			var trElement = null;
	
			var rows = document.all.summaryTable.rows;
	
			for (var i = 0; i < rows.length; i++)
	        {
				if (rows[i].selected == "true")
	            {
					trElement = rows[i];
				}
			}
	
			if (trElement == null)
	        {
				prepareToSendTransactionAction("ContractDetailTran", "cancelRiderForm", "contentIFrame");
	
				return;
			}
	
			var rowId     = trElement.id;
			var parsedRowId = (rowId.split("_"))[1];
	
			var optionTd  = document.getElementById("optionId_" + parsedRowId);
			var optionId  = optionTd.innerText;
	
			prepareToSendTransactionAction("ContractDetailTran", "cancelRiderForm", "contentIFrame");
			clearForm();
			
    	} else {
        	alert("Contract Fields Cannot Be Updated While Bill Schedule Change Is In Progress.");
		}
	}

	function deleteSelectedRider()
    {
		if (disableContractFieldChanges == false) {

			if (shouldShowLockAlert == false) {
				disableBillScheduleChanges = true;	
			}
			
			var trElement = null;
	
			var rows = document.all.summaryTable.rows;
	
			for (var i = 0; i < rows.length; i++)
	        {
				if (rows[i].selected == "true")
	            {
					trElement = rows[i];
				}
			}
	
			var rowId     = trElement.id;
			var parsedRowId = (rowId.split("_"))[1];
	
			var optionTd  = document.getElementById("optionId_" + parsedRowId);
			var optionId  = optionTd.innerText;
	
			prepareToSendTransactionAction("ContractDetailTran", "deleteSelectedRider", "contentIFrame");
			clearForm();
			
		} else {
	    	alert("Contract Fields Cannot Be Updated While Bill Schedule Change Is In Progress.");
		}
	}

	function financialValues()
    {
        var width = screen.width * .70;
        var height = screen.height * .50;

        openDialog("financialValues","left=20,top=20,resizable=yes", width, height);

        prepareToSendTransactionAction("ContractDetailTran", "showFinancialValues", "financialValues");
	}

	function dateValues()
    {
        var width = screen.width * .40;
        var height = screen.height * .50;

		openDialog("dateValues","top=20,left=20,resizable=yes", width, height);

        prepareToSendTransactionAction("ContractDetailTran", "showDateValues", "dateValues");
	}

	function notes()
    {
		if (disableContractFieldChanges == false) {

			if (shouldShowLockAlert == false) {
				disableBillScheduleChanges = true;	
			}
			
	        var width = .99 * screen.width;
	        var height = .90 * screen.height;
	
			openDialog("notes","top=20,left=20,resizable=yes", width, height);
	
	        prepareToSendTransactionAction("ContractDetailTran", "showNotesDialog", "notes");
	        
		} else {
	    	alert("Notes Cannot Be Updated While Bill Schedule Change Is In Progress.");
		}
	}

	function showTaxesDialog()
    {
		if (disableContractFieldChanges == false) {

			if (shouldShowLockAlert == false) {
				disableBillScheduleChanges = true;	
			}
			
	        var width = .50 * screen.width;
	        var height = .32 * screen.height;
	
			openDialog("taxesDialog","top=20,left=20,resizable=yes", width, height);
	
	        prepareToSendTransactionAction("ContractDetailTran", "showTaxesDialog", "taxesDialog");
	        
		} else {
	    	alert("Taxes Cannot Be Updated While Bill Schedule Change Is In Progress.");	
		}
	}

    function checkForEditingException()
    {
        if (editingExceptionExists == "true")
        {
            var width = .75 * screen.width;
            var height = screen.height/3;

            openDialog("exceptionDialog", "resizable=yes", width, height);

            prepareToSendTransactionAction("ContractDetailTran", "showEditingExceptionDialog", "exceptionDialog");
        }
    }

    function checkForVOEditException()
    {
        if (voEditExceptionExists == "true")
        {
            var width = .75 * screen.width;
            var height = screen.height/3;

            openDialog("voEditExceptionDialog", "resizable=yes", width, height);

            prepareToSendTransactionAction("ContractDetailTran", "showVOEditExceptionDialog", "voEditExceptionDialog");
        }
    }

    function continueTransactionAction(transaction, action)
    {
        f.ignoreEditWarnings.value = "true";
        prepareToSendTransactionAction(transaction, action, "contentIFrame");
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
	  <td align="left" nowrap>Effective Date:&nbsp;
            <input disabled type="text" name="effectiveDate" value="<%= effectiveDate %>" size='10' maxlength="10">
      </td>
      <td align="left" nowrap>Issue Date:&nbsp;
          <input disabled type="text" name="issueDate" value="<%= issueDate %>" size='10' maxlength="10">
      </td>
      <td align="left" nowrap>Termination Date:&nbsp;
          <input disabled type="text" name="terminationDate" value="<%= terminateDate %>" size='10' maxlength="10">
     </td>
    </tr>
    <tr>
      <td align="left" nowrap>Application Signed Date:&nbsp;
          <input disabled type="text" name="appSignedDate" value="<%= appSignedDate %>" size='10' maxlength="10">
      </td>
      <td align="left" nowrap>Application Received Date:&nbsp;
          <input disabled type="text" name="appReceivedDate" value="<%= appReceivedDate %>" size='10' maxlength="10">
      </td>
      <td align="left" nowrap>Application State:&nbsp;
        <input type="text" name="applicationState" size="2" maxlength="2" disabled value="<%= applicationState %>">
      </td>
    </tr>
    <tr>
      <td align="left" nowrap>Face Amount:&nbsp;
        <input type="text" name="faceAmount" maxlength="13" size="13" value="<%= faceAmount %>" CURRENCY>
      </td>
      <td align="left" nowrap >Total Face Amount:&nbsp;
        <input disabled type="text" name="totalFaceAmount" maxlength="13" size="13" value="<%= totalFaceAmount %>" CURRENCY>
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
	  <td align="left" nowrap>Coverage:&nbsp;
        <input type="text" name="optionId" value="<%= optionId %>" size="50" maxlength="50">
       </td>
      <td nowrap align="left">Nonforfeiture Option:&nbsp;
        <select disabled name="nonForfeitureOption">
          <option> Please Select </option>
          <%

              for(int i = 0; i < nfoOptions.length; i++)
              {

                  String codeTablePK = nfoOptions[i].getCodeTablePK() + "";
                  String codeDesc    = nfoOptions[i].getCodeDesc();
                  String code        = nfoOptions[i].getCode();

                 if (nfoOption.equalsIgnoreCase(code))
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
 	</tr>
    <tr>
      <td align="left" nowrap>Units:&nbsp;
        <input type="text" name="units" value="<%= units %>" maxlength="20">
      </td>
      <td align="left" nowrap>Age At Issue:&nbsp;
        <input type="text" name="ageAtIssue" value="<%= ageAtIssue %>" maxlength="20">
      </td>
      <!-- Next line for spacing only.  When Uncomment Dividend Option, remove this line -->
       <td align="left" nowrap>Rated Gender:&nbsp;
        <select disabled name="ratedGenderCT">
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
      <!-- COMMENT OUT - DON'T WANT TO DELIVER TO VISION YET
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
      <td align="left" nowrap>Commission Phase ID:&nbsp;
        <input disabled type="text" name="commissionPhaseID" value="<%= commissionPhaseID %>" maxlength="5">
      </td>
      <td align="left" nowrap>Commission Phase Override:&nbsp;
        <input type="text" name="commissionPhaseOverride" value="<%= commissionPhaseOverride %>" maxlength="20">
      </td>
       <td align="left" nowrap>Underwriting Class:&nbsp;
        <select disabled name="underwritingClass">
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
      <td align="left" nowrap>Deduction Override:&nbsp;
        <input type="text" name="deductionAmountOverride" maxlength="20" size="20" value="<%= deductionAmountOverride %>" CURRENCY>
      </td>
      <td align="left" nowrap>Deduction Override Effective Date:&nbsp;
        <input type="text" name="deductionAmountEffectiveDate" size='10' maxlength="10" value="<%= deductionAmountEffectiveDate %>" onKeyUp="DateFormat(this,this.value,event,false)" onBlur="DateFormat(this,this.value,event,true)">
        <a href="javascript:show_calendar('f.deductionAmountEffectiveDate', f.deductionAmountEffectiveDate.value);"><img src="/PORTAL/common/images/calendarIcon.gif" width="16" height="16" border="0" alt="Select a date from the calendar"></a>
      </td>
      <td align="left" nowrap>Group Plan:&nbsp;
          <select disabled name="groupPlan">
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
        &nbsp;&nbsp;Owner's Original State:&nbsp;
        <input type="text" name="originalStateCT" size="2" maxlength="2" disabled value="<%= originalStateCT %>">
      </td>
      <td align="left" nowrap>Status Change:&nbsp;
        <select name="statusChange">
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
       <td align="left" nowrap>Policy Delivery Date:&nbsp;
           <input type="text" name="policyDeliveryDate" value="<%= policyDeliveryDate %>" size='10' maxlength="10" onKeyUp="DateFormat(this,this.value,event,false)" onBlur="DateFormat(this,this.value,event,true)">
           <a href="javascript:show_calendar('f.policyDeliveryDate', f.policyDeliveryDate.value);"><img src="/PORTAL/common/images/calendarIcon.gif" width="16" height="16" border="0" alt="Select a date from the calendar"></a>
       </td>
    </tr>
    <tr>
      <td align="left" nowrap >Waive Free Look Indicator:&nbsp;
        <input disabled type="checkbox" name="waiveFreeLookIndicator" <%= waiveFreeLookIndicator %>>
      </td>
      <td align="left" nowrap>Free Look Days Override:&nbsp;
        <input disabled type="text" name="freeLookDaysOverride" maxlength="2" size="2" value="<%= freeLookDaysOverride %>" >
      </td>
    </tr>
    <tr>
 	  <td align="left" nowrap>Free Look End Date:&nbsp;
              <input disabled type="text" name="freeLookEndDate" value="<%= freeLookEndDate %>" size='10' maxlength="10">
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
      <td>
      <input type="checkbox" name="estateOfTheInsuredInd" <%= estateOfTheInsuredIndStatus %>> Estate Of The Insured
      </td>
    </tr>
	<tr>
	  <td align="left" nowrap colspan="10">
	    <input disabled type="checkbox" name="financialInd" <%=financialIndStatus %>><a href ="javascript:financialValues()">Financial</a>
<%--        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;--%>

		<input disabled type="checkbox" name="dateInd" <%=dateIndStatus %>><a href ="javascript:dateValues()">Dates</a>
<%--        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;--%>

	 	<input disabled type="checkbox" name="notesInd" <%=notesIndStatus %>><a href ="javascript:notes()">Notes</a>
<%--        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;--%>

	 	<input disabled type="checkbox" name="taxesInd" <%=taxesIndStatus %>><a href ="javascript:showTaxesDialog()">Taxes</a>
<%--        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;--%>

        <input disabled type="checkbox" name="depositsInd" <%= depositsIndStatus %> disabled>
        <a href ="javascript:showDepositDialog()">Deposits</a>

        <input disabled type="checkbox" name="billingInd" <%= billingIndStatus %> disabled>
        <a href ="javascript:showContractBillingDialog()">Bill Schedule</a>

<%--        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;--%>
        <input disabled type="checkbox" name="contractBillsInd" <%= contractBillsStatus %> >
        <a href ="javascript:showContractBillHistoryDialog()">Contract Bills</a>
        
        
<%--        <input disabled type="checkbox" name="dobGenderChangeStatus">&nbsp;--%>
<%--        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;--%>
        <a href ="javascript:showNFODBOChangeDialog()">NFO/DBO Change</a>
        &nbsp;&nbsp;
<%--        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;--%>
	 	<input disabled type="checkbox" name="suspenseHistoryInd" <%=suspenseHistoryStatus %>><a href ="javascript:showSuspenseHistoryDialog()"> Suspense History</a>
        <input disabled type="checkbox" name="premiumDueHistory" <%= premiumDueHistoryStatus %> disabled>
        <a href ="javascript:showPremiumDueHistoryDialog()">Premium Due History</a>
        <input disabled type="checkbox" name="financialHistory" <%= financialHistoryStatus %> disabled>
        <a href ="javascript:showFinancialHistoryDialog()">Financial History</a>
        <input disabled type="checkbox" name="leadServicingAgentInfo" <%= leadServicingAgentInfoStatus %> disabled>
        <a href ="javascript:showEnrollmentLeadServiceAgentInfo()">Lead/Servicing Agent Info</a>
	  </td>
	</tr>
    <!-- COMMENTED OUT - DON'T WANT TO DELIVER TO VISION YET
    <tr>
	  <td align="left" nowrap colspan="10">
        <a href ="javascript:showPUADialog()">PUA</a>
	  </td>
	</tr>
    -->
    <tr>
      <td align="left" nowrap>Creation Date:&nbsp;
        <input disabled type="text" name="creationDate" maxlength="10" size="10" value="<%= creationDate %>">
      </td>
	  <td nowrap >Creation Operator:&nbsp;
        <input disabled type="text" name="creationOperator" maxlength="15" size="15" value="<%= creationOperator %>">
      </td>
      <td nowrap>Authorized Signature:&nbsp;
        <select name="authorizedSignatureCT">
          <%
              out.println("<option>Please Select</option>");

              for(int i = 0; i < yesNo.length; i++) {

                  String codeTablePK = yesNo[i].getCodeTablePK() + "";
                  String codeDesc    = yesNo[i].getCodeDesc();
                  String code        = yesNo[i].getCode();

                  if (authorizedSignatureCT.equalsIgnoreCase(code)) {

                      out.println("<option selected name=\"id\" value=\"" + codeTablePK+ "\">" + codeDesc + "</option>");
                  }
                  else  {

                      out.println("<option name=\"id\" value=\"" + codeTablePK + "\">" + codeDesc + "</option>");
                  }
              }
          %>
        </select>
    </tr>
	<tr>
	  <td nowrap colspan="3">
	  </td>
	</tr>
  </table>
  <table width="100%" height="2%" border="0" cellspacing="0" cellpadding="0">
	<tr>
	  <td align="left">
		<input type="button" name="add" value="   Add   " onClick="addRider()">
		<input type="button" name="save" value=" Save " onClick="saveBaseRider()">
		<input type="button" name="cancel" value="Cancel" onClick="cancelRiderForm()">
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
    <table class="summary" id="contractMainSummary" width="100%" style="border-style:solid; border-width:0;" cellspacing="0" cellpadding="0">
      <%
          int rowId   = 0;

          String rowToMatch = "";
          String trClass = "";
          boolean selected = false;
          String insuredNameT = "";
          String riderNumberT = "";
          String ageAtIssueT = "";
          String riderStatus = "";

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
          <td width="12%" nowrap id="optionId_<%= rowId %>" align="left">
            <%= optionId %>
          </td>
          <td width="12%" nowrap id="riderNumber_<%= rowId %>" align="center">
            <%= "0" %>
          </td>
          <td width="12%" nowrap id="riderStatus_<%= rowId %>" align="center">
            <%= segmentStatus %>
          </td>
          <td width="12%" nowrap id="effectiveDateId_<%= rowId %>" align="center">
            <%= effectiveDate %>
          </td>
          <td width="12%" nowrap id="amountId_<%= rowId %>" align="center">
            <script>document.write(formatAsCurrency(<%= faceAmount %>))</script>
          </td>
          <td width="12%" nowrap id="endDateId_<%= rowId %>" align="center">
            <%= terminateDate %>
          </td>
          <td width="12%" nowrap id="ageAtIssueId_<%= rowId %>" align="center">
            <%= ageAtIssue %>
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

              String sortKey = key.substring(0, key.indexOf("_"));
              if (sortKey.length() == 1)
              {
                  sortKey = "0" + sortKey;
              }

              sortedRidersByRiderNumber.put(sortKey, allRiders.get(key));
          }

          String riderSequenceNumber = "";

          Iterator it = sortedRidersByRiderNumber.values().iterator();

          while (it.hasNext())  {

              rowId++;

              PageBean rider = (PageBean)it.next();

              String optionCodePK = rider.getValue("optionCodePK");
              String optionIdT = CodeTableWrapper.getSingleton().getCodeTableEntry(Long.parseLong(optionCodePK)).getCode();
              String riderEffectiveDate = rider.getValue("effectiveDate");
              faceAmount = Util.initString(rider.getValue("segmentAmount"), "0.00");
              String riderEndDate = rider.getValue("terminateDate");
              insuredNameT  = rider.getValue("insuredName");
              ageAtIssueT = rider.getValue("ageAtIssue");
              riderStatus = rider.getValue("riderStatus");
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
          <td width="12%" nowrap id="optionId_<%= rowId %>" align="left">
            <%= optionIdT %>
          </td>
          <td width="12%" nowrap id="riderNumber<%= rowId %>" align="center">
            <%= riderSequenceNumber %>
          </td>
          <td width="12%" nowrap id="riderStatus_<%= rowId %>" align="center">
            <%= riderStatus %>
          </td>
          <td width="12%" nowrap id="effectiveDateId_<%= rowId %>" align="center">
            <%= riderEffectiveDate %>
          </td>
          <td width="12%" nowrap id="amountId_<%= rowId %>" align="center">
            <script>document.write(formatAsCurrency(<%= faceAmount %>))</script>
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

 <input type="hidden" name="financialIndStatus" value="<%= financialIndStatus %>">
 <input type="hidden" name="investmentIndStatus" value="">
 <input type="hidden" name="dateIndStatus" value="<%= dateIndStatus %>">
 <input type="hidden" name="notesIndStatus" value="<%= notesIndStatus %>">
 <input type="hidden" name="taxesIndStatus" value="<%= taxesIndStatus %>">
 <input type="hidden" name="depositsIndStatus" value="<%= depositsIndStatus %>">
 <input type="hidden" name="billingIndStatus" value="<%= billingIndStatus %>">
 <input type="hidden" name="estateOfTheInsuredIndStatus" value="<%= estateOfTheInsuredIndStatus %>">
 <input type="hidden" name="suspenseHistoryStatus" value="<%= suspenseHistoryStatus %>">
 <input type="hidden" name="premiumDueHistoryStatus" value="<%= premiumDueHistoryStatus %>">
  <input type="hidden" name="financialHistoryStatus" value="<%= financialHistoryStatus %>">
 
<input type="hidden" name="contractBillsInd" value="<%= contractBillsStatus %>">

 <input type="hidden" name="segmentPK" value="<%= segmentPK %>">
 <input type="hidden" name="payoutPK" value="<%= payoutPK %>">
 <input type="hidden" name="segmentStatus" value = "<%= segmentStatus %>">

 <!--- *****Dialog Values for Financial*****//-->
 <input type="hidden" name="faceAmount" value="<%= faceAmount %>">
 <input type="hidden" name="savingsPercent" value="<%= savingsPercent %>">
 <input type="hidden" name="dismembermentPercent" value="<%= dismembermentPercent %>">

 <input type="hidden" name="ownerId" value="<%= ownerId %>">
 <input type="hidden" name="contractGroupFK" value="<%= contractGroupFK %>">
 <input type="hidden" name="priorContractGroupFK" value="<%= priorContractGroupFK %>">
 <input type="hidden" name="billScheduleFK" value="<%= billScheduleFK %>">
 <input type="hidden" name="batchContractSetupFK" value="<%= batchContractSetupFK %>">
 <input type="hidden" name="deathBeneOption" value="<%= deathBeneOption %>">
 <input type="hidden" name="dividendOptionCT" value="<%= dividendOptionCT %>">
 <input type="hidden" name="nonForfeitureOption" value="<%= nfoOption %>">
 <input type="hidden" name="ratedGenderCT" value="<%= ratedGenderCT %>">
 <input type="hidden" name="groupPlan" value="<%= groupPlan %>">
 <input type="hidden" name="underwritingClass" value="<%= underwritingClass %>">

 <!--- *****Dialog Values for Dates*****//-->
 <input type="hidden" name="effectiveDate" value="<%= effectiveDate %>">
 <input type="hidden" name="terminationDate" value="<%= terminateDate %>">
 <input type="hidden" name="termDt" value="<%= terminateDate %>">
 <input type="hidden" name="freeLookEndDt" value="<%= freeLookEndDate %>">
 <input type="hidden" name="lastAnnivDate" value="<%= lastAnnivDate %>">

 <input type="hidden" name="finalPayDate" value="<%=finalPayDate %>">
 <input type="hidden" name="statusChangeDate" value="<%=statusChangeDate %>">
 <input type="hidden" name="lastCheckDate" value="<%=lastCheckDate %>">
 <input type="hidden" name="nextPaymentDate" value="<%=nextPaymentDate %>">
 <input type="hidden" name="certainPeriodEndDate" value="<%= certainPeriodEndDate %>">
 <input type="hidden" name="appSignedDate" value="<%= appSignedDate %>">
 <input type="hidden" name="appReceivedDate" value="<%= appReceivedDate %>">
 <input type="hidden" name="issueDate" value="<%= issueDate %>">
 <input type="hidden" name="ignoreEditWarnings" value="">
 <input type="hidden" name="creationDate" value="<%= creationDate %>">
 <input type="hidden" name="creationOperator" value="<%= creationOperator %>">
 <input type="hidden" name="lifePK" value="<%= lifePK %>">
 <input type="hidden" name="freeLookEndDate" value="<%= freeLookEndDate %>">

 <input type="hidden" name="chargeDeductDivisionIndStatus" value="<%= chargeDeductDivisionIndStatus %>">
 <input type="hidden" name="pointInScaleIndStatus" value="<%= pointInScaleIndStatus %>">
 <input type="hidden" name="commitmentIndicatorStatus" value="<%= commitmentIndicatorStatus %>">
 <input type="hidden" name="commitmentAmount" value="<%= commitmentAmount %>">
 <input type="hidden" name="waiveFreeLookIndicator" value="<%= waiveFreeLookIndicator %>">
 <input type="hidden" name="freeLookDaysOverride" value="<%= freeLookDaysOverride %>">
 <input type="hidden" name="dialableSalesLoadPct" value="">
 <input type="hidden" name="segmentName" value="<%= segmentName %>">
 <input type="hidden" name="riderNumber" value="<%= sequenceNumber %>">
 <input type="hidden" name="riderBeanKey" value="<%= riderBeanKey %>">
 <input type="hidden" name="annuitizationValue" value="<%= annuitizationValue %>">
 <input type="hidden" name="suppOriginalContractNumber" value="<%= suppOriginalContractNumber %>">
 <input type="hidden" name="chargeCodeStatus" value="<%= chargeCodeStatus %>">
 <input type="hidden" name="qualNonQual" value="<%= qualNonQual %>">
 <input type="hidden" name="totalFaceAmount" value="<%= totalFaceAmount %>">
 <input type="hidden" name="lastDayOfMonthInd" value="<%= lastDayOfMonthInd %>">
 <input type="hidden" name="commissionPhaseID" value="<%= commissionPhaseID %>">
 <input type="hidden" name="applicationNumber" value="<%= applicationNumber %>">
 <input type="hidden" name="memberOfContractGroup" value="<%= memberOfContractGroup %>">
 <input type="hidden" name="departmentLocationFK" value="<%= departmentLocationFK %>">
 <input type="hidden" name="annualPremium" value="<%= annualPremium %>">
 <input type="hidden" name="consecutiveAPLCount" value="<%= consecutiveAPLCount %>">
 <input type="hidden" name="applicationState" value="<%= applicationState %>">
 <input type="hidden" name="areaId" value="<%= areaId %>">
 <input type="hidden" name="contractType" value="<%= contractType %>">
 <input type="hidden" name="postIssueStatus" value="<%= postIssueStatus %>">
 <input type="hidden" name="ageAtIssue" value="<%= ageAtIssue %>">
 <input type="hidden" name="originalStateCT" value="<%= originalStateCT %>">
 <input type="hidden" name="worksheetTypeCT" value="<%= worksheetTypeCT %>">
 <input type="hidden" name="originalContractGroupFK" value="<%= originalContractGroupFK %>">
 <input type="hidden" name="scheduledTerminationDate" value="<%= scheduledTerminationDate %>">
 <input type="hidden" name="lapsePendingDate" value="<%= lapsePendingDate %>">
 <input type="hidden" name="lapseDate" value="<%= lapseDate %>">
 <input type="hidden" name="paidToDate" value="<%= paidToDate %>">
 <input type="hidden" name="dateInEffectDate" value="<%= dateInEffectDate %>">
 <input type="hidden" name="issueStateORInd" value="<%= issueStateORInd %>">
 <input type="hidden" name="priorPRDDue" value="<%= priorPRDDue %>">
 <input type="hidden" name="currentDeathBenefit" value="<%= currentDeathBenefit %>">
 <input type="hidden" name="guarPaidUpTerm" value="<%= guarPaidUpTerm %>">
 <input type="hidden" name="nonGuarPaidUpTerm" value="<%= nonGuarPaidUpTerm %>">
 <input type="hidden" name="mortalityCredit" value="<%= mortalityCredit %>">
 <input type="hidden" name="endowmentCredit" value="<%= endowmentCredit %>">
 <input type="hidden" name="excessInterestCredit" value="<%= excessInterestCredit %>">
 <input type="hidden" name="dateOfDeathValue" value="<%= dateOfDeathValue %>">
 <input type="hidden" name="originalUnits" value="<%= originalUnits %>">
 <input type="hidden" name="expiryDate" value="<%= expiryDate %>">

<input type="hidden" name="amount" value="<%= amount %>">
<input type="hidden" name="exchangeInd" value="<%= exchangeInd %>">
<input type="hidden" name="cashWithAppInd" value="<%= cashWithAppInd %>">
<input type="hidden" name="waiverInEffect" value="<%= waiverInEffect %>">
<input type="hidden" name="estateOfTheInsured" value="<%= estateOfTheInsured %>">
<input type="hidden" name="sequence" value="<%= sequence %>">
<input type="hidden" name="location" value="<%= location %>">
<input type="hidden" name="indivAnnPremium" value="<%= indivAnnPremium %>">
<input type="hidden" name="issueStateCT" value="<%= issueStateCT %>">
<input type="hidden" name="applicationSignedDate" value="<%= applicationSignedDate %>">
<input type="hidden" name="applicationReceivedDate" value="<%= applicationReceivedDate %>">
<input type="hidden" name="postIssueStatusCT" value="<%= postIssueStatusCT %>">

  <!-- recordPRASEEvents is set by the toolbar -->
 <input type="hidden" name="recordPRASEEvents" value="false">

</form>

</body>
</html>
