/*
 * User: sprasad
 * Date: Feb 23, 2005
 * Time: 1:13:07 PM
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package contract;

import casetracking.*;

import contract.dm.dao.*;

import edit.common.vo.*;

import edit.services.db.*;
import edit.services.db.hibernate.*;

import engine.*;

import java.util.*;

import agent.AgentRequirement;

import edit.common.EDITMap;

import group.BatchProgressLog;
import group.ContractGroupRequirement;

public class FilteredRequirement extends HibernateEntity implements CRUDEntity
{
    private CRUDEntityImpl crudEntityImpl;
    private FilteredRequirementVO filteredRequirementVO;
    private Requirement requirement;
    private Set caseRequirements;
    private Set agentRequirements;
   
    /**
     * The set of associated ContractGroupRequirements.
     */
    private Set<ContractGroupRequirement> contractGroupRequirements;

    /**
     * Target database to be used for lookups, etc.
     */
    public static String DATABASE = SessionHelper.EDITSOLUTIONS;



    /**
     * Instantiates a FilteredRequirement entity with a default FilteredRequirementVO.
     */
    public FilteredRequirement()
    {
        init();
    }

    /**
     * Instantiates a FilteredRequirement entity with a supplied FilteredRequirementVO.
     *
     * @param filteredRequirementVO
     */
    public FilteredRequirement(FilteredRequirementVO filteredRequirementVO)
    {
        init();

        this.filteredRequirementVO = filteredRequirementVO;
    }

    /**
     * Instantiates with associated ProductStructure and RequirementVO. The FilteredRequirement is assumed to be new.
     * @param productStructure
     * @param areaValue
     */
    public FilteredRequirement(long productStructurePK, Requirement requirement)
    {
        filteredRequirementVO = new FilteredRequirementVO();

        crudEntityImpl = new CRUDEntityImpl();

        this.filteredRequirementVO.setProductStructureFK(productStructurePK);

        this.filteredRequirementVO.setRequirementFK(requirement.getPK());
    }

    public FilteredRequirement(ProductStructure productStructure)
    {
        filteredRequirementVO = new FilteredRequirementVO();

        filteredRequirementVO.setProductStructureFK(SessionHelper.getPKValue(productStructure.getProductStructurePK()));
    }

    public void setRequirementFK(Long requirementFK)
    {
        filteredRequirementVO.setRequirementFK(SessionHelper.getPKValue(requirementFK));
    }
     //-- void setRequirementFK(long) 

    /**
     * Constructor
     * @param requirement
     * @param productStructure
     */
    public FilteredRequirement(Requirement requirement, ProductStructure productStructure)
    {
        init();

        requirement.addFilteredRequirement(this);

        this.setProductStructureFK(productStructure.getProductStructurePK());
    }

    /**
     * Getter.
     * @return
     */
    public Long getProductStructureFK()
    {
        return SessionHelper.getPKValue(filteredRequirementVO.getProductStructureFK());
    }
     //-- long getProductStructureFK()

    /**
     * Getter.
     * @return
     */
    public Long getRequirementFK()
    {
        return SessionHelper.getPKValue(filteredRequirementVO.getRequirementFK());
    }

    /**
     * Getter.
     * @return
     */
    public Long getFilteredRequirementPK()
    {
        return SessionHelper.getPKValue(filteredRequirementVO.getFilteredRequirementPK());
    }
     //-- long getFilteredRequirementPK()

    /**
     * Setter.
     * @param productStructureFK
     */
    public void setProductStructureFK(Long productStructureFK)
    {
        filteredRequirementVO.setProductStructureFK(SessionHelper.getPKValue(productStructureFK));
    }
     //-- void setProductStructureFK(long)

    /**
     * Setter.
     * @param filteredRequirementPK
     */
    public void setFilteredRequirementPK(Long filteredRequirementPK)
    {
        filteredRequirementVO.setFilteredRequirementPK(SessionHelper.getPKValue(filteredRequirementPK));
    }
     //-- void setFilteredRequirementPK(long)

    /**
     * Getter.
     * @return
     */
    public Requirement getRequirement()
    {
        return requirement;
    }

    /**
     * Setter.
     * @param requirement
     */
    public void setRequirement(Requirement requirement)
    {
        this.requirement = requirement;
    }

    /**
     * Getter.
     * @return
     */
    public Set getCaseRequirements()
    {
        return caseRequirements;
    }

    /**
     * Setter.
     * @param caseRequirements
     */
    public void setCaseRequirements(Set caseRequirements)
    {
        this.caseRequirements = caseRequirements;
    }

    /**
     * Getter.
     * @return
     */
    public Set getAgentRequirements()
    {
        return agentRequirements;
    }

    /**
     * Setter.
     * @param agentRequirements
     */
    public void setAgentRequirements(Set agentRequirements)
    {
        this.agentRequirements = agentRequirements;
    }

    /**
     * Guarantees that a VO and CRUDEntityImpl are properly instantiated.
     */
    private final void init()
    {
        if (filteredRequirementVO == null)
        {
            filteredRequirementVO = new FilteredRequirementVO();
        }

        if (crudEntityImpl == null)
        {
            crudEntityImpl = new CRUDEntityImpl();
        }

        if (caseRequirements == null)
        {
            caseRequirements = new HashSet();
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
        return filteredRequirementVO;
    }

    /**
     * @return
     * @see edit.services.db.CRUDEntity#getPK()
     */
    public long getPK()
    {
        return filteredRequirementVO.getFilteredRequirementPK();
    }

    /**
     * @param voObject
     */
    public void setVO(VOObject voObject)
    {
        this.filteredRequirementVO = (FilteredRequirementVO) voObject;
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
     * Deletes caseRequirement from collection.
     * @param caseRequirement
     */
    public void deleteCaseRequirement(CaseRequirement caseRequirement)
    {
        for (Iterator iterator = caseRequirements.iterator(); iterator.hasNext();)
        {
            CaseRequirement currentCaseRequirement = (CaseRequirement) iterator.next();

            if (caseRequirement.getCaseRequirementPK().longValue() == currentCaseRequirement.getCaseRequirementPK().longValue())
            {
                iterator.remove();
            }
        }
    }

    /**
     * Adds a CaseRequirement to the set of children
     * @param caseRequirement
     */
    public void addCaseRequirement(CaseRequirement caseRequirement)
    {
        this.getCaseRequirements().add(caseRequirement);

        caseRequirement.setFilteredRequirement(this);

        SessionHelper.saveOrUpdate(caseRequirement, FilteredRequirement.DATABASE);
    }
    
    /**
     * Adder.
     * @param contractGroupRequirement
     */
    public void addContractGroupRequirement(ContractGroupRequirement contractGroupRequirement)
    {
        this.getContractGroupRequirements().add(contractGroupRequirement);
        
        contractGroupRequirement.setFilteredRequirement(this);
        
        SessionHelper.saveOrUpdate(this, getDatabase());
    }

    /**
     * Removes a CaseRequirement from the set of children
     * @param caseRequirement
     */
    public void removeCaseRequirement(CaseRequirement caseRequirement)
    {
        this.getCaseRequirements().remove(caseRequirement);

        caseRequirement.setFilteredRequirement(null);

        SessionHelper.saveOrUpdate(caseRequirement, FilteredRequirement.DATABASE);
    }

    /**
     * Add AgentRequirment.
     * @param agentRequirement
     */
    public void addAgentRequirement(AgentRequirement agentRequirement)
    {
        getAgentRequirements().add(agentRequirement);

        agentRequirement.setFilteredRequirement(this);
    }

    /**
     * Deletes agentRequirement from collection.
     * @param agentRequirement
     */
    public void deleteAgentRequirement(AgentRequirement agentRequirement)
    {
        for (Iterator iterator = agentRequirements.iterator(); iterator.hasNext();)
        {
            AgentRequirement currentAgentRequirement = (AgentRequirement) iterator.next();

            if (agentRequirement.getAgentRequirementPK().longValue() == currentAgentRequirement.getAgentRequirementPK().longValue())
            {
                iterator.remove();
            }
        }
    }

    /************************************** Static Methods **************************************/
    public static final FilteredRequirement findByPK(Long filteredRequirementPK)
    {
        return (FilteredRequirement) SessionHelper.get(FilteredRequirement.class, filteredRequirementPK, FilteredRequirement.DATABASE);
    }

    /**
     * Finder.
     *
     * @param filteredRequirementPK
     */
    public static final FilteredRequirement findByPK(long filteredRequirementPK)
    {
        FilteredRequirement filteredRequirement = null;

        FilteredRequirementVO[] filteredRequirementVOs = new FilteredRequirementDAO().findByPK(filteredRequirementPK, false, null);

        if (filteredRequirementVOs != null)
        {
            filteredRequirement = new FilteredRequirement(filteredRequirementVOs[0]);
        }

        return filteredRequirement;
    }

    /**
     * Finder method by ProductStructurePK and RequirementId.
     * @param productStructurePK
     * @param requirementId
     * @return
     */
    public static final FilteredRequirement findBy_ProductStructurePK_AND_RequirementId(long productStructurePK, String requirementId)
    {
        FilteredRequirement filteredRequirement = null;

        FilteredRequirementVO[] filteredRequirementVOs = new FilteredRequirementDAO().findByProductStructurePK_AND_RequirementId(productStructurePK, requirementId);

        if (filteredRequirementVOs != null)
        {
            filteredRequirement = new FilteredRequirement(filteredRequirementVOs[0]);
        }

        return filteredRequirement;
    }

    /**
     * Finder.
     * @param productStructure
     * @return
     */
    public static final FilteredRequirement[] findBy_ProductStructure(ProductStructure productStructure)
    {
        if (productStructure == null)
        {
            return new FilteredRequirement[0];
        }

        Long productStructurePK = productStructure.getProductStructurePK();

        String hql = "select fr from FilteredRequirement fr where fr.ProductStructureFK = :productStructurePK";

        Map params = new HashMap();

        params.put("productStructurePK", productStructurePK);

        List results = SessionHelper.executeHQL(hql, params, FilteredRequirement.DATABASE);

        return (FilteredRequirement[]) results.toArray(new FilteredRequirement[results.size()]);
    }

    /**
     * Finder by ProductStructure and Requirement.
     * @param productStructure
     * @param requirement
     * @return
     */
    public static final FilteredRequirement findBy_ProductStructure_And_Requirement(ProductStructure productStructure, Requirement requirement)
    {
        FilteredRequirement filteredRequirement = null;

        if (productStructure == null)
        {
            return null;
        }

        Long productStructurePK = productStructure.getProductStructurePK();

        String hql = "select fr from FilteredRequirement fr where fr.ProductStructureFK = :productStructurePK " +
                     "and fr.Requirement = :requirement";

        Map params = new HashMap();

        params.put("productStructurePK", productStructurePK);
        params.put("requirement", requirement);

        List results = SessionHelper.executeHQL(hql, params, FilteredRequirement.DATABASE);

        if (!results.isEmpty())
        {
            filteredRequirement = (FilteredRequirement) results.get(0);
        }

        return filteredRequirement;
    }

    /**
     * Save the entity using Hibernate
     */
    public void hSave()
    {
        SessionHelper.saveOrUpdate(this, FilteredRequirement.DATABASE);
    }

    /**
     * Delete the entity using Hibernate
     */
    public void hDelete()
    {
        SessionHelper.delete(this, FilteredRequirement.DATABASE);
    }

    /**
     * Gets the database this object belongs to
     *
     * @return  string containing the hibernate database name for this object
     */
    public String getDatabase()
    {
        return FilteredRequirement.DATABASE;
    }

  /**
   * Finds all FilteredRequirements associated to the specified ProductStructure with the specified manualInd.
   * @param productStructureFK
   * @return
   */
  public static final FilteredRequirement[] findBy_ProductStructureFK_And_ManualInd(Long productStructureFK, String manualInd)
  {
    String hql = " select filteredRequirement" + 
                 " from FilteredRequirement filteredRequirement" +
                 " join filteredRequirement.Requirement requirement" +
                 " where filteredRequirement.ProductStructureFK = :productStructureFK" +
                 " and requirement.ManualInd = :manualInd";
                 
      Map params = new HashMap();

      params.put("productStructureFK", productStructureFK);
      
      params.put("manualInd", manualInd);

      List<FilteredRequirement> results = SessionHelper.executeHQL(hql, params, FilteredRequirement.DATABASE);

      return results.toArray(new FilteredRequirement[results.size()]);
  }

  /**
   * @see #contractGroupRequirements
   * @param contractGroupRequirements
   */
  public void setContractGroupRequirements(Set<ContractGroupRequirement> contractGroupRequirements)
  {
    this.contractGroupRequirements = contractGroupRequirements;
  }
  
  /**
   * @see #contractGroupRequirements
   * @return ContractGroupRequirement
   */
  public Set<ContractGroupRequirement> getContractGroupRequirements()
  {
    return contractGroupRequirements;
  }

    /**
     * A convenience method to get the associated ProductStructure (it's
     * in another DB).
     */
    public ProductStructure getProductStructure()
    {
        return ProductStructure.findByPK(getProductStructureFK());
    }


        /**
         * Finder. Includes the associated Requirement entity.
         * @param productStructurePK
         * @return
         */
        public static FilteredRequirement[] findBy_ProductStructurePK_V1(Long productStructurePK)
        {
            String hql = " from FilteredRequirement filteredRequirement" +
                        " join fetch filteredRequirement.Requirement" +
                        " where filteredRequirement.ProductStructureFK = :productStructurePK";
                        
            EDITMap params = new EDITMap("productStructurePK", productStructurePK);
            
            List<FilteredProduct> results = SessionHelper.executeHQL(hql, params, SessionHelper.EDITSOLUTIONS);
            
            return results.toArray(new FilteredRequirement[results.size()]);
        }
}
