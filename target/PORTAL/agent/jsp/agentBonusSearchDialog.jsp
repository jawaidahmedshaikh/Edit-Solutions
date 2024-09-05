<%@ taglib uri="/WEB-INF/taglibs-input.tld" prefix="input" %>
<%@ page import="fission.utility.*,
                 edit.common.vo.*,
                 java.util.List,
                 java.util.ArrayList,
                 agent.business.Agent,
                 agent.component.AgentComponent,
                 edit.common.CodeTableWrapper"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%--
  Created by IntelliJ IDEA.
  User: gfrosti
  Date: Nov 12, 2004
  Time: 2:44:53 PM
  To change this template use File | Settings | File Templates.
--%>

<%-- ****************************** BEGIN Java Code ****************************** --%>
<%
    PlacedAgentVO[] placedAgentVOs = (PlacedAgentVO[]) request.getAttribute("placedAgentVOs");

    CodeTableVO[] commissionLevelCTs = CodeTableWrapper.getSingleton().getCodeTableEntries("COMMISSIONLEVEL");
%>
<%!
    /**
     * Gets the agentNumber for the specified PlacedAgent.
     * @param placedAgentVO
     * @return
     */
    private String getAgentNumber(PlacedAgentVO placedAgentVO)
    {
        String agentNumber = ((AgentVO)placedAgentVO.getParentVO(AgentContractVO.class).getParentVO(AgentVO.class)).getAgentNumber();

        return agentNumber;
    }

    /**
     * Gets the agentName for the specified PlacedAgent bases on the client type.
     * @param placedAgentVO
     * @return
     */
    private String getAgentName(PlacedAgentVO placedAgentVO)
    {
        String name = null;

        ClientDetailVO clientDetailVO = (ClientDetailVO) placedAgentVO.getParentVO(ClientRoleVO.class).getParentVO(ClientDetailVO.class);

        String trustTypeCT = clientDetailVO.getTrustTypeCT();

        if (trustTypeCT.equalsIgnoreCase("Corporate") ||
            trustTypeCT.equalsIgnoreCase("CorporateTrust") ||
            trustTypeCT.equalsIgnoreCase("LLC"))
        {
            name = clientDetailVO.getCorporateName();
        }
        else
        {
            name = clientDetailVO.getLastName();
        }

        return name;
    }

    /**
     * Gets the active PlacedAgent, if any.
     * @param placedAgentVO
     */
    private void composePlacedAgentVO(PlacedAgentVO placedAgentVO)
    {
        List inclusionList = new ArrayList();
        inclusionList.add(AgentContractVO.class);
        inclusionList.add(AgentVO.class);
        inclusionList.add(ClientRoleVO.class);
        inclusionList.add(ClientDetailVO.class);

        Agent agent = new AgentComponent();

        placedAgentVO = agent.composePlacedAgentVO(placedAgentVO, inclusionList);
    }
%>
<%-- ****************************** End Java Code ****************************** --%>

<html>
<head>
<title>Participant Search</title>
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
    }

    /**
     * Loads the selected placed agent info into the participating agents page.
     */
    function showParticipatingAgentAfterSearch()
    {
        var selectedId = getSelectedRowId("resultsSummary");

        f.placedAgentPK.value = selectedId;

        sendTransactionAction("AgentBonusTran", "showParticipatingAgentAfterSearch", "participantSelectionDialog");

        closeWindow();
    }

    /**
     * Searches by CommissionLevelCT and CommissionContractCT
     */
    function findPlacedAgentsByCommissionContractCTAndCommissionLevelCT()
    {
        if (selectElementIsEmpty(f.commissionLevelCT))
        {
            alert("Commission Level Required");
        }
        else
        {
            sendTransactionAction("AgentBonusTran", "findPlacedAgentsByCommissionContractCTAndCommissionLevelCT", "_self");
        }
    }

    /**
     * Searches by CommissionLevelCT and AgentNumber
     */
    function findPlacedAgentsByCommissionContractCTAndAgentNumber()
    {
        if (textElementIsEmpty(f.agentNumber))
        {
            alert("Agent Number Required");
        }
        else
        {
            sendTransactionAction("AgentBonusTran", "findPlacedAgentsByCommissionContractCTAndAgentNumber", "_self");
        }
    }

    /**
     * Searches by CommissionLevelCT and AgentName
     */
    function findPlacedAgentsByCommissionContractCTAndAgentName()
    {
        if (textElementIsEmpty(f.agentName))
        {
            alert("Agent Name Required");
        }
        {
            sendTransactionAction("AgentBonusTran", "findPlacedAgentsByCommissionContractCTAndAgentName", "_self");
        }
    }

    /**
     * Convenience method to narrow by CorporateName, TaxId, or ReinsurerId.
     */
    function findPlacedAgents()
    {
        if (!selectElementIsEmpty(f.commissionLevelCT))
        {
            findPlacedAgentsByCommissionContractCTAndCommissionLevelCT();
        }
        else if (!textElementIsEmpty(f.agentNumber))
        {
            findPlacedAgentsByCommissionContractCTAndAgentNumber();
        }
        else if (!textElementIsEmpty(f.agentName))
        {
            findPlacedAgentsByCommissionContractCTAndAgentName();
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
<table class="formData" width="100%" height="20%" border="0" cellspacing="0" cellpadding="5">

    <tr height="1%">
        <td colspan="7" >
            <i>Search by</i>: &nbsp; Commission Level &nbsp;<i>or</i>&nbsp; Agent # &nbsp;<i>or</i>&nbsp; Agent Name.
        </td>
    </tr>
<%--    BEGIN Form Content --%>
    <tr>
        <td align="right" nowrap width="20%">
            Commission Level:&nbsp;
        </td>
        <td align="left" nowrap width="20%">
            <select name="commissionLevelCT" onKeyPress="if (enterKeyPressed()){findPlacedAgentsByCommissionContractCTAndCommissionLevelCT()}" onFocus="clearTextElement(f.agentNumber);clearTextElement(f.agentName)">
                <option value=""> Please Select </option>
<%
                    for(int i = 0; i < commissionLevelCTs.length; i++)
                    {
                        String currentCodeDesc    = commissionLevelCTs[i].getCodeDesc();
                        String currentCode        = commissionLevelCTs[i].getCode();
                        out.println("<option name=\"id\" value=\"" + currentCode+ "\">" + currentCodeDesc + "</option>");
                    }
%>
            </select>
        </td>
        <td align="right" nowrap>
            Agent #:&nbsp;
        </td>
        <td align="left" nowrap>
            <input type="text" name="agentNumber" size="10" maxlength="15" onKeyPress="if (enterKeyPressed()){findPlacedAgentsByCommissionContractCTAndAgentNumber()}" onFocus="clearSelectElement(f.commissionLevelCT);clearTextElement(f.agentName)">
        </td>
        <td align="right" nowrap>
            Agent Name:&nbsp;
        </td>
        <td align="left" nowrap>
            <input type="text" name="agentName" size="20" maxlength="25" onKeyPress="if (enterKeyPressed()){findPlacedAgentsByCommissionContractCTAndAgentName()}" onFocus="clearSelectElement(f.commissionLevelCT);clearTextElement(f.agentNumber)">
        </td>
        <td align="right" nowrap>
           <input type="button" value="Enter " onClick="findPlacedAgents()">
        </td>
    </tr>
<%--    END Form Content --%>
</table>
<%-- ****************************** END Form Data ****************************** --%>

<%-- ****************************** BEGIN Summary Area ****************************** --%>
<%--Buttons--%>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
        <td colspan="4">
            <span class="tableHeading">Search Results</span>
        </td>
    </tr>
</table>

<%--Header--%>
<table class="summary" width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr class="heading">
        <td width="25%">
            Agent Number
        </td>
        <td width="25%">
            Agent Name
        </td>
        <td width="25%">
            Situation
        </td>
        <td width="25%">
            Stop Date
        </td>
    </tr>
</table>

<%--Summary For the Reinsurer Results --%>
<span class="scrollableContent" style="border-style:solid; border-width:1;  position:relative; width:100%; height:55%; top:0; left:0;">
    <table id="resultsSummary" class="summary" width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
<%
    if (placedAgentVOs != null) // Test for the existence of the target VOs.
    {
        for (int i = 0; i < placedAgentVOs.length; i++) // Loop through the target VOs.
        {
            PlacedAgentVO currentPlacedAgentVO = placedAgentVOs[i];

            composePlacedAgentVO(currentPlacedAgentVO);

            long currentPlacedAgentPK = currentPlacedAgentVO.getPlacedAgentPK();

            String agentNumber = Util.initString(getAgentNumber(currentPlacedAgentVO), "&nbsp;");

            String agentName = Util.initString(getAgentName(currentPlacedAgentVO), "&nbsp;");

            String situation = Util.initString(currentPlacedAgentVO.getSituationCode(), "&nbsp;");

            String stopDate = Util.initString(currentPlacedAgentVO.getStopDate(), "&nbsp;");

            boolean isSelected = false;

            boolean isAssociated = false;

            String className = "default";
%>
        <tr class="<%= className %>" id="<%= currentPlacedAgentPK %>" isAssociated="<%= isAssociated %>" isSelected="<%= isSelected %>" onMouseOver="highlightRow()" onMouseOut="unhighlightRow()" onDblClick="showParticipatingAgentAfterSearch()" onClick="selectRow(false)">
            <td width="25%" NOWRAP>
                <%= agentNumber %>
            </td>
            <td width="25%" NOWRAP>
                <%= agentName %>
            </td>
            <td width="25%" NOWRAP>
                <%= situation %>
            </td>
            <td width="25%" NOWRAP>
                <%= stopDate %>
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
<input type="hidden" name="placedAgentPK">

<input:hidden name="bonusProgramPK" attributesText="id=\"bonusProgramPK\""/>
<input:hidden name="contractCodeCT" attributesText="id=\"contractCodeCT\""/>
<%-- ****************************** END Hidden Variables ****************************** --%>

</form>
</body>
</html>