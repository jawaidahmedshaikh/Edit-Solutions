<!-- contractCommitWithholdingDialog.jsp //-->

<!-- ****** JAVA CODE ***** //-->

<%@ page  errorPage="/common/jsp/error.jsp" autoFlush="true" %>
<%@ page import="java.util.*, fission.beans.*" %>

<html>
<head>

<script language="Javascript1.2">

	var f = null;

	function init() {

		f = document.accumulationForm;

		f.amtPaidToDate.value        = opener.f.amtPaidToDate.value;
 		f.amtPaidYTD.value           = opener.f.amtPaidYTD.value;
 		f.fedWithholdingYTD.value    = opener.f.fedWithholdingYTD.value;
 		f.stateWithholdingYTD.value  = opener.f.stateWithholdingYTD.value;
		f.cityWithholdingYTD.value   = opener.f.cityWithholdingYTD.value;
 		f.countyWithholdingYTD.value = opener.f.countyWithholdingYTD.value;

		window.resizeTo(getPreferredWidth(), getPreferredHeight());
	}

	function getPreferredWidth() {

		return 1.50 * document.all.table1.offsetWidth;
	}

	function getPreferredHeight() {

		return 1.25 * document.all.table1.offsetHeight;
	}

	function openDialog(theURL,winName,features,transaction,action) {

	  dialog = window.open(theURL,winName,features);

	  sendTransactionAction(transaction, action, winName);
	}

	function sendTransactionAction(transaction, action, target) {

		f.transaction.value=transaction;
		f.action.value=action;

		f.target = target;

		f.submit();
	}

	function bCancel() {

		window.close();
	}

</script>

<title>Payee Accumulations</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>

<body bgcolor="#DDDDDD" onLoad="init()">
<form name="accumulationForm" method="post" action="/PORTAL/servlet/RequestManager">
  <table id="table1" width="80%" cellspacing="0" cellpadding="0" bgcolor="#DDDDDD">
    <tr>
      <td nowrap align="right">
        <p>Amount Paid-To-Date:
          <input type="text" name="amtPaidToDate" disabled maxlength="15" size="15">
        </p>
      </td>
    </tr>
    <tr>
      <td nowrap>&nbsp;</td>
    </tr>
    <tr>
      <td nowrap align="right">
        <p>Amount Paid Yr-To-Date:
          <input type="text" name="amtPaidYTD" disabled maxlength="15" size="15">
        </p>
      </td>
    </tr>
    <tr>
      <td nowrap>&nbsp;</td>
    </tr>
    <tr>
      <td nowrap align="right">
        <p>Federal W/H Yr-To-Date:
          <input type="text" name="fedWithholdingYTD" disabled maxlength="15" size="15">
        </p>
      </td>
    </tr>
    <tr>
      <td nowrap>&nbsp;</td>
    </tr>
    <tr>
      <td nowrap align="right">
        <p>State W/H Yr-To-Date:
          <input type="text" name="stateWithholdingYTD" disabled maxlength="15" size="15">
        </p>
      </td>
    </tr>
    <tr>
      <td nowrap>&nbsp;</td>
    </tr>
    <tr>
      <td nowrap align="right">
        <p>County W/H Yr-To-Date:
          <input type="text" name="countyWithholdingYTD" disabled maxlength="15" size="15">
        </p>
      </td>
    </tr>
    <tr>
      <td nowrap>&nbsp;</td>
    </tr>
    <tr>
      <td nowrap align="right">
        <p>City W/H Yr-To-Date:
          <input type="text" name="cityWithholdingYTD" disabled maxlength="15" size="15">
        </p>
      </td>
    </tr>
    <tr>
      <td nowrap>&nbsp;</td>
    </tr>
    <tr align="right">
      <td nowrap>
        <input type="button" name="cancel" value="Cancel" onClick="bCancel()">
      </td>
    </tr>
  </table>

     <!-- ****** HIDDEN FIELDS ***** //-->
  <input type="hidden" name="transaction" value="">
  <input type="hidden" name="action"      value="">

</form>
</body>
</html>
