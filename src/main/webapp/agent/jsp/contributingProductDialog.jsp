<%@ page import="fission.utility.*"%>
<%@ page import="edit.common.EDITDate"%>
<%@ taglib uri="/WEB-INF/taglibs-input.tld" prefix="input"%>
<jsp:useBean id="contributingProduct" class="agent.ContributingProduct" scope="request"/>
<!--
 * User: gfrosti
 * Date: Jeb 06, 2006
 * Time: 10:12:46 AM
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 -->
<%
    Long agentGroupPK = Util.initLong(request.getParameter("agentGroupPK"), null);
%>

<script>

    /*
    Saves/Updates this ContributingProduct.
    */
    function saveContributingProduct()
    {
        var requiredFieldsExist = validateForm(f, "REQUIRED");

        if (requiredFieldsExist)
        {
            sendTransactionAction("AgentDetailTran", "saveContributingProduct", "main");

            closeWindow();
        }
    }
</script>

<table class="formData" width="100%" height="80%" border="0" cellspacing="0" cellpadding="5">
    <tr>
        <td align="right" nowrap width="50%">
            <span class="requiredField">*</span>&nbsp;Start Date:&nbsp;
        </td>
        <td align="left" nowrap width="50%">
            <input:text name="uIStartDate" bean="contributingProduct"
                  attributesText="id='uIStartDate' onBlur='DateFormat(this, this.value, event, true)' onKeyUp='DateFormat(this, this.value, event, false)' maxlength='10' size='10' REQUIRED"/>
                  <a href="javascript:show_calendar('f.uIStartDate', f.uIStartDate.value);"><img
                     src="/PORTAL/common/images/calendarIcon.gif" width="16" height="16" border="0"
                     alt="Select a date from the calendar"></a>
        </td>
    </tr>
    <tr>
        <td align="right" width="50%" nowrap="nowrap">
            <span class="requiredField">**</span>&nbsp;Stop Date:&nbsp;
        </td>
        <td align="left" nowrap width="50%">
            <input:text name="uIStopDate" bean="contributingProduct"
                        attributesText="id='uIStopDate' onBlur='DateFormat(this, this.value, event, true)' onKeyUp='DateFormat(this, this.value, event, false)' maxlength='10' size='10'"/>
                        <a href="javascript:show_calendar('f.uIStopDate', f.uIStopDate.value);"><img
                        src="/PORTAL/common/images/calendarIcon.gif" width="16" height="16" border="0"
                        alt="Select a date from the calendar"></a>
        </td>
    </tr>
    <tr>
        <td colspan="2" height="50%" align="left" valign="bottom"><span class="requiredField">*</span> = required&nbsp;&nbsp;&nbsp;&nbsp;<span class="requiredField">**</span>
            = defaults to <%= DateTimeUtil.formatYYYYMMDDToMMDDYYYY(EDITDate.DEFAULT_MAX_DATE) %></td>
    </tr>
</table>

<table width="100%" border="0" cellspacing="0" cellpadding="5">
    <tr>
        <td nowrap="nowrap" width="50%" align="left">&nbsp;</td>
        <td nowrap="nowrap" width="50%" align="right">
            <input type="button" name="save" value="  Save  " onClick="saveContributingProduct()"/>
            <input type="button" name="cancel" value="  Cancel  " onClick="closeWindow();"/>
        </td>
    </tr>
</table>

<input:hidden name="agentGroupPK" default="<%= agentGroupPK.toString() %>"/>
<input:hidden name="contributingProductPK" bean="contributingProduct" attributesText="id=\"contributingProductPK\""/>
