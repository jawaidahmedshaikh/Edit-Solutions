/*
 * User: sdorman
 * Date: Apr 28, 2006
 * Time: 12:00:58 PM
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement. 
 */

package edit.services.db.hibernate;

import org.hibernate.event.def.*;
import org.hibernate.event.*;

public class HibernatePostLoadEventListener extends DefaultPostLoadEventListener
{
    public void onPostLoad(PostLoadEvent event)
    {
        System.out.println("HibernatePostLoadEventListener.onPostLoad");
        super.onPostLoad(event);    //To change body of overridden methods use File | Settings | File Templates.
    }
}
