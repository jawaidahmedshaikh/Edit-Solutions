/*
 * User: sdorman
 * Date: Apr 28, 2006
 * Time: 12:25:15 PM
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement. 
 */

package edit.services.db.hibernate;

import org.hibernate.event.def.*;
import org.hibernate.event.*;
import org.hibernate.*;
import org.hibernate.type.*;
import org.hibernate.engine.*;
import org.hibernate.persister.entity.*;

import java.util.*;

public class HibernateMergeEventListener extends DefaultMergeEventListener
{
    protected Map getMergeMap(Object o)
    {
        System.out.println("HibernateMergeEventListener.getMergeMap");
        return super.getMergeMap(o);    //To change body of overridden methods use File | Settings | File Templates.
    }

    public void onMerge(MergeEvent event) throws HibernateException
    {
        System.out.println("HibernateMergeEventListener.onMerge");
        super.onMerge(event);    //To change body of overridden methods use File | Settings | File Templates.
    }

    public void onMerge(MergeEvent event, Map map) throws HibernateException
    {
        System.out.println("HibernateMergeEventListener.onMerge");
        super.onMerge(event, map);    //To change body of overridden methods use File | Settings | File Templates.
    }

    protected void entityIsPersistent(MergeEvent event, Map map)
    {
        System.out.println("HibernateMergeEventListener.entityIsPersistent");
        super.entityIsPersistent(event, map);    //To change body of overridden methods use File | Settings | File Templates.
    }

    protected void entityIsTransient(MergeEvent event, Map map)
    {
        System.out.println("m");
        super.entityIsTransient(event, map);    //To change body of overridden methods use File | Settings | File Templates.
    }

    protected void entityIsDetached(MergeEvent event, Map map)
    {
        System.out.println("HibernateMergeEventListener.entityIsDetached");
        super.entityIsDetached(event, map);    //To change body of overridden methods use File | Settings | File Templates.
    }

    protected void copyValues(EntityPersister entityPersister, Object o, Object o1, SessionImplementor sessionImplementor, Map map)
    {
        System.out.println("HibernateMergeEventListener.copyValues");
        super.copyValues(entityPersister, o, o1, sessionImplementor, map);    //To change body of overridden methods use File | Settings | File Templates.
    }

    protected void copyValues(EntityPersister entityPersister, Object o, Object o1, SessionImplementor sessionImplementor, Map map, ForeignKeyDirection foreignKeyDirection)
    {
        System.out.println("HibernateMergeEventListener.copyValues");
        super.copyValues(entityPersister, o, o1, sessionImplementor, map, foreignKeyDirection);    //To change body of overridden methods use File | Settings | File Templates.
    }

    protected void cascadeOnMerge(EventSource eventSource, EntityPersister entityPersister, Object o, Map map)
    {
        System.out.println("HibernateMergeEventListener.cascadeOnMerge");
        super.cascadeOnMerge(eventSource, entityPersister, o, map);    //To change body of overridden methods use File | Settings | File Templates.
    }

    protected CascadingAction getCascadeAction()
    {
        System.out.println("HibernateMergeEventListener.getCascadeAction");
        return super.getCascadeAction();    //To change body of overridden methods use File | Settings | File Templates.
    }

    protected Boolean getAssumedUnsaved()
    {
        System.out.println("HibernateMergeEventListener.getAssumedUnsaved");
        return super.getAssumedUnsaved();    //To change body of overridden methods use File | Settings | File Templates.
    }

    protected void cascadeAfterSave(EventSource eventSource, EntityPersister entityPersister, Object o, Object o1) throws HibernateException
    {
        System.out.println("HibernateMergeEventListener.cascadeAfterSave");
        super.cascadeAfterSave(eventSource, entityPersister, o, o1);    //To change body of overridden methods use File | Settings | File Templates.
    }

    protected void cascadeBeforeSave(EventSource eventSource, EntityPersister entityPersister, Object o, Object o1) throws HibernateException
    {
        System.out.println("HibernateMergeEventListener.cascadeBeforeSave");
        super.cascadeBeforeSave(eventSource, entityPersister, o, o1);    //To change body of overridden methods use File | Settings | File Templates.
    }
}
