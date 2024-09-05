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

import contract.Bucket;
import contract.ContractClient;
import contract.Investment;
import contract.InvestmentAllocation;
import contract.Segment;

import contract.dm.dao.InvestmentDAO;

import edit.common.EDITBigDecimal;
import edit.common.EDITDate;
import edit.common.exceptions.EDITCRUDException;
import edit.common.exceptions.EDITEventException;
import edit.common.vo.*;

import edit.services.db.CRUD;
import edit.services.db.CRUDFactory;
import edit.services.db.ConnectionFactory;
import edit.services.db.hibernate.SessionHelper;

import event.*;

import event.financial.client.trx.ClientTrx;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;


public class ValueTransferTrx  extends TransactionProcessor
{
    Segment segment;
    ContractClient contractClient;

    public ValueTransferTrx()
    {

    }

    public ValueTransferTrx(Segment segment)
    {
        this.segment = segment;
        this.contractClient = segment.getOwnerContractClient();
    }

    /**
     * ValueTranfer creation when all processing initiated from case tracking StretchIra process.
     * @param segment
     * @param editTrx
     * @param bucketHistories
     * @return
     */
    public EDITTrx createValueTransferTrx(EDITTrx editTrx, List buckets, List bucketHistories, EDITBigDecimal trxAmount)
    {
        Set investmentAllocationOverrides = createAllocationOverride(buckets, bucketHistories);

        GroupSetup groupSetup = initializeGroupSetup(trxAmount);

        ContractSetup contractSetup = initializeContractSetup(trxAmount);

        for (Iterator iterator = investmentAllocationOverrides.iterator(); iterator.hasNext();)
        {
            InvestmentAllocationOverride investmentAllocationOverride = (InvestmentAllocationOverride) iterator.next();

            contractSetup.addInvestmentAllocationOverride(investmentAllocationOverride);
        }

        ClientSetup clientSetup = new ClientSetup();
        clientSetup.setContractClient(contractClient);
        clientSetup.setClientRole(contractClient.getClientRole());

        contractSetup.addClientSetup(clientSetup);
        groupSetup.addContractSetup(contractSetup);

        EDITTrx newEditTrx = buildDefaultEDITTrx("VT", editTrx.getEffectiveDate(), editTrx.getOperator());
        newEditTrx.setTrxAmount(trxAmount);
        clientSetup.addEDITTrx(newEditTrx);

        super.checkForEvents(editTrx, segment);

        return newEditTrx;
     }

   /**
    *
    * @param buckets
    * @param bucketHistories
    * @return
    */
    public Set createAllocationOverride(List buckets, List bucketHistories)
    {
        int index = 0;
        Set investmentAllocationOverrides = new HashSet();

        for (Iterator iterator = buckets.iterator(); iterator.hasNext();)
        {
            Bucket bucket = (Bucket) iterator.next();
            Investment investment = bucket.getInvestment();

            Long oldFilteredFundPK = investment.getFilteredFundFK();

            Set newInvestments = segment.getInvestments();

            Investment newInvestment = null;

            for (Iterator iterator1 = newInvestments.iterator(); iterator1.hasNext();)
            {
                newInvestment = (Investment) iterator1.next();

                Long newFilteredFundPK = newInvestment.getFilteredFundFK();

                if (newFilteredFundPK == oldFilteredFundPK)
                {
                    break;
                }
            }

            BucketHistory bucketHistory = (BucketHistory) bucketHistories.get(index);

            InvestmentAllocation investmentAllocation = new InvestmentAllocation();
            investmentAllocation.setOverrideStatus("O");
            investmentAllocation.setDollars(bucketHistory.getDollars());
            investmentAllocation.setAllocationPercent(new EDITBigDecimal("0"));

            InvestmentAllocationOverride investmentAllocationOvrd = new InvestmentAllocationOverride();
            investmentAllocationOvrd.setInvestment(newInvestment);
            investmentAllocationOvrd.setInvestmentAllocation(investmentAllocation);
            investmentAllocationOvrd.setToFromStatus("T");
            investmentAllocation.addInvestmentAllocationOverride(investmentAllocationOvrd);

            newInvestment.addInvestmentAllocation(investmentAllocation);
            newInvestment.addInvestmentAllocationOverride(investmentAllocationOvrd);

            //save investment, investmentAllocation
            SessionHelper.saveOrUpdate(newInvestment, SessionHelper.EDITSOLUTIONS);

            newInvestment = (Investment)SessionHelper.get(Investment.class, newInvestment.getInvestmentPK(), SessionHelper.EDITSOLUTIONS);

            investmentAllocationOverrides.add(investmentAllocationOvrd);

            index++;
        }

        return investmentAllocationOverrides;
    }

     /**
      * When the effective date of the trx is less than the current date the transaction will execute immediately
      * @param editTrx
      * @throws edit.common.exceptions.EDITEventException
      */
     public void checkForExecutionOfValueTransfer(EDITTrx editTrx)  throws EDITEventException
     {
        this.editTrx = editTrx;

        //Determine execution
        if (super.isBackdated())
        {
            //Create clientTrx object
            ClientTrx clientTrx = new ClientTrx(editTrx.getAsVO(), editTrx.getOperator());
            clientTrx.setExecutionMode(ClientTrx.REALTIME_MODE);

            String eventType = segment.setEventTypeForDriverScript();
            Long productKey = segment.getProductStructureFK();

            executeValueTransfer(clientTrx, eventType, productKey.longValue(), editTrx);
        }
    }

    /**
     * Build the document model, then execute
     * @param clientTrx
     * @param eventType
     * @param productKey
     * @param editTrx
     * @throws edit.common.exceptions.EDITEventException
     */
    private void executeValueTransfer(ClientTrx clientTrx, String eventType, long productKey, EDITTrx editTrx)  throws EDITEventException
    {
        TrxProcessDocforPerformance praseDocument = super.buildDocument(clientTrx, editTrx);

        super.executeTrx(segment, "NaturalDocVO", praseDocument.getDocument(), "ValueTransfer", editTrx.getStatus(), eventType, editTrx.getEffectiveDate().getFormattedDate(), productKey, true);
    }

    /**
     * Common initialization for CaseTracking transactions
     * @param distributionCode
     * @param isSuppContract
     * @return
     */
     private GroupSetup initializeGroupSetup(EDITBigDecimal trxAmount)
     {
         GroupSetup groupSetup = new GroupSetup();
//         groupSetup.setGroupTypeCT("INDIVIDUAL"); // individuals are now set to null
//         groupSetup.setGroupKey(segment.getContractNumber());
         groupSetup.setGrossNetStatusCT("Gross");
         groupSetup.setGroupAmount(trxAmount);

         return groupSetup;
     }

     /**
      * Build a default ContractSetup
      * @param amount
      * @return
      */
     private ContractSetup initializeContractSetup(EDITBigDecimal trxAmount)
     {
         ContractSetup contractSetup = TransactionProcessor.buildDefaultContractSetup(segment);
         contractSetup.setPolicyAmount(trxAmount);

         return contractSetup;
     }

     /**
      * Build a default EDITTrx
      * @param transactionTypeCT
      * @param editTrx
      * @return
      */
     public EDITTrx buildDefaultEDITTrx(String transactionTypeCT, EDITDate effectiveDate, String operator)
     {
         int taxYear = effectiveDate.getYear();

         EDITTrx editTrx = TransactionProcessor.buildDefaultEDITTrx(transactionTypeCT, effectiveDate, taxYear, operator);

         editTrx.setTrxAmount(new EDITBigDecimal("0"));

         int sequenceNumber = 1;

         super.setSequenceNumber(editTrx, segment, sequenceNumber);

         return editTrx;
    }

    /**
     * Batch VT trx processing must use crud and not hibernate
     * @param editTrxVO
     * @param buckets
     * @param bucketHistories
     * @param trxAmount
     * @return
     */
    public GroupSetupVO createValueTransferTrx(EDITTrxVO editTrxVO, List buckets, EDITTrxHistoryVO editTrxHistoryVO) throws EDITEventException
    {
        EDITBigDecimal trxAmount = new EDITBigDecimal(editTrxHistoryVO.getFinancialHistoryVO(0).getGrossAmount());
        InvestmentAllocationOverrideVO[]  investmentAllocationOverrideVOs = createAllocationOverrideVOs(buckets, editTrxHistoryVO);

        GroupSetup groupSetup = initializeGroupSetup(trxAmount);
        GroupSetupVO groupSetupVO = (GroupSetupVO)groupSetup.getAsVO();

        ContractSetup contractSetup = initializeContractSetup(trxAmount);
        ContractSetupVO  contractSetupVO = (ContractSetupVO)contractSetup.getVO();
        contractSetupVO.setInvestmentAllocationOverrideVO(investmentAllocationOverrideVOs);
        contractSetupVO.setSegmentFK(segment.getPK());

        ClientSetupVO clientSetupVO = new ClientSetupVO();
        clientSetupVO.setContractClientFK(contractClient.getPK());
        clientSetupVO.setClientRoleFK(contractClient.getClientRole().getPK());

        EDITTrx newEditTrx = buildDefaultEDITTrx("VT", new EDITDate(editTrxVO.getEffectiveDate()), editTrxVO.getOperator());
        newEditTrx.setTrxAmount(trxAmount);
        EDITTrxVO newEditTrxVO = newEditTrx.getAsVO();
        clientSetupVO.addEDITTrxVO(newEditTrxVO);
        contractSetupVO.addClientSetupVO(clientSetupVO);
        groupSetupVO.addContractSetupVO(contractSetupVO);

        return groupSetupVO;
     }

  /**
    *
    * @param buckets
    * @param bucketHistories
    * @return
    */
    public InvestmentAllocationOverrideVO[] createAllocationOverrideVOs(List buckets, EDITTrxHistoryVO editTrxHistoryVO) throws EDITEventException
    {
        int index = 0;
        List investmentAllocationOverrideVOs = new ArrayList();
        CRUD crud = CRUDFactory.getSingleton().getCRUD(ConnectionFactory.EDITSOLUTIONS_POOL);

        BucketHistoryVO[] bucketHistoryVOs = editTrxHistoryVO.getBucketHistoryVO();

        for (Iterator iterator = buckets.iterator(); iterator.hasNext();)
        {
            BucketVO bucketVO = (BucketVO) iterator.next();

//            BucketHistoryVO[] bucketHistoryVO = bucketVO.getBucketHistoryVO();

            InvestmentVO[] newInvestments = new InvestmentDAO().findBySegmentPK(segment.getSegmentPK().longValue(), false, null);

            InvestmentVO oldInvestment = new InvestmentDAO().findByInvestmentPK(bucketVO.getInvestmentFK(), false, null)[0];

            long oldFilteredFundPK = oldInvestment.getFilteredFundFK();

            InvestmentVO newInvestment = null;

            for (int i = 0; i < newInvestments.length; i++)
            {
                newInvestment = newInvestments[i];

                long newFilteredFundPK = newInvestment.getFilteredFundFK();

                if (newFilteredFundPK == oldFilteredFundPK)
                {
                    break;
                }
            }

            BucketHistoryVO bucketHistoryVO = null;
            for (int i = 0; i < bucketHistoryVOs.length; i++)
            {
                if (bucketVO.getBucketPK() == bucketHistoryVOs[i].getBucketFK())
                {
                    bucketHistoryVO = bucketHistoryVOs[i];
                    break;
                }
            }

            InvestmentAllocationVO investmentAllocationVO = new InvestmentAllocationVO();
            investmentAllocationVO.setOverrideStatus("O");
            investmentAllocationVO.setDollars(bucketHistoryVO.getDollars());
            investmentAllocationVO.setInvestmentFK(newInvestment.getInvestmentPK());

            InvestmentAllocationOverrideVO investmentAllocationOvrdVO = new InvestmentAllocationOverrideVO();
            investmentAllocationOvrdVO.setToFromStatus("T");
            investmentAllocationOvrdVO.setInvestmentFK(newInvestment.getInvestmentPK());

//            investmentVO.addInvestmentAllocationVO(investmentAllocationVO);
//            investmentVO.addInvestmentAllocationOverrideVO(investmentAllocationOvrdVO);

            try
            {
                long investmentAllocationPK = crud.createOrUpdateVOInDB(investmentAllocationVO);
//                investmentVO.addInvestmentAllocationVO(investmentAllocationVO);
                investmentAllocationOvrdVO.setInvestmentAllocationFK(investmentAllocationPK);
            }
            catch (Exception e)
            {
                EDITEventException editEventException = new EDITEventException(e.getMessage());

                System.out.println(e);

                e.printStackTrace();

                try
                {
                    crud.rollbackTransaction();
                }
                catch (EDITCRUDException e1)
                {
                    System.out.println(e1);

                    e1.printStackTrace();

                    EDITEventException editEventException2 = new EDITEventException(e.getMessage());

                    editEventException = editEventException2;
                }

                throw editEventException;
            }
            finally
            {
                if (crud != null)
                    crud.close();
            }

            investmentAllocationOverrideVOs.add(investmentAllocationOvrdVO);

            index++;
        }

        return (InvestmentAllocationOverrideVO[])investmentAllocationOverrideVOs.toArray(new InvestmentAllocationOverrideVO[investmentAllocationOverrideVOs.size()]);
    }
}
