<!-- ****** JAVA CODE ***** //-->

<%@ page  errorPage="/common/jsp/error.jsp" autoFlush="true" %>
<%@ page import="java.util.*, fission.beans.*,
                 edit.common.CodeTableWrapper,
                 edit.common.vo.*,
                 fission.utility.Util" %>

<%
    CommissionHistoryVO[] commissionHistoryVOs = (CommissionHistoryVO[]) session.getAttribute("extractHistoryVOs");

    String adaAmount = "";
    String adaEarned = "";
    String expenseAllowance = "";
    String eaEarned = "";
//    String bonusAmount = "";
    String selectedCommissionHistoryPK = (String) request.getAttribute("selectedCommissionHistoryPK");

    if (commissionHistoryVOs != null)
    {
        for (int h = 0; h < commissionHistoryVOs.length; h++)
        {
            if ((commissionHistoryVOs[h].getCommissionHistoryPK() + "").equals(selectedCommissionHistoryPK))
            {
                adaAmount = Util.roundToNearestCent(commissionHistoryVOs[h].getADAAmount()).toString();
                expenseAllowance = Util.roundToNearestCent(commissionHistoryVOs[h].getExpenseAmount()).toString();
//                bonusAmount = Util.roundToNearestCent(commissionHistoryVOs[h].getBonusCommissionAmount()).toString();
            }
        }
    }
%>

<html>
<head>

<script src="/PORTAL/common/javascript/commonJavascriptFunctions.js"></script>

<script language="Javascript1.2">

	var f = null;

	function init()
    {
		f = document.allowancesForm;

        formatCurrency();
	}

	function saveAllowancesDialog() {

        sendTransactionAction("AgentDetailTran","saveAllowancesDialog","extractDialog");
		closeWindow();
	}

</script>

<title>Allowances</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>

<body class="dialog" onLoad="init()" bgcolor="#DDDDDD">
<form name="allowancesForm" method="post" action="/PORTAL/servlet/RequestManager">
  <table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0" bgcolor="#DDDDDD">
    <tr>
      <td align="right" nowrap>ADA Amount:&nbsp;</td>
      <td align="left" nowrap>
        <input type="text" name="adaAmount" maxlength="11" size="11" value="<%= adaAmount %>" CURRENCY>
      </td>
    </tr>
    <tr>
      <td align="right" nowrap>ADA Earned:&nbsp;</td>
      <td align="left" nowrap>
        <input disabled type="text" name="adaEarned" maxlength="11" size="11" value="<%= adaEarned %>" CURRENCY>
      </td>
    </tr>
    <tr>
      <td align="right" nowrap>Expense Allowance:&nbsp;</td>
      <td align="left" nowrap>
        <input type="text" name="expenseAllowance" maxlength="11" size="11" value="<%= expenseAllowance %>" CURRENCY>
      </td>
    </tr>
    <tr>
      <td align="right" nowrap>EA Earned:&nbsp;</td>
      <td align="left" nowrap>
        <input disabled type="text" name="eaEarned" maxlength="11" size="11" value="<%= eaEarned %>" CURRENCY>
      </td>
    </tr>
<%--    <tr>--%>
<%--      <td align="right" nowrap>Bonus Amount:&nbsp;</td>--%>
<%--      <td align="left" nowrap>--%>
<%--        <input type="text" name="bonusAmount" maxlength="11" size="11" value="<%= bonusAmount %>" CURRENCY>--%>
<%--      </td>--%>
<%--    </tr>--%>
    <tr>
      <td align="right" nowrap colspan="2">
        <input type="button" name="enter" value="Enter" onClick="saveAllowancesDialog()">
        <input type="button" name="cancel" value="Cancel" onClick="closeWindow()">
      </td>
    </tr>
  </table>

     <!-- ****** HIDDEN FIELDS ***** //-->
  <input type="hidden" name="transaction" value="">
  <input type="hidden" name="action"      value="">
  <input type="hidden" name="selectedCommissionHistoryPK" value="<%= selectedCommissionHistoryPK %>">

</form>
</body>
</html>
