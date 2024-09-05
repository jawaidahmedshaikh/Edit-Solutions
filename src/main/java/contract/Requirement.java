/*
 * User: sprasad
 * Date: Feb 17, 2005
 * Time: 5:43:32 PM
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package contract;

import edit.services.db.CRUDEntity;
import edit.services.db.CRUDEntityImpl;
import edit.services.db.ConnectionFactory;
import edit.services.db.hibernate.*;
import edit.common.vo.VOObject;
import edit.common.vo.RequirementVO;
import contract.dm.dao.RequirementDAO;

import java.util.*;

import engine.ProductStructure;


public class Requirement extends HibernateEntity implements CRUDEntity
{
    private CRUDEntityImpl crudEntityImpl;

    private RequirementVO requirementVO;

    private Set filteredRequirements;
    
    /**
     * Indicates that any entity mapped to this Requirement can
     * only be mapped by a manual selection process.
     */
    public static final String MANUALIND_YES = "Y";
  
    /**
     * Indicates that any entity mapped to this Requirement is
     * automatically mapped (one time only) upon some initial trigger.
     */
    public static final String MANUALIND_NO = "N";


    public static final String FINAL_STATUS_DECL_REQ = "DeclineReq";
    public static final String FINAL_STATUS_INCOMPLETE = "Incomplete";

    /**
     * Used to determine if free-form descriptions should be shown for the description field
     */
    public static final String REQUIREMENT_ID_TEXT = "Text";

    /**
     * Target database to be used for lookups, etc.
     */
    public static String DATABASE = SessionHelper.EDITSOLUTIONS;



    /**
     * Instantiates a Requirement entity with a default RequirementVO.
     */
    public Requirement()
    {
        init();
    }

    /**
     * Instantiates a Requirement entity with a supplied RequirementVO.
     *
     * @param requirementVO
     */
    public Requirement(RequirementVO requirementVO)
    {
        init();

        this.requirementVO = requirementVO;
    }

    public String getAgentViewInd()
    {
        return requirementVO.getAgentViewInd();
    } //-- java.lang.String getAgentViewInd()

    public String getAllowableStatusCT()
    {
        return requirementVO.getAllowableStatusCT();
    } //-- java.lang.String getAllowableStatusCT()

    public int getFollowupDays()
    {
        return requirementVO.getFollowupDays();
    } //-- int getFollowupDays()

    public String getManualInd()
    {
        return requirementVO.getManualInd();
    } //-- java.lang.String getManualInd()

    public String getRequirementDescription()
    {
        return requirementVO.getRequirementDescription();
    } //-- java.lang.String getRequirementDescription()

    public String getRequirementId()
    {
        return requirementVO.getRequirementId();
    } //-- java.lang.String getRequirementId()

    public Long getRequirementPK()
    {
        return SessionHelper.getPKValue(requirementVO.getRequirementPK());
    } //-- long getRequirementPK()

    public void setAgentViewInd(String agentViewInd)
    {
        requirementVO.setAgentViewInd(agentViewInd);
    } //-- void setAgentViewInd(java.lang.String)

    public void setAllowableStatusCT(String allowableStatusCT)
    {
        requirementVO.setAllowableStatusCT(allowableStatusCT);
    } //-- void setAllowableStatusCT(java.lang.String)

    public void setFollowupDays(int followupDays)
    {
        requirementVO.setFollowupDays(followupDays);
    } //-- void setFollowupDays(int)

    public void setManualInd(String manualInd)
    {
        requirementVO.setManualInd(manualInd);
    } //-- void setManualInd(java.lang.String)

    public void setRequirementDescription(String requirementDescription)
    {
        requirementVO.setRequirementDescription(requirementDescription);
    } //-- void setRequirementDescription(java.lang.String)

    public void setRequirementId(String requirementId)
    {
        requirementVO.setRequirementId(requirementId);
    } //-- void setRequirementId(java.lang.String)

    public void setRequirementPK(Long requirementPK)
    {
        requirementVO.setRequirementPK(SessionHelper.getPKValue(requirementPK));
    } //-- void setRequirementPK(long)

    /**
     * Getter.
     * @return
     */
    public String getUpdatePolicyDeliveryDateInd()
    {
        return requirementVO.getUpdatePolicyDeliveryDateInd();
    }

    /**
     * Setter.
     * @param updatePolicyDeliveryDateInd
     */
    public void setUpdatePolicyDeliveryDateInd(String updatePolicyDeliveryDateInd)
    {
        requirementVO.setUpdatePolicyDeliveryDateInd(updatePolicyDeliveryDateInd);
    }

    /**
     * Getter.
     * @return
     */
    public String getFinalStatusCT()
    {
        return requirementVO.getFinalStatusCT();
    }

    /**
     * Setter.
     * @param finalStatusCT
     */
    public void setFinalStatusCT(String finalStatusCT)
    {
        requirementVO.setFinalStatusCT(finalStatusCT);
    }

    /**
     * Getter.
     * @return
     */
    public String getAutoReceipt()
    {
        return requirementVO.getAutoReceipt();
    }

    /**
     * Setter.
     * @param autoReceipt
     */
    public void setAutoReceipt(String autoReceipt)
    {
        requirementVO.setAutoReceipt(autoReceipt);
    }

    public Set getFilteredRequirements()
    {
        return filteredRequirements;
    }

    public void setFilteredRequirements(Set filteredRequirements)
    {
        this.filteredRequirements = filteredRequirements;
    }

    /**
     * Guarantees that a VO and CRUDEntityImpl are properly instantiated.
     */
    private final void init()
    {
        if (requirementVO == null)
        {
            requirementVO = new RequirementVO();
        }

        if (crudEntityImpl == null)
        {
            crudEntityImpl = new CRUDEntityImpl();
        }

        if (filteredRequirements == null)
        {
            filteredRequirements = new HashSet();
        }
    }

    /**
     * @see edit.services.db.CRUDEntity#save()
     */
    public void save()
    {
        crudEntityImpl.save(this, ConnectionFactory.EDITSOLUTIONS_POOL, false);
    }

    /**
     * @throws Throwable
     * @see edit.services.db.CRUDEntity#delete()
     */
    public void delete() throws Throwable
    {
        crudEntityImpl.delete(this, ConnectionFactory.EDITSOLUTIONS_POOL);
    }

    /**
     * @return
     * @see edit.services.db.CRUDEntity#getVO()
     */
    public VOObject getVO()
    {
        return requirementVO;
    }

    /**
     * @return
     * @see edit.services.db.CRUDEntity#getPK()
     */
    public long getPK()
    {
        return requirementVO.getRequirementPK();
    }

    /**
     * @param voObject
     */
    public void setVO(VOObject voObject)
    {
        this.requirementVO = (RequirementVO) voObject;
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
     * Clones the set of Requirements of the ActiveProductStructure to the CloneToProductStructure.
     * @param cloneTo
     */
    public void cloneRequirements(long activeProductStructurePK, long cloneToProductStructurePK) throws Exception
    {
        Requirement[] associatedRequirements = getRequirements(activeProductStructurePK);

        if (associatedRequirements != null)
        {
            for (int i = 0; i < associatedRequirements.length; i++)
            {
                FilteredRequirement filteredRequirement = new FilteredRequirement(cloneToProductStructurePK, associatedRequirements[i]);

                filteredRequirement.save();
            }
        }
    }

    /**
     * Adds a FilteredRequirement to the set of children
     * @param filteredRequirement
     */
    public void addFilteredRequirement(FilteredRequirement filteredRequirement)
    {
        this.getFilteredRequirements().add(filteredRequirement);

        filteredRequirement.setRequirement(this);

        SessionHelper.saveOrUpdate(filteredRequirement, Requirement.DATABASE);
    }

    /**
     * Removes a FilteredRequirement from the set of children
     * @param filteredRequirement
     */
    public void removeFilteredRequirement(FilteredRequirement filteredRequirement)
    {
        this.getFilteredRequirements().remove(filteredRequirement);

        filteredRequirement.setRequirement(null);

        SessionHelper.saveOrUpdate(filteredRequirement, Requirement.DATABASE);
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
     * Returns whether the requirement is manual or not.
     * @return
     */
    public boolean isManualRequirement()
    {
        if ("Y".equalsIgnoreCase(getManualInd()))
        {
            return true;
        }

        return false;
    }

    /**
     * Returns the Requirements that have been mapped to this ProductStructure.
     * @return
     */
    private Requirement[] getRequirements(long activeProductStructurePK)
    {
        return Requirement.findByProductStructureFK(activeProductStructurePK);
    }

    /**
     * Finder.
     *
     * @param requirementPK
     */
    public static final Requirement findByPK(long requirementPK)
    {
        Requirement requirement = null;

        RequirementVO[] requirementVOs = new RequirementDAO().findByPK(requirementPK, false, null);

        if (requirementVOs != null)
        {
            requirement = new Requirement(requirementVOs[0]);
        }

        return requirement;
    }

    public static final Requirement findBy_RequirementId(String requirementId)
    {
        Requirement requirement = null;

        String hql = "select r from Requirement r where r.RequirementId = :requirementId";

        Map params = new HashMap();

        params.put("requirementId", requirementId);

        List results = SessionHelper.executeHQL(hql, params, Requirement.DATABASE);

        if (!results.isEmpty())
        {
            requirement = (Requirement) results.get(0);
        }

        return requirement;
    }

    /**
     * Finder method by SegmentPk and RequirementId.
     * @param segmentPK
     * @param requirementId
     * @return
     */
    public static final Requirement findBySegmentPK_And_RequirementId(long segmentPK, String requirementId)
    {
        Requirement requirement = null;

        RequirementVO[] requirementVOs = new RequirementDAO().findBySegmentPK_And_RequirementId(segmentPK, requirementId);

        if (requirementVOs != null)
        {
            requirement = new Requirement(requirementVOs[0]);
        }

        return requirement;
    }

    /**
     * Finder method by ProductStructureFK.
     * @param productStructureFK
     * @return
     */
    public static final Requirement[] findByProductStructureFK(long productStructureFK)
    {
        Requirement[] requirement = null;

        RequirementVO[] requirementVOs = new RequirementDAO().findByProductStructure(productStructureFK, false, new ArrayList());

        if (requirementVOs != null)
        {
            requirement = new Requirement[requirementVOs.length];

            for (int i = 0; i < requirementVOs.length; i++)
            {
                requirement[i] = new Requirement(requirementVOs[i]);
            }
        }

        return requirement;
    }

    public static final Requirement[] findBy_ProductStructure(ProductStructure productStructure)
    {
        if (productStructure == null)
        {
            return new Requirement[0];
        }

        Long productStructurePK = productStructure.getProductStructurePK();

        String hql = "select distinct r from Requirement r join r.FilteredRequirements fr where fr.ProductStructureFK = :productStructurePK";

        Map params = new HashMap();

        params.put("productStructurePK", productStructurePK);

        List results = SessionHelper.executeHQL(hql, params, Requirement.DATABASE);

        return (Requirement[]) results.toArray(new Requirement[results.size()]);
    }

    /**
     * Finds all manual requirements associated to product structure.
     * @param productStructure
     * @return
     */
    public static final Requirement[] findBy_ProductStructure_And_ManualInd(ProductStructure productStructure, String manualInd)
    {
        if (productStructure == null)
        {
            return new Requirement[0];
        }

        Long productStructurePK = productStructure.getProductStructurePK();

        String hql = "select distinct r from Requirement r join r.FilteredRequirements fr where fr.ProductStructureFK = :productStructurePK" +
                     " and r.ManualInd = :manualInd";

        Map params = new HashMap();

        params.put("productStructurePK", productStructurePK);
        params.put("manualInd", manualInd);

        List results = SessionHelper.executeHQL(hql, params, Requirement.DATABASE);

        return (Requirement[]) results.toArray(new Requirement[results.size()]);
    }

    /**
     * Originally in RequirementDAO.findByFilteredRequirementPK
     * @param filteredRequirementPK
     * @param includeChildVOs
     * @param voExclusionList
     * @return
     */
    public static Requirement[] findBy_FilteredRequirementPK(Long filteredRequirementPK)
    {
        String hql = "select requirement from Requirement requirement" +
                     " join requirement.FilteredRequirements filteredRequirement " +
                     " where filteredRequirement.FilteredRequirementPK = :filteredRequirementPK";

        Map params = new HashMap();

        params.put("filteredRequirementPK", filteredRequirementPK);

        List results = SessionHelper.executeHQL(hql, params, Requirement.DATABASE);

        return (Requirement[]) results.toArray(new Requirement[results.size()]);
    }

    /**
     * Save the entity using Hibernate
     */
    public void hSave()
    {
        SessionHelper.saveOrUpdate(this, Requirement.DATABASE);
    }

    /**
     * Delete the entity using Hibernate
     */
    public void hDelete()
    {
        SessionHelper.delete(this, Requirement.DATABASE);
    }

    /**
     * Gets the database this object belongs to
     *
     * @return  string containing the hibernate database name for this object
     */
    public String getDatabase()
    {
        return Requirement.DATABASE;
    }
}