<!-- ****** JAVA CODE ***** //-->
<%@ taglib uri="/WEB-INF/taglibs-input.tld" prefix="input" %>
<%@ page  errorPage="/common/jsp/error.jsp" autoFlush="true" %>
<%@ page import="java.util.*, fission.beans.*,
                 edit.common.vo.ProductStructureVO,
                 edit.common.vo.FundVO,
                 edit.common.vo.CodeTableVO,
                 edit.common.CodeTableWrapper,
                 engine.util.TransformChargeCodes,
                 fission.utility.Util,
                 edit.common.EDITDate,
                 businesscalendar.BusinessDay,
                 businesscalendar.BusinessCalendar,
                 edit.common.vo.FilteredFundVO,
                 event.EDITTrxHistory" %>

<html>
<head>

<script src="/PORTAL/common/javascript/commonJavascriptFunctions.js"></script>
<script src="/PORTAL/common/javascript/formatDateFunctions.js" type="text/javascript"></script>
<script src="/PORTAL/common/javascript/calendarSelector.js" type="text/javascript"></script>

<script language="Javascript1.2">

	var f = null;

	function init()
        {
		f = document.transferMemorandumParamForm;
	}

	function runTransferMemorandum()
        {
            if (valueIsEmpty(f.date.value))
            {
                alert("Please Enter Date for MemorandumReport");
            }
            else
            {
                sendTransactionAction("DailyDetailTran", "runTransferMemorandum", "main");
            }
	}

	function bCancel()
        {
		sendTransactionAction("DailyDetailTran", "showDailySelection", "main");
	}
</script>

<title>Transfer Memorandum Param</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>

<body bgcolor="#DDDDDD" onLoad="init()">
<form name="transferMemorandumParamForm" method="post" action="/PORTAL/servlet/RequestManager">
  <table id="table1" name="banking" width="56%" border="0" cellspacing="0" cellpadding="0" height="110" bgcolor="#DDDDDD">
    <tr>
      <td width="26%" nowrap align="right">Date:&nbsp;</td>
      <td width="9%" nowrap align="left">
        <input:text name="date"
              attributesText="id='date' onBlur='DateFormat(this, this.value, event, true)' onKeyUp='DateFormat(this, this.value, event, false)' maxlength='10' size='10'"/>
        <a href="javascript:show_calendar('f.date', f.date.value);"><img
               src="/PORTAL/common/images/calendarIcon.gif" width="16" height="16" border="0"
               alt="Select a date from the calendar"></a>
      </td>
    </tr>
    <tr>
      <td width="100%" colspan="4">&nbsp;</td>
    </tr>
    <tr>
      <td width="26%">&nbsp;</td>
      <td width="9%">&nbsp;</td>
      <td width="30%">&nbsp;</td>
      <td width="35%" align="right" nowrap>
        <input type="button" name="enter" value=" Enter " onClick="runTransferMemorandum()">
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