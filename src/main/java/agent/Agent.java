/*
 * User: gfrosti
 * Date: Oct 30, 2003
 * Time: 9:34:18 AM
 *
 * (c) 2000-2008 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
 package agent;

import agent.dm.dao.*;

import client.*;

import edit.common.*;

import edit.common.exceptions.*;

import edit.common.vo.*;

import edit.services.db.*;
import edit.services.db.hibernate.*;
import edit.services.logging.Logging;

import role.*;

import java.util.*;

import org.dom4j.Element;
import org.apache.logging.log4j.Logger;
import contract.*;
import staging.IStaging;
import staging.StagingContext;
import event.business.Event;
import event.component.EventComponent;
import event.CommissionHistory;
import logging.LogEvent;
import fission.utility.Util;


public class Agent extends HibernateEntity implements CRUDEntity, IStaging
{
    private AgentVO agentVO;

    private AgentImpl agentImpl;

    private Set<AgentContract> agentContracts = new HashSet<AgentContract>();
    
    private Set agentLicenses = new HashSet();

    private Set redirects = new HashSet();

    private Set agentGroups = new HashSet();

    private Set<CheckAdjustment> checkAdjustments = new HashSet<CheckAdjustment>();

    private Set agentRequirements = new HashSet();

    private Set<AgentHierarchy> agentHierarchies = new HashSet();
    private Set<ClientRole> clientRoles = new HashSet();

    /**
     * In support of SEGElement.
     */
    private Element agentElement;

    public static final String DATABASE = SessionHelper.EDITSOLUTIONS;


    public Agent()
    {
        agentVO = new AgentVO();
        agentImpl = new AgentImpl();
    }

    public Agent(long agentPK)
    {
        this();
        agentImpl.load(this, agentPK);
    }

    public Agent(AgentVO agentVO)
    {
        this();
        this.agentVO = agentVO;
    }

    /**
     * Getter.
     * @return
     */
    public String getAgentNumber()
    {
        return agentVO.getAgentNumber();
    }

    /**
     * Setter.
     * @param agentNumber
     */
    public void setAgentNumber(String agentNumber)
    {
        agentVO.setAgentNumber(agentNumber);
    }

    /**
     * Updates this Agent's ClientRoleFinancial
     * @param commissionAmount
     * @param adaAmount
     * @param expenseAmount
     * @param redirectEffectiveDate
     * @throws Exception
     */
    public void updateAgentCommissions(EDITBigDecimal commissionAmount, EDITBigDecimal adaAmount, 
                                       EDITBigDecimal expenseAmount, String redirectEffectiveDate, 
                                       EDITBigDecimal commissionTaxable, String commissionTypeCT, 
                                       EDITBigDecimal advanceAmount, EDITBigDecimal debitBalanceAmount,
                                       long checkTo, long placedAgentFK) throws Exception
    {
        commissionAmount = commissionAmount.addEditBigDecimal(adaAmount);

        commissionAmount = commissionAmount.addEditBigDecimal(expenseAmount);

        agentImpl.updateAgentCommissions(this, commissionAmount, redirectEffectiveDate, commissionTaxable, commissionTypeCT, advanceAmount, debitBalanceAmount, checkTo, placedAgentFK);
    }

    /**
     * Updates this Agent's Bonus Balance on ClientRoleFinancial
     * @param bonusCommissionAmount
     * @param redirectEffectiveDate
     * @param commissionTaxable
     * @param commissionTypeCT
     * @throws Exception
     */
    public void updateBonusCommissions(EDITBigDecimal bonusCommissionAmount, 
                                       String redirectEffectiveDate, 
                                       EDITBigDecimal commissionTaxable, 
                                       String commissionTypeCT,
                                       long placedAgentFK) throws Exception
    {
        agentImpl.updateBonusCommissions(this, bonusCommissionAmount, redirectEffectiveDate, commissionTaxable, commissionTypeCT, placedAgentFK);
    }

    public boolean holdingCommissions()
    {
        return (agentVO.getHoldCommStatus().equals("Y")) ? true : false;
    }

    public void save() throws Exception
    {
        SessionHelper.beginTransaction(Agent.DATABASE);

        hSave();

        agentImpl.save(this);

        SessionHelper.commitTransaction(Agent.DATABASE);
    }

    public void delete() throws Exception
    {
        agentImpl.delete(this);
    }

    public VOObject getVO()
    {
        return agentVO;
    }

    public long getPK()
    {
        return agentVO.getAgentPK();
    }

    public void setVO(VOObject voObject)
    {
        this.agentVO = (AgentVO)voObject;
    }

    public boolean isNew()
    {
        return agentImpl.isNew(this);
    }

    public CRUDEntity cloneCRUDEntity()
    {
        return agentImpl.cloneCRUDEntity(this);
    }

    public AgentContract[] getAgentContract()
    {
        return AgentContract.findByAgentPK(getPK());
    }

    public void validateAgentLicense(String trxEffectiveDate, 
                                     String issueState, 
                                     long productStructureFK,
                                     String enrollmentMethod,
                                     String productType) throws EDITValidationException
    {
        agentImpl.validateAgentLicense(this, trxEffectiveDate, issueState, productStructureFK, enrollmentMethod, productType);
    }

    public PlacedAgentBranch[] getPlacedAgentBranches()
    {
        List placedAgentBranches = new ArrayList();

        Set agentContracts = getAgentContracts();

        Iterator it = agentContracts.iterator();

        while (it.hasNext())
        {
            AgentContract agentContract = (AgentContract)it.next();

            PlacedAgentBranch[] placedAgentBranch = agentContract.getPlacedAgentBranches();

            if (placedAgentBranch.length != 0)
            {
                placedAgentBranches.addAll(Arrays.asList(placedAgentBranch));
            }
        }

        return (PlacedAgentBranch[])placedAgentBranches.toArray(new PlacedAgentBranch[placedAgentBranches.size()]);
    }

    /**
     * Getter.
     * @return
     */
    public EDITDate getHireDate()
    {
        return SessionHelper.getEDITDate(agentVO.getHireDate());
    }

    /**
     * Filters all existing PlacedAgentBraches by the filterDate. If a PlacedAgentBranch is emptied because of the
     * filtering process, that branch is not included in the return set.
     * @param filterDate
     * @return
     */
    public PlacedAgentBranch[] getPlacedAgentBranches(EDITDate filterDate)
    {
        List branchesToKeep = new ArrayList();

        PlacedAgentBranch[] placedAgentBranches = getPlacedAgentBranches();

        if (placedAgentBranches != null)
        {
            for (int i = 0; i < placedAgentBranches.length; i++)
            {
                if (placedAgentBranches[i].filter(filterDate))
                {
                    branchesToKeep.add(placedAgentBranches[i]);
                }
            }
        }

        if (branchesToKeep.isEmpty())
        {
            placedAgentBranches = null;
        }
        else
        {
            placedAgentBranches = (PlacedAgentBranch[])branchesToKeep.toArray(new PlacedAgentBranch[branchesToKeep.size()]);
        }

        return placedAgentBranches;
    }

    /**
     * Finder.
     * @param clientRolePK
     * @return
     */
    public static final Agent findByClientRolePK(long clientRolePK)
    {
        Agent agent = null;

        AgentVO[] agentVOs = new AgentDAO().findByClientRolePK(clientRolePK);

        if (agentVOs != null)
        {
            agent = new Agent(agentVOs[0]);
        }

        return agent;
    }

    /**
     *
     * @param clientRoleFinancialPK
     * @return
     */
    public static final Agent findByClientRoleFinancialPK(long clientRoleFinancialPK)
    {
        Agent agent = null;

        AgentVO[] agentVOs = new AgentDAO().findByClientRoleFinancialPK(clientRoleFinancialPK);

        if (agentVOs != null)
        {
            agent = new Agent(agentVOs[0]);
        }

        return agent;
    }

    /**
     * Finder.
     * @param participatingAgentPK
     * @return
     */
    public static final Agent findBy_ParticipatingAgentPK(long participatingAgentPK)
    {
        Agent agent = null;

        AgentVO[] agentVOs = new AgentDAO().findBy_ParticipatingAgentPK(participatingAgentPK);

        if (agentVOs != null)
        {
            agent = new Agent(agentVOs[0]);
        }

        return agent;
    }

    /**
     * Getter. If the DB value null, the disbursementAddressTypeCT value is assumed to be ClientDetail.CLIENT_PRIMARY_ADDRESS.
     * @see ClientDetail
     * @return
     */
    public String getDisbursementAddressTypeCT()
    {
        return agentVO.getDisbursementAddressTypeCT();
    }

    public Long getAgentPK()
    {
        return SessionHelper.getPKValue(agentVO.getAgentPK());
    }
    //-- long getAgentPK()

    public String getAgentStatusCT()
    {
        return agentVO.getAgentStatusCT();
    }
    //-- java.lang.String getAgentStatusCT()

    public String getAgentTypeCT()
    {
        return agentVO.getAgentTypeCT();
    }
    //-- java.lang.String getAgentTypeCT()

    public String getBranch()
    {
        return agentVO.getBranch();
    }
    //-- java.lang.String getBranch()

    public String getCorrespondenceAddressTypeCT()
    {
        return agentVO.getCorrespondenceAddressTypeCT();
    }
    //-- java.lang.String getCorrespondenceAddressTypeCT()

    public void setCorrespondenceAddressTypeCT(String correspondenceAddressTypeCT)
    {
        agentVO.setCorrespondenceAddressTypeCT(correspondenceAddressTypeCT);
    }

    public String getDepartment()
    {
        return agentVO.getDepartment();
    }
    //-- java.lang.String getDepartment()

    public String getHoldCommStatus()
    {
        return agentVO.getHoldCommStatus();
    }
    //-- java.lang.String getHoldCommStatus()

    public String getIntDebitBalStatusCT()
    {
        return agentVO.getIntDebitBalStatusCT();
    }
    //-- java.lang.String getIntDebitBalStatusCT()

    public EDITDateTime getMaintDateTime()
    {
        return SessionHelper.getEDITDateTime(agentVO.getMaintDateTime());
    }
    //-- java.lang.String getMaintDateTime()

    public String getOperator()
    {
        return agentVO.getOperator();
    }
    //-- java.lang.String getOperator()

    public String getRegion()
    {
        return agentVO.getRegion();
    }
    //-- java.lang.String getRegion()

    public EDITDate getTerminationDate()
    {
        return SessionHelper.getEDITDate(agentVO.getTerminationDate());
    }
    //-- java.lang.String getTerminationDate()

    public String getWithholdingStatus()
    {
        return agentVO.getWithholdingStatus();
    }
    //-- java.lang.String getWithholdingStatus()

    /**
     * Getter.
     * @return
     */
    public Long getCompanyFK()
    {
        return SessionHelper.getPKValue(agentVO.getCompanyFK());
    }

    public void setAgentPK(Long agentPK)
    {
        agentVO.setAgentPK(SessionHelper.getPKValue(agentPK));
    }
    //-- void setAgentPK(long)

    public void setAgentStatusCT(String agentStatusCT)
    {
        agentVO.setAgentStatusCT(agentStatusCT);
    }
    //-- void setAgentStatusCT(java.lang.String)

    public void setAgentTypeCT(String agentTypeCT)
    {
        agentVO.setAgentTypeCT(agentTypeCT);
    }
    //-- void setAgentTypeCT(java.lang.String)

    public void setBranch(String branch)
    {
        agentVO.setBranch(branch);
    }
    //-- void setBranch(java.lang.String)

    public void setDepartment(String department)
    {
        agentVO.setDepartment(department);
    }
    //-- void setDepartment(java.lang.String)

    public void setDisbursementAddressTypeCT(String disbursementAddressTypeCT)
    {
        agentVO.setDisbursementAddressTypeCT(disbursementAddressTypeCT);
    }
    //-- void setDisbursementAddressTypeCT(java.lang.String)

    public void setHireDate(EDITDate hireDate)
    {
        agentVO.setHireDate(SessionHelper.getEDITDate(hireDate));
    }
    //-- void setHireDate(java.lang.String)

    public void setHoldCommStatus(String holdCommStatus)
    {
        agentVO.setHoldCommStatus(holdCommStatus);
    }
    //-- void setHoldCommStatus(java.lang.String)

    public void setIntDebitBalStatusCT(String intDebitBalStatusCT)
    {
        agentVO.setIntDebitBalStatusCT(intDebitBalStatusCT);
    }
    //-- void setIntDebitBalStatusCT(java.lang.String)

    public void setMaintDateTime(EDITDateTime maintDateTime)
    {
        agentVO.setMaintDateTime(SessionHelper.getEDITDateTime(maintDateTime));
    }
    //-- void setMaintDateTime(java.lang.String)

    public void setOperator(String operator)
    {
        agentVO.setOperator(operator);
    }
    //-- void setOperator(java.lang.String)

    public void setRegion(String region)
    {
        agentVO.setRegion(region);
    }
    //-- void setRegion(java.lang.String)

    public void setTerminationDate(EDITDate terminationDate)
    {
        agentVO.setTerminationDate(SessionHelper.getEDITDate(terminationDate));
    }
    //-- void setTerminationDate(java.lang.String)

    public void setWithholdingStatus(String withholdingStatus)
    {
        agentVO.setWithholdingStatus(withholdingStatus);
    }
    //-- void setWithholdingStatus(java.lang.String)

    /**
     * Setter
     * @param companyFK
     */
    public void setCompanyFK(Long companyFK)
    {
        agentVO.setCompanyFK(SessionHelper.getPKValue(companyFK));
    }

    /**
     * Getter
     * @return  set of agentContracts
     */
    public Set<AgentContract> getAgentContracts()
    {
        return agentContracts;
    }

    /**
     * Setter
     * @param agentContracts      set of agentContracts
     */
    public void setAgentContracts(Set<AgentContract> agentContracts)
    {
        this.agentContracts = agentContracts;
    }

    /**
     * Finder.
     *
     * @param agentPK
     */
    public static final Agent findByPK(long agentPK)
    {
        Agent agent = null;

        AgentVO[] agentVOs = new AgentDAO().findByAgentPK(agentPK);

        if (agentVOs != null)
        {
            agent = new Agent(agentVOs[0]);
        }

        return agent;
    }

    /**
     * Adds a AgentContract to the set of children
     * @param agentContract
     */
    public void addAgentContract(AgentContract agentContract)
    {
        this.getAgentContracts().add(agentContract);

        agentContract.setAgent(this);

        SessionHelper.saveOrUpdate(agentContract, Agent.DATABASE);
    }

    /**
     * Removes a AgentContract from the set of children
     * @param agentContract
     */
    public void removeAgentContract(AgentContract agentContract)
    {
        this.getAgentContracts().remove(agentContract);

        agentContract.setAgent(null);

        SessionHelper.saveOrUpdate(agentContract, Agent.DATABASE);
    }

    /**
     * Getter
     * @return  set of agentLicenses
     */
    public Set getAgentLicenses()
    {
        return agentLicenses;
    }

    /**
     * Setter
     * @param agentLicenses      set of agentLicenses
     */
    public void setAgentLicenses(Set agentLicenses)
    {
        this.agentLicenses = agentLicenses;
    }

    /**
     * Adds a AgentLicense to the set of children
     * @param agentLicense
     */
    public void addAgentLicense(AgentLicense agentLicense)
    {
        this.getAgentLicenses().add(agentLicense);

        agentLicense.setAgent(this);

        SessionHelper.saveOrUpdate(agentLicense, Agent.DATABASE);
    }

    /**
     * Removes a AgentLicense from the set of children
     * @param agentLicense
     */
    public void removeAgentLicense(AgentLicense agentLicense)
    {
        this.getAgentLicenses().remove(agentLicense);

        agentLicense.setAgent(null);

        SessionHelper.saveOrUpdate(agentLicense, Agent.DATABASE);
    }

    public Set getRedirects()
    {
        return redirects;
    }

    public void setRedirects(Set redirects)
    {
        this.redirects = redirects;
    }

    /**
     * Getter.
     * @return
     */
    public Set<CheckAdjustment> getCheckAdjustments()
    {
        return checkAdjustments;
    }

    /**
     * Setter.
     * @param checkAdjustments
     */
    public void setCheckAdjustments(Set<CheckAdjustment> checkAdjustments)
    {
        this.checkAdjustments = checkAdjustments;
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

    public Set getAgentHierarchies()
    {
        return this.agentHierarchies;
    }

    public void setAgentHierarchies(Set agentHierarchies)
    {
        this.agentHierarchies = agentHierarchies;
    }

    public Set getClientRoles()
    {
        return this.clientRoles;
    }

    public void setClientRoles(Set clientRoles)
    {
        this.clientRoles = clientRoles;
    }

    public void addClientRole(ClientRole clientRole)
    {
        this.clientRoles.add(clientRole);

        clientRole.setAgent(this);

        SessionHelper.saveOrUpdate(clientRole, DATABASE);
    }

    public static final Agent findByPK(Long agentPK)
    {
        return (Agent)SessionHelper.get(Agent.class, agentPK, Agent.DATABASE);
    }

    /**
     * Save the entity using Hibernate
     */
    public void hSave()
    {
        SessionHelper.saveOrUpdate(this, Agent.DATABASE);
    }

    /**
     * Delete the entity using Hibernate
     */
    public void hDelete()
    {
        SessionHelper.delete(this, Agent.DATABASE);
    }

    /**
     * Finder. This version (V!) includes the associated ClientRole, ClientDetail, AgentContract, PlacedAgents.
     * @param agentNumber
     * @return
     */
    public static Agent findBy_AgentNumber_V1(String agentNumber)
    {
        Agent agent = null;

        String hql = " select agent from Agent agent" +
                     " join fetch agent.ClientRoles clientRole" +
//                     " join fetch clientRole.ClientDetail" +
//                     " join fetch agent.AgentContracts agentContract" +
//                     " join fetch agentContract.PlacedAgents" +
                     " where clientRole.ReferenceID = :agentNumber";

        Map params = new HashMap();

        params.put("agentNumber", agentNumber);

        List results = SessionHelper.executeHQL(hql, params, Agent.DATABASE);

        if (!results.isEmpty())
        {
            agent = (Agent)results.get(0);
        }

        return agent;
    }

    public static Agent findByClientRolePK_Company(ClientRole clientRole, Long companyFK)
    {
        Agent agent = null;

        String hql = " select agent from Agent agent" +
                     " where agent.ClientRoles = :clientRole" +
                     " and agent.CompanyFK = :companyFK";

        Map params = new HashMap();

        params.put("clientRole", clientRole);
        params.put("companyFK", companyFK);

        List results = SessionHelper.executeHQL(hql, params, Agent.DATABASE);

        if (!results.isEmpty())
        {
            agent = (Agent)results.get(0);
        }

        return agent;
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
     * Adder.
     * @param agentGroup
     */
    public void addAgentGroup(AgentGroup agentGroup)
    {
        getAgentGroups().add(agentGroup);

        agentGroup.setAgent(this);
    }

    /**
     * Remove.
     * @param agentGroup
     */
    public void removeAgentGroup(AgentGroup agentGroup)
    {
        getAgentGroups().remove(agentGroup);

        agentGroup.setAgent(null);
    }

    /**
     * Adder.
     * @param agentRequirement
     */
    public void addAgentRequirement(AgentRequirement agentRequirement)
    {
        getAgentRequirements().add(agentRequirement);

        agentRequirement.setAgent(this);
    }

    /**
     * Finds the Agents with the matching (partial) agentName and includes the associated ClientRole and ClientDetail.
     * @param agentName
     * @return
     */
    public static Agent[] findBy_AgentName_V1(String agentName, EDITDate currentDate)
    {
        String hql = " select agent from Agent agent" +
                     " join fetch agent.ClientRoles clientRole" +
                     " join fetch clientRole.ClientDetail clientDetail" +
                     " where upper(clientDetail.LastName) like upper(:agentName)" +
                     " or upper(clientDetail.CorporateName) like upper(:agentName)" +
                    " and agent.TerminationDate >= :currentDate";

        Map params = new HashMap();

        params.put("agentName", agentName + "%");

        params.put("currentDate", currentDate);

        List results = null;

        results = SessionHelper.executeHQL(hql, params, Agent.DATABASE);

        return (Agent[]) results.toArray(new Agent[results.size()]);
    }

    /**
     * Convenience method to get the ClientDetail's name.
     * @return
     */
    public String getAgentName()
    {
        String agentName = null;

        if (!getClientRoles().isEmpty())
        {
            Set<ClientRole> clientRoles = getClientRoles();
            agentName = clientRoles.iterator().next().getClientDetail().getName();
        }

        return agentName;
    }

    /**
     * Dummy method to complement getAgentName() to satisfy bean conventions.
     * @param theName
     */
    public void setAgentName(String theName)
    {

    }

    /**
     * A "decorator" to the getDisbursementAddressTypeCT() method to modify the return value if it is null. It is
     * necessary to create this decorator because Hibernate was always finding the Agent object to be dirty and
     * was forcing a re-synch with the DB.
     * @return
     */
    public String getDisbursementAddressType()
    {
        String disbursementAddressTypeCT = getDisbursementAddressTypeCT();

        if (disbursementAddressTypeCT == null)
        {
            disbursementAddressTypeCT = ClientAddress.CLIENT_PRIMARY_ADDRESS;
        }

        return disbursementAddressTypeCT;
    }

    public void onCreate()
    {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Deletes the agentRequirement from collection.
     * @param agentRequirement
     */
    public void deleteAgentRequirement(AgentRequirement agentRequirement)
    {
        for (Iterator iterator = agentRequirements.iterator(); iterator.hasNext();)
        {
            AgentRequirement requirement = (AgentRequirement) iterator.next();

            if (agentRequirement.getAgentRequirementPK().longValue() == requirement.getAgentRequirementPK().longValue())
            {
                iterator.remove();
            }
        }
    }

    /**
     * Convenience method to get the ClientDetail
     * @return
     */
    public ClientDetail getClientDetail()
    {
        ClientDetail clientDetail = null;

        if (!getClientRoles().isEmpty())
        {
            Set<ClientRole> clientRoles = getClientRoles();
            clientDetail = clientRoles.iterator().next().getClientDetail();
        }

        return clientDetail;
    }

    /**
     * Finder.
     * @param agentPK
     * @param state
     * @return
     */
    public static Agent findBy_PK(Long agentPK)
    {
        return (Agent) SessionHelper.get(Agent.class, agentPK, Agent.DATABASE);
    }

    public static Agent findByAgentContract(Long agentContractFK)
    {
        Agent agent = null;

        AgentVO[] agentVOs = new AgentDAO().findByAgentContractPK(agentContractFK.longValue());

        if (agentVOs != null)
        {
            agent = new Agent(agentVOs[0]);
        }

        return agent;
    }
    
    /**
   * Adder.
   * @param checkAdjustment
   */
    public void add(CheckAdjustment checkAdjustment)
    {
      getCheckAdjustments().add(checkAdjustment);
      
      checkAdjustment.setAgent(this);
    }

    public String getDatabase()
    {
        return Agent.DATABASE;
    }

    public StagingContext stage(StagingContext stagingContext, String database)
    {
        staging.Agent stagingAgent = new staging.Agent();
        stagingAgent.setStaging(stagingContext.getStaging());
        stagingAgent.setHireDate(this.getHireDate());
        stagingAgent.setTerminationDate(this.getTerminationDate());
        stagingAgent.setAgentStatus(this.getAgentStatusCT());
        stagingAgent.setAgentType(this.getAgentTypeCT());
        stagingAgent.setDepartment(this.getDepartment());
        stagingAgent.setRegion(this.getRegion());
        stagingAgent.setBranch(this.getBranch());
        stagingAgent.setDisbursementAddressType(this.getDisbursementAddressTypeCT());
        stagingAgent.setCorrespondenceAddressType(this.getCorrespondenceAddressTypeCT());
        stagingAgent.setAgentNumber(stagingContext.getCurrentAgentNumber());

        Set<ClientRole> clientRoles = this.getClientRoles();
        Iterator it = clientRoles.iterator();
        ClientRole clientRole = (ClientRole) it.next();
        ClientDetail clientDetail = clientRole.getClientDetail();
        ClientAddress clientAddress = clientDetail.getPrimaryAddress();
        if (clientAddress == null)
        {
            clientAddress = clientDetail.getBusinessAddress();
        }

        stagingAgent.setLastName(clientDetail.getLastName());
        stagingAgent.setFirstName(clientDetail.getFirstName());
        stagingAgent.setMiddleName(clientDetail.getMiddleName());
        stagingAgent.setTaxIdentification(clientDetail.getTaxIdentification());
        stagingAgent.setCorporateName(clientDetail.getCorporateName());
        if (clientAddress != null)
        {
            stagingAgent.setAddressLine1(clientAddress.getAddressLine1());
            stagingAgent.setAddressLine2(clientAddress.getAddressLine2());
            stagingAgent.setAddressLine3(clientAddress.getAddressLine3());
            stagingAgent.setAddressLine4(clientAddress.getAddressLine4());
            stagingAgent.setCity(clientAddress.getCity());
            stagingAgent.setState(clientAddress.getStateCT());
            stagingAgent.setZip(clientAddress.getZipCode());
        }

        Preference preference = clientRole.getPreference();
        if (preference != null)
        {
            stagingAgent.setMinimumCheckAmount(preference.getMinimumCheck());
        }

        stagingAgent.setManualAdjustments(new EDITBigDecimal());

        stagingAgent.setHoldCheckIndicator(this.getHoldCommStatus());

        ContactInformation contactInformation = clientDetail.getPrimaryContactInformation();
        if (contactInformation != null)
        {
            stagingAgent.setPhoneNumber(contactInformation.getPhoneEmail());
        }

        if (!stagingContext.getEventType().equalsIgnoreCase(StagingContext.GENERAL_LEDGER_STAGING_EVENT) &&
            !stagingContext.getEventType().equalsIgnoreCase(StagingContext.CLIENT_ACCOUNTING_STAGING_EVENT) &&
            !stagingContext.getEventType().equalsIgnoreCase(StagingContext.COMMISSION_STATEMENT))
        {
            stagingAgent.setSegmentBase(stagingContext.getCurrentSegmentBase());
            stagingContext.getCurrentSegmentBase().addAgent(stagingAgent);
        }

        stagingContext.setCurrentAgent(stagingAgent);

        SessionHelper.saveOrUpdate(stagingAgent, database);

        return stagingContext;
    }

    /**
     * Validates that the specified agentNumber is valid for this Agent.
    * In order for this to be true, the following rules apply:
    * 1. a. If this Agent.AgentNumber is null, then the specified agentNumber
    *    must not be null.
    *    b. The specified specified agentNumber must be unique across all [other]
    *       Agent.ClientRole.ReferenceId (although it may be repeated for this Agent).
    *    
    * 2. If this Agent.AgentNumber in not null, then the specified agentNumber
    *    must be null.
     * @param agentNumber
     * @return
     */
    public boolean validAgentNumber(String agentNumber) 
    {
        boolean agentNumberIsValid = false;
        
        if (getAgentNumber() == null) 
        {
            if (agentNumber != null) 
            {
                if (agentNumberIsUnique(agentNumber)) 
                {
                    agentNumberIsValid = true;    
                }
            }
        }
        else 
        {
            if (agentNumber == null)
            {
                agentNumberIsValid = true;
            }
        }
        
        return agentNumberIsValid;
    }
    
    /**
     * Checks to see if the specified agentNumber is unqiue when compared
     * to all [other] Agent.ClientRole.ReferenceId(s).
     * @return true if the specified agentNumber is unique, false otherwise
     */
    private boolean agentNumberIsUnique(String agentNumber) 
    {
        boolean agentNumberIsUnique = false;
        
        int clientRoleCount = ClientRole.findCountBy_AgentPK_ReferenceId_Not(getAgentPK(), agentNumber);
        
        agentNumberIsUnique = (clientRoleCount == 0);
        
        return agentNumberIsUnique;
    }
}
