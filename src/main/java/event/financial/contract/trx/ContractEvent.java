/*
 * User: cgleason
 * Date: Sep 15, 2003
 * Time: 3:39:11 PM
 *
 * (c) 2000-2008 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package event.financial.contract.trx;

import batch.business.*;
import businesscalendar.*;
import client.ClientAddress;
import client.dm.dao.PreferenceDAO;
import contract.*;
import contract.component.NewBusinessUseCaseComponent;
import contract.business.NewBusinessUseCase;
import contract.dm.composer.*;
import contract.dm.composer.VOComposer;
import contract.util.*;
import edit.common.*;
import edit.common.exceptions.*;
import edit.common.vo.*;
import edit.portal.widget.ContractDetailDoubleTableModel;
import edit.services.*;
import edit.services.db.hibernate.*;
import edit.services.logging.*;
import engine.*;
import event.dm.dao.DAOFactory;
import event.financial.client.trx.*;
import event.financial.group.strategy.*;
import event.financial.group.trx.*;
import event.*;
import fission.utility.*;
import logging.*;
import role.ClientRole;
import role.dm.composer.*;

import java.util.*;
import java.math.BigDecimal;

import group.*;
import edit.services.db.CRUD;
import edit.services.db.CRUDFactory;
import edit.services.db.ConnectionFactory;


public class ContractEvent
{
    private static int MA_TRX_TERMINATION_YRS = 100;

    private static String currentYear = null;

    /**
     * Executes the set of ClientTrx associated with the specified ContractSetup.
     * @param contractSetupVO
     * @throws EDITEventException
     */
    public void executeRealTime(ContractSetupVO contractSetupVO) throws Exception
    {
        ClientSetupVO[] clientSetupVO = contractSetupVO.getClientSetupVO();

    	// Get associated editTrxVOs and determine if there is an instance of BC trying to go to incomplete while contract has pending PY transaction(s)
        // If so, cancel PY trx, refund all contract suspense and continue processing to allow contract to go to incomplete
        boolean containsIncompleteTrx = false;
        boolean containsPYTrx = false; 
        
        Long segmentPK = contractSetupVO.getSegmentFK();
        Segment segment = Segment.findByPK(segmentPK);
        
        EDITTrxVO drivingTrxVO = clientSetupVO[0].getEDITTrxVO(0);
        String operator = drivingTrxVO.getOperator();
        
        int executionMode = ClientTrx.REALTIME_MODE;
		String cycleDate = new EDITDate().getFormattedDate();
        
		ProductStructure productStructure = ProductStructure.findByPK(segment.getProductStructureFK().longValue());
        String productType = Util.initString(productStructure.getProductTypeCT(), ProductStructure.VARIABLE_PRODUCT);
		
        EDITTrxVO[] allEDITTrxVO = DAOFactory.getEDITTrxDAO().findAllBySegmentPK_AND_EffectiveDate_GTE_AND_LTE_NA_STATUS(segmentPK, drivingTrxVO.getEffectiveDate(), cycleDate, executionMode, productType);
        
        // Check for scenario of contract going to Incomplete with pending PY
        if (allEDITTrxVO != null && allEDITTrxVO.length > 0)
        {
	        for(EDITTrxVO editTrxVO : allEDITTrxVO)
	    	{        	
	    		ContractSetupVO[] contractSetupVOs = DAOFactory.getContractSetupDAO().findByEDITTrxPK(editTrxVO.getEDITTrxPK());
	    		
	            String transactionType = editTrxVO.getTransactionTypeCT();
	        	
	        	if (contractSetupVOs != null && !editTrxVO.getStatus().equalsIgnoreCase(EDITTrx.STATUS_REVERSAL))
	        	{
	        		for (int i = 0; i<contractSetupVOs.length; i++)
	        		{
	        			String complexChangeType = contractSetupVOs[i].getComplexChangeTypeCT();
	        			
	        			if (complexChangeType != null && complexChangeType.equalsIgnoreCase(ContractSetup.COMPLEXCHANGETYPECT_INCOMPLETE))
	        			{
	        				containsIncompleteTrx = true;
	        			}
	    			}
	    			
	    			if (transactionType != null && transactionType.equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_PREMIUM))
	    			{
	    				containsPYTrx = true;
	    			}
	    			
	    			if (containsIncompleteTrx && containsPYTrx)
	    			{
	    				break;
	    			}
	    			
	        	}
	    	}
        }
    	
    	if (containsIncompleteTrx && containsPYTrx)
		{
    		CRUD crud = null;
			
			try
			{
				crud = CRUDFactory.getSingleton().getCRUD(ConnectionFactory.EDITSOLUTIONS_POOL);
				
				for (EDITTrxVO editTrxVO : allEDITTrxVO)
				{
					// Terminate all pending 'PY' trx
					if (editTrxVO.getTransactionTypeCT().equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_PREMIUM) && 
							editTrxVO.getPendingStatus().equalsIgnoreCase(EDITTrx.PENDINGSTATUS_PENDING))
					{
	    				editTrxVO.setStatus(EDITTrx.STATUS_REVERSAL);
	    				editTrxVO.setPendingStatus(EDITTrx.PENDINGSTATUS_TERMINATED);
	    				editTrxVO.setReversalReasonCodeCT("Incomplete");
	    				editTrxVO.setTerminationTrxFK(drivingTrxVO.getEDITTrxPK());
						
						crud.createOrUpdateVOInDB(editTrxVO);
    				}
					
					// If there are other BC trx to Incomplete, terminate them so only the driving BC trx will be processed
					if (editTrxVO.getTransactionTypeCT().equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_BILLING_CHANGE) && 
							editTrxVO.getPendingStatus().equalsIgnoreCase(EDITTrx.PENDINGSTATUS_PENDING) &&
							editTrxVO.getEDITTrxPK() != drivingTrxVO.getEDITTrxPK())
					{
						ContractSetupVO[] contractSetupVOs = DAOFactory.getContractSetupDAO().findByEDITTrxPK(editTrxVO.getEDITTrxPK());
						String complexChangeType = contractSetupVOs[0].getComplexChangeTypeCT();
						
						if (complexChangeType != null && complexChangeType.equalsIgnoreCase(ContractSetup.COMPLEXCHANGETYPECT_INCOMPLETE))
	        			{
							editTrxVO.setStatus(EDITTrx.STATUS_REVERSAL);
		    				editTrxVO.setPendingStatus(EDITTrx.PENDINGSTATUS_TERMINATED);
		    				editTrxVO.setReversalReasonCodeCT("DuplicateTrx");
							
							crud.createOrUpdateVOInDB(editTrxVO);
	        			}
					}
				}
			}
			catch (Exception e)
	        {
				System.out.println("ERROR - Attempted to Cancel Pending PY Transaction for a Contract Going to Incomplete");
				System.out.println(e);
	            e.printStackTrace();

	            Logging.getLogger(Logging.GENERAL_EXCEPTION).error(new LogEvent(e));
            	Log.logGeneralExceptionToDatabase(null, e);
            	
	            throw e;  
	        }
	        finally
	        {
	            if (crud != null)
	            {
	                crud.close();
	            }
	        }
	
			updateToIncomplete(drivingTrxVO, operator);
		}

        List<ClientTrx> clientTrxs = new ArrayList<>();

        for (int i = 0; i < clientSetupVO.length; i++)
        {
            // Every ClientSetupVO should have one, and only one, EDITTrxVO at this point.
            ClientTrx currentClientTrx = new ClientTrx(clientSetupVO[i].getEDITTrxVO(0));

            if (currentClientTrx.isBackdated())  
            {
                currentClientTrx.setExecutionMode(ClientTrx.REALTIME_MODE);

                currentClientTrx.setCycleDate(new EDITDate().getFormattedDate());

                //Must know the driving transaction code for Full Removal processing in Undo processing
                currentClientTrx.setDrivingBackdatedTrxCode(currentClientTrx.getTransactionTypeCT());

                clientTrxs.add(currentClientTrx);
            }
        }

        executeTransactions(clientTrxs);
        
        if (containsIncompleteTrx && containsPYTrx)
		{
        	// Set newly created suspense record to 'R' to remove it from Suspense UI (no further processing necessary)
			String contractNumber = segment.getContractNumber();
	        
	    	CRUD crud = null;
	        
	        try
			{				
				crud = CRUDFactory.getSingleton().getCRUD(ConnectionFactory.EDITSOLUTIONS_POOL);
				
		    	event.business.Event eventComponent = new event.component.EventComponent();
		        SuspenseVO[] suspenses = eventComponent.composeSuspenseVOByUserDefNumber(contractNumber, new ArrayList());
		
		        if (suspenses != null)
			    {  		
					for (SuspenseVO suspense : suspenses)
					{
						if (new EDITDate(suspense.getProcessDate()).equals(new EDITDate()) && suspense.getAccountingPendingInd().equalsIgnoreCase("N") &&
								suspense.getDirectionCT().equalsIgnoreCase("Remove") && suspense.getMaintenanceInd().equalsIgnoreCase("M"))
						{
							suspense.setMaintenanceInd("R");
							suspense.setReasonCodeCT("RefundInc");
							crud.createOrUpdateVOInDB(suspense);
						}
					}
			    } 
	        }
	        catch(Exception e)
	        {                
	        	System.out.println("Error Updating Suspense Contract Number: " + contractNumber);
	            e.printStackTrace();
	            
	            Logging.getLogger(Logging.GENERAL_EXCEPTION).error(new LogEvent(e));
	        	Log.logGeneralExceptionToDatabase(null, e);

	            throw e;
	        }
	        finally
	        {
	        	if (crud != null) crud.close();
	        	crud = null;
	        }    
		}
    }

    /**
     * Executes the set of ClientTrx associated with the specified Segment
     * @param segmentPK
     * @param cycleDate
     * @param operator
     * @throws EDITEventException
     */
    public void executeBatch(long segmentPK, String cycleDate, String operator) throws Exception
    {
        // Find all EDITTrx for the current SegmentPK
        String[] pendingStatuses = new String[] { "B", "C", "F", "P", "S", "M" };
        EDITTrxVO[] editTrxVO = DAOFactory.getEDITTrxDAO().findBySegmentPK_AND_EffectiveDateLTE_AND_PendingStatus(segmentPK, cycleDate, pendingStatuses);

        List<ClientTrx> clientTrxs = new ArrayList<>();
        
        if (editTrxVO != null)
        {
            // Build the ClientTrx associated with the EDITTrx
            for (int j = 0; j < editTrxVO.length; j++)
            {
                ClientTrx currentClientTrx = new ClientTrx(editTrxVO[j], operator);

                currentClientTrx.setExecutionMode(ClientTrx.BATCH_MODE);

                currentClientTrx.setCycleDate(cycleDate);

                clientTrxs.add(currentClientTrx);
            }
        }

        executeTransactions(clientTrxs);

        clientTrxs.clear();
    }
    
    /**
     * Executes the set of ClientTrx associated with the specified Segment
     * @param segmentPK
     * @param cycleDate
     * @param operator
     * @throws EDITEventException
     */
    public void executeBatch(long segmentPK, String cycleDate, String operator, int index, int totalPKs) throws Exception
    
    {
		EditServiceLocator.getSingleton().getBatchAgent().getBatchStat(Batch.BATCH_JOB_RUN_DAILY_BATCH).setBatchMessage(
    			"Step 7 of 7 (Threaded) - Executing Batch Trx - Segment " + (index+1) + " out of " + totalPKs);
    	
        // Find all EDITTrx for the current SegmentPK
        String[] pendingStatuses = new String[] { "B", "C", "F", "P", "S", "M" };
        EDITTrxVO[] editTrxVO = DAOFactory.getEDITTrxDAO().findBySegmentPK_AND_EffectiveDateLTE_AND_PendingStatus(segmentPK, cycleDate, pendingStatuses);

        List<ClientTrx> clientTrxs = new ArrayList<>();
        
        if (editTrxVO != null)
        {
            // Build the ClientTrx associated with the EDITTrx
            for (int j = 0; j < editTrxVO.length; j++)
            {
                ClientTrx currentClientTrx = new ClientTrx(editTrxVO[j], operator);

                currentClientTrx.setExecutionMode(ClientTrx.BATCH_MODE);

                currentClientTrx.setCycleDate(cycleDate);

                clientTrxs.add(currentClientTrx);
            }
        }

        executeTransactions(clientTrxs);

        clientTrxs.clear();
    }

    private void executeTransactions(List clientTrx) throws Exception
    {
        // Sort according to ClientStrategy
        Collections.sort(clientTrx);

        // Execute each ClientTrx. Should any one fail, the rest of the ClientTrxs for this Segment will not execute.
        for (int j = 0; j < clientTrx.size(); j++)
        {
            try
            {
                ((ClientTrx) clientTrx.get(j)).execute();

                EditServiceLocator.getSingleton().getBatchAgent().getBatchStat(Batch.BATCH_JOB_RUN_DAILY_BATCH).updateSuccess();
            }
            catch (EDITEventException e)
            {
            	boolean contractSkipped = false;
            	
            	ValidationVO[] validationVOs = e.getValidationVO();
            	
            	if (validationVOs != null && validationVOs[0].getMessage().equalsIgnoreCase("Transaction not Allowed for Contract Status"))
            	{
            		contractSkipped = true;
            	}
            	
            	if(contractSkipped)
            	{
            		EditServiceLocator.getSingleton().getBatchAgent().getBatchStat(Batch.BATCH_JOB_RUN_DAILY_BATCH).updateSkip();
            	}
            	else
            	{
            		EditServiceLocator.getSingleton().getBatchAgent().getBatchStat(Batch.BATCH_JOB_RUN_DAILY_BATCH).updateFailure();
            	}
            	
                if (!e.isLogged())
                {
                	Logging.getLogger(Logging.GENERAL_EXCEPTION).error(new LogEvent(e));

                	Log.logGeneralExceptionToDatabase(null, e);
                
                	e.setLogged(true);
                }
                
                System.out.println("Logging To OFAC For EDITTrxPK");
                
                Logging.getLogger(Logging.OFAC).error(new LogEvent(((ClientTrx) clientTrx.get(j)).getEDITTrxVO().getEDITTrxPK()+ ""));

                throw e;
            }
            catch (Exception e)
            {
              EditServiceLocator.getSingleton().getBatchAgent().getBatchStat(Batch.BATCH_JOB_RUN_DAILY_BATCH).updateFailure();
            	
              Logging.getLogger(Logging.GENERAL_EXCEPTION).error(new LogEvent(e));

              Log.logGeneralExceptionToDatabase(null, e);
              
              System.out.println("Logging To OFAC For EDITTrxPK");
              
              Logging.getLogger(Logging.OFAC).error(new LogEvent(((ClientTrx) clientTrx.get(j)).getEDITTrxVO().getEDITTrxPK()+ ""));

              throw new EDITEventException(e.getMessage());
            }
        }
    }
    
    /**
     * Prepare the segment and suspense to process the contract to Incomplete
     * @param editTrxVO
     * @param operator
     * @throws Exception
     */
    public void updateToIncomplete(EDITTrxVO editTrxVO, String operator) throws Exception
    {  
        String contractNumber = EDITTrx.findBy_PK(new Long(editTrxVO.getEDITTrxPK())).getContractNumber();
    	
        CRUD crud = null;
        
        try
		{				
			crud = CRUDFactory.getSingleton().getCRUD(ConnectionFactory.EDITSOLUTIONS_POOL);
			
			List voInclusionList = new ArrayList();
			voInclusionList.add(SegmentVO.class);
          
	        SegmentVO segmentVO = new VOComposer().composeSegmentVO(contractNumber, voInclusionList);
	        
	        if (segmentVO != null)
	        {	            
	            // Set Segment and riders to status of 'Incomplete'
	            segmentVO.setSegmentStatusCT(Segment.SEGMENTSTATUSCT_INCOMPLETE);
	            segmentVO.setStatusChangeDate(new EDITDate().getFormattedDate());
	            segmentVO.setQuoteDate(new EDITDate().getFormattedDate());
	            crud.createOrUpdateVOInDB(segmentVO);
	            
	            SegmentVO[] riders = segmentVO.getSegmentVO();
	
	            if (riders != null)
	            {
	                for (int r = 0; r < riders.length; r++)
	                {
	                    riders[r].setSegmentStatusCT(Segment.SEGMENTSTATUSCT_INCOMPLETE);
	                    crud.createOrUpdateVOInDB(riders[r]);
	                }
	            }
	        }
	           
	        // Get all suspense records associated with this contract    	
	    	event.business.Event eventComponent = new event.component.EventComponent();
	        SuspenseVO[] suspenses = eventComponent.composeSuspenseVOByUserDefNumber(contractNumber, new ArrayList());
	
	        if (suspenses != null)
		    {  		
				for (SuspenseVO suspense : suspenses)
				{
					// Set pending suspense to 0
					suspense.setPendingSuspenseAmount(new EDITBigDecimal().getBigDecimal());
					crud.createOrUpdateVOInDB(suspense);
				}
		    } 
        }
        catch(Exception e)
        {                
        	System.out.println("Error on Incomplete for Contract Number: " + contractNumber);
            e.printStackTrace();
            
            Logging.getLogger(Logging.GENERAL_EXCEPTION).error(new LogEvent(e));
        	Log.logGeneralExceptionToDatabase(null, e);

            throw e;
        }
        finally
        {
        	if (crud != null) crud.close();
        	crud = null;
        }
    }
    
    /**
     * Builds a CommissionPhaseVO record for a terminating PremiumDue record
     * @param premiumDueVO
     * @throws Exception
     */
    public CommissionPhaseVO terminatingCommissionPhase(PremiumDueVO premiumDueVO) throws Exception
    {  
    	CommissionPhaseVO commissionPhaseVO = new CommissionPhaseVO(); 
    	commissionPhaseVO.setPremiumDueFK(premiumDueVO.getPremiumDuePK());
    	commissionPhaseVO.setCommissionPhaseID(1);
    	commissionPhaseVO.setExpectedMonthlyPremium(BigDecimal.ZERO);
    	commissionPhaseVO.setPrevCumExpectedMonthlyPrem(BigDecimal.ZERO);
    	commissionPhaseVO.setEffectiveDate(premiumDueVO.getEffectiveDate());
    	commissionPhaseVO.setPriorExpectedMonthlyPremium(premiumDueVO.getBillAmount());

    	return commissionPhaseVO;
    }

    public void commitContract(Segment segment, String operator, String lastDayOfMonthInd, String incomeMaturityAgeString, String suppressPolicyPages) throws Exception
    {
        SegmentVO segmentVO = (SegmentVO) segment.getVO();

//        String beforeStatusValue = segment.getSegmentStatusCT();

        checkRequirements(segment);

        if (segment.getSegmentNameCT().equalsIgnoreCase("Payout"))
        {
            setPaymentStartDate(segment, incomeMaturityAgeString);
        }

        String segmentStatus = segment.getSegmentStatusCT();
        String postIssueStatus = segment.getPostIssueStatusCT();

        long segmentPK = segment.getPK();

        contract.business.Contract contractComponent = new contract.component.ContractComponent();

        List clientTrx = new ArrayList();

        segment.setOperator(operator);
        if (postIssueStatus == null)
        {
            segmentPK = contractComponent.saveSegmentForNBWithHibernate(segment, "");

        }
        else if (postIssueStatus.equalsIgnoreCase(Segment.SEGMENTSTATUSCT_ISSUEPENDINGPREMIUM) || postIssueStatus.equalsIgnoreCase(Segment.SEGMENTSTATUSCT_ISSUEPENDINGREQ))
        {
            segmentPK = formatContractData(segment, operator, lastDayOfMonthInd, incomeMaturityAgeString, "");
        }


        // If the PostIssueStatus field had a state-change from NULL to "IssuePending".
        if ((segmentStatus.equalsIgnoreCase(Segment.SEGMENTSTATUSCT_PENDING) ||
             segmentStatus.equalsIgnoreCase(Segment.SEGMENTSTATUSCT_SUBMITTED) ||
             segmentStatus.equalsIgnoreCase(Segment.SEGMENTSTATUSCT_REOPEN)) &&
            (postIssueStatus != null))
        {
            //All products now need an Issue trx created and processed through scripts 05/21/08
                clientTrx.addAll(Arrays.asList(createIssueTrxGroupSetup(segment, operator, suppressPolicyPages)));
        }

        if (postIssueStatus != null && (postIssueStatus.equalsIgnoreCase(Segment.SEGMENTSTATUSCT_ISSUEPENDINGPREMIUM) || postIssueStatus.equalsIgnoreCase(Segment.SEGMENTSTATUSCT_ISSUEPENDINGREQ)))
        {
            Suspense[] suspense = Suspense.findByUserDefNumberForIssue(segmentVO.getContractNumber());

            int numDeposits = 0;

            if (suspense != null)
            {
                numDeposits = suspense.length;
            }

            if (numDeposits == 0)
            {
                AgentHierarchyVO[] agentHierarchyVOs = segmentVO.getAgentHierarchyVO();

                for (int a = 0; a < agentHierarchyVOs.length; a++)
                {
                    if (new EDITBigDecimal(agentHierarchyVOs[a].getAdvancePremium()).isGT("0"))
                    {
                        clientTrx.addAll(Arrays.asList(createAdvanceCommissionTrxGroupSetup(segment, agentHierarchyVOs[a], operator)));
                    }
                }
            }

            try
            {
                // All the transactions created for issue transaction are built and then executed at once.
                // Previously all the issue transactions created are executed individually one after the other.
                clientTrx.addAll(Arrays.asList(createTransactionGroupSetupsForIssue(segment, operator)));

                List executableClientTrx = new ArrayList();

                for (int i = 0; i < clientTrx.size(); i++)
                {
                    ClientTrx trx = (ClientTrx) clientTrx.get(i);

                    // execute only back dated transactions.
                    if (trx.isBackdated())
                    {
                        trx.setExecutionMode(ClientTrx.REALTIME_MODE);

                        trx.setCycleDate(new EDITDate().getFormattedDate());

                        executableClientTrx.add(trx);
                    }
                }

                executeTransactions(executableClientTrx);
            }
            catch (Exception e)
            {
                e.printStackTrace();
                throw new Exception("Contract Issued - Transaction Errored");
            }
        }
    }

    /**
     * AutoIssue process invoked by Casetracking Supplemental processing
     * @param segment
     * @param operator
     * @throws Exception
     */
    public void commitContractAutoIssue(Segment segment, String operator)
    {
        try
        {
            //only deferred Annuity processing
            createIssueTrxGroupSetup(segment, operator, "true");

            currentYear = new EDITDate().getFormattedYear();

            createTransactionGroupSetupsForIssue(segment, operator);

            currentYear = null;
        }
        catch (Exception e)
        {
            e.printStackTrace();
//            throw new Exception("Contract Issued - Transaction Errored");
        }
    }



    /**
     * Upon issuing a policy, creates and executes the appropriate set of transactions.  If the transacations have
     * been specified in the config file, gets those transaction types and executes them in order.  If none are
     * in the config file, uses hardcoded defaults
     * @param segmentVO
     * @throws Exception
     */
    public ClientTrx[] createTransactionGroupSetupsForIssue(Segment segment, String operator) throws Exception, EDITEventException
    {
        String[] issueTransactions = getIssueTransactions(segment);

        List clientTrx = new ArrayList();

        if (issueTransactions != null)
        {
            for (int i = 0; i < issueTransactions.length; i++)
            {
                String trxCode = issueTransactions[i];

                if (trxCode.equals("PE")) // PolicyYearEnd
                {
                    clientTrx.addAll(Arrays.asList(createPolicyYearEndTrxGroupSetup(segment, operator)));
                }
                else if (trxCode.equals("CY")) // CalendarYearEnd
                {
                    clientTrx.addAll(Arrays.asList(createCalendarYearEndTrxGroupSetup(segment, operator)));
                }
                else if (trxCode.equals("PQ")) // PolicyQuarter
                {
                    clientTrx.addAll(Arrays.asList(createPolicyQuarterTrxGroupSetup(segment, operator)));
                }
                else if (trxCode.equals("MD")) // ModalDeduction
                {
                    clientTrx.addAll(Arrays.asList(createModalDeductionTrxGroupSetup(segment, operator)));
                }
                else if (trxCode.equals("PY")) // Premium
                {
                    clientTrx.addAll(Arrays.asList(createPremiumTrxGroupSetup(segment, operator)));
                }
                else if (trxCode.equals("MA")) // Maturity
                {
                    clientTrx.addAll(Arrays.asList(createMaturityTrxGroupSetup(segment, operator)));
                }
                else if (trxCode.equals("MF")) //Monthly Fee
                {
                    clientTrx.addAll(Arrays.asList(createMonthlyFeeTrxGroupSetup(segment, operator)));
                }
                else if (trxCode.equals("PO")) //Payout
                {
                    clientTrx.addAll(Arrays.asList(createPayoutTrxGroupSetup(segment, operator)));
                }
                else if (trxCode.equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_STATEMENT))
                {
                    clientTrx.addAll(Arrays.asList(createStatementTrxGroupSetup(segment, operator)));
                }
            }
        }
        else
        {
            if (segment.getOptionCodeCT().equalsIgnoreCase("DFA") ||
                segment.getOptionCodeCT().equalsIgnoreCase("Payout"))
            {
                clientTrx.add(Arrays.asList(createPolicyYearEndTrxGroupSetup(segment, operator)));
                clientTrx.add(Arrays.asList(createCalendarYearEndTrxGroupSetup(segment, operator)));
                clientTrx.add(Arrays.asList(createStatementTrxGroupSetup(segment, operator)));
                clientTrx.add(Arrays.asList(createPremiumTrxGroupSetup(segment, operator))); // this will also execute trx
            }
        }

        return (ClientTrx[]) clientTrx.toArray(new ClientTrx[clientTrx.size()]);
    }

    private void setPaymentStartDate(Segment segment, String incomeMaturityAgeString) throws Exception
    {
        SegmentVO segmentVO = (SegmentVO) segment.getVO();

        PayoutVO[] payoutVO = segmentVO.getPayoutVO();

        EDITDate maturityDate = new EDITDate(payoutVO[0].getPaymentStartDate());

        EDITDate polEffDate = segment.getEffectiveDate();

        String qualNonQual = segment.getQualNonQualCT();

        if (maturityDate != null && maturityDate.equals(new EDITDate(EDITDate.DEFAULT_MAX_DATE)))
        {
            if (segment.getOptionCodeCT().equalsIgnoreCase(Segment.OPTIONCODECT_DEFERRED_ANNUITY))
            {
                EDITDate dateOfBirth = null;
                EDITDate annDOB = null;

                boolean ownerIsCorporate = false;

                List voInclusionList = new ArrayList();
                voInclusionList.add(ClientDetailVO.class);

                ContractClientVO[] contractClientVOs = segmentVO.getContractClientVO();

                if (contractClientVOs != null)
                {
                    for (int c = 0; c < contractClientVOs.length; c++)
                    {
                        ClientRoleVO clientRoleVO = new ClientRoleComposer(voInclusionList).compose(contractClientVOs[c].getClientRoleFK());
                        ClientDetailVO clientDetailVO = (ClientDetailVO) clientRoleVO.getParentVO(ClientDetailVO.class);
                        String roleType = clientRoleVO.getRoleTypeCT();

                        if (roleType.equalsIgnoreCase("OWN"))
                        {
                            if (clientDetailVO.getTrustTypeCT().equalsIgnoreCase("Corporate"))
                            {
                                ownerIsCorporate = true;
                            }
                            else
                            {
                                if (clientDetailVO.getBirthDate() != null)
                                {
                                    dateOfBirth = new EDITDate(clientDetailVO.getBirthDate());
                                }
                            }
                        }

                        if (roleType.equalsIgnoreCase("ANN"))
                        {
                            if (clientDetailVO.getBirthDate() != null)
                            {
                                annDOB = new EDITDate(clientDetailVO.getBirthDate());
                            }
                        }
                    }
                }

                if (ownerIsCorporate)
                {
                    dateOfBirth = annDOB;
                }

                if ((dateOfBirth != null) && !dateOfBirth.equals(new EDITDate(EDITDate.DEFAULT_MAX_DATE)))
                {
                    EDITDate dob = dateOfBirth;

                    if (incomeMaturityAgeString != null)
                    {
    //                    for (int i = 0; i < incomeMaturityDate.length; i++)
    //                    {
    //                        if ((incomeMaturityDate[i].getQualNonQualInd() != null) && incomeMaturityDate[i].getQualNonQualInd().equalsIgnoreCase(qualNonQual))
    //                        {
    //                            int years = Integer.parseInt(incomeMaturityDate[i].getYears());
    //                            String months = incomeMaturityDate[i].getMonths();
    //                            dob.addYears(years);
    //
    //                            if (months != null)
    //                            {
    //                                dob.addMonths(Integer.parseInt(months));
    //                            }
    //                        }
    //                        else if (incomeMaturityDate[i].getQualNonQualInd() == null)
    //                        {
    //                            dob.addYears(Integer.parseInt(incomeMaturityDate[i].getYears()));
    //                        }
    //                    }
                        int index = incomeMaturityAgeString.indexOf(";");     // if age is 70 and 6 months, this string contains 70;6 from ui
                        int years = 0;
                        int months = 0;
                        if (index > 0)
                        {
                            years = Integer.parseInt(incomeMaturityAgeString.substring(0, index));
                            months = Integer.parseInt(incomeMaturityAgeString.substring(index + 1));
                        }
                        else
                        {
                            years = Integer.parseInt(incomeMaturityAgeString);
                        }

                        dob = dob.addYears(years);
                        dob = dob.addMonths(months);
                    }
                    else
                    {
                        dob = maturityDate;
                    }

    //                String[] newMaturityDate = Util.fastTokenizer(dob.getDateAsYYYYMMDD(), "/");
    //                polEffDate[0] = newMaturityDate[0];

                    EDITDate newPaymentStartDate = new EDITDate(dob.getFormattedYear(), polEffDate.getFormattedMonth(), polEffDate.getFormattedDay());

                    payoutVO[0].setPaymentStartDate(newPaymentStartDate.getFormattedDate());
                }
            }
        }
    }

    /**
     * Create an Issue Transaction for processing in PRASE
     * @param segmentVO
     * @throws Exception
     */
    public ClientTrx[] createIssueTrxGroupSetup(Segment segment, String operator, String suppressPolicyPages) throws Exception
    {
        GroupSetupVO isGroupSetupVO = buildDefaultGroupSetup(new Segment[] { segment });

        String transactionTypeCT = "IS";

        EDITDate effectiveDate = segment.getIssueDate();
        int taxYear = effectiveDate.getYear();

        EDITTrxVO newEditTrxVO = buildDefaultEDITTrxVO(transactionTypeCT, effectiveDate, taxYear, operator);

        if (suppressPolicyPages.equalsIgnoreCase("true"))
        {
            newEditTrxVO.setNoCorrespondenceInd("Y");
        }
        else
        {
            newEditTrxVO.setNoCorrespondenceInd("N");
        }

        return new GroupTrx().saveOnlyGroupSetup(isGroupSetupVO, newEditTrxVO, "Issue", segment.getOptionCodeCT(), segment.getProductStructureFK().longValue());
    }

    public long formatContractData(Segment segment, String operator, 
                                   String lastDayOfMonthInd,
                                   String incomeMaturityAgeString,
                                   String conversionValue) throws Exception
    {
        contract.business.Contract contractComponent = new contract.component.ContractComponent();

        PayoutVO payoutVO = null;
        SegmentVO segmentVO = (SegmentVO)segment.getVO();
        if (segmentVO.getPayoutVOCount() > 0)
        {
            payoutVO = segmentVO.getPayoutVO(0);
        }

        EDITDate ceLumpSumDate = null;
        EDITDate finalPaymentDate = null;

        long segmentPK = 0;

        if (payoutVO != null)
        {
//            StringTokenizer st = new StringTokenizer(payoutVO.getPaymentStartDate(), "/");
            EDITDate paymentStartDate = new EDITDate(payoutVO.getPaymentStartDate());

            String optionCode = segment.getOptionCodeCT();
            String frequency = payoutVO.getPaymentFrequencyCT();

            if (optionCode.equalsIgnoreCase("PCA") || optionCode.equalsIgnoreCase("LPC") || optionCode.equalsIgnoreCase("TML") || optionCode.equalsIgnoreCase("JPC"))
            {
//                String terminationYear = st.nextToken();
//                String terminationMonth = st.nextToken();
//                String terminationDay = st.nextToken();
//                int termYear = Integer.parseInt(terminationYear);
//                termYear += payoutVO.getCertainDuration();
//                terminationYear = termYear + "";

                EDITDate terminationDate = paymentStartDate.addYears(payoutVO.getCertainDuration());
                finalPaymentDate = terminationDate;

                EDITDate certainPeriodEndDate = terminationDate;

                if (optionCode.equalsIgnoreCase("LPC") || optionCode.equalsIgnoreCase("JPC"))
                {
                    terminationDate = new EDITDate(EDITDate.DEFAULT_MAX_DATE);
                }

                if (!terminationDate.equals(new EDITDate(EDITDate.DEFAULT_MAX_DATE)))
                {
                    EDITDate ceTermDate = terminationDate;

                    if (ceLumpSumDate != null)
                    {
                        if (ceLumpSumDate.after(ceTermDate))
                        {
                            terminationDate = ceLumpSumDate.addDays(1);
                        }
                        else
                        {
                            terminationDate = ceTermDate.addDays(1);
                        }
                    }
                }

                segment.setTerminationDate(terminationDate);
                payoutVO.setCertainPeriodEndDate(certainPeriodEndDate.getFormattedDate());
            }
            else
            {
                // If OptionCode is NOT 'Amount Certain'
                if (!optionCode.equalsIgnoreCase("AMC"))
                {
                    segment.setTerminationDate(new EDITDate(EDITDate.DEFAULT_MAX_DATE));
                    payoutVO.setCertainPeriodEndDate(new EDITDate(EDITDate.DEFAULT_MAX_DATE).getFormattedDate());
                }
            }

            if (optionCode.equalsIgnoreCase("AMC"))
            {
                finalPaymentDate = new EDITDate(payoutVO.getFinalPaymentDate());
            }

            EDITDate contractTerminationDate = segment.getTerminationDate();

            if (contractTerminationDate.equals(new EDITDate(EDITDate.DEFAULT_MAX_DATE)))
            {
                payoutVO.setFinalPaymentDate(contractTerminationDate.getFormattedDate());
            }
            else if (!optionCode.equalsIgnoreCase(Segment.OPTIONCODECT_TRADITIONAL_LIFE))
            {
                EDITDate ceFinalPaymentDate = finalPaymentDate.subtractMode(frequency);

                payoutVO.setFinalPaymentDate(ceFinalPaymentDate.getFormattedDate());
            }

            segment.setStatusChangeDate(new EDITDate());

            payoutVO.setNextPaymentDate(payoutVO.getPaymentStartDate());
        }
            //check if the amount field needs to be set
//            setAmount(segmentVO);  -- Commented out so that the Amount on Segment won't get reset for Payouts

            if (segment.getSegmentNameCT().equalsIgnoreCase("Life") ||
                segment.getOptionCodeCT().equalsIgnoreCase("Traditional"))
            {
                segment.setTerminationDate(getCalculatedTerminationDate(segment, incomeMaturityAgeString));
            }

            if ((segment.getQualNonQualCT() != null) && (segment.getQualNonQualCT().equalsIgnoreCase("Qualified") && !segment.getQualifiedTypeCT().equalsIgnoreCase("Roth")))
            {
                segment = createRMDTable(segment);
            }

            segment.setOperator(operator);
            segmentPK = contractComponent.saveSegmentForNBWithHibernate(segment, conversionValue);
//        }

        return segmentPK;
    }

    /**
     * Creates an RMD Table for the given Segment
     * @param segmentVO
     * @return
     * @throws Exception
     */
    private Segment createRMDTable(Segment segment) throws Exception
    {
        SegmentVO segmentVO = (SegmentVO) segment.getVO();

        ContractClientVO[] contractClientVOs = segmentVO.getContractClientVO();

        role.business.Lookup roleLookup = new role.component.LookupComponent();

        EDITDate seventyAndHalfDate = null;

        List voInclusionList = new ArrayList();
        voInclusionList.add(ClientDetailVO.class);

        boolean getAnnuitant = false;

        for (int i = 0; i < contractClientVOs.length; i++)
        {
            ClientRoleVO clientRoleVO = roleLookup.composeClientRoleVO(contractClientVOs[i].getClientRoleFK(), voInclusionList);

            if (clientRoleVO.getRoleTypeCT().equalsIgnoreCase("OWN"))
            {
                ClientDetailVO clientDetailVO = (ClientDetailVO) clientRoleVO.getParentVO(ClientDetailVO.class);
                if (clientDetailVO.getTrustTypeCT().equalsIgnoreCase("Individual"))
                {
                    String dateOfBirth = clientDetailVO.getBirthDate();

                    if (dateOfBirth != null)
                    {
                        EDITDate edDOB = new EDITDate(dateOfBirth);
//                        edDOB = edDOB.addYears(70);
//                        edDOB = edDOB.addMonths(6);
                        seventyAndHalfDate = edDOB.getSeventyHalfDate();

//                        String[] seventyAndHalfDateTokenized = Util.fastTokenizer(seventyAndHalfDate, "/");
//                        if (seventyAndHalfDateTokenized[1].equals("02") &&
//                            seventyAndHalfDateTokenized[2].equalsIgnoreCase("29"))
//                        {
//                            seventyAndHalfDate = EDITDate.formatDate(seventyAndHalfDateTokenized[0], seventyAndHalfDateTokenized[1], "28");
//                        }

                        i = contractClientVOs.length;
                    }
                }
                else
                {
                    getAnnuitant = true;
                    i = contractClientVOs.length;
                }
            }
        }

        if (getAnnuitant)
        {
            for (int i = 0; i < contractClientVOs.length; i++)
            {
                ClientRoleVO clientRoleVO = roleLookup.composeClientRoleVO(contractClientVOs[i].getClientRoleFK(), voInclusionList);

                if (clientRoleVO.getRoleTypeCT().equalsIgnoreCase("ANN"))
                {
                    ClientDetailVO clientDetailVO = (ClientDetailVO) clientRoleVO.getParentVO(ClientDetailVO.class);
                    String dateOfBirth = clientDetailVO.getBirthDate();

                    if (dateOfBirth != null)
                    {
                        EDITDate edDOB = new EDITDate(dateOfBirth);
//                        edDOB.addYears(70);
//                        edDOB.addMonths(6);
                        seventyAndHalfDate = edDOB.getSeventyHalfDate();
                        i = contractClientVOs.length;
                    }
                }
            }
        }

        RequiredMinDistributionVO rmdVO = new RequiredMinDistributionVO();
        rmdVO.setRequiredMinDistributionPK(0);
        rmdVO.setSegmentFK(SessionHelper.getPKValue(segment.getSegmentPK()));
        rmdVO.setSeventyAndHalfDate(seventyAndHalfDate.getFormattedDate());
        rmdVO.setAnnualDate(seventyAndHalfDate.getFormattedDate());
        rmdVO.setElectionCT("Opt2");

        segmentVO.addRequiredMinDistributionVO(rmdVO);

        return segment;
    }

    public ClientTrx[] createPayoutTrxGroupSetup(Segment segment, String operator) throws Exception
    {
        List clientTrx = new ArrayList();

        SegmentVO segmentVO = (SegmentVO) segment.getVO();

        Payout payout = new Payout(segmentVO.getPayoutVO(0));
        ContractClientVO[] contractClientVOs = segmentVO.getContractClientVO();

        EDITDate startDate = payout.getPaymentStartDate();

        String lastDayOfMonthInd = payout.getLastDayOfMonthInd();

        int taxYear = startDate.getYear();

        String optionCode = segment.getOptionCodeCT();

        int leadDays = 0;

        EDITDate stopDate = null;
        String lifeContingent = null;
        EDITDate ceDate = startDate;

        if (optionCode.equalsIgnoreCase("LOA") || optionCode.equalsIgnoreCase("LPC") ||
            optionCode.equalsIgnoreCase("LCR") || optionCode.equalsIgnoreCase("JPC") ||
            optionCode.equalsIgnoreCase("JSA"))
        {
            stopDate = new EDITDate(EDITDate.DEFAULT_MAX_DATE);
            lifeContingent = "AN";
        }

        else if (optionCode.equalsIgnoreCase("PCA") || optionCode.equalsIgnoreCase("TML"))
        {
            stopDate = payout.getCertainPeriodEndDate();

            if (optionCode.equalsIgnoreCase("TML"))
            {
                lifeContingent = "AN";
            }

            else
            {
                lifeContingent = "NotLifeContingent";
            }
        }

        else if (optionCode.equalsIgnoreCase("AMC"))
        {
            stopDate = segment.getTerminationDate();
            lifeContingent = "NotLifeContingent";
        }

        GroupSetupVO poGroupSetupVO = buildDefaultGroupSetup(new Segment[] { segment });

        if (!optionCode.equalsIgnoreCase("DFA"))
        {
            String transactionTypeCT = "PO";

            for (int c = 0; c < contractClientVOs.length; c++)
            {
                String disbSource = "";

                long clientRoleFK = contractClientVOs[c].getClientRoleFK();
                ClientRoleVO[] clientRoleVO = role.dm.dao.DAOFactory.getClientRoleDAO().findByClientRolePK(clientRoleFK, false, new ArrayList());

                if (clientRoleVO[0].getRoleTypeCT().equalsIgnoreCase("PAY"))
                {
                    long preferenceFK = clientRoleVO[0].getPreferenceFK();
                    PreferenceVO[] preferenceVO = client.dm.dao.DAOFactory.getPreferenceDAO().findByPreferencePK(preferenceFK, false, new ArrayList());
                    
                    if (preferenceVO == null || preferenceVO.length == 0)
                    {
                        long clientDetailFK = clientRoleVO[0].getClientDetailFK();
                        preferenceVO = new PreferenceDAO().findPrimaryByClientDetailPK(clientDetailFK, false, null);
                    }

                    disbSource = preferenceVO[0].getDisbursementSourceCT();

                    /* We are getting the lead days from Area Table to calculate
                      the effective date of the payout */
                    leadDays = getLeadDays(segmentVO, disbSource);

                    ceDate = ceDate.subtractDays(leadDays);

                    String poEffDate = ceDate.getFormattedDate();

                    if (poEffDate.compareTo(segmentVO.getEffectiveDate()) < 0)
                    {
                        poEffDate = segmentVO.getEffectiveDate();
                    }

                    ScheduledEventVO schedEventVO = new ScheduledEventVO();
                    schedEventVO.setScheduledEventPK(0);
                    schedEventVO.setStartDate(startDate.getFormattedDate());
                    schedEventVO.setStopDate(stopDate.getFormattedDate());
                    schedEventVO.setLastDayOfMonthInd(lastDayOfMonthInd);
                    schedEventVO.setFrequencyCT(payout.getPaymentFrequencyCT());
                    schedEventVO.setLifeContingentCT(lifeContingent);
                    schedEventVO.setCostOfLivingInd("N");

                    poGroupSetupVO.addScheduledEventVO(schedEventVO);

                    EDITTrxVO newEditTrxVO = buildDefaultEDITTrxVO(transactionTypeCT, new EDITDate(poEffDate), taxYear, operator);
                    newEditTrxVO.setDueDate(schedEventVO.getStartDate());

                    SaveGroup saveGroup = new SaveGroup(poGroupSetupVO, newEditTrxVO, "Commit", optionCode);

                    saveGroup.build();
                    clientTrx.addAll(Arrays.asList(saveGroup.save()));
                }
            }
        }

        return (ClientTrx[]) clientTrx.toArray(new ClientTrx[clientTrx.size()]);
    }

    /**
     * If config file specifies no processing of the Issue Trx this method gets invoked
     * @param segmentVO
     * @param operator
     * @param suppressPolicyPages
     * @throws Exception
     */
    public ClientTrx[] createIssueTrx(Segment segment, String operator, String suppressPolicyPages) throws Exception
    {
        GroupSetupVO isGroupSetupVO = buildDefaultGroupSetup(new Segment[] { segment });

        SegmentVO segmentVO = (SegmentVO) segment.getVO();

        ContractClientVO[] contractClientVO = segmentVO.getContractClientVO();

        String disbAddressTypeCT = null;
        String corrAddressTypeCT = null;

        if (contractClientVO != null)
        {
            for (int i = 0; i < contractClientVO.length; i++)
            {
                ClientRoleVO clientRoleVO = new ClientRoleComposer(new ArrayList()).compose(contractClientVO[i].getClientRoleFK());

                if ((clientRoleVO != null) && clientRoleVO.getRoleTypeCT().equalsIgnoreCase("OWN"))
                {
                    disbAddressTypeCT = contractClientVO[i].getDisbursementAddressTypeCT();
                    corrAddressTypeCT = contractClientVO[i].getCorrespondenceAddressTypeCT();

                    break;
                }
            }
        }

        EDITTrxVO newEditTrxVO = new EDITTrxVO();
        newEditTrxVO.setEDITTrxPK(0);
        newEditTrxVO.setEffectiveDate(segment.getIssueDate().getFormattedDate());
        newEditTrxVO.setTaxYear(segment.getEffectiveDate().getYear());

        //status = "g" for generated trx, avoids undo/redo on this trx
        newEditTrxVO.setStatus("G");
        newEditTrxVO.setPendingStatus("H");
        newEditTrxVO.setTransactionTypeCT("IS");
        newEditTrxVO.setNoAccountingInd("N");
        newEditTrxVO.setNoCommissionInd("N");
        newEditTrxVO.setZeroLoadInd("N");
        newEditTrxVO.setPremiumDueCreatedIndicator("N");

        if (suppressPolicyPages.equalsIgnoreCase("true"))
        {
            newEditTrxVO.setNoCorrespondenceInd("Y");
        }
        else
        {
            newEditTrxVO.setNoCorrespondenceInd("N");
        }

        newEditTrxVO.setOperator(operator);

        if (!suppressPolicyPages.equalsIgnoreCase("true"))
        {
            TransactionCorrespondenceVO[] transactionCorrespondenceVO = DAOFactory.getTransactionCorrespondenceDAO().findByTransactionType("IS");

            if (transactionCorrespondenceVO != null)
            {
                EDITTrxCorrespondenceVO editTrxCorrespondenceVO = new EDITTrxCorrespondenceVO();
                editTrxCorrespondenceVO.setEDITTrxCorrespondencePK(0);
                editTrxCorrespondenceVO.setEDITTrxFK(0);
                editTrxCorrespondenceVO.setCorrespondenceDate(segment.getIssueDate().getFormattedDate());
                editTrxCorrespondenceVO.setStatus("P");
                editTrxCorrespondenceVO.setTransactionCorrespondenceFK(transactionCorrespondenceVO[0].getTransactionCorrespondencePK());
                editTrxCorrespondenceVO.setAddressTypeCT(corrAddressTypeCT);
                EDITDate trxEffectiveDate = new EDITDate(newEditTrxVO.getEffectiveDate());

                newEditTrxVO.addEDITTrxCorrespondenceVO(editTrxCorrespondenceVO);
            }
        }

        EDITTrxHistoryVO editTrxHistoryVO = new EDITTrxHistoryVO();
        editTrxHistoryVO.setEDITTrxHistoryPK(0);
        editTrxHistoryVO.setEDITTrxFK(0);

        EDITDate currentDate = new EDITDate();
        editTrxHistoryVO.setProcessDateTime(new EDITDateTime().getFormattedDateTime());
        editTrxHistoryVO.setCycleDate(currentDate.getFormattedDate());
        editTrxHistoryVO.setAccountingPendingStatus("N");
        editTrxHistoryVO.setRealTimeInd("Y");
        editTrxHistoryVO.setAddressTypeCT(disbAddressTypeCT);
        newEditTrxVO.addEDITTrxHistoryVO(editTrxHistoryVO);

        SaveGroup saveGroup = new SaveGroup(isGroupSetupVO, newEditTrxVO, "Commit", "");

        saveGroup.build();
        return saveGroup.save();
    }

    public ClientTrx[] createCalendarYearEndTrxGroupSetup(Segment segment, String operator) throws Exception
    {
        GroupSetupVO cyGroupSetupVO = buildDefaultGroupSetup(new Segment[] { segment });

        String effectiveYear = null;
        if (currentYear != null)
        {
            effectiveYear = currentYear;
        }
        else
        {
            effectiveYear = segment.getEffectiveDate().getFormattedYear();
        }

        EDITDate effectiveDate = new EDITDate(effectiveYear, EDITDate.DEFAULT_MAX_MONTH, EDITDate.DEFAULT_MAX_DAY);

        int taxYear = effectiveDate.getYear();

        EDITDate stopDate = segment.getTerminationDate();

        String lifeContingent = null;

        String optionCode = segment.getOptionCodeCT();

        if (optionCode.equalsIgnoreCase("LOA") || optionCode.equalsIgnoreCase("LCR") || optionCode.equalsIgnoreCase("JPC") || optionCode.equalsIgnoreCase("JSA") || optionCode.equalsIgnoreCase("TML") || optionCode.equalsIgnoreCase("DFA"))
        {
            lifeContingent = "AN";
        }

        else if (optionCode.equalsIgnoreCase("PCA") || optionCode.equalsIgnoreCase("LPC") || optionCode.equalsIgnoreCase("AMC"))
        {
            lifeContingent = "NotLifeContingent";
        }

        ScheduledEventVO schedEventVO = new ScheduledEventVO();

        schedEventVO.setScheduledEventPK(0);
        schedEventVO.setStartDate(effectiveDate.getFormattedDate());
        schedEventVO.setStopDate(stopDate.getFormattedDate());
        schedEventVO.setLastDayOfMonthInd("N");
        schedEventVO.setFrequencyCT("Annual");
        schedEventVO.setLifeContingentCT(lifeContingent);
        schedEventVO.setCostOfLivingInd("N");

        cyGroupSetupVO.addScheduledEventVO(schedEventVO);

        String transactionTypeCT = "CY";

        EDITTrxVO newEditTrxVO = buildDefaultEDITTrxVO(transactionTypeCT, effectiveDate, taxYear, operator);
        newEditTrxVO.setDueDate(effectiveDate.getFormattedDate());

        SaveGroup saveGroup = new SaveGroup(cyGroupSetupVO, newEditTrxVO, "Commit", "");

        saveGroup.build();
        return saveGroup.save();
    }

    public ClientTrx[] createPolicyYearEndTrxGroupSetup(Segment segment, String operator) throws Exception
    {
        GroupSetupVO peGroupSetupVO = buildDefaultGroupSetup(new Segment[] { segment });

        EDITDate effDate = segment.getEffectiveDate();

        if (currentYear != null)
        {
//            String[] dateArray = Util.parseStringDate(effDate);
//            dateArray[2] = currentYear;
            effDate = new EDITDate(currentYear, effDate.getFormattedMonth(), effDate.getFormattedDay());
        }

        EDITDate effDateED = effDate.addYears(1);

        int taxYear = effDateED.getYear();

        ScheduledEventVO schedEventVO = new ScheduledEventVO();
        schedEventVO.setScheduledEventPK(0);
        schedEventVO.setGroupSetupFK(0);
        schedEventVO.setStartDate(effDateED.getFormattedDate());
        schedEventVO.setStopDate(segment.getTerminationDate().getFormattedDate());
        schedEventVO.setLastDayOfMonthInd("N");
        schedEventVO.setFrequencyCT("Annual");

        //        schedEventVO.setLifeContingentCT(lifeContingent);
        schedEventVO.setCostOfLivingInd("N");

        peGroupSetupVO.addScheduledEventVO(schedEventVO);

        String transactionTypeCT = "PE";

        EDITTrxVO newEditTrxVO = buildDefaultEDITTrxVO(transactionTypeCT, effDateED, taxYear, operator);
        newEditTrxVO.setDueDate(effDateED.getFormattedDate());

        SaveGroup saveGroup = new SaveGroup(peGroupSetupVO, newEditTrxVO, "Commit", "");

        saveGroup.build();
        return saveGroup.save();
    }

    /**
     * Builds and executes (if realtime) a PolicyQuarterTrx schuduled to run every 3 months. This follows the same
     * pattern as the PolicyYearEndTrx.
     * @param segmentVO
     * @throws Exception
     */
    public ClientTrx[] createPolicyQuarterTrxGroupSetup(Segment segment, String operator) throws Exception
    {
        List clientTrx = new ArrayList();

        GroupSetupVO peGroupSetupVO = buildDefaultGroupSetup(new Segment[] { segment });

        EDITDate effDateED = segment.getEffectiveDate().addMonths(3);

        int taxYear = effDateED.getYear();

        ScheduledEventVO schedEventVO = new ScheduledEventVO();

        schedEventVO.setScheduledEventPK(0);
        schedEventVO.setGroupSetupFK(0);
        schedEventVO.setStartDate(effDateED.getFormattedDate());
        schedEventVO.setStopDate(segment.getTerminationDate().getFormattedDate());
        schedEventVO.setLastDayOfMonthInd("N");
        schedEventVO.setFrequencyCT("Annual");

        schedEventVO.setCostOfLivingInd("N");

        peGroupSetupVO.addScheduledEventVO(schedEventVO);

        String transactionTypeCT = "PQ";

        EDITTrxVO newEditTrxVO = buildDefaultEDITTrxVO(transactionTypeCT, effDateED, taxYear, operator);

        SaveGroup saveGroup = new SaveGroup(peGroupSetupVO, newEditTrxVO, "Commit", "");

        saveGroup.build();
        clientTrx.addAll(Arrays.asList(saveGroup.save()));

        return (ClientTrx[]) clientTrx.toArray(new ClientTrx[clientTrx.size()]);
    }

    /**
     * Creates default Statement transaction.
     * @param segment
     * @param operator
     * @throws Exception
     */
    public ClientTrx[] createStatementTrxGroupSetup(Segment segment, String operator) throws Exception
    {
        GroupSetupVO groupSetupVO = buildDefaultGroupSetup(new Segment[] { segment });

        EDITDate effectiveDate = segment.getEffectiveDate();

        if (currentYear != null)
        {
            effectiveDate = new EDITDate(currentYear, effectiveDate.getFormattedMonth(), effectiveDate.getFormattedDay());
        }

        effectiveDate = effectiveDate.addYears(1);

        ScheduledEventVO schedEventVO = new ScheduledEventVO();
        schedEventVO.setScheduledEventPK(0);
        schedEventVO.setGroupSetupFK(0);
        schedEventVO.setStartDate(effectiveDate.getFormattedDate());
        schedEventVO.setStopDate(segment.getTerminationDate().getFormattedDate());
        schedEventVO.setLastDayOfMonthInd("N");
        schedEventVO.setFrequencyCT(ScheduledEvent.FREQUENCYCT_ANNUAL);
        schedEventVO.setCostOfLivingInd("N");

        groupSetupVO.addScheduledEventVO(schedEventVO);

        String transactionTypeCT = EDITTrx.TRANSACTIONTYPECT_STATEMENT;

        EDITTrxVO editTrxVO = buildDefaultEDITTrxVO(transactionTypeCT, effectiveDate, effectiveDate.getYear(), operator);
        editTrxVO.setDueDate(effectiveDate.getFormattedDate());

        SaveGroup saveGroup = new SaveGroup(groupSetupVO, editTrxVO, SaveGroup.PROCESSNAME_COMMIT, "");

        saveGroup.build();
        return saveGroup.save();
    }

    private void checkRequirements(Segment segment) throws Exception
    {
        SegmentVO segmentVO = (SegmentVO) segment.getVO();
        ContractRequirementVO[] contractRequirementVO = segmentVO.getContractRequirementVO();
        ClientAddress clientAddress = null;

        Long batchContractSetupFK = new Long(segmentVO.getBatchContractSetupFK());

        ContractGroupRequirement[] batchRequirements = ContractGroupRequirement.findBy_BatcContractSetupFK(batchContractSetupFK);

        boolean pendingStatusFound = false;
        boolean IssuedPendingReqStatusFound = false;

        if ((contractRequirementVO == null) || (contractRequirementVO.length == 0) &&
            batchRequirements == null)
        {
            segment.setPostIssueStatusCT(Segment.SEGMENTSTATUSCT_ISSUEPENDINGPREMIUM);  
            //ECK - set originalState here
            ContractClient ownerClient = ContractClient.findBy_SegmentPK_RoleTypeCT_MaxEffectiveDate(segment, ClientRole.ROLETYPECT_OWNER); //ECK - here for ownerClient code
            clientAddress = ClientAddress.findByClientDetail_And_AddressTypeCT(ownerClient.getClientDetail(), ClientAddress.CLIENT_PRIMARY_ADDRESS);
            segment.setOriginalStateCT(clientAddress.getStateCT());
        }

        else
        {
            for (int i = 0; i < contractRequirementVO.length; i++)
            {
                if (contractRequirementVO[i].getRequirementStatusCT().equalsIgnoreCase("Outstanding"))
                {
                    RequirementVO[] requirementsVO = contract.dm.dao.DAOFactory.getRequirementDAO().findByFilteredRequirementPK(contractRequirementVO[i].getFilteredRequirementFK(), false, new ArrayList());

                    //if null returned here an error should occur
                    if (requirementsVO[0].getAllowableStatusCT().equalsIgnoreCase(Segment.SEGMENTSTATUSCT_PENDING) ||
                        requirementsVO[0].getAllowableStatusCT().equalsIgnoreCase(Segment.SEGMENTSTATUSCT_INITIAL_UW) ||
                        requirementsVO[0].getAllowableStatusCT().equalsIgnoreCase(Segment.SEGMENTSTATUSCT_APPROVED))
                    {
                        pendingStatusFound = true;
                    }
                    else if (requirementsVO[0].getAllowableStatusCT().equalsIgnoreCase(Segment.SEGMENTSTATUSCT_ISSUEPENDINGREQ))
                    {
                        IssuedPendingReqStatusFound = true;
                    }
                }
            }

            if (batchRequirements != null)
            {
                for (int i = 0; i < batchRequirements.length; i++)
                {
                    if (batchRequirements[i].getRequirementStatusCT().equalsIgnoreCase("Outstanding"))
                    {
                        RequirementVO[] requirementsVO = contract.dm.dao.DAOFactory.getRequirementDAO().findByFilteredRequirementPK(batchRequirements[i].getFilteredRequirementFK(), false, new ArrayList());

                        //if null returned here an error should occur
                        if (requirementsVO[0].getAllowableStatusCT().equalsIgnoreCase(Segment.SEGMENTSTATUSCT_PENDING) ||
                            requirementsVO[0].getAllowableStatusCT().equalsIgnoreCase(Segment.SEGMENTSTATUSCT_INITIAL_UW) ||
                            requirementsVO[0].getAllowableStatusCT().equalsIgnoreCase(Segment.SEGMENTSTATUSCT_APPROVED))
                        {
                            pendingStatusFound = true;
                        }
                        else if (requirementsVO[0].getAllowableStatusCT().equalsIgnoreCase(Segment.SEGMENTSTATUSCT_ISSUEPENDINGREQ))
                        {
                            IssuedPendingReqStatusFound = true;
                        }
                    }
                }
            }

            //Only IssuedPendingReq allowable status found
            if (!pendingStatusFound && IssuedPendingReqStatusFound)
            {
                segment.setPostIssueStatusCT(Segment.SEGMENTSTATUSCT_ISSUEPENDINGREQ);
            }
            else if (!pendingStatusFound && !IssuedPendingReqStatusFound) // no outstanding requirements
            {
                segment.setPostIssueStatusCT(Segment.SEGMENTSTATUSCT_ISSUEPENDINGPREMIUM);
            }
        }

        SegmentVO[] riderSegments = segmentVO.getSegmentVO();

        if (riderSegments != null)
        {
            for (int i = 0; i < riderSegments.length; i++)
            {
                riderSegments[i].setPostIssueStatusCT(segmentVO.getPostIssueStatusCT());
                if (clientAddress != null) {
                    riderSegments[i].setOriginalStateCT(clientAddress.getStateCT());
                }
            }
        }
    }

    /**
     * @param segmentVO
     * @param createFreeLookFundOverride
     * @param operator
     * @return
     * @throws Exception
     */
    public ClientTrx[] createPremiumTrxGroupSetup(Segment segment, String operator) throws Exception, EDITEventException
    {
        SegmentVO segmentVO = (SegmentVO) segment.getVO();

        boolean premiumTrxCreated = false;

        DepositsVO[] depositsVOs = segmentVO.getDepositsVO();

        Suspense[] suspense = Suspense.findByUserDefNumberForIssue(segmentVO.getContractNumber());

        Map suspenseHT = accumSuspenseAmount(depositsVOs, suspense, segment);

        Set deposits = segment.getDeposits();

        List clientTrx = new ArrayList();

        if (suspenseHT.size() > 0)
        {
            premiumTrxCreated = true;

            Iterator keys = suspenseHT.keySet().iterator();

            String transactionTypeCT = "PY";
            EDITDate effectiveDate = null;

            if (segment.getSegmentNameCT().equalsIgnoreCase("Life"))
            {
                DateCalcsForPremium dateCalcsForPremium = new DateCalcsForPremium();
                dateCalcsForPremium.calcPremiumDateForIssue(segmentVO, suspense);
                effectiveDate = new EDITDate(dateCalcsForPremium.getDateCalculated());
            }
            else
            {
                effectiveDate = segment.getEffectiveDate();
            }

            while (keys.hasNext())
            {
                String taxYear = (String) keys.next();

                List suspenseVector = (List) suspenseHT.get(taxYear);

                OutSuspenseVO[] outSuspenseVOs = createOutSuspense(suspenseVector, taxYear);

                EDITBigDecimal suspenseTotal = new EDITBigDecimal();

                for (int i = 0; i < outSuspenseVOs.length; i++)
                {
                    //total of deposits amount received for a tax year
                    suspenseTotal = suspenseTotal.addEditBigDecimal(outSuspenseVOs[i].getAmount());
                }

                EDITBigDecimal costBasis = new EDITBigDecimal();
                String qualNoQualCT = segment.getQualNonQualCT();

                if (qualNoQualCT != null)
                {
                    if (qualNoQualCT.equalsIgnoreCase("NonQualified"))
                    {
                        costBasis = getCostBasis(deposits, taxYear);
                    }
                }

                EDITTrxVO newEditTrxVO = buildDefaultEDITTrxVO(transactionTypeCT, effectiveDate, Integer.parseInt(taxYear), operator);

                GroupSetupVO pyGroupSetupVO = buildGroupSetupForPremium(segment, outSuspenseVOs);

                pyGroupSetupVO.getContractSetupVO(0).setPolicyAmount(suspenseTotal.getBigDecimal());

                pyGroupSetupVO.getContractSetupVO(0).setCostBasis(costBasis.getBigDecimal());

                checkForFreeLook(segment, effectiveDate, pyGroupSetupVO);

                CodeTableWrapper codeTableWrapper = CodeTableWrapper.getSingleton();

                String transactionTypeCode = codeTableWrapper.getCodeDescByCodeTableNameAndCode("TRXTYPE", newEditTrxVO.getTransactionTypeCT());

                try
                {
                    clientTrx.addAll(Arrays.asList(new GroupTrx().saveGroupSetupForIssue(pyGroupSetupVO, newEditTrxVO, transactionTypeCode, segment.getOptionCodeCT(), segment.getProductStructureFK().longValue(), suspenseVector, deposits)));
                }
                catch (EDITEventException e)
                {
                    throw e;
                }
                catch (Exception e)
                {
                    throw e;
                }
            }
        }

        return (ClientTrx[]) clientTrx.toArray(new ClientTrx[clientTrx.size()]);
    }

    private void checkForFreeLook(Segment segment, EDITDate effectiveDate, GroupSetupVO pyGroupSetupVO) throws Exception
    {
        engine.business.Lookup engineLookup = new engine.component.LookupComponent();

        String grouping = "FREELOOKPROCESS";
        String field = "FREELOOKINDICATOR";
        String qualifierCT = "*";

        AreaValueVO areaValueVO = engineLookup.getAreaValue(segment.getProductStructureFK().longValue(), segment.getIssueStateCT(), grouping, effectiveDate, field, qualifierCT);

        if (areaValueVO != null)
        {
            //FreeLookIndicator is the first step in determining if there is free look allowed
            if (areaValueVO.getAreaValue().equals("Y"))
            {
                //check waiver field and end date -- Life processing
                if (segment.getWaiveFreeLookIndicator().equalsIgnoreCase("N"))
                {
                    if (segment.getFreeLookEndDate() == null)
                    {
                        createFreeLookFundOverride(pyGroupSetupVO, segment, effectiveDate);
                    }
                }
            }
        }
    }

    /**
     * This method creates the maturity trx and save to the EDITtrx table
     * @param segment
     * @param operator
     * @return
     * @throws Exception
     */
    public ClientTrx[] createMaturityTrxGroupSetup(Segment segment, String operator) throws Exception
    {
        GroupSetupVO groupSetupVO = buildDefaultGroupSetup(new Segment[] { segment });

        String transactionTypeCode = "MA";

        EDITDate effectiveDate = segment.getTerminationDate();
        int taxYear = segment.getEffectiveDate().getYear();

        EDITTrxVO newEditTrxVO = buildDefaultEDITTrxVO(transactionTypeCode, effectiveDate, taxYear, operator);

        return new GroupTrx().saveOnlyGroupSetup(groupSetupVO, newEditTrxVO, transactionTypeCode, segment.getOptionCodeCT(), segment.getProductStructureFK().longValue());
    }

    /**
     * This method is used to calculate the termination date
     * @param segment
     * @return
     * @throws Exception
     */
    public EDITDate getCalculatedTerminationDate(Segment segment, String incomeMaturityAgeString) throws Exception
    {
        EDITDate segmentEffectiveDate = segment.getEffectiveDate();
        EDITDate insuredBirthDate = getInsuredBirthDate(segment);
        EDITDate edTermDate = null;

        if (insuredBirthDate == null)
        {
            throw (new Exception("No Birthdate/ No insured role client"));
        }
        else if (segment.getSegmentNameCT().equalsIgnoreCase("Life"))
        {
            int segEffDtMonth = 0;
            int insrdBrthDtMonth = 0;
            int insrdBrthDtYear = insuredBirthDate.getYear();
            int termYearAsInt = MA_TRX_TERMINATION_YRS;

            if ((segmentEffectiveDate != null) && (insuredBirthDate != null))
            {
                segEffDtMonth = segmentEffectiveDate.getMonth();
                insrdBrthDtMonth = insuredBirthDate.getMonth();

                if (segEffDtMonth < insrdBrthDtMonth)
                {
                    termYearAsInt = termYearAsInt + 1;
                }
            }

            edTermDate = new EDITDate(insrdBrthDtYear, segmentEffectiveDate.getMonth(), segmentEffectiveDate.getDay());
            edTermDate = edTermDate.addYears(termYearAsInt);
        }
        else if (segment.getOptionCodeCT().equalsIgnoreCase(Segment.OPTIONCODECT_TRADITIONAL_LIFE))
        {
            EDITDate terminationDate = insuredBirthDate;

            if (incomeMaturityAgeString != null)
            {
                int index = incomeMaturityAgeString.indexOf(";");     // if age is 70 and 6 months, this string contains 70;6 from ui
                int years = 0;
                int months = 0;
                if (index > 0)
                {
                    years = Integer.parseInt(incomeMaturityAgeString.substring(0, index));
                    months = Integer.parseInt(incomeMaturityAgeString.substring(index + 1));
                }
                else
                {
                    years = Integer.parseInt(incomeMaturityAgeString);
                }

                terminationDate = terminationDate.addYears(years);
                terminationDate = terminationDate.addMonths(months);

                int termYear = terminationDate.getYear();
                int effYear = segmentEffectiveDate.getYear();

                int yearDiff = termYear - effYear;

                edTermDate = segmentEffectiveDate.addYears(yearDiff);
                if (edTermDate.before(terminationDate))
                {
                    edTermDate = edTermDate.addYears(1);
                }
            }
        }

        return edTermDate;
    }

    /**
     * This method traverses the contract clients associated to a segment and
     * gets the birthdate of the Insured.
     * @param segment
     * @return
     * @throws Exception
     */
    public EDITDate getInsuredBirthDate(Segment segment) throws Exception
    {
        SegmentVO segmentVO = (SegmentVO) segment.getVO();

        ContractClientVO[] contractClientVOs = segmentVO.getContractClientVO();

        EDITDate dateOfBirth = null;

        List voInclusionList = new ArrayList();
        voInclusionList.add(ClientDetailVO.class);

        if (contractClientVOs != null)
        {
            for (int c = 0; c < contractClientVOs.length; c++)
            {
                ClientRoleVO clientRoleVO = new ClientRoleComposer(voInclusionList).compose(contractClientVOs[c].getClientRoleFK());
                ClientDetailVO clientDetailVO = (ClientDetailVO) clientRoleVO.getParentVO(ClientDetailVO.class);
                String roleType = clientRoleVO.getRoleTypeCT();

                if (roleType.equalsIgnoreCase("Insured"))
                {
                    dateOfBirth = new EDITDate(clientDetailVO.getBirthDate());
                }
            }
        }

        return dateOfBirth;
    }

    private GroupSetupVO buildGroupSetupForPremium(Segment segment, OutSuspenseVO[] outSuspenseVOs)
    {
        CodeTableWrapper codeTableWrapper = CodeTableWrapper.getSingleton();
        String distributionCode = codeTableWrapper.getCodeByCodeTableNameAndCodeDesc("DISTRIBUTIONCODE", "Normal Distribution");

        GroupSetupVO groupSetupVO = new GroupSetupVO();
        groupSetupVO.setGroupSetupPK(0);
        groupSetupVO.setMemoCode("");
        groupSetupVO.setGrossNetStatusCT("G");
        groupSetupVO.setDistributionCodeCT(distributionCode);
        groupSetupVO.setPremiumTypeCT("Issue");
        groupSetupVO.setGroupAmount(new EDITBigDecimal().getBigDecimal());
        groupSetupVO.setGroupPercent(new EDITBigDecimal().getBigDecimal());

        ContractSetupVO contractSetupVO = new ContractSetupVO();
        contractSetupVO.setContractSetupPK(0);
        contractSetupVO.setGroupSetupFK(0);
        contractSetupVO.setSegmentFK(SessionHelper.getPKValue(segment.getSegmentPK()));

        contractSetupVO.setPolicyAmount(new EDITBigDecimal().getBigDecimal());
        contractSetupVO.setCostBasis(new EDITBigDecimal().getBigDecimal());
        contractSetupVO.setAmountReceived(new EDITBigDecimal().getBigDecimal());

        if (outSuspenseVOs != null)
        {
            contractSetupVO.setOutSuspenseVO(outSuspenseVOs);
        }

        groupSetupVO.addContractSetupVO(contractSetupVO);

        return groupSetupVO;
    }

    /**
     * Creates an Investment based on a free-look fund that has been setup (if any).
     * @param groupSetupVO
     * @param segmentVO
     * @throws Exception
     */
    private void createFreeLookFundOverride(GroupSetupVO groupSetupVO, Segment segment, EDITDate effectiveDate) throws Exception
    {
        ContractSetupVO contractSetupVO = groupSetupVO.getContractSetupVO(0);
        Long productStructurePK = segment.getProductStructureFK();
        String qualifierCT = "*";
        String areaCT = segment.getIssueStateCT();
        String grouping = "FREELOOKPROCESS";
        String field = "FREELOOKFUND";

        Area area = new Area(productStructurePK.longValue(), areaCT, grouping, effectiveDate, qualifierCT);

        AreaValue areaValue = area.getAreaValue(field);

        AreaValueVO areaValueVO = (AreaValueVO) areaValue.getVO();

        if (areaValueVO != null)
        {
            String fundNumber = areaValueVO.getAreaValue();

            engine.business.Lookup engineLookup = new engine.component.LookupComponent();

            FilteredFundVO[] filteredFundVOs = engineLookup.getByFundNumber(fundNumber);

            if (filteredFundVOs != null)
            {
                long filteredFundPK = filteredFundVOs[0].getFilteredFundPK();

                if (filteredFundPK != 0)
                {
                    matchToInvestments(contractSetupVO, segment, filteredFundPK);
                }
            }
        }
    }

    public ClientTrx[] createModalDeductionTrxGroupSetup(Segment segment, String operator) throws Exception
    {
        String transactionTypeCT = "MD";
        EDITDate mdEffDate = segment.getEffectiveDate();
        int taxYear = mdEffDate.getYear();

        EDITTrxVO newEditTrxVO = buildDefaultEDITTrxVO(transactionTypeCT, mdEffDate, taxYear, operator);
        newEditTrxVO.setDueDate(mdEffDate.getFormattedDate());

        if (segment.getPostIssueStatusCT().equalsIgnoreCase(Segment.SEGMENTSTATUSCT_ISSUEPENDINGPREMIUM))
        {
            newEditTrxVO.setPendingStatus("X");
        }

        GroupSetupVO mdGroupSetupVO = buildDefaultGroupSetup(new Segment[] { segment });

        ScheduledEventVO schedEventVO = new ScheduledEventVO();

        schedEventVO.setScheduledEventPK(0);
        schedEventVO.setStartDate(mdEffDate.getFormattedDate());
        schedEventVO.setStopDate(EDITDate.DEFAULT_MAX_DATE);
        schedEventVO.setLastDayOfMonthInd("N");
        schedEventVO.setFrequencyCT("Monthly");
        schedEventVO.setLifeContingentCT("NotLifeContingent");
        schedEventVO.setCostOfLivingInd("N");

        mdGroupSetupVO.addScheduledEventVO(schedEventVO);

        CodeTableWrapper codeTableWrapper = CodeTableWrapper.getSingleton();
        String transactionTypeCode = codeTableWrapper.getCodeDescByCodeTableNameAndCode("TRXTYPE", newEditTrxVO.getTransactionTypeCT());

        return new GroupTrx().saveOnlyGroupSetup(mdGroupSetupVO, newEditTrxVO, transactionTypeCode, segment.getOptionCodeCT(), segment.getProductStructureFK().longValue());
    }

    /**
     * Creates the MF transaction for the given Segment
     * @param segmentVO
     * @param operator
     * @throws Exception
     */
    public ClientTrx[] createMonthlyFeeTrxGroupSetup(Segment segment, String operator) throws Exception
    {
        String transactionTypeCT = "MF";
        EDITDate effectiveDate = segment.getEffectiveDate();
        EDITDate edMFEffDate = effectiveDate.getEndOfMonthDate();

        BusinessCalendar businessCalendar = new BusinessCalendar();

        BusinessDay businessDay = businessCalendar.getBestBusinessDay(edMFEffDate);

        edMFEffDate = businessDay.getBusinessDate();

        int taxYear = edMFEffDate.getYear();

        EDITTrxVO newEditTrxVO = buildDefaultEDITTrxVO(transactionTypeCT, edMFEffDate, taxYear, operator);
        newEditTrxVO.setDueDate(edMFEffDate.getFormattedDate());

        if (segment.getPostIssueStatusCT().equalsIgnoreCase(Segment.SEGMENTSTATUSCT_ISSUEPENDINGPREMIUM))
        {
            newEditTrxVO.setPendingStatus("X");
        }

        //Check for all forward prices for contract investments to determine how the pending status should be set
        contract.business.Lookup contractLookup = new contract.component.LookupComponent();

        InvestmentVO[] investmentVOs = contractLookup.composeInvestmentVOBySegmentPK(SessionHelper.getPKValue(segment.getSegmentPK()), new ArrayList());

        event.business.Event eventComponent = new event.component.EventComponent();

        boolean allForwardPricesFound = eventComponent.checkForForwardPrices(investmentVOs, edMFEffDate.getFormattedDate());

        if (!allForwardPricesFound)
        {
            newEditTrxVO.setPendingStatus("M");
        }

        GroupSetupVO mdGroupSetupVO = buildDefaultGroupSetup(new Segment[] { segment });

        ScheduledEventVO schedEventVO = new ScheduledEventVO();

        schedEventVO.setScheduledEventPK(0);
        schedEventVO.setStartDate(edMFEffDate.getFormattedDate());
        schedEventVO.setStopDate(EDITDate.DEFAULT_MAX_DATE);
        schedEventVO.setLastDayOfMonthInd("Y");
        schedEventVO.setFrequencyCT("Monthly");
        schedEventVO.setLifeContingentCT("NotLifeContingent");
        schedEventVO.setCostOfLivingInd("N");

        mdGroupSetupVO.addScheduledEventVO(schedEventVO);

        CodeTableWrapper codeTableWrapper = CodeTableWrapper.getSingleton();
        String transactionTypeCode = codeTableWrapper.getCodeDescByCodeTableNameAndCode("TRXTYPE", newEditTrxVO.getTransactionTypeCT());

        return new GroupTrx().saveOnlyGroupSetup(mdGroupSetupVO, newEditTrxVO, transactionTypeCode, segment.getOptionCodeCT(), segment.getProductStructureFK().longValue());
    }

    private void createTransferTrxGroupSetup(Segment segment, String operator) throws Exception
    {
        GroupSetupVO tfGroupSetupVO = buildDefaultGroupSetup(new Segment[] { segment });

        EDITDate segEffDate = segment.getEffectiveDate();

        EDITDate tfEffDate = segEffDate.addDays(5);

        EDITTrxVO newEditTrxVO = null;
        newEditTrxVO = new EDITTrxVO();
        newEditTrxVO.setPendingStatus("P");
        newEditTrxVO.setTransactionTypeCT("TF");
        newEditTrxVO.setEffectiveDate(tfEffDate.getFormattedDate());
        newEditTrxVO.setTaxYear(tfEffDate.getYear());
        newEditTrxVO.setNoAccountingInd("N");
        newEditTrxVO.setNoCommissionInd("N");
        newEditTrxVO.setZeroLoadInd("N");
        newEditTrxVO.setNoCorrespondenceInd("N");
        newEditTrxVO.setOperator(operator);
        newEditTrxVO.setPremiumDueCreatedIndicator("N");

        List voExclusionList = new ArrayList();
        voExclusionList.add(BucketVO.class);

        InvestmentVO[] investmentVOs = contract.dm.dao.DAOFactory.getInvestmentDAO().findBySegmentPK(SessionHelper.getPKValue(segment.getSegmentPK()), true, voExclusionList);

        ContractSetupVO[] contractSetupVO = tfGroupSetupVO.getContractSetupVO();

        for (int i = 0; i < contractSetupVO.length; i++)
        {
            for (int j = 0; j < investmentVOs.length; j++)
            {
                InvestmentAllocationOverrideVO newInvAllocOverrideVO = new InvestmentAllocationOverrideVO();
                newInvAllocOverrideVO.setInvestmentAllocationOverridePK(0);
                newInvAllocOverrideVO.setContractSetupFK(contractSetupVO[i].getContractSetupPK());
                newInvAllocOverrideVO.setInvestmentFK(investmentVOs[j].getInvestmentPK());

                InvestmentAllocationVO[] investmentAllocationVOs = investmentVOs[j].getInvestmentAllocationVO();

                for (int k = 0; k < investmentAllocationVOs.length; k++)
                {
                    newInvAllocOverrideVO.setInvestmentAllocationFK(investmentAllocationVOs[k].getInvestmentAllocationPK());

                    if (investmentAllocationVOs[k].getOverrideStatus().equalsIgnoreCase("O"))
                    {
                        newInvAllocOverrideVO.setToFromStatus("F");
                    }
                    else
                    {
                        newInvAllocOverrideVO.setToFromStatus("T");
                    }
                }

                contractSetupVO[i].addInvestmentAllocationOverrideVO(newInvAllocOverrideVO);
            }
        }

        CodeTableWrapper codeTableWrapper = CodeTableWrapper.getSingleton();
        String transactionTypeCode = codeTableWrapper.getCodeDescByCodeTableNameAndCode("TRXTYPE", newEditTrxVO.getTransactionTypeCT());

        new GroupTrx().saveGroupSetup(tfGroupSetupVO, newEditTrxVO, transactionTypeCode, segment.getOptionCodeCT(), segment.getProductStructureFK().longValue());
    }

    private ClientTrx[] createAdvanceCommissionTrxGroupSetup(Segment segment, AgentHierarchyVO agentHierarchyVO, String operator) throws Exception
    {
        EDITBigDecimal advancePremium = new EDITBigDecimal(agentHierarchyVO.getAdvancePremium());

        String transactionTypeCT = "AC";
        EDITDate effectiveDate = segment.getEffectiveDate();
        int taxYear = effectiveDate.getYear();

        EDITTrxVO newEditTrxVO = buildDefaultEDITTrxVO(transactionTypeCT, effectiveDate, taxYear, operator);

        GroupSetupVO acGroupSetupVO = buildDefaultGroupSetup(new Segment[] { segment });

        acGroupSetupVO.getContractSetupVO()[0].setPolicyAmount(advancePremium.getBigDecimal());

        CodeTableWrapper codeTableWrapper = CodeTableWrapper.getSingleton();

        String transactionTypeCode = codeTableWrapper.getCodeDescByCodeTableNameAndCode("TRXTYPE", newEditTrxVO.getTransactionTypeCT());

        return new GroupTrx().saveOnlyGroupSetup(acGroupSetupVO, newEditTrxVO, transactionTypeCode, segment.getOptionCodeCT(), segment.getProductStructureFK().longValue());
    }

    /**
     * Builds the set of BillingTrxs for the supplied Segments.
     * @param segments
     * @param transactionTypeCT         The type of GroupSetup that will be built.
     */
    public void createBillingTrxGroupSetup(Segment[] segments, String transactionTypeCT) throws EDITEventException
    {
        GroupSetupVO groupSetupVO = buildDefaultGroupSetup(segments);

        EDITDate effectiveDate = null;
        int taxYear = 0;
        String operator = null;

        // Normally this would be the only trx used in the GroupSave. However, this EDITTrxVO will simply
        // serve as a template for each EDITTrxVO that needs to be built in the SaveGroup.
        EDITTrxVO editTrxVO = buildDefaultEDITTrxVO(transactionTypeCT, effectiveDate, taxYear, operator);

        SaveGroup saveGroup = new SaveGroup(groupSetupVO, editTrxVO, "Commit", "");

        saveGroup.build();

        saveGroup.save();
    }

    private OutSuspenseVO[] createOutSuspense(List suspenseVector, String taxYear)
    {
        List outSuspenseList = new ArrayList();

        for (int j = 0; j < suspenseVector.size(); j++)
        {
            Suspense suspense = (Suspense) suspenseVector.get(j);

            if (suspense.getTaxYear() == (Integer.parseInt(taxYear)))
            {
                OutSuspenseVO outSuspenseVO = new OutSuspenseVO();
                outSuspenseVO.setSuspenseFK(suspense.getSuspensePK().longValue());
                outSuspenseVO.setAmount(suspense.getSuspenseAmount().getBigDecimal());
                outSuspenseList.add(outSuspenseVO);
            }
        }

        return (OutSuspenseVO[]) outSuspenseList.toArray(new OutSuspenseVO[outSuspenseList.size()]);
    }

    private Map accumSuspenseAmount(DepositsVO[] depositsVOs, Suspense[] suspenses, Segment segment) throws Exception
    {
        Deposits[] deposits = checkForDepositCreation(depositsVOs, suspenses, segment);

        Map suspenseHT = new HashMap();

        if (deposits != null)
        {
            for (int i = 0; i < deposits.length; i++)
            {
                if (deposits[i].getAmountReceived().isGT(new EDITBigDecimal()))
                {
                    Long suspenseFK = deposits[i].getSuspenseFK();

                    if (suspenseFK != null)
                    {
                        Suspense suspense = Suspense.findByPK(suspenseFK);

                        if (suspense != null)
                        {
                            CashBatchContract cashBatchContract = suspense.getCashBatchContract();

                            if (cashBatchContract == null || cashBatchContract.getReleaseIndicator().equalsIgnoreCase("R"))
                            {
                                String taxYear = deposits[i].getTaxYear() + "";

                                if (suspenseHT.containsKey(taxYear))
                                {
                                    List suspenseVector = (List) suspenseHT.get(taxYear);
                                    if (!suspenseVector.contains(suspense))
                                    {
                                        suspenseVector.add(suspense);
                                    }
                                }
                                else
                                {
                                    List suspenseVector = new ArrayList();
                                    suspenseVector.add(suspense);
                                    suspenseHT.put(taxYear, suspenseVector);
                                }
                            }
                        }
                    }
                }
            }
        }

        return suspenseHT;
    }

    private Deposits[] checkForDepositCreation(DepositsVO[] depositsVOs, Suspense[] suspenses, Segment segment)
    {
        Deposits[] updatedDeposits = null;

        List depositsAdded = new ArrayList();

        if (suspenses != null)
        {
            boolean depositFound;

            for (int i = 0; i < suspenses.length; i++)
            {
                depositFound = false;
                for (int j = 0; j < depositsVOs.length; j++)
                {
                    if (depositsVOs[j].getSuspenseFK() == suspenses[i].getSuspensePK().longValue())
                    {
                        depositFound = true;
                        break;
                    }
                }

                if (!depositFound)
                {
                    CashBatchContract cashBatchContract = suspenses[i].getCashBatchContract();
                    if (cashBatchContract == null || cashBatchContract.getReleaseIndicator().equalsIgnoreCase("R"))
                    {
                        Deposits newDeposit = new Deposits();
                        newDeposit.setAmountReceived(suspenses[i].getSuspenseAmount());
                        newDeposit.setCostBasis(suspenses[i].getCostBasis());
                        newDeposit.setDateReceived(suspenses[i].getEffectiveDate());
                        newDeposit.setDepositTypeCT(suspenses[i].getPremiumTypeCT());
                        newDeposit.setOldCompany(suspenses[i].getExchangeCompany());
                        newDeposit.setOldPolicyNumber(suspenses[i].getExchangePolicy());
                        newDeposit.setTaxYear(suspenses[i].getTaxYear());
                        newDeposit.setSuspenseFK(suspenses[i].getSuspensePK());
                        newDeposit.setSegmentFK(segment.getSegmentPK());
                        if (suspenses[i].getDepositTypeCT() != null)
                        {
                            newDeposit.setDepositTypeCT(suspenses[i].getDepositTypeCT());
                        }

                        newDeposit.setSuspense(suspenses[i]);
                        newDeposit.setSegment(segment);

                        NewBusinessUseCase newBusinessUseCase = new NewBusinessUseCaseComponent();

                        newBusinessUseCase.saveDeposit(newDeposit);

                        depositsAdded.add(newDeposit);
                    }
                }
            }

            for (int i = 0; i < depositsAdded.size(); i++)
            {
                segment.addDeposit((Deposits) depositsAdded.get(i));
            }
        }

        updatedDeposits = new Deposits[depositsVOs.length + depositsAdded.size()];

        int j = 0;

        for (int i = 0; i < depositsVOs.length; i++)
        {
            Deposits existingDeposit = new Deposits(depositsVOs[i]);
            updatedDeposits[j] = existingDeposit;
//            segment.addDeposit(existingDeposit);
            j += 1;
        }

        for (int i = 0; i < depositsAdded.size(); i++)
        {
            updatedDeposits[j] = ((Deposits) depositsAdded.get(i));
            j += 1;
        }

        return updatedDeposits;
    }

    private StringBuffer buildExceptionMessages(EDITEventException e, List clientTrx, long segmentPK) throws Exception
    {
        StringBuffer errorMessages = new StringBuffer();

        ClientTrx failedClientTrx = (ClientTrx) e.getFailedTrx();
        Exception originalException = e.getOriginalException();

        int index = clientTrx.indexOf(failedClientTrx);

        SegmentVO segmentVO = new VOComposer().composeSegmentVO(segmentPK, new ArrayList());
        String transactionType = failedClientTrx.getEDITTrxVO().getTransactionTypeCT();

        errorMessages.append("Error [Contract = " + segmentVO.getContractNumber() + "] [Transaction Type = " + transactionType + "]\n");

        for (int i = index + 1; i < clientTrx.size(); i++)
        {
            ClientTrx bypass = (ClientTrx) clientTrx.get(i);
            errorMessages.append("Bypassing next transaction [Contract = " + segmentVO.getContractNumber() + "] [Transaction Type = " + bypass.getEDITTrxVO().getTransactionTypeCT() + "]\n");
        }

        errorMessages.append(originalException);

        return errorMessages;
    }

    private void writeExceptions(StringBuffer errorMessages, String log)
    {
        //        Logger logger = Logging.getLogger(log);
        //
        //        LogEntryVO logEntryVO = new LogEntryVO();
        //
        //        String[] tokens = Util.fastTokenizer(errorMessages.toString(), "\n");
        //
        //        String message = tokens[0];
        //
        //        logEntryVO.setType("Error");
        //        logEntryVO.setMessage(message);
        //        logEntryVO.setMessageDetail(tokens);
        //
        //        logger.error(logEntryVO);
    }

    protected void setAmount(SegmentVO segmentVO) throws Exception
    {
        // double policyAmount = 0;
        // commented above line(s) for double to BigDecimal conversion
        // sprasad 9/30/2004
        EDITBigDecimal policyAmount = new EDITBigDecimal();
        DepositsVO[] depositsVO = segmentVO.getDepositsVO();

        /*for (int i = 0; i < depositsVO.length; i++)
        {
            double depositAmt = depositsVO[i].getAmountReceived();
            policyAmount += depositAmt;
        }*/

        // commented above line(s) for double to BigDecimal conversion
        // sprasad 9/30/2004
        for (int i = 0; i < depositsVO.length; i++)
        {
            policyAmount = policyAmount.addEditBigDecimal(depositsVO[i].getAmountReceived());
        }

        // segmentVO.setAmount(policyAmount);
        // commented above line(s) for double to BigDecimal conversion
        // sprasad 9/30/2004
        segmentVO.setAmount(policyAmount.getBigDecimal());
    }

    /**
     * Establishes default values for the GroupSetupVO and ContractSetupVO
     * @param segments
     * @return
     */
    public GroupSetupVO buildDefaultGroupSetup(Segment[] segments)
    {
        CodeTableWrapper codeTableWrapper = CodeTableWrapper.getSingleton();
        String distributionCode = codeTableWrapper.getCodeByCodeTableNameAndCodeDesc("DISTRIBUTIONCODE", "Normal Distribution");

        GroupSetupVO groupSetupVO = new GroupSetupVO();

        //  @todo Fix for Case processing
        //  TEMPORARY SOLUTION!!
        //  Master no longer exists and individual segments no longer have to belong to any grouping (Master, Case, etc.)
        //  We want groupSetup to have the groupType and caseNumber from Case (or null if individual).  But we also
        //  want to process all the segments for a given Case.  Right now this method takes an array of Segments
        //  but really only one is passed in.  Even if multiples are sent in (like for Cases), I am going to assume that
        //  all the segments are the same - they all belong to the same Case.  S. Dorman 1/18/06
        if (segments[0].belongsToACase())
        {
            ContractGroup caseContractGroup = ContractGroup.findBy_ContractGroupFK(segments[0].getContractGroupFK());

            groupSetupVO.setGroupTypeCT(caseContractGroup.getContractGroupTypeCT());
            groupSetupVO.setGroupKey(caseContractGroup.getContractGroupNumber());
        }
        else
        {
            groupSetupVO.setGroupTypeCT(null);
            groupSetupVO.setGroupKey(null);
        }

        groupSetupVO.setGrossNetStatusCT("Gross");
        groupSetupVO.setDistributionCodeCT(distributionCode);

        for (int i = 0; i < segments.length; i++)
        {
            ContractSetupVO contractSetupVO = new ContractSetupVO();

            contractSetupVO.setSegmentFK(SessionHelper.getPKValue(segments[i].getSegmentPK()));

            //            contractSetupVO.setPolicyAmount(new EDITBigDecimal("0").getBigDecimal());
            //            contractSetupVO.setCostBasis(new EDITBigDecimal("0").getBigDecimal());
            //            contractSetupVO.setAmountReceived(new EDITBigDecimal("0").getBigDecimal());
            groupSetupVO.addContractSetupVO(contractSetupVO);
        }

        return groupSetupVO;
    }

    /**
     * Builds a TransactionType-Neutral version of an EDITTrx(VO). Is is assumed the transaction-specific value will be
     * overlaid.
     * @param transactionTypeCT
     * @param effectiveDate
     * @return
     */
    public EDITTrxVO buildDefaultEDITTrxVO(String transactionTypeCT, EDITDate effectiveDate, int taxYear, String operator)
    {
        EDITTrxVO editTrxVO = new EDITTrxVO();

        if (effectiveDate == null)
        {
            editTrxVO.setEffectiveDate(null);
        }
        else
        {
            editTrxVO.setEffectiveDate(effectiveDate.getFormattedDate());
        }

        editTrxVO.setStatus("N");
        editTrxVO.setPendingStatus("P");
        editTrxVO.setSequenceNumber(1);
        editTrxVO.setTaxYear(taxYear);
        editTrxVO.setTransactionTypeCT(transactionTypeCT);
        editTrxVO.setTrxIsRescheduledInd("N");
        editTrxVO.setCommissionStatus("N");
        editTrxVO.setLookBackInd("N");
        editTrxVO.setNoCorrespondenceInd("N");
        editTrxVO.setNoAccountingInd("N");
        editTrxVO.setNoCorrespondenceInd("N");
        editTrxVO.setNoCommissionInd("N");
        editTrxVO.setZeroLoadInd("N");
        editTrxVO.setPremiumDueCreatedIndicator("N");
        editTrxVO.setMaintDateTime(new EDITDateTime().getFormattedDateTime());
        editTrxVO.setOperator(operator);
        editTrxVO.setPremiumDueCreatedIndicator("N");

        return editTrxVO;
    }

    /**
     * If a free-look fund has been found, then make sure that the investments are properly configured to respect this
     * free-look fund.
     * @param contractSetupVO
     * @param segmentVO
     * @param filteredFundFK
     * @throws Exception
     * @see #createFreeLookFundOverride(edit.common.vo.GroupSetupVO, edit.common.vo.SegmentVO)
     */
    private void matchToInvestments(ContractSetupVO contractSetupVO, Segment segment, long filteredFundFK) throws Exception
    {
        InvestmentVO[] investmentVOs = ((SegmentVO) segment.getVO()).getInvestmentVO();

        boolean investmentFound = false;

        InvestmentAllocationVO[] investmentAllocationVOs = null;

        boolean investmentAllocationFound = false;

        long investmentPK = 0;

        long investmentAllocationPK = 0;

        if (investmentVOs != null)
        {
            for (int i = 0; i < investmentVOs.length; i++)
            {
                if (filteredFundFK == investmentVOs[i].getFilteredFundFK())
                {
                    investmentFound = true;

                    investmentPK = investmentVOs[i].getInvestmentPK();

                    investmentAllocationVOs = investmentVOs[i].getInvestmentAllocationVO();

                    EDITBigDecimal allocPct = new EDITBigDecimal();

                    for (int j = 0; j < investmentAllocationVOs.length; j++)
                    {
                        String overrideStatus = investmentAllocationVOs[j].getOverrideStatus();

                        // double allocPct = investmentAllocationVOs[j].getAllocationPercent();
                        // if (overrideStatus.equalsIgnoreCase("O") && allocPct == 1.0)
                        // commented above line(s) for double to BigDecimal conversion
                        // sprasad 9/30/2004
                        allocPct = new EDITBigDecimal(investmentAllocationVOs[j].getAllocationPercent());

                        if (overrideStatus.equalsIgnoreCase("O") && allocPct.isEQ("1.0"))
                        {
                            investmentAllocationFound = true;

                            investmentAllocationPK = investmentAllocationVOs[j].getInvestmentAllocationPK();

                            break;
                        }
                    }
                     // end of inner for loop
                }
            }
             //end of outer for loop
        }

        if (!investmentFound)
        {
            InvestmentVO investmentVO = new InvestmentVO();
            investmentVO.setInvestmentPK(investmentPK);
            investmentVO.setFilteredFundFK(filteredFundFK);
            investmentVO.setSegmentFK(SessionHelper.getPKValue(segment.getSegmentPK()));
            investmentPK = new contract.dm.StorageManager().saveInvestmentVO(investmentVO);
        }

        if (!investmentAllocationFound)
        {
            InvestmentAllocationVO investmentAllocationVO = new InvestmentAllocationVO();
            investmentAllocationVO.setInvestmentAllocationPK(investmentAllocationPK);
            investmentAllocationVO.setInvestmentFK(investmentPK);

            // investmentAllocationVO.setAllocationPercent(1.0);
            // commented above line(s) for double to BigDecimal conversion
            // sprasad 9/30/2004
            investmentAllocationVO.setAllocationPercent(new EDITBigDecimal("1.0").getBigDecimal());
            investmentAllocationVO.setOverrideStatus("O");
            investmentAllocationPK = new contract.dm.StorageManager().saveInvestmentAllocationVO(investmentAllocationVO);
        }

        //alwayscreate override
        InvestmentAllocationOverrideVO investmentAllocationOverrideVO = new InvestmentAllocationOverrideVO();
        investmentAllocationOverrideVO.setInvestmentAllocationOverridePK(0);
        investmentAllocationOverrideVO.setContractSetupFK(contractSetupVO.getContractSetupPK());
        investmentAllocationOverrideVO.setInvestmentFK(investmentPK);
        investmentAllocationOverrideVO.setInvestmentAllocationFK(investmentAllocationPK);
        investmentAllocationOverrideVO.setToFromStatus("T");

        contractSetupVO.addInvestmentAllocationOverrideVO(investmentAllocationOverrideVO);
    }

    /**
     * For the deposits matching the taxyear accum the cost basis.
     * @param depositsVOs
     * @param taxYear
     * @return
     */
    private EDITBigDecimal getCostBasis(Set deposits, String taxYear)
    {
        EDITBigDecimal costBasisResult = new EDITBigDecimal("0");

        Iterator it = deposits.iterator();

        while (it.hasNext())
        {
            Deposits deposit = (Deposits) it.next();

            if (deposit.getTaxYear() == (Integer.parseInt(taxYear)))
            {
                EDITBigDecimal costBasis = deposit.getCostBasis();
                EDITBigDecimal amtReceived = deposit.getAmountReceived();

                if (costBasis.isEQ("0"))
                {
                    costBasisResult = costBasisResult.addEditBigDecimal(amtReceived);
                }
                else
                {
                    costBasisResult = costBasisResult.addEditBigDecimal(costBasis);
                }
            }
        }

        return costBasisResult;
    }

    /**
     * For non-qualified contract, contractSetup costBasis will come from deposits
     * costBasis or amount receive.d
     * @param costBasis
     * @param suspenseTotal
     * @param segmentVO
     * @return
     */
    private EDITBigDecimal getContractSetupCostBasis(EDITBigDecimal costBasis, EDITBigDecimal suspenseTotal, SegmentVO segmentVO)
    {
        EDITBigDecimal valueToUse = new EDITBigDecimal("0");

        if (segmentVO.getQualNonQualCT().equalsIgnoreCase("NonQualified"))
        {
            if (costBasis.isGT("0"))
            {
                valueToUse = costBasis;
            }
            else
            {
                valueToUse = suspenseTotal;
            }
        }

        return valueToUse;
    }

    /**
     * Retrieve the lead days for the Payout transaction (based on disbursement source)
     * @param segmentVO
     * @return
     */
    private int getLeadDays(SegmentVO segmentVO, String disbSource)
    {
        long productStructurePK = segmentVO.getProductStructureFK();
        String areaCT = segmentVO.getIssueStateCT();
        String qualifierCT = Util.initString(segmentVO.getQualNonQualCT(), "*");
        String grouping = "PAYOUT";

        EDITDate effectiveDate = new EDITDate(segmentVO.getEffectiveDate());
        String field = null;
        if (disbSource != null && disbSource.equalsIgnoreCase("EFT"))
        {
            field = "LEADDAYSEFT";
        }
        else
        {
            field = "LEADDAYSCHECK";
        }

        int leadDays = 0;

        Area area = new Area(productStructurePK, areaCT, grouping, effectiveDate, qualifierCT);
        if (area != null)
        {
            AreaValue areaValue = area.getAreaValue(field);

            if (areaValue != null)
            {
                leadDays = Integer.parseInt(areaValue.getAreaValue());
            }
        }

        return leadDays;
    }

    /* Get the list of transactions (if any) that need to be generated at the time the
     * contract is issued.
     * @param segment
     * @return
     */
    private String[] getIssueTransactions(Segment segment)
    {
        Long productStructurePK = segment.getProductStructureFK();

        String qualifierCT = "*";
        String areaCT = segment.getIssueStateCT();
        String grouping = "AUTOISSUE";

        EDITDate effectiveDate = segment.getEffectiveDate();
        String field = "TRANSACTION";

        Area area = new Area(productStructurePK.longValue(), areaCT, grouping, effectiveDate, qualifierCT);

        AreaValue[] areaValues = area.getMultipleAreaValues(field);

        String[] issueTransactions = null;

        if (areaValues != null)
        {
            issueTransactions = new String[areaValues.length];
            for (int i = 0; i < areaValues.length; i++)
            {
                AreaValueVO areaValueVO = (AreaValueVO) areaValues[i].getVO();
                issueTransactions[i] = areaValueVO.getAreaValue();
            }
        }

        return issueTransactions;
	}

    //    private void determineIssueProcessing(SegmentVO segmentVO, String operator, String suppressPolicyPages) throws Exception
    //    {
    //        Transaction[] issueTransactions = ServicesConfig.getEDITIssueProcess(segmentVO.getProductStructureFK());
    //
    //        if (issueTransactions != null)
    //        {
    //            ContractEvent contractEvent = new ContractEvent();
    //
    //            for (int i = 0; i < issueTransactions.length; i++)
    //            {
    //
    //                String trxCode = issueTransactions[i].getTransactionTypeCT();
    //                String processingInd = issueTransactions[i].getProcessingInd();
    //
    //                if (trxCode.equals("IS"))
    //                {
    //                    if (processingInd.equalsIgnoreCase("Y"))
    //                    {
    //                        contractEvent.createIssueTrxGroupSetup(segmentVO, segmentVO.getCreationOperator(), suppressPolicyPages);
    //                    }
    //                    else
    //                    {
    //                        // send trx directly to history with a status of "G"
    //                        contractEvent.createIssueTrx(segmentVO, segmentVO.getCreationOperator(), suppressPolicyPages);
    //                    }
    //                }
    //            }
    //        }
    //    }
    //    public static void main(String[] args) throws Exception
    //    {
    //        ContractEvent contractEvent = new ContractEvent();
    //
    //        contract.business.Lookup contractLookup = new contract.component.LookupComponent();
    //        SegmentVO[] segmentVO = contractLookup.findBySegmentPK(1066420765658L, true, null);
    //        contractEvent.commitContract(segmentVO[0], "seg", "N", null);
    //
    //        System.out.println("done");
    //    }
}
