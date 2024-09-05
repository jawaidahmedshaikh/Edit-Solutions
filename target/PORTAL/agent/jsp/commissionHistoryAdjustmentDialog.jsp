<!-- taxAdjustmentDialog.jsp //-->

<!-- ****** JAVA CODE ***** //-->

<%@ page  errorPage="/common/jsp/error.jsp" autoFlush="true" %>
<%@ page import="java.util.*, fission.beans.*" %>

<%
    String selectedCommissionHistoryPK = (String) request.getAttribute("selectedCommissionHistoryPK");
	String statementInd = (String) request.getAttribute("statementInd");
    if (statementInd.equalsIgnoreCase("Y"))
    {
        statementInd = "unchecked";
    }
    else
    {
        statementInd = "checked";
    }
%>

<html>
<head>

<script language="Javascript1.2">

	var f = null;

	function init() {

		f = document.adjustmentForm;
	}

	function sendTransactionAction(transaction, action, target)
    {
		f.transaction.value=transaction;
		f.action.value=action;

		f.target = target;

		f.submit();
	}

	function saveAdjustment()
    {
        if (f.statementInd.checked == true)
        {
            f.statementIndStatus.value = "N";
        }
        else
        {
            f.statementIndStatus.value = "Y";
        }

        sendTransactionAction("AgentDetailTran", "saveCommHistoryAdjustment", "contentIFrame");
		window.close();
	}

	function cancelAdjustment ()
    {
        sendTransactionAction("AgentDetailTran", "cancelCommHistoryAdjustment", "contentIFrame");
		window.close();
	}

</script>

<title>Commission History Adjustment</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>

<body bgcolor="#DDDDDD" onLoad="init()" bgcolor="#DDDDDD">
<form name="adjustmentForm" method="post" action="/PORTAL/servlet/RequestManager">
  <table id="table1" width="80%" border="0" cellspacing="0" cellpadding="0" bgcolor="#DDDDDD">
    <tr>
      <td nowrap align="left">
        Suppress On Commission Statement
	    <input type="checkbox" name="statementInd" <%= statementInd %>>
      </td>
    </tr>
    <tr>
      <td>
        &nbsp;
      </td>
    </tr>
    <tr>
      <td width="57%" align="right" nowrap>
        <input type="button" name="enter" value="Enter" onClick="saveAdjustment()">
        <input type="button" name="enter" value="Cancel" onClick="cancelAdjustment()">
      </td>
    </tr>
  </table>

     <!-- ****** HIDDEN FIELDS ***** //-->
  <input type="hidden" name="transaction" value="">
  <input type="hidden" name="action"      value="">
  <input type="hidden" name="selectedCommissionsHistoryPK" value="<%= selectedCommissionHistoryPK %>">
  <input type="hidden" name="statementIndStatus" value="">

</form>
</body>
</html>
