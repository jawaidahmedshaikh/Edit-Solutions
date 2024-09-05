<!-- ****** JAVA CODE ***** //-->

<%@ page  errorPage="/common/jsp/error.jsp" autoFlush="true" %>
<%@ page import="java.util.*,
                 edit.common.*,
                 event.CashBatchContract,
                 event.Suspense,
                 engine.Company,
                 fission.utility.*" %>
<!--
 * User: unknown
 * Date: Jan 1, 2000
 * Time: unknown
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 -->
<%
    String responseMessage = (String) request.getAttribute("responseMessage");
    String action = Util.initString((String) request.getAttribute("action"), "");

    CashBatchContract activeCashBatchContract = (CashBatchContract) request.getAttribute("activeCashBatchContract");

    long activeCashBatchContractPK = 0L;
    String batchId = "";
    String companyName = "";
    String creationDate = "";
    EDITBigDecimal amount = new EDITBigDecimal();
    EDITBigDecimal remainingAmount = new EDITBigDecimal();
    String operator = "";
    String releaseInd = "";
    String groupNumber = "";
    String dueDate = "";

    if (activeCashBatchContract != null)
    {
        activeCashBatchContractPK = Util.initLong(activeCashBatchContract, "cashBatchContractPK", 0L);
        batchId = (String) Util.initObject(activeCashBatchContract, "batchID", "");
        Long companyFK = activeCashBatchContract.getCompanyFK();

        if (companyFK != null)
        {
            Company company = Company.findByPK(companyFK);
            companyName = company.getCompanyName();
        }

        creationDate = DateTimeUtil.formatEDITDateAsMMDDYYYY(activeCashBatchContract.getCreationDate());
        amount = (EDITBigDecimal) Util.initObject(activeCashBatchContract, "amount", new EDITBigDecimal());
        operator = (String) Util.initObject(activeCashBatchContract, "creationOperator", "");
        releaseInd = (String) Util.initObject(activeCashBatchContract, "releaseIndicator", "");
        groupNumber = Util.initString(activeCashBatchContract.getGroupNumber(), "");
        dueDate = DateTimeUtil.formatEDITDateAsMMDDYYYY(activeCashBatchContract.getDueDate());

        if (dueDate == null)
        {
            dueDate = "";
        }

        if (creationDate == null)
        {
            creationDate = "";
        }

        remainingAmount = activeCashBatchContract.calculateRemainingBatchAmount();
//        Suspense[] suspenses = Suspense.findAllByCashBatchContract(activeCashBatchContract);
//        if (suspenses != null)
//        {
//            for (int i = 0; i < suspenses.length; i++)
//            {
//                remainingAmount = remainingAmount.addEditBigDecimal(suspenses[i].getSuspenseAmount());
//            }
//        }
    }

    // Getting CashBatch Filter values
    String status = (String) request.getAttribute("status");
    String month = (String) request.getAttribute("month");
    String day = (String) request.getAttribute("day");
    String year = (String) request.getAttribute("year");
    String filterAmount = (String) request.getAttribute("filterAmount");
    String filterOperator = (String) request.getAttribute("filterOperator");
%>

<html>

<script src="/PORTAL/common/javascript/commonJavascriptFunctions.js"></script>

<script src="/PORTAL/common/javascript/widgetFunctions.js"></script>
<script src="/PORTAL/common/javascript/scrollTable.js"></script>


<script language="JavaScript1.2">

<!-- ******* JAVASCRIPT ****** -->

    var f = null;

    var responseMessage = "<%= responseMessage %>";
    var batchId = "<%= batchId %>";
    var releaseInd = "<%= releaseInd %>";
    var pageAction = "<%= action %>";

    function init()
    {
        f = document.cashBatchSummaryForm;

        checkForResponseMessage();

        formatCurrency();

        if (pageAction == "add")
        {
            showCashBatchEntry();
        }
        else if (pageAction == "create")
        {
            showCashBatchContractSummary();
        }

        // Initialize scroll tables
        initScrollTable(document.getElementById("CashBatchSummaryTableModelScrollTable"));
    }

    function onTableRowSingleClick(tableId)
    {
        sendTransactionAction("EventAdminTran", "showSelectedCashBatch", "_self");
    }

    function showCashBatchEntry()
    {
        var width = .35 * screen.width;
        var height = .40 * screen.height;

        openDialog("cashBatchEntryDialog", "top=0,left=0,resizable=no", width, height);

        sendTransactionAction("EventAdminTran", "showCashBatchEntry", "cashBatchEntryDialog");
    }

    function showCashBatchContractSummary()
    {
        var width = .90 * screen.width;
        var height = .80 * screen.height;

        openDialog("cashBatchContractSummary","top=0,left=0,resizable=yes",width,height);

        sendTransactionAction("EventAdminTran", "showCashBatchContractSummary", "cashBatchContractSummary");
    }

    function addCashBatchEntry()
    {
        sendTransactionAction("EventAdminTran", "addCashBatchEntry", "_self");
    }

    function voidCashBatch()
    {
        if (valueIsEmpty(batchId))
        {
            alert("Please Select Cash Batch For Void");
        }
        else
        {
            sendTransactionAction("EventAdminTran", "voidSelectedCashBatch", "_self");
        }
    }

    function editCashBatch()
    {
        if (releaseInd != "P")
        {
            alert("Cannot Edit - Cash Batch Not Pending");
        }
        else
        {
            var width = .90 * screen.width;
            var height = .80 * screen.height;

            openDialog("cashBatchContractSummary","top=0,left=0,resizable=no",width,height);

            sendTransactionAction("EventAdminTran", "showCashBatchContractSummary", "cashBatchContractSummary");
        }
    }

    function viewCashBatch()
    {
        if (f.activeCashBatchContractPK.value == "0")
        {
            alert("Please Select Cash Batch To View");
        }
        else
        {
            var width = .90 * screen.width;
            var height = .80 * screen.height;

            openDialog("cashBatchContractSummary","top=0,left=0,resizable=no",width,height);

            sendTransactionAction("EventAdminTran", "viewCashBatchContractSummary", "cashBatchContractSummary");
        }
    }

    function showCashBatchFilter()
    {
        var width = .35 * screen.width;
        var height = .30 * screen.height;

        openDialog("cashBatchFilterDialog", "top=0,left=0,resizable=no", width, height);

        sendTransactionAction("EventAdminTran", "showCashBatchFilter", "cashBatchFilterDialog");
    }

</script>
<head>
<title>Cash Batch Summary</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta http-equiv="Cache-Control" content="no-store">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Expires" content="0">

<link href="/PORTAL/common/css/EDITSolutions.css" rel="stylesheet" type="text/css">
</head>

<!-- ****** HTML CODE ***** //-->

<body class="mainTheme" onLoad="init()">
<form name="cashBatchSummaryForm" method="post" action="/PORTAL/servlet/RequestManager">

<%-- ********** BEGIN Form Data ********** --%>

<table class="formData" width="100%" height="10%" border="0" cellspacing="0" cellpadding="0">
<%-- BEGIN Form Content --%>
  <tr>
    <td align="right" nowrap>Batch ID:&nbsp;</td>
    <td align="left" nowrap>
      <input disabled type="text" name="detailBatchId" size="20" maxlength="20" value="<%= batchId %>">
    </td>
    <td align="right" nowrap>Company:&nbsp;</td>
    <td align="left" nowrap>
      <input disabled type="text" name="companyName" size="35" maxlength="35" value="<%= companyName %>">
    </td>
  </tr>
  <tr>
    <td align="right" nowrap>Original Batch Amount:&nbsp;</td>
    <td align="left" nowrap>
      <input disabled type="text" name="amount" size="15" maxlength="15" value="<%= amount.toString() %>" CURRENCY>
    </td>
    <td align="right" nowrap>Remaining Batch Amount:&nbsp;</td>
    <td align="left" nowrap>
      <input disabled type="text" name="remainingAmount" size="15" maxlength="15" value="<%= remainingAmount.toString() %>" CURRENCY>
    </td>
  </tr>
  <tr>
    <td align="right" nowrap>Creation Date:&nbsp;</td>
    <td align="left" nowrap>
      <input disabled type="text" name="creationDate" size="20" maxlength="20" value="<%= creationDate %>">
    </td>
    <td align="right" nowrap>Creation Operator:&nbsp;</td>
    <td align="left" nowrap>
      <input disabled type="text" name="creationOperator" size="15" maxlength="15" value="<%= operator %>">
    </td>
  </tr>
  <tr>
    <td align="right" nowrap>Due Date:&nbsp;</td>
    <td align="left" nowrap>
      <input disabled type="text" name="dueDate" size="20" maxlength="20" value="<%= dueDate %>">
    </td>
    <td align="right" nowrap>Group Number:&nbsp;</td>
    <td align="left" nowrap>
      <input disabled type="text" name="groupNumber" size="15" maxlength="15" value="<%= groupNumber %>">
    </td>
  </tr>

<%-- END Form Content --%>

  <tr class="filler">
    <td>
      &nbsp; <!-- Filler Row -->
    </td>
  </tr>
</table>

<%-- ********** END Form Data ********** --%>

<%-- Buttons --%>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr valign="top">
    <td nowrap align="left">
      <input type="button" id="btnAdd" name="add" value="Add" onClick="addCashBatchEntry()">
      <input type="button" id="btnChange" name="change" value="Change" onClick="editCashBatch()">
      <input type="button" id="btnVoid" name="void" value="Void" onClick="voidCashBatch()">
      <input type="button" id="btnView" name="view" value="View" onClick="viewCashBatch()">
    </td>
    <td nowrap align="right">
      <input type="button" id="btnFilter" name="filter" value="Filter" onClick="showCashBatchFilter()">
    </td>
  </tr>
</table>

<%-- ********** BEGIN Summary Area ********** --%>

<jsp:include page="/common/jsp/widget/tableWidget.jsp" flush="true">
  <jsp:param name="tableId" value="CashBatchSummaryTableModel"/>
  <jsp:param name="tableHeight" value="60"/>
  <jsp:param name="multipleRowSelect" value="false"/>
  <jsp:param name="singleOrDoubleClick" value="single"/>
</jsp:include>

<%-- ********** END Summary Area ********** --%>

<%-- ********** BEGIN Batch Total Area ********** --%>

<%
    String[] batchTotals = (String[]) request.getAttribute("batchTotals");
    String totalAmount = "";
    String totalNumberOfItems = "";

    if (batchTotals != null)
    {
        totalAmount = batchTotals[0];
        totalNumberOfItems = batchTotals[1];
    }
%>

<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td align="left" nowrap>Total $:&nbsp;
      <input disabled type="text" name="totalAmount" size="15" maxlength="15" value="<%= totalAmount %>" CURRENCY>
      &nbsp;&nbsp;&nbsp;&nbsp;Total Items:&nbsp;
      <input disabled type="text" name="totalItems" size="5" maxlength="5" value="<%= totalNumberOfItems %>">
    </td>
  </tr>
</table>

<%-- ********** END Batch Total Area ********** --%>

<%-- ********** BEGIN Hidden Variables ********** --%>

  	<input type="hidden" name="transaction" value="">
    <input type="hidden" name="action"      value="">
    <input type="hidden" name="activeCashBatchContractPK" value="<%= activeCashBatchContractPK %>">
    <input type="hidden" name="batchId" value="<%= batchId %>">
    <input type="hidden" name="companyName" value="<%= companyName %>">

    <%-- values for CashBatch filtering --%>
    <input type="hidden" name="status" value="<%= status %>">
    <input type="hidden" name="month" value="<%= month %>">
    <input type="hidden" name="day" value="<%= day %>">
    <input type="hidden" name="year" value="<%= year %>">
    <input type="hidden" name="filterAmount" value="<%= filterAmount %>">
    <input type="hidden" name="filterOperator" value="<%= filterOperator %>">
    <input type="hidden" name="balanceRemaining" value="<%= remainingAmount %>">

<%-- ********** END Hidden Variable ********** --%>
</form>
</body>
</html>
