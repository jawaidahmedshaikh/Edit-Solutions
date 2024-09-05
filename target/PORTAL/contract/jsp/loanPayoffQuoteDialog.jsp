<!-- ****** JAVA CODE ***** //-->

<%@ page  errorPage="/common/jsp/error.jsp" autoFlush="true" %>
<%@ page import="java.util.*,
                 edit.common.*,
                 edit.common.vo.*,
                 fission.beans.*,
                 fission.utility.Util" %>

<%
    String loanQuoteMessage = (String) request.getAttribute("loanQuoteMessage");
    if (loanQuoteMessage == null)
    {
        loanQuoteMessage = "";
    }
    FilteredFundVO[] filteredFundVO = (FilteredFundVO[]) session.getAttribute("filteredFundVOs");
    LoanPayoffQuoteVO loanPayoffQuoteVO = (LoanPayoffQuoteVO) session.getAttribute("loanPayoffQuoteVO");
    String quoteDate = (String) request.getAttribute("quoteDate");
    String preferredPayoffAmt = "";
    String nonPreferredPayoffAmt = "";
    String grossPayoffAmt = "";
    String unearnedInterest = "";

    if (loanPayoffQuoteVO != null)
    {
        SegmentVO segmentVO = loanPayoffQuoteVO.getSegmentVO();
        InvestmentVO[] investmentVOs = segmentVO.getInvestmentVO();
        for (int i = 0; i < investmentVOs.length; i++)
        {
            long filteredFundFK = investmentVOs[i].getFilteredFundFK();
            for (int f = 0; f < filteredFundVO.length; f++)
            {
                if (filteredFundVO[f].getFilteredFundPK() == filteredFundFK)
                {
                    FundVO fundVO = (FundVO) filteredFundVO[f].getParentVO(FundVO.class);
                    if (fundVO.getFundType().equalsIgnoreCase("Loan"))
                    {
                        EDITBigDecimal preferredPayoff = new EDITBigDecimal();
                        EDITBigDecimal nonPreferredPayoff = new EDITBigDecimal();
                        EDITBigDecimal  grossPayoff = new EDITBigDecimal();
                        EDITBigDecimal unearnedInt = new EDITBigDecimal();
                        BucketVO[] bucketVO = investmentVOs[i].getBucketVO();
                        if (bucketVO != null)
                        {
                            for (int b = 0; b < bucketVO.length; b++)
                            {
                                if (new EDITBigDecimal(bucketVO[b].getGuarCumValue()).isGT("0") ||
                                    new EDITBigDecimal(bucketVO[b].getCumDollars()).isGT("0"))
                                {
                                    preferredPayoff = preferredPayoff.addEditBigDecimal(bucketVO[b].getGuarCumValue());
                                    nonPreferredPayoff = nonPreferredPayoff.addEditBigDecimal(bucketVO[b].getCumDollars());
                                    grossPayoff = grossPayoff.addEditBigDecimal(
                                                new EDITBigDecimal(bucketVO[b].getGuarCumValue()).addEditBigDecimal(
                                                new EDITBigDecimal(bucketVO[b].getCumDollars())
                                                ));
                                    unearnedInt = unearnedInt.addEditBigDecimal(bucketVO[b].getUnearnedInterest());

                                }
                            }
                        }
                    }
                }
            }
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

    var loanQuoteMessage = "<%= loanQuoteMessage %>";

    function init()
    {
	    f = document.loanPayoffQuoteForm;

        f.quoteDate.focus();

        if (loanPayoffQuoteMessage != "")
        {
            alert(loanPayoffQuoteMessage);
        }

        formatCurrency();
    }

    function performLoanPayoffQuote()
    {
        sendTransactionAction("ContractDetailTran","performLoanPayoffQuote","_self");
    }

    function clearLoanPayoffQuoteDialog()
    {
        sendTransactionAction("ContractDetailTran","clearLoanPayoffQuoteDialog","_self");
    }

    function closeLoanPayoffQuoteDialog()
    {
        sendTransactionAction("ContractDetailTran","closeLoanPayoffQuoteDialog","contentIFrame");
        window.close();
    }
</script>

<head>
<title>Loan Payoff Quote</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta http-equiv="Cache-Control" content="no-store">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Expires" content="0">

<link href="/PORTAL/common/css/EDITSolutions.css" rel="stylesheet" type="text/css">
</head>

<body class="dialog" onLoad="init()" style="border-style:solid; border-width:1">

<form  name="loanPayoffQuoteForm" method="post" action="/PORTAL/servlet/RequestManager">

<span id="span1" style="border-style:solid; border-width:1;  position:relative; width:100%; height:100%; top:0; left:0; z-index:0; overflow:visible">
  <table width="100%" height="5%" border="0" cellspacing="0" cellpadding="4">
    <tr height="100%">
      <td align="right" nowrap>Quote Date:&nbsp;</td>
      <td align="left" nowrap>
           <input type="text" name="quoteDate" value="<%= quoteDate %>" size='10' maxlength="10" onKeyUp="DateFormat(this,this.value,event,false)" onBlur="DateFormat(this,this.value,event,true)">
           <a href="javascript:show_calendar('f.quoteDate', f.quoteDate.value);"><img src="/PORTAL/common/images/calendarIcon.gif" width="16" height="16" border="0" alt="Select a date from the calendar"></a>
      </td>
      <td align="left" nowrap>
        <input type="button" name="enter" value="Enter" onClick="performLoanPayoffQuote()">
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        <input type="button" name="clear" value="Clear" onClick="clearLoanPayoffQuoteDialog()">
      </td>
    </tr>
  </table>
  <hr align="left" width="100%" noshade>
  <table width="100%" height="33%" border="0" cellspacing="0" cellpadding="4">
	<tr height="5%">
      <td align="right" nowrap>Preferred Payoff:&nbsp;</td>
	  <td align="left" nowrap>
	    <input disabled type="text" name="preferredPayoffAmt" size="15" value="<%= preferredPayoffAmt %>" CURRENCY>
	  </td>
      <td align="right" nowrap>Non-Preferred Payoff:&nbsp;</td>
      <td align="left" nowrap>
        <input disabled type="text" name="nonPreferredPayoffAmt" size="15" value="<%= nonPreferredPayoffAmt %>" CURRENCY>
      </td>
    </tr>
    <tr height="5%">
      <td align="right" nowrap>Gross Payoff:&nbsp;</td>
      <td align="left" nowrap>
        <input disabled type="text" name="grossPayoffAmt" size="15" value="<%= grossPayoffAmt %>" CURRENCY>
      </td>
      <td align="right" nowrap>Unearned Interest:&nbsp;</td>
      <td align="left" nowrap>
        <input disabled type="text" name="unearnedInterest" size="15" value="<%= unearnedInterest %>" CURRENCY>
      </td>
    </tr>
  </table>
  <br>
  <table class="summary" width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr class="heading">
      <th align="left" width="25%">Preferred Payoff</th>
      <th align="left" width="25%">Non-Preferred Payoff</th>
      <th align="left" width="25%">Unearned Interest</th>
      <th align="left" width="25%">Gross Payoff</th>
    </tr>
  </table>
  <span class="scrollableContent" style="border-style:solid; border-width:1;  position:relative; width:100%; height:35%; top:0; left:0; background-color:#BBBBBB">
    <table class="summary" id="loanPayoffQuoteSummary" width="100%" style="border-style:solid; border-width:0;" cellspacing="0" cellpadding="0">
        <%
            String hClassName = "default";
            boolean hSelected = false;

            String sPreferredPayoff = "";
            String sNonPreferredPayoff = "";
            String sUnearnedInterest = "";
            String sGrossPayoff = "";

            if (loanPayoffQuoteVO != null)
            {
                SegmentVO segmentVO = loanPayoffQuoteVO.getSegmentVO();
                InvestmentVO[] investmentVO = segmentVO.getInvestmentVO();
                if (investmentVO != null)
                {
                    for (int i = 0; i < investmentVO.length; i++)
                    {
                        long filteredFundFK = investmentVO[i].getFilteredFundFK();
                        for (int f = 0; f < filteredFundVO.length; f++)
                        {
                            if (filteredFundVO[f].getFilteredFundPK() == filteredFundFK)
                            {
                                FundVO fundVO = (FundVO) filteredFundVO[f].getParentVO(FundVO.class);
                                if (fundVO.getFundType().equalsIgnoreCase("Loan"))
                                {
                                    BucketVO[] bucketVOs = investmentVO[i].getBucketVO();
                                    if (bucketVOs != null)
                                    {
                                        for (int b = 0; b < bucketVOs.length; b++)
                                        {
                                            sPreferredPayoff = Util.roundDollars(bucketVOs[b].getGuarCumValue()).toString();
                                            sNonPreferredPayoff = Util.roundDollars(bucketVOs[b].getLoanCumDollars()).toString();
                                            sUnearnedInterest = Util.roundDollars(bucketVOs[b].getUnearnedInterest()).toString();
                                            sGrossPayoff = Util.roundDollars(new EDITBigDecimal(bucketVOs[b].getGuarCumValue()).addEditBigDecimal
                                                                             (new EDITBigDecimal(bucketVOs[b].getLoanCumDollars()))).toString();
        %>
        <tr class="<%= hClassName %>" isSelected="<%= hSelected %>"
            onClick="selectInvestmentRow()" onMouseOver="highlightRow()" onMouseOut="unhighlightRow()">
          <td nowrap width="25%">
            <script>document.write(formatAsCurrency(<%= sPreferredPayoff %>))</script>
          </td>
          <td nowrap width="25%">
            <script>document.write(formatAsCurrency(<%= sNonPreferredPayoff %>))</script>
          </td>
          <td nowrap width="25%">
            <script>document.write(formatAsCurrency(<%= sUnearnedInterest %>))</script>
          </td>
          <td nowrap width="25%">
            <script>document.write(formatAsCurrency(<%= sGrossPayoff %>))</script>
          </td>
        </tr>
        <%
                                        } // for loop of bucket
                                    } // end of bucket not = null
                                } // end if (fundtype = Loan)
                            } // end of if (filteredFundFK = filteredFundPK)
                        }  //end of for filtered funds
                    } // end for for investments
                } // end if (investment not = null)
            } // end if (loanPayoffQuote not = null)
        %>
    </table>
  </span>
  <table width="100%" height="2%">
    <tr>
      <td align="right">
        <input type="button" name="close" value="Close" onClick="closeLoanPayoffQuoteDialog()">
      </td>
    </tr>
  </table>
</span>

<!-- ****** HIDDEN FIELDS ***** //-->
<input type="hidden" name="transaction" value="">
<input type="hidden" name="action"      value="">

</form>
</body>
</html>
