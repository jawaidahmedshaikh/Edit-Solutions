<!-- ****** JAVA CODE ***** //-->
<%@ taglib uri="/WEB-INF/taglibs-input.tld" prefix="input" %>
<%@ page  errorPage="/common/jsp/error.jsp" autoFlush="true" %>
<%@ page import="java.util.*, fission.beans.*" %>

<html>
<head>

<script src="/PORTAL/common/javascript/commonJavascriptFunctions.js"></script>
<script src="/PORTAL/common/javascript/formatDateFunctions.js" type="text/javascript"></script>
<script src="/PORTAL/common/javascript/calendarSelector.js" type="text/javascript"></script>

<script language="Javascript1.2">

	var f = null;

	function init() {

		f = document.payoutTrxReportForm;
	}

	function runPayoutTransactionReport() {

		var dateOptionChecked = false;
        var fromDateEntered   = false;
        var toDateEntered     = false;

		for (var i = 0; i < f.dateOption.length; i++) {

			if (f.dateOption[i].checked) {

				dateOptionChecked = true;

				f.selectedDateOption.value = f.dateOption[i].value;
			}
		}

        if (f.fromDate.value != "")
        {
            fromDateEntered = true;
        }

        if (f.toDate.value != "")
        {
            toDateEntered = true;
        }

		if (dateOptionChecked &&
            fromDateEntered   &&
            toDateEntered) {

            sendTransactionAction("DailyDetailTran", "runPayoutTransactionReport", "main");
        }

		else {

            if (!fromDateEntered &&
                !toDateEntered) {

                f.selectedDateOption.value = "E";

                sendTransactionAction("DailyDetailTran", "runPayoutTransactionReport", "main");
            }

            else {

                if (!dateOptionChecked) {

                    alert("A Date Option must be selected.");
                }

                if (!fromDateEntered ||
                    !toDateEntered) {

                    alert("The full date range must be entered.");
                }
            }
		}
	}

	function closeSelectPayoutTrxReportParams() {

		sendTransactionAction("DailyDetailTran", "showReports", "main");
	}

</script>

<title>Untitled Document</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>

<body bgcolor="#DDDDDD" onLoad="init()">
<form name="payoutTrxReportForm" method="post" action="/PORTAL/servlet/RequestManager">
  <table name="taxes" width="56%" border="0" cellspacing="0" cellpadding="0" height="110" bgcolor="#DDDDDD">
    <tr>
	  <td nowrap>

      <%
          out.println("<input type=\"radio\" name=\"dateOption\" value=\"D\">");
          out.println("Due Date");
          out.println("<br>");
          out.println("<input type=\"radio\" name=\"dateOption\" value=\"E\">");
          out.println("Effective Date");
          out.println("<br>");
      %>
  	  </td>
    </tr>
    <tr>
      <td nowrap align="right">From:&nbsp;</td>
      <td nowrap align="left">
        <input:text name="fromDate"
              attributesText="id='fromDate' onBlur='DateFormat(this, this.value, event, true)' onKeyUp='DateFormat(this, this.value, event, false)' maxlength='10' size='10'"/>
        <a href="javascript:show_calendar('f.fromDate', f.fromDate.value);"><img
               src="/PORTAL/common/images/calendarIcon.gif" width="16" height="16" border="0"
               alt="Select a date from the calendar"></a>
      </td>
      <td nowrap align="left">To:&nbsp;</td>
      <td nowrap align="left">
        <input:text name="toDate"
              attributesText="id='toDate' onBlur='DateFormat(this, this.value, event, true)' onKeyUp='DateFormat(this, this.value, event, false)' maxlength='10' size='10'"/>
        <a href="javascript:show_calendar('f.toDate', f.toDate.value);"><img
               src="/PORTAL/common/images/calendarIcon.gif" width="16" height="16" border="0"
               alt="Select a date from the calendar"></a>
      </td>
    </tr>
    <tr>
      <td width="9%">&nbsp;</td>
    </tr>
    <tr>
      <td width="65%" align="right" nowrap>
        <input type="button" name="enter" value=" Enter " onClick="runPayoutTransactionReport()">
        <input type="button" name="cancel" value="Cancel" onClick="closeSelectPayoutTrxReportParams()">
      </td>
    </tr>
  </table>


<!-- ****** HIDDEN FIELDS ***** //-->
  <input type="hidden" name="transaction" value="">
  <input type="hidden" name="action" value="">
  <input type="hidden" name="selectedDateOption" value="">

</form>
</body>
</html>
