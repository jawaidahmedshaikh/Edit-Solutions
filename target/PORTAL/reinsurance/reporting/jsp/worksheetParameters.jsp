<!-- ****** JAVA CODE ***** //-->

<%@ taglib uri="/WEB-INF/taglibs-input.tld" prefix="input" %>
<%@ page  errorPage="/common/jsp/error.jsp" autoFlush="true" %>

<html>
<head>

<title>Worksheet Parameters</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta http-equiv="Cache-Control" content="no-store">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Expires" content="0">
<link href="/PORTAL/common/css/EDITSolutions.css" rel="stylesheet" type="text/css">

<script src="/PORTAL/common/javascript/commonJavascriptFunctions.js"></script>
<script src="/PORTAL/common/javascript/formatDateFunctions.js" type="text/javascript"></script>
<script src="/PORTAL/common/javascript/calendarSelector.js" type="text/javascript"></script>
<script language="Javascript1.2">

	var f = null;

	function init() {

		f = document.worksheetParametersForm;

	}

	function sendTransactionAction(transaction, action, target) {

		f.transaction.value=transaction;
		f.action.value=action;

		f.target = target;

		f.submit();

	}

	function runWorksheet()
    {
		sendTransactionAction("ReportingDetailTran", "runWorksheet", "main");
	}

	function bCancel()
    {
		sendTransactionAction("ReportingDetailTran", "showReportingMain", "main");
	}


</script>
</head>

<body bgcolor="#DDDDDD" onLoad="init()">
<form name="worksheetParametersForm" method="post" action="/PORTAL/servlet/RequestManager">
<span id="mainContent" style="border-style:solid; border-width:0;  position:relative; width:100%; height:70%; top:0; left:0; z-index:0; overflow:visible">
  <table name="batch" width="56%" border="0" cellspacing="0" cellpadding="0" bgcolor="#DDDDDD">
    <tr>
      <td nowrap align="right">Batch ID:&nbsp;</td>
      <td nowrap align="left">
        <input type="text" name="batchId" maxlength="30" size="30">
      </td>
    </tr>
    <tr>
      <td colspan="3" align="right">
        <input type="button" name="enter" value=" Enter " onClick="runWorksheet()">
        <input type="button" name="cancel" value="Cancel"  onClick="bCancel()">
      </td>
    </tr>
  </table>


<!-- ****** HIDDEN FIELDS ***** //-->
  <input type="hidden" name="transaction" value="">
  <input type="hidden" name="action" value="">

</form>
</body>
</html>
