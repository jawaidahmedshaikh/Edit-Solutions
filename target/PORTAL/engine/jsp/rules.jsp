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
                 fission.utility.*,
                 edit.common.EDITDate"%>

<%@ page  errorPage="/common/jsp/error.jsp" autoFlush="true" %>


<%
    // Scripts and Tables for summary areas
    ScriptVO[] scriptVOs = (ScriptVO[]) request.getAttribute("scriptVOs");
    TableDefVO[] tableDefVOs = (TableDefVO[]) request.getAttribute("tableDefVOs");

    // Selected Rule
    RulesVO rulesVO = (RulesVO) request.getAttribute("rulesVO");
    String effectiveDay = null;
    String effectiveMonth = null;
    String effectiveYear = null;
    String processName = null;
    String eventName = null;
    String eventTypeName = null;
    String ruleName = null;
    String rulesPK = null;
    String scriptPK = null;
    String tableDefPK = null;
    String keySourceScriptPK = null;
    String description = null;
    String operator = null;
    String maintDateTime = null;
    if (rulesVO != null)
    {
        if (rulesVO.getEffectiveDate() != null)
        {
            EDITDate effectiveDate = new EDITDate(rulesVO.getEffectiveDate());
            effectiveDay = effectiveDate.getFormattedDay();
            effectiveMonth = effectiveDate.getFormattedMonth();
            effectiveYear = effectiveDate.getFormattedYear();
        }

        processName = rulesVO.getProcessName();
        eventName = rulesVO.getEventName();
        eventTypeName = rulesVO.getEventTypeName();
        ruleName = rulesVO.getRuleName();
        rulesPK = rulesVO.getRulesPK() + "";
        description = rulesVO.getDescription();
        operator = rulesVO.getOperator();
        maintDateTime = rulesVO.getMaintDateTime();

        if (rulesVO.getTableDefFK() != 0)
        {
            keySourceScriptPK = rulesVO.getScriptFK() + "";
            scriptPK = "0";
        }
        else
        {
            scriptPK = rulesVO.getScriptFK() + "";
            keySourceScriptPK = "0";
        }

        tableDefPK = rulesVO.getTableDefFK() + "";
    }
    effectiveDay = Util.initString(effectiveDay, "");
    effectiveMonth = Util.initString(effectiveMonth, "");
    effectiveYear = Util.initString(effectiveYear, "");
    processName = Util.initString(processName, "");
    eventName = Util.initString(eventName, "");
    eventTypeName = Util.initString(eventTypeName, "");
    ruleName = Util.initString(ruleName, "");
    rulesPK = Util.initString(rulesPK, "");
    scriptPK = Util.initString(scriptPK, "");
    tableDefPK = Util.initString(tableDefPK, "");
    keySourceScriptPK = Util.initString(keySourceScriptPK, "");
    description = Util.initString(description, "");
    operator = Util.initString(operator, "");
    maintDateTime = Util.initString(maintDateTime, "");

    // RuleNameVO (for drop downs)
    RuleNameVO ruleNameVO = (RuleNameVO) request.getAttribute("ruleNameVO");
    String[] processNames = null;
    String[] eventNames = null;
    String[] eventTypeNames = null;
    String[] ruleNames = null;
    if (ruleNameVO != null)
    {
        processNames = ruleNameVO.getProcessName();
        eventNames = ruleNameVO.getEventName();
        eventTypeNames = ruleNameVO.getEventTypeName();
        ruleNames = ruleNameVO.getRuleName();
    }

    UIRulesVO[] uiRulesVOs = (UIRulesVO[]) request.getAttribute("uiRulesVOs");

    List attachedRulesPKs = (List) request.getAttribute("attachedRulesPKs");

    String message = (String) request.getAttribute("message");
    message = Util.initString(message, "");

    String pageMode = (String) request.getAttribute("pageMode");
    pageMode = Util.initString(pageMode, "VIEW");
%>

<html>
<head>

<script language="Javascript1.2">

var f = null;

var message = "<%= message %>";

var pageMode = "<%= pageMode %>";

function deleteRule()
{
    sendTransactionAction("ProductRuleStructureTran", "deleteRule", "main");
}

function addRule()
{
    sendTransactionAction("ProductRuleStructureTran", "addRule", "main");
}

function checkForNew()
{
    var select = window.event.srcElement;

    var selectName = select.name;
    var rootSelectName = selectName.substring(0, selectName.length - 6);
    var textField = document.getElementById(rootSelectName);

    if (select.selectedIndex == 0) // If "Please Select"
    {
        textField.value = "";
    }
    else if (select.selectedIndex == 1) // if <new>
    {
        textField.value = "";
        textField.focus();
    }
    else if (select.selectedIndex > 1) // Anything other than "Please Select"
    {
        textField.value = select.options[select.selectedIndex].text;
    }
}

function selectKeySourceScript(selector)
{
    if (selector.selectedIndex >= 1) // Anything other than "Please Select"
    {
        f.keySourceScriptPK.value = selector.options[selector.selectedIndex].value;
    }
}

function selectScriptRow()
{
    selectDeselectRow();

    var tdElement = window.event.srcElement;
    var currentRow = tdElement.parentElement;

    if (currentRow.tagName != "TR") // Bubble up if necessary
    {
        currentRow = currentRow.parentElement;
    }

    f.scriptPK.value = currentRow.scriptPK;
}

function selectTableDefRow()
{
    selectDeselectRow();

    var tdElement = window.event.srcElement;
    var currentRow = tdElement.parentElement;

    if (currentRow.tagName != "TR") // Bubble up if necessary
    {
        currentRow = currentRow.parentElement;
    }

    f.tableDefPK.value = currentRow.tableDefPK;
}

function saveRule()
{
    sendTransactionAction("ProductRuleStructureTran", "saveRule", "main");
}

function showRelation()
{
    sendTransactionAction("ProductRuleStructureTran", "showRelation", "main");
}

function showAttachedProductStructures()
{
    var tdElement = window.event.srcElement;
    var currentRow = tdElement.parentElement.parentElement; // img -> td -> TR (currentRow)

    f.rulesPK.value = currentRow.rulesPK;

    var width   = 0.90 * screen.width;
    var height  = 0.50 * screen.height;

    openDialog("","attachedProductStructuresDialog","left=50,top=50,resizable=yes,width=" + width + ",height=" + height,"ProductRuleStructureTran", "showAttachedProductStructures", "attachedProductStructuresDialog");
}

function showRuleDetail()
{
    var tdElement = window.event.srcElement;

    if (tdElement.tagName == "IMG") // The IMG could have been the source of the click, therefore take a different path.
    {
        if (tdElement.id == "splashButtonScript")
        {
            showAssociatedScriptForRule();
        }
        else if (tdElement.id == "splashButtonTable")
        {
            // Do nothing. This feature will be added in the future.
        }
        else
        {
            showAttachedProductStructures();
        }
    }
    else
    {
        var currentRow = tdElement.parentElement;

        if (currentRow.tagName != "TR") // Bubble up if necessary
        {
            currentRow = currentRow.parentElement;
        }

        f.rulesPK.value = currentRow.rulesPK;

        sendTransactionAction("ProductRuleStructureTran", "showRuleDetail", "main");
    }
}

function showAssociatedScriptForRule()
{
    var tdElement = window.event.srcElement;
    var currentRow = tdElement.parentElement.parentElement; // img -> td -> TR (currentRow)

    f.scriptOrTablePK.value = currentRow.scriptOrTablePK;

    var width   = 0.60 * screen.width;
    var height  = 0.50 * screen.height;

    openDialog("","associatedScriptForRuleDialog","left=50,top=50,resizable=yes,width=" + width + ",height=" + height,"ProductRuleStructureTran", "showAssociatedScriptForRule", "associatedScriptForRuleDialog");
}

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

    var containingTable = currentRow.parentElement;

    for (var i = 0; i < containingTable.rows.length; i++)
    {
        containingTable.rows[i].style.backgroundColor = "#BBBBBB";
        containingTable.rows[i].isSelected = "false";
    }

    // Deactivate the selection in the "other" table
    var otherTable = null;
    if (containingTable.parentElement.id == "scriptsTable")
    {
        otherTable = document.all.tablesTable;
    }
    else
    {
        otherTable = document.all.scriptsTable;
    }

    for (var i = 0; i < otherTable.rows.length; i++)
    {
        otherTable.rows[i].style.backgroundColor = "#BBBBBB";
        otherTable.rows[i].isSelected = "false";
    }

    currentRow.style.backgroundColor = "#FFFFCC";
    currentRow.isSelected = "true";
}


function openDialog(theURL, winName, features, transaction, action, target) {

    dialog = window.open(theURL,winName,features);

    sendTransactionAction(transaction, action, target);
}

function cancelRuleEdits()
{
    sendTransactionAction("ProductRuleStructureTran","cancelRuleEdits", "main");
}


function sendTransactionAction(transaction, action, target) {

	f.transaction.value = transaction;
	f.action.value = action;
	f.target = target;
	f.submit();
}

function disableRuleDetails(shouldDisable)
{
    if (shouldDisable)
    {
        for (var i = 0; i < f.elements.length; i++)
        {
            var elementType = f.elements[i].type;

            if ( (elementType == "text" || elementType == "button" || elementType == "select-one") && (f.elements[i].id.indexOf("btn") < 0))
            {
                f.elements[i].disabled = true;
            }
        }
    }
}

function initForPageMode(pageMode)
{
    if (pageMode == "VIEW")
    {
        disableRuleDetails(true);

        f.btnAdd.disabled = false;
        f.btnSave.disabled = true;
        f.btnCancel.disabled = true;
        f.btnDelete.disabled = false;
    }
    else if (pageMode == "ADD")
    {
        disableRuleDetails(false);

        f.btnAdd.disabled = true;
        f.btnSave.disabled = false;
        f.btnCancel.disabled = false;
        f.btnDelete.disabled = true;

        f.effectiveMonth.focus();
    }
}

function init() {

	f = document.theForm;

    if (message.length > 0)
    {
        alert(message);
    }

    initForPageMode(pageMode);

    var scrollToRow = document.getElementById("<%= rulesPK %>");
    if (scrollToRow != null) {

        scrollToRow.scrollIntoView(false);
    }

    scrollToRow = document.getElementById("<%= tableDefPK %>");
    if (scrollToRow != null) {

        scrollToRow.scrollIntoView(false);
    }

    scrollToRow = document.getElementById("<%= scriptPK %>");
    if (scrollToRow != null) {

        scrollToRow.scrollIntoView(false);
    }
}

</script>
<title>Rules Summary</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta http-equiv="Cache-Control" content="no-store">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Expires" content="0">

<link href="/PORTAL/common/css/PORTAL.css" rel="stylesheet" type="text/css">

</head>

<body class="mainTheme" onLoad="init()">

<form name="theForm" method="post" action="/PORTAL/servlet/RequestManager">
<%--<span style="border-style:solid; border-width:1; border-color:black; width:100%; height:80%; position:relative; top:0; left:0; z-index:0; overflow:visible">--%>

    <span class="tableHeading">Rule Information</span><br>
    <span id="ruleDetailsArea" contentEditable="false" class="contentArea" style="border-style:solid; border-width:1; position:relative; width:100%; height:25%; top:0; left:0; z-index:0; overflow:visible">

        <table width="100%" cellpadding="3" cellspacing="0" border="0">

            <tr>
                <td nowrap>
                    Effective Date:
                </td>

                <td width="20%" nowrap>
                    Process:
                </td>
                <td width="20%" nowrap>
                    Event:
                </td>
                <td width="20%" nowrap>
                    Event Type:
                </td>
                <td width="20%" nowrap>
                    Rule Name:
                </td>
            </tr>
            <tr>
                <td nowrap width="20%">

                    <input type="text" name="effectiveMonth" size="2" maxlength="2" value="<%= effectiveMonth %>">
                    <input type="text" name="effectiveDay" size="2" maxlength="2" value="<%= effectiveDay %>">
                    <input type="text" name="effectiveYear" size="4" maxlength="4" value="<%= effectiveYear %>">

                </td>

                <td width="20%" nowrap>

                    <select name="processNameSelect" id="processNameSelect" onChange="checkForNew()">
                    <option name="id" value="-1">Please Select</option>
                    <option name="id" value="0">&lt;new&gt;</option>
                    <%
                        if (processNames != null)
                        {
                            for(int i = 0; i < processNames.length; i++) {

                                String currentProcessName = processNames[i];

                                if (currentProcessName.equalsIgnoreCase(processName)) {

                                   out.println("<option selected name=\"id\" value=\"" + currentProcessName + "\">" + currentProcessName + "</option>");
                                }
                                else{

                                    out.println("<option name=\"id\" value=\"" + currentProcessName + "\">" + currentProcessName + "</option>");
                                }
                            }
                        }
                    %>
                    </select>
                </td>
                <td width="20%" nowrap>
                    <select name="eventNameSelect" id="eventNameSelect" onChange="checkForNew()">
                    <option name="id" value="-1">Please Select</option>
                    <option name="id" value="0">&lt;new&gt;</option>
                    <%
                        if (eventNames != null)
                        {
                            for(int i = 0; i < eventNames.length; i++) {

                                String currentEventName = eventNames[i];

                                if (currentEventName.equalsIgnoreCase(eventName)) {

                                   out.println("<option selected name=\"id\" value=\"" + currentEventName + "\">" + currentEventName + "</option>");
                                }
                                else{

                                    out.println("<option name=\"id\" value=\"" + currentEventName + "\">" + currentEventName + "</option>");
                                }
                            }
                        }
                    %>
                    </select>
                </td>
                <td width="20%" nowrap>
                    <select name="eventTypeNameSelect" id="eventTypeNameSelect" onChange="checkForNew()">
                    <option name="id" value="-1">Please Select</option>
                    <option name="id" value="0">&lt;new&gt;</option>
                    <%
                        if (eventTypeNames != null)
                        {
                            for(int i = 0; i < eventTypeNames.length; i++) {

                                String currentEventTypeName = eventTypeNames[i];

                                if (currentEventTypeName.equalsIgnoreCase(eventTypeName)) {

                                   out.println("<option selected name=\"id\" value=\"" + currentEventTypeName + "\">" + currentEventTypeName + "</option>");
                                }
                                else{

                                    out.println("<option name=\"id\" value=\"" + currentEventTypeName + "\">" + currentEventTypeName + "</option>");
                                }
                            }
                        }
                    %>
                    </select>
                </td>
                <td width="20%" nowrap>
                  <select name="ruleNameSelect" id="ruleNameSelect" onChange="checkForNew()">
                    <option name="id" value="-1">Please Select</option>
                    <option name="id" value="0">&lt;new&gt;</option>
                    <%
                        if (ruleNames != null)
                        {
                            for(int i = 0; i < ruleNames.length; i++) {

                                String currentRuleName = ruleNames[i];

                                if (currentRuleName.equalsIgnoreCase(ruleName)) {

                                   out.println("<option selected name=\"id\" value=\"" + currentRuleName + "\">" + currentRuleName + "</option>");
                                }
                                else{

                                    out.println("<option name=\"id\" value=\"" + currentRuleName + "\">" + currentRuleName + "</option>");
                                }
                            }
                        }
                    %>
                    </select>
                </td>
            </tr>
            <tr>
                <td width="20%" nowrap align="center">
                    &nbsp;
                </td>
                <td width="20%" nowrap>
                    <input type="text" size="15" maxlength="20" id="processName" name="processName" value="<%= processName %>">
                </td>
                <td width="20%" nowrap>
                    <input type="text" size="15" maxlength="15" id="eventName" name="eventName" value="<%= eventName %>">
                </td>
                <td width="20%" nowrap>
                    <input type="text" size="15" maxlength="15" id="eventTypeName" name="eventTypeName" value="<%= eventTypeName %>">
                </td>
                <td width="20%" nowrap>
                    <input type="text" size="20" maxlength="20" id="ruleName" name="ruleName" value="<%= ruleName %>">
                </td>
            </tr>
            <tr>
                <td colspan="5">
                    &nbsp;
                </td>
            </tr>
            <tr>
                <td align="left" nowrap colspan="3">
                    Description:
                </td>
                <td width="20%" nowrap>
                    Operator:
                </td>
                <td width="20%">
                    Maint Date/Time:
                </td>
            </tr>
            <tr>
                <td align="left" nowrap colspan="3">
                    <input name="description" type="text" size="60" maxsize="100" value="<%= description %>">
                </td>
                <td width="20%" nowrap>
                    <input type="text" name="operator" size="20" value="<%= operator %>" disabled="true">
                </td>
                <td width="20%">
                    <input type="text" name="mainDateTime" size="25" value="<%= maintDateTime %>" disabled="true">
                </td>
            </tr>
            <tr>
                <td colspan="5">
                    &nbsp;
                </td>
            </tr>
        </table>



    </span>

    <br>
    <br>

    <table width="100%" height="50%" border="0" cellspacing="0" cellpadding="0">
        <tr height="5%">
            <td width="49%">
                <span class="tableHeading">Script Summary</span><br>
            </td>
            <td width="2%">
                &nbsp;
            </td>
            <td align="center" width="49%">
                <span class="tableHeading">Table Summary</span><br>
            </td>
        </tr>
        <tr height="95%">
            <td align="center" width="49%">
                <span class="summaryTable" style="border-style:solid; border-width:1; position:relative; width:95%; height:95%; top:5; left:0; z-index:0; overflow:scroll">

                        <table id="scriptsTable" width="100%" border="0" cellspacing="0" cellpadding="0" >
<%
                            if (scriptVOs != null){

                                scriptVOs = (ScriptVO[]) Util.sortObjects(scriptVOs, new String[]{"getScriptName"});

                                for (int i = 0; i < scriptVOs.length; i++){

                                    String currentScriptName = scriptVOs[i].getScriptName();
                                    String currentScriptPK = scriptVOs[i].getScriptPK() + "";

                                    String className = null;

                                    if (!scriptPK.equals("0") && scriptPK.equals(currentScriptPK))
                                    {
                                        className = "highLighted";
                                    }
                                    else{

                                        className = "mainEntry";
                                    }
%>
                                    <tr height="15" class="<%= className %>" id="<%= currentScriptPK %>" scriptPK="<%= currentScriptPK %>"  isSelected="false" onMouseOver="highlightRow()" onMouseOut="unhighlightRow()" onClick="selectScriptRow()">

                                        <td width="20%">
                                            <%= currentScriptName %>
                                        </td>

                                    </tr>
<%
                                }
                            }
%>
                        </table>

                </span>

            </td>
            <td width="2%" nowrap>
                - Or -
            </td>
            <td align="center" valign="middle" width="49%"  bgColor="#30548E">
                <span class="summaryTable" style="border-style:solid; border-width:1; position:relative; width:95%; height:95%; top:5; left:0; z-index:0; overflow:scroll">

                        <table id="tablesTable" width="100%" border="0" cellspacing="0" cellpadding="0" >
<%
                            if (tableDefVOs != null){

                                tableDefVOs = (TableDefVO[]) Util.sortObjects(tableDefVOs, new String[]{"getTableName"});

                                for (int i = 0; i < tableDefVOs.length; i++){

                                    String currentTableName = tableDefVOs[i].getTableName();
                                    String currentTableDefPK = tableDefVOs[i].getTableDefPK() + "";

                                    String className = null;

                                    if (!tableDefPK.equals("0") && tableDefPK.equals(currentTableDefPK))
                                    {
                                        className = "highLighted";
                                    }
                                    else{

                                        className = "mainEntry";
                                    }
%>
                                    <tr height="15" class="<%= className %>" id="<%= currentTableDefPK %>" tableDefPK="<%= currentTableDefPK %>"  isSelected="false" onMouseOver="highlightRow()" onMouseOut="unhighlightRow()" onClick="selectTableDefRow()">

                                        <td>
                                            <%= currentTableName %>
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
        <tr>
            <td>
                &nbsp;
            </td>
            <td>
                &nbsp;
            </td>
            <td align="center" valign="middle" bgColor="#30548E">
                <font face="" color="white">Key Source Script:</font>
            </td>
        </tr>
        <tr>
            <td>
                &nbsp;
            </td>
            <td>
                &nbsp;
            </td>
            <td align="center" valign="middle" width="49%" bgColor="#30548E">
                <select name="keySourceScriptSelect" onChange="selectKeySourceScript(this)">
                <option name="id" value="-1">Please Select</option>
<%
                    if (scriptVOs != null){

                        String sourceScriptFilter = "ZKey";
                        ScriptVO[] filteredScriptVOs = (ScriptVO[]) Util.filterObjects(scriptVOs, "getScriptName", sourceScriptFilter);

                        for (int i = 0; i < filteredScriptVOs.length; i++){

                            String currentScriptName = filteredScriptVOs[i].getScriptName();
                            String currentScriptPK = filteredScriptVOs[i].getScriptPK() + "";

                            if (! keySourceScriptPK.equals("0") && keySourceScriptPK.equals(currentScriptPK))
                            {
                                out.println("<option selected name=\"id\" value=\"" + currentScriptPK + "\">" + currentScriptName + "</option>");
                            }
                            else
                            {
                                out.println("<option name=\"id\" value=\"" + currentScriptPK + "\">" + currentScriptName + "</option>");
                            }
                        }
                    }
%>

                </select>
                <br><br>
            </td>
        </tr>
    </table>

<br><br>

<table width="100%" border="0" cellspacing="0" cellpadding="0">

    <tr>
        <td width="33%">
            <input type="button" id="btnAdd" name="add" value=" Add " onClick="addRule()">
            <input type="button" id="btnSave" name="save" value=" Save " onClick="saveRule()">
            <input type="button" id="btnCancel" name="cancel" value="Cancel" onClick="cancelRuleEdits()">
            <input type="button" id="btnDelete" name="delete" value="Delete" onClick="deleteRule()">
        </td>
        <td width="33%" align="center">
            <span class="tableHeading">Rule Summary</span>
        </td>
        <td align="right" width="33%">
            <input type="button" id="btnRelateRules" name="relateRules" value="Rules Relation" onClick="showRelation()">
        </td>
    </tr>

</table>

<span class="contentArea" style="border-style:solid; border-width:1; position:relative; width:100%; height:50%; top:0; left:0; z-index:0; overflow:visible">

    <table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">

        <tr>

            <th class="dataHeading3" width="4%">
                &nbsp;
            </th>
            <th class="dataHeading3" width="16%">
                Effective Date
            </th>
            <th class="dataHeading3" width="16%">
                Process
            </th>
            <th class="dataHeading3" width="16%">
                Event
            </th>
            <th class="dataHeading3" width="16%">
                Event Type
            </th>
            <th class="dataHeading3" width="16%">
                Rule Name
            </th>
            <th class="dataHeading3" width="16%">
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
                                String currentRule = uiRulesVOs[i].getRulesVO().getRuleName();
                                String scriptOrTableName = null;
                                String scriptOrTablePK = null;
                                boolean isScript = false;

                                if (uiRulesVOs[i].getRulesVO().getTableDefFK() != 0)
                                {
                                    scriptOrTableName = uiRulesVOs[i].getTableDefVO().getTableName();
                                    scriptOrTablePK = uiRulesVOs[i].getTableDefVO().getTableDefPK() + "";
                                    isScript = false;
                                }
                                else
                                {
                                    scriptOrTableName = uiRulesVOs[i].getScriptVO().getScriptName();
                                    scriptOrTablePK = uiRulesVOs[i].getScriptVO().getScriptPK() + "";
                                    isScript = true;
                                }

                                String className = null;

                                if (rulesPK.equals(currentRulesPK))
                                {
                                    className = "highLighted";
                                }
                                else{

                                    className = "mainEntry";
                                }
%>
                                <tr height="15" class="<%= className %>" id="<%= currentRulesPK %>" rulesPK="<%= currentRulesPK %>" scriptOrTablePK="<%= scriptOrTablePK %>" isSelected="false" onMouseOver="highlightRow()" onMouseOut="unhighlightRow()" onClick="showRuleDetail()">

                                    <td width="4%">
                                    <%
                                        if (attachedRulesPKs.contains(new Long(currentRulesPK)))
                                        {
                                            out.println("<img src=\"/PORTAL/engine/images/repeating_b2.gif\" width=\"28\" height=\"15\" alt=\"Show Attached Company Structures\" onMouseOver=\"this.src='/PORTAL/engine/images/repeating_b2_over.gif'\" onMouseOut=\"this.src='/PORTAL/engine/images/repeating_b2.gif'\">");
                                        }
                                        else
                                        {
                                            out.println("&nbsp");
                                        }
                                    %>
                                    </td>
                                    <td width="16%">
                                        <%= currentEffectiveDate %>
                                    </td>
                                    <td width="16%">
                                        <%= currentProcess %>
                                    </td>
                                    <td width="16%">
                                        <%= currentEvent %>
                                    </td>
                                    <td width="16%">
                                        <%= currentEventType %>
                                    </td>
                                    <td width="16%">
                                        <%= currentRule %>
                                    </td>
                                    <td width="16%">
                                        <%= scriptOrTableName %> <%= (isScript)?"<img id=\"splashButtonScript\" src=\"/PORTAL/engine/images/scripttable_b1_over.gif\" width=\"24\" height=\"12\" alt=\"Script\">":"<img id=\"splashButtonTable\" src=\"/PORTAL/engine/images/scripttable_b2_over.gif\" width=\"24\" height=\"12\" alt=\"Table\">" %>
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

<br>

    <table width="100%">

        <tr>
             <td align="left" valign="bottom">
                <img src="/PORTAL/engine/images/repeating_b2.gif" width="28" height="15"> = Attached Rule
                &nbsp;
                &nbsp;
                &nbsp;
                &nbsp;
                <img src="/PORTAL/engine/images/scripttable_b1_over.gif" width="24" height="12" alt="Script"> = Script
                &nbsp;
                &nbsp;
                &nbsp;
                &nbsp;
                <img src="/PORTAL/engine/images/scripttable_b2_over.gif" width="24" height="12" alt="Table"> = Table
             </td>
        </tr>
    </table>




<input type="hidden" name="transaction">
<input type="hidden" name="action">
<input type="hidden" name="rulesPK" value="<%= rulesPK %>">
<input type="hidden" name="scriptPK" value="<%= scriptPK %>">
<input type="hidden" name="tableDefPK" value="<%= tableDefPK %>">
<input type="hidden" name="keySourceScriptPK" value="<%= keySourceScriptPK %>">
<input type="hidden" name="scriptOrTablePK">

</form>
</body>
</html>