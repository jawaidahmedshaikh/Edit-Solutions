/*
 * User: unknown
 * Date: Feb 1, 2002
 * Time: unknown
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */

package engine.dm.dao;

import edit.common.vo.UnitValuesVO;
import edit.services.db.ConnectionFactory;
import edit.services.db.DAO;
import edit.services.db.DBTable;
import edit.services.db.DBUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;



public class UnitValuesDAO extends DAO
{
    private final String POOLNAME;
    private final DBTable DBTABLE;
    private final String TABLENAME;


    public UnitValuesDAO()
    {
        POOLNAME  = ConnectionFactory.ENGINE_POOL;
        DBTABLE   = DBTable.getDBTableForTable("UnitValues");
        TABLENAME = DBTABLE.getFullyQualifiedTableName();
    }

    public UnitValuesVO[] findUnitValuesByFilteredFundId(long filteredFundId)
    {
        String filteredFundFKCol = DBTABLE.getDBColumn("FilteredFundFK").getFullyQualifiedColumnName();

        String sql = " SELECT * FROM " + TABLENAME +
                     " WHERE " + filteredFundFKCol + " = " + filteredFundId;

        return (UnitValuesVO[]) executeQuery(UnitValuesVO.class,
                                              sql,
                                               POOLNAME,
                                                false,
                                                 null);
    }

    /**
     * Get Unit Values that have a particular charge code assoicated
     * @param chargeCodeFK
     * @return
     */
    public UnitValuesVO[] findUnitValuesByFilteredFundAndChargeCode(long filteredFundId, long chargeCodeFK)
    {
        String filteredFundFKCol = DBTABLE.getDBColumn("FilteredFundFK").getFullyQualifiedColumnName();

        String chargeCodeFKCol = DBTABLE.getDBColumn("chargeCodeFK").getFullyQualifiedColumnName();

        String chargeCodeFKCheck = null;
        if (chargeCodeFK == 0)
        {
            chargeCodeFKCheck = chargeCodeFKCol + " IS NULL ";
        }
        else
        {
           chargeCodeFKCheck = chargeCodeFKCol + " = " + chargeCodeFK;
        }

        String sql = " SELECT * FROM " + TABLENAME +
                     " WHERE " +
                     filteredFundFKCol + " = " + filteredFundId + " AND " +
                     chargeCodeFKCheck;

        return (UnitValuesVO[]) executeQuery(UnitValuesVO.class,
                                              sql,
                                               POOLNAME,
                                                false,
                                                 null);
    }

    public UnitValuesVO[] findUnitValuesByPK(long primaryKey)
    {
        String unitValuesPKCol = DBTABLE.getDBColumn(" UnitValuesPK").getFullyQualifiedColumnName();

        String sql = " SELECT * FROM " + TABLENAME +
                     " WHERE " + unitValuesPKCol + " = " + primaryKey;

        return (UnitValuesVO[]) executeQuery(UnitValuesVO.class,
                                              sql,
                                               POOLNAME,
                                                false,
                                                 null);
    }

    /**
     * Filter additionally by charge code when doing the selects.  Unfortunately
     * because the MAX etc is happening in the DB, can't just call more general
     * method and filter the results.
     * @param filteredFundId
     * @param effDate
     * @param pricingDirection
     * @param chargeCodeFK
     * @return
     */
    public UnitValuesVO[] findUnitValuesByFilteredFundIdDateChargeCode(
            long filteredFundId,
            String effDate,
            String pricingDirection,
            long chargeCodeFK)
    {
        String filteredFundFKCol = DBTABLE.getDBColumn("FilteredFundFK").getFullyQualifiedColumnName();
        String effectiveDateCol  = DBTABLE.getDBColumn("EffectiveDate").getFullyQualifiedColumnName();
        String chargeCodeFKCol = DBTABLE.getDBColumn("ChargeCodeFK").getFullyQualifiedColumnName();

        String sql;

        Connection conn = ConnectionFactory.getSingleton().getConnection(POOLNAME);

        PreparedStatement ps = null;

        UnitValuesVO[] unitValuesVOs = null;

        String chargeCodeRestrictionSql = null;
        if (chargeCodeFK == 0)
        {
            chargeCodeRestrictionSql =
                    "( " +
                    chargeCodeFKCol +
                    " IS NULL OR " +
                    chargeCodeFKCol + " = 0)";
        }
        else
        {
            chargeCodeRestrictionSql = chargeCodeFKCol + " = " + chargeCodeFK;
        }


        if (pricingDirection.equalsIgnoreCase("Hedge"))
        {
            //"Hedge" searches for a forward price, and if none is found, searches for a backward price
            sql  =  "SELECT * FROM " + TABLENAME +
                    " WHERE " + filteredFundFKCol + " = " + filteredFundId +
                    " AND " + chargeCodeRestrictionSql +
                    " AND " + effectiveDateCol + " = (" +
                    " SELECT MIN (" + effectiveDateCol + ") FROM " + TABLENAME +
                    " WHERE " + effectiveDateCol + " >= ?" +
                    " AND " + chargeCodeRestrictionSql +
                    " AND " + filteredFundFKCol + " = ? )";

            try
            {
                ps = conn.prepareStatement(sql);

                ps.setDate(1, DBUtil.convertStringToDate(effDate));
                ps.setLong(2, filteredFundId);

                unitValuesVOs = (UnitValuesVO[]) executeQuery(UnitValuesVO.class,
                                                              ps,
                                                              POOLNAME,
                                                              false,
                                                              null);

                if (unitValuesVOs == null || unitValuesVOs.length == 0)
                {
                    sql = "SELECT * FROM " + TABLENAME +
                          " WHERE " + filteredFundFKCol + " = " + filteredFundId +
                          " AND " + chargeCodeRestrictionSql +
                          " AND " + effectiveDateCol + " = (" +
                          " SELECT MAX (" + effectiveDateCol + ") FROM " + TABLENAME +
                          " WHERE " + effectiveDateCol + " <= ?" +
                          " AND " + chargeCodeRestrictionSql +
                          " AND " + filteredFundFKCol + " = ?)";

                    ps = conn.prepareStatement(sql);

                    ps.setDate(1, DBUtil.convertStringToDate(effDate));
                    ps.setLong(2, filteredFundId);

                    unitValuesVOs = (UnitValuesVO[]) executeQuery(UnitValuesVO.class,
                                                                  ps,
                                                                  POOLNAME,
                                                                  false,
                                                                  null);
                }
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

            return unitValuesVOs;
        }
        else
        {
            if (pricingDirection.equals("Backward"))
            {
                sql =   "SELECT * FROM " + TABLENAME +
                        " WHERE " + filteredFundFKCol + " = " + filteredFundId +
                        " AND " + chargeCodeRestrictionSql +
                        " AND " + effectiveDateCol + " = (" +
                        " SELECT MAX (" + effectiveDateCol + ") FROM " + TABLENAME +
                        " WHERE " + effectiveDateCol + " <= ?" +
                        " AND " + chargeCodeRestrictionSql +
                        " AND " + filteredFundFKCol + " = ?)";
            }
            else
            {
                sql  =  "SELECT * FROM " + TABLENAME +
                        " WHERE " + filteredFundFKCol + " = " + filteredFundId +
                        " AND " + chargeCodeRestrictionSql +
                        " AND " + effectiveDateCol + " = (" +
                        " SELECT MIN (" + effectiveDateCol + ") FROM " + TABLENAME +
                        " WHERE " + effectiveDateCol + " >= ?" +
                        " AND " + chargeCodeRestrictionSql +
                        " AND " + filteredFundFKCol + " = ?)";
            }

            try
            {
                ps = conn.prepareStatement(sql);

                ps.setDate(1, DBUtil.convertStringToDate(effDate));
                ps.setLong(2, filteredFundId);

                unitValuesVOs = (UnitValuesVO[]) executeQuery(UnitValuesVO.class,
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

            return unitValuesVOs;
        }

    }

    public UnitValuesVO[] findUnitValuesByFilteredFundIdDate(
            long filteredFundId,
            String effDate,
            String pricingDirection)

    {
        String filteredFundFKCol = DBTABLE.getDBColumn("FilteredFundFK").getFullyQualifiedColumnName();
        String effectiveDateCol  = DBTABLE.getDBColumn("EffectiveDate").getFullyQualifiedColumnName();

        String sql;

        Connection conn = ConnectionFactory.getSingleton().getConnection(POOLNAME);

        PreparedStatement ps = null;

        UnitValuesVO[] unitValuesVOs = null;

        if (pricingDirection.equalsIgnoreCase("Hedge"))
        {
            //"Hedge" searches for a forward price, and if none is found, searches for a backward price
            sql  =  "SELECT * FROM " + TABLENAME +
                    " WHERE " + filteredFundFKCol + " = " + filteredFundId +
                    " AND " + effectiveDateCol + " = (" +
                    " SELECT MIN (" + effectiveDateCol + ") FROM " + TABLENAME +
                    " WHERE " + effectiveDateCol + " >= ?" +
                    " AND " + filteredFundFKCol + " = ?)";


            try
            {
                ps = conn.prepareStatement(sql);

                ps.setDate(1, DBUtil.convertStringToDate(effDate));
                ps.setLong(2, filteredFundId);

                unitValuesVOs = (UnitValuesVO[]) executeQuery(UnitValuesVO.class,
                                                              ps,
                                                              POOLNAME,
                                                              false,
                                                              null);

                if (unitValuesVOs == null || unitValuesVOs.length == 0)
                {
                    sql = "SELECT * FROM " + TABLENAME +
                          " WHERE " + filteredFundFKCol + " = " + filteredFundId +
                          " AND " + effectiveDateCol + " = (" +
                          " SELECT MAX (" + effectiveDateCol + ") FROM " + TABLENAME +
                          " WHERE " + effectiveDateCol + " <= ?" +
                          " AND " + filteredFundFKCol + " = ?)";

                    ps = conn.prepareStatement(sql);

                    ps.setDate(1, DBUtil.convertStringToDate(effDate));
                    ps.setLong(2, filteredFundId);

                    unitValuesVOs = (UnitValuesVO[]) executeQuery(UnitValuesVO.class,
                                                                  ps,
                                                                  POOLNAME,
                                                                  false,
                                                                  null);
                }
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

            return unitValuesVOs;
        }
        else
        {
            if (pricingDirection.equals("Backward"))
            {
                sql =   "SELECT * FROM " + TABLENAME +
                        " WHERE " + filteredFundFKCol + " = " + filteredFundId +
                        " AND " + effectiveDateCol + " = (" +
                        " SELECT MAX (" + effectiveDateCol + ") FROM " + TABLENAME +
                        " WHERE " + effectiveDateCol + " <= ?" +
                        " AND " + filteredFundFKCol + " = ?)";
            }
            else
            {
                sql  =  "SELECT * FROM " + TABLENAME +
                        " WHERE " + filteredFundFKCol + " = " + filteredFundId +
                        " AND " + effectiveDateCol + " = (" +
                        " SELECT MIN (" + effectiveDateCol + ") FROM " + TABLENAME +
                        " WHERE " + effectiveDateCol + " >= ?" +
                        " AND " + filteredFundFKCol + " = ?)";
            }

            try
            {
                ps = conn.prepareStatement(sql);

                ps.setDate(1, DBUtil.convertStringToDate(effDate));
                ps.setLong(2, filteredFundId);

                unitValuesVOs = (UnitValuesVO[]) executeQuery(UnitValuesVO.class,
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

            return unitValuesVOs;
        }
    }

    public UnitValuesVO[] findByFilteredFundFKDate(long filteredFundId, String effectiveDate)
    {
        String filteredFundFKCol = DBTABLE.getDBColumn("FilteredFundFK").getFullyQualifiedColumnName();
        String effectiveDateCol  = DBTABLE.getDBColumn("EffectiveDate").getFullyQualifiedColumnName();

        Connection conn = ConnectionFactory.getSingleton().getConnection(POOLNAME);

        PreparedStatement ps = null;

        UnitValuesVO[] unitValuesVOs = null;

        String sql = " SELECT * FROM " + TABLENAME +
                     " WHERE " + filteredFundFKCol + " = ?" +
                     " AND " + effectiveDateCol + " = ?";

        try
        {
            ps = conn.prepareStatement(sql);

            ps.setLong(1, filteredFundId);
            ps.setDate(2, DBUtil.convertStringToDate(effectiveDate));

            unitValuesVOs = (UnitValuesVO[]) executeQuery(UnitValuesVO.class,
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

        return unitValuesVOs;
    }

    public UnitValuesVO[] findByDateRange(long filteredFundId, String startDate, String stopDate)
    {
        UnitValuesVO[] unitValuesVOs = null;

        String filteredFundFKCol = DBTABLE.getDBColumn("FilteredFundFK").getFullyQualifiedColumnName();
        String effectiveDateCol  = DBTABLE.getDBColumn("EffectiveDate").getFullyQualifiedColumnName();

        String sql;

        sql =   " SELECT * FROM " + TABLENAME +
                " WHERE " + filteredFundFKCol + " = ?" +
                " AND " + effectiveDateCol + " >= ?" +
                " AND " + effectiveDateCol + " <= ?" +
                " ORDER BY " + effectiveDateCol + "ASC";

        Connection conn = null;

        PreparedStatement ps = null;

        try
        {
            conn = ConnectionFactory.getSingleton().getConnection(POOLNAME);

            ps = conn.prepareStatement(sql);

            ps.setLong(1, filteredFundId);
            ps.setDate(2, DBUtil.convertStringToDate(startDate));
            ps.setDate(3, DBUtil.convertStringToDate(stopDate));

            unitValuesVOs = (UnitValuesVO[]) executeQuery(UnitValuesVO.class,
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

        return unitValuesVOs;
    }

    public UnitValuesVO[] findByFilteredFund_ChargeCode_DateRange(long filteredFundId,
                                                                  long chargeCodeFK,
                                                                  String startDate,
                                                                  String stopDate)
    {
        UnitValuesVO[] unitValuesVOs = null;

        String filteredFundFKCol = DBTABLE.getDBColumn("FilteredFundFK").getFullyQualifiedColumnName();
        String chargeCodeFKCol = DBTABLE.getDBColumn("ChargeCodeFK").getFullyQualifiedColumnName();
        String effectiveDateCol  = DBTABLE.getDBColumn("EffectiveDate").getFullyQualifiedColumnName();

        String sql;

        String chargeCodeFKCheck = null;
        if (chargeCodeFK == 0)
        {
            chargeCodeFKCheck = chargeCodeFKCol + " IS NULL ";
        }
        else
        {
           chargeCodeFKCheck = chargeCodeFKCol + " = " + chargeCodeFK;
        }

        sql =   " SELECT * FROM " + TABLENAME +
                " WHERE " + filteredFundFKCol + " = ?" +
                " AND " + chargeCodeFKCheck +
                " AND " + effectiveDateCol + " >= ?" +
                " AND " + effectiveDateCol + " <= ?" +
                " ORDER BY " + effectiveDateCol + "ASC";

        Connection conn = null;

        PreparedStatement ps = null;

        try
        {
            conn = ConnectionFactory.getSingleton().getConnection(POOLNAME);

            ps = conn.prepareStatement(sql);

            ps.setLong(1, filteredFundId);
            ps.setDate(2, DBUtil.convertStringToDate(startDate));
            ps.setDate(3, DBUtil.convertStringToDate(stopDate));

            unitValuesVOs = (UnitValuesVO[]) executeQuery(UnitValuesVO.class,
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

        return unitValuesVOs;
    }

    /**
     * Retrieves all UnitValues records whose UpdateStatus matches the updateStatus parameter value
     * @param updateStatus
     * @return
     */
    public UnitValuesVO[] findUnitValuesByUpdateStatus(String updateStatus)
    {
        String updateStatusCol = DBTABLE.getDBColumn("UpdateStatus").getFullyQualifiedColumnName();

        String sql;

        sql =   "SELECT * FROM " + TABLENAME +
                " WHERE " + updateStatusCol + " = '" + updateStatus + "'";

        return (UnitValuesVO[]) executeQuery(UnitValuesVO.class,
                                              sql,
                                               POOLNAME,
                                                false,
                                                 null);
    }

    public UnitValuesVO[] findUnitValuesByFund_ChargeCode_Date(long filteredFundId,
                                                               long chargeCodeFK,
                                                               String effDate,
                                                               String pricingDirection)

    {
        String filteredFundFKCol = DBTABLE.getDBColumn("FilteredFundFK").getFullyQualifiedColumnName();
        String chargeCodeFKCol = DBTABLE.getDBColumn("ChargeCodeFK").getFullyQualifiedColumnName();
        String effectiveDateCol  = DBTABLE.getDBColumn("EffectiveDate").getFullyQualifiedColumnName();

        String sql;

        String chargeCodeFKCheck = null;
        if (chargeCodeFK == 0)
        {
            chargeCodeFKCheck = chargeCodeFKCol + " IS NULL ";
        }
        else
        {
            chargeCodeFKCheck = chargeCodeFKCol + " = " + chargeCodeFK;
        }

        Connection conn = ConnectionFactory.getSingleton().getConnection(POOLNAME);

        PreparedStatement ps = null;

        UnitValuesVO[] unitValuesVOs = null;

        if (pricingDirection.equalsIgnoreCase("Hedge"))
        {
            //"Hedge" searches for a forward price, and if none is found, searches for a backward price
            sql  =  "SELECT * FROM " + TABLENAME +
                    " WHERE " + filteredFundFKCol + " = " + filteredFundId +
                    " AND " + chargeCodeFKCheck +
                    " AND " + effectiveDateCol + " = (" +
                    " SELECT MIN (" + effectiveDateCol + ") FROM " + TABLENAME +
                    " WHERE " + effectiveDateCol + " >= ?" +
                    " AND " + filteredFundFKCol + " = ?" +
                    " AND " + chargeCodeFKCheck + ")";

            try
            {
                ps = conn.prepareStatement(sql);

                ps.setDate(1, DBUtil.convertStringToDate(effDate));
                ps.setLong(2, filteredFundId);

                unitValuesVOs = (UnitValuesVO[]) executeQuery(UnitValuesVO.class,
                                                              ps,
                                                              POOLNAME,
                                                              false,
                                                              null);

                if (unitValuesVOs == null || unitValuesVOs.length == 0)
                {


                    sql = "SELECT * FROM " + TABLENAME +
                          " WHERE " + filteredFundFKCol + " = " + filteredFundId +
                          " AND " + chargeCodeFKCheck +
                          " AND " + effectiveDateCol + " = (" +
                          " SELECT MAX (" + effectiveDateCol + ") FROM " + TABLENAME +
                          " WHERE " + effectiveDateCol + " <= ?" +
                          " AND " + filteredFundFKCol + " = ?" +
                          " AND " + chargeCodeFKCheck + ")";

                    ps = conn.prepareStatement(sql);

                    ps.setDate(1, DBUtil.convertStringToDate(effDate));
                    ps.setLong(2, filteredFundId);

                    unitValuesVOs = (UnitValuesVO[]) executeQuery(UnitValuesVO.class,
                                                                  ps,
                                                                  POOLNAME,
                                                                  false,
                                                                  null);
                }
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

            return unitValuesVOs;
        }
        else
        {
            if (pricingDirection.equals("Backward"))
            {
                sql =   "SELECT * FROM " + TABLENAME +
                        " WHERE " + filteredFundFKCol + " = ?" +
                        " AND " + chargeCodeFKCheck +
                        " AND " + effectiveDateCol + " = (" +
                        " SELECT MAX (" + effectiveDateCol + ") FROM " + TABLENAME +
                        " WHERE " + effectiveDateCol + " <= ?" +
                        " AND " + filteredFundFKCol + " = ?" +
                        " AND " + chargeCodeFKCheck + ")";
            }
            else
            {
                sql  =  "SELECT * FROM " + TABLENAME +
                        " WHERE " + filteredFundFKCol + " = ?" +
                        " AND " + chargeCodeFKCheck +
                        " AND " + effectiveDateCol + " = (" +
                        " SELECT MIN (" + effectiveDateCol + ") FROM " + TABLENAME +
                        " WHERE " + effectiveDateCol + " >= ?" +
                        " AND " + filteredFundFKCol + " = ?" +
                        " AND " + chargeCodeFKCheck + ")";
            }

            try
            {
                ps = conn.prepareStatement(sql);

                ps.setLong(1, filteredFundId);
                ps.setDate(2, DBUtil.convertStringToDate(effDate));
                ps.setLong(3, filteredFundId);

                unitValuesVOs = (UnitValuesVO[]) executeQuery(UnitValuesVO.class,
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

            return unitValuesVOs;
        }
    }

    public UnitValuesVO[] findByFilteredFundFKDate_ChargeCode(long filteredFundId, long chargeCodeFK, String effectiveDate)
    {
        String filteredFundFKCol = DBTABLE.getDBColumn("FilteredFundFK").getFullyQualifiedColumnName();
        String effectiveDateCol  = DBTABLE.getDBColumn("EffectiveDate").getFullyQualifiedColumnName();
        String chargeCodeFKCol   = DBTABLE.getDBColumn("ChargeCodeFK").getFullyQualifiedColumnName();

        Connection conn = ConnectionFactory.getSingleton().getConnection(POOLNAME);

        PreparedStatement ps = null;

        UnitValuesVO[] unitValuesVOs = null;

         String chargeCodeFKCheck = null;
         if (chargeCodeFK == 0)
         {
             chargeCodeFKCheck = chargeCodeFKCol + " IS NULL ";
         }
         else
         {
             chargeCodeFKCheck = chargeCodeFKCol + " = " + chargeCodeFK;
         }

         String sql = " SELECT * FROM " + TABLENAME +
                      " WHERE " + filteredFundFKCol + " = ?" +
                      " AND "  +  chargeCodeFKCheck +
                      " AND " + effectiveDateCol + " = ?";

        try
        {
            ps = conn.prepareStatement(sql);

            ps.setLong(1, filteredFundId);
            ps.setDate(2, DBUtil.convertStringToDate(effectiveDate));

            unitValuesVOs = (UnitValuesVO[]) executeQuery(UnitValuesVO.class,
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

        return unitValuesVOs;
     }
}
