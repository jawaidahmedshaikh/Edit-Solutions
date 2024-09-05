/*
 * Created by IntelliJ IDEA.
 * User: gfrosti
 * Date: Aug 5, 2002
 * Time: 11:48:07 AM
 * To change template for new class use 
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package edit.services.db;

import org.apache.commons.pool.ObjectPool;
import org.apache.commons.pool.impl.StackObjectPool;

import java.util.HashMap;
import java.util.Map;

public class CRUDFactory
{
    private static CRUDFactory crudFactory;

    private final int MIN_POOL_SIZE = 3;

    private final int MAX_POOL_SIZE = 10;

    private Map pools;

    /**
     * Constructor
     */
    private CRUDFactory()
    {
        pools = new HashMap();

        buildCRUDs();
    }

    /**
     * Returns the only instance of CRUDFactory
     * @return
     */
    synchronized public static CRUDFactory getSingleton()
    {
        if (crudFactory == null)
        {
            crudFactory = new CRUDFactory();
        }

        return crudFactory;
    }

    /**
     * Returns an available instance of the pool.
     * @param dbPoolName
     * @return
     */
    synchronized public CRUD getCRUD(String dbPoolName)
    {
        CRUD crud = null;

        ObjectPool objectPool = (ObjectPool) pools.get(dbPoolName);

        try
        {
            crud = (CRUD) objectPool.borrowObject();
        }
        catch (Exception e)
        {
            System.out.println(e);

            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.

            throw new RuntimeException(e);
        }

        return crud;
    }

    /**
     * Returns an instance to the pool.
     * @param crud
     */
    synchronized protected void returnCRUD(CRUD crud)
    {
        String dbPoolName = crud.getPoolName();

        ObjectPool objectPool = (ObjectPool) pools.get(dbPoolName);

        try
        {
            objectPool.returnObject(crud);
        }
        catch (Exception e)
        {
            System.out.println(e);

            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.

            throw new RuntimeException(e);
        }
    }

    /**
     * Builds associated CRUD instance(s) to be associated with a DB Connection.
     */
    private final void buildCRUDs()
    {
        String[] poolNames = ConnectionFactory.getSingleton().getPoolNames();

        for (int i = 0; i < poolNames.length; i++)
        {
            CRUDPoolableObjectFactory factory = new CRUDPoolableObjectFactory(poolNames[i]);

            ObjectPool objectPool = new StackObjectPool(factory, MAX_POOL_SIZE, MIN_POOL_SIZE);

            pools.put(poolNames[i], objectPool);
        }
    }
}
