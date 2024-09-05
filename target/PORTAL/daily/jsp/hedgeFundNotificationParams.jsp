<!-- ****** JAVA CODE ***** //-->

<%@ page  errorPage="/common/jsp/error.jsp" autoFlush="true" %>
<%@ page import="java.util.*, fission.beans.*" %>


<jsp:useBean id="accountingRunPageBean"
    class="fission.beans.PageBean" scope="request"/>

<html>
<head>

<script language="Javascript1.2">

	var f = null;

	function init() {

		f = document.hedgeFundNotifyParamsForm;
	}

	function sendTransactionAction(transaction, action, target) {

		f.transaction.value=transaction;
		f.action.value=action;

		f.target = target;

		f.submit();
	}

	function runHedgeFundNotification() {

		sendTransactionAction("DailyDetailTran", "runHedgeFundNotification", "main");
	}

	function bCancel() {

		sendTransactionAction("DailyDetailTran", "showDailySelection", "main");
	}

</script>

<title>EDITSolutions - Hedge Fund Notification</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>

<body bgcolor="#DDDDDD" onLoad="init()">
<form name="hedgeFundNotifyParamsForm" method="post" action="/PORTAL/servlet/RequestManager">
  <table id="table1" name="accounting" width="56%" border="0" cellspacing="0" cellpadding="0" bgcolor="#DDDDDD">
    <tr>
      <td width="26%" height="24" nowrap>Notification Date:</td>
      <td width="9%" height="24" nowrap>
        <input type="text" name="notifyMonth" maxlength="2" size="2">/
        <input type="text" name="notifyDay" maxlength="2" size="2">/
        <input type="text" name="notifyYear" maxlength="4" size="4">
      </td>
      <td width="65%" height="24">&nbsp;</td>
    </tr>
    <tr>
      <td width="26%">&nbsp;</td>
      <td width="9%">&nbsp;</td>
      <td width="42%" align="right" nowrap>
        <input type="button" name="enter" value=" Enter " onClick="runHedgeFundNotification()">
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