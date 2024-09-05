<%
    String selectedDepositsPK = (String) request.getAttribute("selectedDepositsPK");
    String suspenseFK = (String) request.getAttribute("suspenseFK");
%>

<html>

<head>
<title>Delete Deposit</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<script language="Javascript1.2">

	var f = null;

	function init() {

		f = document.theForm;;
	}

    function sendTransactionAction(transaction, action, target) {

        f.transaction.value= transaction;
        f.action.value= action;

        f.target = target;

        f.submit();
    }

    function deleteDeposit(){

        sendTransactionAction("QuoteDetailTran", "deleteSelectedDeposit", "depositDialog");

        window.close();
    }

	function cancelDepositDelete() {

		window.close();
	}

</script>
</head>
<body bgcolor="#DDDDDD" onLoad="init()">
<form name="theForm" method="post" action="/PORTAL/servlet/RequestManager">
  <table id="table1" width="43%" border="0" cellspacing="0" cellpadding="8" bgcolor="#DDDDDD">

    <tr>
      <td colspan="3" nowrap>

        Deposit Will Be Permanently Deleted.  Continue?
      </td>
    </tr>
    <tr>
      <td>&nbsp;&nbsp;</td>
      <td colspan="2" align="right" nowrap>
        <input type="button" name="enter" value="Yes" onClick="deleteDeposit()">
        <input type="button" name="cancel" value="No" onClick="cancelDepositDelete()">
      </td>
    </tr>
  </table>

  <input type="hidden" name="transaction" value="">
  <input type="hidden" name="action" value="">
  <input type="hidden" name="selectedDepositsPK" value="<%= selectedDepositsPK %>">
  <input type="hidden" name="suspenseFK" value="<%= suspenseFK %>">
</form>
</body>
</html>
