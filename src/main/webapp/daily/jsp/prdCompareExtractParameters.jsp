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

		f = document.prdCompareExtractParametersForm;

	}

	function runPRDCompareExtract()
    {
		sendTransactionAction("DailyDetailTran", "runPRDCompareExtract", "main");

	}

	function bCancel()
    {
		sendTransactionAction("DailyDetailTran", "showDailySelection", "main");
	}


</script>

<title>PRD Compare Extract Parameters</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>

<body bgcolor="#DDDDDD" onLoad="init()">
<form name="prdCompareExtractParametersForm" method="post" action="/PORTAL/servlet/RequestManager">
  <table name="batch" width="56%" border="0" cellspacing="0" cellpadding="0" bgcolor="#DDDDDD">
    <tr>
      <td nowrap align="right">Extract Date:&nbsp;</td>
      <td nowrap align="left">
        <input:text name="extractDate"
              attributesText="id='extractDate' onBlur='DateFormat(this, this.value, event, true)' onKeyUp='DateFormat(this, this.value, event, false)' maxlength='10' size='10'"/>
        <a href="javascript:show_calendar('f.extractDate', f.extractDate.value);"><img
               src="/PORTAL/common/images/calendarIcon.gif" width="16" height="16" border="0"
               alt="Select a date from the calendar"></a>
      </td>
    </tr>

    <tr>
      <td colspan="3" align="right">
        <input type="button" name="enter" value=" Enter " onClick="runPRDCompareExtract()">
        <input type="button" name="cancel" value="Cancel"  onClick="bCancel()">
      </td>
    </tr>
      <tr>
	  <td nowrap align="right">Output File Type:&nbsp;</td>
      <td nowrap align="left">
		<select name="extractType">
		  <option> Please Select </option>
                  <option name="extractType" value="VENUS">VENUS</option>"
                  <option name="extractType" value="MF">MF</option>"
                  <option name="extractType" value="BOTH">BOTH</option>"
		</select>
      </td>
    </tr> 
  </table>


<!-- ****** HIDDEN FIELDS ***** //-->
  <input type="hidden" name="transaction" value="">
  <input type="hidden" name="action" value="">

</form>
</body>
</html>
