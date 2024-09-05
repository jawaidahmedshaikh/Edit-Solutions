<%@ page import="contract.ContractClient,
                 contract.Segment,
                 java.math.BigDecimal,
                 edit.common.EDITBigDecimal,
                 edit.common.EDITDate,
                 java.util.Set,
                 java.util.Iterator,
                 role.ClientRole,
                 client.ClientDetail,
                 contract.ContractClientAllocation,
                 edit.common.vo.*,
                 fission.utility.*"%>
<!--
 * User: sprasad
 * Date: Mar 21, 2005
 * Time: 11:03:04 AM
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 -->
 <%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%-- ****************************** BEGIN Java Code ****************************** --%>
<%
    String responseMessage = (String) request.getAttribute("responseMessage");

    String segmentPK = (String) request.getAttribute("segmentPK");

    Segment segment = null;

    if (segmentPK != null)
    {
        segment = Segment.findByPK(new Long(segmentPK));
    }

    // Contract information is always displayed.
    String contractNumber = (String) Util.initObject(segment, "ContractNumber", "");
    String segmentName = (String) Util.initObject(segment, "SegmentNameCT", "");
    String segmentStatus = (String) Util.initObject(segment, "SegmentStatusCT", "");

    String process = (String) session.getAttribute("casetracking.process");

    EDITBigDecimal annuitizationValue   = new EDITBigDecimal("0.00");
    EDITBigDecimal interestPaid         = new EDITBigDecimal("0.00");
    EDITBigDecimal grossProceedsPaid    = new EDITBigDecimal("0.00");
    EDITBigDecimal deathValueRemaining  = new EDITBigDecimal("0.00"); 

    if (ClientDetail.CASETRACKING_PROCESS_ANNUITIZATION.equalsIgnoreCase(process))
    {
        annuitizationValue = (EDITBigDecimal) Util.initObject(segment, "AnnuitizationValue", null);
    }
    else if (ClientDetail.CASETRACKING_PROCESS_CLAIMS.equalsIgnoreCase(process))
    {
        annuitizationValue = (EDITBigDecimal) Util.initObject(segment, "DateOfDeathValue", null);
        if (segment != null)
        {
            interestPaid = segment.getTotalInterestProceedsForLumpSumTransactions();
            grossProceedsPaid = segment.getTotalGrossProceedsForLumpSumTransactions();
            if (annuitizationValue != null)
            {
//                EDITBigDecimal totalAccumValue = segment.getTotalAccumulatedValueForLumpSumTransactions();
//                totalAccumValue = totalAccumValue.subtractEditBigDecimal(interestPaid);
//                deathValueRemaining = annuitizationValue.subtractEditBigDecimal(totalAccumValue);
                deathValueRemaining = annuitizationValue.subtractEditBigDecimal(grossProceedsPaid);
            }
        }
    }

    ContractClient activeContractClient = (ContractClient) request.getAttribute("activeContractClient");

    ContractClientAllocation activeAllocation = null;
    if (activeContractClient != null)
    {
        activeAllocation = activeContractClient.getContractClientAllocation();
    }

    EDITDate disbursementEDITDate           = (EDITDate) null;
    EDITBigDecimal deathValue           = new EDITBigDecimal("0");
    EDITBigDecimal interest             = new EDITBigDecimal("0");
    EDITBigDecimal total                = new EDITBigDecimal("0");
    EDITBigDecimal beneAllocPercent     = new EDITBigDecimal("0");

    CaseTrackingQuoteVO quoteVO = (CaseTrackingQuoteVO) session.getAttribute("caseTrackingQuoteVO");

    if (quoteVO != null)
    {
        disbursementEDITDate = new EDITDate(quoteVO.getDisbursementDate());
        deathValue = new EDITBigDecimal(quoteVO.getDeathValue());
        interest = new EDITBigDecimal(quoteVO.getInterest());
        total = deathValue.addEditBigDecimal(interest);
        beneAllocPercent = new EDITBigDecimal(quoteVO.getBeneAllocationPct());
    }
    else
    {
        // Displayed only when the summary entry is selected.
        beneAllocPercent     = (EDITBigDecimal) Util.initObject(activeAllocation, "AllocationPercent", null);
    }

%>
<%-- ****************************** End Java Code ****************************** --%>

<html>
<head>
<title>EDIT SOLUTIONS - Case Tracking - Contract</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta http-equiv="Cache-Control" content="no-store">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Expires" content="0">
<link href="/PORTAL/common/css/EDITSolutions.css" rel="stylesheet" type="text/css">

<%-- ****************************** BEGIN JavaScript ****************************** --%>
<script src="/PORTAL/common/javascript/commonJavascriptFunctions.js"></script>
<script src="/PORTAL/common/javascript/widgetFunctions.js"></script>
<script src="/PORTAL/common/javascript/scrollTable.js"></script>
<script src="/PORTAL/common/javascript/formatDateFunctions.js"></script>
<script src="/PORTAL/common/javascript/calendarSelector.js"></script>


<script>

    var f = null;

    var responseMessage = "<%= responseMessage %>";
    var segmentStatus = "<%= segmentStatus %>";

    /**
     * Called after the body of the page is loaded.
     */
    function init()
    {
        f = document.theForm;

        checkForResponseMessage();

        formatCurrency();

        // Initialize scroll tables
        initScrollTable(document.getElementById("ContractDialogTableModelScrollTable"));
    }

    function showBeneficiariesDialog()
    {
        var width = 0.99 * screen.width;
        var height = 0.85 * screen.height;

	    openDialog("beneficiariesDialog", "top=0,left=0,resizable=no", width,  height);
        sendTransactionAction("CaseTrackingTran", "showBeneficiariesDialog", "beneficiariesDialog");
    }

    function showTransactionsDialog()
    {
        var width = 0.50 * screen.width;
        var height = 0.10 * screen.height;

	    openDialog("casetrackingTransactionsDialog", "top=0,left=0,resizable=no", width,  height);
        sendTransactionAction("CaseTrackingTran", "showTransactionsDialog", "casetrackingTransactionsDialog");
    }

    function closeContractDialog()
    {
        window.close();
    }

    function executeDeathQuote()
    {
        if (segmentStatus != "Death")
        {
            alert('Contract Status Not Equal Death');
            return;
        }

        if (f.disbursementDate.value == "")
        {
            alert("Disbursement Date Not Entered");
            return;
        }

        sendTransactionAction("CaseTrackingTran", "performDeathQuote", "_self");
    }

    function closeAndShowClientPage()
    {
        sendTransactionAction("CaseTrackingTran", "showCasetrackingClient", "main");

        window.close();
    }

    function onTableRowSingleClick(tableId)
    {
        sendTransactionAction("CaseTrackingTran", "showContractDetail", "_self");
    }

</script>
<%-- ****************************** END JavaScript ****************************** --%>

</head>
<body class="mainTheme" onLoad="init()">
<form name="theForm" method="post" action="/PORTAL/servlet/RequestManager">

<%-- ****************************** BEGIN Form Data ****************************** --%>
<table class="formData" width="100%" border="0" cellspacing="0" cellpadding="5">
<%--    BEGIN Form Content --%>
  <tr>
    <td>
      <table width="100%" border="0" cellspacing="0" cellpadding="5">
        <tr>
          <td align="right" nowrap>
            Contract Number:&nbsp;
          </td>
          <td align="left" nowrap>
            <input disabled type="text" name="contractNumber" size="15" maxlength="20" value="<%= contractNumber %>">
          </td>
        </tr>
        <tr>
          <td align="right" nowrap>
            Segment:&nbsp;
          </td>
          <td align="left" nowrap>
            <input disabled type="text" name="segmentName" size="15" maxlength="20" value="<%= segmentName %>">
          </td>
        </tr>
        <tr>
          <td align="right" nowrap>
            Death/Annuitization Value:&nbsp;
          </td>
          <td align="left" nowrap>
            <input disabled type="text" name="annuitizationValue" size="15" maxlength="20" value="<%= annuitizationValue == null? "": annuitizationValue.toString() %>" CURRENCY>
          </td>
        </tr>
        <tr>
          <td align="right" nowrap>
            Interest Paid:&nbsp;
          </td>
          <td align="left" nowrap>
            <input disabled type="text" name="interestPaid" size="15" maxlength="20" value="<%= interestPaid == null ? "" : interestPaid.toString() %>" CURRENCY>
          </td>
        </tr>
        <tr>
          <td align="right" nowrap>
            Gross Proceeds Paid:&nbsp;
          </td>
          <td align="left" nowrap>
            <input disabled type="text" name="grossProceedsPaid" size="15" maxlength="20" value="<%= grossProceedsPaid == null ? "" : grossProceedsPaid.toString() %>" CURRENCY>
          </td>
        </tr>
        <tr>
          <td align="right" nowrap>
            Death Value Remaining:&nbsp;
          </td>
          <td align="left" nowrap>
            <input disabled type="text" name="deathValueRemaining" size="15" maxlength="20" value="<%= deathValueRemaining == null ? "" : deathValueRemaining.toString()%>" CURRENCY>
          </td>
        </tr>
      </table>
    </td>
    <td>
      <span style="border-style:solid; border-width:1; position:relative; width:65%; height:45% top:0; left:0; z-index:0; overflow:visible">
      <table width="100%" border="0" cellspacing="0" cellpadding="5">
        <tr>
          <td align="right" nowrap>
              Bene Alloc %:&nbsp;
          </td>
          <td align="left" nowrap>
              <input type="text" name="beneAllocPercent" size="15" maxlength="20" value="<%= beneAllocPercent == null ? "" : beneAllocPercent.trim() %>">
          </td>
        </tr>
        <tr>
          <td align="right" nowrap>
              Disbursement Date:&nbsp;
          </td>
          <td align="left" nowrap>
           <input type="text" name="disbursementDate" value="<%= disbursementEDITDate == null ? "" : DateTimeUtil.formatYYYYMMDDToMMDDYYYY(disbursementEDITDate.getFormattedDate()) %>" size='10' maxlength="10" onKeyUp="DateFormat(this,this.value,event,false)" onBlur="DateFormat(this,this.value,event,true)">
           <a href="javascript:show_calendar('f.disbursementDate', f.disbursementDate.value);"><img src="/PORTAL/common/images/calendarIcon.gif" width="16" height="16" border="0" alt="Select a date from the calendar"></a>
          </td>
        </tr>
        <tr>
          <td align="right" nowrap>
              Death Value:&nbsp;
          </td>
          <td align="left" nowrap>
              <input disabled type="text" name="deathValue" size="15" maxlength="20" value="<%= deathValue == null ? "" : deathValue.trim() %>" CURRENCY>
          </td>
        </tr>
        <tr>
          <td align="right" nowrap>
              Interest:&nbsp;
          </td>
          <td align="left" nowrap>
              <input disabled type="text" name="interest" size="15" maxlength="20" value="<%= interest == null ? "" : interest.trim() %>" CURRENCY>
          </td>
        </tr>
        <tr>
          <td align="right" nowrap>
              Total:&nbsp;
          </td>
          <td align="left" nowrap>
              <input disabled type="text" name="total" size="15" maxlength="20" value="<%= total == null ? "" : total.trim() %>" CURRENCY>
          </td>
        </tr>
        <tr>
          <td align="right" nowrap>
              &nbsp;
          </td>
          <td align="left" nowrap>
              <input type="button" value=" Quote " onClick="executeDeathQuote()">
          </td>
        </tr>
      </table>
      </span>
    </td>
    </tr>
    <tr class="filler">
      <td colspan="2">
          &nbsp; <!--Filler Row -->
      </td>
    </tr>
    <tr>
      <td align="left" nowrap>
          &nbsp;
      </td>
      <td align="center" valign="bottom" nowrap>
          <a href="javascript:showBeneficiariesDialog()">Beneficiaries</a> &nbsp;&nbsp;
          <a href="javascript:showTransactionsDialog()">Transactions</a>
      </td>
    </tr>
<%--    END Form Content --%>
</table>
<%-- ****************************** END Form Data ****************************** --%>

<br>

<jsp:include page="/common/jsp/widget/tableWidget.jsp" flush="true">
    <jsp:param name="tableId" value="ContractDialogTableModel"/>
    <jsp:param name="tableHeight" value="40"/>
    <jsp:param name="multipleRowSelect" value="false"/>
    <jsp:param name="singleOrDoubleClick" value="single"/>
</jsp:include>

<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
        <td align="right">
            <input type="button" value=" Close " onClick="closeAndShowClientPage()">
        </td>
    </tr>
</table>
<%-- ****************************** END Summary Area ****************************** --%>

<%-- ****************************** BEGIN Hidden Variables ****************************** --%>
<input type="hidden" name="transaction">
<input type="hidden" name="action">
<input type="hidden" name="segmentPK" value="<%= segmentPK %>">
<%--<input type="hidden" name="disbursementDate">--%>
<input type="hidden" name="deathValue" value="<%= deathValue %>">
<input type="hidden" name="interest" value="<%= interest %>">
<%-- ****************************** END Hidden Variables ****************************** --%>

</form>
</body>
</html>