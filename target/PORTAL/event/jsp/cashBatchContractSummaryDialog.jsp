<!-- ****** JAVA CODE ***** //-->

<%@ page  errorPage="/common/jsp/error.jsp" autoFlush="true" %>
<%@ page import="java.util.*,
                 edit.common.*,
                 fission.utility.Util,
                 event.CashBatchContract" %>
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
    String viewMode = Util.initString((String) request.getAttribute("viewMode"), "change");
    String responseMessage = (String) Util.initString((String) request.getAttribute("responseMessage"), "");

    CashBatchContract activeCashBatchContract = (CashBatchContract) request.getAttribute("activeCashBatchContract");

    EDITBigDecimal balanceRemaining = (EDITBigDecimal) request.getAttribute("balanceRemaining");
    if (balanceRemaining == null)
    {
        balanceRemaining = new EDITBigDecimal();
    }

    String batchId = "";
    EDITBigDecimal batchTotal = new EDITBigDecimal();
    EDITBigDecimal totalApplied = new EDITBigDecimal();

    if (activeCashBatchContract != null)
    {
        batchId = (String) Util.initObject(activeCashBatchContract, "batchID", "");
        batchTotal = (EDITBigDecimal) Util.initObject(activeCashBatchContract, "amount", new EDITBigDecimal());
        if (!activeCashBatchContract.getReleaseIndicator().equalsIgnoreCase("P"))
        {
            totalApplied = batchTotal.subtractEditBigDecimal(balanceRemaining);
        }
    }

    // Getting CashBatch Filter values
    String status = (String) request.getAttribute("status");
    String month = (String) request.getAttribute("month");
    String day = (String) request.getAttribute("day");
    String year = (String) request.getAttribute("year");
    String filterAmount = (String) request.getAttribute("filterAmount");
    String filterOperator = (String) request.getAttribute("filterOperator");
    String cashBatchContractFK = (String)request.getAttribute("cashBatchContractFK");
%>

<html>

<script src="/PORTAL/common/javascript/commonJavascriptFunctions.js"></script>

<script src="/PORTAL/common/javascript/widgetFunctions.js"></script>
<script src="/PORTAL/common/javascript/scrollTable.js"></script>


<script language="JavaScript1.2">

<!--******* JAVASCRIPT ******//-->

    var f = null;

    var responseMessage = "<%= responseMessage %>";
    var batchId = "<%= batchId %>";
    var viewMode = "<%= viewMode %>";

    function init()
    {
        f = document.cashBatchContractSummaryForm;

        checkForResponseMessage();

        formatCurrency();

        if (viewMode != "change")
        {
            f.btnAdd.disabled = true;
            f.btnChange.disabled = true;
            f.btnDelete.disabled = true;
            f.btnRelease.disabled = true;
        }

        // Initialize scroll tables
        initScrollTable(document.getElementById("CashBatchContractSummaryTableModelScrollTable"));
    }

    function addCashBatchContractDetail()
    {
        var width = .99 * screen.width;
        var height = .75 * screen.height;

        openDialog("cashBatchContractDetailDialog", "top=0,left=0,resizable=no", width, height);

        sendTransactionAction("EventAdminTran", "addCashBatchContractDetail", "cashBatchContractDetailDialog");
    }

    function deleteSuspense()
    {
        sendTransactionAction("EventAdminTran", "deleteSelectedSuspense", "_self");
    }

    function editSuspense()
    {
        var width = .95 * screen.width;
        var height = .90 * screen.height;

        openDialog("cashBatchContractDetailDialog", "top=0,left=0,resizable=no", width, height);

        sendTransactionAction("EventAdminTran", "showCashBatchContractDetail", "cashBatchContractDetailDialog");
    }

    function viewSuspense()
    {
        var width = .95 * screen.width;
        var height = .90 * screen.height;

        openDialog("cashBatchContractDetailDialog", "top=0,left=0,resizable=no", width, height);

        sendTransactionAction("EventAdminTran", "viewCashBatchContractDetail", "cashBatchContractDetailDialog");
    }

    function releaseCashBatch()
    {
        sendTransactionAction("EventAdminTran", "releaseCashBatch", "_self");
    }

    function closeCashBatchContract()
    {
        sendTransactionAction("EventAdminTran", "cancelCashBatchContractSummary", "main");
        closeWindow();
    }

</script>
<head>
<title>Cash Batch Contract Summary</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta http-equiv="Cache-Control" content="no-store">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Expires" content="0">

<link href="/PORTAL/common/css/EDITSolutions.css" rel="stylesheet" type="text/css">
</head>

<!-- ****** HTML CODE ***** //-->

<body class="mainTheme" onLoad="init()">
<form name="cashBatchContractSummaryForm" method="post" action="/PORTAL/servlet/RequestManager">

<%-- ********** BEGIN Form Data ********** --%>

<table class="formData" width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td>
        <table class="formData" width="100%" height="1%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td align="right" nowrap>Batch ID:&nbsp;<%= batchId %></td>
          </tr>
          <tr class="filler">
            <td>
              &nbsp; <!-- Filler Row -->
            </td>
          </tr>
        </table>
        <table class="formData" width="100%" height="5%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td align="right" nowrap>Batch Total:&nbsp;</td>
            <td align="left" nowrap>
              <input disabled type="text" name="batchTotal" size="15" maxlength="15" value="<%= batchTotal.toString() %>" CURRENCY>
            </td>
            <td align="right" nowrap>Total Applied:&nbsp;</td>
            <td align="left" nowrap>
              <input disabled type="text" name="totalApplied" size="15" maxlength="15" value="<%= totalApplied.toString() %>" CURRENCY>
            </td>
            <td align="right" nowrap>Balance Remaining:&nbsp;</td>
            <td align="left" nowrap>
              <input disabled type="text" name="balanceRemaining" size="15" maxlength="15" value="<%= balanceRemaining.toString() %>" CURRENCY>
            </td>
          </tr>
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
              <input type="button" id="btnAdd" name="add" value="Add" onClick="addCashBatchContractDetail()">
              <input type="button" id="btnChange" name="change" value="Change" onClick="editSuspense()">
              <input type="button" id="btnDelete" name="delete" value="Delete" onClick="deleteSuspense()">
              <input type="button" id="btnView" name="view" value="View" onClick="viewSuspense()">
            </td>
          </tr>
        </table>

<%-- ********** BEGIN Summary Area ********** --%>

        <jsp:include page="/common/jsp/widget/tableWidget.jsp" flush="true">
          <jsp:param name="tableId" value="CashBatchContractSummaryTableModel"/>
          <jsp:param name="tableHeight" value="60"/>
          <jsp:param name="multipleRowSelect" value="false"/>
          <jsp:param name="singleOrDoubleClick" value="single"/>
        </jsp:include>

<%-- ********** END Summary Area ********** --%>

<%-- Buttons --%>
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr valign="top">
            <td nowrap align="right">
              <input type="button" id="btnRelease" name="add" value="Release" onClick="releaseCashBatch()">
            </td>
          </tr>
          <tr>
            <td>&nbsp</td>
          </tr>
          <tr valign="bottom">
            <td nowrap align="right">
              <input type="button" id="btnClose" name="close" value="Close" onClick="closeCashBatchContract()">
            </td>
          </tr>
        </table>
    </td>
  </tr>
</table>



<%-- ********** BEGIN Hidden Variables ********** --%>

  	<input type="hidden" name="transaction" value="">
    <input type="hidden" name="action"      value="">
    <input type="hidden" name="batchId" value="<%= batchId %>">
    <input type="hidden" name="balanceRemaining" value="<%= balanceRemaining %>">

    <%-- values for CashBatch filtering --%>
    <input type="hidden" name="status" value="<%= status %>">
    <input type="hidden" name="month" value="<%= month %>">
    <input type="hidden" name="day" value="<%= day %>">
    <input type="hidden" name="year" value="<%= year %>">
    <input type="hidden" name="filterAmount" value="<%= filterAmount %>">
    <input type="hidden" name="filterOperator" value="<%= filterOperator %>">
    <input type="hidden" name="cashBatchContractFK" value="<%= cashBatchContractFK %>">

<%-- ********** END Hidden Variable ********** --%>

</body>
</form>
</html>
