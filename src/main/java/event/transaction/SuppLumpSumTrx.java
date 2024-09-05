/*
 * User: cgleason
 * Date: Jul 27, 2005
 * Time: 8:41:13 AM
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package event.transaction;

import codetable.TrxProcessDocforPerformance;

import contract.ChangeHistory;
import contract.ContractClient;
import contract.ContractClientAllocation;
import contract.Segment;

import edit.common.EDITBigDecimal;
import edit.common.EDITDate;
import edit.common.vo.*;
import edit.common.exceptions.EDITEventException;

import event.ClientSetup;
import event.ContractClientAllocationOvrd;
import event.ContractSetup;
import event.EDITTrx;
import event.GroupSetup;

import event.financial.client.trx.ClientTrx;
import event.financial.contract.trx.*;


public class SuppLumpSumTrx  extends TransactionProcessor
{
    Segment segment;
    ContractClient contractClient;

    public SuppLumpSumTrx()
    {

    }

    public SuppLumpSumTrx(Segment segment)
    {
        this.segment = segment;
        this.contractClient = segment.getOwnerContractClient();
    }



    public EDITTrx createSuppLumpSumTrx(String operator, String newContractNumber, ContractClient beneContractClient)
    {
        ContractClientAllocationOvrd contractClientAllocationOvrd = createAllocationOverride(beneContractClient);

        GroupSetup groupSetup = initializeGroupSetup();

        ContractSetup contractSetup = TransactionProcessor.buildDefaultContractSetup(segment);

        ClientSetup clientSetup = new ClientSetup();
        clientSetup.setContractClient(contractClient);
        clientSetup.setClientRole(contractClient.getClientRole());

        clientSetup.addContractClientAllocationOvrd(contractClientAllocationOvrd);
        contractSetup.addClientSetup(clientSetup);
        groupSetup.addContractSetup(contractSetup);

        ChangeHistory[] changeHistories = ChangeHistory.findByModifiedKey_AfterValue(segment.getSegmentPK());
        EDITDate effectiveDate = null;

        if (changeHistories != null && changeHistories.length > 0)
        {
            if (changeHistories.length > 1)
            {
                int i = changeHistories.length - 1;
                effectiveDate = changeHistories[i].getEffectiveDate();
            }
            else
            {
                effectiveDate = changeHistories[0].getEffectiveDate();
            }
        }
        else
        {
            effectiveDate = new EDITDate();
        }

        int taxYear = effectiveDate.getYear();

        EDITTrx editTrx = buildDefaultEDITTrx("SLS", effectiveDate, taxYear, operator, newContractNumber);
        clientSetup.addEDITTrx(editTrx);

        super.checkForEvents(editTrx, segment);

        return editTrx;
     }

    /**
     * The suppLumpSum trx will always have this contractClientAllocation attached as an override
     * to the transaction.
     * @param allocationPct
     * @return
     */
    public ContractClientAllocationOvrd createAllocationOverride(ContractClient beneContractClient)
    {
        ContractClientAllocation contractClientAllocation  = beneContractClient.getContractClientAllocation();


//        String splitEqualInd = contractClientAllocation.getSplitEqual();
//        contractClientAllocation.setOverrideStatus("O");
//        contractClientAllocation.setAllocationPercent(allocationPct);
//        contractClientAllocation.setSplitEqual(splitEqualInd);

//        contractClient.addContractClientAllocation(contractClientAllocation);

        ContractClientAllocationOvrd contractClientAllocationOvrd = new ContractClientAllocationOvrd();
        contractClientAllocation.addContractClientAllocationOverride(contractClientAllocationOvrd);

        //save contractClient, allocation and possibly withholding
//        SessionHelper.saveOrUpdate(contractClient, SessionHelper.EDITSOLUTIONS);

        return contractClientAllocationOvrd;
    }

     /**
      * When the effective date of the trx is less than the current date the transaction will execute immediately
      * @param operator
      * @param editTrx
      * @param segment
      * @throws edit.common.exceptions.EDITEventException
      */
     public void checkForExecutionOfSuppLumpSum(String operator, EDITTrx editTrx)  throws EDITEventException, Exception
     {
        long contractSetupPK = editTrx.getClientSetup().getContractSetup().getPK();
        ContractSetupVO contractSetupVO = ContractSetup.findByPK(contractSetupPK);

        long clientSetupPK = editTrx.getClientSetup().getPK();
        ClientSetupVO clientSetupVO = ClientSetup.findByPK(clientSetupPK);

        EDITTrxVO editTrxVO = (EDITTrxVO) editTrx.getAsVO();

        editTrxVO.setClientSetupFK(clientSetupVO.getClientSetupPK());
        clientSetupVO.addEDITTrxVO(editTrxVO);
        contractSetupVO.addClientSetupVO(clientSetupVO);

        try
        {
            new ContractEvent().executeRealTime(contractSetupVO);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw(e);
        }

//        this.editTrx = editTrx;
//        //Determine execution
//        if (super.isBackdated())
//        {
//            //Create clientTrx object
//            ClientTrx clientTrx = new ClientTrx(editTrx.getAsVO(), operator);
//            clientTrx.setExecutionMode(ClientTrx.REALTIME_MODE);
//
//            String eventType = segment.setEventTypeForDriverScript();
//            Long productKey = segment.getProductStructureFK();

//            executeSuppLumpSumTransaction(clientTrx, eventType, productKey.longValue(), editTrx);
//        }
    }

    /**
     * Build the document model, then execute
     * @param clientTrx
     * @param eventType
     * @param productKey
     * @param editTrx
     * @throws edit.common.exceptions.EDITEventException
     */
    private void executeSuppLumpSumTransaction(ClientTrx clientTrx, String eventType, long productKey, EDITTrx editTrx)  throws EDITEventException
    {
        TrxProcessDocforPerformance praseDocument = super.buildDocument(clientTrx, editTrx);

        super.executeTrx(segment, "NaturalDocVO", praseDocument.getDocument(),"SupplementalLumpSum", editTrx.getStatus(), eventType, editTrx.getEffectiveDate().getFormattedDate(), productKey, true);
    }

    /**
     * Common initialization for CaseTracking transactions
     * @param distributionCode
     * @param isSuppContract
     * @return
     */
     private GroupSetup initializeGroupSetup()
     {
         GroupSetup groupSetup = new GroupSetup();
//         groupSetup.setGroupTypeCT("INDIVIDUAL");    // individuals are now set to null
//         groupSetup.setGroupKey(segment.getContractNumber());
         groupSetup.setGrossNetStatusCT("Gross");

         return groupSetup;
     }

     /**
      * Build a default EDITTrx
      * @param transactionTypeCT
      * @param effectiveDate
      * @param taxYear
      * @param operator
      * @param amount
      * @return
      */
     public EDITTrx buildDefaultEDITTrx(String transactionTypeCT, EDITDate effectiveDate, int taxYear, String operator, String newContractNumber)
     {
        EDITTrx editTrx = TransactionProcessor.buildDefaultEDITTrx(transactionTypeCT, effectiveDate, taxYear, operator);

        editTrx.setTrxAmount(new EDITBigDecimal("0"));
        editTrx.setNewPolicyNumber(newContractNumber);

        int sequenceNumber = 1; 
        super.setSequenceNumber(editTrx, segment, sequenceNumber);

        return editTrx;
    }
}
