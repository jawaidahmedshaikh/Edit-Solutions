<!--
 *
 * (c) 2000 - 2004 Systems Engineering Group, LLC.  All rights Reserved
 *  Systems Engineering Group, LLC Propietary and confidential.  Any use
 *  subject to the license agreement.
 -->

<%@ page  errorPage="/common/jsp/error.jsp" autoFlush="true" %>
<%@ page import="java.util.*,
                 edit.common.*,
                 edit.common.vo.*,
                 fission.beans.*,
                 fission.utility.Util" %>

<%
    PageBean formBean = (PageBean) request.getAttribute("formBean");
    SessionBean contractTransactionSessionBean = (SessionBean) session.getAttribute("contractTransactionSessionBean");
    CodeTableWrapper codeTableWrapper = CodeTableWrapper.getSingleton();

    String filterMessage = formBean.getValue("filterMessage");

    PageBean selectedContracts = contractTransactionSessionBean.getPageBean("selectedContracts");

    String[] contracts = selectedContracts.getValues("contracts");

    String filter             = formBean.getValue("filter");
    String segmentFK          = formBean.getValue("segmentFK");
    String editTrxPK          = formBean.getValue("editTrxPK");
    String segmentName        = formBean.getValue("segmentName");
    if (segmentName == null)
    {
        segmentName = "";
    }
    String filterAllocPct     = formBean.getValue("filterAllocPct");
    String filterAllocDollars = formBean.getValue("filterAllocDollars");
    String selectedSegmentPK  = formBean.getValue("selectedSegmentPK");
    String costBasis  	      = formBean.getValue("costBasis");
    String amountReceived     = formBean.getValue("amountReceived");
    String depositsAmt        = formBean.getValue("amountUsed");
    String payeeIndStatus     = formBean.getValue("payeeIndStatus");
    String investmentIndStatus = formBean.getValue("investmentIndStatus");
    String deathStatusEnabled = formBean.getValue("deathStatusEnabled");
    String deathStatus        = formBean.getValue("deathStatus");
    String transactionType    = formBean.getValue("transactionType");
    String companyStructureId = formBean.getValue("companyStructureId");
    String claimStatus        = formBean.getValue("claimStatus");
    String rowId              = formBean.getValue("rowId");

  	PageBean trxBean = contractTransactionSessionBean.getPageBean("formBean");
    String complexChangeType  = trxBean.getValue("complexChangeType");

    CodeTableVO[] deathStatuses = codeTableWrapper.getCodeTableEntries("DEATHSTATUS", Long.parseLong(companyStructureId));
    CodeTableVO[] claimStatuses = codeTableWrapper.getCodeTableEntries("CLAIMSTATUS", Long.parseLong(companyStructureId));

    String displaySegmentName = "";
    if (!segmentName.equals(""))
    {
        displaySegmentName = codeTableWrapper.getCodeDescByCodeTableNameAndCode("SEGMENTNAME", segmentName, Long.parseLong(companyStructureId));
    }

    String rowToMatchBase = selectedSegmentPK;
%>

<html>

<script src="/PORTAL/common/javascript/commonJavascriptFunctions.js"></script>

<script language="Javascript1.2">

	var f = null;

    var transactionType = "<%= transactionType %>";
    var rowId = "<%= rowId %>";
    var segmentName = "<%= segmentName %>";
    var filterMessage = "<%= filterMessage %>";
    var contractNumber = "<%= filter %>";
    var coverage = "<%= segmentName %>";
    var height = screen.height;
    var width = screen.width;

	function init()
    {
		f = document.filterAllocationsForm;

        if (transactionType == "Premium") {

            f.filterAllocPct.disabled = true;
            f.deathStatus.disabled = true;
        }

        if (filterMessage != "" &&
            filterMessage != null &&
            filterMessage != "Keep Page") {

            alert(filterMessage);
        }
        else if (filterMessage != "Keep Page") {

            closeWindow();
        }

        formatCurrency();
	}

    function closeFilterAllocationsDialog()
    {
        f.rowId.value = rowId;
        f.segmentName.value = segmentName;
		sendTransactionAction("ContractDetailTran", "closeFilterAllocDialog", "_self");
    }

    function cancelFilterAllocation()
    {
		clearForm();
    }

	function showFilterAllocationDetail()
    {
        var tdElement = window.event.srcElement;
        var trElement = tdElement.parentElement;

        var segmentName    = trElement.segmentName;
        var contractNumber = trElement.contractNumber;
        var rowId          = trElement.rowId;

        f.filter.value      = contractNumber;
        f.segmentName.value = segmentName;
        f.rowId.value       = rowId;

		sendTransactionAction("ContractDetailTran", "showFilterAllocationDetail", "_self");
	}

	function saveFilterAllocationToSummary()
    {
        f.rowId.value = rowId;
		sendTransactionAction("ContractDetailTran", "saveFilterAllocToSummary", "_self");
	}

	function clearForm()
    {
		f.filter.value       = "";
        f.segmentName.value  = "";
		f.allocPercent.value = "";
		f.allocDollars.value = "";
        f.rowId.value        = "";
	}

	function showTrxSuspenseDialog()
    {
        var amount = f.filterAllocDollars.value;

        if (contractNumber == "" ||
            coverage == "")
        {
            alert("You must select a Contract before selecting Suspense.");
        }
        else if (amount == "" || amount == "0")
        {
            alert("You must enter allocation dollars before selecting Suspense.");
        }
        else
        {
            var width = .99 * screen.width;
            var height = .95 * screen.height;

            openDialog("trxSuspenseDialog", "top=0,left=0,resizable=no", width, height);

            sendTransactionAction("ContractDetailTran", "showTrxSuspenseDialog", "trxSuspenseDialog");
        }
	}

	function showInvestmentDialog()
    {
        var width = .75 * screen.width;
        var height = .70 * screen.height;

		openDialog("investmentDialog", "left=15,top=30,resizable=no", width, height);

        sendTransactionAction("ContractDetailTran", "showInvestmentDialog", "investmentDialog");
	}

	function showPayeeDialog()
    {
        var width = .95 * screen.width;
        var height = .60 * screen.height;

		openDialog("payeeDialog", "left=0,top=0,resizable=no", width, height);

        sendTransactionAction("ContractDetailTran", "showPayeeDialog", "payeeDialog");
	}
</script>

<head>

<title>Filter Allocations</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta http-equiv="Cache-Control" content="no-store">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Expires" content="0">

<link href="/PORTAL/common/css/EDITSolutions.css" rel="stylesheet" type="text/css">

</head>

<body class="mainTheme" onLoad="init()"style="background-color:#DDDDDD">
<form name="filterAllocationsForm" method="post" action="/PORTAL/servlet/RequestManager">
  <table width="100%" height="10%" border="0">
    <tr>
      <td align="left" nowrap colspan="2">Filter:&nbsp;
        <input disabled type="text" name="filter" size="15" maxlength="15" value="<%= filter %>">
        &nbsp;&nbsp;&nbsp;Segment&nbsp;
        <input disabled type="text" name="segment" size="15" maxlength="15" value="<%= displaySegmentName %>">
      </td>
    </tr>
    <tr>
      <td align="left" nowrap>Allocation Percent:&nbsp;
        <input disabled type="text" name="filterAllocPct" size="11" maxlength="11" value="<%= filterAllocPct %>">
      </td>
      <td align="left" nowrap>Allocation Dollars:&nbsp;
        <input disabled type="text" name="filterAllocDollars" size="11" maxlength="11" value="<%= filterAllocDollars %>" CURRENCY>
      </td>
    </tr>
    <tr>
      <td nowrap align="left">&nbsp;</td>
      <td align="left">
        <a href="javascript:showTrxSuspenseDialog()">Suspense:</a>&nbsp;
        <input type="text" name="amountUsed" size="15" disabled maxlength="15" value="<%= depositsAmt %>" CURRENCY>
      </td>
    </tr>
    <tr>
<%--      <td width="15%" align="left" >Cost Basis:&nbsp;--%>
<%--        <input type="text" name="costBasis" size="12" maxlength="12" value="<%= costBasis %>" CURRENCY>--%>
<%--      </td>--%>
      <td width="15%" align="left" colspan="2" nowrap>Complex ChangeType:&nbsp;
        <input disabled type="text" name="complexChangeType" size="20" maxlength="20" value="<%= complexChangeType %>">
      </td>
     </tr>
    <tr>
      <td align="left">Death Status:&nbsp;
        <select name="deathStatus">
          <%
              out.println("<option>Please Select</option>");

              for(int i = 0; i < deathStatuses.length; i++) {

                  String codeTablePK = deathStatuses[i].getCodeTablePK() + "";
                  String codeDesc    = deathStatuses[i].getCodeDesc();
                  String code        = deathStatuses[i].getCode();

                  if (deathStatus.equalsIgnoreCase(code)) {

                      out.println("<option selected name=\"id\" value=\"" + codeTablePK+ "\">" + codeDesc + "</option>");
                  }
                  else  {

                      out.println("<option name=\"id\" value=\"" + codeTablePK + "\">" + codeDesc + "</option>");
                  }
              }
          %>
        </select>
      </td>
      <td align="left">Claim Status:&nbsp;
        <select name="claimStatus">
          <%
              out.println("<option>Please Select</option>");

              for(int i = 0; i < claimStatuses.length; i++) {

                  String codeTablePK = claimStatuses[i].getCodeTablePK() + "";
                  String codeDesc    = claimStatuses[i].getCodeDesc();
                  String code        = claimStatuses[i].getCode();

                  if (claimStatus.equalsIgnoreCase(code)) {

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
    <tr>
      <td>
        <input type="checkbox" name="investmentInd" <%= investmentIndStatus %> disabled>
        <a href="javascript:showInvestmentDialog()">Investments</a>
      </td>
      <td>
        <input type="checkbox" name="payeeInd" <%= payeeIndStatus %> disabled>
        <a href="javascript:showPayeeDialog()">Payee Overrides</a></td>
      </td>
    </tr>
    <tr>
      <td nowrap align="left" colspan="4">
      	<input type="button" value="   Save  " style="background-color:#DEDEDE" onClick="saveFilterAllocationToSummary()">
      	<input type="button" value="  Cancel " style="background-color:#DEDEDE" onClick="cancelCurrentFilter()">
      </td>
    </tr>
  </table>
  <table id="summaryTable" class="summary" width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr class="heading">
	  <th align="left" width="20%">Filter</th>
      <th align="left" width="20%">Segment Name</th>
      <th align="left" width="20%">Option Code</th>
	  <th align="left" width="20%">Allocation Percent</th>
	  <th align="left" width="20%">Allocation Dollars</th>
	</tr>
  </table>
 <span class="scrollableContent" style="border-style:solid; border-width:1;  position:relative; width:100%; height:40%; top:0; left:0; background-color:#BBBBBB">
   <table class="summary" id="filterAllocationsSummary" width="100%" style="border-style:solid; border-width:0;" cellspacing="0" cellpadding="0">
      <%
          String className = "";
          boolean selected = false;
          String iSegmentName = "";
          String iOptionCode = "";
          String iContractNumber = "";
          String iSegmentPK = "";
          String allocPct = "";
          String allocDollars = "";
          if (contracts != null)
          {
              for (int c = 0; c < contracts.length; c++)
              {
                  String[] tokenizedContract = Util.fastTokenizer(contracts[c], ",");
                  iContractNumber = tokenizedContract[0];
                  iSegmentPK = tokenizedContract[1];
                  iSegmentName = tokenizedContract[3];
                  iOptionCode = tokenizedContract[5];
                  allocPct = tokenizedContract[6];
                  allocDollars = tokenizedContract[7];

                  if (iSegmentPK.equalsIgnoreCase(rowToMatchBase))
                  {
                      className = "highlighted";
                      selected = true;
                  }
                  else {

                      className = "default";
                      selected = false;
                  }
      %>
        <tr class="<%= className %>" isSelected="<%= selected %>" segmentName="<%= iSegmentName %>"
            contractNumber="<%= iContractNumber %>" rowId="<%= c %>"
            onMouseOver="highlightRow()" onMouseOut="unhighlightRow()"
            onClick="showFilterAllocationDetail()">

            <td align="left" nowrap width="20%">
                <%= iContractNumber %>
            </td>
            <td align="left" nowrap width="20%">
                <%= iSegmentName %>
            </td>
            <td align="left" nowrap width="20%">
                <%= iOptionCode %>
            </td>
            <td align="left" nowrap width="20%">
                <%= allocPct %>
            </td>
            <td align="left" nowrap width="20%">
               <script>document.write(formatAsCurrency(<%= allocDollars %>))</script>
            </td>
        </tr>
      <%
              }
          }
      %>
    </table>
  </span>
  <table width="100%" border="0" cellspacing="6" cellpadding="0">
    <tr>
	  <td align="right" nowrap>
	    <input type="button" name="cancel" value="Close" onClick ="closeFilterAllocationsDialog()">
	  </td>
	</tr>
  </table>

<!-- ****** HIDDEN FIELDS ***** //-->
  <input type="hidden" name="transaction" value="">
  <input type="hidden" name="action"      value="">
  <input type="hidden" name="filter" value="<%= filter %>">
  <input type="hidden" name="filterMessage" value="<%= filterMessage %>">
  <input type="hidden" name="filterAllocPct" value="<%= filterAllocPct %>">
  <input type="hidden" name="filterAllocDollars" value="<%= filterAllocDollars %>">
  <input type="hidden" name="segmentName" value="<%= segmentName %>">
  <input type="hidden" name="segmentFK" value="<%= segmentFK %>">
  <input type="hidden" name="selectedSegmentPK" value="<%= selectedSegmentPK %>">
  <input type="hidden" name="editTrxPK" value="<%= editTrxPK %>">
  <input type="hidden" name="amountUsed"  value="<%= depositsAmt %>">
  <input type="hidden" name="transactionType" value="<%= transactionType %>">
  <input type="hidden" name="companyStructureId" value="<%= companyStructureId %>">
  <input type="hidden" name="payeeIndStatus" value="<%= payeeIndStatus %>">
  <input type="hidden" name="investmentIndStatus" value="<%= investmentIndStatus %>">
  <input type="hidden" name="rowId" value="<%= rowId %>">
  <input type="hidden" name="complexChangeType" value="">

</form>
</body>
</html>
