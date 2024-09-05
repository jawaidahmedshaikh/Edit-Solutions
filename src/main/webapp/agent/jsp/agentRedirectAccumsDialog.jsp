<!-- agentRedirectAccumsDialog.jsp //-->

<!-- ****** JAVA CODE ***** //-->

<%@ page  errorPage="/common/jsp/error.jsp" autoFlush="true" %>
<%@ page import="java.util.*, fission.beans.*,
                 edit.common.CodeTableWrapper,
                 edit.common.vo.*,
                 fission.utility.Util" %>

<%
    String currentRedirect = "";
    String ytdRedirect = "";
    String cumulativeRedirect = "";
    String heldRedirect = "";
%>

<html>
<head>

<script language="Javascript1.2">

	var f = null;

	function init() {

		f = document.redirectAccumsForm;
	}

	function sendTransactionAction(transaction, action, target) {

		f.transaction.value = transaction;
		f.action.value = action;

		f.target = target;

		f.submit();
	}

	function closeRedirectAccumsDialog() {

		window.close();
	}

</script>

<title>Redirect Accumulations</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>

<body class="dialog" onLoad="init()" bgcolor="#DDDDDD">
<form name="redirectAccumsForm" method="post" action="/PORTAL/servlet/RequestManager">
  <table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0" bgcolor="#DDDDDD">
    <tr>
      <td nowrap align="left">Current:&nbsp;
        <input type="text" name="currentRedirect" maxlength="11" size="11" value="<%= currentRedirect %>">
      </td>
    </tr>
    <tr>
      <td nowrap align="left">YTD:&nbsp;
        <input type="text" name="ytdRedirect" maxlength="11" size="11" value="<%= ytdRedirect %>">
      </td>
    </tr>
    <tr>
      <td nowrap align="left">Cumulative:&nbsp;
        <input type="text" name="cumulativeRedirect" maxlength="11" size="11" value="<%= cumulativeRedirect %>">
      </td>
    </tr>
    <tr>
      <td nowrap align="left">Held:&nbsp;
        <input type="text" name="heldRedirect" maxlength="11" size="11" value="<%= heldRedirect %>">
      </td>
    </tr>
    <tr>
      <td width="57%" align="right" nowrap>
        <input type="button" name="close" value="Close" onClick="closeRedirectAccumsDialog()">
      </td>
    </tr>
  </table>

     <!-- ****** HIDDEN FIELDS ***** //-->
  <input type="hidden" name="transaction" value="">
  <input type="hidden" name="action"      value="">

</form>
</body>
</html>
