/*
 * User: gfrosti
 * Date: Jun 27, 2005
 * Time: 2:25:35 PM
 *
 * (c) 2000-2009 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package edit.services.db.hibernate;

import org.dom4j.*;

import java.io.Serializable;
import java.lang.reflect.*;

import edit.common.exceptions.*;

public abstract class HibernateEntity implements Serializable
{
//    boolean newlyInstantiated = false;

    /**
     * Hibernate entities often exist in a detached state.
     * In the detached state, they can be "marked" for deletion,
     * but can't truly be deleted until re-attached to
     * a Hibernate Session. This field, when flagged true, does
     * not automatically delete the entity. The entity will
     * likely by manually inspected, and manually deleted.
     */
    private boolean deleted;

    public HibernateEntity()
    {
    }

//    public boolean isNewlyInstantiated()
//    {
//        return newlyInstantiated;
//    }
//
//    public void setNewlyInstantiated(boolean newlyInstantiated)
//    {
//        this.newlyInstantiated = newlyInstantiated;
//    }
    /**
     * Ideally, this is a generic implementation - simply save the entity. However, there are times when <b>minimal</b>
     * business logic may reside before persisting. An example would be to set the MaintDateTime. It would be silly to
     * have to "remember" to set this every time an entity was saved.
     */
    public void hSave()
    {
    }

    /**
     * Ideally, this is a generic implementation - simply delete the entity. However, there are times when <b>minimal</b>
     * business logic may reside before deleting.  This also avoids duplicating logic in that should a Child have two Parents,
     * it would be necessary to remove the Child from each Parent's collection. We don't want to duplicate this
     * all over the system.
     */
    public void hDelete() throws EDITDeleteException
    {
    }

    /**
     * Called by SessionHelper immediately after:
     * 1) Calling the default constructor and
     * 2) Saving the HibernateEntity (for the very first time).
     * It acts an extension to the default constructor so that the developer can perform operations to the entity as a
     * new participant to the Hibernate Session. It is not required to add functionality to this method unless needed.
     */
    public void onCreate()
    {
    }

    /**
     * Called just before a HibernateEntity is saved to the DB.
     */
    public void onSave() throws EDITSaveException
    {
    }

    /**
     * 
     */
    public void onDelete() throws EDITDeleteException
    {
    }

    /**
     * Uses reflection to get the database of this object
     *
     * @return  string containing the hibernate database name for this object
     */
    public String getDatabase()
    {
        Class c = this.getClass();

        String database = null;

        try
        {
            Field f = c.getField("DATABASE");

            database = (String) f.get(this);
        }
        catch (Exception e)
        {
            //  Don't do anything, just send to console
            e.printStackTrace();
        }

        return database;
    }

    /**
     * Returns the DOM4J Element equivalent of this object.
     *
     * @param mapNullFields                 if true, will create empty tags for null fields
     * @param convertDates                  if true, will convert date formats from the EDITDate standard format to
     *                                      the current front end format (mm/dd/yyyy)
     *
     * @return  Element containing values for this objects fields.
     */
    public Element getAsElement(boolean mapNullFields, boolean convertDates)
    {
        return SessionHelper.mapToElement(this, this.getDatabase(), mapNullFields, convertDates);
    }

    /**
     * Convenience method that returns the DOM4J Element equivalent of this object without converting dates and without
     * creating empty tags for null fields
     *
     * @return  Element containing values for this objects fields.
     */
    public Element getAsElement()
    {
        return SessionHelper.mapToElement(this, this.getDatabase(), false, false);
    }

    /**
     * Implmentors map the object state to its
     * corresponding Element state.
     *
     * @param version it is possible that implementors will want to have multiple
     * 			ways to unmarshal an entity - the version is a user-defined "flag"
     * 			that allows the implementor to vary the process as needed.
     */
    public Element marshal(String version)
    {
        return null;
    }

    /**
     * @see #deleted
     * @return
     */
    public boolean getDeleted()
    {
        return this.deleted;
    }

    /**
     * @see #deleted
     * @param deleted
     */
    public void setDeleted(boolean deleted)
    {
        this.deleted = deleted;
    }

    /**
     * If this entity has been flagged as true, and this
     * entity exists (as persisted), then it should be
     * deleted.
     * @return true if this entity should be deleted from persistence
     */
    public boolean shouldDelete()
    {
        boolean shouldDelete = false;

        if (getDeleted() == true)
        {
            if (SessionHelper.getPKValue(this) != null)
            {
                shouldDelete = true;
            }
        }

        return shouldDelete;
    }
}
