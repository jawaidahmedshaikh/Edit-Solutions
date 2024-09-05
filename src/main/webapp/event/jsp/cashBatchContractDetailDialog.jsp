<!-- ****** JAVA CODE ***** //-->

<%@ page  errorPage="/common/jsp/error.jsp" autoFlush="true" %>
<%@ page import="java.util.*,
                 edit.common.*,
                 fission.utility.Util,
                 event.CashBatchContract,
                 event.Suspense,
                 edit.common.vo.FilteredFundVO,
                 edit.common.vo.CodeTableVO,
                 contract.Deposits" %>
<!--
 * User: unknown
 * Date: Jan 1, 2000
 * Time: unknown
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 --> 
<%
    String viewMode = Util.initString((String) request.getAttribute("viewMode"), "change");

    String responseMessage = (String) request.getAttribute("responseMessage");

    CodeTableWrapper codeTableWrapper = CodeTableWrapper.getSingleton();

    CodeTableVO[] premiumTypeCTVO = codeTableWrapper.getCodeTableEntries("PREMIUMTYPE");
    CodeTableVO[] plannedUnplannedCTVO = codeTableWrapper.getCodeTableEntries("PLANNEDUNPLANNED");
    CodeTableVO[] depositTypeCTVO = codeTableWrapper.getCodeTableEntries("DEPOSITTYPE");

    CashBatchContract activeCashBatchContract = (CashBatchContract) request.getAttribute("activeCashBatchContract");

    FilteredFundVO[] hedgeFilteredFundVOs = (FilteredFundVO[]) session.getAttribute("hedgeFilteredFundVOs");

    Suspense activeSuspense = (Suspense) request.getAttribute("activeSuspense");
    Deposits activeDeposit = (Deposits) request.getAttribute("activeDeposit");

    String depositAttached = Util.initString((String) request.getAttribute("depositAttached"), "unchecked");

    String batchId = "";
    String cashBatchContractFK = "0";
    if (activeCashBatchContract != null)
    {
        batchId = (String) Util.initObject(activeCashBatchContract, "batchID", "");
        cashBatchContractFK = activeCashBatchContract.getCashBatchContractPK().toString();
    }

    EDITDate currentDate = new EDITDate();

    String suspensePK = "0";
    String policyNumber = Util.initString((String) request.getAttribute("policyNumber"), "");
    String effectiveMonth = Util.initString((String) request.getAttribute("effectiveMonth"), currentDate.getFormattedMonth());
    String effectiveDay = Util.initString((String) request.getAttribute("effectiveDay"), currentDate.getFormattedDay());
    String effectiveYear = Util.initString((String) request.getAttribute("effectiveYear"), currentDate.getFormattedYear());
    EDITBigDecimal suspenseAmount = new EDITBigDecimal(Util.initString((String) request.getAttribute("suspenseAmount"), "0"));
    EDITBigDecimal grossAmount = new EDITBigDecimal(Util.initString((String) request.getAttribute("grossAmount"), "0"));
    String taxYear = Util.initString((String) request.getAttribute("taxYear"), "");
    String checkNumber = Util.initString((String) request.getAttribute("checkNumber"), "");
    String plannedUnplanned = Util.initString((String) request.getAttribute("plannedUnplanned"), "");
    String firstName = Util.initString((String) request.getAttribute("firstName"), "");
    String lastName = Util.initString((String) request.getAttribute("lastName"), "");
    String corporateName = Util.initString((String) request.getAttribute("corporateName"), "");
    String premiumType = Util.initString((String) request.getAttribute("premiumType"), "");
    String depositType = Util.initString((String) request.getAttribute("depositType"), "");
    EDITBigDecimal costBasis = new EDITBigDecimal(Util.initString((String) request.getAttribute("costBasis"), "0"));
    String fund = "";
    String exchangeCompany = Util.initString((String) request.getAttribute("exchangeCompany"), "");
    String exchangePolicy = Util.initString((String) request.getAttribute("exchangePolicy"), "");
    String depositsPK = "0";

    if (activeSuspense != null)
    {
        suspensePK = activeSuspense.getSuspensePK().toString();
        policyNumber = activeSuspense.getUserDefNumber();

        EDITDate effectiveDate = new EDITDate(activeSuspense.getEffectiveDate());
        effectiveYear = effectiveDate.getFormattedYear();
        effectiveMonth = effectiveDate.getFormattedMonth();
        effectiveDay = effectiveDate.getFormattedDay();

        suspenseAmount = activeSuspense.getSuspenseAmount();
        grossAmount = (EDITBigDecimal) Util.initObject(activeSuspense, "grossAmount", new EDITBigDecimal());
        taxYear = activeSuspense.getTaxYear() + "";
        checkNumber = (String) Util.initObject(activeSuspense, "checkNumber", "");
        plannedUnplanned = (String) Util.initObject(activeSuspense, "plannedIndCT", "");
        firstName = (String) Util.initObject(activeSuspense, "firstName", "");
        lastName = (String) Util.initObject(activeSuspense, "lastName", "");
        corporateName = (String) Util.initObject(activeSuspense, "corporateName", "");
        premiumType = (String) Util.initObject(activeSuspense, "premiumTypeCT", "");
        depositType = (String) Util.initObject(activeSuspense, "depositTypeCT", "");
        costBasis = (EDITBigDecimal) Util.initObject(activeSuspense, "costBasis", new EDITBigDecimal());
        fund = ((Long) Util.initObject(activeSuspense, "filteredFundFK", new Long(0))).toString();
    }

    if (activeDeposit != null)
    {
        depositsPK = activeDeposit.getDepositsPK().toString();
        exchangeCompany = Util.initString(activeDeposit.getOldCompany(), "");
        exchangePolicy = Util.initString(activeDeposit.getOldPolicyNumber(), "");
        costBasis = (EDITBigDecimal) Util.initObject(activeDeposit, "costBasis", new EDITBigDecimal());
        taxYear = activeDeposit.getTaxYear() + "";
    }

    // Getting CashBatch Filter values
    String status = (String) request.getAttribute("status");
    String month = (String) request.getAttribute("month");
    String day = (String) request.getAttribute("day");
    String year = (String) request.getAttribute("year");
    String filterAmount = (String) request.getAttribute("filterAmount");
    String filterOperator = (String) request.getAttribute("filterOperator");
    EDITBigDecimal balanceRemaining = (EDITBigDecimal) request.getAttribute("balanceRemaining");

%>

<html>

<script src="/PORTAL/common/javascript/commonJavascriptFunctions.js"></script>

<script src="/PORTAL/common/javascript/widgetFunctions.js"></script>
<script src="/PORTAL/common/javascript/scrollTable.js"></script>


<script language="JavaScript1.2">

<!--******* JAVASCRIPT ******//-->

    var f = null;

    var responseMessage = "<%= responseMessage %>";
    var viewMode = "<%= viewMode %>";
    var suspensePK = "<%= suspensePK %>";

    function init()
    {
        f = document.cashBatchContractDetailForm;

        checkForResponseMessage();

        formatCurrency();

		f.policyNumber.focus();

        if (viewMode != "change")
        {
            f.policyNumber.disabled = true;
            f.effectiveMonth.disabled = true;
            f.effectiveDay.disabled = true;
            f.effectiveYear.disabled = true;
            f.taxYear.disabled = true;
            f.suspenseAmount.disabled = true;
            f.grossAmount.disabled = true;
            f.checkNumber.disabled = true;
            f.plannedUnplanned.disabled = true;
            f.firstName.disabled = true;
            f.lastName.disabled = true;
            f.premiumType.disabled = true;
            f.filteredFundFK.disabled = true;
            f.exchangeCompany.disabled = true;
            f.exchangePolicy.disabled = true;
            f.costBasis.disabled = true;
            f.btnSave.disabled = true;
        }

        if (suspensePK != "0")
        {
            f.policyNumber.disabled = true;
            f.suspenseAmount.disabled = true;
        }

        // Initialize scroll tables
        initScrollTable(document.getElementById("CashBatchDetailTableModelScrollTable"));
    }

    function onTableRowSingleClick(tableId)
    {
        sendTransactionAction("EventAdminTran", "selectExchangeInformation", "_self");
    }

    function getContractInfoForSuspense()
    {
        sendTransactionAction("EventAdminTran", "getContractInfoForSuspense", "_self");
    }

    function saveSuspense()
    {
        if (valueIsEmpty(f.policyNumber.value))
        {
            alert("Please Enter The Policy Number");
        }
        else if (valueIsEmpty(f.suspenseAmount.value) ||
                 f.suspenseAmount.value == "0" ||
                 f.suspenseAmount.value == "0.0" ||
                 f.suspenseAmount.value == "0.00")
        {
            alert("Suspense Amount Must Be Entered");
        }
        else
        {
            try
            {
                formatDate(f.effectiveMonth.value, f.effectiveDay.value, f.effectiveYear.value, true);

                if (valueIsEmpty(f.taxYear.value))
                {
                    f.taxYear.value = f.effectiveYear.value;
                }

                sendTransactionAction("EventAdminTran", "saveSuspenseForCashBatch", "_self");
            }
            catch (e)
            {
                alert("Please Enter The Effective Date");
            }
        }
    }

    function closeSuspenseEntry()
    {
        sendTransactionAction("EventAdminTran", "closeCashBatchDetail", "cashBatchContractSummary");
        closeWindow();
    }

    function cancelSuspenseEntry()
    {
        sendTransactionAction("EventAdminTran", "cancelCashBatchDetail", "_self");
    }

</script>
<head>
<title>Cash Batch Contract Detail</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta http-equiv="Cache-Control" content="no-store">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Expires" content="0">

<link href="/PORTAL/common/css/EDITSolutions.css" rel="stylesheet" type="text/css">
</head>

<!-- ****** HTML CODE ***** //-->

<body class="mainTheme" onLoad="init()">
<form name="cashBatchContractDetailForm" method="post" action="/PORTAL/servlet/RequestManager">

<%-- ********** BEGIN Form Data ********** --%>

<table class="formData" width="100%" height="1%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td align="right" nowrap>Batch ID:&nbsp;<%= batchId %></td>
  </tr>
  <tr class="filler">
    <td>
      &nbsp; <!-- Filler Row -->
    </td>
  </tr>
</table>
<table class="formData" width="100%" height="30%" border="0" cellspacing="0" cellpadding="3">
  <tr>
    <td align="right" nowrap>Policy Number:&nbsp;</td>
    <td align="left" nowrap>
      <input type="text" name="policyNumber" size="15" maxlength="15" value="<%= policyNumber %>" onChange="getContractInfoForSuspense()">
    </td>
    <td align="right" nowrap>EffectiveDate:&nbsp;</td>
    <td align="left" nowrap>
      <input type="text" name="effectiveMonth" size="2" maxlength="2" value="<%= effectiveMonth %>">
      /
      <input type="text" name="effectiveDay" size="2" maxlength="2" value="<%= effectiveDay %>">
      /
      <input type="test" name="effectiveYear" size="4" maxlength="4" value="<%= effectiveYear %>">
    </td>
    <td align="right" nowrap>Tax Year:&nbsp;</td>
    <td align="left" nowrap>
      <input type="text" name="taxYear" size="4" maxlength="4" value="<%= taxYear %>">
    </td>
  </tr>
  <tr>
    <td align="right" nowrap>Suspense Amount:&nbsp;</td>
    <td align="left" nowrap>
      <input type="text" name="suspenseAmount" size="15" maxlength="15" value="<%= suspenseAmount.toString() %>" CURRENCY>
    </td>
    <td align="right" nowrap>Gross Amount:&nbsp;</td>
    <td align="left" nowrap colspan="3">
      <input type="text" name="grossAmount" size="15" maxlength="15" value="<%= grossAmount.toString() %>" CURRENCY>
    </td>
  </tr>
  <tr>
    <td align="right" nowrap>Check Number:&nbsp;</td>
    <td align="left" nowrap>
      <input type="text" name="checkNumber" size="20" maxlength="20" value="<%= checkNumber %>">
    </td>
    <td align="right" nowrap>Planned/Unplanned:&nbsp;</td>
    <td align="left" nowrap colspan="3">
      <select name="plannedUnplanned">
        <%
          out.println("<option>Please Select</option>");

          for(int i = 0; i < plannedUnplannedCTVO.length; i++)
          {
              String codeTablePK = plannedUnplannedCTVO[i].getCodeTablePK() + "";
              String codeDesc    = plannedUnplannedCTVO[i].getCodeDesc();
              String code        = plannedUnplannedCTVO[i].getCode();

             if (plannedUnplanned.equals(code)) {

                 out.println("<option selected name=\"id\" value=\"" + codeTablePK + "\">" + codeDesc + "</option>");
             }
             else  {

                 out.println("<option name=\"id\" value=\"" + codeTablePK + "\">" + codeDesc + "</option>");
             }
          }

        %>
      </select>
    </td>
  </tr>
  <tr>
    <td align="right" nowrap>First Name:&nbsp;</td>
    <td align="left" nowrap>
      <input type="text" name="firstName" size="15" maxlength="15" value="<%= firstName %>">
    </td>
    <td align="right" nowrap>Last Name:&nbsp;</td>
    <td align="left" nowrap>
      <input type="text" name="lastName" size="30" maxlength="30" value="<%= lastName %>">
    </td>
    <td align="right" nowrap>Corporate Name:&nbsp;</td>
    <td align="left" nowrap>
      <input type="text" name="corporateName" size="60" maxlength="60" value="<%= corporateName %>">
    </td>
  </tr>
  <tr>
    <td align="right" nowrap>Premium Type:&nbsp;</td>
    <td align="left" nowrap>
      <select name="premiumType">
        <%
          out.println("<option>Please Select</option>");

          for(int i = 0; i < premiumTypeCTVO.length; i++)
          {
              String codeTablePK = premiumTypeCTVO[i].getCodeTablePK() + "";
              String codeDesc    = premiumTypeCTVO[i].getCodeDesc();
              String code        = premiumTypeCTVO[i].getCode();

             if (premiumType.equals(code)) {

                 out.println("<option selected name=\"id\" value=\"" + codeTablePK + "\">" + codeDesc + "</option>");
             }
             else  {

                 out.println("<option name=\"id\" value=\"" + codeTablePK + "\">" + codeDesc + "</option>");
             }
          }

        %>
      </select>
    </td>
    <td align="right" nowrap>Deposit Type:&nbsp;</td>
    <td align="left" nowrap colspan="3">
      <select name="depositType">
        <%
          out.println("<option>Please Select</option>");

          for(int i = 0; i < depositTypeCTVO.length; i++)
          {
              String codeTablePK = depositTypeCTVO[i].getCodeTablePK() + "";
              String codeDesc    = depositTypeCTVO[i].getCodeDesc();
              String code        = depositTypeCTVO[i].getCode();

             if (depositType.equals(code)) {

                 out.println("<option selected name=\"id\" value=\"" + codeTablePK + "\">" + codeDesc + "</option>");
             }
             else  {

                 out.println("<option name=\"id\" value=\"" + codeTablePK + "\">" + codeDesc + "</option>");
             }
          }

        %>
      </select>
    </td>
  </tr>
  <tr>
    <td align="right" nowrap>Fund:&nbsp;</td>
    <td align="left" nowrap colspan="5">
      <select name="filteredFundFK">
        <option selected value="Please Select"> Please Select </option>
        <%
            if (hedgeFilteredFundVOs != null)
            {
                for(int i = 0; i < hedgeFilteredFundVOs.length; i++)
                {
                    String filteredFundPK = (new Long(hedgeFilteredFundVOs[i].getFilteredFundPK())).toString();

                    String fundNumber = hedgeFilteredFundVOs[i].getFundNumber();

                    if (fund.equals(filteredFundPK))
                    {
                        out.println("<option selected name=\"id\" value=\"" + filteredFundPK + "\">" + fundNumber + "</option>");
                    }
                    else
                    {
                        out.println("<option name=\"id\" value=\"" + filteredFundPK + "\">" + fundNumber + "</option>");
                    }
                }
            }
        %>
      </select>
    </td>
  </tr>
  <tr class="filler">
    <td colspan="6">
      &nbsp; <!-- Filler Row -->
    </td>
  </tr>
  <tr>
    <td align="right" nowrap>Deposit Attached&nbsp;</td>
    <td align="left" nowrap colspan="5">
      <input disabled type="checkbox" name="depositAttached" <%= depositAttached %> >
    </td>
  </tr>
  <tr>
    <td align="right" nowrap>Exchange Company:&nbsp;</td>
    <td align="left" nowrap>
      <input type="text" name="exchangeCompany" size="30" maxlength="30" value="<%= exchangeCompany %>">
    </td>
    <td align="right" nowrap>Exchange Policy Number:&nbsp;</td>
    <td align="left" nowrap>
      <input type="text" name="exchangePolicy" size="15" maxlength="15" value="<%= exchangePolicy %>">
    </td>
    <td align="right" nowrap>Cost Basis:&nbsp;</td>
    <td align="left" nowrap>
      <input type="text" name="costBasis" size="15" maxlength="15" value="<%= costBasis.toString() %>" CURRENCY>
    </td>
  </tr>
  <tr class="filler">
    <td colspan="6">
      &nbsp; <!-- Filler Row -->
    </td>
  </tr>
</table>

<%-- ********** END Form Data ********** --%>

<%-- ********** BEGIN Summary Area ********** --%>

<jsp:include page="/common/jsp/widget/tableWidget.jsp" flush="true">
  <jsp:param name="tableId" value="CashBatchDetailTableModel"/>
  <jsp:param name="tableHeight" value="30"/>
  <jsp:param name="multipleRowSelect" value="false"/>
  <jsp:param name="singleOrDoubleClick" value="single"/>
</jsp:include>

<%-- ********** END Summary Area ********** --%>

<%-- Buttons --%>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr valign="top">
    <td nowrap align="right">
      <input type="button" id="btnSave" name="save" value="Save" onClick="saveSuspense()">
      <input type="button" id="btnCancel" name="cancel" value="Cancel" onClick="cancelSuspenseEntry()">
      <input type="button" id="btnClose" name="close" value="Close" onClick="closeSuspenseEntry()">
    </td>
  </tr>
</table>

<%-- ********** BEGIN Hidden Variables ********** --%>

  	<input type="hidden" name="transaction" value="">
    <input type="hidden" name="action"      value="">
    <input type="hidden" name="batchId" value="<%= batchId %>">
    <input type="hidden" name="cashBatchContractFK" value="<%= cashBatchContractFK %>">
    <input type="hidden" name="suspensePK" value="<%= suspensePK %>">
    <input type="hidden" name="depositsPK" value="<%= depositsPK %>">
    <input type="hidden" name="viewMode" value="<%= viewMode %>">
    <input type="hidden" name="policyNumberWhenDisabled" value="<%= policyNumber %>">
    <input type="hidden" name="suspenseAmountWhenDisabled" value="<%= suspenseAmount %>">
    <input type="hidden" name="balanceRemaining" value="<%= balanceRemaining %>">

    <%-- values for CashBatch filtering --%>
    <input type="hidden" name="status" value="<%= status %>">
    <input type="hidden" name="month" value="<%= month %>">
    <input type="hidden" name="day" value="<%= day %>">
    <input type="hidden" name="year" value="<%= year %>">
    <input type="hidden" name="filterAmount" value="<%= filterAmount %>">
    <input type="hidden" name="filterOperator" value="<%= filterOperator %>">

<%-- ********** END Hidden Variable ********** --%>

</body>
</form>
</html>
