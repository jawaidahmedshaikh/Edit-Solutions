<!-- ****** JAVA CODE ***** //-->

<%@ page  errorPage="/common/jsp/error.jsp" autoFlush="true" %>
<%@ page import="java.util.*, fission.beans.*" %>

<html>
<head>

<script language="Javascript1.2">

	var f = null;

	function init()
    {
		f = document.accountingExtractForm;
	}

	function sendTransactionAction(transaction, action, target)
    {
		f.transaction.value=transaction;
		f.action.value=action;

		f.target = target;

		f.submit();
	}

	function runAccountingExtract()
    {
		sendTransactionAction("ReportingDetailTran", "runAccountingExtract", "main");
	}

	function closeAccountingExtractParams()
    {
		sendTransactionAction("ReportingDetailTran", "showReportingMain", "main");
	}

</script>

<title>Accounting Extract Parameters</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>

<body bgcolor="#DDDDDD" onLoad="init()">
<form name="accountingExtractForm" method="post" action="/PORTAL/servlet/RequestManager">
  <table name="taxes" width="56%" border="0" cellspacing="0" cellpadding="0" height="110" bgcolor="#DDDDDD">
    <tr>
      <td nowrap align="right">Accounting Period(mm/yyyy):&nbsp;</td>
      <td nowrap align="left">
        <input type="text" name="accountingPeriodMonth" maxlength="2" size="2">/
        <input type="text" name="accountingPeriodYear" maxlength="4" size="4">
      </td>
    </tr>
    <tr>
      <td nowrap colspan="4">&nbsp;</td>
      <td nowrap align="right">
        <input type="button" name="enter" value=" Enter " onClick="runAccountingExtract()">
        <input type="button" name="cancel" value="Cancel" onClick="closeAccountingExtractParams()">
      </td>
    </tr>
  </table>


<!-- ****** HIDDEN FIELDS ***** //-->
  <input type="hidden" name="transaction" value="">
  <input type="hidden" name="action" value="">

</form>
</body>
</html>
