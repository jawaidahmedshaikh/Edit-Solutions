/*
 * User: sprasad
 * Date: Apr 6, 2007
 * Time: 9:41:20 AM
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package engine.dm.dao;

import edit.services.db.DBTable;
import edit.services.db.ConnectionFactory;
import edit.services.db.DAO;
import edit.services.db.DBUtil;
import edit.common.vo.ControlBalanceDetailVO;
import edit.common.vo.ControlBalanceVO;

import java.util.ArrayList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ControlBalanceDetailDAO extends DAO
{
    private final String POOLNAME;
    private final DBTable DBTABLE;
    private final String TABLENAME;

    public ControlBalanceDetailDAO()
    {
        POOLNAME  = ConnectionFactory.ENGINE_POOL;
        DBTABLE   = DBTable.getDBTableForTable("ControlBalanceDetail");
        TABLENAME = DBTABLE.getFullyQualifiedTableName();
    }

    /**
     * Finder by ControlBalanceFK
     * @param controlBalanceFK
     * @return
     */
    public ControlBalanceDetailVO[] findByControlBalanceFK(long controlBalanceFK)
    {
        String controlBalanceFKCol = DBTABLE.getDBColumn("ControlBalanceFK").getFullyQualifiedColumnName();

        String sql = " SELECT * FROM " + TABLENAME +
                     " WHERE " + controlBalanceFKCol + " = " + controlBalanceFK;

        return (ControlBalanceDetailVO[]) executeQuery(ControlBalanceDetailVO.class,
                                                       sql,
                                                       POOLNAME,
                                                       false,
                                                       new ArrayList());
    }

    /**
     * Finder by CompanyFilteredFundStructure, Chargecode, AccountingPeriod Range
     * @param companyFilteredFundStructureFK
     * @param chargeCodeFK
     * @param startPeriod
     * @param endPeriod
     * @return ControlBalanceDetailRecords ordered by EndingBalanceCycleDate, AccountingPeriod, EffectiveDate, ValuationDate
     */
    public ControlBalanceDetailVO[] findByCompanyFilteredFundStructrueFK_ChargeCodeFK_AccountingPeriodRange(long companyFilteredFundStructureFK,
                                                                                                            long chargeCodeFK,
                                                                                                            String startPeriod,
                                                                                                            String endPeriod)
    {
        ControlBalanceDetailVO[] controlBalanceDetailVOs = null;

        String controlBalanceFKCol = DBTABLE.getDBColumn("ControlBalanceFK").getFullyQualifiedColumnName();
        String accountingPeriodCol = DBTABLE.getDBColumn("AccountingPeriod").getFullyQualifiedColumnName();
        String effectiveDateCol = DBTABLE.getDBColumn("EffectiveDate").getFullyQualifiedColumnName();
        String valuationDateCol = DBTABLE.getDBColumn("ValuationDate").getFullyQualifiedColumnName();

        DBTable controlBalanceDBTable = DBTable.getDBTableForTable("ControlBalance");
        String controlBalanceTableName = controlBalanceDBTable.getFullyQualifiedTableName();
        String controlBalancePKCol = controlBalanceDBTable.getDBColumn("ControlBalancePK").getFullyQualifiedColumnName();
        String companyFilteredFundStructureFKCol = controlBalanceDBTable.getDBColumn("CompanyFilteredFundStructureFK").getFullyQualifiedColumnName();
        String chargeCodeFKCol = controlBalanceDBTable.getDBColumn("ChargeCodeFK").getFullyQualifiedColumnName();
        String endingBalanceCycleDateCol = controlBalanceDBTable.getDBColumn("EndingBalanceCycleDate").getFullyQualifiedColumnName();

        String chargeCodeRestrictionSql;
        if (chargeCodeFK == 0)
        {
            chargeCodeRestrictionSql = chargeCodeFKCol + " IS NULL ";
        }
        else
        {
            chargeCodeRestrictionSql = chargeCodeFKCol + " = ?";
        }

        String sql = " SELECT * FROM " + TABLENAME +
                     " JOIN " + controlBalanceTableName +
                     " ON " + controlBalancePKCol + " = " + controlBalanceFKCol +
                     " WHERE " + companyFilteredFundStructureFKCol + " = ?" +
                     " AND " + accountingPeriodCol + " >= ?" +
                     " AND " + accountingPeriodCol + " <= ?" +
                     " AND " + chargeCodeRestrictionSql +
                     // Do not delete ORDER BY clause ... the Fund Activity Export may fail.
                     " ORDER BY " + endingBalanceCycleDateCol + ", " + accountingPeriodCol + ", " + effectiveDateCol + ", " + valuationDateCol;

        Connection conn = null;

        PreparedStatement ps = null;

        try
        {
            conn = ConnectionFactory.getSingleton().getConnection(POOLNAME);

            ps = conn.prepareStatement(sql);

            ps.setLong(1, companyFilteredFundStructureFK);
            ps.setString(2, startPeriod);
            ps.setString(3, endPeriod);

            if (chargeCodeFK != 0)
            {
                ps.setLong(4, chargeCodeFK);
            }

            controlBalanceDetailVOs = (ControlBalanceDetailVO[]) executeQuery(ControlBalanceDetailVO.class,
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
                if (conn != null) conn.close();
            }
            catch (SQLException e)
            {
                System.out.println(e);

                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.

                throw new RuntimeException(e);
            }
        }

        return controlBalanceDetailVOs;
    }

    /**
     * Finder by CompanyFilteredFundStructure, ChargeCode, EndingBalanceCycleDate
     * @param companyFilteredFundStructureFK
     * @param chargeCodeFK
     * @param startDate
     * @param endDate
     * @return ControlBalanceDetailRecords ordered by EndingBalanceCycleDate, AccountingPeriod, EffectiveDate, ValuationDate
     */
    public ControlBalanceDetailVO[] findByCompanyFilteredFundStructrueFK_ChargeCodeFK_EndingBalanceCycleDateRange(long companyFilteredFundStructureFK,
                                                                                                                  long chargeCodeFK,
                                                                                                                  String startDate,
                                                                                                                  String endDate)
    {
        ControlBalanceDetailVO[] controlBalanceDetailVOs = null;

        String controlBalanceFKCol = DBTABLE.getDBColumn("ControlBalanceFK").getFullyQualifiedColumnName();
        String accountingPeriodCol = DBTABLE.getDBColumn("AccountingPeriod").getFullyQualifiedColumnName();
        String effectiveDateCol = DBTABLE.getDBColumn("EffectiveDate").getFullyQualifiedColumnName();
        String valuationDateCol = DBTABLE.getDBColumn("ValuationDate").getFullyQualifiedColumnName();

        DBTable controlBalanceDBTable = DBTable.getDBTableForTable("ControlBalance");
        String controlBalanceTableName = controlBalanceDBTable.getFullyQualifiedTableName();
        String controlBalancePKCol = controlBalanceDBTable.getDBColumn("ControlBalancePK").getFullyQualifiedColumnName();
        String productFilteredFundStructureFKCol = controlBalanceDBTable.getDBColumn("ProductFilteredFundStructureFK").getFullyQualifiedColumnName();
        String chargeCodeFKCol = controlBalanceDBTable.getDBColumn("ChargeCodeFK").getFullyQualifiedColumnName();
        String endingBalanceCycleDateCol = controlBalanceDBTable.getDBColumn("EndingBalanceCycleDate").getFullyQualifiedColumnName();

        String chargeCodeRestrictionSql;
        if (chargeCodeFK == 0)
        {
            chargeCodeRestrictionSql = chargeCodeFKCol + " IS NULL ";
        }
        else
        {
            chargeCodeRestrictionSql = chargeCodeFKCol + " = ?";
        }

        String sql = " SELECT * FROM " + TABLENAME +
                     " JOIN " + controlBalanceTableName +
                     " ON " + controlBalancePKCol + " = " + controlBalanceFKCol +
                     " WHERE " + productFilteredFundStructureFKCol + " = ?" +
                     " AND " + endingBalanceCycleDateCol + " >= ?" +
                     " AND " + endingBalanceCycleDateCol + " <= ?" +
                     " AND " + chargeCodeRestrictionSql +
                     // Do not delete ORDER BY clause ... the Fund Activity Export may fail.
                     " ORDER BY " + endingBalanceCycleDateCol + ", " + accountingPeriodCol + ", " + effectiveDateCol + ", " + valuationDateCol;;

        Connection conn = null;

        PreparedStatement ps = null;

        try
        {
            conn = ConnectionFactory.getSingleton().getConnection(POOLNAME);

            ps = conn.prepareStatement(sql);

            ps.setLong(1, companyFilteredFundStructureFK);
            ps.setDate(2, DBUtil.convertStringToDate(startDate));
            ps.setDate(3, DBUtil.convertStringToDate(endDate));

            if (chargeCodeFK != 0)
            {
                ps.setLong(4, chargeCodeFK);
            }

            controlBalanceDetailVOs = (ControlBalanceDetailVO[]) executeQuery(ControlBalanceDetailVO.class,
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
                if (conn != null) conn.close();
            }
            catch (SQLException e)
            {
                System.out.println(e);

                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.

                throw new RuntimeException(e);
            }
        }

        return controlBalanceDetailVOs;
    }

    /**
     * Finds last ControlBalanceDetail record for given ControlBalanceFK
     * Last = Max(AccountingPeriod), Max(EffectiveDate), Max(ValuationDate)
     * @param controlBalanceFK
     * @return
     */
    public ControlBalanceDetailVO[] findLatestByControlBalanceFK(long controlBalanceFK)
    {
        ControlBalanceDetailVO[] controlBalanceDetailVOs = null;

        String controlBalanceFKCol = DBTABLE.getDBColumn("ControlBalanceFK").getFullyQualifiedColumnName();
        String accountingPeriodCol = DBTABLE.getDBColumn("AccountingPeriod").getFullyQualifiedColumnName();
        String effectiveDateCol = DBTABLE.getDBColumn("EffectiveDate").getFullyQualifiedColumnName();
        String valuationDateCol = DBTABLE.getDBColumn("ValuationDate").getFullyQualifiedColumnName();

        String sql = " SELECT * FROM " + TABLENAME +
                     " WHERE " + controlBalanceFKCol + " = ?" +
                     " AND " + accountingPeriodCol + " = " +
                     " ( SELECT MAX( + " + accountingPeriodCol + ") " +
                     " FROM " + TABLENAME + " WHERE " + controlBalanceFKCol + " = ?)" +
                     " AND " + effectiveDateCol + " = " +
                     " ( SELECT MAX( + " + effectiveDateCol + ") " +
                     " FROM " + TABLENAME + " WHERE " + controlBalanceFKCol + " = ?)" +
                     " AND " + valuationDateCol + " = " +
                     " ( SELECT MAX( + " + valuationDateCol + ") " +
                     " FROM " + TABLENAME + " WHERE " + controlBalanceFKCol + " = ?)";

        Connection conn = null;

        PreparedStatement ps = null;

        try
        {
            conn = ConnectionFactory.getSingleton().getConnection(POOLNAME);

            ps = conn.prepareStatement(sql);

            ps.setLong(1, controlBalanceFK);
            ps.setLong(2, controlBalanceFK);
            ps.setLong(3, controlBalanceFK);
            ps.setLong(4, controlBalanceFK);

            controlBalanceDetailVOs = (ControlBalanceDetailVO[]) executeQuery(ControlBalanceDetailVO.class,
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
                if (conn != null) conn.close();
            }
            catch (SQLException e)
            {
                System.out.println(e);

                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.

                throw new RuntimeException(e);
            }
        }

        return controlBalanceDetailVOs;
    }
}
