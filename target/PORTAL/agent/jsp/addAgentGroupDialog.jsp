<%@ taglib uri="/WEB-INF/taglibs-input.tld" prefix="input" %>
<%@ page import="edit.portal.widgettoolkit.TableModel" %>
<title>Add Agent Group</title>
<script type="text/javascript" xml:space="preserve">

    /*
    Find a target Agent for a new AgentGroup.
    */
    function searchAgentForAgentGroup()
    {
        if (!valueIsEmpty(window.event.srcElement.value))
        {
            sendTransactionAction("AgentDetailTran", "searchAgentForAgentGroup", "_self");
        }
        else
        {
            alert("Search Value Required");
        }
    }

    /*
    Callback method from the widgetFunctions.js when double clicking a row. There, we will load the selected Agent
    as a potential AgentGroup.
    */
    function onTableRowDoubleClick(tableId)
    {
        var agentPK = getSelectedRowId("AddAgentGroupTableModel");

        f.agentPK.value = agentPK;

        sendTransactionAction("AgentDetailTran", "loadAgentForAgentGroupAdd", window.opener.name);

        closeWindow();
    }

</script>
<table class="formData" cellspacing="0" cellpadding="5" border="0" width="100%" style="height:2%">
    <tr>
        <td width="20%" align="right">Agent #:</td>
        <td width="20%" align="left">
            <input:text name="dialogAgentNumber"
                        attributesText="onFocus=f.dialogAgentName.value='' onKeyPress='if (enterKeyPressed()){searchAgentForAgentGroup()}'"/>
        </td>
        <td width="20%" align="right">Agent Name:</td>
        <td width="20%" align="left">
            <input:text name="dialogAgentName"
                        attributesText="onFocus=f.dialogAgentNumber.value='' onKeyPress='if (enterKeyPressed()){searchAgentForAgentGroup()}'"/>
        </td>
        <td width="20%" align="right">
            <input type="button" value="Search" onClick="searchAgentForAgentGroup()"/>
        </td>
    </tr>
</table>
<br>
<jsp:include page="/common/jsp/widget/tableWidget.jsp" flush="true">
    <jsp:param name="tableId" value="AddAgentGroupTableModel"/>
    <jsp:param name="tableHeight" value="84"/>
    <jsp:param name="multipleRowSelect" value="false"/>
    <jsp:param name="singleOrDoubleClick" value="<%= TableModel.PROPERTY_SINGLE_OR_DOUBLE_CLICK_VALUE_DOUBLE %>"/>
</jsp:include>

<input:hidden name="agentPK"/>
