/*
 * User: gfrosti
 * Date: Nov 4, 2003
 * Time: 11:27:01 AM
 *
 * (c) 2000-2008 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package role;

import agent.Agent;
import agent.AgentContract;
import agent.PlacedAgent;

import client.*;
import contract.*;

import edit.common.*;

import edit.common.vo.*;
import edit.common.*;

import client.ClientDetail;

import contract.ContractClient;
import contract.Segment;

import edit.common.vo.*;
import edit.common.*;

import edit.services.db.CRUDEntity;
import edit.services.db.ConnectionFactory;
import edit.services.db.hibernate.*;

import org.dom4j.Element;

import role.dm.dao.*;
import role.dm.dao.FastDAO;

import java.util.*;

import event.*;

import group.*;

import org.hibernate.Session;


public class ClientRole extends HibernateEntity implements CRUDEntity
{
    public static final String ROLETYPECT_OWNER = "OWN";
    public static final String ROLETYPECT_INSURED = "Insured";
    public static final String ROLETYPECT_ANNUITANT = "ANN";
    public static final String ROLETYPECT_PRIMARYBENEFICIARY = "PBE";
    public static final String ROLETYPECT_CONTINGENTBENEFICIARY = "CBE";
    public static final String ROLETYPECT_PAYOR = "POR";
    public static final String ROLETYPECT_AGENT = "Agent";
    public static final String ROLETYPECT_DECEASED = "Deceased";
    public static final String PRIMARY_OVERRIDESTATUS = "P";
    public static final String ROLETYPECT_PAYEE = "PAY";
    public static final String ROLETYPECT_SECONDARY_OWNER = "SOW";
    public static final String ROLETYPECT_SECONDARY_ANNUITANT = "SAN";
    public static final String ROLETYPECT_CASE = "Case";
    public static final String ROLETYPECT_GROUP = "Group";
    public static final String ROLETYPECT_ASSIGNEE = "Assignee";
    public static final String ROLETYPECT_FULL_ASSIGNEE = "FASS";
    public static final String ROLETYPECT_COLLATERAL_ASSIGNEE = "CASS";
    public static final String ROLETYPECT_IRREVOCABLE_BENEFICIARY = "IBE";
    public static final String ROLETYPECT_SECONDARY_ADDRESSEE = "SADD";
    public static final String ROLETYPECT_DEPENDENT = "DEP";
    public static final String ROLETYPECT_AGENT_SERVICING = "AgentServicing";
    public static final String ROLETYPECT_AGENT_LEAD = "AgentLead";
    public static final String ROLETYPECT_TERM_INSURED = "TermInsured";
    
    
    public static final String OVERRIDE_STATUS_PRIMARY = "P";
    public static final String OVERRIDE_STATUS_OVERRIDE = "0";

    public static final String[] BENEFICIARY_ROLES = {ROLETYPECT_PRIMARYBENEFICIARY,
                                                      ROLETYPECT_CONTINGENTBENEFICIARY,
                                                      ROLETYPECT_IRREVOCABLE_BENEFICIARY,
                                                      ROLETYPECT_COLLATERAL_ASSIGNEE};

    public static final String [] BENEFICIARY_SECONDARY_OWNER ={ROLETYPECT_PRIMARYBENEFICIARY,
                                                                ROLETYPECT_CONTINGENTBENEFICIARY,
                                                                ROLETYPECT_SECONDARY_OWNER};
    
    public static final String[] BATCH_IMPORT_BENE_ROLES = {ROLETYPECT_PRIMARYBENEFICIARY,
												      ROLETYPECT_CONTINGENTBENEFICIARY};

    public static final String DATABASE = SessionHelper.EDITSOLUTIONS;

    private ClientRoleVO clientRoleVO;
    private ClientRoleImpl clientRoleImpl;
    private Set<ContractClient> contractClients;
    private ClientDetail clientDetail;
    private Long clientRolePK;
    private ContractClient contractClient;
    private Segment segment;
    private String changeHistoryEffDate;
    private Set clientSetups;
    private Set clientRoleFinancials;
    private Set<ContractGroup> contractGroups;
    private Set<EnrollmentLeadServiceAgent> enrollmentLeadServiceAgents;
    private Set<PlacedAgent> placedAgents;

    private TaxProfile taxProfile;
    private Preference preference;
    private Agent agent;

    /**
     * In support of the SEGElement interface.
     */
    private Element clientRoleElement;
    
    public ClientRole()
    {
        init();
    }

    public ClientRole(long clientRolePK)
    {
        this();
        this.clientRoleImpl.load(this, clientRolePK);
    }

    public ClientRole(ClientRoleVO clientRoleVO)
    {
        this();
        this.clientRoleVO = clientRoleVO;
    }

    public ClientRole(ClientRole oldClientRole, String roleTypeCT)
    {
        this.clientRoleVO = (ClientRoleVO) oldClientRole.getVO();
        clientRoleVO.setClientRolePK(0);
        clientRoleVO.setRoleTypeCT(roleTypeCT);
    }

    /**
     * Constructor that takes a roleType and clientDetail.
     * @param roleTypeCT
     * @param clientDetail
     */
    public ClientRole(String roleTypeCT, String overrideStatus, ClientDetail clientDetail)
    {
        this();

        this.clientRoleVO = new ClientRoleVO();     // only need because still using VOs for this object

        this.setRoleTypeCT(roleTypeCT);
        this.setOverrideStatus(overrideStatus);
        this.setClientDetail(clientDetail);

        this.setClientDetailFK(clientDetail.getClientDetailPK());   // only need because still using VOs
    }

    private void init()
    {
        this.clientSetups  = new HashSet();
        this.contractClients = new HashSet();
        this.clientRoleImpl = new ClientRoleImpl();
        this.clientRoleVO = new ClientRoleVO();
        this.clientRoleFinancials = new HashSet();
        this.contractGroups = new HashSet<ContractGroup>();
        this.enrollmentLeadServiceAgents = new HashSet<EnrollmentLeadServiceAgent>();
        this.placedAgents = new HashSet<PlacedAgent>();
    }

    /**
     * Setter.
     * @param roleTypeCT
     */
    public void setRoleTypeCT(String roleTypeCT)
    {
        clientRoleVO.setRoleTypeCT(roleTypeCT);
    }
    //-- void setRoleTypeCT(java.lang.String)

    /**
     * Getter.
     * @return
     */
    public Long getClientRolePK()
    {
        return SessionHelper.getPKValue(clientRoleVO.getClientRolePK());
    }

    /**
     * Setter.
     * @param clientRolePK
     */
    public void setClientRolePK(Long clientRolePK)
    {
        this.clientRoleVO.setClientRolePK(SessionHelper.getPKValue(clientRolePK));
    }

    /**
     * Getter.
     * @return
     */
    public String getOverrideStatus()
    {
        return clientRoleVO.getOverrideStatus();
    }

    //-- java.lang.String getOverrideStatus()

    /**
     * Setter.
     * @param overrideStatus
     */
    public void setOverrideStatus(String overrideStatus)
    {
        clientRoleVO.setOverrideStatus(overrideStatus);
    }

    //-- void setOverrideStatus(java.lang.String)

    /**
     * Setter.
     * @param clientDetail
     */
    public void setClientDetail(ClientDetail clientDetail)
    {
        this.clientDetail = clientDetail;
    }

    /**
     * Getter
     * @return  set of contractClients
     */
    public Set<ContractClient> getContractClients()
    {
        return contractClients;
    }

    /**
     * Setter
     * @param contractClients      set of contractClients
     */
    public void setContractClients(Set<ContractClient> contractClients)
    {
        this.contractClients = contractClients;
    }

    public Long getClientDetailFK()
    {
        return SessionHelper.getPKValue(clientRoleVO.getClientDetailFK());
    }

    /**
     * Adds a ContractClient to the set of children
     * @param contractClient
     */
    public void addContractClient(ContractClient contractClient)
    {
        this.getContractClients().add(contractClient);

        contractClient.setClientRole(this);

        SessionHelper.saveOrUpdate(contractClient, DATABASE);
    }

    /**
     * Removes a ContractClient from the set of children
     * @param contractClient
     */
    public void removeContractClient(ContractClient contractClient)
    {
        this.getContractClients().remove(contractClient);

        contractClient.setClientRole(null);

        SessionHelper.saveOrUpdate(contractClient, DATABASE);
    }



    public VOObject getVO()
    {
        return clientRoleVO;
    }

    public void save(String operator)
    {
        this.clientRoleImpl.save(this);
    }

    public void save()
    {
        this.clientRoleImpl.save(this);
    }

    public void delete() throws Exception
    {
        this.clientRoleImpl.delete(this);
    }

    public long getPK()
    {
        return clientRoleVO.getClientRolePK();
    }

    public void setVO(VOObject voObject)
    {
        this.clientRoleVO = (ClientRoleVO) voObject;
    }

    public boolean isNew()
    {
        return this.clientRoleImpl.isNew(this);
    }

    public CRUDEntity cloneCRUDEntity()
    {
        return this.clientRoleImpl.cloneCRUDEntity(this);
    }

    public String getRoleTypeCT()
    {
        return this.clientRoleVO.getRoleTypeCT();
    }

    /**
       * getter
       * @return
       */
      public EDITDate getNewIssuesEligibilityStartDate()
      {
          return  SessionHelper.getEDITDate(clientRoleVO.getNewIssuesEligibilityStartDate());
      }

      //-- java.lang.String getNewIssuesEligibilityStartDate()

      /**
       * getter
       * @return
       */
      public String getNewIssuesEligibilityStatusCT()
      {
          return clientRoleVO.getNewIssuesEligibilityStatusCT();
      }

      //-- java.lang.String getNewIssuesEligibilityStatusCT()

      /**
       * setter
       * @param newIssuesEligibilityStartDate
       */
      public void setNewIssuesEligibilityStartDate(EDITDate newIssueEligibilityStartDate)
      {
          clientRoleVO.setNewIssuesEligibilityStartDate(SessionHelper.getEDITDate(newIssueEligibilityStartDate));
      }

      //-- void setNewIssueEligibilityStartDate(java.lang.String)

      /**
       * setter
       * @param newIssuesEligibilityStatus
       */
      public void setNewIssuesEligibilityStatusCT(String newIssuesEligibilityStatus)
      {
          clientRoleVO.setNewIssuesEligibilityStatusCT(newIssuesEligibilityStatus);
      }

      //-- void setNewIssuesEligibilityStatusCT(java.lang.String)

    /**
     * Getter.
     * @return
     */
    public String getReferenceID()
    {
        return clientRoleVO.getReferenceID();
    }

    /**
     * Setter.
     * @param referenceID
     */
    public void setReferenceID(String referenceID)
    {
        clientRoleVO.setReferenceID(referenceID);
    }

    public ClientRoleFinancial getClientRoleFinancial()
    {
        ClientRoleFinancial[] clientRoleFinancial = ClientRoleFinancial.findByClientRolePK(getPK());

        if (clientRoleFinancial != null)
        {
            return clientRoleFinancial[0];
        }
        else
        {
            return null;
        }
    }

    /**
     * Removes a PlacedAgent from the set of children
     * @param placedAgent
     */
    public void removePlacedAgent(PlacedAgent placedAgent)
    {
        this.getPlacedAgents().remove(placedAgent);

        placedAgent.setClientRole(null);

        SessionHelper.saveOrUpdate(placedAgent, ClientRole.DATABASE);
    }

    public static ClientRole findByPlacedAgentPK(long placedAgentPK)
    {
        ClientRole clientRole = null;

        long clientRolePK = new FastDAO().findClientRolePKByPlacedAgentPK(placedAgentPK);

        if (clientRolePK != 0)
        {
            clientRole = new ClientRole(clientRolePK);
        }

        return clientRole;
    }

    /**
     * Returns the associated ClientDetail.
     * @return the ClientDetail
     */
    public ClientDetail get_ClientDetail()
    {
        if (clientDetail == null)
        {
            clientDetail = new ClientDetail(clientRoleVO.getClientDetailFK()); // For CRUD
        }

        return clientDetail;
    }

    /**
     * Getter.
     * @return
     */
    public ClientDetail getClientDetail()
    {
        return clientDetail;
    }

    /**
     * Finder.
     * @param contractClient
     * @return
     */
    public static ClientRole findBy_ContractClient(ContractClient contractClient)
    {
        ClientRole clientRole = null;

        clientRole = contractClient.getClientRole();

        SessionHelper.initialize(clientRole);

        return clientRole;
    }

    /**
     * Finder.
     * RoleType of 'Agent' are not retrieved.
     * Requirement for casetracking module.
     * @param clientDetail
     * @param roleTypeCT
     * @return
     */
    public static final ClientRole[] findBy_ClientDetail_Not_Like_RoleTypeCT(ClientDetail clientDetail, String roleTypeCT)
    {
        String hql = "select cr from ClientRole cr inner join cr.ClientDetail cd where cd = :clientDetail" +
                        " and cr.RoleTypeCT not like :roleTypeCT and cr.OverrideStatus = :overrideStatus";

        Map params = new HashMap();

        params.put("clientDetail", clientDetail);
        params.put("roleTypeCT", roleTypeCT);
        params.put("overrideStatus", ClientRole.PRIMARY_OVERRIDESTATUS);

        List results = SessionHelper.executeHQL(hql, params, DATABASE);

        return (ClientRole[]) results.toArray(new ClientRole[results.size()]);
    }

    /**
     * Finder.
     * @param clientDetail
     * @param roleTypeCT
     * @return
     */
    public static final ClientRole findBy_ClientDetail_RoleTypeCT(ClientDetail clientDetail, String roleTypeCT)
    {
        String hql = "select cr from ClientRole cr inner join cr.ClientDetail cd where cd = :clientDetail" +
                        " and cr.RoleTypeCT = :roleTypeCT and cr.OverrideStatus = :overrideStatus";

        Map params = new HashMap();

        params.put("clientDetail", clientDetail);
        params.put("roleTypeCT", roleTypeCT);
        params.put("overrideStatus", ClientRole.PRIMARY_OVERRIDESTATUS);

        List results = SessionHelper.executeHQL(hql, params, DATABASE);

        ClientRole clientRole = null;

        if (!results.isEmpty())
        {
            clientRole = (ClientRole) results.get(0);
        }

        return clientRole;
    }

    /**
     * Finder.
     * @param clientDetail
     * @param roleTypeCT
     * @return
     */
    public static final ClientRole findBy_ClientDetail_RoleTypeCT_ReferenceID(ClientDetail clientDetail, String roleTypeCT, String referenceID)
    {
        String hql = "select cr from ClientRole cr inner join cr.ClientDetail cd where cd = :clientDetail" +
                        " and cr.RoleTypeCT = :roleTypeCT and cr.OverrideStatus = :overrideStatus" +
                        " and cr.ReferenceID = :referenceID";

        Map params = new HashMap();

        params.put("clientDetail", clientDetail);
        params.put("roleTypeCT", roleTypeCT);
        params.put("overrideStatus", ClientRole.PRIMARY_OVERRIDESTATUS);
        params.put("referenceID", referenceID);

        List results = SessionHelper.executeHQL(hql, params, DATABASE);

        ClientRole clientRole = null;

        if (!results.isEmpty())
        {
            clientRole = (ClientRole) results.get(0);
        }

        return clientRole;
    }

    /**
     * Finder.
     * @param clientDetail
     * @param roleTypeCT
     * @return
     */
    public static final ClientRole findBy_ClientDetail_RoleTypeCT_NULLReferenceID(ClientDetail clientDetail, String roleTypeCT)
    {
        String hql = "select cr from ClientRole cr " +
                     "inner join cr.ClientDetail cd " +
                     "where cd = :clientDetail" +
                     " and cr.RoleTypeCT = :roleTypeCT " +
                     " and cr.OverrideStatus = :overrideStatus" +
                     " and cr.ReferenceID is null";

        Map params = new HashMap();

        params.put("clientDetail", clientDetail);
        params.put("roleTypeCT", roleTypeCT);
        params.put("overrideStatus", ClientRole.PRIMARY_OVERRIDESTATUS);

        List results = SessionHelper.executeHQL(hql, params, DATABASE);

        ClientRole clientRole = null;

        if (!results.isEmpty())
        {
            clientRole = (ClientRole) results.get(0);
        }

        return clientRole;
    }

    /**
     * Finder.
     * @param clientDetail
     * @param roleTypeCT
     * @return
     */
    public static final ClientRole[] findBy_RoleType_ReferenceID(String roleType, String referenceID)
    {
        String hql = "select cr from ClientRole cr" +
                     " where cr.RoleTypeCT = :roleType" +
                     " and cr.ReferenceID = :referenceID";

        Map params = new HashMap();

        params.put("roleType", roleType);
        params.put("referenceID", referenceID);

        List results = SessionHelper.executeHQL(hql, params, DATABASE);

        return (ClientRole[]) results.toArray(new ClientRole[results.size()]);
    }
    
    /**
     * Finder by ReferenceID.
     * @param referenceID
     * @return
     */
    public static final ClientRole[] findBy_ReferenceID(String referenceID)
    {
        String hql = "select clientRole from ClientRole clientRole" +
                     " where clientRole.ReferenceID = :referenceID";

        Map params = new HashMap();

        params.put("referenceID", referenceID);

        List results = SessionHelper.executeHQL(hql, params, DATABASE);

        return (ClientRole[]) results.toArray(new ClientRole[results.size()]);
    }

    /**
     * Finder using CRUD
     * @param clientRolePK
     * @return
     */
    public static final ClientRole findByPK(long clientRolePK)
    {
        ClientRole clientRole = null;

        ClientRoleVO[] clientRoleVOs = new ClientRoleDAO().findByClientRolePK(clientRolePK, false, null);

        if (clientRoleVOs != null)
        {
            clientRole = new ClientRole(clientRoleVOs[0]);
        }

        return  clientRole;
    }


    /**
     * @param clientRolePK
     * @return
     */
    public static final ClientRole findByPK(Long clientRolePK)
    {
        return (ClientRole) SessionHelper.get(ClientRole.class, clientRolePK, SessionHelper.EDITSOLUTIONS);
    }

    public Change checkForNonFinancialChanges(String operator)
    {
        ChangeProcessor changeProcessor = new ChangeProcessor();
        Change[] changes = changeProcessor.checkForChanges(clientRoleVO, clientRoleVO.getVoShouldBeDeleted(), ConnectionFactory.EDITSOLUTIONS_POOL, null);
        String roleType = this.getRoleTypeCT();
        long clientDetailPK = 0;
        Change change = null;

        if (clientDetail != null)
        {
            clientDetailPK =  this.getClientDetail().getPK();
        }
        else
        {
            clientDetailPK = clientRoleVO.getClientDetailFK();
        }

        if (changes != null)
        {
            for (int i = 0; i < changes.length; i++)
            {
                //DeathPending status change might be setting this date
                if ((changeHistoryEffDate != null) && !changeHistoryEffDate.equals(""))
                {
                    changes[i].setEffectiveDate(changeHistoryEffDate);
                }
                changeProcessor.processForChanges(changes[i], this, operator, roleType, clientDetailPK);

                // only interested when column 'NewIssuesEligibilityStatusCT' is changed for OWNER role.
                if (roleType.equals(ROLETYPECT_OWNER) && changes[i].getFieldName().equalsIgnoreCase("NewIssuesEligibilityStatusCT"))
                {
                    change = changes[i];
                }
            }
        }

        return change;
    }

    /**
     * Getter.
     * @return
     */
    public Set getClientSetups()
    {
        return clientSetups;
    }

    /**
     * Setter.
     * @param clientSetups
     */
    public void setClientSetups(Set clientSetups)
    {
        this.clientSetups = clientSetups;
    }

    /**
     * Getter.
     * @return
     */
    public Set<ContractGroup> getContractGroups()
    {
        return contractGroups;
    }

    /**
     * Setter.
     * @param contractGroups
     */
    public void setContractGroups(Set<ContractGroup> contractGroups)
    {
        this.contractGroups = contractGroups;
    }

    /**
     * Getter.
     * @return
     */
    public Set<PlacedAgent> getPlacedAgents()
    {
        return placedAgents;
    }

    /**
     * Setter.
     * @param placedAgents
     */
    public void setPlacedAgents(Set<PlacedAgent> placedAgents)
    {
        this.placedAgents = placedAgents;
    }
    
    /**
     * Filters the current set of PlacedAgents by the specified AgentContract.
     * @param agentContract
     * @return 
     */
    public Set<PlacedAgent> filterPlacedAgentsByAgentContract(AgentContract agentContract)
    {
        Set<PlacedAgent> filteredPlacedAgents = new HashSet<PlacedAgent>();
        
        for (PlacedAgent currentPlacedAgent:getPlacedAgents())
        {
            if (currentPlacedAgent.getAgentContractFK().longValue() == agentContract.getAgentContractPK().longValue())
            {
                filteredPlacedAgents.add(currentPlacedAgent);
            }
        }
        
        return filteredPlacedAgents;
    }

    /**
     * Adds a PlacedAgent to the set of children
     * @param placedAgent
     */
    public void addPlacedAgent(PlacedAgent placedAgent)
    {
        this.getPlacedAgents().add(placedAgent);

        placedAgent.setClientRole(this);

        SessionHelper.saveOrUpdate(placedAgent, DATABASE);
    }

    /**
     * Adds a ClientSetup to the set of children
     * @param clientSetup
     */
    public void addClientSetup(ClientSetup clientSetup)
    {
        this.getClientSetups().add(clientSetup);

        clientSetup.setClientRole(this);

        SessionHelper.saveOrUpdate(clientSetup, DATABASE);
    }

    /**
     * Removes a ClientSetup from the set of children
     * @param clientSetup
     */
    public void removeClientSetup(ClientSetup clientSetup)
    {
        this.getClientSetups().remove(clientSetup);

        clientSetup.setClientRole(null);

        SessionHelper.saveOrUpdate(clientSetup, DATABASE);
    }

//    public Long getClientDetailFK()
//    {
//        return SessionHelper.getPKValue(clientRoleVO.getClientDetailFK());
//    }

    public void setClientDetailFK(Long clientDetailFK)
    {
        clientRoleVO.setClientDetailFK(SessionHelper.getPKValue(clientDetailFK));
    }

    public Long getPreferenceFK()
    {
        return SessionHelper.getPKValue(clientRoleVO.getPreferenceFK());
    }

    public void setPreferenceFK(Long preferenceFK)
    {
        clientRoleVO.setPreferenceFK(SessionHelper.getPKValue(preferenceFK));
    }

    public Long getTaxProfileFK()
    {
        return SessionHelper.getPKValue(clientRoleVO.getTaxProfileFK());
    }

    public void setTaxProfileFK(Long taxProfileFK)
    {
        clientRoleVO.setTaxProfileFK(SessionHelper.getPKValue(taxProfileFK));
    }

    public Long getAgentFK()
    {
        return SessionHelper.getPKValue(clientRoleVO.getAgentFK());
    }

    public void setAgentFK(Long agentFK)
    {
        clientRoleVO.setAgentFK(SessionHelper.getPKValue(agentFK));
    }

    public TaxProfile getTaxProfile()
    {
        return taxProfile;
    }

    public void setTaxProfile(TaxProfile taxProfile)
    {
        this.taxProfile = taxProfile;
    }

    public Preference getPreference()
    {
        return preference;
    }

    public void setPreference(Preference preference)
    {
        this.preference = preference;
    }

    public Agent getAgent()
    {
        return agent;
    }

    public void setAgent(Agent agent)
    {
        this.agent = agent;
    }

    /**
     * Finder.
     * @param clientRolePK
     * @return
     */
    public static ClientRole findBy_ClientRolePK(Long clientRolePK)
    {
        return (ClientRole) SessionHelper.get(ClientRole.class, clientRolePK, DATABASE);
    }

    public static ClientRole[] findByPreferenceFK(Long preferenceFK)
    {
        String hql = " select clientRole from ClientRole clientRole" +
                     " where clientRole.PreferenceFK = :preferenceFK";

        Map params = new HashMap();
        params.put("preferenceFK", preferenceFK);

        List results = SessionHelper.executeHQL(hql, params, ClientRole.DATABASE);

        return (ClientRole[]) results.toArray(new ClientRole[results.size()]);
    }

    public static ClientRole[] findByAgentFK(Long agentFK)
    {
        String hql = " select clientRole from ClientRole clientRole" +
                     " where clientRole.AgentFK = :agentFK";

        Map params = new HashMap();
        params.put("agentFK", agentFK);

        List results = SessionHelper.executeHQL(hql, params, ClientRole.DATABASE);

        return (ClientRole[]) results.toArray(new ClientRole[results.size()]);
    }

    public void hSave()
    {
        SessionHelper.saveOrUpdate(this, DATABASE);
    }

    public void hDelete()
    {
        SessionHelper.delete(this, DATABASE);
    }

    /**
     * Getter.
     * @return
     */
    public Set getClientRoleFinancials()
    {
        return clientRoleFinancials;
    }

    /**
     * Setter.
     * @param clientRoleFinancials
     */
    public void setClientRoleFinancials(Set clientRoleFinancials)
    {
        this.clientRoleFinancials = clientRoleFinancials;
    }
    
    /**
   * Adder.
   * @param clientRoleFinancial
   */
    public void add(ClientRoleFinancial clientRoleFinancial)
    {
      getClientRoleFinancials().add(clientRoleFinancial);
      
      clientRoleFinancial.setClientRole(this);
    }

    public Set<EnrollmentLeadServiceAgent> getEnrollmentLeadServiceAgents()
    {
        return this.enrollmentLeadServiceAgents;
    }

    public void setEnrollmentLeadServiceAgents(Set<EnrollmentLeadServiceAgent> enrollmentLeadServiceAgents)
    {
        this.enrollmentLeadServiceAgents = enrollmentLeadServiceAgents;
    }

    public void addEnrollmentLeadServiceAgent(EnrollmentLeadServiceAgent enrollmentLeadServiceAgent)
    {
        this.enrollmentLeadServiceAgents.add(enrollmentLeadServiceAgent);

        enrollmentLeadServiceAgent.setClientRole(this);
    }

    public void removeEnrollmentLeadServiceAgent(EnrollmentLeadServiceAgent enrollmentLeadServiceAgent)
    {
        this.enrollmentLeadServiceAgents.remove(enrollmentLeadServiceAgent);

        enrollmentLeadServiceAgent.setClientRole(null);
    }

    public void associateAgent(Agent agent)
    {
        this.setAgent(agent);
        hSave();
    }

    public String getDatabase()
    {
        return ClientRole.DATABASE;
    }

    /**
     * Determines if this ClientRole is a beneficiary (any kind) or not
     *
     * @return  true if this ClientRole is a beneficiary, false otherwise
     */
    public boolean isBeneficiary()
    {
        String[] beneficiaryRoles = ClientRole.BENEFICIARY_ROLES;

        for (int i = 0; i < beneficiaryRoles.length; i++)
        {
            if (this.getRoleTypeCT().equals(beneficiaryRoles[i]))
            {
                return true;
            }
        }

        return false;
    }

    /**
     * From the specified roleTypeCT and referenceID, this method:
     * 
     * 1. Checks to see if an existing ClientRole uniquely identified by 
     * the specified roleTypeCT and referenceID exists.
     * 
     * 2. If #1 finds an existing ClientRole, then that one is returned.
     * 
     * 3. If #1 does not find an existing ClientRole, then a new ClientRole
     * is constructed and returned.
     * @param roleTypeCT
     * @param referenceID
     * @param agent
     * @param clientDetail
     * @return
     */
    public static ClientRole build(String roleTypeCT, String referenceID, Agent agent, ClientDetail clientDetail) 
    {
        ClientRole clientRole = ClientRole.findBy_Agent_RoleTypeCT_ReferenceID(agent, roleTypeCT, referenceID);
        
        if (clientRole == null) 
        {
            clientRole = new ClientRole(); 
            
            clientRole.setRoleTypeCT(roleTypeCT);
            
            clientRole.setReferenceID(referenceID);
            
            clientRole.setOverrideStatus(ClientRole.OVERRIDE_STATUS_PRIMARY);
        }
        
        return clientRole;
    }
    

    /**
     * Finder. The referenceID may be null which is taken into consideration.
     * @param agent
     * @param roleTypeCT
     * @param referenceID
     * @return
     */
    public static ClientRole findBy_Agent_RoleTypeCT_ReferenceID(Agent agent, 
                                                                 String roleTypeCT, 
                                                                 String referenceID) 
    {
        ClientRole clientRole = null;
    
        String hql = " select clientRole" + 
                    "  from ClientRole clientRole" +        
                    " join clientRole.Agent agent" +
                    " where agent = :agent" +
                    " and clientRole.RoleTypeCT = :roleTypeCT" +
                    " and " +
                    " (clientRole.ReferenceID = :referenceID" +
                    " or clientRole.ReferenceID is null)";
                    
        Map params = new EDITMap("roleTypeCT", roleTypeCT).put("agent", agent).put("referenceID", referenceID);                    
        
        List<ClientRole> results = SessionHelper.executeHQL(hql, params, ClientRole.DATABASE);
        
        if (!results.isEmpty()) 
        {
            clientRole = results.get(0);    
        }
        
        return clientRole;
    }
    
    /**
     * Finder. The referenceID may be null which is taken into consideration.
     * @param agentPK
     * @param roleTypeCT
     * @param referenceID
     * @return
     */
    public static ClientRole findBy_AgentPK_RoleTypeCT_ReferenceID(Long agentPK, 
                                                                 String roleTypeCT, 
                                                                 String referenceID) 
    {
        ClientRole clientRole = null;
    
        String hql = " select clientRole" + 
                    "  from ClientRole clientRole" +        
                    " join clientRole.Agent agent" +
                    " where agent.AgentPK = :agentPK" +
                    " and clientRole.RoleTypeCT = :roleTypeCT" +
                    " and " +
                    " (clientRole.ReferenceID = :referenceID" +
                    " or clientRole.ReferenceID is null)";
                    
        Map params = new EDITMap("roleTypeCT", roleTypeCT).put("agentPK", agentPK).put("referenceID", referenceID);                    
        
        List<ClientRole> results = SessionHelper.executeHQL(hql, params, ClientRole.DATABASE);
        
        if (!results.isEmpty()) 
        {
            clientRole = results.get(0);    
        }
        
        return clientRole;
    }
    
    /**
     * Finder by AgentContract
     * @param agentContract
     * @return
     */
    public static ClientRole[] findBy_AgentContract(AgentContract agentContract) 
    {
        String hql = " select distinct clientRole" +
                     " from ClientRole clientRole" +
                     " join clientRole.Agent agent" +
                     " join agent.AgentContracts agentContracts" +
                     " where agentContracts = :agentContract" +
                     " and clientRole.RoleTypeCT = :roleTypeCT";

        Map params = new HashMap();

        params.put("agentContract", agentContract);
        params.put("roleTypeCT", "Agent");

        List<ClientRole> results = SessionHelper.executeHQL(hql, params, ClientRole.DATABASE);

        return (ClientRole[]) results.toArray(new ClientRole[results.size()]);
    }
    
    /**
     * Finder by AgentContract
     * @param agentContract
     * @return
     */
    public static ClientRole[] findBy_AgentContract_PlacedAgent(AgentContract agentContract) 
    {
        String hql = " select distinct clientRole" +
                     " from ClientRole clientRole" +
                     " join clientRole.PlacedAgents placedAgent " +
                     " join placedAgent.AgentContract agentContracts" +
                     " left join clientRole.Agent agent " +
                     " where agentContracts = :agentContract" +
                     " and clientRole.RoleTypeCT = :roleTypeCT";
        
        Map params = new HashMap();

        params.put("agentContract", agentContract);
        params.put("roleTypeCT", "Agent");

        List<ClientRole> results = SessionHelper.executeHQL(hql, params, ClientRole.DATABASE);

        return (ClientRole[]) results.toArray(new ClientRole[results.size()]);
    }

    /**
     * Finder. Includes associated ClientDetail and its associated entities of
     * TaxInformation, TaxProfiles, ClientAddress as well as possible overrides via
     * ClientRole of Preference and TaxProfile.
     * 
     * This is done within a separate Hibernate Session.
     * @param clientRolePK
     * @return
     */
     public static ClientRole findSeparateBy_ClientRolePK_V1(Long clientRolePK)
     {
        ClientRole clientRole = null;

        String hql = 
            " select clientRole" + 
            " from ClientRole clientRole" + 
            " left join fetch clientRole.Preference" + 
            " left join fetch clientRole.TaxProfile" + 
            " join fetch clientRole.ClientDetail clientDetail" + 
            " left join fetch clientDetail.ClientAddresses clientAddress" + 
            " left join fetch clientDetail.Preferences" + 
            " left join fetch clientDetail.TaxInformations taxInformation" + 
            " left join fetch taxInformation.TaxProfiles" + 
            " where clientRole.ClientRolePK = :clientRolePK";

        Session session = null;

        List<ClientRole> results = null;

        try
        {
            session = 
                    SessionHelper.getSeparateSession(SessionHelper.EDITSOLUTIONS);

            Map params = new EDITMap("clientRolePK", clientRolePK);

            results = 
                    SessionHelper.executeHQL(session, hql, params, 0);
        }
        finally
        {
            if (session != null)
                session.close();
        }

        if (!results.isEmpty())
        {
            clientRole = results.get(0);
        }

        return clientRole;
    }

    /**
     * Finds the number of ClientRoles whose ClientRole.AgentFK is NOT
     * equal to the specified agentFK, and whose ClientRole.ReferenceId is
     * the same as the specified referenceId.
     * @param agentFK
     * @param referenceID
     * @return
     */
    public static int findCountBy_AgentPK_ReferenceId_Not(Long agentFK, 
                                                           String referenceID) 
    {
        int clientRoleCount = 0;

        String hql = "select clientRole from ClientRole clientRole" +
                     " where clientRole.AgentFK != :agentFK" +
                     " and clientRole.ReferenceID = :referenceID";

        Map params = new HashMap();

        params.put("agentFK", agentFK);

        params.put("referenceID", referenceID);

        List<ClientRole> clientRoles = SessionHelper.executeHQL(hql, params, DATABASE);

        if (clientRoles.size() > 0)
        {
            Iterator it = clientRoles.iterator();
            while (it.hasNext())
            {
                ClientRole clientRole = (ClientRole) it.next();
                Long crAgentFK = clientRole.getAgentFK();

                Agent crAgent = Agent.findByPK(crAgentFK.longValue());
                if (crAgent != null)
                {
                    clientRoleCount = clientRoleCount + 1;
                }
            }
        }

        return clientRoleCount;
    }

    /**
     * Finds the associated the ClientRole via CommissionHistory.PlacedAgent.ClientRole.
     * @param commissionHistoryPK
     * @return
     */
    public static ClientRole findSeparateBy_CommissionHistoryPK(Long commissionHistoryPK)
    {
        ClientRole clientRole = null;

        String hql = 
            " select clientRole" +
            " from ClientRole clientRole" +
            " join clientRole.PlacedAgents placedAgent" +
            " join placedAgent.CommissionHistories commissionHistory" +
            " where commissionHistory.CommissionHistoryPK = :commissionHistoryPK";

        Session session = null;

        List<ClientRole> results = null;

        try
        {
            session = SessionHelper.getSeparateSession(SessionHelper.EDITSOLUTIONS);

            Map params = new EDITMap("commissionHistoryPK", commissionHistoryPK);

            results = SessionHelper.makeUnique(SessionHelper.executeHQL(session, hql, params, 0));
        }
        finally
        {
            if (session != null)
            {
                session.close();
            }
        }

        if (!results.isEmpty())
        {
            clientRole = results.get(0);
        }

        return clientRole;
    }
}
