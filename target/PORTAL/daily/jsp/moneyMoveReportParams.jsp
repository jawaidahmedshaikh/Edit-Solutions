<!-- ****** JAVA CODE ***** //-->

<%@ page  errorPage="/common/jsp/error.jsp" autoFlush="true" %>
<%@ page import="java.util.*, fission.beans.*,
                 edit.common.vo.FundVO,
                 edit.common.vo.CodeTableVO,
                 edit.common.CodeTableWrapper" %>


<html>
<head>

<script src="/PORTAL/common/javascript/commonJavascriptFunctions.js"></script>

<script language="Javascript1.2">

	var f = null;

	function init()
    {
		f = document.moneyMoveReportParamForm;
	}

	function runMoneyMoveReport()
    {
        if (f.month.value == "" ||
            f.day.value == "" ||
            f.year.value == "")
        {
            alert("Please Enter Date for Report");
        }
        else
        {
            sendTransactionAction("DailyDetailTran", "runMoneyMoveReport", "main");
        }
	}

	function bCancel()
    {
		sendTransactionAction("DailyDetailTran", "showDailySelection", "main");
	}
</script>

<title>Money Move Report Params</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>

<body bgcolor="#DDDDDD" onLoad="init()">
<form name="moneyMoveReportParamForm" method="post" action="/PORTAL/servlet/RequestManager">
  <table id="table1" name="banking" width="56%" border="0" cellspacing="0" cellpadding="0" height="110" bgcolor="#DDDDDD">
    <tr>
      <td width="26%" nowrap align="right">Date:&nbsp;</td>
      <td width="9%" nowrap align="left">
        <input type="text" name="month" maxlength="2" size="2">
        /
        <input type="text" name="day" maxlength="2" size="2">
        /
        <input type="text" name="year" maxlength="4" size="4">
      </td>
    </tr>
    <tr>
      <td width="100%" colspan="4">&nbsp;</td>
    </tr>
    <tr>
      <td width="26%">&nbsp;</td>
      <td width="9%">&nbsp;</td>
      <td width="30%">&nbsp;</td>
      <td width="35%" align="right" nowrap>
        <input type="button" name="enter" value=" Enter " onClick="runMoneyMoveReport()">
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