<!-- ****** JAVA CODE ***** //-->
<%@ taglib uri="/WEB-INF/taglibs-input.tld" prefix="input" %>
<%@ page  errorPage="/common/jsp/error.jsp" autoFlush="true" %>
<%@ page import="java.util.*, fission.beans.*" %>


<jsp:useBean id="valuationParamsPageBean"
    class="fission.beans.PageBean" scope="request"/>

<%
	String[]  companyNames        = valuationParamsPageBean.getValues("companyNames");
%>

<html>
<head>

<script src="/PORTAL/common/javascript/commonJavascriptFunctions.js"></script>
<script src="/PORTAL/common/javascript/formatDateFunctions.js" type="text/javascript"></script>
<script src="/PORTAL/common/javascript/calendarSelector.js" type="text/javascript"></script>

<script language="Javascript1.2">

	var f = null;

	function init() {

		f = document.valuationRunForm;
	}

	function sendTransactionAction(transaction, action, target) {

		f.transaction.value=transaction;
		f.action.value=action;

		f.target = target;

		f.submit();
	}

	function runValuation() {

		sendTransactionAction("ReportingDetailTran", "runValuation", "main");
	}

	function bCancel() {

		sendTransactionAction("ReportingDetailTran", "showReportingMain", "main");
	}

</script>

<title>Untitled Document</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>

<body bgcolor="#DDDDDD" onLoad="init()">
<form name="valuationRunForm" method="post" action="/PORTAL/servlet/RequestManager">
  <table id="table1" name="accounting" width="56%" border="0" cellspacing="0" cellpadding="0" bgcolor="#DDDDDD">
    <tr>
      <td width="26%" nowrap>Company Key:</td>
      <td width="9%" nowrap>
      <select name="companyName">
      <option selected value="Please Select">
         Please Select
      </option>
  	  <option> All </option>

      <%
      	for(int i = 0; i < companyNames.length; i++) {

			out.println("<option name=\"companyName\">" + companyNames[i] + "</option>");
      	}
      %>
      </select>
      </td>
      <td width="42%">&nbsp;</td>
    </tr>
    <tr>
      <td width="26%" height="24" nowrap>Valuation Date:</td>
      <td nowrap align="left">
        <input:text name="valuationDate"
              attributesText="id='valuationDate' onBlur='DateFormat(this, this.value, event, true)' onKeyUp='DateFormat(this, this.value, event, false)' maxlength='10' size='10'"/>
        <a href="javascript:show_calendar('f.valuationDate', f.valuationDate.value);"><img
               src="/PORTAL/common/images/calendarIcon.gif" width="16" height="16" border="0"
               alt="Select a date from the calendar"></a>
      </td>
      <td width="65%" height="24">&nbsp;</td>
    </tr>
    <tr>
      <td width="26%">&nbsp;</td>
      <td width="9%">&nbsp;</td>
      <td width="42%" align="right" nowrap>
        <input type="button" name="enter" value=" Enter " onClick="runValuation()">
        <input type="button" name="cancel" value="Cancel" onClick="bCancel()">
      </td>
    </tr>
  </table>

<!-- ****** HIDDEN FIELDS ***** //-->
  <input type="hidden" name="transaction" value="">
  <input type="hidden" name="action" value="">

</form>
</body>
</html>