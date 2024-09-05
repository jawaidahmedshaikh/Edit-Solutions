/*
 * User: gfrosti
 * Date: Sep 25, 2003
 * Time: 2:02:25 PM
 *
 * (c) 2000-2008 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package agent.component;

import agent.*;

import agent.business.Agent;

import agent.dm.composer.*;
import agent.dm.dao.DAOFactory;

import client.*;

import edit.common.*;
import edit.common.exceptions.*;
import edit.common.vo.*;

import edit.services.component.*;
import edit.services.db.*;
import edit.services.db.hibernate.*;

import engine.*;

import java.util.*;

import role.*;

import role.dm.dao.*;


public class AgentComponent extends AbstractComponent implements Agent
{
    /*
      * ********************************** Core Business Methods
      * ***************************************
      */
    public void saveAgent(AgentVO agentVO) throws Exception
    {
        agent.Agent agentHibernate = (agent.Agent)SessionHelper.map(agentVO, SessionHelper.EDITSOLUTIONS);
        agentVO.setAgentPK(agentHibernate.getAgentPK());
        agentHibernate.setVO(agentVO);

        try
        {
            agentHibernate.save();
        }
        finally
        {
            SessionHelper.clearSessions();
        }
    }

    public PlacedAgentBranchVO[] getBranchesByAgentContractPK(long agentContractPK) throws Exception
    {
        PlacedAgentBranchVO[] placedAgentBranchVO = null;

        AgentContract agentContract = AgentContract.findBy_AgentContractPK(new Long(agentContractPK));

        PlacedAgentBranch[] placedAgentBranches = agentContract.getPlacedAgentBranches();

        if (placedAgentBranches != null)
        {
            placedAgentBranchVO = new PlacedAgentBranchVO[placedAgentBranches.length];

            for (int i = 0; i < placedAgentBranches.length; i++)
            {
                placedAgentBranchVO[i] = placedAgentBranches[i].getVO();
            }
        }

        return placedAgentBranchVO;
    }

    public PlacedAgentBranchVO[] getBranchesByAgent(agent.Agent agent) throws Exception
    {
        PlacedAgentBranchVO[] placedAgentBranchVO = null;

        PlacedAgentBranch[] placedAgentBranches = agent.getPlacedAgentBranches();

        if (placedAgentBranches != null)
        {
            placedAgentBranchVO = new PlacedAgentBranchVO[placedAgentBranches.length];

            for (int i = 0; i < placedAgentBranches.length; i++)
            {
                PlacedAgent[] currentPlacedAgents = placedAgentBranches[i].getPlacedAgents();

                placedAgentBranchVO[i] = placedAgentBranches[i].getVO();

                PlacedAgentVO[] currentPlacedAgentVOs = placedAgentBranchVO[i].getPlacedAgentVO();

                for (int j = 0; j < currentPlacedAgentVOs.length; j++)
                {
                    PlacedAgentVO currentPlacedAgentVO = currentPlacedAgentVOs[j];

                    PlacedAgent currentPlacedAgent = currentPlacedAgents[j];

                    AgentContract currentAgentContract = currentPlacedAgent.getAgentContract();
                    AgentContractVO currentAgentContractVO = (AgentContractVO) currentAgentContract.getVO();

                    agent.Agent currentAgent = currentAgentContract.getAgent();
                    AgentVO currentAgentVO = (AgentVO) currentAgent.getVO();

                    ClientRole currentClientRole = currentPlacedAgent.getClientRole();
                    ClientRoleVO currentClientRoleVO = (ClientRoleVO) currentClientRole.getVO();

                    ClientDetail currentClientDetail = currentClientRole.getClientDetail();
                    ClientDetailVO currentClientDetailVO = (ClientDetailVO) currentClientDetail.getVO();

                    currentPlacedAgentVO.setParentVO(AgentContractVO.class, currentAgentContractVO);

                    currentAgentContractVO.setParentVO(AgentVO.class, currentAgentVO);

//                    currentAgentVO.setParentVO(ClientRoleVO.class, currentClientRoleVO);
                    currentClientRoleVO.setParentVO(AgentVO.class, currentAgentVO);
                    currentClientRoleVO.setParentVO(ClientDetailVO.class, currentClientDetailVO);
                }
            }
        }

        return placedAgentBranchVO;
    }

    /**
     * @param agentContractPK
     * @param lowestLevelPlacedAgentPK
     * @param placedAgentDetails
     * @param commissionProfilePK
     * @param operator
     * @param agentNumber
     *
     * @return
     *
     * @throws EDITAgentException
     * @see Agent#placeAgentContract(long, long, agent.PlacedAgent, long, String)
     */
    public PlacedAgentBranch placeAgentContract(long agentContractPK, 
                                                long lowestLevelPlacedAgentPK, 
                                                PlacedAgent placedAgentDetails, 
                                                long commissionProfilePK, 
                                                String operator,
                                                String agentNumber) throws EDITAgentException
    {
        try
        {
          synchronized (Hierarchy.AGENT_HIERARCHY_MONITOR)
          {
            SessionHelper.beginTransaction(SessionHelper.EDITSOLUTIONS);
            
//            // Touch these records begin - avoiding a Proxy issue later on by getting these up front.
                PlacedAgent lowestLevelPlacedAgent = (lowestLevelPlacedAgentPK == 0) ? null : PlacedAgent.findBy_PlacedAgentPK(new Long(lowestLevelPlacedAgentPK));
//        
//            lowestLevelPlacedAgent.getGroup_V1();    
//            // Touch these records end
            
                AgentContract agentContract = AgentContract.findBy_AgentContractPK(new Long(agentContractPK));

                agent.Agent agentToBePlaced = agent.Agent.findByPK(agentContract.getAgentFK());
              
                if (!agentToBePlaced.validAgentNumber(agentNumber)) 
                {
                    throw new EDITAgentException(" Invalid Agent Number - 1) It Must be Specified and Unique Across Agents or 2) It Must not be Specified");
                }

                ClientDetail clientDetail = agentToBePlaced.getClientDetail();

                ClientRole clientRole = getAppropriatePlacedAgentClientRole(agentNumber, clientDetail);

                if (clientRole == null)
                {
                    //  An existing ClientRole was not found, create a new one
                    clientRole = createAgentClientRole(agentNumber, clientDetail, placedAgentDetails, agentToBePlaced);

                    clientRole.setAgent(agentToBePlaced);

                    clientDetail.addClientRole(clientRole);

                    clientDetail.hSave();
                    clientRole.hSave();
                }
                else
                {
                    //  An existing ClientRole was found, just set the referenceID (even though it may be the same number)
                    clientRole.setReferenceID(agentNumber);
                    clientRole.hSave();
                }

                placedAgentDetails.setClientRole(clientRole);

                CommissionProfile commissionProfile = CommissionProfile.findBy_PK(new Long(commissionProfilePK));

                // Save now or there will be transient object issue a little later on.
                placedAgentDetails.hSave();

                PlacedAgentCommissionProfile placedAgentCommissionProfile = (PlacedAgentCommissionProfile) SessionHelper.newInstance(PlacedAgentCommissionProfile.class, SessionHelper.EDITSOLUTIONS);

                placedAgentCommissionProfile.setStartDate(placedAgentDetails.getStartDate());

                // Keep this adder - don't use a setter since business logic is required here.
                placedAgentDetails.add(placedAgentCommissionProfile);

//            commissionProfile.add(placedAgentCommissionProfile);
                placedAgentCommissionProfile.setCommissionProfile(commissionProfile); // Performance tune - setter faster than adder

                Hierarchy.getSingleton().placeAgentContract(agentContract, lowestLevelPlacedAgent, placedAgentDetails, operator, agentNumber);

                SessionHelper.commitTransaction(SessionHelper.EDITSOLUTIONS);
          }
        }
        catch (EDITAgentException e)
        {
            SessionHelper.rollbackTransaction(SessionHelper.EDITSOLUTIONS);
        
            System.out.println(e);

            throw e;
        }
        catch (Exception e)
        {
            SessionHelper.rollbackTransaction(SessionHelper.EDITSOLUTIONS);

            System.out.println(e);

            throw new RuntimeException(e);
        }
        finally
        {
            SessionHelper.clearSessions();
        }

        // Reload since the Hibernate session was just cleared.
        placedAgentDetails = PlacedAgent.findBy_PlacedAgentPK(placedAgentDetails.getPlacedAgentPK());

        return new PlacedAgentBranch(placedAgentDetails);
    }

    private String modifyHireDate(String paymentMode, String hireDate)
    {
        EDITDate edHireDate = new EDITDate(hireDate);

        edHireDate = edHireDate.addMode(paymentMode);

        return edHireDate.getFormattedDate();
    }

    public void removePlacedAgent(long placedAgentPK, String operator) throws EDITAgentException
    {
        Long clientRoleFK = null;

        try
        {
            synchronized (Hierarchy.AGENT_HIERARCHY_MONITOR)
            {
                SessionHelper.beginTransaction(SessionHelper.EDITSOLUTIONS);

                PlacedAgent placedAgent = PlacedAgent.findBy_PlacedAgentPK(new Long(placedAgentPK));

                clientRoleFK = placedAgent.getClientRoleFK();

                placedAgent.setOperator(operator);

                Hierarchy.getSingleton().remove(placedAgent);

                SessionHelper.commitTransaction(SessionHelper.EDITSOLUTIONS);
            }
        }
        catch (EDITAgentException e)
        {
            SessionHelper.rollbackTransaction(SessionHelper.EDITSOLUTIONS);

            throw e;
        }
        catch (Exception e)
        {
            SessionHelper.rollbackTransaction(SessionHelper.EDITSOLUTIONS);

            throw new RuntimeException(e);
        }
        finally
        {
            SessionHelper.clearSessions();
        }

        PlacedAgent[] placedAgents = PlacedAgent.findByClientRoleFK(clientRoleFK);
        if (placedAgents.length == 0)
        {
            SessionHelper.beginTransaction(SessionHelper.EDITSOLUTIONS);

            ClientRole clientRole = ClientRole.findByPK(clientRoleFK);

            clientRole.setReferenceID(null);
            SessionHelper.saveOrUpdate(clientRole, SessionHelper.EDITSOLUTIONS);

            SessionHelper.commitTransaction(SessionHelper.EDITSOLUTIONS);
        }
    }

    public PlacedAgentBranchVO shiftAgent(long placedAgentPK, long lowestLevelPlacedAgentPK, int direction, String operator) throws Exception
    {
        PlacedAgentBranch placedAgentBranch = null;

        synchronized (Hierarchy.AGENT_HIERARCHY_MONITOR)
        {
            PlacedAgent placedAgent = new PlacedAgent(placedAgentPK);

            placedAgent.setOperator(operator);

            PlacedAgent lowestLevelPlacedAgent = new PlacedAgent(lowestLevelPlacedAgentPK);

            placedAgentBranch = Hierarchy.getSingleton().shift(lowestLevelPlacedAgent, placedAgent, direction);
        }

        return placedAgentBranch.getVO();
    }

    /**
     *@see Agent#associateCommissionProfile(...)
     */
    public void associateCommissionProfile(Long placedAgentPK, Long commissionProfilePK, EDITDate startDate, EDITDate stopDate) throws EDITAgentException
    {
        try
        {
          synchronized (Hierarchy.AGENT_HIERARCHY_MONITOR)
          {
            SessionHelper.beginTransaction(SessionHelper.EDITSOLUTIONS);

            CommissionProfile commissionProfile = CommissionProfile.findBy_CommissionProfilePK(commissionProfilePK);

            PlacedAgent placedAgent = PlacedAgent.findBy_PlacedAgentPK(placedAgentPK);
       
            PlacedAgentCommissionProfile placedAgentCommissionProfile = null;
            
            placedAgentCommissionProfile = PlacedAgentCommissionProfile.findBy_PlacedAgent_CommissionProfile_StartStopDates(placedAgent, commissionProfile, startDate, stopDate);
            
            if (placedAgentCommissionProfile == null)
            {
                placedAgentCommissionProfile = (PlacedAgentCommissionProfile) SessionHelper.newInstance(PlacedAgentCommissionProfile.class, SessionHelper.EDITSOLUTIONS);    
                
                placedAgentCommissionProfile.setStartDate(startDate);
                
                placedAgentCommissionProfile.setStopDate(stopDate);
            }

            // DON'T performance-tune this with setter versus adder - the adder has business logic.
            placedAgent.add(placedAgentCommissionProfile);
            
//            commissionProfile.add(placedAgentCommissionProfile);
            placedAgentCommissionProfile.setCommissionProfile(commissionProfile); // Performance tune - setter is faster than adder

            SessionHelper.commitTransaction(SessionHelper.EDITSOLUTIONS);
          }
        } 
        catch (EDITAgentException e)
        {
            SessionHelper.rollbackTransaction(SessionHelper.EDITSOLUTIONS);
            
            System.out.println(e);
            
            throw e;
        }
        catch (Exception e)
        {
            SessionHelper.rollbackTransaction(SessionHelper.EDITSOLUTIONS);            

            System.out.println(e);
            
            throw new RuntimeException(e);
        }
        finally
        {
            SessionHelper.clearSessions();
        }
    }

    /**
     * @param companyName
     * @throws Exception
     * @see Agent#updateAgentCommissions(java.lang.String)
     */
    public void updateAgentCommissions(String companyName) throws Exception
    {
        CommissionController commissionController = new CommissionController();

        commissionController.updateAgentCommissions(companyName);
    }

    /**
     * @param contractCodeCT
     * @param processDate
     * @throws Exception
     * @see Agent#generateCommissionStatements(String, String)
     */
    public void generateCommissionStatements(String contractCodeCT, String processDate) throws Exception
    {
        // CommissionStatement commissionStatement = new CommissionStatement();
        //
        // commissionStatement.generateCommissionStatements(contractCodeCT, new
        // EDITDate(processDate));
    }

    /**
     * @param filterAgentId
     * @param commissionAmount
     * @param reduceTaxable
     * @param operator
     * @throws Exception
     * @see Agent#createCommissionAdjustment(java.lang.String,
     *      edit.common.EDITBigDecimal, java.lang.String, java.lang.String)
     */
    public void createCommissionAdjustment(String filterAgentId, EDITBigDecimal commissionAmount, String reduceTaxable, String operator) throws Exception
    {
        CommissionController commissionController = new CommissionController();

        commissionController.createCommissionAdjustment(filterAgentId, commissionAmount, reduceTaxable, operator);
    }

    /**
     * @param contractCodeCT
     * @return
     * @see Agent#generateHierarchyReportByContractCodeCT(String)
     */
    public HierarchyReport generateHierarchyReportByContractCodeCT(String contractCodeCT, boolean includeExpiredAgents)
    {
        HierarchyReport hierarchyReport = new HierarchyReport(includeExpiredAgents, HierarchyReport.BY_CONTRACTCODECT, contractCodeCT);

        hierarchyReport.generateHierarchyReport();

        SessionHelper.clearSessions();

        return hierarchyReport;
    }

    /**
     * @param placedAgentPK
     * @return
     * @see Agent#generateHierarchyReportByPlacedAgent(long)
     */
    public HierarchyReport generateHierarchyReportByPlacedAgent(long placedAgentPK, boolean includeExpiredAgents)
    {
        PlacedAgent placedAgent = PlacedAgent.findBy_PlacedAgentPK(new Long(placedAgentPK));
        
        HierarchyReport hierarchyReport = new HierarchyReport(includeExpiredAgents, HierarchyReport.BY_PLACEDAGENT, placedAgent);

        hierarchyReport.generateHierarchyReport();

        return hierarchyReport;
    }

    /**
     * @param placedAgentPK
     * @param startDate
     * @param stopDate
     * @param situation
     * @param situationChangeReason
     * @param agentNumber
     * @see Agent#updatePlacedAgentDetails(long,String,String,String,String)
     */
    public void updatePlacedAgentDetails(long placedAgentPK, String startDate, String stopDate, String situation, String situationChangeReason, String agentNumber) throws EDITAgentException
    {
        try
        {
            synchronized (Hierarchy.AGENT_HIERARCHY_MONITOR)
            {
                SessionHelper.beginTransaction(SessionHelper.EDITSOLUTIONS);

                PlacedAgent placedAgent = PlacedAgent.findBy_PlacedAgentPK(new Long(placedAgentPK));

                placedAgent.setStartDate(new EDITDate(startDate));

                placedAgent.setStopDate(new EDITDate(stopDate));

                placedAgent.setSituationCode(situation);

                placedAgent.setStopDateReasonCT(situationChangeReason);

                AgentContract agentContract = placedAgent.getAgentContract();

                boolean situationConflicts = agentContract.situationConflicts(placedAgent, agentNumber);

                if (situationConflicts)
                {
                    throw new EDITAgentException("ERROR: Conflicting Situations");
                }

                placedAgent.hSave();

                SessionHelper.commitTransaction(SessionHelper.EDITSOLUTIONS);
            }
        }
        catch (EDITAgentException e)
        {
            SessionHelper.rollbackTransaction(SessionHelper.EDITSOLUTIONS);
          
            System.out.println(e);
      
            e.printStackTrace();

            throw (e);
        }
        catch (Exception e)
        {
            SessionHelper.rollbackTransaction(SessionHelper.EDITSOLUTIONS);

            e.printStackTrace();

            throw new RuntimeException(e);
        }
        finally
        {
            SessionHelper.clearSessions();
        }
    }

    public void updateBonusCommissions(String companyName, String fromDate, String toDate) throws Exception
    {
        CommissionController commissionController = new CommissionController();

        commissionController.updateBonusCommissions(companyName, fromDate, toDate);
    }

    /**
     * @param agentContractPK
     * @param expirationDate
     * @return
     * @throws Exception
     */
    public PlacedAgentBranchVO[] getBranchesByAgentContractPK(long agentContractPK, String expirationDate) throws Exception
    {
        PlacedAgentBranchVO[] placedAgentBranchVO = null;

        AgentContract agentContract = new AgentContract(agentContractPK);

        PlacedAgentBranch[] placedAgentBranch = agentContract.getPlacedAgentBranches();

        if (placedAgentBranch != null)
        {
            placedAgentBranchVO = new PlacedAgentBranchVO[placedAgentBranch.length];

            for (int i = 0; i < placedAgentBranch.length; i++)
            {
                placedAgentBranchVO[i] = placedAgentBranch[i].getVO();
            }
        }

        return placedAgentBranchVO;
    }

    /**
     * @param fromPlacedAgentGroupRootPK
     * @param toPlacedAgentGroupRootPK
     * @param startDate
     * @param stopDateReasonCT
     * @param situationCode
     * @param operator
     * @param agentNumber
     * @see agent.business.Agent#copyPlacedAgentGroup(long,long,String,String,String,String)
     */
    public void copyPlacedAgentGroup(long fromPlacedAgentGroupRootPK, long toPlacedAgentGroupRootPK, String startDate, String stopDateReasonCT, String situationCode, String operator, String agentNumber
                                     ) throws EDITAgentException
    {
        try
        {
            synchronized (Hierarchy.AGENT_HIERARCHY_MONITOR)
            {
                SessionHelper.beginTransaction(SessionHelper.EDITSOLUTIONS);

                EDITDate sDate = new EDITDate(startDate);

                Hierarchy hierarchy = Hierarchy.getSingleton();

                PlacedAgent fromPlacedAgent = PlacedAgent.findBy_PlacedAgentPK(new Long(fromPlacedAgentGroupRootPK));

                PlacedAgent toPlacedAgent = PlacedAgent.findBy_PlacedAgentPK(new Long(toPlacedAgentGroupRootPK));

                hierarchy.copyPlacedAgentGroup(fromPlacedAgent, toPlacedAgent, sDate, stopDateReasonCT, situationCode, operator, agentNumber);

                SessionHelper.commitTransaction(SessionHelper.EDITSOLUTIONS);
            }
        }
        catch (EDITAgentException e)
        {
            SessionHelper.rollbackTransaction(SessionHelper.EDITSOLUTIONS);
            
            System.out.println(e);

            throw e;
        }
        catch (Exception e)
        {
            SessionHelper.rollbackTransaction(SessionHelper.EDITSOLUTIONS);
            
            System.out.println(e);

            throw new RuntimeException(e);
        }
        finally
        {
            SessionHelper.clearSessions();
        }
    }

    /*
      * ********************************** ICRUD Methods
      * ***************************************
      */
    public long createOrUpdateVO(Object voObject, boolean recursively) throws Exception
    {
        return super.createOrUpdateVO(voObject, ConnectionFactory.EDITSOLUTIONS_POOL, recursively);
    }

    public Object retrieveVO(Class voClass, long primaryKey, boolean recursively, List voInclusionList) throws Exception
    {
        return super.retrieveVO(voClass, primaryKey, ConnectionFactory.EDITSOLUTIONS_POOL, false, null);
    }

    public int deleteVO(Class voClass, long primaryKey, boolean recursively) throws Exception
    {
        return super.deleteVO(voClass, primaryKey, ConnectionFactory.EDITSOLUTIONS_POOL, false);
    }

    /*
      * ************************************** Composers
      * *********************************
      */
    public PlacedAgentVO composePlacedAgentVOByPlacedAgentPK(long placedAgentPK, List voInclusionList)
    {
        return new PlacedAgentComposer(voInclusionList).compose(placedAgentPK);
    }

    public PlacedAgentVO[] composePlacedAgentVOByAgentContractFK(long agentContractFK, List voInclusionList) throws Exception
    {
        return new PlacedAgentComposer(voInclusionList).composeByAgentContractFK(agentContractFK);
    }

    public PlacedAgentVO composePlacedAgentVO(PlacedAgentVO placedAgentVO, List voInclusionList)
    {
        new PlacedAgentComposer(voInclusionList).compose(placedAgentVO);

        return placedAgentVO;
    }

    /**
     * @see Agent#findAgentContractVOByAgentName_AND_ContractCodeCT(String,String)
     */
    public AgentContractVO[] findAgentContractVOByAgentName_AND_ContractCodeCT(String agentName, String contractCodeCT)
    {
        return new FastDAO().findAgentContractVOsByAgentName_AND_ContractCodeCT(agentName, contractCodeCT);
    }

    /**
     * @see Agent#findAgentContractVOByAgentId_AND_ContractCodeCT(String,
     *      String)
     */
    public AgentContractVO[] findAgentContractVOByAgentId_AND_ContractCodeCT(String agentId, String contractCodeCT) throws Exception
    {
        return new FastDAO().findAgentContractVOsByAgentId_AND_ContractCodeCT(agentId, contractCodeCT);
    }

    public AgentVO composeAgentVO(long agentPK, List voInclusionList) throws Exception
    {
        return new VOComposer().composeAgentVO(agentPK, voInclusionList);
    }

    public AgentVO composeAgentVOByAgentNumber(String agentNumber, List voInclusionList) throws Exception
    {
        return new VOComposer().composeAgentVOByAgentNumber(agentNumber, voInclusionList);
    }

    public CommissionProfileVO[] composeCommissionProfiles(List voInclusionList) throws Exception
    {
        return new VOComposer().composeCommissionProfiles(voInclusionList);
    }

    public AgentVO[] composeAgentVOByRolePK(long clientRolePK, List voInclusionList)
    {
        return new VOComposer().composeAgentVOByRolePK(clientRolePK, voInclusionList);
    }

    public CommissionProfileVO[] composeCommissionProfileVOByContractCodeCT(String contractCodeCT, List voInclusionList) throws Exception
    {
        return new VOComposer().composeCommissionProfileVOByContractCodeCT(contractCodeCT, voInclusionList);
    }

    public CommissionProfileVO composeCommissionProfileVOByCommissionProfilePK(long commissionProfilePK, List voInclusionList) throws Exception
    {
        return new CommissionProfileComposer(voInclusionList).compose(commissionProfilePK);
    }

    public CommissionProfileVO[] getAllCommissionProfiles() throws Exception
    {
        return DAOFactory.getCommissionProfileDAO().getAllCommissionProfiles();
    }

    public CommissionProfileVO[] getCommissionProfileByPK(long commProfilePK) throws Exception
    {
        return DAOFactory.getCommissionProfileDAO().getCommissionProfileByPK(commProfilePK);
    }

    public long getNextAvailableKey()
    {
        return CRUD.getNextAvailableKey();
    }

    public ElementLockVO lockElement(long agentPK, String username) throws EDITLockException
    {
        ElementLockManager elementLockManager = new ElementLockManager();

        return elementLockManager.lockElement(agentPK, username);
    }

    public int unlockElement(long lockTablePK)
    {
        ElementLockManager elementLockManager = new ElementLockManager();

        return elementLockManager.unlockElement(lockTablePK);
    }

    public AgentVO composeAgentByAgentNumber(String agentNumber, List voInclusionList)
    {
        return new VOComposer().composeAgentVOByAgentNumber(agentNumber, voInclusionList);
    }

    /**
     * @see Agent#composePlacedAgentBranchVO(edit.common.vo.PlacedAgentBranchVO,int)
     */
    public PlacedAgentBranchVO composePlacedAgentBranchVO(PlacedAgentBranchVO placedAgentBranchVO, int depth)
    {
        return new FastDAO().composePlacedAgentBranchVO(placedAgentBranchVO, depth); // To
        // change
        // body
        // of
        // implemented
        // methods
        // use
        // File
        // |
        // Settings | File Templates.
    }

    /**
     * @param agentNumber
     * @param filterDate
     * @return
     * @throws Exception
     * @see Agent#getBranchesByAgentPKAndExpirationDate(long, String)
     */
    public PlacedAgentBranchVO[] getBranchesByAgentNumberAndExpirationDate(String agentNumber, String filterDate) throws Exception
    {
        PlacedAgentBranchVO[] placedAgentBranchVO = null;

        EDITDate filtDate = new EDITDate(filterDate);

        ClientRole[] clientRoles = ClientRole.findBy_RoleType_ReferenceID(ClientRole.ROLETYPECT_AGENT, agentNumber);

        if (clientRoles != null && clientRoles.length > 0)
        {

            PlacedAgent[] placedAgents = PlacedAgent.findBy_ClientRoleFK_AND_StopDate(clientRoles[0].getClientRolePK(), filtDate);

            List placedAgentBranchList = new ArrayList();

            PlacedAgentBranch[] placedAgentBranches = null;

            if (placedAgents != null)
            {
                for (int i = 0; i < placedAgents.length; i++)
                {
                    placedAgentBranchList.add(new PlacedAgentBranch(placedAgents[i]));
                }

                placedAgentBranches = (PlacedAgentBranch[]) placedAgentBranchList.toArray(new PlacedAgentBranch[placedAgentBranchList.size()]);
            }

            placedAgentBranchVO = new PlacedAgentBranchVO[placedAgentBranches.length];

            for (int i = 0; i < placedAgentBranches.length; i++)
            {
                PlacedAgent[] currentPlacedAgents = placedAgentBranches[i].getPlacedAgents();

                placedAgentBranchVO[i] = placedAgentBranches[i].getVO();

                PlacedAgentVO[] currentPlacedAgentVOs = placedAgentBranchVO[i].getPlacedAgentVO();

                for (int j = 0; j < currentPlacedAgentVOs.length; j++)
                {
                    PlacedAgentVO currentPlacedAgentVO = currentPlacedAgentVOs[j];

                    PlacedAgent currentPlacedAgent = currentPlacedAgents[j];

                    AgentContract currentAgentContract = currentPlacedAgent.getAgentContract();
                    AgentContractVO currentAgentContractVO = (AgentContractVO) currentAgentContract.getVO();

                    agent.Agent currentAgent = currentAgentContract.getAgent();
                    AgentVO currentAgentVO = (AgentVO) currentAgent.getVO();

                        ClientRole currentClientRole = currentPlacedAgent.getClientRole();
                    ClientRoleVO currentClientRoleVO = (ClientRoleVO) currentClientRole.getVO();

                    ClientDetail currentClientDetail = currentClientRole.getClientDetail();
                    ClientDetailVO currentClientDetailVO = (ClientDetailVO) currentClientDetail.getVO();

                    currentPlacedAgentVO.setParentVO(AgentContractVO.class, currentAgentContractVO);
                        currentClientRoleVO.setParentVO(ClientDetailVO.class, currentClientDetailVO);
                        currentPlacedAgentVO.setParentVO(ClientRoleVO.class, currentClientRoleVO);

                    currentAgentContractVO.setParentVO(AgentVO.class, currentAgentVO);
                }
            }
        }

        return placedAgentBranchVO;
    }

    /**
     * @param agentNoteVO
     * @see Agent#saveAgentNote(edit.common.vo.AgentNoteVO)
     */
    public void saveAgentNote(AgentNoteVO agentNoteVO)
    {
        AgentNote agentNote = new AgentNote(agentNoteVO);

        agentNote.save();
    }

//    /**
//     * @see Agent#findContributingProductBy_CompanyStructurePK_BonusProgramPK(long, long)
//     * @param companyStructurePK
//     * @param bonusProgramPK
//     * @return
//     */
//    public ContributingProductVO findContributingProductBy_CompanyStructurePK_BonusProgramPK(long companyStructurePK, long bonusProgramPK)
//    {
//        ContributingProductVO contributingProductVO = null;
//
//        ContributingProduct contributingProduct = ContributingProduct.findBy_CompanyStructurePK_BonusProgramPK(companyStructurePK, bonusProgramPK);
//
//        if (contributingProduct != null)
//        {
//            contributingProductVO = (ContributingProductVO) contributingProduct.getVO();
//        }
//
//        return contributingProductVO;
//    }

    /**
     * @see Agent#saveBonusProgram(agent.BonusProgram)
     * @param bonusProgram
     */
    public void saveBonusProgram(BonusProgram bonusProgram)
    {
        try
        {
            SessionHelper.beginTransaction(SessionHelper.EDITSOLUTIONS);

            bonusProgram.hSave();

            SessionHelper.commitTransaction(SessionHelper.EDITSOLUTIONS);
        }
        catch (Exception e)
        {
            SessionHelper.rollbackTransaction(SessionHelper.EDITSOLUTIONS);

            System.out.println(e);

            e.printStackTrace(); //To change body of catch statement use File | Settings | File Templates.

            throw new RuntimeException(e);
        }
        finally
        {
            SessionHelper.clearSessions();
        }
    }

    /**
     * @see Agent#findBonusProgramBy_BonusProgramPK(long)
     * @param bonusProgramPK
     * @return
     */
    public BonusProgram findBonusProgramBy_BonusProgramPK(long bonusProgramPK)
    {
        BonusProgram bonusProgram = BonusProgram.findByPK(new Long(bonusProgramPK));

        return bonusProgram;
    }

    /**
     * @see agent.business.Agent#findAllBonusPrograms()
     * @return
     */
    public BonusProgram[] findAllBonusPrograms()
    {
        return BonusProgram.findAll();
    }

    /**
     * @see Agent#deleteBonusProgram(BonusProgram)
     * @param bonusProgram
     */
    public void deleteBonusProgram(BonusProgram bonusProgram)
    {
        try
        {
            SessionHelper.beginTransaction(SessionHelper.EDITSOLUTIONS);

            bonusProgram.hDelete();

            SessionHelper.commitTransaction(SessionHelper.EDITSOLUTIONS);
        }
        catch (Exception e)
        {
            SessionHelper.rollbackTransaction(SessionHelper.EDITSOLUTIONS);

            System.out.println(e);

            e.printStackTrace(); //To change body of catch statement use File | Settings | File Templates.

            throw new RuntimeException(e);
        }
        finally
        {
            SessionHelper.clearSessions();
        }
    }

    /**
     * @see Agent#findParticipatingAgentsBy_BonusProgramPK(long)
     * @param bonusProgramPK
     * @return
     */
    public ParticipatingAgent[] findParticipatingAgentsBy_BonusProgramPK(long bonusProgramPK)
    {
        return ParticipatingAgent.findBy_BonusProgramPK(bonusProgramPK);
    }

    /**
     * @see Agent#findPlacedAgentsBy_ContractCodeCT_CommissionLevelCT_StartDate_StopDate(String,String,String,String)
     * @param contractCodeCT
     * @param commissionLevelCT
     * @param startDate
     * @param stopDate
     * @return
     */
    public PlacedAgentVO[] findPlacedAgentsBy_ContractCodeCT_CommissionLevelCT_StartDate_StopDate(String contractCodeCT, String commissionLevelCT, String startDate, String stopDate)
    {
        PlacedAgent[] placedAgents = PlacedAgent.findBy_ContractCodeCT_CommissionLevelCT_StartDate_StopDate(contractCodeCT, commissionLevelCT, startDate, stopDate);

        return (PlacedAgentVO[]) CRUDEntityImpl.mapEntityToVO(placedAgents, PlacedAgentVO.class);
    }

    /**
     * @see Agent#findPlacedAgentsBy_CommissionContractCT_AgentNumber_StartDate_StopDate(String,String,String,String)
     * @param contractCodeCT
     * @param agentNumber
     * @param startDate
     * @param stopDate
     * @return
     */
    public PlacedAgentVO[] findPlacedAgentsBy_CommissionContractCT_AgentNumber_StartDate_StopDate(String contractCodeCT, String agentNumber, String startDate, String stopDate)
    {
        PlacedAgent[] placedAgents = PlacedAgent.findBy_CommissionContractCT_AgentNumber_StartDate_StopDate(contractCodeCT, agentNumber, startDate, stopDate);

        return (PlacedAgentVO[]) CRUDEntityImpl.mapEntityToVO(placedAgents, PlacedAgentVO.class);
    }

    /**
     * @see Agent#findB
     * @param contractCodeCT
     * @param agentName
     * @param startDate
     * @param stopDate
     * @return
     */
    public PlacedAgentVO[] findPlacedAgentsBy_CommissionContractCT_AgentName_StartDate_StopDate(String contractCodeCT, String agentName, String startDate, String stopDate)
    {
        PlacedAgent[] placedAgents = PlacedAgent.findBy_CommissionContractCT_AgentName_StartDate_StopDate(contractCodeCT, agentName, startDate, stopDate);

        return (PlacedAgentVO[]) CRUDEntityImpl.mapEntityToVO(placedAgents, PlacedAgentVO.class);
    }

    /**
     * @see Agent#saveParticipatingAgent(ParticipatingAgent, Long, Long)
     * @param participatingAgent
     */
    public void saveParticipatingAgent(ParticipatingAgent participatingAgent, Long bonusProgramPK, Long placedAgentPK)
    {
        BonusProgram bonusProgram = BonusProgram.findByPK(bonusProgramPK);

        PlacedAgent placedAgent = PlacedAgent.findByPK(placedAgentPK);

        try
        {
            SessionHelper.beginTransaction(SessionHelper.EDITSOLUTIONS);

            participatingAgent.setBonusProgram(bonusProgram);

            participatingAgent.setPlacedAgent(placedAgent);

            participatingAgent.hSave();

            SessionHelper.commitTransaction(SessionHelper.EDITSOLUTIONS);
        }
        catch (Exception e)
        {
            SessionHelper.rollbackTransaction(SessionHelper.EDITSOLUTIONS);

            System.out.println(e);

            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.

            throw new RuntimeException(e);
        }
        finally
        {
            SessionHelper.clearSessions();
        }
    }

    /**
     * @see Agent#deleteParticipatingAgent(Long)
     * @param participatingAgentPK
     */
    public void deleteParticipatingAgent(Long participatingAgentPK)
    {
        ParticipatingAgent participatingAgent = ParticipatingAgent.findByPK(participatingAgentPK);

        try
        {
            SessionHelper.beginTransaction(SessionHelper.EDITSOLUTIONS);

            participatingAgent.hDelete();

            SessionHelper.commitTransaction(SessionHelper.EDITSOLUTIONS);
        }
        catch (Exception e)
        {
            SessionHelper.rollbackTransaction(SessionHelper.EDITSOLUTIONS);

            System.out.println(e);

            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.

            throw new RuntimeException(e);
        }
        finally
        {
            SessionHelper.clearSessions();
        }
    }

//    /**
//     * @see Agent#addContributingCompanyStructure(long, long)
//     * @param companyStructurePK
//     * @param bonusProgramPK
//     * @throws EDITAgentException
//     */
//    public void addContributingCompanyStructure(long companyStructurePK, long bonusProgramPK) throws EDITAgentException
//    {
//        ContributingProduct contributingProduct = new ContributingProduct();
//
//        CompanyStructure companyStructure = CompanyStructure.findByPK(companyStructurePK);
//
//        BonusProgram bonusProgram = BonusProgram.findByPK(new Long(bonusProgramPK));
//
//        contributingProduct.associate(companyStructure);
//
//        contributingProduct.associate(bonusProgram);
//
//        contributingProduct.save();
//    }
//
//    /**
//     * @see Agent#removeContributingCompanyStructure(long, long)
//     * @param companyStructurePK
//     * @param bonusProgramPK
//     */
//    public void removeContributingCompanyStructure(long companyStructurePK, long bonusProgramPK)
//    {
//        ContributingProduct contributingProduct = ContributingProduct.findBy_CompanyStructurePK_BonusProgramPK(companyStructurePK, bonusProgramPK);
//
//        contributingProduct.delete();
//    }

    /**
     * @see Agent#findCommissionProfilesBy_ContractCodeCT(String)
     * @param contractCodeCT
     * @return
     */
    public CommissionProfileVO[] findCommissionProfilesBy_ContractCodeCT(String contractCodeCT)
    {
        return (CommissionProfileVO[]) CRUDEntityImpl.mapEntityToVO(CommissionProfile.findBy_ContractCodeCT(contractCodeCT), CommissionProfileVO.class);
    }

    /**
     * @see Agent#addContributingCommissionProfile(Long, Long)
     * @param commissionProfilePK
     * @param bonusProgramPK
     */
    public void addContributingCommissionProfile(Long commissionProfilePK, Long bonusProgramPK)
    {
        ContributingProfile contributingProfile = new ContributingProfile();

        CommissionProfile commissionProfile = CommissionProfile.findBy_PK(commissionProfilePK);

        BonusProgram bonusProgram = BonusProgram.findByPK(bonusProgramPK);

        contributingProfile.setCommissionProfile(commissionProfile);

        contributingProfile.setBonusProgram(bonusProgram);

        SessionHelper.beginTransaction(SessionHelper.EDITSOLUTIONS);

        contributingProfile.hSave();

        SessionHelper.commitTransaction(SessionHelper.EDITSOLUTIONS);
    }

    /**
     * @see Agent#removeContributingCommissionProfile(Long, Long)
     * @param commissionProfilePK
     * @param bonusProgramPK
     */
    public void removeContributingCommissionProfile(Long commissionProfilePK, Long bonusProgramPK)
    {
        ContributingProfile contributingProfile = ContributingProfile.findBy_CommissionProfilePK_BonusProgramPK(commissionProfilePK, bonusProgramPK);

        SessionHelper.beginTransaction(SessionHelper.EDITSOLUTIONS);

        if (contributingProfile != null)
        {
            contributingProfile.hDelete();
        }

        SessionHelper.commitTransaction(SessionHelper.EDITSOLUTIONS);
    }

    /**
     * Composes the redirectVO for the given agent FK
     *
     * @param agentFK
     * @param voInclusionList
     * @return
     */
    public RedirectVO composeRedirectByAgentFK(long agentFK, List voInclusionList)
    {
        return new VOComposer().composeRedirectVOByAgentFK(agentFK, voInclusionList);
    }

    /**
     * @see agent.business.Agent#updateAgentBonuses()
     * @throws EDITAgentException
     */
    public void updateAgentBonuses() throws EDITAgentException
    {
        SessionHelper.clearSessions();
        
        BonusProgram.updateAgentBonuses();
    }

    /**
     * @see agent.business.Agent#runAgentBonusChecks(String, String)
     * @param processDate
     * @param operator
     */
    public void runAgentBonusChecks(String processDate, String operator) throws EDITAgentException
    {
        // ParticipatingAgent.processAgentBonusChecks(new EDITDate(processDate),
        // operator);
    }

    /**
     * @see Agent#findParticipatingAgentsBy_AgentPK(long)
     * @param agentPK
     * @return
     */
    public ParticipatingAgent[] findParticipatingAgentsBy_AgentPK(long agentPK)
    {
        return ParticipatingAgent.findBy_AgentPK(agentPK);
    }

    /**
     * @see Agent#generateBonusCommissionStatements(String, String)
     * @param contractCodeCT
     * @param processDate
     */
    public boolean generateBonusCommissionStatements(String contractCodeCT, String processDate)
    {
        return false; // return
        // ParticipatingAgent.generateBonusCommissionStatements(contractCodeCT,
        // new EDITDate(processDate));
    }

    /**
     * The AgentGroup is assumed to be associated with the Hibernate Session.
     * @see interface#saveAgentGroup(agent.AgentGroup, Long, Long, Long)
     * @param agentGroup
     * @param agentPK
     * @param commissionProfilePK
     */
    public void saveAgentGroup(AgentGroup agentGroup, Long agentPK, Long commissionProfilePK)
    {
        agent.Agent theAgent = agent.Agent.findByPK(agentPK);

        CommissionProfile commissionProfile = CommissionProfile.findBy_PK(commissionProfilePK);

        try
        {
            if (!agentGroup.hasAgentCommissionProfileAssociations())
            {
                theAgent.addAgentGroup(agentGroup);

                commissionProfile.addAgentGroup(agentGroup);
            }

            // It is possible that the CommissionProfile has changed, so we check for this.
            else
            {
                CommissionProfile currentCommissionProfile = agentGroup.getCommissionProfile();

                if (!currentCommissionProfile.getCommissionProfilePK().equals(commissionProfilePK))
                {
                    currentCommissionProfile.removeAgentGroup(agentGroup);

                    commissionProfile.addAgentGroup(agentGroup);
                }
            }

            SessionHelper.beginTransaction(SessionHelper.EDITSOLUTIONS);

            SessionHelper.commitTransaction(SessionHelper.EDITSOLUTIONS);
        }
        catch (Exception e)
        {
            System.out.println(e);

            e.printStackTrace();

            SessionHelper.rollbackTransaction(SessionHelper.EDITSOLUTIONS);
        }
        finally
        {
            SessionHelper.clearSessions();
        }
    }

    /**
     * @see interface#createAgentGroupAssociation(Long, Long, EDITDate)
     * @param placedAgentPK
     * @param agentGroupPK
     * @param startDate
     */
    public void createAgentGroupAssociation(Long placedAgentPK, Long agentGroupPK, EDITDate startDate)
    {
        PlacedAgent placedAgent = PlacedAgent.findBy_PK(placedAgentPK);

        AgentGroup agentGroup = AgentGroup.findBy_PK(agentGroupPK);

        AgentGroupAssociation agentGroupAssociation = (AgentGroupAssociation) SessionHelper.newInstance(AgentGroupAssociation.class,  SessionHelper.EDITSOLUTIONS);

        agentGroupAssociation.setStartDate(startDate);

        try
        {
//            placedAgent.addAgentGroupAssociation(agentGroupAssociation);

            placedAgent.hSave();

            agentGroup.addAgentGroupAssociation(agentGroupAssociation);

            SessionHelper.beginTransaction(SessionHelper.EDITSOLUTIONS);

            SessionHelper.commitTransaction(SessionHelper.EDITSOLUTIONS);
        }
        catch (Exception e)
        {
            System.out.println(e);

            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.

            SessionHelper.rollbackTransaction(SessionHelper.EDITSOLUTIONS);
        }
        finally
        {
            SessionHelper.clearSessions();
        }
    }

    /**
     * @see interface#deleteAgentGroupAssociation(Long)
     */
    public void deleteAgentGroupAssociation(Long agentGroupAssociationPK)
    {
        AgentGroupAssociation agentGroupAssociation = AgentGroupAssociation.findBy_PK(agentGroupAssociationPK);

        try
        {
            agentGroupAssociation.hDelete();

            SessionHelper.beginTransaction(SessionHelper.EDITSOLUTIONS);

            SessionHelper.commitTransaction(SessionHelper.EDITSOLUTIONS);
        }
        catch (Exception e)
        {
            SessionHelper.rollbackTransaction(SessionHelper.EDITSOLUTIONS);

            System.out.println(e);

            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        finally
        {
            SessionHelper.clearSessions();
        }
    }

    /**
     * @see interface#deleteAgentGroup(Long)
     * @param agentGroupPK
     */
    public void deleteAgentGroup(Long agentGroupPK)
    {
        try
        {
            SessionHelper.beginTransaction(SessionHelper.EDITSOLUTIONS);

            AgentGroup agentGroup = AgentGroup.findBy_PK(agentGroupPK);

            agentGroup.hDelete();

            SessionHelper.commitTransaction(SessionHelper.EDITSOLUTIONS);
        }
        catch (Exception e)
        {
            SessionHelper.rollbackTransaction(SessionHelper.EDITSOLUTIONS);

            System.out.println(e);

            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.

            throw new RuntimeException(e);
        }
        finally
        {
            SessionHelper.clearSessions();
        }
    }

    /**
     * @see agent.business.Agent#saveAgentGroupAssociation(agent.AgentGroupAssociation)
     * @param agentGroupAssociation
     */
    public void saveAgentGroupAssociation(AgentGroupAssociation agentGroupAssociation)
    {
        SessionHelper.beginTransaction(SessionHelper.EDITSOLUTIONS);

        SessionHelper.commitTransaction(SessionHelper.EDITSOLUTIONS);
    }

    public void createContributingProduct(Long productStructurePK, Long agentGroupPK, ContributingProduct contributingProduct)
    {
        ProductStructure productStructure = ProductStructure.findByPK(productStructurePK);

        AgentGroup agentGroup = AgentGroup.findBy_PK(agentGroupPK);

        try
        {
            SessionHelper.beginTransaction(SessionHelper.EDITSOLUTIONS);

            // ProductStructure is in a different DB - we have to manually do this.
            contributingProduct.setProductStructureFK(productStructurePK);

            agentGroup.add(contributingProduct);

            SessionHelper.commitTransaction(SessionHelper.EDITSOLUTIONS);
        }
        catch (Exception e)
        {
            System.out.println(e);

            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.

            SessionHelper.rollbackTransaction(SessionHelper.EDITSOLUTIONS);
        }
        finally
        {
            SessionHelper.clearSessions();
        }
    }

    public void deleteContributingProduct(Long contributingProductPK)
    {
        ContributingProduct contributingProduct = ContributingProduct.findBy_PK(contributingProductPK);

        try
        {
            SessionHelper.beginTransaction(SessionHelper.EDITSOLUTIONS);

            contributingProduct.hDelete();

            SessionHelper.commitTransaction(SessionHelper.EDITSOLUTIONS);
        }
        catch (Exception e)
        {
            SessionHelper.rollbackTransaction(SessionHelper.EDITSOLUTIONS);

            System.out.println(e);

            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.

            throw new RuntimeException(e);
        }
        finally
        {
            SessionHelper.clearSessions();
        }
    }

    public void saveContributingProduct(ContributingProduct contributingProduct)
    {
        try
        {
            SessionHelper.beginTransaction(SessionHelper.EDITSOLUTIONS);

            contributingProduct.hSave();

            SessionHelper.commitTransaction(SessionHelper.EDITSOLUTIONS);
        }
        catch (Exception e)
        {
            SessionHelper.rollbackTransaction(SessionHelper.EDITSOLUTIONS);

            System.out.println(e);

            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.

            throw new RuntimeException(e);
        }
        finally
        {
            SessionHelper.clearSessions();
        }
    }

    /**
     * @see agent.business.Agent#savePremiumLevel(agent.PremiumLevel, Long)
     * @param premiumLevel
     */
    public void savePremiumLevel(PremiumLevel premiumLevel, Long bonusProgramPK)
    {
        BonusProgram bonusProgram = BonusProgram.findByPK(bonusProgramPK);

        try
        {
            SessionHelper.beginTransaction(SessionHelper.EDITSOLUTIONS);

            bonusProgram.addPremiumLevel(premiumLevel);

            premiumLevel.hSave();

            SessionHelper.commitTransaction(SessionHelper.EDITSOLUTIONS);
        }
        catch (Exception e)
        {
            SessionHelper.rollbackTransaction(SessionHelper.EDITSOLUTIONS);

            System.out.println(e);

            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.

            throw new RuntimeException(e);
        }
        finally
        {
            SessionHelper.clearSessions();
        }
    }

    /**
     * @see agent.business.Agent#deletePremiumLevel(Long)
     * @param premiumLevelPK
     */
    public void deletePremiumLevel(Long premiumLevelPK)
    {
        PremiumLevel premiumLevel = PremiumLevel.findByPK(premiumLevelPK);

        try
        {
            SessionHelper.beginTransaction(SessionHelper.EDITSOLUTIONS);

            premiumLevel.hDelete();

            SessionHelper.commitTransaction(SessionHelper.EDITSOLUTIONS);
        }
        catch (Exception e)
        {
            SessionHelper.rollbackTransaction(SessionHelper.EDITSOLUTIONS);

            System.out.println(e);

            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.

            throw new RuntimeException(e);
        }
        finally
        {
            SessionHelper.clearSessions();
        }
    }

    /**
     * @see agent.business.Agent#saveBonusCriteria(agent.BonusCriteria, Long)
     * @param bonusCriteria
     * @param premiumLevelPK
     */
    public void saveBonusCriteria(BonusCriteria bonusCriteria, Long premiumLevelPK)
    {
        PremiumLevel premiumLevel = PremiumLevel.findByPK(premiumLevelPK);

        try
        {
            SessionHelper.beginTransaction(SessionHelper.EDITSOLUTIONS);

            premiumLevel.addBonusCriteria(bonusCriteria);

            bonusCriteria.hSave();

            SessionHelper.commitTransaction(SessionHelper.EDITSOLUTIONS);
        }
        catch (Exception e)
        {
            SessionHelper.rollbackTransaction(SessionHelper.EDITSOLUTIONS);

            System.out.println(e);

            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.

            throw new RuntimeException(e);
        }
        finally
        {
            SessionHelper.clearSessions();
        }
    }

    /**
     * @see agent.business.Agent#deleteBonusCriteria(Long)
     * @param bonusCriteriaPK
     */
    public void deleteBonusCriteria(Long bonusCriteriaPK)
    {
        BonusCriteria bonusCriteria = BonusCriteria.findByPK(bonusCriteriaPK);

        try
        {
            SessionHelper.beginTransaction(SessionHelper.EDITSOLUTIONS);

            bonusCriteria.hDelete();

            SessionHelper.commitTransaction(SessionHelper.EDITSOLUTIONS);
        }
        catch (Exception e)
        {
            SessionHelper.rollbackTransaction(SessionHelper.EDITSOLUTIONS);

            System.out.println(e);

            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.

            throw new RuntimeException(e);
        }
        finally
        {
            SessionHelper.clearSessions();
        }
    }

    /**
     *
     * @param bonusCriteriaPK
     * @param productStructurePK
     */
    public void attachBonusContributingProducts(Long bonusCriteriaPK, Long productStructurePK)
    {
        BonusContributingProduct contributingProduct = (BonusContributingProduct) SessionHelper.newInstance(BonusContributingProduct.class,
                                                                                                            SessionHelper.EDITSOLUTIONS);

        BonusCriteria bonusCriteria = BonusCriteria.findByPK(bonusCriteriaPK);

        contributingProduct.setBonusCriteria(bonusCriteria);

        contributingProduct.setProductStructureFK(productStructurePK);

        try
        {
            SessionHelper.beginTransaction(SessionHelper.EDITSOLUTIONS);

            contributingProduct.hSave();

            SessionHelper.commitTransaction(SessionHelper.EDITSOLUTIONS);
        }
        catch (Exception e)
        {
            SessionHelper.rollbackTransaction(SessionHelper.EDITSOLUTIONS);

            System.out.println(e);

            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.

            throw new RuntimeException(e);
        }
        finally
        {
            SessionHelper.clearSessions();
        }
    }

    /**
     *
     * @param bonusCriteriaPK
     * @param productStructurePK
     */
    public void detachBonusContributingProducts(Long bonusCriteriaPK, Long productStructurePK)
    {
        BonusCriteria bonusCriteria = BonusCriteria.findByPK(bonusCriteriaPK);

        BonusContributingProduct contributingProduct = BonusContributingProduct.findByProductStructureFK_BonusCriteria(productStructurePK, bonusCriteria);

        try
        {
            SessionHelper.beginTransaction(SessionHelper.EDITSOLUTIONS);

            contributingProduct.hDelete();

            SessionHelper.commitTransaction(SessionHelper.EDITSOLUTIONS);
        }
        catch (Exception e)
        {
            SessionHelper.rollbackTransaction(SessionHelper.EDITSOLUTIONS);

            System.out.println(e);

            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.

            throw new RuntimeException(e);
        }
        finally
        {
            SessionHelper.clearSessions();
        }
    }

    public void saveParticipantPremiumLevel(PremiumLevel premiumLevel, Long participatingAgentPK)
    {
        ParticipatingAgent participatingAgent = ParticipatingAgent.findByPK(participatingAgentPK);

        try
        {
            SessionHelper.beginTransaction(SessionHelper.EDITSOLUTIONS);

            participatingAgent.addPremiumLevel(premiumLevel);

            premiumLevel.hSave();

            SessionHelper.commitTransaction(SessionHelper.EDITSOLUTIONS);
        }
        catch (Exception e)
        {
            SessionHelper.rollbackTransaction(SessionHelper.EDITSOLUTIONS);

            System.out.println(e);

            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.

            throw new RuntimeException(e);
        }
        finally
        {
            SessionHelper.clearSessions();
        }
    }

    public void deleteParticipantPremiumLevel(Long premiumLevelPK)
    {
        PremiumLevel premiumLevel = PremiumLevel.findByPK(premiumLevelPK);

        try
        {
            SessionHelper.beginTransaction(SessionHelper.EDITSOLUTIONS);

            premiumLevel.hDelete();

            SessionHelper.commitTransaction(SessionHelper.EDITSOLUTIONS);
        }
        catch (Exception e)
        {
            SessionHelper.rollbackTransaction(SessionHelper.EDITSOLUTIONS);

            System.out.println(e);

            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.

            throw new RuntimeException(e);
        }
        finally
        {
            SessionHelper.clearSessions();
        }
    }

    public void attachParticipantContributingProducts(Long bonusCriteriaPK, Long productStructurePK)
    {
        BonusContributingProduct contributingProduct = (BonusContributingProduct) SessionHelper.newInstance(BonusContributingProduct.class,
                                                                                                            SessionHelper.EDITSOLUTIONS);

        BonusCriteria bonusCriteria = BonusCriteria.findByPK(bonusCriteriaPK);

        contributingProduct.setBonusCriteria(bonusCriteria);

        contributingProduct.setProductStructureFK(productStructurePK);

        try
        {
            SessionHelper.beginTransaction(SessionHelper.EDITSOLUTIONS);

            contributingProduct.hSave();

            SessionHelper.commitTransaction(SessionHelper.EDITSOLUTIONS);
        }
        catch (Exception e)
        {
            SessionHelper.rollbackTransaction(SessionHelper.EDITSOLUTIONS);

            System.out.println(e);

            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.

            throw new RuntimeException(e);
        }
        finally
        {
            SessionHelper.clearSessions();
        }
    }

    public void detachParticipantContributingProducts(Long bonusCriteriaPK, Long productStructurePK)
    {
        BonusCriteria bonusCriteria = BonusCriteria.findByPK(bonusCriteriaPK);

        BonusContributingProduct contributingProduct = BonusContributingProduct.findByProductStructureFK_BonusCriteria(productStructurePK, bonusCriteria);

        try
        {
            SessionHelper.beginTransaction(SessionHelper.EDITSOLUTIONS);

            contributingProduct.hDelete();

            SessionHelper.commitTransaction(SessionHelper.EDITSOLUTIONS);
        }
        catch (Exception e)
        {
            SessionHelper.rollbackTransaction(SessionHelper.EDITSOLUTIONS);

            System.out.println(e);

            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.

            throw new RuntimeException(e);
        }
        finally
        {
            SessionHelper.clearSessions();
        }
    }

    /**
     * Determines the appropriate ClientRole to use for placing an agent.  First, checks if the referenceID already
     * exists for a different clientDetail.  If so, throws an error.  If the referenceID exists but for the same clientDetail,
     * returns that ClientRole.  If the referenceID does not yet exist, tries to find the ClientRole using the ClientDetail
     * and roleType and a null referenceID (it may not have been set yet).
     *
     * @param referenceID               referenceID (agentNumber) to match for the ClientRole
     * @param clientDetail              ClientDetail of the agent to be placed.  It must match the ClientDetail
     *                                  on the ClientRole that has the referenceID, otherwise, an error is thrown.
     *
     * @return  ClientRole that should be used to place the agent, null if not found.
     *
     * @throws EDITAgentException
     */
    private ClientRole getAppropriatePlacedAgentClientRole(String referenceID, ClientDetail clientDetail) throws EDITAgentException
    {
        ClientRole clientRole = null;

        ClientRole[] clientRoles = ClientRole.findBy_RoleType_ReferenceID( ClientRole.ROLETYPECT_AGENT, referenceID);

        //  There should only be one ClientRole for a roleType of agent and a given referenceID
        //  If there are any, that means that the referenceID exists for "someone", now check to see that the
        //  ClientDetails match
        if (clientRoles.length > 0)
        {
            clientRole = clientRoles[0];

            ClientDetail existingClientDetail = clientRole.getClientDetail();

            //  If the clientDetail already associated with the clientRole is not the same as the one being placed
            //  throw an error
            if (existingClientDetail.getClientDetailPK().longValue() != clientDetail.getClientDetailPK().longValue())
            {
                throw new EDITAgentException("Cannot place agent - duplicate agent number");
            }

            clientRole = ClientRole.findBy_ClientDetail_RoleTypeCT_ReferenceID(clientDetail, ClientRole.ROLETYPECT_AGENT, referenceID);
        }
        else
        {
            //  ReferenceID was not found, it is new.  See if the role exists with the referenceID not set yet (null)
            clientRole = ClientRole.findBy_ClientDetail_RoleTypeCT_NULLReferenceID(clientDetail, ClientRole.ROLETYPECT_AGENT);
        }

        return clientRole;
    }

    /**
     * Create new agent ClientRole with the given information
     *
     * @param agentNumber
     * @param clientDetail
     * @param placedAgentDetails
     * @param agent
     *
     * @return  newly created ClientRole
     */
    private ClientRole createAgentClientRole(String agentNumber, ClientDetail clientDetail, PlacedAgent placedAgentDetails,
                                             agent.Agent agent)
    {
        ClientRole clientRole = new ClientRole();

        Preference preference = clientDetail.getPrimaryPreference();
        Set<TaxInformation> taxInformation = clientDetail.getTaxInformations();

        clientRole.setClientDetail(clientDetail);
        clientRole.setPreference(preference);

        if (!taxInformation.isEmpty())
        {
            Iterator it = taxInformation.iterator();

            TaxInformation taxInfo = (TaxInformation) it.next();

            Set<TaxProfile> taxProfiles = taxInfo.getTaxProfiles();

            Iterator it2 = taxProfiles.iterator();

            while (it2.hasNext())
            {
                TaxProfile taxProfile = (TaxProfile) it2.next();

                if (taxProfile.getOverrideStatus().equalsIgnoreCase("P"))
                {
                    clientRole.setTaxProfile(taxProfile);
                }
            }
        }

        clientRole.setRoleTypeCT(ClientRole.ROLETYPECT_AGENT);
        clientRole.setOverrideStatus("P");
        clientRole.setReferenceID(agentNumber);
        clientRole.addPlacedAgent(placedAgentDetails);

        ClientRoleFinancial clientRoleFinancial = new ClientRoleFinancial();
        clientRoleFinancial.setClientRole(clientRole);

        String hireDate = agent.getHireDate().toString();

        if (preference != null && !preference.getPaymentModeCT().equals("Auto"))
        {
            hireDate = modifyHireDate(preference.getPaymentModeCT(), hireDate);
        }

        clientRoleFinancial.setLastCheckDateTime(new EDITDateTime(new EDITDate(hireDate), EDITDateTime.DEFAULT_MIN_TIME));
        clientRoleFinancial.setLastStatementDateTime(new EDITDateTime(new EDITDate(hireDate), EDITDateTime.DEFAULT_MIN_TIME));
        clientRole.add(clientRoleFinancial);

        return clientRole;
    }
}
