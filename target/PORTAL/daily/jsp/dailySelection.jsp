<%@ page  errorPage="/common/jsp/error.jsp" autoFlush="true" %>
<%@ page import="java.util.*, fission.beans.*" %>
<%
    String responseMessage = (String) request.getAttribute("responseMessage");
%>

<html>
<head>
<title>Daily Selection</title>


<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">

<script src="/PORTAL/common/javascript/commonJavascriptFunctions.js"></script>

<script language="JavaScript">

    var responseMessage = "<%= responseMessage %>";

   	var f = null;

	function init() {

		f = document.dailyform;

        checkForResponseMessage();
    }

    function importUnitValues()
    {
        sendTransactionAction("DailyDetailTran", "showImportUnitValuesParams", "main");
    }

    function showBatch()
    {
        sendTransactionAction("DailyDetailTran", "showBatchRun", "main");
    }

    function showAccounting()
    {
        sendTransactionAction("DailyDetailTran", "showSelectAccountingToRun", "main");
    }

    function showConfirms()
    {
        sendTransactionAction("DailyDetailTran", "showSelectConfirmsToRun", "main");
    }

    function showBank()
    {
        sendTransactionAction("DailyDetailTran", "showBank", "main");
    }

    function showBankForNACHA()
    {
        sendTransactionAction("DailyDetailTran", "showBankForNACHA", "main");
    }

    function showReports()
    {
        sendTransactionAction("DailyDetailTran", "showReports", "main");
    }

    function showCorrespondence()
    {
        sendTransactionAction("DailyDetailTran", "showCorrespondence", "main");
    }

    function showEquityIndexHedge()
    {
        sendTransactionAction("DailyDetailTran", "showEquityIndexHedge", "main");
    }

    function showGAAPPremiumExtract()
    {
        sendTransactionAction("DailyDetailTran", "showGAAPPremiumExtract", "main");
    }

    function showACTFileParams()
    {
        sendTransactionAction("DailyDetailTran", "showACTFileParams", "main");
    }

    function showPRDParams()
    {
        sendTransactionAction("DailyDetailTran", "showPRDParams", "main");
    }

    function showPRDCompareParams()
    {
        sendTransactionAction("DailyDetailTran", "showPRDCompareParams", "main");
    }

    function showMonthlyValuationParams() 
    {
        sendTransactionAction("DailyDetailTran", "showMonthlyValuationParams", "main");
    }

    function showPolicyPagesParams() 
    {
        sendTransactionAction("DailyDetailTran", "showPolicyPagesParams", "main");
    }

    function showAnnualStatementsParams() 
    {
        sendTransactionAction("DailyDetailTran", "showAnnualStatementsParams", "main");
    }


    function showListBillParams()
    {
        sendTransactionAction("DailyDetailTran", "showListBillParams", "main");
    }

    function showClientAccountingExtractParams()
    {
        sendTransactionAction("DailyDetailTran", "showClientAccountingExtractParams", "main");
    }

    function showCheckNumberImportParams()
    {
        sendTransactionAction("DailyDetailTran", "showCheckNumberImportParams", "main");
    }

</script>
</head>

<body  bgColor="#99BBBB"  onLoad="init()">

<span style="border-style:solid; border-width:1;  position:relative; width:100%; height:100%; top:0; left:0; z-index:0; overflow:visible">
<form name="dailyform" method="post" action="/PORTAL/servlet/RequestManager">
<table width="100%" height="100%" border="0" height="296">
  <tr align="center" valign="middle">
    <td height="294" width="94%" align="top" valign="middle" class="unnamed1">
      <table width="100%" height="100%" border="0" align="center"  bgcolor="#DDDDDD">
        <tr>
          <td>&nbsp;</td>
        </tr>
        <tr>
            <td align="middle"><b><font face="Arial, Helvetica, sans-serif" size="4">
                 <a href="javascript:importUnitValues()">Import Unit Values</a>
	      </td>
        </tr>
        <tr>
            <td align="middle"><b><font face="Arial, Helvetica, sans-serif" size="4">
                 <a href="javascript:showBatch()">Batch</a>
	      </td>
        </tr>
        <tr>
            <td align="middle"><b><font face="Arial, Helvetica, sans-serif" size="4">
            <a href="javascript:showBank()">Bank</a>
	      </td>
        </tr>
        <tr>
            <td align="middle"><b><font face="Arial, Helvetica, sans-serif" size="4">
            <a href="javascript:showBankForNACHA()">Bank EFT(NACHA)</a>
	      </td>
        </tr>
        <tr>
          <td align="middle"><b><font face="Arial, Helvetica, sans-serif" size="4">
            <a href="javascript:showAccounting()">Accounting</a>
	      </td>
        </tr>
        <tr>
          <td align="middle"><b><font face="Arial, Helvetica, sans-serif" size="4">
            <a href="javascript:showReports()">Reports</a>
	      </td>
        </tr>
        <tr>
          <td align="middle"><b><font face="Arial, Helvetica, sans-serif" size="4">
            <a href="javascript:showCorrespondence()">Correspondence</a>
	      </td>
        </tr>
        <tr>
          <td align="middle"><b><font face="Arial, Helvetica, sans-serif" size="4">
            <a href="javascript:showEquityIndexHedge()">Equity Index Hedge</a>
	      </td>
        </tr>
        <tr>
          <td align="middle"><b><font face="Arial, Helvetica, sans-serif" size="4">
            <a href="javascript:showGAAPPremiumExtract()">Premium Extract - Reserves File</a>
	      </td>
        </tr>
        <tr>
          <td align="middle"><b><font face="Arial, Helvetica, sans-serif" size="4">
            <a href="javascript:showACTFileParams()">Controls And Balances Interface</a>
	      </td>
        </tr>


        <tr>
          <td align="middle"><b><font face="Arial, Helvetica, sans-serif" size="4">
            <a href="javascript:showPRDParams()">PRD Extract</a>
	      </td>
        </tr>

        <tr>
          <td align="middle"><b><font face="Arial, Helvetica, sans-serif" size="4">
            <a href="javascript:showPRDCompareParams()">PRD Compare</a>
	      </td>
        </tr>

        <tr>
          <td align="middle"><b><font face="Arial, Helvetica, sans-serif" size="4">
            <a href="javascript:showListBillParams()">Bill</a>
	      </td>
        </tr>
        <tr>
          <td align="middle"><b><font face="Arial, Helvetica, sans-serif" size="4">
            <a href="javascript:showClientAccountingExtractParams()">Client Accounting Extract</a>
	      </td>
        </tr>
        <tr>
          <td align="middle"><b><font face="Arial, Helvetica, sans-serif" size="4">
            <a href="javascript:showCheckNumberImportParams()">Check Number Import</a>
	      </td>
        </tr>
        
        <tr>
          <td align="middle"><b><font face="Arial, Helvetica, sans-serif" size="4">
            <a href="javascript:showPolicyPagesParams()">UL Policy Pages</a>
	      </td>
        </tr>
        <tr>
          <td align="middle"><b><font face="Arial, Helvetica, sans-serif" size="4">
            <a href="javascript:showMonthlyValuationParams()">UL Month End Valuation</a>
	      </td>
        </tr>
        <tr>
          <td align="middle"><b><font face="Arial, Helvetica, sans-serif" size="4">
            <a href="javascript:showAnnualStatementsParams()">UL Annual Statements</a>
	      </td>
        </tr>

        <tr>
            <td height="50%">
                &nbsp;
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
</span>
</body>
</html>
