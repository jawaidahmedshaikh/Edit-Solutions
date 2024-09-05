/*
 * User: gfrosti
 * Date: Jul 17, 2003
 * Time: 10:37:55 AM
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package contract.dm.dao;

import edit.common.vo.*;
import edit.common.EDITDate;
import edit.services.db.*;

import java.util.List;
import java.sql.*;



public class ContractClientDAO extends DAO
{
    private final String POOLNAME;
    private final DBTable DBTABLE;
    private final String TABLENAME;


    public ContractClientDAO()
    {
        POOLNAME  = ConnectionFactory.EDITSOLUTIONS_POOL;
        DBTABLE   = DBTable.getDBTableForTable("ContractClient");
        TABLENAME = DBTABLE.getFullyQualifiedTableName();
    }

    public ContractClientVO[] findBySegmentFK(long segmentFK, boolean includeChildVOs, List voExclusionList)
    {
        ContractClientVO[] contractClientVOs = null;

        String segmentFKCol = DBTABLE.getDBColumn("SegmentFK").getFullyQualifiedColumnName();
        String terminationDateCol = DBTABLE.getDBColumn("TerminationDate").getFullyQualifiedColumnName();
        EDITDate currentDate        = new EDITDate();

         String sql = "SELECT * FROM " + TABLENAME +
                     " WHERE " + segmentFKCol + " = " + segmentFK +
                     " AND " + terminationDateCol  + " > ?";

        Connection conn = null;

        PreparedStatement ps = null;

        try
        {
            conn = ConnectionFactory.getSingleton().getConnection(POOLNAME);

            ps = conn.prepareStatement(sql);

            ps.setDate(1, DBUtil.convertStringToDate(currentDate.getFormattedDate()));

            contractClientVOs = (ContractClientVO[]) executeQuery(ContractClientVO.class,
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

                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.

                throw new RuntimeException(e);
            }
        }

        return contractClientVOs;
    }

    public ContractClientVO[] findByContractClientPK(long contractClientPK,
                                                      boolean includeChildVOs,
                                                       List voExclusionList)
    {
        String contractClientPKCol = DBTABLE.getDBColumn("ContractClientPK").getFullyQualifiedColumnName();

        String sql = "SELECT * FROM " + TABLENAME +
                     " WHERE " + contractClientPKCol + " = " + contractClientPK;

        return (ContractClientVO[]) executeQuery(ContractClientVO.class,
                                                  sql,
                                                   POOLNAME,
                                                    includeChildVOs,
                                                     voExclusionList);
    }

    public ContractClientVO[] findBySegmentPKAndContractClientAllocationOverrideStatus(long segmentPK,
                                                                                        String contractClientAllocationOverrideStatus,
                                                                                         boolean includeChildVOs,
                                                                                          List voExclusionList)
    {
        String segmentFKCol       = DBTABLE.getDBColumn("SegmentFK").getFullyQualifiedColumnName();
        String overrideStatusCol  = DBTABLE.getDBColumn("OverrideStatus").getFullyQualifiedColumnName();
        String terminationDateCol = DBTABLE.getDBColumn("TerminationDate").getFullyQualifiedColumnName();
        EDITDate currentDate      = new EDITDate();

        String sql = "SELECT * FROM " + TABLENAME +
                     " WHERE " + segmentFKCol + " = " + segmentPK +
                     " AND " + terminationDateCol  + " > ?" +
                     " AND " + overrideStatusCol + " = ?";

        ContractClientVO[] contractClientVOs = null;

        Connection conn = null;

        PreparedStatement ps = null;

        try
        {
            conn = ConnectionFactory.getSingleton().getConnection(POOLNAME);

            ps = conn.prepareStatement(sql);

            ps.setDate(1, DBUtil.convertStringToDate(currentDate.getFormattedDate()));
            ps.setString(2, contractClientAllocationOverrideStatus);

            contractClientVOs = (ContractClientVO[]) executeQuery(ContractClientVO.class,
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

                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.

                throw new RuntimeException(e);
            }
        }

        return contractClientVOs;
    }

    /**
     * Finds the specified ContactClientVO with the largest EffectiveDate
     * @param segmentPK
     * @param roleTypeCT
     * @return
     */
    public ContractClientVO[] findBy_SegmentPK_RoleTypeCT_MaxEffectiveDate(long segmentPK, String roleTypeCT)
    {
        // ContractClient
        String segmentFKCol = DBTABLE.getDBColumn("SegmentFK").getFullyQualifiedColumnName();
        String clientRoleFKCol = DBTABLE.getDBColumn("ClientRoleFK").getFullyQualifiedColumnName();
        String effectiveDateCol = DBTABLE.getDBColumn("EffectiveDate").getFullyQualifiedColumnName();

        // ClientRole
        DBTable clientRoleDBTable = DBTable.getDBTableForTable("ClientRole");
        String clientRoleTable = clientRoleDBTable.getFullyQualifiedTableName();
        String clientRolePKCol = clientRoleDBTable.getDBColumn("ClientRolePK").getFullyQualifiedColumnName();
        String roleTypeCTCol = clientRoleDBTable.getDBColumn("RoleTypeCT").getFullyQualifiedColumnName();

        String sql;
        sql =   " SELECT " + TABLENAME + ".*" +
                " FROM " + TABLENAME +
                " INNER JOIN " + clientRoleTable +
                " ON " + clientRoleFKCol + " = " + clientRolePKCol +
                " WHERE " + segmentFKCol + " = " + segmentPK +
                " AND " + roleTypeCTCol + " = '" + roleTypeCT + "'" +
                " AND " + effectiveDateCol + " = " +

                " ( SELECT MAX(" + effectiveDateCol + ") " +
                " FROM " + TABLENAME +
                " INNER JOIN " + clientRoleTable +
                " ON " + clientRoleFKCol + " = " + clientRolePKCol +
                " WHERE " + segmentFKCol + " = " + segmentPK +
                " AND " + roleTypeCTCol + " = '" + roleTypeCT + "')";

        return (ContractClientVO[]) executeQuery(ContractClientVO.class,
                                                  sql,
                                                   POOLNAME,
                                                    false,
                                                     null);
    }

    public ContractClientVO[] findBy_SegmentPK_RoleTypeCT_TerminationDateGTE(long segmentPK, String[] roleTypeCTs, String terminationDate)
    {
        // ContractClient
        String segmentFKCol = DBTABLE.getDBColumn("SegmentFK").getFullyQualifiedColumnName();
        String clientRoleFKCol = DBTABLE.getDBColumn("ClientRoleFK").getFullyQualifiedColumnName();
        String terminationDateCol = DBTABLE.getDBColumn("TerminationDate").getFullyQualifiedColumnName();

        // ClientRole
        DBTable clientRoleDBTable = DBTable.getDBTableForTable("ClientRole");
        String clientRoleTable = clientRoleDBTable.getFullyQualifiedTableName();
        String clientRolePKCol = clientRoleDBTable.getDBColumn("ClientRolePK").getFullyQualifiedColumnName();
        String roleTypeCTCol = clientRoleDBTable.getDBColumn("RoleTypeCT").getFullyQualifiedColumnName();

        String roleTypeSQL = " in(";
        for (int i = 0; i < roleTypeCTs.length; i++)
        {
            roleTypeSQL = roleTypeSQL + "'" + roleTypeCTs[i] + "'";

            if (i < roleTypeCTs.length - 1)
            {
                roleTypeSQL = roleTypeSQL + ",";
            }
            else
            {
                roleTypeSQL = roleTypeSQL + ")";
            }
        }

        String sql =   " SELECT " + TABLENAME + ".*" +
                " FROM " + TABLENAME +
                " INNER JOIN " + clientRoleTable +
                " ON " + clientRoleFKCol + " = " + clientRolePKCol +
                " WHERE " + segmentFKCol + " = ?" +
                " AND " + roleTypeCTCol + roleTypeSQL +
                " AND " + terminationDateCol + " >= ?";

        ContractClientVO[] contractClientVOs = null;

        Connection conn = null;

        PreparedStatement ps = null;

        try
        {
            conn = ConnectionFactory.getSingleton().getConnection(POOLNAME);

            ps = conn.prepareStatement(sql);

            ps.setLong(1, segmentPK);
            ps.setDate(2, DBUtil.convertStringToDate(terminationDate));

            contractClientVOs = (ContractClientVO[]) executeQuery(ContractClientVO.class,
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

                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.

                throw new RuntimeException(e);
            }
        }

        return contractClientVOs;
    }

    public ContractClientVO[] findByClientDetailPK_SegmentPK_RoleType(long clientDetailPK, long segmentPK, String roleTypeCT)
    {
        // ContractClient
        String segmentFKCol = DBTABLE.getDBColumn("SegmentFK").getFullyQualifiedColumnName();
        String clientRoleFKCol = DBTABLE.getDBColumn("ClientRoleFK").getFullyQualifiedColumnName();

        // ClientRole
        DBTable clientRoleDBTable = DBTable.getDBTableForTable("ClientRole");
        String clientRoleTable = clientRoleDBTable.getFullyQualifiedTableName();
        String clientRolePKCol = clientRoleDBTable.getDBColumn("ClientRolePK").getFullyQualifiedColumnName();
        String clientDetailFKCol = clientRoleDBTable.getDBColumn("ClientDetailFK").getFullyQualifiedColumnName();
        String roleTypeCTCol = clientRoleDBTable.getDBColumn("RoleTypeCT").getFullyQualifiedColumnName();

        //ClientDetail
        DBTable clientDetailDBTable = DBTable.getDBTableForTable("ClientDetail");
        String clientDetailTable = clientDetailDBTable.getFullyQualifiedTableName();
        String clientDetailPKCol = clientDetailDBTable.getDBColumn("ClientDetailPK").getFullyQualifiedColumnName();

        String sql;
        sql =   " SELECT " + TABLENAME + ".*" +
                " FROM " + TABLENAME +
                " INNER JOIN " + clientRoleTable +
                " ON " + clientRoleFKCol + " = " + clientRolePKCol +
                " INNER JOIN " + clientDetailTable +
                " ON " + clientDetailFKCol + " = " + clientDetailPKCol +
                " WHERE " + clientDetailPKCol + " = " + clientDetailPK +
                " AND " +  segmentFKCol + " = " + segmentPK +
                " AND " + roleTypeCTCol + " = '" + roleTypeCT + "'";


        return (ContractClientVO[]) executeQuery(ContractClientVO.class,
                                                  sql,
                                                   POOLNAME,
                                                    false,
                                                     null);
    }
}