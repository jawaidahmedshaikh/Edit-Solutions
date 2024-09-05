package event.batch;

import batch.business.Batch;

import edit.common.EDITBigDecimal;
import edit.common.EDITDate;
import edit.common.EDITDateTime;
import edit.common.vo.ControlBalanceDetailVO;
import edit.common.vo.ControlBalanceVO;
import edit.common.vo.EDITExport;
import edit.common.vo.FilteredFundVO;
import edit.common.vo.FundVO;
import edit.common.vo.ProductFilteredFundStructureVO;
import edit.common.vo.UnitValuesVO;
import edit.common.vo.user.ControlsAndBalancesTotalsVO;

import edit.services.EditServiceLocator;
import edit.services.config.ServicesConfig;
import edit.services.db.ConnectionFactory;
import edit.services.db.DBTable;
import edit.services.db.DBUtil;
import edit.services.logging.Logging;

import engine.ChargeCode;
import engine.Fee;

import engine.util.TransformChargeCodes;

import event.EDITTrx;

import fission.utility.Util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeMap;

import logging.LogEvent;

import org.apache.logging.log4j.Logger;


/**
 * This writes out the Activity File as well as storing the new
 * ControlBalance rows.
 * <p/>
 * The report is now sorted by fund number (May 2005).  Since some of
 * the fund names are translated after the database extracts have happened,
 * this is done by writing what were the output lines into memory.  They are
 * sorted and written to the file.
 * <p/>
 * It was originally designed to write out the report by FundNumber
 * and to update the ControlBalance only based on the
 * ProductFilteredFundStructurePK.
 * <p/>
 * Now the report is done by the Client Fund Number.  This either is
 * the original fund number if the buckets did
 * not use a charge code at all, or it is
 * a Client fund number (stored in the charge code row) if they
 * used a charge code and there is a client fund number attached
 * to that charge code.  The Client fund number acts as an alias
 * to the Fund+charge code number combination.  It is the customer's
 * alias for the charge code for their reporting.
 * <p/>
 * The updates to the ControlBalance are now updated by the combination
 * of the ProductFilteredFundStructurePK and the ChargeCodeFK.
 * <p/>

 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement
 */
public class ActivityFileInterfaceProcessor implements Serializable
{
    private static final String fileName = "ActivityFile";
    private static final String[] ACTIVITIES =
            new String[] {"IMFP", "M&EP", "ADMT", "PREM", "RALL", "SURR", "COIN", "ADMF", "PLNS", "LINT", "RDTH"};
    private static final String COST_OF_INSURANCE = "COIN";
    private static final String[] DFCASH_FEE_TYPES_EXCLUDED = new String[] {"AdvisoryFee", "PerformanceFee", "SVAFee"};

    private String cycleDate;

    private engine.business.Lookup engineLookup = new engine.component.LookupComponent();

    /**
     * A map for our output lines that we 'write to' instead of directly to the
     * file.  Once we have finished, we read these out again in sorted order and
     * really write to the Activity File. It is keyed by 4-digit fund number
     * prefix.  The value stored for each is a List of the String output lines.
     * The single report header record is stored with a key = an ascii zero and the
     * single report trailer record is written with a key of ascii 127 character.
     */
    private Map outputLines = new TreeMap();

    private static final String HEADER_KEY = Character.toString((char)0);

    private static final String TRAILER_KEY = Character.toString((char)127);

    /**
     * Keeps a dollar balance and a unit balance -
     * used to store new ControlBalance rows.
     * key is ProductFilteredFundStructurePK|chargeCodeFK
     */
    private Map productFundUpdates = new HashMap();
    private Map dfcashProductFundUpdates = new HashMap();

    /**
     * Keeps summaries for the report.
     * Key is clientFundOrRealFundNumber|transactionType
     */
    private Map totalsByFund = new HashMap();

    /**
     * Keeps totals for ControlBalanceDetail records
     * Key is clientFundOrRealFundNumber|accountingPeriod|effectiveDate|valuationDate
     * Each key has HashMap containing ControlsAndBalancesTotalsVO for each TRASNACTION_TYPE (key)
     */
    private Map controlBalanceDetails = new TreeMap();

    /**
     * Used to do the translation to Client Fund Number.  It lazy-loads
     * the data in one query and keeps it in a map since the data isn't
     * indexed.
     */
    private TransformChargeCodes transformChargeCodes = new TransformChargeCodes();

    private static final String PROCESS = "Process";

    private static final String TRANSACTION_TYPE_PREM = "PREM";
    private static final String TRANSACTION_TYPE_ADMF = "ADMF";
    private static final String TRANSACTION_TYPE_COIN = "COIN";
    private static final String TRANSACTION_TYPE_RALL = "RALL";
    private static final String TRANSACTION_TYPE_RDTH = "RDTH";
    private static final String TRANSACTION_TYPE_SURR = "SURR";
    private static final String TRANSACTION_TYPE_MANDEP = "M&EP";
    private static final String TRANSACTION_TYPE_IMFP = "IMFP";
    private static final String TRANSACTION_TYPE_ADMT = "ADMT";
    private static final String TRANSACTION_TYPE_DFCASH = "DFCASH";
    private static final String TRANSACTION_TYPE_DFOFF = "DFOFF";

    public void createTransactionActivityFile(String cycleDate)
    {
        this.cycleDate = cycleDate;
        EditServiceLocator.getSingleton().getBatchAgent().getBatchStat(Batch.BATCH_JOB_CREATE_TRANSACTION_ACTIVITY_FILE).tagBatchStart(Batch.BATCH_JOB_CREATE_TRANSACTION_ACTIVITY_FILE, "Transaction Activity File");

        List voInclusionList = new ArrayList();
        voInclusionList.add(FilteredFundVO.class);
         voInclusionList.add(ProductFilteredFundStructureVO.class);

        FundVO[] nonActivityFileFundVOs = engineLookup.composeAllFundsByActivityFileInd(voInclusionList, "Y");

        FundVO[] activityFileFundVOs = engineLookup.composeAllFundsByActivityFileInd(voInclusionList, "N");

        boolean generateActivityFile = true;

        if (activityFileFundVOs != null)
        {
            processFunds(activityFileFundVOs, generateActivityFile);
        }

        productFundUpdates = new HashMap();
        dfcashProductFundUpdates = new HashMap();
        totalsByFund = new HashMap();
        controlBalanceDetails = new TreeMap();

        generateActivityFile = false;

        processFunds(nonActivityFileFundVOs, generateActivityFile);
    }

    private void processFunds(FundVO[] fundVOs, boolean generateActivityFile)
    {
        try
        {
            // we will break out of this if it is run again for the same day
            breakout:
                for (int i = 0; i < fundVOs.length; i++)
                {
                    FilteredFundVO[] filteredFundVOs = fundVOs[i].getFilteredFundVO();
                    if (filteredFundVOs == null)
                    {
                        continue;
                    }

                    for (int j = 0; j < filteredFundVOs.length; j++)
                    {
                        ProductFilteredFundStructureVO[] pffsVO =
                                filteredFundVOs[j].getProductFilteredFundStructureVO();

                        pffsVO = (ProductFilteredFundStructureVO[]) Util.sortObjects(pffsVO, new String[] {"getProductFilteredFundStructurePK"});

                        if (pffsVO == null || pffsVO.length == 0)
                        {
                            continue;
                        }

                        ProductFilteredFundStructureVO productFilteredFundStructureVO = pffsVO[pffsVO.length - 1];

                        long productFilteredFundStructurePK = productFilteredFundStructureVO.getProductFilteredFundStructurePK();
                        
                        long filteredFundFK = productFilteredFundStructureVO.getFilteredFundFK();
                        
                        boolean onlyOneClientFundNumber = true;

                        List clientFundNumbers = ChargeCode.getUniqueClientFundNumbers(filteredFundVOs[j].getFilteredFundPK());

                        if (clientFundNumbers.size() > 1)
                        {
                            onlyOneClientFundNumber = false;
                        }
                        
                        long[] allChargeCodeFKs =
                                ChargeCode.getAllChargeCodePKsIncludingZero(filteredFundVOs[j].getFilteredFundPK());
                                
                        long[] chargeCodeFKsToUse = null;
                        if (onlyOneClientFundNumber)
                        {
                            chargeCodeFKsToUse = new long[1];
                            chargeCodeFKsToUse[0] = allChargeCodeFKs[0];
                        }
                        else
                        {
                            chargeCodeFKsToUse = allChargeCodeFKs;
                        }
                        
                        // first get fund/division level information
                        // ==> Fees
                        for (int k = 0; k < chargeCodeFKsToUse.length; k++)
                        {
                            ControlBalanceVO controlBalanceVO =
                                    getLatestControlBalanceVO(productFilteredFundStructurePK,
                                            chargeCodeFKsToUse[k], onlyOneClientFundNumber, allChargeCodeFKs);
                                            
                            String fromDate = controlBalanceVO.getEndingBalanceCycleDate();

                            if (fromDate != null && fromDate.equals(cycleDate))
                            {
                                break breakout;
                                // if we get the same control balance date for
                                // the first item looked up (or any), we can just stop the
                                // report.  In that case, it will be just a header
                                // and trailer.
                            }

                            if (fromDate == null)
                            {
                                fromDate = EDITDate.DEFAULT_MIN_YEAR + EDITDate.DATE_DELIMITER + EDITDate.DEFAULT_MIN_MONTH + EDITDate.DATE_DELIMITER + EDITDate.DEFAULT_MIN_DAY; //"1800/01/01";
                            }

                            getActivityForFee(productFilteredFundStructureVO,
                                    controlBalanceVO,
                                    fromDate,
                                    cycleDate,
                                    chargeCodeFKsToUse[k],
                                    filteredFundVOs[j].getFilteredFundPK(),
                                    onlyOneClientFundNumber);

                            if (!dfcashProductFundUpdates.containsKey(productFilteredFundStructureVO.getProductFilteredFundStructurePK()+ "|" + chargeCodeFKsToUse[k]))
                            {
                                List ffVector = new ArrayList();

                                ffVector.add(0, new EDITBigDecimal(controlBalanceVO.getDFCASHEndingShareBalance()));

                                dfcashProductFundUpdates.put(productFilteredFundStructureVO.getProductFilteredFundStructurePK()+ "|" + chargeCodeFKsToUse[k], ffVector);
                            }
                        }

                        // now get contract level information
                        for (int k = 0; k < chargeCodeFKsToUse.length; k++)
                        {
                            ControlBalanceVO controlBalanceVO =
                                    getLatestControlBalanceVO(productFilteredFundStructurePK,
                                            chargeCodeFKsToUse[k], onlyOneClientFundNumber, allChargeCodeFKs);

                            String fromDate = controlBalanceVO.getEndingBalanceCycleDate();

                            if (fromDate == null)
                            {
                                fromDate = "1800/01/01";
                            }

                            getActivityForFund(productFilteredFundStructureVO,
                                    controlBalanceVO,
                                    filteredFundFK,
                                    fromDate,
                                    cycleDate,
                                    chargeCodeFKsToUse[k],
                                    onlyOneClientFundNumber);
                        }
                    }

                    EditServiceLocator.getSingleton().getBatchAgent().getBatchStat(Batch.BATCH_JOB_CREATE_TRANSACTION_ACTIVITY_FILE).updateSuccess();
                }

            generateOutputLinesAndUpdateBalances(cycleDate, generateActivityFile);
        }
        catch (Exception e)
        {
            EditServiceLocator.getSingleton().getBatchAgent().getBatchStat(Batch.BATCH_JOB_CREATE_TRANSACTION_ACTIVITY_FILE).updateFailure();

            System.out.println(e);

            e.printStackTrace();

            LogEvent logEvent = new LogEvent("Activity File Interface Errored", e);

            Logger logger = Logging.getLogger(Logging.BATCH_JOB);

            logger.error(logEvent);
        }
        finally
        {
            EditServiceLocator.getSingleton().getBatchAgent().getBatchStat(Batch.BATCH_JOB_CREATE_TRANSACTION_ACTIVITY_FILE).tagBatchStop();
        }
    }

     private ControlBalanceVO getLatestControlBalanceVO(long productFilteredFundStructurePK,
                                                       long chargeCodeFK,
                                                       boolean onlyOneClientFundNumber,
                                                       long[] allChargeCodeFKs)
    {
        ControlBalanceVO controlBalanceVO = null;

        /* If there is only one clientFundNumber for a given filtered fund, then we need to sum up
        the ending balances for ALL of the last ControlBalance records for that filtered fund (for all
        charge codes) to return just one ControlBalanceVO - All charge codes will be rolled up into one
        fund entry on the final ACTFILE output */
        if (onlyOneClientFundNumber)
        {
            EDITBigDecimal finalEndingDollarBalance = new EDITBigDecimal();
            EDITBigDecimal finalEndingUnitBalance = new EDITBigDecimal();
            EDITBigDecimal finalEndingShareBalance = new EDITBigDecimal();
            EDITBigDecimal finalDFCASHEndingShareBalance = new EDITBigDecimal();
            for (int i = 0; i < allChargeCodeFKs.length; i++)
            {
                 ControlBalanceVO cbVO = engineLookup.findLastControlBalanceVO(productFilteredFundStructurePK, allChargeCodeFKs[i]);
                if (cbVO != null)
                {
                    if (i == 0)
                    {
                        controlBalanceVO = cbVO;
                        finalEndingDollarBalance = new EDITBigDecimal(cbVO.getEndingDollarBalance());
                        finalEndingUnitBalance = new EDITBigDecimal(cbVO.getEndingUnitBalance());
                        finalEndingShareBalance = new EDITBigDecimal(cbVO.getEndingShareBalance());
                        finalDFCASHEndingShareBalance = new EDITBigDecimal(cbVO.getDFCASHEndingShareBalance());
                    }
                    else
                    {
                        finalEndingDollarBalance = finalEndingDollarBalance.addEditBigDecimal(new EDITBigDecimal(cbVO.getEndingDollarBalance()));
                        finalEndingUnitBalance = finalEndingUnitBalance.addEditBigDecimal(new EDITBigDecimal(cbVO.getEndingUnitBalance()));
                        finalEndingShareBalance = finalEndingShareBalance.addEditBigDecimal(new EDITBigDecimal(cbVO.getEndingShareBalance()));
                        finalDFCASHEndingShareBalance = finalDFCASHEndingShareBalance.addEditBigDecimal(new EDITBigDecimal(cbVO.getDFCASHEndingShareBalance()));
                    }
                }

                if (controlBalanceVO != null)
                {
                    controlBalanceVO.setEndingDollarBalance(finalEndingDollarBalance.getBigDecimal());
                    controlBalanceVO.setEndingUnitBalance(finalEndingUnitBalance.getBigDecimal());
                    controlBalanceVO.setEndingShareBalance(finalEndingShareBalance.getBigDecimal());
                    controlBalanceVO.setDFCASHEndingShareBalance(finalDFCASHEndingShareBalance.getBigDecimal());
                }
            }
        }
        else
        {
            controlBalanceVO =
                     engineLookup.findLastControlBalanceVO(productFilteredFundStructurePK, chargeCodeFK);
        }

        if (controlBalanceVO == null)
        {
            controlBalanceVO = new ControlBalanceVO();
            controlBalanceVO.setEndingDollarBalance(new EDITBigDecimal().getBigDecimal());
            controlBalanceVO.setEndingShareBalance(new EDITBigDecimal().getBigDecimal());
            controlBalanceVO.setEndingUnitBalance(new EDITBigDecimal().getBigDecimal());
            controlBalanceVO.setDFCASHEndingShareBalance(new EDITBigDecimal().getBigDecimal());
        }

        return controlBalanceVO;
    }

    /**
     * Retrieves the financial activity for the given segmentPKS, bucketPKs, and fromDate
     * @param cffsVO
     * @param controlBalanceVO
     * @param productStructureFK
     * @param filteredFundFK
     * @param fromDate
     * @param cycleDate
     * @return
     */
     private Map getActivityForFund(ProductFilteredFundStructureVO cffsVO,
                                   ControlBalanceVO controlBalanceVO,
                                   long filteredFundFK, 
                                   String fromDate,
                                   String cycleDate,
                                   long chargeCodeFK,
                                   boolean onlyOneFundNumber)
    {
        Hashtable resultSet = new Hashtable();
        Connection conn = ConnectionFactory.getSingleton().getConnection(ConnectionFactory.EDITSOLUTIONS_POOL);

        try
        {
            // THIS IS THE TO VERSION
            executeContractSql(filteredFundFK,
                    fromDate,
                    cycleDate,
                    "T",
                    chargeCodeFK,
                    resultSet,
                    conn,
                    onlyOneFundNumber);

            if (resultSet.size() > 0)
            {
                Enumeration rsKeys = resultSet.keys();

                while (rsKeys.hasMoreElements())
                {
                    String rsKey = (String) rsKeys.nextElement();

                    String transactionType = Util.fastTokenizer(rsKey, "|")[0];

                    EDITBigDecimal[] trxTotals = (EDITBigDecimal[]) resultSet.get(rsKey);

                    String accountingPeriod = Util.fastTokenizer(rsKey, "|")[1];
                    String effectiveDate = Util.fastTokenizer(rsKey, "|")[2];
                    String valuationDate = Util.fastTokenizer(rsKey, "|")[3];

                    String cbdKey = accountingPeriod + "|" + effectiveDate + "|" + valuationDate;

                    if (!transactionType.equalsIgnoreCase("IGNR"))
                    {
                        EDITBigDecimal rsDollars = new EDITBigDecimal(trxTotals[0].getBigDecimal());
                        EDITBigDecimal rsUnits = new EDITBigDecimal(trxTotals[1].getBigDecimal());

                        addUnitsDollars(cffsVO,
                                controlBalanceVO,
                                transactionType,
                                rsDollars,
                                rsUnits,
                                cycleDate,
                                chargeCodeFK,
                                cbdKey,
                                null);
                    }
                }
            }

            // THIS IS THE FROM VERSION
            resultSet.clear();
            executeContractSql(filteredFundFK,
                    fromDate,
                    cycleDate,
                    "F",
                    chargeCodeFK,
                    resultSet,
                    conn,
                    onlyOneFundNumber);

            if (resultSet.size() > 0)
            {
                Enumeration rsKeys = resultSet.keys();

                while (rsKeys.hasMoreElements())
                {
                    String rsKey = (String) rsKeys.nextElement();

                    String transactionType = Util.fastTokenizer(rsKey, "|")[0];

                    EDITBigDecimal[] trxTotals = (EDITBigDecimal[]) resultSet.get(rsKey);

                    String accountingPeriod = Util.fastTokenizer(rsKey, "|")[1];
                    String effectiveDate = Util.fastTokenizer(rsKey, "|")[2];
                    String valuationDate = Util.fastTokenizer(rsKey, "|")[3];

                    String cbdKey = accountingPeriod + "|" + effectiveDate + "|" + valuationDate;

                    if (!transactionType.equalsIgnoreCase("IGNR"))
                    {
                        EDITBigDecimal rsDollars = new EDITBigDecimal(trxTotals[0].getBigDecimal());
                        EDITBigDecimal rsUnits = new EDITBigDecimal(trxTotals[1].getBigDecimal());

                        subtractUnitsDollars(cffsVO,
                                controlBalanceVO,
                                transactionType,
                                rsDollars,
                                rsUnits,
                                cycleDate,
                                chargeCodeFK,
                                cbdKey,
                                null);
                    }
                }
            }
        }
        catch (Exception e)
        {
            System.out.println("ActivityFileInterfaceProcessor: " + e);
             
            System.out.println(e);

            e.printStackTrace();

            throw new RuntimeException(e);
        }
        finally
        {
            closeConnection(conn);
        }

        return totalsByFund;
    }

    /**
     * Retrieves the financial activity related to Fees for the cycleDate
     * @param cffsVO
     * @param controlBalanceVO
     * @param fromDate
     * @param cycleDate
     * @return
     */
     private Map getActivityForFee(ProductFilteredFundStructureVO cffsVO,
                                  ControlBalanceVO controlBalanceVO,
                                  String fromDate,
                                  String cycleDate,
                                  long chargeCodeFK,
                                  long filteredFundFK,
                                  boolean onlyOneFundNumber)
    {
        //We need to look for DFCASH types to update the DFCASHEndingShareBalance on the ControlBalance table,
        //but the DFCASH values are NOT to be included on the generated activity file.
        String[] transactionTypeCTs = new String[] {"DFACC", "DPURCH", "DFCASH", "DFOFF"};

        ResultSet rs = null;
        Connection conn = null;

        FilteredFundVO filteredFundVO = (FilteredFundVO) cffsVO.getParentVO(FilteredFundVO.class);

        String fundNumber = filteredFundVO.getFundNumber();

        String clientOrRealFundNumber = convertToClientFund(fundNumber, chargeCodeFK);

        try
        {
            // THIS IS THE TO VERSION

            boolean mAndEFound = false;
            boolean imfpFound = false;

            for (int i = 0; i < transactionTypeCTs.length; i++)
            {
                try
                {
                    conn = ConnectionFactory.getSingleton().getConnection(ConnectionFactory.ENGINE_POOL);
                    rs = executeFeeSql(transactionTypeCTs[i],
                            fromDate,
                            cycleDate,
                            "T",
                            chargeCodeFK,
                            filteredFundFK,
                            rs,
                            conn,
                            onlyOneFundNumber);

                    while (rs.next())
                    {
                        EDITBigDecimal rsDollars =
                                new EDITBigDecimal(rs.getBigDecimal("TotalDollars"));
                        EDITBigDecimal rsUnits =
                                new EDITBigDecimal(rs.getBigDecimal("TotalUnits"));
                        String feeType = (String) rs.getString("FeeTypeCT");
                        String transactionType = setTransactionType(transactionTypeCTs[i], feeType);

                        String accountingPeriod = rs.getString("AccountingPeriod");
                        String effectiveDate = DBUtil.readAndConvertDate(rs, "EffectiveDate");
                        // ValuationDate is same as EffectiveDate for Fees.
                        String valuationDate = DBUtil.readAndConvertDate(rs, "EffectiveDate");

                        String cbdKey = accountingPeriod + "|" + effectiveDate + "|" + valuationDate;

                        String controlBalanceDetailKey = clientOrRealFundNumber + "|" + cbdKey;

                        ControlsAndBalancesTotalsVO controlsAndBalancesTotalsVO = null;

                        if (transactionTypeCTs[i].equalsIgnoreCase("DFACC") ||
                                transactionTypeCTs[i].equalsIgnoreCase("DPURCH"))
                        {
                            if (transactionType.equalsIgnoreCase("M&EP"))
                            {
                                mAndEFound = true;
                            }
                            else if (transactionType.equalsIgnoreCase("IMFP"))
                            {
                                imfpFound = true;
                            }

                            addUnitsDollars(cffsVO,
                                    controlBalanceVO,
                                    transactionType,
                                    rsDollars,
                                    rsUnits,
                                    cycleDate,
                                    chargeCodeFK,
                                    cbdKey,
                                    feeType);
                        }
                        else if ((transactionTypeCTs[i].equals(TRANSACTION_TYPE_DFCASH) && (!excludedFeeType(feeType))) ||
                                    transactionTypeCTs[i].equals(TRANSACTION_TYPE_DFOFF))
                        {
                                 String compFundUpdKey = cffsVO.getProductFilteredFundStructurePK()+ "|" + chargeCodeFK;

                            EDITBigDecimal dfcashEndingShareBalance = new EDITBigDecimal(controlBalanceVO.getDFCASHEndingShareBalance());
                            FilteredFundVO ffVO = (FilteredFundVO) cffsVO.getParentVO(FilteredFundVO.class);

                            EDITBigDecimal nav2 = new EDITBigDecimal();

                            long filteredFundPK = ffVO.getFilteredFundPK();
                            String pricingDirection = ffVO.getPricingDirection();

                            UnitValuesVO[] unitValuesVO = engineLookup.
                                    getUnitValuesByFilteredFundIdDateChargeCode(
                                            filteredFundPK,
                                            cycleDate,
                                            pricingDirection,
                                            chargeCodeFK);
                            if (unitValuesVO != null)
                            {
                                nav2 = new EDITBigDecimal(unitValuesVO[0].getNetAssetValue2());
                            }

                                 if (dfcashProductFundUpdates.containsKey(compFundUpdKey))
                            {
                                     List ffVector = (List) dfcashProductFundUpdates.get(compFundUpdKey);

                                EDITBigDecimal newDFCASHEndingShareBalance = (EDITBigDecimal) ffVector.get(0);

                                newDFCASHEndingShareBalance = newDFCASHEndingShareBalance.addEditBigDecimal(rsDollars.divideEditBigDecimal(nav2));

                                ffVector.add(0, newDFCASHEndingShareBalance);
                            }
                            else
                            {
                                List ffVector = new ArrayList();

                                EDITBigDecimal newDFCASHEndingShareBalance = new EDITBigDecimal();

                                newDFCASHEndingShareBalance = dfcashEndingShareBalance.addEditBigDecimal(rsDollars.divideEditBigDecimal(nav2));

                                ffVector.add(0, newDFCASHEndingShareBalance);

                                     dfcashProductFundUpdates.put(compFundUpdKey, ffVector);
                            }

                            if (this.controlBalanceDetails.containsKey(controlBalanceDetailKey))
                            {
                                Map totalsByTransactionType = (Map) this.controlBalanceDetails.get(controlBalanceDetailKey);

                                if(totalsByTransactionType.containsKey(transactionType))
                                {
                                    controlsAndBalancesTotalsVO = (ControlsAndBalancesTotalsVO) totalsByTransactionType.get(transactionType);

                                    EDITBigDecimal totalDollars = new EDITBigDecimal(controlsAndBalancesTotalsVO.getTotalDollars());
                                    totalDollars = Util.roundToNearestCent(totalDollars.addEditBigDecimal(rsDollars));
                                    controlsAndBalancesTotalsVO.setTotalDollars(totalDollars.getBigDecimal());

                                    controlsAndBalancesTotalsVO = addCashFeeDollars(controlsAndBalancesTotalsVO, feeType, rsDollars);
                                }
                                else
                                {
                                    controlsAndBalancesTotalsVO = createControlsAndBalancesTotalsVO(clientOrRealFundNumber,
                                            transactionType,
                                            feeType,
                                            "T",
                                            rsDollars,
                                            new EDITBigDecimal());

                                    totalsByTransactionType.put(transactionType, controlsAndBalancesTotalsVO);
                                }
                            }
                            else
                            {
                                controlsAndBalancesTotalsVO = createControlsAndBalancesTotalsVO(clientOrRealFundNumber,
                                        transactionType,
                                        feeType,
                                        "T",
                                        rsDollars,
                                        new EDITBigDecimal());

                                Map totalsByTransactionType = new HashMap();
                                totalsByTransactionType.put(transactionType, controlsAndBalancesTotalsVO);

                                this.controlBalanceDetails.put(controlBalanceDetailKey, totalsByTransactionType);
                            }
                        }
                    }
                }
                finally
                {
                    closeResultSet(rs);
                    closeConnection(conn);
                }
            }

            if (!mAndEFound)
            {
                EDITBigDecimal rsDollars = new EDITBigDecimal();
                EDITBigDecimal rsUnits = new EDITBigDecimal();

                addUnitsDollars(cffsVO,
                        controlBalanceVO,
                        "M&EP",
                        rsDollars,
                        rsUnits,
                        cycleDate,
                        chargeCodeFK,
                        null,
                        null);
            }

            if (!imfpFound)
            {
                EDITBigDecimal rsDollars = new EDITBigDecimal();
                EDITBigDecimal rsUnits = new EDITBigDecimal();

                addUnitsDollars(cffsVO,
                        controlBalanceVO,
                        "IMFP",
                        rsDollars,
                        rsUnits,
                        cycleDate,
                        chargeCodeFK,
                        null,
                        null);
            }

            // THIS IS THE FROM VERSION
            rs = null;
            for (int i = 0; i < transactionTypeCTs.length; i++)
            {
                try
                {
                    conn = ConnectionFactory.getSingleton().getConnection(ConnectionFactory.ENGINE_POOL);
                    rs = executeFeeSql(transactionTypeCTs[i],
                            fromDate,
                            cycleDate,
                            "F",
                            chargeCodeFK,
                            filteredFundFK,
                            rs,
                            conn,
                            onlyOneFundNumber);

                    while (rs.next())
                    {
                        EDITBigDecimal rsDollars =
                                new EDITBigDecimal(rs.getBigDecimal("TotalDollars"));
                        EDITBigDecimal rsUnits =
                                new EDITBigDecimal(rs.getBigDecimal("TotalUnits"));
                        String feeType = (String) rs.getString("FeeTypeCT");
                        String transactionType = setTransactionType(transactionTypeCTs[i], feeType);

                        String accountingPeriod = rs.getString("AccountingPeriod");
                        String effectiveDate = DBUtil.readAndConvertDate(rs, "EffectiveDate");
                        // ValuationDate is same as EffectiveDate for Fees.
                        String valuationDate = DBUtil.readAndConvertDate(rs, "EffectiveDate");

                        String cbdKey = accountingPeriod + "|" + effectiveDate + "|" + valuationDate;

                        String controlBalanceDetailKey = clientOrRealFundNumber + "|" + accountingPeriod + "|" + effectiveDate + "|" + valuationDate;

                        ControlsAndBalancesTotalsVO controlsAndBalancesTotalsVO = null;

                        if (transactionTypeCTs[i].equalsIgnoreCase("DFACC") ||
                                transactionTypeCTs[i].equalsIgnoreCase("DPURCH"))
                        {
                            subtractUnitsDollars(cffsVO,
                                    controlBalanceVO,
                                    transactionType,
                                    rsDollars,
                                    rsUnits,
                                    cycleDate,
                                    chargeCodeFK,
                                    cbdKey,
                                    feeType);
                        }
                        else if ((transactionTypeCTs[i].equals(TRANSACTION_TYPE_DFCASH) && (!excludedFeeType(feeType))) ||
                                    transactionTypeCTs[i].equals(TRANSACTION_TYPE_DFOFF))
                        {
                                     String compFundUpdKey = cffsVO.getProductFilteredFundStructurePK()+ "|" + chargeCodeFK;

                            EDITBigDecimal dfcashEndingShareBalance = new EDITBigDecimal(controlBalanceVO.getDFCASHEndingShareBalance());
                            FilteredFundVO ffVO = (FilteredFundVO) cffsVO.getParentVO(FilteredFundVO.class);

                            EDITBigDecimal nav2 = new EDITBigDecimal();

                            long filteredFundPK = ffVO.getFilteredFundPK();
                            String pricingDirection = ffVO.getPricingDirection();

                            UnitValuesVO[] unitValuesVO = engineLookup.
                                    getUnitValuesByFilteredFundIdDateChargeCode(
                                            filteredFundPK,
                                            cycleDate,
                                            pricingDirection,
                                            chargeCodeFK);
                            if (unitValuesVO != null)
                            {
                                nav2 = new EDITBigDecimal(unitValuesVO[0].getNetAssetValue2());
                            }

                                     if (dfcashProductFundUpdates.containsKey(compFundUpdKey))
                            {
                                         List ffVector = (List) dfcashProductFundUpdates.get(compFundUpdKey);

                                EDITBigDecimal newDFCASHEndingShareBalance = (EDITBigDecimal) ffVector.get(0);

                                newDFCASHEndingShareBalance = newDFCASHEndingShareBalance.subtractEditBigDecimal(rsDollars.divideEditBigDecimal(nav2));

                                ffVector.add(0, newDFCASHEndingShareBalance);
                            }
                            else
                            {
                                List ffVector = new ArrayList();

                                EDITBigDecimal newDFCASHEndingShareBalance = new EDITBigDecimal();

                                newDFCASHEndingShareBalance = dfcashEndingShareBalance.subtractEditBigDecimal(rsDollars.divideEditBigDecimal(nav2));

                                ffVector.add(0, newDFCASHEndingShareBalance);

                                         dfcashProductFundUpdates.put(compFundUpdKey, ffVector);
                            }

                            if (this.controlBalanceDetails.containsKey(controlBalanceDetailKey))
                            {
                                Map totalsByTransactionType = (Map) this.controlBalanceDetails.get(controlBalanceDetailKey);

                                if (totalsByTransactionType.containsKey(transactionType))
                                {
                                    controlsAndBalancesTotalsVO = (ControlsAndBalancesTotalsVO) totalsByTransactionType.get(transactionType);

                                    EDITBigDecimal totalDollars = new EDITBigDecimal(controlsAndBalancesTotalsVO.getTotalDollars());
                                    totalDollars = Util.roundToNearestCent(totalDollars.subtractEditBigDecimal(rsDollars));
                                    controlsAndBalancesTotalsVO.setTotalDollars(totalDollars.getBigDecimal());

                                    controlsAndBalancesTotalsVO = subtractCashFeeDollars(controlsAndBalancesTotalsVO, feeType, rsDollars);
                                }
                                else
                                {
                                    controlsAndBalancesTotalsVO = createControlsAndBalancesTotalsVO(clientOrRealFundNumber,
                                            transactionType,
                                            feeType,
                                            "F",
                                            rsDollars,
                                            new EDITBigDecimal());

                                    totalsByTransactionType.put(transactionType, controlsAndBalancesTotalsVO);
                                }
                            }
                            else
                            {
                                controlsAndBalancesTotalsVO = createControlsAndBalancesTotalsVO(clientOrRealFundNumber,
                                        transactionType,
                                        feeType,
                                        "F",
                                        rsDollars,
                                        new EDITBigDecimal());

                                Map totalsByTransactionType = new HashMap();
                                totalsByTransactionType.put(transactionType, controlsAndBalancesTotalsVO);

                                this.controlBalanceDetails.put(controlBalanceDetailKey, totalsByTransactionType);
                            }
                        }
                    }
                }
                finally
                {
                    closeResultSet(rs);
                    closeConnection(conn);
                }
            }
        }
        catch (Exception e)
        {
            System.out.println("ActivityFileInterfaceProcessor: " + e);
            
            System.out.println(e);

            e.printStackTrace();

            throw new RuntimeException(e);
        }
        finally     // this one shouldn't get executed
        {
            closeResultSet(rs);
            closeConnection(conn);
        }

        return totalsByFund;
    }

    private ControlsAndBalancesTotalsVO createControlsAndBalancesTotalsVO(String clientOrRealFundNumber,
                                                                          String transactionType,
                                                                          String feeType,
                                                                          String toFromIndicator,
                                                                          EDITBigDecimal rsDollars,
                                                                          EDITBigDecimal rsUnits)
    {
        ControlsAndBalancesTotalsVO controlsAndBalancesTotalsVO = new ControlsAndBalancesTotalsVO();

        controlsAndBalancesTotalsVO.setFundNumber(clientOrRealFundNumber);
        controlsAndBalancesTotalsVO.setTrxTypeDesc(transactionType);

        EDITBigDecimal totalDollars = Util.roundToNearestCent(rsDollars);
        EDITBigDecimal totalUnits = new EDITBigDecimal(rsUnits.getBigDecimal());

        if (toFromIndicator.equals("F"))
        {
            totalDollars = totalDollars.negate();
            totalUnits = totalUnits.negate();
        }

        controlsAndBalancesTotalsVO.setTotalDollars(totalDollars.getBigDecimal());

        if (!transactionType.equals(TRANSACTION_TYPE_MANDEP) && !transactionType.equals(TRANSACTION_TYPE_IMFP) &&
                !transactionType.equals(TRANSACTION_TYPE_DFCASH) && !transactionType.equals(TRANSACTION_TYPE_DFOFF))
        {
            controlsAndBalancesTotalsVO.setTotalUnits(totalUnits.getBigDecimal());
        }

        if (transactionType.equals(TRANSACTION_TYPE_MANDEP) || transactionType.equals(TRANSACTION_TYPE_IMFP) || transactionType.equals(TRANSACTION_TYPE_ADMT))
        {
            if (feeType.equals(Fee.ME_FEE_TYPE))
            {
                controlsAndBalancesTotalsVO.setTotalAccruedMAndE(totalDollars.getBigDecimal());
            }
            else if (feeType.equals(Fee.ADVISORY_FEE_TYPE))
            {
                controlsAndBalancesTotalsVO.setTotalAccruedAdvisoryFees(totalDollars.getBigDecimal());
            }
            else if (feeType.equals(Fee.MANAGEMENT_FEE_TYPE))
            {
                controlsAndBalancesTotalsVO.setTotalAccruedMgtFees(totalDollars.getBigDecimal());
            }
            else if (feeType.equals(Fee.RVP_FEE_TYPE))
            {
                controlsAndBalancesTotalsVO.setTotalAccruedRVPFees(totalDollars.getBigDecimal());
            }
            else if (feeType.equals(Fee.TRANSFER_MORT_RESERVE_FEE_TYPE))
            {
                controlsAndBalancesTotalsVO.setTotalAccruedContribToMortRSV(totalDollars.getBigDecimal());
            }

        }
        else if (transactionType.equals(TRANSACTION_TYPE_DFCASH) || transactionType.equals(TRANSACTION_TYPE_DFOFF))
        {
            if (feeType.equals(Fee.PREMIUM_FEE_TYPE))
            {
                controlsAndBalancesTotalsVO.setTotalCashNetPremium(totalDollars.getBigDecimal());
            }
            else if (feeType.equals(Fee.GAIN_LOSS_FEE_TYPE))
            {
                controlsAndBalancesTotalsVO.setCashGainLoss(totalDollars.getBigDecimal());
            }
            else if (feeType.equals(Fee.ADMIN_FEE_TYPE))
            {
                controlsAndBalancesTotalsVO.setTotalCashAdminFees(totalDollars.getBigDecimal());
            }
            else if (feeType.equals(Fee.COI_FEE_TYPE))
            {
                controlsAndBalancesTotalsVO.setTotalCashCoi(totalDollars.getBigDecimal());
            }
            else if (feeType.equals(Fee.TRANSFER_FEE_TYPE) || feeType.equals(Fee.LOAN_FEE_TYPE))
            {
                controlsAndBalancesTotalsVO.setTotalCashReallocations(totalDollars.getBigDecimal());
            }
            else if (feeType.equals(Fee.DEATH_FEE_TYPE))
            {
                controlsAndBalancesTotalsVO.setTotalCashRRD(totalDollars.getBigDecimal());
            }
            else if (feeType.equals(Fee.SURRENDER_FEE_TYPE))
            {
                controlsAndBalancesTotalsVO.setTotalCashSurrenders(totalDollars.getBigDecimal());
            }
            else if (feeType.equals(Fee.TRANSFER_MORT_RESERVE_FEE_TYPE))
            {
                controlsAndBalancesTotalsVO.setTotalCashContribToMortRsv(totalDollars.getBigDecimal());
            }
            else if (feeType.equals(Fee.ME_FEE_TYPE))
            {
                controlsAndBalancesTotalsVO.setTotalCashMAndE(totalDollars.getBigDecimal());
            }
            else if (feeType.equals(Fee.ADVISORY_FEE_TYPE))
            {
                controlsAndBalancesTotalsVO.setTotalCashAdvisoryFees(totalDollars.getBigDecimal());
            }
            else if (feeType.equals(Fee.MANAGEMENT_FEE_TYPE))
            {
                controlsAndBalancesTotalsVO.setTotalCashMgmtFees(totalDollars.getBigDecimal());
            }
            else if (feeType.equals(Fee.SVA_FEE_TYPE))
            {
                controlsAndBalancesTotalsVO.setTotalCashSVAFees(totalDollars.getBigDecimal());
            }
            else if (feeType.equals(Fee.ADVANCE_TRANSFER_FEE_TYPE))
            {
                controlsAndBalancesTotalsVO.setTotalCashAdvanceTransfers(totalDollars.getBigDecimal());
            }
            else if (feeType.equals(Fee.RVP_FEE_TYPE))
            {
                controlsAndBalancesTotalsVO.setTotalCashRVPFees(totalDollars.getBigDecimal());
            }
        }

        return controlsAndBalancesTotalsVO;
    }

    private ControlsAndBalancesTotalsVO addCashFeeDollars(ControlsAndBalancesTotalsVO controlsAndBalancesTotalsVO,
                                                          String feeType,
                                                          EDITBigDecimal rsDollars)
    {
        // DFCASH units need not be considered.
        if (feeType.equals(Fee.PREMIUM_FEE_TYPE))
        {
            EDITBigDecimal totalCashNetPremium = new EDITBigDecimal(controlsAndBalancesTotalsVO.getTotalCashNetPremium());
            controlsAndBalancesTotalsVO.setTotalCashNetPremium(Util.roundToNearestCent(totalCashNetPremium.addEditBigDecimal(rsDollars)).getBigDecimal());
        }
        else if (feeType.equals(Fee.GAIN_LOSS_FEE_TYPE))
        {
            EDITBigDecimal cashGainLoss = new EDITBigDecimal(controlsAndBalancesTotalsVO.getCashGainLoss());
            controlsAndBalancesTotalsVO.setCashGainLoss(Util.roundToNearestCent(cashGainLoss.addEditBigDecimal(rsDollars)).getBigDecimal());
        }
        else if (feeType.equals(Fee.ADMIN_FEE_TYPE))
        {
            EDITBigDecimal totalCashAdminFees = new EDITBigDecimal(controlsAndBalancesTotalsVO.getTotalCashAdminFees());
            controlsAndBalancesTotalsVO.setTotalCashAdminFees(Util.roundToNearestCent(totalCashAdminFees.addEditBigDecimal(rsDollars)).getBigDecimal());
        }
        else if (feeType.equals(Fee.COI_FEE_TYPE))
        {
            EDITBigDecimal totalCashCoi = new EDITBigDecimal(controlsAndBalancesTotalsVO.getTotalCashCoi());
            controlsAndBalancesTotalsVO.setTotalCashCoi(Util.roundToNearestCent(totalCashCoi.addEditBigDecimal(rsDollars)).getBigDecimal());
        }
        else if (feeType.equals(Fee.TRANSFER_FEE_TYPE) || feeType.equals(Fee.LOAN_FEE_TYPE))
        {
            EDITBigDecimal totalCashReallocations = new EDITBigDecimal(controlsAndBalancesTotalsVO.getTotalCashReallocations());
            controlsAndBalancesTotalsVO.setTotalCashReallocations(Util.roundToNearestCent(totalCashReallocations.addEditBigDecimal(rsDollars)).getBigDecimal());
        }
        else if (feeType.equals(Fee.DEATH_FEE_TYPE))
        {
            EDITBigDecimal totalCashRRD = new EDITBigDecimal(controlsAndBalancesTotalsVO.getTotalCashRRD());
            controlsAndBalancesTotalsVO.setTotalCashRRD(Util.roundToNearestCent(totalCashRRD.addEditBigDecimal(rsDollars)).getBigDecimal());
        }
        else if (feeType.equals(Fee.SURRENDER_FEE_TYPE))
        {
            EDITBigDecimal totalCashSurrenders = new EDITBigDecimal(controlsAndBalancesTotalsVO.getTotalCashSurrenders());
            controlsAndBalancesTotalsVO.setTotalCashSurrenders(Util.roundToNearestCent(totalCashSurrenders.addEditBigDecimal(rsDollars)).getBigDecimal());
        }
        else if (feeType.equals(Fee.TRANSFER_MORT_RESERVE_FEE_TYPE))
        {
            EDITBigDecimal totalCashContribToMortRsv = new EDITBigDecimal(controlsAndBalancesTotalsVO.getTotalCashContribToMortRsv());
            controlsAndBalancesTotalsVO.setTotalCashContribToMortRsv(Util.roundToNearestCent(totalCashContribToMortRsv.addEditBigDecimal(rsDollars)).getBigDecimal());
        }
        else if (feeType.equals(Fee.ME_FEE_TYPE))
        {
            EDITBigDecimal totalCashMAndE = new EDITBigDecimal(controlsAndBalancesTotalsVO.getTotalCashMAndE());
            controlsAndBalancesTotalsVO.setTotalCashMAndE(Util.roundToNearestCent(totalCashMAndE.addEditBigDecimal(rsDollars)).getBigDecimal());
        }
        else if (feeType.equals(Fee.ADVISORY_FEE_TYPE))
        {
            EDITBigDecimal totalCashAdvisoryFees = new EDITBigDecimal(controlsAndBalancesTotalsVO.getTotalCashAdvisoryFees());
            controlsAndBalancesTotalsVO.setTotalCashAdvisoryFees(Util.roundToNearestCent(totalCashAdvisoryFees.addEditBigDecimal(rsDollars)).getBigDecimal());
        }
        else if (feeType.equals(Fee.MANAGEMENT_FEE_TYPE))
        {
            EDITBigDecimal totalCashMgmtFees = new EDITBigDecimal(controlsAndBalancesTotalsVO.getTotalCashMgmtFees());
            controlsAndBalancesTotalsVO.setTotalCashMgmtFees(Util.roundToNearestCent(totalCashMgmtFees.addEditBigDecimal(rsDollars)).getBigDecimal());
        }
        else if (feeType.equals(Fee.SVA_FEE_TYPE))
        {
            EDITBigDecimal totalCashSVAFees = new EDITBigDecimal(controlsAndBalancesTotalsVO.getTotalCashSVAFees());
            controlsAndBalancesTotalsVO.setTotalCashSVAFees(Util.roundToNearestCent(totalCashSVAFees.addEditBigDecimal(rsDollars)).getBigDecimal());
        }
        else if (feeType.equals(Fee.ADVANCE_TRANSFER_FEE_TYPE))
        {
            EDITBigDecimal totalCashAdvanceTransfers = new EDITBigDecimal(controlsAndBalancesTotalsVO.getTotalCashAdvanceTransfers());
            controlsAndBalancesTotalsVO.setTotalCashAdvanceTransfers(Util.roundToNearestCent(totalCashAdvanceTransfers.addEditBigDecimal(rsDollars)).getBigDecimal());
        }
        else if (feeType.equals(Fee.RVP_FEE_TYPE))
        {
            EDITBigDecimal totalCashRVPFees = new EDITBigDecimal(controlsAndBalancesTotalsVO.getTotalCashRVPFees());
            controlsAndBalancesTotalsVO.setTotalCashRVPFees(Util.roundToNearestCent(totalCashRVPFees.addEditBigDecimal(rsDollars)).getBigDecimal());
        }

        return controlsAndBalancesTotalsVO;
    }

    private ControlsAndBalancesTotalsVO subtractCashFeeDollars(ControlsAndBalancesTotalsVO controlsAndBalancesTotalsVO,
                                                               String feeType,
                                                               EDITBigDecimal rsDollars)
    {
        // DFCASH units need not be considered.
        if (feeType.equals(Fee.PREMIUM_FEE_TYPE))
        {
            EDITBigDecimal totalCashNetPremium = new EDITBigDecimal(controlsAndBalancesTotalsVO.getTotalCashNetPremium());
            controlsAndBalancesTotalsVO.setTotalCashNetPremium(Util.roundToNearestCent(totalCashNetPremium.subtractEditBigDecimal(rsDollars)).getBigDecimal());
        }
        else if (feeType.equals(Fee.GAIN_LOSS_FEE_TYPE))
        {
            EDITBigDecimal cashGainLoss = new EDITBigDecimal(controlsAndBalancesTotalsVO.getCashGainLoss());
            controlsAndBalancesTotalsVO.setCashGainLoss(Util.roundToNearestCent(cashGainLoss.subtractEditBigDecimal(rsDollars)).getBigDecimal());
        }
        else if (feeType.equals(Fee.ADMIN_FEE_TYPE))
        {
            EDITBigDecimal totalCashAdminFees = new EDITBigDecimal(controlsAndBalancesTotalsVO.getTotalCashAdminFees());
            controlsAndBalancesTotalsVO.setTotalCashAdminFees(Util.roundToNearestCent(totalCashAdminFees.subtractEditBigDecimal(rsDollars)).getBigDecimal());
        }
        else if (feeType.equals(Fee.COI_FEE_TYPE))
        {
            EDITBigDecimal totalCashCoi = new EDITBigDecimal(controlsAndBalancesTotalsVO.getTotalCashCoi());
            controlsAndBalancesTotalsVO.setTotalCashCoi(Util.roundToNearestCent(totalCashCoi.subtractEditBigDecimal(rsDollars)).getBigDecimal());
        }
        else if (feeType.equals(Fee.TRANSFER_FEE_TYPE) || feeType.equals(Fee.LOAN_FEE_TYPE))
        {
            EDITBigDecimal totalCashReallocations = new EDITBigDecimal(controlsAndBalancesTotalsVO.getTotalCashReallocations());
            controlsAndBalancesTotalsVO.setTotalCashReallocations(Util.roundToNearestCent(totalCashReallocations.subtractEditBigDecimal(rsDollars)).getBigDecimal());
        }
        else if (feeType.equals(Fee.DEATH_FEE_TYPE))
        {
            EDITBigDecimal totalCashRRD = new EDITBigDecimal(controlsAndBalancesTotalsVO.getTotalCashRRD());
            controlsAndBalancesTotalsVO.setTotalCashRRD(Util.roundToNearestCent(totalCashRRD.subtractEditBigDecimal(rsDollars)).getBigDecimal());
        }
        else if (feeType.equals(Fee.SURRENDER_FEE_TYPE))
        {
            EDITBigDecimal totalCashSurrenders = new EDITBigDecimal(controlsAndBalancesTotalsVO.getTotalCashSurrenders());
            controlsAndBalancesTotalsVO.setTotalCashSurrenders(Util.roundToNearestCent(totalCashSurrenders.subtractEditBigDecimal(rsDollars)).getBigDecimal());
        }
        else if (feeType.equals(Fee.TRANSFER_MORT_RESERVE_FEE_TYPE))
        {
            EDITBigDecimal totalCashContribToMortRsv = new EDITBigDecimal(controlsAndBalancesTotalsVO.getTotalCashContribToMortRsv());
            controlsAndBalancesTotalsVO.setTotalCashContribToMortRsv(Util.roundToNearestCent(totalCashContribToMortRsv.subtractEditBigDecimal(rsDollars)).getBigDecimal());
        }
        else if (feeType.equals(Fee.ME_FEE_TYPE))
        {
            EDITBigDecimal totalCashMAndE = new EDITBigDecimal(controlsAndBalancesTotalsVO.getTotalCashMAndE());
            controlsAndBalancesTotalsVO.setTotalCashMAndE(Util.roundToNearestCent(totalCashMAndE.subtractEditBigDecimal(rsDollars)).getBigDecimal());
        }
        else if (feeType.equals(Fee.ADVISORY_FEE_TYPE))
        {
            EDITBigDecimal totalCashAdvisoryFees = new EDITBigDecimal(controlsAndBalancesTotalsVO.getTotalCashAdvisoryFees());
            controlsAndBalancesTotalsVO.setTotalCashAdvisoryFees(Util.roundToNearestCent(totalCashAdvisoryFees.subtractEditBigDecimal(rsDollars)).getBigDecimal());
        }
        else if (feeType.equals(Fee.MANAGEMENT_FEE_TYPE))
        {
            EDITBigDecimal totalCashMgmtFees = new EDITBigDecimal(controlsAndBalancesTotalsVO.getTotalCashMgmtFees());
            controlsAndBalancesTotalsVO.setTotalCashMgmtFees(Util.roundToNearestCent(totalCashMgmtFees.subtractEditBigDecimal(rsDollars)).getBigDecimal());
        }
        else if (feeType.equals(Fee.SVA_FEE_TYPE))
        {
            EDITBigDecimal totalCashSVAFees = new EDITBigDecimal(controlsAndBalancesTotalsVO.getTotalCashSVAFees());
            controlsAndBalancesTotalsVO.setTotalCashSVAFees(Util.roundToNearestCent(totalCashSVAFees.subtractEditBigDecimal(rsDollars)).getBigDecimal());
        }
        else if (feeType.equals(Fee.ADVANCE_TRANSFER_FEE_TYPE))
        {
            EDITBigDecimal totalCashAdvanceTransfers = new EDITBigDecimal(controlsAndBalancesTotalsVO.getTotalCashAdvanceTransfers());
            controlsAndBalancesTotalsVO.setTotalCashAdvanceTransfers(Util.roundToNearestCent(totalCashAdvanceTransfers.subtractEditBigDecimal(rsDollars)).getBigDecimal());
        }
        else if (feeType.equals(Fee.RVP_FEE_TYPE))
        {
            EDITBigDecimal totalCashRVPFees = new EDITBigDecimal(controlsAndBalancesTotalsVO.getTotalCashRVPFees());
            controlsAndBalancesTotalsVO.setTotalCashRVPFees(Util.roundToNearestCent(totalCashRVPFees.subtractEditBigDecimal(rsDollars)).getBigDecimal());
        }

        return controlsAndBalancesTotalsVO;
    }

    /**
     * Use this to safely close resultset.  Saves a
     * little duplicated code.
     * @param rs
     */
    private void closeResultSet(ResultSet rs)
    {

        if (rs != null)
        {
            try
            {
                rs.close();
            }
            catch (Exception ignore)
            {
            }
        }
    }

    private void closePreparedStatement(PreparedStatement ps)
    {

        if (ps != null)
        {
            try
            {
                ps.close();
            }
            catch (Exception ignore)
            {
            }
        }
    }

    private void closeConnection(Connection conn)
    {
        if (conn != null)
        {
            try
            {
                conn.close();
            }
            catch (Exception ignore)
            {
            }
        }
    }

    /**
     * Set up the export file
     * @return  File - define name
     */
    private File getExportFile(String cycleDate)
    {
        EDITDate editCycleDate = new EDITDate(cycleDate);

        String yy = editCycleDate.getFormattedYear().substring(2, 4);
        String mm = editCycleDate.getFormattedMonth();
        String dd = editCycleDate.getFormattedDay();
        String dateString = yy + mm + dd;

        EDITExport export1 = ServicesConfig.getEDITExport("ExportDirectory1");

        File exportFile = new File(export1.getDirectory() + fileName + "_" + dateString + ".txt");

        return exportFile;
    }

    /**
     * Executes the sql to retrieve financial activity
     * for the given segments, buckets, fromDate and toFromIndicator
     *
     * @param filteredFundFK
     * @param fromDate
     * @param cycleDate
     * @param toFromIndicator
     * @param chargeCodeFK
     * @param resultSet
     * @param conn
     * @throws Exception
     */
    private void executeContractSql(long filteredFundFK,
                                    String fromDate,
                                    String cycleDate,
                                    String toFromIndicator,
                                    long chargeCodeFK,
                                    Hashtable resultSet,
                                    Connection conn,
                                    boolean onlyOneFundNumber) throws Exception
    {
        DBTable bucketHistoryDBTable = DBTable.getDBTableForTable("BucketHistory");
        DBTable editTrxHistoryDBTable = DBTable.getDBTableForTable("EDITTrxHistory");
        DBTable editTrxDBTable = DBTable.getDBTableForTable("EDITTrx");
        DBTable clientSetupDBTable = DBTable.getDBTableForTable("ClientSetup");
        DBTable contractSetupDBTable = DBTable.getDBTableForTable("ContractSetup");
        DBTable bucketDBTable = DBTable.getDBTableForTable("Bucket");
        DBTable segmentDBTable = DBTable.getDBTableForTable("Segment");
        DBTable investmentHistoryDBTable = DBTable.getDBTableForTable("InvestmentHistory");

        String bucketHistoryTable = bucketHistoryDBTable.getFullyQualifiedTableName();
        String editTrxHistoryTable = editTrxHistoryDBTable.getFullyQualifiedTableName();
        String editTrxTable = editTrxDBTable.getFullyQualifiedTableName();
        String clientSetupTable = clientSetupDBTable.getFullyQualifiedTableName();
        String contractSetupTable = contractSetupDBTable.getFullyQualifiedTableName();
        String bucketTable = bucketDBTable.getFullyQualifiedTableName();

        String bucketPKCol = bucketDBTable.getDBColumn("BucketPK").getFullyQualifiedColumnName();
        String bucketInvestmentFKCol = bucketDBTable.getDBColumn("InvestmentFK").getFullyQualifiedColumnName();

        String transactionTypeCTCol = editTrxDBTable.getDBColumn("TransactionTypeCT").getFullyQualifiedColumnName();
        String accountingPeriodCol = editTrxDBTable.getDBColumn("AccountingPeriod").getFullyQualifiedColumnName();
        String effectiveDateCol = editTrxDBTable.getDBColumn("EffectiveDate").getFullyQualifiedColumnName();
        String editTrxPKCol = editTrxDBTable.getDBColumn("EDITTrxPK").getFullyQualifiedColumnName();
        String clientSetupFKCol = editTrxDBTable.getDBColumn("ClientSetupFK").getFullyQualifiedColumnName();
        String statusCol = editTrxDBTable.getDBColumn("Status").getFullyQualifiedColumnName();

        String dollarsCol = bucketHistoryDBTable.getDBColumn("Dollars").getFullyQualifiedColumnName();
        String unitsCol = bucketHistoryDBTable.getDBColumn("Units").getFullyQualifiedColumnName();
        String bucketHistEDITTrxHistoryFKCol = bucketHistoryDBTable.getDBColumn("EDITTrxHistoryFK").getFullyQualifiedColumnName();
        String bucketHistToFromStatusCol = bucketHistoryDBTable.getDBColumn("ToFromStatus").getFullyQualifiedColumnName();
        String buckethistBucketFKCol = bucketHistoryDBTable.getDBColumn("BucketFK").getFullyQualifiedColumnName();
        String bucketHistoryPKCol = bucketHistoryDBTable.getDBColumn("BucketHistoryPK").getFullyQualifiedColumnName();

        String contractSetupSegmentFKCol = contractSetupDBTable.getDBColumn("SegmentFK").getFullyQualifiedColumnName();
        String contractSetupPKCol = contractSetupDBTable.getDBColumn("ContractSetupPK").getFullyQualifiedColumnName();

        String editTrxHistoryPKCol = editTrxHistoryDBTable.getDBColumn("EDITTrxHistoryPK").getFullyQualifiedColumnName();
        String editTrxFKCol = editTrxHistoryDBTable.getDBColumn("EDITTrxFK").getFullyQualifiedColumnName();
        String cycleDateCol = editTrxHistoryDBTable.getDBColumn("CycleDate").getFullyQualifiedColumnName();
        String origProcessDateTimeCol = editTrxHistoryDBTable.getDBColumn("OriginalProcessDateTime").getFullyQualifiedColumnName();

        String clientSetupPKCol = clientSetupDBTable.getDBColumn("ClientSetupPK").getFullyQualifiedColumnName();
        String contractSetupFKCol = clientSetupDBTable.getDBColumn("ContractSetupFK").getFullyQualifiedColumnName();

        String investmentHistoryTable = investmentHistoryDBTable.getFullyQualifiedTableName();
        String invHistChargeCodeFKCol = investmentHistoryDBTable.getDBColumn("ChargeCodeFK").getFullyQualifiedColumnName();
        String invHistEDITTrxHistoryFKCol = investmentHistoryDBTable.getDBColumn("EDITTrxHistoryFK").getFullyQualifiedColumnName();
        String invHistToFromStatusCol = investmentHistoryDBTable.getDBColumn("ToFromStatus").getFullyQualifiedColumnName();
        String invHistInvestmentFKCol = investmentHistoryDBTable.getDBColumn("InvestmentFK").getFullyQualifiedColumnName();
        String valuationDateCol = investmentHistoryDBTable.getDBColumn("ValuationDate").getFullyQualifiedColumnName();
        
        String segmentTable = segmentDBTable.getFullyQualifiedTableName();
        String segmentPKCol = segmentDBTable.getDBColumn("SegmentPK").getFullyQualifiedColumnName();
        
        DBTable investmentDBTable = DBTable.getDBTableForTable("Investment");
        String investmentTable = investmentDBTable.getFullyQualifiedTableName();
        String investmentPKCol = investmentDBTable.getDBColumn("InvestmentPK").getFullyQualifiedColumnName();
        String investmentFilteredFundFKCol = investmentDBTable.getDBColumn("FilteredFundFK").getFullyQualifiedColumnName();
        String investmentSegmentFKCol = investmentDBTable.getDBColumn("SegmentFK").getFullyQualifiedColumnName();

        String chargeCodeRestrictionSql;
        if (chargeCodeFK == 0)
        {
            chargeCodeRestrictionSql = invHistChargeCodeFKCol + " IS NULL ";
        }
        else
        {
            chargeCodeRestrictionSql = invHistChargeCodeFKCol + " = " + chargeCodeFK;
        }

        String editTrxHistorySql = " SELECT DISTINCT " + editTrxHistoryPKCol +
                " FROM " + editTrxHistoryTable +
                " JOIN " + editTrxTable +
                " ON " + editTrxFKCol + " = " + editTrxPKCol +
                " JOIN " + clientSetupTable +
                " ON " + clientSetupFKCol + " = " + clientSetupPKCol +
                " JOIN " + contractSetupTable +
                " ON " + contractSetupFKCol + " = " + contractSetupPKCol +
                " JOIN " + segmentTable +
                " ON " + segmentPKCol + " = " + contractSetupSegmentFKCol + 
                " JOIN " + investmentHistoryTable +
                " ON " + invHistEDITTrxHistoryFKCol + " = " + editTrxHistoryPKCol +
                " JOIN " + investmentTable +
                " ON " + segmentPKCol + " = " + investmentSegmentFKCol + 
                " WHERE " + investmentFilteredFundFKCol + " = " + filteredFundFK;

        if (!onlyOneFundNumber)
        {
            editTrxHistorySql = editTrxHistorySql + " AND " + chargeCodeRestrictionSql;
        }

        editTrxHistorySql = editTrxHistorySql + " AND " + cycleDateCol + " > ?" +
                " AND " + cycleDateCol + " <= ?" +
                " AND " + invHistToFromStatusCol + " = '" + toFromIndicator + "'" +
                " AND ((" + statusCol + " IN ('N', 'A'))" +
                " OR (" + statusCol + " IN ('U', 'R')" +
                " AND " + origProcessDateTimeCol + " <= ?))" +
                " AND " + transactionTypeCTCol + "IN ('PY', 'TF', 'STF', 'FT', 'TU', 'HFTA', 'HFTP', 'HFSA', 'HFSP', 'ML', 'LO', 'LR', 'WI', 'FS', 'SRO', 'NT', 'LS')";
        // MD transactions are processed seperately.

        PreparedStatement ps1 = null;
        PreparedStatement ps2 = null;

        ResultSet rs1 = null;
        ResultSet rs2 = null;

        try
        {
            ps1 = conn.prepareStatement(editTrxHistorySql);
            ps1.setDate(1, DBUtil.convertStringToDate(fromDate));
            ps1.setDate(2, DBUtil.convertStringToDate(cycleDate));
            ps1.setTimestamp(3, DBUtil.convertStringToTimestamp(fromDate + " " + EDITDateTime.DEFAULT_MAX_TIME));

            rs1 = ps1.executeQuery();

            List editTrxHistoryPKList = new ArrayList();

            while (rs1.next())
            {
                long editTrxHistoryPK = rs1.getLong("EDITTrxHistoryPK");

                editTrxHistoryPKList.add(new Long(editTrxHistoryPK));

            } // end rs

            if (editTrxHistoryPKList.size() > 0)
            {
                long[] editTrxHistoryPKs = new long[editTrxHistoryPKList.size()];

                for (int i = 0; i < editTrxHistoryPKList.size(); i++)
                {
                    Long editTrxHistoryPK = (Long) editTrxHistoryPKList.get(i);

                    editTrxHistoryPKs[i] = editTrxHistoryPK.longValue();
                }

                String editTrxHistoryColCheck = DBUtil.createInClause(editTrxHistoryPKCol, editTrxHistoryPKs);

                // Can't use GROUP BY clause because the records retrieved will be cartesian product and the sum columns may be wrong.
                // Ex: If an investment 3 investmenthistories and 3 buckethistories ... the number of rows retrieved will be 9 using
                // the following query and sum would be thrice the actual sum of (BucketHistory.Dollars and BucketHistory.Units)
                String sql = " SELECT " + transactionTypeCTCol + ", " + accountingPeriodCol + ", " + effectiveDateCol + ", "  + valuationDateCol +
                        " , " + dollarsCol + "AS TotalDollars, " + unitsCol + "AS TotalUnits, " + bucketHistoryPKCol +
                        " FROM " + bucketHistoryTable +
                        " JOIN " + editTrxHistoryTable +
                        " ON " + bucketHistEDITTrxHistoryFKCol + " = " + editTrxHistoryPKCol +
                        " JOIN " + editTrxTable +
                        " ON " + editTrxFKCol + " = " + editTrxPKCol +
                        " JOIN " + bucketTable +
                        " ON " + bucketPKCol + " = " + buckethistBucketFKCol +
                        " JOIN " + investmentHistoryTable +
                        " ON " + invHistEDITTrxHistoryFKCol + " = " + bucketHistEDITTrxHistoryFKCol +
                        " AND " + bucketInvestmentFKCol + " = " + invHistInvestmentFKCol +
                        " JOIN " + investmentTable + 
                        " ON " + investmentPKCol + " = " + invHistInvestmentFKCol + 
                        " AND " + investmentPKCol + " = " + bucketInvestmentFKCol + 
                        " WHERE " + editTrxHistoryColCheck +
                        " AND " + investmentFilteredFundFKCol + " = " + filteredFundFK + 
                        " AND " + bucketHistToFromStatusCol + " = '" + toFromIndicator + "'" +
                        " AND " + invHistToFromStatusCol + " = '" + toFromIndicator + "'";

                Map bucketHistoryPKMap = new HashMap();

                try
                {
                    ps2 = conn.prepareStatement(sql);

                    rs2 = ps2.executeQuery();

                    while (rs2.next())
                    {
                        String transactionTypeCT = rs2.getString("TransactionTypeCT");
                        String accountingPeriod = rs2.getString("AccountingPeriod");
                        String effectiveDate = DBUtil.readAndConvertDate(rs2, "EffectiveDate");
                        String valuationDate = DBUtil.readAndConvertDate(rs2, "ValuationDate");
                        EDITBigDecimal totalDollars = new EDITBigDecimal(rs2.getBigDecimal("TotalDollars"));
                        EDITBigDecimal totalUnits = new EDITBigDecimal(rs2.getBigDecimal("TotalUnits"));
                        String bucketHistoryPK = rs2.getLong("BucketHistoryPK")+"";

                        // There is chance of having multiple InvestmentHistories with same ValuationDate or different ValuationDates for an Investment
                        // still BucketHistory is the same ... in this case we have to consider Dollars and Units from BucketHistory only once.
                        // In case of different ValuationDates, we have to consider the InvestmemntHistory with greatest ValuationDate
                        if (!bucketHistoryPKMap.containsKey(bucketHistoryPK))
                        {
                            bucketHistoryPKMap.put(bucketHistoryPK, valuationDate + "|" + totalDollars + "|" + totalUnits);

                            transactionTypeCT = setTransactionType(transactionTypeCT, null);

                            resultSet = updateTrxTotals(resultSet, transactionTypeCT, accountingPeriod, effectiveDate, valuationDate, totalDollars, totalUnits);
                        }
                        else
                        {
                            String previousValuationDate = Util.fastTokenizer((String) bucketHistoryPKMap.get(bucketHistoryPK), "|")[0];

                             if (new EDITDate(valuationDate).after(new EDITDate(previousValuationDate)))
                            {
                                EDITBigDecimal previousTotalDollars = new EDITBigDecimal(Util.fastTokenizer((String) bucketHistoryPKMap.get(bucketHistoryPK), "|")[1]);
                                EDITBigDecimal previousTotalUnits = new EDITBigDecimal(Util.fastTokenizer((String) bucketHistoryPKMap.get(bucketHistoryPK), "|")[2]);

                                resultSet = subtractTrxTotals(resultSet, transactionTypeCT, accountingPeriod, effectiveDate, valuationDate, previousTotalDollars, previousTotalUnits);
                                resultSet = updateTrxTotals(resultSet, transactionTypeCT, accountingPeriod, effectiveDate, valuationDate, totalDollars, totalUnits);
                            }
                        }
                    }
                }
                catch (Exception e)
                {
                    throw e;
                }
                finally
                {
                    closeResultSet(rs2);
                    closePreparedStatement(ps2);
                }
            }

            // FOR 'MD' transactions.
            editTrxHistorySql = " SELECT DISTINCT " + editTrxHistoryPKCol +
            " FROM " + editTrxHistoryTable +
            " JOIN " + editTrxTable +
            " ON " + editTrxFKCol + " = " + editTrxPKCol +
            " JOIN " + clientSetupTable +
            " ON " + clientSetupFKCol + " = " + clientSetupPKCol +
            " JOIN " + contractSetupTable +
            " ON " + contractSetupFKCol + " = " + contractSetupPKCol +
            " JOIN " + segmentTable +
            " ON " + segmentPKCol + " = " + contractSetupSegmentFKCol + 
            " JOIN " + investmentHistoryTable +
            " ON " + invHistEDITTrxHistoryFKCol + " = " + editTrxHistoryPKCol +
            " JOIN " + investmentTable +
            " ON " + segmentPKCol + " = " + investmentSegmentFKCol +             
            " WHERE " + investmentFilteredFundFKCol + " = " + filteredFundFK;

            if (!onlyOneFundNumber)
            {
                editTrxHistorySql = editTrxHistorySql + " AND " + chargeCodeRestrictionSql;
            }

            editTrxHistorySql = editTrxHistorySql + " AND " + cycleDateCol + " > ?" +
                    " AND " + cycleDateCol + " <= ?" +
                    " AND " + invHistToFromStatusCol + " = '" + toFromIndicator + "'" +
                    " AND ((" + statusCol + " IN ('N', 'A'))" +
                    " OR (" + statusCol + " IN ('U', 'R')" +
                    " AND " + origProcessDateTimeCol + " <= ?))" +
                    " AND " + transactionTypeCTCol + "IN ('MD')";

            // Notice the difference in where condition for Undone and Reversal records for 'MD' transactions and non 'MD' transactions

            ps1 = conn.prepareStatement(editTrxHistorySql);
            ps1.setDate(1, DBUtil.convertStringToDate(fromDate));
            ps1.setDate(2, DBUtil.convertStringToDate(cycleDate));
            ps1.setTimestamp(3, DBUtil.convertStringToTimestamp(fromDate + " " + EDITDateTime.DEFAULT_MAX_TIME));

            rs1 = ps1.executeQuery();

            editTrxHistoryPKList = new ArrayList();

            while (rs1.next())
            {
                long editTrxHistoryPK = rs1.getLong("EDITTrxHistoryPK");

                editTrxHistoryPKList.add(new Long(editTrxHistoryPK));
            }
            
            if (editTrxHistoryPKList.size() > 0)
            {
                long[] editTrxHistoryPKs = new long[editTrxHistoryPKList.size()];

                for (int i = 0; i < editTrxHistoryPKList.size(); i++)
                {
                    Long editTrxHistoryPK = (Long) editTrxHistoryPKList.get(i);

                    editTrxHistoryPKs[i] = editTrxHistoryPK.longValue();
                }

                String editTrxHistoryColCheck = DBUtil.createInClause(editTrxHistoryPKCol, editTrxHistoryPKs);

                // SL
                // Before 2/14/08 modification for 'MD' transactions, we used to get Units from BucketHistory and Dollars from BucketChargeHistory
                // So, for every 'MD' transaction we need to go to BucketChargeHistory and then update the totals which is a performance hit
                // There were two BucketChargeHistories 'BucketCoi' and 'BucketAdminCharge' (ChargeTypeCT) that we are interested in
                // (According to Rob as of 2/14/08, the Dollar amount for 'BucketAdminCharge' is always zero
                // and the Dollar amount on BucketHistory is same as Dollar amount on BucketChargeHistory for 'BucketCoi')
                // So, inorder to gain performance increase, we avoided going to BucketChargeHistory to get the Dollar amount.
                // In future, if we need to get the Dollar amount from BucketChargeHistory we have to process each 'MD' record seperately

                // We can not combine BucketHistory and BucketChargeHistory to get the Dollar and Units in one query, if we need
                // to get Dollars from BucketChargeHistory and Units from BucketHistory because the SUM(Units) will be wrong when
                // we use GROUP BY function.
                // For example, if a BucketHistory has two BucketChargeHistories the SUM(Units) will be doubled because
                // the rows retrieved are two.

                // One more advantage we get for performance increase, if we do not need to go to BucketChargeHistory for Dollar
                // amount is, we can use the IN clause for all the EDITTrxHistories and get the totals in one single query - less db chattness.

                String sql = " SELECT " + accountingPeriodCol + ", " + effectiveDateCol + ", "  + valuationDateCol +
                        " , SUM(" + dollarsCol + ") AS TotalDollars, SUM(" + unitsCol + ") AS TotalUnits" +
                        " FROM " + bucketHistoryTable +
                        " JOIN " + editTrxHistoryTable +
                        " ON " + bucketHistEDITTrxHistoryFKCol + " = " + editTrxHistoryPKCol +
                        " JOIN " + editTrxTable +
                        " ON " + editTrxFKCol + " = " + editTrxPKCol +
                        " JOIN " + bucketTable +
                        " ON " + bucketPKCol + " = " + buckethistBucketFKCol +
                        " JOIN " + investmentHistoryTable +
                        " ON " + invHistEDITTrxHistoryFKCol + " = " + bucketHistEDITTrxHistoryFKCol +
                        " AND " + bucketInvestmentFKCol + " = " + invHistInvestmentFKCol +
                        " JOIN " + investmentTable + 
                        " ON " + investmentPKCol + " = " + invHistInvestmentFKCol + 
                        " AND " + investmentPKCol + " = " + bucketInvestmentFKCol + 
                        " WHERE " + editTrxHistoryColCheck +
                        " AND " + investmentFilteredFundFKCol + " = " + filteredFundFK + 
                        " AND " + bucketHistToFromStatusCol + " = '" + toFromIndicator + "'" +
                        " AND " + invHistToFromStatusCol + " = '" + toFromIndicator + "'" +
                        " AND " + dollarsCol + " <> 0" + " AND " + unitsCol + " <> 0" +
                        " GROUP BY " + accountingPeriodCol + ", " + effectiveDateCol + ", " + valuationDateCol;
                        
                try
                {
                    ps2 = conn.prepareStatement(sql);

                    rs2 = ps2.executeQuery();

                    while (rs2.next())
                    {
                        String accountingPeriod = rs2.getString("AccountingPeriod");
                        String effectiveDate = DBUtil.readAndConvertDate(rs2, "EffectiveDate");
                        String valuationDate = DBUtil.readAndConvertDate(rs2, "ValuationDate");
                        EDITBigDecimal totalDollars = new EDITBigDecimal(rs2.getBigDecimal("TotalDollars"));
                        EDITBigDecimal totalUnits = new EDITBigDecimal(rs2.getBigDecimal("TotalUnits"));
                        
                        // Since we avoided going to BucketChargeHistory, there is only one ChargeType for 'MD'
                        String transactionTypeCT = setTransactionType("BucketCoi", null);

                        resultSet = updateTrxTotals(resultSet, transactionTypeCT, accountingPeriod, effectiveDate, valuationDate, totalDollars, totalUnits);
                    }
                }
                catch (Exception e)
                {
                    throw e;
                }
                finally
                {
                    closeResultSet(rs2);
                    closePreparedStatement(ps2);
                }
            }
        }
        catch (Exception e)
        {
            throw e;
        }
        finally
        {
            closeResultSet(rs1);
            closePreparedStatement(ps1);
        }
    }

    // This is for only 'MD' transactions
    private Hashtable getValuesFromBucketChargeHistories(long editTrxHistoryPK,
                                                         Hashtable resultSet,
                                                         String bucketColCheck,
                                                         String toFromIndicator,
                                                         EDITBigDecimal totalUnits,
                                                         Connection conn) throws Exception
    {
        DBTable editTrxHistoryDBTable = DBTable.getDBTableForTable("EDITTrxHistory");
        DBTable editTrxDBTable = DBTable.getDBTableForTable("EDITTrx");
        DBTable bucketHistoryDBTable = DBTable.getDBTableForTable("BucketHistory");
        DBTable bucketChargeHistoryDBTable = DBTable.getDBTableForTable("BucketChargeHistory");
        DBTable bucketDBTable = DBTable.getDBTableForTable("Bucket");

        String bucketHistoryTable = bucketHistoryDBTable.getFullyQualifiedTableName();
        String bucketChargeHistoryTable = bucketChargeHistoryDBTable.getFullyQualifiedTableName();
        String editTrxHistoryTable = editTrxHistoryDBTable.getFullyQualifiedTableName();
        String editTrxTable = editTrxDBTable.getFullyQualifiedTableName();
        String bucketTable = bucketDBTable.getFullyQualifiedTableName();

        String editTrxPKCol = editTrxDBTable.getDBColumn("EDITTrxPK").getFullyQualifiedColumnName();
        String accountingPeriodCol = editTrxDBTable.getDBColumn("AccountingPeriod").getFullyQualifiedColumnName();
        String effectiveDateCol = editTrxDBTable.getDBColumn("EffectiveDate").getFullyQualifiedColumnName();

        String bucketHistEDITTrxHistoryFKCol = bucketHistoryDBTable.getDBColumn("EDITTrxHistoryFK").getFullyQualifiedColumnName();
        String bucketPKCol = bucketDBTable.getDBColumn("BucketPK").getFullyQualifiedColumnName();
        String bucketInvestmentFKCol = bucketDBTable.getDBColumn("InvestmentFK").getFullyQualifiedColumnName();
        String buckethistBucketFKCol = bucketHistoryDBTable.getDBColumn("BucketFK").getFullyQualifiedColumnName();
        String bucketHistoryPKCol = bucketHistoryDBTable.getDBColumn("BucketHistoryPK").getFullyQualifiedColumnName();
        String editTrxHistoryPKCol = editTrxHistoryDBTable.getDBColumn("EDITTrxHistoryPK").getFullyQualifiedColumnName();
        String editTrxFKCol = editTrxHistoryDBTable.getDBColumn("EDITTrxFK").getFullyQualifiedColumnName();
        String bucketHistToFromStatusCol = bucketHistoryDBTable.getDBColumn("ToFromStatus").getFullyQualifiedColumnName();
        String chargeTypeCTCol = bucketChargeHistoryDBTable.getDBColumn("ChargeTypeCT").getFullyQualifiedColumnName();

        String chargeAmountCol = bucketChargeHistoryDBTable.getDBColumn("ChargeAmount").getFullyQualifiedColumnName();
        String bucketHistoryFKCol = bucketChargeHistoryDBTable.getDBColumn("BucketHistoryFK").getFullyQualifiedColumnName();

        DBTable investmentHistoryDBTable = DBTable.getDBTableForTable("InvestmentHistory");
        String investmentHistoryTable = investmentHistoryDBTable.getFullyQualifiedTableName();
        String invHistEDITTrxHistoryFKCol = investmentHistoryDBTable.getDBColumn("EDITTrxHistoryFK").getFullyQualifiedColumnName();
        String invHistInvestmentFKCol = investmentHistoryDBTable.getDBColumn("InvestmentFK").getFullyQualifiedColumnName();
        String invHistToFromStatusCol = investmentHistoryDBTable.getDBColumn("ToFromStatus").getFullyQualifiedColumnName();
        String valuationDateCol = investmentHistoryDBTable.getDBColumn("ValuationDate").getFullyQualifiedColumnName();

        PreparedStatement ps3 = null;

        ResultSet rs3 = null;

        String sql = " SELECT " + chargeTypeCTCol + ", " + accountingPeriodCol + ", " + effectiveDateCol + ", "  + valuationDateCol + ", " +
                " SUM(" + chargeAmountCol + ") AS TotalDollars" +
                " FROM " + bucketChargeHistoryTable +
                " JOIN " + bucketHistoryTable +
                " ON " + bucketHistoryFKCol + " = " + bucketHistoryPKCol +
                " JOIN " + editTrxHistoryTable +
                " ON " + bucketHistEDITTrxHistoryFKCol + " = " + editTrxHistoryPKCol +
                " JOIN " + editTrxTable +
                " ON " + editTrxFKCol + " = " + editTrxPKCol +
                " JOIN " + bucketTable +
                " ON " + bucketPKCol + " = " + buckethistBucketFKCol +
                " JOIN " + investmentHistoryTable +
                " ON " + invHistEDITTrxHistoryFKCol + " = " + bucketHistEDITTrxHistoryFKCol +
                " AND " + bucketInvestmentFKCol + " = " + invHistInvestmentFKCol +
                " WHERE " + editTrxHistoryPKCol + " = " + editTrxHistoryPK +
                " AND " + bucketColCheck +
                " AND " + bucketHistToFromStatusCol + " = '" + toFromIndicator + "'" +
                " AND " + invHistToFromStatusCol + " = '" + toFromIndicator + "'" +
                " AND " + chargeTypeCTCol + " IN ('BucketAdminCharge', 'BucketCoi')" +
                " GROUP BY " + chargeTypeCTCol + ", " + accountingPeriodCol + ", " + effectiveDateCol + ", " + valuationDateCol ;

        try
        {
            ps3 = conn.prepareStatement(sql);

            rs3 = ps3.executeQuery();

            while (rs3.next())
            {
                EDITBigDecimal totalDollars = new EDITBigDecimal(rs3.getBigDecimal("TotalDollars"));

                String chargeTypeCT = setTransactionType(rs3.getString("ChargeTypeCT"), null);

                String accountingPeriod = rs3.getString("AccountingPeriod");
                String effectiveDate = DBUtil.readAndConvertDate(rs3, "EffectiveDate");
                String valuationDate = DBUtil.readAndConvertDate(rs3, "ValuationDate");

                if (chargeTypeCT.equalsIgnoreCase(COST_OF_INSURANCE))
                {
                    resultSet = updateTrxTotals(resultSet, chargeTypeCT, accountingPeriod, effectiveDate, valuationDate, totalDollars, totalUnits);
                }
                else
                {
                    resultSet = updateTrxTotals(resultSet, chargeTypeCT, accountingPeriod, effectiveDate, valuationDate, totalDollars, new EDITBigDecimal());
                }
            }
        }
        finally
        {
            closeResultSet(rs3);
            closePreparedStatement(ps3);
        }

        return resultSet;
    }

    private Hashtable updateTrxTotals(Hashtable resultSet,
                                      String transactionTypeCT,
                                      String accountingPeriod,
                                      String effectiveDate,
                                      String valuationDate,
                                      EDITBigDecimal totalDollars,
                                      EDITBigDecimal totalUnits)
    {
        String resultSetKey = transactionTypeCT + "|" + accountingPeriod + "|" + effectiveDate + "|" + valuationDate;

        if (resultSet.containsKey(resultSetKey))
        {
            EDITBigDecimal[] trxTotals = (EDITBigDecimal[]) resultSet.get(resultSetKey);
            trxTotals[0] = trxTotals[0].addEditBigDecimal(totalDollars);
            trxTotals[1] = trxTotals[1].addEditBigDecimal(totalUnits);
        }
        else
        {
            EDITBigDecimal[] trxTotals = new EDITBigDecimal[2];
            trxTotals[0] = totalDollars;
            trxTotals[1] = totalUnits;

            resultSet.put(resultSetKey, trxTotals);
        }

        return resultSet;
    }

    private Hashtable subtractTrxTotals(Hashtable resultSet,
                                        String transactionTypeCT,
                                        String accountingPeriod,
                                        String effectiveDate,
                                        String valuationDate,
                                        EDITBigDecimal totalDollars,
                                        EDITBigDecimal totalUnits)
    {
        String resultSetKey = transactionTypeCT + "|" + accountingPeriod + "|" + effectiveDate + "|" + valuationDate;

        if (resultSet.containsKey(resultSetKey))
        {
            EDITBigDecimal[] trxTotals = (EDITBigDecimal[]) resultSet.get(resultSetKey);
            trxTotals[0] = trxTotals[0].subtractEditBigDecimal(totalDollars);
            trxTotals[1] = trxTotals[1].subtractEditBigDecimal(totalUnits);
        }
        else
        {
            EDITBigDecimal[] trxTotals = new EDITBigDecimal[2];
            trxTotals[0] = new EDITBigDecimal(totalDollars.getBigDecimal()).negate();
            trxTotals[1] = new EDITBigDecimal(totalUnits.getBigDecimal()).negate();

            resultSet.put(resultSetKey, trxTotals);
        }

        return resultSet;
    }

    /**
     * Executes the sql to retrieve financial activity
     * for the Fees
     * @param fromDate
     * @param cycleDate
     * @param toFromIndicator
     * @param rs
     * @param conn
     * @return
     * @throws Exception
     */
    private ResultSet executeFeeSql(String transactionTypeCT,  //DFACC or DPURCH
                                    String fromDate,
                                    String cycleDate,
                                    String toFromIndicator,
                                    long chargeCodeFK,
                                    long filteredFundFK,
                                    ResultSet rs,
                                    Connection conn,
                                    boolean onlyOneFundNumber) throws Exception
    {

        //        SELECT FOR M&E PAYABLE SAMPLE
        //
        //        SELECT Fee.TransactionTypeCT, SUM(Fee.TrxAmount)
        //        FROM Fee, FeeDescription
        //        WHERE Fee.TransactionTypeCT = �DFACC�
        //        AND Fee.FilteredFundFK = YYYY
        //        AND Fee.FeeDescriptionFK =
        //           FeeDescription.FeeDescriptionPK
        //        AND FeeDescription.FeeTypeCT = �MEFee�
        //        AND Fee.ChargeCodeFK = XX (or IS NULL)
        //        AND Fee.ProcessDateTime > FROMDATE
        //        AND Fee.ProcessDateTime <= CYCLEDATE
        //        AND Fee.ToFromInd = �T�      OR �F�
        //        AND
        //        (
        //              Fee.Status in (�N�, �A�)
        //              OR
        //              (
        //                   Fee.Status IN (�U�, �R�)
        //                   AND Fee.OriginalProcessDateTime
        //                           < FROMDATE
        //               )
        //        )
        //        GROUP BY
        //
        //
        //        -------------------------------------------------------
        //
        //        SELECT FOR ADVANCE TRANSFERS SAMPLE
        //
        //        SELECT Fee.TransactionTypeCT,SUM(Fee.TrxAmount)
        //        FROM Fee
        //        WHERE Fee.TransactionTypeCT = �DPURCH�
        //        AND Fee.FilteredFundFK = YYYY
        //        AND Fee.ChargeCodeFK = XX (or IS NULL)
        //        AND Fee.ProcessDateTime > FROMDATE
        //        AND Fee.ProcessDateTime <= CYCLEDATE
        //        AND Fee.ToFromInd = �T�      OR �F�
        //        AND
        //        (
        //              Fee.Status in (�N�, �A�)
        //              OR
        //              (
        //                   Fee.Status IN (�U�, �R�)
        //                   AND Fee.OriginalProcessDateTime
        //                             < FROMDATE
        //               )
        //        )
        //        GROUP BY


        DBTable feeDBTable  = DBTable.getDBTableForTable("Fee");
        DBTable feeDescriptionDBTable = DBTable.getDBTableForTable("FeeDescription");

        String feeTableName  = feeDBTable.getFullyQualifiedTableName();
        String feeDescriptionTableName = feeDescriptionDBTable.getFullyQualifiedTableName();

        String feeTrxAmountCol = feeDBTable.getDBColumn("TrxAmount").getFullyQualifiedColumnName();
        String feeUnitsCol = feeDBTable.getDBColumn("Units").getFullyQualifiedColumnName();
        String feeTransactionTypeCTCol = feeDBTable.getDBColumn("TransactionTypeCT").getFullyQualifiedColumnName();
        String feeChargeCodeFKCol = feeDBTable.getDBColumn("ChargeCodeFK").getFullyQualifiedColumnName();
        String feeToFromIndCol = feeDBTable.getDBColumn("ToFromInd").getFullyQualifiedColumnName();
        String feeStatusCol = feeDBTable.getDBColumn("StatusCT").getFullyQualifiedColumnName();
        String feeProcessDateTimeCol = feeDBTable.getDBColumn("ProcessDateTime").getFullyQualifiedColumnName();
        String feeOriginalProcessDateCol = feeDBTable.getDBColumn("OriginalProcessDate").getFullyQualifiedColumnName();
        String feefeeDescriptionFKCol = feeDBTable.getDBColumn("FeeDescriptionFK").getFullyQualifiedColumnName();
        String feeFilteredFundFKCol = feeDBTable.getDBColumn("FilteredFundFK").getFullyQualifiedColumnName();
        String feeAccountingPeriodCol = feeDBTable.getDBColumn("AccountingPeriod").getFullyQualifiedColumnName();
        String feeEffectiveDateCol = feeDBTable.getDBColumn("EffectiveDate").getFullyQualifiedColumnName();

        String feeDescriptionFeeTypeCT = feeDescriptionDBTable.getDBColumn("FeeTypeCT").getFullyQualifiedColumnName();
        String feeDescriptionPKCol = feeDescriptionDBTable.getDBColumn("FeeDescriptionPK").getFullyQualifiedColumnName();

        String chargeCodeRestrictionSql;
        if (chargeCodeFK == 0)
        {
            chargeCodeRestrictionSql =  feeChargeCodeFKCol + " IS NULL";
        }
        else
        {
            chargeCodeRestrictionSql = feeChargeCodeFKCol + " = " + chargeCodeFK;
        }

        String toFromIndSql = null;

        // THE TOFROMIND SEEMS TO BE SET TO F OR NULL. TO BE SAFE WE WILL
        // ALSO CHECK FOR T
        if (toFromIndicator.equals("T"))
        {
            toFromIndSql = "(" + feeToFromIndCol + " = 'T' OR " +
                    feeToFromIndCol + " IS NULL )";

        }
        else
        {
            toFromIndSql = feeToFromIndCol + " = 'F'";
        }

        final String DFACC = "DFACC";
        final String DPURCH = "DPURCH";
        final String DFCASH = "DFCASH";
        final String DFOFF = "DFOFF";

        String sql = null;
        // Not sure - may be easier to understand by not trying to
        // factor out sets of common lines between these two.
        if (DFACC.equals(transactionTypeCT))
        {
            sql =
                    " SELECT " + feeTransactionTypeCTCol + ", " + feeDescriptionFeeTypeCT + ", " + feeAccountingPeriodCol + ", " + feeEffectiveDateCol +  ", " +
                    " SUM(" + feeTrxAmountCol + ") AS TotalDollars,  " + "SUM(" + feeUnitsCol + ") AS TotalUnits" +
                    " FROM " + feeTableName +
                    " JOIN " + feeDescriptionTableName +
                    " ON " + feefeeDescriptionFKCol + " = " + feeDescriptionPKCol +
                    " WHERE " + feeTransactionTypeCTCol + " = '" + DFACC + "'" +
                    " AND " + feeFilteredFundFKCol + " = " + filteredFundFK +
                    " AND (" + feeDescriptionFeeTypeCT + " = " + "'MEFee'" +
                    " OR " + feeDescriptionFeeTypeCT + " = " + "'ManagementFee')";

            if (!onlyOneFundNumber)
            {
                sql = sql + " AND " + chargeCodeRestrictionSql;
            }

            sql = sql + " AND " + feeProcessDateTimeCol + " > ?" +
                    " AND " + feeProcessDateTimeCol + " <= ?" +
                    " AND " + toFromIndSql +
                    " AND (" + feeStatusCol + " IN ('N', 'A')" +
                    " OR (" + feeStatusCol + " IN ('U', 'R')" +
                    " AND " + feeOriginalProcessDateCol + " <= ?))" +
                    " GROUP BY " + feeTransactionTypeCTCol + ", " + feeDescriptionFeeTypeCT + ", " + feeAccountingPeriodCol + ", " + feeEffectiveDateCol;

        }
        else if (DPURCH.equals(transactionTypeCT) || DFCASH.equals(transactionTypeCT) || DFOFF.equals(transactionTypeCT))
        {
            sql =
                    " SELECT " + feeTransactionTypeCTCol + ", " + feeDescriptionFeeTypeCT + ", " + feeAccountingPeriodCol + ", " + feeEffectiveDateCol + ", " +
                    " SUM(" + feeTrxAmountCol + ") AS TotalDollars, " + "SUM(" + feeUnitsCol + ") AS TotalUnits" +
                    " FROM " + feeTableName +
                    " JOIN " + feeDescriptionTableName +
                    " ON " + feefeeDescriptionFKCol + " = " + feeDescriptionPKCol +
                    " WHERE " + feeTransactionTypeCTCol + " = '" + transactionTypeCT + "'" +
                    " AND " + feeFilteredFundFKCol + " = " + filteredFundFK;

            if (!onlyOneFundNumber)
            {
                sql = sql + " AND " + chargeCodeRestrictionSql;
            }

            sql = sql + " AND " + feeProcessDateTimeCol + " > ?" +
                    " AND " + feeProcessDateTimeCol + " <= ?" +
                    " AND " + toFromIndSql +
                    " AND (" + feeStatusCol + " IN ('N', 'A')" +
                    " OR (" + feeStatusCol + " IN ('U', 'R')" +
                    " AND " + feeOriginalProcessDateCol + " <= ?))" +
                    " GROUP BY " + feeTransactionTypeCTCol + ", " + feeDescriptionFeeTypeCT + ", " + feeAccountingPeriodCol + ", " + feeEffectiveDateCol;
        }
        else
        {
            throw new IllegalArgumentException(
                    "ActivityFileInterfaceProcessor expects " +
                    "DFACC or DPURCH or DFCASH or DFOFF for executeFeeSql(...)");
        }

        PreparedStatement ps = conn.prepareStatement(sql);
        //Note: from date is not inclusive
        //cycle date that they enter is inclusive
        ps.setTimestamp(1, DBUtil.convertStringToTimestamp(fromDate + " " + EDITDateTime.DEFAULT_MAX_TIME));
        ps.setTimestamp(2, DBUtil.convertStringToTimestamp(cycleDate + " " + EDITDateTime.DEFAULT_MAX_TIME));
        ps.setDate(3, DBUtil.convertStringToDate(fromDate));

        rs = ps.executeQuery();
        return rs;
    }




    /**
     * Adds dollars and units retrieved in getActivityForFund method
     * to existing dollar/unit totals by
     * product
     * @param cffsVO
     * @param controlBalanceVO
     * @param transactionType
     * @param rsDollars
     * @param rsUnits
     * @param cycleDate
     * @return
     * @throws Exception
     */
    private Map addUnitsDollars(ProductFilteredFundStructureVO cffsVO,
                                ControlBalanceVO controlBalanceVO,
                                String transactionType,
                                EDITBigDecimal rsDollars,
                                EDITBigDecimal rsUnits,
                                String cycleDate,
                                long chargeCodeFK,
                                String cbdKey,
                                String feeType) throws Exception
    {
        FilteredFundVO ffVO = (FilteredFundVO) cffsVO.getParentVO(FilteredFundVO.class);

        EDITBigDecimal unitValue = new EDITBigDecimal();

        long filteredFundPK = ffVO.getFilteredFundPK();
        String pricingDirection = ffVO.getPricingDirection();

        if (!rsUnits.isEQ("0"))
        {
            UnitValuesVO[] unitValuesVO = engineLookup.getUnitValuesByFilteredFundIdDateChargeCode(filteredFundPK,
                    cycleDate,
                    pricingDirection,
                    chargeCodeFK);
            if (unitValuesVO != null)
            {
                unitValue = new EDITBigDecimal(unitValuesVO[0].getUnitValue());
            }
        }

        String fundNumber = ffVO.getFundNumber();

        EDITBigDecimal endingDollarBalance = new EDITBigDecimal(controlBalanceVO.getEndingDollarBalance());
        EDITBigDecimal endingUnitBalance = new EDITBigDecimal(controlBalanceVO.getEndingUnitBalance());
        String endingBalCycleDate = controlBalanceVO.getEndingBalanceCycleDate();

        String clientOrRealFundNumber = convertToClientFund(fundNumber, chargeCodeFK);

        String totsByFundKey = clientOrRealFundNumber + "|" + transactionType;

        if (totalsByFund.containsKey(totsByFundKey))
        {
            ControlsAndBalancesTotalsVO cntlsAndBalancesTotalsVO = (ControlsAndBalancesTotalsVO) totalsByFund.get(totsByFundKey);

            EDITBigDecimal totalDollars = new EDITBigDecimal(cntlsAndBalancesTotalsVO.getTotalDollars());
            EDITBigDecimal totalUnits   = new EDITBigDecimal(cntlsAndBalancesTotalsVO.getTotalUnits());
            EDITBigDecimal beginningDollarBalance = new EDITBigDecimal(cntlsAndBalancesTotalsVO.getBeginningDollarBalance());
            EDITBigDecimal beginningUnitBalance = new EDITBigDecimal(cntlsAndBalancesTotalsVO.getBeginningUnitBalance());

            totalDollars = Util.roundToNearestCent(totalDollars.addEditBigDecimal(rsDollars));
            totalUnits = totalUnits.addEditBigDecimal(rsUnits);

            beginningDollarBalance = beginningDollarBalance.addEditBigDecimal(endingDollarBalance);
            beginningUnitBalance = beginningUnitBalance.addEditBigDecimal(endingUnitBalance);

            cntlsAndBalancesTotalsVO.setTotalDollars(totalDollars.getBigDecimal());
            cntlsAndBalancesTotalsVO.setTotalUnits(totalUnits.getBigDecimal());

            totalsByFund.put(totsByFundKey, cntlsAndBalancesTotalsVO);

            String compFundUpdKey = cffsVO.getProductFilteredFundStructurePK()+ "|" + chargeCodeFK;
            if (productFundUpdates.containsKey(compFundUpdKey))
            {
                List ffVector = (List) productFundUpdates.get(compFundUpdKey);

                EDITBigDecimal newEndingDollarBalance = (EDITBigDecimal) ffVector.get(0);
                EDITBigDecimal newEndingUnitBalance = (EDITBigDecimal) ffVector.get(1);
                EDITBigDecimal newEndingUnitBalanceForControlBalance = (EDITBigDecimal) ffVector.get(2);

                if (!transactionType.equalsIgnoreCase("M&EP") && !transactionType.equalsIgnoreCase("IMFP"))
                {
                    newEndingDollarBalance = Util.roundToNearestCent(newEndingDollarBalance.addEditBigDecimal(rsUnits.multiplyEditBigDecimal(unitValue)));
                    newEndingUnitBalance = newEndingUnitBalance.addEditBigDecimal(rsUnits);
                    newEndingUnitBalanceForControlBalance = newEndingUnitBalanceForControlBalance.addEditBigDecimal(rsUnits);
                }

                ffVector.add(0, newEndingDollarBalance);
                ffVector.add(1, Util.roundUnits(newEndingUnitBalance, 3));
                ffVector.add(2, newEndingUnitBalanceForControlBalance);
            }
            else
            {
                List ffVector = new ArrayList();

                EDITBigDecimal newEndingDollarBalance = new EDITBigDecimal(cntlsAndBalancesTotalsVO.getBeginningDollarBalance());
                EDITBigDecimal newEndingUnitBalance = new EDITBigDecimal(cntlsAndBalancesTotalsVO.getBeginningUnitBalance());
                EDITBigDecimal newEndingUnitBalanceForControlBalance = new EDITBigDecimal(cntlsAndBalancesTotalsVO.getBeginningUnitBalance());

                if (!transactionType.equalsIgnoreCase("M&EP") && !transactionType.equalsIgnoreCase("IMFP"))
                {
                    newEndingDollarBalance = Util.roundToNearestCent(endingDollarBalance.addEditBigDecimal(rsUnits.multiplyEditBigDecimal(unitValue)));
                    newEndingUnitBalance = endingUnitBalance.addEditBigDecimal(rsUnits);
                    newEndingUnitBalanceForControlBalance = endingUnitBalance.addEditBigDecimal(rsUnits);
                }

                ffVector.add(0, newEndingDollarBalance);
                ffVector.add(1, Util.roundUnits(newEndingUnitBalance, 3));
                ffVector.add(2, newEndingUnitBalanceForControlBalance);

                productFundUpdates.put(compFundUpdKey, ffVector);
            }
        }
        else
        {
            ControlsAndBalancesTotalsVO cntlsAndBalancesTotalsVO = new ControlsAndBalancesTotalsVO();

            cntlsAndBalancesTotalsVO.setTrxTypeDesc(transactionType);
            cntlsAndBalancesTotalsVO.setFundNumber(fundNumber);
            cntlsAndBalancesTotalsVO.setBeginningDollarBalance(endingDollarBalance.getBigDecimal());
            cntlsAndBalancesTotalsVO.setBeginningUnitBalance(endingUnitBalance.getBigDecimal());

            cntlsAndBalancesTotalsVO.setBeginningBalanceCycleDate(endingBalCycleDate);

            cntlsAndBalancesTotalsVO.setTotalDollars(Util.roundToNearestCent(rsDollars).getBigDecimal());
            cntlsAndBalancesTotalsVO.setTotalUnits(rsUnits.getBigDecimal());

            totalsByFund.put(totsByFundKey, cntlsAndBalancesTotalsVO);

            String compFundUpdKey = cffsVO.getProductFilteredFundStructurePK() + "|" + chargeCodeFK;
            if (productFundUpdates.containsKey(compFundUpdKey))
            {
                List ffVector = (List) productFundUpdates.get(compFundUpdKey);

                EDITBigDecimal newEndingDollarBalance = (EDITBigDecimal) ffVector.get(0);
                EDITBigDecimal newEndingUnitBalance = (EDITBigDecimal) ffVector.get(1);
                EDITBigDecimal newEndingUnitBalanceForControlBalance = (EDITBigDecimal) ffVector.get(2);

                if (!transactionType.equalsIgnoreCase("M&EP") && !transactionType.equalsIgnoreCase("IMFP"))
                {
                    newEndingDollarBalance = Util.roundToNearestCent(newEndingDollarBalance.addEditBigDecimal(rsUnits.multiplyEditBigDecimal(unitValue)));
                    newEndingUnitBalance = newEndingUnitBalance.addEditBigDecimal(rsUnits);
                    newEndingUnitBalanceForControlBalance = newEndingUnitBalanceForControlBalance.addEditBigDecimal(rsUnits);
                }

                ffVector.add(0, newEndingDollarBalance);
                ffVector.add(1, Util.roundUnits(newEndingUnitBalance, 3));
                ffVector.add(2, newEndingUnitBalanceForControlBalance);
            }
            else
            {
                List ffVector = new ArrayList();

                EDITBigDecimal newEndingDollarBalance = new EDITBigDecimal(cntlsAndBalancesTotalsVO.getBeginningDollarBalance());
                EDITBigDecimal newEndingUnitBalance = new EDITBigDecimal(cntlsAndBalancesTotalsVO.getBeginningUnitBalance());
                EDITBigDecimal newEndingUnitBalanceForControlBalance = new EDITBigDecimal(cntlsAndBalancesTotalsVO.getBeginningUnitBalance());

                if (!transactionType.equalsIgnoreCase("M&EP") && !transactionType.equalsIgnoreCase("IMFP"))
                {
                    newEndingDollarBalance = Util.roundToNearestCent(endingDollarBalance.addEditBigDecimal(rsUnits.multiplyEditBigDecimal(unitValue)));
                    newEndingUnitBalance = endingUnitBalance.addEditBigDecimal(rsUnits);
                    newEndingUnitBalanceForControlBalance = endingUnitBalance.addEditBigDecimal(rsUnits);
                }

                ffVector.add(0, newEndingDollarBalance);
                ffVector.add(1, Util.roundUnits(newEndingUnitBalance, 3));
                ffVector.add(2, newEndingUnitBalanceForControlBalance);

                productFundUpdates.put(compFundUpdKey, ffVector);
            }
        }

        if (cbdKey != null)
        {
            String controlBalanceDetailKey = clientOrRealFundNumber + "|" + cbdKey ;

            ControlsAndBalancesTotalsVO controlsAndBalancesTotalsVO = null;

            if (this.controlBalanceDetails.containsKey(controlBalanceDetailKey))
            {
                Map totalsByTransactionType = (Map) this.controlBalanceDetails.get(controlBalanceDetailKey);

                if (totalsByTransactionType.containsKey(transactionType))
                {
                    controlsAndBalancesTotalsVO = (ControlsAndBalancesTotalsVO) totalsByTransactionType.get(controlBalanceDetailKey);

                    EDITBigDecimal totalDollars = new EDITBigDecimal(controlsAndBalancesTotalsVO.getTotalDollars());
                    totalDollars = Util.roundToNearestCent(totalDollars.addEditBigDecimal(rsDollars));
                    controlsAndBalancesTotalsVO.setTotalDollars(totalDollars.getBigDecimal());

                    if (!transactionType.equals(TRANSACTION_TYPE_MANDEP) && !transactionType.equals(TRANSACTION_TYPE_IMFP))
                    {
                        EDITBigDecimal totalUnits = new EDITBigDecimal(controlsAndBalancesTotalsVO.getTotalUnits());
                        totalUnits = totalUnits.addEditBigDecimal(rsUnits);
                        controlsAndBalancesTotalsVO.setTotalUnits(totalUnits.getBigDecimal());
                    }

                    if (transactionType.equals(TRANSACTION_TYPE_MANDEP) || transactionType.equals(TRANSACTION_TYPE_IMFP) || transactionType.equals(TRANSACTION_TYPE_ADMT))
                    {
                        if (feeType.equals(Fee.ME_FEE_TYPE))
                        {
                            EDITBigDecimal totalAccruedMAndE = new EDITBigDecimal(controlsAndBalancesTotalsVO.getTotalCashMAndE());
                            controlsAndBalancesTotalsVO.setTotalAccruedMAndE(Util.roundToNearestCent(totalAccruedMAndE.addEditBigDecimal(rsDollars)).getBigDecimal());
                        }
                        else if (feeType.equals(Fee.ADVISORY_FEE_TYPE))
                        {
                            EDITBigDecimal totalAccruedAdvisoryFees = new EDITBigDecimal(controlsAndBalancesTotalsVO.getTotalAccruedAdvisoryFees());
                            controlsAndBalancesTotalsVO.setTotalAccruedAdvisoryFees(Util.roundToNearestCent(totalAccruedAdvisoryFees.addEditBigDecimal(rsDollars)).getBigDecimal());
                        }
                        else if (feeType.equals(Fee.MANAGEMENT_FEE_TYPE))
                        {
                            EDITBigDecimal totalAccruedMgtFees = new EDITBigDecimal(controlsAndBalancesTotalsVO.getTotalCashMgmtFees());
                            controlsAndBalancesTotalsVO.setTotalAccruedMgtFees(Util.roundToNearestCent(totalAccruedMgtFees.addEditBigDecimal(rsDollars)).getBigDecimal());
                        }
                        else if (feeType.equals(Fee.RVP_FEE_TYPE))
                        {
                            EDITBigDecimal totalAccruedRVPFees = new EDITBigDecimal(controlsAndBalancesTotalsVO.getTotalAccruedRVPFees());
                            controlsAndBalancesTotalsVO.setTotalAccruedRVPFees(Util.roundToNearestCent(totalAccruedRVPFees.addEditBigDecimal(rsDollars)).getBigDecimal());
                        }
                        else if (feeType.equals(Fee.TRANSFER_MORT_RESERVE_FEE_TYPE))
                        {
                            EDITBigDecimal totalAccruedContribToMortRsv = new EDITBigDecimal(controlsAndBalancesTotalsVO.getTotalAccruedContribToMortRSV());
                            controlsAndBalancesTotalsVO.setTotalAccruedContribToMortRSV(Util.roundToNearestCent(totalAccruedContribToMortRsv.addEditBigDecimal(rsDollars)).getBigDecimal());
                        }
                    }
                }
                else
                {
                    controlsAndBalancesTotalsVO = createControlsAndBalancesTotalsVO(clientOrRealFundNumber,
                            transactionType,
                            feeType,
                            "T",
                            rsDollars,
                            rsUnits);

                    totalsByTransactionType.put(transactionType, controlsAndBalancesTotalsVO);
                }
            }
            else
            {
                controlsAndBalancesTotalsVO = createControlsAndBalancesTotalsVO(clientOrRealFundNumber,
                        transactionType,
                        feeType,
                        "T",
                        rsDollars,
                        rsUnits);

                Map totalsByTransactionType = new HashMap();
                totalsByTransactionType.put(transactionType, controlsAndBalancesTotalsVO);

                this.controlBalanceDetails.put(controlBalanceDetailKey, totalsByTransactionType);
            }
        }

        return totalsByFund;
    }

    /**
     * Subtracts dollars and units retrieved in getActivityForFund
     * method to existing dollar/unit totals by
     * product
     * @param cffsVO
     * @param controlBalanceVO
     * @param transactionType
     * @param rsDollars
     * @param rsUnits
     * @param cycleDate
     * @return
     * @throws Exception
     */
    private Map subtractUnitsDollars(ProductFilteredFundStructureVO cffsVO,
                                     ControlBalanceVO controlBalanceVO,
                                     String transactionType,
                                     EDITBigDecimal rsDollars,
                                     EDITBigDecimal rsUnits,
                                     String cycleDate,
                                     long chargeCodeFK,
                                     String cbdKey,
                                     String feeType) throws Exception
    {
        FilteredFundVO ffVO = (FilteredFundVO) cffsVO.getParentVO(FilteredFundVO.class);

        EDITBigDecimal unitValue = new EDITBigDecimal();

        long filteredFundPK = ffVO.getFilteredFundPK();
        String pricingDirection = ffVO.getPricingDirection();

        if (!rsUnits.isEQ("0"))
        {
            UnitValuesVO[] unitValuesVO = engineLookup.getUnitValuesByFilteredFundIdDateChargeCode(filteredFundPK,
                    cycleDate,
                    pricingDirection,
                    chargeCodeFK);
            if (unitValuesVO != null)
            {
                unitValue = new EDITBigDecimal(unitValuesVO[0].getUnitValue());
            }
        }

        String fundNumber = ffVO.getFundNumber();

        EDITBigDecimal endingDollarBalance = new EDITBigDecimal(controlBalanceVO.getEndingDollarBalance());
        EDITBigDecimal endingUnitBalance = new EDITBigDecimal(controlBalanceVO.getEndingUnitBalance());
        String endingBalCycleDate = controlBalanceVO.getEndingBalanceCycleDate();

        String clientOrRealFundNumber = convertToClientFund(fundNumber, chargeCodeFK);

        String totsByFundKey = clientOrRealFundNumber + "|" + transactionType;

        if (totalsByFund.containsKey(totsByFundKey))
        {
            ControlsAndBalancesTotalsVO cntlsAndBalancesTotalsVO = (ControlsAndBalancesTotalsVO) totalsByFund.get(totsByFundKey);

            EDITBigDecimal totalDollars = new EDITBigDecimal(cntlsAndBalancesTotalsVO.getTotalDollars());
            EDITBigDecimal totalUnits   = new EDITBigDecimal(cntlsAndBalancesTotalsVO.getTotalUnits());
            EDITBigDecimal beginningDollarBalance = new EDITBigDecimal(cntlsAndBalancesTotalsVO.getBeginningDollarBalance());
            EDITBigDecimal beginningUnitBalance = new EDITBigDecimal(cntlsAndBalancesTotalsVO.getBeginningUnitBalance());

            totalDollars = totalDollars.subtractEditBigDecimal(rsDollars);
            totalUnits = totalUnits.subtractEditBigDecimal(rsUnits);

            beginningDollarBalance = beginningDollarBalance.addEditBigDecimal(endingDollarBalance);
            beginningUnitBalance = beginningUnitBalance.addEditBigDecimal(endingUnitBalance);

            cntlsAndBalancesTotalsVO.setTotalDollars(totalDollars.getBigDecimal());
            cntlsAndBalancesTotalsVO.setTotalUnits(totalUnits.getBigDecimal());

            totalsByFund.put(totsByFundKey, cntlsAndBalancesTotalsVO);

            String compFundUpdKey = cffsVO.getProductFilteredFundStructurePK() + "|" + chargeCodeFK;
            if (productFundUpdates.containsKey(compFundUpdKey))
            {
                List ffVector = (List) productFundUpdates.get(compFundUpdKey);

                EDITBigDecimal newEndingDollarBalance = (EDITBigDecimal) ffVector.get(0);
                EDITBigDecimal newEndingUnitBalance = (EDITBigDecimal) ffVector.get(1);
                EDITBigDecimal newEndingUnitBalanceForControlBalance = (EDITBigDecimal) ffVector.get(2);

                if (!transactionType.equalsIgnoreCase("M&EP") && !transactionType.equalsIgnoreCase("IMFP"))
                {
                    newEndingDollarBalance = Util.roundToNearestCent(newEndingDollarBalance.subtractEditBigDecimal(rsUnits.multiplyEditBigDecimal(unitValue)));
                    newEndingUnitBalance = newEndingUnitBalance.subtractEditBigDecimal(rsUnits);
                    newEndingUnitBalanceForControlBalance = newEndingUnitBalanceForControlBalance.subtractEditBigDecimal(rsUnits);
                }

                ffVector.add(0, newEndingDollarBalance);
                ffVector.add(1, Util.roundUnits(newEndingUnitBalance, 3));
                ffVector.add(2, newEndingUnitBalanceForControlBalance);
            }
            else
            {
                List ffVector = new ArrayList();

                EDITBigDecimal newEndingDollarBalance = new EDITBigDecimal(cntlsAndBalancesTotalsVO.getBeginningDollarBalance());
                EDITBigDecimal newEndingUnitBalance = new EDITBigDecimal(cntlsAndBalancesTotalsVO.getBeginningUnitBalance());
                EDITBigDecimal newEndingUnitBalanceForControlBalance = new EDITBigDecimal(cntlsAndBalancesTotalsVO.getBeginningUnitBalance());

                if (!transactionType.equalsIgnoreCase("M&EP") && !transactionType.equalsIgnoreCase("IMFP"))
                {
                    newEndingDollarBalance = Util.roundToNearestCent(endingDollarBalance.subtractEditBigDecimal(rsUnits.multiplyEditBigDecimal(unitValue)));
                    newEndingUnitBalance = endingUnitBalance.subtractEditBigDecimal(rsUnits);
                    newEndingUnitBalanceForControlBalance = endingUnitBalance.subtractEditBigDecimal(rsUnits);
                }

                ffVector.add(0, newEndingDollarBalance);
                ffVector.add(1, Util.roundUnits(newEndingUnitBalance, 3));
                ffVector.add(2, newEndingUnitBalanceForControlBalance);

                productFundUpdates.put(compFundUpdKey, ffVector);
            }
        }
        else
        {
            ControlsAndBalancesTotalsVO cntlsAndBalancesTotalsVO = new ControlsAndBalancesTotalsVO();

            cntlsAndBalancesTotalsVO.setTrxTypeDesc(transactionType);
            cntlsAndBalancesTotalsVO.setFundNumber(fundNumber);

            cntlsAndBalancesTotalsVO.setBeginningDollarBalance(endingDollarBalance.getBigDecimal());
            cntlsAndBalancesTotalsVO.setBeginningUnitBalance(endingUnitBalance.getBigDecimal());

            cntlsAndBalancesTotalsVO.setBeginningBalanceCycleDate(endingBalCycleDate);

            cntlsAndBalancesTotalsVO.setTotalDollars(Util.roundToNearestCent(rsDollars).negate().getBigDecimal());
            cntlsAndBalancesTotalsVO.setTotalUnits(new EDITBigDecimal(rsUnits.getBigDecimal()).negate().getBigDecimal());

            totalsByFund.put(totsByFundKey, cntlsAndBalancesTotalsVO);

            String compFundUpdKey = cffsVO.getProductFilteredFundStructurePK() + "|" + chargeCodeFK;
            if (productFundUpdates.containsKey(compFundUpdKey))
            {
                List ffVector = (List) productFundUpdates.get(compFundUpdKey);

                EDITBigDecimal newEndingDollarBalance = (EDITBigDecimal) ffVector.get(0);
                EDITBigDecimal newEndingUnitBalance = (EDITBigDecimal) ffVector.get(1);
                EDITBigDecimal newEndingUnitBalanceForControlBalance = (EDITBigDecimal) ffVector.get(2);

                if (!transactionType.equalsIgnoreCase("M&EP") && !transactionType.equalsIgnoreCase("IMFP"))
                {
                    newEndingDollarBalance = Util.roundToNearestCent(newEndingDollarBalance.subtractEditBigDecimal(rsUnits.multiplyEditBigDecimal(unitValue)));
                    newEndingUnitBalance = newEndingUnitBalance.subtractEditBigDecimal(rsUnits);
                    newEndingUnitBalanceForControlBalance = newEndingUnitBalanceForControlBalance.subtractEditBigDecimal(rsUnits);
                }

                ffVector.add(0, newEndingDollarBalance);
                ffVector.add(1, Util.roundUnits(newEndingUnitBalance, 3));
                ffVector.add(2, newEndingUnitBalanceForControlBalance);
            }
            else
            {
                List ffVector = new ArrayList();

                EDITBigDecimal newEndingDollarBalance = new EDITBigDecimal(cntlsAndBalancesTotalsVO.getBeginningDollarBalance());
                EDITBigDecimal newEndingUnitBalance = new EDITBigDecimal(cntlsAndBalancesTotalsVO.getBeginningUnitBalance());
                EDITBigDecimal newEndingUnitBalanceForControlBalance = new EDITBigDecimal(cntlsAndBalancesTotalsVO.getBeginningUnitBalance());

                if (!transactionType.equalsIgnoreCase("M&EP") && !transactionType.equalsIgnoreCase("IMFP"))
                {
                    newEndingDollarBalance = Util.roundToNearestCent(endingDollarBalance.subtractEditBigDecimal(rsUnits.multiplyEditBigDecimal(unitValue)));
                    newEndingUnitBalance = endingUnitBalance.subtractEditBigDecimal(rsUnits);
                    newEndingUnitBalanceForControlBalance = endingUnitBalance.subtractEditBigDecimal(rsUnits);
                }

                ffVector.add(0, newEndingDollarBalance);
                ffVector.add(1, Util.roundUnits(newEndingUnitBalance, 3));
                ffVector.add(2, newEndingUnitBalanceForControlBalance);

                productFundUpdates.put(compFundUpdKey, ffVector);
            }
        }

        String controlBalanceDetailKey = clientOrRealFundNumber + "|" + cbdKey;

        ControlsAndBalancesTotalsVO controlsAndBalancesTotalsVO = null;

        if (this.controlBalanceDetails.containsKey(controlBalanceDetailKey))
        {
            Map totalsByTransactionType = (Map) this.controlBalanceDetails.get(controlBalanceDetailKey);

            if (totalsByTransactionType.containsKey(transactionType))
            {
                controlsAndBalancesTotalsVO = (ControlsAndBalancesTotalsVO) totalsByTransactionType.get(transactionType);

                EDITBigDecimal totalDollars = new EDITBigDecimal(controlsAndBalancesTotalsVO.getTotalDollars());
                totalDollars = Util.roundToNearestCent(totalDollars.subtractEditBigDecimal(rsDollars));
                controlsAndBalancesTotalsVO.setTotalDollars(totalDollars.getBigDecimal());

                if (!transactionType.equals(TRANSACTION_TYPE_MANDEP) && !transactionType.equals(TRANSACTION_TYPE_IMFP))
                {
                    EDITBigDecimal totalUnits = new EDITBigDecimal(controlsAndBalancesTotalsVO.getTotalUnits());
                    totalUnits = totalUnits.subtractEditBigDecimal(rsUnits);
                    controlsAndBalancesTotalsVO.setTotalUnits(totalUnits.getBigDecimal());
                }

                if (transactionType.equals(TRANSACTION_TYPE_MANDEP) || transactionType.equals(TRANSACTION_TYPE_IMFP) || transactionType.equals(TRANSACTION_TYPE_ADMT))
                {
                    if (feeType.equals(Fee.ME_FEE_TYPE))
                    {
                        EDITBigDecimal totalAccruedMAndE = new EDITBigDecimal(controlsAndBalancesTotalsVO.getTotalAccruedMAndE());
                        controlsAndBalancesTotalsVO.setTotalAccruedMAndE(Util.roundToNearestCent(totalAccruedMAndE.subtractEditBigDecimal(rsDollars)).getBigDecimal());
                    }
                    else if (feeType.equals(Fee.ADVISORY_FEE_TYPE))
                    {
                        EDITBigDecimal totalAccruedAdvisoryFees = new EDITBigDecimal(controlsAndBalancesTotalsVO.getTotalAccruedAdvisoryFees());
                        controlsAndBalancesTotalsVO.setTotalAccruedAdvisoryFees(Util.roundToNearestCent(totalAccruedAdvisoryFees.subtractEditBigDecimal(rsDollars)).getBigDecimal());
                    }
                    else if (feeType.equals(Fee.MANAGEMENT_FEE_TYPE))
                    {
                        EDITBigDecimal totalAccruedMgtFees = new EDITBigDecimal(controlsAndBalancesTotalsVO.getTotalAccruedMgtFees());
                        controlsAndBalancesTotalsVO.setTotalAccruedMgtFees(Util.roundToNearestCent(totalAccruedMgtFees.subtractEditBigDecimal(rsDollars)).getBigDecimal());
                    }
                    else if (feeType.equals(Fee.RVP_FEE_TYPE))
                    {
                        EDITBigDecimal totalAccruedRVPFees = new EDITBigDecimal(controlsAndBalancesTotalsVO.getTotalAccruedRVPFees());
                        controlsAndBalancesTotalsVO.setTotalAccruedRVPFees(Util.roundToNearestCent(totalAccruedRVPFees.subtractEditBigDecimal(rsDollars)).getBigDecimal());
                    }
                    else if (feeType.equals(Fee.TRANSFER_MORT_RESERVE_FEE_TYPE))
                    {
                        EDITBigDecimal totalAccruedContribToMortRsv = new EDITBigDecimal(controlsAndBalancesTotalsVO.getTotalAccruedContribToMortRSV());
                        controlsAndBalancesTotalsVO.setTotalAccruedContribToMortRSV(Util.roundToNearestCent(totalAccruedContribToMortRsv.subtractEditBigDecimal(rsDollars)).getBigDecimal());
                    }
                }
            }
            else
            {
                controlsAndBalancesTotalsVO = createControlsAndBalancesTotalsVO(clientOrRealFundNumber,
                        transactionType,
                        feeType,
                        "F",
                        rsDollars,
                        rsUnits);

                totalsByTransactionType.put(transactionType, controlsAndBalancesTotalsVO);
            }
        }
        else
        {
            controlsAndBalancesTotalsVO = createControlsAndBalancesTotalsVO(clientOrRealFundNumber,
                    transactionType,
                    feeType,
                    "F",
                    rsDollars,
                    rsUnits);

            Map totalsByTransactionType = new HashMap();
            totalsByTransactionType.put(transactionType, controlsAndBalancesTotalsVO);

            this.controlBalanceDetails.put(controlBalanceDetailKey, totalsByTransactionType);
        }

        return totalsByFund;
    }

    private void generateOutputLinesAndUpdateBalances(String cycleDate, boolean generateActivityFile)
    {
        File exportFile = getExportFile(cycleDate);
        try
        {
            if (generateActivityFile)
            {
                // these will add the lines appropriately to the
                // Map outputLines.
                generateHeaderLineInMemory(cycleDate);
                int recordCount = generateDetailLinesInMemory(cycleDate);
                generateTrailerRecordInMemory(recordCount);

                // Read the Map of outoutLines in sorted key order
                // and write the lines to the file
                sortAndWriteMemoryLinesToFile(exportFile);
            }

             updateProductFundBalances(cycleDate);
        }
        catch (Exception e)
        {
            System.out.println(e);
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * Reads the Map of fund keys in sorted order and
     * it writes the stored lines to the exportFile.
     * @param exportFile
     * @throws IOException
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

    private void generateHeaderLineInMemory(String cycleDate)
    {
        StringBuilder fileData = new StringBuilder();
        fileData.append("HEADER");
        fileData.append("   ");
        String cycleYear = cycleDate.substring(0, 4);
        String cycleMonth = cycleDate.substring(5, 7);
        String cycleDay = cycleDate.substring(8);
        fileData.append(cycleMonth + cycleDay + cycleYear);
        fileData.append("                             ");
        fileData.append("ICMG to ITT Hartford Activity Feed");
        appendOutputLineInMemory(fileData.toString());
    }

    private int generateDetailLinesInMemory(String cycleDate) throws Exception
    {
        List voInclusionList = new ArrayList();
        voInclusionList.add(FilteredFundVO.class);
        int recordCount = 1;
         Set fundUpdatesKeySet = productFundUpdates.keySet();
        Iterator it = fundUpdatesKeySet.iterator();

        while (it.hasNext())
        {
            String compFundUpdKey = (String) it.next();
             ProductFundUpdatesKeyHelper helper = new ProductFundUpdatesKeyHelper(compFundUpdKey);
             long productFilteredFundStructurePK = helper.getProductFilteredFundStructureFK();
            long chargeCodeFK = helper.getChargeCodeFK();

             ProductFilteredFundStructureVO cffsVO =
                     engineLookup.composeProductFilteredFundStructureByPK(productFilteredFundStructurePK, voInclusionList);

            FilteredFundVO filteredFundVO = (FilteredFundVO) cffsVO.getParentVO(FilteredFundVO.class);
            long filteredFundFK = filteredFundVO.getFilteredFundPK();
            String pricingDirection = filteredFundVO.getPricingDirection();

            UnitValuesVO[] unitValuesVO =
                    engineLookup.getUnitValuesByFilteredFundIdDateChargeCode(filteredFundFK,
                            cycleDate,
                            pricingDirection,
                            chargeCodeFK);

            if (unitValuesVO != null)
            {
                EDITBigDecimal price = new EDITBigDecimal(unitValuesVO[0].getUnitValue());
                 List fundUpdate = (List) productFundUpdates.get(compFundUpdKey);

                String clientFundOrRealFundNumber =
                        convertToClientFund(filteredFundVO.getFundNumber(),
                                chargeCodeFK);

                EDITBigDecimal netUnitActivity = addFundHeaderInfoRecord(
                        clientFundOrRealFundNumber,
                        price,
                        cffsVO,
                        fundUpdate,
                        chargeCodeFK);
                recordCount += 1;

                recordCount = addTransactionDetailRecords(clientFundOrRealFundNumber,
                        price,
                        netUnitActivity,
                        recordCount);
            }
        }

        return recordCount;
    }


    private EDITBigDecimal addFundHeaderInfoRecord(String clientOrRealFundNumber,
                                                   EDITBigDecimal price,
                                         ProductFilteredFundStructureVO cffsVO,
                                                   List fundUpdate,
                                                   long chargeCodeFK)
    {
        StringBuilder fileData = new  StringBuilder();
        fileData.append("\n");

        int fieldLen = clientOrRealFundNumber.length();
        clientOrRealFundNumber = truncateValueIfNeeded(clientOrRealFundNumber, 4, fieldLen);
        fileData.append(clientOrRealFundNumber);
        fileData = addSpacesIfNeeded(fileData, 4, fieldLen);

        fileData.append("HINF");

        int leftOfDecimalLength = 0;
        int rightOfDecimalLength = 0;

        String priceString = Util.roundUnits(price, 7).toString();
        leftOfDecimalLength = 3;
        rightOfDecimalLength = 7;
        priceString = setStringToCorrectLength(priceString, leftOfDecimalLength, rightOfDecimalLength);
        fileData.append(priceString);
        fileData.append(" ");

        ControlBalanceVO controlBalanceVO = engineLookup.findLastControlBalanceVO(cffsVO.getProductFilteredFundStructurePK(), chargeCodeFK);

        String beginningUnitBalanceString = "           0.000";
        String netUnitActivityString = "           0.000";
        EDITBigDecimal netUnitActivity = new EDITBigDecimal();
        EDITBigDecimal endingUnitBalance = (EDITBigDecimal) fundUpdate.get(1);
        String endingUnitBalanceString = Util.roundUnits(endingUnitBalance, 3).toString();

        if (controlBalanceVO != null)
        {
            beginningUnitBalanceString = Util.roundUnits(controlBalanceVO.getEndingUnitBalance(), 3).toString();
            netUnitActivity = endingUnitBalance.subtractEditBigDecimal(controlBalanceVO.getEndingUnitBalance());
        }
        else
        {
            netUnitActivity = endingUnitBalance;
        }

        netUnitActivityString = Util.roundUnits(netUnitActivity, 3).toString();

        leftOfDecimalLength = 12;
        rightOfDecimalLength = 3;
        beginningUnitBalanceString = setStringToCorrectLength(beginningUnitBalanceString, leftOfDecimalLength, rightOfDecimalLength);
        fileData.append(beginningUnitBalanceString);
        fileData.append(" ");

        endingUnitBalanceString = setStringToCorrectLength(endingUnitBalanceString, leftOfDecimalLength, rightOfDecimalLength);
        fileData.append(endingUnitBalanceString);

        netUnitActivityString = setStringToCorrectLength(netUnitActivityString, leftOfDecimalLength, rightOfDecimalLength);
        fileData.append(netUnitActivityString);

        fileData.append("           ");
        appendOutputLineInMemory(fileData.toString());

        return netUnitActivity;
    }

    private String setStringToCorrectLength(String string, int leftOfDecimalLength, int rightOfDecimalLength)
    {
        int decimalPointIndex = string.indexOf(".");
        String leftOfDecimal = string.substring(0, decimalPointIndex);
        String rightOfDecimal = string.substring(decimalPointIndex + 1);

        int lengthDiff = 0;

        if (leftOfDecimal.length() < leftOfDecimalLength)
        {
            lengthDiff = leftOfDecimalLength - leftOfDecimal.length();
            for (int i = 0; i < lengthDiff; i++)
            {
                leftOfDecimal = " " + leftOfDecimal;
            }
        }

        if (rightOfDecimal.length() < rightOfDecimalLength)
        {
            lengthDiff = rightOfDecimalLength - rightOfDecimal.length();
            for (int i = 0; i < lengthDiff; i++)
            {
                rightOfDecimal = rightOfDecimal + " ";
            }
        }

        string = leftOfDecimal + "." + rightOfDecimal;

        return string;
    }

    private void generateTrailerRecordInMemory(int recordCount)
    {
        StringBuilder fileData = new StringBuilder();
        fileData.append("\n");
        fileData.append("TRAILER");
        fileData.append("  ");
        recordCount += 1;
        int fieldLen = (recordCount + "").length();
        fileData = addSpacesIfNeeded(fileData, 5, fieldLen);
        fileData.append(recordCount);
        fileData.append("                                ");
        fileData.append("ICMG to ITT Hartford Activity Feed");

        appendOutputLineInMemory(fileData.toString());
    }

    private int addTransactionDetailRecords(String clientOrRealFundNumber,
                                            EDITBigDecimal unitValuePrice,
                                            EDITBigDecimal netUnitActivity,
                                            int recordCount)
    {
        boolean generateTINFRecord = false;
        EDITBigDecimal trxTotal = new EDITBigDecimal();
        EDITBigDecimal unitTrxTotal = new EDITBigDecimal();
        EDITBigDecimal gainLoss = new EDITBigDecimal();
        Set keys = null;
        Iterator it = null;
        String trimmedFundNumber = null;
        int leftOfDecimalLength = 0;
        int rightOfDecimalLength = 0;

        for (int i = 0; i < ACTIVITIES.length; i++)
        {
            keys = totalsByFund.keySet();
            it = keys.iterator();

            while (it.hasNext())
            {
                String totsByFundKey = (String) it.next();

                TotalsByFundKeyHelper helper = new TotalsByFundKeyHelper(totsByFundKey);
                String keyTranType = helper.getTransactionType();
                if (keyTranType.equalsIgnoreCase(ACTIVITIES[i]))
                {
                    String keyFund = helper.getFundNumberK();

                    String key = keyFund + keyTranType;
                    // The initial code stored the key directly appending
                    // Reconstruct it here since the logic is built around that.

                    if (key.startsWith(clientOrRealFundNumber) && !key.endsWith("MD"))
                    {
                        if (!key.endsWith("IMFP") &&
                                !key.endsWith("M&EP"))
                        {
                            generateTINFRecord = true;
                            // IF AT LEAST ONE, GENERATE A TINF
                        }

                        ControlsAndBalancesTotalsVO cAndBTotalsVO =
                                (ControlsAndBalancesTotalsVO)
                                totalsByFund.get(totsByFundKey);

                        EDITBigDecimal trxAmount = new EDITBigDecimal(cAndBTotalsVO.getTotalDollars());
                        //                EDITBigDecimal trxUnits = new EDITBigDecimal(cAndBTotalsVO.getTotalUnits());

                        // DON'T INCLUDE THE M&EP OR THE IMFP IN THE TINF TOTAL BELOW
                        if (!key.endsWith("IMFP") &&
                                !key.endsWith("M&EP"))
                        {
                            trxTotal = Util.roundToNearestCent(trxTotal.addEditBigDecimal(trxAmount));
                        }

                        //                unitTrxTotal = unitTrxTotal.addEditBigDecimal(trxUnits);
                        if (((!key.endsWith("IMFP") && !key.endsWith("M&EP")) &&
                                (trxAmount.isGT(EDITBigDecimal.EDIT_DECIMAL_ZILCH_STR) ||
                                trxAmount.isLT(EDITBigDecimal.EDIT_DECIMAL_ZILCH_STR))) ||
                                (key.endsWith("IMFP") || key.endsWith("M&EP")))
                        {
                            StringBuilder fileData = new StringBuilder();
                            fileData.append("\n");

                            int fieldLen = clientOrRealFundNumber.length();
                            trimmedFundNumber = truncateValueIfNeeded(clientOrRealFundNumber, 4, fieldLen);
                            fileData.append(trimmedFundNumber);
                            fileData = addSpacesIfNeeded(fileData, 4, fieldLen);
                            fileData.append(key.substring(fieldLen));

                            String trxAmtString = Util.addOnTrailingZero(Util.roundToNearestCent(trxAmount).toString());
                            leftOfDecimalLength = 12;
                            rightOfDecimalLength = 2;
                            trxAmtString = setStringToCorrectLength(trxAmtString, leftOfDecimalLength, rightOfDecimalLength);
                            fileData.append(trxAmtString);
                            fileData.append("                                                         ");

                            appendOutputLineInMemory(fileData.toString());
                            recordCount += 1;
                        }
                    }
                }
            }
        }

        unitTrxTotal = netUnitActivity.multiplyEditBigDecimal(unitValuePrice);
        gainLoss = trxTotal.subtractEditBigDecimal(unitTrxTotal);
        gainLoss = gainLoss.round(2);

        if (generateTINFRecord)
        {
            StringBuilder fileData = new StringBuilder();
            fileData.append("\n");
            int fieldLen = clientOrRealFundNumber.length();
            trimmedFundNumber = truncateValueIfNeeded(clientOrRealFundNumber, 4, fieldLen);
            fileData.append(trimmedFundNumber);
            fileData = addSpacesIfNeeded(fileData, 4, fieldLen);

            fileData.append("TINF");

            String trxTotalAmtString = Util.addOnTrailingZero(Util.roundToNearestCent(trxTotal).toString());
            leftOfDecimalLength = 12;
            rightOfDecimalLength = 2;
            trxTotalAmtString = setStringToCorrectLength(trxTotalAmtString, leftOfDecimalLength, rightOfDecimalLength);
            fileData.append(trxTotalAmtString);
            fileData.append(" ");

            String outputUnitTrxTotal = Util.addOnTrailingZero(Util.roundToNearestCent(unitTrxTotal).toString());
            leftOfDecimalLength = 12;
            rightOfDecimalLength = 2;
            outputUnitTrxTotal = setStringToCorrectLength(outputUnitTrxTotal, leftOfDecimalLength, rightOfDecimalLength);
            fileData.append(outputUnitTrxTotal);
            fileData.append(" ");


            String gainLossString = Util.addOnTrailingZero(gainLoss + "");
            leftOfDecimalLength = 8;
            rightOfDecimalLength = 2;
            gainLossString = setStringToCorrectLength(gainLossString, leftOfDecimalLength, rightOfDecimalLength);
            fileData.append(gainLossString);

            fileData.append("                             ");

            appendOutputLineInMemory(fileData.toString());
            recordCount += 1;
        }

        return recordCount;
    }

    /**
     * Append the line into its slot in a Map so we can sort them when
     * we're done and then write them out.
     * @param data
     */
    private void appendOutputLineInMemory(String data)
    {

        String key = getKeyForLine(data, 4);

        if (this.outputLines.containsKey(key))
        {

            List linesForFund = (List) this.outputLines.get(key);

            linesForFund.add(data);
        }
        else
        {

            List linesForFund = new ArrayList();

            linesForFund.add(data);

            this.outputLines.put(key, linesForFund);
        }
    }

    private static String getKeyForLine(String data, int keysize)
    {

        String lineWithoutFeed = data;

        if (lineWithoutFeed.startsWith("\n"))
        {
            lineWithoutFeed = data.substring(1, data.length());
        }
        else if (lineWithoutFeed.startsWith("\r\n"))
        {
            lineWithoutFeed = data.substring(2, data.length());
        }

        String prefix =
                lineWithoutFeed.substring(
                        0, Math.min(keysize, lineWithoutFeed.length()));

        if ("HEAD".equalsIgnoreCase(prefix))    // the one header record
        {
            return HEADER_KEY;
        }
        else if ("TRAI".equalsIgnoreCase(prefix))   // the one trailer record
        {
            return TRAILER_KEY;
        }
        else
        {
            return prefix;   // e.g. 8802
        }
    }

    // Note: Whenever a new transaction is added ... make sure that new transaction is added to the
    // contract SQL (Method Name: executeContractSQL) .If we do not filter the transactions in the 'IN' clause
    // we are unnecessarily bringing in the transactions that we don' need
    // If we add ne trxType that we are interested in please add the new trxType in the following statement
    // " AND " + transactionTypeCTCol + "IN ('PY', 'TF', 'STF', 'FT', 'TU', 'HFTA', 'HFTP', 'HFSA', 'HFSP', 'ML', 'LO', 'LR', 'WI', 'FS', 'SRO', 'NT', 'LS', 'MD')"
    // This has to be done in the SQL that is used to get DISTINCT EDITTrxHistoryPKs SQL.
    private String setTransactionType(String trxType, String feeType)
    {
        // Fund level info
        if (trxType.equalsIgnoreCase("DFACC"))
        {
            if (feeType.equalsIgnoreCase("ManagementFee"))
            {
                trxType = "IMFP";  // Management Fee
            }
            else
            {
                trxType = "M&EP";  // M&E Payable
            }
        }
        else if (trxType.equalsIgnoreCase("DPURCH"))
        {
            trxType = "ADMT";  // Advance Transfer
        }
        else if (trxType.equalsIgnoreCase("DFCASH"))
        {
            trxType = "DFCASH"; // Trx Type stays the same (DFCASH only used to update control balance table)
        }
        else if (trxType.equalsIgnoreCase("DFOFF"))
        {
            trxType = "DFOFF"; // For this trxType all FeeTypes are 'Transfer'
        }
        // rest are contract level
        else if (trxType.equalsIgnoreCase("PY"))
        {
            trxType = "PREM";
        }
        else if (trxType.equalsIgnoreCase("TF") ||
                trxType.equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_SERIES_TRANSFER) ||
                trxType.equalsIgnoreCase("FT") ||
                trxType.equalsIgnoreCase("TU") ||
                trxType.equalsIgnoreCase("HFTA") ||
                trxType.equalsIgnoreCase("HFTP") ||
                trxType.equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_HF_SERIES_AMT) ||
                trxType.equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_HF_SERIES_PCT) ||
                trxType.equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_MONTHLY_COLLATERALIZATION) ||
                trxType.equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_LOAN) ||
                trxType.equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_LOAN_REPAYMENT))
        {
            trxType = "RALL";
        }
        else if (trxType.equalsIgnoreCase("WI") ||
                trxType.equalsIgnoreCase("FS") ||
                trxType.equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_SURRENDER_OVERLOAN) ||
                trxType.equalsIgnoreCase("NT"))
        {
            trxType = "SURR";
        }
        else if (trxType.equalsIgnoreCase("LS"))
        {
            trxType = "RDTH";
        }
        else if (trxType.equalsIgnoreCase("BucketAdminCharge"))
        {
            trxType = "ADMF";
        }
        else if (trxType.equalsIgnoreCase("BucketCoi"))
        {
            trxType = "COIN";
        }
        else
        {
            trxType = "IGNR";
        }

        return trxType;
    }

    private StringBuilder addSpacesIfNeeded(StringBuilder fileData, int fieldLen, int valueLen)
    {
        if (valueLen < fieldLen)
        {
            for (int i = 0; i < fieldLen - valueLen; i++)
            {
                fileData.append(" ");
            }
        }

        return fileData;
    }

    private String truncateValueIfNeeded(String value, int fieldLen, int valueLen)
    {
        if (valueLen > fieldLen)
        {
            value = value.substring(0, fieldLen);
        }

        return value;
    }

    /**
     *  Updates the ending balance information on the ProductFilteredFundStructure table
     * @param cycleDate
     * @throws Exception
     */
    private void updateProductFundBalances(String cycleDate) throws Exception
    {
        Iterator ffUpdatesKeys = this.productFundUpdates.keySet().iterator();

        engine.business.Lookup engineLookupComponent = new engine.component.LookupComponent();

        while(ffUpdatesKeys.hasNext())
        {
            String compFundUpdKey = (String) ffUpdatesKeys.next();

            ProductFundUpdatesKeyHelper helper = new ProductFundUpdatesKeyHelper(compFundUpdKey);
            long chargeCodeFK = helper.getChargeCodeFK();
            long productFilteredFundStructureFK = helper.getProductFilteredFundStructureFK();

            List ffUpdateVector = (List) this.productFundUpdates.get(compFundUpdKey);
            List dfcashUpdateVector = (List) this.dfcashProductFundUpdates.get(compFundUpdKey);

            EDITBigDecimal endingDollarBalance = (EDITBigDecimal) ffUpdateVector.get(0);
            EDITBigDecimal endingUnitBalance = (EDITBigDecimal) ffUpdateVector.get(1);

            ControlBalanceVO previousControlBalanceVO = engineLookupComponent.findLastControlBalanceVO(productFilteredFundStructureFK, chargeCodeFK);

            long previousControlBalancePK = 0;

            if (previousControlBalanceVO != null)
            {
                previousControlBalancePK = previousControlBalanceVO.getControlBalancePK();
            }

            ControlBalanceVO controlBalanceVO = new ControlBalanceVO();
            controlBalanceVO.setControlBalancePK(0);
            controlBalanceVO.setProductFilteredFundStructureFK(productFilteredFundStructureFK);
            controlBalanceVO.setEndingBalanceCycleDate(cycleDate);
            controlBalanceVO.setEndingDollarBalance(endingDollarBalance.getBigDecimal());
            controlBalanceVO.setEndingUnitBalance(Util.roundUnits(endingUnitBalance, 3).getBigDecimal());

            if (chargeCodeFK != 0)
            {
                controlBalanceVO.setChargeCodeFK(chargeCodeFK);
            }

            EDITBigDecimal endingShareBalance = getEndingShareBalance(productFilteredFundStructureFK,
                    chargeCodeFK,
                    new EDITBigDecimal(controlBalanceVO.getEndingDollarBalance()),
                    cycleDate);

            controlBalanceVO.setEndingShareBalance(endingShareBalance.getBigDecimal());

            if (dfcashUpdateVector != null)
            {
                EDITBigDecimal dfcashEndingShareBalance = (EDITBigDecimal) dfcashUpdateVector.get(0);

                controlBalanceVO.setDFCASHEndingShareBalance(dfcashEndingShareBalance.round(10).getBigDecimal());
            }

            engine.business.Calculator calculator = new engine.component.CalculatorComponent();

            calculator.saveVONonRecursive(controlBalanceVO);

            createControlBalanceDetailRecords(controlBalanceVO, cycleDate, previousControlBalancePK);
        }
    }

    /**
     * Insertion of new ControlBalanceDetail records. Each unique combination of AccountingPeriod, EffectiveDate, ValuationDate
     * will have new record for a fund for the activity found between last EndingBalanceCycleDate and CycleDate.
     * @param controlBalanceVO
     * @param cycleDate - Date entered on the job paramter page
     * @param previousControlBalancePK
     * @throws Exception
     */
    private void createControlBalanceDetailRecords(ControlBalanceVO controlBalanceVO,
                                                   String cycleDate,
                                                   long previousControlBalancePK) throws Exception
    {
        long productFilteredFundStructureFK = controlBalanceVO.getProductFilteredFundStructureFK();

        ProductFilteredFundStructureVO fundStructureVO = engineLookup.getByPK(productFilteredFundStructureFK)[0];
        long filteredFundFK = fundStructureVO.getFilteredFundFK();

        FilteredFundVO filteredFundVO = engineLookup.findFilteredFundByPK(filteredFundFK)[0];

        String pricingDirection = filteredFundVO.getPricingDirection();
        long chargeCodeFK = controlBalanceVO.getChargeCodeFK();
        String fundNumber = filteredFundVO.getFundNumber();

        UnitValuesVO[] unitValuesVO = engineLookup.getUnitValuesByFilteredFundIdDateChargeCode(filteredFundFK,
                cycleDate,
                pricingDirection,
                chargeCodeFK);

        EDITBigDecimal unitValue = new EDITBigDecimal();

        EDITBigDecimal nav2 = new EDITBigDecimal();

        if (unitValuesVO != null && unitValuesVO.length > 0)
        {
            unitValue = new EDITBigDecimal(unitValuesVO[0].getUnitValue());
            nav2 = new EDITBigDecimal(unitValuesVO[0].getNetAssetValue2());
        }

        engine.business.Calculator calculatorComponent = new engine.component.CalculatorComponent();

        String clientOrRealFundNumber = convertToClientFund(fundNumber, chargeCodeFK);

        EDITDate endingBalanceCycleDate = new EDITDate(controlBalanceVO.getEndingBalanceCycleDate());

        ControlBalanceDetailVO previousControlBalanceDetailVO = engineLookup.findLatestControlBalanceDetailByControlBalanceFK(previousControlBalancePK);
        
        EDITBigDecimal unitBalance = new EDITBigDecimal();

        EDITBigDecimal shareBalance = new EDITBigDecimal();
        
        if (previousControlBalanceDetailVO != null)
        {
            unitBalance = new EDITBigDecimal(previousControlBalanceDetailVO.getUnitBalance());

            shareBalance = new EDITBigDecimal(previousControlBalanceDetailVO.getShareBalance());
        }

        // If there is no activity create a dummy ControlBalanceDetailRecord.
        if (!containsAtleastOneControlBalanceDetailRecord(clientOrRealFundNumber))
        {
            ControlBalanceDetailVO controlBalanceDetailVO = new ControlBalanceDetailVO();

            String accountingPeriod = endingBalanceCycleDate.getFormattedYearAndMonth();

            controlBalanceDetailVO.setAccountingPeriod(accountingPeriod);

            controlBalanceDetailVO.setEffectiveDate(controlBalanceVO.getEndingBalanceCycleDate());

            controlBalanceDetailVO.setValuationDate(controlBalanceVO.getEndingBalanceCycleDate());

            controlBalanceDetailVO.setUnitBalance(unitBalance.getBigDecimal());

            controlBalanceDetailVO.setShareBalance(shareBalance.getBigDecimal());

            EDITBigDecimal policyOwnerValue = unitBalance.multiplyEditBigDecimal(unitValue);

            controlBalanceDetailVO.setPolicyOwnerValue(policyOwnerValue.getBigDecimal());

            EDITBigDecimal netAssets = shareBalance.multiplyEditBigDecimal(nav2);

            controlBalanceDetailVO.setNetAssets(netAssets.getBigDecimal());

            controlBalanceDetailVO.setControlBalanceFK(controlBalanceVO.getControlBalancePK());

            calculatorComponent.saveVONonRecursive(controlBalanceDetailVO);
        }
        else
        {
            ControlsAndBalancesTotalsVO controlsAndBalancesTotalsVO = null;

            Iterator controlBalanceDetailsKeys = this.controlBalanceDetails.keySet().iterator();

            // To store previous ControlBalanceDetail while looping ... need this to verify if when we need to create dummy record
            ControlBalanceDetailVO lastControlBalanceDetailVO = null;

            // Keep track of max ValuationDate
            String maxValuationDate = null;

            // controlBalanceDetails is TreeMap and the records are ordered by
            // FundNumber, AccountingPeriod, EffectiveDate, ValuationDate
            while (controlBalanceDetailsKeys.hasNext())
            {
                String controlBalanceDetailsKey = (String) controlBalanceDetailsKeys.next();

                String clientOrRealFundNumberFromCBDKey = Util.fastTokenizer(controlBalanceDetailsKey, "|")[0];

                if (clientOrRealFundNumber.equals(clientOrRealFundNumberFromCBDKey))
                {
                    String accountingPeriod = Util.fastTokenizer(controlBalanceDetailsKey, "|")[1];

                    String effectiveDate = Util.fastTokenizer(controlBalanceDetailsKey, "|")[2];

                    String valuationDate = Util.fastTokenizer(controlBalanceDetailsKey, "|")[3];

                    ControlBalanceDetailVO controlBalanceDetailVO = new ControlBalanceDetailVO();

                    controlBalanceDetailVO.setAccountingPeriod(accountingPeriod);

                    controlBalanceDetailVO.setEffectiveDate(effectiveDate);

                    controlBalanceDetailVO.setValuationDate(valuationDate);

                    EDITBigDecimal mAndEPayablesReceivables = new EDITBigDecimal();

                    EDITBigDecimal advisoryFeePayablesReceivables = new EDITBigDecimal();

                    EDITBigDecimal mgtFeePayablesReceivables = new EDITBigDecimal();

                    EDITBigDecimal rvpPayablesReceivables = new EDITBigDecimal();

                    EDITBigDecimal netCash = new EDITBigDecimal();

                    EDITBigDecimal policyActivity = new EDITBigDecimal();

                    EDITBigDecimal unitsPurchased = new EDITBigDecimal();

                    EDITBigDecimal sharesPurchased = new EDITBigDecimal();

                    Map totalsByTransactionType = (Map) this.controlBalanceDetails.get(controlBalanceDetailsKey);

                    Iterator totalsByTransactionTypeKeys = totalsByTransactionType.keySet().iterator();

                    while (totalsByTransactionTypeKeys.hasNext())
                    {
                        String transactionType = (String) totalsByTransactionTypeKeys.next();

                        controlsAndBalancesTotalsVO = (ControlsAndBalancesTotalsVO) totalsByTransactionType.get(transactionType);

                        EDITBigDecimal unitActivity = new EDITBigDecimal(controlsAndBalancesTotalsVO.getTotalUnits());

                        unitsPurchased = unitsPurchased.addEditBigDecimal(unitActivity);

                        // All the accrual fields should be treated the following way (opposite to what we treat the other fees and transaction types)
                        // when ToFromIndicator is 'F' the Dollar Amount is positive
                        // when ToFromIndicator is 'T' the Dollar Amount is negative
                        // For all the accrual fields instead of changing the sign while cumulating, reversing the sign before inserting record.
                        // while cumulating, we are handling the accrual values the same way as other values
                        // when ToFromIndicator is 'F' the Dollar Amount is negative
                        // when ToFromIndicator is 'T' the Dollar Amount is positive

                        if (transactionType.equals(TRANSACTION_TYPE_PREM))
                        {
                            controlBalanceDetailVO.setTotalAccruedNetPremiums(new EDITBigDecimal(controlsAndBalancesTotalsVO.getTotalDollars()).negate().getBigDecimal());
                            policyActivity = policyActivity.addEditBigDecimal(new EDITBigDecimal(controlsAndBalancesTotalsVO.getTotalDollars()));
                        }
                        else if (transactionType.equals(TRANSACTION_TYPE_ADMF))
                        {
                            controlBalanceDetailVO.setTotalAccruedAdminFees(new EDITBigDecimal(controlsAndBalancesTotalsVO.getTotalDollars()).negate().getBigDecimal());
                            policyActivity = policyActivity.addEditBigDecimal(new EDITBigDecimal(controlsAndBalancesTotalsVO.getTotalDollars()));
                        }
                        else if (transactionType.equals(TRANSACTION_TYPE_COIN))
                        {
                            controlBalanceDetailVO.setTotalAccruedCOI(new EDITBigDecimal(controlsAndBalancesTotalsVO.getTotalDollars()).negate().getBigDecimal());
                            policyActivity = policyActivity.addEditBigDecimal(new EDITBigDecimal(controlsAndBalancesTotalsVO.getTotalDollars()));
                        }
                        else if (transactionType.equals(TRANSACTION_TYPE_RALL))
                        {
                            controlBalanceDetailVO.setTotalAccruedReallocations(new EDITBigDecimal(controlsAndBalancesTotalsVO.getTotalDollars()).negate().getBigDecimal());
                            policyActivity = policyActivity.addEditBigDecimal(new EDITBigDecimal(controlsAndBalancesTotalsVO.getTotalDollars()));
                        }
                        else if (transactionType.equals(TRANSACTION_TYPE_RDTH))
                        {
                            controlBalanceDetailVO.setTotalAccruedRRD(new EDITBigDecimal(controlsAndBalancesTotalsVO.getTotalDollars()).negate().getBigDecimal());
                            policyActivity = policyActivity.addEditBigDecimal(new EDITBigDecimal(controlsAndBalancesTotalsVO.getTotalDollars()));
                        }
                        else if (transactionType.equals(TRANSACTION_TYPE_SURR))
                        {
                            controlBalanceDetailVO.setTotalAccruedSurrenders(new EDITBigDecimal(controlsAndBalancesTotalsVO.getTotalDollars()).negate().getBigDecimal());
                            policyActivity = policyActivity.addEditBigDecimal(new EDITBigDecimal(controlsAndBalancesTotalsVO.getTotalDollars()));
                        }
                        else if (transactionType.equals(TRANSACTION_TYPE_MANDEP))
                        {
                            controlBalanceDetailVO.setTotalAccruedMAndE(new EDITBigDecimal(controlsAndBalancesTotalsVO.getTotalAccruedMAndE()).negate().getBigDecimal());
                            mAndEPayablesReceivables = mAndEPayablesReceivables.subtractEditBigDecimal(new EDITBigDecimal(controlsAndBalancesTotalsVO.getTotalAccruedMAndE()));
                            // MAndE should be excluded from PolicyActiviy
                        }
                        else if (transactionType.equals(TRANSACTION_TYPE_IMFP))
                        {
                            controlBalanceDetailVO.setTotalAccruedMgtFees(new EDITBigDecimal(controlsAndBalancesTotalsVO.getTotalAccruedMgtFees()).negate().getBigDecimal());
                            mgtFeePayablesReceivables = mgtFeePayablesReceivables.subtractEditBigDecimal(new EDITBigDecimal(controlsAndBalancesTotalsVO.getTotalAccruedMgtFees()));
                            policyActivity = policyActivity.addEditBigDecimal(new EDITBigDecimal(controlsAndBalancesTotalsVO.getTotalDollars()));
                        }
                        else if (transactionType.equals(TRANSACTION_TYPE_ADMT))
                        {
                            controlBalanceDetailVO.setTotalAccruedAdvanceTransfers(new EDITBigDecimal(controlsAndBalancesTotalsVO.getTotalDollars()).negate().getBigDecimal());
                            controlBalanceDetailVO.setTotalAccruedContribToMortRsv(new EDITBigDecimal(controlsAndBalancesTotalsVO.getTotalAccruedContribToMortRSV()).negate().getBigDecimal());

                            controlBalanceDetailVO.setTotalAccruedAdvisoryFees(new EDITBigDecimal(controlsAndBalancesTotalsVO.getTotalAccruedAdvisoryFees()).negate().getBigDecimal());
                            advisoryFeePayablesReceivables = advisoryFeePayablesReceivables.subtractEditBigDecimal(new EDITBigDecimal(controlsAndBalancesTotalsVO.getTotalAccruedAdvisoryFees()));

                            controlBalanceDetailVO.setTotalAccruedRVPFees(new EDITBigDecimal(controlsAndBalancesTotalsVO.getTotalAccruedRVPFees()).negate().getBigDecimal());
                            rvpPayablesReceivables = rvpPayablesReceivables.subtractEditBigDecimal(new EDITBigDecimal(controlsAndBalancesTotalsVO.getTotalAccruedRVPFees()));

                            policyActivity = policyActivity.addEditBigDecimal(new EDITBigDecimal(controlsAndBalancesTotalsVO.getTotalDollars()));
                        }
                        else if (transactionType.equals(TRANSACTION_TYPE_DFCASH))
                        {
                            controlBalanceDetailVO.setTotalCashNetPremium(controlsAndBalancesTotalsVO.getTotalCashNetPremium());
                            netCash = netCash.addEditBigDecimal(new EDITBigDecimal(controlsAndBalancesTotalsVO.getTotalCashNetPremium()));

                            controlBalanceDetailVO.setCashGainLoss(controlsAndBalancesTotalsVO.getCashGainLoss());
                            netCash = netCash.addEditBigDecimal(new EDITBigDecimal(controlsAndBalancesTotalsVO.getCashGainLoss()));

                            controlBalanceDetailVO.setTotalCashAdminFees(controlsAndBalancesTotalsVO.getTotalCashAdminFees());
                            netCash = netCash.addEditBigDecimal(new EDITBigDecimal(controlsAndBalancesTotalsVO.getTotalCashAdminFees()));

                            controlBalanceDetailVO.setTotalCashCoi(controlsAndBalancesTotalsVO.getTotalCashCoi());
                            netCash = netCash.addEditBigDecimal(new EDITBigDecimal(controlsAndBalancesTotalsVO.getTotalCashCoi()));

                            controlBalanceDetailVO.setTotalCashReallocations(controlsAndBalancesTotalsVO.getTotalCashReallocations());
                            netCash = netCash.addEditBigDecimal(new EDITBigDecimal(controlsAndBalancesTotalsVO.getTotalCashReallocations()));

                            controlBalanceDetailVO.setTotalCashRRD(controlsAndBalancesTotalsVO.getTotalCashRRD());
                            netCash = netCash.addEditBigDecimal(new EDITBigDecimal(controlsAndBalancesTotalsVO.getTotalCashRRD()));

                            controlBalanceDetailVO.setTotalCashSurrenders(controlsAndBalancesTotalsVO.getTotalCashSurrenders());
                            netCash = netCash.addEditBigDecimal(new EDITBigDecimal(controlsAndBalancesTotalsVO.getTotalCashSurrenders()));

                            controlBalanceDetailVO.setTotalCashContribToMortRsv(controlsAndBalancesTotalsVO.getTotalCashContribToMortRsv());
                            netCash = netCash.addEditBigDecimal(new EDITBigDecimal(controlsAndBalancesTotalsVO.getTotalCashContribToMortRsv()));

                            controlBalanceDetailVO.setTotalCashMAndE(controlsAndBalancesTotalsVO.getTotalCashMAndE());
                            mAndEPayablesReceivables = mAndEPayablesReceivables.addEditBigDecimal(new EDITBigDecimal(controlsAndBalancesTotalsVO.getTotalCashMAndE()));
                            netCash = netCash.addEditBigDecimal(new EDITBigDecimal(controlsAndBalancesTotalsVO.getTotalCashMAndE()));

                            controlBalanceDetailVO.setTotalCashAdvisoryFees(controlsAndBalancesTotalsVO.getTotalCashAdvisoryFees());
                            advisoryFeePayablesReceivables = advisoryFeePayablesReceivables.addEditBigDecimal(new EDITBigDecimal(controlsAndBalancesTotalsVO.getTotalCashAdvisoryFees()));
                            netCash = netCash.addEditBigDecimal(new EDITBigDecimal(controlsAndBalancesTotalsVO.getTotalCashAdvisoryFees()));

                            controlBalanceDetailVO.setTotalCashMgmtFees(controlsAndBalancesTotalsVO.getTotalCashMgmtFees());
                            mgtFeePayablesReceivables = mgtFeePayablesReceivables.addEditBigDecimal(new EDITBigDecimal(controlsAndBalancesTotalsVO.getTotalCashMgmtFees()));
                            netCash = netCash.addEditBigDecimal(new EDITBigDecimal(controlsAndBalancesTotalsVO.getTotalCashMgmtFees()));

                            controlBalanceDetailVO.setTotalCashSVAFees(controlsAndBalancesTotalsVO.getTotalCashSVAFees());
                            netCash = netCash.addEditBigDecimal(new EDITBigDecimal(controlsAndBalancesTotalsVO.getTotalCashSVAFees()));

                            controlBalanceDetailVO.setTotalCashAdvanceTransfers(controlsAndBalancesTotalsVO.getTotalCashAdvanceTransfers());
                            netCash = netCash.addEditBigDecimal(new EDITBigDecimal(controlsAndBalancesTotalsVO.getTotalCashAdvanceTransfers()));

                            rvpPayablesReceivables = rvpPayablesReceivables.addEditBigDecimal(new EDITBigDecimal(controlsAndBalancesTotalsVO.getTotalCashRVPFees()));
                            netCash = netCash.addEditBigDecimal(new EDITBigDecimal(controlsAndBalancesTotalsVO.getTotalCashRVPFees()));
                        }
                        else if (transactionType.equals(TRANSACTION_TYPE_DFOFF))
                        {
                            controlBalanceDetailVO.setTotalCashReallocations(controlsAndBalancesTotalsVO.getTotalCashReallocations());
                            netCash = netCash.addEditBigDecimal(new EDITBigDecimal(controlsAndBalancesTotalsVO.getTotalCashReallocations()));
                        }
                    } // end by transaction type

                    // UnitsPurchased
                    controlBalanceDetailVO.setUnitsPurchased(unitsPurchased.getBigDecimal());

                    // UnitBalance
                    unitBalance = unitBalance.addEditBigDecimal(unitsPurchased);
                    controlBalanceDetailVO.setUnitBalance(unitBalance.getBigDecimal());

                    // PolicyOwnerValue
                    EDITBigDecimal policyOwnerValue = unitBalance.multiplyEditBigDecimal(unitValue);
                    controlBalanceDetailVO.setPolicyOwnerValue(policyOwnerValue.getBigDecimal());

                    // NetCash
                    controlBalanceDetailVO.setNetCash(netCash.getBigDecimal());

                    // SharesPurnchased

                    if (!netCash.isEQ(new EDITBigDecimal()))
                    {
                        UnitValuesVO[] unitValuesVOForValuationDate = engineLookup.getUnitValuesByFilteredFundIdDateChargeCode(filteredFundFK,
                                    valuationDate,
                                    pricingDirection,
                                    chargeCodeFK);

                        if (unitValuesVOForValuationDate != null && unitValuesVOForValuationDate.length > 0)
                        {
                            nav2 = new EDITBigDecimal(unitValuesVOForValuationDate[0].getNetAssetValue2());
                        }

                        // Just like the UnitBalance and UnitsPurchased we needed ShareBalance and SharesPurchased
                        // with a precision upto 8 decimal places. while calculating SharesPurchased we use the following formula
                        // and dividend is netCash which is an Amount and has a prcision of only 2 decimal places ...
                        // when we divide netCash with nav2 divideEDITBigDecimal method sets the scale of the result to the
                        // scale of dividend. Since we need more precision upto 7 decimal places, we need to set the scale of netCash manually.

                        netCash.setScale(8);

                        sharesPurchased = netCash.divideEditBigDecimal(nav2);
                    }

                    controlBalanceDetailVO.setSharesPurchased(sharesPurchased.getBigDecimal());

                    // ShareBalance
                    shareBalance = shareBalance.addEditBigDecimal(sharesPurchased);
                    controlBalanceDetailVO.setShareBalance(shareBalance.getBigDecimal());

                    // PolicyActivity
                    controlBalanceDetailVO.setPolicyActivity(new EDITBigDecimal(policyActivity.getBigDecimal()).negate().getBigDecimal());

                    // NetAssets
                    EDITBigDecimal netAssets = shareBalance.multiplyEditBigDecimal(nav2);
                    controlBalanceDetailVO.setNetAssets(netAssets.getBigDecimal());

                    // MAndEPayablesReceivables
                    controlBalanceDetailVO.setMAndEPayableReceivables(mAndEPayablesReceivables.getBigDecimal());

                    // AdvisoryFeePayablesReceivables
                    controlBalanceDetailVO.setAdvisoryFeePayablesReceivables(advisoryFeePayablesReceivables.getBigDecimal());

                    // ManagementFeePayablesReceivables
                    controlBalanceDetailVO.setMgtFeePayablesReceivables(mgtFeePayablesReceivables.getBigDecimal());

                    // RVPFeePayablesReceivables
                    controlBalanceDetailVO.setRVPPayablesReceivables(rvpPayablesReceivables.getBigDecimal());

                    // AccuredGainLoss
                    EDITBigDecimal allPayablesReceivables = new EDITBigDecimal();
                    allPayablesReceivables = allPayablesReceivables.addEditBigDecimal(mAndEPayablesReceivables);
                    allPayablesReceivables = allPayablesReceivables.addEditBigDecimal(advisoryFeePayablesReceivables);
                    allPayablesReceivables = allPayablesReceivables.addEditBigDecimal(mgtFeePayablesReceivables);
                    allPayablesReceivables = allPayablesReceivables.addEditBigDecimal(rvpPayablesReceivables);

                    EDITBigDecimal totalAccruedAdvanceTransfers = new EDITBigDecimal();
                    if (controlBalanceDetailVO.getTotalAccruedAdvanceTransfers() != null)
                    {
                        totalAccruedAdvanceTransfers =  new EDITBigDecimal(controlBalanceDetailVO.getTotalAccruedAdvanceTransfers());
                    }

                    EDITBigDecimal accruedGainLoss = netAssets.subtractEditBigDecimal(policyOwnerValue);
                    accruedGainLoss = accruedGainLoss.subtractEditBigDecimal(allPayablesReceivables);
                    accruedGainLoss = accruedGainLoss.addEditBigDecimal(totalAccruedAdvanceTransfers);

                    controlBalanceDetailVO.setAccruedGainLoss(accruedGainLoss.getBigDecimal());

                    controlBalanceVO.addControlBalanceDetailVO(controlBalanceDetailVO);

                    controlBalanceDetailVO.setControlBalanceFK(controlBalanceVO.getControlBalancePK());

                    calculatorComponent.saveVONonRecursive(controlBalanceDetailVO);

                    // Since the records are ordered in ascending order in TreeMap the last record will contain max AccountingPeriod, EffectiveDate, ValuationDate
                    lastControlBalanceDetailVO = controlBalanceDetailVO;

                    // there might be cases where the last ControlBalanceDetail record might not have max ValuationDate so keep track of ValuationDate to put
                    // in dummy record.
                    if (maxValuationDate == null || new EDITDate(valuationDate).after(new EDITDate(maxValuationDate)))
                    {
                        maxValuationDate = valuationDate;
                    }

                } // end if FundNumber matches
            } // end while ControlBalanceDetail keys

            if (!new EDITDate(cycleDate).equals(new EDITDate(lastControlBalanceDetailVO.getEffectiveDate())))
            {
                createDummyLastRecord(cycleDate, lastControlBalanceDetailVO, maxValuationDate);
            }
        } // end else
    }

    private boolean containsAtleastOneControlBalanceDetailRecord(String clientOrRealFundNumber)
    {
        boolean hasControlBalanceDetailRecord = false;

        Set controlBalanceDetailsKeySet = this.controlBalanceDetails.keySet();

        Iterator controlBalanceDetailsKeys = controlBalanceDetailsKeySet.iterator();

        while (controlBalanceDetailsKeys.hasNext())
        {
            String controlBalanceDetailKey = (String) controlBalanceDetailsKeys.next();

            if (controlBalanceDetailKey.startsWith(clientOrRealFundNumber))
            {
                hasControlBalanceDetailRecord = true;

                break;
            }
        }

        return hasControlBalanceDetailRecord;
    }

    /**
     * When the EffectiveDate on last ControlBalanceDetail record does not match the CycleDate, we need to create dummy record
     * with EffectiveDate = CycleDate. All the remaining necessary fields are copied from last ControlBalanceDetial record.
     * @param cycleDate
     * @param lastControlBalanceDetailVO
     * @throws Exception
     */
    private void createDummyLastRecord(String cycleDate, ControlBalanceDetailVO lastControlBalanceDetailVO, String maxValuationDate) throws Exception
    {
        // Create dummy record when EffectiveDate on last ControlBalanceDetailRecord for the fund is not same as CycleDate
        ControlBalanceDetailVO controlBalanceDetailVO = new ControlBalanceDetailVO();

        controlBalanceDetailVO.setAccountingPeriod(lastControlBalanceDetailVO.getAccountingPeriod());

        controlBalanceDetailVO.setEffectiveDate(cycleDate);

        controlBalanceDetailVO.setValuationDate(maxValuationDate);

        controlBalanceDetailVO.setUnitBalance(lastControlBalanceDetailVO.getUnitBalance());

        controlBalanceDetailVO.setShareBalance(lastControlBalanceDetailVO.getShareBalance());

        controlBalanceDetailVO.setNetAssets(lastControlBalanceDetailVO.getNetAssets());

        controlBalanceDetailVO.setPolicyOwnerValue(lastControlBalanceDetailVO.getPolicyOwnerValue());

        controlBalanceDetailVO.setControlBalanceFK(lastControlBalanceDetailVO.getControlBalanceFK());

        engine.business.Calculator calculatorComponent = new engine.component.CalculatorComponent();

        calculatorComponent.saveVONonRecursive(controlBalanceDetailVO);
    }

    /**
     * Calculates the EndingShareBalance value for the ControlBalance table based on the
     * NAV2 value retrieved from the UnitValues table and the endingDollarBalance param
     * passed into this method (endingDollarBalance/nav2)
     * @param productFilteredFundStructureFK
     * @param chargeCodeFK
     * @param endingDollarBalance
     * @param cycleDate
     * @return
     * @throws Exception
     */
    private EDITBigDecimal getEndingShareBalance(long productFilteredFundStructureFK,
                                                 long chargeCodeFK,
                                                 EDITBigDecimal endingDollarBalance,
                                                 String cycleDate) throws Exception
    {
        ProductFilteredFundStructureVO[] cffsVO = engineLookup.getByPK(productFilteredFundStructureFK);
        long filteredFundPK = cffsVO[0].getFilteredFundFK();

        FilteredFundVO[] filteredFundVO = engineLookup.findFilteredFundByPK(filteredFundPK);

        UnitValuesVO[] unitValuesVO = engineLookup.
                getUnitValuesByFilteredFundIdDateChargeCode(filteredFundPK,
                        cycleDate,
                        filteredFundVO[0].getPricingDirection(),
                        chargeCodeFK);

        EDITBigDecimal endingShareBalance = new EDITBigDecimal();

        if (unitValuesVO != null && unitValuesVO.length > 0)
        {
            EDITBigDecimal nav2 = new EDITBigDecimal(unitValuesVO[0].getNetAssetValue2());

            if (nav2.isGT(EDITBigDecimal.EDIT_DECIMAL_ZILCH_STR))
            {
                endingShareBalance = endingDollarBalance.divideEditBigDecimal(nav2);
            }
        }

        return endingShareBalance;
    }


    /**
     * Routine to take a productFilteredFundStructurePK and a chargeCodeFK
     * and convert to a Client Fund Number or the Real Fund Number.
     * @param fundNumber
     * @param chargeCodeFK
     * @return
     */
    private String convertToClientFund(String fundNumber, long chargeCodeFK)
    {
        if (chargeCodeFK == 0)
        {
            return fundNumber;  // it's the real fund number then
        }
        String clientFundNumber =
                this.transformChargeCodes.getClientFundNumber(chargeCodeFK);

        return (clientFundNumber == null) ? fundNumber : clientFundNumber;
    }


    ///////////////////////////////////////////////////////////////////
    //////////////////// Top-level nested class to help ///////////////
    //////////////////// parse out the key parts        ///////////////
    ///////////////////////////////////////////////////////////////////

    /**
     * This nested class is used to help parse the productFundUpdates Map keys
     */
    public static class ProductFundUpdatesKeyHelper
    {
        private String myKeyString;
        private long productFilteredFundStructureFK;
        private long chargeCodeFK;

        public ProductFundUpdatesKeyHelper(String aKeyString)
        {
            try
            {
                this.myKeyString = aKeyString;
                StringTokenizer st = new StringTokenizer(this.myKeyString, "|");
                String compFFStructureFKStr = st.nextToken();
                String chargeCodeFKStr = st.nextToken();
                // just in case there is a null
                if ("null".equalsIgnoreCase(compFFStructureFKStr))
                {
                    this.productFilteredFundStructureFK = 0L;
                }
                else
                {
                    this.productFilteredFundStructureFK = Long.parseLong(compFFStructureFKStr);
                }

                if ("null".equalsIgnoreCase(chargeCodeFKStr))
                {
                    this.chargeCodeFK = 0L;
                }
                else
                {
                    this.chargeCodeFK = Long.parseLong(chargeCodeFKStr);
                }
            }
            catch (Exception ex)
            {
                throw new RuntimeException("ActivityFileInterfaceProcessor " +
                        "problem parsing keystring for productfundupdates: " +
                        this.myKeyString);
            }
        }

        public long getProductFilteredFundStructureFK()
        {
            return this.productFilteredFundStructureFK;
        }

        public long getChargeCodeFK()
        {
            return this.chargeCodeFK;
        }

    }


    ///////////////////////////////////////////////////////////////////
    //////////////////// Top-level nested class to help ///////////////
    //////////////////// parse out the key parts        ///////////////
    ///////////////////////////////////////////////////////////////////

    /**
     * This nested class is used to help parse the TotalsByFund Map keys
     */
    public static class TotalsByFundKeyHelper
    {
        private String myKeyString;
        private String fundNumber;
        private String transactionType;

        public TotalsByFundKeyHelper(String aKeyString)
        {
            try
            {
                this.myKeyString = aKeyString;
                StringTokenizer st = new StringTokenizer(this.myKeyString, "|");
                this.fundNumber = st.nextToken();;
                this.transactionType = st.nextToken();
            }
            catch (Exception ex)
            {
                throw new RuntimeException("ActivityFileInterfaceProcessor " +
                        "problem parsing keystring for totalsbyfund key: " +
                        this.myKeyString);
            }
        }

        public String getFundNumberK()
        {
            return this.fundNumber;
        }

        public String getTransactionType()
        {
            return this.transactionType;
        }
    }

    /**
     * Determine if the retrieved Fee is to be excluded from the calculation of the new
     * DFCASHEndingShareBalance value
     * @param feeType
     * @return
     */
    private boolean excludedFeeType(String feeType)
    {
        boolean excludedFeeType = false;

        for (int i = 0; i < DFCASH_FEE_TYPES_EXCLUDED.length; i++)
        {
            if (feeType.equalsIgnoreCase(DFCASH_FEE_TYPES_EXCLUDED[i]))
            {
                excludedFeeType = true;
                break;
            }
        }

        return excludedFeeType;
    }
}

