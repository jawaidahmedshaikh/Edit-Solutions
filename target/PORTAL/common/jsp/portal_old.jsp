<%@ page isELIgnored="false" %>
<!--
 * User: unknown
 * Date: Jan 1, 2000
 * Time: unknown
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 -->
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page import="edit.portal.common.session.UserSession,
                 edit.common.EDITDate,
                 edit.portal.menu.MenuCreator,
                 java.util.Date,
                 java.io.InputStream"%>
<spring:eval var="venusRevision" expression="@systemProperties['venus.revision']" />
<spring:eval var="venusBranch" expression="@systemProperties['venus.branch']" />
<%
    UserSession userSession = (UserSession) request.getSession().getAttribute("userSession");

    String version = System.getProperty("MajorVersion");
    String build = System.getProperty("Build");
    String builtFromBranch = System.getProperty("BuiltFromBranch");

    String versionInfo = "Version " + version + " Build " + build;
    
    EDITDate currentDate = new EDITDate();

    boolean isLoggedIn = isLoggedIn(userSession);

    InputStream inputStream = getServletConfig().getServletContext().getResourceAsStream("/common/navigation/mainmenu.js");
%>

<%!
    private boolean isLoggedIn(UserSession userSession) throws ClassNotFoundException
    {
        boolean isLoggedIn = false;

        if (MenuCreator.isCompiledWithSecurity())
        {
            if (userSession != null && !userSession.getUsername().equalsIgnoreCase("SEGDev"))
            {
                isLoggedIn = true;
            }
        }
        else
        {
            isLoggedIn = true;
        }

        return isLoggedIn;
    }
%>

<html>
<head>
<title>Welcome to the EDITSOLUTIONS Portal</title>

<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">

<!-- following comment by syam lingamallu -->
<!-- intentionally removed the css file mapping... causing some syle confilcts with menu display -->
<style type="text/css">
body.mainTheme
{
    background: #99BBBB;
    height: 100%;
    margin: 0;
    padding: 0;
}
span.copyright
{
    font:bold 8pt verdana,arial,sans-serif;color="white";
}
span.versioninfo
{
    font:normal 12px verdana,arial,sans-serif;color="black";
}
</style>
<link href="/theme/css/environment.css" rel="stylesheet" type="text/css">

<script type="text/javascript">

var isLoggedIn = <%= isLoggedIn %>;

function showIndividuals(){

    sendTransactionAction("PortalLoginTran", "showIndividuals", "_self");
}

function showReports(){

    sendTransactionAction("PortalLoginTran", "showReports", "_self");
}

function showReportsAdmin(){

    sendTransactionAction("PortalLoginTran", "showReportsAdmin", "_self");
}

function showQuote(){

    sendTransactionAction("PortalLoginTran", "showQuote", "_self");
}

function showContract(){

    sendTransactionAction("PortalLoginTran", "showContract", "_self");
}

function showBatch(){

    sendTransactionAction("PortalLoginTran", "showDailySelection", "_self");
}

function showProductProfessionals(){

    sendTransactionAction("PortalLoginTran", "showProductProfessionals", "_self");
}

function showAccounting(){

    sendTransactionAction("PortalLoginTran", "showAccounting", "_self");
}

function showEditing(){

    sendTransactionAction("PortalLoginTran", "showEditing", "_self");
}

function showSecurityAdmin(){

    sendTransactionAction("PortalLoginTran", "showSecurityAdmin", "_self");
}

function showTransformAdmin(){

    sendTransactionAction("PortalLoginTran", "showTransformAdmin", "_self");
}

function showRoleAdmin()
{
    sendTransactionAction("PortalLoginTran", "showRoleAdmin", "_self");
}

function showQueryAdmin()
{
    sendTransactionAction("PortalLoginTran", "showQueryAdmin", "_self");
}

function showCommissionContract() {

    sendTransactionAction("PortalLoginTran", "showCommissionContract", "_self");
}

function showAgentDetail() {

    sendTransactionAction("PortalLoginTran", "showAgentDetail", "_self");
}

function showAgentHierarchy() {

    sendTransactionAction("PortalLoginTran", "showAgentHierarchy", "_self");
}

function showRequirementTable() {

    sendTransactionAction("PortalLoginTran", "showRequirementTable", "_self");
}

function showTransactionTable()
{
    sendTransactionAction("PortalLoginTran", "showTransactionTable", "_self");
}

function showLoggingAdmin()
{
    sendTransactionAction("PortalLoginTran", "showLoggingAdmin", "_self");
}

function showLoggingSetup()
{
    sendTransactionAction("PortalLoginTran", "showLoggingSetup", "_self");
}

function showLoggingResults()
{
    sendTransactionAction("PortalLoginTran", "showLoggingResults", "_self");
}

function showJournalAdjustment()
{
    sendTransactionAction("PortalLoginTran", "showJournalAdjustment", "_self");
}

function showCashBatch()
{
    sendTransactionAction("PortalLoginTran", "showCashBatch", "_self");
}

function showCodeTableDefSummary()
{
    sendTransactionAction("PortalLoginTran", "showCodeTableDefSummary", "_self");
}

function showOnlineReportSummary()
{
    sendTransactionAction("PortalLoginTran", "showOnlineReportSummary", "_self");
}

/**
* Shows the main page for the reinsurance detail.
*/
function showReinsurerInformation()
{
    sendTransactionAction("PortalLoginTran", "showReinsurerDetails", "_self");
}

/**
 * Shows the Treaty Relationships page.
 */
function showTreatyRelations()
{
    sendTransactionAction("PortalLoginTran", "showTreatyRelations", "_self");
}

/**
 * Shows the Contract Treaty Relationships page.
 */
function showContractTreatyRelations()
{
    sendTransactionAction("PortalLoginTran", "showContractTreatyRelations", "_self");
}

/**
 * Shows the Import New Business page.
 */
function showImportNewBusiness()
{
    sendTransactionAction("PortalLoginTran", "showImportNewBusiness", "_self");
}

/**
 * Shows the Business Calendar page.
 */
function showBusinessCalendar()
{
    sendTransactionAction("PortalLoginTran", "showBusinessCalendar", "_self");
}

/**
 * Shows the Agent Bonus Program page.
 */
function showAgentBonusProgram()
{
    sendTransactionAction("PortalLoginTran", "showAgentBonusProgram", "_self");
}

/**
 * Shows the page from moving one Agent/Situation to another Agent/Situation.
 */
function showAgentMove()
{
   sendTransactionAction("PortalLoginTran", "showAgentMove", "_self");
}

/**
 * Shows the Casetracking main page.
 */
 function showCasetracking()
{
    f.pageToShow.value = "casetrackingClient";

    sendTransactionAction("PortalLoginTran", "showCasetracking", "_self");
}

/**
 * Shows the Case detail main page.
 */
 function showCase()
{
    sendTransactionAction("PortalLoginTran", "showCase", "_self");
}

/**
 * Shows the ScriptExport page.
 */
 function showExportSelection()
{
    sendTransactionAction("PortalLoginTran", "showExportSelection", "_self");
}

/**
 * Show main logging page.
 */
function showLogging() {

    sendTransactionAction("PortalLoginTran", "showLogging", "_self");
}

function showBatchContract()
{
    sendTransactionAction("PortalLoginTran", "showBatchContract", "_self");
    //window.location = "/PORTAL/flex/BatchContractApplication.jsp";
}

function showPRDSystem() {
    window.location = "/PORTAL/PRDApplication-debug/PRDApplication.html";
}

function showBilling()
{
    sendTransactionAction("PortalLoginTran", "showBilling", "_self");
    //window.location = "/PORTAL/flex/BillingApplication.jsp";
}

function showConversion()
{
    sendTransactionAction("PortalLoginTran", "showConversion", "_self");
    //window.location = "/PORTAL/flex/ConversionApplication.jsp";
}

function showQuery()
{
    sendTransactionAction("PortalLoginTran", "showQuery", "_self");
}

function sendTransactionAction(transaction, action, target){

    // f.password.value = str_sha1(f.password.value);

    f.transaction.value = transaction;
    f.action.value      = action;
    f.target            = target;

    f.submit();
}

function init()
{
    f = document.toolkitForm;

    if (isLoggedIn == false)
    {
        sendTransactionAction("SecurityAdminTran", "showLoginPage", "_self");
    }
}
</script>
</head>
<body class="mainTheme" onLoad="init()">

<form name="toolkitForm" method="post" action="/PORTAL/servlet/RequestManager">
    <table cellspacing="2" cellpadding="3" border="0" width="100%" height="100%">
        <tr align="center" valign="middle">
            <td valign="bottom">
                <img src="/PORTAL/common/images/Vision_Logo.JPG" 
                     alt="Venus System" height="191" width="199"/>
            </td>
        </tr>
        <tr align="center" valign="middle">
            <td valign="middle">
                <span class="versioninfo">
                    <%= new Date() %>
                    <br>
                    <%= versionInfo %>
                    <br>

                    <%
                        if (builtFromBranch != null)
                        {
                    %>
                            Built From <%= builtFromBranch %>
                    <%
                        }
                    %>
                </span>
            </td>
        </tr>
        <tr align="center" valign="middle">
            <td valign="middle">
                <span>
                    Venus Build Properties
                    <br>
                    revision: ${venusRevision} | branch: ${venusBranch}
                </span>
            </td>
        </tr>
        <tr align="center" valign="middle">
            <td>
                <script type="text/javascript" src="/PORTAL/common/navigation/xaramenu.js"></script>

                <script type="text/javascript">

                    var loc = "/PORTAL/common/navigation/";

                    var bd=0
                    document.write("<style type=\"text/css\">");
                    document.write("\n<!--\n");
                    document.write(".mainmenu_menu {z-index:999;border-color:#000000;border-style:solid;border-width:"+bd+"px 0px "+bd+"px 0px;background-color:#99bbbb;position:absolute;left:0px;top:0px;visibility:hidden;}");
                    document.write(".mainmenu_plain, a.mainmenu_plain:link, a.mainmenu_plain:visited{text-align:left;background-color:#99bbbb;color:#000000;text-decoration:none;border-color:#000000;border-style:solid;border-width:0px "+bd+"px 0px "+bd+"px;padding:2px 0px 2px 0px;cursor:hand;display:block;font-size:14pt;font-family:Georgia, Times New Roman, Times, serif;}");
                    document.write("a.mainmenu_plain:hover, a.mainmenu_plain:active{background-color:#d6e3e3;color:#000000;text-decoration:none;border-color:#000000;border-style:solid;border-width:0px "+bd+"px 0px "+bd+"px;padding:2px 0px 2px 0px;cursor:hand;display:block;font-size:14pt;font-family:Georgia, Times New Roman, Times, serif;}");
                    document.write("a.mainmenu_l:link, a.mainmenu_l:visited{text-align:left;background:#99bbbb url("+loc+"mainmenu_l.gif) no-repeat right;color:#000000;text-decoration:none;border-color:#000000;border-style:solid;border-width:0px "+bd+"px 0px "+bd+"px;padding:2px 0px 2px 0px;cursor:hand;display:block;font-size:14pt;font-family:Georgia, Times New Roman, Times, serif;}");
                    document.write("a.mainmenu_l:hover, a.mainmenu_l:active{background:#d6e3e3 url("+loc+"mainmenu_l.gif) no-repeat right;color: #000000;text-decoration:none;border-color:#000000;border-style:solid;border-width:0px "+bd+"px 0px "+bd+"px;padding:2px 0px 2px 0px;cursor:hand;display:block;font-size:14pt;font-family:Georgia, Times New Roman, Times, serif;}");
                    document.write("\n-->\n");
                    document.write("</style>");

                    var fc=0x000000;
                    var bc=0xd6e3e3;
                    if(typeof(frames)=="undefined"){var frames=0;}

                    <%
                        try
                        {
                            if (isLoggedIn)
                            {
                                MenuCreator menuCreator = new MenuCreator(request, inputStream);

                                String[] menuLines = menuCreator.displayMenu();

                                for (int i = 0; i < menuLines.length; i++)
                                {
                                    out.println(menuLines[i]);
                                }
                            }
                        }
                        catch (Throwable t)
                        {
                            System.out.println(t);

                            t.printStackTrace();
                        }
                    %>

                    loc="";

                </script>
            </td>
        </tr>
        <tr>
            <td align="center" valign="bottom" >
                <span class="copyright">Copyright 2000-<%= currentDate.getFormattedYear()%>; Systems Engineering Group, LLC. All Rights Reserved.</span>
            </td>
        </tr>
    </table>

<input type="hidden" name="transaction">
<input type="hidden" name="action">
<input type="hidden" name="pageToShow">

</form>

</body>
</html>
