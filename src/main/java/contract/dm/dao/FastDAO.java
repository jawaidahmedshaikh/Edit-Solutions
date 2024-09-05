/*
 * User: unknown
 * Date: Jan 1, 2000
 * Time: unknown
 *
 * (c) 2000-2008 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */

package contract.dm.dao;

import edit.services.db.*;
import edit.common.vo.*;
import edit.common.*;
import fission.utility.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FastDAO extends AbstractFastDAO
{
    private final String POOLNAME;

    private final DBTable CONTRACT_CLIENT_DBTABLE;
    private final DBTable INVESTMENT_DBTABLE;
    private final DBTable BUCKET_DBTABLE;
    private final DBTable SEGMENT_DBTABLE;
    private final DBTable LIFE_DBTABLE;

    private final String CONTRACT_CLIENT_TABLENAME;
    private final String INVESTMENT_TABLENAME;
    private final String BUCKET_TABLENAME;
    private final String SEGMENT_TABLENAME;
    private final String LIFE_TABLENAME;

    private final DBTable EDITTRX_DBTABLE;
    private final DBTable CLIENT_SETUP_DBTABLE;
    private final DBTable CONTRACT_SETUP_DBTABLE;
    private final DBTable PREMIUM_DUE_DBTABLE;

    private final String EDITTRX_TABLENAME;
    private final String CLIENT_SETUP_TABLENAME;
    private final String CONTRACT_SETUP_TABLENAME;
    private final String PREMIUM_DUE_TABLENAME;

    public FastDAO()
    {
        POOLNAME  = ConnectionFactory.EDITSOLUTIONS_POOL;

        CONTRACT_CLIENT_DBTABLE = DBTable.getDBTableForTable("ContractClient");
        INVESTMENT_DBTABLE      = DBTable.getDBTableForTable("Investment");
        BUCKET_DBTABLE          = DBTable.getDBTableForTable("Bucket");
        SEGMENT_DBTABLE         = DBTable.getDBTableForTable("Segment");
        LIFE_DBTABLE            = DBTable.getDBTableForTable("Life");

        CONTRACT_CLIENT_TABLENAME = CONTRACT_CLIENT_DBTABLE.getFullyQualifiedTableName();
        INVESTMENT_TABLENAME      = INVESTMENT_DBTABLE.getFullyQualifiedTableName();
        BUCKET_TABLENAME          = BUCKET_DBTABLE.getFullyQualifiedTableName();
        SEGMENT_TABLENAME         = SEGMENT_DBTABLE.getFullyQualifiedTableName();
        LIFE_TABLENAME            = LIFE_DBTABLE.getFullyQualifiedTableName();


        EDITTRX_DBTABLE        = DBTable.getDBTableForTable("EDITTrx");
        CLIENT_SETUP_DBTABLE   = DBTable.getDBTableForTable("ClientSetup");
        CONTRACT_SETUP_DBTABLE = DBTable.getDBTableForTable("ContractSetup");

        EDITTRX_TABLENAME        = EDITTRX_DBTABLE.getFullyQualifiedTableName();
        CLIENT_SETUP_TABLENAME   = CLIENT_SETUP_DBTABLE.getFullyQualifiedTableName();
        CONTRACT_SETUP_TABLENAME = CONTRACT_SETUP_DBTABLE.getFullyQualifiedTableName();

        PREMIUM_DUE_DBTABLE = DBTable.getDBTableForTable("PremiumDue");
        PREMIUM_DUE_TABLENAME = PREMIUM_DUE_DBTABLE.getFullyQualifiedTableName();
    }

    /**
     * Finds the LapsePendingDate from the associated ContractClient.
     * @param contractClientPK
     * @return
     */
    public String findLapsePendingDateBySegmentPK(long segmentPK)
    {
        Connection conn = null;
        Statement s = null;
        ResultSet rs = null;

        String lapsePendingDateCol = LIFE_DBTABLE.getDBColumn("LapsePendingDate").getFullyQualifiedColumnName();
        String lifeSegmentFKCol    = LIFE_DBTABLE.getDBColumn("SegmentFK").getFullyQualifiedColumnName();

        String sql = "SELECT " + lapsePendingDateCol + " FROM " + LIFE_TABLENAME +
                     " WHERE " + lifeSegmentFKCol + " = " + segmentPK;

        String lapsePendingDate = null;

        try
        {
            conn = ConnectionFactory.getSingleton().getConnection(POOLNAME);

            s = conn.createStatement();

            rs = s.executeQuery(sql);

            if (rs.next())
            {
                lapsePendingDate = DBUtil.readAndConvertDate(rs, 1);
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
                if (rs != null) rs.close();
                if (s != null) s.close();
                if (conn != null) conn.close();
            }
            catch (SQLException e)
            {
                System.out.println(e);

                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.

                throw new RuntimeException(e);
            }
        }

        return lapsePendingDate;
    }

    /**
     * Finds the Life.LapseDate from the associated Contract.
     * @param contractClientPK
     * @return
     */
    public String findLapseDateBySegmentPK(long segmentPK)
    {
        Connection conn = null;
        Statement s = null;
        ResultSet rs = null;

        String segmentFKCol = LIFE_DBTABLE.getDBColumn("SegmentFK").getFullyQualifiedColumnName();
        String lapseDateCol        = LIFE_DBTABLE.getDBColumn("LapseDate").getFullyQualifiedColumnName();

        String sql = "SELECT " + lapseDateCol + " FROM " + LIFE_TABLENAME +
                     " WHERE " + segmentFKCol + " = " + segmentPK;

        String lapseDate = null;

        try
        {
            conn = ConnectionFactory.getSingleton().getConnection(POOLNAME);

            s = conn.createStatement();

            rs = s.executeQuery(sql);

            if (rs.next())
            {
                lapseDate = DBUtil.readAndConvertDate(rs, 1);
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
                if (rs != null) rs.close();
                if (s != null) s.close();
                if (conn != null) conn.close();
            }
            catch (SQLException e)
            {
                System.out.println(e);

                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.

                throw new RuntimeException(e);
            }
        }

        return lapseDate;
    }

    public long findFilteredFundPKByBucketPK(long bucketPK) throws Exception
    {
        Connection conn = null;
        Statement s = null;
        ResultSet rs = null;

        String filteredFundFKCol = INVESTMENT_DBTABLE.getDBColumn("FilteredFundFK").getFullyQualifiedColumnName();
        String investmentPKCol   = INVESTMENT_DBTABLE.getDBColumn("InvestmentPK").getFullyQualifiedColumnName();

        String bucketPKCol       = BUCKET_DBTABLE.getDBColumn("BucketPK").getFullyQualifiedColumnName();
        String investmentFKCol   = BUCKET_DBTABLE.getDBColumn("InvestmentFK").getFullyQualifiedColumnName();

        String sql = "SELECT " + filteredFundFKCol + " FROM " + INVESTMENT_TABLENAME + ", " + BUCKET_TABLENAME +
                     " WHERE " + bucketPKCol + " = " + bucketPK +
                     " AND " + investmentFKCol + " = " + investmentPKCol;

        long filteredFundPK = 0;

        try
        {
            conn = ConnectionFactory.getSingleton().getConnection(ConnectionFactory.EDITSOLUTIONS_POOL);

            s = conn.createStatement();

            rs = s.executeQuery(sql);

            while (rs.next())
            {
                filteredFundPK = rs.getLong("FilteredFundFK");
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

        return filteredFundPK;
    }

    /**
     * Finds the set of all base SegmentPKs for a given ProductStructurePK and SegmentStatusCT. Riders are not included in this list.
     * @param productStructurePK
     * @param segmentStatusCT
     * @return
     */
    public long[] findBaseSegmentPKsByProductStructurePKAndSegmentStatusCT(Long productStructurePK, String[] segmentStatusCT)
    {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sqlIn = new String();

        for (int i = 0; i < segmentStatusCT.length; i++)
        {
            sqlIn = sqlIn + "'" + segmentStatusCT[i] + "'";

            if (i < segmentStatusCT.length - 1)
            {
                sqlIn = sqlIn + ", ";
            }
        }

        String productStructureFKCol = SEGMENT_DBTABLE.getDBColumn("ProductStructureFK").getFullyQualifiedColumnName();
        String segmentFKCol          = SEGMENT_DBTABLE.getDBColumn("SegmentFK").getFullyQualifiedColumnName();
        String segmentStatusCTCol    = SEGMENT_DBTABLE.getDBColumn("SegmentStatusCT").getFullyQualifiedColumnName();
        String segmentPKCol          = SEGMENT_DBTABLE.getDBColumn("SegmentPK").getFullyQualifiedColumnName();

        String sql = "SELECT " + segmentPKCol + " FROM " + SEGMENT_TABLENAME +
                     " WHERE " + productStructureFKCol + " = ?"+
                     " AND " + segmentFKCol + " IS NULL" +
                     " AND " + segmentStatusCTCol + " IN (";

        for (int i = 0; i < segmentStatusCT.length; i++)
        {
            if (i < (segmentStatusCT.length - 1))
            {
                sql += "?, ";
            }
            else
            {
                sql += "?";
            }
        }

        sql += ")";

        List pks = new ArrayList();

        try
        {
            conn = ConnectionFactory.getSingleton().getConnection(POOLNAME);

            ps = conn.prepareStatement(sql);

            ps.setLong(1, productStructurePK.longValue());

            for (int i = 0; i < segmentStatusCT.length; i++)
            {
                String currentSegmentStatus = segmentStatusCT[i];

                ps.setString(i + 2, currentSegmentStatus);
            }

            rs = ps.executeQuery();

            while (rs.next())
            {
                long pk = rs.getLong("SegmentPK");

                pks.add(new Long(pk));
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
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (conn != null) conn.close();
            }
            catch (SQLException e)
            {
                System.out.println(e);

                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.

                throw new RuntimeException(e);
            }
        }

        return Util.convertLongToPrim((Long[]) pks.toArray(new Long[pks.size()]));
    }

    /**
     * Finds the set of all base SegmentPKs for a given ProductStructurePK - Riders are not included in this list.
     * @param productStructurePK
     * @return
     */
    public long[] findBaseSegmentPKsByProductStructureFK(long productStructureFK)
    {
        Connection conn = null;
        Statement s = null;
        ResultSet rs = null;

        String productStructureFKCol = SEGMENT_DBTABLE.getDBColumn("ProductStructureFK").getFullyQualifiedColumnName();
        String segmentFKCol          = SEGMENT_DBTABLE.getDBColumn("SegmentFK").getFullyQualifiedColumnName();
        String segmentPKCol          = SEGMENT_DBTABLE.getDBColumn("SegmentPK").getFullyQualifiedColumnName();

        String sql = "SELECT " + segmentPKCol + " FROM " + SEGMENT_TABLENAME +
                     " WHERE " + productStructureFKCol + " = " + productStructureFK +
                     " AND " + segmentFKCol + " IS NULL";

        List pks = new ArrayList();

        try
        {
            conn = ConnectionFactory.getSingleton().getConnection(POOLNAME);

            s = conn.createStatement();

            rs = s.executeQuery(sql);

            while (rs.next())
            {
                long pk = rs.getLong("SegmentPK");

                pks.add(new Long(pk));
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
                if (rs != null) rs.close();
                if (s != null) s.close();
                if (conn != null) conn.close();
            }
            catch (SQLException e)
            {
                System.out.println(e);

                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.

                throw new RuntimeException(e);
            }
        }

        return Util.convertLongToPrim((Long[]) pks.toArray(new Long[pks.size()]));
    }

    /**
     * Finds the set of all base SegmentPKs for a given set of ProductStructurePKs - Riders are not included in this list.
     * @param productStructurePK
     * @return
     */
    public long[] findBaseSegmentPKsByProductStructureFK(long[] productStructureFKs)
    {
        Connection conn = null;
        Statement s = null;
        ResultSet rs = null;

        String productStructureFKCol = SEGMENT_DBTABLE.getDBColumn("ProductStructureFK").getFullyQualifiedColumnName();
        String segmentFKCol          = SEGMENT_DBTABLE.getDBColumn("SegmentFK").getFullyQualifiedColumnName();
        String segmentPKCol          = SEGMENT_DBTABLE.getDBColumn("SegmentPK").getFullyQualifiedColumnName();

        String sqlIn = new String();

        for (int i = 0; i < productStructureFKs.length; i++)
        {
            sqlIn = sqlIn  + productStructureFKs[i];

            if (i < productStructureFKs.length - 1)
            {
                sqlIn = sqlIn + ", ";
            }
        }

        String sql = "SELECT " + segmentPKCol + " FROM " + SEGMENT_TABLENAME +
                     " WHERE " + productStructureFKCol + " IN (" + sqlIn + ")" +
                     " AND " + segmentFKCol + " IS NULL";

        List pks = new ArrayList();

        try
        {
            conn = ConnectionFactory.getSingleton().getConnection(POOLNAME);

            s = conn.createStatement();

            rs = s.executeQuery(sql);

            while (rs.next())
            {
                long pk = rs.getLong("SegmentPK");

                pks.add(new Long(pk));
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
                if (rs != null) rs.close();
                if (s != null) s.close();
                if (conn != null) conn.close();
            }
            catch (SQLException e)
            {
                System.out.println(e);

                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.

                throw new RuntimeException(e);
            }
        }

        return Util.convertLongToPrim((Long[]) pks.toArray(new Long[pks.size()]));
    }

    /**
     * Finder by PK and using passed in db connection.
     * @param segmentPK
     * @param crud
     * @return
     */
    public SegmentVO findSegmentBySegmentPK(long segmentPK, CRUD crud)
    {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        SegmentVO segmentVO = null;

        String segmentPKCol = SEGMENT_DBTABLE.getDBColumn("SegmentPK").getFullyQualifiedColumnName();

        String sql = " SELECT * FROM " + SEGMENT_TABLENAME +
                     " WHERE " + segmentPKCol + " = " + segmentPK;

        conn = crud.getCrudConn();

        try
        {
            ps = conn.prepareStatement(sql);

            rs = ps.executeQuery();

            rs.next();

            segmentVO = (SegmentVO) CRUD.populateVOFromResultSetRow(rs, VOClass.getVOClassMetaData(SegmentVO.class));
        }
        catch (SQLException e)
        {
            System.out.println(e);

            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.

            throw new RuntimeException(e);
        }
        finally
        {
            try
            {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                // Do not close db connection.
            }
            catch (SQLException e)
            {
                System.out.println(e);

                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.

                throw new RuntimeException(e);
            }
        }

        return segmentVO;
    }

    public InvestmentVO[] findInvestmentBy_SegmentPK(long segmentPK, CRUD crud)
    {
        String segmentFKCol = INVESTMENT_DBTABLE.getDBColumn("SegmentFK").getFullyQualifiedColumnName();

        String sql = "SELECT * FROM " + INVESTMENT_TABLENAME +
                     " WHERE " + segmentFKCol + " = " + segmentPK;

        Connection conn = null;
        Statement s = null;
        ResultSet rs = null;

        List investmentVOsList = new ArrayList();
        InvestmentVO investmentVO = null;

        try
        {
            conn = crud.getCrudConn();
            s = conn.createStatement();
            rs = s.executeQuery(sql);

            while (rs.next())
            {
                investmentVO = (InvestmentVO) CRUD.populateVOFromResultSetRow(rs, VOClass.getVOClassMetaData(InvestmentVO.class));

                investmentVOsList.add(investmentVO);
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
                if (s != null) s.close();
            }
            catch (SQLException e)
            {
                System.out.println(e);
                e.printStackTrace();
            }
        }

        if (investmentVOsList.size() == 0)
        {
            return null;
        }
        else
        {
            return (InvestmentVO[]) investmentVOsList.toArray(new InvestmentVO[investmentVOsList.size()]);
        }
    }
    
    public List<Long> findAllSegmentPKsForBatchCycle(long[] productStructurePKs, String[] nonActiveStatuses, String effectiveDate, String[] pendingStatuses)
    {
         String segmentPKCol = SEGMENT_DBTABLE.getDBColumn("SegmentPK").getFullyQualifiedColumnName();
         String fromSQL = formatBatchStartupQuery(productStructurePKs, nonActiveStatuses, pendingStatuses);

         Connection conn = null;

         PreparedStatement ps = null;

         ResultSet rs = null;

         String sql = "SELECT  DISTINCT "+ segmentPKCol + fromSQL;

         List<Long> segmentKeys = Collections.synchronizedList(new ArrayList<Long>()); // new ArrayList<>();
         
         try
         {
             conn = ConnectionFactory.getSingleton().getConnection(POOLNAME);

             ps = conn.prepareStatement(sql);

             ps.setDate(1, DBUtil.convertStringToDate(effectiveDate));

             rs = ps.executeQuery();

             while (rs.next())
             {
                 segmentKeys.add(rs.getLong("SegmentPK"));
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

         return segmentKeys;
    }

    private String formatBatchStartupQuery(long[] productStructurePKs, String[] nonActiveStatuses, String[] pendingStatuses)
    {
        String productStructureFKCol = SEGMENT_DBTABLE.getDBColumn("ProductStructureFK").getFullyQualifiedColumnName();
        String segmentStatusCTCol    = SEGMENT_DBTABLE.getDBColumn("SegmentStatusCT").getFullyQualifiedColumnName();
        String segmentPKCol          = SEGMENT_DBTABLE.getDBColumn("SegmentPK").getFullyQualifiedColumnName();

        String clientSetupFKCol   = EDITTRX_DBTABLE.getDBColumn("ClientSetupFK").getFullyQualifiedColumnName();
        String pendingStatusCol   = EDITTRX_DBTABLE.getDBColumn("PendingStatus").getFullyQualifiedColumnName();
        String effectiveDateCol   = EDITTRX_DBTABLE.getDBColumn("EffectiveDate").getFullyQualifiedColumnName();
        String statusCol          = EDITTRX_DBTABLE.getDBColumn("Status").getFullyQualifiedColumnName();

        String clientSetupPKCol   = CLIENT_SETUP_DBTABLE.getDBColumn("ClientSetupPK").getFullyQualifiedColumnName();
        String contractSetupFKCol = CLIENT_SETUP_DBTABLE.getDBColumn("ContractSetupFK").getFullyQualifiedColumnName();

        String contractSetupPKCol = CONTRACT_SETUP_DBTABLE.getDBColumn("ContractSetupPK").getFullyQualifiedColumnName();
        String segmentFKCol       = CONTRACT_SETUP_DBTABLE.getDBColumn("SegmentFK").getFullyQualifiedColumnName();

        //ProductStructures to include
        String sqlIn1 = new String();
        for (int i = 0; i < productStructurePKs.length; i++)
         {
             if (i < productStructurePKs.length - 1)
             {
                 sqlIn1 += productStructurePKs[i];

                 sqlIn1 += ", ";
             }
             else
             {
                 sqlIn1 += productStructurePKs[i];
             }
         }

        //Segment statuses to exclude
        String sqlIn2 = new String();
        for (int i = 0; i < nonActiveStatuses.length; i++)
        {
            if (i < nonActiveStatuses.length - 1)
            {
                sqlIn2 += nonActiveStatuses[i];
                sqlIn2 += "' AND NOT " + segmentStatusCTCol + " = '";
            }
            else
            {
                sqlIn2 += nonActiveStatuses[i] +  "')";
            }
        }

        //EDITTrx statuses to include
        String sqlIn3 = new String();

        for (int i = 0; i < pendingStatuses.length; i++)
        {
            if (i < pendingStatuses.length - 1)
            {
                sqlIn3 += pendingStatuses[i] + "', '";
            }
            else
            {
                sqlIn3 += pendingStatuses[i] + "')";
            }
        }

        String sql = " FROM " + SEGMENT_TABLENAME + " INNER JOIN " + CONTRACT_SETUP_TABLENAME + " ON " +
                     segmentPKCol + " = " + segmentFKCol +  " INNER JOIN " + CLIENT_SETUP_TABLENAME + " ON " +
                     contractSetupPKCol + " = " + contractSetupFKCol +  " INNER JOIN " +  EDITTRX_TABLENAME + " ON " +
                     clientSetupPKCol + " = " + clientSetupFKCol +
                     " WHERE " + productStructureFKCol + " IN (" + sqlIn1 + ")" +
                     " AND (NOT " + segmentStatusCTCol + "= '" + sqlIn2 +
                     " AND (" + pendingStatusCol + " IN ('" + sqlIn3 +
                     " AND " + statusCol + " IN ('N', 'A')" +
                     " AND " + effectiveDateCol + " <= ?" + ")";


        return sql;
    }

    public PremiumDueVO[] findPremiumDueBySegmentPK(long segmentPK, EDITDate effectiveDate, CRUD crud)
    {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        String segmentFKCol = PREMIUM_DUE_DBTABLE.getDBColumn("SegmentFK").getFullyQualifiedColumnName();
        String pendingExtractIndicatorCol = PREMIUM_DUE_DBTABLE.getDBColumn("PendingExtractIndicator").getFullyQualifiedColumnName();
        String effectiveDateCol = PREMIUM_DUE_DBTABLE.getDBColumn("EffectiveDate").getFullyQualifiedColumnName();
        String premiumDuePKCol = PREMIUM_DUE_DBTABLE.getDBColumn("PremiumDuePK").getFullyQualifiedColumnName();

        String sql = "SELECT * FROM " + PREMIUM_DUE_TABLENAME +
                     " WHERE " + segmentFKCol + " = " + segmentPK +
                     " AND " + pendingExtractIndicatorCol + " NOT IN ('R')" +
                     " AND " + effectiveDateCol + " = (Select MAX(" + effectiveDateCol + ") FROM " + PREMIUM_DUE_TABLENAME +
                     " WHERE " + segmentFKCol + " = " + segmentPK +
                     " AND " + pendingExtractIndicatorCol + " NOT IN ('R')" +
                     " AND " + effectiveDateCol + " <= ?)" +
                     " ORDER BY " + premiumDuePKCol + " DESC";

        PremiumDueVO premiumDueVO = null;
        List premiumDueList = new ArrayList();

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

             ps.setDate(1, DBUtil.convertStringToDate(effectiveDate.getFormattedDate()));

             rs = ps.executeQuery();

            while (rs.next())
            {
                premiumDueVO = (PremiumDueVO) CRUD.populateVOFromResultSetRow(rs, VOClass.getVOClassMetaData(PremiumDueVO.class));

                premiumDueList.add(premiumDueVO);
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
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                ps = null;
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

                throw new RuntimeException(e);
            }
        }

        return  (PremiumDueVO[]) premiumDueList.toArray(new PremiumDueVO[premiumDueList.size()]);
    }
    
    public PremiumDueVO[] findPremiumDueBySegmentPKToReset(long segmentPK, long editTrxFK, EDITDate effectiveDate, CRUD crud)
    {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        String segmentFKCol = PREMIUM_DUE_DBTABLE.getDBColumn("SegmentFK").getFullyQualifiedColumnName();
        String pendingExtractIndicatorCol = PREMIUM_DUE_DBTABLE.getDBColumn("PendingExtractIndicator").getFullyQualifiedColumnName();
        String effectiveDateCol = PREMIUM_DUE_DBTABLE.getDBColumn("EffectiveDate").getFullyQualifiedColumnName();
        String premiumDuePKCol = PREMIUM_DUE_DBTABLE.getDBColumn("PremiumDuePK").getFullyQualifiedColumnName();
        String EDITTrxFKCol = PREMIUM_DUE_DBTABLE.getDBColumn("EDITTrxFK").getFullyQualifiedColumnName();

        String sql = "SELECT * FROM " + PREMIUM_DUE_TABLENAME +
                     " WHERE " + segmentFKCol + " = " + segmentPK +
                     " AND " + pendingExtractIndicatorCol + " NOT IN ('R')" +
                     " AND " + pendingExtractIndicatorCol + " IN ('A','B','P','M','U','E') " +
                     " AND " + EDITTrxFKCol + " <> " + editTrxFK +
                     " AND " + effectiveDateCol + " = (Select MAX(" + effectiveDateCol + ") FROM " + PREMIUM_DUE_TABLENAME +
                     " WHERE " + segmentFKCol + " = " + segmentPK +
                     " AND " + pendingExtractIndicatorCol + " NOT IN ('R')" +
                     " AND " + effectiveDateCol + " <= ?)" +
                     " ORDER BY " + premiumDuePKCol + " DESC";

        PremiumDueVO premiumDueVO = null;
        List premiumDueList = new ArrayList();

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

             ps.setDate(1, DBUtil.convertStringToDate(effectiveDate.getFormattedDate()));

             rs = ps.executeQuery();

            while (rs.next())
            {
                premiumDueVO = (PremiumDueVO) CRUD.populateVOFromResultSetRow(rs, VOClass.getVOClassMetaData(PremiumDueVO.class));

                premiumDueList.add(premiumDueVO);
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
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                ps = null;
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

                throw new RuntimeException(e);
            }
        }

        return  (PremiumDueVO[]) premiumDueList.toArray(new PremiumDueVO[premiumDueList.size()]);
    }


    public static void main(String[] args)
    {
        FastDAO fastDAO = new FastDAO();

        String[] nonActiveStatuses = new String[]
            {
                "Frozen", "NotTaken", "Pending", "Surrendered", "Terminated"
            };

        long[] csKeys = new long[] {1095789050991L, 1073582839741L};
        String[] pendingStatuses = new String[] { "B", "C", "F", "P", "S" };

         String segmentPKCol = DBTable.getDBTableForTable("Segment").getDBColumn("SegmentPK").getFullyQualifiedColumnName();
         String param = "COUNT " + segmentPKCol;

//        fastDAO.formatBatchStartupQuery(csKeys, nonActiveStatuses, "2005/06/30", pendingStatuses);

        fastDAO.findAllSegmentPKsForBatchCycle(csKeys, nonActiveStatuses, "2005/06/30", pendingStatuses);
    }

}