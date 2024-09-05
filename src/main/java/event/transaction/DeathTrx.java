/*
 * User: cgleason
 * Date: July 26, 2005
 * Time: 8:45:14 AM
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package event.transaction;

import codetable.TrxProcessDocforPerformance;

import contract.ContractClient;
import contract.Segment;

import edit.common.CodeTableWrapper;
import edit.common.EDITBigDecimal;
import edit.common.EDITDate;
import edit.common.exceptions.EDITEventException;
import edit.common.vo.ClientSetupVO;
import edit.common.vo.ContractSetupVO;
import edit.common.vo.EDITTrxVO;

import edit.portal.exceptions.PortalEditingException;

import event.ClientSetup;
import event.ContractSetup;
import event.EDITTrx;
import event.GroupSetup;

import event.financial.client.trx.ClientTrx;
import event.financial.contract.trx.ContractEvent;

public class DeathTrx  extends TransactionProcessor
{
    Segment segment;

    public DeathTrx()
    {

    }

    public DeathTrx(Segment segment)
    {
        this.segment = segment;
    }
    /**
     * @param operator
     * @param editTrx
     * @param segment
     *
     * @throws EDITEventException
     */
    public void checkForExecutionOfDeathTransaction(String operator, EDITTrx editTrx, Segment segment) throws EDITEventException
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

            executeDeathTransaction(clientTrx, eventType, productKey.longValue(), editTrx, segment);
        }
    }

    /**
     * Execute the Death Transaction through a script
     *
     * @param clientTrx
     * @param eventType
     * @param productKey
     * @param editTrx
     *
     * @throws EDITEventException
     */
    private void executeDeathTransaction(ClientTrx clientTrx, String eventType, long productKey, EDITTrx editTrx, Segment segment) throws EDITEventException
    {
        TrxProcessDocforPerformance praseDocument = super.buildDocument(clientTrx, editTrx);

        super.executeTrx(segment, "NaturalDocVO", praseDocument.getDocument(), "Death", editTrx.getStatus(), eventType, editTrx.getEffectiveDate().getFormattedDate(), productKey, true);
    }

    /**
     * Create the death trx for a contract client
     *
     * @param operator
     * @param effectiveDate
     */
    public EDITTrx createDeathEDITTrx(String operator, EDITDate effectiveDate, ContractClient contractClient, String conditionCT, EDITDate dateOfDeath)
    {
        GroupSetup groupSetup = initializeGroupSetup();

        ContractSetup contractSetup = TransactionProcessor.buildDefaultContractSetup(segment);
        contractSetup.setDeathStatusCT("DeathLump");
        contractSetup.setConditionCT(conditionCT);
        contractSetup.setDateOfDeath(dateOfDeath);

        groupSetup.addContractSetup(contractSetup);

        ClientSetup clientSetup = new ClientSetup();
        clientSetup.setContractSetup(contractSetup);
        clientSetup.setContractClient(contractClient);
        clientSetup.setClientRole(contractClient.getClientRole());
        contractSetup.addClientSetup(clientSetup);

        int taxYear = effectiveDate.getYear();
        int sequenceNumber = 1;

        EDITTrx editTrx = buildEDITTrx("DE", effectiveDate, taxYear, operator);

        clientSetup.addEDITTrx(editTrx);

        super.checkForEvents(editTrx, segment);
        
        return editTrx;
    }

    /**
     * Create the death pending trx for a contract client
     *
     * @param operator
     * @param effectiveDate
     */
    public EDITTrx createDeathPendingEDITTrx(String operator, EDITDate effectiveDate, ContractClient contractClient, String conditionCT)
    {
        GroupSetup groupSetup = initializeGroupSetup();

        ContractSetup contractSetup = TransactionProcessor.buildDefaultContractSetup(segment);
        contractSetup.setDeathStatusCT("DeathPending");
        contractSetup.setConditionCT(conditionCT);

        groupSetup.addContractSetup(contractSetup);

        ClientSetup clientSetup = new ClientSetup();
        clientSetup.setContractSetup(contractSetup);
        clientSetup.setContractClient(contractClient);
        clientSetup.setClientRole(contractClient.getClientRole());
        contractSetup.addClientSetup(clientSetup);

        int taxYear = effectiveDate.getYear();

        EDITTrx editTrx = buildEDITTrx("DP", effectiveDate, taxYear, operator);

        clientSetup.addEDITTrx(editTrx);

        super.checkForEvents(editTrx, segment);

        return editTrx;
    }


    /**
      * Build a default EDITTrx
      * @param transactionTypeCT
      * @param effectiveDate
      * @param taxYear
      * @param operator
      * @return
      */
    public EDITTrx buildEDITTrx(String transactionTypeCT, EDITDate effectiveDate, int taxYear, String operator)
    {
        EDITTrx editTrx = TransactionProcessor.buildDefaultEDITTrx(transactionTypeCT, effectiveDate, taxYear, operator);

        int sequenceNumber = 1;
        super.setSequenceNumber(editTrx, segment, sequenceNumber);

        return editTrx;
    }


    /**
       * Common initialization for CaseTracking transactions
       * @param transactionType
       * @return
       */
    private GroupSetup initializeGroupSetup()
    {
        CodeTableWrapper codeTableWrapper = CodeTableWrapper.getSingleton();
        String distributionCode = codeTableWrapper.getCodeByCodeTableNameAndCodeDesc("DISTRIBUTIONCODE", "Normal Distribution");

        GroupSetup groupSetup = new GroupSetup();
//        groupSetup.setGroupTypeCT("INDIVIDUAL");          // individuals are now set to null
//        groupSetup.setGroupKey(segment.getContractNumber());
        groupSetup.setGrossNetStatusCT("Gross");
        groupSetup.setDistributionCodeCT(distributionCode);

        return groupSetup;
    }



    /**
     * Until the system does undo/redo using hibernate the death trx must process using the existing code for und/redo.
     *
     * @param editTrx
     *
     * @throws Exception
     * @throws edit.portal.exceptions.PortalEditingException
     *
     */
    public static void checkForExecutionOfDeathTrx(EDITTrx editTrx) throws Exception, PortalEditingException
    {
        long contractSetupPK = editTrx.getClientSetup().getContractSetup().getPK();
        ContractSetupVO contractSetupVO = ContractSetup.findByPK(contractSetupPK);

        long clientSetupPK = editTrx.getClientSetup().getPK();
        ClientSetupVO clientSetupVO = ClientSetup.findByPK(clientSetupPK);

        EDITTrxVO editTrxVO = EDITTrx.findByPK_UsingCRUD(editTrx.getEDITTrxPK().longValue());
        
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
