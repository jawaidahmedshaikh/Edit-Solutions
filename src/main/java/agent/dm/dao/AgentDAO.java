/*
 * User: gfrosti
 * Date: Jun 25, 2003
 * Time: 10:41:47 AM
 *
 * (c) 2000-2008 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */

package agent.dm.dao;

import edit.common.vo.AgentVO;
import edit.services.db.ConnectionFactory;
import edit.services.db.DAO;
import edit.services.db.DBTable;

import java.util.*;


public class AgentDAO extends DAO
{
    private final String POOLNAME;
    private final DBTable DBTABLE;
    private final String TABLENAME;


    public AgentDAO()
    {
        POOLNAME  = ConnectionFactory.EDITSOLUTIONS_POOL;
        DBTABLE   = DBTable.getDBTableForTable("Agent");
        TABLENAME = DBTABLE.getFullyQualifiedTableName();
    }

    //No longer used - agent is now parent to clientRole
    public AgentVO[] findByClientRolePK(long clientRolePK)
    {
        DBTable clientRoleDBTable = DBTable.getDBTableForTable("ClientRole");
        
        String clientRoleTableName = clientRoleDBTable.getFullyQualifiedTableName();
        
        String agentPKCol = DBTABLE.getDBColumn("AgentPK").getFullyQualifiedColumnName();
        String agentFKCol = clientRoleDBTable.getDBColumn("AgentFK").getFullyQualifiedColumnName();
        
        String clientRolePKCol = clientRoleDBTable.getDBColumn("ClientRolePK").getFullyQualifiedColumnName();

        String sql = " SELECT " + TABLENAME + ".* " +
                     " FROM " + TABLENAME +
                     " JOIN " + clientRoleTableName + 
                     " ON " + agentPKCol + " = " + agentFKCol + 
                     " WHERE " + clientRolePKCol + " = " + clientRolePK;

        return (AgentVO[]) executeQuery(AgentVO.class,
                                         sql,
                                          POOLNAME,
                                           false,
                                            null);
    }

    public AgentVO[] findByClientRolePKAndAgentType(long clientRolePK, String agentType)
    {
        String clientRoleFKCol = DBTABLE.getDBColumn("ClientRoleFK").getFullyQualifiedColumnName();
        String agentTypeCTCol  = DBTABLE.getDBColumn("AgentTeypCT").getFullyQualifiedColumnName();

        String sql = "SELECT * FROM " + TABLENAME +
                     " WHERE " + clientRoleFKCol + " = " + clientRolePK +
                     " AND " + agentTypeCTCol + " = '" + agentType + "'";

        return (AgentVO[]) executeQuery(AgentVO.class,
                                         sql,
                                          POOLNAME,
                                           false,
                                            null);
    }

    public AgentVO[] findByAgentPK(long agentPK)
    {
        String agentPKCol = DBTABLE.getDBColumn("AgentPK").getFullyQualifiedColumnName();

        String sql = "SELECT * FROM " + TABLENAME +
                     " WHERE " + agentPKCol + " = " + agentPK;

        return (AgentVO[]) executeQuery(AgentVO.class,
                                         sql,
                                          POOLNAME,
                                           false,
                                            null);
    }

    public AgentVO[] findAgentByAgentNumber(String agentNumber)
    {
        DBTable clientRoleDBTable = DBTable.getDBTableForTable("ClientRole");

        String clientRoleTable = clientRoleDBTable.getFullyQualifiedTableName();

        String referenceIDCol = clientRoleDBTable.getDBColumn("ReferenceID").getFullyQualifiedColumnName();

        String agentFKCol = clientRoleDBTable.getDBColumn("AgentFK").getFullyQualifiedColumnName();
        String agentPKCol = DBTABLE.getDBColumn("AgentPK").getFullyQualifiedColumnName();

        String sql = "SELECT * FROM " + TABLENAME + ", " + clientRoleTable +
                     " WHERE " + agentFKCol + " = " + agentPKCol +
                     " AND " + referenceIDCol + " = '" + agentNumber + "'";

        return (AgentVO[]) executeQuery(AgentVO.class,
                                         sql,
                                          POOLNAME,
                                           false,
                                            null);
    }

    public AgentVO[] findByAgentContractPK(long agentContractPK)
    {
        DBTable agentContractDBTable = DBTable.getDBTableForTable("AgentContract");

        String agentContractTable = agentContractDBTable.getFullyQualifiedTableName();

        String agentContractPKCol = agentContractDBTable.getDBColumn("AgentContractPK").getFullyQualifiedColumnName();
        String agentFKCol         = agentContractDBTable.getDBColumn("AgentFK").getFullyQualifiedColumnName();
        String agentPKCol         = DBTABLE.getDBColumn("AgentPK").getFullyQualifiedColumnName();

        String sql = "SELECT " + TABLENAME + ".* FROM " + TABLENAME + ", " + agentContractTable +
                     " WHERE " + agentFKCol + " = " + agentPKCol +
                     " AND " + agentContractPKCol + " = " + agentContractPK;

        return (AgentVO[]) executeQuery(AgentVO.class,
                                         sql,
                                          POOLNAME,
                                           false,
                                            null);
    }

    public AgentVO[] findByPlacedAgentPK(long placedAgentPK)
    {
        DBTable agentContractDBTable = DBTable.getDBTableForTable("AgentContract");
        DBTable placedAgentDBTable   = DBTable.getDBTableForTable("PlacedAgent");

        String agentContractTable = agentContractDBTable.getFullyQualifiedTableName();
        String placedAgentTable   = placedAgentDBTable.getFullyQualifiedTableName();

        String agentPKCol         = DBTABLE.getDBColumn("AgentPK").getFullyQualifiedColumnName();
        String agentFKCol         = agentContractDBTable.getDBColumn("AgentFK").getFullyQualifiedColumnName();
        String agentContractPKCol = agentContractDBTable.getDBColumn("AgentContractPK").getFullyQualifiedColumnName();
        String agentContractFKCol = placedAgentDBTable.getDBColumn("AgentContractFK").getFullyQualifiedColumnName();
        String placedAgentPKCol   = placedAgentDBTable.getDBColumn("PlacedAgentPK").getFullyQualifiedColumnName();

        String sql = "SELECT " + TABLENAME + ".* FROM " + TABLENAME + ", " + agentContractTable + ", " + placedAgentTable +
                     " WHERE " + agentFKCol + " = " + agentPKCol +
                     " AND " + agentContractPKCol + " = " + agentContractFKCol +
                     " AND " + placedAgentPKCol + " = " + placedAgentPK;

        return (AgentVO[]) executeQuery(AgentVO.class,
                                         sql,
                                          POOLNAME,
                                           false,
                                            null);
    }

    /**
     * Finder.
     * @param clientRoleFinancialPK
     * @return
     */
    public AgentVO[] findByClientRoleFinancialPK(long clientRoleFinancialPK)
    {
        // ClientRoleFinancial
        DBTable clientRoleFinancialDBTable = DBTable.getDBTableForTable("ClientRoleFinancial");
        String clientRoleFinancialTable = clientRoleFinancialDBTable.getFullyQualifiedTableName();
        String clientRoleFinancialPKCol = clientRoleFinancialDBTable.getDBColumn("ClientRoleFinancialPK").getFullyQualifiedColumnName();
        String clientRoleFKCol1 = clientRoleFinancialDBTable.getDBColumn("ClientRoleFK").getFullyQualifiedColumnName();

        // ClientRole
        DBTable clientRoleDBTable = DBTable.getDBTableForTable("ClientRole");
        String clientRoleTable = clientRoleDBTable.getFullyQualifiedTableName();
        String clientRolePKCol = clientRoleDBTable.getDBColumn("ClientRolePK").getFullyQualifiedColumnName();
        String agentFKCol       = clientRoleDBTable.getDBColumn("AgentFK").getFullyQualifiedColumnName();

        // Agent
        DBTable agentDBTable = DBTable.getDBTableForTable("Agent");
        String agentPKCol = agentDBTable.getDBColumn("AgentPK").getFullyQualifiedColumnName();
//        String clientRoleFKCol2 = agentDBTable.getDBColumn("ClientRoleFK").getFullyQualifiedColumnName();

        String sql;
        sql = "SELECT " + TABLENAME + ".*" +
                " FROM " + TABLENAME +
                " INNER JOIN " + clientRoleTable +
                " ON " + agentFKCol + " = " + agentPKCol +
                " INNER JOIN " + clientRoleFinancialTable +
                " ON " + clientRolePKCol + " = " + clientRoleFKCol1 +
                " WHERE " + clientRoleFinancialPKCol + " = " + clientRoleFinancialPK;

        return (AgentVO[]) executeQuery(AgentVO.class,
                                         sql,
                                          POOLNAME,
                                           false,
                                            null);
    }

    /**
     * Finder.
     * @param participatingAgentPK
     * @return
     */
    public AgentVO[] findBy_ParticipatingAgentPK(long participatingAgentPK)
    {
        // ParticipatingAgent
        DBTable participatingAgentDBTable = DBTable.getDBTableForTable("ParticipatingAgent");
        String participatingAgentTable = participatingAgentDBTable.getFullyQualifiedTableName();
        String participatingAgentPKCol = participatingAgentDBTable.getDBColumn("ParticipatingAgentPK").getFullyQualifiedColumnName();
        String placedAgentFKCol = participatingAgentDBTable.getDBColumn("PlacedAgentFK").getFullyQualifiedColumnName();

        // PlacedAgent
        DBTable placedAgentDBTable = DBTable.getDBTableForTable("PlacedAgent");
        String placedAgentTable = placedAgentDBTable.getFullyQualifiedTableName();
        String placedAgentPKCol = placedAgentDBTable.getDBColumn("PlacedAgentPK").getFullyQualifiedColumnName();
        String agentContractFKCol = placedAgentDBTable.getDBColumn("AgentContractFK").getFullyQualifiedColumnName();

        // AgentContract
        DBTable agentContractDBTable = DBTable.getDBTableForTable("AgentContract");
        String agentContractTable = agentContractDBTable.getFullyQualifiedTableName();
        String agentContractPKCol = agentContractDBTable.getDBColumn("AgentContractPK").getFullyQualifiedColumnName();
        String agentFKCol = agentContractDBTable.getDBColumn("AgentFK").getFullyQualifiedColumnName();

        // Agent
        String agentPKCol = DBTABLE.getDBColumn("AgentPK").getFullyQualifiedColumnName();

        String sql = null;
        sql = "SELECT " + TABLENAME + ".*" +
                " FROM " + participatingAgentTable +
                " INNER JOIN " + placedAgentTable +
                " ON " + placedAgentFKCol + " = " + placedAgentPKCol +
                " INNER JOIN " + agentContractTable +
                " ON " + agentContractFKCol + " = " + agentContractPKCol +
                " INNER JOIN " + TABLENAME +
                " ON " + agentFKCol + " = " + agentPKCol +
                " WHERE " + participatingAgentPKCol + " = " + participatingAgentPK;

        return (AgentVO[]) executeQuery(AgentVO.class,
                                         sql,
                                          POOLNAME,
                                           false,
                                            null);
    }

    /**
     * Finder.
     * @param agentGroupPK
     * @return
     */
    public AgentVO[] findBy_AgentGroupPK(long agentGroupPK)
    {
        // AgentGroup
        DBTable agentGroupDBTable = DBTable.getDBTableForTable("AgentGroup");
        String agentGroupTable = agentGroupDBTable.getFullyQualifiedTableName();
        String agentGroupPKCol = agentGroupDBTable.getDBColumn("AgentGroupPK").getFullyQualifiedColumnName();
        String agentFKCol = agentGroupDBTable.getDBColumn("AgentFK").getFullyQualifiedColumnName();

        // Agent
        String agentPKCol = DBTABLE.getDBColumn("AgentPK").getFullyQualifiedColumnName();

        String sql = " SELECT " + TABLENAME + ".* " +
                    " FROM " + agentGroupTable +
                    " INNER JOIN " + TABLENAME +
                    " ON " + agentFKCol + " = " + agentPKCol +
                    " WHERE " + agentGroupPKCol + " = " + agentGroupPK;

        return (AgentVO[]) executeQuery(AgentVO.class,
                                         sql,
                                          POOLNAME,
                                           false,
                                            null);
    }


    public AgentVO[] findByAgentPK(long agentPK, boolean includeChildren, List voExclusionList)
    {
        String agentPKCol = DBTABLE.getDBColumn("AgentPK").getFullyQualifiedColumnName();

        String sql = "SELECT * FROM " + TABLENAME +
                     " WHERE " + agentPKCol + " = " + agentPK;

        return (AgentVO[]) executeQuery(AgentVO.class,
                                         sql,
                                          POOLNAME,
                                           includeChildren,
                                            voExclusionList);
    }
}