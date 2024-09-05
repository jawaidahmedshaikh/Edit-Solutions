/*
 * User: cgleason
 * Date: Dec 3, 2004
 * Time: 10:47:23 AM
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */

package event.dm.dao;

import edit.services.db.*;
import edit.common.EDITBigDecimal;

import java.sql.*;

import event.EDITTrx;


public class TransactionAccumsFastDAO
{
    private final String POOLNAME;

    private final DBTable FINANCIAL_HISTORY_DBTABLE;
    private final DBTable EDITTRX_HISTORY_DBTABLE;
    private final DBTable EDITTRX_DBTABLE;
    private final DBTable CLIENT_SETUP_DBTABLE;
    private final DBTable CONTRACT_SETUP_DBTABLE;
    private final DBTable GROUP_SETUP_DBTABLE;

    private final String FINANCIAL_HISTORY_TABLENAME;
    private final String EDITTRX_HISTORY_TABLENAME;
    private final String EDITTRX_TABLENAME;
    private final String CLIENT_SETUP_TABLENAME;
    private final String CONTRACT_SETUP_TABLENAME;
    private final String GROUP_SETUP_TABLENAME;

    public TransactionAccumsFastDAO()
    {
        POOLNAME  = ConnectionFactory.EDITSOLUTIONS_POOL;

        FINANCIAL_HISTORY_DBTABLE      = DBTable.getDBTableForTable("FinancialHistory");
        EDITTRX_HISTORY_DBTABLE        = DBTable.getDBTableForTable("EDITTrxHistory");
        EDITTRX_DBTABLE                = DBTable.getDBTableForTable("EDITTrx");
        CLIENT_SETUP_DBTABLE           = DBTable.getDBTableForTable("ClientSetup");
        CONTRACT_SETUP_DBTABLE         = DBTable.getDBTableForTable("ContractSetup");
        GROUP_SETUP_DBTABLE            = DBTable.getDBTableForTable("GroupSetup");

        FINANCIAL_HISTORY_TABLENAME    = FINANCIAL_HISTORY_DBTABLE.getFullyQualifiedTableName();
        EDITTRX_HISTORY_TABLENAME      = EDITTRX_HISTORY_DBTABLE.getFullyQualifiedTableName();
        EDITTRX_TABLENAME              = EDITTRX_DBTABLE.getFullyQualifiedTableName();
        CLIENT_SETUP_TABLENAME         = CLIENT_SETUP_DBTABLE.getFullyQualifiedTableName();
        CONTRACT_SETUP_TABLENAME       = CONTRACT_SETUP_DBTABLE.getFullyQualifiedTableName();
        GROUP_SETUP_TABLENAME          = GROUP_SETUP_DBTABLE.getFullyQualifiedTableName();
    }

    /**
     * This query sums the grossAmount on the FinancialHistory table for all the transactions
     * that satisfy the requirements of the joined tables.  For the contract requested, use the
     * transactions of "PY" with effective date <= the compare date and the premium type matches
     * what was passed in.
     * @param segmentFK
     * @param compareDate
     * @param premiumType
     * @return
     * @throws Exception
     */
    public EDITBigDecimal accumPY_PremiumTypeAndDate(long segmentFK, String compareDate, String premiumType) throws Exception
    {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        String editTrxHistoryFKCol = FINANCIAL_HISTORY_DBTABLE.getDBColumn("EDITTrxHistoryFK").getFullyQualifiedColumnName();
        String editTrxHistoryPKCol = EDITTRX_HISTORY_DBTABLE.getDBColumn("EDITTrxHistoryPK").getFullyQualifiedColumnName();
        String editTrxFKCol = EDITTRX_HISTORY_DBTABLE.getDBColumn("EDITTrxFK").getFullyQualifiedColumnName();
        String editTrxPKCol = EDITTRX_DBTABLE.getDBColumn("EDITTrxPK").getFullyQualifiedColumnName();
        String clientSetupFKCol = EDITTRX_DBTABLE.getDBColumn("ClientSetupFK").getFullyQualifiedColumnName();
        String clientSetupPKCol = CLIENT_SETUP_DBTABLE.getDBColumn("ClientSetupPK").getFullyQualifiedColumnName();
        String contractSetupFKCol = CLIENT_SETUP_DBTABLE.getDBColumn("ContractSetupFK").getFullyQualifiedColumnName();
        String contractSetupPKCol = CONTRACT_SETUP_DBTABLE.getDBColumn("ContractSetupPK").getFullyQualifiedColumnName();
        String groupSetupPKCol = GROUP_SETUP_DBTABLE.getDBColumn("GroupSetupPK").getFullyQualifiedColumnName();
        String groupSetupFKCol = CONTRACT_SETUP_DBTABLE.getDBColumn("GroupSetupFK").getFullyQualifiedColumnName();

        String segmentFKCol = CONTRACT_SETUP_DBTABLE.getDBColumn("SegmentFK").getFullyQualifiedColumnName();

        String premiumTypeCol = GROUP_SETUP_DBTABLE.getDBColumn("PremiumTypeCT").getFullyQualifiedColumnName();
        String transactionTypeCTCol = EDITTRX_DBTABLE.getDBColumn("TransactionTypeCT").getFullyQualifiedColumnName();
        String pendingStatusCol = EDITTRX_DBTABLE.getDBColumn("PendingStatus").getFullyQualifiedColumnName();
        String effectiveDateCol = EDITTRX_DBTABLE.getDBColumn("EffectiveDate").getFullyQualifiedColumnName();
        String grossAmountCol = FINANCIAL_HISTORY_DBTABLE.getDBColumn("GrossAmount").getFullyQualifiedColumnName();
        String statusCol = EDITTRX_DBTABLE.getDBColumn("Status").getFullyQualifiedColumnName();

        String sql = "SELECT SUM(" + grossAmountCol  + ")  FROM " + FINANCIAL_HISTORY_TABLENAME +
                     " INNER JOIN " + EDITTRX_HISTORY_TABLENAME + " ON " + editTrxHistoryFKCol + " = " + editTrxHistoryPKCol +
                     " INNER JOIN " + EDITTRX_TABLENAME + " ON " + editTrxFKCol + " = " + editTrxPKCol +
                     " INNER JOIN " + CLIENT_SETUP_TABLENAME + " ON " + clientSetupFKCol + " = " + clientSetupPKCol +
                     " INNER JOIN " + CONTRACT_SETUP_TABLENAME + " ON " + contractSetupFKCol + " = " + contractSetupPKCol +
                     " INNER JOIN " + GROUP_SETUP_TABLENAME + " ON " + groupSetupFKCol + " = " + groupSetupPKCol +
                     " WHERE " + segmentFKCol + " =  ?"  + " AND " + premiumTypeCol + " = ?" +
                     " AND " + transactionTypeCTCol + " = " + "'PY' AND " + pendingStatusCol + " = " + "'H' " +
                     " AND (" + statusCol + " = 'N'" +
                     " OR   " + statusCol + " = 'A')" +
                     " AND " + effectiveDateCol + " <= ?";

        EDITBigDecimal summedAmount = new EDITBigDecimal("0");

        try
        {
            conn = ConnectionFactory.getSingleton().getConnection(POOLNAME);

            ps = conn.prepareStatement(sql);

            ps.setLong(1, segmentFK);
            ps.setString(2, premiumType);
            ps.setDate(3, DBUtil.convertStringToDate(compareDate));

            rs = ps.executeQuery();

            while (rs.next())
            {
                summedAmount = (new EDITBigDecimal(rs.getBigDecimal(1)));
            }
        }
        catch(Exception e)
        {
            System.out.println(e);
            e.printStackTrace();
            throw e;
        }
        finally
        {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            if (conn != null) conn.close();
        }

        return summedAmount;
    }

    /**
     * This query sums the grossAmount on the FinancialHistory table for all the transactions
     * that satisfy the requirements of the joined tables.  For the contract requested, use the
     * transactions of "PY" with effective date <= the compare date.
     * @param segmentFK
     * @param compareDate
     * @return
     * @throws Exception
     */
    public EDITBigDecimal accumPY_PremiumToDate(long segmentFK, String compareDate) throws Exception
    {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        String editTrxHistoryFKCol = FINANCIAL_HISTORY_DBTABLE.getDBColumn("EDITTrxHistoryFK").getFullyQualifiedColumnName();
        String editTrxHistoryPKCol = EDITTRX_HISTORY_DBTABLE.getDBColumn("EDITTrxHistoryPK").getFullyQualifiedColumnName();
        String editTrxFKCol = EDITTRX_HISTORY_DBTABLE.getDBColumn("EDITTrxFK").getFullyQualifiedColumnName();
        String editTrxPKCol = EDITTRX_DBTABLE.getDBColumn("EDITTrxPK").getFullyQualifiedColumnName();
        String clientSetupFKCol = EDITTRX_DBTABLE.getDBColumn("ClientSetupFK").getFullyQualifiedColumnName();
        String clientSetupPKCol = CLIENT_SETUP_DBTABLE.getDBColumn("ClientSetupPK").getFullyQualifiedColumnName();
        String contractSetupFKCol = CLIENT_SETUP_DBTABLE.getDBColumn("ContractSetupFK").getFullyQualifiedColumnName();
        String contractSetupPKCol = CONTRACT_SETUP_DBTABLE.getDBColumn("ContractSetupPK").getFullyQualifiedColumnName();

        String segmentFKCol = CONTRACT_SETUP_DBTABLE.getDBColumn("SegmentFK").getFullyQualifiedColumnName();

        String transactionTypeCTCol = EDITTRX_DBTABLE.getDBColumn("TransactionTypeCT").getFullyQualifiedColumnName();
        String pendingStatusCol = EDITTRX_DBTABLE.getDBColumn("PendingStatus").getFullyQualifiedColumnName();
        String effectiveDateCol = EDITTRX_DBTABLE.getDBColumn("EffectiveDate").getFullyQualifiedColumnName();
        String grossAmountCol = FINANCIAL_HISTORY_DBTABLE.getDBColumn("GrossAmount").getFullyQualifiedColumnName();
        String statusCol = EDITTRX_DBTABLE.getDBColumn("Status").getFullyQualifiedColumnName();

        String sql = "SELECT  SUM(" + grossAmountCol  + ")  FROM " + FINANCIAL_HISTORY_TABLENAME +
                     " INNER JOIN " + EDITTRX_HISTORY_TABLENAME + " ON " + editTrxHistoryFKCol + " = " + editTrxHistoryPKCol +
                     " INNER JOIN " + EDITTRX_TABLENAME + " ON " + editTrxFKCol + " = " + editTrxPKCol +
                     " INNER JOIN " + CLIENT_SETUP_TABLENAME + " ON " + clientSetupFKCol + " = " + clientSetupPKCol +
                     " INNER JOIN " + CONTRACT_SETUP_TABLENAME + " ON " + contractSetupFKCol + " = " + contractSetupPKCol +
                     " WHERE " + segmentFKCol + " = ?" +
                     " AND " + transactionTypeCTCol + " = 'PY' AND " + pendingStatusCol + " = 'H' " +
                     " AND (" + statusCol + " = 'N'" +
                     " OR   " + statusCol + " = 'A')" +
                     " AND " + effectiveDateCol + " <= ?";

        EDITBigDecimal summedAmount = new EDITBigDecimal("0");

        try
        {
            conn = ConnectionFactory.getSingleton().getConnection(POOLNAME);

            ps = conn.prepareStatement(sql);

            ps.setLong(1, segmentFK);
            ps.setDate(2, DBUtil.convertStringToDate(compareDate));

            rs = ps.executeQuery();

            while (rs.next())
            {
                summedAmount = (new EDITBigDecimal(rs.getBigDecimal(1)));
            }
        }
        catch(Exception e)
        {
            System.out.println(e);
            e.printStackTrace();
            throw e;
        }
        finally
        {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            if (conn != null) conn.close();
        }

        return summedAmount;
    }

    /**
     * This query sums the grossAmount on the FinancialHistory table for all the transactions
     * that satisfy the requirements of the joined tables.  For the contract requested, use the
     * transactions of "PY" with effective date >= start date and effective date <= the end date.
     * @param segmentFK
     * @param startDate
     * @param endDate
     * @return
     * @throws Exception
     */
    public EDITBigDecimal accumPY_PremiumToDateForDateRange(long segmentFK, String startDate, String endDate) throws Exception
    {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        String editTrxHistoryFKCol = FINANCIAL_HISTORY_DBTABLE.getDBColumn("EDITTrxHistoryFK").getFullyQualifiedColumnName();
        String editTrxHistoryPKCol = EDITTRX_HISTORY_DBTABLE.getDBColumn("EDITTrxHistoryPK").getFullyQualifiedColumnName();
        String editTrxFKCol = EDITTRX_HISTORY_DBTABLE.getDBColumn("EDITTrxFK").getFullyQualifiedColumnName();
        String editTrxPKCol = EDITTRX_DBTABLE.getDBColumn("EDITTrxPK").getFullyQualifiedColumnName();
        String clientSetupFKCol = EDITTRX_DBTABLE.getDBColumn("ClientSetupFK").getFullyQualifiedColumnName();
        String clientSetupPKCol = CLIENT_SETUP_DBTABLE.getDBColumn("ClientSetupPK").getFullyQualifiedColumnName();
        String contractSetupFKCol = CLIENT_SETUP_DBTABLE.getDBColumn("ContractSetupFK").getFullyQualifiedColumnName();
        String contractSetupPKCol = CONTRACT_SETUP_DBTABLE.getDBColumn("ContractSetupPK").getFullyQualifiedColumnName();
        String groupSetupPKCol = GROUP_SETUP_DBTABLE.getDBColumn("GroupSetupPK").getFullyQualifiedColumnName();
        String premiumTypeCTCol = GROUP_SETUP_DBTABLE.getDBColumn("PremiumTypeCT").getFullyQualifiedColumnName();

        String groupSetupFKCol = CONTRACT_SETUP_DBTABLE.getDBColumn("GroupSetupFK").getFullyQualifiedColumnName();
        String segmentFKCol = CONTRACT_SETUP_DBTABLE.getDBColumn("SegmentFK").getFullyQualifiedColumnName();
        String transactionTypeCTCol = EDITTRX_DBTABLE.getDBColumn("TransactionTypeCT").getFullyQualifiedColumnName();
        String pendingStatusCol = EDITTRX_DBTABLE.getDBColumn("PendingStatus").getFullyQualifiedColumnName();
        String effectiveDateCol = EDITTRX_DBTABLE.getDBColumn("EffectiveDate").getFullyQualifiedColumnName();
        String grossAmountCol = FINANCIAL_HISTORY_DBTABLE.getDBColumn("GrossAmount").getFullyQualifiedColumnName();
        String statusCol = EDITTRX_DBTABLE.getDBColumn("Status").getFullyQualifiedColumnName();

        String sql = "SELECT  SUM(" + grossAmountCol  + ")  FROM " + FINANCIAL_HISTORY_TABLENAME +
                     " INNER JOIN " + EDITTRX_HISTORY_TABLENAME + " ON " + editTrxHistoryFKCol + " = " + editTrxHistoryPKCol +
                     " INNER JOIN " + EDITTRX_TABLENAME + " ON " + editTrxFKCol + " = " + editTrxPKCol +
                     " INNER JOIN " + CLIENT_SETUP_TABLENAME + " ON " + clientSetupFKCol + " = " + clientSetupPKCol +
                     " INNER JOIN " + CONTRACT_SETUP_TABLENAME + " ON " + contractSetupFKCol + " = " + contractSetupPKCol +
                     " INNER JOIN " + GROUP_SETUP_TABLENAME + " ON " + groupSetupFKCol + " = " + groupSetupPKCol +
                     " WHERE " + segmentFKCol + " = ?" +
                     " AND " + transactionTypeCTCol + " = 'PY' AND " + pendingStatusCol + " = 'H' " +
                     " AND (" + statusCol + " = 'N'" +
                     " OR   " + statusCol + " = 'A')" +
                     " AND (" + effectiveDateCol +  " >= ?" +
                     " AND " + effectiveDateCol + " <= ?)";

        EDITBigDecimal summedAmount = new EDITBigDecimal("0");

        try
        {
            conn = ConnectionFactory.getSingleton().getConnection(POOLNAME);

            ps = conn.prepareStatement(sql);

            ps.setLong(1, segmentFK);
            ps.setDate(2, DBUtil.convertStringToDate(startDate));
            ps.setDate(3, DBUtil.convertStringToDate(endDate));

            rs = ps.executeQuery();

            while (rs.next())
            {
                summedAmount = (new EDITBigDecimal(rs.getBigDecimal(1)));
            }
        }
        catch(Exception e)
        {
            System.out.println(e);
            e.printStackTrace();
            throw e;
        }
        finally
        {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            if (conn != null) conn.close();
        }

        return summedAmount;
    }

    /**
     * This query sums the grossAmount on the FinancialHistory table for all the transactions
     * that satisfy the requirements of the joined tables.  For the contract requested, use the
     * transactions of "PY" that are not of type 'Exchange' with effective date >= start date
     * and effective date <= the end date.
     * @param segmentFK
     * @param startDate
     * @param endDate
     * @return
     * @throws Exception
     */
    public EDITBigDecimal accumPY_PremSinceLast7PayForDateRange(long segmentFK, String startDate, String endDate) throws Exception
    {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        String editTrxHistoryFKCol = FINANCIAL_HISTORY_DBTABLE.getDBColumn("EDITTrxHistoryFK").getFullyQualifiedColumnName();
        String editTrxHistoryPKCol = EDITTRX_HISTORY_DBTABLE.getDBColumn("EDITTrxHistoryPK").getFullyQualifiedColumnName();
        String editTrxFKCol = EDITTRX_HISTORY_DBTABLE.getDBColumn("EDITTrxFK").getFullyQualifiedColumnName();
        String editTrxPKCol = EDITTRX_DBTABLE.getDBColumn("EDITTrxPK").getFullyQualifiedColumnName();
        String clientSetupFKCol = EDITTRX_DBTABLE.getDBColumn("ClientSetupFK").getFullyQualifiedColumnName();
        String clientSetupPKCol = CLIENT_SETUP_DBTABLE.getDBColumn("ClientSetupPK").getFullyQualifiedColumnName();
        String contractSetupFKCol = CLIENT_SETUP_DBTABLE.getDBColumn("ContractSetupFK").getFullyQualifiedColumnName();
        String contractSetupPKCol = CONTRACT_SETUP_DBTABLE.getDBColumn("ContractSetupPK").getFullyQualifiedColumnName();
        String groupSetupPKCol = GROUP_SETUP_DBTABLE.getDBColumn("GroupSetupPK").getFullyQualifiedColumnName();
        String premiumTypeCTCol = GROUP_SETUP_DBTABLE.getDBColumn("PremiumTypeCT").getFullyQualifiedColumnName();

        String groupSetupFKCol = CONTRACT_SETUP_DBTABLE.getDBColumn("GroupSetupFK").getFullyQualifiedColumnName();
        String segmentFKCol = CONTRACT_SETUP_DBTABLE.getDBColumn("SegmentFK").getFullyQualifiedColumnName();
        String transactionTypeCTCol = EDITTRX_DBTABLE.getDBColumn("TransactionTypeCT").getFullyQualifiedColumnName();
        String pendingStatusCol = EDITTRX_DBTABLE.getDBColumn("PendingStatus").getFullyQualifiedColumnName();
        String effectiveDateCol = EDITTRX_DBTABLE.getDBColumn("EffectiveDate").getFullyQualifiedColumnName();
        String grossAmountCol = FINANCIAL_HISTORY_DBTABLE.getDBColumn("GrossAmount").getFullyQualifiedColumnName();
        String statusCol = EDITTRX_DBTABLE.getDBColumn("Status").getFullyQualifiedColumnName();

        String sql = "SELECT  SUM(" + grossAmountCol  + ")  FROM " + FINANCIAL_HISTORY_TABLENAME +
                     " INNER JOIN " + EDITTRX_HISTORY_TABLENAME + " ON " + editTrxHistoryFKCol + " = " + editTrxHistoryPKCol +
                     " INNER JOIN " + EDITTRX_TABLENAME + " ON " + editTrxFKCol + " = " + editTrxPKCol +
                     " INNER JOIN " + CLIENT_SETUP_TABLENAME + " ON " + clientSetupFKCol + " = " + clientSetupPKCol +
                     " INNER JOIN " + CONTRACT_SETUP_TABLENAME + " ON " + contractSetupFKCol + " = " + contractSetupPKCol +
                     " INNER JOIN " + GROUP_SETUP_TABLENAME + " ON " + groupSetupFKCol + " = " + groupSetupPKCol +
                     " WHERE " + segmentFKCol + " = ?" +
                     " AND " + transactionTypeCTCol + " = 'PY' AND " + pendingStatusCol + " = 'H' " +
                     " AND (" + statusCol + " = 'N'" +
                     " OR   " + statusCol + " = 'A')" +
                     " AND (" + effectiveDateCol +  " >= ?"  +
                     " AND " + effectiveDateCol + " <= ?)" +
                     " AND " + premiumTypeCTCol + " NOT IN ('Exchange')";

        EDITBigDecimal summedAmount = new EDITBigDecimal("0");

        try
        {
            conn = ConnectionFactory.getSingleton().getConnection(POOLNAME);

            ps = conn.prepareStatement(sql);

            ps.setLong(1, segmentFK);
            ps.setDate(2, DBUtil.convertStringToDate(startDate));
            ps.setDate(3, DBUtil.convertStringToDate(endDate));

            rs = ps.executeQuery();

            while (rs.next())
            {
                summedAmount = (new EDITBigDecimal(rs.getBigDecimal(1)));
            }
        }
        catch(Exception e)
        {
            System.out.println(e);
            e.printStackTrace();
            throw e;
        }
        finally
        {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            if (conn != null) conn.close();
        }

        return summedAmount;
    }

    /**
     * This query sums the netAmount on the FinancialHistory table for all the transactions
     * that satisfy the requirements of the joined tables.  For the contract requested, use the
     * transactions of "WI" with effective date <= the compare date.
     * @param segmentFK
     * @param compareDate
     * @return
     * @throws Exception
     */
    public EDITBigDecimal accumWI_WithdrawalsToDate(long segmentFK, String compareDate) throws Exception
    {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        String editTrxHistoryFKCol = FINANCIAL_HISTORY_DBTABLE.getDBColumn("EDITTrxHistoryFK").getFullyQualifiedColumnName();
        String editTrxHistoryPKCol = EDITTRX_HISTORY_DBTABLE.getDBColumn("EDITTrxHistoryPK").getFullyQualifiedColumnName();
        String editTrxFKCol = EDITTRX_HISTORY_DBTABLE.getDBColumn("EDITTrxFK").getFullyQualifiedColumnName();
        String editTrxPKCol = EDITTRX_DBTABLE.getDBColumn("EDITTrxPK").getFullyQualifiedColumnName();
        String clientSetupFKCol = EDITTRX_DBTABLE.getDBColumn("ClientSetupFK").getFullyQualifiedColumnName();
        String clientSetupPKCol = CLIENT_SETUP_DBTABLE.getDBColumn("ClientSetupPK").getFullyQualifiedColumnName();
        String contractSetupFKCol = CLIENT_SETUP_DBTABLE.getDBColumn("ContractSetupFK").getFullyQualifiedColumnName();
        String contractSetupPKCol = CONTRACT_SETUP_DBTABLE.getDBColumn("ContractSetupPK").getFullyQualifiedColumnName();

        String segmentFKCol = CONTRACT_SETUP_DBTABLE.getDBColumn("SegmentFK").getFullyQualifiedColumnName();

        String transactionTypeCTCol = EDITTRX_DBTABLE.getDBColumn("TransactionTypeCT").getFullyQualifiedColumnName();
        String pendingStatusCol = EDITTRX_DBTABLE.getDBColumn("PendingStatus").getFullyQualifiedColumnName();
        String effectiveDateCol = EDITTRX_DBTABLE.getDBColumn("EffectiveDate").getFullyQualifiedColumnName();
        String netAmountCol = FINANCIAL_HISTORY_DBTABLE.getDBColumn("NetAmount").getFullyQualifiedColumnName();
        String statusCol = EDITTRX_DBTABLE.getDBColumn("Status").getFullyQualifiedColumnName();

        String sql = "SELECT  SUM(" + netAmountCol  + ")  FROM " + FINANCIAL_HISTORY_TABLENAME +
                     " INNER JOIN " + EDITTRX_HISTORY_TABLENAME + " ON " + editTrxHistoryFKCol + " = " + editTrxHistoryPKCol +
                     " INNER JOIN " + EDITTRX_TABLENAME + " ON " + editTrxFKCol + " = " + editTrxPKCol +
                     " INNER JOIN " + CLIENT_SETUP_TABLENAME + " ON " + clientSetupFKCol + " = " + clientSetupPKCol +
                     " INNER JOIN " + CONTRACT_SETUP_TABLENAME + " ON " + contractSetupFKCol + " = " + contractSetupPKCol +
                     " WHERE " + segmentFKCol + " = ?" +
                     " AND " + transactionTypeCTCol + " = 'WI' AND " + pendingStatusCol + " = 'H' " +
                     " AND (" + statusCol + " = 'N'" +
                     " OR   " + statusCol + " = 'A')" +
                     " AND " + effectiveDateCol + " <= ?";

        EDITBigDecimal summedAmount = new EDITBigDecimal("0");

        try
        {
            conn = ConnectionFactory.getSingleton().getConnection(POOLNAME);

            ps = conn.prepareStatement(sql);

            ps.setLong(1, segmentFK);
            ps.setDate(2, DBUtil.convertStringToDate(compareDate));

            rs = ps.executeQuery();

            while (rs.next())
            {
                summedAmount = (new EDITBigDecimal(rs.getBigDecimal(1)));
            }
        }
        catch(Exception e)
        {
            System.out.println(e);
            e.printStackTrace();
            throw e;
        }
        finally
        {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            if (conn != null) conn.close();
        }

        return summedAmount;
    }

    /**
     * This query sums the netAmount on the FinancialHistory table for all the transactions
     * that satisfy the requirements of the joined tables.  For the contract requested, use the
     * transactions of "WI" with effective date >= start date and effective date <= the end date.
     * @param segmentFK
     * @param startDate
     * @param endDate
     * @return
     * @throws Exception
     */
    public EDITBigDecimal accumWI_WithdrawalsToDateForDateRange(long segmentFK, String startDate, String endDate) throws Exception
    {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        String editTrxHistoryFKCol = FINANCIAL_HISTORY_DBTABLE.getDBColumn("EDITTrxHistoryFK").getFullyQualifiedColumnName();
        String editTrxHistoryPKCol = EDITTRX_HISTORY_DBTABLE.getDBColumn("EDITTrxHistoryPK").getFullyQualifiedColumnName();
        String editTrxFKCol = EDITTRX_HISTORY_DBTABLE.getDBColumn("EDITTrxFK").getFullyQualifiedColumnName();
        String editTrxPKCol = EDITTRX_DBTABLE.getDBColumn("EDITTrxPK").getFullyQualifiedColumnName();
        String clientSetupFKCol = EDITTRX_DBTABLE.getDBColumn("ClientSetupFK").getFullyQualifiedColumnName();
        String clientSetupPKCol = CLIENT_SETUP_DBTABLE.getDBColumn("ClientSetupPK").getFullyQualifiedColumnName();
        String contractSetupFKCol = CLIENT_SETUP_DBTABLE.getDBColumn("ContractSetupFK").getFullyQualifiedColumnName();
        String contractSetupPKCol = CONTRACT_SETUP_DBTABLE.getDBColumn("ContractSetupPK").getFullyQualifiedColumnName();

        String segmentFKCol = CONTRACT_SETUP_DBTABLE.getDBColumn("SegmentFK").getFullyQualifiedColumnName();
        String transactionTypeCTCol = EDITTRX_DBTABLE.getDBColumn("TransactionTypeCT").getFullyQualifiedColumnName();
        String pendingStatusCol = EDITTRX_DBTABLE.getDBColumn("PendingStatus").getFullyQualifiedColumnName();
        String effectiveDateCol = EDITTRX_DBTABLE.getDBColumn("EffectiveDate").getFullyQualifiedColumnName();
        String netAmountCol = FINANCIAL_HISTORY_DBTABLE.getDBColumn("NetAmount").getFullyQualifiedColumnName();
        String statusCol = EDITTRX_DBTABLE.getDBColumn("Status").getFullyQualifiedColumnName();

        String sql = "SELECT  SUM(" + netAmountCol  + ")  FROM " + FINANCIAL_HISTORY_TABLENAME +
                     " INNER JOIN " + EDITTRX_HISTORY_TABLENAME + " ON " + editTrxHistoryFKCol + " = " + editTrxHistoryPKCol +
                     " INNER JOIN " + EDITTRX_TABLENAME + " ON " + editTrxFKCol + " = " + editTrxPKCol +
                     " INNER JOIN " + CLIENT_SETUP_TABLENAME + " ON " + clientSetupFKCol + " = " + clientSetupPKCol +
                     " INNER JOIN " + CONTRACT_SETUP_TABLENAME + " ON " + contractSetupFKCol + " = " + contractSetupPKCol +
                     " WHERE " + segmentFKCol + " = ?" +
                     " AND " + transactionTypeCTCol + " = 'WI' AND " + pendingStatusCol + " = 'H' " +
                     " AND (" + statusCol + " = 'N'" +
                     " OR   " + statusCol + " = 'A')" +
                     " AND (" + effectiveDateCol +  " >= ?" +
                     " AND " + effectiveDateCol + " <= ?)";

        EDITBigDecimal summedAmount = new EDITBigDecimal("0");

        try
        {
            conn = ConnectionFactory.getSingleton().getConnection(POOLNAME);

            ps = conn.prepareStatement(sql);

            ps.setLong(1, segmentFK);
            ps.setDate(2, DBUtil.convertStringToDate(startDate));
            ps.setDate(3, DBUtil.convertStringToDate(endDate));

            rs = ps.executeQuery();

            while (rs.next())
            {
                summedAmount = (new EDITBigDecimal(rs.getBigDecimal(1)));
            }
        }
        catch(Exception e)
        {
            System.out.println(e);
            e.printStackTrace();
            throw e;
        }
        finally
        {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            if (conn != null) conn.close();
        }

        return summedAmount;
    }

    /**
     * This query sums the transaction count on the EDITTrx table for all the transactions
     * that satisfy the requirements of the joined tables.  For the contract requested, use the
     * transactions type passed in, with effective date >= start date and effective date <= the end date.
     * @param segmentFK
     * @param startDate
     * @param endDate
     * @param trxType
     * @return
     * @throws Exception
     */
    public int trxCountForDateRange(long segmentFK, String startDate, String endDate, String trxType) throws Exception
    {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        String clientSetupFKCol = EDITTRX_DBTABLE.getDBColumn("ClientSetupFK").getFullyQualifiedColumnName();
        String clientSetupPKCol = CLIENT_SETUP_DBTABLE.getDBColumn("ClientSetupPK").getFullyQualifiedColumnName();
        String contractSetupFKCol = CLIENT_SETUP_DBTABLE.getDBColumn("ContractSetupFK").getFullyQualifiedColumnName();
        String contractSetupPKCol = CONTRACT_SETUP_DBTABLE.getDBColumn("ContractSetupPK").getFullyQualifiedColumnName();

        String segmentFKCol = CONTRACT_SETUP_DBTABLE.getDBColumn("SegmentFK").getFullyQualifiedColumnName();
        String transactionTypeCTCol = EDITTRX_DBTABLE.getDBColumn("TransactionTypeCT").getFullyQualifiedColumnName();
        String pendingStatusCol = EDITTRX_DBTABLE.getDBColumn("PendingStatus").getFullyQualifiedColumnName();
        String effectiveDateCol = EDITTRX_DBTABLE.getDBColumn("EffectiveDate").getFullyQualifiedColumnName();
        String statusCol = EDITTRX_DBTABLE.getDBColumn("Status").getFullyQualifiedColumnName();

        String sql = "SELECT  COUNT(" + transactionTypeCTCol  + ") FROM " + EDITTRX_TABLENAME +
                     " INNER JOIN " + CLIENT_SETUP_TABLENAME + " ON " + clientSetupFKCol + " = " + clientSetupPKCol +
                     " INNER JOIN " + CONTRACT_SETUP_TABLENAME + " ON " + contractSetupFKCol + " = " + contractSetupPKCol +
                     " WHERE " + segmentFKCol + " = ?" + " AND " + transactionTypeCTCol + " = '" + trxType  + "'" +
                     " AND " + pendingStatusCol + " = 'H' " +
                     " AND " + statusCol + " IN ('N', 'A')" +
                     " AND (" + effectiveDateCol +  " >= ?"  +
                     " AND " + effectiveDateCol + " <= ?)";

        int transactionCount = 0;

        try
        {
            conn = ConnectionFactory.getSingleton().getConnection(POOLNAME);

            ps = conn.prepareStatement(sql);

            ps.setLong(1, segmentFK);
            ps.setDate(2, DBUtil.convertStringToDate(startDate));
            ps.setDate(3, DBUtil.convertStringToDate(endDate));

            rs = ps.executeQuery();

            while (rs.next())
            {
                transactionCount = rs.getInt(1);
            }
        }
        catch(Exception e)
        {
            System.out.println(e);
            e.printStackTrace();
            throw e;
        }
        finally
        {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            if (conn != null) conn.close();
        }

        return transactionCount;
    }

    public EDITBigDecimal accumLS_InterestProceeds(long segmentPK)
    {
        Connection conn = null;
        Statement s = null;
        ResultSet rs = null;

        String editTrxHistoryFKCol = FINANCIAL_HISTORY_DBTABLE.getDBColumn("EDITTrxHistoryFK").getFullyQualifiedColumnName();
        String editTrxHistoryPKCol = EDITTRX_HISTORY_DBTABLE.getDBColumn("EDITTrxHistoryPK").getFullyQualifiedColumnName();
        String editTrxFKCol = EDITTRX_HISTORY_DBTABLE.getDBColumn("EDITTrxFK").getFullyQualifiedColumnName();
        String editTrxPKCol = EDITTRX_DBTABLE.getDBColumn("EDITTrxPK").getFullyQualifiedColumnName();
        String clientSetupFKCol = EDITTRX_DBTABLE.getDBColumn("ClientSetupFK").getFullyQualifiedColumnName();
        String clientSetupPKCol = CLIENT_SETUP_DBTABLE.getDBColumn("ClientSetupPK").getFullyQualifiedColumnName();
        String contractSetupFKCol = CLIENT_SETUP_DBTABLE.getDBColumn("ContractSetupFK").getFullyQualifiedColumnName();
        String contractSetupPKCol = CONTRACT_SETUP_DBTABLE.getDBColumn("ContractSetupPK").getFullyQualifiedColumnName();

        String segmentFKCol = CONTRACT_SETUP_DBTABLE.getDBColumn("SegmentFK").getFullyQualifiedColumnName();

        String transactionTypeCTCol = EDITTRX_DBTABLE.getDBColumn("TransactionTypeCT").getFullyQualifiedColumnName();
        String pendingStatusCol = EDITTRX_DBTABLE.getDBColumn("PendingStatus").getFullyQualifiedColumnName();
        String statusCol = EDITTRX_DBTABLE.getDBColumn("Status").getFullyQualifiedColumnName();
        String interestProceedsCol = FINANCIAL_HISTORY_DBTABLE.getDBColumn("InterestProceeds").getFullyQualifiedColumnName();

        String sql = "SELECT  SUM(" + interestProceedsCol  + ")  FROM " + FINANCIAL_HISTORY_TABLENAME +
                     " INNER JOIN " + EDITTRX_HISTORY_TABLENAME + " ON " + editTrxHistoryFKCol + " = " + editTrxHistoryPKCol +
                     " INNER JOIN " + EDITTRX_TABLENAME + " ON " + editTrxFKCol + " = " + editTrxPKCol +
                     " INNER JOIN " + CLIENT_SETUP_TABLENAME + " ON " + clientSetupFKCol + " = " + clientSetupPKCol +
                     " INNER JOIN " + CONTRACT_SETUP_TABLENAME + " ON " + contractSetupFKCol + " = " + contractSetupPKCol +
                     " WHERE " + segmentFKCol + " = " + segmentPK +
                     " AND " + transactionTypeCTCol + " = '" + EDITTrx.TRANSACTIONTYPECT_LUMPSUM + "'" +
                     " AND " + pendingStatusCol + " = 'H' " +
                     " AND (" + statusCol + " = '" + EDITTrx.STATUS_NATURAL + "' OR " + statusCol + " = '" + EDITTrx.STATUS_APPLY + "')";

        EDITBigDecimal summedIntProceeds = null;

        try
        {
            conn = ConnectionFactory.getSingleton().getConnection(POOLNAME);

            s = conn.createStatement();

            rs = s.executeQuery(sql);

            while (rs.next())
            {
                summedIntProceeds = (new EDITBigDecimal(rs.getBigDecimal(1)));
            }
        }
        catch(Exception e)
        {
            System.out.println(e);
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        finally
        {
            try
            {
                if (rs != null) rs.close();
                if (s != null) s.close();
                if (conn != null) conn.close();
            }
            catch (SQLException e)
            {
                System.out.println(e);
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }

        return summedIntProceeds;
    }

    public EDITBigDecimal accumLS_GrossAmount(long segmentPK)
    {
        Connection conn = null;
        Statement s = null;
        ResultSet rs = null;

        String editTrxHistoryFKCol = FINANCIAL_HISTORY_DBTABLE.getDBColumn("EDITTrxHistoryFK").getFullyQualifiedColumnName();
        String editTrxHistoryPKCol = EDITTRX_HISTORY_DBTABLE.getDBColumn("EDITTrxHistoryPK").getFullyQualifiedColumnName();
        String editTrxFKCol = EDITTRX_HISTORY_DBTABLE.getDBColumn("EDITTrxFK").getFullyQualifiedColumnName();
        String editTrxPKCol = EDITTRX_DBTABLE.getDBColumn("EDITTrxPK").getFullyQualifiedColumnName();
        String clientSetupFKCol = EDITTRX_DBTABLE.getDBColumn("ClientSetupFK").getFullyQualifiedColumnName();
        String clientSetupPKCol = CLIENT_SETUP_DBTABLE.getDBColumn("ClientSetupPK").getFullyQualifiedColumnName();
        String contractSetupFKCol = CLIENT_SETUP_DBTABLE.getDBColumn("ContractSetupFK").getFullyQualifiedColumnName();
        String contractSetupPKCol = CONTRACT_SETUP_DBTABLE.getDBColumn("ContractSetupPK").getFullyQualifiedColumnName();

        String segmentFKCol = CONTRACT_SETUP_DBTABLE.getDBColumn("SegmentFK").getFullyQualifiedColumnName();

        String transactionTypeCTCol = EDITTRX_DBTABLE.getDBColumn("TransactionTypeCT").getFullyQualifiedColumnName();
        String pendingStatusCol = EDITTRX_DBTABLE.getDBColumn("PendingStatus").getFullyQualifiedColumnName();
        String statusCol = EDITTRX_DBTABLE.getDBColumn("Status").getFullyQualifiedColumnName();
        String grossAmountCol = FINANCIAL_HISTORY_DBTABLE.getDBColumn("GrossAmount").getFullyQualifiedColumnName();

        String sql = "SELECT  SUM(" + grossAmountCol  + ")  FROM " + FINANCIAL_HISTORY_TABLENAME +
                     " INNER JOIN " + EDITTRX_HISTORY_TABLENAME + " ON " + editTrxHistoryFKCol + " = " + editTrxHistoryPKCol +
                     " INNER JOIN " + EDITTRX_TABLENAME + " ON " + editTrxFKCol + " = " + editTrxPKCol +
                     " INNER JOIN " + CLIENT_SETUP_TABLENAME + " ON " + clientSetupFKCol + " = " + clientSetupPKCol +
                     " INNER JOIN " + CONTRACT_SETUP_TABLENAME + " ON " + contractSetupFKCol + " = " + contractSetupPKCol +
                     " WHERE " + segmentFKCol + " = " + segmentPK +
                     " AND " + transactionTypeCTCol + " = '" + EDITTrx.TRANSACTIONTYPECT_LUMPSUM + "'" +
                     " AND " + pendingStatusCol + " = 'H' " +
                     " AND (" + statusCol + " = '" + EDITTrx.STATUS_NATURAL + "' OR " + statusCol + " = '" + EDITTrx.STATUS_APPLY + "')";

        EDITBigDecimal summedGrossAmount = null;

        try
        {
            conn = ConnectionFactory.getSingleton().getConnection(POOLNAME);

            s = conn.createStatement();

            rs = s.executeQuery(sql);

            while (rs.next())
            {
                summedGrossAmount = (new EDITBigDecimal(rs.getBigDecimal(1)));
            }
        }
        catch(Exception e)
        {
            System.out.println(e);
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        finally
        {
            try
            {
                if (rs != null) rs.close();
                if (s != null) s.close();
                if (conn != null) conn.close();
            }
            catch (SQLException e)
            {
                System.out.println(e);
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }

        return summedGrossAmount;
    }

    public EDITBigDecimal accumLS_AccumulatedValue(long segmentPK)
    {
        Connection conn = null;
        Statement s = null;
        ResultSet rs = null;

        String editTrxHistoryFKCol = FINANCIAL_HISTORY_DBTABLE.getDBColumn("EDITTrxHistoryFK").getFullyQualifiedColumnName();
        String editTrxHistoryPKCol = EDITTRX_HISTORY_DBTABLE.getDBColumn("EDITTrxHistoryPK").getFullyQualifiedColumnName();
        String editTrxFKCol = EDITTRX_HISTORY_DBTABLE.getDBColumn("EDITTrxFK").getFullyQualifiedColumnName();
        String editTrxPKCol = EDITTRX_DBTABLE.getDBColumn("EDITTrxPK").getFullyQualifiedColumnName();
        String clientSetupFKCol = EDITTRX_DBTABLE.getDBColumn("ClientSetupFK").getFullyQualifiedColumnName();
        String clientSetupPKCol = CLIENT_SETUP_DBTABLE.getDBColumn("ClientSetupPK").getFullyQualifiedColumnName();
        String contractSetupFKCol = CLIENT_SETUP_DBTABLE.getDBColumn("ContractSetupFK").getFullyQualifiedColumnName();
        String contractSetupPKCol = CONTRACT_SETUP_DBTABLE.getDBColumn("ContractSetupPK").getFullyQualifiedColumnName();

        String segmentFKCol = CONTRACT_SETUP_DBTABLE.getDBColumn("SegmentFK").getFullyQualifiedColumnName();

        String transactionTypeCTCol = EDITTRX_DBTABLE.getDBColumn("TransactionTypeCT").getFullyQualifiedColumnName();
        String pendingStatusCol = EDITTRX_DBTABLE.getDBColumn("PendingStatus").getFullyQualifiedColumnName();
        String statusCol = EDITTRX_DBTABLE.getDBColumn("Status").getFullyQualifiedColumnName();
        String accumulatedValueCol = FINANCIAL_HISTORY_DBTABLE.getDBColumn("AccumulatedValue").getFullyQualifiedColumnName();

        String sql = "SELECT  SUM(" + accumulatedValueCol  + ")  FROM " + FINANCIAL_HISTORY_TABLENAME +
                     " INNER JOIN " + EDITTRX_HISTORY_TABLENAME + " ON " + editTrxHistoryFKCol + " = " + editTrxHistoryPKCol +
                     " INNER JOIN " + EDITTRX_TABLENAME + " ON " + editTrxFKCol + " = " + editTrxPKCol +
                     " INNER JOIN " + CLIENT_SETUP_TABLENAME + " ON " + clientSetupFKCol + " = " + clientSetupPKCol +
                     " INNER JOIN " + CONTRACT_SETUP_TABLENAME + " ON " + contractSetupFKCol + " = " + contractSetupPKCol +
                     " WHERE " + segmentFKCol + " = " + segmentPK +
                     " AND " + transactionTypeCTCol + " = '" + EDITTrx.TRANSACTIONTYPECT_LUMPSUM + "'" +
                     " AND " + pendingStatusCol + " = 'H' " +
                     " AND (" + statusCol + " = '" + EDITTrx.STATUS_NATURAL + "' OR " + statusCol + " = '" + EDITTrx.STATUS_APPLY + "')";

        EDITBigDecimal accumulatedValue = null;

        try
        {
            conn = ConnectionFactory.getSingleton().getConnection(POOLNAME);

            s = conn.createStatement();

            rs = s.executeQuery(sql);

            while (rs.next())
            {
                accumulatedValue = (new EDITBigDecimal(rs.getBigDecimal(1)));
            }
        }
        catch(Exception e)
        {
            System.out.println(e);
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        finally
        {
            try
            {
                if (rs != null) rs.close();
                if (s != null) s.close();
                if (conn != null) conn.close();
            }
            catch (SQLException e)
            {
                System.out.println(e);
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }

        return accumulatedValue;
    }


    public static void main(String[] args)     throws Exception
    {
        TransactionAccumsFastDAO tafDAO = new TransactionAccumsFastDAO();

//        EDITBigDecimal  ed = tafDAO.accumPY_PremiumToDate(1101144851215L, "2004/12/02");
//        EDITBigDecimal  ed = tafDAO.accumPY_PremiumToDateForDateRange(1101144851215L, "2003/04/02" ,"2004/12/02");
//        EDITBigDecimal  ed = tafDAO.accumPY_PremiumTypeAndDate(1101144851215L, "2003/04/02" ,"Issue");
//        EDITBigDecimal  ed = tafDAO.accumPY_PremiumTypeAndDate(1101144851215L, "2003/04/02" ,"1035Exchange");
//        EDITBigDecimal  ed = tafDAO.accumWI_WithdrawalsToDate(1101144851215L, "2003/04/02");
//        EDITBigDecimal  ed = tafDAO.accumWI_WithdrawalsToDateForDateRange(1101144851215L, "2003/04/02",  "2003/04/02");
        int  count = tafDAO.trxCountForDateRange(1100784409113L, "2003/04/02",  "2004/12/02", "WI");
//        if (ed != null)
//        {
//            System.out.println("result = " + ed.toString());
//        }
            System.out.println("result = " + count);

    }
}
