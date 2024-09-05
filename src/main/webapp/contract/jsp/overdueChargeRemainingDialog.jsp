<!--
 * User: sprasad
 * Date: Dec 3, 2004
 * Time: 3:55:09 PM
 *
 * (c) 2000-2004 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement. 
 -->

<!-- overdueChargeRemainingDialog.jsp //-->

<!-- ****** JAVA CODE ***** //-->

<%@ page  errorPage="/common/jsp/error.jsp" autoFlush="true" %>
<%@ page import="java.util.*,
                 edit.common.vo.*,
                 fission.utility.Util" %>

<%
    String overdueCoi = Util.initString((String) request.getAttribute("overdueCoi"), "0.00");
    String overdueAdmin = Util.initString((String) request.getAttribute("overdueAdmin"), "0.00");
    String overdueExpense = Util.initString((String) request.getAttribute("overdueExpense"), "0.00");
    String overdueCollateralization = Util.initString((String) request.getAttribute("overdueColl"), "0.00");

    String overdueMessage = Util.initString((String) request.getAttribute("OverdueMessage"), "");
%>

<html>
<head>

<script src="/PORTAL/common/javascript/commonJavascriptFunctions.js"></script>

<script language="Javascript1.2">

	var overdueMessage = "<%= overdueMessage %>";

    function init()
    {
        if (overdueMessage != "")
        {
            alert(overdueMessage);
            window.close();
        }

        formatCurrency();
    }

</script>

<title>Overdue Charge Remaining</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>

<body class="mainTheme" onLoad="init()" bgcolor="#DDDDDD">
<form name="overdueChargeRemainingForm" method="post" action="/PORTAL/servlet/RequestManager">
  <table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0" bgcolor="#DDDDDD">
    <tr>
      <td nowrap align="left">Remaining Coi:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        <input type="text" name="remainingCOI" maxlength="19" size="19" value="<%= overdueCoi %>" disabled CURRENCY>
      </td>
    </tr>
    <tr>
      <td nowrap align="left">Remaining Admin:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        <input type="text" name="remainingAdmin" maxlength="19" size="19" value="<%= overdueAdmin %>" disabled CURRENCY>
      </td>
    </tr>
    <tr>
      <td nowrap align="left">Remaining Expense:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        <input type="text" name="remainingExpense" maxlength="19" size="19" value="<%= overdueExpense %>" disabled CURRENCY>
      </td>
    </tr>
    <tr>
      <td nowrap align="left">Remaining Collateralization:&nbsp;
        <input type="text" name="remainingCollateralization" maxlength="19" size="19" value="<%= overdueCollateralization %>" disabled CURRENCY>
      </td>
    </tr>
    <tr>
      <td nowrap align="right">
        <input type="button" name="close" value="Close" onClick="closeWindow()">
      </td>
    </tr>
  </table>

</form>
</body>
</html>
