<!-- ****** JAVA CODE ***** //-->

<%@ page  errorPage="/common/jsp/error.jsp" autoFlush="true" %>
<%@ page import="java.util.*, fission.beans.*" %>

<%
    PageBean pageBean = (PageBean) request.getAttribute("closeAccountingParamsPageBean");
	String[] marketingPackageNames = pageBean.getValues("marketingPackageNames");
%>

<html>
<head>

<script language="Javascript1.2">

	var f = null;

	function init()
    {
		f = document.closeAccountingForm;
	}

	function sendTransactionAction(transaction, action, target)
    {
		f.transaction.value=transaction;
		f.action.value=action;

		f.target = target;

		f.submit();
	}

	function closeAccounting()
    {
		sendTransactionAction("ReportingDetailTran", "closeAccounting", "main");
	}

	function closeCloseAccountingParams()
    {
		sendTransactionAction("ReportingDetailTran", "showReportingMain", "main");
	}

</script>

<title>Close Accounting Parameters</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>

<body bgcolor="#DDDDDD" onLoad="init()">
<form name="closeAccountingForm" method="post" action="/PORTAL/servlet/RequestManager">
  <table name="taxes" width="56%" border="0" cellspacing="0" cellpadding="0" height="110" bgcolor="#DDDDDD">
    <tr>
      <td nowrap align="right">Marketing Package:&nbsp;</td>
      <td nowrap align="left">
        <select name="marketingPackageName">
          <option selected value="Please Select">
            Please Select
          </option>
	      <option>All</option>
          <%
      	      for(int i = 0; i < marketingPackageNames.length; i++)
              {
   			      out.println("<option name=\"marketingPackageName\">" + marketingPackageNames[i] + "</option>");
      	      }
          %>
        </select>
      </td>
      <td nowrap>&nbsp;</td>
      <td nowrap align="right">Accounting Period(mm/yyyy):&nbsp;</td>
      <td nowrap align="left">
        <input type="text" name="accountingPeriodMonth" maxlength="2" size="2">/
        <input type="text" name="accountingPeriodYear" maxlength="4" size="4">
      </td>
    </tr>
    <tr>
      <td nowrap colspan="4">&nbsp;</td>
      <td nowrap align="right">
        <input type="button" name="enter" value=" Enter " onClick="closeAccounting()">
        <input type="button" name="cancel" value="Cancel" onClick="closeCloseAccountingParams()">
      </td>
    </tr>
  </table>


<!-- ****** HIDDEN FIELDS ***** //-->
  <input type="hidden" name="transaction" value="">
  <input type="hidden" name="action" value="">

</form>
</body>
</html>
