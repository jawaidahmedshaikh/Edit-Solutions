/*
 * User: dlataill
 * Date: Dec 22, 2003
 * Time: 12:29:55 PM
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package event;

import contract.Segment;
import contract.SegmentBackup;

import edit.common.EDITDate;
import edit.common.exceptions.*;
import edit.common.vo.*;

import edit.services.db.CRUD;
import edit.services.db.CRUDEntityImpl;
import edit.services.db.CRUDFactory;
import edit.services.db.ConnectionFactory;
import edit.services.db.RecursionContext;
import edit.services.db.RecursionListener;
import edit.services.db.VOClass;
import edit.portal.exceptions.*;

import engine.business.Analyzer;
import engine.business.Calculator;

import engine.component.AnalyzerComponent;
import engine.component.CalculatorComponent;

import engine.sp.*;

import event.dm.composer.VOComposer;

import event.financial.client.strategy.*;
import event.financial.client.trx.ClientTrx;
import event.financial.client.trx.EDITTrxCompare;

import fission.utility.Util;

import java.util.*;


public class QuoteImpl extends CRUDEntityImpl implements RecursionListener
{

    private EDITTrxVO[] editTrxVOs;
    private static final String NOT_TAKEN_TYPE = "NotTaken";
    private static final String FULL_SURRENDER_TYPE = "FullSurrender";

    protected void getInforceQuote(Quote quote, String quoteDate, String quoteTypeCT, long segmentPK, String operator) throws Exception
    {
        /* Check for SegmentBackup - it should have been deleted the last time a Quote was performed, but
           we are checking for it just in case the previous quote ended abnormally and the segment backup was
           never deleted. */
        SegmentBackup[] segmentBackups = SegmentBackup.findBy_SegmentPK(segmentPK);
        if (segmentBackups != null)
        {
            for (int i = 0; i < segmentBackups.length; i++)
            {
                segmentBackups[i].delete();
            }
        }
        
        try
        {
            QuoteVO quoteVO = setContractForQuote(quote,quoteDate, quoteTypeCT, segmentPK, operator);    

            SegmentVO segmentVO = quoteVO.getSegmentVO(0);
            Segment segment = new Segment(segmentVO);
            String eventType = segment.setEventTypeForDriverScript();

            Calculator calcComponent = new CalculatorComponent();

            SPOutput spOutput = calcComponent.processScript("QuoteVO",
                                                                quoteVO,
                                                                "InforceQuote",
                                                                "*",
                                                                eventType,
                                                                quoteDate,
                                                                segmentVO.getProductStructureFK(),
                                                                true);

            VOObject[] voObjects = spOutput.getSPOutputVO().getVOObject();

            // Map to hashtable
            Map vosByPK = new HashMap();

            for (int i = 0; i < voObjects.length; i++)
            {
                VOObject voObject = (VOObject) voObjects[i];

                if (voObject instanceof QuoteVO)
                {
                    vosByPK.put("quoteVO", voObject);
                }
                else if (voObject instanceof QuoteBucketVO)
                {
                    QuoteBucketVO quoteBucketVO = (QuoteBucketVO) voObject;

                    attachQuoteBucketVOsToCorrespondingQuoteInvestmentVOs(quoteVO, quoteBucketVO);
                }
                else if (voObject instanceof QuoteAdjustmentVO)
                {
                    QuoteAdjustmentVO quoteAdjustmentVO = (QuoteAdjustmentVO) voObject;

                    quoteVO.addQuoteAdjustmentVO(quoteAdjustmentVO);
                }
                else
                {
                    VOClass voClass = VOClass.getVOClassMetaData(voObject.getClass());

                    Long pk = (Long) voClass.getPKGetter().getMethod().invoke(voObject, null);

                    vosByPK.put(pk.toString(), voObject);
                }
            }

            RecursionContext recursionContext = new RecursionContext();

            recursionContext.addToMemory("vosByPK", vosByPK);

            CRUD.recurseVOObjectModel(quoteVO, null, CRUD.RECURSE_TOP_DOWN, this, recursionContext, null);

            quote.setVO(quoteVO);
        }
        catch (SPException e)
        {
            System.out.println(e);

            e.printStackTrace();

            throw e;

        }

        catch (Exception e)
        {
            System.out.println(e);

            e.printStackTrace();
            
            throw e;
        }
        finally
        {
            if (editTrxVOs != null)
            {
                SegmentBackup segmentBackup = new SegmentBackup();
                segmentBackup.restoreContract(segmentPK);
            }
        }
    }

    private QuoteVO setContractForQuote(Quote quote, String quoteDate, String quoteTypeCT, long segmentPK, String operator) throws Exception
    {
        if (quoteTypeCT.equalsIgnoreCase(NOT_TAKEN_TYPE) ||
            quoteTypeCT.equalsIgnoreCase(FULL_SURRENDER_TYPE))
        {
            editTrxVOs = getTransactionsforFS_NT_Quote(segmentPK, quoteDate);
            if (editTrxVOs != null)
            {
                //Sort editTrxVOs by EDITTrxHistoryPK in descending order.
//                editTrxVOs = sortTransactionsForFS_NT_Quote(editTrxVOs, quoteDate);
                editTrxVOs = sortTransactions(editTrxVOs);
            }
        }
        else
        {
            editTrxVOs = checkForUndoOfContract(segmentPK, quoteDate);
            if (editTrxVOs != null)
            {
                editTrxVOs = sortTransactions(editTrxVOs);
            }
        }

        QuoteVO quoteVO = null;

        try
        {
            if (editTrxVOs != null)
            {
                //Currently (06/08) the traditional product needs to undo transactions
                Segment segment = Segment.findByPK(new Long(segmentPK));
                String segmentName = segment.getSegmentNameCT();

                backupSegmentVO(segmentPK, segmentName, quoteDate);

                if (segmentName.equalsIgnoreCase(Segment.SEGMENTNAMECT_TRADITIONAL))
                {
                    undoTransactions(editTrxVOs, operator, quoteDate);
                }
                else
                {
                    undoBuckets(editTrxVOs, operator);

                    undoChargeCodes(editTrxVOs, operator);
                }
            }

            //compose quote vo
            List voInclusionList = new ArrayList();
            voInclusionList.add(ContractClientVO.class);
            voInclusionList.add(ClientRoleVO.class);
            voInclusionList.add(InvestmentVO.class);
            voInclusionList.add(InvestmentAllocationVO.class);
            voInclusionList.add(BucketVO.class);
            voInclusionList.add(BucketAllocationVO.class);
            voInclusionList.add(LifeVO.class);
            voInclusionList.add(SegmentVO.class);
            voInclusionList.add(PremiumDueVO.class);
            voInclusionList.add(CommissionPhaseVO.class);

            SegmentVO segmentVO = new contract.dm.composer.VOComposer().composeSegmentVO(segmentPK, voInclusionList);

            quoteVO = (QuoteVO) quote.getVO();

            ContractClientVO[] contractClients = segmentVO.getContractClientVO();

            //only the owner client will be put into the vo
            segmentVO.removeAllContractClientVO();

            voInclusionList.clear();
            voInclusionList.add(ClientDetailVO.class);
            voInclusionList.add(ClientAddressVO.class);

            EDITDate currentDate = new EDITDate();

            client.business.Lookup clientLookup = new client.component.LookupComponent();

            for (int i = 0; i < contractClients.length; i++)
            {
                ClientRoleVO clientRoleVO = (ClientRoleVO) contractClients[i].getParentVO(ClientRoleVO.class);

                if (clientRoleVO.getRoleTypeCT().equalsIgnoreCase("OWN"))
                {
                    //check for terminated owner don't use it if found
                    int compareValue = 0;
                    compareValue = contractClients[i].getTerminationDate().compareTo(currentDate.getFormattedDate());

                    if (compareValue >= 0)
                    {
                        ClientDetailVO clientDetailVO = clientLookup.composeClientDetailVO(clientRoleVO.getClientDetailFK(), voInclusionList);

                        clientDetailVO.addClientRoleVO(clientRoleVO);
                        segmentVO.addContractClientVO(contractClients[i]);
                        quoteVO.addClientDetailVO(clientDetailVO);
                    }
                }
                else if (clientRoleVO.getRoleTypeCT().equalsIgnoreCase("Insured") ||
                         clientRoleVO.getRoleTypeCT().equalsIgnoreCase("ANN"))
                {
                    //check for terminated insured/annuitant - don't use it if found
                    int compareValue = 0;
                    compareValue = contractClients[i].getTerminationDate().compareTo(currentDate.getFormattedDate());

                    if (compareValue >= 0)
                    {
                        ClientDetailVO clientDetailVO = clientLookup.composeClientDetailVO(clientRoleVO.getClientDetailFK(), voInclusionList);

                        clientDetailVO.addClientRoleVO(clientRoleVO);
                        segmentVO.addContractClientVO(contractClients[i]);
                        quoteVO.addClientDetailVO(clientDetailVO);
                    }
                }
            }

            InvestmentVO[] investmentVOs = segmentVO.getInvestmentVO();
            QuoteInvestmentVO[] quoteInvestmentVOs = null;

            if (investmentVOs != null)
            {
                quoteInvestmentVOs = new QuoteInvestmentVO[investmentVOs.length];

                int key = 0;

                for (int i = 0; i < investmentVOs.length; i++)
                {
                    List bucketList = new ArrayList();
                    BucketVO[] bucketVOs = investmentVOs[i].getBucketVO();
                    if (bucketVOs != null && bucketVOs.length > 0)
                    {
                        for (int j = 0; j < bucketVOs.length; j++)
                        {
                            EDITDate depositDate = new EDITDate(bucketVOs[j].getDepositDate());
                            EDITDate edQuoteDate = new EDITDate(quoteDate);
                            if (depositDate.beforeOREqual(edQuoteDate))
                            {
                                bucketList.add(bucketVOs[j]);
                            }
                        }

                        investmentVOs[i].removeAllBucketVO();

                        if (!bucketList.isEmpty())
                        {
                            investmentVOs[i].setBucketVO((BucketVO[]) bucketList.toArray(new BucketVO[bucketList.size()]));
                        }
                    }

                    key = i + 1;

                    QuoteInvestmentVO quoteInvestmentVO = new QuoteInvestmentVO();
                    quoteInvestmentVO.setQuoteInvestmentPK(key);
                    quoteInvestmentVO.setInvestmentVO(investmentVOs[i]);

                    quoteInvestmentVOs[i] = quoteInvestmentVO;
                }
            }

            quoteVO.setQuoteDate(quoteDate);
            quoteVO.setQuoteTypeCT(quoteTypeCT);
            quoteVO.addSegmentVO(segmentVO);

            //Of the candidate transactions only place PY transactions in the QuoteVO.
            List pyTrxList = new ArrayList();
            if (editTrxVOs != null)
            {
                for (int i = 0; i < editTrxVOs.length; i++)
                {
                    if (editTrxVOs[i].getTransactionTypeCT().equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_PREMIUM))
                    {
                        pyTrxList.add(editTrxVOs[i]);
                    }
                }
            }

            EDITTrxVO[] pyEditTrxVOs = null;
            if (!pyTrxList.isEmpty())
            {
                pyEditTrxVOs = (EDITTrxVO[])pyTrxList.toArray(new EDITTrxVO[pyTrxList.size()]);
                quoteVO.setEDITTrxVO(pyEditTrxVOs);
            }

            if (quoteInvestmentVOs != null)
            {
                quoteVO.setQuoteInvestmentVO(quoteInvestmentVOs);
            }

// removed this execution of script processing becasue anaylzer was now getting executed twice thru scripts - CG 06/08
//            Segment segment = new Segment(segmentVO);
//            String eventType = segment.setEventTypeForDriverScript();
//
//            Calculator calcComponent = new CalculatorComponent();
//
//            SPOutput spOutput = calcComponent.processScript("QuoteVO",
//                                                            quoteVO,
//                                                            "InforceQuote",
//                                                            "*",
//                                                            eventType,
//                                                            quoteDate,
//                                                            segmentVO.getProductStructureFK(),
//                                                            true);
//
//            VOObject[] voObjects = spOutput.getSPOutputVO().getVOObject();
//
//            // Map to hashtable
//            Map vosByPK = new HashMap();
//
//            for (int i = 0; i < voObjects.length; i++)
//            {
//                VOObject voObject = (VOObject) voObjects[i];
//
//                if (voObject instanceof QuoteVO)
//                {
//                    vosByPK.put("quoteVO", voObject);
//                }
//                else if (voObject instanceof QuoteBucketVO)
//                {
//                    QuoteBucketVO quoteBucketVO = (QuoteBucketVO) voObject;
//
//                    attachQuoteBucketVOsToCorrespondingQuoteInvestmentVOs(quoteVO, quoteBucketVO);
//                }
//                else if (voObject instanceof QuoteAdjustmentVO)
//                {
//                    QuoteAdjustmentVO quoteAdjustmentVO = (QuoteAdjustmentVO) voObject;
//
//                    quoteVO.addQuoteAdjustmentVO(quoteAdjustmentVO);
//                }
//                else
//                {
//                    VOClass voClass = VOClass.getVOClassMetaData(voObject.getClass());
//
//                    Long pk = (Long) voClass.getPKGetter().getMethod().invoke(voObject, null);
//
//                    vosByPK.put(pk.toString(), voObject);
//                }
//            }
//
//            RecursionContext recursionContext = new RecursionContext();
//
//            recursionContext.addToMemory("vosByPK", vosByPK);
//
//            CRUD.recurseVOObjectModel(quoteVO, null, CRUD.RECURSE_TOP_DOWN, this, recursionContext, null);
//
//            quote.setVO(quoteVO);
/////////////////////////////////////////////////
        }
        catch (Exception e)
        {
          System.out.println(e);

            e.printStackTrace();
        }

        return quoteVO;
    }


    /**
     * For the Traditional product the transactions must be undone to calculate the Premium Refund.
     * As much of the current undo process is utilized as possible.
     * @param editTrxVOs
     * @param operator
     */
    private void undoTransactions(EDITTrxVO[] editTrxVOs, String operator, String quoteDate) throws Exception
    {
        try
        {
            for (int i = 0; i < editTrxVOs.length; i++)
            {
                ClientTrx clientTrx = new ClientTrx(editTrxVOs[i], operator);
                clientTrx.setCycleDate(quoteDate);
                //The undo process creates new various table rows which were not in the backup.  This boolean
                //allows bypass of database updates for new rows only.  Existing data still gets updated.
                Undo undo = new Undo(clientTrx, true);

                undo.execute();
            }
        }
        catch (Exception e)
        {
            System.out.println(e);

            e.printStackTrace();

            throw e;
        }
    }


    /**
     *
     * @param editTrxVOs
     * @param operator
     */
    private void undoChargeCodes(EDITTrxVO[] editTrxVOs, String operator)
    {
        for (int i = 0; i < editTrxVOs.length; i++)
        {
            EDITTrxVO editTrxVO = editTrxVOs[i];

            ClientTrx clientTrx = new ClientTrx(editTrxVO, operator);

            clientTrx.undoChargeCodes();
        }
    }

    /**
     * Sorts the transactions in descending EDITTrxHistoryPK order (to reverse last transaction processed first).
     * @param editTrxVOs
     * @see EDITTrxCompare#compare(edit.common.vo.EDITTrxVO, edit.common.vo.EDITTrxVO)
     * @return
     */
    private EDITTrxVO[] sortTransactions(EDITTrxVO[] editTrxVOs)
    {
        int i = 0;

        Hashtable editTrxHT = new Hashtable();
        for (i = 0; i < editTrxVOs.length; i++)
        {
            editTrxHT.put(editTrxVOs[i].getEDITTrxPK() + "", editTrxVOs[i]);
        }

        List editTrxHistoryVOList = new ArrayList();
        for (i = 0; i < editTrxVOs.length; i++)
        {
            EDITTrxHistoryVO[] editTrxHistoryVOs = editTrxVOs[i].getEDITTrxHistoryVO();
            for (int j = 0; j < editTrxHistoryVOs.length; j++)
            {
                editTrxHistoryVOList.add(editTrxHistoryVOs[j]);
            }
        }

        EDITTrxHistoryVO[] editTrxHistoryVOs = (EDITTrxHistoryVO[]) editTrxHistoryVOList.toArray(new EDITTrxHistoryVO[editTrxHistoryVOList.size()]);

        editTrxHistoryVOs = (EDITTrxHistoryVO[]) Util.sortObjects(editTrxHistoryVOs, new String[] {"getEDITTrxHistoryPK"});

        List editTrxVOList = new ArrayList();

        for (i = editTrxHistoryVOs.length - 1; i > -1; i--)
        {
            long editTrxFK = ((EDITTrxHistoryVO) editTrxHistoryVOs[i]).getEDITTrxFK();
            editTrxVOList.add(editTrxHT.get(editTrxFK + ""));
        }

        editTrxVOs = (EDITTrxVO[]) editTrxVOList.toArray(new EDITTrxVO[editTrxVOList.size()]);

        return editTrxVOs;
    }


    private EDITTrxVO[] sortTransactionsForFS_NT_Quote(EDITTrxVO[] editTrxVOs, String quoteDate)
    {
        List clientTrxs = new ArrayList();
        for (int i = 0; i < editTrxVOs.length; i++)
        {
            ClientTrx currentClientTrx = new ClientTrx(editTrxVOs[i], "System");

            currentClientTrx.setExecutionMode(ClientTrx.REALTIME_MODE);

            currentClientTrx.setCycleDate(quoteDate);

            clientTrxs.add(currentClientTrx);
        }

        Collections.sort(clientTrxs);

        List editTrxList = new ArrayList();
        for (int i = 0; i < clientTrxs.size(); i++)
        {
            editTrxList.add(((ClientTrx)clientTrxs.get(i)).getEDITTrxVO());
        }

        return (EDITTrxVO[]) editTrxList.toArray(new EDITTrxVO[clientTrxs.size()]);
    }

    protected void getNewBusinessQuote(Quote quote) throws Exception
    {
        QuoteVO quoteVO = (QuoteVO) quote.getVO();

// New Business Quote needs all clients for possible joint processing
//        SegmentVO[] segmentVO = quoteVO.getSegmentVO();
//
//        ContractClientVO[] contractClients = segmentVO[0].getContractClientVO();
//
//        //only the annuitant client will be put into the vo
//        segmentVO[0].removeAllContractClientVO();
//        quoteVO.removeAllClientDetailVO();
//
//        List voInclusionList = new ArrayList();
//        voInclusionList.clear();
//        voInclusionList.add(ClientDetailVO.class);
//        voInclusionList.add(ClientAddressVO.class);
//        voInclusionList.add(PreferenceVO.class);
//
//        for (int c = 0; c < contractClients.length; c++)
//        {
//            long clientRoleFK = contractClients[c].getClientRoleFK();
//            ClientRoleVO clientRoleVO = new ClientRoleComposer(voInclusionList).compose(clientRoleFK);
//
//            if (clientRoleVO != null)
//            {
//                if (clientRoleVO.getRoleTypeCT().equalsIgnoreCase("ANN"))
//                {
//                    segmentVO[0].addContractClientVO(contractClients[c]);
//                    ClientDetailVO clientDetailVO = (ClientDetailVO)clientRoleVO.getParentVO(ClientDetailVO.class);
//                    clientDetailVO.addClientRoleVO(clientRoleVO);
//                    quoteVO.addClientDetailVO(clientDetailVO);
//                }
//            }
//        }

        quoteVO = performNewBusinessQuote(quoteVO);

        quote.setVOOut(quoteVO);
    }

    /**
     * Performs new business quote.
     * @param quoteVO populated with input information
     * @return QuoteVO resluts populated
     * @throws Exception
     */
    public QuoteVO performNewBusinessQuote(QuoteVO quoteVO) throws Exception
    {
        SegmentVO segmentVO = quoteVO.getSegmentVO()[0];

        try
        {
            Segment segment = new Segment(segmentVO);
            String eventType = segment.setEventTypeForDriverScript();

            Calculator calcComponent = new CalculatorComponent();

            SPOutput spOutput = calcComponent.processScript("QuoteVO", quoteVO, "Quote", "*", eventType, segmentVO.getEffectiveDate(), segmentVO.getProductStructureFK(), true);

            VOObject[] voObjects = spOutput.getSPOutputVO().getVOObject();

            // Map to hashtable
            Map vosByPK = new HashMap();

            for (int i = 0; i < voObjects.length; i++)
            {
                VOObject voObject = (VOObject) voObjects[i];

                if (voObject instanceof PayoutVO)
                {
                    vosByPK.put("payoutVO", voObject);
                }
                else if (voObject instanceof SegmentVO)
                {
                    vosByPK.put("segmentVO", voObject);
                }
                else
                {
                    VOClass voClass = VOClass.getVOClassMetaData(voObject.getClass());

                    Long pk = (Long) voClass.getPKGetter().getMethod().invoke(voObject, null);

                    vosByPK.put(pk.toString(), voObject);
                }
            }

            RecursionContext recursionContext = new RecursionContext();

            recursionContext.addToMemory("vosByPK", vosByPK);

            CRUD.recurseVOObjectModel(quoteVO, null, CRUD.RECURSE_TOP_DOWN, this, recursionContext, null);

            return quoteVO;
        }
        catch (Exception e)
        {
          System.out.println(e);

            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.

            throw e;
        }
    }

    private void backupSegmentVO(long segmentPK, String segmentName, String quoteDate) throws Exception
    {
        SegmentBackup segmentBackup = new SegmentBackup();

        segmentBackup.backupContract(segmentPK, segmentName, quoteDate);
    }

    private EDITTrxVO[] checkForUndoOfContract(long segmentPK, String quoteDate) throws Exception
    {
        List voInclusionList = new ArrayList();
        voInclusionList.add(EDITTrxHistoryVO.class);
        voInclusionList.add(FinancialHistoryVO.class);
        voInclusionList.add(BucketHistoryVO.class);

        EDITTrxVO[] editTrxVOs = new VOComposer().composeEDITTrxVOBySegmentPK_AND_PendingStatus_AND_Date(segmentPK, quoteDate, voInclusionList);

        return editTrxVOs;
    }

    private EDITTrxVO[] getTransactionsforFS_NT_Quote(long segmentPK, String quoteDate) throws Exception
    {
        List voInclusionList = new ArrayList();
        voInclusionList.add(EDITTrxHistoryVO.class);
        voInclusionList.add(FinancialHistoryVO.class);
        voInclusionList.add(BucketHistoryVO.class);

        EDITTrxVO[] editTrxVOs = new VOComposer().composeEDITTrxVOBySegmentPendingStatusAndEffDate(segmentPK, new String[]{"H", "L"},quoteDate, voInclusionList );

        return editTrxVOs;
    }
    private void undoBuckets(EDITTrxVO[] editTrxVOs, String operator) throws Exception
    {
        CRUD crud = CRUDFactory.getSingleton().getCRUD(ConnectionFactory.EDITSOLUTIONS_POOL);

        try
        {
            for (int i = 0; i < editTrxVOs.length; i++)
            {
                ClientTrx clientTrx = new ClientTrx(editTrxVOs[i], operator);
                Reversal reversal = new Reversal(clientTrx);

                EDITTrxHistoryVO editTrxHistoryVO = editTrxVOs[i].getEDITTrxHistoryVO(0);

                BucketHistoryVO[] bucketHistoryVOs = editTrxHistoryVO.getBucketHistoryVO();

                for (int j = 0; j < bucketHistoryVOs.length; j++)
                {
                    String fundType = reversal.getFundTypeForBucket(bucketHistoryVOs[j].getBucketFK());

                    String toFromStatus = bucketHistoryVOs[j].getToFromStatus();

                    reversal.updateBucketVO(bucketHistoryVOs[j], fundType, editTrxVOs[i].getTransactionTypeCT(), toFromStatus, crud);
                }
            }
        }
        catch (Exception e)
        {
            System.out.println(e);

            e.printStackTrace();

            throw e;
        }
        finally
        {
            if (crud != null)
                crud.close();

            crud = null;
        }
    }

    /**
     * Adds newly created (via script) QuoteBucketVOs to corresponding QuoteVO.QuoteInvestmentVO.
     * @param quoteVO
     * @param quoteBucketVO
     */
    private void attachQuoteBucketVOsToCorrespondingQuoteInvestmentVOs(QuoteVO quoteVO, QuoteBucketVO quoteBucketVO)
    {
        long bucketFK = quoteBucketVO.getBucketFK();

        QuoteInvestmentVO[] quoteInvestmentVOs = quoteVO.getQuoteInvestmentVO();

        loopQuoteInvestments:
        for (int j = 0; j < quoteInvestmentVOs.length; j++)
        {
            QuoteInvestmentVO quoteInvestmentVO = quoteInvestmentVOs[j];

            InvestmentVO investmentVO = quoteInvestmentVO.getInvestmentVO();

            BucketVO[] bucketVOs = investmentVO.getBucketVO();

            for (int k = 0; k < bucketVOs.length; k++)
            {
                BucketVO bucketVO = bucketVOs[k];

                long bucketPK = bucketVO.getBucketPK();

                if (bucketPK == bucketFK)
                {
                    quoteInvestmentVO.addQuoteBucketVO(quoteBucketVO);

                    break loopQuoteInvestments;
                }
            }
        }
    }
    
    public void currentNode(Object currentNode, Object parentNode, RecursionContext recursionContext)
    {
        Map vosByPK = (Map) recursionContext.getFromMemory("vosByPK");

        VOObject voObject = (VOObject) currentNode;

        VOClass voClass = VOClass.getVOClassMetaData(voObject.getClass());

        if (voObject instanceof QuoteVO)
        {
            if (vosByPK.containsKey("quoteVO"))
            {
                VOObject voFromOutput = (VOObject) vosByPK.get("quoteVO");

                voObject.copyFrom(voFromOutput);

                vosByPK.remove("quoteVO");
            }
        }
        else if (voObject instanceof SegmentVO)
        {
            if (vosByPK.containsKey("segmentVO"))
            {
                VOObject voFromOutput = (VOObject) vosByPK.get("segmentVO");

                voObject.copyFrom(voFromOutput);

                vosByPK.remove("segmentVO");
            }
        }
        else if (voObject instanceof PayoutVO)
        {
            if (vosByPK.containsKey("payoutVO"))
            {
                VOObject voFromOutput = (VOObject) vosByPK.get("payoutVO");

                voObject.copyFrom(voFromOutput);

                vosByPK.remove("payoutVO");
            }
        }
        else if (!(voObject instanceof QuoteAdjustmentVO))
        {
            try
            {
                Long pk = (Long) voClass.getPKGetter().getMethod().invoke(voObject, null);

                if (vosByPK.containsKey(pk.toString()))
                {
                    VOObject voFromOutput = (VOObject) vosByPK.get(pk.toString());

                    voObject.copyFrom(voFromOutput);

                    vosByPK.remove(pk.toString());
                }
            }
            catch (Exception e)
            {
                System.out.println(e);

                e.printStackTrace(); //To change body of catch statement use Options | File Templates.

                throw new RuntimeException(e);
            }
        }

        if (vosByPK.isEmpty())
        {
            recursionContext.setShouldContinueRecursion(false);
        }
    }

    public Analyzer analyzeInforceQuote(Quote quote, String quoteDate, String quoteTypeCT, long segmentPK, String operator) throws Exception
    {
        QuoteVO quoteVO = setContractForQuote(quote,quoteDate, quoteTypeCT, segmentPK, operator);
        Analyzer analyzer = null;

        try
        {
            SegmentVO segmentVO = quoteVO.getSegmentVO(0);
            Segment segment = new Segment(segmentVO);
            String eventType = segment.setEventTypeForDriverScript();

            analyzer = new AnalyzerComponent();
            // Parameter isAnalyzeTrnasaction - when not analyzing transaction the value is false.
            analyzer.loadScriptAndParameters("QuoteVO", quoteVO, "InforceQuote", "*", eventType, quoteDate, segmentVO.getProductStructureFK(), false);
        }
        catch (Exception e)
        {
          System.out.println(e);

            e.printStackTrace();
        }
        finally
        {
            if (editTrxVOs != null)
            {
                SegmentBackup segmentBackup = new SegmentBackup();
                segmentBackup.restoreContract(segmentPK);
            }
        }

        return analyzer;
    }
}
