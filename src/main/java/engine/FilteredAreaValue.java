/*
 * User: gfrosti
 * Date: Oct 29, 2004
 * Time: 3:44:26 PM
 *
 * (c) 2000-2004 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement. 
 */

package engine;

import edit.services.db.CRUDEntity;
import edit.services.db.CRUDEntityImpl;
import edit.services.db.ConnectionFactory;
import edit.services.db.hibernate.*;
import edit.common.vo.*;
import edit.common.exceptions.*;
import engine.dm.dao.*;

public class FilteredAreaValue extends HibernateEntity implements CRUDEntity
{
    private CRUDEntityImpl crudEntityImpl;

    private FilteredAreaValueVO filteredAreaValueVO;
    private ProductStructure productStructure;
    private AreaValue areaValue;

    /**
     * Target database to be used for lookups, etc.
     */
    public static String DATABASE = SessionHelper.ENGINE;


    /**
     * Instantiates a FilteredAreaValue entity with a default FilteredAreaKeyVO.
     */
    public FilteredAreaValue()
    {
        filteredAreaValueVO = new FilteredAreaValueVO();

        crudEntityImpl = new CRUDEntityImpl();
    }

    /**
     * Instantiates a FilteredAreaValue entity with a FilteredAreaKeyVO retrieved from persistence.
     * @param filteredAreaKeyPK
     */
    public FilteredAreaValue(long filteredAreaKeyPK)
    {
        crudEntityImpl = new CRUDEntityImpl();

        crudEntityImpl.load(this, filteredAreaKeyPK, ConnectionFactory.ENGINE_POOL);
    }

    /**
     * Instantiates a FilteredAreaValue entity with a supplied FilteredAreaKeyVO.
     * @param filteredAreaValueVO
     */
    public FilteredAreaValue(FilteredAreaValueVO filteredAreaValueVO)
    {
        crudEntityImpl = new CRUDEntityImpl();

        this.filteredAreaValueVO = filteredAreaValueVO;
    }

    /**
     * Instantiates with associated ProductStructure and AreaKey. The FilteredAreaValue is assumed to be new.
     * @param productStructure
     * @param areaValue
     */
    public FilteredAreaValue(ProductStructure productStructure, AreaValue areaValue)
    {
        filteredAreaValueVO = new FilteredAreaValueVO();

        crudEntityImpl = new CRUDEntityImpl();

        this.filteredAreaValueVO.setProductStructureFK(productStructure.getPK());

        this.filteredAreaValueVO.setAreaValueFK(areaValue.getPK());
    }

    /**
     * Setter.
     * @param filteredAreaValuePK
     */
    public void setFilteredAreaValuePK(Long filteredAreaValuePK)
    {
        this.filteredAreaValueVO.setFilteredAreaValuePK(SessionHelper.getPKValue(filteredAreaValuePK));
    }

    /**
     * Getter.
     * @return
     */
    public Long getFilteredAreaValuePK()
    {
        return SessionHelper.getPKValue(this.filteredAreaValueVO.getFilteredAreaValuePK());
    }

    /**
     * Setter.
     * @param productStructure
     */
    public void setProductStructure(ProductStructure productStructure)
    {
        this.productStructure = productStructure;
    }

    /**
     * Getter.
     * @return
     */
    public ProductStructure getProductStructure()
    {
        return productStructure;
    }

    /**
     * Setter.
     * @param areaValue
     */
    public void setAreaValue(AreaValue areaValue)
    {
        this.areaValue = areaValue;
    }

    /**
     * Getter.
     * @return
     */
    public AreaValue getAreaValue()
    {
        return areaValue;
    }

    /**
     * @see edit.services.db.CRUDEntity#save()
     * @see #checkForDuplicates()
     */
    public void save() throws EDITEngineException
    {
        checkForDuplicates();

        crudEntityImpl.save(this, ConnectionFactory.ENGINE_POOL, false);
    }

    /**
     * Checks for pre-existing association.
     * @throws EDITEngineException if the ProductStructure/AreaValue association has already been made
     */
    private void checkForDuplicates() throws EDITEngineException
    {
        if (filteredAreaValueVO.getProductStructureFK() != 0 || filteredAreaValueVO.getAreaValueFK() != 0)
        {
            FilteredAreaValue filteredAreaValue = FilteredAreaValue.findBy_ProductStructurePK_AreaValuePK(filteredAreaValueVO.getProductStructureFK(), filteredAreaValueVO.getAreaValueFK());

            if (filteredAreaValue != null)
            {
                throw new EDITEngineException("WARNING: Area Value Has Already Been Mapped To Product Structure");
            }
        }
    }

    /**
     * @see edit.services.db.CRUDEntity#delete()
     */
    public void delete()
    {
        crudEntityImpl.delete(this, ConnectionFactory.ENGINE_POOL);
    }

    /**
     * Getter. Returns 1:1 ProductStructures.
     * @return
     */
    public ProductStructure get_ProductStructure()
    {
        return new ProductStructure(filteredAreaValueVO.getProductStructureFK());
    }

    /**
     * @return
     * @see edit.services.db.CRUDEntity#getVO()
     */
    public VOObject getVO()
    {
        return filteredAreaValueVO;
    }

    /**
     * @return
     * @see edit.services.db.CRUDEntity#getPK()
     */
    public long getPK()
    {
        return filteredAreaValueVO.getFilteredAreaValuePK();
    }

    /**
     * @param voObject
     */
    public void setVO(VOObject voObject)
    {
        this.filteredAreaValueVO = (FilteredAreaValueVO) voObject;
    }

    /**
     * @return
     * @see edit.services.db.CRUDEntity#isNew()
     */
    public boolean isNew()
    {
        return crudEntityImpl.isNew(this);
    }

    /**
     * @return
     * @see edit.services.db.CRUDEntity#cloneCRUDEntity()
     */
    public CRUDEntity cloneCRUDEntity()
    {
        return crudEntityImpl.cloneCRUDEntity(this);
    }

    /**
     * Overrides default CRUDEntityImpl.
     * @param crudEntityImpl
     */
    public void setCRUDEntityImpl(CRUDEntityImpl crudEntityImpl)
    {
        this.crudEntityImpl = crudEntityImpl;
    }

    /**
     * Gets the database this object belongs to
     *
     * @return  string containing the hibernate database name for this object
     */
    public String getDatabase()
    {
        return FilteredAreaValue.DATABASE;
    }

    /**
     * The associated AreaValue.
     * @return
     */
    public AreaValue get_AreaValue()
    {
        AreaValue areaValue = new AreaValue(filteredAreaValueVO.getAreaValueFK());

        return areaValue;
    }

    /**
     * Finder.
     * @param areaValuePK
     * @return
     */
    public static FilteredAreaValue[] findBy_AreaValuePK(long areaValuePK)
    {
        FilteredAreaValueVO[] filteredAreaValueVOs = DAOFactory.getFilteredAreaValueDAO().findBy_AreaKeyPK(areaValuePK);

        return (FilteredAreaValue[]) CRUDEntityImpl.mapVOToEntity(filteredAreaValueVOs, FilteredAreaValue.class);
    }

    /**
     * Finder.
     * @param productStructurePK
     * @param areaValuePK
     * @return
     */
    public static FilteredAreaValue findBy_ProductStructurePK_AreaValuePK(long productStructurePK, long areaValuePK)
    {
        FilteredAreaValue filteredAreaValue = null;

        FilteredAreaValueVO[] filteredAreaValueVOs = DAOFactory.getFilteredAreaValueDAO().findBy_ProductStructurePK_AreaValueKeyPK(productStructurePK, areaValuePK);

        if (filteredAreaValueVOs != null)
        {
            filteredAreaValue = new FilteredAreaValue(filteredAreaValueVOs[0]);
        }

        return filteredAreaValue;
    }
}
