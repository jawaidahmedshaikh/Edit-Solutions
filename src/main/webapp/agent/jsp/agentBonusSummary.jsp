<%@ page import="edit.common.vo.BonusProgramVO,
                 fission.utility.*,
                 java.math.BigDecimal,
                 edit.common.vo.ParticipatingAgentVO,
                 agent.component.AgentComponent,
                 agent.business.Agent,
                 agent.ParticipatingAgent,
                 agent.BonusProgram,
                 edit.common.*"%>
<%@ page import="java.util.Iterator"%>
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

    ParticipatingAgent[] participatingAgents = (ParticipatingAgent[]) request.getAttribute("participatingAgents");

    ParticipatingAgent activeParticipatingAgent = (ParticipatingAgent) request.getAttribute("activeParticipatingAgent");

    BonusProgram activeBonusProgram = (BonusProgram) request.getAttribute("activeBonusProgram");

    // From the BonusProgram
    String bonusName = (String) Util.initObject(activeBonusProgram, "bonusName", "");
    String contractCodeCT = (String) Util.initObject(activeBonusProgram, "contractCodeCT", "");
    String commissionLevelCT = (String) Util.initObject(activeBonusProgram, "commissionLevelCT", "");
    EDITDate bonusStartDate = (EDITDate) Util.initObject(activeBonusProgram, "bonusStartDate", null);
    EDITDate bonusStopDate = (EDITDate) Util.initObject(activeBonusProgram, "bonusStopDate", null);
    EDITDate applicationReceivedStopDate = (EDITDate) Util.initObject(activeBonusProgram, "applicationReceivedStopDate", null);
    EDITDate premiumStopDate = (EDITDate) Util.initObject(activeBonusProgram, "premiumStopDate", null);
    String frequencyCT = (String) Util.initObject(activeBonusProgram, "frequencyCT", "");
    String produceCheckInd = (String) Util.initObject(activeBonusProgram, "produceCheckInd", "");
    EDITDate nextCheckDate = (EDITDate) Util.initObject(activeBonusProgram, "nextCheckDate", null);

    // From the ParticipatingAgent

    EDITBigDecimal bonusPremiumBalance = (EDITBigDecimal) Util.initObject(activeParticipatingAgent, "cumulativeGrossAmount", new EDITBigDecimal());
    EDITDateTime lastStatementDateTime = (EDITDateTime) Util.initObject(activeParticipatingAgent, "lastStatementDateTime", null);
    EDITDateTime lastCheckDateTime = (EDITDateTime) Util.initObject(activeParticipatingAgent, "lastCheckDateTime", null);
    EDITBigDecimal lastCheckAmount = (EDITBigDecimal) Util.initObject(activeParticipatingAgent, "lastCheckAmount", new EDITBigDecimal());
    long participatingAgentPK = Util.initLong(activeParticipatingAgent, "participatingAgentPK", 0L);
%>
<%-- ****************************** End Java Code ****************************** --%>

<html>
<head>
<title>Agent Bonus Summary</title>
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

        formatCurrency();

        setCheckBoxState(f.produceCheckInd, "<%= produceCheckInd %>");

        checkForResponseMessage();
    }

    /**
     * Shows detail of the selected ParticipatingAgent's BonusProgram.
     */
    function showAgentBonusDetail()
    {
        var selectedRowId = getSelectedRowId("tableSummary");

        if (valueIsEmpty(selectedRowId))
        {
            alert("Value Is Empty");
        }
        else
        {
            f.activeParticipatingAgentPK.value = selectedRowId;

            sendTransactionAction("AgentDetailTran", "showAgentBonusDetail", "_self");
        }
    }
</script>
<%-- ****************************** END JavaScript ****************************** --%>

</head>
<body class="mainTheme" onLoad="init()">
<form name="theForm" method="post" action="/PORTAL/servlet/RequestManager">

<jsp:include page="agentInfoHeader.jsp" flush="true"/>

<%-- ****************************** BEGIN Form Data ****************************** --%>
<table class="formData" width="100%" border="0" cellspacing="0" cellpadding="5">
<%--    BEGIN Form Content --%>
    <tr>
        <td align="right" nowrap>
            Bonus Name:&nbsp;
        </td>
        <td align="left" nowrap>
            <input type="text" name="bonusName" size="50" maxlength="75" value="<%= bonusName %>" disabled>
        </td>
        <td align="right" nowrap>
            Bonus Premium Balance:&nbsp;
        </td>
        <td align="left" nowrap>
            <input type="text" name="bonusPremiumBalance" size="10" value="<%= bonusPremiumBalance %>" CURRENCY disabled>
        </td>
    </tr>
    <tr>
        <td align="right" nowrap>
            Contract Code:&nbsp;
        </td>
        <td align="left" nowrap>
            <input type="text" name="contractCodeCT" size="20" value="<%= contractCodeCT %>" disabled>
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
            Commission Level:&nbsp;
        </td>
        <td align="left" nowrap>
            <input type="text" name="commissionLevelCT" size="20" value="<%= commissionLevelCT %>" disabled>
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
            Bonus Start Date:&nbsp;
        </td>
        <td align="left" nowrap>
            <input type="text" name="bonusStartDate" size="10" value="<%= bonusStartDate == null ? "" : DateTimeUtil.formatEDITDateAsMMDDYYYY(bonusStartDate) %>" disabled>
        </td>
        <td align="right" nowrap>
            Last Statement Date:&nbsp;
        </td>
        <td align="left" nowrap>
            <input type="text" name="lastStatementDate" size="10" value="<%= lastStatementDateTime == null ? "" : lastStatementDateTime.getFormattedDateTime() %>" disabled>
        </td>
    </tr>
    <tr>
        <td align="right" nowrap>
            Bonus Stop Date:&nbsp;
        </td>
        <td align="left" nowrap>
            <input type="text" name="bonusStopDate" size="10" value="<%= bonusStopDate == null ? "" : DateTimeUtil.formatEDITDateAsMMDDYYYY(bonusStopDate) %>" disabled>
        </td>
        <td align="right" nowrap>
            Last Check Date/Time:&nbsp;
        </td>
        <td align="left" nowrap>
            <input type="text" name="lastCheckDateTime" size="10" value="<%= lastCheckDateTime == null ? "" : lastCheckDateTime.toString() %>" disabled>
        </td>
    </tr>
    <tr>
        <td align="right" nowrap>
            Application Received Stop Date:&nbsp;
        </td>
        <td align="left" nowrap>
            <input type="text" name="applicationReceivedStopDate" size="10" value="<%= applicationReceivedStopDate == null ? "" : DateTimeUtil.formatEDITDateAsMMDDYYYY(applicationReceivedStopDate) %>" disabled>
        </td>
        <td align="right" nowrap>
            Last Check Amount:&nbsp;
        </td>
        <td align="left" nowrap>
            <input type="text" name="lastCheckAmount" size="10" value="<%= lastCheckAmount %>" CURRENCY disabled>
        </td>
    </tr>
    <tr>
        <td align="right" nowrap>
            Premium Stop Date:&nbsp;
        </td>
        <td align="left" nowrap>
            <input type="text" name="premiumStopDate" size="10" value="<%= premiumStopDate == null ? "" : DateTimeUtil.formatEDITDateAsMMDDYYYY(premiumStopDate) %>" disabled>
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
            Mode:&nbsp;
        </td>
        <td align="left" nowrap>
            <input type="text" name="frequencyCT" size="10" value="<%= frequencyCT %>" disabled>
        </td>
        <td align="right" nowrap>
            Next Check Date:&nbsp;
        </td>
        <td align="left" nowrap>
            <input type="text" name="nextCheckDate" size="10" value="<%= nextCheckDate == null ? "" : DateTimeUtil.formatEDITDateAsMMDDYYYY(nextCheckDate) %>" disabled>
        </td>
    </tr>
    <tr>
        <td align="right" nowrap>
            Produce Check:&nbsp;
        </td>
        <td align="left" nowrap>
            <input type="checkbox" name="produceCheckInd" disabled>
        </td>
        <td align="right" nowrap>
            &nbsp;
        </td>
        <td align="left" nowrap>
            &nbsp;
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

<%-- ****************************** BEGIN Summary Area ****************************** --%>
<%-- Column Headings --%>
<table class="summary" width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr class="heading">
        <td width="20%" nowrap>
            Bonus Name
        </td>
        <td width="20%" nowrap>
            Contract Code
        </td>
        <td width="20%" nowrap>
            Commission Level
        </td>
        <td width="20%" nowrap>
            Start Date
        </td>
        <td width="20%" nowrap>
            Stop Date
        </td>
    </tr>
</table>

<%--Summary--%>
<span class="scrollableContent" style="border-style:solid; border-width:1;  position:relative; width:100%; height:40%; top:0; left:0;">
    <table id="tableSummary" class="summary" width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
<%
    if (participatingAgents != null) // Test for the existence of the target VOs.
    {
        for (int i = 0; i < participatingAgents.length; i++) // Loop through the target VOs.
        {
            ParticipatingAgent currentParticipatingAgent = participatingAgents[i];

            BonusProgram currentBonusProgram = currentParticipatingAgent.getBonusProgram();

            String currentBonusName = currentBonusProgram.getBonusName();

            String currentContractCodeCT = currentBonusProgram.getContractCodeCT();
            String currentContractCode = CodeTableWrapper.getSingleton().getCodeDescByCodeTableNameAndCode("CONTRACTCODE", currentContractCodeCT);

            String currentCommissionLevelCT = currentBonusProgram.getCommissionLevelCT();
            String currentCommissionLevel = CodeTableWrapper.getSingleton().getCodeDescByCodeTableNameAndCode("COMMISSIONLEVEL", currentCommissionLevelCT);

            String currentStartDate = DateTimeUtil.formatEDITDateAsMMDDYYYY(currentBonusProgram.getBonusStartDate());

            String currentStopDate = currentBonusProgram.getBonusStopDate().getFormattedDate();

            long currentParticipatingAgentPK = currentParticipatingAgent.getParticipatingAgentPK().longValue();

            boolean isSelected = false;

            boolean isAssociated = false; // (e.g. CompanyStructure to Foo associations)

            String className = null;

            if (currentParticipatingAgentPK == participatingAgentPK)
            {
                isSelected = true;

                className = "highlighted";
            }
            else
            {
                className = "default";
            }
%>
        <tr class="<%= className %>" id="<%= currentParticipatingAgentPK %>" isAssociated="<%= isAssociated %>" isSelected="<%= isSelected %>" onMouseOver="highlightRow()" onMouseOut="unhighlightRow()" onClick="selectRow(false);showAgentBonusDetail()">
            <td width="20%" nowrap>
                <%= currentBonusName %>
            </td>
            <td width="20%" nowrap>
                <%= currentContractCode %>
            </td>
            <td width="20%" nowrap>
                <%= currentCommissionLevel %>
            </td>
            <td width="20%" nowrap>
                <%= currentStartDate %>
            </td>
            <td width="20%" nowrap>
                <%= currentStopDate %>
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

<table width="100%">
  <tr>
    <td colspan="4" align="right" nowrap>
      <input type="button" name="enter" value="Close" onClick="closeWindow()">
    </td>
  </tr>
</table>

<%-- ****************************** BEGIN Hidden Variables ****************************** --%>
<input type="hidden" name="transaction">
<input type="hidden" name="action">
<input type="hidden" name="activeParticipatingAgentPK">
<%-- ****************************** END Hidden Variables ****************************** --%>

</form>
</body>
</html>