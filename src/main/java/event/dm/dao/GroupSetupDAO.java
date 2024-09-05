/*
 * Created by IntelliJ IDEA.
 * User: gfrosti
 * Date: Jun 25, 2003
 * Time: 10:41:47 AM
 *
 * (c) 2000-2004 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package event.dm.dao;

import edit.common.vo.GroupSetupVO;
import edit.services.db.ConnectionFactory;
import edit.services.db.DAO;
import edit.services.db.DBTable;
import contract.*;
import group.*;


public class GroupSetupDAO extends DAO
{
    private final String POOLNAME;

    private final DBTable GROUP_SETUP_DBTABLE;
    private final DBTable CONTRACT_SETUP_DBTABLE;
    private final DBTable CLIENT_SETUP_DBTABLE;
    private final DBTable EDITTRX_DBTABLE;

    private final String GROUP_SETUP_TABLENAME;
    private final String CONTRACT_SETUP_TABLENAME;
    private final String CLIENT_SETUP_TABLENAME;
    private final String EDITTRX_TABLENAME;


    public GroupSetupDAO()
    {
        POOLNAME  = ConnectionFactory.EDITSOLUTIONS_POOL;

        GROUP_SETUP_DBTABLE    = DBTable.getDBTableForTable("GroupSetup");
        CONTRACT_SETUP_DBTABLE = DBTable.getDBTableForTable("ContractSetup");
        CLIENT_SETUP_DBTABLE   = DBTable.getDBTableForTable("ClientSetup");
        EDITTRX_DBTABLE        = DBTable.getDBTableForTable("EDITTrx");

        GROUP_SETUP_TABLENAME    = GROUP_SETUP_DBTABLE.getFullyQualifiedTableName();
        CONTRACT_SETUP_TABLENAME = CONTRACT_SETUP_DBTABLE.getFullyQualifiedTableName();
        CLIENT_SETUP_TABLENAME   = CLIENT_SETUP_DBTABLE.getFullyQualifiedTableName();
        EDITTRX_TABLENAME        = EDITTRX_DBTABLE.getFullyQualifiedTableName();
    }

    public GroupSetupVO[] findByEditTrxPK(long editTrxPK)
    {
        String groupSetupPKCol    = GROUP_SETUP_DBTABLE.getDBColumn("GroupSetupPK").getFullyQualifiedColumnName();

        String groupSetupFKCol    = CONTRACT_SETUP_DBTABLE.getDBColumn("GroupSetupFK").getFullyQualifiedColumnName();
        String contractSetupPKCol = CONTRACT_SETUP_DBTABLE.getDBColumn("ContractSetupPK").getFullyQualifiedColumnName();

        String contractSetupFKCol = CLIENT_SETUP_DBTABLE.getDBColumn("ContractSetupFK").getFullyQualifiedColumnName();
        String clientSetupPKCol   = CLIENT_SETUP_DBTABLE.getDBColumn("ClientSetupPK").getFullyQualifiedColumnName();

        String clientSetupFKCol   = EDITTRX_DBTABLE.getDBColumn("ClientSetupFK").getFullyQualifiedColumnName();
        String editTrxPKCol       = EDITTRX_DBTABLE.getDBColumn("EDITTrxPK").getFullyQualifiedColumnName();

        String sql = "SELECT " + GROUP_SETUP_TABLENAME + ".* FROM " + GROUP_SETUP_TABLENAME + ", " + CONTRACT_SETUP_TABLENAME + ", " + CLIENT_SETUP_TABLENAME + ", " + EDITTRX_TABLENAME +
                     " WHERE " + groupSetupPKCol + " = " + groupSetupFKCol +
                     " AND " + contractSetupPKCol + " = " + contractSetupFKCol +
                     " AND " + clientSetupPKCol + " = " + clientSetupFKCol +
                     " AND " + editTrxPKCol + " = " + editTrxPK;

        return (GroupSetupVO[]) executeQuery(GroupSetupVO.class,
                                              sql,
                                               POOLNAME,
                                                false,
                                                 null);
    }

    public GroupSetupVO[] findByCommissionHistoryPK(long commissionHistoryPK)
    {
        DBTable editTrxHistoryDBTable    = DBTable.getDBTableForTable("EDITTrxHistory");
        DBTable commissionHistoryDBTable = DBTable.getDBTableForTable("CommissionHistory");

        String editTrxHistoryTable    = editTrxHistoryDBTable.getFullyQualifiedTableName();
        String commissionHistoryTable = commissionHistoryDBTable.getFullyQualifiedTableName();

        String groupSetupPKCol        = GROUP_SETUP_DBTABLE.getDBColumn("GroupSetupPK").getFullyQualifiedColumnName();

        String groupSetupFKCol        = CONTRACT_SETUP_DBTABLE.getDBColumn("GroupSetupFK").getFullyQualifiedColumnName();
        String contractSetupPKCol     = CONTRACT_SETUP_DBTABLE.getDBColumn("ContractSetupPK").getFullyQualifiedColumnName();

        String contractSetupFKCol     = CLIENT_SETUP_DBTABLE.getDBColumn("ContractSetupFK").getFullyQualifiedColumnName();
        String clientSetupPKCol       = CLIENT_SETUP_DBTABLE.getDBColumn("ClientSetupPK").getFullyQualifiedColumnName();

        String clientSetupFKCol       = EDITTRX_DBTABLE.getDBColumn("ClientSetupFK").getFullyQualifiedColumnName();
        String editTrxPKCol           = EDITTRX_DBTABLE.getDBColumn("EDITTrxPK").getFullyQualifiedColumnName();

        String editTrxFKCol           = editTrxHistoryDBTable.getDBColumn("EDITTrxFK").getFullyQualifiedColumnName();
        String editTrxHistoryPKCol    = editTrxHistoryDBTable.getDBColumn("EDITTrxHistoryPK").getFullyQualifiedColumnName();

        String editTrxHistoryFKCol    = commissionHistoryDBTable.getDBColumn("EDITTrxHistoryFK").getFullyQualifiedColumnName();
        String commissionHistoryPKCol = commissionHistoryDBTable.getDBColumn("CommissionHistoryPK").getFullyQualifiedColumnName();


        String sql = "SELECT " + GROUP_SETUP_TABLENAME + ".* FROM " + GROUP_SETUP_TABLENAME + ", " + CONTRACT_SETUP_TABLENAME + ", " + CLIENT_SETUP_TABLENAME + ", " + EDITTRX_TABLENAME + ", " + editTrxHistoryTable + ", " + commissionHistoryTable +
                     " WHERE " + groupSetupPKCol + " = " + groupSetupFKCol +
                     " AND " + contractSetupPKCol + " = " + contractSetupFKCol +
                     " AND " + clientSetupPKCol + " = " + clientSetupFKCol +
                     " AND " + editTrxPKCol + " = " + editTrxFKCol +
                     " AND " + editTrxHistoryPKCol + " = " + editTrxHistoryFKCol +
                     " AND " + commissionHistoryPKCol + " = " + commissionHistoryPK;

        return (GroupSetupVO[]) executeQuery(GroupSetupVO.class,
                                              sql,
                                               POOLNAME,
                                                false,
                                                 null);
    }

    public GroupSetupVO[] findBySegmentPK(long segmentPK)
    {
        String groupSetupPKCol = GROUP_SETUP_DBTABLE.getDBColumn("GroupSetupPK").getFullyQualifiedColumnName();

        String groupSetupFKCol = CONTRACT_SETUP_DBTABLE.getDBColumn("GroupSetupFK").getFullyQualifiedColumnName();
        String segmentFKCol    = CONTRACT_SETUP_DBTABLE.getDBColumn("SegmentFK").getFullyQualifiedColumnName();

        String sql = "SELECT " + GROUP_SETUP_TABLENAME + ".* FROM " + GROUP_SETUP_TABLENAME + ", " + CONTRACT_SETUP_TABLENAME +
                     " WHERE " + groupSetupPKCol + " = " + groupSetupFKCol +
                     " AND " + segmentFKCol + " = " + segmentPK;

        return (GroupSetupVO[]) executeQuery(GroupSetupVO.class,
                                              sql,
                                               POOLNAME,
                                                false,
                                                 null);
    }

    /**
     * Retrieves all Pending GroupSetupVOs for the given segment and transaction type
     * @param segmentPK
     * @param trxType
     * @return
     */
    public GroupSetupVO[] findBySegmentPKAndTrxType(long segmentPK, String trxType)
    {
        String groupSetupPKCol = GROUP_SETUP_DBTABLE.getDBColumn("GroupSetupPK").getFullyQualifiedColumnName();

        String contractSetupPKCol = CONTRACT_SETUP_DBTABLE.getDBColumn("ContractSetupPK").getFullyQualifiedColumnName();
        String groupSetupFKCol = CONTRACT_SETUP_DBTABLE.getDBColumn("GroupSetupFK").getFullyQualifiedColumnName();
        String segmentFKCol    = CONTRACT_SETUP_DBTABLE.getDBColumn("SegmentFK").getFullyQualifiedColumnName();

        String contractSetupFKCol = CLIENT_SETUP_DBTABLE.getDBColumn("ContractSetupFK").getFullyQualifiedColumnName();
        String clientSetupPKCol = CLIENT_SETUP_DBTABLE.getDBColumn("ClientSetupPK").getFullyQualifiedColumnName();

        String clientSetupFKCol = EDITTRX_DBTABLE.getDBColumn("ClientSetupFK").getFullyQualifiedColumnName();
        String transactionTypeCTCol = EDITTRX_DBTABLE.getDBColumn("TransactionTypeCT").getFullyQualifiedColumnName();
        String pendingStatusCol = EDITTRX_DBTABLE.getDBColumn("PendingStatus").getFullyQualifiedColumnName();

        String sql = "SELECT " + GROUP_SETUP_TABLENAME + ".* FROM " + GROUP_SETUP_TABLENAME + ", " +
                     CONTRACT_SETUP_TABLENAME + ", " + CLIENT_SETUP_TABLENAME + ", " + EDITTRX_TABLENAME +
                     " WHERE " + groupSetupPKCol + " = " + groupSetupFKCol +
                     " AND " + contractSetupPKCol + " = " + contractSetupFKCol +
                     " AND " + clientSetupPKCol + " = " + clientSetupFKCol +
                     " AND " + segmentFKCol + " = " + segmentPK +
                     " AND " + transactionTypeCTCol + " = '" + trxType + "'" +
                     " AND " + pendingStatusCol + " = 'P'";

        return (GroupSetupVO[]) executeQuery(GroupSetupVO.class,
                                             sql,
                                             POOLNAME,
                                             false,
                                             null);
    }

    public GroupSetupVO[] findByContractSetupPK(long contractSetupPK)
    {
        String groupSetupPKCol = GROUP_SETUP_DBTABLE.getDBColumn("GroupSetupPK").getFullyQualifiedColumnName();

        String groupSetupFKCol    = CONTRACT_SETUP_DBTABLE.getDBColumn("GroupSetupFK").getFullyQualifiedColumnName();
        String contractSetupPKCol = CONTRACT_SETUP_DBTABLE.getDBColumn("ContractSetupPK").getFullyQualifiedColumnName();

        String sql = "SELECT " + GROUP_SETUP_TABLENAME + ".* FROM " + GROUP_SETUP_TABLENAME + ", " + CONTRACT_SETUP_TABLENAME +
                     " WHERE " + groupSetupPKCol + " = " + groupSetupFKCol +
                     " AND " + contractSetupPKCol + " = " + contractSetupPK;

        return (GroupSetupVO[]) executeQuery(GroupSetupVO.class,
                                              sql,
                                               POOLNAME,
                                                false,
                                                 null);
    }

    public GroupSetupVO findByGroupSetupPK(long groupSetupPK)
    {
        String groupSetupPKCol = GROUP_SETUP_DBTABLE.getDBColumn("GroupSetupPK").getFullyQualifiedColumnName();

        // What was the purpose of those other tables?
        String sql = "SELECT * FROM " + GROUP_SETUP_TABLENAME + // ", " + CONTRACT_SETUP_TABLENAME + ", " + CLIENT_SETUP_TABLENAME + ", " + EDITTRX_TABLENAME +
                     " WHERE " + groupSetupPKCol + " = " + groupSetupPK;

        GroupSetupVO[] groupSetupVO = (GroupSetupVO[]) executeQuery(GroupSetupVO.class,
                                                                     sql,
                                                                      POOLNAME,
                                                                       false,
                                                                        null); 

        GroupSetupVO groupSetupToReturn = null;

        if (groupSetupVO != null)
        {
            groupSetupToReturn = groupSetupVO[0];
        }

        return groupSetupToReturn;
    }

    /**
     * Finds all GroupSetups with a groupTypeCT of grouping.  Grouping groupTypes are Case and ListBill.
     *
     * @return  array of GroupSetupVOs
     */
    public GroupSetupVO[] findByGroupTypeCT_OfGroup()
    {
        String groupSetupPKCol = GROUP_SETUP_DBTABLE.getDBColumn("GroupTypeCT").getFullyQualifiedColumnName();

        String sql = "SELECT * FROM " + GROUP_SETUP_TABLENAME +
                     " WHERE " + groupSetupPKCol + " = '" + ContractGroup.CONTRACTGROUPTYPECT_CASE + "'" +
                     " OR " + groupSetupPKCol + " = '" + ContractGroup.CONTRACTGROUPTYPECT_GROUP + "'";


        GroupSetupVO[] groupSetupVOs = (GroupSetupVO[]) executeQuery(GroupSetupVO.class,
                                                                     sql,
                                                                      POOLNAME,
                                                                       false,
                                                                        null);

        return groupSetupVOs;
    }
}