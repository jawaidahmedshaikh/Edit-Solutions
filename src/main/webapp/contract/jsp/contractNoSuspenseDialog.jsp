
<!-- ****** JAVA CODE ***** //-->

<%@ page  errorPage="/common/jsp/error.jsp" autoFlush="true" %>
<%@ page import="java.util.*,
                 edit.common.*,
                 edit.common.vo.*,
                 fission.beans.*" %>

<jsp:useBean id="formBean"
    class="fission.beans.PageBean" scope="request"/>

<%
    String scrollingTrxPageSize = (String) request.getAttribute("scrollingTrxPageSize");
    String beginScrollingTrxPK = (String) request.getAttribute("beginScrollingTrxPK");
    String endScrollingTrxPK = (String) request.getAttribute("endScrollingTrxPK");
%>


<html>
<head>

<script language="Javascript1.2">

	var f = null;

	function init() {

		f = document.noSuspenseForm;
	}

	function sendTransactionAction(transaction, action, target) {

		f.transaction.value=transaction;
		f.action.value=action;

		f.target = target;

		f.submit();
	}

	function bCancel() {

		sendTransactionAction("ContractDetailTran", "redisplayTranPayment", "contentIFrame");
        window.close();
	}

</script>

<title>No Money In Suspense</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>

<h1>There is no money in suspense for this contract</h1>
<body bgcolor="#FFFFFF" onLoad="init()">
<form name="noSuspenseForm" method="post" action="/PORTAL/servlet/RequestManager">
  <table id="table1" name="accounting" width="56%" border="0" cellspacing="0" cellpadding="0" bgcolor="#FFFFFF">
    <tr>
      <td width="26%">&nbsp;</td>
      <td width="9%">&nbsp;</td>
      <td width="65%" align="right" nowrap>
        <input type="button" name="cancel" value="Cancel" onClick="bCancel()">
      </td>
    </tr>
  </table>

<!-- ****** HIDDEN FIELDS ***** //-->
  <input type="hidden" name="transaction" value="">
  <input type="hidden" name="action" value="">
  <input type="hidden" name="scrollingTrxPageSize" value="<%= scrollingTrxPageSize %>">
  <input type="hidden" name="beginScrollingTrxPK" value="<%= beginScrollingTrxPK %>">
  <input type="hidden" name="endScrollingTrxPK" value="<%= endScrollingTrxPK %>">


</form>
</body>
</html>


