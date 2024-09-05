<%@ page import="edit.common.vo.CodeTableVO,
                 codetable.dm.dao.CodeTableDAO,
                 fission.utility.Util,
                 edit.common.CodeTableWrapper,
                 contract.Segment"%>
<!--
 * User: sprasad
 * Date: Mar 18, 2005
 * Time: 5:07:16 PM
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 -->
 <%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%-- ****************************** BEGIN Java Code ****************************** --%>
<%
    String responseMessage = (String) request.getAttribute("responseMessage");

    String segmentPK    = (String) request.getAttribute("segmentPK");
    Segment segment     = Segment.findByPK(new Long(segmentPK));

    String contractNumber   = segment.getContractNumber();
    String segmentNameCT    = segment.getSegmentNameCT();

    String casetrackingOption = (String) request.getAttribute("casetrackingOption");

    String effectiveDate = "";

    String zeroInterestIndicator = "unchecked";
%>
<%-- ****************************** End Java Code ****************************** --%>

<html>
<head>
<title>EDIT SOLUTIONS - Case Tracking - Lump Sum Proceeds</title>
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

    /**
     * Called after the body of the page is loaded.
     */
    function init()
    {
        f = document.theForm;

        checkForResponseMessage();

        formatCurrency();

        // Initialize scroll tables
        initScrollTable(document.getElementById("ContractDetailDoubleTableModelScrollTable"));
    }

    /**
     * Dummary method to show how one might show the detail of a selected row in the summary table.
     */
    function showTableSummaryDetail()
    {
        var selectedRowId = getSelectedRowId("tableSummary");

        if (valueIsEmpty(selectedRowId))
        {
            alert("Value Is Empty");
        }
        else
        {
            f.activeTableSummaryPK.value = selectedRowId;

            sendTransactionAction("FooTran", "showTableSummaryDetail", "main");
        }
    }

    /**
     *  save the Lump Sum transactions requested
     */
    function saveLumpSum()
    {
       disableActionButtons();
       
       if (f.zeroInterestIndicator.checked == true) {
           f.zeroInterestIndicator.value = "checked";
       } 

       sendTransactionAction("CaseTrackingTran", "saveLumpSumTrx", "_self");
    }

    /*
     * Moves rows.
     */
    function moveRows(tableId)
    {
        move(tableId, "CaseTrackingTran", "updateCasetrackingOptionDoubleTable", "_self");
    }

    function showWithholdingOverrideDialog()
    {
        var width = 0.99 * screen.width;
        var height = 0.50 * screen.height;

        openDialog("withHoldingOverrideDialog", "top=0,left=0,resizable=no", width,  height);
        sendTransactionAction("CaseTrackingTran", "showWithholdingOverrideDialog", "withHoldingOverrideDialog");
    }

    function showChargesOverrideDialog()
    {
        var width = 0.99 * screen.width;
        var height = 0.50 * screen.height;

        openDialog("chargesOverrideDialog", "top=0,left=0,resizable=no", width,  height);
        sendTransactionAction("CaseTrackingTran", "showChargeOverridesDialog", "chargesOverrideDialog");
    }

    /**
     * While a tranaction is being saved, we want to disable all buttons to prevent double-saves.
     */
    function disableActionButtons()
    {
        document.all.trxMessage.innerHTML = "<font color='blue' face=''>Processing...<font>";

        document.all.btnSave.style.backgroundColor = "#99BBBB";
        document.all.btnCancel.style.backgroundColor = "#99BBBB";
        document.all.btnClose.style.backgroundColor = "#99BBBB";

        document.all.btnSave.disabled = true;
        document.all.btnCancel.disabled = true;
        document.all.btnClose.disabled = true;
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
        <td align="right" nowrap>
            Contract Number:&nbsp;
        </td>
        <td align="left" nowrap>
            <input disabled name="contractNumber" type="text" size="15" value="<%= contractNumber %>">
        </td>
        <td align="right" nowrap>
            &nbsp;
        </td>
        <td align="left" nowrap>
            &nbsp;
        </td>
        <td align="right" nowrap>
            Segment:&nbsp;
        </td>
        <td align="left" nowrap>
            <input disabled name="segment" type="text" size="15" value="<%= segmentNameCT %>">
        </td>
    </tr>
    <tr>
        <td colspan="6">
            <jsp:include page="/common/jsp/widget/doubleTableWidget.jsp" flush="true">
                <jsp:param name="tableId" value="ContractDetailDoubleTableModel"/>
                <jsp:param name="multipleRowSelect" value="true"/>
            </jsp:include>
        </td>
    </tr>
    <tr>
        <td colspan="6">
            &nbsp;
        </td>
    </tr>
    <tr>
        <td align="right" nowrap>
            Amount Override:&nbsp;
        </td>
        <td align="left" nowrap>
            <input name="amountOverride" type="text" size="11" maxlength="11" value="" CURRENCY>
        </td>
        <td align="right" nowrap>
            &nbsp;
        </td>
        <td align="left" nowrap>
            &nbsp;
        </td>
        <td align="right" nowrap>
            &nbsp;
        </td>
        <td align="left" nowrap>
            &nbsp;
        </td>
    </tr>
    <tr>
        <td align="right" nowrap>
            Interest Override:&nbsp;
        </td>
        <td align="left" nowrap>
            <input name="interestOverride" type="text" size="11" maxlength="11" value="" CURRENCY>
        </td>
        <td align="right" nowrap>
            Zero Interest&nbsp;
        </td>
        <td align="left" nowrap>
            <input type="checkbox" name="zeroInterestIndicator" <%= zeroInterestIndicator %>>
        </td>
        <td align="right" nowrap>
            &nbsp;
        </td>
        <td align="left" nowrap>
            &nbsp;
        </td>
    </tr>
    <tr>
        <td align="right" nowrap>
            Effective Date:&nbsp;
        </td>
        <td align="left" nowrap>
           <input type="text" name="effectiveDate" value="<%= effectiveDate %>" size='10' maxlength="10" onKeyUp="DateFormat(this,this.value,event,false)" onBlur="DateFormat(this,this.value,event,true)">
           <a href="javascript:show_calendar('f.effectiveDate', f.effectiveDate.value);"><img src="/PORTAL/common/images/calendarIcon.gif" width="16" height="16" border="0" alt="Select a date from the calendar"></a>
        </td>
        <td align="right" nowrap>
            &nbsp;
        </td>
        <td align="left" nowrap>
            &nbsp;
        </td>
        <td align="right" nowrap>
            &nbsp;
        </td>
        <td align="left" nowrap>
            &nbsp;
        </td>
    </tr>
    <tr>
        <td align="right" nowrap>
            Tax Year:&nbsp;
        </td>
        <td align="left" nowrap>
            <input name="taxYear" type="text" size="4" maxlength="4" value="">
        </td>
        <td align="right" nowrap>
            &nbsp;
        </td>
        <td align="left" nowrap>
            &nbsp;
        </td>
        <td align="right" nowrap>
            <a href="javascript:showChargesOverrideDialog()">Charges</a>
        </td>
        <td align="left" nowrap>
            <a href="javascript:showWithholdingOverrideDialog()">Withholding Overrides</a>
        </td>
    </tr>
<%--    END Form Content --%>

    <tr>
        <td align="left" colspan="3">
            &nbsp;
        </td>
        <td id="trxMessage">
            &nbsp;
        </td>
       <td align="left" colspan="1">
            &nbsp;
        </td>
        <td align="right">
            <input id="btnSave" type="button" value=" Save " onClick="saveLumpSum()">
            <input id="btnCancel" type="button" value=" Cancel " onClick="resetForm()">
            <input id="btnClose" type="button" value=" Close " onClick="closeWindow()">
        </td>
    </tr>
</table>
<%-- ****************************** END Form Data ****************************** --%>

<%-- ****************************** BEGIN Hidden Variables ****************************** --%>
<input type="hidden" name="transaction">
<input type="hidden" name="action">
<%--<input type="hidden" name="effectiveDate">--%>
<input type="hidden" name="segmentPK" value="<%= segmentPK %>">
<input type="hidden" name="casetrackingOption" value="<%= casetrackingOption %>">
<%-- ****************************** END Hidden Variables ****************************** --%>

</form>
</body>
</html>
