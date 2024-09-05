/*
 * User: gfrosti
 * Date: Oct 27, 2004
 * Time: 2:52:55 PM
 *
 * (c) 2000-2004 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package engine;

import edit.common.exceptions.*;
import edit.common.vo.*;
import edit.services.db.*;
import edit.services.db.hibernate.HibernateEntity;
import edit.services.db.hibernate.SessionHelper;
import engine.dm.dao.*;


public class AreaKey extends HibernateEntity implements CRUDEntity
{
	private static final long serialVersionUID = 1L;
	
	public static final String GROUPING_FREELOOKPROCESS = "FREELOOKPROCESS";
    public static final String GROUPING_COMMISSION = "COMMISSION";
    public static final String GROUPING_PENDINGREQUIREMENTS = "PENDINGREQUIREMENTS";

    public static final String FIELD_FREELOOKDAYS = "FREELOOKDAYS";
    public static final String FIELD_HOLDDAYS = "HOLDDAYS";
    public static final String FIELD_ALLOWRIDERS = "ALLOWRIDERS";
    public static final String FIELD_ADVANCEFINALNOTIFY = "ADVANCEFINALNOTIFY";

    private static final String ERROR_AREA_KEY_ALREADY_EXISTS = "AreaKey Already Exists";
    private static AreaKeyDAO areaKeyDAO;
    private AreaKeyVO areaKeyVO;
    private CRUDEntityImpl crudEntityImpl;
    
    public static final String DATABASE = SessionHelper.ENGINE;

    /************************************** Constructor Methods **************************************/
    /**
     * Instantiates a AreaKey entity with a default AreaKeyVO.
     */
    public AreaKey()
    {
        areaKeyVO = new AreaKeyVO();
    }

    /**
     * Instantiates a AreaKey entity with a AreaKeyPK retrieved from persistence.
     * @param areaKeyPK
     */
    public AreaKey(long areaKeyPK)
    {
        areaKeyVO = new AreaKeyVO();

        //getCRUDEntityImpl().load(this, areaKeyPK, ConnectionFactory.ENGINE_POOL);

        this.setVO(((AreaKey) SessionHelper.get(AreaKey.class, areaKeyPK, AreaKey.DATABASE)).getVO());
    }

    /**
     * Instantiates a AreaKey entity with an AreaKeyVO retrieved from persistence.
     * @param areaKeyVO
     */
    public AreaKey(AreaKeyVO areaKeyVO)
    {
        this.areaKeyVO = areaKeyVO;
    }

    /**
     * Instantiates a AreaKey entity with a supplied AreaKeyVO.
     * @param grouping
     * @param field
     */
    public AreaKey(String grouping, String field)
    {
        areaKeyVO = new AreaKeyVO();

        areaKeyVO.setGrouping(grouping.toUpperCase());

        areaKeyVO.setField(field.toUpperCase());
    }

    /************************************** Public Methods **************************************/
    /**
     * @return
     * @see edit.services.db.CRUDEntity#cloneCRUDEntity()
     */
    public CRUDEntity cloneCRUDEntity()
    {
        return getCRUDEntityImpl().cloneCRUDEntity(this);
    }

    /**
     * @throws EDITEngineException
     * @see edit.services.db.CRUDEntity#delete()
     */
    public void delete() throws EDITEngineException
    {
        if (hasAreaValueAssociations())
        {
            throw new EDITEngineException("Unable To Delete Area Key - Area Value Assocociations Exist");
        }

        getCRUDEntityImpl().delete(this, ConnectionFactory.ENGINE_POOL);
    }

    /**
     * True if there are any AreaValues associated with this AreaKey.
     * @return
     */
    private boolean hasAreaValueAssociations()
    {
        AreaValue[] areaValues = getAreaValues();

        return (areaValues != null);
    }

    /**
     * Getter.
     * @return
     */
    public String getField()
    {
        return areaKeyVO.getField();
    }

    /**
     * Getter.
     * @return
     */
    public String getGrouping()
    {
        return areaKeyVO.getGrouping();
    }

    /**
     * @return
     * @see edit.services.db.CRUDEntity#getPK()
     */
    public long getPK()
    {
        return areaKeyVO.getAreaKeyPK();
    }

    /**
     * @return
     * @see edit.services.db.CRUDEntity#getVO()
     */
    public VOObject getVO()
    {
        return areaKeyVO;
    }

    /**
     * @return
     * @see edit.services.db.CRUDEntity#isNew()
     */
    public boolean isNew()
    {
        return getCRUDEntityImpl().isNew(this);
    }

    /**
     * @see edit.services.db.CRUDEntity#save()
     */
    public void save() throws EDITEngineException
    {
        checkForDuplicate(getGrouping(), getField());

        getCRUDEntityImpl().save(this, ConnectionFactory.ENGINE_POOL, false);
    }

    /**
     * Allows substitution of AreaKeyDAO instance.
     * @param areaKeyDAO
     */
    public void setAreaKeyDAO(AreaKeyDAO areaKeyDAO)
    {
        this.areaKeyDAO = areaKeyDAO;
    }

    /**
     * Allows substitution of default CRUDEntityImpl instance.
     * @param crudEntityImpl
     */
    public void setCRUDEntityImpl(CRUDEntityImpl crudEntityImpl)
    {
        this.crudEntityImpl = crudEntityImpl;
    }

    public void setField(String field)
    {
        areaKeyVO.setField(field);
    }

    //-- void setField(java.lang.String) 

    /**
     * @param voObject
     */
    public void setVO(VOObject voObject)
    {
        this.areaKeyVO = (AreaKeyVO) voObject;
    }

    public AreaValue[] getAreaValues()
    {
        return AreaValue.findBy_AreaKeyPK(getPK());
    }

    /************************************** Private Methods **************************************/
    /**
     * @param grouping
     * @param field
     * @throws EDITEngineException
     */
    private final void checkForDuplicate(String grouping, String field) throws EDITEngineException
    {
        if (getPK() == 0) // Only check if this is potentially new
        {
            boolean areaKeyExists = false;

            AreaKey areaKey = findByGrouping_AND_Field(grouping, field);

            areaKeyExists = (areaKey != null);

            if (areaKeyExists)
            {
                throw new EDITEngineException(ERROR_AREA_KEY_ALREADY_EXISTS + " [grouping:" + grouping + "] [field:" + field + "]");
            }
        }
    }

    /**
     * Returns an instance of CRUDEntityImpl. A testing framework may substitute this instance.
     * @return
     */
    private CRUDEntityImpl getCRUDEntityImpl()
    {
        if (crudEntityImpl == null)
        {
            crudEntityImpl = new CRUDEntityImpl();
        }

        return crudEntityImpl;
    }

    /************************************** Static Methods **************************************/
    /**
      * Finder. There can be only 0 or 1 returned.
      * @param areaKeyPK
      * @return
      */
    public static AreaKey findBy_AreaKeyPK(long areaKeyPK)
    {
        AreaKey[] areaKeys = null;

        AreaKeyVO[] areaKeyVOs = getAreaKeyDAO().findBy_AreaPK(areaKeyPK);

        areaKeys = (AreaKey[]) CRUDEntityImpl.mapVOToEntity(areaKeyVOs, AreaKey.class);

        return areaKeys[0];
    }

    /**
      * Finder. There can be 0 or many.
      * @param grouping
      * @param productStructurePK
      * @return
      */
    public static AreaKey[] findBy_Grouping(String grouping)
    {
        AreaKey[] areaKeys = null;

        AreaKeyVO[] areaKeyVOs = getAreaKeyDAO().findBy_Grouping(grouping);

        areaKeys = (AreaKey[]) CRUDEntityImpl.mapVOToEntity(areaKeyVOs, AreaKey.class);

        return areaKeys;
    }

    /**
     * Finder by grouping and field. There can be zero or one.
     * @param grouping
     * @param field
     * @return
     */
    public static AreaKey findByGrouping_AND_Field(String grouping, String field)
    {
        AreaKey areaKey = null;

        AreaKeyVO[] areaKeyVOs = getAreaKeyDAO().findByGrouping_AND_Field(grouping, field);

        if (areaKeyVOs != null)
        {
            areaKey = new AreaKey(areaKeyVOs[0]);
        }

        return areaKey;
    }

    /**
     * Returns an instance of AreaKeyDAO.
     * @return
     */
    private static AreaKeyDAO getAreaKeyDAO()
    {
        if (areaKeyDAO == null)
        {
            areaKeyDAO = engine.dm.dao.DAOFactory.getAreaKeyDAO();
        }

        return areaKeyDAO;
    }

    public static AreaKey[] findAllAreaKeys()
    {
        AreaKeyVO[] areaKeyVOs = DAOFactory.getAreaKeyDAO().findAll();

        return (AreaKey[]) CRUDEntityImpl.mapVOToEntity(areaKeyVOs, AreaKey.class);
    }
    
    public void setAreaKeyPK(Long areaKeyPK)
    {
        areaKeyVO.setAreaKeyPK(SessionHelper.getPKValue(areaKeyPK));
    }

    public Long getAreaKeyPK()
    {
        return SessionHelper.getPKValue(areaKeyVO.getAreaKeyPK());
    }
    
    public void setGrouping(String grouping)
    {
        areaKeyVO.setGrouping(grouping);
    }

    /*public String getGrouping()
    {
        return areaKeyVO.getGrouping();
    }
    
    public void setField(String field)
    {
        areaKeyVO.setField(field);
    }

    public String getField()
    {
        return areaKeyVO.getField();
    }*/
}
