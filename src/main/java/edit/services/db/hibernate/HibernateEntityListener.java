/*
 * User: gfrosti
 * Date: Feb 23, 2006
 * Time: 1:58:45 PM
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package edit.services.db.hibernate;

import java.util.Set;

import org.hibernate.event.*;
import org.hibernate.HibernateException;


public class HibernateEntityListener implements SaveOrUpdateEventListener, DeleteEventListener
{
    public void onSaveOrUpdate(SaveOrUpdateEvent saveOrUpdateEvent) throws HibernateException
    {
//        HibernateEntity hibernateEntity = (HibernateEntity) saveOrUpdateEvent.getObject();
//
//        if (hibernateEntity.isNewlyInstantiated())
//        {
//            //  it's a new instantiation, don't call the entity's callback, just reset the flag for future saves
//            hibernateEntity.setNewlyInstantiated(false);
//        }
//        else
//        {
//            //  it's not a new instantiation, call the entity's callback
//            hibernateEntity.onSave();
//        }
    }

    public void onDelete(DeleteEvent deleteEvent) throws HibernateException
    {
//        System.out.println("HibernateEntityListener.onDelete");
    }

  public void onDelete(DeleteEvent deleteEvent, Set set) throws HibernateException
  {
  }
}
