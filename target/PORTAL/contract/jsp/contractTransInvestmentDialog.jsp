<!-- ****** JAVA CODE ***** //-->

<%@ page  errorPage="/common/jsp/error.jsp" autoFlush="true" %>
<%@ page import="java.util.*,
                 fission.beans.*,
                 edit.common.*,
                 edit.common.vo.*,
                 fission.utility.Util" %>

<%
    PageBean formBean = (PageBean) request.getAttribute("formBean");
    SessionBean contractInvestmentOverrides = (SessionBean) session.getAttribute("contractInvestmentOverrides");

    UIFilteredFundVO[] uiFilteredFundVOs = (UIFilteredFundVO[]) session.getAttribute("uiFilteredFundVOs");
    UIInvestmentOverrideVO[] uiInvestmentOverrideVOs = (UIInvestmentOverrideVO[]) session.getAttribute("uiInvestmentOverrideVOs");

    String companyStructureId = formBean.getValue("companyStructureId");
	String fundId = formBean.getValue("fundId");
    String segmentFK = formBean.getValue("segmentFK");
    String invAllocOvrdPK = formBean.getValue("investmentAllocationOverridePK");
    String investmentFK = formBean.getValue("investmentFK");
    String invAllocFK = Util.initString(formBean.getValue("investmentAllocationFK"), "");

	String allocationPercent = formBean.getValue("allocationPercent");
	String allocationDollars = formBean.getValue("allocationDollars");
	String allocationUnits = formBean.getValue("allocationUnits");
    String fromToIndicator = formBean.getValue("fromToIndicator");
    String hfStatus = formBean.getValue("hfStatus");
    String hfiaIndicator = formBean.getValue("hfiaIndicator");
    String holdingAccountIndicator = formBean.getValue("holdingAccountIndicator");
    String bucketFK = formBean.getValue("bucketFK");

    String filter             = formBean.getValue("filter");
    String filterMessage      = formBean.getValue("filterMessage");
    String segmentName        = formBean.getValue("segmentName");
    String filterAllocPct     = formBean.getValue("filterAllocPct");
    String filterAllocDollars = formBean.getValue("filterAllocDollars");
    String costBasis  	      = formBean.getValue("costBasis");
    String amountReceived     = formBean.getValue("amountReceived");
    String suspenseAmt        = formBean.getValue("amountUsed");
    String payeeIndStatus     = formBean.getValue("payeeIndStatus");
    String investmentIndStatus = formBean.getValue("investmentIndStatus");
    String deathStatusEnabled = formBean.getValue("deathStatusEnabled");
    String deathStatus        = formBean.getValue("deathStatus");
    String transactionType    = formBean.getValue("transactionType");
    String filterAllocRowId   = formBean.getValue("rowId");

    String rowToMatchBase = invAllocOvrdPK + "_" + fundId;
%>

<html>

<script src="/PORTAL/common/javascript/commonJavascriptFunctions.js"></script>

<script language="Javascript1.2">

	var f = null;

    var investmentAllocationFK = "<%= invAllocFK %>";

	function init()
    {
		f = document.investmentForm;

        formatCurrency();
	}

    function checkChangeability()
    {
        if (investmentAllocationFK != "")
        {
            alert("Cannot change values.  Please add a new override.");
        }
    }

    function closeInvestmentDialog()
    {
    	sendTransactionAction("ContractDetailTran", "closeAndSaveInvestmentAllocations", "filterAllocDialog");
        window.close();
    }

	function selectRow()
    {
		var tdElement = window.event.srcElement;
		var trElement = tdElement.parentElement;

		var fundKey = trElement.id;
        var ovrdKey = trElement.ovrdId;

        f.fundKey.value = ovrdKey + "_" + fundKey;

		sendTransactionAction("ContractDetailTran", "showInvestmentDetailSummary", "_self");
	}

	function selectAvailableRow()
    {
		var tdElement = window.event.srcElement;
		var trElement = tdElement.parentElement;

		var invAllocFK = trElement.invAllocFK;
        var investmentFK = trElement.investmentFK;

		f.investmentAllocationFK.value = invAllocFK;
        f.investmentFK.value = investmentFK;

		sendTransactionAction("ContractDetailTran", "selectAvailableInvestmentOverride", "_self");
	}

	function addNewInvestmentOverride()
    {
		clearForm();
	}

	function cancelInvestmentOverride()
    {
		var key           = f.key.value;
		var transactionId = f.transactionId.value;

		clearForm();

		f.key.value = key;
		f.transactionId.value = transactionId;

		sendTransactionAction("ContractDetailTran","cancelInvestmentOverride","_self")
	}

	function updateInvestmentOverride()
    {
		sendTransactionAction("ContractDetailTran", "updateInvestmentOverride", "_self");
	}

	function deleteInvestmentOverride()
    {
		sendTransactionAction("ContractDetailTran","deleteInvestmentOverride","_self")
	}

	function clearForm()
    {
		f.fundId.selectedIndex = 0;
		f.allocationPercent.value = "";
		f.allocationDollars.value = "";
		f.allocationUnits.value   = "";
        investmentAllocationFK = "";
        f.investmentAllocationFK.value = "";
        f.investmentAllocationFK.value = "";
	}

</script>

<head>
<title>EDITSOLUTIONS - Investment Overrides</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta http-equiv="Cache-Control" content="no-store">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Expires" conte="0">

<link href="/PORTAL/common/css/EDITSolutions.css" rel="stylesheet" type="text/css">

</head>

<body class="mainTheme" onLoad="init()">
<form name="investmentForm" method="post" action="/PORTAL/servlet/RequestManager">

  <table width="100%" height="10%">
    <tr>
      <td nowrap align="right" width="30%">Fund:</td>
      <td  width="30%">
	    <select name="fundId" onFocus="checkChangeability()">
	      <option> Please Select </option>
	      <%
            if (uiFilteredFundVOs != null) {

                for(int i = 0; i < uiFilteredFundVOs.length; i++) {

                    FilteredFundVO[] filteredFundVO = uiFilteredFundVOs[i].getFilteredFundVO();
                    String uiFilteredFundPK = filteredFundVO[0].getFilteredFundPK() + "";

                    FundVO[] fundVO = uiFilteredFundVOs[i].getFundVO();
                    String uiFundName = fundVO[0].getName();
                    String typeCode = fundVO[0].getTypeCodeCT();

                    if (!typeCode.equalsIgnoreCase("system"))
                    {
                        if (fundId.equals(uiFilteredFundPK)) {

                            out.println("<option selected name=\"id\" value=\"" + uiFilteredFundPK + "\">" + uiFundName + "</option>");
                        }

                        else {

                            out.println("<option name=\"id\" value=\"" + uiFilteredFundPK + "\">" + uiFundName + "</option>");
                        }
                    }
                }
            }
	      %>
	    </select>
      </td>
      <td align="right" width="30%" nowrap>Allocation Percent:</td>
      <td  width="30%">
        <input type="text" name="allocationPercent" size="9" maxlength="9" value="<%= allocationPercent %>" onFocus="checkChangeability()">
      </td>
    </tr>
    <tr>
      <td nowrap align="right">Allocation Dollars:</td>
      <td>
        <input type="text" name="allocationDollars" size="12" maxlength="12" value="<%= allocationDollars %>" onFocus="checkChangeability()" CURRENCY>
      </td>
      <td align="right" nowrap>Allocation Units:</td>
      <td>
        <input type="text" name="allocationUnits" size="15" maxlength="15" value="<%= allocationUnits %>" onFocus="checkChangeability()">
      </td>
    </tr>
	<tr>
	  <td nowrap align="left" colspan="4">
		<input type="button" value="   Add   " style="background-color:#DEDEDE" onClick="addNewInvestmentOverride()">
		<input type="button" value="   Save  " style="background-color:#DEDEDE" onClick="updateInvestmentOverride()">
		<input type="button" value="  Cancel " style="background-color:#DEDEDE" onClick="cancelInvestmentOverride()">
		<input type="button" value="  Delete " style="background-color:#DEDEDE" onClick="deleteInvestmentOverride()">
	  </td>
	</tr>
  </table>
  <table class="summary" width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr class="heading">
	  <th width="25%">Fund</th>
	  <th width="25%">Allocation Percent</th>
	  <th width="25%">Allocation Dollars</th>
	  <th width="25%">Allocation Units</th>
	</tr>
    <tr>
  </table>
  <span class="scrollableContent" style="border-style:solid; border-width:1;  position:relative; width:100%; height:30%; top:0; left:0; background-color:#BBBBBB">
    <table class="summary" id="selectedInvestmentSummary" width="100%" style="border-style:solid; border-width:0;" cellspacing="0" cellpadding="0">
      <%
        String rowToMatch = "";

        Iterator it = contractInvestmentOverrides.getPageBeans().values().iterator();

        while (it.hasNext())  {

            String className = "default";
            boolean selected = false;

            PageBean investmentBean = (PageBean) it.next();

            String iFundId	= investmentBean.getValue("fundId");
            String iInvAllocOvrdPK = investmentBean.getValue("investmentAllocationOverridePK");
            String fundName = "";
            if (uiFilteredFundVOs != null) {

                for (int f = 0; f < uiFilteredFundVOs.length; f++) {

                    FilteredFundVO[] filteredFundVOs = uiFilteredFundVOs[f].getFilteredFundVO();
                    String filteredFundPK = filteredFundVOs[0].getFilteredFundPK() + "";
                    if (filteredFundPK.equals(iFundId)) {

                        FundVO[] fundVOs = uiFilteredFundVOs[f].getFundVO();
                        fundName = fundVOs[0].getName();
                        break;
                    }
                }
            }
            String iAllocationPercent	= investmentBean.getValue("allocationPercent");
            String iAllocationDollars	= Util.initString(investmentBean.getValue("allocationDollars"), "0.00");
            String iAllocationUnits		= investmentBean.getValue("allocationUnits");

            // Store behind the scenes...
            String iStatus = investmentBean.getValue("status");

            if (!iStatus.equalsIgnoreCase("deleted"))
            {
                rowToMatch = iInvAllocOvrdPK + "_" + iFundId;

                if (rowToMatch.equals(rowToMatchBase)) {

                    className = "highlighted";
                    selected = true;
                }
                else {

                    className = "default";
                    selected = false;
                }
      %>
      <tr class="<%= className %>" isSelected="<%= selected %>" id="<%= iFundId %>" ovrdId="<%= iInvAllocOvrdPK %>"
          onMouseOver="highlightRow()" onMouseOut="unhighlightRow()" onClick="selectRow()">
        <td width="25%" nowrap>
          <%= fundName %>
        </td>
        <td width="25%" nowrap>
          <%= iAllocationPercent %>
        </td>
        <td width="25%" nowrap>
          <script>document.write(formatAsCurrency(<%= iAllocationDollars %>))</script>
        </td>
        <td width="25%" nowrap>
          <%= iAllocationUnits %>
        </td>
      </tr>
      <%
            } // end if
        } // end while
      %>
    </table>
  </span>
  <table width="100%" border="0" cellspacing="6" cellpadding="0">
    <tr>
	  <td align="right" nowrap>
	    <input type="button" name="close" value="Close" onClick ="closeInvestmentDialog()">
	  </td>
    </tr>
  </table>

<!-- ****** HIDDEN FIELDS ***** //-->
 <input type="hidden" name="transaction" value="">
 <input type="hidden" name="action"      value="">
 <input type="hidden" name="companyStructureId" value="<%= companyStructureId %>">
 <input type="hidden" name="segmentFK" value="<%= segmentFK %>">
 <input type="hidden" name="investmentAllocationOverridePK" value="<%= invAllocOvrdPK %>">
 <input type="hidden" name="investmentAllocationFK" value="<%= invAllocFK %>">
 <input type="hidden" name="investmentFK" value="<%= investmentFK %>">

 <input type="hidden" name="filter" value="<%= filter %>">
 <input type="hidden" name="filterMessage" value="<%= filterMessage %>">
 <input type="hidden" name="segmentName" value="<%= segmentName %>">
 <input type="hidden" name="filterAllocPct" value="<%= filterAllocPct %>">
 <input type="hidden" name="filterAllocDollars" value="<%= filterAllocDollars %>">
 <input type="hidden" name="costBasis" value="<%= costBasis %>">
 <input type="hidden" name="amountReceived" value="<%= amountReceived %>">
 <input type="hidden" name="amountUsed"  value="<%= suspenseAmt %>">
 <input type="hidden" name="transactionType" value="<%= transactionType %>">
 <input type="hidden" name="payeeIndStatus" value="<%= payeeIndStatus %>">
 <input type="hidden" name="investmentIndStatus" value="<%= investmentIndStatus %>">
 <input type="hidden" name="deathStatusEnabled" value="<%= deathStatusEnabled %>">
 <input type="hidden" name="deathStatus" value="<%= deathStatus %>">
 <input type="hidden" name="transactionType" value="<%= transactionType %>">
 <input type="hidden" name="fromToIndicator" value="<%= fromToIndicator %>">
 <input type="hidden" name="hfStatus" value="<%= hfStatus %>">
 <input type="hidden" name="hfiaIndicator" value="<%= hfiaIndicator %>">
 <input type="hidden" name="holdingAccountIndicator" value="<%= holdingAccountIndicator %>">
 <input type="hidden" name="rowId" value="<%= filterAllocRowId %>">
 <input type="hidden" name="bucketFK" value="<%= bucketFK %>">
 <input type="hidden" name="fundKey" value="">

</form>
</body>
</html>
