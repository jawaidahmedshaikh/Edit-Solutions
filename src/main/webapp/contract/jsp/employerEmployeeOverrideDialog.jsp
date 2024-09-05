<%@ page  errorPage="/common/jsp/error.jsp" autoFlush="true" %>
<%@ page import="java.util.*, fission.beans.*" %>

<jsp:useBean id="contractTransactionSessionBean"
    class="fission.beans.SessionBean" scope="session"/>

<%
    PageBean formBean = contractTransactionSessionBean.getPageBean("formBean");

	String employerContribution = formBean.getValue("employerContribution");
	String employeeContribution = formBean.getValue("employeeContribution");
%>

<html>
<head>
<title>Employer/Employee Override</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">

<script src="/PORTAL/common/javascript/commonJavascriptFunctions.js"></script>

<script language="Javascript1.2">

	var f = null;

	function init()
    {
		f = document.employerEmployeeForm;

        formatCurrency();
	}

	function saveEmployerEmployeeOverride()
    {
        sendTransactionAction("ContractDetailTran", "updateEmployerEmployeeOverride", "transactionDialog");
        window.close();
	}
</script>
</head>
<body class="mainTheme" onLoad="init()">
<form name="employerEmployeeForm" method="post" action="/PORTAL/servlet/RequestManager">
  <table id="table1" width="40%" border="0" cellspacing="5" cellpadding="0">
    <tr>
      <td align="right" nowrap>Employer Contribution:&nbsp;</td>
      <td align="left" nowrap>
        <input type="text" name="employerContribution" size="11" maxlength="11" value="<%= employerContribution %>" CURRENCY>
      </td>
    </tr>
    <tr>
      <td align="right" nowrap>Employee Contribution:&nbsp;</td>
      <td align="left" nowrap>
        <input type="text" name="employeeContribution" size="11" maxlength="11" value="<%= employeeContribution %>" CURRENCY>
      </td>
    </tr>
    <tr>
      <td align="right" width="10%" nowrap>&nbsp;</td>
      <td align="right" width="10%" nowrap>
        <input type="button" name="enter" value=" Enter" onClick="saveEmployerEmployeeOverride()">
        <input type="button" name="cancel" value="Cancel" onClick ="closeWindow()">
      </td>
    </tr>
  </table>

  <!-- ****** HIDDEN FIELDS ***** //-->
  <input type="hidden" name="transaction"  value="">
  <input type="hidden" name="action"       value="">

</form>

</body>
</html>
