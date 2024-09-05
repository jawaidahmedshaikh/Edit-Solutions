<!-- contractCommitContractNumberDialog.jsp //-->

<!-- ****** JAVA CODE ***** //-->

<%@ page  errorPage="/common/jsp/error.jsp" autoFlush="true" %>
<%@ page import="java.util.*, fission.beans.*,
                 edit.common.CodeTableWrapper,
                 edit.common.vo.CodeTableVO" %>

<html>
<head>

<script language="Javascript1.2">

	var f = null;

	function init() {

		f = document.contractCodeForm;
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

	function saveContractCode() {

        if (f.contractCode.value == "") {

            alert("Please Enter Contract Code");
        }
        else {

            sendTransactionAction("AgentDetailTran", "saveContractCode", "main")
            window.close();
        }
	}

</script>

<title>Add New Contract Code</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>

<body class="dialog" bgcolor="#DDDDDD" onLoad="init()">
<form name="contractCodeForm" method="post" action="/PORTAL/servlet/RequestManager">
  <table id="table1" width="51%" border="0" cellspacing="0" cellpadding="0" bgcolor="#DDDDDD">
    <tr>
      <td colspan="3" nowrap>Contract Code:
        <input type="text" name="contractCode" maxlength="20" size="20">
      </td>
    </tr>
    <tr>
      <td colspan="3" nowrap>&nbsp;</td>
    </tr>
    <tr>
      <td colspan="3" align="right" valign="bottom" nowrap>
        <input type="button" name="enter" value="Enter" onClick="saveContractCode()">
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
