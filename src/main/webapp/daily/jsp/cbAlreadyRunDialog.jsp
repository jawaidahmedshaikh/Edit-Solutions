<!-- ****** JAVA CODE ***** //-->

<%@ page  errorPage="/common/jsp/error.jsp" autoFlush="true" %>
<%@ page import="java.util.*, fission.beans.*,
                 fission.utility.Util" %>

<%
    String cycleMonth = (String) request.getAttribute("cycleMonth");
    String cycleDay = (String) request.getAttribute("cycleDay");
    String cycleYear = (String) request.getAttribute("cycleYear");
%>

<html>
<head>

<script src="/PORTAL/common/javascript/commonJavascriptFunctions.js"></script>

<script language="Javascript1.2">

	var f = null;

	function init()
    {
		f = document.cbAlreadyRunForm;
	}

	function createActivityFile()
    {
        f.okayToContinue.value = "Y";
		sendTransactionAction("DailyDetailTran", "createActivityFile", "main");
        closeWindow();
	}

	function bCancel()
    {
		sendTransactionAction("DailyDetailTran", "showACTFileParams", "main");
        closeWindow();
	}

</script>

<title>EDITSolutions - Controls And Balances Interface Already Run</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>

<body bgcolor="#DDDDDD" onLoad="init()">
<form name="cbAlreadyRunForm" method="post" action="/PORTAL/servlet/RequestManager">
  <table id="table1" name="activityFile" width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      <td colspan="2">Controls And Balances Already Run For Selected Date - Do You Wish To Continue?</td>
    </tr>
    <tr>
      <td align="right" nowrap colspan="2">
        <input type="button" name="continue" value="Continue" onClick="createActivityFile()">
        <input type="button" name="cancel" value="Cancel" onClick="bCancel()">
      </td>
    </tr>
  </table>

<!-- ****** HIDDEN FIELDS ***** //-->
  <input type="hidden" name="transaction" value="">
  <input type="hidden" name="action" value="">
  <input type="hidden" name="cycleMonth" value="<%= cycleMonth %>">
  <input type="hidden" name="cycleDay" value="<%= cycleDay %>">
  <input type="hidden" name="cycleYear" value="<%= cycleYear %>">
  <input type="hidden" name="okayToContinue" value="">

</form>
</body>
</html>