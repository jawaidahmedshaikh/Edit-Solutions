/*
 * User: sdorman
 * Date: Apr 28, 2006
 * Time: 12:23:23 PM
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement. 
 */

package edit.services.db.hibernate;

import org.hibernate.event.def.*;
import org.hibernate.event.*;
import org.hibernate.persister.entity.*;
import org.hibernate.*;
import org.hibernate.engine.*;

import java.io.*;

public class HibernateFlushEntityEventListener extends DefaultFlushEntityEventListener
{
    public void checkId(Object o, EntityPersister entityPersister, Serializable serializable, EntityMode entityMode) throws HibernateException
    {
        System.out.println("HibernateFlushEntityEventListener.checkId");
        super.checkId(o, entityPersister, serializable, entityMode);    //To change body of overridden methods use File | Settings | File Templates.
    }

    public void onFlushEntity(FlushEntityEvent event) throws HibernateException
    {
        System.out.println("HibernateFlushEntityEventListener.onFlushEntity");
        super.onFlushEntity(event);    //To change body of overridden methods use File | Settings | File Templates.
    }

    protected void validate(Object o, EntityPersister entityPersister, Status status, EntityMode entityMode)
    {
        System.out.println("HibernateFlushEntityEventListener.validate");
        super.validate(o, entityPersister, status, entityMode);    //To change body of overridden methods use File | Settings | File Templates.
    }

    protected boolean handleInterception(FlushEntityEvent event)
    {
        System.out.println("");
        return super.handleInterception(event);    //To change body of overridden methods use File | Settings | File Templates.
    }

    protected boolean invokeInterceptor(SessionImplementor sessionImplementor, Object o, EntityEntry entityEntry, Object[] objects, EntityPersister entityPersister)
    {
        System.out.println("HibernateFlushEntityEventListener.invokeInterceptor");
        return super.invokeInterceptor(sessionImplementor, o, entityEntry, objects, entityPersister);    //To change body of overridden methods use File | Settings | File Templates.
    }

    protected void dirtyCheck(FlushEntityEvent event) throws HibernateException
    {
        System.out.println("HibernateFlushEntityEventListener.dirtyCheck");
        super.dirtyCheck(event);    //To change body of overridden methods use File | Settings | File Templates.
    }
}
