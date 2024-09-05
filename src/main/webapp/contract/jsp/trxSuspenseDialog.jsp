<!-- ****** JAVA CODE ***** //-->

<%@ page  errorPage="/common/jsp/error.jsp" autoFlush="true" %>
<%@ page import="java.util.*,
                 edit.common.*,
                 edit.common.vo.*,
                 edit.common.vo.user.*,
                 fission.beans.*,
                 edit.portal.common.session.UserSession,
                 event.Suspense,
                 fission.utility.*" %>
<!--
 * User: unknown
 * Date: Jan 1, 2000
 * Time: unknown
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 --> 
<jsp:useBean id="formBean"
   class="fission.beans.PageBean" scope="request"/>
<%
    String trxSuspenseMessage = Util.initString((String) request.getAttribute("trxSuspenseMessage"), "");

    String scrollingTrxPageSize = (String) request.getAttribute("scrollingTrxPageSize");
    String beginScrollingTrxPK = (String) request.getAttribute("beginScrollingTrxPK");
    String endScrollingTrxPK = (String) request.getAttribute("endScrollingTrxPK");

    String filter             = formBean.getValue("filter");
    String filterMessage      = formBean.getValue("filterMessage");
    String segmentFK          = formBean.getValue("segmentFK");
    String selectedSegmentPK  = formBean.getValue("selectedSegmentPK");
    String editTrxPK          = formBean.getValue("editTrxPK");
    String segmentName        = formBean.getValue("segmentName");
    String filterAllocPct     = formBean.getValue("filterAllocPct");
    String filterAllocDollars = formBean.getValue("filterAllocDollars");
    String costBasis  	      = formBean.getValue("costBasis");
    String amountReceived     = formBean.getValue("amountReceived");
    String payeeIndStatus     = formBean.getValue("payeeIndStatus");
    String investmentIndStatus = formBean.getValue("investmentIndStatus");
    String deathStatusEnabled = formBean.getValue("deathStatusEnabled");
    String deathStatus        = formBean.getValue("deathStatus");
    String transactionType    = formBean.getValue("transactionType");
    String rowId              = formBean.getValue("rowId");

    String companyStructureId = formBean.getValue("companyStructureId");
    String amtNeeded          = formBean.getValue("amountNeeded");
    String amtUsed            = formBean.getValue("amountUsed");

    CodeTableWrapper codeTableWrapper = CodeTableWrapper.getSingleton();
    CodeTableVO[] depositTypes = null;
    if (Util.isANumber(companyStructureId))
    {
        depositTypes = codeTableWrapper.getCodeTableEntries("DEPOSITTYPE", Long.parseLong(companyStructureId));
    }
    else
    {
        depositTypes = codeTableWrapper.getCodeTableEntries("DEPOSITTYPE");
    }

    DepositsVO[] depositsVOs = (DepositsVO[]) session.getAttribute("trxDepositsVOs");

    Suspense activeSuspense = (Suspense) request.getAttribute("activeSuspense");

    String depositType       = Util.initString(formBean.getValue("depositType"), "");
    //  OldCompany data displays in a field labeled ExchangeCompamy
    String oldCompany        = "";
    //  OldPolicyNumber displays in a field labeled ExchangePolicy
    String oldPolicyNumber   = "";
    String depositCostBasis  = "";
    String dateReceivedDate = "";
    String depositAmtReceived = "";
    String taxYear           = "";
    String suspenseFK        = "";
    String selectedDepositsPK = (String) request.getAttribute("selectedDepositsPK");
    String exchangePolicyEffectiveDate  = "";
    String exchangeIssueAge             = "";
    String exchangeDuration             = "";
    String preTEFRAGain = "";
    String preTEFRAAmount = "";
    String postTEFRAGain = "";
    String postTEFRAAmount = "";
    String exchangeLoanAmount = "";

    if (selectedDepositsPK == null) {

        selectedDepositsPK = "";
    }

    DepositsVO currentDepositVO = (DepositsVO) session.getAttribute("currentTrxDepositVO");
    if (currentDepositVO  != null)
    {
        if (currentDepositVO.getDepositsPK() > 0)
        {
            selectedDepositsPK = currentDepositVO.getDepositsPK() + "";
        }
    }

    if (depositsVOs != null)
    {
        for (int i = 0; i < depositsVOs.length; i++)
        {
            if ((depositsVOs[i].getDepositsPK() + "").equals(selectedDepositsPK))
            {
                depositType = depositsVOs[i].getDepositTypeCT();
                oldCompany = depositsVOs[i].getOldCompany();
                oldPolicyNumber = depositsVOs[i].getOldPolicyNumber();
                
                if (depositsVOs[i].getCostBasis() != null)
                {
                    depositCostBasis = depositsVOs[i].getCostBasis().toString();
                }

                dateReceivedDate = DateTimeUtil.formatYYYYMMDDToMMDDYYYY(depositsVOs[i].getDateReceived());
                
                depositAmtReceived = depositsVOs[i].getAmountReceived().toString();
                taxYear = depositsVOs[i].getTaxYear() + "";
                suspenseFK = depositsVOs[i].getSuspenseFK() + "";

                exchangePolicyEffectiveDate = DateTimeUtil.formatYYYYMMDDToMMDDYYYY(depositsVOs[i].getExchangePolicyEffectiveDate());

                exchangeIssueAge = depositsVOs[i].getExchangeIssueAge() + "";
                exchangeDuration = depositsVOs[i].getExchangeDuration() + "";
                preTEFRAGain = new EDITBigDecimal(depositsVOs[i].getPreTEFRAGain()).trim();
                preTEFRAAmount = new EDITBigDecimal(depositsVOs[i].getPreTEFRAAmount()).toString();
                postTEFRAGain = new EDITBigDecimal(depositsVOs[i].getPostTEFRAGain()).trim();
                postTEFRAAmount = new EDITBigDecimal(depositsVOs[i].getPostTEFRAAmount()).toString();
                exchangeLoanAmount = new EDITBigDecimal(depositsVOs[i].getExchangeLoanAmount()).toString();
                break;
            }
        }
    }

    if (currentDepositVO != null)
    {
        depositType = currentDepositVO.getDepositTypeCT();
        oldCompany = currentDepositVO.getOldCompany();
        oldPolicyNumber = currentDepositVO.getOldPolicyNumber();
        exchangePolicyEffectiveDate = DateTimeUtil.formatYYYYMMDDToMMDDYYYY(currentDepositVO.getExchangePolicyEffectiveDate());

        exchangeIssueAge = currentDepositVO.getExchangeIssueAge()+"";
        exchangeDuration = currentDepositVO.getExchangeDuration()+"";
        depositCostBasis = currentDepositVO.getCostBasis().toString();

        dateReceivedDate = DateTimeUtil.formatYYYYMMDDToMMDDYYYY(currentDepositVO.getDateReceived());

        depositAmtReceived = currentDepositVO.getAmountReceived().toString();
        taxYear = currentDepositVO.getTaxYear() + "";
        suspenseFK = currentDepositVO.getSuspenseFK() + "";
        preTEFRAGain = new EDITBigDecimal(currentDepositVO.getPreTEFRAGain()).trim();
        preTEFRAAmount = new EDITBigDecimal(currentDepositVO.getPreTEFRAAmount()).toString();
        postTEFRAGain = new EDITBigDecimal(currentDepositVO.getPostTEFRAGain()).trim();
        postTEFRAAmount = new EDITBigDecimal(currentDepositVO.getPostTEFRAAmount()).toString();
        exchangeLoanAmount = new EDITBigDecimal(currentDepositVO.getExchangeLoanAmount()).toString();
    }

    if (activeSuspense != null)
    {
        oldCompany = Util.initString(activeSuspense.getExchangeCompany(), "");
        oldPolicyNumber = Util.initString(activeSuspense.getExchangePolicy(), "");
        depositCostBasis = activeSuspense.getCostBasis().toString();

        dateReceivedDate = DateTimeUtil.formatYYYYMMDDToMMDDYYYY(activeSuspense.getEffectiveDate().getFormattedDate());

        depositAmtReceived = activeSuspense.getSuspenseAmount().toString();
        taxYear = activeSuspense.getTaxYear() + "";
        suspenseFK = activeSuspense.getSuspensePK().toString();
        preTEFRAGain = activeSuspense.getPreTEFRAGain().toString();
        preTEFRAAmount = activeSuspense.getPreTEFRAAmount().toString();
        postTEFRAGain = activeSuspense.getPostTEFRAGain().toString();
        postTEFRAAmount = activeSuspense.getPostTEFRAAmount().toString();
    }

	String rowToMatchBase     = selectedDepositsPK;
%>

<html>

<script src="/PORTAL/common/javascript/commonJavascriptFunctions.js"></script>
<script src="/PORTAL/common/javascript/formatDateFunctions.js"></script>
<script src="/PORTAL/common/javascript/calendarSelector.js"></script>

<script src="/PORTAL/common/javascript/widgetFunctions.js"></script>
<script src="/PORTAL/common/javascript/scrollTable.js"></script>


<script language="Javascript1.2">

	var f = null;

    var trxSuspenseMessage = "<%= trxSuspenseMessage %>";
    var suspenseFK = "<%= suspenseFK %>";
    var selectedDepositsPK = "<%= selectedDepositsPK %>";
    var depositAmtReceived = "<%= depositAmtReceived %>";

	function init()
    {
		f = document.depositForm;

        if (trxSuspenseMessage != "")
        {
            alert(trxSuspenseMessage);
        }

        formatCurrency();

        // Initialize scroll tables
        initScrollTable(document.getElementById("TransactionSuspenseTableModelScrollTable"));
	}

    function showLockAlert()
    {
        if (shouldShowLockAlert == true)
        {
            alert("The Contract can not be edited.");

            return false;
        }
    }

    function onTableRowSingleClick(tableId)
    {
        sendTransactionAction("ContractDetailTran", "showSelectedTrxSuspense", "_self");
    }

	function cancelDepositInfo()
    {
        sendTransactionAction("ContractDetailTran", "clearTrxSuspenseFormOnCancel", "_self");
	}

	function saveTrxSuspenseSelectionToSummary()
    {
        if (suspenseFK == "")
        {
            alert("Please Select Suspense");
        }
        else if (f.depositType.selectedIndex == 0)
        {
            alert("Please Select Deposit Type");
        }
        else
        {
            sendTransactionAction("ContractDetailTran", "saveTrxSuspenseSelectionToSummary", "_self");
        }
	}

	function deleteSelectedDeposit()
    {
        if (selectedDepositsPK == "")
        {
            alert("Deposit Record Not Selected - Nothing to Delete");
        }
        else
        {
            var shouldDelete = confirm("Deposit Will Be Permanently Deleted. Continue?");

            if (shouldDelete)
            {
                sendTransactionAction("ContractDetailTran", "deleteSelectedDeposit", "_self");
            }
        }
	}

	function selectDepositRow()
    {
		var tdElement = window.event.srcElement;
		var trElement = tdElement.parentElement;

		var depositsPK = trElement.id;
		f.selectedDepositsPK.value = depositsPK;

		sendTransactionAction("ContractDetailTran", "showTrxDepositDetailSummary", "_self");
	}

    function closeTrxSuspenseDialog()
    {
        sendTransactionAction("ContractDetailTran", "closeTrxSuspenseDialog", "filterAllocDialog");
        closeWindow();
    }

</script>

<head>
<title>Transaction Suspense</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta http-equiv="Cache-Control" content="no-store">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Expires" content="0">

<link href="/PORTAL/common/css/EDITSolutions.css" rel="stylesheet" type="text/css">
</head>

<body class="mainTheme" onLoad="init()" style="border-style:solid; border-width:1;">
<form name= "depositForm" method="post" action="/PORTAL/servlet/RequestManager">

<span id="mainContent" style="border-style:solid; border-width:1; position:relative; width:100%; height:100%; top:0; left:0; z-index:0; overflow:visible;  background-color:#DDDDDD">
  <table width="100%" height="33%" border="0" cellspacing="0" cellpadding="3">
    <tr>
      <td>Amount Needed:
        <input type="text" name="amountNeeded" disabled size="15" maxlength="15" value="<%= amtNeeded %>" CURRENCY>
      </td>
      &nbsp;&nbsp;
      <td>Amount Used:
        <input type="text" name="amountUsed" disabled size="15" maxlength="15" value="<%= amtUsed %>" CURRENCY>
      </td>
      <td>&nbsp;</td>
    </tr>
    <tr valign="top">
      <td align="left" nowrap colspan="3">
      <table width="100%" height="100%" border="0" cellspacing="0" cellpadding="3">
        <tr>
          <td align="left" nowrap>Deposit Type:&nbsp
            <select name="depositType" tabindex="1">
              <option> Please Select </option>
              <%
                for (int i = 0; i < depositTypes.length; i++) {

                    String codeTablePK = depositTypes[i].getCodeTablePK() + "";
                    String codeDesc    = depositTypes[i].getCodeDesc();
                    String code        = depositTypes[i].getCode();

                    if (depositType != null &&
                            depositType.equalsIgnoreCase(code)) {

                        out.println("<option selected name=\"id\" value=\"" + codeTablePK+ "\">" + codeDesc + "</option>");
                    }
                    else  {

                        out.println("<option name=\"id\" value=\"" + codeTablePK + "\">" + codeDesc + "</option>");
                    }
                }
              %>
            </select>
          </td>
        </tr>
      </table>
      </td>
    </tr>
    <tr>
      <td width="33%">
        <span style="border-style:solid; border-width:1;  position:relative; width:100%; height:100%; top:0; left:0">
        <table width="100%" height="100%" border="0" cellspacing="0" cellpadding="3">
          <tr>
            <td align="right" nowrap>Suspense Amount:&nbsp;</td>
            <td align="left" nowrap>
              <input disabled type="text" name="amountReceived" maxlength="11" size="11" value="<%= depositAmtReceived %>" CURRENCY>
            </td>
          </tr>
          <tr>
            <td align="right" nowrap>Cost Basis $:&nbsp;</td>
            <td align="left" nowrap>
              <input type="text" name="costBasis" tabindex = "3" maxlength="11" size="11" value="<%= depositCostBasis %>" CURRENCY>
            </td>
          </tr>
          <tr>
            <td align="right" nowrap>Date Received:&nbsp;</td>
            <td align="left" nowrap>
              <input disabled type="text" name="dateReceivedDate" maxlength="10" size="10" value="<%= dateReceivedDate %>">
            </td>
          </tr>
          <tr>
            <td align="right" nowrap>Tax Year:&nbsp;</td>
            <td align="left" nowrap>
              <input disabled type="text" name="taxYear" maxlength="4" size="4" value="<%= taxYear %>">
            </td>
          </tr>
          <tr>
            <td align="right" nowrap colspan="2">&nbsp;</td>
          </tr>
        </table>
        </span>
      </td>
      <td widht="34%">
        <span style="border-style:solid; border-width:1;  position:relative; width:100%; height:100%; top:0; left:0">
        <table width="100%" height="100%" border="0" cellspacing="0" cellpadding="3">
          <tr>
            <td align="right" nowrap>Exchange Company:&nbsp;</td>
            <td align="left" nowrap>
              <input type="text" name="oldCompany" tabindex="4" maxlength="30" size="30" value="<%= Util.initString(oldCompany,"") %>">
            </td>
          </tr>
          <tr>
            <td align="right" nowrap>Exchange Policy Number:&nbsp;</td>
            <td align="left" nowrap colspan="3">
              <input type="text" name="oldPolicyNumber" tabindex="5" maxlength="15" size="15" value="<%=Util.initString(oldPolicyNumber,"")%>">
            </td>
          </tr>
          <tr>
            <td align="right" nowrap>Exchange Policy Effective Date:&nbsp;</td>
            <td align="left" nowrap>
               <input type="text" name="exchangePolicyEffectiveDate" value="<%= exchangePolicyEffectiveDate %>" size='10' maxlength="10" onKeyUp="DateFormat(this,this.value,event,false)" onBlur="DateFormat(this,this.value,event,true)">
               <a href="javascript:show_calendar('f.exchangePolicyEffectiveDate', f.exchangePolicyEffectiveDate.value);"><img src="/PORTAL/common/images/calendarIcon.gif" width="16" height="16" border="0" alt="Select a date from the calendar"></a>
            </td>
          </tr>
          <tr>
            <td align="right" nowrap>Exchange Issue Age:&nbsp;</td>
            <td align="left" nowrap>
              <input type="text" name="exchangeIssueAge" tabindex="9" maxlength="15" size="15" value="<%= exchangeIssueAge %>">
            </td>
          </tr>
          <tr>
            <td align="right" nowrap>Exchange Duration:&nbsp;</td>
            <td align="left" nowrap>
              <input type="text" name="exchangeDuration" tabindex="10" maxlength="15" size="15" value="<%= exchangeDuration %>">
            </td>
          </tr>
        </table>
        </span>
      </td>
      <td width="33%">
        <span style="border-style:solid; border-width:1;  position:relative; width:100%; height:100%; top:0; left:0">
        <table width="100%" height="100%" border="0" cellspacing="0" cellpadding="3">
          <tr>
            <td align="right" nowrap>
                Pre TEFRA Gain:&nbsp;
            </td>
            <td align="left" nowrap>
                <input type="text" name="preTEFRAGain" tabindex="11" size="15" value="<%= preTEFRAGain %>">
            </td>
        </tr>
        <tr>
            <td align="right" nowrap>
                Pre TEFRA Amount:&nbsp;
            </td>
            <td align="left" nowrap>
                <input type="text" name="preTEFRAAmount" tabindex="12" maxlength="11" size="15" value="<%= preTEFRAAmount %>" CURRENCY>
            </td>
        </tr>
        <tr>
            <td align="right" nowrap>
                Post TEFRA Gain:&nbsp;
            </td>
            <td align="left" nowrap>
                <input type="text" name="postTEFRAGain" tabindex="13" size="15" value="<%= postTEFRAGain %>">
            </td>
        </tr>
        <tr>
            <td align="right" nowrap>
                Post TEFRA Amount:&nbsp;
            </td>
            <td align="left" nowrap>
                <input type="text" name="postTEFRAAmount" tabindex="14" maxlength="11" size="15" value="<%= postTEFRAAmount %>" CURRENCY>
            </td>
        </tr>
        <tr>
           <td align="right" nowrap>
               Exchange Loan Amount:&nbsp;
           </td>
           <td align="left" nowrap>
               <input type="text" name="exchangeLoanAmount" tabindex="16" maxlength="11" size="15" value="<%= exchangeLoanAmount %>" CURRENCY>
           </td>
       </tr>

        </table>
        </span>
      </td>
    </tr>
  </table>

  <br>
  <h3 align="center">- Deposits -</h3>

  <table width="100%" height="2%" border="0" cellspacing="0" cellpadding="0">
	<tr>
	  <td nowrap align="left">
		<input type="button" value="   Save  " style="background-color:#DEDEDE" onClick="saveTrxSuspenseSelectionToSummary()">
		<input type="button" value="  Cancel " style="background-color:#DEDEDE" onClick="cancelSuspenseInfo()">
		<input type="button" value="  Delete " style="background-color:#DEDEDE" onClick="deleteSelectedDeposit()">
	  </td>
	</tr>
  </table>
  <table class="summary" id="summaryTable" width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr class="heading">
      <th width="20%" align="left">Exchange Company</th>
      <th width="20%" align="left">Exchange Policy #</th>
      <th width="20%" align="left">Anticipated Amt</th>
      <th width="20%" align="left">Amt Received</th>
      <th width="20%" align="left">Dt Rcvd</th>
    </tr>
  </table>
  <span class="scrollableContent" style="border-style:solid; border-width:1;  position:relative; width:100%; height:10%; top:0; left:0; background-color:#BBBBBB">
    <table class="summary" id="trxDepositSummary" width="100%" style="border-style:solid; border-width:0;" cellspacing="0" cellpadding="0">
        <%
          String rowToMatch = "";
          String trClass = "default";
          boolean selected = false;

          if (depositsVOs != null)
          {
              for (int i = 0; i < depositsVOs.length; i++)
              {
                  if (!depositsVOs[i].getVoShouldBeDeleted())
                  {
                      String sOldCompany = depositsVOs[i].getOldCompany();
                      String sOldPolicyNumber = depositsVOs[i].getOldPolicyNumber();
                      String sAnticipatedAmount = depositsVOs[i].getAnticipatedAmount().toString();
                      String sAmountReceived = depositsVOs[i].getAmountReceived().toString();
                      String sDateReceived = DateTimeUtil.formatYYYYMMDDToMMDDYYYY(depositsVOs[i].getDateReceived());
                      String sDepositsPK = depositsVOs[i].getDepositsPK() + "";

                      rowToMatch = sDepositsPK;
                      if (rowToMatch.equals(rowToMatchBase))
                      {
                          trClass = "highlighted";
                          selected = true;
                      }
                      else
                      {
                          trClass = "default";
                          selected = false;
                      }
                      sOldCompany       = Util.initString(sOldCompany,"");
                      sOldPolicyNumber  = Util.initString(sOldPolicyNumber,"");
        %>
        <tr class="<%= trClass %>" isSelected="<%= selected %>" id="<%= sDepositsPK %>"
            onMouseOver="highlightRow()" onMouseOut="unhighlightRow()" onClick="selectDepositRow()">
          <td width="20%" align="left" nowrap>
            <%= sOldCompany %>
          </td>
          <td width="20%" align="left" nowrap>
            <%= sOldPolicyNumber %>
          </td>
          <td width="20%" align="left" nowrap>
            <script>document.write(formatAsCurrency(<%= sAnticipatedAmount %>))</script>
          </td>
          <td width="20%" align="left" nowrap>
            <script>document.write(formatAsCurrency(<%= sAmountReceived %>))</script>
          </td>
          <td width="20%" align="left" nowrap>
            <%= sDateReceived %>
          </td>
        </tr>
        <%
                  } // end if
              } // end for
          } // end if
        %>
    </table>
  </span>

  <br>
  <h3 align="center">- Suspense -</h3>
  <br>
<%-- ********** BEGIN Suspense Summary Area ********** --%>

<jsp:include page="/common/jsp/widget/tableWidget.jsp" flush="true">
  <jsp:param name="tableId" value="TransactionSuspenseTableModel"/>
  <jsp:param name="tableHeight" value="20"/>
  <jsp:param name="multipleRowSelect" value="false"/>
  <jsp:param name="singleOrDoubleClick" value="single"/>
</jsp:include>

<%-- ********** END Suspense Summary Area ********** --%>

  <table id="table3" width="100%" border="0" cellspacing="6" cellpadding="0">
	<tr>
	  <td align="right" nowrap colspan="2">
		<input type="button" name="close" value="Close" onClick ="closeTrxSuspenseDialog()">
	  </td>
	</tr>
  </table>
</span>


<!-- ****** HIDDEN FIELDS ***** //-->
 <input type="hidden" name="transaction" value="">
 <input type="hidden" name="action"      value="">

 <input type="hidden" name="selectedDepositsPK" value="<%= selectedDepositsPK %>">

 <input type="hidden" name="scrollingTrxPageSize" value="<%= scrollingTrxPageSize %>">
 <input type="hidden" name="beginScrollingTrxPK" value="<%= beginScrollingTrxPK %>">
 <input type="hidden" name="endScrollingTrxPK" value="<%= endScrollingTrxPK %>">

 <input type="hidden" name="suspenseFK" value="<%= suspenseFK %>">
 <input type="hidden" name="dateReceivedDate" value="<%= dateReceivedDate %>">
 <input type="hidden" name="depositAmtReceived" value="<%= depositAmtReceived %>">

 <input type="hidden" name="amountNeeded" value="<%= amtNeeded %>">
 <input type="hidden" name="amountUsed" value="<%= amtUsed %>">

 <input type="hidden" name="filter" value="<%= filter %>">
 <input type="hidden" name="filterMessage" value="<%= filterMessage %>">
 <input type="hidden" name="segmentName" value="<%= segmentName %>">
 <input type="hidden" name="segmentFK" value="<%= segmentFK %>">
 <input type="hidden" name="selectedSegmentPK" value="<%= selectedSegmentPK %>">
 <input type="hidden" name="editTrxPK" value="<%= editTrxPK %>">
 <input type="hidden" name="companyStructureId" value="<%= companyStructureId %>">
 <input type="hidden" name="filterAllocPct" value="<%= filterAllocPct %>">
 <input type="hidden" name="filterAllocDollars" value="<%= filterAllocDollars %>">
 <input type="hidden" name="costBasis" value="<%= costBasis %>">
 <input type="hidden" name="amountReceived" value="<%= amountReceived %>">
 <input type="hidden" name="payeeIndStatus" value="<%= payeeIndStatus %>">
 <input type="hidden" name="investmentIndStatus" value="<%= investmentIndStatus %>">
 <input type="hidden" name="deathStatusEnabled" value="<%= deathStatusEnabled %>">
 <input type="hidden" name="deathStatus" value="<%= deathStatus %>">
 <input type="hidden" name="transactionType" value="<%= transactionType %>">
 <input type="hidden" name="rowId" value="<%= rowId %>">
 <input type="hidden" name="taxYear" value="<%= taxYear %>">

</form>
</body>
</html>
