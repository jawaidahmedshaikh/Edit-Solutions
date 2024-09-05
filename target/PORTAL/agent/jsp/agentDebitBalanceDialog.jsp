<!-- ****** JAVA CODE ***** //-->

<%@ page  errorPage="/common/jsp/error.jsp" autoFlush="true" %>
<%@ page import="java.util.*, fission.beans.*,
                 edit.common.vo.*,
                 agent.*,
                 edit.common.*,
                 fission.utility.*, 
					  role.ClientRole,
                 role.ClientRoleFinancial,
                 fission.utility.DateTimeUtil" %>

<%
    String selectedAgentNumber = Util.initString((String) request.getAttribute("selectedAgentNumber"), "");

    String dbInterest = "";
    String dbLastValueDate = "";
    String dbStatus = "";
    String dbAmount = "";

    if (!selectedAgentNumber.equals(""))
            {
        ClientRole clientRole = ClientRole.findBy_RoleType_ReferenceID(ClientRole.ROLETYPECT_AGENT, selectedAgentNumber)[0];
        ClientRoleFinancial clientRoleFinancial = clientRole.getClientRoleFinancial();
        if (clientRoleFinancial != null)
            {
            dbInterest = clientRoleFinancial.getIDBAmount().toString();
            EDITDate lastValueDate = clientRoleFinancial.getIDBLastValDate();
            if (lastValueDate != null)
                {
                dbLastValueDate = DateTimeUtil.formatEDITDateAsMMDDYYYY(lastValueDate);
                    }

            dbAmount = clientRoleFinancial.getDBAmount().toString();
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
		f = document.debitBalanceForm;

        formatCurrency();
	}

	function saveDebitBalance()
    {
        sendTransactionAction("AgentDetailTran", "saveDebitBalance", "_self");
		window.close();
	}

</script>

<title>Debit Balance Repay Information</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>

<body class="dialog" onLoad="init()" bgcolor="#DDDDDD">
<form name="debitBalanceForm" method="post" action="/PORTAL/servlet/RequestManager">
  <table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0" bgcolor="#DDDDDD">
    <tr>
      <td nowrap align="right">DB Interest:&nbsp;</td>
      <td nowrap align="left">
        <input disabled type="text" name="dbInterest" maxlength="25" size="18" value="<%= dbInterest %>" CURRENCY>
      </td>
    </tr>
    <tr>
      <td nowrap align="right">DB Last Value Date:&nbsp;</td>
      <td nowrap align="left">
        <input disabled type="text" name="dbLastValueDate" maxlength="10" size="10" value="<%= dbLastValueDate %>">
      </td>
    </tr>
    <tr>
      <td nowrap align="right">DB Status:&nbsp;</td>
      <td nowrap align="left">
        <input disabled type="text" name="dbStatus" maxlength="20" size="11" value="<%= dbStatus %>">
      </td>
    </tr>
    <tr>
      <td nowrap align="right">DB Amount:&nbsp;</td>
      <td nowrap align="left">
        <input disabled type="text" name="dbAmount" maxlength="25" size="18" value="<%= dbAmount %>" CURRENCY>
      </td>
    </tr>
    <tr>
      <td colspan="2" align="right" nowrap>
        <input type="button" name="enter" value="Close" onClick="closeWindow()">
      </td>
    </tr>
  </table>

     <!-- ****** HIDDEN FIELDS ***** //-->
  <input type="hidden" name="transaction" value="">
  <input type="hidden" name="action"      value="">
  <input type="hidden" name="selectedAgentNumber" value="<%= selectedAgentNumber %>">

</form>
</body>
</html>
