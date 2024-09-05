/*
 * User: sdorman
 * Date: Apr 28, 2006
 * Time: 12:21:57 PM
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

public class HibernateEvictEventListener extends DefaultEvictEventListener
{
    public void onEvict(EvictEvent event) throws HibernateException
    {
        System.out.println("HibernateEvictEventListener.onEvict");
        super.onEvict(event);    //To change body of overridden methods use File | Settings | File Templates.
    }

    protected void doEvict(Object o, EntityKey entityKey, EntityPersister entityPersister, EventSource eventSource) throws HibernateException
    {
        System.out.println("HibernateEvictEventListener.doEvict");
        super.doEvict(o, entityKey, entityPersister, eventSource);    //To change body of overridden methods use File | Settings | File Templates.
    }
}
