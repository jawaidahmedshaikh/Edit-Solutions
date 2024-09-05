/*
 * User: unknown
 * Date: Oct 3, 2001
 * Time: unknown
 *
 * (c) 2000-2008 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */

package contract.business;

import edit.common.vo.*;
import edit.common.exceptions.*;
import edit.common.exceptions.EDITValidationException;
import edit.common.*;
import edit.services.component.ICRUD;
import edit.services.component.ILockableElement;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import engine.sp.SPException;
import contract.*;
import contract.ui.ImportNewBusinessResponse;


/**
 * The Contract request Controller Interface
 */
public interface Contract extends ICRUD, ILockableElement
{
    public void backupSegment(SegmentVO segmentVO) throws Exception;

    public SegmentVO restoreSegment(long segmentPK) throws Exception;

    public void deleteSegmentFromBackup(long segmentId) throws Exception;
    
    public void restoreSegmentFromBackup(SegmentVO segmentVO) throws Exception;

    /**
     * Persists the Segment
     *
     * @param contractVO                        set selectedRiderPK 
     * @param segmentVO                         Segment to be saved
     * @param saveChangeHistory                 True if want change history to be saved (used if changes were made to
     *                                              the segment and want those changes tracked)
     * @param operator                          Identifier of the operator for saving change history, NULL if not saving
     *                                              change history
     *
     * @return primary key of the saved Segment
     *
     * @throws EDITContractException
     */
    public long saveSegment(ContractVO contractVO, SegmentVO segmentVO, String conversonValue, boolean saveChangeHistory, String operator) throws EDITContractException;
    /**
     * Persists the Segment
     *
     * @param segmentVO                         Segment to be saved
     * @param saveChangeHistory                 True if want change history to be saved (used if changes were made to
     *                                              the segment and want those changes tracked)
     * @param operator                          Identifier of the operator for saving change history, NULL if not saving
     *                                              change history
     *
     * @return primary key of the saved Segment
     *
     * @throws EDITContractException
     */
    public long saveSegment(SegmentVO segmentVO, String conversonValue, boolean saveChangeHistory, String operator) throws EDITContractException;

    /**
     * Persists a BillSchedule
     * @param billScheduleVO
     * @return
     * @throws EDITContractException
     */
    public Long saveBillSchedule(BillScheduleVO billScheduleVO) throws EDITContractException;

    /**
     * Persists the Segment
     *
     * @param segmentVO                           Segment to be saved
     * @param conversionValue   - conversionValue from to be used on BillingChange

     * @return primary key of the saved Segment
     *
     * @throws EDITContractException
     */
    public long saveSegmentForNBWithHibernate(Segment segment, String conversionValue) throws EDITContractException;

    /**
     * Persists the Segment
     *
     * @param segmentVO                           Segment to be saved
     * @param saveChangeHistory                 True if want change history to be saved (used if changes were made to
     *                                              the segment and want those changes tracked)
     * @param operator                          Identifier of the operator for saving change history, NULL if not saving
     *                                              change history
     *
     * @return primary key of the saved Segment
     *
     * @throws EDITContractException
     */
    public long saveSegmentForNewBusiness(SegmentVO segmentVO, boolean saveChangeHistory, String operator) throws EDITContractException;


    /**
     * Saves the segment non-recursively
     * @param segmentVO
     * @param saveChangeHistory
     * @param operator
     * @return
     * @throws EDITContractException
     */
    public long saveSegmentNonRecursively(SegmentVO segmentVO, boolean saveChangeHistory, String operator) throws EDITContractException;

//    public void saveSegmentOnUnCommit(SegmentVO segmentVO) throws Exception;

    public void commitSegment(SegmentVO segmentVO) throws Exception;

    public void updateSegmentAfterBatch(SegmentVO segmentVO) throws Exception;

	public void deleteSegment(SegmentVO segmentVO) throws Exception;

	public void deleteQuote(SegmentVO segmentVO) throws Exception;

	public String autoGenerateContractNumber(String businessContract) throws Exception;

    public void saveChangeHistory(ChangeHistoryVO changeHistoryVO) throws Exception;

//    public long saveSegmentAndHistory(ContractVO contractVO, String operator) throws Exception;

    public long saveBucketVO(BucketVO bucketVO) throws Exception;

    public long saveInvestmentAllocationVO(InvestmentAllocationVO investmentAllocationVO) throws Exception;

    public long saveInvestmentVO(InvestmentVO investmentVO) throws Exception;

    public long saveRequirementVO(RequirementVO requirementVO) throws Exception;

    /**
     * Persists a ContractClient.  If the ContractClient's role is a Payor, it will also save the BillLapse.  If the
     * Master is of type Group, the Billing already exists and won't be used, otherwise, the Billing will also be saved.
     *
     * For each Payor there is one BillLapse and one Billing.
     *
     * @param contractClientVO                  ContractClient to be saved
     * @param billLapseVO                       BillLapse to be saved if ContractClient's role is Payor
     * @param billingVO                         Billing to be saved if ContractClient's role is Payor and Master is
     *                                          of type List or Individual (i.e. not Case)
     *
     * @return  saved ContractClient's primary key
     *
     * @throws EDITContractException            if Billing is not provided either in the database or in the VO structure

     */
    public long saveContractClientVO(ContractClientVO contractClientVO) throws EDITContractException;

    public long saveContractClientAllocationVO(ContractClientAllocationVO contractClientAllocationVO) throws Exception;

    public long saveWithholdingVO(WithholdingVO withholdingVO) throws Exception;

    public void attachRequirementsToProductStructure(long productStructurePK, long[] requirementPKs) throws Exception;

    public void detachRequirementsFromProductStructure(long productStructurePK, long[] requirementPKs) throws Exception;

    public abstract long getNextAvailableKey();

    public String processBank(String companyName, String contractId) throws Exception;

	public BatchStatusVO[] getBatchStatus() throws Exception;

    public void updateBatchList(String completedBatchId)throws Exception;

    public void restoreContract(long segmentPK) throws Exception;

    /**
     * Saves or updates a Billing entry.
     * @param billingVO
     */
//    public void saveBilling(BillingVO billingVO) throws EDITContractException;

    /**
     * Saves or updates a BillLapse entry.
     * @param billLapseVO
     * @throws edit.common.exceptions.EDITContractException If this entry does not have associations for BillLapse, ContractClient, and Master entities.
     */
//    public void saveBillLapse(BillLapseVO billLapseVO) throws EDITContractException;

    public void deleteChangeHistory(long segmentPK);

    /**
     * Import an Excel workbook and save as new business.  Each row in the workbook represents a new contract.
     *
     * <P>
     * NOTE:  The Excel file has been read and stored as a workbook by the tran.  This is because the ui layer has access
     * to the file but the back end may not.
     *
     * @param hssfWorkbook              Workbook to be processed (has already been read from the file into the workbook
     *                                  via the tran)
     * @param productStructureId    The ProductStructure to be used when importing the new contracts.
     *
     * @param operator              The operator id for the user importing new business
     * @return Array of ImportNewBusinessResponse Objects denoting whether each save was successful or not, and if
     *         the save failed for duplicate client(s), the user will have a chance to correct (again)
     *
     * @throws EDITContractException if there is a problem saving the new business information
     */
    public ImportNewBusinessResponse[] importNewBusiness(HSSFWorkbook hssfWorkbook, 
                                                         String productStructureId,
                                                         String operator) throws EDITContractException;

    /**
     * Import an Excel workbook for the given importValues(error overrides) and save as new business.  Each row
     * in the workbook represents a new contract.  This method is only executed after the initial import has been run -
     * this method is for error corrections ONLY.
     * @param hssfWorkbook     Workbook to be processes (has already been read from the file into the workbook via the
     *                         Tran.
     * @param importValues     Error Overrides specified by the user from the initial run of the import
     * @param productStructureId    The ProductStructure to be used when importing the new contracts.
     * @param operator         The operator id of user importing new business.
     * @return Array of ImportNewBusinessResponse Objects denoting whether each save was successful or not, and if
     *         the save failed for duplicate client(s), the user will have a chance to correct (again)
     * @throws EDITContractException if there is a problem saving the new business information
     */
    public ImportNewBusinessResponse[] importNewBusiness(HSSFWorkbook hssfWorkbook, 
                                                         String importValues, 
                                                         String productStructureId,
                                                         String operator) throws EDITContractException;

    /**
     * * Checks for correct data in the quoteVO.   Does this by running through the edit scripts
     * <P>
     * Note: this was originally in QuoteDetailTran.  Moved it to the component so other user interfaces could use it.
     * Ideally, this should be automatically called as part of the saveQuote process but no one knows the proper flow.
     *
     * @param quoteVO
     * @param processName
     *
     * @return SPOutputVO - resultant VO from processing the scripts
     *
     * @throws SPException
     */
    public SPOutputVO validateQuote(QuoteVO quoteVO, String processName) throws SPException, EDITValidationException;

    /**
     * Calculates the person's age when the policy is issued as of the last birth date
     *
     * @param birthDate         date of birth
     * @param effectiveDate     date when the policy is issued
     *
     * @return age of the person
     *
     * @throws EDITContractException if either date is not a valid date
     */
    public int calculateIssueAge(String birthDate, String effectiveDate) throws EDITContractException;

    /**
     * Calculates the person's age when the policy is issued as of the nearest birth date.  This applies to life contracts.
     *
     * @param birthDate         date of birth
     * @param effectiveDate     date when the policy is issued
     *
     * @return age of the person
     *
     * @throws EDITContractException if either date is not a valid date
     */
    public int calculateIssueAgeForLifeContracts(String birthDate, String effectiveDate) throws EDITContractException;

    /**
     * * Checks for correct data in the contractVO.   Does this by running through the edit scripts
     * <P>
     * Note: this was originally in ContractDetailTran.  Moved it to the component so other user interfaces could use it.
     * Ideally, this should be automatically called as part of the saveContract process but no one knows the proper flow.
     *
     * @param contractVO
     * @param processName
     *
     * @return SPOutputVO - resultant VO from processing the scripts
     *
     * @throws SPException
     */
    public SPOutputVO validateContract(ContractVO contractVO, String processName) throws SPException, EDITValidationException;

    /**
     * Associates a Requirment to a Contract if the requirement is associated to same Product Structure as Contract.
     * @param segmentPK
     * @param productStructurePK
     * @param requirementId
     * @return
     */
    public ContractRequirement insertContractRequirement(long segmentPK, long productStructurePK, String requirementId) throws EDITValidationException;

    /**
     * Clone Requirements from activeCompanyStructure to cloneToCompanyStructure.
     * @param activeCompanyStructurePK
     * @param cloneToCompanyStructurePK
     */
    public void cloneRequirements(long activeCompanyStructurePK, long cloneToCompanyStructurePK) throws Exception;

    public String saveDeposit(Long depositsPK, String depositType, String operator);
}

