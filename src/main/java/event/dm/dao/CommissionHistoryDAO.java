/*
 * User: gfrosti
 * Date: Jun 25, 2003
 * Time: 10:41:47 AM
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package event.dm.dao;

import edit.common.*;
import edit.common.vo.CommissionHistoryVO;
import edit.services.db.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import event.EDITTrx;


public class CommissionHistoryDAO extends DAO
{
    private final String POOLNAME;
    private final DBTable DBTABLE;
    private final String TABLENAME;


    public CommissionHistoryDAO()
    {
        POOLNAME  = ConnectionFactory.EDITSOLUTIONS_POOL;
        DBTABLE   = DBTable.getDBTableForTable("CommissionHistory");
        TABLENAME = DBTABLE.getFullyQualifiedTableName();
    }

    public CommissionHistoryVO[] findByEDITTrxHistoryPK(long editTrxHistoryPK) throws Exception
    {
        String editTrxHistoryFKCol= DBTABLE.getDBColumn("EDITTrxHistoryFK").getFullyQualifiedColumnName();

        String sql = "SELECT * FROM " + TABLENAME +
                     " WHERE " + editTrxHistoryFKCol + " = " + editTrxHistoryPK;

        return (CommissionHistoryVO[]) executeQuery(CommissionHistoryVO.class,
                                                     sql,
                                                      POOLNAME,
                                                       false,
                                                         null);
    }

    public CommissionHistoryVO[] findByProcessDateGT_AND_PlacedAgentPK(String processDate, long placedAgentPK) throws Exception
    {
        DBTable editTrxHistoryDBTable = DBTable.getDBTableForTable("EDITTrxHistory");

        String editTrxHistoryTable = editTrxHistoryDBTable.getFullyQualifiedTableName();

        String editTrxHistoryFKCol = DBTABLE.getDBColumn("EDITTrxHistoryFK").getFullyQualifiedColumnName();
        String placedAgentFKCol    = DBTABLE.getDBColumn("PlacedAgentFK").getFullyQualifiedColumnName();

        String editTrxHistoryPKCol = editTrxHistoryDBTable.getDBColumn("EDITTrxHistoryPK").getFullyQualifiedColumnName();
        String processDateCol      = editTrxHistoryDBTable.getDBColumn("ProcessDateTime").getFullyQualifiedColumnName();

        String sql = "SELECT " + TABLENAME + ".* FROM " + TABLENAME + ", " + editTrxHistoryTable +
                     " WHERE " + editTrxHistoryFKCol + " = " + editTrxHistoryPKCol +
                     " AND " + placedAgentFKCol + " = ?" +
                     " AND " + processDateCol + " > ?";

        CommissionHistoryVO[] commissionHistoryVOs = null;
        Connection conn = null;

        PreparedStatement ps = null;

        try
        {
            EDITDateTime processDateTime = new EDITDateTime(new EDITDate(processDate), EDITDateTime.DEFAULT_MIN_TIME);

            conn = ConnectionFactory.getSingleton().getConnection(POOLNAME);

            ps = conn.prepareStatement(sql);

            ps.setLong(1, placedAgentPK);
            ps.setTimestamp(2, DBUtil.convertStringToTimestamp(processDateTime.getFormattedDateTime()));

            commissionHistoryVOs = (CommissionHistoryVO[]) executeQuery(CommissionHistoryVO.class,
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

        return commissionHistoryVOs;
    }

    public CommissionHistoryVO[] findByUpdateDateTime_AND_PlacedAgentPK_AND_StatementInd(long placedAgentPK, String statementInd) throws Exception
    {
        // CommissionHistory
        String placedAgentFKCol  = DBTABLE.getDBColumn("PlacedAgentFK").getFullyQualifiedColumnName();
        String updateDateTimeCol = DBTABLE.getDBColumn("UpdateDateTime").getFullyQualifiedColumnName();
        String statementIndCol   = DBTABLE.getDBColumn("StatementInd").getFullyQualifiedColumnName();
        String updateStatusCol   = DBTABLE.getDBColumn("UpdateStatus").getFullyQualifiedColumnName();

        // PlacedAgent
        DBTable placedAgentDBTable = DBTable.getDBTableForTable("PlacedAgent");
        String placedAgentTable = placedAgentDBTable.getFullyQualifiedTableName();
        String placedAgentPKCol = placedAgentDBTable.getDBColumn("PlacedAgentPK").getFullyQualifiedColumnName();
        String clientRoleFKCol1 = placedAgentDBTable.getDBColumn("ClientRoleFK").getFullyQualifiedColumnName();

        // ClientRole
        DBTable clientRoleDBTable = DBTable.getDBTableForTable("ClientRole");
        String clientRoleTable = clientRoleDBTable.getFullyQualifiedTableName();
        String clientRolePKCol = clientRoleDBTable.getDBColumn("ClientRolePK").getFullyQualifiedColumnName();

        // ClientRoleFinancial
        DBTable clientRoleFinancialDBTable = DBTable.getDBTableForTable("ClientRoleFinancial");
        String clientRoleFinancialTable = clientRoleFinancialDBTable.getFullyQualifiedTableName();
        String clientRoleFKCol2 = clientRoleFinancialDBTable.getDBColumn("ClientRoleFK").getFullyQualifiedColumnName();
        String lastStatementDateTimeCol = clientRoleFinancialDBTable.getDBColumn("LastStatementDateTime").getFullyQualifiedColumnName();

        String sql;
        sql =   " SELECT " + TABLENAME + ".* " +
                " FROM " + TABLENAME +
                " INNER JOIN " + placedAgentTable +
                " ON " + placedAgentFKCol + " = " + placedAgentPKCol +
                " INNER JOIN " + clientRoleTable +
                " ON " + clientRoleFKCol1 + " = " + clientRolePKCol +
                " INNER JOIN " + clientRoleFinancialTable +
                " ON " + clientRolePKCol + " = " + clientRoleFKCol2 +
                " WHERE " + placedAgentFKCol + " = " + placedAgentPK +
                " AND (" + updateDateTimeCol + " > " + lastStatementDateTimeCol + " OR " + lastStatementDateTimeCol + " IS NULL)" +
                " AND " + statementIndCol + " = '" + statementInd + "'" +
                " AND " + updateStatusCol + " IN ('H', 'L')";

        return (CommissionHistoryVO[]) executeQuery(CommissionHistoryVO.class,
                                                     sql,
                                                      POOLNAME,
                                                       false,
                                                         null);
    }

    public CommissionHistoryVO[] findByUpdateDateTimeGTPlacedAgentPKAndTrxType(String updateDateTime,
                                                                                long placedAgentPK) throws Exception
    {
        DBTable editTrxHistoryDBTable    = DBTable.getDBTableForTable("EDITTrxHistory");
        DBTable editTrxDBTable           = DBTable.getDBTableForTable("EDITTrx");

        String editTrxHistoryTable      = editTrxHistoryDBTable.getFullyQualifiedTableName();
        String editTrxTable             = editTrxDBTable.getFullyQualifiedTableName();

        String transactionTypeCTCol     = editTrxDBTable.getDBColumn("TransactionTypeCT").getFullyQualifiedColumnName();
        String editTrxPKCol             = editTrxDBTable.getDBColumn("EDITTrxPK").getFullyQualifiedColumnName();

        String editTrxFKCol             = editTrxHistoryDBTable.getDBColumn("EDITTrxFK").getFullyQualifiedColumnName();
        String editTrxHistoryPKCol      = editTrxHistoryDBTable.getDBColumn("EDITTrxHistoryPK").getFullyQualifiedColumnName();

        String editTrxHistoryFKCol      = DBTABLE.getDBColumn("EDITTrxHistoryFK").getFullyQualifiedColumnName();
        String placedAgentFKCol         = DBTABLE.getDBColumn("PlacedAgentFK").getFullyQualifiedColumnName();
        String updateDateTimeCol        = DBTABLE.getDBColumn("UpdateDateTime").getFullyQualifiedColumnName();

        String sql = "SELECT " + TABLENAME + ".* FROM " + TABLENAME + ", " + editTrxTable + ", " + editTrxHistoryTable +
                     " WHERE " + transactionTypeCTCol + " = 'CK'" +
                     " AND " + editTrxFKCol + " = " + editTrxPKCol +
                     " AND " + editTrxHistoryFKCol + " = " + editTrxHistoryPKCol +
                     " AND " + placedAgentFKCol + " = ?" +
                     " AND " + updateDateTimeCol + " > ?";

        CommissionHistoryVO[] commissionHistoryVOs = null;
        Connection conn = null;

        PreparedStatement ps = null;

        try
        {
            conn = ConnectionFactory.getSingleton().getConnection(POOLNAME);

            ps = conn.prepareStatement(sql);

            ps.setLong(1, placedAgentPK);
            ps.setTimestamp(2, DBUtil.convertStringToTimestamp(updateDateTime));

            commissionHistoryVOs = (CommissionHistoryVO[]) executeQuery(CommissionHistoryVO.class,
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

        return commissionHistoryVOs;
    }

    public CommissionHistoryVO[] findByUpdateDateTimeGTPlacedAgentPKAndStatus(String updateDateTime,
                                                                               long[] placedAgentFKs,
                                                                                String[] statuses) throws Exception
    {
        CommissionHistoryVO[] commissionHistoryVOs = null;

        String placedAgentFKCol  = DBTABLE.getDBColumn("PlacedAgentFK").getFullyQualifiedColumnName();
        String updateDateTimeCol = DBTABLE.getDBColumn("UpdateDateTime").getFullyQualifiedColumnName();
        String updateStatusCol   = DBTABLE.getDBColumn( "UpdateStatus").getFullyQualifiedColumnName();
        String commissionTypeCTCol = DBTABLE.getDBColumn("CommissionTypeCT").getFullyQualifiedColumnName();
        String includedInDebitBalIndCol = DBTABLE.getDBColumn("IncludedInDebitBalInd").getFullyQualifiedColumnName();

        String sqlIn = "";
        for (int i = 0; i < placedAgentFKs.length; i++)
        {
            if (i == placedAgentFKs.length - 1)
            {
                sqlIn = sqlIn + placedAgentFKs[i] + ")";
            }
            else
            {
                sqlIn = sqlIn + placedAgentFKs[i] + ", ";
            }
        }

        String sql = "SELECT * FROM " + TABLENAME +
                     " WHERE " + placedAgentFKCol + " IN (" + sqlIn +
                     " AND " + updateDateTimeCol + " > ?" +
                     " AND NOT " + commissionTypeCTCol + " = 'Check'" +
                     " AND " + includedInDebitBalIndCol + "IS NULL" +
                     " AND " + updateStatusCol + " IN (";

        int lastStatus = statuses.length - 1;
        for (int s = 0; s < statuses.length; s++)
        {
            if (s == lastStatus)
            {
                sql = sql + "'" + statuses[s] + "')";
            }
            else
            {
                sql = sql + "'" + statuses[s] + "', ";
            }
        }

        Connection conn = null;

        PreparedStatement ps = null;

        try
        {
            conn = ConnectionFactory.getSingleton().getConnection(POOLNAME);

            ps = conn.prepareStatement(sql);

            ps.setTimestamp(1, DBUtil.convertStringToTimestamp(updateDateTime));

            commissionHistoryVOs = (CommissionHistoryVO[]) executeQuery(CommissionHistoryVO.class,
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

        return commissionHistoryVOs;
    }

    public CommissionHistoryVO[] findByUpdateDateTimeGTPlacedAgentPKBonusAmtAndStatus(String updateDateTime,
                                                                                       long[] placedAgentFKs,
                                                                                        String[] statuses)
                                                                                     throws Exception
    {
        String placedAgentFKCol         = DBTABLE.getDBColumn("PlacedAgentFK").getFullyQualifiedColumnName();
        String updateDateTimeCol        = DBTABLE.getDBColumn("UpdateDateTime").getFullyQualifiedColumnName();
        String updateStatusCol          = DBTABLE.getDBColumn("UpdateStatus").getFullyQualifiedColumnName();
        String bonusCommissionAmountCol = DBTABLE.getDBColumn("BonusCommissionAmount").getFullyQualifiedColumnName();

        String sqlIn = "";

        for (int i = 0; i < placedAgentFKs.length; i++)
        {
            if (i == placedAgentFKs.length - 1)
            {
                sqlIn = sqlIn + placedAgentFKs[i] + ")";
            }
            else
            {
                sqlIn = sqlIn + placedAgentFKs[i] + ", ";
            }
        }

        String sql = "SELECT * FROM " + TABLENAME +
                     " WHERE " + placedAgentFKCol + " IN (" + sqlIn +
                     " AND " + updateDateTimeCol + " > ?" +
                     " AND " + bonusCommissionAmountCol + " > 0 " +
                     " AND " + updateStatusCol + " IN (";

        int lastStatus = statuses.length - 1;

        for (int s = 0; s < statuses.length; s++)
        {
            if (s == lastStatus)
            {
                sql = sql +"'"+statuses[s]+"')";
            }
            else
            {
                sql = sql +"'"+statuses[s]+"', ";
            }
        }

        CommissionHistoryVO[] commissionHistoryVOs = null;
        Connection conn = null;

        PreparedStatement ps = null;

        try
        {
            conn = ConnectionFactory.getSingleton().getConnection(POOLNAME);

            ps = conn.prepareStatement(sql);

            ps.setTimestamp(1, DBUtil.convertStringToTimestamp(updateDateTime));

            commissionHistoryVOs = (CommissionHistoryVO[]) executeQuery(CommissionHistoryVO.class,
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

        return commissionHistoryVOs;
    }

    public CommissionHistoryVO[] findByUpdateDateTimeLTPlacedAgentPK(long[] placedAgentFKs,
                                                                      String updateDateTime) throws Exception
    {
        String placedAgentFKCol  = DBTABLE.getDBColumn("PlacedAgentFK").getFullyQualifiedColumnName();
        String updateDateTimeCol = DBTABLE.getDBColumn("UpdateDateTime").getFullyQualifiedColumnName();

        String sqlIn = "";
        for (int i = 0; i < placedAgentFKs.length; i++)
        {
            if (i == placedAgentFKs.length - 1)
            {
               sqlIn = sqlIn + placedAgentFKs[i] + ")";
            }
            else
            {
               sqlIn = sqlIn + placedAgentFKs[i] + ", ";
            }
        }

        String sql = "SELECT * FROM " + TABLENAME +
                     " WHERE " + placedAgentFKCol + " IN (" + sqlIn +
                     " AND " + updateDateTimeCol + " <= ?";

        CommissionHistoryVO[] commissionHistoryVOs = null;
        Connection conn = null;

        PreparedStatement ps = null;

        try
        {
            conn = ConnectionFactory.getSingleton().getConnection(POOLNAME);

            ps = conn.prepareStatement(sql);

            ps.setTimestamp(1, DBUtil.convertStringToTimestamp(updateDateTime));

            commissionHistoryVOs = (CommissionHistoryVO[]) executeQuery(CommissionHistoryVO.class,
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

        return commissionHistoryVOs;
    }

    public CommissionHistoryVO[] findByUpdateDateTimeGTLTPlacedAgentPK(long[] placedAgentFKs,
                                                                        String currentUpdateDateTime,
                                                                         String priorUpdateDateTime) throws Exception
    {
        CommissionHistoryVO[] commissionHistoryVOs = null;

        String placedAgentFKCol    = DBTABLE.getDBColumn("PlacedAgentFK").getFullyQualifiedColumnName();
        String updateDateTimeCol   = DBTABLE.getDBColumn("UpdateDateTime").getFullyQualifiedColumnName();
        String commissionTypeCTCol = DBTABLE.getDBColumn("CommissionTypeCT").getFullyQualifiedColumnName();

        String sqlIn = "";
        for (int i = 0; i < placedAgentFKs.length; i++)
        {
            if (i == placedAgentFKs.length - 1)
            {
                sqlIn = sqlIn + placedAgentFKs[i] + ")";
            }
            else
            {
                sqlIn = sqlIn + placedAgentFKs[i] + ", ";
            }
        }

        String sql = " SELECT * FROM " + TABLENAME +
                     " WHERE " + placedAgentFKCol + "IN (" + sqlIn +
                     " AND (" + updateDateTimeCol + " > ?" +
                     " AND " + updateDateTimeCol + " <= ?)" +
                     " AND NOT " + commissionTypeCTCol + " = 'Check'";       

        Connection conn = null;

        PreparedStatement ps = null;

        try
        {
            conn = ConnectionFactory.getSingleton().getConnection(POOLNAME);

            ps = conn.prepareStatement(sql);

            ps.setTimestamp(1, DBUtil.convertStringToTimestamp(priorUpdateDateTime));
            ps.setTimestamp(2, DBUtil.convertStringToTimestamp(currentUpdateDateTime));

            commissionHistoryVOs = (CommissionHistoryVO[]) executeQuery(CommissionHistoryVO.class,
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

        return commissionHistoryVOs;
    }

    public CommissionHistoryVO[] findByUpdateDateTimeGTELTEPlacedAgentPK(long[] placedAgentFKs,
                                                                         String toDate,
                                                                         String fromDate) throws Exception
    {
        CommissionHistoryVO[] commissionHistoryVOs = null;

        String placedAgentFKCol    = DBTABLE.getDBColumn("PlacedAgentFK").getFullyQualifiedColumnName();
        String updateDateTimeCol   = DBTABLE.getDBColumn("UpdateDateTime").getFullyQualifiedColumnName();
        String commissionTypeCTCol = DBTABLE.getDBColumn("CommissionTypeCT").getFullyQualifiedColumnName();

        String sqlIn = "";
        for (int i = 0; i < placedAgentFKs.length; i++)
        {
            if (i == placedAgentFKs.length - 1)
            {
                sqlIn = sqlIn + placedAgentFKs[i] + ")";
            }
            else
            {
                sqlIn = sqlIn + placedAgentFKs[i] + ", ";
            }
        }

        String sql = " SELECT * FROM " + TABLENAME +
                     " WHERE " + placedAgentFKCol + "IN (" + sqlIn +
                     " AND (" + updateDateTimeCol + " >= ?" +
                     " AND " + updateDateTimeCol + " <= ?)" +
                     " AND NOT " + commissionTypeCTCol + " = 'Check'";

        Connection conn = null;

        PreparedStatement ps = null;

        try
        {
            conn = ConnectionFactory.getSingleton().getConnection(POOLNAME);

            ps = conn.prepareStatement(sql);

            ps.setTimestamp(1, DBUtil.convertStringToTimestamp(fromDate + " " + EDITDateTime.DEFAULT_MIN_TIME));
            ps.setTimestamp(2, DBUtil.convertStringToTimestamp(toDate + " " + EDITDateTime.DEFAULT_MAX_TIME));

            commissionHistoryVOs = (CommissionHistoryVO[]) executeQuery(CommissionHistoryVO.class,
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

        return commissionHistoryVOs;
    }

    public CommissionHistoryVO[] findByPlacedAgentPKExcludingUpdateStatus(long[] placedAgentFKs,
                                                                           String[] updateStatuses) throws Exception
    {
        String placedAgentFKCol = DBTABLE.getDBColumn("PlacedAgentFK").getFullyQualifiedColumnName();
        String updateStatusCol  = DBTABLE.getDBColumn("UpdateStatus").getFullyQualifiedColumnName();

        String sqlIn = "";
        for (int i = 0; i < placedAgentFKs.length; i++)
        {
            if (i == placedAgentFKs.length - 1)
            {
                sqlIn = sqlIn + placedAgentFKs[i] + ")";
            }
            else
            {
                sqlIn = sqlIn + placedAgentFKs[i] + ", ";
            }
        }

        String sql = "SELECT * FROM " + TABLENAME +
                     " WHERE " + placedAgentFKCol + "IN (" + sqlIn +
                     " AND NOT " + updateStatusCol + " IN (";
        int lastStatus = updateStatuses.length - 1;
        for (int s = 0; s < updateStatuses.length; s++)
        {
            if (s == lastStatus)
            {
                sql = sql + "'" + updateStatuses[s] + "')";
            }
            else
            {
                sql = sql + "'" +  updateStatuses[s] + "',";
            }
        }

        return (CommissionHistoryVO[]) executeQuery(CommissionHistoryVO.class,
                                                     sql,
                                                      POOLNAME,
                                                       false,
                                                         null);
    }

    /**
     * Find CommisionHistory by placed agent, trans type, dates and policy number.
     * The policy number lookup is case-insensitive.
     * @param placedAgentFKs
     * @param transactionType
     * @param fromDate
     * @param toDate
     * @param contractNumber
     * @return
     */
    public CommissionHistoryVO[] findByPlacedAgentTransTypeDatesAndPolicy(long[] placedAgentFKs,
                        String transactionType,
                        EDITDate fromDate,
                        EDITDate toDate,
                        String contractNumber)
    {

        // if the contract number is used, we create extra FROM tables and
        // extra WHERE clause
        String contractExtraFromList = "";
        String contractEextraWhereClause = "";
        if (contractNumber != null)
        {
            String[] fromAndWhereExtras = makeFilterPolicyTablesAndClause(contractNumber);
            contractExtraFromList = fromAndWhereExtras[0];
            contractEextraWhereClause = fromAndWhereExtras[1];
        }

        DBTable editTrxHistoryDBTable    = DBTable.getDBTableForTable("EDITTrxHistory");
        DBTable editTrxDBTable           = DBTable.getDBTableForTable("EDITTrx");

        String editTrxHistoryTable    = editTrxHistoryDBTable.getFullyQualifiedTableName();
        String editTrxTable           = editTrxDBTable.getFullyQualifiedTableName();

        String placedAgentFKCol     = DBTABLE.getDBColumn("PlacedAgentFK").getFullyQualifiedColumnName();

        String editTrxHistoryFKCol  = DBTABLE.getDBColumn("EDITTrxHistoryFK").getFullyQualifiedColumnName();
        String updateStatusCol      = DBTABLE.getDBColumn("UpdateStatus").getFullyQualifiedColumnName();

        String editTrxHistoryPKCol  = editTrxHistoryDBTable.getDBColumn("EDITTrxHistoryPK").getFullyQualifiedColumnName();
        String editTrxFKCol         = editTrxHistoryDBTable.getDBColumn("EDITTrxFK").getFullyQualifiedColumnName();
        String processDateCol       = editTrxHistoryDBTable.getDBColumn("ProcessDateTime").getFullyQualifiedColumnName();

        String editTrxPKCol         = editTrxDBTable.getDBColumn("EDITTrxPK").getFullyQualifiedColumnName();
        String transactionTypeCTCol = editTrxDBTable.getDBColumn("TransactionTypeCT").getFullyQualifiedColumnName();

        String sqlIn = "";
        for (int i = 0; i < placedAgentFKs.length; i++)
        {
            if (i == placedAgentFKs.length - 1)
            {
                sqlIn = sqlIn + placedAgentFKs[i] + ")";
            }
            else
            {
                sqlIn = sqlIn + placedAgentFKs[i] + ", ";
            }
        }

        String sql = "SELECT DISTINCT " +
                TABLENAME + ".* FROM " +
                TABLENAME + ", " +
                editTrxTable + ", " +
                editTrxHistoryTable +
                contractExtraFromList +     // may have nothing or more tables
                " WHERE " + placedAgentFKCol + " IN (" + sqlIn +
                " AND " + editTrxHistoryFKCol + " = " + editTrxHistoryPKCol +
                " AND " + editTrxFKCol + " = " + editTrxPKCol +
                contractEextraWhereClause;   // may have nothing or more conditions


        if (!transactionType.equals(""))
        {
            sql = sql + " AND " + transactionTypeCTCol + " = '" + transactionType + "'";
        }

        // need to change the date because the SQL datetime needs a legal date after 1753/01/01
        if (fromDate == null)
        {
            fromDate = new EDITDate(EDITDate.DEFAULT_MIN_DATE);
        }

        fromDate = fromDate.subtractDays(1);

        toDate = toDate.addDays(1);

        sql = sql + " AND " + processDateCol + " > ?" +
                    " AND " + processDateCol + " < ?" +
                    " AND NOT " + updateStatusCol + "= 'U'";

        CommissionHistoryVO[] commissionHistoryVOs = null;
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

            commissionHistoryVOs = (CommissionHistoryVO[]) executeQuery(CommissionHistoryVO.class,
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

        return commissionHistoryVOs;
    }

    /**
     * Used by CommissionHistoryDAO.findByPlacedAgentTransTypeDatesAndPolicy
     * @param contractNumber
     * @return String[] with extra table names for FROM
     * clause and extra string for WHERE clause
     */
    private String[] makeFilterPolicyTablesAndClause(String contractNumber)
    {

        DBTable segmentDBTable    = DBTable.getDBTableForTable("Segment");
        DBTable contractSetupDBTable = DBTable.getDBTableForTable("ContractSetup");
        DBTable clientSetupDBTable = DBTable.getDBTableForTable("ClientSetup");
        DBTable editTrxDBTable = DBTable.getDBTableForTable("EDITTrx");

        String segmentTable =  segmentDBTable.getFullyQualifiedTableName();
        String contractSetupTable = contractSetupDBTable.getFullyQualifiedTableName();
        String clientSetupTable = clientSetupDBTable.getFullyQualifiedTableName();
        String editTrxTable = editTrxDBTable.getFullyQualifiedTableName();

        String segmentContractNumberCol = segmentDBTable.getDBColumn("ContractNumber").getFullyQualifiedColumnName();
        String contractSetupSegmentFKCol = contractSetupDBTable.getDBColumn("SegmentFK").getFullyQualifiedColumnName();
        String segmentPKCol = segmentDBTable.getDBColumn("SegmentPK").getFullyQualifiedColumnName();
        String clientSetupContractSetupFKCol = clientSetupDBTable.getDBColumn("ContractSetupFK").getFullyQualifiedColumnName();
        String contractSetupPKCol = contractSetupDBTable.getDBColumn("ContractSetupPK").getFullyQualifiedColumnName();
        String editTrxClientSetupFKCol = editTrxDBTable.getDBColumn("ClientSetupFK").getFullyQualifiedColumnName();
        String clientSetupPKCol = clientSetupDBTable.getDBColumn("ClientSetupPK").getFullyQualifiedColumnName();

        // make part of the FROM clause
        String extraTableNames = ", " + segmentTable + "," + contractSetupTable + "," + clientSetupTable;

        contractNumber = contractNumber.toUpperCase();

        // make part of the WHERE CLAUSE
        String sqlClause = " AND UPPER(" + segmentContractNumberCol + ") = '" + contractNumber + "'" +
                " AND " + contractSetupSegmentFKCol + " = " + segmentPKCol +
                " AND " + clientSetupContractSetupFKCol + " = " + contractSetupPKCol +
                " AND " + editTrxClientSetupFKCol + " = " + clientSetupPKCol + " ";


        return new String[] {extraTableNames, sqlClause};
    }


    public CommissionHistoryVO[] findByPlacedAgentPKAndCommissionType(long[] placedAgentFKs,
                                                                       String commissionType) throws Exception
    {
        String placedAgentFKCol    = DBTABLE.getDBColumn("PlacedAgentFK").getFullyQualifiedColumnName();
        String commissionTypeCTCol = DBTABLE.getDBColumn("CommissionTypeCT").getFullyQualifiedColumnName();
        String updateStatusCol     = DBTABLE.getDBColumn("UpdateStatus").getFullyQualifiedColumnName();
        String updateDateTimeCol   = DBTABLE.getDBColumn("UpdateDateTime").getFullyQualifiedColumnName();

        String sqlIn = "";
        for (int i = 0; i < placedAgentFKs.length; i++)
        {
            if (i == placedAgentFKs.length - 1)
            {
                 sqlIn = sqlIn + placedAgentFKs[i] + ")";
            }
            else
            {
                sqlIn = sqlIn + placedAgentFKs[i] + ", ";
            }
        }

        String sql = "SELECT * FROM " + TABLENAME +
                     " WHERE " + placedAgentFKCol + " IN (" + sqlIn +
                     " AND " + commissionTypeCTCol + " = '" + commissionType + "'" +
                     " AND " + updateStatusCol + " IN('H','B')" +
                     " ORDER BY " + updateDateTimeCol + " DESC";

        return (CommissionHistoryVO[]) executeQuery(CommissionHistoryVO.class,
                                                     sql,
                                                      POOLNAME,
                                                       false,
                                                         null);
    }

    public CommissionHistoryVO[] findByPK(long commissionHistoryPK)
    {
        String commissionHistoryPKCol = DBTABLE.getDBColumn("CommissionHistoryPK").getFullyQualifiedColumnName();

        String sql = "SELECT * FROM " + TABLENAME +
                     " WHERE " + commissionHistoryPKCol + " = " + commissionHistoryPK;

        return (CommissionHistoryVO[]) executeQuery(CommissionHistoryVO.class,
                                                     sql,
                                                      POOLNAME,
                                                       false,
                                                         null);
    }

    public CommissionHistoryVO[] findByUpdateStatus(String updateStatus) throws Exception
    {
        String updateStatusCol = DBTABLE.getDBColumn("UpdateStatus").getFullyQualifiedColumnName();

        String sql = "SELECT * FROM " + TABLENAME +
                     " WHERE " + updateStatusCol + " ='" + updateStatus + "'";

        return (CommissionHistoryVO[]) executeQuery(CommissionHistoryVO.class,
                                                     sql,
                                                      POOLNAME,
                                                       false,
                                                         null);
    }

    public CommissionHistoryVO[] findByPlacedAgentPKAndBonusUpdateStatus(long placedAgentPK, String bonusUpdateStatus)
    {
        String bonusUpdateStatusCol  = DBTABLE.getDBColumn("BonusUpdateStatus").getFullyQualifiedColumnName();
        String placedAgentFKCol = DBTABLE.getDBColumn("PlacedAgentFK").getFullyQualifiedColumnName();

        String sql = "SELECT * FROM " + TABLENAME +
                     " WHERE " + bonusUpdateStatusCol + " = '" + bonusUpdateStatus + "'" +
                     " AND " + placedAgentFKCol + " = " + placedAgentPK;

        return (CommissionHistoryVO[]) executeQuery(CommissionHistoryVO.class,
                                                     sql,
                                                      POOLNAME,
                                                       false,
                                                         null);
    }

    /**
     * Finder.
     * @param placedAgentPK
     * @param updateStatuses
     * @return
     */
    public CommissionHistoryVO[] findByPlacedAgentPKAndUpdateStatus(long placedAgentPK, String[] updateStatuses)
    {
        String updateStatusCol  = DBTABLE.getDBColumn("UpdateStatus").getFullyQualifiedColumnName();
        String placedAgentFKCol = DBTABLE.getDBColumn("PlacedAgentFK").getFullyQualifiedColumnName();

        String sqlIn = "";
        for (int i = 0; i < updateStatuses.length; i++)
        {
            if (i == updateStatuses.length - 1)
            {
                 sqlIn = sqlIn + "'" + updateStatuses[i] + "'";
            }
            else
            {
                sqlIn = sqlIn + "'" + updateStatuses[i] + "', ";
            }
        }

        String sql = " SELECT * FROM " + TABLENAME +
                     " WHERE " + updateStatusCol + " IN (" + sqlIn + ")" +
                     " AND " + placedAgentFKCol + " = " + placedAgentPK;

        return (CommissionHistoryVO[]) executeQuery(CommissionHistoryVO.class,
                                                     sql,
                                                      POOLNAME,
                                                       false,
                                                         null);
    }

    public CommissionHistoryVO[] findByPlacedAgentPK(long placedAgentPK) throws Exception
    {
        String placedAgentFKCol = DBTABLE.getDBColumn("PlacedAgentFK").getFullyQualifiedColumnName();

        String sql = "SELECT * FROM " + TABLENAME +
                     " WHERE " + placedAgentFKCol + " = " + placedAgentPK;

        return (CommissionHistoryVO[]) executeQuery(CommissionHistoryVO.class,
                                                     sql,
                                                      POOLNAME,
                                                       false,
                                                         null);
    }

    public CommissionHistoryVO[] findByAgentSnapshotPK_SegmentFK_TrxTypeCTs(long agentSnapshotPK, long segmentFK, String[] trxTypeCTs) throws Exception
    {
      DBTable agentHierarchyDBTable = DBTable.getDBTableForTable("AgentHierarchy");
      String agentHierarchyTable = agentHierarchyDBTable.getFullyQualifiedTableName();
      String agentHierarchyPKCol = agentHierarchyDBTable.getDBColumn("AgentHierarchyPK").getFullyQualifiedColumnName();
      String segmentFKCol = agentHierarchyDBTable.getDBColumn("SegmentFK").getFullyQualifiedColumnName(); 
      
      DBTable agentSnapshotDBTable = DBTable.getDBTableForTable("AgentSnapshot");
      String agentSnapshotTable = agentSnapshotDBTable.getFullyQualifiedTableName();
      String agentSnapshotPKCol = agentSnapshotDBTable.getDBColumn("AgentSnapshotPK").getFullyQualifiedColumnName();
      String agentHierarchyFKCol = agentSnapshotDBTable.getDBColumn("AgentHierarchyFK").getFullyQualifiedColumnName();
      
      String editTrxHistoryFKCol = DBTABLE.getDBColumn("EDITTrxHistoryFK").getFullyQualifiedColumnName();
      String agentSnapshotFKCol = DBTABLE.getDBColumn("AgentSnapshotFK").getFullyQualifiedColumnName();

      DBTable editTrxHistoryDBTable = DBTable.getDBTableForTable("EDITTrxHistory");
      String editTrxHistoryTable = editTrxHistoryDBTable.getFullyQualifiedTableName();
      String editTrxFKCol = editTrxHistoryDBTable.getDBColumn("EDITTrxFK").getFullyQualifiedColumnName();
      String editTrxHistoryPKCol = editTrxHistoryDBTable.getDBColumn("EDITTrxHistoryPK").getFullyQualifiedColumnName();
      
      DBTable editTrxDBTable = DBTable.getDBTableForTable("EDITTrx");
      String editTrxTable = editTrxDBTable.getFullyQualifiedTableName();
      String transactionTypeCTCol = editTrxDBTable.getDBColumn("TransactionTypeCT").getFullyQualifiedColumnName();
      String editTrxPKCol = editTrxDBTable.getDBColumn("EDITTrxPK").getFullyQualifiedColumnName();      

      String sql = " SELECT " + TABLENAME + ".*" +
                  " FROM " + agentHierarchyTable +
                  
                  " INNER JOIN " + agentSnapshotTable + 
                  " ON " + agentHierarchyPKCol + " = " + agentHierarchyFKCol +
                  
                  " INNER JOIN " + TABLENAME +
                  " ON " + agentSnapshotPKCol + " = " + agentSnapshotFKCol + 
                  
                  " INNER JOIN " + editTrxHistoryTable +
                  " ON " + editTrxHistoryFKCol + " = " + editTrxHistoryPKCol +
                  
                  " INNER JOIN " + editTrxTable +
                  " ON " + editTrxFKCol + " = " + editTrxPKCol +
                  
                  " WHERE " + segmentFKCol + " = " + segmentFK +
                
                  " AND " + agentSnapshotPKCol + " = " + agentSnapshotPK +
                  
                  " AND " + transactionTypeCTCol + " IN (";
                    
                    for (int i = 0; i < trxTypeCTs.length; i++)
                    {
                      sql += "'" + trxTypeCTs[i] + "'";
                      
                      if (i < (trxTypeCTs.length - 1))
                      {
                        sql += ", ";
                      }
                    }
                    
                    sql += ")";

        return (CommissionHistoryVO[]) executeQuery(CommissionHistoryVO.class,
                                                     sql,
                                                      POOLNAME,
                                                       false,
                                                         null);
    }

    public CommissionHistoryVO[] findCommissionHistoryVOByAccountPendingStatus_AND_ProcessDateLTE(String accountPendingStatus,
                                                                                                   String processDate)
                                                                                                 throws Exception
    {
        DBTable editTrxHistoryDBTable = DBTable.getDBTableForTable("EDITTrxHistory");

        String editTrxHistoryTable = editTrxHistoryDBTable.getFullyQualifiedTableName();

        String accountingPendingStatusCol = DBTABLE.getDBColumn("AccountingPendingStatus").getFullyQualifiedColumnName();
        String editTrxHistoryFKCol        = DBTABLE.getDBColumn("EDITTrxHistoryFK").getFullyQualifiedColumnName();

        String processDateCol             = editTrxHistoryDBTable.getDBColumn("ProcessDateTime").getFullyQualifiedColumnName();
        String editTrxHistoryPKCol        = editTrxHistoryDBTable.getDBColumn("EDITTrxHistoryPK").getFullyQualifiedColumnName();

        String sql = "SELECT * FROM " + TABLENAME +
                     " WHERE " + accountingPendingStatusCol + " = ?" +
                     " AND " + editTrxHistoryFKCol + " IN (SELECT " + editTrxHistoryPKCol + " FROM " + editTrxHistoryTable +
                     " WHERE " + processDateCol + " <= ?)";

        CommissionHistoryVO[] commissionHistoryVOs = null;
        Connection conn = null;

        PreparedStatement ps = null;

        try
        {
            EDITDateTime processDateTime = new EDITDateTime(new EDITDate(processDate), EDITDateTime.DEFAULT_MAX_TIME);

            conn = ConnectionFactory.getSingleton().getConnection(POOLNAME);

            ps = conn.prepareStatement(sql);

            ps.setString(1, accountPendingStatus);
            ps.setTimestamp(2, DBUtil.convertStringToTimestamp(processDateTime.getFormattedDateTime()));

            commissionHistoryVOs = (CommissionHistoryVO[]) executeQuery(CommissionHistoryVO.class,
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

        return commissionHistoryVOs;
    }

    public CommissionHistoryVO[] findByProcessDateCheckTrx(long placedAgentPK, String startingDate, String endingDate, String[] trxTypes) throws Exception
    {
        DBTable editTrxDBTable           = DBTable.getDBTableForTable("EDITTrx");
        DBTable editTrxHistoryDBTable    = DBTable.getDBTableForTable("EDITTrxHistory");

        String editTrxTable           = editTrxDBTable.getFullyQualifiedTableName();
        String editTrxHistoryTable    = editTrxHistoryDBTable.getFullyQualifiedTableName();

        String transactionTypeCTCol    = editTrxDBTable.getDBColumn("TransactionTypeCT").getFullyQualifiedColumnName();
        String editTrxPKCol            = editTrxDBTable.getDBColumn("EDITTrxPK").getFullyQualifiedColumnName();

        String editTrxHistoryFKCol     = DBTABLE.getDBColumn("EDITTrxHistoryFK").getFullyQualifiedColumnName();
        String placedAgentFKCol        = DBTABLE.getDBColumn("PlacedAgentFK").getFullyQualifiedColumnName();

        String editTrxFKCol            = editTrxHistoryDBTable.getDBColumn("EDITTrxFK").getFullyQualifiedColumnName();
        String editTrxHistoryPKCol     = editTrxHistoryDBTable.getDBColumn("EDITTrxHistoryPK").getFullyQualifiedColumnName();
        String processDateCol          = editTrxHistoryDBTable.getDBColumn("ProcessDateTime").getFullyQualifiedColumnName();

        String trxTypeList = "";

        for (int i = 0; i < trxTypes.length; i++)
        {
            String trxType = trxTypes[i];

            trxTypeList += "'" + trxType + "'";

            if (i != (trxTypes.length - 1))
            {
                trxTypeList += ", ";
            }
        }

        String sql = "SELECT " + TABLENAME + ".* FROM " + TABLENAME + ", " + editTrxTable + ", " + editTrxHistoryTable +
                     " WHERE " + transactionTypeCTCol + " IN (" + trxTypeList + ")" +
                     " AND " + editTrxFKCol + " = " + editTrxPKCol +
                     " AND " + editTrxHistoryFKCol + " = " + editTrxHistoryPKCol +
                     " AND " + placedAgentFKCol + " = ?" +
                     " AND " + processDateCol + " >= ?" +
                     " AND " + processDateCol + " <= ?";

        CommissionHistoryVO[] commissionHistoryVOs = null;
        Connection conn = null;

        PreparedStatement ps = null;

        try
        {
            EDITDateTime startingDateTime = new EDITDateTime(new EDITDate(startingDate), EDITDateTime.DEFAULT_MIN_TIME);
            EDITDateTime endingDateTime = new EDITDateTime(new EDITDate(endingDate), EDITDateTime.DEFAULT_MAX_TIME);

            conn = ConnectionFactory.getSingleton().getConnection(POOLNAME);

            ps = conn.prepareStatement(sql);

            ps.setLong(1, placedAgentPK);
            ps.setTimestamp(2, DBUtil.convertStringToTimestamp(startingDateTime.getFormattedDateTime()));
            ps.setTimestamp(3, DBUtil.convertStringToTimestamp(endingDateTime.getFormattedDateTime()));

            commissionHistoryVOs = (CommissionHistoryVO[]) executeQuery(CommissionHistoryVO.class,
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

        return commissionHistoryVOs;
    }

    /**
     * Retrieves CommissionHistories for given contract and update status.
     * @param segmentPK
     * @param updateStatus
     * @param crud
     * @return
     */
    // Note: This method is overloaded. If you need a version that does not need crud, please see other version.
    public CommissionHistoryVO[] findBySegmentPK_UpdateStatus(long segmentPK, String updateStatus, CRUD crud)
    {
        List commissionHistories = new ArrayList();

        //The crud passed in can be null
        boolean crudPassedInAsNull = false;
        if (crud == null)
        {
            crud = CRUDFactory.getSingleton().getCRUD(POOLNAME);
            crudPassedInAsNull = true;
        }

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        DBTable editTrxHistoryDBTable = DBTable.getDBTableForTable("EDITTrxHistory");
        DBTable editTrxDBTable = DBTable.getDBTableForTable("EDITTrx");
        DBTable clientSetupDBTable = DBTable.getDBTableForTable("ClientSetup");
        DBTable contractSetupDBTable = DBTable.getDBTableForTable("ContractSetup");

        String editTrxHistoryTable = editTrxHistoryDBTable.getFullyQualifiedTableName();
        String editTrxTable = editTrxDBTable.getFullyQualifiedTableName();
        String clientSetupTable = clientSetupDBTable.getFullyQualifiedTableName();
        String contractSetupTable = contractSetupDBTable.getFullyQualifiedTableName();

        // CommissionHistory
        String editTrxHistoryFKCol = DBTABLE.getDBColumn("EDITTrxHistoryFK").getFullyQualifiedColumnName();
        String updateStatusCol = DBTABLE.getDBColumn("UpdateStatus").getFullyQualifiedColumnName();

        // EDITTrxHistory
        String editTrxHistoryPKCol = editTrxHistoryDBTable.getDBColumn("EDITTrxHistoryPK").getFullyQualifiedColumnName();
        String editTrxFKCol = editTrxHistoryDBTable.getDBColumn("EDITTrxFK").getFullyQualifiedColumnName();

        // ContractSetup
        String contractSetupPKCol = contractSetupDBTable.getDBColumn("ContractSetupPK").getFullyQualifiedColumnName();
        String segmentFKCol = contractSetupDBTable.getDBColumn("SegmentFK").getFullyQualifiedColumnName();

        // ClientSetup
        String clientSetupPKCol = clientSetupDBTable.getDBColumn("ClientSetupPK").getFullyQualifiedColumnName();
        String contractSetupFKCol = clientSetupDBTable.getDBColumn("ContractSetupFK").getFullyQualifiedColumnName();

        // EDITTrx
        String editTrxPKCol = editTrxDBTable.getDBColumn("EDITTrxPK").getFullyQualifiedColumnName();
        String clientSetupFKCol = editTrxDBTable.getDBColumn("ClientSetupFK").getFullyQualifiedColumnName();
        String statusCol = editTrxDBTable.getDBColumn("Status").getFullyQualifiedColumnName();

        String sql = " SELECT " + TABLENAME + ".* FROM " + TABLENAME +
                     " JOIN " + editTrxHistoryTable +
                     " ON " + editTrxHistoryPKCol + " = " + editTrxHistoryFKCol +
                     " JOIN " + editTrxTable +
                     " ON " + editTrxPKCol + " = " + editTrxFKCol +
                     " JOIN " + clientSetupTable +
                     " ON " + clientSetupPKCol + " = " + clientSetupFKCol +
                     " JOIN " + contractSetupTable +
                     " ON " + contractSetupPKCol + " = " + contractSetupFKCol +
                     " WHERE " + segmentFKCol + " = ?" +
                     " AND " + updateStatusCol + " = ?" +
                     " AND " + statusCol + " IN (?, ?)";

        try
        {
            conn = crud.getCrudConn();

            ps = conn.prepareStatement(sql);

            ps.setLong(1, segmentPK);
            ps.setString(2, updateStatus);
            ps.setString(3, EDITTrx.STATUS_NATURAL);
            ps.setString(4, EDITTrx.STATUS_APPLY);

            rs = ps.executeQuery();

            while (rs.next())
            {
                CommissionHistoryVO commissionHistoryVO = (CommissionHistoryVO) CRUD.populateVOFromResultSetRow(rs, VOClass.getVOClassMetaData(CommissionHistoryVO.class));

                commissionHistories.add(commissionHistoryVO);
            }
        }
        catch (SQLException e)
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
                if (rs != null) rs.close();

                // If the crud passed in was null, close the connection started here
                if (crudPassedInAsNull)
                {
                    crud.close();
                    crud = null;
                }
            }
            catch (SQLException e)
            {
                System.out.println(e);

                e.printStackTrace();

                throw new RuntimeException(e);
            }
        }

        if (commissionHistories.size() == 0)
        {
            return null;
        }
        else
        {
            return (CommissionHistoryVO[]) commissionHistories.toArray(new CommissionHistoryVO[commissionHistories.size()]);
        }
    }

    /**
     * Retrieves CommissionHistories for given contract and update status.
     * @param segmentPK
     * @param updateStatus
     * @return
     */
    // Note: This method is overloaded. If you need a version that needs crud, please see other version.
    public CommissionHistoryVO[] findBySegmentPK_UpdateStatus(long segmentPK, String updateStatus)
    {
        CommissionHistoryVO[] commissionHistoryVOs = null;

        DBTable editTrxHistoryDBTable = DBTable.getDBTableForTable("EDITTrxHistory");
        DBTable editTrxDBTable = DBTable.getDBTableForTable("EDITTrx");
        DBTable clientSetupDBTable = DBTable.getDBTableForTable("ClientSetup");
        DBTable contractSetupDBTable = DBTable.getDBTableForTable("ContractSetup");

        String editTrxHistoryTable = editTrxHistoryDBTable.getFullyQualifiedTableName();
        String editTrxTable = editTrxDBTable.getFullyQualifiedTableName();
        String clientSetupTable = clientSetupDBTable.getFullyQualifiedTableName();
        String contractSetupTable = contractSetupDBTable.getFullyQualifiedTableName();

        // CommissionHistory
        String editTrxHistoryFKCol = DBTABLE.getDBColumn("EDITTrxHistoryFK").getFullyQualifiedColumnName();
        String updateStatusCol = DBTABLE.getDBColumn("UpdateStatus").getFullyQualifiedColumnName();

        // EDITTrxHistory
        String editTrxHistoryPKCol = editTrxHistoryDBTable.getDBColumn("EDITTrxHistoryPK").getFullyQualifiedColumnName();
        String editTrxFKCol = editTrxHistoryDBTable.getDBColumn("EDITTrxFK").getFullyQualifiedColumnName();

        // ContractSetup
        String contractSetupPKCol = contractSetupDBTable.getDBColumn("ContractSetupPK").getFullyQualifiedColumnName();
        String segmentFKCol = contractSetupDBTable.getDBColumn("SegmentFK").getFullyQualifiedColumnName();

        // ClientSetup
        String clientSetupPKCol = clientSetupDBTable.getDBColumn("ClientSetupPK").getFullyQualifiedColumnName();
        String contractSetupFKCol = clientSetupDBTable.getDBColumn("ContractSetupFK").getFullyQualifiedColumnName();

        // EDITTrx
        String editTrxPKCol = editTrxDBTable.getDBColumn("EDITTrxPK").getFullyQualifiedColumnName();
        String clientSetupFKCol = editTrxDBTable.getDBColumn("ClientSetupFK").getFullyQualifiedColumnName();

        String sql = " SELECT " + TABLENAME + ".* FROM " + TABLENAME +
                     " JOIN " + editTrxHistoryTable +
                     " ON " + editTrxHistoryPKCol + " = " + editTrxHistoryFKCol +
                     " JOIN " + editTrxTable +
                     " ON " + editTrxPKCol + " = " + editTrxFKCol +
                     " JOIN " + clientSetupTable +
                     " ON " + clientSetupPKCol + " = " + clientSetupFKCol +
                     " JOIN " + contractSetupTable +
                     " ON " + contractSetupPKCol + " = " + contractSetupFKCol +
                     " WHERE " + segmentFKCol + " = ?" +
                     " AND " + updateStatusCol + " = ?";

        Connection conn = ConnectionFactory.getSingleton().getConnection(POOLNAME);

        PreparedStatement ps = null;

        try
        {
            ps = conn.prepareStatement(sql);

            ps.setLong(1, segmentPK);
            ps.setString(2, updateStatus);

            commissionHistoryVOs =  (CommissionHistoryVO[]) executeQuery(CommissionHistoryVO.class,
                                                                          ps,
                                                                           POOLNAME,
                                                                            false,
                                                                             null);
        }
        catch (SQLException e)
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

        return commissionHistoryVOs;
    }

    /**
     * Finder.
     * @param agentGroupPK
     * @param segmentPK
     * @return
     */
    public CommissionHistoryVO[] findByAgentGroupPK_SegmentFK(long agentGroupPK, long segmentPK)
    {
        DBTable editTrxHistoryDBTable = DBTable.getDBTableForTable("EDITTrxHistory");
        DBTable editTrxDBTable = DBTable.getDBTableForTable("EDITTrx");
        DBTable clientSetupDBTable = DBTable.getDBTableForTable("ClientSetup");
        DBTable contractSetupDBTable = DBTable.getDBTableForTable("ContractSetup");

        String editTrxHistoryTable      = editTrxHistoryDBTable.getFullyQualifiedTableName();
        String editTrxTable             = editTrxDBTable.getFullyQualifiedTableName();
        String clientSetupTable = clientSetupDBTable.getFullyQualifiedTableName();
        String contractSetupTable = contractSetupDBTable.getFullyQualifiedTableName();

        String agentGroupFKCol = DBTABLE.getDBColumn("AgentGroupFK").getFullyQualifiedColumnName();
        String editTrxHistoryFKCol = DBTABLE.getDBColumn("EDITTrxHistoryFK").getFullyQualifiedColumnName();

        String contractSetupPKCol = contractSetupDBTable.getDBColumn("ContractSetupPK").getFullyQualifiedColumnName();
        String segmentFKCol = contractSetupDBTable.getDBColumn("SegmentFK").getFullyQualifiedColumnName();

        String clientSetupPKCol = clientSetupDBTable.getDBColumn("ClientSetupPK").getFullyQualifiedColumnName();
        String contractSetupFKCol = clientSetupDBTable.getDBColumn("ContractSetupFK").getFullyQualifiedColumnName();

        String editTrxPKCol = editTrxDBTable.getDBColumn("EDITTrxPK").getFullyQualifiedColumnName();
        String clientSetupFKCol = editTrxDBTable.getDBColumn("ClientSetupFK").getFullyQualifiedColumnName();

        String editTrxHistoryPKCol = editTrxHistoryDBTable.getDBColumn("EDITTrxHistoryPK").getFullyQualifiedColumnName();
        String editTrxFKCol = editTrxHistoryDBTable.getDBColumn("EDITTrxFK").getFullyQualifiedColumnName();

        String sql = "SELECT * FROM " + TABLENAME + ", " + editTrxHistoryTable + ", " + editTrxTable + ", " +
                     clientSetupTable + ", " + contractSetupTable +
                     " WHERE " + segmentFKCol + " = " + segmentPK +
                     " AND " + contractSetupPKCol + " = " + contractSetupFKCol +
                     " AND " + clientSetupPKCol + " = " + clientSetupFKCol +
                     " AND " + editTrxPKCol + " = " + editTrxFKCol +
                     " AND " + editTrxHistoryPKCol + " = " + editTrxHistoryFKCol +
                     " AND " + agentGroupPK + " = " + agentGroupPK;

        return (CommissionHistoryVO[]) executeQuery(CommissionHistoryVO.class,
                                                     sql,
                                                      POOLNAME,
                                                       false,
                                                         null);
    }

    public CommissionHistoryVO[] findByPlacedAgentPK_UpdateDateTime(long placedAgentPK, EDITDateTime lastCheckDateTime)
    {
        CommissionHistoryVO[] commissionHistoryVOs = null;

        String placedAgentFKCol  = DBTABLE.getDBColumn("PlacedAgentFK").getFullyQualifiedColumnName();
        String updateDateTimeCol = DBTABLE.getDBColumn("UpdateDateTime").getFullyQualifiedColumnName();
        String commissionTypeCTCol = DBTABLE.getDBColumn("CommissionTypeCT").getFullyQualifiedColumnName();


        String sql = "SELECT * FROM " + TABLENAME +
                     " WHERE " + placedAgentFKCol + " = ?" +
                     " AND " + updateDateTimeCol + " >  ?" +
                     " AND NOT " + commissionTypeCTCol + " = 'Check'";;

        Connection conn = null;

        PreparedStatement ps = null;

        try
        {
            conn = ConnectionFactory.getSingleton().getConnection(POOLNAME);

            ps = conn.prepareStatement(sql);

            ps.setLong(1, placedAgentPK);
            ps.setTimestamp(2, DBUtil.convertStringToTimestamp(lastCheckDateTime.getFormattedDateTime()));

            commissionHistoryVOs = (CommissionHistoryVO[]) executeQuery(CommissionHistoryVO.class,
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

        return commissionHistoryVOs;
    }

  /**
   * Finder.
   * @param agentSnapshotPK
   * @return
   */
  public CommissionHistoryVO[] findBy_AgentSnapshotPK(long agentSnapshotPK)
  {
    String agentSnapshotFKCol = DBTABLE.getDBColumn("AgentSnapshotFK").getFullyQualifiedColumnName();

    String sql = "SELECT * FROM " + TABLENAME +
                 " WHERE " + agentSnapshotFKCol + " = " + agentSnapshotPK;

    return (CommissionHistoryVO[]) executeQuery(CommissionHistoryVO.class,
                                                 sql,
                                                  POOLNAME,
                                                   false,
                                                     null);  
  }
}
