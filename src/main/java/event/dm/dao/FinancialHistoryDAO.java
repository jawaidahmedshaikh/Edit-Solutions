/*
 * User: gfrosti
 * Date: Jun 25, 2003
 * Time: 10:41:47 AM
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package event.dm.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import edit.common.EDITDate;
import edit.common.vo.EDITTrxVO;
import edit.common.vo.FinancialHistoryVO;
import edit.services.db.ConnectionFactory;
import edit.services.db.DAO;
import edit.services.db.DBTable;
import edit.services.db.DBUtil;


public class FinancialHistoryDAO extends DAO
{
    private final String POOLNAME;
    private final DBTable DBTABLE;
    private final String TABLENAME;


    public FinancialHistoryDAO()
    {
        POOLNAME  = ConnectionFactory.EDITSOLUTIONS_POOL;
        DBTABLE   = DBTable.getDBTableForTable("FinancialHistory");
        TABLENAME = DBTABLE.getFullyQualifiedTableName();
    }

    public FinancialHistoryVO[] findByFinancialHistoryPK(long financialHistoryPK)
    {
        String financialHistoryPKCol = DBTABLE.getDBColumn("FinancialHistoryPK").getFullyQualifiedColumnName();

        String sql = "SELECT * FROM " + TABLENAME +
                     " WHERE " + financialHistoryPKCol + " = " + financialHistoryPK;

        return (FinancialHistoryVO[]) executeQuery(FinancialHistoryVO.class,
                                                    sql,
                                                     POOLNAME,
                                                      false,
                                                       null);
    }

    public FinancialHistoryVO[] findByEditTrxHistoryPK(long editTrxHistoryPK)
    {
        String editTrxHistoryFKCol = DBTABLE.getDBColumn("EDITTrxHistoryFK").getFullyQualifiedColumnName();

        String sql = "SELECT * FROM " + TABLENAME +
                     " WHERE " +  editTrxHistoryFKCol + " = " + editTrxHistoryPK;

        return (FinancialHistoryVO[]) executeQuery(FinancialHistoryVO.class,
                                                    sql,
                                                     POOLNAME,
                                                      false,
                                                       null);
    }

    /**
     * Finder.
     * @param financialHistoryPK
     * @return
     */
    public FinancialHistoryVO[] findByPK(long financialHistoryPK)
    {
        String financialHistoryPKCol = DBTABLE.getDBColumn("FincancialHistoryPK").getFullyQualifiedColumnName();

        String sql = "SELECT * FROM " + TABLENAME +
                     " WHERE " +  financialHistoryPKCol + " = " + financialHistoryPK;

        return (FinancialHistoryVO[]) executeQuery(FinancialHistoryVO.class,
                                                    sql,
                                                     POOLNAME,
                                                      false,
                                                       null);
    }

    /**
     * Finder.
     * @param editTrxHistoryPK
     * @return
     */
    public FinancialHistoryVO[] findByEDITTrxHistoryPK(long editTrxHistoryPK)
    {
        String editTrxHistoryFKCol = DBTABLE.getDBColumn("EDITTrxHistoryFK").getFullyQualifiedColumnName();

        String sql;
        sql =   " SELECT * " +
                " FROM " + TABLENAME +
                " WHERE " + editTrxHistoryFKCol + " = " + editTrxHistoryPK;

        return (FinancialHistoryVO[]) executeQuery(FinancialHistoryVO.class,
                                                     sql,
                                                      POOLNAME,
                                                       false,
                                                         null);
    }


    /**
     * Find all FinancialHistorys for a given editTrxPK
     * @param editTrxPK
     * @return
     */
    public FinancialHistoryVO[] findBy_EDITTrxPK(long editTrxPK)
    {
        DBTable editTrxHistoryDBTable = DBTable.getDBTableForTable("EDITTrxHistory");
        String editTrxHistoryTableName = editTrxHistoryDBTable.getFullyQualifiedTableName();

        String editTrxHistoryFKCol = DBTABLE.getDBColumn("EDITTrxHistoryFK").getFullyQualifiedColumnName();
        String editTrxHistoryPKCol = editTrxHistoryDBTable.getDBColumn("EDITTrxHistoryPK").getFullyQualifiedColumnName();
        String editTrxFKCol = editTrxHistoryDBTable.getDBColumn("EDITTrxFK").getFullyQualifiedColumnName();

        String sql;
        sql =   " SELECT  " + TABLENAME + ".* "  +
                " FROM " + TABLENAME + ", " + editTrxHistoryTableName +
                " WHERE " + editTrxHistoryFKCol + " = " + editTrxHistoryPKCol +
                " AND " + editTrxFKCol + " = " + editTrxPK;
        
        return (FinancialHistoryVO[]) executeQuery(FinancialHistoryVO.class,
                                                     sql,
                                                      POOLNAME,
                                                       false,
                                                         null);
    }
    
    public FinancialHistoryVO[] findPreviousActiveTrxFinancialHistory(long segmentPK, EDITDate date)
    {
    	FinancialHistoryVO[] financialHistoryVOs = null;
        
        DBTable editTrx_dbTable = DBTable.getDBTableForTable("EDITTrx");
        DBTable editTrxHistory_dbTable  = DBTable.getDBTableForTable("EDITTrxHistory");
        DBTable clientSetup_dbTable = DBTable.getDBTableForTable("ClientSetup");
        DBTable contractSetup_dbTable = DBTable.getDBTableForTable("ContractSetup");
        DBTable transactionPriority_dbTable = DBTable.getDBTableForTable("TransactionPriority");
        DBTable segment_dbTable = DBTable.getDBTableForTable("Segment");

        String editTrx_tableName = editTrx_dbTable.getFullyQualifiedTableName();
        String editTrxHistory_tableName = editTrxHistory_dbTable.getFullyQualifiedTableName();
        String clientSetup_tableName = clientSetup_dbTable.getFullyQualifiedTableName();
        String contractSetup_tableName = contractSetup_dbTable.getFullyQualifiedTableName();
        String transactionPriority_tableName = transactionPriority_dbTable.getFullyQualifiedTableName();
        String segment_tableName = segment_dbTable.getFullyQualifiedTableName();

        String editTrxHistoryFKCol = DBTABLE.getDBColumn("EDITTrxHistoryFK").getFullyQualifiedColumnName();

        String editTrxHistoryPKCol = editTrxHistory_dbTable.getDBColumn("EDITTrxHistoryPK").getFullyQualifiedColumnName();
        String editTrxFKCol = editTrxHistory_dbTable.getDBColumn("EDITTrxFK").getFullyQualifiedColumnName();

        String editTrxPKCol = editTrx_dbTable.getDBColumn("EDITTrxPK").getFullyQualifiedColumnName();
        String pendingStatusCol = editTrx_dbTable.getDBColumn("PendingStatus").getFullyQualifiedColumnName();
        String statusCol = editTrx_dbTable.getDBColumn("Status").getFullyQualifiedColumnName();
        String effectiveDateCol = editTrx_dbTable.getDBColumn("EffectiveDate").getFullyQualifiedColumnName();
        String editTrxTransactionTypeCol = editTrx_dbTable.getDBColumn("TransactionTypeCT").getFullyQualifiedColumnName();
        String clientSetupFKCol   = editTrx_dbTable.getDBColumn("ClientSetupFK").getFullyQualifiedColumnName();
        String trxSequenceCol   = editTrx_dbTable.getDBColumn("SequenceNumber").getFullyQualifiedColumnName();

        String clientSetupPKCol = clientSetup_dbTable.getDBColumn("ClientSetupPK").getFullyQualifiedColumnName();
        String contractSetupFKCol = clientSetup_dbTable.getDBColumn("ContractSetupFK").getFullyQualifiedColumnName();
        String contractSetupPKCol = contractSetup_dbTable.getDBColumn("ContractSetupPK").getFullyQualifiedColumnName();
        String segmentFKCol  = contractSetup_dbTable.getDBColumn("SegmentFK").getFullyQualifiedColumnName();

        String transactionPriorityTransactionTypeCol = transactionPriority_dbTable.getDBColumn("TransactionTypeCT").getFullyQualifiedColumnName();
        String priorityCol = transactionPriority_dbTable.getDBColumn("Priority").getFullyQualifiedColumnName();

        String segmentPKCol = segment_dbTable.getDBColumn("SegmentPK").getFullyQualifiedColumnName();

        String sql = "SELECT " + TABLENAME + ".* FROM " + editTrx_tableName +
                     " INNER JOIN " + clientSetup_tableName + " ON " + clientSetupFKCol + " = " + clientSetupPKCol +
                     " INNER JOIN " + contractSetup_tableName + " ON " + contractSetupFKCol + " = " + contractSetupPKCol +
                     " INNER JOIN " + segment_tableName + " ON " + segmentFKCol + " = " + segmentPKCol +
                     " INNER JOIN " + transactionPriority_tableName + " ON " + editTrxTransactionTypeCol + " = " + transactionPriorityTransactionTypeCol +
                     " INNER JOIN " + editTrxHistory_tableName + " ON " + editTrxPKCol + " = " + editTrxFKCol +
                     " INNER JOIN " + TABLENAME + " ON " + editTrxHistoryPKCol + " = " + editTrxHistoryFKCol +
                     " WHERE " + segmentPKCol + " = ? " +
                     " AND " + statusCol + " in ('N', 'A')" +
                     " AND " + pendingStatusCol + " = 'H'" + 
                     " AND " + effectiveDateCol + " <= ? "  +
                    
                     " ORDER BY " + effectiveDateCol + " DESC, " + priorityCol + " DESC, " + trxSequenceCol + " DESC ";
                
        Connection conn = null;

        PreparedStatement ps = null;

        try
        {
            conn = ConnectionFactory.getSingleton().getConnection(POOLNAME);

            ps = conn.prepareStatement(sql);
            
            ps.setLong(1, segmentPK);
            ps.setDate(2, DBUtil.convertStringToDate(date.getFormattedDate()));

            financialHistoryVOs = (FinancialHistoryVO[]) executeQuery(FinancialHistoryVO.class, ps, POOLNAME, false, null);
        }
        catch (Exception e)
        {
            System.out.println(e);

            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.

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

        return financialHistoryVOs;
    }
}