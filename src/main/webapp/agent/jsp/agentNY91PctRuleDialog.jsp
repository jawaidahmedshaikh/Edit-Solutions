<!-- agentNY91PctRuleDialog.jsp //-->

<!-- ****** JAVA CODE ***** //-->

<%@ page  errorPage="/common/jsp/error.jsp" autoFlush="true" %>
<%@ page import="java.util.*, fission.beans.*,
                 edit.common.CodeTableWrapper,
                 edit.common.vo.*,
                 fission.utility.Util,
                 role.ClientRole,
                 role.ClientRoleFinancial" %>

<%
    String selectedAgentNumber = Util.initString((String) request.getAttribute("selectedAgentNumber"), "");

    String nyPrem = "";
    String nyComm = "";
    String excessShortage = "";

    if (!selectedAgentNumber.equals(""))
    {
        ClientRole clientRole = ClientRole.findBy_RoleType_ReferenceID(ClientRole.ROLETYPECT_AGENT, selectedAgentNumber)[0];
        ClientRoleFinancial clientRoleFinancial = clientRole.getClientRoleFinancial();
        if (clientRoleFinancial != null)
        {
            excessShortage = Util.roundToNearestCent(clientRoleFinancial.getNYPrem().subtractEditBigDecimal(clientRoleFinancial.getNYComm())).toString();
            nyPrem = clientRoleFinancial.getNYPrem().toString();
            nyComm = clientRoleFinancial.getNYComm().toString();
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
		f = document.ny91PctRuleForm;

        formatCurrency();
	}

</script>

<title>NY 91% Rule</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>

<body class="dialog" onLoad="init()" bgcolor="#DDDDDD">
<form name="ny91PctRuleForm" method="post" action="/PORTAL/servlet/RequestManager">
  <table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0" bgcolor="#DDDDDD">
    <tr>
      <td nowrap align="left">91% Prem:&nbsp;
        <input disabled type="text" name="nyPrem" maxlength="11" size="11" value="<%= nyPrem %>" CURRENCY>
      </td>
    </tr>
    <tr>
      <td nowrap align="left">91% Comm:&nbsp;
        <input disabled type="text" name="nyComm" maxlength="11" size="11" value="<%= nyComm %>" CURRENCY>
      </td>
    </tr>
    <tr>
      <td nowrap align="left">91% Excess/Shortage:&nbsp;
        <input disabled type="text" name="excessShortage" maxlength="11" size="11" value="<%= excessShortage %>" CURRENCY>
      </td>
      </td>
    </tr>
    <tr>
      <td width="57%" align="right" nowrap>
        <input type="button" name="close" value="Close" onClick="closeWindow()">
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
