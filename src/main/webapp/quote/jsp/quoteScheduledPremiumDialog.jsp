
<!-- ****** JAVA CODE ***** //-->

<%@ page  errorPage="/common/jsp/error.jsp" autoFlush="true" %>
<%@ page import="java.util.*,
                 edit.common.*,
                 edit.common.vo.*,
                 fission.beans.*,
                 fission.utility.Util" %>

<jsp:useBean id="formBean"
    class="fission.beans.PageBean" scope="request"/>

<jsp:useBean id="quoteMainSessionBean"
    class="fission.beans.SessionBean" scope="session"/>
<%
    PageBean mainBean = quoteMainSessionBean.getPageBean("formBean");
	String scheduledPremiumAmount = mainBean.getValue("scheduledPremiumAmount");
	String totalFaceAmount = mainBean.getValue("totalFaceAmount");
    String companyStructureId = Util.initString(mainBean.getValue("companyStructureId"), "0");
    
    EDITDate now = new EDITDate();
    String effectiveDate = "" + now.getMMDDYYYYDate();
%>

<html>
<head>

<script src="/PORTAL/common/javascript/formatDateFunctions.js"></script>
<script src="/PORTAL/common/javascript/calendarSelector.js"></script>
<link rel="stylesheet"
	href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
<script src="//code.jquery.com/jquery-1.10.2.js"></script>
<script src="//code.jquery.com/ui/1.11.4/jquery-ui.js"></script>
<script src="/PORTAL/common/javascript/jquery.maskedinput.js"></script>
<link rel="stylesheet" href="/resources/demos/style.css">

<script>
  $('document').ready(function() {
    $( ".datepicker" ).datepicker({
	       dateFormat: "mm/dd/yy",
	       altFormat: "yy/mm/dd",
	       showOn: "button",
	       buttonImage: "/PORTAL/common/images/calendarIcon.gif" });
    $( ".datepicker" ).mask("99/99/9999", {placedholder:"mm/dd/yyyy"});
  });
</script>

<script language="Javascript1.2">

	var f = null;

	function init() {

		f = document.scheduledPremiumForm;

		window.resizeTo(getPreferredWidth(), getPreferredHeight());
	}

	function getPreferredWidth() {

		return .60 * document.all.table1.offsetWidth;
	}

	function getPreferredHeight() {

		return 5.50 * document.all.table1.offsetHeight;
	}

	function openDialog(theURL,winName,features,transaction,action) {

	  dialog = window.open(theURL,winName,features);

	  sendTransactionAction(transaction, action, winName);
	}

	function showTransactionDefault() {

/* 		f.scheduledPremiumAmount.value = "5.55";
 */
		sendTransactionAction("QuoteDetailTran", "saveScheduledPremium", "contentIFrame");
		window.close();
	}

	function sendTransactionAction(transaction, action, target) {

		f.transaction.value=transaction;
		f.action.value=action;

		f.target = target;

		f.submit();
	}

	function cancelDialog() {

		window.close();

	}

    var now = "<%=now.getMMDDYYYYDate()%>";
    function checkEffectiveDate(efDateElement, date) {
        var now = "<%=now.getMMDDYYYYDate()%>";
        var nowYYYYMMDD = convertMDYtoYMDNoSlash(now);
        var dateFormatted = convertMDYtoYMDNoSlash(date);
        var greaterThanOrEqual = Number(dateFormatted) >= Number(nowYYYYMMDD);

        if (!greaterThanOrEqual) {
            alert("Effective Date may not be back-dated.")
            efDateElement.value = now;
            return false;
        } else {
            return true;
        }
    }
	
</script>

<title>Enter Scheduled Premium</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>

<body bgcolor="#DDDDDD" onLoad="init()">
<form name="scheduledPremiumForm" method="post" action="/PORTAL/servlet/RequestManager">
  <table id="table1" width="51%" border="0" cellspacing="0" cellpadding="0" bgcolor="#DDDDDD">
    <tr>
      <td align="left" nowrap >Scheduled Premium:&nbsp;
        <input type="text" name="scheduledPremiumAmount" maxlength="20" size="20" value="<%= scheduledPremiumAmount %>" CURRENCY>
      </td>
    </tr>
    <tr>
	  <td align="left" nowrap>Effective Date:&nbsp;
		<input class="datepicker" type="text"
			id="effectiveDate"
			value="<%=effectiveDate%>"
			name="effectiveDate" size="10">
	  </td>
	</tr>
    <tr>
      <td colspan="2" align="left">
        <input type="button" name="enter" value="Enter" onClick="showTransactionDefault()">
        <input type="button" name="cancel" value="Cancel" onClick="cancelDialog()">
      </td>
    </tr>
  </table>
Note: Any changes to the main page will be lost when this change is saved. If you have changes you would like saved, cancel this dialog and save them first.
  <!-- ****** HIDDEN FIELDS ***** //-->
<input type="hidden" name="transaction" value="">
<input type="hidden" name="action"      value="">
<input type="hidden" name="scheduledPremiumAmount"  value="<%= scheduledPremiumAmount %>">
 <input type="hidden" name="effectiveDate" value="<%= effectiveDate %>">

</form>
</body>
</html>
