/*
 * User: dlataill
 * Date: April 3, 2003
 * Time: 1:01:03 PM
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */

package reporting.interfaces;

import batch.business.*;

import edit.common.*;

import edit.common.vo.*;
import edit.common.vo.user.*;

import edit.services.*;

import edit.services.command.*;

import edit.services.interfaces.*;

import fission.utility.*;

import java.util.*;

import engine.*;


public class ValuationInterfaceCmd extends AbstractInterface implements Command
{
    private Map clientCache;
    private Map companyCache;
    private String valuationDate;
    private StringBuffer fileData;
    private StringBuffer fileData2;

    public void setValuationInformation(Map clientCache, Map companyCache, String valuationDate)
    {
        this.clientCache = clientCache;
        this.companyCache = companyCache;
        this.valuationDate = valuationDate;
        fileData = new StringBuffer();
        fileData2 = new StringBuffer();
    }

    public Object exec()
    {
        try
        {
            CodeTableWrapper codeTableWrapper = CodeTableWrapper.getSingleton();
            engine.business.Lookup engineLookup = new engine.component.LookupComponent();

            FundVO[] fundVOs = engineLookup.getAllFunds();

            Map fundsHT = new HashMap();

            for (int f = 0; f < fundVOs.length; f++)
            {
                String fundPK = fundVOs[f].getFundPK() + "";
                fundsHT.put(fundPK, fundVOs[f].getFundType());
            }

            Map valuationHT = new HashMap();
            Map benefitExtractHT = new HashMap();

            engine.business.Lookup calcLookup = new engine.component.LookupComponent();

            long[] productStructureIds = new long[companyCache.size()];

            Iterator productKeys = companyCache.keySet().iterator();

            int i = 0;

            while (productKeys.hasNext())
            {
                String productKey = (String) productKeys.next();
                productStructureIds[i] = Long.parseLong(productKey);
                i++;
            }

            SegmentVO[] segmentVOs = contract.dm.dao.DAOFactory.getSegmentDAO().findAllSegmentsForValuation(valuationDate, productStructureIds);

            boolean variableFundFound = false;

            for (int s = 0; s < segmentVOs.length; s++)
            {
                variableFundFound = false;

                InvestmentVO[] investmentVOs = segmentVOs[s].getInvestmentVO();

                for (int a = 0; a < investmentVOs.length; a++)
                {
                    long filteredFundFK = investmentVOs[a].getFilteredFundFK();
                    FilteredFundVO[] filteredFundVO = calcLookup.findFilteredFundByPK(filteredFundFK, false, null);

                    String fundType = (String) fundsHT.get(filteredFundVO[0].getFundFK() + "");

                    if (fundType.equalsIgnoreCase("V"))
                    {
                        variableFundFound = true;

                        break;
                    }
                }

                if (!variableFundFound)
                {
                    String contractNumber = segmentVOs[s].getContractNumber();
                    String effectiveDate = segmentVOs[s].getEffectiveDate();

                    EDITDate editEffectiveDate = new EDITDate(effectiveDate);

                    String issueDate = DateTimeUtil.formatYYYYMMDDToMMDDYYYY(effectiveDate);

                    String planId = "SPIA";
                    String numberOfContracts = "1";
                    String assumedIntRate = "";
                    String mAndECharge = "";
                    String initialUnitValue = "";
                    String currentUnitValue = "";

                    String segmentStatus = segmentVOs[s].getSegmentStatusCT();

                    String status = "";

                    if (segmentStatus.equalsIgnoreCase("Commit") || segmentStatus.equalsIgnoreCase("Active") || segmentStatus.equalsIgnoreCase("Frozen") || segmentStatus.equalsIgnoreCase("DeathCertain") || segmentStatus.equalsIgnoreCase("DeathPending") || segmentStatus.equalsIgnoreCase("JointDeathPrimary") || segmentStatus.equalsIgnoreCase("JointDeathSecondary"))
                    {
                        status = "1";
                    }

                    else if (segmentStatus.equalsIgnoreCase("DeathLife") || segmentStatus.equalsIgnoreCase("DeathLump"))
                    {
                        status = "11";
                    }

                    else if (segmentStatus.equalsIgnoreCase("Surrendered"))
                    {
                        status = "12";
                    }

                    else if (segmentStatus.equalsIgnoreCase("Terminated"))
                    {
                        status = "15";
                    }

                    else
                    {
                        status = "99";
                    }

                    String numberOfLives = "";
                    String optionCode = segmentVOs[s].getOptionCodeCT();

                    if (optionCode.equalsIgnoreCase("PCA") || optionCode.equalsIgnoreCase("INT") || optionCode.equalsIgnoreCase("AMC"))
                    {
                        numberOfLives = "0";
                    }

                    else if (optionCode.equalsIgnoreCase("LOA") || optionCode.equalsIgnoreCase("LPC"))
                    {
                        numberOfLives = "1";
                    }

                    else if (optionCode.equalsIgnoreCase("JCP") || optionCode.equalsIgnoreCase("JSA"))
                    {
                        numberOfLives = "2";
                    }

                    long productStructureFK = segmentVOs[s].getProductStructureFK();

                    ProductStructureVO productStructureVO = (ProductStructureVO) companyCache.get(productStructureFK + "");

                    Company company = Company.findByPK(productStructureVO.getCompanyFK());
                    String companyName = company.getCompanyName();
                    String businessContract = productStructureVO.getBusinessContractName();
                    String kindOfAnnuity = "";
                    String deathStatus = "";

                    if (businessContract.equalsIgnoreCase("GSN") || businessContract.equalsIgnoreCase("SN") || businessContract.equalsIgnoreCase("SP"))
                    {
                        kindOfAnnuity = "0";
                        deathStatus = "0";
                    }

                    else
                    {
                        if (businessContract.equalsIgnoreCase("SL") || businessContract.equalsIgnoreCase("SW") || businessContract.equalsIgnoreCase("GSW"))
                        {
                            kindOfAnnuity = "1";

                            if (segmentStatus.equalsIgnoreCase("Active") || segmentStatus.equalsIgnoreCase("Commit") || segmentStatus.equalsIgnoreCase("DeathCertain"))
                            {
                                deathStatus = "0";
                            }

                            else if (segmentStatus.equalsIgnoreCase("DeathLife") || segmentStatus.equalsIgnoreCase("DeathLump") || segmentStatus.equalsIgnoreCase("JointDeathPrimary"))
                            {
                                if (segmentStatus.equalsIgnoreCase("DeathLump") && (optionCode.equalsIgnoreCase("JPC") || optionCode.equalsIgnoreCase("JSA")))
                                {
                                    deathStatus = "3";
                                }

                                else
                                {
                                    deathStatus = "1";
                                }
                            }

                            else if (segmentStatus.equalsIgnoreCase("JointDeathSecondary"))
                            {
                                deathStatus = "2";
                            }
                        }
                    }

                    String issueState = segmentVOs[s].getIssueStateCT().toUpperCase();
                    String qualNonQual = segmentVOs[s].getQualNonQualCT();

                    if (qualNonQual.equalsIgnoreCase("Y"))
                    {
                        qualNonQual = "N";
                    }

                    else
                    {
                        qualNonQual = "Q";
                    }

                    //SRAMAM 09/2004 DOUBLE2DECIMAL
                    //                String totalGrossConsid = Util.formatDecimal("########0.00",
                    //                                                              segmentVOs[s].getAmount());
                    String totalGrossConsid = Util.formatDecimal("########0.00", new EDITBigDecimal(segmentVOs[s].getAmount()));

                    String considPayMode = "0";
                    String fundsHeldOnDep = "0";
                    String firstPayIssueAge = "";
                    String firstPaySex = "";
                    String firstPaySubStd = "";
                    String firstPayDateOfDeath = "";
                    String secondPayIssueAge = "";
                    String secondPaySex = "";
                    String secondPaySubStd = "";
                    String secondPayDateOfDeath = "";

                    //                ClientRelationshipVO[] clientRelationshipVOs = segmentVOs[s].getClientRelationshipVO();
                    boolean firstPayeeInfoSet = false;

                    //                for (int r = 0; r < clientRelationshipVOs.length; r++) {
                    //
                    //                    PayeeVO[] payeeVO = clientRelationshipVOs[r].getPayeeVO();
                    //                    if (payeeVO != null && payeeVO.length > 0) {
                    //
                    //                        String clientFK = clientRelationshipVOs[r].getClientFK() + "";
                    //                        ClientDetailVO clientDetailVO = (ClientDetailVO) clientCache.get(clientFK);
                    //
                    //                        if (!businessContract.equalsIgnoreCase("SN") &&
                    //                            !businessContract.equalsIgnoreCase("SP") &&
                    //                            !businessContract.equalsIgnoreCase("GSN")) {
                    //
                    //                            if (!firstPayeeInfoSet) {
                    //
                    //                                firstPaySex = clientDetailVO.getGenderCT();
                    //                                if (firstPaySex.equalsIgnoreCase("Male")) {
                    //
                    //                                    firstPaySex = "0";
                    //                                }
                    //
                    //                                else if (firstPaySex.equalsIgnoreCase("Female")) {
                    //
                    //                                    firstPaySex = "1";
                    //                                }
                    //
                    //                                String dob = clientDetailVO.getBirthDate();
                    //                                EDITDate ceEffDate = new EDITDate(effectiveDate);
                    //                                EDITDate ceDOB = new EDITDate(dob);
                    //                                firstPayIssueAge = ceEffDate.getAge(ceDOB, "last") + "";
                    //                                firstPaySubStd = "0";
                    //                                StringTokenizer dateOfDeath = new StringTokenizer
                    //                                                              (clientDetailVO.getDateOfDeath(), "/");
                    //                                String dateOfDeathYear = dateOfDeath.nextToken();
                    //                                String dateOfDeathMonth = dateOfDeath.nextToken();
                    //                                String dateOfDeathDay = dateOfDeath.nextToken();
                    //                                firstPayDateOfDeath = dateOfDeathMonth + "/" +
                    //                                                      dateOfDeathDay   + "/" + dateOfDeathYear;
                    //
                    //                                firstPayeeInfoSet = true;
                    //                            }
                    //
                    //                            else {
                    //
                    //                                secondPaySex = clientDetailVO.getGenderCT();
                    //                                if (secondPaySex.equalsIgnoreCase("Male")) {
                    //
                    //                                    secondPaySex = "0";
                    //                                }
                    //
                    //                                else if (secondPaySex.equalsIgnoreCase("Female")) {
                    //
                    //                                    secondPaySex = "1";
                    //                                }
                    //
                    //                                String dob = clientDetailVO.getBirthDate();
                    //                                EDITDate ceEffDate = new EDITDate(effectiveDate);
                    //                                EDITDate ceDOB = new EDITDate(dob);
                    //                                secondPayIssueAge = ceEffDate.getAge(ceDOB, "last") + "";
                    //                                secondPaySubStd = "0";
                    //                                StringTokenizer dateOfDeath = new StringTokenizer(clientDetailVO.getDateOfDeath(), "/");
                    //                                String dateOfDeathYear = dateOfDeath.nextToken();
                    //                                String dateOfDeathMonth = dateOfDeath.nextToken();
                    //                                String dateOfDeathDay = dateOfDeath.nextToken();
                    //                                secondPayDateOfDeath = dateOfDeathMonth + "/" +
                    //                                                       dateOfDeathDay   + "/" + dateOfDeathYear;
                    //
                    //                                break;
                    //                            }
                    //                        }
                    //                    }
                    //                }
                    PayoutVO[] payoutVO = segmentVOs[s].getPayoutVO();

                    //                String paymentAmount = Util.formatDecimal("########0.00", payoutVO[0].getPaymentAmount());
                    String paymentAmount = Util.formatDecimal("########0.00", new EDITBigDecimal(payoutVO[0].getPaymentAmount()));

//                    StringTokenizer payStartDate = new StringTokenizer(payoutVO[0].getPaymentStartDate(), EDITDate.DATE_DELIMITER);
//                    String startYear = payStartDate.nextToken();
//                    String startMonth = payStartDate.nextToken();
//                    String startDay = payStartDate.nextToken();
//                    String paymentStartDate = startMonth + EDITDate.DATE_DELIMITER + startDay + EDITDate.DATE_DELIMITER + startYear;
                    String paymentStartDate = DateTimeUtil.formatYYYYMMDDToMMDDYYYY(payoutVO[0].getPaymentStartDate());


                    String paymentMode = payoutVO[0].getPaymentFrequencyCT();

                    if (paymentMode.equalsIgnoreCase("Monthly"))
                    {
                        paymentMode = "1";
                    }

                    else if (paymentMode.equalsIgnoreCase("Quarterly"))
                    {
                        paymentMode = "3";
                    }

                    else if (paymentMode.equalsIgnoreCase("SemiAnnual"))
                    {
                        paymentMode = "6";
                    }

                    else if (paymentMode.equalsIgnoreCase("Annual"))
                    {
                        paymentMode = "12";
                    }

                    String terminationDate = segmentVOs[s].getTerminationDate();

                    if (terminationDate.equals(EDITDate.DEFAULT_MAX_DATE))
                    {
                        terminationDate = null;
                    }

                    else
                    {
                        EDITDate ceDate = new EDITDate(terminationDate);

                        if (paymentMode.equalsIgnoreCase("MO"))
                        {
                            ceDate = ceDate.subtractMonths(1);
                        }

                        else if (paymentMode.equalsIgnoreCase("QU"))
                        {
                            ceDate = ceDate.subtractMonths(3);
                        }

                        else if (paymentMode.equalsIgnoreCase("SA"))
                        {
                            ceDate = ceDate.subtractMonths(6);
                        }

                        else if (paymentMode.equalsIgnoreCase("AN"))
                        {
                            ceDate = ceDate.subtractYears(1);
                        }

                        terminationDate = ceDate.getFormattedDate();
                    }

//                    StringTokenizer termDateST = new StringTokenizer(terminationDate, EDITDate.DATE_DELIMITER);
//                    String terminationYear = termDateST.nextToken();
//                    String terminationMonth = termDateST.nextToken();
//                    String terminationDay = termDateST.nextToken();
//                    terminationDate = terminationMonth + EDITDate.DATE_DELIMITER + terminationDay + EDITDate.DATE_DELIMITER + terminationYear;
                    terminationDate = DateTimeUtil.formatYYYYMMDDToMMDDYYYY(terminationDate);

                    String certainPeriodEndDate = payoutVO[0].getCertainPeriodEndDate();

                    if ((certainPeriodEndDate != null) && !certainPeriodEndDate.equals(""))
                    {
                        if (certainPeriodEndDate.equals(EDITDate.DEFAULT_MAX_DATE))
                        {
                            certainPeriodEndDate = null;
                        }

                        else
                        {
                            EDITDate ceDate = new EDITDate(certainPeriodEndDate);

                            if (paymentMode.equalsIgnoreCase("MO"))
                            {
                                ceDate = ceDate.subtractMonths(1);
                            }

                            else if (paymentMode.equalsIgnoreCase("QU"))
                            {
                                ceDate = ceDate.subtractMonths(3);
                            }

                            else if (paymentMode.equalsIgnoreCase("SA"))
                            {
                                ceDate = ceDate.subtractMonths(6);
                            }

                            else if (paymentMode.equalsIgnoreCase("AN"))
                            {
                                ceDate = ceDate.subtractYears(1);
                            }

                            certainPeriodEndDate = ceDate.getFormattedDate();
                        }

//                        StringTokenizer cpeDateST = new StringTokenizer(certainPeriodEndDate, EDITDate.DATE_DELIMITER);
//                        String cpeYear = cpeDateST.nextToken();
//                        String cpeMonth = cpeDateST.nextToken();
//                        String cpeDay = cpeDateST.nextToken();
//                        certainPeriodEndDate = cpeMonth + EDITDate.DATE_DELIMITER + cpeDay + EDITDate.DATE_DELIMITER + cpeYear;
                        certainPeriodEndDate = DateTimeUtil.formatYYYYMMDDToMMDDYYYY(certainPeriodEndDate);
                    }

                    else
                    {
                        certainPeriodEndDate = "";
                    }

                    //SRAMAM 09/2004 DOUBLE2DECIMAL
                    //                String reducePercent1 = Util.formatDecimal("0.00####", payoutVO[0].getReducePercent1());
                    //                String reducePercent2 = Util.formatDecimal("0.00####", payoutVO[0].getReducePercent2());
                    String reducePercent1 = Util.formatDecimal("0.00####", new EDITBigDecimal(payoutVO[0].getReducePercent1()));
                    String reducePercent2 = Util.formatDecimal("0.00####", new EDITBigDecimal(payoutVO[0].getReducePercent2()));

                    String lastProcessingDate = DateTimeUtil.formatYYYYMMDDToMMDDYYYY(valuationDate);

                    ValuationVO valuationVO = new ValuationVO();

                    valuationVO.setEffectiveYear(editEffectiveDate.getFormattedYear());
                    valuationVO.setPlanId(planId);
                    valuationVO.setContractNumber(contractNumber);
                    valuationVO.setNumberOfContracts(numberOfContracts);
                    valuationVO.setCompanyName(companyName);
                    valuationVO.setIssueState(issueState);
                    valuationVO.setQualifiedIndicator(qualNonQual);
                    valuationVO.setStatus(status);
                    valuationVO.setKindOfAnnuity(kindOfAnnuity);
                    valuationVO.setIssueDate(issueDate);
                    valuationVO.setTotalGrossConsideration(totalGrossConsid);
                    valuationVO.setConsiderationPayMode(considPayMode);
                    valuationVO.setFundsHeldOnDeposit(fundsHeldOnDep);
                    valuationVO.setNumberOfLives(numberOfLives);
                    valuationVO.setDeathStatus(deathStatus);
                    valuationVO.setFirstPayIssueAge(firstPayIssueAge);
                    valuationVO.setFirstPaySex(firstPaySex);
                    valuationVO.setFirstPayDateOfDeath(firstPayDateOfDeath);
                    valuationVO.setSecondPayIssueAge(secondPayIssueAge);
                    valuationVO.setSecondPaySex(secondPaySex);
                    valuationVO.setSecondPayDateOfDeath(secondPayDateOfDeath);
                    valuationVO.setPaymentAmount(paymentAmount);
                    valuationVO.setPaymentStartDate(paymentStartDate);
                    valuationVO.setTerminationDate(terminationDate);
                    valuationVO.setPaymentMode(paymentMode);
                    valuationVO.setCertainPeriodEndDate(certainPeriodEndDate);
                    valuationVO.setReducePercent1(reducePercent1);
                    valuationVO.setReducePercent2(reducePercent2);
                    valuationVO.setLastProcessingDate(lastProcessingDate);

                    valuationHT.put(contractNumber, valuationVO);
                }
            }

            Map sortedValuation = sortValuation(valuationHT);

            Iterator it = sortedValuation.values().iterator();

            while (it.hasNext())
            {
                ValuationVO valuationVO = (ValuationVO) it.next();

                if (fileData.length() > 0)
                {
                    fileData.append("\n");
                }

                else
                {
                    fileData.append("BLOCKID,PLANID,CONTRACT,CONTRACTS,COMPANY,MARKET,AGENCY,");
                    fileData.append("STATE,QUALIFIED,STATUS,KIND,IDATE,OY,GCO,GCMODE,FHD0,LIVES,");
                    fileData.append("DSTATUS,IAX,SEXX,XAQDX,DDATEX,IAY,SEXY,XAQDY,DDATEY,BA,");
                    fileData.append("BASDATE,BAEDATE,BAMODE,BACPEDATE,BACONTFACX,BACONTFACY,LPDATE");
                    fileData.append("\n");
                }

                fileData.append(valuationVO.getEffectiveYear());
                fileData.append(",");
                fileData.append(valuationVO.getPlanId());
                fileData.append(",");
                fileData.append(valuationVO.getContractNumber());
                fileData.append(",");
                fileData.append(valuationVO.getNumberOfContracts());
                fileData.append(",");
                fileData.append(valuationVO.getCompanyName());
                fileData.append(",");

                //          Market would be here - left blank intentionally
                fileData.append(",");

                //          Agency would be here - left blank intentionally
                fileData.append(",");
                fileData.append(valuationVO.getIssueState());
                fileData.append(",");
                fileData.append(valuationVO.getQualifiedIndicator());
                fileData.append(",");
                fileData.append(valuationVO.getStatus());
                fileData.append(",");
                fileData.append(valuationVO.getKindOfAnnuity());
                fileData.append(",");
                fileData.append(valuationVO.getIssueDate());
                fileData.append(",");

                //          Original Contract Issue Year would be here - left blank intentionally
                fileData.append(",");
                fileData.append(valuationVO.getTotalGrossConsideration());
                fileData.append(",");
                fileData.append(valuationVO.getConsiderationPayMode());
                fileData.append(",");
                fileData.append(valuationVO.getFundsHeldOnDeposit());
                fileData.append(",");
                fileData.append(valuationVO.getNumberOfLives());
                fileData.append(",");
                fileData.append(valuationVO.getDeathStatus());
                fileData.append(",");
                fileData.append(valuationVO.getFirstPayIssueAge());
                fileData.append(",");
                fileData.append(valuationVO.getFirstPaySex());
                fileData.append(",");

                if (valuationVO.getFirstPaySubStd() != null)
                {
                    fileData.append(valuationVO.getFirstPaySubStd());
                }

                fileData.append(",");
                fileData.append(valuationVO.getFirstPayDateOfDeath());
                fileData.append(",");
                fileData.append(valuationVO.getSecondPayIssueAge());
                fileData.append(",");
                fileData.append(valuationVO.getSecondPaySex());
                fileData.append(",");

                if (valuationVO.getSecondPaySubStd() != null)
                {
                    fileData.append(valuationVO.getSecondPaySubStd());
                }

                fileData.append(",");
                fileData.append(valuationVO.getSecondPayDateOfDeath());
                fileData.append(",");
                fileData.append(valuationVO.getPaymentAmount());
                fileData.append(",");
                fileData.append(valuationVO.getPaymentStartDate());
                fileData.append(",");
                fileData.append(valuationVO.getTerminationDate());
                fileData.append(",");
                fileData.append(valuationVO.getPaymentMode());
                fileData.append(",");
                fileData.append(valuationVO.getCertainPeriodEndDate());
                fileData.append(",");
                fileData.append(valuationVO.getReducePercent1());
                fileData.append(",");
                fileData.append(valuationVO.getReducePercent2());
                fileData.append(",");
                fileData.append(valuationVO.getLastProcessingDate());
            }

            exportExtract(fileData, "ValuationExtract");

            CodeTableVO[] optionCodeCTVO = codeTableWrapper.getCodeTableEntries("OPTIONCODE");
            CodeTableVO[] transactionCTVO = codeTableWrapper.getCodeTableEntries("TRXTYPE");

            String optionCode = "AMC";
            String transactionType = "LS";

            SegmentVO[] segmentsForBenefitExtract = contract.dm.dao.DAOFactory.getSegmentDAO().findAllSegmentsForBenefitExtract(valuationDate, optionCode, transactionType, productStructureIds);

            for (int s = 0; s < segmentsForBenefitExtract.length; s++)
            {
                variableFundFound = false;

                InvestmentVO[] investmentVOs = segmentsForBenefitExtract[s].getInvestmentVO();

                for (int a = 0; a < investmentVOs.length; a++)
                {
                    long filteredFundFK = investmentVOs[a].getFilteredFundFK();
                    FilteredFundVO[] filteredFundVO = calcLookup.findFilteredFundByPK(filteredFundFK, false, null);

                    String fundType = (String) fundsHT.get(filteredFundVO[0].getFundFK() + "");

                    if (fundType.equalsIgnoreCase("V"))
                    {
                        variableFundFound = true;

                        break;
                    }
                }

                if (!variableFundFound)
                {
                    String contractNumber = segmentsForBenefitExtract[s].getContractNumber();
                    String effectiveYear = new EDITDate(segmentsForBenefitExtract[s].getEffectiveDate()).getFormattedYear();
                    String planId = "SPIA";

                    String segmentStatus = segmentsForBenefitExtract[s].getSegmentStatusCT();

                    String numberOfLives = "";
                    optionCode = segmentsForBenefitExtract[s].getOptionCodeCT();

                    if (optionCode.equalsIgnoreCase("PCA") || optionCode.equalsIgnoreCase("INT") || optionCode.equalsIgnoreCase("AMC"))
                    {
                        numberOfLives = "0";
                    }

                    else if (optionCode.equalsIgnoreCase("LOA") || optionCode.equalsIgnoreCase("LPC"))
                    {
                        numberOfLives = "1";
                    }

                    else if (optionCode.equalsIgnoreCase("JCP") || optionCode.equalsIgnoreCase("JSA"))
                    {
                        numberOfLives = "2";
                    }

                    long productStructureFK = segmentsForBenefitExtract[s].getProductStructureFK();

                    ProductStructureVO productStructureVO = (ProductStructureVO) companyCache.get(productStructureFK + "");

                    String businessContract = productStructureVO.getBusinessContractName();
                    String deathStatus = "";

                    if (businessContract.equalsIgnoreCase("GSN") || businessContract.equalsIgnoreCase("SN") || businessContract.equalsIgnoreCase("SP"))
                    {
                        deathStatus = "0";
                    }

                    else
                    {
                        if (businessContract.equalsIgnoreCase("SL") || businessContract.equalsIgnoreCase("SW") || businessContract.equalsIgnoreCase("GSW"))
                        {
                            if (segmentStatus.equalsIgnoreCase("Active") || segmentStatus.equalsIgnoreCase("Commit") || segmentStatus.equalsIgnoreCase("DeathCertain"))
                            {
                                deathStatus = "0";
                            }

                            else if (segmentStatus.equalsIgnoreCase("DeathLife") || segmentStatus.equalsIgnoreCase("DeathLump") || segmentStatus.equalsIgnoreCase("JointDeathPrimary"))
                            {
                                if (segmentStatus.equalsIgnoreCase("DeathLump") && (optionCode.equalsIgnoreCase("JPC") || optionCode.equalsIgnoreCase("JSA")))
                                {
                                    deathStatus = "3";
                                }

                                else
                                {
                                    deathStatus = "1";
                                }
                            }

                            else if (segmentStatus.equalsIgnoreCase("JointDeathSecondary"))
                            {
                                deathStatus = "2";
                            }
                        }
                    }

                    String firstPayIssueAge = "";
                    String firstPaySex = "";
                    String firstPaySubStdRatio = "1";
                    String firstPaySubStdRatioPeriod = "999";
                    String firstPaySubStdConstant = "0";
                    String firstPaySubStdConstantPeriod = "999";
                    String firstPaySubStdAge = "0";
                    String secondPayIssueAge = "";
                    String secondPaySex = "";
                    String secondPaySubStdRatio = "";
                    String secondPaySubStdRatioPeriod = "";
                    String secondPaySubStdConstant = "";
                    String secondPaySubStdConstantPeriod = "";
                    String secondPaySubStdAge = "";
                    String firstPayDateOfDeath = "";
                    String secondPayDateOfDeath = "";

                    //                ClientRelationshipVO[] clientRelationshipVOs = segmentsForBenefitExtract[s].getClientRelationshipVO();
                    //
                    //                boolean firstPayeeInfoSet = false;
                    //
                    //                for (int r = 0; r < clientRelationshipVOs.length; r++) {
                    //
                    //                    PayeeVO[] payeeVO = clientRelationshipVOs[r].getPayeeVO();
                    //                    if (payeeVO != null && payeeVO.length > 0) {
                    //
                    //                        String clientFK = clientRelationshipVOs[r].getClientFK() + "";
                    //                        ClientDetailVO clientDetailVO = (ClientDetailVO) clientCache.get(clientFK);
                    //
                    //                        if (!businessContract.equalsIgnoreCase("SN") &&
                    //                            !businessContract.equalsIgnoreCase("SP") &&
                    //                            !businessContract.equalsIgnoreCase("GSN")) {
                    //
                    //                            if (!firstPayeeInfoSet) {
                    //
                    //                                firstPaySex = clientDetailVO.getGenderCT();
                    //                                if (firstPaySex.equalsIgnoreCase("Male")) {
                    //
                    //                                    firstPaySex = "0";
                    //                                }
                    //
                    //                                else if (firstPaySex.equalsIgnoreCase("Female")) {
                    //
                    //                                    firstPaySex = "1";
                    //                                }
                    //
                    //                                firstPayIssueAge = clientRelationshipVOs[r].getIssueAge() + "";
                    //
                    //                                ClientRelSubStandardVO[] clientSubStandardVO =
                    //                                        clientRelationshipVOs[r].getClientRelSubStandardVO();
                    //
                    //                                if (clientSubStandardVO != null && clientSubStandardVO.length > 0) {
                    //
                    //                                    String subStdRating = clientSubStandardVO[0].getSubstandardTableRating();
                    //                                    double flatExtra = clientSubStandardVO[0].getFlatExtraAmount();
                    //                                    int flatExtraDur = clientSubStandardVO[0].getFlatExtraAmtDur();
                    //
                    //                                    if (subStdRating != null && !subStdRating.equals("")) {
                    //
                    //                                        firstPaySubStdRatio = clientSubStandardVO[0].getSubstandardTableRating();
                    //                                    }
                    //
                    //                                    if (flatExtra > 0) {
                    //
                    //                                        firstPaySubStdConstant =
                    //                                                Util.formatDecimal("########0.00",
                    //                                                                     clientSubStandardVO[0].getFlatExtraAmount());
                    //                                    }
                    //
                    //                                    if (flatExtraDur > 0) {
                    //
                    //                                        firstPaySubStdConstantPeriod = clientSubStandardVO[0].getFlatExtraAmtDur() + "";
                    //                                    }
                    //                                }
                    //
                    //                                StringTokenizer dateOfDeath = new StringTokenizer
                    //                                                              (clientDetailVO.getDateOfDeath(), "/");
                    //                                String dateOfDeathYear = dateOfDeath.nextToken();
                    //                                String dateOfDeathMonth = dateOfDeath.nextToken();
                    //                                String dateOfDeathDay = dateOfDeath.nextToken();
                    //                                firstPayDateOfDeath = dateOfDeathMonth + "/" +
                    //                                                      dateOfDeathDay   + "/" + dateOfDeathYear;
                    //
                    //                                firstPayeeInfoSet = true;
                    //                            }
                    //
                    //                            else {
                    //
                    //                                secondPaySex = clientDetailVO.getGenderCT();
                    //                                if (secondPaySex.equalsIgnoreCase("Male")) {
                    //
                    //                                    secondPaySex = "0";
                    //                                }
                    //
                    //                                else if (secondPaySex.equalsIgnoreCase("Female")) {
                    //
                    //                                    secondPaySex = "1";
                    //                                }
                    //
                    //                                secondPayIssueAge = clientRelationshipVOs[r].getIssueAge() + "";
                    //
                    //                                ClientRelSubStandardVO[] clientSubStandardVO =
                    //                                        clientRelationshipVOs[r].getClientRelSubStandardVO();
                    //
                    //                                secondPaySubStdRatio = "1";
                    //                                secondPaySubStdRatioPeriod = "999";
                    //                                secondPaySubStdConstant = "0";
                    //                                secondPaySubStdConstantPeriod = "999";
                    //                                secondPaySubStdAge   = "0";
                    //
                    //                                if (clientSubStandardVO != null && clientSubStandardVO.length > 0) {
                    //
                    //                                    String subStdRating = clientSubStandardVO[0].getSubstandardTableRating();
                    //                                    double flatExtra = clientSubStandardVO[0].getFlatExtraAmount();
                    //                                    int flatExtraDur = clientSubStandardVO[0].getFlatExtraAmtDur();
                    //
                    //                                    if (subStdRating != null && !subStdRating.equals("")) {
                    //
                    //                                        secondPaySubStdRatio = clientSubStandardVO[0].getSubstandardTableRating();
                    //                                    }
                    //
                    //                                    if (flatExtra > 0) {
                    //
                    //                                        secondPaySubStdConstant =
                    //                                                Util.formatDecimal("########0.00",
                    //                                                                     clientSubStandardVO[0].getFlatExtraAmount());
                    //                                    }
                    //
                    //                                    if (flatExtraDur > 0) {
                    //
                    //                                        secondPaySubStdConstantPeriod = clientSubStandardVO[0].getFlatExtraAmtDur() + "";
                    //                                    }
                    //                                }
                    //
                    //                                StringTokenizer dateOfDeath = new StringTokenizer(clientDetailVO.getDateOfDeath(), "/");
                    //                                String dateOfDeathYear = dateOfDeath.nextToken();
                    //                                String dateOfDeathMonth = dateOfDeath.nextToken();
                    //                                String dateOfDeathDay = dateOfDeath.nextToken();
                    //                                secondPayDateOfDeath = dateOfDeathMonth + "/" +
                    //                                                       dateOfDeathDay   + "/" + dateOfDeathYear;
                    //
                    //                                break;
                    //                            }
                    //                        }
                    //                    }
                    //                }
                    PayoutVO[] payoutVO = segmentsForBenefitExtract[s].getPayoutVO();

                    String paymentMode = payoutVO[0].getPaymentFrequencyCT();
                    String benefitAmount = "";
                    String benefitStartDate = "";
                    String benefitEndDate = "";
                    String benefitMode = "0";
                    String certainPeriodEndDate = "";

                    if (optionCode.equalsIgnoreCase("AMC"))
                    {
                        //benefitAmount = Util.formatDecimal("########0.00", payoutVO[0].getFinalDistributionAmount());
                        benefitAmount = Util.formatDecimal("########0.00", new EDITBigDecimal(payoutVO[0].getFinalDistributionAmount()));

                        EDITDate ceDate = new EDITDate(segmentsForBenefitExtract[s].getTerminationDate());

                        if (paymentMode.equalsIgnoreCase("Monthly"))
                        {
                            ceDate = ceDate.subtractMonths(1);
                        }

                        else if (paymentMode.equalsIgnoreCase("Quarterly"))
                        {
                            ceDate = ceDate.subtractMonths(3);
                        }

                        else if (paymentMode.equalsIgnoreCase("SemiAnnual"))
                        {
                            ceDate = ceDate.subtractMonths(6);
                        }

                        else if (paymentMode.equalsIgnoreCase("Annual"))
                        {
                            ceDate = ceDate.subtractYears(1);
                        }

                        benefitStartDate = DateTimeUtil.formatYYYYMMDDToMMDDYYYY(ceDate.getFormattedDate());
                    }
                    else
                    {
                        //                    TransactionsVO[] transactionsVOs = segmentsForBenefitExtract[s].getTransactionsVO();
                        //
                        //                    for (int t = 0; t < transactionsVOs.length; t++) {
                        //
                        //                        if (transactionsVOs[t].getTransactionType().equalsIgnoreCase(transactionType)) {
                        //
                        //                            benefitAmount = Util.formatDecimal("########0.00", transactionsVOs[t].getAmount());
                        //                            ScheduledEventVO[] scheduledEventVO = transactionsVOs[t].getScheduledEventVO();
                        //                            StringTokenizer startDateST = new StringTokenizer(scheduledEventVO[0].getStartDate(), "/");
                        //                            String benefitStartYear = startDateST.nextToken();
                        //                            String benefitStartMonth = startDateST.nextToken();
                        //                            String benefitStartDay = startDateST.nextToken();
                        //                            benefitStartDate = benefitStartMonth + "/" +
                        //                                               benefitStartDay + "/" + benefitStartYear;
                        //                        }
                        //                    }
                    }

                    benefitEndDate = benefitStartDate;
                    certainPeriodEndDate = benefitStartDate;

                    BenefitExtractVO benefitExtractVO = new BenefitExtractVO();

                    benefitExtractVO.setEffectiveYear(effectiveYear);
                    benefitExtractVO.setPlanId(planId);
                    benefitExtractVO.setContractNumber(contractNumber);
                    benefitExtractVO.setNumberOfLives(numberOfLives);
                    benefitExtractVO.setFirstPayIssueAge(firstPayIssueAge);
                    benefitExtractVO.setFirstPaySex(firstPaySex);
                    benefitExtractVO.setFirstPaySubStdRatio(firstPaySubStdRatio);
                    benefitExtractVO.setFirstPaySubStdRatioPeriod(firstPaySubStdRatioPeriod);
                    benefitExtractVO.setFirstPaySubStdConstant(firstPaySubStdConstant);
                    benefitExtractVO.setFirstPaySubStdConstantPeriod(firstPaySubStdConstantPeriod);
                    benefitExtractVO.setFirstPaySubStdAge(firstPaySubStdAge);
                    benefitExtractVO.setSecondPayIssueAge(secondPayIssueAge);
                    benefitExtractVO.setSecondPaySex(secondPaySex);
                    benefitExtractVO.setSecondPaySubStdRatio(secondPaySubStdRatio);
                    benefitExtractVO.setSecondPaySubStdRatioPeriod(secondPaySubStdRatioPeriod);
                    benefitExtractVO.setSecondPaySubStdConstant(secondPaySubStdConstant);
                    benefitExtractVO.setSecondPaySubStdConstantPeriod(secondPaySubStdConstantPeriod);
                    benefitExtractVO.setSecondPaySubStdAge(secondPaySubStdAge);
                    benefitExtractVO.setFirstPayDateOfDeath(firstPayDateOfDeath);
                    benefitExtractVO.setSecondPayDateOfDeath(secondPayDateOfDeath);
                    benefitExtractVO.setDeathStatus(deathStatus);
                    benefitExtractVO.setBenefitAmount(benefitAmount);
                    benefitExtractVO.setBenefitStartDate(benefitStartDate);
                    benefitExtractVO.setBenefitEndDate(benefitEndDate);
                    benefitExtractVO.setBenefitMode(benefitMode);
                    benefitExtractVO.setCertainPeriodEndDate(certainPeriodEndDate);

                    benefitExtractHT.put(contractNumber, benefitExtractVO);
                }
            }

            Map sortedBenefitExtract = sortBenefitExtract(benefitExtractHT);

            it = sortedBenefitExtract.values().iterator();

            while (it.hasNext())
            {
                BenefitExtractVO benefitExtractVO = (BenefitExtractVO) it.next();

                if (fileData2.length() > 0)
                {
                    fileData2.append("\n");
                }

                else
                {
                    fileData2.append("BLOCKID,PLANID,CONTRACT,LIVES,IAX,SEXX,RQDX,NRQDX,KQDX,");
                    fileData2.append("NKQDX,XAQDX,IAY,SEXY,RQDY,NRQDY,KQDY,NKQDY,XAQDY,DDATEX,");
                    fileData2.append("DDATEY,DSTATUS,BA,BASDATE,BAEDATE,BAMODE,BACPEDATE,XBA,");
                    fileData2.append("XBASDATE,XBAEDATE,XBAFREQ,XBF,XBFSDATE,XBFEDATE,SBFFREQ,");
                    fileData2.append("XBFM,BACONTFACX,BACONTFACY,MISC1,MISC2,MISC3");
                    fileData2.append("\n");
                }

                fileData2.append(benefitExtractVO.getEffectiveYear());
                fileData2.append(",");
                fileData2.append(benefitExtractVO.getPlanId());
                fileData2.append(",");
                fileData2.append(benefitExtractVO.getContractNumber());
                fileData2.append(",");
                fileData2.append(benefitExtractVO.getNumberOfLives());
                fileData2.append(",");
                fileData2.append(benefitExtractVO.getFirstPayIssueAge());
                fileData2.append(",");
                fileData2.append(benefitExtractVO.getFirstPaySex());
                fileData2.append(",");
                fileData2.append(benefitExtractVO.getFirstPaySubStdRatio());
                fileData2.append(",");
                fileData2.append(benefitExtractVO.getFirstPaySubStdRatioPeriod());
                fileData2.append(",");
                fileData2.append(benefitExtractVO.getFirstPaySubStdConstant());
                fileData2.append(",");
                fileData2.append(benefitExtractVO.getFirstPaySubStdConstantPeriod());
                fileData2.append(",");
                fileData2.append(benefitExtractVO.getFirstPaySubStdAge());
                fileData2.append(",");
                fileData2.append(benefitExtractVO.getSecondPayIssueAge());
                fileData2.append(",");
                fileData2.append(benefitExtractVO.getSecondPaySex());
                fileData2.append(",");
                fileData2.append(benefitExtractVO.getSecondPaySubStdRatio());
                fileData2.append(",");
                fileData2.append(benefitExtractVO.getSecondPaySubStdRatioPeriod());
                fileData2.append(",");
                fileData2.append(benefitExtractVO.getSecondPaySubStdConstant());
                fileData2.append(",");
                fileData2.append(benefitExtractVO.getSecondPaySubStdConstantPeriod());
                fileData2.append(",");
                fileData2.append(benefitExtractVO.getSecondPaySubStdAge());
                fileData2.append(",");
                fileData2.append(benefitExtractVO.getFirstPayDateOfDeath());
                fileData2.append(",");
                fileData2.append(benefitExtractVO.getSecondPayDateOfDeath());
                fileData2.append(",");
                fileData2.append(benefitExtractVO.getDeathStatus());
                fileData2.append(",");
                fileData2.append(benefitExtractVO.getBenefitAmount());
                fileData2.append(",");
                fileData2.append(benefitExtractVO.getBenefitStartDate());
                fileData2.append(",");
                fileData2.append(benefitExtractVO.getBenefitEndDate());
                fileData2.append(",");
                fileData2.append(benefitExtractVO.getBenefitMode());
                fileData2.append(",");
                fileData2.append(benefitExtractVO.getCertainPeriodEndDate());
                fileData2.append(",");

                //          Benefit Change Amount would be here - left blank intentionally
                fileData2.append(",");

                //          Start Date of Amount Change would be here - left blank intentionally
                fileData2.append(",");

                //          End Date of Amount Change would be here - left blank intentionally
                fileData2.append(",");

                //          Frequency of Amount Change would be here - left blank intentionally
                fileData2.append(",");

                //          Benefit Change Factor would be here - left blank intentionally
                fileData2.append(",");

                //          Start Date of Factor Change would be here - left blank intentionally
                fileData2.append(",");

                //          End Date of Factor Change would be here - left blank intentionally
                fileData2.append(",");

                //          Frequency of Factor Change would be here - left blank intentionally
                fileData2.append(",");

                //          Factor Change Method would be here - left blank intentionally
                fileData2.append(",");

                //          Benefit Continuation Factor 1st payee would be here - left blank intentionally
                fileData2.append(",");

                //          Benefit Continuation Factor 2nd payee would be here - left blank intentionally
                fileData2.append(",");

                //          Misc1 would be here - left blank intentionally
                fileData2.append(",");

                //          Misc2 would be here - left blank intentionally
                fileData2.append(",");

                //          Misc3 would be here - left blank intentionally
                EditServiceLocator.getSingleton().getBatchAgent().getBatchStat(Batch.BATCH_JOB_CREATE_VALUATION_EXTRACTS).updateSuccess();
            }

            exportExtract(fileData2, "BenefitExtract");
        }
        catch (Exception e)
        {
            EditServiceLocator.getSingleton().getBatchAgent().getBatchStat(Batch.BATCH_JOB_CREATE_VALUATION_EXTRACTS).updateFailure();

          System.out.println(e);

            e.printStackTrace();
        }

        return null;
    }

    private TreeMap sortValuation(Map valuationHT)
    {
        TreeMap sortedValuationRecords = new TreeMap();

        Iterator enumer = valuationHT.values().iterator();

        while (enumer.hasNext())
        {
            ValuationVO valuationVO = (ValuationVO) enumer.next();

            String effectiveYear = valuationVO.getEffectiveYear();
            String planId = valuationVO.getPlanId();
            String contractNumber = valuationVO.getContractNumber();

            sortedValuationRecords.put(effectiveYear + planId + contractNumber, valuationVO);
        }

        return sortedValuationRecords;
    }

    private TreeMap sortBenefitExtract(Map benefitExtractHT)
    {
        TreeMap benefitExtractMap = new TreeMap();

        Iterator enumer = benefitExtractHT.values().iterator();

        while (enumer.hasNext())
        {
            BenefitExtractVO benefitExtractVO = (BenefitExtractVO) enumer.next();

            String effectiveYear = benefitExtractVO.getEffectiveYear();
            String planId = benefitExtractVO.getPlanId();
            String contractNumber = benefitExtractVO.getContractNumber();

            benefitExtractMap.put(effectiveYear + planId + contractNumber, benefitExtractVO);
        }

        return benefitExtractMap;
    }

    public void exportExtract(StringBuffer fileData, String fileName)
    {
        super.exportData(fileData.toString(), fileName);
    }
}
