/*
 * Created by IntelliJ IDEA.
 * User: dlataill
 * Date: Oct 17, 2003
 * Time: 12:47:29 PM
 *
 * (c) 2000-2004 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */

package agent.dm.dao;

import edit.common.vo.CommissionProfileVO;
import edit.services.db.*;

import java.sql.*;


public class CommissionProfileDAO extends DAO
{
    private final String POOLNAME;
    private final DBTable DBTABLE;
    private final String TABLENAME;


    public CommissionProfileDAO()
    {
        POOLNAME  = ConnectionFactory.EDITSOLUTIONS_POOL;
        DBTABLE   = DBTable.getDBTableForTable("CommissionProfile");
        TABLENAME = DBTABLE.getFullyQualifiedTableName();
    }

    public CommissionProfileVO[] getAllCommissionProfiles()
    {
        String sql = "SELECT * FROM " + TABLENAME;

        return (CommissionProfileVO[]) executeQuery(CommissionProfileVO.class,
                                                     sql,
                                                      POOLNAME,
                                                       false,
                                                        null);
    }

    public CommissionProfileVO[] findBycontractCodeCT(String contractCodeCT)
    {
        String contractCodeCTCol = DBTABLE.getDBColumn("ContractCodeCT").getFullyQualifiedColumnName();

        String sql;
        sql =   " SELECT * " +
                " FROM " + TABLENAME +
                " WHERE " + contractCodeCTCol + " = '" + contractCodeCT + "'";

        return (CommissionProfileVO[]) executeQuery(CommissionProfileVO.class,
                                                     sql,
                                                      POOLNAME,
                                                       false,
                                                        null);
    }

    public CommissionProfileVO[] getCommissionProfileByPK(long commProfilePK)
    {
        String commissionProfilePKCol = DBTABLE.getDBColumn("CommissionProfilePK").getFullyQualifiedColumnName();

        String sql = "SELECT * FROM " + TABLENAME +
                     " WHERE " + commissionProfilePKCol + " = " + commProfilePK;

        return (CommissionProfileVO[]) executeQuery(CommissionProfileVO.class,
                                                     sql,
                                                      POOLNAME,
                                                       false,
                                                        null);
    }

    /**
     * Finder.
     * @param commissionProfilePK
     * @return
     */
    public CommissionProfileVO[] findByPK(long commissionProfilePK)
    {
        String commissionProfilePKCol = DBTABLE.getDBColumn("CommissionProfilePK").getFullyQualifiedColumnName();

        String sql = " SELECT * FROM " + TABLENAME +
                " WHERE " + commissionProfilePKCol + " = " + commissionProfilePK;

        return (CommissionProfileVO[]) executeQuery(CommissionProfileVO.class, sql, POOLNAME, false, null);
    }

    /**
     * Finds the CommissionProfile associated with the PlacedAgent via PlacedAgentCommissionProfile whose
     * PlacedAgentCommissionProfile.StartDate <= date <= PlacedAgentCommissionProfile.StopDate.
     */
    public CommissionProfileVO[] findBy_PlacedAgentPK_StopStartDate(long placedAgentPK, String date)
    {
        // PlacedAgentCommissionProfile
        DBTable placedAgentCommissionProfileDBTable = DBTable.getDBTableForTable("PlacedAgentCommissionProfile");
        String placedAgentCommissionProfileTable = placedAgentCommissionProfileDBTable.getFullyQualifiedTableName();
        String commissionProfileFKCol = placedAgentCommissionProfileDBTable.getDBColumn("CommissionProfileFK").getFullyQualifiedColumnName();
        String placedAgentFKCol = placedAgentCommissionProfileDBTable.getDBColumn("PlacedAgentFK").getFullyQualifiedColumnName();
        String startDateCol = placedAgentCommissionProfileDBTable.getDBColumn("StartDate").getFullyQualifiedColumnName();
        String stopDateCol = placedAgentCommissionProfileDBTable.getDBColumn("StopDate").getFullyQualifiedColumnName();
        
        // CommissionProfile
        String commissionProfilePKCol = DBTABLE.getDBColumn("CommissionProfilePK").getFullyQualifiedColumnName();
        
        String sql = " select " + TABLENAME + ".*" +
                    " from " + TABLENAME +
                    " left join " + placedAgentCommissionProfileTable +
                    " on " + commissionProfilePKCol + " = " + commissionProfileFKCol +
                    " where " + placedAgentFKCol + " = ?" +
                    " and " + startDateCol + " <= ?" +
                    " and " + stopDateCol + " >= ?";
               
        CommissionProfileVO[] commissionProfileVOs = null;
        Connection conn = null;

        PreparedStatement ps = null;

        try
        {
            conn = ConnectionFactory.getSingleton().getConnection(POOLNAME);

            ps = conn.prepareStatement(sql);

            ps.setLong(1, placedAgentPK);
            ps.setDate(2, DBUtil.convertStringToDate(date));
            ps.setDate(3, DBUtil.convertStringToDate(date));

            commissionProfileVOs = (CommissionProfileVO[]) executeQuery(CommissionProfileVO.class,
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
        return commissionProfileVOs;
    }
}
