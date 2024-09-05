<%@ page import="fission.utility.Util,
                 java.text.SimpleDateFormat,
                 java.util.Date,
                 edit.common.EDITDate,
                 java.util.Calendar,
                 agent.business.Agent,
                 agent.component.AgentComponent,
                 edit.common.vo.*,
                 edit.common.CodeTableWrapper,
                 agent.ContributingProfile"%>
 <%--
  Created by IntelliJ IDEA.
  User: gfrosti
  Date: Nov 12, 2004
  Time: 3:08:17 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%-- ****************************** BEGIN Java Code ****************************** --%>
<%
    String responseMessage = (String) request.getAttribute("responseMessage");

    CommissionProfileVO[] commissionProfileVOs = (CommissionProfileVO[]) request.getAttribute("commissionProfileVOs");

    String bonusProgramPK = (String) request.getAttribute("bonusProgramPK");

    String contractCodeCT = (String) request.getAttribute("contractCodeCT");
%>
<%!
    /**
     * Sorts the specified CommissionProfilez
     * @param commissionProfileVOs
     * @return
     */
    private CommissionProfileVO[] sortCommissionProfiles(CommissionProfileVO[] commissionProfileVOs)
    {
        CommissionProfileVO[] sortedCommissionProfileVOs = null;

        sortedCommissionProfileVOs = (CommissionProfileVO[]) Util.sortObjects(commissionProfileVOs, new String[]{"getContractCodeCT", "getCommissionLevelCT", "getCommissionOptionCT"});

        return sortedCommissionProfileVOs;
    }
%>
<%-- ****************************** End Java Code ****************************** --%>

<html>
<head>
<title>Contributing Profiles</title>
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
     * Shows the agent bonus program page.
     */
    function showBonusProgram()
    {
        sendTransactionAction("AgentBonusTran", "showBonusProgram", "main");
    }

    /**
     * Adds a CommissionProfile to the set of CommissionProfiles that can participate in the BonusProgram.
     */
    function addContributingCommissionProfiles()
    {
        var selectedRowIds = getSelectedRowIds("commissionProfileSummary");

        if (valueIsEmpty(selectedRowIds))
        {
            alert("Commission Profile Selection Required");
        }
        else
        {
            f.selectedCommissionProfilePKs.value = selectedRowIds;

            sendTransactionAction("AgentBonusTran", "addContributingCommissionProfiles", "main");
        }
    }

    /**
     * Removes a CommissionProfile from the set of CommissionProfiles that can participate in the BonusProgram.
     */
    function removeContributingCommissionProfiles()
    {
        var selectedRowIds = getSelectedRowIds("commissionProfileSummary");

        if (valueIsEmpty(selectedRowIds))
        {
            alert("Commission Profile Selection Required");
        }
        else
        {
            f.selectedCommissionProfilePKs.value = selectedRowIds;

            sendTransactionAction("AgentBonusTran", "removeContributingCommissionProfiles", "main");
        }
    }
</script>
<%-- ****************************** END JavaScript ****************************** --%>

</head>
<body class="mainTheme" onLoad="init()">
<form name="theForm" method="post" action="/PORTAL/servlet/RequestManager">

<%-- ****************************** BEGIN Summary Area ****************************** --%>
<%--Buttons--%>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
        <td align="left" width="33%">
            <input type="button" value=" Add  " onClick="addContributingCommissionProfiles()">
            <input type="button" value="Remove" onClick="removeContributingCommissionProfiles()">
        </td>
        <td width="33%">
            <span class="tableHeading">Contributing Commission Profiles</span>
        </td>
        <td align="right" width="33%">
            <input type="button" value="Agent Bonus Program" onClick="showBonusProgram()">
        </td>
    </tr>
</table>

<%-- Column Headings --%>
<table class="summary" width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr class="heading">
        <td width="33%" nowrap>
            Contract Code
        </td>
        <td width="33%" nowrap>
            Commission Level
        </td>
        <td width="34%" nowrap>
            Commission Option
        </td>
    </tr>
</table>

<%--Summary--%>
<span class="scrollableContent" style="border-style:solid; border-width:1;  position:relative; width:100%; height:90%; top:0; left:0;">
    <table id="commissionProfileSummary" class="summary" width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
<%
    if (commissionProfileVOs != null) // Test for the existence of the target VOs.
    {
        commissionProfileVOs = sortCommissionProfiles(commissionProfileVOs);

        Agent agentComponent = new AgentComponent();

        long currentBonusProgramPK = Long.parseLong(bonusProgramPK);

        for (int i = 0; i < commissionProfileVOs.length; i++) // Loop through the target VOs.
        {
            CommissionProfileVO currentCommissionProfileVO = commissionProfileVOs[i];

            long currentCommissionProfilePK = currentCommissionProfileVO.getCommissionProfilePK();

            ContributingProfile contributingProfile = null; // ??? agentComponent.findContributingProfileBy_CommissionProfilePK_BonusProgramPK(currentCommissionProfilePK, currentBonusProgramPK);

            String currentContractCodeCT = currentCommissionProfileVO.getContractCodeCT();
            String currentContractCodeDesc = CodeTableWrapper.getSingleton().getCodeDescByCodeTableNameAndCode("CONTRACTCODE", currentContractCodeCT);

            String currentCommissionLevelCT = currentCommissionProfileVO.getCommissionLevelCT();
            String currentCommissionLevelDesc = CodeTableWrapper.getSingleton().getCodeDescByCodeTableNameAndCode("COMMISSIONLEVEL", currentCommissionLevelCT);

            String currentCommissionOptionCT = currentCommissionProfileVO.getCommissionOptionCT();
            String currentCommissionOptionDesc = CodeTableWrapper.getSingleton().getCodeDescByCodeTableNameAndCode("COMMISSIONOPTION", currentCommissionOptionCT);

            boolean isSelected = false;

            boolean isAssociated = false;

            String className = null;

            if (contributingProfile != null)
            {
                isAssociated = true;

                className = "associated";
            }
            else
            {
                className = "default";
            }
%>
        <tr class="<%= className %>" id="<%= currentCommissionProfilePK %>" isAssociated="<%= isAssociated %>" isSelected="<%= isSelected %>" onMouseOver="highlightRow()" onMouseOut="unhighlightRow()" onClick="selectRow(true)">
            <td width="33%" nowrap>
                <%= currentContractCodeDesc %>
            </td>
            <td width="33%" nowrap>
                <%= currentCommissionLevelDesc %>
            </td>
            <td width="34%" nowrap>
                <%= currentCommissionOptionDesc %>
            </td>
        </tr>
<%
        }// end for
    } // end if
%>
        <tr class="filler">
            <td colspan="3">
                &nbsp;
            </td>
        </tr>
    </table>
</span>
<%-- ****************************** END Summary Area ****************************** --%>

<%-- ****************************** BEGIN Hidden Variables ****************************** --%>
<input type="hidden" name="transaction">
<input type="hidden" name="action">
<input type="hidden" name="bonusProgramPK" value="<%= bonusProgramPK %>">
<input type="hidden" name="contractCodeCT" value="<%= contractCodeCT %>">
<input type="hidden" name="selectedCommissionProfilePKs">
<%-- ****************************** END Hidden Variables ****************************** --%>




</form>
</body>
</html>