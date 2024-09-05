/*
 * User: sdorman
 * Date: Apr 28, 2006
 * Time: 12:22:36 PM
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement. 
 */

package edit.services.db.hibernate;

import org.hibernate.event.def.*;
import org.hibernate.event.*;
import org.hibernate.*;

public class HibernateFlushEventListener extends DefaultFlushEventListener
{
    public void onFlush(FlushEvent event) throws HibernateException
    {
        System.out.println("HibernateFlushEventListener.onFlush");
        super.onFlush(event);    //To change body of overridden methods use File | Settings | File Templates.
    }
}
