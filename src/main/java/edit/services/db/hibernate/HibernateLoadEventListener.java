/*
 * User: sdorman
 * Date: Apr 28, 2006
 * Time: 9:10:45 AM
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement. 
 */

package edit.services.db.hibernate;

import org.hibernate.event.def.*;
import org.hibernate.event.*;
import org.hibernate.*;
import org.hibernate.engine.*;
import org.hibernate.persister.entity.*;


public class HibernateLoadEventListener extends DefaultLoadEventListener
{
    private static final ThreadLocal callerType = new ThreadLocal();

    public void onLoad(LoadEvent event, LoadType loadType) throws HibernateException
    {
        System.out.println("HibernateLoadEventListener.onLoad");
        Thread thread = Thread.currentThread();
        ClassLoader classLoader = thread.getContextClassLoader();

        ThreadLocal tl = new ThreadLocal();
        tl.set("");

        super.onLoad(event, loadType);    //To change body of overridden methods use File | Settings | File Templates.
    }

    protected Object load(LoadEvent event, EntityPersister entityPersister, EntityKey entityKey, LoadType loadType) throws HibernateException
    {
        System.out.println("HibernateLoadEventListener.load");
        return super.load(event, entityPersister, entityKey, loadType);    //To change body of overridden methods use File | Settings | File Templates.
    }

    protected Object loadFromDatasource(LoadEvent event, EntityPersister entityPersister, EntityKey entityKey, LoadType loadType) throws HibernateException
    {
        System.out.println("HibernateLoadEventListener.loadFromDatasource");
        return super.loadFromDatasource(event, entityPersister, entityKey, loadType);    //To change body of overridden methods use File | Settings | File Templates.
    }

    protected Object loadFromSessionCache(LoadEvent event, EntityKey entityKey, LoadType loadType) throws HibernateException
    {
        System.out.println("HibernateLoadEventListener.loadFromSessionCache");
        return super.loadFromSessionCache(event, entityKey, loadType);    //To change body of overridden methods use File | Settings | File Templates.
    }

    protected Object loadFromSecondLevelCache(LoadEvent event, EntityPersister entityPersister, LoadType loadType) throws HibernateException
    {
        System.out.println("HibernateLoadEventListener.loadFromSecondLevelCache");
        return super.loadFromSecondLevelCache(event, entityPersister, loadType);    //To change body of overridden methods use File | Settings | File Templates.
    }

    protected Object proxyOrLoad(LoadEvent event, EntityPersister entityPersister, EntityKey entityKey, LoadType loadType) throws HibernateException
    {
        System.out.println("HibernateLoadEventListener.proxyOrLoad");
        return super.proxyOrLoad(event, entityPersister, entityKey, loadType);    //To change body of overridden methods use File | Settings | File Templates.
    }

    protected Object lockAndLoad(LoadEvent event, EntityPersister entityPersister, EntityKey entityKey, LoadType loadType, SessionImplementor sessionImplementor) throws HibernateException
    {
        System.out.println("HibernateLoadEventListener.lockAndLoad");
        return super.lockAndLoad(event, entityPersister, entityKey, loadType, sessionImplementor);    //To change body of overridden methods use File | Settings | File Templates.
    }

    protected Object doLoad(LoadEvent event, EntityPersister entityPersister, EntityKey entityKey, LoadType loadType) throws HibernateException
    {
        System.out.println("HibernateLoadEventListener.doLoad");
        return super.doLoad(event, entityPersister, entityKey, loadType);    //To change body of overridden methods use File | Settings | File Templates.
    }
}
