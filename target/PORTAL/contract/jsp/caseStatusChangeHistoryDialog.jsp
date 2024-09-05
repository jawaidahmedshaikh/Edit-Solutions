<!-- ****** JAVA CODE ***** //-->

<%@ page  errorPage="/common/jsp/error.jsp" autoFlush="true" %>
<html>

<script src="/PORTAL/common/javascript/commonJavascriptFunctions.js"></script>

<script language="JavaScript1.2">

var f = null;

function init()
{
    f = document.statusChangeHistoryForm;
}

</script>

<head>

<title>Case Status Change History</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta http-equiv="Cache-Control" content="no-store">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Expires" content="0">

<link href="/PORTAL/common/css/EDITSolutions.css" rel="stylesheet" type="text/css">
</head>

<script src="/PORTAL/common/javascript/widgetFunctions.js"></script>
<script src="/PORTAL/common/javascript/scrollTable.js"></script>
<script src="/PORTAL/common/javascript/commonJavascriptFunctions.js"></script>

<!-- ****** HTML CODE ***** //-->

<body class="dialog" onLoad="init()" style="border-style:solid; border-width:1;">
<form name="statusChangeHistoryForm" method="post" action="/PORTAL/servlet/RequestManager">

<jsp:include page="/common/jsp/widget/tableWidget.jsp" flush="true">
    <jsp:param name="tableId" value="CaseStatusChangeHistoryTableModel"/>
    <jsp:param name="tableHeight" value="60"/>
    <jsp:param name="multipleRowSelect" value="false"/>
    <jsp:param name="singleOrDoubleClick" value="none"/>
</jsp:include>

<table id="closeDialog" width="100%" height="2%" border="0" cellspacing="0" cellpadding="0">
<tr>
  <td colspan="8" nowrap align="right">
    <input type="button" name="close" value="Close" onClick="window.close()">
  </td>
</tr>
</table>

<!-- ****** HIDDEN FIELDS ***** //-->
<input type="hidden" name="transaction" value="">
<input type="hidden" name="action"      value="">

</body>
</form>
</html>
