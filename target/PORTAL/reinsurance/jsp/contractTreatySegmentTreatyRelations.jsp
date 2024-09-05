<!--
 * User: gfrosti
 * Date: Nov 15, 2004
 * Time: 9:32:58 AM
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 -->
<%@ page import="fission.utility.*,
                 edit.common.vo.*,
                 edit.common.*,
                 java.math.*,
                 reinsurance.business.*,
                 reinsurance.component.*,
                 reinsurance.*,
                 group.*,
                 contract.business.*,
                 contract.component.*"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%-- ****************************** BEGIN Java Code ****************************** --%>
<%
    String responseMessage = (String) request.getAttribute("responseMessage");

    TreatyVO[] treatyVOs = (TreatyVO[]) request.getAttribute("treatyVOs");

    String activeCasePK = (String) request.getAttribute("activeCasePK");

    String activeSegmentPK = (String) request.getAttribute("activeSegmentPK");

    String activeTreatyGroupPK = (String) request.getAttribute("activeTreatyGroupPK");

%>
<%-- ****************************** End Java Code ****************************** --%>

<html>
<head>
<title>Treaty Group List</title>
<link href="/PORTAL/common/css/EDITSolutions.css" rel="stylesheet" type="text/css">

<%-- ****************************** BEGIN JavaScript ****************************** --%>
<script src="/PORTAL/common/javascript/commonJavascriptFunctions.js"></script>

<script>

    var f = null;

    var responseMessage = "<%= responseMessage %>";

    /**
     * Called after the body of the page is loaded.
     */
    function init()
    {
        f = document.theForm;

        checkForResponseMessage();
    }

    /**
     * Attaches the set of selected Treaties to the selected Segment.
     */
    function attachTreatiesToSegment()
    {
        var selectedTreatyPKs = getSelectedRowIds("treatySummary");

        if (selectedTreatyPKs.length == 0)
        {
            alert("Treaty(s) Required");
        }
        else if (valueIsEmpty(f.activeSegmentPK.value))
        {
            alert("Segment Required");
        }
        else
        {
            f.selectedTreatyPKs.value = selectedTreatyPKs;

            sendTransactionAction("ReinsuranceTran", "attachTreatiesToSegment", "_self");
        }
    }

    /**
     * Detaches the set of selected Treaties to the selected Segment.
     */
    function detachTreatiesFromSegment()
    {
        var selectedTreatyPKs = getSelectedRowIds("treatySummary");

        if (selectedTreatyPKs.length == 0)
        {
            alert("Treaty(s) Required");
        }
        else if (valueIsEmpty(f.activeSegmentPK.value))
        {
            alert("Segment Required");
        }
        else
        {
            f.selectedTreatyPKs.value = selectedTreatyPKs;

            sendTransactionAction("ReinsuranceTran", "detachTreatiesFromSegment", "_self");
        }
    }

    /**
     * Changes on this dialog may effect the contractTreatyRelations page. It should be reloaded.
     */
    function showContractTreatyRelations()
    {
        sendTransactionAction("ReinsuranceTran", "showContractTreatyRelations", "main");
    }

    /**
     * Shows the ContractTreaty dialog where Treaties that have been associated to Segments can be modified (as
     * Contract Treaties).
     */
    function showContractTreatyDialog()
    {
        var imgElement = window.event.srcElement;

        var tdElement = imgElement.parentElement;

        var trElement = tdElement.parentElement;

        f.activeTreatyPK.value = trElement.id;

        var width = getScreenWidth() * 0.60;

        var height = getScreenHeight() * 0.60;

        openDialog("contractTreatyDialog", null, width, height);

        sendTransactionAction("ReinsuranceTran", "showContractTreatyDialog", "contractTreatyDialog");
    }
</script>
<%-- ****************************** END JavaScript ****************************** --%>

</head>
<body class="mainTheme" onLoad="init()">
<form name="theForm" method="post" action="/PORTAL/servlet/RequestManager">



<%-- ****************************** BEGIN Segment Summary ****************************** --%>
<%--Table Heading--%>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
        <td align="left" width="33%">
            &nbsp;
        </td>
        <td width="33%">
            <span class="tableHeading">Coverage Summary</span>
        </td>
        <td align="right" width="33%">
            &nbsp;
        </td>
    </tr>
</table>

<%-- Column Headings --%>
<table class="summary" width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr class="heading">
        <td width="25%" nowrap>
            Contract / Case Number
        </td>
        <td width="25%" nowrap>
            Effective Date
        </td>
        <td width="25%" nowrap>
            Status
        </td>
        <td width="25%" nowrap>
            Name
        </td>
    </tr>
</table>

<%--Summary--%>
    <table id="segmentSummary" class="summary" width="100%" border="0" cellspacing="0" cellpadding="0">
<%
if (!activeSegmentPK.equals("0") || !activeCasePK.equals("0")) // Test for the existence of the target VOs.
    {
        Lookup contractLookup = new LookupComponent();

        String currentSegmentOrCaseNumber = null;

        String currentSegmentOrCaseEffectiveDate = null;

        String currentSegmentOrContractGroupName = null;

        String currentSegmentOrContractGroupStatus = null;

        long currentSegmentOrContractGroupPK = 0;

        if (!activeSegmentPK.equals("0"))
        {
            SegmentVO segmentVO = contractLookup.findSegmentBy_SegmentPK(Long.parseLong(activeSegmentPK));

            // Number
            currentSegmentOrCaseNumber = segmentVO.getContractNumber();

            // Effective Date
            currentSegmentOrCaseEffectiveDate = Util.initString(segmentVO.getEffectiveDate(), null);

            currentSegmentOrCaseEffectiveDate = (currentSegmentOrCaseEffectiveDate != null)?DateTimeUtil.formatYYYYMMDDToMMDDYYYY(currentSegmentOrCaseEffectiveDate):"&nbsp;";

            // PK
            currentSegmentOrContractGroupPK = segmentVO.getSegmentPK();

            // Status
            currentSegmentOrContractGroupStatus = Util.initString(segmentVO.getSegmentStatusCT(), "&nbsp;");

            // Name
            currentSegmentOrContractGroupName = Util.initString(segmentVO.getSegmentNameCT(), "&nbsp;");
        }

        else if (!activeCasePK.equals("0"))
        {
            ContractGroup contractGroup = ContractGroup.findBy_ContractGroupPK(new Long(activeCasePK));

            // Number
            currentSegmentOrCaseNumber = contractGroup.getContractGroupNumber();

            // Effective Date
            currentSegmentOrCaseEffectiveDate = DateTimeUtil.formatYYYYMMDDToMMDDYYYY(contractGroup.getEffectiveDate().getFormattedDate());

            // PK
            currentSegmentOrContractGroupPK = contractGroup.getContractGroupPK().longValue();

            // Status
            currentSegmentOrContractGroupStatus = contractGroup.getCaseStatusCT();

            // Name
            currentSegmentOrContractGroupName = contractGroup.getClientRole().getClientDetail().getCorporateName();
        }

        boolean isSelected = false;

        boolean isAssociated = false;

        String className = "highlighted";
%>
        <tr class="<%= className %>" id="<%= currentSegmentOrContractGroupPK %>" isAssociated="<%= isAssociated %>" isSelected="<%= isSelected %>" onMouseOver="highlightRow()" onMouseOut="unhighlightRow()" onClick="selectRow(false);showAttachedTreatiesBySegment()">
            <td width="25%" nowrap>
                <%= currentSegmentOrCaseNumber %>
            </td>
            <td width="25%" nowrap>
                <%= currentSegmentOrCaseEffectiveDate %>
            </td>
            <td width="25%" nowrap>
                <%= currentSegmentOrContractGroupStatus %>
            </td>
            <td width="25%" nowrap>
                <%= currentSegmentOrContractGroupName %>
            </td>
        </tr>
<%
    } // end if
%>

    </table>
<%-- ****************************** END Segment Summary ****************************** --%>

<br>

<%-- ****************************** BEGIN Summary Area ****************************** --%>

<span class="tableHeading">Treaty List</span>
<%--Header--%>
<table class="summary" width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr class="heading">
        <td width="1%" nowrap>
            &nbsp;
        </td>
        <td width="39%" nowrap>
            Reinsurer Name
        </td>
        <td width="15%" nowrap>
            Reinsurer Number
        </td>
        <td width="15%" nowrap>
            Start Date
        </td>
        <td width="15%" nowrap>
            Stop Date
        </td>
        <td width="15%" nowrap>
            Pool Percentage
        </td>
    </tr>
</table>

<%--Summary--%>
<span class="scrollableContent" style="border-style:solid; border-width:1;  position:relative; width:100%; height:75%; top:0; left:0;">
    <table id="treatySummary" class="summary" width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
<%
    if (treatyVOs != null) // Test for the existence of the target VOs.
    {
        Reinsurance reinsurance = new ReinsuranceComponent();

        client.business.Lookup clientLookup = new client.component.LookupComponent();

        for (int i = 0; i < treatyVOs.length; i++) // Loop through the target VOs.
        {
            if (treatyVOs[i].getStatus() != null ||
                treatyVOs[i].getVoShouldBeDeleted())
            {
                continue;
            }
            
            long currentTreatyPK = treatyVOs[i].getTreatyPK();

            long currentReinsurerPK = treatyVOs[i].getReinsurerFK();

            ClientDetailVO clientDetailVO = clientLookup.findClientDetailBy_ReinsurerPK(currentReinsurerPK);

            String corporateName = Util.initString(clientDetailVO.getCorporateName(), "&nbsp;");

            ReinsurerVO reinsurerVO = reinsurance.findReinsurerBy_ReinsurerPK(currentReinsurerPK);

            String currentReinsurerNumber = reinsurerVO.getReinsurerNumber();

            String currentStartDate = Util.initString(treatyVOs[i].getStartDate(), null);

            currentStartDate = (currentStartDate != null)?DateTimeUtil.formatYYYYMMDDToMMDDYYYY(currentStartDate):"&nbsp;";

            String currentStopDate = Util.initString(treatyVOs[i].getStopDate(), null);

            currentStopDate = (currentStopDate != null)?DateTimeUtil.formatYYYYMMDDToMMDDYYYY(currentStopDate):"&nbsp;";
            
            ContractTreatyVO contractTreatyVO = null;

            if (!activeCasePK.equals("0"))
            {
                ContractTreaty contractTreaty = ContractTreaty.findBy_ContractGroupPK_TreatyPK(new Long(activeCasePK), new Long(currentTreatyPK));
                
                contractTreatyVO = (ContractTreatyVO) contractTreaty.getVO();
            }
            else if (!activeSegmentPK.equals("0"))
            {
                contractTreatyVO = reinsurance.findContractTreatyBy_SegmentPK_TreatyPK(Long.parseLong(activeSegmentPK), currentTreatyPK);
            }

            if (contractTreatyVO.getStatus() != null ||
                contractTreatyVO.getVoShouldBeDeleted())
            {
                continue;
            }

            BigDecimal currentPoolPercentage = treatyVOs[i].getPoolPercentage();

            if (contractTreatyVO != null)
            {
                if ("Y".equals(contractTreatyVO.getTreatyOverrideInd()))
                {
                    currentPoolPercentage = contractTreatyVO.getPoolPercentage();
                }
            }            

            boolean isSelected = false;

            boolean isAssociated = false;

            String className = null;

            if (contractTreatyVO != null) // There is a an association between the active Segment and the current Treaty.
            {
                className = "associated";

                isAssociated = true;
            }
            else
            {
                className = "default";
            }
%>
        <tr class="<%= className %>" id="<%= currentTreatyPK %>" isAssociated="<%= isAssociated %>" isSelected="<%= isSelected %>" onMouseOver="highlightRow()" onMouseOut="unhighlightRow()" onClick="selectRow(true)">
            <td width="1%" nowrap>
                <%
                    if (isAssociated)
                    {
                %>
                    <img src="/PORTAL/common/images/I.gif" alt="Show Contract Treaty Dialog" onClick="showContractTreatyDialog()">&nbsp;
                <%
                    }
                    else
                    {
                %>
                    &nbsp;
                <%
                    }
                %>
            </td>
            <td width="39%" nowrap>
                <%= corporateName %>
            </td>
            <td width="15%" nowrap>
                <%= currentReinsurerNumber %>
            </td>
            <td width="15%" nowrap>
                <%= currentStartDate %>
            </td>
            <td width="15%" nowrap>
                <%= currentStopDate %>
            </td>
            <td width="15%" nowrap>
                <%= currentPoolPercentage %>
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
<%-- ****************************** END Summary Area ****************************** --%>

<%--Buttons--%>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
        <td align="right" width="100%">
            <input type="button" value=" Attach " onClick="attachTreatiesToSegment()">
            <input type="button" value=" Detach " onClick="detachTreatiesFromSegment()">
            <input type="button" value=" Cancel " onClick="showContractTreatyRelations();closeWindow()">
        </td>
    </tr>
</table>

<%-- ****************************** BEGIN Hidden Variables ****************************** --%>
<input type="hidden" name="transaction">
<input type="hidden" name="action">
<input type="hidden" name="selectedTreatyPKs">
<input type="hidden" name="activeCasePK" value="<%= activeCasePK %>">
<input type="hidden" name="activeSegmentPK" value="<%= activeSegmentPK %>">
<input type="hidden" name="activeTreatyPK">
<input type="hidden" name="activeTreatyGroupPK" value="<%= activeTreatyGroupPK %>">
<%-- ****************************** END Hidden Variables ****************************** --%>

</form>
</body>
</html>