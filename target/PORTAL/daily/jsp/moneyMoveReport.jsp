 <!-- ************* JSP Code ************* -->
<%@ page errorPage="/common/jsp/error.jsp" autoFlush="true" %>

<%@ page import="edit.common.vo.*,
                 fission.utility.Util,
                 edit.common.EDITBigDecimal,
                 engine.Fee,
                 java.util.*,
                 edit.common.EDITDate,
                 edit.common.EDITDateTime,
                 edit.services.db.CRUD,
                 edit.services.db.CRUDFactory,
                 edit.services.db.ConnectionFactory,
                 engine.ChargeCode,
                 engine.util.TransformChargeCodes,
                 businesscalendar.BusinessDay,
                 businesscalendar.BusinessCalendar,
                 event.EDITTrx"%>

<%

    TransformChargeCodes transformChargeCodes = new TransformChargeCodes();
    // use this to translate Fund plus ChargeCode to ClientFundNumber.

    String companyName = (String) request.getAttribute("companyName");
    if (companyName == null)
    {
        companyName = "";
    }

    EDITDate date = new EDITDate((String) request.getAttribute("date"));
    String month = date.getFormattedMonth();
    String day = date.getFormattedDay();
    String year = date.getFormattedYear();

    if (day.startsWith("0"))
    {
        day = day.substring(1);
    }

    String displayDate = date.getMonthName() + " " + day + ", " + year;

    EDITDate edParamDate = new EDITDate(year, month, day);

    BusinessDay businessDay = new BusinessCalendar().findNextBusinessDay(edParamDate, 1);

    EDITDate businessDate = businessDay.getBusinessDate();

    String cashMoveMonth = businessDate.getMonthName();
    String cashMoveDay = businessDate.getFormattedDay();
    String cashMoveYear = businessDate.getFormattedYear();

    String cashMoveDate = cashMoveMonth + " " + cashMoveDay + ", " + cashMoveYear;

    Hashtable moneyMoveReportInfo = (Hashtable) request.getAttribute("moneyMoveReportInfo");

    FilteredFundVO[] filteredFundVOs = (FilteredFundVO[]) moneyMoveReportInfo.get("funds");

    Hashtable cashTransfers = (Hashtable) moneyMoveReportInfo.get("cashTransfers");

    Hashtable accrualTransfers = (Hashtable) moneyMoveReportInfo.get("accrualTransfers");

    String[] feeTypes = new String[] {"Withdrawal", "Premium", "Reallocation", "AdvanceTransfer", "Surrender",
                                      "Death", "NewIssues", "TransferMortReserve", "AdminFee", "COI", "GainLoss",
                                      "PerformanceFee", "MEFee", "ManagementFee", "AdvisoryFee", "SVAFee"};

    String[] transactions = new String[] {"Withdrawal", "Net Premium", "Reallocation", "Advance Transfers",
                                          "Surrender", "Due To Death", "Transfer Units due to Eligibility",
                                          "Mortality Reserve", "Admin Fee", "COI", "(Gain)/Loss", "Performance Fee",
                                          "M&E Fee", "Management Fee", "Advisory Fee", "SVA Fee"};

    String GAIN_LOSS_FEE = "GainLoss";
%>

<%!
    private void generateDCASHTrx(String filteredFundFK, 
                                  String feeType, 
                                  EDITBigDecimal feeAmount,
                                  EDITDate date,
                                  long chargeCodeFK)
    {
        CRUD crud = null;

//        String date = tokenizedDate[2] + "/" + tokenizedDate[0] + "/" + tokenizedDate[1];

        if (feeType.equalsIgnoreCase("Net Premium"))
        {
            feeType = "NetPremium";
        }
        else
        {
            feeType = "AdvanceTransfer";
        }

        long feeDescriptionFK = 0;

        engine.business.Lookup engineLookup = new engine.component.LookupComponent();
        FeeDescriptionVO feeDescriptionVO = engineLookup.findFeeDescriptionBy_FilteredFundPK_And_FeeTypeCT(Long.parseLong(filteredFundFK), feeType);
        if (feeDescriptionVO != null)
        {
            feeDescriptionFK = feeDescriptionVO.getFeeDescriptionPK();
        }

        FeeVO feeVO = new FeeVO();
        feeVO.setFeePK(0);
        feeVO.setFilteredFundFK(Long.parseLong(filteredFundFK));
        feeVO.setFeeDescriptionFK(feeDescriptionFK);
        feeVO.setEffectiveDate(date.getFormattedDate());
        feeVO.setProcessDateTime(date.getFormattedDate());
        feeVO.setOriginalProcessDate(date.getFormattedDate());
        feeVO.setReleaseInd("N");
        feeVO.setStatusCT("N");
        feeVO.setAccountingPendingStatus("N");
        feeVO.setTrxAmount(feeAmount.getBigDecimal());
        feeVO.setTransactionTypeCT("DCASH");
        feeVO.setMaintDateTime(new EDITDateTime().getFormattedDateTime());
        feeVO.setOperator("System");
        feeVO.setChargeCodeFK(chargeCodeFK);

        try
        {
            crud = CRUDFactory.getSingleton().getCRUD(ConnectionFactory.EDITSOLUTIONS_POOL);
            crud.createOrUpdateVOInDB(feeVO);
        }
        catch (Exception e)
        {
            System.out.println(e);
            e.printStackTrace();
        }
        finally
        {
            if (crud != null) crud.close();

            crud = null;
        }
    }
%>

<html>
<head>
<title>Money Move Report</title>
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
    Money Move Report
  </h4>
  <table align="left" width="100%" cellpadding="0" cellspacing="0">
  <%
      String prevFundNumberOrClientFundNumber = "";     // ex. 8504
      // SEE  fundNumberOrClientFundNumber BELOW - SIMILAR DEFINITION

      String prevFundName = "";       // ex. MS AIP Hedge Fund

      String fundNumberOrClientFundNumber = "";
      // THIS IS THE REAL FUND NUMBER IF THERE WAS CLIENT FUND NUMBER (CHARGE CODE)
      // OR IT IS THE CLIENT FUND NUMBER

      String filteredFundPK = "";
      long chargeCodeFK = 0;

      Hashtable fundTotalsHT = new Hashtable();

      boolean trxTypeMatchFound = false;

      int i = 0;

      EDITBigDecimal[] transferAmounts = new EDITBigDecimal[16];
      for (i = 0; i < transferAmounts.length; i++)
      {
          transferAmounts[i] = new EDITBigDecimal();
      }

      EDITBigDecimal grandTotalTransfer = new EDITBigDecimal();
      EDITBigDecimal totalFundTransfer = new EDITBigDecimal();
      EDITBigDecimal displayAmount = new EDITBigDecimal();

      // START OF FILTERED FUND VO's for loop
      for (i = 0; i < filteredFundVOs.length; i++)
      {
          FundVO fundVO = (FundVO) filteredFundVOs[i].getParentVO(FundVO.class);
          String fundName = fundVO.getName();

          filteredFundPK = filteredFundVOs[i].getFilteredFundPK() + "";

          String realFundNumber = filteredFundVOs[i].getFundNumber();
          // THE REAL FUND NUMBER IS THE NORMAL FUND NUMBER WITHOUT
          // TRYING TO DO ANYTHING FANCY FOR CLIENT FUND NUMBER

          long[] chargeCodeFKs =
                  ChargeCode.getAllChargeCodePKsIncludingZero(
                          filteredFundVOs[i].getFilteredFundPK());

          // FOR LOOP FOR CHARGECODEFKs BEGINS (long loop!)
          for (int chargeCode_ix = 0; chargeCode_ix < chargeCodeFKs.length; chargeCode_ix++)
          {
              // NOTE: There's a j and k below already! l isn't good to use so
              // try chargeCode_ix instead as the variable.

              chargeCodeFK = chargeCodeFKs[chargeCode_ix];

              String theKeyForMaps =
                        realFundNumber + "_" +
                        filteredFundPK + "_" +
                        chargeCodeFK;

              fundNumberOrClientFundNumber =
                      transformChargeCodes.
                        getClientFundNumberOrRealFundNumber(
                                realFundNumber, chargeCodeFK);

              if (prevFundNumberOrClientFundNumber.equals(""))
              {
                  prevFundNumberOrClientFundNumber = fundNumberOrClientFundNumber;
                  prevFundName = fundName;
              }

              if (!fundNumberOrClientFundNumber.equals(prevFundNumberOrClientFundNumber))
              {
                  if (!totalFundTransfer.isEQ(new EDITBigDecimal()))
                  {
  %>
    <tr>
      <td align="left" colspan="4">
      <%= prevFundNumberOrClientFundNumber %>,&nbsp;<%= prevFundName %>
      </td>
    </tr>
    <tr>
      <td align="left" colspan="4">
      Please transfer the following amounts To/(from) the Separate Account Division noted above.
      </td>
    </tr>
    <tr>
      <td align="left" colspan="4">
      Shares should be redeemed/purchased based on NAVs as of&nbsp;<%= displayDate %>.
      </td>
    </tr>
    <tr>
      <td align="left" colspan="4">
      Cash should be moved on&nbsp;<%= cashMoveDate %>.
      </td>
    </tr>
    <tr>
        <td colspan="4">&nbsp;</td>
    </tr>
    <br>
    <br>
  <%
                      for (int j = 0; j < transactions.length; j++)
                      {
                          if (transferAmounts[j].isLT(new EDITBigDecimal()))
                          {
                              displayAmount = transferAmounts[j].multiplyEditBigDecimal("-1");
  %>
    <tr>
      <td align="left" width="2%">&nbsp;</td>
      <td align="left" width="20%"><%= transactions[j] %></td>
      <td align="right" width="15%">(<script>document.write(formatFee(<%= displayAmount %>))</script>)</td>
      <td align="left" width="50%">&nbsp;</td>
    </tr>
  <%
                          }
                          else
                          {
  %>
    <tr>
      <td align="left" width="2%">&nbsp;</td>
      <td align="left" width="20%"><%= transactions[j] %></td>
      <td align="right" width="15%"><script>document.write(formatFee(<%= transferAmounts[j]%>))</script></td>
      <td align="left" width="50%">&nbsp;</td>
    </tr>
  <%
                          }
                          
                          if ((transactions[j].equalsIgnoreCase("Net Premium") ||
                               transactions[j].equalsIgnoreCase("Advance Transfers")) &&
                               !transferAmounts[j].isEQ(new EDITBigDecimal()))
                          {
                              generateDCASHTrx(
                                      filteredFundPK,
                                      transactions[j],
                                      transferAmounts[j],
                                      date,
                                      chargeCodeFK);
                          }
                      }

                      fundTotalsHT.put(prevFundNumberOrClientFundNumber + "_" + prevFundName, totalFundTransfer);
                      
                      if (totalFundTransfer.isLT(new EDITBigDecimal()))
                      {
                          displayAmount = totalFundTransfer.multiplyEditBigDecimal("-1");
  %>
    <tr>
      <td align="left" colspan="3" width="37%"><hr size="1" color="black" noshade></hr></td>
      <td align="left" width="50%">&nbsp;</td>
    </tr>
    <tr>
      <td align="left" width="2%">&nbsp;</td>
      <td align="left" width="20%">Total</td>
      <td align="right" width="15%">(<script>document.write(formatFee(<%= displayAmount %>))</script>)</td>
      <td align="left" width="50%">&nbsp;</td>
    </tr>
    <tr>
      <td align="left" colspan="3" width="37%"><hr size="2" color="black" noshade></hr></td>
      <td align="left" width="50%">&nbsp;</td>
    </tr>
  <%
                      }
                      else
                      {
  %>
    <tr>
      <td align="left" colspan="3" width="37%"><hr size="1" color="black" noshade></hr></td>
      <td align="left" width="50%">&nbsp;</td>
    </tr>
    <tr>
      <td align="left" width="2%">&nbsp;</td>
      <td align="left" width="20%">Total</td>
      <td align="right" width="15%"><script>document.write(formatFee(<%= totalFundTransfer %>))</script></td>
      <td align="left" width="50%">&nbsp;</td>
    </tr>
    <tr>
      <td align="left" colspan="3" width="37%"><hr size="2" color="black" noshade></hr></td>
      <td align="left" width="50%">&nbsp;</td>
    </tr>
    <tr>
      <td colspan="4" width="100%">&nbsp;</td>
    </tr>
  <%
                      }
                      
                      for (int j = 0; j < transferAmounts.length; j++)
                      {
                          transferAmounts[j] = new EDITBigDecimal();
                      }

                      totalFundTransfer = new EDITBigDecimal();
                  }

                  prevFundNumberOrClientFundNumber = fundNumberOrClientFundNumber;
                  prevFundName = fundName;
              }

              Fee[] fee = (Fee[]) cashTransfers.get(theKeyForMaps);

              EDITTrxHistoryVO[] editTrxHistoryVOs =
                      (EDITTrxHistoryVO[]) accrualTransfers.get(theKeyForMaps);

              if (fee != null || editTrxHistoryVOs != null)
              {
                  if (fee != null)
                  {
                      for (int j = 0; j < fee.length; j++)
                      {
                          FeeVO feeVO = (FeeVO) fee[j].getVO();

                          if (feeVO.getTransactionTypeCT().equalsIgnoreCase("DPURCH") ||
                              feeVO.getTransactionTypeCT().equalsIgnoreCase("DFACC"))
                          {
                              FeeDescriptionVO feeDescriptionVO = fee[j].getFeeDescription();

                              for (int k = 0; k < transferAmounts.length; k++)
                              {
                                  if (feeDescriptionVO.getFeeTypeCT().equalsIgnoreCase(feeTypes[k]))
                                  {
                                      if ((feeVO.getToFromInd() != null && feeVO.getToFromInd().equalsIgnoreCase("F")) ||
                                          (feeDescriptionVO.getFeeTypeCT().equalsIgnoreCase(GAIN_LOSS_FEE) &&
                                           new EDITBigDecimal(feeVO.getTrxAmount()).isGT("0")))
                                      {
                                          transferAmounts[k] = transferAmounts[k].subtractEditBigDecimal(feeVO.getTrxAmount());
    
                                          totalFundTransfer = totalFundTransfer.subtractEditBigDecimal(feeVO.getTrxAmount());
                                          grandTotalTransfer = grandTotalTransfer.subtractEditBigDecimal(feeVO.getTrxAmount());
                                      }
                                      else
                                      {
                                          transferAmounts[k] = transferAmounts[k].addEditBigDecimal(feeVO.getTrxAmount());

                                          totalFundTransfer = totalFundTransfer.addEditBigDecimal(feeVO.getTrxAmount());
                                          grandTotalTransfer = grandTotalTransfer.addEditBigDecimal(feeVO.getTrxAmount());
                                      }

                                      /* Calculate Gain/Loss if fee type is M&E Fee - we will always add the gainLoss
                                         value to transferAmounts subscript 10 because that is the subscript for the
                                         gain/loss value, and the value will always be returned with the appropriate
                                         sign. */
//                                      if (feeVO.getTransactionTypeCT().equalsIgnoreCase("DFACC") &&
//                                          feeDescriptionVO.getFeeTypeCT().equalsIgnoreCase("MEFee"))
//                                      {
//                                          EDITBigDecimal gainLoss = Util.calculateGainLoss(new EDITBigDecimal(feeVO.getUnits()),
//                                                                                           feeVO.getEffectiveDate(),
//                                                                                           feeVO.getRedemptionDate(),
//                                                                                           filteredFundVOs[i].getFilteredFundPK(),
//                                                                                           feeVO.getChargeCodeFK(),
//                                                                                           filteredFundVOs[i].getPricingDirection());
//                                          transferAmounts[10] = transferAmounts[10].addEditBigDecimal(gainLoss);
//
//                                          totalFundTransfer = totalFundTransfer.addEditBigDecimal(gainLoss);
//                                          grandTotalTransfer = grandTotalTransfer.addEditBigDecimal(gainLoss);
//                                      }
                                  }
                              }
                          }
                      }
                  }
              }

              if (editTrxHistoryVOs != null)
              {
                  for (int j = 0; j < editTrxHistoryVOs.length; j++)
                  {
                      trxTypeMatchFound = false;

                      EDITTrxVO editTrxVO = (EDITTrxVO) editTrxHistoryVOs[j].getParentVO(EDITTrxVO.class);
                      String trxType = editTrxVO.getTransactionTypeCT();

                      EDITBigDecimal trxAmount = new EDITBigDecimal();
                      EDITBigDecimal coiAmount = new EDITBigDecimal();
                      EDITBigDecimal adminFeeAmount = new EDITBigDecimal();
                      EDITBigDecimal gainLoss = new EDITBigDecimal();

                      if (trxType.equalsIgnoreCase("MD"))
                      {
                          String trxFromToInd = "";
                          
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
                              if (investmentVO.getFilteredFundFK() == Long.parseLong(filteredFundPK))
                              {
                                  BucketChargeHistoryVO[] bucketChargeHistoryVOs = bucketHistoryVOs[k].getBucketChargeHistoryVO();
                                  if (bucketChargeHistoryVOs != null)
                                  {
                                      for (int m = 0; m < bucketChargeHistoryVOs.length; m++)
                                      {
                                          if (trxFromToInd.equalsIgnoreCase("F"))
                                          {
                                              if (bucketChargeHistoryVOs[m].getChargeTypeCT().equalsIgnoreCase("BucketCoi"))
                                              {
                                                  coiAmount = coiAmount.subtractEditBigDecimal(bucketChargeHistoryVOs[m].getChargeAmount());
                                              }
                                              else if (bucketChargeHistoryVOs[m].getChargeTypeCT().equalsIgnoreCase("BucketAdminCharge"))
                                              {
                                                  adminFeeAmount = adminFeeAmount.subtractEditBigDecimal(bucketChargeHistoryVOs[m].getChargeAmount());
                                              }
                                          }
                                          else
                                          {
                                              if (bucketChargeHistoryVOs[m].getChargeTypeCT().equalsIgnoreCase("BucketCoi"))
                                              {
                                                  coiAmount = coiAmount.addEditBigDecimal(bucketChargeHistoryVOs[m].getChargeAmount());
                                              }
                                              else if (bucketChargeHistoryVOs[m].getChargeTypeCT().equalsIgnoreCase("BucketAdminCharge"))
                                              {
                                                  adminFeeAmount = adminFeeAmount.addEditBigDecimal(bucketChargeHistoryVOs[m].getChargeAmount());
                                              }
                                          }
                                      }
                                  }
                              }
                          }
                      }

                      InvestmentHistoryVO[] investmentHistoryVOs = editTrxHistoryVOs[j].getInvestmentHistoryVO();
                      for (int l = 0; l < investmentHistoryVOs.length; l++)
                      {
                          InvestmentVO investmentVO = (InvestmentVO) investmentHistoryVOs[l].getParentVO(InvestmentVO.class);
                          if (investmentVO.getFilteredFundFK() == Long.parseLong(filteredFundPK))
                          {
                              if (!trxType.equalsIgnoreCase("MD"))
                              {
                                  if (investmentHistoryVOs[l].getToFromStatus() != null)
                                  {
                                      if (investmentHistoryVOs[l].getToFromStatus().equalsIgnoreCase("T"))
                                      {
                                          trxAmount = trxAmount.addEditBigDecimal(investmentHistoryVOs[l].getInvestmentDollars());
                                      }
                                      else
                                      {
                                          trxAmount = trxAmount.subtractEditBigDecimal(investmentHistoryVOs[l].getInvestmentDollars());
                                      }
                                  }
                              }

                              gainLoss = gainLoss.addEditBigDecimal(investmentHistoryVOs[l].getGainLoss());
                          }
                      }

                      for (int l = 0; l < EDITTrx.withdrawalTrxTypes.length; l++)
                      {
                          if (trxType.equalsIgnoreCase(EDITTrx.withdrawalTrxTypes[l]))
                          {
                              transferAmounts[0] = transferAmounts[0].addEditBigDecimal(trxAmount);
                              trxTypeMatchFound = true;
                              break;
                          }
                      }

                      for (int l = 0; l < EDITTrx.premiumTrxTypes.length; l++)
                      {
                          if (trxType.equalsIgnoreCase(EDITTrx.premiumTrxTypes[l]))
                          {
                              transferAmounts[1] = transferAmounts[1].addEditBigDecimal(trxAmount);
                              trxTypeMatchFound = true;
                              break;
                          }
                      }

                      for (int l = 0; l < EDITTrx.transferTrxTypes.length; l++)
                      {
                          if (trxType.equalsIgnoreCase(EDITTrx.transferTrxTypes[l]));
                          {                              
                              transferAmounts[2] = transferAmounts[2].addEditBigDecimal(trxAmount);
                              trxTypeMatchFound = true;
                              break;
                          }
                      }

                      for (int l = 0; l < EDITTrx.moneyMoveSurrenderTrxTypes.length; l++)
                      {
                          if (trxType.equalsIgnoreCase(EDITTrx.surrenderTrxTypes[l]))
                          {
                              transferAmounts[4] = transferAmounts[4].addEditBigDecimal(trxAmount);
                              trxTypeMatchFound = true;
                              break;
                          }
                      }

                      for (int l = 0; l < EDITTrx.deathTrxTypes.length; l++)
                      {
                          if (trxType.equalsIgnoreCase(EDITTrx.deathTrxTypes[l]))
                          {
                              transferAmounts[5] = transferAmounts[5].addEditBigDecimal(trxAmount);
                              trxTypeMatchFound = true;
                              break;
                          }
                      }
                      
                      for (int l = 0; l < EDITTrx.newIssueTrxTypes.length; l++)
                      {
                          if (trxType.equalsIgnoreCase(EDITTrx.newIssueTrxTypes[l]) &&
                              editTrxVO.getTransferUnitsType().equalsIgnoreCase("NewIssue"))
                          {
                              transferAmounts[6] = transferAmounts[6].addEditBigDecimal(trxAmount);
                              trxTypeMatchFound = true;
                              break;
                          }
                      }

                      for (int l = 0; l < EDITTrx.coiTrxTypes.length; l++)
                      {
                          if (trxType.equalsIgnoreCase(EDITTrx.coiTrxTypes[l]))
                          {
                              transferAmounts[8] = transferAmounts[8].addEditBigDecimal(adminFeeAmount);
                              transferAmounts[9] = transferAmounts[9].addEditBigDecimal(coiAmount);
                              trxAmount = trxAmount.addEditBigDecimal(adminFeeAmount);
                              trxAmount = trxAmount.addEditBigDecimal(coiAmount);
                              trxTypeMatchFound = true;
                              break;
                          }
                      }

                      if (trxTypeMatchFound)
                      {
                          transferAmounts[10] = transferAmounts[10].addEditBigDecimal(gainLoss);
                          totalFundTransfer = totalFundTransfer.addEditBigDecimal(trxAmount);
                          grandTotalTransfer = grandTotalTransfer.addEditBigDecimal(trxAmount);
                      }
                  }
              }
          }   // FOR LOOP FOR CHARGECODEFKs ENDS

      }  // END OF FILTERED FUND VO's for loop

      if (!totalFundTransfer.isEQ(new EDITBigDecimal()))
      {
  %>
    <tr>
      <td align="left" colspan="4">
      <%= prevFundNumberOrClientFundNumber %>,&nbsp;<%= prevFundName %>
      </td>
    </tr>
    <tr>
      <td align="left" colspan="4">
      Please transfer the following amounts To/(from) the Separate Account Division noted above.
      </td>
    </tr>
    <tr>
      <td align="left" colspan="4">
      Shares should be redeemed/purchased based on NAVs as of&nbsp;<%= displayDate %>.
      </td>
    </tr>
    <tr>
      <td align="left" colspan="4">
      Cash should be moved on&nbsp;<%= cashMoveDate %>.
      </td>
    </tr>
    <tr>
        <td colspan="4">&nbsp;</td>
    </tr>
    <br>
    <br>
  <%        for (int j = 0; j < transactions.length; j++)
          {
              if (transferAmounts[j].isLT(new EDITBigDecimal()))
              {
                  displayAmount = transferAmounts[j].multiplyEditBigDecimal("-1");
  %>
    <tr>
      <td align="left" width="2%">&nbsp;</td>
      <td align="left" width="20%"><%= transactions[j] %></td>
      <td align="right" width="15%">(<script>document.write(formatFee(<%= displayAmount %>))</script>)</td>
      <td align="left" width="50%">&nbsp;</td>
    </tr>
  <%
              }
              else
              {
  %>
    <tr>
      <td align="left" width="2%">&nbsp;</td>
      <td align="left" width="20%"><%= transactions[j] %></td>
      <td align="right" width="15%"><script>document.write(formatFee(<%= transferAmounts[j]%>))</script></td>
      <td align="left" width="50%">&nbsp;</td>
    </tr>
  <%
              }
              
              if ((transactions[j].equalsIgnoreCase("Net Premium") ||
                   transactions[j].equalsIgnoreCase("Advance Transfers")) &&
                   !transferAmounts[j].isEQ(new EDITBigDecimal()))
              {
                      generateDCASHTrx(
                              filteredFundPK,
                              transactions[j],
                              transferAmounts[j],
                              date,
                              chargeCodeFK);
              }
          }
          
          if (totalFundTransfer.isLT(new EDITBigDecimal()))
          {
              displayAmount = totalFundTransfer.multiplyEditBigDecimal("-1");
  %>
    <tr>
      <td align="left" colspan="3" width="37%"><hr size="1" color="black" noshade></hr></td>
      <td align="left" width="50%">&nbsp;</td>
    </tr>
    <tr>
      <td align="left" width="2%">&nbsp;</td>
      <td align="left" width="20%">Total</td>
      <td align="right" width="15%">(<script>document.write(formatFee(<%= displayAmount %>))</script>)</td>
      <td align="left" width="50%">&nbsp;</td>
    </tr>
    <tr>
      <td colspan="4" width="100%">&nbsp;</td>
    </tr>
  <%
          }
          else
          {
  %>
    <tr>
      <td align="left" colspan="3" width="37%"><hr size="1" color="black" noshade></hr></td>
      <td align="left" width="50%">&nbsp;</td>
    </tr>
    <tr>
      <td align="left" width="2%">&nbsp;</td>
      <td align="left" width="20%">Total</td>
      <td align="right" width="15%"><script>document.write(formatFee(<%= totalFundTransfer %>))</script></td>
      <td align="left" width="50%">&nbsp;</td>
    </tr>
    <tr>
      <td colspan="4" width="100%">&nbsp;</td>
    </tr>
  <%
          }
      }
  %>
    <tr>
      <td align="left" colspan="3" width="37%"><hr size="1" color="black" noshade></hr></td>
      <td align="left" width="50%">&nbsp;</td>
    </tr>
    <tr>
      <td align="left" colspan="3" width="37%"><%= new EDITDateTime().getFormattedDateTime() %></td>
      <td align="left" width="50%">&nbsp;</td>
    </tr>
    <tr>
      <td colspan="4" width="100%">&nbsp;</td>
    </tr>
    <tr>
      <td colspan="4" width="100%">&nbsp;</td>
    </tr>
    <tr>
      <td colspan="4" width="100%">&nbsp;</td>
    </tr>
    <tr>
      <td colspan="4" width="100%">&nbsp;</td>
    </tr>
    <tr>
      <td align="left" width="50%" colspan="3">Cash Moves on:&nbsp;<%= cashMoveDate %></td>
      <td align="right" width="50%"><%= companyName %></td>
    </tr>
    <tr>
      <td align="left" width="50%" colspan="3">With Valuation date of:&nbsp;<%= displayDate %></td>
      <td align="right" width="50%">&nbsp;</td>
    </tr>
    <tr>
      <td align="left" colspan="4"><hr size="1" color="black" noshade></hr></td>
    </tr>
    <tr>
      <td align="left" width="5%">Fund Code</td>
      <td align="left" width="30%">Fund Name</td>
      <td align="left" width="15%">Purchase</td>
      <td align="left" width="50%">Redemption</td>
    </tr>
  <%
      EDITBigDecimal totalPurchases = new EDITBigDecimal();
      EDITBigDecimal totalRedemptions = new EDITBigDecimal();

      Enumeration fundTotalsKeys = fundTotalsHT.keys();
      while (fundTotalsKeys.hasMoreElements())
      {
          String key = (String) fundTotalsKeys.nextElement();
          String totalFundNumber = key.substring(0, key.indexOf("_"));
          String totalFundName = key.substring(key.indexOf("_") + 1);
  %>
    <tr>
      <td align="left" width="5%"><%= totalFundNumber %></td>
      <td align="left" width="30%"><%= totalFundName %></td>
  <%
          EDITBigDecimal totalFundValue = (EDITBigDecimal) fundTotalsHT.get(key);
          
          if (totalFundValue.isGT(new EDITBigDecimal()))
          {
              totalPurchases = totalPurchases.addEditBigDecimal(totalFundValue);
  %>
      <td align="left" width="15%"><script>document.write(formatFee(<%= totalFundValue %>))</script></td>
      <td align="left" width="50%">&nbsp;</td>
  <%
          }
          else
          {
              totalFundValue = totalFundValue.multiplyEditBigDecimal("-1");
              totalRedemptions = totalRedemptions.addEditBigDecimal(totalFundValue);
  %>
      <td align="left" width="15%">&nbsp;</td>
      <td align="left" width="50%">(<script>document.write(formatFee(<%= totalFundValue %>))</script>)</td>
  <%
          }
  %>
    </tr>
  <%
      }
  %>
    <tr>
      <td align="left" colspan="4"><hr size="2" color="black" noshade></hr></td>
    </tr>
    <tr>
      <td align="left" width="5%">&nbsp;</td>
      <td align="left" width="30%">Total</td>
      <td align="left" width="15%"><script>document.write(formatFee(<%= totalPurchases %>))</script></td>
  <%
      if (totalRedemptions.isEQ(EDITBigDecimal.EDIT_DECIMAL_ZILCH_STR))
      {
  %>
      <td align="left" width="50%">&nbsp;</td>
  <%
      }
      else
      {
  %>
      <td align="left" width="50%">(<script>document.write(formatFee(<%= totalRedemptions %>))</script>)</td>
  <%
      }
  %>
    </tr>
  </table>
</body>
</html>
