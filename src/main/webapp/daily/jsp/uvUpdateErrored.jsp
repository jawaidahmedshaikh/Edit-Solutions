<html>
<head>

<script language="Javascript1.2">

	var f = null;

	function init() {

		f = document.uvUpdateErroredForm;
	}

	function sendTransactionAction(transaction, action, target) {

		f.transaction.value=transaction;
		f.action.value=action;

		f.target = target;

		f.submit();
	}

	function bCancel() {

		sendTransactionAction("DailyDetailTran", "showDailySelection", "main");
	}

</script>

<title>Unit Value Update Errored</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>

<h1>Unit Value Update Completed With Errors - Check Logs For Details</h1>
<body bgcolor="#FFFFFF" onLoad="init()">
<form name="uvUpdateErroredForm" method="post" action="/PORTAL/servlet/RequestManager">
  <table id="table1" name="uvUpdate" width="56%" border="0" cellspacing="0" cellpadding="0" bgcolor="#FFFFFF">
    <tr>
      <td width="26%">&nbsp;</td>
      <td width="9%">&nbsp;</td>
      <td width="42%" align="right" nowrap>
        <input type="button" name="cancel" value="Cancel" onClick="bCancel()">
      </td>
    </tr>
  </table>

<!-- ****** HIDDEN FIELDS ***** //-->
  <input type="hidden" name="transaction" value="">
  <input type="hidden" name="action" value="">

</form>
</body>
</html>



