/*
 * User: unknown
 * Date: Sep 24, 2001
 * Time: unknown
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */

package contract.dm;

import contract.dm.dao.DAOFactory;
import contract.dm.composer.SegmentComposer;
import contract.*;

import edit.common.*;
import edit.common.exceptions.EDITContractException;
import edit.common.vo.*;
import edit.common.Change;
import edit.common.exceptions.EDITLockException;

import edit.services.db.CRUD;
import edit.services.db.CRUDFactory;
import edit.services.db.ConnectionFactory;
import edit.services.db.ElementLockManager;

import event.CommissionHistory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import fission.utility.Util;

/**
 * The StorageManager will handle the Object persistence.  There
 * are methods for adding, updating, deleting, and retrieving data
 * to the database.  The StorageManager will also establish and
 * maintain all database connections.
 *
 */
public class StorageManager implements Serializable {

// Member variables:

	private	long segmentPK                   = 0;

    private final String POOLNAME = ConnectionFactory.EDITSOLUTIONS_POOL;

    /**
     * StorageManager constructor.
     */
    public StorageManager() {

    }

    public void backupSegment(SegmentVO segmentVO) throws Exception {

        CRUD crud = null;

        try
        {
            crud = CRUDFactory.getSingleton().getCRUD(POOLNAME);

            crud.backupBaseSegmentVOAsBinary(segmentVO);
        }
        catch (Exception e)
        {
            System.out.println(e);

            e.printStackTrace();

            throw e;
        }
        finally
        {
            if (crud != null) crud.close();
        }
    }

    public SegmentVO restoreSegment(long segmentPK) throws Exception {

        CRUD crud = null;

        SegmentVO segmentVO = null;

        try
        {
            crud = CRUDFactory.getSingleton().getCRUD(POOLNAME);

            segmentVO = crud.restoreBaseSegmentFromBinary(segmentPK);
        }
        catch (Exception e)
        {
            System.out.println(e);

            e.printStackTrace();

            throw e;
        }
        finally
        {
            if (crud != null) crud.close();
        }

        return segmentVO;
    }

    public void deleteContractBackup(long segmentPK) throws Exception {

        CRUD crud = null;

        try
        {
            crud = CRUDFactory.getSingleton().getCRUD(POOLNAME);

            crud.deleteSegmentBackup(segmentPK);
        }
        catch (Exception e)
        {
            throw e;
        }
        finally
        {
            if (crud != null) crud.close();
        }
    }

    public void restoreSegment(SegmentVO segmentVO) throws Exception {

        CRUD crud = null;

        try {

            crud = CRUDFactory.getSingleton().getCRUD(POOLNAME);

            crud.createOrUpdateVOInDBRecursivelyFromRestore(segmentVO);
        }

        catch (Exception e) {

            System.out.println(e);

            e.printStackTrace();

            throw e;
        }
        finally
        {
            if (crud != null) crud.close();
        }
    }


    // NOTE this method was replaced with a new saveSegment that does not contain the hoakiness of adding/excluding other VOs
//    public void saveSegmentOnUnCommit(SegmentVO segmentVO) throws Exception {
//
//        CRUD crud = null;
//
//        try {
//
//            crud = CRUDFactory.getSingleton().getCRUD(POOLNAME);
//
//            crud.createOrUpdateVOInDBRecursively(segmentVO);
//        }
//
//        catch (Exception e) {
//
//            System.out.println(e);
//
//            e.printStackTrace();
//
//            throw e;
//        }
//        finally
//        {
//            if (crud != null) crud.close();
//        }
//    }

    public long saveSegment(Segment segment, String operator)
    {
        CRUD crud = null;

        long pk;

        try
        {
            crud = CRUDFactory.getSingleton().getCRUD(POOLNAME);

//            adjustPayorsForBilling(segment);

            Long segmentPK = segment.getSegmentPK();
            
            CommissionHistory[] adjustedCommissionHistories = adjustCommissionHistoriesForMissingAgentHierarchies((SegmentVO) segment.getVO());

            // Save the adjustedCommissionHistories [BEFORE] saving the Segment to avoid any constraint
            // violations between AgentSnapshot and CommissionHistory.
            for (int i = 0; i < adjustedCommissionHistories.length; i++)
            {
              CommissionHistoryVO currentAdjustedCommissionHistoryVO = (CommissionHistoryVO) adjustedCommissionHistories[i].getVO();
              
              crud.createOrUpdateVOInDB(currentAdjustedCommissionHistoryVO);
            }

            if (segmentPK != null) // Using the Hibenate PK, if any.
            {
                // Only check for product structure changes if the segment has been saved at least once before
                checkForProductStructureChange(segment);
            }

            //Use an exclusion list to stop deletion beyond the actual tables processed.
            //The segmentVO MUST contain all existing data because it will be synced.
            List voExclusionList = new ArrayList();
            voExclusionList.add(ContractSetupVO.class);
            voExclusionList.add(ClientSetupVO.class);
            voExclusionList.add(ProductStructureVO.class);
            voExclusionList.add(FilteredFundVO.class);
            voExclusionList.add(InvestmentAllocationOverrideVO.class);
            voExclusionList.add(WithholdingVO.class);
            voExclusionList.add(BucketVO.class);
            voExclusionList.add(ClientRoleVO.class);
            voExclusionList.add(ContractClientAllocationOvrdVO.class);
            voExclusionList.add(PlacedAgentVO.class);
            voExclusionList.add(CommissionProfileVO.class);
            voExclusionList.add(ContractTreatyVO.class);
            voExclusionList.add(InvestmentHistoryVO.class);
            

            pk = crud.createOrUpdateVOInDBRecursively((SegmentVO) segment.getVO(), voExclusionList);
        }
        catch (Exception e)
        {
            System.out.println(e);

            e.printStackTrace();

            throw new RuntimeException(e);
        }
        finally
        {
            if (crud != null) crud.close();

            crud = null;
        }

        return pk;
	}

    public long saveSegmentNonRecursively(Segment segment, boolean saveChangeHistory, String operator, boolean complexChangeFound)
    {
        CRUD crud = null;

        long pk;

        try
        {
            crud = CRUDFactory.getSingleton().getCRUD(POOLNAME);

            if (segment.getSegmentPK().longValue() != 0)
            {
                // Only check for product structure changes if the segment has been saved at least once before
                checkForProductStructureChange(segment);
            }

            pk = crud.createOrUpdateVOInDB((SegmentVO) segment.getVO());
        }
        catch (Exception e)
        {
            System.out.println(e);

            e.printStackTrace();

            throw new RuntimeException(e);
        }
        finally
        {
            if (crud != null) crud.close();

            crud = null;
        }

        return pk;
	}

    // NOTE this method was replaced with a new saveSegment that does not contain the hoakiness of adding/excluding other VOs
    /**
     * Persists the SegmentVO while excluding the ChangeHistoryVOs with the SegmentVO being supplied from the SegmentVO in persistence.
     * @param segmentVO
     * @return
     * @throws Exception
     */
//    public long saveSegment(SegmentVO segmentVO) throws Exception {
//
//        long segmentPK = segmentVO.getSegmentPK();
//
//        if (segmentPK != 0) {
//
//            addMissingVOs(segmentVO, segmentPK);
//        }
//
//        List voExclusionList = new ArrayList();
//
//        voExclusionList.add(ChangeHistoryVO.class);
//
//        if (! billingExists(segmentVO))
//        {
//            voExclusionList.add(BillingVO.class);
//            voExclusionList.add(BillLapseVO.class);
//        }
//
//        CRUD crud = null;
//
//        try {
//
//            crud = CRUDFactory.getSingleton().getCRUD(POOLNAME);
//
//            long pk = crud.createOrUpdateVOInDBRecursively(segmentVO, voExclusionList);
//
//            return pk;
//        }
//
//        catch (Exception e) {
//
//            System.out.println("contract.dm.StorageManager.saveSegment(): " + e.toString());
//            e.printStackTrace();
//
//            throw new Exception(e.toString());
//        }
//        finally
//        {
//            if (crud != null) crud.close();
//        }
//    }

    /**
     * Persists the MasterVO without children
     * @param masterVO
     * @return
     * @throws Exception
     */
//    public long saveMaster(MasterVO masterVO) throws Exception
//    {
//        CRUD crud = null;
//
//        try
//        {
//            crud = CRUDFactory.getSingleton().getCRUD(POOLNAME);
//
//            long pk = crud.createOrUpdateVOInDB(masterVO);
//
//            return pk;
//        }
//        catch (Exception e)
//        {
//            System.out.println("contract.dm.StorageManager.saveMaster(): " + e.toString());
//
//            e.printStackTrace();
//
//            throw new Exception(e.toString());
//        }
//        finally
//        {
//            if (crud != null) crud.close();
//        }
//    }

    public void saveChangeHistory(ChangeHistoryVO changeHistoryVO) throws Exception {

        CRUD crud = null;

        try
        {
            crud = CRUDFactory.getSingleton().getCRUD(POOLNAME);

            crud.createOrUpdateVOInDBRecursively(changeHistoryVO);
        }
        catch (Exception e)
        {
            System.out.println(e);

            e.printStackTrace();

            throw e;
        }
        finally
        {
            if (crud != null) crud.close();
        }
    }

    public void updateQuote(SegmentVO segmentVO) throws Exception {

        CRUD crud = null;

        List voExclusionList = null;

        voExclusionList = new ArrayList();

        try {

            crud = CRUDFactory.getSingleton().getCRUD(POOLNAME);

            voExclusionList.add(ContractSetupVO.class);

            crud.createOrUpdateVOInDBRecursively(segmentVO, voExclusionList);
        }
        catch (Exception e)
        {
            System.out.println(e);

            e.printStackTrace();

            throw e;
        }
        finally
        {
            if (crud != null) crud.close();
        }
    }

    public void commitSegment(SegmentVO segmentVO) throws Exception {

        CRUD crud = null;

        try {

            crud = CRUDFactory.getSingleton().getCRUD(POOLNAME);

            crud.createOrUpdateVOInDBRecursively(segmentVO);
        }
        catch (Exception e) {

            System.out.println("contract.dm.StorageManager.commitSegment(): " + e);
            
          System.out.println(e);

            e.printStackTrace();

            throw new Exception(e.toString());
        }
        finally
        {
            if (crud != null) crud.close();
        }
    }

    public void updateSegmentAfterBatch(SegmentVO segmentVO) throws Exception {

        CRUD crud = null;

        try {

            List exclusionVector = new ArrayList();

            // These were never retrieved as part of the original Lookup.
            exclusionVector.add(NoteReminderVO.class);

            crud = CRUDFactory.getSingleton().getCRUD(POOLNAME);

            crud.createOrUpdateVOInDBRecursively(segmentVO, exclusionVector);
        }

        catch (Exception e) {

            System.out.println(e);

            e.printStackTrace();

            throw e;
        }
        finally {

            if (crud != null) crud.close();
        }
    }

    public void deleteQuote(SegmentVO segmentVO) throws Exception {

        segmentPK = segmentVO.getSegmentPK();

        CRUD crud = null;

        try {

            crud = CRUDFactory.getSingleton().getCRUD(POOLNAME);

            crud.deleteVOFromDBRecursively(SegmentVO.class, segmentPK);
        }
        catch (Exception e)
        {
            System.out.println(e);

            e.printStackTrace();

            throw e;
        }
        finally
        {
            if (crud != null) crud.close();
        }
    }

    public void deleteSegment(SegmentVO segmentVO) throws Exception {

        segmentPK = segmentVO.getSegmentPK();

        CRUD crud = null;

        try {

            crud = CRUDFactory.getSingleton().getCRUD(POOLNAME);

            crud.deleteVOFromDBRecursively(SegmentVO.class, segmentPK);
        }

        catch (Exception e)
        {
            System.out.println(e);

            e.printStackTrace();

            throw e;
        }
        finally
        {
            if (crud != null) crud.close();
        }
    }

    public ElementLockVO lockElement(long elementPK,
                                      String username)
                                    throws EDITLockException {

        ElementLockManager elementLockManager = new ElementLockManager();

        return elementLockManager.lockElement(elementPK, username);
    }

    public int unlockElement(long lockTablePK) {

        ElementLockManager elementLockManager = new ElementLockManager();

        return elementLockManager.unlockElement(lockTablePK);
    }

    // NOTE: this method was replaced with a new saveSegment which consolidates multiple save methods
//    public long saveSegmentAndHistory(ContractVO contractVO, String operator) throws Exception
//    {
//        SegmentVO segmentVO = (contractVO.getSegmentVO());
//
//        segmentPK = segmentVO.getSegmentPK();
//
//
//        //this fixes losing the investmentAllocationOverrides on a change of a contract
//        InvestmentVO[] investmentVOs = segmentVO.getInvestmentVO();
//        for (int i = 0; i < investmentVOs.length; i++)
//        {
//            long investmentFK = investmentVOs[i].getInvestmentPK();
//            InvestmentAllocationVO[] investmentAllocationVOs = DAOFactory.getInvestmentAllocationDAO().findByInvestmentFKAndOverrideStatus(investmentFK, "O", false, null);
//
//            if (investmentAllocationVOs != null)
//            {
//                for (int j = 0; j < investmentAllocationVOs.length; j++)
//                {
//                    investmentVOs[i].addInvestmentAllocationVO(investmentAllocationVOs[j]);
//                }
//            }
//        }
//
//        CRUD crud = null;
//
//        try
//        {
//            List exclusionVector = new ArrayList();
//            exclusionVector.add(ChangeHistoryVO.class);
//
//            if (! setSegmentFKExists(segmentVO))
//            {
//                exclusionVector.add(BillingVO.class);
//                exclusionVector.add(BillLapseVO.class);
//            }
//
//            crud = CRUDFactory.getSingleton().getCRUD(POOLNAME);
//
//            crud.setShouldTrackChangeHistory(true);
//
//            long pk = crud.createOrUpdateVOInDBRecursively(segmentVO, exclusionVector);
//
//            //get all the ChangeVOs from crud
//            Change[] changeVOs = crud.getChangeVOs();
//
//            //  Reset the tracking status
//            crud.setShouldTrackChangeHistory(false);
//
//            if (changeVOs != null) {
//
//                //if an Allocation or ClientRelationship have been added bypass all detail before and after, until a different
//                //table name found
//                for (int i = 0; i < changeVOs.length; i++) {
//
//                    Object afterVO   = changeVOs[i].getAfterVO();
//                    Object beforeVO  = changeVOs[i].getBeforeVO();
//                    int action       = changeVOs[i].getStatus();
//                    String fieldName = changeVOs[i].getFieldName();
//                    String tableName = null;
//                    long modifiedRecordFK = 0;
//
//                    if (beforeVO != null) {
//                        tableName = changeVOs[i].getBeforeTableName();
//                        modifiedRecordFK = ((Long) beforeVO.getClass().
//                                             getMethod("get" + tableName + "PK", null).
//                                              invoke(beforeVO, null)).longValue();
//                    }
//                    else {
//
//                        tableName = changeVOs[i].getAfterTableName();
//                        modifiedRecordFK = ((Long) afterVO.getClass().
//                                             getMethod("get" + tableName + "PK", null).
//                                              invoke(afterVO, null)).longValue();
//                    }
//
//                    if (tableName.equalsIgnoreCase("Investment") &&  action == Change.ADDED) {
//
//                        if (fieldName.equalsIgnoreCase("FilteredFundFK")) {
//
//                            processChangeHistory(changeVOs[i], tableName,
//                                                  fieldName, modifiedRecordFK,
//                                                   action, operator, segmentPK, crud);
//                        }
//                    }
//
//                    else if (tableName.equalsIgnoreCase("ContractClient") &&
//                             action == Change.ADDED) {
//
//                        if (fieldName.equalsIgnoreCase("ClientRoleFK")) {
//
//                            processChangeHistory(changeVOs[i], tableName,
//                                                  fieldName, modifiedRecordFK,
//                                                   action, operator, segmentPK, crud);
//                        }
//                    }
//
//                    else if ((tableName.equalsIgnoreCase("AgentHierarchy") ||
//                             tableName.equalsIgnoreCase("AgentSnapshot")) &&
//                             beforeVO == null)
//                    {
//                        //do nothing - do not write these records to history
//                    }
//
//                    else if (tableName.equalsIgnoreCase("Segment"))
//                    {
//                        if (fieldName.equalsIgnoreCase("SegmentStatusCT") &&
//                             (beforeVO.getClass().getMethod("get" + tableName + "FK", null).
//                                         invoke(beforeVO, null)) != null  &&
//                              (changeVOs[i].getBeforeValue().equalsIgnoreCase("Pending")))
//                        {
//                            //do nothing - do not write this reocrd to history
//                        }
//                    }
//
//                    else {
//
//                        processChangeHistory(changeVOs[i], tableName,
//                                              fieldName, modifiedRecordFK,
//                                               action, operator, segmentPK, crud);
//                    }
//                }
//            }
//
//            return pk;
//        }
//        catch(Exception e)
//        {
//            System.out.println("contract.dm.StorageManager.saveSegmentAndHistory(): " + e.toString());
//            e.printStackTrace();
//
//            throw new Exception(e.toString());
//		}
//        finally
//        {
//            if (crud != null) crud.close();
//        }
//	}

    /**
     * The changeVOs that are valid are written to the ChangeHistory table
     * @param change
     * @param tableName
     * @param fieldName
     * @param modifiedRecordFK
     * @param action
     * @param operator
     * @param segmentPK
     * @param crud
     * @param complexChangeFound
     * @throws Exception
     */
    public void processChangeHistory(Change change, String tableName, String fieldName, long modifiedRecordFK,
                                      int action, String operator, long segmentPK, CRUD crud, boolean complexChangeFound)  throws Exception {

        EDITDate processDate = new EDITDate();
        String beforeValue = change.getBeforeValue();
        String afterValue  = change.getAfterValue();

        //exception code for conversion data
        boolean validChangeVO = false;
        if (beforeValue != null)
        {
            validChangeVO = checkChangeVO(beforeValue, afterValue);
            if (complexChangeFound && fieldName.startsWith("Pending"))
            {
                validChangeVO = false;
            }
        }

        if (validChangeVO)
        {
            if (!((beforeValue == null || beforeValue.equals("")) && (afterValue == null || afterValue.equals(""))))

            {
                //No change records will be generated for the NoteReminder table and for fields of maintDateTime and operator
                if (!fieldName.equalsIgnoreCase("MaintDateTime") &&
                    !fieldName.equalsIgnoreCase("Operator") &&
                    !tableName.equalsIgnoreCase("NoteReminder"))  {

                    ChangeHistoryVO changeHistoryVO = new ChangeHistoryVO();
                    changeHistoryVO.setTableName(tableName);
                    changeHistoryVO.setFieldName(fieldName);
                    changeHistoryVO.setEffectiveDate(processDate.getFormattedDate());
                    changeHistoryVO.setProcessDate(processDate.getFormattedDate());
                    changeHistoryVO.setOperator(operator);
                    String maintDateTime = new EDITDateTime().toString();
                    changeHistoryVO.setMaintDateTime(maintDateTime);
                    changeHistoryVO.setChangeHistoryPK(0);
//                    changeHistoryVO.setSegmentFK(segmentPK);
                    changeHistoryVO.setModifiedRecordFK(modifiedRecordFK);

                    //adjust before and after field for CT values
                    if (fieldName.endsWith("CT")) {

                        if (beforeValue == null || beforeValue.equals("0")) {

                            beforeValue = "";
                        }

                        if (afterValue == null || afterValue.equals("0")) {

                            afterValue = "";
                        }
                    }

                    if (action == Change.CHANGED) {

                        changeHistoryVO.setBeforeValue(beforeValue);
                        changeHistoryVO.setAfterValue(afterValue);
                    }

                    //Add and Delete processing fall through here, do special process for fund or relationship adds
                    if (action == Change.ADDED)   {

                        changeHistoryVO.setBeforeValue("");

                        changeHistoryVO.setAfterValue(afterValue);
                    }

                    else if (action == Change.DELETED) {

                        changeHistoryVO.setAfterValue("");
                        changeHistoryVO.setBeforeValue(beforeValue);
                    }

                    crud.createOrUpdateVOInDB(changeHistoryVO);
                }
            }
        }
    }
    /**
     * Eliminate the different zero representations from being valid changes.
     * @param beforeValue
     * @param afterValue
     * @return
     */

    private boolean checkChangeVO(String beforeValue, String afterValue)
    {
        if (Util.isANumber(beforeValue))
        {
            EDITBigDecimal ebdNumber = new EDITBigDecimal(beforeValue);
            if (ebdNumber.isEQ("0") && afterValue == null)
            {
                return false;
            }
            else if (afterValue != null && Util.isANumber(afterValue))
            {
                EDITBigDecimal afterEBDNumber = new EDITBigDecimal(afterValue);
                if (ebdNumber.isEQ(afterEBDNumber))
                {
                    return false;
                }
            }
            else
            {
                return true;
            }
        }
        else
        {
            return true;
        }
        return false;
    }

    public long saveBucketVO(BucketVO bucketVO) throws Exception
    {
        CRUD crud = null;

        long bucketPK = 0;

        try
        {
            crud = CRUDFactory.getSingleton().getCRUD(ConnectionFactory.EDITSOLUTIONS_POOL);

            bucketPK = crud.createOrUpdateVOInDB(bucketVO);
        }
        catch (Exception e)
        {
            System.out.println(e);

            e.printStackTrace();

            throw e;
        }
        finally
        {
            if (crud != null) crud.close();
        }

        return bucketPK;
    }

    public long saveInvestmentVO(InvestmentVO investmentVO) {

        CRUD crud = null;

        long investmentPK = 0;

        try {

            crud = CRUDFactory.getSingleton().getCRUD(ConnectionFactory.EDITSOLUTIONS_POOL);

            investmentPK = crud.createOrUpdateVOInDB(investmentVO);
        }
        catch (Exception e) {

            System.out.println(e);

            e.printStackTrace();

            throw new RuntimeException(e);
        }
        finally {

            if (crud != null) crud.close();
        }

        return investmentPK;
    }

    public long saveRequirementVO(RequirementVO requirementVO) throws Exception {

        CRUD crud = null;

        long requirementPK = 0;

        try {

            crud = CRUDFactory.getSingleton().getCRUD(ConnectionFactory.EDITSOLUTIONS_POOL);

            requirementPK = crud.createOrUpdateVOInDB(requirementVO);
        }
        catch (Exception e) {

            System.out.println(e);

            e.printStackTrace();

            throw e;
        }
        finally {

            if (crud != null) crud.close();
        }

        return requirementPK;
    }

    public long saveInvestmentAllocationVO(InvestmentAllocationVO investmentAllocationVO)
    {
        CRUD crud = null;

        long investmentAllocationPK = 0;

        try
        {
            crud = CRUDFactory.getSingleton().getCRUD(ConnectionFactory.EDITSOLUTIONS_POOL);

            investmentAllocationPK = crud.createOrUpdateVOInDB(investmentAllocationVO);
        }
        catch (Exception e)
        {
            System.out.println(e);

            e.printStackTrace();

            throw new RuntimeException(e);
        }
        finally
        {
            if (crud != null) crud.close();
        }

        return investmentAllocationPK;
    }

//      No longer being used now that we have a ContractClient CRUDEntity
//    public long saveContractClientVO(ContractClientVO contractClientVO) throws Exception {
//
//        CRUD crud = null;
//
//        long contractClientPK = 0;
//
//        try {
//
//            crud = CRUDFactory.getSingleton().getCRUD(ConnectionFactory.EDITSOLUTIONS_POOL);
//
//            contractClientPK = crud.createOrUpdateVOInDB(contractClientVO);
//        }
//        catch (Exception e) {
//
//            System.out.println(e);
//
//            e.printStackTrace();
//
//            throw e;
//        }
//        finally {
//
//            if (crud != null) crud.close();
//        }
//
//        return contractClientPK;
//    }

    public long saveContractClientAllocationVO(ContractClientAllocationVO contractClientAllocationVO) throws Exception
    {
        CRUD crud = null;

        long contractClientAllocationPK = 0;

        try
        {
            crud = CRUDFactory.getSingleton().getCRUD(ConnectionFactory.EDITSOLUTIONS_POOL);

            contractClientAllocationPK = crud.createOrUpdateVOInDB(contractClientAllocationVO);
        }
        catch (Exception e)
        {
            System.out.println(e);

            e.printStackTrace();

            throw e;
        }
        finally
        {
            if (crud != null) crud.close();
        }

        return contractClientAllocationPK;
    }

    public long saveWithholdingVO(WithholdingVO withholdingVO) throws Exception {

        CRUD crud = null;

        long withholdingPK = 0;

        try {

            crud = CRUDFactory.getSingleton().getCRUD(ConnectionFactory.EDITSOLUTIONS_POOL);

            withholdingPK = crud.createOrUpdateVOInDB(withholdingVO);
        }
        catch (Exception e) {

            System.out.println(e);

            e.printStackTrace();

            throw e;
        }
        finally {

            if (crud != null) crud.close();
        }

        return withholdingPK;
    }

    public void attachRequirementsToCompanyStructure(long productStructurePK,
                                                      long[] requirementPKs) throws Exception {

        CRUD crud = null;

        try {

            crud = CRUDFactory.getSingleton().getCRUD(ConnectionFactory.EDITSOLUTIONS_POOL);

            for (int i = 0; i < requirementPKs.length; i++) {

                FilteredRequirementVO[] filteredRequirementVOs =
                        DAOFactory.getFilteredRequirementDAO().
                         findProductStructureFKAndRequirementPK(productStructurePK,
                                                                 requirementPKs[i], false, null);

                if (filteredRequirementVOs == null) {

                    FilteredRequirementVO filteredRequirementVO = new FilteredRequirementVO();
                    filteredRequirementVO.setProductStructureFK(productStructurePK);
                    filteredRequirementVO.setRequirementFK(requirementPKs[i]);

                    crud.createOrUpdateVOInDB(filteredRequirementVO);
                }
            }
        }
        catch (Exception e) {

          System.out.println(e);

            e.printStackTrace();  //To change body of catch statement use Options | File Templates.
            throw e;
        }
        finally {

            if (crud != null) crud.close();
        }
    }

    public void detachRequirementsFromCompanyStructure(long productStructurePK,
                                                        long[] requirementPKs)
                                                      throws Exception {

        CRUD crud = null;

        try {

            crud = CRUDFactory.getSingleton().getCRUD(ConnectionFactory.EDITSOLUTIONS_POOL);

            for (int i = 0; i < requirementPKs.length; i++) {

                FilteredRequirementVO[] filteredRequirementVOs = DAOFactory.getFilteredRequirementDAO().findProductStructureFKAndRequirementPK(productStructurePK, requirementPKs[i], false, null);

                if (filteredRequirementVOs != null) {

                    crud.deleteVOFromDB(FilteredRequirementVO.class, filteredRequirementVOs[0].getFilteredRequirementPK());
                }
            }
        }
        catch (Exception e) {

          System.out.println(e);

            e.printStackTrace();  //To change body of catch statement use Options | File Templates.

            throw e;
        }
        finally {

            if (crud != null) crud.close();
        }
    }

//    /**
//     * It is possible, although not preferred, that the client is not supplying billing information at this time. We
//     * don't want the DB to synchronise with this omission and delete any billing.
//     * @param segmentVO
//     * @return
//     */
//    private boolean billingExists(SegmentVO segmentVO)
//    {
//        boolean billingExists = false;
//
//        ContractClientVO[] contractClientVOs = segmentVO.getContractClientVO();
//
//        for (int i = 0; i < contractClientVOs.length; i++)
//        {
//            if (contractClientVOs[i].getBillLapseVOCount() > 0)
//            {
//                billingExists = true;
//
//                break;
//            }
//        }
//
//        return billingExists;
//    }

    /**
     * Determine if a changeVO will become a change history record
     * @param changeVOs
     * @param crud
     * @param operator
     * @param segmentPK
     * @param complexChangeFound
     * @throws Exception
     */
    private void saveChangeHistories(Change[] changeVOs, CRUD crud, String operator, long segmentPK, boolean complexChangeFound) throws Exception
    {
        if (changeVOs != null)
        {
            //if an Allocation or ClientRelationship have been added bypass all detail before and after, until a different
            //table name found
            for (int i = 0; i < changeVOs.length; i++)
            {
                Object afterVO   = changeVOs[i].getAfterVO();
                Object beforeVO  = changeVOs[i].getBeforeVO();
                int action       = changeVOs[i].getStatus();
                String fieldName = changeVOs[i].getFieldName();
                String tableName = null;
                long modifiedRecordFK = 0;

                try
                {
                    if (beforeVO != null)
                    {
                        tableName = changeVOs[i].getBeforeTableName();
                        modifiedRecordFK = ((Long) beforeVO.getClass().
                                getMethod("get" + tableName + "PK", null).invoke(beforeVO, null)).longValue();
                    }
                    else
                    {
                        tableName = changeVOs[i].getAfterTableName();
                        modifiedRecordFK = ((Long) afterVO.getClass().
                                getMethod("get" + tableName + "PK", null).invoke(afterVO, null)).longValue();
                    }

                    if (tableName.equalsIgnoreCase("Investment") &&  action == Change.ADDED)
                    {
                        if (fieldName.equalsIgnoreCase("FilteredFundFK"))
                        {
                            processChangeHistory(changeVOs[i], tableName, fieldName, modifiedRecordFK,
                                                   action, operator, segmentPK, crud, complexChangeFound);
                        }
                    }
                    else if (tableName.equalsIgnoreCase("ContractClient") && action == Change.ADDED)
                    {
                        if (fieldName.equalsIgnoreCase("ClientRoleFK"))
                        {
                            processChangeHistory(changeVOs[i], tableName, fieldName, modifiedRecordFK,
                                                   action, operator, segmentPK, crud, complexChangeFound);
                        }
                    }
                    else if ((tableName.equalsIgnoreCase("AgentHierarchy") || tableName.equalsIgnoreCase("AgentSnapshot"))
                            && beforeVO == null)
                    {
                        //do nothing - do not write these records to history
                    }
                    else if (tableName.equalsIgnoreCase("Segment"))
                    {
                        if (fieldName.equalsIgnoreCase("SegmentStatusCT") &&
                            (beforeVO != null &&
                             (beforeVO.getClass().getMethod("get" + tableName + "FK", null).invoke(beforeVO, null)) != null &&
                             (changeVOs[i].getBeforeValue().equalsIgnoreCase("Pending"))))
                        {
                            //do nothing - do not write this record to history
                        }
                        else
                        {
                            processChangeHistory(changeVOs[i], tableName, fieldName, modifiedRecordFK,
                                                   action, operator, segmentPK, crud, complexChangeFound);
                        }
                    }
                    else
                    {
                        processChangeHistory(changeVOs[i], tableName, fieldName, modifiedRecordFK,
                                               action, operator, segmentPK, crud, complexChangeFound);
                    }
                }
                catch (Exception e)
                {
                  System.out.println(e);

                    e.printStackTrace();  //To change body of catch statement use Options | File Templates.

                    throw new Exception(e.toString());
                }
            }
        }
    }

//  COMMENTED OUT. NO LONGER USING BILLLAPSE IN THIS WAY BUT DON'T WANT TO LOSE LOGIC JUST YET
//    /**
//     * Modifies the ContractClient and its billing information appropriately.
//     *
//     * Checks each ContractClient to see if it is a Payor.  If so, checks to see if a BillLapse has been provided.
//     * If the PolicyGroup is of type Case, the Billing already exists in persistence. Otherwise, the Billing has to
//     * be provided and is persisted in this method.  Also sets the FKs appropriately
//     *
//     * For each Payor there is one BillLapse and one Billing. A Payor is allowed to not have a BillLapse because it can
//     * be added at a later time.  However, if a BillLapse is provided, a Billing must also be provided either in the VO
//     * structure or in the database (for PolicyGroups of type Case)
//     *
//     * NOTE: most of the logic in this method is repeated in ContractComponent.saveContractClient().  At the time of
//     * development, these 2 methods were not easily put into one, hence the duplication
//     *
//     * @param segmentVO
//     *
//     * @throws EDITContractException            if Billing is not provided either in the database or in the VO structure
//     */
//    public void adjustPayorsForBilling(Segment segment) throws EDITContractException
//    {
//        SegmentVO segmentVO = (SegmentVO) segment.getVO();
//
//        ContractClientVO[] contractClientVOs = segmentVO.getContractClientVO();
//
//        for (int i = 0; i < segmentVO.getContractClientVOCount(); i++)
//        {
//            ContractClient contractClient = new ContractClient(contractClientVOs[i]);
//
//            if (contractClient.isPayor())
//            {
//                BillLapseVO[] billLapseVOs = contractClientVOs[i].getBillLapseVO();
//
//                if (contractClientVOs[i].getBillLapseVOCount() > 0)         // Have BillLapse
//                {
//                    BillLapse billLapse = new BillLapse(billLapseVOs[0]);   // There should only be one BillLapse per Payor
//
//                    BillingVO billingVO = (BillingVO) billLapse.getParentVO(BillingVO.class);
//
//                    if (billLapse.getBillingFK() == 0 && billingVO == null )
//                    {
//                        //  This is a new BillLapse and Billing was not provided
//                        //  If this Segment belongs to a Case, check to see if the Case has a Billing
//                        if (segment.belongsToACase())
//                        {
//                            PolicyGroup policyGroup = PolicyGroup.findByPK(segment.getPolicyGroupFK());
//                            Case foundCase = Case.findByPolicyGroupFK(policyGroup.getPolicyGroupPK());
//                            if (! foundCase.hasBilling())
//                            {
//                                // No Billing at Case level either, it must be provided
//                                throw new EDITContractException("Must provide Billing for a ContractClient of role type Payor " +
//                                    "at the Segment or the Case level: ContractClientPK = " + contractClient.getPK());
//                            }
//                        }
//                        else
//                        {
//                            // Billing must be provided
//                            throw new EDITContractException("Must provide Billing for a ContractClient of role type Payor " +
//                                    "at the Segment: ContractClientPK = " + contractClient.getPK());
//                        }
//                    }
//                    else
//                    {
//                        //  Billing was provided, be sure to save it
//                        Billing billing = new Billing(billingVO);
//
//                        billing.setSegmentFK(segment.getSegmentPK());
//
//                        billing.save();
//
//                        billLapse.setBillingFK(billing.getBillingPK());
//                    }
//                }
//            }
//        }
//    }

    /**
     * Check for ProductStructure change, if not equal to the existing database contract, do maintenance on the
     * ContractRequirements.  The old ones must be deleted and the new ones added to the contract.
     * @param segmentVO
     * @throws Exception
     */
    public void checkForProductStructureChange(Segment segment) throws Exception
    {
        Long productStructureID = segment.getProductStructureFK();

        List inclusionList = new ArrayList();

        SegmentComposer segmentComposer = new SegmentComposer(inclusionList);

        Long segmentPK = segment.getSegmentPK();

        SegmentVO existingSegmentVO = segmentComposer.compose(segmentPK.longValue());

        if (productStructureID.longValue() != existingSegmentVO.getProductStructureFK())
        {
            deleteContractRequirements(segmentPK.longValue());
        }
    }

    /**
     * Product structure change dictates the existing requirements get deleted.
     * @param segmentPK
     * @throws Exception
     */
    private void deleteContractRequirements(long segmentPK) throws Exception
    {
        List voExclusionList = new ArrayList();
        voExclusionList.add(RequirementVO.class);

        ContractRequirementVO[] contractRequirementVOs = DAOFactory.getContractRequirementDAO().findBySegmentFK(segmentPK, true, voExclusionList);

        if (contractRequirementVOs != null)
        {
            for (int i = 0; i < contractRequirementVOs.length; i++)
            {
                ContractRequirement contractRequirement = new ContractRequirement(contractRequirementVOs[i]);
                contractRequirement.delete();
            }
        }
    }

  /**
   * It is possible that an AgentHierarchy(s) has been deleted. In such a
   * case, some of the AgentSnapshots of its parent AgentHierarchy [might]
   * have associations with CommissionHistories [AgentSnapshot->CommissionHitory(s)].
   * Before saving the specified SegmentVO, any corresponding CommissionHistories 
   * need to have their AgentSnapshotFK nulled otherwise a constraint violation
   * will occur.
   * @param segmentVO
   */
  public CommissionHistory[] adjustCommissionHistoriesForMissingAgentHierarchies(SegmentVO segmentVO)
  {
    AgentHierarchy[] dbAgentHierarchies = AgentHierarchy.findBySegmentPK(segmentVO.getSegmentPK());
    
    AgentHierarchyVO[] memoryAgentHierarchies = segmentVO.getAgentHierarchyVO();
    
    List commissionHistories = new ArrayList();

    if (dbAgentHierarchies != null)
    {
      for (int i = 0; i < dbAgentHierarchies.length; i++)
      {
        boolean agentHierarchyWasDeleted = true;

        long currentDBAgentHierarchyPK = dbAgentHierarchies[i].getAgentHierarchyPK().longValue();

        // Flag any dbAgentHierarchy that is no longer in-memory (e.g. deleted)
        for (int j = 0; j < memoryAgentHierarchies.length; j++)
        {
          long currentMemoryAgentHierarchyPK = memoryAgentHierarchies[j].getAgentHierarchyPK();

          if (currentDBAgentHierarchyPK == currentMemoryAgentHierarchyPK)
          {
            agentHierarchyWasDeleted = false;

            break;
          }
        }

        // For the deleted AgentHierarchy, drill-down to find the CommissionHistories
        // which must have their AgentSnapshot associations nulled-out.
        if (agentHierarchyWasDeleted)
        {
          AgentSnapshot[] dbAgentSnapshots = AgentSnapshot.findBy_AgentHierarchyPK(currentDBAgentHierarchyPK);

          if (dbAgentSnapshots != null)
          {
            for (int j = 0; j < dbAgentSnapshots.length; j++)
            {
              long currentDBAgentSnapshotPK = dbAgentSnapshots[j].getAgentSnapshotPK().longValue();
  
              CommissionHistory[] dbCommissionHistories = CommissionHistory.findBy_AgentSnapshotPK(currentDBAgentSnapshotPK);
  
              if (dbCommissionHistories != null)
              {
                for (int k = 0; k < dbCommissionHistories.length; k++)
                {
                  CommissionHistory currentDBCommissionHistory = dbCommissionHistories[k];
    
                  currentDBCommissionHistory.setAgentSnapshotFK(null);
    
                  commissionHistories.add(currentDBCommissionHistory);
                }
              }
            }
          }
        }

        // Reset for next loop
        agentHierarchyWasDeleted = true;
      }
    }
    
    return (CommissionHistory[]) commissionHistories.toArray(new CommissionHistory[commissionHistories.size()]);
  }
}
