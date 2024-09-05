<!-- ****** JAVA CODE ***** //-->

<%@ page  errorPage="/common/jsp/error.jsp" autoFlush="true" %>
<%@ page import="java.util.*, fission.beans.*,
                 edit.common.vo.*" %>

<%
    String message = (String) request.getAttribute("message");
%>

<html>
<head>

<script language="Javascript1.2">

	var f = null;

    var message = "<%= message %>";

	function init() {

		f = document.theForm;

        if (message != "null")
        {
            alert(message);
        }
	}

	function sendTransactionAction(transaction, action, target) {

		f.transaction.value=transaction;
		f.action.value=action;

		f.target = target;

		f.submit();
	}

	function runYearEndClientBalance() {

		sendTransactionAction("ReportingDetailTran", "runYearEndClientBalance", "main");
	}

    function showReportingMain()
    {
		sendTransactionAction("ReportingDetailTran", "showReportingMain", "main");
    }

</script>

<title>Year-End Client Balance</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>

<body bgcolor="#DDDDDD" onLoad="init()">
<form name="theForm" method="post" action="/PORTAL/servlet/RequestManager">
  <table id="table1" name="reporting" width="56%" border="0" cellspacing="0" cellpadding="0" height="110" bgcolor="#DDDDDD">

    <tr>
      <td width="26%">&nbsp;</td>
      <td width="9%">&nbsp;</td>
      <td width="65%" align="right" nowrap>
        <input type="button" name="enter" value=" Run " onClick="runYearEndClientBalance()">
        <input type="button" name="cancel" value="Cancel" onClick="showReportingMain()">
      </td>
    </tr>
  </table>

<!-- ****** HIDDEN FIELDS ***** //-->
  <input type="hidden" name="transaction" value="">
  <input type="hidden" name="action" value="">

</form>
</body>
</html>