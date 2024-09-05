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
package role.dm.dao;

import edit.common.vo.ClientRoleFinancialVO;
import edit.common.vo.ClientRoleVO;
import edit.services.db.ConnectionFactory;
import edit.services.db.DAO;
import edit.services.db.DBTable;
import client.Preference;


public class ClientRoleFinancialDAO extends DAO
{
    private final String POOLNAME;
    private final DBTable DBTABLE;
    private final String TABLENAME;


    public ClientRoleFinancialDAO()
    {
        POOLNAME  = ConnectionFactory.EDITSOLUTIONS_POOL;
        DBTABLE   = DBTable.getDBTableForTable("ClientRoleFinancial");
        TABLENAME = DBTABLE.getFullyQualifiedTableName();
    }

	public ClientRoleFinancialVO[] findByAgentPK(long agentPK)
    {
        DBTable clientRoleDBTable = DBTable.getDBTableForTable("ClientRole");
//        DBTable agentDBTable      = DBTable.getDBTableForTable("Agent");

        String clientRoleTable = clientRoleDBTable.getFullyQualifiedTableName();
//        String agentTable      = agentDBTable.getFullyQualifiedTableName();

        String clientRolePKCol                    = DBTABLE.getDBColumn("ClientRolePK").getFullyQualifiedColumnName();
        String clientRoleFinancialClientRoleFKCol = DBTABLE.getDBColumn("ClientRoleFK").getFullyQualifiedColumnName();

        String agentFKCol = clientRoleDBTable.getDBColumn("AgnetFK").getFullyQualifiedColumnName();

//        String agentClientRoleFKCol = agentDBTable.getDBColumn("ClientRoleFK").getFullyQualifiedColumnName();
//        String agentPKCol           = agentDBTable.getDBColumn("AgentPK").getFullyQualifiedColumnName();

        String sql = " SELECT " + TABLENAME + ".* FROM " + clientRoleTable +
                     " WHERE " + clientRoleFinancialClientRoleFKCol + " = " + clientRolePKCol +
                     " AND " + agentFKCol + " = " + agentPK;

        return (ClientRoleFinancialVO[]) executeQuery(ClientRoleVO.class,
                                                sql,
                                                 POOLNAME,
                                                  false,
                                                   null);
	}

	public ClientRoleFinancialVO[] findByClientRolePK(long clientRolePK)
    {
        String clientRoleFKCol = DBTABLE.getDBColumn("ClientRoleFK").getFullyQualifiedColumnName();

        String sql = " SELECT * FROM " + TABLENAME +
                     " WHERE " + clientRoleFKCol + " = " + clientRolePK;

        return (ClientRoleFinancialVO[]) executeQuery(ClientRoleFinancialVO.class,
                                                sql,
                                                 POOLNAME,
                                                  false,
                                                   null);
	}

    /**
     * Finder.
     * @param commissionBalance
     * @param redirectBalance
     * @param disbursementSourceCT
     * @param paymentModeCT
     * @return
     */
	public ClientRoleFinancialVO[] findByCommissionBalanceGT_AND_RedirectBalanceGT_AND_DisbursementSourceCT_AND_PaymentModeCT(double commissionBalance, double redirectBalance, String disbursementSourceCT, String paymentModeCT)
    {
        // ClientRoleFinancial
        String commBalanceCol     = DBTABLE.getDBColumn("CommBalance").getFullyQualifiedColumnName();
        String redirectBalanceCol = DBTABLE.getDBColumn("RedirectBalance").getFullyQualifiedColumnName();
        String clientRoleFKCol = DBTABLE.getDBColumn("ClientRoleFK").getFullyQualifiedColumnName();

        // ClientRole
        DBTable clientRoleDBTable = DBTable.getDBTableForTable("ClientRole");
        String clientRoleTable = clientRoleDBTable.getFullyQualifiedTableName();
        String clientRolePKCol = clientRoleDBTable.getDBColumn("ClientRolePK").getFullyQualifiedColumnName();
        String clientDetailFKCol1 = clientRoleDBTable.getDBColumn("ClientDetailFK").getFullyQualifiedColumnName();
        String preferenceFKCol = clientRoleDBTable.getDBColumn("PreferenceFK").getFullyQualifiedColumnName();

        // ClientDetail
        DBTable clientDetailDBTable = DBTable.getDBTableForTable("ClientDetail");
        String clientDetailTable = clientDetailDBTable.getFullyQualifiedTableName();
        String clientDetailPKCol = clientDetailDBTable.getDBColumn("ClientDetailPK").getFullyQualifiedColumnName();

        // Preference
        DBTable preferenceDBTable = DBTable.getDBTableForTable("Preference");
        String preferenceTable = preferenceDBTable.getFullyQualifiedTableName();
        String clientDetailFKCol2 = preferenceDBTable.getDBColumn("ClientDetailFK").getFullyQualifiedColumnName();
        String disbursementSourceCTCol = preferenceDBTable.getDBColumn("DisbursementSourceCT").getFullyQualifiedColumnName();
        String paymentModeCTCol = preferenceDBTable.getDBColumn("PaymentModeCT").getFullyQualifiedColumnName();
        String overrideStatusCol = preferenceDBTable.getDBColumn("OverrideStatus").getFullyQualifiedColumnName();
        String preferencePKCol = preferenceDBTable.getDBColumn("PreferencePK").getFullyQualifiedColumnName();
        String preferenceTypeCTCol = preferenceDBTable.getDBColumn("PreferenceTypeCT").getFullyQualifiedColumnName();

        String sql = " SELECT " + TABLENAME + ".* FROM " + TABLENAME +
                     " INNER JOIN " + clientRoleTable +
                     " ON " + clientRoleFKCol + " = " + clientRolePKCol +
                     " INNER JOIN " + clientDetailTable +
                     " ON " + clientDetailFKCol1 + " = " + clientDetailPKCol +
                     " INNER JOIN " + preferenceTable +
                     " ON " + clientDetailPKCol + " = " + clientDetailFKCol2 +
                     " WHERE (" + commBalanceCol + " > " + commissionBalance +
                     " OR " + redirectBalanceCol + " > " + redirectBalance + ")" +
                     " AND " + disbursementSourceCTCol + " = '" + disbursementSourceCT + "'" +
                     " AND " + paymentModeCTCol + " = '" + paymentModeCT + "'" +
                     " AND " + overrideStatusCol + " = '" + Preference.PRIMARY + "'" +
                     " AND " + preferenceFKCol + " is null" + 
                     " AND " + preferenceTypeCTCol + " = '" + Preference.TYPE_DISBURSEMENT + "'" + 
            
                     " UNION " + 
            
                     " SELECT " + TABLENAME + ".* FROM " + TABLENAME +
                     " INNER JOIN " + clientRoleTable +
                     " ON " + clientRoleFKCol + " = " + clientRolePKCol +
                     " INNER JOIN " + preferenceTable +
                     " ON " + preferenceFKCol + " = " + preferencePKCol +
                     " WHERE (" + commBalanceCol + " > " + commissionBalance +
                     " OR " + redirectBalanceCol + " > " + redirectBalance + ")" +
                     " AND " + disbursementSourceCTCol + " = '" + disbursementSourceCT + "'" +
                     " AND " + paymentModeCTCol + " = '" + paymentModeCT + "'";

        return (ClientRoleFinancialVO[]) executeQuery(ClientRoleFinancialVO.class,
                                                sql,
                                                 POOLNAME,
                                                  false,
                                                   null);
	}

    public ClientRoleFinancialVO[] findAll()
    {
        String sql = " SELECT * FROM " + TABLENAME;

        return (ClientRoleFinancialVO[]) executeQuery(ClientRoleFinancialVO.class,
                                                sql,
                                                 POOLNAME,
                                                  false,
                                                   null);
    }

    /**
     * Finder.
     * @param agentContractPK
     * @return
     */
    public ClientRoleFinancialVO[] findByAgentContractPK(long agentContractPK)
    {
        // ClientRoleFinancial
        String clientRoleFKCol1 = DBTABLE.getDBColumn("ClientRoleFK").getFullyQualifiedColumnName();

        // ClientRole
        DBTable clientRoleDBTable = DBTable.getDBTableForTable("ClientRole");
        String clientRoleTable = clientRoleDBTable.getFullyQualifiedTableName();
        String clientRolePKCol = clientRoleDBTable.getDBColumn("ClientRolePK").getFullyQualifiedColumnName();
        String crAgentFKCol = clientRoleDBTable.getDBColumn("AgentFK").getFullyQualifiedColumnName();

        // Agent
        DBTable agentDBTable = DBTable.getDBTableForTable("Agent");
        String agentTable = agentDBTable.getFullyQualifiedTableName();
        String agentPKCol = agentDBTable.getDBColumn("AgentPK").getFullyQualifiedColumnName();
//        String clientRoleFKCol2 = agentDBTable.getDBColumn("ClientRoleFK").getFullyQualifiedColumnName();

        // AgentContract
        DBTable agentContractDBTable = DBTable.getDBTableForTable("AgentContract");
        String agentContractTable = agentContractDBTable.getFullyQualifiedTableName();
        String agentFKCol = agentContractDBTable.getDBColumn("AgentFK").getFullyQualifiedColumnName();
        String agentContractPKCol = agentContractDBTable.getDBColumn("AgentContractPK").getFullyQualifiedColumnName();

        String sql;
        sql = " SELECT " + TABLENAME + ".*" +
                " FROM " + TABLENAME +
                " INNER JOIN " + clientRoleTable +
                " ON " + clientRoleFKCol1 + " = " + clientRolePKCol +
                " INNER JOIN " + agentContractTable +
                " ON " + crAgentFKCol + " = " + agentFKCol +
                " WHERE " + agentContractPKCol + " = " + agentContractPK;

        return (ClientRoleFinancialVO[]) executeQuery(ClientRoleFinancialVO.class,
                                                sql,
                                                 POOLNAME,
                                                  false,
                                                   null);
    }
}