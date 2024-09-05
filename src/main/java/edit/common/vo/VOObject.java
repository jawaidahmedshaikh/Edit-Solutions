/*
 * Created by IntelliJ IDEA.
 * User: gfrosti
 * Date: Aug 20, 2002
 * Time: 1:06:00 PM
 * To change template for new class use
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package edit.common.vo;

import edit.services.db.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.io.*;

import contract.dm.dao.DAOFactory;
import org.dom4j.*;
import org.dom4j.io.*;
import fission.utility.*;

public class VOObject implements Cloneable
{
    private boolean voChanged;
    private boolean voShouldBeDeleted;

    private Map parentVOs;      // key = Class, value = VOObject

    private boolean collectionsCleared = false;

    public VOObject()
    {
    }

    public void clearCollections()
    {
        if (! collectionsCleared)
        {
            VOClass voClass = VOClass.getVOClassMetaData(getClass());

            VOMethod[] voRemovers = voClass.getVORemovers();

            try
            {
                for (int i = 0; i < voRemovers.length; i++)
                {
                    voRemovers[i].getMethod().invoke(this, null);
                }
            }
            catch (IllegalAccessException e)
            {
              System.out.println(e);

                e.printStackTrace();  //To change body of catch statement use Options | File Templates.

                throw new RuntimeException(e.getMessage());
            }
            catch (IllegalArgumentException e)
            {
              System.out.println(e);

                e.printStackTrace();  //To change body of catch statement use Options | File Templates.

                throw new RuntimeException(e.getMessage());
            }
            catch (InvocationTargetException e)
            {
              System.out.println(e);

                e.printStackTrace();  //To change body of catch statement use Options | File Templates.

                throw new RuntimeException(e.getMessage());
            }
            finally
            {
                collectionsCleared = true;
            }
        }
    }

    public Object cloneVO()
    {
        try
        {
            Object newVO = getClass().newInstance();

            VOClass voClassMD = VOClass.getVOClassMetaData(getClass());
            String tableName = voClassMD.getTableName();

            DBDatabase dbDatabase = DBDatabase.getDBDatabaseForTable(tableName);
            DBTable dbTable = dbDatabase.getDBTable(tableName);
            DBColumn[] dbColumns = dbTable.getDBColumns();

            for (int i = 0; i < dbColumns.length; i++)
            {
                VOMethod newVOSetter = voClassMD.getSimpleSetter(dbColumns[i].getColumnName());
                VOMethod currentVOGetter = voClassMD.getSimpleGetter(dbColumns[i].getColumnName());

                newVOSetter.getMethod().invoke(newVO, new Object[]{currentVOGetter.getMethod().invoke(this, null)});
            }

            return newVO;
        }
        catch (Exception e)
        {
            String msg = "edit.common.vo.VOObject.clone(): " + e.getMessage();
            System.out.println(msg);
          System.out.println(e);

            e.printStackTrace();

            throw new RuntimeException(msg);
        }
    }

    public boolean equals(Object obj)
    {
        // Override Castor's default behavior for the VO's
        return true;
    }

     public void validate() throws org.exolab.castor.xml.ValidationException
     {
         // Castor generates the validate method for the VOs
     }

    public void setVoChanged(boolean voChanged)
    {
        this.voChanged = voChanged;
    }

    public boolean getVoChanged()
    {
        return this.voChanged;
    }

    public void setVoShouldBeDeleted(boolean voShouldBeDeleted)
    {
        this.voShouldBeDeleted = voShouldBeDeleted;
    }

    public boolean getVoShouldBeDeleted()
    {
        return this.voShouldBeDeleted;
    }

    public void setParentVO(Class parentVOClass, VOObject parentVO)
    {
        if (parentVOs == null)
        {
            parentVOs = new HashMap();
        }

        parentVOs.put(parentVOClass, parentVO);
    }

    public VOObject getParentVO(Class parentVOClass)
    {
        return (VOObject) parentVOs.get(parentVOClass);
    }

    public Map getParentVOs()
    {
        return parentVOs;
    }

    public void copyFrom(VOObject copyFromVO)
    {
        VOMethod[] copyFromGetters = VOClass.getVOClassMetaData(copyFromVO.getClass()).getSimpleAndCTAndFKAndPKGetters();

        try
        {
            for (int i = 0; i < copyFromGetters.length; i++)
            {
                String getterName = copyFromGetters[i].getMethod().getName();

                Class getterType = copyFromGetters[i].getTargetFieldType();

                Object getterValue = copyFromGetters[i].getMethod().invoke(copyFromVO, null);

                String setterName = "set" + getterName.substring(3, getterName.length());

                Method copyToSetter = this.getClass().getMethod(setterName, new Class[]{getterType});

                copyToSetter.invoke(this, new Object[]{getterValue});
            }
        }
        catch (Exception e)
        {
            System.out.println(e);

            e.printStackTrace();  //To change body of catch statement use Options | File Templates.

            throw new RuntimeException(e);
        }
    }

    protected void finalize() throws Throwable
    {
        super.finalize();    //To change body of overridden methods use File | Settings | File Templates.

        clearCollections();
    }

    /**
     * Maps a VOObject to its Dom4J Element equivalent.
     * @param voObject
     * @return
     */
    public static Element map(VOObject voObject)
    {
        Element element;

        try
        {
            Document document = XMLUtil.parse(Util.marshalVO(voObject));

            element = document.getRootElement();

            element.setDocument(null);
        }
        catch (Exception e)
        {
            System.out.println(e);

            e.printStackTrace();

            throw new RuntimeException(e);
        }

        return element;
    }
    
  /**
   * Gets the primary key for a value object
   *
   * @return The primary key
   */
  public long getPKValue()
  {
      VOClass voClassMD = VOClass.getVOClassMetaData(getClass());

      Method pkGetter = voClassMD.getPKGetter().getMethod();

      long pkVal = 0;

      try
      {
          pkVal = ((Long) pkGetter.invoke(this, null)).longValue();
      }
      catch (Exception e)
      {
          System.out.println(e);

          e.printStackTrace(); //To change body of catch statement use Options | File Templates.

          throw new RuntimeException(e);
      }

      return pkVal;
  }    
}
