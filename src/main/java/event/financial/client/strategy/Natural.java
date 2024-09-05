/*
 * User: gfrosti
 * Date: Aug 13, 2003
 * Time: 1:43:57 PM
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package event.financial.client.strategy;

import codetable.PRASEDocument;
import codetable.PRASEDocumentFactory;
import codetable.ReinsuranceCheckDocument;

import contract.*;

import edit.common.CodeTableWrapper;
import edit.common.exceptions.EDITEventException;
import edit.common.vo.*;

import edit.services.logging.Logging;

import engine.UnitValues;

import engine.business.Calculator;

import engine.component.CalculatorComponent;

import engine.sp.SPException;
import engine.sp.SPOutput;
import engine.sp.custom.document.GroupSetupDocument;
import engine.sp.custom.document.PRASEDocBuilder;

import event.EDITTrx;

import event.financial.client.trx.ClientTrx;

import java.util.Set;

import logging.Log;
import logging.LogEvent;


public class Natural extends ClientStrategy
{
    public Natural(ClientTrx clientTrx)
    {
        super(clientTrx);
    }

    public Natural(ClientTrx clientTrx, String sortStatus)
    {
        super(clientTrx);
        super.setSortStatus(sortStatus);
    }

    public ClientStrategy[] execute() throws EDITEventException
    {
        ClientStrategy[] clientStrategy = null;

        Object praseDocument = buildPRASEDocument();

        EDITTrxVO editTrxVO = super.getClientTrx().getEDITTrxVO();
        GroupSetupVO groupSetupVO = getGroupSetupVO(praseDocument);
        String trxStatusInd = editTrxVO.getStatus();
        String trxEffDate = editTrxVO.getEffectiveDate();
        String transactionType = editTrxVO.getTransactionTypeCT();
        CodeTableWrapper codeTableWrapper = CodeTableWrapper.getSingleton();
        String name = codeTableWrapper.getCodeDescByCodeTableNameAndCode("TRXTYPE", transactionType);
        if (name == null)
        {
            name = codeTableWrapper.getCodeDescByCodeTableNameAndCode("DEATHTRXTYPE", transactionType);
        }

        SegmentVO segmentVO = getSegmentVO(praseDocument);


        String pendingStatus = editTrxVO.getPendingStatus();

        if (transactionType.equalsIgnoreCase("MF") || transactionType.equalsIgnoreCase("TU"))
        {
            InvestmentVO[] allInvestmentVOs = segmentVO.getInvestmentVO();

            try
            {
                boolean missing = UnitValues.areUnitValuesMissingForInvestmentsWithChargeCodes(allInvestmentVOs, editTrxVO.getEffectiveDate());

                // if we are missing some of the unit values, then it is M
                // and will wait, otherwise it is P and will execute now.
                pendingStatus = missing ? "M" : "P";
            }
            catch (Exception e)
            {
                throw new RuntimeException("Problem evaluating pending status", e);
            }
        }

        if (!pendingStatus.equalsIgnoreCase("M"))
        {
            Segment segment = new Segment(segmentVO);
            String eventType = segment.setEventTypeForDriverScript();
            long productKey = segmentVO.getProductStructureFK();

            Calculator calcComponent = new CalculatorComponent();

            SPOutput spOutput = null;

            try
            {
                if (praseDocument instanceof PRASEDocument)
                {
                  spOutput = calcComponent.processScript("ReinsuranceCheckDocVO", ((PRASEDocument)praseDocument).getDocumentAsVO(), name, trxStatusInd, eventType, trxEffDate, productKey, true);
                }
                else if (praseDocument instanceof GroupSetupDocument)
                {
                  spOutput = calcComponent.processScriptWithDocument(((PRASEDocBuilder) praseDocument), name, trxStatusInd, eventType, trxEffDate, productKey, true);
                }
            }
            catch (SPException e)
            {
            	EDITEventException editEventException = new EDITEventException(e.getMessage());
            	
            	if (!e.isLogged())
            	{
	                System.out.println(e);
	
	                e.printStackTrace();
	
	                Logging.getLogger(Logging.GENERAL_EXCEPTION).error(new LogEvent(e));
	
	                Log.logGeneralExceptionToDatabase(null, e);
	                
	                e.setLogged(true);
	                
            	}
            	
            	if (e.isLogged())
            	{
	                editEventException.setLogged(true);
            	}
            	
                ValidationVO[] validationVOs = e.getValidationVO();

                editEventException.setValidationVO(validationVOs);

                throw editEventException;
            }
            catch (Exception e) // This was a RuntimeException - not sure why.
            {
                System.out.println(e);

                e.printStackTrace();

                Logging.getLogger(Logging.GENERAL_EXCEPTION).error(new LogEvent(e));

                Log.logGeneralExceptionToDatabase(null, e);

                EDITEventException editEventException = new EDITEventException(e.getMessage());

                throw editEventException;
            }

            if (spOutput.hasCalculationOutputs())
            {
                NaturalSave naturalRedoSave = new NaturalSave();

                try
                {
                    clientStrategy = naturalRedoSave.doUpdates(spOutput, editTrxVO, super.getClientTrx().getCycleDate(), groupSetupVO, segmentVO, super.getClientTrx().getExecutionMode());
                }
                catch (EDITEventException e)
                {
                  System.out.println(e);

                  throw new EDITEventException(e.getMessage());
                }
            }
        }
        else
        {
            editTrxVO.setPendingStatus(pendingStatus);

            try
            {
                new EDITTrx(editTrxVO).save();
            }
            catch (Exception e)
            {
                System.out.println(e);

                e.printStackTrace();

                throw new RuntimeException("Problem Saving " + editTrxVO.getTransactionTypeCT() + " Transaction", e);
            }
        }

        if (praseDocument instanceof PRASEDocument)
        {
          ((PRASEDocument)praseDocument).getDocumentAsVO().clearCollections();
        }
        else if (praseDocument instanceof GroupSetupDocument)
        {
          ((GroupSetupDocument) praseDocument).clearContent();
        }

        return clientStrategy;
    }

    /**
     * Builds the appropriate PRASEDocument depending on the transactionTypeCT.
     * @return
     */
    private Object buildPRASEDocument() throws EDITEventException
    {
        Object praseDocument = null;

        if (getClientTrx().getTransactionTypeCT().equals("RCK"))
        {
            praseDocument = PRASEDocumentFactory.getSingleton().getReinsuranceCheckDocument(getClientTrx());
            ((PRASEDocument) praseDocument).buildDocument();
        }
        else
        {
            praseDocument = new GroupSetupDocument(getClientTrx().getEDITTrxVO().getEDITTrxPK());
            
            ((PRASEDocBuilder) praseDocument).build();
        }

        return praseDocument;
    }

    /**
     * Returns the GroupSetupVO element of the supplied PRASEDocument.
     * @param praseDocument
     * @return
     */
    private GroupSetupVO getGroupSetupVO(Object praseDocument)
    {
        GroupSetupVO groupSetupVO = null;

        if (praseDocument instanceof ReinsuranceCheckDocument)
        {
            ReinsuranceCheckDocument reinsuranceCheckDocument = (ReinsuranceCheckDocument) praseDocument;

            ReinsuranceCheckDocVO reinsuranceCheckDocVO = (ReinsuranceCheckDocVO) reinsuranceCheckDocument.getDocumentAsVO();

            groupSetupVO = reinsuranceCheckDocVO.getGroupSetupVO(0);
        }
        else if (praseDocument instanceof GroupSetupDocument)
        {
//            TransactionProcessDocument transactionProcessDocument = (TransactionProcessDocument) praseDocument;
//
//            NaturalDocVO naturalDocVO = (NaturalDocVO) transactionProcessDocument.getDocumentAsVO();
//
//            groupSetupVO = naturalDocVO.getGroupSetupVO(0);
            
          groupSetupVO = ((GroupSetupDocument) praseDocument).getAsGroupSetupVODocument(false);
        }

        return groupSetupVO;
    }

    /**
     * Returns the SegmentVO element of the supplied PRASEDocument.
     * @param praseDocument
     * @return
     */
    private SegmentVO getSegmentVO(Object praseDocument)
    {
        SegmentVO segmentVO = null;

        if (praseDocument instanceof ReinsuranceCheckDocument)
        {
            ReinsuranceCheckDocument reinsuranceCheckDocument = (ReinsuranceCheckDocument) praseDocument;

            ReinsuranceCheckDocVO reinsuranceCheckDocVO = (ReinsuranceCheckDocVO) reinsuranceCheckDocument.getDocumentAsVO();

            segmentVO = reinsuranceCheckDocVO.getSegmentVO(0);
        }
        else if (praseDocument instanceof GroupSetupDocument)
        {
//            TransactionProcessDocument transactionProcessDocument = (TransactionProcessDocument) praseDocument;
//
//            NaturalDocVO naturalDocVO = (NaturalDocVO) transactionProcessDocument.getDocumentAsVO();
//
//            segmentVO = naturalDocVO.getBaseSegmentVO().getSegmentVO();

          Segment segment = ((GroupSetupDocument) praseDocument).getGroupSetup().getContractSetup().getSegment();

          segmentVO = (SegmentVO)segment.getVO();
          
          // When the first edit transaction in a given request is executed, hibernate is returning proxies
          // instead of actual entity objects (supposed to return actual entities with left join fetch, do 
          // not know the reason why hibernate is returning proxies)
          // When it gets proxies, with lazy initialization it is getting all the children of segment 
          // which includes investments and the second time with the same query (building groupsetup document)
          // it is getting the actual objects, not proxies hence there was problem with execution of transactions, 
          // that needs investments.
           // only add the investment if there are none.
          if (segmentVO.getInvestmentVO().length == 0)
          {
              addInvestments(segment, segmentVO);
          }
        }

        return segmentVO;
    }
    
    /**
     * Adds investments to contract.
     * @param segment
     * @param segmentVO
     */
    private void addInvestments(Segment segment, SegmentVO segmentVO) 
    {
        Set<Investment> investments = segment.getInvestments();
        
        for (Investment investment : investments) 
        {
            InvestmentVO investmentVO = (InvestmentVO) investment.getVO();

            Set<Bucket> buckets = investment.getBuckets();
            if (!buckets.isEmpty())
            {
                for (Bucket bucket : buckets)
                {
                    BucketVO bucketVO = (BucketVO) bucket.getVO();

                    investmentVO.addBucketVO(bucketVO);
                }
            }

            segmentVO.addInvestmentVO(investmentVO);
        }
    }
}
