/*
 * User: unknown
 * Date: Jan 1, 2000
 * Time: unknown
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package event.dm.dao;

import edit.common.*;
import edit.common.vo.*;
import edit.services.db.*;
import event.ContractSetup;
import event.EDITTrx;
import event.EditTrxKeyAndTranType;
import event.CommissionHistory;
import fission.utility.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FastDAO extends AbstractFastDAO
{
    private final String POOLNAME;

    private final DBTable FINANCIAL_HISTORY_DBTABLE;
    private final DBTable EDITTRX_HISTORY_DBTABLE;
    private final DBTable EDITTRX_DBTABLE;
    private final DBTable CLIENT_SETUP_DBTABLE;
    private final DBTable CONTRACT_SETUP_DBTABLE;
    private final DBTable EDITTRX_CORRESPONDENCE_DBTABLE;
    private final DBTable COMMISSION_HISTORY_DBTABLE;
    private final DBTable BONUS_COMMISSION_HISTORY_DBTABLE;
    private final DBTable REINSURANCE_HISTORY_DBTABLE;
    private final DBTable SUSPENSE_DBTABLE;
    private final DBTable INSUSPENSE_DBTABLE;
    private final DBTable SEGMENT_DBTABLE;
    private final DBTable BUCKET_HISTORY_DBTABLE;
    private final DBTable INVESTMENT_HISTORY_DBTABLE;
    private final DBTable REQUIRED_MIN_DISTRIBUTION_DBTABLE;
    private final DBTable DEPOSITS_DBTABLE;
    private final DBTable CHECK_ADJUSTMENT_DBTABLE;

    private final String FINANCIAL_HISTORY_TABLENAME;
    private final String EDITTRX_HISTORY_TABLENAME;
    private final String EDITTRX_TABLENAME;
    private final String CLIENT_SETUP_TABLENAME;
    private final String CONTRACT_SETUP_TABLENAME;
    private final String EDITTRX_CORRESPONDENCE_TABLENAME;
    private final String COMMISSION_HISTORY_TABLENAME;
    private final String BONUS_COMMISSION_HISTORY_TABLENAME;
    private final String REINSURANCE_HISTORY_TABLENAME;
    private final String SUSPENSE_TABLENAME;    
    private final String INSUSPENSE_TABLENAME;
    private final String SEGMENT_TABLENAME;
    private final String INVESTMENT_HISTORY_TABLENAME;
    private final String REQUIRED_MIN_DISTRIBUTION_TABLENAME;
    private final String DEPOSITS_TABLENAME;
    private final String CHECK_ADJUSTMENT_TABLENAME;

    public FastDAO()
    {
        POOLNAME  = ConnectionFactory.EDITSOLUTIONS_POOL;

        FINANCIAL_HISTORY_DBTABLE      = DBTable.getDBTableForTable("FinancialHistory");
        EDITTRX_HISTORY_DBTABLE        = DBTable.getDBTableForTable("EDITTrxHistory");
        EDITTRX_DBTABLE                = DBTable.getDBTableForTable("EDITTrx");
        CLIENT_SETUP_DBTABLE           = DBTable.getDBTableForTable("ClientSetup");
        CONTRACT_SETUP_DBTABLE         = DBTable.getDBTableForTable("ContractSetup");
        EDITTRX_CORRESPONDENCE_DBTABLE = DBTable.getDBTableForTable("EDITTrxCorrespondence");
        COMMISSION_HISTORY_DBTABLE     = DBTable.getDBTableForTable("CommissionHistory");
        BONUS_COMMISSION_HISTORY_DBTABLE = DBTable.getDBTableForTable("BonusCommissionHistory");
        REINSURANCE_HISTORY_DBTABLE    = DBTable.getDBTableForTable("ReinsuranceHistory");
        SUSPENSE_DBTABLE               = DBTable.getDBTableForTable("Suspense");
        INSUSPENSE_DBTABLE             = DBTable.getDBTableForTable("InSuspense");
        SEGMENT_DBTABLE                = DBTable.getDBTableForTable("Segment");
        BUCKET_HISTORY_DBTABLE         = DBTable.getDBTableForTable("BucketHistory");
        INVESTMENT_HISTORY_DBTABLE     = DBTable.getDBTableForTable("InvestmentHistory");
        REQUIRED_MIN_DISTRIBUTION_DBTABLE = DBTable.getDBTableForTable("RequiredMinDistribution");
        DEPOSITS_DBTABLE                = DBTable.getDBTableForTable("Deposits");
        CHECK_ADJUSTMENT_DBTABLE       = DBTable.getDBTableForTable("CheckAdjustment");

        FINANCIAL_HISTORY_TABLENAME      = FINANCIAL_HISTORY_DBTABLE.getFullyQualifiedTableName();
        EDITTRX_HISTORY_TABLENAME        = EDITTRX_HISTORY_DBTABLE.getFullyQualifiedTableName();
        EDITTRX_TABLENAME                = EDITTRX_DBTABLE.getFullyQualifiedTableName();
        CLIENT_SETUP_TABLENAME           = CLIENT_SETUP_DBTABLE.getFullyQualifiedTableName();
        CONTRACT_SETUP_TABLENAME         = CONTRACT_SETUP_DBTABLE.getFullyQualifiedTableName();
        EDITTRX_CORRESPONDENCE_TABLENAME = EDITTRX_CORRESPONDENCE_DBTABLE.getFullyQualifiedTableName();
        COMMISSION_HISTORY_TABLENAME     = COMMISSION_HISTORY_DBTABLE.getFullyQualifiedTableName();
        BONUS_COMMISSION_HISTORY_TABLENAME = BONUS_COMMISSION_HISTORY_DBTABLE.getFullyQualifiedTableName();
        REINSURANCE_HISTORY_TABLENAME    = REINSURANCE_HISTORY_DBTABLE.getFullyQualifiedTableName();
        SUSPENSE_TABLENAME               = SUSPENSE_DBTABLE.getFullyQualifiedTableName();
        INSUSPENSE_TABLENAME             = INSUSPENSE_DBTABLE.getFullyQualifiedTableName();
        SEGMENT_TABLENAME                = SEGMENT_DBTABLE.getFullyQualifiedTableName();
        INVESTMENT_HISTORY_TABLENAME     = INVESTMENT_HISTORY_DBTABLE.getFullyQualifiedTableName();
        REQUIRED_MIN_DISTRIBUTION_TABLENAME =  REQUIRED_MIN_DISTRIBUTION_DBTABLE.getFullyQualifiedTableName();
        DEPOSITS_TABLENAME               = DEPOSITS_DBTABLE.getFullyQualifiedTableName();
        CHECK_ADJUSTMENT_TABLENAME       = CHECK_ADJUSTMENT_DBTABLE.getFullyQualifiedTableName();
    }


    public boolean findBySegmentPK_AND_ProcessDateLTE(long segmentPK, String processDate) throws Exception
    {
        boolean recordFound = false;

        Connection conn = null;
        ResultSet rs = null;
        PreparedStatement ps = null;

        String accountingPendingStatusCol = EDITTRX_HISTORY_DBTABLE.getDBColumn("AccountingPendingStatus").getFullyQualifiedColumnName();
        String editTrxFKCol               = EDITTRX_HISTORY_DBTABLE.getDBColumn("EDITTrxFK").getFullyQualifiedColumnName();
        String processDateCol             = EDITTRX_HISTORY_DBTABLE.getDBColumn("ProcessDateTime").getFullyQualifiedColumnName();

        String editTrxPKCol               = EDITTRX_DBTABLE.getDBColumn("EDITTrxPK").getFullyQualifiedColumnName();
        String clientSetupFKCol           = EDITTRX_DBTABLE.getDBColumn("ClientSetupFK").getFullyQualifiedColumnName();

        String clientSetupPKCol           = CLIENT_SETUP_DBTABLE.getDBColumn("ClientSetupPK").getFullyQualifiedColumnName();
        String contractSetupFKCol         = CLIENT_SETUP_DBTABLE.getDBColumn("ContractSetupFK").getFullyQualifiedColumnName();

        String contractSetupPKCol         = CONTRACT_SETUP_DBTABLE.getDBColumn("ContractSetupPK").getFullyQualifiedColumnName();
        String segmentFKCol               = CONTRACT_SETUP_DBTABLE.getDBColumn("SegmentFK").getFullyQualifiedColumnName();

        String sql = "SELECT " + accountingPendingStatusCol + " FROM " + EDITTRX_HISTORY_TABLENAME + ", " +
                     EDITTRX_TABLENAME + ", " + CLIENT_SETUP_TABLENAME + ", " + CONTRACT_SETUP_TABLENAME +
                     " WHERE " + editTrxFKCol + " = " + editTrxPKCol +
                     " AND " + clientSetupFKCol + " = " + clientSetupPKCol +
                     " AND " + contractSetupFKCol + " = " + contractSetupPKCol +
                     " AND " + segmentFKCol + " = ?" +
                     " AND " + processDateCol + " <= ?";

        try
        {
            EDITDateTime processDateTime = new EDITDateTime(new EDITDate(processDate), EDITDateTime.DEFAULT_MAX_TIME);

            conn = ConnectionFactory.getSingleton().getConnection(POOLNAME);

            ps = conn.prepareStatement(sql);

            ps.setLong(1, segmentPK);
            ps.setTimestamp(2, DBUtil.convertStringToTimestamp(processDateTime.getFormattedDateTime()));

            rs = ps.executeQuery();

            if (rs.next())
            {
                recordFound = true;
            }
        }
        catch (Exception e)
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

        return recordFound;
    }

    public long[] findDISTINCTSegmentPKByEffectiveDateLTE_AND_PendingStatus(String effectiveDate, String pendingStatus) throws Exception
    {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        String clientSetupFKCol   = EDITTRX_DBTABLE.getDBColumn("ClientSetupFK").getFullyQualifiedColumnName();
        String effectiveDateCol   = EDITTRX_DBTABLE.getDBColumn("EffectiveDate").getFullyQualifiedColumnName();

        String clientSetupPKCol   = CLIENT_SETUP_DBTABLE.getDBColumn("ClientSetupPK").getFullyQualifiedColumnName();
        String contractSetupFKCol = CLIENT_SETUP_DBTABLE.getDBColumn("ContractSetupFK").getFullyQualifiedColumnName();

        String contractSetupPKCol = CONTRACT_SETUP_DBTABLE.getDBColumn("ContractSetupPK").getFullyQualifiedColumnName();
        String segmentFKCol       = CONTRACT_SETUP_DBTABLE.getDBColumn("SegmentFK").getFullyQualifiedColumnName();

        String pendingStatusCol   = EDITTRX_DBTABLE.getDBColumn("PendingStatus").getFullyQualifiedColumnName();

        String sql = "SELECT DISTINCT " + segmentFKCol + " FROM " + EDITTRX_TABLENAME + ", " +
                     CLIENT_SETUP_TABLENAME + ", " + CONTRACT_SETUP_TABLENAME +
                     " WHERE " + clientSetupFKCol + " = " + clientSetupPKCol +
                     " AND " + contractSetupFKCol + " = " + contractSetupPKCol +
                     " AND " + pendingStatusCol + " = ?" +
                     " AND " + effectiveDateCol + " <= ?";

        List segmentPKs = new ArrayList();

        try
        {
            conn = ConnectionFactory.getSingleton().getConnection(POOLNAME);

            ps = conn.prepareStatement(sql);

            ps.setString(1, pendingStatus);
            ps.setDate(2, DBUtil.convertStringToDate(effectiveDate));

            rs = ps.executeQuery();

            while (rs.next())
            {
                segmentPKs.add(new Long(rs.getLong("SegmentFK")));
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

        if (segmentPKs.size() == 0)
        {
            return null;
        }
        else
        {
            return Util.convertLongToPrim((Long[]) segmentPKs.toArray(new Long[segmentPKs.size()]));
        }
    }

    /**
     * Get EDITTrxCorrespondence records by the requested date and Status = "P"
     * @param currentDate
     * @return  array of EDITTrxCorrespondence keys
     * @throws Exception
     */
    public long[] findCorrespondenceByStatusLTDate(String currentDate)  throws Exception
    {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        String correspondenceDateCol = EDITTRX_CORRESPONDENCE_DBTABLE.getDBColumn("CorrespondenceDate").getFullyQualifiedColumnName();
        String statusCol             = EDITTRX_CORRESPONDENCE_DBTABLE.getDBColumn("Status").getFullyQualifiedColumnName();

        String sql = "SELECT * FROM " + EDITTRX_CORRESPONDENCE_TABLENAME +
                     " WHERE " + correspondenceDateCol + " <= ?" +
                     " AND " + statusCol + " = 'P'";

        List editTrxCorrespondencePKs = new ArrayList();

        try
        {
            conn = ConnectionFactory.getSingleton().getConnection(POOLNAME);

            ps = conn.prepareStatement(sql);

            ps.setDate(1, DBUtil.convertStringToDate(currentDate));

            rs = ps.executeQuery();

            while (rs.next())
            {
                editTrxCorrespondencePKs.add(new Long(rs.getLong("EDITTrxCorrespondencePK")));
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

        if (editTrxCorrespondencePKs.size() == 0)
        {
            return null;
        }
        else
        {
            return Util.convertLongToPrim((Long[]) editTrxCorrespondencePKs.toArray(new Long[editTrxCorrespondencePKs.size()]));
        }
    }

    /**
     * Get EDITTrxHistory records for the requested Date and with accountPendingStatus = "Y"
     * @param accountPendingStatus
     * @param processDate
     * @return  array of EDITTrxHistory keys
     * @throws Exception
     */
    public long[] findEDITTrxHistoryPKsByAccountPendingStatus_AND_ProcessDateLTE(String accountPendingStatus, String processDate)  throws Exception
    {
        Connection conn = null;
        ResultSet rs = null;
        PreparedStatement ps = null;

        String editTrxFKCol               = EDITTRX_HISTORY_DBTABLE.getDBColumn("EDITTrxFK").getFullyQualifiedColumnName();
        String accountingPendingStatusCol = EDITTRX_HISTORY_DBTABLE.getDBColumn("AccountingPendingStatus").getFullyQualifiedColumnName();
        String processDateCol             = EDITTRX_HISTORY_DBTABLE.getDBColumn("ProcessDateTime").getFullyQualifiedColumnName();

        String editTrxPKCol               = EDITTRX_DBTABLE.getDBColumn("EDITTrxPK").getFullyQualifiedColumnName();
        String clientSetupFKCol           = EDITTRX_DBTABLE.getDBColumn("ClientSetupFK").getFullyQualifiedColumnName();

        String clientSetupPKCol           = CLIENT_SETUP_DBTABLE.getDBColumn("ClientSetupPK").getFullyQualifiedColumnName();
        String contractSetupFKCol         = CLIENT_SETUP_DBTABLE.getDBColumn("ContractSetupFK").getFullyQualifiedColumnName();

        String contractSetupPKCol         = CONTRACT_SETUP_DBTABLE.getDBColumn("ContractSetupPK").getFullyQualifiedColumnName();

        String sql = "SELECT " + EDITTRX_HISTORY_TABLENAME + ".* FROM " + EDITTRX_HISTORY_TABLENAME + ", " +
                     EDITTRX_TABLENAME + ", " + CLIENT_SETUP_TABLENAME + ", " + CONTRACT_SETUP_TABLENAME +
                     " WHERE " + editTrxFKCol + " = " + editTrxPKCol +
                     " AND " + clientSetupFKCol + " = " + clientSetupPKCol +
                     " AND " + contractSetupFKCol + " = " + contractSetupPKCol +
                     " AND " + accountingPendingStatusCol + " = ?" +
                     " AND " + processDateCol + " <= ?";

        List editTrxHistoryPKs = new ArrayList();

        try
        {
            EDITDateTime processDateTime = new EDITDateTime(new EDITDate(processDate), EDITDateTime.DEFAULT_MAX_TIME);

            conn = ConnectionFactory.getSingleton().getConnection(POOLNAME);

            ps = conn.prepareStatement(sql);

            ps.setString(1, accountPendingStatus);
            ps.setTimestamp(2, DBUtil.convertStringToTimestamp(processDateTime.getFormattedDateTime()));

            rs = ps.executeQuery();

            while (rs.next())
            {
                editTrxHistoryPKs.add(new Long(rs.getLong("EDITTrxHistoryPK")));
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

        if (editTrxHistoryPKs.size() == 0)
        {
            return null;
        }
        else
        {
            return Util.convertLongToPrim((Long[]) editTrxHistoryPKs.toArray(new Long[editTrxHistoryPKs.size()]));
        }
    }
    
    /**
     * Get EDITTrxHistory records for the requested Date and with accountPendingStatus = "Y"
     * @param accountPendingStatus
     * @param processDate
     * @return  array of EDITTrxHistory keys
     * @throws Exception
     */
    public long[] findEDITTrxHistoryPKsForAccounting(String accountPendingStatus, String processDate)  throws Exception
    {
        Connection conn = null;
        ResultSet rs = null;
        PreparedStatement ps = null;

        String editTrxFKCol               = EDITTRX_HISTORY_DBTABLE.getDBColumn("EDITTrxFK").getFullyQualifiedColumnName();
        String accountingPendingStatusCol = EDITTRX_HISTORY_DBTABLE.getDBColumn("AccountingPendingStatus").getFullyQualifiedColumnName();
        String processDateCol             = EDITTRX_HISTORY_DBTABLE.getDBColumn("ProcessDateTime").getFullyQualifiedColumnName();

        String editTrxPKCol               = EDITTRX_DBTABLE.getDBColumn("EDITTrxPK").getFullyQualifiedColumnName();
        String clientSetupFKCol           = EDITTRX_DBTABLE.getDBColumn("ClientSetupFK").getFullyQualifiedColumnName();
        String transactionType			  = EDITTRX_DBTABLE.getDBColumn("TransactionTypeCT").getFullyQualifiedColumnName();

        String clientSetupPKCol           = CLIENT_SETUP_DBTABLE.getDBColumn("ClientSetupPK").getFullyQualifiedColumnName();
        String contractSetupFKCol         = CLIENT_SETUP_DBTABLE.getDBColumn("ContractSetupFK").getFullyQualifiedColumnName();

        String contractSetupPKCol         = CONTRACT_SETUP_DBTABLE.getDBColumn("ContractSetupPK").getFullyQualifiedColumnName();

        String sql = "SELECT " + EDITTRX_HISTORY_TABLENAME + ".* FROM " + EDITTRX_HISTORY_TABLENAME + ", " +
                     EDITTRX_TABLENAME + ", " + CLIENT_SETUP_TABLENAME + ", " + CONTRACT_SETUP_TABLENAME +
                     " WHERE " + editTrxFKCol + " = " + editTrxPKCol +
                     " AND " + clientSetupFKCol + " = " + clientSetupPKCol +
                     " AND " + contractSetupFKCol + " = " + contractSetupPKCol +
                     " AND " + accountingPendingStatusCol + " = ?" +
                     " AND " + processDateCol + " <= ? " + 
                     " AND " + transactionType + " NOT IN ('CK', 'BCK') ";

        List<Long> editTrxHistoryPKs = new ArrayList<>();

        try
        {
            EDITDateTime processDateTime = new EDITDateTime(new EDITDate(processDate), EDITDateTime.DEFAULT_MAX_TIME);

            conn = ConnectionFactory.getSingleton().getConnection(POOLNAME);

            ps = conn.prepareStatement(sql);

            ps.setString(1, accountPendingStatus);
            ps.setTimestamp(2, DBUtil.convertStringToTimestamp(processDateTime.getFormattedDateTime()));

            rs = ps.executeQuery();

            while (rs.next())
            {
                editTrxHistoryPKs.add(new Long(rs.getLong("EDITTrxHistoryPK")));
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

        if (editTrxHistoryPKs.size() == 0)
        {
            return null;
        }
        else
        {
            return Util.convertLongToPrim((Long[]) editTrxHistoryPKs.toArray(new Long[editTrxHistoryPKs.size()]));
        }
    }

    /**
     * Get EDITTrxHistory Primary Keys for the requested investment
     * @param accountPendingStatus
     * @param processDate
     * @return  array of EDITTrxHistory keys
     * @throws Exception
     */
    public long[] findEDITTrxHistoryPKsByInvestment(long filteredFundFK)  throws Exception
    {
        Connection conn = null;
        ResultSet rs = null;
        PreparedStatement ps = null;

        DBTable investmentDBTable = DBTable.getDBTableForTable("Investment");
        DBTable investmentHistoryDBTable = DBTable.getDBTableForTable("InvestmentHistory");

        String investmentTable = investmentDBTable.getFullyQualifiedTableName();
        String investmentHistoryTable = investmentHistoryDBTable.getFullyQualifiedTableName();

        String editTrxHistoryPKCol = EDITTRX_HISTORY_DBTABLE.getDBColumn("EDITTrxHistoryPK").getFullyQualifiedColumnName();

        String editTrxHistoryFKCol = investmentHistoryDBTable.getDBColumn("EDITTrxHistoryFK").getFullyQualifiedColumnName();
        String investmentFKCol = investmentHistoryDBTable.getDBColumn("InvestmentFK").getFullyQualifiedColumnName();

        String investmentPKCol = investmentDBTable.getDBColumn("InvestmentPK").getFullyQualifiedColumnName();
        String filteredFundFKCol = investmentDBTable.getDBColumn("FilteredFundFK").getFullyQualifiedColumnName();

        String sql = "SELECT DISTINCT " + editTrxHistoryPKCol + " FROM " + EDITTRX_HISTORY_TABLENAME + ", " +
                     investmentHistoryTable + ", " + investmentTable +
                     " WHERE " + filteredFundFKCol + " = ?" +
                     " AND " + investmentPKCol + " = " + investmentFKCol +
                     " AND " + editTrxHistoryFKCol + " = " + editTrxHistoryPKCol;

        List editTrxHistoryPKs = new ArrayList();

        try
        {
            conn = ConnectionFactory.getSingleton().getConnection(POOLNAME);

            ps = conn.prepareStatement(sql);

            ps.setLong(1, filteredFundFK);

            rs = ps.executeQuery();

            while (rs.next())
            {
                editTrxHistoryPKs.add(new Long(rs.getLong("EDITTrxHistoryPK")));
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

        if (editTrxHistoryPKs.size() == 0)
        {
            return null;
        }
        else
        {
            return Util.convertLongToPrim((Long[]) editTrxHistoryPKs.toArray(new Long[editTrxHistoryPKs.size()]));
        }
    }


    /**
     * Get CommissionHistory records for the requested Date and with accountPendingStatus = "Y"
     * @param accountPendingStatus
     * @param processDate
     * @return  array of CommissionHistory keys
     * @throws Exception
     */
    public long[] findCommissionHistoryPKsByByAccountPendingStatus_AND_ProcessDateLTE(String accountPendingStatus, String processDate)  throws Exception
    {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        String accountingPendingStatusCol = COMMISSION_HISTORY_DBTABLE.getDBColumn("AccountingPendingStatus").getFullyQualifiedColumnName();
        String editTrxHistoryFKCol        = COMMISSION_HISTORY_DBTABLE.getDBColumn("EDITTrxHistoryFK").getFullyQualifiedColumnName();

        String editTrxHistoryPKCol        = EDITTRX_HISTORY_DBTABLE.getDBColumn("EDITTrxHistoryPK").getFullyQualifiedColumnName();
        String processDateCol             = EDITTRX_HISTORY_DBTABLE.getDBColumn("ProcessDateTime").getFullyQualifiedColumnName();

        String sql = "SELECT * FROM " + COMMISSION_HISTORY_TABLENAME +
                     " WHERE " + accountingPendingStatusCol + " = ?" +
                     " AND " + editTrxHistoryFKCol + " IN (SELECT " + editTrxHistoryPKCol + " FROM " + EDITTRX_HISTORY_TABLENAME +
                     " WHERE " + processDateCol + " <= ?)";

        List commissionHistoryPKs = new ArrayList();

        try
        {
            EDITDateTime processDateTime = new EDITDateTime(new EDITDate(processDate), EDITDateTime.DEFAULT_MAX_TIME);
             conn = ConnectionFactory.getSingleton().getConnection(POOLNAME);

            ps = conn.prepareStatement(sql);

            ps.setString(1, accountPendingStatus);
            ps.setTimestamp(2, DBUtil.convertStringToTimestamp(processDateTime.getFormattedDateTime()));

            rs = ps.executeQuery();

            while (rs.next())
            {
                commissionHistoryPKs.add(new Long(rs.getLong("CommissionHistoryPK")));
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

        if (commissionHistoryPKs.size() == 0)
        {
            return null;
        }
        else
        {
            return Util.convertLongToPrim((Long[]) commissionHistoryPKs.toArray(new Long[commissionHistoryPKs.size()]));
        }
    }

    /**
     * Get BonusCommissionHistory records for the requested Date and with accountPendingStatus = "Y"
     * @param accountPendingStatus
     * @param processDate
     * @return  array of BonusCommissionHistory keys
     * @throws Exception
     */
    public long[] findBonusCommissionHistoryPKsByByAccountPendingStatus_AND_ProcessDateLTE(String accountPendingStatus, String processDate)  throws Exception
    {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        String accountingPendingStatusCol = BONUS_COMMISSION_HISTORY_DBTABLE.getDBColumn("AccountingPendingStatus").getFullyQualifiedColumnName();
        String commissionHistoryFKCol = BONUS_COMMISSION_HISTORY_DBTABLE.getDBColumn("CommissionHistoryFK").getFullyQualifiedColumnName();

        String commissionHistoryPKCol = COMMISSION_HISTORY_DBTABLE.getDBColumn("CommissionHistoryPK").getFullyQualifiedColumnName();
        String editTrxHistoryFKCol = COMMISSION_HISTORY_DBTABLE.getDBColumn("EDITTrxHistoryFK").getFullyQualifiedColumnName();

        String editTrxHistoryPKCol        = EDITTRX_HISTORY_DBTABLE.getDBColumn("EDITTrxHistoryPK").getFullyQualifiedColumnName();
        String processDateCol             = EDITTRX_HISTORY_DBTABLE.getDBColumn("ProcessDateTime").getFullyQualifiedColumnName();

        String sql = "SELECT " + BONUS_COMMISSION_HISTORY_TABLENAME + ".* FROM " + BONUS_COMMISSION_HISTORY_TABLENAME + ", " + COMMISSION_HISTORY_TABLENAME +
                     " WHERE " + accountingPendingStatusCol + " = ?" +
                     " AND " + commissionHistoryFKCol + " = " + commissionHistoryPKCol +
                     " AND " + editTrxHistoryFKCol + " IN (SELECT " + editTrxHistoryPKCol + " FROM " + EDITTRX_HISTORY_TABLENAME +
                     " WHERE " + processDateCol + " <= ?)";

        List bonusCommissionHistoryPKs = new ArrayList();

        try
        {
            EDITDateTime processDateTime = new EDITDateTime(new EDITDate(processDate), EDITDateTime.DEFAULT_MAX_TIME);
             conn = ConnectionFactory.getSingleton().getConnection(POOLNAME);

            ps = conn.prepareStatement(sql);

            ps.setString(1, accountPendingStatus);
            ps.setTimestamp(2, DBUtil.convertStringToTimestamp(processDateTime.getFormattedDateTime()));

            rs = ps.executeQuery();

            while (rs.next())
            {
                bonusCommissionHistoryPKs.add(new Long(rs.getLong("BonusCommissionHistoryPK")));
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

        if (bonusCommissionHistoryPKs.size() == 0)
        {
            return null;
        }
        else
        {
            return Util.convertLongToPrim((Long[]) bonusCommissionHistoryPKs.toArray(new Long[bonusCommissionHistoryPKs.size()]));
        }
    }

    /**
     * Get ReinsuranceHistory records for the requested Date and with accountPendingStatus = "Y"
     * @param accountPendingStatus
     * @param processDate
     * @return  array of ReinsuranceHistory keys
     * @throws Exception
     */
    public long[] findReinsuranceHistoryPKsByByAccountPendingStatus(String accountPendingStatus)  throws Exception
    {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        String accountingPendingStatusCol = REINSURANCE_HISTORY_DBTABLE.getDBColumn("AccountingPendingStatus").getFullyQualifiedColumnName();


        String sql = "SELECT * FROM " + REINSURANCE_HISTORY_TABLENAME +
                     " WHERE " + accountingPendingStatusCol + " = ?";

        List reinsuranceHistoryPKs = new ArrayList();

        try
        {
            conn = ConnectionFactory.getSingleton().getConnection(POOLNAME);

            ps = conn.prepareStatement(sql);

            ps.setString(1, accountPendingStatus);

            rs = ps.executeQuery();

            while (rs.next())
            {
                reinsuranceHistoryPKs.add(new Long(rs.getLong("ReinsuranceHistoryPK")));
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

        if (reinsuranceHistoryPKs.size() == 0)
        {
            return null;
        }
        else
        {
            return Util.convertLongToPrim((Long[]) reinsuranceHistoryPKs.toArray(new Long[reinsuranceHistoryPKs.size()]));
        }
    }

    /**
     * Get Suspense records for the requested Date and with accountingPendingStatus = "Y"
     * @param processDate
     * @return array of Suspense keys
     * @throws Exception
     */
    public long[] findAccountingPendingSuspenseEntries(String processDate)  throws Exception
    {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        String processDateCol          = SUSPENSE_DBTABLE.getDBColumn("ProcessDate").getFullyQualifiedColumnName();
        String accountingPendingIndCol = SUSPENSE_DBTABLE.getDBColumn("AccountingPendingInd").getFullyQualifiedColumnName();
        String originalAmount  		   = SUSPENSE_DBTABLE.getDBColumn("OriginalAmount").getFullyQualifiedColumnName();

        String sql = "SELECT * FROM " + SUSPENSE_TABLENAME +
                     " WHERE " + processDateCol + " <= ?" +
                     " AND " + accountingPendingIndCol + " = 'Y'" +
                     " AND " + originalAmount + " > 0";

        List suspensePKs = new ArrayList();

        try
        {
            EDITDateTime processDateTime = new EDITDateTime(new EDITDate(processDate), EDITDateTime.DEFAULT_MAX_TIME);

            conn = ConnectionFactory.getSingleton().getConnection(POOLNAME);

            ps = conn.prepareStatement(sql);

            ps.setTimestamp(1, DBUtil.convertStringToTimestamp(processDateTime.getFormattedDateTime()));

            rs = ps.executeQuery();

            while (rs.next())
            {
                suspensePKs.add(new Long(rs.getLong("SuspensePK")));
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

        if (suspensePKs.size() == 0)
        {
            return null;
        }
        else
        {
            return Util.convertLongToPrim((Long[]) suspensePKs.toArray(new Long[suspensePKs.size()]));
        }
    }

    /**
     * Get all InSuspense records
     * @return array of Suspense foreign keys for the InSuspense records
     * @throws Exception
     */
    public long[] findAllSuspensePKs()  throws Exception
    {
        Connection conn = null;
        Statement s = null;
        ResultSet rs = null;

        String sql = "SELECT * FROM " + SUSPENSE_TABLENAME;

        List suspensePKs = new ArrayList();

        try
        {
            conn = ConnectionFactory.getSingleton().getConnection(POOLNAME);

            s = conn.createStatement();

            rs = s.executeQuery(sql);

            while (rs.next())
            {
                suspensePKs.add(new Long(rs.getLong("SuspensePK")));
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
            if (s != null) s.close();
            if (conn != null) conn.close();
        }

        if (suspensePKs.size() == 0)
        {
            return null;
        }
        else
        {
            return Util.convertLongToPrim((Long[]) suspensePKs.toArray(new Long[suspensePKs.size()]));
        }
    }
    
    /**
     * Get all Suspense records applicable to the daily batch Bank job
     * @return array of Suspense primary keys
     * @throws Exception
     */
    public long[] findSuspensePKsForBankExtract()  throws Exception
    {
        Connection conn = null;
        Statement s = null;
        ResultSet rs = null;

        String editTrxHistoryPKCol 		  = EDITTRX_HISTORY_DBTABLE.getDBColumn("EDITTrxHistoryPK").getFullyQualifiedColumnName();
        String editTrxFKCol               = EDITTRX_HISTORY_DBTABLE.getDBColumn("EDITTrxFK").getFullyQualifiedColumnName();

        String editTrxPKCol               = EDITTRX_DBTABLE.getDBColumn("EDITTrxPK").getFullyQualifiedColumnName();
        String editTrxStatusCol           = EDITTRX_DBTABLE.getDBColumn("Status").getFullyQualifiedColumnName();

        String suspensePKCol           	  = SUSPENSE_DBTABLE.getDBColumn("SuspensePK").getFullyQualifiedColumnName();
        String suspenseDirectionCol       = SUSPENSE_DBTABLE.getDBColumn("DirectionCT").getFullyQualifiedColumnName();
        String suspenseDisbursementCol    = SUSPENSE_DBTABLE.getDBColumn("DisbursementSourceCT").getFullyQualifiedColumnName();
        String suspenseMaintenanceIndCol  = SUSPENSE_DBTABLE.getDBColumn("MaintenanceInd").getFullyQualifiedColumnName();
        
        String insuspensePKCol			  = INSUSPENSE_DBTABLE.getDBColumn("InSuspensePK").getFullyQualifiedColumnName();
        String suspenseFKCol              = INSUSPENSE_DBTABLE.getDBColumn("SuspenseFK").getFullyQualifiedColumnName();
        String editTrxHistoryFKCol		  = INSUSPENSE_DBTABLE.getDBColumn("EDITTrxHistoryFK").getFullyQualifiedColumnName();

        String financialEDITHistoryFKCol  = FINANCIAL_HISTORY_DBTABLE.getDBColumn("EDITTrxHistoryFK").getFullyQualifiedColumnName();
        String financialDisbursementCol   = FINANCIAL_HISTORY_DBTABLE.getDBColumn("DisbursementSourceCT").getFullyQualifiedColumnName();
        
        String sql = "SELECT " + SUSPENSE_TABLENAME + ".* FROM " + SUSPENSE_TABLENAME + 
        		" LEFT JOIN " + INSUSPENSE_TABLENAME + " ON " + suspensePKCol + " = " + suspenseFKCol +
        		" LEFT JOIN " + EDITTRX_HISTORY_TABLENAME + " ON " + editTrxHistoryFKCol + " = " + editTrxHistoryPKCol +
        		" LEFT JOIN " + EDITTRX_TABLENAME + " ON " + editTrxFKCol + " = " + editTrxPKCol +
        		" LEFT JOIN " + FINANCIAL_HISTORY_TABLENAME + " ON " + editTrxHistoryPKCol + " = " + financialEDITHistoryFKCol +
                " WHERE " + suspenseMaintenanceIndCol + " NOT IN ('R', 'D')" +
                " AND (" +
                		"(" + suspenseDirectionCol + "='Apply' AND " + editTrxStatusCol + "='R' AND (" + suspenseDisbursementCol + " IN ('Check', 'EFT') OR " + financialDisbursementCol + " IN ('Check', 'EFT'))) OR " +
                		"(" + suspenseDirectionCol + "='Remove' AND " + editTrxStatusCol + " IN ('N', 'A')) OR " +
                		"(" + suspenseDirectionCol + "='Remove' AND " + insuspensePKCol + " IS NULL)" +
                	  ")";
        
        List suspensePKs = new ArrayList();

        try
        {
            conn = ConnectionFactory.getSingleton().getConnection(POOLNAME);

            s = conn.createStatement();

            rs = s.executeQuery(sql);

            while (rs.next())
            {
                suspensePKs.add(new Long(rs.getLong("SuspensePK")));
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
            if (s != null) s.close();
            if (conn != null) conn.close();
        }

        if (suspensePKs.size() == 0)
        {
            return null;
        }
        else
        {
            return Util.convertLongToPrim((Long[]) suspensePKs.toArray(new Long[suspensePKs.size()]));
        }
    }


    /**
     * Get CommissionHistory records for the updateStatuses = "U" and "L" and for Segments which do NOT have Segment.SegmentStatusCT of
     * the specified SegmentStatusCT and CommissionHistory records not related to any segment
     * Modified to get CommissionHistory records for the updateStatuses = "L" and CommHoldReleaseDate is prior to current date.
     * <b>Note: The order of statuses should be "U" and "L" other wise the query would give incorrect results. The reason the order
     * is specifie because for updateStaus = "L" need to compared the CommHoldReleaseDate also.</b>
     * @param updateStatuses
     * @param segmentStatusCT
     * @return array of CommissionHistory keys
     */
    public long[] findCommissionHistoryPKsByUpdateStatus_NOT_SegmentStatusCT(String[] updateStatuses, String segmentStatusCT)
    {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        // CommissionHistory
        String editTrxHistoryFKCol = COMMISSION_HISTORY_DBTABLE.getDBColumn("EDITTrxHistoryFK").getFullyQualifiedColumnName();
        String updateStatusCol = COMMISSION_HISTORY_DBTABLE.getDBColumn("UpdateStatus").getFullyQualifiedColumnName();
        String commissionHistoryPKCol = COMMISSION_HISTORY_DBTABLE.getDBColumn("CommissionHistoryPK").getFullyQualifiedColumnName();
        String commHoldReleaseDateCol = COMMISSION_HISTORY_DBTABLE.getDBColumn("CommHoldReleaseDate").getFullyQualifiedColumnName();

        // EDITTrxHistory
        String editTrxHistoryPKCol = EDITTRX_HISTORY_DBTABLE.getDBColumn("EDITTrxHistoryPK").getFullyQualifiedColumnName();
        String editTrxFKCol = EDITTRX_HISTORY_DBTABLE.getDBColumn("EDITTrxFK").getFullyQualifiedColumnName();

        // EDITTrx
        String editTrxPKCol = EDITTRX_DBTABLE.getDBColumn("EDITTrxPK").getFullyQualifiedColumnName();
        String clientSetupFKCol = EDITTRX_DBTABLE.getDBColumn("ClientSetupFK").getFullyQualifiedColumnName();

        // ClientSetup
        String clientSetupPKCol = CLIENT_SETUP_DBTABLE.getDBColumn("ClientSetupPK").getFullyQualifiedColumnName();
        String contractSetupFKCol = CLIENT_SETUP_DBTABLE.getDBColumn("ContractSetupFK").getFullyQualifiedColumnName();

        // ContractSetup
        String contractSetupPKCol = CONTRACT_SETUP_DBTABLE.getDBColumn("ContractSetupPK").getFullyQualifiedColumnName();
        String segmentFKCol = CONTRACT_SETUP_DBTABLE.getDBColumn("SegmentFK").getFullyQualifiedColumnName();

        // Segment
        String segmentPKCol = SEGMENT_DBTABLE.getDBColumn("SegmentPK").getFullyQualifiedColumnName();
        String segmentStatusCTCol = SEGMENT_DBTABLE.getDBColumn("SegmentStatusCT").getFullyQualifiedColumnName();

        String sql = " SELECT " + commissionHistoryPKCol +
                     " FROM " + COMMISSION_HISTORY_TABLENAME +
                     " INNER JOIN " + EDITTRX_HISTORY_TABLENAME +
                     " ON " + editTrxHistoryFKCol + " = " + editTrxHistoryPKCol +
                     " INNER JOIN " + EDITTRX_TABLENAME +
                     " ON " + editTrxFKCol + " = " + editTrxPKCol +
                     " INNER JOIN " + CLIENT_SETUP_TABLENAME +
                     " ON " + clientSetupFKCol + " = " + clientSetupPKCol +
                     " INNER JOIN " + CONTRACT_SETUP_TABLENAME +
                     " ON " + contractSetupFKCol + " = " + contractSetupPKCol +
                     " INNER JOIN " + SEGMENT_TABLENAME +
                     " ON " + segmentFKCol + " = " + segmentPKCol +
                     " WHERE (" + updateStatusCol + " = ?" +
                     " OR (" + updateStatusCol + " = ?" + " AND " + commHoldReleaseDateCol + " <= ?" + "))" +
                     " AND " + segmentStatusCTCol + " != ?" +
                     " UNION " +
                     " SELECT " + commissionHistoryPKCol +
                     " FROM " + COMMISSION_HISTORY_TABLENAME +
                     " INNER JOIN " + EDITTRX_HISTORY_TABLENAME +
                     " ON " + editTrxHistoryFKCol + " = " + editTrxHistoryPKCol +
                     " INNER JOIN " + EDITTRX_TABLENAME +
                     " ON " + editTrxFKCol + " = " + editTrxPKCol +
                     " INNER JOIN " + CLIENT_SETUP_TABLENAME +
                     " ON " + clientSetupFKCol + " = " + clientSetupPKCol +
                     " INNER JOIN " + CONTRACT_SETUP_TABLENAME +
                     " ON " + contractSetupFKCol + " = " + contractSetupPKCol +
                     " WHERE (" + updateStatusCol + " = ?" +
                     " OR (" + updateStatusCol + " = ?" + " AND " + commHoldReleaseDateCol + " <= ?" + "))" +
                     " AND " + segmentFKCol + " IS NULL";

        List commissionHistoryPKs = new ArrayList();

        try
        {
            conn = ConnectionFactory.getSingleton().getConnection(POOLNAME);

            ps = conn.prepareStatement(sql);

            EDITDate currentDate = new EDITDate();

            ps.setString(1, updateStatuses[0]);
            ps.setString(2, updateStatuses[1]);
            ps.setDate(3, DBUtil.convertStringToDate(currentDate.getFormattedDate()));
            ps.setString(4, segmentStatusCT);
            ps.setString(5, updateStatuses[0]);
            ps.setString(6, updateStatuses[1]);
            ps.setDate(7, DBUtil.convertStringToDate(currentDate.getFormattedDate()));

            rs = ps.executeQuery();

            while (rs.next())
            {
                commissionHistoryPKs.add(new Long(rs.getLong("CommissionHistoryPK")));
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
                if (ps != null) ps.close();
                if (conn != null) conn.close();
            }
            catch(Exception e)
            {
                System.out.println(e);

                e.printStackTrace();

                throw new RuntimeException(e);
            }
        }

        if (commissionHistoryPKs.size() == 0)
        {
            return null;
        }
        else
        {
            return Util.convertLongToPrim((Long[]) commissionHistoryPKs.toArray(new Long[commissionHistoryPKs.size()]));
        }
    }

    /**
     * Get CommissionHistory records for the updateStatus = "U"
     * @param updateStatus
     * @return array of CommissionHistory keys
     * @throws Exception
     */
    public long[] findCommissionHistoryPKsByUpdateStatus(String updateStatus)  throws Exception
    {
        Connection conn = null;
        Statement s = null;
        ResultSet rs = null;

        String updateStatusCol = COMMISSION_HISTORY_DBTABLE.getDBColumn("UpdateStatus").getFullyQualifiedColumnName();

        String sql = "SELECT * FROM " + COMMISSION_HISTORY_TABLENAME +
                     " WHERE " + updateStatusCol + " = '" + updateStatus + "'";

        List commissionHistoryPKs = new ArrayList();

        try
        {
            conn = ConnectionFactory.getSingleton().getConnection(POOLNAME);

            s = conn.createStatement();

            rs = s.executeQuery(sql);

            while (rs.next())
            {
                commissionHistoryPKs.add(new Long(rs.getLong("CommissionHistoryPK")));
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
            if (s != null) s.close();
            if (conn != null) conn.close();
        }

        if (commissionHistoryPKs.size() == 0)
        {
            return null;
        }
        else
        {
            return Util.convertLongToPrim((Long[]) commissionHistoryPKs.toArray(new Long[commissionHistoryPKs.size()]));
        }
    }

    /**
     * Get the keys of all the EDITTrx records that have an EDITTrxHistory process date between the start and end dates.
     * That are premium, withdrawal, not taken or full surrender trxs with a status of "N" or "R" and a pending status of "H".
     * @param startDate
     * @param endDate
     * @param segmentFK
     * @return array of EDITTrxPKs
     * @throws Exception
     */
    public long[] findPremTrxByDateRangeSegmentPK(String startDate, String endDate, long segmentFK) throws Exception
    {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        String editTrxFKCol               = EDITTRX_HISTORY_DBTABLE.getDBColumn("EDITTrxFK").getFullyQualifiedColumnName();
        String originalProcessDateTimeCol = EDITTRX_HISTORY_DBTABLE.getDBColumn("OriginalProcessDateTime").getFullyQualifiedColumnName();
        String processDateCol             = EDITTRX_HISTORY_DBTABLE.getDBColumn("ProcessDateTime").getFullyQualifiedColumnName();

        String editTrxPKCol               = EDITTRX_DBTABLE.getDBColumn("EDITTrxPK").getFullyQualifiedColumnName();
        String clientSetupFKCol           = EDITTRX_DBTABLE.getDBColumn("ClientSetupFK").getFullyQualifiedColumnName();
        String transactionTypeCTCol       = EDITTRX_DBTABLE.getDBColumn("TransactionTypeCT").getFullyQualifiedColumnName();
        String pendingStatusCol           = EDITTRX_DBTABLE.getDBColumn("PendingStatus").getFullyQualifiedColumnName();
        String statusCol                  = EDITTRX_DBTABLE.getDBColumn("Status").getFullyQualifiedColumnName();

        String clientSetupPKCol           = CLIENT_SETUP_DBTABLE.getDBColumn("ClientSetupPK").getFullyQualifiedColumnName();
        String contractSetupFKCol         = CLIENT_SETUP_DBTABLE.getDBColumn("ContractSetupFK").getFullyQualifiedColumnName();

        String contractSetupPKCol         = CONTRACT_SETUP_DBTABLE.getDBColumn("ContractSetupPK").getFullyQualifiedColumnName();
        String segmentFKCol               = CONTRACT_SETUP_DBTABLE.getDBColumn("SegmentFK").getFullyQualifiedColumnName();

        String sql = "SELECT DISTINCT " + editTrxPKCol + " FROM " + EDITTRX_TABLENAME + ", " +
                     EDITTRX_HISTORY_TABLENAME + ", " + CLIENT_SETUP_TABLENAME + ", " + CONTRACT_SETUP_TABLENAME +
                     " WHERE " + editTrxFKCol + " = " + editTrxPKCol +
                     " AND " + clientSetupFKCol + " = " + clientSetupPKCol +
                     " AND " + contractSetupFKCol + " = " + contractSetupPKCol +
                     " AND " + segmentFKCol + " = " + segmentFK +
                     " AND " + processDateCol + " >= ?" +
                     " AND " + processDateCol + " <= ?" +
                     " AND (" + transactionTypeCTCol + " <> 'IS'" +
                     " AND " + pendingStatusCol + " = 'H'" +
                     " AND (" + statusCol + " = 'N' OR " + statusCol + " = 'A'" +
//                     " OR ((" + statusCol + " = 'R' OR " + statusCol + " = 'U') AND (SUBSTRING(" + originalProcessDateTimeCol + ", 1, 10) < '" + startDate + "'" +
//                     " OR SUBSTRING(" + originalProcessDateTimeCol + ", 1, 10) > '" + endDate + "'))))";
                     " OR ((" + statusCol + " = 'R' OR " + statusCol + " = 'U') AND " + originalProcessDateTimeCol + " < ?" +
                     " OR " + originalProcessDateTimeCol + " > ?)))";

        List editTrxPKs = new ArrayList();

        try
        {
            EDITDateTime startDateTime = new EDITDateTime(new EDITDate(startDate), EDITDateTime.DEFAULT_MIN_TIME);
            EDITDateTime endDateTime = new EDITDateTime(new EDITDate(endDate), EDITDateTime.DEFAULT_MAX_TIME);

            conn = ConnectionFactory.getSingleton().getConnection(POOLNAME);

            ps = conn.prepareStatement(sql);

            ps.setTimestamp(1, DBUtil.convertStringToTimestamp(startDateTime.getFormattedDateTime()));
            ps.setTimestamp(2, DBUtil.convertStringToTimestamp(endDateTime.getFormattedDateTime()));
            ps.setTimestamp(3, DBUtil.convertStringToTimestamp(startDateTime.getFormattedDateTime()));
            ps.setTimestamp(4, DBUtil.convertStringToTimestamp(endDateTime.getFormattedDateTime()));

            rs = ps.executeQuery();

            while (rs.next())
            {
                editTrxPKs.add(new Long(rs.getLong("EDITTrxPK")));
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

        if (editTrxPKs.size() == 0)
        {
            return null;
        }
        else
        {
            return Util.convertLongToPrim((Long[]) editTrxPKs.toArray(new Long[editTrxPKs.size()]));
        }
    }

    /**
     * Get the keys of all the Segment records that have an EDITTrxHistory process date between the start and end dates.
     * That are premium, withdrawal, not taken or full surrender trxs with a status of "N" or "R" and a pending status of "H"
     * for a selected product structure.
     * @param startDate
     * @param endDate
     * @param productStructureFK
     * @return
     * @throws Exception
     */
    public long[] getUniqueSegmentPKsByDateRangeAndProductStructure(String startDate, String endDate, long productStructureFK) throws Exception
    {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        String editTrxFKCol               = EDITTRX_HISTORY_DBTABLE.getDBColumn("EDITTrxFK").getFullyQualifiedColumnName();
        String originalProcessDateTimeCol = EDITTRX_HISTORY_DBTABLE.getDBColumn("OriginalProcessDateTime").getFullyQualifiedColumnName();
        String processDateCol             = EDITTRX_HISTORY_DBTABLE.getDBColumn("ProcessDateTime").getFullyQualifiedColumnName();

        String editTrxPKCol               = EDITTRX_DBTABLE.getDBColumn("EDITTrxPK").getFullyQualifiedColumnName();
        String clientSetupFKCol           = EDITTRX_DBTABLE.getDBColumn("ClientSetupFK").getFullyQualifiedColumnName();
        String transactionTypeCTCol       = EDITTRX_DBTABLE.getDBColumn("TransactionTypeCT").getFullyQualifiedColumnName();
        String pendingStatusCol           = EDITTRX_DBTABLE.getDBColumn("PendingStatus").getFullyQualifiedColumnName();
        String statusCol                  = EDITTRX_DBTABLE.getDBColumn("Status").getFullyQualifiedColumnName();

        String clientSetupPKCol           = CLIENT_SETUP_DBTABLE.getDBColumn("ClientSetupPK").getFullyQualifiedColumnName();
        String contractSetupFKCol         = CLIENT_SETUP_DBTABLE.getDBColumn("ContractSetupFK").getFullyQualifiedColumnName();

        String contractSetupPKCol         = CONTRACT_SETUP_DBTABLE.getDBColumn("ContractSetupPK").getFullyQualifiedColumnName();
        String segmentFKCol               = CONTRACT_SETUP_DBTABLE.getDBColumn("SegmentFK").getFullyQualifiedColumnName();

        String segmentPKCol               = SEGMENT_DBTABLE.getDBColumn("SegmentPK").getFullyQualifiedColumnName();
        String productStructureFKCol      = SEGMENT_DBTABLE.getDBColumn("ProductStructureFK").getFullyQualifiedColumnName();

        String sql = "SELECT DISTINCT " + segmentFKCol + " FROM " + EDITTRX_TABLENAME + ", " +
                     EDITTRX_HISTORY_TABLENAME + ", " + CLIENT_SETUP_TABLENAME + ", " + CONTRACT_SETUP_TABLENAME +
                     ", " + SEGMENT_TABLENAME +
                     " WHERE " + editTrxFKCol + " = " + editTrxPKCol +
                     " AND " + clientSetupFKCol + " = " + clientSetupPKCol +
                     " AND " + contractSetupFKCol + " = " + contractSetupPKCol +
                     " AND " + segmentFKCol + " = " + segmentPKCol +
                     " AND " + productStructureFKCol + " = ?" +
                     " AND " + processDateCol + " >= ?" +
                     " AND " + processDateCol + " <= ?" +
                     " AND (" + transactionTypeCTCol + " <> 'IS'" +
                     " AND " + pendingStatusCol + " = 'H'" +
                     " AND (" + statusCol + " = 'N' OR " + statusCol + " = 'A'" +
                     " OR ((" + statusCol + " = 'R' OR " + statusCol + " = 'U') AND " + originalProcessDateTimeCol + " < ?" +
                     " OR " + originalProcessDateTimeCol + " > ?)))";
//        " OR ((" + statusCol + " = 'R' OR " + statusCol + " = 'U') AND (SUBSTRING(" + originalProcessDateTimeCol + ", 1, 10) < '" + startDate + "'" +
//        " OR SUBSTRING(" + originalProcessDateTimeCol + ", 1, 10) > '" + endDate + "'))))";

        List segmentPKs = new ArrayList();

        try
        {
            EDITDateTime startDateTime = new EDITDateTime(new EDITDate(startDate), EDITDateTime.DEFAULT_MIN_TIME);
            EDITDateTime endDateTime = new EDITDateTime(new EDITDate(endDate), EDITDateTime.DEFAULT_MAX_TIME);

            conn = ConnectionFactory.getSingleton().getConnection(POOLNAME);

            ps = conn.prepareStatement(sql);

            ps.setLong(1,productStructureFK);
            ps.setTimestamp(2, DBUtil.convertStringToTimestamp(startDateTime.getFormattedDateTime()));
            ps.setTimestamp(3, DBUtil.convertStringToTimestamp(endDateTime.getFormattedDateTime()));
            ps.setTimestamp(4, DBUtil.convertStringToTimestamp(startDateTime.getFormattedDateTime()));
            ps.setTimestamp(5, DBUtil.convertStringToTimestamp(endDateTime.getFormattedDateTime()));

            rs = ps.executeQuery();

            while (rs.next())
            {
                segmentPKs.add(new Long(rs.getLong("SegmentFK")));
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

        if (segmentPKs.size() == 0)
        {
            return null;
        }
        else
        {
            return Util.convertLongToPrim((Long[]) segmentPKs.toArray(new Long[segmentPKs.size()]));
        }
    }

    /**
     * Get the keys of all the Segment records that have an EDITTrxHistory process date between the start and end dates.
     * That are premium, withdrawal, not taken or full surrender trxs with a status of "N" or "R" and a pending status of "H"
     * for all product structures.
     * @param startDate
     * @param endDate
     * @return
     * @throws Exception
     */
    public long[] getUniqueSegmentPKsByDateRange(String startDate, String endDate) throws Exception
    {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        String editTrxFKCol               = EDITTRX_HISTORY_DBTABLE.getDBColumn("EDITTrxFK").getFullyQualifiedColumnName();
        String originalProcessDateTimeCol = EDITTRX_HISTORY_DBTABLE.getDBColumn("OriginalProcessDateTime").getFullyQualifiedColumnName();
        String processDateCol             = EDITTRX_HISTORY_DBTABLE.getDBColumn("ProcessDateTime").getFullyQualifiedColumnName();

        String editTrxPKCol               = EDITTRX_DBTABLE.getDBColumn("EDITTrxPK").getFullyQualifiedColumnName();
        String clientSetupFKCol           = EDITTRX_DBTABLE.getDBColumn("ClientSetupFK").getFullyQualifiedColumnName();
        String transactionTypeCTCol       = EDITTRX_DBTABLE.getDBColumn("TransactionTypeCT").getFullyQualifiedColumnName();
        String pendingStatusCol           = EDITTRX_DBTABLE.getDBColumn("PendingStatus").getFullyQualifiedColumnName();
        String statusCol                  = EDITTRX_DBTABLE.getDBColumn("Status").getFullyQualifiedColumnName();

        String clientSetupPKCol           = CLIENT_SETUP_DBTABLE.getDBColumn("ClientSetupPK").getFullyQualifiedColumnName();
        String contractSetupFKCol         = CLIENT_SETUP_DBTABLE.getDBColumn("ContractSetupFK").getFullyQualifiedColumnName();

        String contractSetupPKCol         = CONTRACT_SETUP_DBTABLE.getDBColumn("ContractSetupPK").getFullyQualifiedColumnName();
        String segmentFKCol               = CONTRACT_SETUP_DBTABLE.getDBColumn("SegmentFK").getFullyQualifiedColumnName();

        String sql = "SELECT DISTINCT " + segmentFKCol + " FROM " + EDITTRX_TABLENAME + ", " +
                     EDITTRX_HISTORY_TABLENAME + ", " + CLIENT_SETUP_TABLENAME + ", " + CONTRACT_SETUP_TABLENAME +
                     " WHERE " + editTrxFKCol + " = " + editTrxPKCol +
                     " AND " + clientSetupFKCol + " = " + clientSetupPKCol +
                     " AND " + contractSetupFKCol + " = " + contractSetupPKCol +
                     " AND " + processDateCol + " >= ?" +
                     " AND " + processDateCol + " <= ?" +
                     " AND (" + transactionTypeCTCol + " <> 'IS'" +
                     " AND " + pendingStatusCol + " = 'H'" +
                     " AND (" + statusCol + " = 'N' OR " + statusCol + " = 'A'" +
//                     " OR ((" + statusCol + " = 'R' OR " + statusCol + " = 'U') AND (SUBSTRING(" + originalProcessDateTimeCol + ", 1, 10) < '" + startDate + "'" +
//                     " OR SUBSTRING(" + originalProcessDateTimeCol + ", 1, 10) > '" + endDate + "'))))";
                     " OR ((" + statusCol + " = 'R' OR " + statusCol + " = 'U')" +
                     " AND " + originalProcessDateTimeCol + " < ?" +
                     " OR " + originalProcessDateTimeCol + " > ?)))";

        List segmentPKs = new ArrayList();

        try
        {
            EDITDateTime startDateTime = new EDITDateTime(new EDITDate(startDate), EDITDateTime.DEFAULT_MIN_TIME);
            EDITDateTime endDateTime = new EDITDateTime(new EDITDate(endDate), EDITDateTime.DEFAULT_MAX_TIME);

            conn = ConnectionFactory.getSingleton().getConnection(POOLNAME);

            ps = conn.prepareStatement(sql);

            ps.setTimestamp(1, DBUtil.convertStringToTimestamp(startDateTime.getFormattedDateTime()));
            ps.setTimestamp(2, DBUtil.convertStringToTimestamp(endDateTime.getFormattedDateTime()));
            ps.setTimestamp(3, DBUtil.convertStringToTimestamp(startDateTime.getFormattedDateTime()));
            ps.setTimestamp(4, DBUtil.convertStringToTimestamp(endDateTime.getFormattedDateTime()));

            rs = ps.executeQuery();

            while (rs.next())
            {
                segmentPKs.add(new Long(rs.getLong("SegmentFK")));
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

        if (segmentPKs.size() == 0)
        {
            return null;
        }
        else
        {
            return Util.convertLongToPrim((Long[]) segmentPKs.toArray(new Long[segmentPKs.size()]));
        }
    }

    public long[] findBySegmentPK_AND_PendingStatus(long segmentPK, String[] pendingStatuses, String transactionType, CRUD crud) throws Exception
    {
        Connection conn = null;
        Statement s = null;
        ResultSet rs = null;

        String clientSetupFKCol   = EDITTRX_DBTABLE.getDBColumn("ClientSetupFK").getFullyQualifiedColumnName();
        String pendingStatusCol   = EDITTRX_DBTABLE.getDBColumn("PendingStatus").getFullyQualifiedColumnName();
        String transactionTypeCTCol = EDITTRX_DBTABLE.getDBColumn("TransactionTypeCT").getFullyQualifiedColumnName();

        String clientSetupPKCol   = CLIENT_SETUP_DBTABLE.getDBColumn("ClientSetupPK").getFullyQualifiedColumnName();
        String contractSetupFKCol = CLIENT_SETUP_DBTABLE.getDBColumn("ContractSetupFK").getFullyQualifiedColumnName();

        String contractSetupPKCol = CONTRACT_SETUP_DBTABLE.getDBColumn("ContractSetupPK").getFullyQualifiedColumnName();
        String segmentFKCol       = CONTRACT_SETUP_DBTABLE.getDBColumn("SegmentFK").getFullyQualifiedColumnName();

        String sqlIn = "";

        for (int i = 0; i < pendingStatuses.length; i++)
        {
            if (i < pendingStatuses.length - 1)
            {
                sqlIn = sqlIn + pendingStatuses[i] + "', '";
            }
            else
            {
                sqlIn = sqlIn + pendingStatuses[i] + "')";
            }
        }

        String trxTypeSqlStmt = "";
        if (transactionType != null)
        {
            trxTypeSqlStmt = " AND " + transactionTypeCTCol + " NOT IN ('" + transactionType + "')";
        }

        String sql = "SELECT " + EDITTRX_TABLENAME + ".* FROM " + EDITTRX_TABLENAME + ", " +
                     CLIENT_SETUP_TABLENAME + ", " + CONTRACT_SETUP_TABLENAME +
                     " WHERE " + clientSetupFKCol + " = " + clientSetupPKCol +
                     " AND " + contractSetupFKCol + " = " + contractSetupPKCol +
                     " AND " + segmentFKCol + " = " + segmentPK +
                     " AND " + pendingStatusCol + " IN ('" + sqlIn +
                     " AND " + transactionTypeCTCol + " NOT IN ('" + transactionType + "')";

        List editTrxVOs = new ArrayList();

        try
        {
            if (crud == null)
            {
                conn = ConnectionFactory.getSingleton().getConnection(POOLNAME);
            }
            else
            {
                conn = crud.getCrudConn();
            }

            s = conn.createStatement();

            rs = s.executeQuery(sql);

            while (rs.next())
            {
                editTrxVOs.add(new Long(rs.getLong("EDITTrxPK")));
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
            if (s != null) s.close();
            //DO NOT close the connection passed in
            if (crud == null)
            {
                if (conn != null) conn.close();
            }
        }

        if (editTrxVOs.size() == 0)
        {
            return null;
        }
        else
        {
            return Util.convertLongToPrim((Long[]) editTrxVOs.toArray(new Long[editTrxVOs.size()]));
        }
    }

    public long[] findBase_RiderTrxBySegmentPK_AND_PendingStatus(long segmentPK, String[] pendingStatuses, String transactionType, CRUD crud) throws Exception
    {
        Connection conn = null;
        Statement s = null;
        ResultSet rs = null;

        String clientSetupFKCol   = EDITTRX_DBTABLE.getDBColumn("ClientSetupFK").getFullyQualifiedColumnName();
        String pendingStatusCol   = EDITTRX_DBTABLE.getDBColumn("PendingStatus").getFullyQualifiedColumnName();
        String transactionTypeCTCol = EDITTRX_DBTABLE.getDBColumn("TransactionTypeCT").getFullyQualifiedColumnName();

        String clientSetupPKCol   = CLIENT_SETUP_DBTABLE.getDBColumn("ClientSetupPK").getFullyQualifiedColumnName();
        String contractSetupFKCol = CLIENT_SETUP_DBTABLE.getDBColumn("ContractSetupFK").getFullyQualifiedColumnName();

        String contractSetupPKCol = CONTRACT_SETUP_DBTABLE.getDBColumn("ContractSetupPK").getFullyQualifiedColumnName();
        String segmentFKCol       = CONTRACT_SETUP_DBTABLE.getDBColumn("SegmentFK").getFullyQualifiedColumnName();

        String segmentPKCol       = SEGMENT_DBTABLE.getDBColumn("SegmentPK").getFullyQualifiedColumnName();
        String segmentFKColumn    = SEGMENT_DBTABLE.getDBColumn("SegmentFK").getFullyQualifiedColumnName();

        String sqlIn = "";

        for (int i = 0; i < pendingStatuses.length; i++)
        {
            if (i < pendingStatuses.length - 1)
            {
                sqlIn = sqlIn + pendingStatuses[i] + "', '";
            }
            else
            {
                sqlIn = sqlIn + pendingStatuses[i] + "')";
            }
        }

        String sql = "SELECT " + EDITTRX_TABLENAME + ".* FROM " + EDITTRX_TABLENAME +
                     " INNER JOIN " + CLIENT_SETUP_TABLENAME + " ON " + clientSetupFKCol + " = " + clientSetupPKCol +
                     " INNER JOIN " + CONTRACT_SETUP_TABLENAME + " ON " + contractSetupFKCol + " = " + contractSetupPKCol +
                     " INNER JOIN " + SEGMENT_TABLENAME + " ON " + segmentFKCol + " = " + segmentPKCol +
                     " WHERE  (" + segmentPKCol + " = " + segmentPK +
                     " OR " + segmentFKColumn + " = " + segmentPK +
                     ") AND " + pendingStatusCol + " IN ('" + sqlIn +
                     " AND " + transactionTypeCTCol + " NOT IN ('" + transactionType + "')" ;


        List editTrxVOs = new ArrayList();

        try
        {
            if (crud == null)
            {
                conn = ConnectionFactory.getSingleton().getConnection(POOLNAME);
            }
            else
            {
                conn = crud.getCrudConn();
            }

            s = conn.createStatement();

            rs = s.executeQuery(sql);

            while (rs.next())
            {
                editTrxVOs.add(new Long(rs.getLong("EDITTrxPK")));
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
            if (s != null) s.close();
            //DO NOT close the connection passed in
            if (crud == null)
            {
                if (conn != null) conn.close();
            }
        }

        if (editTrxVOs.size() == 0)
        {
            return null;
        }
        else
        {
            return Util.convertLongToPrim((Long[]) editTrxVOs.toArray(new Long[editTrxVOs.size()]));
        }
    }

    public EDITTrxVO[] findByRange_AND_PendingStatus(long startingEDITTrxPK,
                                                     String[] pendingStatus,
                                                     int fetchSize,
                                                     int scrollDirection,
                                                     boolean firstPass) throws Exception
    {
        Connection conn = null;
        Statement s = null;
        ResultSet rs = null;

        List editTrxVOs = new ArrayList();

        try
        {
            // Get driving editTrxVO
            EDITTrxVO editTrxVO = null;

            String pendingStatusCol     = EDITTRX_DBTABLE.getDBColumn("PendingStatus").getFullyQualifiedColumnName();
            String transactionTypeCTCol = EDITTRX_DBTABLE.getDBColumn("TransactionTypeCT").getFullyQualifiedColumnName();
            String effectiveDateCol     = EDITTRX_DBTABLE.getDBColumn("EffectiveDate").getFullyQualifiedColumnName();

            String sqlIn = "";

            for (int i = 0; i < pendingStatus.length; i++)
            {
                if (i < pendingStatus.length - 1)
                {
                    sqlIn = sqlIn + pendingStatus[i] + "', '";
                }
                else
                {
                    sqlIn = sqlIn + pendingStatus[i] + "')";
                }
            }

            String sql = "SELECT * FROM " + EDITTRX_TABLENAME +
                         " WHERE " + pendingStatusCol + " IN ('" +  sqlIn +
                         " AND " + transactionTypeCTCol  + " NOT IN('CK', 'RCK') ORDER BY " + effectiveDateCol + " ASC";

            int count = 0;

            conn = ConnectionFactory.getSingleton().getConnection(POOLNAME);

            s = conn.createStatement();

            rs = s.executeQuery(sql);

            int forwardCount = 0;
            boolean forwardCountStarted = false;

            while (rs.next())
            {
                count++;

                editTrxVO = (EDITTrxVO) CRUD.populateVOFromResultSetRow(rs, VOClass.getVOClassMetaData(EDITTrxVO.class));

                editTrxVOs.add(editTrxVO);

                if (count >= fetchSize  && !firstPass && startingEDITTrxPK != editTrxVO.getEDITTrxPK())
                {
                    editTrxVOs.remove(0);
                }

                if (startingEDITTrxPK == editTrxVO.getEDITTrxPK())
                {
                    if (scrollDirection > 0)
                    {
                        forwardCountStarted = true;
                    }
                    else
                    {
                        break;
                    }
                }

                if (forwardCountStarted)
                {
                    forwardCount++;
                    if (forwardCount == fetchSize)
                        break;
                }
            }
        }
        catch (Exception e)
        {
            System.out.println(e);
            e.printStackTrace();
            throw e;
        }
        finally
        {
            if (rs != null) rs.close();
            if (s != null) s.close();
            if (conn != null) conn.close();
        }

        if (editTrxVOs.size() == 0)
        {
            return null;
        }
        else
        {
            return (EDITTrxVO[]) editTrxVOs.toArray(new EDITTrxVO[editTrxVOs.size()]);
        }
    }

    public InvestmentHistoryVO[] findInvestmentHistoryByInvestmentAndEditTrxHistory(long investmentFK, long editTrxHistoryPK) throws Exception
    {
        Connection conn = null;
        Statement s = null;
        ResultSet rs = null;

        List invHistoryVOs = new ArrayList();

        try
        {
            InvestmentHistoryVO investmentHistoryVO = null;

            String editTrxHistoryFKCol = INVESTMENT_HISTORY_DBTABLE.getDBColumn("EDITTrxHistoryFK").getFullyQualifiedColumnName();
            String investmentFKCol     = INVESTMENT_HISTORY_DBTABLE.getDBColumn("InvestmentFK").getFullyQualifiedColumnName();

            String sql = "SELECT * FROM " + INVESTMENT_HISTORY_TABLENAME +
                         " WHERE " + editTrxHistoryFKCol + " = " + editTrxHistoryPK +
                         " AND " + investmentFKCol + " = " + investmentFK;

            conn = ConnectionFactory.getSingleton().getConnection(POOLNAME);

            s = conn.createStatement();

            rs = s.executeQuery(sql);

            while (rs.next())
            {
                investmentHistoryVO = (InvestmentHistoryVO) CRUD.populateVOFromResultSetRow(rs, VOClass.getVOClassMetaData(InvestmentHistoryVO.class));

                invHistoryVOs.add(investmentHistoryVO);
            }
        }
        catch (Exception e)
        {
            System.out.println(e);
            e.printStackTrace();
            throw e;
        }
        finally
        {
            if (rs != null) rs.close();
            if (s != null) s.close();
            if (conn != null) conn.close();
        }

        if (invHistoryVOs.size() == 0)
        {
            return null;
        }
        else
        {
            return (InvestmentHistoryVO[]) invHistoryVOs.toArray(new InvestmentHistoryVO[invHistoryVOs.size()]);
        }
    }

    /**
     * Get the keys of all the Segment records for a given product structure, segment name and qualified types
     * that has activity which can be determined by looking at if there are any premium transactions for given tax year.
     *
     * @param productStructureFK
     * @param segmentNameCT
     * @param taxYear
     * @param qualifiedTypes
     *
     * @return
     *
     * @throws Exception
     */
    public List getTE5498UniqueSegmentPKsByProductStructureAndSegmentNameAndQualifiedTypes(long productStructureFK,
                                                                                           String segmentNameCT,
                                                                                           String taxYear,
                                                                                           String[] qualifiedTypes) throws Exception
    {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        String segmentPKCol          = SEGMENT_DBTABLE.getDBColumn("SegmentPK").getFullyQualifiedColumnName();
        String segmentFKCol          = CONTRACT_SETUP_DBTABLE.getDBColumn("SegmentFK").getFullyQualifiedColumnName();

        String contractSetupPKCol    = CONTRACT_SETUP_DBTABLE.getDBColumn("ContractSetupPK").getFullyQualifiedColumnName();
        String contractSetupFKCol    = CLIENT_SETUP_DBTABLE.getDBColumn("ContractSetupFK").getFullyQualifiedColumnName();

        String clientSetupPKCol      = CLIENT_SETUP_DBTABLE.getDBColumn("ClientSetupPK").getFullyQualifiedColumnName();
        String clientSetupFKCol      = EDITTRX_DBTABLE.getDBColumn("ClientSetupFK").getFullyQualifiedColumnName();

        String segmentNameCTCol      = SEGMENT_DBTABLE.getDBColumn("SegmentNameCT").getFullyQualifiedColumnName();
        String productStructureFKCol = SEGMENT_DBTABLE.getDBColumn("ProductStructureFK").getFullyQualifiedColumnName();

        String taxYearCol            = EDITTRX_DBTABLE.getDBColumn("TaxYear").getFullyQualifiedColumnName();
        String transactionTypeCTCol  = EDITTRX_DBTABLE.getDBColumn("TransactionTypeCT").getFullyQualifiedColumnName();

        String statusCol             = EDITTRX_DBTABLE.getDBColumn("Status").getFullyQualifiedColumnName();
        String pendingStatusCol      = EDITTRX_DBTABLE.getDBColumn("PendingStatus").getFullyQualifiedColumnName();

        String qualifiedTypeCTCol    = SEGMENT_DBTABLE.getDBColumn("QualifiedTypeCT").getFullyQualifiedColumnName();

        // No rider/waiver segments are selected because the SegmentName of waiver segments is 'Waiver'
        String sql = " SELECT DISTINCT " + segmentPKCol +
                     " FROM " + SEGMENT_TABLENAME +
                     " INNER JOIN " + CONTRACT_SETUP_TABLENAME +
                     " ON " + segmentPKCol + " = " + segmentFKCol +
                     " INNER JOIN " + CLIENT_SETUP_TABLENAME +
                     " ON " + contractSetupPKCol + " = " + contractSetupFKCol +
                     " INNER JOIN " + EDITTRX_TABLENAME +
                     " ON " + clientSetupPKCol + " = " + clientSetupFKCol +
                     " WHERE " + productStructureFKCol + " = ?" +
                     " AND " + segmentNameCTCol + " = ?" +
                     " AND " + taxYearCol + " = ?" +
                     " AND " + transactionTypeCTCol + " = ?" +
                     " AND " + pendingStatusCol + " = ?" +
                     " AND (" + statusCol + " = ? OR " + statusCol + " = ?)";

        sql = sql + " AND " + qualifiedTypeCTCol + " IN (";

        for( int i = 0; i < qualifiedTypes.length; i++)
        {
            sql = sql + "?";

            if ((i+1) != qualifiedTypes.length)
            {
                sql = sql + ",";
            }
        }

        sql = sql + ")";

        List segmentPKs = new ArrayList();

        try
        {
            conn = ConnectionFactory.getSingleton().getConnection(ConnectionFactory.EDITSOLUTIONS_POOL);

            ps = conn.prepareStatement(sql);

            ps.setLong(1, productStructureFK);
            ps.setString(2, segmentNameCT);
            ps.setInt(3, Integer.parseInt(taxYear));
            ps.setString(4, EDITTrx.TRANSACTIONTYPECT_CALENDARYEAREND);
            ps.setString(5, EDITTrx.PENDINGSTATUS_HISTORY);
            ps.setString(6, EDITTrx.STATUS_NATURAL);
            ps.setString(7, EDITTrx.STATUS_APPLY);

            // when the number of previous parameters is changed the 'i' value needs to be changed.
            // for example if the previous parameters are 3 then i value needs to be set to 4.
            for( int i = 8; i < (qualifiedTypes.length + 8); i++)
            {
                ps.setString(i, qualifiedTypes[i-8]);
            }

            rs = ps.executeQuery();

            while (rs.next())
            {
                segmentPKs.add(new Long(rs.getLong("SegmentPK")));
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

        return segmentPKs;
    }

    /**
     * Get the keys of all the Segment records that have an EDITTrxHistory process date between the start and end dates.
     * That are premium, withdrawal, not taken or full surrender trxs with a status of "N" or "R" and a pending status of "H"
     * for a selected product structure.
     *
     * @param startDate
     * @param endDate
     * @param productStructureFK
     * @param tranType
     *
     * @return
     *
     * @throws Exception
     */
    public List getTEUniqueSegmentPKsByDateRangeAndProductStructure(String startDate,
                                                                         String endDate,
                                                                         long productStructureFK,
                                                                         String[] tranType,
                                                                         String taxYear) throws Exception
    {
        Connection conn = null;
        ResultSet rs = null;
        PreparedStatement ps = null;

        String segmentPKCol               = SEGMENT_DBTABLE.getDBColumn("SegmentPK").getFullyQualifiedColumnName();
        String productStructureFKCol      = SEGMENT_DBTABLE.getDBColumn("ProductStructureFK").getFullyQualifiedColumnName();

        String contractSetupSegmentFKCol  = CONTRACT_SETUP_DBTABLE.getDBColumn("SegmentFK").getFullyQualifiedColumnName();
        String contractSetupPKCol         = CONTRACT_SETUP_DBTABLE.getDBColumn("ContractSetupPK").getFullyQualifiedColumnName();

        String clientSetupPKCol           = CLIENT_SETUP_DBTABLE.getDBColumn("ClientSetupPK").getFullyQualifiedColumnName();
        String contractSetupFKCol         = CLIENT_SETUP_DBTABLE.getDBColumn("ContractSetupFK").getFullyQualifiedColumnName();

        String editTrxFKCol               = EDITTRX_HISTORY_DBTABLE.getDBColumn("EDITTrxFK").getFullyQualifiedColumnName();
        String processDateCol             = EDITTRX_HISTORY_DBTABLE.getDBColumn("ProcessDateTime").getFullyQualifiedColumnName();
        String originalProcessDateTimeCol = EDITTRX_HISTORY_DBTABLE.getDBColumn("OriginalProcessDateTime").getFullyQualifiedColumnName();

        String editTrxPKCol               = EDITTRX_DBTABLE.getDBColumn("EDITTrxPK").getFullyQualifiedColumnName();
        String clientSetupFKCol           = EDITTRX_DBTABLE.getDBColumn("ClientSetupFK").getFullyQualifiedColumnName();
        String transactionTypeCTCol       = EDITTRX_DBTABLE.getDBColumn("TransactionTypeCT").getFullyQualifiedColumnName();
        String statusCol                  = EDITTRX_DBTABLE.getDBColumn("Status").getFullyQualifiedColumnName();
        String pendingStatusCol           = EDITTRX_DBTABLE.getDBColumn("PendingStatus").getFullyQualifiedColumnName();
        String taxYearCol                 = EDITTRX_DBTABLE.getDBColumn("TaxYear").getFullyQualifiedColumnName();


        String sql = " SELECT DISTINCT " + contractSetupSegmentFKCol +
                     " FROM " + EDITTRX_TABLENAME + ", " +  EDITTRX_HISTORY_TABLENAME + ", " + CLIENT_SETUP_TABLENAME + ", " + CONTRACT_SETUP_TABLENAME + ", " + this.SEGMENT_TABLENAME +
                     " WHERE " + editTrxFKCol + " = " + editTrxPKCol +
                     " AND " + clientSetupFKCol + " = " + clientSetupPKCol +
                     " AND " + contractSetupFKCol + " = " + contractSetupPKCol +
                     " AND " + contractSetupSegmentFKCol  + " = " + segmentPKCol;

        if (productStructureFK != 0 )
        {
            sql = sql + " AND " +  productStructureFKCol + " = " + productStructureFK;
        }

        sql = sql + " AND " +  processDateCol  + " >= ?";
        sql = sql + " AND " + processDateCol  + " <= ?";

        if (tranType != null && tranType.length > 0 )
        {
            sql = sql + " AND (" + transactionTypeCTCol + " IN (";

            for( int i = 0 ; i < tranType.length; i++)
            {
                sql = sql + "'" + tranType[i] + "'";

                if ((i+1) != tranType.length)
                {
                    sql = sql + ",";
                }
            }

            sql = sql + ")";
        }
        else
        {
            sql = sql + " AND (" + transactionTypeCTCol + " <> '' ";
        }

        sql = sql + " AND " + pendingStatusCol + " = 'H'";
        sql = sql + " AND " + taxYearCol + " = " + taxYear;
        sql = sql + " AND (" + statusCol + " = 'N' OR " + statusCol + " = 'A'";
        sql = sql + " OR ((" + statusCol + " = 'R' OR " + statusCol + " = 'U') AND (" + originalProcessDateTimeCol + " < ?";
        sql = sql + " OR " + originalProcessDateTimeCol + " > ?))))";

        List segmentPKs = new ArrayList();

        try
        {
            EDITDateTime startDateTime = new EDITDateTime(new EDITDate(startDate), EDITDateTime.DEFAULT_MIN_TIME);
            EDITDateTime endDateTime = new EDITDateTime(new EDITDate(endDate), EDITDateTime.DEFAULT_MAX_TIME);

            conn = ConnectionFactory.getSingleton().getConnection(ConnectionFactory.EDITSOLUTIONS_POOL);

            ps = conn.prepareStatement(sql);

            ps.setTimestamp(1, DBUtil.convertStringToTimestamp(startDateTime.getFormattedDateTime()));
            ps.setTimestamp(2, DBUtil.convertStringToTimestamp(endDateTime.getFormattedDateTime()));
            ps.setTimestamp(3, DBUtil.convertStringToTimestamp(startDateTime.getFormattedDateTime()));
            ps.setTimestamp(4, DBUtil.convertStringToTimestamp(endDateTime.getFormattedDateTime()));

            rs = ps.executeQuery();

            while (rs.next())
            {
                segmentPKs.add(new Long(rs.getLong("SegmentFK")));
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

        return segmentPKs;
    }

    public java.util.ArrayList findTaxExtByDateRangeSegmentPK(String startDate,
                                                              String endDate,
                                                              long segmentFK,
                                                              String[] trantype,
                                                              String taxYear) throws Exception
    {
        Connection conn = null;
        ResultSet rs = null;
        PreparedStatement ps = null;

        String editTrxPKCol               = EDITTRX_DBTABLE.getDBColumn("EDITTrxPK").getFullyQualifiedColumnName();
        String transactionTypeCTCol       = EDITTRX_DBTABLE.getDBColumn("TransactionTypeCT").getFullyQualifiedColumnName();
        String clientSetupFKCol           = EDITTRX_DBTABLE.getDBColumn("ClientSetupFK").getFullyQualifiedColumnName();
        String statusCol                  = EDITTRX_DBTABLE.getDBColumn("Status").getFullyQualifiedColumnName();
        String pendingStatusCol           = EDITTRX_DBTABLE.getDBColumn("PendingStatus").getFullyQualifiedColumnName();
        String taxYearCol                 = EDITTRX_DBTABLE.getDBColumn("TaxYear").getFullyQualifiedColumnName();

        String clientSetupPKCol           = CLIENT_SETUP_DBTABLE.getDBColumn("ClientSetupPK").getFullyQualifiedColumnName();
        String contractSetupFKCol         = CLIENT_SETUP_DBTABLE.getDBColumn("ContractSetupFK").getFullyQualifiedColumnName();

        String contractSetupPKCol         = CONTRACT_SETUP_DBTABLE.getDBColumn("ContractSetupPK").getFullyQualifiedColumnName();
        String segmentFKCol               = CONTRACT_SETUP_DBTABLE.getDBColumn("SegmentFK").getFullyQualifiedColumnName();

        String editTrxHistoryPKCol = EDITTRX_HISTORY_DBTABLE.getDBColumn("EDITTrxHistoryPK").getFullyQualifiedColumnName();
        String editTrxFKCol               = EDITTRX_HISTORY_DBTABLE.getDBColumn("EDITTrxFK").getFullyQualifiedColumnName();
        String processDateCol             = EDITTRX_HISTORY_DBTABLE.getDBColumn("ProcessDateTime").getFullyQualifiedColumnName();
        String originalProcessDateTimeCol = EDITTRX_HISTORY_DBTABLE.getDBColumn("OriginalProcessDateTime").getFullyQualifiedColumnName();

        String editTrxHistoryFKCol = FINANCIAL_HISTORY_DBTABLE.getDBColumn("EDITTrxHistoryFK").getFullyQualifiedColumnName();

        String sql = " SELECT DISTINCT " + editTrxPKCol + ", " + transactionTypeCTCol +
                     " FROM " + EDITTRX_TABLENAME +
                     " INNER JOIN " + EDITTRX_HISTORY_TABLENAME +
                     " ON " + editTrxFKCol + " = " + editTrxPKCol +
                     " INNER JOIN " + FINANCIAL_HISTORY_TABLENAME +
                     " ON " + editTrxHistoryFKCol + " = " + editTrxHistoryPKCol +
                     " INNER JOIN " + CLIENT_SETUP_TABLENAME +
                     " ON " + clientSetupFKCol + " = " + clientSetupPKCol +
                     " INNER JOIN " + CONTRACT_SETUP_TABLENAME +
                     " ON " + contractSetupFKCol + " = " + contractSetupPKCol +
                     " WHERE " + segmentFKCol + " = " + segmentFK +
                     " AND " + processDateCol + " >= ?" +
                     " AND " + processDateCol + " <= ?";

        if(trantype != null && trantype.length > 0 )
        {
            sql = sql + " AND (" + transactionTypeCTCol + " IN (";

            for( int i = 0 ; i < trantype.length; i++)
            {
                sql = sql + "'" + trantype[i] + "'";

                if ((i+1) != trantype.length)
                {
                    sql = sql + ",";
                }
            }

            sql = sql + ")";
        }
        else
        {
            sql = sql + "' AND (" + transactionTypeCTCol + " <> '' ";
        }

        sql = sql + " AND " + pendingStatusCol + " = 'H'";
        sql = sql + " AND " + taxYearCol + " = " + taxYear;
        sql = sql + " AND (" + statusCol + " = 'N' OR " + statusCol + " = 'A')";
        sql = sql + " OR ((" + statusCol + " = 'R' OR " + statusCol + " = 'U') AND " + originalProcessDateTimeCol + " < ?";
        sql = sql + " OR " + originalProcessDateTimeCol + " > ?))";
        sql = sql + " ORDER BY " + transactionTypeCTCol;

        java.util.ArrayList editTrxPKs = new java.util.ArrayList();
        EditTrxKeyAndTranType editTrxKeyAndTranType = null;

        try
        {
            EDITDateTime startDateTime = new EDITDateTime(new EDITDate(startDate), EDITDateTime.DEFAULT_MIN_TIME);
            EDITDateTime endDateTime = new EDITDateTime(new EDITDate(endDate), EDITDateTime.DEFAULT_MAX_TIME);

            conn = ConnectionFactory.getSingleton().getConnection(ConnectionFactory.EDITSOLUTIONS_POOL);

            ps = conn.prepareStatement(sql);

            ps.setTimestamp(1, DBUtil.convertStringToTimestamp(startDateTime.getFormattedDateTime()));
            ps.setTimestamp(2, DBUtil.convertStringToTimestamp(endDateTime.getFormattedDateTime()));
            ps.setTimestamp(3, DBUtil.convertStringToTimestamp(startDateTime.getFormattedDateTime()));
            ps.setTimestamp(4, DBUtil.convertStringToTimestamp(endDateTime.getFormattedDateTime()));
            rs = ps.executeQuery();

            while (rs.next())
            {
                editTrxKeyAndTranType = new EditTrxKeyAndTranType(rs.getLong("EDITTrxPK"), rs.getString("TransactionTypeCT"));

                editTrxPKs.add(editTrxKeyAndTranType);
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

        if (editTrxPKs.size() == 0)
        {
            return null;
        }
        else
        {
           // return Util.convertLongToPrim((Long[]) editTrxPKs.toArray(new Long[editTrxPKs.size()]));
           return editTrxPKs;
        }
    }

    public java.util.ArrayList findEditTrxFor1099R(String startDate,
                                                   String endDate,
                                                   long segmentFK,
                                                   String[] trantype,
                                                   String taxYear) throws Exception
    {
        Connection conn = null;
        ResultSet rs = null;
        PreparedStatement ps = null;

        String editTrxPKCol               = EDITTRX_DBTABLE.getDBColumn("EDITTrxPK").getFullyQualifiedColumnName();
        String transactionTypeCTCol       = EDITTRX_DBTABLE.getDBColumn("TransactionTypeCT").getFullyQualifiedColumnName();
        String clientSetupFKCol           = EDITTRX_DBTABLE.getDBColumn("ClientSetupFK").getFullyQualifiedColumnName();
        String statusCol                  = EDITTRX_DBTABLE.getDBColumn("Status").getFullyQualifiedColumnName();
        String pendingStatusCol           = EDITTRX_DBTABLE.getDBColumn("PendingStatus").getFullyQualifiedColumnName();
        String taxYearCol                 = EDITTRX_DBTABLE.getDBColumn("TaxYear").getFullyQualifiedColumnName();

        String clientSetupPKCol           = CLIENT_SETUP_DBTABLE.getDBColumn("ClientSetupPK").getFullyQualifiedColumnName();
        String contractSetupFKCol         = CLIENT_SETUP_DBTABLE.getDBColumn("ContractSetupFK").getFullyQualifiedColumnName();

        String contractSetupPKCol         = CONTRACT_SETUP_DBTABLE.getDBColumn("ContractSetupPK").getFullyQualifiedColumnName();
        String segmentFKCol               = CONTRACT_SETUP_DBTABLE.getDBColumn("SegmentFK").getFullyQualifiedColumnName();
        String complexChangeTypeCTCol     = CONTRACT_SETUP_DBTABLE.getDBColumn("ComplexChangeTypeCT").getFullyQualifiedColumnName();

        String editTrxHistoryPKCol = EDITTRX_HISTORY_DBTABLE.getDBColumn("EDITTrxHistoryPK").getFullyQualifiedColumnName();
        String editTrxFKCol               = EDITTRX_HISTORY_DBTABLE.getDBColumn("EDITTrxFK").getFullyQualifiedColumnName();
        String processDateCol             = EDITTRX_HISTORY_DBTABLE.getDBColumn("ProcessDateTime").getFullyQualifiedColumnName();
        String originalProcessDateTimeCol = EDITTRX_HISTORY_DBTABLE.getDBColumn("OriginalProcessDateTime").getFullyQualifiedColumnName();

        String editTrxHistoryFKCol = FINANCIAL_HISTORY_DBTABLE.getDBColumn("EDITTrxHistoryFK").getFullyQualifiedColumnName();
        String taxableIndicatorCol = FINANCIAL_HISTORY_DBTABLE.getDBColumn("TaxableIndicator").getFullyQualifiedColumnName();

        String trxTypeSql = "";

        String[] updatedTranType = null;

        int j = 0;

        if (trantype != null && trantype.length > 0)
        {
            updatedTranType = new String[trantype.length - 1];
            for (int i = 0; i < trantype.length; i++)
            {
                if (!trantype[i].equalsIgnoreCase("CC"))
                {
                    updatedTranType[j] = trantype[i];
                    j += 1;
                }
            }
        }

        if(updatedTranType != null)
        {
            trxTypeSql = transactionTypeCTCol + " IN (";

            for( int i = 0 ; i < updatedTranType.length; i++)
            {
                trxTypeSql = trxTypeSql + "'" + updatedTranType[i] + "'";

                if ((i+1) != updatedTranType.length)
                {
                    trxTypeSql = trxTypeSql + ",";
                }
            }

            trxTypeSql = trxTypeSql + ")";
        }

        String sql = " SELECT DISTINCT " + editTrxPKCol + ", " + transactionTypeCTCol +
                     " FROM " + EDITTRX_TABLENAME +
                     " INNER JOIN " + EDITTRX_HISTORY_TABLENAME +
                     " ON " + editTrxFKCol + " = " + editTrxPKCol +
                     " INNER JOIN " + FINANCIAL_HISTORY_TABLENAME +
                     " ON " + editTrxHistoryFKCol + " = " + editTrxHistoryPKCol +
                     " INNER JOIN " + CLIENT_SETUP_TABLENAME +
                     " ON " + clientSetupFKCol + " = " + clientSetupPKCol +
                     " INNER JOIN " + CONTRACT_SETUP_TABLENAME +
                     " ON " + contractSetupFKCol + " = " + contractSetupPKCol +
                     " WHERE " + segmentFKCol + " = " + segmentFK +
                     " AND " + processDateCol + " >= ?" +
                     " AND " + processDateCol + " <= ?" +
                     " AND (" + trxTypeSql +
                     " OR (" + transactionTypeCTCol + " = 'CC'" +
                     " AND (" + complexChangeTypeCTCol + " = 'OwnershipChange'" +
                     " OR " + complexChangeTypeCTCol + " = 'ROTHConversion')))" +
                     " AND " + pendingStatusCol + " = 'H'" +
                     " AND " + taxYearCol + " = " + taxYear +
                     " AND " + taxableIndicatorCol + " NOT IN ('N')" +
                     " AND (" + statusCol + " IN ('N', 'A')" +
                     " OR (" + statusCol + " IN ('R','U')" +
                     " AND (" + originalProcessDateTimeCol + " < ?  OR " + originalProcessDateTimeCol + " > ?)))" +
                     " ORDER BY " + transactionTypeCTCol;

        java.util.ArrayList editTrxPKs = new java.util.ArrayList();
        EditTrxKeyAndTranType editTrxKeyAndTranType = null;

        try
        {
            EDITDateTime startDateTime = new EDITDateTime(new EDITDate(startDate), EDITDateTime.DEFAULT_MIN_TIME);
            EDITDateTime endDateTime = new EDITDateTime(new EDITDate(endDate), EDITDateTime.DEFAULT_MAX_TIME);

            conn = ConnectionFactory.getSingleton().getConnection(ConnectionFactory.EDITSOLUTIONS_POOL);

            ps = conn.prepareStatement(sql);

            ps.setTimestamp(1, DBUtil.convertStringToTimestamp(startDateTime.getFormattedDateTime()));
            ps.setTimestamp(2, DBUtil.convertStringToTimestamp(endDateTime.getFormattedDateTime()));
            ps.setTimestamp(3, DBUtil.convertStringToTimestamp(startDateTime.getFormattedDateTime()));
            ps.setTimestamp(4, DBUtil.convertStringToTimestamp(endDateTime.getFormattedDateTime()));

            rs = ps.executeQuery();

            while (rs.next())
            {
                editTrxKeyAndTranType = new EditTrxKeyAndTranType(rs.getLong("EDITTrxPK"), rs.getString("TransactionTypeCT"));

                editTrxPKs.add(editTrxKeyAndTranType);
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

        if (editTrxPKs.size() == 0)
        {
            return null;
        }
        else
        {
           // return Util.convertLongToPrim((Long[]) editTrxPKs.toArray(new Long[editTrxPKs.size()]));
           return editTrxPKs;
        }
    }

    public long[] findTaxExtByDateRangeFor1099Misc(String startDate, String endDate, String[] trantype, String taxYear) throws Exception
    {
        Connection conn = null;
        ResultSet rs = null;
        PreparedStatement ps = null;

        String editTrxPKCol               = EDITTRX_DBTABLE.getDBColumn("EDITTrxPK").getFullyQualifiedColumnName();
        String transactionTypeCTCol       = EDITTRX_DBTABLE.getDBColumn("TransactionTypeCT").getFullyQualifiedColumnName();
        String pendingStatusCol           = EDITTRX_DBTABLE.getDBColumn("PendingStatus").getFullyQualifiedColumnName();
        String statusCol                  = EDITTRX_DBTABLE.getDBColumn("Status").getFullyQualifiedColumnName();

        String editTrxFKCol               = EDITTRX_HISTORY_DBTABLE.getDBColumn("EDITTrxFK").getFullyQualifiedColumnName();
        String processDateCol             = EDITTRX_HISTORY_DBTABLE.getDBColumn("ProcessDateTime").getFullyQualifiedColumnName();
        String originalProcessDateTimeCol = EDITTRX_HISTORY_DBTABLE.getDBColumn("OriginalProcessDateTime").getFullyQualifiedColumnName();

        String sql = " SELECT DISTINCT " + editTrxPKCol  +
                     " FROM " + EDITTRX_TABLENAME + " INNER JOIN " + EDITTRX_HISTORY_TABLENAME  +
                     " ON " + editTrxPKCol + " = " + editTrxFKCol +
                     " WHERE " + processDateCol + " >= ?" +
                     " AND " + processDateCol + " <= ?" +
                     " AND " + transactionTypeCTCol + " IN ('CK', 'CA')" +
                     " AND " + pendingStatusCol + " = 'H'" +
                     " AND (" + statusCol + " = 'N' OR " + statusCol + " = 'A'" +
                     " OR ((" + statusCol + " = 'R' OR " + statusCol + " = 'U')" +
                     " AND (" + originalProcessDateTimeCol + " < ?" +
                     " OR " + originalProcessDateTimeCol + " > ?)))";


        List editTrxPKs = new ArrayList();

        try
        {
            EDITDateTime startDateTime = new EDITDateTime(new EDITDate(startDate), EDITDateTime.DEFAULT_MIN_TIME);
            EDITDateTime endDateTime = new EDITDateTime(new EDITDate(endDate), EDITDateTime.DEFAULT_MAX_TIME);

            conn = ConnectionFactory.getSingleton().getConnection(ConnectionFactory.EDITSOLUTIONS_POOL);

            ps = conn.prepareStatement(sql);

            ps.setTimestamp(1, DBUtil.convertStringToTimestamp(startDateTime.getFormattedDateTime()));
            ps.setTimestamp(2, DBUtil.convertStringToTimestamp(endDateTime.getFormattedDateTime()));
            ps.setTimestamp(3, DBUtil.convertStringToTimestamp(startDateTime.getFormattedDateTime()));
            ps.setTimestamp(4, DBUtil.convertStringToTimestamp(endDateTime.getFormattedDateTime()));

            rs = ps.executeQuery();

            while (rs.next())
            {
                editTrxPKs.add(new Long(rs.getLong("EDITTrxPK")));
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

        if (editTrxPKs.size() == 0)
        {
            return null;
        }
        else
        {
            return Util.convertLongToPrim((Long[]) editTrxPKs.toArray(new Long[editTrxPKs.size()]));
        }

    }

    /**
     * Retrieves the total Gross Amount of WI and SW transactions for the specified contract and tax year
     * @param taxYear
     * @param segmentFK
     * @return
     * @throws Exception
     */
    public EDITBigDecimal getWithdrawalsTaxYearToDate(int taxYear, long segmentFK) throws Exception
    {
        Connection conn = null;
        Statement s = null;
        ResultSet rs = null;

        EDITBigDecimal totalGrossAmount = new EDITBigDecimal();

        String editTrxPKCol = EDITTRX_DBTABLE.getDBColumn("EDITTrxPK").getFullyQualifiedColumnName();
        String taxYearCol = EDITTRX_DBTABLE.getDBColumn("TaxYear").getFullyQualifiedColumnName();
        String clientSetupFKCol = EDITTRX_DBTABLE.getDBColumn("ClientSetupFK").getFullyQualifiedColumnName();
        String transactionTypeCTCol = EDITTRX_DBTABLE.getDBColumn("TransactionTypeCT").getFullyQualifiedColumnName();

        String clientSetupPKCol = CLIENT_SETUP_DBTABLE.getDBColumn("ClientSetupPK").getFullyQualifiedColumnName();
        String contractSetupFKCol = CLIENT_SETUP_DBTABLE.getDBColumn("ContractSetupFK").getFullyQualifiedColumnName();

        String contractSetupPKCol = CONTRACT_SETUP_DBTABLE.getDBColumn("ContractSetupPK").getFullyQualifiedColumnName();
        String segmentFKCol = CONTRACT_SETUP_DBTABLE.getDBColumn("SegmentFK").getFullyQualifiedColumnName();

        String editTrxHistoryPKCol = EDITTRX_HISTORY_DBTABLE.getDBColumn("EDITTrxHistoryPK").getFullyQualifiedColumnName();
        String editTrxFKCol = EDITTRX_HISTORY_DBTABLE.getDBColumn("EDITTrxFK").getFullyQualifiedColumnName();

        String grossAmountCol = FINANCIAL_HISTORY_DBTABLE.getDBColumn("GrossAmount").getFullyQualifiedColumnName();
        String editTrxHistoryFKCol = FINANCIAL_HISTORY_DBTABLE.getDBColumn("EDITTrxHistoryFK").getFullyQualifiedColumnName();

        String sql = " SELECT SUM(" + grossAmountCol + ") AS TotalGrossAmount" +
                     " FROM " + FINANCIAL_HISTORY_TABLENAME + ", " + EDITTRX_HISTORY_TABLENAME + ", " +
                      EDITTRX_TABLENAME + ", " + CLIENT_SETUP_TABLENAME + ", " + CONTRACT_SETUP_TABLENAME +
                     " WHERE " + segmentFKCol + " = " + segmentFK +
                     " AND " + transactionTypeCTCol + " IN ('WI', 'SW')" +
                     " AND " + taxYearCol + " = " + taxYear +
                     " AND " + editTrxHistoryFKCol + " = " + editTrxHistoryPKCol +
                     " AND " + editTrxFKCol + " = " + editTrxPKCol +
                     " AND " + clientSetupFKCol + " = " + clientSetupPKCol +
                     " AND " + contractSetupFKCol + " = " + contractSetupPKCol;

        try
        {
            conn = ConnectionFactory.getSingleton().getConnection(ConnectionFactory.EDITSOLUTIONS_POOL);

            s = conn.createStatement();

            rs = s.executeQuery(sql);

            while (rs.next())
            {
                totalGrossAmount = totalGrossAmount.addEditBigDecimal(new EDITBigDecimal(rs.getBigDecimal("TotalGrossAmount")));
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
            if (s != null) s.close();
            if (conn != null) conn.close();
        }

        return totalGrossAmount;
    }

    /**
     * Retrieves the total Gross Amount of RW transactions for the specified contract and tax year
     * @param taxYear
     * @param segmentFK
     * @return
     * @throws Exception
     */
    public EDITBigDecimal getRmdsTaxYearToDate(int taxYear, long segmentFK) throws Exception
    {
        Connection conn = null;
        Statement s = null;
        ResultSet rs = null;

        EDITBigDecimal totalGrossAmount = new EDITBigDecimal();

        String editTrxPKCol = EDITTRX_DBTABLE.getDBColumn("EDITTrxPK").getFullyQualifiedColumnName();
        String taxYearCol = EDITTRX_DBTABLE.getDBColumn("TaxYear").getFullyQualifiedColumnName();
        String clientSetupFKCol = EDITTRX_DBTABLE.getDBColumn("ClientSetupFK").getFullyQualifiedColumnName();
        String transactionTypeCTCol = EDITTRX_DBTABLE.getDBColumn("TransactionTypeCT").getFullyQualifiedColumnName();

        String clientSetupPKCol = CLIENT_SETUP_DBTABLE.getDBColumn("ClientSetupPK").getFullyQualifiedColumnName();
        String contractSetupFKCol = CLIENT_SETUP_DBTABLE.getDBColumn("ContractSetupFK").getFullyQualifiedColumnName();

        String contractSetupPKCol = CONTRACT_SETUP_DBTABLE.getDBColumn("ContractSetupPK").getFullyQualifiedColumnName();
        String segmentFKCol = CONTRACT_SETUP_DBTABLE.getDBColumn("SegmentFK").getFullyQualifiedColumnName();

        String editTrxHistoryPKCol = EDITTRX_HISTORY_DBTABLE.getDBColumn("EDITTrxHistoryPK").getFullyQualifiedColumnName();
        String editTrxFKCol = EDITTRX_HISTORY_DBTABLE.getDBColumn("EDITTrxFK").getFullyQualifiedColumnName();

        String grossAmountCol = FINANCIAL_HISTORY_DBTABLE.getDBColumn("GrossAmount").getFullyQualifiedColumnName();
        String editTrxHistoryFKCol = FINANCIAL_HISTORY_DBTABLE.getDBColumn("EDITTrxHistoryFK").getFullyQualifiedColumnName();

        String sql = " SELECT SUM(" + grossAmountCol + ") AS TotalGrossAmount" +
                     " FROM " + FINANCIAL_HISTORY_TABLENAME + ", " + EDITTRX_HISTORY_TABLENAME + ", " +
                      EDITTRX_TABLENAME + ", " + CLIENT_SETUP_TABLENAME + ", " + CONTRACT_SETUP_TABLENAME +
                     " WHERE " + segmentFKCol + " = " + segmentFK +
                     " AND " + transactionTypeCTCol + " = 'RW'" +
                     " AND " + taxYearCol + " = " + taxYear +
                     " AND " + editTrxHistoryFKCol + " = " + editTrxHistoryPKCol +
                     " AND " + editTrxFKCol + " = " + editTrxPKCol +
                     " AND " + clientSetupFKCol + " = " + clientSetupPKCol +
                     " AND " + contractSetupFKCol + " = " + contractSetupPKCol;

        try
        {
            conn = ConnectionFactory.getSingleton().getConnection(ConnectionFactory.EDITSOLUTIONS_POOL);

            s = conn.createStatement();

            rs = s.executeQuery(sql);

            while (rs.next())
            {
                totalGrossAmount = totalGrossAmount.addEditBigDecimal(new EDITBigDecimal(rs.getBigDecimal("TotalGrossAmount")));
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
            if (s != null) s.close();
            if (conn != null) conn.close();
        }

        return totalGrossAmount;
    }

    /**
     * Retrieves the AccumulatedValue for the last CY transaction (if it exists)
     * @param taxYear
     * @param segmentFK
     * @return
     * @throws Exception
     */
    public EDITBigDecimal getPriorCYAccumulatedValue(long segmentFK) throws Exception
    {
        Connection conn = null;
        Statement s = null;
        ResultSet rs = null;

        EDITBigDecimal accumulatedValue = new EDITBigDecimal();

        String editTrxPKCol = EDITTRX_DBTABLE.getDBColumn("EDITTrxPK").getFullyQualifiedColumnName();
        String effectiveDateCol = EDITTRX_DBTABLE.getDBColumn("EffectiveDate").getFullyQualifiedColumnName();
        String clientSetupFKCol = EDITTRX_DBTABLE.getDBColumn("ClientSetupFK").getFullyQualifiedColumnName();
        String transactionTypeCTCol = EDITTRX_DBTABLE.getDBColumn("TransactionTypeCT").getFullyQualifiedColumnName();

        String clientSetupPKCol = CLIENT_SETUP_DBTABLE.getDBColumn("ClientSetupPK").getFullyQualifiedColumnName();
        String contractSetupFKCol = CLIENT_SETUP_DBTABLE.getDBColumn("ContractSetupFK").getFullyQualifiedColumnName();

        String contractSetupPKCol = CONTRACT_SETUP_DBTABLE.getDBColumn("ContractSetupPK").getFullyQualifiedColumnName();
        String segmentFKCol = CONTRACT_SETUP_DBTABLE.getDBColumn("SegmentFK").getFullyQualifiedColumnName();

        String editTrxHistoryPKCol = EDITTRX_HISTORY_DBTABLE.getDBColumn("EDITTrxHistoryPK").getFullyQualifiedColumnName();
        String editTrxFKCol = EDITTRX_HISTORY_DBTABLE.getDBColumn("EDITTrxFK").getFullyQualifiedColumnName();

        String accumulatedValueCol = FINANCIAL_HISTORY_DBTABLE.getDBColumn("AccumulatedValue").getFullyQualifiedColumnName();
        String editTrxHistoryFKCol = FINANCIAL_HISTORY_DBTABLE.getDBColumn("EDITTrxHistoryFK").getFullyQualifiedColumnName();

        String sql = " SELECT " + accumulatedValueCol +
                     " FROM " + FINANCIAL_HISTORY_TABLENAME + ", " + EDITTRX_HISTORY_TABLENAME + ", " +
                     EDITTRX_TABLENAME + ", " + CLIENT_SETUP_TABLENAME + ", " + CONTRACT_SETUP_TABLENAME +
                     " WHERE " + segmentFKCol + " = " + segmentFK +
                     " AND " + transactionTypeCTCol + " = 'CY'" +
                     " AND " + editTrxHistoryFKCol + " = " + editTrxHistoryPKCol +
                     " AND " + editTrxFKCol + " = " + editTrxPKCol +
                     " AND " + clientSetupFKCol + " = " + clientSetupPKCol +
                     " AND " + contractSetupFKCol + " = " + contractSetupPKCol +
                     " AND " + effectiveDateCol + " = (SELECT MAX(EffectiveDate)" +
                     " FROM " + FINANCIAL_HISTORY_TABLENAME + ", " + EDITTRX_HISTORY_TABLENAME + ", " +
                     EDITTRX_TABLENAME + ", " + CLIENT_SETUP_TABLENAME + ", " + CONTRACT_SETUP_TABLENAME +
                     " WHERE " + segmentFKCol + " = " + segmentFK +
                     " AND " + transactionTypeCTCol + " = 'CY'" +
                     " AND " + editTrxHistoryFKCol + " = " + editTrxHistoryPKCol +
                     " AND " + editTrxFKCol + " = " + editTrxPKCol +
                     " AND " + clientSetupFKCol + " = " + clientSetupPKCol +
                     " AND " + contractSetupFKCol + " = " + contractSetupPKCol + ")";

        try
        {
            conn = ConnectionFactory.getSingleton().getConnection(ConnectionFactory.EDITSOLUTIONS_POOL);

            s = conn.createStatement();

            rs = s.executeQuery(sql);

            while (rs.next())
            {
                accumulatedValue = new EDITBigDecimal(rs.getBigDecimal("AccumulatedValue"));
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
            if (s != null) s.close();
            if (conn != null) conn.close();
        }

        return accumulatedValue;
    }

    public EDITTrxVO[] findEDITTrxByEffectiveDateAndTrxTypeCT(String effectiveDate, String transactionTypeCT, long segmentPK, CRUD crud)
    {
        String clientSetupFKCol     = EDITTRX_DBTABLE.getDBColumn("ClientSetupFK").getFullyQualifiedColumnName();
        String effectiveDateCol     = EDITTRX_DBTABLE.getDBColumn("EffectiveDate").getFullyQualifiedColumnName();
        String transactionTypeCTCol = EDITTRX_DBTABLE.getDBColumn("TransactionTypeCT").getFullyQualifiedColumnName();

        String contractSetupFKCol   = CLIENT_SETUP_DBTABLE.getDBColumn("ContractSetupFK").getFullyQualifiedColumnName();
        String clientSetupPKCol     = CLIENT_SETUP_DBTABLE.getDBColumn("ClientSetupPK").getFullyQualifiedColumnName();

        String segmentFKCol         = CONTRACT_SETUP_DBTABLE.getDBColumn("SegmentFK").getFullyQualifiedColumnName();
        String contractSetupPKCol   = CONTRACT_SETUP_DBTABLE.getDBColumn("ContractSetupPK").getFullyQualifiedColumnName();

        String sql = "SELECT " + EDITTRX_TABLENAME + ".* FROM " + EDITTRX_TABLENAME + ", " + CLIENT_SETUP_TABLENAME + ", " + CONTRACT_SETUP_TABLENAME +
                     " WHERE " + segmentFKCol + " = ?" +
                     " AND " + contractSetupFKCol + " = " + contractSetupPKCol +
                     " AND " + clientSetupFKCol + " = " + clientSetupPKCol +
                     " AND " + effectiveDateCol + " = ?" +
                     " AND " + transactionTypeCTCol + " = ?";

        Connection conn = null;

        PreparedStatement ps = null;

        ResultSet rs = null;

        List editTrxVOsList = new ArrayList();

        EDITTrxVO editTrxVO = null;

        try
        {
            conn = crud.getCrudConn();

            ps = conn.prepareStatement(sql);

            ps.setLong(1, segmentPK);
            ps.setDate(2, DBUtil.convertStringToDate(effectiveDate));
            ps.setString(3, transactionTypeCT);

            rs = ps.executeQuery();

            while (rs.next())
            {
                editTrxVO = (EDITTrxVO) CRUD.populateVOFromResultSetRow(rs, VOClass.getVOClassMetaData(EDITTrxVO.class));

                editTrxVOsList.add(editTrxVO);
            }
        }
        catch(Exception e)
        {
            System.out.println(e);
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                //DO NOT close the connection passed in if not null
                if (crud == null)
                {
                    if (conn != null) conn.close();
                }
            }
            catch (SQLException e)
            {
                System.out.println(e);
                e.printStackTrace();
            }
        }

        if (editTrxVOsList.size() == 0)
        {
            return null;
        }
        else
        {
            return (EDITTrxVO[]) editTrxVOsList.toArray(new EDITTrxVO[editTrxVOsList.size()]);
        }
    }


    /**
     * Get List of all PKs for segments with certain product structures and which
     * have a RequiredMinDistribution row for them.
     * Used by RMD Notification.
     * @param productStructurePKs an array of ProductStructure primary keys
     * @return List of SegmentPKs
     * @throws Exception
     */
    public List findAllSegmentPKsWithRequiredMinDistribution(long[] productStructurePKs) throws Exception
    {

        Connection conn = null;
        Statement s = null;
        ResultSet rs = null;

        String productStructureFKCol = SEGMENT_DBTABLE.getDBColumn("ProductStructureFK").getFullyQualifiedColumnName();
        String segmentPKCol =  SEGMENT_DBTABLE.getDBColumn("SegmentPK").getFullyQualifiedColumnName();

        String requiredMinDistSegmentFKCol =
                REQUIRED_MIN_DISTRIBUTION_DBTABLE.getDBColumn("SegmentFK").getFullyQualifiedColumnName();

        String sql = 
                " SELECT DISTINCT " + segmentPKCol +
                " FROM " + SEGMENT_TABLENAME + " , " + REQUIRED_MIN_DISTRIBUTION_TABLENAME +
                " WHERE " + productStructureFKCol + " IN (";

        for (int i = 0; i < productStructurePKs.length; i++)
        {
            if (i < productStructurePKs.length - 1)
            {
                sql += productStructurePKs[i];
                sql += ", ";
            }
            else
            {
                sql += productStructurePKs[i];
            }
        }

        sql += ") AND " + requiredMinDistSegmentFKCol + " = " + segmentPKCol;

        List segmentPKs = new ArrayList();

        try
        {
            conn = ConnectionFactory.getSingleton().getConnection(POOLNAME);

            s = conn.createStatement();

            rs = s.executeQuery(sql);

            while (rs.next())
            {
                segmentPKs.add(new Long(rs.getLong("SegmentPK")));
            }
        }
        catch (Exception e)
        {
            System.out.println(e);
            e.printStackTrace();
            throw e;
        }
        finally
        {
            if (rs != null)
                rs.close();
            if (s != null)
                s.close();
            if (conn != null)
                conn.close();
        }

        return segmentPKs;
    }

    public long[] findBySegmentPK_TrxType_PendingStatus(long segmentPK, String trxType, String pendingStatus, CRUD crud) throws Exception
    {
        Connection conn = null;
        Statement s = null;
        ResultSet rs = null;

        String clientSetupFKCol = EDITTRX_DBTABLE.getDBColumn("ClientSetupFK").getFullyQualifiedColumnName();
        String pendingStatusCol = EDITTRX_DBTABLE.getDBColumn("PendingStatus").getFullyQualifiedColumnName();
        String trxTypeCol = EDITTRX_DBTABLE.getDBColumn("TransactionTypeCT").getFullyQualifiedColumnName();

        String clientSetupPKCol = CLIENT_SETUP_DBTABLE.getDBColumn("ClientSetupPK").getFullyQualifiedColumnName();
        String contractSetupFKCol = CLIENT_SETUP_DBTABLE.getDBColumn("ContractSetupFK").getFullyQualifiedColumnName();

        String contractSetupPKCol = CONTRACT_SETUP_DBTABLE.getDBColumn("ContractSetupPK").getFullyQualifiedColumnName();
        String segmentFKCol = CONTRACT_SETUP_DBTABLE.getDBColumn("SegmentFK").getFullyQualifiedColumnName();

        String sql = "SELECT " + EDITTRX_TABLENAME + ".* FROM " + EDITTRX_TABLENAME + ", " +
                     CLIENT_SETUP_TABLENAME + ", " + CONTRACT_SETUP_TABLENAME +
                     " WHERE " + clientSetupFKCol + " = " + clientSetupPKCol +
                     " AND " + contractSetupFKCol + " = " + contractSetupPKCol +
                     " AND " + segmentFKCol + " = " + segmentPK +
                     " AND " + pendingStatusCol + " = '" + pendingStatus + "'" +
                     " AND " + trxTypeCol + " = '" + trxType + "'";

        List editTrxVOs = new ArrayList();

        try
        {
//            conn = ConnectionFactory.getSingleton().getConnection(POOLNAME);

            conn = crud.getCrudConn();

            s = conn.createStatement();

            rs = s.executeQuery(sql);

            while (rs.next())
            {
                editTrxVOs.add(new Long(rs.getLong("EDITTrxPK")));
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
            if (s != null) s.close();
            if (crud == null)
            {
                if (conn != null) conn.close();
            }
        }

        if (editTrxVOs.size() == 0)
        {
            return null;
        }
        else
        {
            return Util.convertLongToPrim((Long[]) editTrxVOs.toArray(new Long[editTrxVOs.size()]));
        }
    }

    /**
     * Returns the sum of FinancialHistory.GrossAmount for all premium transactions for given segment, taxyear, deposit
     * types and transactions that are processed in given date range.
     * @param segmentPK
     * @param taxYear
     * @param depositTypes
     * @param startDate
     * @param endDate
     * @return
     * @throws Exception
     */
    public EDITBigDecimal getTotalAmountReceivedForPremiumTransactionsBy_SegmentPK_TaxYear_DepositTypes_DateRange(long segmentPK,
                                                                                                               String taxYear,
                                                                                                               String[] depositTypes,
                                                                                                               String startDate,
                                                                                                               String endDate) throws Exception
    {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        String editTrxPKCol         = EDITTRX_DBTABLE.getDBColumn("EDITTrxPK").getFullyQualifiedColumnName();
        String editTrxFKCol         = EDITTRX_HISTORY_DBTABLE.getDBColumn("EDITTrxFK").getFullyQualifiedColumnName();

        String clientSetupFKCol     = EDITTRX_DBTABLE.getDBColumn("ClientSetupFK").getFullyQualifiedColumnName();
        String clientSetupPKCol     = CLIENT_SETUP_DBTABLE.getDBColumn("ClientSetupPK").getFullyQualifiedColumnName();

        String contractSetupFKCol   = CLIENT_SETUP_DBTABLE.getDBColumn("ContractSetupFK").getFullyQualifiedColumnName();
        String contractSetupPKCol   = CONTRACT_SETUP_DBTABLE.getDBColumn("ContractSetupPK").getFullyQualifiedColumnName();

        String depositsEDITTrxFKCol = DEPOSITS_DBTABLE.getDBColumn("EDITTrxFK").getFullyQualifiedColumnName();
        String segmentFKCol         = CONTRACT_SETUP_DBTABLE.getDBColumn("SegmentFK").getFullyQualifiedColumnName();

        String transactionTypeCTCol = EDITTRX_DBTABLE.getDBColumn("TransactionTypeCT").getFullyQualifiedColumnName();
        String taxYearCol           = EDITTRX_DBTABLE.getDBColumn("TaxYear").getFullyQualifiedColumnName();

        String pendingStatusCol     = EDITTRX_DBTABLE.getDBColumn("PendingStatus").getFullyQualifiedColumnName();
        String statusCol            = EDITTRX_DBTABLE.getDBColumn("Status").getFullyQualifiedColumnName();

        String processDateCol       = EDITTRX_HISTORY_DBTABLE.getDBColumn("ProcessDateTime").getFullyQualifiedColumnName();
        String depositTypeCTCol     = DEPOSITS_DBTABLE.getDBColumn("DepositTypeCT").getFullyQualifiedColumnName();

        String amountReceivedCol    = DEPOSITS_DBTABLE.getDBColumn("AmountReceived").getFullyQualifiedColumnName();

        String sql = " SELECT SUM( " + amountReceivedCol + ") AS TotalAmountReceived"  +
                     " FROM " + EDITTRX_TABLENAME +
                     " INNER JOIN " + EDITTRX_HISTORY_TABLENAME +
                     " ON " + editTrxFKCol + " = " + editTrxPKCol +
                     " INNER JOIN " + CLIENT_SETUP_TABLENAME +
                     " ON " + clientSetupFKCol + " = " + clientSetupPKCol +
                     " INNER JOIN " + CONTRACT_SETUP_TABLENAME +
                     " ON " + contractSetupFKCol + " = " + contractSetupPKCol +
                     " INNER JOIN " + DEPOSITS_TABLENAME +
                     " ON " + depositsEDITTrxFKCol + " = " + editTrxPKCol +
                     " WHERE " + segmentFKCol + " = ?" +
                     " AND " + taxYearCol + " = ?" +
                     " AND " + transactionTypeCTCol + " = ?" +
                     " AND " + pendingStatusCol + " = ?" +
                     " AND (" + statusCol + " = ? OR " + statusCol + " = ?)" +
                     " AND " + processDateCol + " >= ?" +
                     " AND " + processDateCol + " <= ?";

        sql = sql + " AND " + depositTypeCTCol + " IN (";

        for( int i = 0 ; i < depositTypes.length; i++)
        {
            sql = sql + "?";

            if ((i+1) != depositTypes.length)
            {
                sql = sql + ",";
            }
        }

        sql = sql + ")";

        EDITBigDecimal totalGrossAmount = new EDITBigDecimal("0");

        try
        {
            EDITDateTime startDateTime = new EDITDateTime(new EDITDate(startDate), EDITDateTime.DEFAULT_MIN_TIME);
            EDITDateTime endDateTime = new EDITDateTime(new EDITDate(endDate), EDITDateTime.DEFAULT_MAX_TIME);

            conn = ConnectionFactory.getSingleton().getConnection(ConnectionFactory.EDITSOLUTIONS_POOL);

            ps = conn.prepareStatement(sql);

            ps.setLong(1, segmentPK);
            ps.setInt(2, Integer.parseInt(taxYear));
            ps.setString(3, EDITTrx.TRANSACTIONTYPECT_PREMIUM);
            ps.setString(4, EDITTrx.PENDINGSTATUS_HISTORY);
            ps.setString(5, EDITTrx.STATUS_NATURAL);
            ps.setString(6, EDITTrx.STATUS_APPLY);
            ps.setTimestamp(7, DBUtil.convertStringToTimestamp(startDateTime.getFormattedDateTime()));
            ps.setTimestamp(8, DBUtil.convertStringToTimestamp(endDateTime.getFormattedDateTime()));

            // need to change the 'i' value depending on the number of previous parameters set.
            // if the number of previous parameters is changed to 9 then i value needs to be set to 10.
            for( int i = 9 ; i < (depositTypes.length + 9); i++)
            {
                ps.setString(i, depositTypes[i-9]);
            }

            rs = ps.executeQuery();

            while (rs.next())
            {
                totalGrossAmount = new EDITBigDecimal(rs.getBigDecimal("TotalAmountReceived"));
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

        return totalGrossAmount;
    }

    /**
     * Returns FinancialHistory.AccumulatedValue for Calendar Year End transaction for given segment and tax year.
     * @param segmentPK
     * @param taxYear
     * @return
     * @throws Exception
     */
    public EDITBigDecimal getAccumulatedValueForCalendarYearEndTransactionBy_SegmentPK_TaxYear(long segmentPK,
                                                                                               String taxYear) throws Exception
    {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        String editTrxPKCol         = EDITTRX_DBTABLE.getDBColumn("EDITTrxPK").getFullyQualifiedColumnName();
        String editTrxFKCol         = EDITTRX_HISTORY_DBTABLE.getDBColumn("EDITTrxFK").getFullyQualifiedColumnName();

        String editTrxHistoryPKCol  = EDITTRX_HISTORY_DBTABLE.getDBColumn("EDITTrxHistoryPK").getFullyQualifiedColumnName();
        String editTrxHistoryFKCol  = FINANCIAL_HISTORY_DBTABLE.getDBColumn("EDITTrxHistoryFK").getFullyQualifiedColumnName();

        String clientSetupFKCol     = EDITTRX_DBTABLE.getDBColumn("ClientSetupFK").getFullyQualifiedColumnName();
        String clientSetupPKCol     = CLIENT_SETUP_DBTABLE.getDBColumn("ClientSetupPK").getFullyQualifiedColumnName();

        String contractSetupFKCol   = CLIENT_SETUP_DBTABLE.getDBColumn("ContractSetupFK").getFullyQualifiedColumnName();
        String contractSetupPKCol   = CONTRACT_SETUP_DBTABLE.getDBColumn("ContractSetupPK").getFullyQualifiedColumnName();

        String segmentFKCol         = CONTRACT_SETUP_DBTABLE.getDBColumn("SegmentFK").getFullyQualifiedColumnName();

        String transactionTypeCTCol = EDITTRX_DBTABLE.getDBColumn("TransactionTypeCT").getFullyQualifiedColumnName();
        String taxYearCol           = EDITTRX_DBTABLE.getDBColumn("TaxYear").getFullyQualifiedColumnName();

        String pendingStatusCol     = EDITTRX_DBTABLE.getDBColumn("PendingStatus").getFullyQualifiedColumnName();
        String statusCol            = EDITTRX_DBTABLE.getDBColumn("Status").getFullyQualifiedColumnName();

        String accumulatedValueCol  = FINANCIAL_HISTORY_DBTABLE.getDBColumn("AccumulatedValue").getFullyQualifiedColumnName();

        String sql = " SELECT " + accumulatedValueCol + " AS AccumulatedValue"  +
                     " FROM " + EDITTRX_TABLENAME +
                     " INNER JOIN " + EDITTRX_HISTORY_TABLENAME +
                     " ON " + editTrxFKCol + " = " + editTrxPKCol +
                     " INNER JOIN " + FINANCIAL_HISTORY_TABLENAME +
                     " ON " + editTrxHistoryFKCol + " = " + editTrxHistoryPKCol +
                     " INNER JOIN " + CLIENT_SETUP_TABLENAME +
                     " ON " + clientSetupFKCol + " = " + clientSetupPKCol +
                     " INNER JOIN " + CONTRACT_SETUP_TABLENAME +
                     " ON " + contractSetupFKCol + " = " + contractSetupPKCol +
                     " WHERE " + segmentFKCol + " = ?" +
                     " AND " + taxYearCol + " = ?" +
                     " AND " + transactionTypeCTCol + " = ?" +
                     " AND " + pendingStatusCol + " = ?" +
                     " AND (" + statusCol + " = ? OR " + statusCol + " = ?)";

        EDITBigDecimal accumulatedValue = new EDITBigDecimal("0");

        try
        {
            conn = ConnectionFactory.getSingleton().getConnection(ConnectionFactory.EDITSOLUTIONS_POOL);

            ps = conn.prepareStatement(sql);

            ps.setLong(1, segmentPK);
            ps.setInt(2, Integer.parseInt(taxYear));
            ps.setString(3, EDITTrx.TRANSACTIONTYPECT_CALENDARYEAREND);
            ps.setString(4, EDITTrx.PENDINGSTATUS_HISTORY);
            ps.setString(5, EDITTrx.STATUS_NATURAL);
            ps.setString(6, EDITTrx.STATUS_APPLY);

            rs = ps.executeQuery();

            while (rs.next())
            {
                accumulatedValue = new EDITBigDecimal(rs.getBigDecimal("AccumulatedValue"));
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

        return accumulatedValue;
    }

    /**
     * Returns FinancialHistory.PriorCostBasis for Complex Change transaction (ComplexTypeCT='ROTH Conversion')
     * for given segment and tax year.
     * @param segmentPK
     * @param taxYear
     * @return
     * @throws Exception
     */
    public EDITBigDecimal getROTHConversionForComplexChangeTransactionBy_SegmentPK_TaxYear(long segmentPK,
                                                                                           String taxYear) throws Exception
    {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        String editTrxPKCol         = EDITTRX_DBTABLE.getDBColumn("EDITTrxPK").getFullyQualifiedColumnName();
        String editTrxFKCol         = EDITTRX_HISTORY_DBTABLE.getDBColumn("EDITTrxFK").getFullyQualifiedColumnName();

        String editTrxHistoryPKCol  = EDITTRX_HISTORY_DBTABLE.getDBColumn("EDITTrxHistoryPK").getFullyQualifiedColumnName();
        String editTrxHistoryFKCol  = FINANCIAL_HISTORY_DBTABLE.getDBColumn("EDITTrxHistoryFK").getFullyQualifiedColumnName();

        String clientSetupFKCol     = EDITTRX_DBTABLE.getDBColumn("ClientSetupFK").getFullyQualifiedColumnName();
        String clientSetupPKCol     = CLIENT_SETUP_DBTABLE.getDBColumn("ClientSetupPK").getFullyQualifiedColumnName();

        String contractSetupFKCol   = CLIENT_SETUP_DBTABLE.getDBColumn("ContractSetupFK").getFullyQualifiedColumnName();
        String contractSetupPKCol   = CONTRACT_SETUP_DBTABLE.getDBColumn("ContractSetupPK").getFullyQualifiedColumnName();

        String segmentFKCol         = CONTRACT_SETUP_DBTABLE.getDBColumn("SegmentFK").getFullyQualifiedColumnName();

        String transactionTypeCTCol = EDITTRX_DBTABLE.getDBColumn("TransactionTypeCT").getFullyQualifiedColumnName();
        String taxYearCol           = EDITTRX_DBTABLE.getDBColumn("TaxYear").getFullyQualifiedColumnName();

        String pendingStatusCol     = EDITTRX_DBTABLE.getDBColumn("PendingStatus").getFullyQualifiedColumnName();
        String statusCol            = EDITTRX_DBTABLE.getDBColumn("Status").getFullyQualifiedColumnName();

        String complextChangeTypeCTCol = CONTRACT_SETUP_DBTABLE.getDBColumn("ComplexChangeTypeCT").getFullyQualifiedColumnName();
        String accumulatedValueCol    = FINANCIAL_HISTORY_DBTABLE.getDBColumn("AccumulatedValue").getFullyQualifiedColumnName();

        String sql = " SELECT " + accumulatedValueCol + " AS AccumulatedValue"  +
                     " FROM " + EDITTRX_TABLENAME +
                     " INNER JOIN " + EDITTRX_HISTORY_TABLENAME +
                     " ON " + editTrxFKCol + " = " + editTrxPKCol +
                     " INNER JOIN " + FINANCIAL_HISTORY_TABLENAME +
                     " ON " + editTrxHistoryFKCol + " = " + editTrxHistoryPKCol +
                     " INNER JOIN " + CLIENT_SETUP_TABLENAME +
                     " ON " + clientSetupFKCol + " = " + clientSetupPKCol +
                     " INNER JOIN " + CONTRACT_SETUP_TABLENAME +
                     " ON " + contractSetupFKCol + " = " + contractSetupPKCol +
                     " WHERE " + segmentFKCol + " = ?" +
                     " AND " + taxYearCol + " = ?" +
                     " AND " + transactionTypeCTCol + " = ?" +
                     " AND " + complextChangeTypeCTCol + " = ?" +
                     " AND " + pendingStatusCol + " = ?" +
                     " AND (" + statusCol + " = ? OR " + statusCol + " = ?)";

        EDITBigDecimal accumulatedValue = new EDITBigDecimal("0");

        try
        {
            conn = ConnectionFactory.getSingleton().getConnection(ConnectionFactory.EDITSOLUTIONS_POOL);

            ps = conn.prepareStatement(sql);

            ps.setLong(1, segmentPK);
            ps.setInt(2, Integer.parseInt(taxYear));
            ps.setString(3, EDITTrx.TRANSACTIONTYPECT_COMPLEXCHANGE);
            ps.setString(4, ContractSetup.COMPLEXCHANGETYPECT_ROTHCONVERSION);
            ps.setString(5, EDITTrx.PENDINGSTATUS_HISTORY);
            ps.setString(6, EDITTrx.STATUS_NATURAL);
            ps.setString(7, EDITTrx.STATUS_APPLY);

            rs = ps.executeQuery();

            while (rs.next())
            {
                accumulatedValue = new EDITBigDecimal(rs.getBigDecimal("AccumulatedValue"));
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

        return accumulatedValue;
    }

    public long[] findQualifyingAdjustments() throws Exception
    {
        Connection conn = null;
        ResultSet rs = null;
        PreparedStatement ps = null;

        String nextDueDateCol   = CHECK_ADJUSTMENT_DBTABLE.getDBColumn("NextDueDate").getFullyQualifiedColumnName();
        String adjustmentCompleteIndCol    = CHECK_ADJUSTMENT_DBTABLE.getDBColumn("AdjustmentCompleteInd").getFullyQualifiedColumnName();

        EDITDate currentDate = new EDITDate();

        String sql = "SELECT " + CHECK_ADJUSTMENT_TABLENAME + ".* FROM " + CHECK_ADJUSTMENT_TABLENAME +
                     " WHERE " + adjustmentCompleteIndCol + " IS NULL " +
                     " AND " + nextDueDateCol + " <= '" + currentDate.getFormattedDate() + "'";

        List checkAdjustmentPKs = new ArrayList();

        try
        {
            conn = ConnectionFactory.getSingleton().getConnection(POOLNAME);

            ps = conn.prepareStatement(sql);

            rs = ps.executeQuery();

            while (rs.next())
            {
                checkAdjustmentPKs.add(new Long(rs.getLong("CheckAdjustmentPK")));
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

        if (checkAdjustmentPKs.size() == 0)
        {
            return null;
        }
        else
        {
            return Util.convertLongToPrim((Long[]) checkAdjustmentPKs.toArray(new Long[checkAdjustmentPKs.size()]));
        }
    }

    public long[] findSuspensePKsForContract(String contractId) throws Exception
    {
        String userDefNumberCol = SUSPENSE_DBTABLE.getDBColumn("UserDefNumber").getFullyQualifiedColumnName();
        String directionCol = SUSPENSE_DBTABLE.getDBColumn(("Direction")).getFullyQualifiedColumnName();

        Connection conn = null;
        Statement s = null;
        ResultSet rs = null;

        String sql = "SELECT * FROM " + SUSPENSE_TABLENAME +
                     " WHERE " + userDefNumberCol + " = '" + contractId +
                     "' AND " + directionCol + " = 'Remove'";

        List suspensePKs = new ArrayList();

        try
        {
            conn = ConnectionFactory.getSingleton().getConnection(POOLNAME);

            s = conn.createStatement();

            rs = s.executeQuery(sql);

            while (rs.next())
            {
                suspensePKs.add(new Long(rs.getLong("SuspensePK")));
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
            if (s != null) s.close();
            if (conn != null) conn.close();
        }

        if (suspensePKs.size() == 0)
        {
            return null;
        }
        else
        {
            return Util.convertLongToPrim((Long[]) suspensePKs.toArray(new Long[suspensePKs.size()]));
        }
    }

    public long[] findSuspensePKsForBankJob() throws Exception
    {
        String userDefNumberCol = SUSPENSE_DBTABLE.getDBColumn("UserDefNumber").getFullyQualifiedColumnName();
        String directionCol = SUSPENSE_DBTABLE.getDBColumn("DirectionCT").getFullyQualifiedColumnName();
        String maintenanceIndCol = SUSPENSE_DBTABLE.getDBColumn("MaintenanceInd").getFullyQualifiedColumnName();

        Connection conn = null;
        Statement s = null;
        ResultSet rs = null;

        String sql = "SELECT * FROM " + SUSPENSE_TABLENAME +
                     " WHERE " + directionCol + " = 'Remove'" +
                     "AND " + maintenanceIndCol  + " = 'M'";

        List suspensePKs = new ArrayList();

        try
        {
            conn = ConnectionFactory.getSingleton().getConnection(POOLNAME);

            s = conn.createStatement();

            rs = s.executeQuery(sql);

            while (rs.next())
            {
                suspensePKs.add(new Long(rs.getLong("SuspensePK")));
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
            if (s != null) s.close();
            if (conn != null) conn.close();
        }

        if (suspensePKs.size() == 0)
        {
            return null;
        }
        else
        {
            return Util.convertLongToPrim((Long[]) suspensePKs.toArray(new Long[suspensePKs.size()]));
        }
    }

    /**
     * Calculation of the OutstandingAdvanceBalance requires retrieval of the positive advance amounts and then
     * subtracting the negative advance amounts to get the total net value.
     * @param placedAgentFKs
     * @param voInclusionList
     * @return
     * @throws Exception
     */
    public EDITBigDecimal getOutstandingAdvanceBalance(long[] placedAgentFKs, List voInclusionList) throws Exception
    {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        String commissionTypeCTCol = COMMISSION_HISTORY_DBTABLE.getDBColumn("CommissionTypeCT").getFullyQualifiedColumnName();
        String placedAgentFKCol = COMMISSION_HISTORY_DBTABLE.getDBColumn("PlacedAgentFK").getFullyQualifiedColumnName();
        String commissionAmountCol = COMMISSION_HISTORY_DBTABLE.getDBColumn("CommissionAmount").getFullyQualifiedColumnName();

        String sqlPlacedAgentPKIn = "";
        for (int i = 0; i < placedAgentFKs.length; i++)
        {
            if (i == placedAgentFKs.length - 1)
            {
                sqlPlacedAgentPKIn = sqlPlacedAgentPKIn + placedAgentFKs[i] + ")";
            }
            else
            {
                sqlPlacedAgentPKIn = sqlPlacedAgentPKIn + placedAgentFKs[i] + ", ";
            }
        }

        String sqlCommissionTypeIn = "('" + CommissionHistory.COMMISSIONTYPECT_ADVANCE + "', '" +
                                       CommissionHistory.COMMISSIONTYPECT_CHARGEBACK_REVERSAL + "', '" +
                                       CommissionHistory.COMMISSIONTYPECT_RECOVERY_CHARGEBACK + "', '" +
                                       CommissionHistory.COMMISSIONTYPECT_ADVANCE_CHARGEBACK_REVERSAL + "')";

        String sql = " SELECT SUM( " + commissionAmountCol + ") AS OutstandingAdvanceBalance"  +
                     " FROM " + COMMISSION_HISTORY_TABLENAME +
                     " WHERE " + placedAgentFKCol + " IN (" + sqlPlacedAgentPKIn +
                     " AND " + commissionTypeCTCol + " IN " + sqlCommissionTypeIn;

        EDITBigDecimal outstandingAdvanceBalance = new EDITBigDecimal();

        try
        {
            conn = ConnectionFactory.getSingleton().getConnection(ConnectionFactory.EDITSOLUTIONS_POOL);

            ps = conn.prepareStatement(sql);

            rs = ps.executeQuery();

            while (rs.next())
            {
                outstandingAdvanceBalance = new EDITBigDecimal(rs.getBigDecimal("OutstandingAdvanceBalance"));
            }

            sqlCommissionTypeIn = "('" + CommissionHistory.COMMISSIONTYPECT_ADVANCE_CHARGEBACK + "', '" +
                                    CommissionHistory.COMMISSIONTYPECT_CHARGEBACK + "', '" +
                                    CommissionHistory.COMMISSIONTYPECT_TERM_ADVANCE_CHARGEBACK + "', '" +
                                    CommissionHistory.COMMISSIONTYPECT_ADVANCE_RECOVERY + "')";

            sql = " SELECT SUM( " + commissionAmountCol + ") AS OutstandingAdvanceBalance"  +
                         " FROM " + COMMISSION_HISTORY_TABLENAME +
                         " WHERE " + placedAgentFKCol + " IN (" + sqlPlacedAgentPKIn +
                         " AND " + commissionTypeCTCol + " IN " + sqlCommissionTypeIn;

            ps = conn.prepareStatement(sql);

            rs = ps.executeQuery();

            while (rs.next())
            {
                outstandingAdvanceBalance = outstandingAdvanceBalance.subtractEditBigDecimal(new EDITBigDecimal(rs.getBigDecimal("OutstandingAdvanceBalance")));
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

        return outstandingAdvanceBalance;
    }

   /**
     * Get Suspense records for the contract number passed in where the pendingSuspenseAmount > 0
     * @param processDate
     * @return array of Suspense keys
     * @throws Exception
     */
    public SuspenseVO[] findByUserDefNumber_PendingSuspenseAmount(String contractNumber, CRUD crud)  throws Exception
    {
        Connection conn = null;
        Statement s = null;
        ResultSet rs = null;

        String pendingSuspenseAmountCol = SUSPENSE_DBTABLE.getDBColumn("PendingSuspenseAmount").getFullyQualifiedColumnName();
        String userDefNumberCol = SUSPENSE_DBTABLE.getDBColumn("UserDefNumber").getFullyQualifiedColumnName();

        String sql = "SELECT * FROM " + SUSPENSE_TABLENAME +
                     " WHERE " + userDefNumberCol + " = '" + contractNumber + "'" +
                     " AND " + pendingSuspenseAmountCol + " > 0";

        List suspenseVOs = new ArrayList();
        SuspenseVO suspenseVO = null;

        try
        {
            if (crud == null)
            {
                conn = ConnectionFactory.getSingleton().getConnection(POOLNAME);
            }
            else
            {
                conn = crud.getCrudConn();
            }

            s = conn.createStatement();

            rs = s.executeQuery(sql);

            while (rs.next())
            {
                suspenseVO = (SuspenseVO) CRUD.populateVOFromResultSetRow(rs, VOClass.getVOClassMetaData(SuspenseVO.class));
                suspenseVOs.add(suspenseVO);
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
            if (s != null) s.close();
            //DO NOT close the connection passed in
            if (crud == null)
            {
                if (conn != null) conn.close();
            }
        }

        if (suspenseVOs.size() == 0)
        {
            return null;
        }
        else
        {
            return (SuspenseVO[]) suspenseVOs.toArray(new SuspenseVO[suspenseVOs.size()]);
        }
    }

   /**
     * Get Suspense records for the contract number passed in where the pendingSuspenseAmount > 0
     * @param processDate
     * @return array of Suspense keys
     * @throws Exception
     */
    public SuspenseVO[] findByUserDefNumber_PendingSuspenseAmountEQZero(String contractNumber, CRUD crud)  throws Exception
    {
        Connection conn = null;
        Statement s = null;
        ResultSet rs = null;

        String pendingSuspenseAmountCol = SUSPENSE_DBTABLE.getDBColumn("PendingSuspenseAmount").getFullyQualifiedColumnName();
        String userDefNumberCol = SUSPENSE_DBTABLE.getDBColumn("UserDefNumber").getFullyQualifiedColumnName();
        String directionCol = SUSPENSE_DBTABLE.getDBColumn("DirectionCT").getFullyQualifiedColumnName();

        String sql = "SELECT * FROM " + SUSPENSE_TABLENAME +
                     " WHERE " + userDefNumberCol + " = '" + contractNumber + "'" +
                     " AND " + pendingSuspenseAmountCol + " = 0" +
                     " AND " + directionCol + " = 'Apply'";

        List suspenseVOs = new ArrayList();
        SuspenseVO suspenseVO = null;

        try
        {
            if (crud == null)
            {
                conn = ConnectionFactory.getSingleton().getConnection(POOLNAME);
            }
            else
            {
                conn = crud.getCrudConn();
            }

            s = conn.createStatement();

            rs = s.executeQuery(sql);

            while (rs.next())
            {
                suspenseVO = (SuspenseVO) CRUD.populateVOFromResultSetRow(rs, VOClass.getVOClassMetaData(SuspenseVO.class));
                suspenseVOs.add(suspenseVO);
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
            if (s != null) s.close();
            //DO NOT close the connection passed in
            if (crud == null)
            {
                if (conn != null) conn.close();
            }
        }

        if (suspenseVOs.size() == 0)
        {
            return null;
        }
        else
        {
            return (SuspenseVO[]) suspenseVOs.toArray(new SuspenseVO[suspenseVOs.size()]);
        }
    }

    public EDITTrxVO[] findMaxEffectiveDate(long segmentPK, EDITDate effectiveDate, String transactionCode, CRUD crud)
    {
        String clientSetupFKCol   = EDITTRX_DBTABLE.getDBColumn("ClientSetupFK").getFullyQualifiedColumnName();
        String effectiveDateCol   = EDITTRX_DBTABLE.getDBColumn("EffectiveDate").getFullyQualifiedColumnName();
        String pendingStatusCol   = EDITTRX_DBTABLE.getDBColumn("PendingStatus").getFullyQualifiedColumnName();
        String transactionTypeCTCol = EDITTRX_DBTABLE.getDBColumn("TransactionTypeCT").getFullyQualifiedColumnName();
        String statusCol          = EDITTRX_DBTABLE.getDBColumn("Status").getFullyQualifiedColumnName();

        String clientSetupPKCol   = CLIENT_SETUP_DBTABLE.getDBColumn("ClientSetupPK").getFullyQualifiedColumnName();
        String contractSetupFKCol = CLIENT_SETUP_DBTABLE.getDBColumn("ContractSetupFK").getFullyQualifiedColumnName();

        String contractSetupPKCol = CONTRACT_SETUP_DBTABLE.getDBColumn("ContractSetupPK").getFullyQualifiedColumnName();
        String segmentFKCol       = CONTRACT_SETUP_DBTABLE.getDBColumn("SegmentFK").getFullyQualifiedColumnName();


        String sql = "SELECT " + EDITTRX_TABLENAME + ".* FROM " + EDITTRX_TABLENAME + ", " +
                      CLIENT_SETUP_TABLENAME + ", " + CONTRACT_SETUP_TABLENAME  +
                     " WHERE " + clientSetupFKCol + " = " + clientSetupPKCol +                      // join
                     " AND " + contractSetupFKCol + " = " + contractSetupPKCol +                    // join
                     " AND " + segmentFKCol + " = ?" +
                     " AND " + pendingStatusCol + " = 'H' " +
                     " AND " + statusCol + " IN ('A', 'N')" +
                     " AND " + transactionTypeCTCol + " = '" + transactionCode + "' " +
                     " AND " + effectiveDateCol + " =  (SELECT MAX (" + effectiveDateCol +
                     ") FROM " + EDITTRX_TABLENAME + ", " + CLIENT_SETUP_TABLENAME + ", " +
                      CONTRACT_SETUP_TABLENAME +
                     " WHERE " + clientSetupFKCol + " = " + clientSetupPKCol +                      // join
                     " AND " + contractSetupFKCol + " = " + contractSetupPKCol +                    // join
                     " AND " + segmentFKCol + " = ?" +
                     " AND " + pendingStatusCol + " = 'H' " +
                     " AND " + statusCol + " IN ('A', 'N')" +
                     " AND " +  transactionTypeCTCol + " = '" + transactionCode + "' " +
                     " AND " + effectiveDateCol + "<= ?)";

        Connection conn = null;
        ResultSet rs = null;
        PreparedStatement ps = null;
        EDITTrxVO editTrxVO = null;
        List editTrxVOs = new ArrayList();

        try
        {
            if (crud == null)
            {
                conn = ConnectionFactory.getSingleton().getConnection(POOLNAME);
            }
            else
            {
                conn = crud.getCrudConn();
            }

            ps = conn.prepareStatement(sql);

            ps.setLong(1, segmentPK);
            ps.setLong(2, segmentPK);
            ps.setDate(3, DBUtil.convertStringToDate(effectiveDate.getFormattedDate()));

            rs = ps.executeQuery();

            while (rs.next())
            {
                editTrxVO = (EDITTrxVO) CRUD.populateVOFromResultSetRow(rs, VOClass.getVOClassMetaData(EDITTrxVO.class));
                editTrxVOs.add(editTrxVO);
            }

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

                if (crud == null)
                {
                    if (conn != null) conn.close();
                }
            }
            catch (SQLException e)
            {
                System.out.println(e);

                e.printStackTrace();

                throw new RuntimeException(e);
            }
        }

        if (editTrxVOs.size() == 0)
        {
            return null;
        }
        else
        {
            return (EDITTrxVO[]) editTrxVOs.toArray(new EDITTrxVO[editTrxVOs.size()]);
        }
    }

    public EDITTrxVO[] findClaimPayoutTrx(long segmentPK, EDITDate effectiveDate, CRUD crud)
    {
        String clientSetupFKCol   = EDITTRX_DBTABLE.getDBColumn("ClientSetupFK").getFullyQualifiedColumnName();
        String effectiveDateCol   = EDITTRX_DBTABLE.getDBColumn("EffectiveDate").getFullyQualifiedColumnName();
        String pendingStatusCol   = EDITTRX_DBTABLE.getDBColumn("PendingStatus").getFullyQualifiedColumnName();
        String transactionTypeCTCol = EDITTRX_DBTABLE.getDBColumn("TransactionTypeCT").getFullyQualifiedColumnName();
        String statusCol          = EDITTRX_DBTABLE.getDBColumn("Status").getFullyQualifiedColumnName();

        String clientSetupPKCol   = CLIENT_SETUP_DBTABLE.getDBColumn("ClientSetupPK").getFullyQualifiedColumnName();
        String contractSetupFKCol = CLIENT_SETUP_DBTABLE.getDBColumn("ContractSetupFK").getFullyQualifiedColumnName();

        String contractSetupPKCol = CONTRACT_SETUP_DBTABLE.getDBColumn("ContractSetupPK").getFullyQualifiedColumnName();
        String segmentFKCol       = CONTRACT_SETUP_DBTABLE.getDBColumn("SegmentFK").getFullyQualifiedColumnName();


        String sql = "SELECT " + EDITTRX_TABLENAME + ".* FROM " + EDITTRX_TABLENAME + ", " +
                      CLIENT_SETUP_TABLENAME + ", " +
                      CONTRACT_SETUP_TABLENAME  +
                     " WHERE " + clientSetupFKCol + " = " + clientSetupPKCol +                      // join
                     " AND " + contractSetupFKCol + " = " + contractSetupPKCol +                    // join
                     " AND " + segmentFKCol + " = ?" +
                     " AND " + pendingStatusCol + " = 'H' " +
                     " AND " + statusCol + " IN ('A', 'N')" +
                     " AND " +  transactionTypeCTCol + " = 'CPO'" +
                     " AND " + effectiveDateCol + " =  (SELECT MIN (" + effectiveDateCol +
                     ") FROM " + EDITTRX_TABLENAME + ", " + CLIENT_SETUP_TABLENAME + ", " +
                      CONTRACT_SETUP_TABLENAME +
                     " WHERE " + clientSetupFKCol + " = " + clientSetupPKCol +                      // join
                     " AND " + contractSetupFKCol + " = " + contractSetupPKCol +                    // join
                     " AND " + segmentFKCol + " = ?" +
                     " AND " + pendingStatusCol + " = 'H' " +
                     " AND " + statusCol + " IN ('A', 'N')" +
                     " AND " +  transactionTypeCTCol + " = 'CPO'" +
                     " AND " + effectiveDateCol + "> ?)";

        Connection conn = null;
        ResultSet rs = null;
        PreparedStatement ps = null;
        EDITTrxVO editTrxVO = null;
        List editTrxVOs = new ArrayList();

        try
        {
            if (crud == null)
            {
                conn = ConnectionFactory.getSingleton().getConnection(POOLNAME);
            }
            else
            {
                conn = crud.getCrudConn();
            }

            ps = conn.prepareStatement(sql);

            ps.setLong(1, segmentPK);
            ps.setLong(2, segmentPK);
            ps.setDate(3, DBUtil.convertStringToDate(effectiveDate.getFormattedDate()));

            rs = ps.executeQuery();

            while (rs.next())
            {
                editTrxVO = (EDITTrxVO) CRUD.populateVOFromResultSetRow(rs, VOClass.getVOClassMetaData(EDITTrxVO.class));
                editTrxVOs.add(editTrxVO);
            }

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

                if (crud == null)
                {
                    if (conn != null) conn.close();
                }
            }
            catch (SQLException e)
            {
                System.out.println(e);

                e.printStackTrace();

                throw new RuntimeException(e);
            }
        }

        if (editTrxVOs.size() == 0)
        {
            return null;
        }
        else
        {
            return (EDITTrxVO[]) editTrxVOs.toArray(new EDITTrxVO[editTrxVOs.size()]);
        }
    }
}
