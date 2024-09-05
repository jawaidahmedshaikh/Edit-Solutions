/**
 * Created by IntelliJ IDEA.
 * User: gfrosti
 * Date: Jun 10, 2002
 * Time: 11:07:20 AM
 * To change template for new class use
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package edit.services.db;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VOClass
{
    private String tableName;

    private String pkColumnName;

    private Class voClass;

    private List childVOClasses;

    private boolean isReflexiveVO = false;

    private Map voAdders;
    private Map voGetters;
    private List voRemovers;
    private Map simpleGetters;
    private Map simpleSetters;
    private Map voSetters;
    private VOMethod pkGetter;
    private VOMethod pkSetter;


    private static Map voClasses;
    
    /**
     * Serializes the creation of VOClasses in a multi-threaded environment.
     */
    private static final Object MONITOR_NEW_VOCLASS = new Object();

    /**
     * Serializes thre creation of Child VOClasses in a multi-thread environment.
     */
    private static final Object MONITOR_CHILD_VOCLASS = new Object();

    static
    {
        voClasses = new HashMap();
    }

    public VOClass(Class voClass)
    {
        this.voClass = voClass;
        this.tableName = getTableName(voClass);
        this.pkColumnName = getPKColumnName(tableName);
        this.voAdders = new HashMap();
        this.voGetters = new HashMap();
        this.simpleGetters = new HashMap();
        this.simpleSetters = new HashMap();
        this.voSetters = new HashMap();
        this.voRemovers = new ArrayList();

        loadMethods();
    }

    public static VOClass getVOClassMetaData(Class voClass)
    {
        VOClass voClassMD = null;

        if ((voClassMD = (VOClass) voClasses.get(voClass)) == null)
        {
            synchronized (MONITOR_NEW_VOCLASS)
            {
              voClassMD = new VOClass(voClass);
  
              voClasses.put(voClass, voClassMD);
            }
        }

        return voClassMD;
    }

    public boolean isReflexiveVO()
    {
        return isReflexiveVO;
    }

    protected Class getVOClass()
    {
        return voClass;
    }

    private void loadMethods()
    {
        Method[] allMethods = voClass.getDeclaredMethods();

        for (int i = 0; i < allMethods.length; i++)
        {
            int methodCategory = VOMethod.getMethodCategory(allMethods[i]);

            // Check the validity of the method category
            if (methodCategory != -1)
            {
                VOMethod voMethod = new VOMethod(allMethods[i]);

                if (methodCategory == VOMethod.VO_ADDER)
                {
                    Class paramType = voMethod.getMethod().getParameterTypes()[0];

                    if (paramType.equals(voClass))
                    {
                        isReflexiveVO = true;
                    }

                    String methodName = voMethod.getMethod().getName();
                    String tableName = methodName.substring(3, methodName.length() - 2);

                    voAdders.put(tableName, voMethod);
                }

                else if (methodCategory == VOMethod.VO_GETTER)
                {
                    String methodName = voMethod.getMethod().getName();
                    String tableName = methodName.substring(3, methodName.length() - 2);

                    voGetters.put(tableName, voMethod);
                }

                else if (methodCategory == VOMethod.CT_GETTER || methodCategory == VOMethod.FK_GETTER || methodCategory == VOMethod.PK_GETTER || methodCategory == VOMethod.SIMPLE_GETTER)
                {
                    String methodName = voMethod.getMethod().getName();
                    String columnName = methodName.substring(3, methodName.length());

                    simpleGetters.put(columnName, voMethod);
                }

                else if (methodCategory == VOMethod.CT_SETTER || methodCategory == VOMethod.FK_SETTER || methodCategory == VOMethod.PK_SETTER || methodCategory == VOMethod.SIMPLE_SETTER)
                {
                    String methodName = voMethod.getMethod().getName();
                    String columnName = methodName.substring(3, methodName.length());

                    simpleSetters.put(columnName, voMethod);
                }
                else if (methodCategory == VOMethod.VO_SETTER)
                {
                    String methodName = voMethod.getMethod().getName();
                    String columnName = methodName.substring(3, methodName.length() - 2);

                    voSetters.put(columnName, voMethod);
                }

                else if (methodCategory == VOMethod.VO_REMOVER)
                {
                    voRemovers.add(voMethod);                }
            }
        }
    }

    public String getTableName()
    {
        return tableName;
    }

    /**
     * Returns the database table associated with the target VO Class
     *
     * @param voClass The target VO Class
     * @return The associated table name
     */
    public static String getTableName(Class voClass)
    {
        String qualifiedClassName = voClass.getName();

        String tableName = qualifiedClassName.substring(CRUD.VO_PACKAGE_NAME.length() + 1, qualifiedClassName.length() - 2);

        return tableName;
    }

    public VOMethod[] getSimpleAndCTAndFKAndPKGetters()
    {
        if (simpleGetters.size() == 0)
        {
            return null;
        }
        else
        {
            return (VOMethod[]) simpleGetters.values().toArray(new VOMethod[simpleGetters.size()]);
        }
    }

    private VOMethod[] getVOAdders()
    {
        if (voAdders.size() == 0)
        {
            return null;
        }
        else
        {
            return (VOMethod[]) voAdders.values().toArray(new VOMethod[voAdders.size()]);
        }
    }

    public VOMethod[] getVORemovers()
    {
        return (VOMethod[]) voRemovers.toArray(new VOMethod[voRemovers.size()]);
    }

    public VOMethod getVOSetter(String childTableName)
    {
        return (VOMethod) voSetters.get(childTableName);
    }

    public VOMethod getVOGetter(String childTableName)
    {
        return (VOMethod) voGetters.get(childTableName);
    }

    public VOMethod[] getVOGetters()
    {
        return (VOMethod[]) voGetters.values().toArray(new VOMethod[voGetters.size()]);
    }

    public String getPKColumnName()
    {
        return this.pkColumnName;
    }

    /**
     * Returns the database primary key column name for the target VO Class
     *
     * @param The target VO Class
     * @return The database column name
     */
    protected static String getPKColumnName(String parentTableName)
    {
        return parentTableName + "PK";
    }

    /**
     * Returns the foreign key column name for a given parent /child VO Class relationship
     */
    protected static String getFKColumnName(String parentTableName)
    {
        return parentTableName + "FK";
    }

    /**
     * Returns the appropriate PK getter method forthe target VO Class
     *
     * @param voClass The target VO Class
     * @return The target PK getter method
     */
    public VOMethod getPKGetter()
    {
        if (pkGetter == null)
        {
            String columnName = tableName + "PK";

            pkGetter = (VOMethod) simpleGetters.get(columnName);
        }

        return pkGetter;
    }

    /**
     * Returns the appropriate PK setter method forthe target VO Class
     *
     * @param voClass The target VO Class
     * @return The target PK setter method
     */
    public VOMethod getPKSetter()
    {
        if (pkSetter == null)
        {
            String columnName = tableName + "PK";

            pkSetter = (VOMethod) simpleSetters.get(columnName);
        }

        return pkSetter;
    }

      /**
     * Builds the simple setter associated with a table column
     *
     * @param columnName The target column name
     * @param voClass The target VO Class
     * @param params The necessary parameter Class types for the methods signature
     * @exception Exception
     */
    public VOMethod getSimpleSetter(String columnName)
    {
        return (VOMethod) simpleSetters.get(columnName);
    }

    public VOMethod getSimpleGetter(String columnName)
    {
        return (VOMethod) simpleGetters.get(columnName);
    }

    /**
     * Returns the non-indexed adder method for the target child VO Class
     *
     * @param parentVOClass The target parent VO Class
     * @param childVOClass The target child VO Class
     * @return The target adder method
     */
    protected VOMethod getVOAdder(String childTableName)
    {
        return (VOMethod) voAdders.get(childTableName);
    }

    /**
     * Returns the appropriate FK setter method for a given parent/child VO relationship
     *
     * @param parentVOClas The target VO Class
     * @param childVOClass The target VO Class
     * @return The target FK setter method
     */
    public VOMethod getFKSetter(String parentTableName)
    {
        String columnsName = parentTableName + "FK";

        return (VOMethod) simpleSetters.get(columnsName);
    }

    public VOMethod getFKGetter(String parentTableName)
    {
        String columnsName = parentTableName + "FK";

        return (VOMethod) simpleGetters.get(columnsName);
    }

    /**
     * Gets all 1st level child VO Classes for the target parentVOClass
     *
     * @param parentVOClass The target parent VO Class
     * @param voClassExclusionList The set of child VO Clases that should not be included in the list
     * @return The array of 1st level child VO Classes
     */
    public Class[] getChildVOClasses(List childVOsExclusionList)
    {
        if (childVOClasses == null)
        {
            synchronized (MONITOR_CHILD_VOCLASS)
            {
              childVOClasses = new ArrayList();
  
              VOMethod[] adderMethods = getVOAdders();
  
              if (adderMethods != null)
              {
                  for (int i = 0; i < adderMethods.length; i++)
                  {
                      Class childVOClass = adderMethods[i].getMethod().getParameterTypes()[0];
                      
                      // Prevent double entries in a multithreaded environment.
                      if (!childVOClasses.contains(childVOClass)) 
                      {
                        childVOClasses.add(childVOClass);
                      }
                  }
              }
            }
        }

        if (childVOClasses.size() == 0)
        {
            return null;
        }

        if (childVOsExclusionList == null)
        {
            return (Class[]) childVOClasses.toArray(new Class[childVOClasses.size()]);
        }
        else
        {
            List returnClasses = new ArrayList();

            for (int i = 0; i < childVOClasses.size(); i++)
            {
                if (childVOsExclusionList == null || !childVOsExclusionList.contains(childVOClasses.get(i)))
                {
                    returnClasses.add(childVOClasses.get(i));
                }
            }

            return (Class[]) (returnClasses.toArray(new Class[returnClasses.size()]));
        }
    }
}
