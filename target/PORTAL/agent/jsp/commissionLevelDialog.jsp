<!-- contractCommitContractNumberDialog.jsp //-->

<!-- ****** JAVA CODE ***** //-->

<%@ page  errorPage="/common/jsp/error.jsp" autoFlush="true" %>
<%@ page import="java.util.*, fission.beans.*,
                 edit.common.CodeTableWrapper,
                 edit.common.vo.CodeTableVO" %>

<%
    String contractCode = (String) request.getAttribute("selectedContractCode");
    String selectedCommContractFK = (String) request.getAttribute("selectedCommContractFK");
%>
<html>
<head>

<script language="Javascript1.2">

	var f = null;

	function init() {

		f = document.commissionLevelForm;
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

	function saveCommissionLevelDescription() {

        if (f.commissionLevel.value == "") {

            alert("Please Enter Commission Level");
        }
        else {

            sendTransactionAction("AgentDetailTran", "saveCommissionLevelDescription", "main")
            window.close();
        }
	}

</script>

<title>Add New Commission Level</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>

<body class="dialog" bgcolor="#DDDDDD" onLoad="init()">
<form name="commissionLevelForm" method="post" action="/PORTAL/servlet/RequestManager">
  <table id="table1" width="51%" border="0" cellspacing="0" cellpadding="0" bgcolor="#DDDDDD">
    <tr>
      <td align="right" nowrap>ContractCode:&nbsp;</td>
      <td align="left" colspan="2" nowrap>
        <input disabled type="text" name="contractCode" maxlength="20" size="20" value="<%= contractCode %>">
      </td>
    </tr>
    <tr>
      <td align="right" nowrap>Commission Level:&nbsp;</td>
      <td align="left" colspan="2" nowrap>
        <input type="text" name="commissionLevel" maxlength="5" size="5">
      </td>
    </tr>
    <tr>
      <td colspan="3" align="right" valign="bottom" nowrap>
        <input type="button" name="enter" value="Enter" onClick="saveCommissionLevelDescription()">
        <input type="button" name="cancel" value="Cancel" onClick="cancelDialog()">
      </td>
    </tr>
  </table>

     <!-- ****** HIDDEN FIELDS ***** //-->
  <input type="hidden" name="transaction" value="">
  <input type="hidden" name="action"      value="">
  <input type="hidden" name="selectedCommContractFK" value="<%= selectedCommContractFK %>">
  <input type="hidden" name="selectedContractCode" value="<%= contractCode %>">

</form>
</body>
</html>
