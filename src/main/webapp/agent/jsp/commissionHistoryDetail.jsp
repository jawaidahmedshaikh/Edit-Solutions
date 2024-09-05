<%@ page  errorPage="/common/jsp/error.jsp" autoFlush="true" %>
<%@ page import="java.util.*,
                 edit.common.*,
                 edit.common.vo.*,
                 edit.common.vo.user.*,
                 fission.beans.*,
                 fission.utility.*,
                 edit.portal.common.session.UserSession,
                 edit.services.db.hibernate.SessionHelper" %>

<%
    CodeTableWrapper codeTableWrapper = CodeTableWrapper.getSingleton();

    CommissionHistoryVO[] commissionHistoryVOs = (CommissionHistoryVO[]) session.getAttribute("commissionHistoryVOs");

    String transactionType = "";
    String commProcess = "";
    String commType = "";
    String effectiveMonth = "";
    String effectiveDay = "";
    String effectiveYear = "";
    String processMonth = "";
    String processDay = "";
    String processYear = "";
    String grossAmount = "";
    String commissionableAmount = "";
    String debitBalanceRepay = "";
    String commissionAmount = "";
    String payToClient = "";
    String contractNumber = "";
    String coverage = "";
    String issueState = "";
    String issueAge = "";
    String policyDur = "";
    String allowancesStatus = "unchecked";
    String agentPK = "0";
    String operator = "";
    String maintDate = "";
    String disbursementSource = "";
    EDITDate commHoldReleaseDate = null;
    AgentVO agentVO = (AgentVO) session.getAttribute("agentVO");
    if (agentVO != null) {

        agentPK = agentVO.getAgentPK() + "";
    }
    String selectedCommissionHistoryPK = (String) request.getAttribute("selectedCommissionHistoryPK");
    if (selectedCommissionHistoryPK == null)
    {
        selectedCommissionHistoryPK = "";
    }

    if (commissionHistoryVOs != null)
    {
        for (int h = 0; h < commissionHistoryVOs.length; h++)
        {
            if ((commissionHistoryVOs[h].getCommissionHistoryPK() + "").equals(selectedCommissionHistoryPK))
            {
                EDITTrxHistoryVO editTrxHistoryVO = (EDITTrxHistoryVO) commissionHistoryVOs[h].getParentVO(EDITTrxHistoryVO.class);
                EDITTrxVO editTrxVO = (EDITTrxVO) editTrxHistoryVO.getParentVO(EDITTrxVO.class);
                FinancialHistoryVO[] financialHistoryVO = editTrxHistoryVO.getFinancialHistoryVO();
                ClientSetupVO clientSetupVO = (ClientSetupVO) editTrxVO.getParentVO(ClientSetupVO.class);
                ContractSetupVO contractSetupVO = (ContractSetupVO) clientSetupVO.getParentVO(ContractSetupVO.class);
                SegmentVO segmentVO = null;
                if (contractSetupVO.getParentVOs() != null)
                {
                    segmentVO = (SegmentVO) contractSetupVO.getParentVO(SegmentVO.class);
                }
                AgentContractVO agentContractVO = (AgentContractVO) commissionHistoryVOs[h].getParentVO(PlacedAgentVO.class).getParentVO(AgentContractVO.class);

                transactionType = codeTableWrapper.getCodeDescByCodeTableNameAndCode("TRXTYPE", editTrxVO.getTransactionTypeCT());
                commProcess = codeTableWrapper.getCodeDescByCodeTableNameAndCode("COMMISSIONPROCESS", agentContractVO.getCommissionProcessCT());

                EDITDate effectiveDate = new EDITDate(editTrxVO.getEffectiveDate());
                effectiveMonth = effectiveDate.getFormattedMonth();
                effectiveDay = effectiveDate.getFormattedDay();
                effectiveYear = effectiveDate.getFormattedYear();

                EDITDate processDate = new EDITDateTime(editTrxHistoryVO.getProcessDateTime()).getEDITDate();
                processMonth = processDate.getFormattedMonth();
                processDay = processDate.getFormattedDay();
                processYear = processDate.getFormattedYear();

                commHoldReleaseDate = SessionHelper.getEDITDate(commissionHistoryVOs[h].getCommHoldReleaseDate());
                if (financialHistoryVO != null && financialHistoryVO.length > 0)
                {
                    grossAmount = financialHistoryVO[0].getGrossAmount().toString();
                    commissionableAmount = Util.roundToNearestCent(financialHistoryVO[0].getCommissionableAmount()).toString();
                    disbursementSource = Util.initString(financialHistoryVO[0].getDisbursementSourceCT(), "");
                }
                else
                {
                    grossAmount = "0";
                    commissionableAmount = "0";
                }
                commissionAmount = Util.roundToNearestCent(commissionHistoryVOs[h].getCommissionAmount()).toString();
                debitBalanceRepay = Util.roundToNearestCent(commissionHistoryVOs[h].getDebitBalanceAmount()).toString();
                commType = Util.initString(codeTableWrapper.getCodeDescByCodeTableNameAndCode("COMMISSIONTYPE", commissionHistoryVOs[h].getCommissionTypeCT()), "");
                if (segmentVO != null)
                {
                    contractNumber = segmentVO.getContractNumber();
                    coverage = segmentVO.getOptionCodeCT();
                    issueState = segmentVO.getIssueStateCT();
                    String[] polEffDate = DateTimeUtil.initDate(segmentVO.getEffectiveDate());
                    String polEffYear = polEffDate[0];
                    int duration = Integer.parseInt(effectiveYear) - Integer.parseInt(polEffYear);
                    policyDur = (duration + 1) + "";
                }
                allowancesStatus = "checked";
                operator = commissionHistoryVOs[h].getOperator();
                if (operator == null)
                {
                    operator = "";
                }
                maintDate = Util.initString(commissionHistoryVOs[h].getMaintDateTime(), "");
            }
        }
    }

    String rowToMatchBase = selectedCommissionHistoryPK;

    UserSession userSession = (UserSession) session.getAttribute("userSession");
%>

<%!
	private TreeMap sortHistoryByEffectiveDateContractNumber(CommissionHistoryVO[] commissionHistoryVOs)
    {
		TreeMap sortedHistories = new TreeMap();

		for (int h = 0; h < commissionHistoryVOs.length; h++)
        {
            EDITTrxHistoryVO editTrxHistoryVO = (EDITTrxHistoryVO) commissionHistoryVOs[h].getParentVO(EDITTrxHistoryVO.class);
            EDITTrxVO editTrxVO = (EDITTrxVO) editTrxHistoryVO.getParentVO(EDITTrxVO.class);
            SegmentVO segmentVO = null;
            String contractNumber = editTrxVO.getEDITTrxPK() + "";
            if (editTrxVO.getParentVOs() != null)
            {
                ClientSetupVO clientSetupVO = (ClientSetupVO) editTrxVO.getParentVO(ClientSetupVO.class);
                ContractSetupVO contractSetupVO = (ContractSetupVO) clientSetupVO.getParentVO(ContractSetupVO.class);
                if (contractSetupVO != null && contractSetupVO.getParentVOs() != null)
                {
                    segmentVO = (SegmentVO) contractSetupVO.getParentVO(SegmentVO.class);
                }
                if (segmentVO != null)
                {
                    contractNumber = segmentVO.getContractNumber();
                }
            }
            String effectiveDate = editTrxVO.getEffectiveDate();
            String commissionHistoryPK = commissionHistoryVOs[h].getCommissionHistoryPK() + "";

            sortedHistories.put(effectiveDate + contractNumber + commissionHistoryPK, commissionHistoryVOs[h]);
		}

		return sortedHistories;
	}
%>


<html>

<script src="/PORTAL/common/javascript/commonJavascriptFunctions.js"></script>

<script language="Javascript1.2">

	var f = null;

    var shouldShowLockAlert = true;
    var height = screen.height;
    var width  = screen.width;

    var commType = "<%= commType %>";

	function init()
    {
		f = document.commissionHistoryForm;

		top.frames["main"].setActiveTab("historyTab");

        var scrollToRow = document.getElementById("<%= rowToMatchBase %>");

        if (scrollToRow != null)
        {

            scrollToRow.scrollIntoView(false);
        }

        shouldShowLockAlert = <%= ! userSession.getAgentIsLocked() %>;

        for (var i = 0; i < f.elements.length; i++)
        {
            var elementType = f.elements[i].type;
            var elementName = f.elements[i].value;

            if ( (elementType == "text" || (elementType == "button" && elementName != "Filter")) && (shouldShowLockAlert == true) )
            {
                f.elements[i].onclick = showLockAlert;
                f.elements[i].onkeydown = showLockAlert;
            }
        }

        formatCurrency();
	}

    function showLockAlert(){

        if (shouldShowLockAlert == true){

            alert("The Agent can not be edited.");

            return false;
        }
    }

	function selectHistoryRow() {

		var tdElement = window.event.srcElement;
		var trElement = tdElement.parentElement;

		var key = trElement.id;

		f.selectedCommissionHistoryPK.value = key;

		sendTransactionAction("AgentDetailTran", "showHistoryDetailSummary", "contentIFrame");
	}

    function showCommissionCheckHistory()
    {
        if (commType != "Check")
        {
            alert("History Available For Commission Checks Only");
        }
        else
        {
            var width = 0.95 * screen.width;
            var height = .50 * screen.height;

            openDialog("commissionCheckHistoryDialog","left=0,top=0,resizable=no", width, height);

            sendTransactionAction("AgentDetailTran", "showCommissionCheckHistory", "commissionCheckHistoryDialog");
        }
    }

	function adjustCommHistory()
    {
        var width = .30 * screen.width;
        var height = .15 * screen.height;

        if (valueIsEmpty(f.selectedCommissionHistoryPK.value))
        {
            alert('Please Select History Record.');
        }
        else
        {
		    openDialog("commHistoryAdjustmentDialog","left=0,top=0,resizable=no", width, height);

            sendTransactionAction("AgentDetailTran", "showCommHistoryAdjustmentDialog", "commHistoryAdjustmentDialog");
        }
	}

    function saveCommHistory()
    {
        sendTransactionAction("AgentDetailTran", "saveCommissionHistory", "contentIFrame");
    }

	function showCommissionHistoryFilter()
    {
        var width = .55 * screen.width;
        var height = .35 * screen.height;

		openDialog("commHistoryFilterDialog","left=0,top=0,resizable=no", width, height);

        sendTransactionAction("AgentDetailTran", "showCommissionHistoryFilterDialog", "commHistoryFilterDialog");
	}

	function showAllowances()
    {
        var width = .40 * screen.width;
        var height = .50 * screen.height;

		openDialog("allowancesDialog","left=0,top=0,resizable=no", width, height);

        sendTransactionAction("AgentDetailTran", "showAllowancesDialog", "allowancesDialog");
	}
</script>

<head>
<title>Commission History Detail</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta http-equiv="Cache-Control" content="no-store">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Expires" content="0">

<link href="/PORTAL/common/css/EDITSolutions.css" rel="stylesheet" type="text/css">
</head>

<body class="mainTheme" onLoad="init()" style="border-style:solid; border-width:1;">
<form name= "commissionHistoryForm" method="post" action="/PORTAL/servlet/RequestManager">

<input type="hidden" name="page" value="">
<jsp:include page="agentInfoHeader.jsp" flush="true"/>

  <table width="80%" height="40%" border="0" cellspacing="0" cellpadding="5">
    <tr>
      <td align="right" nowrap>Transaction Type:&nbsp;</td>
      <td align="left" nowrap>
        <input disabled type="text" name="transactionType" size="20" maxlength="20" value="<%= transactionType %>">
      </td>
      <td align="left" nowrap>Commission Process:&nbsp;</td>
      <td align="left" nowrap>
        <input disabled type="text" name="commProcess" size="20" maxlength="20" value="<%= commProcess %>">
      </td>
      <td align="right" rowspan="6">
        <fieldset style="border-style:solid; border-width:1px; border-color:gray">
        <legend align="top"><font color="black">Policy Info</font></legend>
        <span style="position:relative; width:100%; top:0; left:0; z-index:0; overflow:visible">
          <table width="100%" height="100%" border="0" cellspacing="0" cellpadding="5">
            <tr>
              <td align="right" nowrap>Policy Number:&nbsp;</td>
              <td align="left" nowrap>
                <input disabled type="text" name="contractNumber" size="15" maxlength="15" value="<%= contractNumber %>">
              </td>
            </tr>
            <tr>
              <td align="right" nowrap>Coverage:&nbsp;</td>
              <td align="left" nowrap>
                <input disabled type="text" name="coverage" size="20" maxlength="20" value="<%= coverage %>">
              </td>
            </tr>
            <tr>
              <td align="right" nowrap>Issue State:&nbsp;</td>
              <td align="left" nowrap>
                <input disabled type="text" name="issueState" size="20" maxlength="20" value="<%= issueState %>">
              </td>
            </tr>
            <tr>
              <td align="right" nowrap>Issue Age:&nbsp;</td>
              <td align="left" nowrap>
                <input disabled type="text" name="issueAge" size="3" maxlength="3" value="<%= issueAge %>">
              </td>
            </tr>
            <tr>
              <td align="right" nowrap>Policy Dur:&nbsp;</td>
              <td align="left" nowrap>
                <input disabled type="text" name="policyDur" size="3" maxlength="3" value="<%= policyDur %>">
              </td>
            </tr>
          </table>
        </span>
        </fieldset>
      </td>

    </tr>
    <tr>
      <td align="right" nowrap>Effective Date:&nbsp;</td>
      <td align="left" nowrap>
        <input disabled type="text" name="effectiveMonth" size="2" maxlength="2" value="<%= effectiveMonth%>">
        /
        <input disabled type="text" name="effectiveDay" size="2" maxlength="2" value="<%= effectiveDay%>">
        /
        <input disabled type="text" name="effectiveYear" size="4" maxlength="4" value="<%= effectiveYear%>">
      </td>
      <td align="left" nowrap>Commission Type:&nbsp;</td>
      <td align="left" nowrap>
        <input disabled type="text" name="commType" size="20" maxlength="20" value="<%= commType %>">
        <a href="javascript:showCommissionCheckHistory()"><font face="" size="1">(history)</font></a>
      </td>
    </tr>
    <tr>
      <td align="right" nowrap>Process Date:&nbsp;</td>
      <td align="left" nowrap>
        <input disabled type="text" name="processMonth" size="2" maxlength="2" value="<%= processMonth%>">
        /
        <input disabled type="text" name="processDay" size="2" maxlength="2" value="<%= processDay%>">
        /
        <input disabled type="text" name="processYear" size="4" maxlength="4" value="<%= processYear%>">
      </td>
      <td align="left" nowrap>Pay To Client #:&nbsp;</td>
      <td align="left" nowrap>
        <input disabled type="text" name="payToClient" size="15" maxlength="15" value="<%= payToClient %>">
      </td>
    </tr>
    <tr>
      <td align="right" nowrap>Hold Release Date:&nbsp;</td>
      <td align="left" nowrap>
        <input disabled type="text" name="commHoldReleaseMonth" size="2" maxlength="2" value="<%= commHoldReleaseDate == null ? "" : commHoldReleaseDate.getFormattedMonth() %>">
        /
        <input disabled type="text" name="commHoldReleaseDay" size="2" maxlength="2" value="<%= commHoldReleaseDate == null ? "" : commHoldReleaseDate.getFormattedDay() %>">
        /
        <input disabled type="text" name="commHoldReleaseYear" size="4" maxlength="4" value="<%= commHoldReleaseDate == null ? "" : commHoldReleaseDate.getFormattedYear() %>">
      </td>
      <td align="left" nowrap>Disbursement Source:&nbsp;</td>
      <td align="left" nowrap>
        <input type="text" name="disbursementSource" disabled value="<%= disbursementSource %>">
      </td>
    </tr>
    <tr>
      <td align="right" nowrap>Gross Trx Amount:&nbsp;</td>
      <td align="left" nowrap>
        <input disabled type="text" name="grossAmount" size="11" maxlength="25" value="<%= grossAmount %>" CURRENCY>
      </td>
    </tr>
    <tr>
      <td align="right" nowrap>Commissionable Amount:&nbsp;</td>
      <td align="left" nowrap>
        <input disabled type="text" name="commissionableAmount" size="11" maxlength="25" value="<%= commissionableAmount %>" CURRENCY>
      </td>
    </tr>
    <tr>
      <td align="right" nowrap>Commission Amount:&nbsp;</td>
      <td align="left" nowrap>
        <input disabled type="text" name="commissionAmount" size="11" maxlength="25" value="<%= commissionAmount %>" CURRENCY>
      </td>
    </tr>
    <tr>
      <td align="right" nowrap>Debit Balance Repay:&nbsp;</td>
      <td align="left" nowrap>
        <input disabled type="text" name="debitBalanceRepay" size="11" maxlength="25" value="<%= debitBalanceRepay %>" CURRENCY>
      </td>
     </tr>

    <tr>
      <td align="right" nowrap>Operator:&nbsp;</td>
      <td align="left" nowrap>
        <input type="text" name="operator" disabled value="<%= operator %>">
      </td>
      <td align="left" nowrap>Date/Time:&nbsp;</td>
      <td align="left" nowrap>
        <input type="text" name="dateTime" disabled value="<%= maintDate %>">
      </td>
      <td align="left" nowrap>
        <input disabled type="checkbox" name="allowancesStatus" <%= allowancesStatus %> >
        <a href ="javascript:showAllowances()">Allowances</a>
      </td>
    </tr>
  </table>
  <table width="100%" height="1%" border="0" cellspacing="0" cellpadding="0">
  	<tr>
  	  <td nowrap align="left">
		<input type="button" value="Save" style="background-color:#DEDEDE" onClick="saveCommHistory()">
		<input type="button" value="Adjustment" style="background-color:#DEDEDE" onClick="adjustCommHistory()">
      </td>
  	  <td nowrap align="right">
		<input type="button" value="Filter" style="background-color:#DEDEDE" onClick="showCommissionHistoryFilter()">
      </td>
  	</tr>
  </table>
  <table class="summary" width="100%" border="0" cellspacing="0" cellpadding="2">
	<tr class="heading">
	  <th align="left" width="12%">Acct Pend</th>
      <th align="left" width="12%">Updt Status</th>
	  <th align="left" width="13%">Pol Num</th>
	  <th align="left" width="13%">Eff Date</th>
	  <th align="left" width="12%">Trx Type</th>
      <th align="left" width="13%">Comm Type</th>
	  <th align="left" width="12%">Comm Amt</th>
      <th align="left" width="13%">Disb Src</th>
	</tr>
  </table>
  <span class="scrollableContent" style="border-style:solid; border-width:1;  position:relative; width:100%; height:58%; top:0; left:0; background-color:#BBBBBB">
    <table class="summary" id="commHistSummary" width="100%" style="border-style:solid; border-width:0;" cellspacing="0" cellpadding="0">
      <%
          String rowToMatch = "";
          String trClass = "default";
          String selected = "false";

          String accountPendingInd = "";
          String updateStatus = "";
          String sContractNumber = "";
          String sEffectiveDate = "";
          String sTransactionType = "";
          String sCommType = "";
          String sCommissionAmount = "0.00";
          String sCommissionHistoryPK = "";
          String sDisbSource = "";
          if (commissionHistoryVOs != null)
          {
              Map sortedHistoryBeans = sortHistoryByEffectiveDateContractNumber(commissionHistoryVOs);

              Iterator it = sortedHistoryBeans.values().iterator();

              while (it.hasNext())
              {
                  CommissionHistoryVO commHistoryVO = (CommissionHistoryVO) it.next();
                  EDITTrxHistoryVO editTrxHistoryVO = (EDITTrxHistoryVO) commHistoryVO.getParentVO(EDITTrxHistoryVO.class);
                  EDITTrxVO editTrxVO = (EDITTrxVO) editTrxHistoryVO.getParentVO(EDITTrxVO.class);
                  FinancialHistoryVO[] finHistoryVO = (FinancialHistoryVO[]) editTrxHistoryVO.getFinancialHistoryVO();
                  sTransactionType = codeTableWrapper.getCodeDescByCodeTableNameAndCode("TRXTYPE", editTrxVO.getTransactionTypeCT());
                  sCommType = Util.initString(commHistoryVO.getCommissionTypeCT(), "");

                  if (!sTransactionType.equalsIgnoreCase("NonFinancial"))
                  {
                      sCommissionAmount = Util.roundToNearestCent(commHistoryVO.getCommissionAmount()).toString();
                      ClientSetupVO clientSetupVO = (ClientSetupVO) editTrxVO.getParentVO(ClientSetupVO.class);
                      ContractSetupVO contractSetupVO = (ContractSetupVO) clientSetupVO.getParentVO(ContractSetupVO.class);
                      SegmentVO segmentVO = null;
                      if (contractSetupVO != null && contractSetupVO.getParentVOs() != null)
                      {
                          segmentVO = (SegmentVO) contractSetupVO.getParentVO(SegmentVO.class);
                      }
                      if (segmentVO != null)
                      {
                          sContractNumber = segmentVO.getContractNumber();
                      }
                      else
                      {
                         sContractNumber = "";
                      }
                  }
                  else
                  {
                      sContractNumber = "";
                      sCommissionAmount = "";
                  }

                  accountPendingInd = commHistoryVO.getAccountingPendingStatus();
                  updateStatus = commHistoryVO.getUpdateStatus();
                  sEffectiveDate = DateTimeUtil.formatYYYYMMDDToMMDDYYYY(editTrxVO.getEffectiveDate());

                  if (finHistoryVO != null && finHistoryVO.length > 0 && (sTransactionType.equalsIgnoreCase("Check") || sTransactionType.equalsIgnoreCase("BonusCheck")))
                  {
                      sDisbSource = finHistoryVO[0].getDisbursementSourceCT();
                  }
                  else
                  {
                      sDisbSource = "";
                  }

                  sCommissionHistoryPK = commHistoryVO.getCommissionHistoryPK() + "";

                  rowToMatch = sCommissionHistoryPK;
                  if (rowToMatch.equals(rowToMatchBase))
                  {
                      trClass = "highlighted";
                      selected = "true";
                  }
                  else
                  {
                      trClass = "default";
                      selected = "false";
                  }
      %>
        <tr class="<%= trClass %>" isSelected="<%= selected %>" id="<%= sCommissionHistoryPK %>"
            onClick="selectHistoryRow()" onMouseOver="highlightRow()" onMouseOut="unhighlightRow()">
          <td align="left" nowrap width="12%">
            <%= accountPendingInd %>
          </td>
          <td align="left" nowrap width="12%">
            <%= updateStatus %>
          </td>
          <td align="left" nowrap width="13%">
            <%= sContractNumber %>
          </td>
          <td align="left" nowrap width="13%">
            <%= sEffectiveDate %>
          </td>
          <td align="left" nowrap width="12%">
            <%= sTransactionType %>
          </td>
          <td align="left" nowrap width="13%">
            <%= sCommType %>
          </td>
          
          <%
              if (!sCommissionAmount.equals(""))
              {
          %>
          <td align="left" nowrap width="12%">
            <script>document.write(formatAsCurrency(<%= sCommissionAmount %>))</script>
          </td>
           <%
                   }
           %>

          <td align="left" nowrap width="13%">
            <%= sDisbSource %>
          </td>
        </tr>
      <%
                }//end while
            }//end if
      %>
    </table>
  </span>

<!-- ****** HIDDEN FIELDS ***** //-->
 <input type="hidden" name="transaction" value="">
 <input type="hidden" name="action"      value="">
 <input type="hidden" name="selectedCommissionHistoryPK" value="<%= selectedCommissionHistoryPK %>">
 <input type="hidden" name="agentPK" value="<%= agentPK %>">

</form>
</body>
</html>
