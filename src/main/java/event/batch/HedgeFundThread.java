/*
 * User: gfrosti
 * Date: Oct 26, 2005
 * Time: 9:20:34 AM
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

import edit.common.EDITDate;
import edit.common.EDITMap;
import edit.common.vo.EquityIndexHedgeDetailVO;
import edit.common.vo.SubBucketDetailVO;
import edit.common.vo.VOObject;

import edit.services.EditServiceLocator;
import edit.services.db.hibernate.SessionHelper;

import engine.*;

import engine.business.Calculator;

import engine.component.CalculatorComponent;

import engine.sp.SPOutput;

import fission.threading.SEGPooledExecutor;

import fission.utility.XMLReportWriter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import logging.Log;

import org.dom4j.Document;



public class HedgeFundThread implements Runnable
{
    private ProductStructure productStructure;
    private Long baseSegmentPK;
    private EDITDate runDate;
    private String createSubBucketInd;
    private XMLReportWriter eihExportWriter;
//    private SEGPooledExecutor segPooledExecutor;

    /**
     * Constructor with required context information to run the HedgeFund Extract.
     * @param productStructure
     * @param baseSegmentPK
     * @param runDate
     * @param createSubBucketInd
     * @param eihExportWriter
     * @param subBucketExportWriter
     */
    public HedgeFundThread(ProductStructure productStructure, Long baseSegmentPK, EDITDate runDate, String createSubBucketInd, XMLReportWriter eihExportWriter)
    {
        this.productStructure = productStructure;
        this.baseSegmentPK = baseSegmentPK;
        this.runDate = runDate;
        this.createSubBucketInd = createSubBucketInd;
        this.eihExportWriter = eihExportWriter;
//      this.segPooledExecutor = segPooledExecutor;
    }

    /**
     * Builds the extract.
     */
    public void run()
    {
        Calculator calcComponent = new CalculatorComponent();

        long equityIndexHedgeCounter = 0;
        
        Segment currentSegment = null;

        try
        {
            currentSegment = composeSegment(baseSegmentPK);

            EquityIndexHedgeDocument equityIndexHedgeDocument = PRASEDocumentFactory.getSingleton()
                                                                                    .getEquityIndexHedgeDocument(currentSegment,
                    productStructure);

            equityIndexHedgeDocument.buildDocument(equityIndexHedgeCounter, runDate, createSubBucketInd);

            Document currentNaturalDocument = equityIndexHedgeDocument.getDocument();

            equityIndexHedgeCounter = equityIndexHedgeDocument.getHighestEquityIndexHedgeCounter();
            String eventType = currentSegment.setEventTypeForDriverScript();

            SPOutput spOutput = calcComponent.processScriptWithDocument("NaturalDocVO", currentNaturalDocument, "EquityIndexHedge",
                    "*", eventType , runDate.getFormattedDate(),
                    productStructure.getProductStructurePK().longValue(), true);

            VOObject[] voObjects = spOutput.getSPOutputVO().getVOObject();

            List eihDetails = new ArrayList();           
            List subBuckets = new ArrayList();

            for (int o = 0; o < voObjects.length; o++)
            {
                VOObject voObject = voObjects[o];

                if (voObject instanceof EquityIndexHedgeDetailVO)
                {
                    eihDetails.add(voObject);
                }
                else if (voObject instanceof SubBucketDetailVO)
                {
                    if (createSubBucketInd.equalsIgnoreCase("Y"))
                    {
                        subBuckets.add(voObject);
                    }
                }
            }

            for (int i = 0; i < eihDetails.size(); i++)
            {
                EquityIndexHedgeDetailVO eihDetailVO = (EquityIndexHedgeDetailVO) eihDetails.get(i);

                if (subBuckets.size() > 0)
                {
                    for (int j = 0; j < subBuckets.size(); j++)
                    {
                        if (((SubBucketDetailVO) subBuckets.get(j)).getEquityIndexHedgeDetailFK() == eihDetailVO.getEquityIndexHedgeDetailPK())
                        {
                            eihDetailVO.setSubBucketDetailVO((SubBucketDetailVO[]) subBuckets.toArray(new SubBucketDetailVO[subBuckets.size()]));
                        }
                    }
                }

                eihExportWriter.writeVO(eihDetailVO);
            }

            currentNaturalDocument.clearContent();

            EditServiceLocator.getSingleton().getBatchAgent()
                              .getBatchStat(Batch.BATCH_JOB_CREATE_EQUITY_INDEX_HEDGE_EXTRACTS).updateSuccess();
        }
        catch (Exception e)
        {
            EditServiceLocator.getSingleton().getBatchAgent()
                              .getBatchStat(Batch.BATCH_JOB_CREATE_EQUITY_INDEX_HEDGE_EXTRACTS).updateFailure();

            System.out.println("Failed to process baseSegmentPK: " + baseSegmentPK + " - " + e);

            e.printStackTrace();
            
            logErrorToDatabase(e, currentSegment.getContractNumber());            
        }
        finally
        {
            SessionHelper.clearSessions();

//            segPooledExecutor.jobCompleted();

//            Thread.yield();
        }
    }

    /**
    * Composes a Segment for this job. The composed Segment consists of:
    * Payout
    * AgentHierarchy
    * AgentSnapshot
    * Investment
    * InvestmentAllocation
    * Deposits
    * ContractClient
    * Bucket
    * BucketAllocation
    * AnnualizedSubBucket (optional)
    * @param segmentPK
    * @return
    */
    public Segment composeSegment(Long segmentPK)
    {
        Segment segment = null;

        String hql = "select segment from Segment segment" + " join fetch segment.Payouts" +
            " join fetch segment.AgentHierarchies agentHierarchy" +
            " join fetch agentHierarchy.AgentSnapshots" +
            " join fetch segment.Investments investment" +
            " join fetch investment.InvestmentAllocations" +
            " join fetch segment.ContractClients contractClient" +
            " join fetch contractClient.ClientRole clientRole" +
            " join fetch investment.Buckets bucket" + // " left join fetch bucket.BucketAllocations" +
            " left join fetch bucket.AnnualizedSubBuckets" +
            " left join fetch segment.Deposits" +
            " where segment.SegmentPK = :segmentPK" +
            " and clientRole.RoleTypeCT in ('OWN', 'ANN', 'SAN')" +
            " and contractClient.OverrideStatus = 'P'";

        Map params = new HashMap();

        params.put("segmentPK", segmentPK);

        List results = SessionHelper.executeHQL(hql, params, SessionHelper.EDITSOLUTIONS);

        if (!results.isEmpty())
        {
            segment = (Segment) results.get(0);
        }

        return segment;
    }
    
    /**
     * Logs the ProcessDate, ContractNumber, and RunDate as dictated by the online log. 
     */
    private void logErrorToDatabase(Exception e, String contractNumber)
    {
        EDITMap columnInfo = new EDITMap("ProcessDate", new EDITDate().getFormattedDate());
        
        columnInfo.put("ContractNumber", contractNumber);
        
        columnInfo.put("RunDate", runDate.getFormattedDate());

        Log.logToDatabase(Log.EQUITY_INDEX_HEDGE, "Equity Index Hedge Errored: " + e.getMessage(), columnInfo);
    }    
}
