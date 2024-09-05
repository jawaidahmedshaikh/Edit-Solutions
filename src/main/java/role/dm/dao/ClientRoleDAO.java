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

import edit.common.vo.ClientRoleVO;
import edit.services.db.*;

import java.util.List;
import java.sql.*;



public class ClientRoleDAO extends DAO
{
    private final String POOLNAME;
    private final DBTable DBTABLE;
    private final String TABLENAME;


    public ClientRoleDAO()
    {
        POOLNAME  = ConnectionFactory.EDITSOLUTIONS_POOL;
        DBTABLE   = DBTable.getDBTableForTable("ClientRole");
        TABLENAME = DBTABLE.getFullyQualifiedTableName();
    }

    public ClientRoleVO[] findByAgentPK_AND_RedirectEffectiveDate(long agentPK, String redirectEffectiveDate)
    {
        ClientRoleVO[] clientRoleVOs = null;

        DBTable agentDBTable    = DBTable.getDBTableForTable("Agent");
        DBTable redirectDBTable = DBTable.getDBTableForTable("Redirect");

        String agentTable    = agentDBTable.getFullyQualifiedTableName();
        String redirectTable = redirectDBTable.getFullyQualifiedTableName();

        String clientRolePKCol = DBTABLE.getDBColumn( "ClientRolePK").getFullyQualifiedColumnName();

        String agentPKCol = agentDBTable.getDBColumn("AgentPK").getFullyQualifiedColumnName();

        String agentFKCol      = redirectDBTable.getDBColumn("AgentFK").getFullyQualifiedColumnName();
        String clientRoleFKCol = redirectDBTable.getDBColumn("ClientRoleFK").getFullyQualifiedColumnName();
        String startDateCol    = redirectDBTable.getDBColumn("StartDate").getFullyQualifiedColumnName();
        String stopDateCol     = redirectDBTable.getDBColumn("StopDate").getFullyQualifiedColumnName();

        String sql = " SELECT " + TABLENAME + ".* FROM " + agentTable + ", " + redirectTable + ", " + TABLENAME +
                     " WHERE " + agentPKCol + " = " + agentFKCol +
                     " AND " + clientRoleFKCol + " = " + clientRolePKCol +
                     " AND " + startDateCol + " <= ?" +
                     " AND " + stopDateCol + " >= ?" +
                     " AND " + agentPKCol + " = ?";

        Connection conn = null;

        PreparedStatement ps = null;

        try
        {
            conn = ConnectionFactory.getSingleton().getConnection(POOLNAME);

            ps = conn.prepareStatement(sql);

            ps.setDate(1, DBUtil.convertStringToDate(redirectEffectiveDate));
            ps.setDate(2, DBUtil.convertStringToDate(redirectEffectiveDate));
            ps.setLong(3, agentPK);

            clientRoleVOs = (ClientRoleVO[]) executeQuery(ClientRoleVO.class,
                                                     ps,
                                                      POOLNAME,
                                                       false,
                                                        null);
        }
        catch (Exception e)
        {
            System.out.println(e);
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        finally
        {
            try
            {
              if (ps != null) ps.close();
              ps = null;
              if (conn != null) conn.close();
              conn = null;
            }
            catch (SQLException e)
            {
                System.out.println(e);
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }

        return clientRoleVOs;

    }

    public ClientRoleVO[] findByClientRolePKAndRoleType(long clientRolePK, String roleTypeCT, boolean includeChildVOs, List voExclusionList)
    {
        String clientRolePKCol = DBTABLE.getDBColumn("ClientRolePK").getFullyQualifiedColumnName();
        String roleTypeCTCol   = DBTABLE.getDBColumn("RoleTypeCT").getFullyQualifiedColumnName();

        String sql = " SELECT * FROM " + TABLENAME +
                     " WHERE " + clientRolePKCol + " = " + clientRolePK +
                     " AND " + roleTypeCTCol + " = '" + roleTypeCT + "'";

        return (ClientRoleVO[]) executeQuery(ClientRoleVO.class,
                sql,
                POOLNAME,
                includeChildVOs,
                voExclusionList);
    }

    public ClientRoleVO[] findByClientRolePK(long clientRolePK, boolean includeChildVOs, List voExclusionList)
    {
        String clientRolePKCol = DBTABLE.getDBColumn("ClientRolePK").getFullyQualifiedColumnName();

        String sql = " SELECT * FROM " + TABLENAME +
                     " WHERE " + clientRolePKCol + " = " + clientRolePK;

        return (ClientRoleVO[]) executeQuery(ClientRoleVO.class,
                sql,
                POOLNAME,
                includeChildVOs,
                voExclusionList);
    }

    public ClientRoleVO[] findByAgentFK(long agentFK, boolean includeChildVOs, List voExclusionList)
    {
        String agentFKCol = DBTABLE.getDBColumn("AgentFK").getFullyQualifiedColumnName();

        String sql = " SELECT * FROM " + TABLENAME +
                     " WHERE " + agentFKCol + " = " + agentFK;

        return (ClientRoleVO[]) executeQuery(ClientRoleVO.class,
                sql,
                POOLNAME,
                includeChildVOs,
                voExclusionList);
    }

    public ClientRoleVO[] findByClientDetailFK(long clientDetailFK, boolean includeChildVOs, List voExclusionList)
    {
        String clientDetailFKCol = DBTABLE.getDBColumn("ClientDetailFK").getFullyQualifiedColumnName();

        String sql = " SELECT * FROM " + TABLENAME +
                     " WHERE " + clientDetailFKCol + " = " + clientDetailFK;

        return (ClientRoleVO[]) executeQuery(ClientRoleVO.class,
                sql,
                POOLNAME,
                includeChildVOs,
                voExclusionList);
    }

    public ClientRoleVO[] findByRoleTypeClientDetailFKStatus(String roleType, long clientDetailFK, String overrideStatus)
    {
        String clientDetailFKCol = DBTABLE.getDBColumn("ClientDetailFK").getFullyQualifiedColumnName();
        String roleTypeCTCol     = DBTABLE.getDBColumn("RoleTypeCT").getFullyQualifiedColumnName();
        String overrideStatusCol = DBTABLE.getDBColumn("OverrideStatus").getFullyQualifiedColumnName();

        String sql = " SELECT * FROM " + TABLENAME +
                     " WHERE " + clientDetailFKCol + " = " + clientDetailFK +
                     " AND " + roleTypeCTCol + " = '" + roleType + "'" +
                     " AND " + overrideStatusCol + " = '" + overrideStatus + "'";

        return (ClientRoleVO[]) executeQuery(ClientRoleVO.class,
                sql,
                POOLNAME,
                false,
                null);
    }

    public ClientRoleVO[] findByAllKeysAndRoleType(long clientDetailFK, long bankAcctInfoFK, long preferenceFK,
                                                   long taxProfileFK, String roleTypeCT, boolean includeChildVOs,
                                                   List voExclusionList)
    {
        String clientDetailFKCol           = DBTABLE.getDBColumn("ClientDetailFK").getFullyQualifiedColumnName();
        String bankAccountInformationFKCol = DBTABLE.getDBColumn("BankAccountInformationFK").getFullyQualifiedColumnName();
        String preferenceFKCol             = DBTABLE.getDBColumn("PreferenceFK").getFullyQualifiedColumnName();
        String taxProfileFKCol             = DBTABLE.getDBColumn("TaxProfileFK").getFullyQualifiedColumnName();
        String roleTypeCTCol               = DBTABLE.getDBColumn("RoleTypeCT").getFullyQualifiedColumnName();

        String sql = " SELECT * FROM " + TABLENAME +
                     " WHERE " + clientDetailFKCol + " = " + clientDetailFK;

        if (bankAcctInfoFK == 0)
        {
            sql += " AND " + bankAccountInformationFKCol + " IS NULL";
        }
        else
        {
            sql += " AND " + bankAccountInformationFKCol + " = " + bankAcctInfoFK;
        }

        if (preferenceFK == 0)
        {
            sql += " AND " + preferenceFKCol + " IS NULL";
        }
        else
        {
            sql += " AND " + preferenceFKCol + " = " + preferenceFK;
        }

        if (taxProfileFK == 0)
        {
            sql += " AND " + taxProfileFKCol + " IS NULL";
        }
        else
        {
            sql += " AND " + taxProfileFKCol + " = " + taxProfileFK;
        }

        sql += " AND " + roleTypeCTCol + " = '"+ roleTypeCT + "'";

        return (ClientRoleVO[]) executeQuery(ClientRoleVO.class,
                sql,
                POOLNAME,
                includeChildVOs,
                voExclusionList);
    }

    public ClientRoleVO[] getClientRoleByClientDetailAndRoleType(long clientDetailFK, String roleType,
                                                                 boolean includeChildVOs, List voExclusionList)
    {
        String clientDetailFKCol = DBTABLE.getDBColumn("ClientDetailFK").getFullyQualifiedColumnName();
        String roleTypeCTCol     = DBTABLE.getDBColumn("RoleTypeCT").getFullyQualifiedColumnName();

        String sql = " SELECT * FROM " + TABLENAME +
                     " WHERE " + clientDetailFKCol + " = " + clientDetailFK +
                     " AND " + roleTypeCTCol + " = '" + roleType + "'";

        return (ClientRoleVO[]) executeQuery(ClientRoleVO.class,
                sql,
                POOLNAME,
                includeChildVOs,
                voExclusionList);
    }
}

