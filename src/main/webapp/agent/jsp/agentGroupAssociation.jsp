<%@ taglib uri="/WEB-INF/taglibs-input.tld" prefix="input" %>
<%@ page import="edit.portal.widgettoolkit.TableModel" %>
<%@ page import="fission.utility.Util" %>
<jsp:useBean id="agentGroup" class="agent.AgentGroup" scope="request"/>
<jsp:useBean id="agent1" class="agent.Agent" scope="request"/>

<%
    Long agentGroupPK = Util.initLong(request.getParameter("agentGroupPK"), null);
%>

<script language="javascript" type="text/javascript">

    /*
    Initializes this page after reloading (after resizing)
    */
    function initAgentGroupAssociation()
    {
        // Blank-out the agentName and agentValue if both are present. This is a
        // side-effect of the Jakarta input tags.
        if (!valueIsEmpty(f.agentName.value) & !valueIsEmpty(f.agentNumber.value))
        {
            f.agentName.value = "";

            f.agentNumber.value = "";
        }
    }

    /*
        Returns to the AgentGroup page.
    */
    function showAgentGroup()
    {
        sendTransactionAction("AgentDetailTran", "showAgentGroup", "_self");
    }

    /*
    Searches for PlacedAgents as candidates for AgentGroupAssocations.
    */
    function searchPlacedAgentsForAgentGroupAssociation()
    {
        sendTransactionAction("AgentDetailTran", "searchPlacedAgentsForAgentGroupAssociation", "_self");
    }

    /*
    Associates the selected PlacedAgent to the currently selected AgentGroup.
    */
    function createAgentGroupAssociation()
    {
        var selectedPlacedAgentPK = getSelectedRowId("PlacedAgentReportToTableModel");

        if (valueIsEmpty(selectedPlacedAgentPK))
        {
            alert("A PlacedAgent Must Be Selected");
        }
        else
        {
            var requiredFieldsExist = validateForm(f);

            if (requiredFieldsExist)
            {
                sendTransactionAction("AgentDetailTran", "createAgentGroupAssociation", "_self");
            }
        }
    }

    /*
    Removes the association between an AgentGroup and a PlacedAgent.
    */
    function deleteAgentGroupAssociation()
    {
        var selectedAgentGroupAssociationPK = getSelectedRowId("AgentGroupAssociationTableModel");

        if (valueIsEmpty(selectedAgentGroupAssociationPK))
        {
            alert("An AgentGroupAssociation Must Be Selected");
        }
        else
        {
            if (confirm("Delete AgentGroupAssocation?"))
            {
                sendTransactionAction("AgentDetailTran", "deleteAgentGroupAssociation", "_self");
            }
        }
    }

    /*
    Displays the AgentGroupAssociation dialog to modify the attributes.
    */
    function showAgentGroupAssociationDialog()
    {
        var width = 0.35 * screen.width;
        var height = 0.20 * screen.height;

        var selectedAgentGroupAssociationPK = getSelectedRowId("AgentGroupAssociationTableModel");

        if (valueIsEmpty(selectedAgentGroupAssociationPK))
        {
            alert("An AgentGroupAssociation Must Be Selected");
        }
        else
        {
            openDialog("agentGroupAssociationDialog", "top=0, left=0, resizable=no", width, height);

            sendTransactionAction("AgentDetailTran", "showAgentGroupAssociationDialog", "agentGroupAssociationDialog");
        }
    }

</script>

<table class="formData" cellspacing="0" cellpadding="5" border="0" width="100%" height="10%">
    <tr style="font-style:italic; color:rgb(99,99,99);">
        <td nowrap="nowrap" width="16.7%" align="right">Contract Code:</td>
        <td align="left" nowrap="nowrap" width="16.7%">
            <%= Util.initString(agentGroup.getContractCodeCT(), "") %>
        </td>
        <td align="right" nowrap="nowrap" width="16.7%">Agent Name:</td>
        <td align="left" nowrap="nowrap" width="16.7%">
            <%= Util.initString(agent1.getAgentName(), "") %>
        </td>
        <td align="right" nowrap="nowrap" width="16.7%">Type:</td>
        <td nowrap="nowrap" width="16.7%" align="left">
            <%= Util.initString(agentGroup.getAgentGroupTypeCT(), "") %>
        </td>
    </tr>
    <tr>
        <td width="16.7%" align="left" nowrap="nowrap"
            valign="bottom" colspan="5">Agent #:&nbsp;
            <input:text name="agentNumber"
                        attributesText="onFocus=f.agentName.value=\'\' maxlength=\'11\' size=\'11\' onKeyPress=\'if (enterKeyPressed()){searchPlacedAgentsForAgentGroupAssociation()}\' NORESET"/>
            &nbsp;&nbsp;&nbsp;&nbsp;Agent Name:&nbsp;
            <input:text name="agentName"
                        attributesText="onFocus=f.agentNumber.value=\'\' maxlength=\'25 size=\'11\' onKeyPress=\'if (enterKeyPressed()){searchPlacedAgentsForAgentGroupAssociation()}\' NORESET"/>
            <input type="button" value="Find" align="right"
                   onclick="searchPlacedAgentsForAgentGroupAssociation()"/>
        </td>
        <td width="16.7%">
            &nbsp;
        </td>
    </tr>
</table>

<table cellspacing="0" cellpadding="0" border="0" width="100%" height="2%">
    <tr>
        <th width="50%" align="center" valign="bottom">
            <span class="tableHeading">Placed Agents/Report-To</span>
        </th>
        <th width="50%" align="center" valign="bottom">
            <span class="tableHeading">Active Associated Agents</span>
        </th>
    </tr>
</table>


<table cellspacing="0" cellpadding="0" border="0" width="100%" height="81%">
    <tr>
        <td width="100%" height="100%" nowrap="nowrap">
        <span style="position:relative; left:0; width:50%; height:100%; padding-right:2px">
            <jsp:include page="/common/jsp/widget/tableWidget.jsp" flush="true">
                <jsp:param name="tableId" value="PlacedAgentReportToTableModel"/>
                <jsp:param name="tableHeight" value="100"/>
                <jsp:param name="multipleRowSelect" value="false"/>
                <jsp:param name="singleOrDoubleClick"
                           value="<%= TableModel.PROPERTY_SINGLE_OR_DOUBLE_CLICK_VALUE_SINGLE %>"/>
            </jsp:include>
        </span>
        <span style="position: relative; width:50%; height:100%; padding-left:2px">
            <jsp:include page="/common/jsp/widget/tableWidget.jsp" flush="true">
                <jsp:param name="tableId" value="AgentGroupAssociationTableModel"/>
                <jsp:param name="tableHeight" value="100"/>
                <jsp:param name="multipleRowSelect" value="false"/>
                <jsp:param name="singleOrDoubleClick"
                           value="<%= TableModel.PROPERTY_SINGLE_OR_DOUBLE_CLICK_VALUE_SINGLE %>"/>
            </jsp:include>
        </span>
        </td>
    </tr>
</table>

<table cellspacing="0" height="5%" cellpadding="5" border="0" width="100%">
    <tr valign="bottom">
        <td width="20%" align="right"><span class="requiredField">*</span>&nbsp;Start Date:&nbsp;</td>
        <td width="20%" align="left">
            <input:text name="startDate"
                        attributesText="id='startDate' onBlur='DateFormat(this,this.value,event,true)' onKeyUp='DateFormat(this,this.value,event,false)' maxlength='10' size='10' REQUIRED"/>
            <a href="javascript:show_calendar('f.startDate', f.startDate.value);"><img
                    src="/PORTAL/common/images/calendarIcon.gif" width="16" height="16" border="0"
                    alt="Select a date from the calendar"></a>
        </td>
        <td width="20%" align="center">
            <input type="button" value="  &#8592;  " name="btnLeft" onClick="deleteAgentGroupAssociation()"/>
            <input type="button" name="btnRight" value="  &#8594;  " onClick="createAgentGroupAssociation()"/>
        </td>
        <td width="20%" align="right">
            <input type="button" name="modifyAgentGroupAssociation" value="Modify AG Assoc." onClick="showAgentGroupAssociationDialog()"/>
        </td>
        <td width="20%" align="right">
            <a class="textlink1" href="javascript:showAgentGroup()">Agent Group</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        </td>
    </tr>
</table>


<input:hidden name="selectedRowIds_AgentGroupTableModel" default="<%= agentGroupPK.toString() %>"/>
<input:hidden name="agentGroupPK" default="<%= agentGroupPK.toString() %>" attributesText="NORESET"/>

<script language="javascript" type="text/javascript">

    window.onfocus = initAgentGroupAssociation;
</script>