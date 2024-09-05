<!-- ****** JAVA CODE ***** //-->

<%@ page  errorPage="/common/jsp/error.jsp" autoFlush="true" %>
<%@ page import="java.util.*, fission.beans.*" %>

<html>
<head>
<title>Untitled Document</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">

<script src="/PORTAL/common/javascript/commonJavascriptFunctions.js"></script>

<script language="JavaScript">

   	var f = null;

	function init()
    {
		f = document.dailyform;
    }

    function runChartOfAccountsReport()
    {
        sendTransactionAction("DailyDetailTran", "runChartOfAccountsReport", "main");
    }

    function showAccountingDetailRun()
    {
        sendTransactionAction("DailyDetailTran", "showAccountingDetailRun", "main");
    }

    function showFinancialActivityRun()
    {
        sendTransactionAction("DailyDetailTran", "showFinancialActivityRun", "main");
    }

    function showSelectPayoutTrxReportParams()
    {
        sendTransactionAction("DailyDetailTran", "showSelectPayoutTrxReportParams", "main");
    }

    function showControlsAndBalancesRun()
    {
        sendTransactionAction("DailyDetailTran", "showControlsAndBalancesRun", "main");
    }

    function showWithholdingRptParamSelection()
    {
        sendTransactionAction("DailyDetailTran", "showWithholdingRptParamSelection", "main");
    }

    function showSepAcctValByDivision()
    {
        sendTransactionAction("DailyDetailTran", "showSepAcctValByDivision", "main");
    }

    function showSepAcctValByCase()
    {
        sendTransactionAction("DailyDetailTran", "showSepAcctValByCase", "main");
    }

    function showCaseManagerReviewParams()
    {
        sendTransactionAction("DailyDetailTran", "showCaseManagerReviewParams", "main");
    }

    function showFundActivityReportParams()
    {
        sendTransactionAction("DailyDetailTran", "showFundActivityReportParams", "main");
    }

    function showAssetLiabilitiesReportParams()
    {
        sendTransactionAction("DailyDetailTran", "showAssetLiabilitiesReportParams", "main");
    }

</script>
</head>

<body bgcolor="#DDDDDD" onLoad="init()">
<form name="dailyform" method="post" action="/PORTAL/servlet/RequestManager">
<span class="unnamed1"></span>
<table width="100%" border="0" height="296">
  <tr align="center" valign="middle">
    <td height="294" width="3%"  bordercolor="#DDDDDD" bgcolor="#DDDDDD">&nbsp;
    </td>
    <td height="294" width="94%" align="top" valign="middle" class="unnamed1">
      <table width="53%" border="0" align="center">
        <tr>
            <td height="34" width="11%">&nbsp;</td>
            <td height="34" width="86%">&nbsp;</td>
            <td height="34" width="3%">&nbsp;</td>
        </tr>
        <tr>
            <td height="42" width="11%">&nbsp;</td>
            <td height="42" width="11%"><b><font face="Arial, Helvetica, sans-serif" size="4">
                 <a href="javascript:runChartOfAccountsReport()">Chart Of Accounts Report</a>
  	        </td>
        </tr>
        <tr>
            <td height="42" width="11%">&nbsp;</td>
            <td height="42" width="11%"><b><font face="Arial, Helvetica, sans-serif" size="4">
                 <a href="javascript:showAccountingDetailRun()">Accounting Detail</a>
	        </td>
        </tr>
        <tr>
            <td height="42" width="11%">&nbsp;</td>
            <td height="42" width="11%"><b><font face="Arial, Helvetica, sans-serif" size="4">
                 <a href="javascript:showFinancialActivityRun()">Financial Activity Report</a>
	      </td>
        </tr>
        <tr>
            <td height="42" width="11%">&nbsp;</td>
            <td height="42" width="11%"><b><font face="Arial, Helvetica, sans-serif" size="4">
                 <a href="javascript:showSelectPayoutTrxReportParams()">Payout Transaction Report</a>
	      </td>
        </tr>
        <tr>
            <td height="42" width="11%">&nbsp;</td>
            <td height="42" width="11%"><b><font face="Arial, Helvetica, sans-serif" size="4">
                 <a href="javascript:showControlsAndBalancesRun()">Controls And Balances Report</a>
	      </td>
        </tr>
        <tr>
            <td height="42" width="11%">&nbsp;</td>
            <td height="42" width="11%"><b><font face="Arial, Helvetica, sans-serif" size="4">
                 <a href="javascript:showWithholdingRptParamSelection()">Withholding Report</a>
	      </td>
        </tr>
        <tr>
            <td height="42" width="11%">&nbsp;</td>
            <td height="42" width="11%"><b><font face="Arial, Helvetica, sans-serif" size="4">
                 <a href="javascript:showSepAcctValByDivision()">Separate Account Investment Values By Division</a>
	      </td>
        </tr>
        <tr>
            <td height="42" width="11%">&nbsp;</td>
            <td height="42" width="11%"><b><font face="Arial, Helvetica, sans-serif" size="4">
                 <a href="javascript:showSepAcctValByCase()">Separate Account Investment Values By Case</a>
	      </td>
        </tr>
        <tr>
            <td height="42" width="11%">&nbsp;</td>
            <td height="42" width="11%"><b><font face="Arial, Helvetica, sans-serif" size="4">
                 <a href="javascript:showCaseManagerReviewParams()">Case Manager Review</a>
	      </td>
        </tr>
        <tr>
            <td height="42" width="11%">&nbsp;</td>
            <td height="42" width="11%"><b><font face="Arial, Helvetica, sans-serif" size="4">
                 <a href="javascript:showFundActivityReportParams()">Fund Activity Report</a>
	      </td>
        </tr>
        <tr>
            <td height="42" width="11%">&nbsp;</td>
            <td height="42" width="11%"><b><font face="Arial, Helvetica, sans-serif" size="4">
                 <a href="javascript:showAssetLiabilitiesReportParams()">Asset - Liabilities Report (ALR)</a>
	      </td>
        </tr>
      </table>
    </td>
  </tr>
</table>

<!-- ****** Hidden Values ***** //-->
	<input type="hidden" name="transaction">
	<input type="hidden" name="action">

</form>
</body>
</html>
