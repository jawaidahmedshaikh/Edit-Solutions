<%-- the static segment can be included to JSP using the <%@include ...%> directive, e.g:
    <%@include file="/WEB-INF/jspf/header.jspf" %>

    J2EE1.4 also enables to specify header or footer segments for multiple pages in web.xml file.
    Use the <include-prelude> or <include-coda> elements in <jsp-config> section, e.g.:
    <jsp-config>
      <jsp-property-group>
        <url-pattern>*.jsp</url-pattern>
        <include-prelude>/WEB-INF/jspf/header.jspf</include-prelude>
      </jsp-property-group>
    </jsp-config>
--%>
<%-- any content can be specified here e.g.: --%>

<!-- force IE into quirks mode -->
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<%@ taglib uri="/WEB-INF/taglibs-input.tld" prefix="input" %>
<%@ page import="edit.portal.widgettoolkit.*, edit.portal.taglib.*, edit.common.*, edit.common.vo.*, agent.CommissionProfile, fission.utility.Util" %>

<%
    // ContractCodeCT <select>
    CodeTableVO[] contractCodeVOCTs = CodeTableWrapper.getSingleton().getCodeTableEntries("CONTRACTCODE");
    InputSelect contractCodeCTs = new InputSelect(contractCodeVOCTs, CodeTableVO.class, new String[]{"codeDesc"}, "code", InputSelect.ASCENDING);
    
    // StopDateReasonCT <select>
    CodeTableVO[] stopDateReasonVOCTs = CodeTableWrapper.getSingleton().getCodeTableEntries("STOPDATEREASON");
    InputSelect stopDateReasonCTs = new InputSelect(stopDateReasonVOCTs, CodeTableVO.class, new String[]{"codeDesc"}, "code", InputSelect.ASCENDING);
    
    // CommissionLevels
    CommissionProfile[] commissionProfiles = CommissionProfile.find_All();
    InputSelect commissionProfileValues = new InputSelect(commissionProfiles, CommissionProfile.class, new String[]{"commissionLevelCT", "commissionOptionCT"}, "commissionProfilePK", InputSelect.ASCENDING);
    
%>

<style type="text/css">
    
th.box 
{
	border: 1px solid Black;
}    

</style>

<script language="javascript" type="text/javascript">
    
    function init()
    {

    }
    
    /**
     * The sendTransactionAction is an included method. We need to do some visual edits before and after
     * the form submission.
     */
    function sendTransactionActionInterceptor(transaction, action, targetWindow)
    {
        disableHierachyButtons();
    
        sendTransactionAction(transaction, action, targetWindow);
        
        enableHierarchyButtons();
    }
    
    /** 
     * Physically and visually disables all actiion buttons to prevent any form
     * of double submission.
     */
    function disableHierachyButtons()
    {
        f.btnHierarchy.style.backgroundColor = "#99BBBB";
        f.btnHierarchyMovingFrom.style.backgroundColor = "#99BBBB";
        f.btnHierarchyMovingTo.style.backgroundColor = "#99BBBB";
        f.btnValidate.style.backgroundColor = "#99BBBB";     
   
        f.btnHierarchy.disabled = true;
        f.btnHierarchyMovingFrom.disabled = true;
        f.btnHierarchyMovingTo.disabled = true;        
        f.btnValidate.disabled = true;        
        
        f.btnGroupMove.disabled = true;

        f.startDate.contentEditable = false;
        f.situationCode.contentEditable = false;
    }
    
    /** 
     * Physically and visually enables all action buttons.
     */    
    function enableHierarchyButtons()
    {
        f.btnHierarchy.style.backgroundColor = "#99CCCC";
        f.btnHierarchyMovingFrom.style.backgroundColor = "#99CCCC";
        f.btnHierarchyMovingTo.style.backgroundColor = "#99CCCC";
        f.btnValidate.style.backgroundColor = "#99CCCC";        

        f.btnHierarchy.disabled = false;
        f.btnHierarchyMovingFrom.disabled = false;
        f.btnHierarchyMovingTo.disabled = false;        
        f.btnValidate.disabled = false;          

        f.btnGroupMove.disabled = false;
        
        f.startDate.contentEditable = true;
        f.situationCode.contentEditable = true;    
    }    
    
    /**
     * Displays validation selection dialog.
     */
    function showValidateHierarchySelection()
    {
        var width = 0.25 * screen.width;
        var height = 0.25 * screen.height;

        openDialog("validateHierarchyReport2", null, width, height);

        sendTransactionActionInterceptor("AgentDetailTran", "showValidateHierarchySelection", "validateHierarchyReport2");    
    }    
    
    /**
     * Opens a dialog that shows the tree-structure of the select from/to PlacedAgent.
     *
     */
    function showHierarchyReport(movingFromTo)
    {
        var movingFromToPlacedAgentPK = null;
        
        var action = null;
        
        var reportName = null;
    
        if (movingFromTo == "movingFrom")
        {
            var selectedMoveFromPlacedAgent = getSelectedRowId("AgentMoveFromTableModel");
            
            if (valueIsEmpty(selectedMoveFromPlacedAgent))
            {
                alert("A 'moving-from' Agent must be selected.");

                return false;
            }
            
            movingFromToPlacedAgentPK = selectedMoveFromPlacedAgent;
            
            action = "generateHierarchyReportByPlacedAgentPK";
            
            reportName = "moveFromReport2";
        }
        else if (movingFromTo == "movingTo")
        {
            var selectedMoveToPlacedAgent = getSelectedRowId("AgentMoveToTableModel");
            
            if (valueIsEmpty(selectedMoveToPlacedAgent))
            {
                alert("A 'moving-to' Agent must be selected.");

                return false;
            }            
            
            movingFromToPlacedAgentPK = selectedMoveToPlacedAgent;
            
            action = "generateHierarchyReportByPlacedAgentPK";
            
            reportName = "movingToReport2";
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
            
            reportName = "contractCodeReport2";
        }
        
        f.movingFromToPlacedAgentPK.value = movingFromToPlacedAgentPK;
        
        
        var width = 0.75 * screen.width;
        var height = 0.75 * screen.height;

        openDialog(reportName, null, width, height);

        sendTransactionActionInterceptor("AgentDetailTran", action, reportName);
    }
    
    /**
     * Moves the "from" PlacedAgent and its child PlacedAgents to
     * the selected "to" PlacedAgent. Required fields are evaluated here
     * since only the Base system currently supports selective REQUIRED field
     * evaluation(s).
     */
    function moveAgentGroup()
    {
        if (requiredMoveFieldsPresent())
        {
            sendTransactionActionInterceptor("AgentDetailTran", "moveAgentGroup", "template-toolbar-main");
        }
    }
    
    /**
     * Callback method from the tableWidget.
     */
    function onTableRowSingleClick(tableId)
    {
        if (tableId == "AgentMoveFromTableModel")
        {
            findMovingFromToPlacedAgent("movingFrom");
        }
        else if (tableId = "AgentMoveToTableModel")
        {
            findMovingFromToPlacedAgent("movingTo");
        }
    }
    
    /**
     * A moving-from, report-to, startDate, stopDateReasonCT, and commissionLevel must be specified at
     * a minimum to do an agent move.
     */
    function requiredMoveFieldsPresent()
    {
        // verify moving-from and report-to agents have been selected.
        var selectedMoveFromPlacedAgent = getSelectedRowId("AgentMoveFromTableModel");    
        
        var selectedMoveToPlacedAgent = getSelectedRowId("AgentMoveToTableModel");
                
        if (valueIsEmpty(selectedMoveFromPlacedAgent) || valueIsEmpty(selectedMoveToPlacedAgent))
        {
            alert("A 'moving-from' and 'report-to' Agent must be selected.");
            
            return false;
        }
        
        // verify that startDate, stopDateReasonCT, and commissionLevels are specified
        var startDate = f.startDate;
        
        var stopDateReasonCT = f.stopDateReasonCT;
        
        var commissionProfilePK = f.commissionProfilePK;
        
        var requiredFieldMissing = false;
        
        if (textElementIsEmpty(startDate))
        {
            startDate.style.backgroundColor = "#FFFFCC";
            
            requiredFieldMissing = true;
        }
        
        if (selectElementIsEmpty(stopDateReasonCT))
        {
            stopDateReasonCT.style.backgroundColor = "#FFFFCC";
            
            requiredFieldMissing = true;
        }
        
        if (selectElementIsEmpty(commissionProfilePK))
        {
            commissionProfilePK.style.backgroundColor = "#FFFFCC";
            
            requiredFieldMissing = true;
        }
        
        if (requiredFieldMissing)
        {
            alert("Stop Date, Stop Date Reason, and Commission Level are required fields.");
        }
        
        return !requiredFieldMissing;
    }
    
    /**
     * Resets this page.
     */
    function clearAgentMove()
    {
        resetForm();    
    
        sendTransactionActionInterceptor("AgentDetailTran", "clearAgentMove", "template-toolbar-main");      
    }
    
    /**
    * Clears the text of the specified Textfield.
    */ 
    function clearText(textfieldId)
    {
    clearTextElement(document.getElementById(textfieldId));
    }

    /**
    * Finds the moving-from PlacedAgent from (either) the Agent # or the Agent Name.
    */
    function findMovingFromToPlacedAgent(movingFromTo)
    {
        if (requiredSearchFieldsPresent(movingFromTo) && validateForm(f))
        {
            var selectedRowId = getSelectedRowId("AgentMoveFromTableModel");

            sendTransactionActionInterceptor("AgentDetailTran", "findMovingFromToPlacedAgent", "template-toolbar-main");      
        }
    }
     
    /**
    * When searching, the AgentNumber or AgentName is required for either the move-from or move-to fields.
    */
    function requiredSearchFieldsPresent(movingFromTo)
    {
        var requiredFieldsExist = true;
    
        // Check Moving-From fields.
        if (movingFromTo == "movingFrom")
        {
            if (textElementIsEmpty(document.all.movingFromAgentNumber) && textElementIsEmpty(document.all.movingFromAgentName))
            {
                requiredFieldsExist = false;

                document.all.movingFromAgentNumber.style.backgroundColor = "#FFFFCC";

                document.all.movingFromAgentName.style.backgroundColor = "#FFFFCC";

                document.all.movingFromAgentNumber.focus();
            }
        }
        
        // Check Moving-To fields.
        if (movingFromTo == "movingTo")
        {
            if (textElementIsEmpty(document.all.movingToAgentNumber) && textElementIsEmpty(document.all.movingToAgentName))
            {
                requiredFieldsExist = false;

                document.all.movingToAgentNumber.style.backgroundColor = "#FFFFCC";

                document.all.movingToAgentName.style.backgroundColor = "#FFFFCC";

                document.all.movingToAgentNumber.focus();
            }
        }
        
        if (!requiredFieldsExist)
        {
            alert("When searching, an Agent # or Agent Name is required.");        
        }
        
        return requiredFieldsExist;
    }
  
    
</script>

<table border="0" width="100%" height="15%" cellspacing="0">
    <thead>
        <tr height="5%">
            <th colspan="3" class="box">Contract Code: <input:select name="contractCodeCT" options="<%= contractCodeCTs.getOptions() %>" attributesText="id='contractCodeCT' REQUIRED"/>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                <input name="btnHierarchy" title="Shows all hierarchies for the selected Contract Code" value="report" type="button" style="border-style:solid; border-width:1; background-color: #99CCCC; font-size: 8pt;" onClick="showHierarchyReport('contractCodeCT')">
                &nbsp;
                &nbsp;
                <input name="btnValidate" title="Opens a dialog that offers several hierarchy validation options" value="validate" type="button" style="border-style:solid; border-width:1; background-color: #99CCCC; font-size: 8pt;" onClick="showValidateHierarchySelection()">                        
            </th>
        </tr>
    </thead>
    <tbody>
        <tr height="5%">
            <td>
                <span class="tableHeading">Moving Agent
                    &nbsp;&nbsp;<input name="btnHierarchyMovingFrom" title="Shows the hierarchy for the selected 'moving-from' agent" value="sub-report" type="button" style="border-style:solid; border-width:1; background-color: #99CCCC; font-size: 7pt;" onClick="showHierarchyReport('movingFrom')">
                </span>
            </td>
            <td>
                <span class="tableHeading">New 'Report To' Agent
                    &nbsp;&nbsp;<input name="btnHierarchyMovingTo" title="Shows the hierarchy for the selected 'moving-' agent" value="sub-report " type="button" style="border-style:solid; border-width:1; background-color: #99CCCC; font-size: 7pt;" onClick="showHierarchyReport('movingTo')">                            
                </span>
            </td>
            <td><span class="tableHeading">Active Agent Hierarchy</span></td>
        </tr>    
        <tr height="5%">
            <td align="left">
                #:&nbsp;<input:text name="movingFromAgentNumber" bean="" size="10" attributesText="id='movingFromAgentNumber' onFocus=\"clearText('movingFromAgentName')\" onKeyPress=\"if (enterKeyPressed()){findMovingFromToPlacedAgent()}\""/>
                &nbsp;&nbsp;<font size="1"><i>or</i></font>&nbsp;
                Name: <input:text name="movingFromAgentName" bean="" size="10" attributesText="id='movingFromAgentName' onFocus=\"clearText('movingFromAgentNumber')\" onKeyPress=\"if (enterKeyPressed()){findMovingFromToPlacedAgent()}\""/>
                <input type="button" value="find" onClick="findMovingFromToPlacedAgent('movingFrom')">
                &nbsp;
                
            </td>
            <td align="left">
                #:&nbsp;<input:text name="movingToAgentNumber" bean="" size="10" attributesText="id='movingToAgentNumber' onFocus=\"clearText('movingToAgentName')\" onKeyPress=\"if (enterKeyPressed()){findMovingFromToPlacedAgent()}\""/>
                &nbsp;&nbsp;<font size="1"><i>or</i></font>&nbsp;
                Name: <input:text name="movingToAgentName" bean="" size="10" attributesText="id='movingToAgentName' onFocus=\"clearText('movingToAgentNumber')\" onKeyPress=\"if (enterKeyPressed()){findMovingFromToPlacedAgent()}\""/>
                <input type="button" value="find" onClick="findMovingFromToPlacedAgent('movingTo')">   
                &nbsp;
            </td>
            <td>&nbsp;</td>
        </tr>
    </tbody>
</table>

<span style="vertical-align:top; position:relative; width:33%; height:83%; padding-left:2px">            
    <jsp:include page="/common/jsp/widget/tableWidget.jsp" flush="true">
        <jsp:param name="tableId" value="AgentMoveFromTableModel"/>
        <jsp:param name="tableHeight" value="100"/> 
        <jsp:param name="multipleRowSelect" value="false"/>
        <jsp:param name="singleOrDoubleClick" value="<%= TableModel.PROPERTY_SINGLE_OR_DOUBLE_CLICK_VALUE_SINGLE %>"/>
    </jsp:include>
</span>

<span style="vertical-align:top; position: relative; width:33%; height:83%; padding-left:2px">            
    <jsp:include page="/common/jsp/widget/tableWidget.jsp" flush="true">
        <jsp:param name="tableId" value="AgentMoveToTableModel"/>
        <jsp:param name="tableHeight" value="100"/> 
        <jsp:param name="multipleRowSelect" value="false"/>
        <jsp:param name="singleOrDoubleClick" value="<%= TableModel.PROPERTY_SINGLE_OR_DOUBLE_CLICK_VALUE_SINGLE %>"/>
    </jsp:include>
</span>  

<span style="vertical-align:top; position: relative; width:34%; height:83%; padding-left:2px">            
    <jsp:include page="/common/jsp/widget/tableWidget.jsp" flush="true">
        <jsp:param name="tableId" value="AgentMoveSelectedTableModel"/>
        <jsp:param name="tableHeight" value="40"/> 
        <jsp:param name="multipleRowSelect" value="false"/>
        <jsp:param name="singleOrDoubleClick" value="<%= TableModel.PROPERTY_SINGLE_OR_DOUBLE_CLICK_VALUE_NONE %>"/>
    </jsp:include>
    
    <br>
    
    <table border="0" style="padding:0px; margin:0px; border-style: solid; border-width:1; border-color:black; position:relative; width:100%; height:30%; top:0; left:0;">
        <tbody>
            <tr>
                <td width="10%" nowrap align="right"><span class="requiredField">*</span>&nbsp;Start Date:&nbsp;</td>
                <td nowrap align="left">
                    <input:text name="startDate" attributesText="id='startDate' onBlur='DateFormat(this,this.value,event,true)' onKeyUp='DateFormat(this,this.value,event,false)' maxlength='10' size='10'"/>
                    <a href="javascript:show_calendar('f.startDate', f.startDate.value);"><img
                            src="/PORTAL/common/images/calendarIcon.gif" width="16" height="16" border="0"
                            alt="Select a date from the calendar"></a>                
                </td>
            </tr>
            <tr>
                <td nowrap align="right">Situation:&nbsp;</td>
                <td nowrap align="left"><input:text name="situationCode" bean="" size="10" attributesText="id='situationCode'"/></td>
            </tr>
            <tr>
                <td nowrap align="right"><span class="requiredField">*</span>&nbsp;Stop Reason Code:&nbsp;</td>
                <td nowrap align="left"><input:select name="stopDateReasonCT" options="<%= stopDateReasonCTs.getOptions() %>" attributesText="id='stopDateReasonCT'"/></th></td>
            </tr>
            <tr>
                <td nowrap align="right"><span class="requiredField">*</span>&nbsp;Commission Level:&nbsp;</td>
                <td nowrap align="left"><input:select name="commissionProfilePK" options="<%= commissionProfileValues.getOptions() %>" attributesText="id='commissionProfilePK'"/></td>
            </tr>
            <tr>
                <td nowrap align="top" width="100%" colspan="2">
                    <span class="requiredField">*</span>&nbsp;<font face="" style="font:italic normal; font-size: xx-small">required</font>
                </td>
            </tr>            
        </tbody>
    </table>
        
    <br><br>
    
    <table border="0" style="vertical-align: bottom; padding:0px; margin:0px; border-style: solid; border-width:0; border-color:black; position:relative; width:100%; height:20%; top:0; left:0;">
        <tbody>
            <tr>
                <td nowrap align="right"  valign="bottom" width="50%"><input type="button" value="Clear" onClick="clearAgentMove()"></td>
                <td nowrap align="left"  valign="bottom" width="50%"><input type="button" name="btnGroupMove" value="Group Move" onClick="moveAgentGroup()"></td>
            </tr>
        </tbody>
    </table>    
</span>            

<input type="hidden" name="movingFromToPlacedAgentPK" value=""/>
   

