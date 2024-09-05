/*
 * User: cgleason
 * Date: Oct 24, 2003
 * Time: 2:47:23 PM
 *
 * (c) 2000-2008 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package event.financial.client.strategy;

import edit.common.*;
import edit.common.exceptions.*;
import edit.common.vo.*;
import edit.services.db.CRUD;
import edit.services.db.CRUDFactory;
import edit.services.db.ConnectionFactory;
import event.financial.group.strategy.SaveGroup;
import event.financial.group.trx.*;
import event.financial.contract.trx.ContractEvent;
import event.financial.client.trx.ClientTrx;
import event.transaction.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Hashtable;
import java.util.Enumeration;
import java.math.BigDecimal;

import fission.utility.Util;
import contract.*;
import engine.UnitValues;



public class TrxGeneration
{
    public TrxGeneration()
    {

    }

    protected ClientStrategy[] generateSpawnedTransaction(String spawnedTransaction, EDITTrxVO editTrxVO,
                                                          GroupSetupVO groupSetupVO, SegmentVO baseSegmentVO,
                                                          List buckets, CRUD crud, ClientStrategy[] clientStrategy,
                                                          List clientStrategyArray) throws Exception
    {
        if (spawnedTransaction.equalsIgnoreCase("PR"))
        {
            boolean generateTrx = determineIfRebalTrxNeeded(baseSegmentVO.getSegmentPK(), crud);

            if (generateTrx)
            {
                List voInclusionList = new ArrayList();
                voInclusionList.add(InvestmentVO.class);
                voInclusionList.add(InvestmentAllocationVO.class);
                voInclusionList.add(BucketVO.class);

                crud.retrieveVOFromDBRecursively(baseSegmentVO, voInclusionList, true);

                boolean modifyEffDate = true;

                EDITTrxVO newEditTrxVO = generateRebalanceTrx(editTrxVO, groupSetupVO, baseSegmentVO.getProductStructureFK(), crud, baseSegmentVO, modifyEffDate);

                ClientTrx naturalTrx = new ClientTrx(newEditTrxVO, editTrxVO.getOperator());
                clientStrategyArray = new ArrayList();
                clientStrategyArray.add(new Natural(naturalTrx, "natural"));
                clientStrategy = (ClientStrategy[]) clientStrategyArray.toArray(new ClientStrategy[clientStrategyArray.size()]);
            }
        }
        else if (spawnedTransaction.equalsIgnoreCase("RN"))
        {
            boolean modifyLookup = true;
            EDITTrxVO[] newEditTrxVOs = generateRenewalTrx(editTrxVO, groupSetupVO, baseSegmentVO, buckets, crud, modifyLookup);

            clientStrategyArray = new ArrayList();

            for (int i = 0; i < newEditTrxVOs.length; i++)
            {
                ClientTrx naturalTrx = new ClientTrx(newEditTrxVOs[i], editTrxVO.getOperator());
                clientStrategyArray.add(new Natural(naturalTrx, "natural"));
            }

            clientStrategy = (ClientStrategy[]) clientStrategyArray.toArray(new ClientStrategy[clientStrategyArray.size()]);
        }

        return clientStrategy;
    }

    /**
     * For redo buckets are contained in the InvestmentVO
     * @param editTrxVO
     * @param groupSetupVO
     * @param baseSegmentVO
     * @param crud
     * @throws EDITEventException
     * @throws EDITDeleteException 
     */
    protected void generateRenewalTrx(EDITTrxVO editTrxVO, GroupSetupVO groupSetupVO, SegmentVO baseSegmentVO, List buckets, CRUD crud) throws EDITEventException, EDITDeleteException
    {
        InvestmentVO[] investmentVO = baseSegmentVO.getInvestmentVO();
        engine.business.Lookup engineLookup = new engine.component.LookupComponent();

        if (investmentVO != null)
        {
            for (int i = 0; i < investmentVO.length; i++)
            {
                long filteredFundId = investmentVO[i].getFilteredFundFK();
                FilteredFundVO[] filteredFundVO = engineLookup.findFilteredFundbyPKAndFundType(filteredFundId, false, null);
                if (filteredFundVO != null)
                {
                    createOverrides(investmentVO[i], crud);
                    String renewalDate = getRenewalDate(investmentVO[i].getBucketVO(), buckets);
                    createEvent(editTrxVO, groupSetupVO, investmentVO[i], renewalDate, crud);
                }
            }
        }
    }

    protected EDITTrxVO[] generateRenewalTrx(EDITTrxVO editTrxVO, GroupSetupVO groupSetupVO, SegmentVO baseSegmentVO, List buckets, CRUD crud, boolean modifyLookup) throws Exception
    {
        List newEditTrxVOs = new ArrayList();

        InvestmentVO[] investmentVO = baseSegmentVO.getInvestmentVO();

        engine.business.Lookup engineLookup = new engine.component.LookupComponent();

        if (investmentVO != null)
        {
            for (int i = 0; i < investmentVO.length; i++)
            {
                long filteredFundId = investmentVO[i].getFilteredFundFK();
                FilteredFundVO[] filteredFundVO = null;
                if (modifyLookup)
                {
                    filteredFundVO = engineLookup.findFilteredFundByPK(filteredFundId, false, null);
                }
                else
                {
                    filteredFundVO = engineLookup.findFilteredFundbyPKAndFundType(filteredFundId, false, null);
                }

                if (filteredFundVO != null)
                {
                    boolean renewal = setBuckets(investmentVO[i], buckets);
                    if (renewal)
                    {
                        createOverrides(investmentVO[i], crud);
                        String renewalDate = getRenewalDate(investmentVO[i].getBucketVO(), buckets);
                        EDITTrxVO newEditTrxVO = createEvent(editTrxVO, groupSetupVO, investmentVO[i], renewalDate, crud);
                        newEditTrxVOs.add(newEditTrxVO);
                    }
                }
            }
        }

        return (EDITTrxVO[]) newEditTrxVOs.toArray(new EDITTrxVO[newEditTrxVOs.size()]);
    }

    private String getRenewalDate(BucketVO[] bucketVOs, List buckets)
    {
        String renewalDate = "";
        for (int i = 0; i < bucketVOs.length; i++)
        {
            for (int j = 0; j < buckets.size(); j++)
            {
                if (((BucketVO) buckets.get(j)).getBucketPK() == bucketVOs[i].getBucketPK())
                {
                    renewalDate = bucketVOs[i].getRenewalDate();
                    j = buckets.size();
                    i = bucketVOs.length;
                }
            }
        }

        return renewalDate;
    }

     /**
      * Buckets can be existing and just created join the two into investmentsVO
      * @param investmentVO
      * @param buckets
      */
     private boolean setBuckets(InvestmentVO investmentVO, List buckets)
     {
         boolean renewal = false;
         boolean bucketFound = false;
         for (int i = 0; i < buckets.size(); i++)
         {
             bucketFound = false;
             BucketVO bucketVO = (BucketVO)buckets.get(i);
             BucketVO[] investBucketVOs = (BucketVO[])investmentVO.getBucketVO();
             if (investBucketVOs == null || investBucketVOs.length == 0)
             {
                 if (bucketVO.getInvestmentFK() == investmentVO.getInvestmentPK())
                 {
                     if (bucketVO.getRenewalDate() != null)
                     {
                         renewal = true;
                     }

                     investmentVO.addBucketVO(bucketVO);
                     bucketFound = true;
                 }
             }
             else
             {
                 for (int j = 0; j < investBucketVOs.length; j++)
                 {
                     if (bucketVO.getBucketPK() == investBucketVOs[j].getBucketPK())
                     {
                         bucketFound = true;
                         if (bucketVO.getRenewalDate() != null)
                         {
                             renewal = true;
                         }

                         investmentVO.removeBucketVO(j);
                         investmentVO.addBucketVO(bucketVO);
                     }
                 }
             }

             if (!bucketFound)
             {
                 if (bucketVO.getInvestmentFK() == investmentVO.getInvestmentPK())
                 {
                     if (bucketVO.getRenewalDate() != null)
                     {
                         renewal = true;
                     }

                     investmentVO.addBucketVO(bucketVO);
                 }
             }
         }

         return renewal;
     }
   
    private void createOverrides(InvestmentVO investmentVO, CRUD crud) throws EDITEventException
    {
        InvestmentAllocationVO investmentAllocationVO = new InvestmentAllocationVO();
        investmentAllocationVO.setInvestmentAllocationPK(0);
        investmentAllocationVO.setInvestmentFK(investmentVO.getInvestmentPK());
        // investmentAllocationVO.setAllocationPercent(1);
        // commented above line(s) for double to BigDecimal conversion
        // sprasad 9/30/2004
        investmentAllocationVO.setAllocationPercent( new EDITBigDecimal("1").getBigDecimal() );
        investmentAllocationVO.setOverrideStatus("O");

//        BucketVO bucketVO = investmentVO.getBucketVO(0);
//        BucketAllocationVO bucketAllocationVO = new BucketAllocationVO();
//        bucketAllocationVO.setBucketAllocationPK(0);
//        bucketAllocationVO.setBucketFK(bucketVO.getBucketPK());
//        // bucketAllocationVO.setAllocationPercent(1);
//        // commented above line(s) for double to BigDecimal conversion
//        // sprasad 9/30/2004
//        bucketAllocationVO.setAllocationPercent( new EDITBigDecimal("1").getBigDecimal() );

        try
        {
            crud.createOrUpdateVOInDB(investmentAllocationVO);
            investmentVO.addInvestmentAllocationVO(investmentAllocationVO);

//            crud.createOrUpdateVOInDB(bucketAllocationVO);
//            bucketVO.addBucketAllocationVO(bucketAllocationVO);
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
    }

    /**
     * Create the TU transaction
     * @param editTrxVO
     * @param groupSetupVO
     * @param baseSegmentVO
     */
    public ClientStrategy[] createTransferUnitsTrx(EDITDate effectiveDate, String operator, SegmentVO baseSegmentVO, long editTrxPK, long clientRolePK, long contractClientPK) throws EDITEventException
    {
        boolean isTUTranAlreadyWaiting = checkIfTUTranAlreadyWaiting(baseSegmentVO);

        // if there already is a TU tran out there waiting to be executed,
        // don't create another one.
        if (isTUTranAlreadyWaiting)
            return null;

        int taxYear = effectiveDate.getYear();

        InvestmentVO[] allInvestmentVOs = null;

        try
        {
            // the investments in the baseSegment itself don't have
            // non-primary investments.  Get them from the database
            // to start with.
            allInvestmentVOs = getAllInvestmentVOs(baseSegmentVO);
        }
        catch(Exception e)
        {
            throw new RuntimeException("Problem Retrieving Investments", e);
        }

        String pendingStatus = null;
        try
        {
            boolean missing =
                    UnitValues.areUnitValuesMissingForInvestmentsWithChargeCodes(allInvestmentVOs, effectiveDate.getFormattedDate());
            // if we are missing some of the unit values, then it is M
            // and will wait, otherwise it is P and will execute now.
            pendingStatus = missing ? "M" : "P";
        }
        catch(Exception e)
        {
            //EDITEventException editEventException = new EDITEventException(e);
            //System.out.println(e);
            //e.printStackTrace();
            //throw editEventException;
            throw new RuntimeException("Problem evaluating pending status", e);
        }

        // initialize group setup
        GroupSetupVO newGroupSetupVO = new GroupSetupVO();
        newGroupSetupVO.setGroupSetupPK(0);
        newGroupSetupVO.setGroupAmount( new EDITBigDecimal().getBigDecimal() );
        newGroupSetupVO.setGroupPercent( new EDITBigDecimal().getBigDecimal() );

        // initialize contract setup
        ContractSetupVO contractSetupVO = new ContractSetupVO();
        contractSetupVO.setContractSetupPK(0);
        contractSetupVO.setGroupSetupFK(0);
        contractSetupVO.setSegmentFK(baseSegmentVO.getSegmentPK());
        contractSetupVO.setPolicyAmount( new EDITBigDecimal().getBigDecimal() );
        contractSetupVO.setCostBasis( new EDITBigDecimal().getBigDecimal() );
        contractSetupVO.setAmountReceived( new EDITBigDecimal().getBigDecimal() );

        // initialize client setup
        ClientSetupVO clientSetupVO = new ClientSetupVO();
        clientSetupVO.setClientSetupPK(0);
        clientSetupVO.setContractSetupFK(0);
        clientSetupVO.setClientRoleFK(clientRolePK);
        clientSetupVO.setContractClientFK(contractClientPK);

        // create the EDITTrx for the TU trx
        ContractEvent contractEvent = new ContractEvent();
        EDITTrxVO newEditTrxVO = contractEvent.buildDefaultEDITTrxVO("TU", effectiveDate, taxYear, operator);

        contractSetupVO.addClientSetupVO(clientSetupVO);
        newGroupSetupVO.addContractSetupVO(contractSetupVO);
        clientSetupVO.addEDITTrxVO(newEditTrxVO);

        // Set the trxAmount.  Scripts are looking at this.
        // If it is later pulled from the database it will be zero
        // but for real-time transaction, the script will pull
        // a null.
        newEditTrxVO.setTrxAmount( new EDITBigDecimal().getBigDecimal() );

        // set it to M or P
        newEditTrxVO.setPendingStatus(pendingStatus);

        // point back to the premium transaction so that when the premium is undone, this will be undone too.
        // When editTrxPK = 0, TU's are being generated from the automatic client processing for new issues
        if (editTrxPK != 0)
        {
            newEditTrxVO.setOriginatingTrxFK(editTrxPK);
            newEditTrxVO.setTransferUnitsType("M&E");
        }
        else
        {
            newEditTrxVO.setTransferUnitsType("NewIssue");
        }

        // make investmentAllocationOverrides
        InvestmentAllocationOverrideVO[] investmentAllocationOverrides =
                createInvestmentAllocations(allInvestmentVOs);

        // add each investmentAllocationOverride to contract setup of the EDITTrx record
        for (int i = 0; i < investmentAllocationOverrides.length; i++)
        {
            InvestmentAllocationOverrideVO investmentAllocationOverride = investmentAllocationOverrides[i];
            contractSetupVO.addInvestmentAllocationOverrideVO(investmentAllocationOverride);
        }

        // instantiate a SaveGroup, build and save it
        String optionCode = baseSegmentVO.getOptionCodeCT();
        SaveGroup saveGroup = null;
        try
        {
            if (editTrxPK != 0)
            {
                saveGroup = new SaveGroup(newGroupSetupVO, newEditTrxVO, "TU", optionCode);
                saveGroup.build();
                saveGroup.save();
            }
            else
            {
                //new Issue TU's must be processed immediately if "p" status
                GroupTrx groupTrx = new GroupTrx();
                groupTrx.saveGroupSetup(newGroupSetupVO, newEditTrxVO, "TU", optionCode, baseSegmentVO.getProductStructureFK());
            }
        }
        catch (EDITEventException e)
        {
          System.out.println(e);

            e.printStackTrace();
            throw new RuntimeException("problem with SaveGroup operation", e);
        }

        if ("P".equals(pendingStatus) && editTrxPK != 0)
        {
            // the EditTrx must be set in a Client object and ultimately become a ClientStrategy
            GroupSetupVO savedGroupSetupVO = saveGroup.getGroupSetupVO();

            ClientSetupVO clientSetupVOFromSaveGroup =
                    savedGroupSetupVO.getContractSetupVO()[0].getClientSetupVO(0);

            EDITTrxVO hftaEDITTrxVO = clientSetupVOFromSaveGroup.getEDITTrxVO(0);

            ClientTrx clientTrx = new ClientTrx(hftaEDITTrxVO, operator);

            List clientStrategyList = new ArrayList();

            clientStrategyList.add(new Natural(clientTrx, "natural"));

            return (ClientStrategy[]) clientStrategyList.toArray(new ClientStrategy[clientStrategyList.size()]);
        }
        else
        {
            return null;
        }
    }

    /**
     * Check the EDITTrx out there already for this segment.  If there
     * are TU trans with M status waiting to execute later, return true.
     * @param baseSegmentVO
     * @return
     */
    private boolean checkIfTUTranAlreadyWaiting(SegmentVO baseSegmentVO)
    {
        long segmentPK = baseSegmentVO.getSegmentPK();
        EDITTrxVO[] editTrxVOs = null;
        try
        {
            editTrxVOs = event.dm.dao.DAOFactory.getEDITTrxDAO().
                    findAllNotExecutedBySegmentPK(segmentPK, "TU");
        }
        catch(Exception e)
        {
          System.out.println(e);

            e.printStackTrace();
            throw new RuntimeException("Problem getting EDITTrx for TU", e);
        }
        return (editTrxVOs != null);
    }

    /**
     * Save investment allocations to the database and pass back an array
     * of InvestmentAllocationOverrideVO's
     * @param allInvestments
     * @return  InvestmentAllocationOverrideVO[]
     */
    private InvestmentAllocationOverrideVO[] createInvestmentAllocations(
            InvestmentVO[] allInvestments)
    {

        List investmentAllocOverridesList = new ArrayList();

        for (int i = 0; i < allInvestments.length; i++)
        {
            InvestmentVO investmentVO = allInvestments[i];
            InvestmentAllocationVO iaVO = new InvestmentAllocationVO();
            iaVO.setInvestmentAllocationPK(0L);
            iaVO.setInvestmentFK(investmentVO.getInvestmentPK());
            iaVO.setAllocationPercent(new BigDecimal(1.00));
            iaVO.setOverrideStatus("O");
            CRUD crud = null;
            long newInvestmentAllocationPK = 0L;

            // save to DB plain vanilla the investment allocation
            try
            {
                crud = CRUDFactory.getSingleton().getCRUD(ConnectionFactory.EDITSOLUTIONS_POOL);
                newInvestmentAllocationPK = crud.createOrUpdateVOInDB(iaVO);
            }
            catch(Exception e)
            {
                throw new RuntimeException("TrxGeneration: problem saving InvestmentAllocationVO", e);
            }
            finally
            {
                if (crud != null) crud.close();
                crud = null;
            }


            if (newInvestmentAllocationPK > 0L)
            {
                InvestmentAllocationOverrideVO iaoVO = new InvestmentAllocationOverrideVO();
                iaoVO.setInvestmentAllocationOverridePK(0L);
                iaoVO.setContractSetupFK(0L);
                iaoVO.setInvestmentFK(investmentVO.getInvestmentPK());
                iaoVO.setInvestmentAllocationFK(newInvestmentAllocationPK);
                iaoVO.setToFromStatus("F");
                investmentAllocOverridesList.add(iaoVO);
            }
        }

        // convert our List to an InvestmentAllocationOverrideVO[] array
        InvestmentAllocationOverrideVO[] retArray =
                    (InvestmentAllocationOverrideVO[])
                        investmentAllocOverridesList.toArray(
                             new InvestmentAllocationOverrideVO[
                                     investmentAllocOverridesList.size()]);

        return retArray;
    }

    /**
     * Create the EDITTrx for the TU transaction
     * @param editTrxVO
     * @return
     */
    private EDITTrxVO createEDITTrxForTU(EDITTrxVO editTrxVO)
    {
        EDITDate effectiveDate = null;
        int taxYear = 0;

        if (editTrxVO != null)
        {
            effectiveDate = new EDITDate(editTrxVO.getEffectiveDate());
            taxYear = effectiveDate.getYear();
        }

        String operator = editTrxVO.getOperator();

        ContractEvent contractEvent = new ContractEvent();
        EDITTrxVO newEditTrxVO = contractEvent.buildDefaultEDITTrxVO("TU", effectiveDate, taxYear, operator);

        // Set the trxAmount.  Scripts are looking at this.
        // If it is later pulled from the database it will be zero
        // but for real-time transaction, the script will pull
        // a null.
        newEditTrxVO.setTrxAmount( new EDITBigDecimal().getBigDecimal() );

        return newEditTrxVO;
    }

    private InvestmentVO[] getAllInvestmentVOs(SegmentVO baseSegmentVO) throws Exception
    {
        List voInclusionList = new ArrayList();
        voInclusionList.add(BucketVO.class);

        long segmentPK = baseSegmentVO.getSegmentPK();

        contract.business.Lookup contractLookup = new contract.component.LookupComponent();

        InvestmentVO[] investmentVOs = contractLookup.composeInvestmentVOBySegmentPK(segmentPK, voInclusionList);

//        InvestmentVO[] investmentVOs = contract.dm.dao.DAOFactory.getInvestmentDAO().
//                            findBySegmentPK(segmentPK, false, new ArrayList());
        return investmentVOs;
    }

    private EDITTrxVO createEvent(EDITTrxVO editTrxVO, GroupSetupVO groupSetupVO,
                                  InvestmentVO investmentVO, String renewalDate, CRUD crud) throws EDITEventException, EDITDeleteException
    {
        ContractSetupVO[] oldContractSetupVO = groupSetupVO.getContractSetupVO();
        ClientSetupVO[] oldClientSetupVO = oldContractSetupVO[0].getClientSetupVO();

        GroupSetupVO newGroupSetupVO = new GroupSetupVO();
        newGroupSetupVO.setGroupSetupPK(0);
        newGroupSetupVO.setGroupAmount(new EDITBigDecimal().getBigDecimal());
        newGroupSetupVO.setGroupPercent(new EDITBigDecimal().getBigDecimal());

        ContractSetupVO contractSetupVO = new ContractSetupVO();
        contractSetupVO.setContractSetupPK(0);
        contractSetupVO.setGroupSetupFK(0);
        contractSetupVO.setSegmentFK(oldContractSetupVO[0].getSegmentFK());
        contractSetupVO.setPolicyAmount(new EDITBigDecimal().getBigDecimal());
        contractSetupVO.setDeathStatusCT(null);
        contractSetupVO.setCostBasis(new EDITBigDecimal().getBigDecimal());
        contractSetupVO.setAmountReceived(new EDITBigDecimal().getBigDecimal());

        long investmentPK = investmentVO.getInvestmentPK();

        InvestmentAllocationVO investmentAllocationVO = investmentVO.getInvestmentAllocationVO(0);
        InvestmentAllocationOverrideVO investmentAllocationOverrideVO = new InvestmentAllocationOverrideVO();
        investmentAllocationOverrideVO.setInvestmentAllocationOverridePK(0);
        investmentAllocationOverrideVO.setContractSetupFK(0);
        investmentAllocationOverrideVO.setInvestmentAllocationFK(investmentAllocationVO.getInvestmentAllocationPK());
        investmentAllocationOverrideVO.setInvestmentFK(investmentPK);
        investmentAllocationOverrideVO.setToFromStatus("T");
        contractSetupVO.addInvestmentAllocationOverrideVO(investmentAllocationOverrideVO);

        newGroupSetupVO.addContractSetupVO(contractSetupVO);

        ClientSetupVO clientSetupVO = new ClientSetupVO();
        contractSetupVO.removeAllClientSetupVO();
        clientSetupVO.setClientSetupPK(0);
        clientSetupVO.setContractSetupFK(0);
        clientSetupVO.setContractClientFK(oldClientSetupVO[0].getContractClientFK());
        clientSetupVO.setClientRoleFK(oldClientSetupVO[0].getClientRoleFK());


        EDITTrxVO newEDITTrxVO = new EDITTrxVO();

        long originatingTrx = editTrxVO.getEDITTrxPK();
        newEDITTrxVO.setEDITTrxPK(0);
        newEDITTrxVO.setClientSetupFK(0);
        String year = renewalDate.substring(0, 4);
        newEDITTrxVO.setEffectiveDate(renewalDate);
        newEDITTrxVO.setStatus("N");
        newEDITTrxVO.setPendingStatus("P");
        newEDITTrxVO.setSequenceNumber(1);
        newEDITTrxVO.setTaxYear(Integer.parseInt(year));
        newEDITTrxVO.setTrxAmount(new EDITBigDecimal().getBigDecimal());
        newEDITTrxVO.setDueDate(null);
        newEDITTrxVO.setTransactionTypeCT("RN");
        newEDITTrxVO.setTrxIsRescheduledInd("N");
        newEDITTrxVO.setCommissionStatus("N");
        newEDITTrxVO.setLookBackInd("N");
        newEDITTrxVO.setOriginatingTrxFK(originatingTrx);
        newEDITTrxVO.setOperator(editTrxVO.getOperator());
        newEDITTrxVO.setMaintDateTime(new EDITDateTime().getFormattedDateTime());
        newEDITTrxVO.setNoCommissionInd("N");
        newEDITTrxVO.setZeroLoadInd("N");
        newEDITTrxVO.setNoAccountingInd("N");
        newEDITTrxVO.setNoCorrespondenceInd("N");
        newEDITTrxVO.setNoCheckEFT("N");
        newEDITTrxVO.setPremiumDueCreatedIndicator("N");

        clientSetupVO.addEDITTrxVO(newEDITTrxVO);
        contractSetupVO.addClientSetupVO(clientSetupVO);

        SaveGroup saveGroup = new SaveGroup(newGroupSetupVO, newEDITTrxVO, null, null);

        saveGroup.build(crud);
        saveGroup.save(crud);

        GroupSetupVO savedGroupSetupVO = saveGroup.getGroupSetupVO();
        clientSetupVO = savedGroupSetupVO.getContractSetupVO()[0].getClientSetupVO(0);
        editTrxVO = clientSetupVO.getEDITTrxVO(0);

        return editTrxVO;
    }

    public EDITTrxVO generateRebalanceTrx(EDITTrxVO editTrxVO, GroupSetupVO groupSetupVO, long productKey,
                                          CRUD crud, SegmentVO segmentVO, boolean modifyEffDate) throws Exception
    {
        GroupSetupVO newGroupSetupVO = initializeGroupSetup(groupSetupVO, segmentVO);

        EDITTrxVO newEditTrxVO = new EDITTrxVO();

        newEditTrxVO.setEDITTrxPK(0);
        newEditTrxVO.setClientSetupFK(0);
        newEditTrxVO.setEffectiveDate(editTrxVO.getEffectiveDate());
        newEditTrxVO.setOriginatingTrxFK(editTrxVO.getEDITTrxPK());
        newEditTrxVO.setTransactionTypeCT("PR");
        newEditTrxVO.setTaxYear(editTrxVO.getTaxYear());
        newEditTrxVO.setOperator(editTrxVO.getOperator());
        newEditTrxVO.setMaintDateTime(new EDITDateTime().getFormattedDateTime());
        newEditTrxVO.setNoAccountingInd("N");
        newEditTrxVO.setNoCommissionInd("N");
        newEditTrxVO.setZeroLoadInd("N");
        newEditTrxVO.setNoCorrespondenceInd("N");
        newEditTrxVO.setPremiumDueCreatedIndicator("N");

        //Modification made for a specific demo needs to be deactivated at this time
//        String newEffDate = Util.getRebalanceEffDate(newEditTrxVO.getEffectiveDate());
//        newEditTrxVO.setEffectiveDate(newEffDate);

        SaveGroup saveGroup = new SaveGroup(newGroupSetupVO, newEditTrxVO, "PR", null);

        saveGroup.build(crud);

        saveGroup.save(crud);

        GroupSetupVO savedGroupSetupVO = saveGroup.getGroupSetupVO();
        ClientSetupVO clientSetupVO = savedGroupSetupVO.getContractSetupVO()[0].getClientSetupVO(0);
        newEditTrxVO = clientSetupVO.getEDITTrxVO(0);

        return newEditTrxVO;
    }

    private GroupSetupVO initializeGroupSetup(GroupSetupVO groupSetupVO, SegmentVO segmentVO) throws Exception
    {
        ContractSetupVO contractSetupVO = groupSetupVO.getContractSetupVO(0);

        GroupSetupVO newGroupSetupVO = new GroupSetupVO();
        newGroupSetupVO.setGroupSetupPK(0);
        /*newGroupSetupVO.setGroupAmount(0);
        newGroupSetupVO.setGroupPercent(0);*/
        // commented above line(s) for double to BigDecimal conversion
        // sprasad 9/30/2004
        newGroupSetupVO.setGroupAmount( new EDITBigDecimal().getBigDecimal() );
        newGroupSetupVO.setGroupPercent( new EDITBigDecimal().getBigDecimal() );

        ContractSetupVO newContractSetupVO = new ContractSetupVO();
        newContractSetupVO.setContractSetupPK(0);
        newContractSetupVO.setGroupSetupFK(0);
        /*newContractSetupVO.setPolicyAmount(0);
        newContractSetupVO.setCostBasis(0);
        newContractSetupVO.setAmountReceived(0);*/
        // commented above line(s) for double to BigDecimal conversion
        // sprasad 9/30/2004
        newContractSetupVO.setPolicyAmount( new EDITBigDecimal().getBigDecimal() );
        newContractSetupVO.setCostBasis( new EDITBigDecimal().getBigDecimal() );
        newContractSetupVO.setAmountReceived( new EDITBigDecimal().getBigDecimal() );
        newContractSetupVO.setSegmentFK(contractSetupVO.getSegmentFK());

        InvestmentAllocationOverrideVO[] investmentAllocationOverrideVOs = setupFundsForRebalance(newContractSetupVO, segmentVO);

        newContractSetupVO.setInvestmentAllocationOverrideVO(investmentAllocationOverrideVOs);

        newGroupSetupVO.addContractSetupVO(newContractSetupVO);

        return newGroupSetupVO;
    }

    private InvestmentAllocationOverrideVO[] setupFundsForRebalance(ContractSetupVO contractSetupVO, SegmentVO segmentVO) throws Exception
    {
        long segmentFK = contractSetupVO.getSegmentFK();

        contract.business.Lookup lookup = new contract.component.LookupComponent();

/*
        List voInclusionList = new ArrayList();
        voInclusionList.add(InvestmentVO.class);
        voInclusionList.add(InvestmentAllocationVO.class);
        voInclusionList.add(BucketVO.class);

        SegmentVO segmentVO = lookup.composeSegmentVO(segmentFK, voInclusionList);
*/

        InvestmentVO[] investmentVOs = segmentVO.getInvestmentVO();

        EDITBigDecimal totalRebalanceAmount = new EDITBigDecimal();
        InvestmentVO fromInvestmentVO = null;

        Hashtable toFunds = new Hashtable();

        for (int i = 0; i < investmentVOs.length; i++)
        {
            EDITBigDecimal bucketRebalanceAmount = new EDITBigDecimal();
            BucketVO[] bucketVOs = investmentVOs[i].getBucketVO();
            for (int j = 0; j < bucketVOs.length; j++)
            {
                EDITBigDecimal rebalAmt = new EDITBigDecimal(bucketVOs[j].getRebalanceAmount());

                if (rebalAmt.isGT(EDITBigDecimal.EDIT_DECIMAL_ZILCH_STR))
                {
                    bucketRebalanceAmount = bucketRebalanceAmount.addEditBigDecimal(rebalAmt);
                }
            }

            if (bucketRebalanceAmount.isGT(EDITBigDecimal.EDIT_DECIMAL_ZILCH_STR))
            {
                totalRebalanceAmount = bucketRebalanceAmount;
                fromInvestmentVO = investmentVOs[i];
            }

            InvestmentAllocationVO[] investmentAllocationVOs = investmentVOs[i].getInvestmentAllocationVO();
            for (int k = 0; k < investmentAllocationVOs.length; k++)
            {
                if (investmentAllocationVOs[k].getOverrideStatus().equalsIgnoreCase("P"))
                {
                    toFunds.put(investmentVOs[i].getInvestmentPK() + "", investmentAllocationVOs[k]);
                    k = investmentAllocationVOs.length;
                }
            }
        }

        InvestmentAllocationOverrideVO[] invAllocOverrideVOs = getToFundOverrides(toFunds);

        long fromInvAllocationPK = getRebalFromInvAllocPK(totalRebalanceAmount, fromInvestmentVO);

        //Create From Fund Override
        InvestmentAllocationOverrideVO fromInvAllocOverrideVO = new InvestmentAllocationOverrideVO();
        fromInvAllocOverrideVO.setInvestmentAllocationOverridePK(0);
        fromInvAllocOverrideVO.setInvestmentFK(fromInvestmentVO.getInvestmentPK());
        fromInvAllocOverrideVO.setInvestmentAllocationFK(fromInvAllocationPK);
        fromInvAllocOverrideVO.setToFromStatus("F");

        invAllocOverrideVOs[toFunds.size()] = fromInvAllocOverrideVO;

        return invAllocOverrideVOs;
    }

    private long getRebalFromInvAllocPK(EDITBigDecimal rebalanceAmount, InvestmentVO fromInvestmentVO) throws Exception
    {
        InvestmentAllocation investmentAllocation = new InvestmentAllocation(fromInvestmentVO.getInvestmentAllocationVO());
        long investmentAllocationPK = investmentAllocation.getPKForAllocationDollars(rebalanceAmount);

        if (investmentAllocationPK == 0)
        {
            InvestmentAllocation newInvestmentAllocation = new InvestmentAllocation(fromInvestmentVO.getInvestmentPK(),
                                                                                    Util.roundToNearestCent(rebalanceAmount).getBigDecimal(),
                                                                                    "O",
                                                                                    "Dollars");
            newInvestmentAllocation.save();
            investmentAllocationPK = newInvestmentAllocation.getPK();
        }

        return investmentAllocationPK;
    }

    private InvestmentAllocationOverrideVO[] getToFundOverrides(Hashtable toFunds)
    {
        InvestmentAllocationOverrideVO[] invAllocOverrideVOs = new InvestmentAllocationOverrideVO[toFunds.size() + 1];

        Enumeration toFundKeys = toFunds.keys();

        int i = 0;

        while (toFundKeys.hasMoreElements())
        {
            String investmentPK = (String) toFundKeys.nextElement();
            InvestmentAllocationVO invAllocVO = (InvestmentAllocationVO) toFunds.get(investmentPK);

            InvestmentAllocationOverrideVO invAllocOverrideVO = new InvestmentAllocationOverrideVO();
            invAllocOverrideVO.setInvestmentAllocationOverridePK(0);
            invAllocOverrideVO.setContractSetupFK(0);
            invAllocOverrideVO.setInvestmentFK(Long.parseLong(investmentPK));
            invAllocOverrideVO.setInvestmentAllocationFK(invAllocVO.getInvestmentAllocationPK());
            invAllocOverrideVO.setToFromStatus("T");

            invAllocOverrideVOs[i] = invAllocOverrideVO;

            i += 1;
        }

        return invAllocOverrideVOs;
    }

    public EDITTrxVO createVTTransaction(SegmentVO segment, EDITTrxVO editTrxVO, List buckets, EDITTrxHistoryVO editTrxHistoryVO) throws EDITEventException
     {
         String contractNumber = editTrxVO.getNewPolicyNumber();
         Segment[] newSegments = Segment.findBy_ContractNumber(contractNumber);

         ValueTransferTrx valueTransferTrx = new ValueTransferTrx(newSegments[0]);

         GroupSetupVO groupSetupVO = valueTransferTrx.createValueTransferTrx(editTrxVO, buckets, editTrxHistoryVO);
         EDITTrxVO newEditTrxVO = groupSetupVO.getContractSetupVO(0).getClientSetupVO(0).getEDITTrxVO(0);

         String optionCode = segment.getOptionCodeCT();

         SaveGroup saveGroup = new SaveGroup(groupSetupVO, newEditTrxVO, "VT", optionCode);
         saveGroup.build();
         saveGroup.save();
         GroupSetupVO savedGroupSetupVO = saveGroup.getGroupSetupVO();

         newEditTrxVO = savedGroupSetupVO.getContractSetupVO(0).getClientSetupVO(0).getEDITTrxVO(0);

         return newEditTrxVO;
     }

    private boolean determineIfRebalTrxNeeded(long segmentPK, CRUD crud)
    {
        boolean generateTrx = false;

        InvestmentVO[] investmentVOs = new contract.dm.dao.FastDAO().findInvestmentBy_SegmentPK(segmentPK, crud);

        List voInclusionList = new ArrayList();
        voInclusionList.add(BucketVO.class);

        if (investmentVOs != null)
        {
            for (int i = 0; i < investmentVOs.length; i++)
            {
                crud.retrieveVOFromDBRecursively(investmentVOs[i], voInclusionList, true);
            }

            BucketVO[] bucketVOs = null;
            for (int i = 0; i < investmentVOs.length; i++)
            {
                bucketVOs = investmentVOs[i].getBucketVO();
                for (int j = 0; j < bucketVOs.length; j++)
                {
                    EDITBigDecimal rebalanceAmount = new EDITBigDecimal(bucketVOs[j].getRebalanceAmount());
                    if (rebalanceAmount.isGT("0"))
                    {
                        generateTrx = true;
                        break;
                    }
                }
            }
        }

        return generateTrx;
    }
}
