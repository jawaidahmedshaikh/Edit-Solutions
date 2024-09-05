<!-- ****** JAVA CODE ***** //-->

<%@ page  errorPage="/common/jsp/error.jsp" autoFlush="true" %>
<%@ page import="java.util.*,
                 edit.common.*,
                 edit.common.vo.*,
                 fission.beans.*,
                 contract.dm.dao.DAOFactory, 
                 fission.utility.*,
                 contract.dm.dao.DAOFactory,
                 engine.ProductStructure,
                 engine.dm.dao.ProductStructureDAO,
                 edit.portal.exceptions.*,
                 edit.common.exceptions.*" %>

<jsp:useBean id="contractMainSessionBean"
    class="fission.beans.SessionBean" scope="session"/>
<%
    PortalEditingException editingException = (PortalEditingException) session.getAttribute("portalEditingException");
    String editingExceptionExists = "false";
    if (editingException != null){

        editingExceptionExists = "true";
    }

    VOEditException voEditException = (VOEditException) session.getAttribute("VOEditException");
    String voEditExceptionExists = "false";
    if (voEditException != null){

        voEditExceptionExists = "true";
    }

    CodeTableWrapper codeTableWrapper = CodeTableWrapper.getSingleton();

    CodeTableVO[] hedgeModeCTVOs = codeTableWrapper.getCodeTableEntries("HEDGEMODE");

    String productStr = contractMainSessionBean.getValue("companyStructureId"); //companyStructureId is product structure here
    // ProductStructureVO productStructureVO = new ProductStructureDAO().findByProductStructureId(Long.parseLong(productStr))[0];

    CodeTableVO[] quoteTypeCTs = codeTableWrapper.getCodeTableEntries("QUOTETYPE",new Long(productStr).longValue());

    String inforceQuoteMessage = (String) request.getAttribute("inforceQuoteMessage");
    inforceQuoteMessage = Util.initString(inforceQuoteMessage, "");

    String errorMessage = (String) request.getAttribute("errorMessage");
    errorMessage = Util.initString(errorMessage, "");

    String analyzeTrx = (String) request.getAttribute("analyzeInforceQuote");

    String selectedFund = Util.initString((String) request.getAttribute("selectedFund"), "");

    FilteredFundVO[] filteredFundVO = (FilteredFundVO[]) session.getAttribute("filteredFundVOs");
    QuoteVO quoteVO = (QuoteVO) session.getAttribute("quoteVO");

    String contractNumber = contractMainSessionBean.getValue("contractId");

    String quoteDate = Util.initString((String) request.getAttribute("quoteDate"), "");
    String ownerName = "";
    String insAnnName = "";

    String quoteTypeCT = (String) request.getAttribute("quoteTypeCT");

    if (quoteVO != null && quoteVO.getSegmentVOCount() > 0)
    {
        quoteDate = DateTimeUtil.formatYYYYMMDDToMMDDYYYY(quoteVO.getQuoteDate());

        SegmentVO segmentVO = quoteVO.getSegmentVO(0);
        ClientDetailVO[] clientDetailVO = quoteVO.getClientDetailVO();
        ContractClientVO[] contractClientVO = segmentVO.getContractClientVO();
        for (int i = 0; i < contractClientVO.length; i++)
        {
            ClientRoleVO clientRoleVO = (ClientRoleVO) contractClientVO[i].getParentVO(ClientRoleVO.class);
            if (clientRoleVO.getRoleTypeCT().equalsIgnoreCase("OWN"))
            {
                for (int j = 0; j < clientDetailVO.length; j++)
                {
                    if (clientDetailVO[j].getClientDetailPK() == clientRoleVO.getClientDetailFK())
                    {
                        if (clientDetailVO[j].getLastName() == null || clientDetailVO[j].getLastName().equals(""))
                        {
                            ownerName = clientDetailVO[j].getCorporateName();
                        }
                        else
                        {
                            ownerName = clientDetailVO[j].getLastName() + ", " +
                                        Util.initString(clientDetailVO[j].getFirstName(), "") + " " +
                                        Util.initString(clientDetailVO[j].getMiddleName(), "");
                        }

                        break;
                    }
                }
            }
            else if (clientRoleVO.getRoleTypeCT().equalsIgnoreCase("Insured") ||
                     clientRoleVO.getRoleTypeCT().equalsIgnoreCase("ANN"))
            {
                for (int j = 0; j < clientDetailVO.length; j++)
                {
                    if (clientDetailVO[j].getClientDetailPK() == clientRoleVO.getClientDetailFK())
                    {
                        if (clientDetailVO[j].getLastName() == null || clientDetailVO[j].getLastName().equals(""))
                        {
                            insAnnName = clientDetailVO[j].getCorporateName();
                        }
                        else
                        {
                            insAnnName = clientDetailVO[j].getLastName() + ", " +
                                        Util.initString(clientDetailVO[j].getFirstName(), "") + " " +
                                        Util.initString(clientDetailVO[j].getMiddleName(), "");
                        }
                    }

                    break;
                }
            }
        }
    }

    String accumValue = "";
    String cashSurrValue = "";
    String guarMinSurrValue = "";
    String freeAmtRemaining = "";
    String deathBenefit = "";
    String unlockedCashValue = "";
    String surrenderCharge = "";

    if (quoteVO != null)
    {
        accumValue = Util.roundDollars(quoteVO.getAccumValue()).toString();
        cashSurrValue = Util.roundDollars(quoteVO.getCashSurrenderValue()).toString();
        guarMinSurrValue = Util.roundDollars(quoteVO.getGuarMinimumSurrenderValue()).toString();
        freeAmtRemaining = Util.roundDollars(quoteVO.getSegmentVO()[0].getFreeAmountRemaining()).toString();
        deathBenefit = Util.roundDollars(quoteVO.getDeathBenefit()).toString();
        unlockedCashValue = Util.roundDollars(quoteVO.getUnlockedCashValue()).toString();
        surrenderCharge = Util.roundDollars(quoteVO.getSurrenderCharge()).toString();
    }
%>

<%!
	private String getRedemptionDate(QuoteVO quoteVO, String lockupEndDate, int redemptionDays, String redemptionMode)
    {
        EDITDate redemptionDate = null;

        if (lockupEndDate != null)
        {
            EDITDate edLockupEndDate = new EDITDate(lockupEndDate);

            redemptionDate = edLockupEndDate.addDays(redemptionDays);

            redemptionDate = redemptionDate.getEndOfModeDate(redemptionMode);
        }

        if (redemptionDate != null)
        {
		return redemptionDate.getFormattedDate();
	}
        else
        {
            return "";
        }
	}
%>

<html>

<!-- ***** JAVASCRIPT *****  -->

<script src="/PORTAL/common/javascript/commonJavascriptFunctions.js"></script>
<script src="/PORTAL/common/javascript/formatDateFunctions.js"></script>
<script src="/PORTAL/common/javascript/calendarSelector.js"></script>

<script language="Javascript1.2">

    var f = null;

    var inforceQuoteMessage = "<%= inforceQuoteMessage %>";
    var errorMessage = "<%= errorMessage %>";
    var analyzeTrx = "<%= analyzeTrx %>";
    var editingExceptionExists = "<%= editingExceptionExists %>";
    var voEditExceptionExists = "<%= voEditExceptionExists %>";

    function init() {

	    f = document.inforceQuoteForm;

        f.quoteDate.focus();

        if (errorMessage != "")
        {
            alert(errorMessage);
            window.close();
        }
        else if (inforceQuoteMessage != "")
        {
            alert(inforceQuoteMessage);
        }

        formatCurrency();

        if (analyzeTrx == "true") {

            showAnalyzer();
        }
    }

    function performInforceQuote()
    {
        if (f.quoteType.selectedIndex == 0)
        {
            alert('Please Select Quote Type');
            return;
        }

        editDate();
        sendTransactionAction("ContractDetailTran","performInforceQuote","_self");
    }

	function analyzeInforceQuote()
    {
        editDate();
		sendTransactionAction("ContractDetailTran", "analyzeInforceQuote", "_self");
	}

    function editDate()
    {
        if (f.quoteDate.value == "")
        {
            alert("Quote Date Not Entered");
        }
    }

    function showSelectedFund()
    {
		var tdElement = window.event.srcElement;
		var trElement = tdElement.parentElement;

        f.selectedFund.value = trElement.investmentPK;

        sendTransactionAction("ContractDetailTran", "showSelectedInfQuoteFund", "_self");
    }

    function clearInforceQuoteDialog()
    {
        sendTransactionAction("ContractDetailTran","clearInforceQuoteDialog","_self");
    }

    function closeInforceQuoteDialog()
    {
        sendTransactionAction("ContractDetailTran","closeInforceQuoteDialog","contentIFrame");
        window.close();
    }

	function showAnalyzer()
    {
        var width = screen.width;
        var height = screen.height;

		openDialog("analyzeInforceQuote", "left=0,top=0,resizable=yes", width, height);

        sendTransactionAction("ContractDetailTran", "showAnalyzer","analyzeInforceQuote");
    }

    function checkForEditingException()
    {
        if (editingExceptionExists == "true")
        {
            var width = .75 * screen.width;
            var height = screen.height/3;

            openDialog("exceptionDialog", "resizable=no", width, height);

            sendTransactionAction("ContractDetailTran", "showEditingExceptionDialog", "exceptionDialog");
        }
    }

    function checkForVOEditException()
    {
        if (voEditExceptionExists == "true")
        {
            var width = .75 * screen.width;
            var height = screen.height/3;

            openDialog("voEditExceptionDialog", "resizable=no", width, height);

            sendTransactionAction("ContractDetailTran", "showVOEditExceptionDialog", "voEditExceptionDialog");
        }
    }

    function continueTransactionAction(transaction, action)
    {
        f.ignoreEditWarnings.value = "true";
        sendTransactionAction(transaction, action, "contentIFrame");
    }

</script>

<head>
<title>Inforce Quote</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta http-equiv="Cache-Control" content="no-store">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Expires" content="0">

<link href="/PORTAL/common/css/EDITSolutions.css" rel="stylesheet" type="text/css">
</head>

<body class="mainTheme" onLoad="init(); checkForEditingException(); checkForVOEditException()" style="border-style:solid; border-width:1">

<form  name="inforceQuoteForm" method="post" action="/PORTAL/servlet/RequestManager">

<span id="span1" style="border-style:solid; border-width:1;  position:relative; width:100%; height:100%; top:0; left:0; z-index:0; overflow:visible">
  <table width="100%" height="5%" border="0" cellspacing="0" cellpadding="4">
    <tr height="100%">
      <td align="right" nowrap>Contract Number:&nbsp;</td>
      <td align="left" nowrap>
        <input type="text" name="contractNumber" maxlength="15" size="15" value="<%= contractNumber %>">
      </td>
      <td align="right" nowrap>Quote Date:&nbsp;</td>
      <td align="left" nowrap>
           <input type="text" name="quoteDate" value="<%= quoteDate %>" size='10' maxlength="10" onKeyUp="DateFormat(this,this.value,event,false)" onBlur="DateFormat(this,this.value,event,true)">
           <a href="javascript:show_calendar('f.quoteDate', f.quoteDate.value);"><img src="/PORTAL/common/images/calendarIcon.gif" width="16" height="16" border="0" alt="Select a date from the calendar"></a>
      </td>
      <td align="right" nowrap>Quote Type:&nbsp;</td>
      <td align="left" nowrap>
        <select name="quoteType">
          <option value="null">Please Select</option>
          <%
              for (int i = 0; i < quoteTypeCTs.length; i++)
              {
                  String code = quoteTypeCTs[i].getCode();
                  String codeDesc = quoteTypeCTs[i].getCodeDesc();

                  if (code.equals(quoteTypeCT))
                  {
                    out.println("<option selected name=\"id\" value=\""  + code + "\">" + codeDesc + "</option>");
                  }
                  else
                  {
                    out.println("<option name=\"id\" value=\"" + code + "\">" + codeDesc + "</option>");
                  }
              }
          %>
                    
        </select>
      </td>
    </tr>
    <tr height="100%">
      <td align="right" nowrap>Owner Name:&nbsp;</td>
      <td align="left" colspan="3" nowrap>
        <input disabled type="text" name="ownerName" maxlength="63" size="63" value="<%= ownerName %>">
      </td>
      
      <% 
      		String contractStatus = contractMainSessionBean.getValue("statusCode");
      
      		
      		
      %>
      <td align="right" nowrap>Contract Status:&nbsp;</td>
      <td align="left" > <%out.print(contractStatus); %> </td>
    </tr>
    <tr height="100%">
      <td align="right" nowrap>Ins/Ann Name:&nbsp;</td>
      <td align="left" nowrap colspan="5">
        <input disabled type="text" name="insAnnName" maxlength="63" size="63" value="<%= insAnnName %>">
      </td>
    </tr>
    <tr>
      <td align="right" nowrap colspan="6">
        <input type="button" name="enter" value="Enter" onClick="performInforceQuote()">
        <input type="button" name="clear" value="Clear" onClick="clearInforceQuoteDialog()">
        <input type="button" name="analyze" value=" Analyze " onClick="analyzeInforceQuote()">
        </td>
    </tr>
  </table>
  <hr align="left" width="100%" noshade>
  <table width="100%" height="33%" border="0" cellspacing="0" cellpadding="4">
    <tr>
    <td width="50%" align="left">
    <table width="100%" border="0" cellspacing="0" cellpadding="4">
	<tr>
      <td align="right" nowrap>Accum Value:&nbsp;</td>
	  <td align="left" nowrap>
	    <input disabled type="text" name="accumValue" size="15" value="<%= accumValue %>" CURRENCY>
	  </td>
    </tr>
    <tr>
      <td align="right" nowrap>Cash Surr Value:&nbsp;</td>
      <td align="left" nowrap>
        <input disabled type="text" name="cashSurrValue" size="15" value="<%= cashSurrValue %>" CURRENCY>
      </td>
	</tr>
    <tr>
      <td align="right" nowrap>Guar Min Surr Value:&nbsp;</td>
      <td align="left" nowrap>
        <input disabled type="text" name="guarMinSurrValue" size="15" value="<%= guarMinSurrValue %>" CURRENCY>
      </td>
    </tr>
    <tr>
      <td align="right" nowrap>Unlocked Cash Value:&nbsp;</td>
      <td align="left" nowrap>
        <input disabled type="text" name="unlockedCashValue" size="15" value="<%= unlockedCashValue %>" CURRENCY>
      </td>
    </tr>
    <tr>
      <td align="right" nowrap>Free Amount Remaining:&nbsp;</td>
      <td align="left" nowrap>
        <input disabled type="text" name="freeAmtRemaining" size="15" value="<%= freeAmtRemaining %>" CURRENCY>
      </td>
    </tr>
    <tr>
      <td align="right" nowrap>Death Benefit:&nbsp;</td>
      <td align="left" nowrap>
        <input disabled type="text" name="deathBenefit" size="15" value="<%= deathBenefit %>" CURRENCY>
      </td>
    </tr>
    <tr>
      <td align="right" nowrap>Surrender Charge:&nbsp;</td>
      <td align="left" nowrap>
        <input disabled type="text" name="surrenderCharge" size="15" value="<%= surrenderCharge %>" CURRENCY>
      </td>
    </tr>
    </table>
    </td>
    <td width="50%" align="right">
    <table width="100%" height="60%" border="0" cellspacing="0" cellpadding="4">
    <tr>
      <td align="left" nowrap rowspan="3">Adjustments:&nbsp;
        <table class="summary" id="summaryTable" width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr class="heading">
            <th width="50%" align="left">Adjustment Type</th>
            <th width="50%" align="left">Amount</th>
          </tr>
        </table>
        <span class="scrollableContent" style="border-style:solid; border-width:1;  position:relative; width:100%; height:100%; top:0; left:0; background-color:#BBBBBB">
          <table class="summary" id="quoteChargesSummary" width="100%" style="border-style:solid; border-width:0;" cellspacing="0" cellpadding="0">
              <%
                String cClassName = "default";
                String cSelected = "false";

                if (quoteVO != null)
                {
                    QuoteAdjustmentVO[] quoteAdjustmentVOs = quoteVO.getQuoteAdjustmentVO();
                    
                    for (int i = 0; i < quoteAdjustmentVOs.length; i++)
                    {
                        QuoteAdjustmentVO quoteAdjustmentVO = quoteAdjustmentVOs[i];
                        
                        String cChargeType = quoteAdjustmentVO.getChargeTypeCT();
                        String cChargeAmount = Util.roundDollars(quoteAdjustmentVO.getChargeAmount()).toString();
              %>
              <tr class="<%= cClassName %>" isSelected="<%= cSelected %>"
                  onMouseOver="highlightRow()" onMouseOut="unhighlightRow()">
                <td width="50%" nowrap>
                  <%= cChargeType %>
                </td>
                <td width="50%" nowrap>
                  <script>document.write(formatAsCurrency(<%= cChargeAmount %>))</script>
                </td>
              </tr>
              <%
                    }
                }
              %>
          </table>
        </span>
      </td>
      </tr>
      </table>
    </td>
    </tr>
  </table>
  Investment Information
  <table class="summary" width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr class="heading">
      <th align="left" width="25%">Fund</th>
      <th align="left" width="25%">Fund Type</th>
      <th align="left" width="25%">Cum Dollars</th>
      <th align="left" width="25%">Cum Units</th>
    </tr>
  </table>
  <span class="scrollableContent" style="border-style:solid; border-width:1;  position:relative; width:100%; height:15%; top:0; left:0; background-color:#BBBBBB">
    <table class="summary" id="inforceQuoteSummary" width="100%" style="border-style:solid; border-width:0;" cellspacing="0" cellpadding="0">
        <%
            String hClassName = "default";
            boolean hSelected = false;

            String sInvestmentFund = "";
            String sFundType = "";
            String sCumDollars = "";
            String sCumUnits = "";
            EDITBigDecimal sumCumDollars = new EDITBigDecimal();
            EDITBigDecimal sumCumUnits = new EDITBigDecimal();

            if (quoteVO != null)
            {
                QuoteInvestmentVO[] quoteInvestmentVO = quoteVO.getQuoteInvestmentVO();
                if (quoteInvestmentVO != null)
                {
                    for (int i = 0; i < quoteInvestmentVO.length; i++)
                    {
                        sumCumDollars = new EDITBigDecimal();
                        sumCumUnits = new EDITBigDecimal();

                        InvestmentVO investmentVO = quoteInvestmentVO[i].getInvestmentVO();
                        if (!selectedFund.equals("") &&
                            investmentVO.getInvestmentPK() == Long.parseLong(selectedFund))
                        {
                            hClassName = "highlighted";
                            hSelected = true;
                        }
                        else
                        {
                            hClassName = "default";
                            hSelected = false;
                        }

                        long filteredFundFK = investmentVO.getFilteredFundFK();
                        for (int j = 0; j < filteredFundVO.length; j++)
                        {
                            if (filteredFundVO[j].getFilteredFundPK() == filteredFundFK)
                            {
                                FundVO fundVO = (FundVO) filteredFundVO[j].getParentVO(FundVO.class);
                                sInvestmentFund = Util.initString(fundVO.getName(), "");
                                sFundType = Util.initString(fundVO.getFundType(), "");
                                BucketVO[] bucketVOs = investmentVO.getBucketVO();
                                if (bucketVOs != null)
                                {
                                    for (int k = 0; k < bucketVOs.length; k++)
                                    {
                                        sumCumUnits = sumCumUnits.addEditBigDecimal(bucketVOs[k].getCumUnits());
                                        sumCumDollars = sumCumDollars.addEditBigDecimal(bucketVOs[k].getCumDollars());
                                    }

                                    sCumDollars = Util.roundDollars(sumCumDollars).toString();
                                    sCumUnits = sumCumUnits.toString();
        %>
        <tr class="<%= hClassName %>" isSelected="<%= hSelected %>" investmentPK="<%= investmentVO.getInvestmentPK() %>"
            onClick="showSelectedFund()" onMouseOver="highlightRow()" onMouseOut="unhighlightRow()">
          <td nowrap width="25%">
            <%= sInvestmentFund %>
          </td>
          <td nowrap width="25%">
            <%= sFundType %>
          </td>
          <td nowrap width="25%">
            <script>document.write(formatAsCurrency(<%= sCumDollars %>))</script>
          </td>
          <td nowrap width="25%">
            <%= sCumUnits %>
          </td>
        </tr>
        <%
                                } //end of bucket not = null

                                break;
                            }
                        }  //end of for filtered funds
                    }
                }
            }
        %>
    </table>
  </span>
  <br>
  Bucket Information
  <%
      if (selectedFund.equals(""))
      {
  %>
  <table class="summary" width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr class="heading">
      <th align="left" width="25%">&nbsp</th>
      <th align="left" width="25%">&nbsp</th>
      <th align="left" width="25%">&nbsp</th>
      <th align="left" width="25%">&nbsp</th>
    </tr>
  </table>
  <span class="scrollableContent" style="border-style:solid; border-width:1;  position:relative; width:100%; height:15%; top:0; left:0; background-color:#BBBBBB">
    <table class="summary" id="inforceQuoteBucketSummary" width="100%" style="border-style:solid; border-width:0;" cellspacing="0" cellpadding="0">
      <tr>
        <td nowrap width="25%">&nbsp</td>
        <td nowrap width="25%">&nbsp</td>
        <td nowrap width="25%">&nbsp</td>
        <td nowrap width="25%">&nbsp</td>
      </tr>
    </table>
  </span>
  <%
      }
      else
      {
        String bFundType = "";
        String bBeginningSandP = "";
        String bCurrIndexCap = "";
        String bParticipationRate = "";
        String bMargin = "";
        String bDepositDate = "";
        String bCumDollars = "";
        String bCumUnits = "";
        String bCurrIntRate = "";
        String bBonusIntRate = "";
        String bBonusRateDur = "";
        String bTotalIntEarned = "";
        String bUnitValue = "";
        String bUnitValueDate = "";
        String bLockupEndDate = "";
        String bLockupAmount = "";
        String bIndexCredits = "";
        String bRedemptionDate = "";
        String redemptionMode = "";
        String redemptionModeDesc = "";

        List voInclusionList = new ArrayList();
        voInclusionList.add(EDITTrxHistoryVO.class);
        voInclusionList.add(EDITTrxVO.class);

        if (quoteVO != null)
        {
            QuoteInvestmentVO[] quoteInvestmentVO = quoteVO.getQuoteInvestmentVO();
            if (quoteInvestmentVO != null)
            {
                event.business.Event eventComponent = new event.component.EventComponent();
                for (int i = 0; i < quoteInvestmentVO.length; i++)
                {
                    InvestmentVO investmentVO = quoteInvestmentVO[i].getInvestmentVO();
                    if (investmentVO.getInvestmentPK() == Long.parseLong(selectedFund))
                    {
                        long filteredFundFK = investmentVO.getFilteredFundFK();
                        for (int j = 0; j < filteredFundVO.length; j++)
                        {
                            if (filteredFundVO[j].getFilteredFundPK() == filteredFundFK)
                            {
                                redemptionMode = Util.initString(filteredFundVO[j].getWithdrawalModeCT(), "");
                                redemptionModeDesc = Util.initString(codeTableWrapper.getCodeDescByCodeTableNameAndCode("HEDGEMODE", redemptionMode), "");
                                int redemptionDays = filteredFundVO[j].getWithdrawalDays();

                                FundVO fundVO = (FundVO) filteredFundVO[j].getParentVO(FundVO.class);
                                bFundType = Util.initString(fundVO.getFundType(), "");
                                if (bFundType.equalsIgnoreCase("EquityIndex"))
                                {
  %>
  <table class="summary" width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr class="heading">
      <th align="left" width="14%">Deposit Date</th>
      <th align="left" width="14%">Cum Dollars</th>
      <th align="left" width="14%">Beginning S&P</th>
      <th align="left" width="14%">Curr Index Cap</th>
      <th align="left" width="14%">Participation Rate</th>
      <th align="left" width="14%">Margin</th>
      <th align="left" width="14%">Index Credits</th>
    </tr>
  </table>
  <span class="scrollableContent" style="border-style:solid; border-width:1;  position:relative; width:100%; height:15%; top:0; left:0; background-color:#BBBBBB">
    <table class="summary" id="inforceQuoteBucketSummary" width="100%" style="border-style:solid; border-width:0;" cellspacing="0" cellpadding="0">
  <%
                                    QuoteBucketVO[] quoteBucketVOs = quoteInvestmentVO[i].getQuoteBucketVO();

                                    for (int k = 0; k < quoteBucketVOs.length; k++)
                                    {

                                        bBeginningSandP = quoteBucketVOs[k].getBeginningSandP().toString();
                                        bCurrIndexCap = quoteBucketVOs[k].getCurrentIndexCapRate().toString();
                                        bParticipationRate = quoteBucketVOs[k].getParticipationRate().toString();
                                        bMargin = quoteBucketVOs[k].getMargin().toString();

                                        EDITBigDecimal sumIndexCredits = new EDITBigDecimal();

                                        long bucketFK = quoteBucketVOs[k].getBucketFK();
                                        BucketVO[] bucketVOs = investmentVO.getBucketVO();
                                        for (int l = 0; l < bucketVOs.length; l++)
                                        {
                                            BucketVO bucketVO = bucketVOs[l];

                                            if (bucketVO.getBucketPK() == bucketFK)
                                            {
                                                bDepositDate = bucketVO.getDepositDate();
                                                bCumDollars = Util.roundDollars(bucketVO.getCumDollars()).toString();
                                                break;
                                            }
                                        }

                                        BucketHistoryVO[] bucketHistoryVOs = eventComponent.composeBucketHistoryByBucketFK(bucketFK, voInclusionList);
                                        if (bucketHistoryVOs != null)
                                        {
                                            for (int l = 0; l < bucketHistoryVOs.length; l++)
                                            {
                                                EDITTrxVO editTrxVO = (EDITTrxVO) bucketHistoryVOs[l].getParentVO(EDITTrxHistoryVO.class).getParentVO(EDITTrxVO.class);
                                                if (editTrxVO.getStatus().equalsIgnoreCase("N") ||
                                                    editTrxVO.getStatus().equalsIgnoreCase("A"))
                                                {
                                                    EDITDate effectiveDate = new EDITDate(editTrxVO.getEffectiveDate());
                                                    EDITDate quoteEDITDate = new EDITDate(quoteVO.getQuoteDate());

                                                    if (effectiveDate.before(quoteEDITDate) || effectiveDate.equals(quoteEDITDate))
                                                    {
                                                        sumIndexCredits = sumIndexCredits.addEditBigDecimal(Util.roundToNearestCent(bucketHistoryVOs[l].getInterestEarnedCurrent()));
                                                        sumIndexCredits = sumIndexCredits.addEditBigDecimal(Util.roundToNearestCent(bucketHistoryVOs[l].getBonusInterestEarned()));
                                                    }
                                                }
                                            }

                                            bIndexCredits = (Util.roundToNearestCent(sumIndexCredits)).toString();
                                        }
  %>
      <tr>
        <td nowrap width="14%">
          <%= Util.initString(bDepositDate, "") %>
        </td>
        <td nowrap width="14%">
          <script>document.write(formatAsCurrency(<%= bCumDollars %>))</script>
        </td>
        <td nowrap width="14%">
          <%= bBeginningSandP %>
        </td>
        <td nowrap width="14%">
          <%= bCurrIndexCap %>
        </td>
        <td nowrap width="14%">
          <%= bParticipationRate %>
        </td>
        <td nowrap width="14%">
          <%= bMargin %>
        </td>
        <td nowrap width="14%">
          <script>document.write(formatAsCurrency(<%= bIndexCredits %>))</script>
        </td>
      </tr>
  <%
                                    }
  %>
    </table>
  </span>
  <%
                                }
                                else if (bFundType.equalsIgnoreCase("Fixed") ||
                                         bFundType.equalsIgnoreCase("MVA"))
                                {
  %>
  <table class="summary" width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr class="heading">
      <th align="left" width="16%">Deposit Date</th>
      <th align="left" width="16%">Cum Dollars</th>
      <th align="left" width="16%">Curr Int Rate</th>
      <th align="left" width="16%">Bonus Int Rate</th>
      <th align="left" width="16%">Bonus Rate Dur</th>
      <th align="left" width="16%">Total Int Earned</th>
    </tr>
  </table>
  <span class="scrollableContent" style="border-style:solid; border-width:1;  position:relative; width:100%; height:15%; top:0; left:0; background-color:#BBBBBB">
    <table class="summary" id="inforceQuoteBucketSummary" width="100%" style="border-style:solid; border-width:0;" cellspacing="0" cellpadding="0">
  <%
                                    EDITBigDecimal sumTotalIntEarned = new EDITBigDecimal();
                                    BucketVO[] bucketVOs = investmentVO.getBucketVO();
                                    if (bucketVOs != null)
                                    {
                                        for (int k = 0; k < bucketVOs.length; k++)
                                        {    
                                            bCumDollars = Util.roundDollars(bucketVOs[k].getCumDollars()).toString();
                                            bCurrIntRate = bucketVOs[k].getBucketInterestRate().toString();
                                            bBonusIntRate = bucketVOs[k].getBonusIntRate().toString();
                                            bBonusRateDur = bucketVOs[k].getBonusIntRateDur() + "";

                                            long bucketPK = bucketVOs[k].getBucketPK();
                                            BucketHistoryVO[] bucketHistoryVOs = eventComponent.composeBucketHistoryByBucketFK(bucketPK, voInclusionList);
                                            if (bucketHistoryVOs != null)
                                            {
                                                for (int l = 0; l < bucketHistoryVOs.length; l++)
                                                {
                                                    EDITTrxVO editTrxVO = (EDITTrxVO) bucketHistoryVOs[l].getParentVO(EDITTrxHistoryVO.class).getParentVO(EDITTrxVO.class);
                                                    if (editTrxVO.getStatus().equalsIgnoreCase("N") ||
                                                        editTrxVO.getStatus().equalsIgnoreCase("A"))
                                                    {
                                                        EDITDate effectiveDate = new EDITDate(editTrxVO.getEffectiveDate());
                                                        EDITDate quoteEDITDate = new EDITDate(quoteVO.getQuoteDate());

                                                        if (effectiveDate.before(quoteEDITDate) || effectiveDate.equals(quoteEDITDate))
                                                        {
                                                            sumTotalIntEarned = sumTotalIntEarned.addEditBigDecimal(Util.roundToNearestCent(bucketHistoryVOs[l].getInterestEarnedCurrent()));
                                                            sumTotalIntEarned = sumTotalIntEarned.addEditBigDecimal(Util.roundToNearestCent(bucketHistoryVOs[l].getBonusInterestEarned()));
                                                        }
                                                    }
                                                }

                                                bTotalIntEarned = (Util.roundToNearestCent(sumTotalIntEarned)).toString();
                                            }
  %>
      <tr>
        <td nowrap width="16%">
          <%= bucketVOs[k].getDepositDate() %>
        </td>
        <td nowrap width="16%">
          <script>document.write(formatAsCurrency(<%= bCumDollars %>))</script>
        </td>
        <td nowrap width="16%">
          <%= bCurrIntRate %>
        </td>
        <td nowrap width="16%">
          <%= bBonusIntRate %>
        </td>
        <td nowrap width="16%">
          <%= bBonusRateDur %>
        </td>
        <td nowrap width="16%">
          <script>document.write(formatAsCurrency(<%= bTotalIntEarned %>))</script>
        </td>
      </tr>
  <%
                                        }
                                    }
  %>
    </table>
  </span>
  <%
                                }
                                else if (bFundType.equalsIgnoreCase("Variable") ||
                                         bFundType.equalsIgnoreCase("Hedge"))
                                {
  %>
  <table class="summary" width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr class="heading">
      <th align="left" width="9%">Deposit Date</th>
      <th align="left" width="10%">Cum Dollars</th>
      <th align="left" width="10%">Cum Units</th>
      <th align="left" width="10%">Unit Value</th>
      <th align="left" width="9%">Unit Value Date</th>
      <th align="left" width="11%">Lockup Amount</th>
      <th align="left" width="9%">Lockup End Date</th>
      <th align="left" width="9%">Redemption Date</th>
      <th align="left" width="13%">Redemption Mode</th>
    </tr>
  </table>
  <span class="scrollableContent" style="border-style:solid; border-width:1;  position:relative; width:100%; height:15%; top:0; left:0; background-color:#BBBBBB">
    <table class="summary" id="inforceQuoteBucketSummary" width="100%" style="border-style:solid; border-width:0;" cellspacing="0" cellpadding="0">
  <%
                                    if (quoteInvestmentVO[i].getUnitValue() != null)
                                    {
                                        bUnitValue = quoteInvestmentVO[i].getUnitValue().toString();
                                    }
                                    else
                                    {
                                        bUnitValue = (new EDITBigDecimal()).toString();
                                    }

                                    bUnitValueDate = Util.initString(quoteInvestmentVO[i].getUnitValueDate(), "");

                                    BucketVO[] bucketVOs = investmentVO.getBucketVO();
                                    if (bucketVOs != null)
                                    {
                                        for (int k = 0; k < bucketVOs.length; k++)
                                        {
                                            bCumDollars = Util.roundDollars(bucketVOs[k].getCumDollars()).toString();
                                            bCumUnits = new EDITBigDecimal(bucketVOs[k].getCumUnits()).toString();
                                            bLockupEndDate = Util.initString(bucketVOs[k].getLockupEndDate(), "");
                                            if (!bLockupEndDate.equals(""))
                                            {
                                                EDITDate edLockupEndDate = new EDITDate(bLockupEndDate);
                                                EDITDate edQuoteDate = new EDITDate(quoteVO.getQuoteDate());
                                                if (edLockupEndDate.after(edQuoteDate) || edLockupEndDate.equals(edQuoteDate))
                                                {
                                                    bLockupAmount = bCumDollars;
                                                }
                                                else
                                                {
                                                    bLockupAmount = "0";
                                                }
                                            }
                                            else
                                            {
                                                bLockupAmount = "0";
                                            }

                                            bRedemptionDate = getRedemptionDate(quoteVO, bucketVOs[k].getLockupEndDate(), redemptionDays, redemptionMode);
  %>
      <tr>
        <td align="left" nowrap width="9%">
          <%= bucketVOs[k].getDepositDate() %>
        </td>
        <td align="left" nowrap width="10%">
          <script>document.write(formatAsCurrency(<%= bCumDollars %>))</script>
        </td>
        <td align="left" nowrap width="10%">
          <%= bCumUnits %>
        </td>
        <td align="left" nowrap width="10%">
          <%= bUnitValue %>
        </td>
        <td align="left" nowrap width="10%">
          <%= bUnitValueDate %>
        </td>
        <td align="left" nowrap width="10%">
          <script>document.write(formatAsCurrency(<%= bLockupAmount %>))</script>
        </td>
        <td align="left" nowrap width="9%">
          <%= bLockupEndDate %>
        </td>
        <td align="left" nowrap width="9%">
          <%= bRedemptionDate %>
        </td>
        <td align="left" nowrap width="13%">
          <%= redemptionModeDesc %>
        </td>
      </tr>
  <%
                                        }
                                    }
  %>
    </table>
  </span>
  <%
                                }
                            }
                        }
                    }
                }
            }
         }
      }
  %>
  <table width="100%" height="2%">
    <tr>
      <td align="right">
        <input type="button" name="close" value="Close" onClick="closeInforceQuoteDialog()">
      </td>
    </tr>
  </table>
</span>

<!-- ****** HIDDEN FIELDS ***** //-->
<input type="hidden" name="transaction" value="">
<input type="hidden" name="action"      value="">

<input type="hidden" name="selectedFund" value="">

</form>
</body>
</html>
