/*
 * User: gfrosti
 * Date: Nov 19, 2004
 * Time: 12:51:20 PM
 *
 * (c) 2000-2004 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement. 
 */

package reinsurance;

import edit.services.db.CRUDEntity;
import edit.services.db.CRUDEntityImpl;
import edit.services.db.ConnectionFactory;
import edit.common.vo.VOObject;
import edit.common.vo.FilteredTreatyGroupVO;
import edit.common.exceptions.*;
import edit.services.db.hibernate.HibernateEntity;
import edit.services.db.hibernate.SessionHelper;
import reinsurance.dm.dao.*;
import engine.*;

public class FilteredTreatyGroup extends HibernateEntity implements CRUDEntity
{
    private CRUDEntityImpl crudEntityImpl;

    private FilteredTreatyGroupVO filteredTreatyGroupVO;

    private TreatyGroup treatyGroup;

    /**
     * Instantiates a FilteredTreatyGroup entity with a default FilteredTreatyGroupVO.
     */
    public FilteredTreatyGroup()
    {
        init();
    }

    /**
     * Instantiates a FilteredTreatyGroup entity with a FilteredTreatyGroupVO retrieved from persistence.
     * @param filteredTreatyGroupPK
     */
    public FilteredTreatyGroup(long filteredTreatyGroupPK)
    {
        init();

        crudEntityImpl.load(this, filteredTreatyGroupPK, ConnectionFactory.EDITSOLUTIONS_POOL);
    }

    public FilteredTreatyGroup(long treatyGroupPK, long productStructurePK)
    {
        init();

        this.filteredTreatyGroupVO.setTreatyGroupFK(treatyGroupPK);

        this.filteredTreatyGroupVO.setProductStructureFK(productStructurePK);
    }

    /**
     * Instantiates a FilteredTreatyGroup entity with a supplied FilteredTreatyGroupVO.
     * @param filteredTreatyGroupVO
     */
    public FilteredTreatyGroup(FilteredTreatyGroupVO filteredTreatyGroupVO)
    {
        init();

        this.filteredTreatyGroupVO = filteredTreatyGroupVO;
    }

    /**
     * Guarantees that a VO and CRUDEntityImpl are properly instantiated.
     */
    private final void init()
    {
        if (filteredTreatyGroupVO == null)
        {
            filteredTreatyGroupVO = new FilteredTreatyGroupVO();
        }

        if (crudEntityImpl == null)
        {
            crudEntityImpl = new CRUDEntityImpl();
        }
    }

    /**
     * @see edit.services.db.CRUDEntity#save()
     */
    public void save() throws EDITReinsuranceException
    {
        checkForDuplicates();

        crudEntityImpl.save(this, ConnectionFactory.EDITSOLUTIONS_POOL, false);
    }

    /**
     * Checks for pre-existing association.
     * @throws edit.common.exceptions.EDITReinsuranceException if the ProductStructure/TreatyGroup association has already been made
     */
    private void checkForDuplicates() throws EDITReinsuranceException
    {
        if (filteredTreatyGroupVO.getProductStructureFK() != 0 || filteredTreatyGroupVO.getTreatyGroupFK() != 0)
        {
            FilteredTreatyGroup filteredTreatyGroup = FilteredTreatyGroup.findBy_ProductStructurePK_TreatyGroupPK(filteredTreatyGroupVO.getProductStructureFK(), filteredTreatyGroupVO.getTreatyGroupFK());

            if (filteredTreatyGroup != null)
            {
                throw new EDITReinsuranceException("WARNING: Treaty Group Has Already Been Mapped To Product Structure");
            }
        }
    }

    /**
     * Finder.
     * @param productStructurePK
     * @param treatyGroupPK
     * @return
     */
    public static FilteredTreatyGroup findBy_ProductStructurePK_TreatyGroupPK(long productStructurePK, long treatyGroupPK)
    {
        FilteredTreatyGroup filteredTreatyGroup = null;

        FilteredTreatyGroupVO[] filteredTreatyGroupVOs = new FilteredTreatyGroupDAO().findBy_ProductStructurePK_TreatyGroupPK(productStructurePK, treatyGroupPK);

        if (filteredTreatyGroupVOs != null)
        {
            filteredTreatyGroup = new FilteredTreatyGroup(filteredTreatyGroupVOs[0]);
        }

        return filteredTreatyGroup;
    }

    /**
     * @see edit.services.db.CRUDEntity#delete()
     */
    public void delete()
    {
        crudEntityImpl.delete(this, ConnectionFactory.EDITSOLUTIONS_POOL);
    }

    /**
     * @return
     * @see edit.services.db.CRUDEntity#getVO()
     */
    public VOObject getVO()
    {
        return filteredTreatyGroupVO;
    }

    public long getPK()
    {
        return this.filteredTreatyGroupVO.getFilteredTreatyGroupPK();
    }

    /**
     * @return
     * @see edit.services.db.CRUDEntity#getPK()
     */
    public Long getFilteredTreatyGroupPK()
    {
        return SessionHelper.getPKValue(filteredTreatyGroupVO.getFilteredTreatyGroupPK());
    }

    public void setFilteredTreatyGroupPK(Long filteredTreatyGroupPK)
    {
        filteredTreatyGroupVO.setFilteredTreatyGroupPK(SessionHelper.getPKValue(filteredTreatyGroupPK));
    }

    public Long getProductStructureFK()
    {
        return SessionHelper.getPKValue(filteredTreatyGroupVO.getProductStructureFK());
    }

    public void setProductStructureFK(Long productStructureFK)
    {
        this.filteredTreatyGroupVO.setProductStructureFK(SessionHelper.getPKValue(productStructureFK));
    }

    /**
     * @param voObject
     */
    public void setVO(VOObject voObject)
    {
        this.filteredTreatyGroupVO = (FilteredTreatyGroupVO) voObject;
    }

    public Long getTreatyGroupFK()
    {
        return SessionHelper.getPKValue(filteredTreatyGroupVO.getTreatyGroupFK());
    }

    public void setTreatyGroupFK(Long treatyGroupFK)
    {
        this.filteredTreatyGroupVO.setTreatyGroupFK(SessionHelper.getPKValue(treatyGroupFK));
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
     *
     * @param treatyGroupPK
     * @return
     */
    public static FilteredTreatyGroup findBy_TreatyGroupPK(long treatyGroupPK)
    {
        FilteredTreatyGroup filteredTreatyGroup = null;

        FilteredTreatyGroupVO[] filteredTreatyGroupVOs = new FilteredTreatyGroupDAO().findBy_TreatyGroupPK(treatyGroupPK);

        if (filteredTreatyGroupVOs != null)
        {
            filteredTreatyGroup = new FilteredTreatyGroup(filteredTreatyGroupVOs[0]);
        }

        return filteredTreatyGroup;
    }

    @Override
    public String getDatabase()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * @return the treatyGroup
     */
    public TreatyGroup getTreatyGroup()
    {
        return treatyGroup;
    }

    /**
     * @param treatyGroup the treatyGroup to set
     */
    public void setTreatyGroup(TreatyGroup treatyGroup)
    {
        this.treatyGroup = treatyGroup;
    }
}
