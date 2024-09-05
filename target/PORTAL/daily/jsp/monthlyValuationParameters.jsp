<!-- ****** JAVA CODE ***** //-->
<%@ taglib uri="/WEB-INF/taglibs-input.tld" prefix="input" %>
<%@ page  errorPage="/common/jsp/error.jsp" autoFlush="true" %>
<%@ page import="java.util.*, fission.beans.*,
                 fission.utility.Util" %>

<html>
<head>

<script src="/PORTAL/common/javascript/commonJavascriptFunctions.js"></script>
<script src="/PORTAL/common/javascript/formatDateFunctions.js" type="text/javascript"></script>
<script src="/PORTAL/common/javascript/calendarSelector.js" type="text/javascript"></script>

<script language="Javascript1.2">

	var f = null;

	function init() {

		f = document.monthlyValuationParametersForm;

	}

	function runMonthlyValuation()
    {
		sendTransactionAction("DailyDetailTran", "runMonthlyValuation", "main");

	}

	function bCancel()
    {
		sendTransactionAction("DailyDetailTran", "showDailySelection", "main");
	}


</script>

<title>PRD Extract Parameters</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>

<body bgcolor="#DDDDDD" onLoad="init()">
<form name="monthlyValuationParametersForm" method="post" action="/PORTAL/servlet/RequestManager">
  <table name="batch" width="56%" border="0" cellspacing="0" cellpadding="0" bgcolor="#DDDDDD">
    <tr>
      <td nowrap align="left">Extract Date:&nbsp;
        <input:text name="extractDate"
              attributesText="id='extractDate' onBlur='DateFormat(this, this.value, event, true)' onKeyUp='DateFormat(this, this.value, event, false)' maxlength='10' size='10'"/>
        <a href="javascript:show_calendar('f.extractDate', f.extractDate.value);"><img
               src="/PORTAL/common/images/calendarIcon.gif" width="16" height="16" border="0"
               alt="Select a date from the calendar"></a>
      </td>
    </tr>
    <tr>
            <td align="left" nowrap >Async: &nbsp;
        <input type="checkbox" name="isAsync" checked>
      </td>
    </tr>
        <tr>
            <td align="left" nowrap >Year End Values: &nbsp;
        <input type="checkbox" name="isYev" checked>
      </td>
    </tr>
    <tr>
            <td align="left" nowrap >Contract Number: &nbsp;
        <input type="text" name="mevContractNumber" size="20">
      </td>
    </tr>
    <tr>
      <td colspan="3" align="right">
        <input type="button" name="enter" value=" Enter " onClick="runMonthlyValuation()">
        <input type="button" name="cancel" value="Cancel"  onClick="bCancel()">
      </td>
    </tr>
  </table>


<!-- ****** HIDDEN FIELDS ***** //-->
  <input type="hidden" name="transaction" value="">
  <input type="hidden" name="action" value="">

</form>
</body>
</html>
