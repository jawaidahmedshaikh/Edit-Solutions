<%
    String reportMessage = (String) request.getAttribute("ReportMessage");
%>
<html>
<head>

<script src="/PORTAL/common/javascript/commonJavascriptFunctions.js"></script>

<script language="Javascript1.2">

	var f = null;

	function init()
    {
		f = document.sepAcctValByDivErrorForm;
	}

	function bCancel()
    {
		sendTransactionAction("DailyDetailTran", "showDailySelection", "main");
	}

</script>

<title>Separate Account Investment Values By Division Report - Error</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>

<h1><%= reportMessage %></h1>
<body bgcolor="#FFFFFF" onLoad="init()">
<form name="sepAcctValByDivErrorForm" method="post" action="/PORTAL/servlet/RequestManager">
  <table id="table1" name="accounting" width="56%" border="0" cellspacing="0" cellpadding="0" bgcolor="#FFFFFF">
    <tr>
      <td width="26%">&nbsp;</td>
      <td width="9%">&nbsp;</td>
      <td width="65%" align="right" nowrap>
        <input type="button" name="close" value="Close" onClick="bCancel()">
      </td>
    </tr>
  </table>

<!-- ****** HIDDEN FIELDS ***** //-->
  <input type="hidden" name="transaction" value="">
  <input type="hidden" name="action" value="">

</form>
</body>
</html>



