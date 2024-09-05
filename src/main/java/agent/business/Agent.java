package agent.business;

import agent.*;
import edit.common.EDITBigDecimal;
import edit.common.EDITDate;
import edit.common.exceptions.EDITAgentException;
import edit.common.vo.*;
import edit.services.component.ICRUD;
import edit.services.component.ILockableElement;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: gfrosti
 * Date: Sep 25, 2003
 * Time: 2:01:24 PM
 * To change this template use Options | File Templates.
 */
public interface Agent extends ICRUD, ILockableElement
{
    /**
     * Saves the Agent composition including AgentContract, AgentlLicense, etc.
     * @param agentVO
     * @throws Exception
     */
    public void saveAgent(AgentVO agentVO) throws Exception;

    /**
     * Find all branches that an AgentContract is participating in.
     * @param agentContractPK
     * @return
     * @throws Exception
     */
    public PlacedAgentBranchVO[] getBranchesByAgentContractPK(long agentContractPK) throws Exception;

    /**
     * Finds all branches that an Agent is participating in.
     * @param agent
     * @return
     * @throws Exception
     */
    public PlacedAgentBranchVO[] getBranchesByAgent(agent.Agent agent) throws Exception;

    /**
     * Finds all branches that a given AgentNumber is participating in. PlacedAgents with a Stop Date <= the specified Expiration
     * Date will not be included in the branches.
     * @param agentNumber
     * @param filterDate
     * @return
     * @throws Exception
     */
    public PlacedAgentBranchVO [] getBranchesByAgentNumberAndExpirationDate(String agentNumber, String filterDate) throws Exception;

    /**
     * Places an AgentContract into the Hierarchy as the neweset lowest level agent
     * @param agentContractPK
     * @param lowestLevelPlacedAgentPK
     * @param placedAgentDetails
     * @param commissionProfilePK
     * @param operator
     * @param agentNumber
     * @return
     * @throws EDITAgentException
     */
    public PlacedAgentBranch placeAgentContract(long agentContractPK, 
                                                long lowestLevelPlacedAgentPK, 
                                                PlacedAgent placedAgentDetails, 
                                                long commissionProfilePK, 
                                                String operator,
                                                String agentNumber) throws EDITAgentException;

    /**
     * Removes a PlacedAgent from the PlacedAgent Tree. This can have many 'report-to' consquences
     * @param placedAgentPK
     * @throws EDITAgentException
     */
    public void removePlacedAgent(long placedAgentPK, String operator) throws EDITAgentException;

    /**
     * Shifts a PlacedAgent up or down its branch in the Hierarchy.
     * @param placedAgentPK
     * @param lowestLevelPlacedAgentPK Required to build the branch
     * @param direction                (+) to shift up the branch, (-) to shift down the branch.
     * @throws Exception
     */
    public PlacedAgentBranchVO shiftAgent(long placedAgentPK, long lowestLevelPlacedAgentPK, int direction, String operator) throws Exception;

    /**
     * Associates a CommissionProfile with a PlacedAgent. Any previous association is preserved by previous start/stop dates.
     * @param placedAgentPK
     * @param startDate
     * @param stopDate
     * @param commissionProfilePK
     * @throws Exception
     */
     public void associateCommissionProfile(Long placedAgentPK, Long commissionProfilePK, EDITDate startDate, EDITDate stopDate) throws EDITAgentException;

    /**
     * Updates the Agent's ClientRoleFinancials
     * @param companyName
     * @throws Exception
     */
    public void updateAgentCommissions(String companyName) throws Exception;

    /**
     * Updates the Agent's Bonus Cum on ClientRoleFinancial
     * @param companyName
     * @param fromDate
     * @param toDate
     * @throws Exception
     */
    public void updateBonusCommissions(String companyName, String fromDate, String toDate) throws Exception;

    /**
     * Generates XML report for CommissionHistory activity by CommissionContract
     * @param contractCodeCT
     * @param processDate
     * @throws Exception
     */
    public void generateCommissionStatements(String contractCodeCT, String processDate) throws Exception;


    public void createCommissionAdjustment(String filterAgentId, EDITBigDecimal commissionAmount, String reduceTaxable, String operator) throws Exception;

    /**
     * Generates the hierarchical report for a given commission contract.
     * @param contractCodeCT
     */
    public HierarchyReport generateHierarchyReportByContractCodeCT(String contractCodeCT, boolean includeExpiredAgents);

    /**
     * Generates the hierarchical report for a given placed agent.
     * @param placedAgentPK
     */
    public HierarchyReport generateHierarchyReportByPlacedAgent(long placedAgentPK, boolean includeExpiredAgents);

    /**
     * Updates the specified PlacedAgent with the supplied values.
     * @param placedAgentPK
     * @param startDate
     * @param stopDate
     * @param situation
     * @param stopDateReasonCT
     * @param agentNumber
     */
    public void updatePlacedAgentDetails(long placedAgentPK, String startDate, String stopDate, String situation, String stopDateReasonCT, String AgentNumber) throws EDITAgentException;

    /**
     * Copies a PlacedAgent group to a (underneath) a new root.
     * @param fromPlacedAgentGroupRootPK
     * @param toPlacedAgentGroupRootPK
     * @param stopDateReasonCT
     * @param situationCode
     * @throws EDITAgentException
     * @see agent.Hierarchy#copyPlacedAgentGroup(agent.PlacedAgent,agent.PlacedAgent,edit.common.EDITDate,String,String,String)
     */
    public void copyPlacedAgentGroup(long fromPlacedAgentGroupRootPK, long toPlacedAgentGroupRootPK, String startDate, String stopDateReasonCT, String situationCode, String operator, String agentNumber) throws EDITAgentException;

    /* ************************************** Composers ********************************* */

    /**
     * Finds the Agent by the specified agentId and commissionContractPK
     * @param agentId
     * @param contractCodeCT
     * @return
     */
    public AgentContractVO[] findAgentContractVOByAgentId_AND_ContractCodeCT(String agentId, String contractCodeCT) throws Exception;

    /**
     * Finds the set of all Agents by the specified partial last-name or corporate-name and commissionContractPK
     * @param agentName
     * @param contractCodeCT
     * @return
     */
    public AgentContractVO[] findAgentContractVOByAgentName_AND_ContractCodeCT(String agentName, String contractCodeCT);

    public PlacedAgentVO composePlacedAgentVOByPlacedAgentPK(long placedAgentPK, List voInclusionList);

    public PlacedAgentVO[] composePlacedAgentVOByAgentContractFK(long agentContractFK, List voInclusionList) throws Exception;

    public PlacedAgentVO composePlacedAgentVO(PlacedAgentVO placedAgentVO, List voInclusionList);

    public AgentVO composeAgentVO(long agentPK, List voInclusionList) throws Exception;

    public AgentVO[] composeAgentVOByRolePK(long clientRolePK, List voInclusionList);

    public AgentVO composeAgentVOByAgentNumber(String agentNumber, List voInclusionList) throws Exception;

    public CommissionProfileVO[] composeCommissionProfiles(List voInclusionList) throws Exception;

    public CommissionProfileVO[] composeCommissionProfileVOByContractCodeCT(String contractCodeCT, List voInclusionList) throws Exception;

    public CommissionProfileVO composeCommissionProfileVOByCommissionProfilePK(long commissionProfilePK, List voInclusionList) throws Exception;

    public CommissionProfileVO[] getAllCommissionProfiles() throws Exception;

    public CommissionProfileVO[] getCommissionProfileByPK(long commContractPK) throws Exception;

    public AgentVO composeAgentByAgentNumber(String agentNumber, List voInclusionList);

    public abstract long getNextAvailableKey();

    /**
     * Associates the AgentContractVO, AgentVO, ClientRoleVO, CommissionProfileVO, CommissionLevelVO, CommissionLevelDescriptionVO, and ClientDetailVO for each PlacedAgentVO within the
     * branch.
     * @param placedAgentBranchVO
     * @param depth
     * @return
     */
    public PlacedAgentBranchVO composePlacedAgentBranchVO(PlacedAgentBranchVO placedAgentBranchVO, int depth);

    /**
     * Save or updates an Agent Note for a specified Agent. It is assumed that the AgentFK has already
     * been set.
     * @param agentNoteVO
     */
    public void saveAgentNote(AgentNoteVO agentNoteVO);

//    /**
//     * Finder.
//     * @param companyStructurePK
//     * @param bonusProgramPK
//     * @return
//     */
//    public ContributingProductVO findContributingProductBy_CompanyStructurePK_BonusProgramPK(long companyStructurePK, long bonusProgramPK);

    /**
     * Saves the user-supplied BonusProgram.
     * @param bonusProgram
     */
    public void saveBonusProgram(BonusProgram bonusProgram);

    /**
     * Finder.
     * @param bonusProgramPK
     * @return
     */
    public BonusProgram findBonusProgramBy_BonusProgramPK(long bonusProgramPK);

    /**
     * Finder.
     * @return
     */
    public BonusProgram[] findAllBonusPrograms();

    /**
     * Delete the specified BonusProgram.
     * @param bonusProgram
     */
    public void deleteBonusProgram(BonusProgram bonusProgram);

    /**
     * Finder.
     * @param bonusProgramPK
     * @return
     */
    public ParticipatingAgent[] findParticipatingAgentsBy_BonusProgramPK(long bonusProgramPK);

    /**
     * Finder.
     * @param contractCodeCT
     * @param commissionLevelCT
     * @param startDate
     * @param stopDate
     * @return
     */
    public PlacedAgentVO[] findPlacedAgentsBy_ContractCodeCT_CommissionLevelCT_StartDate_StopDate(String contractCodeCT, String commissionLevelCT, String startDate, String stopDate);

    /**
     * Finder.
     * @param contractCodeCT
     * @param agentNumber
     * @param startDate
     * @param stopDate
     * @return
     */
    public PlacedAgentVO[] findPlacedAgentsBy_CommissionContractCT_AgentNumber_StartDate_StopDate(String contractCodeCT, String agentNumber, String startDate, String stopDate);

    /**
     * @param contractCodeCT
     * @param agentName
     * @param startDate
     * @param stopDate
     * @return
     */
    public PlacedAgentVO[] findPlacedAgentsBy_CommissionContractCT_AgentName_StartDate_StopDate(String contractCodeCT, String agentName, String startDate, String stopDate);

    /**
     * Saves or updates the speficified Participating Agent.
     * @param participatingAgent
     */
    public void saveParticipatingAgent(ParticipatingAgent participatingAgent, Long bonusProgramPK, Long placedAgentPK);

    /**
     * Deletes the specified Participating Agent.
     * @param participatingAgentPK
     */
    public void deleteParticipatingAgent(Long participatingAgentPK);

//    /**
//     * Adds a CompanyStructure to the set of CompanyStructures that can contribute to a specified BonusProgram.
//     * @param companyStructurePK
//     * @param bonusProgramPK
//     * @throws EDITAgentException
//     */
//    public void addContributingCompanyStructure(long companyStructurePK, long bonusProgramPK) throws EDITAgentException;
//
//    /**
//     * Removes a CompanyStructure from the set of CompanyStructures contributing to a specified BonusProgram.
//     * @param companyStructurePK
//     * @param bonusProgramPK
//     */
//    public void removeContributingCompanyStructure(long companyStructurePK, long bonusProgramPK);

    /**
     * Finder.
     * @param contractCodeCT
     * @return
     */
    public CommissionProfileVO[] findCommissionProfilesBy_ContractCodeCT(String contractCodeCT);

    /**
     * Adds a CommissionProfile to the set of ProductStructures that can contribute to a specified BonusProgram.
     * @param commissionProfilePK
     * @param bonusProgramPK
     */
    public void addContributingCommissionProfile(Long commissionProfilePK, Long bonusProgramPK);

    /**
     * Removes a CommissionProfile from the set of CommissionProfiles contributing to a specified BonusProgram.
     * @param commissionProfilePK
     * @param bonusProgramPK
     */
    public void removeContributingCommissionProfile(Long commissionProfilePK, Long bonusProgramPK);

    /**
     * Composes the redirectVO for the given agent FK
     * @param agentFK
     * @param voInclusionList
     * @return
     */
    public RedirectVO composeRedirectByAgentFK(long agentFK, List voInclusionList);

    /**
     * Executes the update-process of the Agent bonus program. A bonus name of null signifies that all bonus programs
     * should be processed.
     * @throws EDITAgentException
     */
    public void updateAgentBonuses() throws EDITAgentException;

    /**
     * Intitiates the process of building Agent Bonus Check transactions and running them real-time.
     * @param processDate
     * @param operator
     * @throws EDITAgentException
     */
    public void runAgentBonusChecks(String processDate, String operator) throws EDITAgentException;

    /**
     * The set of ParticipatingAgents (Agents that participate in a BonusProgram), if any.
     * @param agentPK
     * @return
     */
    public ParticipatingAgent[] findParticipatingAgentsBy_AgentPK(long agentPK);

    /**
     * Builds Bonus Commission Statements for all qualifying ParticipatingAgents.
     * @param contractCodeCT
     * @param processDate
     */
    public boolean generateBonusCommissionStatements(String contractCodeCT, String processDate);

    /**
     * Saves or updates the specified AgentGroup and establishes/updates the associations with the the specified
     * CommissionProfiles.
     * @param agentGroup
     * @param agentPK
     * @param commissionProfilePK
     */
    public void saveAgentGroup(AgentGroup agentGroup, Long agentPK, Long commissionProfilePK);

    /**
     * Associates the selected PlacedAgent to the currently selected AgentGroup.
     * @param startDate
     */
    public void createAgentGroupAssociation(Long placedAgentPK, Long agentGroupPK, EDITDate startDate);

    /**
     * Removes the association between an AgentGroup and a PlacedAgent.
     * @param agentGroupAssociationPK
     */
    public void deleteAgentGroupAssociation(Long agentGroupAssociationPK);

    /**
     * Deletes the specified AgentGroup and any AgentGroupAssocations (a child entity of AgentGroup).
     * @param agentGroupPK
     */
    public void deleteAgentGroup(Long agentGroupPK);

    /**
     * Saves or updates specified agentGroupAssociation
     * @param agentGroupAssociation
     */
    public void saveAgentGroupAssociation(AgentGroupAssociation agentGroupAssociation);

    /**
     * Associates the specified ProductStructure to the specified AgentGroup to create a
     * ContributingProduct.
     * @param agentGroupPK
     * @param contributingProduct
     */
    public void createContributingProduct(Long productStructurePK, Long agentGroupPK, ContributingProduct contributingProduct);

    /**
     * Disassociates the selected ProductStructure from the current AgentGroup deleting the ContributingProduct.
     * @param contributingProductPK
     */
    public void deleteContributingProduct(Long contributingProductPK);

    /**
     * Saves/Updates the specified ContributingProduct.
     * @param contributingProduct
     */
    public void saveContributingProduct(ContributingProduct contributingProduct);

    /**
     * Adds/Updates the specified PremiumLevel to given Bonus Program.
     * @param premiumLevel
     */
    public void savePremiumLevel(PremiumLevel premiumLevel, Long bonusProgramPK);

    /**
     * Deletes the specified Premium Level.
     * @param premiumLevelPK
     */
    public void deletePremiumLevel(Long premiumLevelPK);

    /**
     * Adds/Updates the specified Bonus Criteria to give Prmium Level
     * @param bonusCriteria
     * @param premiumLevelPK
     */
    public void saveBonusCriteria(BonusCriteria bonusCriteria, Long premiumLevelPK);

    /**
     * Deletes the specified Bonus Criteria
     * @param bonusCriteriaPK
     */
    public void deleteBonusCriteria(Long bonusCriteriaPK);

    /**
     * Associates Bonus Criteria with Products.
     * @param bonusCriteriaPK
     * @param productStructurePK
     */
    public void attachBonusContributingProducts(Long bonusCriteriaPK, Long productStructurePK);

    /**
     * Disassociates Bonus Criteria with Products.
     * @param bonusCriterialPK
     * @param productStructurePK
     */
    public void detachBonusContributingProducts(Long bonusCriterialPK, Long productStructurePK);

    /**
     * Saves Participant Premium Level information.
     * @param premiumLevel
     * @param participatingAgentPK
     */
    public void saveParticipantPremiumLevel(PremiumLevel premiumLevel, Long participatingAgentPK);

    /**
     * Deletes specified participant Premium Level.
     * @param premiumLevelPK
     */
    public void deleteParticipantPremiumLevel(Long premiumLevelPK);

    /**
     * Associates Participant Bonus Criteria with Products.
     * @param bonusCriteriaPK
     * @param productStructurePK
     */
    public void attachParticipantContributingProducts(Long bonusCriteriaPK, Long productStructurePK);

    /**
     * Disassociates Participants Bonus Criteria with Products.
     * @param bonusCriteriaPK
     * @param productStructurePK
     */
    public void detachParticipantContributingProducts(Long bonusCriteriaPK, Long productStructurePK);
}


