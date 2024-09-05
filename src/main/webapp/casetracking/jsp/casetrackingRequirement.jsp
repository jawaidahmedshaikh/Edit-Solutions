<%@ page import="casetracking.CaseRequirement,
                 contract.Requirement,
                 edit.common.EDITDate,
                 edit.common.vo.CodeTableVO,
                 edit.common.CodeTableWrapper,
                 engine.ProductStructure,
                 contract.FilteredRequirement,
                 fission.utility.*"%>
<!--
 * User: sprasad
 * Date: Mar 17, 2005
 * Time: 3:07:57 PM
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 -->
 <%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%-- ****************************** BEGIN Java Code ****************************** --%>
<%
    String responseMessage = (String) request.getAttribute("responseMessage");

    String process = (String) session.getAttribute("casetracking.process");

    ProductStructure companyStructure = ProductStructure.findByNames(process, "*", "*", "*", "*");

    CodeTableVO[] statuses = null;

    if (companyStructure != null)
    {
        statuses = CodeTableWrapper.getSingleton().getCodeTableEntries("REQUIREMENTSTATUS", companyStructure.getProductStructurePK().longValue());
    }

    CaseRequirement activeCaseRequirement = (CaseRequirement) request.getAttribute("activeCaseRequirement");
    FilteredRequirement filteredRequirement = (FilteredRequirement) Util.initObject(activeCaseRequirement, "FilteredRequirement", null);
    Requirement activeRequirement = (Requirement) Util.initObject(filteredRequirement, "Requirement", null);

    long activeCaseRequirementPK = Util.initLong(activeCaseRequirement, "caseRequirementPK", 0L);
    String activeRequirementId = (String) Util.initObject(activeRequirement, "requirementId", "");
    String activeRequirementDesc = (String) Util.initObject(activeRequirement, "requirementDescription", "");
    String activeStatusCT = (String) Util.initObject(activeCaseRequirement, "requirementStatusCT", "");
    EDITDate activeReceivedDate = (EDITDate) Util.initObject(activeCaseRequirement, "receivedDate", null);
    EDITDate activeEffectiveDate = (EDITDate) Util.initObject(activeCaseRequirement, "effectiveDate", null);
    EDITDate activeFollowupDate = (EDITDate) Util.initObject(activeCaseRequirement, "followupDate", null);
%>
<%-- ****************************** End Java Code ****************************** --%>

<html>
<head>
<title>EDIT SOLUTIONS - Case Tracking - Requirement</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta http-equiv="Cache-Control" content="no-store">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Expires" content="0">
<link href="/PORTAL/common/css/EDITSolutions.css" rel="stylesheet" type="text/css">

<%-- ****************************** BEGIN JavaScript ****************************** --%>
<script src="/PORTAL/common/javascript/commonJavascriptFunctions.js"></script>
<script src="/PORTAL/casetracking/javascript/casetrackingTabFunctions.js"></script>
<script src="/PORTAL/common/javascript/widgetFunctions.js"></script>
<script src="/PORTAL/common/javascript/scrollTable.js"></script>
<script src="/PORTAL/common/javascript/formatDateFunctions.js"></script>
<script src="/PORTAL/common/javascript/calendarSelector.js"></script>


<script>

    var f = null;

    var responseMessage = "<%= responseMessage %>";

    /**
     * Called after the body of the page is loaded.
     */
    function init()
    {
        f = document.theForm;

        setActiveImage("requirement");

        checkForResponseMessage();

        // Initialize scroll tables
        initScrollTable(document.getElementById("RequirementsTableModelScrollTable"));
    }

    function onTableRowSingleClick(tableId)
    {
        sendTransactionAction("CaseTrackingTran", "showRequirementDetail", "_self");
    }

    function addCaseRequirement()
    {
        sendTransactionAction("CaseTrackingTran", "addCaseRequirement", "_self");
    }

    function addCaseRequirement()
    {
        var width = 0.60 * screen.width;
        var height = 0.20 * screen.height;

        openDialog("addManualRequirement", "top=0,left=0,resizable=no", width,  height);

        sendTransactionAction("CaseTrackingTran", "addCaseRequirement", "addManualRequirement");
    }

    function saveCaseRequirement()
    {
        if (f.caseRequirementPK.value == 0)
        {
            alert('Please Select the Requirement to Modify');
            return;
        }

        sendTransactionAction("CaseTrackingTran", "saveCaseRequirement", "_self");
    }

    function cancelCaseRequirement()
    {
        sendTransactionAction("CaseTrackingTran", "cancelCaseRequirement", "_self");
    }

    function deleteCaseRequirement()
    {
        if (f.caseRequirementPK.value == 0)
        {
            alert('Please Select the Requirement to Delete');
            return;
        }

        sendTransactionAction("CaseTrackingTran", "deleteCaseRequirement", "_self");
    }

</script>
<%-- ****************************** END JavaScript ****************************** --%>

</head>
<body class="mainTheme" onLoad="init()">
<form name="theForm" method="post" action="/PORTAL/servlet/RequestManager">

<%-- ****************************** Tab Content ****************************** --%>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      <td>
        <jsp:include page="casetrackingTabContent.jsp" flush="true"/>
      </td>
    </tr>
</table>

<%-- ****************************** BEGIN Form Data ****************************** --%>
<table class="formData" width="100%" border="0" cellspacing="0" cellpadding="5">
<%--    BEGIN Form Content --%>
    <tr>
        <td align="right" nowrap>
            Requirement Id:
        </td>
        <td align="left" colspan="3" nowrap>
            <input disabled type="text" name="requirementId" size="5" value="<%= activeRequirementId %>">
        </td>
    </tr>
    <tr>
        <td align="right" nowrap>
            Requirement Description:
        </td>
        <td align="left" colspan="3" nowrap>
            <input disabled type="text" name="requirementId" size="50" value="<%= activeRequirementDesc%>">
        </td>
    </tr>
    <tr>
        <td align="right" nowrap>
            Status:
        </td>
        <td align="left" nowrap>
            <select name="requirementStatusCT">
                <option value="null">Please Select</option>
                <%
                    if (statuses != null)
                    {
                        for(int i = 0; i < statuses.length; i++)
                        {
                            String currentCodeDesc    = statuses[i].getCodeDesc();
                            String currentCode        = statuses[i].getCode();

                            if (currentCode.equalsIgnoreCase(activeStatusCT))
                            {
                                out.println("<option selected name=\"id\" value=\"" + currentCode+ "\">" + currentCodeDesc + "</option>");
                            }
                            else
                            {
                                out.println("<option name=\"id\" value=\"" + currentCode + "\">" + currentCodeDesc + "</option>");
                            }
                        }
                    }
                %>
            </select>
        </td>
        <td align="right" nowrap>
            Received Date:
        </td>
        <td align="left" nowrap>
           <input type="text" name="receivedDate" value="<%= activeReceivedDate == null ? "" : DateTimeUtil.formatYYYYMMDDToMMDDYYYY(activeReceivedDate.getFormattedDate()) %>" size='10' maxlength="10" onKeyUp="DateFormat(this,this.value,event,false)" onBlur="DateFormat(this,this.value,event,true)">
           <a href="javascript:show_calendar('f.receivedDate', f.receivedDate.value);"><img src="/PORTAL/common/images/calendarIcon.gif" width="16" height="16" border="0" alt="Select a date from the calendar"></a>
        </td>
    </tr>
    <tr>
        <td align="right" nowrap>
            Effective Date:
        </td>
        <td align="left" nowrap>
           <input type="text" name="effectiveDate" value="<%= activeEffectiveDate == null ? "" : DateTimeUtil.formatYYYYMMDDToMMDDYYYY(activeEffectiveDate.getFormattedDate()) %>" size='10' maxlength="10" onKeyUp="DateFormat(this,this.value,event,false)" onBlur="DateFormat(this,this.value,event,true)">
           <a href="javascript:show_calendar('f.effectiveDate', f.effectiveDate.value);"><img src="/PORTAL/common/images/calendarIcon.gif" width="16" height="16" border="0" alt="Select a date from the calendar"></a>
        </td>
        <td align="right" nowrap>
            Followup Date:
        </td>
        <td align="left" nowrap>
           <input type="text" name="followupDate" value="<%= activeFollowupDate == null ? "" : DateTimeUtil.formatYYYYMMDDToMMDDYYYY(activeFollowupDate.getFormattedDate()) %>" size='10' maxlength="10" onKeyUp="DateFormat(this,this.value,event,false)" onBlur="DateFormat(this,this.value,event,true)">
           <a href="javascript:show_calendar('f.followupDate', f.followupDate.value);"><img src="/PORTAL/common/images/calendarIcon.gif" width="16" height="16" border="0" alt="Select a date from the calendar"></a>
        </td>
    </tr>
<%--    END Form Content --%>

    <tr class="filler">
        <td colspan="4">
            &nbsp; <!--Filler Row -->
        </td>
    </tr>
</table>
<%-- ****************************** END Form Data ****************************** --%>

<br>

<%--Buttons--%>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
        <td align="left" width="33%">
            <input type="button" value=" Add  " onClick="addCaseRequirement()">
            <input type="button" value=" Save " onClick="saveCaseRequirement()">
            <input type="button" value="Cancel" onClick="cancelCaseRequirement()">
            <input type="button" value="Delete" onClick="deleteCaseRequirement()">
        </td>
        <td width="33%">
            &nbsp;
        </td>
        <td align="right" width="33%">
            &nbsp;
        </td>
    </tr>
</table>

<%-- ****************************** BEGIN Summary Area ****************************** --%>

<jsp:include page="/common/jsp/widget/tableWidget.jsp" flush="true">
    <jsp:param name="tableId" value="RequirementsTableModel"/>
    <jsp:param name="tableHeight" value="50"/>
    <jsp:param name="multipleRowSelect" value="false"/>
    <jsp:param name="singleOrDoubleClick" value="single"/>
</jsp:include>

<%-- ****************************** END Summary Area ****************************** --%>

<%-- ****************************** BEGIN Hidden Variables ****************************** --%>
<input type="hidden" name="transaction">
<input type="hidden" name="action">
<input type="hidden" name="pageToShow">
<input type="hidden" name="caseRequirementPK" value="<%= activeCaseRequirementPK %>">
<%--<input type="hidden" name="receivedDate">--%>
<%--<input type="hidden" name="effectiveDate">--%>
<%--<input type="hidden" name="followupDate">--%>
<%-- ****************************** END Hidden Variables ****************************** --%>

</form>
</body>
</html>