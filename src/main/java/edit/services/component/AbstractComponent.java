/*
 * Created by IntelliJ IDEA.
 * User: gfrosti
 * Date: Oct 16, 2002
 * Time: 9:12:59 AM
 * To change template for new class use
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package edit.services.component;

import edit.services.db.CRUD;
import edit.services.db.CRUDFactory;

import java.util.List;

public class AbstractComponent {

    public long createOrUpdateVO(Object voObject, String poolName, boolean recursively)
    {
        CRUD crud = null;

        long primaryKey = 0;

        try
        {
            crud = CRUDFactory.getSingleton().getCRUD(poolName);

            if (recursively)
            {
                primaryKey = crud.createOrUpdateVOInDBRecursively(voObject, false);
            }
            else
            {
                primaryKey = crud.createOrUpdateVOInDB(voObject);
            }
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
        }

        return primaryKey;
    }

    public int deleteVO(Class voClass, long primaryKey, String poolName, boolean recursively)
    {
        CRUD crud = null;

        int numDeleted = 0;

        try
        {
            crud = CRUDFactory.getSingleton().getCRUD(poolName);

            if (recursively)
            {
                numDeleted = crud.deleteVOFromDBRecursively(voClass, primaryKey);
            }
            else
            {
                numDeleted = crud.deleteVOFromDB(voClass, primaryKey);
            }
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
        }

        return numDeleted;
    }

    public Object retrieveVO(Class voClass, long primaryKey, String poolName, boolean recursively, List voInclusionList)
    {
        CRUD crud = null;

        Object voObject = null;

        try
        {
            crud = CRUDFactory.getSingleton().getCRUD(poolName);

            if (recursively)
            {
                voObject = crud.retrieveVOFromDB(voClass, primaryKey);

                crud.retrieveVOFromDBRecursively(voObject, voInclusionList, true);
            }
            else
            {
                voObject = crud.retrieveVOFromDB(voClass, primaryKey);
            }
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
        }

        return voObject;
    }
}
