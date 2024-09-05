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

import edit.common.vo.EDITTrxHistoryVO;
import edit.common.*;
import edit.services.db.ConnectionFactory;
import edit.services.db.DAO;
import edit.services.db.DBTable;
import edit.services.db.DBUtil;

import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.ArrayList;

public class EDITTrxHistoryDAO extends DAO
{
    private final String POOLNAME;

    private final DBTable EDITTRX_HISTORY_DBTABLE;
    private final DBTable EDITTRX_DBTABLE;
    private final DBTable CLIENT_SETUP_DBTABLE;
    private final DBTable CONTRACT_SETUP_DBTABLE;
    private final DBTable SEGMENT_DBTABLE;

    private final String EDITTRX_HISTORY_TABLENAME;
    private final String EDITTRX_TABLENAME;
    private final String CLIENT_SETUP_TABLENAME;
    private final String CONTRACT_SETUP_TABLENAME;
    private final String SEGMENT_TABLENAME;

    public EDITTrxHistoryDAO()
    {
        POOLNAME  = ConnectionFactory.EDITSOLUTIONS_POOL;

        EDITTRX_HISTORY_DBTABLE = DBTable.getDBTableForTable("EDITTrxHistory");
        EDITTRX_DBTABLE = DBTable.getDBTableForTable("EDITTrx");
        CLIENT_SETUP_DBTABLE = DBTable.getDBTableForTable("ClientSetup");
        CONTRACT_SETUP_DBTABLE = DBTable.getDBTableForTable("ContractSetup");
        SEGMENT_DBTABLE = DBTable.getDBTableForTable("Segment");

        EDITTRX_HISTORY_TABLENAME = EDITTRX_HISTORY_DBTABLE.getFullyQualifiedTableName();
        EDITTRX_TABLENAME = EDITTRX_DBTABLE.getFullyQualifiedTableName();
        CLIENT_SETUP_TABLENAME = CLIENT_SETUP_DBTABLE.getFullyQualifiedTableName();
        CONTRACT_SETUP_TABLENAME = CONTRACT_SETUP_DBTABLE.getFullyQualifiedTableName();
        SEGMENT_TABLENAME = SEGMENT_DBTABLE.getFullyQualifiedTableName();
    }

    public EDITTrxHistoryVO[] findByEditTrxPK(long editTrxPK)
    {
        String editTrxFKCol = EDITTRX_HISTORY_DBTABLE.getDBColumn("EDITTrxFK").getFullyQualifiedColumnName();

        String sql = "SELECT * FROM " + EDITTRX_HISTORY_TABLENAME +
                     " WHERE " + editTrxFKCol + " = " + editTrxPK;

        return (EDITTrxHistoryVO[]) executeQuery(EDITTrxHistoryVO.class,
                                                  sql,
                                                   POOLNAME,
                                                    false,
                                                     null);
    }

    public EDITTrxHistoryVO[] findByEditTrxHistoryPK(long editTrxHistoryPK) throws Exception
    {
        String editTrxHistoryPKCol = EDITTRX_HISTORY_DBTABLE.getDBColumn("EDITTrxHistoryPK").getFullyQualifiedColumnName();

       String sql = "SELECT * FROM " + EDITTRX_HISTORY_TABLENAME +
                    " WHERE " + editTrxHistoryPKCol + " = " + editTrxHistoryPK;

       return (EDITTrxHistoryVO[]) executeQuery(EDITTrxHistoryVO.class,
                                                 sql,
                                                  POOLNAME,
                                                   false,
                                                    null);


    }

    public EDITTrxHistoryVO[] findByEffectiveDateGTEOrdered(String effectiveDate) throws Exception
    {
        String editTrxFKCol = EDITTRX_HISTORY_DBTABLE.getDBColumn("EDITTrxFK").getFullyQualifiedColumnName();

        String effectiveDateCol = EDITTRX_DBTABLE.getDBColumn("EffectiveDate").getFullyQualifiedColumnName();
        String editTrxPKCol = EDITTRX_DBTABLE.getDBColumn("EDITTrxPK").getFullyQualifiedColumnName();

        String sql = "SELECT " + EDITTRX_HISTORY_TABLENAME + ".* FROM " + EDITTRX_HISTORY_TABLENAME + ", " + EDITTRX_TABLENAME +
                    " WHERE " + effectiveDateCol + " >= ?" +
                    " AND " + editTrxFKCol + " = " + editTrxPKCol +
                    " ORDER BY " + effectiveDateCol + " ASC";

        EDITTrxHistoryVO[] editTrxHistoryVOs = null;

        Connection conn = null;

        PreparedStatement ps = null;

        try
        {
            conn = ConnectionFactory.getSingleton().getConnection(POOLNAME);

            ps = conn.prepareStatement(sql);

            ps.setDate(1, DBUtil.convertStringToDate(effectiveDate));

            editTrxHistoryVOs = (EDITTrxHistoryVO[]) executeQuery(EDITTrxHistoryVO.class,
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

        return editTrxHistoryVOs;
    }

    public EDITTrxHistoryVO[] findEDITTrxHistoryBySegmentFKAndCycleDate(long[] segmentFKs, String fromDate, String toDate) throws Exception
    {
        String editTrxFKCol = EDITTRX_HISTORY_DBTABLE.getDBColumn("EDITTrxFK").getFullyQualifiedColumnName();
        String cycleDateCol = EDITTRX_HISTORY_DBTABLE.getDBColumn("CycleDate").getFullyQualifiedColumnName();

        String editTrxPKCol = EDITTRX_DBTABLE.getDBColumn("EDITTrxPK").getFullyQualifiedColumnName();
        String pendingStatusCol = EDITTRX_DBTABLE.getDBColumn("PendingStatus").getFullyQualifiedColumnName();
        String clientSetupFKCol = EDITTRX_DBTABLE.getDBColumn("ClientSetupFK").getFullyQualifiedColumnName();

        String clientSetupPKCol = CLIENT_SETUP_DBTABLE.getDBColumn("ClientSetupPK").getFullyQualifiedColumnName();
        String contractSetupFKCol = CLIENT_SETUP_DBTABLE.getDBColumn("ContractSetupFK").getFullyQualifiedColumnName();

        String contractSetupPKCol = CONTRACT_SETUP_DBTABLE.getDBColumn("ContractSetupPK").getFullyQualifiedColumnName();
        String segmentFKCol = CONTRACT_SETUP_DBTABLE.getDBColumn("SegmentFK").getFullyQualifiedColumnName();

        String sqlIn = "";

        for (int i = 0; i < segmentFKs.length; i++)
        {
            if (i < segmentFKs.length - 1)
            {
                sqlIn = sqlIn + segmentFKs[i] + ", ";
            }
            else
            {
                sqlIn = sqlIn + segmentFKs[i] + ")";
            }
        }

        String sql = "SELECT DISTINCT " + EDITTRX_HISTORY_TABLENAME + ".* FROM " + EDITTRX_HISTORY_TABLENAME + ", " +
                     CLIENT_SETUP_TABLENAME + ", " + EDITTRX_TABLENAME + ", " + CONTRACT_SETUP_TABLENAME +
                     " WHERE " + editTrxFKCol + " = " + editTrxPKCol +
                     " AND " + clientSetupFKCol + " = " + clientSetupPKCol +
                     " AND " + contractSetupFKCol + " = " + contractSetupPKCol +
                     " AND " + segmentFKCol + " IN (" + sqlIn +
                     " AND " + cycleDateCol + " >= ?" +
                     " AND " + cycleDateCol + " <= ?" +
                     " AND " + pendingStatusCol + " = 'H'";

        EDITTrxHistoryVO[] editTrxHistoryVOs = null;

        Connection conn = null;

        PreparedStatement ps = null;

        try
        {
            conn = ConnectionFactory.getSingleton().getConnection(POOLNAME);

            ps = conn.prepareStatement(sql);

            ps.setDate(1, DBUtil.convertStringToDate(fromDate));
            ps.setDate(2, DBUtil.convertStringToDate(toDate));

            editTrxHistoryVOs = (EDITTrxHistoryVO[]) executeQuery(EDITTrxHistoryVO.class,
                                                     ps,
                                                      POOLNAME,
                                                       false,
                                                        null);
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

                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.

                throw new RuntimeException(e);
            }
        }

        return editTrxHistoryVOs;
    }

    public EDITTrxHistoryVO[] findEDITTrxHistoryByCompanyStructureAndCycleDate(long companyStructureFK, String fromDate, String toDate) throws Exception
    {
        String editTrxFKCol = EDITTRX_HISTORY_DBTABLE.getDBColumn("EDITTrxFK").getFullyQualifiedColumnName();
        String cycleDateCol = EDITTRX_HISTORY_DBTABLE.getDBColumn("CycleDate").getFullyQualifiedColumnName();

        String editTrxPKCol = EDITTRX_DBTABLE.getDBColumn("EDITTrxPK").getFullyQualifiedColumnName();
        String pendingStatusCol = EDITTRX_DBTABLE.getDBColumn("PendingStatus").getFullyQualifiedColumnName();
        String clientSetupFKCol = EDITTRX_DBTABLE.getDBColumn("ClientSetupFK").getFullyQualifiedColumnName();

        String clientSetupPKCol = CLIENT_SETUP_DBTABLE.getDBColumn("ClientSetupPK").getFullyQualifiedColumnName();
        String contractSetupFKCol = CLIENT_SETUP_DBTABLE.getDBColumn("ContractSetupFK").getFullyQualifiedColumnName();

        String contractSetupPKCol = CONTRACT_SETUP_DBTABLE.getDBColumn("ContractSetupPK").getFullyQualifiedColumnName();
        String segmentFKCol = CONTRACT_SETUP_DBTABLE.getDBColumn("SegmentFK").getFullyQualifiedColumnName();

        String segmentPKCol = SEGMENT_DBTABLE.getDBColumn("SegmentPK").getFullyQualifiedColumnName();
        String companyStructureFKCol = SEGMENT_DBTABLE.getDBColumn("CompanyStructureFK").getFullyQualifiedColumnName();

        String sql = "SELECT DISTINCT " + EDITTRX_HISTORY_TABLENAME + ".* FROM " + EDITTRX_HISTORY_TABLENAME + ", " +
                     CLIENT_SETUP_TABLENAME + ", " + EDITTRX_TABLENAME + ", " + CONTRACT_SETUP_TABLENAME +", " + SEGMENT_TABLENAME +
                     " WHERE " + editTrxFKCol + " = " + editTrxPKCol +
                     " AND " + clientSetupFKCol + " = " + clientSetupPKCol +
                     " AND " + contractSetupFKCol + " = " + contractSetupPKCol +
                     " AND " + segmentFKCol + " = " + segmentPKCol +
                     " AND " + companyStructureFKCol + " = " + companyStructureFK +
                     " AND " + cycleDateCol + " >= ?" +
                     " AND " + cycleDateCol + " <= ?" +
                     " AND " + pendingStatusCol + " = 'H'";

        EDITTrxHistoryVO[] editTrxHistoryVOs = null;

        Connection conn = null;

        PreparedStatement ps = null;

        try
        {
            conn = ConnectionFactory.getSingleton().getConnection(POOLNAME);

            ps = conn.prepareStatement(sql);

            ps.setDate(1, DBUtil.convertStringToDate(fromDate));
            ps.setDate(2, DBUtil.convertStringToDate(toDate));

            editTrxHistoryVOs = (EDITTrxHistoryVO[]) executeQuery(EDITTrxHistoryVO.class,
                                                     ps,
                                                      POOLNAME,
                                                       false,
                                                        null);
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

                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.

                throw new RuntimeException(e);
            }
        }

        return editTrxHistoryVOs;
    }


    public EDITTrxHistoryVO[] findEDITTrxHistoryByProcessDateTrxType(long segmentPK, String fromDate, String toDate,
                                                                     String[] trxType) throws Exception
    {
        EDITTrxHistoryVO[] editTrxHistoryVOs = null;

        String processDateCol = EDITTRX_HISTORY_DBTABLE.getDBColumn("ProcessDateTime").getFullyQualifiedColumnName();
        String editTrxFKCol = EDITTRX_HISTORY_DBTABLE.getDBColumn("EDITTrxFK").getFullyQualifiedColumnName();

        String clientSetupFKCol = EDITTRX_DBTABLE.getDBColumn("ClientSetupFK").getFullyQualifiedColumnName();
        String statusCol = EDITTRX_DBTABLE.getDBColumn("Status").getFullyQualifiedColumnName();
        String editTrxPKCol = EDITTRX_DBTABLE.getDBColumn("EDITTrxPK").getFullyQualifiedColumnName();
        String pendingStatusCol = EDITTRX_DBTABLE.getDBColumn("PendingStatus").getFullyQualifiedColumnName();
        String transactionTypeCTCol = EDITTRX_DBTABLE.getDBColumn("TransactionTypeCT").getFullyQualifiedColumnName();

        String contractSetupFKCol = CLIENT_SETUP_DBTABLE.getDBColumn("ContractSetupFK").getFullyQualifiedColumnName();
        String clientSetupPKCol = CLIENT_SETUP_DBTABLE.getDBColumn("ClientSetupPK").getFullyQualifiedColumnName();

        String segmentFKCol = CONTRACT_SETUP_DBTABLE.getDBColumn("SegmentFK").getFullyQualifiedColumnName();
        String contractSetupPKCol = CONTRACT_SETUP_DBTABLE.getDBColumn("ContractSetupPK").getFullyQualifiedColumnName();

        String sql = "SELECT DISTINCT " + EDITTRX_HISTORY_TABLENAME + ".* FROM " + EDITTRX_HISTORY_TABLENAME + ", " +
                     EDITTRX_TABLENAME + ", " + CLIENT_SETUP_TABLENAME + ", " + CONTRACT_SETUP_TABLENAME +
                     " WHERE " + segmentFKCol + " = " + segmentPK +
                     " AND " + contractSetupFKCol + " = " + contractSetupPKCol +
                     " AND " + clientSetupFKCol + " = " + clientSetupPKCol +
                     " AND (" + statusCol + " = 'N'" +
                     " OR " + statusCol + " = 'A')" +
                     " AND " + editTrxFKCol + " = " + editTrxPKCol +
                     " AND " + processDateCol + " >= ?" +
                     " AND " + processDateCol + " <= ?" +
                     " AND " + pendingStatusCol + " = 'H'" +
                     " AND " + transactionTypeCTCol + " IN('";

        for (int i = 0; i < trxType.length; i++)
        {
            if (i < trxType.length - 1)
            {
                sql = sql + trxType[i] + "', '";
            }
            else
            {
                sql = sql + trxType[i] + "')";
            }
        }

        Connection conn = null;

        PreparedStatement ps = null;

        try
        {
            EDITDateTime fromDateTime = new EDITDateTime(new EDITDate(fromDate), EDITDateTime.DEFAULT_MIN_TIME);
            EDITDateTime toDateTime = new EDITDateTime(new EDITDate(toDate), EDITDateTime.DEFAULT_MAX_TIME);

            conn = ConnectionFactory.getSingleton().getConnection(POOLNAME);

            ps = conn.prepareStatement(sql);

            ps.setTimestamp(1, DBUtil.convertStringToTimestamp(fromDateTime.getFormattedDateTime()));
            ps.setTimestamp(2, DBUtil.convertStringToTimestamp(toDateTime.getFormattedDateTime()));

            editTrxHistoryVOs = (EDITTrxHistoryVO[]) executeQuery(EDITTrxHistoryVO.class,
                                                                  ps,
                                                                  POOLNAME,
                                                                  false,
                                                                  null);
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

                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.

                throw new RuntimeException(e);
            }
        }

        return editTrxHistoryVOs;
    }

    public EDITTrxHistoryVO[] findEDITTrxHistoryByDateLTE_And_TrxType(long segmentPK,
                                                                      String toDate,
                                                                      String[] trxType) throws Exception
    {
        EDITTrxHistoryVO[] editTrxHistoryVOs = null;

        String processDateCol = EDITTRX_HISTORY_DBTABLE.getDBColumn("ProcessDateTime").getFullyQualifiedColumnName();
        String editTrxFKCol = EDITTRX_HISTORY_DBTABLE.getDBColumn("EDITTrxFK").getFullyQualifiedColumnName();

        String clientSetupFKCol = EDITTRX_DBTABLE.getDBColumn("ClientSetupFK").getFullyQualifiedColumnName();
        String statusCol = EDITTRX_DBTABLE.getDBColumn("Status").getFullyQualifiedColumnName();
        String editTrxPKCol = EDITTRX_DBTABLE.getDBColumn("EDITTrxPK").getFullyQualifiedColumnName();
        String pendingStatusCol = EDITTRX_DBTABLE.getDBColumn("PendingStatus").getFullyQualifiedColumnName();
        String transactionTypeCTCol = EDITTRX_DBTABLE.getDBColumn("TransactionTypeCT").getFullyQualifiedColumnName();
        String effectiveDateCol = EDITTRX_DBTABLE.getDBColumn("EffectiveDate").getFullyQualifiedColumnName();

        String contractSetupFKCol = CLIENT_SETUP_DBTABLE.getDBColumn("ContractSetupFK").getFullyQualifiedColumnName();
        String clientSetupPKCol = CLIENT_SETUP_DBTABLE.getDBColumn("ClientSetupPK").getFullyQualifiedColumnName();

        String segmentFKCol = CONTRACT_SETUP_DBTABLE.getDBColumn("SegmentFK").getFullyQualifiedColumnName();
        String contractSetupPKCol = CONTRACT_SETUP_DBTABLE.getDBColumn("ContractSetupPK").getFullyQualifiedColumnName();

        String sql = "SELECT " + EDITTRX_HISTORY_TABLENAME + ".* FROM " + EDITTRX_HISTORY_TABLENAME + ", " +
                     EDITTRX_TABLENAME + ", " + CLIENT_SETUP_TABLENAME + ", " + CONTRACT_SETUP_TABLENAME +
                     " WHERE " + segmentFKCol + " = " + segmentPK +
                     " AND " + contractSetupFKCol + " = " + contractSetupPKCol +
                     " AND " + clientSetupFKCol + " = " + clientSetupPKCol +
                     " AND (" + statusCol + " = 'N'" +
                     " OR " + statusCol + " = 'A')" +
                     " AND " + editTrxFKCol + " = " + editTrxPKCol +
                     " AND " + processDateCol + " < ?" +
                     " AND " + pendingStatusCol + " = 'H'" +
                     " AND " + transactionTypeCTCol + " IN('";

        for (int i = 0; i < trxType.length; i++)
        {
            if (i < trxType.length - 1)
            {
                sql = sql + trxType[i] + "', '";
            }
            else
            {
                sql = sql + trxType[i] + "')";
            }
        }

        sql = sql + " ORDER BY " + effectiveDateCol + " ASC";

        Connection conn = null;

        PreparedStatement ps = null;

        try
        {
            conn = ConnectionFactory.getSingleton().getConnection(POOLNAME);

            ps = conn.prepareStatement(sql);

            ps.setTimestamp(1, DBUtil.convertStringToTimestamp(toDate + " 23:59:59:999"));

            editTrxHistoryVOs = (EDITTrxHistoryVO[]) executeQuery(EDITTrxHistoryVO.class,
                                                                  ps,
                                                                  POOLNAME,
                                                                  false,
                                                                  null);
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

                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.

                throw new RuntimeException(e);
            }
        }

        return editTrxHistoryVOs;
    }

    /**
     * Finds all EDITTrxHistory records for the given segment whose effective date is greater than
     * or equal to the given fromDate and less than or equal to the given toDate.
     * If the effective date is equal to the given fromDate, the transaction priority must be
     * greater than the transaction priority of the given transaction type.  If the effective date
     * is equal to the given toDate, the transaction priority must be less than or equal to the
     * priority of the given transaction type
     * @param segmentPK
     * @param effDate
     * @param transactionType
     * @return
     * @throws Exception
     */
    public EDITTrxHistoryVO[] findEDITTrxHistoryByEffectiveDate(long segmentPK,
                                                                String fromDate,
                                                                String toDate,
                                                                String transactionType) throws Exception
    {
        EDITTrxHistoryVO[] editTrxHistoryVOs = null;

        DBTable transactionPriorityDBTable = DBTable.getDBTableForTable("TransactionPriority");

        String transactionPriorityTable = transactionPriorityDBTable.getFullyQualifiedTableName();
        String trxPriorityTransactionTypeCTCol = transactionPriorityDBTable.getDBColumn("TransactionTypeCT").getFullyQualifiedColumnName();
        String priorityCol = transactionPriorityDBTable.getDBColumn("Priority").getFullyQualifiedColumnName();

        String editTrxFKCol = EDITTRX_HISTORY_DBTABLE.getDBColumn("EDITTrxFK").getFullyQualifiedColumnName();
        String editTrxHistoryPKCol = EDITTRX_HISTORY_DBTABLE.getDBColumn("EDITTrxHistoryPK").getFullyQualifiedColumnName();

        String editTrxPKCol = EDITTRX_DBTABLE.getDBColumn("EDITTrxPK").getFullyQualifiedColumnName();
        String clientSetupFKCol = EDITTRX_DBTABLE.getDBColumn("ClientSetupFK").getFullyQualifiedColumnName();
        String statusCol = EDITTRX_DBTABLE.getDBColumn("Status").getFullyQualifiedColumnName();
        String effectiveDateCol = EDITTRX_DBTABLE.getDBColumn("EffectiveDate").getFullyQualifiedColumnName();
        String editTrxTransactionTypeCTCol = EDITTRX_DBTABLE.getDBColumn("TransactionTypeCT").getFullyQualifiedColumnName();
        String pendingStatusCol = EDITTRX_DBTABLE.getDBColumn("PendingStatus").getFullyQualifiedColumnName();

        String clientSetupPKCol = CLIENT_SETUP_DBTABLE.getDBColumn("ClientSetupPK").getFullyQualifiedColumnName();
        String contractSetupFKCol = CLIENT_SETUP_DBTABLE.getDBColumn("ContractSetupFK").getFullyQualifiedColumnName();

        String contractSetupPKCol = CONTRACT_SETUP_DBTABLE.getDBColumn("ContractSetupPK").getFullyQualifiedColumnName();
        String segmentFKCol = CONTRACT_SETUP_DBTABLE.getDBColumn("SegmentFK").getFullyQualifiedColumnName();

        String sql = " SELECT DISTINCT " + EDITTRX_HISTORY_TABLENAME + ".* FROM " + EDITTRX_HISTORY_TABLENAME + ", " +
                     EDITTRX_TABLENAME + ", " + CLIENT_SETUP_TABLENAME + ", " + CONTRACT_SETUP_TABLENAME + ", " + transactionPriorityTable +
                     " WHERE " + segmentFKCol + " = " + segmentPK +
                     " AND " + contractSetupFKCol + " = " + contractSetupPKCol +
                     " AND " + clientSetupFKCol + " = " + clientSetupPKCol +
                     " AND " + statusCol + "IN ('N', 'A')" +
                     " AND " + editTrxFKCol + " = " + editTrxPKCol +
                     " AND ((" + effectiveDateCol + " > ?" +
                     " AND " + effectiveDateCol + " < ?)" +
                     "  OR (" + effectiveDateCol + " = ?" +
                     " AND (" + trxPriorityTransactionTypeCTCol + " = " + editTrxTransactionTypeCTCol +
                     " AND " + priorityCol + " <= (SELECT " + priorityCol + " FROM " + transactionPriorityTable +
                     " WHERE " + trxPriorityTransactionTypeCTCol + " = '" + transactionType + "')))" +
                     " OR (" + effectiveDateCol + " = ?" +
                     " AND (" + trxPriorityTransactionTypeCTCol + " = " + editTrxTransactionTypeCTCol +
                     " AND " + priorityCol + " >= (SELECT " + priorityCol + " FROM " + transactionPriorityTable +
                     " WHERE " + trxPriorityTransactionTypeCTCol + " = '" + transactionType + "'))))" +
                     " AND " + pendingStatusCol + " = 'H'" +
                     " ORDER BY " + editTrxHistoryPKCol + " ASC";

        Connection conn = null;

        PreparedStatement ps = null;

        try
        {
            conn = ConnectionFactory.getSingleton().getConnection(POOLNAME);

            ps = conn.prepareStatement(sql);

            ps.setDate(1, DBUtil.convertStringToDate(fromDate));
            ps.setDate(2, DBUtil.convertStringToDate(toDate));
            ps.setDate(3, DBUtil.convertStringToDate(toDate));
            ps.setDate(4, DBUtil.convertStringToDate(fromDate));

            editTrxHistoryVOs = (EDITTrxHistoryVO[]) executeQuery(EDITTrxHistoryVO.class,
                                                                  ps,
                                                                  POOLNAME,
                                                                  false,
                                                                  null);
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

                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.

                throw new RuntimeException(e);
            }
        }

        return editTrxHistoryVOs;
    }

    /**
     * Finds all EDITTrxHistory records for the given segment whose effective date is less than
     * or equal to the given effective date.  If the effective date = given effective date,
     * the transaction priority must be less than or equal to the priority of the given transaction type
     * @param segmentPK
     * @param effDate
     * @param transactionType
     * @return
     * @throws Exception
     */
    public EDITTrxHistoryVO[] findEDITTrxHistoryByEffectiveDateLTE(long segmentPK,
                                                                   String effDate,
                                                                   String transactionType) throws Exception
    {
        EDITTrxHistoryVO[] editTrxHistoryVOs = null;

        DBTable transactionPriorityDBTable = DBTable.getDBTableForTable("TransactionPriority");

        String transactionPriorityTable = transactionPriorityDBTable.getFullyQualifiedTableName();
        String trxPriorityTransactionTypeCTCol = transactionPriorityDBTable.getDBColumn("TransactionTypeCT").getFullyQualifiedColumnName();
        String priorityCol = transactionPriorityDBTable.getDBColumn("Priority").getFullyQualifiedColumnName();

        String editTrxFKCol = EDITTRX_HISTORY_DBTABLE.getDBColumn("EDITTrxFK").getFullyQualifiedColumnName();

        String editTrxPKCol = EDITTRX_DBTABLE.getDBColumn("EDITTrxPK").getFullyQualifiedColumnName();
        String clientSetupFKCol = EDITTRX_DBTABLE.getDBColumn("ClientSetupFK").getFullyQualifiedColumnName();
        String statusCol = EDITTRX_DBTABLE.getDBColumn("Status").getFullyQualifiedColumnName();
        String effectiveDateCol = EDITTRX_DBTABLE.getDBColumn("EffectiveDate").getFullyQualifiedColumnName();
        String editTrxTransactionTypeCTCol = EDITTRX_DBTABLE.getDBColumn("TransactionTypeCT").getFullyQualifiedColumnName();
        String pendingStatusCol = EDITTRX_DBTABLE.getDBColumn("PendingStatus").getFullyQualifiedColumnName();

        String clientSetupPKCol = CLIENT_SETUP_DBTABLE.getDBColumn("ClientSetupPK").getFullyQualifiedColumnName();
        String contractSetupFKCol = CLIENT_SETUP_DBTABLE.getDBColumn("ContractSetupFK").getFullyQualifiedColumnName();

        String contractSetupPKCol = CONTRACT_SETUP_DBTABLE.getDBColumn("ContractSetupPK").getFullyQualifiedColumnName();
        String segmentFKCol = CONTRACT_SETUP_DBTABLE.getDBColumn("SegmentFK").getFullyQualifiedColumnName();

        String sql = " SELECT DISTINCT " + EDITTRX_HISTORY_TABLENAME + ".* FROM " + EDITTRX_HISTORY_TABLENAME + ", " +
                     EDITTRX_TABLENAME + ", " + CLIENT_SETUP_TABLENAME + ", " + CONTRACT_SETUP_TABLENAME + ", " + transactionPriorityTable +
                     " WHERE " + segmentFKCol + " = " + segmentPK +
                     " AND " + contractSetupFKCol + " = " + contractSetupPKCol +
                     " AND " + clientSetupFKCol + " = " + clientSetupPKCol +
                     " AND " + statusCol + "IN ('N', 'A')" +
                     " AND " + editTrxFKCol + " = " + editTrxPKCol +
                     " AND (" + effectiveDateCol + " < ?" +
                     "  OR (" + effectiveDateCol + " = ?" +
                     " AND (" + trxPriorityTransactionTypeCTCol + " = " + editTrxTransactionTypeCTCol +
                     " AND " + priorityCol + " <= (SELECT " + priorityCol + " FROM " + transactionPriorityTable +
                     " WHERE " + trxPriorityTransactionTypeCTCol + " = '" + transactionType + "'))))" +
                     " AND " + pendingStatusCol + " = 'H'";

        Connection conn = null;

        PreparedStatement ps = null;

        try
        {
            conn = ConnectionFactory.getSingleton().getConnection(POOLNAME);

            ps = conn.prepareStatement(sql);

            ps.setDate(1, DBUtil.convertStringToDate(effDate));
            ps.setDate(2, DBUtil.convertStringToDate(effDate));

            editTrxHistoryVOs = (EDITTrxHistoryVO[]) executeQuery(EDITTrxHistoryVO.class,
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

        return editTrxHistoryVOs;
    }

    public EDITTrxHistoryVO[] findEDITTrxHistoryVOByAccountPendingStatus_AND_ProcessDateLTE(String accountPendingStatus,
                                                                                            String processDate) throws Exception
    {
        String editTrxFKCol = EDITTRX_HISTORY_DBTABLE.getDBColumn("EDITTrxFK").getFullyQualifiedColumnName();
        String accountingPendingStatusCol = EDITTRX_HISTORY_DBTABLE.getDBColumn("AccountingPendingStatus").getFullyQualifiedColumnName();
        String processDateCol = EDITTRX_HISTORY_DBTABLE.getDBColumn("ProcessDateTime").getFullyQualifiedColumnName();

        String editTrxPKCol = EDITTRX_DBTABLE.getDBColumn("EDITTrxPK").getFullyQualifiedColumnName();
        String clientSetupFKCol = EDITTRX_DBTABLE.getDBColumn("ClientSetupFK").getFullyQualifiedColumnName();

        String clientSetupPKCol = CLIENT_SETUP_DBTABLE.getDBColumn("ClientSetupPK").getFullyQualifiedColumnName();
        String contractSetupFKCol = CLIENT_SETUP_DBTABLE.getDBColumn("ContractSetupFK").getFullyQualifiedColumnName();

        String contractSetupPKCol = CONTRACT_SETUP_DBTABLE.getDBColumn("ContractSetupPK").getFullyQualifiedColumnName();

        String sql = "SELECT " + EDITTRX_HISTORY_TABLENAME + ".* FROM " + EDITTRX_HISTORY_TABLENAME + ", " +
                     EDITTRX_TABLENAME + ", " + CLIENT_SETUP_TABLENAME + ", " + CONTRACT_SETUP_TABLENAME +
                     " WHERE " + editTrxFKCol + " = " + editTrxPKCol +
                     " AND " + clientSetupFKCol + " = " + clientSetupPKCol +
                     " AND " + contractSetupFKCol + " = " + contractSetupPKCol +
                     " AND " + accountingPendingStatusCol + " = ?" +
                     " AND " + processDateCol + " <= ?";

        EDITTrxHistoryVO[] editTrxHistoryVOs = null;
        Connection conn = null;
        PreparedStatement ps = null;

        try
        {
            EDITDateTime processDateTime = new EDITDateTime(new EDITDate(processDate), EDITDateTime.DEFAULT_MIN_TIME);

            conn = ConnectionFactory.getSingleton().getConnection(POOLNAME);

            ps = conn.prepareStatement(sql);

            ps.setString(1, accountPendingStatus);
            ps.setDate(2, DBUtil.convertStringToDate(processDateTime.getFormattedDateTime()));

            editTrxHistoryVOs = (EDITTrxHistoryVO[]) executeQuery(EDITTrxHistoryVO.class,
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

        return editTrxHistoryVOs;
    }

    public EDITTrxHistoryVO[] findEDITTrxHistoryVOClosestToQuoteDate(String quoteDate, long segmentFK) throws Exception
    {
        DBTable transactionPriorityDBTable = DBTable.getDBTableForTable("TransactionPriority");

        String transactionPriorityTable = transactionPriorityDBTable.getFullyQualifiedTableName();

        String editTrxHistoryPKCol = EDITTRX_HISTORY_DBTABLE.getDBColumn("EDITTrxHistoryPK").getFullyQualifiedColumnName();
        String editTrxFKCol = EDITTRX_HISTORY_DBTABLE.getDBColumn("EDITTrxFK").getFullyQualifiedColumnName();

        String editTrxPKCol = EDITTRX_DBTABLE.getDBColumn("EDITTrxPK").getFullyQualifiedColumnName();
        String effectiveDateCol = EDITTRX_DBTABLE.getDBColumn("EffectiveDate").getFullyQualifiedColumnName();
        String clientSetupFKCol = EDITTRX_DBTABLE.getDBColumn("ClientSetupFK").getFullyQualifiedColumnName();
        String sequenceNumberCol = EDITTRX_DBTABLE.getDBColumn("SequenceNumber").getFullyQualifiedColumnName();

        String clientSetupPKCol = CLIENT_SETUP_DBTABLE.getDBColumn("ClientSetupPK").getFullyQualifiedColumnName();
        String contractSetupFKCol = CLIENT_SETUP_DBTABLE.getDBColumn("ContractSetupFK").getFullyQualifiedColumnName();

        String contractSetupPKCol = CONTRACT_SETUP_DBTABLE.getDBColumn("ContractSetupPK").getFullyQualifiedColumnName();
        String segmentFKCol = CONTRACT_SETUP_DBTABLE.getDBColumn("SegmentFK").getFullyQualifiedColumnName();

        String priorityCol = transactionPriorityDBTable.getDBColumn("Priority").getFullyQualifiedColumnName();

        String sql = "SELECT TOP 1 " + EDITTRX_HISTORY_TABLENAME + ".* FROM " + EDITTRX_HISTORY_TABLENAME + ", " +
                     EDITTRX_TABLENAME + ", " + transactionPriorityTable +
                     " WHERE " + editTrxHistoryPKCol + " IN ( SELECT " + editTrxHistoryPKCol +
                     " FROM " + EDITTRX_HISTORY_TABLENAME + " WHERE " + editTrxFKCol + " IN (SELECT " + editTrxPKCol +
                     " FROM " + EDITTRX_TABLENAME + " WHERE " + effectiveDateCol + " <= ?" +
                     " AND " + clientSetupFKCol + " IN (SELECT " + clientSetupPKCol + " FROM " + CLIENT_SETUP_TABLENAME +
                     " WHERE " + contractSetupFKCol + " IN (SELECT " + contractSetupPKCol + " FROM " + CONTRACT_SETUP_TABLENAME +
                     " WHERE " + segmentFKCol + " = ?)))) " +
                     " ORDER BY " + effectiveDateCol + " DESC, " + priorityCol + " DESC, " + sequenceNumberCol + " DESC";

        EDITTrxHistoryVO[] editTrxHistoryVOs = null;
        Connection conn = null;
        PreparedStatement ps = null;

        try
        {
            conn = ConnectionFactory.getSingleton().getConnection(POOLNAME);

            ps = conn.prepareStatement(sql);

            ps.setDate(1, DBUtil.convertStringToDate(quoteDate));
            ps.setLong(2, segmentFK);

            editTrxHistoryVOs = (EDITTrxHistoryVO[]) executeQuery(EDITTrxHistoryVO.class,
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

        return editTrxHistoryVOs;
    }

    /**
     * Finds all EDITTrxHistorys for a given segmentPK
     * @param segmentPK     primary key of associated segment
     * @return All EDITTrxHistoryVOs for given segment
     * @throws Exception
     */
    public EDITTrxHistoryVO[] findEDITTrxHistoryBySegmentPK(long segmentPK) throws Exception
    {
        String editTrxFKCol = EDITTRX_HISTORY_DBTABLE.getDBColumn("EDITTrxFK").getFullyQualifiedColumnName();

        String editTrxPKCol = EDITTRX_DBTABLE.getDBColumn("EDITTrxPK").getFullyQualifiedColumnName();
        String clientSetupFKCol = EDITTRX_DBTABLE.getDBColumn("ClientSetupFK").getFullyQualifiedColumnName();

        String clientSetupPKCol = CLIENT_SETUP_DBTABLE.getDBColumn("ClientSetupPK").getFullyQualifiedColumnName();
        String contractSetupFKCol = CLIENT_SETUP_DBTABLE.getDBColumn("ContractSetupFK").getFullyQualifiedColumnName();

        String contractSetupPKCol = CONTRACT_SETUP_DBTABLE.getDBColumn("ContractSetupPK").getFullyQualifiedColumnName();
        String segmentFKCol = CONTRACT_SETUP_DBTABLE.getDBColumn("SegmentFK").getFullyQualifiedColumnName();

        String sql = "SELECT " + EDITTRX_HISTORY_TABLENAME + ".* FROM " + EDITTRX_HISTORY_TABLENAME + ", " +
                     EDITTRX_TABLENAME + ", " + CLIENT_SETUP_TABLENAME + ", " + CONTRACT_SETUP_TABLENAME +
                     " WHERE " + editTrxFKCol + " = " + editTrxPKCol +
                     " AND " + clientSetupFKCol + " = " + clientSetupPKCol +
                     " AND " + contractSetupFKCol + " = " + contractSetupPKCol +
                     " AND " + segmentFKCol + " = " + segmentPK;

        return (EDITTrxHistoryVO[]) executeQuery(EDITTrxHistoryVO.class,
                                                  sql,
                                                   POOLNAME,
                                                    false,
                                                     null);
    }

    public EDITTrxHistoryVO[] findEDITTrxByDate_And_Fund(String startDate,
                                                         String endDate,
                                                         String dateType,
                                                         long filteredFundFK) throws Exception
    {
        List editTrxHistoryList = new ArrayList();

        long[] editTrxHistoryPKs = new event.dm.dao.FastDAO().findEDITTrxHistoryPKsByInvestment(filteredFundFK);

        if (editTrxHistoryPKs != null && editTrxHistoryPKs.length > 0)
        {
            int i = 0;
            int j = editTrxHistoryPKs.length;
            String sqlIn = "";
            if (j > 1000)
            {
                j = 1000;

                while (j < editTrxHistoryPKs.length)
                {
                    sqlIn = createEDITTrxHistoryINStatement(editTrxHistoryPKs, i, j);

                    editTrxHistoryList = performDBQueryForEDITrxByDate_And_Fund(startDate, endDate, dateType, sqlIn, editTrxHistoryList);

                    i += 1000;
                    j += 1000;
                }

                sqlIn = createEDITTrxHistoryINStatement(editTrxHistoryPKs, i, editTrxHistoryPKs.length);

                editTrxHistoryList = performDBQueryForEDITrxByDate_And_Fund(startDate, endDate, dateType, sqlIn, editTrxHistoryList);
            }
            else
            {
                sqlIn = createEDITTrxHistoryINStatement(editTrxHistoryPKs, i, editTrxHistoryPKs.length);

                editTrxHistoryList = performDBQueryForEDITrxByDate_And_Fund(startDate, endDate, dateType, sqlIn, editTrxHistoryList);
            }
        }

        if (editTrxHistoryList.size() > 0)
        {
            return (EDITTrxHistoryVO[]) editTrxHistoryList.toArray(new EDITTrxHistoryVO[editTrxHistoryList.size()]);
        }
        else
        {
            return null;
        }
    }

    private String createEDITTrxHistoryINStatement(long[] editTrxHistoryPKs, int i, int j)
    {
        String sqlIn = "(";

        for (int k = i; k < j; k++)
        {
            if (k < j - 1)
            {
                sqlIn = sqlIn + editTrxHistoryPKs[k] + ", ";
            }
            else
            {
                sqlIn = sqlIn + editTrxHistoryPKs[k] + ")";
            }
        }

        return sqlIn;
    }

    private List performDBQueryForEDITrxByDate_And_Fund(String startDate, String endDate, String dateType,
                                                        String sqlIn, List editTrxHistoryList)
    {
        EDITTrxHistoryVO[] editTrxHistoryVOs = null;

        String editTrxFKCol = EDITTRX_HISTORY_DBTABLE.getDBColumn("EDITTrxFK").getFullyQualifiedColumnName();
        String editTrxHistoryPKCol = EDITTRX_HISTORY_DBTABLE.getDBColumn("EDITTrxHistoryPK").getFullyQualifiedColumnName();
        String processDateTimeCol = EDITTRX_HISTORY_DBTABLE.getDBColumn("ProcessDateTime").getFullyQualifiedColumnName();

        String editTrxPKCol = EDITTRX_DBTABLE.getDBColumn("EDITTrxPK").getFullyQualifiedColumnName();
        String effectiveDateCol = EDITTRX_DBTABLE.getDBColumn("EffectiveDate").getFullyQualifiedColumnName();
        String statusCol = EDITTRX_DBTABLE.getDBColumn("Status").getFullyQualifiedColumnName();
        String accountingPeriodCol = EDITTRX_DBTABLE.getDBColumn("AccountingPeriod").getFullyQualifiedColumnName();
        String trxTypeCol = EDITTRX_DBTABLE.getDBColumn("TransactionTypeCT").getFullyQualifiedColumnName();

        String dateColumnToUse = "";
        String orderBy = "";
        if (dateType.equalsIgnoreCase("Effective"))
        {
            dateColumnToUse = effectiveDateCol;
            orderBy = effectiveDateCol + ", " + processDateTimeCol + ", " + accountingPeriodCol;
        }
        else if (dateType.equalsIgnoreCase("Process"))
        {
            dateColumnToUse = processDateTimeCol;
            orderBy = processDateTimeCol + ", " + effectiveDateCol + ", " + accountingPeriodCol;
        }
        else
        {
            dateColumnToUse = accountingPeriodCol;
            startDate = startDate.substring(0,7);
            endDate = endDate.substring(0,7);
            orderBy = accountingPeriodCol + ", " + effectiveDateCol + ", " + processDateTimeCol;
        }

        String sql = "SELECT " + EDITTRX_HISTORY_TABLENAME + ".* FROM " + EDITTRX_HISTORY_TABLENAME + ", " +
                     EDITTRX_TABLENAME +
                     " WHERE " + editTrxHistoryPKCol + " IN " + sqlIn +
                     " AND " + editTrxFKCol + " = " + editTrxPKCol +
                     " AND " + statusCol + " IN ('N', 'A')" +
                     " AND " + trxTypeCol + " NOT IN ('MF','IS','CC')" +
                     " AND " + dateColumnToUse + " >= ?" +
                     " AND " + dateColumnToUse + " <= ?" +
                     " ORDER BY " + orderBy + " ASC";

        Connection conn = null;

        PreparedStatement ps = null;

        try
        {
            conn = ConnectionFactory.getSingleton().getConnection(POOLNAME);

            ps = conn.prepareStatement(sql);

            if (dateType.equalsIgnoreCase("AccountingPeriod"))
            {
                ps.setString(1, startDate);
                ps.setString(2, endDate);
            }
            else
            {
                ps.setTimestamp(1, DBUtil.convertStringToTimestamp(startDate + " 00:00:00:000"));
                ps.setTimestamp(2, DBUtil.convertStringToTimestamp(endDate + " 23:59:59:999"));
            }

            editTrxHistoryVOs = (EDITTrxHistoryVO[]) executeQuery(EDITTrxHistoryVO.class,
                                                                  ps,
                                                                  POOLNAME,
                                                                  false,
                                                                  null);
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

                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.

                throw new RuntimeException(e);
            }
        }


        if (editTrxHistoryVOs != null)
        {
            for (int k = 0; k < editTrxHistoryVOs.length; k++)
            {
                editTrxHistoryList.add(editTrxHistoryVOs[k]);
            }
        }

        return editTrxHistoryList;
    }


    /**
     * This variation is used by the Money Move Report.  We need to break out
     * the Fees by ChargeCode because the client wants to see the Client
     * Fund Number if it is specified.
     * @param startDate
     * @param endDate
     * @param dateType
     * @param filteredFundFK
     * @param chargeCodeFK
     * @return
     * @throws Exception
     */
    public EDITTrxHistoryVO[] findEDITTrxByDate_And_Fund(String startDate,
                                                         String endDate,
                                                         String dateType,
                                                         long filteredFundFK,
                                                         long chargeCodeFK) throws Exception
    {
        EDITTrxHistoryVO[] editTrxHistoryVOs = null;

        DBTable investmentDBTable = DBTable.getDBTableForTable("Investment");
        DBTable investmentHistoryDBTable = DBTable.getDBTableForTable("InvestmentHistory");

        String investmentTable = investmentDBTable.getFullyQualifiedTableName();
        String investmentHistoryTable = investmentHistoryDBTable.getFullyQualifiedTableName();

        String editTrxFKCol = EDITTRX_HISTORY_DBTABLE.getDBColumn("EDITTrxFK").getFullyQualifiedColumnName();
        String editTrxHistoryPKCol = EDITTRX_HISTORY_DBTABLE.getDBColumn("EDITTrxHistoryPK").getFullyQualifiedColumnName();
        String processDateTimeCol = EDITTRX_HISTORY_DBTABLE.getDBColumn("ProcessDateTime").getFullyQualifiedColumnName();

        String editTrxPKCol = EDITTRX_DBTABLE.getDBColumn("EDITTrxPK").getFullyQualifiedColumnName();
        String effectiveDateCol = EDITTRX_DBTABLE.getDBColumn("EffectiveDate").getFullyQualifiedColumnName();
        String statusCol = EDITTRX_DBTABLE.getDBColumn("Status").getFullyQualifiedColumnName();
        String accountingPeriodCol = EDITTRX_DBTABLE.getDBColumn("AccountingPeriod").getFullyQualifiedColumnName();
        String trxTypeCol = EDITTRX_DBTABLE.getDBColumn("TransactionTypeCT").getFullyQualifiedColumnName();

        String editTrxHistoryFKCol = investmentHistoryDBTable.getDBColumn("EDITTrxHistoryFK").getFullyQualifiedColumnName();
        String investmentFKCol = investmentHistoryDBTable.getDBColumn("InvestmentFK").getFullyQualifiedColumnName();

        String investmentChargeCodeFKCol = investmentHistoryDBTable.getDBColumn("ChargeCodeFK").getFullyQualifiedColumnName();

        String investmentPKCol = investmentDBTable.getDBColumn("InvestmentPK").getFullyQualifiedColumnName();
        String filteredFundFKCol = investmentDBTable.getDBColumn("FilteredFundFK").getFullyQualifiedColumnName();

        String dateColumnToUse = "";
        String orderBy = "";

        if (dateType.equalsIgnoreCase("Effective"))
        {
            dateColumnToUse = effectiveDateCol;
            orderBy = effectiveDateCol + ", " + processDateTimeCol + ", " + accountingPeriodCol;
        }
        else if (dateType.equalsIgnoreCase("Process"))
        {
            dateColumnToUse = processDateTimeCol;
            orderBy = processDateTimeCol + ", " + effectiveDateCol + ", " + accountingPeriodCol;
        }
        else
        {
            dateColumnToUse = accountingPeriodCol;
            startDate = startDate.substring(0,7);
            endDate = endDate.substring(0,7);
            orderBy = accountingPeriodCol + ", " + effectiveDateCol + ", " + processDateTimeCol;
        }

        String investHistChargeCodeWhereClause = null;
        if (chargeCodeFK == 0)
        {
             investHistChargeCodeWhereClause = investmentChargeCodeFKCol + " IS NULL ";
        }
        else
        {
             investHistChargeCodeWhereClause = investmentChargeCodeFKCol + " = " + chargeCodeFK;
        }

        String sql = "SELECT DISTINCT " + EDITTRX_HISTORY_TABLENAME + ".*" + ", " + EDITTRX_TABLENAME + ".* FROM " + EDITTRX_HISTORY_TABLENAME + ", " +
                     EDITTRX_TABLENAME + ", " + investmentHistoryTable + ", " + investmentTable +
                     " WHERE " + filteredFundFKCol + " = " + filteredFundFK +
                     " AND " + investmentPKCol + " = " + investmentFKCol +
                     " AND " + investHistChargeCodeWhereClause +
                     " AND " + editTrxHistoryFKCol + " = " + editTrxHistoryPKCol +
                     " AND " + editTrxFKCol + " = " + editTrxPKCol +
                     " AND " + statusCol + " IN ('N', 'A')" +
                     " AND " + trxTypeCol + " NOT IN ('MF','IS','CC')" +
                     " AND " + dateColumnToUse + " >= ?" +
                     " AND " + dateColumnToUse + " <= ?" +
                     " ORDER BY " + orderBy + " ASC";

        Connection conn = null;

        PreparedStatement ps = null;

        try
        {
            conn = ConnectionFactory.getSingleton().getConnection(POOLNAME);

            ps = conn.prepareStatement(sql);

            if (dateType.equalsIgnoreCase("AccountingPeriod"))
            {
                ps.setString(1, startDate);
                ps.setString(2, endDate);
            }
            else
            {
                ps.setTimestamp(1, DBUtil.convertStringToTimestamp(startDate + " 00:00:00:000"));
                ps.setTimestamp(2, DBUtil.convertStringToTimestamp(endDate + " 23:59:59:999"));
            }

            editTrxHistoryVOs = (EDITTrxHistoryVO[]) executeQuery(EDITTrxHistoryVO.class,
                                                                  ps,
                                                                  POOLNAME,
                                                                  false,
                                                                  null);
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

                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.

                throw new RuntimeException(e);
            }
        }

        return editTrxHistoryVOs;
    }

    /**
     * This variation is used by the Money Move Report.  We need to break out
     * the Fees by ChargeCode because the client wants to see the Client
     * Fund Number if it is specified.
     * @param startDate
     * @param endDate
     * @param dateType
     * @param filteredFundFK
     * @param chargeCodeFK
     * @param segmentFKs
     * @return
     * @throws Exception
     */
    public EDITTrxHistoryVO[] findEDITTrxByDateSegmentFund(String startDate,
                                                           String endDate,
                                                           String dateType,
                                                           long filteredFundFK,
                                                           long chargeCodeFK,
                                                           long[] segmentFKs) throws Exception
    {
        EDITTrxHistoryVO[] editTrxHistoryVOs = null;

        DBTable investmentDBTable = DBTable.getDBTableForTable("Investment");
        DBTable investmentHistoryDBTable = DBTable.getDBTableForTable("InvestmentHistory");

        String investmentTable = investmentDBTable.getFullyQualifiedTableName();
        String investmentHistoryTable = investmentHistoryDBTable.getFullyQualifiedTableName();

        String editTrxFKCol = EDITTRX_HISTORY_DBTABLE.getDBColumn("EDITTrxFK").getFullyQualifiedColumnName();
        String editTrxHistoryPKCol = EDITTRX_HISTORY_DBTABLE.getDBColumn("EDITTrxHistoryPK").getFullyQualifiedColumnName();
        String processDateTimeCol = EDITTRX_HISTORY_DBTABLE.getDBColumn("ProcessDateTime").getFullyQualifiedColumnName();

        String editTrxPKCol = EDITTRX_DBTABLE.getDBColumn("EDITTrxPK").getFullyQualifiedColumnName();
        String effectiveDateCol = EDITTRX_DBTABLE.getDBColumn("EffectiveDate").getFullyQualifiedColumnName();
        String statusCol = EDITTRX_DBTABLE.getDBColumn("Status").getFullyQualifiedColumnName();
        String accountingPeriodCol = EDITTRX_DBTABLE.getDBColumn("AccountingPeriod").getFullyQualifiedColumnName();
        String clientSetupFKCol = EDITTRX_DBTABLE.getDBColumn("ClientSetupFK").getFullyQualifiedColumnName();

        String contractSetupFKCol = CLIENT_SETUP_DBTABLE.getDBColumn("ContractSetupFK").getFullyQualifiedColumnName();
        String clientSetupPKCol = CLIENT_SETUP_DBTABLE.getDBColumn("ClientSetupPK").getFullyQualifiedColumnName();

        String segmentFKCol = CONTRACT_SETUP_DBTABLE.getDBColumn("SegmentFK").getFullyQualifiedColumnName();
        String contractSetupPKCol = CONTRACT_SETUP_DBTABLE.getDBColumn("ContractSetupPK").getFullyQualifiedColumnName();

        String editTrxHistoryFKCol = investmentHistoryDBTable.getDBColumn("EDITTrxHistoryFK").getFullyQualifiedColumnName();
        String investmentFKCol = investmentHistoryDBTable.getDBColumn("InvestmentFK").getFullyQualifiedColumnName();

        String investmentChargeCodeFKCol = investmentHistoryDBTable.getDBColumn("ChargeCodeFK").getFullyQualifiedColumnName();

        String investmentPKCol = investmentDBTable.getDBColumn("InvestmentPK").getFullyQualifiedColumnName();
        String filteredFundFKCol = investmentDBTable.getDBColumn("FilteredFundFK").getFullyQualifiedColumnName();

        String dateColumnToUse = "";
        if (dateType.equalsIgnoreCase("Effective"))
        {
            dateColumnToUse = effectiveDateCol;
        }
        else
        {
            dateColumnToUse = processDateTimeCol;
        }

        String segmentFKSql = "";
        for (int i = 0; i < segmentFKs.length; i++)
        {
            if (i < segmentFKs.length - 1)
            {
                segmentFKSql += segmentFKs[i];
                segmentFKSql += ", ";
            }
            else
            {
                segmentFKSql += segmentFKs[i];
            }
        }

        String investHistChargeCodeWhereClause = null;
        if (chargeCodeFK == 0)
        {
             investHistChargeCodeWhereClause = investmentChargeCodeFKCol + " IS NULL ";
        }
        else
        {
             investHistChargeCodeWhereClause = investmentChargeCodeFKCol + " = " + chargeCodeFK;
        }

        String sql = "SELECT " + EDITTRX_HISTORY_TABLENAME + ".* FROM " + EDITTRX_HISTORY_TABLENAME + ", " +
                     EDITTRX_TABLENAME + ", " + CLIENT_SETUP_TABLENAME + ", " + CONTRACT_SETUP_TABLENAME + ", " +
                     investmentHistoryTable + ", " + investmentTable +
                     " WHERE " + filteredFundFKCol + " = " + filteredFundFK +
                     " AND " + investmentPKCol + " = " + investmentFKCol +
                     " AND " + investHistChargeCodeWhereClause +
                     " AND " + editTrxHistoryFKCol + " = " + editTrxHistoryPKCol +
                     " AND " + editTrxFKCol + " = " + editTrxPKCol +
                     " AND " + clientSetupFKCol + " = " + clientSetupPKCol +
                     " AND " + contractSetupFKCol + " = " + contractSetupPKCol +
                     " AND " + segmentFKCol + " IN(" + segmentFKSql + ")" + 
                     " AND " + statusCol + " IN ('N', 'A')" +
                     " AND " + dateColumnToUse + " >= ?" +
                     " AND " + dateColumnToUse + " <= ?" +
                     " ORDER BY " + dateColumnToUse + ", " + accountingPeriodCol + " ASC";

        Connection conn = null;

        PreparedStatement ps = null;

        try
        {
            conn = ConnectionFactory.getSingleton().getConnection(POOLNAME);

            ps = conn.prepareStatement(sql);

            ps.setTimestamp(1, DBUtil.convertStringToTimestamp(startDate + " 00:00:00:000"));
            ps.setTimestamp(2, DBUtil.convertStringToTimestamp(endDate + " 23:59:59:999"));

            editTrxHistoryVOs = (EDITTrxHistoryVO[]) executeQuery(EDITTrxHistoryVO.class,
                                                                  ps,
                                                                  POOLNAME,
                                                                  false,
                                                                  null);
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

                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.

                throw new RuntimeException(e);
            }
        }

        return editTrxHistoryVOs;
    }

    /**
     * Finder.
     * @param editTrxHistoryPK
     * @return
     */
    public EDITTrxHistoryVO[] findByPK(long editTrxHistoryPK)
    {
        String editTrxHistoryPKCol = EDITTRX_HISTORY_DBTABLE.getDBColumn("EDITTrxHistoryPK").getFullyQualifiedColumnName();

        String sql;
        sql =   " SELECT * " +
                " FROM " + EDITTRX_HISTORY_TABLENAME +
                " WHERE " + editTrxHistoryPKCol + " = " + editTrxHistoryPK;

        return (EDITTrxHistoryVO[]) executeQuery(EDITTrxHistoryVO.class,
                                                 sql,
                                                 POOLNAME,
                                                 false,
                                                 null);
    }

    /**
     * Finder.
     * @param segmentPK
     * @param cycleDate
     * @return
     */
    public EDITTrxHistoryVO[] findBy_SegmentPK_CycleDateGTE(long segmentPK, String cycleDate)
    {
        // EDITTrxHistory
        String editTrxFKCol = EDITTRX_HISTORY_DBTABLE.getDBColumn("EDITTrxFK").getFullyQualifiedColumnName();
        String cycleDateCol = EDITTRX_HISTORY_DBTABLE.getDBColumn("CycleDate").getFullyQualifiedColumnName();

        // EDITTrx
        String editTrxPKCol =  EDITTRX_DBTABLE.getDBColumn("EDITTrxPK").getFullyQualifiedColumnName();
        String clientSetupFKCol = EDITTRX_DBTABLE.getDBColumn("ClientSetupFK").getFullyQualifiedColumnName();

        // ClientSetup
        String clientSetupPKCol = CLIENT_SETUP_DBTABLE.getDBColumn("ClientSetupPK").getFullyQualifiedColumnName();
        String contractSetupFKCol = CLIENT_SETUP_DBTABLE.getDBColumn("ContractSetupFK").getFullyQualifiedColumnName();

        // ContractSetup
        String contractSetupPKCol = CONTRACT_SETUP_DBTABLE.getDBColumn("ContractSetupPK").getFullyQualifiedColumnName();
        String segmentFKCol = CONTRACT_SETUP_DBTABLE.getDBColumn("SegmentFK").getFullyQualifiedColumnName();

        String sql;
        sql =   " SELECT " + EDITTRX_HISTORY_TABLENAME + ".*" +
                " FROM " + EDITTRX_HISTORY_TABLENAME +
                " INNER JOIN " + EDITTRX_TABLENAME +
                " ON " + editTrxFKCol + " = " + editTrxPKCol +
                " INNER JOIN " + CLIENT_SETUP_TABLENAME +
                " ON " + clientSetupFKCol + " = " + clientSetupPKCol +
                " INNER JOIN " + CONTRACT_SETUP_TABLENAME +
                " ON " + contractSetupFKCol + " = " + contractSetupPKCol +
                " WHERE " + segmentFKCol + " = ?" +
                " AND " + cycleDateCol + " >= ?";


        EDITTrxHistoryVO[] editTrxHistoryVOs = null;
        Connection conn = null;
        PreparedStatement ps = null;

        try
        {
            conn = ConnectionFactory.getSingleton().getConnection(POOLNAME);

            ps = conn.prepareStatement(sql);

            ps.setLong(1, segmentPK);
            ps.setDate(2, DBUtil.convertStringToDate(cycleDate));

            editTrxHistoryVOs = (EDITTrxHistoryVO[]) executeQuery(EDITTrxHistoryVO.class,
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

        return editTrxHistoryVOs;
    }

    /**
      * Finds all EDITTrxHistory records for the given segment whose effective date is greater than
      * or equal to the given fromDate and less than or equal to the given toDate.
      * If the effective date is equal to the given fromDate, the transaction priority must be
      * greater than the transaction priority of the given transaction type.  If the effective date
      * is equal to the given toDate, the transaction priority must be less than or equal to the
      * priority of the given transaction type
      * @param segmentPK
      * @param effDate
      * @param transactionType
      * @return
      * @throws Exception
      */
     public EDITTrxHistoryVO[] findEDITTrxHistoryByEffectiveDateForAccting(long segmentPK,
                                                                 String fromDate,
                                                                 String toDate,
                                                                 String transactionType) throws Exception
     {
         EDITTrxHistoryVO[] editTrxHistoryVOs = null;

         DBTable transactionPriorityDBTable = DBTable.getDBTableForTable("TransactionPriority");

         String transactionPriorityTable = transactionPriorityDBTable.getFullyQualifiedTableName();
         String trxPriorityTransactionTypeCTCol = transactionPriorityDBTable.getDBColumn("TransactionTypeCT").getFullyQualifiedColumnName();
         String priorityCol = transactionPriorityDBTable.getDBColumn("Priority").getFullyQualifiedColumnName();

         String editTrxFKCol = EDITTRX_HISTORY_DBTABLE.getDBColumn("EDITTrxFK").getFullyQualifiedColumnName();
         String editTrxHistoryPKCol = EDITTRX_HISTORY_DBTABLE.getDBColumn("EDITTrxHistoryPK").getFullyQualifiedColumnName();

         String editTrxPKCol = EDITTRX_DBTABLE.getDBColumn("EDITTrxPK").getFullyQualifiedColumnName();
         String clientSetupFKCol = EDITTRX_DBTABLE.getDBColumn("ClientSetupFK").getFullyQualifiedColumnName();
         String statusCol = EDITTRX_DBTABLE.getDBColumn("Status").getFullyQualifiedColumnName();
         String effectiveDateCol = EDITTRX_DBTABLE.getDBColumn("EffectiveDate").getFullyQualifiedColumnName();
         String editTrxTransactionTypeCTCol = EDITTRX_DBTABLE.getDBColumn("TransactionTypeCT").getFullyQualifiedColumnName();
         String pendingStatusCol = EDITTRX_DBTABLE.getDBColumn("PendingStatus").getFullyQualifiedColumnName();

         String clientSetupPKCol = CLIENT_SETUP_DBTABLE.getDBColumn("ClientSetupPK").getFullyQualifiedColumnName();
         String contractSetupFKCol = CLIENT_SETUP_DBTABLE.getDBColumn("ContractSetupFK").getFullyQualifiedColumnName();

         String contractSetupPKCol = CONTRACT_SETUP_DBTABLE.getDBColumn("ContractSetupPK").getFullyQualifiedColumnName();
         String segmentFKCol = CONTRACT_SETUP_DBTABLE.getDBColumn("SegmentFK").getFullyQualifiedColumnName();

         String sql = " SELECT DISTINCT " + EDITTRX_HISTORY_TABLENAME + ".* FROM " + EDITTRX_HISTORY_TABLENAME + ", " +
                      EDITTRX_TABLENAME + ", " + CLIENT_SETUP_TABLENAME + ", " + CONTRACT_SETUP_TABLENAME + ", " + transactionPriorityTable +
                      " WHERE " + segmentFKCol + " = " + segmentPK +
                      " AND " + contractSetupFKCol + " = " + contractSetupPKCol +
                      " AND " + clientSetupFKCol + " = " + clientSetupPKCol +
                      " AND " + statusCol + "IN ('N', 'A')" +
                      " AND " + editTrxFKCol + " = " + editTrxPKCol +
                      " AND ((" + effectiveDateCol + " > ?" +
                      " AND " + effectiveDateCol + " < ?)" +
                      "  OR (" + effectiveDateCol + " = ?" +
                      " AND (" + trxPriorityTransactionTypeCTCol + " = " + editTrxTransactionTypeCTCol +
                      " AND " + priorityCol + " <= (SELECT " + priorityCol + " FROM " + transactionPriorityTable +
                      " WHERE " + trxPriorityTransactionTypeCTCol + " = '" + transactionType + "')))" +
                      " OR (" + effectiveDateCol + " = ?" +
                      " AND (" + trxPriorityTransactionTypeCTCol + " = " + editTrxTransactionTypeCTCol +
                      " AND " + priorityCol + " >= (SELECT " + priorityCol + " FROM " + transactionPriorityTable +
                      " WHERE " + trxPriorityTransactionTypeCTCol + " = '" + transactionType + "'))))" +
                      " AND " + pendingStatusCol + " = 'H'" +
                      " ORDER BY " + editTrxHistoryPKCol + " ASC";

         Connection conn = null;

         PreparedStatement ps = null;

         try
         {
             conn = ConnectionFactory.getSingleton().getConnection(POOLNAME);

             ps = conn.prepareStatement(sql);

             ps.setDate(1, DBUtil.convertStringToDate(fromDate));
             ps.setDate(2, DBUtil.convertStringToDate(toDate));
             ps.setDate(3, DBUtil.convertStringToDate(toDate));
             ps.setDate(4, DBUtil.convertStringToDate(toDate));

             editTrxHistoryVOs = (EDITTrxHistoryVO[]) executeQuery(EDITTrxHistoryVO.class,
                                                                   ps,
                                                                   POOLNAME,
                                                                   false,
                                                                   null);
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

                 e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.

                 throw new RuntimeException(e);
             }
         }

         return editTrxHistoryVOs;
     }

}
