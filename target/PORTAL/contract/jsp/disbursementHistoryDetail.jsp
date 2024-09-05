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
    SessionBean contractHistories = (SessionBean) session.getAttribute("contractHistories");
    SessionBean contractFundActivities = (SessionBean) session.getAttribute("contractFundActivities");

    CodeTableWrapper codeTableWrapper = CodeTableWrapper.getSingleton();
    String companyStructureId = contractMainSessionBean.getPageBean("formBean").getValue("companyStructureId");

    CodeTableVO[] options  = codeTableWrapper.getCodeTableEntries("OPTIONCODE", Long.parseLong(companyStructureId));
    CodeTableVO[] reversalReasonCodes = codeTableWrapper.getCodeTableEntries("REVERSALREASONCODE", Long.parseLong(companyStructureId));

//	PageBean formBean      = contractMainSessionBean.getPageBean("historyFormBean");
	PageBean formBean      = contractHistories.getPageBean("dh");

    String editTrxPK = formBean.getValue("editTrxPK");
    String editTrxHistoryPK = formBean.getValue("editTrxHistoryPK");
 	String effectiveDate   = formBean.getValue("effectiveDate");

	String dateTime 			= formBean.getValue("financialMaintDate");
	String operator  			= formBean.getValue("financialOperator");

	String processDate			= formBean.getValue("processDate");

	String transactionType	 	= formBean.getValue("transactionType");
	String optionId     	 	= formBean.getValue("optionId");
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

	String taxYear              = formBean.getValue("taxYear");
	String grossAmount          = formBean.getValue("grossAmount");
    String charges              = formBean.getValue("charges");

	String netAmount            = formBean.getValue("netAmount");

	String releaseDate         = formBean.getValue("releaseDate");

	String returnDate          = formBean.getValue("returnDate");

    String interestProceeds     = formBean.getValue("interestProceeds");

	String distributionCode     = Util.initString(formBean.getValue("distributionCode"), "");
    if (!distributionCode.equals(""))
    {
        distributionCode = codeTableWrapper.getCodeDescByCodeTableNameAndCode("DISTRIBUTIONCODE", distributionCode);
    }

    String calculatedDistCode   = Util.initString(formBean.getValue("calculatedDistCode"), "");
    if (!calculatedDistCode.equals(""))
    {
        String calculatedDistCodeDesc = codeTableWrapper.getCodeDescByCodeTableNameAndCode("DISTRIBUTIONCODE", calculatedDistCode);
        if (calculatedDistCodeDesc != null)
        {
            calculatedDistCode = calculatedDistCodeDesc;   
        }
    }
	String controlNumber        = formBean.getValue("controlNumber");
	String payeeClientId        = formBean.getValue("financialClientId");
	String freeAmount           = formBean.getValue("freeAmount");
	String federalWithholding   = formBean.getValue("federalWithholding");
	String cityWithholding      = formBean.getValue("cityWithholding");
	String stateWithholding     = formBean.getValue("stateWithholding");
	String countyWithholding    = formBean.getValue("countyWithholding");
	String checkAmount			= formBean.getValue("checkAmount");
	String taxableBenefit       = formBean.getValue("taxableBenefit");
    String costBasis            = formBean.getValue("costBasis");
    String deathStatus          = formBean.getValue("deathStatus");
    String correspondenceExistsStatus = formBean.getValue("correspondenceExistsStatus");
    String loanInterestDollars = formBean.getValue("loanInterestDollars");
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
    var lockAlertStatus = "<%= lockAlertStatus %>"

	function init()
    {
		f = document.contractDisbursementHistoryForm;

		top.frames["main"].setActiveTab("historyTab");

        if (lockAlertStatus == "true")
        {
            shouldShowLockAlert = <%= ! userSession.getSegmentIsLocked() %>;
        }

        for (var i = 0; i < f.elements.length; i++)
        {
            var elementType = f.elements[i].type;

            if ( (elementType == "text" || elementType == "button") && (shouldShowLockAlert == true) )
            {
                if (f.elements[i].NOLOCK  == null)
                {
                    f.elements[i].onclick = showLockAlert;
                    f.elements[i].onkeydown = showLockAlert;
                }
            }
        }

        formatCurrency();

        // Initialize scroll tables
        initScrollTable(document.getElementById("ContractHistorySummaryTableModelScrollTable"));
	}

    function showLockAlert()
    {
        if (shouldShowLockAlert == true){

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

    function selectHistoryRow(key)
    {
        f.key.value = key;

        prepareToSendTransactionAction("ContractDetailTran", "showHistoryDetailSummary", "contentIFrame");
    }

     function onTableRowSingleClick(tableId)
    {
<%--        f.key.value = key;--%>

		prepareToSendTransactionAction("ContractDetailTran", "showHistoryDetailSummary", "contentIFrame");
	}
    
	function cancelReversal()
    {
		f.reversal.value = "";
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
		else
        {
			prepareToSendTransactionAction("ContractDetailTran", "runReversal", "_top")
		}
	}

	function restoreRealTimeBackup()
    {
		prepareToSendTransactionAction("ContractDetailTran", "restoreContractFromBackup", "contentIFrame");
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

    function showOriginatingTrxDialog()
    {
        var width = .60 * screen.width;
        var height= .50 * screen.height;

        openDialog("originatingTrxDialog", "top=0,left=0,resizable=no", width, height);

        prepareToSendTransactionAction("ContractDetailTran","showOriginatingTrxDialog", "originatingTrxDialog");
    }

	function adjustTaxes()
    {
        var width = .75 * screen.width;
        var height = .50 * screen.height;

		openDialog("taxAdjustmentDialog", "left=0,top=0,resizable=no", width, height);

        prepareToSendTransactionAction("ContractDetailTran", "showDisburseTaxAdjustDialog", "taxAdjustmentDialog");
	}

     function showLoanSettlementDialog()
     {
         var width = .60 * screen.width;
         var height= .50 * screen.height;

         openDialog("loanSettlementDialog", "top=0,left=0,resizable=no", width, height);

         prepareToSendTransactionAction("ContractDetailTran","showLoanSettlementDialog", "loanSettlementDialog");
     }

    function prepareToSendTransactionAction(transaction, action, target)
    {
        sendTransactionAction(transaction, action, target);
    }
</script>
<head>
<title>Disbursement History Detail</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta http-equiv="Cache-Control" content="no-store">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Expires" content="0">

<link href="/PORTAL/common/css/EDITSolutions.css" rel="stylesheet" type="text/css">
</head>

<body class="mainTheme" onLoad="init()" style="border-style:solid; border-width:1;">
<form name= "contractDisbursementHistoryForm" method="post" action="/PORTAL/servlet/RequestManager">

<input type="hidden" name="page" value="">
<jsp:include page="contractInfoHeader.jsp" flush="true"/>

  <table width="100%" height="40%" border="0" cellspacing="8" cellpadding="0">
    <tr>
      <td align="left" nowrap>Effective Date:&nbsp;
        <input disabled type="text" name="effectiveDate" size="10" maxlength="10" value="<%= effectiveDate%>">
      </td>
      <td align="left" nowrap>Process Date:&nbsp;
        <input disabled type="text" name="processDate" size="10" maxlength="10" value="<%= processDate%>">
      </td>
      <td align="left" nowrap>Transaction Type:&nbsp;
        <input disabled type="text" name="transactionType" size="10" value="<%= transactionType%>">
      </td>
      <td align="left" nowrap>Segment:&nbsp;
        <input disabled type="text" name="segment" size="10" value="<%= optionIdValue%>">
      </td>
    </tr>
    <tr>
      <td align="left" nowrap>Sequence:&nbsp;
        <input type="text" name="sequenceNumber" size="10" value="<%= sequence%>">
      </td>
      <td align="left" nowrap >Original Process Date:&nbsp;
        <input disabled type="text" name="origProcessDate" size="10" maxlength="10" value="<%= origProcessDate%>">
      </td>
      <td align="left" nowrap>Release Date:&nbsp;
        <input type="text" name="releaseDate" size="10" maxlength="10" value="<%= releaseDate%>">
      </td>
      <td align="left" nowrap>Return Date:&nbsp;
        <input type="text" name="returnDate" size="10" maxlength="10" value="<%= returnDate%>">
      </td>
    </tr>
    <tr>
      <td align="left" nowrap>Dist Code Override:&nbsp;
        <input type="text" name="distributionCode" size="20" value="<%= distributionCode%>">
      </td>
      <td align="left" nowrap>Control Number:&nbsp;
        <input type="text" name="controlNumber" size="10" value="<%= controlNumber%>">
      </td>
      <td align="left" nowrap>Payee Client Id:&nbsp;
        <input type="text" name="payeeClientId" size="11" maxlength="11" value="<%= payeeClientId%>">
      </td>
      <td align="left" nowrap>Death Status:&nbsp;
        <input type="text" name="deathStatus" size="21" maxlength="21" value="<%= deathStatus%>">
      </td>
    </tr>
    <tr>
      <td align="left" nowrap colspan="4">Calc Dist Code:&nbsp;
        <input type="text" name="calculatedDistCode" size="50" value="<%= calculatedDistCode %>">
      </td>
    </tr>
    <tr>
      <td alignt="left" nowrap>Gross Amount:&nbsp;
        <input type="text" name="grossAmount" size="10" value="<%= grossAmount%>" CURRENCY>
      </td>
      <td align="left" nowrap>Free Amount:&nbsp;
        <input type="text" name="freeAmount" size="10" value="<%= freeAmount%>" CURRENCY>
      </td>
      <td align="left" nowrap><a href ="javascript:showCharges()">Total Adjustments</a>
        <input type="text" name="charges" size="10" value="<%= charges%>" CURRENCY>
      </td>
      <td align="left" nowrap>&nbsp;</td>
    </tr>
    <tr>
      <td align="left" nowrap>Net Amount:&nbsp;
        <input type="text" name="netAmount" size="10" value="<%= netAmount%>" CURRENCY>
      </td>
      <td align="left" nowrap>Federal Withholding:&nbsp;
        <input type="text" name="federalWithholding" size="10" value="<%= federalWithholding%>" CURRENCY>
      </td>
      <td align="left" nowrap>State Withholding:&nbsp;
        <input type="text" name="stateWithholding" size="10" value="<%= stateWithholding%>" CURRENCY>
      </td>
      <td align="left" nowrap>&nbsp;</td>
    </tr>
    <tr>
      <td align="left" nowrap>City Withholding:&nbsp;
        <input type="text" name="cityWithholding" size="10" value="<%= cityWithholding%>" CURRENCY>
      </td>
      <td align="left" nowrap>County Withholding:&nbsp;
        <input type="text" name="countyWithholding" size="10" value="<%= countyWithholding%>" CURRENCY>
      </td>
      <td align="left" nowrap>Check Amount:&nbsp;
        <input type="text" name="checkAmount" size="15" value="<%= checkAmount%>" CURRENCY>
      </td>
      <td align="left" nowrap>Advanced Interest:&nbsp;
        <input type="text" name="checkAmount" size="15" value="<%= loanInterestDollars %>" CURRENCY>
      </td>
    </tr>
    <tr>
      <td align="left" nowrap>Tax Year:&nbsp;
        <input type="text" name="taxYear" size="4" maxlength="4" value="<%= taxYear%>">
      </td>
      <td align="left" nowrap>Taxable Benefit:
        <input type="text" name="taxableBenefit" size="10" value="<%= taxableBenefit%>" CURRENCY>
      </td>
      <td align="left" nowrap>
        <input type="checkbox" name="correspondenceInd" <%= correspondenceExistsStatus %> disabled>
        <a href="javascript:showCorrespondenceDialog()">Correspondence</a>
      </td>
      <td align="left" nowrap>&nbsp;</td>
    </tr>
    <tr>
      <td align="left" nowrap>Cost Basis:&nbsp;
        <input type="text" name="costBasis" size="11" maxlength="11" value="<%= costBasis %>" CURRENCY>
      </td>
      <td align="left" nowrap>Interest Proceeds:&nbsp;
        <input type="text" name="interestProceeds" size="11" maxlength="11" value="<%= interestProceeds %>" CURRENCY>
      </td>
      <td align="left" nowrap>
        <input type="checkbox" name="originatingTrxExistInd" <%= originatingTrxExistStatus %> disabled>
        <a href="javascript:showOriginatingTrxDialog()">Originating Transaction Info&nbsp;</a>
      </td>
      <td align="left" nowrap>
        <input type="checkbox" name="loanSettlementExistInd" <%= loanSettlementExistsStatus %> disabled>
        <a href="javascript:showLoanSettlementDialog()">Loan Settlement Info&nbsp;</a>
      </td>
    </tr>
  </table>
  <table class="summary" id="summaryTable2" width="100%" border="0" cellspacing="0" cellpadding="2">
    <tr class="heading">
      <th align="left" width="16%">Investment/Fund</th>
      <th align="left" width="16%">Alloc %</th>
  	  <th align="left" width="16%">Bonus Amount</th>
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
            investmentFK = fundActivityBean.getValue("investmentFK");
            if (editTrxHistoryFK.equals(editTrxHistoryPK))
            {
                fundDescription = fundActivityBean.getValue("fundDescription");
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
      <tr investmentFK="<%= prevInvestmentFK %>" fundName="<%= prevInvestment %>" isSelected="false" class="default"
          onMouseOver="highlightRow()" onMouseOut="unhighlightRow()" onClick="showBucketAndInvestmentHistories()">
        <td nowrap width="16%" align="left">
          <%= prevInvestment %>
        </td>
        <td nowrap width="16%" align="left">
          <%= allocationPercent %>
        </td>
        <td nowrap width="16%" align="left">
          <script>document.write(formatAsCurrency(<%= bonusAmount %>))</script>
        </td>
        <td nowrap width="16%" align="left">
          <script>document.write(formatAsCurrency(<%= dollars %>))</script>
        </td>
        <td nowrap width="16%" align="left">
          <%= units %>
        </td>
        <td nowrap width="16%" align="left">
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
        <td nowrap width="16%" align="left">
          <%= fundDescription %>
        </td>
        <td nowrap width="16%" align="left">
          <%= allocationPercent %>
        </td>
        <td nowrap width="16%" align="left">
          <script>document.write(formatAsCurrency(<%= bonusAmount %>))</script>
        </td>
        <td nowrap width="16%" align="left">
          <script>document.write(formatAsCurrency(<%= dollars %>))</script>
        </td>
        <td nowrap width="16%" align="left">
          <%= units %>
        </td>
        <td nowrap width="16%" align="left">
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
	   <td nowrap align="left">Operator:
	      <input type="text" name="operator" value="<%= operator%>">
	   </td>
	   <td nowrap align="left">Date/Time:
	      <input type="text" name="dateTime" value="<%= dateTime%>">
	   </td>
	   <td nowrap align="left">Reversal Reason Code:&nbsp;
	     <input type="text" name="dateTime" disabled value="<%= reversalReasonCode %>">
	  </td>
	 </tr>
  </table>
  <%--<table width="100%" height="1%" border="0" cellspacing="8" cellpadding="0">
    <tr>
	  <td nowrap align="left">Reversal:
 	    <input disabled size="1" maxlength="1" type="text" name="reversal">
      </td>
      <td nowrap align="left">Reversal Reason Code:&nbsp;
        <select name="reversalReasonCode">
          <%
         	  out.println("<option>Please Select</option>");

              for(int i = 0; i < reversalReasonCodes.length; i++)
              {
                  String codeTablePK = reversalReasonCodes[i].getCodeTablePK() + "";
                  String codeDesc    = reversalReasonCodes[i].getCodeDesc();
                  String code        = reversalReasonCodes[i].getCode();

                  if (reversalReasonCode.equalsIgnoreCase(code))
                  {
                      out.println("<option selected name=\"id\" value=\"" + codeTablePK+ "\">" + codeDesc + "</option>");
                  }
                  else
                  {
                      out.println("<option name=\"id\" value=\"" + codeTablePK + "\">" + codeDesc + "</option>");
                  }
              }
          %>
        </select>
      </td>
      <td nowrap align="left">Operator:
        <input type="text" name="operator" value="<%= operator%>">
      </td>
      <td nowrap align="left">Date/Time:
        <input type="text" name="dateTime" value="<%= dateTime%>">
      </td>
    </tr>
  </table>--%>
  <br/><br/>
  <table width="100%" height="1%" border="0" cellspacing="0" cellpadding="0">
  	<tr>
  	  <td nowrap align="left">
        <%--<input type="button" value="   Save  " style="background-color:#DEDEDE" onClick="runReversal()">
  		<input type="button" value="  Cancel " style="background-color:#DEDEDE" onClick="cancelReversal()">--%>
		<input type="button" value="  Filter " style="background-color:#DEDEDE" onClick="showContractHistoryFilter()" NOLOCK>
		<input type="button" value="Adjustment" style="background-color:#DEDEDE" onClick="adjustTaxes()">
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

 <input type="hidden" name="optionId" value="<%= optionId %>">
 <input type="hidden" name="editTrxHistoryPK" value="<%= editTrxHistoryPK %>">
 <input type="hidden" name="editTrxPK" value="<%= editTrxPK %>">
 <input type="hidden" name="origProcessDate" value="<%= origProcessDate %>">
 <input type="hidden" name="investmentFK" value="">
 <input type="hidden" name="fundName" value="">
 <input type="hidden" name="originatingTrxPK" value="<%= originatingTrxPK %>">
 <input type="hidden" name="key" value="">
 <input type="hidden" name="filterTransaction" value="<%= request.getAttribute("filterTransaction") %>">
 <input type="hidden" name="statusRestriction" value="<%= request.getAttribute("statusRestriction") %>">
 <input type="hidden" name="fromDate" value="<%= request.getAttribute("fromDate") %>">
 <input type="hidden" name="toDate" value="<%= request.getAttribute("toDate") %>">
 <input type="hidden" name="filterPeriod" value="<%= request.getAttribute("filterPeriod") %>">


</form>
</body>
</html>
