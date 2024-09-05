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
                 java.util.*,
                 fission.utility.*,
                 edit.common.*,
                 java.math.BigDecimal"%>

<%@ page  errorPage="/common/jsp/error.jsp" autoFlush="true" %>


<%
    // User selected Table and TableKeys
    TableDefVO tableDefVO = (TableDefVO) request.getAttribute("tableDefVO");
    String accessType = "";
    String tableName = "";
    if (tableDefVO != null)
    {
        accessType = Util.initString(tableDefVO.getAccessType(), "");
        tableName = Util.initString(tableDefVO.getTableName(), "");
    }

    TableKeysVO tableKeysVO = (TableKeysVO) request.getAttribute("tableKeysVO");
    String gender = "";
    String classType = "";
    String tableType = "";
    String state = "";
    String effectiveMonth = "";
    String effectiveDay = "";
    String effectiveYear = "";
    String bandAmount = "";
    String userKey = "";
    if (tableKeysVO != null)
    {
        gender = Util.initString(tableKeysVO.getGender(), "");
        classType = Util.initString(tableKeysVO.getClassType(), "");
        tableType = Util.initString(tableKeysVO.getTableType(), "");
        state = Util.initString(tableKeysVO.getState(), "");
        if (tableKeysVO.getEffectiveDate() != null)
        {
            EDITDate effectiveDate = new EDITDate(tableKeysVO.getEffectiveDate());
            effectiveYear = effectiveDate.getFormattedYear();
            effectiveMonth = effectiveDate.getFormattedMonth();
            effectiveDay = effectiveDate.getFormattedDay();
        }
        bandAmount = Util.initString(tableKeysVO.getBandAmount() + "", "");
        userKey = Util.initString(tableKeysVO.getUserKey(), "");
    }

    // For rate summary
    RateTableVO[] rateTableVOs = (RateTableVO[]) request.getAttribute("rateTableVOs");

    // For drop-downs and table summary
    TableDefVO[] tableDefVOs = (TableDefVO[]) request.getAttribute("tableDefVOs");

    if (tableDefVOs != null)
    {
        // sort by table name
        tableDefVOs = (TableDefVO[]) Util.sortObjects(tableDefVOs, new String[]{"getTableName"});

        // also sort by effective date and sex
         for (int i = 0; i < tableDefVOs.length; i++)
         {
            TableKeysVO[] tableKeys = tableDefVOs[i].getTableKeysVO();

             if (tableKeys != null)
             {
                 tableKeys = (TableKeysVO[]) Util.sortObjects(tableKeys, new String[]{"getEffectiveDate", "getGender"});

                 tableDefVOs[i].setTableKeysVO(tableKeys);
             }
         }
    }

    CodeTableVO[] genderCodes = CodeTableWrapper.getSingleton().getCodeTableEntries("GENDER");
    CodeTableVO[] classTypeCodes = CodeTableWrapper.getSingleton().getCodeTableEntries("CLASS");
    CodeTableVO[] tableTypeCodes = CodeTableWrapper.getSingleton().getCodeTableEntries("TABLETYPE");
    CodeTableVO[] stateCodes = CodeTableWrapper.getSingleton().getCodeTableEntries("STATE");
    CodeTableVO[] accessTypeCodes = CodeTableWrapper.getSingleton().getCodeTableEntries("ACCESSTYPE");

    String message = (String) request.getAttribute("message");
    message = Util.initString(message, "");

    String pageMode = (String) request.getAttribute("pageMode");
    pageMode = Util.initString(pageMode, "VIEW");

    String tableDefPK = Util.initString((String) request.getAttribute("tableDefPK"), "0");
    String tableKeysPK = Util.initString((String) request.getAttribute("tableKeysPK"), "0");

    List attachedTableDefPKs = (List) request.getAttribute("attachedTableDefPKs");

    int rateTableCount = 100;

%>

<html>
<head>

<script language="Javascript1.2">

var f = null;

var colorHighlighted = "#FFFFCC";
var colorAssocEntry = "#00BB00";
var colorMainEntry = "#BBBBBB";

var message = "<%= message %>";

var pageMode = "<%= pageMode %>";

function highlightRow() {

    var tdElement = window.event.srcElement;
    var currentRow = tdElement.parentElement;

    if (currentRow.tagName != "TR") // Bubble up if necessary
    {
        currentRow = currentRow.parentElement;
    }

    currentRow.style.backgroundColor = colorHighlighted;
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
            currentRow.style.backgroundColor = colorAssocEntry;
        }
        else if (className == "highLighted")
        {
            currentRow.style.backgroundColor = colorHighlighted;
        }
        else {

            currentRow.style.backgroundColor = colorMainEntry;
        }
    }
}

function openDialog(theURL, winName, features, transaction, action, target) {

    dialog = window.open(theURL,winName,features);

    sendTransactionAction(transaction, action, target);
}

function cancelTableEdits()
{
    sendTransactionAction("TableTran","cancelTableEdits", "main");
}

function saveTable()
{
    sendTransactionAction("TableTran","saveTable", "main");
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

    }
    else if (pageMode == "ADD")
    {

    }
}

function setAccessType()
{
    var tableDefPKSelect = f.selectTableDefPK;

    var accessTypeSelect = f.selectAccessType;

    var targetAccessType = tableDefPKSelect.options[tableDefPKSelect.selectedIndex].accessType;

    for (var i = 0; i < accessTypeSelect.length; i++)
    {
        var currentAccessType = accessTypeSelect.options[i].value;

        if (currentAccessType == targetAccessType)
        {
            accessTypeSelect.options[i].selected = true;

            break;
        }
    }
}

function showTableRates()
{
    var tdElement = window.event.srcElement;
    var currentRow = tdElement.parentElement;

    if (tdElement.tagName == "IMG" && tdElement.id != "splashButton") // The IMG could have been the source of the click, therefore take a different path.
    {
        showAssociatedRulesForTable();
    }
    else
    {
        var tableDefPK = currentRow.tableDefPK;
        var tableKeysPK = currentRow.tableKeysPK;

        f.tableDefPK.value = tableDefPK;
        f.tableKeysPK.value = tableKeysPK;

        sendTransactionAction("TableTran","showTableRates", "main");
    }
}

function showAssociatedRulesForTable()
{
    var tdElement = window.event.srcElement;
    var currentRow = tdElement.parentElement.parentElement; // img -> td -> TR (currentRow)

    f.tableDefPK.value = currentRow.tableDefPK;
    f.tableKeysPK.value = currentRow.tableKeysPK;

    var width   = 0.90 * screen.width;
    var height  = 0.50 * screen.height;

    openDialog("","associatedRulesForTableDialog","left=50,top=50,resizable=yes,width=" + width + ",height=" + height,"TableTran", "showAssociatedRulesForTable", "associatedRulesForTableDialog");
}

function addTable()
{
    sendTransactionAction("TableTran","addTable", "main");
}

function deleteTable()
{
    sendTransactionAction("TableTran","deleteTable", "main");
}

function checkForNewTable()
{
    var select = window.event.srcElement;

    if (select.selectedIndex == 0) // If "Please Select"
    {
        f.tableName.value = "";
    }
    else if (select.selectedIndex == 1) // if <new>
    {
        f.tableName.value = "";
        f.tableName.focus();
    }
    else if (select.selectedIndex > 1) // Anything other than "Please Select"
    {
        f.tableName.value = select.options[select.selectedIndex].text;
    }
}

function importRateTables() {

    var importForm = document.FileForm;

    importForm.target = "_self";

    importForm.submit();

    // display file selection box
}

function init() {

	f = document.theForm;

    if (message.length > 0)
    {
        alert(message);
    }

    initForPageMode(pageMode);

    var scrollToRow = document.getElementById("<%= tableDefPK + "-" + tableKeysPK %>");

    if (scrollToRow != null) {

        scrollToRow.scrollIntoView(false);
    }
}

</script>
<title>Tables</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta http-equiv="Cache-Control" content="no-store">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Expires" content="0">

<link href="/PORTAL/common/css/PORTAL.css" rel="stylesheet" type="text/css">

</head>

<body class="mainTheme" onLoad="init()">

<form name="theForm" method="post" action="/PORTAL/servlet/RequestManager">

<span class="tableHeading">Table Information</span><br>
<table border="0" width="100%" align="center" cellspacing="0" class="contentArea">

<tr>
    <td width="10%">
        &nbsp;
    </td>
    <td align="right" width="20%">
        Table:
    </td>
    <td align="left" width="20%">
        <select name="selectTableDefPK" onChange="checkForNewTable()">
            <option value="-1" accessType="-1">Please Select</option>
            <option value="0" accessType="-1">&lt;new&gt;</option>
                <%
                    if (tableDefVOs != null)
                    {
                        for(int i = 0; i < tableDefVOs.length; i++) {

                            String currentTableName = tableDefVOs[i].getTableName();
                            String currentTableDefPK = tableDefVOs[i].getTableDefPK() + "";
                            String currentAccessType = tableDefVOs[i].getAccessType();

                            if (currentTableDefPK.equalsIgnoreCase(tableDefPK)) {

                               out.println("<option selected accessType=\"" + currentAccessType + "\" name=\"id\" value=\"" + currentTableDefPK + "\">" + currentTableName + "</option>");
                            }
                            else{

                                out.println("<option accessType=\"" + currentAccessType + "\" name=\"id\" value=\"" + currentTableDefPK + "\">" + currentTableName + "</option>");
                            }
                        }
                    }
                %>
        </select>
    </td>
    <td align="right" width="20%">
        Access Type:
    </td>
    <td align="left" width="20%">
        <select name="selectAccessType">
            <option value="-1">Please Select</option>
                <%
                    if (accessTypeCodes != null)
                    {
                        accessTypeCodes = (CodeTableVO[]) Util.sortObjects(accessTypeCodes, new String[]{"getCodeDesc"});

                        for(int i = 0; i < accessTypeCodes.length; i++) {

                            String currentAccessTypeCode = accessTypeCodes[i].getCode();
                            String currentCodeTablePK = accessTypeCodes[i].getCodeTablePK() + "";
                            String currentAccessTypeCodeDesc = accessTypeCodes[i].getCodeDesc();

                            if (currentAccessTypeCode.equalsIgnoreCase(accessType)) {

                               out.println("<option selected name=\"id\" value=\"" + currentAccessTypeCode + "\">" + currentAccessTypeCodeDesc + "</option>");
                            }
                            else{

                                out.println("<option name=\"id\" value=\"" + currentAccessTypeCode + "\">" + currentAccessTypeCodeDesc + "</option>");
                            }
                        }
                    }
                %>
        </select>
    </td>
    <td width="10%">
        &nbsp;
    </td>
</tr>

<tr>
    <td>
        &nbsp;
    </td>
    <td>&nbsp;</td>
    <td><input type="text" size="15" name="tableName" value="<%= tableName %>"></td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>
        &nbsp;
    </td>
</tr>

<tr>
    <td>
        &nbsp;
    </td>
    <td align="right">
        Sex:
    </td>
    <td align="left">
        <select name="selectGenderCode">
            <option value="-1">Please Select</option>
                <%
                    if (genderCodes != null)
                    {
                        genderCodes = (CodeTableVO[]) Util.sortObjects(genderCodes, new String[]{"getCodeDesc"});

                        for(int i = 0; i < genderCodes.length; i++) {

                            String currentGenderCode = genderCodes[i].getCode();
                            String currentCodeTablePK = genderCodes[i].getCodeTablePK() + "";
                            String currentGenderCodeDesc = genderCodes[i].getCodeDesc();

                            if (currentGenderCode.equalsIgnoreCase(gender)) {

                               out.println("<option selected name=\"id\" value=\"" + currentGenderCode + "\">" + currentGenderCodeDesc + "</option>");
                            }
                            else{

                                out.println("<option name=\"id\" value=\"" + currentGenderCode + "\">" + currentGenderCodeDesc + "</option>");
                            }
                        }
                    }
                %>
        </select>
    </td>
    <td align="right">
        Class Type:
    </td>
    <td align="left">
        <select name="selectClassTypeCode">
            <option value="-1">Please Select</option>
                <%
                    if (classTypeCodes != null)
                    {
                        classTypeCodes = (CodeTableVO[]) Util.sortObjects(classTypeCodes, new String[]{"getCodeDesc"});

                        for(int i = 0; i < classTypeCodes.length; i++) {

                            String currentClassTypeCode = classTypeCodes[i].getCode();
                            String currentCodeTablePK = classTypeCodes[i].getCodeTablePK() + "";
                            String currentClassTypeCodeDesc = classTypeCodes[i].getCodeDesc();

                            if (currentClassTypeCode.equalsIgnoreCase(classType)) {

                               out.println("<option selected name=\"id\" value=\"" + currentClassTypeCode + "\">" + currentClassTypeCodeDesc + "</option>");
                            }
                            else{

                                out.println("<option name=\"id\" value=\"" + currentClassTypeCode + "\">" + currentClassTypeCodeDesc + "</option>");
                            }
                        }
                    }
                %>
        </select>
    </td>
    <td>
        &nbsp;
    </td>
</tr>

<tr>
    <td>
        &nbsp;
    </td>
    <td align="right">
        Band Amount:
    </td>
    <td align="left">
        <input name="bandAmount" type="text" size="19" value="<%= bandAmount %>">
    </td>
    <td align="right">
        Table Type:
    </td>
    <td align="left">
        <select name="selectTableTypeCode">
            <option value="-1">Please Select</option>
                <%
                    if (tableTypeCodes != null)
                    {
                        tableTypeCodes = (CodeTableVO[]) Util.sortObjects(tableTypeCodes, new String[]{"getCodeDesc"});

                        for(int i = 0; i < tableTypeCodes.length; i++) {

                            String currentTableTypeCode = tableTypeCodes[i].getCode();
                            String currentCodeTablePK = tableTypeCodes[i].getCodeTablePK() + "";
                            String currentTableTypeCodeDesc = tableTypeCodes[i].getCodeDesc();

                            if (currentTableTypeCode.equalsIgnoreCase(tableType)) {

                               out.println("<option selected name=\"id\" value=\"" + currentTableTypeCode + "\">" + currentTableTypeCodeDesc + "</option>");
                            }
                            else{

                                out.println("<option name=\"id\" value=\"" + currentTableTypeCode + "\">" + currentTableTypeCodeDesc + "</option>");
                            }
                        }
                    }
                %>
        </select>
    </td>
    <td>
        &nbsp;
    </td>
</tr>

<tr>
    <td>
        &nbsp;
    </td>
    <td align="right">
        Area:
    </td>
    <td align="left">
        <select name="selectStateCode">
            <option value="-1">Please Select</option>
                <%
                    if (stateCodes != null)
                    {
                        stateCodes = (CodeTableVO[]) Util.sortObjects(stateCodes, new String[]{"getCodeDesc"});

                        for(int i = 0; i < stateCodes.length; i++) {

                            String currentStateCode = stateCodes[i].getCode();
                            String currentCodeTablePK = stateCodes[i].getCodeTablePK() + "";
                            String currentStateCodeDesc = stateCodes[i].getCodeDesc();

                            if (currentStateCode.equalsIgnoreCase(state)) {

                               out.println("<option selected name=\"id\" value=\"" + currentStateCode + "\">" + currentStateCodeDesc + "</option>");
                            }
                            else{

                                out.println("<option name=\"id\" value=\"" + currentStateCode + "\">" + currentStateCodeDesc + "</option>");
                            }
                        }
                    }
                %>
        </select>
    </td>
    <td align="right">
        Effective Date:
    </td>
    <td align="left">
        <input name="effectiveMonth" type="text" size="2" maxlength="2" value="<%= effectiveMonth %>">/
        <input name="effectiveDay" type="text" size="2" maxlength="2" value="<%= effectiveDay %>">/
        <input name="effectiveYear" type="text" size="4" maxlength="4" value="<%= effectiveYear %>">
    </td>
    <td>
        &nbsp;
    </td>
</tr>

<tr>
    <td>
        &nbsp;
    </td>
    <td align="right">
        User Key:
    </td>
    <td align="left">
        <input name="userKey" type="text" size="15" value="<%= userKey %>">
    </td>
    <td>
        &nbsp;
    </td>
    <td>
        &nbsp;
    </td>
    <td>
        &nbsp;
    </td>
</tr>

</table>

<br>
<span class="tableHeading">Table Rates</span>
<table border="0" width="100%" height="30%" align="center" cellspacing="0" class="summaryArea">

    <tr>
        <td colspan="5" height="100%">
            <span class="scrollableContent">
            <table class="scrollableArea" border="0" width="100%" cellspacing="0">
<%
        if (rateTableVOs != null)
        {
            rateTableVOs = (RateTableVO[]) Util.sortObjects(rateTableVOs, new String[]{"getAge", "getDuration"});

            rateTableCount = (rateTableVOs.length > 100)?rateTableVOs.length:100;
        }

        for (int i = 0; i < rateTableCount; i++)
        {
            RateTableVO currentRateTableVO = null;
            String currentRateTablePK = "";
            String currentAge = "";
            String currentDuration = "";
            String currentRate = "";

            if (rateTableVOs != null)
            {
                if (i < rateTableVOs.length)
                {
                    currentRateTableVO = rateTableVOs[i];

                    currentRateTablePK = currentRateTableVO.getRateTablePK() + "";
                    currentAge = currentRateTableVO.getAge() + "";
                    currentDuration = currentRateTableVO.getDuration() + "";
                    currentRate = (new EDITBigDecimal(currentRateTableVO.getRate())).toString();
                }
            }

%>
                <tr rateTablePK="<%= currentRateTablePK %>">
                    <td>
                        <input type="hidden" name="rateTablePK<%= i %>" value="<%= currentRateTablePK %>">
                    </td>
                    <td width="10%" align="center">
                        Entry: <%= (i + 1) %>
                    </td>
                    <td width="30%" align="center">
                        Age: <input type="text" size="3" maxsize="3" name="age<%= i %>" value="<%= currentAge %>">
                    </td>
                    <td width="30%" align="center">
                        Duration: <input type="text" size="3" maxsize="3" name="duration<%= i %>" value="<%= currentDuration %>">
                    </td>
                    <td width="30%" align="center">
                        Rate: <input type="text" size="15" name="rate<%= i %>" value="<%= currentRate %>">
                    </td>
                </tr>
<%
        }
%>
            </span>
            </table>
        </td>
    </tr>

</table>


<br>
<table width="100%" border="0" cellspacing="0" cellpadding="0">

    <tr>
        <td width="33%" align="left">
            <input type="button" id="btnAdd" name="add" value=" Add " onClick="addTable()">
            <input type="button" id="btnSave" name="save" value=" Save " onClick="saveTable()">
            <input type="button" id="btnCancel" name="cancel" value="Cancel" onClick="cancelTableEdits()">
            <input type="button" id="btnDelete" name="delete" value="Delete" onClick="deleteTable()">
        </td>
        <td width="33%" align="center">
            <span class="tableHeading">Table Summary</span>
        </td>
        <td align="right">
            &nbsp;
        </td>
    </tr>

</table>

<table width="100%" cellpadding="0" cellspacing="0" height="50%" align="center" class="summaryArea">

    <tr>
        <th width="4%">
            &nbsp;
        </th>
        <th width="11%">
            Name
        </th>
        <th width="10%">
            Eff Date
        </th>
        <th width="11%">
            Sex
        </th>
        <th width="10%">
            Class
        </th>
        <th width="11%">
            Band Amt
        </th>
        <th width="10%">
            Area
        </th>
        <th width="11%">
            Type
        </th>
        <th width="11%">
            User Key
        </th>
        <th width="11%">
            Access Type
        </th>
    </tr>
    <tr>
        <td colspan="10" height="95%">
            <span class="scrollableContent">
            <table class="scrollableArea" width="100%" height="100%" cellspacing="0" cellpadding="0">
<%
    if (tableDefVOs != null)
    {
        for (int i = 0; i < tableDefVOs.length; i++)
        {
            TableDefVO currentTableDefVO = tableDefVOs[i];
            String currentTableName = Util.initString(currentTableDefVO.getTableName(), "");
            String currentAccessType = Util.initString(currentTableDefVO.getAccessType(), "");
            String currentTableDefPK = currentTableDefVO.getTableDefPK() + "";
            String currentTableKeysPK = "";
            String currentEffectiveDate = "";
            String currentGender = "";
            String currentClass = "";
            String currentBandAmount = "";
            String currentArea = "";
            String currentTableType = "";
            String currentUserKey = "";

            TableKeysVO[] currentTableKeysVOs = currentTableDefVO.getTableKeysVO();

            if (currentTableKeysVOs != null)
            {
                for (int j = 0; j < currentTableKeysVOs.length; j++)
                {
                    currentTableKeysPK = Util.initString(currentTableKeysVOs[j].getTableKeysPK() + "", "");
                    currentEffectiveDate = Util.initString(currentTableKeysVOs[j].getEffectiveDate(), "");
                    if (!currentEffectiveDate.equals(""))
                    {
                        currentEffectiveDate = DateTimeUtil.formatEDITDateAsMMDDYYYY(new EDITDate(currentEffectiveDate));
                    }

                    currentGender = Util.initString(currentTableKeysVOs[j].getGender(), "");
                    currentClass = Util.initString(currentTableKeysVOs[j].getClassType(), "");
                    currentBandAmount = currentTableKeysVOs[j].getBandAmount().toString();
                    currentArea = Util.initString(currentTableKeysVOs[j].getState(), "");
                    currentTableType = Util.initString(currentTableKeysVOs[j].getTableType(), "");
                    currentUserKey = Util.initString(currentTableKeysVOs[j].getUserKey(), "");

                    String className = null;

                    if (tableDefPK.equals(currentTableDefPK) && tableKeysPK.equals(currentTableKeysPK))
                    {
                        className = "highLighted";
                    }
                    else{

                        className = "mainEntry";
                    }
%>
                <tr id="<%= currentTableDefPK + "-" + currentTableKeysPK%>" tableDefPK="<%= currentTableDefPK %>" tableKeysPK="<%= currentTableKeysPK %>" class="<%= className %>" selected="" onMouseOver="highlightRow()" onMouseOut="unhighlightRow()" onClick="showTableRates()">
                    <td width="4%">
                    <%
                        if (attachedTableDefPKs.contains(new Long(currentTableDefPK)))
                        {
                            out.println("<img src=\"/PORTAL/engine/images/repeating_b2.gif\" width=\"28\" height=\"15\" alt=\"Show Attached Company Structures\" onMouseOver=\"this.src='/PORTAL/engine/images/repeating_b2_over.gif'\" onMouseOut=\"this.src='/PORTAL/engine/images/repeating_b2.gif'\">");
                        }
                        else
                        {
                            out.println("&nbsp;");
                        }
                    %>
                    </td>
                    <td width="11%">
                        <%= currentTableName %>&nbsp;
                    </td>
                    <td width="10%">
                        <%= currentEffectiveDate %>&nbsp;
                    </td>
                    <td width="11%">
                        <%= currentGender %>&nbsp;
                    </td>
                    <td width="10%">
                        <%= currentClass %>&nbsp;
                    </td>
                    <td width="11%">
                        <%= currentBandAmount %>&nbsp;
                    </td>
                    <td width="10%">
                        <%=  currentArea %>&nbsp;
                    </td>
                    <td width="11%">
                        <%= currentTableType %>&nbsp;
                    </td>
                    <td width="11%">
                        <%= currentUserKey %>&nbsp;
                    </td>
                    <td width="11%">
                        <%= currentAccessType %>&nbsp;
                    </td>
                </tr>
<%
                }
            }
        }
    }
%>
            </table>
            </span>
        </td>
    </tr>

</table>
<input type="hidden" name="transaction">
<input type="hidden" name="action">
<input type="hidden" name="tableDefPK" value="<%= tableDefPK %>">
<input type="hidden" name="tableKeysPK" value="<%= tableKeysPK %>">
<input type="hidden" name="rateTableCount" value="<%= rateTableCount %>">

</form>

<br>

<form name="FileForm" method="post" action="/PORTAL/servlet/RequestManager?transaction=TableTran&action=importRateTables" enctype="multipart/form-data">

    <table width="100%">

        <tr>
             <td align="left" valign="bottom">
                <img src="/PORTAL/engine/images/repeating_b2.gif" width="28" height="15"> = Attached Table
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                <input type="file" name="fileName" accept="text" size="17" title="Select File for Import">
                <input type="button" name="import" value="Import" onClick="importRateTables()">
             </td>
        </tr>
    </table>
 <input type="hidden" name="fileName" value="">
</form>


</body>
</html>