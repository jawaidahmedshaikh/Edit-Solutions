<%@ page  errorPage="/common/jsp/error.jsp" autoFlush="true" %>
<%@ page import="java.util.*,
                 edit.common.*,
                 edit.common.vo.*,
                 edit.common.vo.user.*,
                 fission.beans.*,
                 fission.utility.*,
                 edit.portal.common.session.UserSession,
                 agent.ui.servlet.CommissionHistoryExtractCache,
                 edit.services.db.hibernate.SessionHelper" %>

<%
    String extractMessage = (String) request.getAttribute("extractMessage");
    if (extractMessage == null)
    {
        extractMessage = "";
    }

    CodeTableWrapper codeTableWrapper = CodeTableWrapper.getSingleton();

    // CHANGED THIS TO USE CACHE CLASS - LAZY-LOAD THIS DATA ONLY WHEN NEEDED.
    CommissionHistoryExtractCache commHistExtractCache = new CommissionHistoryExtractCache(session);
    CommissionHistoryVO[] commissionHistoryVOs = commHistExtractCache.getCommissionExtracts();

    String filterAgentId = Util.initString((String) session.getAttribute("filterAgentId"), "");
    String transactionType = Util.initString((String) request.getAttribute("transactionType"), "");
    String commProcess = "";
    String commType = Util.initString((String) request.getAttribute("commType"), "");
    String effectiveMonth = Util.initString((String) request.getAttribute("effectiveMonth"), "");
    String effectiveDay = Util.initString((String) request.getAttribute("effectiveDay"), "");
    String effectiveYear = Util.initString((String) request.getAttribute("effectiveYear"), "");
    String processMonth = Util.initString((String) request.getAttribute("processMonth"), "");
    String processDay = Util.initString((String) request.getAttribute("processDay"), "");
    String processYear = Util.initString((String) request.getAttribute("processYear"), "");
    String grossAmount = "";
    String commissionableAmount = Util.initString((String) request.getAttribute("commissionableAmount"), "");
    String commissionAmount = Util.initString((String) request.getAttribute("commissionAmount"), "");
    String payToClient = "";
    String contractNumber = "";
    String coverage = "";
    String issueState = "";
    String issueAge = "";
    String policyDur = "";
    String name = "";
    String status = "";
    String agentNumber = "";
    String allowancesStatus = "unchecked";
    String operator = "";
    String maintDate = "";
    String statementInd = "";
    String reduceTaxable = Util.initString((String) request.getAttribute("reduceTaxable"), "N");
    String reduceTaxableStatus = "unchecked";
    EDITDate commHoldReleaseDate = null;
    if (reduceTaxable.equals("Y"))
    {
        reduceTaxableStatus = "checked";
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
                PlacedAgentVO placedAgentVO = (PlacedAgentVO) commissionHistoryVOs[h].getParentVO(PlacedAgentVO.class);
                AgentContractVO agentContractVO = (AgentContractVO) placedAgentVO.getParentVO(AgentContractVO.class);
                AgentVO agentVO = (AgentVO) agentContractVO.getParentVO(AgentVO.class);
                ClientDetailVO clientDetailVO = (ClientDetailVO) placedAgentVO.getParentVO(ClientRoleVO.class).getParentVO(ClientDetailVO.class);
                EDITTrxVO editTrxVO = (EDITTrxVO) editTrxHistoryVO.getParentVO(EDITTrxVO.class);
                FinancialHistoryVO[] financialHistoryVO = editTrxHistoryVO.getFinancialHistoryVO();
                ClientSetupVO clientSetupVO = (ClientSetupVO) editTrxVO.getParentVO(ClientSetupVO.class);
                ContractSetupVO contractSetupVO = (ContractSetupVO) clientSetupVO.getParentVO(ContractSetupVO.class);
                SegmentVO segmentVO = null;
                if (contractSetupVO.getParentVOs() != null)
                {
                    segmentVO = (SegmentVO) contractSetupVO.getParentVO(SegmentVO.class);
                }

                String corporateName = clientDetailVO.getCorporateName();
                String lastName = clientDetailVO.getLastName();
                String firstName = clientDetailVO.getFirstName();
                if (lastName != null)
                {
                    name = lastName + ", " + firstName;
                }
                else
                {
                    name = corporateName;
                }
                agentNumber = agentVO.getAgentNumber();
                status = agentVO.getAgentStatusCT();
                statementInd = commissionHistoryVOs[h].getStatementInd();
                if (statementInd.equalsIgnoreCase("N"))
                {
                    statementInd = "checked";
                }
                else
                {
                    statementInd = "unchecked";
                }
                transactionType = codeTableWrapper.getCodeDescByCodeTableNameAndCode("TRXTYPE", editTrxVO.getTransactionTypeCT());
                if (!transactionType.equalsIgnoreCase("Commission Adjustment"))
                {
                    commProcess = codeTableWrapper.getCodeDescByCodeTableNameAndCode("COMMISSIONPROCESS", agentContractVO.getCommissionProcessCT());
                }
                commType = codeTableWrapper.getCodeDescByCodeTableNameAndCode("COMMISSIONTYPE", commissionHistoryVOs[h].getCommissionTypeCT());

                EDITDate effectiveDate = new EDITDate(editTrxVO.getEffectiveDate());
                effectiveMonth = effectiveDate.getFormattedMonth();
                effectiveDay = effectiveDate.getFormattedDay();
                effectiveYear = effectiveDate.getFormattedYear();

                EDITDate processDate = new EDITDateTime(editTrxHistoryVO.getProcessDateTime()).getEDITDate();
                processMonth = processDate.getFormattedMonth();
                processDay = processDate.getFormattedDay();
                processYear = processDate.getFormattedYear();

                if (financialHistoryVO != null && financialHistoryVO.length > 0)
                {
                    grossAmount = financialHistoryVO[0].getGrossAmount().toString();
                    commissionableAmount = Util.roundToNearestCent(financialHistoryVO[0].getCommissionableAmount()).toString();
                }
                commissionAmount = Util.roundToNearestCent(commissionHistoryVOs[h].getCommissionAmount()).toString();
                commHoldReleaseDate = SessionHelper.getEDITDate(commissionHistoryVOs[h].getCommHoldReleaseDate());
                reduceTaxable = commissionHistoryVOs[h].getReduceTaxable();

                if (reduceTaxable == null)
                {
                    reduceTaxable = "N";
                }
                if (reduceTaxable.equalsIgnoreCase("Y"))
                {
                    reduceTaxableStatus = "checked";
                }
                if ((segmentVO != null) && (segmentVO.getEffectiveDate() != null))
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
                maintDate = commissionHistoryVOs[h].getMaintDateTime();
                if (maintDate == null)
                {
                    maintDate = "";
                }
            }
        } 
    }

    String reduceTaxableDisabled = "Y";
    if (transactionType.equalsIgnoreCase("Commission Adjustment"))
    {
        reduceTaxableDisabled = "N";
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
            ClientSetupVO clientSetupVO = (ClientSetupVO) editTrxVO.getParentVO(ClientSetupVO.class);
            ContractSetupVO contractSetupVO = (ContractSetupVO) clientSetupVO.getParentVO(ContractSetupVO.class);
            SegmentVO segmentVO = null;
            if (contractSetupVO.getParentVOs() != null)
            {
                segmentVO = (SegmentVO) contractSetupVO.getParentVO(SegmentVO.class);
            }

            String contractNumber = "";
            if (segmentVO != null)
            {
                contractNumber = segmentVO.getContractNumber();
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

    var extractMessage = "<%= extractMessage %>";
    var reduceTaxableDisabled = "<%= reduceTaxableDisabled %>";
    var selectedCommissionHistoryPK = "<%= selectedCommissionHistoryPK %>";
    var transactionType = "<%= transactionType %>";

    var shouldShowLockAlert = true;
    var height = screen.height;
    var width  = screen.width;

	function init()
    {
		f = document.commissionExtractForm;

        if (extractMessage != "")
        {
            alert(extractMessage)
        }

        if (reduceTaxableDisabled == "Y")
        {
            f.reduceTaxableStatus.disabled = true;
        }
        else
        {
            f.reduceTaxableStatus.disabled = false;
        }

        formatCurrency();
	}

	function selectRow() {

		var tdElement = window.event.srcElement;
		var trElement = tdElement.parentElement;

		var key = trElement.id;

		f.selectedCommissionHistoryPK.value = key;

		sendTransactionAction("AgentDetailTran", "showExtractDetailSummary", "_self");
	}

    function addNewExtract()
    {
        if (f.filterAgentId.value == "")
        {
            alert("Cannot Add Commission Extract Without Filtered Agent Id");
        }
        else
        {
            sendTransactionAction("AgentDetailTran", "addNewExtract", "_self");
        }
    }

    function saveExtract()
    {
        if (selectedCommissionHistoryPK == "" &&
            transactionType != "Commission Adjustment")
        {
            alert("Extract Not Selected For Save");
        }
        else
        {
            if (f.reduceTaxableStatus.checked == true)
            {
                f.reduceTaxable.value = "Y";
            }
            else
            {
                f.reduceTaxable.value = "N";
            }

            if (f.statementInd.checked == true)
            {
                f.statementIndStatus.value = "N";
            }
            else
            {
                f.statementIndStatus.value = "Y";
            }

            f.commHoldReleaseDate.value = formatDate(f.commHoldReleaseMonth.value, f.commHoldReleaseDay.value, f.commHoldReleaseYear.value, false);

            sendTransactionAction("AgentDetailTran", "saveExtractToSummary", "_self");
        }
    }

    function cancelExtract()
    {
        sendTransactionAction("AgentDetailTran", "cancelCurrentExtract", "_self");
    }

    function deleteExtract()
    {
        if (selectedCommissionHistoryPK == "")
        {
            alert("Please Select an Extract For Deletion");
        }
        else
        {
            sendTransactionAction("AgentDetailTran", "deleteSelectedExtract", "_self");
        }
    }

    function filterExtracts()
    {
        if (f.filterAgentId.value == "")
        {
            alert("Please Enter Agent Id for Filter");
        }
        else
        {
            sendTransactionAction("AgentDetailTran", "filterExtracts", "_self");
        }
    }

    function removeExtractFilter()
    {
        sendTransactionAction("AgentDetailTran", "removeExtractFilter", "_self");
    }

	function showAllowances() {

        var width = .40 * screen.width;
        var height = .50 * screen.height;

		openDialog("extractAllowances","left=0,top=0,resizable=no", width, height);

        sendTransactionAction("AgentDetailTran", "showExtractAllowancesDialog", "extractAllowances");
	}

    function closeExtractDialog()
    {
        sendTransactionAction("AgentDetailTran", "closeExtractDialog", "contentIFrame");
        closeWindow();
    }

</script>

<head>
<title>Commission Extract Detail</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta http-equiv="Cache-Control" content="no-store">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Expires" content="0">

<link href="/PORTAL/common/css/EDITSolutions.css" rel="stylesheet" type="text/css">
</head>

<body class="mainTheme" onLoad="init()" style="border-style:solid; border-width:1;">
<form name= "commissionExtractForm" method="post" action="/PORTAL/servlet/RequestManager">

  <input type="hidden" name="page" value="">
  <jsp:include page="agentInfoHeader.jsp" flush="true"/>

  <table width="80%" height="40%" border="0" cellspacing="0" cellpadding="5">
    <tr>
      <td align="right" nowrap>Transaction Type:&nbsp;</td>
      <td align="left" nowrap>
        <input disabled type="text" name="transactionType" size="22" maxlength="22" value="<%= transactionType %>">
      </td>
      <td align="left" nowrap>Commission Process:&nbsp;
        <input disabled type="text" name="commProcess" size="20" maxlength="20" value="<%= commProcess %>">
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
      <td align="left" nowrap>Commission Type:&nbsp;
        <input disabled type="text" name="commType" size="20" maxlength="20" value="<%= commType %>">
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
      <td align="left" rowspan="6">
        <fieldset style="border-style:solid; border-width:1px; border-color:gray">
        <legend align="top"><font color="black">Policy Info</font></legend>
        <span style="position:relative; width:100%; top:0; left:0; z-index:0; overflow:visible">
          <table width="100%" height="100%" border="0" cellspacing="0" cellpadding="5">
            <tr>
              <td align="right" nowrap>Policy Number:&nbsp;</td>
              <td align="left" nowrap>
                <input disabled type="text" name="contractNumber" size="15" maxlength="15" value="<%= contractNumber %>">            </tr>
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
      <td align="right" nowrap>Gross Trx Amount:&nbsp;</td>
      <td align="left" nowrap>
        <input disabled type="text" name="grossAmount" size="11" maxlength="11" value="<%= grossAmount %>" CURRENCY>
      </td>
    </tr>
    <tr>
      <td align="right" nowrap>Commissionable Amount:&nbsp;</td>
      <td align="left" nowrap>
        <input type="text" name="commissionableAmount" size="11" maxlength="11" value="<%= commissionableAmount %>" CURRENCY>
      </td>
    </tr>
    <tr>
      <td align="right" nowrap>Commission Amount:&nbsp;</td>
      <td align="left" nowrap>
        <input type="text" name="commissionAmount" size="11" maxlength="11" value="<%= commissionAmount %>" CURRENCY>
      </td>
    </tr>
    <tr>
      <td align="right" nowrap>Hold Release Date:&nbsp;</td>
      <td align="left" nowrap>
        <input type="text" name="commHoldReleaseMonth" size="2" maxlength="2" value="<%= commHoldReleaseDate == null ? "" : commHoldReleaseDate.getFormattedMonth() %>">
        /
        <input type="text" name="commHoldReleaseDay" size="2" maxlength="2" value="<%= commHoldReleaseDate == null ? "" : commHoldReleaseDate.getFormattedDay() %>">
        /
        <input type="text" name="commHoldReleaseYear" size="4" maxlength="4" value="<%= commHoldReleaseDate == null ? "" : commHoldReleaseDate.getFormattedYear() %>">
      </td>
    </tr>
    <tr>
      <td align="left" nowrap>&nbsp;</td>
    </tr>
    <tr>
      <td align="right" nowrap>Pay To Client/Agent Id:&nbsp;</td>
      <td align="left" nowrap>
        <input disabled type="text" name="payToClient" size="15" maxlength="15" value="<%= payToClient %>">
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Reduce Taxable&nbsp;
        <input type="checkbox" name="reduceTaxableStatus" <%= reduceTaxableStatus %>>
      </td>
      <td align="right" nowrap>
        Suppress On Commission Statement&nbsp;
        <input type="checkbox" name="statementInd" <%= statementInd %>>
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        <input disabled type="checkbox" name="allowancesStatus" <%= allowancesStatus %> >
        <a href ="javascript:showAllowances()">Allowances</a>
      </td>
    </tr>
    <tr>
    <tr>
      <td align="right" nowrap>Operator:&nbsp;
      <td align="left" nowrap>
        <input type="text" name="operator" disabled value="<%= operator %>">
      </td>
      <td align="right" nowrap>Date/Time:&nbsp;
        <input type="text" name="dateTime" disabled value="<%= maintDate %>">
      </td>
    </tr>
  </table>
  <table width="100%" height="2%" border="0" cellspacing="0" cellpadding="0">
	<tr>
	  <td align="left">
        <input type="button" name="add" value="Add" onClick="addNewExtract()">
		<input type="button" name="save" value="Save" onClick="saveExtract()">
		<input type="button" name="cancel" value="Cancel" onClick="cancelExtract()">
		<input type="button" name="delete" value="Delete" onClick="deleteExtract()">
	  </td>
      <td nowrap align="right">
        <input type="text" name="filterAgentId" size="15" maxlength="15" value="<%= filterAgentId %>">
        <input type="button" value="Filter" onClick="filterExtracts()">
        <input type="button" value="Remove Filter" onClick="removeExtractFilter()">
	</tr>
  </table>
  <table class="summary" width="100%" height="2%" border="0" cellspacing="0" cellpadding="0">
    <tr class="heading">
	  <th align="left" width="11%">Acct Pend</th>
      <th align="left" width="12%">Updt Status</th>
	  <th align="left" width="13%">Pol Num</th>
	  <th align="left" width="13%">Eff Date</th>
	  <th align="left" width="13%">Trx Type</th>
      <th align="left" width="14%">Comm Type</th>
	  <th align="left" width="14%">Comm Amt</th>
	</tr>
  </table>
  <span class="scrollableContent" style="border-style:solid; border-width:1;  position:relative; width:100%; height:30%; top:0; left:0; background-color:#BBBBBB">
    <table class="summary" id="commExtractSummary" width="100%" style="border-style:solid; border-width:0;" cellspacing="0" cellpadding="0">
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
          String sCommissionAmount = "";
          String sCommissionHistoryPK = "";
          if (commissionHistoryVOs != null)
          {
              Map sortedHistoryBeans = sortHistoryByEffectiveDateContractNumber(commissionHistoryVOs);

              Iterator it = sortedHistoryBeans.values().iterator();

              while (it.hasNext())
              {
                  CommissionHistoryVO commHistoryVO = (CommissionHistoryVO) it.next();
                  EDITTrxHistoryVO editTrxHistoryVO = (EDITTrxHistoryVO) commHistoryVO.getParentVO(EDITTrxHistoryVO.class);
                  EDITTrxVO editTrxVO = (EDITTrxVO) editTrxHistoryVO.getParentVO(EDITTrxVO.class);
                  ClientSetupVO clientSetupVO = (ClientSetupVO) editTrxVO.getParentVO(ClientSetupVO.class);
                  ContractSetupVO contractSetupVO = (ContractSetupVO) clientSetupVO.getParentVO(ContractSetupVO.class);
                  SegmentVO segmentVO = null;
                  if (contractSetupVO.getParentVOs() != null)
                  {
                      segmentVO = (SegmentVO) contractSetupVO.getParentVO(SegmentVO.class);
                  }

                  accountPendingInd = commHistoryVO.getAccountingPendingStatus();
                  updateStatus = commHistoryVO.getUpdateStatus();
                  sEffectiveDate = DateTimeUtil.formatYYYYMMDDToMMDDYYYY(editTrxVO.getEffectiveDate());
                  if (segmentVO != null)
                  {
                      sContractNumber = segmentVO.getContractNumber();
                  }
                  else
                  {
                      sContractNumber = "";
                  }
                  sTransactionType = codeTableWrapper.getCodeDescByCodeTableNameAndCode("TRXTYPE", editTrxVO.getTransactionTypeCT());
                  sCommType = commHistoryVO.getCommissionTypeCT();
                  sCommissionAmount = Util.roundToNearestCent(commHistoryVO.getCommissionAmount()).toString();
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
            onClick="selectRow()" onMouseOver="highlightRow()" onMouseOut="unhighlightRow()">
          <td align="left" nowrap width="12%">
            <%= accountPendingInd %>
          </td>
          <td align="left" nowrap width="12%">
            <%= updateStatus %>
          </td>
          <td align="left" nowrap width="14%">
            <%= sContractNumber %>
          </td>
          <td align="left" nowrap width="14%">
            <%= sEffectiveDate %>
          </td>
          <td align="left" nowrap width="14%">
            <%= sTransactionType %>
          </td>
          <td align="left" nowrap width="14%">
            <%= sCommType %>
          </td>
          <td align="left" nowrap width="14%">
            <script>document.write(formatAsCurrency(<%= sCommissionAmount %>))</script>
          </td>
        </tr>
      <%
                }//end while
            }//end if
      %>
    </table>
  </span>
  <table id="closeTable" width="100%" height="2%" border="0" cellspacing="0" cellpadding="0">
    <tr>
	  <td colspan="8" nowrap align="right" bgcolor="#99BBBB">
        <input type="button" name="close" value="Close" onClick="closeExtractDialog()">
      </td>
    </tr>
  </table>

<!-- ****** HIDDEN FIELDS ***** //-->
 <input type="hidden" name="transaction" value="">
 <input type="hidden" name="action"      value="">
 <input type="hidden" name="selectedCommissionHistoryPK" value="<%= selectedCommissionHistoryPK %>">
 <input type="hidden" name="transactionType" value="<%= transactionType %>">
 <input type="hidden" name="commType" value="<%= commType %>">
 <input type="hidden" name="reduceTaxable" value="">
 <input type="hidden" name="statementIndStatus" value="">
 <input type="hidden" name="commHoldReleaseDate" value="">

</form>
</body>
</html>
