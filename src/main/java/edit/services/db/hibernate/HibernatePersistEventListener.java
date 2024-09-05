/*
 * User: sdorman
 * Date: Apr 28, 2006
 * Time: 12:26:25 PM
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

import java.util.*;

public class HibernatePersistEventListener extends DefaultPersistEventListener
{
    public void onPersist(PersistEvent event) throws HibernateException
    {
        System.out.println("HibernatePersistEventListener.onPersist");
        super.onPersist(event);    //To change body of overridden methods use File | Settings | File Templates.
    }

    public void onPersist(PersistEvent event, Map map) throws HibernateException
    {
        System.out.println("HibernatePersistEventListener.onPersist");
        super.onPersist(event, map);    //To change body of overridden methods use File | Settings | File Templates.
    }

    protected void entityIsPersistent(PersistEvent event, Map map)
    {
        System.out.println("HibernatePersistEventListener.entityIsPersistent");
        super.entityIsPersistent(event, map);    //To change body of overridden methods use File | Settings | File Templates.
    }

    protected void entityIsTransient(PersistEvent event, Map map) throws HibernateException
    {
        System.out.println("HibernatePersistEventListener.entityIsTransient");
        super.entityIsTransient(event, map);    //To change body of overridden methods use File | Settings | File Templates.
    }

    protected CascadingAction getCascadeAction()
    {
        System.out.println("HibernatePersistEventListener.getCascadeAction");
        return super.getCascadeAction();    //To change body of overridden methods use File | Settings | File Templates.
    }

    protected Boolean getAssumedUnsaved()
    {
        System.out.println("HibernatePersistEventListener.getAssumedUnsaved");
        return super.getAssumedUnsaved();    //To change body of overridden methods use File | Settings | File Templates.
    }
}
