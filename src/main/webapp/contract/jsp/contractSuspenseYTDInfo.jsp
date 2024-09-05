<html>
<head>
<title>Year-to-Date Information</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<script language="Javascript1.2">

	var f = null;

	function init() {

		f = document.ytdInformationForm;

		f.totalDisbursements.value	 = opener.f.totalDisbursements.value;
		f.fedWithholdingYTD.value	 = opener.f.fedWithholdingYTD.value;
		f.stateWithholdingYTD.value	 = opener.f.stateWithholdingYTD.value;
        f.countyWithholdingYTD.value = opener.f.countyWithholdingYTD.value;
        f.cityWithholdingYTD.value   = opener.f.cityWithholdingYTD.value;
	}

	function enterDialog() {

		window.close();
	}


</script>
</head>
<body  bgcolor="#DDDDDD" onLoad="init()">
<form name="ytdInformationForm" method="post" action="/PORTAL/servlet/RequestManager">
  <table id="table1" width="100%" border="0" cellspacing="0" cellpadding="0" bgcolor="#DDDDDD">
    <tr>
      <td nowrap width="5%" align="right">Total Disbursements:&nbsp;
        <input type="text" name="totalDisbursements" size="15" maxlength="15" disabled>
      </td>
    </tr>
    <tr>
      <td nowrap width="5%" align="right">Federal Withholding:&nbsp;
        <input type="text" name="fedWithholdingYTD" size="15" maxlength="15" disabled>
      </td>
    </tr>
    <tr>
      <td nowrap width="5%" align="right">State Withholding:&nbsp;
        <input type="text" name="stateWithholdingYTD" size="15" maxlength="15" disabled>
      </td>
    </tr>
    <tr>
      <td nowrap width="5%" align="right">County Withholding:&nbsp;
        <input type="text" name="countyWithholdingYTD" size="15" maxlength="15" disabled>
      </td>
    </tr>
    <tr>
      <td nowrap width="5%" align="right">City Withholding:&nbsp;
        <input type="text" name="cityWithholdingYTD" size="15" maxlength="15" disabled>
      </td>
    </tr>
    <tr>
      <td nowrap width="5%" align="right">&nbsp;
        <input type="button" name="enter" value="Enter" onClick="enterDialog()">
      </td>
    </tr>
  </table>
</form>
</body>
</html>
