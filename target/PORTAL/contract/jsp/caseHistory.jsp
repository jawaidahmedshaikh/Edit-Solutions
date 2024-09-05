<%@ page import="edit.common.EDITDate,
                 fission.utility.Util,
                 edit.common.vo.CodeTableVO,
                 edit.common.CodeTableWrapper,
                 edit.services.db.hibernate.*,
                 java.util.*,
                 contract.*,
                 group.*,
                 client.*,
                 engine.*,
                 edit.portal.common.session.*,
                 fission.utility.*" %>
<!--
 * User: dlataille
 * Date: May 2, 2007
 * Time: 8:28:50 AM
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 -->
 <%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%-- ****************************** BEGIN Java Code ****************************** --%>
<%
    String responseMessage = (String) request.getAttribute("responseMessage");
    String groupNumber = Util.initString((String) request.getAttribute("selectedGroupNumber"), "");
    String fromDate = (String) request.getAttribute("fromDate");
    String toDate = (String) request.getAttribute("toDate");
    String selectedPDPK = (String) request.getAttribute("selectedPDPK");

    ContractGroup groupContractGroup = (ContractGroup) request.getAttribute("contractGroup");
    PayrollDeduction payrollDeduction = (PayrollDeduction) request.getAttribute("payrollDeduction");
    String groupName = "";

    String transactionType = "PRD";
    EDITDate effectiveDate = null;
    String effectiveDateVar = "";

    if (payrollDeduction != null)
    {
        effectiveDate = payrollDeduction.getPRDExtractDate();
        effectiveDateVar = DateTimeUtil.formatEDITDateAsMMDDYYYY(effectiveDate);
    }

    if (groupContractGroup != null)
    {
        groupName = groupContractGroup.getOwnerClient().getName();
    }

%>
<%-- ****************************** End Java Code ****************************** --%>

<html>
<head>
<title>EDIT SOLUTIONS - Case History</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta http-equiv="Cache-Control" content="no-store">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Expires" content="0">
<link href="/PORTAL/common/css/EDITSolutions.css" rel="stylesheet" type="text/css">

<%-- ****************************** BEGIN JavaScript ****************************** --%>
<script src="/PORTAL/common/javascript/commonJavascriptFunctions.js"></script>
<script src="/PORTAL/contract/javascript/caseMainTabFunctions.js"></script>
<script src="/PORTAL/common/javascript/widgetFunctions.js"></script>
<script src="/PORTAL/common/javascript/scrollTable.js"></script>
<script src="/PORTAL/common/javascript/formatDateFunctions.js"></script>
<script src="/PORTAL/common/javascript/calendarSelector.js"></script>


<script>

    var f = null;

    var responseMessage = "<%= responseMessage %>";
    var shouldShowLockAlert = true;

    /**
     * Called after the body of the page is loaded.
     */
    function init()
    {
        f = document.theForm;

        setActiveImage("history");

        checkForResponseMessage();

<%--        // Initialize scroll tables--%>
<%--        initScrollTable(document.getElementById("GroupSummaryTableModelScrollTable"));--%>

<%--        var caseIsLocked = <%= userSession.getCaseIsLocked()%>;--%>
<%--        var username = "<%= userSession.getUsername() %>";--%>
<%--        var elementPK = "<%= userSession.getCasePK() %>";--%>
<%--		top.frames["header"].updateLockState(caseIsLocked, username, elementPK);--%>

<%--        shouldShowLockAlert = !caseIsLocked;--%>

<%--        for (var i = 0; i < f.elements.length; i++)--%>
<%--        {--%>
<%--            var elementType = f.elements[i].type;--%>
<%----%>
<%--            if ( (elementType == "text" || elementType == "button") && (shouldShowLockAlert == true) )--%>
<%--            {--%>
<%--                f.elements[i].onclick = showLockAlert;--%>
<%--                f.elements[i].onkeydown = showLockAlert;--%>
<%--            }--%>
<%--        }--%>
    }

    function showLockAlert()
    {
<%--        if (shouldShowLockAlert == true)--%>
<%--        {--%>
<%--            alert("The Case can not be edited.");--%>
<%----%>
<%--            return false;--%>
<%--        }--%>
    }

    function showFilterCaseHistory()
    {
        var width = .55 * screen.width;
        var height = .35 * screen.height;

        openDialog("caseHistoryFilterDialog","left=0,top=0,resizable=no", width, height);

        sendTransactionAction("CaseDetailTran", "showCaseHistoryFilter", "caseHistoryFilterDialog");
    }

    function resetFormValues()
    {
        f.groupNumber.value = "";
        f.groupName.value = "";
        f.effectiveDate.value = "";
        f.statusCT.selectedIndex = 0;
        f.operator.value = "";

        sendTransactionAction("CaseDetailTran", "cancelGroupEntry", "main");
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

    /*
    Callback method from TableModel implemenation.
    */
    function onTableRowSingleClick(tableId)
    {
        showPRDDetail();
    }

    /**
    * Shows the detail(s) of the just-selected Group Summary Table.
    */
    function showPRDDetail()
    {
        sendTransactionAction("CaseDetailTran", "showPRDDetail", "_self");
    }

    function prdReport()
    {
        var width = .55 * screen.width;
        var height = .35 * screen.height;

        openDialog("prdReportDialog","left=0,top=0,resizable=no", width, height);

        sendTransactionAction("CaseDetailTran", "showPRDReport", "prdReportDialog");
    }

</script>
<%-- ****************************** END JavaScript ****************************** --%>

</head>
<body class="mainTheme" onLoad="init()">
<form name="theForm" method="post" action="/PORTAL/servlet/RequestManager">

<%-- ****************************** Tab Content ****************************** --%>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      <td>
        <jsp:include page="caseMainTabContent.jsp" flush="true"/>
      </td>
    </tr>
</table>

<%-- ****************************** BEGIN Form Data ****************************** --%>
<table class="formData" width="100%" border="0" cellspacing="0" cellpadding="0">
<%--    BEGIN Form Content --%>
    <table>
    <tr>
        <td align="left" nowrap>
            Group Number:&nbsp;
        </td>
        <td align="left" nowrap>
            <input disabled type="text" name="groupNumber" size="15" value="<%= groupNumber %>">
        </td>
        <td align="left" nowrap colspan="2">&nbsp;</td>
    </tr>
    <tr>
        <td align="left" nowrap>
            Group Name:&nbsp;
        </td>
        <td align="left" nowrap>
             <input disabled type="text" name="groupName" size="30" maxLength="60" value="<%= groupName %>">
        </td>
        <td align="left" nowrap colspan="2">&nbsp;</td>
    </tr>
    <tr>
        <td align="left" nowrap>
            Transaction Type:&nbsp;
        </td>
        <td align="left" nowrap>
            <input disabled type="text" name="transactionType" size="35" value="<%= transactionType %>">
        </td>
        <td align="left" nowrap colspan="2">&nbsp;</td>
    </tr>
    <tr>
        <td align="left" nowrap>Effective Date:&nbsp;</td>
        <td align="left" nowrap>
            <input disabled type="text" name="effectiveDate" value="<%= effectiveDateVar %>" size='10' maxlength="10" onKeyUp="DateFormat(this,this.value,event,false)" onBlur="DateFormat(this,this.value,event,true)">
        </td>
        <td align="left" nowrap>&nbsp;</td>
        <td align="left" nowrap>
            <a href ="javascript:prdReport()">PRD Report</a>
        </td>
    </tr>
    <tr>
        <td align="left" nowrap colspan="4">&nbsp;</td>
    </tr>
</table>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
	  	<td nowrap align="right" colspan="6">
			<input id="btnFilter" type="button" value="Filter" onClick="showFilterCaseHistory()">
        </td>
    </tr>
</table>
<%--    END Form Content --%>
</table>
<%-- ****************************** END Form Data ****************************** --%>

<%-- ****************************** BEGIN Summary Area ****************************** --%>

<jsp:include page="/common/jsp/widget/tableWidget.jsp" flush="true">
    <jsp:param name="tableId" value="CaseHistoryTableModel"/>
    <jsp:param name="tableHeight" value="60"/>
    <jsp:param name="multipleRowSelect" value="false"/>
    <jsp:param name="singleOrDoubleClick" value="single"/>
</jsp:include>

<%-- ****************************** END Summary Area ****************************** --%>

<%-- ****************************** BEGIN Hidden Variables ****************************** --%>
<input type="hidden" name="transaction">
<input type="hidden" name="action">
<input type="hidden" name="pageToShow">
<input type="hidden" name="selectedGroupNumber" value="<%= groupNumber %>">
<input type="hidden" name="fromDate" value="<%= fromDate %>">
<input type="hidden" name="toDate" value="<%= toDate %>">
<input type="hidden" name="groupName" value="<%= groupName %>">
<input type="hidden" name="selectedPDPK" value="<%= selectedPDPK %>">
<%-- ****************************** END Hidden Variables ****************************** --%>

</form>
</body>
</html>
