/*
 * User: gfrosti
 * Date: Nov 3, 2003
 * Time: 3:17:15 PM
 *
 * (c) 2000-2008 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package agent;

import agent.dm.dao.*;
import batch.business.Batch;
import edit.common.*;
import edit.common.vo.*;
import edit.services.EditServiceLocator;
import edit.services.db.hibernate.*;
import event.dm.composer.CommissionHistoryComposer;
import event.dm.dao.FastDAO;
import event.*;

import java.util.*;

import logging.*;
import role.*;
import group.BatchContractSetup;
import contract.ContractClient;
import client.ClientDetail;
import client.ClientAddress;



public class CommissionController
{
    /**
     * Get all the commission history keys where the update status equals "U".  Create a commission statement for each of
     * these records by building a fluffy vo, with agent information.
     * @param companyName = 'All'
     * @throws Exception
     */
    public void updateAgentCommissions(String companyName) throws Exception
    {
        EditServiceLocator.getSingleton().getBatchAgent().getBatchStat(Batch.BATCH_JOB_UPDATE_AGENT_COMMISSIONS).tagBatchStart(Batch.BATCH_JOB_UPDATE_AGENT_COMMISSIONS, "Agent Commission");

        //Before the agent update create checkAdjustment transactions and CommissionHistory for them.
        processAllCheckAdjustments();
        // The companyName is being ignored for now - we are just doing all of them.
        event.business.Event eventComponent = new event.component.EventComponent();

        try
        {
            // The DAO method requireds that the statuses are passed in the order 'U' and 'L'
            // Warning: Changing the order gives incorrect results.
            long[] commissionHistoryPKs = new FastDAO().findCommissionHistoryPKsByUpdateStatus_NOT_SegmentStatusCT(new String[] {"U", "L"}, "ActivePendingComm");

            if (commissionHistoryPKs != null)
            {
                for (int i = 0; i < commissionHistoryPKs.length; i++)
                {
                    AgentVO agentVO = null;
                    EDITTrxVO editTrxVO = null;
                    ClientRole clientRole = null;

                    try
                    {
                        CommissionHistoryVO commissionHistoryVO = getCommissionHistoryVO(commissionHistoryPKs[i]);

                        editTrxVO = (EDITTrxVO) commissionHistoryVO.getParentVO(EDITTrxHistoryVO.class).getParentVO(EDITTrxVO.class);

                        agentVO = getAgentVO(commissionHistoryVO);

                        Agent agent = new Agent(agentVO);


                        ClientSetupVO clientSetupVO = (ClientSetupVO) editTrxVO.getParentVO(ClientSetupVO.class);
                        ContractSetupVO contractSetupVO = (ContractSetupVO) clientSetupVO.getParentVO(ContractSetupVO.class);

                        long clientRolePK = clientSetupVO.getClientRoleFK();
                        clientRole = new ClientRole(clientRolePK);

                        SegmentVO segmentVO = null;

                        if (contractSetupVO.getParentVOs() != null)
                        {
                            segmentVO = (SegmentVO) contractSetupVO.getParentVO(SegmentVO.class);
                        }

                        String issueState = getIssueState(segmentVO);
                        String enrollmentMethod = getEnrollmentMethod(segmentVO);
                        String productType = getProductType(segmentVO);

                        long productStructureFK = getProductStructureFK(commissionHistoryVO);

                        EDITBigDecimal commissionAmount = getCommissionAmount(commissionHistoryVO);

                        EDITBigDecimal debitBalanceAmount = new EDITBigDecimal(commissionHistoryVO.getDebitBalanceAmount());

                        EDITBigDecimal adaAmount = new EDITBigDecimal(commissionHistoryVO.getADAAmount());

                        EDITBigDecimal expenseAmount = new EDITBigDecimal(commissionHistoryVO.getExpenseAmount());

                        EDITBigDecimal advanceAmount = new EDITBigDecimal();

                        String trxEffectiveDate = editTrxVO.getEffectiveDate();

                        EDITBigDecimal commissionTaxable = new EDITBigDecimal(commissionHistoryVO.getCommissionTaxable());

                        long checkTo = commissionHistoryVO.getCheckTo();

                        String commissionTypeCT = commissionHistoryVO.getCommissionTypeCT();

                        if (commissionTypeCT.equalsIgnoreCase(CommissionHistory.COMMISSIONTYPECT_ADVANCE) ||
                            commissionTypeCT.equalsIgnoreCase(CommissionHistory.COMMISSIONTYPECT_ADVANCE_RECOVERY) ||
                            commissionTypeCT.equalsIgnoreCase(CommissionHistory.COMMISSIONTYPECT_ADVANCE_CHARGEBACK) ||
                            commissionTypeCT.equalsIgnoreCase(CommissionHistory.COMMISSIONTYPECT_RECOVERY_CHARGEBACK) ||
                            commissionTypeCT.equalsIgnoreCase(CommissionHistory.COMMISSIONTYPECT_ADVANCE_CHARGEBACK_REVERSAL) ||
                            commissionTypeCT.equalsIgnoreCase(CommissionHistory.COMMISSIONTYPECT_TERM_ADVANCE_CHARGEBACK) ||
                            commissionTypeCT.equalsIgnoreCase(CommissionHistory.COMMISSIONTYPECT_TERM_ADVANCE_CHARGEBACK_REVERSAL))
                        {
                            advanceAmount = commissionAmount;
                        }

                        //  If the commissionAmount is not equal to zero, validate the agent license
                        //  We don't want to check the license if the commission rate is zero.  A zero commission rate
                        //  equates to a zero commissionAmount

                        if (! new EDITBigDecimal(commissionHistoryVO.getCommissionAmount()).isEQ(EDITBigDecimal.EDIT_DECIMAL_ZILCH_STR) &&
                            !commissionTypeCT.equalsIgnoreCase(CommissionHistory.COMMISSIONTYPECT_COMM_ADJ))
                        {
                            String licenseValidationState = getLicenseValidationState(segmentVO);
                            agent.validateAgentLicense(trxEffectiveDate, licenseValidationState, productStructureFK, enrollmentMethod, productType);
                        }


                        agent.updateAgentCommissions(commissionAmount, adaAmount, expenseAmount, trxEffectiveDate, commissionTaxable, commissionTypeCT, advanceAmount, debitBalanceAmount, checkTo, commissionHistoryVO.getPlacedAgentFK());

                        AdditionalCompensationVO[] additionalCompensationVO = ((AgentContractVO) commissionHistoryVO.getParentVO(PlacedAgentVO.class).getParentVO(AgentContractVO.class)).getAdditionalCompensationVO();

                        if ((additionalCompensationVO != null) && (additionalCompensationVO.length > 0) && !isAgentGroupCommissionHistory(commissionHistoryVO))
                        {
                            String bonusCommissionStatus = additionalCompensationVO[0].getBonusCommissionStatus();

                            if ((bonusCommissionStatus != null) && bonusCommissionStatus.equals("Y"))
                            {
                                commissionHistoryVO.setUpdateStatus("B");
                            }
                            else
                            {
                                commissionHistoryVO.setUpdateStatus("H");

                                commissionHistoryVO.setUpdateDateTime(new EDITDateTime().getFormattedDateTime());
                            }
                        }
                        else
                        {
                            commissionHistoryVO.setUpdateStatus("H");

                            commissionHistoryVO.setUpdateDateTime(new EDITDateTime().getFormattedDateTime());
                        }

                        eventComponent.createOrUpdateVO(commissionHistoryVO, false);

                        //clear memory for the next commission history reocord
                        commissionHistoryVO = null;

                        agentVO = null;

                        additionalCompensationVO = null;

                        EditServiceLocator.getSingleton().getBatchAgent().getBatchStat(Batch.BATCH_JOB_UPDATE_AGENT_COMMISSIONS).updateSuccess();
                    }
                    catch (Exception e)
                    {
                        EditServiceLocator.getSingleton().getBatchAgent().getBatchStat(Batch.BATCH_JOB_UPDATE_AGENT_COMMISSIONS).updateFailure();

                        String message = "[* Agent Update Process *] Failed For CommissionHistoryPK [" + commissionHistoryPKs[i] + "]";

                        System.out.println(message);
              
                        System.out.println(e);

                        e.printStackTrace(); //To change body of catch statement use Options | File Templates.

                        logErrorToDatabase(message, clientRole, editTrxVO);
                    }
                }

                commissionHistoryPKs = null;
            }
        }
        finally
        {
            EditServiceLocator.getSingleton().getBatchAgent().getBatchStat(Batch.BATCH_JOB_UPDATE_AGENT_COMMISSIONS).tagBatchStop();
        }
    }

    private String getLicenseValidationState(SegmentVO segmentVO)
    {
        String enrollmentMethod = null;
        String licenseValidationState = null;
        BatchContractSetup batchContractSetup = BatchContractSetup.findByPK(new Long(segmentVO.getBatchContractSetupFK()));

        //Set issueState either from residenceState or AppicationSignedState depending on the value in enrollmentMethod
        if (batchContractSetup != null)
        {
            enrollmentMethod = batchContractSetup.getEnrollmentMethodCT();
            if (enrollmentMethod.equalsIgnoreCase("FaceToFace"))
            {
                licenseValidationState = batchContractSetup.getApplicationSignedStateCT();
            }
            else
            {
                ContractClient[] contractClients = ContractClient.findBy_SegmentFK(new Long(segmentVO.getSegmentPK()));

                ContractClient ownerContractClient = ContractClient.getOwnerContractClient(contractClients);
                ClientRole clientRole = ownerContractClient.getClientRole();
                ClientDetail clientDetail = clientRole.getClientDetail();
                ClientAddress clientAddress = clientDetail.getPrimaryAddress();

                licenseValidationState = clientAddress.getStateCT();
            }
        }

        return licenseValidationState;
    }

    //private void logErrorToDatabase(String message, AgentVO agentVO, EDITTrxVO editTrxVO)
     private void logErrorToDatabase(String message, ClientRole clientRole, EDITTrxVO editTrxVO)
    {
        EDITTrx editTrx = EDITTrx.findBy_PK(new Long(editTrxVO.getEDITTrxPK()));

        EDITMap columnInfo = new EDITMap("ProcessDate", new EDITDate().getFormattedDate());
        columnInfo.put("AgentNumber", clientRole.getReferenceID());
        columnInfo.put("ContractNumber", editTrx.getContractNumber());
        columnInfo.put("TransactionType", editTrx.getTransactionTypeCT());

        Log.logToDatabase(Log.AGENT_UPDATE, message, columnInfo);
    }

    /**
     * Gets the AgentVO based on the CommissionHistory being associated with a PlacedAgent or an AgentGroup.
     * @param commissionHistoryVO
     * @return
     */
    private AgentVO getAgentVO(CommissionHistoryVO commissionHistoryVO)
    {
        AgentVO agentVO = null;

        if (isAgentGroupCommissionHistory(commissionHistoryVO))
        {
            agentVO = DAOFactory.getAgentDAO().findBy_AgentGroupPK(commissionHistoryVO.getAgentGroupFK())[0];
        }
        else
        {
            agentVO = DAOFactory.getAgentDAO().findByPlacedAgentPK(commissionHistoryVO.getPlacedAgentFK())[0];
        }

        return agentVO;
    }

    /**
     * Composes CommissionHistoryVO by whether or not the CommissionHistoryVO has an associated PlacedAgent or an
     * associated AgentGroup.
     * @param commissionHistoryPK
     * @return
     * @throws Exception
     */
    private CommissionHistoryVO getCommissionHistoryVO(long commissionHistoryPK)
            throws Exception
    {
        List voInclusionList = new ArrayList();
        voInclusionList.add(EDITTrxHistoryVO.class);
        voInclusionList.add(EDITTrxVO.class);
        voInclusionList.add(ClientSetupVO.class);
        voInclusionList.add(ContractSetupVO.class);
        voInclusionList.add(SegmentVO.class);
        voInclusionList.add(PlacedAgentVO.class);
        voInclusionList.add(AdditionalCompensationVO.class);
        voInclusionList.add(AgentContractVO.class);

        CommissionHistoryVO commissionHistoryVO = new CommissionHistoryComposer(voInclusionList).compose(commissionHistoryPK);

        return commissionHistoryVO;
    }

    /**
     * Checks whether or not the specified CommissionHistory is associated with an AgentGroup.
     * @param commissionHistoryVO
     * @return true if the specified CommissionHistory is associated with an AgentGroup
     */
    private boolean isAgentGroupCommissionHistory(CommissionHistoryVO commissionHistoryVO)
    {
        return (commissionHistoryVO.getAgentGroupFK() != 0);
    }

    /**
     * Get all the commission history keys where the update status equals "B".
     * @param companyName
     * @param fromDate
     * @param toDate
     * @throws Exception
     */
    public void updateBonusCommissions(String companyName, String fromDate, String toDate)
        throws Exception
    {
        EDITDate fromDateED = new EDITDate(fromDate);
        EDITDate toDateED = new EDITDate(toDate);

        // The companyName is being ignored for now - we are just doing all of them.
        event.business.Event eventComponent = new event.component.EventComponent();

        long[] commissionHistoryPKs = new FastDAO().findCommissionHistoryPKsByUpdateStatus("B");

        if (commissionHistoryPKs != null)
        {
            for (int i = 0; i < commissionHistoryPKs.length; i++)
            {
                try
                {
                    List voInclusionList = new ArrayList();
                    voInclusionList.add(EDITTrxHistoryVO.class);
                    voInclusionList.add(EDITTrxVO.class);
                    voInclusionList.add(ClientSetupVO.class);
                    voInclusionList.add(ContractSetupVO.class);
                    voInclusionList.add(SegmentVO.class);
                    voInclusionList.add(PlacedAgentVO.class);
                    voInclusionList.add(AdditionalCompensationVO.class);
                    voInclusionList.add(AgentContractVO.class);

                    CommissionHistoryVO commissionHistoryVO = new CommissionHistoryComposer(voInclusionList).compose(commissionHistoryPKs[i]);
                    EDITDate updateDateED = new EDITDate(commissionHistoryVO.getUpdateDateTime().substring(0, 10));

                    if ((updateDateED.after(fromDateED) || updateDateED.equals(fromDateED)) && (updateDateED.before(toDateED) || updateDateED.equals(toDateED)))
                    {
                        EDITTrxVO editTrxVO = (EDITTrxVO) commissionHistoryVO.getParentVO(EDITTrxHistoryVO.class).getParentVO(EDITTrxVO.class);
                        ContractSetupVO contractSetupVO = (ContractSetupVO) editTrxVO.getParentVO(ClientSetupVO.class).getParentVO(ContractSetupVO.class);
                        SegmentVO segmentVO = null;

                        if (contractSetupVO.getParentVOs() != null)
                        {
                            segmentVO = (SegmentVO) contractSetupVO.getParentVO(SegmentVO.class);
                        }

                        // Only perform the update if the contract is in an active status
                        if ((segmentVO != null) && segmentVO.getSegmentStatusCT().equalsIgnoreCase("Active"))
                        {
                            AgentVO agentVO = DAOFactory.getAgentDAO().findByPlacedAgentPK(commissionHistoryVO.getPlacedAgentFK())[0];

                            Agent agent = new Agent(agentVO);


                            String issueState = getIssueState(segmentVO);
                            String enrollmentMethod = getEnrollmentMethod(segmentVO);
                            String productType = getProductType(segmentVO);

                            long productStructureFK = getProductStructureFK(commissionHistoryVO);

                            EDITBigDecimal bonusAmount = getBonusCommissionAmount(commissionHistoryVO);
                            String trxEffectiveDate = editTrxVO.getEffectiveDate();

                            EDITBigDecimal commissionTaxable = new EDITBigDecimal(commissionHistoryVO.getCommissionTaxable());
                            String commissionTypeCT = commissionHistoryVO.getCommissionTypeCT();

                            //  If the commissionAmount is not equal to zero, validate the agent license
                            //  We don't want to check the license if the commission rate is zero.  A zero commission rate
                            //  equates to a zero commissionAmount
                            if (! new EDITBigDecimal(commissionHistoryVO.getCommissionAmount()).isEQ(EDITBigDecimal.EDIT_DECIMAL_ZILCH_STR))
                            {
                                String licenseValidationState = getLicenseValidationState(segmentVO);
                                agent.validateAgentLicense(trxEffectiveDate, issueState, productStructureFK, enrollmentMethod, productType);
                            }

                            agent.updateBonusCommissions(bonusAmount, trxEffectiveDate, commissionTaxable, commissionTypeCT, commissionHistoryVO.getPlacedAgentFK());

                            commissionHistoryVO.setUpdateStatus("H");
                            commissionHistoryVO.setUpdateDateTime(new EDITDateTime().getFormattedDateTime());

                            eventComponent.createOrUpdateVO(commissionHistoryVO, false);

                            //clear memory for the next commission history reocord
                            commissionHistoryVO = null;
                            agentVO = null;
                        }
                    }
                }
                catch (Exception e)
                {
                    System.out.println("[* Bonus Commission Update Process *] Failed For CommissionHistoryPK [" + commissionHistoryPKs[i] + "]");
            
                    System.out.println(e);
        
                    e.printStackTrace(); //To change body of catch statement use Options | File Templates.
                }
            }

            commissionHistoryPKs = null;
        }
    }

    /**
     * Creates the Commission Adjustment (CA) transaction for the specified agent
     * Initiated from the Extract Dialog page, add button
     * @param filterAgentId
     * @param commissionAmount - entered on the commission extract page in agent detail
     * @param reduceTaxable - one-byte indicator which specifies if this adjustment should reduce the taxable amount
     * @param operator - the operator id of the user creating the commission adjustment
     * @throws Exception
     */
    public void createCommissionAdjustment(String filterAgentId, EDITBigDecimal commissionAmount, String reduceTaxable, String operator)
        throws Exception
    {
        ClientRole clientRole = ClientRole.findBy_RoleType_ReferenceID(ClientRole.ROLETYPECT_AGENT, filterAgentId)[0];

        if (clientRole != null)
        {
            Agent agent = clientRole.getAgent();

            PlacedAgent placedAgent = null;

            Set<AgentContract> agentContracts = agent.getAgentContracts();

            Iterator it = agentContracts.iterator();
            while (it.hasNext())
            {
                AgentContract agentContract = (AgentContract) it.next();
                Set<PlacedAgent> placedAgents = agentContract.getPlacedAgents();
                if (!placedAgents.isEmpty())
                {
                    Iterator it2 = placedAgents.iterator();
                    placedAgent = (PlacedAgent) it2.next();
                }
            }

            SessionHelper.beginTransaction(SessionHelper.EDITSOLUTIONS);
            GroupSetup groupSetup = setDefaultsForTrxAndHistory(commissionAmount, CommissionHistory.COMMISSIONTYPECT_ADJUSTMENT, placedAgent);

            EDITTrx editTrx = groupSetup.getContractSetup().getClientSetup().getEDITTrx();
            editTrx.setEffectiveDate(new EDITDate());
            editTrx.setTaxYear(new EDITDate().getYear());

            CommissionHistory commissionHistory = getCommissionHistory(editTrx);

            commissionHistory.setOperator(operator);
            commissionHistory.setReduceTaxable(reduceTaxable);

            SessionHelper.saveOrUpdate(groupSetup, SessionHelper.EDITSOLUTIONS);
            SessionHelper.commitTransaction(SessionHelper.EDITSOLUTIONS);
            SessionHelper.closeSession(SessionHelper.EDITSOLUTIONS);
        }
    }

    private EDITBigDecimal getCommissionAmount(CommissionHistoryVO commissionHistoryVO)
    {
        // double commissionAmount = commissionHistoryVO.getCommissionAmount();
        // commented above line(s) for double to BigDecimal conversion
        // sprasad 9/24/2004
        EDITBigDecimal commissionAmount = new EDITBigDecimal(commissionHistoryVO.getCommissionAmount());

        String commissionTypeCT = commissionHistoryVO.getCommissionTypeCT();

        if (commissionTypeCT.equals("ChargeBack"))
        {
            // commissionAmount *= -1.0;
            // commented above line(s) for double to BigDecimal conversion
            // sprasad 9/24/2004
            commissionAmount = commissionAmount.negate();
        }

        return commissionAmount;
    }

    private EDITBigDecimal getBonusCommissionAmount(CommissionHistoryVO commissionHistoryVO)
    {
        // double bonusCommissionAmount = commissionHistoryVO.getBonusCommissionAmount();
        // commented above line(s) for double to BigDecimal conversion
        // sprasad 9/24/2004
        //
        EDITBigDecimal bonusCommissionAmount = null;

        //
        //        bonusCommissionAmount = new EDITBigDecimal( commissionHistoryVO.getBonusCommissionAmount() );
        //
        //
        //        String commissionTypeCT = commissionHistoryVO.getCommissionTypeCT();
        //
        //        if (commissionTypeCT.equals("ChargeBack"))
        //        {
        //            // bonusCommissionAmount *= -1.0;
        //            // commented above line(s) for double to BigDecimal conversion
        //            // sprasad 9/24/2004
        //            bonusCommissionAmount = bonusCommissionAmount.negate();
        //        }
        return bonusCommissionAmount;
    }

    private String getIssueState(SegmentVO segmentVO)
    {
        String issueState = null;

        if (segmentVO != null)
        {
            issueState = segmentVO.getIssueStateCT();
        }

        return issueState;
    }

    private String getEnrollmentMethod(SegmentVO segmentVO)
    {
        String enrollmentMethod = null;

        if (segmentVO != null)
        {
            long batchContractSetupFK = segmentVO.getBatchContractSetupFK();
            if (batchContractSetupFK > 0)
            {
                BatchContractSetup batchContractSetup = BatchContractSetup.findByPK(new Long(batchContractSetupFK));
                if (batchContractSetup != null)
                {
                    enrollmentMethod = batchContractSetup.getEnrollmentMethodCT();
                }
            }
        }

        return enrollmentMethod;
    }

    private String getProductType(SegmentVO segmentVO)
       {
           String productType = "";

           if (segmentVO != null)
           {
               productType = segmentVO.getSegmentNameCT();
           }

           return productType;
    }


    private long getProductStructureFK(CommissionHistoryVO commissionHistoryVO)
    {
        EDITTrxVO editTrxVO = (EDITTrxVO) commissionHistoryVO.getParentVO(EDITTrxHistoryVO.class).getParentVO(EDITTrxVO.class);
        ClientSetupVO clientSetupVO = (ClientSetupVO) editTrxVO.getParentVO(ClientSetupVO.class);
        ContractSetupVO contractSetupVO = (ContractSetupVO) clientSetupVO.getParentVO(ContractSetupVO.class);
        SegmentVO segmentVO = null;

        if (contractSetupVO.getParentVOs() != null)
        {
            segmentVO = (SegmentVO) contractSetupVO.getParentVO(SegmentVO.class);
        }

        long productStructureFK = 0;

        if (segmentVO != null)
        {
            productStructureFK = segmentVO.getProductStructureFK();
        }

        return productStructureFK;
    }


    /**
     * Pre-process for the AgentUpdate job.  Create pending CommissionHistory with 'CA' transactions
     * in a history status, for all qualifying adjustments.  The next due date must less than or equal to
     * the current date.
     */
    private void processAllCheckAdjustments()
    {
        try
        {
            long[] checkAdjustmentPKs = CheckAdjustment.getQualifyingAdjustmentPKs();
            
            if (checkAdjustmentPKs != null)
            {
                for (int i = 0; i < checkAdjustmentPKs.length; i++)
                {
                    try
                    {
                        SessionHelper.beginTransaction(SessionHelper.EDITSOLUTIONS);
                        
                        CheckAdjustment checkAdjustment = CheckAdjustment.findByPK(new Long(checkAdjustmentPKs[i]));

                        if (!checkAdjustment.getAdjustmentTypeCT().equalsIgnoreCase(CheckAdjustment.DEBIT_BALANCE_AUTO_REPAY))
                        {
                            //this trx doesn't go through scripts, the trx and its history is hard-coded
                            createAndSaveCATrx(checkAdjustment);

                            //For scheduled adjustments reset the nextDueDate until it exceeds the stop date
                            if (checkAdjustment.getAdjustmentStatusCT().equalsIgnoreCase(CheckAdjustment.SCHEDULED_ADJUSTMENT_STATUS))
                            {
                                updateNextDueDate(checkAdjustment);
                            } 
                            else
                            {
                                checkAdjustment.setAdjustmentCompleteInd("Y");
                            }

                            checkAdjustment.hSave();
                        }
                        
                        SessionHelper.commitTransaction(SessionHelper.EDITSOLUTIONS);
                    } 
                    catch (Exception e)
                    {
                        SessionHelper.rollbackTransaction(SessionHelper.EDITSOLUTIONS);

                        System.out.println(e);
                        
                        e.printStackTrace();                       
                    } 
                    finally
                    {
                        SessionHelper.closeSession(SessionHelper.EDITSOLUTIONS);
                    }
                }
            }
        }
        catch (Exception e)
        {
            System.out.println(e);

            e.printStackTrace();
        }
    }

    private AgentVO getClientRoleKey(long agentFK)
    {
        List voExclusionList = new ArrayList();
        voExclusionList.add(AgentNoteVO.class);
        voExclusionList.add(CheckAdjustmentVO.class);
        voExclusionList.add(RedirectVO.class);
        voExclusionList.add(VestingVO.class);
        voExclusionList.add(AgentLicenseVO.class);
        voExclusionList.add(CommissionProfileVO.class);
        voExclusionList.add(ParticipatingAgentVO.class);
        voExclusionList.add(CommissionHistoryVO.class);
        voExclusionList.add(AgentSnapshotVO.class);

        AgentVO[] agentVOs = new AgentDAO().findByAgentPK(agentFK, true, voExclusionList);

        return agentVOs[0];
    }

    /**
     * Creation of CA trx for AgentUpdate Processing
     * @param checkAdjustment
     */
    private void createAndSaveCATrx(CheckAdjustment checkAdjustment)
    {
        EDITBigDecimal amount = checkAdjustment.getAdjustmentDollar();

        String adjustmentTypeCT = checkAdjustment.getAdjustmentTypeCT();
        
        PlacedAgent placedAgent = checkAdjustment.getPlacedAgent();
        
        GroupSetup groupSetup = setDefaultsForTrxAndHistory(amount, adjustmentTypeCT, placedAgent);

        EDITTrx editTrx = groupSetup.getContractSetup().getClientSetup().getEDITTrx();
        editTrx.setEffectiveDate(checkAdjustment.getNextDueDate());
        editTrx.setTaxYear(checkAdjustment.getNextDueDate().getYear());
        editTrx.setCheckAdjustmentFK(checkAdjustment.getCheckAdjustmentPK());

        CommissionHistory commissionHistory = getCommissionHistory(editTrx);

        if (checkAdjustment.getTaxableStatusCT().equalsIgnoreCase(CheckAdjustment.NON_TAXABLE_STATUS))
        {
            commissionHistory.setCommissionNonTaxable(amount);
            commissionHistory.setReduceTaxable("N");
        }

        if (checkAdjustment.getTaxableStatusCT().equalsIgnoreCase(CheckAdjustment.TAXABLE_STATUS))
        {
            commissionHistory.setCommissionTaxable(amount);
            commissionHistory.setReduceTaxable("N");
        }
        if (checkAdjustment.getTaxableStatusCT().equalsIgnoreCase(CheckAdjustment.REDUCE_TAXABLE_STATUS))
        {
            commissionHistory.setReduceTaxable("Y");
        }
        if (checkAdjustment.getAdjustmentTypeCT().equalsIgnoreCase(CheckAdjustment.DEBIT_BAL_PAYOFF_NOTICE_TYPE))
        {
            commissionHistory.setUpdateStatus("H");
        }
        else
        {
            commissionHistory.setUpdateStatus("U");
        }

        commissionHistory.setOperator("System");

        SessionHelper.saveOrUpdate(groupSetup, SessionHelper.EDITSOLUTIONS);
    }

    private GroupSetup setDefaultsForTrxAndHistory(EDITBigDecimal commissionAmount, String commissionTypeCT, PlacedAgent placedAgent)
    {
        GroupSetup groupSetup = (GroupSetup)SessionHelper.newInstance(GroupSetup.class, SessionHelper.EDITSOLUTIONS);

        groupSetup.setGroupAmount(commissionAmount);

        ContractSetup contractSetup = (ContractSetup) SessionHelper.newInstance(ContractSetup.class, SessionHelper.EDITSOLUTIONS);
        contractSetup.setPolicyAmount(commissionAmount);

        ClientRole clientRole = placedAgent.getClientRole();
        ClientSetup clientSetup = (ClientSetup) SessionHelper.newInstance(ClientSetup.class, SessionHelper.EDITSOLUTIONS);
        clientRole.addClientSetup(clientSetup);

        EDITTrx editTrx = (EDITTrx) SessionHelper.newInstance(EDITTrx.class, SessionHelper.EDITSOLUTIONS);
        editTrx.setStatus("N");
        editTrx.setPendingStatus("H");
        editTrx.setSequenceNumber(1);
        editTrx.setTrxAmount(commissionAmount);
        editTrx.setTransactionTypeCT("CA");
        editTrx.setMaintDateTime(new EDITDateTime());
        editTrx.setOperator("System");
        editTrx.setTrxIsRescheduledInd("N");
        editTrx.setCommissionStatus("Y");
        editTrx.setLookBackInd("N");
        editTrx.setNoAccountingInd("N");
        editTrx.setNoCommissionInd("N");
        editTrx.setZeroLoadInd("N");
        editTrx.setNoCorrespondenceInd("Y");
        editTrx.setPremiumDueCreatedIndicator("N");

        CommissionHistory commissionHistory = (CommissionHistory) SessionHelper.newInstance(CommissionHistory.class, SessionHelper.EDITSOLUTIONS);
        placedAgent.addCommissionHistory(commissionHistory);
        commissionHistory.setCommissionAmount(commissionAmount);
        commissionHistory.setUpdateStatus("U");
        commissionHistory.setAccountingPendingStatus("Y");
        commissionHistory.setMaintDateTime(new EDITDateTime());
        commissionHistory.setSourcePlacedAgentFK(placedAgent.getPlacedAgentPK());
        commissionHistory.setCommissionTypeCT(commissionTypeCT);
        commissionHistory.setUpdateDateTime(new EDITDateTime());
        commissionHistory.setStatementInd("Y");

        EDITTrxHistory editTrxHistory = (EDITTrxHistory) SessionHelper.newInstance(EDITTrxHistory.class, SessionHelper.EDITSOLUTIONS);
        editTrxHistory.setProcessDateTime(new EDITDateTime());
        editTrxHistory.setCycleDate(new EDITDate());
        editTrxHistory.setAccountingPendingStatus("N");

        editTrxHistory.addCommissionHistory(commissionHistory);
        editTrx.addEDITTrxHistory(editTrxHistory);
        clientSetup.addEDITTrx(editTrx);
        contractSetup.addClientSetup(clientSetup);
        groupSetup.addContractSetup(contractSetup);

        return groupSetup;
    }

    private void updateNextDueDate(CheckAdjustment checkAdjustment)
    {
        EDITDate nextDueDate = checkAdjustment.getNextDueDate();

        nextDueDate = nextDueDate.addMode(checkAdjustment.getModeCT());
        EDITDate stopDate = checkAdjustment.getStopDate();

        if (nextDueDate.after(stopDate))
        {
            checkAdjustment.setAdjustmentCompleteInd("Y");
        }
        else
        {
            checkAdjustment.setNextDueDate(nextDueDate);
        }
    }

    private CommissionHistory getCommissionHistory(EDITTrx editTrx)
    {
        CommissionHistory commissionHistory = null;
        Set editTrxHistories= editTrx.getEDITTrxHistories();

        for (Iterator iterator = editTrxHistories.iterator(); iterator.hasNext();)
        {
             EDITTrxHistory editTrxHistory = (EDITTrxHistory) iterator.next();
             Set commissionHistories = editTrxHistory.getCommissionHistories();

             if(!commissionHistories.isEmpty())
             {
                 for (Iterator iteratorCom = commissionHistories.iterator(); iteratorCom.hasNext();)
                 {
                     commissionHistory = (CommissionHistory) iteratorCom.next();
                 }
             }
        }

        return commissionHistory;
    }
}
