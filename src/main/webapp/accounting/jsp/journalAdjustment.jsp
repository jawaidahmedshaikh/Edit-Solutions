<%@ page  errorPage="/common/jsp/error.jsp" autoFlush="true" %>
<%@ page import="java.util.*,
                 edit.common.*,
                 edit.common.vo.*,
                 edit.common.vo.user.*,
                 edit.portal.exceptions.*,
                 edit.common.exceptions.VOEditException,
                 fission.utility.*,
                 accounting.AccountingDetail" %>

<%
	EDITDate now = new EDITDate();
	String effectiveDate = "" + now.getMMDDYYYYDate();

    String responseMessageType = (String) request.getAttribute("responseMessageType");
    String responseMessage = (String) request.getAttribute("responseMessage");

    CodeTableWrapper codeTableWrapper = CodeTableWrapper.getSingleton();

    CodeTableVO[] trxTypes = codeTableWrapper.getTRXCODE_CodeTableEntries();
    CodeTableVO[] states = codeTableWrapper.getCodeTableEntries("STATE");
    CodeTableVO[] voucherSources = codeTableWrapper.getCodeTableEntries("VOUCHERSOURCE");

    String accountingDetailPK = "";
    String accountingProcessDate = "" + now.getMMDDYYYYDate();
    String accountingPeriod = "" + now.getYYYYMMDate();
    String processDate = "" + now.getMMDDYYYYDate();
    String companyName = "";
    String businessContractName = "";
    String groupNumber = "";
    String groupName = "";
    String contractNumber = "";
    String transactionType = "";
    String fundNumber = "";
    String comments = "";
    String accountNumber = "";
    String accountName = "";
    String debitCreditInd = "";
    String amount="";
    String debitAmount = "";
    String creditAmount = "";
    String state = "";
    String suppressInd = "";
    String dateTime = "";
    String operator = "";
    String batchAmount = "";
    String batchControl = "" + (new Date()).getTime();
    String voucherSource = "";
    String description = "";
    String agentCode = "";
    String selectedAccountingDetailPK = (String) request.getAttribute("selectedAccountingDetailPK");

    String accountNumberWithAccountName = "";

    CompanyStructureNameVO companyStructureNameVO = (CompanyStructureNameVO) request.getAttribute("companyStructureNameVO");
    String[] businessContractNames = null;
    if (companyStructureNameVO != null)
    {
        businessContractNames = companyStructureNameVO.getBusinessContractName();
    }

    AccountingDetailVO selectedAccountingDetailVO = (AccountingDetailVO) request.getAttribute("selectedAccountingDetailVO");

    if (selectedAccountingDetailVO != null)
    {
        accountingDetailPK = selectedAccountingDetailVO.getAccountingDetailPK()+"";

        if (selectedAccountingDetailVO.getEffectiveDate() != null)
        {
            effectiveDate = Util.initString(DateTimeUtil.formatYYYYMMDDToMMDDYYYY(selectedAccountingDetailVO.getEffectiveDate()), "");
        }
        if (selectedAccountingDetailVO.getAccountingProcessDate() != null)
        {
            accountingProcessDate = Util.initString(DateTimeUtil.formatYYYYMMDDToMMDDYYYY(selectedAccountingDetailVO.getAccountingProcessDate()), "");
        }
        if (selectedAccountingDetailVO.getAccountingPeriod() != null)
        {
        	accountingPeriod = Util.initString(selectedAccountingDetailVO.getAccountingPeriod(), "");
        }

        processDate = Util.initString(DateTimeUtil.formatYYYYMMDDToMMDDYYYY(selectedAccountingDetailVO.getProcessDate()), "");
        companyName = selectedAccountingDetailVO.getCompanyName();
        groupNumber = selectedAccountingDetailVO.getGroupNumber();
        groupName = Util.initString(selectedAccountingDetailVO.getGroupName(), "");
        businessContractName = selectedAccountingDetailVO.getBusinessContractName();
        contractNumber = Util.initString(selectedAccountingDetailVO.getContractNumber(), "");
        transactionType = selectedAccountingDetailVO.getTransactionCode();
        fundNumber = selectedAccountingDetailVO.getFundNumber();
        comments = Util.initString(selectedAccountingDetailVO.getComments(), "");
        state = selectedAccountingDetailVO.getStateCodeCT();
        accountNumber = selectedAccountingDetailVO.getAccountNumber();
        accountName = selectedAccountingDetailVO.getAccountName();
        accountNumberWithAccountName = accountNumber + " - " + accountName;
        debitCreditInd = selectedAccountingDetailVO.getDebitCreditInd();
        amount = selectedAccountingDetailVO.getAmount() == null ? "" : selectedAccountingDetailVO.getAmount().toString();
        batchAmount = selectedAccountingDetailVO.getBatchAmount() == null ? "" : selectedAccountingDetailVO.getBatchAmount().toString();
        batchControl = Util.initString(selectedAccountingDetailVO.getBatchControl(), "");
        voucherSource = Util.initString(selectedAccountingDetailVO.getVoucherSource(), "");
        description = Util.initString(selectedAccountingDetailVO.getDescription(), "");
        agentCode = Util.initString(selectedAccountingDetailVO.getAgentCode(), "");

        if (debitCreditInd != null)
        {
            if (debitCreditInd.equals(AccountingDetail.DEBITCREDITIND_DEBIT))
            {
                debitAmount = amount;
            }
            else if (debitCreditInd.equals(AccountingDetail.DEBITCREDITIND_CREDIT))
            {
                creditAmount = amount;
            }
        }
        suppressInd = selectedAccountingDetailVO.getAccountingPendingStatus();
        dateTime = Util.initString(selectedAccountingDetailVO.getMaintDateTime(), "");
        operator = Util.initString(selectedAccountingDetailVO.getOperator(), "");
    }

    AccountingDetailVO selectedAccountingDetailAccountSummaryVO = (AccountingDetailVO) request.getAttribute("selectedAccountingDetailAccountSummaryVO");

    String selectedAccountingDetailAccountSummaryPK = "";

    if (selectedAccountingDetailAccountSummaryVO != null)
    {
        selectedAccountingDetailAccountSummaryPK = selectedAccountingDetailAccountSummaryVO.getAccountingDetailPK()+"";

        state = selectedAccountingDetailAccountSummaryVO.getStateCodeCT();
        accountNumber = selectedAccountingDetailAccountSummaryVO.getAccountNumber();
        accountName = selectedAccountingDetailAccountSummaryVO.getAccountName();
        accountNumberWithAccountName = accountNumber + " - " + accountName;
        debitCreditInd = selectedAccountingDetailAccountSummaryVO.getDebitCreditInd();
        amount = selectedAccountingDetailAccountSummaryVO.getAmount() == null ? "" : selectedAccountingDetailAccountSummaryVO.getAmount().toString();

        if (debitCreditInd != null)
        {
            if (debitCreditInd.equals(AccountingDetail.DEBITCREDITIND_DEBIT))
            {
                debitAmount = amount;
            }
            else if (debitCreditInd.equals(AccountingDetail.DEBITCREDITIND_CREDIT))
            {
                creditAmount = amount;
            }
        }

        suppressInd = selectedAccountingDetailAccountSummaryVO.getAccountingPendingStatus();
    }

    FilteredFundVO[] filteredFundVOs = (FilteredFundVO[]) request.getAttribute("filteredFundVOs");

    /*
    * The variables companyNames and groupNumbers are JSON strings of maps. The maps weren't converting
    * from java to javascript correctly so I used JSON strings to accomplish the transfer.
    */
    String groupNumbersWithNames = (String) request.getAttribute("groupNumbersWithNames");
    String accountNumbersWithAccountNames = (String) request.getAttribute("accountNumbersWithAccountNames");
    String companyAccountWithRequiredFields = (String) request.getAttribute("companyAccountWithRequiredFields");
    String[] companyNames = (String[]) request.getAttribute("companyNames");
    String[] groupNumbers = (String[]) request.getAttribute("groupNumbers");

    List accountingDetailAccountSummaryVOs = (List) session.getAttribute("AccountingDetail.JournalAdjustment.AccountSummary");
%>

<html>
<head>

<script src="/PORTAL/common/javascript/commonJavascriptFunctions.js"></script>
<script src="/PORTAL/common/javascript/formatDateFunctions.js"></script>
<script src="/PORTAL/common/javascript/calendarSelector.js"></script>
<script src="/PORTAL/common/javascript/widgetFunctions.js"></script>
<script src="/PORTAL/common/javascript/json2.js"></script>

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

    /*
    * This function handles changes to the companyName field. It's creating a quasi-dynamic functionality
    * to populate the accountNameWithAccountName select field with filtered options. All of the values
    * have already been sent from the server so it's not going back to the server for them. It's using
    * companyName as a key to determine which set of account numbers to display.
    */
    $("#companyName").change(populateAccountNumNameSelectOptions);

    $("#accountNumberWithAccountName").change(function() {
        var selectedValList = $("#accountNumberWithAccountName").val().split(" - ");
        var selectedCompany = $("#companyName").val();
        var selectedAccountNum = selectedValList[0];
        
        var cawrf = '<%= companyAccountWithRequiredFields %>';
        var jsObj = JSON.parse(cawrf);
        var selectedValuesKey = selectedCompany + "-" + selectedAccountNum;
        var stateAgentStatuses = jsObj[selectedValuesKey];
        var stateRequired = stateAgentStatuses.charAt(1);
        var agentRequired = stateAgentStatuses.charAt(4);

        if (typeof stateRequired !== "undefined") {
			if (stateRequired == "Y") {
				document.getElementById("stateLabel").innerHTML = '<span class="requiredField">*</span>&nbsp;State:&nbsp;';
				document.getElementById("state").required = true;
			} else {
				document.getElementById("stateLabel").innerHTML = 'State:&nbsp;';
				document.getElementById("state").required = false;
			}
        }

        if (typeof agentRequired !== "undefined") {
			if (agentRequired == "Y") {
				document.getElementById("agentCodeLabel").innerHTML = '<span class="requiredField">*</span>&nbsp;Agent Code:&nbsp;';
				document.getElementById("agentCode").required = true;
			} else {
				document.getElementById("agentCodeLabel").innerHTML = 'Agent Code:&nbsp;';
				document.getElementById("agentCode").required = false;
			}
        }
    });

    /*
    * Another sort of quasi-dynamic field. Select an account number, automatically update the account name field.
    */
    $("#groupNumber").change(populateGroupName);
  });
  
	var f = null;

    var shouldShowLockAlert = true;

    var suppressInd = "<%= suppressInd %>";

    var responseMessageType = "<%= responseMessageType %>";

    var responseMessage = "<%= responseMessage %>";

	function init()
    {
		f = document.journalAdjustmentForm;

        checkForResponseMessage();

        if (suppressInd == "N")
        {
            f.suppress.value = "checked";
        }
        formatCurrency();

        populateAccountNumNameSelectOptions();
        populateGroupName();
	}

    function populateAccountNumNameSelectOptions() {
    	var selectedVal = $("#companyName").val();
    	if (!valueIsEmpty(selectedVal)) {
	        var anwan = '<%= accountNumbersWithAccountNames %>';
	        var jsObj = JSON.parse(anwan);
	        
	        var selectedCompanyAccountsStr = jsObj[selectedVal];
	        $("#accountNumberWithAccountName").find('option').remove().end().append('<option value="null"></option>').val(null);
	        
	        if (typeof selectedCompanyAccountsStr !== "undefined") {
	            var selectedCompanyAccountList =  selectedCompanyAccountsStr.slice(1,-1).split(", ");
	            $.each(selectedCompanyAccountList, function(i, entry) {
	            	$("#accountNumberWithAccountName").append('<option id="id" value="' + entry + '">' + entry + '</option>');
	            });
	        }
    	}
    }

    function populateGroupName() {
    	var gn = String($("#groupNumber").val());
    	if (!valueIsEmpty(gn)) {
	        var gnwn = '<%= groupNumbersWithNames %>';
	        var jsObj = JSON.parse(gnwn);
	        
	        var selectedVal = jsObj[gn];
	        document.journalAdjustmentForm.groupName.value = selectedVal;
    	}
    }
	
    function checkForResponseMessage()
    {

        if (!valueIsEmpty(responseMessageType))
        {
            if (responseMessageType == "warning")
            {
                if (!valueIsEmpty(responseMessage))
                {
                   alert(responseMessage);

                }
            }
            else
            {
                if (!valueIsEmpty(responseMessage))
                {
                    alert(responseMessage);
                }
            }
        }

    }

    function addJournalAdjustment()
    {
        sendTransactionAction("AccountingDetailTran", "addJournalAdjustment", "_self");
    }

    function saveJournalAdjustment()
    {
        if (validateForm(f))
        {
            sendTransactionAction("AccountingDetailTran", "saveJournalAdjustment", "_self");
        }
    }

    function cancelJournalAdjustment()
    {
        sendTransactionAction("AccountingDetailTran", "cancelJournalAdjustment", "_self");
    }

    /**
      This function is called upon selecting row in the Table Widget summary area
     */
    function onTableRowSingleClick(tableId)
    {
        f.isAccountingDetailSelectedFromSummary.value = "Y";

        sendTransactionAction("AccountingDetailTran", "showJournalAdjustmentDetailSummary", "_self");
    }

    function addAccountInfoSummary()
    {
        f.isAccountingDetailSelectedFromSummary.value = "N";

        if (f.selectedAccountingDetailAccountSummaryPK.value > 0)
        {
            alert('Can Not Add To This Entry');
        }
        else
        {
            if (validateForm(f))
            {
                sendTransactionAction("AccountingDetailTran", "clearAccountingDetailAccountSummaryForAddOrCancel", "_self");
            }
        }
    }

    function saveAccountInfoSummary()
    {
        f.isAccountingDetailSelectedFromSummary.value = "N";

        if (validateForm(f))
        {
            if (f.agentCode.required == true && valueIsEmpty(f.agentCode.value))
            {
            	alert('This account requires an Agent Code');
            }
            else if (f.state.required == true && (valueIsEmpty(f.state.value) || f.state.value == "Please Select")) 
            {
                alert('This account requires a State');
            }
            // Both Debit And Credit can not be zero
            else if (valueIsZero(f.debitAmount.value) && valueIsZero(f.creditAmount.value))
            {
                alert('Both Credit Amount And Debit Amount Can Not Be Zero');
            }
            // Both Debit and Credit can not be entered
            else if (f.debitAmount.value != 0 && f.creditAmount.value != 0)
            {
                alert('Can Not Enter Both Credit Amount And Debit Amount In One Entry');
            }
            // If Debit Amount is not entered ... make sure the Credit amount is greater than zero
            // Or If Credit Amount is not entered ... make sure the Debit amount is greater than zero
            else if ( (valueIsZero(f.debitAmount.value) && f.creditAmount.value <= 0) ||
                        (valueIsZero(f.creditAmount.value) && f.debitAmount.value <= 0) )
            {
                alert('Amount Should be Greater Than Zero');
            }
            else
            {
                f.suppressInd.value = "Y";

                if (f.suppress.checked == true)
                {
                    f.suppressInd.value = "N";
                }

                sendTransactionAction("AccountingDetailTran", "saveAccountingDetailAccountSummary", "_self");
            }
        }
    }

    function clearAccountInfoSummary()
    {
        f.isAccountingDetailSelectedFromSummary.value = "N";

        if (validateForm(f))
        {
            sendTransactionAction("AccountingDetailTran", "clearAccountingDetailAccountSummaryForAddOrCancel", "_self");
        }
    }

    function deleteAccountInfoSummary()
    {
        f.isAccountingDetailSelectedFromSummary.value = "N";

        if (f.selectedAccountingDetailAccountSummaryPK.value == "")
        {
            alert('Please Select Row To Delete');
        }
        else if (f.selectedAccountingDetailAccountSummaryPK.value > 0)
        {
            alert('Can Not Delete This Entry');
        }
        else
        {
            sendTransactionAction("AccountingDetailTran", "deleteAccountingDetailAccountSummary", "_self");
        }
    }

	/**
      This function is called upon selecting row in the Account Info summary area
     */
    function selectAccountingDetailAccountSummary()
    {
        f.isAccountingDetailSelectedFromSummary.value = "N";

		f.selectedAccountingDetailAccountSummaryPK.value = getSelectedRowId("accountInfoSummary");

		sendTransactionAction("AccountingDetailTran", "showAccountingDetailAccountSummaryDetail", "_self");
	}

    function validateAccountingPeriod()
    {
        var accountingPeriodRegEx = /^\d{4}\/\d{2}$/;

        var errorCode = 0;

        var accountingPeriodMonth;

        if (!valueIsEmpty(f.accountingPeriod.value))
        {
            if (!f.accountingPeriod.value.match(accountingPeriodRegEx))
            {
                alert('Invalid Accounting Period. [Format: yyyy/mm  Ex: 2000/01]');
                f.accountingPeriod.focus();
                return false;
            }

            accountingPeriodMonth = f.accountingPeriod.value.split("/")[1];

            if (accountingPeriodMonth < 1 || accountingPeriodMonth > 12)
            {
                alert('Invalid Accounting Period. Month value should be between \'01\' and \'12\'');
                f.accountingPeriod.focus();
                return false;
            }
        }

        return true;
    }

    var now = "<%=now.getMMDDYYYYDate()%>";
    function checkDate(dateElement, date) {
        var now = "<%=now.getMMDDYYYYDate()%>";
        var nowYYYYMMDD = convertMDYtoYMDNoSlash(now);
        var dateFormatted = convertMDYtoYMDNoSlash(date);
        var greaterThanOrEqual = Number(dateFormatted) >= Number(nowYYYYMMDD);

        if (!greaterThanOrEqual) {
            alert("Date may not be back-dated.")
            dateElement.value = now;
            return false;
        } else {
            return true;
        }
    }

</script>

<title>Journal Adjustment</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta http-equiv="Cache-Control" content="no-store">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Expires" content="0">

<link href="/PORTAL/common/css/EDITSolutions.css" rel="stylesheet" type="text/css">
</head>

<body class="mainTheme" onLoad="init();" style="border-style:solid; border-width:1;">
<form name= "journalAdjustmentForm" method="post" action="/PORTAL/servlet/RequestManager">

  <table width="100%" border="0" cellpadding="4" cellspacing="0">
    <tr>
	  <td align="left" nowrap><span class="requiredField">*</span>&nbsp;Effective Date:&nbsp;
		<input class="datepicker" type="text" REQUIRED disabled
			id="effectiveDate" onChange="checkDate(this,this.value)"
			value="<%=effectiveDate%>"
			name="effectiveDate" size="10">
	  </td>
	  <td align="left" nowrap>&nbsp;Acctg Process Date:&nbsp;
		<input class="datepicker" type="text" disabled
			id="accountingProcessDate" onChange="checkDate(this,this.value)"
			value="<%=accountingProcessDate%>"
			name="accountingProcessDate" size="10">
	  </td>
      <td align="right" nowrap>Acctg Period (yyyy/mm):&nbsp;</td>
      <td align="left" nowrap>
        <input type="text" disabled name="accountingPeriod" maxlength="7" size="7" value="<%= accountingPeriod %>" onBlur="validateAccountingPeriod()">
      </td>
	  <td align="left" nowrap><span class="requiredField">*</span>&nbsp;Process Date:&nbsp;
		<input class="datepicker" type="text" REQUIRED disabled
			id="processDate" onChange="checkDate(this,this.value)"
			value="<%=processDate%>"
			name="processDate" size="10">
	  </td>
    </tr>
    <tr>
      <td align="right" nowrap><span class="requiredField">*</span>&nbsp;Company Name:&nbsp;
      <td align="left" nowrap>
        <select name="companyName" id="companyName" REQUIRED>
          <option value="null">Please Select</option>
          <%
              if (companyNames != null)
              {
                  for(int i = 0; i < companyNames.length; i++)
                  {
                      if (companyNames[i].equalsIgnoreCase(companyName))
                      {
                          out.println("<option selected name=\"id\" value=\"" + companyNames[i] + "\">" + companyNames[i] + "</option>");
                      }
                      else
                      {
                          out.println("<option name=\"id\" value=\"" + companyNames[i] + "\">" + companyNames[i] + "</option>");
                      }
                  }
              }
          %>
        </select>
      </td>
      <td align="right" nowrap><span class="requiredField">*</span>&nbsp;Bus Contract Name:&nbsp;
      <td align="left" colspan="3" nowrap>
        <select name="businessContractName" REQUIRED>
          <option value="null">Please Select</option>
          <%
              if (businessContractNames != null)
              {
                  for(int i = 0; i < businessContractNames.length; i++)
                  {
                      if (businessContractNames[i].equals(businessContractName))
                      {
                          out.println("<option selected name=\"id\" value=\"" + businessContractNames[i] + "\">" + businessContractNames[i] + "</option>");
                      }
                      else
                      {
                          out.println("<option name=\"id\" value=\"" + businessContractNames[i] + "\">" + businessContractNames[i] + "</option>");
                      }
                  }
              }
          %>
        </select>
      </td>
    </tr>
    <tr>
      <td align="right" nowrap><span class="requiredField">*</span>&nbsp;Contract Number:&nbsp;</td>
      <td align="left" nowrap>
        <input type="text" name="contractNumber" maxlength="15" size="15" value="<%= contractNumber %>" REQUIRED>
      </td>
      <td align="right" nowrap><span class="requiredField">*</span>&nbsp;Transaction Type:&nbsp;</td>
      <td align="left" nowrap>
        <select name="transactionType" REQUIRED>
          <option value="null">Please Select</option>
          <%
              for(int i = 0; i < trxTypes.length; i++)
              {
                  String codeDesc    = trxTypes[i].getCodeDesc();
                  String code        = trxTypes[i].getCode();

                  if (code.equalsIgnoreCase(transactionType))
                  {
                      out.println("<option selected name=\"id\" value=\"" + code + "\">" + codeDesc + "</option>");
                  }
                  else
                  {
                      out.println("<option name=\"id\" value=\"" + code + "\">" + codeDesc + "</option>");
                  }
              }
          %>
        </select>
      </td>
    </tr>
    <tr>
      <td align="right" nowrap><span class="requiredField">*</span>&nbsp;Group Number:&nbsp;
      <td align="left" nowrap>
        <select name="groupNumber" id="groupNumber">
          <option value="null">Please Select</option>
          <%
              if (groupNumbers != null)
              {
                  for(int i = 0; i < groupNumbers.length; i++)
                  {
                      if (groupNumbers[i].equalsIgnoreCase(groupNumber))
                      {
                          out.println("<option selected name=\"id\" value=\"" + groupNumbers[i] + "\">" + groupNumbers[i] + "</option>");
                      }
                      else
                      {
                          out.println("<option name=\"id\" value=\"" + groupNumbers[i] + "\">" + groupNumbers[i] + "</option>");
                      }
                  }
              }
          %>
        </select>
      </td>
      <td align="right" nowrap><span class="requiredField">*</span>&nbsp;Group Name:&nbsp;</td>
      <td align="left" nowrap colspan="3">
        <input type="text" name="groupName" id="groupName" maxlength="60" size="60" value="<%= groupName %>" disabled>
      </td>
    </tr>
    <tr>
      <td align="right" nowrap>Fund Number:&nbsp;</td>
      <td align="left" nowrap>
        <select name="fundNumber">
          <option value="null">Please Select</option>
          <%
              for(int i = 0; i < filteredFundVOs.length; i++)
              {
                  String currentFundNumber = filteredFundVOs[i].getFundNumber();

                  if (currentFundNumber.equalsIgnoreCase(fundNumber))
                  {
                      out.println("<option selected name=\"id\" value=\"" + currentFundNumber + "\">" + currentFundNumber + "</option>");
                  }
                  else
                  {
                      out.println("<option name=\"id\" value=\"" + currentFundNumber + "\">" + currentFundNumber + "</option>");
                  }
              }
          %>
        </select>
      </td>
      <td align="right" nowrap>Comments:&nbsp;</td>
      <td align="left" nowrap colspan="7">
        <input type="text" name="comments" maxlength="30" size="30" value="<%= comments %>">
      </td>
    </tr>
    <tr valign="top">
      <td colspan="8">
        <fieldset name="accountInfoSummary" style="border-style:solid; border-width:1px; border-color:gray">
        <legend><font color="black"><nobr>Account Info Summary&nbsp;</nobr></font></legend>
        <table width="100%" border="0" cellpadding="4" cellspacing="0">
          <tr>
            <td align="right" nowrap>Account # - Account Name:&nbsp;</td>
            <td align="left" nowrap>
              <select name="accountNumberWithAccountName" id="accountNumberWithAccountName">
                <option value="null"></option>
<%--                 <%
                    if (accountNumbersWithAccountNames != null)
                    {
                        for(int i = 0; i < accountNumbersWithAccountNames.length; i++)
                        {

                        String currentAccountNumberWithAccountName = accountNumbersWithAccountNames[i];

                        if (currentAccountNumberWithAccountName.equalsIgnoreCase(accountNumberWithAccountName))
                        {
                           out.println("<option selected name=\"id\" value=\"" + currentAccountNumberWithAccountName + "\">" + currentAccountNumberWithAccountName + "</option>");
                        }
                        else
                        {
                           out.println("<option name=\"id\" value=\"" + currentAccountNumberWithAccountName + "\">" + currentAccountNumberWithAccountName + "</option>");
                        }
                     }
                    }
                %>
 --%>              </select>
            </td>
            <td align="right" nowrap>Debit Amount:&nbsp;</td>
            <td align="left" nowrap>
              <input type="text" name="debitAmount" maxlength="20" size="20" value="<%= debitAmount %>" CURRENCY>
            </td>
            <td align="right" nowrap>Credit Amount:&nbsp;</td>
            <td align="left" nowrap>
              <input type="text" name="creditAmount" maxlength="20" size="20" value="<%= creditAmount %>" CURRENCY>
            </td>
          </tr>
          <tr>
            <td align="right" nowrap id="stateLabel">State:&nbsp;</td>
            <td align="left" nowrap>
              <select name="state" id="state">
                <option value="null">Please Select</option>
                <%
                    for(int i = 0; i < states.length; i++) {

                        String codeDesc    = states[i].getCodeDesc();
                        String code        = states[i].getCode();

                        if (code.equalsIgnoreCase(state))
                        {
                           out.println("<option selected name=\"id\" value=\"" + code + "\">" + code + " - " + codeDesc + "</option>");
                        }
                        else
                        {
                           out.println("<option name=\"id\" value=\"" + code + "\">" + code + " - " + codeDesc + "</option>");
                        }
                     }
                %>
              </select>
            </td>
            <td align="right" nowrap id="agentCodeLabel">Agent Code:&nbsp;</td>
      		<td align="left" nowrap>
            	<input type="text" name="agentCode" id="agentCode" maxlength="10" size="10" value="<%= agentCode %>">
          	</td>
            <td align="right" nowrap>Suppress:&nbsp;</td>
            <td align="left"nowrap>
              <input type="checkbox" name="suppress" <%= suppressInd %>>
            </td>
          </tr>
    <tr>
      <td align="right" nowrap>Batch Amount:&nbsp;</td>
      <td align="left" nowrap>
            <input type="text" name="batchAmount" maxlength="20" size="20" value="<%= batchAmount %>" CURRENCY>
      </td>
      <td align="right" nowrap>Batch Control:&nbsp;</td>
      <td align="left" nowrap>
            <input type="text" name="batchControl" maxlength="15" size="15" value="<%= batchControl %>">
      </td>
    </tr>
    <tr>
      <td align="right" nowrap>Voucher Source:&nbsp;</td>
      <td align="left" nowrap colspan="8">
        <select name="voucherSource">
          <option value="null">Please Select</option>
          <%
              for(int i = 0; i < voucherSources.length; i++) {

                  String codeDesc    = voucherSources[i].getCodeDesc();
                  String code        = voucherSources[i].getCode();

                  if (code.equalsIgnoreCase(voucherSource))
                  {
                     out.println("<option selected name=\"id\" value=\"" + code + "\">" + code + " - " + codeDesc + "</option>");
                  }
                  else
                  {
                     out.println("<option name=\"id\" value=\"" + code + "\">" + code + " - " + codeDesc + "</option>");
                  }
               }
          %>
        </select>
      </td>
        </tr>
        <tr>
          <td align="right" nowrap>Description:&nbsp;</td>
          <td align="left" nowrap colspan="5">
            <input type="text" name="description" maxlength="21" size="21" value="<%= description %>">
      </td>
    </tr>
	<tr>
            <td align="left" colspan="6">
                <input type="button" id="btnAddAcctInfoSummary" name="add" value="   Add   " onClick="addAccountInfoSummary()">
                <input type="button" id="btnSaveAcctInfoSummary" name="save" value="  Save  " onClick="saveAccountInfoSummary()">
                <input type="button" id="btnCancelAcctInfoSummary" name="cancel" value=" Cancel " onClick="clearAccountInfoSummary()">
                <input type="button" id="btnDeleteAcctInfoSummary" name="delete" value=" Delete " onClick="deleteAccountInfoSummary()">
            </td>
          </tr>
          <tr height="30%">
            <td colspan="6">
              <table class="summary" width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr class="heading">
                  <th width="20%" align="left">Account # - Account Name</th>
                  <th width="20%" align="left">Debit Amount</th>
                  <th width="20%" align="left">Credit Amount</th>
                  <th width="20%" align="left">State</th>
                  <th width="20%" align="left">Accounting Pending</th>
                </tr>
              </table>
              <span class="scrollableContent" style="border-style:solid; border-width:1;  position:relative; width:100%; height:95%; top:0; background-color:#BBBBBB">
                <table class="summary" id="accountInfoSummary" width="100%" border="0" cellspacing="0" cellpadding="0">
                  <%
                        String rowToMatch = "";
                        String trClass = "";
                        String selected = "";

                        if (accountingDetailAccountSummaryVOs != null)
                        {
                            for (int i = 0; i < accountingDetailAccountSummaryVOs.size(); i++)
                            {
                                AccountingDetailVO accountingDetailAccountSummaryVO = (AccountingDetailVO) accountingDetailAccountSummaryVOs.get(i);

                                String currentAccountingDetailAccountSummaryPK = accountingDetailAccountSummaryVO.getAccountingDetailPK()+"";

                                String currentAccountNumber = accountingDetailAccountSummaryVO.getAccountNumber();
                                String currentAccountName = accountingDetailAccountSummaryVO.getAccountName();
                                String currentDebitCreditInd = accountingDetailAccountSummaryVO.getDebitCreditInd();
                                String currentDebitAmount = "0";
                                String currentCreditAmount = "0";

                                if (currentDebitCreditInd.equals(AccountingDetail.DEBITCREDITIND_DEBIT))
                                {
                                    currentDebitAmount = accountingDetailAccountSummaryVO.getAmount().toString();
                                }
                                else
                                {
                                    currentCreditAmount = accountingDetailAccountSummaryVO.getAmount().toString();
                                }

                                String currentState = Util.initString(accountingDetailAccountSummaryVO.getStateCodeCT(), "");

                                String currentSuppressInd = accountingDetailAccountSummaryVO.getAccountingPendingStatus();

                                if (currentAccountingDetailAccountSummaryPK.equals(selectedAccountingDetailAccountSummaryPK))
                                {
                                    trClass = "highLighted";
                                    selected = "true";
                                }
                                else
                                {
                                    trClass = "default";
                                    selected="false";
                                }
                  %>
                    <tr class="<%= trClass %>" isSelected="<%= selected %>"
                        id="<%= currentAccountingDetailAccountSummaryPK %>" onClick="selectRow(false); selectAccountingDetailAccountSummary()"
                        onMouseOver="highlightRow()" onMouseOut="unhighlightRow()">
                      <td width="20%" nowrap>
                        <%= currentAccountNumber + " - " + currentAccountName %>
                      </td>
                      <td width="20%" nowrap>
                        <script>document.write(formatAsCurrency(<%= currentDebitAmount %>))</script>
                      </td>
                      <td width="20%" nowrap>
                        <script>document.write(formatAsCurrency(<%= currentCreditAmount %>))</script>
                      </td>
                      <td width="20%" nowrap>
                        <%= currentState %>
                      </td>
                      <td width="20%" nowrap>
                        <%= currentSuppressInd %>
                      </td>
                    </tr>
                  <%
                            }// end for
                        }// end if
                  %>
                </table>
                </span>
            </td>
          </tr>
        </table>
        <table>
          <tr class="filler"> <!-- A dummy row to help with sizing -->
            <td>
              &nbsp;
            </td>
          </tr>
        </table>
        </fieldset>
      </td>
    </tr>
    <tr>
      <td align="right" nowrap>Date/Time:&nbsp;</td>
      <td align="left" nowrap>
        <input type="text" name="dateTime" size="20" value="<%= dateTime %>" disabled>
      </td>
      <td align="right" nowrap>Operator:&nbsp;</td>
      <td align="left" nowrap>
        <input type="text" name="operator" size="15" value="<%= operator %>" disabled>
      </td>
      <td align="right" colspan="4" nowrap>
        <span class="requiredField">* = required </span>
      </td>
    </tr>
  </table>
  <table width="100%" height="2%" border="0" cellspacing="0" cellpadding="0">
	<tr>
	  <td align="left">
		<input type="button" id="btnAddJournalAdjustment" name="add" value="   Add   " onClick="addJournalAdjustment()">
		<input type="button" id="btnSaveJournalAdjustment" name="save" value="  Save  " onClick="saveJournalAdjustment()">
		<input type="button" id="btnCancelJournalAdjustment" name="cancel" value="Cancel" onClick="cancelJournalAdjustment()">
	  </td>
	</tr>
  </table>


<%-- ****************************** BEGIN Summary Area ****************************** --%>

<jsp:include page="/common/jsp/widget/tableWidget.jsp" flush="true">
  <jsp:param name="tableId" value="AccountingDetailTableModel"/>
  <jsp:param name="tableHeight" value="30"/>
  <jsp:param name="multipleRowSelect" value="false"/>
  <jsp:param name="singleOrDoubleClick" value="single"/>
</jsp:include>

<%-- ****************************** END Summary Area ****************************** --%>

<!-- ****** HIDDEN FIELDS ***** //-->
 <input type="hidden" name="transaction"  value="">
 <input type="hidden" name="action"       value="">
 <input type="hidden" name="selectedAccountingDetailPK" value="<%= accountingDetailPK %>">
 <input type="hidden" name="selectedAccountingDetailAccountSummaryPK" value="<%= selectedAccountingDetailAccountSummaryPK %>">
 <input type="hidden" name="isAccountingDetailSelectedFromSummary">
 <input type="hidden" name="suppressInd">
 
</body>
</html>
