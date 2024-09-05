<!-- ****** JAVA CODE ***** //-->
<%@ taglib uri="/WEB-INF/taglibs-input.tld" prefix="input" %>
<%@ page  errorPage="/common/jsp/error.jsp" autoFlush="true" %>
<%@ page import="java.util.*, fission.beans.*,
                 edit.common.vo.*" %>

<%
	String[]  companyNames = (String[]) request.getAttribute("companyNames");

    String message = (String) request.getAttribute("message");
%>

<html>
<head>

<script src="/PORTAL/common/javascript/commonJavascriptFunctions.js"></script>
<script src="/PORTAL/common/javascript/formatDateFunctions.js" type="text/javascript"></script>
<script src="/PORTAL/common/javascript/calendarSelector.js" type="text/javascript"></script>

<script language="Javascript1.2">

	var f = null;

    var message = "<%= message %>";

	function init() {

		f = document.theForm;

        if (message != "null")
        {
            alert(message);
        }
	}

	function sendTransactionAction(transaction, action, target) {

		f.transaction.value=transaction;
		f.action.value=action;

		f.target = target;

		f.submit();
	}

	function runBonusCommissions()
    {
        if (f.fromDate.value == "" ||
            f.toDate.value == "" ||)
        {
            alert("Complete From and To Dates Must Be Entered To Run Bonus Commissions");
        }
        else
        {
		    sendTransactionAction("ReportingDetailTran", "runBonusCommissions", "main");
        }
	}

    function showReportingMain()
    {
		sendTransactionAction("ReportingDetailTran", "showReportingMain", "main");
    }

</script>

<title>Bonus Commissions</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>

<body bgcolor="#DDDDDD" onLoad="init()">
<form name="theForm" method="post" action="/PORTAL/servlet/RequestManager">
  <table id="table1" name="reporting" width="56%" border="0" cellspacing="0" cellpadding="0" height="110" bgcolor="#DDDDDD">
    <tr>
      <td width="26%" nowrap>Company Key:</td>
      <td width="9%" nowrap>
      <select name="companyName">
      <option selected value="-1">
         Please Select
      </option>
	<option value="All"> All </option>

      <%
          if (companyNames != null)
          {
              for(int i = 0; i < companyNames.length; i++)
              {
                  out.println("<option name=\"id\" value=\"" + companyNames[i] + "\">" + companyNames[i] + "</option>");
              }
          }
      %>
      </select>
      </td>
      <td width="65%">&nbsp;</td>
    </tr>
    <tr>
      <td width="26%" height="24" nowrap align="right">From:&nbsp;</td>
      <td nowrap align="left">
        <input:text name="fromDate"
              attributesText="id='fromDate' onBlur='DateFormat(this, this.value, event, true)' onKeyUp='DateFormat(this, this.value, event, false)' maxlength='10' size='10'"/>
        <a href="javascript:show_calendar('f.fromDate', f.fromDate.value);"><img
               src="/PORTAL/common/images/calendarIcon.gif" width="16" height="16" border="0"
               alt="Select a date from the calendar"></a>
      </td>
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;To:&nbsp;
      <td nowrap align="left">
        <input:text name="toDate"
              attributesText="id='toDate' onBlur='DateFormat(this, this.value, event, true)' onKeyUp='DateFormat(this, this.value, event, false)' maxlength='10' size='10'"/>
        <a href="javascript:show_calendar('f.toDate', f.toDate.value);"><img
               src="/PORTAL/common/images/calendarIcon.gif" width="16" height="16" border="0"
               alt="Select a date from the calendar"></a>
      </td>
    </tr>
    <tr>
      <td width="26%">&nbsp;</td>
      <td width="9%">&nbsp;</td>
      <td width="65%" align="right" nowrap>
        <input type="button" name="enter" value=" Enter " onClick="runBonusCommissions()">
        <input type="button" name="cancel" value="Cancel" onClick="showReportingMain()">
      </td>
    </tr>
  </table>

<!-- ****** HIDDEN FIELDS ***** //-->
  <input type="hidden" name="transaction" value="">
  <input type="hidden" name="action" value="">

</form>
</body>
</html>