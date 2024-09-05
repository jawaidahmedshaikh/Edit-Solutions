<!--
 * User: gfrosti
 * Date: Nov 15, 2004
 * Time: 10:33:35 AM
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 -->
<%@ page import="fission.utility.*,
                 edit.common.vo.*,
                 reinsurance.business.*,
                 reinsurance.component.*,
                 reinsurance.*,
                 contract.*,
                 group.*"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%-- ****************************** BEGIN Java Code ****************************** --%>
<%
    String responseMessage = (String) request.getAttribute("responseMessage");

    ContractGroup activeCase = (ContractGroup) request.getAttribute("activeCase");
    long activeCasePK = 0;
    if (activeCase != null)
    {
        activeCasePK = activeCase.getContractGroupPK().longValue();
    }


    SegmentVO activeSegmentVO = (SegmentVO) request.getAttribute("activeSegmentVO");

    long activeSegmentPK = (activeSegmentVO != null)?activeSegmentVO.getSegmentPK():0;

    SegmentVO[] segmentVOs = (SegmentVO[]) request.getAttribute("segmentVOs");

    TreatyGroupVO[] treatyGroupVOs = (TreatyGroupVO[]) request.getAttribute("treatyGroupVOs");
    
    boolean disableCaseSummary = ("true".equals(request.getAttribute("disableCaseSummary")))?true:false;
    
    boolean disableSegmentSummary = ("true".equals(request.getAttribute("disableSegmentSummary")))?true:false;

%>
<%!
    /**
     * Wraps the text in <i></i> if true.
     * @param text
     * @param italicize
     * @return
     */ 
    private String italicize(String text, boolean italicize)
    {
        if (italicize)
        {
            return "<i>" + text + " </i>";
        }
        else
        {
            return text;
        }
    }

    /**
     * Utility method that returns true if the supplied Segment is a base Segment, false if it is a rider Segment.
     * @param segmentVO
     * @return
     */
    private boolean segmentIsBase(SegmentVO segmentVO)
    {
        return (segmentVO.getSegmentFK() == 0);
    }
%>

<%-- ****************************** End Java Code ****************************** --%>

<html>
<head>
<title>Contract Treaty Relations</title>
<link href="/PORTAL/common/css/EDITSolutions.css" rel="stylesheet" type="text/css">

<%-- ****************************** BEGIN JavaScript ****************************** --%>
<script src="/PORTAL/common/javascript/commonJavascriptFunctions.js"></script>

<script>

    var f = null;
    
    var responseMessage = "<%= responseMessage %>";

    var activeCasePK = "<%= activeCasePK %>";

    var activeSegmentPK = "<%= activeSegmentPK %>";

    /**
     * Called after the body of the page is loaded.
     */
    function init()
    {
        f = document.theForm;

        checkForResponseMessage();
    }

    /**
     * Pops-up the search for treaties dialog.
     */
    function showSearchForTreatiesDialog()
    {
        var width = getScreenWidth();

        var height = getScreenHeight() * 0.50;

        openDialog("treatySearchDialog", null, width, height);

        sendTransactionAction("ReinsuranceTran", "showSearchForTreatiesDialog", "treatySearchDialog");
    }

    /**
     * Convenience method to determine whether Cases are being associated with TreatyGroups or Segments are being associated.
     */
    function attachTreatyGroups()
    {
        var selectedTreatyGroupPKs = getSelectedRowIds("treatySummary");

        if (valueIsEmpty(selectedTreatyGroupPKs))
        {
            alert("Treaties Required");
        }
        else if (f.activeSegmentPK.value != "0")
        {
            attachTreatyGroupsToSegment();
        }
        else if (f.activeCasePK.value != "0")
        {
            attachTreatyGroupsToContractGroup();
        }
    }

    /**
     * Convenience method to determine whether Cases are being associated with TreatyGroups or Segments are being associated.
     */
    function detachTreatyGroups()
    {
        var selectedTreatyGroupPKs = getSelectedRowIds("treatySummary");

        if (valueIsEmpty(selectedTreatyGroupPKs))
        {
            alert("Treaties Required");
        }
        else if (activeSegmentPK != "0")
        {
            detachTreatyGroupsFromSegment();
        }
        else if (activeCasePK != "0")
        {
            detachTreatyGroupsFromContractGroup();
        }
    }

    /**
     * Dissaciates the selected Treaties from the selected Case.
     */
    function detachTreatyGroupsFromCase()
    {
        alert("To Be Implemented");
    }

    /**
     * Shows the set of TreatyGroups, if any, attached to the selected Segment.
     */
    function showAttachedTreatiesBySegment()
    {
        var selectedRowId = getSelectedRowId("segmentSummary");

        f.activeSegmentPK.value = selectedRowId;

        sendTransactionAction("ReinsuranceTran", "showAttachedTreatiesBySegment", "main");
    }

    /**
     * Attaches the set of selected TreatyGroups to the selected Segment.
     */
    function attachTreatyGroupsToSegment()
    {
        var selectedTreatyGroupPKs = getSelectedRowIds("treatySummary");

        if (selectedTreatyGroupPKs.length == 0)
        {
            alert("Treaty(s) Required");
        }
        else if (f.activeSegmentPK.value == "0")
        {
            alert("Segment Required");
        }
        else
        {
            f.selectedTreatyGroupPKs.value = selectedTreatyGroupPKs;

            sendTransactionAction("ReinsuranceTran", "attachTreatyGroupsToSegment", "main");
        }
    }

    /**
     * Attaches the set of selected TreatyGroups to the selected ContractGroup.
     */
    function attachTreatyGroupsToContractGroup()
    {
        var selectedTreatyGroupPKs = getSelectedRowIds("treatySummary");

        if (selectedTreatyGroupPKs.length == 0)
        {
            alert("Treaty(s) Required");
        }
        else if (f.activeCasePK.value == "0")
        {
            alert("ContractGroup Required");
        }
        else
        {
            f.selectedTreatyGroupPKs.value = selectedTreatyGroupPKs;

            sendTransactionAction("ReinsuranceTran", "attachTreatyGroupsToContractGroup", "main");
        }
    }

    /**
     * Detaches the set of selected TreatyGroups to the selected Segment.
     */
    function detachTreatyGroupsFromSegment()
    {
        var selectedTreatyGroupPKs = getSelectedRowIds("treatySummary");

        if (selectedTreatyGroupPKs.length == 0)
        {
            alert("Treaty(s) Required");
        }
        else if (valueIsEmpty(f.activeSegmentPK.value))
        {
            alert("Segment Required");
        }
        else
        {
            f.selectedTreatyGroupPKs.value = selectedTreatyGroupPKs;

            sendTransactionAction("ReinsuranceTran", "detachTreatyGroupsFromSegment", "main");
        }
    }

    /**
     * Shows the set of TreatyGroups, if any, attached to the selected Case.
     */
    function showAttachedTreatiesByCase()
    {
        alert("To Be Implemented");
    }

    /**
     * Attaches the set of selected TreatyGroups to the selected Case.
     */
    function attachTreatyGroupsToCase()
    {
        alert("To Be Implemented");
    }

    /**
     * Opens the dialog for overriding Segment/Treaty relations.
     */
    function showSegentOrContractGroupTreatyRelations()
    {
        var imgElement = window.event.srcElement;

        var tdElement = imgElement.parentElement;

        var trElement = tdElement.parentElement;

        f.activeTreatyGroupPK.value = trElement.id;

        window.event.cancelBubble = true;

        var width = getScreenWidth() * 0.80;

        var height = getScreenHeight() * 0.70;

        openDialog("treatySearchDialog", null, width, height);

        if (activeSegmentPK != "0")
        {
            sendTransactionAction("ReinsuranceTran", "showSegmentTreatyRelations", "treatySearchDialog");
        }
        else if (activeCasePK != "0")
        {
            sendTransactionAction("ReinsuranceTran", "showContractGroupTreatyRelations", "treatySearchDialog");
        }
    }


</script>
<%-- ****************************** END JavaScript ****************************** --%>

</head>
<body class="mainTheme" onLoad="init()">
<form name="theForm" method="post" action="/PORTAL/servlet/RequestManager">

<%-- ****************************** BEGIN Case Summary ****************************** --%>
<%--Buttons--%>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
        <td align="left" width="33%">
            &nbsp;
        </td>
        <td width="33%">
            <span class="tableHeading">Case Summary</span>
        </td>
        <td align="right" width="33%">
            <input type="button" value="Search" onClick="showSearchForTreatiesDialog()">
        </td>
    </tr>
</table>

<%-- Column Headings --%>
<table class="summary" width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr class="heading">
        <td width="25%" nowrap>
            Case Number
        </td>
        <td width="25%" nowrap>
            Effective Date
        </td>
        <td width="25%" nowrap>
            Status
        </td>
        <td width="25%" nowrap>
            Group Type
        </td>
    </tr>
</table>

<%--Summary--%>
<span class="scrollableContent" style="border-style:solid; border-width:1;  position:relative; width:100%; height:10%; top:0; left:0;">
    <table id="caseSummary" class="summary" width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
<%
    /**
     * This is tricky in that it serves two purposes. It is possible the user has simply search for a Case, and found
     * one. The other possibility is that the user has search-for, and selected a Segment. In that situation, the associated
     * Case of that Segment is displayed.
     */
    if (activeCase != null)
    {
        boolean isSelected = true;

        boolean isAssociated = false;

        String className = "highlighted";
        
        String currentCaseNumber = Util.initString(activeCase.getContractGroupNumber(), "&nbsp;");

        String currentEffectiveDate = Util.initString(activeCase.getEffectiveDate().getFormattedDate(), null);

        currentEffectiveDate = (currentEffectiveDate != null)?DateTimeUtil.formatYYYYMMDDToMMDDYYYY(currentEffectiveDate):"&nbsp;";

        String currentStatus = Util.initString(activeCase.getCaseStatusCT(), "&nbsp;");

        String currentGroupType = Util.initString(activeCase.getCaseTypeCT(), "&nbsp;");
%>
        <tr class="<%= className %>" id="<%= activeCasePK %>" isAssociated="<%= isAssociated %>" isSelected="<%= isSelected %>" onMouseOver="if (!<%= disableCaseSummary %>){highlightRow()}" onMouseOut="if (!<%= disableCaseSummary %>){unhighlightRow()}" onClick="if (!<%= disableCaseSummary %>){selectRow(false)}">
            <td width="25%" nowrap>
                <%= italicize(currentCaseNumber, disableCaseSummary) %>
            </td>
            <td width="25%" nowrap>
                <%= italicize(currentEffectiveDate, disableCaseSummary) %>
            </td>
            <td width="25%" nowrap>
                <%= italicize(currentStatus, disableCaseSummary) %>
            </td>
            <td width="25%" nowrap>
                <%= italicize(currentGroupType, disableCaseSummary) %>
            </td>
        </tr>
<%
    } // end if
%>      
        <tr class="filler">
            <td colspan="4">
                &nbsp;
            </td>
        </tr>
    </table>
</span>
<%-- ****************************** END Case Summary ****************************** --%>

<br><br>

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
            Contract Number
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
<span class="scrollableContent" style="border-style:solid; border-width:1;  position:relative; width:100%; height:32%; top:0; left:0;">
    <table id="segmentSummary" class="summary" width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
<%
    if (segmentVOs != null) // Test for the existence of the target VOs.
    {
        for (int i = 0; i < segmentVOs.length; i++) // Loop through the target VOs.
        {
            long currentSegmentPK = segmentVOs[i].getSegmentPK();

            String currentContractNumber = Util.initString(segmentVOs[i].getContractNumber(), "&nbsp;");

            String currentEffectiveDate = Util.initString(segmentVOs[i].getEffectiveDate(), null);

            currentEffectiveDate = (currentEffectiveDate != null)?DateTimeUtil.formatYYYYMMDDToMMDDYYYY(currentEffectiveDate):"&nbsp;";

            String currentStatus = Util.initString(segmentVOs[i].getSegmentStatusCT(), "&nbsp;");

            String currentSegmentName = Util.initString(segmentVOs[i].getSegmentNameCT(), "&nbsp;");

            boolean isSelected = false;
            
            boolean isAssociated = false;

            String className = null;

            if (currentSegmentPK == activeSegmentPK)
            {
                isSelected = true;
                
                className = "highlighted";
            }
            else
            {
                className = "default";
            }
%>
        <tr class="<%= className %>" id="<%= currentSegmentPK %>" isAssociated="<%= isAssociated %>" isSelected="<%= isSelected %>" onMouseOver="highlightRow()" onMouseOut="unhighlightRow()" onClick="selectRow(false);showAttachedTreatiesBySegment()">
            <td>
                <%= italicize(currentContractNumber, disableSegmentSummary) %>
            </td>
            <td>
                <%= italicize(currentEffectiveDate, disableSegmentSummary) %>
            </td>
            <td>
                <%= italicize(currentStatus, disableSegmentSummary) %>
            </td>
            <td>
                <%= italicize(currentSegmentName, disableSegmentSummary) %>
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
<%-- ****************************** END Segment Summary ****************************** --%>

<br><br>

<%-- ****************************** BEGIN Treaty Summary ****************************** --%>
<%--Table Heading--%>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
        <td align="left" width="33%">
            &nbsp;
        </td>
        <td width="33%">
            <span class="tableHeading">Treaty Summary</span>
        </td>
        <td align="right" width="33%">
            &nbsp;
        </td>
    </tr>
</table>

<%-- Column Headings --%>
<table class="summary" width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr class="heading">
        <td nowrap width="1%">
        </td>
        <td width="99%" nowrap>
            Treaty Group #
        </td>
    </tr>
</table>

<%--Summary--%>
<span class="scrollableContent" style="border-style:solid; border-width:1;  position:relative; width:100%; height:32%; top:0; left:0;">
    <table id="treatySummary" class="summary" width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
<%
    if (treatyGroupVOs != null) // Test for the existence of the target VOs.
    {
        Reinsurance reinsurance = new ReinsuranceComponent();

        for (int i = 0; i < treatyGroupVOs.length; i++) // Loop through the target VOs.
        {
            long currentTreatyGroupFK = treatyGroupVOs[i].getTreatyGroupPK();

            String currentTreatyGroupNumber = Util.initString(treatyGroupVOs[i].getTreatyGroupNumber(), "&nbsp;");

            boolean isSelected = false;

            boolean isAssociated = false;

            String className = "default";

            if (activeCasePK == 0 && activeSegmentPK != 0) // has to be one or the other
            {
                TreatyGroupVO assocTreatyGroupVO = reinsurance.findTreatyGroupBy_TreatyGroupPK_SegmentPK(currentTreatyGroupFK, activeSegmentPK);

                if (assocTreatyGroupVO != null)
                {
                    isAssociated = true;

                    className = "associated";
                }
            }

            if (activeCasePK != 0 && activeSegmentPK == 0) // has to be one or the other
            {
                TreatyGroup assocTreatyGroup = TreatyGroup.findBy_TreatyGroupPK_ContractGroupPK(currentTreatyGroupFK, activeCasePK);

                if (assocTreatyGroup != null)
                {
                    isAssociated = true;

                    className = "associated";
                }
            }
%>
        <tr class="<%= className %>" id="<%= currentTreatyGroupFK %>" isAssociated="<%= isAssociated %>" isSelected="<%= isSelected %>" onMouseOver="highlightRow()" onMouseOut="unhighlightRow()" onClick="selectRow(true)">
            <td width="1%" nowrap>
                <img src="/PORTAL/common/images/I.gif" alt="Show Treaty Override Dialog" onClick="showSegentOrContractGroupTreatyRelations()">&nbsp;
            </td>
            <td width="99%" nowrap>
                <%= currentTreatyGroupNumber %>
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
<%-- ****************************** END Treaty Summary ****************************** --%>

<%--Buttons--%>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
        <td align="right" width="100%">
            <input type="button" value=" Attach " onClick="attachTreatyGroups()">
            <input type="button" value=" Detach " onClick="detachTreatyGroups()">
        </td>
    </tr>
</table>

<%-- ****************************** BEGIN Hidden Variables ****************************** --%>
<input type="hidden" name="transaction">
<input type="hidden" name="action">
<input type="hidden" name="selectedTreatyGroupPKs">
<input type="hidden" name="activeSegmentPK" value="<%= activeSegmentPK %>">
<input type="hidden" name="activeCasePK" value="<%= activeCasePK %>">
<input type="hidden" name="activeTreatyGroupPK">
<input type="hidden" name="disableCaseSummary" value="<%= disableCaseSummary %>">
<input type="hidden" name="disableSegmentSummary" value="<%= disableSegmentSummary %>">
<%-- ****************************** END Hidden Variables ****************************** --%>

</form>
</body>
</html>