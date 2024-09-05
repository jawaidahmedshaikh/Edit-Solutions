<%@ page  errorPage="/common/jsp/error.jsp" autoFlush="true" %>
<%@ page import="java.util.*,
                 edit.common.*,
                 edit.common.vo.*,
                 edit.common.vo.user.*,
                 fission.beans.*,
                 edit.portal.common.session.UserSession,
                 java.text.DecimalFormat,
                 fission.utility.Util" %>

<%
    BucketHistoryVO[] bucketHistoryVOs = (BucketHistoryVO[]) request.getAttribute("bucketHistoryVOs");
    InvestmentHistoryVO[] investmentHistoryVOs = (InvestmentHistoryVO[]) request.getAttribute("investmentHistoryVOs");

    // use this to look up the charge code number to show for a given charge code fk that is in the
    // investmentHistoryVOs.
    Map mapChargeCodePKsToNumbers = (Map) request.getAttribute("mapChargeCodePKsToNumbers");

    String fundName = (String) request.getAttribute("fundName");
%>

<html>

<script src="/PORTAL/common/javascript/commonJavascriptFunctions.js"></script>

<script language="Javascript1.2">

	var f = null;

	function init()
    {
		f = document.bucketAndInvestmentHistoriesForm;

        formatCurrency();
	}

</script>

<head>
<title>Bucket and Investment History</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta http-equiv="Cache-Control" content="no-store">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Expires" content="0">

<link href="/PORTAL/common/css/EDITSolutions.css" rel="stylesheet" type="text/css">
</head>

<body class="mainTheme" onLoad="init()" style="border-style:solid; border-width:1;">
<form name= "bucketAndInvestmentHistoriesForm" method="post" action="/PORTAL/servlet/RequestManager">

  <table width="100%" height="2%" cellspacing="0" cellpadding="2">
    <tr>
      <td nowrap align="left">Fund Name:&nbsp; <%= fundName %></td>
    </tr>
  </table>
  <br>
  <br>
  Bucket History
  <table class="summary" width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr class="heading">
      <th align="left" width="20%">Deposit Date</th>
	  <th align="left" width="20%">Bonus Amt</th>
	  <th align="left" width="20%">Dollars</th>
	  <th align="left" width="20%">Units</th>
	  <th align="left" width="20%">Cum Unit/Dollars</th>
	</tr>
  </table>
  <span class="scrollableContent" style="border-style:solid; border-width:1;  position:relative; width:100%; height:30%; top:0; left:0; background-color:#BBBBBB">
    <table class="summary" id="bucketSummary" width="100%" style="border-style:solid; border-width:0;" cellspacing="0" cellpadding="0">
	  <%
          if (bucketHistoryVOs != null)
          {
              for (int i = 0; i < bucketHistoryVOs.length; i++)
              {
                  String depositDate = ((BucketVO) bucketHistoryVOs[i].getParentVO(BucketVO.class)).getDepositDate();
                  String cumulativeDollars = Util.initString(bucketHistoryVOs[i].getCumDollars().toString(), "0.00");
                  String dollars = Util.initString(bucketHistoryVOs[i].getDollars().toString(), "0.00");
                  String bonusAmount = Util.initString(bucketHistoryVOs[i].getBonusAmount().toString(), "0.00");
                  String cumulativeUnits = Util.initString(bucketHistoryVOs[i].getCumUnits().toString(), "0.0000000000");
                  String units = (new EDITBigDecimal(bucketHistoryVOs[i].getUnits())).toString();
                  String summaryValue = "";
                  if (new EDITBigDecimal(cumulativeUnits).isEQ(new EDITBigDecimal()))
                  {
                      summaryValue = cumulativeDollars;
                  }
                  else
                  {
                      summaryValue = cumulativeUnits;
                  }
	  %>
        <tr>
          <td nowrap width="20%">
            <%= depositDate %>
          </td>
          <td nowrap width="20%">
            <script>document.write(formatAsCurrency(<%= bonusAmount %>))</script>
          </td>
          <td nowrap width="20%">
            <script>document.write(formatAsCurrency(<%= dollars %>))</script>
          </td>
          <td nowrap width="20%">
            <%= units %>
          </td>
          <td nowrap width="20%">
          <%
            if (new EDITBigDecimal(cumulativeUnits).isEQ(new EDITBigDecimal()))
            {
          %>
          <script>document.write(formatAsCurrency(<%= summaryValue %>))</script>
          <%
            }
            else
            {
          %>
            <%= summaryValue %>
          <%
            }
          %>
          </td>
        </tr>
      <%
              }
          }
	  %>
    </table>
  </span>
  <br>
  <br>
  Investment History
  <table class="summary" width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr class="heading">
      <th align="left" width="14%">Valuation Date</th>
	  <th align="left" width="14%">Dollars</th>
	  <th align="left" width="14%">Units</th>
      <th align="left" width="14%">Gain/Loss</th>
	  <th align="left" width="14%">To/From</th>
      <th align="left" width="14%">ChargeCode</th>
      <th align="left" width="14%">Final Price Status</th>
	</tr>
  </table>
  <span class="scrollableContent" style="border-style:solid; border-width:1;  position:relative; width:100%; height:30%; top:0; left:0; background-color:#BBBBBB">
    <table class="summary" id="invHistSummary" width="100%" style="border-style:solid; border-width:0;" cellspacing="0" cellpadding="0">
	  <%
          if (investmentHistoryVOs != null)
          {
              for (int i = 0; i < investmentHistoryVOs.length; i++)
              {
                  String valuationDate = Util.initString(investmentHistoryVOs[i].getValuationDate(), "");
                  String dollars = Util.initString(investmentHistoryVOs[i].getInvestmentDollars().toString(), "0.00");
                  String units = (new EDITBigDecimal(investmentHistoryVOs[i].getInvestmentUnits())).toString();
                  String gainLoss = Util.initString(investmentHistoryVOs[i].getGainLoss().toString(), "0.00");
                  String toFromStatus = Util.initString(investmentHistoryVOs[i].getToFromStatus(), "");
                  String finalPriceStatus = Util.initString(investmentHistoryVOs[i].getFinalPriceStatus(), "");
                  long chargeCodeFK = investmentHistoryVOs[i].getChargeCodeFK();
                  String chargeCodeNum = "";
                  if (chargeCodeFK != 0L)
                  {
                       chargeCodeNum = (String) mapChargeCodePKsToNumbers.get(new Long(chargeCodeFK));
                  }
      %>
        <tr>
          <td nowrap width="14%">
            <%= valuationDate %>
          </td>
          <td nowrap width="14%">
            <script>document.write(formatAsCurrency(<%= dollars %>))</script>
          </td>
          <td nowrap width="14%">
            <%= units %>
          </td>
          <td nowrap width="14%">
            <script>document.write(formatAsCurrency(<%= gainLoss %>))</script>
          </td>
          <td nowrap width="14%">
            <%= toFromStatus %>
          </td>
          <td nowrap width="14%">
            <%= chargeCodeNum %>
          </td>
          <td nowrap width="14%">
            <%= finalPriceStatus %>
          </td>
        </tr>
      <%
              }
          }
	  %>
    </table>
  </span>
  <table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr>
	  <td align="right" nowrap>
	    <input type="button" name="cancel" value="Close" onClick ="closeWindow()">
	  </td>
	</tr>
  </table>

<!-- ****** HIDDEN FIELDS ***** //-->
 <input type="hidden" name="transaction" value="">
 <input type="hidden" name="action"      value="">

</form>
</body>
</html>
