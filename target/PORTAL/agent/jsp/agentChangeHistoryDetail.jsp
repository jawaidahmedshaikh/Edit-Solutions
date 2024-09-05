<%@ page  errorPage="/common/jsp/error.jsp" autoFlush="true" %>
<%@ page import="java.util.*,
                 edit.common.*,
                 edit.common.vo.*,
                 edit.common.vo.user.*,
                 fission.beans.*,
                 fission.utility.*,
                 edit.portal.common.session.UserSession" %>

<%
    CodeTableWrapper codeTableWrapper = CodeTableWrapper.getSingleton();

    CommissionHistoryVO[] commissionHistoryVOs = (CommissionHistoryVO[]) session.getAttribute("commissionHistoryVOs");
    ChangeHistoryVO[] agentChangeHistoryVOs = (ChangeHistoryVO[])session.getAttribute("agentChangeHistoryVOs");
    ClientDetailVO clientDetailVO = (ClientDetailVO)session.getAttribute("clientDetailVO");

    String transactionType = "";
    String effectiveMonth = "";
    String effectiveDay = "";
    String effectiveYear = "";
    String tableName = "";
    String fieldName = "";
    String beforeValue = "";
    String afterValue = "";
    String agentPK = "0";
    String operator = "";
    String maintDate = "";
    String licenseNumber = "";
    String agentId = "";

    AgentVO agentVO = (AgentVO) session.getAttribute("agentVO");
    if (agentVO != null) {

        agentPK = agentVO.getAgentPK() + "";
    }
    String selectedCommissionHistoryPK = (String) request.getAttribute("selectedCommissionHistoryPK");

    if (agentChangeHistoryVOs != null)
    {
        AgentLicenseVO[] agentLicenseVOs = agentVO.getAgentLicenseVO();

        for (int i = 0; i < agentChangeHistoryVOs.length; i++)
        {
            if ((agentChangeHistoryVOs[i].getChangeHistoryPK() + "").equals(selectedCommissionHistoryPK))
            {
                for (int j = 0; j < agentLicenseVOs.length; j++)
                {
                    if ((agentChangeHistoryVOs[i].getModifiedRecordFK() + "").equals(agentLicenseVOs[j].getAgentLicensePK() + ""))
                    {
                        licenseNumber = Util.initString(agentLicenseVOs[j].getLicenseNumber(), "");
                     }
                        agentId = Util.initString(agentVO.getAgentNumber(), "");

                        EDITDate effectiveDate = new EDITDate(agentChangeHistoryVOs[i].getEffectiveDate());
                        effectiveMonth = effectiveDate.getFormattedMonth();
                        effectiveDay = effectiveDate.getFormattedDay();
                        effectiveYear = effectiveDate.getFormattedYear();

                        transactionType = "NonFinancial";
                        tableName = agentChangeHistoryVOs[i].getTableName();
                        fieldName = agentChangeHistoryVOs[i].getFieldName();
                        beforeValue = Util.initString(agentChangeHistoryVOs[i].getBeforeValue(), "");
                        afterValue = agentChangeHistoryVOs[i].getAfterValue();
                        operator = agentChangeHistoryVOs[i].getOperator();
                        if (operator == null)
                        {
                            operator = "";
                        }
                        maintDate = agentChangeHistoryVOs[i].getMaintDateTime();
                        if (maintDate == null)
                        {
                            maintDate = "";
                        }

                }
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

<script language="Javascript1.2">

	var f = null;

    var shouldShowLockAlert = true;
    var height = screen.height;
    var width  = screen.width;

    var colorMouseOver = "#00BB00";
    var colorHighlighted = "#FFFFCC";
    var colorMainEntry   = "#BBBBBB";

    function highlightRow() {

        var tdElement = window.event.srcElement;
        var currentRow = tdElement.parentElement;

        if (currentRow.tagName != "TR") {

            currentRow = currentRow.parentElement;
        }

        currentRow.style.backgroundColor = colorMouseOver;
    }

    function unhighlightRow() {

        var tdElement = window.event.srcElement;
        var currentRow = tdElement.parentElement;

        if (currentRow.tagName != "TR") {

            currentRow = currentRow.parentElement;
        }

        var className = currentRow.className;

        if (currentRow.isSelected != "true") {

            if (className == "highLighted") {

                currentRow.style.backgroundColor = colorHighlighted;
            }

            else {

                currentRow.style.backgroundColor = colorMainEntry;
            }
        }
        else {

            currentRow.style.backgroundColor = colorHighlighted;
        }
    }

	function init() {

		f = document.agentChangeHistoryForm;

		top.frames["main"].setActiveTab("historyTab");

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
	}

    function showLockAlert(){

        if (shouldShowLockAlert == true){

            alert("The Agent can not be edited.");

            return false;
        }
    }

	function selectRow() {

		var tdElement = window.event.srcElement;
		var trElement = tdElement.parentElement;

		var key = trElement.id;

		f.selectedCommissionHistoryPK.value = key;

		sendTransactionAction("AgentDetailTran", "showHistoryDetailSummary", "contentIFrame");
	}

	function showCommissionHistoryFilter()
    {
        var width = .35 * screen.width;
        var height = .20 * screen.height;

		openDialog("","commHistoryFilterDialog","left=0,top=0,resizable=no,width=" + width + ",height=" + height,"AgentDetailTran", "showCommissionHistoryFilterDialog", "commHistoryFilterDialog");
	}

	function openDialog(theURL,winName,features, transaction, action, target) {

	  dialog = window.open(theURL,winName,features);

	  sendTransactionAction(transaction, action, target);
	}

	function sendTransactionAction(transaction, action, target) {

		f.transaction.value = transaction;
		f.action.value = action;

		f.target = target;

		f.submit();
	}

</script>

<head>
<title>Agent Change History Detail</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta http-equiv="Cache-Control" content="no-store">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Expires" content="0">

<link href="/PORTAL/common/css/PORTAL.css" rel="stylesheet" type="text/css">
</head>

<body class="mainTheme" onLoad="init()" style="border-style:solid; border-width:1;">
<form name= "agentChangeHistoryForm" method="post" action="/PORTAL/servlet/RequestManager">

<input type="hidden" name="page" value="">
<jsp:include page="agentInfoHeader.jsp" flush="true"/>

<span id="mainContent" style="border-style:solid; border-width:1;  position:relative; width:100%; height:100%; top:0; left:0; z-index:0; overflow:visible">
  <table width="80%" border="0" cellspacing="8" cellpadding="5" height="40%">
    <tr>
      <td align="right" nowrap>Transaction Type:&nbsp;</td>
      <td align="left" nowrap>
        <input disabled type="text" name="transactionType" size="20" maxlength="20" value="<%= transactionType %>">
      </td>
      <td align="right" nowrap>Effective Date:&nbsp;</td>
      <td align="left" nowrap>
        <input disabled type="text" name="effectiveMonth" size="2" maxlength="2" value="<%= effectiveMonth%>">
        /
        <input disabled type="text" name="effectiveDay" size="2" maxlength="2" value="<%= effectiveDay%>">
        /
        <input disabled type="text" name="effectiveYear" size="4" maxlength="4" value="<%= effectiveYear%>">
      </td>
    </tr>
    <tr>
      <td align="right" nowrap>Agent Number:&nbsp;</td>
      <td align="left" nowrap>
        <input disabled type="text" name="agentId" size="20" value="<%= agentId%>">
      </td>
      <td align="right" nowrap>License Number:&nbsp;</td>
      <td align="left" nowrap>
        <input disabled type="text" name="licenseNumber" size="20" value="<%= licenseNumber%>">
      </td>
    </tr>
    <tr>
      <td align="right" nowrap>Table Name:&nbsp;</td>
      <td align="left" nowrap>
        <input disabled type="text" name="tableName" value="<%= tableName%>">
      </td>
      <td align="right" nowrap>Field Name:&nbsp;</td>
      <td align="left" nowrap>
        <input disabled type="text" name="fieldName" size="20" value="<%= fieldName%>">
      </td>
    </tr>
    <tr>
      <td align="right" nowrap>Before Change Value:&nbsp;</td>
      <td align="left" nowrap>
        <input disabled type="text" name="beforeValue" size="20" value="<%= beforeValue%>">
      </td>
      <td align="right" nowrap>After Change Value:&nbsp;</td>
      <td align="left" nowrap>
        <input disabled type="text" name="afterValue" size="20" value="<%= afterValue%>">
      </td>
    </tr>
    <tr>
      <td align="right" nowrap>Operator:&nbsp;</td>
      <td align="left" nowrap>
        <input disabled type="text" name="operator" value="<%= operator%>">
      </td>
      <td align="right" nowrap>Date/Time:&nbsp;</td>
      <td align="left" nowrap>
        <input disabled type="text" name="maintDate" value="<%= maintDate%>">
      </td>
    </tr>
  </table>
  <table width="100%" height="1%" border="0" cellspacing="0" cellpadding="0">
  	<tr>
  	  <td nowrap align="right">
		<input type="button" value="Filter" style="background-color:#DEDEDE" onClick="showCommissionHistoryFilter()">
      </td>
  	</tr>
  </table>
  <table class="summaryArea" id="summaryTable2" width="100%" height="58%" border="0" cellspacing="0" cellpadding="2">
	<tr height="1%">
	  <th align="left" width="12%">Acct Pend</th>
      <th align="left" width="12%">Updt Status</th>
	  <th align="left" width="13%">Pol Num</th>
	  <th align="left" width="13%">Eff Date</th>
	  <th align="left" width="12%">Trx Type</th>
      <th align="left" width="13%">Comm Type</th>
	  <th align="left" width="12%">Comm Amt</th>
      <th align="left" width="13%">Disb Src</th>
	</tr>
    <tr width="100%" height="99%">
      <td colspan="8">
        <span class="scrollableContent">
          <table class="scrollableArea" id="commissionHistorySummary" width="100%" border="0" cellspacing="0" cellpadding="0">
		    <%
			  String rowToMatch = "";
			  String trClass = "mainEntry";
			  String selected = "false";

              String accountPendingInd = "";
              String updateStatus = "";
              String sContractNumber = "";
              String sEffectiveDate = "";
			  String sTransactionType = "";
			  String sCommissionAmount = "";
              String sCommissionHistoryPK = "";
              String sCommType = "";
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
                      sCommType = commHistoryVO.getCommissionTypeCT();
                      if (sCommType == null)
                      {
                          sCommType = "";
                      }

                      if (!sTransactionType.equalsIgnoreCase("NonFinancial"))
                      {
//                          double sCommAmt = Util.roundToNearestCent(commHistoryVO.getCommissionAmount());
//                          sCommissionAmount = Util.formatDecimal("########0.00", sCommAmt);
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
                      }

                      accountPendingInd = commHistoryVO.getAccountingPendingStatus();
                      updateStatus = commHistoryVO.getUpdateStatus();

                      sEffectiveDate = DateTimeUtil.formatYYYYMMDDToMMDDYYYY(editTrxVO.getEffectiveDate());

                      if (finHistoryVO != null && finHistoryVO.length > 0 && sTransactionType.equalsIgnoreCase("Check"))
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
                          trClass = "highLighted";
                          selected = "true";
                      }
                      else
                      {
                          trClass = "mainEntry";
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
			  <td align="left" nowrap width="12%">
				<%= sCommissionAmount %>
			  </td>
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
      </td>
    </tr>
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
