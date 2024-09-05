<%@ page  errorPage="/common/jsp/error.jsp" autoFlush="true" %>
<%@ page import="java.util.*,
                 edit.common.*,
                 edit.common.vo.*,
                 edit.common.vo.user.*,
                 fission.beans.*,
                 edit.portal.common.session.UserSession,
                 java.text.DecimalFormat,
                 fission.utility.Util,
                 java.math.BigDecimal" %>

<!--
 * User: unknown
 * Date: Jan 1, 2000
 * Time: unknown
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 -->
<%
    SessionBean contractMainSessionBean = (SessionBean) session.getAttribute("contractMainSessionBean");
    SessionBean contractFundActivities = (SessionBean) session.getAttribute("contractFundActivities");
    SessionBean contractHistories = (SessionBean) session.getAttribute("contractHistories");
	PageBean formBean      = contractHistories.getPageBean("fh");

    CodeTableWrapper codeTableWrapper = CodeTableWrapper.getSingleton();
    String companyStructureId = contractMainSessionBean.getPageBean("formBean").getValue("companyStructureId");

    CodeTableVO[] options  = codeTableWrapper.getCodeTableEntries("OPTIONCODE", Long.parseLong(companyStructureId));
    CodeTableVO[] reversalReasonCodes = codeTableWrapper.getCodeTableEntries("REVERSALREASONCODE", Long.parseLong(companyStructureId));

//	PageBean formBean      = contractMainSessionBean.getPageBean("historyFormBean");

    String editTrxPK = formBean.getValue("editTrxPK");
    String editTrxHistoryPK = formBean.getValue("editTrxHistoryPK");
 	String effectiveDate   = formBean.getValue("effectiveDate");

	String dateTime 			= formBean.getValue("financialMaintDate");
	String operator  			= formBean.getValue("financialOperator");

	String processDate			= formBean.getValue("processDate");

	String transactionType	 	= formBean.getValue("transactionType");
	String optionId 		 	= formBean.getValue("optionId");
    String status               = formBean.getValue("statusInd");
    String reversalReasonCode   = formBean.getValue("reversalReasonCode");
    String optionIdValue        = "";
    for (int i = 0; i < options.length; i++) {

        if ((options[i].getCode()).equals(optionId)) {

            optionIdValue = options[i].getCodeDesc();
            i = options.length;
        }
    }

	String sequence		     	= formBean.getValue("sequenceNumber");

	String origProcessDate = formBean.getValue("origProcessDate");

	String premiumType          = formBean.getValue("premiumType");
	String taxYear              = formBean.getValue("taxYear");
	String grossAmount          = formBean.getValue("grossAmount");
    String charges              = formBean.getValue("charges");

	String netAmount            = formBean.getValue("netAmount");
    String costBasis            = formBean.getValue("costBasis");
    String complexChangeType    = formBean.getValue("complexChangeType");
    String complexChangeNewValue = formBean.getValue("complexChangeNewValue");

    String correspondenceExistsStatus = formBean.getValue("correspondenceExistsStatus");
    String overdueChargeExistsStatus = (String) formBean.getValue("overdueChargeExistsStatus");
    String originatingTrxPK = (String) formBean.getValue("originatingTrxFK");

    String originatingTrxExistStatus = "unchecked";
    if (!originatingTrxPK.equals("0") && !originatingTrxPK.equals(""))
    {
        originatingTrxExistStatus = "checked";
    }

    String loanSettlementExistsStatus = "unchecked";
    Hashtable loanSettlement = (Hashtable)session.getAttribute("loanSettlement");
    if (loanSettlement != null)
    {
        String loanSettlementPK = (String)loanSettlement.get("loanSettlementPK");
        if (!loanSettlementPK.equals("0"))
        {
            loanSettlementExistsStatus = "checked";
        }
    }
    
	String lockAlertStatus = Util.initString((String)request.getAttribute("shouldShowLockAlert"), "false");

    UserSession userSession = (UserSession) session.getAttribute("userSession");
%>

<html>

<script src="/PORTAL/common/javascript/commonJavascriptFunctions.js"></script>
<script src="/PORTAL/common/javascript/widgetFunctions.js"></script>
<script src="/PORTAL/common/javascript/scrollTable.js"></script>


<script language="Javascript1.2">

	var f = null;

    var shouldShowLockAlert = true;
    var height = screen.height;
    var width  = screen.width;
    var transactionType = "<%= transactionType %>";
    var status = "<%= status %>";
    var complexChangeType = "<%= complexChangeType %>";
    var lockAlertStatus = "<%= lockAlertStatus %>";
    var operator = "<%= operator %>";

	function init()
    {
		f = document.contractFinancialHistoryForm;

		top.frames["main"].setActiveTab("historyTab");

        if (lockAlertStatus == "true")
        {
            shouldShowLockAlert = <%= ! userSession.getSegmentIsLocked() %>;
        }

        for (var i = 0; i < f.elements.length; i++)
        {
            var elementType = f.elements[i].type;

            if (shouldShowLockAlert == true)
            {
                if (f.elements[i].NOLOCK  == null)
                {
                    f.elements[i].onclick = showLockAlert;
                    f.elements[i].onkeydown = showLockAlert;
                }
                f.elements[i].onchange = showLockAlert;
            }
        }

        formatCurrency();

        // Initialize scroll tables
        initScrollTable(document.getElementById("ContractHistorySummaryTableModelScrollTable"));
	}

    function showLockAlert()
    {
        if (shouldShowLockAlert == true)
        {
            alert("The Contract can not be edited.");

            return false;
        }
    }

    function showContractHistoryFilter()
    {
        var width = .55 * screen.width;
        var height = .35 * screen.height;

        openDialog("contractHistoryFilterDialog","left=0,top=0,resizable=no", width, height);

        prepareToSendTransactionAction("ContractDetailTran", "showContractHistoryFilterDialog", "contractHistoryFilterDialog");
    }


	function onTableRowSingleClick(tableId)
    {

		prepareToSendTransactionAction("ContractDetailTran", "showHistoryDetailSummary", "contentIFrame");
	}

	function runReversal()
    {
		if (f.reversal.value == "")
        {
			alert("Reversal Needs To Be Entered");
		}
        else if (transactionType == "Issue" && status == "G")
        {
            alert("Cannot Reverse Issue Transaction");
        }
        else if (status == "U" || status == "R")
        {
            alert("Cannot Reverse an Undone/Reversed Transaction");
        }
        /*else if (transactionType == "BillingChange" && complexChangeType != "CONV")
        {
            alert("Reversal of BC Not Allowed");
        }*/
        else if (operator == "Conversion")
        {
        	alert("Reversal of Conversion Transaction Not Allowed");	
        }
		else
        {
			prepareToSendTransactionAction("ContractDetailTran", "runReversal", "_top")
		}
	}

	function cancelReversal()
    {
		f.reversal.value = "";
	}

	function restoreRealTimeBackup()
    {
		prepareToSendTransactionAction("ContractDetailTran", "restoreContractFromBackup", "contentIFrame");
	}

	function adjustTaxes()
    {
        var width = .75 * screen.width;
        var height = .50 * screen.height;

		openDialog("taxAdjustmentDialog", "left=0,top=0,resizable=no", width, height);

        prepareToSendTransactionAction("ContractDetailTran", "showTaxAdjustmentDialog", "taxAdjustmentDialog");
	}

	function showCharges()
    {
        var width = .75 * screen.width;
        var height = .50 * screen.height;

		openDialog("chargesDialog", "left=0,top=0,resizable=no", width, height);

        prepareToSendTransactionAction("ContractDetailTran", "showChargesDialog", "chargesDialog");
	}

    function showCorrespondenceDialog()
    {
        var width = .75 * screen.width;
        var height = .60 * screen.height;

		openDialog("correspondenceDialog", "left=0,top=0,resizable=no", width, height);

        prepareToSendTransactionAction("ContractDetailTran", "showHistoryCorrespondenceDialog", "correspondenceDialog");
    }

    function showOverdueChargeRemainingDialog()
    {
        var width = .45 * screen.width;
        var height= .30 * screen.height;

        openDialog("overdueChargeRemainingDialog", "top=0,left=0,resizable=no", width, height);

        prepareToSendTransactionAction("ContractDetailTran","showOverdueChargeRemainingDialog", "overdueChargeRemainingDialog");
    }

    function showOriginatingTrxDialog()
    {
        var width = .60 * screen.width;
        var height= .50 * screen.height;

        openDialog("originatingTrxDialog", "top=0,left=0,resizable=no", width, height);

        prepareToSendTransactionAction("ContractDetailTran","showOriginatingTrxDialog", "originatingTrxDialog");
    }

     function showLoanSettlementDialog()
     {
         var width = .60 * screen.width;
         var height= .50 * screen.height;

         openDialog("loanSettlementDialog", "top=0,left=0,resizable=no", width, height);

         prepareToSendTransactionAction("ContractDetailTran","showLoanSettlementDialog", "loanSettlementDialog");
     }

    function showBucketAndInvestmentHistories()
    {
		var tdElement = window.event.srcElement;
		var trElement = tdElement.parentElement;

        f.investmentFK.value = trElement.investmentFK;
        f.fundName.value = trElement.fundName;

        var width   = 0.90 * screen.width;
        var height  = 0.85 * screen.height;

        if (!valueIsEmpty(f.investmentFK.value))
        {
            openDialog("bucketAndInvestmentHistoriesDialog", "left=0,top=0,resizable=no", width, height);

            prepareToSendTransactionAction("ContractDetailTran", "showBucketAndInvestmentHistories", "bucketAndInvestmentHistoriesDialog");
        }
        else
        {
            alert("No Bucket/Investment History Information To Display");
        }
    }

    function prepareToSendTransactionAction(transaction, action, target)
    {
        sendTransactionAction(transaction, action, target);
    }

</script>

<head>
<title>History Detail</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta http-equiv="Cache-Control" content="no-store">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Expires" content="0">

<link href="/PORTAL/common/css/EDITSolutions.css" rel="stylesheet" type="text/css">
</head>

<body class="mainTheme" onLoad="init()" style="border-style:solid; border-width:1;">
<form name= "contractFinancialHistoryForm" method="post" action="/PORTAL/servlet/RequestManager">

<input type="hidden" name="page" value="">
<jsp:include page="contractInfoHeader.jsp" flush="true"/>

  <table width="80%" height="40%" border="0" cellspacing="0" cellpadding="5">
    <tr>
      <td align="left" nowrap>Effective Date:&nbsp;
        <input disabled type="text" name="effectiveDate" size="10" maxlength="10" value="<%= effectiveDate%>">
      </td>
      <td align="left" nowrap>Process Date:&nbsp;
        <input disabled type="text" name="processDate" size="10" maxlength="10" value="<%= processDate%>">
      </td>
      <td align="left" nowrap>Transaction Type:&nbsp;
        <input disabled type="text" name="transactionType" size="20" maxlength="20" value="<%= transactionType%>">
      </td>
    </tr>
    <tr>
      <td align="left" nowrap>Segment:&nbsp;
        <input disabled type="text" name="segment" value="<%= optionIdValue%>">
      </td>
      <td align="left" nowrap>Sequence:&nbsp;
        <input disabled type="text" name="sequenceNumber" value="<%= sequence%>">
      </td>
      <td align="left" nowrap>Original Process Date:&nbsp;
        <input disabled type="text" name="origProcessDate" size="10" maxlength="10" value="<%= origProcessDate %>">
      </td>
    </tr>
    <tr>
      <td align="left" nowrap>Premium Type:&nbsp;
        <input disabled type="text" name="premiumType" value="<%= premiumType%>">
      </td>
      <td align="left" nowrap colspan="2">Cost Basis:&nbsp;
        <input disabled type="text" name="costBasis" size="11" maxlength="11" value="<%= costBasis %>" CURRENCY>
        &nbsp;&nbsp;&nbsp;
        Tax Year:&nbsp;
        <input disabled type="text" name="taxYear" size="4" maxlength="4" value="<%= taxYear%>">
        &nbsp;&nbsp;&nbsp;
        <input type="checkbox" name="correspondenceInd" <%= correspondenceExistsStatus %> disabled>
        <a href="javascript:showCorrespondenceDialog()">Correspondence</a>
        <input type="checkbox" name="overdueChargeRemainingInd" <%= overdueChargeExistsStatus %> disabled>
        <a href="javascript:showOverdueChargeRemainingDialog()">Overdue Charge Remaining&nbsp;</a>
      </td>
    </tr>
    <tr>
      <td align="left" nowrap >
        <input type="checkbox" name="originatingTrxExistInd" <%= originatingTrxExistStatus %> disabled>
        <a href="javascript:showOriginatingTrxDialog()">Originating Transaction Info&nbsp;</a>
      </td>
      <td align="left" nowrap>
        <input type="checkbox" name="loanSettlementExistInd" <%= loanSettlementExistsStatus %> disabled>
        <a href="javascript:showLoanSettlementDialog()">Loan Settlement Info&nbsp;</a>
      </td>
    </tr>
    <tr>
      <%
          if (complexChangeType.equals(""))
          {
      %>
      <td colspan="2">&nbsp;</td>
      <%
          }
          else
          {
      %>
      <td align="left">Complex Change Type:&nbsp;
        <input disabled type="text" name="complexChangeType" size="20" maxlength="20" value="<%= complexChangeType%>">
      </td>
      <%
              if (complexChangeType.equalsIgnoreCase("FaceIncrease"))
              {
      %>
      <td colspan="2" align="left">Complex Change New Value:&nbsp;
        <input disabled type="text" name="complexChangeNewValue" size="20" maxlength="20" value="<%= complexChangeNewValue %>" CURRENCY>
      </td>
      <%
              }
              else
              {
      %>
      <td colspan="2" align="left">Complex Change New Value:&nbsp;
        <input disabled type="text" name="complexChangeNewValue" size="20" maxlength="20" value="<%= complexChangeNewValue %>">
      </td>
      <%
              }
      %>
      <%
          }
      %>
    </tr>
    <tr>
      <td align="left" nowrap>Gross Amount:&nbsp;
        <input disabled type="text" name="grossAmount" value="<%= grossAmount%>" CURRENCY>
      </td>
      <td align="left" nowrap><a href ="javascript:showCharges()">Total Adjustments</a>
        <input disabled type="text" name="charges" size="10" value="<%= charges%>" CURRENCY>
      </td>
      <td align="left" nowrap>Net Amount:&nbsp;
        <input disabled type="text" name="netAmount" value="<%= netAmount%>" CURRENCY>
      </td>
    </tr>
    <tr>
      <td colspan="3" nowrap>Investment Amounts:</td>
    </tr>
  </table>
  <table class="summary" id="summaryTable2" width="100%" border="0" cellspacing="0" cellpadding="2">
	<tr class="heading">
	  <th align="left" width="16%">Investment/Fund</th>
	  <th align="left" width="16%">Alloc %</th>
	  <th align="left" width="16%">Bonus Amt</th>
	  <th align="left" width="16%">Dollars</th>
	  <th align="left" width="16%">Units</th>
	  <th align="left" width="16%">Cum Unit/Dollars</th>
	</tr>
  </table>
  <span class="scrollableContent" style="border-style:solid; border-width:1;  position:relative; width:100%; height:28%; top:0; left:0; background-color:#BBBBBB">
    <table id="contractHistoryFundSummary" width="100%" border="0" cellspacing="0" cellpadding="0">
	  <%
		Map fundActivityBeans = contractFundActivities.getPageBeans();

        String allocationPercent = "";
        String cumulativeDollars = "";
        EDITBigDecimal totalCumDollars = new EDITBigDecimal();
        String dollars = "";
        EDITBigDecimal totalDollars = new EDITBigDecimal();
        String bonusAmount = "";
        EDITBigDecimal totalBonusAmount = new EDITBigDecimal();
        String fundDescription = "";
        String cumulativeUnits = "";
        EDITBigDecimal totalCumUnits = new EDITBigDecimal();
        String units = "";
        EDITBigDecimal totalUnits = new EDITBigDecimal();
        String summaryValue = "";
        String prevInvestment = "";
        String investmentFK = "";
        String prevInvestmentFK = "";
        boolean prevInvestmentOutput = false;

        Iterator it2 = fundActivityBeans.values().iterator();

        while (it2.hasNext())
        {
            prevInvestmentOutput = false;
            PageBean fundActivityBean = (PageBean) it2.next();

            String editTrxHistoryFK = fundActivityBean.getValue("editTrxHistoryFK");
            investmentFK = Util.initString(fundActivityBean.getValue("investmentFK"), "");
            if (editTrxHistoryFK.equals(editTrxHistoryPK))
            {
                fundDescription = Util.initString(fundActivityBean.getValue("fundDescription"), "");
                if (prevInvestment.equals(""))
                {
                    prevInvestment = fundDescription;
                    prevInvestmentFK = investmentFK;
                }

                if (!fundDescription.equals(prevInvestment))
                {
                    prevInvestmentOutput = true;
                    cumulativeDollars = Util.roundToNearestCent(totalCumDollars).toString();
                    if (totalDollars.isLT(EDITBigDecimal.EDIT_DECIMAL_ZILCH_STR))
                    {
                        totalDollars = totalDollars.multiplyEditBigDecimal("-1");
                    }

                    dollars = Util.roundToNearestCent(totalDollars).toString();
                    if (totalBonusAmount.isLT(EDITBigDecimal.EDIT_DECIMAL_ZILCH_STR))
                    {
                        totalBonusAmount = totalBonusAmount.multiplyEditBigDecimal("-1");
                    }

                    bonusAmount = Util.roundToNearestCent(totalBonusAmount).toString();
                    cumulativeUnits = totalCumUnits.getBigDecimal() + "";
                    if (totalUnits.isLT(EDITBigDecimal.EDIT_DECIMAL_ZILCH_STR))
                    {
                        totalUnits = totalUnits.multiplyEditBigDecimal("-1");
                    }

                    units = totalUnits.toString();
                    if (totalCumUnits.isEQ(new EDITBigDecimal()))
                    {
                        summaryValue = cumulativeDollars;
                    }
                    else
                    {
                        summaryValue = cumulativeUnits;
                    }
	  %>
      <tr investmentFK="<%= prevInvestmentFK %>" fundName="<%= prevInvestment %>" isSelected="false" class="default"
          onMouseOver="highlightRow()" onMouseOut="unhighlightRow()" onClick="showBucketAndInvestmentHistories()">
        <td nowrap width="16%">
          <%= prevInvestment %>
        </td>
        <td nowrap width="16%">
          <%= allocationPercent %>
        </td>
        <td nowrap width="16%">
          <script>document.write(formatAsCurrency(<%= bonusAmount %>))</script>
        </td>
        <td nowrap width="16%">
          <script>document.write(formatAsCurrency(<%= dollars %>))</script>
        </td>
        <td nowrap width="16%">
           <%= units %>
        </td>
        <td nowrap width="16%">
        <%
          if (totalCumUnits.isEQ(new EDITBigDecimal()))
          {
        %>
              <script>document.write(formatAsCurrency(<%= summaryValue %>))</script>
        <%
          }
          else
          {
        %>
              <%= summaryValue %>
        <%
          }
        %>
        </td>
      </tr>
      <%
                    totalCumDollars = new EDITBigDecimal();
                    totalDollars = new EDITBigDecimal();
                    totalBonusAmount = new EDITBigDecimal();
                    totalCumUnits = new EDITBigDecimal();
                    totalUnits = new EDITBigDecimal();
                    prevInvestment = fundDescription;
                    prevInvestmentFK = investmentFK;
                }

                allocationPercent = fundActivityBean.getValue("allocationPercent");
                totalCumDollars = totalCumDollars.addEditBigDecimal(fundActivityBean.getValue("cumulativeDollars"));
                totalDollars = totalDollars.addEditBigDecimal(fundActivityBean.getValue("dollars"));
                totalBonusAmount = totalBonusAmount.addEditBigDecimal(fundActivityBean.getValue("bonusAmount"));
                totalCumUnits = totalCumUnits.addEditBigDecimal(fundActivityBean.getValue("cumulativeUnits"));
                totalUnits = totalUnits.addEditBigDecimal(fundActivityBean.getValue("units"));
            }
        }

        if (!prevInvestmentOutput)
        {
            fundDescription = prevInvestment;
            investmentFK = prevInvestmentFK;
        }

        cumulativeDollars = Util.roundToNearestCent(totalCumDollars).toString();
        if (totalDollars.isLT(EDITBigDecimal.EDIT_DECIMAL_ZILCH_STR))
        {
            totalDollars = totalDollars.multiplyEditBigDecimal("-1");
        }

        dollars = Util.roundToNearestCent(totalDollars).toString();
        if (totalBonusAmount.isLT(EDITBigDecimal.EDIT_DECIMAL_ZILCH_STR))
        {
            totalBonusAmount = totalBonusAmount.multiplyEditBigDecimal("-1");
        }

        bonusAmount = Util.roundToNearestCent(totalBonusAmount).toString();
        cumulativeUnits = totalCumUnits.toString();
        if (totalUnits.isLT(EDITBigDecimal.EDIT_DECIMAL_ZILCH_STR))
        {
            totalUnits = totalUnits.multiplyEditBigDecimal("-1");
        }

        units = totalUnits.toString();
        if (totalCumUnits.isEQ(new EDITBigDecimal()))
        {
            summaryValue = cumulativeDollars;
        }
        else
        {
            summaryValue = cumulativeUnits;
        }
      %>
      <tr investmentFK="<%= investmentFK %>" fundName="<%= fundDescription %>" isSelected="false" class="default"
          onMouseOver="highlightRow()" onMouseOut="unhighlightRow()" onClick="showBucketAndInvestmentHistories()">
        <td nowrap width="16%">
          <%= fundDescription %>
        </td>
        <td nowrap width="16%">
          <%= allocationPercent %>
        </td>
        <td nowrap width="16%">
          <script>document.write(formatAsCurrency(<%= bonusAmount %>))</script>
        </td>
        <td nowrap width="16%">
          <script>document.write(formatAsCurrency(<%= dollars %>))</script>
        </td>
        <td nowrap width="16%">
          <%= units %>
        </td>
        <td nowrap width="16%">
        <%
          if (totalCumUnits.isEQ(new EDITBigDecimal()))
          {
        %>
              <script>document.write(formatAsCurrency(<%= summaryValue %>))</script>
        <%
          }
          else
          {
        %>
              <%= summaryValue %>
        <%
          }
        %>
        </td>
      </tr>
    </table>
  </span>
  <table>
  	<tr>
	  <td nowrap align="left">Operator:&nbsp;
	     <input type="text" name="operator" disabled value="<%= operator%>">
	  </td>
	  <td nowrap align="left">Date/Time:&nbsp;
	     <input type="text" name="dateTime" disabled value="<%= dateTime%>">
	  </td>
	  <td nowrap align="left">Reversal Reason Code:&nbsp;
	     <input type="text" name="dateTime" disabled value="<%= reversalReasonCode %>">
	  </td>
	</tr>
  </table>
  <br/><br/>
  <table width="100%" height="1%" border="0" cellspacing="0" cellpadding="0">
  	<tr>
  	  <td nowrap align="left">
  		<%--<input type="button" value="   Save  " style="background-color:#DEDEDE" onClick="runReversal()">
  		<input type="button" value="  Cancel " style="background-color:#DEDEDE" onClick="cancelReversal()">--%>
<%--		<input type="button" value="R/T Restore" style="background-color:#DEDEDE" onClick="restoreRealTimeBackup()">--%>
		<input type="button" value="Adjustment" style="background-color:#DEDEDE" onClick="adjustTaxes()">
	    <input type="button" value="  Filter " style="background-color:#DEDEDE" onClick="showContractHistoryFilter()" NOLOCK>
      </td>
  	</tr>
  </table>

<%-- ****************************** BEGIN Summary Area ****************************** --%>

<jsp:include page="/common/jsp/widget/tableWidget.jsp" flush="true">
    <jsp:param name="tableId" value="ContractHistorySummaryTableModel"/>
    <jsp:param name="tableHeight" value="60"/>
    <jsp:param name="multipleRowSelect" value="false"/>
    <jsp:param name="singleOrDoubleClick" value="single"/>
</jsp:include>

<%-- ****************************** END Summary Area ****************************** --%>


<!-- ****** HIDDEN FIELDS ***** //-->
 <input type="hidden" name="transaction" value="">
 <input type="hidden" name="action"      value="">

 <input type="hidden" name="key" value="">
 <input type="hidden" name="optionId" value="<%= optionId %>">
 <input type="hidden" name="editTrxHistoryPK" value="<%= editTrxHistoryPK %>">
 <input type="hidden" name="editTrxPK" value="<%= editTrxPK %>">
 <input type="hidden" name="origProcessDate" value="<%= origProcessDate %>">
 <input type="hidden" name="investmentFK" value="">
 <input type="hidden" name="fundName" value="">
 <input type="hidden" name="originatingTrxPK" value="<%= originatingTrxPK %>">
 <input type="hidden" name="filterTransaction" value="<%= request.getAttribute("filterTransaction") %>">
 <input type="hidden" name="statusRestriction" value="<%= request.getAttribute("statusRestriction") %>">
 <input type="hidden" name="fromDate" value="<%= request.getAttribute("fromDate") %>">
 <input type="hidden" name="toDate" value="<%= request.getAttribute("toDate") %>">
 <input type="hidden" name="filterPeriod" value="<%= request.getAttribute("filterPeriod") %>">
</form>
</body>
</html>
