<%@ page import="edit.common.vo.CodeTableVO,
                 codetable.dm.dao.CodeTableDAO,
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
%>
<%-- ****************************** End Java Code ****************************** --%>

<html>
<head>
<title>EDIT SOLUTIONS - Case Tracking - Spousal Continuation</title>
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

    /*
     * Moves rows.
     */
    function moveRows(tableId)
    {
        move(tableId, "CaseTrackingTran", "updateCasetrackingOptionDoubleTable", "_self");
    }

    function saveSpousalContinuation()
    {
        disableActionButtons();

        sendTransactionAction("CaseTrackingTran", "saveSpousalContinuation", "_self");
    }

    /**
     * While a tranaction is being saved, we want to disable all buttons to prevent double-saves.
     */
    function disableActionButtons()
    {
        document.all.trxMessage.innerHTML = "<font color='blue' face=''>Processing...<font>";

        document.all.btnSubmit.style.backgroundColor = "#99BBBB";
        document.all.btnCancel.style.backgroundColor = "#99BBBB";
        document.all.btnClose.style.backgroundColor = "#99BBBB";

        document.all.btnSubmit.disabled = true;
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
                <jsp:param name="multipleRowSelect" value="false"/>
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
            <input id="btnSubmit" type="button" value=" Submit " onClick="saveSpousalContinuation()">
            <input id="btnCancel" type="button" value=" Cancel " onClick="resetForm()">
            <input id="btnClose"  type="button" value=" Close " onClick="window.close()">
        </td>
    </tr>
</table>
<%-- ****************************** END Form Data ****************************** --%>

<%-- ****************************** BEGIN Hidden Variables ****************************** --%>
<input type="hidden" name="transaction">
<input type="hidden" name="action">
<input type="hidden" name="segmentPK" value="<%= segmentPK %>">
<input type="hidden" name="casetrackingOption" value="<%= casetrackingOption %>">
<%--<input type="hidden" name="effectiveDate">--%>
<%-- ****************************** END Hidden Variables ****************************** --%>

</form>
</body>
</html>