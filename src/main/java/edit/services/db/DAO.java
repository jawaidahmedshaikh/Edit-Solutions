/*
* Created by IntelliJ IDEA.
* User: cgleason
* Date: Mar 27, 2002
* Time: 12:34:48 PM
* To change template for new class use
* Code Style | Class Templates options (Tools | IDE Options).
*/
package edit.services.db;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *  This class is an abstract class to be subclassed by all DAO objects.
 *  It provides common services such as caching and connection management.
 */


public abstract class DAO implements Serializable
{
    /**
     * Executes a defined SQL query for the purpose mapping the ResultSet to its value objects.
     * @param targetVOClass
     * @param sql
     * @param poolName Used to find the associated DB Connection
     * @param includeChildVOs true of child value objects should be recursively retrieved.
     * @param childVOsExclusionList
     * @return
     */ 
    public Object[] executeQuery(Class targetVOClass,
                                              String sql,
                                              String poolName,
                                              boolean includeChildVOs,
                                              List childVOsExclusionList)

    {
        Object[] valueObjects = null;

        Connection conn = null;
        Statement s = null;
        ResultSet rs = null;

        try
        {
            conn = ConnectionFactory.getSingleton().getConnection(poolName);

            s = conn.createStatement();

            rs = s.executeQuery(sql);

            valueObjects = processResultSet(targetVOClass, rs, poolName, includeChildVOs, childVOsExclusionList);
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
                
                rs = null;
            
                if (s != null) s.close();
                
                s = null;
                
                if (conn != null) conn.close();
                
                conn = null;
            }
            catch (SQLException e)
            {
                System.out.println(e);

                e.printStackTrace();  //To change body of catch statement use Options | File Templates.

                throw new RuntimeException(e);
            }
        }

        return valueObjects;
    }

    /**
     * Executes a defined SQL query for the purpose mapping the ResultSet to its value objects.
     * The passed-in PreparedStatement is [NOT] closed.
     * @param targetVOClass
     * @param ps
     * @param poolName Used to find the associated DB Connection
     * @param includeChildVOs true of child value objects should be recursively retrieved.
     * @param childVOsExclusionList
     * @return
     */
    public Object[] executeQuery(Class targetVOClass,
                                              PreparedStatement ps,
                                              String poolName,
                                              boolean includeChildVOs,
                                              List childVOsExclusionList)
    {
        Object[] valueObjects = null;

        ResultSet rs = null;

        try
        {
            rs = ps.executeQuery();

            valueObjects = this.processResultSet(targetVOClass, rs, poolName, includeChildVOs, childVOsExclusionList);
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
                
                rs = null;
            }
            catch (SQLException e)
            {
                System.out.println(e);

                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.

                throw new RuntimeException(e);
            }
        }

        return valueObjects;
    }

    /**
     * Maps the ResultSet to the target VOs. The passed-in ResultSet is closed.
     * @param targetVOClass
     * @param rs
     * @param poolName
     * @param includeChildVOs
     * @param childVOsExclusionList
     * @return
     */
    private Object[] processResultSet(Class targetVOClass, ResultSet rs, String poolName, boolean includeChildVOs, List childVOsExclusionList)
    {
        Object valueObjects = null;

        List results = new ArrayList();

        CRUD crud = null;

        try
        {
            if (includeChildVOs)
            {
                crud = CRUDFactory.getSingleton().getCRUD(poolName);
            }

            while (rs.next())
            {
                Object parentVO = CRUD.populateVOFromResultSetRow(rs, VOClass.getVOClassMetaData(targetVOClass));

                if (includeChildVOs)
                {
                    crud.retrieveVOFromDBRecursively(parentVO, childVOsExclusionList, false);

                    results.add(parentVO);
                }
                else
                {
                    results.add(parentVO);
                }
            }

            if (results.size() == 0)
            {
                valueObjects = null;
            }
            else
            {
                valueObjects = Array.newInstance(targetVOClass, results.size());

                int resultsSize = results.size();

                for (int i = 0; i < resultsSize; i++)
                {
                    Array.set(valueObjects, i, results.get(i));
                }
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
            if (crud != null) crud.close();
        }

        if (valueObjects != null)
        {
            return (Object[]) valueObjects;
        }
        else
        {
            return null;
        }
    }
}
