<%@ page import="edit.portal.widgettoolkit.TableModel,
                 fission.utility.*" %>
<%@ page import="edit.common.EDITDate" %>
<%@ taglib uri="/WEB-INF/taglibs-input.tld" prefix="input" %>
<jsp:useBean id="agentGroup" class="agent.AgentGroup" scope="request"/>
<jsp:useBean id="agent1" class="agent.Agent" scope="request"/>
 
<script language="javascript" type="text/javascript">
    /*
        Returns to the AgentGroup page.
    */
    function showAgentGroup()
    {
        sendTransactionAction("AgentDetailTran", "showAgentGroup", "_self");
    }

    /*
    Associates the selected CompanyStructure to the current AgentGroup creating a ContributingProduct.
    */
    function createContributingProduct()
    {
        var selectedCompanyStructurePK = getSelectedRowId("AgentGroupCompanyStructureTableModel");

        if (valueIsEmpty(selectedCompanyStructurePK))
        {
            alert("CompanyStructure Must Be Selected");
        }
        else
        {
            var requiredFieldsExist = validateForm(f);

            if (requiredFieldsExist)
            {
                sendTransactionAction("AgentDetailTran", "createContributingProduct", "_self");
            }
        }
    }

    /*
    Disassociates the selected CompanyStructure from the current AgentGroup deleting the ContributingProduct.
    */
    function deleteContributingProduct()
    {
        var selectedContributingProductPK = getSelectedRowId("ContributingProductTableModel");

        if (valueIsEmpty(selectedContributingProductPK))
        {
            alert("ContributingProduct Must Be Selected");
        }
        else
        {
            if (confirm("Delete Contributing Product?"))
            {
                sendTransactionAction("AgentDetailTran", "deleteContributingProduct", "_self");
            }
        }
    }

    /*
    Displays the AgentGroupAssociation dialog to modify the attributes.
    */
    function showContributingProductDialog()
    {
        var width = 0.35 * screen.width;
        var height = 0.20 * screen.height;

        var selectedContributingProductPK = getSelectedRowId("ContributingProductTableModel");

        if (valueIsEmpty(selectedContributingProductPK))
        {
            alert("A ContributingProduct Must Be Selected");
        }
        else
        {
            openDialog("contributingProductDialog", "top=0, left=0, resizable=no", width, height);

            sendTransactionAction("AgentDetailTran", "showContributingProductDialog", "contributingProductDialog");
        }
    }

</script>

<table class="formData" border="0" cellspacing="0" cellpadding="5" width="100%" height="10%">
    <tr style="font-style:italic; color:rgb(99,99,99);">
        <td nowrap="nowrap" width="20%" align="right">Contract Code:</td>
        <td align="left" nowrap="nowrap" width="15%">
            <%= Util.initString(agentGroup.getContractCodeCT(), "") %>
        </td>
        <td align="right" nowrap="nowrap" width="15%">Agent Name:</td>
        <td align="left" nowrap="nowrap" width="15%">
            <%= Util.initString(agent1.getAgentName(), "") %>
        </td>
        <td align="right" nowrap="nowrap" width="15%">Type:</td>
        <td nowrap="nowrap" width="20%" align="left">
            <%= Util.initString(agentGroup.getAgentGroupTypeCT(), "") %>
        </td>
    </tr>
    <tr>
        <td nowrap="nowrap" width="20%" align="left" colspan="4"><span class="requiredField">*</span>&nbsp;Start Date:
            <input:text name="uIStartDate"
                        attributesText="id=\'uIStartDate\' onBlur=\'DateFormat(this,this.value,event,true)\' onKeyUp=\'DateFormat(this,this.value,event,false)\' maxlength=\'10\' size=\'10\' REQUIRED"/>
            <a href="javascript:show_calendar('f.uIStartDate', f.uIStartDate.value);"><img src="/PORTAL/common/images/calendarIcon.gif" width="16" height="16" border="0" alt="Select a date from the calendar"/></a>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <span class="requiredField">
                **
            </span>Stop Date:
            <input:text name="uIStopDate"
                        attributesText="id=\'uIStopDate\' onBlur=\'DateFormat(this,this.value,event,true)\' onKeyUp=\'DateFormat(this,this.value,event,false)\' maxlength=\'10\' size=\'10\'"/>
            <a href="javascript:show_calendar('f.uIStopDate', f.uIStopDate.value);"><img src="/PORTAL/common/images/calendarIcon.gif" width="16" height="16" border="0" alt="Select a date from the calendar"/></a>
        </td>
        <td align="left" nowrap="nowrap" width="15%">&nbsp;</td>
        <td nowrap="nowrap" width="20%">&nbsp;</td>
    </tr>
</table>

<table cellspacing="0" cellpadding="0" border="0" width="100%" height="2%">
    <tr>
        <th width="50%" align="center" valign="bottom">
            <span class="tableHeading">Company Structure</span>
        </th>
        <th width="50%" align="center" valign="bottom">
            <span class="tableHeading">Contributing Product</span>
        </th>
    </tr>
</table>

<table cellspacing="0" cellpadding="0" width="100%" height="81%">
    <tr>
        <td width="100%" height="100%" nowrap="nowrap">
            <span style="position:relative; left:0; width:50%; height:100%; padding-right:2px">
                <jsp:include page="/common/jsp/widget/tableWidget.jsp" flush="true">
                    <jsp:param name="tableId" value="AgentGroupCompanyStructureTableModel"/>
                    <jsp:param name="tableHeight" value="100"/>
                    <jsp:param name="multipleRowSelect" value="false"/>
                    <jsp:param name="singleOrDoubleClick" value="<%= TableModel.PROPERTY_SINGLE_OR_DOUBLE_CLICK_VALUE_SINGLE %>"/>
                </jsp:include>
            </span>

            <span style="position:relative; left:0; width:50%; height:100%; padding-left:2px">
                <jsp:include page="/common/jsp/widget/tableWidget.jsp" flush="true">
                    <jsp:param name="tableId" value="ContributingProductTableModel"/>
                    <jsp:param name="tableHeight" value="100"/>
                    <jsp:param name="multipleRowSelect" value="false"/>
                    <jsp:param name="singleOrDoubleClick" value="<%= TableModel.PROPERTY_SINGLE_OR_DOUBLE_CLICK_VALUE_SINGLE %>"/>
                </jsp:include>
            </span>
        </td>
    </tr>
</table>

<table cellspacing="0" cellpadding="5" border="0" width="100%" height="2%">
    <tr valign="bottom">
        <td nowrap="nowrap" align="left" width="20%" colspan="2"><span class="requiredField">*</span> = required</td>
        <td nowrap="nowrap" align="left" width="20%"><span class="requiredField">**</span>
            = defaults to <%= DateTimeUtil.formatYYYYMMDDToMMDDYYYY(EDITDate.DEFAULT_MAX_DATE) %></td>
        <td nowrap="nowrap" align="center" width="20%">
            <input type="button" value="  &#8592;  " name="btnLeft"
                   onclick="deleteContributingProduct()"/>
            <input type="button" name="btnRight" value="  &#8594;  " onClick="createContributingProduct()"/>
        </td>
        <td nowrap="nowrap" align="right" width="20%">

            <input type="button" name="modifyContributingProduct"
                   value="Modify CP Assoc."
                   onclick="showContributingProductDialog()"/>
             </td>
        <td nowrap="nowrap" align="right" width="20%">
            <a class="textlink1" href="javascript:showAgentGroup()">Agent Group</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        </td>
    </tr>
</table>

<input:hidden name="agentGroupPK" attributesText="NORESET" default="<%= agentGroup.getAgentGroupPK().toString() %>"/>