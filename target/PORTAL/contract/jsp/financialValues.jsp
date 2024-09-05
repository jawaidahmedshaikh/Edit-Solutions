<!--
 * (c) 2000 - 2004 Systems Engineering Group, LLC.  All rights Reserved
 *  Systems Engineering Group, LLC Propietary and confidential.  Any use
 *  subject to the license agreement.
 -->
<!-- contractFinancialValuesDialog.jsp //-->

<!-- ****** JAVA CODE ***** //-->

<%@ page  errorPage="/common/jsp/error.jsp" autoFlush="true" %>
<%@ page import="java.util.*, fission.beans.*,
                 fission.utility.Util,
                 edit.common.*" %>

<jsp:useBean id="contractMainSessionBean"
    class="fission.beans.SessionBean" scope="session"/>

<%
	PageBean formBean  = contractMainSessionBean.getPageBean("formBean");

	String lumpSumYrToDate = Util.initString(formBean.getValue("lumpSumYrToDate"), "0.00");
	String accumulatedFees = Util.initString(formBean.getValue("accumulatedFees"), "0.00");
	String amountPaidYrToDate = Util.initString(formBean.getValue("amountPaidYrToDate"), "0.00");
    String guidelineSinglePrem = Util.initString(formBean.getValue("guidelineSinglePrem"), "0.00");
    String guidelineLevelPrem = Util.initString(formBean.getValue("guidelineLevelPrem"), "0.00");
    String tamra = Util.initString(formBean.getValue("tamra"), "0.00");
	String purchaseAmount = Util.initString(formBean.getValue("purchaseAmount"), "0.00");
	String costBasis = Util.initString(formBean.getValue("costBasis"), "0.00");
	String exclusionRatio = Util.initString(formBean.getValue("exclusionRatio"), "0.00");
	String amountPaidToDate = Util.initString(formBean.getValue("amountPaidToDate"), "0.00");
	String accumulatedLoads = Util.initString(formBean.getValue("accumulatedLoads"), "0.00");
	String withdrawalYrToDate = Util.initString(formBean.getValue("withdrawlYrToDate"), "0.00");
	String yearlyTaxableBenefit = Util.initString(formBean.getValue("yearlyTaxableBenefit"), "0.00");
	String mrdAmount = Util.initString(formBean.getValue("mrdAmount"), "0.00");
	String lumpSumPaidToDate = Util.initString(formBean.getValue("lumpSumPaidToDate"), "0.00");
	String paymentAmount = Util.initString(formBean.getValue("paymentAmount"), "0.00");
	String totalExpectedReturn = Util.initString(formBean.getValue("totalExpectedReturn"), "0.00");
	String recoveredCostBasis = Util.initString(formBean.getValue("recoveredCostBasis"), "0.00");
	String finalDistributionAmount = Util.initString(formBean.getValue("finalDistributionAmount"), "0.00");
    String freeAmount = Util.initString(formBean.getValue("freeAmount"), "0.00");
    String freeAmountRemaining = Util.initString(formBean.getValue("freeAmountRemaining"), "0.00");
    String tamraStartDate = Util.initString(formBean.getValue("tamraStartDate"), "");
    String MAPEndDate = Util.initString(formBean.getValue("MAPEndDate"), "");
    String mecGuidelineSinglePremium = Util.initString(formBean.getValue("mecGuidelineSinglePremium"), "0.00");
    String mecGuidelineLevelPremium = Util.initString(formBean.getValue("mecGuidelineLevelPremium"), "0.00");
    String cumGuidelineLevelPremium = Util.initString(formBean.getValue("cumGuidelineLevelPremium"), "0.00");
    String maxNetAmountAtRisk = Util.initString(formBean.getValue("maxNetAmountAtRisk"), "0.00");
    String chargeDeductAmount = Util.initString(formBean.getValue("chargeDeductAmount"), "0.00");
%>

<html>
<head>
<title>Financial Values</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">

<script src="/PORTAL/common/javascript/commonJavascriptFunctions.js"></script>

<script language="Javascript1.2">

	var f = null;

	function init()
    {
		f = document.financialValueForm;

        formatCurrency();
	}
</script>
</head>
<body class="mainTheme" bgcolor="#DDDDDD" onLoad="init()">
<form name="financialValueForm" method="post" action="/PORTAL/servlet/RequestManager">
  <table id="table1" width="45%" border="0" cellspacing="6" cellpadding="0">
    <tr>
      <td nowrap width="5%" align="right">Amount:</td>
      <td nowrap align="left">
        <input type="text" name="purchaseAmount" size="19" maxlength="19" disabled value="<%= purchaseAmount %>" CURRENCY>
      </td>
      <td nowrap width="5%" align="right">Payment Amount:</td>
      <td nowrap align="left">
        <input type="text" name="paymentAmount" size="19" maxlength="19" disabled value="<%= paymentAmount %>" CURRENCY>
      </td>
    </tr>
    <tr>
      <td nowrap width="5%" align="right">Cost Basis:</td>
      <td nowrap align="left">
        <input type="text" name="costBasis" size="19" maxlength="19" disabled value="<%= costBasis %>" CURRENCY>
      </td>
      <td nowrap width="5%" align="right">Total Expected Return:</td>
      <td nowrap align="left">
        <input type="text" name="totalExpectedReturn" size="19" maxlength="19" disabled value="<%= totalExpectedReturn %>" CURRENCY>
      </td>
    </tr>
    <tr>
      <td nowrap width="5%" align="right">Exclustion Ratio:</td>
      <td nowrap align="left">
        <input type="text" name="exclusionRatio" size="19" maxlength="19" disabled value="<%= exclusionRatio %>">
      </td>
      <td nowrap width="5%" align="right">Recovered Cost Basis:</td>
      <td nowrap align="left">
        <input type="text" name="recoveredCostBasis" size="19" maxlength="19" disabled value="<%= recoveredCostBasis %>" CURRENCY>
      </td>
    </tr>
    <tr>
      <td nowrap width="5%" align="right">Amount Paid-To-Date:</td>
      <td nowrap align="left">
        <input type="text" name="amountPaidToDate" size="19" maxlength="19" disabled value="<%= amountPaidToDate %>" CURRENCY>
      </td>
      <td nowrap width="5%" align="right">Amt Pd Yr-To-Date:</td>
      <td nowrap align="left">
        <input type="text" name="amountPaidYrToDate" size="19" maxlength="19" disabled value="<%= amountPaidYrToDate %>" CURRENCY>
      </td>
    </tr>
    <tr>
      <td nowrap width="5%" align="right">Accumulated Loads:</td>
      <td nowrap align="left">
        <input type="text" name="accumulatedLoads" size="19" maxlength="19" disabled value="<%= accumulatedLoads %>" CURRENCY>
      </td>
      <td nowrap width="5%" align="right">Accumulated Fees:</td>
      <td nowrap align="left">
        <input type="text" name="accumulatedFees" size="19" maxlength="19" disabled value="<%= accumulatedFees %>" CURRENCY>
      </td>
    </tr>
    <tr>
      <td nowrap width="5%" align="right">Withdrawal Yr-To-Date:</td>
      <td nowrap align="left">
        <input type="text" name="withdrawalYrToDate" size="19" maxlength="19" disabled value="<%= withdrawalYrToDate %>" CURRENCY>
      </td>
      <td nowrap width="5%" align="right">Final Distribution Amount:</td>
      <td nowrap align="left">
        <input type="text" name="finalDistributionAmount" size="19" maxlength="19" disabled value="<%= finalDistributionAmount %>" CURRENCY>
      </td>
    </tr>
    <tr>
      <td nowrap width="5%" align="right">Yearly Taxable Benefit:</td>
      <td nowrap align="left">
        <input type="text" name="yearlyTaxableBenefit" size="19" maxlength="19" disabled value="<%= yearlyTaxableBenefit %>" CURRENCY>
      </td>
      <td nowrap width="5%" align="right">MRD Amount:</td>
      <td nowrap align="left">
        <input type="text" name="mrdAmount" size="19" maxlength="19" disabled value="<%= mrdAmount %>" CURRENCY>
      </td>
    </tr>
    <tr>
      <td nowrap width="5%" align="right">Lump Sum Paid-To-Date: </td>
      <td nowrap align="left">
        <input type="text" name="lumpSumPaidToDate" size="19" maxlength="19" disabled value="<%= lumpSumPaidToDate %>" CURRENCY>
      </td>
      <td nowrap width="5%" align="right">Lump Sum Yr-To-Date: </td>
      <td nowrap align="left">
        <input type="text" name="lumpSumYrToDate" size="19" maxlength="19" disabled value="<%= lumpSumYrToDate %>" CURRENCY>
      </td>
    </tr>
    <tr>
      <td nowrap width="5%" align="right">Free Amount: </td>
      <td nowrap align="left">
        <input type="text" name="freeAmount" size="19" maxlength="19" disabled value="<%= freeAmount %>" CURRENCY>
      </td>
      <td nowrap width="5%" align="right">Free Amt Remaining: </td>
      <td>
        <input type="text" name="freeAmountRemaining" size="19" maxlength="19" disabled value="<%= freeAmountRemaining %>" CURRENCY>
      </td>
    </tr>
    <tr>
      <td nowrap width="5%" align="right">Guideline Single Premium: </td>
      <td nowrap align="left">
        <input type="text" name="guidelineSinglePrem" size="28" maxlength="28" disabled value="<%= guidelineSinglePrem %>" CURRENCY>
      </td>
      <td nowrap width="5%" align="right">Guideline Level Premium: </td>
      <td nowrap align="left">
        <input type="text" name="guidelineLevelPrem" size="19" maxlength="19" disabled value="<%= guidelineLevelPrem %>" CURRENCY>
      </td>
    </tr>
    <tr>
      <td nowrap width="5%" align="right">TAMRA: </td>
      <td nowrap align="left">
        <input type="text" name="tamra" size="19" maxlength="19" disabled value="<%= tamra %>" CURRENCY>
      </td>
<%--      <td nowrap colspan="2">&nbsp;</td>--%>
      <td nowrap width="5%" align="right">TAMRA Start Date: </td>
      <td nowrap align="left">
        <input type="text" name="tamraStartDate" size="10" maxlength="10" disabled value="<%= tamraStartDate %>">
      </td>
    </tr>
    <tr>
      <td nowrap width="5%" align="right">MEC Guideline Single Premium: </td>
      <td nowrap align="left">
        <input type="text" name="mecGuidelineSinglePremium" size="19" maxlength="19" disabled value="<%= mecGuidelineSinglePremium %>" CURRENCY>
      </td>
      <td nowrap width="5%" align="right">MEC Guideline Level Premium: </td>
      <td nowrap align="left">
        <input type="text" name="mecGuidelineLevelPremium" size="19" maxlength="19" disabled value="<%= mecGuidelineLevelPremium %>" CURRENCY>
      </td>
    </tr>
    <tr>
      <td nowrap width="5%" align="right">Cum Guideline Level Premium: </td>
      <td nowrap align="left">
        <input type="text" name="cumGuidelineLevelPremium" size="19" maxlength="19" disabled value="<%= cumGuidelineLevelPremium %>" CURRENCY>
      </td>
      <td nowrap width="5%" align="right">Max Net Amount At Risk: </td>
      <td nowrap align="left">
        <input type="text" name="maxNetAmountAtRisk" size="19" maxlength="19" disabled value="<%= maxNetAmountAtRisk %>" CURRENCY>
      </td>
    </tr>
    <tr>
      <td nowrap width="5%" align="right">Charge Deduct Remaining:&nbsp;</td>
      <td nowrap align="left" colspan="3">
        <input type="text" name="chargeDeductRemaining" size="19" maxlength="19" disabled value="<%= chargeDeductAmount %>" CURRENCY>
      </td>
    </tr>
    <tr>
      <td colspan="3">&nbsp;</td>
      <td align="right">
        <input type="button" name="enter" value="Enter" onClick="closeWindow()">
      </td>
    </tr>
  </table>
</form>
</body>
</html>
