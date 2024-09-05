/*
 * User: dlataill
 * Date: Jan 29, 2004
 * Time: 12:43:07 PM
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package event.batch;

import batch.business.Batch;

import codetable.EquityIndexHedgeDocument;
import codetable.PRASEDocumentFactory;

import contract.Segment;

import contract.dm.dao.FastDAO;

import edit.common.EDITDate;
import edit.common.EDITMap;
import edit.common.vo.EDITExport;
import edit.common.vo.EquityIndexHedgeDetailVO;
import edit.common.vo.EquityIndexHedgeVO;
import edit.common.vo.SubBucketDetailVO;
import edit.common.vo.VOObject;

import edit.services.EditServiceLocator;
import edit.services.config.ServicesConfig;
import edit.services.logging.Logging;

import engine.ProductStructure;

import engine.business.Analyzer;

import engine.component.AnalyzerComponent;

import fission.threading.SEGPooledExecutor;

import fission.utility.Util;
import fission.utility.XMLReportWriter;
import fission.utility.XMLUtil;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.Serializable;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import logging.Log;
import logging.LogEvent;

import org.apache.logging.log4j.Logger;

import org.dom4j.Document;


public class EquityIndexHedgeProcessor implements Serializable
{
//    private SEGPooledExecutor pooledExecutor = Util.getSEGPooledExecutor();

    public EquityIndexHedgeProcessor()
    {
        super();
    }

    public void createEquityIndexHedgeExtract(String productStructure, String runDate, String createSubBucketInd)
    {
        EditServiceLocator.getSingleton().getBatchAgent()
                          .getBatchStat(Batch.BATCH_JOB_CREATE_EQUITY_INDEX_HEDGE_EXTRACTS).tagBatchStart(Batch.BATCH_JOB_CREATE_EQUITY_INDEX_HEDGE_EXTRACTS,
            "Hedge Extract");

        ProductStructure[] productStructures = getProductStructures(productStructure);

        try
        {
            processRequestForSelectedProductStructures(productStructures, new EDITDate(runDate), createSubBucketInd);
        }
        catch (Exception e)
        {
            System.out.println(e);

            e.printStackTrace(); //To change body of catch statement use Options | File Templates.

            LogEvent logEvent = new LogEvent("Equity Index Hedge Errored", e);

            Logger logger = Logging.getLogger(Logging.BATCH_JOB);

            logger.error(logEvent);

//            logErrorToDatabase(runDate);
        }
        finally
        {
            EditServiceLocator.getSingleton().getBatchAgent()
                              .getBatchStat(Batch.BATCH_JOB_CREATE_EQUITY_INDEX_HEDGE_EXTRACTS).tagBatchStop();
        }
    }

    /**
     * The set of ProductStructures relating to the specified productStrucure(PK). All ProductStructures are returned if
     * the productStructure name is "All".
     * @param productStructure
     * @return
     */
    private ProductStructure[] getProductStructures(String productStructure)
    {
        ProductStructure[] productStructures = null;

        if (productStructure.equalsIgnoreCase("All"))
        {
            productStructures = ProductStructure.find_All_V1();
        }
        else
        {
            productStructures = new ProductStructure[1];

            productStructures[0] = ProductStructure.findByPK(new Long(productStructure));
        }

        return productStructures;
    }

    private void processRequestForSelectedProductStructures(ProductStructure[] productStructures, EDITDate runDate,
        String createSubBucketInd) throws Exception
    {
        String eihExportFileName = getExportFileName("SEGEIH", null);

        XMLReportWriter eihExportWriter = new XMLReportWriter(EquityIndexHedgeVO.class, eihExportFileName);

        Hashtable coStructSegments = new Hashtable();

        for (int i = 0; i < productStructures.length; i++)
        {
            long[] baseSegmentPKs = getSegmentPKsForNaturalDoc(productStructures[i]);

            coStructSegments.put(productStructures[i].getProductStructurePK() + "", baseSegmentPKs);

//            pooledExecutor.addToJobCount(baseSegmentPKs.length);
        }

        try
        {
            for (int i = 0; i < productStructures.length; i++)
            {
                long[] baseSegmentPKs = (long[]) coStructSegments.get(productStructures[i].getProductStructurePK() + "");

                for (int j = 0; j < baseSegmentPKs.length; j++)
                {
                    try
                    {
                        HedgeFundThread thread = new HedgeFundThread(productStructures[i], new Long(baseSegmentPKs[j]),
                                runDate, createSubBucketInd, eihExportWriter);
                                
//                        pooledExecutor.execute(thread);

                        thread.run();
                    }
                    catch (Exception e)
                    {
                        System.out.println(e);
                        
                        e.printStackTrace();
                        
                    }
                }
            }

//            pooledExecutor.waitUntilJobCompleted();
        }
        finally
        {
            eihExportWriter.close();
        }
    }

    public Analyzer analyzeEquityHedgeRequest(String contractNumber, EDITDate runDate, String createSubBucketInd)
    {
        Segment segment = Segment.findByContractNumber(contractNumber);
        Document equityIndexHedge = setupContractData(segment, runDate, createSubBucketInd);

        Analyzer analyzer = null;

        try
        {
            analyzer = new AnalyzerComponent();

            String eventType = segment.setEventTypeForDriverScript();

            analyzer.loadScriptAndParametersWithDocument("NaturalDocVO", equityIndexHedge, "EquityIndexHedge", "*", eventType, runDate.getFormattedDate(), segment.getProductStructureFK().longValue());
        }
        catch (Exception e)
        {
          System.out.println(e);

            e.printStackTrace();
        }

        return analyzer;
    }

    private Document setupContractData(Segment segment, EDITDate runDate, String createSubBucketInd)
    {
        long equityIndexHedgeCounter = 0;
        ProductStructure productStructure = new ProductStructure(segment.getProductStructureFK().longValue());
        Long segmentPK = segment.getSegmentPK();
        Segment currentSegment = new HedgeFundThread(productStructure, segmentPK, runDate, createSubBucketInd, null).composeSegment(segmentPK);

        EquityIndexHedgeDocument equityIndexHedgeDocument = PRASEDocumentFactory.getSingleton()
                                                                                    .getEquityIndexHedgeDocument(currentSegment,
                    productStructure);

        equityIndexHedgeDocument.buildDocument(equityIndexHedgeCounter, runDate, createSubBucketInd);

        Document currentDocument = equityIndexHedgeDocument.getDocument();

        return currentDocument;
    }



    private long[] getSegmentPKsForNaturalDoc(ProductStructure productStructure)
        throws Exception
    {
        String[] hedgeStatuses = new String[] { "Active", "ActivePendingComm", "DeathPending" };

        return new FastDAO().findBaseSegmentPKsByProductStructurePKAndSegmentStatusCT(productStructure.getProductStructurePK(),
            hedgeStatuses);
    }

    private String getExportFileName(String fileNamePrefix, String fileName)
    {
        EDITExport export1 = ServicesConfig.getEDITExport("ExportDirectory1");

        String exportFile;

        if (fileName == null)
        {
            exportFile = export1.getDirectory() + fileNamePrefix + "_" + System.currentTimeMillis() + ".xml";
        }
        else
        {
            int fileIdStart = fileName.indexOf("_");
            String fileId = fileName.substring(fileIdStart);
            exportFile = export1.getDirectory() + fileNamePrefix + "_" + fileId;
        }

        return exportFile;
    }

    private void exportVOObjectToFile(VOObject voObject, File exportFile)
        throws Exception
    {
        String parsedXML = roundDollarFields(voObject);

        parsedXML = XMLUtil.parseOutXMLDeclaration(parsedXML);     

        appendToFile(exportFile, parsedXML);
    }

    private void appendToFile(File exportFile, String data)
        throws Exception
    {
        BufferedWriter bw = new BufferedWriter(new FileWriter(exportFile, true));

        bw.write(data);

        bw.flush();

        bw.close();
    }

    private void insertStartExportFile(File exportFile, String startTag)
        throws Exception
    {
        appendToFile(exportFile, "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");

        appendToFile(exportFile, startTag);
    }

    private void insertEndExportFile(File exportFile, String endTag)
        throws Exception
    {
        appendToFile(exportFile, endTag);
    }

    private String roundDollarFields(VOObject voObject)
        throws Exception
    {
        String[] fieldNames = null;

        if (voObject instanceof EquityIndexHedgeDetailVO)
        {
            fieldNames = setupEIHFieldNamesForRounding();
        }
        else if (voObject instanceof SubBucketDetailVO)
        {
            fieldNames = setupSubBucketFieldNamesForRounding();
        }

        String voToXML = Util.roundDollarTextFields(voObject, fieldNames);

        return voToXML;
    }

    private String[] setupEIHFieldNamesForRounding()
    {
        List fieldNames = new ArrayList();

        fieldNames.add("EquityIndexHedgeDetailVO.PriorAnnivAccountValue");
        fieldNames.add("EquityIndexHedgeDetailVO.WithdrawalsSincePriorAnniv");
        fieldNames.add("EquityIndexHedgeDetailVO.CurrentAccountValue");
        fieldNames.add("EquityIndexHedgeDetailVO.CurrentMinCashSurrenderValue");
        fieldNames.add("EquityIndexHedgeDetailVO.CurrentSurrenderCharge");
        fieldNames.add("EquityIndexHedgeDetailVO.PremiumBonusAmount");
        fieldNames.add("EquityIndexHedgeDetailVO.FirstYearBonus");
        fieldNames.add("EquityIndexHedgeDetailVO.PriorYearSurrenderValue");
        fieldNames.add("EquityIndexHedgeDetailVO.CurrentMVACharge");
        fieldNames.add("EquityIndexHedgeDetailVO.GrossPremiumCollected");
        fieldNames.add("EquityIndexHedgeDetailVO.ContractSurrValGuar");
        fieldNames.add("EquityIndexHedgeDetailVO.PriorYearEndAcctValue");
        fieldNames.add("EquityIndexHedgeDetailVO.PriorYearEndMinAcctValue");
        fieldNames.add("EquityIndexHedgeDetailVO.PriorYearEndCashSurrenderValue");
        fieldNames.add("EquityIndexHedgeDetailVO.TotalMinAccountValue");
        fieldNames.add("EquityIndexHedgeDetailVO.RebalanceAmount");

        return (String[]) fieldNames.toArray(new String[fieldNames.size()]);
    }

    private String[] setupSubBucketFieldNamesForRounding()
    {
        List fieldNames = new ArrayList();

        fieldNames.add("SubBucketDetailVO.FundValue");
        fieldNames.add("SubBucketDetailVO.ChangeInFundValue");

        return (String[]) fieldNames.toArray(new String[fieldNames.size()]);
    }
}
