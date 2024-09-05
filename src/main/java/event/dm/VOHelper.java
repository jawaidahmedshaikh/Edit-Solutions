package event.dm;

import edit.common.vo.VOObject;
import edit.services.db.*;

import java.util.*;


/**
 * Created by IntelliJ IDEA.
 * User: sdorman
 * Date: Nov 14, 2003
 * Time: 11:44:46 AM
 * To change this template use Options | File Templates.
 */
public class VOHelper
{
    private final String VO_PACKAGE = "edit.common.vo";

    private CRUD crud;

    private static Map adjacentClasses = new HashMap() ; // key = tablename, value = List of adjacent classes
    private static Map parentClasses = new HashMap();    // key = tablename, value = List of parent classes


    public VOObject compose(long pk, Class voClass, List voInclusionList)
    {
        VOClass voClassMD = VOClass.getVOClassMetaData(voClass);

        VOObject voObject = null;

        String dbPoolName = getDBPool(voClassMD);

        try
        {
            crud = CRUDFactory.getSingleton().getCRUD(dbPoolName);

            voObject = (VOObject) crud.retrieveVOFromDB(voClass, pk);
        }
        catch (Exception e)
        {
            System.out.println(e);

            e.printStackTrace();  //To change body of catch statement use Options | File Templates.

            throw new RuntimeException(e);
        }
        finally
        {
            if (crud != null) crud.close();

            crud = null;
        }

        compose(voObject, voInclusionList);

        return voObject;
    }

    public void compose(VOObject voObject, List voInclusionList)
    {
        if (voInclusionList != null)
        {
            for (int i = 0; i < voInclusionList.size(); i++)
            {
                Class voInclusionClass = (Class) voInclusionList.get(i);

                if (isParent(voObject, voInclusionClass))
                {
                    this.associate(voObject, voInclusionClass, voInclusionList);
                }
                else if (isAdjacent(voObject, voInclusionClass))
                {
                    this.append(voObject, voInclusionClass, voInclusionList);
                }
            }
        }
    }



    // *************** Private methods ******************

    private void associate(VOObject childVOObject, Class parentClass, List voInclusionList)
    {
        VOClass childVOClass = VOClass.getVOClassMetaData(childVOObject.getClass());

        VOClass parentVOClass = VOClass.getVOClassMetaData(parentClass);

        VOMethod fkMethod = childVOClass.getFKGetter(parentVOClass.getTableName());

        try
        {
            long parentPK = ((Long) fkMethod.getMethod().invoke(childVOObject, null)).longValue();

            if (parentPK != 0)
            {
                //  Remove parent from voInclusionList to avoid recursive calls.
                //  Don't change original list, just the one you send to the compose.
                List newVoInclusionList = new ArrayList(voInclusionList);
                newVoInclusionList.remove(parentClass);

                VOObject parentVO = compose(parentPK, parentClass, newVoInclusionList);

                childVOObject.setParentVO(parentClass, parentVO);
            }
        }
        catch (Exception e)
        {
            System.out.println(e);

            e.printStackTrace();  //To change body of catch statement use Options | File Templates.

            throw new RuntimeException(e);
        }
    }

    private void append(VOObject parentVOObject, Class childClass, List voInclusionList)
    {
        VOClass parentVOClass = VOClass.getVOClassMetaData(parentVOObject.getClass());
        VOClass childVOClass = VOClass.getVOClassMetaData(childClass);

        Object[] childVOs = null;

        String childDBPoolName = getDBPool(childVOClass);

        VOMethod pkGetter = parentVOClass.getPKGetter();

        try
        {
            long parentPK = ((Long) pkGetter.getMethod().invoke(parentVOObject, null)).longValue();

            childVOs = crud.retrieveVOFromDB(childClass, parentVOObject.getClass(), parentPK);
        }
        catch (Exception e)
        {
            System.out.println(e);

            e.printStackTrace();  //To change body of catch statement use Options | File Templates.

            throw new RuntimeException(e);
        }
        finally
        {
            if (crud != null) crud.close();

            crud = null;
        }

        //  Remove child from voInclusionList to avoid recursive calls.
        //  Don't change original list, just the one you send to the compose.
        List newVoInclusionList = new ArrayList(voInclusionList);
        newVoInclusionList.remove(childClass);

        if (childVOs != null)
        {
            for (int i = 0; i < childVOs.length; i++)
            {
                compose((VOObject) childVOs[i], newVoInclusionList);
            }

            VOMethod voSetterMethod = parentVOClass.getVOSetter(childVOClass.getTableName());

            try
            {
                voSetterMethod.getMethod().invoke(parentVOObject, new Object[]{childVOs});
            }
            catch (Exception e)
            {
                System.out.println(e);

                e.printStackTrace();  //To change body of catch statement use Options | File Templates.

                throw new RuntimeException(e);
            }
        }
    }

    private boolean isParent(VOObject voObject, Class voInclusionClass)
    {
        String tableName = getTableName(voObject);

        List parentClasses = getParentClasses(tableName);

        if (parentClasses != null)
        {
            if (parentClasses.contains(voInclusionClass))
            {
                return true;
            }
            else
            {
                return false;
            }
        }
        else
        {
            return false;
        }
    }

    private boolean isAdjacent(VOObject voObject, Class voInclusionClass)
    {
        String tableName = getTableName(voObject);

        List adjacentClasses = getAdjacentClasses(tableName);

        if (adjacentClasses != null)
        {
            if (adjacentClasses.contains(voInclusionClass))
            {
                return true;
            }
            else
            {
                return false;
            }
        }
        else
        {
            return false;
        }
    }

    private void initializeParentClasses(String tableName)
    {
        DBDatabase dbDatabase = DBDatabase.getDBDatabaseForTable(tableName);
        DBTable dbTable = dbDatabase.getDBTable(tableName);

        if (dbTable != null)
        {
            //  Get all the parent tables for the specified tablename
            Map parentTables = dbTable.getParentTables();

            //  Convert the tablenames to a vector of Class objects
            List classes = convertTableNamesToClassObjects(parentTables.keySet().iterator());

            //  Add the tablename and vector of Class objects to the parentClasses hashtable
            parentClasses.put(tableName, classes);
        }
    }

    private void initializeAdjacentClasses(String tableName)
    {
        DBDatabase dbDatabase = DBDatabase.getDBDatabaseForTable(tableName);
        DBTable dbTable = dbDatabase.getDBTable(tableName);

        if (dbTable != null)
        {
            //  Get all the adjacent tables for the specified tablename
            Map adjacentTables = dbTable.getAdjacentTables();

            //  Convert the tablenames to a vector of Class objects
            List classes = convertTableNamesToClassObjects(adjacentTables.keySet().iterator());

            //  Add the tablename and vector of Class objects to the adjacentClasses hashtable
            adjacentClasses.put(tableName, classes);
        }
    }

    private List getParentClasses(String tableName)
    {
        List classes = null;

        if (! parentClasses.containsKey(tableName))
        {
            initializeParentClasses(tableName);
        }

        if (parentClasses.containsKey(tableName))   //  Check again because above initialization may have added it
        {
            classes = (List) parentClasses.get(tableName);
            return classes;
        }
        else
        {
            return null;
        }
    }

    private List getAdjacentClasses(String tableName)
    {
        List classes = null;

        if (! adjacentClasses.containsKey(tableName))
        {
            initializeAdjacentClasses(tableName);
        }

        if (adjacentClasses.containsKey(tableName))   //  Check again because above initialization may have added it
        {
            classes = (List) adjacentClasses.get(tableName);
            return classes;
        }
        else
        {
            return null;
        }
    }

    private String getDBPool(VOClass voClass)
    {
        String tableName = voClass.getTableName();

        DBDatabase dbDatabase = DBDatabase.getDBDatabaseForTable(tableName);

        return dbDatabase.getPoolName();
    }

    private List convertTableNamesToClassObjects(Iterator tableNames)
    {
        List classes = new ArrayList();

        //  Create a Class for each parent table name and add to the vector
        while (tableNames.hasNext())
        {
            String tableName = (String) tableNames.next();
            String className = this.VO_PACKAGE + "." + tableName + "VO";

            Class c = null;

            try
            {
                c = Class.forName(className);
            }
            catch (ClassNotFoundException e)
            {
                System.out.println(e);

                e.printStackTrace();  //To change body of catch statement use Options | File Templates.

                throw new RuntimeException(e);
            }

            classes.add(c);
        }

        return classes;
    }

    private String getTableName(VOObject voObject)
    {
        VOClass voObjectVOClass = VOClass.getVOClassMetaData(voObject.getClass());
        String tableName = voObjectVOClass.getTableName();

        return tableName;
    }
}