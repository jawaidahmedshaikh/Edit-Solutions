<!-- agentAdjustAccumsDialog.jsp //-->

<!-- ****** JAVA CODE ***** //-->

<%@ page  errorPage="/common/jsp/error.jsp" autoFlush="true" %>
<%@ page import="java.util.*, fission.beans.*,
                 edit.common.CodeTableWrapper,
                 edit.common.vo.*,
                 fission.utility.Util" %>

<%
    String currentAdj = "";
    String ytdAdj = "";
    String cumulativeAdj = "";
%>

<html>
<head>

<script language="Javascript1.2">

	var f = null;

	function init() {

		f = document.adjustmentAccumsForm;
	}

	function sendTransactionAction(transaction, action, target) {

		f.transaction.value = transaction;
		f.action.value = action;

		f.target = target;

		f.submit();
	}

	function closeAdjustAccumsDialog() {

		window.close();
	}

</script>

<title>Adjustment Accumulations</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>

<body class="dialog" onLoad="init()" bgcolor="#DDDDDD">
<form name="adjustmentAccumsForm" method="post" action="/PORTAL/servlet/RequestManager">
  <table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0" bgcolor="#DDDDDD">
    <tr>
      <td nowrap align="left">Current:&nbsp;
        <input type="text" name="currentAdj" maxlength="11" size="11" value="<%= currentAdj %>">
      </td>
    </tr>
    <tr>
      <td nowrap align="left">YTD:&nbsp;
        <input type="text" name="ytdAdj" maxlength="11" size="11" value="<%= ytdAdj %>">
      </td>
    </tr>
    <tr>
      <td nowrap align="left">Cumulative:&nbsp;
        <input type="text" name="cumulativeAdj" maxlength="11" size="11" value="<%= cumulativeAdj %>">
      </td>
    </tr>
    <tr>
      <td width="57%" align="right" nowrap>
        <input type="button" name="close" value="Close" onClick="closeAdjustAccumsDialog()">
      </td>
    </tr>
  </table>

     <!-- ****** HIDDEN FIELDS ***** //-->
  <input type="hidden" name="transaction" value="">
  <input type="hidden" name="action"      value="">

</form>
</body>
</html>
