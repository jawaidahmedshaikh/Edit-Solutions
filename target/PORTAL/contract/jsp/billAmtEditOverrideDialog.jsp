<%@ page  errorPage="/common/jsp/error.jsp" autoFlush="true" %>
<%@ page import="java.util.*, fission.beans.*" %>

<jsp:useBean id="contractTransactionSessionBean"
    class="fission.beans.SessionBean" scope="session"/>

<%
    PageBean formBean = contractTransactionSessionBean.getPageBean("formBean");

    String billAmtEditOverrideIndStatus = formBean.getValue("billAmtEditOverrideIndStatus");
%>

<html>
<head>
<title>Bill Amount Edit Override</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">

<script language="Javascript1.2">

	var f = null;

	function init() {

		f = document.billAmtEditOverrideForm;
	}

    function cancelBillAmtEditOverride() {

    	window.close();
    }

	function saveBillAmtEditOverride()
    {
		if (f.billAmtEditOverrideInd.checked == true)
        {
			f.billAmtEditOverrideIndStatus.value = "checked";
		}

		sendTransactionAction("ContractDetailTran", "updateBillAmtEditOverride", "transactionDialog");
        window.close();
	}

    function sendTransactionAction(transaction, action, target) {

	   	f.transaction.value=transaction;
    	f.action.value=action;

    	f.target = target;

    	f.submit();
    }

</script>
</head>
<body bgcolor="#DDDDDD" onLoad="init()">
<form name="billAmtEditOverrideForm" method="post" action="/PORTAL/servlet/RequestManager">
  <table id="table1" width="40%" border="0" cellspacing="5" cellpadding="0" bgcolor="#DDDDDD">
    <tr>
      <td align="right" width="10%" nowrap height="31">Override Bill Amount Edit&nbsp;</td>
      <td align="left" width="10%" nowrap height="31">
        <input type="checkbox" name="billAmtEditOverrideInd" <%= billAmtEditOverrideIndStatus %>>
      </td>
    </tr>
    <tr>
      <td align="right" width="10%" nowrap>&nbsp;</td>
      <td align="right" width="10%" nowrap>
        <input type="button" name="enter" value=" Enter" onClick="saveBillAmtEditOverride()">
        <input type="button" name="cancel" value="Cancel" onClick ="cancelBillAmtEditOverride()">
      </td>
    </tr>
  </table>

  <!-- ****** HIDDEN FIELDS ***** //-->
  <input type="hidden" name="transaction"  value="">
  <input type="hidden" name="action"       value="">

  <input type="hidden" name="billAmtEditOverrideIndStatus" value="">

</form>

</body>
</html>
