package edit.services.db.hibernate;

import contract.Segment;

import edit.common.EDITDate;

import edit.services.config.ServicesConfig;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.hibernate.EntityMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.engine.SessionFactoryImplementor;
import org.hibernate.engine.SessionImplementor;
import org.hibernate.event.PreUpdateEvent;
import org.hibernate.mapping.PersistentClass;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.tuple.StandardProperty;
import org.hibernate.tuple.entity.EntityMetamodel;
import org.hibernate.type.CollectionType;
import org.hibernate.type.Type;
import org.hibernate.persister.entity.EntityPersister;

/**
 * Detects the field-level differences between the entity currently
 * in session and its corresponding entity on the database.
 */
public class HibernateEntityDifferenceEngine
{
    /**
     * The Hibernate entity has been previously saved to the DB
     * but was not loaded using the current Hibernate session. For
     * example, the entity could have been built from the values
     * stored in client state (like JSP pages). Hibernate will not
     * be able to detect before/after changes to the entity.
     */
    public static final int ENTITY_STATE_DETACHED = 0;

    /**
     * The Hibernate entity has been previously saved and has been loaded
     * using the current Hibernate session. Hibernate will be able to detect
     * before/after changes to the entity.
     */
    public static final int ENTITY_STATE_PERSISTENT = 1;

    /**
     * The entity for which to generate field level differences.
     */
    private HibernateEntity inSessionHibernateEntity;

    /**
     * Represents a HibernateEntity in session and the set 
     * of field level before and after differences between the
     * in-session HibernateEntity and its database version.
     */
    public HibernateEntityDifferenceEngine(HibernateEntity inSessionHibernateEntity)
    {
        this.inSessionHibernateEntity = inSessionHibernateEntity;
    }

    /**
     * Compares the in-Session HibernateEntity with its DB equivalent. It generates
     * a HibernateFieldDifference containing and entry for every field-level difference found.
     * 
     * The Hibernate EventListener framework permits a performance advantage in that
     * same-session persistent entities (loaded and saved within the same session) already have
     * their differences mapped by Hiberante. This allows us to to build a difference comparison without
     * having to load the entity from the DB. If the entity with merely detached and then saved, then
     * Hibernate is unaware of what its previous state was. Therefore, it is necessary to strike the DB
     * to do a comparison check.
     * @return the set of field-level differences as a HiberanteEntityDifference or null if no differences were found
     */
    public HibernateEntityDifference generateHibernateEntityDifference(Object[] newState, Object[] oldState, String[] fieldNames, Type[] fieldTypes)
    {
        HibernateEntityDifference hibernateEntityDifference = null;
        
        HibernateFieldDifference[] hibernateFieldDifferences = null;

        int entityState = getEntityState(oldState);
        
        if (entityState == ENTITY_STATE_DETACHED)
        {
            hibernateFieldDifferences = findDetachedDifferences(newState, fieldNames, fieldTypes);
        }
        else if (entityState == ENTITY_STATE_PERSISTENT)
        {
            hibernateFieldDifferences = findPersistentDifferences(newState, oldState, fieldNames, fieldTypes);
        }

        if (hibernateFieldDifferences != null)
        {
            hibernateEntityDifference = new HibernateEntityDifference(getHibernateEntityName(), getHibernateEntityPK(), fieldNames, newState, hibernateFieldDifferences);
        }
        
        return hibernateEntityDifference;
    }

    /**
     * A convenience method to retrieve Hibernate's ClassMetadata for the targeted
     * HibernateEntity.
     * @return
     */
    public ClassMetadata getClassMetadata()
    {
        ClassMetadata classMetadata = null;

        Class hibernateEntityClass = getInSessionHibernateEntity().getClass();

        String database = getInSessionHibernateEntity().getDatabase();

        classMetadata = SessionHelper.getClassMetadata(hibernateEntityClass, database);

        return classMetadata;
    }

    /**
     * A convenience method to get the name of the targeted entity.
     * @return
     */
    public String getHibernateEntityName()
    {
        return getClassMetadata().getEntityName();
    }

    /**
     * A convenience method
     * @return
     */
    public Long getHibernateEntityPK()
    {
        return SessionHelper.getPKValue(getInSessionHibernateEntity());
    }

    /**
     * Loads the DB version of the inSessionHibernateEntity.
     * @return the DB version of inSessionHibernateEntity
     */
    private HibernateEntity getInDBHibernateEntity()
    {
        HibernateEntity inDBHibernateEntity = null;

        Session session = null;

        try
        {
            Class hibernateEntityClass = getInSessionHibernateEntity().getClass();

            Long hibernateEntityPK = SessionHelper.getPKValue(getInSessionHibernateEntity());

            session = SessionHelper.getSeparateSession(getInSessionHibernateEntity().getDatabase());

            inDBHibernateEntity = (HibernateEntity) session.get(hibernateEntityClass, hibernateEntityPK);
        }
        finally
        {
            if (session != null)
            {
                session.close();
            }
        }

        return inDBHibernateEntity;
    }

    /**
     * @see #inSessionHibernateEntity
     * @param inSessionHibernateEntity
     */
    public void setInSessionHibernateEntity(HibernateEntity inSessionHibernateEntity)
    {
        this.inSessionHibernateEntity = inSessionHibernateEntity;
    }

    /**
     * @see #inSessionHibernateEntity
     */
    public HibernateEntity getInSessionHibernateEntity()
    {
        return inSessionHibernateEntity;
    }

    /**
     * A convenenience method to determine if the specified PreUpdateEvent
     * was generated via the updating of a Detached entity or a Persistent entity.
     * 
     * If the entity was detached, then there is no "old state" since Hibernate
     * had no means of tracking its state changes. This fact is used in 
     * determining the entity state.
     * @param oldState the state as received from the HibernateEntityDifferenceInterceptor
     * @return
     */
    private int getEntityState(Object oldState)
    {
        int entityState = ENTITY_STATE_PERSISTENT;

        if (oldState == null)
        {
            entityState = ENTITY_STATE_DETACHED;
        }

        return entityState;
    }

    /**
     * Finds the field-level differences between the entity as originally loaded
     * from the DB in the current session and the entity just before it is
     * to updated in the DB.
     * 
     * Attention was made to instantiate only necessary objects.
     * 
     * @param newState the in-session state of the Hibernate entity
     * @param oldState the DB state of the modified Hibernate entity
     * @param fieldNames the fieldNames (simple and not simple) of the targeted Hibernate entity
     * @param fieldTypes the associates types of the fieldNames
     */
    private HibernateFieldDifference[] findPersistentDifferences(Object[] newState, Object[] oldState, String[] fieldNames, Type[] fieldTypes)
    {
        List<HibernateFieldDifference> hibernateFieldDifferences = null;

        for (int i = 0; i < fieldNames.length; i++)
        {
            if (SessionHelper.isSimpleField(fieldNames[i], fieldTypes[i]))
            {
                Object oldValue = oldState[i];

                Object newValue = newState[i];
                
                if (!valuesAreEqual(oldValue, newValue))
                {
                    if (hibernateFieldDifferences == null)
                    {
                        hibernateFieldDifferences = new ArrayList<HibernateFieldDifference>();
                    }
                    
                    HibernateFieldDifference hibernateFieldDifference = new HibernateFieldDifference(fieldNames[i], oldValue, newValue);
                    
                    hibernateFieldDifferences.add(hibernateFieldDifference);
                }
            }
        }
        
        if (hibernateFieldDifferences != null)
        {
            return hibernateFieldDifferences.toArray(new HibernateFieldDifference[hibernateFieldDifferences.size()]);
        }
        else
        {
            return null;
        }
    }

    /**
     * Finds the differences between the in-session HibernateEntity and its state as represented on the DB.
     * This is achieved by performing a DB lookup in a separate Hibernate session for the targeted entity.
     * 
     * Attention was given to only instantiate necessary objects to minimize stress on memory.
     * 
     * @param newState the in-session state of the Hibernate entity
     * @param fieldNames the names of all fields on the Hibernate entity (simple or not)
     * @param fieldTypes the types of the corresponding fieldNames
     */
    private HibernateFieldDifference[] findDetachedDifferences(Object[] newState, String[] fieldNames, Type[] fieldTypes)
    {
        List<HibernateFieldDifference> hibernateFieldDifferences = null;
        
        HibernateEntity inDBHibernateEntity = getInDBHibernateEntity();

        for (int i = 0; i < fieldNames.length; i++)
        {
            if (SessionHelper.isSimpleField(fieldNames[i], fieldTypes[i]))
            {
                Object oldValue = getFieldValue(fieldNames[i], inDBHibernateEntity);

                Object newValue = newState[i];

                if (!valuesAreEqual(oldValue, newValue))
                {
                    if (hibernateFieldDifferences == null)
                    {
                        hibernateFieldDifferences = new ArrayList<HibernateFieldDifference>();
                    }
                    
                    HibernateFieldDifference hibernateFieldDifference = new HibernateFieldDifference(fieldNames[i], oldValue, newValue);
                    
                    hibernateFieldDifferences.add(hibernateFieldDifference);
                }
            }
        }
        
        if (hibernateFieldDifferences != null)
        {
            return hibernateFieldDifferences.toArray(new HibernateFieldDifference[hibernateFieldDifferences.size()]);
        }
        else
        {
            return null;
        }
    }

    /**
     * Finds the value associated with the specified hibernateEntity/fieldName.
     * @param fieldName
     * @param hibernateEntity
     * @return
     */
    private Object getFieldValue(String fieldName, HibernateEntity hibernateEntity)
    {
        Object fieldValue = getClassMetadata().getPropertyValue(hibernateEntity, fieldName, EntityMode.POJO);

        return fieldValue;
    }

    /**
     * True if the specified values are equal
     * by value.
     * @param oldValue the original value (most likely that which is on the DB)
     * @return newValue the new value (most likely that which is in session)
     */
    public boolean valuesAreEqual(Object oldValue, Object newValue)
    {
        boolean valuesAreEqual = false;

        if ((oldValue == null) && (newValue == null))
        {
            valuesAreEqual = true;
        }
        else if ((oldValue != null) && (newValue != null))
        {
            if (oldValue instanceof String)
            {
                if (((String)oldValue).equalsIgnoreCase((String) newValue)) // We allow for case difference
                {
                    valuesAreEqual = true;
                }
            }
            else if (oldValue.equals(newValue))
            {
                valuesAreEqual = true;
            }
        }
        
        return valuesAreEqual;
    }
}
