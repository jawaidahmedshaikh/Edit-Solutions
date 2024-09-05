/*
 * User: gfrosti
 * Date: Aug 11, 2003
 * Time: 4:59:05 PM
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */

package event.financial.client.trx;

import contract.*;
import edit.common.*;
import edit.common.exceptions.*;
import edit.common.vo.*;
import edit.services.db.*;
import edit.services.db.hibernate.SessionHelper;
import edit.services.logging.*;
import event.*;
import event.common.*;
import event.dm.dao.*;
import event.financial.client.strategy.*;
import logging.*;

import org.apache.logging.log4j.Logger;

import java.util.*;

import engine.ProductStructure;
import fission.utility.Util;
import logging.Log;
import logging.LogEvent;
import util.PendingStatus;

public class ClientTrx  implements Comparable
{
    public static final int REALTIME_MODE = 0;
    public static final int BATCH_MODE = 1;
    private EDITTrxVO editTrxVO;
    public long suspenseFK = 0;
    private String cycleDate;
    private int executionMode;
    private ClientTrxImpl clientTrxImpl;
    private String drivingBackdatedTrxCode;
    private boolean hasProcessedPYUndo;

    /**
     * Constructor. The operator will replace whatever current rider is saved in the EDITTrx.
     * @param editTrxVO
     * @param operator
     */
    public ClientTrx(EDITTrxVO editTrxVO, String operator)
    {
        this();
        this.editTrxVO = editTrxVO;
        editTrxVO.setOperator(operator);
    }

    /**
     * Constructor. The operator will replace whatever current rider is saved in the EDITTrx.
     * @param editTrxPK
     * @param operator
     */
    public ClientTrx(long editTrxPK, String operator) throws Exception
    {
        this(DAOFactory.getEDITTrxDAO().findByEDITTrxPK(editTrxPK)[0], operator);
    }

    /**
     * Constructor. The operator is the same as the operator in the supplied EDITTrxVO.
     * @param editTrxVO
     */
    public ClientTrx(EDITTrxVO editTrxVO)
    {
        this(editTrxVO, editTrxVO.getOperator());
    }

    private ClientTrx()
    {
        this.clientTrxImpl = new ClientTrxImpl();
    }

    public void setExecutionMode(int executionMode)
    {
        this.executionMode = executionMode;
    }

    public int getExecutionMode()
    {
        return executionMode;
    }

    public void execute() throws Exception
    {
        long editTrxPK = this.getPK();

        EDITTrxVO editTrxVO = new event.component.EventComponent().composeEDITTrxVOByEDITTrxPK(editTrxPK, new ArrayList());
        if (!editTrxVO.getPendingStatus().equalsIgnoreCase(Constants.TrxStatus.HISTORY) &&
            !editTrxVO.getPendingStatus().equalsIgnoreCase(Constants.TrxStatus.LOOKUP)  &&
            !editTrxVO.getPendingStatus().equalsIgnoreCase(Constants.TrxStatus.OVERDUE) &&
            !editTrxVO.getPendingStatus().equalsIgnoreCase(Constants.TrxStatus.WAITING_FOR_UVS))
        {
            List strategyChain = new StrategyChain(this).buildNaturalStrategyChain();

            executeStrategyChain(strategyChain);
        }
    }

    public long reverse(String reversalReasonCode) throws Exception
    {
        this.setCycleDate(new EDITDate().getFormattedDate());

        List strategyChain = new StrategyChain(this).buildReversalStrategyChain(reversalReasonCode);

        return executeStrategyChain(strategyChain);
    }
    
    public long batchReverse(String reversalReasonCode, List<Long> pksToReverse, boolean isSubmitReversalChain, EDITTrxVO earliestPYToRedo) throws Exception
    {
        this.setCycleDate(new EDITDate().getFormattedDate());

        List strategyChain = new StrategyChain(this).buildBatchReversalStrategyChain(reversalReasonCode, pksToReverse, isSubmitReversalChain, earliestPYToRedo);

        return executeStrategyChain(strategyChain);
    }

    public long save(CRUD crud, int notificationDays, String notificationDaysType)
    {
        return clientTrxImpl.save(this, crud, notificationDays, notificationDaysType);
    }

    public long save()
    {
        return clientTrxImpl.save(this);
    }

    public long save(CRUD crud)
    {
        return clientTrxImpl.save(this, crud);
    }

    public int delete()
    {
        try
        {
            new DeleteTrx(this).execute();
        }
        catch (EDITEventException e)
        {
            Logging.getLogger(Logging.GENERAL_EXCEPTION).error(new LogEvent(e));

            Log.logGeneralExceptionToDatabase(null, e);

            throw new RuntimeException(e);
        }

        editTrxVO = null;

        // Successful deletion.
        return 1;
    }

    public EDITTrxVO getEDITTrxVO()
    {
        return this.editTrxVO;
    }

    public String getCycleDate()
    {
        return this.cycleDate;
    }

    public void setCycleDate(String cycleDate)
    {
        this.cycleDate = cycleDate;
    }

    public String  getDrivingBackdatedTrxCode()
    {
        return this.drivingBackdatedTrxCode;
    }

    public void setDrivingBackdatedTrxCode(String drivingBackdatedTrxCode)
    {
        this.drivingBackdatedTrxCode = drivingBackdatedTrxCode;
    }

    public boolean shouldReschedule()
    {
        if (isScheduledEvent() && editTrxVO.getTrxIsRescheduledInd().equals("N"))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public boolean isScheduledEvent()
    {
        return (editTrxVO.getDueDate() != null);
    }

    public boolean isBackdated()
    {
        String productType = getProductTypeForTrx();

        if ((productType.equalsIgnoreCase(ProductStructure.VARIABLE_PRODUCT) &&
             new EDITDate(editTrxVO.getEffectiveDate()).before(new EDITDate())) ||
            (productType.equalsIgnoreCase(ProductStructure.FIXED_PRODUCT) &&
             new EDITDate(editTrxVO.getEffectiveDate()).beforeOREqual(new EDITDate())))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public static boolean isBackdated(ClientTrx clientTrx, long productStructurePK)
    {
        String productType = ClientTrx.getProductTypeForTrx(productStructurePK);

        EDITTrxVO editTrxVO = clientTrx.getEDITTrxVO();

        if ((productType.equalsIgnoreCase(ProductStructure.VARIABLE_PRODUCT) &&
             new EDITDate(editTrxVO.getEffectiveDate()).before(new EDITDate())) ||
            (productType.equalsIgnoreCase(ProductStructure.FIXED_PRODUCT) &&
             new EDITDate(editTrxVO.getEffectiveDate()).beforeOREqual(new EDITDate())))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    private String getProductTypeForTrx()
    {
        String productType = ProductStructure.VARIABLE_PRODUCT;
        
        Segment segment = Segment.findByEditTrxPK(getEDITTrxVO().getEDITTrxPK());
        
        //ClientSetup clientSetup = ClientSetup.findByPK(new Long(this.editTrxVO.getClientSetupFK()));
        //ContractSetup contractSetup = clientSetup.get_ContractSetup();

        //Segment segment = Segment.findByPK(contractSetup.getSegmentFK());

        if (segment != null)
        {
            //ProductStructure productStructure = segment.getProductStructure();
            ProductStructure productStructure = ProductStructure.findByPK(segment.getProductStructureFK().longValue());
            
            productType = Util.initString(productStructure.getProductTypeCT(), ProductStructure.VARIABLE_PRODUCT);
        }

        return productType;
    }
    
    private String getSegmentNameForTrx()
    {
        //String productType = ProductStructure.VARIABLE_PRODUCT;
        
    	String segmentName = null;
    	
        Segment segment = Segment.findByEditTrxPK(getEDITTrxVO().getEDITTrxPK());
        
        if (segment != null)
        {            
        	segmentName = segment.getSegmentNameCT();
        }

        return segmentName;
    }
    
    private String getContractNumberForTrx()
    {        
    	String segmentName = null;
    	
        Segment segment = Segment.findByEditTrxPK(getEDITTrxVO().getEDITTrxPK());
        
        if (segment != null)
        {            
        	segmentName = segment.getContractNumber();
        }

        return segmentName;
    }

    private static String getProductTypeForTrx(long productStructurePK)
    {
        ProductStructure productStructure = ProductStructure.findBy_ProductStructurePK_V1(new Long(productStructurePK));
        String productType = Util.initString(productStructure.getProductTypeCT(), ProductStructure.VARIABLE_PRODUCT);

        return productType;
    }

    /**
     * Returns true if the transactionTypeCT has been configured as Reinsurable.
     * @see TransactionPriority
     * @return
     */
    public boolean isReinsurable()
    {
        TransactionPriorityVO[] transactionPriorityVO = DAOFactory.getTransactionPriorityDAO().findByTrxType(getTransactionTypeCT());

        String reinsurableInd = transactionPriorityVO[0].getReinsuranceInd();

        if (reinsurableInd != null)
        {
            if (reinsurableInd.equalsIgnoreCase("Y"))
            {
                return true;
            }
        }

        return false;
    }

    /**
     * Returns true if this reinsurable transaction:
     * 1) Is reinsurable
     * 2) Has ContractTreaty information associated with it.
     * It is not necessary to call isReinsurable() before invoking this method as it would be redundant.
     * @return
     */
    public boolean hasReinsurance()
    {
        boolean hasReinsurance = false;

        if (isReinsurable())
        {
            ClientSetup clientSetup = getClientSetup();

            ContractSetup contractSetup = new ContractSetup(clientSetup.getContractSetupFK().longValue());

            Segment segment = new Segment(contractSetup.getSegmentFK().longValue());

            hasReinsurance = (segment.getContractTreaty() != null);
        }

        return hasReinsurance;
    }

    public boolean isCommissionableEvent(String trxType)
    {
        TransactionPriorityVO[] transactionPriorityVO = DAOFactory.getTransactionPriorityDAO().findByTrxType(trxType);

        String commissionableEventInd = transactionPriorityVO[0].getCommissionableEventInd();

        if (commissionableEventInd != null)
        {
            if (commissionableEventInd.equalsIgnoreCase("Y"))
            {
                return true;
            }
        }

        return false;
    }

    public static int getTransactionPriority(String transactionTypeCT)
    {
        return TransactionPriorityCache.getInstance().getPriority(transactionTypeCT);
    }

    private long executeStrategyChain(List strategyChain) throws Exception
    {
    	
    	hasProcessedPYUndo = false;
    	
    	ClientStrategy clientStrategyToEvaluate = null;
    	
    	String segmentName = getSegmentNameForTrx();
    	boolean isTraditionalSegment = false;

    	if (segmentName != null && segmentName.equalsIgnoreCase(Segment.SEGMENTNAMECT_TRADITIONAL)) {
    		isTraditionalSegment = true;
    	}
    	
		int maxStrategyChainTrx = Integer.parseInt(System.getProperty("maxStrategyChainTrx"));
    	
        for (int i = 0; i < strategyChain.size(); i++)
        {
            ClientStrategy currentStrategy = null;

            try
            {
            	if (strategyChain.size() > maxStrategyChainTrx) {
                    Segment segment = Segment.findByEditTrxPK(((ClientStrategy)strategyChain.get(0)).getClientTrx().getPK());
                    String contractNumber = "";
                    if (segment != null) {
                    	contractNumber = segment.getContractNumber();
                    }
                    
                	throw new Exception("Strategy Chain Trx Exceed Limit - Verify QTY of EDITTrx Records - Contract: " + contractNumber);
                }
            	
            	// Removed per Carrie - 3/15/2022
            	/*
				if (hasProcessedPYUndo || (isTraditionalSegment && (((ClientStrategy) strategyChain.get(i)) instanceof Redo || ((ClientStrategy) strategyChain.get(i)) instanceof Natural) && 
        			(((ClientStrategy) strategyChain.get(i)).getClientTrx().getEDITTrxVO().getTransactionTypeCT().equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_BILLING_CHANGE) ||
        			((ClientStrategy) strategyChain.get(i)).getClientTrx().getEDITTrxVO().getTransactionTypeCT().equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_FACEINCREASE) ||
        			((ClientStrategy) strategyChain.get(i)).getClientTrx().getEDITTrxVO().getTransactionTypeCT().equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_MATURITY)) &&
        			((ClientStrategy) strategyChain.get(i)).getClientTrx().getEDITTrxVO().getPremiumDueCreatedIndicator().equalsIgnoreCase("N"))) {
            		
            		if (!hasProcessedPYUndo) {
            			clientStrategyToEvaluate = (ClientStrategy) strategyChain.get(i);
            		}
            		
            		if (((ClientStrategy) strategyChain.get(i)).getClientTrx().getEDITTrxVO().getTransactionTypeCT().equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_BILLING_CHANGE)) {            			
            			
            			ContractSetupVO[] contractSetupVO = DAOFactory.getContractSetupDAO().findByEDITTrxPK(((ClientStrategy) strategyChain.get(i)).getClientTrx().getEDITTrxVO().getEDITTrxPK());

                        String complexChangeType = null;
                        if (contractSetupVO != null && contractSetupVO.length > 0) 
                        {
                        	complexChangeType = contractSetupVO[0].getComplexChangeTypeCT();
                        }
                        
                        if (complexChangeType == null || (complexChangeType != null && 
                        		!complexChangeType.trim().equalsIgnoreCase(ContractSetup.COMPLEXCHANGETYPECT_CLAIMSTOP))) {
                    		verifyAddPYRedo(strategyChain, clientStrategyToEvaluate, i);
                        }
            		} else {
                		verifyAddPYRedo(strategyChain, clientStrategyToEvaluate, i);

            		}
            	} 
            	*/
            	
                currentStrategy = ((ClientStrategy) strategyChain.get(i));
                
                // System.out.println("***** " + strategyChain.size() + " " + currentStrategy.getClientTrx().getEDITTrxVO().getTransactionTypeCT() + " - " +
                // currentStrategy.getClientTrx().getEDITTrxVO().getEffectiveDate() + " - " + currentStrategy.getClass().getSimpleName());

                //check EDITTrx status for possible update from another strategy chain
                if (isEditTrxExecutable(currentStrategy))
                {
                    ClientStrategy[] newStrategy = currentStrategy.execute();

                    suspenseFK = currentStrategy.suspenseFK;

                    if (newStrategy != null) // We assume that the newStrategy comes after any previously executed strategy.
                    {
                        verifyStrategyChainInsert(strategyChain, newStrategy);
//                        Collections.sort(strategyChain);
                    }
                }
            }
            catch (EDITEventException e)
            {
            	logException(e, currentStrategy, strategyChain);
            	
            	if (!e.isLogged())
            	{
	                Logging.getLogger(Logging.GENERAL_EXCEPTION).error(new LogEvent(e));
	
	                Log.logGeneralExceptionToDatabase(null, e);
	                
	                e.setLogged(true);
            	}

                throw e;
            }
        }

        return suspenseFK;
    }

    private void verifyAddPYRedo(List strategyChain, ClientStrategy nextStrategy, int currentIndex) throws EDITEventException
    {
    	// If the contracts then-current (after undos) Life.PaidToDate > the BC, FI or MA EDITTrx.EffectiveDate, 
    	// undo/redo the most recent PY using the current trx's effective date 

    	try {
	        Segment segment = Segment.findByEditTrxPK(nextStrategy.getClientTrx().getEDITTrxVO().getEDITTrxPK());
	        Life life = segment.get_Life();
	        
	        EDITDate paidToDate = life.getPaidToDate();
	        EDITDate effectiveDate = new EDITDate(nextStrategy.getClientTrx().getEDITTrxVO().getEffectiveDate());
	        
	    	if (paidToDate != null && effectiveDate != null && paidToDate.after(effectiveDate))
	    	{
	    		EDITTrx[] editTrxs = EDITTrx.findByActive_EffectiveDate_TrxType_SortedDesc(segment.getSegmentPK(), effectiveDate, EDITTrx.TRANSACTIONTYPECT_PREMIUM);
	    		
	    		if (editTrxs != null && editTrxs.length > 0) 
	    		{
	    			EDITTrx editTrx = editTrxs[0];
	
	    			try {
		    			SessionHelper.beginTransaction(SessionHelper.EDITSOLUTIONS);
				    			
		    			// Check for new sequenceNumber
		    			EDITTrx[] editTrxPYs = EDITTrx.findBy_SegmentPK_EffectiveDate_TransactionTypeCT(segment.getSegmentPK(), effectiveDate, EDITTrx.TRANSACTIONTYPECT_PREMIUM);

		    	        int sequenceNumber = 1;

		    	        if (editTrxPYs != null && editTrxPYs.length > 0)
		    	        {
		    	            sequenceNumber = editTrxPYs[0].getSequenceNumber() + 1; // EDITTrxs were ordered by SequenceNumber descending.
		    	        }
		    	        
		    	        // reset effectiveDate of PY to that of the nextStrategy trx
		    			editTrx.setEffectiveDate(effectiveDate);
		    			editTrx.setSequenceNumber(sequenceNumber);
		    					    			
		                SessionHelper.saveOrUpdate(editTrx, SessionHelper.EDITSOLUTIONS);
		                SessionHelper.commitTransaction(SessionHelper.EDITSOLUTIONS);
		                
	    			} catch (Exception e) {
	                    SessionHelper.rollbackTransaction(SessionHelper.EDITSOLUTIONS);
	
	                    throw new EDITEventException(e.getMessage());
	                }
	    			
	    			ClientTrx undoClientTrx = new ClientTrx(editTrx.getAsVO());
	    			
	    			undoClientTrx.setCycleDate(nextStrategy.getClientTrx().getCycleDate());
	    			undoClientTrx.setExecutionMode(nextStrategy.getClientTrx().getExecutionMode());
	    			undoClientTrx.setDrivingBackdatedTrxCode(nextStrategy.getClientTrx().getEDITTrxVO().getTransactionTypeCT());
	
			        Undo undo = new Undo(undoClientTrx, "undo");
			        
			        strategyChain.add(currentIndex, undo);
	    			
		    		ClientTrx redoClientTrx = new ClientTrx((EDITTrxVO)editTrx.getAsVO().cloneVO());
		
		    		redoClientTrx.setCycleDate(nextStrategy.getClientTrx().getCycleDate());
		    		redoClientTrx.setExecutionMode(nextStrategy.getClientTrx().getExecutionMode());
		
			        Redo redo = new Redo(redoClientTrx, "redo");
			        
			        if (redo.getClientTrx().getEDITTrxVO().getReapplyEDITTrxFK() == 0)
	                {
			        	redo.getClientTrx().getEDITTrxVO().setReapplyEDITTrxFK(redo.getClientTrx().getEDITTrxVO().getEDITTrxPK());
	                }
			        
		        	redo.getClientTrx().getEDITTrxVO().setEDITTrxPK(0);
			        redo.getClientTrx().getEDITTrxVO().setStatus("A");
			        redo.getClientTrx().getEDITTrxVO().setPendingStatus("P");
	
	                // Now reset the originatingTrxFK to make sure we have the correct pointer to the originating trx.
	                // Added because undo/redo will change the originating trx's PK
	                if (redo.getClientTrx().getEDITTrxVO().getOriginatingTrxFK() > 0)
	                {
	                    EDITTrxVO[] editTrxVO = DAOFactory.getEDITTrxDAO().findByReapplyEDITTrxFK(redo.getClientTrx().getEDITTrxVO().getOriginatingTrxFK());
	                    if (editTrxVO != null)
	                    {
	                    	redo.getClientTrx().getEDITTrxVO().setOriginatingTrxFK(editTrxVO[0].getEDITTrxPK());
	                    }
	                }
	                
	                redo.getClientTrx().save(); // Persists, even should the RedoTrx ultimately fail.
	
	                //Now process OverdueChargeRemaining records (if there are any) to reset the EDITTrxFK to be equal to
	                //the reapply transaction's primary key (instead of the undo transaction's PK)
	                OverdueChargeVO[] overdueChargeVOs = DAOFactory.getOverdueChargeDAO().findByEDITTrxFK(redo.getClientTrx().getEDITTrxVO().getReapplyEDITTrxFK());
	
	                if (overdueChargeVOs != null)
	                {
	                    event.business.Event eventComponent = new event.component.EventComponent();
	                    for (int j = 0; j < overdueChargeVOs.length; j++)
	                    {
	                        overdueChargeVOs[j].setEDITTrxFK(redo.getClientTrx().getEDITTrxVO().getEDITTrxPK());
	                        eventComponent.createOrUpdateVO(overdueChargeVOs[j], false);
	                    }
	                }
			        
			        strategyChain.add((currentIndex + 1), redo);
			        
			        List strategiesLeft = new ArrayList();
			        int nextIndex = currentIndex + 1;

	    			for (int x = nextIndex; x < strategyChain.size(); x++) {
	    				strategiesLeft.add(strategyChain.get(x));
	    			}
	    			
	    			Collections.sort(strategiesLeft);
	    			
	    			// strategyChain = strategyChain.subList(0, currentIndex);
	    			strategyChain.subList(nextIndex, strategyChain.size()).clear();
	    			strategyChain.addAll(strategiesLeft);
			        
			        hasProcessedPYUndo = true;
			            
	    		}
	    	} else {
	    		// done undoing PYs
	    		/*if (hasProcessedPYUndo) {
	    			// sort the PY redos properly into the strategy chain
		    		// Collections.sort(strategyChain); // what is this doing.. replicate when adding in py redos!!
		    		// EditTrxCompare.compare is what sorts strat chain
    				List strategiesLeft = new ArrayList();

	    			for (int x = currentIndex; x < strategyChain.size(); x++) {
	    				strategiesLeft.add(strategyChain.get(x));
	    			}
	    			
	    			Collections.sort(strategiesLeft);
	    			
	    			// strategyChain = strategyChain.subList(0, currentIndex);
	    			strategyChain.subList(currentIndex, strategyChain.size()).clear();
	    			strategyChain.addAll(strategiesLeft);
	    		}*/
	    		
	    		hasProcessedPYUndo = false;
	    	}
	    	
	    } catch (Exception e) {
            SessionHelper.rollbackTransaction(SessionHelper.EDITSOLUTIONS);
        	
            throw new EDITEventException(e.getMessage());
        }
    }
    

    private void verifyStrategyChainInsert(List strategyChain, ClientStrategy[] newStrategy)
    {
        boolean insertedIntoChain = false;
        for (int j = 0; j < newStrategy.length; j++)
        {
            if ((newStrategy[j].getClientTrx().getEDITTrxVO().getEffectiveDate().compareTo(cycleDate)) < 1)
            {
                strategyChain.add(newStrategy[j]);
                insertedIntoChain = true;
            }
        }

        if (insertedIntoChain)
        {
            ClientStrategy currentStrategy = null;
            List undo = new ArrayList();
            List natural = new ArrayList();

            //get undo
            for (int i = 0; i < strategyChain.size(); i++)
            {
                currentStrategy = ((ClientStrategy) strategyChain.get(i));

                if (currentStrategy instanceof Undo || currentStrategy instanceof Reversal)
                {
                    undo.add(currentStrategy);
                }
                else
                {
                    natural.add(currentStrategy);
                }
            }

            Collections.sort(undo);
            Collections.reverse(undo); // order = descending

            Collections.sort(natural); // order = ascending

            strategyChain.clear();
            strategyChain.addAll(undo); // Undos will go first
            strategyChain.addAll(natural); // Naturals and Redos will follow.        //To change body of created methods use File | Settings | File Templates.
        }
    }

    /**
     * Logs the exception of the currently running trx, and the following trx in the strategy chain that won't
     * be executed.
     * @param e
     * @param currentStrategy
     * @param strategyChain
     */
    private void logException(EDITEventException e, ClientStrategy currentStrategy, List strategyChain)
    {
    	ClientTrx clientTrx = currentStrategy.getClientTrx();
    	
    	String message = Constants.TrxError.TRX_ERRORED + " - " + e.getMessage();
    	
        // Create error log for the strategy that failed
        logger(message, e, clientTrx);

        // Create an error log entry for the rest of the strategy chain (since they won't be processed).
        int indexOfFailedStrategy = strategyChain.indexOf(currentStrategy);
        int bypassCounter = 0;
        
        for (int j = indexOfFailedStrategy + 1; j < strategyChain.size(); j++)
        {
            bypassCounter++;
        }
        
        if (bypassCounter > 0)
        {
        	message = bypassCounter + " " + Constants.TrxError.TRX_BYPASSED;
        	logger(message, null, clientTrx);
        }
    }

    public int compareTo(Object o)
    {
        ClientTrx visitingClientTrx = (ClientTrx) o;

        return new EDITTrxCompare().compare(editTrxVO, visitingClientTrx.getEDITTrxVO());
    }

    /**
     * Getter.
     * @return
     */
    public long getPK()
    {
        return editTrxVO.getEDITTrxPK();
    }

    /**
     * Getter.
     * @return
     */
    public String getTransactionTypeCT()
    {
        return editTrxVO.getTransactionTypeCT();
    }

    /**
     * Getter.
     * @return
     */
    public int getSequenceNumber()
    {
        return editTrxVO.getSequenceNumber();
    }

    /**
     * Getter.
     * @return
     */
    public String getOperator()
    {
        return editTrxVO.getOperator();
    }

    /**
     * Getter.
     */
    public String getEffetiveDate()
    {
        return editTrxVO.getEffectiveDate();
    }

    /**
     * Submits the exception and its context to logging.
     * @see Logger
     * @param message
     * @param e
     * @param clientTrx
     */
    private void logger(String message, EDITEventException e, ClientTrx clientTrx)
    {
        Segment segment = Segment.findByEditTrxPK(clientTrx.getPK());

        String contractNumber = "None Found!";
        String effectiveDate = "None Found!";
        String contractStatus = "None Found!";

        if (segment != null)
        {
            contractNumber = segment.getContractNumber();
            contractStatus = segment.getSegmentStatusCT();
        }

        effectiveDate = clientTrx.getEffetiveDate();
        String operator = clientTrx.getOperator();
        String processDate = clientTrx.getCycleDate();
        int sequenceNumber = clientTrx.getSequenceNumber();
        String transactionTypeCT = clientTrx.getTransactionTypeCT();
        long editTrxPK = clientTrx.getPK();

        String mode = (executionMode == ClientTrx.BATCH_MODE) ? "batch" : "realtime";

        Logger logger = Logging.getLogger(Logging.EXECUTE_TRANSACTION);

        LogEvent logEvent = new LogEvent(message, e);

        logEvent.setContextName(Logging.EXECUTE_TRANSACTION);
        logEvent.addToContext("ContractNumber", contractNumber);
        logEvent.addToContext("EffectiveDate", effectiveDate);
        logEvent.addToContext("Operator", operator);
        logEvent.addToContext("ProcessDate", processDate);
        logEvent.addToContext("SequenceNumber", String.valueOf(sequenceNumber));
        logEvent.addToContext("TransactionTypeCT", transactionTypeCT);
        logEvent.addToContext("Mode", mode);
        logEvent.addToContext("ContractStatus", contractStatus);

        logger.error(logEvent);

        logToDatabase(contractNumber, contractStatus, effectiveDate, operator, processDate, sequenceNumber, transactionTypeCT, message);
    }

    /**
     * Log the error information to the database
     *
     * @param contractNumber
     * @param effectiveDate
     * @param operator
     * @param processDate
     * @param sequenceNumber
     * @param transactionTypeCT
     * @param message
     */
    private void logToDatabase(String contractNumber, String contractStatus, String effectiveDate, String operator, String processDate,
                               int sequenceNumber, String transactionTypeCT, String message)
    {
        EDITMap columnInfo = new EDITMap();
        columnInfo.put("ContractNumber", contractNumber);
        columnInfo.put("ContractStatus", contractStatus);
        columnInfo.put("EffectiveDate", effectiveDate);
        columnInfo.put("Operator", operator);
        columnInfo.put("ProcessDate", processDate);
        columnInfo.put("SequenceNumber", String.valueOf(sequenceNumber));
        columnInfo.put("TransactionTypeCT", transactionTypeCT);

        Log.logToDatabase(Log.EXECUTE_TRANSACTION, message, columnInfo);
    }

    /**
     * Getter.
     * @return
     */
    public ClientSetup getClientSetup()
    {
        return ClientSetup.findByPK(new Long(editTrxVO.getClientSetupFK()));
    }

    /**
     * Returns true if this is a newly created entity that has not yet been persisted.
     * @return
     */
    public boolean isNew()
    {
        return (editTrxVO.getEDITTrxPK() == 0);
    }

    /**
     * Setter.
     * @param status
     */
    public void setReinsuranceStatus(String status)
    {
        editTrxVO.setReinsuranceStatus(status);
    }

    /**
     * For the current strategy being executed, check its current pendingStatus on the data base.
     * It could have been processed by a backdated spawned transaction.
     * @return  boolean true or false
     */
    private boolean isEditTrxExecutable(ClientStrategy currentStrategy)
    {

        boolean executeTrx = true;

        if (currentStrategy instanceof Natural ||
            currentStrategy instanceof Redo)
        {
            long edittTrxPK = currentStrategy.getClientTrx().getPK();
            try
            {
                EDITTrxVO[] editTrxVOs = DAOFactory.getEDITTrxDAO().findByEDITTrxPK(edittTrxPK);
                if (editTrxVOs != null)
                {
                    String editTrxStatus = editTrxVOs[0].getPendingStatus();
                    if (editTrxStatus.equalsIgnoreCase(PendingStatus.HISTORY.getDbText()) ||
                    	editTrxStatus.equalsIgnoreCase(PendingStatus.DELETED.getDbText()) ||
                        editTrxStatus.equalsIgnoreCase(Constants.TrxStatus.LOOKUP)  ||
                        editTrxStatus.equalsIgnoreCase(Constants.TrxStatus.OVERDUE) ||
                        editTrxStatus.equalsIgnoreCase(PendingStatus.TERMINATED.getDbText()))
                    {
                        executeTrx = false;
                    }
                }
                else
                {
                    executeTrx = false;
                }
            }
            catch (Exception e)
            {
              System.out.println(e);

                e.printStackTrace();
            }
        }

        return executeTrx;
    }

    /**
     * Returns true if the transaction is in the pending state.
     * @return
     */
    public boolean isPending()
    {
        boolean isPending = false;

        String status = getPendingStatus();

        if (status != null &&
            (status.equalsIgnoreCase("P") || status.equalsIgnoreCase("X")))
        {
            isPending = true;
        }

        return isPending;
    }

    /**
     * Getter.
     * @return
     */
    private String getPendingStatus()
    {
        return editTrxVO.getPendingStatus();
    }

    /**
     * Getter.
     * @return
     */
    private String getStatus()
    {
        return editTrxVO.getStatus();
    }

    /**
     * If this trx is a TransactionType of TU, PY, TF, HFTA, HFTP, FT
     * @return
     */
    private boolean shouldUndoChargeCodes()
    {
        boolean shouldUndoChargeCodes = false;

        String trxType = getTransactionTypeCT();

        if (trxType.equals("TU") || trxType.equals("PY") || trxType.equals("TF") ||
            trxType.equals("HFTA") || trxType.equals("HFTP") || trxType.equals("FT") ||
            trxType.equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_SERIES_TRANSFER) ||
            trxType.equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_HF_SERIES_AMT) ||
            trxType.equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_HF_SERIES_PCT))
        {
            shouldUndoChargeCodes = true;
        }

        return shouldUndoChargeCodes;
    }

    /**
     * Undos any prior ChargeCodes applied to the corresponding Investment.
     */
    public void undoChargeCodes()
    {
        if (shouldUndoChargeCodes())
        {
            EDITTrxHistory editTrxHistory = getEDITTrxHistory();

            editTrxHistory.undoChargeCodes();
        }
    }

    /**
     * Gets the associated InvestmentHistory, if any.
     */
    private EDITTrxHistory getEDITTrxHistory()
    {
        EDITTrxHistory editTrxHistory = EDITTrxHistory.findBy_EDITTrxPK(getPK());

        return editTrxHistory;
    }
}
