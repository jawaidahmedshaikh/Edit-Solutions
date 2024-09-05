/*
 * User: gfrosti
 * Date: Nov 17, 2004
 * Time: 10:45:10 AM
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement. 
 */

package reinsurance;

import edit.services.db.CRUDEntity;
import edit.services.db.CRUDEntityImpl;
import edit.services.db.ConnectionFactory;
import edit.services.db.hibernate.*;
import edit.common.vo.VOObject;
import edit.common.vo.TreatyGroupVO;
import edit.common.exceptions.*;
import group.ContractGroup;
import reinsurance.dm.dao.*;
import contract.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;



public class TreatyGroup extends HibernateEntity implements CRUDEntity
{
    private CRUDEntityImpl crudEntityImpl;

    private TreatyGroupVO treatyGroupVO;

    private Set<Treaty> treaties = new HashSet<Treaty>();

    private Set<FilteredTreatyGroup> filteredTreatyGroups = new HashSet<FilteredTreatyGroup>();

    /**
     * Target database to be used for lookups, etc.
     */
    public static String DATABASE = SessionHelper.EDITSOLUTIONS;


    /**
     * Instantiates a TreatyGroup entity with a default TreatyGroupVO.
     */
    public TreatyGroup()
    {
        init();
    }

    /**
     * Instantiates a TreatyGroup entity with a TreatyGroupVO retrieved from persistence.
     * @param treatyGroupPK
     */
    public TreatyGroup(long treatyGroupPK)
    {
        init();

        crudEntityImpl.load(this, treatyGroupPK, ConnectionFactory.EDITSOLUTIONS_POOL);
    }

    /**
     * Instantiates a TreatyGroup entity with a supplied TreatyGroupVO.
     * @param treatyGroupVO
     */
    public TreatyGroup(TreatyGroupVO treatyGroupVO)
    {
        init();

        this.treatyGroupVO = treatyGroupVO;
    }

    /**
     * Guarantees that a VO and CRUDEntityImpl are properly instantiated.
     */
    private final void init()
    {
        if (treatyGroupVO == null)
        {
            treatyGroupVO = new TreatyGroupVO();
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
     * Getter.
     * @return
     */
    public String getTreatyGroupNumber()
    {
        return treatyGroupVO.getTreatyGroupNumber();
    } //-- java.lang.String getTreatyGroupNumber()

    /**
     * Getter.
     * @return
     */
    public Long getTreatyGroupPK()
    {
        return SessionHelper.getPKValue(treatyGroupVO.getTreatyGroupPK());
    } //-- long getTreatyGroupPK()

    /**
     * Setter.
     * @param treatyGroupNumber
     */
    public void setTreatyGroupNumber(String treatyGroupNumber)
    {
        treatyGroupVO.setTreatyGroupNumber(treatyGroupNumber);
    } //-- void setTreatyGroupNumber(java.lang.String)

    /**
     * Setter.
     * @param treatyGroupPK
     */
    public void setTreatyGroupPK(Long treatyGroupPK)
    {
        treatyGroupVO.setTreatyGroupPK(SessionHelper.getPKValue(treatyGroupPK));
    } //-- void setTreatyGroupPK(long)


    /**
     * Getter
     * @return  set of treaties
     */
    public Set<Treaty> getTreaties()
    {
        return treaties;
    }

    /**
     * Setter
     * @param treaties      set of treaties
     */
    public void setTreaties(Set<Treaty> treaties)
    {
        this.treaties = treaties;
    }

    /**
     * Adds a Treaty to the set of children
     * @param treaty
     */
    public void addTreaty(Treaty treaty)
    {
        this.getTreaties().add(treaty);

        treaty.setTreatyGroup(this);

        SessionHelper.saveOrUpdate(treaty, TreatyGroup.DATABASE);
    }

    /**
     * Removes a Treaty from the set of children
     * @param treaty
     */
    public void removeTreaty(Treaty treaty)
    {
        this.getTreaties().remove(treaty);

        treaty.setTreatyGroup(null);

        SessionHelper.saveOrUpdate(treaty, TreatyGroup.DATABASE);
    }

    /**
     * Gets the database this object belongs to
     *
     * @return  string containing the hibernate database name for this object
     */
    public String getDatabase()
    {
        return TreatyGroup.DATABASE;
    }

    /**
     * Duplicate GroupNumbers are not allowed.
     * @throws EDITReinsuranceException if GroupNumber already exists
     */
    private void checkForDuplicates() throws EDITReinsuranceException
    {
        TreatyGroup treatyGroup = TreatyGroup.findBy_GroupNumber(treatyGroupVO.getTreatyGroupNumber());

        if (treatyGroup != null)
        {
            if (isNew())
            {
                throw new EDITReinsuranceException("Duplicate Group Numbers Not Allowed");
            }

            if (getPK() != treatyGroup.getPK())
            {
                throw new EDITReinsuranceException("Duplicate Group Numbers Not Allowed");
            }
        }
    }

    /**
     * @throws EDITReinsuranceException
     * @see edit.services.db.CRUDEntity#delete()
     */
    public void delete() throws EDITReinsuranceException
    {
        checkForAssociations();

        crudEntityImpl.delete(this, ConnectionFactory.EDITSOLUTIONS_POOL);
    }

    /**
     * A Treaty Group can not be deleted if it has any current associations with Product Structure, or Treaty.
     * @throws EDITReinsuranceException
     */
    private void checkForAssociations() throws EDITReinsuranceException
    {
        if (getFilteredTreatyGroups() != null)
        {
            throw new EDITReinsuranceException("Product Structure Assocociations Exist - The Treaty Group Can Not Be Deleted");
        }

        if (!getTreaties().isEmpty())
        {
            throw new EDITReinsuranceException("Treaty Associations Exist - The Treaty Group Can Not Be Deleted");
        }
    }

    /**
     * @return
     * @see edit.services.db.CRUDEntity#getVO()
     */
    public VOObject getVO()
    {
        return treatyGroupVO;
    }

    /**
     * @return
     * @see edit.services.db.CRUDEntity#getPK()
     */
    public long getPK()
    {
        return treatyGroupVO.getTreatyGroupPK();
    }

    /**
     * @param voObject
     */
    public void setVO(VOObject voObject)
    {
        this.treatyGroupVO = (TreatyGroupVO) voObject;
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
     * The list of child FilteredTreatyGroups for this TreatyGroup.
     * @return
     */
    public Set<FilteredTreatyGroup> getFilteredTreatyGroups()
    {
        return this.filteredTreatyGroups;
    }

    public void setFilteredTreatyGroups(Set<FilteredTreatyGroup> filteredTreatyGroups)
    {
        this.filteredTreatyGroups = filteredTreatyGroups;
    }

    /**
     * Finder.
     * @return
     */
    public static TreatyGroup[] findAllTreatyGroups()
    {
        TreatyGroupVO[] treatyGroupVOs = new TreatyGroupDAO().findAllTreatyGroupVOs();

        return (TreatyGroup[]) CRUDEntityImpl.mapVOToEntity(treatyGroupVOs, TreatyGroup.class);
    }

    /**
     * Finder.
     * @param groupNumber
     * @return
     */
    public static TreatyGroup findBy_GroupNumber(String groupNumber)
    {
        TreatyGroup treatyGroup = null;

        TreatyGroupVO[] treatyGroupVOs = new TreatyGroupDAO().findBy_GroupNumber(groupNumber);

        if (treatyGroupVOs != null)
        {
            treatyGroup = new TreatyGroup(treatyGroupVOs[0]);
        }

        return treatyGroup;
    }

    /**
     * Finder.
     * @param productStructurePK
     * @return
     */
    public static TreatyGroup[] findBy_ProductStructurePK(long productStructurePK)
    {
        return (TreatyGroup[]) CRUDEntityImpl.mapVOToEntity(new TreatyGroupDAO().findBy_ProductStructurePK(productStructurePK), TreatyGroup.class);
    }

    /**
     * Finds the specified TreatyGroup if, in fact, it has been associated with the specified Segment.
     * @param treatyGroupPK
     * @param segmentPK
     * @return
     */
    public static TreatyGroup findTreatyGroupBy_TreatyGroupPK_SegmentPK(long treatyGroupPK, long segmentPK)
    {
        TreatyGroup treatyGroup = null;

        TreatyGroupVO[] treatyGroupVOs = new TreatyGroupDAO().findTreatyGroupBy_TreatyGroupPK_SegmentPK(treatyGroupPK, segmentPK);

        if (treatyGroupVOs != null)
        {
            treatyGroup = new TreatyGroup(treatyGroupVOs[0]);
        }

        return treatyGroup;
    }

    /**
     * Finds the specified TreatyGroup if, in fact, it has been associated with the specified ContractGroup.
     * @param treatyGroupPK
     * @param segmentPK
     * @return
     */
    public static TreatyGroup findBy_TreatyGroupPK_ContractGroupPK(Long treatyGroupPK, Long contractGroupPK)
    {
        TreatyGroup treatyGroup = null;

        String hql = " select treatyGroup from TreatyGroup treatyGroup" +
                     " join treatyGroup.Treaties treaty" +
                     " join treaty.ContractTreaties contractTreaty" +
                     " where treatyGroup.TreatyGroupPK = :treatyGroupPK" +
                     " and contractTreaty.ContractGroupFK = :contractGroupPK";

        Map params = new HashMap();

        params.put("treatyGroupPK", treatyGroupPK);

        params.put("contractGroupPK", contractGroupPK);

        List<TreatyGroup> results = SessionHelper.executeHQL(hql, params, DATABASE);

        if (!results.isEmpty())
        {
            treatyGroup = results.get(0); // should only be one (I believe)
        }

        return treatyGroup;
    }

    /**
     * Associates each and every Treaty of this TreatyGroup to a Segment via a ContractTreaty.
     * @param segment
     */
    public void associateSegment(Segment segment) throws EDITReinsuranceException
    {
        Treaty[] treaties = Treaty.findBy_TreatyGroupPK(getPK());

        if (treaties != null)
        {
            for (int i = 0; i < treaties.length; i++)
            {
                Treaty treaty = treaties[i];

                ContractTreaty contractTreaty = new ContractTreaty();

                contractTreaty.associateSegment(segment);

                contractTreaty.associateTreaty(treaty);

                contractTreaty.save();
            }
        }
    }

    /**
     * Associates every Treaty of this TreatyGroup with the specified
     * contractGroup using the current transactional context.
     * @param contractGroup
     */
    public void associateContractGroup(ContractGroup contractGroup) throws EDITReinsuranceException
    {
        if (getTreaties() != null)
        {
            for (Treaty treaty:getTreaties())
            {
                ContractTreaty contractTreaty = (ContractTreaty) SessionHelper.newInstance(ContractTreaty.class, DATABASE);

                contractTreaty.setTreaty(treaty);

                contractTreaty.setContractGroup(contractGroup);
                
                contractTreaty.checkForContractGroupDuplicates();
            }
        }
    }

    /**
     * Disassociates each and every Treaty of this TreatyGroup from the specified Segment.
     * @param segment
     */
    public void disassociateFromSegment(Segment segment) throws EDITReinsuranceException
    {
        Treaty[] treaties = Treaty.findBy_TreatyGroupPK(getPK());

        if (treaties != null)
        {
            for (int i = 0; i < treaties.length; i++)
            {
                Treaty treaty = treaties[i];

                ContractTreaty contractTreaty = ContractTreaty.findBy_SegmentPK_TreatyPK(segment.getPK(), treaty.getPK());

                contractTreaty.delete();
            }
        }
    }

	/**
     * Finds the specified TreatyGroup for the given treatyGroupPK.
     * @param treatyGroupPK
     * @return
     */
    public static TreatyGroup findBy_TreatyGroupPK(long treatyGroupPK)
    {
        TreatyGroup treatyGroup = null;

        TreatyGroupVO[] treatyGroupVOs = new TreatyGroupDAO().findBy_PK(treatyGroupPK);

        if (treatyGroupVOs != null)
        {
            treatyGroup = new TreatyGroup(treatyGroupVOs[0]);
        }

        return treatyGroup;
    }

	/**
     * Finds the specified TreatyGroup for the given treatyGroupPK.
     * @param treatyGroupPK
     * @return
     */
    public static TreatyGroup findBy_TreatyGroupPK(Long treatyGroupPK)
    {
        return (TreatyGroup) SessionHelper.get(TreatyGroup.class, treatyGroupPK, DATABASE);
    }

    /**
     * Finder. Finds all the
     * @param contractGroupPK
     * @return
     */
    public static TreatyGroup[] findBy_ContractGroupPK(Long contractGroupPK)
    {
        String hql = " select treatyGroup from" +
                     " TreatyGroup treatyGroup, FilteredTreatyGroup filteredTreatyGroup, FilteredProduct filteredProduct, ContractGroup contractGroup" +
                     " where treatyGroup.TreatyGroupPK = filteredTreatyGroup.TreatyGroupFK" +
                     " and filteredTreatyGroup.ProductStructureFK = filteredProduct.ProductStructureFK" +
                     " and filteredProduct.ContractGroupFK = contractGroup.ContractGroupPK" +
                     " and contractGroup.ContractGroupPK = :contractGroupPK";

        Map params = new HashMap();

        params.put("contractGroupPK", contractGroupPK);

        List<TreatyGroup> results = SessionHelper.makeUnique(SessionHelper.executeHQL(hql, params, DATABASE));

        return results.toArray(new TreatyGroup[results.size()]);
    }

    /**
     * Save the entity using Hibernate
     */
    public void hSave()
    {
        SessionHelper.saveOrUpdate(this, TreatyGroup.DATABASE);
    }

    /**
     * Delete the entity using Hibernate
     */
    public void hDelete()
    {
        SessionHelper.delete(this, TreatyGroup.DATABASE);
    }
}
