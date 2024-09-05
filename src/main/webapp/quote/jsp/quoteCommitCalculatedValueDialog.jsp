<!-- quoteCommitCalculatedValueDialog.jsp //-->

<!-- ****** JAVA CODE ***** //-->

<%@ page  errorPage="/common/jsp/error.jsp" autoFlush="true" %>
<%@ page import="java.util.*, fission.beans.*" %>

<jsp:useBean id="quoteMainSessionBean"
    class="fission.beans.SessionBean" scope="session"/>

<%
	PageBean formBean  = quoteMainSessionBean.getPageBean("formBean");

	String certainDuration         = formBean.getValue("certainDuration");
	String finalDistributionAmount = formBean.getValue("finalDistributionAmount");
	String premimumTaxes           = formBean.getValue("premiumTaxes");
	String fees                    = formBean.getValue("fees");
	String frontEndLoads           = formBean.getValue("frontEndLoads");
	String totalProjectedAnnuity   = formBean.getValue("totalProjectedAnnuity");
	String exclusionRatio          = formBean.getValue("exclusionRatio");
	String excessInterest          = formBean.getValue("excessInterest");
	String yearlyTaxableBenefit    = formBean.getValue("yearlyTaxableBenefit");
	String mrdAmount               = formBean.getValue("mrdAmount");
    String commutedValue           = formBean.getValue("commutedValue");
    String guidelineSinglePrem     = formBean.getValue("guidelineSinglePrem");
    String guidelineLevelPrem      = formBean.getValue("guidelineLevelPrem");
    String tamra                   = formBean.getValue("tamra");
%>

<html>
<head>

<script src="/PORTAL/common/javascript/commonJavascriptFunctions.js"></script>

<script language="Javascript1.2">

	var f = null;

	function init()
    {
		f = document.calculatedValueForm;

        formatCurrency();
	}
</script>

<title>Calculated Value</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>

<body class="mainTheme" bgcolor="#DDDDDD" onLoad="init()">
<form name="calculatedValueForm" method="post" action="/PORTAL/servlet/RequestManager">
  <table id="table1" width="80%" border="0" cellspacing="0" cellpadding="0" bgcolor="#DDDDDD">
    <tr>
      <td align="right" nowrap>Certain Duration:&nbsp;</td>
      <td align="left" nowrap>
        <input disabled type="text" name="certainDuration" size="8" value="<%= certainDuration %>">
      </td>
      <td align="right" nowrap>Final Distribution Amount:&nbsp;</td>
      <td align="left" nowrap>
        <input disabled type="text" name="finalDistributionAmount" size="15" value="<%= finalDistributionAmount %>" CURRENCY>
      </td>
    </tr>
    <tr>
      <td colspan="4" nowrap>&nbsp;</td>
    </tr>
    <tr>
      <td align="right" nowrap>Premium Taxes:&nbsp;</td>
      <td align="left" nowrap>
        <input disabled type="text" name="premimumTaxes" value="<%= premimumTaxes %>" CURRENCY>
      </td>
      <td align="right" nowrap>Front-end Loads:&nbsp;</td>
      <td align="left" nowrap>
        <input disabled type="text" name="frontEndLoads" value= "<%= frontEndLoads %>" CURRENCY>
      </td>
    </tr>
    <tr>
      <td colspan="4" nowrap>&nbsp;</td>
    </tr>
    <tr>
      <td align="right" nowrap>Fees:&nbsp;</td>
      <td align="left" nowrap>
        <input disabled type="text" name="fees" value="<%= fees %>" CURRENCY>
      </td>
      <td align="right" nowrap>Total Projected Annuity:&nbsp;</td>
      <td align="left" nowrap>
        <input disabled type="text" name="totalProjectedAnnuity" value="<%= totalProjectedAnnuity%>" CURRENCY>
      </td>
    </tr>
    <tr>
      <td colspan="4" nowrap>&nbsp;</td>
    </tr>
    <tr>
      <td align="right" nowrap>Exclusion Ratio:&nbsp;</td>
      <td align="left" nowrap>
        <input disabled type="text" name="exclusionRatio" value="<%= exclusionRatio%>">
      </td>
      <td align="right" nowrap>Excess Interest:&nbsp;</td>
      <td align="left" nowrap>
        <input disabled type="text" name="excessInterest" value="<%= excessInterest %>" CURRENCY>
      </td>
    </tr>
    <tr>
      <td colspan="4" nowrap>&nbsp;</td>
    </tr>
    <tr>
      <td align="right" nowrap>Yearly Taxable Benefit:&nbsp;</td>
      <td align="left" nowrap>
        <input disabled type="text" name="yearlyTaxableBenefit" value="<%= yearlyTaxableBenefit %>" CURRENCY>
      </td>
      <td align="right" nowrap>MRD Amount:&nbsp;</td>
      <td align="left" nowrap>
        <input disabled type="text" name="mrdAmount" value="<%= mrdAmount %>" CURRENCY>
      </td>
    </tr>
    <tr>
      <td colspan="4" nowrap>&nbsp;</td>
    </tr>
    <tr>
      <td align="right" nowrap>Commuted Value:&nbsp;</td>
      <td align="left" nowrap>
        <input disabled type="text" name="commutedValue" value="<%= commutedValue %>" CURRENCY>
      </td>
      <td align="right" nowrap>TAMRA:&nbsp;</td>
      <td align="left" nowrap>
        <input disabled type="text" name="tamra" value="<%= tamra %>" CURRENCY>
      </td>
    </tr>
    <tr>
      <td colspan="4" nowrap>&nbsp;</td>
    </tr>
    <tr>
      <td align="right" nowrap>Guideline Single Premium:&nbsp;</td>
      <td align="left" nowrap>
        <input disabled type="text" name="guidelineSinglePrem" value="<%= guidelineSinglePrem %>" CURRENCY>
      </td>
      <td align="right" nowrap>Guideline Level Premium:&nbsp;</td>
      <td align="left" nowrap>
        <input disabled type="text" name="guidelineLevelPrem" value="<%= guidelineLevelPrem %>" CURRENCY>
      </td>
    </tr>
    <tr>
      <td colspan="4" nowrap>&nbsp;</td>
    </tr>
    <tr>
      <td align="right" nowrap colspan="4">

        <input type="button" name="enter" value="Enter" onClick="closeWindow()">
      </td>
    </tr>
  </table>

     <!-- ****** HIDDEN FIELDS ***** //-->
  <input type="hidden" name="transaction" value="">
  <input type="hidden" name="action"      value="">

</form>
</body>
</html>
