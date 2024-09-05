<%@ page import="edit.common.vo.FeeVO,
                 edit.common.vo.FeeDescriptionVO,
                 edit.common.EDITBigDecimal,
                 java.util.Map,
                 java.util.HashMap,
                 edit.common.vo.ChargeCodeVO,
                 fission.utility.*"%>
<!--
 * User: sprasad
 * Date: Feb 2, 2005
 * Time: 5:11:20 PM
 *
 * (c) 2000-2005 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 -->
 <%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%-- ****************************** BEGIN Java Code ****************************** --%>
<%
    String responseMessage = (String) request.getAttribute("responseMessage");
    String errorMessage = Util.initString((String) request.getAttribute("errorMessage"), "");
    String dfcashTrxEffectiveDate = (String) request.getAttribute("dfcashTrxEffectiveDate");
    String selectedSuspensePK = (String) request.getAttribute("selectedSuspensePK");
    FeeVO feeVO = (FeeVO) request.getAttribute("feeVO");

    String dfcashTrxEffectiveMonth = "";
    String dfcashTrxEffectiveDay = "";
    String dfcashTrxEffectiveYear = "";

    if (dfcashTrxEffectiveDate != null)
    {
        String[] effectiveDateTokens = DateTimeUtil.initDate(dfcashTrxEffectiveDate);
        dfcashTrxEffectiveMonth = effectiveDateTokens[1];
        dfcashTrxEffectiveDay = effectiveDateTokens[2];
        dfcashTrxEffectiveYear = effectiveDateTokens[0];
    }

    long selectedFeeTrxPK = 0;
    long selectedFeeDescriptionFK = 0;
    EDITBigDecimal selectedTrxAmount = null;
    EDITBigDecimal selectedAccumTrxAmtReceived = null;
    EDITBigDecimal selectedTrxAmountDue= new EDITBigDecimal("0.00");
    long selectedChargeCodeFK = 0;

    if (feeVO != null)
    {
        selectedFeeTrxPK = feeVO.getFeePK();
        selectedFeeDescriptionFK = feeVO.getFeeDescriptionFK();
        selectedTrxAmount = new EDITBigDecimal(feeVO.getTrxAmount());
        selectedAccumTrxAmtReceived = new EDITBigDecimal(feeVO.getAccumulatedTrxAmtReceived());
        selectedTrxAmountDue = selectedTrxAmount.subtractEditBigDecimal(selectedAccumTrxAmtReceived);
        selectedChargeCodeFK = feeVO.getChargeCodeFK();
    }

    FeeVO[] feeVOs = (FeeVO[]) request.getAttribute("hedgeFeeTrxVOs");
    String selectedFilteredFundFK = (String) request.getAttribute("selectedFilteredFundFK");

    Map feeAmountsByFeePK = (HashMap) session.getAttribute("feeAmountsMapByFeePK");
    Boolean isFeeAmountsEntered = (Boolean)session.getAttribute("isFeeAmountsEntered");
    boolean isFeeAmountsEnteredValue = false;
    if (isFeeAmountsEntered != null)
    {
        isFeeAmountsEnteredValue = isFeeAmountsEntered.booleanValue();
    }
%>
<%-- ****************************** End Java Code ****************************** --%>

<html>
<head>
<title>Division Level Fees</title>
<link href="/PORTAL/common/css/EDITSolutions.css" rel="stylesheet" type="text/css">

<%-- ****************************** BEGIN JavaScript ****************************** --%>
<script src="/PORTAL/common/javascript/commonJavascriptFunctions.js"></script>

<script>

    var f = null;

    var responseMessage = "<%= responseMessage %>";
    var errorMessage = "<%= errorMessage %>";
    var isFeeAmountsEnteredValue = "<%= isFeeAmountsEnteredValue %>";

    /**
     * Called after the body of the page is loaded.
     */
    function init()
    {
        f = document.theForm;

        if (errorMessage != "")
        {
            alert(errorMessage);
        }
        
        checkForResponseMessage();
    }

    /**
     * Summary method to show how one might show the detail of a selected row in the summary table.
     */
    function selectFeeTrx()
    {
        var selectedRowId = getSelectedRowId("feeSummary");

        if (valueIsEmpty(selectedRowId))
        {
            alert("Value Is Empty");
            return false;
        }
        else
        {
            f.selectedFeeTrxPK.value = selectedRowId;
            sendTransactionAction("EventAdminTran", "showDivisionLevelFeesDialog", "_self");
        }
    }

    function saveFeeTrx()
    {
        if (f.selectedFeeTrxPK.value == "0")
        {
            alert("Pl. Select the Fee");
            return false;
        }
        else
        {
            sendTransactionAction("EventAdminTran", "saveFeeAmountReceived", "_self");
        }
    }

    function closeFeeDialog()
    {
        sendTransactionAction("EventAdminTran", "clearFeeSessionParams", "main");
        window.close();
    }

    function saveFeeTrxAmounts()
    {
        if (valueIsEmpty(f.effectiveMonth.value) ||
            valueIsEmpty(f.effectiveDay.value) ||
            valueIsEmpty(f.effectiveYear.value))
        {
            alert("Please Enter Effective Date");
            return false;
        }
        else if (isFeeAmountsEnteredValue == "false")
        {
            alert("Please Enter Fee Amounts");
            return false;
        }
        else
        {
            sendTransactionAction("EventAdminTran", "saveFeeTrxAmounts", "_self");
        }
    }
</script>
<%-- ****************************** END JavaScript ****************************** --%>

</head>
<body class="mainTheme" onLoad="init()">
<form name="theForm" method="post" action="/PORTAL/servlet/RequestManager">

<%-- ****************************** BEGIN Form Data ****************************** --%>
<table class="formData" width="100%" border="0" cellspacing="0" cellpadding="5">
<%--    BEGIN Form Content --%>
    <tr>
        <td align="right" nowrap>
            Effective Date:&nbsp;
        </td>
        <td align="left" nowrap>
            <input name="effectiveMonth" type="text" size="2" maxlength="2" value="<%= dfcashTrxEffectiveMonth %>"> /
            <input name="effectiveDay" type="text" size="2" maxlength="2" value="<%= dfcashTrxEffectiveDay %>"> /
            <input name="effectiveYear" type="text" size="4" maxlength="4" value="<%= dfcashTrxEffectiveYear %>">
        </td>
        <td align="right" nowrap>
            Amount:&nbsp;
        </td>
        <td align="left" nowrap>
            <input type="text" name="amount" size="15" maxlength="20" value="<%= selectedTrxAmountDue %>">
        </td>
    </tr>
<%--    END Form Content --%>

    <tr class="filler">
        <td colspan="4">
            &nbsp; <!--Filler Row -->
        </td>
    </tr>
</table>
<%-- ****************************** END Form Data ****************************** --%>

<br>

<%-- ****************************** BEGIN Summary Area ****************************** --%>
<%--Buttons--%>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
        <td align="left" width="33%">
            <input type="button" value=" Save " onClick="saveFeeTrx()">
        </td>
        <td width="33%">
            &nbsp;
        </td>
        <td align="right" width="33%">
            &nbsp;
        </td>
    </tr>
</table>

<%-- Column Headings --%>
<table class="summary" width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr class="heading">
        <td width="15%" nowrap>
            Trx Type
        </td>
        <td width="15%" nowrap>
            Eff Date
        </td>
        <td width="15%" nowrap>
            Status
        </td>
        <td width="15%" nowrap>
            Fee Type
        </td>
        <td width="15%" nowrap>
            Amt Due
        </td>
        <td width="15%" nowrap>
            Amt Paid
        </td>
        <td width="10%" nowrap>
            Charge Code
        </td>
    </tr>
</table>

<%--Summary--%>
<span class="scrollableContent" style="border-style:solid; border-width:1;  position:relative; width:100%; height:60%; top:0; left:0;">
    <table id="feeSummary" class="summary" width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
        <%
          String trxType = "";
          String effectiveDate = "";
          String status = "";
          EDITBigDecimal trxAmount = null;
          EDITBigDecimal accumTrxAmtReceived = null;
          EDITBigDecimal trxAmountDue= null;
          long feePK = 0;
          long feeDescriptionFK = 0;
          String feeTypeCT = "";
          String trxAmountPaid = null;
          long chargeCodeFK = 0;
          String chargeCode = "";

          FeeDescriptionVO feeDescriptionVO = null;
          engine.business.Lookup engineLookup = new engine.component.LookupComponent();
          ChargeCodeVO chargeCodeVO = null;
          engine.business.Calculator calculator = new engine.component.CalculatorComponent();

          String className = "default";
          boolean isSelected = false;

          if (feeVOs != null)
          {
              for (int i = 0; i < feeVOs.length; i++)
              {
                  feePK = feeVOs[i].getFeePK();
                  trxType = feeVOs[i].getTransactionTypeCT();
                  effectiveDate = feeVOs[i].getEffectiveDate();
                  if (effectiveDate != null)
                  {
                      effectiveDate = DateTimeUtil.formatYYYYMMDDToMMDDYYYY(effectiveDate);
                  }
                  status = feeVOs[i].getStatusCT();
                  feeDescriptionFK = feeVOs[i].getFeeDescriptionFK();
                  feeDescriptionVO = engineLookup.findFeeDescriptionBy_FeeDescriptionPK(feeDescriptionFK);
                  if (feeDescriptionVO != null)
                  {
                      feeTypeCT = feeDescriptionVO.getFeeTypeCT();
                  }
                  trxAmount = new EDITBigDecimal(feeVOs[i].getTrxAmount());
                  accumTrxAmtReceived = new EDITBigDecimal(feeVOs[i].getAccumulatedTrxAmtReceived());
                  trxAmountDue = trxAmount.subtractEditBigDecimal(accumTrxAmtReceived);
                  chargeCodeFK = feeVOs[i].getChargeCodeFK();
                  if (chargeCodeFK != 0)
                  {
                      chargeCodeVO = calculator.findChargeCodeBy_ChargeCodePK(chargeCodeFK);
                      if (chargeCodeVO != null)
                      {
                          chargeCode = chargeCodeVO.getChargeCode();
                      }
                  }

                  if (feeAmountsByFeePK.containsKey(feePK+""))
                  {
                      trxAmountPaid = ((EDITBigDecimal) feeAmountsByFeePK.get(feePK+"")).toString();
                  }
                  trxAmountPaid = Util.initString(trxAmountPaid, "0.00");

                  if (feePK == selectedFeeTrxPK)
                  {
                      className = "highlighted";
                      isSelected = true;
                  }
                  else
                  {
                      className = "default";
                      isSelected = false;
                  }
        %>
        <tr class="<%= className %>" isSelected="<%= isSelected %>" id="<%= feePK %>" feeTypeCT="<%= feeTypeCT %>"
            onMouseOver="highlightRow()" onMouseOut="unhighlightRow()" onClick="selectRow(false);selectFeeTrx()">
          <td width="15%" align="left">
            <%= trxType %>
          </td>
          <td width="15%" align="left">
            <%= effectiveDate %>
          </td>
          <td width="15%" align="left">
            <%= status %>
          </td>
          <td width="15%" align="left">
            <%= feeTypeCT %>
          </td>
          <td width="15%" align="left">
            <script>document.write(formatAsCurrency(<%= trxAmountDue %>))</script>
          </td>
          <td width="15%" align="left">
            <script>document.write(formatAsCurrency(<%= trxAmountPaid %>))</script>
          </td>
          <td width="15%" align="left">
            <%= chargeCode %>
          </td>
        </tr>
        <%
                  trxAmountPaid = null;
                  chargeCode = "";
              }
          }
        %>
        <tr class="filler">
            <td colspan="6">
                &nbsp;
            </td>
        </tr>
    </table>
</span>

<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
        <td align="left" width="33%">
            &nbsp;
        </td>
        <td width="33%">
            &nbsp;
        </td>
        <td align="right" width="33%">
            <input type="button" value=" Save " onClick="saveFeeTrxAmounts()">
            <input type="button" value=" Close " onClick="closeFeeDialog()">
        </td>
    </tr>
</table>
<%-- ****************************** END Summary Area ****************************** --%>

<%-- ****************************** BEGIN Hidden Variables ****************************** --%>
<input type="hidden" name="transaction">
<input type="hidden" name="action">
<input type="hidden" name="selectedFeeTrxPK" value="<%= selectedFeeTrxPK %>">
<input type="hidden" name="selectedFeeDescriptionFK" value="<%= selectedFeeDescriptionFK %>">
<input type="hidden" name="selectedFilteredFundFK" value="<%= selectedFilteredFundFK %>">
<input type="hidden" name="selectedSuspensePK" value="<%= selectedSuspensePK %>">
<input type="hidden" name="dfcashTrxEffectiveDate" value="<%= dfcashTrxEffectiveDate %>">
<input type="hidden" name="selectedChargeCodeFK" value="<%= selectedChargeCodeFK %>">
<%-- ****************************** END Hidden Variables ****************************** --%>

</form>
</body>
</html>