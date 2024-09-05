<%@ page  errorPage="/common/jsp/error.jsp" autoFlush="true" %>
<%@ page import="java.util.*, fission.beans.*,
                 fission.utility.*" %>

<%
String responseMessage  = Util.initString((String) request.getAttribute("responseMessage"), "");

%>

<html>
<head>
<title>Untitled Document</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<script language="JavaScript">

   	var f = null;
    var responseMessage = "<%= responseMessage %>";

	function init() {

		f = document.reportingform;

        if (responseMessage != "") {

            alert(responseMessage);
        }
    }

    function showReserves() {

        sendTransactionAction("ReportingDetailTran", "showReserves");

    }
    function showTemplateEditor(){

        sendTransactionAction("ReportingDetailTran", "showTemplateEditor");
    }

    function showYETaxReporting(){

        sendTransactionAction("ReportingDetailTran", "showYETaxReporting");
    }

    function runValuation(){

        sendTransactionAction("ReportingDetailTran", "showValuationParamsSelectionPage");
    }

    function showAgentUpdate()
    {
        sendTransactionAction("ReportingDetailTran", "showAgentUpdate");
    }

    function showBonusCommissions()
    {
        sendTransactionAction("ReportingDetailTran", "showBonusCommissions");
    }

    function showCommissionChecks()
    {
        sendTransactionAction("ReportingDetailTran", "showCommissionChecks");
    }

    function showCommissionEFT()
    {
        sendTransactionAction("ReportingDetailTran", "showCommissionEFT");
    }

    function showProcessChecks()
    {
        sendTransactionAction("ReportingDetailTran", "showProcessChecks");
    }

    function showCommissionStatements()
    {
        sendTransactionAction("ReportingDetailTran", "showCommissionStatements");
    }

    /**
     * Renders the kick-off screen for Bonus Commssion Statements.
     */
    function showBonusCommissionStatements()
    {
        sendTransactionAction("ReportingDetailTran", "showBonusCommissionStatements");
    }

    function showYearEndClientBalance()
    {
        sendTransactionAction("ReportingDetailTran", "showYearEndClientBalance");
    }

    function showCloseAccounting()
    {
        sendTransactionAction("ReportingDetailTran", "showCloseAccounting");
    }

    function showAccountingExtractParams()
    {
        sendTransactionAction("ReportingDetailTran", "showAccountingExtractParams");
    }

    function scrubAllClients()
    {
        sendTransactionAction("ClientDetailTran", "checkOFACForAllClients");
    }

    /**
     * Returns the screen that kicks-off this reinsurance process.
     */
    function showUpdateReisuranceBalances()
    {
        sendTransactionAction("ReinsuranceTran", "showUpdateReinsuranceBalances");
    }

    /**
     * Returns the screen that kicks-off this reinsurance process.
     */
    function showCreateReinsuranceCheckTransactions()
    {
        sendTransactionAction("ReinsuranceTran", "showReinsuranceCheckTransactions");
    }

    function showRmdNotifications()
    {
        sendTransactionAction("ReportingDetailTran", "showRmdNotifications");
    }

    function showCoiReplenishment()
    {
        sendTransactionAction("ReportingDetailTran", "showCoiReplenishment");
    }

    /**
     * Returns the page that kicks-off the staging process.
     */
    function showStageTables()
    {
        sendTransactionAction("ReportingDetailTran", "showStageTables");
    }

    /**
     * Returns the page that kicks-off the Agent Bonus Update process.
     */
    function showUpdateAgentBonuses()
    {
        sendTransactionAction("ReportingDetailTran", "showUpdateAgentBonuses");
    }

    /**
     * Returns the page that kicks-off the creation and execution of the Agent Bonus Checks.
     */
    function showRunAgentBonusChecks()
    {
        sendTransactionAction("ReportingDetailTran", "showRunAgentBonusChecks");
    }

    function showRunReinsuranceChecks()
    {
        sendTransactionAction("ReportingDetailTran", "showRunReinsuranceChecks");
    }

    function showRunAlphaExtract()
    {
        sendTransactionAction("ReportingDetailTran", "runAlphaExtract");
    }

    function showPendingReqExtractParam()
    {
        sendTransactionAction("ReportingDetailTran", "showPendingReqExtractParam");
    }

    function showCashBatchImport()
    {
        sendTransactionAction("ReportingDetailTran", "showCashBatchImport");
    }

    function showDataWarehouseParams()
    {
        sendTransactionAction("ReportingDetailTran", "showDataWarehouseParams");
    }

    function showManualAccountingImportParams()
    {
        sendTransactionAction("ReportingDetailTran", "showManualAccountingImportParams");
    }

    function runWorksheet()
    {
        sendTransactionAction("ReportingDetailTran", "showWorksheetParams");
    }


    function sendTransactionAction(tmpTransaction, tmpAction)
    {
        f.transaction.value = tmpTransaction;
        f.action.value      = tmpAction;
        f.submit();
    }

</script>
</head>

<body bgColor="#99BBBB" onLoad="init()">


<span style="border-style:solid; border-width:1;  position:relative; width:100%; height:100%; top:0; left:0; z-index:0; overflow:visible">
<form name="reportingform" method="post" action="/PORTAL/servlet/RequestManager">
<table width="100%" border="0" height="100%" bgColor="#DDDDDD">
  <tr>
    <td align="middle"><b><font face="Arial, Helvetica, sans-serif" size="4">
        <a href="javascript:showAgentUpdate()">Agent Update</a>
    </td>
  </tr>
  <tr>
    <td align="middle"><b><font face="Arial, Helvetica, sans-serif" size="4">
        <a href="javascript:showCommissionChecks()">Build Agent Check CK</a>
    </td>
  </tr>
  <tr>
    <td align="middle"><b><font face="Arial, Helvetica, sans-serif" size="4">
        <a href="javascript:showCommissionEFT()">Build Agent EFT CK</a>
    </td>
  </tr>
  <tr>
    <td align="middle"><b><font face="Arial, Helvetica, sans-serif" size="4">
        <a href="javascript:showProcessChecks()">Run Agent CK</a>
    </td>
  </tr>
  <tr>
    <td align="middle"><b><font face="Arial, Helvetica, sans-serif" size="4">
        <a href="javascript:showCommissionStatements()">Commission Statements</a>
    </td>
  </tr>
  <tr>
    <td align="middle"><b><font face="Arial, Helvetica, sans-serif" size="4">
        <a href="javascript:runValuation()">Valuation</a>
    </td>
  </tr>
  <tr>
    <td align="middle"><b><font face="Arial, Helvetica, sans-serif" size="4">
        <a href="javascript:showYETaxReporting()">Year-End Tax Reporting</a>
    </td>
  </tr>
  <tr>
    <td align="middle"><b><font face="Arial, Helvetica, sans-serif" size="4">
        <a href="javascript:showAccountingExtractParams()">Accounting Extract</a>
    </td>
  </tr>
  <tr>
    <td align="middle"><b><font face="Arial, Helvetica, sans-serif" size="4">
        <a href="javascript:showCloseAccounting()">Close Accounting</a>
    </td>
  </tr>
  <tr>
    <td align="middle"><b><font face="Arial, Helvetica, sans-serif" size="4">
        <a href="javascript:scrubAllClients()">OFAC-Scrub All Clients</a>
    </td>
  </tr>
  <tr>
    <td align="middle"><b><font face="Arial, Helvetica, sans-serif" size="4">
        <a href="javascript:showUpdateReisuranceBalances()">Reinsurance Update</a>
    </td>
  </tr>
  <tr>
    <td align="middle"><b><font face="Arial, Helvetica, sans-serif" size="4">
        <a href="javascript:showCreateReinsuranceCheckTransactions()">Build Reinsurance CK</a>
    </td>
  </tr>
  <tr>
    <td align="middle"><b><font face="Arial, Helvetica, sans-serif" size="4">
        <a href="javascript:showRmdNotifications()">RMD Notifications</a>
    </td>
  </tr>
  <tr>
    <td align="middle"><b><font face="Arial, Helvetica, sans-serif" size="4">
        <a href="javascript:showCoiReplenishment()">COI Replenishment</a>
    </td>
  </tr>
  <tr>
    <td align="middle"><b><font face="Arial, Helvetica, sans-serif" size="4">
        <a href="javascript:showStageTables()">Stage Tables</a>
    </td>
  </tr>
  <tr>
    <td align="middle"><b><font face="Arial, Helvetica, sans-serif" size="4">
        <a href="javascript:showUpdateAgentBonuses()">Update Agent Bonuses</a>
    </td>
  </tr>
  <tr>
    <td align="middle"><b><font face="Arial, Helvetica, sans-serif" size="4">
        <a href="javascript:showRunAgentBonusChecks()">Run Agent Bonus Checks</a>
    </td>
  </tr>
  <tr>
    <td align="middle"><b><font face="Arial, Helvetica, sans-serif" size="4">
        <a href="javascript:showBonusCommissionStatements()">Bonus Commission Statements</a>
    </td>
  </tr>
  <tr>
    <td align="middle"><b><font face="Arial, Helvetica, sans-serif" size="4">
        <a href="javascript:showRunReinsuranceChecks()">Run Reinsurance Checks</a>
    </td>
  </tr>
  <tr>
    <td align="middle"><b><font face="Arial, Helvetica, sans-serif" size="4">
        <a href="javascript:showRunAlphaExtract()">Alpha Export</a>
    </td>
  </tr>
  <tr>
    <td align="middle"><b><font face="Arial, Helvetica, sans-serif" size="4">
        <a href="javascript:showPendingReqExtractParam()">Pending Requirements Export</a>
    </td>
  </tr>
  <tr>
    <td align="middle"><b><font face="Arial, Helvetica, sans-serif" size="4">
        <a href="javascript:showCashBatchImport()">Cash Batch Import</a>
    </td>
  </tr>
  <tr>
    <td align="middle"><b><font face="Arial, Helvetica, sans-serif" size="4">
        <a href="javascript:showManualAccountingImportParams()">Manual Accounting Import</a>
    </td>
  </tr>
  <tr>
    <td align="middle"><b><font face="Arial, Helvetica, sans-serif" size="4">
        <a href="javascript:showDataWarehouseParams()">Data Warehouse</a>
    </td>
  </tr>
  <tr>
    <td align="middle"><b><font face="Arial, Helvetica, sans-serif" size="4">
        <a href="javascript:runWorksheet()">Worksheet</a>
    </td>
  </tr>
  <tr>
    <td height="70%">
        &nbsp;
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
