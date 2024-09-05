<%@ taglib uri="/WEB-INF/taglibs-input.tld" prefix="input" %>
<%@ page import="agent.CommissionProfile,
                 fission.utility.*" %>
<%@ page import="edit.common.CodeTableWrapper" %>
<%@ page import="edit.common.EDITDate" %>
<%@ page import="edit.common.vo.CodeTableVO" %>
<%@ page import="edit.portal.taglib.InputSelect" %>
<%@ page import="edit.portal.widgettoolkit.TableModel" %>
<%@ page import="edit.services.db.hibernate.SessionHelper"%>
<%@ page contentType="text/html;charset=windows-1252" %>
<jsp:useBean id="agent1" class="agent.Agent" scope="request"/>
<jsp:useBean id="agentGroup" class="agent.AgentGroup" scope="request"/>
<jsp:useBean id="commissionProfile" class="agent.CommissionProfile" scope="request"/>
<% 
    // AgentGroupTypeCT <select>
    CodeTableVO[] agentGroupTypeVOCTs = CodeTableWrapper.getSingleton().getCodeTableEntries("AGENTGROUPTYPE");
    InputSelect agentGroupTypeCTs = new InputSelect(agentGroupTypeVOCTs, CodeTableVO.class, new String[]{"codeDesc"}, "code", InputSelect.ASCENDING);

    // ContractCodeCT <select>
    CodeTableVO[] contractCodeVOCTs = CodeTableWrapper.getSingleton().getCodeTableEntries("CONTRACTCODE");
    InputSelect contractCodeCTs = new InputSelect(contractCodeVOCTs, CodeTableVO.class, new String[]{"codeDesc"}, "code", InputSelect.ASCENDING);

    // CommissionProfile <select>s.
    CommissionProfile[] commissionProfiles = CommissionProfile.find_All();
    InputSelect commissionProfileSelect = new InputSelect(commissionProfiles, CommissionProfile.class, new String[]{"commissionLevelCT", "commissionOptionCT"}, "commissionProfilePK", InputSelect.ASCENDING);
%>

<script language="javascript" type="text/javascript">

/*
Brings up the list of participating PlacedAgents.
*/
function showBrokerDealerParticipants()
{
    sendTransactionAction("AgentDetailTran", "showBrokerDealerParticipants", "main");
}

/*
Prepares page for adding a new AgentGroup.
*/
function addAgentGroup()
{
    resetForm();

    selectUnselectAllRowsInTable("AgentGroupTableModel", false);

    var width = 0.90 * getScreenWidth();

    var height = 0.50 * getScreenHeight();

    openDialog("addAgentGroupDialog", "top=0,left=0,resizable=no", width, height);

    sendTransactionAction("AgentDetailTran", "addAgentGroup", "addAgentGroupDialog");
}

/*
Saves/Updates the Agent Group information.
*/
function saveAgentGroup()
{
    var requiredFieldsExist = validateForm(f, "REQUIRED");

    if (requiredFieldsExist)
    {
        sendTransactionAction("AgentDetailTran", "saveAgentGroup", "_self");
    }
}

/*
Shows the Agent Group Assocations page.
*/
function showAgentGroupAssociations()
{
    if (!valueIsEmpty(f.agentGroupPK.value))
    {
        sendTransactionAction("AgentDetailTran", "showAgentGroupAssociations", "_self");
    }
    else
    {
        alert("An AgentGroup Must Be Selected")
    }
}

/*
Shows ContributingProducts.
*/
function showAgentGroupContributingProducts()
{
    if (!valueIsEmpty(f.agentGroupPK.value))
    {
        sendTransactionAction("AgentDetailTran", "showAgentGroupContributingProducts", "_self");
    }
    else
    {
        alert("An AgentGroup Must Be Selected")
    }
}

/*
Callback method from TableModel implemenation.
*/
function onTableRowSingleClick(tableId)
{
    showAgentGroupDetail();
}

/*
Shows the detail of the selected AgentGroup.
*/
function showAgentGroupDetail()
{
    sendTransactionAction("AgentDetailTran", "showAgentGroupDetail", "_self");
}

/*
AgentGroup page is reset and rendered in its default state.
*/
function cancelAgentGroup()
{
    resetForm();

    sendTransactionAction("AgentDetailTran", "cancelAgentGroup", "_self");
}
/*
Deletes the currently selected AgentGroup.
*/
function deleteAgentGroup()
{
    if (!valueIsEmpty(f.agentGroupPK.value))
    {
        if (confirm("Delete AgentGroup?"))
        {
            sendTransactionAction("AgentDetailTran", "deleteAgentGroup", "_self");
        }
    }
    else
    {
        alert("An AgentGroup Must Be Selected")
    }
}

</script>

<table class="formData" cellspacing="0" cellpadding="5" border="0" style="width:100%; height:45%">
    <tr>
        <td align="right" width="25%" nowrap="nowrap" valign="middle"><span class="requiredField">*</span>&nbsp;Contract
            Code:
        </td>
        <td align="left" width="25%" nowrap="nowrap" valign="middle">
            <input:select bean="agentGroup" name="contractCodeCT" options="<%= contractCodeCTs.getOptions() %>" attributesText="id='contractCodeCT' REQUIRED"/>
        </td>
        <td align="right" nowrap="nowrap" width="25%" valign="middle">
            <span class="requiredField">
                *
            </span>
            &nbsp;Commission Profile:
        </td>
        <td align="left" nowrap="nowrap" width="25%" valign="middle">
            <input:select bean="commissionProfile" name="commissionProfilePK"
                          options="<%= commissionProfileSelect.getOptions() %>"
                          attributesText="id='commissionProfile' REQUIRED"/>
        </td>
    </tr>
    <tr>
        <td align="right" width="25%" nowrap="nowrap" valign="middle">Agent #:</td>
        <td align="left" width="25%" nowrap="nowrap" valign="middle">
            <input:text name="agentNumber" bean="agent1" attributesText="id='agentNumber' readonly='readonly' REQUIRED"
                        size="25"/>
        </td>
        <td align="right" nowrap="nowrap" width="25%" valign="middle">
            <span class="requiredField">
                *
            </span>
            &nbsp;Effective Date:
        </td>
        <td align="left" nowrap="nowrap" width="25%" valign="middle">
            <input:text name="uiEffectiveDate" bean="agentGroup"
                        attributesText="id='effectiveDate' onBlur='DateFormat(this,this.value,event,true)' onKeyUp='DateFormat(this,this.value,event,false)' maxlength='10' size='10' REQUIRED"/>
            <a href="javascript:show_calendar('f.effectiveDate', f.effectiveDate.value);"><img
                    src="/PORTAL/common/images/calendarIcon.gif" width="16" height="16" border="0"
                    alt="Select a date from the calendar"></a>
        </td>
    </tr>
    <tr>
        <td align="right" nowrap="nowrap" width="25%" valign="middle">Agent Name:</td>
        <td align="left" nowrap="nowrap" width="25%" valign="middle">
            <input:text name="agentName" bean="agent1" size="25" attributesText="readonly='readonly' REQUIRED"/>
        </td>
        <td align="right" nowrap="nowrap" valign="middle" width="25%">
            <span class="requiredField">
                **
            </span>&nbsp;Termination Date:</td>
        <td align="left" nowrap="nowrap" valign="middle" width="25%">
            <input:text name="uiTerminationDate" bean="agentGroup"
                        attributesText="id='terminationDate' onBlur='DateFormat(this,this.value,event,true)' onKeyUp='DateFormat(this,this.value,event,false)' maxlength='10' size='10'"/>
            <a href="javascript:show_calendar('f.terminationDate', f.terminationDate.value);"><img
                    src="/PORTAL/common/images/calendarIcon.gif" width="16" height="16" border="0"
                    alt="Select a date from the calendar"></a>
        </td>
    </tr>
    <tr>
        <td align="right" nowrap="nowrap" width="25%" valign="middle"><span class="requiredField">*</span>&nbsp;Group
            Type:
        </td>
        <td align="left" nowrap="nowrap" width="25%" valign="middle">
            <input:select bean="agentGroup" name="agentGroupTypeCT" options="<%= agentGroupTypeCTs.getOptions() %>"
                          attributesText="id='agentGroupCT' REQUIRED"/>
        </td>
        <td nowrap="nowrap" width="25%" align="right">
            <a class="textlink1" id="participant" href="javascript:showAgentGroupAssociations()">Participants</a>&nbsp;&nbsp;
        </td>
        <td nowrap="nowrap" width="25%" align="left">
            &nbsp;&nbsp;<a class="textlink1" id="contributingProduct" href="javascript:showAgentGroupContributingProducts()">Contributing
            Products</a>
        </td>
    </tr>
    <tr>
        <td align="left" nowrap="nowrap" width="25%" valign="bottom" height="50%">
            <span class="requiredField">
                *
            </span>
            = required&nbsp;&nbsp;
        </td>
        <td align="left" nowrap="nowrap" width="25%" valign="bottom">
            <span class="requiredField">
                **
            </span>
            = defaults to '<%= DateTimeUtil.formatYYYYMMDDToMMDDYYYY(EDITDate.DEFAULT_MAX_DATE) %>' if not specified&nbsp;&nbsp;
        </td>
        <td nowrap="nowrap" width="25%" align="right">&nbsp;</td>
        <td nowrap="nowrap" valign="top" width="25%">&nbsp;</td>
    </tr>
</table>
<p>
    <table cellspacing="0" cellpadding="0" border="0" style="width:100%; height:2%">
        <tr>
            <td width="20%" nowrap="nowrap">
                <input type="button" name="btnAdd" value=" Add  " onClick="addAgentGroup()"/>
                <input type="button" name="btnSave" value="Save " onClick="saveAgentGroup()"/>
                <input type="button" name="btnCancel" value="Cancel" onClick="cancelAgentGroup()"/>
                <input type="button" name="btnDelete" value="Delete" onClick="deleteAgentGroup()"/>
            </td>
            <td width="20%" valign="top">&nbsp;</td>
            <td width="20%" valign="top" align="center"><span class="tableHeading">Agent Group Summary</span></td>
            <td width="20%" valign="top">&nbsp;</td>
            <td width="20%" valign="top" align="right">
                &nbsp;
            </td>
        </tr>
    </table>
    <jsp:include page="/common/jsp/widget/tableWidget.jsp" flush="true">
        <jsp:param name="tableId" value="AgentGroupTableModel"/>
        <jsp:param name="tableHeight" value="47"/>
        <jsp:param name="multipleRowSelect" value="false"/>
        <jsp:param name="singleOrDoubleClick" value="<%= TableModel.PROPERTY_SINGLE_OR_DOUBLE_CLICK_VALUE_SINGLE %>"/>
    </jsp:include>
</p>
<input:hidden name="agentPK" bean="agent1" attributesText="id='agentPK'"/>
<input:hidden name="agentGroupPK" bean="agentGroup" attributesText="id='agentGroupPK'"/>
