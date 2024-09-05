/*
 * User: sprasad
 * Date: Jan 10, 2005
 * Time: 5:05:56 PM
 *
 * (c) 2000-2005 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package engine.dm.dao;

import edit.common.vo.FeeVO;
import edit.common.vo.FilteredFundVO;

import edit.services.db.ConnectionFactory;
import edit.services.db.DAO;
import edit.services.db.DBTable;
import edit.services.db.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class FeeDAO extends DAO
{
    private final String POOLNAME;
    private final DBTable DBTABLE;
    private final String TABLENAME;

    public FeeDAO()
    {
        POOLNAME = ConnectionFactory.ENGINE_POOL;
        DBTABLE = DBTable.getDBTableForTable("Fee");
        TABLENAME = DBTABLE.getFullyQualifiedTableName();
    }

    /**
     * Finder.
     */
    public FeeVO[] findAll()
    {
        String sql = " SELECT * FROM " + TABLENAME;

        return (FeeVO[]) executeQuery(FeeVO.class,
                                      sql,
                                      POOLNAME,
                                      false,
                                      null);
    }

    /**
     * Finder method by PK.
     *
     * @param feePK
     */
    public FeeVO[] findByPK(long feePK)
    {
        String feePKCol = DBTABLE.getDBColumn("FeePK").getFullyQualifiedColumnName();

        String sql = " SELECT * FROM " + TABLENAME +
                " WHERE " + feePKCol + " = " + feePK;

        return (FeeVO[]) executeQuery(FeeVO.class, sql, POOLNAME, false, null);
    }

    /**
     * Finder method by feeDescriptionPK
     * @param feeDescriptionPK
     * @return
     */
    public FeeVO[] findByFeeDescriptionPK(long feeDescriptionPK)
    {
        String feeDescriptionFKCol = DBTABLE.getDBColumn("FeeDescriptionFK").getFullyQualifiedColumnName();

        String sql = " SELECT * FROM " + TABLENAME +
                " WHERE " + feeDescriptionFKCol + " = " + feeDescriptionPK;

        return (FeeVO[]) executeQuery(FeeVO.class, sql, POOLNAME, false, null);
    }

    /**
     * Finder method by filtereFundPK.
     *
     * @param filteredFunPK
     */
    public FeeVO[] findByFilteredFundPK(long filteredFunPK)
    {
        String filteredFundFKCol = DBTABLE.getDBColumn("FilteredFundFK").getFullyQualifiedColumnName();

        String sql = " SELECT * FROM " + TABLENAME +
                " WHERE " + filteredFundFKCol + " = " + filteredFunPK;

        return (FeeVO[]) executeQuery(FeeVO.class, sql, POOLNAME, false, null);
    }

    /**
     * Finder method by filtereFundpk and pricingTypeCT and feeRedemption.
     * @param filteredFundPK
     * @param pricingTypeCT
     * @return
     */
    public FeeVO[] findByFilteredFundPK_And_PricingTypeCT_And_FeeRedemption_And_TrxTypeCT
                                                                            (long filteredFundPK,
                                                                            String pricingTypeCT,
                                                                            String feeRedemption,
                                                                            String transactionTypeCT)
    {
        String filteredFundFKCol = DBTABLE.getDBColumn("FilteredFundFK").getFullyQualifiedColumnName();
        String feeDescriptionFKCol = DBTABLE.getDBColumn("FeeDescriptionFK").getFullyQualifiedColumnName();
        String transactionTypeCTCol = DBTABLE.getDBColumn("TransactionTypeCT").getFullyQualifiedColumnName();
        String releaseIndCol = DBTABLE.getDBColumn("ReleaseInd").getFullyQualifiedColumnName();

        DBTable feeDescriptionDBTable = DBTable.getDBTableForTable("FeeDescription");
        String feeDescriptionTable = feeDescriptionDBTable.getFullyQualifiedTableName();

        String pricingTypeCTCol = feeDescriptionDBTable.getDBColumn("PricingTypeCT").getFullyQualifiedColumnName();
        String feeDescriptionPKCol = feeDescriptionDBTable.getDBColumn("FeeDescriptionPK").getFullyQualifiedColumnName();
        String feeRedemptionCol = feeDescriptionDBTable.getDBColumn("FeeRedemption").getFullyQualifiedColumnName();

        String sql = " SELECT " + TABLENAME + ".* FROM " + TABLENAME +
                " INNER JOIN " + feeDescriptionTable + " ON " +
                feeDescriptionPKCol + " = " + feeDescriptionFKCol +
                " WHERE " + filteredFundFKCol + " = " + filteredFundPK +
                " AND " + pricingTypeCTCol + " = '" + pricingTypeCT + "'" +
                " AND " + feeRedemptionCol + " = '" + feeRedemption + "'" +
                " AND " + transactionTypeCTCol + " = '" + transactionTypeCT + "'" +
                " AND " + releaseIndCol + " = 'Y'";

        return (FeeVO[]) executeQuery(FeeVO.class, sql, POOLNAME, false, null);
    }

    /**
     * Returns all FeeVOs for the given filteredFundFK, where the effective date or process date (defined by the
     * dateType parameter) falls between or on the given start/end dates.
     * @param filteredFundPK
     * @param startDate
     * @param endDate
     * @param dateType
     * @return
     */
    public FeeVO[] findByFilteredFundPK_And_Date(long filteredFundPK, String startDate, String endDate, String dateType)
    {
        Connection conn = ConnectionFactory.getSingleton().getConnection(POOLNAME);

        PreparedStatement ps = null;

        FeeVO[] feeVOs = null;

        String filteredFundFKCol = DBTABLE.getDBColumn("FilteredFundFK").getFullyQualifiedColumnName();
        String effectiveDateCol = DBTABLE.getDBColumn("EffectiveDate").getFullyQualifiedColumnName();
        String processDateTimeCol = DBTABLE.getDBColumn("ProcessDateTime").getFullyQualifiedColumnName();
        String accountingPeriodCol = DBTABLE.getDBColumn("AccountingPeriod").getFullyQualifiedColumnName();

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
            orderBy =  accountingPeriodCol + ", " + effectiveDateCol + ", " + processDateTimeCol;
        }

        String sql = "SELECT * FROM " + TABLENAME +
                     " WHERE " + filteredFundFKCol + " = ?" +
                     " AND " + dateColumnToUse + " >= ?" +
                     " AND " + dateColumnToUse + " <= ?" +
                     " ORDER BY " + orderBy + " ASC";

        try
        {
            ps = conn.prepareStatement(sql);

            ps.setLong(1, filteredFundPK);
            ps.setDate(2, DBUtil.convertStringToDate(startDate));
            ps.setDate(3, DBUtil.convertStringToDate(endDate));

            feeVOs = (FeeVO[]) executeQuery(FeeVO.class,
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

        return feeVOs;
    }

     /**
     * Returns all FeeVOs for the given filteredFundFK, where the effective date
     * or process date (defined by the
     * dateType parameter) falls between or on the given start/end dates and
     * chargeCodeFK is null or matches.
     * @param filteredFundPK
     * @param startDate
     * @param endDate
     * @param dateType
     * @param chargeCodeFK
     * @return
     */
    public FeeVO[] findByFilteredFundPKDateChargeCode(long filteredFundPK,
                                                      String startDate,
                                                      String endDate,
                                                      String dateType,
                                                      long chargeCodeFK)
    {
         Connection conn = ConnectionFactory.getSingleton().getConnection(POOLNAME);

         PreparedStatement ps = null;

         FeeVO[] feeVOs = null;

        String filteredFundFKCol = DBTABLE.getDBColumn("FilteredFundFK").getFullyQualifiedColumnName();
        String effectiveDateCol = DBTABLE.getDBColumn("EffectiveDate").getFullyQualifiedColumnName();
        String processDateTimeCol = DBTABLE.getDBColumn("ProcessDateTime").getFullyQualifiedColumnName();
        String accountingPeriodCol = DBTABLE.getDBColumn("AccountingPeriod").getFullyQualifiedColumnName();
        String chargeCodeFKCol = DBTABLE.getDBColumn("ChargeCodeFK").getFullyQualifiedColumnName();
        String feePKCol = DBTABLE.getDBColumn("FeePK").getFullyQualifiedColumnName();

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

        String chargeCodeWhereClause = null;
        if (chargeCodeFK == 0)
        {
             chargeCodeWhereClause = chargeCodeFKCol + " IS NULL ";
        }
        else
        {
             chargeCodeWhereClause = chargeCodeFKCol + " = " + chargeCodeFK;
        }

        String sql =
                "SELECT * FROM " + TABLENAME +
                " WHERE " + filteredFundFKCol + " = ?" +
                " AND " + chargeCodeWhereClause +
                " AND " + dateColumnToUse + " >= ?" +
                " AND " + dateColumnToUse + " <= ?" +
                " ORDER BY "+ orderBy + "," + feePKCol +  " ASC";

         try
         {
             ps = conn.prepareStatement(sql);

             ps.setLong(1, filteredFundPK);
             ps.setDate(2, DBUtil.convertStringToDate(startDate));
             ps.setDate(3, DBUtil.convertStringToDate(endDate));

             feeVOs = (FeeVO[]) executeQuery(FeeVO.class,
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

         return feeVOs;
    }

    /**
     * Returns all FeeVOs for the given filteredFundFK, where the effective date
     * or process date (defined by the
     * dateType parameter) falls between or on the given start/end dates and
     * chargeCodeFK is null or matches.
     * and for given TransactionTypeCT
     * @param filteredFundPK
     * @param startDate
     * @param endDate
     * @param dateType
     * @param chargeCodeFK
     * @param transactionTypeCT
     * @return
     */
    public FeeVO[] findByFilteredFundPK_DateRange_ChargeCodeFK_TransactionTypeCT(
                    long filteredFundPK,
                    String startDate,
                    String endDate,
                    String dateType,
                    long chargeCodeFK,
                    String transactionTypeCT)
    {
        FeeVO[] feeVOs = null;

        Connection conn = ConnectionFactory.getSingleton().getConnection(POOLNAME);

        String filteredFundFKCol = DBTABLE.getDBColumn("FilteredFundFK").getFullyQualifiedColumnName();
        String effectiveDateCol = DBTABLE.getDBColumn("EffectiveDate").getFullyQualifiedColumnName();
        String processDateTimeCol = DBTABLE.getDBColumn("ProcessDateTime").getFullyQualifiedColumnName();
        String accountingPeriodCol = DBTABLE.getDBColumn("AccountingPeriod").getFullyQualifiedColumnName();
        String chargeCodeFKCol = DBTABLE.getDBColumn("ChargeCodeFK").getFullyQualifiedColumnName();
        String feePKCol = DBTABLE.getDBColumn("FeePK").getFullyQualifiedColumnName();
        String transactionTypeCTCol = DBTABLE.getDBColumn("TransactionTypeCT").getFullyQualifiedColumnName();

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

        String chargeCodeWhereClause = null;
        if (chargeCodeFK == 0)
        {
             chargeCodeWhereClause = chargeCodeFKCol + " IS NULL ";
        }
        else
        {
             chargeCodeWhereClause = chargeCodeFKCol + " = " + chargeCodeFK;
        }

        String sql =
                "SELECT * FROM " + TABLENAME +
                " WHERE " + filteredFundFKCol + " = " + filteredFundPK +
                " AND " + chargeCodeWhereClause +
                " AND " + dateColumnToUse + " >= ? " +
                " AND " + dateColumnToUse + " <= ? " +
                " AND " + transactionTypeCTCol + " = '" + transactionTypeCT + "'" +
                " ORDER BY "+ orderBy + "," + feePKCol +  " ASC";

        try
        {
            PreparedStatement ps = conn.prepareStatement(sql);

            if (dateType.equalsIgnoreCase("Effective"))
            {
                ps.setDate(1, DBUtil.convertStringToDate(startDate));
                ps.setDate(2, DBUtil.convertStringToDate(endDate));
            }
            else if (dateType.equalsIgnoreCase("Process"))
            {
                // startDate would be a day prior to endDate
                // hence the time for startDate is 23:59:99:999 not 00:00:00:000
                ps.setTimestamp(1, DBUtil.convertStringToTimestamp(startDate + " 23:59:59:999"));
                ps.setTimestamp(2, DBUtil.convertStringToTimestamp(endDate + " 23:59:59:999"));
            }
            else
            {
                ps.setString(1, startDate);
                ps.setString(2, endDate);
            }

            feeVOs = (FeeVO[]) executeQuery(FeeVO.class, ps, POOLNAME, false, null);

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
                if (conn != null) conn.close();
            }
            catch (SQLException e)
            {
                System.out.println(e);

                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.

                throw new RuntimeException(e);
            }
        }

        return feeVOs;
    }

    /**
     * Returns all FeeVOs for the given filteredFundFK, where the effective date
     * or process date (defined by the
     * dateType parameter) falls between or on the given start/end dates and
     * chargeCodeFK is null or matches.
     * @param filteredFundPK
     * @param startDate
     * @param endDate
     * @param dateType
     * @param chargeCodeFK
     * @param feeTypeCT
     * @return
     */
    public FeeVO[] findByFilteredFundPKDateChargeCodeAndFeeType(
                    long filteredFundPK,
                    String startDate,
                    String endDate,
                    String dateType,
                    long chargeCodeFK,
                    String feeTypeCT)
    {
        FeeVO[] feeVOs = null;

        Connection conn = ConnectionFactory.getSingleton().getConnection(POOLNAME);

        String filteredFundFKCol = DBTABLE.getDBColumn("FilteredFundFK").getFullyQualifiedColumnName();
        String effectiveDateCol = DBTABLE.getDBColumn("EffectiveDate").getFullyQualifiedColumnName();
        String processDateTimeCol = DBTABLE.getDBColumn("ProcessDateTime").getFullyQualifiedColumnName();
        String accountingPeriodCol = DBTABLE.getDBColumn("AccountingPeriod").getFullyQualifiedColumnName();
        String chargeCodeFKCol = DBTABLE.getDBColumn("ChargeCodeFK").getFullyQualifiedColumnName();
        String feePKCol = DBTABLE.getDBColumn("FeePK").getFullyQualifiedColumnName();
        String feeDescriptionFKCol = DBTABLE.getDBColumn("FeeDescriptionFK").getFullyQualifiedColumnName();

        DBTable feeDescriptionDBTable = DBTable.getDBTableForTable("FeeDescription");
        String feeDescriptionTable = feeDescriptionDBTable.getFullyQualifiedTableName();

        String feeTypeCTCol = feeDescriptionDBTable.getDBColumn("FeeTypeCT").getFullyQualifiedColumnName();
        String feeDescriptionPKCol = feeDescriptionDBTable.getDBColumn("FeeDescriptionPK").getFullyQualifiedColumnName();

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

        String chargeCodeWhereClause = null;
        if (chargeCodeFK == 0)
        {
             chargeCodeWhereClause = chargeCodeFKCol + " IS NULL ";
        }
        else
        {
             chargeCodeWhereClause = chargeCodeFKCol + " = " + chargeCodeFK;
        }

        String sql =
                "SELECT * FROM " + TABLENAME +
                " JOIN " + feeDescriptionTable +
                " ON " + feeDescriptionPKCol + " = " + feeDescriptionFKCol +
                " WHERE " + filteredFundFKCol + " = " + filteredFundPK +
                " AND " + chargeCodeWhereClause +
                " AND " + dateColumnToUse + " >= ?" +
                " AND " + dateColumnToUse + " <= ?" +
                " AND " + feeTypeCTCol + " = '" + feeTypeCT + "'" +
                " ORDER BY "+ orderBy + "," + feePKCol +  " ASC";

        try
        {
            PreparedStatement ps = conn.prepareStatement(sql);

            if (dateType.equalsIgnoreCase("Effective"))
            {
                ps.setDate(1, DBUtil.convertStringToDate(startDate));
                ps.setDate(2, DBUtil.convertStringToDate(endDate));
            }
            else if (dateType.equalsIgnoreCase("Process"))
            {
                // startDate would be a day prior to endDate
                // hence the time for startDate is 23:59:99:999 not 00:00:00:000
                ps.setTimestamp(1, DBUtil.convertStringToTimestamp(startDate + " 23:59:59:999"));
                ps.setTimestamp(2, DBUtil.convertStringToTimestamp(endDate + " 23:59:59:999"));
            }
            else
            {
                ps.setString(1, startDate);
                ps.setString(2, endDate);
            }

            feeVOs = (FeeVO[]) executeQuery(FeeVO.class, ps, POOLNAME, false, null);

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
                if (conn != null) conn.close();
            }
            catch (SQLException e)
            {
                System.out.println(e);

                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.

                throw new RuntimeException(e);
            }
        }

        return feeVOs;
    }

    public FeeVO[] findByFeeType_And_Date(String feeType, String runDate)
    {
        DBTable feeDescriptionDBTable = DBTable.getDBTableForTable("FeeDescription");
        String feeDescriptionTable = feeDescriptionDBTable.getFullyQualifiedTableName();

        String effectiveDateCol = DBTABLE.getDBColumn("EffectiveDate").getFullyQualifiedColumnName();
        String feeDescriptionFKCol = DBTABLE.getDBColumn("FeeDescriptionFK").getFullyQualifiedColumnName();

        String feeTypeCTCol = feeDescriptionDBTable.getDBColumn("FeeTypeCT").getFullyQualifiedColumnName();
        String feeDescriptionPKCol = feeDescriptionDBTable.getDBColumn("FeeDescriptionPK").getFullyQualifiedColumnName();

        String sql = "SELECT " + TABLENAME + ".* FROM " + TABLENAME + ", " + feeDescriptionTable +
                     " WHERE " + feeTypeCTCol + " = '" + feeType + "'" +
                     " AND " + feeDescriptionFKCol + " = " + feeDescriptionPKCol +
                     " AND " + effectiveDateCol + " = '" + runDate + "'";

        return (FeeVO[]) executeQuery(FeeVO.class, sql, POOLNAME, false, null);
    }

    public FeeVO[] findAllAccountingPendingByDate(String accountingProcessDate)
    {
        FeeVO[] feeVOs = null;

        String accountingPendingStatusCol = DBTABLE.getDBColumn("AccountingPendingStatus").getFullyQualifiedColumnName();
        String effectiveDateCol = DBTABLE.getDBColumn("EffectiveDate").getFullyQualifiedColumnName();

        String sql = "SELECT * FROM " + TABLENAME +
                     " WHERE " + accountingPendingStatusCol + " = 'Y'" +
                     " AND " + effectiveDateCol + " <= ?";

        Connection conn = null;
        PreparedStatement ps = null;

        try
        {
            conn = ConnectionFactory.getSingleton().getConnection(POOLNAME);

            ps = conn.prepareStatement(sql);

            ps.setDate(1, DBUtil.convertStringToDate(accountingProcessDate));

            feeVOs = (FeeVO[]) executeQuery(FeeVO.class,
                                            ps,
                                            POOLNAME,
                                            false,
                                            null);
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

        return feeVOs;
    }

    public FeeVO[] findFeesForUnitUpdate(long filteredFundFK)
    {
        String unitsCol = DBTABLE.getDBColumn("Units").getFullyQualifiedColumnName();
        String filteredFundFKCol = DBTABLE.getDBColumn("FilteredFundFK").getFullyQualifiedColumnName();
        String trxAmountCol = DBTABLE.getDBColumn("TrxAmount").getFullyQualifiedColumnName();

        String sql = "SELECT * FROM " + TABLENAME +
                     " WHERE " + filteredFundFKCol + " = " + filteredFundFK +
                     " AND " + unitsCol + " = 0" +
                     " AND " + trxAmountCol + " > 0";

        return (FeeVO[]) executeQuery(FeeVO.class, sql, POOLNAME, false, null);
    }
}
