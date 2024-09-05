/*
 * User: gfrosti
 * Date: Dec 23, 2004
 * Time: 4:50:14 PM
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package agent.ui.servlet;

import agent.BonusCriteria;
import agent.BonusProgram;
import agent.ParticipatingAgent;
import agent.PremiumLevel;

import agent.business.Agent;

import agent.component.AgentComponent;

import edit.common.EDITDate;
import edit.common.EDITList;
import edit.common.vo.CommissionProfileVO;
import edit.common.vo.PlacedAgentVO;

import edit.portal.common.transactions.Transaction;
import edit.portal.widget.AgentBonusPremiumLevelTableModel;
import edit.portal.widget.ParticipantBonusCriteriaTableModel;

import edit.services.db.hibernate.SessionHelper;

import engine.ProductStructure;

import fission.global.AppReqBlock;

import fission.utility.Util;


public class AgentBonusTran extends Transaction
{
    // Actions
    private static final String SHOW_PARTICIPANT_SELECTION = "showParticipantSelection";
    private static final String SHOW_BONUS_PROGRAM = "showBonusProgram";
    private static final String SHOW_AGENT_BONUS_PROD_CRITERIA_ASSOC = "showAgentBonusProdCriteriaAssoc";
    private static final String SHOW_COMMISSION_PROFILE_SELECTION = "showCommissionProfileSelection";
    private static final String ADD_BONUS_PROGRAM = "addBonusProgram";
    private static final String SAVE_BONUS_PROGRAM = "saveBonusProgram";
    private static final String SHOW_BONUS_PROGRAM_DETAIL = "showBonusProgramDetail";
    private static final String CANCEL_BONUS_PROGRAM = "cancelBonusProgram";
    private static final String DELETE_BONUS_PROGRAM = "deleteBonusProgram";
    private static final String SHOW_AGENT_BONUS_SEARCH_DIALOG = "showAgentBonusSearchDialog";
    private static final String FIND_PLACED_AGENTS_BY_COMMISSIONCONTRACTCT_AND_COMMISSIONLEVELCT = "findPlacedAgentsByCommissionContractCTAndCommissionLevelCT";
    private static final String FIND_PLACED_AGENTS_BY_COMMISSIONCONTRACTCT_AND_AGENTNUMBER = "findPlacedAgentsByCommissionContractCTAndAgentNumber";
    private static final String FIND_PLACED_AGENTS_BY_COMMISSIONCONTRACTCT_AND_AGENTNAME = "findPlacedAgentsByCommissionContractCTAndAgentName";
    private static final String SHOW_PARTICIPATING_AGENT_AFTER_SEARCH = "showParticipatingAgentAfterSearch";
    private static final String SAVE_PARTICIPATING_AGENT = "saveParticipatingAgent";
    private static final String SHOW_PARTICIPATING_AGENT_DETAIL = "showParticipatingAgentDetail";
    private static final String CANCEL_PARTICIPATING_AGENT = "cancelParticipatingAgent";
    private static final String DELETE_PARTICIPATING_AGENT = "deleteParticipatingAgent";
    private static final String SHOW_PARTICIPANT_PROD_CRITERIA_ASSOC = "showParticipantProdCriteriaAssoc";
    private static final String ADD_CONTRIBUTING_COMMISSION_PROFILES = "addContributingCommissionProfiles";
    private static final String REMOVE_CONTRIBUTING_COMMISSION_PROFILES = "removeContributingCommissionProfiles";
    private static final String ADD_AGENT_BONUS_PREMIUM_LEVEL = "addAgentBonusPremiumLevel";
    private static final String SAVE_AGENT_BONUS_PREMIUM_LEVEL = "saveAgentBonusPremiumLevel";
    private static final String CLEAR_AGENT_BONUS_PREMIUM_LEVEL = "clearAgentBonusPremiumLevel";
    private static final String DELETE_AGENT_BONUS_PREMIUM_LEVEL = "deleteAgentBonusPremiumLevel";
    private static final String SHOW_AGENT_BONUS_PREMIUM_LEVEL_DETAIL = "showAgentBonusPremiumLevelDetail";
    private static final String SAVE_BONUS_CRITERIA = "saveBonusCriteria";
    private static final String DELETE_BONUS_CRITERIA = "deleteBonusCriteria";
    private static final String ATTACH_CONTRIBUTING_PRODUCT_STRUCTURES = "attachContributingProductStructures";
    private static final String DETACH_CONTRIBUTING_PRODUCT_STRUCTURES = "detachContributingProductStructures";
    private static final String SHOW_BONUS_CRITERIA_DETAIL = "showBonusCriteriaDetail";
    private static final String ADD_AGENT_BONUS_PROD_CRIT_ASSOC = "addAgentBonusProdCritAssoc";
    private static final String CLEAR_AGENT_BONUS_PROD_CRIT_ASSOC = "clearAgentBonusProdCritAssoc";
    private static final String SHOW_PARTICIPANT_BONUS_CRITERIA_DETAIL = "showParticipantBonusCriteriaDetail";
    private static final String ADD_PARTICIPANT_PROD_CRIT_ASSOC = "addParticipantProdCritAssoc";
    private static final String CLEAR_PARTICIPANT_PROD_CRIT_ASSOC = "clearParticipantProdCritAssoc";
    private static final String SAVE_PARTICIPANT_BONUS_CRITERIA = "saveParticipantBonusCriteria";
    private static final String DELETE_PARTICIPANT_BONUS_CRITERIA = "deleteParticipantBonusCriteria";
    private static final String ATTACH_PARTICIPANT_CONTRIBUTING_PRODUCTS = "attachParticipantContributingProducts";
    private static final String DETACH_PARTICIPANT_CONTRIBUTING_PRODUCTS = "detachParticipantContributingProducts";
    private static final String SAVE_PARTICIPANT_PREMIUM_LEVEL = "saveParticipantPremiumLevel";
    private static final String SHOW_PARTICIPANT_PREMIUM_LEVEL_DETAIL = "showParticipantPremiumLevelDetail";
    private static final String CLEAR_PARTICIPANT_PREMIUM_LEVEL = "clearParticipantPremiumLevel";
    private static final String DELETE_PARTICIPANT_PREMIUM_LEVEL = "deleteParticipantPremiumLevel";

    // Pages
    private static final String AGENT_BONUS_PARTICIPANT_SELECTION = "/agent/jsp/agentBonusParticipantSelection.jsp";
    private static final String AGENT_BONUS_PROGRAM = "/agent/jsp/agentBonusProgram.jsp";
    private static final String AGENT_BONUS_PRODUCT_CRITERIA_ASSOCIATION = "/agent/jsp/agentBonusProductCriteriaAssociation.jsp";
    private static final String AGENT_BONUS_COMMSSION_PROFILE_SELECTION = "/agent/jsp/agentBonusCommissionProfileSelection.jsp";
    private static final String AGENT_BONUS_SEARCH_DIALOG = "/agent/jsp/agentBonusSearchDialog.jsp";
    private static final String PARTICIPANT_PRODUCT_CRITERIA_ASSOCIATION = "/agent/jsp/participantProductCriteriaAssociation.jsp";
    private static final String AGENT_BONUS_TOOLBAR = "/agent/jsp/agentBonusToolbar.jsp";
    private static final String TEMPLATE_TOOLBAR_MAIN = "/common/jsp/template/template-toolbar-main.jsp";

    /**
     * @param appReqBlock
     * @return
     * @throws Throwable
     * @see Transaction#execute(fission.global.AppReqBlock)
     */
    public String execute(AppReqBlock appReqBlock) throws Throwable
    {
        String action = appReqBlock.getReqParm("action");

        String returnPage = null;

        if (action.equals(SHOW_PARTICIPANT_SELECTION))
        {
            returnPage = showParticipantSelection(appReqBlock);
        }
        else if (action.equals(SHOW_BONUS_PROGRAM))
        {
            returnPage = showBonusProgram(appReqBlock);
        }
        else if (action.equals(SHOW_AGENT_BONUS_PROD_CRITERIA_ASSOC))
        {
            returnPage = showAgentBonusProductsCriteriaAssociation(appReqBlock);
        }
        else if (action.equals(SHOW_COMMISSION_PROFILE_SELECTION))
        {
            returnPage = showCommssionProfileSelection(appReqBlock);
        }
        else if (action.equals(ADD_BONUS_PROGRAM))
        {
            returnPage = addBonusProgram(appReqBlock);
        }
        else if (action.equals(SAVE_BONUS_PROGRAM))
        {
            returnPage = saveBonusProgram(appReqBlock);
        }
        else if (action.equals(SHOW_BONUS_PROGRAM_DETAIL))
        {
            returnPage = showBonusProgramDetail(appReqBlock);
        }
        else if (action.equals(CANCEL_BONUS_PROGRAM))
        {
            returnPage = cancelBonusProgram(appReqBlock);
        }
        else if (action.equals(DELETE_BONUS_PROGRAM))
        {
            returnPage = deleteBonusProgram(appReqBlock);
        }
        else if (action.equals(SHOW_AGENT_BONUS_SEARCH_DIALOG))
        {
            returnPage = showAgentBonusSearchDialog(appReqBlock);
        }
        else if (action.equals(FIND_PLACED_AGENTS_BY_COMMISSIONCONTRACTCT_AND_COMMISSIONLEVELCT))
        {
            return findPlacedAgentsByCommissionContractCTAndCommissionLevelCT(appReqBlock);
        }
        else if (action.equals(FIND_PLACED_AGENTS_BY_COMMISSIONCONTRACTCT_AND_AGENTNUMBER))
        {
            return findPlacedAgentsByCommissionContractCTAndAgentNumber(appReqBlock);
        }
        else if (action.equals(FIND_PLACED_AGENTS_BY_COMMISSIONCONTRACTCT_AND_AGENTNAME))
        {
            return findPlacedAgentsByCommissionContractCTAndAgentName(appReqBlock);
        }
        else if (action.equals(SHOW_PARTICIPATING_AGENT_AFTER_SEARCH))
        {
            returnPage = showParticipatingAgentAfterSearch(appReqBlock);
        }
        else if (action.equals(SAVE_PARTICIPATING_AGENT))
        {
            returnPage = saveParticipatingAgent(appReqBlock);
        }
        else if (action.equals(SHOW_PARTICIPATING_AGENT_DETAIL))
        {
            returnPage = showParticipatingAgentDetail(appReqBlock);
        }
        else if (action.equals(CANCEL_PARTICIPATING_AGENT))
        {
            returnPage = cancelParticipatingAgent(appReqBlock);
        }
        else if (action.equals(DELETE_PARTICIPATING_AGENT))
        {
            returnPage = deleteParticipatingAgent(appReqBlock);
        }
        else if (action.equals(SHOW_PARTICIPANT_PROD_CRITERIA_ASSOC))
        {
            returnPage = showParticipantProdCriteriaAssoc(appReqBlock);
        }
        else if (action.equals(ADD_CONTRIBUTING_COMMISSION_PROFILES))
        {
            returnPage = addContributingCommissionProfiles(appReqBlock);
        }
        else if (action.equals(REMOVE_CONTRIBUTING_COMMISSION_PROFILES))
        {
            returnPage = removeContributingCommissionProfiles(appReqBlock);
        }
        else if (action.equals(ADD_AGENT_BONUS_PREMIUM_LEVEL))
        {
            returnPage = addAgentBonusPremiumLevel(appReqBlock);
        }
        else if (action.equals(SAVE_AGENT_BONUS_PREMIUM_LEVEL))
        {
            returnPage = saveAgentBonusPremiumLevel(appReqBlock);
        }
        else if (action.equals(CLEAR_AGENT_BONUS_PREMIUM_LEVEL))
        {
            returnPage = clearAgentBonusPremiumLevel(appReqBlock);
        }
        else if (action.equals(DELETE_AGENT_BONUS_PREMIUM_LEVEL))
        {
            returnPage = deleteAgentBonusPremiumLevel(appReqBlock);
        }
        else if (action.equals(SHOW_AGENT_BONUS_PREMIUM_LEVEL_DETAIL))
        {
            returnPage = showAgentBonusPremiumLevelDetail(appReqBlock);
        }
        else if (action.equals(SAVE_BONUS_CRITERIA))
        {
            returnPage = saveBonusCriteria(appReqBlock);
        }
        else if (action.equals(DELETE_BONUS_CRITERIA))
        {
            returnPage = deleteBonusCriteria(appReqBlock);
        }
        else if (action.equals(ATTACH_CONTRIBUTING_PRODUCT_STRUCTURES))
        {
            returnPage = attachContributingProductStructures(appReqBlock);
        }
        else if (action.equals(DETACH_CONTRIBUTING_PRODUCT_STRUCTURES))
        {
            returnPage = detachContributingProductStructures(appReqBlock);
        }
        else if (action.equals(SHOW_BONUS_CRITERIA_DETAIL))
        {
            returnPage = showBonusCriteriaDetail(appReqBlock);
        }
        else if (action.equals(ADD_AGENT_BONUS_PROD_CRIT_ASSOC))
        {
            returnPage = addAgentBonusProdCritAssoc(appReqBlock);
        }
        else if (action.equals(CLEAR_AGENT_BONUS_PROD_CRIT_ASSOC))
        {
            returnPage = clearAgentBonusProdCritAssoc(appReqBlock);
        }
        else if (action.equals(SHOW_PARTICIPANT_BONUS_CRITERIA_DETAIL))
        {
            returnPage = showParticipantBonusCriteriaDetail(appReqBlock);
        }
        else if (action.equals(ADD_PARTICIPANT_PROD_CRIT_ASSOC))
        {
            returnPage = addParticipantProdCritAssoc(appReqBlock);
        }
        else if (action.equals(CLEAR_PARTICIPANT_PROD_CRIT_ASSOC))
        {
            returnPage = clearParticipantProdCritAssoc(appReqBlock);
        }
        else if (action.equals(SAVE_PARTICIPANT_BONUS_CRITERIA))
        {
            returnPage = saveParticipantBonusCriteria(appReqBlock);
        }
        else if (action.equals(DELETE_PARTICIPANT_BONUS_CRITERIA))
        {
            returnPage = deleteParticipantBonusCriteria(appReqBlock);
        }
        else if (action.equals(ATTACH_PARTICIPANT_CONTRIBUTING_PRODUCTS))
        {
            returnPage = attachParticipantContributingProducts(appReqBlock);
        }
        else if (action.equals(DETACH_PARTICIPANT_CONTRIBUTING_PRODUCTS))
        {
            returnPage = detachParticipantContributingProducts(appReqBlock);
        }
        else if (action.equals(SAVE_PARTICIPANT_PREMIUM_LEVEL))
        {
            returnPage = saveParticipantPremiumLevel(appReqBlock);
        }
        else if (action.equals(SHOW_PARTICIPANT_PREMIUM_LEVEL_DETAIL))
        {
            returnPage = showParticipantPremiumLevelDetail(appReqBlock);
        }
        else if (action.equals(CLEAR_PARTICIPANT_PREMIUM_LEVEL))
        {
            returnPage = clearParticipantPremiumLevel(appReqBlock);
        }
        else if (action.equals(DELETE_PARTICIPANT_PREMIUM_LEVEL))
        {
            returnPage = deleteParticipantPremiumLevel(appReqBlock);
        }
        else
        {
            String transaction = appReqBlock.getReqParm("transaction");

            throw new RuntimeException("Unrecognized Transaction/Action [" + transaction + " / " + action + "]");
        }

        return returnPage;
    }

    /**
     * Removes ProductStructures that will contribute to the BonusProgram.
     * @param appReqBlock
     * @return
     */
    private String removeContributingCommissionProfiles(AppReqBlock appReqBlock)
    {
        String selectedCommissionProfilePKs = Util.initString(appReqBlock.getReqParm("selectedCommissionProfilePKs"), null);

        String bonusProgramPK = Util.initString(appReqBlock.getReqParm("bonusProgramPK"), null);

        Agent agent = new AgentComponent();

        String[] commissionProfilePKs = Util.fastTokenizer(selectedCommissionProfilePKs, ",");

        String responseMessage = null;

        for (int i = 0; i < commissionProfilePKs.length; i++)
        {
            agent.removeContributingCommissionProfile(new Long(commissionProfilePKs[i]), new Long(bonusProgramPK));
        }

        responseMessage = "Profiles(s) Successfully Removed From Contribution";

        appReqBlock.getHttpServletRequest().setAttribute("responseMessage", responseMessage);

        return showCommssionProfileSelection(appReqBlock);
    }

    private String addAgentBonusPremiumLevel(AppReqBlock appReqBlock)
    {
        new AgentBonusPremiumLevelTableModel(appReqBlock).resetAllRows();

        return showBonusProgramDetail(appReqBlock);
    }

    private String saveAgentBonusPremiumLevel(AppReqBlock appReqBlock)
    {
        PremiumLevel premiumLevel = (PremiumLevel) Util.mapFormDataToHibernateEntity(appReqBlock.getHttpServletRequest(),
                                                                                     PremiumLevel.class,
                                                                                     SessionHelper.EDITSOLUTIONS, false);

        Long bonusProgramPK = new Long(appReqBlock.getReqParm("bonusProgramPK"));

        Agent agent = new AgentComponent();

        agent.savePremiumLevel(premiumLevel, bonusProgramPK);

        new AgentBonusPremiumLevelTableModel(appReqBlock).resetAllRows();

        appReqBlock.putInRequestScope("pageMessage", new EDITList().addTo("Premium Level Successfully Saved/Updated"));

        return showBonusProgramDetail(appReqBlock);
    }

    private String clearAgentBonusPremiumLevel(AppReqBlock appReqBlock)
    {
        new AgentBonusPremiumLevelTableModel(appReqBlock).resetAllRows();

        return showBonusProgramDetail(appReqBlock);
    }

    private String deleteAgentBonusPremiumLevel(AppReqBlock appReqBlock)
    {
        String premiumLevelPK = new AgentBonusPremiumLevelTableModel(appReqBlock).getSelectedRowId();

        Agent agent = new AgentComponent();

        agent.deletePremiumLevel(new Long(premiumLevelPK));

        new AgentBonusPremiumLevelTableModel(appReqBlock).resetAllRows();

        appReqBlock.putInRequestScope("pageMessage", new EDITList().addTo("Premium Level Successfully Deleted"));

        return showBonusProgramDetail(appReqBlock);
    }

    private String showAgentBonusPremiumLevelDetail(AppReqBlock appReqBlock)
    {
        String premiumLevelPK = new AgentBonusPremiumLevelTableModel(appReqBlock).getSelectedRowId();

        PremiumLevel premiumLevel = PremiumLevel.findByPK(new Long(premiumLevelPK));

        appReqBlock.putInRequestScope("selectedPremiumLevel", premiumLevel);

        return showBonusProgramDetail(appReqBlock);
    }

    private String saveParticipantPremiumLevel(AppReqBlock appReqBlock) throws Exception
    {
        PremiumLevel premiumLevel = (PremiumLevel) Util.mapFormDataToHibernateEntity(appReqBlock.getHttpServletRequest(),
                                                                                     PremiumLevel.class,
                                                                                     SessionHelper.EDITSOLUTIONS, false);

        Long participatingAgentPK = new Long(appReqBlock.getReqParm("participatingAgentPK"));

        Agent agent = new AgentComponent();

        agent.saveParticipantPremiumLevel(premiumLevel, participatingAgentPK);

        appReqBlock.putInRequestScope("responseMessage", "Premium Level Successfully Saved/Updated");

        return showParticipatingAgentDetail(appReqBlock);
    }

    private String clearParticipantPremiumLevel(AppReqBlock appReqBlock) throws Exception
    {
        appReqBlock.setReqParm("premiumLevlePK", null);

        return showParticipatingAgentDetail(appReqBlock);
    }

    private String deleteParticipantPremiumLevel(AppReqBlock appReqBlock)
    {
        String premiumLevelPK = appReqBlock.getReqParm("premiumLevlePK");

        Agent agent = new AgentComponent();

        agent.deleteParticipantPremiumLevel(new Long(premiumLevelPK));

        appReqBlock.putInRequestScope("responseMessage", "Premium Level Successfully Deleted");
        
        appReqBlock.setReqParm("premiumLevlePK", null);

        return showParticipatingAgentDetail(appReqBlock);
    }

    private String showParticipantPremiumLevelDetail(AppReqBlock appReqBlock) throws Exception
    {
        String premiumLevelPK = appReqBlock.getReqParm("premiumLevlePK");

        PremiumLevel premiumLevel = PremiumLevel.findByPK(new Long(premiumLevelPK));

        appReqBlock.putInRequestScope("selectedPremiumLevel", premiumLevel);

        return showParticipatingAgentDetail(appReqBlock);
    }

    private String addAgentBonusProdCritAssoc(AppReqBlock appReqBlock)
    {
        appReqBlock.setReqParm("bonusCriteriaPK", null);

        return showAgentBonusProductsCriteriaAssociation(appReqBlock);
    }

    private String saveBonusCriteria(AppReqBlock appReqBlock)
    {
        BonusCriteria bonusCriteria = (BonusCriteria) Util.mapFormDataToHibernateEntity(appReqBlock.getHttpServletRequest(),
                                                                                        BonusCriteria.class,
                                                                                        SessionHelper.EDITSOLUTIONS, false);

        Long premiumLevelPK = new Long(appReqBlock.getReqParm("premiumLevelPK"));

        Agent agent = new AgentComponent();

        agent.saveBonusCriteria(bonusCriteria, premiumLevelPK);

        appReqBlock.setReqParm("bonusCriteriaPK", null);

        appReqBlock.getHttpServletRequest().setAttribute("responseMessage", "Bonus Criteria Successfully Saved/Updated");

        return showAgentBonusProductsCriteriaAssociation(appReqBlock);
    }

    private String clearAgentBonusProdCritAssoc(AppReqBlock appReqBlock)
    {
        appReqBlock.setReqParm("bonusCriteriaPK", null);

        return showAgentBonusProductsCriteriaAssociation(appReqBlock);
    }

    private String deleteBonusCriteria(AppReqBlock appReqBlock)
    {
        Long bonusCriteriaPK = new Long(appReqBlock.getReqParm("bonusCriteriaPK"));

        Agent agent = new AgentComponent();

        agent.deleteBonusCriteria(new Long(bonusCriteriaPK));

        appReqBlock.setReqParm("bonusCriteriaPK", null);

        appReqBlock.putInRequestScope("responseMessage", "Bonus Criteria Successfully Deleted");

        return showAgentBonusProductsCriteriaAssociation(appReqBlock);
    }

    /**
     * Adds to the set of CommissionProfiles that can contribute to a BonusProgram.
     * @param appReqBlock
     * @return
     */
    private String addContributingCommissionProfiles(AppReqBlock appReqBlock)
    {
        String selectedCommissionProfilePKs = Util.initString(appReqBlock.getReqParm("selectedCommissionProfilePKs"), null);

        String bonusProgramPK = Util.initString(appReqBlock.getReqParm("bonusProgramPK"), null);

        Agent agent = new AgentComponent();

        String[] commissionProfilePKs = Util.fastTokenizer(selectedCommissionProfilePKs, ",");

        String responseMessage = null;

        for (int i = 0; i < commissionProfilePKs.length; i++)
        {
            agent.addContributingCommissionProfile(new Long(commissionProfilePKs[i]), new Long(bonusProgramPK));
        }

        responseMessage = "Profiles(s) Successfully Added For Contribution";

        appReqBlock.getHttpServletRequest().setAttribute("responseMessage", responseMessage);

        return showCommssionProfileSelection(appReqBlock);
    }

    /**
     * Attach ProductStructures that will contribute to the BonusCriteria.
     * @param appReqBlock
     * @return
     */
    private String attachContributingProductStructures(AppReqBlock appReqBlock)
    {
        String selectedProductStructurePKs = appReqBlock.getReqParm("selectedProductStructurePKs");

        Long bonusCriteriaPK = new Long(appReqBlock.getReqParm("bonusCriteriaPK"));

        Agent agent = new AgentComponent();

        String[] productStructurePKs = Util.fastTokenizer(selectedProductStructurePKs, ",");

        for (int i = 0; i < productStructurePKs.length; i++)
        {
            agent.attachBonusContributingProducts(bonusCriteriaPK, new Long(productStructurePKs[i]));
        }

        String responseMessage = "Product(s) Successfully Added For Contribution";

        appReqBlock.putInRequestScope("responseMessage", responseMessage);

        return showAgentBonusProductsCriteriaAssociation(appReqBlock);
    }

    /**
     * Detaches ProductStructures that will contribute to the BonusProgram.
     * @param appReqBlock
     * @return
     */
    private String detachContributingProductStructures(AppReqBlock appReqBlock) throws Exception
    {
        String selectedProductStructurePKs = appReqBlock.getReqParm("selectedProductStructurePKs");

        Long bonusCriteriaPK = new Long(appReqBlock.getReqParm("bonusCriteriaPK"));

        Agent agent = new AgentComponent();

        String[] productStructurePKs = Util.fastTokenizer(selectedProductStructurePKs, ",");

        for (int i = 0; i < productStructurePKs.length; i++)
        {
            agent.detachBonusContributingProducts(bonusCriteriaPK, new Long(productStructurePKs[i]));
        }

        String responseMessage = "Product(s) Successfully Removed From Contribution";

        appReqBlock.getHttpServletRequest().setAttribute("responseMessage", responseMessage);

        return showAgentBonusProductsCriteriaAssociation(appReqBlock);
    }

    /**
     * Deletes the specified Participating Agent.
     * @param appReqBlock
     * @return
     */
    private String deleteParticipatingAgent(AppReqBlock appReqBlock)
    {
        String participatingAgentPK = appReqBlock.getReqParm("participatingAgentPK");

        Agent agent = new AgentComponent();

        agent.deleteParticipatingAgent(new Long(participatingAgentPK));

        return showParticipantSelection(appReqBlock);
    }

    /**
     * Cancels current Participating Agent edits.
     * @param appReqBlock
     * @return
     */
    private String cancelParticipatingAgent(AppReqBlock appReqBlock)
    {
        return showParticipantSelection(appReqBlock);
    }

    /**
     * Shows the detail of the selected Participating Agent.
     * @param appReqBlock
     * @return
     */
    private String showParticipatingAgentDetail(AppReqBlock appReqBlock)
    {
        Long participatingAgentPK = new Long(appReqBlock.getReqParm("participatingAgentPK"));

        ParticipatingAgent participatingAgent = ParticipatingAgent.findByPK(participatingAgentPK);

        appReqBlock.putInRequestScope("selectedParticipatingAgent", participatingAgent);

        return showParticipantSelection(appReqBlock);
    }

    /**
     * Saves the details of the Participating Agent.
     * @param appReqBlock
     * @return
     */
    private String saveParticipatingAgent(AppReqBlock appReqBlock)
    {
        ParticipatingAgent participatingAgent = (ParticipatingAgent) Util.mapFormDataToHibernateEntity(appReqBlock.getHttpServletRequest(),
                                                                                                       ParticipatingAgent.class,
                                                                                                       SessionHelper.EDITSOLUTIONS, false);

        Long bonusProgramPK = new Long(appReqBlock.getReqParm("bonusProgramPK"));

        Long placedAgentPK = new Long(appReqBlock.getReqParm("placedAgentPK"));

        Agent agent = new AgentComponent();

        agent.saveParticipatingAgent(participatingAgent, bonusProgramPK, placedAgentPK);

        appReqBlock.putInRequestScope("responseMessage", "Participating Agent Successfully Saved/Updated");

        return showParticipantSelection(appReqBlock);
    }

    /**
     * Attach ProductStructures that will contribute to the BonusProgram.
     * @param appReqBlock
     * @return
     */
    private String attachParticipantContributingProducts(AppReqBlock appReqBlock)
    {
        String selectedProductStructurePKs = appReqBlock.getReqParm("selectedProductStructurePKs");

        Long bonusCriteriaPK = new Long(new ParticipantBonusCriteriaTableModel(appReqBlock).getSelectedRowId());

        Agent agent = new AgentComponent();

        String[] productStructurePKs = Util.fastTokenizer(selectedProductStructurePKs, ",");

        for (int i = 0; i < productStructurePKs.length; i++)
        {
            agent.attachParticipantContributingProducts(bonusCriteriaPK, new Long(productStructurePKs[i]));
        }

        String responseMessage = "Product(s) Successfully Added For Contribution";

        appReqBlock.putInRequestScope("responseMessage", responseMessage);

        new ParticipantBonusCriteriaTableModel(appReqBlock).resetAllRows();

        return showParticipantProdCriteriaAssoc(appReqBlock);
    }

    /**
     * Detaches ProductStructures that will contribute to the BonusProgram.
     * @param appReqBlock
     * @return
     */
    private String detachParticipantContributingProducts(AppReqBlock appReqBlock)
    {
        String selectedProductStructurePKs = appReqBlock.getReqParm("selectedProductStructurePKs");

        Long bonusCriteriaPK = new Long(new ParticipantBonusCriteriaTableModel(appReqBlock).getSelectedRowId());

        Agent agent = new AgentComponent();

        String[] productStructurePKs = Util.fastTokenizer(selectedProductStructurePKs, ",");

        for (int i = 0; i < productStructurePKs.length; i++)
        {
            agent.detachParticipantContributingProducts(bonusCriteriaPK, new Long(productStructurePKs[i]));
        }

        String responseMessage = "Product(s) Successfully Removed From Contribution";

        appReqBlock.getHttpServletRequest().setAttribute("responseMessage", responseMessage);

        new ParticipantBonusCriteriaTableModel(appReqBlock).resetAllRows();

        return showParticipantProdCriteriaAssoc(appReqBlock);
    }

    /**
     * Returns the Participating Agent screen with the just-selected PlacedAgent data.
     * @param appReqBlock
     * @return
     */
    private String showParticipatingAgentAfterSearch(AppReqBlock appReqBlock) throws Exception
    {
        String placedAgentPK = appReqBlock.getReqParm("placedAgentPK");

        appReqBlock.putInRequestScope("placedAgentPK", placedAgentPK);

        return showParticipantSelection(appReqBlock);
    }

    /**
     * Finder for search dialog.
     *
     * @param appReqBlock
     * @return
     */
    private String findPlacedAgentsByCommissionContractCTAndAgentName(AppReqBlock appReqBlock)
    {
        String contractCodeCT = appReqBlock.getReqParm("contractCodeCT");

        String agentName = appReqBlock.getReqParm("agentName");

        String bonusProgramPK = appReqBlock.getReqParm("bonusProgramPK");

        BonusProgram bonusProgram = BonusProgram.findByPK(new Long(bonusProgramPK));

        EDITDate startDate = bonusProgram.getBonusStartDate();

        EDITDate stopDate = bonusProgram.getBonusStopDate();

        Agent agent = new AgentComponent();

        PlacedAgentVO[] placedAgentVOs = agent.findPlacedAgentsBy_CommissionContractCT_AgentName_StartDate_StopDate(contractCodeCT, agentName, startDate.getFormattedDate(), stopDate.getFormattedDate());

        appReqBlock.putInRequestScope("placedAgentVOs", placedAgentVOs);

        return showAgentBonusSearchDialog(appReqBlock);
    }

    /**
     * Finder for search dialog.
     *
     * @param appReqBlock
     * @return
     */
    private String findPlacedAgentsByCommissionContractCTAndAgentNumber(AppReqBlock appReqBlock)
    {
        String contractCodeCT = appReqBlock.getReqParm("contractCodeCT");

        String agentNumber = appReqBlock.getReqParm("agentNumber");

        String bonusProgramPK = appReqBlock.getReqParm("bonusProgramPK");

        BonusProgram bonusProgram = BonusProgram.findByPK(new Long(bonusProgramPK));

        EDITDate startDate = bonusProgram.getBonusStartDate();

        EDITDate stopDate = bonusProgram.getBonusStopDate();

        Agent agent = new AgentComponent();

        PlacedAgentVO[] placedAgentVOs = agent.findPlacedAgentsBy_CommissionContractCT_AgentNumber_StartDate_StopDate(contractCodeCT, agentNumber, startDate.getFormattedDate(), stopDate.getFormattedDate());

        appReqBlock.putInRequestScope("placedAgentVOs", placedAgentVOs);

        return showAgentBonusSearchDialog(appReqBlock);
    }

    /**
     * Finder for search dialog.
     *
     * @param appReqBlock
     * @return
     */
    private String findPlacedAgentsByCommissionContractCTAndCommissionLevelCT(AppReqBlock appReqBlock)
    {
        String contractCodeCT = appReqBlock.getReqParm("contractCodeCT");

        String commissionLevelCT = appReqBlock.getReqParm("commissionLevelCT");

        String bonusProgramPK = appReqBlock.getReqParm("bonusProgramPK");

        BonusProgram bonusProgram = BonusProgram.findByPK(new Long(bonusProgramPK));

        EDITDate startDate = bonusProgram.getBonusStartDate();

        EDITDate stopDate = bonusProgram.getBonusStopDate();

        Agent agent = new AgentComponent();

        PlacedAgentVO[] placedAgentVOs = agent.findPlacedAgentsBy_ContractCodeCT_CommissionLevelCT_StartDate_StopDate(contractCodeCT, commissionLevelCT, startDate.getFormattedDate(), stopDate.getFormattedDate());

        appReqBlock.putInRequestScope("placedAgentVOs", placedAgentVOs);

        return showAgentBonusSearchDialog(appReqBlock);
    }

    /**
     * Shows the search dialog for finding Participating Agents.
     *
     * @param appReqBlock
     * @return
     */
    private String showAgentBonusSearchDialog(AppReqBlock appReqBlock)
    {
        return AGENT_BONUS_SEARCH_DIALOG;
    }

    /**
     * Shows the detail of the user-selected BonusProgram.
     *
     * @param appReqBlock
     * @return
     */
    private String showBonusProgramDetail(AppReqBlock appReqBlock)
    {
        String bonusProgramPK = appReqBlock.getReqParm("bonusProgramPK");

        BonusProgram bonusProgram = BonusProgram.findByPK(new Long(bonusProgramPK));

        appReqBlock.putInRequestScope("selectedBonusProgram", bonusProgram);

        return showBonusProgram(appReqBlock);
    }

    /**
     * Initializes the bonus program page for a new entry.
     *
     * @param appReqBlock
     * @return
     */
    private String addBonusProgram(AppReqBlock appReqBlock)
    {
        appReqBlock.setReqParm("bonusProgramPK", null);

        return showBonusProgram(appReqBlock);
    }


    /**
     * Saves or update the BonusProgram.
     *
     * @param appReqBlock
     * @return
     */
    private String saveBonusProgram(AppReqBlock appReqBlock)
    {
        BonusProgram bonusProgram = (BonusProgram) Util.mapFormDataToHibernateEntity(appReqBlock.getHttpServletRequest(),
                                                                                     BonusProgram.class,
                                                                                     SessionHelper.EDITSOLUTIONS, false);

        Agent agent = new AgentComponent();

        agent.saveBonusProgram(bonusProgram);

        appReqBlock.setReqParm("bonusProgramPK", null);

        new AgentBonusPremiumLevelTableModel(appReqBlock).resetAllRows();

        appReqBlock.putInRequestScope("pageMessage", new EDITList().addTo("Bonus Program Successfully Saved/Updated"));

        return showBonusProgram(appReqBlock);
    }

    /**
     * Cancels any current BonusProgram edits.
     *
     * @param appReqBlock
     * @return
     */
    private String cancelBonusProgram(AppReqBlock appReqBlock)
    {
        appReqBlock.setReqParm("bonusProgramPK", null);

        return showBonusProgram(appReqBlock);
    }

    /**
     * Deletes the selected BonusProgram.
     *
     * @param appReqBlock
     * @return
     */
    private String deleteBonusProgram(AppReqBlock appReqBlock)
    {
        String bonusProgramPK = appReqBlock.getReqParm("bonusProgramPK");

        BonusProgram bonusProgram = BonusProgram.findByPK(new Long(bonusProgramPK));

        Agent agent = new AgentComponent();

        agent.deleteBonusProgram(bonusProgram);

        appReqBlock.setReqParm("bonusProgramPK", null);

        new AgentBonusPremiumLevelTableModel(appReqBlock).resetAllRows();

        appReqBlock.putInRequestScope("pageMessage", new EDITList().addTo("Bonus Program Successfully Deleted"));

        return showBonusProgram(appReqBlock);
    }

    /**
     * Renders the agent bonus level selection page.
     * @param appReqBlock
     * @return
     */
    private String showCommssionProfileSelection(AppReqBlock appReqBlock)
    {
        String bonusProgramPK = appReqBlock.getReqParm("bonusProgramPK");

        String contractCodeCT = appReqBlock.getReqParm("contractCodeCT");

        Agent agent = new AgentComponent();

        CommissionProfileVO[] commissionProfileVOs = agent.findCommissionProfilesBy_ContractCodeCT(contractCodeCT);

        appReqBlock.putInRequestScope("bonusProgramPK", bonusProgramPK);

        appReqBlock.putInRequestScope("contractCodeCT", contractCodeCT);

        appReqBlock.putInRequestScope("commissionProfileVOs", commissionProfileVOs);

        return AGENT_BONUS_COMMSSION_PROFILE_SELECTION;
    }

    /**
     * Render the agent bonus product and criteria association page.
     * @param appReqBlock
     * @return
     */
    private String showAgentBonusProductsCriteriaAssociation(AppReqBlock appReqBlock)
    {
        Long premiumLevelPK = new Long(appReqBlock.getReqParm("premiumLevelPK"));

        PremiumLevel premiumLevel = PremiumLevel.findByPK(premiumLevelPK);

        appReqBlock.putInRequestScope("selectedPremiumLevel", premiumLevel);

        ProductStructure[] productStructures = ProductStructure.findByTypeCodeCT(ProductStructure.TYPECODECT_PRODUCT);

        appReqBlock.putInRequestScope("productStructures", productStructures);

        return AGENT_BONUS_PRODUCT_CRITERIA_ASSOCIATION;
    }

    private String showBonusCriteriaDetail(AppReqBlock appReqBlock)
    {
        String bonusCriteriaPK = appReqBlock.getReqParm("bonusCriteriaPK");

        BonusCriteria bonusCriteria = BonusCriteria.findByPK(new Long(bonusCriteriaPK));

        appReqBlock.putInRequestScope("selectedBonusCriteria", bonusCriteria);

        return showAgentBonusProductsCriteriaAssociation(appReqBlock);
    }

    /**
     * Render the agent bonus participant product and criteria association page (for overriding
     * bonus criteria at the participant level).
     * @param appReqBlock
     * @return
     */
    private String showParticipantProdCriteriaAssoc(AppReqBlock appReqBlock)
    {
        Long bonusProgramPK = new Long(appReqBlock.getReqParm("bonusProgramPK"));

        BonusProgram bonusProgram = BonusProgram.findByPK(bonusProgramPK);

        Long premiumLevelPK = new Long(appReqBlock.getReqParm("premiumLevelPK"));

        PremiumLevel premiumLevel = PremiumLevel.findByPK(premiumLevelPK);

        appReqBlock.putInRequestScope("selectedPremiumLevel", premiumLevel);

        new ParticipantBonusCriteriaTableModel(appReqBlock);

        ProductStructure[] validProductStructures = bonusProgram.getProductStructures();

        validProductStructures = (ProductStructure[]) Util.sortObjects(validProductStructures, new String[] {"getProductStructurePK"});

        appReqBlock.putInRequestScope("productStructures", validProductStructures);

        return PARTICIPANT_PRODUCT_CRITERIA_ASSOCIATION;
    }

    private String showParticipantBonusCriteriaDetail(AppReqBlock appReqBlock)
    {
        String bonusCriteriaPK = new ParticipantBonusCriteriaTableModel(appReqBlock).getSelectedRowId();

        BonusCriteria bonusCriteria = BonusCriteria.findByPK(new Long(bonusCriteriaPK));

        appReqBlock.putInRequestScope("selectedBonusCriteria", bonusCriteria);

        return showParticipantProdCriteriaAssoc(appReqBlock);
    }

    private String addParticipantProdCritAssoc(AppReqBlock appReqBlock)
    {
        new ParticipantBonusCriteriaTableModel(appReqBlock).resetAllRows();

        return showParticipantProdCriteriaAssoc(appReqBlock);
    }

    private String saveParticipantBonusCriteria(AppReqBlock appReqBlock)
    {
        BonusCriteria bonusCriteria = (BonusCriteria) Util.mapFormDataToHibernateEntity(appReqBlock.getHttpServletRequest(),
                                                                                        BonusCriteria.class,
                                                                                        SessionHelper.EDITSOLUTIONS, false);

        Long premiumLevelPK = new Long(appReqBlock.getReqParm("premiumLevelPK"));

        Agent agent = new AgentComponent();

        agent.saveBonusCriteria(bonusCriteria, premiumLevelPK);

        new ParticipantBonusCriteriaTableModel(appReqBlock).resetAllRows();

        appReqBlock.putInRequestScope("responseMessage", "Bonus Criteria Successfully Saved/Updated");

        return showParticipantProdCriteriaAssoc(appReqBlock);
    }

    private String clearParticipantProdCritAssoc(AppReqBlock appReqBlock)
    {
        new ParticipantBonusCriteriaTableModel(appReqBlock).resetAllRows();

        return showParticipantProdCriteriaAssoc(appReqBlock);
    }

    private String deleteParticipantBonusCriteria(AppReqBlock appReqBlock)
    {
        String bonusCriteriaPK = new ParticipantBonusCriteriaTableModel(appReqBlock).getSelectedRowId();

        Agent agent = new AgentComponent();

        agent.deleteBonusCriteria(new Long(bonusCriteriaPK));

        new ParticipantBonusCriteriaTableModel(appReqBlock).resetAllRows();

        appReqBlock.putInRequestScope("responseMessage", "Bonus Criteria Successfully Deleted");

        return showParticipantProdCriteriaAssoc(appReqBlock);
    }

    /**
     * Renders the agent bonus program page.
     *
     * @param appReqBlock
     * @return
     */
    private String showBonusProgram(AppReqBlock appReqBlock)
    {
        BonusProgram[] bonusPrograms = BonusProgram.findAll();

        appReqBlock.putInRequestScope("bonusPrograms", bonusPrograms);

        new AgentBonusPremiumLevelTableModel(appReqBlock);

        appReqBlock.putInRequestScope("toolbar", AGENT_BONUS_TOOLBAR);

        appReqBlock.putInRequestScope("main", AGENT_BONUS_PROGRAM);

        return TEMPLATE_TOOLBAR_MAIN;
    }

    /**
     * Renders the participant selection page.
     *
     * @param appReqBlock
     * @return
     */
    private String showParticipantSelection(AppReqBlock appReqBlock)
    {
        Long bonusProgramPK = new Long(appReqBlock.getReqParm("bonusProgramPK"));

        BonusProgram bonusProgram = BonusProgram.findByPK(bonusProgramPK);

        appReqBlock.putInRequestScope("selectedBonusProgram", bonusProgram);

        ParticipatingAgent[] participatingAgents = (ParticipatingAgent[]) bonusProgram.getParticipatingAgents().
                                                            toArray(new ParticipatingAgent[bonusProgram.getParticipatingAgents().size()]);

        // to display records in consistent order.
        participatingAgents = (ParticipatingAgent[]) Util.sortObjects(participatingAgents, new String[] {"getParticipatingAgentPK"});

        appReqBlock.putInRequestScope("participatingAgents", participatingAgents);

        return AGENT_BONUS_PARTICIPANT_SELECTION;
    }
}
