package engine.sp;

import org.apache.commons.pool.PoolableObjectFactory;

/**
 * User: gfrosti
 * <p/>
 * Date: May 19, 2004
 * <p/>
 * Time: 10:08:40 AM
 * <p/>
 * <p/>
 * <p/>
 * (c) 2000-2004 Systems Engineering Group, LLC.  All Rights Reserved
 * <p/>
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * <p/>
 * subject to the license agreement.
 */

public class ScriptProcessorPoolableObjectFactory implements PoolableObjectFactory
{
    public ScriptProcessorPoolableObjectFactory()
    {
    }

    public Object makeObject() throws Exception
    {
        ScriptProcessor sp = new ScriptProcessorImpl();

        return sp;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void destroyObject(Object o) throws Exception
    {
        ScriptProcessor sp = (ScriptProcessor) o;

        sp.clear();

        o = null;
    }

    public boolean validateObject(Object o)
    {
        return true;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void activateObject(Object o) throws Exception
    {
    }

    public void passivateObject(Object o) throws Exception
    {
        ScriptProcessor sp = (ScriptProcessor) o;

        sp.clear();
    }
}