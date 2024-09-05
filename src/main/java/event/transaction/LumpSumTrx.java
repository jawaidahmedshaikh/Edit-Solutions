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

import contract.ContractClient;
import contract.ContractClientAllocation;
import contract.Segment;
import contract.Withholding;

import edit.common.CodeTableWrapper;
import edit.common.EDITBigDecimal;
import edit.common.EDITDate;
import edit.common.exceptions.EDITEventException;
import edit.common.vo.ClientSetupVO;
import edit.common.vo.ContractSetupVO;
import edit.common.vo.EDITTrxVO;

import edit.portal.exceptions.PortalEditingException;

import edit.services.db.hibernate.SessionHelper;

import event.Charge;
import event.ClientSetup;
import event.ContractClientAllocationOvrd;
import event.ContractSetup;
import event.EDITTrx;
import event.GroupSetup;
import event.WithholdingOverride;

import event.financial.client.trx.ClientTrx;
import event.financial.contract.trx.ContractEvent;


public class LumpSumTrx  extends TransactionProcessor
{
    Segment segment;
    ContractClient contractClient;

    public LumpSumTrx()
    {

    }

    public LumpSumTrx(Segment segment, ContractClient contractClient)
    {
        this.segment = segment;
        this.contractClient = contractClient;
    }

    /**
      * For the contractClient, create a LumpSum trx.  This trx needs to have a ContractClientAllocationOvrd
      * attached to it with the allocation percent of the beneficiary selected.  There also can be withholding,
      * which must be attached to the contractClient.  Format the required pieces of a transaction, GroupSetup,
      * ContractSetup, ClientSetup and EDITTrx, then save it.  The last step is to check for execution of the trx
      * just created.  If it is backdated it must execute real time.
      * @param operator
      * @param effectiveDate
      * @param taxYear
      * @param amount
      * @param withholding
      * @param allocationPct
      * @param isSuppContract Is this Lump Sum Trx going to be created by processing Supplemental Contract
      */
     public EDITTrx createLumpSumTrx(String operator, EDITDate effectiveDate, int taxYear,
                                     EDITBigDecimal amountOverride, EDITBigDecimal interestOverride,
                                     String zeroInterestIndicator, Withholding withholding, Charge[] charges,
                                     EDITBigDecimal allocationPct, boolean isSuppContract, int sequenceNumber)
     {
        //format the contractClientAllocation and save it along with Withholding, for the ContractClient
         if (withholding != null)
         {
             contractClient.addWithholding(withholding);
         }

        ContractClientAllocationOvrd contractClientAllocationOvrd = createAllocationOverride(allocationPct);

        GroupSetup groupSetup = initializeGroupSetup(isSuppContract);

        ContractSetup contractSetup = initializeContractSetup(amountOverride);

        ClientSetup clientSetup = new ClientSetup();
//        clientSetup.setContractClient(contractClient);
         contractClient.addClientSetup(clientSetup);

         contractSetup.addClientSetup(clientSetup);

        clientSetup.setClientRole(contractClient.getClientRole());

        clientSetup.addContractClientAllocationOvrd(contractClientAllocationOvrd);

        if (charges != null)
        {
            for (int i = 0; i < charges.length; i++)
            {
                groupSetup.addCharge(charges[i]);
            }
        }

        //checkFor withholding
        if (withholding != null)
        {
            WithholdingOverride withholdingOverride = new WithholdingOverride();
//             withholdingOverride.setWithholding(withholding);
            withholding.addWithholdingOverride(withholdingOverride);
            //hibernate mapping doesn't support the following relationship
//            clientSetup.addWithholdingOverride(withholdingOverride);
            withholdingOverride.setClientSetup(clientSetup);
            withholdingOverride.setClientSetupFK(clientSetup.getClientSetupPK());
        }

//        contractSetup.addClientSetup(clientSetup);
        groupSetup.addContractSetup(contractSetup);

        EDITTrx editTrx = buildDefaultEDITTrx("LS", effectiveDate, taxYear, operator, sequenceNumber);
         if (amountOverride.isGT("0"))
         {
             editTrx.setTrxAmount(amountOverride);
         }
         if (interestOverride.isGT("0"))
         {
             editTrx.setInterestProceedsOverride(interestOverride);
         }

        editTrx.setZeroInterestIndicator(zeroInterestIndicator);

         clientSetup.addEDITTrx(editTrx);

        super.checkForEvents(editTrx, segment);

        return editTrx;
     }

    /**
     * The lumpSum trx will always have this contractClientAllocation attached as an override
     * to the transaction.
     * @param allocationPct
     * @return
     */
    public ContractClientAllocationOvrd createAllocationOverride(EDITBigDecimal allocationPct)
    {
        ContractClientAllocation contractClientAllocation  = contractClient.getContractClientAllocation();
//        String splitEqualInd = Util.initString(contractClient.getContractClientAllocation().getSplitEqual(), "N");
//        ContractClientAllocation contractClientAllocation = new ContractClientAllocation();
//        contractClientAllocation.setOverrideStatus("O");
//        contractClientAllocation.setAllocationPercent(allocationPct);
//        contractClientAllocation.setSplitEqual(splitEqualInd);

//        contractClient.addContractClientAllocation(contractClientAllocation);

        ContractClientAllocationOvrd contractClientAllocationOvrd = new ContractClientAllocationOvrd();
        contractClientAllocation.addContractClientAllocationOverride(contractClientAllocationOvrd);

        //save contractClient, allocation and possibly withholding
        SessionHelper.saveOrUpdate(contractClient, SessionHelper.EDITSOLUTIONS);

        return contractClientAllocationOvrd;
    }

     /**
      * When the effective date of the trx is less than the current date the transaction will execute immediately
      * @param operator
      * @param editTrx
      * @param segment
      * @throws EDITEventException
      */
     public void checkForExecutionOfLumpSum(String operator, EDITTrx editTrx)  throws EDITEventException
     {
        this.editTrx = editTrx;
        //Determine execution
        if (super.isBackdated())
        {
            //Create clientTrx object
            ClientTrx clientTrx = new ClientTrx(editTrx.getAsVO(), operator);
            clientTrx.setExecutionMode(ClientTrx.REALTIME_MODE);

            String eventType = segment.setEventTypeForDriverScript();
            Long productKey = segment.getProductStructureFK();

            executeLumpSumTransaction(clientTrx, eventType, productKey.longValue(), editTrx);
        }
    }

    /**
     * Build the document model, then execute
     * @param clientTrx
     * @param eventType
     * @param productKey
     * @param editTrx
     * @throws EDITEventException
     */
    private void executeLumpSumTransaction(ClientTrx clientTrx, String eventType, long productKey, EDITTrx editTrx)  throws EDITEventException
    {
        TrxProcessDocforPerformance praseDocument = super.buildDocument(clientTrx, editTrx);

        super.executeTrx(segment, "NaturalDocVO", praseDocument.getDocument(),"LumpSum", editTrx.getStatus(), eventType, editTrx.getEffectiveDate().getFormattedDate(), productKey, true);
    }

    /**
     * Common initialization for CaseTracking transactions
     * @param isSuppContract
     * @return
     */
    private GroupSetup initializeGroupSetup(boolean isSuppContract)
    {
        GroupSetup groupSetup = new GroupSetup();
//        groupSetup.setGroupTypeCT("INDIVIDUAL");    // individuals are now set to null
//        groupSetup.setGroupKey(segment.getContractNumber());
        groupSetup.setGrossNetStatusCT("Gross");

        String distributionCode = CodeTableWrapper.getSingleton().getCodeByCodeTableNameAndCodeDesc("DISTRIBUTIONCODE", "Death");
        groupSetup.setDistributionCodeCT(distributionCode);

        if (isSuppContract)
        {
            groupSetup.setWithdrawalTypeCT(GroupSetup.WITHDRAWALTYPECT_SUPPCONTRACT);
        }

        return groupSetup;
    }

    /**
     * Build a default ContractSetup
     * @param amount
     * @return
     */
    private ContractSetup initializeContractSetup(EDITBigDecimal amount)
    {
//        ContractSetup contractSetup = new ContractSetup();
//        contractSetup.setSegment(segment);
//        contractSetup.setAmountReceived(new EDITBigDecimal("0"));
//        contractSetup.setCostBasis(new EDITBigDecimal("0"));
//        contractSetup.setPolicyAmount(amount);

        ContractSetup contractSetup = TransactionProcessor.buildDefaultContractSetup(segment);
        contractSetup.setPolicyAmount(amount);


        return contractSetup;
    }

     /**
      * Build a default EDITTrx
      * @param transactionTypeCT
      * @param effectiveDate
      * @param taxYear
      * @param operator
      * @param amount
      * @param interestOverride
      * @return
      */
    public EDITTrx buildDefaultEDITTrx(String transactionTypeCT, EDITDate effectiveDate, int taxYear,
                                       String operator, int sequenceNumber)
    {
        EDITTrx editTrx = TransactionProcessor.buildDefaultEDITTrx(transactionTypeCT, effectiveDate, taxYear, operator);

        super.setSequenceNumber(editTrx, segment, sequenceNumber);

        return editTrx;
    }

    /**
     * Until the system does undo/redo using hibernate the death trx must process using the existing code for und/redo.
     * @param editTrx
     * @throws Exception
     * @throws edit.portal.exceptions.PortalEditingException
     */
    public void checkForExecutionOfLumpSumTrx(EDITTrx editTrx) throws Exception, PortalEditingException
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
          System.out.println(e);

            e.printStackTrace();
            throw(e);
        }
    }
}
