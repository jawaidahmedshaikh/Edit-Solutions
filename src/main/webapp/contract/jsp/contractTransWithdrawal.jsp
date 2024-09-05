<!--
 * (c) 2000 - 2004 Systems Engineering Group, LLC.  All rights Reserved
 *  Systems Engineering Group, LLC Propietary and confidential.  Any use
 *  subject to the license agreement.
 -->
<%@ taglib uri="/WEB-INF/SecurityTaglib.tld" prefix="security" %>

<%@ page  errorPage="/common/jsp/error.jsp" autoFlush="true" %>
<%@ page import="java.util.*,
                 edit.common.*,
                 edit.common.vo.*,
                 fission.beans.*,
                 edit.portal.exceptions.*,
                 edit.common.exceptions.VOEditException,
                 fission.utility.Util,
                 fission.utility.*" %>
<%@ page import="edit.services.config.*" %>

<%
    String allowPRASERecording = ServicesConfig.getAllowPRASERecording();

    SessionBean contractTransactionSessionBean = (SessionBean) session.getAttribute("contractTransactionSessionBean");
    SessionBean contractMainSessionBean = (SessionBean) session.getAttribute("contractMainSessionBean");
    SessionBean contractTransactions = (SessionBean) session.getAttribute("contractTransactions");

    String transactionMessage = (String) contractTransactionSessionBean.getValue("transactionMessage");

    if (transactionMessage == null) {

        transactionMessage = "";
    }

    String undoMessage = Util.initString((String) contractTransactionSessionBean.getValue("undoMessage"), "");

    contractTransactionSessionBean.putValue("transactionMessage", "");

    String message = Util.initString((String) request.getAttribute("message"), "");

    String exitMessage = Util.initString((String) request.getAttribute("exitMessage"), "");

    if (exitMessage == null) {

    	exitMessage = "";
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
    String companyStructureKey = Util.initString(contractMainSessionBean.getPageBean("formBean").getValue("companyStructureId"), "0");

	PageBean formBean = contractTransactionSessionBean.getPageBean("formBean");
    PageBean availableContracts = contractTransactionSessionBean.getPageBean("availableContracts");
    PageBean selectedContracts = contractTransactionSessionBean.getPageBean("selectedContracts");
    String drivingSegmentName = formBean.getValue("drivingSegmentName");

    String undoTrxApproved = Util.initString((String) contractTransactionSessionBean.getValue("undoTrxApproved"), "");
    
    String filter           = formBean.getValue("filter");
    String filterType       = formBean.getValue("filterType");
    if (filterType == null) {

        filterType = "";
        filter = "";
    }
    String[] contracts      = null;
    String[] selectedValues = null;
    if (filterType.equalsIgnoreCase("Client ID") && availableContracts != null) 
    {
        contracts = availableContracts.getValues("contracts");

        if (selectedContracts != null) {

            selectedValues = selectedContracts.getValues("contracts");
        }
    }
 	String transactionId 	= formBean.getValue("transactionId");
    String groupSetupPK     = formBean.getValue("groupSetupPK");
    String contractSetupPK  = formBean.getValue("contractSetupPK");
    String clientSetupPK    = formBean.getValue("clientSetupPK");
    String contractClientFK = formBean.getValue("contractClientFK");
    String clientRoleFK     = formBean.getValue("clientRoleFK");
    String editTrxPK        = formBean.getValue("editTrxPK");
    String segmentFK        = formBean.getValue("segmentFK");
    String scheduledEventPK = formBean.getValue("scheduledEventPK");
	String contractId		= formBean.getValue("contractId");
	String ruleId			= formBean.getValue("ruleId");
	String riderId			= formBean.getValue("riderId");
	String riderType		= formBean.getValue("riderType");
	String transactionType	= formBean.getValue("transactionType");

	String effectiveDate	= formBean.getValue("effectiveDate");

	String sequenceNumber	= formBean.getValue("sequenceNumber");
	String amount			= formBean.getValue("amount");
    String percent          = formBean.getValue("percent");

	String grossNetInd		= formBean.getValue("grossNetInd");
	String memoCode         = formBean.getValue("memoCode");
	String taxYear			= formBean.getValue("taxYear");
    String statusInd        = formBean.getValue("statusInd");
    String pendingStatus    = formBean.getValue("pendingStatus");

	String investmentIndStatus	= formBean.getValue("investmentIndStatus");
	String eventIndStatus   = formBean.getValue("eventIndStatus");
	String payeeIndStatus   = formBean.getValue("payeeIndStatus");
    String processIndStatus = formBean.getValue("processIndStatus");
    String correspondenceExistsStatus = formBean.getValue("correspondenceExistsStatus");
    String noCorrespondenceIndStatus = formBean.getValue("noCorrespondenceIndStatus");
    String noAccountingIndStatus = formBean.getValue("noAccountingIndStatus");
    String noCommissionIndStatus = formBean.getValue("noCommissionIndStatus");
    String zeroLoadIndStatus = formBean.getValue("zeroLoadIndStatus");
    String noCheckEFTStatus = formBean.getValue("noCheckEFTStatus");

	String operator         = formBean.getValue("operator");
	String maintDate		= formBean.getValue("maintDate");
	String companyName      = formBean.getValue("companyName");
	String companyStructureId = formBean.getValue("companyStructureId");

	String distributionId   = formBean.getValue("distributionId");
	String lifeContingentId = formBean.getValue("lifeContingentId");

	String eventFees        = formBean.getValue("eventFees");
	String eventLoads       = formBean.getValue("eventLoads");
	String eventPremiumTaxes = formBean.getValue("eventPremiumTaxes");
    String commissionStatus = formBean.getValue("commissionStatus");
    String trxIsRescheduledInd = formBean.getValue("trxIsRescheduledInd");
    String groupPercent     = formBean.getValue("groupPercent");
    String withdrawalType   = formBean.getValue("withdrawalType");
    String suppressDecreaseFaceInd = formBean.getValue("suppressDecreaseFaceInd");
    String complexChangeType = formBean.getValue("complexChangeType");
    String complexChangeValue = formBean.getValue("complexChangeValue");
    String reinsuranceStatus = Util.initString(formBean.getValue("reinsuranceStatus"), "");

    String notificationAmount = formBean.getValue("notificationAmount");
    String notificationAmountReceived = formBean.getValue("notificationAmountReceived");
    String accountingPeriod = formBean.getValue("accountingPeriod");
    String interestOverride = formBean.getValue("interestOverride");
    String newPolicyNumber = formBean.getValue("newPolicyNumber");
    String reapplyEDITTrxFK = formBean.getValue("reapplyEDITTrxFK");
    String premiumDueCreateInd = formBean.getValue("premiumDueCreateInd");

    String contributionExcessDate = formBean.getValue("contributionExcessDate");

    String authorizedSignatureCT = formBean.getValue("authorizedSignatureCT");

    ProductStructureVO[] productStructureVOs = new engine.component.LookupComponent().getProductStructuresByTypeCode("Product");

	CodeTableVO[] frequencies   = codeTableWrapper.getCodeTableEntries("FREQUENCY", Long.parseLong(companyStructureKey));
	CodeTableVO[] distributions = codeTableWrapper.getCodeTableEntries("DISTRIBUTIONCODE", Long.parseLong(companyStructureKey));
	CodeTableVO[] options       = codeTableWrapper.getCodeTableEntries("OPTIONCODE", Long.parseLong(companyStructureKey));
	CodeTableVO[] netGrosses    = codeTableWrapper.getCodeTableEntries("NETGROSSIND", Long.parseLong(companyStructureKey));
	CodeTableVO[] lifeContingencies = codeTableWrapper.getCodeTableEntries("LIFECONTINGENT", Long.parseLong(companyStructureKey));
    CodeTableVO[] yesNo = codeTableWrapper.getCodeTableEntries("YESNO");

	String optionId = formBean.getValue("optionId");

	String rowToMatchBase = formBean.getValue("transactionId");

    String filterContractNumber = (String) session.getAttribute("filterContractNumber");
    if (filterContractNumber == null) {

        filterContractNumber = "";
    }

    String analyzeTrx = (String) request.getAttribute("analyzeTransaction");
    String beginScrollingTrxPK = (String) request.getAttribute("beginScrollingTrxPK");
    String endScrollingTrxPK = (String) request.getAttribute("endScrollingTrxPK");

    String originatingTrxPK = (String) formBean.getValue("originatingTrxFK");
    String originatingTrxExistStatus = "unchecked";
    if (!originatingTrxPK.equals("0") && !originatingTrxPK.equals(""))
    {
        originatingTrxExistStatus = "checked";
    }
%>

<%!
	private TreeMap sortTransactionsByEffectiveDate(SessionBean transactions) {

		Map transactionBeans = transactions.getPageBeans();

		TreeMap sortedTransactions = new TreeMap();

		Iterator enumer  = transactionBeans.values().iterator();

		while (enumer.hasNext()) {

			PageBean transactionBean = (PageBean) enumer.next();

			String key = transactionBean.getValue("transactionId");

            if (!key.equals("")) {
                String effDate = DateTimeUtil.formatMMDDYYYYToYYYYMMDD(transactionBean.getValue("effectiveDate"));
                sortedTransactions.put(effDate + key, transactionBean);
            }
		}

		return sortedTransactions;
	}
%>

<html>

<script src="/PORTAL/common/javascript/commonJavascriptFunctions.js"></script>
<script src="/PORTAL/common/javascript/formatDateFunctions.js"></script>
<script src="/PORTAL/common/javascript/calendarSelector.js"></script>

<script language="Javascript1.2">

    var message = "<%= message %>";
    var transactionMessage = "<%= transactionMessage %>";
    var exitMessage = "<%= exitMessage %>";

    var undoMessage = "<%= undoMessage %>";
    undoMessage = undoMessage.replace(/#/g,"\n");

    var editingExceptionExists = "<%= editingExceptionExists %>";
    var voEditExceptionExists = "<%= voEditExceptionExists %>";
    var analyzeTrx = "<%= analyzeTrx %>";
    var filterType = "<%= filterType %>";
    var height = screen.height;
    var width  = screen.width;
    var drivingSegmentName = "<%= drivingSegmentName %>";
    var transactionType = "<%= transactionType %>";

	var f = null;

    function showCorrespondenceDialog()
    {
        var width = 0.75 * screen.width;
        var height = .50 * screen.height;

		openDialog("correspondenceDialog", "left=0,top=0,resizable=no", width, height);

        prepareToSendTransactionAction("ContractDetailTran", "showCorrespondenceDialog", "correspondenceDialog");
    }

	function init()
    {
        var previousRow = document.getElementById("<%= transactionId %>");

        if (previousRow != null) {

            previousRow.scrollIntoView(false);
        }
		f = document.trxWithdrawalForm;

        if (transactionMessage != "" &&
            transactionMessage != null) {

            alert(transactionMessage);
        }

        if (exitMessage != "" &&
        		exitMessage != null) {

                alert(exitMessage);
            }

        if (message != "")
        {
            alert(message);
        }

        if (undoMessage.length > 0)
    	{
        	allowUndo = confirm("You are using a past effective date that will cause the following transaction(s) to be undone/redone:\n\n" + undoMessage +
        			"\nIf you wish to continue using this past date, please press 'OK' and re-save this transaction." +
					"\n\nOtherwise press 'Cancel' and adjust the transaction as needed.");
        	
        	setUndoTrxApproval();
    	}

        if (analyzeTrx == "true") {

            showAnalyzer();
        }

        formatCurrency();
	}

	function setUndoTrxApproval ()
	{
		if (allowUndo) {
			f.undoTrxApproved.value = "true";
		}
		else {
			f.undoTrxApproved.value = "";
		}
	}

    function filterTransactions()
    {
        if (f.filterContractNumber.value == "")
        {
            f.filtered.value = "FALSE"
        }
        else
        {
            f.filtered.value = "TRUE";
        }

        prepareToSendTransactionAction("ContractDetailTran", "loadTransactionProcess", "_self");
    }

    function scrollTransactionsForward()
    {
        if (f.filterContractNumber.value != "")
        {
            alert("You Cannot Select Next> With Contract Number Filter.  Please Remove Contract Number.");
        }
        else
        {
            f.scrollDirection.value = "FORWARD";

            prepareToSendTransactionAction("ContractDetailTran", "loadTransactionProcess", "_self");
        }
    }

    function scrollTransactionsBackward()
    {
        if (f.filterContractNumber.value != "")
        {
            alert("You Cannot Select <Prev With Contract Number Filter.  Please Remove Contract Number.");
        }
        else
        {
            f.scrollDirection.value = "BACKWARD";

            prepareToSendTransactionAction("ContractDetailTran", "loadTransactionProcess", "_self");
        }
    }

    function selectItemRow(tableName)
    {
        var tdElement = window.event.srcElement;
        var currentRow = tdElement.parentElement;

        var className = currentRow.className;
        var segmentName = currentRow.segmentName;

        if (drivingSegmentName == "")
        {
            if (segmentName == "contract")
            {
                drivingSegmentName = "contract";
            }
            else
            {
                drivingSegmentName = "segment";
            }
        }

        if (drivingSegmentName == "contract" &&
            segmentName != "contract")
        {
            alert("Cannot Select Item at Segment Level; Must Select Contract Number");
        }
        else if (drivingSegmentName == "segment" &&
                 segmentName == "contract")
        {
            alert("Cannot Select Item at Contract Level; Must Select Segment");
        }
        else if (currentRow.isSelected == "false")
        {
            currentRow.style.backgroundColor = "#FFFFCC";
            currentRow.isSelected = "true";
        }
        else
        {
            if (className == "default")
            {
                currentRow.style.backgroundColor = "#BBBBBB";
            }

            currentRow.isSelected = "false";
        }

        if (tableName == "availableItemsTable" &&
            drivingSegmentName != "")
        {
            var resetDrivingSegmentName = checkForSelectedRows();
            if (resetDrivingSegmentName == "true")
            {
                drivingSegmentName = "";
            }
        }
    }

    function checkForSelectedRows()
    {
        var currentTable = document.all.availableItemsTable;
        var resetDrivingSegmentName = "true";
        for (var i = 0; i < currentTable.rows.length; i++)
        {
            if (currentTable.rows[i].isSelected == "true")
            {
                resetDrivingSegmentName = "false";
                i = currentTable.rows.length;
            }
        }

        return resetDrivingSegmentName;
    }

    function selectAvailableItems(howMany)
    {
        if (howMany == "all" && drivingSegmentName == "")
        {
            drivingSegmentName = "contract";
        }

        var selectedContractNumbers = getSelectedContractNumbers("availableItemsTable", howMany);
        var selectedSegmentNames = getSelectedSegmentNames("availableItemsTable", howMany);

        f.selectedContractNumbers.value = selectedContractNumbers;
        f.selectedSegmentNames.value = selectedSegmentNames;
        f.drivingSegmentName.value = drivingSegmentName;

        prepareToSendTransactionAction("ContractDetailTran", "selectAvailableItems", "_self");
    }

    function removeSelectedItems(howMany)
    {
        var selectedContractNumbers = getSelectedContractNumbers("selectedItemsTable", howMany);
        var selectedSegmentNames = getSelectedSegmentNames("selectedItemsTable", howMany);

        f.selectedContractNumbers.value = selectedContractNumbers;
        f.selectedSegmentNames.value = selectedSegmentNames;
        f.drivingSegmentName.value = drivingSegmentName;

        prepareToSendTransactionAction("ContractDetailTran", "removeSelectedItems", "_self");
    }

    function getSelectedContractNumbers(tableName, howMany)
    {
        var currentTable = "";
        if (tableName == "availableItemsTable")
        {
            currentTable = document.all.availableItemsTable;
        }
        else
        {
            currentTable = document.all.selectedItemsTable;
        }

        var selectedContractNumbers = "";

        for (var i = 0; i < currentTable.rows.length; i++)
        {
            if (currentTable.rows[i].isSelected == "true" ||
                howMany == "all")
            {
                if ((howMany == "all" && currentTable.rows[i].segmentName != "contract") ||
                    howMany != "all")
                {
                    selectedContractNumbers += currentTable.rows[i].contractNumber + ",";
                }
            }
        }

        return selectedContractNumbers;
    }

    function getSelectedSegmentNames(tableName, howMany)
    {
        var currentTable = "";
        if (tableName == "availableItemsTable")
        {
            currentTable = document.all.availableItemsTable;
        }
        else
        {
            currentTable = document.all.selectedItemsTable;
        }

        var selectedSegmentNames = "";

        for (var i = 0; i < currentTable.rows.length; i++)
        {
            if (currentTable.rows[i].isSelected == "true" ||
                howMany == "all")
            {
                if ((howMany == "all" && currentTable.rows[i].segmentName != "contract") ||
                    howMany != "all")
                {
                    selectedSegmentNames += currentTable.rows[i].segmentName + ",";
                }
            }
        }

        return selectedSegmentNames;
    }

    function showFilterAllocationDialog()
    {
        var width = .75 * screen.width;
        var height = .55 * screen.height;

        openDialog("filterAllocDialog", "left=0,top=0,resizable=yes", width, height);

        prepareToSendTransactionAction("ContractDetailTran", "showFilterAllocationDialog", "filterAllocDialog");
    }

	function selectRow()
    {
		var tdElement = window.event.srcElement;
		var trElement = tdElement.parentElement;

		var key = trElement.id;

		f.key.value = key;

		prepareToSendTransactionAction("ContractDetailTran", "showTransactionDetailSummary", "_self");
	}

	function showEventDialog()
    {
        var width = .80 * screen.width;
        var height = .40 * screen.height;

		openDialog("eventDialog", "left=0,top=0,resizable=yes", width, height);

        prepareToSendTransactionAction("ContractDetailTran", "showEventDialog", "eventDialog");
	}

	function showProcessOverrideDialog()
    {
        var width = .35 * screen.width;
        var height = .30 * screen.height;

		openDialog("processOverrideDialog", "left=0,top=0,resizable=yes", width, height);

        prepareToSendTransactionAction("ContractDetailTran", "showProcessOverrideDialog", "processOverrideDialog");
	}

	function showAnalyzer()
    {
        var width = screen.width;
        var height = screen.height;

		openDialog("analyzeTransaction", "left=0,top=0,resizable=yes", width, height);

        prepareToSendTransactionAction("ContractDetailTran", "showAnalyzer","analyzeTransaction");
	}

	function showFilterCriteriaDialog()
    {
        var width = .50 * screen.width;
        var height = .37 * screen.height;

	  	openDialog("filterCriteriaDialog", "left=0,top=0,resizable=yes", width, height);

        prepareToSendTransactionAction("ContractDetailTran", "showFilterCriteriaDialog", "filterCriteriaDialog");
	}

	function saveTransactionToSummary()
    {
        try
        {
            if (transactionType == "")
            {
                alert("Transaction Must Be Selected/Entered For Save");
            }
            else
            {
                if (!valueIsZero(f.amount.value) && !valueIsZero(f.percent.value))
                {
                    alert("Cannot Enter Both Amount and Percent Values");
                }
                else
                {
                    // Set the value of recordPRASEEvents in the main jsp so the tran can get it
                    <%
                        if (allowPRASERecording.equals("Y"))  // if it is N, there is no checkbox
                        {
                    %>
                            if (f.recordPRASEEventsCheckBox.checked == true)
                            {
                                f.recordPRASEEvents.value = "true";
                            }
                    <%
                        }
                    %>

                    disableActionButtons();

                    prepareToSendTransactionAction("ContractDetailTran", "saveTransactionToSummary", "_self");
                }
            }
        }
        catch (e)
        {
            alert(e);
        }
	}

	function cancelTransaction()
    {
		prepareToSendTransactionAction("ContractDetailTran", "cancelTransaction", "_self");
	}

	function deleteTransaction()
    {
		prepareToSendTransactionAction("ContractDetailTran", "deleteTransaction", "_self");
	}

	function analyzeTransaction()
    {
		prepareToSendTransactionAction("ContractDetailTran", "analyzeTransaction", "_self");
	}

	function prepareToSendTransactionAction(transaction, action, target)
    {
        if (f.suppressDecreaseFace.checked == true)
        {
            f.suppressDecreaseFaceInd.value = "checked";
        }

        sendTransactionAction(transaction, action, target);
	}

    function checkForEditingException()
    {
        if (editingExceptionExists == "true")
        {
            if (message == "Money Locked")
            {
                var width = .75 * screen.width;
                var height = .25 * screen.height;

                openDialog("lockedValuesDialog", "resizable=no", width, height);

                prepareToSendTransactionAction("ContractDetailTran", "showLockedValuesDialog", "lockedValuesDialog");
            }
            else if (message == "Invalid Investments")
            {
                var width = .75 * screen.width;
                var height = .15 * screen.height;

                openDialog("invalidInvestmentsDialog", "resizable=no", width, height);

                prepareToSendTransactionAction("ContractDetailTran", "showInvalidInvestmentsDialog", "invalidInvestmentsDialog");
            }
            else
            {
                var width = .75 * screen.width;
                var height = screen.height/3;

                openDialog("exceptionDialog", "resizable=no", width, height);

                prepareToSendTransactionAction("ContractDetailTran", "showEditingExceptionDialog", "exceptionDialog");
            }
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

        disableActionButtons();

        prepareToSendTransactionAction(transaction, action, "_self");
    }

    function closeTrxDialog()
    {
        f.filterContractNumber.value = "";
        <%
            session.setAttribute("filterContractNumber", "");
        %>
        closeWindow();
        sendTransactionAction("ContractDetailTran","closeTranDialog","contentIFrame");
    }

    /*
     * Brings up the flex PRASE Events application
     */
    function showPRASEEventsDialog()
    {
        window.open("/PORTAL/flex/SPResultsApplication.jsp", "_blank", "toolbar=no, resizable=yes");
    }

    /**
     * While a tranaction is being saved, we want to disable all buttons to prevent double-saves.
     */
    function disableActionButtons()
    {
        document.all.trxMessage.innerHTML = "<font color='blue' face=''>Processing...<font>";

        document.all.btnAdd.style.backgroundColor = "#99BBBB";
        document.all.btnSave.style.backgroundColor = "#99BBBB";
        document.all.btnCancel.style.backgroundColor = "#99BBBB";
        document.all.btnDelete.style.backgroundColor = "#99BBBB";
        document.all.btnAnalyze.style.backgroundColor = "#99BBBB";
        document.all.textFilterContractNumber.style.backgroundColor = "#99BBBB";
        document.all.btnFLTR.style.backgroundColor = "#99BBBB";
        document.all.btnPrev.style.backgroundColor = "#99BBBB";
        document.all.btnNext.style.backgroundColor = "#99BBBB";

        document.all.btnAdd.disabled = true;
        document.all.btnSave.disabled = true;
        document.all.btnCancel.disabled = true;
        document.all.btnDelete.disabled = true;
        document.all.btnAnalyze.disabled = true;
        document.all.textFilterContractNumber.disabled = true;
        document.all.btnFLTR.disabled = true;
        document.all.btnPrev.disabled = true;
        document.all.btnNext.disabled = true;
    }

    function showOriginatingTrxDialog()
    {
        var width = .60 * screen.width;
        var height= .50 * screen.height;

        openDialog("originatingTrxDialog", "top=0,left=0,resizable=no", width, height);

        sendTransactionAction("ContractDetailTran","showOriginatingTrxDialog", "originatingTrxDialog");
    }

</script>

<head>
<title>EDITSOLUTIONS - Transactions</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta http-equiv="Cache-Control" content="no-store">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Expires" content="0">

<link href="/PORTAL/common/css/EDITSolutions.css" rel="stylesheet" type="text/css">

</head>

<body class="mainTheme" onLoad="init(); checkForEditingException(); checkForVOEditException()">
 <form name="trxWithdrawalForm" method="post" action="/PORTAL/servlet/RequestManager">
  <table border="0" align="left" cellspacing="0" cellpadding="2" height="2%">
    <tr height="100%">
      <td nowrap bgcolor="#99BBBB">Filter&nbsp;
        <input type="text" name="filter" size="15" maxlength="15" value="<%= filter %>">
      </td>
      <td nowrap bgcolor="#99BBBB">Filter Type&nbsp;
        <input type="text" name="filterType" size="15" maxlength="15" value="<%= filterType %>">
      </td>
    </tr>
  </table>

  <br>
  <br>

  <table align="center" width="100%" height="8%" border="0" cellspacing="0" cellpadding="0">
   <tr>
     <td width="43%" style="background-color:#99BBBB">
       <div align="center">Available Items</div>
     </td>
     <td width="10%" style="background-color:#99BBBB">
       &nbsp;
     </td>
     <td width="43%" style="background-color:#99BBBB">
       <div align="center">Selected Items</div>
     </td>
   </tr>
   <tr>
     <td width="43%">
       <span class="scrollableContent" style="border-style:solid; border-width:1;  position:relative; width:95%; height:95%; top:0; left:8; background-color:#BBBBBB">
         <table class="summary" id="availableItemsTable" width="100%" style="border-style:solid; border-width:1;" cellspacing="0" cellpadding="0">
          <%
              String segmentPK = "";
              String companyStructurePK = "";
              String segmentName = "";
              String contractNumber = "";
              String prevContractNumber = "";
              if (contracts != null)
              {
                  for (int c = 0; c < contracts.length; c++)
                  {
                      String className = "default";
                      String[] tokenizedContract = Util.fastTokenizer(contracts[c], ",");
                      contractNumber = tokenizedContract[0];
                      segmentPK = tokenizedContract[1];
                      companyStructurePK = tokenizedContract[2];
                      segmentName = tokenizedContract[3];
                      if (prevContractNumber != contractNumber)
                      {
                          prevContractNumber = contractNumber;
           %>
           <tr class="<%= className %>" segmentName="contract" contractNumber="<%= contractNumber %>"
               onMouseOver="highlightRow()" onMouseOut="unhighlightRow()"
               onClick="selectItemRow('availableItemsTable')" isSelected="false">
             <td>
               <%= contractNumber %>
             </td>
           </tr>
           <%
                      }
           %>
           <tr class="<%= className %>" id="<%= segmentPK %>" contractNumber="<%= contractNumber %>"
               companyStructurePK="<%= companyStructurePK %>" segmentName="<%= segmentName %>"
               isSelected="false" onMouseOver="highlightRow()" onMouseOut="unhighlightRow()"
               onClick="selectItemRow('availableItemsTable')">
             <td>
               &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<%= segmentName %>
             </td>
           </tr>
           <%
                  }
              }
           %>
         </table>
       </span>
     </td>
     <td align="center" width="10%" nowrap bgcolor="#99BBBB">
        <span style="border-style:solid;
              border-width:0; position:relative; width:90%; height:90%; left:0; z-index:0">
          <table id="buttonsTable" width="90%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td align="center" bgcolor="#99BBBB">
                <input type="button" value="ADD >" onClick="selectAvailableItems('selected')">
              </td>
            </tr>
            <tr>
              <td align="center" bgcolor="#99BBBB">
                &nbsp;
              </td>
            </tr>
            <tr>
              <td align="center" bgcolor="#99BBBB">
                <input type="button" value="< REMOVE" onClick="removeSelectedItems('selected')">
              </td>
            </tr>
            <tr>
              <td align="center" bgcolor="#99BBBB">
                &nbsp;
              </td>
            </tr>
            <tr>
              <td align="center" bgcolor="#99BBBB">
                <input type="button" value="SEL ALL" onClick="selectAvailableItems('all')">
              </td>
            </tr>
            <tr>
              <td align="center" bgcolor="#99BBBB">
                &nbsp;
              </td>
            </tr>
            <tr>
              <td align="center" bgcolor="#99BBBB">
                <input type="button" value="SEL NONE" onClick="removeSelectedItems('all')">
              </td>
            </tr>
          </table>
        </span>
     </td>
     <td width="43%">
       <span class="scrollableContent" style="border-style:solid; border-width:1;  position:relative; width:95%; height:95%; top:0; left:5; background-color:#BBBBBB">
         <table class="summary" id="selectedItemsTable" width="100%" style="border-style:solid; border-width:1;" cellspacing="0" cellpadding="0">
           <%
               String selectedSegmentPK = "";
               String selectedCompanyStructurePK = "";
               String selectedContractNumber = "";
               String selectedSegmentName = "";
               String selectedPrevContractNumber = "";
               if (selectedValues != null)
               {
                   for (int c = 0; c < selectedValues.length; c++)
                   {
                       String className = "default";
                       String[] tokenizedValues = Util.fastTokenizer(selectedValues[c], ",");
                       selectedContractNumber = tokenizedValues[0];
                       selectedSegmentPK = tokenizedValues[1];
                       selectedCompanyStructurePK = tokenizedValues[2];
                       selectedSegmentName = tokenizedValues[3];
                       if (selectedPrevContractNumber != selectedContractNumber)
                       {
                           selectedPrevContractNumber = selectedContractNumber;
           %>
           <tr class="<%= className %>" segmentName="contract" contractNumber="<%= selectedContractNumber %>"
               onMouseOver="highlightRow()" onMouseOut="unhighlightRow()"
               onClick="selectItemRow('selectedItemsTable')" isSelected="false">
             <td>
               <%= selectedContractNumber %>
             </td>
           </tr>
           <%
                       }
           %>
           <tr class="<%= className %>" id="<%= selectedSegmentPK %>" contractNumber="<%= selectedContractNumber %>"
               companyStructurePK="<%= selectedCompanyStructurePK %>" segmentName="<%= selectedSegmentName %>"
               isSelected="false" onMouseOver="highlightRow()" onMouseOut="unhighlightRow()"
               onClick="selectItemRow('selectedItemsTable')">
             <td>
               &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<%= selectedSegmentName %>
             </td>
           </tr>
           <%
                   }
               }
           %>
          </table>
        </span>
      </td>
    </tr>
    <br>
  </table>

  <table width="100%" height="40%" border="0" style="border-style:solid; border-width:1; border-color:Black; background-color:#BBBBBB">
    <tr>
      <td nowrap align="right">CompanyKey:&nbsp;</td>
      <td nowrap align="left">
        <select name="companyStructureId">
          <%
         	out.println("<option>Please Select</option>");

			for(int i=0; i< productStructureVOs.length; i++){

                String currentCompanyStructurePK = productStructureVOs[i].getProductStructurePK() + "";

			if (companyStructureId.equals(currentCompanyStructurePK)) {

				out.println("<option selected value=\""+ currentCompanyStructurePK + "\">" + Util.getProductStructure(productStructureVOs[i], ",") + "</option>");
			}else

				out.println("<option value=\""+ currentCompanyStructurePK + "\">" + Util.getProductStructure(productStructureVOs[i], ",") + "</option>");
			}
          %>
        </select>
      </td>
      <td nowrap align="right">Contract Number:&nbsp;</td>
      <td nowrap align="left">
        <input disabled type="text" name="contractId" size="15" maxlength="15" value="<%= contractId%>">
      </td>
    </tr>
    <tr>
      <td nowrap align="right">Transaction Type:&nbsp;</td>
      <td nowrap align="left">
        <input disabled type="text" name="transactionType" size="15" maxlength="15" value="<%= transactionType%>">
      </td>
      <td nowrap align="right">Sequence:&nbsp;</td>
      <td nowrap align="left">
        <input disabled type="text" name="sequenceNumber" size="4" maxlength="4" value="<%= sequenceNumber%>">
      </td>
    </tr>
    <tr>
      <td nowrap align="right">Effective Date:&nbsp;</td>
      <td nowrap align="left">
           <input type="text" name="effectiveDate" value="<%= effectiveDate %>" size='10' maxlength="10" onKeyUp="DateFormat(this,this.value,event,false)" onBlur="DateFormat(this,this.value,event,true)">
           <a href="javascript:show_calendar('f.effectiveDate', f.effectiveDate.value);"><img src="/PORTAL/common/images/calendarIcon.gif" width="16" height="16" border="0" alt="Select a date from the calendar"></a>
      </td>

      <td nowrap align="right">Tax Year:&nbsp</td>
      <td nowrap align="left">
        <input type="text" name="taxYear" size="4" maxlength="4" value="<%= taxYear %>">
      </td>
      <td nowrap align="left">
        <input type="checkbox" name="originatingTrxExistInd" <%= originatingTrxExistStatus %> disabled>
        <a href="javascript:showOriginatingTrxDialog()">Originating Transaction Info&nbsp;</a>
      </td>
    </tr>
    <tr>
      <td nowrap align="right">Amount:&nbsp;</td>
      <td nowrap align="left">
        <input type="text" name="amount" size="12" maxlength="12" value="<%= amount%>" CURRENCY>
      </td>
      <td nowrap align="right">Percent:&nbsp;</td>
      <td nowrap align="left">
        <input type="text" name="percent" size="12" maxlength="12" value="<%= percent %>">
      </td>
    </tr>
    <tr>
      <td nowrap align="right">Coverage/Rider Type:&nbsp;</td>
      <td nowrap align="left">
      <select name="optionId">
          <%
            out.println("<option>Please Select</option>");

            for(int i = 0; i < options.length; i++) {

                String codeTablePK = options[i].getCodeTablePK() + "";
                String codeDesc    = options[i].getCodeDesc();
                String code        = options[i].getCode();

                if (optionId.equalsIgnoreCase(codeTablePK)) {

                    out.println("<option selected name=\"id\" value=\"" + codeTablePK+ "\">" + codeDesc + "</option>");
                }
                else  {

                    out.println("<option name=\"id\" value=\"" + codeTablePK + "\">" + codeDesc + "</option>");
                }
            }
          %>
        </select>
      </td>
      <td nowrap align="right">Distribution Code Override:&nbsp;</td>
      <td nowrap align="left">
        <select name="distributionId">
          <%
              out.println("<option>Please Select</option>");

              for(int i = 0; i < distributions.length; i++) {

                  String codeTablePK = distributions[i].getCodeTablePK() + "";
                  String codeDesc    = distributions[i].getCodeDesc();
                  String code        = distributions[i].getCode();

                  if (distributionId.equalsIgnoreCase(codeTablePK)) {

                      out.println("<option selected name=\"id\" value=\"" + codeTablePK+ "\">" + codeDesc + "</option>");
                  }
                  else  {

                      out.println("<option name=\"id\" value=\"" + codeTablePK + "\">" + codeDesc + "</option>");
                  }
              }

          %>
        </select>
      </td>
      <td nowrap align="left">
        Suppress Decrease Face:&nbsp;<input type="checkbox" name="suppressDecreaseFace" <%=suppressDecreaseFaceInd %>>
      </td>
    </tr>
    <tr>
      <td nowrap align="right">Memo Code:&nbsp;</td>
      <td nowrap align="left">
        <input type="text" name="memoCode" size="7" maxlength="7" value="<%= memoCode %>">
      </td>
      <td nowrap align="right">Net/Gross:&nbsp;</td>
      <td nowrap align="left">
        <select name="grossNetInd">
          <%
              out.println("<option>Please Select</option>");

              for(int i = 0; i < netGrosses.length; i++) {

                  String codeTablePK = netGrosses[i].getCodeTablePK() + "";
                  String codeDesc    = netGrosses[i].getCodeDesc();
                  String code        = netGrosses[i].getCode();

                  if (grossNetInd.equalsIgnoreCase(codeTablePK)) {

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
      <td nowrap align="right">Overrides:&nbsp;</td>
      <td nowrap align="left">
        <input type="checkbox" name="eventInd" <%= eventIndStatus %> disabled>
        <a href="javascript:showEventDialog()">Charges</a>
        <input type="checkbox" name="correspondenceInd" <%= correspondenceExistsStatus %> disabled>
        <a href="javascript:showCorrespondenceDialog()">Correspondence</a>
      </td>
      <td nowrap align="right">Date Contribution Excess:&nbsp;</td>
      <td nowrap align="left">
           <input type="text" name="contributionExcessDate" value="<%= contributionExcessDate %>" size='10' maxlength="10" onKeyUp="DateFormat(this,this.value,event,false)" onBlur="DateFormat(this,this.value,event,true)">
           <a href="javascript:show_calendar('f.contributionExcessDate', f.contributionExcessDate.value);"><img src="/PORTAL/common/images/calendarIcon.gif" width="16" height="16" border="0" alt="Select a date from the calendar"></a>
       </td>

      <td nowrap align="right">Authorized Signature:&nbsp;
        <select name="authorizedSignatureCT">
          <%
              out.println("<option>Please Select</option>");

              for(int i = 0; i < yesNo.length; i++) {

                  String codeTablePK = yesNo[i].getCodeTablePK() + "";
                  String codeDesc    = yesNo[i].getCodeDesc();
                  String code        = yesNo[i].getCode();

                  if (authorizedSignatureCT.equalsIgnoreCase(codeTablePK)) {

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
      <td nowrap align="right">&nbsp;</td>
      <td nowrap align="left">
        <input type="checkbox" name="processInd" <%= processIndStatus %> disabled>
        <a href="javascript:showProcessOverrideDialog()">Process</a>
      </td>
      <td nowrap align="right" colspan="2">&nbsp;</td>
    </tr>
    <tr>
      <td nowrap align="right">Operator:&nbsp;</td>
      <td nowrap align="left" colspan="3">
        <table width="100%" cellspacing="2" cellpadding="0">
          <tr>
            <td nowrap align="left">
              <input type="text" name="operator" disabled value="<%= operator %>">
            </td>
            <td width="13%" nowrap align="right">Date/Time:&nbsp;</td>
            <td nowrap align="left">
              <input type="text" name="dateTime" disabled value="<%= maintDate %>">
            </td>
            <td width="13%" nowrap align="right">Accounting Period:&nbsp;</td>
            <td nowrap align="left">
              <input type="text" size="7" maxlength="7" name="accountingPeriod" value="<%= accountingPeriod %>">
            </td>
            <td nowrap align="left">
              <input type="button" value="FLTR ALLOC" onClick="showFilterAllocationDialog()">
            </td>
          </tr>
        </table>
      </td>
    </tr>
  </table>
  <table width="100%" height="1%" border="0" cellspacing="0" cellpadding="0" style="border-style:solid; border-width:1; border-color:Black; background-color:#BBBBBB">
    <tr>
      <td nowrap align="left">
  		<input id="btnAdd" type="button" value="   Add   " onClick="showFilterCriteriaDialog()">
  		<input id="btnSave" type="button" value="   Save  " onClick="saveTransactionToSummary()">
  		<input id="btnCancel" type="button" value="  Cancel " onClick="cancelTransaction()">
  		<input id="btnDelete" type="button" value="  Delete " onClick="deleteTransaction()">
        <input id="btnAnalyze" type="button" value=" Analyze " onClick="analyzeTransaction()">
  	  </td>
      <%
         if (allowPRASERecording.equals("Y"))
         {
      %>
            <td>
                PRASE Events: &nbsp;
                <input type="checkbox" name="recordPRASEEventsCheckBox">Record &nbsp;
                <img src="/PORTAL/common/images/btnShow.gif" onClick="showPRASEEventsDialog()">
            </td>
      <%
          }
      %>
      <td id="trxMessage">
        &nbsp;
      </td>
      <td nowrap align="right">
        <input id="textFilterContractNumber" type="text" name="filterContractNumber" size="15" maxlength="15" value="<%= filterContractNumber %>">
        <input id="btnFLTR" type="button" value="   FLTR    " onClick="filterTransactions()">
  		<input id="btnPrev" type="button" value="   <Prev   " onClick="scrollTransactionsBackward()">
  		<input id="btnNext" type="button" value="   Next>   " onClick="scrollTransactionsForward()">
      </td>
    </tr>
  </table>
  <table class="summary" width="100%" cellspacing="0" border="0">
    <tr class="heading">
      <th width="12%">Company</th>
      <th width="12%">Contract Number</th>
      <th width="12%">Effective Date</th>
  	  <th width="12%">Seq</th>
  	  <th width="12%">Status</th>
  	  <th width="12%">Transaction</th>
  	  <th width="12%">Amount</th>
  	  <th width="12%">Coverage/Rider</th>
    </tr>
  </table>
  <span class="scrollableContent" style="border-style:solid; border-width:1;  position:relative; width:100%; height:49%; top:0; left:0; background-color:#BBBBBB">
    <table class="summary" id="contractTrxSummary" width="100%" style="border-style:solid; border-width:0;" cellspacing="0" cellpadding="0">
      <%
        String rowToMatch = "";
        String className = "";
        boolean selected = false;

        if (contractTransactions.hasPageBeans())
        {
            Map sortedTransactions = sortTransactionsByEffectiveDate(contractTransactions);

            Iterator it = sortedTransactions.values().iterator();

            while (it.hasNext())
            {
                PageBean transactionBean = (PageBean) it.next();

                String tCompanyName 	  = transactionBean.getValue("companyName");
                String tContractId		  = transactionBean.getValue("contractId");
                String tEffectiveDate	  = transactionBean.getValue("effectiveDate");
                String tSequenceNumber	  = transactionBean.getValue("sequenceNumber");
                String tTransactionStatus = transactionBean.getValue("statusInd");
                String tTransactionType	  = transactionBean.getValue("transactionType");
                String tAmount			  = transactionBean.getValue("amount");
                String tOptionIdValue     = transactionBean.getValue("optionId");

                // Store behind the scenes...
                String key = transactionBean.getValue("transactionId");

                rowToMatch = key;

                if (rowToMatch.equals(rowToMatchBase))
                {
                    className = "highlighted";

                    selected = true;
                }
                else
                {
                    className = "default";

                    selected = false;
                }
      %>
        <tr class="<%= className %>" isSelected="<%= selected %>" id="<%= key %>"
            onMouseOver="highlightRow()" onMouseOut="unhighlightRow()"
            onClick="selectRow()">
            <td nowrap width="12%">
                <%= tCompanyName %>
            </td>
            <td nowrap width="12%">
                <%= tContractId %>
            </td>
            <td nowrap width="12%">
                <%= tEffectiveDate %>
            </td>
            <td nowrap width="12%">
                <%= tSequenceNumber %>
            </td>
            <td nowrap width="12%">
                <%= tTransactionStatus %>
            </td>
            <td nowrap width="12%">
                <%= tTransactionType %>
            </td>
            <td nowrap width="12%">
                <script>document.write(formatAsCurrency(<%= tAmount %>))</script>
            </td>
            <td nowrap width="12%">
                <%= tOptionIdValue %>
            </td>
        </tr>
      <%
            }// end while
        } // end if
      %>
    </table>
  </span>
      </td>
    </tr>
  </table>
  <table id="closeTable" width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
	  <td colspan="8" nowrap align="right">
	  	<input type="button" value="   Close  " onClick="closeTrxDialog()">
      </td>
    </tr>
  </table>

<!-- ****** HIDDEN FIELDS ***** //-->
 <input type="hidden" name="transaction"   value="">
 <input type="hidden" name="transactionId" value="<%= transactionId %>">
 <input type="hidden" name="statusInd"     value="<%= statusInd %>">
 <input type="hidden" name="pendingStatus"     value="<%= pendingStatus %>"> 
 <input type="hidden" name="action"        value="">
 <input type="hidden" name="key"           value="">
 <input type="hidden" name="ignoreEditWarnings" value="">
 <input type="hidden" name="filtered" value="">
 <input type="hidden" name="scrollDirection" value="">
 <input type="hidden" name="selectedContractNumbers" value="">
 <input type="hidden" name="selectedSegmentNames" value="">
 <input type="hidden" name="drivingSegmentName" value="">
 <input type="hidden" name="contractId" value="<%= contractId %>">
 <input type="hidden" name="transactionType" value="<%= transactionType %>">
 <input type="hidden" name="sequenceNumber" value="<%= sequenceNumber %>">
 <input type="hidden" name="beginScrollingTrxPK" value="<%= beginScrollingTrxPK %>">
 <input type="hidden" name="endScrollingTrxPK" value="<%= endScrollingTrxPK %>">
 <input type="hidden" name="noCorrespondenceIndStatus" value="<%= noCorrespondenceIndStatus %>">
 <input type="hidden" name="noAccountingIndStatus" value="<%= noAccountingIndStatus %>">
 <input type="hidden" name="noCommissionIndStatus" value="<%= noCommissionIndStatus %>">
 <input type="hidden" name="zeroLoadIndStatus" value="<%= zeroLoadIndStatus %>">
 <input type="hidden" name="noCheckEFTStatus" value="<%= noCheckEFTStatus %>">
 <input type="hidden" name="processIndStatus" value="<%= processIndStatus %>">
 <input type="hidden" name="groupSetupPK" value="<%= groupSetupPK %>">
 <input type="hidden" name="contractSetupPK" value="<%= contractSetupPK %>">
 <input type="hidden" name="clientSetupPK" value="<%= clientSetupPK %>">
 <input type="hidden" name="contractClientFK" value="<%= contractClientFK %>">
 <input type="hidden" name="editTrxPK" value="<%= editTrxPK %>">
 <input type="hidden" name="commissionStatus" value="<%= commissionStatus %>">
 <input type="hidden" name="trxIsRescheduledInd" value="<%= trxIsRescheduledInd %>">
 <input type="hidden" name="groupPercent" value="<%= groupPercent %>">
 <input type="hidden" name="withdrawalType" value="<%= withdrawalType %>">
 <input type="hidden" name="clientRoleFK" value="<%= clientRoleFK %>">
 <input type="hidden" name="noScrolling" value="">
 <input type="hidden" name="suppressDecreaseFaceInd" value="<%= suppressDecreaseFaceInd %>">
 <input type="hidden" name="complexChangeType" value="<%= complexChangeType %>">
 <input type="hidden" name="complexChangeValue" value="<%= complexChangeValue %>">
 <input type="hidden" name="notificationAmount" value="<%= notificationAmount %>">
 <input type="hidden" name="notificationAmountReceived" value="<%= notificationAmountReceived %>">
 <input type="hidden" name="reinsuranceStatus" value="<%= reinsuranceStatus %>">
 <input type="hidden" name="originatingTrxPK" value="<%= originatingTrxPK %>">
 <input type="hidden" name="companyStructureId" value="<%= companyStructureId %>">
 <input type="hidden" name="contributionExcessDate" value="">
 <input type="hidden" name="interestOverride" value="<%= interestOverride %>">
 <input type="hidden" name="newPolicyNumber" value="<%= newPolicyNumber %>">
 <input type="hidden" name="reapplyEDITTrxFK" value="<%= reapplyEDITTrxFK %>">
 <input type="hidden" name="premiumDueCreateInd" value="<%= premiumDueCreateInd %>">
 <input type="hidden" name="undoTrxApproved" value="<%= undoTrxApproved %>">
 <input type="hidden" name="recordPRASEEvents" value="false">

</form>
</body>
</html>
