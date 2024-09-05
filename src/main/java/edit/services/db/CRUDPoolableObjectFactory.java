package edit.services.db;

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

public class CRUDPoolableObjectFactory implements PoolableObjectFactory
{
    String dbPoolName = null;

    public CRUDPoolableObjectFactory(String dbPoolName)
    {
        this.dbPoolName = dbPoolName;
    }

    public Object makeObject() throws Exception
    {
        CRUD crud = new CRUD();

        return crud;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void destroyObject(Object o) throws Exception
    {
        CRUD crud = (CRUD) o;

        crud.resetState();

        o = null;
    }

    public boolean validateObject(Object o)
    {
        return true;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void activateObject(Object o) throws Exception
    {
        CRUD crud = (CRUD) o;

        crud.initState(dbPoolName);
    }

    public void passivateObject(Object o) throws Exception
    {
        CRUD crud = (CRUD) o;

        crud.resetState();
    }
}
