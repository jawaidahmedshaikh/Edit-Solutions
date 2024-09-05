<%@ page  errorPage="/common/jsp/error.jsp" autoFlush="true" %>
<%@ page import="java.util.*,
                 edit.common.*,
                 edit.common.vo.*,
                 edit.portal.widgettoolkit.TableModel,
                 fission.beans.*,
                 edit.portal.exceptions.*,
                 edit.common.exceptions.VOEditException,
                 java.text.DecimalFormat,
                 event.*,
                 contract.Segment,
                 fission.utility.*,
                 engine.Company,
                 edit.portal.widget.*" %>
<%@ page import="edit.services.db.hibernate.SessionHelper"%>

<jsp:useBean id="contractMainSessionBean"
    class="fission.beans.SessionBean" scope="session"/>

<!-- contractSuspense.jsp //-->

<%
    String responseMessage = Util.initString((String) request.getAttribute("responseMessage"), "");

    String suspenseMessage = Util.initString((String) request.getAttribute("suspenseMessage"), "");

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

    String[] companies = (String[]) request.getAttribute("companies");

    CodeTableWrapper codeTableWrapper = CodeTableWrapper.getSingleton();
    String companyStructureId = contractMainSessionBean.getPageBean("formBean").getValue("companyStructureId");

    CodeTableVO[] directionCTVO = null;
    CodeTableVO[] disbursementCTVO = null;
    CodeTableVO[] premiumTypeCTVO = null;
    CodeTableVO[] reasonCodeCTVO = null;

    String selectedSuspensePK = Util.initString((String) request.getAttribute("selectedSuspensePK"), "0");
    Suspense suspense = (Suspense) request.getAttribute("suspense");

    if (!selectedSuspensePK.equals("0"))
    {
        suspense = Suspense.findByPK(new Long(selectedSuspensePK));
    }

    String segmentFK      = "";
    String effectiveDate  = "";
    String processDate    = "";
    String amount         = "";
    String pendingAmount  = "";
    String status         = "";
    String contractNumber = "";
    String userNumber     = "";
    String direction      = "";
    String memoCode       = "";
    String premiumType    = "";
    String suspenseType   = "";
    String disbursementSource = "";
    String clientId       = "";
    String operator       = "";
    String maintDate      = "";
    String originalContractNumber = "";
    String originalAmount = "";
    String originalMemoCode = "";
    String contractPlacedFrom = "";
    String accountingPending = "";
    String clientInformationInd = "unchecked";
    String trxInformationInd = "unchecked";
    String preTEFRAGain = "";
    String preTEFRAAmount = "";
    String postTEFRAGain = "";
    String postTEFRAAmount = "";
    String reasonCode = "";
    String companyName = "";
	String clientDetailPK = "";
	String suspenseOriginalAmount = "";
	String suspenseCostBasis = "";
	
    long companyFK = 0;
    EDITBigDecimal totalSuspenseAmount = new EDITBigDecimal();
    EDITBigDecimal premiumSuspense = new EDITBigDecimal();
    int numberOfSuspenseItems = 0;

    userNumber = Util.initString((String) contractMainSessionBean.getValue("contractId"), "");
    
    if(companyStructureId != null && companyStructureId != "")
    {
    	companyName = Company.findByProductStructurePK(Long.parseLong(companyStructureId)).getCompanyName();
    }

    if (suspense != null)
    {
        if (suspense.getOriginalContractNumber() != null)
        {
            contractNumber = suspense.getOriginalContractNumber();
            Segment segment = Segment.findByContractNumber(contractNumber);
            if (segment != null)
            {
                companyStructureId = segment.getProductStructure().getProductStructurePK().toString();
                segmentFK = segment.getSegmentPK().toString();
            }
        }

        if (suspense.getEffectiveDate() != null)
        {
            effectiveDate = DateTimeUtil.formatEDITDateAsMMDDYYYY(suspense.getEffectiveDate());
        }

        if (suspense.getProcessDate() != null)
        {
            processDate = DateTimeUtil.formatEDITDateAsMMDDYYYY(suspense.getProcessDate());
        }


        if (suspense.getCompanyFK() != null)
        {
            companyName = Company.findByPK(suspense.getCompanyFK()).getCompanyName();
        }

        if ((companyName == null || companyName == "") && (companyStructureId != null && companyStructureId != ""))
        {
        	companyName = Company.findByProductStructurePK(Long.parseLong(companyStructureId)).getCompanyName();
        }
        
        amount = suspense.getSuspenseAmount().toString();
        pendingAmount = suspense.getPendingSuspenseAmount().toString();
		suspenseOriginalAmount = (suspense.getOriginalAmount() == null ? "" : suspense.getOriginalAmount().toString()); 
		suspenseCostBasis = (suspense.getCostBasis() == null ? "" : suspense.getOriginalAmount().toString()); 
        userNumber = suspense.getUserDefNumber();
        direction = Util.initString(suspense.getDirectionCT(), "");
        memoCode = Util.initString(suspense.getMemoCode(), "");
        premiumType = Util.initString(suspense.getPremiumTypeCT(), "");
        suspenseType = Util.initString(suspense.getSuspenseType(), "");
        operator = Util.initString(suspense.getOperator(), "");
        maintDate = (String) Util.initObject(suspense, "maintDateTime", new EDITDateTime()).toString();
        originalContractNumber = suspense.getOriginalContractNumber();
        originalAmount = suspense.getOriginalAmount().toString();
        originalMemoCode = Util.initString(suspense.getOriginalMemoCode(), "");
        contractPlacedFrom = suspense.getContractPlacedFrom();
        accountingPending = suspense.getAccountingPendingInd();
        preTEFRAGain = suspense.getPreTEFRAGain().toString();
        preTEFRAAmount = suspense.getPreTEFRAAmount().toString();
        postTEFRAGain = suspense.getPostTEFRAGain().toString();
        postTEFRAAmount = suspense.getPostTEFRAAmount().toString();
        reasonCode = Util.initString(suspense.getReasonCodeCT(), "");

        if (suspense.getClientDetailFK() != null ||
            suspense.getClientAddressFK() != null ||
            suspense.getPreferenceFK() != null)
        {
            clientInformationInd = "checked";
        }

        if (!suspense.getInSuspenses().isEmpty())
        {
            Set inSuspenses = suspense.getInSuspenses();
            Iterator it = inSuspenses.iterator();
            while (it.hasNext())
            {
                InSuspense inSuspense = (InSuspense) it.next();
                EDITTrxHistory editTrxHistory = inSuspense.getEDITTrxHistory();
                EDITTrx editTrx = editTrxHistory.getEDITTrx();
                FinancialHistory financialHistory = editTrxHistory.getFinancialHistory();
                status = editTrx.getStatus();
                if (financialHistory != null)
                {
                    disbursementSource = Util.initString(financialHistory.getDisbursementSourceCT(), "");
                }

                trxInformationInd = "checked";
            }
        }
    }

    if (companyStructureId != null && Util.isANumber(companyStructureId))
    {
        directionCTVO    = codeTableWrapper.getCodeTableEntries("DIRECTION", Long.parseLong(companyStructureId));
        disbursementCTVO = codeTableWrapper.getCodeTableEntries("DISBURSESOURCE", Long.parseLong(companyStructureId));
        premiumTypeCTVO  = codeTableWrapper.getCodeTableEntries("PREMIUMTYPE", Long.parseLong(companyStructureId));
        reasonCodeCTVO = codeTableWrapper.getCodeTableEntries("SUSPENSEREASONCODE", Long.parseLong(companyStructureId));
    }
    else
    {
        directionCTVO = codeTableWrapper.getCodeTableEntries("DIRECTION");
        disbursementCTVO = codeTableWrapper.getCodeTableEntries("DISBURSESOURCE");
        premiumTypeCTVO = codeTableWrapper.getCodeTableEntries("PREMIUMTYPE");
        reasonCodeCTVO = codeTableWrapper.getCodeTableEntries("SUSPENSEREASONCODE");
    }

%>

<html>

<script src="/PORTAL/common/javascript/commonJavascriptFunctions.js"></script>
<script src="/PORTAL/common/javascript/widgetFunctions.js"></script>
<script src="/PORTAL/common/javascript/scrollTable.js"></script>
<script src="/PORTAL/common/javascript/formatDateFunctions.js" type="text/javascript"></script>
<script src="/PORTAL/common/javascript/calendarSelector.js" type="text/javascript"></script>


<script language="Javascript1.2">

    var suspenseMessage = "<%= suspenseMessage %>";
    var responseMessage = "<%= responseMessage %>";

    var editingExceptionExists = "<%= editingExceptionExists %>";
    var voEditExceptionExists = "<%= voEditExceptionExists %>";
    var pendingAmount = "<%= pendingAmount %>";
    var accountingPending = "<%= accountingPending %>";
    var contractPlacedFrom = "<%= contractPlacedFrom %>";
    var userNumber = "<%= userNumber %>";
    var direction = "<%= direction %>";

	var dialog = null;

	var f = null;

	function init()
    {
		f = document.suspenseForm;

        checkForResponseMessage();

        if (suspenseMessage != "" &&
            suspenseMessage != null) {

            alert(suspenseMessage);
        }


        if (direction == "Remove")
        {
            f.btnVoid.disabled = true;
            f.btnRefund.disabled = true;
            f.btnTransfer.disabled = true;
        }
        else
        {
            f.btnVoid.disabled = false;
            f.btnRefund.disabled = false;
            f.btnTransfer.disabled = false;
        }

        formatCurrency();

<%--        // Initialize scroll tables--%>
<%--        initScrollTable(document.getElementById("FilterSuspenseTableModelScrollTable"));--%>
	}

    function filterSuspense()
    {
        var height  = .40 * screen.height;
        var width = screen.width * .50;

        openDialog("filterSuspenseDialog", "top=0,left=0,resizable=no,scrollbars=yes", width, height);

        sendTransactionAction("ContractDetailTran", "showFilterSuspenseDialog", "filterSuspenseDialog");
    }



	function onTableRowSingleClick(tableId)
    {
		sendTransactionAction("ContractDetailTran", "showSuspenseDetail", "_self");
	}

	function addSuspenseEntry()
    {
		sendTransactionAction("ContractDetailTran", "addOrCancelSuspenseEntry", "_self");
	}

	function saveSuspenseEntry()
    {
        if (f.userNumber.value == "")
        {
            alert("User Def Number Must Be Entered For Save");
        }
        else if (selectElementIsEmpty(f.company))
        {
            alert("Company Must Be Selected");
        }
        else if (f.effectiveDate.value == "")
        {
            alert("Effective Date Must Be Entered For Save");
        }
        else
        {
            sendTransactionAction("ContractDetailTran", "saveSuspenseEntry", "_self");
        }
	}

	function cancelSuspenseEntry()
    {
		sendTransactionAction("ContractDetailTran", "addOrCancelSuspenseEntry", "_self");
	}

	function voidSuspenseEntry()
    {
		sendTransactionAction("ContractDetailTran", "voidSuspenseEntry", "_self");
	}

    function refundSuspenseEntry()
    {
        if (selectElementIsEmpty(f.company))
        {
            alert("Company Must Be Selected");
        }
        else
        {
            var height  = .90 * screen.height;
            var width   = screen.width;

            openDialog("refundSuspenseDialog", "top=0,left=0,resizable=no,scrollbars=yes", width, height);

            sendTransactionAction("ContractDetailTran", "showRefundSuspenseDialog", "refundSuspenseDialog");
        }
    }

    function transferSuspenseEntry()
    {
        var height  = .60 * screen.height;
        var width = screen.width * .60;

        openDialog("suspenseTransferDialog", "top=0,left=0,resizable=no,scrollbars=yes", width, height);

        sendTransactionAction("ContractDetailTran", "showSuspenseTransferDialog", "suspenseTransferDialog");
    }

	function trxInformation()
    {
        if (userNumber == "")
        {
            alert("Suspense Entry Must Be Selected To View Transaction Information");
        }
        else
        {
            var height  = .40 * screen.height;
            var width   = .60 * screen.width;

            openDialog("transactionInformation", "top=0,left=0,resizable=no", width, height);

            sendTransactionAction("ContractDetailTran", "showSuspenseTransactionInfo", "transactionInformation");
        }
	}

	function clientInformation()
    {
        if (userNumber == "")
        {
            alert("Suspense Entry Must Be Selected To Enter Client Information");
        }
        else if (selectElementIsEmpty(f.company))
        {
            alert("Company Must Be Selected");
        }
        else
        {
            var height  = .90 * screen.height;
            var width   = screen.width;

            openDialog("refundSuspenseDialog", "top=0,left=0,scrollbars=yes,resizable=no", width, height);

            sendTransactionAction("ContractDetailTran", "showSuspenseClientInfo", "refundSuspenseDialog");
        }
	}

    function checkForEditingException()
    {
        if (editingExceptionExists == "true")
        {
            var width = .75 * screen.width;
            var height = screen.height/3;

            openDialog("exceptionDialog", "resizable=no", width, height);

            sendTransactionAction("ContractDetailTran", "showEditingExceptionDialog", "exceptionDialog");
        }
    }

    function checkForVOEditException()
    {
        if (voEditExceptionExists == "true")
        {
            var width = .75 * screen.width;
            var height = screen.height/3;

            openDialog("voEditExceptionDialog", "resizable=no", width, height);

            sendTransactionAction("ContractDetailTran", "showVOEditExceptionDialog", "voEditExceptionDialog");
        }
    }

    function continueTransactionAction(transaction, action)
    {
        f.ignoreEditWarnings.value = "true";
        sendTransactionAction(transaction, action, "_self");
    }

<%--    function showTEFRAInformation()--%>
<%--    {--%>
<%--        if (userNumber == "")--%>
<%--        {--%>
<%--            alert("Suspense Entry Must Be Selected To Enter TEFRA Information");--%>
<%--        }--%>
<%--        else--%>
<%--        {--%>
<%--            var height  = .30 * screen.height;--%>
<%--            var width   = .40 * screen.width;--%>
<%----%>
<%--            openDialog("tefraInformation", "top=0,left=0,scrollbars=yes,resizable=no", width, height);--%>
<%----%>
<%--            sendTransactionAction("ContractDetailTran", "showTEFRAInformation", "tefraInformation");--%>
<%--        }--%>
<%--	}--%>

    function closeWindow()
    {
        sendTransactionAction("ContractDetailTran", "closeSuspenseDialog", "suspenseDialog");
		window.close();
    }

</script>

<head>
<title>EDITSOLUTIONS - Suspense</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta http-equiv="Cache-Control" content="no-store">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Expires" content="0">

<link href="/PORTAL/common/css/EDITSolutions.css" rel="stylesheet" type="text/css">
</head>

<body class="mainTheme" onLoad="init(); checkForEditingException(); checkForVOEditException()">
<form name="suspenseForm" method="post" action="">
  <table width="100%" height="50%" border="0" cellspacing="5" cellpadding="0">

    <tr>
      <td align="left" nowrap>User Number:&nbsp;
        <input type="text" name="userNumber" size="15" maxlength="15" value="<%= userNumber %>">
      </td>
      <td align="left" nowrap>Company:&nbsp;
          <select name="company">
            <%
              out.println("<option>Please Select</option>");

                if (companies != null)
                {
                    for(int i = 0; i < companies.length; i++)
                    {
                        String company = companies[i];

                        if (companyName.equals(company)) {

                           out.println("<option selected name=\"id\" value=\"" + company + "\">" + company + "</option>");
                        }
                        else  {

                           out.println("<option name=\"id\" value=\"" + company + "\">" + company + "</option>");
                        }
                    }
                }

            %>
          </select>
      </td>
      <td align="left" nowrap>Suspense Type:&nbsp;
        <input disabled type="text" name="suspenseType" size="10" maxlength="10" value="<%= suspenseType %>">
      </td>
    </tr>
    <tr>
      <td align="left" nowrap>Effective Date:&nbsp;
            <input type="text" name="effectiveDate" value="<%= effectiveDate %>" size='10' maxlength="10" onKeyUp="DateFormat(this,this.value,event,false)" onBlur="DateFormat(this,this.value,event,true)">
            <a href="javascript:show_calendar('f.effectiveDate', f.effectiveDate.value);"><img src="/PORTAL/common/images/calendarIcon.gif" width="16" height="16" border="0" alt="Select a date from the calendar"></a>
        </td>
      <td align="left" nowrap>Process Date:&nbsp;
            <input disabled type="text" name="processDate" value="<%= processDate %>" size='10' maxlength="10">
        </td>
    </tr>
    <tr>
      <td align="left" nowrap>Direction:&nbsp;
        <select name="direction">
          <%
              out.println("<option>Please Select</option>");

              for(int i = 0; i < directionCTVO.length; i++) {

                  String codeTablePK = directionCTVO[i].getCodeTablePK() + "";
                  String codeDesc    = directionCTVO[i].getCodeDesc();
                  String code        = directionCTVO[i].getCode();

                 if (direction.equals(code)) {

                     out.println("<option selected name=\"id\" value=\"" + codeTablePK + "\">" + codeDesc + "</option>");
                 }
                 else  {

                     out.println("<option name=\"id\" value=\"" + codeTablePK + "\">" + codeDesc + "</option>");
                 }
              }

         %>
        </select>
      </td>
      <td align="left" nowrap colspan="2">Status:&nbsp;
        <input type="text" name="status" disabled size="1" maxlength="1" value="<%= status %>">
      </td>
    </tr>
    <tr>
      <td align="left" nowrap>Amount:&nbsp;
        <input type="text" name="amount" size="15" maxlength="15" value="<%= amount %>" CURRENCY>
      </td>
      <td align="left" nowrap>Pending Amount:&nbsp;
        <input type="text" name="pendingAmount" disabled size="15" maxlength="15" value="<%= pendingAmount %>" CURRENCY>
      </td>
      <td align="left" nowrap>Memo Code:&nbsp;
        <input type="text" name="memoCode" size="7" maxlength="7" value="<%= memoCode %>">
      </td>
    </tr>
    <tr>
      <td align="left" nowrap>Original Amount:&nbsp;
        <input type="text" name="suspenseOriginalAmount" size="15" maxlength="15" value="<%= suspenseOriginalAmount %>" CURRENCY>
      </td>
      <td align="left" nowrap>Cost Basis:&nbsp;
        <input type="text" name="suspenseCostBasis" size="15" maxlength="15" value="<%= suspenseCostBasis %>" CURRENCY>
      </td>
    </tr>
    <tr>
      <td align="left" nowrap>Disbursement Source: &nbsp;
        <select name="disbursementSource">
          <%
              out.println("<option>Please Select</option>");

              for(int i = 0; i < disbursementCTVO.length; i++) {

                  String codeTablePK = disbursementCTVO[i].getCodeTablePK() + "";
                  String codeDesc    = disbursementCTVO[i].getCodeDesc();
                  String code        = disbursementCTVO[i].getCode();

                 if (disbursementSource.equals(code)) {

                     out.println("<option selected name=\"id\" value=\"" + codeTablePK + "\">" + codeDesc + "</option>");
                 }
                 else  {

                     out.println("<option name=\"id\" value=\"" + codeTablePK + "\">" + codeDesc + "</option>");
                 }
              }

         %>
        </select>
      </td>
      <td align="left" nowrap>Premium Type:&nbsp;
        <select name="premiumType">
          <%
              out.println("<option>Please Select</option>");

              for(int i = 0; i < premiumTypeCTVO.length; i++) {

                  String codeTablePK = premiumTypeCTVO[i].getCodeTablePK() + "";
                  String codeDesc    = premiumTypeCTVO[i].getCodeDesc();
                  String code        = premiumTypeCTVO[i].getCode();

                 if (premiumType.equals(code)) {

                     out.println("<option selected name=\"id\" value=\"" + codeTablePK + "\">" + codeDesc + "</option>");
                 }
                 else  {

                     out.println("<option name=\"id\" value=\"" + codeTablePK + "\">" + codeDesc + "</option>");
                 }
              }

         %>
        </select>
      </td>
      <td align="left" nowrap>ClientId:&nbsp;
        <input type="text" name="clientId" disabled size="15" maxlength="15" value="<%= clientId %>">
      </td>
    </tr>
    <tr>
      <td align="left" nowrap>Pre TEFRA Gain:&nbsp;
        <input type="text" name="preTEFRAGain" size="15" value="<%= preTEFRAGain %>" CURRENCY>
      </td>
      <td align="left" nowrap colspan="2">Post TEFRA Gain:&nbsp;
        <input type="text" name="postTEFRAGain" size="15" value="<%= postTEFRAGain %>" CURRENCY>
      </td>
    </tr>
    <tr>
      <td align="left" nowrap>Pre TEFRA Amount:&nbsp;
        <input type="text" name="preTEFRAAmount" maxlength="11" size="15" value="<%= preTEFRAAmount %>" CURRENCY>
      </td>
      <td align="left" nowrap colspan="2">Post TEFRA Amount:&nbsp;
        <input type="text" name="postTEFRAAmount" maxlength="11" size="15" value="<%= postTEFRAAmount %>" CURRENCY>
      </td>
    </tr>
    <tr>
      <td align="left" nowrap>
        <input type="checkbox" name="trxInformationInd" <%= trxInformationInd %> disabled><a href ="javascript:trxInformation()">Transaction Information</a>
      </td>
      <td align="left" nowrap colspan="2">
        <input type="checkbox" name="clientInformationInd" <%= clientInformationInd %> disabled><a href ="javascript:clientInformation()">Client Information</a>&nbsp;&nbsp;&nbsp;
      </td>
    </tr>
    <tr>
      <td colspan="3">
        &nbsp;
      </td>
    </tr>
    <tr>
      <td align="left" nowrap>Operator: &nbsp;
        <input type="text" name="operator" disabled size="8" maxlength="8" value="<%= operator %>">
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        Date/Time: &nbsp;
        <input type="text" name="maintDate" disabled size="23" maxlength="23" value="<%= maintDate %>">
      </td>
    </tr>
  </table>
  <br>
  <table width="100%" height="1%" border="0" cellspacing="0" cellpadding="0">
    <tr height="100%">
	  <td nowrap align="left">
	  	<input type="button" name="btnAdd" value="   Add   " onClick="addSuspenseEntry()">
		<input type="button" name="btnSave" value="   Save  " onClick="saveSuspenseEntry()">
	  	<input type="button" name="btnCancel" value="  Cancel " onClick="cancelSuspenseEntry()">
		<input type="button" name="btnVoid" value="  Void   " onClick="voidSuspenseEntry()">
        <input type="button" name="btnRefund" value=" Refund  " onClick="refundSuspenseEntry()">
        <input type="button" name="btnTransfer" value=" Transfer  " onClick="transferSuspenseEntry()">
	  </td>
      <td nowrap align="right">Reason Code:</td>
      <td nowrap align="left">
        <select name="reasonCode">
          <%
              out.println("<option>Please Select</option>");

              for(int i = 0; i < reasonCodeCTVO.length; i++) {

                  String codeTablePK = reasonCodeCTVO[i].getCodeTablePK() + "";
                  String codeDesc    = reasonCodeCTVO[i].getCodeDesc();
                  String code        = reasonCodeCTVO[i].getCode();

                 if (reasonCode.equals(code)) {

                     out.println("<option selected name=\"id\" value=\"" + codeTablePK + "\">" + codeDesc + "</option>");
                 }
                 else  {

                     out.println("<option name=\"id\" value=\"" + codeTablePK + "\">" + codeDesc + "</option>");
                 }
              }

         %>
        </select>
      </td>
      <td nowrap align="right">
        <input type="button" value="   Filter    " onClick="filterSuspense()">
      </td>
	</tr>
  </table>
  <br>

<%-- ****************************** BEGIN Summary Area ****************************** --%>

<jsp:include page="/common/jsp/widget/tableWidget.jsp" flush="true">
    <jsp:param name="tableId" value="FilterSuspenseTableModel"/>
    <jsp:param name="tableHeight" value="60"/>
    <jsp:param name="multipleRowSelect" value="false"/>
    <jsp:param name="singleOrDoubleClick" value="single"/>
</jsp:include>

<%-- ****************************** END Summary Area ****************************** --%>
          <%
              Map tableModelMap = (Map) request.getAttribute("tableModels");
              FilterSuspenseTableModel  filterSuspenseTableModel = (FilterSuspenseTableModel)tableModelMap.get("FilterSuspenseTableModel");
              numberOfSuspenseItems = filterSuspenseTableModel.getNumberOfSuspenseItems();
              totalSuspenseAmount = filterSuspenseTableModel.getTotalSuspenseAmount();
              premiumSuspense = filterSuspenseTableModel.getPremiumSuspense();
         %>


  <table id="table3" width="100%" height="1%" border="0" cellspacing="6" cellpadding="0">
    <tr>
      <td align="left" nowrap>Total $:&nbsp;
        <input disabled type="text" name="totalSuspenseAmount" size="20" maxlength="20" value="<%= totalSuspenseAmount %>" CURRENCY>
      </td>
      <td align="left" nowrap>Total Items:&nbsp;
        <input disabled type="text" name="numberOfSuspenseItems" size="5" maxlength="5" value="<%= numberOfSuspenseItems %>">
      </td>
      <td align="left" nowrap>Premium Suspense:&nbsp;
        <input disabled type="text" name="premiumSuspense" size="20" maxlength="20" value="<%= premiumSuspense %>" CURRENCY>
      </td>
      <td align="right" nowrap colspan="2">
        <input type="button" name="close" value="Close" onClick ="closeWindow()">
      </td>
    </tr>
  </table>

<!-- ****** HIDDEN FIELDS ***** //-->
 <input type="hidden" name="transaction"  value="">
 <input type="hidden" name="action"       value="">
 <input type="hidden" name="originalContractNumber" value="<%= originalContractNumber %>">
 <input type="hidden" name="originalAmount" value="<%= originalAmount %>">
 <input type="hidden" name="originalMemoCode" value="<%= originalMemoCode %>">

 <input type="hidden" name="key"   value="">
 <input type="hidden" name="suspensePK" value="<%= selectedSuspensePK %>">
 <input type="hidden" name="userNumber" value="<%= contractNumber %>">
 <input type="hidden" name="segmentFK" value="<%= segmentFK %>">
 <input type="hidden" name="ignoreEditWarnings" value="">
 <input type="hidden" name="suspenseType" value="<%= suspenseType %>">
 <input type="hidden" name="pendingAmount" value="<%= pendingAmount %>">
 <input type="hidden" name="selectedSuspensePK" value="<%= selectedSuspensePK %>">

</form>
</body>
</html>
