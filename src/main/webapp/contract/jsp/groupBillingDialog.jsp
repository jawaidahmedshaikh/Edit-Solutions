<%@ taglib uri="/WEB-INF/taglibs-input.tld" prefix="input"%>
<%@ page
	import="edit.common.EDITDate,
                 fission.utility.Util,
                 edit.common.vo.CodeTableVO,
                 edit.common.CodeTableWrapper,
                 edit.services.db.hibernate.*,
                 java.util.*,
                 contract.*,
                 client.*,
                 engine.*,
                 edit.portal.common.session.*,
                 edit.portal.taglib.InputSelect,
                 group.ContractGroup,
                 security.Operator,
                 edit.portal.exceptions.PortalEditingException"%>
<!--
 * User: dlataill
 * Date: May 1, 2007
 * Time: 8:39:50 AM
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 -->
<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<%-- ****************************** BEGIN Java Code ****************************** --%>
<jsp:useBean id="contractGroup" class="group.ContractGroup"
	scope="request" />
<jsp:useBean id="clientDetail" class="client.ClientDetail"
	scope="request" />
<jsp:useBean id="billSchedule" class="billing.BillSchedule"
	scope="request" />

<%
	String responseMessage = (String) request
			.getAttribute("responseMessage");

	PortalEditingException editingException = (PortalEditingException) session
			.getAttribute("portalEditingException");
	String editingExceptionExists = editingException == null
			? "false"
			: "true";

	CodeTableWrapper codeTableWrapper = CodeTableWrapper.getSingleton();

	Company[] companies = Company.find_All_ProductLevel();

	InputSelect billingCompanyCTs = new InputSelect(companies,
			Company.class, new String[]{"companyName"}, "companyName",
			InputSelect.ASCENDING);

	Operator[] operators = Operator
			.findByOperatorType(Operator.CASE_REP);

	InputSelect operatorCTs = new InputSelect(operators, Company.class,
			new String[]{"firstName", "lastName"}, new String[]{
					"firstName", "lastName"}, InputSelect.ASCENDING);

	CodeTableVO[] frequencyVOCTs = codeTableWrapper
			.getCodeTableEntries("BILLFREQUENCY");
	InputSelect frequencyCTs = new InputSelect(frequencyVOCTs,
			CodeTableVO.class, new String[]{"codeDesc"}, "code",
			InputSelect.ASCENDING);

	CodeTableVO[] weekDayVOCTs = codeTableWrapper
			.getCodeTableEntries("WEEKDAY");
	InputSelect weekDayCTs = new InputSelect(weekDayVOCTs,
			CodeTableVO.class, new String[]{"codeDesc"}, "code",
			InputSelect.ASCENDING);

	CodeTableVO[] sortOptionVOCTs = codeTableWrapper
			.getCodeTableEntries("SORTOPTION");
	InputSelect sortOptionCTs = new InputSelect(sortOptionVOCTs,
			CodeTableVO.class, new String[]{"codeDesc"}, "code",
			InputSelect.ASCENDING);

	CodeTableVO[] billingConsolidationVOCTs = codeTableWrapper
			.getCodeTableEntries("YESNO");
	InputSelect billingConsolidationCTs = new InputSelect(
			billingConsolidationVOCTs, CodeTableVO.class,
			new String[]{"codeDesc"}, "code", InputSelect.ASCENDING);

	CodeTableVO[] socialSecurityMaskVOCTs = CodeTableWrapper
			.getSingleton().getCodeTableEntries("SOCIALSECURITYMASK");
	InputSelect socialSecurityMaskCTs = new InputSelect(
			socialSecurityMaskVOCTs, CodeTableVO.class,
			new String[]{"codeDesc"}, "code", InputSelect.ASCENDING);

	CodeTableVO[] statusVOCTs = CodeTableWrapper.getSingleton()
			.getCodeTableEntries("BILLSTATUS");
	InputSelect statusCTs = new InputSelect(statusVOCTs,
			CodeTableVO.class, new String[]{"codeDesc"}, "code",
			InputSelect.ASCENDING);

	CodeTableVO[] monthVOCTs = codeTableWrapper
			.getCodeTableEntries("MONTH");
	InputSelect monthCTs = new InputSelect(monthVOCTs,
			CodeTableVO.class, new String[]{"codeDesc"}, "code",
			InputSelect.ASCENDING);

	CodeTableVO[] numberOfMonthsVOCTs = codeTableWrapper
			.getCodeTableEntries("NUMBEROFMONTHS");
	InputSelect numberOfMonthsCTs = new InputSelect(
			numberOfMonthsVOCTs, CodeTableVO.class,
			new String[]{"codeDesc"}, "code", InputSelect.ASCENDING);

	CodeTableVO[] prdFrequencyVOCTs = codeTableWrapper
			.getCodeTableEntries("DEDUCTIONFREQUENCY");
	InputSelect prdFrequencyCTs = new InputSelect(prdFrequencyVOCTs,
			CodeTableVO.class, new String[]{"codeDesc"}, "code",
			InputSelect.ASCENDING);

	String billingCompanyNumber = "";
	String lastBillDueDate = "";
	if (billSchedule.getLastBillDueDate() != null) {
		lastBillDueDate = billSchedule.getLastBillDueDate().toString();
	}

	String billingCompany = billSchedule.getBillingCompanyCT();

	for (int i = 0; i < companies.length; i++) {
		if (companies[i].getCompanyName().equalsIgnoreCase(
				billingCompany)) {
			billingCompanyNumber = Util.initString(
					companies[i].getBillingCompanyNumber(), "");
			break;
		}
	}
	
	EDITDate currentDate = new EDITDate();
	String strCurrentDate = currentDate.getFormattedDate();
	
%>
<%-- ****************************** End Java Code ****************************** --%>

<html>
<head>
<title>EDIT SOLUTIONS - Group Billing</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta http-equiv="Cache-Control" content="no-store">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Expires" content="0">

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

<link href="/PORTAL/common/css/EDITSolutions.css" rel="stylesheet"
	type="text/css">

<%-- ****************************** BEGIN JavaScript ****************************** --%>
<script src="/PORTAL/common/javascript/commonJavascriptFunctions.js"></script>
<script src="/PORTAL/common/javascript/widgetFunctions.js"></script>
<script src="/PORTAL/common/javascript/scrollTable.js"></script>
<script src="/PORTAL/common/javascript/formatDateFunctions.js"
	type="text/javascript"></script>
<script src="/PORTAL/common/javascript/calendarSelector.js"
	type="text/javascript"></script>

<script>

    var f = null;

    var responseMessage = "<%=responseMessage%>";

    var shouldShowLockAlert = true;

    var companies = new Array(<%=companies.length%>);

    var operators = new Array(<%=operators.length%>);
    
    var editingExceptionExists = "<%=editingExceptionExists%>";

    var changeInd = false;
    var varMonthChangeInd = false;

    var lastBillDueDate = "<%=lastBillDueDate%>";

    /**
     * Called after the body of the page is loaded.
     */
    function init()
    {
        f = document.theForm;

        checkForResponseMessage();

        // Initialize scroll tables
<%--        initScrollTable(f.getElementById("GroupBillingTableModel"));--%>

        initializeCompanies();

        initializeOperators();
        
        checkForEditingException();
    }

    function initializeCompanies()
    {
        <%for (int i = 0; i < companies.length; i++) {%>
                companies[<%=i%>] = new Object();
                companies[<%=i%>].companyName = "<%=companies[i].getCompanyName()%>";
                companies[<%=i%>].billingCompanyNumber = "<%=companies[i].getBillingCompanyNumber()%>";
        <%}%>
    }

    function initializeOperators()
    {
        <%for (int i = 0; i < operators.length; i++) {%>
                operators[<%=i%>] = new Object();
                operators[<%=i%>].repName = "<%=operators[i].getFirstName() + " "
						+ operators[i].getLastName()%>";
                operators[<%=i%>].repExt = "<%=Util.initString(operators[i].getTelephoneExtension(),"")%>";
<%}%>
	}

	function showLockAlert() {
<%--        if (shouldShowLockAlert == true)--%>
	
<%--        {--%>
	
<%--            alert("The Case can not be edited.");--%>
	
<%----%>
	
<%--            return false;--%>
	
<%--        }--%>
	}

	function setBillingCompanyNumber() {
		selectedCompany = f.billingCompanyCT.options[f.billingCompanyCT.selectedIndex].value;

		for (var i = 0; i < companies.length; i++) {
			if (companies[i].companyName == selectedCompany) {
				f.billingCompanyNumber.value = companies[i].billingCompanyNumber;
			}
		}
	}

	function setRepExt() {
		selectedRep = f.repName.options[f.repName.selectedIndex].value;

		for (var i = 0; i < operators.length; i++) {
			if (operators[i].repName == selectedRep) {
				f.repPhoneNumber.value = operators[i].repExt;
			}
		}
	}

	function setBillFreqChangeInd() {
		changeInd = true;

		selectedCode = f.billingModeCT.options[f.billingModeCT.selectedIndex].text;

		if (selectedCode == "Variable Monthly") {
			varMonthChangeInd = true;
		} else {
			varMonthChangeInd = false;
		}
	}

	function setChangeInd() {
		changeInd = true;
	}

	function setIndForDateCheck() {
		changeInd = true;
	}

	function validateDeductionFrequency() {
		
		var monthDeductionFrequencies = ["48", "40", "36", "24", "20", "18", "12", "10", "09" ];
		var variableDeductionFrequencies = ["26", "52"];
		var quartDeductionFrequencies = ["04"];
		var semiAnnDeductionFrequencies = ["02"];
		var annualDeductionFrequencies = ["01"];
		
		var billingModeCT = f.billingModeCT.options[f.billingModeCT.selectedIndex].text;
		var deductionFrequencyCT = f.deductionFrequencyCT.options[f.deductionFrequencyCT.selectedIndex].text;
		
		if (billingModeCT == "Monthly") {
			for(x = 0; x < monthDeductionFrequencies.length; x++) {
				if (deductionFrequencyCT == monthDeductionFrequencies[x]) {
					return true;
				}
			}
		} else if(billingModeCT == "Variable Monthly" || billingModeCT == "13thly") {
			for(x = 0; x < variableDeductionFrequencies.length; x++) {
				if (deductionFrequencyCT == variableDeductionFrequencies[x]) {
					return true;
				}
			}
		} else if(billingModeCT == "Quarterly") {
			for(x = 0; x < quartDeductionFrequencies.length; x++) {
				if (deductionFrequencyCT == quartDeductionFrequencies[x]) {
					return true;
				}
			}
		} else if(billingModeCT == "SemiAnnual") {
			for(x = 0; x < semiAnnDeductionFrequencies.length; x++) {
				if (deductionFrequencyCT == semiAnnDeductionFrequencies[x]) {
					return true;
				}
			}
		} else if(billingModeCT == "Annual") {
			for(x = 0; x < annualDeductionFrequencies.length; x++) {
				if (deductionFrequencyCT == annualDeductionFrequencies[x]) {
					return true;
				}
			}
		} else {
			return false;
		}
	}
	
	function validateSkipMonths() {
		
		var billingModeCT = f.billingModeCT.options[f.billingModeCT.selectedIndex].text;
		var deductionFrequencyCT = f.deductionFrequencyCT.options[f.deductionFrequencyCT.selectedIndex].text;
		
		var skipMonthStart1CT = f.skipMonthStart1CT.options[f.skipMonthStart1CT.selectedIndex].text;
		var skipNumberOfMonths1CT = f.skipNumberOfMonths1CT.options[f.skipNumberOfMonths1CT.selectedIndex].text;
		
		var skipMonthStart2CT = f.skipMonthStart2CT.options[f.skipMonthStart2CT.selectedIndex].text;
		var skipNumberOfMonths2CT = f.skipNumberOfMonths2CT.options[f.skipNumberOfMonths2CT.selectedIndex].text;
		
		var skipMonthStart3CT = f.skipMonthStart3CT.options[f.skipMonthStart3CT.selectedIndex].text;
		var skipNumberOfMonths3CT = f.skipNumberOfMonths3CT.options[f.skipNumberOfMonths3CT.selectedIndex].text;
		
		var totalSkipMonths = 0;
		var validDeductionFrequency = true;
		
		var missingSkipMonthString = "Oops ... You Forgot to Select Number of Skip Months Where Skip Month Start Has Been Chosen!";
		var missingSkipStartString = "Oops ... You Forgot to Select Skip Month Start Where Number of Skip Months Has Been Chosen!";
		var wrongBillingModeString = "Skip Months Allowed For Monthly Billing Only!";
		
		if (skipMonthStart1CT != "Please Select") {
			if (billingModeCT != "Monthly") {
				return wrongBillingModeString;
			}
			if (skipNumberOfMonths1CT != "Please Select") {
				totalSkipMonths += Number(skipNumberOfMonths1CT);
			} else {
				return missingSkipMonthString;
			}
		} else {
			if (skipNumberOfMonths1CT != "Please Select") {
				return missingSkipStartString;
			}
		}
		
		if (skipMonthStart2CT != "Please Select") {
			if (billingModeCT != "Monthly") {
				return wrongBillingModeString;
			}
			if (skipNumberOfMonths2CT != "Please Select") {
				totalSkipMonths += Number(skipNumberOfMonths2CT);
			} else {
				return missingSkipMonthString;
			}
		} else {
			if (skipNumberOfMonths2CT != "Please Select") {
				return missingSkipStartString;
			}
		}
		
		if (skipMonthStart3CT != "Please Select") {
			if (billingModeCT != "Monthly") {
				return wrongBillingModeString;
			}
			if (skipNumberOfMonths3CT != "Please Select") {
				totalSkipMonths += Number(skipNumberOfMonths3CT);
			} else {
				return missingSkipMonthString;
			}
		} else {
			if (skipNumberOfMonths3CT != "Please Select") {
				return missingSkipStartString;
			}
		}
		
		if (deductionFrequencyCT == 9) {
			if (totalSkipMonths != 3) {
				validDeductionFrequency = false;
			}
		} else if (deductionFrequencyCT == 18) {
			if (totalSkipMonths != 3) {
				validDeductionFrequency = false;
			}
		} else if (deductionFrequencyCT == 36) {
			if (totalSkipMonths != 3) {
				validDeductionFrequency = false;
			}
		} else if (deductionFrequencyCT == 10) {
			if (totalSkipMonths != 2) {
				validDeductionFrequency = false;
			}
		} else if (deductionFrequencyCT == 20) {
			if (totalSkipMonths != 2) {
				validDeductionFrequency = false;
			}
		} else if (deductionFrequencyCT == 40) {
			if (totalSkipMonths != 2) {
				validDeductionFrequency = false;
			}
		} else {
			if (totalSkipMonths != 0) {
				validDeductionFrequency = false;
			}
		}
			
		if (!validDeductionFrequency) {
			return "Number of Skip Months Invalid";
		} else {
			return "";
		}
	}
	
	function validateDeductionDate() {
		
		var firstBillDueDateDOW = new Date($('#firstBillDueDateDP').val()).getDay();
		var firstDeductionDateDOW = new Date ($('#firstDeductionDateDP').val()).getDay();
		
		var sameDayOfWeek = false;
				
		if (firstBillDueDateDOW == firstDeductionDateDOW) {
			sameDayOfWeek = true;
		}
		
		return sameDayOfWeek;
	}
	
	function saveGroupBillingChange() { 
		
		var varMonthDedChangeStartDateDP = $('#varMonthDedChangeStartDateDP').val();
		var billChangeStartDateDP = $('#billChangeStartDateDP').val();
		var changeEffectiveDateDP = $('#changeEffectiveDateDP').val();
		var firstBillDueDateDP = $('#firstBillDueDateDP').val();
		var billingModeCT = f.billingModeCT.options[f.billingModeCT.selectedIndex].text;
		var deductionFrequencyCT = f.deductionFrequencyCT.options[f.deductionFrequencyCT.selectedIndex].text;
		var weekDayCT = f.weekDayCT.options[f.weekDayCT.selectedIndex].text;
		
		var changeDate = new Date(changeEffectiveDateDP);
		var currentEDITDate = "<%= strCurrentDate %>";
		var currentDate = new Date();
				
		var skipMonthsMessage = validateSkipMonths();
		
		if (skipMonthsMessage != "") {
			alert(skipMonthsMessage);
			return;
		}
		
		if (!validateDeductionFrequency()) {
			alert("Deduction Frequency Invalid for Selected Billing Frequency");
			return;
		}
		
		if (varMonthChangeInd == true && varMonthDedChangeStartDateDP == ""
				&& lastBillDueDate != "") {
			alert("Variable Month Deduction Change Start Date Required");
			return;
		}

		if (changeInd == true && billChangeStartDateDP == "" && lastBillDueDate != "") {
			alert("Bill Change Start Date Required");
			return;
		}
		
		//if (changeInd == true && lastBillDueDate != "") {
			// Set ChangeEffectiveDate to current date				
			//f.changeEffectiveDateDP.value = currentEDITDate;
			
			//changeEffectiveDateDP = f.changeEffectiveDateDP.value;
			//changeDate = new Date(changeEffectiveDateDP);
		//}

		//if (changeInd == true && changeEffectiveDateDP == "" && lastBillDueDate != "") {
			//alert("Change Effective Date Required");
			//return;
		//}
		
		//if (changeDate > currentDate) {
			//alert("Change Effective Date Cannot Be Future-Dated... Please use the current date or earlier.");
			//return;
		//}
		
		if (firstBillDueDateDP == "") {
			alert("First Bill Due Date Required");
			return;
		}
		
		if (billingModeCT == "Variable Monthly") {
			if (weekDayCT == "Please Select") {
				alert ("Week Day Required for Variable Monthly Billing");
				return;
			}
			if (!validateDeductionDate()) {
				alert("First Bill Due and First Deduction Dates Must Be the Same Day of the Week");
				return;
			}
		} else {
			if (weekDayCT != "Please Select") {
				alert ("Week Day Allowed for Variable Monthly Billing Only ... Please De-Select Week Day Value");
				return;
			}
		}
		
		sendTransactionAction("CaseDetailTran", "saveGroupBillingChange",	"groupBillingDialog");
	}

	function closeBilling() {
		sendTransactionAction("CaseDetailTran", "showGroupSummary", "_main");
		window.close();
	}

	function cancelGroupBillingChange() {
	}

	function resetFormValues() {
<%--        f.groupNumber.value = "";--%>
	
<%--        f.groupName.value = "";--%>
	
<%--        f.effectiveMonth.value = "";--%>
	
<%--        f.effectiveDay.value = "";--%>
	
<%--        f.effectiveYear.value = "";--%>
	
<%--        f.terminationMonth.value = "";--%>
	
<%--        f.terminationDay.value = "";--%>
	
<%--        f.terminationYear.value = "";--%>
	
<%--        f.statusCT.selectedIndex = 0;--%>
	
<%--        f.operator.value = "";--%>
	
<%--        f.creationDay.value = "";--%>
	
<%--        f.creationMonth.value = "";--%>
	
<%--        f.creationYear.value = "";--%>
	
<%----%>
	
<%--        sendTransactionAction("CaseDetailTran", "cancelGroupEntry", "main");--%>
	}

	/**
	 * While a tranaction is being saved, we want to disable all buttons to prevent double-saves.
	 */
	function disableActionButtons() {
		document.all.trxMessage.innerHTML = "<font color='blue' face=''>Processing...<font>";

		document.all.btnSave.style.backgroundColor = "#99BBBB";
		document.all.btnCancel.style.backgroundColor = "#99BBBB";

		document.all.btnSave.disabled = true;
		document.all.btnCancel.disabled = true;
	}

	function checkForEditingException() {
		if (editingExceptionExists == "true") {
			var width = .75 * screen.width;
			var height = screen.height / 3;

			openDialog("exceptionDialog", "resizable=no", width, height);

			sendTransactionAction("CaseDetailTran",
					"showEditingExceptionDialog", "exceptionDialog");
		}
	}

	function continueTransactionAction(transaction, action) {
		f.ignoreEditWarnings.value = "true";
		sendTransactionAction(transaction, action, "");
	}
</script>
<%-- ****************************** END JavaScript ****************************** --%>

</head>
<body class="dialog" onLoad="init()">
	<form name="theForm" method="post"
		action="/PORTAL/servlet/RequestManager">

		<%-- ****************************** BEGIN Form Data ****************************** --%>
		<span id="mainContent"
			style="border-style: solid; border-width: 1; position: relative; width: 100%; height: 70%; top: 0; left: 0; z-index: 0; overflow: scroll">
			<%--    BEGIN Form Content --%>
			<table width="100%" height="100%" border="0" cellspacing="0"
				cellpadding="0">
				<tr>
					<td align="left" nowrap>Group Number:&nbsp; <input:text
							name="contractGroupNumber" bean="contractGroup"
							attributesText="id='contractGroupNumber' disabled" size="20" />
						&nbsp;&nbsp;&nbsp;&nbsp;
					</td>
					<td align="left" nowrap colspan="4">Group Name:&nbsp; <input:text
							name="name" bean="clientDetail"
							attributesText="id='contractGroupName' disabled" size="50" />
					</td>
				</tr>
				<tr>
					<td align="left" valign="top" nowrap colspan="5">&nbsp;</td>
				</tr>
				<tr>
					<td align="left" valign="top" colspan="5" rowpsan="4"><span
						style="width: 27%; position: relative; top: 0; left: 0; z-index: 0; overflow: visible">
							<table width="100%" height="100%">
								<tr>
									<td align="left" valign="top" nowrap>Billing
										Company:&nbsp;</td>
									<td align="left" nowrap="nowrap"><input:select
											bean="billSchedule" name="billingCompanyCT"
											options="<%=billingCompanyCTs.getOptions()%>"
											attributesText="id='billingCompanyCT' onChange='setBillingCompanyNumber()' " /></td>
								</tr>
								<tr>
									<td align="left" valign="top" nowrap>Billing
										Frequency:&nbsp;</td>
									<td align="left" nowrap="nowrap">
										<%--                        <input:select bean="billSchedule" name="billingModeCT" options="<%= frequencyCTs.getOptions() %>"--%>
										<%--                                        attributesText="id='billingModeCT' "/>--%>

										<select name="billingModeCT" onChange="setBillFreqChangeInd()">
											<option value="null">Please Select</option>
											<%
												for (int i = 0; i < frequencyVOCTs.length; i++) {
													String codeDesc = frequencyVOCTs[i].getCodeDesc();
													String code = frequencyVOCTs[i].getCode();

													if ((billSchedule.getBillingModeCT() != null)
															&& (billSchedule.getBillingModeCT()
																	.equalsIgnoreCase(code))) {
														out.println("<option selected name=\"id\" value=\"" + code
																+ "\">" + codeDesc + "</option>");
													} else {
														out.println("<option name=\"id\" value=\"" + code + "\">"
																+ codeDesc + "</option>");
													}
												}
											%>
									</select>
									</td>
								</tr>
								<tr>
									<td align="left" valign="top" nowrap>Deduction
										Frequency:&nbsp;</td>
									<td align="left" nowrap="nowrap">
										<%--                                <input:select bean="billSchedule" name="deductionFrequencyCT" options="<%= prdFrequencyCTs.getOptions() %>"--%>
										<%--                                        attributesText="id='deductionFrequencyCT' "/>--%>

										<select name="deductionFrequencyCT" onChange="setChangeInd()">
											<option value="null">Please Select</option>
											<%
												for (int i = 0; i < prdFrequencyVOCTs.length; i++) {
													String codeDesc = prdFrequencyVOCTs[i].getCodeDesc();
													String code = prdFrequencyVOCTs[i].getCode();

													if ((billSchedule.getDeductionFrequencyCT() != null)
															&& (billSchedule.getDeductionFrequencyCT()
																	.equalsIgnoreCase(code))) {
														out.println("<option selected name=\"id\" value=\"" + code
																+ "\">" + codeDesc + "</option>");
													} else {
														out.println("<option name=\"id\" value=\"" + code + "\">"
																+ codeDesc + "</option>");
													}
												}
											%>
									</select>
									</td>
								</tr>
								<tr>
									<td align="left" valign="top" nowrap>First Deduction
										Date:&nbsp;</td>
									<td align="left" nowrap><input class="datepicker"
										type="text" id="firstDeductionDateDP"
										value="<%=billSchedule.getUIFirstDeductionDate()%>"
										name="firstDeductionDate" size="10"></td>
								</tr>
							</table>
					</span> <span
						style="width: 17%; position: relative; top: 0; left: 0; z-index: 0; overflow: visible">
							<table width="100%" height="100%">
								<tr>
									<td align="left" nowrap>&nbsp;</td>
								</tr>
							</table>
					</span>
						<fieldset
							style="width: 28%; border-style: solid; border-width: 1px; border-color: gray">
							<span
								style="position: relative; top: 0; left: 0; z-index: 0; overflow: visible">
								<table width="100%" height="100%" border="0" cellspacing="0"
									cellpadding="1">
									<tr>
										<td align="left" nowrap>Next Bill Extract Date:&nbsp;</td>
										<td align="left" nowrap><input:text
												name="nextBillExtractDate" bean="billSchedule"
												attributesText="disabled id='nextBillExtractDate' onBlur='DateFormat(this, this.value, event, true)' onKeyUp='DateFormat(this, this.value, event, false)' maxlength='10' size='10'" />
										</td>
									</tr>
									<tr>
										<td align="left" nowrap>Next Bill Due Date:&nbsp;</td>
										<td align="left" nowrap><input:text
												name="nextBillDueDate" bean="billSchedule"
												attributesText="disabled id='nextBillDueDate' onBlur='DateFormat(this, this.value, event, true)' onKeyUp='DateFormat(this, this.value, event, false)' maxlength='10' size='10'" />
										</td>
									</tr>
									<tr>
										<td align="left" nowrap>Last Bill Due Date:&nbsp;</td>
										<td align="left" nowrap><input:text
												name="lastBillDueDate" bean="billSchedule"
												attributesText="disabled id='lastBillDueDate' onBlur='DateFormat(this, this.value, event, true)' onKeyUp='DateFormat(this, this.value, event, false)' maxlength='10' size='10'" />
										</td>
									</tr>
									<tr>
										<td align="left" nowrap colspan="2">&nbsp;</td>
									</tr>
								</table>
							</span>
						</fieldset></td>
				</tr>
				<tr>
					<td align="left" valign="top" colspan="5" rowpsan="3"><span
						style="width: 27%; position: relative; top: 0; left: 0; z-index: 0; overflow: visible">
							<table width="100%" height="100%">
								<tr>
									<td align="left" valign="top" nowrap>First Bill Due
										Date:&nbsp;</td>
									<td align="left" nowrap><input class="datepicker"
										type="text" id="firstBillDueDateDP"
										value="<%=billSchedule.getUIFirstBillDueDate()%>"
										name="firstBillDueDate" size="10"></td>
								</tr>
								<tr>
									<td align="left" valign="top" nowrap>Lead Days O/R:&nbsp;
									</td>
									<td align="left" valign="top" nowrap><input:text
											name="leadDaysOR" bean="billSchedule"
											attributesText="id='leadDaysOR'" size="3" /></td>
								</tr>
								<tr>
									<td align="left" valign="top" nowrap>Week Day:&nbsp;</td>
									<td align="left" nowrap="nowrap">
										<%--                                        <input:select bean="billSchedule" name="weekDayCT" options="<%= weekDayCTs.getOptions() %>"--%>
										<%--                                        attributesText="id='weekDayCT' onChange='setIndForDateCheck()' "/>--%>

										<select name="weekDayCT" onChange="setIndForDateCheck()">
											<option value="null">Please Select</option>
											<%
												String weekDay = billSchedule.getWeekDayCT();

												for (int i = 0; i < weekDayVOCTs.length; i++) {
													String codeDesc = weekDayVOCTs[i].getCodeDesc();
													String code = weekDayVOCTs[i].getCode();

													if (weekDay != null && weekDay.equalsIgnoreCase(code)) {
														out.println("<option selected name=\"id\" value=\"" + code
																+ "\">" + codeDesc + "</option>");
													} else {
														out.println("<option name=\"id\" value=\"" + code + "\">"
																+ codeDesc + "</option>");
													}
												}
											%>
									</select>
									</td>
								</tr>
								<tr>
									<td align="left" nowrap colspan="2">&nbsp;</td>
								</tr>
							</table>
					</span> <span
						style="width: 17%; position: relative; top: 0; left: 0; z-index: 0; overflow: visible">
							<table width="100%" height="100%">
								<tr>
									<td align="left" nowrap>&nbsp;</td>
								</tr>
							</table>
					</span> <span
						style="width: 28%; position: relative; top: 0; left: 0; z-index: 0; overflow: visible">
							<table width="100%" height="100%">
								<tr>
									<td align="left" valign="top" nowrap>Sort Option:&nbsp;</td>
									<td align="left" nowrap="nowrap"><input:select
											bean="billSchedule" name="sortOptionCT"
											options="<%=sortOptionCTs.getOptions()%>"
											attributesText="id='sortOptionCT'" /></td>
								</tr>
								<tr>
									<td align="left" valign="top" nowrap>Billing
										Consolidation:&nbsp;</td>
									<td align="left" nowrap="nowrap"><input:select
											bean="billSchedule" name="billingConsolidationCT"
											options="<%=billingConsolidationCTs.getOptions()%>"
											attributesText="id='billingConsolidationCT'" /></td>
								</tr>
								<tr>
									<td align="left" valign="top" nowrap>Social Security
										Mask:&nbsp;</td>
									<td align="left" nowrap="nowrap"><input:select
											bean="billSchedule" name="socialSecurityMaskCT"
											options="<%=socialSecurityMaskCTs.getOptions()%>"
											attributesText="id='socialSecurityMaskCT'" /></td>
								</tr>
							</table>
					</span></td>
				</tr>
				<tr>
					<td align="left" valign="top" nowrap colspan="5" rowspan="3">
						<fieldset
							style="width: 27%; border-style: solid; border-width: 1px; border-color: gray">
							<span
								style="position: relative; top: 0; left: 0; z-index: 0; overflow: visible">
								<table width="100%" height="100%" border="0" cellspacing="1"
									cellpadding="1">
									<tr>
										<td align="left" nowrap>Skip Month Start</td>
										<td align="left" nowrap># of Month</td>
									</tr>
									<tr>
										<td align="left" nowrap="nowrap"><input:select
												bean="billSchedule" name="skipMonthStart1CT"
												options="<%=monthCTs.getOptions()%>"
												attributesText="id='skipMonthStart1CT'" /></td>
										</td>
										<td align="left" nowrap="nowrap"><input:select
												bean="billSchedule" name="skipNumberOfMonths1CT"
												options="<%=numberOfMonthsCTs.getOptions()%>"
												attributesText="id='skipNumberOfMonths1CT'" /></td>
										</td>
									</tr>
									<tr>
										<td align="left" nowrap="nowrap"><input:select
												bean="billSchedule" name="skipMonthStart2CT"
												options="<%=monthCTs.getOptions()%>"
												attributesText="id='skipMonthStart2CT'" /></td>
										</td>
										<td align="left" nowrap="nowrap"><input:select
												bean="billSchedule" name="skipNumberOfMonths2CT"
												options="<%=numberOfMonthsCTs.getOptions()%>"
												attributesText="id='skipNumberOfMonths2CT'" /></td>
										</td>
									</tr>
									<td align="left" nowrap="nowrap"><input:select
											bean="billSchedule" name="skipMonthStart3CT"
											options="<%=monthCTs.getOptions()%>"
											attributesText="id='skipMonthStart3CT'" /></td>
									</td>
									<td align="left" nowrap="nowrap"><input:select
											bean="billSchedule" name="skipNumberOfMonths3CT"
											options="<%=numberOfMonthsCTs.getOptions()%>"
											attributesText="id='skipNumberOfMonths3CT'" /></td>
									</td>
									</tr>
								</table>
							</span>
						</fieldset> <span
						style="width: 17%; position: relative; top: 0; left: 0; z-index: 0; overflow: visible">
							<table width="100%" height="100%">
								<tr>
									<td align="left" nowrap>&nbsp;</td>
								</tr>
							</table>
					</span>
						<fieldset
							style="width: 28%; border-style: solid; border-width: 1px; border-color: gray">
							<span
								style="position: relative; top: 0; left: 0; z-index: 0; overflow: visible">
								<table width="100%" height="100%" border="0" cellspacing="0"
									cellpadding="1">
									<tr>
										<td align="left" nowrap>&nbsp;</td>
										<td align="left" nowrap># Copies</td>
									</tr>
									<tr>
										<td align="left" nowrap>Group:&nbsp;</td>
										<td align="left" nowrap><input:text
												name="numberOfCopiesGroup" bean="billSchedule"
												attributesText="id='numberOfCopiesGroup'" size="3" /></td>
									</tr>
									<tr>
										<td align="left" nowrap>Agent:&nbsp;</td>
										<td align="left" nowrap><input:text
												name="numberOfCopiesAgent" bean="billSchedule"
												attributesText="id='numberOfCopiesAgent'" size="3" /></td>
									</tr>
								</table>
							</span>
						</fieldset>
					</td>
				</tr>
				<tr>
					<td align="left" valign="top" nowrap colspan="5">&nbsp;</td>
				</tr>
				<tr>
					<td align="left" valign="top" nowrap colspan="5">&nbsp;</td>
				</tr>
				<tr>
					<td align="left" nowrap width="5%">Effective Date:&nbsp;</td>
					<td align="left" nowrap><input class="datepicker" type="text"
						id="billEffectiveDateDP"
						value="<%=billSchedule.getUIBillEffectiveDate()%>"
						name="billEffectiveDate" size="10"></td>
					<td align="left" nowrap width="17%">&nbsp;</td>
					<td align="left" nowrap width="5%">Rep Name:&nbsp;</td>
					<td align="left" nowrap width="23%"><input:select
							bean="billSchedule" name="repName"
							options="<%=operatorCTs.getOptions()%>"
							attributesText="id='repName' onChange='setRepExt()' " /></td>
				</tr>
				<tr>
					<td align="left" nowrap width="5%">Termination Date:&nbsp;</td>
					<td align="left" nowrap><input class="datepicker" type="text"
						id="billTerminationDateDP"
						value="<%=billSchedule.getUIBillTerminationDate()%>"
						name="terminationDate" size="10"></td>
					<td align="left" nowrap width="17%">&nbsp;</td>
					<td align="left" nowrap width="5%">Rep Ext:&nbsp;</td>
					<td align="left" nowrap width="23%"><input:text
							name="repPhoneNumber" bean="billSchedule"
							attributesText="id='repPhoneNumber'" size="12" /></td>
				</tr>
				<tr>
					<td align="left" nowrap>Status:&nbsp;</td>
					<td align="left" nowrap="nowrap"><input:select
							bean="billSchedule" name="statusCT"
							options="<%=statusCTs.getOptions()%>"
							attributesText="id='statusCT'" /></td>
					<td align="left" nowrap width="17%">&nbsp;</td>
					<td align="left" nowrap width="5%">Billing Company #:&nbsp;</td>
					<td align="left" nowrap width="23%"><input disabled
						type="text" name="billingCompanyNumber" maxlength="20" size="20"
						value="<%=billingCompanyNumber%>"></td>
				</tr>
				<tr>
					<td align="left" nowrap colspan="5">&nbsp;</td>
				</tr>
				<tr>
					<td align="left" nowrap>Bill Change Start Date:&nbsp;</td>
					<td align="left" nowrap colspan="4"><input class="datepicker"
						type="text" id="billChangeStartDateDP"
						value="<%=billSchedule.getUIBillChangeStartDate()%>"
						name="billChangeStartDate" size="10"></td>
				</tr>
				<tr>
					<td align="left" nowrap>Variable Month Deduction Change Start
						Date:&nbsp;</td>
					<td align="left" nowrap colspan="4"><input class="datepicker"
						type="text" id="varMonthDedChangeStartDateDP"
						value="<%=billSchedule.getUIVarMonthDedChangeStartDate()%>"
						name="varMonthDedChangeStartDate" size="10"></td>
				</tr>
				<tr>
					<td align="left" nowrap>Change Effective Date:&nbsp;</td>
					<td align="left" nowrap colspan="4"><input disabled 
						type="text" id="changeEffectiveDateDP"
						value="<%=Util.initString(billSchedule.getUIChangeEffectiveDate(), "")%>"
						name="changeEffectiveDate" size="10"></td>
				</tr>
				<tr>

					<td nowrap colspan="5" align="right"><input id="btnSave"
						type="button" value=" Save " onClick="saveGroupBillingChange()">
						<input id="btnCancel" type="button" value="Cancel"
						onClick="cancelGroupBillingChange()"></td>
				</tr>
				<tr>
					<td align="left" nowrap colspan="1">&nbsp;</td>
					<td id="groupsMessage"><span class="tableHeading">Billing
							History</span></td>
				</tr>
			</table> <%--    END Form Content --%>
		</span>
		<%-- ****************************** END Form Data ****************************** --%>

		<%-- ****************************** BEGIN Summary Area ****************************** --%>

		<jsp:include page="/common/jsp/widget/tableWidget.jsp" flush="true">
			<jsp:param name="tableId" value="GroupBillingTableModel" />
			<jsp:param name="tableHeight" value="30" />
			<jsp:param name="multipleRowSelect" value="false" />
			<jsp:param name="singleOrDoubleClick" value="none" />
		</jsp:include>

		<%-- ****************************** END Summary Area ****************************** --%>

		<table id="closeDialog" width="100%" height="2%" border="0"
			cellspacing="0" cellpadding="0">
			<tr>
				<td colspan="8" nowrap align="right"><input type="button"
					name="close" value="Close" onClick="closeBilling()"></td>
			</tr>
		</table>

		<%-- ****************************** BEGIN Hidden Variables ****************************** --%>
		<input:hidden name="transaction" />
		<input:hidden name="action" />
		<input:hidden name="creationOperator" bean="billSchedule" />
		<input:hidden name="creationDate" bean="billSchedule" />
		<input:hidden name="firstBillDueDate" bean="billSchedule" />
		<input:hidden name="effectiveDate" bean="billSchedule" />
		<input:hidden name="terminationDate" bean="billSchedule" />
		<input:hidden name="billSchedulePK" bean="billSchedule" />
		<input:hidden name="lastBillDueDate" bean="billSchedule" />
		<input:hidden name="nextBillExtractDate" bean="billSchedule" />
		<input:hidden name="nextBillDueDate" bean="billSchedule" />
		<input:hidden name="firstDeductionDate" bean="billSchedule" />
		<input:hidden name="billChangeStartDate" bean="billSchedule" />
		<input:hidden name="varMonthDedChangeStartDate" bean="billSchedule" />
		<input:hidden name="changeEffectiveDate" bean="billSchedule" />

		<%-- ****************************** END Hidden Variables ****************************** --%>

	</form>
</body>
</html>
