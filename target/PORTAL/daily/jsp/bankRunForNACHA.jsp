
<%@ taglib uri="/WEB-INF/taglibs-input.tld" prefix="input"%>
<%@ page errorPage="/common/jsp/error.jsp" autoFlush="true"%>
<%@ page
	import="java.util.*, fission.beans.*,
                 edit.common.vo.*,
                 edit.common.*"%>


<jsp:useBean id="bankRunPageBean" class="fission.beans.PageBean"
	scope="request" />

<%
	String[] companyNames = bankRunPageBean.getValues("companyNames");
%>

<html>
<head>

<script src="/PORTAL/common/javascript/commonJavascriptFunctions.js"></script>
<script src="/PORTAL/common/javascript/formatDateFunctions.js"
	type="text/javascript"></script>
<script src="/PORTAL/common/javascript/calendarSelector.js"
	type="text/javascript"></script>

<script language="Javascript1.2">
	

	var f = null;

	function init()
    {

		f = document.bankRunForNACHAForm;
	}


	function createBankExtract()
    {
		sendTransactionAction("DailyDetailTran", "createBankForNACHA", "main");
	}

	function bCancel()
    {
		sendTransactionAction("DailyDetailTran", "showDailySelection", "main");
	}


</script>

<title>Untitled Document</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>

<body bgcolor="#DDDDDD" onLoad="init()">
	<form name="bankRunForNACHAForm" method="post"
		action="/PORTAL/servlet/RequestManager">
		<table id="table1" name="banking" width="56%" border="0"
			cellspacing="0" cellpadding="0" height="110" bgcolor="#DDDDDD">
			<tr>
				<td width="26%" nowrap>Company Key:</td>
				<td width="9%" nowrap><select name="companyName">
						<option selected value="Please Select">Please Select</option>
						<option>All</option>

						<%
							for (int i = 0; i < companyNames.length; i++) {

							out.println("<option name=\"companyName\">" + companyNames[i] + "</option>");
						}
						%>
				</select></td>
				<td width="65%">&nbsp;</td>
			</tr>

			<br>
			<tr>
				<td nowrap align="right">Process Date:&nbsp;</td>
				<td nowrap align="left"><input:text name="processDate"
						attributesText="id='processDate' onBlur='DateFormat(this, this.value, event, true)' onKeyUp='DateFormat(this, this.value, event, false)' maxlength='10' size='10'" />
					<a
					href="javascript:show_calendar('f.processDate', f.processDate.value);"><img
						src="/PORTAL/common/images/calendarIcon.gif" width="16"
						height="16" border="0" alt="Select a date from the calendar"></a>
				</td>
			</tr>
			<tr>
				<td align="left" nowrap>ListBill EFT file: &nbsp; <input
					type="checkbox" name="isListBill">
				</td>
			</tr>
			<tr>
				<td align="left" nowrap>Create CashBatch file: &nbsp; <input
					type="checkbox" name="createCashBatchFile">
				</td>
			</tr>
			<tr>
				<td width="26%">&nbsp;</td>
				<td width="9%">&nbsp;</td>
				<td width="65%" align="right" nowrap><input type="button"
					name="enter" value=" Enter " onClick="createBankExtract()">
					<input type="button" name="cancel" value="Cancel"
					onClick="bCancel()"></td>
			</tr>
		</table>

		<!-- ****** HIDDEN FIELDS ***** //-->
		<input type="hidden" name="transaction" value=""> <input
			type="hidden" name="action" value="">

	</form>
</body>
</html>
