<!-- ****** JAVA CODE ***** //-->

<%@ page  errorPage="/common/jsp/error.jsp" autoFlush="true" %>
<%@ page import="java.util.*,
                 edit.common.*,
                 edit.common.vo.*,
                 fission.beans.*,
                 fission.utility.Util" %>

<jsp:useBean id="contractDepositBucketsBean"
    class="fission.beans.SessionBean" scope="session"/>

<%
    CodeTableWrapper codeTableWrapper = CodeTableWrapper.getSingleton();

    String investmentFK = (String) request.getAttribute("investmentFK");
    String bucketPK     = (String) request.getAttribute("bucketPK");
    String cumDollarsUnits = "";
    String cumDollars   = (String) request.getAttribute("cumDollars");
    if (cumDollars == null) {

        cumDollars = "0";
    }
    String cumUnits     = (String) request.getAttribute("cumUnits");
    if (cumUnits == null) {

        cumUnits = "0";
    }
    if (new EDITBigDecimal(cumUnits).isEQ(new EDITBigDecimal())) {

        cumDollarsUnits = cumDollars;
    }
    else {

        cumDollarsUnits = cumUnits;
    }

    String depositDate      = (String) request.getAttribute("depositDate");
    if (depositDate == null) {

        depositDate = "";
    }

    String renewalDate      = (String) request.getAttribute("renewalDate");
    if (renewalDate == null) {

        renewalDate = "";
    }

    String depositAmount    = (String) request.getAttribute("depositAmount");
    if (depositAmount == null) {

        depositAmount = "";
    }

    String bonusAmount = (String) request.getAttribute("bonusAmount");
    if (bonusAmount == null)
    {
        bonusAmount = "";
    }

    String rebalanceAmount = (String) request.getAttribute("rebalanceAmount");
    if (rebalanceAmount == null)
    {
        rebalanceAmount = "";
    }

    String indexCapRate = (String) request.getAttribute("indexCapRate");
    if (indexCapRate == null)
    {
        indexCapRate = "";
    }

    String lastValDate      = (String) request.getAttribute("lastValDate");
    if (lastValDate == null) {

        lastValDate = "";
    }

    String interestRate     = (String) request.getAttribute("interestRate");
    if (interestRate == null) {

        interestRate = "";
    }
    else if (!interestRate.equals("")) {

       // interestRate = Util.formatDecimal("0.#######", Double.parseDouble(interestRate));
        interestRate = Util.formatDecimal("0.#######", new EDITBigDecimal(interestRate));
    }

    String priorBucketRate  = (String) request.getAttribute("priorBucketRate");
    if (priorBucketRate == null)
    {
        priorBucketRate = "";
    }
    else if (!priorBucketRate.equals(""))
    {
        priorBucketRate = Util.formatDecimal("0.#######", new EDITBigDecimal(priorBucketRate));
    }

    String duration         = (String) request.getAttribute("duration");
    if (duration == null) {
        duration = "";
    }

    String bucketInterestRate = (String) request.getAttribute("bucketInterestRate");
    if (bucketInterestRate == null)
    {
        bucketInterestRate = "";
    }
    else if (!bucketInterestRate.equals(""))
    {
        bucketInterestRate = Util.formatDecimal("0.#######", new EDITBigDecimal(bucketInterestRate));
    }

    String poUnits            = (String) request.getAttribute("poUnits");
    if (poUnits == null) {

        poUnits = "";
    }
    String poDollars          = (String) request.getAttribute("poDollars");
    if (poDollars == null) {

        poDollars = "";
    }

    String  boIntRate        = Util.initString((String) request.getAttribute("boIntRate"), "0");
    if (boIntRate == null)
    {
        boIntRate = "";
    }
    else
    {
        boIntRate = Util.formatDecimal("0.#######", new EDITBigDecimal(boIntRate));
    }
    String  boDuration       = (String) request.getAttribute("boDuration");
    if (boDuration == null)
    {
        boDuration = "";
    }

    String beginningBucketPK = (String) request.getAttribute("beginningBucketPK");
    String endingBucketPK    = (String) request.getAttribute("endingBucketPK");

	String rowToMatchBase     = bucketPK;
%>

<%!
    private TreeMap sortBucketsByDepositDate(SessionBean contractDepositBuckets) {

		Map buckets = contractDepositBuckets.getPageBeans();

		TreeMap sortedBuckets = new TreeMap();

		Iterator enumer  = buckets.values().iterator();

		while (enumer.hasNext()) {

			PageBean bucketBean = (PageBean) enumer.next();

			String depositDate = bucketBean.getValue("depositDate");

            if (!depositDate.equals("")) {

                sortedBuckets.put(depositDate + bucketBean.getValue("bucketPK"), bucketBean);
            }
		}

		return sortedBuckets;
	}
%>

<html>

<script src="/PORTAL/common/javascript/commonJavascriptFunctions.js"></script>

<script language="Javascript1.2">

	var f = null;

	function init() 
    {
		f = document.depositBucketsForm;
        
        formatCurrency();
	}

    function scrollBucketsForward()
    {
        f.scrollDirection.value = "FORWARD";

        sendTransactionAction("ContractDetailTran", "scrollDepositBuckets", "_self");
    }

    function scrollBucketsBackward()
    {
        f.scrollDirection.value = "BACKWARD";

        sendTransactionAction("ContractDetailTran", "scrollDepositBuckets", "_self");
    }

	function selectRow() 
    {
		var tdElement = window.event.srcElement;
		var trElement = tdElement.parentElement;

		var selectedBucket = trElement.id;

        f.selectedBucket.value = selectedBucket;

		sendTransactionAction("ContractDetailTran", "showDepositBucketsDetailSummary", "_self");
	}
</script>

<head>

<link href="/PORTAL/common/css/EDITSolutions.css" rel="stylesheet" type="text/css">

<title>Deposit Buckets</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>

<body class="mainTheme" onLoad="init()">

<form name= "depositBucketsForm" method="post" action="/PORTAL/servlet/RequestManager">

  <table width="100%" height="45%" border="0" cellspacing="0" cellpadding="5">
    <tr>
	  <td align="right" nowrap>Deposit Amount:&nbsp;
      </td>
      <td align="left">
        <input type="text" name="depositAmount" disabled maxlength="15" size="15" value="<%= depositAmount %>" CURRENCY>
      </td>
      <td align="right">
            Bucket Interest Rate:&nbsp;
      </td>
	  <td align="left" nowrap>
        <input type="text" name="bucketInterestRate" disabled maxlength="15" size="15" value="<%= bucketInterestRate %>">
      </td>
    </tr>
    <tr>
	  <td align="right" nowrap>Bonus Amount:&nbsp;
      </td>
      <td align="left">
        <input type="text" name="bonusAmount" disabled maxlength="15" size="15" value="<%= bonusAmount %>" CURRENCY>
      </td>
      <td align="right">
        Index Cap Rate:&nbsp;
      </td>
	  <td align="left" nowrap>
        <input type="text" name="indexCapRate" disabled maxlength="15" size="15" value="<%= indexCapRate %>">
      </td>
    </tr>
    <tr>
      <td align="right" nowrap>Deposit Date:&nbsp;
      </td>
      <td>
		<input type="left" name="depositDate" disabled maxlength="10" size="10" value="<%= depositDate %>">
      </td>
      <td align="right">
        Renewal Date:&nbsp;
      </td>
      <td align="left" nowrap>
		<input type="text" name="renewalDate" disabled maxlength="10" size="10" value="<%= renewalDate %>">
      </td>
    </tr>
    <tr>
      <td align="right" nowrap>Cumulative Dollars/Units:&nbsp;
      </td>
      <td align="left">
        <input type="text" name="cumDollarsUnits" disabled maxlength="15" size="15" value="<%= cumDollarsUnits %>" CURRENCY>
      </td>
      <td align="right">
        Last Valuation Date:&nbsp;
      </td>
      <td align="left" nowrap>
		<input type="text" name="lastValDate" disabled maxlength="10" size="10" value="<%= lastValDate %>">
      </td>
    </tr>
    <tr>
      <td align="right" nowrap>Interest Rate Override:&nbsp;
      </td>
      <td align="left">
        <input type="text" name="interestRate" disabled maxlength="15" size="15" value="<%= interestRate %>">
      </td>
      <td align="right">
            Duration Override:&nbsp;
      </td>
      <td align="left" nowrap>
		<input type="text" name="duration" disabled maxlength="3" size="3" value="<%= duration %>">
      </td>
    </tr>
    <tr>
      <td align="right" nowrap>Payout Units:&nbsp;
      </td>
      <td align="left">
        <input type="text" name="poUnits" disabled maxlength="15" size="15" value="<%= poUnits %>">
      </td>
      <td align="right">
        Payout Dollars:&nbsp;
      </td>
      <td align="left" nowrap>
		<input type="text" name="poDollars" disabled maxlength="15" size="15" value="<%= poDollars %>" CURRENCY>
      </td>
    </tr>
    <tr>
      <td align="right" nowrap>Bonus Interest Rate:&nbsp;
      </td>
      <td align="left">
        <input type="text" name="boIntRate" disabled maxlength="10" size="10" value="<%= boIntRate %>">
      </td>
      <td align="right">
        Bonus Int.Rate Dur:&nbsp;
      </td>
      <td align="left" nowrap>
		<input type="text" name="boDuration" disabled maxlength="8" size="8" value="<%= boDuration %>">
      </td>
    </tr>
    <tr>
      <td align="right" nowrap>Rebalance Amount:&nbsp;
      </td>
      <td align="left">
        <input type="text" name="rebalanceAmount" disabled maxlength="15" size="15" value="<%= rebalanceAmount %>" CURRENCY>
      </td>
      <td align="right">Prior Interest/Index Cap Rate:&nbsp;
      </td>
      <td align="left" nowrap>
        <input type="text" name="priorBucketRate" disabled maxlength="15" size="15" value="<%= priorBucketRate %>">
      </td>
    </tr>
  </table>

  <br>

 <table width="100%" height="2%" border="0" cellspacing="0" cellpadding="0">
   <tr>
     <td nowrap align="right">
  		<input type="button" value="   <Prev   " onClick="scrollBucketsBackward()">
  		<input type="button" value="   Next>   " onClick="scrollBucketsForward()">
     </td>
   </tr>
 </table>

 <table id="summaryTable" class="summary" width="100%" border="0" cellspacing="0" cellpadding="0">
   <tr class="heading">
     <th width="33%">Deposit Amount</th>
     <th width="33%">Deposit Date</th>
	 <th width="33%">Cum Dollars/Units</th>
   </tr>
 </table>
 <span class="scrollableContent" style="border-style:solid; border-width:1;  position:relative; width:100%; height:45%; top:0; left:0; background-color:#BBBBBB">
   <table class="summary" id="depositBucketsSummary" width="100%" style="border-style:solid; border-width:0;" cellspacing="0" cellpadding="0">
	<%
		String rowToMatch = "";
		String trClass = "";
		boolean selected = false;

        Map sortedBuckets = sortBucketsByDepositDate(contractDepositBucketsBean);

        Iterator it = sortedBuckets.values().iterator();

		while (it.hasNext()) {

            PageBean bucketBean = (PageBean) it.next();

            String sBucketPK      = bucketBean.getValue("bucketPK");
            String sDepositAmount = bucketBean.getValue("depositAmount");
            String sDepositDate   = bucketBean.getValue("depositDate");
			String sCumDollars    = bucketBean.getValue("cumDollars");
            String sCumUnits      = bucketBean.getValue("cumUnits");
            String sCumDollarsUnits = "";
            if (new EDITBigDecimal(sCumUnits).isEQ(new EDITBigDecimal())) {

                sCumDollarsUnits = sCumDollars;
            }
            else {

                sCumDollarsUnits = sCumUnits;
            }

            rowToMatch = sBucketPK;

			if (rowToMatch.equals(rowToMatchBase)) {

				trClass = "highlighted";
				selected = true;
			}
			else {

				trClass = "default";
				selected = false;
			}
	%>

	<tr class="<%= trClass %>" isSelected="<%= selected %>" id="<%= sBucketPK %>"
        onMouseOver="highlightRow()" onMouseOut="unhighlightRow()" onClick="selectRow()">
	  <td nowrap align="left" width="33%">
        <script>document.write(formatAsCurrency(<%= sDepositAmount %>))</script>
	  </td>
	  <td nowrap align="left" width="33%">
		<%= sDepositDate %>
	  </td>
	  <td nowrap align="left" width="33%">
      <%
          if (new EDITBigDecimal(sCumUnits).isEQ(new EDITBigDecimal()))
          {
      %>
          <script>document.write(formatAsCurrency(<%= sCumDollarsUnits %>))</script>
      <%
          }
          else
          {
      %>
  		  <%= sCumDollarsUnits %>
      <%
          }
      %>
	  </td>
	</tr>
	<%
        }
	%>
    <tr class="filler">
        <td colspan="3">
             &nbsp;
        </td>
    </tr>
  </table>
  </span>
    </td>
   </tr>
</table>

  <table id="closeTable" width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
	  	<td colspan="8" nowrap align="right">
	  		<input type="button" value="   Close  " onClick="closeWindow()">
        </td>
    </tr>
  </table>

<!-- ****** HIDDEN FIELDS ***** //-->
 <input type="hidden" name="transaction" value="">
 <input type="hidden" name="action"      value="">

 <input type="hidden" name="selectedBucket" value="">
 <input type="hidden" name="scrollDirection" value="">
 <input type="hidden" name="investmentFK" value="<%= investmentFK %>">
 <input type="hidden" name="beginningBucketPK" value="<%= beginningBucketPK %>">
 <input type="hidden" name="endingBucketPK" value="<%= endingBucketPK %>">

</form>
</body>
</html>






