/*
 * Created by IntelliJ IDEA.
 * User: gfrosti
 * Date: Jul 17, 2003
 * Time: 3:29:36 PM
 *
 * (c) 2000-2004 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package client.dm.dao;

import edit.common.vo.PreferenceVO;
import edit.services.db.ConnectionFactory;
import edit.services.db.DAO;
import edit.services.db.DBTable;

import java.util.List;


public class PreferenceDAO extends DAO
{
    private final String POOLNAME;
    private final DBTable DBTABLE;
    private final String TABLENAME;


    public PreferenceDAO()
    {
        POOLNAME  = ConnectionFactory.EDITSOLUTIONS_POOL;
        DBTABLE   = DBTable.getDBTableForTable("Preference");
        TABLENAME = DBTABLE.getFullyQualifiedTableName();
    }

    public PreferenceVO[] findByPreferencePK(long preferencePK, boolean includeChildVOs, List voExclusionList)
    {
        String preferencePKCol = DBTABLE.getDBColumn("PreferencePK").getFullyQualifiedColumnName();

        String sql = "SELECT * FROM " + TABLENAME +
                     " WHERE " + preferencePKCol + " = " + preferencePK;

        return (PreferenceVO[]) executeQuery(PreferenceVO.class,
                                              sql,
                                               POOLNAME,
                                                includeChildVOs,
                                                 voExclusionList);
    }

    public PreferenceVO[] findPrimaryByClientDetailPK(long clientDetailPK, boolean includeChildVOs, List voExclusionList)
    {
        String clientDetailFKCol = DBTABLE.getDBColumn("ClientDetailFK").getFullyQualifiedColumnName();
        String overrideStatusCol = DBTABLE.getDBColumn("OverrideStatus").getFullyQualifiedColumnName();

        String sql = "SELECT * FROM " + TABLENAME +
                     " WHERE " + clientDetailFKCol + " = " + clientDetailPK +
                     " AND " + overrideStatusCol + " = 'P'";

        return (PreferenceVO[]) executeQuery(PreferenceVO.class,
                                              sql,
                                               POOLNAME,
                                                includeChildVOs,
                                                 voExclusionList);
    }

    public PreferenceVO[] findByPK(long preferencePK)
    {
        String preferencePKCol = DBTABLE.getDBColumn("PreferencePK").getFullyQualifiedColumnName();

        String sql = "SELECT * FROM " + TABLENAME +
                     " WHERE " + preferencePKCol + " = " + preferencePK;

        return (PreferenceVO[]) executeQuery(PreferenceVO.class,
                                              sql,
                                               POOLNAME,
                                                false,
                                                 null);
    }

    /**
     * Finder.
     * @param clientRoleFinancialPK
     * @param overrideStatus
     * @return
     */
    public PreferenceVO[] findByClientRoleFinancialPK_OverrideStatus(long clientRoleFinancialPK, String overrideStatus)
    {
        // ClientRoleFinancial
        DBTable clientRoleFinancialDBTable = DBTable.getDBTableForTable("ClientRoleFinancial");
        String clientRoleFinancialTable = clientRoleFinancialDBTable.getFullyQualifiedTableName();
        String clientRoleFinancialPKCol = clientRoleFinancialDBTable.getDBColumn("ClientRoleFinancialPK").getFullyQualifiedColumnName();
        String clientRoleFKCol = clientRoleFinancialDBTable.getDBColumn("ClientRoleFK").getFullyQualifiedColumnName();

        // ClientRole
        DBTable clientRoleDBTable = DBTable.getDBTableForTable("ClientRole");
        String clientRoleTable = clientRoleDBTable.getFullyQualifiedTableName();
        String clientRolePKCol = clientRoleDBTable.getDBColumn("ClientRolePK").getFullyQualifiedColumnName();
        String clientDetailFKCol1 = clientRoleDBTable.getDBColumn("ClientDetailFK").getFullyQualifiedColumnName();

        // ClientDetail
        DBTable clientDetailDBTable = DBTable.getDBTableForTable("ClientDetail");
        String clientDetailTable = clientDetailDBTable.getFullyQualifiedTableName();
        String clientDetailPKCol = clientDetailDBTable.getDBColumn("ClientDetailPK").getFullyQualifiedColumnName();

        // Preference
        String clientDetailFKCol2 = DBTABLE.getDBColumn("ClientDetailFK").getFullyQualifiedColumnName();
        String overrideStatusCol = DBTABLE.getDBColumn("OverrideStatus").getFullyQualifiedColumnName();

        String sql;
        sql = "SELECT " + TABLENAME + ".*" +
                " FROM " + clientRoleFinancialTable +
                " INNER JOIN " + clientRoleTable +
                " ON " + clientRoleFKCol + " = " + clientRolePKCol +
                " INNER JOIN " + clientDetailTable +
                " ON " + clientDetailFKCol1 + " = " + clientDetailPKCol +
                " INNER JOIN " + TABLENAME +
                " ON " + clientDetailPKCol + " = " + clientDetailFKCol2 +
                " WHERE " + clientRoleFinancialPKCol + " = " + clientRoleFinancialPK +
                " AND " + overrideStatusCol + " = '" + overrideStatus + "'";

        return (PreferenceVO[]) executeQuery(PreferenceVO.class,
                                              sql,
                                               POOLNAME,
                                                false,
                                                 null);
    }

    /**
     * Finder.
     * @param placedAgentPK
     * @param overrideStatus
     * @return
     */
    public PreferenceVO[] findByPlacedAgentPK_OverrideStatus(long placedAgentPK, String overrideStatus)
    {
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
        DBTable agentDBTable = DBTable.getDBTableForTable("Agent");
        String agentTable = agentDBTable.getFullyQualifiedTableName();
        String agentPKCol = agentDBTable.getDBColumn("AgentPK").getFullyQualifiedColumnName();
        String clientRoleFKCol = agentDBTable.getDBColumn("ClientRoleFK").getFullyQualifiedColumnName();

        // ClientRole
        DBTable clientRoleDBTable = DBTable.getDBTableForTable("ClientRole");
        String clientRoleTable = clientRoleDBTable.getFullyQualifiedTableName();
        String clientRolePKCol = clientRoleDBTable.getDBColumn("ClientRolePK").getFullyQualifiedColumnName();
        String clientDetailFKCol1 = clientRoleDBTable.getDBColumn("ClientDetailFK").getFullyQualifiedColumnName();

        // ClientDetail
        DBTable clientDetailDBTable = DBTable.getDBTableForTable("ClientDetail");
        String clientDetailTable = clientDetailDBTable.getFullyQualifiedTableName();
        String clientDetailPKCol = clientDetailDBTable.getDBColumn("ClientDetailPK").getFullyQualifiedColumnName();

        // Preference
        String clientDetailFKCol2 = DBTABLE.getDBColumn("ClientDetailFK").getFullyQualifiedColumnName();
        String overrideStatusCol = DBTABLE.getDBColumn("OverrideStatus").getFullyQualifiedColumnName();

        String sql;
        sql = "SELECT " + TABLENAME + ".*" +
                " FROM " + TABLENAME +
                " INNER JOIN " + clientDetailTable +
                " ON " + clientDetailFKCol2 + " = " + clientDetailPKCol +
                " INNER JOIN " + clientRoleTable +
                " ON " + clientDetailPKCol + " = " + clientDetailFKCol1 +
                " INNER JOIN " + agentTable +
                " ON " + clientRolePKCol + " = " + clientRoleFKCol +
                " INNER JOIN " + agentContractTable +
                " ON " + agentPKCol + " = " + agentFKCol +
                " INNER JOIN " + placedAgentTable +
                " ON " + agentContractPKCol + " = " + agentContractFKCol +
                " WHERE " + placedAgentPKCol + " = " + placedAgentPK +
                " AND " + overrideStatusCol + " = '" + overrideStatus + "'";

        return (PreferenceVO[]) executeQuery(PreferenceVO.class,
                                              sql,
                                               POOLNAME,
                                                false,
                                                 null);    }
}
