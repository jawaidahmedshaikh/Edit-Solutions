<!-- ****** JAVA CODE ***** //-->

<%@ page  errorPage="/common/jsp/error.jsp" autoFlush="true" %>
<%@ page import="java.util.*,
                 fission.beans.*,
                 edit.common.*,
                 edit.common.vo.*,
                 fission.utility.Util" %>

<jsp:useBean id="contractSuspense"
    class="fission.beans.SessionBean" scope="session"/>

<jsp:useBean id="contractTransSuspense"
   class="fission.beans.SessionBean" scope="session"/>

<jsp:useBean id="contractTransactionSessionBean"
   class="fission.beans.SessionBean" scope="session"/>

<jsp:useBean id="formBean"
   class="fission.beans.PageBean" scope="request"/>

<jsp:useBean id="contractMainSessionBean"
    class="fission.beans.SessionBean" scope="session"/>
<%
    String amtNeeded     = formBean.getValue("amountNeeded");
    String amtUsed       = formBean.getValue("amountUsed");
    String suspensePK    = formBean.getValue("suspensePK");
    String outSuspensePK = formBean.getValue("outSuspensePK");

    String scrollingTrxPageSize = (String) request.getAttribute("scrollingTrxPageSize");
    String beginScrollingTrxPK = (String) request.getAttribute("beginScrollingTrxPK");
    String endScrollingTrxPK = (String) request.getAttribute("endScrollingTrxPK");

    String filter             = formBean.getValue("filter");
    String filterMessage      = formBean.getValue("filterMessage");
    String segmentFK          = formBean.getValue("segmentFK");
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
    String companyStructureId = formBean.getValue("companyStructureId");
    String rowId              = formBean.getValue("rowId");
%>

<%!
    private TreeMap sortSuspenseByEffectiveDate(SessionBean suspense) {

		Map suspenseBeans = suspense.getPageBeans();

		TreeMap sortedSuspenseEntries = new TreeMap();

		Iterator enumer  = suspenseBeans.values().iterator();

		while (enumer.hasNext()) {

			PageBean suspenseBean = (PageBean) enumer.next();

            String suspensePK     = suspenseBean.getValue("suspensePK");

			sortedSuspenseEntries.put(suspenseBean.getValue("effectiveDate") + suspensePK, suspenseBean);
		}

		return sortedSuspenseEntries;
	}

    private TreeMap sortSelectedSuspenseByEffectiveDate(SessionBean transSuspense) {

		Map suspenseBeans = transSuspense.getPageBeans();

		TreeMap sortedSelectedSuspense = new TreeMap();

		Iterator enumer  = suspenseBeans.values().iterator();

		while (enumer.hasNext()) {

			PageBean suspenseBean = (PageBean) enumer.next();

            String suspensePK     = suspenseBean.getValue("suspensePK");
            String outSuspensePK  = suspenseBean.getValue("outSuspensePK");

			sortedSelectedSuspense.put(suspenseBean.getValue("effectiveDate") + suspensePK + outSuspensePK, suspenseBean);
		}

		return sortedSelectedSuspense;
	}
%>

<html>

<script src="/PORTAL/common/javascript/commonJavascriptFunctions.js"></script>

<script language="Javascript1.2">

	var dialog = null;

	var f = null;
    var amountNeeded = "<%= amtNeeded %>";
    var amountUsed = "<%= amtUsed %>";

	function init()
    {
		f = document.suspenseForm;

        formatCurrency();
	}

	function selectRow()
    {
		var tdElement = window.event.srcElement;
		var trElement = tdElement.parentElement;

		var rowId     = trElement.id;
		var parsedRowId = (rowId.split("_"))[1];

		var suspensePK = trElement.suspensePK;

		var inUseAmountTd  = document.getElementById("inUseAmount_" + parsedRowId);
		var inUseAmount    = inUseAmountTd.innerText;

        var suspenseAmountTd = document.getElementById("suspenseAmount_" + parsedRowId);
        var suspenseAmount   = suspenseAmountTd.innerText;

        if (amountNeeded == amountUsed)
        {
            alert("Suspense Amount needed has been reached.");
        }
        else
        {
            if (inUseAmount == suspenseAmount)
            {
                alert("Suspense Not Available - Entire Amount is Already In Use.");
            }
            else
            {
                f.suspensePK.value    = suspensePK;
                sendTransactionAction("ContractDetailTran", "selectSuspenseEntry", "_self");
            }
        }
	}

    function highlightSelectedSuspense()
    {
		var tdElement  = window.event.srcElement;
		var trElement  = tdElement.parentElement;
		var suspensePK = trElement.id;
        var outSuspensePK = trElement.outSuspensePK;
		f.suspensePK.value = suspensePK;
        f.outSuspensePK.value = outSuspensePK;
		sendTransactionAction("ContractDetailTran", "highlightSuspenseEntry", "_self");
    }

	function deleteSelectedSuspense()
    {
        f.suspensePK.value = "<%= suspensePK %>";
        f.outSuspensePK.value = "<%= outSuspensePK %>";
        if (f.suspensePK.value == "")
        {
            alert ("Please Select A Suspense Record to Delete");
        }
        else
        {
            sendTransactionAction("ContractDetailTran", "deleteSelectedTrxSuspense", "_self");
        }
	}

	function cancelSuspenseChanges()
    {
		sendTransactionAction("ContractDetailTran", "cancelTrxSuspenseEntry", "_self");
		window.close();
	}

	function saveSuspenseChanges()
    {
        <%
        try
        {
            if ((Util.isANumber(amtNeeded) &&
                 Util.isANumber(amtUsed)) &&
                 //(Double.parseDouble(amtNeeded) != Double.parseDouble(amtUsed)))
                (!new EDITBigDecimal(amtNeeded).isEQ(new EDITBigDecimal(amtUsed))))
            {
        %>
                alert("Not enough suspense selected.  Amount Needed must equal Amount Used");
        <%
            }
            else
            {
        %>
                sendTransactionAction("ContractDetailTran", "saveTrxSuspenseChanges", "filterAllocDialog");
                window.close();
        <%
            }
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());


        }
        %>
	}

</script>

<head>
<title>EDITSOLUTIONS - Transaction Suspense</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta http-equiv="Cache-Control" content="no-store">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Expires" content="0">

<link href="/PORTAL/common/css/EDITSolutions.css" rel="stylesheet" type="text/css">
</head>

<body class="mainTheme" onLoad="init()">
<form name= "suspenseForm" method="post" action="/PORTAL/servlet/RequestManager">

  <table width="100%" height="5%" border="0" cellspacing="6" cellpadding="0">
    <tr>
      <td>Amount Needed:
        <input type="text" name="amountNeeded" disabled size="15" maxlength="15" value="<%= amtNeeded %>" CURRENCY>
      </td>
      &nbsp;&nbsp;
      <td>Amount Used:
        <input type="text" name="amountUsed" disabled size="15" maxlength="15" value="<%= amtUsed %>" CURRENCY>
      </td>
  </table>

  <table width="100%" height="1%" border="0" cellspacing="0" cellpadding="0">
    <tr height="5%">
      <td align="left" height="14">
	    <input type="button" name="delete" value="Delete" onClick="deleteSelectedSuspense()">
	  </td>
	</tr>
  </table>
  <table class="summary" width="100%" cellspacing="0" border="0">
    <tr class="heading">
      <th align="left" width="25%">Effective Date</th>
      <th align="left" width="25%">Suspense Amount</th>
      <th align="left" width="25%">Coverage</th>
      <th align="left" width="25%">Amount Used</th>
    </tr>
  </table>
  <span class="scrollableContent" style="border-style:solid; border-width:1;  position:relative; width:100%; height:30%; top:0; left:0; background-color:#BBBBBB">
    <table class="summary" id="availableTrxSuspenseSummary" width="100%" style="border-style:solid; border-width:0;" cellspacing="0" cellpadding="0">
      <%
         int columnId1 = 0;

         String rowToMatch1 = "";

         String trClass1 = "";
         boolean selected1 = false;

         Map sortedSelectedSuspense = sortSelectedSuspenseByEffectiveDate(contractTransSuspense);

         Iterator itS = sortedSelectedSuspense.values().iterator();

         while (itS.hasNext())
        {
             PageBean suspenseBean = (PageBean)itS.next();

             String suspenseStatus = suspenseBean.getValue("suspenseStatus");
             if (!suspenseStatus.equals("deleted"))
             {
                 String sOutSuspensePK  = suspenseBean.getValue("outSuspensePK");
                 String sSuspensePK    = suspenseBean.getValue("suspensePK");
                 String effectiveDate  = suspenseBean.getValue("effectiveDate");
                 String suspenseAmount = suspenseBean.getValue("amount");
                 String suspAmtUsed    = suspenseBean.getValue("amountUsed");
                 String optionIdValue  = suspenseBean.getValue("optionId");
                 rowToMatch1 = sSuspensePK;

                 if (rowToMatch1.equals(suspensePK))
                 {
                     trClass1 = "highlighted";
                     selected1 = true;
                 }
                 else
                 {
                     trClass1 = "default";
                     selected1 = false;
                 }
       %>
       <tr class="<%= trClass1 %>" isSelected="<%= selected1 %>" id="<%= sSuspensePK %>"
           outSuspensePK="<%= sOutSuspensePK %>" onMouseOver="highlightRow()"
           onMouseOut="unhighlightRow()" onClick="highlightSelectedSuspense()">
         <td width="25%" nowrap id="effectiveDate_<%= columnId1 %>">
            <%= effectiveDate %>
         </td>
         <td width="25%" nowrap id="suspenseAmount_<%= columnId1 %>">
            <script>document.write(formatAsCurrency(<%= suspenseAmount %>))</script>
         </td>
         <td width="25%" nowrap id="coverage_<%= columnId1 %>">
            <%= optionIdValue %>
         </td>
         <td width="25%" nowrap id="suspAmtUsed_<%= columnId1++ %>">
            <script>document.write(formatAsCurrency(<%= suspAmtUsed %>))</script>
         </td>
       </tr>
       <%
             } // end if
         }// end while
       %>
    </table>
  </span>
  <table class="summary" width="100%" cellspacing="0" border="0">
    <tr class="heading">
      <th align="left" width="25%">Effective Date</th>
      <th align="left" width="25%">Suspense Amount</th>
      <th align="left" width="25%">Coverage/Rider</th>
      <th align="left" width="25%">In Use</th>
    </tr>
  <span class="scrollableContent" style="border-style:solid; border-width:1;  position:relative; width:100%; height:30%; top:0; left:0; background-color:#BBBBBB">
    <table class="summary" id="selectedTrxSuspenseSummary" width="100%" style="border-style:solid; border-width:0;" cellspacing="0" cellpadding="0">
        <%
          int rowId2   = 0;
          int columnId = 0;

          String trClass = "";
          boolean selected = false;

          Map sortedSuspenseEntries = sortSuspenseByEffectiveDate(contractSuspense);

          Iterator it = sortedSuspenseEntries.values().iterator();

          while (it.hasNext())  {

              PageBean suspenseBean = (PageBean)it.next();

              String sSuspensePK = suspenseBean.getValue("suspensePK");
              String sOptionIdValue = suspenseBean.getValue("optionId");
              String sEffectiveDate = suspenseBean.getValue("effectiveDate");
              String sSuspenseAmount = suspenseBean.getValue("amount");
              String sInUseAmount   = suspenseBean.getValue("inUseAmount");
        %>
        <tr class="<%= trClass %>" isSelected="<%= selected %>" id="rowId_<%= rowId2++ %>"
            suspensePK="<%= sSuspensePK %>" onClick="selectRow()" onMouseOver="highlightRow()"
            onMouseOut="unhighlightRow()">
          <td width="25%" nowrap id="effectiveDate_<%= columnId %>">
            <%= sEffectiveDate %>
          </td>
          <td width="25%" nowrap id="suspenseAmount_<%= columnId %>">
            <script>document.write(formatAsCurrency(<%= sSuspenseAmount %>))</script>
          </td>
          <td width="25%" nowrap id="coverage_<%= columnId %>">
            <%= sOptionIdValue %>
          </td>
          <td width="25%" nowrap id="inUseAmount_<%= columnId++ %>">
            <script>document.write(formatAsCurrency(<%= sInUseAmount %>))</script>
          </td>
        </tr>
        <%
          }// end while
        %>
    </table>
  </span>
  <table>
    <tr>
      <td nowrap width="5%" height="1%" align="right">&nbsp;
        <input type="button" name="enter" value="Enter" onClick="saveSuspenseChanges()">
        &nbsp;&nbsp;&nbsp;
        <input type="button" name="cancel" value="Cancel" onClick="cancelSuspenseChanges()">
      </td>
    </tr>
  </table>

<!-- ****** HIDDEN FIELDS ***** //-->
 <input type="hidden" name="transaction" value="">
 <input type="hidden" name="action"      value="">
 <input type="hidden" name="suspensePK"  value="">
 <input type="hidden" name="outSuspensePK" value="">
 <input type="hidden" name="scrollingTrxPageSize" value="<%= scrollingTrxPageSize %>">
 <input type="hidden" name="beginScrollingTrxPK" value="<%= beginScrollingTrxPK %>">
 <input type="hidden" name="endScrollingTrxPK" value="<%= endScrollingTrxPK %>">
 <input type="hidden" name="amountNeeded" value="<%= amtNeeded %>">
 <input type="hidden" name="amountUsed" value="<%= amtUsed %>">

 <input type="hidden" name="filter" value="<%= filter %>">
 <input type="hidden" name="filterMessage" value="<%= filterMessage %>">
 <input type="hidden" name="segmentName" value="<%= segmentName %>">
 <input type="hidden" name="segmentFK" value="<%= segmentFK %>">
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

</form>
</body>
</html>
