<!-- ****** JAVA CODE ***** //-->

<%@ page  errorPage="/common/jsp/error.jsp" autoFlush="true" %>
<%@ page import="java.util.*, fission.beans.*" %>


<jsp:useBean id="reservesPageBean"
    class="fission.beans.PageBean" scope="request"/>

<%
	String[]  companyNames = reservesPageBean.getValues("companyNames");
%>

<html>
<head>

<script language="Javascript1.2">

	var f = null;

	function init() {

		f = document.reservesRunForm;
	}

	function sendTransactionAction(transaction, action, target) {

		f.transaction.value=transaction;
		f.action.value=action;

		f.target = target;

		f.submit();
	}

	function processReserves() {

		sendTransactionAction("ReportingDetailTran", "processReserves", "main");
	}

	function closeReserves() {

		sendTransactionAction("ReportingDetailTran", "showReportingMain", "main");
	}

</script>

<title>Untitled Document</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>

<body bgcolor="#DDDDDD" onLoad="init()">
<form name="reservesRunForm" method="post" action="/PORTAL/servlet/RequestManager">
  <table id="table1" name="reporting" width="56%" border="0" cellspacing="0" cellpadding="0" height="110" bgcolor="#DDDDDD">
    <tr>
      <td width="26%" nowrap>Company Key:</td>
      <td width="9%" nowrap>
      <select name="companyName">
      <option selected value="Please Select">
         Please Select
      </option>
	<option> All </option>

      <%
      	for(int i = 0; i < companyNames.length; i++) {

			out.println("<option name=\"companyName\">" + companyNames[i] + "</option>");
      	}
      %>
      </select>
      </td>
      <td width="65%">&nbsp;</td>
    </tr>
    <tr>
      <td width="26%" height="24" nowrap>Process Date:</td>
      <td width="9%" height="24" nowrap>
        <input type="text" name="effectiveMonth" maxlength="2" size="2">/
        <input type="text" name="effectiveDay" maxlength="2" size="2">/
        <input type="text" name="effectiveYear" maxlength="4" size="4">
      </td>
      <td width="65%" height="24">&nbsp;</td>
    </tr>
    <tr>
      <td width="26%">&nbsp;</td>
      <td width="9%">&nbsp;</td>
      <td width="65%" align="right" nowrap>
        <input type="button" name="enter" value=" Enter " onClick="processReserves()">
        <input type="button" name="cancel" value="Cancel" onClick="closeReserves()">
      </td>
    </tr>
  </table>

<!-- ****** HIDDEN FIELDS ***** //-->
  <input type="hidden" name="transaction" value="">
  <input type="hidden" name="action" value="">

</form>
</body>
</html>