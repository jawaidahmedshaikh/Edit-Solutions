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

import edit.common.vo.PlacedAgentVO;
import edit.services.db.ConnectionFactory;
import edit.services.db.DAO;
import edit.services.db.DBTable;
import edit.services.db.DBUtil;

import java.sql.*;


public class PlacedAgentDAO extends DAO
{
    private final String POOLNAME;
    private final DBTable DBTABLE;
    private final String TABLENAME;


    public PlacedAgentDAO()
    {
        POOLNAME  = ConnectionFactory.EDITSOLUTIONS_POOL;
        DBTABLE   = DBTable.getDBTableForTable("PlacedAgent");
        TABLENAME = DBTABLE.getFullyQualifiedTableName();
    }

    /**
     * Useful to find the Parent PlacedAgent of a Child PlacedAgent
     * @param hierarchyLevel
     * @param leftBoundary
     * @param rightBoundary
     * @return
     */
    public PlacedAgentVO[] findByHiearchyLevel_AND_LeftBoundaryLTE_AND_RightBoundaryGTE(int hierarchyLevel,
                                                                                         long leftBoundary,
                                                                                          long rightBoundary)
    {
        String hierarchyLevelCol = DBTABLE.getDBColumn("HierarchyLevel").getFullyQualifiedColumnName();
        String leftBoundaryCol   = DBTABLE.getDBColumn("LeftBoundary").getFullyQualifiedColumnName();
        String rightBoundaryCol  = DBTABLE.getDBColumn("RightBoundary").getFullyQualifiedColumnName();

        String sql = "SELECT * FROM " + TABLENAME +
                     " WHERE " + hierarchyLevelCol + " = " + hierarchyLevel +
                     " AND " + leftBoundaryCol + " <= " + leftBoundary +
                     " AND " + rightBoundaryCol + " >= "  + rightBoundary;

        return (PlacedAgentVO[]) executeQuery(PlacedAgentVO.class,
                                               sql,
                                                POOLNAME,
                                                 false,
                                                  null);
    }

    public PlacedAgentVO[] findByAgentContractPK(long agentContractPK)
    {
        String agentContractFKCol = DBTABLE.getDBColumn("AgentContractFK").getFullyQualifiedColumnName();

        String sql = "SELECT * FROM " + TABLENAME +
                     " WHERE " + agentContractFKCol + " = " + agentContractPK;

        return (PlacedAgentVO[]) executeQuery(PlacedAgentVO.class,
                                               sql,
                                                POOLNAME,
                                                 false,
                                                  null);
    }

    /**
     * Finder.
     * @param contractCodeCT
     * @param commissionLevelCT
     * @param startDate
     * @param stopDate
     * @return
     */
    public PlacedAgentVO[] findBy_ContractCodeCT_CommissionLevelCT_StartDate_StopDate(String contractCodeCT, String commissionLevelCT, String startDate, String stopDate)
    {
        // CommissionProfile
        DBTable commissionProfileDBTable = DBTable.getDBTableForTable("CommissionProfile");
        String commissionProfileTable = commissionProfileDBTable.getFullyQualifiedTableName();
        String contractCodeCTCol = commissionProfileDBTable.getDBColumn("ContractCodeCT").getFullyQualifiedColumnName();
        String commissionLevelCTCol = commissionProfileDBTable.getDBColumn("CommissionLevelCT").getFullyQualifiedColumnName();
        String commissionProfilePKCol = commissionProfileDBTable.getDBColumn("CommissionProfilePK").getFullyQualifiedColumnName();

        //PlacedAgentCommissionProfile
        DBTable placedAgentCommProfileDBTable = DBTable.getDBTableForTable("PlacedAgentCommissionProfile");
        String placedAgentCommissionProfileTable = placedAgentCommProfileDBTable.getFullyQualifiedTableName();
        String commissionProfileFKCol = placedAgentCommProfileDBTable.getDBColumn("CommissionProfileFK").getFullyQualifiedColumnName();
        String placedAgentFKCol = placedAgentCommProfileDBTable.getDBColumn("PlacedAgentFK").getFullyQualifiedColumnName();


        // PlacedAgent
        String placedAgentPKCol = DBTABLE.getDBColumn("PlacedAgentPK").getFullyQualifiedColumnName();
        String startDateCol = DBTABLE.getDBColumn("StartDate").getFullyQualifiedColumnName();
        String stopDateCol = DBTABLE.getDBColumn("StopDate").getFullyQualifiedColumnName();

        String sql;
        sql =   " SELECT " + TABLENAME + ".*" +
                " FROM " + TABLENAME +
                " INNER JOIN " + placedAgentCommissionProfileTable +
                " ON " + placedAgentPKCol + " = " + placedAgentFKCol +
                " INNER JOIN " + commissionProfileTable +
                " ON " + commissionProfileFKCol + " = " + commissionProfilePKCol +
                " WHERE " + contractCodeCTCol + " = ?" +
                " AND " + commissionLevelCTCol + " = ?" +
                " AND " + startDateCol + " <= ?" +
                " AND " + stopDateCol + " >= ?";

        PlacedAgentVO[] placedAgentVOs = null;
        Connection conn = null;

        PreparedStatement ps = null;

        try
        {
            conn = ConnectionFactory.getSingleton().getConnection(POOLNAME);

            ps = conn.prepareStatement(sql);

            ps.setString(1, contractCodeCT);
            ps.setString(2, commissionLevelCT);
            ps.setDate(3, DBUtil.convertStringToDate(stopDate));
            ps.setDate(4, DBUtil.convertStringToDate(startDate));

            placedAgentVOs = (PlacedAgentVO[]) executeQuery(PlacedAgentVO.class,
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
        return placedAgentVOs;
    }

    /**
     * Finder.
     * @param contractCodeCT
     * @param agentNumber
     * @param startDate
     * @param stopDate
     * @return
     */
    public PlacedAgentVO[] findBy_CommissionContractCT_AgentNumber_StartDate_StopDate(String contractCodeCT, String agentNumber, String startDate, String stopDate)
    {
        // CommissionProfile
        DBTable commissionProfileDBTable = DBTable.getDBTableForTable("CommissionProfile");
        String commissionProfileTable = commissionProfileDBTable.getFullyQualifiedTableName();
        String contractCodeCTCol = commissionProfileDBTable.getDBColumn("ContractCodeCT").getFullyQualifiedColumnName();
        String commissionProfilePKCol = commissionProfileDBTable.getDBColumn("CommissionProfilePK").getFullyQualifiedColumnName();

        //PlacedAgentCommissionProfile
        DBTable placedAgentCommProfileDBTable = DBTable.getDBTableForTable("PlacedAgentCommissionProfile");
        String placedAgentCommissionProfileTable = placedAgentCommProfileDBTable.getFullyQualifiedTableName();
        String commissionProfileFKCol = placedAgentCommProfileDBTable.getDBColumn("CommissionProfileFK").getFullyQualifiedColumnName();
        String placedAgentFKCol = placedAgentCommProfileDBTable.getDBColumn("PlacedAgentFK").getFullyQualifiedColumnName();

        // PlacedAgent
        String placedAgentPKCol = DBTABLE.getDBColumn("PlacedAgentPK").getFullyQualifiedColumnName();
        String agentContractFKCol = DBTABLE.getDBColumn("AgentContractFK").getFullyQualifiedColumnName();
        String startDateCol = DBTABLE.getDBColumn("StartDate").getFullyQualifiedColumnName();
        String stopDateCol = DBTABLE.getDBColumn("StopDate").getFullyQualifiedColumnName();

        // AgentContract
        DBTable agentContractDBTable = DBTable.getDBTableForTable("AgentContract");
        String agentContractTable = agentContractDBTable.getFullyQualifiedTableName();
        String agentContractPKCol = agentContractDBTable.getDBColumn("AgentContractPK").getFullyQualifiedColumnName();
        String agentFKCol = agentContractDBTable.getDBColumn("AgentFK").getFullyQualifiedColumnName();

        // Agent
        DBTable agentDBTable = DBTable.getDBTableForTable("Agent");
        String agentTable = agentDBTable.getFullyQualifiedTableName();
        String agentPKCol = agentDBTable.getDBColumn("AgentPK").getFullyQualifiedColumnName();
        String agentNumberCol = agentDBTable.getDBColumn("AgentNumber").getFullyQualifiedColumnName();

        String sql;
        sql =   " SELECT " + TABLENAME + ".*" +
                " FROM " + TABLENAME +
                " INNER JOIN " + placedAgentCommissionProfileTable +
                " ON " + placedAgentPKCol + " = " + placedAgentFKCol +
                " INNER JOIN " + commissionProfileTable +
                " ON " + commissionProfilePKCol + " = " + commissionProfileFKCol +
                " INNER JOIN " + agentContractTable +
                " ON " + agentContractFKCol + " = " + agentContractPKCol +
                " INNER JOIN " + agentTable +
                " ON " + agentFKCol + " = " + agentPKCol +
                " WHERE " + contractCodeCTCol + " = ?" +
                " AND " + agentNumberCol + " = ?" +
                " AND ( (" + startDateCol + " <= ?) AND (" + stopDateCol + " >= ?) )";

        PlacedAgentVO[] placedAgentVOs = null;
        Connection conn = null;

        PreparedStatement ps = null;

        try
        {
            conn = ConnectionFactory.getSingleton().getConnection(POOLNAME);

            ps = conn.prepareStatement(sql);

            ps.setString(1, contractCodeCT);
            ps.setString(2, agentNumber);
            ps.setDate(3, DBUtil.convertStringToDate(stopDate));
            ps.setDate(4, DBUtil.convertStringToDate(startDate));

            placedAgentVOs = (PlacedAgentVO[]) executeQuery(PlacedAgentVO.class,
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
        return placedAgentVOs;

    }

    /**
     * Finder. AgentName entails the query of both corporate and individual names off of ClientDetail.
     * The PlacedAgent's start/stop dates must satisfy startDate <= PA.startDate <= stopDate OR startDate <= PA.stopDate <= stopDate
     * @param contractCodeCT
     * @param agentName
     * @return
     */
    public PlacedAgentVO[] findBy_CommissionContractCT_AgentName_StartDate_StopDate(String contractCodeCT, String agentName, String startDate, String stopDate)
    {
        // CommissionProfile
        DBTable commissionProfileDBTable = DBTable.getDBTableForTable("CommissionProfile");
        String commissionProfileTable = commissionProfileDBTable.getFullyQualifiedTableName();
        String contractCodeCTCol = commissionProfileDBTable.getDBColumn("ContractCodeCT").getFullyQualifiedColumnName();
        String commissionProfilePKCol = commissionProfileDBTable.getDBColumn("CommissionProfilePK").getFullyQualifiedColumnName();

        //PlacedAgentCommissionProfile
        DBTable placedAgentCommProfileDBTable = DBTable.getDBTableForTable("PlacedAgentCommissionProfile");
        String placedAgentCommissionProfileTable = placedAgentCommProfileDBTable.getFullyQualifiedTableName();
        String commissionProfileFKCol = placedAgentCommProfileDBTable.getDBColumn("CommissionProfileFK").getFullyQualifiedColumnName();
        String placedAgentFKCol = placedAgentCommProfileDBTable.getDBColumn("PlacedAgentFK").getFullyQualifiedColumnName();

        // PlacedAgent
        String placedAgentPKCol = DBTABLE.getDBColumn("PlacedAgentPK").getFullyQualifiedColumnName();
        String agentContractFKCol = DBTABLE.getDBColumn("AgentContractFK").getFullyQualifiedColumnName();
        String startDateCol = DBTABLE.getDBColumn("StartDate").getFullyQualifiedColumnName();
        String stopDateCol = DBTABLE.getDBColumn("StopDate").getFullyQualifiedColumnName();

        // AgentContract
        DBTable agentContractDBTable = DBTable.getDBTableForTable("AgentContract");
        String agentContractTable = agentContractDBTable.getFullyQualifiedTableName();
        String agentContractPKCol = agentContractDBTable.getDBColumn("AgentContractPK").getFullyQualifiedColumnName();
        String agentFKCol = agentContractDBTable.getDBColumn("AgentFK").getFullyQualifiedColumnName();

        // Agent
        DBTable agentDBTable = DBTable.getDBTableForTable("Agent");
        String agentTable = agentDBTable.getFullyQualifiedTableName();
        String agentPKCol = agentDBTable.getDBColumn("AgentPK").getFullyQualifiedColumnName();
//        String clientRoleFKCol = agentDBTable.getDBColumn("ClientRoleFK").getFullyQualifiedColumnName();

        // ClientRole
        DBTable clientRoleDBTable = DBTable.getDBTableForTable("ClientRole");
        String clientRoleTable = clientRoleDBTable.getFullyQualifiedTableName();
        String clientRolePKCol = clientRoleDBTable.getDBColumn("ClientRolePK").getFullyQualifiedColumnName();
        String clientDetailFKCol = clientRoleDBTable.getDBColumn("ClientDetailFK").getFullyQualifiedColumnName();
        String crAgentFKCol = clientRoleDBTable.getDBColumn("AgentFK").getFullyQualifiedColumnName();

        // ClientDetail
        DBTable clientDetailDBTable = DBTable.getDBTableForTable("ClientDetail");
        String clientDetailTable = clientDetailDBTable.getFullyQualifiedTableName();
        String clientDetailPKCol = clientDetailDBTable.getDBColumn("ClientDetailPK").getFullyQualifiedColumnName();
        String lastNameCol = clientDetailDBTable.getDBColumn("LastName").getFullyQualifiedColumnName();
        String corporateNameCol = clientDetailDBTable.getDBColumn("CorporateName").getFullyQualifiedColumnName();

        String sql;
        sql =   " SELECT " + TABLENAME + ".*" +
                " FROM " + TABLENAME +
                " INNER JOIN " + placedAgentCommissionProfileTable +
                " ON " + placedAgentPKCol + " = " + placedAgentFKCol +
                " INNER JOIN " + commissionProfileTable +
                " ON " + commissionProfilePKCol + " = " + commissionProfileFKCol +
                " INNER JOIN " + agentContractTable +
                " ON " + agentContractFKCol + " = " + agentContractPKCol +
                " INNER JOIN " + clientRoleTable +
                " ON " + crAgentFKCol + " = " + agentFKCol +
                " INNER JOIN " + clientDetailTable +
                " ON " + clientDetailFKCol + " = " + clientDetailPKCol +
                " WHERE " + contractCodeCTCol + " = ?" +
                " AND (" + lastNameCol + " LIKE '" + agentName + "%' OR " + corporateNameCol + " LIKE '" + agentName + "%')" +
                " AND ( (" + startDateCol + " <= ?) AND (" + stopDateCol + " >= ?) )";


        PlacedAgentVO[] placedAgentVOs = null;
        Connection conn = null;

        PreparedStatement ps = null;

        try
        {
            conn = ConnectionFactory.getSingleton().getConnection(POOLNAME);

            ps = conn.prepareStatement(sql);

            ps.setString(1, contractCodeCT);
            ps.setDate(2, DBUtil.convertStringToDate(stopDate));
            ps.setDate(3, DBUtil.convertStringToDate(startDate));

            placedAgentVOs = (PlacedAgentVO[]) executeQuery(PlacedAgentVO.class,
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
        return placedAgentVOs;
    }
}