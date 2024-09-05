<%@ page  errorPage="/common/jsp/error.jsp" autoFlush="true" %>
<%@ page import="java.util.*,
                 edit.common.*,
                 edit.common.vo.*,
                 edit.common.vo.user.*,
                 fission.beans.*,
                 edit.portal.common.session.UserSession,
                 fission.utility.Util,
                 contract.ChangeHistory,
                 fission.utility.DateTimeUtil" %>

<jsp:useBean id="clientDetailSessionBean"
    class="fission.beans.SessionBean" scope="session"/>

<%
    ChangeHistory changeHistory = (ChangeHistory) request.getAttribute("changeHistory");

    String effectiveDate = "";
    String afterValue = "";
    String beforeValue = "";
    String fieldName = "";
    String dateTime = "";
    String operator = "";
    String processDate = "";
	String transactionType = "NF";
    String idName = "";

    if (changeHistory != null)
    {
        effectiveDate = DateTimeUtil.formatYYYYMMDDToMMDDYYYY(changeHistory.getEffectiveDate().getFormattedDate());
        afterValue = Util.initString(changeHistory.getAfterValue(), "");
        beforeValue = Util.initString(changeHistory.getBeforeValue(), "");

	    fieldName = changeHistory.getFieldName();
	    dateTime = changeHistory.getMaintDateTime().getFormattedDateTime();
	    operator = changeHistory.getOperator();
	    processDate = DateTimeUtil.formatYYYYMMDDToMMDDYYYY(changeHistory.getProcessDate().getFormattedDate());
        idName = changeHistory.getTableName().substring(7);
    }
%>

<html>

<script src="/PORTAL/common/javascript/commonJavascriptFunctions.js"></script>
<script src="/PORTAL/common/javascript/widgetFunctions.js"></script>
<script src="/PORTAL/common/javascript/scrollTable.js"></script>

<script language="Javascript1.2">

	var f = null;
    var transactionType = "<%= transactionType %>";

	function init()
    {
		f = document.contractHistoryForm;
		top.frames["main"].setActiveTab("historyTab");
    }

	function selectHistoryRow()
    {
       alert("select");
		var tdElement = window.event.srcElement;
		var trElement = tdElement.parentElement;

		var key = trElement.id;

		f.key.value = key;

		sendTransactionAction("ClientDetailTran", "showHistoryDetailSummary", "contentIFrame");
	}

    function onTableRowSingleClick(tableId)
    {
		sendTransactionAction("ClientDetailTran", "showHistoryDetailSummary", "contentIFrame");
	}

</script>
<head>
<title>Change History Detail</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta http-equiv="Cache-Control" content="no-store">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Expires" content="0">

<link href="/PORTAL/common/css/EDITSolutions.css" rel="stylesheet" type="text/css">
</head>

<body class="mainTheme" onLoad="init()" style="border-style:solid; border-width:1;">
<form name= "contractHistoryForm" method="post" action="/PORTAL/servlet/RequestManager">

<input type="hidden" name="page" value="">

  <table width="94%" border="0" cellspacing="8" cellpadding="0" height="35%">
    <tr>
      <td nowrap align="right">Id:&nbsp;</td>
      <td nowrap align="left" colspan="5">
        <input type="text" name="idName" size="20" value="<%= idName%>">
      </td>
    </tr>
    <tr>
      <td nowrap align="right">Effective Date:&nbsp;</td>
      <td nowrap align="left">
        <input disabled type="text" name="effectiveDate" size="10" maxlength="10" value="<%= effectiveDate%>">
      </td>
      <td nowrap align="right">Process Date:&nbsp;</td>
      <td nowrap align="left">
        <input type="text" name="processDate" size="10" maxlength="10" value="<%= processDate%>">
      </td>
      <td nowrap align="right">Transaction Type:&nbsp;</td>
      <td nowrap align="left">
        <input type="text" name="transactionType" value="<%= transactionType%>">
      </td>
    </tr>
    <tr>
      <td nowrap align="right">Field Name:&nbsp;</td>
      <td nowrap align="left">
        <input type="text" name="fieldName" size="20" value="<%= fieldName%>">
      <td nowrap align="right">Before Change Value:&nbsp;</td>
      <td nowrap align="left">
        <input type="text" name="beforeValue" size="20" value="<%= beforeValue%>">
      </td>
      <td nowrap align="right">After Change Value:&nbsp;</td>
      <td nowrap align="left">
        <input type="text" name="afterValue" size="20" value="<%= afterValue%>">
      </td>
    </tr>
    <tr>
      <td nowrap align="right">Operator:&nbsp;</td>
      <td nowrap align="left">
        <input type="text" name="operator" value="<%= operator%>">
      </td>
      <td nowrap align="right">Date/Time:&nbsp;</td>
      <td nowrap align="left" colspan="3">
        <input type="text" name="maintDate" value="<%= dateTime%>">
      </td>
    </tr>
  </table>

<%-- ****************************** BEGIN Summary Area ****************************** --%>

<jsp:include page="/common/jsp/widget/tableWidget.jsp" flush="true">
    <jsp:param name="tableId" value="ClientHistorySummaryTableModel"/>
    <jsp:param name="tableHeight" value="60"/>
    <jsp:param name="multipleRowSelect" value="false"/>
    <jsp:param name="singleOrDoubleClick" value="single"/>
</jsp:include>

<%-- ****************************** END Summary Area ****************************** --%>


<!-- ****** HIDDEN FIELDS ***** //-->
 <input type="hidden" name="transaction" value="">
 <input type="hidden" name="action"      value="">

 <input type="hidden" name="key" value="">

<!-- recordPRASEEvents is set by the toolbar when saving the client -->
    <input type="hidden" name="recordPRASEEvents" value="false">

</form>
</body>
</html>
