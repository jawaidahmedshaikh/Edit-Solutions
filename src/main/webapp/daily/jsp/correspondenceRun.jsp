<!-- ****** JAVA CODE ***** //-->

<%@ page  errorPage="/common/jsp/error.jsp" autoFlush="true" %>
<%@ page import="java.util.*, fission.beans.*" %>


<jsp:useBean id="correspondenceRunPageBean"
    class="fission.beans.PageBean" scope="request"/>

<%
	String[]  companyNames = correspondenceRunPageBean.getValues("companyNames");
%>

<html>
<head>

<script language="Javascript1.2">

	var f = null;

	function init() {

		f = document.correspondenceRunForm;
	}

	function sendTransactionAction(transaction, action, target) {

		f.transaction.value=transaction;
		f.action.value=action;

		f.target = target;

		f.submit();
	}

	function createCorrespondence() {

		sendTransactionAction("DailyDetailTran", "createCorrespondence", "main");
	}

	function bCancel() {

		sendTransactionAction("DailyDetailTran", "showDailySelection", "main");
	}

</script>

<title>Correspondence</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>

<body bgcolor="#DDDDDD" onLoad="init()">
<form name="correspondenceRunForm" method="post" action="/PORTAL/servlet/RequestManager">
  <table id="table1" name="banking" width="56%" border="0" cellspacing="0" cellpadding="0" height="110" bgcolor="#DDDDDD">
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
      <td width="26%">&nbsp;</td>
      <td width="9%">&nbsp;</td>
      <td width="65%" align="right" nowrap>
        <input type="button" name="enter" value=" Enter " onClick="createCorrespondence()">
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