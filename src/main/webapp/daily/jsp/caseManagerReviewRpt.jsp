<!-- ************* JSP Code ************* -->
<%@ page errorPage="/common/jsp/error.jsp" autoFlush="true" %>

<%@ page import="edit.common.vo.*,
                 edit.common.EDITBigDecimal,
                 engine.Fee,
                 java.util.*,
                 edit.common.EDITDate,
                 engine.FilteredFund,
                 engine.ChargeCode,
                 event.EDITTrx,
                 fission.utility.*"%>

<%
    String dateType = (String) request.getAttribute("dateType");
    String startDate = (String) request.getAttribute("startDate");
    String endDate = (String) request.getAttribute("endDate");

    Hashtable caseManagerReviewInfo = (Hashtable) request.getAttribute("caseManagerReviewInfo");

    List funds = (ArrayList) caseManagerReviewInfo.get("funds");

    Hashtable cashTransfers = (Hashtable) caseManagerReviewInfo.get("cashTransfers");

    Hashtable accrualTransfers = (Hashtable) caseManagerReviewInfo.get("accrualTransfers");

    String[] feeTypes = new String[] {"Transfer", "Premium", "AdvanceTransfer", "Surrender",
                                      "Death", "TransferMortReserve", "AdminFee", "COI", "MEFee",
                                      "ManagementFee", "AdvisoryFee", "PerformanceFee", "SVAFee", "GainLoss"};

    final String GAIN_LOSS_FEE = "GainLoss";

    Hashtable erroredTrxHT = new Hashtable();
%>

<%!
    private long[] getChargeCodeFKsToUse(long chargeCodeFK,
                                         long filteredFundPK)
    {
        long[] chargeCodeFKsToUse = null;

        if (chargeCodeFK == 0)
        {
            chargeCodeFKsToUse = ChargeCode.getAllChargeCodePKsIncludingZero(filteredFundPK);
        }
        else
        {
            chargeCodeFKsToUse = new long[1];
            chargeCodeFKsToUse[0] = chargeCodeFK;
        }

        return chargeCodeFKsToUse;
    }

    private String getValuationDate(InvestmentHistoryVO[] investmentHistoryVOs)
    {
        EDITDate latestValuationDate = null;

        for (int i = 0; i < investmentHistoryVOs.length; i++)
        {
            if (latestValuationDate == null)
            {
                latestValuationDate = new EDITDate(investmentHistoryVOs[i].getValuationDate());
            }
            else
            {
                if (new EDITDate(investmentHistoryVOs[i].getValuationDate()).after(latestValuationDate))
                {
                    latestValuationDate = new EDITDate(investmentHistoryVOs[i].getValuationDate());
                }
            }
        }

        return latestValuationDate.getFormattedDate();
    }
%>

<html>
<head>
<title>Case Manager Review Detail Level</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link href="/PORTAL/common/css/EDITSolutions.css" rel="stylesheet" type="text/css">

<!-- ************* Java Script Code ************* -->

<script src="/PORTAL/common/javascript/commonJavascriptFunctions.js"></script>

<script>

<!-- Formats a fee as currency, or &nsbp; if the fee is zilch. -->
function formatFee(theFee)
{
    var formattedFee = null;

    if (theFee == "0")
    {
        formattedFee = "&nbsp;";
    }
    else
    {
        formattedFee = formatAsCurrency(theFee);
    }

    return formattedFee;
}

</script>

</head>

<!-- ************* HTML Code ************* -->
<body>

  <h4 align="center">
    CASE MANAGER Review Detail Level - Between&nbsp;<%= startDate %>&nbsp;and&nbsp;<%= endDate %>&nbsp;&nbsp;(<%= dateType %>&nbsp;Date)
  </h4>

  <%
      engine.business.Lookup engineLookup = new engine.component.LookupComponent();

      String trxFromToInd = null;
      String feeFromToInd = null;
      String displayAcctngPeriod = null;
      String displayProcessDate = null;
      String displayValuationDate = null;

      EDITBigDecimal total = new EDITBigDecimal("0", 7);
      EDITBigDecimal allTransfersTotal = new EDITBigDecimal();
      EDITBigDecimal cashTransferTotal = new EDITBigDecimal();
      EDITBigDecimal accrualTransferTotal = new EDITBigDecimal();
      EDITBigDecimal currentBalance = new EDITBigDecimal();
      EDITBigDecimal currentFeeBalance = new EDITBigDecimal();

      EDITBigDecimal[] allCashFees = new EDITBigDecimal[feeTypes.length];
      EDITBigDecimal[] allAccrualFees = new EDITBigDecimal[feeTypes.length];
      EDITBigDecimal[] fees = new EDITBigDecimal[feeTypes.length];

      boolean tableHeaderCreated = false;
      boolean recordsOutputted = false;

      for (int i = 0; i < funds.size(); i++)
      {
          recordsOutputted = false;
          String fundKey = (String) funds.get(i);
          String fundNumber = fundKey.substring(0, fundKey.indexOf("_"));

          int indexOf__ = fundKey.indexOf("__");
          String filteredFundFK = "";
          long chargeCodeFK = 0;
          if (indexOf__ > -1)
          {
              filteredFundFK = fundKey.substring(fundKey.indexOf("_") + 1, fundKey.indexOf("__"));
              chargeCodeFK = Long.parseLong(fundKey.substring(fundKey.indexOf("__") + 2));
          }
          else
          {
              filteredFundFK = fundKey.substring(fundKey.indexOf("_") + 1);
          }

          FilteredFund filteredFund = FilteredFund.findByPK(new Long(filteredFundFK));
          String pricingDirection = filteredFund.getPricingDirection();

          long[] chargeCodeFKsToUse = getChargeCodeFKsToUse(chargeCodeFK, Long.parseLong(filteredFundFK));

          Fee[] fee = (Fee[]) cashTransfers.get(fundKey);
          EDITTrxHistoryVO[] editTrxHistoryVOs = (EDITTrxHistoryVO[]) accrualTransfers.get(fundKey);

          for (int j= 0; j < fees.length; j++)
          {
              fees[j] = new EDITBigDecimal();
          }

          for (int j= 0; j < allCashFees.length; j++)
          {
              allCashFees[j] = new EDITBigDecimal();
          }

          for (int j= 0; j < allAccrualFees.length; j++)
          {
              allAccrualFees[j] = new EDITBigDecimal();
          }

          cashTransferTotal = new EDITBigDecimal();
          accrualTransferTotal = new EDITBigDecimal();

          String prevDateProcessValue = "";
          String prevEffDateValue = "";
          String prevValuationDateValue = "";
          String prevAccountingPeriod = "";
          String feePrevDateProcessValue = "";
          String feePrevEffDateValue = "";
          String feePrevValuationDateValue = "";

          boolean trxTypeMatchFound = false;

  %>
<div width="100%" align="left">
  <table width="100%" cellpadding="2" cellspacing="4">
    <tr>
      <td align="left" colspan="18">
        Policy Admin Fund Code:&nbsp;<%= fundNumber %>
      </td>
    </tr>
    <tr>
      <td align="left" colspan="18"><hr size="2" color="black" noshade></hr></td>
    </tr>
  </table>
</div>
  <%

          if (fee != null || editTrxHistoryVOs != null)
          {
              if (fee != null)
              {
                  tableHeaderCreated = false;

                  for (int j = 0; j < fee.length; j++)
                  {
                      FeeVO feeVO = (FeeVO) fee[j].getVO();

                      if (feeVO.getReleaseDate() != null)
                      {
                          EDITDate releaseDate = new EDITDate(feeVO.getReleaseDate());
                          displayAcctngPeriod = releaseDate.getMonthName() + "-" + releaseDate.getFormattedYear();
                      }
                      else
                      {
                          displayAcctngPeriod = "";
                      }

                      feeFromToInd = feeVO.getToFromInd();

                      if (feeVO.getTransactionTypeCT().equalsIgnoreCase("DFCASH"))
                      {
                          if (!tableHeaderCreated)
                          {
                              tableHeaderCreated = true;
                              recordsOutputted = true;
  %>
<div width="100%" align="left">
  <table width="100%" cellpadding="2" cellspacing="4">
    <tr>
      <td align="left" colspan="18">
        <b>Cash Transfers To/(From) Fund</b>
      </td>
    </tr>
    <tr>
      <td align="left" colspan="18"><hr size="1" color="black" noshade></hr></td>
    </tr>
  </table>
  <table border="1" borderstyle="solid" width="100%" cellpadding="0" cellspacing="1">
    <tr>
      <td align="center" width="5%"><font size="1">Accounting Period</font></td>
      <td align="center" width="5%"><font size="1">Date Process</font></td>
      <td align="center" width="5%"><font size="1">Cash Valuation Date</font></td>
      <td align="center" width="5%"><font size="1">Total</font></td>
      <td align="center" width="5%"><font size="1">Reallocation</font></td>
      <td align="center" width="5%"><font size="1">Net Premium</font></td>
      <td align="center" width="5%"><font size="1">Advance Transfers</font></td>
      <td align="center" width="5%"><font size="1">Surrender</font></td>
      <td align="center" width="5%"><font size="1">RRD</font></td>
      <td align="center" width="5%"><font size="1">Transfer Mortality Reserve</font></td>
      <td align="center" width="5%"><font size="1">Admin Fee</font></td>
      <td align="center" width="5%"><font size="1">COI</font></td>
      <td align="center" width="5%"><font size="1">M&E Fee</font></td>
      <td align="center" width="5%"><font size="1">Management Fee</font></td>
      <td align="center" width="5%"><font size="1">Advisory Fee</font></td>
      <td align="center" width="5%"><font size="1">Performance Fee</font></td>
      <td align="center" width="5%"><font size="1">SVA Fee</font></td>
      <td align="center" width="5%"><font size="1">(Gain)/Loss</font></td>
    </tr>
  <%
                          }

                          String currDateProcessValue = feeVO.getProcessDateTime().substring(0, 10);
                          String currEffDateValue = feeVO.getEffectiveDate();

                          if (prevDateProcessValue.equals(""))
                          {
                              prevDateProcessValue = currDateProcessValue;
                          }

                          if (prevEffDateValue.equals(""))
                          {
                              prevEffDateValue = currEffDateValue;
                          }

                          UnitValuesVO[] unitValuesVO = engineLookup.getUnitValuesByFilteredFundIdDateChargeCode(Long.parseLong(filteredFundFK),
                                                                                                                 feeVO.getEffectiveDate(),
                                                                                                                 pricingDirection,
                                                                                                                 chargeCodeFKsToUse[0]);
                          String currValuationDateValue = "";
                          if (unitValuesVO != null && unitValuesVO.length > 0)
                          {
                              currValuationDateValue = unitValuesVO[0].getEffectiveDate();
                          }

                          if (prevValuationDateValue.equals(""))
                          {
                              prevValuationDateValue = currValuationDateValue;
                          }

                          if (!currDateProcessValue.equals(prevDateProcessValue) ||
                              !currEffDateValue.equals(prevEffDateValue))
                          {
                              EDITDate displayDate = null;

                              if (dateType.equalsIgnoreCase("Effective"))
                              {
                                  displayDate = new EDITDate(prevEffDateValue);
                              }
                              else
                              {
                                  displayDate = new EDITDate(prevDateProcessValue);
                              }

                              displayProcessDate = DateTimeUtil.formatEDITDateAsMMDDYYYY(displayDate).substring(0, 7); // only want 2 digit year

                              displayDate = new EDITDate(prevValuationDateValue);
                              displayValuationDate = DateTimeUtil.formatEDITDateAsMMDDYYYY(displayDate).substring(0, 7); // only want 2 digit year
  %>
    <tr>
      <td align="right" width="6%"><font size="1"><%= displayAcctngPeriod %></font></td>
      <td align="right" width="5%"><font size="1"><%= displayProcessDate %></font></td>
      <td align="right" width="5%"><font size="1"><%= displayValuationDate %></font></td>
  <%
      if (total.isLT(EDITBigDecimal.EDIT_DECIMAL_ZILCH_STR))
      {
          total = total.multiplyEditBigDecimal("-1");
  %>
      <td align="right" width="5%"><font size="1">(<script>document.write(formatFee(<%= total %>))</script>)</font></td>
  <%
      }
      else
      {
  %>
      <td align="right" width="5%"><font size="1"><script>document.write(formatFee(<%= total %>))</script></font></td>
  <%
      }

      for (int k = 0; k < fees.length; k++)
      {
          if (fees[k].isLT(EDITBigDecimal.EDIT_DECIMAL_ZILCH_STR))
          {
              fees[k] = fees[k].multiplyEditBigDecimal("-1");
  %>
      <td align="right" width="5%"><font size="1">(<script>document.write(formatFee(<%= fees[k] %>))</script>)</font></td>
  <%
          }
          else
          {
  %>
      <td align="right" width="5%"><font size="1"><script>document.write(formatFee(<%= fees[k] %>))</script></font></td>
  <%
          }
      }
  %>
    </tr>
  <%
                              prevDateProcessValue = currDateProcessValue;
                              prevEffDateValue = currEffDateValue;
                              prevValuationDateValue = currValuationDateValue;

                              for (int k = 0; k < fees.length; k++)
                              {
                                  fees[k] = new EDITBigDecimal();
                              }

                              total = new EDITBigDecimal("0", 7);
                          }

                          FeeDescriptionVO feeDescriptionVO = fee[j].getFeeDescription();

                          if (feeDescriptionVO != null)
                          {
                              for (int k = 0; k < feeTypes.length; k++)
                              {
                                  if (feeDescriptionVO.getFeeTypeCT().equalsIgnoreCase(feeTypes[k]))
                                  {
                                      if ((feeFromToInd != null && feeFromToInd.equalsIgnoreCase("F")) ||
                                          (feeDescriptionVO.getFeeTypeCT().equalsIgnoreCase(GAIN_LOSS_FEE) &&
                                           new EDITBigDecimal(feeVO.getTrxAmount()).isGT("0")))
                                      {
                                          fees[k] = fees[k].subtractEditBigDecimal(feeVO.getTrxAmount());
                                          allCashFees[k] = allCashFees[k].subtractEditBigDecimal(feeVO.getTrxAmount());
    
                                          total = total.subtractEditBigDecimal(feeVO.getTrxAmount());
                                          allTransfersTotal = allTransfersTotal.subtractEditBigDecimal(feeVO.getTrxAmount());
                                          cashTransferTotal = cashTransferTotal.subtractEditBigDecimal(feeVO.getTrxAmount());
                                      }
                                      else
                                      {
                                          fees[k] = fees[k].addEditBigDecimal(feeVO.getTrxAmount());
                                          allCashFees[k] = allCashFees[k].addEditBigDecimal(feeVO.getTrxAmount());
    
                                          total = total.addEditBigDecimal(feeVO.getTrxAmount());
                                          allTransfersTotal = allTransfersTotal.addEditBigDecimal(feeVO.getTrxAmount());
                                          cashTransferTotal = cashTransferTotal.addEditBigDecimal(feeVO.getTrxAmount());
                                      }
                                  }
                              }
                          }
                      }
                  }

                  if (!prevDateProcessValue.equals(""))
                  {
                      EDITDate displayDate = null;

                      if (dateType.equalsIgnoreCase("Effective"))
                      {
                          displayDate = new EDITDate(prevEffDateValue);
                      }
                      else
                      {
                          displayDate = new EDITDate(prevDateProcessValue);
                      }

                      displayProcessDate = DateTimeUtil.formatEDITDateAsMMDDYYYY(displayDate).substring(0, 7); // only want 2 digit year

                      displayDate = new EDITDate(prevValuationDateValue);
                      displayValuationDate = DateTimeUtil.formatEDITDateAsMMDDYYYY(displayDate).substring(0, 7); // only want 2 digit year

  %>

    <tr>
      <td align="right" width="6%"><font size="1"><%= displayAcctngPeriod %></font></td>
      <td align="right" width="5%"><font size="1"><%= displayProcessDate %></font></td>
      <td align="right" width="5%"><font size="1"><%= displayValuationDate %></font></td>
  <%
      if (total.isLT(EDITBigDecimal.EDIT_DECIMAL_ZILCH_STR))
      {
          total = total.multiplyEditBigDecimal("-1");
  %>
      <td align="right" width="5%"><font size="1">(<script>document.write(formatFee(<%= total %>))</script>)</font></td>
  <%
      }
      else
      {
  %>
      <td align="right" width="5%"><font size="1"><script>document.write(formatFee(<%= total %>))</script></font></td>
  <%
      }
                      
      for (int k = 0; k < fees.length; k++)
      {
          if (fees[k].isLT(EDITBigDecimal.EDIT_DECIMAL_ZILCH_STR))
          {
              fees[k] = fees[k].multiplyEditBigDecimal("-1");
  %>
      <td align="right" width="5%"><font size="1">(<script>document.write(formatFee(<%= fees[k] %>))</script>)</font></td>
  <%
          }
          else
          {
  %>
      <td align="right" width="5%"><font size="1"><script>document.write(formatFee(<%= fees[k] %>))</script></font></td>
  <%
          }
      }
  %>
    </tr>
  <%
                      if (tableHeaderCreated)
                      {
  %>
    <tr>
      <td align="right" width="6%"><font size="1">All Date Total</font></td>
      <td align="right" width="5%"><font size="1">&nbsp;</font></td>
      <td align="right" width="5%"><font size="1">&nbsp;</font></td>
  <%
      if (cashTransferTotal.isLT(EDITBigDecimal.EDIT_DECIMAL_ZILCH_STR))
      {
          cashTransferTotal = cashTransferTotal.multiplyEditBigDecimal("-1");
  %>
      <td align="right" width="5%"><font size="1">(<script>document.write(formatFee(<%= cashTransferTotal %>))</script>)</font></td>
  <%
      }
      else
      {
  %>
      <td align="right" width="5%"><font size="1"><script>document.write(formatFee(<%= cashTransferTotal %>))</script></font></td>
  <%
      }
      for (int j = 0; j < allCashFees.length; j++)
      {
          if (allCashFees[j].isLT(EDITBigDecimal.EDIT_DECIMAL_ZILCH_STR))
          {
              allCashFees[j] = allCashFees[j].multiplyEditBigDecimal("-1");
  %>
      <td align="right" width="5%"><font size="1">(<script>document.write(formatFee(<%= allCashFees[j] %>))</script>)</font></td>
  <%
          }
          else
          {
  %>
      <td align="right" width="5%"><font size="1"><script>document.write(formatFee(<%= allCashFees[j] %>))</script></font></td>
  <%
          }
      }
  %>
    </tr>
  <%
                      }
                  }
  %>
  </table>
</div>
  <%
              }

              for (int j= 0; j < fees.length; j++)
              {
                  fees[j] = new EDITBigDecimal();
              }

              total = new EDITBigDecimal("0", 7);

              prevDateProcessValue = "";
              prevEffDateValue = "";
              prevValuationDateValue = "";
              prevAccountingPeriod = "";
              feePrevDateProcessValue = "";
              feePrevEffDateValue = "";
              feePrevValuationDateValue = "";

              if (editTrxHistoryVOs != null)
              {
                  tableHeaderCreated = false;

                  for (int j = 0; j < editTrxHistoryVOs.length; j++)
                  {
                      trxTypeMatchFound = false;
                      EDITTrxVO editTrxVO = (EDITTrxVO) editTrxHistoryVOs[j].getParentVO(EDITTrxVO.class);
                      String trxType = editTrxVO.getTransactionTypeCT();
                      String currAccountingPeriod = editTrxVO.getAccountingPeriod();

                      String currValuationDateValue = "";
                      EDITBigDecimal trxAmount = new EDITBigDecimal();
                      EDITBigDecimal gainLoss = new EDITBigDecimal();
                      EDITBigDecimal coiAmount = new EDITBigDecimal();
                      EDITBigDecimal adminFeeAmount = new EDITBigDecimal();

                      String contractNumber = "";
                      boolean invalidTrx = false;

                      if (trxType.equalsIgnoreCase("MD"))
                      {
                          if (editTrxVO.getStatus().equalsIgnoreCase("N") ||
                              editTrxVO.getStatus().equalsIgnoreCase("A"))
                          {
                              trxFromToInd = "F";
                          }
                          else
                          {
                              trxFromToInd = "T";
                          }

                          BucketHistoryVO[] bucketHistoryVOs = editTrxHistoryVOs[j].getBucketHistoryVO();
                          for (int k = 0; k < bucketHistoryVOs.length; k++)
                          {
                              InvestmentVO investmentVO = (InvestmentVO) bucketHistoryVOs[k].getParentVO(BucketVO.class).getParentVO(InvestmentVO.class);
                              if (investmentVO.getFilteredFundFK() == Long.parseLong(filteredFundFK))
                              {
                                  currValuationDateValue = getValuationDate(editTrxHistoryVOs[j].getInvestmentHistoryVO());
                                  BucketChargeHistoryVO[] bucketChargeHistoryVOs = bucketHistoryVOs[k].getBucketChargeHistoryVO();
                                  if (bucketChargeHistoryVOs != null)
                                  {
                                      for (int m = 0; m < bucketChargeHistoryVOs.length; m++)
                                      {
                                          if (bucketChargeHistoryVOs[m].getChargeTypeCT().equalsIgnoreCase("BucketCoi"))
                                          {
                                              if (trxFromToInd.equalsIgnoreCase("F"))
                                              {
                                                  coiAmount = coiAmount.addEditBigDecimal(bucketChargeHistoryVOs[m].getChargeAmount());
                                              }
                                              else
                                              {
                                                  coiAmount = coiAmount.subtractEditBigDecimal(bucketChargeHistoryVOs[m].getChargeAmount());
                                              }
                                          }
                                          else if (bucketChargeHistoryVOs[m].getChargeTypeCT().equalsIgnoreCase("BucketAdminCharge"))
                                          {
                                              if (trxFromToInd.equalsIgnoreCase("F"))
                                              {
                                                  adminFeeAmount = adminFeeAmount.addEditBigDecimal(bucketChargeHistoryVOs[m].getChargeAmount());
                                              }
                                              else
                                              {
                                                  adminFeeAmount = adminFeeAmount.subtractEditBigDecimal(bucketChargeHistoryVOs[m].getChargeAmount());
                                              }
                                          }
                                      }
                                  }
                              }
                          }
                      }

                      InvestmentHistoryVO[] investmentHistoryVOs = editTrxHistoryVOs[j].getInvestmentHistoryVO();
                      for (int k = 0; k < investmentHistoryVOs.length; k++)
                      {
                          InvestmentVO investmentVO = (InvestmentVO) investmentHistoryVOs[k].getParentVO(InvestmentVO.class);
                          if (investmentVO.getFilteredFundFK() == Long.parseLong(filteredFundFK))
                          {
                              if (!trxType.equalsIgnoreCase("MD"))
                              {
                                  currValuationDateValue = investmentHistoryVOs[k].getValuationDate();
                                  trxFromToInd = investmentHistoryVOs[k].getToFromStatus();

                                  if (trxFromToInd == null)
                                  {
                                      String[] erroredTrxInfo = new String[] {trxType, editTrxVO.getEffectiveDate()};
                                      erroredTrxHT.put(contractNumber, erroredTrxInfo);
                                      invalidTrx = true;
                                  }
                                  else if (trxFromToInd.equalsIgnoreCase("T"))
                                  {
                                      trxAmount = trxAmount.subtractEditBigDecimal(new EDITBigDecimal(investmentHistoryVOs[k].getInvestmentDollars()));
                                  }
                                  else
                                  {
                                      trxAmount = trxAmount.addEditBigDecimal(new EDITBigDecimal(investmentHistoryVOs[k].getInvestmentDollars()));
                                  }
                              }

                              gainLoss = gainLoss.addEditBigDecimal(new EDITBigDecimal(investmentHistoryVOs[k].getGainLoss()));
                          }
                      }

                      if (currValuationDateValue == null)
                      {
                          currValuationDateValue = "";
                      }

                      String currDateProcessValue = editTrxHistoryVOs[j].getProcessDateTime().substring(0, 10);
                      String currEffDateValue = editTrxVO.getEffectiveDate();

                      if (prevDateProcessValue.equals(""))
                      {
                          EDITDate edCurrDateProcValue = new EDITDate(currDateProcessValue);
                          EDITDate edCurrEffDateValue = new EDITDate(currEffDateValue);

                          if (fee != null)
                          {
                              for (int k = 0; k < fee.length; k++)
                              {
                                  FeeVO feeVO = (FeeVO) fee[k].getVO();

                                  if (feeVO.getReleaseDate() != null)
                                  {
                                      EDITDate releaseDate = new EDITDate(feeVO.getReleaseDate());
                                      displayAcctngPeriod = releaseDate.getMonthName() + "-" + releaseDate.getFormattedYear();
                                  }
                                  else
                                  {
                                      displayAcctngPeriod = "";
                                  }

                                  feeFromToInd = feeVO.getToFromInd();
                                  if (feeVO.getTransactionTypeCT().equalsIgnoreCase("DPURCH") ||
                                      feeVO.getTransactionTypeCT().equalsIgnoreCase("DFACC"))
                                  {
                                      String feeCurrDateProcessValue = feeVO.getProcessDateTime().substring(0, 10);
                                      String feeCurrEffDateValue = feeVO.getEffectiveDate();

                                      EDITDate edFeeCurrDateProcValue = new EDITDate(feeCurrDateProcessValue);
                                      EDITDate edFeeCurrEffDateValue = new EDITDate(feeCurrEffDateValue);

                                      UnitValuesVO[] unitValuesVO = engineLookup.getUnitValuesByFilteredFundIdDateChargeCode(Long.parseLong(filteredFundFK),
                                                                                                                             feeCurrEffDateValue,
                                                                                                                             pricingDirection,
                                                                                                                             chargeCodeFKsToUse[0]);
                                      String feeCurrValuationDateValue = "";
                                      if (unitValuesVO != null && unitValuesVO.length > 0)
                                      {
                                          feeCurrValuationDateValue = unitValuesVO[0].getEffectiveDate();
                                      }
                                      if (feePrevValuationDateValue.equals(""))
                                      {
                                          feePrevValuationDateValue = feeCurrValuationDateValue;
                                      }

                                      if ((dateType.equalsIgnoreCase("Effective") &&
                                           edFeeCurrEffDateValue.before(edCurrEffDateValue)) ||
                                          (dateType.equalsIgnoreCase("Process") &&
                                           edFeeCurrDateProcValue.before(edCurrDateProcValue)))
                                      {
                                          if (feePrevDateProcessValue.equals(""))
                                          {
                                              feePrevDateProcessValue = feeCurrDateProcessValue;
                                          }

                                          if (feePrevEffDateValue.equals(""))
                                          {
                                              feePrevEffDateValue = feeCurrEffDateValue;
                                          }

                                          if ((!feeCurrDateProcessValue.equals(feePrevDateProcessValue) ||
                                               !feeCurrEffDateValue.equals(feePrevEffDateValue)) &&
                                              !total.isEQ("0"))
                                          {
                                              EDITDate displayDate = null;

                                              if (dateType.equalsIgnoreCase("Effective"))
                                              {
                                                  displayDate = new EDITDate(feePrevEffDateValue);
                                              }
                                              else
                                              {
                                                  displayDate = new EDITDate(feePrevDateProcessValue);
                                              }

                                              displayProcessDate = DateTimeUtil.formatEDITDateAsMMDDYYYY(displayDate).substring(0, 7); // only want 2 digit year

                                              displayDate = new EDITDate(feePrevValuationDateValue);
                                              displayValuationDate = DateTimeUtil.formatEDITDateAsMMDDYYYY(displayDate).substring(0, 7); // only want 2 digit year

                                              if (!tableHeaderCreated)
                                              {
                                                  tableHeaderCreated = true;
                                                  recordsOutputted = true;
  %>
<br>
<div class="pagebreak" width="100%" align="left">
  <table width="100%" cellpadding="2" cellspacing="4">
    <tr>
      <td align="left" colspan="18">
        <b>Accrual Transfers (To)/From Fund</b>
      </td>
    </tr>
    <tr>
      <td align="left" colspan="18"><hr size="1" color="black" noshade></hr></td>
    </tr>
  </table>
  <table border="1" borderstyle="solid" width="100%" cellpadding="0" cellspacing="1">
    <tr>
      <td align="center" width="5%"><font size="1">Accounting Period</font></td>
      <td align="center" width="5%"><font size="1">Date Process</font></td>
      <td align="center" width="5%"><font size="1">Valuation Date</font></td>
      <td align="center" width="5%"><font size="1">Total</font></td>
      <td align="center" width="5%"><font size="1">Reallocation</font></td>
      <td align="center" width="5%"><font size="1">Net Premium</font></td>
      <td align="center" width="5%"><font size="1">Advance Transfers</font></td>
      <td align="center" width="5%"><font size="1">Surrender</font></td>
      <td align="center" width="5%"><font size="1">RRD</font></td>
      <td align="center" width="5%"><font size="1">Transfer Mortality Reserve</font></td>
      <td align="center" width="5%"><font size="1">Admin Fee</font></td>
      <td align="center" width="5%"><font size="1">COI</font></td>
      <td align="center" width="5%"><font size="1">M&E Fee</font></td>
      <td align="center" width="5%"><font size="1">Management Fee</font></td>
      <td align="center" width="5%"><font size="1">Advisory Fee</font></td>
      <td align="center" width="5%"><font size="1">Performance Fee</font></td>
      <td align="center" width="5%"><font size="1">SVA Fee</font></td>
      <td align="center" width="5%"><font size="1">Gain/(Loss)</font></td>
    </tr>
  <%
                                              }
  %>
    <tr>
      <td align="right" width="6%"><font size="1"><%= displayAcctngPeriod %></font></td>
      <td align="right" width="5%"><font size="1"><%= displayProcessDate %></font></td>
      <td align="right" width="5%"><font size="1"><%= displayValuationDate %></font></td>
  <%
                                              
      if (total.isLT(EDITBigDecimal.EDIT_DECIMAL_ZILCH_STR))
      {
          total = total.multiplyEditBigDecimal("-1");
  %>
      <td align="right" width="5%"><font size="1">(<script>document.write(formatFee(<%= total %>))</script>)</font></td>
  <%
      }
      else
      {
  %>
      <td align="right" width="5%"><font size="1"><script>document.write(formatFee(<%= total %>))</script></font></td>
  <%
      }

      for (int l = 0; l < fees.length; l++)
      {
          if (fees[l].isLT(EDITBigDecimal.EDIT_DECIMAL_ZILCH_STR))
          {
              fees[l] = fees[l].multiplyEditBigDecimal("-1");
  %>
      <td align="right" width="5%"><font size="1">(<script>document.write(formatFee(<%= fees[l] %>))</script>)</font></td>
  <%
          }
          else
          {
  %>
      <td align="right" width="5%"><font size="1"><script>document.write(formatFee(<%= fees[l] %>))</script></font></td>
  <%
          }
      }
  %>
    </tr>
  <%
                                              feePrevDateProcessValue = feeCurrDateProcessValue;
                                              feePrevEffDateValue = feeCurrEffDateValue;
                                              feePrevValuationDateValue = feeCurrValuationDateValue;

                                              for (int l = 0; l < fees.length; l++)
                                              {
                                                  fees[l] = new EDITBigDecimal();
                                              }

                                              total = new EDITBigDecimal("0", 7);
                                          }

                                          FeeDescriptionVO feeDescriptionVO = fee[k].getFeeDescription();
                                          
                                          if (feeDescriptionVO != null)
                                          {
                                              for (int l = 0; l < feeTypes.length; l++)
                                              {
                                                  if (feeDescriptionVO.getFeeTypeCT().equalsIgnoreCase(feeTypes[l]))
                                                  {
                                                      if ((feeFromToInd != null && feeFromToInd.equalsIgnoreCase("F")) ||
                                                          (feeDescriptionVO.getFeeTypeCT().equalsIgnoreCase(GAIN_LOSS_FEE) &&
                                                           new EDITBigDecimal(feeVO.getTrxAmount()).isGT("0")))
                                                      {
                                                          fees[l] = fees[l].addEditBigDecimal(feeVO.getTrxAmount());
                                                          allAccrualFees[l] = allAccrualFees[l].addEditBigDecimal(feeVO.getTrxAmount());
            
                                                          total = total.addEditBigDecimal(feeVO.getTrxAmount());
                                                          allTransfersTotal = allTransfersTotal.addEditBigDecimal(feeVO.getTrxAmount());
                                                          accrualTransferTotal = accrualTransferTotal.addEditBigDecimal(feeVO.getTrxAmount());
                                                      }
                                                      else
                                                      {
                                                          fees[l] = fees[l].subtractEditBigDecimal(feeVO.getTrxAmount());
                                                          allAccrualFees[l] = allAccrualFees[l].subtractEditBigDecimal(feeVO.getTrxAmount());
            
                                                          total = total.subtractEditBigDecimal(feeVO.getTrxAmount());
                                                          allTransfersTotal = allTransfersTotal.subtractEditBigDecimal(feeVO.getTrxAmount());
                                                          accrualTransferTotal = accrualTransferTotal.subtractEditBigDecimal(feeVO.getTrxAmount());
                                                      }
                                                  }
                                              }
                                          }
                                      }
                                  }
                              }

                              if (((dateType.equalsIgnoreCase("Effective") &&
                                    !feePrevEffDateValue.equals("")) ||
                                   (dateType.equalsIgnoreCase("Process") &&
                                    !feePrevDateProcessValue.equals(""))) &&
                                  !total.isEQ("0"))
                              {
                                  EDITDate displayDate = null;

                                  if (dateType.equalsIgnoreCase("Effective"))
                                  {
                                      displayDate = new EDITDate(feePrevEffDateValue);
                                  }
                                  else
                                  {
                                      displayDate = new EDITDate(feePrevDateProcessValue);
                                  }

                                  displayProcessDate = DateTimeUtil.formatEDITDateAsMMDDYYYY(displayDate).substring(0, 7); // only want 2 digit year

                                  displayDate = new EDITDate(feePrevValuationDateValue);
                                  displayValuationDate = DateTimeUtil.formatEDITDateAsMMDDYYYY(displayDate).substring(0, 7); // only want 2 digit year

                                  if (!tableHeaderCreated)
                                  {
                                      tableHeaderCreated = true;
                                      recordsOutputted = true;
  %>
<br>
<div class="pagebreak" width="100%" align="left">
<table width="100%" cellpadding="2" cellspacing="4">
    <tr>
      <td align="left" colspan="18">
        <b>Accrual Transfers (To)/From Fund</b>
      </td>
    </tr>
    <tr>
      <td align="left" colspan="18"><hr size="1" color="black" noshade></hr></td>
    </tr>
  </table>
  <table border="1" borderstyle="solid" width="100%" cellpadding="0" cellspacing="1">
    <tr>
      <td align="center" width="5%"><font size="1">Accounting Period</font></td>
      <td align="center" width="5%"><font size="1">Date Process</font></td>
      <td align="center" width="5%"><font size="1">Valuation Date</font></td>
      <td align="center" width="5%"><font size="1">Total</font></td>
      <td align="center" width="5%"><font size="1">Reallocation</font></td>
      <td align="center" width="5%"><font size="1">Net Premium</font></td>
      <td align="center" width="5%"><font size="1">Advance Transfers</font></td>
      <td align="center" width="5%"><font size="1">Surrender</font></td>
      <td align="center" width="5%"><font size="1">RRD</font></td>
      <td align="center" width="5%"><font size="1">Transfer Mortality Reserve</font></td>
      <td align="center" width="5%"><font size="1">Admin Fee</font></td>
      <td align="center" width="5%"><font size="1">COI</font></td>
      <td align="center" width="5%"><font size="1">M&E Fee</font></td>
      <td align="center" width="5%"><font size="1">Management Fee</font></td>
      <td align="center" width="5%"><font size="1">Advisory Fee</font></td>
      <td align="center" width="5%"><font size="1">Performance Fee</font></td>
      <td align="center" width="5%"><font size="1">SVA Fee</font></td>
      <td align="center" width="5%"><font size="1">Gain/(Loss)</font></td>
    </tr>
  <%
                                  }
  %>
    <tr>
      <td align="right" width="6%"><font size="1"><%= displayAcctngPeriod %></font></td>
      <td align="right" width="5%"><font size="1"><%= displayProcessDate %></font></td>
      <td align="right" width="5%"><font size="1"><%= displayValuationDate %></font></td>
  <%
      if (total.isLT(EDITBigDecimal.EDIT_DECIMAL_ZILCH_STR))
      {
          total = total.multiplyEditBigDecimal("-1");
  %>
      <td align="right" width="5%"><font size="1">(<script>document.write(formatFee(<%= total %>))</script>)</font></td>
  <%
      }
      else
      {
  %>
      <td align="right" width="5%"><font size="1"><script>document.write(formatFee(<%= total %>))</script></font></td>
  <%
      }

      for (int k = 0; k < fees.length; k++)
      {
          if (fees[k].isLT(EDITBigDecimal.EDIT_DECIMAL_ZILCH_STR))
          {              
              fees[k] = fees[k].multiplyEditBigDecimal("-1");
  %>
      <td align="right" width="5%"><font size="1">(<script>document.write(formatFee(<%= fees[k] %>))</script>)</font></td>
  <%
          }
          else
          {
  %>
      <td align="right" width="5%"><font size="1"><script>document.write(formatFee(<%= fees[k] %>))</script></font></td>
  <%
          }
      }
  %>
    </tr>
  <%
                              }

                              for (int l = 0; l < fees.length; l++)
                              {
                                  fees[l] = new EDITBigDecimal();
                              }

                              total = new EDITBigDecimal("0", 7);
                          }

                          prevDateProcessValue = currDateProcessValue;
                          prevEffDateValue = currEffDateValue;
                      }

                      if (prevValuationDateValue.equals(""))
                      {
                          prevValuationDateValue = currValuationDateValue;
                      }

                      if (prevAccountingPeriod.equals(""))
                      {
                          prevAccountingPeriod = currAccountingPeriod;
                      }

                      if ((!currDateProcessValue.equals(prevDateProcessValue) ||
                           !currEffDateValue.equals(prevEffDateValue) ||
                           !currAccountingPeriod.equals(prevAccountingPeriod)))
                      {
                          if (!total.isEQ("0"))
                          {
                              displayAcctngPeriod = EDITDate.getMonthName(DateTimeUtil.getMonthFromAccountingPeriod(prevAccountingPeriod)) + "-" + DateTimeUtil.getYearFromAccountingPeriod(prevAccountingPeriod);

                              EDITDate displayDate = null;

                              if (dateType.equalsIgnoreCase("Effective"))
                              {
                                  displayDate = new EDITDate(prevEffDateValue);
                              }
                              else
                              {
                                  displayDate = new EDITDate(prevDateProcessValue);
                              }

                              displayProcessDate = DateTimeUtil.formatEDITDateAsMMDDYYYY(displayDate).substring(0, 7); // only want 2 digit year

                              displayDate = new EDITDate(prevValuationDateValue);
                              displayValuationDate = DateTimeUtil.formatEDITDateAsMMDDYYYY(displayDate).substring(0, 7); // only want 2 digit year

                              if (!tableHeaderCreated)
                              {
                                  tableHeaderCreated = true;
                                  recordsOutputted = true;
  %>
<br>
<div class="pagebreak" width="100%" align="left">
<table width="100%" cellpadding="2" cellspacing="4">
    <tr>
      <td align="left" colspan="18">
        <b>Accrual Transfers (To)/From Fund</b>
      </td>
    </tr>
    <tr>
      <td align="left" colspan="18"><hr size="1" color="black" noshade></hr></td>
    </tr>
  </table>
  <table border="1" borderstyle="solid" width="100%" cellpadding="0" cellspacing="1">
    <tr>
      <td align="center" width="5%"><font size="1">Accounting Period</font></td>
      <td align="center" width="5%"><font size="1">Date Process</font></td>
      <td align="center" width="5%"><font size="1">Valuation Date</font></td>
      <td align="center" width="5%"><font size="1">Total</font></td>
      <td align="center" width="5%"><font size="1">Reallocation</font></td>
      <td align="center" width="5%"><font size="1">Net Premium</font></td>
      <td align="center" width="5%"><font size="1">Advance Transfers</font></td>
      <td align="center" width="5%"><font size="1">Surrender</font></td>
      <td align="center" width="5%"><font size="1">RRD</font></td>
      <td align="center" width="5%"><font size="1">Transfer Mortality Reserve</font></td>
      <td align="center" width="5%"><font size="1">Admin Fee</font></td>
      <td align="center" width="5%"><font size="1">COI</font></td>
      <td align="center" width="5%"><font size="1">M&E Fee</font></td>
      <td align="center" width="5%"><font size="1">Management Fee</font></td>
      <td align="center" width="5%"><font size="1">Advisory Fee</font></td>
      <td align="center" width="5%"><font size="1">Performance Fee</font></td>
      <td align="center" width="5%"><font size="1">SVA Fee</font></td>
      <td align="center" width="5%"><font size="1">Gain/(Loss)</font></td>
    </tr>
  <%
                              }
  %>
    <tr>
      <td align="right" width="6%"><font size="1"><%= displayAcctngPeriod %></font></td>
      <td align="right" width="5%"><font size="1"><%= displayProcessDate %></font></td>
      <td align="right" width="5%"><font size="1"><%= displayValuationDate %></font></td>
  <%
      if (total.isLT(EDITBigDecimal.EDIT_DECIMAL_ZILCH_STR))
      {
          total = total.multiplyEditBigDecimal("-1");
  %>
      <td align="right" width="5%"><font size="1">(<script>document.write(formatFee(<%= total %>))</script>)</font></td>
  <%
      }
      else
      {
  %>
      <td align="right" width="5%"><font size="1"><script>document.write(formatFee(<%= total %>))</script></font></td>
  <%
      }

      for (int k = 0; k < fees.length; k++)
      {
          if (fees[k].isLT(EDITBigDecimal.EDIT_DECIMAL_ZILCH_STR))
          {
              fees[k] = fees[k].multiplyEditBigDecimal("-1");
  %>
      <td align="right" width="5%"><font size="1">(<script>document.write(formatFee(<%= fees[k] %>))</script>)</font></td>
  <%
          }
          else
          {
  %>
      <td align="right" width="5%"><font size="1"><script>document.write(formatFee(<%= fees[k] %>))</script></font></td>
  <%
          }
      }
  %>
    </tr>
  <%
                              for (int k = 0; k < fees.length; k++)
                              {
                                  fees[k] = new EDITBigDecimal();
                              }

                              total = new EDITBigDecimal("0", 7);

                              feePrevDateProcessValue = "";
                              feePrevEffDateValue = "";
                              feePrevValuationDateValue = "";
                              EDITDate edPrevDateProcValue = new EDITDate(prevDateProcessValue);
                              EDITDate edPrevEffDateValue = new EDITDate(prevEffDateValue);
                              EDITDate edCurrDateProcValue = new EDITDate(currDateProcessValue);
                              EDITDate edCurrEffDateValue = new EDITDate(currEffDateValue);

                              if (fee != null)
                              {
                                  for (int k = 0; k < fee.length; k++)
                                  {
                                      FeeVO feeVO = (FeeVO) fee[k].getVO();

                                      if (feeVO.getReleaseDate() != null)
                                      {
                                          EDITDate releaseDate = new EDITDate(feeVO.getReleaseDate());
                                          displayAcctngPeriod = releaseDate.getMonthName() + "-" + releaseDate.getFormattedYear();
                                      }
                                      else
                                      {
                                          displayAcctngPeriod = "";
                                      }

                                      feeFromToInd = feeVO.getToFromInd();
                                      if (feeVO.getTransactionTypeCT().equalsIgnoreCase("DPURCH") ||
                                          feeVO.getTransactionTypeCT().equalsIgnoreCase("DFACC"))
                                      {
                                          String feeCurrDateProcessValue = feeVO.getProcessDateTime().substring(0, 10);
                                          String feeCurrEffDateValue = feeVO.getEffectiveDate();

                                          EDITDate edFeeCurrDateProcValue = new EDITDate(feeCurrDateProcessValue);
                                          EDITDate edFeeCurrEffDateValue = new EDITDate(feeCurrEffDateValue);

                                          if ((dateType.equalsIgnoreCase("Effective") &&
                                               ( (edFeeCurrEffDateValue.after(edPrevEffDateValue) || edFeeCurrEffDateValue.equals(edPrevEffDateValue)) &&
                                                edFeeCurrEffDateValue.before(edCurrEffDateValue))) ||
                                              (dateType.equalsIgnoreCase("Process") &&
                                               ( (edFeeCurrDateProcValue.after(edPrevDateProcValue) || edFeeCurrDateProcValue.equals(edPrevDateProcValue)) &&
                                                edFeeCurrDateProcValue.before(edCurrDateProcValue))))
                                          {
                                              if (feePrevDateProcessValue.equals(""))
                                              {
                                                  feePrevDateProcessValue = feeCurrDateProcessValue;
                                              }

                                              if (feePrevEffDateValue.equals(""))
                                              {
                                                  feePrevEffDateValue = feeCurrEffDateValue;
                                              }

                                              UnitValuesVO[] feeUnitValuesVO = engineLookup.
                                                      getUnitValuesByFilteredFundIdDateChargeCode(Long.parseLong(filteredFundFK),
                                                                                                  feeCurrEffDateValue,
                                                                                                  pricingDirection,
                                                                                                  chargeCodeFKsToUse[0]);
                                              String feeCurrValuationDateValue = "";
                                              if (feeUnitValuesVO != null && feeUnitValuesVO.length > 0)
                                              {
                                                  feeCurrValuationDateValue = feeUnitValuesVO[0].getEffectiveDate();
                                              }

                                              if (feePrevValuationDateValue.equals(""))
                                              {
                                                  feePrevValuationDateValue = feeCurrValuationDateValue;
                                              }

                                              if ((!feeCurrDateProcessValue.equals(feePrevDateProcessValue) ||
                                                   !feeCurrEffDateValue.equals(feePrevEffDateValue)) &&
                                                  (!total.isEQ("0")))
                                              {
                                                  if (dateType.equalsIgnoreCase("Effective"))
                                                  {
                                                      displayDate = new EDITDate(feePrevEffDateValue);
                                                  }
                                                  else
                                                  {
                                                      displayDate = new EDITDate(feePrevDateProcessValue);
                                                  }

                                                  displayProcessDate = DateTimeUtil.formatEDITDateAsMMDDYYYY(displayDate).substring(0, 7); // only want 2 digit year

                                                  displayDate = new EDITDate(feePrevValuationDateValue);
                                                  displayValuationDate = DateTimeUtil.formatEDITDateAsMMDDYYYY(displayDate).substring(0, 7); // only want 2 digit year

                                                  if (!tableHeaderCreated)
                                                  {
                                                      tableHeaderCreated = true;
                                                      recordsOutputted = true;
  %>
<br>
<div class="pagebreak" width="100%" align="left">
<table width="100%" cellpadding="2" cellspacing="4">
    <tr>
      <td align="left" colspan="18">
        <b>Accrual Transfers (To)/From Fund</b>
      </td>
    </tr>
    <tr>
      <td align="left" colspan="18"><hr size="1" color="black" noshade></hr></td>
    </tr>
  </table>
  <table border="1" borderstyle="solid" width="100%" cellpadding="0" cellspacing="1">
    <tr>
      <td align="center" width="5%"><font size="1">Accounting Period</font></td>
      <td align="center" width="5%"><font size="1">Date Process</font></td>
      <td align="center" width="5%"><font size="1">Valuation Date</font></td>
      <td align="center" width="5%"><font size="1">Total</font></td>
      <td align="center" width="5%"><font size="1">Reallocation</font></td>
      <td align="center" width="5%"><font size="1">Net Premium</font></td>
      <td align="center" width="5%"><font size="1">Advance Transfers</font></td>
      <td align="center" width="5%"><font size="1">Surrender</font></td>
      <td align="center" width="5%"><font size="1">RRD</font></td>
      <td align="center" width="5%"><font size="1">Transfer Mortality Reserve</font></td>
      <td align="center" width="5%"><font size="1">Admin Fee</font></td>
      <td align="center" width="5%"><font size="1">COI</font></td>
      <td align="center" width="5%"><font size="1">M&E Fee</font></td>
      <td align="center" width="5%"><font size="1">Management Fee</font></td>
      <td align="center" width="5%"><font size="1">Advisory Fee</font></td>
      <td align="center" width="5%"><font size="1">Performance Fee</font></td>
      <td align="center" width="5%"><font size="1">SVA Fee</font></td>
      <td align="center" width="5%"><font size="1">Gain/(Loss)</font></td>
    </tr>
  <%
                                                  }
  %>
    <tr>
      <td align="right" width="6%"><font size="1"><%= displayAcctngPeriod %></font></td>
      <td align="right" width="5%"><font size="1"><%= displayProcessDate %></font></td>
      <td align="right" width="5%"><font size="1"><%= displayValuationDate %></font></td>
  <%
      if (total.isLT(EDITBigDecimal.EDIT_DECIMAL_ZILCH_STR))
      {
          total = total.multiplyEditBigDecimal("-1");
  %>
      <td align="right" width="5%"><font size="1">(<script>document.write(formatFee(<%= total %>))</script>)</font></td>
  <%
      }
      else
      {
  %>
      <td align="right" width="5%"><font size="1"><script>document.write(formatFee(<%= total %>))</script></font></td>
  <%
      }

      for (int l = 0; l < fees.length; l++)
      {
          if (fees[l].isLT(EDITBigDecimal.EDIT_DECIMAL_ZILCH_STR))
          {
              fees[l] = fees[l].multiplyEditBigDecimal("-1");
  %>
      <td align="right" width="5%"><font size="1">(<script>document.write(formatFee(<%= fees[l] %>))</script>)</font></td>
  <%
          }
          else
          {
  %>
      <td align="right" width="5%"><font size="1"><script>document.write(formatFee(<%= fees[l] %>))</script></font></td>
  <%
          }
      }
  %>
    </tr>
  <%
                                                  feePrevDateProcessValue = feeCurrDateProcessValue;
                                                  feePrevEffDateValue = feeCurrEffDateValue;
                                                  feePrevValuationDateValue = feeCurrValuationDateValue;

                                                  for (int l = 0; l < fees.length; l++)
                                                  {
                                                      fees[l] = new EDITBigDecimal();
                                                  }

                                                  total = new EDITBigDecimal("0", 7);
                                              }

                                              FeeDescriptionVO feeDescriptionVO = fee[k].getFeeDescription();

                                              if (feeDescriptionVO != null)
                                              {
                                                  for (int l = 0; l < feeTypes.length; l++)
                                                  {
                                                      if (feeDescriptionVO.getFeeTypeCT().equalsIgnoreCase(feeTypes[l]))
                                                      {
                                                          if ((feeFromToInd != null && feeFromToInd.equalsIgnoreCase("F")) ||
                                                              (feeDescriptionVO.getFeeTypeCT().equalsIgnoreCase(GAIN_LOSS_FEE) &&
                                                               new EDITBigDecimal(feeVO.getTrxAmount()).isGT("0")))
                                                          {
                                                              fees[l] = fees[l].addEditBigDecimal(feeVO.getTrxAmount());
                                                              allAccrualFees[l] = allAccrualFees[l].addEditBigDecimal(feeVO.getTrxAmount());

                                                              total = total.addEditBigDecimal(feeVO.getTrxAmount());
                                                              allTransfersTotal = allTransfersTotal.addEditBigDecimal(feeVO.getTrxAmount());
                                                              accrualTransferTotal = accrualTransferTotal.addEditBigDecimal(feeVO.getTrxAmount());
                                                          }
                                                          else
                                                          {
                                                              fees[l] = fees[l].subtractEditBigDecimal(feeVO.getTrxAmount());
                                                              allAccrualFees[l] = allAccrualFees[l].subtractEditBigDecimal(feeVO.getTrxAmount());

                                                              total = total.subtractEditBigDecimal(feeVO.getTrxAmount());
                                                              allTransfersTotal = allTransfersTotal.subtractEditBigDecimal(feeVO.getTrxAmount());
                                                              accrualTransferTotal = accrualTransferTotal.subtractEditBigDecimal(feeVO.getTrxAmount());
                                                          }
                                                      }
                                                  }
                                              }
                                          }
                                      }
                                  }


                                  if (((dateType.equalsIgnoreCase("Effective") &&
                                        !feePrevEffDateValue.equals("")) ||
                                       (dateType.equalsIgnoreCase("Process") &&
                                        !feePrevDateProcessValue.equals(""))) &&
                                      !total.isEQ("0"))
                                  {
                                      if (dateType.equalsIgnoreCase("Effective"))
                                      {
                                          displayDate = new EDITDate(feePrevEffDateValue);
                                      }
                                      else
                                      {
                                          displayDate = new EDITDate(feePrevDateProcessValue);
                                      }

                                      displayProcessDate = DateTimeUtil.formatEDITDateAsMMDDYYYY(displayDate).substring(0, 7); // only want 2 digit year

                                      displayDate = new EDITDate(feePrevValuationDateValue);
                                      displayValuationDate = DateTimeUtil.formatEDITDateAsMMDDYYYY(displayDate).substring(0, 7); // only want 2 digit year

                                      if (!tableHeaderCreated)
                                      {
                                          tableHeaderCreated = true;
                                          recordsOutputted = true;
  %>
<br>
<div class="pagebreak" width="100%" align="left">
<table width="100%" cellpadding="2" cellspacing="4">
    <tr>
      <td align="left" colspan="18">
        <b>Accrual Transfers (To)/From Fund</b>
      </td>
    </tr>
    <tr>
      <td align="left" colspan="18"><hr size="1" color="black" noshade></hr></td>
    </tr>
  </table>
  <table border="1" borderstyle="solid" width="100%" cellpadding="0" cellspacing="1">
    <tr>
      <td align="center" width="5%"><font size="1">Accounting Period</font></td>
      <td align="center" width="5%"><font size="1">Date Process</font></td>
      <td align="center" width="5%"><font size="1">Valuation Date</font></td>
      <td align="center" width="5%"><font size="1">Total</font></td>
      <td align="center" width="5%"><font size="1">Reallocation</font></td>
      <td align="center" width="5%"><font size="1">Net Premium</font></td>
      <td align="center" width="5%"><font size="1">Advance Transfers</font></td>
      <td align="center" width="5%"><font size="1">Surrender</font></td>
      <td align="center" width="5%"><font size="1">RRD</font></td>
      <td align="center" width="5%"><font size="1">Transfer Mortality Reserve</font></td>
      <td align="center" width="5%"><font size="1">Admin Fee</font></td>
      <td align="center" width="5%"><font size="1">COI</font></td>
      <td align="center" width="5%"><font size="1">M&E Fee</font></td>
      <td align="center" width="5%"><font size="1">Management Fee</font></td>
      <td align="center" width="5%"><font size="1">Advisory Fee</font></td>
      <td align="center" width="5%"><font size="1">Performance Fee</font></td>
      <td align="center" width="5%"><font size="1">SVA Fee</font></td>
      <td align="center" width="5%"><font size="1">Gain/(Loss)</font></td>
    </tr>
  <%
                                  }
  %>
    <tr>
      <td align="right" width="6%"><font size="1"><%= displayAcctngPeriod %></font></td>
      <td align="right" width="5%"><font size="1"><%= displayProcessDate %></font></td>
      <td align="right" width="5%"><font size="1"><%= displayValuationDate %></font></td>
  <%
      if (total.isLT(EDITBigDecimal.EDIT_DECIMAL_ZILCH_STR))
      {
          total = total.multiplyEditBigDecimal("-1");
  %>
      <td align="right" width="5%"><font size="1">(<script>document.write(formatFee(<%= total %>))</script>)</font></td>
  <%
      }
      else
      {
  %>
      <td align="right" width="5%"><font size="1"><script>document.write(formatFee(<%= total %>))</script></font></td>
  <%
      }

      for (int l = 0; l < fees.length; l++)
      {
          if (fees[l].isLT(EDITBigDecimal.EDIT_DECIMAL_ZILCH_STR))
          {
              fees[l] = fees[l].multiplyEditBigDecimal("-1");
  %>
      <td align="right" width="5%"><font size="1">(<script>document.write(formatFee(<%= fees[l] %>))</script>)</font></td>
  <%
          }
          else
          {
  %>
      <td align="right" width="5%"><font size="1"><script>document.write(formatFee(<%= fees[l] %>))</script></font></td>
  <%
          }
      }
  %>
    </tr>
  <%
                                  }

                                  for (int l = 0; l < fees.length; l++)
                                  {
                                      fees[l] = new EDITBigDecimal();
                                  }

                                  total = new EDITBigDecimal("0", 7);
                              }
                          }

                          prevDateProcessValue = currDateProcessValue;
                          prevEffDateValue = currEffDateValue;
                          prevAccountingPeriod = currAccountingPeriod;
                          prevValuationDateValue = currValuationDateValue;
                      }


                      if (!invalidTrx)
                      {
                          for (int k = 0; k < EDITTrx.transferTrxTypes.length; k++)
                          {
                              if (trxType.equalsIgnoreCase(EDITTrx.transferTrxTypes[k]))
                              {
                                  fees[0] = fees[0].addEditBigDecimal(trxAmount);
                                  allAccrualFees[0] = allAccrualFees[0].addEditBigDecimal(trxAmount);

                                  trxTypeMatchFound = true;
                                  break;
                              }
                          }

                          for (int k = 0; k < EDITTrx.premiumTrxTypes.length; k++)
                          {
                              if (trxType.equalsIgnoreCase(EDITTrx.premiumTrxTypes[k]))
                              {
                                  fees[1] = fees[1].addEditBigDecimal(trxAmount);
                                  allAccrualFees[1] = allAccrualFees[1].addEditBigDecimal(trxAmount);

                                  trxTypeMatchFound = true;
                                  break;
                              }
                          }

                          for (int k = 0; k < EDITTrx.surrenderTrxTypes.length; k++)
                          {
                              if (trxType.equalsIgnoreCase(EDITTrx.surrenderTrxTypes[k]))
                              {
                                  fees[3] = fees[3].addEditBigDecimal(trxAmount);
                                  allAccrualFees[3] = allAccrualFees[3].addEditBigDecimal(trxAmount);

                                  trxTypeMatchFound = true;
                                  break;
                              }
                          }

                          for (int k = 0; k < EDITTrx.deathTrxTypes.length; k++)
                          {
                              if (trxType.equalsIgnoreCase(EDITTrx.deathTrxTypes[k]))
                              {
                                  fees[4] = fees[4].addEditBigDecimal(trxAmount);
                                  allAccrualFees[4] = allAccrualFees[4].addEditBigDecimal(trxAmount);

                                  trxTypeMatchFound = true;
                                  break;
                              }
                          }

                          for (int k = 0; k < EDITTrx.coiTrxTypes.length; k++)
                          {
                              if (trxType.equalsIgnoreCase(EDITTrx.coiTrxTypes[k]))
                              {
                                  fees[6] = fees[6].addEditBigDecimal(adminFeeAmount);
                                  fees[7] = fees[7].addEditBigDecimal(coiAmount);
                                  allAccrualFees[6] = allAccrualFees[6].addEditBigDecimal(adminFeeAmount);
                                  allAccrualFees[7] = allAccrualFees[7].addEditBigDecimal(coiAmount);

                                  total = total.addEditBigDecimal(adminFeeAmount);
                                  total = total.addEditBigDecimal(coiAmount);
                                  allTransfersTotal = allTransfersTotal.addEditBigDecimal(adminFeeAmount);
                                  allTransfersTotal = allTransfersTotal.addEditBigDecimal(coiAmount);
                                  accrualTransferTotal = accrualTransferTotal.addEditBigDecimal(adminFeeAmount);
                                  accrualTransferTotal = accrualTransferTotal.addEditBigDecimal(coiAmount);

                                  break;
                              }
                          }

                          if (trxTypeMatchFound)
                          {
                              total = total.addEditBigDecimal(trxAmount);
                              allTransfersTotal = allTransfersTotal.addEditBigDecimal(trxAmount);
                              accrualTransferTotal = accrualTransferTotal.addEditBigDecimal(trxAmount);
                              fees[13] = fees[13].addEditBigDecimal(gainLoss);
                          }
                      }
                  }

                  if (((dateType.equalsIgnoreCase("Effective") &&
                        !prevEffDateValue.equals("")) ||
                       (dateType.equalsIgnoreCase("Process") &&
                        !prevDateProcessValue.equals(""))) &&
                      !total.isEQ("0"))
                  {
                      displayAcctngPeriod = EDITDate.getMonthName(DateTimeUtil.getMonthFromAccountingPeriod(prevAccountingPeriod)) + "-" + DateTimeUtil.getYearFromAccountingPeriod(prevAccountingPeriod);

                      EDITDate displayDate = null;

                      if (dateType.equalsIgnoreCase("Effective"))
                      {
                          displayDate = new EDITDate(prevEffDateValue);
                      }
                      else
                      {
                          displayDate = new EDITDate(prevDateProcessValue);
                      }

                      displayProcessDate = DateTimeUtil.formatEDITDateAsMMDDYYYY(displayDate).substring(0, 7); // only want 2 digit year

                      displayDate = new EDITDate(prevValuationDateValue);
                      displayValuationDate = DateTimeUtil.formatEDITDateAsMMDDYYYY(displayDate).substring(0, 7); // only want 2 digit year

                      if (!tableHeaderCreated)
                      {
                          tableHeaderCreated = true;
                          recordsOutputted = true;
  %>
<br>
<div class="pagebreak" width="100%" align="left">
<table width="100%" cellpadding="2" cellspacing="4">
    <tr>
      <td align="left" colspan="18">
        <b>Accrual Transfers (To)/From Fund</b>
      </td>
    </tr>
    <tr>
      <td align="left" colspan="18"><hr size="1" color="black" noshade></hr></td>
    </tr>
  </table>
  <table border="1" borderstyle="solid" width="100%" cellpadding="0" cellspacing="1">
    <tr>
      <td align="center" width="5%"><font size="1">Accounting Period</font></td>
      <td align="center" width="5%"><font size="1">Date Process</font></td>
      <td align="center" width="5%"><font size="1">Valuation Date</font></td>
      <td align="center" width="5%"><font size="1">Total</font></td>
      <td align="center" width="5%"><font size="1">Reallocation</font></td>
      <td align="center" width="5%"><font size="1">Net Premium</font></td>
      <td align="center" width="5%"><font size="1">Advance Transfers</font></td>
      <td align="center" width="5%"><font size="1">Surrender</font></td>
      <td align="center" width="5%"><font size="1">RRD</font></td>
      <td align="center" width="5%"><font size="1">Transfer Mortality Reserve</font></td>
      <td align="center" width="5%"><font size="1">Admin Fee</font></td>
      <td align="center" width="5%"><font size="1">COI</font></td>
      <td align="center" width="5%"><font size="1">M&E Fee</font></td>
      <td align="center" width="5%"><font size="1">Management Fee</font></td>
      <td align="center" width="5%"><font size="1">Advisory Fee</font></td>
      <td align="center" width="5%"><font size="1">Performance Fee</font></td>
      <td align="center" width="5%"><font size="1">SVA Fee</font></td>
      <td align="center" width="5%"><font size="1">Gain/(Loss)</font></td>
    </tr>
  <%
                      }
  %>
    <tr>
      <td align="right" width="6%"><font size="1"><%= displayAcctngPeriod %></font></td>
      <td align="right" width="5%"><font size="1"><%= displayProcessDate %></font></td>
      <td align="right" width="5%"><font size="1"><%= displayValuationDate %></font></td>
  <%
      if (total.isLT(EDITBigDecimal.EDIT_DECIMAL_ZILCH_STR))
      {
          total = total.multiplyEditBigDecimal("-1");
  %>
      <td align="right" width="5%"><font size="1">(<script>document.write(formatFee(<%= total %>))</script>)</font></td>
  <%
      }
      else
      {
  %>
      <td align="right" width="5%"><font size="1"><script>document.write(formatFee(<%= total %>))</script></font></td>
  <%
      }
                      
      for (int k = 0; k < fees.length; k++)
      {
          if (fees[k].isLT(EDITBigDecimal.EDIT_DECIMAL_ZILCH_STR))
          {
              fees[k] = fees[k].multiplyEditBigDecimal("-1");
  %>
      <td align="right" width="5%"><font size="1">(<script>document.write(formatFee(<%= fees[k] %>))</script>)</font></td>
  <%
          }
          else
          {
  %>
      <td align="right" width="5%"><font size="1"><script>document.write(formatFee(<%= fees[k] %>))</script></font></td>
  <%
          }
      }
  %>
    </tr>
  <%
                      for (int j= 0; j < fees.length; j++)
                      {
                          fees[j] = new EDITBigDecimal();
                      }

                      total = new EDITBigDecimal("0", 7);
                  }

                  if (fee != null)
                  {
                      EDITDate edCurrDateProcValue = new EDITDate(prevDateProcessValue);
                      EDITDate edCurrEffDateValue = new EDITDate(prevEffDateValue);
                      feePrevDateProcessValue = "";
                      feePrevEffDateValue = "";

                      for (int k = 0; k < fee.length; k++)
                      {
                          FeeVO feeVO = (FeeVO) fee[k].getVO();

                          if (feeVO.getReleaseDate() != null)
                          {
                              EDITDate releaseDate = new EDITDate(feeVO.getReleaseDate());
                              displayAcctngPeriod = releaseDate.getMonthName() + "-" + releaseDate.getFormattedYear();
                          }
                          else
                          {
                              displayAcctngPeriod = "";
                          }

                          feeFromToInd = feeVO.getToFromInd();
                          if (feeVO.getTransactionTypeCT().equalsIgnoreCase("DPURCH") ||
                              feeVO.getTransactionTypeCT().equalsIgnoreCase("DFACC"))
                          {
                              String feeCurrDateProcessValue = feeVO.getProcessDateTime().substring(0, 10);
                              String feeCurrEffDateValue = feeVO.getEffectiveDate();

                              EDITDate edFeeCurrDateProcValue = new EDITDate(feeCurrDateProcessValue);
                              EDITDate edFeeCurrEffDateValue = new EDITDate(feeCurrEffDateValue);

                              if ((dateType.equalsIgnoreCase("Effective") &&
                                   (edFeeCurrEffDateValue.after(edCurrEffDateValue) || edFeeCurrEffDateValue.equals(edCurrEffDateValue))) ||
                                  (dateType.equalsIgnoreCase("Process") &&
                                   (edFeeCurrDateProcValue.after(edCurrDateProcValue) || edFeeCurrDateProcValue.equals(edCurrDateProcValue)) ))
                              {
                                  if (feePrevDateProcessValue.equals(""))
                                  {
                                      feePrevDateProcessValue = feeCurrDateProcessValue;
                                  }

                                  if (feePrevEffDateValue.equals(""))
                                  {
                                      feePrevEffDateValue = feeCurrEffDateValue;
                                  }

                                  UnitValuesVO[] feeUnitValuesVO = engineLookup.
                                          getUnitValuesByFilteredFundIdDateChargeCode(Long.parseLong(filteredFundFK),
                                                                                      feeCurrEffDateValue,
                                                                                      pricingDirection,
                                                                                      chargeCodeFKsToUse[0]);
                                  String feeCurrValuationDateValue = "";
                                  if (feeUnitValuesVO != null && feeUnitValuesVO.length > 0)
                                  {
                                      feeCurrValuationDateValue = feeUnitValuesVO[0].getEffectiveDate();
                                  }
                                  if (feePrevValuationDateValue.equals(""))
                                  {
                                      feePrevValuationDateValue = feeCurrValuationDateValue;
                                  }

                                  if ((!feeCurrDateProcessValue.equals(feePrevDateProcessValue) ||
                                       !feeCurrEffDateValue.equals(feePrevEffDateValue)) &&
                                      (!total.isEQ("0")))
                                  {
                                      EDITDate displayDate = null;

                                      if (dateType.equalsIgnoreCase("Effective"))
                                      {
                                          displayDate = new EDITDate(feePrevEffDateValue);
                                      }
                                      else
                                      {
                                          displayDate = new EDITDate(feePrevDateProcessValue);
                                      }

                                      displayProcessDate = DateTimeUtil.formatEDITDateAsMMDDYYYY(displayDate).substring(0, 7); // only want 2 digit year

                                      displayDate = new EDITDate(feePrevValuationDateValue);
                                      displayValuationDate = DateTimeUtil.formatEDITDateAsMMDDYYYY(displayDate).substring(0, 7); // only want 2 digit year

                                      if (!tableHeaderCreated)
                                      {
                                          tableHeaderCreated = true;
                                          recordsOutputted = true;
  %>
<br>
<div class="pagebreak" width="100%" align="left">
<table width="100%" cellpadding="2" cellspacing="4">
    <tr>
      <td align="left" colspan="18">
        <b>Accrual Transfers (To)/From Fund</b>
      </td>
    </tr>
    <tr>
      <td align="left" colspan="18"><hr size="1" color="black" noshade></hr></td>
    </tr>
  </table>
  <table border="1" borderstyle="solid" width="100%" cellpadding="0" cellspacing="1">
    <tr>
      <td align="center" width="5%"><font size="1">Accounting Period</font></td>
      <td align="center" width="5%"><font size="1">Date Process</font></td>
      <td align="center" width="5%"><font size="1">Valuation Date</font></td>
      <td align="center" width="5%"><font size="1">Total</font></td>
      <td align="center" width="5%"><font size="1">Reallocation</font></td>
      <td align="center" width="5%"><font size="1">Net Premium</font></td>
      <td align="center" width="5%"><font size="1">Advance Transfers</font></td>
      <td align="center" width="5%"><font size="1">Surrender</font></td>
      <td align="center" width="5%"><font size="1">RRD</font></td>
      <td align="center" width="5%"><font size="1">Transfer Mortality Reserve</font></td>
      <td align="center" width="5%"><font size="1">Admin Fee</font></td>
      <td align="center" width="5%"><font size="1">COI</font></td>
      <td align="center" width="5%"><font size="1">M&E Fee</font></td>
      <td align="center" width="5%"><font size="1">Management Fee</font></td>
      <td align="center" width="5%"><font size="1">Advisory Fee</font></td>
      <td align="center" width="5%"><font size="1">Performance Fee</font></td>
      <td align="center" width="5%"><font size="1">SVA Fee</font></td>
      <td align="center" width="5%"><font size="1">Gain/(Loss)</font></td>
    </tr>
  <%
                                      }
  %>
    <tr>
      <td align="right" width="6%"><font size="1"><%= displayAcctngPeriod %></font></td>
      <td align="right" width="5%"><font size="1"><%= displayProcessDate %></font></td>
      <td align="right" width="5%"><font size="1"><%= displayValuationDate %></font></td>
<%
      if (total.isLT(EDITBigDecimal.EDIT_DECIMAL_ZILCH_STR))
      {
          total = total.multiplyEditBigDecimal("-1");
  %>
      <td align="right" width="5%"><font size="1">(<script>document.write(formatFee(<%= total %>))</script>)</font></td>
  <%
      }
      else
      {
  %>
      <td align="right" width="5%"><font size="1"><script>document.write(formatFee(<%= total %>))</script></font></td>
  <%
      }
                                      
      for (int l = 0; l < fees.length; l++)
      {
          if (fees[l].isLT(EDITBigDecimal.EDIT_DECIMAL_ZILCH_STR))
          {
              fees[l] = fees[l].multiplyEditBigDecimal("-1");
  %>
      <td align="right" width="5%"><font size="1">(<script>document.write(formatFee(<%= fees[l] %>))</script>)</font></td>
  <%
          }
          else
          {
  %>
      <td align="right" width="5%"><font size="1"><script>document.write(formatFee(<%= fees[l] %>))</script></font></td>
  <%
          }
      }
  %>
      </tr>
  <%
                                      feePrevDateProcessValue = feeCurrDateProcessValue;
                                      feePrevEffDateValue = feeCurrEffDateValue;
                                      feePrevValuationDateValue = feeCurrValuationDateValue;

                                      for (int l = 0; l < fees.length; l++)
                                      {
                                          fees[l] = new EDITBigDecimal();
                                      }

                                      total = new EDITBigDecimal("0", 7);
                                  }

                                  FeeDescriptionVO feeDescriptionVO = fee[k].getFeeDescription();

                                  if (feeDescriptionVO != null)
                                  {
                                      for (int l = 0; l < feeTypes.length; l++)
                                      {
                                          if (feeDescriptionVO.getFeeTypeCT().equalsIgnoreCase(feeTypes[l]))
                                          {
                                              if ((feeFromToInd != null && feeFromToInd.equalsIgnoreCase("F")) ||
                                                  (feeDescriptionVO.getFeeTypeCT().equalsIgnoreCase(GAIN_LOSS_FEE) &&
                                                   new EDITBigDecimal(feeVO.getTrxAmount()).isGT("0")))
                                              {
                                                  fees[l] = fees[l].addEditBigDecimal(feeVO.getTrxAmount());
                                                  allAccrualFees[l] = allAccrualFees[l].addEditBigDecimal(feeVO.getTrxAmount());

                                                  total = total.addEditBigDecimal(feeVO.getTrxAmount());
                                                  allTransfersTotal = allTransfersTotal.addEditBigDecimal(feeVO.getTrxAmount());
                                                  accrualTransferTotal = accrualTransferTotal.addEditBigDecimal(feeVO.getTrxAmount());
                                              }
                                              else
                                              {
                                                  fees[l] = fees[l].subtractEditBigDecimal(feeVO.getTrxAmount());
                                                  allAccrualFees[l] = allAccrualFees[l].subtractEditBigDecimal(feeVO.getTrxAmount());

                                                  total = total.subtractEditBigDecimal(feeVO.getTrxAmount());
                                                  allTransfersTotal = allTransfersTotal.subtractEditBigDecimal(feeVO.getTrxAmount());
                                                  accrualTransferTotal = accrualTransferTotal.subtractEditBigDecimal(feeVO.getTrxAmount());
                                              }
                                          }
                                      }
                                  }
                              }
                          }
                      }

                      if (((dateType.equalsIgnoreCase("Effective") &&
                            !feePrevEffDateValue.equals("")) ||
                           (dateType.equalsIgnoreCase("Process") &&
                            !feePrevDateProcessValue.equals(""))) &&
                          !total.isEQ("0"))
                      {
                          EDITDate displayDate = null;

                          if (dateType.equalsIgnoreCase("Effective"))
                          {
                              displayDate = new EDITDate(feePrevEffDateValue);
                          }
                          else
                          {
                              displayDate = new EDITDate(feePrevDateProcessValue);
                          }

                          displayProcessDate = DateTimeUtil.formatEDITDateAsMMDDYYYY(displayDate).substring(0, 7); // only want 2 digit year

                          displayDate = new EDITDate(feePrevValuationDateValue);
                          displayValuationDate = DateTimeUtil.formatEDITDateAsMMDDYYYY(displayDate).substring(0, 7); // only want 2 digit year

                          if (!tableHeaderCreated)
                          {
                              tableHeaderCreated = true;
                              recordsOutputted = true;
  %>
<br>
<div class="pagebreak" width="100%" align="left">
<table width="100%" cellpadding="2" cellspacing="4">
    <tr>
      <td align="left" colspan="18">
        <b>Accrual Transfers (To)/From Fund</b>
      </td>
    </tr>
    <tr>
      <td align="left" colspan="18"><hr size="1" color="black" noshade></hr></td>
    </tr>
  </table>
  <table border="1" borderstyle="solid" width="100%" cellpadding="0" cellspacing="1">
    <tr>
      <td align="center" width="5%"><font size="1">Accounting Period</font></td>
      <td align="center" width="5%"><font size="1">Date Process</font></td>
      <td align="center" width="5%"><font size="1">Valuation Date</font></td>
      <td align="center" width="5%"><font size="1">Total</font></td>
      <td align="center" width="5%"><font size="1">Reallocation</font></td>
      <td align="center" width="5%"><font size="1">Net Premium</font></td>
      <td align="center" width="5%"><font size="1">Advance Transfers</font></td>
      <td align="center" width="5%"><font size="1">Surrender</font></td>
      <td align="center" width="5%"><font size="1">RRD</font></td>
      <td align="center" width="5%"><font size="1">Transfer Mortality Reserve</font></td>
      <td align="center" width="5%"><font size="1">Admin Fee</font></td>
      <td align="center" width="5%"><font size="1">COI</font></td>
      <td align="center" width="5%"><font size="1">M&E Fee</font></td>
      <td align="center" width="5%"><font size="1">Management Fee</font></td>
      <td align="center" width="5%"><font size="1">Advisory Fee</font></td>
      <td align="center" width="5%"><font size="1">Performance Fee</font></td>
      <td align="center" width="5%"><font size="1">SVA Fee</font></td>
      <td align="center" width="5%"><font size="1">Gain/(Loss)</font></td>
    </tr>
  <%
                          }
  %>
    <tr>
      <td align="right" width="6%"><font size="1"><%= displayAcctngPeriod %></font></td>
      <td align="right" width="5%"><font size="1"><%= displayProcessDate %></font></td>
      <td align="right" width="5%"><font size="1"><%= displayValuationDate %></font></td>
  <%
      if (total.isLT(EDITBigDecimal.EDIT_DECIMAL_ZILCH_STR))
      {
          total = total.multiplyEditBigDecimal("-1");
  %>
      <td align="right" width="5%"><font size="1">(<script>document.write(formatFee(<%= total %>))</script>)</font></td>
  <%
      }
      else
      {
  %>
      <td align="right" width="5%"><font size="1"><script>document.write(formatFee(<%= total %>))</script></font></td>
  <%
      }
                          
      for (int l = 0; l < fees.length; l++)
      {
          if (fees[l].isLT(EDITBigDecimal.EDIT_DECIMAL_ZILCH_STR))
          {
              fees[l] = fees[l].multiplyEditBigDecimal("-1");
  %>
      <td align="right" width="5%"><font size="1">(<script>document.write(formatFee(<%= fees[l] %>))</script>)</font></td>
  <%
          }
          else
          {
  %>
      <td align="right" width="5%"><font size="1"><script>document.write(formatFee(<%= fees[l] %>))</script></font></td>
  <%
          }
      }
  %>
    </tr>
  <%
                          if (tableHeaderCreated)
                          {
  %>
    <tr>
      <td align="right" width="6%"><font size="1">All Date Total</font></td>
      <td align="right" width="5%"><font size="1">&nbsp;</font></td>
      <td align="right" width="5%"><font size="1">&nbsp;</font></td>
  <%
      if (accrualTransferTotal.isLT(EDITBigDecimal.EDIT_DECIMAL_ZILCH_STR))
      {
          accrualTransferTotal = accrualTransferTotal.multiplyEditBigDecimal("-1");
  %>
      <td align="right" width="5%"><font size="1">(<script>document.write(formatFee(<%= accrualTransferTotal %>))</script>)</font></td>
  <%
      }
      else
      {
  %>
      <td align="right" width="5%"><font size="1"><script>document.write(formatFee(<%= accrualTransferTotal %>))</script></font></td>
  <%
      }
                          
      for (int l = 0; l < allAccrualFees.length; l++)
      {
          if (allAccrualFees[l].isLT(EDITBigDecimal.EDIT_DECIMAL_ZILCH_STR))
          {
  %>
      <td align="right" width="5%"><font size="1">(<script>document.write(formatFee(<%= allAccrualFees[l].multiplyEditBigDecimal("-1") %>))</script>)</font></td>
  <%
          }
          else
          {
  %>
      <td align="right" width="5%"><font size="1"><script>document.write(formatFee(<%= allAccrualFees[l] %>))</script></font></td>
  <%
          }
      }
  %>
    </tr>
    <tr>
      <td align="right" width="3%"><font size="1">Current Balance</font></td>
      <td align="right" width="3%"><font size="1">&nbsp;</font></td>
      <td align="right" width="3%"><font size="1">&nbsp;</font></td>
  <%
      if (accrualTransferTotal.isLT(new EDITBigDecimal()))
      {
          currentBalance = cashTransferTotal.addEditBigDecimal(accrualTransferTotal);
      }
      else
      {
          currentBalance = cashTransferTotal.subtractEditBigDecimal(accrualTransferTotal);
      }

      if (currentBalance.isLT(EDITBigDecimal.EDIT_DECIMAL_ZILCH_STR))
      {
          currentBalance = currentBalance.multiplyEditBigDecimal("-1");
  %>
      <td align="right" width="5%"><font size="1">(<script>document.write(formatFee(<%= currentBalance %>))</script>)</font></td>
  <%
      }
      else
      {
  %>
      <td align="right" width="5%"><font size="1"><script>document.write(formatFee(<%= currentBalance %>))</script></font></td>
  <%
      }

      for (int l = 0; l < allAccrualFees.length; l++)
      {
          currentFeeBalance = allCashFees[l].addEditBigDecimal(allAccrualFees[l]);

          if (currentFeeBalance.isLT(EDITBigDecimal.EDIT_DECIMAL_ZILCH_STR))
          {
              currentFeeBalance = currentFeeBalance.multiplyEditBigDecimal("-1");
  %>
      <td align="right" width="5%"><font size="1">(<script>document.write(formatFee(<%= currentFeeBalance %>))</script>)</font></td>
  <%
          }
          else
          {
  %>
      <td align="right" width="5%"><font size="1"><script>document.write(formatFee(<%= currentFeeBalance %>))</script></font></td>
  <%
          }
      }
  %>
    </tr>
  <%
                          }
                      }
                      else
                      {
                          if (tableHeaderCreated)
                          {
  %>
    <tr>
      <td align="right" width="6%"><font size="1">All Date Total</font></td>
      <td align="right" width="5%"><font size="1">&nbsp;</font></td>
      <td align="right" width="5%"><font size="1">&nbsp;</font></td>
  <%
      if (accrualTransferTotal.isLT(EDITBigDecimal.EDIT_DECIMAL_ZILCH_STR))
      {
          accrualTransferTotal = accrualTransferTotal.multiplyEditBigDecimal("-1");
  %>
      <td align="right" width="5%"><font size="1">(<script>document.write(formatFee(<%= accrualTransferTotal %>))</script>)</font></td>
  <%
      }
      else
      {
  %>
      <td align="right" width="5%"><font size="1"><script>document.write(formatFee(<%= accrualTransferTotal %>))</script></font></td>
  <%
      }
                          
      for (int l = 0; l < allAccrualFees.length; l++)
      {
          if (allAccrualFees[l].isLT(EDITBigDecimal.EDIT_DECIMAL_ZILCH_STR))
          {
  %>
      <td align="right" width="5%"><font size="1">(<script>document.write(formatFee(<%= allAccrualFees[l].multiplyEditBigDecimal("-1") %>))</script>)</font></td>
  <%
          }
          else
          {
  %>
      <td align="right" width="5%"><font size="1"><script>document.write(formatFee(<%= allAccrualFees[l] %>))</script></font></td>
  <%
          }
      }
  %>
    </tr>
    <tr>
      <td align="right" width="3%"><font size="1">Current Balance</font></td>
      <td align="right" width="3%"><font size="1">&nbsp;</font></td>
      <td align="right" width="3%"><font size="1">&nbsp;</font></td>
  <%
      if (accrualTransferTotal.isLT(new EDITBigDecimal()))
      {
          currentBalance = cashTransferTotal.addEditBigDecimal(accrualTransferTotal);
      }
      else
      {
          currentBalance = cashTransferTotal.subtractEditBigDecimal(accrualTransferTotal);
      }

      if (currentBalance.isLT(EDITBigDecimal.EDIT_DECIMAL_ZILCH_STR))
      {
          currentBalance = currentBalance.multiplyEditBigDecimal("-1");
  %>
      <td align="right" width="5%"><font size="1">(<script>document.write(formatFee(<%= currentBalance %>))</script>)</font></td>
  <%
      }
      else
      {
  %>
      <td align="right" width="5%"><font size="1"><script>document.write(formatFee(<%= currentBalance %>))</script></font></td>
  <%
      }

      for (int l = 0; l < allAccrualFees.length; l++)
      {
          currentFeeBalance = allCashFees[l].addEditBigDecimal(allAccrualFees[l]);

          if (currentFeeBalance.isLT(EDITBigDecimal.EDIT_DECIMAL_ZILCH_STR))
          {
              currentFeeBalance = currentFeeBalance.multiplyEditBigDecimal("-1");
  %>
      <td align="right" width="5%"><font size="1">(<script>document.write(formatFee(<%= currentFeeBalance %>))</script>)</font></td>
  <%
          }
          else
          {
  %>
      <td align="right" width="5%"><font size="1"><script>document.write(formatFee(<%= currentFeeBalance %>))</script></font></td>
  <%
          }
      }
  %>
    </tr>
  <%
                          }
                      }
                  }
                  else
                  {
                      if (tableHeaderCreated)
                      {
  %>
    <tr>
      <td align="right" width="6%"><font size="1">All Date Total</font></td>
      <td align="right" width="5%"><font size="1">&nbsp;</font></td>
      <td align="right" width="5%"><font size="1">&nbsp;</font></td>
  <%
      if (accrualTransferTotal.isLT(EDITBigDecimal.EDIT_DECIMAL_ZILCH_STR))
      {
          accrualTransferTotal = accrualTransferTotal.multiplyEditBigDecimal("-1");
  %>
      <td align="right" width="5%"><font size="1">(<script>document.write(formatFee(<%= accrualTransferTotal %>))</script>)</font></td>
  <%
      }
      else
      {
  %>
      <td align="right" width="5%"><font size="1"><script>document.write(formatFee(<%= accrualTransferTotal %>))</script></font></td>
  <%
      }

      for (int l = 0; l < allAccrualFees.length; l++)
      {
          if (allAccrualFees[l].isLT(EDITBigDecimal.EDIT_DECIMAL_ZILCH_STR))
          {
  %>
      <td align="right" width="5%"><font size="1">(<script>document.write(formatFee(<%= allAccrualFees[l].multiplyEditBigDecimal("-1") %>))</script>)</font></td>
  <%
          }
          else
          {
  %>
      <td align="right" width="5%"><font size="1"><script>document.write(formatFee(<%= allAccrualFees[l] %>))</script></font></td>
  <%
          }
      }
  %>
    </tr>
    <tr>
      <td align="right" width="3%"><font size="1">Current Balance</font></td>
      <td align="right" width="3%"><font size="1">&nbsp;</font></td>
      <td align="right" width="3%"><font size="1">&nbsp;</font></td>
  <%
      if (accrualTransferTotal.isLT(new EDITBigDecimal()))
      {
          currentBalance = cashTransferTotal.addEditBigDecimal(accrualTransferTotal);
      }
      else
      {
          currentBalance = cashTransferTotal.subtractEditBigDecimal(accrualTransferTotal);
      }

      if (currentBalance.isLT(EDITBigDecimal.EDIT_DECIMAL_ZILCH_STR))
      {
          currentBalance = currentBalance.multiplyEditBigDecimal("-1");
  %>
      <td align="right" width="5%"><font size="1">(<script>document.write(formatFee(<%= currentBalance %>))</script>)</font></td>
  <%
      }
      else
      {
  %>
      <td align="right" width="5%"><font size="1"><script>document.write(formatFee(<%= currentBalance %>))</script></font></td>
  <%
      }

      for (int l = 0; l < allAccrualFees.length; l++)
      {
          currentFeeBalance = allCashFees[l].addEditBigDecimal(allAccrualFees[l]);

          if (currentFeeBalance.isLT(EDITBigDecimal.EDIT_DECIMAL_ZILCH_STR))
          {
              currentFeeBalance = currentFeeBalance.multiplyEditBigDecimal("-1");
  %>
      <td align="right" width="5%"><font size="1">(<script>document.write(formatFee(<%= currentFeeBalance %>))</script>)</font></td>
  <%
          }
          else
          {
  %>
      <td align="right" width="5%"><font size="1"><script>document.write(formatFee(<%= currentFeeBalance %>))</script></font></td>
  <%
          }
      }
  %>
    </tr>
  <%
                  }

  %>
  </table>
</div>
  <%
                  }
              }
              else
              {
                  if (fee != null)
                  {
                      tableHeaderCreated = false;

                      EDITDate edCurrDateProcValue = null;
                      EDITDate edCurrEffDateValue = null;
                      if (!prevDateProcessValue.equals(""))
                      {
                        edCurrDateProcValue = new EDITDate(prevDateProcessValue);
                      }

                      if (!prevEffDateValue.equals(""))
                      {
                          edCurrEffDateValue = new EDITDate(prevEffDateValue);
                      }

                      feePrevDateProcessValue = "";
                      feePrevEffDateValue = "";

                      for (int k = 0; k < fee.length; k++)
                      {
                          FeeVO feeVO = (FeeVO) fee[k].getVO();

                          if (feeVO.getReleaseDate() != null)
                          {
                              EDITDate releaseDate = new EDITDate(feeVO.getReleaseDate());
                              displayAcctngPeriod = releaseDate.getMonthName() + "-" + releaseDate.getFormattedYear();
                          }
                          else
                          {
                              displayAcctngPeriod = "";
                          }

                          feeFromToInd = feeVO.getToFromInd();
                          if (feeVO.getTransactionTypeCT().equalsIgnoreCase("DPURCH") ||
                              feeVO.getTransactionTypeCT().equalsIgnoreCase("DFACC"))
                          {
                              String feeCurrDateProcessValue = feeVO.getProcessDateTime().substring(0, 10);
                              String feeCurrEffDateValue = feeVO.getEffectiveDate();

                              EDITDate edFeeCurrDateProcValue = new EDITDate(feeCurrDateProcessValue);
                              EDITDate edFeeCurrEffDateValue = new EDITDate(feeCurrEffDateValue);

                              if ((dateType.equalsIgnoreCase("Effective") &&
                                   (edCurrEffDateValue == null ||
                                    (edFeeCurrEffDateValue.after(edCurrEffDateValue) || edFeeCurrEffDateValue.equals(edCurrEffDateValue)) )) ||
                                  (dateType.equalsIgnoreCase("Process") &&
                                   (edCurrDateProcValue == null ||
                                    (edFeeCurrDateProcValue.after(edCurrDateProcValue) || edFeeCurrDateProcValue.equals(edCurrDateProcValue)) )))
                               {
                                  if (feePrevDateProcessValue.equals(""))
                                  {
                                      feePrevDateProcessValue = feeCurrDateProcessValue;
                                  }

                                  if (feePrevEffDateValue.equals(""))
                                  {
                                      feePrevEffDateValue = feeCurrEffDateValue;
                                  }

                                  UnitValuesVO[] unitValuesVO = engineLookup.
                                          getUnitValuesByFilteredFundIdDateChargeCode(Long.parseLong(filteredFundFK),
                                                                                      feeCurrEffDateValue,
                                                                                      pricingDirection,
                                                                                      chargeCodeFKsToUse[0]);
                                  String feeCurrValuationDateValue = "";
                                  if (unitValuesVO != null && unitValuesVO.length > 0)
                                  {
                                      feeCurrValuationDateValue = unitValuesVO[0].getEffectiveDate();
                                  }
                                  if (feePrevValuationDateValue.equals(""))
                                  {
                                      feePrevValuationDateValue = feeCurrValuationDateValue;
                                  }

                                  if ((!feeCurrDateProcessValue.equals(feePrevDateProcessValue) ||
                                       !feeCurrEffDateValue.equals(feePrevEffDateValue)) &&
                                      (!total.isEQ("0")))
                                  {
                                      EDITDate displayDate = null;

                                      if (dateType.equalsIgnoreCase("Effective"))
                                      {
                                          displayDate = new EDITDate(feePrevEffDateValue);
                                      }
                                      else
                                      {
                                          displayDate = new EDITDate(feePrevDateProcessValue);
                                      }

                                      displayProcessDate = DateTimeUtil.formatEDITDateAsMMDDYYYY(displayDate).substring(0, 7); // only want 2 digit year

                                      displayDate = new EDITDate(feePrevValuationDateValue);
                                      displayValuationDate = DateTimeUtil.formatEDITDateAsMMDDYYYY(displayDate).substring(0, 7); // only want 2 digit year

                                      if (!tableHeaderCreated)
                                      {
                                          tableHeaderCreated = true;
                                          recordsOutputted = true;
  %>

<div class="pagebreak" width="100%" align="left">
  <table width="100%" cellpadding="2" cellspacing="4">
    <tr>
      <td align="left" colspan="18">
        <b>Accrual Transfers (To)/From Fund</b>
      </td>
    </tr>
    <tr>
      <td align="left" colspan="18"><hr size="1" color="black" noshade></hr></td>
    </tr>
  </table>
  <table border="1" borderstyle="solid" width="100%" cellpadding="0" cellspacing="1">
    <tr>
      <td align="center" width="5%"><font size="1">Accounting Period</font></td>
      <td align="center" width="5%"><font size="1">Date Process</font></td>
      <td align="center" width="5%"><font size="1">Valuation Date</font></td>
      <td align="center" width="5%"><font size="1">Total</font></td>
      <td align="center" width="5%"><font size="1">Reallocation</font></td>
      <td align="center" width="5%"><font size="1">Net Premium</font></td>
      <td align="center" width="5%"><font size="1">Advance Transfers</font></td>
      <td align="center" width="5%"><font size="1">Surrender</font></td>
      <td align="center" width="5%"><font size="1">RRD</font></td>
      <td align="center" width="5%"><font size="1">Transfer Mortality Reserve</font></td>
      <td align="center" width="5%"><font size="1">Admin Fee</font></td>
      <td align="center" width="5%"><font size="1">COI</font></td>
      <td align="center" width="5%"><font size="1">M&E Fee</font></td>
      <td align="center" width="5%"><font size="1">Management Fee</font></td>
      <td align="center" width="5%"><font size="1">Advisory Fee</font></td>
      <td align="center" width="5%"><font size="1">Performance Fee</font></td>
      <td align="center" width="5%"><font size="1">SVA Fee</font></td>
      <td align="center" width="5%"><font size="1">Gain/(Loss)</font></td>
    </tr>
  <%
                                      }
  %>
    <tr>
      <td align="right" width="6%"><font size="1"><%= displayAcctngPeriod %></font></td>
      <td align="right" width="5%"><font size="1"><%= displayProcessDate %></font></td>
      <td align="right" width="5%"><font size="1"><%= displayValuationDate %></font></td>
      <td align="right" width="6%"><font size="1"><script>document.write(formatFee(<%= total %>))</script></font></td>
  <%
                      for (int l = 0; l < fees.length; l++)
                      {
  %>
      <td align="right" width="6%"><font size="1"><script>document.write(formatFee(<%= fees[l] %>))</script></font></td>
  <%
                      }
  %>
    </tr>
  <%
                                      feePrevDateProcessValue = feeCurrDateProcessValue;
                                      feePrevEffDateValue = feeCurrEffDateValue;

                                      for (int l = 0; l < fees.length; l++)
                                      {
                                          fees[l] = new EDITBigDecimal();
                                      }

                                      total = new EDITBigDecimal("0", 7);
                                  }

                                  FeeDescriptionVO feeDescriptionVO = fee[k].getFeeDescription();

                                  if (feeDescriptionVO != null)
                                  {
                                      for (int l = 0; l < feeTypes.length; l++)
                                      {
                                          if (feeDescriptionVO.getFeeTypeCT().equalsIgnoreCase(feeTypes[l]))
                                          {
                                              if ((feeFromToInd != null && feeFromToInd.equalsIgnoreCase("F")) ||
                                                  (feeDescriptionVO.getFeeTypeCT().equalsIgnoreCase(GAIN_LOSS_FEE) &&
                                                   new EDITBigDecimal(feeVO.getTrxAmount()).isGT("0")))
                                              {
                                                  fees[l] = fees[l].addEditBigDecimal(feeVO.getTrxAmount());
                                                  allAccrualFees[l] = allAccrualFees[l].addEditBigDecimal(feeVO.getTrxAmount());

                                                  total = total.addEditBigDecimal(feeVO.getTrxAmount());
                                                  allTransfersTotal = allTransfersTotal.addEditBigDecimal(feeVO.getTrxAmount());
                                              }
                                              else
                                              {
                                                  fees[l] = fees[l].subtractEditBigDecimal(feeVO.getTrxAmount());
                                                  allAccrualFees[l] = allAccrualFees[l].subtractEditBigDecimal(feeVO.getTrxAmount());

                                                  total = total.subtractEditBigDecimal(feeVO.getTrxAmount());
                                                  allTransfersTotal = allTransfersTotal.subtractEditBigDecimal(feeVO.getTrxAmount());
                                              }
                                          }
                                      }
                                  }
                              }
                          }
                      }

                      if (((dateType.equalsIgnoreCase("Effective") &&
                            !feePrevEffDateValue.equals("")) ||
                           (dateType.equalsIgnoreCase("Process") &&
                            !feePrevDateProcessValue.equals(""))) &&
                          !total.isEQ("0"))
                      {
                          EDITDate displayDate = null;

                          if (dateType.equalsIgnoreCase("Effective"))
                          {
                              displayDate = new EDITDate(feePrevEffDateValue);
                          }
                          else
                          {
                              displayDate = new EDITDate(feePrevDateProcessValue);
                          }

                          displayProcessDate = DateTimeUtil.formatEDITDateAsMMDDYYYY(displayDate).substring(0, 7); // only want 2 digit year

                          displayDate = new EDITDate(feePrevValuationDateValue);
                          displayValuationDate = DateTimeUtil.formatEDITDateAsMMDDYYYY(displayDate).substring(0, 7); // only want 2 digit year
                          
                          if (!tableHeaderCreated)
                          {
                              tableHeaderCreated = true;
                              recordsOutputted = true;
  %>
<div class="pagebreak" width="100%" align="left">
  <table width="100%" cellpadding="2" cellspacing="4">
    <tr>
      <td align="left" colspan="18">
        <b>Accrual Transfers (To)/From Fund</b>
      </td>
    </tr>
    <tr>
      <td align="left" colspan="18"><hr size="1" color="black" noshade></hr></td>
    </tr>
  </table>
  <table border="1" borderstyle="solid" width="100%" cellpadding="0" cellspacing="1">
    <tr>
      <td align="center" width="5%"><font size="1">Accounting Period</font></td>
      <td align="center" width="5%"><font size="1">Date Process</font></td>
      <td align="center" width="5%"><font size="1">Valuation Date</font></td>
      <td align="center" width="5%"><font size="1">Total</font></td>
      <td align="center" width="5%"><font size="1">Reallocation</font></td>
      <td align="center" width="5%"><font size="1">Net Premium</font></td>
      <td align="center" width="5%"><font size="1">Advance Transfers</font></td>
      <td align="center" width="5%"><font size="1">Surrender</font></td>
      <td align="center" width="5%"><font size="1">RRD</font></td>
      <td align="center" width="5%"><font size="1">Transfer Mortality Reserve</font></td>
      <td align="center" width="5%"><font size="1">Admin Fee</font></td>
      <td align="center" width="5%"><font size="1">COI</font></td>
      <td align="center" width="5%"><font size="1">M&E Fee</font></td>
      <td align="center" width="5%"><font size="1">Management Fee</font></td>
      <td align="center" width="5%"><font size="1">Advisory Fee</font></td>
      <td align="center" width="5%"><font size="1">Performance Fee</font></td>
      <td align="center" width="5%"><font size="1">SVA Fee</font></td>
      <td align="center" width="5%"><font size="1">Gain/(Loss)</font></td>
    </tr>
  <%
                          }
  %>
    <tr>
      <td align="right" width="6%"><font size="1"><%= displayAcctngPeriod %></font></td>
      <td align="right" width="5%"><font size="1"><%= displayProcessDate %></font></td>
      <td align="right" width="5%"><font size="1"><%= displayValuationDate %></font></td>
  <%
      if (total.isLT(EDITBigDecimal.EDIT_DECIMAL_ZILCH_STR))
      {
          total = total.multiplyEditBigDecimal("-1");
  %>
      <td align="right" width="5%"><font size="1">(<script>document.write(formatFee(<%= total %>))</script>)</font></td>
  <%
      }
      else
      {
  %>
      <td align="right" width="5%"><font size="1"><script>document.write(formatFee(<%= total %>))</script></font></td>
  <%
      }

      for (int l = 0; l < fees.length; l++)
      {
          if (fees[l].isLT(EDITBigDecimal.EDIT_DECIMAL_ZILCH_STR))
          {
              fees[l] = fees[l].multiplyEditBigDecimal("-1");
  %>
      <td align="right" width="5%"><font size="1">(<script>document.write(formatFee(<%= fees[l] %>))</script>)</font></td>
  <%
          }
          else
          {
  %>
      <td align="right" width="5%"><font size="1"><script>document.write(formatFee(<%= fees[l] %>))</script></font></td>
  <%
          }
      }
  %>
    </tr>
  <%
                          if (tableHeaderCreated)
                          {
  %>
    <tr>
      <td align="right" width="6%"><font size="1">All Date Total</font></td>
      <td align="right" width="5%"><font size="1">&nbsp;</font></td>
      <td align="right" width="5%"><font size="1">&nbsp;</font></td>
  <%
      if (accrualTransferTotal.isLT(EDITBigDecimal.EDIT_DECIMAL_ZILCH_STR))
      {
          accrualTransferTotal = accrualTransferTotal.multiplyEditBigDecimal("-1");
  %>
      <td align="right" width="5%"><font size="1">(<script>document.write(formatFee(<%= accrualTransferTotal %>))</script>)</font></td>
  <%
      }
      else
      {
  %>
      <td align="right" width="5%"><font size="1"><script>document.write(formatFee(<%= accrualTransferTotal %>))</script></font></td>
  <%
      }

      for (int l = 0; l < allAccrualFees.length; l++)
      {
          if (allAccrualFees[l].isLT(EDITBigDecimal.EDIT_DECIMAL_ZILCH_STR))
          {
  %>
      <td align="right" width="5%"><font size="1">(<script>document.write(formatFee(<%= allAccrualFees[l].multiplyEditBigDecimal("-1") %>))</script>)</font></td>
  <%
          }
          else
          {
  %>
      <td align="right" width="5%"><font size="1"><script>document.write(formatFee(<%= allAccrualFees[l] %>))</script></font></td>
  <%
          }
      }
  %>
    </tr>
    <tr>
      <td align="right" width="3%"><font size="1">Current Balance</font></td>
      <td align="right" width="3%"><font size="1">&nbsp;</font></td>
      <td align="right" width="3%"><font size="1">&nbsp;</font></td>
  <%
      if (accrualTransferTotal.isLT(new EDITBigDecimal()))
      {
          currentBalance = cashTransferTotal.addEditBigDecimal(accrualTransferTotal);
      }
      else
      {
          currentBalance = cashTransferTotal.subtractEditBigDecimal(accrualTransferTotal);
      }

      if (currentBalance.isLT(EDITBigDecimal.EDIT_DECIMAL_ZILCH_STR))
      {
          currentBalance = currentBalance.multiplyEditBigDecimal("-1");
  %>
      <td align="right" width="5%"><font size="1">(<script>document.write(formatFee(<%= currentBalance %>))</script>)</font></td>
  <%
      }
      else
      {
  %>
      <td align="right" width="5%"><font size="1"><script>document.write(formatFee(<%= currentBalance %>))</script></font></td>
  <%
      }

      for (int l = 0; l < allAccrualFees.length; l++)
      {
          currentFeeBalance = allCashFees[l].addEditBigDecimal(allAccrualFees[l]);

          if (currentFeeBalance.isLT(EDITBigDecimal.EDIT_DECIMAL_ZILCH_STR))
          {
              currentFeeBalance = currentFeeBalance.multiplyEditBigDecimal("-1");
  %>
      <td align="right" width="5%"><font size="1">(<script>document.write(formatFee(<%= currentFeeBalance %>))</script>)</font></td>
  <%
          }
          else
          {
  %>
      <td align="right" width="5%"><font size="1"><script>document.write(formatFee(<%= currentFeeBalance %>))</script></font></td>
  <%
          }
      }
  %>
    </tr>
  <%
                          }
                      }
                      else
                      {
                          if (tableHeaderCreated)
                          {
  %>
    <tr>
      <td align="right" width="6%"><font size="1">All Date Total</font></td>
      <td align="right" width="5%"><font size="1">&nbsp;</font></td>
      <td align="right" width="5%"><font size="1">&nbsp;</font></td>
  <%
      if (accrualTransferTotal.isLT(EDITBigDecimal.EDIT_DECIMAL_ZILCH_STR))
      {
          accrualTransferTotal = accrualTransferTotal.multiplyEditBigDecimal("-1");
  %>
      <td align="right" width="5%"><font size="1">(<script>document.write(formatFee(<%= accrualTransferTotal %>))</script>)</font></td>
  <%
      }
      else
      {
  %>
      <td align="right" width="5%"><font size="1"><script>document.write(formatFee(<%= accrualTransferTotal %>))</script></font></td>
  <%
      }

      for (int l = 0; l < allAccrualFees.length; l++)
      {
          if (allAccrualFees[l].isLT(EDITBigDecimal.EDIT_DECIMAL_ZILCH_STR))
          {
  %>
      <td align="right" width="5%"><font size="1">(<script>document.write(formatFee(<%= allAccrualFees[l].multiplyEditBigDecimal("-1") %>))</script>)</font></td>
  <%
          }
          else
          {
  %>
      <td align="right" width="5%"><font size="1"><script>document.write(formatFee(<%= allAccrualFees[l] %>))</script></font></td>
  <%
          }
      }
  %>
    </tr>
    <tr>
      <td align="right" width="3%"><font size="1">Current Balance</font></td>
      <td align="right" width="3%"><font size="1">&nbsp;</font></td>
      <td align="right" width="3%"><font size="1">&nbsp;</font></td>
  <%
      if (accrualTransferTotal.isLT(new EDITBigDecimal()))
      {
          currentBalance = cashTransferTotal.addEditBigDecimal(accrualTransferTotal);
      }
      else
      {
          currentBalance = cashTransferTotal.subtractEditBigDecimal(accrualTransferTotal);
      }

      if (currentBalance.isLT(EDITBigDecimal.EDIT_DECIMAL_ZILCH_STR))
      {
          currentBalance = currentBalance.multiplyEditBigDecimal("-1");
  %>
      <td align="right" width="5%"><font size="1">(<script>document.write(formatFee(<%= currentBalance %>))</script>)</font></td>
  <%
      }
      else
      {
  %>
      <td align="right" width="5%"><font size="1"><script>document.write(formatFee(<%= currentBalance %>))</script></font></td>
  <%
      }

      for (int l = 0; l < allAccrualFees.length; l++)
      {
          currentFeeBalance = allCashFees[l].addEditBigDecimal(allAccrualFees[l]);

          if (currentFeeBalance.isLT(EDITBigDecimal.EDIT_DECIMAL_ZILCH_STR))
          {
              currentFeeBalance = currentFeeBalance.multiplyEditBigDecimal("-1");
  %>
      <td align="right" width="5%"><font size="1">(<script>document.write(formatFee(<%= currentFeeBalance %>))</script>)</font></td>
  <%
          }
          else
          {
  %>
      <td align="right" width="5%"><font size="1"><script>document.write(formatFee(<%= currentFeeBalance %>))</script></font></td>
  <%
          }
      }
  %>
    </tr>
  <%
                          }
                      }
                  }
                  else
                  {
                      if (tableHeaderCreated)
                      {
  %>
    <tr>
      <td align="right" width="6%"><font size="1">All Date Total</font></td>
      <td align="right" width="5%"><font size="1">&nbsp;</font></td>
      <td align="right" width="5%"><font size="1">&nbsp;</font></td>
  <%
      if (accrualTransferTotal.isLT(EDITBigDecimal.EDIT_DECIMAL_ZILCH_STR))
      {
          accrualTransferTotal = accrualTransferTotal.multiplyEditBigDecimal("-1");
  %>
      <td align="right" width="5%"><font size="1">(<script>document.write(formatFee(<%= accrualTransferTotal %>))</script>)</font></td>
  <%
      }
      else
      {
  %>
      <td align="right" width="5%"><font size="1"><script>document.write(formatFee(<%= accrualTransferTotal %>))</script></font></td>
  <%
      }

      for (int l = 0; l < allAccrualFees.length; l++)
      {
          if (allAccrualFees[l].isLT(EDITBigDecimal.EDIT_DECIMAL_ZILCH_STR))
          {
  %>
      <td align="right" width="5%"><font size="1">(<script>document.write(formatFee(<%= allAccrualFees[l].multiplyEditBigDecimal("-1") %>))</script>)</font></td>
  <%
          }
          else
          {
  %>
      <td align="right" width="5%"><font size="1"><script>document.write(formatFee(<%= allAccrualFees[l] %>))</script></font></td>
  <%
          }
      }
  %>
    </tr>
    <tr>
      <td align="right" width="3%"><font size="1">Current Balance</font></td>
      <td align="right" width="3%"><font size="1">&nbsp;</font></td>
      <td align="right" width="3%"><font size="1">&nbsp;</font></td>
  <%
      if (accrualTransferTotal.isLT(new EDITBigDecimal()))
      {
          currentBalance = cashTransferTotal.addEditBigDecimal(accrualTransferTotal);
      }
      else
      {
          currentBalance = cashTransferTotal.subtractEditBigDecimal(accrualTransferTotal);
      }

      if (currentBalance.isLT(EDITBigDecimal.EDIT_DECIMAL_ZILCH_STR))
      {
          currentBalance = currentBalance.multiplyEditBigDecimal("-1");
  %>
      <td align="right" width="5%"><font size="1">(<script>document.write(formatFee(<%= currentBalance %>))</script>)</font></td>
  <%
      }
      else
      {
  %>
      <td align="right" width="5%"><font size="1"><script>document.write(formatFee(<%= currentBalance %>))</script></font></td>
  <%
      }

      for (int l = 0; l < allAccrualFees.length; l++)
      {
          currentFeeBalance = allCashFees[l].addEditBigDecimal(allAccrualFees[l]);

          if (currentFeeBalance.isLT(EDITBigDecimal.EDIT_DECIMAL_ZILCH_STR))
          {
              currentFeeBalance = currentFeeBalance.multiplyEditBigDecimal("-1");
  %>
      <td align="right" width="5%"><font size="1">(<script>document.write(formatFee(<%= currentFeeBalance %>))</script>)</font></td>
  <%
          }
          else
          {
  %>
      <td align="right" width="5%"><font size="1"><script>document.write(formatFee(<%= currentFeeBalance %>))</script></font></td>
  <%
          }
      }
  %>
    </tr>
  <%
                      }
                  }
              }
  %>
    </table>
  </div>
  <%
          }

          if (!recordsOutputted)
          {
   %>
 <div width="100%" align="left">
   <table width="100%" cellpadding="2" cellspacing="4">
     <tr>
       <td align="left" colspan="22">
         <b>No Activity For Fund</b>
       </td>
     </tr>
   </table>
 </div>
   <%
          }
      }
   %>

  <h4 align="center">
    Case Manager Review Report - Errored Transactions
  </h4>

<table>
  <%

      if (erroredTrxHT.size() > 0)
      {
          Enumeration errorKeys = erroredTrxHT.keys();
          while (errorKeys.hasMoreElements())
          {
              String contractNumber = (String) errorKeys.nextElement();

              String[] errorTrxInfo = (String[]) erroredTrxHT.get(contractNumber);
  %>
  <tr>
    <td>
      <%= contractNumber %> - <%= errorTrxInfo[0] %>, effective <%= errorTrxInfo[1] %>
    </td>
  </tr>
  <%
          }
      }
      else
      {
  %>
  <tr>
    <td>
      No transactions Errored
    </td>
  </tr>
  <%
      }
  %>
  <tr>
    <td>
      &nbsp;
    </td>
  </tr>
  <tr>
    <td>
      ***** END OF CASE MANAGER REVIEW REPORT *****
    </td>
  </tr>
</table>

</body>
</html>
