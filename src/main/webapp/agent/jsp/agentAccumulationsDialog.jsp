<!-- agentAccumulationsDialog.jsp //-->

<!-- ****** JAVA CODE ***** //-->

<%@ page  errorPage="/common/jsp/error.jsp" autoFlush="true" %>
<%@ page import="java.util.*, fission.beans.*,
                 edit.common.CodeTableWrapper,
                 edit.common.vo.*,
                 fission.utility.Util,
                 edit.common.EDITBigDecimal,
                 edit.common.EDITDate,
                 event.CommissionHistory,
                 role.ClientRoleFinancial,
                 role.ClientRole" %>

<%
    String selectedAgentNumber = Util.initString((String) request.getAttribute("selectedAgentNumber"), "");

    CommissionHistoryVO[] commissionHistoryVOs = (CommissionHistoryVO[]) request.getAttribute("commissionHistoryVOs");

    String advances = "";
    String advChargebacks = "";
    String netAdvanceAmt = "";
    String firstYear = "";
    String renewal = "";
    String recoveries = "";
    String totalEarnings = "";
    String manualAdjustments = "";
    String agentBalance = "";

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
                if (commissionTypeCT.equalsIgnoreCase(CommissionHistory.COMMISSIONTYPECT_ADVANCE_RECOVERY))
                {
                recoveredComm = recoveredComm.addEditBigDecimal(commissionHistoryVOs[h].getCommissionAmount());
                }
            }
            else if (commissionTypeCT.equalsIgnoreCase(CommissionHistory.COMMISSIONTYPECT_FIRST_YEAR_NEG_EARN) ||
                     commissionTypeCT.equalsIgnoreCase(CommissionHistory.COMMISSIONTYPECT_RECOVERY_CHARGEBACK))
            {
                firstYearComm = firstYearComm.subtractEditBigDecimal(commissionHistoryVOs[h].getCommissionAmount());
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
            //else if (commissionTypeCT.equalsIgnoreCase(CommissionHistory.COMMISSIONTYPECT_ADVANCE_RECOVERY))
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
    }

    if (!selectedAgentNumber.equals(""))
    {
        ClientRole[] clientRole = ClientRole.findBy_RoleType_ReferenceID(ClientRole.ROLETYPECT_AGENT, selectedAgentNumber);
        ClientRoleFinancial clientRoleFinancial = clientRole[0].getClientRoleFinancial();
        if (clientRoleFinancial != null)
        {
            agentBalance = clientRoleFinancial.getLifetimeCommBalance().toString();
        }
    }
%>

<html>
<head>

<script src="/PORTAL/common/javascript/commonJavascriptFunctions.js"></script>

<script language="Javascript1.2">

	var f = null;

	function init() {

		f = document.accumulationsForm;

        formatCurrency();
	}

</script>

<title>Accumulations</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>

<body class="dialog" onLoad="init()" bgcolor="#DDDDDD">
<form name="accumulationsForm" method="post" action="/PORTAL/servlet/RequestManager">
  <b>Year-To-Date</b>
  <span style="border-style:solid; border-width:1; position:relative; width:90%; height:50%; top:0; left:0; z-index:0; overflow:visible">
    <table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0" bgcolor="#DDDDDD">
    <tr>
      <td align="left" nowrap>Advances:&nbsp;</td>
      <td align="left" nowrap>&nbsp;</td>
    </tr>
    <tr>
      <td align="right" nowrap>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Advances:&nbsp;
        <input disabled type="text" name="advances" maxlength="11" size="11" value="<%= advances %>" CURRENCY>
      </td>
      <td align="left" nowrap>&nbsp;</td>
    </tr>
    <tr>
      <td align="right" nowrap>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Chargebacks:&nbsp;
        <input disabled type="text" name="advChargebacks" maxlength="11" size="11" value="<%= advChargebacks %>" CURRENCY>
      </td>
      <td align="left" nowrap>&nbsp;</td>
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
    </tr>
    <tr>
      <td align="left" nowrap>Earnings:&nbsp;</td>
      <td align="left" nowrap>&nbsp;</td>
    </tr>
    <tr>
      <td align="right" nowrap>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;First Year:&nbsp;
        <input disabled type="text" name="firstYear" maxlength="11" size="11" value="<%= firstYear %>" CURRENCY>
      </td>
      <td align="left" nowrap>&nbsp;</td>
    </tr>
    <tr>
      <td align="right" nowrap>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Renewal:&nbsp;
        <input disabled type="text" name="renewal" maxlength="11" size="11" value="<%= renewal %>" CURRENCY>
      </td>
      <td align="left" nowrap>&nbsp;</td>
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
    </tr>
      <td align="left" nowrap>Recoveries:&nbsp;</td>
      <td align="left" nowrap>
        <input disabled type="text" name="recoveries" maxlength="11" size="11" value="<%= recoveries %>" CURRENCY>
      </td>
    <tr>
    </tr>
    <tr>
      <td align="left" nowrap>Manual Adjustments:&nbsp;
      </td>
      <td align="left" nowrap>
        <input disabled type="text" name="manualAdjustments" maxlength="11" size="11" value="<%= manualAdjustments %>" CURRENCY>
      </td>
    </tr>
    <tr>
      <td align="left" nowrap>Agent Balance (Lifetime):&nbsp;
      </td>
      <td align="left" nowrap>
        <input disabled type="text" name="agentBalance" maxlength="11" size="11" value="<%= agentBalance %>" CURRENCY>
      </td>
    </tr>
    </table>
  </span>
  <br>
  <br>
  <span>
    <table width="90%" height="10%" border="0" cellspacing="0" cellpadding="0" bgcolor="#DDDDDD">
      <tr>
        <td width="100%" align="right" nowrap>
          <input type="button" name="close" value="Close" onClick="closeWindow()">
        </td>
      </tr>
    </table>
  </span>

     <!-- ****** HIDDEN FIELDS ***** //-->
  <input type="hidden" name="transaction" value="">
  <input type="hidden" name="action"      value="">
  <input type="hidden" name="selectedAgentNumber" value="<%= selectedAgentNumber %>">

</form>
</body>
</html>
