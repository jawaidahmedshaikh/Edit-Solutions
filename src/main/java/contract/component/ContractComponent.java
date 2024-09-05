/*
 * User: unknown
 * Date: Oct 3, 2001
 * Time: unknown
 *
 * (c) 2000-2008 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */

package contract.component;

import contract.*;

import contract.batch.BankProcessor;

import contract.dm.StorageManager;

import contract.ui.ImportNewBusinessResponse;

import edit.common.EDITBigDecimal;
import edit.common.EDITDate;
import edit.common.exceptions.EDITContractException;
import edit.common.exceptions.EDITLockException;
import edit.common.exceptions.EDITValidationException;
import edit.common.vo.*;

import edit.services.component.AbstractComponent;
import edit.services.db.CRUD;
import edit.services.db.ConnectionFactory;
import edit.services.db.hibernate.*;
import edit.services.logging.Logging;

import engine.component.CalculatorComponent;

import engine.sp.SPException;
import engine.sp.SPOutput;
import engine.sp.custom.document.*;

import extension.ICMGExcelMapper;

import fission.utility.*;

import java.util.*;

import logging.LogEvent;

import org.apache.logging.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import billing.*;

import edit.common.exceptions.EDITCaseException;
import edit.common.exceptions.EDITNonFinancialException;

import event.*;
import group.*;



/**
 * The Contract request controller
 */
public class ContractComponent extends AbstractComponent implements contract.business.Contract {


    //Member variables
    private StorageManager sm;

    private BankProcessor  bankProcessor;


    /**
     * ContractComponent constructor
     */
	public ContractComponent() {

        sm = new StorageManager();
        bankProcessor = new BankProcessor();
	}

    public void backupSegment(SegmentVO segmentVO) throws Exception {

        sm.backupSegment(segmentVO);
    }

    public SegmentVO restoreSegment(long segmentPK) throws Exception {

        return sm.restoreSegment(segmentPK);
    }

    public void deleteSegmentFromBackup(long segmentId) throws Exception {

        sm.deleteContractBackup(segmentId);
    }

    public void restoreSegmentFromBackup(SegmentVO segmentVO) throws Exception {

        sm.restoreSegment(segmentVO);
    }

    /**
     * @see contract.business.Contract#saveSegment
     */
    public long saveSegment(SegmentVO segmentVO, String conversionValue, boolean saveChangeHistory, String operator) throws EDITContractException
    {
             
    	return saveSegment(null, segmentVO, conversionValue, saveChangeHistory, operator);
    }

    /**
     * @see contract.business.Contract#saveSegment
     */
    public long saveSegment(ContractVO contractVO, SegmentVO segmentVO, String conversionValue, boolean saveChangeHistory, String operator) throws EDITContractException
    {

//        Segment segment = new Segment(segmentVO);
        SessionHelper.clearSessions();

        Segment segment = (Segment) SessionHelper.map(segmentVO, SessionHelper.EDITSOLUTIONS);
        segment.setOperator(operator);
        segmentVO.setSegmentPK(segment.getSegmentPK().longValue());

        // This makes sure we don't lose all of the "fluffy" stuff attached to the SegmentVO.
        segment.setVO(segmentVO);

        DepartmentLocation departmentLocation = segment.getDepartmentLocation();
        if (departmentLocation != null && segmentVO.getDepartmentLocationFK() == 0)
        {
            departmentLocation.removeSegment(segment);
        }

        // check whether it is necessary to create freelook transaction or not
        boolean createFreeLookTransaction = segment.createFreeLookTransaction();

        try
        {
            StorageManager sm = new StorageManager();
            if (segment.getSegmentPK() != null && segment.getSegmentPK().longValue() != 0)
            {
                // Only check for product structure changes if the segment has been saved at least once before
                checkForProductStructureChange(segment);
            }
        }
        catch (Exception e)
        {
            System.out.println(e);
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        Segment[] newRiderSegments = segment.checkForNewRider(segmentVO);

        //  Determine what type of billSchedule the segment's BillSchedule was changed to BEFORE saving the segment
        String billScheduleChangedType = segment.getBillScheduleChangedType();

        segment.checkForComplexChange();

        //The segment entity is fully populated by hibernate the VOs are no longer needed for update
        segment.save();
        
        // set SelectedRiderPK for use with scripts
        /*
        if (contractVO != null) {
            if ((newRiderSegments != null) && (newRiderSegments.length > 0)) {
                contractVO.setSelectedRiderPK(Segment.findBy_ContractNumberAndRiderNumber(
                		newRiderSegments[0].getContractNumber(), newRiderSegments[0].getRiderNumber()).getSegmentPK());	
            } else {
        	    int i = segment.getRiders().length;
        	    contractVO.setSelectedRiderPK(segment.get_Riders()[i - 1].getSegmentPK());
            }
        }
        */

        if (createFreeLookTransaction)
        {
            segment.createFreeLookEDITTrx();
        }

        SessionHelper.clearSessions();
        
        try
        {
            if (newRiderSegments != null)
            {
                for (int i = 0; i < newRiderSegments.length; i++)
                {
                    new EDITTrx().createBCTrxForNewRiders(segment, newRiderSegments[i]);
                }
            }

            //  Create the appropriate BC trxs for the billSchedule change AFTER the segment has been saved
            segment.createBCTrxForBillScheduleChange(billScheduleChangedType, conversionValue);

            if (segmentVO.getRequiredMinDistributionVOCount() > 0)
            {
                RequiredMinDistributionVO rmdVO = segmentVO.getRequiredMinDistributionVO(0);

                if ((rmdVO.getElectionCT() != null) && (rmdVO.getFrequencyCT() != null))
                {
                    segment.generateOrUpdateRmdTrx(rmdVO);
                }
            }
        }
        catch (Exception e)
        {
            System.out.println(e);
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        return segment.getPK();
	}

    /**
     * @see contract.business.Contract#saveBillSchedule(edit.common.vo.BillScheduleVO)
     */
    public Long saveBillSchedule(BillScheduleVO billScheduleVO) throws EDITContractException
    {
        BillSchedule billSchedule = (BillSchedule) SessionHelper.map(billScheduleVO, SessionHelper.EDITSOLUTIONS);

        try
        {
            SessionHelper.beginTransaction(SessionHelper.EDITSOLUTIONS);

            SessionHelper.saveOrUpdate(billSchedule, SessionHelper.EDITSOLUTIONS);
            SessionHelper.commitTransaction(SessionHelper.EDITSOLUTIONS);
        }
        catch (Exception e)
        {
            System.out.println(e);

            e.printStackTrace();

            SessionHelper.rollbackTransaction(SessionHelper.EDITSOLUTIONS);

            throw new EDITContractException(e.getMessage());
        }
        finally
        {
            SessionHelper.clearSessions();
        }
        
        return billSchedule.getBillSchedulePK();
    }

    public long saveSegmentForNBWithHibernate(Segment segment, String conversionValue) throws EDITContractException
    {
        SegmentVO segmentVO = (SegmentVO)segment.getVO();

        // needs to be verified before saving the contract
        // check whether it is necessary to create freelook transaction or not
        boolean createFreeLookTransaction = segment.createFreeLookTransaction();

        segment.verifyAndUpdatePolicyDeliveryDate();

        segment.setContractNumberToUpperCase();

        Long segmentPK = segment.getSegmentPK();

        StorageManager sm = new StorageManager();

        CommissionHistory[] adjustedCommissionHistories = sm.adjustCommissionHistoriesForMissingAgentHierarchies((SegmentVO) segment.getVO());

        // Save the adjustedCommissionHistories [BEFORE] saving the Segment to avoid any constraint
        // violations between AgentSnapshot and CommissionHistory.
        for (int i = 0; i < adjustedCommissionHistories.length; i++)
        {
            CommissionHistory currentAdjustedCommissionHistory = new CommissionHistory((CommissionHistoryVO) adjustedCommissionHistories[i].getVO());

            SessionHelper.beginTransaction(SessionHelper.EDITSOLUTIONS);
            SessionHelper.saveOrUpdate(currentAdjustedCommissionHistory, SessionHelper.EDITSOLUTIONS);
            SessionHelper.commitTransaction(SessionHelper.EDITSOLUTIONS);
        }

        try
        {
            if (segmentPK != null && segmentPK.longValue() != 0)
            {
                // Only check for product structure changes if the segment has been saved at least once before
                checkForProductStructureChange(segment);
            }
        }
        catch (Exception e)
        {
            System.out.println(e);
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        Segment[] newRiderSegments = segment.checkForNewRider(segmentVO);

        //  Determine if any riders have been deleted before saving the segment
        Segment[] deletedRiders = segment.ridersDeleted();

        //  Determine what type of billSchedule the segment's BillSchedule was changed to BEFORE saving the segment
        String billScheduleChangedType = segment.getBillScheduleChangedType();

        //  Determine if the owner has been changed (i.e. one was deleted)
        boolean ownerDeleted = segment.ownerDeleted();

        // Hibernate save
        segment.save();

        if (createFreeLookTransaction)
        {
            segment.createFreeLookEDITTrx();
        }

        try
        {
            //  Create a BC trx if a rider was added if contract is NOT SubmitPend status (Submit trx will handle changes in that case)
            if (newRiderSegments != null && !segment.getSegmentStatusCT().equalsIgnoreCase(Segment.SEGMENTSTATUSCT_SUBMIT_PEND))
            {
                for (int i = 0; i < newRiderSegments.length; i++)
                {
                    new EDITTrx().createBCTrxForNewRiders(segment, newRiderSegments[i]);
                }
            }

            //  Create a BC trx if a rider was deleted if contract is NOT SubmitPend status (Submit trx will handle changes in that case)
            if (deletedRiders != null && !segment.getSegmentStatusCT().equalsIgnoreCase(Segment.SEGMENTSTATUSCT_SUBMIT_PEND))
            {
                for (int i = 0; i < deletedRiders.length; i++)
                {
                    EDITTrx.createBillingChangeTrxGroupSetup(segment, segment.getOperator(), ContractSetup.COMPLEXCHANGETYPECT_RIDER_DELETE, deletedRiders[i].getEffectiveDate(), deletedRiders[i].getRiderNumber());
                }
            }

            //  Create the appropriate BC trxs for the billSchedule change AFTER the segment has been saved
            segment.createBCTrxForBillScheduleChange(billScheduleChangedType, conversionValue);

            if (ownerDeleted)
            {
                EDITTrx.createComplexChangeTrxGroupSetup(segment, segment.getOperator(), ContractSetup.COMPLEXCHANGETYPECT_OWNER_DELETE, new EDITDate());
            }

            if (segmentVO.getRequiredMinDistributionVOCount() > 0)
            {
                RequiredMinDistributionVO rmdVO = segmentVO.getRequiredMinDistributionVO(0);

                if ((rmdVO.getElectionCT() != null) && (rmdVO.getFrequencyCT() != null))
                {
                    segment.generateOrUpdateRmdTrx(rmdVO);
                }
            }
        }
        catch (Exception e)
        {
            System.out.println(e);
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        finally
        {
            SessionHelper.clearSessions();
        }

        return segmentPK;
    }

    /**
     * Check for ProductStructure change, if not equal to the existing database contract, do maintenance on the
     * ContractRequirements.  The old ones must be deleted and the new ones added to the contract.
     * @param segmentVO
     * @throws Exception
     */
    public void checkForProductStructureChange(Segment segment) throws Exception
    {
        Long productStructureID = segment.getProductStructureFK();

        Segment existingSegment = Segment.findByPK(segment.getSegmentPK());

        if (productStructureID.longValue() != existingSegment.getProductStructureFK())
        {
            Set contractRequirements = segment.getContractRequirements();
            for (Iterator iterator = contractRequirements.iterator(); iterator.hasNext();)
            {
                ContractRequirement contractRequirement = (ContractRequirement) iterator.next();

                contractRequirement.hDelete();
            }
        }
    }

   /**
     * @see contract.business.Contract#saveSegment
     */
    public long saveSegmentForNewBusiness(SegmentVO segmentVO, boolean saveChangeHistory, String operator) throws EDITContractException
    {
       Segment segment = new Segment(segmentVO);
      
       segment.setSaveChangeHistory(saveChangeHistory);
       segment.setOperator(operator);
       if (segment.getWorksheetTypeCT() == null)
       {
           segment.setWorksheetTypeCT(Segment.WORKSHEETTYPECT_CORRECTION);
       }

       segment.saveSegmentForNewBusiness();

       return segment.getPK();
    }

    /**
     * Saves the segment non-recursively
     * @param segmentVO
     * @param saveChangeHistory
     * @param operator
     * @return
     * @throws EDITContractException
     */
    public long saveSegmentNonRecursively(SegmentVO segmentVO, boolean saveChangeHistory, String operator) throws EDITContractException
    {
        Segment segment = new Segment(segmentVO);

        segment.setSaveChangeHistory(saveChangeHistory);
        segment.setOperator(operator);

        segment.saveNonRecursively();

        return segment.getPK();
    }

    // NOTE: replaced with saveSegment to consolidate to one save method
//    public void saveSegmentOnUnCommit(SegmentVO segmentVO) throws Exception
//    {
//        sm.saveSegmentOnUnCommit(segmentVO);
//    }

    public void commitSegment(SegmentVO segmentVO) throws Exception {

        sm.commitSegment(segmentVO);
	}

    public void updateSegmentAfterBatch(SegmentVO segmentVO) throws Exception {

        sm.updateSegmentAfterBatch(segmentVO);
	}

    public void deleteSegment(SegmentVO segmentVO) throws Exception {

        sm.deleteSegment(segmentVO);
	}

    public void deleteQuote(SegmentVO segmentVO) throws Exception {

        sm.deleteQuote(segmentVO);
	}

	public String autoGenerateContractNumber(String businessContract) throws Exception {

        String contractNum = null;

		// Only grab the 1st three characters

        if (businessContract.length() >= 3) {
            businessContract = businessContract.substring(0,3);
        }

        try
        {
            long nextAvailableKey = CRUD.getNextAvailableKey();

            // contractNum = businessContract + Util.formatDecimal("000000000000",(double)nextAvailableKey);
            // commented above line(s) for double to BigDecimal conversion
            // sprasad 9/29/2004
            contractNum = businessContract + Util.formatDecimal( "000000000000", new EDITBigDecimal(nextAvailableKey+"") );
            if (contractNum.length() > 15)
            {
                contractNum = contractNum.substring(0, 15);
            }
        }
        catch (Exception e)
        {
            System.out.println(e);

            e.printStackTrace();

            throw e;
        }

		return contractNum;
	}

	public void saveChangeHistory(ChangeHistoryVO changeHistoryVO) throws Exception {

		sm.saveChangeHistory(changeHistoryVO);

	}

     public long createOrUpdateVO(Object valueObject, boolean recursively) throws Exception{

        long pk = super.createOrUpdateVO(valueObject, ConnectionFactory.EDITSOLUTIONS_POOL, recursively);

        return pk;
    }

    public void deleteVO(String voName, long primaryKey) throws Exception {

        throw new Exception("This method has not been implemented");
    }

    public ElementLockVO lockElement(long segmentPK, String username) throws EDITLockException{

        return sm.lockElement(segmentPK, username);
    }

    public int unlockElement(long lockTablePK){

        return sm.unlockElement(lockTablePK);
    }

    // NOTE: replaced with saveSegment to consolidate to one save method
//    public long saveSegmentAndHistory(ContractVO contractVO, String operator) throws Exception
//    {
//        return sm.saveSegmentAndHistory(contractVO, operator);
//	}

    public long saveBucketVO(BucketVO bucketVO) throws Exception
    {
        return sm.saveBucketVO(bucketVO);
    }

    public long saveInvestmentAllocationVO(InvestmentAllocationVO investmentAllocationVO) throws Exception
    {
        return sm.saveInvestmentAllocationVO(investmentAllocationVO);
    }

    public long saveInvestmentVO(InvestmentVO investmentVO) throws Exception {

        return sm.saveInvestmentVO(investmentVO);
    }

    public long saveRequirementVO(RequirementVO requirementVO) throws Exception {

        return sm.saveRequirementVO(requirementVO);
    }

    /**
     * @see contract.business.Contract#saveContractClientVO
     *
     * NOTE: most of the logic in this method is repeated in StorageManager.saveSegment().  At the time of development,
     * these 2 methods were not easily put into one, hence the duplication
     *
     * This method assumes that the Billing is being attached to the Segment and not the Case.  If the Billing is null,
     * then Billing must exist in the Segment's Case.  If not, an error is thrown.
     */
    public long saveContractClientVO(ContractClientVO contractClientVO) throws EDITContractException
    {
        ContractClient contractClient = new ContractClient(contractClientVO);

        contractClient.save();

        long contractClientPK = contractClient.getPK();

        // COMMENTED OUT FOR NOW. NO LONGER USING BILLLAPSE THIS WAY
//        if (contractClient.isPayor())
//        {
//            if (billLapseVO != null)
//            {
//                if (billingVO == null)
//                {
//                    //  No Billing provided, must have a Billing for the BillLapse
//                    throw new EDITContractException("Must provide Billing for a ContractClient of role type Payor " +
//                        ": ContractClientPK = " + contractClient.getPK());
//                }
//                else
//                {
//                    BillLapse billLapse = new BillLapse(billLapseVO);   // There should only be one BillLapse per Payor
//
//                    Billing billing = new Billing(billingVO);
//
//                    billing.setSegmentFK(new Long(contractClientVO.getSegmentFK()));
//
//                    billing.save();
//
//                    billLapse.setContractClientFK(new Long(contractClientPK));
//                    billLapse.setBillingFK(billing.getBillingPK());
//
//                    billLapse.save();
//                }
//            }
//        }

        return contractClientPK;
    }

    public long saveContractClientAllocationVO(ContractClientAllocationVO contractClientAllocationVO) throws Exception {

        return sm.saveContractClientAllocationVO(contractClientAllocationVO);
    }

    public long saveWithholdingVO(WithholdingVO withholdingVO) throws Exception {

        return sm.saveWithholdingVO(withholdingVO);
    }

     public int deleteVO(Class voClass, long primaryKey, boolean recursively) throws Exception
    {
        return super.deleteVO(voClass, primaryKey, ConnectionFactory.EDITSOLUTIONS_POOL, false);
    }

    public Object retrieveVO(Class voClass, long primaryKey, boolean recursively, List voInclusionList) throws Exception
    {
        return super.retrieveVO(voClass, primaryKey, ConnectionFactory.EDITSOLUTIONS_POOL, false, null);
    }

    public void attachRequirementsToProductStructure(long productStructurePK, long[] requirementPKs) throws Exception {

        sm.attachRequirementsToCompanyStructure(productStructurePK, requirementPKs);
    }

    public void detachRequirementsFromProductStructure(long productStructurePK, long[] requirementPKs) throws Exception {

        sm.detachRequirementsFromCompanyStructure(productStructurePK, requirementPKs);
    }

    public long getNextAvailableKey()
    {
        return CRUD.getNextAvailableKey();
    }

    public String processBank(String companyName, String contractId) throws Exception {

        return null;//bankProcessor.createBankExtracts(companyName, contractId);
    }
	public BatchStatusVO[] getBatchStatus() throws Exception
    {
//		return batchProcessor.getBatchStatus();
        return null;
    }
    public void updateBatchList(String completedBatchId)throws Exception
	{
//		batchProcessor.updateBatchList(completedBatchId);
	}

    public void restoreContract(long segmentFK)  throws Exception
    {
        SegmentBackup segmentBackup = new SegmentBackup();
        segmentBackup.restoreContract(segmentFK);
    }

//    /**
//     * @see contract.business.Contract#saveBilling(edit.common.vo.BillingVO)
//     */
//    public void saveBilling(BillingVO billingVO) throws EDITContractException
//    {
//        Billing billing = new Billing(billingVO);
//
//        billing.save();
//    }

//    /**
//     * @see contract.business.Contract#saveBillLapse(edit.common.vo.BillLapseVO)
//     */
//    public void saveBillLapse(BillLapseVO billLapseVO) throws EDITContractException
//    {
//        BillLapse billLapse = new BillLapse(billLapseVO);
//
//        billLapse.save();
//    }

    public void deleteChangeHistory(long segmentPK) {

        Segment segment = new Segment(segmentPK);

        segment.deleteChangeHistory();
    }

    /**
     * @see contract.business.Contract#importNewBusiness(org.apache.poi.hssf.usermodel.HSSFWorkbook)
     */
    public ImportNewBusinessResponse[] importNewBusiness(HSSFWorkbook hssfWorkbook, 
                                                         String productStructureId,
                                                         String operator) throws EDITContractException
    {
        ICMGExcelMapper mapper = new ICMGExcelMapper();

        ImportNewBusinessResponse[] importResponses = mapper.importNewBusiness(hssfWorkbook, productStructureId, operator);

        return importResponses;
    }

    /**
     * @see contract.business.Contract#importNewBusiness(org.apache.poi.hssf.usermodel.HSSFWorkbook, String)
     */
    public ImportNewBusinessResponse[] importNewBusiness(HSSFWorkbook hssfWorkbook, 
                                                         String importValues, 
                                                         String productStructureId,
                                                         String operator) throws EDITContractException
    {
        ICMGExcelMapper mapper = new ICMGExcelMapper();

        ImportNewBusinessResponse[] importResponses = mapper.importNewBusiness(hssfWorkbook, importValues, productStructureId, operator);

        return importResponses;
    }

    /**
     * @see contract.business.Contract#validateQuote(QuoteVO, String)
     */
    public SPOutputVO validateQuote(QuoteVO quoteVO, String processName) throws SPException, EDITValidationException
    {
        SPOutput spOutput   = null;
        SPOutputVO spOutputVO = null;
        SegmentVO baseSegmentVO = quoteVO.getSegmentVO()[0];

        long segmentPK           = baseSegmentVO.getSegmentPK();
        long productStructurePK  = baseSegmentVO.getProductStructureFK();
        EDITDate effectiveDate   = new EDITDate();
        Segment segment = new Segment(baseSegmentVO);
        String eventType = segment.setEventTypeForValidationScript();

        try
        {
            spOutput = new CalculatorComponent().processScriptForNBValidation("QuoteVO", quoteVO, processName, "*", eventType, effectiveDate.getFormattedDate(),
                                                           productStructurePK, false);

            spOutputVO = spOutput.getSPOutputVO();
        }
        catch (SPException e)
        {
            System.out.println(e);

            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.

            throw e;
        }

        // insert new requirement if there are no validations
        if (spOutputVO.getValidationVOCount() == 0)
        {
            VOObject[] voObjects = spOutputVO.getVOObject();

            ContractRequirement contractRequirement = null;

            ContractRequirementVO contractRequirementVO = null;

            for (int i = 0; i < voObjects.length; i++)
            {
                VOObject voObject = voObjects[i];

                if (voObject instanceof InsertRequirementVO)
                {
                    InsertRequirementVO insertRequirementVO = (InsertRequirementVO) voObject;

                    String requirementId = insertRequirementVO.getRequirementId();

                    contractRequirement = insertContractRequirement(segmentPK, productStructurePK, requirementId);

                    if (contractRequirement != null)
                    {
                        contractRequirementVO = (ContractRequirementVO) contractRequirement.getVO();

                        // here need to add it to segmentVO othere wise gets deleted while saving the quote
                        // because the quote is saved recursively at this point of time. 
                        baseSegmentVO.addContractRequirementVO(contractRequirementVO);
                    }
                }
            }
        }

        return spOutputVO;
    }

    /**
     * @see contract.business.Contract#calculateIssueAge(String, String)
     */
    public int calculateIssueAge(String birthDate, String effectiveDate) throws EDITContractException
    {
        int issueAge = 0;

        if ((EDITDate.isACandidateDate(birthDate)) && (EDITDate.isACandidateDate(effectiveDate)))
        {
            EDITDate ceEffDate = new EDITDate(effectiveDate);
            EDITDate ceDOB = new EDITDate(birthDate);

            issueAge = ceDOB.getAgeAtLastBirthday(ceEffDate);
        }
        else
        {
            throw new EDITContractException("CalculateIssueAge: Invalid date - birthDate = " + birthDate + ", effectiveDate = " + effectiveDate);
        }

        return issueAge;
    }

    /**
     * @see contract.business.Contract#calculateIssueAgeForLifeContracts(String, String)
     */
    public int calculateIssueAgeForLifeContracts(String birthDate, String effectiveDate) throws EDITContractException
    {
        int issueAge = 0;

        if ((EDITDate.isACandidateDate(birthDate)) && (EDITDate.isACandidateDate(effectiveDate)))
        {
            EDITDate ceEffDate = new EDITDate(effectiveDate);
            EDITDate ceDOB = new EDITDate(birthDate);

            issueAge = ceDOB.getAgeAtNearestBirthday(ceEffDate);
        }
        else
        {
            throw new EDITContractException("CalculateIssueAgeForLifeContracts: Invalid date - birthDate = " + birthDate + ", effectiveDate = " + effectiveDate);
        }

        return issueAge;
    }

    /**
     * @see contract.business.Contract#validateContract(edit.common.vo.ContractVO, String)
     */
    public SPOutputVO validateContract(ContractVO contractVO, String processName) throws SPException, EDITValidationException
    {
        SPOutput spOutput = null;
        SPOutputVO spOutputVO   = null;
        SegmentVO baseSegmentVO = contractVO.getSegmentVO();

        long segmentPK          = baseSegmentVO.getSegmentPK();
        long productStructurePK = baseSegmentVO.getProductStructureFK();
        String effectiveDate    = baseSegmentVO.getEffectiveDate();
        Segment segment = new Segment(baseSegmentVO);
        String eventType = segment.setEventTypeForValidationScript();

        try
        {
            spOutput = new CalculatorComponent().processScript("ContractVO", contractVO,
                                                  processName,
                                                  "*",
                                                  eventType,
                                                  effectiveDate,
                                                  productStructurePK,
                                                  false);

            spOutputVO = spOutput.getSPOutputVO();
        }
        catch (SPException e)
        {
            System.out.println(e);

            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.

            throw e;
        }

        // insert new requirement if there are no validations
        if (spOutputVO.getValidationVOCount() == 0)
        {
            VOObject[] voObjects = spOutputVO.getVOObject();

            for (int i = 0; i < voObjects.length; i++)
            {
                VOObject voObject = voObjects[i];

                if (voObject instanceof InsertRequirementVO)
                {
                    InsertRequirementVO insertRequirementVO = (InsertRequirementVO) voObject;

                    String requirementId = insertRequirementVO.getRequirementId();

                    insertContractRequirement(segmentPK, productStructurePK, requirementId);
                }
            }
        }


        return spOutputVO;
    }

    /**
     * @see contract.business.Contract#insertContractRequirement(long, long, String)
     * @param requirementId
     * @param segmentPK
     * @return
     */
    // this is returning ContractRequirment to add it to segmentVO
    // this is used only in validateQuote because the quote is saved recursively.
    public ContractRequirement insertContractRequirement(long segmentPK, long productStructurePK, String requirementId) throws EDITValidationException
    {
        ContractRequirement contractRequirement = null;

        FilteredRequirement filteredRequirement = FilteredRequirement.
                findBy_ProductStructurePK_AND_RequirementId(productStructurePK, requirementId);

        if (filteredRequirement != null)
        {
            if (segmentPK != 0)
            {
                Segment segment = Segment.findByPK(segmentPK);
                contractRequirement = segment.insertRequirement(requirementId);
            }
            else
            {
                // if new contract create new contractRequirement and associate to filteredRequirment.
                contractRequirement = new ContractRequirement();
                contractRequirement.associateFilteredRequirement(filteredRequirement);
            }
        }
        else
        {
            String message = "The Requirement [" + requirementId + "] is not associated to the same Company Structure as Contract";

            LogEvent logEvent = new LogEvent(message);

            Logger logger = Logging.getLogger(Logging.GENERAL_EXCEPTION);

            logger.error(logEvent);

            logging.Log.logGeneralExceptionToDatabase(null, new Exception(message));
        }

        return contractRequirement;
    }

    /**
     * Clone Requirements from activeCompanyStructure to cloneToCompanyStructure.
     * @param activeCompanyStructure
     * @param cloneToCompanyStructure
     */
    public void cloneRequirements(long activeCompanyStructurePK, long cloneToCompanyStructurePK) throws Exception 
    {
        new Requirement().cloneRequirements(activeCompanyStructurePK, cloneToCompanyStructurePK);
    }

    /**
     * Saves the deposit specified by the depositsPK to the database (for the change to the deposit type)
     * @param depositsPK
     * @param depositType
     * @param operator
     * @return
     */
    public String saveDeposit(Long depositsPK, String depositType, String operator)
    {
        String message = new Deposits().save(depositsPK, depositType, operator);

        return message;
    }

    /**
         * break the parent-child relation in all the related tables
         * delete the chlildren AgentHierarchyAllocation & AgentSnapshot(tree)
         * Delete the entity AgentHierarchy using Hibernate
    */
    public void deleteContractAgentHierarchy(AgentHierarchy agentHierarchyCurr) throws EDITCaseException
    {
        AgentHierarchy agentHierarchy = AgentHierarchy.findByPK(agentHierarchyCurr.getAgentHierarchyPK());

        try
        {
            SessionHelper.beginTransaction(SessionHelper.EDITSOLUTIONS);

            agentHierarchy.deleteAgentSnapshots();
            agentHierarchy.deleteAgentHierarchyAllocations();
            agentHierarchy.hDelete();            

            SessionHelper.commitTransaction(SessionHelper.EDITSOLUTIONS);
        }
        catch (Exception e)
        {
            System.out.println(e);
            e.printStackTrace();

            SessionHelper.rollbackTransaction(SessionHelper.EDITSOLUTIONS);
            throw new EDITCaseException(e.getMessage());
        }
        finally
        {
            SessionHelper.clearSessions();
        }
    }


}
