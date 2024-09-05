
<jsp:useBean id="formBean"
   class="fission.beans.PageBean" scope="request"/>

<%
    String scrollingTrxPageSize = (String) request.getAttribute("scrollingTrxPageSize");
    String beginScrollingTrxPK = (String) request.getAttribute("beginScrollingTrxPK");
    String endScrollingTrxPK = (String) request.getAttribute("endScrollingTrxPK");

    String filter             = formBean.getValue("filter");
    String filterMessage      = formBean.getValue("filterMessage");
    String segmentFK          = formBean.getValue("segmentFK");
    String editTrxPK          = formBean.getValue("editTrxPK");
    String segmentName        = formBean.getValue("segmentName");
    String filterAllocPct     = formBean.getValue("filterAllocPct");
    String filterAllocDollars = formBean.getValue("filterAllocDollars");
    String costBasis  	      = formBean.getValue("costBasis");
    String amountReceived     = formBean.getValue("amountReceived");
    String payeeIndStatus     = formBean.getValue("payeeIndStatus");
    String investmentIndStatus = formBean.getValue("investmentIndStatus");
    String deathStatusEnabled = formBean.getValue("deathStatusEnabled");
    String deathStatus        = formBean.getValue("deathStatus");
    String transactionType    = formBean.getValue("transactionType");
    String rowId              = formBean.getValue("rowId");

    String companyStructureId = formBean.getValue("companyStructureId");
    String amtNeeded          = formBean.getValue("amountNeeded");
    String amtUsed            = formBean.getValue("amountUsed");

    String selectedDepositsPK = (String) request.getAttribute("selectedDepositsPK");
    String suspenseFK = (String) request.getAttribute("suspenseFK");
%>

<html>

<head>
<title>Delete Deposit</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<script language="Javascript1.2">

	var f = null;

	function init() {

		f = document.theForm;;
	}

    function sendTransactionAction(transaction, action, target) {

        f.transaction.value= transaction;
        f.action.value= action;

        f.target = target;

        f.submit();
    }

    function deleteDeposit(){

        sendTransactionAction("ContractDetailTran", "deleteSelectedDeposit", "trxDepositDialog");

        window.close();
    }

	function cancelDepositDelete() {

		window.close();
	}

</script>
</head>
<body bgcolor="#DDDDDD" onLoad="init()">
<form name="theForm" method="post" action="/PORTAL/servlet/RequestManager">
  <table id="table1" width="43%" border="0" cellspacing="0" cellpadding="8" bgcolor="#DDDDDD">

    <tr>
      <td colspan="3" nowrap>

        Deposit Will Be Permanently Deleted.  Continue?
      </td>
    </tr>
    <tr>
      <td>&nbsp;&nbsp;</td>
      <td colspan="2" align="right" nowrap>
        <input type="button" name="enter" value="Yes" onClick="deleteDeposit()">
        <input type="button" name="cancel" value="No" onClick="cancelDepositDelete()">
      </td>
    </tr>
  </table>

  <input type="hidden" name="transaction" value="">
  <input type="hidden" name="action" value="">
  <input type="hidden" name="selectedDepositsPK" value="<%= selectedDepositsPK %>">
  <input type="hidden" name="suspenseFK" value="<%= suspenseFK %>">

  <input type="hidden" name="scrollingTrxPageSize" value="<%= scrollingTrxPageSize %>">
  <input type="hidden" name="beginScrollingTrxPK" value="<%= beginScrollingTrxPK %>">
  <input type="hidden" name="endScrollingTrxPK" value="<%= endScrollingTrxPK %>">

  <input type="hidden" name="amountNeeded" value="<%= amtNeeded %>">
  <input type="hidden" name="amountUsed" value="<%= amtUsed %>">

  <input type="hidden" name="filter" value="<%= filter %>">
  <input type="hidden" name="filterMessage" value="<%= filterMessage %>">
  <input type="hidden" name="segmentName" value="<%= segmentName %>">
  <input type="hidden" name="segmentFK" value="<%= segmentFK %>">
  <input type="hidden" name="editTrxPK" value="<%= editTrxPK %>">
  <input type="hidden" name="companyStructureId" value="<%= companyStructureId %>">
  <input type="hidden" name="filterAllocPct" value="<%= filterAllocPct %>">
  <input type="hidden" name="filterAllocDollars" value="<%= filterAllocDollars %>">
  <input type="hidden" name="costBasis" value="<%= costBasis %>">
  <input type="hidden" name="amountReceived" value="<%= amountReceived %>">
  <input type="hidden" name="payeeIndStatus" value="<%= payeeIndStatus %>">
  <input type="hidden" name="investmentIndStatus" value="<%= investmentIndStatus %>">
  <input type="hidden" name="deathStatusEnabled" value="<%= deathStatusEnabled %>">
  <input type="hidden" name="deathStatus" value="<%= deathStatus %>">
  <input type="hidden" name="transactionType" value="<%= transactionType %>">
  <input type="hidden" name="rowId" value="<%= rowId %>">

</form>
</body>
</html>
