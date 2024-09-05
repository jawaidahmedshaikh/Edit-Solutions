/**
 * Created by IntelliJ IDEA.
 * User: gfrosti
 * Date: Dec 13, 2004
 * Time: 11:37:52 AM
 * To change this template use File | Settings | File Templates.
 */
package businesscalendar.dm.dao;

import edit.common.vo.BusinessDayVO;

import edit.services.db.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.ArrayList;

import com.javaunderground.jdbc.*;


public class BusinessDayDAO extends DAO
{
    private final DBTable DBTABLE;
    private final String POOLNAME;
    private final String TABLENAME;

    public BusinessDayDAO()
    {
        POOLNAME = ConnectionFactory.EDITSOLUTIONS_POOL;
        DBTABLE = DBTable.getDBTableForTable("BusinessDay");
        TABLENAME = DBTABLE.getFullyQualifiedTableName();
    }

    /**
     * Finder.
     * @return
     */
    public BusinessDayVO[] findAll()
    {
        String sql = " SELECT * FROM " + TABLENAME;

        return (BusinessDayVO[]) executeQuery(BusinessDayVO.class, sql, POOLNAME, false, null);
    }

    /**
     * Finder.
     * @param businessDayPK
     * @return
     */
    public BusinessDayVO[] findByPK(long businessDayPK)
    {
        String businessDayPKCol = DBTABLE.getDBColumn("BusinessDayPK").getFullyQualifiedColumnName();

        String sql = " SELECT * FROM " + TABLENAME + " WHERE " + businessDayPKCol + " = " + businessDayPK;

        return (BusinessDayVO[]) executeQuery(BusinessDayVO.class, sql, POOLNAME, false, null);
    }

    /**
     * Finder.
     * @param businessDate
     * @return
     */
    public BusinessDayVO[] findBy_BusinessDate_ActiveInd(String businessDate, String activeInd)
    {
        BusinessDayVO[] businessDayVOs = null;

        String businessDateCol = DBTABLE.getDBColumn("BusinessDate").getFullyQualifiedColumnName();
        String activeIndCol    = DBTABLE.getDBColumn("ActiveInd").getFullyQualifiedColumnName();

        String sql;
        sql = " SELECT * " + " FROM " + TABLENAME +
              " WHERE " + businessDateCol + " = ? " +
              " AND " + activeIndCol + " = ?";
        System.out.println("findBy_BusinessDate_ActiveInd: " + sql);
        System.out.println("findBy_BusinessDate_ActiveInd: businessDate: " + businessDate);
              
        Connection conn = ConnectionFactory.getSingleton().getConnection(POOLNAME);
        
        PreparedStatement ps = null;
        
        try
        {
            ps = conn.prepareStatement(sql);
            
            ps.setDate(1, DBUtil.convertStringToDate(businessDate));
            ps.setString(2, activeInd);
            
            businessDayVOs = (BusinessDayVO[]) executeQuery(BusinessDayVO.class, ps, POOLNAME, false, null);
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
              
        return businessDayVOs;
    }

    /**
     * Finder. BusinessDate1 is assumed <= BusinessDate2.
     * @param businessDate1YYYYMMDD
     * @param businessDate2YYYYMMDD
     * @return
     */
    public BusinessDayVO[] findBy_BusinessDate1_MAX_LT_BusinessDate2_ActiveInd(String businessDate1YYYYMMDD, String businessDate2YYYYMMDD, String activeInd)
    {
        BusinessDayVO[] businessDayVOs = null;
        
        String businessDateCol = DBTABLE.getDBColumn("BusinessDate").getFullyQualifiedColumnName();
        String activeIndCol    = DBTABLE.getDBColumn("ActiveInd").getFullyQualifiedColumnName();

        String sql;
        sql = " SELECT *" + 
              " FROM " + TABLENAME +
              " WHERE (" + businessDateCol + " = " + 
                "(SELECT MAX(" + businessDateCol + ")" + 
                " FROM " + TABLENAME + 
                " WHERE (" + businessDateCol + " < ?)" +
                  " AND (" + businessDateCol + " >= ?)" +
                  " AND " + activeIndCol + " = ?))";
                  
        Connection conn = ConnectionFactory.getSingleton().getConnection(ConnectionFactory.EDITSOLUTIONS_POOL);
        
        PreparedStatement ps = null;
        
        try 
        {
            ps = conn.prepareStatement(sql);
            
            ps.setDate(1, DBUtil.convertStringToDate(businessDate2YYYYMMDD));
            ps.setDate(2, DBUtil.convertStringToDate(businessDate1YYYYMMDD));
            ps.setString(3, activeInd);
            
            businessDayVOs = (BusinessDayVO[]) executeQuery(BusinessDayVO.class, ps, POOLNAME, false, null);
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

        return businessDayVOs;
    }

    /**
     * Finder. BusinessDate1 is assumed <= BusinessDate2.
     * @param businessDate1YYYYMMDD
     * @param businessDate2YYYYMMDD
     * @return
     */
    public BusinessDayVO[] findBy_BusinessDate1_MIN_GT_BusinessDate2(String businessDate1YYYYMMDD, String businessDate2YYYYMMDD)
    {
        BusinessDayVO[] businessDayVOs = null;
        
        String businessDateCol = DBTABLE.getDBColumn("BusinessDate").getFullyQualifiedColumnName();

        String sql;
        sql = " SELECT *" + 
              " FROM " + TABLENAME + 
              " WHERE (" + businessDateCol + " = " + 
              "(SELECT MIN(" + businessDateCol + ")" + " " +
                "FROM " + TABLENAME + " " +
                "WHERE (" + businessDateCol + " > ?)" + 
                " AND (" + businessDateCol + " <= ?)))";
        
        Connection conn = ConnectionFactory.getSingleton().getConnection(ConnectionFactory.EDITSOLUTIONS_POOL);
        
        PreparedStatement ps = null;
        
        try 
        {
            ps = conn.prepareStatement(sql);
            
            ps.setDate(1, DBUtil.convertStringToDate(businessDate1YYYYMMDD));
            ps.setDate(2, DBUtil.convertStringToDate(businessDate2YYYYMMDD));
            
            businessDayVOs = (BusinessDayVO[]) executeQuery(BusinessDayVO.class, ps, POOLNAME, false, null);
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

        return businessDayVOs;
    }

    /**
     * Finds the set of BusinessDays greater than the specified date ordered by BusinessDate Ascending.
     * @param businessDate
     * @param maxRows
     * @return
     */
    public BusinessDayVO[] findBy_MaxRows_BusinessDate_GT(String businessDate, int maxRows)
    {
        Connection conn = null;

        PreparedStatement ps = null;

        ResultSet rs = null;

        BusinessDayVO[] businessDayVOs = null;

        try
        {
            conn = ConnectionFactory.getSingleton().getConnection(ConnectionFactory.EDITSOLUTIONS_POOL);

            String businessDateCol = DBTABLE.getDBColumn("BusinessDate").getFullyQualifiedColumnName();
            String activeIndCol = DBTABLE.getDBColumn("ActiveInd").getFullyQualifiedColumnName();

            String sql;
            sql =   "SELECT * " +
                    " FROM " + TABLENAME +
                    " WHERE " + businessDateCol + " > ?" +
                    " AND " + activeIndCol + " = 'Y'" +
                    " ORDER BY " + businessDateCol + " ASC ";

//            ps = StatementFactory.getStatement(conn, sql, new EDITSolutionsSqlFormatter(), DebugLevel.ON);
            ps = conn.prepareStatement(sql);

            ps.setMaxRows(maxRows);
            
            ps.setDate(1, DBUtil.convertStringToDate(businessDate));

//            System.out.println(" debuggable statement= " + ps.toString());

            businessDayVOs = (BusinessDayVO[]) executeQuery(BusinessDayVO.class, ps, POOLNAME, false, null);
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
                if (rs != null)
                {
                    rs.close();
                }

                if (ps != null)
                {
                    ps.close();
                }

                if (conn != null)
                {
                    conn.close();
                }
            }
            catch (SQLException e)
            {
                System.out.println(e);

                e.printStackTrace();

                throw new RuntimeException(e);
            }
        }

        return businessDayVOs;
    }

    /**
     * Finds the set of BusinessDays less than the specified date ordered by BusinessDate descending.
     * @param businessDate
     * @param maxRows
     * @return
     */
    public BusinessDayVO[] findBy_MaxRows_BusinessDate_LT_ActiveInd(String businessDate, int maxRows, String activeInd)
    {
        Connection conn = null;

        PreparedStatement ps = null;

        ResultSet rs = null;

        BusinessDayVO[] businessDayVOs = null;

        List<BusinessDayVO> results = new ArrayList<BusinessDayVO>();

        try
        {
            conn = ConnectionFactory.getSingleton().getConnection(ConnectionFactory.EDITSOLUTIONS_POOL);

            String businessDateCol = DBTABLE.getDBColumn("BusinessDate").getFullyQualifiedColumnName();
            String activeIndCol = DBTABLE.getDBColumn("ActiveInd").getFullyQualifiedColumnName();

            String sql;
            sql =   "SELECT * " +
                    " FROM " + TABLENAME +
                    " WHERE " + businessDateCol + " < ?" +
                    " AND " + activeIndCol + " = ? " +
                    " ORDER BY " + businessDateCol + " DESC ";

            ps = conn.prepareStatement(sql);

            ps.setMaxRows(maxRows);
            
            ps.setDate(1, DBUtil.convertStringToDate(businessDate));
            ps.setString(2, activeInd);
            
//            rs = ps.executeQuery(sql);

            businessDayVOs = (BusinessDayVO[]) executeQuery(BusinessDayVO.class, ps, POOLNAME, false, null);

//            VOClass voClassMD = VOClass.getVOClassMetaData(BusinessDayVO.class);
//
//            while (rs.next())
//            {
//                BusinessDayVO currentBusinessDayVO = (BusinessDayVO) CRUD.populateVOFromResultSetRow(rs, voClassMD);
//
//                results.add(currentBusinessDayVO);
//            }
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
                if (rs != null)
                {
                    rs.close();
                }

                if (ps != null)
                {
                    ps.close();
                }

                if (conn != null)
                {
                    conn.close();
                }
            }
            catch (SQLException e)
            {
                System.out.println(e);

                e.printStackTrace();

                throw new RuntimeException(e);
            }
        }

        return businessDayVOs;
    }

    /**
     * Finds the set of BusinessDays greater than the specified date ordered by BusinessDate Ascending.
     * @param businessDate
     * @param maxRows
     * @return
     */
    public BusinessDayVO[] findBy_MaxRows_BusinessDate_LT(String businessDate, int maxRows)
    {
        Connection conn = null;

        PreparedStatement ps = null;

        ResultSet rs = null;

        BusinessDayVO[] businessDayVOs = null;

        List<BusinessDayVO> results = new ArrayList<BusinessDayVO>();

        try
        {
            conn = ConnectionFactory.getSingleton().getConnection(ConnectionFactory.EDITSOLUTIONS_POOL);

            String businessDateCol = DBTABLE.getDBColumn("BusinessDate").getFullyQualifiedColumnName();

            String sql;
            sql =   "SELECT * " +
                    " FROM " + TABLENAME +
                    " WHERE " + businessDateCol + " < ?" +
                    " ORDER BY " + businessDateCol + " DESC ";

            ps = conn.prepareStatement(sql);

            ps.setMaxRows(maxRows);
            
            ps.setDate(1, DBUtil.convertStringToDate(businessDate));
            
//            rs = ps.executeQuery(sql);

            businessDayVOs = (BusinessDayVO[]) executeQuery(BusinessDayVO.class, ps, POOLNAME, false, null);

//            VOClass voClassMD = VOClass.getVOClassMetaData(BusinessDayVO.class);
//
//            while (rs.next())
//            {
//                BusinessDayVO currentBusinessDayVO = (BusinessDayVO) CRUD.populateVOFromResultSetRow(rs, voClassMD);
//
//                results.add(currentBusinessDayVO);
//            }
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
                if (rs != null)
                {
                    rs.close();
                }

                if (ps != null)
                {
                    ps.close();
                }

                if (conn != null)
                {
                    conn.close();
                }
            }
            catch (SQLException e)
            {
                System.out.println(e);

                e.printStackTrace();

                throw new RuntimeException(e);
            }
        }

        return businessDayVOs;
    }

    /**
     * Finds the set of BusinessDays within the specified range inclusive.
     * @param beginDate
     * @param endDate
     * @return
     */
    public BusinessDayVO[] findBy_Range_Inclusive(String beginDate, String endDate)
    {
        BusinessDayVO[] businessDayVOs = null;

        String businessDateCol = DBTABLE.getDBColumn("BusinessDate").getFullyQualifiedColumnName();
        String activeIndCol    = DBTABLE.getDBColumn("ActiveInd").getFullyQualifiedColumnName();

        String sql;
        sql =   " SELECT * " +
                " FROM " + TABLENAME +
                " WHERE " + businessDateCol + " >= ?" +
                " AND " + businessDateCol + " <= ?" +
                " AND " + activeIndCol + " = ?" + 
                " ORDER BY " + businessDateCol + " ASC";
                
        Connection conn = ConnectionFactory.getSingleton().getConnection(POOLNAME);

        PreparedStatement ps = null;
        
        try
        {
            ps = conn.prepareStatement(sql);
            
            ps.setDate(1, DBUtil.convertStringToDate(beginDate));
            ps.setDate(2, DBUtil.convertStringToDate(endDate));
            ps.setString(3, "Y");
            
            businessDayVOs = (BusinessDayVO[]) executeQuery(BusinessDayVO.class, ps, POOLNAME, false, null);
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

        return businessDayVOs;
    }
}
