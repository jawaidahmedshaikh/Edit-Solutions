/**
 * User: dlataill
 * Date: Feb 14, 2005
 * Time: 8:47:27 AM
 *
 * (c) 2000-2005 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement
 */
package engine.dm.dao;

import edit.common.vo.ControlBalanceVO;

import edit.services.db.ConnectionFactory;
import edit.services.db.DAO;
import edit.services.db.DBTable;
import edit.services.db.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import java.util.ArrayList;


public class ControlBalanceDAO extends DAO
{
    private final String POOLNAME;
    private final DBTable DBTABLE;
    private final String TABLENAME;


    public ControlBalanceDAO()
    {
        POOLNAME  = ConnectionFactory.ENGINE_POOL;
        DBTABLE   = DBTable.getDBTableForTable("ControlBalance");
        TABLENAME = DBTABLE.getFullyQualifiedTableName();
    }

    public ControlBalanceVO[] getControlBalanceVOByProductFilteredFundStructureFK(long productFilteredFundStructureFK)
    {
        String productFilteredFundStructureFKCol = DBTABLE.getDBColumn("ProductFilteredFundStructureFK").getFullyQualifiedColumnName();
        String endingBalanceCycleDateCol = DBTABLE.getDBColumn("EndingBalanceCycleDate").getFullyQualifiedColumnName();

        String sql = " SELECT * FROM " + TABLENAME +
                     " WHERE " + productFilteredFundStructureFKCol + " = " + productFilteredFundStructureFK +
                     " ORDER BY " + endingBalanceCycleDateCol + " DESC";

        return (ControlBalanceVO[]) executeQuery(ControlBalanceVO.class,
                                                 sql,
                                                 POOLNAME,
                                                 false,
                                                 new ArrayList());
    }

    /**
     * Get the ControlBalances in descending date order for a given filtered fund.
     * If charge code is 0, then it will look for NULL.
     * @param productFilteredFundStructureFK
     * @param chargeCodeFK
     * @return
     */
    public ControlBalanceVO[] getControlBalanceByFilteredFundStrucAndChargeCode(
            long productFilteredFundStructureFK, long chargeCodeFK)
    {
        String productFilteredFundStructureFKCol = DBTABLE.getDBColumn("ProductFilteredFundStructureFK").getFullyQualifiedColumnName();
        String endingBalanceCycleDateCol = DBTABLE.getDBColumn("EndingBalanceCycleDate").getFullyQualifiedColumnName();
        String chargeCodeFKCol = DBTABLE.getDBColumn("ChargeCodeFK").getFullyQualifiedColumnName();

        String chargeCodeRestrictionSql;
        if (chargeCodeFK == 0)
        {
            chargeCodeRestrictionSql = chargeCodeFKCol + " IS NULL ";
        }
        else
        {
            chargeCodeRestrictionSql = chargeCodeFKCol + " = " + chargeCodeFK;
        }

        String sql = " SELECT * FROM " + TABLENAME +
                " WHERE " + productFilteredFundStructureFKCol + " = " + productFilteredFundStructureFK +
                " AND " + chargeCodeRestrictionSql +
                " ORDER BY " + endingBalanceCycleDateCol + " DESC";

        return (ControlBalanceVO[]) executeQuery(ControlBalanceVO.class,
                                                 sql,
                                                 POOLNAME,
                                                 false,
                                                 new ArrayList());
    }

    public ControlBalanceVO[] getControlBalanceVOByCoFilteredFundStruct_DateClosest(long productFilteredFundStructureFK,
                                                                                    String processDate)
    {
        Connection conn = ConnectionFactory.getSingleton().getConnection(POOLNAME);

        PreparedStatement ps = null;

        ControlBalanceVO[] controlBalanceVOs = null;

        String productFilteredFundStructureFKCol = DBTABLE.getDBColumn("ProductFilteredFundStructureFK").getFullyQualifiedColumnName();
        String endingBalanceCycleDateCol = DBTABLE.getDBColumn("EndingBalanceCycleDate").getFullyQualifiedColumnName();

        String sql = "SELECT * FROM " + TABLENAME +
                     " WHERE " + productFilteredFundStructureFKCol + " = ?" +
                     " AND " + endingBalanceCycleDateCol + " = (SELECT MAX(" + endingBalanceCycleDateCol + ")" +
                     " FROM " + TABLENAME + " WHERE " + productFilteredFundStructureFKCol + " = ?" +
                     " AND " + endingBalanceCycleDateCol + " <= ?)";

        try
        {
            ps = conn.prepareStatement(sql);

            ps.setLong(1, productFilteredFundStructureFK);
            ps.setLong(2, productFilteredFundStructureFK);
            ps.setDate(3, DBUtil.convertStringToDate(processDate));

            controlBalanceVOs = (ControlBalanceVO[]) executeQuery(ControlBalanceVO.class,
                                                                   ps,
                                                 POOLNAME,
                                                 false,
                                                 new ArrayList());
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

        return controlBalanceVOs;
    }

    /**
     * Returns the ControlBalance record for the given ProductFilteredFundStructureFK and ChargeCodeFK whose EndingBalanceCycleDate
     * is equal to (or if not found it will return the record just previous to) the given processDate
     * @param productFilteredFundStructureFK
     * @param chargeCodeFK
     * @param processDate
     * @return
     */
    public ControlBalanceVO[] getControlBalanceVOByCoFilteredFundStruct_DateClosest(long productFilteredFundStructureFK,
                                                                                    long chargeCodeFK,
                                                                                    String processDate)
    {
        ControlBalanceVO[] controlBalanceVOs = null;

        String productFilteredFundStructureFKCol = DBTABLE.getDBColumn("ProductFilteredFundStructureFK").getFullyQualifiedColumnName();
        String endingBalanceCycleDateCol = DBTABLE.getDBColumn("EndingBalanceCycleDate").getFullyQualifiedColumnName();
        String chargeCodeFKCol = DBTABLE.getDBColumn("ChargeCodeFK").getFullyQualifiedColumnName();

        String chargeCodeRestrictionSql;
        if (chargeCodeFK == 0)
        {
            chargeCodeRestrictionSql = chargeCodeFKCol + " IS NULL ";
        }
        else
        {
            chargeCodeRestrictionSql = chargeCodeFKCol + " = " + chargeCodeFK;
        }

        String sql = "SELECT * FROM " + TABLENAME +
                     " WHERE " + productFilteredFundStructureFKCol + " = ?" +
                     " AND " + chargeCodeRestrictionSql +
                     " AND " + endingBalanceCycleDateCol + " = (SELECT MAX(" + endingBalanceCycleDateCol + ")" +
                     " FROM " + TABLENAME +
                     " WHERE " + productFilteredFundStructureFKCol + " = ?" +
                     " AND " + chargeCodeRestrictionSql +
                     " AND " + endingBalanceCycleDateCol + " <= ?)";

        Connection conn = null;

        PreparedStatement ps = null;

        try
        {
            conn = ConnectionFactory.getSingleton().getConnection(POOLNAME);

            ps = conn.prepareStatement(sql);

            ps.setLong(1, productFilteredFundStructureFK);
            ps.setLong(2, productFilteredFundStructureFK);
            ps.setDate(3, DBUtil.convertStringToDate(processDate));

            controlBalanceVOs = (ControlBalanceVO[]) executeQuery(ControlBalanceVO.class,
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

        return controlBalanceVOs;
    }

    /**
     * @param productFilteredFundStructureFK
     * @param chargeCodeFK
     * @param startPeriod
     * @param endPeriod
     * @return
     */
    public ControlBalanceVO[] findByCoFilteredFundStructrueFK_ChargeCodeFK_AccountingPeriod(long productFilteredFundStructureFK,
                                                                                            long chargeCodeFK,
                                                                                            String startPeriod,
                                                                                            String endPeriod)
    {
        ControlBalanceVO[] controlBalanceVOs = null;

        String companyFilteredFundStructureFKCol = DBTABLE.getDBColumn("CompanyFilteredFundStructureFK").getFullyQualifiedColumnName();
        String chargeCodeFKCol = DBTABLE.getDBColumn("ChargeCodeFK").getFullyQualifiedColumnName();
        String controlBalancePKCol = DBTABLE.getDBColumn("ControlBalancePK").getFullyQualifiedColumnName();

        DBTable controlBalanceDetailDBTable = DBTable.getDBTableForTable("ControlBalanceDetail");
        String controlBalanceDetailTable = controlBalanceDetailDBTable.getFullyQualifiedTableName();
        String controlBalanceFKCol = controlBalanceDetailDBTable.getDBColumn("ControlBalanceFK").getFullyQualifiedColumnName();
        String accountingPeriodCol = controlBalanceDetailDBTable.getDBColumn("AccountingPeriod").getFullyQualifiedColumnName();

        String chargeCodeRestrictionSql;
        if (chargeCodeFK == 0)
        {
            chargeCodeRestrictionSql = chargeCodeFKCol + " IS NULL ";
        }
        else
        {
            chargeCodeRestrictionSql = chargeCodeFKCol + " = " + chargeCodeFK;
        }

        String sql = " SELECT * FROM " + TABLENAME +
                     " JOIN " + controlBalanceDetailTable +
                     " ON " + controlBalancePKCol + " = " + controlBalanceFKCol +
                     " WHERE " + companyFilteredFundStructureFKCol + " = " + productFilteredFundStructureFK +
                     " AND " + chargeCodeRestrictionSql +
                     " AND " + accountingPeriodCol + " >= '" + startPeriod + "'" +
                     " AND " + accountingPeriodCol + " <= '" + endPeriod + "'";

        Connection conn = null;

        PreparedStatement ps = null;

        try
        {
            conn = ConnectionFactory.getSingleton().getConnection(POOLNAME);

            ps = conn.prepareStatement(sql);

            controlBalanceVOs = (ControlBalanceVO[]) executeQuery(ControlBalanceVO.class,
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

        return controlBalanceVOs;
    }

    /**
     * Finder by CompanyFilteredFundStructure, ChargeCode, EndingBalanceCycleDate
     * @param productFilteredFundStructureFK
     * @param chargeCodeFK
     * @param startDate
     * @param endDate
     * @return
     */
    public ControlBalanceVO[] findByCoFilteredFundStructrueFK_ChargeCodeFK_DateRange(long productFilteredFundStructureFK,
                                                                                     long chargeCodeFK,
                                                                                     String startDate,
                                                                                     String endDate)
    {
        ControlBalanceVO[] controlBalanceVOs = null;

        String productFilteredFundStructureFKCol = DBTABLE.getDBColumn("ProductFilteredFundStructureFK").getFullyQualifiedColumnName();
        String chargeCodeFKCol = DBTABLE.getDBColumn("ChargeCodeFK").getFullyQualifiedColumnName();
        String endingBalanceCycleDateCol = DBTABLE.getDBColumn("EndingBalanceCycleDate").getFullyQualifiedColumnName();

        String chargeCodeRestrictionSql;
        if (chargeCodeFK == 0)
        {
            chargeCodeRestrictionSql = chargeCodeFKCol + " IS NULL ";
        }
        else
        {
            chargeCodeRestrictionSql = chargeCodeFKCol + " = " + chargeCodeFK;
        }

        String sql = "SELECT * FROM " + TABLENAME +
                     " WHERE " + productFilteredFundStructureFKCol + " = " + productFilteredFundStructureFK +
                     " AND " + chargeCodeRestrictionSql +
                     " AND " + endingBalanceCycleDateCol + " >= ?" +
                     " AND " + endingBalanceCycleDateCol + " <= ?";

        Connection conn = null;

        PreparedStatement ps = null;

        try
        {
            conn = ConnectionFactory.getSingleton().getConnection(POOLNAME);

            ps = conn.prepareStatement(sql);

            ps.setDate(1, DBUtil.convertStringToDate(startDate));

            ps.setDate(2, DBUtil.convertStringToDate(endDate));

            controlBalanceVOs = (ControlBalanceVO[]) executeQuery(ControlBalanceVO.class,
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

        return controlBalanceVOs;
    }
}
