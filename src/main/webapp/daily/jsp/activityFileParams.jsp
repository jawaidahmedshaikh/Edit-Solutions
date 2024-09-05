<!-- ****** JAVA CODE ***** //-->
<%@ taglib uri="/WEB-INF/taglibs-input.tld" prefix="input" %>
<%@ page  errorPage="/common/jsp/error.jsp" autoFlush="true" %>
<%@ page import="java.util.*, fission.beans.*,
                 fission.utility.Util" %>

<%
    String errorMessage = Util.initString((String) request.getAttribute("ErrorMessage"), "");

    String cycleDate = Util.initString((String) request.getAttribute("cycleDate"), "");
%>

<html>
<head>

<script src="/PORTAL/common/javascript/commonJavascriptFunctions.js"></script>
<script src="/PORTAL/common/javascript/formatDateFunctions.js" type="text/javascript"></script>
<script src="/PORTAL/common/javascript/calendarSelector.js" type="text/javascript"></script>

<script language="Javascript1.2">

	var f = null;
    var errorMessage = "<%= errorMessage %>";

	function init() {

		f = document.activityFileParamsForm;

        if (errorMessage != "")
        {
            if (errorMessage == "Already Run")
            {
                var width = screen.width * .40;
                var height = screen.height * .13;

                openDialog("cbAlreadyRun", "top=0,left=0,resizable=no", width, height);

                sendTransactionAction("DailyDetailTran", "showCBAlreadyRunDialog", "cbAlreadyRun");
            }
            else
            {
                alert(errorMessage)
            }
        }
	}

	function createActivityFile()
    {
		sendTransactionAction("DailyDetailTran", "createActivityFile", "main");
	}

	function bCancel() {

		sendTransactionAction("DailyDetailTran", "showDailySelection", "main");
	}

</script>

<title>EDITSolutions - Controls And Balances Interface Params</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>

<body bgcolor="#DDDDDD" onLoad="init()">
<form name="activityFileParamsForm" method="post" action="/PORTAL/servlet/RequestManager">
  <table id="table1" name="activityFile" width="56%" border="0" cellspacing="0" cellpadding="0" bgcolor="#DDDDDD">
    <tr>
      <td nowrap align="right">Cycle Date:&nbsp;</td>
      <td nowrap align="left">
        <input:text name="cycleDate"
              attributesText="id='cycleDate' onBlur='DateFormat(this, this.value, event, true)' onKeyUp='DateFormat(this, this.value, event, false)' maxlength='10' size='10'"/>
        <a href="javascript:show_calendar('f.cycleDate', f.cycleDate.value);"><img
               src="/PORTAL/common/images/calendarIcon.gif" width="16" height="16" border="0"
               alt="Select a date from the calendar"></a>
      </td>
    </tr>
    <tr>
      <td nowrap colspan="2">&nbsp;</td>
    </tr>
    <tr>
      <td nowrap>&nbsp;</td>
      <td nowrap align="right">
        <input type="button" name="enter" value=" Enter " onClick="createActivityFile()">
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