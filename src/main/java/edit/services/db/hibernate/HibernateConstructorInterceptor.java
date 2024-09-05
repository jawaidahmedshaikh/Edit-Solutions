/*
 * User: sdorman
 * Date: May 2, 2006
 * Time: 10:25:48 AM
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement. 
 */

package edit.services.db.hibernate;

import org.hibernate.*;
import org.hibernate.type.*;

import java.io.*;
import java.util.*;
import java.lang.reflect.*;

public class HibernateConstructorInterceptor extends EmptyInterceptor
{
      private static final Byte fakeParam = new Byte((byte)0);

      private static final Class[] HIBERNATE_SIGNATURE = new Class[] {Serializable.class, byte.class};

      private Map constructors = new HashMap(30);

      public Object instantiate( String entityName, EntityMode entityMode, Serializable id )
      {
         if ( entityMode != EntityMode.POJO ) return null; //Don't know how to deal with other entity types

         Object cachedValue = constructors.get( entityName );

         if ( cachedValue == fakeParam ) return null; //We've already checked, and there's no Hibernate constructor

         try
         {
            Constructor constructor;
            if ( cachedValue != null )
            {
               constructor = (Constructor) cachedValue; //Found the constructor in the cache
            }
            else
            {
               Class entityClass = Class.forName( entityName ); //Not sure whether this is the safest way to get the class, there might be a better alternative.

               try
               {
                  constructor = entityClass.getConstructor( HIBERNATE_SIGNATURE );
                  constructors.put( entityName, constructor ); //Found it! Store it in the cache for next time.
               }
               catch( NoSuchMethodException e )
               {
                  constructors.put( entityName, fakeParam ); //No Hibernate constructor. Remember for next time to save the time to check.
                  return null;
               }
            }

            return constructor.newInstance( new Object[] {id, fakeParam} ); //Call Hibernate constructor with id.
         }
         catch( Exception e )
         {
           System.out.println(e);

            e.printStackTrace();
            return null;
         }
      }

//    public void onDelete(Object o, Serializable serializable, Object[] objects, String[] strings, Type[] types)
//    {
//        super.onDelete(o, serializable, objects, strings, types);    //To change body of overridden methods use File | Settings | File Templates.
//    }
//
//    public boolean onFlushDirty(Object o, Serializable serializable, Object[] objects, Object[] objects1, String[] strings, Type[] types)
//    {
//        return super.onFlushDirty(o, serializable, objects, objects1, strings, types);    //To change body of overridden methods use File | Settings | File Templates.
//    }
//
//    public boolean onLoad(Object o, Serializable serializable, Object[] objects, String[] strings, Type[] types)
//    {
//        return super.onLoad(o, serializable, objects, strings, types);    //To change body of overridden methods use File | Settings | File Templates.
//    }
//
//    public boolean onSave(Object o, Serializable serializable, Object[] objects, String[] strings, Type[] types)
//    {
//        return super.onSave(o, serializable, objects, strings, types);    //To change body of overridden methods use File | Settings | File Templates.
//    }
//
//    public void postFlush(Iterator iterator)
//    {
//        super.postFlush(iterator);    //To change body of overridden methods use File | Settings | File Templates.
//    }
//
//    public void preFlush(Iterator iterator)
//    {
//        super.preFlush(iterator);    //To change body of overridden methods use File | Settings | File Templates.
//    }
//
//    public Boolean isTransient(Object o)
//    {
//        return super.isTransient(o);    //To change body of overridden methods use File | Settings | File Templates.
//    }
//
//    public int[] findDirty(Object o, Serializable serializable, Object[] objects, Object[] objects1, String[] strings, Type[] types)
//    {
//        return super.findDirty(o, serializable, objects, objects1, strings, types);    //To change body of overridden methods use File | Settings | File Templates.
//    }
//
//    public String getEntityName(Object o)
//    {
//        return super.getEntityName(o);    //To change body of overridden methods use File | Settings | File Templates.
//    }
//
//    public Object getEntity(String s, Serializable serializable)
//    {
//        return super.getEntity(s, serializable);    //To change body of overridden methods use File | Settings | File Templates.
//    }
//
//    public void afterTransactionBegin(Transaction transaction)
//    {
//        super.afterTransactionBegin(transaction);    //To change body of overridden methods use File | Settings | File Templates.
//    }
//
//    public void afterTransactionCompletion(Transaction transaction)
//    {
//        super.afterTransactionCompletion(transaction);    //To change body of overridden methods use File | Settings | File Templates.
//    }
//
//    public void beforeTransactionCompletion(Transaction transaction)
//    {
//        super.beforeTransactionCompletion(transaction);    //To change body of overridden methods use File | Settings | File Templates.
//    }
//
//    public String onPrepareStatement(String s)
//    {
//        return super.onPrepareStatement(s);    //To change body of overridden methods use File | Settings | File Templates.
//    }
//
//    public void onCollectionRemove(Object o, Serializable serializable) throws CallbackException
//    {
//        super.onCollectionRemove(o, serializable);    //To change body of overridden methods use File | Settings | File Templates.
//    }
//
//    public void onCollectionRecreate(Object o, Serializable serializable) throws CallbackException
//    {
//        super.onCollectionRecreate(o, serializable);    //To change body of overridden methods use File | Settings | File Templates.
//    }
//
//    public void onCollectionUpdate(Object o, Serializable serializable) throws CallbackException
//    {
//        super.onCollectionUpdate(o, serializable);    //To change body of overridden methods use File | Settings | File Templates.
//    }
   }
