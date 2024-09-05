/*
 * User: sdorman
 * Date: Apr 28, 2006
 * Time: 12:27:07 PM
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement. 
 */

package edit.services.db.hibernate;

import org.hibernate.event.def.*;
import org.hibernate.event.*;
import org.hibernate.*;

import java.util.*;

public class HibernateRefreshEventListener extends DefaultRefreshEventListener
{
    public void onRefresh(RefreshEvent event) throws HibernateException
    {
        System.out.println("HibernateRefreshEventListener.onRefresh");
        super.onRefresh(event);    //To change body of overridden methods use File | Settings | File Templates.
    }

    public void onRefresh(RefreshEvent event, Map map) throws HibernateException
    {
        System.out.println("HibernateRefreshEventListener.onRefresh using map");
        super.onRefresh(event, map);    //To change body of overridden methods use File | Settings | File Templates.
    }
}
