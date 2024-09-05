/*
 * Created by IntelliJ IDEA.
 * User: gfrosti
 * Date: Aug 5, 2002
 * Time: 11:48:07 AM
 * To change template for new class use
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package engine.sp;

import org.apache.commons.pool.ObjectPool;
import org.apache.commons.pool.impl.StackObjectPool;

import java.util.HashMap;
import java.util.Map;

public class ScriptProcessorFactory
{
    private static ScriptProcessorFactory spFactory;

    private static final String POOL_NAME = "scriptProcessor-pool";

    private final int MIN_POOL_SIZE = 3;

    private final int MAX_POOL_SIZE = 10;

    private Map pools;

    /**
     * Constructor
     */
    private ScriptProcessorFactory()
    {
        pools = new HashMap();

        buildScriptProcessors();
    }

    /**
     * Returns the only instance of ScriptProcessorFactory
     * @return
     */
    public static ScriptProcessorFactory getSingleton()
    {
        if (spFactory == null)
        {
            spFactory = new ScriptProcessorFactory();
        }

        return spFactory;
    }

    /**
     * Returns an available instance of the pool.
     * @param dbPoolName
     * @return
     */
    public ScriptProcessor getScriptProcessor()
    {
        ScriptProcessor scriptProcessor = null;

        ObjectPool objectPool = (ObjectPool) pools.get(POOL_NAME);

        try
        {
            scriptProcessor = (ScriptProcessor) objectPool.borrowObject();
        }
        catch (Exception e)
        {
            System.out.println(e);

            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.

            throw new RuntimeException(e);
        }

        return scriptProcessor;
    }

    /**
     * Returns an instance to the pool.
     * @param crud
     */
    protected void returnScriptProcessor(ScriptProcessor scriptProcessor)
    {
        ObjectPool objectPool = (ObjectPool) pools.get(POOL_NAME);

        try
        {
            objectPool.returnObject(scriptProcessor);
        }
        catch (Exception e)
        {
            System.out.println(e);

            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.

            throw new RuntimeException(e);
        }
    }

    /**
     * Builds associated ScriptProcessor instance(s) to be associated with a DB Connection.
     */
    private final void buildScriptProcessors()
    {
            ScriptProcessorPoolableObjectFactory factory = new ScriptProcessorPoolableObjectFactory();

            ObjectPool objectPool = new StackObjectPool(factory, MAX_POOL_SIZE, MIN_POOL_SIZE);

            pools.put(POOL_NAME, objectPool);
    }
}
