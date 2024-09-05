/*
 * User: dlataill
 * Date: Jan 5, 2005
 * Time: 11:55:30 AM
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement
 */
package event.batch;

import batch.business.Batch;

import businesscalendar.BusinessCalendar;
import businesscalendar.BusinessDay;

import codetable.component.CodeTableComponent;

import contract.Investment;
import contract.Segment;

import edit.common.EDITDate;
import edit.common.vo.AreaValueVO;
import edit.common.vo.BucketVO;
import edit.common.vo.ChargeCodeVO;
import edit.common.vo.CoiReplenishmentVO;
import edit.common.vo.ProductStructureVO;
import edit.common.vo.DepositsVO;
import edit.common.vo.EDITExport;
import edit.common.vo.EDITTrxVO;
import edit.common.vo.FilteredFundVO;
import edit.common.vo.FundVO;
import edit.common.vo.InvestmentAllocationVO;
import edit.common.vo.InvestmentTransferValueVO;
import edit.common.vo.InvestmentVO;
import edit.common.vo.LifeVO;
import edit.common.vo.SPOutputVO;
import edit.common.vo.SegmentVO;
import edit.common.vo.VOObject;

import edit.services.EditServiceLocator;
import edit.services.config.ServicesConfig;
import edit.services.logging.Logging;

import engine.Area;
import engine.AreaValue;
import engine.ChargeCode;
import engine.ProductStructure;
import engine.FilteredFund;

import engine.sp.SPOutput;

import event.EDITTrx;

import event.financial.contract.trx.ContractEvent;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;

import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import logging.LogEvent;

import org.apache.logging.log4j.Logger;


public class COIReplenishmentProcessor implements Serializable
{
    private Map outputLines = new TreeMap();
    private boolean headerCreated;
    private String businessContractName;

    public void processCoiReplenishment(String productStructure, String runDate, String contractNumber)
    {
        this.headerCreated = false;
        this.businessContractName = "";
        EditServiceLocator.getSingleton().getBatchAgent().getBatchStat(Batch.BATCH_JOB_PROCESS_COI_REPLENISHMENT).tagBatchStart(Batch.BATCH_JOB_PROCESS_COI_REPLENISHMENT, "Coi Replenishment");

        try
        {
            contract.business.Lookup contractLookup = new contract.component.LookupComponent();

            SegmentVO[] segmentVOs = null;
            if (!contractNumber.equals(""))
            {
                getProductStructurePKs(productStructure);

                Segment segment = Segment.findByContractNumber(contractNumber);
                segmentVOs = new SegmentVO[1];
                segmentVOs[0] = (SegmentVO) segment.getVO();

                processSegment((SegmentVO)segment.getVO(), runDate, contractLookup);
            }
            else
            {
                long[] productStructurePKs = getProductStructurePKs(productStructure);

                for (int i = 0; i < productStructurePKs.length; i++)
                {
                    Segment[] segments = Segment.findByProductStructureFK(new Long(productStructurePKs[i]));

                    if (segments != null)
                    {
                        for (int j = 0; j < segments.length; j++)
                        {
                            processSegment((SegmentVO) segments[j].getVO(), runDate, contractLookup);
                        }
                    }
                }
            }
        }
        catch (Exception e)
        {
        }
        finally
        {
            EditServiceLocator.getSingleton().getBatchAgent().getBatchStat(Batch.BATCH_JOB_PROCESS_COI_REPLENISHMENT).tagBatchStop();
        }
    }

    /**
     * Retrieves the productStructure PKs for which COI Replenishment needs to be processed
     * @param productStructure
     * @return
     */
    private long[] getProductStructurePKs(String selectedProductStructure)
    {
        long[] productStructurePKs = null;

        if (selectedProductStructure.equalsIgnoreCase("All"))
        {
            engine.business.Lookup engineLookup = new engine.component.LookupComponent();

            ProductStructureVO[] productStructureVOs = engineLookup.findAllProductTypeStructureVOs(false, null);
            productStructurePKs = new long[productStructureVOs.length];

            for (int i = 0; i < productStructureVOs.length; i++)
            {
                productStructurePKs[i] = productStructureVOs[i].getProductStructurePK();
            }

            this.businessContractName = selectedProductStructure;
        }
        else
        {
            ProductStructure productStructure = ProductStructure.findByPK(new Long(selectedProductStructure));
            this.businessContractName = productStructure.getBusinessContractName();
            productStructurePKs = new long[] { Long.parseLong(selectedProductStructure) };
        }

        return productStructurePKs;
    }

    private void processSegment(SegmentVO segmentVO,
                                String runDate,
                                contract.business.Lookup contractLookup) throws Exception
    {
        String currentContractBeingProcessed = null;

        List voInclusionList = new ArrayList();
        voInclusionList.add(SegmentVO.class);
        voInclusionList.add(LifeVO.class);
        voInclusionList.add(InvestmentVO.class);
        voInclusionList.add(BucketVO.class);
        voInclusionList.add(DepositsVO.class);

        contract.business.Contract contractComponent = new contract.component.ContractComponent();
        engine.business.Calculator engine = new engine.component.CalculatorComponent();

        try
        {
            if ((segmentVO.getChargeDeductDivisionInd() != null) && segmentVO.getChargeDeductDivisionInd().equalsIgnoreCase("Y"))
            {
                SegmentVO currentSegmentVO = contractLookup.composeSegmentVO(segmentVO.getSegmentPK(), voInclusionList);

                currentContractBeingProcessed = currentSegmentVO.getContractNumber();

                CoiReplenishmentVO coiReplenishmentVO = getCOIReplenishmentVO(runDate, currentSegmentVO);

                if (coiReplenishmentVO != null)
                {
                    SPOutput spOutput = engine.processScript("CoiReplenishmentVO", coiReplenishmentVO, "CoiReplenishment", "*", "*", runDate, currentSegmentVO.getProductStructureFK(), false);

                    SegmentVO updatedSegmentVO = processOutputs(spOutput.getSPOutputVO(), runDate);

                    contractComponent.saveSegmentNonRecursively(updatedSegmentVO, false, "System");

                    EditServiceLocator.getSingleton().getBatchAgent().getBatchStat(Batch.BATCH_JOB_PROCESS_COI_REPLENISHMENT).updateSuccess();
                }
            }

            if (outputLines.size() > 0)
            {
                File exportFile = getExportFile(runDate);
                // Read the Map of outoutLines in sorted key order
                // and write the lines to the file
                sortAndWriteMemoryLinesToFile(exportFile);
            }
        }
        catch (Exception e)
        {
            EditServiceLocator.getSingleton().getBatchAgent().getBatchStat(Batch.BATCH_JOB_PROCESS_COI_REPLENISHMENT).updateFailure();

          System.out.println(e);

            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.

            String message = "COI Replenishment Failed on Contract: " + currentContractBeingProcessed +
                           " Exception is: " + e.getMessage();

            LogEvent logEvent = new LogEvent(message, e);

            Logger logger = Logging.getLogger(Logging.BATCH_JOB);

            logger.error(logEvent);
        }
    }

    /**
     * Builds the COIReplenishmentVO to be processed for the given segment and runDate
     * @param runDate
     * @param segmentVO
     * @return
     */
    private CoiReplenishmentVO getCOIReplenishmentVO(String runDate, SegmentVO segmentVO)
    {
        CodeTableComponent codeTableComponent = new CodeTableComponent();

        return codeTableComponent.buildCOIReplenishmentVO(runDate, segmentVO);
    }

    /**
     * Loops through the vo objects output from the COIReplenishment script process
     * @param spOutputVO
     * @param runDate
     * @return
     * @throws Exception
     */
    private SegmentVO processOutputs(SPOutputVO spOutputVO, String runDate) throws Exception
    {
        VOObject[] voObjects = spOutputVO.getVOObject();

        SegmentVO segmentVO = null;

        Map vosByInvestmentFK = new HashMap();

        if (voObjects != null)
        {
            for (int j = 0; j < voObjects.length; j++)
            {
                VOObject voObject = (VOObject) voObjects[j];

                if (voObject instanceof SegmentVO)
                {
                    segmentVO = (SegmentVO) voObject;
                }
                else if (voObject instanceof InvestmentTransferValueVO)
                {
                    InvestmentTransferValueVO investmentTransferValueVO = (InvestmentTransferValueVO) voObject;

                    vosByInvestmentFK.put(investmentTransferValueVO.getInvestmentFK() + "", voObject);
                }
            }
        }

        if (!vosByInvestmentFK.isEmpty())
        {
//            createTransferTransactions(segmentVO, vosByInvestmentFK, runDate);
            if (!this.headerCreated)
            {
                createExtractHeader(runDate);
                this.headerCreated = true;
            }

            createExtract(segmentVO, vosByInvestmentFK);
        }

        return segmentVO;
    }

    private void createExtractHeader(String runDate)
    {
        StringBuffer fileData = new StringBuffer();
        fileData.append("HEADER");
        fileData.append("   ");
        fileData.append(runDate.substring(0, 4) + runDate.substring(5, 7) + runDate.substring(8));
        fileData.append("          ");
        fileData.append(this.businessContractName);
        appendOutputLineInMemory(fileData.toString(), "");
    }

    private void createExtract(SegmentVO segmentVO, Map vosByInvestmentFK) throws Exception
    {
        Set keySet = vosByInvestmentFK.keySet();
        Iterator it = keySet.iterator();
        while (it.hasNext())
        {
            String investmentPK = (String) it.next();

            InvestmentTransferValueVO investmentTransferValueVO = (InvestmentTransferValueVO) vosByInvestmentFK.get(investmentPK);

            String clientFundNumber = getClientFundNumber(investmentPK);

            StringBuffer fileData = new StringBuffer();
            fileData.append("\n");
            fileData.append(segmentVO.getContractNumber());
            fileData.append(",");
            fileData.append(clientFundNumber);
            fileData.append(",");
            fileData.append(investmentTransferValueVO.getAmount().toString());
            fileData.append(",");
            fileData.append(segmentVO.getChargeDeductAmount().toString());

            appendOutputLineInMemory(fileData.toString(), segmentVO.getContractNumber());
        }
    }

    /**
     * Creates a TF or HFTA transaction for the given segment and InvestmentTransferValueVOs
     * (stored in the vosByInvesmtentFK map)
     * @param segmentVO
     * @param vosByInvestmentFK
     * @param runDate
     * @throws Exception
     */
    private void createTransferTransactions(Segment segment, Map vosByInvestmentFK, String runDate) throws Exception
    {
        contract.business.Lookup contractLookup = new contract.component.LookupComponent();

        List voInclusionList = new ArrayList();
        voInclusionList.add(InvestmentAllocationVO.class);

        InvestmentVO[] investmentVOs = contractLookup.composeInvestmentVOBySegmentPK(segment.getSegmentPK().longValue(), voInclusionList);

        long filteredFundPK = getChargeDeductFund(segment);

        Set voKeys = vosByInvestmentFK.keySet();
        Iterator it = voKeys.iterator();

        while (it.hasNext())
        {
            String investmentFK = (String) it.next();

            InvestmentTransferValueVO investmentTransferValueVO = (InvestmentTransferValueVO) vosByInvestmentFK.get(investmentFK);
            BigDecimal transferAmount = investmentTransferValueVO.getAmount();

            for (int i = 0; i < investmentVOs.length; i++)
            {
                if (investmentVOs[i].getInvestmentPK() == Long.parseLong(investmentFK))
                {
                    String fundType = getFundType(investmentVOs[i].getFilteredFundFK());

                    //Create a TF or HFTA transaction from this fund into the charge deduct fund
                    //where investmentVOs[i] is the From fund, and filteredFundPK is the To fund
                    if (fundType.equalsIgnoreCase("Hedge"))
                    {
                        createHFTATransaction(segment, investmentVOs[i], filteredFundPK, transferAmount, runDate);
                    }
                    else
                    {
                        createTFTransaction(segment, investmentVOs[i], filteredFundPK, transferAmount, runDate);
                    }

                    i = investmentVOs.length;
                }
            }
        }
    }

    /**
     * Retrieves the charge deduct fund from the product table
     * @param segmentVO
     * @return
     * @throws Exception
     */
    private long getChargeDeductFund(Segment segment) throws Exception
    {
        long productStructurePK = segment.getProductStructureFK().longValue();
        String areaCT = segment.getIssueStateCT();
        String qualifierCT = "*";
        String grouping = "TRANSACTION";

        EDITDate effectiveDate = segment.getEffectiveDate();
        String field = "CHARGEDEDUCTFUND";

        Area area = new Area(productStructurePK, areaCT, grouping, effectiveDate, qualifierCT);

        AreaValue areaValue = area.getAreaValue(field);

        AreaValueVO areaValueVO = (AreaValueVO) areaValue.getVO();

        String fundNumber = areaValueVO.getAreaValue();

        long filteredFundPK = getFilteredFundPK(fundNumber);

        return filteredFundPK;
    }

    /**
     * Gets the filtered fund PK for the given fund number
     * @param fundNumber
     * @return
     * @throws Exception
     */
    private long getFilteredFundPK(String fundNumber) throws Exception
    {
        engine.business.Lookup engineLookup = new engine.component.LookupComponent();

        FilteredFundVO[] filteredFundVO = engineLookup.getByFundNumber(fundNumber);

        long filteredFundPK = 0;

        if (filteredFundVO.length > 0)
        {
            filteredFundPK = filteredFundVO[0].getFilteredFundPK();
        }

        return filteredFundPK;
    }

    /**
     * Gets the fund type for the specified filtered fund
     * @param filteredFundFK
     * @return
     * @throws Exception
     */
    private String getFundType(long filteredFundFK) throws Exception
    {
        engine.business.Lookup engineLookup = new engine.component.LookupComponent();

        String fundType = null;

        FundVO[] fundVO = engineLookup.getFundByFilteredFundFK(filteredFundFK, false, new ArrayList());

        if ((fundVO != null) && (fundVO.length > 0))
        {
            fundType = fundVO[0].getFundType();
        }

        return fundType;
    }

    /**
     * Generates an HFTA transaction for the given segment, transfer amount,
     * investment(from fund) and filteredFund (to fund)
     * @param segment
     * @param investmentVO
     * @param filteredFundFK
     * @param transferAmount
     * @param runDate
     */
    private void createHFTATransaction(Segment segment, InvestmentVO investmentVO, long filteredFundFK, BigDecimal transferAmount, String runDate)
    {
        String transactionType = "HFTA";

        engine.business.Lookup engineLookup = new engine.component.LookupComponent();

        //We need to find the COIReplenishment information in order to determine the correct trx effective date
        FilteredFundVO[] filteredFundVO = engineLookup.findFilteredFundByPK(investmentVO.getFilteredFundFK());

        int notificationDays = filteredFundVO[0].getCOIReplenishmentDays();
        String notificationDaysType = filteredFundVO[0].getCOIReplenishmentDaysTypeCT();
        String coiReplenMode = filteredFundVO[0].getCOIReplenishmentModeCT();

        EDITDate edRunDate = new EDITDate(runDate);

        if (notificationDays > 0)
        {
            if ((notificationDaysType != null) && notificationDaysType.equalsIgnoreCase("Business"))
            {
                BusinessCalendar businessCalendar = new BusinessCalendar();
                BusinessDay businessDay = businessCalendar.findNextBusinessDay(edRunDate, notificationDays);
                edRunDate = businessDay.getBusinessDate();
            }
            else
            {
                edRunDate = edRunDate.addDays(notificationDays);
            }
        }

        EDITDate effectiveDate = null;

        if (coiReplenMode.equalsIgnoreCase("Monthly"))
        {
            effectiveDate = edRunDate.getEndOfMonthDate();
        }
        else if (coiReplenMode.equalsIgnoreCase("Annual"))
        {
            effectiveDate = new EDITDate(edRunDate.getFormattedYear(), EDITDate.DEFAULT_MAX_MONTH, EDITDate.DEFAULT_MAX_DAY);
        }
        else
        {
            effectiveDate = edRunDate.getEndOfModeDate(coiReplenMode);
        }

        int taxYear = effectiveDate.getYear();

        String operator = "System";

        ContractEvent contractEvent = new ContractEvent();

        EDITTrxVO editTrxVO = contractEvent.buildDefaultEDITTrxVO(transactionType, effectiveDate, taxYear, operator);

        EDITTrx editTrx = new EDITTrx(editTrxVO);

        editTrx.createHFTATransactionGroupSetup(segment, investmentVO, filteredFundFK, transferAmount, notificationDays, notificationDaysType);
    }

    /**
     * Generates a TF Transaction for the given segment, runDate, transfer amount,
     * investment (from fund), and filteredFund (to fund)
     * @param segmentVO
     * @param investmentVO
     * @param filteredFundFK
     * @param transferAmount
     * @param runDate
     * @throws Exception
     */
    private void createTFTransaction(Segment segment, InvestmentVO investmentVO, long filteredFundFK, BigDecimal transferAmount, String runDate) throws Exception
    {
        String transactionType = "TF";

        int taxYear = Integer.parseInt(runDate.substring(0, 4));

        String operator = "System";

        ContractEvent contractEvent = new ContractEvent();

        EDITTrxVO editTrxVO = contractEvent.buildDefaultEDITTrxVO(transactionType, new EDITDate(runDate), taxYear, operator);

        EDITTrx editTrx = new EDITTrx(editTrxVO);

        editTrx.createTransferTransactionGroupSetup(segment, investmentVO, filteredFundFK, transferAmount);
    }

    private File getExportFile(String runDate)
    {
        EDITExport export1 = ServicesConfig.getEDITExport("ExportDirectory1");

        String fileDate = runDate.substring(2, 4) + runDate.substring(5, 7);

        File exportFile = new File(export1.getDirectory() + "COIReplenish" + fileDate + ".prn");

        return exportFile;
    }

    /**
     * Append the line into its slot in a Map so we can sort them when
     * we're done and then write them out.
     * @param data
     */
    private void appendOutputLineInMemory(String data, String contractNumber)
    {
        String key = contractNumber;

        if (this.outputLines.containsKey(key))
        {

            List linesForContract = (List) this.outputLines.get(key);

            linesForContract.add(data);
        }
        else
        {
            List linesForContract = new ArrayList();

            linesForContract.add(data);

            this.outputLines.put(key, linesForContract);
        }
    }

    private String getClientFundNumber(String investmentPK)
    {
        Investment investment = Investment.findByPK(new Long(investmentPK));
        ChargeCode[] chargeCodes = ChargeCode.findByFilteredFundPK(investment.getFilteredFundFK().longValue());

        String clientFundNumber = "";

        if (chargeCodes != null && investment.getChargeCodeFK() != null)
        {
            for (int i = 0; i < chargeCodes.length; i++)
            {
                if (chargeCodes[i].getPK() == investment.getChargeCodeFK().longValue())
                {
                    clientFundNumber = ((ChargeCodeVO) chargeCodes[i].getVO()).getClientFundNumber();
                    break;
                }
            }
        }
        else
        {
            FilteredFund filteredFund = FilteredFund.findByPK(investment.getFilteredFundFK());
            clientFundNumber = filteredFund.getFundNumber();

            if (clientFundNumber.length() > 4)
            {
                clientFundNumber = clientFundNumber.trim();
            }
        }

        return clientFundNumber;
    }

    /**
     * Reads the Map of fund keys in sorted order and
     * it writes the stored lines to the exportFile.
     * @param exportFile
     * @throws java.io.IOException
     */
    private void sortAndWriteMemoryLinesToFile(File exportFile)
            throws IOException
    {
       FileWriter fw = new FileWriter(exportFile);
       BufferedWriter bw = new BufferedWriter(fw);

       Iterator it = this.outputLines.keySet().iterator();

       while (it.hasNext())
       {
           String key =  (String) it.next();
           List listOfLines = (List) this.outputLines.get(key);
           for (int i = 0; i < listOfLines.size(); i++)
           {
               String aLine =  (String) listOfLines.get(i);
               bw.write(aLine);
           }
       }

       bw.close();
       fw.close();
    }
}
