<!--
 * User: gfrosti
 * Date: Nov 12, 2004
 * Time: 2:44:53 PM
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 -->
<%@ page import="fission.utility.*,
                 edit.common.vo.*,
                 contract.*,
                 group.*"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%-- ****************************** BEGIN Java Code ****************************** --%>
<%
    ContractGroup thisCase = (ContractGroup) request.getAttribute("case");

    SegmentVO[] segmentVOs = (SegmentVO[]) request.getAttribute("segmentVOs");
%>
<%-- ****************************** End Java Code ****************************** --%>

<html>
<head>
<title>Contract Treaty Search</title>
<link href="/PORTAL/common/css/EDITSolutions.css" rel="stylesheet" type="text/css">

<%-- ****************************** BEGIN JavaScript ****************************** --%>
<script src="/PORTAL/common/javascript/commonJavascriptFunctions.js"></script>

<script>

    var f = null;

    /**
     * Called after the body of the page is loaded.
     */
    function init()
    {
        f = document.theForm;

        f.contractNumber.focus();
    }

    /**
     * Loads the selected client info into the reinsurer detail page.
     */
<%--    function showCaseSegmentDetail()--%>
<%--    {--%>
<%--        var selectedId = getSelectedRowId("clientSummary");--%>
<%--    }--%>

    /**
     * Searches by direct match against the CaseNumber.
     */
    function findCaseByCaseNumber()
    {
        if (textElementIsEmpty(f.caseNumber))
        {
            alert("Case Number Required");
        }
        else
        {
            sendTransactionAction("ReinsuranceTran", "findCaseByCaseNumber", "_self");
        }
    }

    /**
     * Searches by direct match against the ContractNumber.
     */
    function findSegmentByContractNumber()
    {
        if (textElementIsEmpty(f.contractNumber))
        {
            alert("Contract Number Required");
        }
        else
        {
            sendTransactionAction("ReinsuranceTran", "findSegmentByContractNumber", "_self");
        }
    }

    /**
     * Searches by partial match of the corporate name.
     */
    function findSegmentByPartialCorporateName()
    {
        if (textElementIsEmpty(f.partialCorporateName))
        {
            alert("Corporate Name Required");
        }
        else
        {
            sendTransactionAction("ReinsuranceTran", "findSegmentByPartialCorporateName", "_self");
        }
    }

    /**
     * Once Segment(s) have been found, the set of coverages for the selected Segments are to be displayed.
     */
    function showCoverageSummaryAfterSearch()
    {
        var selectedRowId = getSelectedRowId("resultsSummary");

        f.segmentPK.value = selectedRowId;

        sendTransactionAction("ReinsuranceTran", "showCoverageSummaryAfterSearch", "main");

        closeWindow();
    }

    /**
     * Once the Case has been found, its detail is to be displayed.
     */
    function showCaseSummaryAfterSearch()
    {
        var selectedRowId = getSelectedRowId("resultsSummary");

        f.casePK.value = selectedRowId;

        sendTransactionAction("ReinsuranceTran", "showCaseSummaryAfterSearch", "main");

        closeWindow();
    }

    /**
     * Convenience method to narrow by CaseNumber, ContractNumber, or CorporateName
     */
    function findContractTreaty()
    {
        if (!textElementIsEmpty(f.caseNumber))
        {
            findCaseByCaseNumber();
        }
        else if (!textElementIsEmpty(f.contractNumber))
        {
            findSegmentByContractNumber();
        }
        else if (!textElementIsEmpty(f.partialCorporateName))
        {
            findSegmentByPartialCorporateName();
        }
        else
        {
            alert("Search Parameter Required");
        }
    }

</script>
<%-- ****************************** END JavaScript ****************************** --%>

</head>
<body class="mainTheme" onLoad="init()">
<form name="theForm" method="post" action="/PORTAL/servlet/RequestManager">

<%-- ****************************** BEGIN Form Data ****************************** --%>
<table class="formData" width="100%" height="25%" border="0" cellspacing="0" cellpadding="5">
    <tr height="50%">
        <td colspan="4">
            &nbsp; <!--Filler Row -->
        </td>
    </tr>

<%--    BEGIN Form Content --%>
    <tr>
        <td align="right" nowrap>
            Contract Number:
        </td>
        <td align="left" nowrap>
            <input type="text" name="contractNumber" size="25" maxlength="25" onKeyPress="if (enterKeyPressed()){findSegmentByContractNumber()}"  onFocus="clearTextElement(f.caseNumber);clearTextElement(f.partialCorporateName)">
        </td>
        <td align="right" nowrap>
            &nbsp;
        </td>
        <td align="left" nowrap>
            &nbsp;
        </td>
    </tr>
    <tr>
        <td align="right" nowrap width="25%">
            Case Number:
        </td>
        <td align="left" nowrap width="25%">
            <input type="text" name="caseNumber" size="25" maxlength="15" onKeyPress="if (enterKeyPressed()){findCaseByCaseNumber()}" onFocus="clearTextElement(f.contractNumber);clearTextElement(f.partialCorporateName)">
        </td>
        <td align="right" nowrap width="25%">
            &nbsp;
        </td>
        <td align="left" nowrap width="25%">
            &nbsp;
        </td>
    </tr>
    <tr>
        <td align="right" nowrap>
            Corporate Name:
        </td>
        <td align="left" nowrap>
            <input type="text" name="partialCorporateName" size="25" maxlength="25" onKeyPress="if (enterKeyPressed()){findSegmentByPartialCorporateName()}" onFocus="clearTextElement(f.contractNumber);clearTextElement(f.caseNumber)">
        </td>
        <td align="right" nowrap>
            &nbsp;
        </td>
        <td align="left" nowrap>
            &nbsp;
        </td>
    </tr>
    <tr>
        <td align="right" nowrap>
            &nbsp;
        </td>
        <td align="left" nowrap>
            &nbsp;
        </td>
        <td align="right" nowrap>
            &nbsp;
        </td>
        <td align="right" nowrap>
            <input type="button" value="Enter " onClick="findContractTreaty()">
        </td>
    </tr>
<%--    END Form Content --%>

    <tr height="50%">
        <td colspan="4">
            &nbsp; <!--Filler Row -->
        </td>
    </tr>
</table>
<%-- ****************************** END Form Data ****************************** --%>

<br>

<%-- ****************************** BEGIN Summary Area ****************************** --%>
<%--Buttons--%>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
        <td align="left" width="33%">
            &nbsp;
        </td>
        <td width="33%">
            <span class="tableHeading">Search Results</span>
        </td>
        <td align="right" width="33%">
            &nbsp;
        </td>
    </tr>
</table>

<%--Header--%>
<%
    if (thisCase != null)
    {
%>
<table class="summary" width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr class="heading">
        <td width="25%">
            Case Number
        </td>
        <td width="25%">
            Effective Date
        </td>
        <td width="25%">
            Status
        </td>
        <td width="25%">
            Group Type
        </td>
    </tr>
</table>
<%--Summary For the Case Results --%>
<span class="scrollableContent" style="border-style:solid; border-width:1;  position:relative; width:100%; height:40%; top:0; left:0;">
    <table id="resultsSummary" class="summary" width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
<%
        String currentCasePK = String.valueOf(thisCase.getContractGroupPK());

        String currentCaseNumber = Util.initString(thisCase.getContractGroupNumber(), "&nbsp;");
        String currentEffectiveDate = Util.initString(thisCase.getEffectiveDate().getFormattedDate(), null);

        currentEffectiveDate = (currentEffectiveDate != null)?DateTimeUtil.formatYYYYMMDDToMMDDYYYY(currentEffectiveDate):"&nbsp;";
        String currentStatus = Util.initString(thisCase.getCaseStatusCT(), "&nbsp;");

        String currentGroupTypeCT = Util.initString(thisCase.getContractGroupTypeCT(), "&nbsp;");

        boolean isSelected = false;

        boolean isAssociated = false;

        String className = "default";
%>
        <tr class="<%= className %>" id="<%= currentCasePK %>" isAssociated="<%= isAssociated %>" isSelected="<%= isSelected %>" onMouseOver="highlightRow()" onMouseOut="unhighlightRow()" onDblClick="showCaseSummaryAfterSearch()" onClick="selectRow(false)">
            <td width="25%" NOWRAP>
                <%= currentCaseNumber %>
            </td>
            <td width="25%" NOWRAP>
                <%= currentEffectiveDate %>
            </td>
            <td width="25%" NOWRAP>
                <%= currentStatus %>
            </td>
            <td width="25%" NOWRAP>
                <%= currentGroupTypeCT %>
            </td>
        </tr>

        <tr class="filler">
            <td colspan="4">
                &nbsp;
            </td>
        </tr>
    </table>
</span>
<%
    }
    else
    {
%>
<table class="summary" width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr class="heading">
        <td width="25%">
            Contract Number
        </td>
        <td width="25%">
            Effective Date
        </td>
        <td width="25%">
            Status
        </td>
        <td width="25%">
            Name
        </td>
    </tr>
</table>

<%--Summary For the Segment Results --%>
<span class="scrollableContent" style="border-style:solid; border-width:1;  position:relative; width:100%; height:40%; top:0; left:0;">
    <table id="resultsSummary" class="summary" width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
<%
    if (segmentVOs != null) // Test for the existence of the target VOs.
    {
        for (int i = 0; i < segmentVOs.length; i++) // Loop through the target VOs.
        {
            String currentSegmentPK = String.valueOf(segmentVOs[i].getSegmentPK());

            String currentContractNumber = Util.initString(segmentVOs[i].getContractNumber(), "&nbsp;");

            String currentEffectiveDate = Util.initString(segmentVOs[i].getEffectiveDate(), "&nbsp;");

            String currentStatusCT = Util.initString(segmentVOs[i].getSegmentStatusCT(), "&nbsp;");

            String currentSegmentNameCT = Util.initString(segmentVOs[i].getSegmentNameCT(), "&nbsp;");

            boolean isSelected = false;

            boolean isAssociated = false;

            String className = "default";
%>
        <tr class="<%= className %>" id="<%= currentSegmentPK %>" isAssociated="<%= isAssociated %>" isSelected="<%= isSelected %>" onMouseOver="highlightRow()" onMouseOut="unhighlightRow()" onDblClick="showCoverageSummaryAfterSearch()" onClick="selectRow(false)">
            <td width="25%" NOWRAP>
                <%= currentContractNumber %>
            </td>
            <td width="25%" NOWRAP>
                <%= currentEffectiveDate %>
            </td>
            <td width="25%" NOWRAP>
                <%= currentStatusCT %>
            </td>
            <td width="25%" NOWRAP>
                <%= currentSegmentNameCT %>
            </td>
        </tr>
<%
        }// end for
    } // end if
%>
        <tr class="filler">
            <td colspan="4">
                &nbsp;
            </td>
        </tr>
    </table>
</span>
<%
    }
%>


<%-- ****************************** END Summary Area ****************************** --%>

<br>

<%-- ****************************** BEGIN Buttons ****************************** --%>
<%--Buttons--%>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
        <td align="right">
            <input type="button" value="Cancel" onClick="closeWindow()">
        </td>
    </tr>
</table>
<%-- ****************************** END Buttons ****************************** --%>

<%-- ****************************** BEGIN Hidden Variables ****************************** --%>
<input type="hidden" name="transaction">
<input type="hidden" name="action">
<input type="hidden" name="segmentPK">
<input type="hidden" name="casePK">
<%-- ****************************** END Hidden Variables ****************************** --%>

</form>
</body>
</html>