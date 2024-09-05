package event.financial.client.trx;

import edit.common.vo.ContractSetupVO;
import edit.common.vo.EDITTrxVO;
import edit.common.vo.OverdueChargeVO;
import edit.services.db.CRUD;
import edit.services.db.CRUDFactory;
import edit.services.db.ConnectionFactory;
import edit.common.EDITDate;
import edit.common.EDITDateTime;
import edit.common.exceptions.*;
import event.ContractSetup;
import event.EDITTrx;
import event.dm.dao.DAOFactory;
import event.financial.client.strategy.*;
import event.common.TransactionPriorityCache;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import contract.Segment;
import engine.ProductStructure;
import fission.utility.Util;

/**
 * Created by IntelliJ IDEA.
 * User: gfrosti
 * Date: Aug 14, 2003
 * Time: 2:53:30 PM
 * (c) 2000 - 2005 Systems Engineering Group, LLC.  All rights Reserved
 * Systems Engineering Group, LLC Propietary and confidential.  Any use
 * subject to the license agreement.
 */
public class StrategyChain
{
    private ClientTrx drivingClientTrx;

    private List undoTrxList;

    public StrategyChain(ClientTrx clientTrx)
    {
        this.drivingClientTrx = clientTrx;
    }

    public ClientTrx getDrivingClientTrx()
    {
        return drivingClientTrx;
    }

    public List buildNaturalStrategyChain() throws EDITEventException, Exception
    {
        undoTrxList = new ArrayList();
        List strategyChain = new ArrayList();

        EDITTrxVO editTrxVO = drivingClientTrx.getEDITTrxVO();

        String cycleDate = drivingClientTrx.getCycleDate();

        if (drivingClientTrx.isBackdated() && !editTrxVO.getTransactionTypeCT().equals("CK") && 
        		!editTrxVO.getTransactionTypeCT().equals("RCK") && !editTrxVO.getTransactionTypeCT().equals("ADC")) // Don't CheckTransactions are different 'CK' and 'RCK'.
        {
            ContractSetupVO[] contractSetupVO = DAOFactory.getContractSetupDAO().findByEDITTrxPK(editTrxVO.getEDITTrxPK());

            long segmentPK = contractSetupVO[0].getSegmentFK();

            Segment segment = Segment.findByPK(segmentPK);
            ProductStructure productStructure = ProductStructure.findByPK(segment.getProductStructureFK().longValue());

            String productType = Util.initString(productStructure.getProductTypeCT(), ProductStructure.VARIABLE_PRODUCT);

            int executionMode = drivingClientTrx.getExecutionMode();

            EDITTrxVO[] allEDITTrxVO = DAOFactory.getEDITTrxDAO().findAllBySegmentPK_AND_EffectiveDate_GTE_AND_LTE(segmentPK, editTrxVO.getEffectiveDate(), cycleDate, executionMode, productType);

            //only go through undo/redo if more than target trx
            if (allEDITTrxVO != null && allEDITTrxVO.length > 0)
            {
                List undo = buildUndoTrx(allEDITTrxVO, editTrxVO.getEDITTrxPK(), editTrxVO.getTransactionTypeCT(), editTrxVO.getEffectiveDate(), editTrxVO.getSequenceNumber());
                List redo = buildRedoTrx(allEDITTrxVO,
                                         editTrxVO.getEDITTrxPK(),
                                         editTrxVO.getTransactionTypeCT(),
                                         editTrxVO.getEffectiveDate(),
                                         editTrxVO.getSequenceNumber(),
                                         undo);

                Collections.sort(redo); // order redo transactions before saving to database.

                saveRedoTrxToDatabase(redo, editTrxVO.getEDITTrxPK());

                List natural = buildNaturalTrx(allEDITTrxVO);

                Collections.sort(undo);
                Collections.reverse(undo); // order = descending

                natural.addAll(redo); // combine natual and redo
                Collections.sort(natural); // order = ascending

                strategyChain.addAll(undo); // Undos will go first
                strategyChain.addAll(natural); // Naturals and Redos will follow.
            }
            else
            {
                strategyChain = buildNaturalTrx(new EDITTrxVO[]{drivingClientTrx.getEDITTrxVO()});
            }
        }
        else
        {
            strategyChain = buildNaturalTrx(new EDITTrxVO[]{drivingClientTrx.getEDITTrxVO()});
        }

        return strategyChain;
    }

    public List buildReversalStrategyChain(String reversalReasonCode) throws Exception
    {
        undoTrxList = new ArrayList();
        List strategyChain = new ArrayList();

        drivingClientTrx.getEDITTrxVO().setReversalReasonCodeCT(reversalReasonCode);

        EDITTrxVO editTrxVO = drivingClientTrx.getEDITTrxVO();
        long editTrxPK = editTrxVO.getEDITTrxPK();

        ContractSetupVO[] contractSetupVO = DAOFactory.getContractSetupDAO().findByEDITTrxPK(editTrxVO.getEDITTrxPK());

        long segmentPK = contractSetupVO[0].getSegmentFK();

        EDITTrxVO[] allEDITTrxVO = DAOFactory.getEDITTrxDAO().findBySegmentPK_AND_EffectiveDateGT_AND_NotTransactionType(segmentPK, editTrxVO.getEffectiveDate(), "ADC");

        if (editTrxVO != null)
        {
            Reversal reversal = new Reversal(drivingClientTrx, "undo");

        	if (!editTrxVO.getTransactionTypeCT().equalsIgnoreCase("ADC")) {
	
            List undo = buildUndoTrx(allEDITTrxVO, editTrxPK, editTrxVO.getTransactionTypeCT(), editTrxVO.getEffectiveDate(), editTrxVO.getSequenceNumber());
            List redo = buildRedoTrx(allEDITTrxVO, editTrxPK, editTrxVO.getTransactionTypeCT(), editTrxVO.getEffectiveDate(), editTrxVO.getSequenceNumber(), undo);

            //If any of the undo transactions were spawned from the reversal trx, it should be a Reversal ('R') not a an Unod ('U')
            for (int i = 0; i < undo.size(); i++)
            {
                EDITTrxVO undoTrx = ((Undo) undo.get(i)).getClientTrx().getEDITTrxVO();
                if (undoTrx.getOriginatingTrxFK() == editTrxVO.getEDITTrxPK())
                {
                    undoTrx.setStatus("R");
                    undo.set(i, new Reversal(new ClientTrx(undoTrx), "undo"));
                }
            }

            undo.add(reversal);

            List updatedRedo = new ArrayList();

            //If any of the redo transactions were spawned from the reversal trx, there should not be a reapply ('A'
            //The transaction will be reversed, not undone
            for (int i = 0; i < redo.size(); i++)
            {
                EDITTrxVO redoTrx = ((Redo) redo.get(i)).getClientTrx().getEDITTrxVO();
                if (redoTrx.getOriginatingTrxFK() != editTrxVO.getEDITTrxPK())
                {
                    updatedRedo.add(redo.get(i));
                }
            }

            Collections.sort(undo);
            Collections.sort(updatedRedo);

            Collections.reverse(undo); // order = descending

            saveRedoTrxToDatabase(updatedRedo, editTrxVO.getEDITTrxPK());

            strategyChain.addAll(undo);
            strategyChain.addAll(updatedRedo);
            
	        } else {
	        	strategyChain.add(reversal);
	        }
        }

        return strategyChain;
    }

    public List buildBatchReversalStrategyChain(String reversalReasonCode, List<Long> pksToReverse, boolean isSubmitReversalChain, EDITTrxVO earliestPYToRedo) throws Exception
    {
        undoTrxList = new ArrayList();
        List strategyChain = new ArrayList();

        drivingClientTrx.getEDITTrxVO().setReversalReasonCodeCT(reversalReasonCode);

        EDITTrxVO editTrxVO = drivingClientTrx.getEDITTrxVO();
        long editTrxPK = editTrxVO.getEDITTrxPK();
        
        String operator = drivingClientTrx.getOperator();

        ContractSetupVO[] contractSetupVO = DAOFactory.getContractSetupDAO().findByEDITTrxPK(editTrxVO.getEDITTrxPK());

        long segmentPK = contractSetupVO[0].getSegmentFK();
        
        String effectiveDate = editTrxVO.getEffectiveDate();
        String transactionType = editTrxVO.getTransactionTypeCT();
        int sequenceNumber = editTrxVO.getSequenceNumber();
        Long undoRedoKey = null;
        
        if (earliestPYToRedo != null) {
        	effectiveDate = earliestPYToRedo.getEffectiveDate();
            transactionType = earliestPYToRedo.getTransactionTypeCT();
            sequenceNumber = earliestPYToRedo.getSequenceNumber();
            undoRedoKey = earliestPYToRedo.getEDITTrxPK();
        }
        
        EDITTrxVO[] allEDITTrxVO = DAOFactory.getEDITTrxDAO().findBySegmentPK_AND_EffectiveDateGT_AND_NotTransactionType_IncludeTs(
        		segmentPK, effectiveDate, "ADC");

        if (pksToReverse != null && pksToReverse.size() > 0) {
        	
            List undo = buildBatchUndoTrx(allEDITTrxVO, reversalReasonCode, pksToReverse, editTrxPK, transactionType, 
            		effectiveDate, sequenceNumber, isSubmitReversalChain, undoRedoKey, operator);
            
            EDITTrxVO[] undoArray = null;
            if (undo != null && undo.size() > 0) {
            	undoArray = new EDITTrxVO[undo.size()];
            	int index = 0;
            	for (int x = 0; x < undo.size(); x++) {
            		if (undo.get(x) instanceof Undo) {
            			undoArray[index] = ((Undo) undo.get(x)).getClientTrx().getEDITTrxVO();
            			index++;
            		}
            	}
            } else {
            	undoArray = new EDITTrxVO[0];
            }
            	            
            List redo = buildRedoTrx(undoArray, editTrxPK, transactionType, effectiveDate, sequenceNumber, undo);

            // If any of the undo transactions were spawned from the reversal trx, it should be a Reversal ('R') not a an Undo ('U')
            for (int i = 0; i < undo.size(); i++) {	
            	if (undo.get(i) instanceof Undo) {
	                EDITTrxVO undoTrx = ((Undo) undo.get(i)).getClientTrx().getEDITTrxVO();
	                if (pksToReverse.contains(undoTrx.getOriginatingTrxFK())) {
	                    undoTrx.setStatus("R");
	                    undo.set(i, new Reversal(new ClientTrx(undoTrx), "undo"));
	                }
	            }
            }
	
            List updatedRedo = new ArrayList();

            //If any of the redo transactions were spawned from the reversal trx, there should not be a reapply ('A'
            //The transaction will be reversed, not undone
            for (int i = 0; i < redo.size(); i++) {
                EDITTrxVO redoTrx = ((Redo) redo.get(i)).getClientTrx().getEDITTrxVO();
                if (!pksToReverse.contains(redoTrx.getOriginatingTrxFK())) {
                    updatedRedo.add(redo.get(i));
                }
            }

            Collections.sort(undo);
            Collections.sort(updatedRedo);

            Collections.reverse(undo); // order = descending

            saveRedoTrxToDatabase(updatedRedo, editTrxVO.getEDITTrxPK());

            strategyChain.addAll(undo);
            strategyChain.addAll(updatedRedo);
        }

        return strategyChain;
    }
    
    private List buildUndoTrx(EDITTrxVO[] candidateEDITTrxVO, long editTrxPK, String transactionType, String effectiveDate, int sequenceNumber) throws EDITEventException
    {
        //  The candidate trxs consist of only those whose effectiveDates are >= the effectiveDate on the trx being processed
        //  (seems to be done via the db lookup before this call)
        //  If the candidate's effectiveDate > effectiveDate of the trx being processed, undo
        //  If they are equal, compare the priorities - if the priority is less, undo

        TransactionPriorityCache transactionPriorityCache = TransactionPriorityCache.getInstance();

        int priority = transactionPriorityCache.getPriority(transactionType);

        List undo = new ArrayList();

        if (candidateEDITTrxVO != null)
        {
            for (int i = 0; i < candidateEDITTrxVO.length; i++)
            {
                int candidatePriority = transactionPriorityCache.getPriority(candidateEDITTrxVO[i].getTransactionTypeCT());

                String pendingStatus = candidateEDITTrxVO[i].getPendingStatus();
                String status = candidateEDITTrxVO[i].getStatus();

                if ((((pendingStatus.equalsIgnoreCase("H") || pendingStatus.equalsIgnoreCase("L") ||
                      pendingStatus.equalsIgnoreCase("S") || pendingStatus.equalsIgnoreCase("B") ||
                      pendingStatus.equalsIgnoreCase("F") || pendingStatus.equalsIgnoreCase("O")) &&
                     (status.equalsIgnoreCase("N") || status.equalsIgnoreCase("A")) &&
                     candidateEDITTrxVO[i].getEDITTrxPK() != editTrxPK) ||
                     (status.equalsIgnoreCase("U") && pendingStatus.equalsIgnoreCase("P"))) ||
                     pendingStatus.equalsIgnoreCase("C"))
                {
                    //We always want to create the undo for transactions equal to driving effective date if
                    //they have a pending status of "C" (they need to be undone/redone to recalculate the value of
                    //the transaction correctly
                    if (candidateEDITTrxVO[i].getEffectiveDate().equals(effectiveDate) &&
                        !pendingStatus.equalsIgnoreCase("C"))
                    {
                        if (candidatePriority > priority ||
                            (candidatePriority == priority && candidateEDITTrxVO[i].getSequenceNumber() > sequenceNumber))
                        {
                        	if (candidateEDITTrxVO[i].getOperator() != null && candidateEDITTrxVO[i].getOperator().equalsIgnoreCase("Conversion")) {
                        		
                        		String errorMessage = "Processing this transaction will cause a conversion " + candidateEDITTrxVO[i].getTransactionTypeCT() + 
                						" trx to be undone.  Undo/redo of conversion transactions is not supported.";
                        		throw new EDITEventException(errorMessage);
                        		
                        	} else {
	                            undo.add(buildUndo(candidateEDITTrxVO[i]));
	                            undoTrxList.add(candidateEDITTrxVO[i].getEDITTrxPK() + "");
                        	}
                        }
                    }
                    else
                    {
                    	if (candidateEDITTrxVO[i].getOperator() != null && candidateEDITTrxVO[i].getOperator().equalsIgnoreCase("Conversion")) {
                    		
                    		String errorMessage = "Processing this transaction will cause a conversion " + candidateEDITTrxVO[i].getTransactionTypeCT() + 
            						" trx to be undone.  Undo/redo of conversion transactions is not supported.";
                    		throw new EDITEventException(errorMessage);
                    		
                    	} else {
                    		undo.add(buildUndo(candidateEDITTrxVO[i]));
                    		undoTrxList.add(candidateEDITTrxVO[i].getEDITTrxPK() + "");
                    	}
                    }
                }
            }
        }

        return undo;
    }

    private List buildBatchUndoTrx(EDITTrxVO[] candidateEDITTrxVO, String reversalReasonCode, List<Long> pksToReverse, long editTrxPK, 
    		String transactionType, String effectiveDate, int sequenceNumber, boolean isSubmitReversalChain, Long undoRedoKey, String operator) throws EDITEventException
    {
        // The candidate trxs consist of only those whose effectiveDates are >= the effectiveDate on the earliest trx being processed in the batch
        // If the candidate's effectiveDate > effectiveDate of the trx being processed, undo
        // If effective dates are equal, compare the priorities/sequences - if the priority/sequence is more, undo

        TransactionPriorityCache transactionPriorityCache = TransactionPriorityCache.getInstance();

        int priority = transactionPriorityCache.getPriority(transactionType);

        List undo = new ArrayList();

        if (candidateEDITTrxVO != null)
        {
            CRUD crud = null;

            try {
            	
	            for (int i = 0; i < candidateEDITTrxVO.length; i++) {
	            	
	            	//System.out.print("STRAT CHAIN BUILD: " + candidateEDITTrxVO[i].getTransactionTypeCT() + " - " + candidateEDITTrxVO[i].getEffectiveDate() +
	            	//		" - S/PS: " + candidateEDITTrxVO[i].getStatus() + "/" + candidateEDITTrxVO[i].getPendingStatus());

	            	if (isSubmitReversalChain && pksToReverse.contains(candidateEDITTrxVO[i].getTerminationTrxFK()) && 
	            			!pksToReverse.contains(candidateEDITTrxVO[i].getOriginatingTrxFK()) &&
	            			(new EDITDate(candidateEDITTrxVO[i].getEffectiveDate())).beforeOREqual(new EDITDate()) &&
	            			!(pksToReverse.contains(candidateEDITTrxVO[i].getEDITTrxPK()) && candidateEDITTrxVO[i].getPendingStatus().equalsIgnoreCase("H"))) {
	            		// when we reverse the SB, there should be no other active transactions on the contract... no exceptions
	            		// isSubmitReversalChain lets us know we should delete trx that may turn to pending when terminating trx is reversed
	            		// set current/past-dated T-status trx associated by their terminationTrxFK to D if they aren't in the reversal chain
	            		if (crud == null) {
	                        crud = CRUDFactory.getSingleton().getCRUD(ConnectionFactory.EDITSOLUTIONS_POOL);
	            		}
	            		
            			candidateEDITTrxVO[i].setPendingStatus("D");
            			candidateEDITTrxVO[i].setMaintDateTime(new EDITDateTime().getFormattedDateTime());
	            		candidateEDITTrxVO[i].setReversalReasonCodeCT(reversalReasonCode);
	            		candidateEDITTrxVO[i].setOperator(operator);

            			crud.createOrUpdateVOInDB(candidateEDITTrxVO[i]);
	
	            	} else if (pksToReverse.contains(candidateEDITTrxVO[i].getEDITTrxPK())) {
	            		if (candidateEDITTrxVO[i].getPendingStatus().equalsIgnoreCase("H")) {
		            		// trx should be reversed
		            		candidateEDITTrxVO[i].setReversalReasonCodeCT(reversalReasonCode);
		            		undo.add(buildReversal(candidateEDITTrxVO[i], operator));
		                    undoTrxList.add(candidateEDITTrxVO[i].getEDITTrxPK() + "");
	            		}
	                    
	            	} else {
	            		// trx should be undone if it meets status/effectiveDate/priority/sequence criteria
		                int candidatePriority = transactionPriorityCache.getPriority(candidateEDITTrxVO[i].getTransactionTypeCT());
		
		                String pendingStatus = candidateEDITTrxVO[i].getPendingStatus();
		                String status = candidateEDITTrxVO[i].getStatus();
		
		                if ((pendingStatus.equalsIgnoreCase("H") &&
		                     (status.equalsIgnoreCase("N") || status.equalsIgnoreCase("A"))) ||
		                     (status.equalsIgnoreCase("U") && pendingStatus.equalsIgnoreCase("P")))
		                {
		                    if (candidateEDITTrxVO[i].getEffectiveDate().equals(effectiveDate))
		                    {
		                        if ((undoRedoKey != null && candidateEDITTrxVO[i].getEDITTrxPK() == undoRedoKey.longValue()) ||
		                        	candidatePriority > priority ||
		                            (candidatePriority == priority && candidateEDITTrxVO[i].getSequenceNumber() > sequenceNumber))
		                        {
		                        	if (candidateEDITTrxVO[i].getOperator() != null && candidateEDITTrxVO[i].getOperator().equalsIgnoreCase("Conversion")) {
		                        		
		                        		String errorMessage = "Processing this transaction will cause a conversion " + candidateEDITTrxVO[i].getTransactionTypeCT() + 
		                						" trx to be undone.  Undo/redo of conversion transactions is not supported.";
		                        		throw new EDITEventException(errorMessage);
		                        		
		                        	} else {
			                            undo.add(buildUndo(candidateEDITTrxVO[i], operator));
			                            undoTrxList.add(candidateEDITTrxVO[i].getEDITTrxPK() + "");
		                        	}
		                        }
		                    }
		                    else
		                    {
		                    	if (candidateEDITTrxVO[i].getOperator() != null && candidateEDITTrxVO[i].getOperator().equalsIgnoreCase("Conversion")) {
		                    		
		                    		String errorMessage = "Processing this transaction will cause a conversion " + candidateEDITTrxVO[i].getTransactionTypeCT() + 
		            						" trx to be undone.  Undo/redo of conversion transactions is not supported.";
		                    		throw new EDITEventException(errorMessage);
		                    		
		                    	} else {
		                    		undo.add(buildUndo(candidateEDITTrxVO[i], operator));
		                    		undoTrxList.add(candidateEDITTrxVO[i].getEDITTrxPK() + "");
		                    	}
		                    }
		                }
		            }
	            }
	        } catch (Exception e) {
	        	System.out.println(e);
	            e.printStackTrace();
	        	throw e;
	        } finally {
	        	if (crud != null)
	                crud.close();
	            crud = null;
	        }            
        }

        return undo;
    }
    
    private Undo buildUndo(EDITTrxVO candidateEDITTrxVO)
    {
        Undo undo = null;
        ClientTrx currentClientTrx = new ClientTrx(candidateEDITTrxVO);

        currentClientTrx.setCycleDate(drivingClientTrx.getCycleDate());
        currentClientTrx.setExecutionMode(drivingClientTrx.getExecutionMode());
        currentClientTrx.setDrivingBackdatedTrxCode(drivingClientTrx.getTransactionTypeCT());

        undo = new Undo(currentClientTrx, "undo");

        return undo;
    }
    
    private Undo buildUndo(EDITTrxVO candidateEDITTrxVO, String operator)
    {
        Undo undo = null;
        ClientTrx currentClientTrx = new ClientTrx(candidateEDITTrxVO);

        currentClientTrx.setCycleDate(drivingClientTrx.getCycleDate());
        currentClientTrx.setExecutionMode(drivingClientTrx.getExecutionMode());
        currentClientTrx.setDrivingBackdatedTrxCode(drivingClientTrx.getTransactionTypeCT());

        undo = new Undo(currentClientTrx, "undo");
        
        undo.getClientTrx().getEDITTrxVO().setOperator(operator);

        return undo;
    }
    
    private Reversal buildReversal(EDITTrxVO candidateEDITTrxVO, String operator)
    {
        Reversal reversal = null;
        ClientTrx currentClientTrx = new ClientTrx(candidateEDITTrxVO);

        currentClientTrx.setCycleDate(drivingClientTrx.getCycleDate());
        currentClientTrx.setExecutionMode(drivingClientTrx.getExecutionMode());

        reversal = new Reversal(currentClientTrx, "undo");
        
        reversal.getClientTrx().getEDITTrxVO().setOperator(operator);

        return reversal;
    }

    private List buildRedoTrx(EDITTrxVO[] candidateEDITTrxVO,
                              long editTrxPK,
                              String transactionType,
                              String effectiveDate,
                              int sequenceNumber,
                              List undo)
    {
        //  The candidate trxs consist of only those whose effectiveDates are >= the effectiveDate on the trx being processed
        //  (seems to be done via the db lookup before this call)
        //  If the candidate's effectiveDate > effectiveDate of the trx being processed, redo
        //  If they are equal, compare the priorities - if the priority is less, redo

        TransactionPriorityCache transactionPriorityCache = TransactionPriorityCache.getInstance();

        int priority = transactionPriorityCache.getPriority(transactionType);

        List redo = new ArrayList();

        if (candidateEDITTrxVO != null)
        {
            for (int i = 0; i < candidateEDITTrxVO.length; i++)
            {
            	if (candidateEDITTrxVO[i] != null) {
            		
	                int candidatePriority = transactionPriorityCache.getPriority(candidateEDITTrxVO[i].getTransactionTypeCT());
	
	                String pendingStatus = candidateEDITTrxVO[i].getPendingStatus();
	                String status = candidateEDITTrxVO[i].getStatus();
	                
	                String complexChangeType = null;
	
	                if (candidateEDITTrxVO[i].getTransactionTypeCT().equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_BILLING_CHANGE)) {
		                ContractSetupVO[] contractSetupVO = DAOFactory.getContractSetupDAO().findByEDITTrxPK(candidateEDITTrxVO[i].getEDITTrxPK());
		
		                if (contractSetupVO != null && contractSetupVO.length > 0) 
		                {
		                	complexChangeType = contractSetupVO[0].getComplexChangeTypeCT();
		                }
	                }
	
	                // Only proceed to build redo if trx is not a BC Batch (those can be reversed but are not redone)
	                if (!(candidateEDITTrxVO[i].getTransactionTypeCT() != null && candidateEDITTrxVO[i].getTransactionTypeCT().equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_BILLING_CHANGE) && 
	                		complexChangeType != null && complexChangeType.equalsIgnoreCase(ContractSetup.COMPLEXCHANGETYPECT_BATCH)))
	                {
	                	
		                if ((((pendingStatus.equalsIgnoreCase("H") || pendingStatus.equalsIgnoreCase("L") ||
		                      pendingStatus.equalsIgnoreCase("S") || pendingStatus.equalsIgnoreCase("B") ||
		                      pendingStatus.equalsIgnoreCase("F") || pendingStatus.equalsIgnoreCase("O")) &&
		                     ((status.equalsIgnoreCase("N") && candidateEDITTrxVO[i].getEDITTrxPK() != editTrxPK) ||
		                      status.equalsIgnoreCase("A"))) ||
		                    (status.equalsIgnoreCase("A") && pendingStatus.equalsIgnoreCase("P"))) ||
		                    pendingStatus.equalsIgnoreCase("C"))
		                {
		                    //We always want to create the redo for transactions equal to driving effective date if
		                    //they have a pending status of "C" (they need to be undone/redone to recalculate the value of
		                    //the transaction correctly
		                    if (candidateEDITTrxVO[i].getEffectiveDate().equals(effectiveDate) &&
		                        !pendingStatus.equalsIgnoreCase("C"))
		                    {
		                        if (candidatePriority > priority ||
		                            (candidatePriority == priority &&
		                             (candidateEDITTrxVO[i].getSequenceNumber() > sequenceNumber ||
		                              candidateEDITTrxVO[i].getSequenceNumber() == sequenceNumber)))
		                        {
		                            redo.add(buildRedo((EDITTrxVO)candidateEDITTrxVO[i].cloneVO(), undo));
		                        }
		                    }
		                    else
		                    {
		                        redo.add(buildRedo((EDITTrxVO)candidateEDITTrxVO[i].cloneVO(), undo));
		                    }
		                }
		            }
	            }
            }
        }

        return redo;
    }

    private Redo buildRedo(EDITTrxVO candidateEDITTrxVO, List undo)
    {
        Redo redo = null;
        boolean undoRedo = checkForUndo(candidateEDITTrxVO, undo);
        if (undoRedo)
        {
            candidateEDITTrxVO.setReapplyEDITTrxFK(0);
        }
        ClientTrx currentClientTrx = new ClientTrx(candidateEDITTrxVO);

        currentClientTrx.setCycleDate(drivingClientTrx.getCycleDate());
        currentClientTrx.setExecutionMode(drivingClientTrx.getExecutionMode());

        redo = new Redo(currentClientTrx, "redo");

        return redo;
    }

    private List buildNaturalTrx(EDITTrxVO[] candidateEDITTrxVO) throws EDITEventException
    {
        List natural = new ArrayList();

        for (int i = 0; i < candidateEDITTrxVO.length; i++)
        {
             // Add all Rescheduled Trx
            if ((candidateEDITTrxVO[i].getPendingStatus().equals("P") ||
                 candidateEDITTrxVO[i].getPendingStatus().equals("B") ||
                 candidateEDITTrxVO[i].getPendingStatus().equals("C") ||
                 candidateEDITTrxVO[i].getPendingStatus().equals("F") ||
                 candidateEDITTrxVO[i].getPendingStatus().equals("S") ||
                 candidateEDITTrxVO[i].getPendingStatus().equals("M")) &&
                candidateEDITTrxVO[i].getStatus().equals("N") &&
                !undoTrxList.contains(candidateEDITTrxVO[i].getEDITTrxPK() + ""))
            {
                ClientTrx currentClientTrx = new ClientTrx(candidateEDITTrxVO[i]);

                currentClientTrx.setCycleDate(drivingClientTrx.getCycleDate());
                currentClientTrx.setExecutionMode(drivingClientTrx.getExecutionMode());

                if (!candidateEDITTrxVO[i].getTransactionTypeCT().equalsIgnoreCase("CK") && !candidateEDITTrxVO[i].getTransactionTypeCT().equalsIgnoreCase("RCK"))
                {
                    natural.add(new Natural(currentClientTrx, "natural"));
                }
                else
                {
                    natural.add(new Check(currentClientTrx, "natural"));
                }

                if (currentClientTrx.shouldReschedule())
                {
                    Reschedule reschedule = new Reschedule(currentClientTrx);

                    reschedule.execute();

                    ClientTrx[] rescheduledClientTrx = reschedule.getRescheduledClientTrx();

                    for (int j = 0; j < rescheduledClientTrx.length; j++)
                    {
                        if (rescheduledClientTrx[j].getEDITTrxVO().getPendingStatus().equalsIgnoreCase("P"))
                        {
                            natural.add(new Natural(rescheduledClientTrx[j], "natural"));
                        }
                    }
                }
            }
        }

        return natural;
    }

    private boolean checkForUndo(EDITTrxVO candidateEDITTrxVO, List undo)
    {
        boolean undoFound = false;

        for (int i = 0; i < undo.size(); i++)
        {
        	if (undo.get(i) instanceof Undo) {
	            EDITTrxVO editTrx = ((Undo)undo.get(i)).getClientTrx().getEDITTrxVO();
	            if (editTrx.getEDITTrxPK() == candidateEDITTrxVO.getEDITTrxPK())
	            {
	                undoFound = true;
	                i = undo.size();
	            }
        	}
        }

        return undoFound;
    }

    private void saveRedoTrxToDatabase(List redo, long drivingTrxPK) throws Exception
    {
        for (int i = 0; i < redo.size(); i++)
        {
            EDITTrxVO redoEditTrxVO = ((Redo) redo.get(i)).getClientTrx().getEDITTrxVO();

//            if (redoEditTrxVO.getReapplyEDITTrxFK() == 0 &&
            if (redoEditTrxVO.getEDITTrxPK() != drivingTrxPK ||
                (redoEditTrxVO.getEDITTrxPK() == drivingTrxPK &&
                 redoEditTrxVO.getPendingStatus().equalsIgnoreCase("C")))
            {
                if (redoEditTrxVO.getReapplyEDITTrxFK() == 0)
                {
                    redoEditTrxVO.setReapplyEDITTrxFK(redoEditTrxVO.getEDITTrxPK());
                    redoEditTrxVO.setEDITTrxPK(0);
                }
                redoEditTrxVO.setStatus("A");
                redoEditTrxVO.setPendingStatus("P");
                redoEditTrxVO.setPremiumDueCreatedIndicator("N");

                // Now reset the originatingTrxFK to make sure we have the correct pointer to the originating trx.
                // Added because undo/redo will change the originating trx's PK
                if (redoEditTrxVO.getOriginatingTrxFK() > 0)
                {
                    EDITTrxVO[] editTrxVO = DAOFactory.getEDITTrxDAO().findByReapplyEDITTrxFK(redoEditTrxVO.getOriginatingTrxFK());
                    if (editTrxVO != null)
                    {
                        redoEditTrxVO.setOriginatingTrxFK(editTrxVO[0].getEDITTrxPK());
                    }
                }
            }

            ((Redo) redo.get(i)).getClientTrx().save(); // Persists, even should the RedoTrx ultimately fail.

            //Now process OverdueChargeRemaining records (if there are any) to reset the EDITTrxFK to be equal to
            //the reapply transaction's primary key (instead of the undo transaction's PK)
            redoEditTrxVO = ((Redo) redo.get(i)).getClientTrx().getEDITTrxVO();

            OverdueChargeVO[] overdueChargeVOs = DAOFactory.getOverdueChargeDAO().findByEDITTrxFK(redoEditTrxVO.getReapplyEDITTrxFK());

            if (overdueChargeVOs != null)
            {
                event.business.Event eventComponent = new event.component.EventComponent();
                for (int j = 0; j < overdueChargeVOs.length; j++)
                {
                    overdueChargeVOs[j].setEDITTrxFK(redoEditTrxVO.getEDITTrxPK());
                    eventComponent.createOrUpdateVO(overdueChargeVOs[j], false);
                }
            }
        }
    }
}
