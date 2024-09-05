/*
 * User: unknown
 * Date: Oct 8, 2001
 * Time: unknown
 *
 * (c) 2000-2008 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */

package contract.business;

import edit.common.vo.*;
import edit.services.component.ILookup;

import java.util.List;

public interface Lookup extends ILookup {

    public abstract SegmentVO[] getSegmentBySegmentPK(long segmentPK,
                                                       boolean getChildren,
                                                        List voExclusionList)
                                                     throws Exception;

    public abstract SegmentVO[] getSegmentByContractNumber(String contractNumber,
                                                            boolean getChildrenInd,
                                                             List voExclusionList)
                                                          throws Exception;

    public abstract SegmentVO[] getAllSegments() throws Exception;

    public abstract RequirementVO[] getAllRequirementVOsByProductStructure(long productStructurePK,
                                                                            boolean includeChildVOs,
                                                                             List voExclusionList) throws Exception;

    public abstract SegmentVO[] getAllActiveSegmentsByCSId(long[] csIds, String[] statusCodes) throws Exception;

    public abstract SegmentVO[] getAllSegmentsByCSId(long[] csIds) throws Exception;

    /**
     * Gets all Segments for the given product structure PKs
     * @param csIds
     * @param voInclusionList
     * @return
     * @throws Exception
     */
    public abstract SegmentVO[] getAllSegmentsByCSId(long[] csIds, List voInclusionList) throws Exception;

    public abstract InvestmentVO[] getInvestmentByPK(long investmentPK) throws Exception;

    public abstract InvestmentVO[] getInvestmentByFilteredFundFKAndSegmentFK(long filteredFundFK,
                                                                              long segmentFK) throws Exception;

   /**
    * Gets all the ChangeHistorys for a given contract
    *
    * @param contractNumber                contract identifier for which ChangeHistory records should be found
    *
    * @return  Array of ChangeHistoryVOs
    *
    * @throws Exception
    */
    public abstract ChangeHistoryVO[] getAllChangeHistoryByContractNumber(String contractNumber) throws Exception;

    public abstract ChangeHistoryVO[] getChangeHistoryByPK(long changeHistoryPK) throws Exception;

    public abstract SegmentVO[] getBaseAndRidersBySegmentPK(long segmentPK) throws Exception;

    public SegmentVO[] getSegmentNonRecursivelyByPK(long segmentPK) throws Exception;

    public SegmentVO[] findSegmentsByClientRoleFK(long clientRoleFK, boolean includeChildVOs, List voExlusionList);

//    public MasterVO[] findMasterByMasterNumber(String masterNumber, boolean includeChildVOs, List voExclusionList);

    /**
     * Find a Master for the given primary key
     *
     * @param masterPK                      primary key of the Master to be returned
     *
     * @return  MasterVO containing the specified primary key
     */
//    public MasterVO[] findMasterByMasterPK(long masterPK);

    /**
     * Finds all Masters with the specified groupTypeCT
     *
     * @param groupTypeCT                     groupTypeCT field of Master to match upon
     *
     * @return Array of MasterVOs with the specified groupType
     *
     * @throws Exception
     */
//    public MasterVO[] findMasterByGroupType(String groupTypeCT) throws Exception;

    public SegmentVO[] findSegmentByProductStructureContractNumber(long productStructurePK, String contractNumber, boolean includeChildVOs, List voExclusionList) throws Exception;

	public SegmentVO[] findBySegmentPK(long segmentPK, boolean includeChildVOs, String[] voExclusionList) throws Exception;

    public abstract BucketVO[] getBucketsByInvestmentFK(long investmentFK,
                                                         boolean includeChildVOs,
                                                          List voExclusionVector)
                                                       throws Exception;

    public abstract BucketVO[] getBucketsByInvestmentFKForScrolling(long investmentFK,
                                                                     long beginningBucketPK,
                                                                      String scrollingDirection)
                                                                   throws Exception;

    public long[] findContractClientPKsBySegmentPKAndRoleType(long segmentPK, String clientRole);

    public InvestmentAllocationVO[] findInvestmentAllocationByInvestmentAllocationPK(long investmentAllocationPK, boolean includeChildVOs, List voExclusionList) throws Exception;

    public WithholdingVO[] findWithholdingVOByWithholdingPK(long withholdingPK, boolean includeChildVOs, List voExclusionList) throws Exception;

    public ContractClientAllocationVO[] findContractClientAllocationVOByContractClientPKAndAllocPct(long contractClientPK,
                                                                                                     double allocationPct) throws Exception;

    public ContractClientVO composeContractClientVO(ClientSetupVO clientSetupVO, List voInclusionList) throws Exception;

    public ContractClientVO[] composeContractClientVO(long segmentPK, String contractClientOverrideStatus, List voInclusionList) throws Exception;

    public ContractClientVO[] composeContractClientVOBySegmentFK(long segmentFK, List voInclusionList) throws Exception;

    public SegmentVO composeSegmentVO(long segmentPK, List voInclusionList) throws Exception;

    public SegmentVO composeSegmentVO(String contractNumber, List voInclusionList);

    public SegmentVO[] composeSegmentVOs(String contractNumber, List voInclusionList);

    public SegmentVO[] composeSegmentVOByFund(long filteredFundFK, List voInclusionList);

    public InvestmentVO[] composeInvestmentVO(long segmentPK,
                                              InvestmentAllocationOverrideVO[] investmentAllocationOverrideVO,
                                              List voInclusionList,
                                              String trxType) throws Exception;

    /**
     * Retrieves all invesments for the given segment
     * @param segmentPK
     * @param voInclusionList
     * @return
     * @throws Exception
     */
    public InvestmentVO[] composeInvestmentVOBySegmentPK(long segmentPK, List voInclusionList) throws Exception;
    
    /**
     * Retrieves the investment for the given investment key
     * @param investmentPK
     * @param voInclusionList
     * @return
     * @throws Exception
     */
    public InvestmentVO composeInvestmentVOByPK(long investmentPK, List voInclusionList) throws Exception;

    /**
     * Retrieves all segments that have a segmentStatusCT in the array of statuses passed in parameters
     * @param segmentStatus
     * @param voInclusionList
     * @return
     * @throws Exception
     */
    public SegmentVO[] composeSegmentVOByStatus(String[] segmentStatus, List voInclusionList) throws Exception;

    public RequirementVO[] composeRequirementVOs(List voInclusionList) throws Exception;

    public RequirementVO composeRequirementVOByPK(long requirementPK, List voInclusionList) throws Exception;

    public FilteredRequirementVO composeFilteredRequirementVOByPK(long filteredRequirementPK, List voInclusionList) throws Exception;

    public AgentSnapshotVO[] composeAgentSnapshotVOsByPlacedAgentFK(long placedAgentFK, List voInclusionList) throws Exception;

    public SegmentVO[] findSegmentVOByContractNumber(String contractNumber, boolean includeChildVOs, List voInclusionList);

    public ContractRequirementVO[] buildContractRequirements(long productStructureFK);

    public RequirementVO[] findByFilteredRequirementPK(long requirementPK,  boolean includeChildVOs, List voInclusionList) throws Exception;

    public RequirementVO[] findRequirementByProductStructurePKAndManualInd(long productStructurePK,
                                                                            String manualInd,
                                                                             boolean includeChildVOs,
                                                                              List voInclusionList)
                                                                          throws Exception;

    public FilteredRequirementVO[] findFilteredRequirementByProductStructureAndRequirement(long productStructurePK,
                                                                                            long requirementPK,
                                                                                             boolean includeChildVOs,
                                                                                              List voInclusionList)
                                                                                          throws Exception;
    public ContractRequirementVO composeContractRequirementVO(ContractRequirementVO contractRequirementVO, List voInclusionList) throws Exception;

    public AgentSnapshotVO[] findSortedAgentSnapshotVOs(long agentHierarchyPK) throws Exception;

    public NoteReminderVO[] findAllNotes(long segmentPK) throws Exception;

//    /**
//     * Finds the set of all base SegmentPKs for a given ProductStructurePK and SegmentStatusCT. Riders are not included in this list.
//     * @param productStructurePK
//     * @param segmentStatusCT
//     * @return
//     * @throws Exception
//     */
//    public long[] findBaseSegmentPKsByCompanyStructurePKAndSegmentStatusCT(long productStructurePK, String[] segmentStatusCT) throws Exception;

    /**
     * Finds all deposits for a given contract and transaction
     * @param segmentPK - the primary key for the contract
     * @param editTrxPK - the primary key for the transaction
     * @return DepositsVO[] - all the deposits for a given contract used to create a given transaction
     * @throws Exception
     */
    public DepositsVO[] composeDepositsBySegmentPKAndEDITTrxPK(long segmentPK, String editTrxPK) throws Exception;

    /**
     * Finds all deposits for a given transaction
     * @param editTRXPK - the primary key for the transaction
     * @return DepositsVO[] - all the deposits for a given transaction
     * @throws Exception
     */
    public DepositsVO[] composeDepositsByEDITTrxPK(long editTRXPK) throws Exception;

    /**
     * Finds the deposit for a given deposit key
     * @param depositsPK - the primary key for the deposit
     * @return DepositsVO - the deposit for the given deposit key
     * @throws Exception
     */
    public DepositsVO composeDepositsVOByPK(long depositsPK) throws Exception;

    public ChangeHistoryVO[] getChangeHistoryForContract(long segmentPK) throws Exception;

    public ChangeHistoryVO[] getChangeHistoryForContract(SegmentVO segmentVO) throws Exception;


    /**
     * Retrieves the InvestmentAllocation record for the given investment key
     * @param investmentFK
     * @param includeChildren
     * @param voExclusionVector
     * @return
     */
    public InvestmentAllocationVO[] getByInvestmentAllocation(long investmentFK, boolean includeChildren, List voExclusionVector);

    /**
     * Finds the set of all Segments that can be associated with the partial corporate name.
     * @param partialCorporateName
     * @return
     */
    public SegmentVO[] findSegmentsBy_PartialCorporateName(String partialCorporateName);

    /**
     * Finds the set of riders for the specified Segment.
     * @param segmentPK
     * @return
     */
    public SegmentVO[] findRiderSegmentsBy_SegmentPK(long segmentPK);

    /**
     * Finder.
     * @param segmentPK
     * @return
     */
    public SegmentVO findSegmentBy_SegmentPK(long segmentPK);

    /**
     * Finder.
     * @param reinsuranceHistoryPK
     * @return
     */
    public SegmentVO findSegmentBy_ReinsuranceHistoryPK(long reinsuranceHistoryPK);

    /**
     * From an array of ContractClients get the Insured ContractClient
     * @param contractClientVOs
     * @return   ContractClientVO of the insured
     */
    public ContractClientVO getTheInsuredContractClient(ContractClientVO[] contractClientVOs);
}
