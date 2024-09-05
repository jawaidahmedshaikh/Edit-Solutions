<%@ page  errorPage="/common/jsp/error.jsp" autoFlush="true" %>
<%@ page import="java.util.*,
                 edit.common.*,
                 edit.common.vo.*,
                 edit.common.vo.user.*,
                 fission.beans.*,
                 edit.portal.exceptions.*,
                 edit.common.exceptions.VOEditException,
                 fission.utility.*,
                 edit.portal.common.session.UserSession,
                 agent.ui.servlet.CommissionHistoryExtractCache,
                 role.ClientRoleFinancial,
                 event.dm.dao.DAOFactory,
                 event.CommissionHistory,
                 client.ClientDetail,
                 client.Preference,
                 agent.PlacedAgent,
                 agent.Agent,
                 role.ClientRole" %>

<%
    String agentMessage = Util.initString((String) request.getAttribute("agentMessage"), "");
    String selectedAgentNumber = Util.initString((String) request.getAttribute("selectedAgentNumber"), "");
    CommissionHistoryVO[] commissionHistoryVOs = (CommissionHistoryVO[]) request.getAttribute("commissionHistoryVOs");

    // CHANGED THIS TO USE CACHE CLASS - LAZY-LOAD THIS DATA ONLY WHEN NEEDED.
    CommissionHistoryExtractCache commHistExtractCache = new CommissionHistoryExtractCache(session);
    CommissionHistoryVO[] agentExtractVOs = new CommissionHistoryVO[0];

    String lastStatementDate = "";
    String statementProducedInd = "";
    String lastStatementAmount = "";
    String advances = "";
    String advChargebacks = "";
    String netAdvanceAmt = "";
    String firstYear = "";
    String renewal = "";
    String recoveries = "";
    String totalEarnings = "";
    String lastCheckDate = "";
    String rollupBalance = "";
    String commPending = "";
    String commHeld = "";
    String lastCheckAmount = "";
    String manualAdjustments = "";
    String agentBalance = "";
    String projectedCommPayment = "";
    String outstandingAdvBal = "";
    String ny91PctRuleStatus = "unchecked";
    String debitBalanceInfoStatus = "unchecked";
    String agentPK = "0";
    String holdCommStatus = "";
    String debitBalancePayoff = "";
    ClientRoleFinancial clientRoleFinancial = null;
    AgentVO agentVO = (AgentVO) session.getAttribute("agentVO");
    List agentNumbers = new ArrayList();
    if (agentVO != null)
    {
        ClientRole selectedClientRole = null;
        
        PlacedAgent[] placedAgents = PlacedAgent.findBy_Agent(new Agent(agentVO));
        
        for (int i = 0; i < placedAgents.length; i++)
        {
            ClientRole clientRole = placedAgents[i].getClientRole();
            if (clientRole.getReferenceID() != null && !agentNumbers.contains(clientRole.getReferenceID()))
            {
                agentNumbers.add(clientRole.getReferenceID());
                if (clientRole.getReferenceID().equals(selectedAgentNumber))
                {
                    selectedClientRole = clientRole;
                }
            }
        }
        
        agentPK = agentVO.getAgentPK() + "";
        holdCommStatus = Util.initString(agentVO.getHoldCommStatus(), "N");
        EDITBigDecimal minimumCheckAmount = new EDITBigDecimal();
        if (selectedClientRole != null)
        {
            Preference preference = selectedClientRole.getPreference();
            if (preference != null)
            {
                minimumCheckAmount = preference.getMinimumCheck();
            }

            clientRoleFinancial = selectedClientRole.getClientRoleFinancial();

            if (clientRoleFinancial != null)
            {
                EDITBigDecimal commBalance = clientRoleFinancial.getCommBalance();
                if (commBalance.isLT("0") || commBalance.isLT(minimumCheckAmount) ||
                    holdCommStatus.equalsIgnoreCase("Y"))
                {
                    projectedCommPayment = new EDITBigDecimal().toString();
                }
                else
                {
                    projectedCommPayment = commBalance.toString();
                }

                agentBalance = commBalance.toString();

                outstandingAdvBal = clientRoleFinancial.getLifetimeAdvanceBalance().toString();

                EDITDateTime lastStatementDateTime = clientRoleFinancial.getLastStatementDateTime();
                if (lastStatementDateTime != null)
                {
                    lastStatementDate = DateTimeUtil.formatEDITDateAsMMDDYYYY(lastStatementDateTime.getEDITDate());
                }

                statementProducedInd = clientRoleFinancial.getStatementProducedInd();
                if (statementProducedInd == null || statementProducedInd.equalsIgnoreCase("N"))
                {
                    statementProducedInd = "unchecked";
                }
                else
                {
                    statementProducedInd = "checked";
                }

                lastStatementAmount = clientRoleFinancial.getLastStatementAmount().toString();

                EDITDateTime lastCheckDateTime = clientRoleFinancial.getLastCheckDateTime();

                if (lastCheckDateTime != null)
                {
                    lastCheckDate = DateTimeUtil.formatEDITDateAsMMDDYYYY(lastCheckDateTime.getEDITDate());

                }

                lastCheckAmount = clientRoleFinancial.getLastCheckAmount().toString();

                if (clientRoleFinancial.getNYPrem().isGT("0"))
                {
                    ny91PctRuleStatus = "checked";
                }

                if (clientRoleFinancial.getDBAmount().isGT("0"))
                {
                    debitBalanceInfoStatus = "checked";
                }
            }
    
            agentExtractVOs = commHistExtractCache.getCommissionExtracts(selectedAgentNumber);
        }
    }
    
    if (commissionHistoryVOs != null)
    {
        EDITBigDecimal advancedCommissions = new EDITBigDecimal();
        EDITBigDecimal advChgbckComm = new EDITBigDecimal();
        EDITBigDecimal firstYearComm = new EDITBigDecimal();
        EDITBigDecimal renewalComm = new EDITBigDecimal();
        EDITBigDecimal recoveredComm = new EDITBigDecimal();
        EDITBigDecimal adjustmentComm = new EDITBigDecimal();
        String commissionTypeCT = "";
        for (int h = 0; h < commissionHistoryVOs.length; h++)
        {
            commissionTypeCT = commissionHistoryVOs[h].getCommissionTypeCT();
//            EDITDate lastCheckDate = null;
//
//            if (lastCheckDateString != null && !lastCheckDateString.equals(""))
//            {
//                EDITDateTime lastCheckDateTime = new EDITDateTime(lastCheckDateString);
//
//                lastCheckDate = lastCheckDateTime.getEDITDate();
//
//                boolean updateTimeGood = false;
//                if (commissionHistoryVOs[h].getUpdateDateTime() != null)
//                {
//                    EDITDateTime updateDateTime = new EDITDateTime(commissionHistoryVOs[h].getUpdateDateTime());
//
//                    EDITDate updateDate = updateDateTime.getEDITDate();
//
//                    int updateHour = updateDateTime.getHour();
//                    int updateMinute = updateDateTime.getMinute();
//                    int updateSecond = updateDateTime.getSecond();
//
//                    int checkHour = lastCheckDateTime.getHour();
//                    int checkMinute = lastCheckDateTime.getMinute();
//                    int checkSecond = lastCheckDateTime.getSecond();
//
//                    if (updateHour > checkHour)
//                    {
//                        updateTimeGood = true;
//                    }
//                    else if (updateHour == checkHour &&
//                             updateMinute > checkMinute)
//                    {
//                        updateTimeGood = true;
//                    }
//                    else if (updateHour == checkHour &&
//                             updateMinute == checkMinute &&
//                             updateSecond > checkSecond)
//                    {
//                        updateTimeGood = true;
//                    }
//                    if (updateDate.after(lastCheckDate) || (updateDate.equals(lastCheckDate) && updateTimeGood))
//                    {
            if (commissionTypeCT.equalsIgnoreCase(CommissionHistory.COMMISSIONTYPECT_ADVANCE) ||
                commissionTypeCT.equalsIgnoreCase(CommissionHistory.COMMISSIONTYPECT_ADVANCE_ADJUSTMENT) ||
                commissionTypeCT.equalsIgnoreCase(CommissionHistory.COMMISSIONTYPECT_ADVANCE_CHARGEBACK_REVERSAL))
            {
                advancedCommissions = advancedCommissions.addEditBigDecimal(commissionHistoryVOs[h].getCommissionAmount());
            }
            else if (commissionTypeCT.equalsIgnoreCase(CommissionHistory.COMMISSIONTYPECT_ADVANCE_CHARGEBACK))
            {
                advancedCommissions = advancedCommissions.subtractEditBigDecimal(commissionHistoryVOs[h].getCommissionAmount());
            }
            else if (commissionTypeCT.equalsIgnoreCase(CommissionHistory.COMMISSIONTYPECT_CHARGEBACK) ||
                     commissionTypeCT.equalsIgnoreCase(CommissionHistory.COMMISSIONTYPECT_TERM_ADVANCE_CHARGEBACK))
            {
                advChgbckComm = advChgbckComm.addEditBigDecimal(commissionHistoryVOs[h].getCommissionAmount());
            }
            else if (commissionTypeCT.equalsIgnoreCase(CommissionHistory.COMMISSIONTYPECT_CHARGEBACK_REVERSAL) ||
                     commissionTypeCT.equalsIgnoreCase(CommissionHistory.COMMISSIONTYPECT_TERM_ADVANCE_CHARGEBACK_REVERSAL))
            {
                advChgbckComm = advChgbckComm.subtractEditBigDecimal(commissionHistoryVOs[h].getCommissionAmount());
            }
            else if (commissionTypeCT.equalsIgnoreCase(CommissionHistory.COMMISSIONTYPECT_FIRST_YEAR) ||
                     commissionTypeCT.equalsIgnoreCase(CommissionHistory.COMMISSIONTYPECT_ADVANCE_RECOVERY))
            {
                firstYearComm = firstYearComm.addEditBigDecimal(commissionHistoryVOs[h].getCommissionAmount());
                
                //**? moved from below
                if (commissionTypeCT.equalsIgnoreCase(CommissionHistory.COMMISSIONTYPECT_ADVANCE_RECOVERY))
                {
                recoveredComm = recoveredComm.addEditBigDecimal(commissionHistoryVOs[h].getCommissionAmount());
                }
            }
            else if (commissionTypeCT.equalsIgnoreCase(CommissionHistory.COMMISSIONTYPECT_FIRST_YEAR_NEG_EARN) ||
                     commissionTypeCT.equalsIgnoreCase(CommissionHistory.COMMISSIONTYPECT_RECOVERY_CHARGEBACK))
            {
                firstYearComm = firstYearComm.subtractEditBigDecimal(commissionHistoryVOs[h].getCommissionAmount());
                
                //**? moved from below
                if (commissionTypeCT.equalsIgnoreCase(CommissionHistory.COMMISSIONTYPECT_RECOVERY_CHARGEBACK))
                {
                recoveredComm = recoveredComm.subtractEditBigDecimal(commissionHistoryVOs[h].getCommissionAmount());
                }
            }
            else if (commissionTypeCT.equalsIgnoreCase(CommissionHistory.COMMISSIONTYPECT_RENEWAL))
            {
                renewalComm = renewalComm.addEditBigDecimal(commissionHistoryVOs[h].getCommissionAmount());
            }
            else if (commissionTypeCT.equalsIgnoreCase(CommissionHistory.COMMISSIONTYPECT_RNWL_NEG_EARN))
            {
                renewalComm = renewalComm.subtractEditBigDecimal(commissionHistoryVOs[h].getCommissionAmount());
            }
            //else if (commissionTypeCT.equalsIgnoreCase(CommissionHistory.COMMISSIONTYPECT_ADVANCE_RECOVERY)) //**moved above in the para**?
            //{
            //    recoveredComm = recoveredComm.addEditBigDecimal(commissionHistoryVOs[h].getCommissionAmount());
            //}
            //else if (commissionTypeCT.equalsIgnoreCase(CommissionHistory.COMMISSIONTYPECT_RECOVERY_CHARGEBACK))
            //{
            //    recoveredComm = recoveredComm.subtractEditBigDecimal(commissionHistoryVOs[h].getCommissionAmount());
            //}
            else if (commissionTypeCT.equalsIgnoreCase(CommissionHistory.COMMISSIONTYPECT_ADJUSTMENT) ||
                     commissionTypeCT.equalsIgnoreCase(CommissionHistory.COMMISSIONTYPECT_COMM_ADJ))
            {
                adjustmentComm = adjustmentComm.addEditBigDecimal(commissionHistoryVOs[h].getCommissionAmount());
            }
        }

        advances = Util.roundToNearestCent(advancedCommissions).toString();
        advChargebacks = Util.roundToNearestCent(advChgbckComm).toString();
        EDITBigDecimal netAdvanceComm = advancedCommissions.subtractEditBigDecimal(advChgbckComm); 
        netAdvanceAmt = Util.roundToNearestCent(netAdvanceComm).toString();
        
        firstYear = Util.roundToNearestCent(firstYearComm).toString();
        renewal = Util.roundToNearestCent(renewalComm).toString();
        recoveries = Util.roundToNearestCent(recoveredComm).toString();
        EDITBigDecimal totalEarnedComm = Util.roundToNearestCent(firstYearComm.addEditBigDecimal(renewalComm));
        totalEarnings = Util.roundToNearestCent(totalEarnedComm).toString();

        manualAdjustments = Util.roundToNearestCent(adjustmentComm).toString();
        
        //agentBalance = Util.roundToNearestCent(netAdvanceComm.addEditBigDecimal(totalEarnedComm).addEditBigDecimal(adjustmentComm)).toString();
    }

    if (agentExtractVOs != null)
    {
        EDITBigDecimal  pendingUpdates = new EDITBigDecimal();
        EDITBigDecimal  heldCommissions = new EDITBigDecimal();
        EDITBigDecimal  accumDebitBalance = new EDITBigDecimal();

        for (int h = 0; h < agentExtractVOs.length; h++)
        {
            if (agentExtractVOs[h].getCommissionTypeCT() != null)
            {
                if (agentExtractVOs[h].getCommissionTypeCT().equalsIgnoreCase(CommissionHistory.COMMISSIONTYPECT_NEGATIVE_EARNINGS) ||
                    agentExtractVOs[h].getCommissionTypeCT().equalsIgnoreCase(CommissionHistory.COMMISSIONTYPECT_CHARGEBACK) ||
                    agentExtractVOs[h].getCommissionTypeCT().equalsIgnoreCase(CommissionHistory.COMMISSIONTYPECT_TERM_ADVANCE_CHARGEBACK))
                {
                    pendingUpdates = pendingUpdates.subtractEditBigDecimal(agentExtractVOs[h].getCommissionAmount());
                }
                else if (!agentExtractVOs[h].getCommissionTypeCT().equalsIgnoreCase(CommissionHistory.COMMISSIONTYPECT_ADVANCE_RECOVERY) &&
                         !agentExtractVOs[h].getCommissionTypeCT().equalsIgnoreCase(CommissionHistory.COMMISSIONTYPECT_RECOVERY_CHARGEBACK))
                {
                    pendingUpdates = pendingUpdates.addEditBigDecimal(agentExtractVOs[h].getCommissionAmount());
                }
            }

            accumDebitBalance = accumDebitBalance.addEditBigDecimal(agentExtractVOs[h].getDebitBalanceAmount());
        }

        CommissionHistoryVO[] heldCommissionsForLargeCase = commHistExtractCache.getCommissionHistoriesHeldForLargeCase();

        EDITBigDecimal totalCommHeldForLargeCases = new EDITBigDecimal();

        for (int i = 0; i < heldCommissionsForLargeCase.length; i++)
        {
            CommissionHistoryVO commissionHistoryVO = heldCommissionsForLargeCase[i];
            totalCommHeldForLargeCases = totalCommHeldForLargeCases.addEditBigDecimal(new EDITBigDecimal(commissionHistoryVO.getCommissionAmount()));
        }

        // Display sum of large cases held commissions and pending updates.
        if (holdCommStatus.equalsIgnoreCase("Y"))
        {
            heldCommissions = pendingUpdates;
            heldCommissions = heldCommissions.addEditBigDecimal(totalCommHeldForLargeCases);
        }
        // Display sum of all CommissionHistories that are held for large case processing.
        // i.e. CommissionHistory records with UpdateStatsu = 'L'
        else
        {
            heldCommissions = totalCommHeldForLargeCases;
        }

        commPending = Util.roundToNearestCent(pendingUpdates).toString();
        commHeld = Util.roundToNearestCent(heldCommissions).toString();
        debitBalancePayoff = Util.roundToNearestCent(accumDebitBalance).toString();
    }

    UserSession userSession = (UserSession) session.getAttribute("userSession");

    EDITDate edEstimatedNextCheckDate = (EDITDate) Util.initObject(clientRoleFinancial, "estimatedNextCheckDate", null);
    String estimatedNextCheckDate = Util.initString(DateTimeUtil.formatEDITDateAsMMDDYYYY(edEstimatedNextCheckDate), "");
%>

<html>

<script src="/PORTAL/common/javascript/commonJavascriptFunctions.js"></script>

<script language="Javascript1.2">

	var f = null;

    var shouldShowLockAlert = true;;
    var agentIsLocked = <%= userSession.getAgentIsLocked()%>;
    var agentMessage = "<%= agentMessage %>";

	function init()
    {
		f = document.agentFinancialForm;

        if (agentMessage != "")
        {
            alert(agentMessage);
        }

		top.frames["main"].setActiveTab("financialTab");

        var contractIsLocked = <%= userSession.getAgentIsLocked()%>;
        var username = "<%= userSession.getUsername() %>";
        var elementPK = "<%= userSession.getAgentPK() %>";
		top.frames["header"].updateLockState(contractIsLocked, username, elementPK);

        shouldShowLockAlert = !agentIsLocked;

        for (var i = 0; i < f.elements.length; i++)
        {
            var elementType = f.elements[i].type;

            if ( (elementType == "text" || elementType == "button") && (shouldShowLockAlert == true) )
            {
                f.elements[i].onclick = showLockAlert;
                f.elements[i].onkeydown = showLockAlert;
            }
        }

        formatCurrency();
	}

    function showLockAlert(){

        if (shouldShowLockAlert == true){

            alert("The Contract can not be edited.");

            return false;
        }
    }

    function showCommissionBalanceHistory()
    {
        var width = 0.95 * screen.width;
        var height = .50 * screen.height;

		openDialog("commissionBalanceHistoryDialog","left=0,top=0,resizable=no", width, height);

        sendTransactionAction("AgentDetailTran", "showCommissionBalanceHistory", "commissionBalanceHistoryDialog");
    }

    function showRollupBalanceHistory()
    {
        var width = 0.95 * screen.width;
        var height = .50 * screen.height;

		openDialog("commissionBalanceHistoryDialog","left=0,top=0,resizable=no", width, height);

        sendTransactionAction("AgentDetailTran", "showRollupBalanceHistory", "commissionBalanceHistoryDialog");
    }

    function showBonusCommissionBalanceHistory()
    {
        var width = 0.95 * screen.width;
        var height = .50 * screen.height;

		openDialog("bonusBalanceHistoryDialog","left=0,top=0,resizable=no", width, height);

        sendTransactionAction("AgentDetailTran", "showBonusCommissionBalanceHistory", "bonusBalanceHistoryDialog");
    }

    function showCommissionEarnedHistory()
    {
        var width = 0.95 * screen.width;
        var height = .50 * screen.height;

		openDialog("commissionEarnedHistoryDialog","left=0,top=0,resizable=no", width, height);

        sendTransactionAction("AgentDetailTran", "showCommissionEarnedHistory", "commissionEarnedHistoryDialog");
    }

    function showCommissionHeldHistory()
    {
        var width = 0.95 * screen.width;
        var height = .50 * screen.height;

		openDialog("commissionHeldHistoryDialog","left=0,top=0,resizable=no", width, height);

        sendTransactionAction("AgentDetailTran", "showCommissionHeldHistory", "commissionHeldHistoryDialog");
    }

    function showCommissionPendingUpdateHistory()
    {
        var width = 0.95 * screen.width;
        var height = .50 * screen.height;

		openDialog("commissionPendingHistoryDialog","left=0,top=0,resizable=no", width, height);

        sendTransactionAction("AgentDetailTran", "showCommissionPendingUpdateHistory", "commissionPendingHistoryDialog");
    }

	function showAccumulationsDialog()
    {
        if (f.selectedAgentNumber.value == "")
        {
            alert("Please Select Agent Number");
        }
        else
        {
        var width = .40 * screen.width;
        var height = .50 * screen.height;

		openDialog("accumulations","left=0,top=0,resizable=no", width, height);

        sendTransactionAction("AgentDetailTran", "showAccumulationsDialog", "accumulations");
        }
    }

	function showNY91PctRuleDialog()
    {
        var width = .35 * screen.width;
        var height = .40 * screen.height;

		openDialog("ny91Pct","left=0,top=0,resizable=no", width, height);

        sendTransactionAction("AgentDetailTran", "showNY91PctRuleDialog", "ny91Pct");
	}

	function showDebitBalanceInfoDialog()
    {
        if (f.selectedAgentNumber.value == "")
        {
            alert("Please Select Agent Number");
        }
        else
        {
            var width = .40 * screen.width;
            var height = .25 * screen.height;

            openDialog("debitBalance","left=0,top=0,resizable=no", width, height);

            sendTransactionAction("AgentDetailTran", "showDebitBalanceDialog", "debitBalance");
        }
	}

	function showBonusInfoDialog()
    {
        var width = .99 * screen.width;
        var height = .90 * screen.height;

        openDialog("bonusSummary","left=0,top=0,resizable=no", width, height);

        sendTransactionAction("AgentDetailTran", "showBonus", "bonusSummary");
	}

	function showPreferences()
    {
        if (f.selectedAgentNumber.value == "")
        {
            alert("Please Select Agent Number");
        }
        else
        {
            var width = 0.80 * screen.width;
            var height = 0.90 * screen.height;

            openDialog("preferenceDialog","left=0,top=0,resizable=no", width, height);

            sendTransactionAction("AgentDetailTran", "showPreferences", "preferenceDialog");
       }
	}

    function checkForAgentSelection()
    {
        sendTransactionAction("AgentDetailTran", "showSelectedAgentFinancial", "contentIFrame");
    }


</script>

<head>
<title>Agent Financial</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta http-equiv="Cache-Control" content="no-store">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Expires" content="0">

<link href="/PORTAL/common/css/EDITSolutions.css" rel="stylesheet" type="text/css">
</head>

<body class="mainTheme" onLoad="init();" style="border-style:solid; border-width:1;">
<form name= "agentFinancialForm" method="post" action="/PORTAL/servlet/RequestManager">

<input type="hidden" name="page" value="">
<jsp:include page="agentInfoHeader.jsp" flush="true"/>

<span id="mainContent" style="border-style:solid; border-width:1;  position:relative; width:100%; height:100%; top:0; left:0; z-index:0; overflow:visible">
  <table width="100%" height="75%">
    <tr>
      <td align="left" nowrap>Agent Number:&nbsp;
          <select name="selectedAgentNumber" onChange="checkForAgentSelection()">
           <option>Please Select</option>
            <%
                if (agentNumbers.size() > 0)
                {
                  for(int i = 0; i < agentNumbers.size(); i++)
                  {
                      if (agentNumbers.get(i).equals(selectedAgentNumber))
                      {
                         out.println("<option selected name=\"id\" value=\"" + agentNumbers.get(i) + "\">" + agentNumbers.get(i) + "</option>");
                      }
                      else  {
                         out.println("<option name=\"id\" value=\"" + agentNumbers.get(i) + "\">" + agentNumbers.get(i) + "</option>");
                      }
                  }
                }
            %>
          </select>
      </td>
    </tr>
    <tr>
      <td align="left" nowrap>Advances:&nbsp;</td>
      <td align="left" nowrap>&nbsp;</td>
      <td align="right" nowrap>Last Statement Run Date:&nbsp;</td>
      <td align="left" nowrap>
        <input disabled type="text" name="lastStatementDate" maxlength="10" size="10" value="<%= lastStatementDate %>">
      </td>
      <td align="left" nowrap>Statement Produced&nbsp;
        <input disabled type="checkbox" name="statementProduced" <%= statementProducedInd %> >
      </td>
    </tr>
    <tr>
      <td align="right" nowrap>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Advances:&nbsp;
        <input disabled type="text" name="advances" maxlength="11" size="11" value="<%= advances %>" CURRENCY>
      </td>
      <td align="left" nowrap>&nbsp;</td>
      <td align="right" nowrap>Last Statement Run Amount:&nbsp;</td>
      <td align="left" nowrap colspan="2">
        <input disabled type="text" name="lastStatementAmount" maxlength="11" size="11" value="<%= lastStatementAmount %>" CURRENCY>
      </td>
    </tr>
    <tr>
      <td align="right" nowrap>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Chargebacks:&nbsp;
        <input disabled type="text" name="advChargebacks" maxlength="11" size="11" value="<%= advChargebacks %>" CURRENCY>
      </td>
      <td align="left" nowrap>&nbsp;</td>
      <td align="right" nowrap>Last Check Date:&nbsp;</td>
      <td align="left" nowrap colspan="2">
        <input disabled type="text" name="lastCheckDate" maxlength="10" size="10" value="<%= lastCheckDate %>">
      </td>
    </tr>
    <tr>
      <td align="left" nowrap>
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Net Advances:&nbsp;
      </td>
      <td align="left" nowrap>
        <input disabled type="text" name="netAdvances" maxlength="11" size="11" value="<%= netAdvanceAmt %>" CURRENCY>
      </td>
      <td align="right" nowrap>Last Check Amount:&nbsp;</td>
      <td align="left" nowrap colspan="2">
        <input disabled type="text" name="lastCheckAmount" maxlength="11" size="11" value="<%= lastCheckAmount %>" CURRENCY>
      </td>
    </tr>
    <tr>
      <td align="left" nowrap>Earnings:&nbsp;</td>
      <td align="left" nowrap>&nbsp;</td>
      <td align="right" nowrap>Estimated Next Check Date:&nbsp;</td>
      <td align="left" nowrap colspan="2">
        <input disabled type="text" name="estimatedNextCheckDate" maxlength="10" size="10" value="<%= estimatedNextCheckDate %>">
      </td>
    </tr>
    <tr>
      <td align="right" nowrap>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;First Year:&nbsp;
        <input disabled type="text" name="firstYear" maxlength="11" size="11" value="<%= firstYear %>" CURRENCY>
      </td>
      <td align="left" nowrap>&nbsp;</td>
      <td align="right" nowrap>Roll-up Balance:&nbsp;</td>
      <td align="left" nowrap colspan="2">
        <input disabled type="text" name="rollupBalance" maxlength="11" size="11" value="<%= rollupBalance %>" CURRENCY>
        <a href="javascript:showRollupBalanceHistory()"><font face="" size="1">(history)</font></a>
      </td>
    </tr>
    <tr>
      <td align="right" nowrap>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Renewal:&nbsp;
        <input disabled type="text" name="renewal" maxlength="11" size="11" value="<%= renewal %>" CURRENCY>
      </td>
      <td align="left" nowrap>&nbsp;</td>
      <td align="right" nowrap>Comm Held:&nbsp;</td>
      <td align="left" nowrap>
        <input disabled type="text" name="commHeld" maxlength="11" size="11" value="<%= commHeld %>" CURRENCY>
        <a href="javascript:showCommissionHeldHistory()"><font face="" size="1">(history)</font></a>
      </td>
    </tr>
    <tr>
      <td align="left" nowrap>
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Total Earnings:&nbsp;
      </td>
      <td align="left" nowrap>
        <input disabled type="text" name="totalEarnings" maxlength="11" size="11" value="<%= totalEarnings %>" CURRENCY>
      </td>
      <td align="right" nowrap>Comm Pending Update:&nbsp;</td>
      <td align="left" nowrap colspan="2">
        <input disabled type="text" name="commPending" maxlength="24" size="18" value="<%= commPending %>" CURRENCY>
        <a href="javascript:showCommissionPendingUpdateHistory()"><font face="" size="1">(history)</font></a>
      </td>
    </tr>
    <tr>
      <td align="left" nowrap>Recoveries:</td>
      <td align="left" nowrap>
        <input disabled type="text" name="recoveries" maxlength="11" size="11" value="<%= recoveries %>" CURRENCY>
      </td>
      <td align="right" nowrap>Debit Payoff Pending Update:&nbsp;</td>
      <td align="left" nowrap colspan="2">
        <input disabled type="text" name="debitBalancePayoff" maxlength="30" size="18" value="<%= debitBalancePayoff %>">
      </td>
    </tr>
    <tr>
      <td align="left" nowrap>Manual Adjustments:&nbsp;
      </td>
      <td align="left" nowrap>
        <input disabled type="text" name="manualAdjustments" maxlength="11" size="11" value="<%= manualAdjustments %>" CURRENCY>
      </td>
      <td align="right" nowrap colspan="3">&nbsp;</td>
    </tr>
    <tr>
      <td align="left" nowrap>Agent Balance:&nbsp;
      </td>
      <td align="left" nowrap>
        <input disabled type="text" name="agentBalance" maxlength="11" size="11" value="<%= agentBalance %>" CURRENCY>
        <a href="javascript:showCommissionBalanceHistory()"><font face="" size="1">(history)</font></a>
      </td>
      <td align="right" nowrap colspan="3">&nbsp;</td>
    </tr>
    <tr>
      <td align="left" nowrap>Projected Commission Payment:&nbsp;
      </td>
      <td align="left" nowrap>
        <input disabled type="text" name="projectedCommPayment" maxlength="11" size="11" value="<%= projectedCommPayment %>" CURRENCY>
      </td>
      <td align="right" nowrap colspan="3">&nbsp;</td>
    </tr>
    <tr>
      <td align="left" nowrap>Outstanding Advance Balance:&nbsp;
      </td>
      <td align="left" nowrap>
        <input disabled type="text" name="outstandingAdvBal" maxlength="11" size="11" value="<%= outstandingAdvBal %>" CURRENCY>
      </td>
      <td align="right" nowrap colspan="3">&nbsp;</td>
    </tr>
    <tr>
      <td align="left" nowrap colspan="5">
        <a href ="javascript:showAccumulationsDialog()">Accumulations</a>
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        <input disabled type="checkbox" name="ny91PctRuleStatus" <%= ny91PctRuleStatus %> >
        <a href ="javascript:showNY91PctRuleDialog()">NY 91% Rule</a>
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        <input disabled type="checkbox" name="debitBalanceInfoStatus" <%= debitBalanceInfoStatus %> >
        <a href ="javascript:showDebitBalanceInfoDialog()">Debit Balance Repay Info</a>
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        <a href ="javascript:showBonusInfoDialog()">Bonus Info</a>
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        <a href ="javascript:showPreferences()">Preference</a>
      </td>
    </tr>
  </table>
</span>

<!-- ****** HIDDEN FIELDS ***** //-->
 <input type="hidden" name="transaction"  value="">
 <input type="hidden" name="action"       value="">

 <input type="hidden" name="agentPK" value="<%= agentPK %>">

</body>
</html>