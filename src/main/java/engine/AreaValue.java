/*
 * User: gfrosti
 * Date: Oct 29, 2004
 * Time: 1:12:31 PM
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package engine;

import edit.common.*;

import edit.common.exceptions.*;

import edit.common.vo.*;

import edit.services.db.*;
import edit.services.db.hibernate.*;

import engine.dm.dao.*;

import java.util.*;



public class AreaValue extends HibernateEntity implements CRUDEntity
{
    private static final Object DEFAULT_AREA_CT = "*";
    private static final Object DEFAULT_QUALIFIER_CT = "*";
    private AreaKey areaKey;
    private AreaValueVO areaValueVO;
    private CRUDEntityImpl crudEntityImpl;

    private Set productStructures;

    /**
     * Target database to be used for lookups, etc.
     */
    public static String DATABASE = SessionHelper.ENGINE;


    /************************************** Constructor Methods **************************************/
    /**
     * Instantiates a AreaValue entity with a default AreaValueVO.
     */
    public AreaValue()
    {
        init();
    }

    /**
     * Instantiates a AreaValue entity with a AreaValueVO retrieved from persistence.
     * @param areaValuePK
     */
    public AreaValue(long areaValuePK)
    {
        init();

        crudEntityImpl.load(this, areaValuePK, ConnectionFactory.ENGINE_POOL);
    }

    /**
     * Instantiates a AreaValue entity with a supplied AreaValueVO.
     * @param areaValueVO
     */
    public AreaValue(AreaValueVO areaValueVO)
    {
        init();

        this.areaValueVO = areaValueVO;
    }

    /************************************** Public Methods **************************************/
    /**
     * Associates an AreaKey.
     * @param areaKey
     */
    public void associateAreaKey(AreaKey areaKey)
    {
        areaValueVO.setAreaKeyFK(areaKey.getPK());
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
     * An AreaValue can contribute itself if the following conditions hold true:
     * 1. 'Its' effectiveDate is <= than the supplied effectiveDate.
     * 2. 'Its' areaCT matches the supplied areaCT.
     * 3. 'Its' areaCT does not match the supplied areaCT, but 'its' areaCT is the default of '*'.
     * @param areaCT
     * @param effectiveDate
     * @param areaValueList
     */
    public void contributeAreaValue(String areaCT, EDITDate effectiveDate, String field, List areaValueList, List valueAddedList, String qualifierCT)
    {
        if (isEffective(effectiveDate))
        {
            if (get_AreaCT().equalsIgnoreCase(areaCT) &&
                get_QualifierCT().equalsIgnoreCase(qualifierCT))
            {
                areaValueList.add(this);
                valueAddedList.add(field);
            }
        }
    }

    public void contributeDefaultValue(EDITDate effectiveDate, List areaValueList, String qualifierCT, String areaCT)
    {
        if (isEffective(effectiveDate))
        {
            if ((get_AreaCT().equals(AreaValue.DEFAULT_AREA_CT) &&
                 get_QualifierCT().equalsIgnoreCase(qualifierCT)) ||
                (get_AreaCT().equalsIgnoreCase(areaCT) &&
                 get_QualifierCT().equals(AreaValue.DEFAULT_QUALIFIER_CT)) ||
                (get_AreaCT().equals(AreaValue.DEFAULT_AREA_CT) &&
                 get_QualifierCT().equals(AreaValue.DEFAULT_QUALIFIER_CT)))
            {
                areaValueList.add(this);
            }
        }
    }

    /**
     * @throws EDITEngineException
     * @see edit.services.db.CRUDEntity#delete()
     */
    public void delete() throws EDITEngineException
    {
        if (hasProductStructureAssociations())
        {
            throw new EDITEngineException("Unable To Delete Area Value - Product Structure Assocociations Exist");
        }

        crudEntityImpl.delete(this, ConnectionFactory.ENGINE_POOL);
    }

    public String get_AreaCT()
    {
        return areaValueVO.getAreaCT();
    }

    public String get_QualifierCT()
    {
        return areaValueVO.getQualifierCT();
    }

    //-- java.lang.String getAreaCT()

    /**
     * The associated AreaKey.
     * @return
     */
    public AreaKey getAreaKey()
    {
        if (areaKey == null)
        {
            areaKey = new AreaKey(areaValueVO.getAreaKeyFK());
        }

        return areaKey;
    }

    public String get_AreaValue()
    {
        return areaValueVO.getAreaValue();
    }

    /**
     * The set of ProductStructures associated with the AreaValue.
     * @return
     */
    public ProductStructure[] get_ProductStructures()
    {
        ProductStructure[] productStructures = null;

        FilteredAreaValue[] filteredAreaValues = FilteredAreaValue.findBy_AreaValuePK(getPK());

        if (filteredAreaValues != null)
        {
            productStructures = new ProductStructure[filteredAreaValues.length];

            for (int i = 0; i < filteredAreaValues.length; i++)
            {
                productStructures[i] = filteredAreaValues[i].getProductStructure();
            }
        }

        return productStructures;
    }

    //-- java.lang.String getAreaValue() 

    /**
     * Getter.
     * @return
     */
    public EDITDate get_EffectiveDate()
    {
        return new EDITDate(areaValueVO.getEffectiveDate());
    }

    /**
     * Setter
     * @param effectiveDate
     */
    public void setEffectiveDate(EDITDate effectiveDate)
    {
        areaValueVO.setEffectiveDate(SessionHelper.getEDITDate(effectiveDate));
    }

    /**
     * Getter
     * @return
     */
    public EDITDate getEffectiveDate()
    {
        return SessionHelper.getEDITDate(areaValueVO.getEffectiveDate());
    }

    /**
     * Setter.
     * @param areaCT
     */
    public void setAreaCT(String areaCT)
    {
        areaValueVO.setAreaCT(areaCT);
    }

    /**
     * Getter
     * @return
     */
    public String getAreaCT()
    {
        return areaValueVO.getAreaCT();
    }

    /**
     * Setter
     * @param areaValue
     */
    public void setAreaValue(String areaValue)
    {
        areaValueVO.setAreaValue(areaValue);
    }

    public String getAreaValue()
    {
        return areaValueVO.getAreaValue();
    }

    /**
     * Setter.
     * @param areaValuePK
     */
    public void setAreaValuePK(Long areaValuePK)
    {
        areaValueVO.setAreaValuePK(SessionHelper.getPKValue(areaValuePK));
    }

    /**
     * Getter.
     * @return
     */
    public Long getAreaValuePK()
    {
        return SessionHelper.getPKValue(areaValueVO.getAreaValuePK());
    }

	/**
     * Setter.
     * @param qualifierCT
     */
    public void setQualifierCT(String qualifierCT)
    {
        areaValueVO.setQualifierCT(qualifierCT);
    }

    /**
     * Getter.
     * @return
     */
    public String getQualifierCT()
    {
        return areaValueVO.getQualifierCT();
    }

    /**
     * @return
     * @see edit.services.db.CRUDEntity#getPK()
     */
    public long getPK()
    {
        return areaValueVO.getAreaValuePK();
    }

    /**
     * @return
     * @see edit.services.db.CRUDEntity#getVO()
     */
    public VOObject getVO()
    {
        return areaValueVO;
    }

    /**
     * Determines if this AreaValue's effective date is <= than the supplied effective date.
     * @param effectiveDate
     * @return
     */
    public boolean isEffective(EDITDate effectiveDate)
    {
        boolean isEffective = false;

        EDITDate areaValueEffectiveDate = get_EffectiveDate();

        if (areaValueEffectiveDate.getFormattedDate().compareTo(effectiveDate.getFormattedDate()) <= 0)
        {
            isEffective = true;
        }

        return isEffective;
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
     * @see edit.services.db.CRUDEntity#save()
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
        AreaValue areaValue = AreaValue.findBy_AreaKeyPK_AreaCT_AreaValue(areaValueVO.getAreaKeyFK(), get_AreaCT(), get_AreaValue(), get_EffectiveDate().getFormattedDate(), get_QualifierCT());

        if ((areaValue != null) && (areaValue.getPK() != getPK()))
        {
            throw new EDITEngineException("The Area Value [" + getAreaValue() + "] Already Exists For The Specified AreaKey");
        }
    }

    public void set_AreaCT(String areaCT)
    {
        areaValueVO.setAreaCT(areaCT);
    }

    public void set_Qualifier(String qualifierCT)
    {
        areaValueVO.setQualifierCT(qualifierCT);
    }

    //-- void setAreaCT(java.lang.String) 

    /**
     * Setter. Overrides default AreaKey.
     * @param areaKey
     */
    public void set_AreaKey(AreaKey areaKey)
    {
        this.areaKey = areaKey;
    }

    public void set_AreaValue(String areaValue)
    {
        areaValueVO.setAreaValue(areaValue);
    }

    //-- void setAreaValue(java.lang.String) 

    /**
     * Replaces default CRUDEntityImpl.
     * @param crudEntityImpl
     */
    public void setCRUDEntityImpl(CRUDEntityImpl crudEntityImpl)
    {
        this.crudEntityImpl = crudEntityImpl;
    }

    /**
     * @param voObject
     */
    public void setVO(VOObject voObject)
    {
        this.areaValueVO = (AreaValueVO) voObject;
    }

    /************************************** Private Methods **************************************/
    /**
     * True if there are ProductStructructures mapped to this AreaValue.
     * @return
     */
    private boolean hasProductStructureAssociations()
    {
        ProductStructure[] productStructures = get_ProductStructures();

        return (productStructures != null);
    }

    /**
     * Guarantees that a VO and CRUDEntityImpl are properly instantiated.
     */
    private final void init()
    {
        if (areaValueVO == null)
        {
            areaValueVO = new AreaValueVO();
        }

        if (crudEntityImpl == null)
        {
            crudEntityImpl = new CRUDEntityImpl();
        }
    }

    /************************************** Static Methods **************************************/
    /**
     * Finder. 0 or many can be found.
     * @param productStructurePK
     * @param grouping
     * @return
     */
    public static AreaValue[] findBy_ProductStructurePK_Grouping(long productStructurePK, String grouping)
    {
        AreaValueVO[] areaValueVOs = DAOFactory.getAreaValueDAO().findBy_ProductStructurePK_Grouping(productStructurePK, grouping);

        return (AreaValue[]) CRUDEntityImpl.mapVOToEntity(areaValueVOs, AreaValue.class);
    }

    /**
     * Finder. 0 or many can be found.
     * @param productStructurePK
     * @param grouping
     * @param field
     * @return
     */
    public static AreaValue[] findBy_ProductStructurePK_Grouping_Field(long productStructurePK, String grouping, String field)
    {
        AreaValueVO[] areaValueVOs = DAOFactory.getAreaValueDAO().findBy_ProductStructurePK_Grouping_Field(productStructurePK, grouping, field);

        return (AreaValue[]) CRUDEntityImpl.mapVOToEntity(areaValueVOs, AreaValue.class);
    }

    /**
     * Finder. 0 or many can be found.
     * @param areaKeyPK
     * @return
     */
    public static AreaValue[] findBy_AreaKeyPK(long areaKeyPK)
    {
        AreaValueVO[] areaValueVOs = DAOFactory.getAreaValueDAO().findBy_AreaKeyPK(areaKeyPK);

        return (AreaValue[]) CRUDEntityImpl.mapVOToEntity(areaValueVOs, AreaValue.class);
    }

    /**
     * Finder. 0 or 1 may be found.
     * @param areaKeyPK
     * @return
     */
    public static AreaValue[] findBy_AreaValuePK(long areaValuePK)
    {
        AreaValueVO[] areaValueVOs = DAOFactory.getAreaValueDAO().findByPK(areaValuePK);

        return (AreaValue[]) CRUDEntityImpl.mapVOToEntity(areaValueVOs, AreaValue.class);
    }

    /**
     * Finder.
     * @param productStructurePK
     * @return
     */
    public static AreaValue[] findBy_ProductStructurePK(long productStructurePK)
    {
        AreaValueVO[] areaValueVOs = DAOFactory.getAreaValueDAO().findBy_ProductStructurePK(productStructurePK);

        return (AreaValue[]) CRUDEntityImpl.mapVOToEntity(areaValueVOs, AreaValue.class);
    }

    /**
     * Finder.
     * @param areaKeyPK
     * @param areaCT
     * @param areaValue
     * @return
     */
    public static final AreaValue findBy_AreaKeyPK_AreaCT_AreaValue(long areaKeyPK, String areaCT, String areaValue, String effectiveDate, String qualifierCT)
    {
        AreaValue theAreaValue = null;

        AreaValueVO[] areaValueVOs = new AreaValueDAO().findBy_AreaKeyPK_AreaCT_AreaValue(areaKeyPK, areaCT, areaValue, effectiveDate, qualifierCT);

        if (areaValueVOs != null)
        {
            theAreaValue = new AreaValue(areaValueVOs[0]);
        }

        return theAreaValue;
    }

    /**
     * Getter
     * @return  set of productStructures
     */
    public Set getProductStructures()
    {
        return productStructures;
    }

    /**
     * Setter
     * @param productStructures      set of productStructures
     */
    public void setProductStructures(Set productStructures)
    {
        this.productStructures = productStructures;
    }

    /**
     * Adds a ProductStructure to the set of children
     * @param productStructure
     */
    public void addProductStructure(ProductStructure productStructure)
    {
        this.getProductStructures().add(productStructure);

        productStructure.addAreaValue(this);

        SessionHelper.saveOrUpdate(productStructure, AreaValue.DATABASE);
    }

    /**
     * Removes a ProductStructure from the set of children
     * @param productStructure
     */
    public void removeProductStructure(ProductStructure productStructure)
    {
        this.getProductStructures().remove(productStructure);

        productStructure.removeAreaValue(null);

        SessionHelper.saveOrUpdate(productStructure, AreaValue.DATABASE);
    }


    /**
     * Save the entity using Hibernate
     */
    public void hSave()
    {
        SessionHelper.saveOrUpdate(this, AreaValue.DATABASE);
    }

    /**
     * Delete the entity using Hibernate
     */
    public void hDelete()
    {
        SessionHelper.delete(this, AreaValue.DATABASE);
    }

    /**
     * Gets the database this object belongs to
     *
     * @return  string containing the hibernate database name for this object
     */
    public String getDatabase()
    {
        return AreaValue.DATABASE;
    }
}
