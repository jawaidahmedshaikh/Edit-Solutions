<!-- confirmQuoteDeleteDialog.jsp //-->

<!-- ****** JAVA CODE ***** //-->

<%@ page  errorPage="/common/jsp/error.jsp" autoFlush="true" %>
<%@ page import="java.util.*, fission.beans.*" %>

<jsp:useBean id="formBean"
    class="fission.beans.PageBean" scope="request"/>

<jsp:useBean id="quoteMainSessionBean"
    class="fission.beans.SessionBean" scope="session"/>

<html>
<head>

<script language="Javascript1.2">

	var f = null;

	function init() {

		f = document.confirmQuoteDeleteForm;
	}

 	function sendTransactionAction(transaction, action, target) {

		f.transaction.value=transaction;
		f.action.value=action;
		f.target = target;

		f.submit();
	}

	function cancelDialog() {

		window.close();
	}

	function deleteQuote() {

        sendTransactionAction("QuoteDetailTran", "deleteQuote", "contentIFrame");

		window.close();
	}

</script>

<title>Confirm Quote Deletion</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>

<body bgcolor="#DDDDDD" onLoad="init()">
<form name="confirmQuoteDeleteForm" method="post" action="/PORTAL/servlet/RequestManager">
  <table id="table1" name="confirmDelete" width="56%" border="0" cellspacing="0" cellpadding="0" bgcolor="#FFFFFF">
    <tr bgcolor="#DDDDDD">
      <td align="left" nowrap>
        Are you sure you want to delete this quote?
      </td>
    </tr>
    <tr bgcolor="#DDDDDD">
      <td align="left" nowrap>
        &nbsp;
      </td>
    </tr>
    <tr bgcolor="#DDDDDD">
      <td align="right" valign="bottom" nowrap>
        <input type="button" name="delete" value="Delete" onClick="deleteQuote()">
        <input type="button" name="cancel" value="Cancel" onClick="cancelDialog()">
      </td>
    </tr>
  </table>

     <!-- ****** HIDDEN FIELDS ***** //-->
  <input type="hidden" name="transaction" value="">
  <input type="hidden" name="action"      value="">

</form>
</body>
</html>
