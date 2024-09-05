 <!-- ************* JSP Code ************* -->
<%@ page errorPage="/common/jsp/error.jsp" autoFlush="true" %>

<%@ page import="java.util.*,
                 fission.beans.PageBean,
                 edit.common.CodeTableWrapper,
                 edit.common.vo.*,
                 fission.utility.Util,
                 fission.beans.SessionBean,
                 edit.common.EDITBigDecimal,
                 edit.common.EDITDateTime,
                 engine.*"%>

<%
    CodeTableWrapper codeTableWrapper = CodeTableWrapper.getSingleton();

    ProductStructureVO[] productStructureVOs = (ProductStructureVO[]) request.getAttribute("companyStructureVOs");
    FundVO[] fundVOs = (FundVO[]) request.getAttribute("fundVOs");

    String fromDate = (String) request.getAttribute("fromDate");
    String toDate   = (String) request.getAttribute("toDate");

    Map companyHT = new HashMap();

    for (int i = 0; i < productStructureVOs.length; i++)
    {
        String companyStructurePK = productStructureVOs[i].getProductStructurePK() + "";
        companyHT.put(companyStructurePK, productStructureVOs[i]);
    }

    Map filteredFundHT = new HashMap();

    for (int i = 0; i < fundVOs.length; i++)
    {
        FilteredFundVO[] filteredFundVOs = fundVOs[i].getFilteredFundVO();
        if (filteredFundVOs != null)
        {
            for (int j = 0; j < filteredFundVOs.length; j++)
            {
                String filteredFundPK = filteredFundVOs[j].getFilteredFundPK() + "";
                filteredFundHT.put(filteredFundPK, fundVOs[i]);
            }
        }
    }

    EDITTrxHistoryVO[] editTrxHistoryVOs = (EDITTrxHistoryVO[]) request.getAttribute("editTrxHistoryVOs");

    Map transactionTreeMap = sortTransactionsByCompanyName(editTrxHistoryVOs, companyHT);
%>


<%!
    private boolean isExcludedTrxType(String trxType)
    {
        boolean isExcludedTrxType = false;
        if (trxType.equalsIgnoreCase("Check") ||
            trxType.equalsIgnoreCase("ComplexChange") ||
            trxType.equalsIgnoreCase("Death") ||
            trxType.equalsIgnoreCase("FaceDecrease"))
        {
            isExcludedTrxType = true;
        }

        return isExcludedTrxType;
    }
    
    private boolean isTransferTrx(String trxType)
    {
        boolean isTransfer = false;
        if (trxType.equalsIgnoreCase("FreeLookTransfer") ||
            trxType.equalsIgnoreCase("HedgeFundTransferPct") ||
            trxType.equalsIgnoreCase("HedgeFundTransferAmt") ||
            trxType.equalsIgnoreCase("Transfer"))
        {
            isTransfer = true;
        }

        return isTransfer;
    }
    
    private TreeMap sortTransactionsByCompanyName(EDITTrxHistoryVO[] editTrxHistoryVOs,
                                                   Map companyHT)
                                                 throws Exception {

        String companyName = "*";

		TreeMap sortedTransactions = new TreeMap();

        if (editTrxHistoryVOs != null)
        {
            for (int i = 0; i < editTrxHistoryVOs.length; i++)
            {
                companyName = "*";

                EDITTrxVO editTrxVO = (EDITTrxVO) editTrxHistoryVOs[i].getParentVO(EDITTrxVO.class);
                ClientSetupVO clientSetupVO = (ClientSetupVO) editTrxVO.getParentVO(ClientSetupVO.class);
                ContractSetupVO contractSetupVO = (ContractSetupVO) clientSetupVO.getParentVO(ContractSetupVO.class);
                SegmentVO segmentVO = null;
                if (contractSetupVO.getParentVOs() != null)
                {
                    segmentVO = (SegmentVO) contractSetupVO.getParentVO(SegmentVO.class);
                }
                if (segmentVO != null)
                {
                    long companyStructureFK = segmentVO.getProductStructureFK();
                    ProductStructureVO productStructure = (ProductStructureVO) companyHT.get(companyStructureFK + "");
                    Company company = Company.findByPK(new Long(productStructure.getCompanyFK()));
                    companyName = (company.getCompanyName()).trim();
                }

                sortedTransactions.put(companyName + editTrxVO.getTransactionTypeCT() + editTrxHistoryVOs[i].getEDITTrxHistoryPK(), editTrxHistoryVOs[i]);
            }
		}

		return sortedTransactions;
	}

//    private TreeMap sortTotalsByCompanyNameTrxType(EDITTrxHistoryVO[] editTrxHistoryVOs,
//                                                    String companyName,
//                                                     CodeTableVO[] trxTypes)
//                                                  throws Exception {
//
//		TreeMap sortedTotals = new TreeMap();
//
//        for (int e = 0; e < editTrxHistoryVOs.length; e++) {
//
//            String voCompanyName = finActivityTotalsVOs[s].getCompanyName();
//
//            if (voCompanyName.equals(companyName)) {
//
//                String transactionType = finActivityTotalsVOs[s].getTransactionType();
//
//                for (int t = 0; t < trxTypes.length; t++) {
//
//                    if (trxTypes[t].getCode().equalsIgnoreCase(transactionType)) {
//
//                        sortedTotals.put(companyName + trxTypes[t].getCodeDesc(), finActivityTotalsVOs[s]);
//                    }
//                }
//            }
//		}
//
//		return sortedTotals;
//	}
%>
<html>
<head>
<title>Financial Activity Report</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link href="/PORTAL/common/css/PORTAL.css" rel="stylesheet" type="text/css">

<!-- ************* Java Script Code ************* -->
<script>
</script>
</head>

<!-- ************* HTML Code ************* -->
<body>

<h3 align="center">
    FINANCIAL ACTIVITY REPORT
</h3>
<h5 align="right">
    From:&nbsp;<%= fromDate %>&nbsp;&nbsp;&nbsp;&nbsp;To:&nbsp;<%= toDate %>
</h5>

<hr size="3">

  <table align="left" cellpadding="2" cellspacing="4">
    <%

        String prevCompany = "";
        String prevTrx = "";

        EDITBigDecimal trxDollars   = new EDITBigDecimal();
        EDITBigDecimal trxUnits     = new EDITBigDecimal();
        EDITBigDecimal trxGainLoss  = new EDITBigDecimal();
        EDITBigDecimal companyDollarsInOut = new EDITBigDecimal();
        EDITBigDecimal companyDollarsTsfr = new EDITBigDecimal();

        List voInclusionList = new ArrayList();
        voInclusionList.add(EDITTrxVO.class);
        voInclusionList.add(ClientSetupVO.class);
        voInclusionList.add(ContractSetupVO.class);
        voInclusionList.add(InvestmentAllocationOverrideVO.class);
        voInclusionList.add(SegmentVO.class);
        voInclusionList.add(InvestmentVO.class);
        voInclusionList.add(InvestmentAllocationVO.class);
        voInclusionList.add(BucketVO.class);
        voInclusionList.add(BucketHistoryVO.class);
        voInclusionList.add(InvestmentHistoryVO.class);
        voInclusionList.add(FinancialHistoryVO.class);
        voInclusionList.add(WithholdingHistoryVO.class);
        voInclusionList.add(ChargeHistoryVO.class);

        Map trxValues = new HashMap();

        if (editTrxHistoryVOs != null)
        {
            for (int i = 0; i < editTrxHistoryVOs.length; i++)
            {
                event.business.Event eventComponent = new event.component.EventComponent();
                EDITTrxHistoryVO editTrxHistoryVO = eventComponent.composeEDITTrxHistoryVOByPK(editTrxHistoryVOs[i].getEDITTrxHistoryPK(), voInclusionList);
                EDITTrxVO editTrxVO = (EDITTrxVO) editTrxHistoryVO.getParentVO(EDITTrxVO.class);
                String trxType = editTrxVO.getTransactionTypeCT();

                trxType = codeTableWrapper.getCodeDescByCodeTableNameAndCode("TRXTYPE", trxType);
                if (!trxType.equalsIgnoreCase("Issue") && !isExcludedTrxType(trxType))
                {
                    ContractSetupVO contractSetupVO = (ContractSetupVO) editTrxVO.getParentVO(ClientSetupVO.class).
                                                                                   getParentVO(ContractSetupVO.class);
                    SegmentVO segmentVO = (SegmentVO) contractSetupVO.getParentVO(SegmentVO.class);
                    int finHistoryCount = editTrxHistoryVO.getFinancialHistoryVOCount();
                    if (finHistoryCount > 0)
                    {
                        InvestmentVO[] investmentVOs = segmentVO.getInvestmentVO();

                        Map investmentHT = new HashMap();

                        for (int j = 0; j < investmentVOs.length; j++)
                        {
                            String investmentPK = investmentVOs[j].getInvestmentPK() + "";
                            investmentHT.put(investmentPK, investmentVOs[j]);
                        }

                        long companyStructureFK = segmentVO.getProductStructureFK();
                        ProductStructureVO productStructure = (ProductStructureVO) companyHT.get(companyStructureFK + "");
                        Company company = Company.findByPK(new Long(productStructure.getCompanyFK()));
                        String companyName = (company.getCompanyName()).trim();

                        if (prevCompany.equals(""))
                        {
                            prevCompany = companyName;
                        }
                        else
                        {
                            if (!companyName.equals(prevCompany))
                            {
      %>
      <tr>
        <td colspan="9">
            <font color="000000">TOTALS&nbsp;&nbsp;&nbsp;<%= prevCompany %></font>
        </td>
      </tr>
      <tr>
         <td colspan="9" />
      </tr>
      <tr>
         <td colspan="9" />
      </tr>
      <tr>
         <td/>
         <td>
             <font color="000000">UNITS</font>
         </td>
         <td colspan="2">
             <font color="000000">DOLLARS</font>
         </td>
         <td colspan="5">
             <font color="000000">GAIN/(LOSS)</font>
         </td>
      </tr>
      <tr>
         <td colspan="9" />
      </tr>
      <tr>
         <td colspan="9" />
      </tr>
      <%
                                String[] trxTotals = new String[3];
                                trxTotals[0] = trxDollars + "";
                                trxTotals[1] = trxUnits + "";
                                trxTotals[2] = trxGainLoss + "";

                                trxValues.put(prevTrx, trxTotals);

                                Iterator trxValuesKeys = trxValues.keySet().iterator();

                                while (trxValuesKeys.hasNext())
                                {
                                    String transactionType = (String) trxValuesKeys.next();
                                    String[] trxValuesArray = (String[]) trxValues.get(transactionType);

                                    EDITBigDecimal transDollars = new EDITBigDecimal((String) trxValuesArray[0]);
                                    EDITBigDecimal transUnits = new EDITBigDecimal((String) trxValuesArray[1]);
                                    EDITBigDecimal transGainLoss = new EDITBigDecimal((String) trxValuesArray[2]);

                                    if (transactionType.equalsIgnoreCase("Premium"))
                                    {
                                        companyDollarsInOut = companyDollarsInOut.addEditBigDecimal(transDollars);
                                    }
                                    else if (!isTransferTrx(transactionType))
                                    {
                                        companyDollarsInOut = companyDollarsInOut.subtractEditBigDecimal(transDollars);
                                    }
                                    else
                                    {
                                        companyDollarsTsfr = companyDollarsTsfr.addEditBigDecimal(transDollars);
                                    }

                                    String totalUnitsStr = Util.formatDecimal("0.000000", transUnits);
                                    String totalDollarsStr = Util.formatDecimal("###,###,##0.00", transDollars);
                                    String totalGainLossStr = Util.formatDecimal("###,###,##0.00", transGainLoss);
      %>
      <tr>
        <td>
            <font color="#616161"><%= transactionType %></font>
        </td>
        <td>
            <font color="#616161"><%= totalUnitsStr %></font>
        </td>
        <td colspan="2">
            <font color="#616161"><%= totalDollarsStr %></font>
        </td>
        <td colspan="5">
            <font color="#616161"><%= totalGainLossStr %></font>
        </td>
      </tr>
      <tr>
         <td colspan="9" />
      </tr>
      <tr>
         <td colspan="9" />
      </tr>
      <%
                                }

                                String companyDollarsInOutStr = Util.formatDecimal("###,###,##0.00", companyDollarsInOut);
                                String companyDollarsTsfrStr = Util.formatDecimal("###,###,##0.00", companyDollarsTsfr);
      %>
      <tr>
        <td colspan="2">
            <font color="#000000">END&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<%= prevCompany %>&nbsp;&nbsp;&nbsp;Money In/Out</font>
        </td>
        <td colspan="7">
            <font color="#616161"><%= companyDollarsInOutStr %></font>
        </td>
      </tr>
      <tr>
        <td colspan="2">
            <font color="#000000">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<%= prevCompany %>&nbsp;&nbsp;&nbsp;Transfers</font>
        </td>
        <td colspan="7">
            <font color="#616161"><%= companyDollarsTsfrStr %></font>
        </td>
      </tr>
      <tr>
         <td colspan="9">
             ===================================================================================================================================
         </td>
      </tr>
      <tr>
         <td colspan="9" />
      </tr>
      <tr>
         <td colspan="9" />
      </tr>
      <tr>
         <td colspan="9" />
      </tr>
      <%
                                companyDollarsInOut = new EDITBigDecimal();
                                companyDollarsTsfr = new EDITBigDecimal();
                                prevCompany = companyName;
                                prevTrx = "";
                                trxDollars = new EDITBigDecimal();
                                trxUnits = new EDITBigDecimal();
                                trxGainLoss = new EDITBigDecimal();
                                trxValues.clear();
                            }
                        }

                        String marketingPkgName = (productStructure.getMarketingPackageName()).trim();
                        String groupProductName = (productStructure.getGroupProductName()).trim();
                        String areaName = (productStructure.getAreaName()).trim();
                        String businessContractName = (productStructure.getBusinessContractName()).trim();
                        String companyStructureName = companyName + "," +
                                                      marketingPkgName + "," +
                                                      groupProductName + "," +
                                                      areaName + "," +
                                                      businessContractName;

                        String contractNumber = segmentVO.getContractNumber();

                        if (prevTrx.equals(""))
                        {
                            prevTrx = trxType;
                        }
                        else
                        {
                            if (!prevTrx.equals(trxType))
                            {
                                String[] trxTotals = new String[3];
                                trxTotals[0] = trxDollars + "";
                                trxTotals[1] = trxUnits + "";
                                trxTotals[2] = trxGainLoss + "";

                                trxValues.put(prevTrx, trxTotals);

                                trxDollars = new EDITBigDecimal();
                                trxUnits = new EDITBigDecimal();
                                trxGainLoss = new EDITBigDecimal();
                                prevTrx = trxType;
                            }
                        }


                        String effDate = editTrxVO.getEffectiveDate();
                        String processDate = new EDITDateTime(editTrxHistoryVO.getProcessDateTime()).getEDITDate().getFormattedDate();

                        String cycleDate = editTrxHistoryVO.getCycleDate();
                        String seqNumber = editTrxVO.getSequenceNumber() + "";
                        String reversalInd = "N";
                        if (editTrxVO.getStatus().equalsIgnoreCase("R"))
                        {
                            reversalInd = "Y";
                        }

                        String taxYear = editTrxVO.getTaxYear() + "";
                        String grossAmount = "0.00";
                        String netAmount = "0.00";
                        String taxableBenefit = "0.00";
                        EDITBigDecimal checkAmount = new EDITBigDecimal();

                        if (editTrxHistoryVO.getFinancialHistoryVOCount() > 0)
                        {
                            FinancialHistoryVO financialHistoryVO = editTrxHistoryVO.getFinancialHistoryVO(0);
                            grossAmount = Util.formatDecimal("###,###,##0.00", financialHistoryVO.getGrossAmount());
                            netAmount = Util.formatDecimal("###,###,##0.00", financialHistoryVO.getNetAmount());
                            taxableBenefit = Util.formatDecimal("###,###,##0.00", financialHistoryVO.getTaxableBenefit());
                            checkAmount = new EDITBigDecimal(financialHistoryVO.getCheckAmount());
                        }

                        WithholdingHistoryVO[] withholdingHistoryVO = editTrxHistoryVO.getWithholdingHistoryVO();
                        EDITBigDecimal fedWithholding = new EDITBigDecimal();
                        EDITBigDecimal stateWithholding = new EDITBigDecimal();
                        EDITBigDecimal countyWithholding = new EDITBigDecimal();
                        EDITBigDecimal cityWithholding = new EDITBigDecimal();

                        if (withholdingHistoryVO != null && withholdingHistoryVO.length > 0)
                        {
                            fedWithholding      = new EDITBigDecimal(withholdingHistoryVO[0].getFederalWithholdingAmount());
                            stateWithholding    = new EDITBigDecimal(withholdingHistoryVO[0].getStateWithholdingAmount());
                            countyWithholding   = new EDITBigDecimal(withholdingHistoryVO[0].getCountyWithholdingAmount());
                            cityWithholding     = new EDITBigDecimal(withholdingHistoryVO[0].getCityWithholdingAmount());
                        }

                        String checkAmountStr = "";
                        String fedWithholdStr = "";
                        String stateWithholdStr = "";
                        String cityWithholdStr = "";
                        String countyWithholdStr = "";
                        boolean disbursement = true;

                        if (checkAmount.isEQ(new EDITBigDecimal()))
                        {
                            disbursement = false;
                        }
                        else
                        {
                            checkAmountStr = Util.formatDecimal("###,###,##0.00", checkAmount);
                            fedWithholdStr = Util.formatDecimal("###,###,##0.00", fedWithholding);
                            stateWithholdStr = Util.formatDecimal("###,###,##0.00", stateWithholding);
                            cityWithholdStr = Util.formatDecimal("###,###,##0.00", cityWithholding);
                            countyWithholdStr = Util.formatDecimal("###,###,##0.00", countyWithholding);
                        }

                        ChargeHistoryVO[] chargeHistoryVOs = editTrxHistoryVO.getChargeHistoryVO();

                        EDITBigDecimal loads = new EDITBigDecimal();
                        EDITBigDecimal fees  = new EDITBigDecimal();
                        EDITBigDecimal premiumTaxes = new EDITBigDecimal();

                        String loadsStr = "";
                        String feesStr = "";
                        String premiumTaxesStr = "";

                        if (chargeHistoryVOs != null)
                        {
                            for (int j = 0; j < chargeHistoryVOs.length; j++)
                            {
                                String chargeType = chargeHistoryVOs[j].getChargeTypeCT();
                                if (chargeType.equalsIgnoreCase("PremiumTax"))
                                {
                                    premiumTaxes = new EDITBigDecimal(chargeHistoryVOs[j].getChargeAmount());
                                }
                                else if (chargeType.equalsIgnoreCase("TransactionFee"))
                                {
                                    fees = new EDITBigDecimal(chargeHistoryVOs[j].getChargeAmount());
                                }
                                else
                                {
                                    loads = loads.addEditBigDecimal(chargeHistoryVOs[j].getChargeAmount());
                                }
                            }

                            premiumTaxesStr = Util.formatDecimal("###,###,##0.00", premiumTaxes);
                            feesStr = Util.formatDecimal("###,###,##0.00", fees);
                            loadsStr = Util.formatDecimal("###,###,##0.00", loads);
                        }
      %>
      <tr>
        <td>
            <font color="000000">CO STRUCTURE&nbsp;&nbsp;&nbsp;</font>
        </td>
        <td>
            <font color="000000">POLICY #&nbsp;&nbsp;&nbsp;</font>
        </td>
        <td>
            <font color="000000">TRX TYPE&nbsp;&nbsp;&nbsp;</font>
        </td>
        <td>
            <font color="000000">EFF DATE&nbsp;&nbsp;&nbsp;</font>
        </td>
        <td>
            <font color="000000">PROC DATE&nbsp;&nbsp;&nbsp;</font>
        </td>
        <td>
            <font color="000000">CYCLE DATE&nbsp;&nbsp;&nbsp;</font>
        </td>
        <td>
            <font color="000000">SEQ#&nbsp;&nbsp;&nbsp;</font>
        </td>
        <td>
            <font color="000000">RVSL&nbsp;&nbsp;&nbsp;</font>
        </td>
        <td>
            <font color="000000">TAX YR</font>
        </td>
      </tr>
      <tr>
         <td>
            <font color="#616161"><%= companyStructureName %></font>
         </td>
         <td>
            <font color="#616161"><%= contractNumber %></font>
         </td>
         <td>
            <font color="#616161"><%= trxType %></font>
         </td>
         <td>
            <font color="#616161"><%= effDate %></font>
         </td>
         <td>
            <font color="#616161"><%= processDate %></font>
         </td>
         <td>
            <font color="#616161"><%= cycleDate %></font>
         </td>
         <td>
            <font color="#616161"><%= seqNumber %></font>
         </td>
         <td>
            <font color="#616161"><%= reversalInd %></font>
         </td>
         <td>
            <font color="#616161"><%= taxYear %></font>
         </td>
       </tr>
       <tr>
         <td colspan="9" />
       </tr>
       <tr>
         <td colspan="9" />
       </tr>
       <%
                        if (!disbursement) {
       %>
       <tr>
        <td>
            <font color="000000">GROSS AMOUNT&nbsp;&nbsp;&nbsp;</font>
        </td>
        <td>
            <font color="000000">LOADS&nbsp;&nbsp;&nbsp;</font>
        </td>
        <td>
            <font color="000000">FEES&nbsp;&nbsp;&nbsp;</font>
        </td>
        <td>
            <font color="000000">PREM TAX&nbsp;&nbsp;&nbsp;</font>
        </td>
        <td colspan="2">
            <font color="000000">NET AMOUNT&nbsp;&nbsp;&nbsp;</font>
        </td>
        <td colspan="3">
            <font color="000000">TAXABLE BENEFIT&nbsp;&nbsp;&nbsp;</font>
        </td>
      </tr>
      <tr>
         <td>
            <font color="#616161"><%= grossAmount %></font>
         </td>
         <td>
            <font color="#616161"><%= loadsStr %></font>
         </td>
         <td>
            <font color="#616161"><%= feesStr %></font>
         </td>
         <td>
            <font color="#616161"><%= premiumTaxesStr %></font>
         </td>
         <td colspan="2">
            <font color="#616161"><%= netAmount %></font>
         </td>
         <td colspan="3">
            <font color="#616161"><%= taxableBenefit %></font>
         </td>
       </tr>
       <%
                        }
                        else
                        {
       %>
       <tr>
        <td>
            <font color="000000">GROSS AMOUNT&nbsp;&nbsp;&nbsp;</font>
        </td>
        <td>
            <font color="000000">LOADS&nbsp;&nbsp;&nbsp;</font>
        </td>
        <td>
            <font color="000000">FEES&nbsp;&nbsp;&nbsp;</font>
        </td>
        <td colspan="3">
            <font color="000000">NET AMOUNT&nbsp;&nbsp;&nbsp;</font>
        </td>
        <td colspan="3">
            <font color="000000">TAXABLE BENEFIT&nbsp;&nbsp;&nbsp;</font>
        </td>
      </tr>
      <tr>
         <td>
            <font color="#616161"><%= grossAmount %></font>
         </td>
         <td>
            <font color="#616161"><%= loadsStr %></font>
         </td>
         <td>
            <font color="#616161"><%= feesStr %></font>
         </td>
         <td colspan="3">
            <font color="#616161"><%= netAmount %></font>
         </td>
         <td colspan="3">
            <font color="#616161"><%= taxableBenefit %></font>
         </td>
       </tr>
       <tr>
        <td>
            <font color="000000">WITHHOLDING:&nbsp;&nbsp;&nbsp;</font>
        </td>
        <td>
            <font color="000000">FED&nbsp;&nbsp;&nbsp;</font>
        </td>
        <td>
            <font color="000000">STATE&nbsp;&nbsp;&nbsp;</font>
        </td>
        <td>
            <font color="000000">CITY&nbsp;&nbsp;&nbsp;</font>
        </td>
        <td colspan="2">
            <font color="000000">COUNTY&nbsp;&nbsp;&nbsp;</font>
        </td>
        <td colspan="3">
            <font color="000000">CHECK AMOUNT&nbsp;&nbsp;&nbsp;</font>
        </td>
      </tr>
      <tr>
         <td>
            &nbsp;
         </td>
         <td>
            <font color="#616161"><%= fedWithholdStr %></font>
         </td>
         <td>
            <font color="#616161"><%= stateWithholdStr %></font>
         </td>
         <td>
            <font color="#616161"><%= cityWithholdStr %></font>
         </td>
         <td colspan="2">
            <font color="#616161"><%= countyWithholdStr %></font>
         </td>
         <td colspan="3">
            <font color="#616161"><%= checkAmountStr %></font>
         </td>
       </tr>
       <%
                        }
       %>
       <tr>
         <td colspan="9" />
       </tr>
       <tr>
         <td colspan="9" />
       </tr>
       <tr>
        <td>
            <font color="000000">INVEST/FUND:&nbsp;&nbsp;&nbsp;</font>
        </td>
        <td>
            <font color="000000">ALLOC %&nbsp;&nbsp;&nbsp;</font>
        </td>
        <td>
            <font color="000000">DOLLARS&nbsp;&nbsp;&nbsp;</font>
        </td>
        <td>
            <font color="000000">UNITS&nbsp;&nbsp;&nbsp;</font>
        </td>
        <td>
            <font color="000000">GAIN/(LOSS)&nbsp;&nbsp;&nbsp;</font>
        </td>
        <td>
            <font color="000000">TO/FROM&nbsp;&nbsp;&nbsp;</font>
        </td>
        <td colspan="3">
            <font color="000000">VALUATION DATE&nbsp;&nbsp;&nbsp;</font>
        </td>
      </tr>
     <%
                        EDITBigDecimal dollars  = new EDITBigDecimal();
                        EDITBigDecimal units    = new EDITBigDecimal();
                        EDITBigDecimal gainLoss = new EDITBigDecimal();
                        String toFromInd = "";
                        String valuationDate = "";

                        InvestmentHistoryVO[] investmentHistoryVOs = editTrxHistoryVO.getInvestmentHistoryVO();
                        if (investmentHistoryVOs != null)
                        {
                            for (int j = 0; j < investmentHistoryVOs.length; j++)
                            {
                                toFromInd = investmentHistoryVOs[j].getToFromStatus();
                                if (toFromInd != null)
                                {
                                    dollars = new EDITBigDecimal(investmentHistoryVOs[j].getInvestmentDollars());
                                    units   = new EDITBigDecimal(investmentHistoryVOs[j].getInvestmentUnits());
                                    gainLoss = new EDITBigDecimal();
                                    long investmentFK = investmentHistoryVOs[j].getInvestmentFK();

                                    valuationDate = Util.initString(investmentHistoryVOs[j].getValuationDate(), "");
                                    if (valuationDate.length() > 10)
                                    {
                                        valuationDate = valuationDate.substring(0, 10);
                                    }


                                    trxDollars  = trxDollars.addEditBigDecimal(dollars);
                                    trxUnits    = trxUnits.addEditBigDecimal(units);
                                    trxGainLoss = trxGainLoss.addEditBigDecimal(gainLoss);

                                    String dollarsStr = "";
                                    String unitsStr = "";

                                    unitsStr = Util.formatDecimal("0.000000", units);
                                    dollarsStr = Util.formatDecimal("###,###,##0.00", dollars);

                                    String gainLossStr = Util.formatDecimal("###,###,##0.00", gainLoss);

                                    int invAllocOvrCount = contractSetupVO.getInvestmentAllocationOverrideVOCount();
                                    if (invAllocOvrCount > 0)
                                    {
                                        boolean matchFound = false;

                                        InvestmentAllocationOverrideVO[] invAllocOvrVO = contractSetupVO.getInvestmentAllocationOverrideVO();
                                        for (int k = 0; k < invAllocOvrCount; k++)
                                        {
                                            if (!matchFound) // skip this logic if we already found a match on the investment.
                                            {
                                                long investmentAllocationKey = 0;

                                                InvestmentVO investmentVO = null;

                                                // match the investment history to the correct investmentAllocationOverride
                                                if (investmentFK == invAllocOvrVO[k].getInvestmentFK())
                                                {
                                                    matchFound = true;
                                                    investmentVO = (InvestmentVO) invAllocOvrVO[k].getParentVO(InvestmentVO.class);
                                                }

                                                if (matchFound)
                                                {
                                                    investmentAllocationKey = invAllocOvrVO[k].getInvestmentAllocationFK();

                                                    String filteredFundFK = investmentVO.getFilteredFundFK() + "";
                                                    FundVO fundVO = (FundVO) filteredFundHT.get(filteredFundFK);
                                                    String allocationPct = "";

                                                    String fundName = fundVO.getName();
                                                    InvestmentAllocationVO[] invAllocVOs = investmentVO.getInvestmentAllocationVO();

                                                    for (int l = 0; l < invAllocVOs.length; l++)
                                                    {
                                                        if (invAllocVOs[l].getInvestmentAllocationPK() == investmentAllocationKey)
                                                        {
                                                            allocationPct = invAllocVOs[l].getAllocationPercent() + "";
                                                            break;
                                                        }
                                                    }
     %>
      <tr>
         <td>
            <font color="#616161"><%= fundName %></font>
         </td>
         <td>
            <font color="#616161"><%= allocationPct %></font>
         </td>
         <td>
            <font color="#616161"><%= dollarsStr %></font>
         </td>
         <td>
            <font color="#616161"><%= unitsStr %></font>
         </td>
         <td>
            <font color="#616161"><%= gainLossStr %></font>
         </td>
         <td>
            <font color="#616161"><%= toFromInd %></font>
         </td>
         <td colspan="3">
            <font color="#616161"><%= valuationDate %></font>
         </td>
       </tr>
       <%
                                                } // end if (for matchFound)
                                            } // end if for (!matchFound - used in order to skip invAllocOvrd if a match has already been found)
                                        } //end invAllocOvrd
                                    } // end invAllocCount > 0
                                    else
                                    {
                                        InvestmentVO investmentVO = null;
                                        InvestmentAllocationVO[] invAllocVOs = null;
                                        String allocationPct = "";
                                        String fundName = "";
                                        Iterator investmentEnum = investmentHT.values().iterator();
                                        while (investmentEnum.hasNext())
                                        {
                                            investmentVO = (InvestmentVO) investmentEnum.next();
                                            if (investmentFK == investmentVO.getInvestmentPK())
                                            {
                                                invAllocVOs = investmentVO.getInvestmentAllocationVO();

                                                for (int y = 0; y < invAllocVOs.length; y++)
                                                {
                                                    String overrideStatus = invAllocVOs[y].getOverrideStatus();
                                                    if (overrideStatus != null && overrideStatus.equalsIgnoreCase("P"))
                                                    {
                                                        allocationPct = invAllocVOs[y].getAllocationPercent() + "";

                                                        String filteredFundFK = investmentVO.getFilteredFundFK() + "";
                                                        FundVO fundVO = (FundVO) filteredFundHT.get(filteredFundFK);
                                                        fundName = fundVO.getName();
                                                        break;
                                                    }
                                                }
                                            }
                                        }    //end while
     %>
      <tr>
         <td>
            <font color="#616161"><%= fundName %></font>
         </td>
         <td>
            <font color="#616161"><%= allocationPct %></font>
         </td>
         <td>
            <font color="#616161"><%= dollarsStr %></font>
         </td>
         <td>
            <font color="#616161"><%= unitsStr %></font>
         </td>
         <td>
            <font color="#616161"><%= gainLossStr %></font>
         </td>
         <td>
            <font color="#616161"><%= toFromInd %></font>
         </td>
         <td colspan="3">
            <font color="#616161"><%= valuationDate %></font>
         </td>
       </tr>
       <%
                                    } //end of else
                                } // end if toFromInd != null
                            }  //end of for looop
                        }   //end if bucketHistoryVOs != null
       %>
       <tr>
         <td colspan="9">
             ------------------------------------------------------------------------------------------------------------------------------
         </td>
       </tr>
       <tr>
         <td colspan="9" />
       </tr>
       <tr>
         <td colspan="9" />
       </tr>
       <%
                    }
                }
            }

            String[] trxTotals = new String[3];
            trxTotals[0] = trxDollars + "";
            trxTotals[1] = trxUnits + "";
            trxTotals[2] = trxGainLoss + "";

            trxValues.put(prevTrx, trxTotals);
        }
    %>
      <tr>
        <td colspan="9">
            <font color="000000">TOTALS&nbsp;&nbsp;&nbsp;<%= prevCompany %></font>
        </td>
      </tr>
      <tr>
         <td colspan="9" />
      </tr>
      <tr>
         <td colspan="9" />
      </tr>
      <tr>
         <td/>
         <td>
             <font color="000000">UNITS</font>
         </td>
         <td colspan="2">
             <font color="000000">DOLLARS</font>
         </td>
         <td colspan="5">
             <font color="000000">GAIN/(LOSS)</font>
         </td>
      </tr>
      <tr>
         <td colspan="9" />
      </tr>
      <tr>
         <td colspan="9" />
      </tr>
      <%
         Iterator trxValuesKeys = trxValues.keySet().iterator();

         while (trxValuesKeys.hasNext())
         {
             String transactionType = (String) trxValuesKeys.next();
             String[] trxValuesArray = (String[]) trxValues.get(transactionType);

             EDITBigDecimal transDollars    = new EDITBigDecimal((String) trxValuesArray[0]);
             EDITBigDecimal transUnits      = new EDITBigDecimal((String) trxValuesArray[1]);
             EDITBigDecimal transGainLoss   = new EDITBigDecimal((String) trxValuesArray[2]);

             if (transactionType.equalsIgnoreCase("Premium"))
             {
                 companyDollarsInOut = companyDollarsInOut.addEditBigDecimal(transDollars);
             }
             else if (!isTransferTrx(transactionType))
             {
                 companyDollarsInOut = companyDollarsInOut.subtractEditBigDecimal(transDollars);
             }
             else
             {
                 companyDollarsTsfr = companyDollarsTsfr.addEditBigDecimal(transDollars);
             }

              String totalUnitsStr = Util.formatDecimal("0.000000", transUnits);
              String totalDollarsStr = Util.formatDecimal("###,###,##0.00", transDollars);
              String totalGainLossStr = Util.formatDecimal("###,###,##0.00", transGainLoss);
      %>
      <tr>
        <td>
            <font color="#616161"><%= transactionType %></font>
        </td>
        <td>
            <font color="#616161"><%= totalUnitsStr %></font>
        </td>
        <td colspan="2">
            <font color="#616161"><%= totalDollarsStr %></font>
        </td>
        <td colspan="5">
            <font color="#616161"><%= totalGainLossStr %></font>
        </td>
      </tr>
      <tr>
         <td colspan="9" />
      </tr>
      <tr>
         <td colspan="9" />
      </tr>
      <%
          }

          String companyDollarsInOutStr = Util.formatDecimal("###,###,##0.00", companyDollarsInOut);
          String companyDollarsTsfrStr = Util.formatDecimal("###,###,##0.00", companyDollarsTsfr);
      %>
      <tr>
        <td colspan="2">
            <font color="#000000">END&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<%= prevCompany %>&nbsp;&nbsp;&nbsp;Money In/Out</font>
        </td>
        <td colspan="7">
            <font color="#616161"><%= companyDollarsInOutStr %></font>
        </td>
      </tr>
      <tr>
        <td colspan="2">
            <font color="#000000">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<%= prevCompany %>&nbsp;&nbsp;&nbsp;Transfers</font>
        </td>
        <td colspan="7">
            <font color="#616161"><%= companyDollarsTsfrStr %></font>
        </td>
      </tr>
    </table>

<br>

</body>
</html>
