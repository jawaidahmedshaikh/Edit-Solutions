<!--
 * User: sprasad
 * Date: Oct 30, 2006
 * Time: 2:11:29 PM
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement. 
 -->
 <!-- ****** JAVA CODE ***** //-->

 <%@ page  errorPage="/common/jsp/error.jsp" autoFlush="true" %>
 <%@ page import="java.util.*,
                  edit.common.*,
                  edit.common.vo.*,
                  fission.beans.*,
                  engine.Fund,
                  fission.utility.*,
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

     String productStr = contractMainSessionBean.getValue("companyStructureId"); //companyStructureId is product structure here

     CodeTableVO[] quoteTypeCTs = codeTableWrapper.getCodeTableEntries("QUOTETYPE",new Long(productStr).longValue());

     String inforceQuoteMessage = (String) request.getAttribute("loanQuoteMessage");
     inforceQuoteMessage = Util.initString(inforceQuoteMessage, "");

     String errorMessage = (String) request.getAttribute("errorMessage");
     errorMessage = Util.initString(errorMessage, "");

     String analyzeTrx = (String) request.getAttribute("analyzeInforceQuote");

     String selectedFund = Util.initString((String) request.getAttribute("selectedFund"), "");

     FilteredFundVO[] filteredFundVOs = (FilteredFundVO[]) session.getAttribute("filteredFundVOs");
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

//                     break;
                 }
             }
         }
     }

     EDITBigDecimal accumValue = new EDITBigDecimal();
     EDITBigDecimal maximumLoanAvailable = new EDITBigDecimal();
     EDITBigDecimal cashSurrenderValue = new EDITBigDecimal();
     EDITBigDecimal lienBalance = new EDITBigDecimal();
     EDITBigDecimal loanPrincipal = new EDITBigDecimal();
     EDITBigDecimal accruedLoanInterest = new EDITBigDecimal();
     EDITBigDecimal totalLoanBalance = new EDITBigDecimal();
     EDITBigDecimal loanInterestRate = new EDITBigDecimal();
     EDITBigDecimal creditingInterestRate = new EDITBigDecimal();
     EDITBigDecimal loanPayoff = new EDITBigDecimal();
     EDITBigDecimal surrenderCharge = new EDITBigDecimal();
     
     if (quoteVO != null && quoteVO.getSegmentVOCount() > 0)
     {
         accumValue = Util.roundDollars(quoteVO.getAccumValue());
         maximumLoanAvailable = new EDITBigDecimal(quoteVO.getMaxNonPreferredAvailable()).addEditBigDecimal(new EDITBigDecimal(quoteVO.getMaxPreferredAvailable()));
         cashSurrenderValue = new EDITBigDecimal(quoteVO.getCashSurrenderValue());
         lienBalance = new EDITBigDecimal(quoteVO.getLienBalance());
         loanPayoff = new EDITBigDecimal(quoteVO.getLoanPayoff());
         surrenderCharge = Util.roundDollars(quoteVO.getSurrenderCharge());

         SegmentVO segmentVO = quoteVO.getSegmentVO(0);
         InvestmentVO[] investmentVOs = segmentVO.getInvestmentVO();

         for (int i = 0; i < investmentVOs.length; i++)
         {
             InvestmentVO investmentVO = investmentVOs[i];
             BucketVO[] bucketVOs = investmentVO.getBucketVO();

             FilteredFundVO filteredFundVO = getFilteredFundVO(filteredFundVOs, investmentVO.getFilteredFundFK());
             FundVO fundVO = (FundVO) filteredFundVO.getParentVO(FundVO.class);

             for (int j = 0; j < bucketVOs.length; j++)
             {
                 BucketVO bucketVO = bucketVOs[j];
                 loanPrincipal = loanPrincipal.addEditBigDecimal(bucketVO.getLoanPrincipalRemaining());
                 accruedLoanInterest = accruedLoanInterest.addEditBigDecimal(bucketVO.getAccruedLoanInterest());
                 totalLoanBalance = loanPrincipal.addEditBigDecimal(accruedLoanInterest);

                 // The LoanInterestRate and BucketInterestRate exists only in Loan funds.
                 if (Fund.PREFERRED_LOAN_QUALIFIER.equals(fundVO.getLoanQualifierCT()) || Fund.NONPREFRRED_LOAN_QUALIFIER.equals(fundVO.getLoanQualifierCT()))
                 {
                     loanInterestRate = new EDITBigDecimal(bucketVO.getLoanInterestRate());
                     creditingInterestRate = new EDITBigDecimal(bucketVO.getBucketInterestRate());
                 }
             }
         }
     }
 %>

 <%!

    /**
     * Helper method to retrieve FilteredFundVO from set of FilteredFundVOs.
     * @param filteredFundVOs
     * @param filteredFundPK
     * @return filteredFundVO
     */
    private FilteredFundVO getFilteredFundVO(FilteredFundVO[] filteredFundVOs, long filteredFundPK)
    {
        FilteredFundVO filteredFundVO = null;

        for (int i = 0; i < filteredFundVOs.length; i++)
        {
              if (filteredFundPK == filteredFundVOs[i].getFilteredFundPK())
              {
                  filteredFundVO = filteredFundVOs[i];
                  break;
              }
        }

        return filteredFundVO;
    }

    /**
     * Helper method to retrieve QuoteBucketVO from set of QuoteBucketVOs
     * @param quoteBucketVOs
     * @param bucketPK
     * @return quoteBucketVO
     */
    private QuoteBucketVO getQuoteBucketVO(QuoteBucketVO[] quoteBucketVOs, long bucketPK)
    {
        QuoteBucketVO quoteBucketVO = null;

        for (int i = 0; i < quoteBucketVOs.length; i++)
        {
            if (bucketPK == quoteBucketVOs[i].getBucketFK())
            {
                quoteBucketVO = quoteBucketVOs[i];
            }
        }

        return quoteBucketVO;
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

         if (analyzeTrx == "true")
         {
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

         try
         {
             sendTransactionAction("ContractDetailTran","performInforceQuote","_self");
         }
         catch (e)
         {
             alert("Quote Date Invalid");
         }
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
 <title>Loan Quote</title>
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
       <td align="left" nowrap colspan="5">
         <input disabled type="text" name="ownerName" maxlength="63" size="63" value="<%= ownerName %>">
       </td>
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
   <table width="100%" border="0" cellspacing="0" cellpadding="4">
    <tr>
      <td align="right" nowrap>Accum Value:&nbsp;</td>
      <td align="left" nowrap>
        <input disabled type="text" name="accumValue" size="15" value="<%= accumValue %>" CURRENCY>
      </td>
      <td align="right" nowrap>Lien Balance:&nbsp;</td>
      <td align="left" nowrap>
        <input disabled type="text" name="lienBalance" size="15" value="<%= lienBalance %>" CURRENCY>
      </td>
    </tr>
    <tr>
      <td align="right" nowrap>Maximum Loan Available:&nbsp;</td>
      <td align="left" nowrap>
        <input disabled type="text" name="maximumLoanAvailable" size="15" value="<%= maximumLoanAvailable %>" CURRENCY>
      </td>
      <td align="right" nowrap>Cash Surrender Value:&nbsp;</td>
      <td align="left" nowrap>
        <input disabled type="text" name="cashSurrenderValue" size="15" value="<%= cashSurrenderValue %>" CURRENCY>
      </td>
    </tr>
	<tr>
      <td align="right" nowrap>Loan Principal:&nbsp;</td>
	  <td align="left" colspan="3" nowrap>
	    <input disabled type="text" name="loanPrincipal" size="15" value="<%= loanPrincipal %>" CURRENCY>
	  </td>
    </tr>
    <tr>
      <td align="right" nowrap>Accrued Loan Interest:&nbsp;</td>
      <td align="left" colspan="3" nowrap>
        <input disabled type="text" name="acrruedLoanInterest" size="15" value="<%= accruedLoanInterest %>" CURRENCY>
      </td>
	</tr>
    <tr>
      <td align="right" nowrap>Total Loan Balance:&nbsp;</td>
      <td align="left" nowrap>
        <input disabled type="text" name="totalLoanBalance" size="15" value="<%= totalLoanBalance %>" CURRENCY>
      </td>
      <td align="right" nowrap>Loan Payoff:&nbsp;</td>
      <td align="left" nowrap>
        <input disabled type="text" name="loanPayoff" size="15" value="<%= loanPayoff %>" CURRENCY>
      </td>
    </tr>
    <tr>
      <td align="right" nowrap>Loan Interest Rate:&nbsp;</td>
      <td align="left" nowrap>
        <input disabled type="text" name="loanInterestRate" size="15" value="<%= loanInterestRate %>" CURRENCY>
      </td>
      <td align="right" nowrap>Crediting Interest Rate:&nbsp;</td>
      <td align="left" nowrap>
        <input disabled type="text" name="creditingInterestRate" size="15" value="<%= creditingInterestRate %>" CURRENCY>
      </td>
    </tr>
    <tr>
      <td align="right" nowrap>Surrender Charge:&nbsp;</td>
      <td align="left" colspan="3" nowrap>
        <input disabled type="text" name="surrenderCharge" size="15" value="<%= surrenderCharge %>" CURRENCY>
      </td>
	</tr>
    </table>
   <br>
   Investment Information
   <table class="summary" width="100%" border="0" cellspacing="0" cellpadding="0">
     <tr class="heading">
       <th align="left" width="20%">Fund</th>
       <th align="left" width="20%">Fund Type</th>
       <th align="left" width="20%">Cum Dollars</th>
       <th align="left" width="20%">Cum Units</th>
       <th align="left" width="20%">Available Loan Amount</th>
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
             EDITBigDecimal availableLoanAmount = new EDITBigDecimal();

             if (quoteVO != null)
             {
                 QuoteInvestmentVO[] quoteInvestmentVO = quoteVO.getQuoteInvestmentVO();
                 if (quoteInvestmentVO != null)
                 {
                     for (int i = 0; i < quoteInvestmentVO.length; i++)
                     {
                         sumCumDollars = new EDITBigDecimal();
                         sumCumUnits = new EDITBigDecimal();
                         availableLoanAmount = new EDITBigDecimal();

                         QuoteBucketVO[] quoteBucketVOs = quoteInvestmentVO[i].getQuoteBucketVO();

                         for (int j = 0; j < quoteBucketVOs.length; j++)
                         {
                             QuoteBucketVO quoteBucketVO = quoteBucketVOs[j];
                             availableLoanAmount = availableLoanAmount.addEditBigDecimal(quoteBucketVO.getAvailableLoan());
                         }

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
                         for (int j = 0; j < filteredFundVOs.length; j++)
                         {
                             if (filteredFundVOs[j].getFilteredFundPK() == filteredFundFK)
                             {
                                 FundVO fundVO = (FundVO) filteredFundVOs[j].getParentVO(FundVO.class);
                                 sInvestmentFund = Util.initString(fundVO.getName(), "");
                                 sFundType = Util.initString(fundVO.getFundType(), "");
                                 BucketVO[] bucketVOs = investmentVO.getBucketVO();
                                 if (bucketVOs != null)
                                 {
                                     for (int k = 0; k < bucketVOs.length; k++)
                                     {
                                         sumCumUnits = sumCumUnits.addEditBigDecimal(bucketVOs[k].getCumUnits());
                                         sumCumDollars = sumCumDollars.addEditBigDecimal(bucketVOs[k].getLoanCumDollars());
                                     }

                                     sCumDollars = Util.roundDollars(sumCumDollars).toString();
                                     sCumUnits = sumCumUnits.toString();
         %>
         <tr class="<%= hClassName %>" isSelected="<%= hSelected %>" investmentPK="<%= investmentVO.getInvestmentPK() %>"
             onClick="showSelectedFund()" onMouseOver="highlightRow()" onMouseOut="unhighlightRow()">
           <td nowrap width="20%">
             <%= sInvestmentFund %>
           </td>
           <td nowrap width="20%">
             <%= sFundType %>
           </td>
           <td nowrap width="20%">
             <script>document.write(formatAsCurrency(<%= sCumDollars %>))</script>
           </td>
           <td nowrap width="20%">
             <%= sCumUnits %>
           </td>
           <td nowrap width="20%">
             <script>document.write(formatAsCurrency(<%= availableLoanAmount %>))</script>
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
         String qbAvailableLoan = "0";
         String qbUnlockedLoanAmt = "0";
         String qbUnearnedLoanInterest = "0";

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
                         for (int j = 0; j < filteredFundVOs.length; j++)
                         {
                             if (filteredFundVOs[j].getFilteredFundPK() == filteredFundFK)
                             {
                                 FundVO fundVO = (FundVO) filteredFundVOs[j].getParentVO(FundVO.class);
                                 bFundType = Util.initString(fundVO.getFundType(), "");
                                 if (bFundType.equalsIgnoreCase("EquityIndex"))
                                 {
   %>
   <table class="summary" width="100%" border="0" cellspacing="0" cellpadding="0">
     <tr class="heading">
       <th align="left" width="9%">Deposit Date</th>
       <th align="left" width="10%">Cum Dollars</th>
       <th align="left" width="10%">Beginning S&P</th>
       <th align="left" width="10%">Curr Index Cap</th>
       <th align="left" width="11%">Participation Rate</th>
       <th align="left" width="10%">Margin</th>
       <th align="left" width="10%">Index Credits</th>
       <th align="left" width="10%">Available Loan</th>
       <th align="left" width="10%">Unlocked Loan Amt</th>
       <th align="left" width="10%">Unearned Loan Interest</th>
     </tr>
   </table>
   <span class="scrollableContent" style="border-style:solid; border-width:1;  position:relative; width:100%; height:15%; top:0; left:0; background-color:#BBBBBB">
     <table class="summary" id="inforceQuoteBucketSummary" width="100%" style="border-style:solid; border-width:0;" cellspacing="0" cellpadding="0">
   <%
                                     bBeginningSandP = quoteInvestmentVO[i].getBeginningSandP().toString();
                                     bCurrIndexCap = quoteInvestmentVO[i].getCurrentIndexCapRate().toString();
                                     bParticipationRate = quoteInvestmentVO[i].getParticipationRate().toString();
                                     bMargin = quoteInvestmentVO[i].getMargin().toString();
                                     BucketVO[] bucketVOs = investmentVO.getBucketVO();
                                     QuoteBucketVO[] quoteBucketVOs= quoteInvestmentVO[i].getQuoteBucketVO();
                                     EDITBigDecimal sumIndexCredits = new EDITBigDecimal();
                                     if (bucketVOs != null)
                                     {
                                         for (int k = 0; k < bucketVOs.length; k++)
                                         {
                                             bCumDollars = Util.roundDollars(bucketVOs[k].getCumDollars()).toString();

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
                                                         sumIndexCredits = sumIndexCredits.addEditBigDecimal(Util.roundToNearestCent(bucketHistoryVOs[l].getInterestEarnedCurrent()));
                                                         sumIndexCredits = sumIndexCredits.addEditBigDecimal(Util.roundToNearestCent(bucketHistoryVOs[l].getBonusInterestEarned()));

                                                     }
                                                 }

                                                 bIndexCredits = (Util.roundToNearestCent(sumIndexCredits)).toString();
                                             }
                                             
                                             QuoteBucketVO quoteBucketVO = getQuoteBucketVO(quoteBucketVOs, bucketPK);

                                             if (quoteBucketVO != null)
                                             {
                                                 qbAvailableLoan = Util.roundDollars(quoteBucketVO.getAvailableLoan()).toString();
                                                 qbUnlockedLoanAmt = Util.roundDollars(quoteBucketVO.getUnlockedLoanAmount()).toString();
                                             }
   %>
       <tr>
         <td nowrap width="9%">
           <%= bucketVOs[k].getDepositDate() %>
         </td>
         <td nowrap width="10%">
           <script>document.write(formatAsCurrency(<%= bCumDollars %>))</script>
         </td>
         <td nowrap width="10%">
           <%= bBeginningSandP %>
         </td>
         <td nowrap width="10%">
           <%= bCurrIndexCap %>
         </td>
         <td nowrap width="11%">
           <%= bParticipationRate %>
         </td>
         <td nowrap width="10%">
           <%= bMargin %>
         </td>
         <td nowrap width="10%">
           <script>document.write(formatAsCurrency(<%= bIndexCredits %>))</script>
         </td>
         <td nowrap width="10%">
           <script>document.write(formatAsCurrency(<%= qbAvailableLoan %>))</script>
         </td>
         <td nowrap width="11%">
           <script>document.write(formatAsCurrency(<%= qbUnlockedLoanAmt %>))</script>
         </td>
         <td nowrap width="10%">
           <script>document.write(formatAsCurrency(<%= qbUnearnedLoanInterest %>))</script>
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
                                 else if (bFundType.equalsIgnoreCase("Fixed") ||
                                          bFundType.equalsIgnoreCase("MVA"))
                                 {
   %>
   <table class="summary" width="100%" border="0" cellspacing="0" cellpadding="0">
     <tr class="heading">
       <th align="left" width="9%">Deposit Date</th>
       <th align="left" width="11%">Cum Dollars</th>
       <th align="left" width="12%">Curr Int Rate</th>
       <th align="left" width="12%">Bonus Int Rate</th>
       <th align="left" width="11%">Bonus Rate Dur</th>
       <th align="left" width="12%">Total Int Earned</th>
       <th align="left" width="12%">Available Loan</th>
       <th align="left" width="11%">Unlocked Loan Amt</th>
       <th align="left" width="9%">Unearned Loan Interest</th>
     </tr>
   </table>
   <span class="scrollableContent" style="border-style:solid; border-width:1;  position:relative; width:100%; height:15%; top:0; left:0; background-color:#BBBBBB">
     <table class="summary" id="inforceQuoteBucketSummary" width="100%" style="border-style:solid; border-width:0;" cellspacing="0" cellpadding="0">
   <%
                                     EDITBigDecimal sumTotalIntEarned = new EDITBigDecimal();
                                     BucketVO[] bucketVOs = investmentVO.getBucketVO();
                                     QuoteBucketVO[] quoteBucketVOs = quoteInvestmentVO[i].getQuoteBucketVO();
                                     if (bucketVOs != null)
                                     {
                                         for (int k = 0; k < bucketVOs.length; k++)
                                         {
                                             bCumDollars = Util.roundDollars(bucketVOs[k].getCumDollars()).toString();
                                             bCurrIntRate = Util.formatDecimal("#########0.0", bucketVOs[k].getBucketInterestRate());
                                             bBonusIntRate = Util.formatDecimal("#########0.0", bucketVOs[k].getBonusIntRate());
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
                                                         sumTotalIntEarned = sumTotalIntEarned.addEditBigDecimal(Util.roundToNearestCent(bucketHistoryVOs[l].getInterestEarnedCurrent()));
                                                         sumTotalIntEarned = sumTotalIntEarned.addEditBigDecimal(Util.roundToNearestCent(bucketHistoryVOs[l].getBonusInterestEarned()));

                                                     }
                                                 }

                                                 bTotalIntEarned = (Util.roundToNearestCent(sumTotalIntEarned)).toString();
                                             }

                                             QuoteBucketVO quoteBucketVO = getQuoteBucketVO(quoteBucketVOs, bucketPK);

                                             if (quoteBucketVO != null)
                                             {
                                                 qbAvailableLoan = Util.roundDollars(quoteBucketVO.getAvailableLoan()).toString();
                                                 qbUnlockedLoanAmt = Util.roundDollars(quoteBucketVO.getUnlockedLoanAmount()).toString();
                                                 qbUnearnedLoanInterest = Util.formatDecimal("#########0.0", quoteBucketVO.getUnearnedLoanInterest());
                                             }
   %>
       <tr>
         <td nowrap width="9%">
           <%= bucketVOs[k].getDepositDate() %>
         </td>
         <td nowrap width="11%">
           <script>document.write(formatAsCurrency(<%= bCumDollars %>))</script>
         </td>
         <td nowrap width="12%">
           <%= bCurrIntRate %>
         </td>
         <td nowrap width="12%">
           <%= bBonusIntRate %>
         </td>
         <td nowrap width="11%">
           <%= bBonusRateDur %>
         </td>
         <td nowrap width="12%">
           <script>document.write(formatAsCurrency(<%= bTotalIntEarned %>))</script>
         </td>
         <td nowrap width="12%">
           <script>document.write(formatAsCurrency(<%= qbAvailableLoan %>))</script>
         </td>
         <td nowrap width="11%">
           <script>document.write(formatAsCurrency(<%= qbUnlockedLoanAmt %>))</script>
         </td>
         <td nowrap width="9%">
           <script>document.write(formatAsCurrency(<%= qbUnearnedLoanInterest %>))</script>
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
       <th align="left" width="12%">Cum Dollars</th>
       <th align="left" width="13%">Cum Units</th>
       <th align="left" width="13%">Unit Value</th>
       <th align="left" width="10%">Unit Value Date</th>
       <th align="left" width="11%">Lockup Amount</th>
       <th align="left" width="12%">Available Loan</th>
       <th align="left" width="12%">Unlocked Loan Amt</th>
       <th align="left" width="8%">Unearned Loan Interest</th>
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
                                     QuoteBucketVO[] quoteBucketVOs = quoteInvestmentVO[i].getQuoteBucketVO();
                                     if (bucketVOs != null)
                                     {
                                         for (int k = 0; k < bucketVOs.length; k++)
                                         {
                                             long bucketPK = bucketVOs[k].getBucketPK();
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

                                             QuoteBucketVO quoteBucketVO = getQuoteBucketVO(quoteBucketVOs, bucketPK);

                                             if (quoteBucketVO != null)
                                             {
                                                 qbAvailableLoan = Util.roundDollars(quoteBucketVO.getAvailableLoan()).toString();
                                                 qbUnlockedLoanAmt = Util.roundDollars(quoteBucketVO.getUnlockedLoanAmount()).toString();
                                             }
   %>
       <tr>
         <td align="left" nowrap width="9%">
           <%= bucketVOs[k].getDepositDate() %>
         </td>
         <td align="left" nowrap width="12%">
           <script>document.write(formatAsCurrency(<%= bCumDollars %>))</script>
         </td>
         <td align="left" nowrap width="13%">
           <%= bCumUnits %>
         </td>
         <td align="left" nowrap width="13%">
           <%= bUnitValue %>
         </td>
         <td align="left" nowrap width="10%">
           <%= bUnitValueDate %>
         </td>
         <td align="left" nowrap width="11%">
           <script>document.write(formatAsCurrency(<%= bLockupAmount %>))</script>
         </td>
         <td align="left" nowrap width="12%">
           <script>document.write(formatAsCurrency(<%= qbAvailableLoan %>))</script>
         </td>
         <td align="left" nowrap width="12%">
           <script>document.write(formatAsCurrency(<%= qbUnlockedLoanAmt %>))</script>
         </td>
         <td nowrap width="8%">
           <script>document.write(formatAsCurrency(<%= qbUnearnedLoanInterest %>))</script>
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
