<%@ taglib uri="/WEB-INF/SecurityTaglib.tld" prefix="security" %>
<%@ page  errorPage="/common/jsp/error.jsp" autoFlush="true" %>
<%@ page import="java.util.*,
				 java.math.*,
				 billing.*,
                 edit.common.*,
                 edit.common.vo.*,
                 fission.beans.*,
                 edit.portal.exceptions.*,
                 edit.common.exceptions.VOEditException,
                 event.Suspense,
                 fission.utility.Util,
                                  edit.portal.widgettoolkit.TableModel,
                                  edit.portal.widget.*,
                 
                 fission.utility.*" %>
<%@ page import="edit.services.config.*" %>

<%
    String allowPRASERecording = ServicesConfig.getAllowPRASERecording();

    SessionBean contractTransactionSessionBean = (SessionBean) session.getAttribute("contractTransactionSessionBean");
    SessionBean contractMainSessionBean = (SessionBean) session.getAttribute("contractMainSessionBean");
    SessionBean contractTransactions = (SessionBean) session.getAttribute("contractTransactions");

    String transactionMessage = (String) contractTransactionSessionBean.getValue("transactionMessage");

    String message = Util.initString((String) request.getAttribute("message"), "");

    if (transactionMessage == null) {

        transactionMessage = "";
    }

    String exitMessage = Util.initString((String) request.getAttribute("exitMessage"), "");

    if (exitMessage == null) {

    	exitMessage = "";
    }
    
    String undoMessage = Util.initString((String) contractTransactionSessionBean.getValue("undoMessage"), "");

    contractTransactionSessionBean.putValue("transactionMessage", "");

    String undoTrxApproved = Util.initString((String) contractTransactionSessionBean.getValue("undoTrxApproved"), "");

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
    PageBean selectedContracts  = contractTransactionSessionBean.getPageBean("selectedContracts");
    String drivingSegmentName = formBean.getValue("drivingSegmentName");

    String filter          = formBean.getValue("filter");
    String filterType      = formBean.getValue("filterType");
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
 	String transactionId    = formBean.getValue("transactionId");
    String groupSetupPK     = formBean.getValue("groupSetupPK");
    String contractSetupPK  = formBean.getValue("contractSetupPK");
    String clientSetupPK    = formBean.getValue("clientSetupPK");
    String contractClientFK = formBean.getValue("contractClientFK");
    String clientRoleFK     = formBean.getValue("clientRoleFK");
    String editTrxPK        = formBean.getValue("editTrxPK");
    String segmentFK        = formBean.getValue("segmentFK");
	String contractId	    = formBean.getValue("contractId");
	String ruleId		    = formBean.getValue("ruleId");
	String riderId		    = formBean.getValue("riderId");
	String riderType	    = formBean.getValue("riderType");
	String transactionType  = "Premium";

	String effectiveDate   = formBean.getValue("effectiveDate");
	String startDate	   = formBean.getValue("startDate");
	String stopDate		   = formBean.getValue("stopDate");

	String sequenceNumber  = formBean.getValue("sequenceNumber");
	String statusType	   = formBean.getValue("statusType");
	String statusInd	   = formBean.getValue("statusInd");
    String pendingStatus   = formBean.getValue("pendingStatus");
	String amount		   = formBean.getValue("amount");
	String grossNetInd	   = formBean.getValue("grossNetInd");
	String memoCode		   = formBean.getValue("memoCode");
	String premiumType	   = formBean.getValue("premiumType");
    if (premiumType == null)
    {
        premiumType = "";
    }
	String taxYear		   = formBean.getValue("taxYear");
	String investmentIndStatus = formBean.getValue("investmentIndStatus");
	String eventIndStatus  = formBean.getValue("eventIndStatus");
    String processIndStatus = formBean.getValue("processIndStatus");
    String correspondenceExistsStatus = formBean.getValue("correspondenceExistsStatus");
    String employerEmployeeIndStatus = formBean.getValue("employerEmployeeIndStatus");
	String payeeIndStatus  = formBean.getValue("payeeIndStatus");
	String operator		   = formBean.getValue("operator");
	String maintDate	   = formBean.getValue("maintDate");
	String companyName     = formBean.getValue("companyName");
	String companyStructureId = formBean.getValue("companyStructureId");
    String noCorrespondenceIndStatus = formBean.getValue("noCorrespondenceIndStatus");
    String noAccountingIndStatus = formBean.getValue("noAccountingIndStatus");
    String noCommissionIndStatus = formBean.getValue("noCommissionIndStatus");
    String zeroLoadIndStatus = formBean.getValue("zeroLoadIndStatus");
    String noCheckEFTStatus = formBean.getValue("noCheckEFTStatus");
    String employerContribution = formBean.getValue("employerContribution");
    String employeeContribution = formBean.getValue("employeeContribution");
    String commissionStatus = formBean.getValue("commissionStatus");
    String trxIsRescheduledInd = formBean.getValue("trxIsRescheduledInd");
    String groupPercent = formBean.getValue("groupPercent");
    String withdrawalType = formBean.getValue("withdrawalType");
    String notificationAmount = formBean.getValue("notificationAmount");
    String notificationAmountReceived = formBean.getValue("notificationAmountReceived");
    String reinsuranceStatus = Util.initString(formBean.getValue("reinsuranceStatus"), "");
    String accountingPeriod = formBean.getValue("accountingPeriod");
    String interestOverride = formBean.getValue("interestOverride");
    String newPolicyNumber = formBean.getValue("newPolicyNumber");
    String reapplyEDITTrxFK = formBean.getValue("reapplyEDITTrxFK");
    String premiumDueCreateInd = formBean.getValue("premiumDueCreateInd");

    ProductStructureVO[] productStructureVOs = new engine.component.LookupComponent().getAllProductStructures();

    CodeTableVO[] options       = codeTableWrapper.getCodeTableEntries("OPTIONCODE", Long.parseLong(companyStructureKey));
    CodeTableVO[] premiumTypes  = codeTableWrapper.getCodeTableEntries("PREMIUMTYPE", Long.parseLong(companyStructureKey));

	String optionId           = formBean.getValue("optionId");
	if ((optionId == null || optionId.equals("")) && options != null && options.length > 0) {
		optionId = options[0].getCodeTablePK()+"";
	}
	
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
    
    EDITDate currentDate = new EDITDate();
    
    String trxSource = formBean.getValue("trxSource");

%>

<%!
	private TreeMap sortTransactionsByEffectiveDate(SessionBean transactions) {

		Map transactionBeans = transactions.getPageBeans();

		TreeMap sortedTransactions = new TreeMap();

		Iterator enumer  = transactionBeans.values().iterator();

		while (enumer.hasNext()) {

			PageBean transactionBean = (PageBean) enumer.next();

			String key = transactionBean.getValue("transactionId");
			String transactionType = transactionBean.getValue("transactionType");
			
            if (!key.equals("") && transactionType != null && transactionType.equalsIgnoreCase("Premium")) {
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
<script src="/PORTAL/common/javascript/widgetFunctions.js"></script>
<script src="/PORTAL/common/javascript/scrollTable.js"></script>

<script language="Javascript1.2">

    var transactionMessage = "<%= transactionMessage %>";
    var message = "<%= message %>";
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
    var allowUndo = "";

	var f = null;

	function init()
    {
        var previousRow = document.getElementById("<%= transactionId %>");

        if (previousRow != null) {

            previousRow.scrollIntoView(false);
        }

		f = document.trxPaymentForm;

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

	function showAnalyzer()
    {
        var width = screen.width;
        var height = screen.height;

		openDialog("analyzeTransaction", "left=0,top=0,resizable=yes", width, height);

        prepareToSendTransactionAction("ContractDetailTran", "showAnalyzer","analyzeTransaction");
    }

	function saveTransactionToSummary()
    {
        try
        {
            if (f.amount.value == "" ||
                     f.amount.value == "0" ||
                     f.amount.value == "0.0")
            {
                alert("Payment Amount Must Be Entered");
            }
            else
            {
                disableActionButtons();

                prepareToSendTransactionAction("ContractDetailTran", "saveTransactionToSummary", "_self");
            }
        }
        catch (e)
        {
            alert(e.message);
            
        }
	}

	function cancelTransaction()
    {
		prepareToSendTransactionAction("ContractDetailTran", "cancelTransaction", "_self");
	}

	function analyzeTransaction()
    {
		prepareToSendTransactionAction("ContractDetailTran", "analyzeTransaction", "_self");
	}

	function prepareToSendTransactionAction(transaction, action, target)
    {
        sendTransactionAction(transaction, action, target);
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
        prepareToSendTransactionAction(transaction, action, "_self");
    }

    function closeTrxDialog()
    {
        f.filterContractNumber.value = "";
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

        document.all.btnSave.style.backgroundColor = "#99BBBB";
        document.all.btnCancel.style.backgroundColor = "#99BBBB";
        
        document.all.btnSave.disabled = true;
        document.all.btnCancel.disabled = true;
    }

    function onTableRowSingleClick(tableId)
    {
        sendTransactionAction("ContractDetailTran", "showQuickPayDialog", "_self");
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
<form name="trxPaymentForm" method="post" action="/PORTAL/servlet/RequestManager">
  <table width="100%" height="40%" style="border-style:solid; border-width:1; border-color:Black; background-color:#BBBBBB">
    <tr>
      <td nowrap width="15%" align="right">Company Key:</td>
      <td align="left">
        <select name="companyStructureId" id="companyStructureId">
          <%
	     	out.println("<option>Please Select</option>");

            if (productStructureVOs != null)
            {
                for(int i = 0; i < productStructureVOs.length; i++)
                {
                    String currentCompanyStructurePK = productStructureVOs[i].getProductStructurePK() + "";
                    String currentCompanyStructure = Util.getProductStructure(productStructureVOs[i], ",");

                    if (companyStructureId.equals(currentCompanyStructurePK))
                    {
                        out.println("<option selected value=\""+ currentCompanyStructurePK + "\">" + currentCompanyStructure + "</option>");
                    }
                    else
                    {
                        out.println("<option value=\""+ currentCompanyStructurePK + "\">" + currentCompanyStructure + "</option>");
                    }
                }
            }
		  %>
        </select>
      </td>
      <td width="15%" nowrap align="right">Contract Number:</td>
      <td width="14%" colspan="5" align="left">
        <input disabled type="text" name="contractId" size="15" colspan="3" maxlength="15" value="<%= contractId %>">
      </td>
    </tr>
    <tr>
      <td width="15%" nowrap align="right">Transaction:</td>
      <td align="left">
        <input disabled type="text" name="transactionType" size="15" maxlength="15"  colspan="3"value="<%= transactionType %>">
      </td>
    </tr>
    <tr>
      <td nowrap width="15%" align="right">Effective Date:</td>
      <td height="32" nowrap align="left">
           <input type="text" name="effectiveDate" value="<%= effectiveDate %>" size='10' maxlength="10" onKeyUp="DateFormat(this,this.value,event,false)" onBlur="DateFormat(this,this.value,event,true)">
           <a href="javascript:show_calendar('f.effectiveDate', f.effectiveDate.value);"><img src="/PORTAL/common/images/calendarIcon.gif" width="16" height="16" border="0" alt="Select a date from the calendar"></a>
      </td>
    </tr>
    <tr>
      <td nowrap align="right">Coverage/Rider:</td>
      <td align="left" nowrap>
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
                  else {

                      out.println("<option name=\"id\" value=\"" + codeTablePK + "\">" + codeDesc + "</option>");
                  }
              }
		  %>
        </select>
      </td>
     
    </tr>
    <tr>
      <td height="0" width="15%" align="right">Memo Code:</td>
      <td width="12%" align="left">
        <input type="text" name="memoCode" size="7" maxlength="7" value="<%= memoCode %>">
      </td>
    </tr>
    <tr>
      <td nowrap width="15%" align="right">Payment Amount:</td>
      <td width="12%" align="left">
        <input disabled type="text" name="amount" size="12" maxlength="12" value="<%= amount %>" CURRENCY>
      </td>
    </tr>
    <tr>
      <td align="right"nowrap width="15%">Premium Type:</td>
      <td align="left" nowrap>
        <select name="premiumType">
          <%
		      out.println("<option>Please Select</option>");

              for(int i = 0; i < premiumTypes.length; i++) {

                  String codeTablePK = premiumTypes[i].getCodeTablePK() + "";
                  String codeDesc    = premiumTypes[i].getCodeDesc();
                  String code        = premiumTypes[i].getCode();

                  if (premiumType.equalsIgnoreCase(codeTablePK)) {

                      out.println("<option selected name=\"id\" value=\"" + codeTablePK+ "\">" + codeDesc + "</option>");
                  }
                  else {

                      out.println("<option name=\"id\" value=\"" + codeTablePK + "\">" + codeDesc + "</option>");
                  }
              }
		  %>
        </select>
      </td>
      
    </tr>
    
    <tr>
      <td width="15%" align="right">&nbsp;</td>
      <td width="12%">&nbsp;</td>
    </tr>
  </table>
  <table width="100%" height="1%" border="0" cellspacing="0" cellpadding="0" style="border-style:solid; border-width:1; border-color:Black; background-color:#BBBBBB">
    <tr>
	  	<td nowrap align="left">
			<input id="btnSave" type="button" value="   Save  " onClick="saveTransactionToSummary()">
	  		<input id="btnCancel" type="button" value="  Cancel " onClick="cancelTransaction()">
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
    </tr>
  </table>

          <br>
  <h4 align="center">- Suspense -</h4>

<%-- ********** BEGIN Suspense Summary Area ********** --%>

<jsp:include page="/common/jsp/widget/tableWidget.jsp" flush="true">
  <jsp:param name="tableId" value="TransactionSuspenseTableModel"/>
  <jsp:param name="tableHeight" value="30"/>
  <jsp:param name="multipleRowSelect" value="true"/>
  <jsp:param name="singleOrDoubleClick" value="single"/>
</jsp:include>

<%-- ********** END Suspense Summary Area ********** --%>

  <br>
  <h4 align="center">- Pending PYs -</h4>
  
  <table class="summary" border="0" width="100%" cellspacing="0" cellpadding="0">
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
  <span class="scrollableContent" style="border-style:solid; border-width:1;  position:relative; width:100%; height:20%; top:0; left:0; background-color:#BBBBBB">
    <table id="contractTrxSummary" width="100%" style="border-style:solid; border-width:0;" cellspacing="0" cellpadding="0">
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

                if (rowToMatch.equals(rowToMatchBase)) {

                    className = "highlighted";

                    selected = true;
                }
                else {

                    className = "default";

                    selected = false;
                }
      %>
        <tr class="<%= className %>" isSelected="<%= selected %>" id="<%= key %>">
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
	  <td colspan="8" nowrap align="right" bgcolor="#99BBBB">
	  	<input type="button" value="   Close  " onClick="closeTrxDialog()">
      </td>
    </tr>
  </table>

<!-- ****** HIDDEN FIELDS ***** //-->
 <input type="hidden" name="transaction"  value="">
 <input type="hidden" name="action"       value="">
 <input type="hidden" name="key"         value="">
 <input type="hidden" name="transactionId"  value="<%= transactionId %>">
 <input type="hidden" name="statusInd" value="<%= statusInd %>">
 <input type="hidden" name="pendingStatus" value="<%= pendingStatus %>">
 <input type="hidden" name="segmentFK"     value="<%= segmentFK %>">
 <input type="hidden" name="ignoreEditWarnings" value="">
 <input type="hidden" name="filtered" value="">
 <input type="hidden" name="scrollDirection" value="">
 <input type="hidden" name="selectedContractNumbers" value="">
 <input type="hidden" name="selectedSegmentNames" value="">
 <input type="hidden" name="drivingSegmentName" value="">
 <input type="hidden" name="filter" value="<%= filter %>">
 <input type="hidden" name="filterType" value="<%= filterType %>">
 <input type="hidden" name="contractId" value="<%= contractId %>">
 <input type="hidden" name="transactionType" value="<%= transactionType %>">
 <input type="hidden" name="groupSetupPK" value="<%= groupSetupPK %>">
 <input type="hidden" name="contractSetupPK" value="<%= contractSetupPK %>">
 <input type="hidden" name="clientSetupPK" value="<%= clientSetupPK %>">
 <input type="hidden" name="contractClientFK" value="<%= contractClientFK %>">
 <input type="hidden" name="editTrxPK" value="<%= editTrxPK %>">
 <input type="hidden" name="sequenceNumber" value="<%= sequenceNumber %>">
 <input type="hidden" name="beginScrollingTrxPK" value="<%= beginScrollingTrxPK %>">
 <input type="hidden" name="endScrollingTrxPK" value="<%= endScrollingTrxPK %>">
 <input type="hidden" name="noCorrespondenceIndStatus" value="<%= noCorrespondenceIndStatus %>">
 <input type="hidden" name="noAccountingIndStatus" value="<%= noAccountingIndStatus %>">
 <input type="hidden" name="noCommissionIndStatus" value="<%= noCommissionIndStatus %>">
 <input type="hidden" name="zeroLoadIndStatus" value="">
 <input type="hidden" name="noCheckEFTStatus" value="<%= noCheckEFTStatus %>">
 <input type="hidden" name="employerContribution" value="<%= employerContribution %>">
 <input type="hidden" name="employeeContribution" value="<%= employeeContribution %>">
 <input type="hidden" name="employerEmployeeIndStatus" value="<%= employerEmployeeIndStatus %>">
 <input type="hidden" name="processIndStatus" value="<%= processIndStatus %>">
 <input type="hidden" name="commissionStatus" value="<%= commissionStatus %>">
 <input type="hidden" name="trxIsRescheduledInd" value="<%= trxIsRescheduledInd %>">
 <input type="hidden" name="groupPercent" value="<%= groupPercent %>">
 <input type="hidden" name="withdrawalType" value="<%= withdrawalType %>">
 <input type="hidden" name="clientRoleFK" value="<%= clientRoleFK %>">
 <input type="hidden" name="noScrolling" value="">
 <input type="hidden" name="notificationAmount" value="<%= notificationAmount %>">
 <input type="hidden" name="notificationAmountReceived" value="<%= notificationAmountReceived %>">
 <input type="hidden" name="reinsuranceStatus" value="<%= reinsuranceStatus %>">
 <input type="hidden" name="originatingTrxPK" value="<%= originatingTrxPK %>">
 <input type="hidden" name="companyStructureId" value="<%= companyStructureId %>">
 <input type="hidden" name="interestOverride" value="<%= interestOverride %>">
 <input type="hidden" name="newPolicyNumber" value="<%= newPolicyNumber %>">
 <input type="hidden" name="reapplyEDITTrxFK" value="<%= reapplyEDITTrxFK %>">
 <input type="hidden" name="premiumDueCreateInd" value="<%= premiumDueCreateInd %>">
 <input type="hidden" name="recordPRASEEvents" value="false">
 <input type="hidden" name="undoTrxApproved" value="<%= undoTrxApproved %>">
 <input type="hidden" name="trxSource" value="<%= trxSource %>">
 <input type="hidden" name="amount" value="<%= amount %>">
 
</form>
</body>
</html>
