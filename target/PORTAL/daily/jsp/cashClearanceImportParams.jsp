<!-- ****** JAVA CODE ***** //-->

<%@ page  errorPage="/common/jsp/error.jsp" autoFlush="true" %>
<%@ page import="java.util.*" %>

<html>
<head>

<script language="Javascript1.2">

	var f = null;

	function init()
    {
		f = document.cashClearanceImportForm;
	}

	function sendTransactionAction(transaction, action, target)
    {
		f.transaction.value=transaction;
		f.action.value=action;
		f.target = target;

		f.submit();
	}

	function importCashClearance()
    {
		sendTransactionAction("DailyDetailTran", "importCashClearance", "main");
	}

	function cancelCashClearance()
    {
		sendTransactionAction("DailyDetailTran", "showDailySelection", "main");
	}

</script>

<title>EDITSolutions - Cash Clearance Import Date</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>

<body bgcolor="#DDDDDD" onLoad="init()">
<form name="cashClearanceImportForm" method="post" action="/PORTAL/servlet/RequestManager">
  <table id="table1" name="accounting" width="56%" border="0" cellspacing="0" cellpadding="0" bgcolor="#DDDDDD">
    <tr>
      <td nowrap align="right">Cash Clearance Import Date:&nbsp;</td>
      <td nowrap align="left">
        <input type="text" name="ccImportMonth" maxlength="2" size="2">/
        <input type="text" name="ccImportDay" maxlength="2" size="2">/
        <input type="text" name="ccImportYear" maxlength="4" size="4">
      </td>
    </tr>
    <tr>
      <td nowrap colspan="2">&nbsp;</td>
    </tr>
    <tr>
      <td nowrap>&nbsp;</td>
      <td nowrap align="right">
        <input type="button" name="enter" value=" Enter " onClick="importCashClearance()">
        <input type="button" name="cancel" value=" Cancel " onClick="cancelCashClearance()">
      </td>
    </tr>
  </table>

<!-- ****** HIDDEN FIELDS ***** //-->
  <input type="hidden" name="transaction" value="">
  <input type="hidden" name="action" value="">

</form>
</body>
</html>