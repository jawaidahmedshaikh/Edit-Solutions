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
package agent.dm.dao;

import edit.common.vo.*;

import edit.services.db.ConnectionFactory;
import edit.services.db.DAO;
import edit.services.db.DBTable;


public class CheckAdjustmentDAO extends DAO
{
    private final String POOLNAME;
    private final DBTable DBTABLE;
    private final String TABLENAME;

    public CheckAdjustmentDAO()
    {
        POOLNAME = ConnectionFactory.EDITSOLUTIONS_POOL;
        DBTABLE = DBTable.getDBTableForTable("CheckAdjustment");
        TABLENAME = DBTABLE.getFullyQualifiedTableName();
    }


    /**
     * Finder.
     * @param agentPK
     * @return
     */
    public CheckAdjustmentVO[] findByAgentPK(long agentPK)
    {
        DBTable editTrxDBTable = DBTable.getDBTableForTable("EDITTrx");
        String editTrxTable = editTrxDBTable.getFullyQualifiedTableName();

        String agentFKCol = DBTABLE.getDBColumn("AgentFK").getFullyQualifiedColumnName();
        String checkAdjustmentPKCol = DBTABLE.getDBColumn("CheckAdjustmentPK").getFullyQualifiedColumnName();

        String checkAdjustmentFKCol    = editTrxDBTable.getDBColumn("CheckAdjustmentFK").getFullyQualifiedColumnName();
        String pendingStatusCol    = editTrxDBTable.getDBColumn("PendingStatus").getFullyQualifiedColumnName();

        String sql = "SELECT * FROM " + TABLENAME +  ", " +  editTrxTable +
                     " WHERE " + agentFKCol + " = " + agentPK +
                     " AND (" + checkAdjustmentPKCol + " = " + checkAdjustmentFKCol +
                     " AND NOT " + pendingStatusCol + " = 'H')";

        System.out.println("sql = " + sql);
        
        return (CheckAdjustmentVO[]) executeQuery(CheckAdjustmentVO.class, sql, POOLNAME, false, null);
    }

    public CheckAdjustmentVO[] findByPK(long checkAdjustmentPK)
    {
        String checkAdjustmentPKCol = DBTABLE.getDBColumn("CheckAdjustmentPK").getFullyQualifiedColumnName();

        String sql = "SELECT * FROM " + TABLENAME +
                     " WHERE " + checkAdjustmentPKCol + " = " + checkAdjustmentPK;

        return (CheckAdjustmentVO[]) executeQuery(CheckAdjustmentVO.class, sql, POOLNAME, false, null);
    }
}
