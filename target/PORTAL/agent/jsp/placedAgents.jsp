<%@ page import="edit.common.vo.*,
                 java.util.*,
                 fission.utility.*,
                 edit.common.*,
                 agent.ui.*,
                 agent.*,
                 client.*,
                 edit.services.db.hibernate.*,
                 role.ClientRole"%>
<%
    String placedAgentMessage = (String) request.getAttribute("placedAgentMessage");

    if (placedAgentMessage == null)
    {
        placedAgentMessage = "";
    }
    String activeContractCodeCT = (String) request.getAttribute("activeContractCodeCT");
    String activeAgentContractPK = (String) request.getAttribute("activeAgentContractPK");
    String activeLowestLevelPlacedAgentPK = (String) request.getAttribute("activeLowestLevelPlacedAgentPK");
    String activePlacedAgentPK = (String) request.getAttribute("activePlacedAgentPK");
    String agentId = Util.initString((String) request.getAttribute("agentId"), "");
    String reportToId = Util.initString((String) request.getAttribute("reportToId"), "");
    String agentName = Util.initString((String) request.getAttribute("agentName"), "");
    
    String reportToName = Util.initString((String) request.getAttribute("reportToName"), "");
    String startDate = Util.initString((String) request.getAttribute("startDate"), "");    

    CommissionProfile[] commissionProfiles = (CommissionProfile[]) request.getAttribute("commissionProfiles");

    AgentContract[] agentContracts = (AgentContract[]) request.getAttribute("agentContracts");

    PlacedAgentBranch[] placedAgentBranches = (PlacedAgentBranch[]) request.getAttribute("placedAgentBranches");

    PlacedAgentBranch placedAgentBranch = (PlacedAgentBranch) request.getAttribute("placedAgentBranch");

    long commissionProfilePK = Long.parseLong(Util.initString((String) request.getAttribute("commissionProfilePK"), "0"));

    boolean isPlacedAgentBranchError = (request.getAttribute("isPlacedAgentBranchError") != null)?true:false;

    String showFullDetail = (String) request.getAttribute("showFullDetail");

    String requestSource = Util.initString((String) request.getAttribute("requestSource"), "");

    boolean enableAgentId = (requestSource.equals("agentReportTo"))?false: true;

    boolean allowGroupCopy = (requestSource.equals("agentReportTo"))?false: true;

    String situation = Util.initString((String) request.getAttribute("situation"), "");

    String agentNumber = Util.initString((String) request.getAttribute("agentNumber"), "");


    HierarchyReport hierarchyReport = (HierarchyReport) request.getAttribute("hierarchyReport");
    String showGroupPlacedAgentPK = Util.initString((String) request.getAttribute("showGroupPlacedAgentPK"), null);

    CodeTableVO[] contractCodeCTs = CodeTableWrapper.getSingleton().getCodeTableEntries("CONTRACTCODE");
%>
<%!
    /**
     * Performs date comparison between the date passed-in and the current date.
     * @param stopDate
     * @return true if stopDate is less than the current date
     */
    private boolean showAsDisabled(EDITDate stopDate)
    {
        boolean showAsDisabled = false;

        if (stopDate != null)
        {
            EDITDate currentDate = new EDITDate();

            if (stopDate.before(currentDate))
            {
                showAsDisabled = true;
            }
        }

        return showAsDisabled;
    }

%>

<html>
<head>

<script src="/PORTAL/common/javascript/commonJavascriptFunctions.js"></script>
<script src="/PORTAL/common/javascript/formatDateFunctions.js"></script>
<script src="/PORTAL/common/javascript/calendarSelector.js"></script>

<script language="Javascript1.2">

    var f = null;

    var activeAgentContractPK = "<%= activeAgentContractPK %>";
    var activeLowestLevelPlacedAgentPK = "<%= activeLowestLevelPlacedAgentPK %>";
    var activePlacedAgentPK = "<%= activePlacedAgentPK %>";
<%--    var activeCommissionProfilePK = "<%= commissionProfilePK %>";--%>
    var placedAgentMessage = "<%= placedAgentMessage %>";
    var isPlacedAgentBranchError = <%= isPlacedAgentBranchError %>;
    var showFullDetail = "<%= showFullDetail %>";
    var groupSelected = <%= (hierarchyReport != null) %>

    function init()
    {
    	f = document.theForm;

        scrollActiveEntriesIntoView();
        
        if (placedAgentMessage != "")
        {
            alert(placedAgentMessage);
        }

        if (isPlacedAgentBranchError)
        {
            showInvalidPlacedAgentBranchError();
        }

        if (showFullDetail == "true")
        {
            f.showFullDetail.checked = true;
        }
    }

    function showInvalidPlacedAgentBranchError()
    {
        alert("Error - Invalid Hierarchy Has Been Created");
    }

    function openDialog(theURL,winName,features,transaction,action) {

        dialog = window.open(theURL,winName,features);

        sendTransactionAction(transaction, action, winName);
    }

    function scrollActiveEntriesIntoView()
    {
        if (activeAgentContractPK != "null")
        {
            document.getElementById(activeAgentContractPK).scrollIntoView(true);
        }

        if (activeLowestLevelPlacedAgentPK != "null")
        {
            document.getElementById(activeLowestLevelPlacedAgentPK).scrollIntoView(true);
        }

        if (activePlacedAgentPK != "null")
        {
            document.getElementById(activePlacedAgentPK).scrollIntoView(true);
        }
    }

    function sendTransactionAction(transaction, action, target) {
    
        disableHierachyButtons();
    
        f.transaction.value = transaction;
        f.action.value = action;
        f.target = target;
        f.submit();
        
        enableHierarchyButtons();
    }

    function showAgentContracts(selector)
    {
        var selectorValue = selector.value;
        
        if (selectorValue == "0")
        {
            clearPlacedAgents();
        }
        else
        {
            f.activeContractCodeCT.value = selectorValue;

            sendTransactionAction("AgentDetailTran", "showAgentContracts", "_self");
        }
    }

    function showAgentContractHierarchies()
    {
        var tdElement = window.event.srcElement;

        var trElement = tdElement.parentElement;

        var agentContractPK = trElement.id;

        f.activeAgentContractPK.value = agentContractPK;

        sendTransactionAction("AgentDetailTran", "showAgentContractHierarchies", "_self");
    }

    function showPlacedAgentHierarchy()
    {
        var tdElement = window.event.srcElement;

        var trElement = tdElement.parentElement;

        var placedAgentBranchPK = trElement.id;

        f.activeLowestLevelPlacedAgentPK.value = placedAgentBranchPK;

        sendTransactionAction("AgentDetailTran", "showPlacedAgentHierarchy", "_self");
    }

    function showCommissionProfile()
    {
        var tdElement = window.event.srcElement;

        var trElement = tdElement.parentElement;

        var placedAgentPK = trElement.id;

        f.activePlacedAgentPK.value = placedAgentPK;

        sendTransactionAction("AgentDetailTran", "showCommissionProfile", "_self");
    }

    function setCommissionProfile()
    {
        var tdElement = window.event.srcElement;
        var currentRow = tdElement.parentElement;

<%--        f.associatedCommissionProfilePK.value = currentRow.commissionProfilePK;--%>

        selectDeselectRow();
    }

    function clearPlacedAgents()
    {
        sendTransactionAction("AgentDetailTran", "clearPlacedAgents", "_self");
    }

    function placeAgentContract()
    {
        var activeAgentContractPK = f.activeAgentContractPK.value;

        if ((f.startDate.value == 0) || (f.commissionProfilePK.selectedIndex == 0))
        {
            f.startDate.style.backgroundColor = "#FFFFCC";

            f.commissionProfilePK.style.backgroundColor = "#FFFFCC";

            alert("Start Date And Commission Profile Are Required");
        }
        else if (activeAgentContractPK == "null")
        {
            alert("An Agent Must Be Selected");
        }
        else if (f.commissionProfilePK.selectedIndex == 0)
        {
            alert("Commission Level Required");
        }
        else
        {
            sendTransactionAction("AgentDetailTran", "placeAgentContract", "_self");

            if (opener)
            {
                opener.sendTransactionAction("AgentDetailTran", "showPlacedAgentDetail", "contentIFrame");
            }
        }
    }

    function removePlacedAgent()
    {
        var activePlacedAgentPK = f.activePlacedAgentPK.value;

        if (activePlacedAgentPK == "null")
        {
            alert("An Agent In The Hierarchy Must Be Selected");
        }
        else
        {
            sendTransactionAction("AgentDetailTran", "removePlacedAgent", "_self");
        }
    }

    /**
     * Physically and visually disables all actiion buttons to prevent any form
     * of double submission.
     */
    function disableHierachyButtons()
    {
        f.btnLeft.style.backgroundColor = "#99BBBB";
        f.btnRight.style.backgroundColor = "#99BBBB";
        f.btnHierarchy.style.backgroundColor = "#99BBBB";
        f.btnHierarchyMovingFrom.style.backgroundColor = "#99BBBB";
        f.btnHierarchyMovingTo.style.backgroundColor = "#99BBBB";
        f.btnValidate.style.backgroundColor = "#99BBBB";

        f.btnLeft.disabled = true;
        f.btnRight.disabled = true;
        f.btnHierarchy.disabled = true;
        f.btnHierarchyMovingFrom.disabled = true;
        f.btnHierarchyMovingTo.disabled = true;
        f.btnValidate.disabled = true;

        f.startDate.contentEditable = false;
        f.situation.contentEditable = false;
    }

    /**
     * Physically and visually enables all action buttons.
     */
    function enableHierarchyButtons()
    {
        f.btnLeft.style.backgroundColor = "#99CCCC";
        f.btnRight.style.backgroundColor = "#99CCCC";
        f.btnHierarchy.style.backgroundColor = "#99CCCC";
        f.btnHierarchyMovingFrom.style.backgroundColor = "#99CCCC";
        f.btnHierarchyMovingTo.style.backgroundColor = "#99CCCC";
        f.btnValidate.style.backgroundColor = "#99CCCC";

        f.btnLeft.disabled = false;
        f.btnRight.disabled = false;
        f.btnHierarchy.disabled = false;
        f.btnHierarchyMovingFrom.disabled = false;
        f.btnHierarchyMovingTo.disabled = false;
        f.btnValidate.disabled = false;

        f.startDate.contentEditable = true;
        f.situation.contentEditable = true;
    }

    function shiftPlacedAgent(direction)
    {
        var activePlacedAgentPK = f.activePlacedAgentPK.value;

        if (activePlacedAgentPK == "null")
        {
            alert("An Agent In The Hierarchy Must Be Selected");
        }
        else
        {
            f.shiftDirection.value = direction;

            sendTransactionAction("AgentDetailTran", "shiftPlacedAgent", "_self");
        }
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

    function unhighlightRow()
    {
        var tdElement = window.event.srcElement;
        var currentRow = tdElement.parentElement;

        var className = currentRow.className;

        if (currentRow.isSelected != "true")
        {
            if (className == "associated")
            {
                currentRow.style.backgroundColor = "#00BB00";
            }
            else if (className == "highlighted")
            {
                currentRow.style.backgroundColor = "#FFFFCC";
            }
            else if (className == "default")
            {
                currentRow.style.backgroundColor = "#BBBBBB";
            }
            else if (className == "disabled + default")
            {
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
            var trRow = containingTable.rows[i];

            var className = trRow.className;

            if (className == "associated")
            {
                trRow.style.backgroundColor = "#00BB00";
            }
            else
            {
                trRow.style.backgroundColor = "#BBBBBB";
            }

            trRow.isSelected = "false";
        }

        currentRow.style.backgroundColor = "#FFFFCC";

        currentRow.isSelected = "true";
    }

    /**
     * Displays validation selection dialog.
     */
    function showValidateHierarchySelection()
    {
        var width = 0.25 * screen.width;
        var height = 0.25 * screen.height;

        openDialog("hierarchyReport", null, width, height);

        sendTransactionAction("AgentDetailTran", "showValidateHierarchySelection", "hierarchyReport");
    }

    /**
     * Opens a dialog that shows the tree-structure of the select from/to PlacedAgent.
     *
     */
    function showHierarchyReport(movingFromTo)
    {
        var movingFromToPlacedAgentPK = null;

        var reportName = null;

        var action = null;

        if (movingFromTo == "reportTo")
        {
            if (f.activeLowestLevelPlacedAgentPK.value.length == 0 || f.activeLowestLevelPlacedAgentPK.value == "null")
            {
                alert("A 'report-to' Agent must be selected.");

                return false;
            }

            movingFromToPlacedAgentPK = f.activeLowestLevelPlacedAgentPK.value;

            action = "generateHierarchyReportByPlacedAgentPK";

            reportName = "movingFromReport";
        }
        else if (movingFromTo == "activePlacedAgent")
        {
<%--            if (f.activePlacedAgentPK.value.length == 0)--%>
            if (f.activeLowestLevelPlacedAgentPK.value.length == 0 || f.activeLowestLevelPlacedAgentPK.value == "null")
            {
                alert("An 'active' Agent must be selected.");

                return false;
            }

            movingFromToPlacedAgentPK = f.activePlacedAgentPK.value;

            action = "generateHierarchyReportByPlacedAgentPK";

            reportName = "movingToReport";
        }
        else if (movingFromTo == "contractCodeCT")
        {
            if (f.contractCodeCT.value == 0)
            {
                f.contractCodeCT.style.backgroundColor = "#FFFFCC";

                alert("Contract Code Required");

                return false;
            }

            action = "showHierarchyReport";

            reportName = "contractCodeReport";
        }

        f.movingFromToPlacedAgentPK.value = "MOVING:" + movingFromToPlacedAgentPK;

        var width = 0.75 * screen.width;

        var height = 0.75 * screen.height;

        openDialog("",reportName,"left=0,top=0,resizable=no,width=" + width + ",height=" + height, "AgentDetailTran", action, reportName);
    }


    /**
     * Displays validation selection dialog.
     */
    function showValidateHierarchySelection()
    {
        var width = 0.25 * screen.width;
        var height = 0.25 * screen.height;

        openDialog("","validateHierarchySelection","left=0,top=0,resizable=no,width=" + width + ",height=" + height, "AgentDetailTran", "showValidateHierarchySelection", "validateHierarchySelection");
    }

    /**
     * Clears the supplied textField.
     */
    function clearTextField(textField)
    {
        textField.value = "";
    }

    /**
     * Finds the set of AgentContracts for the agentId or lastName/corporateName (never both) under the selected CommissionContract.
     */
    function findAgent()
    {
        if (f.contractCodeCT.value == 0)
        {
            f.contractCodeCT.style.backgroundColor = "#FFFFCC";

            alert("Contract Code Must Be Selected");
        }
        else
        {
            var agentId = f.agentId.value;

            var agentName = f.agentName.value;

            if (agentId.length != 0)
            {
                sendTransactionAction("AgentDetailTran", "findAgentContractByAgentId_AND_ContractCodeCT", "_self");
            }
            else if (agentName.length != 0)
            {
                sendTransactionAction("AgentDetailTran", "findAgentContractByAgentName_AND_ContractCodeCT", "_self");
            }
        }
    }

    /**
     * Finds an agent and its report to(s) for the supplied agentId
     */
    function findReportToAgent()
    {
        if (f.contractCodeCT.value == 0)
        {
            f.contractCodeCT.style.backgroundColor = "#FFFFCC";

            alert("Contract Code Must Be Selected");
        }
        else
        {
            var reportToId = f.reportToId.value;

            var reportToName = f.reportToName.value;

            if (reportToId.length != 0)
            {
                sendTransactionAction("AgentDetailTran", "findReportToAgentByAgentId_AND_ContractCodeCT", "_self");
            }
            else if (reportToName.length != 0)
            {
                sendTransactionAction("AgentDetailTran", "findReportToAgentByAgentName_AND_ContractCodeCT", "_self");
            }
        }
    }

    /**
     * Selects the text of the supplied textField.
     */
    function selectText(textField)
    {
        textField.select();
    }

    /**
     * Returns true if the Enter key has just been clicked.
     */
    function checkForEnter(){

        var eventObj = window.event;

        if (eventObj.keyCode == 13){

            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * Set the activeAgentContract since selecting an AgentContract does not submit the form.
     */
    function setActiveAgentContractPK(tableRow)
    {
        f.activeAgentContractPK.value = tableRow.agentContractPK;
    }

    /**
     * If this screen is a "pop-up" from the Hiearchy tabl of AgentDetail, then refresh that screen to reflect any
     * changes created here.
     */
    function updateAgentHierarchyTab()
    {
        opener.sendTransactionAction("AgentDetailTran", "showPlacedAgentDetail", "contentIFrame");
    }

    /*
     * Closes this dialog
     */
    function closeWindow()
    {
        window.close();
    }

    /**
     * Returns true if the html select element is still on the 0th element.
     */
    function selectElementIsEmpty(theElement)
    {
        var tagName = theElement.tagName;

        var elementIsEmpty = false;

        if (tagName == "SELECT")
        {
            if(theElement.selectedIndex == 0)
            {
                elementIsEmpty = true;
            }
        }
        else
        {
            alert("ERROR! This Javascript Function Does Not Recognize The Element [" + tagName + "]");
        }

        return elementIsEmpty;
    }

</script>

<title>Placed Agent</title>

<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta http-equiv="Cache-Control" content="no-store">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Expires" content="0">

<link href="/PORTAL/common/css/EDITSolutions.css" rel="stylesheet" type="text/css">

<style type="text/css">

    td.box
    {
            border: 1px solid Black;
    }

</style>

</head>


<body class="mainTheme" onLoad="init()" >

<form name="theForm" method="post" action="/PORTAL/servlet/RequestManager">

<table border="0" width="100%" height="100%">

    <tr height="5%">
        <td colspan="3" align="middle" class="box">

            <span class="formData" style="border-style:solid; border-width:0; position:relative; width:100%; height:25%; top:0; left:0; z-index:0; overflow:visible">

                <span class="requiredField">*</span>&nbsp;Contract Code:&nbsp;

                <select name="contractCodeCT" onChange="showAgentContracts(this)">
                    <option name="id" value="0">Please Select</option>
    <%
        if (contractCodeCTs != null)
        {
            for (int i = 0; i < contractCodeCTs.length; i++)
            {
                String contractCodeCT = contractCodeCTs[i].getCode();
                String contractCodeDescription = contractCodeCTs[i].getCodeDesc();

                if ( (activeContractCodeCT != null) && (activeContractCodeCT.equals(contractCodeCT)))
                {
                    out.println("<option selected name=\"id\" value=\"" + contractCodeCT + "\">" + contractCodeCT + " - " + contractCodeDescription + "</option>");
                }
                else
                {
                    out.println("<option name=\"id\" value=\"" + contractCodeCT + "\">" + contractCodeCT + " - " + contractCodeDescription + "</option>");
                }
            }
        }
    %>
                </select>

                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;

                <input name="btnHierarchy" title="Shows all hierarchies for the selected Contract Code" value="report" type="button" style="border-style:solid; border-width:1; background-color: #99CCCC; font-size: 8pt;" onClick="showHierarchyReport('contractCodeCT')">
                &nbsp;
                &nbsp;
                <input name="btnValidate" title="Opens a dialog that offers several hierarchy validation options" value="validate" type="button" style="border-style:solid; border-width:1; background-color: #99CCCC; font-size: 8pt;" onClick="showValidateHierarchySelection()">
            </span>
        </td>
    </tr>
    
    <tr height="1%">
        <td width="20%" nowrap>
            <span class="tableHeading">Agent</span>
        </td>
        <td width="46%" nowrap>
            <span class="tableHeading">Report To Agent
            &nbsp;&nbsp;<input name="btnHierarchyMovingFrom" title="Shows the hierarchy for the selected 'report-to' agent" value="sub-report" type="button" style="border-style:solid; border-width:1; background-color: #99CCCC; font-size: 7pt;" onClick="showHierarchyReport('reportTo')">            
            </span>
        </td>
        <td width="33%" nowrap>
            <span class="tableHeading">Active Agent Hierarchy
            &nbsp;&nbsp;<input name="btnHierarchyMovingTo" title="Shows the hierarchy for the selected 'active-' agent" value="sub-report " type="button" style="border-style:solid; border-width:1; background-color: #99CCCC; font-size: 7pt;" onClick="showHierarchyReport('activePlacedAgent')">                                        
            </span>
        </td>
    </tr>

    <tr height="1%">
        <td width="20%" nowrap>
            #:&nbsp;<input CONTENTEDITABLE="<%= enableAgentId %>" type="text" size="10" name="agentId" value="<%= agentId %>" onFocus="clearTextField(document.theForm.agentName); selectText(this)" onKeyDown="if (checkForEnter()){findAgent()}">
            &nbsp;&nbsp;<font size="1"><i>or</i></font>&nbsp;
            Name:&nbsp;<input "<%= (!enableAgentId)?"DISABLED":"" %>" type="text" size="10" name="agentName" value="<%= agentName %>" onFocus="clearTextField(document.theForm.agentId); selectText(this)" onKeyDown="if (checkForEnter()){findAgent()}">
            <input type="button" value="find" onClick="findAgent()">
        </td>

        <td width="46%" nowrap>
            #:&nbsp;<input type="text" size="10" name="reportToId" value="<%= reportToId %>" onFocus="clearTextField(document.theForm.reportToName); selectText(this)" onKeyDown="if (checkForEnter()){findReportToAgent()}">
            &nbsp;&nbsp;<font size="1"><i>or</i></font>&nbsp;
            Name:&nbsp;<input type="text" size="10" name="reportToName" value="<%= reportToName %>" onFocus="clearTextField(document.theForm.reportToId); selectText(this)" onKeyDown="if (checkForEnter()){findReportToAgent()}">
            <input type="button" value="find" onClick="findReportToAgent()">
        </td>

        <td width="25%" nowrap>
            &nbsp;
        </td>
    </tr>

    <tr>
        <td rowspan="3">
            <!-- Summary Table for AgentContracts  -->

            <table id="agentContractsTable1" class="summary" width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">

                <tr class="heading">
                    <th width="50%">
                        Agent #
                    </th>
                    <th width="50%">
                        Name
                    </th>
                </tr>
                <tr>
                    <td height="100%" colspan="2">
                        <span class="scrollableContent">
                            <table class="summary" width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">

    <%
        if (agentContracts != null)
        {
            // Sort before displaying..

            Map sortedAgentContracts = new TreeMap();

            for (int i = 0; i <agentContracts.length; i++)
            {
                AgentContract currentAgentContract = agentContracts[i];

                ClientRole[] clientRoles = ClientRole.findByAgentFK(currentAgentContract.getAgent().getAgentPK());

                ClientDetail currentClientDetail = clientRoles[0].getClientDetail();

                String lastName = currentClientDetail.getName();

                sortedAgentContracts.put(lastName + currentAgentContract.getAgentContractPK().toString(), currentAgentContract);
            }

            Iterator it = sortedAgentContracts.values().iterator();

            while (it.hasNext())
            {
                AgentContract currentAgentContract = (AgentContract) it.next();

                Agent currentAgent = currentAgentContract.getAgent();
                ClientRole[] clientRoles = ClientRole.findByAgentFK(currentAgent.getAgentPK());

                ClientDetail currentClientDetail = clientRoles[0].getClientDetail();

                String agentContractPK = currentAgentContract.getAgentContractPK().toString();

                String currentAgentId = Util.initString(currentAgent.getAgentNumber(), "&nbsp;");

                String lastName = currentClientDetail.getName();
                                
                String firstName = Util.initString((String) currentClientDetail.getFirstName(), "");
                if (firstName != null && !firstName.equals("")) {
                	firstName = ", " + firstName;
                }

                String className = null;

                String isSelected = null;

                PlacedAgent[] placedAgents = currentAgentContract.getPlacedAgent();

                if (placedAgents != null)
                {
                    for (int j = 0; j < placedAgents.length; j++)
                    {
                        ClientRole clientRole = ClientRole.findByPK(placedAgents[j].getClientRoleFK());
                        currentAgentId = clientRole.getReferenceID();
                        
                        if (agentContractPK.equals(activeAgentContractPK))
                        {
                            className = "highlighted";
    
                            isSelected = "true";
                        }
                        else
                        {
    
                            className = "default";
    
                            isSelected = "false";
                        }
    %>
                                <tr class="<%= className %>" id="<%= agentContractPK %>" agentContractPK="<%= agentContractPK %>" isSelected="<%= isSelected %>" onMouseOver="highlightRow()" onMouseOut="unhighlightRow()" onClick="selectDeselectRow(); setActiveAgentContractPK(this)">
                                    <td width="50%">
                                        <%= currentAgentId %>
                                    </td>
                                    <td width="50%">
                                        <%= lastName %><%= firstName %>
                                    </td>
                                </tr>
    <%
                    }
                }
                else
                {
    %>
                                <tr class="<%= className %>" id="<%= agentContractPK %>" agentContractPK="<%= agentContractPK %>" isSelected="<%= isSelected %>" onMouseOver="highlightRow()" onMouseOut="unhighlightRow()" onClick="selectDeselectRow(); setActiveAgentContractPK(this)">
                                    <td width="50%">
                                        <%= currentAgentId %>
                                    </td>
                                    <td width="50%">
                                        <%= lastName %><%= firstName %>
                                    </td>
                                </tr>
    <%
                }
            }
        }
    %>
                                <tr class="filler"> <!-- A dummy row to help with sizing -->
                                    <td colspan="2">
                                        &nbsp;
                                    </td>
                                </tr>
                            </table>
                        </span>
                    </td>
                </tr>
            </table>
        </td>

        <td rowspan="3">
            <!-- Summary Table for Report To Hierarchies  -->
            <table id="reportToHierarchiesTable" class="summary" width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
                <tr class="heading">
                    <th width="14%">
                        Agent #
                    </th>
                    <th width="14%">
                        Name
                    </th>
                    <th width="14%">
                        Situation
                    </th>
                    <th width="14%">
                        Comm Lvl
                    </th>
                    <th width="14%">
                        Comm Opt
                    </th>
                    <th width="14%">
                        Rprt To Id
                    </th>
                    <th width="14%">
                        Name
                    </th>
                </tr>
                <tr>
                    <td height="99%" colspan="7">
                        <span class="scrollableContent">
                            <table class="summary" width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
    <%
        if (placedAgentBranches != null)
        {
            for (int i = 0; i < placedAgentBranches.length; i++)
            {
                // These are in order of HierarchyLevel ascending - we want the reverse.
                placedAgentBranches[i].sort(PlacedAgentBranch.DESCENDING);

                PlacedAgent[] currentPlacedAgentBranch = placedAgentBranches[i].getPlacedAgents();

                int branchLength = currentPlacedAgentBranch.length;

                PlacedAgent currentPlacedAgent = currentPlacedAgentBranch[0];

                Agent currentAgent = currentPlacedAgent.getAgentContract().getAgent();

                CommissionProfile currentCommissionProfile = currentPlacedAgent.getActivePlacedAgentCommissionProfile().getCommissionProfile();
                ClientDetail currentClientDetail = currentPlacedAgent.getClientRole().getClientDetail();
                
                String placedAgentPK = currentPlacedAgent.getPlacedAgentPK().toString();
                ClientRole clientRole = ClientRole.findByPK(currentPlacedAgent.getClientRoleFK());
                String placedAgentId = Util.initString(clientRole.getReferenceID(), "&nbsp;");
                String placedAgentLastName = currentClientDetail.getName();
                
                String placedAgentFirstName = Util.initString((String) currentClientDetail.getFirstName(), "");
                if (placedAgentFirstName != null && !placedAgentFirstName.equals("")) {
                	placedAgentFirstName = ", " + placedAgentFirstName;
                }
                
                EDITDate currentStopDate = currentPlacedAgent.getStopDate();
                String commissionLevelCT = "&nbsp;";
                String commissionOptionCT = "&nbsp;";
                String currentSituation = Util.initString(currentPlacedAgent.getSituationCode(), "&nbsp;");

                boolean showAsDisabled = showAsDisabled(currentStopDate);

                if (currentCommissionProfile != null)
                {
                    commissionOptionCT = currentCommissionProfile.getCommissionOptionCT();

                    commissionLevelCT = currentCommissionProfile.getCommissionLevelCT();
                }

                PlacedAgent currentReportToPlacedAgent = null;

                ClientDetail currentReportToClientDetail = null;

                String reportToAgentId = "&nbsp;";
                String reportToLastName = "&nbsp;";

                if (branchLength > 1) // There is a report to Agent.
                {
                    currentReportToPlacedAgent = currentPlacedAgentBranch[1];

                    Agent currentReportToAgent = currentReportToPlacedAgent.getAgentContract().getAgent();

                    currentReportToClientDetail = currentReportToPlacedAgent.getClientRole().getClientDetail();

                    ClientRole reportToClientRole = ClientRole.findByPK(currentReportToPlacedAgent.getClientRoleFK());

                    reportToAgentId = Util.initString(reportToClientRole.getReferenceID(), "&nbsp;");

                    reportToLastName = currentReportToClientDetail.getName();
                }

                String className = null;

                String isSelected = null;

                if (placedAgentPK.equals(activeLowestLevelPlacedAgentPK))
                {
                    className = "highlighted";

                    isSelected = "true";
                }
                else
                {
                    className = "default";

                    isSelected = "false";
                }
    %>
                                <tr <%= (showAsDisabled)?"class='disabled + " + className + "'":"class='" + className + "'" %> id="<%= placedAgentPK %>" placedAgentBranchPK="<%= placedAgentPK %>" isSelected="<%= isSelected %>" onMouseOver="highlightRow()" onMouseOut="unhighlightRow()" onClick="showPlacedAgentHierarchy()">
                                    <td width="14%">
                                        <%= placedAgentId %>
                                    </td>
                                    <td width="14%">
                                        <%= placedAgentLastName %><%= placedAgentFirstName %>
                                    </td>
                                    <td width="14%">
                                        <%= currentSituation %>
                                    </td>
                                    <td width="14%">
                                        <%= commissionLevelCT %>
                                    </td>
                                    <td width="14%">
                                        <%= commissionOptionCT %>
                                    </td>
                                    <td width="14%">
                                        <%= reportToAgentId %>
                                    </td>
                                    <td width="14%">
                                        <%= reportToLastName %>
                                    </td>
                                </tr>
    <%
            }
        }
    %>
                                <tr class="filler"> <!-- A dummy row to help with sizing -->
                                    <td colspan="4">
                                        &nbsp;
                                    </td>
                                </tr>
                            </table>
                        </span>
                    </td>
                </tr>
            </table>
        </td>

        <td height="70%">

            <!-- Summary Table for The Placed Agent Hierarchy  -->
            <table class="summary" width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
                <tr class="heading">
                    <th width="25%">
                        Agent #
                    </th>
                    <th width="25%">
                        Name
                    </th>
                    <th width="25%">
                        Comm Lvl
                    </th>
                    <th width="25%">
                        Comm Opt
                    </th>
                </tr>
                <tr>
                    <td height="99%" colspan="4">
                        <span class="scrollableContent">
                            <table class="summary" width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">

    <%
        if (placedAgentBranch != null)
        {
            for (int i = 0; i < placedAgentBranch.getPlacedAgents().length; i++)
            {
                PlacedAgent currentPlacedAgent = placedAgentBranch.getPlacedAgents()[i];

                Agent currentAgent = currentPlacedAgent.getAgentContract().getAgent();

                CommissionProfile currentCommissionProfile = currentPlacedAgent.getActivePlacedAgentCommissionProfile().getCommissionProfile();

                ClientDetail currentClientDetail = currentPlacedAgent.getClientRole().getClientDetail();

                String placedAgentPK = currentPlacedAgent.getPlacedAgentPK().toString();
                ClientRole clientRole = ClientRole.findByPK(currentPlacedAgent.getClientRoleFK());
                String currentAgentId = Util.initString(clientRole.getReferenceID(), "&nbsp;");
                String lastName = currentClientDetail.getName();
                
                String firstName = Util.initString((String) currentClientDetail.getFirstName(), "");
                if (firstName != null && !firstName.equals("")) {
                	firstName = ", " + firstName;
                }
                
                String commissionLevelCT = "&nbsp;";
                String commissionOptionCT = "&nbsp;";
                EDITDate currentStopDate = currentPlacedAgent.getStopDate();

                boolean showAsDisabled = showAsDisabled(currentStopDate);

                if (currentCommissionProfile != null)
                {
                    commissionOptionCT = currentCommissionProfile.getCommissionOptionCT();

                    commissionLevelCT = currentCommissionProfile.getCommissionLevelCT();
                }

                String className = null;

                String isSelected = null;

                if (placedAgentPK.equals(activePlacedAgentPK))
                {
                    className = "highlighted";

                    isSelected = "true";
                }
                else
                {
                    className = "default";

                    isSelected = "false";
                }
    %>
                                <tr <%= (showAsDisabled)?"class='disabled + " + className + "'":"class='" + className + "'" %> id="<%= placedAgentPK %>" placedAgentPK="<%= placedAgentPK %>" isSelected="<%= isSelected %>" onMouseOver="highlightRow()" onMouseOut="unhighlightRow()" onClick="showCommissionProfile()">
                                    <td width="25%">
                                        <%= currentAgentId %>
                                    </td>
                                    <td width="25%">
                                        <%= lastName %><%= firstName %>
                                    </td>
                                    <td width="25%">
                                        <%= commissionLevelCT %>
                                    </td>
                                    <td width="25%">
                                        <%= commissionOptionCT %>
                                    </td>
                                </tr>
    <%
            }
        }
    %>
                                <tr class="filler"> <!-- A dummy row to help with sizing -->
                                    <td colspan="4">
                                        &nbsp;
                                    </td>
                                </tr>
                            </table>
                        </span>
                    </td>
                </tr>
            </table>
        </td>
    </tr>

    <tr>
        <td height="25%">
            <!-- Summary Table for AgentContracts  -->
            <table border="0" style="padding:0px; margin:0px; border-style: solid; border-width:1; border-color:black; position:relative; width:100%; height:100%; top:0; left:0;">
                <tr>
                    <td align="right" width="20%" nowrap>
                        <span class="requiredField">*</span>&nbsp;Start-Date:&nbsp;
                    </td>
                    <td align="left" nowrap>
                    <input type="text" name="startDate" value="<%= (EDITDate.isACandidateDate(startDate))?DateTimeUtil.formatYYYYMMDDToMMDDYYYY(startDate):startDate %>" id="startDate" onBlur="DateFormat(this,this.value,event,true)" onKeyUp="DateFormat(this,this.value,event,false)" maxlength="10" size="10"/>
                    <a href="javascript:show_calendar('f.startDate', f.startDate.value);"><img
                            src="/PORTAL/common/images/calendarIcon.gif" width="16" height="16" border="0"
                            alt="Select a date from the calendar"></a>     
                    </td>
                </tr>
                <tr>
                    <td align="right" nowrap>
                        Situation:&nbsp;
                    </td>
                    <td align="left" nowrap>
                        <input type="text" name="situation" value="<%= situation %>" length="20" maxlength="15">
                    </td>
                </tr>
                <tr>
                    <td align="right" nowrap>
                        Agent Number:&nbsp;
                    </td>
                    <td align="left" nowrap>
                        <input type="text" name="agentNumber" value="<%= agentNumber %>" length="11" maxlength="11">
                    </td>
                </tr>
                <tr>
                    <td align="right" nowrap>
                        <span class="requiredField">*</span>&nbsp;Commission Level:&nbsp;
                    </td>
                    <td align="left" nowrap>
                        <!-- Summary Table for CommissionProfiles  -->
                        <select name="commissionProfilePK">

                            <option name="id" value="">Please Select</option>
    <%
        if (commissionProfiles != null)
        {
            // We need to sort the select list by a means that is not natural with the VOs.
            List listItems = new ArrayList();

            for (int i = 0; i < commissionProfiles.length; i++)
            {
                CommissionProfile currentCommissionProfile = commissionProfiles[i];

                long currentCommissionProfilePK = currentCommissionProfile.getCommissionProfilePK().longValue();
                String commissionLevelCT = currentCommissionProfile.getCommissionLevelCT();
                String commissionOptionCT = currentCommissionProfile.getCommissionOptionCT();

                String listItem = commissionLevelCT + "," + commissionOptionCT + "," + currentCommissionProfilePK;

                listItems.add(listItem);
            }

            Collections.sort(listItems);

            int listItemsCount = listItems.size();

            for (int i = 0; i < listItemsCount; i++)
            {
                String listItemLine = (String) listItems.get(i);

                String[] listTokens = Util.fastTokenizer(listItemLine, ",");

                String commissionLevel = listTokens[0];
                String commissionOption = listTokens[1];
                long currentCommissionProfilePK = Long.parseLong(listTokens[2]);

                if (commissionProfilePK == currentCommissionProfilePK)
                {
                    out.println("<option selected name=\"id\" value=\"" + currentCommissionProfilePK + "\">" + commissionLevel + " - " + commissionOption +  "</option>");
                }
                else
                {
                    out.println("<option name=\"id\" value=\"" + currentCommissionProfilePK + "\">" + commissionLevel + " - " + commissionOption +  "</option>");
                }
            }
        }
    %>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td colspan="2">
                      <br>
                      <span class="requiredField">*</span>&nbsp;<font face="" style="font:italic normal; font-size: xx-small">required</font>
                    </td>
                </tr>
            </table>
        </td>
    </tr>
    <tr>
        <td height="5%">
            <table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td width="33.33%" align="left" valign="bottom">
                        &nbsp;&nbsp;&nbsp;&nbsp;<input name="clear" value="Clear" type="button" onClick="clearPlacedAgents()">
                    </td>

                    <td width="33.33%" align="middle" valign="bottom">
                        <input name="btnLeft" value="  &#8592;  " type="button" style="border-style:solid; border-width:1; background-color: #99CCCC; font-size: 11pt;" onClick="removePlacedAgent()">
                        &nbsp;&nbsp;
                        <input name="btnRight" value="  &#8594;  " type="button" style="border-style:solid; border-width:1; background-color: #99CCCC; font-size: 11pt;" onClick="placeAgentContract()">&nbsp;&nbsp;&nbsp;
                    </td>

                    <td width="33.33%" align="right" valign="bottom">

                        <script>
                            if (opener)
                            {
                            document.write("<input type='button' value='Close' onClick='updateAgentHierarchyTab(); closeWindow()'>");
                            }
                            else
                            {
                            document.write("&nbsp;");
                            }
                        </script>
                            &nbsp;&nbsp;&nbsp;&nbsp;
                    </td>
                </tr>
            </table>
        </td>
    </tr>
</table>

<input type="hidden" name="transaction" value="">
<input type="hidden" name="action" value="">

<input type="hidden" name="activeContractCodeCT" value="<%= activeContractCodeCT %>">
<input type="hidden" name="activeAgentContractPK" value="<%= activeAgentContractPK %>">
<input type="hidden" name="activeLowestLevelPlacedAgentPK" value="<%= activeLowestLevelPlacedAgentPK %>">
<input type="hidden" name="activePlacedAgentPK" value="<%= activePlacedAgentPK %>">
<input type="hidden" name="shiftDirection" value="null">
<input type="hidden" name="showGroupPlacedAgentPK" value="<%= showGroupPlacedAgentPK %>">
<input type="hidden" name="movingFromToPlacedAgentPK" value=""/>

<%--The Placed Agent Hierachy Page needs to know if it should disable editing of the agentId. --%>
<input type="hidden" name="requestSource" value="<%= requestSource %>">


</form>

</body>
</html>
