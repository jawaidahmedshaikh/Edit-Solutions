/*
 * User: unknown
 * Date: Oct 8, 2001
 * Time: unknown
 *
 * (c) 2000-2008 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */

package contract.component;

import contract.*;
import contract.business.Lookup;
import contract.dm.composer.*;
import contract.dm.dao.DAOFactory;
import contract.dm.dao.FastDAO;
import edit.common.vo.*;
import edit.common.ChangeHistory;
import edit.services.component.AbstractLookupComponent;
import edit.services.db.*;
import fission.utility.Util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class LookupComponent extends AbstractLookupComponent implements Lookup
{

    //member variables
//    private QueryBuilder queryBuilder;

    public LookupComponent()
    {

//        queryBuilder = new QueryBuilder();
    }

    public SegmentVO[] getSegmentBySegmentPK(long segmentPK,
                                             boolean getChildrenInd,
                                             List voExclusionList)
            throws Exception
    {

        return DAOFactory.getSegmentDAO().findBySegmentPK(segmentPK,
                                                          getChildrenInd,
                                                          voExclusionList);
    }

    public SegmentVO[] getSegmentNonRecursivelyByPK(long segmentPK) throws Exception
    {
        SegmentVO[] segmentVOs = DAOFactory.getSegmentDAO().findNonRecursivelyByPK(segmentPK);

        return segmentVOs;
    }

    public SegmentVO[] getSegmentByContractNumber(String contractNumber,
                                                   boolean getChildrenInd,
                                                    List voExclusionList) throws Exception
    {
        return DAOFactory.getSegmentDAO().findSegmentByContractNumber(contractNumber,
                                                                      getChildrenInd,
                                                                      voExclusionList);
    }

    public SegmentVO[] getAllActiveSegmentsByCSId(long[] csIds, String[] statusCodes) throws Exception
    {

        return DAOFactory.getSegmentDAO().findAllActiveByCSId(csIds, statusCodes);
    }

    public SegmentVO[] getAllSegmentsByCSId(long[] csIds) throws Exception
    {
        SegmentVO[] segmentVOs = DAOFactory.getSegmentDAO().findAllByCSId(csIds);

        return segmentVOs;
    }

    /**
     * Gets all Segments for the given company structure PKs
     * @param csIds
     * @param voInclusionList
     * @return
     * @throws Exception
     */
    public SegmentVO[] getAllSegmentsByCSId(long[] csIds, List voInclusionList) throws Exception
    {
        return new VOComposer().composeSegmentVOByProductStructure(csIds, voInclusionList);
    }

    public SegmentVO[] getAllSegments() throws Exception
    {
        SegmentVO[] segmentVOs = DAOFactory.getSegmentDAO().findAllSegments();

        return segmentVOs;
    }

    public RequirementVO[] getAllRequirementVOsByProductStructure(long productStructurePK,
                                                                  boolean includeChildVOs,
                                                                  List voExclusionList) throws Exception
    {

        return DAOFactory.getRequirementDAO().findByProductStructure(productStructurePK,
                                                                     false,
                                                                     voExclusionList);
    }

    public InvestmentVO[] getInvestmentByPK(long investmentPK) throws Exception
    {

        return DAOFactory.getInvestmentDAO().findByInvestmentPK(investmentPK, false, null);
    }

    public InvestmentVO[] getInvestmentByFilteredFundFKAndSegmentFK(long filteredFundFK,
                                                                    long segmentFK) throws Exception
    {

        return DAOFactory.getInvestmentDAO().findByFilteredFundFKAndSegmentFK(filteredFundFK,
                                                                              segmentFK,
                                                                              false, null);
    }

   /**
    * @see contract.business.Lookup#getAllChangeHistoryByContractNumber
    */
    public ChangeHistoryVO[] getAllChangeHistoryByContractNumber(String contractNumber) throws Exception
    {
        ChangeHistoryVO[] changeHistoryVOs = DAOFactory.getChangeHistoryDAO().findByContractNumber(contractNumber);

        return changeHistoryVOs;
    }

    public ChangeHistoryVO[] getChangeHistoryByPK(long changeHistoryPK) throws Exception
    {

        ChangeHistoryVO[] changeHistoryVOs = DAOFactory.getChangeHistoryDAO().
                findChangeHistoryByPK(changeHistoryPK);

        return changeHistoryVOs;
    }

    public SegmentVO[] getBaseAndRidersBySegmentPK(long segmentPK) throws Exception
    {

        SegmentVO[] segmentVOs = DAOFactory.getSegmentDAO().findBaseAndRidersBySegmentPK(segmentPK);

        return segmentVOs;
    }

    public SegmentVO[] findSegmentsByClientRoleFK(long clientRoleFK,
                                                  boolean includeChildVOs,
                                                  List voExclusionList)
    {

        return DAOFactory.getSegmentDAO().findByClientRoleFK(clientRoleFK,
                                                             includeChildVOs,
                                                             voExclusionList);
    }

    public SegmentVO[] findSegmentByProductStructureContractNumber(long productStructurePK,
                                                                   String contractNumber,
                                                                   boolean includeChildVOs,
                                                                   List voExclusionList)
            throws Exception
    {

        return DAOFactory.getSegmentDAO().findByProductStructureContractNumber(productStructurePK,
                                                                               contractNumber,
                                                                               includeChildVOs,
                                                                               voExclusionList);
    }

    public SegmentVO[] findBySegmentPK(long segmentPK, boolean includeChildVOs, String[] voExclusionList) throws Exception
    {

        SegmentVO[] segmentVOs = DAOFactory.
                getSegmentDAO().
                findBySegmentPK(segmentPK,
                                includeChildVOs,
                                super.convertVOClassExclusionList(voExclusionList));

        return segmentVOs;
    }

    public SegmentVO[] getAllSegmentsForBenefitExtract(String valuationDate,
                                                       String optionCode,
                                                       String transactionType,
                                                       long[] productStructureIds)
            throws Exception
    {

        return DAOFactory.getSegmentDAO().
                findAllSegmentsForBenefitExtract(valuationDate,
                                                 optionCode,
                                                 transactionType,
                                                 productStructureIds);
    }

    public BucketVO[] getBucketsByInvestmentFK(long investmentFK,
                                               boolean includeChildVOs,
                                               List voExclusionVector) throws Exception
    {

        return DAOFactory.getBucketDAO().findByInvestmentFK(investmentFK, includeChildVOs, voExclusionVector);
    }

    public BucketVO[] getBucketsByInvestmentFKForScrolling(long investmentFK,
                                                            long beginningBucketPK,
                                                             String scrollDirection)
            throws Exception
    {
        DBTable bucketDBTable   = DBTable.getDBTableForTable("Bucket");

        String bucketTableName = bucketDBTable.getFullyQualifiedTableName();

        String investmentFKCol = bucketDBTable.getDBColumn("InvestmentFK").getFullyQualifiedColumnName();
        String depositDateCol  = bucketDBTable.getDBColumn("DepositDate").getFullyQualifiedColumnName();

        String sql = "SELECT * FROM " + bucketTableName +
                     " WHERE " + investmentFKCol + " = " + investmentFK +
                     " ORDER BY " + depositDateCol + " ASC";

        Connection conn = null;

        Statement s = null;
        ResultSet rs = null;

        CRUD crud = null;

        List bucketPKs = new ArrayList();

        try
        {
            conn = ConnectionFactory.getSingleton().getConnection(ConnectionFactory.EDITSOLUTIONS_POOL);

            s = conn.createStatement();

            rs = s.executeQuery(sql);

            boolean targetBucketFound = false;
            boolean skipBucket = true;

            while (rs.next())
            {
                long bucketPK = rs.getLong("BucketPK");

                if (bucketPK == beginningBucketPK || beginningBucketPK == 0)
                {
                    targetBucketFound = true;
                }

                if (!targetBucketFound && scrollDirection.equalsIgnoreCase("BACKWARD"))
                {
                    bucketPKs.add(new Long(bucketPK));
                }

                if (targetBucketFound && scrollDirection.equalsIgnoreCase("BACKWARD"))
                {
                    if (25 <= bucketPKs.size())
                    {
                        bucketPKs = bucketPKs.
                                subList((bucketPKs.size() - 25), bucketPKs.size());
                    }

                    break;
                }

                if (targetBucketFound && scrollDirection.equalsIgnoreCase("FORWARD"))
                {
                    if (skipBucket && (beginningBucketPK != 0))
                    {
                        skipBucket = false;
                    }

                    bucketPKs.add(new Long(bucketPK));

                    if (bucketPKs.size() == 25)
                    {
                        break;
                    }
                }
            }

            if (!targetBucketFound)
            {
                return getBucketsByInvestmentFKForScrolling(investmentFK, 0, "FORWARD");
            }
            else
            {
                List buckets = null;

                crud = CRUDFactory.getSingleton().getCRUD(ConnectionFactory.EDITSOLUTIONS_POOL);
                buckets = new ArrayList();
                Iterator it = bucketPKs.iterator();
                while (it.hasNext())
                {
                    Long currentBucketPK = (Long) it.next();

                    BucketVO currentBucketVO = (BucketVO)
                            crud.retrieveVOFromDB(BucketVO.class, currentBucketPK.longValue());

                    buckets.add(currentBucketVO);
                }

                if (buckets.size() == 0)
                {
                    return null;
                }
                else
                {
                    return (BucketVO[]) buckets.toArray(new BucketVO[buckets.size()]);
                }
            }
        }
        catch (Exception e)
        {
            System.out.println(e);

            e.printStackTrace();

            throw e;
        }

        finally
        {
            if (rs != null) rs.close();
            if (s != null) s.close();
            if (conn != null) conn.close();

            if (crud != null) crud.close();
        }
    }

    public long[] findContractClientPKsBySegmentPKAndRoleType(long segmentPK,
                                                              String roleType)
    {
        List clientRolePKsByRoleType = new ArrayList();

        ContractClientVO[] contractClientVOs = DAOFactory.getContractClientDAO().findBySegmentFK(segmentPK, false, null);

        if (contractClientVOs != null)
        {
            for (int i = 0; i < contractClientVOs.length; i++)
            {
                String overrideStatus = contractClientVOs[i].getOverrideStatus();
                if (overrideStatus == null || overrideStatus.equalsIgnoreCase("P"))
                {
                    long[] clientRolePK = new role.dm.dao.FastDAO().findClientRolePKsByClientRolePKAndRoleType(contractClientVOs[i].getClientRoleFK(), roleType);

                    if (clientRolePK != null)
                    {
                        if (clientRolePK[0] != 0)
                        {
                            clientRolePKsByRoleType.add(new Long(contractClientVOs[i].getContractClientPK()));
                        }
                    }
                }
            }
        }

        if (clientRolePKsByRoleType.size() == 0)
        {
            return null;
        }
        else
        {
            return Util.convertLongToPrim((Long[]) clientRolePKsByRoleType.toArray(new Long[clientRolePKsByRoleType.size()]));
        }
    }

    public InvestmentAllocationVO[] findInvestmentAllocationByInvestmentAllocationPK(long investmentAllocationPK, boolean includeChildVOs, List voExclusionList) throws Exception
    {
        return DAOFactory.getInvestmentAllocationDAO().findByInvestmentAllocationPK(investmentAllocationPK, includeChildVOs, voExclusionList);
    }

    public WithholdingVO[] findWithholdingVOByWithholdingPK(long withholdingPK, boolean includeChildVOs, List voExclusionList) throws Exception
    {
        return DAOFactory.getWithholdingDAO().findByWithholdingPK(withholdingPK, includeChildVOs, voExclusionList);
    }

    public ContractClientAllocationVO[] findContractClientAllocationVOByContractClientPKAndAllocPct(long contractClientPK,
                                                                                                    double allocationPct) throws Exception
    {

        return DAOFactory.getContractClientAllocationDAO().findByContractClientPKAndAllocPct(contractClientPK,
                                                                                             allocationPct);
    }

    public ContractClientVO composeContractClientVO(ClientSetupVO clientSetupVO, List voInclusionList) throws Exception
    {
        return new VOComposer().composeContractClientVO(clientSetupVO, voInclusionList);
    }

    public ContractClientVO[] composeContractClientVO(long segmentPK, String contractClientOverrideStatus, List voInclusionList) throws Exception
    {
        return new VOComposer().composeContractClientVO(segmentPK, contractClientOverrideStatus, voInclusionList);
    }

    public ContractClientVO[] composeContractClientVOBySegmentFK(long segmentFK, List voInclusionList) throws Exception
    {
        return new VOComposer().composeContractClientVOBySegmentFK(segmentFK, voInclusionList);
    }

    public SegmentVO composeSegmentVO(long segmentPK, List voInclusionList) throws Exception
    {
        return new VOComposer().composeSegmentVO(segmentPK, voInclusionList);
    }

    public SegmentVO composeSegmentVO(String contractNumber, List voInclusionList)
    {
        return new VOComposer().composeSegmentVO(contractNumber, voInclusionList);
    }

    public SegmentVO[] composeSegmentVOs(String contractNumber, List voInclusionList)
    {
        return new VOComposer().composeSegmentVOs(contractNumber, voInclusionList);
    }

    public SegmentVO[] composeSegmentVOByFund(long filteredFundFK, List voInclusionList)
    {
        return new VOComposer().composeSegmentVOByFund(filteredFundFK, voInclusionList);
    }

    public InvestmentVO[] composeInvestmentVO(long segmentPK,
                                              InvestmentAllocationOverrideVO[] investmentAllocationOverrideVO,
                                              List voInclusionList,
                                              String trxType) throws Exception
    {
        return new VOComposer().composeInvestmentVO(segmentPK, investmentAllocationOverrideVO, voInclusionList, trxType);
    }

    /**
     * Retrieves all invesments for the given segment
     * @param segmentPK
     * @param voInclusionList
     * @return
     * @throws Exception
     */
    public InvestmentVO[] composeInvestmentVOBySegmentPK(long segmentPK, List voInclusionList) throws Exception
    {
        return new VOComposer().composeInvestmentVOBySegmentPK(segmentPK, voInclusionList);
    }

    /**
     * Retrieves the investment for the given investment key
     * @param investmentPK
     * @param voInclusionList
     * @return
     * @throws Exception
     */
    public InvestmentVO composeInvestmentVOByPK(long investmentPK, List voInclusionList) throws Exception
    {
        return new VOComposer().composeInvestmentVOByPK(investmentPK, voInclusionList);
    }

    public RequirementVO[] composeRequirementVOs(List voInclusionList) throws Exception
    {
        return new VOComposer().composeRequirementVOs(voInclusionList);
    }

    public RequirementVO composeRequirementVOByPK(long requirementPK, List voInclusionList) throws Exception
    {
        return new VOComposer().composeRequirementVOByPK(requirementPK, voInclusionList);
    }

    public FilteredRequirementVO composeFilteredRequirementVOByPK(long filteredRequirementPK, List voInclusionList) throws Exception
    {
        return new VOComposer().composeFilteredRequirementVOByPK(filteredRequirementPK, voInclusionList);
    }

    public AgentSnapshotVO[] composeAgentSnapshotVOsByPlacedAgentFK(long placedAgentFK, List voInclusionList) throws Exception
    {
        return new VOComposer().composeAgentSnapshotVOsByPlacedAgentFK(placedAgentFK, voInclusionList);
    }

    public SegmentVO[] findSegmentVOByContractNumber(String contractNumber, boolean includeChildVOs, List voInclusionList)
    {
        return DAOFactory.getSegmentDAO().findSegmentByContractNumber(contractNumber, includeChildVOs, voInclusionList);
    }

    public SegmentVO[] findSegmentVOByCompanyName_AND_ProcessDateLTE(String companyName, String processDate) throws Exception
    {
//        List accountingPendingSegmentVOs = new ArrayList();
//
//        engine.business.Lookup calcLookup = new engine.component.LookupComponent();
//
//        CompanyStructureVO[] companyStructureVO = null;
//
//        if (companyName.equalsIgnoreCase("All"))
//        {
//            companyStructureVO = calcLookup.getAllCompanyStructures();
//        }
//
//        else
//        {
//            companyStructureVO = calcLookup.getAllCompanyStructuresByCoName(companyName);
//        }
//
//        if (companyStructureVO != null)
//        {
//            Event eventComponent = new EventComponent();
//
//            for (int i = 0; i < companyStructureVO.length; i++)
//            {
//                SegmentVO[] segmentVO = DAOFactory.getSegmentDAO().findByCompanyStructurePK(companyStructureVO[i].getCompanyStructurePK(), false, null);
//
//                if (segmentVO != null)
//                {
//                    for (int j = 0; j < segmentVO.length; j++)
//                    {
//                        if (eventComponent.isAccountingPending(segmentVO[j].getSegmentPK(), processDate))
//                        {
//                            accountingPendingSegmentVOs.add(segmentVO[j]);
//                        }
//                    }
//                }
//            }
//        }
//
//        if (accountingPendingSegmentVOs.size() == 0)
//        {
//            return null;
//        }
//        else
//        {
//            return (SegmentVO[]) accountingPendingSegmentVOs.toArray(new SegmentVO[accountingPendingSegmentVOs.size()]);
//        }
        return null;
    }

    public ContractRequirementVO[] buildContractRequirements(long productStructureFK)
    {
        ContractRequirementVO contractRequirementVO = null;

        return new ContractRequirement(contractRequirementVO).buildInitialContractRequirements(productStructureFK);
    }

    public RequirementVO[] findByFilteredRequirementPK(long filteredRequirementPK, boolean includeChildVOs, List voInclusionList) throws Exception
    {
        return DAOFactory.getRequirementDAO().findByFilteredRequirementPK(filteredRequirementPK, includeChildVOs, voInclusionList);
    }

    public RequirementVO[] findRequirementByProductStructurePKAndManualInd(long productStructurePK,
                                                                            String manualInd,
                                                                            boolean includeChildVOs,
                                                                            List voExclusionList)
                                                                            throws Exception
    {

        return DAOFactory.getRequirementDAO().findByProductStructurePKAndManualInd(productStructurePK,
                                                                                   manualInd,
                                                                                   includeChildVOs,
                                                                                   voExclusionList);
    }

    public FilteredRequirementVO[] findFilteredRequirementByProductStructureAndRequirement(long productStructurePK,
                                                                                           long requirementPK,
                                                                                           boolean includeChildVOs,
                                                                                           List voExclusionList)
            throws Exception
    {

        return DAOFactory.getFilteredRequirementDAO().findProductStructureFKAndRequirementPK(productStructurePK,
                                                                                             requirementPK,
                                                                                             includeChildVOs,
                                                                                             voExclusionList);
    }

    public ContractRequirementVO composeContractRequirementVO(ContractRequirementVO contractRequirementVO, List voInclusionList) throws Exception
    {
        return new ContractRequirementComposer(voInclusionList).compose(contractRequirementVO);

    }

    public AgentSnapshotVO[] findSortedAgentSnapshotVOs(long agentHierarchyPK) throws Exception
    {
        return DAOFactory.getAgentSnapshotDAO().findSortedAgentSnapshotVOs(agentHierarchyPK);
    }

    public NoteReminderVO[] findAllNotes(long segmentPK) throws Exception
    {
        return DAOFactory.getNoteReminderDAO().findBySegmentPK(segmentPK, false, null);
    }

//    public long[] findBaseSegmentPKsByCompanyStructurePKAndSegmentStatusCT(long productStructurePK, String[] segmentStatusCT)
//    {
//        return new FastDAO().findBaseSegmentPKsByCompanyStructurePKAndSegmentStatusCT(productStructurePK, segmentStatusCT);
//    }

    public DepositsVO[] composeDepositsBySegmentPKAndEDITTrxPK(long segmentPK, String editTrxPK) throws Exception
    {
        return new VOComposer().composeDepositsBySegmentPKAndEDITTrxPK(segmentPK, editTrxPK);
    }

    public DepositsVO[] composeDepositsByEDITTrxPK(long editTrxPK) throws Exception
    {
        return new VOComposer().composeDepositsByEDITTrxPK(editTrxPK);
    }

    public DepositsVO composeDepositsVOByPK(long depositsPK) throws Exception
    {
        return new VOComposer().composeDepositsVOByPK(depositsPK);
    }

    public ChangeHistoryVO[] getChangeHistoryForContract(long segmentPK) throws Exception
    {

        return DAOFactory.getChangeHistoryDAO().findByModifiedKey(segmentPK);
    }

    public ChangeHistoryVO[] getChangeHistoryForContract(SegmentVO segmentVO) throws Exception
    {
        ChangeHistory changeHistory = new ChangeHistory();

        return changeHistory.findAllChangeHistoryForSegment(segmentVO);
    }


    /**
     * Retrieves the InvestmentAllocation record for the given investment key
     * @param investmentFK
     * @param includeChildren
     * @param voExclusionVector
     * @return
     */
    public InvestmentAllocationVO[] getByInvestmentAllocation(long investmentFK, boolean includeChildren, List voExclusionList)
    {
        return DAOFactory.getInvestmentAllocationDAO().findByInvestmentPK(investmentFK, includeChildren, voExclusionList);
    }

    /**
     * Retrieves all segments that have a segmentStatusCT in the array of statuses passed in parameters
     * @param segmentStatus
     * @param voInclusionList
     * @return
     * @throws Exception
     */
    public SegmentVO[] composeSegmentVOByStatus(String[] segmentStatus, List voInclusionList) throws Exception
    {
        return new VOComposer().composeSegmentVOByStatus(segmentStatus, voInclusionList);
    }

    /**
     * @see contract.business.Lookup#findSegmentsBy_PartialCorporateName(String)
     * @param partialCorporateName
     * @return
     */
    public SegmentVO[] findSegmentsBy_PartialCorporateName(String partialCorporateName)
    {
        return (SegmentVO[]) CRUDEntityImpl.mapEntityToVO(Segment.findBy_PartialCorporateName(partialCorporateName), SegmentVO.class);
    }

    /**
     * @see Lookup#findRiderSegmentsBy_SegmentPK(long)
     * @param segmentPK
     * @return
     */
    public SegmentVO[] findRiderSegmentsBy_SegmentPK(long segmentPK)
    {
        Segment baseSegment = new Segment(segmentPK);

        Segment[] riderSegments = baseSegment.get_Riders();

        return (SegmentVO[]) CRUDEntityImpl.mapEntityToVO(riderSegments, SegmentVO.class);
    }

    /**
     * Finder.
     * @param segmentPK
     * @return
     */
    public SegmentVO findSegmentBy_SegmentPK(long segmentPK)
    {
        return (SegmentVO)(new Segment(segmentPK).getVO());
    }

    /**
     * Finder.
     * @param reinsuranceHistoryPK
     * @return
     */
    public SegmentVO findSegmentBy_ReinsuranceHistoryPK(long reinsuranceHistoryPK)
    {
        SegmentVO segmentVO = null;

        Segment segment = Segment.findBy_ReinsuranceHistoryPK(reinsuranceHistoryPK);

        if (segment != null)
        {
            segmentVO = (SegmentVO) segment.getVO();
        }

        return segmentVO;
    }

    public ContractClientVO getTheInsuredContractClient(ContractClientVO[] contractClientVOs)
    {
        ContractClient[] contractClients = this.convertContractClientVOs(contractClientVOs);

        ContractClient contractClient = ContractClient.getInsuredContractClient(contractClients);

        return (ContractClientVO) contractClient.getVO();
    }

    // The following method is temporary until we fully go to Hibernate
    private ContractClient[] convertContractClientVOs(ContractClientVO[] contractClientVOs)
    {
        List contractClients = new ArrayList();

        for (int i = 0; i < contractClientVOs.length; i++)
        {
            ContractClient contractClient = new ContractClient(contractClientVOs[i]);

            contractClients.add(contractClient);
        }

        return (ContractClient[]) contractClients.toArray(new ContractClient[contractClients.size()]);
    }
}
