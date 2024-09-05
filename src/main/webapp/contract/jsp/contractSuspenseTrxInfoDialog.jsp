<%@ page  errorPage="/common/jsp/error.jsp" autoFlush="true" %>
<%@ page import="java.util.*,
                 edit.common.*,
                 edit.common.vo.*,
                 fission.beans.*,
                 edit.portal.exceptions.*,
                 edit.common.exceptions.VOEditException,
                 fission.utility.Util,
                 java.text.DecimalFormat,
                 event.*" %>

<jsp:useBean id="contractMainSessionBean"
    class="fission.beans.SessionBean" scope="session"/>

<%
    String selectedSuspensePK = Util.initString((String) request.getAttribute("selectedSuspensePK"), "0");
    SuspenseVO suspenseVO = null;
    Suspense suspense = (Suspense)request.getAttribute("suspense");
    if (suspense != null)
    {
        suspenseVO = (SuspenseVO)suspense.getVO();
    }

    String transactionType = "";
    String fees           = "";
    String fedWithholding = "";
    String stateWithholding = "";
    String countyWithholding = "";
    String cityWithholding = "";
    String taxableBenefit = "";
    String originalContractNumber = "";
    String originalAmount = "";
    String originalMemoCode = "";

//    SuspenseVO[] suspenseVOs = (SuspenseVO[]) session.getAttribute("suspenseVOs");
    if (suspenseVO != null)
    {
//        for (int i = 0; i < suspenseVOs.length; i++)
//        {
//            if (suspenseVOs[i].getSuspensePK() == Long.parseLong(selectedSuspensePK))
//            {
                originalContractNumber = Util.initString(suspenseVO.getOriginalContractNumber(), "");
                originalAmount = suspenseVO.getOriginalAmount().toString();
                originalMemoCode = Util.initString(suspenseVO.getOriginalMemoCode(), "");

                if (!suspense.getInSuspenses().isEmpty())
                {
                    Set inSuspenses = suspense.getInSuspenses();
                    Iterator it = inSuspenses.iterator();
                    while (it.hasNext())
                    {
                        InSuspense inSuspense = (InSuspense) it.next();
                        EDITTrxHistory editTrxHistory = inSuspense.getEDITTrxHistory();
                        EDITTrx editTrx = editTrxHistory.getEDITTrx();
                        FinancialHistory financialHistory = editTrxHistory.getFinancialHistory();
                        WithholdingHistory withholdingHistory = editTrxHistory.getWithholdingHistory();
                        transactionType = editTrx.getTransactionTypeCT();
                        if (financialHistory != null)
                        {
                            taxableBenefit = financialHistory.getTaxableBenefit().toString();
                        }
                        if (withholdingHistory != null)
                        {
                            fedWithholding = withholdingHistory.getFederalWithholdingAmount().toString();
                            stateWithholding = withholdingHistory.getStateWithholdingAmount().toString();
                            countyWithholding = withholdingHistory.getCountyWithholdingAmount().toString();
                            cityWithholding = withholdingHistory.getCityWithholdingAmount().toString();
                        }
                    }
                }
//            }
//        }
    }
%>

<html>

<script src="/PORTAL/common/javascript/commonJavascriptFunctions.js"></script>

<script language="Javascript1.2">

	var dialog = null;

	var f = null;

	function init()
    {
		f = document.suspenseForm;

        formatCurrency();
	}

	function closeSuspenseTrxInfo()
    {
		sendTransactionAction("ContractDetailTran", "closeSuspenseTransactionInfo", "suspenseDialog");
        closeWindow();
	}

</script>

<head>
<title>EDITSOLUTIONS - Suspense Trx Information</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta http-equiv="Cache-Control" content="no-store">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Expires" content="0">

<link href="/PORTAL/common/css/EDITSolutions.css" rel="stylesheet" type="text/css">
</head>

<body class="mainTheme" onLoad="init();">
<form name="suspenseForm" method="post" action="">
  <table width="100%" height="50%" border="0" cellspacing="5" cellpadding="0">
    <tr>
      <td nowrap align="right">Transaction:&nbsp;</td>
      <td nowrap align="left">
        <input type="text" name="transactionType" disabled size="15" maxlength="15" value="<%= transactionType %>">
      </td>
	  <td align="left" nowrap rowspan="4">
        <span style="border-style:solid; border-width:1; position:relative; width:100%; height:100%; top:0; left:0; z-index:0">
          <table>
            <tr>
              <td nowrap align="right">Original Contract Number:&nbsp;</td>
              <td nowrap align="left">
                <input type="text" name="originalContractNumber" size="15" maxlength="15" disabled value="<%= originalContractNumber %>">
              </td>
            </tr>
            <tr>
              <td nowrap align="right">Original Amount:&nbsp;</td>
              <td nowrap align="left">
                <input type="text" name="originalAmount" size="15" maxlength="15" disabled value="<%= originalAmount %>" CURRENCY>
              </td>
            </tr>
            <tr>
              <td nowrap align="right">Original Memo Code:&nbsp;</td>
              <td nowrap align="left">
                <input type="text" name="originalMemoCode" size="15" maxlength="15" disabled value="<%= originalMemoCode %>">
              </td>
            </tr>
          </table>
        </span>
	  </td>
    </tr>
    <tr>
      <td nowrap align="right">Fees:&nbsp;</td>
      <td nowrap align="left" colspan="2">
        <input type="text" name="fees" size="15" disabled maxlength="15" value="<%= fees %>" CURRENCY>
      </td>
    </tr>
    <tr>
      <td nowrap align="right">Federal W/H:&nbsp;
      <td nowrap align="left" colspan="2">
        <input type="text" name="federalWithholding" disabled size="15" maxlength="15" value="<%= fedWithholding %>" CURRENCY>
      </td>
    </tr>
    <tr>
      <td nowrap align="right">State W/H:&nbsp;
      <td nowrap align="left" colspan="2">
        <input type="text" name="stateWithholding" disabled size="15" maxlength="15" value="<%= stateWithholding %>" CURRENCY>
      </td>
    </tr>
    <tr>
      <td nowrap align="right">County W/H:&nbsp;</td>
      <td nowrap align="left" colspan="2">
        <input type="text" name="countyWithholding" disabled size="15" maxlength="15" value="<%= countyWithholding %>" CURRENCY>
      </td>
    </tr>
    <tr>
      <td nowrap align="right">City W/H:&nbsp;</td>
      <td nowrap align="left" colspan="2">
        <input type="text" name="cityWithholding" disabled size="15" maxlength="15" value="<%= cityWithholding %>" CURRENCY>
      </td>
    </tr>
    <tr>
      <td nowrap align="right">Taxable Benefit:&nbsp;</td>
      <td nowrap align="left" colspan="2">
        <input type="text" name="taxableBenefit" disabled size="15" maxlength="15" value="<%= taxableBenefit %>" CURRENCY>
      </td>
    </tr>
    <tr>
      <td align="left" nowrap colspan="3">
        &nbsp;
      </td>
    </tr>
    <tr>
      <td align="right" nowrap colspan="3">
        <input type="button" name="close" value="Close" onClick ="closeSuspenseTrxInfo()">
      </td>
    </tr>
  </table>

<!-- ****** HIDDEN FIELDS ***** //-->
 <input type="hidden" name="transaction"  value="">
 <input type="hidden" name="action"       value="">

 <input type="hidden" name="key"   value="">
 <input type="hidden" name="suspensePK" value="<%= selectedSuspensePK %>">
 <input type="hidden" name="filtered" value="">

</form>
</body>
</html>
