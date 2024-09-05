/*
 * User: sdorman
 * Date: Apr 28, 2006
 * Time: 12:20:11 PM
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

public class HibernateDeleteEventListener extends DefaultDeleteEventListener
{
    public void onDelete(DeleteEvent event) throws HibernateException
    {
        System.out.println("HibernateDeleteEventListener.onDelete");
        super.onDelete(event);    //To change body of overridden methods use File | Settings | File Templates.
    }

    protected boolean invokeDeleteLifecycle(EventSource eventSource, Object o, EntityPersister entityPersister)
    {
        System.out.println("HibernateDeleteEventListener.invokeDeleteLifecycle");
        return super.invokeDeleteLifecycle(eventSource, o, entityPersister);    //To change body of overridden methods use File | Settings | File Templates.
    }
}
