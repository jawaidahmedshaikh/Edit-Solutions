/*
 * User: sdorman
 * Date: Apr 28, 2006
 * Time: 12:28:34 PM
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement. 
 */

package edit.services.db.hibernate;

import org.hibernate.event.def.*;
import org.hibernate.event.*;
import org.hibernate.*;
import org.hibernate.persister.entity.*;
import org.hibernate.engine.*;

import java.io.*;

public class HibernateSaveOrUpdateEventListener extends DefaultSaveOrUpdateEventListener
{
    public void onSaveOrUpdate(SaveOrUpdateEvent event) throws HibernateException
    {
        System.out.println("HibernateSaveOrUpdateEventListener.onSaveOrUpdate");
//        HibernateEntity hibernateEntity = (HibernateEntity) event.getObject();
//
//        if (hibernateEntity.isNewlyInstantiated())
//        {
//            //  it's a new instantiation, don't call the entity's callback, just reset the flag for future saves
//            hibernateEntity.setNewlyInstantiated(false);
//        }
//        else
//        {
//            //  it's not a new instantiation, call the entity's callback and save the object
//            hibernateEntity.onSave();
//
//            super.onSaveOrUpdate(event);
//        }
    }

    protected boolean reassociateIfUninitializedProxy(Object o, SessionImplementor sessionImplementor)
    {
        System.out.println("HibernateSaveOrUpdateEventListener.reassociateIfUninitializedProxy");
        return super.reassociateIfUninitializedProxy(o, sessionImplementor);    //To change body of overridden methods use File | Settings | File Templates.
    }

    protected Serializable performSaveOrUpdate(SaveOrUpdateEvent event)
    {
        System.out.println("HibernateSaveOrUpdateEventListener.performSaveOrUpdate");
        return super.performSaveOrUpdate(event);    //To change body of overridden methods use File | Settings | File Templates.
    }

    protected Serializable entityIsPersistent(SaveOrUpdateEvent event) throws HibernateException
    {
        System.out.println("HibernateSaveOrUpdateEventListener.entityIsPersistent");
        return super.entityIsPersistent(event);    //To change body of overridden methods use File | Settings | File Templates.
    }

    protected Serializable entityIsTransient(SaveOrUpdateEvent event) throws HibernateException
    {
        System.out.println("HibernateSaveOrUpdateEventListener.entityIsTransient");
        return super.entityIsTransient(event);    //To change body of overridden methods use File | Settings | File Templates.
    }

    protected Serializable saveWithGeneratedOrRequestedId(SaveOrUpdateEvent event)
    {
        System.out.println("HibernateSaveOrUpdateEventListener.saveWithGeneratedOrRequestedId");
        return super.saveWithGeneratedOrRequestedId(event);    //To change body of overridden methods use File | Settings | File Templates.
    }

    protected void entityIsDetached(SaveOrUpdateEvent event) throws HibernateException
    {
        System.out.println("HibernateSaveOrUpdateEventListener.entityIsDetached");
        super.entityIsDetached(event);    //To change body of overridden methods use File | Settings | File Templates.
    }

    protected Serializable getUpdateId(Object o, EntityPersister entityPersister, Serializable serializable, EntityMode entityMode) throws HibernateException
    {
        System.out.println("HibernateSaveOrUpdateEventListener.getUpdateId");
        return super.getUpdateId(o, entityPersister, serializable, entityMode);    //To change body of overridden methods use File | Settings | File Templates.
    }

    protected void performUpdate(SaveOrUpdateEvent event, Object o, EntityPersister entityPersister) throws HibernateException
    {
        System.out.println("HibernateSaveOrUpdateEventListener.performUpdate");
        super.performUpdate(event, o, entityPersister);    //To change body of overridden methods use File | Settings | File Templates.
    }

    protected boolean invokeUpdateLifecycle(Object o, EntityPersister entityPersister, EventSource eventSource)
    {
        System.out.println("HibernateSaveOrUpdateEventListener.invokeUpdateLifecycle");
        return super.invokeUpdateLifecycle(o, entityPersister, eventSource);    //To change body of overridden methods use File | Settings | File Templates.
    }

    protected CascadingAction getCascadeAction()
    {
        System.out.println("HibernateSaveOrUpdateEventListener.getCascadeAction");
        return super.getCascadeAction();    //To change body of overridden methods use File | Settings | File Templates.
    }
}
