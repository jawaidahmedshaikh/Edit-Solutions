<!-- quoteCommitTaxesDialog.jsp //-->

<!-- ****** JAVA CODE ***** //-->

<%@ page  errorPage="/common/jsp/error.jsp" autoFlush="true" %>
<%@ page import="java.util.*, fission.beans.*,
                 edit.common.vo.CodeTableVO,
                 edit.common.CodeTableWrapper" %>

<jsp:useBean id="quoteTaxesSessionBean"
    class="fission.beans.SessionBean" scope="session"/>

<%
	String exchangeIndStatus = quoteTaxesSessionBean.getValue("exchangeIndStatus");
    String taxReportingGroup = quoteTaxesSessionBean.getValue("taxReportingGroup");
%>

<html>
<head>

<script language="Javascript1.2">

	var f = null;

	function init() {

		f = document.taxesForm;
	}

	function sendTransactionAction(transaction, action, target) {

		if (f.exchangeInd.checked == true) {
			f.exchangeIndStatus.value = "checked";
		}

		f.transaction.value = transaction;
		f.action.value = action;

		f.target = target;

		f.submit();
	}

	function saveTaxes() {

        sendTransactionAction("QuoteDetailTran", "saveTaxes", "contentIFrame");
		window.close();
	}

	function cancelTaxes() {

		window.close();
	}

</script>

<title>Taxes</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>

<body bgcolor="#DDDDDD" onLoad="init()" bgcolor="#DDDDDD">
<form name="taxesForm" method="post" action="/PORTAL/servlet/RequestManager">
  <table width="80%" border="0" cellspacing="0" cellpadding="0" bgcolor="#DDDDDD">
    <tr>
      <td nowrap align="left">
        <input type="checkbox" name="exchangeInd" <%= exchangeIndStatus %> >
        1035 Exchange
      </td>
    </tr>
    <tr>
      <td>
        &nbsp;
      </td>
    </tr>
    <tr>
      <td nowrap align="left">Tax Reporting Group:&nbsp;
        <input type="text" name="taxReportingGroup" value="<%= taxReportingGroup %>">
      </td>
    </tr>
    <tr>
      <td>
        &nbsp;
      </td>
    </tr>
    <tr>
      <td width="57%" align="right" nowrap>
        <input type="button" name="enter" value="Enter" onClick="saveTaxes()">
        <input type="button" name="enter" value="Cancel" onClick="cancelTaxes()">
      </td>
    </tr>
  </table>

     <!-- ****** HIDDEN FIELDS ***** //-->
  <input type="hidden" name="transaction" value="">
  <input type="hidden" name="action"      value="">
  <input type="hidden" name="nonQualifiedInitialPurchaseIndStatus" value="">
  <input type="hidden" name="exchangeIndStatus" value="">

</form>
</body>
</html>
