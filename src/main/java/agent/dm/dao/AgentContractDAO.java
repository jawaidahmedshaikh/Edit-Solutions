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

import edit.common.vo.AgentContractVO;

import edit.services.db.ConnectionFactory;
import edit.services.db.DAO;
import edit.services.db.DBTable;


public class AgentContractDAO extends DAO
{
    private final String POOLNAME;
    private final DBTable DBTABLE;
    private final String TABLENAME;

    public AgentContractDAO()
    {
        POOLNAME = ConnectionFactory.EDITSOLUTIONS_POOL;
        DBTABLE = DBTable.getDBTableForTable("AgentContract");
        TABLENAME = DBTABLE.getFullyQualifiedTableName();
    }

    /**
     * Finder.
     * @param commissionContractPK
     * @return
     */
    public AgentContractVO[] findByCommissionContractPK(long commissionContractPK)
    {
        DBTable commissionContractDBTable = DBTable.getDBTableForTable("CommissionContract");

        String commissionContractTable = commissionContractDBTable.getFullyQualifiedTableName();

        String commissionContractFKCol = DBTABLE.getDBColumn("CommissionContractFK").getFullyQualifiedColumnName();
        String commissionContractPKCol = commissionContractDBTable.getDBColumn("CommissionContractPK").getFullyQualifiedColumnName();

        String sql = "SELECT " + TABLENAME + ".* FROM " + TABLENAME + ", " + commissionContractTable + " WHERE " + commissionContractFKCol + " = " + commissionContractPK + " AND " + commissionContractPKCol + " = " + commissionContractPK;

        return (AgentContractVO[]) executeQuery(AgentContractVO.class, sql, POOLNAME, false, null);
    }

    /**
     * Finder.
     * @param agentPK
     * @return
     */
    public AgentContractVO[] findByAgentPK(long agentPK)
    {
        String agentFKCol = DBTABLE.getDBColumn("AgentFK").getFullyQualifiedColumnName();

        String sql = "SELECT * FROM " + TABLENAME + " WHERE " + agentFKCol + " = " + agentPK;

        return (AgentContractVO[]) executeQuery(AgentContractVO.class, sql, POOLNAME, false, null);
    }

    /**
     * Finder.
     * @param agentContractPK
     * @return
     */
    public AgentContractVO[] findByAgentContractPK(long agentContractPK)
    {
        String agentContractPKCol = DBTABLE.getDBColumn("AgentContractPK").getFullyQualifiedColumnName();

        String sql = "SELECT * FROM " + TABLENAME + " WHERE " + agentContractPKCol + " = " + agentContractPK;

        return (AgentContractVO[]) executeQuery(AgentContractVO.class, sql, POOLNAME, false, null);
    }
}
