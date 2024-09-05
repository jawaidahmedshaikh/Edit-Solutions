/*
 * User: gfrosti
 * Date: Oct 29, 2003
 * Time: 2:25:21 PM
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package agent;

import agent.dm.dao.CommissionProfileDAO;

import edit.common.vo.CommissionProfileVO;
import edit.common.vo.VOObject;

import edit.services.db.CRUDEntity;
import edit.services.db.CRUDEntityImpl;
import edit.services.db.hibernate.SessionHelper;
import edit.services.db.hibernate.HibernateEntity;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.dom4j.Element;



public class CommissionProfile extends HibernateEntity implements CRUDEntity
{
    private CommissionProfileVO commissionProfileVO;
    private CommissionProfileImpl commissionProfileImpl;
    private Set agentGroups;
    private Set contributingProfiles;
    private Set placedAgentCommissionProfiles = new HashSet();
    private Set agentSnapshots;

    /**
     * In support of the SEGElement interface.
     */
    private Element commissionProfileElement;


    public static final String DATABASE = SessionHelper.EDITSOLUTIONS;



    public CommissionProfile()
    {
        commissionProfileVO = new CommissionProfileVO();
        this.commissionProfileImpl = new CommissionProfileImpl();
    }

    public CommissionProfile(CommissionProfileVO commissionProfileVO)
    {
        this();
        this.commissionProfileVO = commissionProfileVO;
    }

    public CommissionProfile(long commissionProfilePK)
    {
        this();
        this.commissionProfileImpl.load(this, commissionProfilePK);
    }

    /**
     * Setter.
     * @param agentGroups
     */
    public void setAgentGroups(Set agentGroups)
    {
        this.agentGroups = agentGroups;
    }

    /**
     * Getter.
     * @return
     */
    public Set getAgentGroups()
    {
        return agentGroups;
    }

    /**
     * Setter.
     * @param contributingProfiles
     */
    public void setContributingProfiles(Set contributingProfiles)
    {
        this.contributingProfiles = contributingProfiles;
    }

    /**
     * Getter.
     * @return
     */
    public Set getContributingProfiles()
    {
        return contributingProfiles;
    }

    public void setAgentSnapshots(Set agentSnapshots)
    {
        this.agentSnapshots = agentSnapshots;
    }

    public Set getAgentSnapshots()
    {
        return agentSnapshots;
    }

    public String getCommissionLevelCT()
    {
        return commissionProfileVO.getCommissionLevelCT();
    }

    //-- java.lang.String getCommissionLevelCT()
    public String getCommissionOptionCT()
    {
        return commissionProfileVO.getCommissionOptionCT();
    }

    //-- java.lang.String getCommissionOptionCT()
    public Long getCommissionProfilePK()
    {
        return SessionHelper.getPKValue(commissionProfileVO.getCommissionProfilePK());
    }

    //-- long getCommissionProfilePK()
    public String getContractCodeCT()
    {
        return commissionProfileVO.getContractCodeCT();
    }

    //-- java.lang.String getContractCodeCT()
    public void setCommissionLevelCT(String commissionLevelCT)
    {
        commissionProfileVO.setCommissionLevelCT(commissionLevelCT);
    }

    //-- void setCommissionLevelCT(java.lang.String)
    public void setCommissionOptionCT(String commissionOptionCT)
    {
        commissionProfileVO.setCommissionOptionCT(commissionOptionCT);
    }

    //-- void setCommissionOptionCT(java.lang.String)
    public void setCommissionProfilePK(Long commissionProfilePK)
    {
        commissionProfileVO.setCommissionProfilePK(SessionHelper.getPKValue(commissionProfilePK));
    }

    //-- void setCommissionProfilePK(long)
    public void setContractCodeCT(String contractCodeCT)
    {
        commissionProfileVO.setContractCodeCT(contractCodeCT);
    }

    //-- void setContractCodeCT(java.lang.String)
    public void setTrailStatus(String trailStatus)
    {
        commissionProfileVO.setTrailStatus(trailStatus);
    }

    //-- void setTrailStatus(java.lang.String)
    public String getTrailStatus()
    {
        return commissionProfileVO.getTrailStatus();
    }

    //-- java.lang.String getTrailStatus()
    public VOObject getVO()
    {
        return commissionProfileVO;
    }

    public void save() throws Exception
    {
        commissionProfileImpl.save(this);
    }

    public void delete() throws Exception
    {
        commissionProfileImpl.delete(this);
    }

    public long getPK()
    {
        return commissionProfileVO.getCommissionProfilePK();
    }

    public void setVO(VOObject voObject)
    {
        this.commissionProfileVO = (CommissionProfileVO) voObject;
    }

    public boolean isNew()
    {
        return commissionProfileImpl.isNew(this);
    }

    public CRUDEntity cloneCRUDEntity()
    {
        return commissionProfileImpl.cloneCRUDEntity(this);
    }

    /**
     * STATELESS Finder.
     * @param contractCodeCT
     * @return
     */
    public static CommissionProfile[] findBy_ContractCodeCT(String contractCodeCT)
    {
        String hql = "from CommissionProfile cp where cp.ContractCodeCT = :contractCodeCT";

        Map params = new HashMap();

        params.put("contractCodeCT", contractCodeCT);

        List results = SessionHelper.executeHQL(hql, params, CommissionProfile.DATABASE);

        return (CommissionProfile[]) results.toArray(new CommissionProfile[results.size()]);
    }

    /**
     * Finder.
     * @param commissionProfilePK
     * @return
     */
    public static CommissionProfile findByPK(long commissionProfilePK)
    {
        CommissionProfile commissionProfile = null;

        CommissionProfileVO[] commissionProfileVOs = new CommissionProfileDAO().findByPK(commissionProfilePK);

        if (commissionProfileVOs != null)
        {
            commissionProfile = new CommissionProfile(commissionProfileVOs[0]);
        }

        return commissionProfile;
    }

    /**
     * Finder.
     * @param commissionProfilePK
     * @return
     */
    public static CommissionProfile findBy_PK(Long commissionProfilePK)
    {
        return (CommissionProfile) SessionHelper.get(CommissionProfile.class, commissionProfilePK, CommissionProfile.DATABASE);
    }

    /**
     * Finder.
     * @return
     */
    public static CommissionProfile[] findAll()
    {
        CommissionProfileVO[] commissionProfileVOs = new CommissionProfileDAO().getAllCommissionProfiles();

        return (CommissionProfile[]) CRUDEntityImpl.mapVOToEntity(commissionProfileVOs, CommissionProfile.class);
    }

    /**
     * Finder (Hibernate).
     * @return
     */
    public static CommissionProfile[] find_All()
    {
        String hql = "from CommissionProfile";

        List results = SessionHelper.executeHQL(hql, null, CommissionProfile.DATABASE);

        return (CommissionProfile[]) results.toArray(new CommissionProfile[results.size()]);
    }

    /**
     * Finder.
     * @param commissionProfilePK
     * @return
     */
    public static final CommissionProfile findBy_CommissionProfilePK(Long commissionProfilePK)
    {
        return (CommissionProfile) SessionHelper.get(CommissionProfile.class, commissionProfilePK, CommissionProfile.DATABASE);
    }

    /**
     * Adder.
     * @param agentGroup
     */
    public void addAgentGroup(AgentGroup agentGroup)
    {
        getAgentGroups().add(agentGroup);

        agentGroup.setCommissionProfile(this);
    }

    /**
     * Remover.
     * @param agentGroup
     */
    public void removeAgentGroup(AgentGroup agentGroup)
    {
        getAgentGroups().remove(agentGroup);

        agentGroup.setCommissionProfile(null);
    }

    /**
     * Adder.
     * @param agentGroup
     */
    public void addContributingProfile(ContributingProfile contributingProfile)
    {
        getContributingProfiles().add(contributingProfile);

        contributingProfile.setCommissionProfile(this);
    }

    /**
     * Remover.
     * @param agentGroup
     */
    public void removeContributingProfile(ContributingProfile contributingProfile)
    {
        getContributingProfiles().remove(contributingProfile);

        contributingProfile.setCommissionProfile(null);
    }

    /**
     * @see interface#hSave()
     */
    public void hSave()
    {
        SessionHelper.saveOrUpdate(this, CommissionProfile.DATABASE);
    }

    /**
     * @see interface#hDelete()
     */
    public void hDelete()
    {
        SessionHelper.delete(this, CommissionProfile.DATABASE);
    }

    public void onCreate()
    {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public Set getPlacedAgentCommissionProfiles()
    {
        return placedAgentCommissionProfiles;
    }

    public void setPlacedAgentCommissionProfiles(Set placedAgentCommissionProfiles)
    {
        this.placedAgentCommissionProfiles = placedAgentCommissionProfiles;
    }

    /**
     * Adder.
     */
    public void add(PlacedAgentCommissionProfile placedAgentCommissionProfile)
    {
        getPlacedAgentCommissionProfiles().add(placedAgentCommissionProfile);
        
        placedAgentCommissionProfile.setCommissionProfile(this);
    }

    /**
     * Remover.
     */
    public void remove(PlacedAgentCommissionProfile placedAgentCommissionProfile)
    {
        getPlacedAgentCommissionProfiles().remove(placedAgentCommissionProfile);
        
        placedAgentCommissionProfile.setCommissionProfile(null);
    }

    /**
     * Finds the CommissionProfile via the PlacedAgentCommissionProfile with the 
     * PlacedAgentCommisisonProfile.StartDate <= date <= PlacedAgentCommissionProfile.StopDate.
     */
    public static CommissionProfileVO findActiveCommissionProfileVO(long placedAgentPK, String date)
    {
        CommissionProfileVO commissionProfileVO = null;
        
        CommissionProfileVO[] commissionProfileVOs = new CommissionProfileDAO().findBy_PlacedAgentPK_StopStartDate(placedAgentPK, date);
        
        if (commissionProfileVOs != null)
        {
            commissionProfileVO = commissionProfileVOs[0];
        }
        
        return commissionProfileVO;
    }


    public String getDatabase()
    {
        return CommissionProfile.DATABASE;
    }
}
