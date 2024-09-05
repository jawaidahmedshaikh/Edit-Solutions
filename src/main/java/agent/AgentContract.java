/*
 * User: gfrosti
 * Date: Sep 25, 2003
 * Time: 11:59:40 AM
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package agent;


import edit.common.*;

import edit.common.vo.*;

import edit.services.db.*;
import edit.services.db.hibernate.*;


import role.*;

import java.util.*;

import client.Preference;

import org.dom4j.Element;


public class AgentContract extends HibernateEntity implements CRUDEntity
{
    private AgentContractVO agentContractVO;
    private Agent agent;
    private AgentContractImpl agentContractImpl;
    private Set<PlacedAgent> placedAgents = new HashSet<PlacedAgent>();
    private Set<AdditionalCompensation> additionalCompensations = new HashSet<AdditionalCompensation>();
    
    /**
     * In support of the SEGElement interface.
     */
    private Element agentContractElement;

    public static final String DATABASE = SessionHelper.EDITSOLUTIONS;


    public AgentContract()
    {
        agentContractVO = new AgentContractVO();
        this.agentContractImpl = new AgentContractImpl();
    }

    public AgentContract(long agentContractPK)
    {
        this();
        agentContractImpl.load(this, agentContractPK);
    }

    public AgentContract(AgentContractVO agentContractVO)
    {
        this();
        this.agentContractVO = agentContractVO;
    }

    /**
     * Getter
     * @return  set of placedAgents
     */
    public Set<PlacedAgent> getPlacedAgents()
    {
        return placedAgents;
    }

    /**
     * Setter
     * @param placedAgents      set of placedAgents
     */
    public void setPlacedAgents(Set<PlacedAgent> placedAgents)
    {
        this.placedAgents = placedAgents;
    }

    /**
     * Adds a PlacedAgent to the set of children
     * @param placedAgent
     */
    public void addPlacedAgent(PlacedAgent placedAgent)
    {
        this.getPlacedAgents().add(placedAgent);

        placedAgent.setAgentContract(this);

        SessionHelper.saveOrUpdate(placedAgent, AgentContract.DATABASE);
    }

    /**
     * Removes a PlacedAgent from the set of children
     * @param placedAgent
     */
    public void removePlacedAgent(PlacedAgent placedAgent)
    {
        this.getPlacedAgents().remove(placedAgent);

        placedAgent.setAgentContract(null);

        SessionHelper.saveOrUpdate(placedAgent, AgentContract.DATABASE);
    }


    public Long getAgentContractPK()
    {
        return SessionHelper.getPKValue(agentContractVO.getAgentContractPK());
    }

    //-- long getAgentContractPK() 
    public Long getAgentFK()
    {
        return SessionHelper.getPKValue(agentContractVO.getAgentFK());
    }

    //-- long getAgentFK() 
    public String getCommissionProcessCT()
    {
        return agentContractVO.getCommissionProcessCT();
    }

    //-- java.lang.String getCommissionProcessCT() 
    public String getContractCodeCT()
    {
        return agentContractVO.getContractCodeCT();
    }

    //-- java.lang.String getContractCodeCT() 
    public EDITDate getContractEffectiveDate()
    {
        return SessionHelper.getEDITDate(agentContractVO.getContractEffectiveDate());
    }

    //-- java.lang.String getContractEffectiveDate() 
    public EDITDate getContractStopDate()
    {
        return SessionHelper.getEDITDate(agentContractVO.getContractStopDate());
    }

    //-- java.lang.String getContractStopDate()
    public void setAgentContractPK(Long agentContractPK)
    {
        agentContractVO.setAgentContractPK(SessionHelper.getPKValue(agentContractPK));
    }

    //-- void setAgentContractPK(long) 
    public void setAgentFK(Long agentFK)
    {
        agentContractVO.setAgentFK(SessionHelper.getPKValue(agentFK));
    }

    //-- void setAgentFK(long) 
    public void setCommissionProcessCT(String commissionProcessCT)
    {
        agentContractVO.setCommissionProcessCT(commissionProcessCT);
    }

    //-- void setCommissionProcessCT(java.lang.String) 
    public void setContractCodeCT(String contractCodeCT)
    {
        agentContractVO.setContractCodeCT(contractCodeCT);
    }

    //-- void setContractCodeCT(java.lang.String) 
    public void setContractEffectiveDate(EDITDate contractEffectiveDate)
    {
        agentContractVO.setContractEffectiveDate(SessionHelper.getEDITDate(contractEffectiveDate));
    }

    //-- void setContractEffectiveDate(java.lang.String) 
    public void setContractStopDate(EDITDate contractStopDate)
    {
        agentContractVO.setContractStopDate(SessionHelper.getEDITDate(contractStopDate));
    }
    
    //-- void setContractStopDate(java.lang.String)
    public void save()
    {
        agentContractImpl.save(this);
    }

    public void delete() throws Exception
    {
        agentContractImpl.delete(this);
    }

    public VOObject getVO()
    {
        return agentContractVO;
    }

    public long getPK()
    {
        return agentContractVO.getAgentContractPK();
    }

    public void setVO(VOObject voObject)
    {
        this.agentContractVO = (AgentContractVO) voObject;
    }

    public boolean isNew()
    {
        return agentContractImpl.isNew(this);
    }

    public CRUDEntity cloneCRUDEntity()
    {
        return agentContractImpl.cloneCRUDEntity(this);
    }

    /**
     * Getter
     * @return  set of additionalCompensations
     */
    public Set<AdditionalCompensation> getAdditionalCompensations()
    {
        return additionalCompensations;
    }

    /**
     * Setter
     * @param additionalCompensations      set of additionalCompensations
     */
    public void setAdditionalCompensations(Set<AdditionalCompensation> additionalCompensations)
    {
        this.additionalCompensations = additionalCompensations;
    }

    /**
     * Adds a AdditionalCompensation to the set of children
     * @param additionalCompensation
     */
    public void addAdditionalCompensation(AdditionalCompensation additionalCompensation)
    {
        this.getAdditionalCompensations().add(additionalCompensation);

        additionalCompensation.setAgentContract(this);

        SessionHelper.saveOrUpdate(additionalCompensation, AgentContract.DATABASE);
    }

    /**
     * Removes a AdditionalCompensation from the set of children
     * @param additionalCompensation
     */
    public void removeAdditionalCompensation(AdditionalCompensation additionalCompensation)
    {
        this.getAdditionalCompensations().remove(additionalCompensation);

        additionalCompensation.setAgentContract(null);

        SessionHelper.saveOrUpdate(additionalCompensation, AgentContract.DATABASE);
    }


    /**
     * Getter/CRUD
     * @return
     */
    public PlacedAgent[] getPlacedAgent()
    {
        return PlacedAgent.findByAgentContractPK(getPK());
    }

    public boolean hasPlacedAgent() throws Exception
    {
        boolean hasPlacedAgent = false;
        PlacedAgent[] placedAgent = PlacedAgent.findByAgentContractPK(getPK());

        if ((placedAgent != null) && (placedAgent.length > 0))
        {
            hasPlacedAgent = true;
        }

        return hasPlacedAgent;
    }

    public AdditionalCompensation getAdditionalCompensation() throws Exception
    {
        AdditionalCompensation[] additionalCompensation = AdditionalCompensation.findByAgentContractPK(getPK());

        if (additionalCompensation != null)
        {
            return additionalCompensation[0];
        }
        else
        {
            return null;
        }
    }

    public void associateAgent(Agent agent)
    {
        //Use hibernate to save 10/08/07
//        this.agentContractVO.setAgentFK(agent.getPK());

        this.setAgent(agent);

        hSave();

        //this saves the child
        save();

    }

    /**
     * Getter/CRUD
     * @return
     */
    public Agent get_Agent()
    {
        if (agent == null)
        {
            agent = new Agent(agentContractVO.getAgentFK());
        }

        return agent;
    }

    /**
     * Getter/Hibernate
     * @return
     */
    public Agent getAgent()
    {
        return agent;
    }

    /**
     * Setter.
     * @param agent
     */
    public void setAgent(Agent agent)
    {
        this.agent = agent;
    }

    public static AgentContract[] findByCommissionContractPK(long commissionContractPK) throws Exception
    {
        return AgentContractImpl.findByCommissionContractPK(commissionContractPK);
    }

    public static AgentContract[] findByAgentPK(long agentPK)
    {
        return AgentContractImpl.findByAgentPK(agentPK);
    }

    /**
     * Finds all PlacedAgentBranch(es) associated with this AgentContract.
     * @return the set of PlacedAgentBranch(es), or null
     */
    public PlacedAgentBranch[] getPlacedAgentBranches()
    {
        Set placedAgents = getPlacedAgents();

        List placedAgentBranches = new ArrayList();

        for (Iterator iterator = placedAgents.iterator(); iterator.hasNext();)
        {
            PlacedAgent placedAgent = (PlacedAgent) iterator.next();

            placedAgentBranches.add(new PlacedAgentBranch(placedAgent));
        }

        return (PlacedAgentBranch[]) placedAgentBranches.toArray(new PlacedAgentBranch[placedAgentBranches.size()]);
    }

    /**
     * Determines if a Situation is valid. A valid Situation does not conflict by name or Start and Stop
     * date with any other Situation under a given AgentContract.
     * @return true if the StartDate, StopDate, and Situation are in a valid state for the targeted AgentContract
     */
    public boolean situationConflicts(PlacedAgent placedAgent, String agentNumber)
    {
        boolean situationConflicts = false;

        Set placedAgents = getPlacedAgents();

        if (!placedAgents.isEmpty())
        {
            for (Iterator iterator = placedAgents.iterator(); iterator.hasNext();)
            {
                PlacedAgent currentPlacedAgent = (PlacedAgent) iterator.next();

                situationConflicts = currentPlacedAgent.situationConficts(placedAgent, agentNumber);

                if (situationConflicts)
                {
                    break;
                }
            }
        }
        else
        {
            situationConflicts = false; // There are no other PlacedAgents - there is no conflict.
        }

        return situationConflicts;
    }

    /**
     * Finds the PlacedAgentBranch whose leaf agent has the same SituationCode as the supplied SituationCode, and whose
     * Situation.dateIsInRange(). There is only one PlacedAgentBranch that can satisfy these parameters.
     * @param situationCode
     * @param date
     * @return the active PlacedAgentBranch as determined by the supplied parameters, or null
     */
    public PlacedAgentBranch findActivePlacedAgentBranch(String situationCode, EDITDate date)
    {
        PlacedAgentBranch activePlacedAgentBranch = null;

        Set placedAgents = getPlacedAgents();

        for (Iterator iterator = placedAgents.iterator(); iterator.hasNext();)
        {
            PlacedAgent currentPlacedAgent = (PlacedAgent) iterator.next();

            Situation currentSituation = currentPlacedAgent.getSituation();

            String currentSituationCode = currentSituation.getSituationCode();

            if ((currentSituationCode == situationCode) || ((currentSituationCode != null) && currentSituationCode.equalsIgnoreCase(situationCode)))
            {
                if (currentSituation.dateIsInRange(date))
                {
                    activePlacedAgentBranch = new PlacedAgentBranch(currentPlacedAgent);

                    break;
                }
            }
        }

        return activePlacedAgentBranch;
    }

    /**
     * Returns true if the lastCheckDateTime <= the specified date. The "time" element of lastCheckDate"Time" is ignored.
     * @return
     */
    public boolean shouldCreateCommissionStatement(EDITDate date)
    {
        boolean shouldCreateCommissionStatement;

        ClientRoleFinancial clientRoleFinancial = ClientRoleFinancial.findByAgentContractPK(getPK());

        if (clientRoleFinancial != null)
        {
            EDITDateTime lastCheckDateTime = clientRoleFinancial.getLastCheckDateTime();

            if (lastCheckDateTime != null)
            {
                EDITDate lastCheckDate = lastCheckDateTime.getEDITDate();

                shouldCreateCommissionStatement = lastCheckDate.before(date) || lastCheckDate.equals(date);
            }
            else
            {
                shouldCreateCommissionStatement = true;
            }
        }
        else
        {
            shouldCreateCommissionStatement = false;
        }

        return shouldCreateCommissionStatement;
    }

    /**
     * Finder.
     * @param contractCodeCT
     * @param paymentModeCT
     * @return
     */
    public static final AgentContract[] findBy_ContractCodeCT_AND_PaymentModeCT(String contractCodeCT, String paymentModeCT)
    {
        String hql = " select distinct agentContract" +
                     " from AgentContract agentContract" +
                     " inner join agentContract.Agent agent " +
                     " inner join agent.ClientRoles clientRole" +
                     " inner join clientRole.Preference preference " +
                     " where agentContract.ContractCodeCT = :contractCodeCT" +
                     " and preference.PaymentModeCT = :paymentModeCT";

        Map params = new HashMap();
        params.put("contractCodeCT", contractCodeCT);
        params.put("paymentModeCT", paymentModeCT);

        List results = SessionHelper.executeHQL(hql, params, AgentContract.DATABASE);

        return (AgentContract[]) results.toArray(new AgentContract[results.size()]);
    }

    /**
     * True if the PKs are the same.
     * @param obj
     * @return
     */
    public boolean equals(Object obj)
    {
        boolean equal = false;

        AgentContract visitingAgentContract = (AgentContract) obj;

        if (getAgentContractPK().equals(visitingAgentContract.getAgentContractPK()))
        {
            equal = true;
        }

        return equal;
    }

    /**
     * Finder
     * @param agentContractPK
     * @return
     */
    public static final AgentContract findBy_AgentContractPK(Long agentContractPK)
    {
        return (AgentContract) SessionHelper.get(AgentContract.class, agentContractPK, AgentContract.DATABASE);
    }

    /**
     * STATELESS Finder where the resulting AgentContract has Agent, ClientRole, and ClientDetail attached.
     * @param agentName
     * @param contractCodeCT
     * @return
     */
    public static final AgentContract[] findBy_AgentName_And_ContractCodeCT(String agentName, String contractCodeCT)
    {
        String hql = " select ac from AgentContract ac " +
                    " join fetch ac.Agent a" +
                    " join fetch a.ClientRoles cr" +
                    " join fetch cr.ClientDetail cd" +
                    " where ac.ContractCodeCT = :contractCodeCT" +
                    " and ((cd.LastName like :lastName" + " and cd.TrustTypeCT = 'Individual')" +
                    " or (cd.CorporateName like :corporateName " +
                    " and (cd.TrustTypeCT = 'Corporate' or cd.TrustTypeCT = 'LLC')))";
        Map params = new HashMap();
        params.put("contractCodeCT", contractCodeCT);
        params.put("lastName", agentName + "%");
        params.put("corporateName", agentName + "%");

        List results = SessionHelper.executeHQL(hql, params, AgentContract.DATABASE);

        return (AgentContract[]) results.toArray(new AgentContract[results.size()]);
    }

    /**
     * STATELESS Finder
     * @param agentFK
     * @return
     */
    public static final AgentContract[] findBy_AgentFK(Long agentFK)
    {
        String hql = " select ac from AgentContract ac " +
                    " where ac.AgentFK = :agentFK";

        Map params = new HashMap();
        params.put("agentFK", agentFK);

        List results = SessionHelper.executeHQL(hql, params, AgentContract.DATABASE);

        return (AgentContract[]) results.toArray(new AgentContract[results.size()]);
    }

    /**
     * STATELESS Finder where the resulting AgentContract contains Agent, ClientRole, and ClientDetail.
     * @param agentId
     * @param contractCodeCT
     * @return
     */
    public static final AgentContract findBy_AgentId_AND_ContractCodeCT(String agentId, String contractCodeCT)
    {
        AgentContract agentContract = null;

//        String hql = " select ac from AgentContract ac " +
//                        " join fetch ac.Agent a" +
//                        " join fetch a.ClientRole cr " +
//                        " join fetch cr.ClientDetail" +
//                        " where ac.ContractCodeCT = :contractCodeCT" +
//                        " and a.AgentNumber = :agentNumber";
        String hql = " select distinct ac from AgentContract ac " +
                        " join fetch ac.Agent a" +
                        " join fetch a.ClientRoles cr " +
                        " join fetch cr.ClientDetail" +
                        " left join fetch ac.PlacedAgents pa" +
                        " left join fetch pa.ClientRole pacr" +
                        " where ac.ContractCodeCT = :contractCodeCT" +
                        " and (a.AgentNumber = :agentNumber" +
                        " or (pa.AgentContract = ac" +
                        " and pacr.ReferenceID = :agentNumber))";

        Map params = new HashMap();
        params.put("contractCodeCT", contractCodeCT);
        params.put("agentNumber", agentId);

        List results = SessionHelper.executeHQL(hql, params, AgentContract.DATABASE);

        if (!results.isEmpty())
        {
            agentContract = (AgentContract) results.get(0);
        }

        return agentContract;
    }


    /**
     * Save the entity using Hibernate
     */
    public void hSave()
    {
        SessionHelper.saveOrUpdate(this, AgentContract.DATABASE);
    }

    /**
     * Delete the entity using Hibernate
     */
    public void hDelete()
    {
        SessionHelper.delete(this, AgentContract.DATABASE);

        SessionHelper.saveOrUpdate(this, AgentContract.DATABASE);
    }


    public String getDatabase()
    {
        return AgentContract.DATABASE;
    }
}
