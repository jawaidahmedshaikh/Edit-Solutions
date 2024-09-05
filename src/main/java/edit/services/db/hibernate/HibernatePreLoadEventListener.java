/*
 * User: sdorman
 * Date: Apr 28, 2006
 * Time: 11:44:19 AM
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement. 
 */

package edit.services.db.hibernate;

import org.hibernate.event.*;
import org.hibernate.event.def.*;

public class HibernatePreLoadEventListener extends DefaultPreLoadEventListener
{
    public void onPreLoad(PreLoadEvent event)
    {
        System.out.println("HibernatePreLoadEventListener.onPreLoad");
        super.onPreLoad(event);    //To change body of overridden methods use File | Settings | File Templates.
    }
}
