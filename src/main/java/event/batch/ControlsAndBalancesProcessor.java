/*
 * User: dlataill
 * Date: Sep 9, 2004
 * Time: 12:07:06 PM
 *
 * (c) 2000-2004 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement
 */
package event.batch;

import edit.common.CodeTableWrapper;
import edit.common.EDITBigDecimal;

import edit.common.vo.*;
import edit.common.vo.user.ControlsAndBalancesTotalsVO;

import edit.services.config.ServicesConfig;

import edit.services.db.ConnectionFactory;
import edit.services.db.DBTable;
import edit.services.logging.Logging;

import fission.utility.Util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.Serializable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.*;

import logging.LogEvent;
import org.apache.logging.log4j.Logger;


//import org.apache.regexp.RE;
public class ControlsAndBalancesProcessor implements Serializable
{
    private static final String POOLNAME = ConnectionFactory.EDITSOLUTIONS_POOL;
    private static final String fileName = "ControlsAndBalances";
    private static final String oneSpace = " ";
    private static final String CONTROLS_AND_BALANCES_ERROR_PAGE = "/daily/jsp/controlsAndBalancesError.jsp";
    private static final String CONTROLS_AND_BALANCES_SYSTEM_ERROR = "/daily/jsp/controlsAndBalancesSystemError.jsp";
    private static final String CONTROLS_AND_BALANCES_COMPLETE = "/daily/jsp/controlsAndBalancesComplete.jsp";
    private ProductStructureVO[] productStructureVOs;
    private FundVO[] fundVOs;
    private String cycleDate;
    private StringBuffer fileData;
    private CodeTableWrapper codeTableWrapper;

    //    private StorageManager engineSM;
    private SegmentVO[] segmentVOs;
    private Map companyBuySellHT;
    private Map totalsByCompanyName;
    private Map productFundUpdates;
    private Map companyNameHT;
    private Map businessContractHT;

    public void setControlsAndBalancesInformation(String companyName, String cycleDate) throws Exception
    {
        this.cycleDate = cycleDate;

        engine.business.Lookup calcLookup = new engine.component.LookupComponent();

        if (companyName.equalsIgnoreCase("All"))
        {
            productStructureVOs = calcLookup.getAllProductStructures();
        }
        else
        {
            productStructureVOs = calcLookup.getAllProductStructuresByCoName(companyName);
        }

        Vector csIds = new Vector();

        for (int i = 0; i < productStructureVOs.length; i++)
        {
            csIds.add(productStructureVOs[i].getProductStructurePK() + "");
        }

        long[] productKeys = new long[csIds.size()];

        for (int i = 0; i < csIds.size(); i++)
        {
            productKeys[i] = Long.parseLong((String) csIds.elementAt(i));
        }

        fundVOs = calcLookup.getAllFunds();
        fileData = new StringBuffer();
    }

    /**
     * Creates the Controls And Balances Report for the company and cycle date specified in parameters set through
     * the setControlsAndBalancesInformation method within this class.
     * @throws Exception
     */
    public String createControlsAndBalancesReport()
    {
        String returnPage = "";

        int i = 0;

        companyNameHT = new HashMap();
        businessContractHT = new HashMap();

        try
        {
            for (i = 0; i < productStructureVOs.length; i++)
            {
                CompanyVO companyVO = (CompanyVO) productStructureVOs[i].getParentVO(CompanyVO.class);

                companyNameHT.put(companyVO.getCompanyPK() + "", companyVO.getCompanyName().trim());

                businessContractHT.put(productStructureVOs[i].getProductStructurePK() + "", productStructureVOs[i].getBusinessContractName());
            }

            totalsByCompanyName = new HashMap();
            productFundUpdates = new HashMap();

            long[] csIds = new long[1];
            long[] segmentPKs;
            long[] bucketPKs;

            //initiate the output file
            File exportFile = getExportFile();

            for (i = 0; i < fundVOs.length; i++)
            {
                FilteredFundVO[] ffVOs = fundVOs[i].getFilteredFundVO();

                if (ffVOs != null)
                {
                    for (int j = 0; j < ffVOs.length; j++)
                    {
                        ProductFilteredFundStructureVO[] productFFStructureVOs = ffVOs[j].getProductFilteredFundStructureVO();

                        for (int k = 0; k < productStructureVOs.length; k++)
                        {
                            long productStructurePK = productStructureVOs[k].getProductStructurePK();

                            for (int l = 0; l < productFFStructureVOs.length;
                                    l++)
                            {
                                long ffProductStructureFK = productFFStructureVOs[l].getProductStructureFK();

                                if (ffProductStructureFK == productStructurePK)
                                {
                                    csIds[0] = productStructurePK;
                                    segmentVOs = contract.dm.dao.DAOFactory.getSegmentDAO().findAllByCSId(csIds);

                                    if (segmentVOs != null)
                                    {
                                        segmentPKs = null;
                                        bucketPKs = null;

                                        // commented due to change in table structure
                                        // tables modified: ProductFilteredFundStructure

                                        /*String fromDate = productFFStructureVOs[l].getEndingBalanceCycleDate();*/
                                        SegmentVO[] filteredFundSegments = this.getSegmentsContainingFilteredFund(segmentVOs, ffVOs[j].getFilteredFundPK());

                                        if (filteredFundSegments != null)
                                        {
                                            segmentPKs = new long[filteredFundSegments.length];

                                            List buckets = new ArrayList();

                                            for (int m = 0;
                                                    m < filteredFundSegments.length;
                                                    m++)
                                            {
                                                segmentPKs[m] = filteredFundSegments[m].getSegmentPK();
                                                buckets = this.getBucketsForFilteredFund(filteredFundSegments[m], ffVOs[j].getFilteredFundPK(), buckets);
                                            }

                                            if (buckets.size() > 0)
                                            {
                                                bucketPKs = new long[buckets.size()];

                                                for (int n = 0; n < buckets.size();
                                                        n++)
                                                {
                                                    bucketPKs[n] = ((BucketVO) buckets.get(n)).getBucketPK();
                                                }
                                            }

                                            if (bucketPKs != null)
                                            {
                                                // commented due to change in table structure
                                                // tables modified: ProductFilteredFundStructure

                                                /*returnPage = this.getActivityForFund(productStructurePK, fundVOs[i], segmentPKs, bucketPKs, fromDate);*/
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            outputCompanyTotals(totalsByCompanyName);

            updateProductFundBalances(productFundUpdates);

            if (returnPage.equals(""))
            {
                exportControlsAndBalances(exportFile);

                returnPage = CONTROLS_AND_BALANCES_COMPLETE;
            }
        }
        catch (Exception e)
        {
            returnPage = CONTROLS_AND_BALANCES_SYSTEM_ERROR;

            System.out.println("ControlsAndBalancesInterfaceCmd: " + e);

            e.printStackTrace();

            LogEvent logEvent = new LogEvent("Controls And Balances Errored", e);

            Logger logger = Logging.getLogger(Logging.BATCH_JOB);

            logger.error(logEvent);
        }

        return returnPage;
    }

    /**
     * Returns only those segments that contain the specified fund
     * @param segmentVOs - the group of segments for a specific product structure
     * @param filteredFundPK
     * @return
     */
    private SegmentVO[] getSegmentsContainingFilteredFund(SegmentVO[] segmentVOs, long filteredFundPK)
    {
        List segmentsToReturn = new ArrayList();

        for (int i = 0; i < segmentVOs.length; i++)
        {
            InvestmentVO[] investmentVOs = segmentVOs[i].getInvestmentVO();

            for (int j = 0; j < investmentVOs.length; j++)
            {
                if (investmentVOs[j].getFilteredFundFK() == filteredFundPK)
                {
                    segmentsToReturn.add(segmentVOs[i]);

                    break;
                }
            }
        }

        return (SegmentVO[]) segmentsToReturn.toArray(new SegmentVO[segmentsToReturn.size()]);
    }

    /**
     * Returns the BucketVOs for the specified fund
     * @param segmentVO
     * @param filteredFundPK
     * @param buckets
     * @return
     */
    private List getBucketsForFilteredFund(SegmentVO segmentVO, long filteredFundPK, List buckets)
    {
        InvestmentVO[] investmentVOs = segmentVO.getInvestmentVO();

        for (int i = 0; i < investmentVOs.length; i++)
        {
            if (investmentVOs[i].getFilteredFundFK() == filteredFundPK)
            {
                BucketVO[] bucketVOs = investmentVOs[i].getBucketVO();

                if (bucketVOs != null)
                {
                    for (int j = 0; j < bucketVOs.length; j++)
                    {
                        buckets.add(bucketVOs[j]);
                    }
                }
            }
        }

        return buckets;
    }

    /**
     * Retrieves the financial activity for the given segmentPKS, bucketPKs, and fromDate
     * @param productStructurePK
     * @param fundVO
     * @param segmentPKs
     * @param bucketPKs
     * @param fromDate
     * @return
     * @throws Exception
     */
    private String getActivityForFund(long productStructurePK, FundVO fundVO, long[] segmentPKs, long[] bucketPKs, String fromDate) throws Exception
    {
        ResultSet rs = null;
        Connection conn = ConnectionFactory.getSingleton().getConnection(POOLNAME);
        String returnPage = "";

        try
        {
            rs = executeSql(segmentPKs, bucketPKs, fromDate, "T", rs, conn);

            if (rs.wasNull())
            {
                returnPage = CONTROLS_AND_BALANCES_ERROR_PAGE;
            }
            else
            {
                while (rs.next())
                {
                    String transactionType = rs.getString("TransactionTypeCT");
                    EDITBigDecimal rsDollars = new EDITBigDecimal(rs.getBigDecimal("TotalDollars"));
                    EDITBigDecimal rsUnits = new EDITBigDecimal(rs.getBigDecimal("TotalUnits"));

                    addUnitsDollars(productStructurePK, transactionType, rsDollars, rsUnits, fundVO);
                }
            }

            rs = null;
            rs = executeSql(segmentPKs, bucketPKs, fromDate, "F", rs, conn);

            if (rs.wasNull())
            {
                returnPage = CONTROLS_AND_BALANCES_ERROR_PAGE;
            }
            else
            {
                while (rs.next())
                {
                    String transactionType = rs.getString("TransactionTypeCT");
                    EDITBigDecimal rsDollars = new EDITBigDecimal(rs.getBigDecimal("TotalDollars"));
                    EDITBigDecimal rsUnits = new EDITBigDecimal(rs.getBigDecimal("TotalUnits"));

                    subtractUnitsDollars(productStructurePK, transactionType, rsDollars, rsUnits, fundVO);
                }
            }
        }
        catch (Exception e)
        {
            returnPage = CONTROLS_AND_BALANCES_SYSTEM_ERROR;

            System.out.println("ControlsAndBalancesInterfaceCmd: " + e);
            e.printStackTrace();

            throw e;
        }
        finally
        {
            rs.close();

            if (conn != null)
            {
                conn.close();
            }
        }

        return returnPage;
    }

    /**
     * Executes the sql to retrieve financial activity for the given segments, buckets, fromDate and toFromIndicator
     * @param segmentPKs
     * @param bucketPKs
     * @param fromDate
     * @param toFromIndicator
     * @param rs
     * @param conn
     * @return
     * @throws Exception
     */
    private ResultSet executeSql(long[] segmentPKs, long[] bucketPKs, String fromDate, String toFromIndicator, ResultSet rs, Connection conn) throws Exception
    {
        DBTable bucketHistoryDBTable = DBTable.getDBTableForTable("BucketHistory");
        DBTable editTrxHistoryDBTable = DBTable.getDBTableForTable("EDITTrxHistory");
        DBTable editTrxDBTable = DBTable.getDBTableForTable("EDITTrx");
        DBTable clientSetupDBTable = DBTable.getDBTableForTable("ClientSetup");
        DBTable contractSetupDBTable = DBTable.getDBTableForTable("ContractSetup");

        String bucketHistoryTable = bucketHistoryDBTable.getFullyQualifiedTableName();
        String editTrxHistoryTable = editTrxHistoryDBTable.getFullyQualifiedTableName();
        String editTrxTable = editTrxDBTable.getFullyQualifiedTableName();
        String clientSetupTable = clientSetupDBTable.getFullyQualifiedTableName();
        String contractSetupTable = contractSetupDBTable.getFullyQualifiedTableName();

        String transactionTypeCTCol = editTrxDBTable.getDBColumn("TransactionTypeCT").getFullyQualifiedColumnName();
        String editTrxPKCol = editTrxDBTable.getDBColumn("EDITTrxPK").getFullyQualifiedColumnName();
        String clientSetupFKCol = editTrxDBTable.getDBColumn("ClientSetupFK").getFullyQualifiedColumnName();

        String dollarsCol = bucketHistoryDBTable.getDBColumn("Dollars").getFullyQualifiedColumnName();
        String unitsCol = bucketHistoryDBTable.getDBColumn("Units").getFullyQualifiedColumnName();
        String bucketFKCol = bucketHistoryDBTable.getDBColumn("BucketFK").getFullyQualifiedColumnName();
        String editTrxHistoryFKCol = bucketHistoryDBTable.getDBColumn("EDITTrxHistoryFK").getFullyQualifiedColumnName();
        String toFromStatusCol = bucketHistoryDBTable.getDBColumn("ToFromStatus").getFullyQualifiedColumnName();

        String segmentFKCol = contractSetupDBTable.getDBColumn("SegmentFK").getFullyQualifiedColumnName();
        String contractSetupPKCol = contractSetupDBTable.getDBColumn("ContractSetupPK").getFullyQualifiedColumnName();

        String editTrxHistoryPKCol = editTrxHistoryDBTable.getDBColumn("EDITTrxHistoryPK").getFullyQualifiedColumnName();
        String editTrxFKCol = editTrxHistoryDBTable.getDBColumn("EDITTrxFK").getFullyQualifiedColumnName();
        String cycleDateCol = editTrxHistoryDBTable.getDBColumn("CycleDate").getFullyQualifiedColumnName();

        String clientSetupPKCol = clientSetupDBTable.getDBColumn("ClientSetupPK").getFullyQualifiedColumnName();
        String contractSetupFKCol = clientSetupDBTable.getDBColumn("ContractSetupFK").getFullyQualifiedColumnName();

        String sql = " SELECT " + transactionTypeCTCol + ", " + " SUM(" + dollarsCol + ") AS TotalDollars," + " SUM(" + unitsCol + ") AS TotalUnits" + " FROM " + bucketHistoryTable + ", " + editTrxHistoryTable + ", " + editTrxTable + ", " + clientSetupTable + ", " + contractSetupTable + " WHERE " + segmentFKCol + " IN (";

        for (int i = 0; i < segmentPKs.length; i++)
        {
            if (i < (segmentPKs.length - 1))
            {
                sql += segmentPKs[i];
                sql += ", ";
            }
            else
            {
                sql += segmentPKs[i];
            }
        }

        sql += (") AND " + bucketFKCol + " IN (");

        for (int i = 0; i < bucketPKs.length; i++)
        {
            if (i < (bucketPKs.length - 1))
            {
                sql += bucketPKs[i];
                sql += ", ";
            }
            else
            {
                sql += bucketPKs[i];
            }
        }

        sql += (")" + " AND " + editTrxHistoryFKCol + " = " + editTrxHistoryPKCol + " AND " + editTrxFKCol + " = " + editTrxPKCol + " AND " + clientSetupFKCol + " = " + clientSetupPKCol + " AND " + contractSetupFKCol + " = " + contractSetupPKCol + " AND " + cycleDateCol + " > " + fromDate + " AND " + cycleDateCol + " <= " + cycleDate + " AND " + toFromStatusCol + " = '" + toFromIndicator + "'" + " GROUP BY " + transactionTypeCTCol + " ORDER BY " + transactionTypeCTCol);

        try
        {
            PreparedStatement ps = conn.prepareStatement(sql);

            rs = ps.executeQuery();
        }
        catch (Exception e)
        {
            throw e;
        }
        finally
        {
            return rs;
        }
    }

    /**
     * Adds dollars and units retrieved in getActivityForFund method to existing dollar/unit totals by
     * product
     * @param productStructurePK
     * @param transactionType
     * @param rsDollars
     * @param rsUnits
     * @param fundVO
     * @throws Exception
     */
    private void addUnitsDollars(long productStructurePK, String transactionType, EDITBigDecimal rsDollars, EDITBigDecimal rsUnits, FundVO fundVO) throws Exception
    {
        String fundName = fundVO.getName().trim();
        long fundPK = fundVO.getFundPK();

        engine.business.Lookup engineLookup = new engine.component.LookupComponent();
        FilteredFundVO[] ffVO = engineLookup.getSpecificUnitValue(productStructurePK, fundPK, cycleDate);

        long productFilteredFundStructurePK = 0;
        long filteredFundPK = 0;
        String fundNumber = "";

        /* double endingDollarBalance = 0;
        double endingUnitBalance = 0;
        double unitValue = 0; */

        // commented above line(s) for double to BigDecimal conversion
        // sprasad 9/28/2004
        EDITBigDecimal endingDollarBalance = new EDITBigDecimal();
        EDITBigDecimal endingUnitBalance = new EDITBigDecimal();
        EDITBigDecimal unitValue = new EDITBigDecimal();
        String endingBalCycleDate = "";
        String companyName = "";
        String businessContract = "";

        for (int u = 0; u < ffVO.length; u++)
        {
            ProductFilteredFundStructureVO[] productFFStrucutureVOs = ffVO[u].getProductFilteredFundStructureVO();

            for (int v = 0; v < productFFStrucutureVOs.length; v++)
            {
                if (productFFStrucutureVOs[v].getProductStructureFK() == productStructurePK)
                {
                    productFilteredFundStructurePK = productFFStrucutureVOs[v].getProductFilteredFundStructurePK();
                    filteredFundPK = ffVO[u].getFilteredFundPK();

                    String pricingDirection = ffVO[u].getPricingDirection();

                    if (rsUnits.isGT("0"))
                    {
                        UnitValuesVO[] unitValuesVO = engineLookup.getUnitValuesByFilteredFundIdDate(filteredFundPK, cycleDate, pricingDirection);
                        unitValue = new EDITBigDecimal(unitValuesVO[0].getUnitValue());
                    }

                    fundNumber = ffVO[u].getFundNumber();

                    // commented due to change in table structure
                    // tables modified: ProductFilteredFundStructure
                    /*endingDollarBalance = new EDITBigDecimal( productFFStrucutureVOs[v].getEndingDollarBalance() );
                    endingUnitBalance = new EDITBigDecimal( productFFStrucutureVOs[v].getEndingUnitBalance() );
                    endingBalCycleDate = productFFStrucutureVOs[v].getEndingBalanceCycleDate();*/
//                    companyName = (String) companyNameHT.get(companyPK + "");
                    businessContract = (String) businessContractHT.get(productStructurePK + "");

                    break;
                }
            }
        }

        if (totalsByCompanyName.containsKey(companyName + businessContract + fundPK + transactionType))
        {
            ControlsAndBalancesTotalsVO cntlsAndBalancesTotalsVO = (ControlsAndBalancesTotalsVO) totalsByCompanyName.get(companyName + businessContract + fundPK + transactionType);

            EDITBigDecimal totalDollars = new EDITBigDecimal(cntlsAndBalancesTotalsVO.getTotalDollars());
            EDITBigDecimal totalUnits = new EDITBigDecimal(cntlsAndBalancesTotalsVO.getTotalUnits());
            EDITBigDecimal beginningDollarBalance = new EDITBigDecimal(cntlsAndBalancesTotalsVO.getBeginningDollarBalance());
            EDITBigDecimal beginningUnitBalance = new EDITBigDecimal(cntlsAndBalancesTotalsVO.getBeginningUnitBalance());

            if (rsUnits.isGT("0"))
            {
                totalDollars = totalDollars.addEditBigDecimal(rsUnits.multiplyEditBigDecimal(unitValue));
                totalUnits = totalUnits.addEditBigDecimal(rsUnits);
            }
            else
            {
                totalDollars = totalDollars.addEditBigDecimal(rsDollars);
            }

            beginningDollarBalance = beginningDollarBalance.addEditBigDecimal(endingDollarBalance);
            beginningUnitBalance = beginningUnitBalance.addEditBigDecimal(endingUnitBalance);

            if (productFundUpdates.containsKey(productFilteredFundStructurePK + ""))
            {
                List ffVector = (List) productFundUpdates.get(productFilteredFundStructurePK + "");

                EDITBigDecimal newEndingDollarBalance = (EDITBigDecimal) ffVector.get(0);
                EDITBigDecimal newEndingUnitBalance = (EDITBigDecimal) ffVector.get(1);

                if (rsUnits.isGT("0"))
                {
                    newEndingDollarBalance = newEndingDollarBalance.addEditBigDecimal(rsUnits.multiplyEditBigDecimal(unitValue));
                    newEndingUnitBalance = newEndingUnitBalance.addEditBigDecimal(rsUnits);
                }
                else
                {
                    newEndingDollarBalance = newEndingDollarBalance.addEditBigDecimal(rsDollars);
                }

                ffVector.add(0, newEndingDollarBalance);
                ffVector.add(1, newEndingUnitBalance);
            }
            else
            {
                List ffVector = new ArrayList();

                EDITBigDecimal newEndingDollarBalance = new EDITBigDecimal();
                EDITBigDecimal newEndingUnitBalance = new EDITBigDecimal();

                if (rsUnits.isGT("0"))
                {
                    newEndingDollarBalance = endingDollarBalance.addEditBigDecimal(rsUnits.multiplyEditBigDecimal(unitValue));
                    newEndingUnitBalance = endingUnitBalance.addEditBigDecimal(rsUnits);
                }
                else
                {
                    newEndingDollarBalance = endingDollarBalance.addEditBigDecimal(rsDollars);
                    newEndingUnitBalance = endingUnitBalance;
                }

                ffVector.add(0, newEndingDollarBalance);
                ffVector.add(1, newEndingUnitBalance);

                productFundUpdates.put(productFilteredFundStructurePK + "", ffVector);
            }
        }
        else
        {
            ControlsAndBalancesTotalsVO cntlsAndBalancesTotalsVO = new ControlsAndBalancesTotalsVO();

            cntlsAndBalancesTotalsVO.setCompanyName(companyName);
            cntlsAndBalancesTotalsVO.setBusinessContract(businessContract);
            cntlsAndBalancesTotalsVO.setTrxTypeDesc(transactionType);
            cntlsAndBalancesTotalsVO.setFundNumber(fundNumber);
            cntlsAndBalancesTotalsVO.setBeginningDollarBalance(endingDollarBalance.getBigDecimal());
            cntlsAndBalancesTotalsVO.setBeginningUnitBalance(endingUnitBalance.getBigDecimal());

            cntlsAndBalancesTotalsVO.setBeginningBalanceCycleDate(endingBalCycleDate);
            cntlsAndBalancesTotalsVO.setFundName(fundName);

            if (rsUnits.isGT("0"))
            {
                cntlsAndBalancesTotalsVO.setTotalDollars(rsUnits.multiplyEditBigDecimal(unitValue).getBigDecimal());
                cntlsAndBalancesTotalsVO.setTotalUnits(rsUnits.getBigDecimal());
            }
            else
            {
                cntlsAndBalancesTotalsVO.setTotalDollars(rsDollars.getBigDecimal());
                cntlsAndBalancesTotalsVO.setTotalUnits(new EDITBigDecimal().getBigDecimal());
            }

            totalsByCompanyName.put(companyName + businessContract + fundPK + transactionType, cntlsAndBalancesTotalsVO);

            if (productFundUpdates.containsKey(productFilteredFundStructurePK + ""))
            {
                List ffVector = (List) productFundUpdates.get(productFilteredFundStructurePK + "");

                EDITBigDecimal newEndingDollarBalance = (EDITBigDecimal) ffVector.get(0);
                EDITBigDecimal newEndingUnitBalance = (EDITBigDecimal) ffVector.get(1);

                if (rsUnits.isGT("0"))
                {
                    newEndingDollarBalance = newEndingDollarBalance.addEditBigDecimal(rsUnits.multiplyEditBigDecimal(unitValue));
                    newEndingUnitBalance = newEndingUnitBalance.addEditBigDecimal(rsUnits);
                }
                else
                {
                    newEndingDollarBalance = newEndingDollarBalance.addEditBigDecimal(rsDollars);
                }

                ffVector.add(0, newEndingDollarBalance);
                ffVector.add(1, newEndingUnitBalance);
            }
            else
            {
                List ffVector = new ArrayList();

                EDITBigDecimal newEndingDollarBalance = new EDITBigDecimal();
                EDITBigDecimal newEndingUnitBalance = new EDITBigDecimal();

                if (rsUnits.isGT("0"))
                {
                    newEndingDollarBalance = endingDollarBalance.addEditBigDecimal(rsUnits.multiplyEditBigDecimal(unitValue));
                    newEndingUnitBalance = endingUnitBalance.addEditBigDecimal(rsUnits);
                }
                else
                {
                    newEndingDollarBalance = endingDollarBalance.addEditBigDecimal(rsDollars);
                    newEndingUnitBalance = endingUnitBalance;
                }

                ffVector.add(0, newEndingDollarBalance);
                ffVector.add(1, newEndingUnitBalance);

                productFundUpdates.put(productFilteredFundStructurePK + "", ffVector);
            }
        }
    }

    /**
     * Subtracts dollars and units retrieved in getActivityForFund method to existing dollar/unit totals by
     * product
     * @param productStructurePK
     * @param transactionType
     * @param rsDollars
     * @param rsUnits
     * @param fundVO
     * @throws Exception
     */
    private void subtractUnitsDollars(long productStructurePK, String transactionType, EDITBigDecimal rsDollars, EDITBigDecimal rsUnits, FundVO fundVO) throws Exception
    {
        String fundName = fundVO.getName().trim();
        long fundPK = fundVO.getFundPK();

        engine.business.Lookup engineLookup = new engine.component.LookupComponent();
        FilteredFundVO[] ffVO = engineLookup.getSpecificUnitValue(productStructurePK, fundPK, cycleDate);

        long productFilteredFundStructurePK = 0;
        long filteredFundPK = 0;
        String fundNumber = "";
        EDITBigDecimal endingDollarBalance = new EDITBigDecimal();
        EDITBigDecimal endingUnitBalance = new EDITBigDecimal();
        EDITBigDecimal unitValue = new EDITBigDecimal();
        String endingBalCycleDate = "";
        String companyName = "";
        String businessContract = "";

        for (int u = 0; u < ffVO.length; u++)
        {
            ProductFilteredFundStructureVO[] productFFStrucutureVOs = ffVO[u].getProductFilteredFundStructureVO();

            for (int v = 0; v < productFFStrucutureVOs.length; v++)
            {
                if (productFFStrucutureVOs[v].getProductStructureFK() == productStructurePK)
                {
                    productFilteredFundStructurePK = productFFStrucutureVOs[v].getProductFilteredFundStructurePK();
                    filteredFundPK = ffVO[u].getFilteredFundPK();

                    String pricingDirection = ffVO[u].getPricingDirection();

                    /*if (rsUnits > 0)
                    {
                        UnitValuesVO[] unitValuesVO = engineLookup.getUnitValuesByFilteredFundIdDate(filteredFundPK, cycleDate, pricingDirection);

                        unitValue = unitValuesVO[0].getUnitValue();
                    }*/
                    if (rsUnits.isGT("0"))
                    {
                        UnitValuesVO[] unitValuesVO = engineLookup.getUnitValuesByFilteredFundIdDate(filteredFundPK, cycleDate, pricingDirection);
                        unitValue = new EDITBigDecimal(unitValuesVO[0].getUnitValue());
                    }

                    fundNumber = ffVO[u].getFundNumber();

                    // commented due to change in table structure
                    // tables modified: ProductFilteredFundStructure
                    /*endingDollarBalance = new EDITBigDecimal( productFFStrucutureVOs[v].getEndingDollarBalance() );
                    endingUnitBalance = new EDITBigDecimal( productFFStrucutureVOs[v].getEndingUnitBalance() );

                    endingBalCycleDate = productFFStrucutureVOs[v].getEndingBalanceCycleDate();*/
//                    companyName = (String) companyNameHT.get(companyStructurePK + "");
                    businessContract = (String) businessContractHT.get(productStructurePK + "");

                    break;
                }
            }
        }

        if (totalsByCompanyName.containsKey(companyName + businessContract + fundPK + transactionType))
        {
            ControlsAndBalancesTotalsVO cntlsAndBalancesTotalsVO = (ControlsAndBalancesTotalsVO) totalsByCompanyName.get(companyName + businessContract + fundPK + transactionType);

            EDITBigDecimal totalDollars = new EDITBigDecimal(cntlsAndBalancesTotalsVO.getTotalDollars());
            EDITBigDecimal totalUnits = new EDITBigDecimal(cntlsAndBalancesTotalsVO.getTotalUnits());
            EDITBigDecimal beginningDollarBalance = new EDITBigDecimal(cntlsAndBalancesTotalsVO.getBeginningDollarBalance());
            EDITBigDecimal beginningUnitBalance = new EDITBigDecimal(cntlsAndBalancesTotalsVO.getBeginningUnitBalance());

            if (rsUnits.isGT("0"))
            {
                totalDollars = totalDollars.addEditBigDecimal(rsUnits.multiplyEditBigDecimal(unitValue));
                totalUnits = totalUnits.addEditBigDecimal(rsUnits);
            }
            else
            {
                totalDollars = totalDollars.addEditBigDecimal(rsDollars);
            }

            beginningDollarBalance = beginningDollarBalance.addEditBigDecimal(endingDollarBalance);
            beginningUnitBalance = beginningUnitBalance.addEditBigDecimal(endingUnitBalance);

            if (productFundUpdates.containsKey(productFilteredFundStructurePK + ""))
            {
                List ffVector = (List) productFundUpdates.get(productFilteredFundStructurePK + "");

                EDITBigDecimal newEndingDollarBalance = (EDITBigDecimal) ffVector.get(0);
                EDITBigDecimal newEndingUnitBalance = (EDITBigDecimal) ffVector.get(1);

                if (rsUnits.isGT("0"))
                {
                    newEndingDollarBalance = newEndingDollarBalance.subtractEditBigDecimal(rsUnits.multiplyEditBigDecimal(unitValue));
                    newEndingUnitBalance = newEndingUnitBalance.subtractEditBigDecimal(rsUnits);
                }
                else
                {
                    newEndingDollarBalance = newEndingDollarBalance.subtractEditBigDecimal(rsDollars);
                }

                ffVector.add(0, newEndingDollarBalance);
                ffVector.add(1, newEndingUnitBalance);
            }
            else
            {
                List ffVector = new ArrayList();

                EDITBigDecimal newEndingDollarBalance = new EDITBigDecimal();
                EDITBigDecimal newEndingUnitBalance = new EDITBigDecimal();

                if (rsUnits.isGT("0"))
                {
                    newEndingDollarBalance = endingDollarBalance.subtractEditBigDecimal(rsUnits.multiplyEditBigDecimal(unitValue));
                    newEndingUnitBalance = endingUnitBalance.subtractEditBigDecimal(rsUnits);
                }
                else
                {
                    newEndingDollarBalance = endingDollarBalance.subtractEditBigDecimal(rsDollars);
                    newEndingUnitBalance = endingUnitBalance;
                }

                ffVector.add(0, newEndingDollarBalance);
                ffVector.add(1, newEndingUnitBalance);

                productFundUpdates.put(productFilteredFundStructurePK + "", ffVector);
            }
        }
        else
        {
            ControlsAndBalancesTotalsVO cntlsAndBalancesTotalsVO = new ControlsAndBalancesTotalsVO();

            cntlsAndBalancesTotalsVO.setCompanyName(companyName);
            cntlsAndBalancesTotalsVO.setBusinessContract(businessContract);
            cntlsAndBalancesTotalsVO.setTrxTypeDesc(transactionType);
            cntlsAndBalancesTotalsVO.setFundNumber(fundNumber);
            cntlsAndBalancesTotalsVO.setBeginningDollarBalance(endingDollarBalance.getBigDecimal());
            cntlsAndBalancesTotalsVO.setBeginningUnitBalance(endingUnitBalance.getBigDecimal());

            cntlsAndBalancesTotalsVO.setBeginningBalanceCycleDate(endingBalCycleDate);
            cntlsAndBalancesTotalsVO.setFundName(fundName);

            if (rsUnits.isGT("0"))
            {
                cntlsAndBalancesTotalsVO.setTotalDollars(rsUnits.multiplyEditBigDecimal(unitValue).negate().getBigDecimal());
                cntlsAndBalancesTotalsVO.setTotalUnits(rsUnits.negate().getBigDecimal());
            }
            else
            {
                cntlsAndBalancesTotalsVO.setTotalDollars(rsDollars.negate().getBigDecimal());
                cntlsAndBalancesTotalsVO.setTotalUnits(new EDITBigDecimal().getBigDecimal());
            }

            totalsByCompanyName.put(companyName + businessContract + fundPK + transactionType, cntlsAndBalancesTotalsVO);

            if (productFundUpdates.containsKey(productFilteredFundStructurePK + ""))
            {
                List ffVector = (List) productFundUpdates.get(productFilteredFundStructurePK + "");

                EDITBigDecimal newEndingDollarBalance = (EDITBigDecimal) ffVector.get(0);
                EDITBigDecimal newEndingUnitBalance = (EDITBigDecimal) ffVector.get(1);

                if (rsUnits.isGT("0"))
                {
                    newEndingDollarBalance = newEndingDollarBalance.subtractEditBigDecimal(rsUnits.multiplyEditBigDecimal(unitValue));
                    newEndingUnitBalance = newEndingUnitBalance.subtractEditBigDecimal(rsUnits);
                }
                else
                {
                    newEndingDollarBalance = newEndingDollarBalance.subtractEditBigDecimal(rsDollars);
                }

                ffVector.add(0, newEndingDollarBalance);
                ffVector.add(1, newEndingUnitBalance);
            }
            else
            {
                List ffVector = new ArrayList();

                EDITBigDecimal newEndingDollarBalance = new EDITBigDecimal();
                EDITBigDecimal newEndingUnitBalance = new EDITBigDecimal();

                if (rsUnits.isGT("0"))
                {
                    newEndingDollarBalance = endingDollarBalance.subtractEditBigDecimal(rsUnits.multiplyEditBigDecimal(unitValue));
                    newEndingUnitBalance = endingUnitBalance.subtractEditBigDecimal(rsUnits);
                }
                else
                {
                    newEndingDollarBalance = endingDollarBalance.subtractEditBigDecimal(rsDollars);
                    newEndingUnitBalance = endingUnitBalance;
                }

                ffVector.add(0, newEndingDollarBalance);
                ffVector.add(1, newEndingUnitBalance);

                productFundUpdates.put(productFilteredFundStructurePK + "", ffVector);
            }
        }
    }

    /**
     * Outputs fund activity by product/fund to the extract file
     * @param totalsByCompanyName
     * @throws Exception
     */
    private void outputCompanyTotals(Map totalsByCompanyName) throws Exception
    {
        codeTableWrapper = CodeTableWrapper.getSingleton();

        Map sortedTotals = sortTotalsByCompanyAndFund(totalsByCompanyName);

        CodeTableVO[] trxTypes = codeTableWrapper.getCodeTableEntries("TRXTYPE");

        String prevCompany = "";
        String prevBusinessContract = "";
        String prevFund = "";
        String prevFundName = "";
        EDITBigDecimal fundDollarTotal = new EDITBigDecimal();
        EDITBigDecimal fundUnitTotal = new EDITBigDecimal();
        EDITBigDecimal fundBuySellUnits = new EDITBigDecimal();
        EDITBigDecimal fundBuySellDollars = new EDITBigDecimal();
        companyBuySellHT = new HashMap();

        int fieldLen = 0;
        int numSpaces = 0;

        Iterator it = sortedTotals.values().iterator();

        while (it.hasNext())
        {
            ControlsAndBalancesTotalsVO cntlsAndBalancesTotalsVO = (ControlsAndBalancesTotalsVO) it.next();

            String currentCompanyName = cntlsAndBalancesTotalsVO.getCompanyName();
            String currentBusinessContract = cntlsAndBalancesTotalsVO.getBusinessContract();
            String fundNumber = cntlsAndBalancesTotalsVO.getFundNumber();
            EDITBigDecimal beginningDollarBalance = Util.roundToNearestCent(cntlsAndBalancesTotalsVO.getBeginningDollarBalance());
            EDITBigDecimal beginningUnitBalance = new EDITBigDecimal(cntlsAndBalancesTotalsVO.getBeginningUnitBalance());
            String fundName = cntlsAndBalancesTotalsVO.getFundName();
            String trxTypeDesc = cntlsAndBalancesTotalsVO.getTrxTypeDesc();
            EDITBigDecimal totalUnits = new EDITBigDecimal(cntlsAndBalancesTotalsVO.getTotalUnits());
            EDITBigDecimal totalDollars = Util.roundToNearestCent(cntlsAndBalancesTotalsVO.getTotalDollars());

            if (!currentCompanyName.equals(prevCompany))
            {
                if (fileData.length() > 0)
                {
                    outputBuySellReport(prevCompany);
                }

                else
                {
                    fileData.append("                                   ");
                    fileData.append("Controls Report");
                    fileData.append("\n");
                    fileData.append("\n");
                    fileData.append("\n");
                    fileData.append("\n");
                }

                prevCompany = currentCompanyName;
                prevBusinessContract = currentBusinessContract;
                prevFund = "";
                prevFundName = "";

                fileData.append("Company: ");
                fileData.append(prevCompany);
                fileData.append("   ");
                fileData.append(prevBusinessContract);
                fileData.append("\n");
                fileData.append("Cycle Date: ");
                fileData.append(cycleDate);
                fileData.append("\n");
                fileData.append("                                                             ");
                fileData.append("Effective Accum Units        Value");
            }

            if (!currentBusinessContract.equals(prevBusinessContract))
            {
                if (fileData.length() > 0)
                {
                    outputFundTotal(prevFund, prevFundName, fundUnitTotal, fundDollarTotal, fundBuySellUnits, fundBuySellDollars);

                    prevBusinessContract = currentBusinessContract;
                    prevFund = "";
                    prevFundName = "";

                    /*fundUnitTotal      = 0;
                    fundDollarTotal    = 0;
                    fundBuySellUnits   = 0;
                    fundBuySellDollars = 0;*/

                    // commented above line(s) for double to BigDecimal conversion
                    // sprasad 9/28/2004
                    fundUnitTotal = new EDITBigDecimal();
                    fundDollarTotal = new EDITBigDecimal();
                    fundBuySellUnits = new EDITBigDecimal();
                    fundBuySellDollars = new EDITBigDecimal();

                    fileData.append("\n");
                    fileData.append("\n");
                    fileData.append("\n");
                    fileData.append("Company: ");
                    fileData.append(prevCompany);
                    fileData.append("   ");
                    fileData.append(prevBusinessContract);
                    fileData.append("\n");
                    fileData.append("Cycle Date: ");
                    fileData.append(cycleDate);
                    fileData.append("\n");
                    fileData.append("                                                             ");
                    fileData.append("Effective Accum Units        Value");
                }
            }

            if (!fundNumber.equals(prevFund))
            {
                if (!prevFund.equals(""))
                {
                    outputFundTotal(prevFund, prevFundName, fundUnitTotal, fundDollarTotal, fundBuySellUnits, fundBuySellDollars);

                    fundUnitTotal = new EDITBigDecimal();
                    fundDollarTotal = new EDITBigDecimal();
                    fundBuySellUnits = new EDITBigDecimal();
                    fundBuySellDollars = new EDITBigDecimal();
                }

                prevFund = fundNumber;
                prevFundName = fundName;
                fundDollarTotal = beginningDollarBalance;
                fundUnitTotal = beginningUnitBalance;

                fileData.append("\n");
                fileData.append("\n");
                fileData.append("Start - Fund: ");
                fieldLen = fundNumber.length();
                numSpaces = 6 - fieldLen;

                for (int i = 1; i <= numSpaces; i++)
                {
                    fundNumber = fundNumber + oneSpace;
                }

                fileData.append(fundNumber);
                fileData.append(" ");

                if (fundName.length() > 40)
                {
                    fundName = fundName.substring(0, 40);
                }

                fieldLen = fundName.length();
                numSpaces = 40 - fieldLen;

                for (int i = 1; i <= numSpaces; i++)
                {
                    fundName = fundName + oneSpace;
                }

                fileData.append(fundName);

                String beginningUnitBalStr = Util.formatDecimal("#,###,##0.000000000000", beginningUnitBalance);
                fieldLen = beginningUnitBalStr.length();
                numSpaces = 22 - fieldLen;

                for (int i = 1; i <= numSpaces; i++)
                {
                    beginningUnitBalStr = beginningUnitBalStr + oneSpace;
                }

                fileData.append(beginningUnitBalStr);

                String beginningDollarBalStr = Util.formatDecimal("###,###,###,##0.00", beginningDollarBalance);
                fieldLen = beginningDollarBalStr.length();
                numSpaces = 18 - fieldLen;

                for (int i = 1; i <= numSpaces; i++)
                {
                    beginningDollarBalStr = beginningDollarBalStr + oneSpace;
                }

                fileData.append("       ");
                fileData.append(beginningDollarBalStr);
            }

            fileData.append("\n");
            fileData.append("     ");

            for (int i = 0; i < trxTypes.length; i++)
            {
                if (trxTypes[i].getCode().equalsIgnoreCase(trxTypeDesc))
                {
                    trxTypeDesc = trxTypes[i].getCodeDesc();
                    break;
                }
                trxTypeDesc = codeTableWrapper.getCodeDescByCodeTableNameAndCode("DEATHTRXTYPE", trxTypeDesc);
            }

            fieldLen = trxTypeDesc.length();
            numSpaces = 56 - fieldLen;

            for (int i = 1; i <= numSpaces; i++)
            {
                trxTypeDesc = trxTypeDesc + oneSpace;
            }


            fileData.append(trxTypeDesc);
            fundUnitTotal = fundUnitTotal.addEditBigDecimal(totalUnits);
            fundBuySellUnits = fundBuySellUnits.addEditBigDecimal(totalUnits);
            fundDollarTotal = fundDollarTotal.addEditBigDecimal(totalDollars);
            fundBuySellDollars = fundBuySellDollars.addEditBigDecimal(totalDollars);

            String totalUnitsStr = "";

            if (totalUnits.isLT("0"))
            {
                totalUnits = totalUnits.negate();

                totalUnitsStr = "(" + Util.formatDecimal("#,###,##0.000000000000", totalUnits) + ")";
            }
            else
            {
                totalUnitsStr = Util.formatDecimal("#,###,##0.000000000000", totalUnits);
            }

            fieldLen = totalUnitsStr.length();
            numSpaces = 22 - fieldLen;

            for (int i = 1; i <= numSpaces; i++)
            {
                totalUnitsStr = totalUnitsStr + oneSpace;
            }

            fileData.append(totalUnitsStr);

            String totalDollarsStr = "";

            if (totalDollars.isLT("0"))
            {
                totalDollars = totalDollars.negate();

                totalDollarsStr = "(" + Util.formatDecimal("###,###,##0.00", Util.roundToNearestCent(totalDollars)) + ")";
            }
            else
            {
                totalDollarsStr = Util.formatDecimal("###,###,##0.00", Util.roundToNearestCent(totalDollars));
            }

            fieldLen = totalDollarsStr.length();
            numSpaces = 18 - fieldLen;

            for (int i = 1; i <= numSpaces; i++)
            {
                totalDollarsStr = totalDollarsStr + oneSpace;
            }

            fileData.append("       ");
            fileData.append(totalDollarsStr);
        }

        if (!sortedTotals.isEmpty())
        {
            outputFundTotal(prevFund, prevFundName, fundUnitTotal, fundDollarTotal, fundBuySellUnits, fundBuySellDollars);
        }

        outputBuySellReport(prevCompany);
    }

    /**
     * Outputs the ending fund balance for a given fund
     * @param prevFund
     * @param prevFundName
     * @param fundUnitTotal
     * @param fundDollarTotal
     * @param fundBuySellUnits
     * @param fundBuySellDollars
     */
    private void outputFundTotal(String prevFund, String prevFundName, EDITBigDecimal fundUnitTotal, EDITBigDecimal fundDollarTotal, EDITBigDecimal fundBuySellUnits, EDITBigDecimal fundBuySellDollars)
    {
        fileData.append("\n");
        fileData.append("End - Fund:   ");

        int fieldLen = prevFund.length();
        int numSpaces = 6 - fieldLen;

        for (int i = 1; i <= numSpaces; i++)
        {
            prevFund = prevFund + oneSpace;
        }

        fileData.append(prevFund);
        fileData.append(" ");

        if (prevFundName.length() > 40)
        {
            prevFundName = prevFundName.substring(0, 40);
        }

        fieldLen = prevFundName.length();
        numSpaces = 40 - fieldLen;

        for (int i = 1; i <= numSpaces; i++)
        {
            prevFundName = prevFundName + oneSpace;
        }

        fileData.append(prevFundName);

        String fundUnitTotalStr = "";

        if (fundUnitTotal.isGT("0"))
        {
            fundUnitTotal = fundUnitTotal.negate();

            fundUnitTotalStr = "(" + Util.formatDecimal("#,###,##0.000000000000", fundUnitTotal) + ")";
        }
        else
        {
            fundUnitTotalStr = Util.formatDecimal("#,###,##0.000000000000", fundUnitTotal);
        }

        fieldLen = fundUnitTotalStr.length();
        numSpaces = 23 - fieldLen;

        for (int i = 1; i <= numSpaces; i++)
        {
            fundUnitTotalStr = fundUnitTotalStr + oneSpace;
        }

        fileData.append(fundUnitTotalStr);

        String fundDollarTotalStr = "";

        if (fundDollarTotal.isGT("0"))
        {
            fundDollarTotal = fundDollarTotal.negate();

            fundDollarTotalStr = "(" + Util.formatDecimal("###,###,###,##0.00", Util.roundToNearestCent(fundDollarTotal)) + ")";
        }
        else
        {
            fundDollarTotalStr = Util.formatDecimal("###,###,###,##0.00", Util.roundToNearestCent(fundDollarTotal));
        }

        fileData.append("      ");
        fieldLen = fundDollarTotalStr.length();
        numSpaces = 18 - fieldLen;

        for (int i = 1; i <= numSpaces; i++)
        {
            fundDollarTotalStr = fundDollarTotalStr + oneSpace;
        }

        fileData.append(fundDollarTotalStr);

        if (companyBuySellHT.containsKey(prevFund))
        {
            List fundBuySellVector = (List) companyBuySellHT.get(prevFund);

            EDITBigDecimal vectorBuySellUnits = (EDITBigDecimal) fundBuySellVector.get(2);
            EDITBigDecimal vectorBuySellDollars = (EDITBigDecimal) fundBuySellVector.get(3);

            vectorBuySellUnits = vectorBuySellUnits.addEditBigDecimal(fundBuySellUnits);
            vectorBuySellDollars = vectorBuySellDollars.addEditBigDecimal(fundBuySellDollars);

            fundBuySellVector.add(2, vectorBuySellUnits);
            fundBuySellVector.add(3, vectorBuySellDollars);
        }
        else
        {
            List fundBuySellVector = new ArrayList();

            fundBuySellVector.add(0, prevFund);
            fundBuySellVector.add(1, prevFundName);
            fundBuySellVector.add(2, fundBuySellUnits);
            fundBuySellVector.add(3, fundBuySellDollars);

            companyBuySellHT.put(prevFund, fundBuySellVector);
        }
    }

    /**
     * Creates the Buy/Sell Report for the specified product
     * @param prevCompany
     */
    private void outputBuySellReport(String prevCompany)
    {
        Map sortedBuySell = sortBuySellByFund();

        fileData.append("\n");
        fileData.append("---------------------------------------------------");
        fileData.append("---------------------------------------------------");
        fileData.append("\n");
        fileData.append("\n");
        fileData.append("                                   ");
        fileData.append("Buy/Sell Report");
        fileData.append("\n");
        fileData.append("\n");

        fileData.append("Company: ");
        fileData.append(prevCompany);
        fileData.append("\n");
        fileData.append("Cycle Date: ");
        fileData.append(cycleDate);
        fileData.append("\n");
        fileData.append("\n");
        fileData.append("\n");
        fileData.append("Fund #     Fund Name");
        fileData.append("                                         ");
        fileData.append("Buy/(Sell) Units             Buy/(Sell) Value");
        fileData.append("\n");
        fileData.append("\n");

        Iterator it = sortedBuySell.values().iterator();

        while (it.hasNext())
        {
            List fundBuySellVector = (List) it.next();

            String fundNumber = (String) fundBuySellVector.get(0);
            String fundName = (String) fundBuySellVector.get(1);
            EDITBigDecimal buySellUnits = (EDITBigDecimal) fundBuySellVector.get(2);
            EDITBigDecimal buySellDollars = Util.roundToNearestCent((EDITBigDecimal) fundBuySellVector.get(3));

            int fieldLen = fundNumber.length();
            int numSpaces = 6 - fieldLen;

            for (int i = 1; i <= numSpaces; i++)
            {
                fundNumber = fundNumber + oneSpace;
            }

            fileData.append(fundNumber);

            if (fundName.length() > 40)
            {
                fundName = fundName.substring(0, 40);
            }

            fileData.append("     ");
            fieldLen = fundName.length();
            numSpaces = 40 - fieldLen;

            for (int i = 1; i <= numSpaces; i++)
            {
                fundName = fundName + oneSpace;
            }

            fileData.append(fundName);

            String buySellUnitsStr = "";

            if (buySellUnits.isGT("0"))
            {
                buySellUnits = buySellUnits.negate();

                buySellUnitsStr = "(" + Util.formatDecimal("#,###,##0.000000000000", buySellUnits) + ")";
            }
            else
            {
                buySellUnitsStr = Util.formatDecimal("#,###,##0.000000000000", buySellUnits);
            }

            fieldLen = buySellUnitsStr.length();
            numSpaces = 26 - fieldLen;

            for (int i = 1; i <= numSpaces; i++)
            {
                buySellUnitsStr = buySellUnitsStr + oneSpace;
            }

            fileData.append("          ");
            fileData.append(buySellUnitsStr);

            String buySellDollarsStr = "";

            if (buySellDollars.isGT("0"))
            {
                buySellDollars = buySellDollars.negate();

                buySellDollarsStr = "(" + Util.formatDecimal("###,###,###,##0.00", buySellDollars) + ")";
            }
            else
            {
                buySellDollarsStr = Util.formatDecimal("###,###,###,##0.00", buySellDollars);
            }

            fieldLen = buySellDollarsStr.length();
            numSpaces = 18 - fieldLen;

            for (int i = 1; i <= numSpaces; i++)
            {
                buySellDollarsStr = buySellDollarsStr + oneSpace;
            }

            fileData.append("   ");
            fileData.append(buySellDollarsStr);
            fileData.append("\n");
        }
    }

    /**
     * Updates the ending balance information on the ProductFilteredFundStructure table
     * @param productFundUpdates
     * @throws Exception
     */
    private void updateProductFundBalances(Map productFundUpdates) throws Exception
    {
        Iterator ffUpdatesKeys = productFundUpdates.keySet().iterator();

        while (ffUpdatesKeys.hasNext())
        {
            String key = (String) ffUpdatesKeys.next();

            List ffUpdateVector = (List) productFundUpdates.get(key);

            engine.business.Lookup engineLookup = new engine.component.LookupComponent();
            ProductFilteredFundStructureVO[] productFilteredFundStructureVO = engineLookup.getByPK(Long.parseLong(key));

            // commented due to change in table structure
            // tables modified: ProductFilteredFundStructure

            /*productFilteredFundStructureVO[0].setEndingBalanceCycleDate(cycleDate);

            productFilteredFundStructureVO[0].setEndingDollarBalance( ( (EDITBigDecimal) ffUpdateVector.get(0) ).getBigDecimal() );
            productFilteredFundStructureVO[0].setEndingUnitBalance( ( (EDITBigDecimal) ffUpdateVector.get(1) ).getBigDecimal() );*/
            engine.business.Calculator calculator = new engine.component.CalculatorComponent();
            calculator.saveVONonRecursive(productFilteredFundStructureVO[0]);
        }

        companyBuySellHT.clear();
    }

    /**
     * Sorts the fund totals by company name, businessContract, fundNumber, and transaction.
     * @param totalsByCompanyName
     * @return
     */
    private TreeMap sortTotalsByCompanyAndFund(Map totalsByCompanyName)
    {
        TreeMap sortedTotals = new TreeMap();

        Iterator enumer = totalsByCompanyName.values().iterator();

        while (enumer.hasNext())
        {
            ControlsAndBalancesTotalsVO cntlsAndBalancesTotalsVO = (ControlsAndBalancesTotalsVO) enumer.next();

            String companyName = cntlsAndBalancesTotalsVO.getCompanyName();
            String businessContract = cntlsAndBalancesTotalsVO.getBusinessContract();
            String fundNumber = cntlsAndBalancesTotalsVO.getFundNumber();
            String transaction = cntlsAndBalancesTotalsVO.getTrxTypeDesc();

            sortedTotals.put(companyName + businessContract + fundNumber + transaction, cntlsAndBalancesTotalsVO);
        }

        return sortedTotals;
    }

    /**
     * Sorts the Buy/Sell informtion by fund
     * @return
     */
    private TreeMap sortBuySellByFund()
    {
        TreeMap sortedBuySell = new TreeMap();

        Iterator enumer = companyBuySellHT.keySet().iterator();

        while (enumer.hasNext())
        {
            String fundNumber = (String) enumer.next();

            sortedBuySell.put(fundNumber, companyBuySellHT.get(fundNumber));
        }

        return sortedBuySell;
    }

    /**
     * Set up the export file
     * @return  File - define name
     */
    private File getExportFile()
    {
        EDITExport export1 = ServicesConfig.getEDITExport("ExportDirectory1");

        File exportFile = new File(export1.getDirectory() + fileName + System.currentTimeMillis() + ".txt");

        return exportFile;
    }

    /**
     * Write extract record to the export file
     * @param exportFile
     * @throws Exception
     */
    private void exportControlsAndBalances(File exportFile) throws Exception
    {
        appendToFile(exportFile, fileData.toString());
    }

    /**
     * Each new extract record gets appended to the file.
     * @param exportFile
     * @param data
     * @throws Exception
     */
    private void appendToFile(File exportFile, String data) throws Exception
    {
        BufferedWriter bw = new BufferedWriter(new FileWriter(exportFile, true));

        bw.write(data);

        bw.flush();

        bw.close();
    }
}
