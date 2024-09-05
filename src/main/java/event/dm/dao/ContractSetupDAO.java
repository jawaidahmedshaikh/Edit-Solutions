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

import edit.common.vo.ContractSetupVO;
import edit.services.db.*;

import java.util.*;
import java.sql.*;

public class ContractSetupDAO extends DAO
{
    private final String POOLNAME;
    private final DBTable DBTABLE;
    private final String TABLENAME;


    public ContractSetupDAO()
    {
        POOLNAME  = ConnectionFactory.EDITSOLUTIONS_POOL;
        DBTABLE   = DBTable.getDBTableForTable("ContractSetup");
        TABLENAME = DBTABLE.getFullyQualifiedTableName();
    }

    public ContractSetupVO[] findByContractSetupPK(long contractSetupPK)
    {
        String contractSetupPKCol = DBTABLE.getDBColumn("ContractSetupPK").getFullyQualifiedColumnName();

        String sql = "SELECT * FROM " + TABLENAME +
                     " WHERE " + contractSetupPKCol + " = " + contractSetupPK;

        return (ContractSetupVO[]) executeQuery(ContractSetupVO.class,
                                                 sql,
                                                  POOLNAME,
                                                   false,
                                                    null);
    }

    public ContractSetupVO[] findBySegmentPK(long segmentPK) throws Exception
    {
        String segmentFKCol = DBTABLE.getDBColumn( "SegmentFK").getFullyQualifiedColumnName();

        String sql = "SELECT * FROM " + TABLENAME +
                     " WHERE " + segmentFKCol + " = " + segmentPK;

        return (ContractSetupVO[]) executeQuery(ContractSetupVO.class,
                                                 sql,
                                                  POOLNAME,
                                                   false,
                                                    null);
    }


    public ContractSetupVO[] findByEDITTrxPK(long editTrxPK)
    {
        DBTable clientSetupDBTable = DBTable.getDBTableForTable("ClientSetup");
        DBTable editTrxDBTable     = DBTable.getDBTableForTable("EDITTrx");

        String clientSetupTable   = clientSetupDBTable.getFullyQualifiedTableName();
        String editTrxTable       = editTrxDBTable.getFullyQualifiedTableName();

        String editTrxPKCol       = editTrxDBTable.getDBColumn("EDITTrxPK").getFullyQualifiedColumnName();
        String clientSetupFKCol   = editTrxDBTable.getDBColumn("ClientSetupFK").getFullyQualifiedColumnName();
        String clientSetupPKCol   = clientSetupDBTable.getDBColumn("ClientSetupPK").getFullyQualifiedColumnName();
        String contractSetupFKCol = clientSetupDBTable.getDBColumn("ContractSetupFK").getFullyQualifiedColumnName();

        String contractSetupPKCol = DBTABLE.getDBColumn("ContractSetupPK").getFullyQualifiedColumnName();

        String sql = "SELECT " + TABLENAME + ".* FROM " + editTrxTable + ", " + clientSetupTable + ", " + TABLENAME +
                     " WHERE " + editTrxPKCol + " = " + editTrxPK +
                     " AND " + clientSetupFKCol + " = " + clientSetupPKCol +
                     " AND " + contractSetupFKCol + " = " + contractSetupPKCol;

        return (ContractSetupVO[]) executeQuery(ContractSetupVO.class,
                                                 sql,
                                                  POOLNAME,
                                                   false,
                                                    null);
    }

    public ContractSetupVO[] findByGroupSetupPK(long groupSetupPK)
    {
        String groupSetupFKCol = DBTABLE.getDBColumn("GroupSetupFK").getFullyQualifiedColumnName();

        String sql = "SELECT * FROM " + TABLENAME +
                     " WHERE " + groupSetupFKCol + " = " + groupSetupPK;

        return (ContractSetupVO[]) executeQuery(ContractSetupVO.class,
                                                 sql,
                                                  POOLNAME,
                                                   false,
                                                    null);
    }

    public ContractSetupVO[] findThruHistoryBySegmentPK(long segmentPK, String effectiveDate, boolean includeChildren, List voExclusionList) throws Exception
    {
        ContractSetupVO[] contractSetupVOs = null;

        DBTable clientSetupDBTable = DBTable.getDBTableForTable("ClientSetup");
        DBTable editTrxDBTable     = DBTable.getDBTableForTable("EDITTrx");
        DBTable editTrxHistoryDBTable     = DBTable.getDBTableForTable("EDITTrxHistory");
        DBTable financialHistoryDBTable   = DBTable.getDBTableForTable("FinancialHistory");
        DBTable commissionHistoryDBTable  = DBTable.getDBTableForTable("CommissionHistory");
        DBTable segmentHistoryDBTable     = DBTable.getDBTableForTable("SegmentHistory");

        String clientSetupTable   = clientSetupDBTable.getFullyQualifiedTableName();
        String editTrxTable       = editTrxDBTable.getFullyQualifiedTableName();
        String editTrxHistoryTable       = editTrxHistoryDBTable.getFullyQualifiedTableName();
        String financialHistoryTable     = financialHistoryDBTable.getFullyQualifiedTableName();
        String commissionHistoryTable    = commissionHistoryDBTable.getFullyQualifiedTableName();
        String segmentHistoryTable       = segmentHistoryDBTable.getFullyQualifiedTableName();

        String editTrxPKCol       = editTrxDBTable.getDBColumn("EDITTrxPK").getFullyQualifiedColumnName();
        String clientSetupFKCol   = editTrxDBTable.getDBColumn("ClientSetupFK").getFullyQualifiedColumnName();
        String effectiveDateCol   = editTrxDBTable.getDBColumn("EffectiveDate").getFullyQualifiedColumnName();
        String pendingStatusCol   = editTrxDBTable.getDBColumn("PendingStatus").getFullyQualifiedColumnName();
        String statusCol          = editTrxDBTable.getDBColumn("Status").getFullyQualifiedColumnName();

        String clientSetupPKCol   = clientSetupDBTable.getDBColumn("ClientSetupPK").getFullyQualifiedColumnName();
        String contractSetupFKCol = clientSetupDBTable.getDBColumn("ContractSetupFK").getFullyQualifiedColumnName();
        String editTrxFKCol         = editTrxHistoryDBTable.getDBColumn("EDITTrxFK").getFullyQualifiedColumnName();
        String editTrxHistoryPKCol     = editTrxHistoryDBTable.getDBColumn("EDITTrxHistoryPK").getFullyQualifiedColumnName();
        String finEditTrxHistoryFKCol  = financialHistoryDBTable.getDBColumn("EDITTrxHistoryFK").getFullyQualifiedColumnName();
        String segEditTrxHistoryFKCol  = segmentHistoryDBTable.getDBColumn("EDITTrxHistoryFK").getFullyQualifiedColumnName();

        String contractSetupPKCol = DBTABLE.getDBColumn("ContractSetupPK").getFullyQualifiedColumnName();
        String segmentFKCol = DBTABLE.getDBColumn( "SegmentFK").getFullyQualifiedColumnName();

        String sql = "SELECT * FROM " + TABLENAME +
                     " INNER JOIN " + clientSetupTable +
                     " ON " + contractSetupFKCol + " = " + contractSetupPKCol +
                     " INNER JOIN " + editTrxTable +
                     " ON " + clientSetupFKCol + " = " + clientSetupPKCol +
                     " INNER JOIN " + editTrxHistoryTable +
                     " ON " + editTrxFKCol + " = " + editTrxPKCol +
                     " INNER JOIN " + financialHistoryTable +
                     " ON " + finEditTrxHistoryFKCol + " = " + editTrxHistoryPKCol +
                     " INNER JOIN " + segmentHistoryTable +
                     " ON " + segEditTrxHistoryFKCol + " = " + editTrxHistoryPKCol +
                     " WHERE " + segmentFKCol + " = " + segmentPK +
                     " AND " + effectiveDateCol + " >= ?" +
                     " AND " + pendingStatusCol + " IN ('H', 'L')" +
                     " AND " + statusCol + " IN ('N', 'A')";

        Connection conn = null;

        PreparedStatement ps = null;

        try
        {
            conn = ConnectionFactory.getSingleton().getConnection(POOLNAME);

            ps = conn.prepareStatement(sql);

            ps.setDate(1, DBUtil.convertStringToDate(effectiveDate));

            contractSetupVOs = (ContractSetupVO[]) executeQuery(ContractSetupVO.class,
                                                     ps,
                                                      POOLNAME,
                                                       includeChildren,
                                                        voExclusionList);
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

        return contractSetupVOs;
    }
}
