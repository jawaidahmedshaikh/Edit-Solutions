<!--
 * User: unknown
 * Date: Jan 1, 2000
 * Time: unknown
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 -->
<%@ page import="edit.common.vo.*,
                 edit.common.vo.user.*,
                 java.util.*,
                 edit.common.EDITDate,
                 fission.utility.*,
                 engine.*"%>
<%@ page  errorPage="/common/jsp/error.jsp" autoFlush="true" %>


<%
	// The page that control is returned to if the Cancel button
	// is clicked.
	String returnPage = request.getParameter("returnPage");

    String message = (String) request.getAttribute("message");
    message = (message == null)?"":message;

    ProductStructureVO[] productStructureVOs = (ProductStructureVO[]) request.getAttribute("productStructureVOs");

    UIRulesVO[] uiRulesVOs = (UIRulesVO[]) request.getAttribute("uiRulesVOs");

    List attachedRulesPKs = (List) request.getAttribute("attachedRulesPKs");

    List repeatingRulePKs = (List) request.getAttribute("repeatingRulePKs");

    String productStructurePK = (String) request.getAttribute("productStructurePK");
%>

<html>
<head>

<script language="Javascript1.2">

var rule = null;
var company = null;
var message = "<%= message %>";

function highlightRow() {

    var tdElement = window.event.srcElement;
    var currentRow = tdElement.parentElement;

    if (currentRow.tagName != "TR") // Bubble up if necessary
    {
        currentRow = currentRow.parentElement;
    }

    currentRow.style.backgroundColor = "#FFFFCC";
}

function unhighlightRow(){

    var tdElement = window.event.srcElement;
    var currentRow = tdElement.parentElement;

    if (currentRow.tagName != "TR") // Bubble up if necessary
    {
        currentRow = currentRow.parentElement;
    }

    var className = currentRow.className;

    if (currentRow.isSelected != "true") {

        if (className == "assocEntry")
        {
            currentRow.style.backgroundColor = "#00BB00";
        }
        else if (className == "highLighted")
        {
            currentRow.style.backgroundColor = "#FFFFCC";
        }
        else {

            currentRow.style.backgroundColor = "#BBBBBB";
        }
    }
}

function selectDeselectRow() {

    var tdElement = window.event.srcElement;
    var currentRow = tdElement.parentElement;

    if (currentRow.tagName != "TR") // Bubble up if necessary
    {
        currentRow = currentRow.parentElement;
    }

    var className = currentRow.className;

    if (currentRow.isSelected == "false"){

        currentRow.style.backgroundColor = "#FFFFCC";
        currentRow.isSelected = "true";
    }
    else {

        if (className == "mainEntry"){

            currentRow.style.backgroundColor = "#BBBBBB";
        }
        else if (className == "assocEntry"){

            currentRow.style.backgroundColor = "#00BB00";
        }

        currentRow.isSelected = "false";
    }
}

function showBuildRuleDialog()
{
    if (f.productStructurePK.value == "0")
    {
        alert("Please Select Company Structure For Build")
    }
    else
    {
        var width   = 1.00 * screen.width;
        var height  = 0.90 * screen.height;

        openDialog("","buildRuleDialog","left=0,top=0,resizable=yes,scrollbars=yes,width=" + width + ",height=" + height,"ProductRuleStructureTran", "showBuildRuleDialog", "buildRuleDialog");
    }
}

function openDialog(theURL, winName, features, transaction, action, target) {

    dialog = window.open(theURL,winName,features);

    sendTransactionAction(transaction, action, target);
}

function cancelRelation()
{
    f.productStructurePK.value = "";

    sendTransactionAction("ProductRuleStructureTran","cancelRelation", "main");
}

function showRelation()
{
    var tdElement = window.event.srcElement;
    var currentRow = tdElement.parentElement;

    f.productStructurePK.value = currentRow.productStructurePK;

    sendTransactionAction("ProductRuleStructureTran", "showRelation", "main");
}

function sendTransactionAction(transaction, action, target) {

	f.transaction.value = transaction;
	f.action.value = action;
	f.target = target;
	f.submit();
}

function showRulesSummary()
{
    sendTransactionAction("ProductRuleStructureTran", "showRuleSummary", "main");
}

function init() {

	f = document.relationForm;

    if (message.length > 0){

        alert(message);
    }

    var scrollToRow = document.getElementById("<%= productStructurePK %>");

    if (scrollToRow != null) {

        scrollToRow.scrollIntoView(false);
    }
}

</script>
<title>Rules Relation</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta http-equiv="Cache-Control" content="no-store">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Expires" content="0">

<link href="/PORTAL/common/css/PORTAL.css" rel="stylesheet" type="text/css">

</head>

<body class="mainTheme" onLoad="init()">


<form name="relationForm">

    <span class="tableHeading">Company Structure Summary</span><br>
    <span class="contentArea" style="border-style:solid; border-width:1; position:relative; width:100%; height:35%; top:0; left:0; z-index:0; overflow:visible">


        <table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">

            <tr>

                <th class="dataHeading4" width="20%">
                    Company
                </th>
                <th class="dataHeading3" width="20%">
                    Marketing Package
                </th>
                <th class="dataHeading3" width="20%">
                    Group Product
                </th>
                <th class="dataHeading3" width="20%">
                    Area
                </th>
                <th class="dataHeading3" width="20%">
                    Business Contract
                </th>

            </tr>

            <tr height="100%">

                <td colspan="5">

                    <span class="summaryTable" style="border-style:solid; border-width:1; position:relative; width:100%; height:100%; top:0; left:0; z-index:0; overflow:scroll">

                        <table id="companyStructuresTable" width="100%" border="0" cellspacing="0" cellpadding="0" >
<%
                            if (productStructureVOs != null){

                                for (int i = 0; i < productStructureVOs.length; i++){

                                    String currentProductStructurePK = productStructureVOs[i].getProductStructurePK() + "";
                                    Company company = Company.findByPK(new Long(productStructureVOs[i].getCompanyFK()));

                                    String currentCompany = company.getCompanyName();
                                    String currentMarketingPackage = productStructureVOs[i].getMarketingPackageName();
                                    String currentGroupProduct = productStructureVOs[i].getGroupProductName();
                                    String currentArea = productStructureVOs[i].getAreaName();
                                    String currentBusinessContract = productStructureVOs[i].getBusinessContractName();

                                    String className = null;

                                    if (productStructurePK.equals(currentProductStructurePK))
                                    {
                                        className = "highLighted";
                                    }
                                    else{

                                        className = "mainEntry";
                                    }
%>
                                    <tr height="15" class="<%= className %>" id="<%= currentProductStructurePK %>" productStructurePK="<%= currentProductStructurePK %>"  isSelected="false" onMouseOver="highlightRow()" onMouseOut="unhighlightRow()" onClick="showRelation()">

                                        <td width="20%">
                                            <%= currentCompany %>
                                        </td>
                                        <td width="20%">
                                            <%= currentMarketingPackage %>
                                        </td>
                                        <td width="20%">
                                            <%= currentGroupProduct %>
                                        </td>
                                        <td width="20%">
                                            <%= currentArea %>
                                        </td>
                                        <td width="20%">
                                            <%= currentBusinessContract %>
                                        </td>
                                    </tr>
<%
                                }
                            }
%>
                        </table>

                    </span>
                </td>

            </tr>
        </table>

        </span>


        <br><br>
    <table width="100%">

        <tr>
            <td width="33%">
                &nbsp;
            </td>
             <td align="center" valign="bottom" width="33%">
                <span class="tableHeading">Rule Structure Summary</span>
             </td>
            <td align="right" valign="bottom" width="33%">
                <input type="button" value="Rules Summary" onClick="showRulesSummary()">
            </td>
        </tr>
    </table>

    <span class="contentArea" style="border-style:solid; border-width:1; position:relative; width:100%; height:50%; top:0; left:0; z-index:0; overflow:visible">

        <table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">

            <tr>

                <th class="dataHeading3" width="2%">
                    &nbsp;
                </th>
                <th class="dataHeading3" width="14%">
                    Effective Date
                </th>
                <th class="dataHeading3" width="14%">
                    Process
                </th>
                <th class="dataHeading3" width="14%">
                    Event
                </th>
                <th class="dataHeading3" width="14%">
                    Event Type
                </th>
                <th class="dataHeading3" width="14%">
                    Rule Name
                </th>
                <th class="dataHeading3" width="14%">
                    Script/Table Name
                </th>

            </tr>

            <tr height="100%">

                <td colspan="8">

                    <span class="summaryTable" style="border-style:solid; border-width:1; position:relative; width:100%; height:100%; top:0; left:0; z-index:0; overflow:scroll">

                        <table id="rulesTable" width="100%" border="0" cellspacing="0" cellpadding="0" >
<%
                            if (uiRulesVOs != null){

                                Arrays.sort(uiRulesVOs);

                                for (int i = 0; i < uiRulesVOs.length; i++){

                                    String currentRulesPK = uiRulesVOs[i].getRulesVO().getRulesPK() + "";
                                    String currentEffectiveDate = DateTimeUtil.formatEDITDateAsMMDDYYYY(new EDITDate(uiRulesVOs[i].getRulesVO().getEffectiveDate()));
                                    String currentProcess = uiRulesVOs[i].getRulesVO().getProcessName();
                                    String currentEvent = uiRulesVOs[i].getRulesVO().getEventName();
                                    String currentEventType = uiRulesVOs[i].getRulesVO().getEventTypeName();
                                    String currentSubRule = uiRulesVOs[i].getRulesVO().getSubRuleName();
                                    String currentRule = uiRulesVOs[i].getRulesVO().getRuleName();
                                    String scriptOrTableName = null;

                                    if (uiRulesVOs[i].getRulesVO().getTableDefFK() == 0)
                                    {
                                        scriptOrTableName = uiRulesVOs[i].getScriptVO().getScriptName();
                                    }
                                    else
                                    {
                                        scriptOrTableName = uiRulesVOs[i].getTableDefVO().getTableName();
                                    }

                                    String className = null;

                                    if (attachedRulesPKs.contains(new Long(currentRulesPK)))
                                    {
                                        className = "assocEntry";
                                    }
                                    else{

                                        className = "mainEntry";
                                    }
%>
                                    <tr height="15" class="<%= className %>" rulesPK="<%= currentRulesPK %>">

                                        <td width="2%">
                                        <%
                                            if (repeatingRulePKs.contains(new Long(currentRulesPK)))
                                            {
                                                out.println("<img src=\"/PORTAL/engine/images/repeating_b1.gif\" width=\"28\" height=\"15\" alt=\"Repeating Rule Name\" onMouseOver=\"this.src='/PORTAL/engine/images/repeating_b1_over.gif'\" onMouseOut=\"this.src='/PORTAL/engine/images/repeating_b1.gif'\">");
                                            }
                                            else
                                            {
                                                out.println("&nbsp");
                                            }
                                        %>
                                        </td>
                                        <td width="14%">
                                            <%= currentEffectiveDate %>
                                        </td>
                                        <td width="14%">
                                            <%= currentProcess %>
                                        </td>
                                        <td width="14%">
                                            <%= currentEvent %>
                                        </td>
                                        <td width="14%">
                                            <%= currentEventType %>
                                        </td>
                                        <td width="14%">
                                            <%= currentRule %>
                                        </td>
                                        <td width="14%">
                                            <%= scriptOrTableName %>
                                        </td>
                                    </tr>
<%
                                }
                            }
%>
                        </table>

                    </span>

                </td>
            </tr>

        </table>

    </span>

    <table width="100%">

        <tr>
             <td align="left" valign="bottom">
                <img src="/PORTAL/engine/images/repeating_b1.gif" width="28" height="15" alt="Repeating Rule Name"> = Repeating Rule Name
             </td>

            <td align="right" valign="bottom">
                <input type="button" name="build" value="Build" onClick="showBuildRuleDialog()">
                <input type="button" name="cancel" value="Cancel" onClick="cancelRelation()">
            </td>
        </tr>
    </table>


 <input type="hidden" name="transaction">
 <input type="hidden" name="action">
 <input type="hidden" name="productStructurePK" value="<%= productStructurePK %>">
 <input type="hidden" name="selectedRulePKs" value="">


<%-- <input type="hidden" name="ruleId" value="">--%>
<%-- <input type="hidden" name="companyStructureId" value="">--%>
<%-- <input type="hidden" name="returnPage" value="<%= returnPage %>">--%>

</form>
</body>
</html>