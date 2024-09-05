/*
 * User: unknown
 * Date: Oct 5, 2001
 * Time: unknown
 *
 * (c) 2000-2008 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */

package contract.dm.dao;

import java.io.Serializable;

public class DAOFactory implements Serializable {

//******************************
//          Variables
//******************************

    private static ChangeHistoryDAO              changeHistoryDAO;
    private static PayoutDAO                     payoutDAO;
    private static SegmentDAO                    segmentDAO;
    private static ContractClientDAO             contractClientDAO;
    private static BucketDAO                     bucketDAO;
    private static InvestmentAllocationDAO       investmentAllocationDAO;
    private static WithholdingDAO                withholdingDAO;
    private static ContractClientAllocationDAO   contractClientAllocationDAO;
    private static NoteReminderDAO               noteReminderDAO;
    private static InvestmentDAO                 investmentDAO;
    private static BucketAllocationDAO           bucketAllocationDAO;
    private static ContractRequirementDAO        contractRequirementDAO;
    private static FilteredRequirementDAO        filteredRequirementDAO;
    private static RequirementDAO                requirementDAO;
    private static AgentHierarchyDAO             agentHierarchyDAO;
    private static AgentSnapshotDAO              agentSnapshotDAO;
    private static SegmentBackupDAO              segmentBackupDAO;
    private static DepositsDAO                   depositsDAO;
    private static LifeDAO                       lifeDAO;

    static {

        changeHistoryDAO             = new ChangeHistoryDAO();
        payoutDAO                    = new PayoutDAO();
        segmentDAO                   = new SegmentDAO();
        contractClientDAO            = new ContractClientDAO();
        bucketDAO                    = new BucketDAO();
        investmentAllocationDAO      = new InvestmentAllocationDAO();
        withholdingDAO               = new WithholdingDAO();
        contractClientAllocationDAO  = new ContractClientAllocationDAO();
        noteReminderDAO              = new NoteReminderDAO();
        investmentDAO                = new InvestmentDAO();
        bucketAllocationDAO          = new BucketAllocationDAO();
        contractRequirementDAO       = new ContractRequirementDAO();
        filteredRequirementDAO       = new FilteredRequirementDAO();
        requirementDAO               = new RequirementDAO();
        agentHierarchyDAO            = new AgentHierarchyDAO();
        agentSnapshotDAO             = new AgentSnapshotDAO();
        segmentBackupDAO             = new SegmentBackupDAO();
        depositsDAO                  = new DepositsDAO();
       lifeDAO                       = new LifeDAO();
    }

    public static BucketAllocationDAO getBucketAllocationDAO()
    {
        return bucketAllocationDAO;
    }

    public static InvestmentDAO getInvestmentDAO()
    {
        return investmentDAO;
    }

    public static NoteReminderDAO getNoteReminderDAO()
    {
        return noteReminderDAO;
    }

    public static ContractClientAllocationDAO getContractClientAllocationDAO()
    {
        return contractClientAllocationDAO;
    }

    public static WithholdingDAO getWithholdingDAO()
    {
        return withholdingDAO;
    }

    public static InvestmentAllocationDAO getInvestmentAllocationDAO()
    {
        return investmentAllocationDAO;
    }

    public static BucketDAO getBucketDAO()
    {
        return bucketDAO;
    }

    public static ContractClientDAO getContractClientDAO()
    {
        return contractClientDAO;
    }

    public static ChangeHistoryDAO getChangeHistoryDAO() {

        return changeHistoryDAO;
    }

    public static SegmentDAO getSegmentDAO() {

        return segmentDAO;
    }

    public static PayoutDAO getPayoutDAO() {

        return payoutDAO;
    }

    public static ContractRequirementDAO getContractRequirementDAO() {

        return contractRequirementDAO;
    }

    public static RequirementDAO getRequirementDAO() {

        return requirementDAO;
    }

    public static FilteredRequirementDAO getFilteredRequirementDAO() {

        return filteredRequirementDAO;
    }

    public static AgentHierarchyDAO getAgentHierarchyDAO() {

        return agentHierarchyDAO;
    }

    public static AgentSnapshotDAO getAgentSnapshotDAO() {

        return agentSnapshotDAO;
    }

    public static SegmentBackupDAO getSegmentBackupDAO()
    {
        return segmentBackupDAO;
    }

    public static DepositsDAO getDepositsDAO()
    {
        return depositsDAO;
    }

    public static LifeDAO getLifeDAO()
    {
        return lifeDAO;
    }
}
