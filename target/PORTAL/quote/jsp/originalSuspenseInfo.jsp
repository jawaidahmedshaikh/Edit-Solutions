<%
    String originalAmount = (String) request.getAttribute("originalAmount");
    String originalMemoCode = (String) request.getAttribute("originalMemoCode");
    String originalContractNumber = (String) request.getAttribute("originalContractNumber");
%>
<html>
<head>
<title>Original Information</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">

<script src="/PORTAL/common/javascript/commonJavascriptFunctions.js"></script>

<script language="Javascript1.2">

	var f = null;

	function init()
    {
		f = document.originalInformationForm;

        formatCurrency();
	}
</script>
</head>
<body class="mainTheme" bgcolor="#DDDDDD" onLoad="init()">
<form name="originalInformationForm" method="post" action="/PORTAL/servlet/RequestManager">
  <table id="table1" width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      <td nowrap width="5%" align="right">Original Contract Number:&nbsp;
        <input type="text" name="originalContractNumber" size="15" maxlength="15" disabled value="<%= originalContractNumber %>">
      </td>
    </tr>
    <tr>
      <td nowrap width="5%" align="right">Original Amount:&nbsp;
        <input type="text" name="originalAmount" size="15" maxlength="15" disabled value="<%= originalAmount %>" CURRENCY>
      </td>
    </tr>
    <tr>
      <td nowrap width="5%" align="right">Original Memo Code:&nbsp;
        <input type="text" name="originalMemoCode" size="15" maxlength="15" disabled value="<%= originalMemoCode %>">
      </td>
    </tr>
    <tr>
      <td nowrap width="5%" align="right">&nbsp;
        <input type="button" name="enter" value="Enter" onClick="closeWindow()">
      </td>
    </tr>
  </table>
</form>
</body>
</html>
