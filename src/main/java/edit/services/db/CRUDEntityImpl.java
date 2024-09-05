package edit.services.db;

import edit.common.vo.*;
import edit.common.vo.user.*;

import java.lang.reflect.*;

/**
 * Created by IntelliJ IDEA.
 * User: gfrosti
 * Date: Nov 10, 2003
 * Time: 3:04:51 PM
 * To change this template use Options | File Templates.
 */
public class CRUDEntityImpl implements CRUDEntityI
{
    private CRUD crud = null;
    private boolean externalCRUD = false;

    public void setCRUD(CRUD crud) throws Exception
    {
        this.crud = crud;
        externalCRUD = true;
    }
    /**
     * Persists the state of a CRUDEntity.
     * @param crudEntity
     * @param poolName
     * @param trackChanges
     * @return The set of changes if so requested.
     */
//    public ChangeVO[] save(CRUDEntity crudEntity, String poolName, boolean trackChanges)
    public void save(CRUDEntity crudEntity, String poolName, boolean trackChanges)
    {
//        if (crudEntity.getVO() == null) // You can't save a null VO
//        {
//            return null;
//        }

//        else if (crudEntity.getVO().getVoShouldBeDeleted())
        if (crudEntity.getVO().getVoShouldBeDeleted())
        {
            this.delete(crudEntity, poolName);

//            return null;
        }

        else
        {
//            CRUD crud = null;
//            ChangeVO[] changeVOs = null;

            try
            {
                if (crud == null)
                {
                    crud = CRUDFactory.getSingleton().getCRUD(poolName);
                }

//                if (trackChanges)
//                    crud.setShouldTrackChangeHistory(true);

                if (crudEntity.getVO() != null)
                {
                    crud.createOrUpdateVOInDB(crudEntity.getVO());
                }

                //get all the ChangeVOs from crud
//                if (trackChanges)
//                {
//                    changeVOs = crud.getChangeVOs();
//                    //Reset the tracking status
//                    crud.setShouldTrackChangeHistory(false);
//                }

//                return changeVOs;
            }
            catch (Exception e)
            {
                System.out.println(e);

                e.printStackTrace();

                throw new RuntimeException(e);
            }
            finally
            {
                if (externalCRUD)
                {
                    externalCRUD = false;
                }
                else
                {
                    if (crud != null) crud.close();

                    crud = null;
                }
            }
        }
    }

    /**
     * Loads a CRUDEntity from persistance.
     * @param crudEntity
     * @param pk
     * @param poolName
     */
    public void load(CRUDEntity crudEntity, long pk, String poolName)
    {
        if (pk != 0)
        {
            CRUD crud = null;

            VOObject voObject = null;

            try
            {
                Class voClass = crudEntity.getVO().getClass();

                crud = CRUDFactory.getSingleton().getCRUD(poolName);

                voObject = (VOObject) crud.retrieveVOFromDB(voClass, pk);

                if (voObject == null)
                {
            //                throw new RuntimeException("Invalid PK For CRUDEntity [" + crudEntity.getClass().getName() + "]");
                }

                crudEntity.setVO(voObject);
            }
            finally
            {
                if (crud != null) crud.close();

                crud = null;
            }
        }
    }

    /**
     * Deletes a CRUDEntity from persistance.
     * @param crudEntity
     * @param poolName
     */
    public void delete(CRUDEntity crudEntity, String poolName)
    {
        CRUD crud = null;

        try
        {
            Class voClass = crudEntity.getVO().getClass();

            crud = CRUDFactory.getSingleton().getCRUD(poolName);

            crud.deleteVOFromDB(voClass, crudEntity.getPK());

            crudEntity.setVO(null);
        }
        finally
        {
            if (crud != null) crud.close();

            crud = null;
        }
    }

    /**
     * Performs a deep clone of a CRUDEntity. This is not recursive.
     * @param crudEntity
     * @return
     */
    public CRUDEntity cloneCRUDEntity(CRUDEntity crudEntity)
    {
        CRUDEntity newCRUDEntity = null;

        VOObject voObject = crudEntity.getVO();

        VOClass voClass = VOClass.getVOClassMetaData(voObject.getClass());

        VOMethod pkSetter = voClass.getPKSetter();

        try
        {
            newCRUDEntity = (CRUDEntity) Class.forName(crudEntity.getClass().getName()).newInstance();

            VOObject clonedVOObject = (VOObject) voObject.cloneVO();

            // Zero-out the pk.
            pkSetter.getMethod().invoke(clonedVOObject, new Object[]{new Long(0)});

            newCRUDEntity.setVO(clonedVOObject);
        }
        catch (Exception e)
        {
            System.out.println(e);

            e.printStackTrace();  //To change body of catch statement use Options | File Templates.

            throw new RuntimeException(e);
        }

        return newCRUDEntity;
    }

    /**
     * Determines if a CRUDEntity has yet been persisted.
     * @param crudEntity
     * @return
     */
    public boolean isNew(CRUDEntity crudEntity)
    {
        return (crudEntity.getPK() == 0);
    }

    /**
     * Maps a set of VOs to their CRUDEntity equivalent.
     * @param vos         the set of VOs to map
     * @param entityClass the data-type to map to
     * @return the mapped VOs, or null if the supplied vos are null
     */
    public static CRUDEntity[] mapVOToEntity(Object[] vos, Class entityClass)
    {
        CRUDEntity[] entities = null;

        if (vos != null)
        {
            entities = (CRUDEntity[]) Array.newInstance(entityClass, vos.length);

            try
            {
                for (int i = 0; i < vos.length; i++)
                {
                    CRUDEntity currentEntity = (CRUDEntity) entityClass.newInstance();

                    currentEntity.setVO((VOObject) vos[i]);

                    Array.set(entities, i, currentEntity);
                }
            }
            catch (Exception e)
            {
                throw new RuntimeException(e);
            }
        }

        return entities;
    }

    /**
     * Maps a set of Entities to their VO equivalent.
     * @param entities  the set of entities to map
     * @param voClass the target VO class
     * @return the mapped VOs, or null if the supplied vos are null
     */
    public static VOObject[] mapEntityToVO(CRUDEntity[] entities, Class voClass)
    {
        VOObject[] vos = null;

        if (entities != null)
        {
            vos = (VOObject[]) Array.newInstance(voClass, entities.length);

            try
            {
                for (int i = 0; i < entities.length; i++)
                {
                    Array.set(vos, i, entities[i].getVO());
                }
            }
            catch (Exception e)
            {
                throw new RuntimeException(e);
            }
        }

        return vos;
    }

    /**
     * Finds the set of child CRUDEntities for a given parent CRUDEntity.
     * @param parentEntity
     * @param childEntityClass
     * @param childVOClass
     * @param poolName
     * @return the child CRUDEntities, null of there are none
     */
    public CRUDEntity[] getChildEntities(CRUDEntity parentEntity, Class childEntityClass, Class childVOClass, String poolName)
    {
        CRUD crud = null;

        CRUDEntity[] crudEntities = null;

        long parentPK = parentEntity.getPK();

        try
        {
            Object[] childVOs = null;

            crud = CRUDFactory.getSingleton().getCRUD(poolName);

            childVOs = crud.retrieveVOFromDB(childVOClass, parentEntity.getVO().getClass(), parentPK);

            crudEntities = mapVOToEntity(childVOs, childEntityClass);
        }
        catch (Exception e)
        {
            System.out.println(e);

            e.printStackTrace();

            throw new RuntimeException(e);
        }
        finally
        {
            if (crud != null) crud.close();

            crud = null;
        }

        return crudEntities;
    }
}
