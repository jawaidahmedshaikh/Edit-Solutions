/*
 * User: sprasad
 * Date: Apr 3, 2007
 * Time: 10:06:11 AM
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package event.batch;

import edit.common.EDITBigDecimal;
import edit.common.EDITDate;
import edit.common.vo.ControlBalanceDetailVO;
import edit.common.vo.ControlBalanceVO;
import edit.common.vo.EDITExport;
import edit.common.vo.FilteredFundVO;
import edit.common.vo.ProductFilteredFundStructureVO;
import edit.common.vo.UnitValuesVO;

import edit.services.config.ServicesConfig;
import edit.services.logging.Logging;

import engine.ChargeCode;
import engine.ProductStructure;

import engine.dm.dao.ControlBalanceDAO;
import engine.dm.dao.ControlBalanceDetailDAO;

import engine.util.TransformChargeCodes;

import fission.utility.Util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import logging.LogEvent;

import org.apache.logging.log4j.Logger;

import reporting.batch.ReportsProcessor;

import businesscalendar.BusinessDay;
import businesscalendar.BusinessCalendar;

public class ActivityFileExportProcessor
{
    private static final String FILE_NAME_PREFIX = "HNW";

    private static final String FILE_EXTENSION = ".prn";

    private Map outputLines = new TreeMap();

    public static final String[] DATE_TYPES = new String[] {"Accounting Period", "Process Date"};

    public static final String[] DATE_TYPE_VALUES = new String[] {"AccountingPeriod", "ProcessDate"};

    /**
     * Generates extract file for all fund activities for given date range.
     * @param marketingPackageName
     * @param selectedFundPKs
     * @param dateType
     * @param startDate
     * @param endDate
     */
    public void exportFundActivity(String marketingPackageName, String selectedFundPKs, String dateType, String startDate, String endDate)
    {
        try
        {
            String fileName = FILE_NAME_PREFIX + endDate.substring(2, 4) + endDate.substring(5, 7);

            EDITExport export1 = ServicesConfig.getEDITExport("ExportDirectory1");

            File exportFile = new File(export1.getDirectory() + fileName +  FILE_EXTENSION);
            
            String[] selectedFundPKsAsArray = Util.fastTokenizer(selectedFundPKs, ",");

            for (int i = 0; i < selectedFundPKsAsArray.length; i++)
            {
                String selectedFundPK = selectedFundPKsAsArray[i];

                FilteredFundVO[] filteredFundVOs = new ReportsProcessor().getFilteredFunds(marketingPackageName, selectedFundPK);

                String[] fundKeys = getSortedFunds(filteredFundVOs);

                engine.business.Lookup engineLookupComponent = new engine.component.LookupComponent();

                List productStructurePKs = getProductStructurePKs(marketingPackageName);

                List voInclusionList = new ArrayList();
                voInclusionList.add(ProductFilteredFundStructureVO.class);

//                boolean lastLine = false;

                for (int j = 0; j < fundKeys.length; j++)
                {
                    String fundKey = fundKeys[j];
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

                    FilteredFundVO[] filteredFundVO = engineLookupComponent.composeFilteredFundVOByFilteredFundPK(Long.parseLong(filteredFundFK), voInclusionList);

                    String pricingDirection = filteredFundVO[0].getPricingDirection();

                    long[] chargeCodeFKsToUse = getChargeCodeFKsToUse(chargeCodeFK, Long.parseLong(filteredFundFK));

                    ProductFilteredFundStructureVO[] productFilteredFundStructureVOs = getProductFilteredFundStructures(filteredFundVO);

//                    if ((i == selectedFundPKs.length -1) && (j == fundKeys.length - 1))
//                    {
//                        lastLine = true;
//                    }

                    generateExportFileRecord(fundNumber,
                                             startDate,
                                             endDate,
                                             Long.parseLong(filteredFundFK),
                                             pricingDirection,
                                             chargeCodeFKsToUse,
                                            productFilteredFundStructureVOs,
                                            productStructurePKs,
                                             marketingPackageName,
                                             dateType);

                    sortAndWriteMemoryLinesToFile(exportFile);
                }
            } // end for loop selectedFundPKs
        }
        catch (Exception e)
        {
            e.printStackTrace();

            LogEvent logEvent = new LogEvent("Activity File Export Errored", e);

            Logger logger = Logging.getLogger(Logging.BATCH_JOB);

            logger.error(logEvent);
        }
    }

    /**
     * Sorts the filtered fund on Fund Number ChargeCode.FundNumber if exists.
     * @param filteredFundVOs
     * @return
     */
    private String[] getSortedFunds(FilteredFundVO[] filteredFundVOs)
    {
        List fundKeys = new ArrayList();

        Map sortedFunds = new TreeMap();

        if (filteredFundVOs != null)
        {
            TransformChargeCodes transformChargeCodes = new TransformChargeCodes();

            for (int i = 0; i < filteredFundVOs.length; i++)
            {
                List clientFundNumbers = ChargeCode.getUniqueClientFundNumbers(filteredFundVOs[i].getFilteredFundPK());

                long[] chargeCodePKs = ChargeCode.getAllChargeCodePKsIncludingZero(filteredFundVOs[i].getFilteredFundPK());

                if (clientFundNumbers.size() > 1)
                {
                    for (int j = 0; j < chargeCodePKs.length; j++)
                    {
                        String clientFundNumber = transformChargeCodes.getClientFundNumber(chargeCodePKs[j]);

                        if (clientFundNumber == null)
                        {
                            clientFundNumber = filteredFundVOs[i].getFundNumber();
                        }

                        sortedFunds.put(clientFundNumber + "_" + filteredFundVOs[i].getFilteredFundPK() + "__" + chargeCodePKs[j], "");
                    }
                }
                else
                {
                    String clientFundNumber = transformChargeCodes.getClientFundNumber(chargeCodePKs[0]);

                    if (clientFundNumber == null)
                    {
                        clientFundNumber = filteredFundVOs[i].getFundNumber();
                    }

                    sortedFunds.put(clientFundNumber + "_" + filteredFundVOs[i].getFilteredFundPK(), "");
                }
            }
        }

        Set sortedFundKeys = sortedFunds.keySet();

        Iterator it = sortedFundKeys.iterator();

        while (it.hasNext())
        {
            fundKeys.add(it.next());
        }

        return (String[]) fundKeys.toArray(new String[fundKeys.size()]);
    }

    /**
     * Generates a record for each fund.
     * @param fundNumber
     * @param startDate
     * @param endDate
     * @param filteredFundFK
     * @param pricingDirection
     * @param chargeCodeFKsToUse
     * @param productFilteredFundStructureVOs
     * @param productStructurePKs
     * @param marketingPackage
     * @throws Exception
     */
    private void generateExportFileRecord(String fundNumber,
                                          String startDate,
                                          String endDate,
                                          long filteredFundFK,
                                          String pricingDirection,
                                          long[] chargeCodeFKsToUse,
                                          ProductFilteredFundStructureVO[] productFilteredFundStructureVOs,
                                          List productStructurePKs,
                                          String marketingPackage,
                                          String dateType) throws Exception
    {

        engine.business.Lookup engineLookup = new engine.component.LookupComponent();

        UnitValuesVO[] unitValuesVO = null;

        String effectiveDate = null;

        String startDateYear = Util.fastTokenizer(startDate, "/")[0];

        String startDateMonth = Util.fastTokenizer(startDate, "/")[1];

        String startDateDay = null;

        String endDateYear = Util.fastTokenizer(endDate, "/")[0];

        String endDateMonth = Util.fastTokenizer(endDate, "/")[1];

        String endDateDay = null;

        if (dateType.equals(DATE_TYPE_VALUES[0]))
        {
            startDateDay = "01";

            EDITDate endDateEDITDate = new EDITDate(endDate + "/01");

            effectiveDate = endDateEDITDate.getEndOfMonthDate().getFormattedDate();

            BusinessDay bestBusinessDay = new BusinessCalendar().getBestBusinessDay(new EDITDate(effectiveDate));

            effectiveDate = bestBusinessDay.getBusinessDate().getFormattedDate(); 
        }
        else if (dateType.equals(DATE_TYPE_VALUES[1]))
        {
            startDateDay = Util.fastTokenizer(startDate, "/")[2];

            endDateDay = Util.fastTokenizer(endDate, "/")[2];

            effectiveDate = endDate;
        }

        unitValuesVO = engineLookup.getUnitValuesByFilteredFundIdDateChargeCode(filteredFundFK,
                                                                                effectiveDate,
                                                                                pricingDirection,
                                                                                chargeCodeFKsToUse[0]);


        if (unitValuesVO != null && unitValuesVO.length > 0)
        {
            EDITBigDecimal netAssets = new EDITBigDecimal();
            EDITBigDecimal policyownerValue = new EDITBigDecimal();
            EDITBigDecimal accruedGainLoss = new EDITBigDecimal();
            EDITBigDecimal totalUnits = new EDITBigDecimal();

            EDITBigDecimal totalAccruedNetPremiums = new EDITBigDecimal();
            EDITBigDecimal totalAccruedCOI = new EDITBigDecimal();
            EDITBigDecimal totalAccruedSurrenders = new EDITBigDecimal();

            EDITBigDecimal totalAccruedAdminFees = new EDITBigDecimal();
            EDITBigDecimal totalAccruedReallocations = new EDITBigDecimal();
            EDITBigDecimal totalAccruedMAndE = new EDITBigDecimal();
            EDITBigDecimal totalAccruedAdvanceTransfers = new EDITBigDecimal();

            EDITBigDecimal mAndEPayablesReceivables = new EDITBigDecimal();
            EDITBigDecimal advisoryFeePayablesReceivables = new EDITBigDecimal();
            EDITBigDecimal mgtFeesPayablesReceivables = new EDITBigDecimal();
            EDITBigDecimal rvpPayablesReceivables = new EDITBigDecimal();

            EDITBigDecimal unitValue = new EDITBigDecimal(unitValuesVO[0].getUnitValue());
            EDITBigDecimal nav2Assets = new EDITBigDecimal(unitValuesVO[0].getNAV2Assets());
            EDITBigDecimal netAssetsLessPolicyownerValue = new EDITBigDecimal();
            EDITBigDecimal netAssetsLessMutualFundAssets = new EDITBigDecimal();

            for (int i = 0; i < productFilteredFundStructureVOs.length; i++)
            {
                for (int j = 0; j < chargeCodeFKsToUse.length; j++)
                {
                    if (isProductStructureSelected(marketingPackage, productStructurePKs, productFilteredFundStructureVOs[i].getProductStructureFK()))
                    {
                        ControlBalanceDetailVO[] controlBalanceDetailVOs = null;

                        long companyFilteredFundStructurePK = productFilteredFundStructureVOs[i].getProductFilteredFundStructurePK();

                        ControlBalanceDetailDAO controlBalanceDetailDAO = new ControlBalanceDetailDAO();

                        if (dateType.equals(DATE_TYPE_VALUES[0]))
                        {
                            controlBalanceDetailVOs = controlBalanceDetailDAO.findByCompanyFilteredFundStructrueFK_ChargeCodeFK_AccountingPeriodRange(companyFilteredFundStructurePK,
                                                                                                                                                      chargeCodeFKsToUse[j],
                                                                                                                                                      startDate,
                                                                                                                                                      endDate);
                        }

                        else if (dateType.equals(DATE_TYPE_VALUES[1]))
                        {
                            controlBalanceDetailVOs = controlBalanceDetailDAO.findByCompanyFilteredFundStructrueFK_ChargeCodeFK_EndingBalanceCycleDateRange(companyFilteredFundStructurePK,
                                                                                                                                                            chargeCodeFKsToUse[j],
                                                                                                                                                            startDate,
                                                                                                                                                            endDate);
                        }

                        if (controlBalanceDetailVOs != null && controlBalanceDetailVOs.length > 0)
                        {
                            for (int k = 0; k < controlBalanceDetailVOs.length; k++)
                            {
                                ControlBalanceDetailVO controlBalanceDetailVO = controlBalanceDetailVOs[k];

                                // The ControlBalanceDetail rows are ordered by EndingBalanceCycleDate, AccountingPeriod, EffectiveDate, CycleDate
                                // The default order is ascending and the following fileds need to be populated from last record values.
                                if (k == controlBalanceDetailVOs.length - 1)
                                {
                                    netAssets = new EDITBigDecimal(controlBalanceDetailVO.getNetAssets());
                                    policyownerValue = new EDITBigDecimal(controlBalanceDetailVO.getPolicyOwnerValue());
                                    totalUnits = new EDITBigDecimal(controlBalanceDetailVO.getUnitBalance());
                                }

                                // Cumulative fields ...
                                accruedGainLoss = accruedGainLoss.addEditBigDecimal(controlBalanceDetailVO.getAccruedGainLoss());

                                totalAccruedNetPremiums = totalAccruedNetPremiums.addEditBigDecimal(controlBalanceDetailVO.getTotalAccruedNetPremiums());
                                totalAccruedCOI = totalAccruedCOI.addEditBigDecimal(controlBalanceDetailVO.getTotalAccruedCOI());
                                totalAccruedSurrenders = totalAccruedSurrenders.addEditBigDecimal(controlBalanceDetailVO.getTotalAccruedSurrenders());

                                totalAccruedAdminFees = totalAccruedAdminFees.addEditBigDecimal(controlBalanceDetailVO.getTotalAccruedAdminFees());
                                totalAccruedReallocations = totalAccruedReallocations.addEditBigDecimal(controlBalanceDetailVO.getTotalAccruedReallocations());
                                totalAccruedMAndE = totalAccruedMAndE.addEditBigDecimal(controlBalanceDetailVO.getTotalAccruedMAndE());
                                totalAccruedAdvanceTransfers = totalAccruedAdvanceTransfers.addEditBigDecimal(controlBalanceDetailVO.getTotalAccruedAdvanceTransfers());

                                mAndEPayablesReceivables = mAndEPayablesReceivables.addEditBigDecimal(controlBalanceDetailVO.getMAndEPayableReceivables());
                                advisoryFeePayablesReceivables = advisoryFeePayablesReceivables.addEditBigDecimal(controlBalanceDetailVO.getAdvisoryFeePayablesReceivables());
                                mgtFeesPayablesReceivables = mgtFeesPayablesReceivables.addEditBigDecimal(controlBalanceDetailVO.getMgtFeePayablesReceivables());
                                rvpPayablesReceivables = rvpPayablesReceivables.addEditBigDecimal(controlBalanceDetailVO.getRVPPayablesReceivables());
                            }
                        }
                    }
                }
            }

            netAssetsLessPolicyownerValue = netAssets.subtractEditBigDecimal(policyownerValue);
            netAssetsLessMutualFundAssets = netAssets.subtractEditBigDecimal(nav2Assets);

            String endDateOutput = endDateYear + endDateMonth + endDateDay;
            String lowRunDateOutput = startDateYear + startDateMonth + startDateDay;

            StringBuffer fileData = new StringBuffer();
            fileData.append(fundNumber);
            fileData.append(",");
            fileData.append(lowRunDateOutput);
            fileData.append(",");
            fileData.append(endDateOutput);
            fileData.append(",");
            fileData.append(endDateOutput);
            fileData.append(",");
            fileData.append(Util.roundToNearestCent(netAssets));
            fileData.append(",");
            fileData.append(Util.roundToNearestCent(policyownerValue));
            fileData.append(",");
            fileData.append(Util.roundToNearestCent(accruedGainLoss));
            fileData.append(",");
            fileData.append(Util.formatDecimal("0.00000000", totalUnits));
            fileData.append(",");
            fileData.append(unitValue);
            fileData.append(",");
            fileData.append(Util.roundToNearestCent(netAssetsLessPolicyownerValue));
            fileData.append(",");
            // TotalAcccruedMAndE = blank
            fileData.append(",");
            fileData.append(Util.roundToNearestCent(totalAccruedNetPremiums));
            fileData.append(",");
            fileData.append(Util.roundToNearestCent(totalAccruedAdminFees));
            fileData.append(",");
            fileData.append(Util.roundToNearestCent(totalAccruedCOI));
            fileData.append(",");
            fileData.append(Util.roundToNearestCent(totalAccruedReallocations));
            fileData.append(",");
            // TotalAccuruedRRD = blank
            fileData.append(",");
            fileData.append(Util.roundToNearestCent(totalAccruedSurrenders));
            fileData.append(",");
            // TotalAccruedContribToMortRsv = blank
            fileData.append(",");
            fileData.append(Util.roundToNearestCent(totalAccruedMAndE));
            fileData.append(",");
            // TotalAccruedInvAdvisoryFees = blank
            fileData.append(",");
            // TotalAccruedMgmtFees = blank
            fileData.append(",");
            // TotalAccruedRVPFees = blank
            fileData.append(",");
            // TotalAccruedSeedMoney = blank
            fileData.append(",");
            fileData.append(Util.roundToNearestCent(totalAccruedAdvanceTransfers));
            fileData.append(",");
            // Total Gain/Loss On Seed Money = blank
            fileData.append(",");
            fileData.append(Util.roundToNearestCent(mAndEPayablesReceivables));
            fileData.append(",");
            fileData.append(Util.roundToNearestCent(advisoryFeePayablesReceivables));
            fileData.append(",");
            fileData.append(Util.roundToNearestCent(mgtFeesPayablesReceivables));
            fileData.append(",");
            fileData.append(Util.roundToNearestCent(rvpPayablesReceivables));
            fileData.append(",");
            fileData.append(Util.roundToNearestCent(netAssetsLessMutualFundAssets));
//            if (!lastLine)
//            {
//                fileData.append("\n");
//            }

            appendOutputLineInMemory(fileData.toString());
        }
    }

    /**
     * Determines if product structure is selected.
     * @param marketingPackage
     * @param productStructurePKs
     * @param productStructurePK
     * @return
     */
    private boolean isProductStructureSelected(String marketingPackage, List productStructurePKs, long productStructurePK)
    {
        boolean returnValue = false;

        if (marketingPackage.equalsIgnoreCase("All"))
        {
            returnValue = true;
        }
        else if (productStructurePKs.contains(productStructurePK + ""))
        {
            returnValue = true;
        }

        return returnValue;
    }

    /**
     * Holds records in memory.
     * @param data
     */
    private void appendOutputLineInMemory(String data)
    {
        String key = data.substring(0, 4);

        List linesForFund = new ArrayList();

        linesForFund.add(data);

        outputLines.put(key, linesForFund);
    }

    /**
     * Write to file.
     * @param exportFile
     * @throws IOException
     */
    private void sortAndWriteMemoryLinesToFile(File exportFile)
             throws IOException
    {
        FileWriter fw = new FileWriter(exportFile);
        BufferedWriter bw = new BufferedWriter(fw);

        Iterator it = outputLines.keySet().iterator();

        while (it.hasNext())
        {
            String key =  (String) it.next();
            List listOfLines = (List) outputLines.get(key);
            for (int i = 0; i < listOfLines.size(); i++)
            {
                String aLine =  (String) listOfLines.get(i);
                bw.write(aLine);
                bw.newLine();
            }
        }

        bw.close();
        fw.close();
    }

    /**
     * Return product structures for given marketing package.
     * @param marketingPackage
     * @return
     */
    private List getProductStructurePKs(String marketingPackage)
    {
        List productStructurePKs = new ArrayList();

        ProductStructure[] productStructures = ProductStructure.findByMarketingPackage(marketingPackage);

        for (int i = 0; i < productStructures.length; i++)
        {
            productStructurePKs.add(productStructures[i].getProductStructurePK().toString());
        }

        return productStructurePKs;
    }

    /**
     * Returns charge codes for given filtered fund.
     * @param chargeCodeFK
     * @param filteredFundPK
     * @return
     */
    private long[] getChargeCodeFKsToUse(long chargeCodeFK, long filteredFundPK)
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

    /**
     * Returns ProductFilteredFundStructures for given FilteredFunds.
     * @param filteredFundVOs
     * @return
     */
    private ProductFilteredFundStructureVO[] getProductFilteredFundStructures(FilteredFundVO[] filteredFundVOs)
    {
        List productFilteredFundStructures = new ArrayList();

        for (int i = 0; i < filteredFundVOs.length; i++)
        {
            ProductFilteredFundStructureVO[] productFilteredFundStructureVOs = filteredFundVOs[i].getProductFilteredFundStructureVO();

            for (int j = 0; j < productFilteredFundStructureVOs.length; j++)
            {
                productFilteredFundStructures.add(productFilteredFundStructureVOs[j]);
            }
        }

        return (ProductFilteredFundStructureVO[]) productFilteredFundStructures.toArray(new ProductFilteredFundStructureVO[productFilteredFundStructures.size()]);
    }
}
