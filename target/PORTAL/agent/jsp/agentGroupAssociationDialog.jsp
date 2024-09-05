<%@ page import="fission.utility.*"%>
<%@ page import="edit.common.EDITDate"%>
<%@ page import="edit.portal.taglib.InputSelect"%>
<%@ page import="edit.common.CodeTableWrapper"%>
<%@ page import="edit.common.vo.CodeTableVO"%>
<%@ page import="edit.services.db.hibernate.SessionHelper"%>
<%@ taglib uri="/WEB-INF/taglibs-input.tld" prefix="input"%>
<jsp:useBean id="agentGroupAssocation" class="agent.AgentGroupAssociation" scope="request"/>
<!--
 * User: sprasad
 * Date: Jan 26, 2006
 * Time: 10:12:46 AM
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 -->

<%
    Long agentGroupPK = Util.initLong(request.getParameter("agentGroupPK"), null);

    // ContractCodeCT <select>
    CodeTableVO[] stopDateReasonVOCTs = CodeTableWrapper.getSingleton().getCodeTableEntries("STOPDATEREASON");
    InputSelect stopDateReasonCTs = new InputSelect(stopDateReasonVOCTs, CodeTableVO.class, new String[]{"codeDesc"}, "code", InputSelect.ASCENDING);

%>

<script>
    function saveAgentGroupAssociation()
    {
        var requiredFieldsExist = validateForm(f, "REQUIRED");

        if (requiredFieldsExist)
        {
            sendTransactionAction("AgentDetailTran", "saveAgentGroupAssociation", "main");

            closeWindow();
        }
    }

    function cancelAgentGroupAssociationDialog()
    {
        closeWindow();
    }
</script>

<table class="formData" width="100%" height="80%" border="0" cellspacing="0" cellpadding="5">
    <tr>
        <td align="right" nowrap>
            <span class="requiredField">*</span>&nbsp;Start Date:&nbsp;
        </td>
        <td align="left" nowrap>
            <input:text name="uiStartDate" bean="agentGroupAssociation"
                  attributesText="id='startDate' onBlur='DateFormat(this, this.value, event, true)' onKeyUp='DateFormat(this, this.value, event, false)' maxlength='10' size='10' REQUIRED"/>
                  <a href="javascript:show_calendar('f.startDate', f.startDate.value);"><img
                     src="/PORTAL/common/images/calendarIcon.gif" width="16" height="16" border="0"
                     alt="Select a date from the calendar"></a>
        </td>
    </tr>
    <tr>
        <td align="right" nowrap>
            <span class="requiredField">**</span>&nbsp;&nbsp;Stop Date:&nbsp;
        </td>
        <td align="left" nowrap>
            <input:text name="uiStopDate" bean="agentGroupAssociation"
                        attributesText="id='stopDate' onBlur='DateFormat(this, this.value, event, true)' onKeyUp='DateFormat(this, this.value, event, false)' maxlength='10' size='10'"/>
                        <a href="javascript:show_calendar('f.stopDate', f.stopDate.value);"><img
                        src="/PORTAL/common/images/calendarIcon.gif" width="16" height="16" border="0"
                        alt="Select a date from the calendar"></a>
        </td>
    </tr>
    <tr>
        <td align="right" nowrap>
            Stop Date Reason CT:&nbsp;
        </td>
        <td align="left" nowrap>
            <input:select bean="agentGroupAssociation" name="stopDateReasonCT" options="<%= stopDateReasonCTs.getOptions() %>" attributesText="id='stopDateReasonCT'"/>

        </td>
    </tr>
    <tr>
    <tr>
        <td colspan="2" height="50%" align="left" valign="bottom"><span class="requiredField">*</span> = required&nbsp;&nbsp;&nbsp;&nbsp;<span class="requiredField">**</span>
            = defaults to <%= DateTimeUtil.formatYYYYMMDDToMMDDYYYY(EDITDate.DEFAULT_MAX_DATE) %></td>
    </tr>
</table>

<table width="100%" border="0" cellspacing="0" cellpadding="5">
    <tr>
        <td>&nbsp;</td>
        <td align="right">
            <input type="button" name="save" value="  Save  " onClick="saveAgentGroupAssociation()"/>
            <input type="button" name="cancel" value="  Cancel  " onClick="cancelAgentGroupAssociationDialog();"/>
        </td>
    </tr>
</table>

<input:hidden name="agentGroupPK" default="<%= agentGroupPK.toString() %>"/>
<input:hidden name="agentGroupAssociationPK" bean="agentGroupAssociation" attributesText="id=\"agentGroupAssociationPK\""/>
