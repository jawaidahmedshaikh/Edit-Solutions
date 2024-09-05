/*
 * User: unknown
 * Date: Jan 1, 2000
 * Time: unknown
 *
 * (c) 2000-2008 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package agent.ui.servlet;

import agent.*;
import agent.business.Agent;
import agent.business.AgentUseCase;
import agent.component.AgentComponent;
import agent.component.AgentUseCaseComponent;
import contract.FilteredRequirement;
import contract.Requirement;
import contract.dm.dao.DAOFactory;
import edit.common.*;
import edit.common.exceptions.EDITAgentException;
import edit.common.exceptions.EDITDeleteException;
import edit.common.exceptions.EDITLockException;
import edit.common.vo.*;
import edit.portal.common.session.UserSession;
import edit.portal.common.transactions.Transaction;
import edit.portal.exceptions.PortalEditingException;
import edit.portal.widget.AgentMoveFromTableModel;
import edit.portal.widget.AgentMoveSelectedTableModel;
import edit.portal.widget.AgentMoveSelectedTableRow;
import edit.portal.widget.AgentMoveToTableModel;
import edit.portal.widget.AgentRequirementsTableModel;
import edit.portal.widget.ContributingProductTableModel;
import edit.portal.widget.PlacedAgentReportToTableModel;
import edit.portal.widgettoolkit.TableModel;
import edit.services.db.hibernate.SessionHelper;
import engine.ProductStructure;
import engine.Company;
import engine.component.CalculatorComponent;
import engine.sp.SPException;
import engine.sp.SPOutput;
import event.business.Event;
import event.component.EventComponent;
import event.component.EventUseCaseComponent;
import fission.beans.PageBean;
import fission.beans.SessionBean;
import fission.global.AppReqBlock;
import fission.utility.DateTimeUtil;
import fission.utility.Util;

import java.util.*;

import logging.Log;
import role.ClientRole;
import role.ClientRoleFinancial;
import security.Operator;
import client.ClientDetail;
import client.Preference;
import client.ClientAddress;
import client.component.ClientUseCaseComponent;


public class AgentDetailTran extends Transaction
{
    //These actions are for showing the tabs of the agent system
    private static final String SHOW_AGENT_DETAIL_MAIN_DEFAULT = "showAgentDetailMainDefault";
    private static final String SHOW_AGENT_DETAIL_MAIN_CONTENT = "showAgentDetailMainContent";
    private static final String SHOW_JUMP_TO_DIALOG = "showJumpToDialog";
    private static final String SHOW_VO_EDIT_EXCEPTION_DIALOG = "showVOEditExceptionDialog";
    private static final String SHOW_VESTING_DIALOG = "showVestingDialog";
    private static final String SHOW_PAYMENT_ROUTING_INFO_DIALOG = "showPaymentRoutingInfoDialog";
    private static final String SHOW_TAX_INFO_DIALOG = "showTaxInfoDialog";
    private static final String SHOW_NOTES_DIALOG = "showNotesDialog";
    private static final String SHOW_NOTES_DETAIL_SUMMARY = "showNotesDetailSummary";
    private static final String SHOW_FINANCIAL = "showFinancial";
    private static final String SHOW_SELECTED_AGENT_FINANCIAL = "showSelectedAgentFinancial";
    private static final String SHOW_ACCUMULATIONS_DIALOG = "showAccumulationsDialog";
    private static final String SHOW_NY_91_PCT_RULE_DIALOG = "showNY91PctRuleDialog";
    private static final String SHOW_DEBIT_BALANCE_DIALOG = "showDebitBalanceDialog";
    private static final String SHOW_LICENSE = "showLicense";
    private static final String SHOW_LICENSE_DETAIL_SUMMARY = "showLicenseDetailSummary";
    private static final String SHOW_CONTRACT = "showContract";
    private static final String SHOW_CONTRACT_DETAIL_SUMMARY = "showContractDetailSummary";
    private static final String SHOW_ADDITIONAL_COMPENSATION_DIALOG = "showAdditionalCompensationDialog";
    private static final String SHOW_ADJUSTMENTS = "showAdjustments";
    private static final String SHOW_ADJUSTMENT_DETAIL_SUMMARY = "showAdjustmentDetailSummary";
    private static final String SHOW_ADJUST_ACCUMS_DIALOG = "showAdjustAccumsDialog";
    private static final String SHOW_ANNUALIZED_DIALOG = "showAnnualizedDialog";
    private static final String SHOW_REDIRECT = "showRedirect";
    private static final String SHOW_REDIRECT_DETAIL_SUMMARY = "showRedirectDetailSummary";
    private static final String SHOW_REDIRECT_ACCUMS_DIALOG = "showRedirectAccumsDialog";
    private static final String SHOW_REDIRECT_SEARCH_DIALOG = "showRedirectSearchDialog";
    private static final String SHOW_REDIRECT_AFTER_SEARCH = "showRedirectAfterSearch";
    private static final String SHOW_HISTORY = "showHistory";
    private static final String SHOW_HISTORY_DETAIL_SUMMARY = "showHistoryDetailSummary";
    private static final String SHOW_ALLOWANCES_DIALOG = "showAllowancesDialog";
    private static final String SHOW_COMMISSION_CONTRACT = "showCommissionContract";
    private static final String SHOW_CONTRACT_CODE_DIALOG = "showContractCodeDialog";
    private static final String SHOW_COMMISSION_LEVEL_DIALOG = "showCommissionLevelDialog";
    private static final String SHOW_PROFILE_DETAIL_SUMMARY = "showProfileDetailSummary";
    private static final String SHOW_CANCEL_AGENT_CONFIRMATION_DIALOG = "showCancelAgentConfirmationDialog";
    private static final String SHOW_EXTRACT_DIALOG = "showExtractDialog";
    private static final String SHOW_EXTRACT_DETAIL_SUMMARY = "showExtractDetailSummary";
    private static final String SHOW_EXTRACT_ALLOWANCES_DIALOG = "showExtractAllowancesDialog";
    private static final String SHOW_DELETE_AGENT_CONFIRM_DIALOG = "showDeleteAgentConfirmationDialog";
    private static final String SHOW_HIERARCHY_REPORT = "showHierarchyReport";
    private static final String SHOW_COMM_HISTORY_ADJUSTMENT_DIALOG = "showCommHistoryAdjustmentDialog";
    private static final String SHOW_COMM_HISTORY_FILTER_DIALOG = "showCommissionHistoryFilterDialog";
    private static final String ADD_NEW_AGENT = "addNewAgent";
    private static final String ADD_OR_CANCEL_PROFILE = "addOrCancelProfile";
    private static final String ADD_NEW_EXTRACT = "addNewExtract";
    private static final String BUILD_AGENT_ROLE = "buildAgentRole";
    private static final String CANCEL_ADDITIONAL_COMPENSATION = "cancelAdditionalCompensation";
    private static final String CANCEL_AGENT = "cancelAgent";
    private static final String CANCEL_CURRENT_EXTRACT = "cancelCurrentExtract";
    private static final String CANCEL_COMM_HISTORY_ADJUSTMENT = "cancelCommHistoryAdjustment";
    private static final String CLEAR_FORM_FOR_ADD_OR_CANCEL = "clearFormForAddOrCancel";
    private static final String CLOSE_NOTES_DIALOG = "closeNotesDialog";
    private static final String CLOSE_EXTRACT_DIALOG = "closeExtractDialog";
    private static final String DELETE_AGENT = "deleteAgent";
    private static final String DELETE_COMMISSION_PROFILE = "deleteCommissionProfile";
    private static final String DELETE_CURRENT_NOTE = "deleteCurrentNote";
    private static final String DELETE_LICENSE = "deleteLicense";
    private static final String DELETE_CONTRACT = "deleteContract";
    private static final String DELETE_REDIRECT = "deleteRedirect";
    private static final String DELETE_SELECTED_EXTRACT = "deleteSelectedExtract";
    private static final String DELETE_ADJUSTMENT = "deleteAdjustment";
    private static final String FILTER_COMMISSION_HISTORY = "filterCommissionHistory";
    private static final String FIND_AGENTS_BY_NAME_DOB = "findAgentsByNameDOB";
    private static final String FIND_AGENTS_BY_NAME = "findAgentsByName";
    private static final String FIND_AGENTS_BY_TAX_ID = "findAgentsByTaxId";
    private static final String FIND_AGENTS_BY_AGENT_ID = "findAgentsByAgentId";
    private static final String LOAD_AGENT_DETAIL_AFTER_SEARCH = "loadAgentDetailAfterSearch";
    private static final String LOCK_AGENT = "lockAgent";
    private static final String SELECT_CONTRACT_CODE = "selectContractCode";
    private static final String SELECT_COMMISSION_LEVEL = "selectCommissionLevel";
    private static final String SELECT_COMMISSION_CONTRACT_FOR_AGENT = "selectCommissionContractForAgent";
    private static final String SAVE_NOTE_TO_SUMMARY = "saveNoteToSummary";
    private static final String SAVE_VESTING = "saveVesting";
    private static final String SAVE_DEBIT_BALANCE = "saveDebitBalance";
    private static final String SAVE_CONTRACT_CODE = "saveContractCode";
    private static final String SAVE_COMMISSION_LEVEL_DESCRIPTION = "saveCommissionLevelDescription";
    private static final String SAVE_COMMISSION_PROFILE = "saveCommissionProfile";
    private static final String SAVE_AGENT_DETAILS = "saveAgentDetails";
    private static final String SAVE_LICENSE_TO_SUMMARY = "saveLicenseToSummary";
    private static final String SAVE_CONTRACT_TO_SUMMARY = "saveContractToSummary";
    private static final String SAVE_ADDITIONAL_COMPENSATION = "saveAdditionalCompensation";
    private static final String SAVE_ADJUSTMENT_TO_SUMMARY = "saveAdjustmentToSummary";
    private static final String SAVE_REDIRECT_TO_SUMMARY = "saveRedirectToSummary";
    private static final String SAVE_ALLOWANCES_DIALOG = "saveAllowancesDialog";
    private static final String SAVE_EXTRACT_TO_SUMMARY = "saveExtractToSummary";
    private static final String SAVE_COMM_HISTORY_ADJUSTMENT = "saveCommHistoryAdjustment";
    private static final String SAVE_COMMISSION_HISTORY = "saveCommissionHistory";
    private static final String SHOW_PLACED_AGENTS = "showPlacedAgents";
    private static final String SHOW_AGENT_CONTRACTS = "showAgentContracts";
    private static final String SHOW_AGENT_CONTRACT_HIERARCHIES = "showAgentContractHierarchies";
    private static final String SHOW_PLACED_AGENT_HIERARCHY = "showPlacedAgentHierarchy";
    private static final String SHOW_COMMISSION_PROFILE = "showCommissionProfile";
    private static final String ASSOCIATE_COMMISSION_PROFILE = "associateCommissionProfile";
    private static final String CLEAR_PLACED_AGENTS = "clearPlacedAgents";
    private static final String PLACE_AGENT_CONTRACT = "placeAgentContract";
    private static final String REMOVE_PLACED_AGENT = "removePlacedAgent";
    private static final String SHIFT_PLACED_AGENT = "shiftPlacedAgent";
    private static final String FILTER_EXTRACTS = "filterExtracts";
    private static final String REMOVE_EXTRACT_FILTER = "removeExtractFilter";
    private static final String CANCEL_AGENT_ADD = "cancelAgentAdd";
    private static final String SET_PLACED_AGENT_INACTIVE_IND = "setPlacedAgentInactiveInd";
    private static final String SHOW_COMMISSION_BALANCE_HISTORY = "showCommissionBalanceHistory";
    private static final String SHOW_BONUS_COMMISSION_BALANCE_HISTORY = "showBonusCommissionBalanceHistory";
    private static final String SHOW_COMMISSION_EARNED_HISTORY = "showCommissionEarnedHistory";
    private static final String SHOW_COMMISSION_HELD_HISTORY = "showCommissionHeldHistory";
    private static final String SHOW_COMMISSION_PENDING_UPDATE_HISTORY = "showCommissionPendingUpdateHistory";
    private static final String SHOW_ROLLUP_BALANCE_HISTORY = "showRollupBalanceHistory";
    private static final String SHOW_COMMISSION_CHECK_HISTORY = "showCommissionCheckHistory";
    private static final String FIND_AGENT_CONTRACT_BY_AGENTID_AND_CONTRACTCODECT = "findAgentContractByAgentId_AND_ContractCodeCT";
    private static final String FIND_REPORTOAGENT_BY_AGENTID_AND_CONTRACTCODECT = "findReportToAgentByAgentId_AND_ContractCodeCT";
    private static final String FIND_AGENT_CONTRACT_BY_AGENTNAME_AND_CONTRACTCODECT = "findAgentContractByAgentName_AND_ContractCodeCT";
    private static final String FIND_REPORTTO_AGENT_BY_AGENT_NAME_AND_CONTRACTCODECT = "findReportToAgentByAgentName_AND_ContractCodeCT";
    private static final String SHOW_AGENT_REPORT_TO = "showAgentReportTo";
    private static final String SHOW_REPORT_TO_DETAIL = "showPlacedAgentDetail";
    private static final String SHOW_HIERARCHY = "showHierarchy";
    private static final String UPDATE_PLACED_AGENT_DETAILS = "updatePlacedAgentDetails";
    private static final String CANCEL_PLACED_AGENT_DETAILS = "cancelPlacedAgentDetails";
    private static final String DELETE_PLACED_AGENT = "deletePlacedAgent";
    private static final String SHOW_PLACED_AGENT_GROUP = "showPlacedAgentGroup";
    private static final String COPY_PLACED_AGENT_GROUP = "copyPlacedAgentGroup";
    private static final String SHOW_COMMISSION_LEVEL_SUMMARY_DIALOG = "showCommissionLevelSummaryDialog";
    private static final String SAVE_NOTES_DIALOG = "saveNotesDialog";
    private static final String CANCEL_NOTES_DIALOG = "cancelNotesDialog";
    private static final String ADD_NEW_AGENT_NOTES = "addNewAgentNote";
    private static final String SHOW_EDITING_EXCEPTION_DIALOG = "showEditingExceptionDialog";
    private static final String SHOW_BONUS = "showBonus";
    private static final String SHOW_AGENT_BONUS_DETAIL = "showAgentBonusDetail";
    private static final String SHOW_VALIDATE_HIERARCHY_SELECTION = "showValidateHierarchySelection";
    private static final String VALIDATE_AGENT_HIERARCHY = "validateAgentHierarchy";
    private static final String SHOW_AGENT_GROUP = "showAgentGroup";
    private static final String ADD_AGENT_GROUP = "addAgentGroup";
    private static final String SAVE_AGENT_GROUP = "saveAgentGroup";
    private static final String SEARCH_AGENT_FOR_AGENT_GROUP = "searchAgentForAgentGroup";
    private static final String LOAD_AGENT_FOR_AGENT_GROUP_ADD = "loadAgentForAgentGroupAdd";
    private static final String SHOW_AGENT_GROUP_ASSOCATIONS = "showAgentGroupAssociations";
    private static final String SHOW_AGENT_GROUP_DETAIL = "showAgentGroupDetail";
    private static final String SEARCH_PLACEDAGENTS_FOR_AGENTGROUPASSOCATION = "searchPlacedAgentsForAgentGroupAssociation";
    private static final String CREATE_AGENTGROUPASSOCIATION = "createAgentGroupAssociation";
    private static final String DELETE_AGENTGROUPASSOCIATION = "deleteAgentGroupAssociation";
    private static final String CLOSE_COMMISSION_LEVEL_SUMMARY = "closeCommissionLevelSummary";
    private static final String CANCEL_AGENT_GROUP = "cancelAgentGroup";
    private static final String DELETE_AGENT_GROUP = "deleteAgentGroup";
    private static final String SHOW_AGENT_GROUP_ASSOCIATION_DIALOG = "showAgentGroupAssociationDialog";
    private static final String SAVE_AGENT_GROUP_ASSOCIATION = "saveAgentGroupAssociation";
    private static final String SHOW_AGENT_GROUP_CONTRIBUTING_PRODUCTS = "showAgentGroupContributingProducts";
    private static final String CREATE_CONTRIBUTINGPRODUCT = "createContributingProduct";
    private static final String DELETE_CONTRIBUTINGPRODUCT = "deleteContributingProduct";
    private static final String SAVE_CONTRIBUTING_PRODUCT = "saveContributingProduct";
    private static final String SHOW_REQUIREMENTS = "showRequirements";
    private static final String ADD_REQUIREMENT = "addRequirement";
    private static final String CANCEL_REQUIREMENT = "cancelRequirement";
    private static final String SAVE_REQUIREMENT = "saveRequirement";
    private static final String DELETE_SELECTED_REQUIREMENT = "deleteSelectedRequirement";
    private static final String SHOW_REQUIREMENT_DETAIL = "showRequirementDetail";
    private static final String SAVE_MANUAL_REQUIREMENT = "saveManualRequirement";
    private static final String SHOW_REQUIREMENT_DESCRIPTION = "showRequirementDescription";
    private static final String SHOW_PREFERENCES = "showPreferences";
    private static final String SHOW_SELECTED_PREFERENCE = "showSelectedPreference";
    private static final String CLEAR_PREFERENCE_FOR_ADD = "clearPreferenceForAdd";
    private static final String SHOW_CONTRACT_AGENT_INFO = "showContractAgentInfo";
    private static final String SAVE_PREFERENCE = "savePreference";
    private static final String SELECT_PREFERENCE_FOR_AGENT = "selectPreferenceForAgent";

    private static final String FIND_MOVING_FROM_TO_PLACED_AGENT = "findMovingFromToPlacedAgent";
    private static final String MOVE_AGENT_GROUP = "moveAgentGroup";

    //Pages that the Tran will return
    private static final String AGENT_DETAIL_MAIN_FRAMESET = "/agent/jsp/agentDetailMainframeset.jsp";
    private static final String AGENT_DETAIL_MAIN = "/agent/jsp/agentDetailMain.jsp";
    private static final String AGENT_FINANCIAL = "/agent/jsp/agentFinancial.jsp";
    private static final String AGENT_LICENSE = "/agent/jsp/agentLicense.jsp";
    private static final String AGENT_CONTRACT = "/agent/jsp/agentContract.jsp";
    private static final String AGENT_ADJUSTMENTS = "/agent/jsp/agentAdjustments.jsp";
    private static final String AGENT_REDIRECT = "/agent/jsp/agentRedirect.jsp";
    private static final String COMMISSION_HISTORY_DETAIL = "/agent/jsp/commissionHistoryDetail.jsp";
    private static final String AGENT_ADD = "/agent/jsp/agentAdd.jsp";
    private static final String COMMISSION_CONTRACT = "/agent/jsp/commissionProfile.jsp";
    private static final String PLACED_AGENTS = "/agent/jsp/placedAgents.jsp";
    private static final String AGENT_HIERARCHY_TOOL_BAR = "/agent/jsp/agentHierarchyTool-bar.jsp";
    private static final String BROKER_DEALER_ARRANGEMENT = "/agent/jsp/agentGroup.jsp";
    private static final String ADD_AGENT_GROUP_DIALOG = "/agent/jsp/addAgentGroupDialog.jsp";
    private static final String AGENT_GROUP_ASSOCIATION = "/agent/jsp/agentGroupAssociation.jsp";
    private static final String AGENT_GROUP_CONTRIBUTING_PRODUCTS = "/agent/jsp/agentGroupContributingProducts.jsp";
    private static final String SHOW_CONTRIBUTING_PRODUCT_DIALOG = "showContributingProductDialog";
    private static final String CLEAR_AGENT_MOVE = "clearAgentMove";
    private static final String GENERATE_HIERARCHY_REPORT_BY_PLACEDAGENTPK = "generateHierarchyReportByPlacedAgentPK";

    private static final String TEMPLATE_MAIN = "/common/jsp/template/template-main.jsp";
    private static final String TEMPLATE_TOOLBAR_MAIN = "/common/jsp/template/template-toolbar-main.jsp";

    // Dialog boxes that the Tran will return
    private static final String JUMP_TO_DIALOG = "/common/jsp/jumpToDialog.jsp";
    private static final String VO_EDIT_EXCEPTION_DIALOG = "/common/jsp/VOEditExceptionDialog.jsp";
    private static final String AGENT_VESTING_DIALOG = "/agent/jsp/agentVestingDialog.jsp";
    private static final String AGENT_PAYMENT_ROUTING_INFO_DIALOG = "/agent/jsp/agentPaymentRoutingInfoDialog.jsp";
    private static final String AGENT_TAX_INFO_DIALOG = "/agent/jsp/agentTaxInfoDialog.jsp";
    private static final String AGENT_NOTES_DIALOG = "/agent/jsp/agentNotesDialog.jsp";
    private static final String AGENT_ACCUMULATIONS_DIALOG = "/agent/jsp/agentAccumulationsDialog.jsp";
    private static final String AGENT_NY_91_PCT_RULE_DIALOG = "/agent/jsp/agentNY91PctRuleDialog.jsp";
    private static final String AGENT_DEBIT_BALANCE_DIALOG = "/agent/jsp/agentDebitBalanceDialog.jsp";
    private static final String AGENT_ADDITIONAL_COMP_DIALOG = "/agent/jsp/agentAdditionalCompensationDialog.jsp";
    private static final String AGENT_ADJUST_ACCUMS_DIALOG = "/agent/jsp/agentAdjustAccumsDialog.jsp";
    private static final String ANNUALIZED_DIALOG = "/agent/jsp/annualizedDialog.jsp";
    private static final String AGENT_REDIRECT_ACCUMS_DIALOG = "/agent/jsp/agentRedirectAccumsDialog.jsp";
    private static final String AGENT_REDIRECT_SEARCH_DIALOG = "/agent/jsp/agentRedirectSearchDialog.jsp";
    private static final String ALLOWANCES_DIALOG = "/agent/jsp/allowancesDialog.jsp";
    private static final String CONTRACT_CODE_DIALOG = "/agent/jsp/contractCodeDialog.jsp";
    private static final String COMMISSION_LEVEL_DIALOG = "/agent/jsp/commissionLevelDialog.jsp";
    private static final String CANCEL_AGENT_CONFIRMATION_DIALOG = "/agent/jsp/cancelAgentConfirmationDialog.jsp";
    private static final String COMMISSION_HISTORY_DIALOG = "/agent/jsp/commissionHistoryDialog.jsp";
    private static final String COMMISSION_EXTRACT_DIALOG = "/agent/jsp/commissionExtractDialog.jsp";
    private static final String EXTRACT_ALLOWANCES_DIALOG = "/agent/jsp/extractAllowancesDialog.jsp";
    private static final String AGENT_CHANGE_HISTORY_DETAIL = "/agent/jsp/agentChangeHistoryDetail.jsp";
    private static final String DELETE_AGENT_CONFIRM_DIALOG = "/agent/jsp/deleteAgentConfirmationDialog.jsp";
    private static final String HIERARCHY_REPORT_DIALOG = "/agent/jsp/hierarchyReportDialog.jsp";
    private static final String COMMISSION_HISTORY_ADJUSTMENT_DIALOG = "/agent/jsp/commissionHistoryAdjustmentDialog.jsp";
    private static final String AGENT_REPORT_TO = "/agent/jsp/agentReportTo.jsp";
    private static final String COMMISSION_HISTORY_FILTER_DIALOG = "/agent/jsp/commissionHistoryFilterDialog.jsp";
    private static final String COMMISSION_LEVEL_SUMMARY_DIALOG = "/agent/jsp/commissionLevelSummaryDialog.jsp";
    private static final String EDITING_EXCEPTION_DIALOG = "/common/jsp/editingExceptionDialog.jsp";
    private static final String AGENT_BONUS_SUMMARY = "/agent/jsp/agentBonusSummary.jsp";
    private static final String VALIDATE_AGENT_HIERARCHY_SELECTION_PAGE = "/agent/jsp/validateAgentHierarchySelection.jsp";
    private static final String VALIDATE_AGENT_HIERARCHY_PAGE = "/agent/jsp/validateAgentHierarchy.jsp";
    private static final String AGENT_GROUP_ASSOCIATION_DIALOG = "/agent/jsp/agentGroupAssociationDialog.jsp";
    private static final String AGENT_GROUP_TOOLBAR = "/agent/jsp/agentGroupToolbar.jsp";
    private static final String CONTRIBUTING_PRODUCT_DIALOG = "/agent/jsp/contributingProductDialog.jsp";
    private static final String AGENT_REQUIREMENTS = "/agent/jsp/agentRequirements.jsp";
    private static final String AGENT_MANUAL_REQUIREMENT_DIALOG = "/agent/jsp/agentManualRequirementDialog.jsp";
    private static final String PREFERENCES_DIALOG = "/agent/jsp/preferencesDialog.jsp";
    private static final String CONTRACT_AGENT_INFO_DIALOG = "/agent/jsp/contractAgentInformationDialog.jsp";

    private static final String AGENT_MOVE_TOOLBAR = "/agent/jsp/agentMoveToolbar.jsp";
    private static final String AGENT_MOVE = "/agent/jsp/agentMove.jsp";

    public String execute(AppReqBlock appReqBlock) throws Exception
    {
        preProcessRequest(appReqBlock);

        String action = appReqBlock.getReqParm("action");
        String returnPage = null;

        try
        {
            if (action.equals(SHOW_AGENT_DETAIL_MAIN_DEFAULT))
            {
                returnPage = showAgentDetailMainDefault(appReqBlock);
            }
            else if (action.equals(SHOW_AGENT_DETAIL_MAIN_CONTENT))
            {
                returnPage = showAgentDetailMainContent(appReqBlock);
            }
            else if (action.equals(SHOW_VESTING_DIALOG))
            {
                returnPage = showVestingDialog(appReqBlock);
            }
            else if (action.equals(SAVE_VESTING))
            {
                returnPage = saveVesting(appReqBlock);
            }
            else if (action.equals(SHOW_PAYMENT_ROUTING_INFO_DIALOG))
            {
                returnPage = showPaymentRoutingInfoDialog(appReqBlock);
            }
            else if (action.equals(SHOW_TAX_INFO_DIALOG))
            {
                returnPage = showTaxInfoDialog(appReqBlock);
            }
            else if (action.equals(SHOW_NOTES_DIALOG))
            {
                returnPage = showNotesDialog(appReqBlock);
            }
            else if (action.equals(SHOW_NOTES_DETAIL_SUMMARY))
            {
                returnPage = showNotesDetailSummary(appReqBlock);
            }
            else if (action.equals(SAVE_NOTE_TO_SUMMARY))
            {
                returnPage = saveNoteToSummary(appReqBlock);
            }
            else if (action.equals(CLOSE_NOTES_DIALOG))
            {
                returnPage = closeNotesDialog(appReqBlock);
            }
            else if (action.equals(DELETE_CURRENT_NOTE))
            {
                returnPage = deleteCurrentNote(appReqBlock);
            }
            else if (action.equals(SHOW_FINANCIAL))
            {
                returnPage = showFinancial(appReqBlock);
            }
            else if (action.equals(SHOW_SELECTED_AGENT_FINANCIAL))
            {
                returnPage = showSelectedAgentFinancial(appReqBlock);
            }
            else if (action.equals(SHOW_ACCUMULATIONS_DIALOG))
            {
                returnPage = showAccumulationsDialog(appReqBlock);
            }
            else if (action.equals(SHOW_NY_91_PCT_RULE_DIALOG))
            {
                returnPage = showNY91PctRuleDialog(appReqBlock);
            }
            else if (action.equals(SHOW_DEBIT_BALANCE_DIALOG))
            {
                returnPage = showDebitBalanceDialog(appReqBlock);
            }
            else if (action.equals(SAVE_DEBIT_BALANCE))
            {
                returnPage = saveDebitBalance(appReqBlock);
            }
            else if (action.equals(SHOW_LICENSE))
            {
                returnPage = showLicense(appReqBlock);
            }
            else if (action.equals(SHOW_LICENSE_DETAIL_SUMMARY))
            {
                returnPage = showLicenseDetailSummary(appReqBlock);
            }
            else if (action.equals(SAVE_LICENSE_TO_SUMMARY))
            {
                returnPage = saveLicenseToSummary(appReqBlock);
            }
            else if (action.equals(DELETE_LICENSE))
            {
                returnPage = deleteLicense(appReqBlock);
            }
            else if (action.equals(DELETE_CONTRACT))
            {
                returnPage = deleteContract(appReqBlock);
            }
            else if (action.equals(SHOW_CONTRACT))
            {
                returnPage = showContract(appReqBlock);
            }
            else if (action.equals(SHOW_CONTRACT_DETAIL_SUMMARY))
            {
                returnPage = showContractDetailSummary(appReqBlock);
            }
            else if (action.equals(SELECT_COMMISSION_CONTRACT_FOR_AGENT))
            {
                returnPage = selectCommissionContractForAgent(appReqBlock);
            }
            else if (action.equals(SAVE_CONTRACT_TO_SUMMARY))
            {
                returnPage = saveContractToSummary(appReqBlock);
            }
            else if (action.equals(SHOW_ADDITIONAL_COMPENSATION_DIALOG))
            {
                returnPage = showAdditionalCompensationDialog(appReqBlock);
            }
            else if (action.equals(SAVE_ADDITIONAL_COMPENSATION))
            {
                returnPage = saveAdditionalCompensation(appReqBlock);
            }
            else if (action.equals(CANCEL_ADDITIONAL_COMPENSATION))
            {
                returnPage = cancelAdditionalCompensation(appReqBlock);
            }
            else if (action.equals(SHOW_ADJUSTMENTS))
            {
                returnPage = showAdjustments(appReqBlock);
            }
            else if (action.equals(SHOW_ADJUSTMENT_DETAIL_SUMMARY))
            {
                returnPage = showAdjustmentDetailSummary(appReqBlock);
            }
            else if (action.equals(SAVE_ADJUSTMENT_TO_SUMMARY))
            {
                returnPage = saveAdjustmentToSummary(appReqBlock);
            }
            else if (action.equals(DELETE_ADJUSTMENT))
            {
                returnPage = deleteAdjustment(appReqBlock);
            }
            else if (action.equals(SHOW_ADJUST_ACCUMS_DIALOG))
            {
                returnPage = showAdjustAccumsDialog(appReqBlock);
            }
            else if (action.equals(SHOW_ANNUALIZED_DIALOG))
            {
                returnPage = showAnnualizedDialog(appReqBlock);
            }
            else if (action.equals(SHOW_REDIRECT))
            {
                returnPage = showRedirect(appReqBlock);
            }
            else if (action.equals(SHOW_REDIRECT_DETAIL_SUMMARY))
            {
                returnPage = showRedirectDetailSummary(appReqBlock);
            }
            else if (action.equals(CLEAR_FORM_FOR_ADD_OR_CANCEL))
            {
                returnPage = clearFormForAddOrCancel(appReqBlock);
            }
            else if (action.equals(SAVE_REDIRECT_TO_SUMMARY))
            {
                returnPage = saveRedirectToSummary(appReqBlock);
            }
            else if (action.equals(DELETE_REDIRECT))
            {
                returnPage = deleteRedirect(appReqBlock);
            }
            else if (action.equals(SHOW_REDIRECT_ACCUMS_DIALOG))
            {
                returnPage = showRedirectAccumsDialog(appReqBlock);
            }
            else if (action.equals(SHOW_REDIRECT_SEARCH_DIALOG))
            {
                returnPage = showRedirectSearchDialog(appReqBlock);
            }
            else if (action.equals(SHOW_REDIRECT_AFTER_SEARCH))
            {
                returnPage = showRedirectAfterSearch(appReqBlock);
            }
            else if (action.equals(SHOW_HISTORY))
            {
                returnPage = showHistory(appReqBlock);
            }
            else if (action.equals(SHOW_HISTORY_DETAIL_SUMMARY))
            {
                returnPage = showHistoryDetailSummary(appReqBlock);
            }
            else if (action.equals(SHOW_ALLOWANCES_DIALOG))
            {
                returnPage = showAllowancesDialog(appReqBlock);
            }
            else if (action.equals(ADD_NEW_AGENT))
            {
                returnPage = addNewAgent(appReqBlock);
            }
            else if (action.equals(BUILD_AGENT_ROLE))
            {
                returnPage = buildAgentRole(appReqBlock);
            }
            else if (action.equals(FIND_AGENTS_BY_NAME_DOB))
            {
                returnPage = findAgentsByNameDOB(appReqBlock);
            }
            else if (action.equals(FIND_AGENTS_BY_NAME))
            {
                returnPage = findAgentsByName(appReqBlock);
            }
            else if (action.equals(FIND_AGENTS_BY_TAX_ID))
            {
                returnPage = findAgentsByTaxId(appReqBlock);
            }
            else if (action.equals(FIND_AGENTS_BY_AGENT_ID))
            {
                returnPage = findAgentsByAgentId(appReqBlock);
            }
            else if (action.equals(SHOW_COMMISSION_CONTRACT))
            {
                returnPage = showCommissionContract(appReqBlock);
            }
            else if (action.equals(SHOW_CONTRACT_CODE_DIALOG))
            {
                returnPage = showContractCodeDialog(appReqBlock);
            }
            else if (action.equals(SELECT_CONTRACT_CODE))
            {
                returnPage = selectContractCode(appReqBlock);
            }
            else if (action.equals(SAVE_CONTRACT_CODE))
            {
                returnPage = saveContractCode(appReqBlock);
            }
            else if (action.equals(SHOW_COMMISSION_LEVEL_DIALOG))
            {
                returnPage = showCommissionLevelDialog(appReqBlock);
            }
            else if (action.equals(SELECT_COMMISSION_LEVEL))
            {
                returnPage = selectCommissionLevel(appReqBlock);
            }
            else if (action.equals(SAVE_COMMISSION_LEVEL_DESCRIPTION))
            {
                returnPage = saveCommissionLevelDescription(appReqBlock);
            }
            else if (action.equals(ADD_OR_CANCEL_PROFILE))
            {
                returnPage = addOrCancelProfile(appReqBlock);
            }
            else if (action.equals(SHOW_PROFILE_DETAIL_SUMMARY))
            {
                returnPage = showProfileDetailSummary(appReqBlock);
            }
            else if (action.equals(SAVE_COMMISSION_PROFILE))
            {
                returnPage = saveCommissionProfile(appReqBlock);
            }
            else if (action.equals(DELETE_COMMISSION_PROFILE))
            {
                returnPage = deleteCommissionProfile(appReqBlock);
            }
            else if (action.equals(LOAD_AGENT_DETAIL_AFTER_SEARCH))
            {
                returnPage = loadAgentDetailAfterSearch(appReqBlock);
            }
            else if (action.equals(LOCK_AGENT))
            {
                returnPage = lockAgent(appReqBlock);
            }
            else if (action.equals(SHOW_CANCEL_AGENT_CONFIRMATION_DIALOG))
            {
                returnPage = showCancelAgentConfirmationDialog(appReqBlock);
            }
            else if (action.equals(SAVE_AGENT_DETAILS))
            {
                returnPage = saveAgentDetails(appReqBlock);
            }
            else if (action.equals(CANCEL_AGENT))
            {
                returnPage = cancelAgent(appReqBlock);
            }
            else if (action.equals(SHOW_DELETE_AGENT_CONFIRM_DIALOG))
            {
                returnPage = showDeleteAgentConfirmDialog(appReqBlock);
            }
            else if (action.equals(DELETE_AGENT))
            {
                returnPage = deleteAgent(appReqBlock);
            }
            else if (action.equals(SHOW_JUMP_TO_DIALOG))
            {
                returnPage = showJumpToDialog(appReqBlock);
            }
            else if (action.equals(SHOW_VO_EDIT_EXCEPTION_DIALOG))
            {
                returnPage = showVOEditExceptionDialog(appReqBlock);
            }
            else if (action.equals(SHOW_PLACED_AGENTS))
            {
                returnPage = showPlacedAgents(appReqBlock);
            }
            else if (action.equals(SHOW_AGENT_CONTRACTS))
            {
                returnPage = showAgentContracts(appReqBlock);
            }
            else if (action.equals(SHOW_AGENT_CONTRACT_HIERARCHIES))
            {
                returnPage = showAgentContractHierarchies(appReqBlock);
            }
            else if (action.equals(SHOW_PLACED_AGENT_HIERARCHY))
            {
                returnPage = showPlacedAgentHierarchy(appReqBlock);
            }
            else if (action.equals(SHOW_COMMISSION_PROFILE))
            {
                returnPage = showCommissionProfile(appReqBlock);
            }
            else if (action.equals(ASSOCIATE_COMMISSION_PROFILE))
            {
                returnPage = associateCommissionProfile(appReqBlock);
            }
            else if (action.equals(CLEAR_PLACED_AGENTS))
            {
                returnPage = clearPlacedAgents(appReqBlock);
            }
            else if (action.equals(PLACE_AGENT_CONTRACT))
            {
                returnPage = placeAgentContract(appReqBlock);
            }
            else if (action.equals(REMOVE_PLACED_AGENT))
            {
                returnPage = removePlacedAgent(appReqBlock);
            }
            else if (action.equals(SHIFT_PLACED_AGENT))
            {
                returnPage = shiftPlacedAgent(appReqBlock);
            }
            else if (action.equals(SHOW_COMMISSION_BALANCE_HISTORY))
            {
                returnPage = showCommissionBalanceHistory(appReqBlock);
            }
            else if (action.equals(SHOW_BONUS_COMMISSION_BALANCE_HISTORY))
            {
                returnPage = showBonusCommissionBalanceHistory(appReqBlock);
            }
            else if (action.equals(SHOW_COMMISSION_EARNED_HISTORY))
            {
                returnPage = showCommissionEarnedHistory(appReqBlock);
            }
            else if (action.equals(SHOW_COMMISSION_HELD_HISTORY))
            {
                returnPage = showCommissionHeldHistory(appReqBlock);
            }
            else if (action.equals(SHOW_COMMISSION_PENDING_UPDATE_HISTORY))
            {
                returnPage = showCommissionPendingUpdateHistory(appReqBlock);
            }
            else if (action.equals(SHOW_ROLLUP_BALANCE_HISTORY))
            {
                returnPage = showRollupBalanceHistory(appReqBlock);
            }
            else if (action.equals(SHOW_COMMISSION_CHECK_HISTORY))
            {
                returnPage = showCommissionCheckHistory(appReqBlock);
            }
            else if (action.equals(SHOW_EXTRACT_DIALOG))
            {
                returnPage = showExtractDialog(appReqBlock);
            }
            else if (action.equals(SHOW_EXTRACT_DETAIL_SUMMARY))
            {
                returnPage = showExtractDetailSummary(appReqBlock);
            }
            else if (action.equals(SHOW_EXTRACT_ALLOWANCES_DIALOG))
            {
                returnPage = showExtractAllowancesDialog(appReqBlock);
            }
            else if (action.equals(SAVE_ALLOWANCES_DIALOG))
            {
                returnPage = saveAllowancesDialog(appReqBlock);
            }
            else if (action.equals(SAVE_EXTRACT_TO_SUMMARY))
            {
                returnPage = saveExtractToSummary(appReqBlock);
            }
            else if (action.equals(CANCEL_CURRENT_EXTRACT))
            {
                returnPage = cancelCurrentExtract(appReqBlock);
            }
            else if (action.equals(DELETE_SELECTED_EXTRACT))
            {
                returnPage = deleteSelectedExtract(appReqBlock);
            }
            else if (action.equals(CLOSE_EXTRACT_DIALOG))
            {
                returnPage = closeExtractDialog(appReqBlock);
            }
            else if (action.equals(SHOW_HIERARCHY_REPORT))
            {
                returnPage = showHierarchyReport(appReqBlock);
            }
            else if (action.equals(FILTER_EXTRACTS))
            {
                returnPage = filterExtracts(appReqBlock);
            }
            else if (action.equals(REMOVE_EXTRACT_FILTER))
            {
                returnPage = removeExtractFilter(appReqBlock);
            }
            else if (action.equals(ADD_NEW_EXTRACT))
            {
                returnPage = addNewExtract(appReqBlock);
            }
            else if (action.equals(CANCEL_AGENT_ADD))
            {
                returnPage = cancelAgentAdd(appReqBlock);
            }
            else if (action.equals(SET_PLACED_AGENT_INACTIVE_IND))
            {
                returnPage = setPlacedAgentInactiveInd(appReqBlock);
            }
            else if (action.equals(SHOW_COMM_HISTORY_ADJUSTMENT_DIALOG))
            {
                returnPage = showCommHistoryAdjustmentDialog(appReqBlock);
            }
            else if (action.equals(SHOW_COMM_HISTORY_FILTER_DIALOG))
            {
                returnPage = showCommHistoryFilterDialog(appReqBlock);
            }
            else if (action.equals(FILTER_COMMISSION_HISTORY))
            {
                returnPage = filterCommissionHistory(appReqBlock);
            }
            else if (action.equals(SAVE_COMM_HISTORY_ADJUSTMENT))
            {
                returnPage = saveCommHistoryAdjustment(appReqBlock);
            }
            else if (action.equals(CANCEL_COMM_HISTORY_ADJUSTMENT))
            {
                returnPage = cancelCommHistoryAdjustment(appReqBlock);
            }
            else if (action.equals(SAVE_COMMISSION_HISTORY))
            {
                returnPage = saveCommissionHistory(appReqBlock);
            }
            else if (action.equals(FIND_AGENT_CONTRACT_BY_AGENTID_AND_CONTRACTCODECT))
            {
                returnPage = findAgentContractByAgentId_AND_ContractCodeCT(appReqBlock);
            }
            else if (action.equals(FIND_REPORTOAGENT_BY_AGENTID_AND_CONTRACTCODECT))
            {
                returnPage = findReportToAgentByAgentId_AND_ContractCodeCT(appReqBlock);
            }
            else if (action.equals(FIND_AGENT_CONTRACT_BY_AGENTNAME_AND_CONTRACTCODECT))
            {
                returnPage = findAgentContractByAgentName_AND_ContractCodeCT(appReqBlock);
            }
            else if (action.equals(FIND_REPORTTO_AGENT_BY_AGENT_NAME_AND_CONTRACTCODECT))
            {
                returnPage = findReportToAgentByAgentName_AND_ContractCodeCT(appReqBlock);
            }
            else if (action.equals(SHOW_AGENT_REPORT_TO))
            {
                returnPage = showAgentReportTo(appReqBlock);
            }
            else if (action.equals(SHOW_REPORT_TO_DETAIL))
            {
                returnPage = showPlacedAgentDetail(appReqBlock);
            }
            else if (action.equals(SHOW_HIERARCHY))
            {
                returnPage = showHierarchy(appReqBlock);
            }
            else if (action.equals(UPDATE_PLACED_AGENT_DETAILS))
            {
                returnPage = updatePlacedAgentDetails(appReqBlock);
            }
            else if (action.equals(CANCEL_PLACED_AGENT_DETAILS))
            {
                returnPage = cancelPlacedAgentDetails(appReqBlock);
            }
            else if (action.equals(DELETE_PLACED_AGENT))
            {
                returnPage = deletePlacedAgent(appReqBlock);
            }
            else if (action.equals(SHOW_PLACED_AGENT_GROUP))
            {
                return showPlacedAgentGroup(appReqBlock);
            }
            else if (action.equals(COPY_PLACED_AGENT_GROUP))
            {
               // return copyPlacedAgentGroup(appReqBlock);
            }
            else if (action.equalsIgnoreCase(SHOW_COMMISSION_LEVEL_SUMMARY_DIALOG))
            {
                return showCommissionLevelSummaryDialog(appReqBlock);
            }
            else if (action.equals(CANCEL_NOTES_DIALOG))
            {
                returnPage = cancelNotesDialog(appReqBlock);
            }
            else if (action.equals(ADD_NEW_AGENT_NOTES))
            {
                returnPage = addNewAgentNote(appReqBlock);
            }
            else if (action.equals(SAVE_NOTES_DIALOG))
            {
                returnPage = saveNotesDialog(appReqBlock);
            }
            else if (action.equals(SHOW_EDITING_EXCEPTION_DIALOG))
            {
                returnPage = showEditingExceptionDialog(appReqBlock);
            }
            else if (action.equals(SHOW_BONUS))
            {
                returnPage = showBonusSummary(appReqBlock);
            }
            else if (action.equals(SHOW_AGENT_BONUS_DETAIL))
            {
                return showAgentBonusDetail(appReqBlock);
            }
            else if (action.equals(SHOW_VALIDATE_HIERARCHY_SELECTION))
            {
                return showValidateHierarchySelection(appReqBlock);
            }
            else if (action.equals(VALIDATE_AGENT_HIERARCHY))
            {
                return validateAgentHierarchy(appReqBlock);
            }
            else if (action.equals(SHOW_AGENT_GROUP))
            {
                return showAgentGroup(appReqBlock);
            }
            else if (action.equals(ADD_AGENT_GROUP))
            {
                return addAgentGroup(appReqBlock);
            }
            else if (action.equals(SAVE_AGENT_GROUP))
            {
                return saveAgentGroup(appReqBlock);
            }
            else if (action.equals(SEARCH_AGENT_FOR_AGENT_GROUP))
            {
                return searchAgentForAgentGroup(appReqBlock);
            }
            else if (action.equals(LOAD_AGENT_FOR_AGENT_GROUP_ADD))
            {
                return loadAgentForAgentGroupAdd(appReqBlock);
            }
            else if (action.equals(SHOW_AGENT_GROUP_ASSOCATIONS))
            {
                return showAgentGroupAssociations(appReqBlock);
            }
            else if (action.equals(SHOW_AGENT_GROUP_DETAIL))
            {
                return showAgentGroupDetail(appReqBlock);
            }
            else if (action.equals(SEARCH_PLACEDAGENTS_FOR_AGENTGROUPASSOCATION))
            {
                return searchPlacedAgentsForAgentGroupAssociation(appReqBlock);
            }
            else if (action.equals(CREATE_AGENTGROUPASSOCIATION))
            {
                return createAgentGroupAssociation(appReqBlock);
            }
            else if (action.equals(DELETE_AGENTGROUPASSOCIATION))
            {
                return deleteAgentGroupAssociation(appReqBlock);
            }
            else if (action.equals(CLOSE_COMMISSION_LEVEL_SUMMARY))
            {
                return closeCommissionLevelSummary(appReqBlock);
            }
            else if (action.equals(CANCEL_AGENT_GROUP))
            {
                return cancelAgentGroup(appReqBlock);
            }
            else if (action.equals(DELETE_AGENT_GROUP))
            {
                return deleteAgentGroup(appReqBlock);
            }
            else if (action.equals(SHOW_AGENT_GROUP_ASSOCIATION_DIALOG))
            {
                return showAgentGroupAssociationDialog(appReqBlock);
            }
            else if (action.equals(SAVE_AGENT_GROUP_ASSOCIATION))
            {
                return saveAgentGroupAssociation(appReqBlock);
            }
            else if (action.equals(SHOW_AGENT_GROUP_CONTRIBUTING_PRODUCTS))
            {
                return showAgentGroupContributingProducts(appReqBlock);
            }
            else if (action.equals(CREATE_CONTRIBUTINGPRODUCT))
            {
                return createContributingProduct(appReqBlock);
            }
            else if (action.equals(DELETE_CONTRIBUTINGPRODUCT))
            {
                return deleteContributingProduct(appReqBlock);
            }
            else if (action.equals(SHOW_CONTRIBUTING_PRODUCT_DIALOG))
            {
                return showContributingProductDialog(appReqBlock);
            }
            else if (action.equals(SAVE_CONTRIBUTING_PRODUCT))
            {
                return saveContributingProduct(appReqBlock);
            }
            else if (action.equals(SHOW_REQUIREMENTS))
            {
                return showRequirements(appReqBlock);
            }
            else if (action.equals(CANCEL_REQUIREMENT))
            {
                return cancelRequirement(appReqBlock);
            }
            else if (action.equals(ADD_REQUIREMENT))
            {
                return addRequirement(appReqBlock);
            }
            else if (action.equals(SAVE_REQUIREMENT))
            {
                return saveRequirement(appReqBlock);
            }
            else if (action.equals(DELETE_SELECTED_REQUIREMENT))
            {
                return deleteSelectedRequirement(appReqBlock);
            }
            else if (action.equals(SHOW_REQUIREMENT_DETAIL))
            {
                return showRequirementDetail(appReqBlock);
            }
            else if (action.equals(SAVE_MANUAL_REQUIREMENT))
            {
                return saveManualRequirement(appReqBlock);
            }
            else if (action.equals(SHOW_REQUIREMENT_DESCRIPTION))
            {
                return showRequirementDescription(appReqBlock);
            }
            else if (action.equals(CLEAR_AGENT_MOVE))
            {
                return clearAgentMove(appReqBlock);
            }
            else if (action.equals(FIND_MOVING_FROM_TO_PLACED_AGENT))
            {
                return findMovingFromToPlacedAgent(appReqBlock);
            }
            else if (action.equals(MOVE_AGENT_GROUP))
            {
                return moveAgentGroup(appReqBlock);
            }
            else if (action.equals(GENERATE_HIERARCHY_REPORT_BY_PLACEDAGENTPK))
            {
                return generateHierarchyReportByPlacedAgentPK(appReqBlock);
			}
            else if (action.equalsIgnoreCase(SHOW_PREFERENCES))
            {
                return showPreferences(appReqBlock);
            }
            else if (action.equalsIgnoreCase(SHOW_SELECTED_PREFERENCE))
            {
                return showSelectedPreference(appReqBlock);
            }
            else if (action.equalsIgnoreCase(CLEAR_PREFERENCE_FOR_ADD))
            {
                return clearPreferenceForAdd(appReqBlock);
            }
            else if (action.equalsIgnoreCase(SHOW_CONTRACT_AGENT_INFO))
            {
                return showContractAgentInfo(appReqBlock);
            }
            else if (action.equalsIgnoreCase(SAVE_PREFERENCE))
            {
                return savePreference(appReqBlock);
            }
            else if (action.equalsIgnoreCase(SELECT_PREFERENCE_FOR_AGENT))
            {
                return selectPreferenceForAgent(appReqBlock);
            }
        }
        catch (Exception e)
        {
            System.out.println(e);
            e.printStackTrace();
            throw e;
        }

        postProcessRequest(appReqBlock);

        return returnPage;
    }

    /**
     * Saves/Updates this ContributingProduct.
     * @param appReqBlock
     * @return
     */
    private String saveContributingProduct(AppReqBlock appReqBlock)
    {
        ContributingProduct contributingProduct = (ContributingProduct) Util.mapFormDataToHibernateEntity(appReqBlock.getHttpServletRequest(), ContributingProduct.class, SessionHelper.EDITSOLUTIONS, false);

        Agent agentComponent = new AgentComponent();

        agentComponent.saveContributingProduct(contributingProduct);

        appReqBlock.putInRequestScope("pageMessage", "ContributingProduct Successfully Saved/Updated");

        return showAgentGroupContributingProducts(appReqBlock);
    }

    /**
     * Displays the AgentGroupAssociation dialog to modify the attributes.
     * @param appReqBlock
     * @return
     */
    private String showContributingProductDialog(AppReqBlock appReqBlock)
    {
        String contributingProductPK = new ContributingProductTableModel(appReqBlock, TableModel.SCOPE_REQUEST).getSelectedRowId();

        ContributingProduct contributingProduct = ContributingProduct.findBy_PK(new Long(contributingProductPK));

        appReqBlock.putInRequestScope("contributingProduct", contributingProduct);

        appReqBlock.putInRequestScope("main", CONTRIBUTING_PRODUCT_DIALOG);

        appReqBlock.putInRequestScope("htmlTitle", "Contributing Product Detail");

        return TEMPLATE_MAIN;
    }

    /**
     * Disassociates the selected ProductStructure from the current AgentGroup deleting the ContributingProduct.
     * @param appReqBlock
     * @return
     */
    private String deleteContributingProduct(AppReqBlock appReqBlock)
    {
        ContributingProductTableModel contributingProductTableModel = new ContributingProductTableModel(appReqBlock, TableModel.SCOPE_REQUEST);

        Agent agentComponent = new AgentComponent();

        Long selectedContributingProductPK = new Long(contributingProductTableModel.getSelectedRowId());

        agentComponent.deleteContributingProduct(selectedContributingProductPK);

        appReqBlock.putInRequestScope("pageMessage", "ContributingProduct Successfully Deleted");

        appReqBlock.putInRequestScope("pageCommand", "resetForm");

        return showAgentGroupContributingProducts(appReqBlock);
    }

    /**
     * Associates the selected ProductStructure to the current AgentGroup creating a ContributingProduct.
     * @param appReqBlock
     * @return
     */
    private String createContributingProduct(AppReqBlock appReqBlock)
    {
//        Long agentGroupPK = new Long(appReqBlock.getReqParm("agentGroupPK"));
//
//        ContributingProduct contributingProduct = (ContributingProduct) Util.mapFormDataToHibernateEntity(appReqBlock.getHttpServletRequest(), ContributingProduct.class, SessionHelper.EDITSOLUTIONS);
//
//        AgentGroup agentGroup = AgentGroup.findBy_PK(agentGroupPK);
//
//        AgentGroupCompanyStructureTableModel agentGroupCompanyStructureTableModel = new AgentGroupCompanyStructureTableModel(appReqBlock, TableModel.SCOPE_REQUEST, agentGroup);
//
//        Agent agentComponent = new AgentComponent();
//
//        Long selectedCompanyStructurePK = new Long(agentGroupCompanyStructureTableModel.getSelectedRowId());
//
//        agentComponent.createContributingProduct(selectedCompanyStructurePK, agentGroupPK, contributingProduct);
//
//        appReqBlock.putInRequestScope("contributingProduct", contributingProduct);
//
//        appReqBlock.putInRequestScope("pageMessage", "ContributingProduct Successfully Created");
//
//        appReqBlock.putInRequestScope("pageCommand", "resetForm");

        return showAgentGroupContributingProducts(appReqBlock);
    }

    /**
     * Renders the page that shows the mapping of AgentGroups To ProductStructures as ContributingProducts.
     * @param appReqBlock
     * @return
     */
    private String showAgentGroupContributingProducts(AppReqBlock appReqBlock)
    {
//        String agentGroupPK = appReqBlock.getReqParm("agentGroupPK");
//
//        AgentGroup agentGroup = AgentGroup.findBy_PK(new Long(agentGroupPK));
//
//        new AgentGroupProductStructureTableModel(appReqBlock, TableModel.SCOPE_REQUEST, agentGroup);
//
//        new ContributingProductTableModel(appReqBlock, TableModel.SCOPE_REQUEST, agentGroup);
//
//        agent.Agent theAgent = agentGroup.getAgent();
//
//        appReqBlock.putInRequestScope("agentGroup", agentGroup);
//
//        appReqBlock.putInRequestScope("agent", theAgent);
//
//        appReqBlock.putInRequestScope("toolbar", AGENT_GROUP_TOOLBAR);
//
//        appReqBlock.putInRequestScope("main", AGENT_GROUP_CONTRIBUTING_PRODUCTS);

        return TEMPLATE_TOOLBAR_MAIN;
    }

    /**
     * Deletes the selected AgentGroup.
     * @param appReqBlock
     * @return
     */
    private String deleteAgentGroup(AppReqBlock appReqBlock)
    {
//        Agent agentComponent = new AgentComponent();
//
//        AgentGroupTableModel agentGroupTableModel = new AgentGroupTableModel(appReqBlock);
//
//        String agentGroupPK = agentGroupTableModel.getSelectedAgentGroupPK();
//
//        agentComponent.deleteAgentGroup(new Long(agentGroupPK));
//
//        agentGroupTableModel.resetAllRows();
//
//        appReqBlock.setReqParm("agentPK", null);
//
//        appReqBlock.setReqParm("agentGroupPK", null);
//
//        appReqBlock.putInRequestScope("pageMessage", "AgentGroup Successfully Deleted");
//
//        appReqBlock.putInRequestScope("pageCommand", "resetForm");

        return showAgentGroup(appReqBlock);
    }

    /**
     * AgentGroup page is reset and rendered in its default state.
     * @param appReqBlock
     * @return
     */
    private String cancelAgentGroup(AppReqBlock appReqBlock)
    {
        return showAgentGroup(appReqBlock);
    }

    /**
     * Removes the association between an AgentGroup and a PlacedAgent.
     * @param appReqBlock
     * @return
     */
    private String deleteAgentGroupAssociation(AppReqBlock appReqBlock)
    {
//        AgentGroupAssociationTableModel tableModel = new AgentGroupAssociationTableModel(appReqBlock);
//
//        Long agentGroupAssociationPK = new Long(tableModel.getSelectedRowId());
//
//        Agent agentComponent = new AgentComponent();
//
//        agentComponent.deleteAgentGroupAssociation(agentGroupAssociationPK);
//
//        tableModel.resetAllRows();
//
//        appReqBlock.putInRequestScope("pageMessage", "AgentGroupAssociation Successfully Deleted");
//
        return showAgentGroupAssociations(appReqBlock);
    }

    /**
     * Associates the selected PlacedAgent to the currently selected AgentGroup.
     * @param appReqBlock
     * @return
     */
    private String createAgentGroupAssociation(AppReqBlock appReqBlock)
    {
        Long agentGroupPK = Util.initLong(appReqBlock.getReqParm("agentGroupPK"), null);

        PlacedAgentReportToTableModel tableModel = new PlacedAgentReportToTableModel(appReqBlock);

        Long placedAgentPK = new Long(tableModel.getSelectedRowId());

        Agent agentComponent = new AgentComponent();

        EDITDate startDate = DateTimeUtil.getEDITDateFromMMDDYYYY(appReqBlock.getReqParm("startDate"));

        agentComponent.createAgentGroupAssociation(placedAgentPK, agentGroupPK, startDate);

        tableModel.resetAllRows();

        appReqBlock.putInRequestScope("pageCommand", "resetForm");

        appReqBlock.putInRequestScope("pageMessage", "AgentGroupAssociation Successfully Created");

        return showAgentGroupAssociations(appReqBlock);
    }

    /**
     * Finds PlacedAgents by AgentName or AgentNumber to be (potentially) assocociated with the currently
     * selected AgentGroup.
     * @param appReqBlock
     * @return
     */
    private String searchPlacedAgentsForAgentGroupAssociation(AppReqBlock appReqBlock)
    {
        return showAgentGroupAssociations(appReqBlock);
    }

    /**
     * Shows the detail of the selected AgentGroup.
     * @param appReqBlock
     * @return
     */
    private String showAgentGroupDetail(AppReqBlock appReqBlock)
    {
        return showAgentGroup(appReqBlock);
    }

    /**
     * Shows the Agent Group Associations page.
     * @param appReqBlock
     * @return
     */
    private String showAgentGroupAssociations(AppReqBlock appReqBlock)
    {
//        String agentGroupPK = appReqBlock.getReqParm("agentGroupPK");
//
//        AgentGroup agentGroup = AgentGroup.findBy_PK(new Long(agentGroupPK));
//
//        agent.Agent agent = agentGroup.getAgent();
//
//        new AgentGroupAssociationTableModel(appReqBlock);
//
//        new PlacedAgentReportToTableModel(appReqBlock);
//
//        appReqBlock.putInRequestScope("toolbar", AGENT_GROUP_TOOLBAR);
//
//        appReqBlock.putInRequestScope("main", AGENT_GROUP_ASSOCIATION);
//
//        appReqBlock.putInRequestScope("agentGroup", agentGroup);
//
//        appReqBlock.putInRequestScope("agent", agent);

        return TEMPLATE_TOOLBAR_MAIN;
    }

    /**
     * Loads the Agent info after an Agent search to add a new AgentGroup.
     * @param appReqBlock
     * @return
     */
    private String loadAgentForAgentGroupAdd(AppReqBlock appReqBlock)
    {
        return showAgentGroup(appReqBlock);
    }

    /**
     * Performs the search to find an Agent to use for an AgentGroup.
     * @param appReqBlock
     * @return
     */
    private String searchAgentForAgentGroup(AppReqBlock appReqBlock)
    {
        return addAgentGroup(appReqBlock);
    }

    /**
     * Saves/Updates the AgentGroup information.
     * @param appReqBlock
     * @return
     */
    private String saveAgentGroup(AppReqBlock appReqBlock)
    {
        AgentGroup agentGroup = (AgentGroup) Util.mapFormDataToHibernateEntity(appReqBlock.getHttpServletRequest(), AgentGroup.class, SessionHelper.EDITSOLUTIONS, false);

        Long agentPK = Util.initLong(appReqBlock.getReqParm("agentPK"), null);

        Long commissionProfilePK = Util.initLong(appReqBlock.getReqParm("commissionProfilePK"), null);

        Agent agentComponent = new AgentComponent();

        agentComponent.saveAgentGroup(agentGroup, agentPK, commissionProfilePK);

        // When the screen renders, this AgentGroup will be selected.
        String agentGroupPK = agentGroup.getAgentGroupPK().toString();

        appReqBlock.setReqParm("agentGroupPK", agentGroupPK);

        appReqBlock.putInRequestScope("pageMessage", "AgentGroup Successfully Saved/Updated");

        return showAgentGroup(appReqBlock);
    }

    /**
     * Prepares page for the adding of a new AgentGroup.
     * @param appReqBlock
     * @return
     */
    private String addAgentGroup(AppReqBlock appReqBlock)
    {
//        new AddAgentGroupTableModel(appReqBlock);
//
//        appReqBlock.putInRequestScope("main", ADD_AGENT_GROUP_DIALOG);

        return TEMPLATE_MAIN;
    }

    /**
     * Shows the BrokerDealer Arrangement page.
     * @param appReqBlock
     * @return
     */
    private String showAgentGroup(AppReqBlock appReqBlock)
    {
//        agent.Agent selectedAgent = null;
//
//        CommissionProfile commissionProfile = null;
//
//        // Find the selected AgentGroup, if any.
//        AgentGroupTableModel agentGroupTableModel = new AgentGroupTableModel(appReqBlock);
//
//        AgentGroup agentGroup = null;
//
//        String agentGroupPK = agentGroupTableModel.getSelectedAgentGroupPK();
//
//        if (agentGroupPK == null)
//        {
//            // If the AgentGroup was just saved for the 1st time, it isn't "selected", just manually placed here.
//            agentGroupPK = Util.initString(appReqBlock.getReqParm("agentGroupPK"), null);
//
//            agentGroupTableModel.setSelectedAgentGroupPK(agentGroupPK);
//        }
//
//        if (agentGroupPK != null)
//        {
//            // There is an active AgentGroup, so (make sure) it is flagged in the TableModel.
//            agentGroup = AgentGroup.findBy_PK(new Long(agentGroupPK));
//
//            // Since there is an AgentGroup, then there is certainly an Agent.
//            selectedAgent = agentGroup.getAgent();
//
//            // And there also has to be a CommissionProfile.
//            commissionProfile = agentGroup.getCommissionProfile();
//        }
//
//        // If there is no selected AgentGroup, then check to see if there is an Agent which
//        // may be available as a result of a search.
//        else
//        {
//            Long agentPK = Util.initLong(appReqBlock.getReqParm("agentPK"), null);
//
//            if (agentPK != null)
//            {
//                selectedAgent = agent.Agent.findBy_PK_V2(agentPK);
//            }
//        }
//
//        appReqBlock.putInRequestScope("toolbar", AGENT_GROUP_TOOLBAR);
//
//        appReqBlock.putInRequestScope("main", BROKER_DEALER_ARRANGEMENT);
//
//        appReqBlock.putInRequestScope("agentGroup", agentGroup);
//
//        appReqBlock.putInRequestScope("agent", selectedAgent);
//
//        appReqBlock.putInRequestScope("commissionProfile", commissionProfile);

        return TEMPLATE_TOOLBAR_MAIN;
    }

    /**
    * Report that validates the integrity of the Agent Hierarchy.
    * @param appReqBlock
    * @return
    */
    private String validateAgentHierarchy(AppReqBlock appReqBlock)
    {
        return VALIDATE_AGENT_HIERARCHY_PAGE;
    }

    /**
     * Displays selection dialog for generating the Validate Hierarchy Report.
     * @param appReqBlock
     * @return
     */
    private String showValidateHierarchySelection(AppReqBlock appReqBlock)
    {
        return VALIDATE_AGENT_HIERARCHY_SELECTION_PAGE;
    }

    /**
     * Shows the details of the selected ParticipatingAgent's BonusProgram.
     * @param appReqBlock
     * @return
     */
    private String showAgentBonusDetail(AppReqBlock appReqBlock)
    {
        String activeParticipatingAgentPK = appReqBlock.getReqParm("activeParticipatingAgentPK");

        ParticipatingAgent participatingAgent = ParticipatingAgent.findByPK(new Long(activeParticipatingAgentPK));

        appReqBlock.putInRequestScope("activeParticipatingAgent", participatingAgent);

        appReqBlock.putInRequestScope("activeBonusProgram", participatingAgent.getBonusProgram());

        return showBonusSummary(appReqBlock);
    }

    /**
     * Returns the bonus summary page.
     * @param appReqBlock
     * @return
     */
    private String showBonusSummary(AppReqBlock appReqBlock)
    {
        // Check for authorization
        new AgentUseCaseComponent().accessBonus();

        AgentVO agentVO = (AgentVO) appReqBlock.getFromSessionScope("agentVO");

        if (agentVO != null)
        {
            long agentPK = agentVO.getAgentPK();

            Agent agent = new AgentComponent();

            ParticipatingAgent[] participatingAgents = agent.findParticipatingAgentsBy_AgentPK(agentPK);

            appReqBlock.putInRequestScope("participatingAgents", participatingAgents);
        }

        String currentPage = (String) appReqBlock.getHttpSession().getAttribute("currentPage");
        String previousPage = currentPage;

        savePreviousPageToVO(appReqBlock, previousPage);

        appReqBlock.getHttpSession().setAttribute("currentPage", AGENT_BONUS_SUMMARY);
        appReqBlock.getHttpSession().setAttribute("previousPage", previousPage);

        return AGENT_BONUS_SUMMARY;
    }

    /**
     * Displays the list of Commission Levels with the associated Commission Level hightlighted.
     * @param appReqBlock
     * @return
     * @throws Exception
     */
    protected String showCommissionLevelSummaryDialog(AppReqBlock appReqBlock)
        throws Exception
    {
        return COMMISSION_LEVEL_SUMMARY_DIALOG;
    }

    /**
     * Toggles the display of a PlacedAgent's group-view.
     * @param appReqBlock
     * @return
     * @throws Exception
     */
    protected String showPlacedAgentGroup(AppReqBlock appReqBlock)
        throws Exception
    {
        String showGroupPlacedAgentPK = appReqBlock.getReqParm("showGroupPlacedAgentPK");

        appReqBlock.setReqParm("showGroupPlacedAgentPK", showGroupPlacedAgentPK);

        return showPlacedAgents(appReqBlock);
    }

    /**
     * This removes a PlacedAgent from the hierarchy, but from the Hierarchy Tab, and not the Agent Hiearchy page.
     * @param appReqBlock
     * @return
     * @throws Exception
     * @see AgentDetailTran#removePlacedAgent(fission.global.AppReqBlock)
     */
    protected String deletePlacedAgent(AppReqBlock appReqBlock)
        throws Exception
    {
        String activePlacedAgentPK2 = appReqBlock.getReqParm("activePlacedAgentPK2");

        appReqBlock.setReqParm("activePlacedAgentPK2", null);

        appReqBlock.setReqParm("pageMode", "BROWSE");

        String operator = appReqBlock.getUserSession().getUsername();

        Agent agent = new AgentComponent();

        UserSession userSession = appReqBlock.getUserSession();

        agent.removePlacedAgent(Long.parseLong(activePlacedAgentPK2), operator);

        appReqBlock.getHttpServletRequest().setAttribute("message", "Database Updated");

        return showAgentReportTo(appReqBlock);
    }

    /**
     * Resets the agentReportTo page to its intialized state.
     * @param appReqBlock
     * @return
     * @throws Exception
     */
    protected String cancelPlacedAgentDetails(AppReqBlock appReqBlock)
        throws Exception
    {
        appReqBlock.setReqParm("activePlacedAgentPK2", null);

        appReqBlock.setReqParm("pageMode", "BROWSE");

        return showAgentReportTo(appReqBlock);
    }

    /**
     * Updates the Start-Date, Stop-Date, Situation, and AgentNumber of a PlacedAgent.
     * @param appReqBlock
     * @return
     * @throws Exception
     */
    protected String updatePlacedAgentDetails(AppReqBlock appReqBlock)
            throws Exception
    {
        String activePlacedAgentPK2 = Util.initString(appReqBlock.getReqParm("activePlacedAgentPK2"), null);

        String stopDateReasonCT = Util.initString(appReqBlock.getReqParm("stopDateReasonCT"), null);

        String startDate = Util.initString(appReqBlock.getReqParm("startDate"), null);


        String stopDate = Util.initString(appReqBlock.getReqParm("stopDate"), null);


        EDITDate stopDateField = DateTimeUtil.getEDITDateFromMMDDYYYY(stopDate);


        EDITDate startDateField = DateTimeUtil.getEDITDateFromMMDDYYYY(startDate);


        if (startDate != null)
        {
            startDate = DateTimeUtil.getEDITDateFromMMDDYYYY(startDate).getFormattedDate();
        }



        if (stopDate != null)
        {
            stopDate = DateTimeUtil.getEDITDateFromMMDDYYYY(stopDate).getFormattedDate();
        }

        String situation = Util.initString(appReqBlock.getReqParm("situation"), null);

        String agentNumber = Util.initString(appReqBlock.getReqParm("agentId"), null);

        Agent agentComponent = new AgentComponent();

        String message = null;

        UserSession userSession = appReqBlock.getUserSession();

        if (startDateField.before(stopDateField))
        {
            try
            {
                agentComponent.updatePlacedAgentDetails(Long.parseLong(activePlacedAgentPK2), startDate, stopDate, situation, stopDateReasonCT, agentNumber);

                message = "Database Updated";
            } catch (EDITAgentException e)
            {
                message = e.getMessage();
            }

        } else
        {
            message = "Stop-Date is prior to Start-Date";
        }



        appReqBlock.getHttpServletRequest().setAttribute("message", message);

        return showAgentReportTo(appReqBlock);
    }

    /**
     * Displays the Hierarchy tab.
     * @param appReqBlock
     * @return
     * @throws Exception
     */
    protected String showHierarchy(AppReqBlock appReqBlock)
        throws Exception
    {
        return showAgentReportTo(appReqBlock);
    }

    /**
     * Shows the Report-To Agent's detail when selected from the report-to summary.
     * @param appReqBlock
     * @return
     * @throws Exception
     */
    protected String showPlacedAgentDetail(AppReqBlock appReqBlock)
        throws Exception
    {
        appReqBlock.setReqParm("pageMode", "SELECT");

        return showAgentReportTo(appReqBlock);
    }

    /**
     * Renders the agentReportTo.jsp whose state is determined by the selectedReportTo branch, and selectedPlacedAgent.
     * @param appReqBlock
     * @return
     * @throws Exception
     */
    protected String showAgentReportTo(AppReqBlock appReqBlock)
        throws Exception
    {
        AgentVO agentVO = (AgentVO) appReqBlock.getHttpSession().getAttribute("agentVO");

        long agentPK = 0;

        String pageMode = appReqBlock.getReqParm("pageMode");

        String activePlacedAgentPK2 = Util.initString(appReqBlock.getReqParm("activePlacedAgentPK2"), null);

        String selectedCommissionContractPK = Util.initString(appReqBlock.getReqParm("selectedCommissionContractPK"), null);

        PlacedAgentBranch placedAgentBranch = null;

        if (activePlacedAgentPK2 != null)
        {
            UserSession userSession = appReqBlock.getUserSession();
            
            placedAgentBranch = PlacedAgentBranch.findBy_PlacedAgentPK(new Long(activePlacedAgentPK2));
        }

        if (agentVO != null)
        {
            agentPK = agentVO.getAgentPK();
        }

        if (agentPK != 0)
        {
            Agent agentComponent = new AgentComponent();

            UserSession userSession = appReqBlock.getUserSession();

            agent.Agent agentEntity = agent.Agent.findByPK(new Long(agentPK));
            
            PlacedAgentBranch[] placedAgentBranches = agentEntity.getPlacedAgentBranches();

            appReqBlock.getHttpServletRequest().setAttribute("placedAgentBranches", placedAgentBranches);
        }

        appReqBlock.getHttpServletRequest().setAttribute("pageMode", Util.initString(pageMode, "BROWSE"));

        String currentPage = (String) appReqBlock.getHttpSession().getAttribute("currentPage");
        String previousPage = currentPage;

        savePreviousPageToVO(appReqBlock, previousPage);

        appReqBlock.getHttpSession().setAttribute("currentPage", AGENT_REPORT_TO);
        appReqBlock.getHttpSession().setAttribute("previousPage", previousPage);
        appReqBlock.getHttpServletRequest().setAttribute("activePlacedAgentPK2", activePlacedAgentPK2);
        appReqBlock.getHttpServletRequest().setAttribute("placedAgentBranch", placedAgentBranch);
        appReqBlock.getHttpServletRequest().setAttribute("selectedCommissionContractPK", selectedCommissionContractPK);

        return AGENT_REPORT_TO;
    }

    /**
     * Find the set of Agents by lastName that have been associated with the specified ContractCodeCT.
     * @param appReqBlock
     * @return
     * @throws Exception
     */
    protected String findReportToAgentByAgentName_AND_ContractCodeCT(AppReqBlock appReqBlock)
        throws Exception
    {
        appReqBlock.setReqParm("activeLowestLevelPlacedAgentPK", null);

        appReqBlock.setReqParm("activePlacedAgentPK", null);

        String startDate = Util.initString(appReqBlock.getReqParm("startDate"), null);

        String situation = Util.initString(appReqBlock.getReqParm("situation"), null);

        String agentNumber = Util.initString(appReqBlock.getReqParm("agentNumber"), null);

        appReqBlock.getHttpServletRequest().setAttribute("startDate", startDate);

        appReqBlock.getHttpServletRequest().setAttribute("situation", situation);

        appReqBlock.getHttpServletRequest().setAttribute("agentNumber", agentNumber);

        return showPlacedAgents(appReqBlock);
    }

    /**
     * Find the set of Agents by lastName that have been associated with the specified CommissionContract.
     * @param appReqBlock
     * @return
     * @throws Exception
     */
    protected String findAgentContractByAgentName_AND_ContractCodeCT(AppReqBlock appReqBlock)
        throws Exception
    {
        appReqBlock.setReqParm("activeAgentContractPK", null);

        return showPlacedAgents(appReqBlock);
    }

    /**
     * Finds the Agent by agentId that have been associated with the specified CommissionContract.
     * @param appReqBlock
     * @return
     * @throws Exception
     */
    protected String findAgentContractByAgentId_AND_ContractCodeCT(AppReqBlock appReqBlock)
        throws Exception
    {
        appReqBlock.setReqParm("activeAgentContractPK", null);

        String startDate = Util.initString(appReqBlock.getReqParm("startDate"), null);

        String situation = Util.initString(appReqBlock.getReqParm("situation"), null);

        String agentNumber = Util.initString(appReqBlock.getReqParm("agentNumber"), null);

        appReqBlock.getHttpServletRequest().setAttribute("startDate", startDate);

        appReqBlock.getHttpServletRequest().setAttribute("situation", situation);

        appReqBlock.getHttpServletRequest().setAttribute("agentNumber", agentNumber);

        return showPlacedAgents(appReqBlock);
    }

    /**
     * Finds a placed-agent and its report-to(s) for the specified commission-contract and agentId.
     * @param appReqBlock
     * @return
     * @throws Exception
     */
    protected String findReportToAgentByAgentId_AND_ContractCodeCT(AppReqBlock appReqBlock)
        throws Exception
    {
        appReqBlock.setReqParm("activeLowestLevelPlacedAgentPK", null);

        appReqBlock.setReqParm("activePlacedAgentPK", null);

        return showPlacedAgents(appReqBlock);
    }

    protected String showHierarchyReport(AppReqBlock appReqBlock)
        throws Exception
    {
        String contractCodeCT = appReqBlock.getReqParm("contractCodeCT");

        String activePlacedAgentPK = Util.initString(appReqBlock.getReqParm("activePlacedAgentPK"), null);

        appReqBlock.getHttpServletRequest().setAttribute("activePlacedAgentPK", activePlacedAgentPK);

        Agent agentComponent = new AgentComponent();

        UserSession userSession = appReqBlock.getUserSession();
        
        // If the param wasn't sent, assume that expired PlacedAgents will not be rendered.
        boolean includeExpiredAgents = (appReqBlock.getReqParm("includeExpiredAgents") != null && appReqBlock.getReqParm("includeExpiredAgents").equals("true"))?true:false;

        HierarchyReport hierarchyReport = agentComponent.generateHierarchyReportByContractCodeCT(contractCodeCT, includeExpiredAgents);

        appReqBlock.getHttpServletRequest().setAttribute("hierarchyReport", hierarchyReport);

        return AgentDetailTran.HIERARCHY_REPORT_DIALOG;
    }

    protected String showCommissionBalanceHistory(AppReqBlock appReqBlock)
        throws Exception
    {
        AgentVO agentVO = (AgentVO) appReqBlock.getHttpSession().getAttribute("agentVO");

        List commissionHistoryVOs = new ArrayList();

        if (agentVO != null)
        {
            ClientRoleVO[] clientRoleVO = agentVO.getClientRoleVO();
            for (int i = 0; i < clientRoleVO.length; i++)
            {
                //Only Agent Role have a financial record now
                if (clientRoleVO[i].getRoleTypeCT().equalsIgnoreCase(ClientRole.ROLETYPECT_AGENT))
                {
                    ClientRoleFinancialVO[] clientRoleFinancialVO = clientRoleVO[i].getClientRoleFinancialVO();

                    String lastCheckDateTime = clientRoleFinancialVO[0].getLastCheckDateTime();

                    if (lastCheckDateTime == null)
                    {
                        lastCheckDateTime = EDITDateTime.DEFAULT_MIN_DATETIME;
                    }

                    Event eventComponent = new EventComponent();

                    List voInclusionList = new ArrayList();
                    voInclusionList.add(EDITTrxHistoryVO.class);
                    voInclusionList.add(EDITTrxVO.class);
                    voInclusionList.add(ClientSetupVO.class);
                    voInclusionList.add(ContractSetupVO.class);
                    voInclusionList.add(SegmentVO.class);

                    PlacedAgentVO[] placedAgentVO = clientRoleVO[i].getPlacedAgentVO();
                    long[] placedAgentPKs = new long[placedAgentVO.length];

                    if ((placedAgentVO != null) && (placedAgentVO.length > 0))
                    {
                        String[] statuses = new String[2];
                        statuses[0] = "B";
                        statuses[1] = "H";

                        for (int j = 0; j < placedAgentVO.length; j++)
                        {
                            placedAgentPKs[j] = placedAgentVO[j].getPlacedAgentPK();
                        }

                        CommissionHistoryVO[] commissionHistoryVO = eventComponent.composeCommissionHistoryVOByUpdateDateTimeGTPlacedAgentPKAndStatus(lastCheckDateTime, placedAgentPKs, statuses, voInclusionList);

                        if (commissionHistoryVO != null)
                        {
                            commissionHistoryVOs.addAll(Arrays.asList(commissionHistoryVO));
                        }
                    }
                }
            }
        }

        appReqBlock.getHttpServletRequest().setAttribute("commissionHistoryVOs", commissionHistoryVOs.toArray(new CommissionHistoryVO[commissionHistoryVOs.size()]));

        return COMMISSION_HISTORY_DIALOG;
    }

    protected String showBonusCommissionBalanceHistory(AppReqBlock appReqBlock)
        throws Exception
    {
        AgentVO agentVO = (AgentVO) appReqBlock.getHttpSession().getAttribute("agentVO");

        List commissionHistoryVOs = new ArrayList();

        if (agentVO != null)
        {
            ClientRoleVO[] clientRoleVO = agentVO.getClientRoleVO();
            for (int i = 0; i < clientRoleVO.length; i++)
            {
                if (clientRoleVO[i].getRoleTypeCT().equalsIgnoreCase(ClientRole.ROLETYPECT_AGENT))
                {
                    ClientRoleFinancialVO[] clientRoleFinancialVO = clientRoleVO[i].getClientRoleFinancialVO();
                    String lastCheckDateTime = clientRoleFinancialVO[0].getLastCheckDateTime();

                    if (lastCheckDateTime == null)
                    {
                        lastCheckDateTime = EDITDateTime.DEFAULT_MIN_DATETIME + " AM";
                    }

                    Event eventComponent = new EventComponent();

                    List voInclusionList = new ArrayList();
                    voInclusionList.add(EDITTrxHistoryVO.class);
                    voInclusionList.add(EDITTrxVO.class);
                    voInclusionList.add(ClientSetupVO.class);
                    voInclusionList.add(ContractSetupVO.class);
                    voInclusionList.add(SegmentVO.class);

                    PlacedAgentVO[] placedAgentVO = clientRoleVO[i].getPlacedAgentVO();
                    long[] placedAgentPKs = new long[placedAgentVO.length];

                    if ((placedAgentVO != null) && (placedAgentVO.length > 0))
                    {
                        String[] statuses = new String[2];
                        statuses[0] = "B";
                        statuses[1] = "H";

                        for (int j = 0; j < placedAgentVO.length; j++)
                        {
                            placedAgentPKs[j] = placedAgentVO[j].getPlacedAgentPK();
                        }

                        CommissionHistoryVO[] commissionHistoryVO = eventComponent.composeCommHistByUpdateDateTimeGTPlacedAgentPKBonusAmtAndStatus(lastCheckDateTime, placedAgentPKs, statuses, voInclusionList);

                        if (commissionHistoryVO != null)
                        {
                            commissionHistoryVOs.addAll(Arrays.asList(commissionHistoryVO));
                        }
                    }
                }
            }
        }

        appReqBlock.getHttpServletRequest().setAttribute("commissionHistoryVOs", commissionHistoryVOs.toArray(new CommissionHistoryVO[commissionHistoryVOs.size()]));

        return COMMISSION_HISTORY_DIALOG;
    }

    protected String showCommissionEarnedHistory(AppReqBlock appReqBlock)
        throws Exception
    {
        AgentVO agentVO = (AgentVO) appReqBlock.getHttpSession().getAttribute("agentVO");

        List commissionHistoryVOs = new ArrayList();

        if (agentVO != null)
        {
            ClientRoleVO[] clientRoleVO = agentVO.getClientRoleVO();
            for (int i = 0; i < clientRoleVO.length; i++)
            {
                if (clientRoleVO[i].getRoleTypeCT().equalsIgnoreCase(ClientRole.ROLETYPECT_AGENT))
                {
                    ClientRoleFinancialVO[] clientRoleFinancialVO = clientRoleVO[i].getClientRoleFinancialVO();
                    String lastCheckDateTime = clientRoleFinancialVO[0].getLastCheckDateTime();

                    Event eventComponent = new EventComponent();

                    List voInclusionList = new ArrayList();
                    voInclusionList.add(EDITTrxHistoryVO.class);
                    voInclusionList.add(EDITTrxVO.class);
                    voInclusionList.add(ClientSetupVO.class);
                    voInclusionList.add(ContractSetupVO.class);
                    voInclusionList.add(SegmentVO.class);

                    PlacedAgentVO[] placedAgentVO = clientRoleVO[i].getPlacedAgentVO();
                    long[] placedAgentPKs = new long[placedAgentVO.length];

                    if ((placedAgentVO != null) && (placedAgentVO.length > 0))
                    {
                        String[] statuses = new String[2];
                        statuses[0] = "B";
                        statuses[1] = "H";

                        for (int j = 0; j < placedAgentVO.length; j++)
                        {
                            placedAgentPKs[j] = placedAgentVO[j].getPlacedAgentPK();
                        }

                        CommissionHistoryVO[] commissionHistoryVO = eventComponent.composeCommissionHistoryVOByUpdateDateTimeGTPlacedAgentPKAndStatus(lastCheckDateTime, placedAgentPKs, statuses, voInclusionList);

                        if (commissionHistoryVO != null)
                        {
                            commissionHistoryVOs.addAll(Arrays.asList(commissionHistoryVO));
                        }
                    }
                }
            }
        }

        appReqBlock.getHttpServletRequest().setAttribute("commissionHistoryVOs", commissionHistoryVOs.toArray(new CommissionHistoryVO[commissionHistoryVOs.size()]));

        return COMMISSION_HISTORY_DIALOG;
    }

    protected String showCommissionHeldHistory(AppReqBlock appReqBlock)
        throws Exception
    {
        AgentVO agentVO = (AgentVO) appReqBlock.getHttpSession().getAttribute("agentVO");

        List commissionHistoryVOs = new ArrayList();

        if ((agentVO != null) && ((agentVO.getHoldCommStatus() != null) && agentVO.getHoldCommStatus().equalsIgnoreCase("Y")))
        {
            ClientRoleVO[] clientRoleVO = agentVO.getClientRoleVO();
            for (int i = 0; i < clientRoleVO.length; i++)
            {
                Event eventComponent = new EventComponent();

                List voInclusionList = new ArrayList();
                voInclusionList.add(EDITTrxHistoryVO.class);
                voInclusionList.add(EDITTrxVO.class);
                voInclusionList.add(ClientSetupVO.class);
                voInclusionList.add(ContractSetupVO.class);
                voInclusionList.add(SegmentVO.class);

                PlacedAgentVO[] placedAgentVO = clientRoleVO[i].getPlacedAgentVO();
                long[] placedAgentPKs = new long[placedAgentVO.length];

                if ((placedAgentVO != null) && (placedAgentVO.length > 0))
                {
                    //Statuses to exclude from search
                    String[] statuses = new String[2];
                    statuses[0] = "H";
                    statuses[1] = "B";

                    for (int j = 0; j < placedAgentVO.length; j++)
                    {
                        placedAgentPKs[j] = placedAgentVO[j].getPlacedAgentPK();
                    }

                    CommissionHistoryVO[] commissionHistoryVO = eventComponent.composeCommissionHistoryVOByPlacedAgentPKExcludingUpdateStatus(placedAgentPKs, statuses, voInclusionList);

                    if (commissionHistoryVO != null)
                    {
                        commissionHistoryVOs.addAll(Arrays.asList(commissionHistoryVO));
                    }
                }
            }
        }

        appReqBlock.getHttpServletRequest().setAttribute("commissionHistoryVOs", commissionHistoryVOs.toArray(new CommissionHistoryVO[commissionHistoryVOs.size()]));

        return COMMISSION_HISTORY_DIALOG;
    }

    protected String showCommissionPendingUpdateHistory(AppReqBlock appReqBlock)
        throws Exception
    {
        AgentVO agentVO = (AgentVO) appReqBlock.getHttpSession().getAttribute("agentVO");

        List commissionHistoryVOs = new ArrayList();
        if (agentVO != null)
        {
           ClientRoleVO[] clientRoleVO = agentVO.getClientRoleVO();
            String selectedAgentNO = (String)appReqBlock.getHttpSession().getAttribute("selectedAgentNumber");
            for (int i = 0; i < clientRoleVO.length; i++)
            {
                String agentNO = Util.initString(clientRoleVO[i].getReferenceID(), "");
                
                if(!agentNO.equalsIgnoreCase(selectedAgentNO))
                {
                    continue;
                }
                Event eventComponent = new EventComponent();

                List voInclusionList = new ArrayList();
                voInclusionList.add(EDITTrxHistoryVO.class);
                voInclusionList.add(EDITTrxVO.class);
                voInclusionList.add(ClientSetupVO.class);
                voInclusionList.add(ContractSetupVO.class);
                voInclusionList.add(SegmentVO.class);

                PlacedAgentVO[] placedAgentVO = clientRoleVO[i].getPlacedAgentVO();
                long[] placedAgentPKs = new long[placedAgentVO.length];

                if ((placedAgentVO != null) && (placedAgentVO.length > 0))
                {
                    String[] statuses = new String[2];
                    statuses[0] = "B";
                    statuses[1] = "H";

                    for (int j = 0; j < placedAgentVO.length; j++)
                    {
                        placedAgentPKs[j] = placedAgentVO[j].getPlacedAgentPK();
                    }

                    CommissionHistoryVO[] commissionHistoryVO = eventComponent.composeCommissionHistoryVOByPlacedAgentPKExcludingUpdateStatus(placedAgentPKs, statuses, voInclusionList);

                    if (commissionHistoryVO != null)
                    {
                        commissionHistoryVOs.addAll(Arrays.asList(commissionHistoryVO));
                    }
                }
            }
        }

        appReqBlock.getHttpServletRequest().setAttribute("commissionHistoryVOs", commissionHistoryVOs.toArray(new CommissionHistoryVO[commissionHistoryVOs.size()]));
         
        return COMMISSION_HISTORY_DIALOG;
    }

    protected String showCommissionCheckHistory(AppReqBlock appReqBlock)
        throws Exception
    {
        AgentVO agentVO = (AgentVO) appReqBlock.getHttpSession().getAttribute("agentVO");
        String selectedCommHistoryPK = appReqBlock.getReqParm("selectedCommissionHistoryPK");

        List commissionHistoryVOs = new ArrayList();

        if (agentVO != null)
        {
            // Tom and I could not make sense of the following code. We did know that we needed to get 
            // the correct ClientRole for the currently selected CommissionHistory. That's the point
            // of the following lookup. I tried to touch as little as possible. GF.
            ClientRole clientRole = ClientRole.findSeparateBy_CommissionHistoryPK(new Long(selectedCommHistoryPK));
            
            ClientRoleVO[] clientRoleVO = agentVO.getClientRoleVO();
            
            for (int i = 0; i < clientRoleVO.length; i++)
            {
                if (clientRoleVO[i].getClientRolePK() == clientRole.getClientRolePK().longValue())
                {
                    Event eventComponent = new EventComponent();
    
                    List checkVOInclusionList = new ArrayList();
                    checkVOInclusionList.add(EDITTrxHistoryVO.class);
    
                    PlacedAgentVO[] placedAgentVO = clientRoleVO[i].getPlacedAgentVO();
                    long[] placedAgentPKs = new long[placedAgentVO.length];
    
                    if ((placedAgentVO != null) && (placedAgentVO.length > 0))
                    {
                        for (int j = 0; j < placedAgentVO.length; j++)
                        {
                            placedAgentPKs[j] = placedAgentVO[j].getPlacedAgentPK();
                        }
    
                        CommissionHistoryVO[] checkCommHist = eventComponent.composeCommissionHistoryVOByPlacedAgentPKAndCommissionType(placedAgentPKs, "Check", checkVOInclusionList);
    
                        if (checkCommHist != null)
                        {
                            List voInclusionList = new ArrayList();
                            voInclusionList.add(EDITTrxHistoryVO.class);
                            voInclusionList.add(EDITTrxVO.class);
                            voInclusionList.add(ClientSetupVO.class);
                            voInclusionList.add(ContractSetupVO.class);
                            voInclusionList.add(SegmentVO.class);
    
                            CommissionHistoryVO[] commissionHistoryVO = null;
    
                            if (checkCommHist.length == 1)
                            {
                                EDITTrxHistoryVO editTrxHistoryVO = (EDITTrxHistoryVO) checkCommHist[0].getParentVO(EDITTrxHistoryVO.class);
    
                                //                                String currentCheckProcessDateTime = editTrxHistoryVO.getProcessDate() + " " + editTrxHistoryVO.getProcessTime();
                                String currentCheckProcessDateTime = editTrxHistoryVO.getProcessDateTime();
                                commissionHistoryVO = eventComponent.composeCommissionHistoryVOByUpdateDateTimeLTPlacedAgentPK(placedAgentPKs, currentCheckProcessDateTime, voInclusionList);
                            }
                            else
                            {
                                for (int k = 0; k < checkCommHist.length; k++)
                                {
                                    if ((checkCommHist[k].getCommissionHistoryPK() + "").equals(selectedCommHistoryPK))
                                    {
                                        EDITTrxHistoryVO currentEditTrxHistoryVO = (EDITTrxHistoryVO) checkCommHist[k].getParentVO(EDITTrxHistoryVO.class);
    
                                        //                                        String currentCheckProcessDateTime = currentEditTrxHistoryVO.getProcessDate() + " " + currentEditTrxHistoryVO.getProcessTime();
                                        String currentCheckProcessDateTime = currentEditTrxHistoryVO.getProcessDateTime();
    
                                        if (checkCommHist.length == (k + 1))
                                        {
                                            commissionHistoryVO = eventComponent.composeCommissionHistoryVOByUpdateDateTimeLTPlacedAgentPK(placedAgentPKs, currentCheckProcessDateTime, voInclusionList);
                                        }
                                        else
                                        {
                                            EDITTrxHistoryVO priorEditTrxHistoryVO = (EDITTrxHistoryVO) checkCommHist[k + 1].getParentVO(EDITTrxHistoryVO.class);
    
                                            //                                            String priorCheckProcessDateTime = priorEditTrxHistoryVO.getProcessDate() + " " + priorEditTrxHistoryVO.getProcessTime();
                                            String priorCheckProcessDateTime = priorEditTrxHistoryVO.getProcessDateTime();
                                            commissionHistoryVO = eventComponent.composeCommissionHistoryVOByUpdateDateTimeGTLTPlacedAgentPK(placedAgentPKs, currentCheckProcessDateTime, priorCheckProcessDateTime,
                                                    voInclusionList);
                                        }
    
                                        break;
                                    }
                                }
                            }
    
                            if (commissionHistoryVO != null)
                            {
                                commissionHistoryVOs.addAll(Arrays.asList(commissionHistoryVO));
                            }
                        }
                    }
            }
            }
        }

        appReqBlock.getHttpServletRequest().setAttribute("commissionHistoryVOs", commissionHistoryVOs.toArray(new CommissionHistoryVO[commissionHistoryVOs.size()]));

        return COMMISSION_HISTORY_DIALOG;
    }

    protected String showExtractDialog(AppReqBlock appReqBlock)
        throws Exception
    {
        String currentPage = (String) appReqBlock.getHttpSession().getAttribute("currentPage");

        appReqBlock.getHttpSession().setAttribute("currentPage", COMMISSION_EXTRACT_DIALOG);
        appReqBlock.getHttpSession().setAttribute("previousPage", currentPage);

        // Check for authorization
        new AgentUseCaseComponent().viewComissionExtract();

        CommissionHistoryVO[] commissionHistoryVO = null;
        AgentVO agentVO = (AgentVO) appReqBlock.getHttpSession().getAttribute("agentVO");

        if (agentVO != null)
        {
            appReqBlock.setReqParm("filterAgentId", agentVO.getAgentNumber());

            getFilteredExtracts(appReqBlock);
        }
        else
        {
            appReqBlock.getHttpSession().removeAttribute("filterAgentId");

            CommissionHistoryExtractCache commHistExractCache = new CommissionHistoryExtractCache(appReqBlock.getHttpSession());
            
            // LOADS IN ALL COMMISSION EXTRACTS BY UPDATE STATUS INTO SESSION.
            // THIS COULD TAKE A WHILE.
            commHistExractCache.loadCommissionExtractsByUpdateStatusIntoSession();
        }

        return COMMISSION_EXTRACT_DIALOG;
    }

    protected String showExtractDetailSummary(AppReqBlock appReqBlock)
        throws Exception
    {
        String selectedCommissionHistoryPK = appReqBlock.getFormBean().getValue("selectedCommissionHistoryPK");
        appReqBlock.getHttpServletRequest().setAttribute("selectedCommissionHistoryPK", selectedCommissionHistoryPK);

        return COMMISSION_EXTRACT_DIALOG;
    }

    protected String showExtractAllowancesDialog(AppReqBlock appReqBlock)
        throws Exception
    {
        String selectedCommissionHistoryPK = appReqBlock.getFormBean().getValue("selectedCommissionHistoryPK");
        appReqBlock.getHttpServletRequest().setAttribute("selectedCommissionHistoryPK", selectedCommissionHistoryPK);

        return EXTRACT_ALLOWANCES_DIALOG;
    }

    protected String saveAllowancesDialog(AppReqBlock appReqBlock)
        throws Exception
    {
        PageBean formBean = appReqBlock.getFormBean();
        String selectedCommissionHistoryPK = formBean.getValue("selectedCommissionHistoryPK");
        String adaAmount = formBean.getValue("adaAmount");
        String expenseAmount = formBean.getValue("expenseAllowance");
        String bonusCommissionAmount = formBean.getValue("bonusAmount");

        //CommissionHistoryVO[] commissionHistoryVOs =
        //    (CommissionHistoryVO[]) appReqBlock.getHttpSession().getAttribute("agentExtractVOs");
        // CHANGED THIS TO USE HELPER - LAZY-LOAD THIS DATA ONLY WHEN NEEDED!
        CommissionHistoryExtractCache commHistExtractCache = new CommissionHistoryExtractCache(appReqBlock.getHttpSession());
        CommissionHistoryVO[] commissionHistoryVOs = commHistExtractCache.getCommissionExtracts();

        for (int h = 0; h < commissionHistoryVOs.length; h++)
        {
            if (commissionHistoryVOs[h].getCommissionHistoryPK() == Long.parseLong(selectedCommissionHistoryPK))
            {
                if (Util.isANumber(adaAmount))
                {
                    //SRAMAM 09/2004 DOUBLE2DECIMAL
                    commissionHistoryVOs[h].setADAAmount(new EDITBigDecimal(adaAmount).getBigDecimal());
                }

                if (Util.isANumber(expenseAmount))
                {
                    commissionHistoryVOs[h].setExpenseAmount(new EDITBigDecimal(expenseAmount).getBigDecimal());
                }

                if (Util.isANumber(bonusCommissionAmount))
                {
                    //                    commissionHistoryVOs[h].setBonusCommissionAmount(new EDITBigDecimal(bonusCommissionAmount).getBigDecimal());
                }

                break;
            }
        }

        return COMMISSION_EXTRACT_DIALOG;
    }

    protected String saveExtractToSummary(AppReqBlock appReqBlock)
        throws Exception
    {
        String operator = appReqBlock.getUserSession().getUsername();

        event.business.Event eventComponent = new event.component.EventComponent();

        PageBean formBean = appReqBlock.getFormBean();
        String transactionType = formBean.getValue("transactionType");
        String selectedCommissionHistoryPK = formBean.getValue("selectedCommissionHistoryPK");
        String commissionableAmount = formBean.getValue("commissionableAmount");
        String commissionAmount = formBean.getValue("commissionAmount");
        String reduceTaxable = formBean.getValue("reduceTaxable");
        String filterAgentId = formBean.getValue("filterAgentId");
        String statementInd = formBean.getValue("statementIndStatus");
        String commHoldReleaseDate = Util.initString(formBean.getValue("commHoldReleaseDate"), null);
        boolean commissionAdjustmentErrored = false;

        if (selectedCommissionHistoryPK.equals(""))
        {
            if (Util.isANumber(commissionAmount))
            {
                Agent agentComponent = new AgentComponent();

                agentComponent.createCommissionAdjustment(filterAgentId, new EDITBigDecimal(commissionAmount), reduceTaxable, operator);
            }
            else
            {
                commissionAdjustmentErrored = true;
                appReqBlock.getHttpServletRequest().setAttribute("extractMessage", "Commission Amount Must Be Entered");
            }
        }
        else
        {
            //CommissionHistoryVO[] commissionHistoryVOs = (CommissionHistoryVO[])
            //              appReqBlock.getHttpSession().getAttribute("agentExtractVOs");
            // CHANGED THIS TO USE HELPER - LAZY-LOAD THIS DATA ONLY WHEN NEEDED!
            CommissionHistoryExtractCache commHistExtractCache = new CommissionHistoryExtractCache(appReqBlock.getHttpSession());
            CommissionHistoryVO[] commissionHistoryVOs = commHistExtractCache.getCommissionExtracts();

            for (int h = 0; h < commissionHistoryVOs.length; h++)
            {
                if (commissionHistoryVOs[h].getCommissionHistoryPK() == Long.parseLong(selectedCommissionHistoryPK))
                {
                    if (Util.isANumber(commissionAmount))
                    {
                        commissionHistoryVOs[h].setCommissionAmount(new EDITBigDecimal(commissionAmount).getBigDecimal());
                    }

                    if (Util.isANumber(commissionableAmount))
                    {
                        EDITTrxHistoryVO editTrxHistory = (EDITTrxHistoryVO) commissionHistoryVOs[h].getParentVO(EDITTrxHistoryVO.class);
                        FinancialHistoryVO[] financialHistoryVO = editTrxHistory.getFinancialHistoryVO();

                        financialHistoryVO[0].setCommissionableAmount(new EDITBigDecimal(commissionableAmount).getBigDecimal());
                        eventComponent.createOrUpdateVO(financialHistoryVO[0], false);
                    }

                    commissionHistoryVOs[h].setCommHoldReleaseDate(commHoldReleaseDate);
                    commissionHistoryVOs[h].setOperator(operator);
                    commissionHistoryVOs[h].setMaintDateTime(new EDITDateTime().getFormattedDateTime());
                    commissionHistoryVOs[h].setReduceTaxable(reduceTaxable);
                    commissionHistoryVOs[h].setStatementInd(statementInd);
                    eventComponent.createOrUpdateVO(commissionHistoryVOs[h], false);

                    break;
                }
            }
        }

        appReqBlock.getHttpServletRequest().setAttribute("filterAgentId", filterAgentId);

        if (commissionAdjustmentErrored)
        {
            appReqBlock.getHttpServletRequest().setAttribute("transactionType", transactionType);
            appReqBlock.getHttpServletRequest().setAttribute("selectedCommissionHistoryPK", selectedCommissionHistoryPK);
            appReqBlock.getHttpServletRequest().setAttribute("commissionableAmount", commissionableAmount);
            appReqBlock.getHttpServletRequest().setAttribute("commissionAmount", commissionAmount);
            appReqBlock.getHttpServletRequest().setAttribute("reduceTaxable", reduceTaxable);

            return addNewExtract(appReqBlock);
        }
        else
        {
            if (filterAgentId.equals(""))
            {
                getAllExtracts(appReqBlock);
            }
            else
            {
                getFilteredExtracts(appReqBlock);
            }

            return COMMISSION_EXTRACT_DIALOG;
        }
    }

    protected String cancelCurrentExtract(AppReqBlock appReqBlock)
        throws Exception
    {
        return COMMISSION_EXTRACT_DIALOG;
    }

    protected String deleteSelectedExtract(AppReqBlock appReqBlock)
        throws Exception
    {
        String selectedCommissionHistoryPK = appReqBlock.getFormBean().getValue("selectedCommissionHistoryPK");
        String transactionType = appReqBlock.getReqParm("transactionType");
        event.business.Event eventComponent = new event.component.EventComponent();

        if (transactionType.equalsIgnoreCase("Commission Adjustment"))
        {
            eventComponent.deleteCommissionAdjustment(Long.parseLong(selectedCommissionHistoryPK));
        }
        else
        {
            eventComponent.deleteVO(CommissionHistoryVO.class, Long.parseLong(selectedCommissionHistoryPK), false);
        }

        String filterAgentId = appReqBlock.getReqParm("filterAgentId");
        appReqBlock.getHttpServletRequest().setAttribute("filterAgentId", filterAgentId);
        appReqBlock.getHttpSession().removeAttribute(CommissionHistoryExtractCache.AGENT_EXTRACT_SESSION_KEY);

        AgentVO agentVO = (AgentVO) appReqBlock.getHttpSession().getAttribute("agentVO");

        if (filterAgentId.equals(""))
        {
            getAllExtracts(appReqBlock);
        }
        else
        {
            getFilteredExtracts(appReqBlock);
        }

        return COMMISSION_EXTRACT_DIALOG;
    }

    protected String closeExtractDialog(AppReqBlock appReqBlock)
        throws Exception
    {
        AgentVO agentVO = (AgentVO) appReqBlock.getHttpSession().getAttribute("agentVO");

        if (agentVO != null)
        {
            appReqBlock.setReqParm("filterAgentId", agentVO.getAgentNumber());

            getFilteredExtracts(appReqBlock);
        }

        String previousPage = (String) appReqBlock.getHttpSession().getAttribute("previousPage");
        String currentPage = (String) appReqBlock.getHttpSession().getAttribute("currentPage");

        loadAuthorizedCompanies(appReqBlock.getUserSession(), appReqBlock);
        
        appReqBlock.getHttpSession().setAttribute("currentPage", previousPage);
        appReqBlock.getHttpSession().setAttribute("previousPage", currentPage);

        if (previousPage.equals(AgentDetailTran.AGENT_REPORT_TO))
        {
            showAgentReportTo(appReqBlock);
        }
        else if (previousPage.equals((AgentDetailTran.AGENT_REQUIREMENTS)))
        {
            showRequirements(appReqBlock);
        }

        return previousPage;
    }

    protected String shiftPlacedAgent(AppReqBlock appReqBlock)
        throws Exception
    {
        String activePlacedAgentPK = Util.initString(appReqBlock.getReqParm("activePlacedAgentPK"), null);

        String activeLowestLevelPlacedAgentPK = Util.initString(appReqBlock.getReqParm("activeLowestLevelPlacedAgentPK"), "0");

        String direction = Util.initString(appReqBlock.getReqParm("shiftDirection"), null);

        String operator = appReqBlock.getUserSession().getUsername();

        Agent agent = new AgentComponent();

        UserSession userSession = appReqBlock.getUserSession();

        PlacedAgentBranchVO placedAgentBranchVO = agent.shiftAgent(Long.parseLong(activePlacedAgentPK), Long.parseLong(activeLowestLevelPlacedAgentPK), Integer.parseInt(direction), operator);

        appReqBlock.setReqParm("activeLowestLevelPlacedAgentPK", placedAgentBranchVO.getPlacedAgentVO(placedAgentBranchVO.getPlacedAgentVOCount() - 1).getPlacedAgentPK() + "");

        return showPlacedAgents(appReqBlock);
    }

    protected String removePlacedAgent(AppReqBlock appReqBlock)
        throws Exception
    {
        // Check for authorization
        new AgentUseCaseComponent().removePlacedAgent();

        String activePlacedAgentPK = Util.initString(appReqBlock.getReqParm("activePlacedAgentPK"), null);

        String activeLowestLevelPlacedAgentPK = Util.initString(appReqBlock.getReqParm("activeLowestLevelPlacedAgentPK"), "0");

        Agent agent = new AgentComponent();

        String operator = appReqBlock.getUserSession().getUsername();

        UserSession userSession = appReqBlock.getUserSession();

        try
        {
            agent.removePlacedAgent(Long.parseLong(activePlacedAgentPK), operator);

            if (activeLowestLevelPlacedAgentPK.equals(activePlacedAgentPK))
            {
                activeLowestLevelPlacedAgentPK = null;
            }

            appReqBlock.setReqParm("activeLowestLevelPlacedAgentPK", activeLowestLevelPlacedAgentPK);

            appReqBlock.setReqParm("activePlacedAgentPK", null);

            appReqBlock.getHttpServletRequest().setAttribute("placedAgentMessage", "Database Updated");
        }
        catch (EDITAgentException e)
        {
            appReqBlock.getHttpServletRequest().setAttribute("placedAgentMessage", e.getMessage());
        }

        return showPlacedAgents(appReqBlock);
    }

    protected String placeAgentContract(AppReqBlock appReqBlock)
        throws Exception
    {
        // Check for authorization
        new AgentUseCaseComponent().addPlacedAgent();

        Agent agent = new AgentComponent();

        String activeAgentContractPK = Util.initString(appReqBlock.getReqParm("activeAgentContractPK"), null);

        String activeLowestLevelPlacedAgentPK = Util.initString(appReqBlock.getReqParm("activeLowestLevelPlacedAgentPK"), null);

        String commissionProfilePK = Util.initString(appReqBlock.getReqParm("commissionProfilePK"), null);

        String operator = appReqBlock.getUserSession().getUsername();

        String startDate = Util.initString(appReqBlock.getReqParm("startDate"), null);

        if (startDate != null)
        {
            startDate = DateTimeUtil.getEDITDateFromMMDDYYYY(startDate).getFormattedDate();
        }

        String situation = Util.initString(appReqBlock.getReqParm("situation"), null);

        String agentNumber = Util.initString(appReqBlock.getReqParm("agentNumber"), null);

        PlacedAgent placedAgentDetails = new PlacedAgent();

        placedAgentDetails.setStartDate(startDate);

        placedAgentDetails.setSituationCode(situation);

        PlacedAgentBranch placedAgentBranch = null;

        try
        {
            long lActiveLowestLevelPlacedAgentPK = (activeLowestLevelPlacedAgentPK == null) ? 0 : Long.parseLong(activeLowestLevelPlacedAgentPK);

            placedAgentBranch = agent.placeAgentContract(new Long(activeAgentContractPK).longValue(),
                                                         lActiveLowestLevelPlacedAgentPK, placedAgentDetails,
                                                         Long.parseLong(commissionProfilePK), operator, agentNumber);

            activeLowestLevelPlacedAgentPK = placedAgentBranch.getLeaf().getPlacedAgentPK().toString();

            appReqBlock.setReqParm("activePlacedAgentPK", null);

            appReqBlock.setReqParm("commissionProfilePK", null);

            appReqBlock.getHttpServletRequest().setAttribute("placedAgentMessage", "Database Updated");
        }
        catch (EDITAgentException e)
        {
            String message = e.getMessage();

            appReqBlock.getHttpServletRequest().setAttribute("placedAgentMessage", message);

            appReqBlock.getHttpServletRequest().setAttribute("startDate", startDate);

            appReqBlock.getHttpServletRequest().setAttribute("situation", situation);

            appReqBlock.getHttpServletRequest().setAttribute("agentNumber", agentNumber);

            appReqBlock.getHttpServletRequest().setAttribute("commissionProfilePK", commissionProfilePK);
        }

        appReqBlock.setReqParm("activeLowestLevelPlacedAgentPK", activeLowestLevelPlacedAgentPK);

        return showPlacedAgents(appReqBlock);
    }

    protected String clearPlacedAgents(AppReqBlock appReqBlock)
        throws Exception
    {
        appReqBlock.setReqParm("activeContractCodeCT", null);
        
        appReqBlock.setReqParm("activeCommissionContractPK", null);
        appReqBlock.setReqParm("activeAgentContractPK", null);
        appReqBlock.setReqParm("activeLowestLevelPlacedAgentPK", null);
        appReqBlock.setReqParm("activePlacedAgentPK", null);
        appReqBlock.setReqParm("agentId", null);
        appReqBlock.setReqParm("reportToId", null);
        appReqBlock.setReqParm("agentName", null);
        appReqBlock.setReqParm("reportToName", null);
        appReqBlock.setReqParm("showGroupPlacedAgentPK", null);

        appReqBlock.setReqParm("startDate", null);

        appReqBlock.setReqParm("situation", null);
        appReqBlock.setReqParm("agentNumber", null);
        appReqBlock.setReqParm("commissionProfilePK", null);

        return showPlacedAgents(appReqBlock);
    }

    protected String associateCommissionProfile(AppReqBlock appReqBlock)
        throws Exception
    {
        Agent agent = new AgentComponent();
        
        EDITDate startDate = DateTimeUtil.getEDITDateFromMMDDYYYY(appReqBlock.getReqParm("startDate"));
        
        EDITDate stopDate = DateTimeUtil.getEDITDateFromMMDDYYYY(appReqBlock.getReqParm("stopDate"));

        String activeCommissionProfilePK = Util.initString(appReqBlock.getReqParm("activeCommissionProfilePK"), null);
        
        String activePlacedAgentPK = appReqBlock.getReqParm("activePlacedAgentPK2");
        
        String activeContractCode = appReqBlock.getReqParm("activeContractCode");

        UserSession userSession = appReqBlock.getUserSession();
        try
        {
            agent.associateCommissionProfile(new Long(activePlacedAgentPK), new Long(activeCommissionProfilePK), startDate, stopDate);
            
            appReqBlock.putInRequestScope("pageMessage", "Commission Profile Successfully Assigned");
        } 
        catch (NumberFormatException e)
        {
            System.out.println(e);
            
            e.printStackTrace();
            
            throw new RuntimeException(e);
        
        } catch (EDITAgentException e)
        {
            appReqBlock.putInRequestScope("pageMessage", e.getMessage());
        }

        appReqBlock.getHttpServletRequest().setAttribute("activeCommissionProfilePK", activeCommissionProfilePK);
        appReqBlock.getHttpServletRequest().setAttribute("activePlacedAgentPK2", activePlacedAgentPK);
        appReqBlock.getHttpServletRequest().setAttribute("activeContractCode", activeContractCode);

        return showCommissionLevelSummaryDialog(appReqBlock);
    }

    protected String closeCommissionLevelSummary(AppReqBlock appReqBlock) throws Exception
    {
        return showAgentReportTo(appReqBlock);
    }

    protected String showCommissionProfile(AppReqBlock appReqBlock)
        throws Exception
    {
        String startDate = Util.initString(appReqBlock.getReqParm("startDate"), null);

        String situation = Util.initString(appReqBlock.getReqParm("situation"), null);

        String agentNumber = Util.initString(appReqBlock.getReqParm("agentNumber"), null);

        appReqBlock.getHttpServletRequest().setAttribute("startDate", startDate);

        appReqBlock.getHttpServletRequest().setAttribute("situation", situation);

        appReqBlock.getHttpServletRequest().setAttribute("agentNumber", agentNumber);

        return showPlacedAgents(appReqBlock);
    }

    protected String showPlacedAgentHierarchy(AppReqBlock appReqBlock)
        throws Exception
    {
        appReqBlock.setReqParm("activePlacedAgentPK", null);

        String startDate = Util.initString(appReqBlock.getReqParm("startDate"), null);

        String situation = Util.initString(appReqBlock.getReqParm("situation"), null);

        String agentNumber = Util.initString(appReqBlock.getReqParm("agentNumber"), null);

        appReqBlock.getHttpServletRequest().setAttribute("startDate", startDate);

        appReqBlock.getHttpServletRequest().setAttribute("situation", situation);

        appReqBlock.getHttpServletRequest().setAttribute("agentNumber", agentNumber);

        return showPlacedAgents(appReqBlock);
    }

    protected String showAgentContractHierarchies(AppReqBlock appReqBlock)
        throws Exception
    {
        return showPlacedAgents(appReqBlock);
    }

    protected String showAgentContracts(AppReqBlock appReqBlock)
        throws Exception
    {
        appReqBlock.setReqParm("activeAgentContractPK", null);
        appReqBlock.setReqParm("activeLowestLevelPlacedAgentPK", null);
        appReqBlock.setReqParm("activePlacedAgentPK", null);

        String startDate = Util.initString(appReqBlock.getReqParm("startDate"), null);

        String situation = Util.initString(appReqBlock.getReqParm("situation"), null);

        String agentNumber = Util.initString(appReqBlock.getReqParm("agentNumber"), null);

        appReqBlock.getHttpServletRequest().setAttribute("startDate", startDate);

        appReqBlock.getHttpServletRequest().setAttribute("situation", situation);

        appReqBlock.getHttpServletRequest().setAttribute("agentNumber", agentNumber);

        return showPlacedAgents(appReqBlock);
    }

    protected String showPlacedAgents(AppReqBlock appReqBlock)
        throws Exception
    {
        // Track previous user selections.
        String activeContractCodeCT = Util.initString(appReqBlock.getReqParm("activeContractCodeCT"), null);
        appReqBlock.getHttpServletRequest().setAttribute("activeContractCodeCT", activeContractCodeCT);

        String agentId = Util.initString(appReqBlock.getReqParm("agentId"), null);
        appReqBlock.getHttpServletRequest().setAttribute("agentId", agentId);

        String reportToId = Util.initString(appReqBlock.getReqParm("reportToId"), null);
        appReqBlock.getHttpServletRequest().setAttribute("reportToId", reportToId);

        String agentName = Util.initString(appReqBlock.getReqParm("agentName"), null);
        appReqBlock.getHttpServletRequest().setAttribute("agentName", agentName);

        String reportToName = Util.initString(appReqBlock.getReqParm("reportToName"), null);
        appReqBlock.getHttpServletRequest().setAttribute("reportToName", reportToName);

        String activeAgentContractPK = Util.initString(appReqBlock.getReqParm("activeAgentContractPK"), null);
        appReqBlock.getHttpServletRequest().setAttribute("activeAgentContractPK", activeAgentContractPK);

        String flagErrors = Util.initString(appReqBlock.getReqParm("flagHierarchyErrors"), "false");
        appReqBlock.getHttpServletRequest().setAttribute("flagHierarchyErrors", flagErrors);

        String showFullDetail = Util.initString(appReqBlock.getReqParm("showFullDetail"), "false");
        appReqBlock.getHttpServletRequest().setAttribute("showFullDetail", showFullDetail);

        // Active PlacedAgentBranch (The PK is actually that of the lowest-level PlacedAgentPK of a branch
        String activeLowestLevelPlacedAgentPK = Util.initString((String) appReqBlock.getReqParm("activeLowestLevelPlacedAgentPK"), null);
        appReqBlock.getHttpServletRequest().setAttribute("activeLowestLevelPlacedAgentPK", activeLowestLevelPlacedAgentPK);

        String activePlacedAgentPK = Util.initString(appReqBlock.getReqParm("activePlacedAgentPK"), null);
        appReqBlock.getHttpServletRequest().setAttribute("activePlacedAgentPK", activePlacedAgentPK);

        // Load summary areas.
        // The Placed Agent Hierarchy will check the existence of this value to disable editing of the agentId.
        String requestSource = appReqBlock.getReqParm("requestSource");

        /// The client may wish to see the group-view of the currently selected lowestLevelPlacedAgentPK
        String showGroupPlacedAgentPK = Util.initString(appReqBlock.getReqParm("showGroupPlacedAgentPK"), null);

        UserSession userSession = appReqBlock.getUserSession();

        if ((activeContractCodeCT != null) && ((agentId != null) || (agentName != null)))
        {
            AgentContract[] agentContracts = null;

            if (agentId != null)
            {
                AgentContract agentContract = AgentContract.findBy_AgentId_AND_ContractCodeCT(agentId, activeContractCodeCT);

                if (agentContract != null)
                {
                    agentContracts = new AgentContract[] { agentContract };
                }
            }
            else if (agentName != null)
            {
                agentContracts = AgentContract.findBy_AgentName_And_ContractCodeCT(agentName, activeContractCodeCT);
            }

            agentContracts = checkForAuthorization(appReqBlock, agentContracts);

            CommissionProfile[] commissionProfiles = CommissionProfile.findBy_ContractCodeCT(activeContractCodeCT);

            appReqBlock.getHttpServletRequest().setAttribute("commissionProfiles", commissionProfiles);

            appReqBlock.getHttpServletRequest().setAttribute("agentContracts", agentContracts);
        }

        if ((activeContractCodeCT != null) && ((reportToId != null) || (reportToName != null)))
        {
            PlacedAgentBranch[] placedAgentBranches = null;

            if (reportToId != null)
            {
                placedAgentBranches = PlacedAgentBranch.findBy_AgentId_AND_ContractCodeCT(reportToId, activeContractCodeCT, 2);
            }
            else if (reportToName != null)
            {
                placedAgentBranches = PlacedAgentBranch.findBy_AgentName_AND_ContractCodeCT(reportToName, activeContractCodeCT, 2);
            }

            appReqBlock.getHttpServletRequest().setAttribute("placedAgentBranches", placedAgentBranches);
        }

//        if (showGroupPlacedAgentPK != null)
//        {
//            Agent agentComponent = new AgentComponent();
//
//            HierarchyReport hierarchyReport = agentComponent.generateHierarchyReportByPlacedAgent(Long.parseLong(showGroupPlacedAgentPK));
//
//            appReqBlock.getHttpServletRequest().setAttribute("hierarchyReport", hierarchyReport);
//            appReqBlock.getHttpServletRequest().setAttribute("showGroupPlacedAgentPK", showGroupPlacedAgentPK);
//        }

        if (activeLowestLevelPlacedAgentPK != null)
        {
            PlacedAgentBranch placedAgentBranch = PlacedAgentBranch.findBy_PlacedAgentPK(new Long(activeLowestLevelPlacedAgentPK));

            appReqBlock.getHttpServletRequest().setAttribute("placedAgentBranch", placedAgentBranch);
        }

        if (activePlacedAgentPK != null)
        {
            PlacedAgentVO placedAgentVO = composePlacedAgentVOByPlacedAgentPK(activePlacedAgentPK, new ArrayList());

            appReqBlock.getHttpServletRequest().setAttribute("placedAgentVO", placedAgentVO);
        }

        appReqBlock.getHttpServletRequest().setAttribute("requestSource", requestSource);

        return PLACED_AGENTS;
    }

    /**
     * Will set the InactiveIndicator on the lowest level agent in a specified hierarchy - if the indicator on the
     * Agent Hierarchy page is checked, the InactiveIndicator on the PlacedAgent table in the Role database will be
     * set to 'Y', otherwise it will be set to 'N'.
     * @param appReqBlock
     * @return
     * @throws Exception
     */
    protected String setPlacedAgentInactiveInd(AppReqBlock appReqBlock)
        throws Exception
    {
        String inactiveIndicator = appReqBlock.getReqParm("inactiveIndicator");
        String activeLowestLevelPlacedAgentPK = appReqBlock.getReqParm("activeLowestLevelPlacedAgentPK");
        agent.business.Agent agentComponent = new agent.component.AgentComponent();
        PlacedAgentVO placedAgentVO = agentComponent.composePlacedAgentVOByPlacedAgentPK(Long.parseLong(activeLowestLevelPlacedAgentPK), new ArrayList());
        placedAgentVO.setInactiveIndicator(inactiveIndicator);

        PlacedAgent placedAgent = new PlacedAgent(placedAgentVO);
        placedAgent.hSave();

        return showPlacedAgents(appReqBlock);
    }

    protected String showJumpToDialog(AppReqBlock appReqBlock)
        throws Exception
    {
        String jumpToTarget = appReqBlock.getReqParm("jumpToTarget");
        appReqBlock.getHttpServletRequest().setAttribute("jumpToTarget", jumpToTarget);

        return JUMP_TO_DIALOG;
    }

    protected void preProcessRequest(AppReqBlock appReqBlock)
        throws Exception
    {
    }

    protected void postProcessRequest(AppReqBlock appReqBlock)
        throws Exception
    {
    }

    protected String loadAgentDetailAfterSearch(AppReqBlock appReqBlock)
        throws Exception
    {
        appReqBlock.getHttpSession().removeAttribute("commissionHistoryVOs");
        appReqBlock.getHttpSession().removeAttribute(CommissionHistoryExtractCache.AGENT_EXTRACT_SESSION_KEY);

        //        appReqBlock.getHttpSession().removeAttribute("agentChangeHistoryVOs");
        appReqBlock.getHttpSession().setAttribute("searchValue", appReqBlock.getReqParm("searchValue"));

        UserSession userSession = appReqBlock.getUserSession();

        userSession.setAgentPK(Long.parseLong(appReqBlock.getReqParm("searchValue")));

        loadAuthorizedCompanies(userSession, appReqBlock);

        String bypassFrameset = appReqBlock.getReqParm("bypassFrameset");

        if ((bypassFrameset != null) && bypassFrameset.equals("true")) // If we are reloading the agent after having saved it, we need to skip the loading of the frameset.
        {
            return showAgentDetailMainContent(appReqBlock);
        }
        else
        {
            return AGENT_DETAIL_MAIN_FRAMESET;
        }
    }

    protected String showAgentDetailMainDefault(AppReqBlock appReqBlock)
        throws Exception
    {
        appReqBlock.getHttpSession().removeAttribute("agentVO");
        appReqBlock.getHttpSession().removeAttribute("clientDetailVO");
        appReqBlock.getHttpSession().removeAttribute("clientDetailVOs");
        appReqBlock.getHttpSession().removeAttribute("commissionHistoryVOs");
        appReqBlock.getHttpSession().removeAttribute(CommissionHistoryExtractCache.AGENT_EXTRACT_SESSION_KEY);

        appReqBlock.getHttpSession().setAttribute("currentPage", AGENT_DETAIL_MAIN);
        appReqBlock.getHttpSession().setAttribute("previousPage", "");

        return AGENT_DETAIL_MAIN;
    }

    protected String showAgentDetailMainContent(AppReqBlock appReqBlock)
        throws Exception
    {
        String agentPK = (String) appReqBlock.getReqParm("agentPK");
        AgentVO agentVO = (AgentVO) appReqBlock.getHttpSession().getAttribute("agentVO");

        UserSession userSession = appReqBlock.getUserSession();

        if ((agentPK != null) && !agentPK.equals("0") && !userSession.getAgentIsLocked())
        //                (agentVO == null || !(agentVO.getAgentPK() + "").equals(agentPK)))
        {
            //composeAgentAndCommissionHistory(appReqBlock, agentPK);
            composeAgent(appReqBlock, agentPK);
        }

        loadAuthorizedCompanies(userSession, appReqBlock);

        userSession.setParameter("selectedClientRoleFK", (String)appReqBlock.getHttpSession().getAttribute("clientRoleFK"));

        appReqBlock.getHttpSession().setAttribute("currentPage", AGENT_DETAIL_MAIN);
        appReqBlock.getHttpSession().setAttribute("previousPage", "");

        return AGENT_DETAIL_MAIN;
    }

    //private void composeAgentAndCommissionHistory(AppReqBlock appReqBlock, String agentPK) throws Exception
    private void composeAgent(AppReqBlock appReqBlock, String agentPK)
        throws Exception
    {
        appReqBlock.getHttpSession().removeAttribute("commissionHistoryVOs");
        appReqBlock.getHttpSession().removeAttribute(CommissionHistoryExtractCache.AGENT_EXTRACT_SESSION_KEY);

        //        appReqBlock.getHttpSession().removeAttribute("agentChangeHistoryVOs");
        AgentVO agentVO = (AgentVO) appReqBlock.getHttpSession().getAttribute("agentVO");

        Agent agent = new AgentComponent();

        List voInclusionList = new ArrayList();
        voInclusionList.add(AgentNoteVO.class);
        voInclusionList.add(VestingVO.class);
        voInclusionList.add(CheckAdjustmentVO.class);
        voInclusionList.add(AgentLicenseVO.class);
        voInclusionList.add(AdditionalCompensationVO.class);
        voInclusionList.add(AgentContractVO.class);
        voInclusionList.add(PlacedAgentVO.class);
        voInclusionList.add(RedirectVO.class);
        voInclusionList.add(ClientRoleVO.class);
        voInclusionList.add(ClientRoleFinancialVO.class);
        voInclusionList.add(ClientDetailVO.class);
        voInclusionList.add(PreferenceVO.class);
        voInclusionList.add(TaxInformationVO.class);
        voInclusionList.add(TaxProfileVO.class);
        voInclusionList.add(AgentRequirementVO.class);

        //        voInclusionList.add(AgentChangeHistoryVO.class);
        agentVO = agent.composeAgentVO(Long.parseLong(agentPK), voInclusionList);
        ClientDetailVO clientDetailVO = null;
        if(agentVO.getClientRoleVO().length >0)
        {
            clientDetailVO = (ClientDetailVO) agentVO.getClientRoleVO(0).getParentVO(ClientDetailVO.class);
        }
        
        // This isn't good, but we need the ClientRoleVO associated with the ClientRole.RoleTypeCT = "Agent".
        ClientRoleVO agentClientRoleVO = null;
        
        ClientRoleVO[] clientRoleVOs = agentVO.getClientRoleVO();
        
        for (ClientRoleVO currentClientRoleVO:clientRoleVOs)
        {
            if (currentClientRoleVO.getRoleTypeCT().equals(ClientRole.ROLETYPECT_AGENT))
            {
                agentClientRoleVO = currentClientRoleVO;
                
                break;
            }
        }
        

        appReqBlock.getHttpSession().setAttribute("agentVO", agentVO);
        appReqBlock.getHttpSession().setAttribute("clientRoleFK", agentClientRoleVO.getClientRolePK() + "");
        appReqBlock.getHttpSession().setAttribute("clientDetailVO", clientDetailVO);
        appReqBlock.getHttpSession().setAttribute("agentPK", agentPK);

        // THIS HAS BEEN CHANGED TO LAZY-LOAD.  SEE CommissionHistoryExtractCache class.
        //getCommissionExtracts(appReqBlock, agentVO);
    }

    private List reformatChangeHistory(ChangeHistoryVO[] agentChangeHistoryVOs, EDITDate fromDate, EDITDate toDate)
    {
        List commissionHistory = new ArrayList();

        for (int i = 0; i < agentChangeHistoryVOs.length; i++)
        {
            EDITDate effectiveDate = new EDITDate(agentChangeHistoryVOs[i].getEffectiveDate());

            if (effectiveDate.before(toDate) || effectiveDate.equals(toDate))
            {
                //  fromDate can be null, if so, don't check it for the date range, otherwise, do
                if (fromDate == null || (effectiveDate.after(fromDate) || effectiveDate.equals(fromDate)))
                {
                    EDITTrxHistoryVO editTrxHistoryVO = new EDITTrxHistoryVO();
                    EDITTrxVO editTrxVO = new EDITTrxVO();
                    editTrxVO.setEffectiveDate(agentChangeHistoryVOs[i].getEffectiveDate());
                    editTrxVO.setTransactionTypeCT("NF");

                    //                editTrxHistoryVO.setProcessDate(agentChangeHistoryVOs[i].getEffectiveDate());
                    editTrxHistoryVO.setProcessDateTime(new EDITDateTime(agentChangeHistoryVOs[i].getEffectiveDate()).toString());
                    editTrxHistoryVO.setParentVO(EDITTrxVO.class, editTrxVO);

                    CommissionHistoryVO commissionHistoryVO = new CommissionHistoryVO();
                    commissionHistoryVO.setCommissionHistoryPK(agentChangeHistoryVOs[i].getChangeHistoryPK());
                    commissionHistoryVO.setAccountingPendingStatus("");
                    commissionHistoryVO.setUpdateStatus("");
                    commissionHistoryVO.setParentVO(EDITTrxHistoryVO.class, editTrxHistoryVO);
                    commissionHistory.add(commissionHistoryVO);
                }
            }
        }

        return commissionHistory;
    }

    protected String showVestingDialog(AppReqBlock appReqBlock)
        throws Exception
    {
        String currentPage = (String) appReqBlock.getHttpSession().getAttribute("currentPage");
        String previousPage = currentPage;

        savePreviousPageToVO(appReqBlock, previousPage);

        appReqBlock.getHttpSession().setAttribute("currentPage", AGENT_VESTING_DIALOG);
        appReqBlock.getHttpSession().setAttribute("previousPage", previousPage);

        return AGENT_VESTING_DIALOG;
    }

    protected String saveVesting(AppReqBlock appReqBlock)
        throws Exception
    {
        CodeTableWrapper codeTableWrapper = CodeTableWrapper.getSingleton();

        AgentVO agentVO = (AgentVO) appReqBlock.getHttpSession().getAttribute("agentVO");
        PageBean formBean = appReqBlock.getFormBean();

        boolean vestingVOExisted = true;
        VestingVO[] vestingVOs = agentVO.getVestingVO();
        VestingVO vestingVO = null;

        if ((vestingVOs == null) || (vestingVOs.length == 0))
        {
            vestingVO = new VestingVO();
            vestingVOExisted = false;
        }
        else
        {
            vestingVO = vestingVOs[0];
        }

        String vestingPK = formBean.getValue("vestingPK");

        if (Util.isANumber(vestingPK))
        {
            vestingVO.setVestingPK(Long.parseLong(vestingPK));
        }
        else
        {
            vestingVO.setVestingPK(0);
        }

        vestingVO.setAgentFK(agentVO.getAgentPK());

        String vestingBasis = formBean.getValue("vestingBasis");

        if (Util.isANumber(vestingBasis))
        {
            vestingBasis = codeTableWrapper.getCodeTableEntry(Long.parseLong(vestingBasis)).getCode();
        }
        else
        {
            vestingBasis = "";
        }

        vestingVO.setTermVestingBasisCT(vestingBasis);

        String vestingStatus = formBean.getValue("vestingStatus");

        if (Util.isANumber(vestingStatus))
        {
            vestingStatus = codeTableWrapper.getCodeTableEntry(Long.parseLong(vestingStatus)).getCode();
        }
        else
        {
            vestingStatus = "";
        }

        vestingVO.setTermVestingStatusCT(vestingStatus);

        String vestingPercent = formBean.getValue("vestingPercent");

        if (Util.isANumber(vestingPercent))
        {
            vestingVO.setTermVestingPercent(new EDITBigDecimal(vestingPercent).getBigDecimal());

            // vestingVO.setTermVestingPercent(Double.parseDouble(vestingPercent));
        }
        else
        {
            //vestingVO.setTermVestingPercent(0);
            vestingVO.setTermVestingPercent(new EDITBigDecimal().getBigDecimal());
        }

        String vestingDuration = formBean.getValue("vestingDuration");
        if (Util.isANumber(vestingDuration))
        {
            vestingVO.setVestingDuration(Integer.parseInt(vestingDuration));
        }
        else
        {
            vestingVO.setVestingDuration(0);
        }

        if (!vestingVOExisted)
        {
            agentVO.addVestingVO(vestingVO);
        }

        UserSession userSession = appReqBlock.getUserSession();
        loadAuthorizedCompanies(userSession, appReqBlock);

        appReqBlock.getHttpSession().setAttribute("currentPage", AGENT_DETAIL_MAIN);
        appReqBlock.getHttpSession().setAttribute("previousPage", AGENT_VESTING_DIALOG);

        return AGENT_DETAIL_MAIN;
    }

    protected String showPaymentRoutingInfoDialog(AppReqBlock appReqBlock)
        throws Exception
    {
        String currentPage = (String) appReqBlock.getHttpSession().getAttribute("currentPage");
        String previousPage = currentPage;

        savePreviousPageToVO(appReqBlock, previousPage);

        appReqBlock.getHttpSession().setAttribute("currentPage", AGENT_PAYMENT_ROUTING_INFO_DIALOG);
        appReqBlock.getHttpSession().setAttribute("previousPage", previousPage);

        return AGENT_PAYMENT_ROUTING_INFO_DIALOG;
    }

    protected String showTaxInfoDialog(AppReqBlock appReqBlock)
        throws Exception
    {
        String currentPage = (String) appReqBlock.getHttpSession().getAttribute("currentPage");
        String previousPage = currentPage;

        savePreviousPageToVO(appReqBlock, previousPage);

        appReqBlock.getHttpSession().setAttribute("currentPage", AGENT_TAX_INFO_DIALOG);
        appReqBlock.getHttpSession().setAttribute("previousPage", previousPage);

        return AGENT_TAX_INFO_DIALOG;
    }

    protected String showNotesDialog(AppReqBlock appReqBlock)
        throws Exception
    {
        String currentPage = (String) appReqBlock.getHttpSession().getAttribute("currentPage");
        String previousPage = currentPage;

        savePreviousPageToVO(appReqBlock, previousPage);

        // the following code is added to implement notes functionality
        // before agent notes dialog does not have two buttons 'SAVE' and 'CANCEL'
        // as like quote notes page and contract notes page
        // syam prasad 8/17/04
        // let me verify whether already agentnotes are loaded or not?
        AgentVO agentVO;
        AgentNoteVO[] agentNoteVOs;
        agentVO = (AgentVO) appReqBlock.getHttpSession().getAttribute("agentVO");

        agentNoteVOs = agentVO.getAgentNoteVO();

        appReqBlock.getHttpSession().setAttribute("UnchangedNotesVOs", agentNoteVOs);

        appReqBlock.getHttpSession().setAttribute("currentPage", AGENT_NOTES_DIALOG);
        appReqBlock.getHttpSession().setAttribute("previousPage", previousPage);

        return AGENT_NOTES_DIALOG;
    }

    protected String showNotesDetailSummary(AppReqBlock appReqBlock)
        throws Exception
    {
        String agentNotePK = appReqBlock.getFormBean().getValue("agentNotePK");
        String selectedNoteType = appReqBlock.getFormBean().getValue("selectedNoteType");
        appReqBlock.getHttpServletRequest().setAttribute("agentNotePK", agentNotePK);
        appReqBlock.getHttpServletRequest().setAttribute("selectedNoteType", selectedNoteType);

        return AGENT_NOTES_DIALOG;
    }

    protected String saveNoteToSummary(AppReqBlock appReqBlock)
        throws Exception
    {
        new AgentUseCaseComponent().updateAgentNotes();

        CodeTableWrapper codeTableWrapper = CodeTableWrapper.getSingleton();

        AgentVO agentVO = (AgentVO) appReqBlock.getHttpSession().getAttribute("agentVO");
        PageBean formBean = appReqBlock.getFormBean();

        String agentNotePK = formBean.getValue("agentNotePK");
        String noteType = formBean.getValue("noteTypeId");

        if (Util.isANumber(noteType))
        {
            noteType = codeTableWrapper.getCodeTableEntry(Long.parseLong(noteType)).getCode();
        }
        else
        {
            noteType = "";
        }

        boolean agentNoteExisted = false;
        AgentNoteVO[] noteVOs = null;
        AgentNoteVO agentNoteVO = null;

        /**
         * identified the problem that when user is trying to modify the existing
         * note, actually the system is adding a new note.
         * identified that there is problem in the following code
         * actually the coder is setting the boolean value agentNoteExisted to true
         * in the condition when  'noteVOs' is null object which
         * is never true because getAgentVO() never throws null,
         * because getAgentNoteVO() method does not return null object
         * another problem i see is why the coder is looping through null object???????
         * see the modified code just below the commented code
         * syam lingamallu 08/11/04
        **/

        /* if (noteVOs == null)
        {
            for (int n = 0; n < noteVOs.length; n++)
            {
                if (!agentNotePK.equals("0"))
                {
                    agentNoteVO = noteVOs[n];
                    agentNoteExisted = true;
                }
            }
        } */

        // when new agent note is being added the value of agentNotePK is "" (received from form),
        // so no need to check in the existing agentNoteVO list
        if (!agentNotePK.equals("") && !agentNotePK.equals("0"))
        {
            // make a call to the method that returns all the agentNoteVOs related to agent
            noteVOs = agentVO.getAgentNoteVO();

            if ((noteVOs != null) && (noteVOs.length > 0))
            {
                // loop through agentNoteVOs to find out the matching one
                for (int n = 0; n < noteVOs.length; n++)
                {
                    agentNoteVO = (AgentNoteVO) noteVOs[n];

                    //if (agentNotePK.equals(String.valueOf(agentNoteVO.getAgentNotePK())))
                    if (agentNoteVO != null)
                    {
                        // agentNotePK is the one that is passed by the form which is captured above
                        if ((Long.valueOf(agentNotePK.trim())).longValue() == agentNoteVO.getAgentNotePK())
                        {
                            // if it finds one set boolean value to true
                            agentNoteExisted = true;

                            break;
                        }

                        // end if
                    }

                    // end if
                }

                // end for
            }

            // end if
        }

        // end if
        if (!agentNoteExisted)
        {
            Agent agent = new AgentComponent();
            agentNoteVO = new AgentNoteVO();
            agentNoteVO.setAgentNotePK(agent.getNextAvailableKey() * -1);
        }

        agentNoteVO.setAgentFK(agentVO.getAgentPK());
        agentNoteVO.setNoteTypeCT(Util.initString(noteType, null));

        String sequence = formBean.getValue("sequence");
        int noteSequence = 0;

        if (!Util.isANumber(sequence))
        {
            noteSequence = setAgentNoteSequenceNumber(agentVO);
        }
        else
        {
            noteSequence = Integer.parseInt(sequence);
        }

        agentNoteVO.setSequence(noteSequence);

        String noteQualifier = formBean.getValue("noteQualifierId");

        if (Util.isANumber(noteQualifier))
        {
            noteQualifier = codeTableWrapper.getCodeTableEntry(Long.parseLong(noteQualifier)).getCode();
        }
        else
        {
            noteQualifier = "";
        }

        agentNoteVO.setNoteQualifierCT(Util.initString(noteQualifier, null));

        String note = formBean.getValue("note");
        agentNoteVO.setNote(Util.initString(note, null));

        String operator = appReqBlock.getUserSession().getUsername();

        agentNoteVO.setOperator(Util.initString(operator, null));
        agentNoteVO.setMaintDateTime(new EDITDateTime().getFormattedDateTime());

        if (!agentNoteExisted)
        {
            agentVO.addAgentNoteVO(agentNoteVO);
        }

        return AGENT_NOTES_DIALOG;
    }

    protected String closeNotesDialog(AppReqBlock appReqBlock)
        throws Exception
    {
        appReqBlock.getHttpSession().setAttribute("currentPage", AGENT_DETAIL_MAIN);
        appReqBlock.getHttpSession().setAttribute("previousPage", AGENT_NOTES_DIALOG);

        return AGENT_DETAIL_MAIN;
    }

    protected String deleteCurrentNote(AppReqBlock appReqBlock)
        throws Exception
    {
        new AgentUseCaseComponent().updateAgentNotes();

        String agentNotePK = appReqBlock.getReqParm("agentNotePK");
        AgentVO agentVO = (AgentVO) appReqBlock.getHttpSession().getAttribute("agentVO");

        if (agentVO != null)
        {
            AgentNoteVO[] agentNoteVOs = agentVO.getAgentNoteVO();

            if (agentNoteVOs != null)
            {
                for (int n = 0; n < agentNoteVOs.length; n++)
                {
                    if ((agentNoteVOs[n].getAgentNotePK() + "").equals(agentNotePK))
                    {
                        if (agentNotePK.startsWith("-"))
                        {
                            agentVO.removeAgentNoteVO(n);
                        }
                        else
                        {
                            agentNoteVOs[n].setVoShouldBeDeleted(true);
                        }
                    }
                }
            }
        }

        return AGENT_NOTES_DIALOG;
    }

    protected String showRequirements(AppReqBlock appReqBlock) throws Exception
    {
        String currentPage = (String) appReqBlock.getHttpSession().getAttribute("currentPage");
        String previousPage = currentPage;

        savePreviousPageToVO(appReqBlock, previousPage);

        appReqBlock.getHttpSession().setAttribute("currentPage", AGENT_REQUIREMENTS);
        appReqBlock.getHttpSession().setAttribute("previousPage", previousPage);

        AgentVO agentVO = (AgentVO) appReqBlock.getHttpSession().getAttribute("agentVO");

        ProductStructure productStructure = ProductStructure.findByNames("Agent", "*", "*", "*", "*");
        agent.Agent selectedAgent = agent.Agent.findBy_PK(new Long(agentVO.getAgentPK()));

        new AgentRequirementsTableModel(productStructure, selectedAgent, appReqBlock);

        return AGENT_REQUIREMENTS;
    }

    protected String cancelRequirement(AppReqBlock appReqBlock) throws Exception
    {
        ProductStructure productStructure = ProductStructure.findByNames("Agent", "*", "*", "*", "*");

        AgentVO agentVO = (AgentVO) appReqBlock.getHttpSession().getAttribute("agentVO");

        agent.Agent selectedAgent = agent.Agent.findBy_PK(new Long(agentVO.getAgentPK()));

        new AgentRequirementsTableModel(productStructure, selectedAgent, appReqBlock).resetAllRows();

        return showRequirements(appReqBlock);
    }

    protected String addRequirement(AppReqBlock appReqBlock) throws Exception
    {
        return showManualRequirementDialog(appReqBlock);
    }

    protected String saveRequirement(AppReqBlock appReqBlock) throws Exception
    {
        AgentRequirement agentRequirement = (AgentRequirement) Util.mapFormDataToHibernateEntity(appReqBlock.getHttpServletRequest(), AgentRequirement.class, SessionHelper.EDITSOLUTIONS, false);

        String receivedDate = Util.initString(appReqBlock.getReqParm("receivedDate"), null);
        if (receivedDate == null)
        {
            agentRequirement.setReceivedDate(null);
        }

        String effectiveDate = Util.initString(appReqBlock.getReqParm("effectiveDate"), null);
        if (effectiveDate == null)
        {
            agentRequirement.setEffectiveDate(null);
        }

        String followupDate = Util.initString(appReqBlock.getReqParm("followupDate"), null);
        if (followupDate == null)
        {
            agentRequirement.setFollowupDate(null);
        }

        AgentUseCase agentUseCaseComponent = new AgentUseCaseComponent();

        agentUseCaseComponent.updateAgentRequirement(agentRequirement);

        ProductStructure productStructure = ProductStructure.findByNames("Agent", "*", "*", "*", "*");

        AgentVO agentVO = (AgentVO) appReqBlock.getHttpSession().getAttribute("agentVO");

        agent.Agent selectedAgent = agent.Agent.findBy_PK(new Long(agentVO.getAgentPK()));

        new AgentRequirementsTableModel(productStructure, selectedAgent, appReqBlock).resetAllRows();

        appReqBlock.getHttpServletRequest().setAttribute("responseMessage", "Database Updated");

        return showRequirements(appReqBlock);
    }

    protected String deleteSelectedRequirement(AppReqBlock appReqBlock) throws Exception
    {
        String agentRequirementPK = appReqBlock.getReqParm("agentRequirementPK");

        AgentUseCase agentUseCaseComponent = new AgentUseCaseComponent();

        AgentRequirement agentRequirement = AgentRequirement.findByPK(new Long(agentRequirementPK));

        agentUseCaseComponent.deleteRequirement(agentRequirement);

        appReqBlock.getHttpServletRequest().setAttribute("responseMessage", "Database Updated");

        return showRequirements(appReqBlock);
    }

    protected String showRequirementDetail(AppReqBlock appReqBlock) throws Exception
    {
        AgentVO agentVO = (AgentVO) appReqBlock.getHttpSession().getAttribute("agentVO");

        ProductStructure productStructure = ProductStructure.findByNames("Agent", "*", "*", "*", "*");
        agent.Agent selectedAgent = agent.Agent.findBy_PK(new Long(agentVO.getAgentPK()));

        String agentRequirementPK = new AgentRequirementsTableModel(productStructure, selectedAgent, appReqBlock).getSelectedRowId();

        AgentRequirement agentRequirement = AgentRequirement.findByPK(new Long(agentRequirementPK));

        appReqBlock.getHttpServletRequest().setAttribute("activeAgentRequirement", agentRequirement);

        return showRequirements(appReqBlock);
    }

    private String showManualRequirementDialog(AppReqBlock appReqBlock) throws Exception
    {
        ProductStructure productStructure = ProductStructure.findByNames("Agent", "*", "*", "*", "*");

        Requirement[] requirements = Requirement.findBy_ProductStructure_And_ManualInd(productStructure, "Y");

        appReqBlock.getHttpServletRequest().setAttribute("requirements", requirements);

        return AGENT_MANUAL_REQUIREMENT_DIALOG;
    }

    private String saveManualRequirement(AppReqBlock appReqBlock) throws Exception
    {
        AgentUseCase agentUseCaseComponent = new AgentUseCaseComponent();

        String requirementId = appReqBlock.getReqParm("requirementId");

        Requirement requirement = Requirement.findBy_RequirementId(requirementId);

        AgentVO agentVO = (AgentVO) appReqBlock.getHttpSession().getAttribute("agentVO");

        ProductStructure productStructure = ProductStructure.findByNames("Agent", "*", "*", "*", "*");

        FilteredRequirement filteredRequirement = FilteredRequirement.findBy_ProductStructure_And_Requirement(productStructure, requirement);

        agent.Agent selectedAgent = agent.Agent.findBy_PK(new Long(agentVO.getAgentPK()));

        AgentRequirement agentRequirement = AgentRequirement.findBy_Agent_And_FilteredRequirement(selectedAgent, filteredRequirement);

        String responseMessage = null;

        if (agentRequirement == null)
        {
            agentUseCaseComponent.associateFilteredRequirementToAgent(selectedAgent, filteredRequirement);

            responseMessage = "Database Updated";
        }
        else
        {
            responseMessage = "This Requirement is already associated with the Agent.";
        }

        appReqBlock.getHttpServletRequest().setAttribute("responseMessage", responseMessage);

        return showRequirements(appReqBlock);
    }

    private String showRequirementDescription(AppReqBlock appReqBlock) throws Exception
    {
        String requirementId = appReqBlock.getReqParm("requirementId");

        Requirement requirement = Requirement.findBy_RequirementId(requirementId);

        appReqBlock.getHttpServletRequest().setAttribute("requirement", requirement);

        return showManualRequirementDialog(appReqBlock);
    }

    protected String showFinancial(AppReqBlock appReqBlock)
        throws Exception
    {
        String currentPage = (String) appReqBlock.getHttpSession().getAttribute("currentPage");
        String previousPage = currentPage;

        savePreviousPageToVO(appReqBlock, previousPage);

        appReqBlock.getHttpSession().setAttribute("currentPage", AGENT_FINANCIAL);
        appReqBlock.getHttpSession().setAttribute("previousPage", previousPage);

        return AGENT_FINANCIAL;
    }

    protected String showSelectedAgentFinancial(AppReqBlock appReqBlock) throws Exception
    {
        String selectedAgentNumber = appReqBlock.getFormBean().getValue("selectedAgentNumber");
        if(selectedAgentNumber.equals("") || selectedAgentNumber.equalsIgnoreCase("Please Select"))
        {
            return AGENT_FINANCIAL;
        }

        List commissionHistoryVOs = new ArrayList();

        ClientRole clientRole = ClientRole.findBy_RoleType_ReferenceID(ClientRole.ROLETYPECT_AGENT, selectedAgentNumber)[0];

        ClientRoleFinancial clientRoleFinancial = clientRole.getClientRoleFinancial();

        String lastCheckDateTime = null;
        String lastStatementDateTime = null;

//      Modified at Vision's request 2/11/2011 to use LastStatementDateTime instead of LastCheckDateTime to pull
//      CommissionHistory records for agent financial display.
        if (clientRoleFinancial == null)
        {
//            lastCheckDateTime = EDITDateTime.DEFAULT_MIN_DATETIME;
              lastStatementDateTime = EDITDateTime.DEFAULT_MIN_DATETIME;
        }
        else if (clientRoleFinancial.getLastCheckDateTime() == null)
        {
//            lastCheckDateTime = EDITDateTime.DEFAULT_MIN_DATETIME;
              lastStatementDateTime = EDITDateTime.DEFAULT_MIN_DATETIME;
        }
        else
        {
//            EDITDateTime lastCheckDate = clientRoleFinancial.getLastCheckDateTime();
//            lastCheckDateTime = lastCheckDate.toString();
            EDITDateTime lastStatementDate = clientRoleFinancial.getLastStatementDateTime();
            lastStatementDateTime = lastStatementDate.toString();
        }

        Set<PlacedAgent> placedAgents = clientRole.getPlacedAgents();

        Event eventComponent = new EventComponent();

        List voInclusionList = new ArrayList();

        long[] placedAgentPKs = new long[placedAgents.size()];

        if (!placedAgents.isEmpty())
        {
            String[] statuses = new String[2];
            statuses[0] = "B";
            statuses[1] = "H";

            int i = 0;

            Iterator it = placedAgents.iterator();
            while (it.hasNext())
            {
                PlacedAgent placedAgent = (PlacedAgent) it.next();
                placedAgentPKs[i] = placedAgent.getPlacedAgentPK().longValue();
                i += 1;
            }

//            CommissionHistoryVO[] commissionHistoryVO = eventComponent.composeCommissionHistoryVOByUpdateDateTimeGTPlacedAgentPKAndStatus(lastCheckDateTime, placedAgentPKs, statuses, voInclusionList);
            CommissionHistoryVO[] commissionHistoryVO = eventComponent.composeCommissionHistoryVOByUpdateDateTimeGTPlacedAgentPKAndStatus(lastStatementDateTime, placedAgentPKs, statuses, voInclusionList);

            if (commissionHistoryVO != null)
            {
                commissionHistoryVOs.addAll(Arrays.asList(commissionHistoryVO));
            }
        }

        appReqBlock.getHttpServletRequest().setAttribute("commissionHistoryVOs", commissionHistoryVOs.toArray(new CommissionHistoryVO[commissionHistoryVOs.size()]));
        appReqBlock.getHttpServletRequest().setAttribute("selectedAgentNumber", selectedAgentNumber);
        appReqBlock.getHttpSession().setAttribute("selectedAgentNumber", selectedAgentNumber);
        
        return AGENT_FINANCIAL;
    }

    protected String showPreferences(AppReqBlock appReqBlock)
    {
        SessionBean preferenceSessionBean = new SessionBean();
        String selectedAgentNumber = appReqBlock.getFormBean().getValue("selectedAgentNumber");
        ClientRole clientRole = ClientRole.findBy_RoleType_ReferenceID(ClientRole.ROLETYPECT_AGENT, selectedAgentNumber)[0];
        ClientDetail clientDetail = clientRole.getClientDetail();
        Set<Preference> preferences = clientDetail.getPreferences();
        Iterator it = preferences.iterator();
        while (it.hasNext())
        {
            Preference preference = (Preference) it.next();

            PageBean preferencePageBean = new PageBean();

            preferencePageBean.putValue("clientDetailFK", preference.getClientDetailFK().toString());
            preferencePageBean.putValue("preferencePK", preference.getPreferencePK().toString());
            preferencePageBean.putValue("printAs", Util.initString(preference.getPrintAs(), ""));
            preferencePageBean.putValue("printAs2", Util.initString(preference.getPrintAs2(), ""));
            preferencePageBean.putValue("disbursementSource", Util.initString(preference.getDisbursementSourceCT(), ""));
            preferencePageBean.putValue("paymentMode", Util.initString(preference.getPaymentModeCT(), ""));
            preferencePageBean.putValue("minimumCheck", preference.getMinimumCheck().toString());
            preferencePageBean.putValue("bankAccountNumber", Util.initString(preference.getBankAccountNumber(), ""));
            preferencePageBean.putValue("bankRoutingNumber", Util.initString(preference.getBankRoutingNumber(), ""));
            preferencePageBean.putValue("bankAccountType", Util.initString(preference.getBankAccountTypeCT(), ""));
            preferencePageBean.putValue("bankName", Util.initString(preference.getBankName(), ""));
            preferencePageBean.putValue("bankAddressLine1", Util.initString(preference.getBankAddressLine1(), ""));
            preferencePageBean.putValue("bankAddressLine2", Util.initString(preference.getBankAddressLine2(), ""));
            preferencePageBean.putValue("bankCity", Util.initString(preference.getBankCity(), ""));
            preferencePageBean.putValue("bankState", Util.initString(preference.getBankStateCT(), ""));
            preferencePageBean.putValue("bankZipCode", Util.initString(preference.getBankZipCode(), ""));
            preferencePageBean.putValue("overrideStatus", Util.initString(preference.getOverrideStatus(), ""));
            preferencePageBean.putValue("preferenceType", Util.initString(preference.getPreferenceTypeCT(), ""));

            preferenceSessionBean.putPageBean(preference.getPreferencePK() + "", preferencePageBean);
        }

        appReqBlock.getHttpSession().setAttribute("preferenceSessionBean", preferenceSessionBean);
        appReqBlock.getHttpServletRequest().setAttribute("selectedAgentNumber", selectedAgentNumber);

        return PREFERENCES_DIALOG;
    }

    private String showSelectedPreference(AppReqBlock appReqBlock)
    {
        PageBean formBean = appReqBlock.getFormBean();
        String preferencePK = formBean.getValue("preferencePK");
        String selectedAgentNumber = formBean.getValue("selectedAgentNumber");

        PageBean pageBean = appReqBlock.getSessionBean("preferenceSessionBean").getPageBean(preferencePK);

        appReqBlock.getHttpServletRequest().setAttribute("pageBean", pageBean);
        appReqBlock.getHttpServletRequest().setAttribute("selectedAgentNumber", selectedAgentNumber);

        return PREFERENCES_DIALOG;
    }

    private String clearPreferenceForAdd(AppReqBlock appReqBlock)
    {
        PageBean formBean = appReqBlock.getFormBean();
        String selectedAgentNumber = formBean.getValue("selectedAgentNumber");

        appReqBlock.getHttpServletRequest().setAttribute("selectedAgentNumber", selectedAgentNumber);

        return PREFERENCES_DIALOG;
    }

    private String showContractAgentInfo(AppReqBlock appReqBlock)
    {
        String preferencePK = appReqBlock.getFormBean().getValue("preferencePK");
        String selectedAgentNumber = appReqBlock.getFormBean().getValue("selectedAgentNumber");
        appReqBlock.getHttpServletRequest().setAttribute("preferencePK", preferencePK);
        appReqBlock.getHttpServletRequest().setAttribute("selectedAgentNumber", selectedAgentNumber);

        return CONTRACT_AGENT_INFO_DIALOG;
    }

    private String savePreference(AppReqBlock appReqBlock)
    {
        PageBean formBean = appReqBlock.getFormBean();

        String selectedAgentNumber = formBean.getValue("selectedAgentNumber");
        appReqBlock.getHttpServletRequest().setAttribute("selectedAgentNumber", selectedAgentNumber);

        new ClientUseCaseComponent().updatePreference();

        boolean primaryPreferenceFound = false;

        String overrideStatus = formBean.getValue("overrideStatus");
        String preferenceType = Util.initString(formBean.getValue("preferenceType"), "");

        SessionBean preferenceSessionBean = appReqBlock.getSessionBean("preferenceSessionBean");
        Map preferencePageBeans = preferenceSessionBean.getPageBeans();
        Set preferenceKeys = preferencePageBeans.keySet();
        if (overrideStatus.equalsIgnoreCase("P"))
        {
            Iterator it = preferenceKeys.iterator();
            while (it.hasNext())
            {
                String pageBeanPK = (String) it.next();
                PageBean pageBean = (PageBean) preferencePageBeans.get(pageBeanPK);
                if (pageBean.getValue("overrideStatus").equalsIgnoreCase("P"))
                {
                    String preferenceTYPEPB = Util.initString(pageBean.getValue("preferenceType"), "");
                    if (preferenceTYPEPB.equalsIgnoreCase(preferenceType))
                    {
                        primaryPreferenceFound = true;
                        break;
                    }
                }
            }
        }

        if (primaryPreferenceFound)
        {
            appReqBlock.getHttpServletRequest().setAttribute("preferenceMessage", "Default Information Already Exists");
        }

        if (!primaryPreferenceFound)
        {
            try
            {
                ClientRole clientRole = ClientRole.findBy_RoleType_ReferenceID(ClientRole.ROLETYPECT_AGENT, selectedAgentNumber)[0];
                Preference preference = Preference.createNewPreference(formBean, clientRole);

                appReqBlock.getSessionBean("preferenceSessionBean").putPageBean(preference.getPreferencePK().toString(), formBean);
                appReqBlock.getHttpServletRequest().setAttribute("preferenceMessage", "Preference Added");
            }
            catch (Exception e)
            {
                System.out.println(e);

                e.printStackTrace();

                appReqBlock.getHttpServletRequest().setAttribute("preferenceMessage", "Preference Add Failed");
            }
        }

        return PREFERENCES_DIALOG;
    }

    protected String selectPreferenceForAgent(AppReqBlock appReqBlock)
    {
        PageBean formBean = appReqBlock.getFormBean();
        String selectedAgentNumber = formBean.getValue("selectedAgentNumber");
        String preferencePK = formBean.getValue("preferencePK");

        try
        {
            Preference preference = Preference.findByPK(new Long(preferencePK));
            ClientRole clientRole = ClientRole.findBy_RoleType_ReferenceID(ClientRole.ROLETYPECT_AGENT, selectedAgentNumber)[0];
            Preference.updateExistingPreference(preference, clientRole.getClientRolePK());
            appReqBlock.getHttpServletRequest().setAttribute("agentMessage", "Preference Updated");
        }
        catch(Exception e)
        {
            appReqBlock.getHttpServletRequest().setAttribute("agentMessage", "Preference Update Failed");
        }

        appReqBlock.getHttpSession().removeAttribute("preferenceSessionBean");
        appReqBlock.getHttpServletRequest().setAttribute("selectedAgentNumber", selectedAgentNumber);
        return AGENT_FINANCIAL;
    }

    protected String showAccumulationsDialog(AppReqBlock appReqBlock)
        throws Exception
    {
        String selectedAgentNumber = appReqBlock.getFormBean().getValue("selectedAgentNumber");
        appReqBlock.getHttpServletRequest().setAttribute("selectedAgentNumber", selectedAgentNumber);

        EDITDate currSystemDate = new EDITDate();
        EDITDate fromDate = null;
        EDITDate toDate = null;
        /*if (currSystemDate.getMonth() == 12 &&
            currSystemDate.getDay() < 16)
        {
            fromDate = new EDITDate(currSystemDate.getYear() - 1, 12, 16);
            toDate = new EDITDate(currSystemDate.getYear(), 12, 15);
        }
        else
        {
            fromDate = new EDITDate(currSystemDate.getYear(), 12, 16);
            toDate = new EDITDate(currSystemDate.getYear() + 1, 12, 15);
        }*/
         if (currSystemDate.getMonth() == 12 && currSystemDate.getDay() >= 16)
            {
                fromDate = new EDITDate(currSystemDate.getYear(), 12, 16);
                toDate = new EDITDate(currSystemDate.getYear() + 1, 12, 15);

            }
        else
            {
                fromDate = new EDITDate(currSystemDate.getYear() - 1, 12, 16);
                toDate = new EDITDate(currSystemDate.getYear(), 12, 15);
            }


        String currentPage = (String) appReqBlock.getHttpSession().getAttribute("currentPage");
        String previousPage = currentPage;

        savePreviousPageToVO(appReqBlock, previousPage);

        //appReqBlock.getHttpSession().setAttribute("currentPage", AGENT_ACCUMULATIONS_DIALOG);
        //appReqBlock.getHttpSession().setAttribute("previousPage", previousPage);

        List commissionHistoryVOs = new ArrayList();

        if (selectedAgentNumber != null)
        {
            ClientRole clientRole = ClientRole.findBy_RoleType_ReferenceID(ClientRole.ROLETYPECT_AGENT, selectedAgentNumber)[0];
            ClientRoleFinancial clientRoleFinancial = clientRole.getClientRoleFinancial();

            if (clientRoleFinancial != null)
            {
                EDITDateTime lastCheckDateTime = clientRoleFinancial.getLastCheckDateTime();

                if (lastCheckDateTime == null)
                {
                    lastCheckDateTime = new EDITDateTime(EDITDateTime.DEFAULT_MIN_DATETIME);
                }

                Set<PlacedAgent> placedAgents = clientRole.getPlacedAgents();

                Event eventComponent = new EventComponent();

                List voInclusionList = new ArrayList();

                long[] placedAgentPKs = new long[placedAgents.size()];

                if (!placedAgents.isEmpty())
                {
                    String[] statuses = new String[2];
                    statuses[0] = "B";
                    statuses[1] = "H";

                    Iterator it = placedAgents.iterator();
                    int j = 0;
                    while (it.hasNext())
                    {
                        PlacedAgent placedAgent = (PlacedAgent) it.next();
                        placedAgentPKs[j] = placedAgent.getPlacedAgentPK().longValue();
                        j += 1;
                    }

                    CommissionHistoryVO[] commissionHistoryVO = eventComponent.composeCommissionHistoryVOByUpdateDateTimeGTELTEPlacedAgentPK(placedAgentPKs, toDate.toString(), fromDate.toString(), voInclusionList);

                    if (commissionHistoryVO != null)
                    {
                        commissionHistoryVOs.addAll(Arrays.asList(commissionHistoryVO));
                    }
                }
            }
        }

        appReqBlock.getHttpServletRequest().setAttribute("commissionHistoryVOs", commissionHistoryVOs.toArray(new CommissionHistoryVO[commissionHistoryVOs.size()]));

        return AGENT_ACCUMULATIONS_DIALOG;
    }

    protected String showNY91PctRuleDialog(AppReqBlock appReqBlock)
        throws Exception
    {
        String currentPage = (String) appReqBlock.getHttpSession().getAttribute("currentPage");
        String previousPage = currentPage;

        savePreviousPageToVO(appReqBlock, previousPage);

        appReqBlock.getHttpSession().setAttribute("currentPage", AGENT_NY_91_PCT_RULE_DIALOG);
        appReqBlock.getHttpSession().setAttribute("previousPage", previousPage);

        return AGENT_NY_91_PCT_RULE_DIALOG;
    }

    protected String showDebitBalanceDialog(AppReqBlock appReqBlock)
        throws Exception
    {
        String currentPage = (String) appReqBlock.getHttpSession().getAttribute("currentPage");
        String previousPage = currentPage;

        savePreviousPageToVO(appReqBlock, previousPage);

        appReqBlock.getHttpSession().setAttribute("previousPage", previousPage);

        String selectedAgentNumber = appReqBlock.getFormBean().getValue("selectedAgentNumber");
        appReqBlock.getHttpServletRequest().setAttribute("selectedAgentNumber", selectedAgentNumber);

        return AGENT_DEBIT_BALANCE_DIALOG;
    }

    protected String saveDebitBalance(AppReqBlock appReqBlock)
        throws Exception
    {
        return AGENT_FINANCIAL;
    }

    protected String showLicense(AppReqBlock appReqBlock)
        throws Exception
    {
        String currentPage = (String) appReqBlock.getHttpSession().getAttribute("currentPage");
        String previousPage = currentPage;

        savePreviousPageToVO(appReqBlock, previousPage);

        appReqBlock.getHttpSession().setAttribute("currentPage", AGENT_LICENSE);
        appReqBlock.getHttpSession().setAttribute("previousPage", previousPage);

        return AGENT_LICENSE;
    }

    protected String showLicenseDetailSummary(AppReqBlock appReqBlock)
        throws Exception
    {
        String agentLicensePK = appReqBlock.getFormBean().getValue("agentLicensePK");
        appReqBlock.getHttpServletRequest().setAttribute("agentLicensePK", agentLicensePK);

        return AGENT_LICENSE;
    }

    protected String saveLicenseToSummary(AppReqBlock appReqBlock)
        throws Exception
    {
        CodeTableWrapper codeTableWrapper = CodeTableWrapper.getSingleton();

        PageBean formBean = appReqBlock.getFormBean();
        String agentLicensePK = formBean.getValue("agentLicensePK");
        String licenseNumber = formBean.getValue("licenseNumber");

        boolean licenseExisted = false;
        AgentVO agentVO = (AgentVO) appReqBlock.getHttpSession().getAttribute("agentVO");
        AgentLicenseVO[] licenseVOs = agentVO.getAgentLicenseVO();
        AgentLicenseVO agentLicenseVO = null;

        if (licenseVOs != null)
        {
            for (int l = 0; l < licenseVOs.length; l++)
            {
                String licensePK = licenseVOs[l].getAgentLicensePK() + "";

                if (licensePK.equals(agentLicensePK))
                {
                    agentLicenseVO = licenseVOs[l];
                    licenseExisted = true;
                }
            }
        }

        if (!licenseExisted)
        {
            agentLicenseVO = new AgentLicenseVO();
        }

        if (Util.isANumber(agentLicensePK))
        {
            agentLicenseVO.setAgentLicensePK(Long.parseLong(agentLicensePK));
        }
        else
        {
            agent.business.Agent agentComponent = new agent.component.AgentComponent();
            agentLicenseVO.setAgentLicensePK(agentComponent.getNextAvailableKey() * -1);
        }

        agentLicenseVO.setAgentFK(agentVO.getAgentPK());

        String nasd = formBean.getValue("nasd");
        agentLicenseVO.setNASD(Util.initString(nasd, null));

        String nasdEffMonth = formBean.getValue("nasdEffMonth");
        String nasdEffDay = formBean.getValue("nasdEffDay");
        String nasdEffYear = formBean.getValue("nasdEffYear");
        String nasdEffDate = DateTimeUtil.initDate(nasdEffMonth, nasdEffDay, nasdEffYear, null);

        agentLicenseVO.setNASDEffectiveDate(nasdEffDate);

        String nasdRenewalMonth = formBean.getValue("nasdRenewalMonth");
        String nasdRenewalDay = formBean.getValue("nasdRenewalDay");
        String nasdRenewalYear = formBean.getValue("nasdRenewalYear");
        String nasdRenewalDate = DateTimeUtil.initDate(nasdRenewalMonth, nasdRenewalDay, nasdRenewalYear, null);

        agentLicenseVO.setNASDRenewalDate(nasdRenewalDate);

        String state = formBean.getValue("state");

        if (Util.isANumber(state))
        {
            state = codeTableWrapper.getCodeTableEntry(Long.parseLong(state)).getCode();
        }
        else
        {
            state = "";
        }

        agentLicenseVO.setStateCT(Util.initString(state, null));

        String resident = formBean.getValue("residentStatus");

        if (Util.isANumber(resident))
        {
            resident = codeTableWrapper.getCodeTableEntry(Long.parseLong(resident)).getCode();
        }
        else
        {
            resident = "";
        }

        agentLicenseVO.setResidentCT(Util.initString(resident, null));

        String nasdStatus = formBean.getValue("nasdStatus");

        if (nasdStatus.equalsIgnoreCase("checked"))
        {
            nasdStatus = "Y";
        }
        else
        {
            nasdStatus = "N";
        }

        agentLicenseVO.setStateNASD(nasdStatus);

        String productType = formBean.getValue("productType");

        if (Util.isANumber(productType))
        {
            productType = codeTableWrapper.getCodeTableEntry(Long.parseLong(productType)).getCode();
        }
        else
        {
            productType = "";
        }

        agentLicenseVO.setProductTypeCT(Util.initString(productType, null));
        agentLicenseVO.setLicenseNumber(Util.initString(licenseNumber, null));

        String licenseType = formBean.getValue("licenseType");

        if (Util.isANumber(licenseType))
        {
            licenseType = codeTableWrapper.getCodeTableEntry(Long.parseLong(licenseType)).getCode();
        }
        else
        {
            licenseType = "";
        }

        agentLicenseVO.setLicenseTypeCT(licenseType);

        String renewTermStatus = formBean.getValue("renewTermStatus");

        if (Util.isANumber(renewTermStatus))
        {
            renewTermStatus = codeTableWrapper.getCodeTableEntry(Long.parseLong(renewTermStatus)).getCode();
        }
        else
        {
            renewTermStatus = "";
        }

        agentLicenseVO.setRenewTermStatusCT(Util.initString(renewTermStatus, null));

        String licenseStatus = formBean.getValue("status");

        if (Util.isANumber(licenseStatus))
        {
            licenseStatus = codeTableWrapper.getCodeTableEntry(Long.parseLong(licenseStatus)).getCode();
        }
        else
        {
            licenseStatus = "";
        }

        agentLicenseVO.setStatusCT(Util.initString(licenseStatus, null));

        String licEffMonth = formBean.getValue("licEffMonth");
        String licEffDay = formBean.getValue("licEffDay");
        String licEffYear = formBean.getValue("licEffYear");
        String licEffDate = DateTimeUtil.initDate(licEffMonth, licEffDay, licEffYear, null);

        agentLicenseVO.setLicEffDate(licEffDate);

        String licExpMonth = formBean.getValue("licExpMonth");
        String licExpDay = formBean.getValue("licExpDay");
        String licExpYear = formBean.getValue("licExpYear");
        String licExpDate = DateTimeUtil.initDate(licExpMonth, licExpDay, licExpYear, EDITDate.DEFAULT_MAX_DATE);

        agentLicenseVO.setLicExpDate(licExpDate);

        String licTermMonth = formBean.getValue("licTermMonth");
        String licTermDay = formBean.getValue("licTermDay");
        String licTermYear = formBean.getValue("licTermYear");
        String licTermDate = DateTimeUtil.initDate(licTermMonth, licTermDay, licTermYear, EDITDate.DEFAULT_MAX_DATE);

        agentLicenseVO.setLicTermDate(licTermDate);

        String eoStatus = formBean.getValue("eoStatus");

        if (eoStatus.equalsIgnoreCase("checked"))
        {
            eoStatus = "Y";
        }
        else
        {
            eoStatus = "N";
        }

        agentLicenseVO.setErrorOmmissStatus(eoStatus);

        String eoExpMonth = formBean.getValue("eoExpMonth");
        String eoExpDay = formBean.getValue("eoExpDay");
        String eoExpYear = formBean.getValue("eoExpYear");
        String eoExpDate = DateTimeUtil.initDate(eoExpMonth, eoExpDay, eoExpYear, EDITDate.DEFAULT_MAX_DATE);

        agentLicenseVO.setErrorOmmissExpDate(eoExpDate);

        if (!licenseExisted)
        {
            agentVO.addAgentLicenseVO(agentLicenseVO);
            appReqBlock.getHttpSession().setAttribute("agentVO", agentVO);
        }

        return AGENT_LICENSE;
    }

    protected String deleteLicense(AppReqBlock appReqBlock)
        throws Exception
    {
        PageBean formBean = appReqBlock.getFormBean();
        String agentLicensePK = formBean.getValue("agentLicensePK");

        AgentVO agentVO = (AgentVO) appReqBlock.getHttpSession().getAttribute("agentVO");
        AgentLicenseVO[] licenseVOs = agentVO.getAgentLicenseVO();

        for (int l = 0; l < licenseVOs.length; l++)
        {
            if ((licenseVOs[l].getAgentLicensePK() + "").equals(agentLicensePK))
            {
                licenseVOs[l].setVoShouldBeDeleted(true);
            }
        }

        return AGENT_LICENSE;
    }

    protected String deleteContract(AppReqBlock appReqBlock)
        throws Exception
    {
        PageBean formBean = appReqBlock.getFormBean();
        String agentContractPK = formBean.getValue("agentContractPK");

        //        String commissionContractCT = formBean.getValue("commissionContractCT");
        String contractMessage = "";

        if (Util.isANumber(agentContractPK) && !agentContractPK.equals("0"))
        {
            agent.business.Agent agentComponent = new agent.component.AgentComponent();

            PlacedAgentVO[] placedAgentVOs = agentComponent.composePlacedAgentVOByAgentContractFK(Long.parseLong(agentContractPK), new ArrayList());

            if ((placedAgentVOs != null) && (placedAgentVOs.length > 0))
            {
                contractMessage = "Contract Has Been Placed - Cannot Be Deleted";
            }
        }

        if (contractMessage.equals(""))
        {
            AgentVO agentVO = (AgentVO) appReqBlock.getHttpSession().getAttribute("agentVO");
            AgentContractVO[] agentContractVOs = agentVO.getAgentContractVO();

            for (int l = 0; l < agentContractVOs.length; l++)
            {
                if ((agentContractVOs[l].getAgentContractPK() + "").equals(agentContractPK))
                //                        (agentContractVOs[l].getContractCodeCT() + "").equals(commissionContractCT))
                {
                    int additionalCompensationCount = agentContractVOs[l].getAdditionalCompensationVOCount();

                    if (additionalCompensationCount > 0)
                    {
                        contractMessage = "Contract Has Additional Compensation Information - Cannot Be Deleted";
                    }
                    else
                    {
                        agentContractVOs[l].setVoShouldBeDeleted(true);
                    }
                }
            }
        }

        if (!contractMessage.equals(""))
        {
            appReqBlock.getHttpServletRequest().setAttribute("contractMessage", contractMessage);
        }

        return AGENT_CONTRACT;
    }

    protected String showContract(AppReqBlock appReqBlock)
        throws Exception
    {
        String currentPage = (String) appReqBlock.getHttpSession().getAttribute("currentPage");
        String previousPage = currentPage;

        savePreviousPageToVO(appReqBlock, previousPage);

        appReqBlock.getHttpSession().setAttribute("currentPage", AGENT_CONTRACT);
        appReqBlock.getHttpSession().setAttribute("previousPage", previousPage);

        return AGENT_CONTRACT;
    }

    protected String showContractDetailSummary(AppReqBlock appReqBlock)
        throws Exception
    {
        String agentContractPK = appReqBlock.getFormBean().getValue("agentContractPK");
        String selectedContractCodeCT = appReqBlock.getFormBean().getValue("selectedContractCodeCT");
        appReqBlock.getHttpServletRequest().setAttribute("agentContractPK", agentContractPK);
        appReqBlock.getHttpServletRequest().setAttribute("contractCodeCT", selectedContractCodeCT);

        return AGENT_CONTRACT;
    }

    protected String selectCommissionContractForAgent(AppReqBlock appReqBlock)
        throws Exception
    {
        String agentContractPK = appReqBlock.getFormBean().getValue("agentContractPK");
        String contractCodeCT = appReqBlock.getFormBean().getValue("contractCodeCT");
        appReqBlock.getHttpServletRequest().setAttribute("agentContractPK", agentContractPK);
        appReqBlock.getHttpServletRequest().setAttribute("contractCodeCT", contractCodeCT);

        return AGENT_CONTRACT;
    }

    protected String saveContractToSummary(AppReqBlock appReqBlock)
        throws Exception
    {
        CodeTableWrapper codeTableWrapper = CodeTableWrapper.getSingleton();

        PageBean formBean = appReqBlock.getFormBean();
        String agentContractPK = formBean.getValue("agentContractPK");
        String contractCodeCT = formBean.getValue("contractCodeCT");

        boolean contractExisted = false;
        AgentVO agentVO = (AgentVO) appReqBlock.getHttpSession().getAttribute("agentVO");
        AgentContractVO[] agentContractVOs = agentVO.getAgentContractVO();
        AgentContractVO agentContractVO = null;
        AdditionalCompensationVO addtnlCompVO = null;

        if (agentContractVOs != null)
        {
            for (int c = 0; c < agentContractVOs.length; c++)
            {
                String pk = agentContractVOs[c].getAgentContractPK() + "";
                String currentContractCodeCT = agentContractVOs[c].getContractCodeCT();

                if (agentContractPK.equals("0") && (!pk.equals("0") && currentContractCodeCT.equals(contractCodeCT)))
                {
                    appReqBlock.getHttpServletRequest().setAttribute("agentContractError", "Contract Code Already Exists");
                }

                if (pk.equals("0"))
                {
                    if (currentContractCodeCT.equals(contractCodeCT))
                    {
                        agentContractVO = agentContractVOs[c];
                        contractExisted = true;
                    }
                }
                else if (pk.equals(agentContractPK))
                {
                    agentContractVO = agentContractVOs[c];
                    contractExisted = true;
                }
            }
        }

        if (!contractExisted)
        {
            agentContractVO = new AgentContractVO();
        }

        if (Util.isANumber(agentContractPK) &&
            Long.parseLong(agentContractPK) != 0)
        {
            agentContractVO.setAgentContractPK(Long.parseLong(agentContractPK));
        }
        else
        {
            agent.business.Agent agentComponent = new AgentComponent();
            agentContractVO.setAgentContractPK(agentComponent.getNextAvailableKey() * -1);
        }

        agentContractVO.setAgentFK(agentVO.getAgentPK());

        String commissionProcess = formBean.getValue("commissionProcess");

        if (Util.isANumber(commissionProcess))
        {
            commissionProcess = codeTableWrapper.getCodeTableEntry(Long.parseLong(commissionProcess)).getCode();
        }
        else
        {
            commissionProcess = "";
        }

        agentContractVO.setCommissionProcessCT(commissionProcess);

        String effMonth = formBean.getValue("effMonth");
        String effDay = formBean.getValue("effDay");
        String effYear = formBean.getValue("effYear");
        String effDate = DateTimeUtil.initDate(effMonth, effDay, effYear, null);

        agentContractVO.setContractEffectiveDate(effDate);

        String stopMonth = formBean.getValue("stopMonth");
        String stopDay = formBean.getValue("stopDay");
        String stopYear = formBean.getValue("stopYear");
        String stopDate = DateTimeUtil.initDate(stopMonth, stopDay, stopYear, EDITDate.DEFAULT_MAX_DATE);

        agentContractVO.setContractStopDate(stopDate);
        agentContractVO.setContractCodeCT(contractCodeCT);

        String adaType = formBean.getValue("adaType");

        if (!adaType.equals(""))
        {
            boolean additionalCompExisted = false;

            if (agentContractVO.getAdditionalCompensationVOCount() > 0)
            {
                addtnlCompVO = agentContractVO.getAdditionalCompensationVO(0);
                additionalCompExisted = true;
            }
            else
            {
                addtnlCompVO = new AdditionalCompensationVO();
            }

            String additionalCompPK = formBean.getValue("additionalCompensationPK");

            if (Util.isANumber(additionalCompPK))
            {
                addtnlCompVO.setAdditionalCompensationPK(Long.parseLong(additionalCompPK));
            }
            else
            {
                addtnlCompVO.setAdditionalCompensationPK(0);
            }

            addtnlCompVO.setAgentContractFK(agentContractVO.getAgentContractPK());
            addtnlCompVO.setADATypeCT(adaType);

            String annualizedMax = formBean.getValue("annualizedMax");

            if (Util.isANumber(annualizedMax))
            {
                //addtnlCompVO.setAnnualizedMax(Double.parseDouble(annualizedMax));
                addtnlCompVO.setAnnualizedMax(new EDITBigDecimal(annualizedMax).getBigDecimal());
            }
            else
            {
                addtnlCompVO.setAnnualizedMax(new EDITBigDecimal().getBigDecimal());
            }

            String serviceFeeStatus = formBean.getValue("serviceFeeStatus");

            if (serviceFeeStatus.equalsIgnoreCase("checked"))
            {
                serviceFeeStatus = "Y";
            }
            else
            {
                serviceFeeStatus = "N";
            }

            addtnlCompVO.setExpenseAllowanceStatus(serviceFeeStatus);

            String bonusCommissionStatus = formBean.getValue("bonusCommissionStatus");

            if (bonusCommissionStatus.equalsIgnoreCase("checked"))
            {
                bonusCommissionStatus = "Y";
            }
            else
            {
                bonusCommissionStatus = "N";
            }

            addtnlCompVO.setBonusCommissionStatus(bonusCommissionStatus);

            String ny91PctStatus = formBean.getValue("ny91PctStatus");

            if (ny91PctStatus.equalsIgnoreCase("checked"))
            {
                ny91PctStatus = "Y";
            }
            else
            {
                ny91PctStatus = "N";
            }

            addtnlCompVO.setNY91PercentStatus(ny91PctStatus);

            if (!additionalCompExisted)
            {
                agentContractVO.addAdditionalCompensationVO(addtnlCompVO);
            }
        }

        if (!contractExisted)
        {
            agentVO.addAgentContractVO(agentContractVO);
        }

        return AGENT_CONTRACT;
    }

    protected String showAdditionalCompensationDialog(AppReqBlock appReqBlock)
        throws Exception
    {
        CodeTableWrapper codeTableWrapper = CodeTableWrapper.getSingleton();

        PageBean formBean = appReqBlock.getFormBean();

        String adaType = formBean.getValue("adaType");
        String annualizedMax = formBean.getValue("annualizedMax");
        String serviceFeeStatus = formBean.getValue("serviceFeeStatus");
        String bonusCommissionStatus = formBean.getValue("bonusCommissionStatus");
        String ny91PctStatus = formBean.getValue("ny91PctStatus");
        String additionalCompPK = formBean.getValue("additionalCompensationPK");
        String agentContractPK = formBean.getValue("agentContractPK");
        String commissionContractFK = formBean.getValue("commissionContractFK");
        String effMonth = formBean.getValue("effMonth");
        String effDay = formBean.getValue("effDay");
        String effYear = formBean.getValue("effYear");
        String stopMonth = formBean.getValue("stopMonth");
        String stopDay = formBean.getValue("stopDay");
        String stopYear = formBean.getValue("stopYear");
        String commissionProcess = formBean.getValue("commissionProcess");

        if (Util.isANumber(commissionProcess))
        {
            commissionProcess = codeTableWrapper.getCodeTableEntry(Long.parseLong(commissionProcess)).getCode();
        }

        appReqBlock.getHttpServletRequest().setAttribute("adaType", adaType);
        appReqBlock.getHttpServletRequest().setAttribute("annualizedMax", annualizedMax);
        appReqBlock.getHttpServletRequest().setAttribute("serviceFeeStatus", serviceFeeStatus);
        appReqBlock.getHttpServletRequest().setAttribute("bonusCommissionStatus", bonusCommissionStatus);
        appReqBlock.getHttpServletRequest().setAttribute("ny91PctStatus", ny91PctStatus);
        appReqBlock.getHttpServletRequest().setAttribute("additionalCompensationPK", additionalCompPK);
        appReqBlock.getHttpServletRequest().setAttribute("agentContractPK", agentContractPK);
        appReqBlock.getHttpServletRequest().setAttribute("commissionContractFK", commissionContractFK);
        appReqBlock.getHttpServletRequest().setAttribute("effMonth", effMonth);
        appReqBlock.getHttpServletRequest().setAttribute("effDay", effDay);
        appReqBlock.getHttpServletRequest().setAttribute("effYear", effYear);
        appReqBlock.getHttpServletRequest().setAttribute("stopMonth", stopMonth);
        appReqBlock.getHttpServletRequest().setAttribute("stopDay", stopDay);
        appReqBlock.getHttpServletRequest().setAttribute("stopYear", stopYear);
        appReqBlock.getHttpServletRequest().setAttribute("commissionProcess", commissionProcess);

        String currentPage = (String) appReqBlock.getHttpSession().getAttribute("currentPage");
        String previousPage = currentPage;

        savePreviousPageToVO(appReqBlock, previousPage);

        appReqBlock.getHttpSession().setAttribute("currentPage", AGENT_ADDITIONAL_COMP_DIALOG);
        appReqBlock.getHttpSession().setAttribute("previousPage", previousPage);

        return AGENT_ADDITIONAL_COMP_DIALOG;
    }

    protected String saveAdditionalCompensation(AppReqBlock appReqBlock)
        throws Exception
    {
        CodeTableWrapper codeTableWrapper = CodeTableWrapper.getSingleton();

        PageBean formBean = appReqBlock.getFormBean();

        String adaType = formBean.getValue("adaType");

        if (Util.isANumber(adaType))
        {
            adaType = codeTableWrapper.getCodeTableEntry(Long.parseLong(adaType)).getCode();
        }

        String annualizedMax = formBean.getValue("annualizedMax");
        String serviceFeeStatus = formBean.getValue("serviceFeeStatus");
        String bonusCommissionStatus = formBean.getValue("bonusCommissionStatus");
        String ny91PctStatus = formBean.getValue("ny91PctStatus");
        String additionalCompPK = formBean.getValue("additionalCompensationPK");
        String agentContractPK = formBean.getValue("agentContractPK");
        String commissionContractFK = formBean.getValue("commissionContractFK");
        String effMonth = formBean.getValue("effMonth");
        String effDay = formBean.getValue("effDay");
        String effYear = formBean.getValue("effYear");
        String stopMonth = formBean.getValue("stopMonth");
        String stopDay = formBean.getValue("stopDay");
        String stopYear = formBean.getValue("stopYear");
        String commissionProcess = formBean.getValue("commissionProcess");

        if (Util.isANumber(commissionProcess))
        {
            commissionProcess = codeTableWrapper.getCodeTableEntry(Long.parseLong(commissionProcess)).getCode();
        }

        appReqBlock.getHttpServletRequest().setAttribute("adaType", adaType);
        appReqBlock.getHttpServletRequest().setAttribute("annualizedMax", annualizedMax);
        appReqBlock.getHttpServletRequest().setAttribute("serviceFeeStatus", serviceFeeStatus);
        appReqBlock.getHttpServletRequest().setAttribute("bonusCommissionStatus", bonusCommissionStatus);
        appReqBlock.getHttpServletRequest().setAttribute("ny91PctStatus", ny91PctStatus);
        appReqBlock.getHttpServletRequest().setAttribute("additionalCompensationPK", additionalCompPK);
        appReqBlock.getHttpServletRequest().setAttribute("agentContractPK", agentContractPK);
        appReqBlock.getHttpServletRequest().setAttribute("commissionContractFK", commissionContractFK);
        appReqBlock.getHttpServletRequest().setAttribute("effMonth", effMonth);
        appReqBlock.getHttpServletRequest().setAttribute("effDay", effDay);
        appReqBlock.getHttpServletRequest().setAttribute("effYear", effYear);
        appReqBlock.getHttpServletRequest().setAttribute("stopMonth", stopMonth);
        appReqBlock.getHttpServletRequest().setAttribute("stopDay", stopDay);
        appReqBlock.getHttpServletRequest().setAttribute("stopYear", stopYear);
        appReqBlock.getHttpServletRequest().setAttribute("commissionProcess", commissionProcess);

        return AGENT_CONTRACT;
    }

    protected String cancelAdditionalCompensation(AppReqBlock appReqBlock)
        throws Exception
    {
        CodeTableWrapper codeTableWrapper = CodeTableWrapper.getSingleton();

        PageBean formBean = appReqBlock.getFormBean();

        String agentContractPK = formBean.getValue("agentContractPK");
        String commissionContractFK = formBean.getValue("commissionContractFK");
        String effMonth = formBean.getValue("effMonth");
        String effDay = formBean.getValue("effDay");
        String effYear = formBean.getValue("effYear");
        String stopMonth = formBean.getValue("stopMonth");
        String stopDay = formBean.getValue("stopDay");
        String stopYear = formBean.getValue("stopYear");
        String commissionProcess = formBean.getValue("commissionProcess");

        if (Util.isANumber(commissionProcess))
        {
            commissionProcess = codeTableWrapper.getCodeTableEntry(Long.parseLong(commissionProcess)).getCode();
        }

        appReqBlock.getHttpServletRequest().setAttribute("agentContractPK", agentContractPK);
        appReqBlock.getHttpServletRequest().setAttribute("commissionContractFK", commissionContractFK);
        appReqBlock.getHttpServletRequest().setAttribute("effMonth", effMonth);
        appReqBlock.getHttpServletRequest().setAttribute("effDay", effDay);
        appReqBlock.getHttpServletRequest().setAttribute("effYear", effYear);
        appReqBlock.getHttpServletRequest().setAttribute("stopMonth", stopMonth);
        appReqBlock.getHttpServletRequest().setAttribute("stopDay", stopDay);
        appReqBlock.getHttpServletRequest().setAttribute("stopYear", stopYear);
        appReqBlock.getHttpServletRequest().setAttribute("commissionProcess", commissionProcess);

        return AGENT_CONTRACT;
    }

    protected String showAdjustments(AppReqBlock appReqBlock)
        throws Exception
    {
        String currentPage = (String) appReqBlock.getHttpSession().getAttribute("currentPage");
        String previousPage = currentPage;

        savePreviousPageToVO(appReqBlock, previousPage);

        //check for active DebitPay rebalance
        AgentVO agentVO = (AgentVO)appReqBlock.getHttpSession().getAttribute("agentVO");
        CheckAdjustmentVO[] checkAdjustmentVOs = agentVO.getCheckAdjustmentVO();

        String activeDebitBalanceRepay = "false";
        EDITDate stopDate = new EDITDate(EDITDate.DEFAULT_MIN_DATE);
        if (checkAdjustmentVOs != null)
        {
            for (int i = 0; i < checkAdjustmentVOs.length; i++)
            {
                if (checkAdjustmentVOs[i].getAdjustmentTypeCT().equalsIgnoreCase("DebitBalAutoRepay"))
                {

                    String adjustmentCompleteInd = checkAdjustmentVOs[i].getAdjustmentCompleteInd();
                    if (adjustmentCompleteInd == null || !adjustmentCompleteInd.equalsIgnoreCase("Y"))
                    {
                        activeDebitBalanceRepay = "true";
                        break;
                    }
                }
            }
        }

        appReqBlock.getHttpServletRequest().setAttribute("activeInd", activeDebitBalanceRepay);
        appReqBlock.getHttpSession().setAttribute("currentPage", AGENT_ADJUSTMENTS);
        appReqBlock.getHttpSession().setAttribute("previousPage", previousPage);

        return AGENT_ADJUSTMENTS;
    }

    protected String showAdjustmentDetailSummary(AppReqBlock appReqBlock)
        throws Exception
    {
        String checkAdjustmentPK = appReqBlock.getFormBean().getValue("checkAdjustmentPK");
        String activeInd = Util.initString(appReqBlock.getFormBean().getValue("activeInd"), null);
        
        appReqBlock.getHttpServletRequest().setAttribute("checkAdjustmentPK", checkAdjustmentPK);
        appReqBlock.getHttpServletRequest().setAttribute("activeInd", activeInd);

        return AGENT_ADJUSTMENTS;
    }

    protected String saveAdjustmentToSummary(AppReqBlock appReqBlock)
        throws Exception
    {
        PageBean formBean = appReqBlock.getFormBean();

        AgentVO agentVO = (AgentVO)appReqBlock.getHttpSession().getAttribute("agentVO");

        String checkAdjustmentPK = Util.initString(formBean.getValue("checkAdjustmentPK"), "0");
        
        String agentNumber = Util.initString(formBean.getValue("agentNumber"), null);
        String adjustmentType = Util.initString(formBean.getValue("adjustmentType"), null);
        String adjStatus = Util.initString(formBean.getValue("adjustmentStatus"), null);
        String mode = Util.initString(formBean.getValue("adjustmentMode"), null);

        String description = Util.initString(formBean.getValue("description"), null);
        EDITBigDecimal adjAmt = new EDITBigDecimal(Util.initString(formBean.getValue("adjustmentAmount"), "0"));
        EDITBigDecimal adjustmentPercent = new EDITBigDecimal(Util.initString(formBean.getValue("adjustmentPercent"), "0"));

        String taxableStatus = Util.initString(formBean.getValue("taxableStatus"),  null);

        String adjStartDate = Util.initString(formBean.getValue("adjStartDate"), null);
        adjStartDate = DateTimeUtil.formatMMDDYYYYToYYYYMMDD(adjStartDate);
        String adjStopDate = Util.initString(formBean.getValue("adjStopDate"), null);
        if (adjStopDate == null)
        {
            adjStopDate = EDITDate.DEFAULT_MAX_DATE;
        }
        else
        {
            adjStopDate = DateTimeUtil.formatMMDDYYYYToYYYYMMDD(adjStopDate);
        }

        String nextDueDate = Util.initString(formBean.getValue("nextDueDate"), null);
        if (nextDueDate == null)
        {
            nextDueDate = adjStartDate;
        }
        else
        {
            nextDueDate = DateTimeUtil.formatMMDDYYYYToYYYYMMDD(nextDueDate);
        }
        
        if (adjStatus.equals(CheckAdjustment.ONETIME_ADJUSTMENT_STATUS))
        {
            if (adjStartDate == null)
            {
                adjStartDate = nextDueDate;
            }
        }
        
        // Client Role entry is unique when searching by Agent Number (ReferenceID)
        ClientRole clientRole = ClientRole.findBy_ReferenceID(agentNumber)[0];
        
        PlacedAgent[] placedAgents = clientRole.getPlacedAgents().toArray(new PlacedAgent[clientRole.getPlacedAgents().size()]);
        
        // To make sure that everytime we assign the same PlacedAgentFK to CheckAdjustment
        placedAgents = (PlacedAgent[])  Util.sortObjects(placedAgents, new String[] {"getPlacedAgentPK"});

        CheckAdjustmentVO[] checkAdjustmentVOs = agentVO.getCheckAdjustmentVO();
        CheckAdjustmentVO checkAdjustmentVO = null;
        boolean adjustmentExisted = false;

         if (checkAdjustmentVOs != null)
         {
             for (int i = 0; i < checkAdjustmentVOs.length; i++)
             {
                 String checkAdjustmentKey = checkAdjustmentVOs[i].getCheckAdjustmentPK() + "";

                 if (checkAdjustmentKey.equals(checkAdjustmentPK))
                 {
                     checkAdjustmentVO = checkAdjustmentVOs[i];
                     adjustmentExisted = true;
                 }
             }
         }

         if (!adjustmentExisted)
         {
             checkAdjustmentVO = new CheckAdjustmentVO();
         }

        if (!checkAdjustmentPK.equals("0"))
        {
            adjustmentExisted = true;
        }

        if (!adjustmentExisted)
        {
            agent.business.Agent agentComponent = new agent.component.AgentComponent();
            checkAdjustmentVO.setCheckAdjustmentPK(agentComponent.getNextAvailableKey() * -1);
        }
        else
        {
            checkAdjustmentVO.setCheckAdjustmentPK(Long.parseLong(checkAdjustmentPK));
        }

        checkAdjustmentVO.setAgentFK(agentVO.getAgentPK());
        checkAdjustmentVO.setAdjustmentTypeCT(adjustmentType);
        checkAdjustmentVO.setAdjustmentDollar(adjAmt.getBigDecimal());
        checkAdjustmentVO.setAdjustmentPercent(adjustmentPercent.getBigDecimal());
        checkAdjustmentVO.setTaxableStatusCT(taxableStatus);
        checkAdjustmentVO.setStartDate(adjStartDate);
        checkAdjustmentVO.setStopDate(adjStopDate);
        checkAdjustmentVO.setAdjustmentStatusCT(adjStatus);
        checkAdjustmentVO.setModeCT(mode);
        checkAdjustmentVO.setNextDueDate(nextDueDate);

        checkAdjustmentVO.setDescription(description);
        
        // first one found
        checkAdjustmentVO.setPlacedAgentFK(placedAgents[0].getPlacedAgentPK());

        if (!adjustmentExisted)
        {
            agentVO.addCheckAdjustmentVO(checkAdjustmentVO);
        }

        appReqBlock.getHttpServletRequest().setAttribute("activeInd", appReqBlock.getFormBean().getValue("activeInd"));
        
        return AGENT_ADJUSTMENTS;
    }

    protected String deleteAdjustment(AppReqBlock appReqBlock)
        throws Exception
    {
        PageBean formBean = appReqBlock.getFormBean();
        String checkAdjustmentPK = Util.initString(formBean.getValue("checkAdjustmentPK"), "0");

        AgentVO agentVO = (AgentVO) appReqBlock.getHttpSession().getAttribute("agentVO");
        CheckAdjustmentVO[] checkAdjustmentVOs = agentVO.getCheckAdjustmentVO();

        for (int l = 0; l < checkAdjustmentVOs.length; l++)
        {
            if ((checkAdjustmentVOs[l].getCheckAdjustmentPK() + "").equals(checkAdjustmentPK))
            {
                checkAdjustmentVOs[l].setVoShouldBeDeleted(true);
            }
        }

        return AGENT_ADJUSTMENTS;
    }


    protected String showAdjustAccumsDialog(AppReqBlock appReqBlock)
        throws Exception
    {
        String currentPage = (String) appReqBlock.getHttpSession().getAttribute("currentPage");
        String previousPage = currentPage;

        savePreviousPageToVO(appReqBlock, previousPage);

        appReqBlock.getHttpSession().setAttribute("currentPage", AGENT_ADJUST_ACCUMS_DIALOG);
        appReqBlock.getHttpSession().setAttribute("previousPage", previousPage);

        return AGENT_ADJUST_ACCUMS_DIALOG;
    }

    protected String showAnnualizedDialog(AppReqBlock appReqBlock)
        throws Exception
    {
        String currentPage = (String) appReqBlock.getHttpSession().getAttribute("currentPage");
        String previousPage = currentPage;

        savePreviousPageToVO(appReqBlock, previousPage);

        appReqBlock.getHttpSession().setAttribute("currentPage", ANNUALIZED_DIALOG);
        appReqBlock.getHttpSession().setAttribute("previousPage", previousPage);

        return ANNUALIZED_DIALOG;
    }

    protected String showRedirect(AppReqBlock appReqBlock)
        throws Exception
    {
        String currentPage = (String) appReqBlock.getHttpSession().getAttribute("currentPage");
        String previousPage = currentPage;

        savePreviousPageToVO(appReqBlock, previousPage);

        appReqBlock.getHttpSession().setAttribute("currentPage", AGENT_REDIRECT);
        appReqBlock.getHttpSession().setAttribute("previousPage", previousPage);

        return AGENT_REDIRECT;
    }

    protected String showRedirectDetailSummary(AppReqBlock appReqBlock)
        throws Exception
    {
        String redirectPK = appReqBlock.getFormBean().getValue("redirectPK");
        appReqBlock.getHttpServletRequest().setAttribute("redirectPK", redirectPK);

        return AGENT_REDIRECT;
    }

    protected String clearFormForAddOrCancel(AppReqBlock appReqBlock)
        throws Exception
    {
        appReqBlock.getHttpServletRequest().setAttribute("activeInd", appReqBlock.getFormBean().getValue("activeInd"));

        return (String) appReqBlock.getHttpSession().getAttribute("currentPage");
    }

    protected String saveRedirectToSummary(AppReqBlock appReqBlock)
        throws Exception
    {
        PageBean formBean = appReqBlock.getFormBean();
        String redirectPK = formBean.getValue("redirectPK");
        String redirectType = formBean.getValue("redirectType");
        String clientRolePK = formBean.getValue("clientRolePK");
        String clientDetailFK = formBean.getValue("clientDetailFK");

        String startMonth = formBean.getValue("redirectStartMonth");
        String startDay = formBean.getValue("redirectStartDay");
        String startYear = formBean.getValue("redirectStartYear");
        String startDate = DateTimeUtil.initDate(startMonth, startDay, startYear, null);

        String stopMonth = formBean.getValue("redirectStopMonth");
        String stopDay = formBean.getValue("redirectStopDay");
        String stopYear = formBean.getValue("redirectStopYear");
        String stopDate = DateTimeUtil.initDate(stopMonth, stopDay, stopYear, EDITDate.DEFAULT_MAX_DATE);

        EDITDate edStartDate = new EDITDate(startDate);
        EDITDate edStopDate = new EDITDate(stopDate);

        EDITDate existingStartDate = null;
        EDITDate existingStopDate = null;
        boolean overlappingDates = false;

        AgentVO agentVO = (AgentVO) appReqBlock.getHttpSession().getAttribute("agentVO");
        RedirectVO[] redirectVOs = agentVO.getRedirectVO();

        boolean redirectExisted = false;
        RedirectVO redirectVO = null;

        if (redirectVOs != null)
        {
            for (int r = 0; r < redirectVOs.length; r++)
            {
                String pk = redirectVOs[r].getRedirectPK() + "";

                if (pk.equals(redirectPK))
                {
                    redirectVO = redirectVOs[r];
                    redirectExisted = true;
                }
            }
        }

        ClientDetail clientDetail = ClientDetail.findBy_ClientDetailPK(new Long(clientDetailFK));

        ClientRole clientRole = null;

        for (int i = 0; i < redirectVOs.length; i++)
        {
            existingStartDate = new EDITDate(redirectVOs[i].getStartDate());
            existingStopDate = new EDITDate(redirectVOs[i].getStopDate());

            if ((edStartDate.afterOREqual(existingStartDate) &&
                 edStopDate.beforeOREqual(existingStopDate)) ||
                (edStartDate.beforeOREqual(existingStartDate) &&
                 (edStopDate.beforeOREqual(existingStopDate) &&
                  edStopDate.afterOREqual(existingStartDate))) ||
                ((edStartDate.afterOREqual(existingStartDate) &&
                  edStartDate.beforeOREqual(existingStopDate)) &&
                 edStopDate.afterOREqual(existingStopDate)))
            {
                if ((redirectExisted && redirectVOs[i].getRedirectPK() == redirectVO.getRedirectPK()) ||
                     !redirectExisted)
                {
                    overlappingDates = true;
                    break;
                }
            }
        }

        if (overlappingDates)
        {
            appReqBlock.getHttpServletRequest().setAttribute("redirectType", redirectType);
            appReqBlock.getHttpServletRequest().setAttribute("clientDetail", clientDetail);
            appReqBlock.getHttpServletRequest().setAttribute("clientRole", clientRole);
            appReqBlock.getHttpServletRequest().setAttribute("startDate", startDate);
            appReqBlock.getHttpServletRequest().setAttribute("stopDate", stopDate);
            appReqBlock.getHttpServletRequest().setAttribute("redirectError", "Overlapping Redirect Periods - Cannot Save");
        }
        else
        {
            boolean errorGenerated = false;

            if (!clientRolePK.equals(""))
            {
                clientRole = ClientRole.findBy_ClientRolePK(new Long(clientRolePK));
            }
            else
            {
                try
                {
                    SessionHelper.beginTransaction(SessionHelper.EDITSOLUTIONS);
                    clientRole = new ClientRole();
                    clientRole.setClientDetail(clientDetail);
                    clientRole.setRoleTypeCT(ClientRole.ROLETYPECT_ASSIGNEE);

                    clientDetail.addClientRole(clientRole);

                    clientDetail.hSave();
                    SessionHelper.commitTransaction(SessionHelper.EDITSOLUTIONS);
                }
                catch (Exception e)
                {
                    errorGenerated = true;
                    SessionHelper.rollbackTransaction(SessionHelper.EDITSOLUTIONS);
                    appReqBlock.getHttpServletRequest().setAttribute("redirectError", "Error Generated in Assignee Role Creation");
                }
            }

            if (!errorGenerated)
            {
                ClientRoleVO clientRoleVO = (ClientRoleVO) clientRole.getVO();
                ClientDetailVO clientDetailVO = (ClientDetailVO) clientDetail.getVO();
                clientRoleVO.setParentVO(ClientDetailVO.class, clientDetailVO);

                if (!redirectExisted)
                {
                    redirectVO = new RedirectVO();
                }

                if (Util.isANumber(redirectPK))
                {
                    redirectVO.setRedirectPK(Long.parseLong(redirectPK));
                }
                else
                {
                    agent.business.Agent agentComponent = new agent.component.AgentComponent();
                    redirectVO.setRedirectPK(agentComponent.getNextAvailableKey() * -1);
                }

                redirectVO.setAgentFK(agentVO.getAgentPK());
                redirectVO.setRedirectTypeCT(redirectType);

                redirectVO.setStartDate(startDate);

                redirectVO.setStopDate(stopDate);
                redirectVO.setClientRoleFK(clientRole.getClientRolePK().longValue());

                redirectVO.setParentVO(ClientRoleVO.class, clientRoleVO);

                if (!redirectExisted)
                {
                    agentVO.addRedirectVO(redirectVO);
                }
            }
        }

        return AGENT_REDIRECT;
    }

    protected String deleteRedirect(AppReqBlock appReqBlock)
        throws Exception
    {
        PageBean formBean = appReqBlock.getFormBean();
        String redirectPK = formBean.getValue("redirectPK");

        AgentVO agentVO = (AgentVO) appReqBlock.getHttpSession().getAttribute("agentVO");
        RedirectVO[] redirectVOs = agentVO.getRedirectVO();

        for (int l = 0; l < redirectVOs.length; l++)
        {
            if ((redirectVOs[l].getRedirectPK() + "").equals(redirectPK))
            {
                redirectVOs[l].setVoShouldBeDeleted(true);
            }
        }

        return AGENT_REDIRECT;
    }

    protected String showRedirectAccumsDialog(AppReqBlock appReqBlock)
        throws Exception
    {
        return AGENT_REDIRECT_ACCUMS_DIALOG;
    }

    protected String showRedirectSearchDialog(AppReqBlock appReqBlock) throws Exception
    {
        return AGENT_REDIRECT_SEARCH_DIALOG;
    }

    protected String showRedirectAfterSearch(AppReqBlock appReqBlock) throws Exception
    {
        String redirectType = appReqBlock.getFormBean().getValue("redirectType");
        appReqBlock.getHttpServletRequest().setAttribute("redirectType", redirectType);

        String clientDetailPK = appReqBlock.getFormBean().getValue("clientDetailPK");
        String companyName = appReqBlock.getFormBean().getValue("companyName");

        Company company = null;

        if (!companyName.equals(""))
        {
            company = Company.findBy_CompanyName(companyName);
        }

        ClientDetail clientDetail = ClientDetail.findBy_ClientDetailPK(new Long(clientDetailPK));

        ClientRole clientRole = null;

        if (redirectType.equalsIgnoreCase(Redirect.REDIRECTYPE_REVERSION))
        {
            clientRole = ClientRole.findBy_ClientDetail_RoleTypeCT(clientDetail, ClientRole.ROLETYPECT_AGENT);
        }
        else
        {
            clientRole  = ClientRole.findBy_ClientDetail_RoleTypeCT(clientDetail, ClientRole.ROLETYPECT_ASSIGNEE);
        }

        appReqBlock.getHttpServletRequest().setAttribute("clientDetail", clientDetail);

        if (clientRole != null)
        {
            appReqBlock.getHttpServletRequest().setAttribute("clientRole", clientRole);
        }

        return AGENT_REDIRECT;
    }

    protected String showHistory(AppReqBlock appReqBlock)
        throws Exception
    {
        String currentPage = (String) appReqBlock.getHttpSession().getAttribute("currentPage");
        String previousPage = currentPage;

        savePreviousPageToVO(appReqBlock, previousPage);

        appReqBlock.getHttpSession().setAttribute("currentPage", COMMISSION_HISTORY_DETAIL);
        appReqBlock.getHttpSession().setAttribute("previousPage", previousPage);

        return COMMISSION_HISTORY_DETAIL;
    }

    protected String showHistoryDetailSummary(AppReqBlock appReqBlock)
        throws Exception
    {
        String selectedCommissionHistoryPK = appReqBlock.getFormBean().getValue("selectedCommissionHistoryPK");
        boolean agentChangeHistoryFound = false;

        CommissionHistoryVO[] commissionHistories = (CommissionHistoryVO[]) appReqBlock.getHttpSession().getAttribute("commissionHistoryVOs");

        for (int i = 0; i < commissionHistories.length; i++)
        {
            if ((commissionHistories[i].getCommissionHistoryPK() + "").equals(selectedCommissionHistoryPK))
            {
                EDITTrxVO editTrxVO = (EDITTrxVO) commissionHistories[i].getParentVO(EDITTrxHistoryVO.class).getParentVO(EDITTrxVO.class);

                if (editTrxVO.getTransactionTypeCT().equalsIgnoreCase("NF"))
                {
                    agentChangeHistoryFound = true;

                    break;
                }
            }
        }

        appReqBlock.getHttpServletRequest().setAttribute("selectedCommissionHistoryPK", selectedCommissionHistoryPK);

        if (agentChangeHistoryFound)
        {
            return AGENT_CHANGE_HISTORY_DETAIL;
        }
        else
        {
            return COMMISSION_HISTORY_DETAIL;
        }
    }

    protected String showCommHistoryAdjustmentDialog(AppReqBlock appReqBlock)
        throws Exception
    {
        String selectedCommissionHistoryPK = appReqBlock.getReqParm("selectedCommissionHistoryPK");
        appReqBlock.getHttpServletRequest().setAttribute("selectedCommissionHistoryPK", selectedCommissionHistoryPK);

        //  Check for authorization
        new EventUseCaseComponent().adjustCommissionHistory();

        CommissionHistoryVO[] commissionHistoryVOs = (CommissionHistoryVO[]) appReqBlock.getHttpSession().getAttribute("commissionHistoryVOs");

        if (commissionHistoryVOs != null)
        {
            for (int i = 0; i < commissionHistoryVOs.length; i++)
            {
                if ((commissionHistoryVOs[i].getCommissionHistoryPK() + "").equals(selectedCommissionHistoryPK))
                {
                    appReqBlock.getHttpServletRequest().setAttribute("statementInd", commissionHistoryVOs[i].getStatementInd());

                    break;
                }
            }
        }

        return COMMISSION_HISTORY_ADJUSTMENT_DIALOG;
    }

    protected String showCommHistoryFilterDialog(AppReqBlock appReqBlock)
        throws Exception
    {
        return COMMISSION_HISTORY_FILTER_DIALOG;
    }

    protected String filterCommissionHistory(AppReqBlock appReqBlock)
        throws Exception
    {
        appReqBlock.getHttpSession().removeAttribute("commissionHistoryVOs");

        CodeTableWrapper codeTableWrapper = CodeTableWrapper.getSingleton();

        String fromMonth = appReqBlock.getReqParm("fromMonth");
        String fromDay = appReqBlock.getReqParm("fromDay");
        String fromYear = appReqBlock.getReqParm("fromYear");
        EDITDate fromDate = DateTimeUtil.initDate(fromMonth, fromDay, fromYear);

        String toMonth = appReqBlock.getReqParm("toMonth");
        String toDay = appReqBlock.getReqParm("toDay");
        String toYear = appReqBlock.getReqParm("toYear");
        EDITDate toDate = DateTimeUtil.initDate(toMonth, toDay, toYear);

        String filterPeriod = appReqBlock.getReqParm("filterPeriod");

        if (Util.isANumber(filterPeriod) && !filterPeriod.equals("0"))
        {
            filterPeriod = codeTableWrapper.getCodeTableEntry(Long.parseLong(filterPeriod)).getCode();

            EDITDate currentDate = new EDITDate();

            toDate = currentDate;

            if (filterPeriod.equalsIgnoreCase("PriorWeek"))
            {
                fromDate = currentDate.subtractDays(7);
            }
            else if (filterPeriod.equalsIgnoreCase("PriorMonth"))
            {
                fromDate = currentDate.subtractMonths(1);
            }
            else if (filterPeriod.equalsIgnoreCase("AllPeriods"))
            {
                fromDate = null;
            }
        }

        String filterTransaction = appReqBlock.getReqParm("filterTransaction");

        if (Util.isANumber(filterTransaction) && !filterTransaction.equals("0"))
        {
            filterTransaction = codeTableWrapper.getCodeTableEntry(Long.parseLong(filterTransaction)).getCode();
        }
        else
        {
            filterTransaction = "";
        }

        String filterContractNumber = Util.initString(appReqBlock.getReqParm("policyNumber"), null);

        event.business.Event eventComponent = new event.component.EventComponent();

        AgentVO agentVO = (AgentVO) appReqBlock.getHttpSession().getAttribute("agentVO");

        List commissionHistories = null;

        List voInclusionList = new ArrayList();
        voInclusionList.add(EDITTrxHistoryVO.class);
        voInclusionList.add(FinancialHistoryVO.class);
        voInclusionList.add(EDITTrxVO.class);
        voInclusionList.add(ClientSetupVO.class);
        voInclusionList.add(ContractSetupVO.class);
        voInclusionList.add(SegmentVO.class);
        voInclusionList.add(AgentContractVO.class);
        voInclusionList.add(PlacedAgentVO.class);
        voInclusionList.add(AgentVO.class);
        voInclusionList.add(ClientRoleVO.class);
        voInclusionList.add(ClientDetailVO.class);

        if (filterTransaction.equals("") || filterTransaction.equals("NF"))
        {
            //gets agent license changes only
            ChangeHistoryVO[] agentLicenseChangeHistoryVOs = DAOFactory.getChangeHistoryDAO().getAgentLicenseChangeHistories(agentVO.getAgentPK());

            //get agent table changes
            ChangeHistoryVO[] agentTableChangeHistoryVOs = DAOFactory.getChangeHistoryDAO().getAgentChangeHistories(agentVO.getAgentPK());

            ChangeHistoryVO[] agentChangeHistoryVOs = (ChangeHistoryVO[])Util.joinArrays(agentLicenseChangeHistoryVOs, agentTableChangeHistoryVOs, ChangeHistoryVO.class);

            if (agentChangeHistoryVOs != null)
            {
                commissionHistories = reformatChangeHistory(agentChangeHistoryVOs, fromDate, toDate);
                appReqBlock.getHttpSession().setAttribute("agentChangeHistoryVOs", agentChangeHistoryVOs);
            }
        }

        AgentContractVO[] agentContractVOs = agentVO.getAgentContractVO();

        if (agentContractVOs != null)
        {
            if (commissionHistories == null)
            {
                commissionHistories = new ArrayList();
            }

            for (int i = 0; i < agentContractVOs.length; i++)
            {
                PlacedAgentVO[] placedAgentVOs = agentContractVOs[i].getPlacedAgentVO();
                long[] placedAgentPKs = new long[placedAgentVOs.length];
                String[] statuses = new String[1];
                statuses[0] = "U";

                if ((placedAgentVOs != null) && (placedAgentVOs.length > 0))
                {
                    for (int j = 0; j < placedAgentVOs.length; j++)
                    {
                        placedAgentPKs[j] = placedAgentVOs[j].getPlacedAgentPK();
                    }

                    CommissionHistoryVO[] commissionHistoryVO = eventComponent.composeCommHistoryByPlacedAgentTransTypeDatesAndPolicy(placedAgentPKs, filterTransaction, fromDate, toDate, filterContractNumber,
                            voInclusionList);

                    if (commissionHistoryVO != null)
                    {
                        for (int k = 0; k < commissionHistoryVO.length; k++)
                        {
                            commissionHistories.add(commissionHistoryVO[k]);
                        }
                    }
                }
            }
        }

        if (commissionHistories.size() > 0)
        {
            CommissionHistoryVO[] commissionHistoryVOs = (CommissionHistoryVO[]) commissionHistories.toArray(new CommissionHistoryVO[commissionHistories.size()]);

            appReqBlock.getHttpSession().setAttribute("commissionHistoryVOs", commissionHistoryVOs);
        }

        return COMMISSION_HISTORY_DETAIL;
    }

    protected String saveCommissionHistory(AppReqBlock appReqBlock)
        throws Exception
    {
        String selectedCommissionHistoryPK = appReqBlock.getReqParm("selectedCommissionHistoryPK");
        CommissionHistoryVO[] commissionHistoryVOs = (CommissionHistoryVO[]) appReqBlock.getHttpSession().getAttribute("commissionHistoryVOs");

        for (int i = 0; i < commissionHistoryVOs.length; i++)
        {
            if ((commissionHistoryVOs[i].getCommissionHistoryPK() + "").equals(selectedCommissionHistoryPK))
            {
                event.business.Event eventComponent = new event.component.EventComponent();
                eventComponent.saveCommissionHistoryAdjustment(commissionHistoryVOs[i]);
            }
        }

        return COMMISSION_HISTORY_DETAIL;
    }

    protected String saveCommHistoryAdjustment(AppReqBlock appReqBlock)
        throws Exception
    {
        String selectedCommissionHistoryPK = appReqBlock.getReqParm("selectedCommissionsHistoryPK");
        appReqBlock.getHttpServletRequest().setAttribute("selectedCommissionHistoryPK", selectedCommissionHistoryPK);

        String statementInd = appReqBlock.getReqParm("statementIndStatus");
        CommissionHistoryVO[] commissionHistoryVOs = (CommissionHistoryVO[]) appReqBlock.getHttpSession().getAttribute("commissionHistoryVOs");

        for (int i = 0; i < commissionHistoryVOs.length; i++)
        {
            if ((commissionHistoryVOs[i].getCommissionHistoryPK() + "").equals(selectedCommissionHistoryPK))
            {
                commissionHistoryVOs[i].setStatementInd(statementInd);

                break;
            }
        }

        return COMMISSION_HISTORY_DETAIL;
    }

    protected String cancelCommHistoryAdjustment(AppReqBlock appReqBlock)
        throws Exception
    {
        String selectedCommissionHistoryPK = appReqBlock.getReqParm("selectedCommissionsHistoryPK");
        appReqBlock.getHttpServletRequest().setAttribute("selectedCommissionHistoryPK", selectedCommissionHistoryPK);

        return COMMISSION_HISTORY_DETAIL;
    }

    protected String showAllowancesDialog(AppReqBlock appReqBlock)
        throws Exception
    {
        String selectedCommissionHistoryPK = appReqBlock.getFormBean().getValue("selectedCommissionHistoryPK");
        appReqBlock.getHttpServletRequest().setAttribute("selectedCommissionHistoryPK", selectedCommissionHistoryPK);

        String currentPage = (String) appReqBlock.getHttpSession().getAttribute("currentPage");
        String previousPage = currentPage;

        savePreviousPageToVO(appReqBlock, previousPage);

        appReqBlock.getHttpSession().setAttribute("currentPage", ALLOWANCES_DIALOG);
        appReqBlock.getHttpSession().setAttribute("previousPage", previousPage);

        return ALLOWANCES_DIALOG;
    }

    protected String addNewAgent(AppReqBlock appReqBlock)
        throws Exception
    {
        // Check for authorization
        new AgentUseCaseComponent().addAgent();

        UserSession userSession = (UserSession) appReqBlock.getHttpSession().getAttribute("userSession");
        userSession.lockAgent(0);

        appReqBlock.getHttpSession().removeAttribute("agentVO");
        appReqBlock.getHttpSession().removeAttribute("clientDetailVOs");
        appReqBlock.getHttpSession().removeAttribute("commissionHistoryVOs");
        appReqBlock.getHttpSession().removeAttribute(CommissionHistoryExtractCache.AGENT_EXTRACT_SESSION_KEY);

        return AGENT_ADD;
    }

    protected String buildAgentRole(AppReqBlock appReqBlock)
        throws Exception
    {
        UserSession userSession = (UserSession) appReqBlock.getHttpSession().getAttribute("userSession");
        agent.business.Agent agentComponent = new agent.component.AgentComponent();

        loadAuthorizedCompanies(userSession, appReqBlock);

        PageBean formBean = appReqBlock.getFormBean();
        String selectedClientDetailPK = formBean.getValue("selectedClientDetailPK");
        long clientDetailPK = Long.parseLong(selectedClientDetailPK);

        client.business.Lookup clientLookup = new client.component.LookupComponent();
        List voExclusionList = new ArrayList();
        voExclusionList.add(ClientAddressVO.class);
        voExclusionList.add(ClientRoleVO.class);
        voExclusionList.add(ReinsurerVO.class);
        voExclusionList.add(FilteredRequirementVO.class);

        ClientDetailVO[] clientDetailVOs = clientLookup.findClientDetailByClientPK(clientDetailPK, true, voExclusionList);

        appReqBlock.getHttpSession().setAttribute("clientDetailVO", clientDetailVOs[0]);

//        role.business.Lookup roleLookup = new role.component.LookupComponent();
        role.business.Role roleComponent = new role.component.RoleComponent();
        String agentNumber = null;
//        ClientRoleVO[] clientRoleVOs = roleLookup.getRoleByRoleTypeClientDetailFKStatusReferenceID("Agent", clientDetailPK, "P", agentNumber);
        long clientRoleFK = 0;

/*        if ((clientRoleVOs != null) && (clientRoleVOs.length > 0))
        {
            clientRoleFK = clientRoleVOs[0].getClientRolePK();
            appReqBlock.getHttpServletRequest().setAttribute("formBean", formBean);
            appReqBlock.getHttpSession().setAttribute("clientRoleFK", clientRoleFK + "");

            appReqBlock.getHttpSession().setAttribute("currentPage", AGENT_DETAIL_MAIN);
            appReqBlock.getHttpSession().setAttribute("previousPage", "");

            AgentVO agentVO = agentComponent.composeAgentVO(clientRoleVOs[0].getAgentFK(), new ArrayList());

            if (agentVO != null)
            {
                userSession.lockAgent(agentVO.getAgentPK());

                appReqBlock.setReqParm("agentPK", agentVO.getAgentPK() + "");
                appReqBlock.getHttpServletRequest().setAttribute("errorMessage", "Agent Already Existed");

                return showAgentDetailMainContent(appReqBlock);
            }
            else
            {
                return AGENT_DETAIL_MAIN;
            }
        }
        else
        {  */
            PreferenceVO[] preferences = clientDetailVOs[0].getPreferenceVO();
            TaxInformationVO[] taxInformationVO = clientDetailVOs[0].getTaxInformationVO();

            int i = 0;
            long preferenceFK = 0;
            long taxProfileFK = 0;

            if (preferences != null)
            {
                for (i = 0; i < preferences.length; i++)
                {
                    if (preferences[i].getOverrideStatus().equalsIgnoreCase("P"))
                    {
                        preferenceFK = preferences[i].getPreferencePK();

                        break;
                    }
                }
            }

            if ((taxInformationVO != null) && (taxInformationVO.length > 0))
            {
                TaxProfileVO[] taxProfileVO = taxInformationVO[0].getTaxProfileVO();

                if (taxProfileVO != null)
                {
                    for (i = 0; i < taxProfileVO.length; i++)
                    {
                        if (taxProfileVO[i].getOverrideStatus().equalsIgnoreCase("P"))
                        {
                            taxProfileFK = taxProfileVO[i].getTaxProfilePK();

                            break;
                        }
                    }
                }
            }

            ClientRoleVO clientRoleVO = new ClientRoleVO();
            clientRoleVO.setClientRolePK(0);
            clientRoleVO.setClientDetailFK(clientDetailPK);
            clientRoleVO.setPreferenceFK(preferenceFK);
            clientRoleVO.setTaxProfileFK(taxProfileFK);
            clientRoleVO.setRoleTypeCT("Agent");
            clientRoleVO.setOverrideStatus("P");
            clientRoleVO.setReferenceID(agentNumber);

            clientRoleFK = roleComponent.saveOrUpdateClientRole(clientRoleVO);

            //ClientRoleFinancial always needs to exist with the client role
            ClientRoleFinancialVO clientRoleFinancialVO = new ClientRoleFinancialVO();
            clientRoleFinancialVO.setClientRoleFinancialPK(0);
            clientRoleFinancialVO.setClientRoleFK(clientRoleFK);

            agentComponent.createOrUpdateVO(clientRoleFinancialVO, false);

            appReqBlock.getHttpServletRequest().setAttribute("formBean", formBean);
            appReqBlock.getHttpSession().setAttribute("clientRoleFK", clientRoleFK + "");

            appReqBlock.getHttpSession().setAttribute("currentPage", AGENT_DETAIL_MAIN);
            appReqBlock.getHttpSession().setAttribute("previousPage", "");

            return AGENT_DETAIL_MAIN;
//        }
    }

    protected String findAgentsByNameDOB(AppReqBlock appReqBlock)
        throws Exception
    {
        client.business.Lookup clientLookup = new client.component.LookupComponent();

        PageBean formBean = appReqBlock.getFormBean();
        String name = formBean.getValue("name");
        String dateOfBirth = DateTimeUtil.formatMMDDYYYYToYYYYMMDD(formBean.getValue("dob"));

        String[] nameElements = Util.fastTokenizer(name, ",");

        ClientDetailVO[] clientDetailVOs = null;
        List voExclusionList = new ArrayList();
        voExclusionList.add(PreferenceVO.class);
        voExclusionList.add(TaxInformationVO.class);
        voExclusionList.add(ClientRoleVO.class);

        if (nameElements.length == 2)
        {
            if (nameElements[1].equals(""))
            {
//                clientDetailVOs = clientLookup.findClientDetailByLastNameDOB(nameElements[0].trim(), dateOfBirth, true, voExclusionList);
                clientDetailVOs = client.dm.dao.DAOFactory.getClientDetailDAO().findByLastName_AND_BirthDateWithoutOvrds(nameElements[0].trim(),
                        dateOfBirth, true, voExclusionList);
            }
            else
            {
                clientDetailVOs = clientLookup.findClientDetailByLastNamePartialFirstNameDOB(nameElements[0].trim(), nameElements[1].trim(), dateOfBirth, true, voExclusionList);
                clientDetailVOs = client.dm.dao.DAOFactory.getClientDetailDAO().findByLastNamePartialFirstName_AND_BirthDateWithoutOvrds(nameElements[0].trim(),
                        nameElements[1].trim(), dateOfBirth, true, voExclusionList);
            }
        }
        else if (nameElements.length == 1)
        {
//            clientDetailVOs = clientLookup.findClientDetailByPartialLastNameDOB(nameElements[0].trim(), dateOfBirth, true, voExclusionList);
            clientDetailVOs = client.dm.dao.DAOFactory.getClientDetailDAO().findByPartialLastName_AND_BirthDateWithoutOvrds(nameElements[0].trim(),
                       dateOfBirth, true, voExclusionList);
        }

        if (clientDetailVOs != null)
        {
            appReqBlock.getHttpSession().setAttribute("clientDetailVOs", clientDetailVOs);
            appReqBlock.getHttpServletRequest().setAttribute("searchStatus", "");
        }
        else
        {
            appReqBlock.getHttpServletRequest().setAttribute("searchStatus", "noData");
        }

        return AGENT_ADD;
    }

    protected String findAgentsByName(AppReqBlock appReqBlock)
        throws Exception
    {
        client.business.Lookup clientLookup = new client.component.LookupComponent();

        PageBean formBean = appReqBlock.getFormBean();
        String name = formBean.getValue("name");

        name = Util.substitute(name, "'", "''");

        String[] nameElements = Util.fastTokenizer(name, ",");

        ClientDetailVO[] clientDetailVOs = null;
        List voExclusionList = new ArrayList();
        voExclusionList.add(PreferenceVO.class);
        voExclusionList.add(TaxInformationVO.class);
        voExclusionList.add(ClientRoleVO.class);

        if (nameElements.length == 2)
        {
            if (nameElements[1].equals(""))
            {
//                clientDetailVOs = clientLookup.findClientDetailByLastName(nameElements[0].trim(), true, voExclusionList);
                clientDetailVOs = client.dm.dao.DAOFactory.getClientDetailDAO().findByLastNameWithoutOvrds(nameElements[0].trim(), true, voExclusionList);
            }
            else
            {
                clientDetailVOs = clientLookup.findClientDetailByLastNamePartialFirstName(nameElements[0].trim(), nameElements[1].trim(), true, voExclusionList);
                clientDetailVOs = client.dm.dao.DAOFactory.getClientDetailDAO().findByLastNamePartialFirstNameWithoutOvrds(nameElements[0].trim(), nameElements[1].trim(), true, voExclusionList);
            }
        }
        else if (nameElements.length == 1)
        {
//            clientDetailVOs = clientLookup.findClientDetailByPartialLastName(nameElements[0].trim(), true, voExclusionList);
            clientDetailVOs = client.dm.dao.DAOFactory.getClientDetailDAO().findByPartialLastNameWithoutOvrds(nameElements[0].trim(), true, voExclusionList);
        }

        if (clientDetailVOs != null)
        {
            appReqBlock.getHttpSession().setAttribute("clientDetailVOs", clientDetailVOs);
            appReqBlock.getHttpServletRequest().setAttribute("searchStatus", "");
        }
        else
        {
            appReqBlock.getHttpServletRequest().setAttribute("searchStatus", "noData");
        }

        return AGENT_ADD;
    }

    protected String findAgentsByTaxId(AppReqBlock appReqBlock)
        throws Exception
    {
        client.business.Lookup clientLookup = new client.component.LookupComponent();
        PageBean formBean = appReqBlock.getFormBean();
        String taxId = formBean.getValue("taxIdentification");

        List voExclusionList = new ArrayList();
        voExclusionList.add(PreferenceVO.class);
        voExclusionList.add(TaxInformationVO.class);
        voExclusionList.add(ClientRoleVO.class);

//        ClientDetailVO[] clientDetailVOs = clientLookup.findClientDetailByTaxId(taxId, true, voExclusionList);
        ClientDetailVO[] clientDetailVOs = client.dm.dao.DAOFactory.getClientDetailDAO().findByTaxIdWithoutOvrds(taxId, true, voExclusionList);

        if (clientDetailVOs != null)
        {
            appReqBlock.getHttpSession().setAttribute("clientDetailVOs", clientDetailVOs);
            appReqBlock.getHttpServletRequest().setAttribute("searchStatus", "");
        }
        else
        {
            appReqBlock.getHttpServletRequest().setAttribute("searchStatus", "noData");
        }

        return AGENT_ADD;
    }

    protected String findAgentsByAgentId(AppReqBlock appReqBlock)
        throws Exception
    {
        client.business.Lookup clientLookup = new client.component.LookupComponent();
        PageBean formBean = appReqBlock.getFormBean();
        String agentNumber = formBean.getValue("agentId");

        agent.business.Agent agentComponent = new agent.component.AgentComponent();
        AgentVO agentVO = agentComponent.composeAgentVOByAgentNumber(agentNumber, new ArrayList());

        if (agentVO != null)
        {
            appReqBlock.getHttpServletRequest().setAttribute("searchStatus", "Agent Already Exists");
        }
        else
        {
            List voExclusionList = new ArrayList();
            voExclusionList.add(PreferenceVO.class);
            voExclusionList.add(TaxInformationVO.class);
            voExclusionList.add(ClientRoleVO.class);

            ClientDetailVO[] clientDetailVOs = clientLookup.findClientDetailByAgentId(agentNumber, true, voExclusionList);

            if (clientDetailVOs != null)
            {
                appReqBlock.getHttpSession().setAttribute("clientDetailVOs", clientDetailVOs);
                appReqBlock.getHttpServletRequest().setAttribute("searchStatus", "");
            }
            else
            {
                appReqBlock.getHttpServletRequest().setAttribute("searchStatus", "noData");
            }
        }

        return AGENT_ADD;
    }

    protected String showCommissionContract(AppReqBlock appReqBlock)
        throws Exception
    {
        agent.business.Agent agentComponent = new agent.component.AgentComponent();

        CommissionProfileVO[] commissionProfileVOs = agentComponent.composeCommissionProfiles(new ArrayList());

        if (commissionProfileVOs != null)
        {
            appReqBlock.getHttpSession().setAttribute("commissionProfileVOs", commissionProfileVOs);
        }

        return COMMISSION_CONTRACT;
    }

    protected String showContractCodeDialog(AppReqBlock appReqBlock)
        throws Exception
    {
        return CONTRACT_CODE_DIALOG;
    }

    protected String selectContractCode(AppReqBlock appReqBlock)
        throws Exception
    {
        PageBean formBean = appReqBlock.getFormBean();
        String selectedCommContractFK = formBean.getValue("selectedCommContractFK");

        //        appReqBlock.getHttpServletRequest().setAttribute("selectedContractCode", selectedContractCode);
        appReqBlock.getHttpServletRequest().setAttribute("selectedCommContractFK", selectedCommContractFK);

        return COMMISSION_CONTRACT;
    }

    protected String saveContractCode(AppReqBlock appReqBlock)
        throws Exception
    {
        String contractCode = appReqBlock.getFormBean().getValue("contractCode");

        boolean contractCodeExists = false;

        return COMMISSION_CONTRACT;
    }

    protected String showCommissionLevelDialog(AppReqBlock appReqBlock)
        throws Exception
    {
        PageBean formBean = appReqBlock.getFormBean();
        String selectedContractCode = formBean.getValue("selectedContractCode");
        String selectedCommContractFK = formBean.getValue("selectedCommContractFK");

        appReqBlock.getHttpServletRequest().setAttribute("selectedContractCode", selectedContractCode);
        appReqBlock.getHttpServletRequest().setAttribute("selectedCommContractFK", selectedCommContractFK);

        return COMMISSION_LEVEL_DIALOG;
    }

    protected String selectCommissionLevel(AppReqBlock appReqBlock)
        throws Exception
    {
        PageBean formBean = appReqBlock.getFormBean();
        String selectedCommLevelDescriptionFK = formBean.getValue("selectedCommLevelDescriptionFK");
        String selectedCommContractFK = formBean.getValue("selectedCommContractFK");
        String selectedContractCode = formBean.getValue("selectedContractCode");

        appReqBlock.getHttpServletRequest().setAttribute("selectedContractCode", selectedContractCode);
        appReqBlock.getHttpServletRequest().setAttribute("selectedCommContractFK", selectedCommContractFK);
        appReqBlock.getHttpServletRequest().setAttribute("selectedCommLevelDescriptionFK", selectedCommLevelDescriptionFK);

        return COMMISSION_CONTRACT;
    }

    protected String saveCommissionLevelDescription(AppReqBlock appReqBlock)
        throws Exception
    {
        String selectedContractCodeCT = appReqBlock.getFormBean().getValue("selectedContractCodeCT");

        appReqBlock.getHttpServletRequest().setAttribute("selectedContractCodeCT", selectedContractCodeCT);

        return COMMISSION_CONTRACT;
    }

    protected String addOrCancelProfile(AppReqBlock appReqBlock)
        throws Exception
    {
        return COMMISSION_CONTRACT;
    }

    protected String showProfileDetailSummary(AppReqBlock appReqBlock)
        throws Exception
    {
        String selectedCommProfilePK = appReqBlock.getReqParm("selectedCommProfilePK");

        appReqBlock.getHttpServletRequest().setAttribute("selectedCommProfilePK", selectedCommProfilePK);

        return COMMISSION_CONTRACT;
    }

    protected String saveCommissionProfile(AppReqBlock appReqBlock)
        throws Exception
    {
        PageBean formBean = appReqBlock.getFormBean();

        String selectedCommProfilePK = formBean.getValue("selectedCommProfilePK");
        String contractCode = formBean.getValue("contractCode");
        String commissionOption = formBean.getValue("commissionOption");
        String commissionLevel = formBean.getValue("commissionLevel");
        String trailStatus = formBean.getValue("trailStatus");

        if (trailStatus.equalsIgnoreCase("checked"))
        {
            trailStatus = "Y";
        }
        else
        {
            trailStatus = "N";
        }

        Agent agentComponent = new AgentComponent();

        CommissionProfileVO commProfileVO = new CommissionProfileVO();

        if (Util.isANumber(selectedCommProfilePK))
        {
            commProfileVO.setCommissionProfilePK(Long.parseLong(selectedCommProfilePK));
        }
        else
        {
            commProfileVO.setCommissionProfilePK(0);
        }

        commProfileVO.setCommissionOptionCT(commissionOption);
        commProfileVO.setTrailStatus(trailStatus);
        commProfileVO.setContractCodeCT(contractCode);
        commProfileVO.setCommissionLevelCT(commissionLevel);

        agentComponent.createOrUpdateVO(commProfileVO, false);

        CommissionProfileVO[] commissionProfileVOs = agentComponent.composeCommissionProfiles(new ArrayList());

        appReqBlock.getHttpSession().setAttribute("commissionProfileVOs", commissionProfileVOs);

        return COMMISSION_CONTRACT;
    }

    protected String deleteCommissionProfile(AppReqBlock appReqBlock)
        throws Exception
    {
        PageBean formBean = appReqBlock.getFormBean();

        String selectedCommProfilePK = formBean.getValue("selectedCommProfilePK");

        Agent agentComponent = new AgentComponent();
        List profileVOInclusionList = new ArrayList();
        profileVOInclusionList.add(PlacedAgentVO.class);
        profileVOInclusionList.add(PlacedAgentCommissionProfileVO.class);

        CommissionProfileVO selectedCommProfile = agentComponent.composeCommissionProfileVOByCommissionProfilePK(Long.parseLong(selectedCommProfilePK), profileVOInclusionList);

        if (selectedCommProfile.getPlacedAgentCommissionProfileVOCount() > 0)
        {
        	String errorMessage = "Cannot Delete Profile - Attached to " + selectedCommProfile.getPlacedAgentCommissionProfileVOCount() + " Placed Agent Commission Profile(s)";
        	String operator = appReqBlock.getUserSession().getUsername();
        	
        	EDITMap columnInfo = new EDITMap();
            columnInfo.put("ContractCodeCT", selectedCommProfile.getContractCodeCT());
            columnInfo.put("CommissionLevelCT", selectedCommProfile.getCommissionLevelCT());
            columnInfo.put("CommissionOptionCT", selectedCommProfile.getCommissionOptionCT());
            columnInfo.put("TrailStatus", selectedCommProfile.getTrailStatus());
            columnInfo.put("Operator", operator);

            Log.logToDatabase(Log.MAINTENANCE_COMMISSION_PROFILE, errorMessage, columnInfo);
            
            appReqBlock.getHttpServletRequest().setAttribute("profileMessage", "Cannot Delete Profile - Placed Agent is Attached");
        }
        else
        {
            agentComponent.deleteVO(CommissionProfileVO.class, Long.parseLong(selectedCommProfilePK), false);

            CommissionProfileVO[] commissionProfileVOs = agentComponent.composeCommissionProfiles(new ArrayList());

            if (commissionProfileVOs != null)
            {
                appReqBlock.getHttpSession().setAttribute("commissionProfileVOs", commissionProfileVOs);
            }
            else
            {
                appReqBlock.getHttpSession().removeAttribute("commissionProfileVOs");
            }
        }

        return COMMISSION_CONTRACT;
    }

    protected String lockAgent(AppReqBlock appReqBlock)
        throws Exception
    {
        new AgentUseCaseComponent().updateAgent();

        String agentPK = null;

        AgentVO agentVO = (AgentVO) appReqBlock.getHttpSession().getAttribute("agentVO");

        if (agentVO != null)
        {
            agentPK = agentVO.getAgentPK() + "";
        }

        if ((agentPK == null) || agentPK.equals(""))
        {
            agentPK = "0";
        }

        UserSession userSession = appReqBlock.getUserSession();

        loadAuthorizedCompanies(userSession, appReqBlock);

        if (!agentPK.equals("0"))
        {
            try
            {
                userSession.lockAgent(Long.parseLong(agentPK));
            }
            catch (EDITLockException e)
            {
                appReqBlock.getHttpServletRequest().setAttribute("errorMessage", e.getMessage());
            }
        }

        return AGENT_DETAIL_MAIN;
    }

    protected String showCancelAgentConfirmationDialog(AppReqBlock appReqBlock)
        throws Exception
    {
        return CANCEL_AGENT_CONFIRMATION_DIALOG;
    }

    protected void savePreviousPageToVO(AppReqBlock appReqBlock, String pageName)
    {
        CodeTableWrapper codeTableWrapper = CodeTableWrapper.getSingleton();

        PageBean formBean = appReqBlock.getFormBean();
        AgentVO agentVO = (AgentVO) appReqBlock.getHttpSession().getAttribute("agentVO");

        if (pageName.equals(AGENT_DETAIL_MAIN))
        {
            if (agentVO == null)
            {
                agentVO = new AgentVO();
            }

            String agentPK = formBean.getValue("agentPK");

            if (Util.isANumber(agentPK))
            {
                agentVO.setAgentPK(Long.parseLong(agentPK));
            }
            else
            {
                agentVO.setAgentPK(0);
            }
//
//            String clientRoleFK = (String) appReqBlock.getHttpSession().getAttribute("clientRoleFK");
//
//            if ((clientRoleFK != null) && Util.isANumber(clientRoleFK))
//            {
//                agentVO.setClientRoleFK(Long.parseLong(clientRoleFK));
//            }
//            else
//            {
//                agentVO.setClientRoleFK(0);
//            }

            String companyName = appReqBlock.getReqParm("company");
            Company company = Company.findBy_CompanyName(companyName);

            agentVO.setCompanyFK(company.getCompanyPK().longValue());

            String agentNumber = formBean.getValue("agentNumber");
            agentVO.setAgentNumber(Util.initString(agentNumber, null));

            String hireMonth = formBean.getValue("hireMonth");
            String hireDay = formBean.getValue("hireDay");
            String hireYear = formBean.getValue("hireYear");
            String hireDate = DateTimeUtil.initDate(hireMonth, hireDay, hireYear, null);

            agentVO.setHireDate(hireDate);

            String termMonth = formBean.getValue("terminationMonth");
            String termDay = formBean.getValue("terminationDay");
            String termYear = formBean.getValue("terminationYear");
            String termDate = DateTimeUtil.initDate(termMonth, termDay, termYear, EDITDate.DEFAULT_MAX_DATE);

            agentVO.setTerminationDate(termDate);

            String agentStatus = formBean.getValue("status");

            if (Util.isANumber(agentStatus))
            {
                agentStatus = codeTableWrapper.getCodeTableEntry(Long.parseLong(agentStatus)).getCode();
            }
            else
            {
                agentStatus = "";
            }

            agentVO.setAgentStatusCT(Util.initString(agentStatus, null));

            String agentType = formBean.getValue("agentType");

            if (Util.isANumber(agentType))
            {
                agentType = codeTableWrapper.getCodeTableEntry(Long.parseLong(agentType)).getCode();
            }
            else
            {
                agentType = "";
            }

            agentVO.setAgentTypeCT(Util.initString(agentType, null));

            String withholdingStatus = formBean.getValue("withholdingStatus");

            if (withholdingStatus.equalsIgnoreCase("checked"))
            {
                withholdingStatus = "Y";
            }
            else
            {
                withholdingStatus = "N";
            }

            agentVO.setWithholdingStatus(withholdingStatus);

            String stateLicExcStatus = formBean.getValue("stateLicExcStatus");

            if (stateLicExcStatus.equalsIgnoreCase("checked"))
            {
                stateLicExcStatus = "Y";
            }
            else
            {
                stateLicExcStatus = "N";
            }

            String holdCommStatus = formBean.getValue("holdCommStatus");

            if (holdCommStatus.equalsIgnoreCase("checked"))
            {
                holdCommStatus = "Y";
            }
            else
            {
                holdCommStatus = "N";
            }

            agentVO.setHoldCommStatus(holdCommStatus);

            String department = formBean.getValue("department");
            agentVO.setDepartment(Util.initString(department, null));

            String region = formBean.getValue("region");
            agentVO.setRegion(Util.initString(region, null));

            String branch = formBean.getValue("branch");
            agentVO.setBranch(Util.initString(branch, null));

            String disbAddressType = formBean.getValue("disbAddressType");

            if (Util.isANumber(disbAddressType))
            {
                disbAddressType = codeTableWrapper.getCodeTableEntry(Long.parseLong(disbAddressType)).getCode();
            }
            else
            {
                disbAddressType = "";
            }

            agentVO.setDisbursementAddressTypeCT(Util.initString(disbAddressType, null));

            String corrAddressType = formBean.getValue("corrAddressType");

            if (Util.isANumber(corrAddressType))
            {
                corrAddressType = codeTableWrapper.getCodeTableEntry(Long.parseLong(corrAddressType)).getCode();
            }
            else
            {
                corrAddressType = "";
            }

            agentVO.setCorrespondenceAddressTypeCT(Util.initString(corrAddressType, null));
        }

        appReqBlock.getHttpSession().setAttribute("agentVO", agentVO);
    }

    protected String cancelAgent(AppReqBlock appReqBlock)
        throws Exception
    {
        appReqBlock.getHttpSession().removeAttribute("clientDetailVO");
        appReqBlock.getHttpSession().removeAttribute("agentVO");
        appReqBlock.getHttpSession().removeAttribute("commissionHistoryVOs");
        appReqBlock.getHttpSession().removeAttribute(CommissionHistoryExtractCache.AGENT_EXTRACT_SESSION_KEY);
        appReqBlock.getHttpSession().setAttribute("currentPage", AGENT_DETAIL_MAIN);
        appReqBlock.getHttpSession().setAttribute("previousPage", "");

        String clientRoleFK = (String) appReqBlock.getHttpSession().getAttribute("clientRoleFK");
        
        if(clientRoleFK != null && !clientRoleFK.equals("") && !clientRoleFK.equals("null"))
        {
            ClientRole clientRole = ClientRole.findByPK(new Long(clientRoleFK));
    
            AgentVO agentVO = checkForRoleRemoval(clientRole);
    
            if (agentVO == null)
            {
                deleteClientRole(clientRoleFK);
            }
        }
        
        UserSession userSession = appReqBlock.getUserSession();

        userSession.unlockAgent();

        return AGENT_DETAIL_MAIN;
    }

    protected String showDeleteAgentConfirmDialog(AppReqBlock appReqBlock)
        throws Exception
    {
        String agentPK = "";
        String agentId = "";
        AgentVO agentVO = (AgentVO) appReqBlock.getHttpSession().getAttribute("agentVO");

        if (agentVO != null)
        {
            ClientDetailVO clientDetailVO = (ClientDetailVO) appReqBlock.getHttpSession().getAttribute("clientDetailVO");
            agentPK = agentVO.getAgentPK() + "";

            if (clientDetailVO != null)
            {
                agentId = clientDetailVO.getClientIdentification();
            }

            appReqBlock.getHttpServletRequest().setAttribute("agentPK", agentPK);
            appReqBlock.getHttpServletRequest().setAttribute("agentId", agentId);
        }

        return DELETE_AGENT_CONFIRM_DIALOG;
    }

    protected String deleteAgent(AppReqBlock appReqBlock)
        throws Exception
    {
        // Check for authorization
        new AgentUseCaseComponent().deleteAgent();

        PageBean formBean = appReqBlock.getFormBean();
        String agentPK = formBean.getValue("agentPK");

        if (Util.isANumber(agentPK))
        {
            try
            {
                agent.Agent agent = new agent.Agent((AgentVO) appReqBlock.getHttpSession().getAttribute("agentVO"));
                AgentVO agentVOTemp = (AgentVO)appReqBlock.getHttpSession().getAttribute("agentVO");
                
                String clientRoleFK = "0";
                if(agentVOTemp.getClientRoleVO().length>0)
                {
                    clientRoleFK = agentVOTemp.getClientRoleVO(0).getClientRolePK()+"";
                }
                
                agent.delete();
                appReqBlock.getHttpServletRequest().setAttribute("errorMessage", "Agent Deleted");
                appReqBlock.getHttpSession().removeAttribute("agentVO");
                appReqBlock.getHttpSession().removeAttribute("clientDetailVO");
                appReqBlock.getHttpSession().removeAttribute("commissionHistoryVOs");
                appReqBlock.getHttpSession().removeAttribute(CommissionHistoryExtractCache.AGENT_EXTRACT_SESSION_KEY);

                ClientRole clientRole = ClientRole.findByPK(new Long(clientRoleFK));

                AgentVO agentVO = checkForRoleRemoval(clientRole);

                if (agentVO == null)
                {
                    deleteClientRoleFinancial(clientRoleFK);
                    deleteClientRole(clientRoleFK);
                }
            }
            catch (EDITDeleteException d)
            {
                appReqBlock.getHttpServletRequest().setAttribute("errorMessage", d.getMessage());
            }
        }
        else
        {
            appReqBlock.getHttpServletRequest().setAttribute("errorMessage", "Agent Must Be Selected For Delete");
        }

        return AGENT_DETAIL_MAIN;
    }

    protected String saveAgentDetails(AppReqBlock appReqBlock)
        throws Exception
    {
        String currentPage = (String) appReqBlock.getHttpSession().getAttribute("currentPage");
        savePreviousPageToVO(appReqBlock, currentPage);

        String ignoreEditWarnings = appReqBlock.getReqParm("ignoreEditWarnings");
        ignoreEditWarnings = (ignoreEditWarnings == null) ? "" : ignoreEditWarnings;

        String operator = appReqBlock.getUserSession().getUsername();

        agent.business.Agent agentComponent = new agent.component.AgentComponent();

        AgentVO agentVO = (AgentVO) appReqBlock.getHttpSession().getAttribute("agentVO");
        AgentVO existingAgentVO = null;
        String agentNumber = Util.initString(agentVO.getAgentNumber(), null);
        if (agentNumber != null)
        {
            existingAgentVO = agentComponent.composeAgentVOByAgentNumber(agentNumber, new ArrayList());
        }

        role.business.Lookup roleLookup = new role.component.LookupComponent();
        List clientVOInclusionList = new ArrayList();
        clientVOInclusionList.add(ClientDetailVO.class);
        clientVOInclusionList.add(ClientAddressVO.class);
        clientVOInclusionList.add(TaxInformationVO.class);
        clientVOInclusionList.add(TaxProfileVO.class);
        clientVOInclusionList.add(PreferenceVO.class);
        
        String clientRolefk = (String)appReqBlock.getHttpSession().getAttribute("clientRoleFK");
        ClientRoleVO clientRoleVO = roleLookup.composeClientRoleVO(new Long(clientRolefk).longValue(), clientVOInclusionList);
        
        if (clientRoleVO != null)
            {
                if (agentVO.getClientRoleVOCount() > 0)
                {
                    ClientRoleVO[] clientRoleVOs = agentVO.getClientRoleVO();
                    for (int i = 0; i < clientRoleVOs.length; i++)
                    {
                        if (clientRoleVOs[i].getClientRolePK() == clientRoleVO.getClientRolePK())
                        {
                            agentVO.removeClientRoleVO(i);
                            break;
                        }
                    }
                }

                // In Equitrust world the agent number is assigned at the time of creating the agent and is stored
                // in Agent table.
                // In Vision world the agent number is assigned when an agent is placed in a hierarchy and the agent number
                // is saved ClientRole.ReferenceID. (Agent.AgentNumber is always null)
                // As per Tom, update the ReferenceID only when Agent Number is not null.
                if (agentNumber != null)
                {
                    clientRoleVO.setReferenceID(agentNumber);
                }

                agentVO.addClientRoleVO(clientRoleVO);
            }

        if (agentVO != null)
        {
            if ((existingAgentVO != null) && (existingAgentVO.getAgentPK() != agentVO.getAgentPK()))
            {
                appReqBlock.getHttpServletRequest().setAttribute("errorMessage", "Agent Number Already Exists");
            }
            else
            {
                String disbAddressType = agentVO.getDisbursementAddressTypeCT();
                String corrAddressType = agentVO.getCorrespondenceAddressTypeCT();
                boolean disbAddressTypeFound = false;
                boolean corrAddressTypeFound = false;

                if (((disbAddressType != null) && !disbAddressType.equals("")) || ((corrAddressType != null) && !corrAddressType.equals("")))
                {
                    ClientDetailVO clientDetailVO = (ClientDetailVO)clientRoleVO.getParentVO(ClientDetailVO.class);
                    ClientAddressVO[] clientAddresses = clientDetailVO.getClientAddressVO();
                    if ((disbAddressType != null) && !disbAddressType.equals(""))
                    {
                        if (clientAddresses != null)
                        {
                            for (int i = 0; i < clientAddresses.length; i++)
                            {
                                if (clientAddresses[i].getAddressTypeCT().equals(disbAddressType))
                                {
                                    disbAddressTypeFound = true;

                                    break;
                                }
                            }
                        }
                    }
                    else
                    {
                        disbAddressTypeFound = true;
                    }

                    if ((corrAddressType != null) && !corrAddressType.equals(""))
                    {
                        if (clientAddresses != null)
                        {
                            for (int i = 0; i < clientAddresses.length; i++)
                            {
                                if (clientAddresses[i].getAddressTypeCT().equals(corrAddressType))
                                {
                                    corrAddressTypeFound = true;

                                    break;
                                }
                            }
                        }
                    }
                    else
                    {
                        corrAddressTypeFound = true;
                    }

                    if (!disbAddressTypeFound)
                    {
                        appReqBlock.getHttpServletRequest().setAttribute("errorMessage", "Disbursement Address Type Not Found For Agent");
                    }

                    else if (!corrAddressTypeFound)
                    {
                        appReqBlock.getHttpServletRequest().setAttribute("errorMessage", "Correspondence Address Type Not Found For Agent");
                    }
                }
                else
                {
                    disbAddressTypeFound = true;
                    corrAddressTypeFound = true;
                }

                if (disbAddressTypeFound && corrAddressTypeFound)
                {
                    agentVO.setOperator(Util.initString(operator, null));
                    agentVO.setMaintDateTime(new EDITDateTime().getFormattedDateTime());

                    int a = 0;
                    AgentContractVO[] agentContractVOs = agentVO.getAgentContractVO();
                    AgentLicenseVO[] agentLicenseVOs = agentVO.getAgentLicenseVO();
                    CheckAdjustmentVO[] checkAdjustmentVOs = agentVO.getCheckAdjustmentVO();
                    RedirectVO[] redirectVOs = agentVO.getRedirectVO();
                    AgentNoteVO[] agentNoteVOs = agentVO.getAgentNoteVO();
                    List agentLicenseVOsToBeDeleted = new ArrayList();

                    if (agentContractVOs != null)
                    {
                        for (a = 0; a < agentContractVOs.length; a++)
                        {
                            if (agentContractVOs[a].getAgentContractPK() < 0)
                            {
                                agentContractVOs[a].setAgentContractPK(0);
                            }
                        }
                    }

                    if (agentLicenseVOs != null)
                    {
                        for (a = 0; a < agentLicenseVOs.length; a++)
                        {
                            if (agentLicenseVOs[a].getVoShouldBeDeleted())
                            {
                                agentLicenseVOsToBeDeleted.add(agentLicenseVOs[a]);
                                agentVO.removeAgentLicenseVO(a);
                            }

                            if (agentLicenseVOs[a].getAgentLicensePK() < 0)
                            {
                                agentLicenseVOs[a].setAgentLicensePK(0);
                            }
                        }
                    }

                    if (checkAdjustmentVOs != null)
                    {
                        for (a = 0; a < checkAdjustmentVOs.length; a++)
                        {
                            if (checkAdjustmentVOs[a].getCheckAdjustmentPK() < 0)
                            {
                                checkAdjustmentVOs[a].setCheckAdjustmentPK(0);
                            }
                        }
                    }

                    if (redirectVOs != null)
                    {
                        for (a = 0; a < redirectVOs.length; a++)
                        {
                            if (redirectVOs[a].getRedirectPK() < 0)
                            {
                                redirectVOs[a].setRedirectPK(0);
                            }
                        }
                    }

                    if (agentNoteVOs != null)
                    {
                        for (a = 0; a < agentNoteVOs.length; a++)
                        {
                            if (agentNoteVOs[a].getAgentNotePK() < 0)
                            {
                                agentNoteVOs[a].setAgentNotePK(0);
                            }
                        }
                    }

                    if (!ignoreEditWarnings.equalsIgnoreCase("true"))
                    {
                        try
                        {
                            validateAgent(agentVO, appReqBlock);
                        }
                        catch (PortalEditingException e)
                        {
                            System.out.println(e);

                            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.

                            throw e;
                        }
                    }

                    if (agentLicenseVOsToBeDeleted.size() > 0)
                    {
                        for (a = 0; a < agentLicenseVOsToBeDeleted.size(); a++)
                        {
                            agentVO.addAgentLicenseVO((AgentLicenseVO) agentLicenseVOsToBeDeleted.get(a));
                        }
                    }

                    agentComponent.saveAgent(agentVO);

                    long agentPK = agentVO.getAgentPK();

//                    List voInclusionList = new ArrayList();
//                    voInclusionList.add(ClientRoleVO.class);
//                    voInclusionList.add(PreferenceVO.class);
//                    voInclusionList.add(ClientRoleFinancialVO.class);
//
//                    AgentVO newAgentVO = agentComponent.composeAgentVO(agentPK, voInclusionList);
                    if (clientRoleVO != null)
                    {
                        ClientRole[] clientRoles = ClientRole.findByAgentFK(new Long(agentPK));

                        for (int i = 0; i < clientRoles.length; i++)
                        {
                            if (clientRoles[i].getRoleTypeCT().equalsIgnoreCase("Agent"))
                            {
                                Preference preference =  (Preference)clientRoles[i].getPreference();

                                String paymentMode = "Auto";

                                if ((preference != null) && (preference.getPaymentModeCT() != null))
                                {
                                    paymentMode = preference.getPaymentModeCT();
                                }

                                ClientRoleFinancial clientRoleFinancial = clientRoles[i].getClientRoleFinancial();

                                String hireDate = agentVO.getHireDate();

                                if (!paymentMode.equals("Auto"))
                                {
                                    hireDate = modifyHireDate(paymentMode, hireDate);
                                }

                                if (clientRoleFinancial.getLastCheckDateTime() == null)
                                {
                                    clientRoleFinancial.setLastCheckDateTime(new EDITDateTime(new EDITDate(hireDate), EDITDateTime.DEFAULT_MIN_TIME));
                                }

                                if (clientRoleFinancial.getLastStatementDateTime() == null)
                                {
                                    clientRoleFinancial.setLastStatementDateTime(new EDITDateTime(new EDITDate(hireDate), EDITDateTime.DEFAULT_MIN_TIME));
                                }

                                agentComponent.createOrUpdateVO(clientRoleFinancial.getVO(), false);

                                role.business.Role roleComponent = new role.component.RoleComponent();

                                // In Equitrust world the agent number is assigned at the time of creating the agent and is stored
                                // in Agent table.
                                // In Vision world the agent number is assigned when an agent is placed in a hierarchy and the agent number
                                // is saved ClientRole.ReferenceID. (Agent.AgentNumber is always null)
                                // As per Tom, update the ReferenceID only when Agent Number is not null.

                                if (agentNumber != null)
                                {
                                    clientRoles[i].setReferenceID(agentNumber);

                                    roleComponent.saveOrUpdateClientRole((ClientRoleVO) clientRoles[i].getVO());
                                }
                            }
                        }
                    }

                    appReqBlock.getHttpSession().setAttribute("currentPage", AGENT_DETAIL_MAIN);
                    appReqBlock.getHttpSession().setAttribute("previousPage", "");

                    UserSession userSession = appReqBlock.getUserSession();

                    userSession.unlockAgent();

                    appReqBlock.getHttpSession().removeAttribute("clientDetailVO");
                    appReqBlock.getHttpSession().removeAttribute("agentVO");
                    appReqBlock.getHttpSession().removeAttribute("commissionHistoryVOs");
                    appReqBlock.getHttpSession().removeAttribute(CommissionHistoryExtractCache.AGENT_EXTRACT_SESSION_KEY);
//                    appReqBlock.getHttpSession().removeAttribute("companies");
                }
            }
        }

        appReqBlock.setReqParm("searchValue", agentVO.getAgentPK() + "");

        appReqBlock.setReqParm("bypassFrameset", "true"); // We want to reload this agent, but not within a frameset.

        appReqBlock.setReqParm("agentPK", agentVO.getAgentPK() + "");

        return loadAgentDetailAfterSearch(appReqBlock);

        //        return AGENT_DETAIL_MAIN;
    }

    private String modifyHireDate(String paymentMode, String hireDate)
    {
        EDITDate edHireDate = new EDITDate(hireDate);

        edHireDate = edHireDate.addMode(paymentMode);

        return edHireDate.getFormattedDate();
    }

    private int setAgentNoteSequenceNumber(AgentVO agentVO) throws Exception
    {
        int highestSeq = 0;
        int i = 0;
        boolean seqNumberMissing = false;

        AgentNoteVO[] agentNoteVOs = agentVO.getAgentNoteVO();

        if (agentNoteVOs != null)
        {
            for (i = 0; i < agentNoteVOs.length; i++)
            {
                int sequenceNumber = agentNoteVOs[i].getSequence();

                if (sequenceNumber > highestSeq)
                {
                    highestSeq = sequenceNumber;
                }
                else if (sequenceNumber == 0)
                {
                    seqNumberMissing = true;
                }
            }
        }

        return highestSeq += 1;
    }

    protected String showVOEditExceptionDialog(AppReqBlock appReqBlock)
        throws Exception
    {
//        VOEditException voEditException = (VOEditException) appReqBlock.getHttpSession().getAttribute("VOEditException");
//
//        // Remove voEditException from Session (to clear it), and move it to request scope.
//        appReqBlock.getHttpSession().removeAttribute("VOEditException");
//
//        appReqBlock.getHttpServletRequest().setAttribute("VOEditException", voEditException);

        return VO_EDIT_EXCEPTION_DIALOG;
    }

    /* ********************************* Lookup Methods ********************************** */
    private CommissionProfileVO[] composeCommissionProfileVOsByContractCodeCT(String contractCodeCT)
        throws Exception
    {
        Agent agent = new AgentComponent();

        CommissionProfileVO[] commissionProfileVO = agent.composeCommissionProfileVOByContractCodeCT(contractCodeCT, new ArrayList());

        return commissionProfileVO;
    }

    /**
     * Finds the agent by agentId and contractCodeCT
     * @param agentId
     * @param contractCodeCT
     * @return
     * @throws Exception
     */
    private AgentContractVO[] findAgentContractVOByAgentId_AND_ContractCodeCT(String agentId, String contractCodeCT)
        throws Exception
    {
        Agent agent = new AgentComponent();

        AgentContractVO[] agentContractVO = agent.findAgentContractVOByAgentId_AND_ContractCodeCT(agentId, contractCodeCT);

        return agentContractVO;
    }

    private PlacedAgentBranchVO findPlacedAgentBranchVOByPlacedAgentPK(String placedAgentPK, List voInclusionList)
        throws Exception
    {
        //Cache boolean set to true since this method is not used
        PlacedAgentBranch placedAgentBranch = PlacedAgentBranch.findBy_PlacedAgentPK(new Long(placedAgentPK));

        PlacedAgentBranchVO placedAgentBranchVO = placedAgentBranch.getVO();

        return placedAgentBranchVO;
    }

    private void reverseElementsOfPlacedAgentBranchVO(PlacedAgentBranchVO placedAgentBranchVO)
    {
        List branchAsList = Arrays.asList(placedAgentBranchVO.getPlacedAgentVO());

        Collections.reverse(branchAsList);

        PlacedAgentVO[] reversedElements = (PlacedAgentVO[]) branchAsList.toArray(new PlacedAgentVO[branchAsList.size()]);

        placedAgentBranchVO.setPlacedAgentVO(reversedElements);
    }

    private PlacedAgentVO composePlacedAgentVOByPlacedAgentPK(String placedAgentPK, List voInclusionList)
        throws Exception
    {
        Agent agent = new AgentComponent();

        PlacedAgentVO placedAgentVO = agent.composePlacedAgentVOByPlacedAgentPK(new Long(placedAgentPK).longValue(), voInclusionList);

        return placedAgentVO;
    }

    private AgentVO checkForRoleRemoval(ClientRole clientRole)
        throws Exception
    {
        agent.business.Agent agentComponent = new agent.component.AgentComponent();

        Long agentFK = clientRole.getAgentFK();
        if (agentFK != null)
        {
            return agentComponent.composeAgentVO(clientRole.getAgentFK().longValue(), new ArrayList());
        }
        else
        {
            return null;
        }
    }

    private void deleteClientRole(String clientRoleFK)
        throws Exception
    {
        //Delete both clientRole and ClientRoleFinancial that are created while adding new agent.
        deleteClientRoleFinancial(clientRoleFK);
        role.ClientRole clientRole = new ClientRole(Long.parseLong(clientRoleFK));        
        clientRole.delete();
    }

    private void deleteClientRoleFinancial(String clientRoleFK)
        throws Exception
    {
        role.business.Role roleComponent = new role.component.RoleComponent();
        ClientRoleFinancialVO clientRoleFinancialVO = roleComponent.composeClientRoleFinancialByClientRoleFK(Long.parseLong(clientRoleFK), new ArrayList());

        if (clientRoleFinancialVO != null)
        {
            role.ClientRoleFinancial clientRoleFinancial = new ClientRoleFinancial(clientRoleFinancialVO);
            clientRoleFinancial.delete();
        }
    }

    public String filterExtracts(AppReqBlock appReqBlock)
        throws Exception
    {
        appReqBlock.getHttpSession().removeAttribute(CommissionHistoryExtractCache.AGENT_EXTRACT_SESSION_KEY);

        getFilteredExtracts(appReqBlock);

        return COMMISSION_EXTRACT_DIALOG;
    }

    private void getFilteredExtracts(AppReqBlock appReqBlock)
        throws Exception
    {
        String filterAgentId = appReqBlock.getReqParm("filterAgentId");
        appReqBlock.getHttpSession().setAttribute("filterAgentId", filterAgentId);

        CommissionHistoryExtractCache commHistExtractCache = new CommissionHistoryExtractCache(appReqBlock.getHttpSession());
        AgentVO agentVO = (AgentVO) appReqBlock.getHttpSession().getAttribute("agentVO");
        String extractMessage = commHistExtractCache.loadFilteredExtractsIntoSession(filterAgentId,agentVO);

        if ("Agent Not Found".equalsIgnoreCase(extractMessage))
        {
            appReqBlock.getHttpServletRequest().setAttribute("filterAgentId", "");
        }

        appReqBlock.getHttpServletRequest().setAttribute("extractMessage", extractMessage);
    }

    private void getAllExtracts(AppReqBlock appReqBlock)
        throws Exception
    {
        CommissionHistoryExtractCache commHistExractCache = new CommissionHistoryExtractCache(appReqBlock.getHttpSession());

        // LOADS IN ALL COMMISSION EXTRACTS BY UPDATE STATUS INTO SESSION.
        // THIS COULD TAKE A WHILE.
        commHistExractCache.loadCommissionExtractsByUpdateStatusIntoSession();
    }

    public String removeExtractFilter(AppReqBlock appReqBlock)
        throws Exception
    {
        appReqBlock.getHttpSession().setAttribute("filterAgentId", "");

        getAllExtracts(appReqBlock);

        return COMMISSION_EXTRACT_DIALOG;
    }

    private String addNewExtract(AppReqBlock appReqBlock)
    {
        String filterAgentId = appReqBlock.getReqParm("filterAgentId");
        appReqBlock.getHttpServletRequest().setAttribute("filterAgentId", filterAgentId);

        appReqBlock.getHttpServletRequest().setAttribute("transactionType", "Commission Adjustment");
        appReqBlock.getHttpServletRequest().setAttribute("commType", "Adjustment");

        EDITDate currentDate = new EDITDate();

        appReqBlock.getHttpServletRequest().setAttribute("effectiveYear", currentDate.getFormattedYear());
        appReqBlock.getHttpServletRequest().setAttribute("effectiveMonth", currentDate.getFormattedMonth());
        appReqBlock.getHttpServletRequest().setAttribute("effectiveDay", currentDate.getFormattedDay());
        appReqBlock.getHttpServletRequest().setAttribute("processYear", currentDate.getFormattedYear());
        appReqBlock.getHttpServletRequest().setAttribute("processMonth", currentDate.getFormattedMonth());
        appReqBlock.getHttpServletRequest().setAttribute("processDay", currentDate.getFormattedDay());

        return COMMISSION_EXTRACT_DIALOG;
    }

    public String cancelAgentAdd(AppReqBlock appReqBlock)
        throws Exception
    {
        UserSession userSession = (UserSession) appReqBlock.getHttpSession().getAttribute("userSession");
        userSession.resetAgentBoolean();
        appReqBlock.getHttpSession().setAttribute("cancelStatus", "cancelAdd");

        return AGENT_ADD;
    }

    /**
     * when the user clicks on cancel button on agent notes dialog
     * added by syam prasad on 8/10/04
     * @param appReqBlock
     * @return
     * @throws Exception
     */
    private String cancelNotesDialog(AppReqBlock appReqBlock)
        throws Exception
    {
        String currentPage;

        AgentVO agentVO;
        AgentNoteVO[] agentNoteVOs;

        agentNoteVOs = (AgentNoteVO[]) appReqBlock.getHttpSession().getAttribute("UnchangedNotesVOs");
        agentVO = (AgentVO) appReqBlock.getHttpSession().getAttribute("agentVO");

        // setAgentNote removes all existing agentNoteVOs first
        agentVO.setAgentNoteVO(agentNoteVOs);

        currentPage = (String) appReqBlock.getHttpSession().getAttribute("currentPage");
        appReqBlock.getHttpSession().setAttribute("previousPage", currentPage);
        appReqBlock.getHttpSession().setAttribute("currentPage", AGENT_DETAIL_MAIN);

        loadAuthorizedCompanies(appReqBlock.getUserSession(), appReqBlock);

        return AGENT_DETAIL_MAIN;
    }

    /**
     * when the user clicks on enter button on agent notes dialog
     * added by syam prasad on 8/10/04
     * @param appReqBlock
     * @return
     * @throws Exception
     */
    private String saveNotesDialog(AppReqBlock appReqBlock)
        throws Exception
    {
        int a = 0;
        long agentPK = 0;

        String currentPage;

        AgentNoteVO[] agentNoteVOs;
        AgentNoteVO agentNoteVO;
        Agent agentComponent;

        AgentVO agentVO;
        agentVO = (AgentVO) appReqBlock.getHttpSession().getAttribute("agentVO");

        if (agentVO != null)
        {
            agentPK = agentVO.getAgentPK();
        }

        if (agentPK > 0)
        {
            if (agentVO != null)
            {
                agentNoteVOs = agentVO.getAgentNoteVO();

                if ((agentNoteVOs != null) && (agentNoteVOs.length > 0))
                {
                    for (a = 0; a < agentNoteVOs.length; a++)
                    {
                        agentNoteVO = (AgentNoteVO) agentNoteVOs[a];

                        if (agentNoteVO != null)
                        {
                            if (agentNoteVO.getAgentNotePK() < 0)
                            {
                                agentNoteVO.setAgentNotePK(0);
                            }
                        }
                    }
                }

                agentComponent = new AgentComponent();

                for (a = 0; a < agentNoteVOs.length; a++)
                {
                    if (agentNoteVOs[a] != null)
                    {
                        //                        // this is to delete from agentVO object stored in session
                        //                        if ( agentNoteVOs[a].getVoShouldBeDeleted() ) {
                        //                            agentVO.removeAgentNoteVO(a);
                        //                        }
                        // actual save, update  or delete in database
                        agentComponent.saveAgentNote(agentNoteVOs[a]);
                    }
                }
            }
        }
        else
        {
            // basically we need to do nothing, but need to see
        }

        UserSession userSession = appReqBlock.getUserSession();
        loadAuthorizedCompanies(userSession, appReqBlock);
        
        currentPage = (String) appReqBlock.getHttpSession().getAttribute("currentPage");
        appReqBlock.getHttpSession().setAttribute("previousPage", currentPage);
        appReqBlock.getHttpSession().setAttribute("currentPage", AGENT_DETAIL_MAIN);

        return AGENT_DETAIL_MAIN;
    }

    /**
     * when new agent note is being added
     * added by syam prasad on 8/16/04
     * @return
     * @throws Exception
     */
    private String addNewAgentNote(AppReqBlock appReqBlock)
        throws Exception
    {
        AgentVO agentVO;
        int newSequenceNumber;

        agentVO = (AgentVO) appReqBlock.getHttpSession().getAttribute("agentVO");

        //        newSequenceNumber = agent.AgentNote.getNextSequenceNumber(agentVO.getAgentNoteVO());
        appReqBlock.getHttpServletRequest().setAttribute("NewAgentNote", "true");

        //        appReqBlock.getHttpServletRequest().setAttribute("NewSeqenceNumber", newSequenceNumber+"");
        return AGENT_NOTES_DIALOG;
    }

    private void validateAgent(AgentVO agentVO, AppReqBlock appReqBlock) throws SPException, PortalEditingException
    {
        SPOutput spOutput = null;
        String processName = "AgentSave";
        EDITDate effectiveDate = new EDITDate();
        long productStructurePK;

        ValidationVO[] validationVOs = null;
        PortalEditingException editingException = null;
        ProductStructureVO[] productStructureVOs = null;

        productStructureVOs = new engine.component.LookupComponent().findProductStructureByNames("Agent", "*", "*", "*", "*");

        productStructurePK = productStructureVOs[0].getProductStructurePK();

        try
        {
            spOutput = new CalculatorComponent().processScript("AgentVO", agentVO, processName, "*", "*", effectiveDate.getFormattedDate(), productStructurePK, false);
        }
        catch (SPException e)
        {
            System.out.println(e);

            e.printStackTrace(); //To change body of catch statement use File | Settings | File Templates.

            throw e;
        }

        validationVOs = spOutput.getSPOutputVO().getValidationVO();

        if (spOutput.hasValidationOutputs())
        {
            UserSession userSession = appReqBlock.getUserSession();

            loadAuthorizedCompanies(userSession, appReqBlock);

            editingException = new PortalEditingException();
            editingException.setValidationVOs(validationVOs);
            editingException.setReturnPage(AGENT_DETAIL_MAIN);

            appReqBlock.getHttpSession().setAttribute("currentPage", AGENT_DETAIL_MAIN);
            appReqBlock.getHttpSession().setAttribute("previousPage", "");

            throw editingException;
        }
    }

    protected String showEditingExceptionDialog(AppReqBlock appReqBlock)
        throws Exception
    {
        PortalEditingException editingException = (PortalEditingException) appReqBlock.getHttpSession().getAttribute("portalEditingException");

        // Remove editingException from Session (to clear it), and move it to request scope.
        appReqBlock.getHttpSession().removeAttribute("portalEditingException");

        appReqBlock.getHttpServletRequest().setAttribute("portalEditingException", editingException);

        return EDITING_EXCEPTION_DIALOG;
    }

    protected String showRollupBalanceHistory(AppReqBlock appReqBlock) throws Exception
    {
        List commissionHistoryVOs = new ArrayList();

        appReqBlock.getHttpServletRequest().setAttribute("commissionHistoryVOs", commissionHistoryVOs.toArray(new CommissionHistoryVO[commissionHistoryVOs.size()]));

        return COMMISSION_HISTORY_DIALOG;
    }

    /**
     * Displays AgentGroup Association Dialog.
     * @param appReqBlock
     * @return
     */
    protected String showAgentGroupAssociationDialog(AppReqBlock appReqBlock)
    {
//        String selectedAgentGroupAssociationPK = new AgentGroupAssociationTableModel(appReqBlock).getSelectedRowId();
//
//        AgentGroupAssociation agentGroupAssociation = AgentGroupAssociation.findBy_PK(new Long(selectedAgentGroupAssociationPK));
//
//        appReqBlock.putInRequestScope("agentGroupAssociation", agentGroupAssociation);
//
//        appReqBlock.putInRequestScope("main", AGENT_GROUP_ASSOCIATION_DIALOG);
//
//        appReqBlock.putInRequestScope("htmlTitle", "Associated Agent Detail");

        return TEMPLATE_MAIN;
    }

    /**
     * Saves AgentGroupAssociation Details
     * @param appReqBlock
     * @return
     */
    protected String saveAgentGroupAssociation(AppReqBlock appReqBlock)
    {
        AgentGroupAssociation agentGroupAssociation = (AgentGroupAssociation)
                Util.mapFormDataToHibernateEntity(appReqBlock.getHttpServletRequest(), AgentGroupAssociation.class, SessionHelper.EDITSOLUTIONS, false);

        Agent agentComponent = new AgentComponent();

        agentComponent.saveAgentGroupAssociation(agentGroupAssociation);

        appReqBlock.putInRequestScope("pageMessage", "AgentGroupAssociation Successfully Saved");

        return showAgentGroupAssociations(appReqBlock);
    }

    /**
     * The default page rendered when moving a Placed Agent.
     */
    private String showAgentMove(AppReqBlock appReqBlock)
    {
        AgentMoveFromTableModel agentMoveFromTableModel = new AgentMoveFromTableModel(appReqBlock, null, null, null);
        
        AgentMoveToTableModel agentMoveToTableModel = new AgentMoveToTableModel(appReqBlock, null, null, null);
        
        AgentMoveSelectedTableModel agentMoveSelectedTableModel = new AgentMoveSelectedTableModel(appReqBlock, null, null);
        
        appReqBlock.putInRequestScope("toolbar", AGENT_MOVE_TOOLBAR);

        appReqBlock.putInRequestScope("main", AGENT_MOVE);

        return TEMPLATE_TOOLBAR_MAIN;        
    }
    
    /**
     * Finds all PlacedAgent branches from the specified Agent # or Agent Name.
     */
    private String findMovingFromToPlacedAgent(AppReqBlock appReqBlock)
    {
        String movingFromAgentName = Util.initString(appReqBlock.getReqParm("movingFromAgentName"), null);
        
        String movingFromAgentNumber = Util.initString(appReqBlock.getReqParm("movingFromAgentNumber"), null);
        
        String movingToAgentName = Util.initString(appReqBlock.getReqParm("movingToAgentName"), null);
        
        String movingToAgentNumber = Util.initString(appReqBlock.getReqParm("movingToAgentNumber"), null);        
        
        String contractCodeCT = Util.initString(appReqBlock.getReqParm("contractCodeCT"), null);
        
        AgentMoveFromTableModel agentMoveFromTableModel = new AgentMoveFromTableModel(appReqBlock, movingFromAgentName, movingFromAgentNumber, contractCodeCT);
        
        AgentMoveToTableModel agentMoveToTableModel = new AgentMoveToTableModel(appReqBlock, movingToAgentName, movingToAgentNumber, contractCodeCT);
        
        AgentMoveSelectedTableModel agentMoveSelectedTableModel = new AgentMoveSelectedTableModel(appReqBlock, agentMoveFromTableModel.getSelectedRowId(), agentMoveToTableModel.getSelectedRowId());
        
        appReqBlock.putInRequestScope("toolbar", AGENT_MOVE_TOOLBAR);

        appReqBlock.putInRequestScope("main", AGENT_MOVE);

        return TEMPLATE_TOOLBAR_MAIN;  
    }
    
    /**
     * Clears the current agent-move screen.
     */
    private String clearAgentMove(AppReqBlock appReqBlock)
    {
        return showAgentMove(appReqBlock);
    }

    /**
     * Performs an agent-group move driven my a "from" PlacedAgent and a "to" PlacedAgent.
     */
    private String moveAgentGroup(AppReqBlock appReqBlock)
    {
        // Agent From/To PKs.
        AgentMoveFromTableModel agentMoveFromTableModel = new AgentMoveFromTableModel(appReqBlock, null, null, null);
        
        String placedAgentFromPK = AgentMoveSelectedTableRow.parseForPlacedAgentPK(agentMoveFromTableModel.getSelectedRowId());
        
        AgentMoveToTableModel agentMoveToTableModel = new AgentMoveToTableModel(appReqBlock, null, null, null);   
        
        String placedAgentToPK = AgentMoveSelectedTableRow.parseForPlacedAgentPK(agentMoveToTableModel.getSelectedRowId());
        
        // StartDate, StopDateReasonCT, SituationCode, AgentNumber
        EDITDate startDate = DateTimeUtil.getEDITDateFromMMDDYYYY(appReqBlock.getReqParm("startDate"));
        
        String stopDateReasonCT = Util.initString(appReqBlock.getReqParm("stopDateReasonCT"), null);
        
        String situationCode = Util.initString(appReqBlock.getReqParm("situationCode"), null);

        String agentNumber = Util.initString(appReqBlock.getReqParm("agentNumber"), null);

        // Operator
        String operator = appReqBlock.getUserSession().getUsername();
        try
        {
            Agent agent = new AgentComponent();

            agent.copyPlacedAgentGroup(Long.parseLong(placedAgentFromPK), Long.parseLong(placedAgentToPK), startDate.getFormattedDate(), stopDateReasonCT, situationCode, operator, agentNumber);

            // Reset the state of this page
            agentMoveFromTableModel.resetAllRows();
            
            agentMoveToTableModel.resetAllRows();
            
            appReqBlock.setReqParm("movingFromAgentName", null);

            appReqBlock.setReqParm("movingFromAgentNumber", null);

            appReqBlock.setReqParm("movingToAgentName", null);

            appReqBlock.setReqParm("movingToAgentNumber", null);                 
            
            // Set any message
            appReqBlock.putInRequestScope("pageCommand", "resetForm");

            appReqBlock.putInRequestScope("pageMessage", new EDITList().addTo("Group Move successfully completed."));
        }
        catch (NumberFormatException ex)
        {
            System.out.println(ex);
            
            ex.printStackTrace();
            
            throw ex;
        }
        catch (EDITAgentException ex)
        {
            appReqBlock.putInRequestScope("pageMessage", ex.getMessage());
        }
                
        return findMovingFromToPlacedAgent(appReqBlock);
    }

    /**
     * Renders the hierarchy (tree-structure) for the selected PlacedAgent.
     */
    private String generateHierarchyReportByPlacedAgentPK(AppReqBlock appReqBlock)
    {
        AgentComponent agentComponent = new AgentComponent(); 
        
        // If the param wasn't sent, assume that expired PlacedAgents will not be rendered.
        boolean includeExpiredAgents = (appReqBlock.getReqParm("includeExpiredAgents") != null && appReqBlock.getReqParm("includeExpiredAgents").equals("true"))?true:false;

        String movingFromToPlacedAgentPK = Util.fastTokenizer(appReqBlock.getReqParm("movingFromToPlacedAgentPK"), ":")[1];
               
        HierarchyReport hierarchyReport = agentComponent.generateHierarchyReportByPlacedAgent(Long.parseLong(movingFromToPlacedAgentPK), includeExpiredAgents);     
        
        appReqBlock.putInRequestScope("hierarchyReport", hierarchyReport);
        
        appReqBlock.putInRequestScope("activePlacedAgentPK", movingFromToPlacedAgentPK);
        
        return AgentDetailTran.HIERARCHY_REPORT_DIALOG;        
    }

    private void loadAuthorizedCompanies(UserSession userSession, AppReqBlock appReqBlock)
    {
        ProductStructure[] productStructures = userSession.getProductStucturesForUser();

        boolean viewAllAgents = false;
        List companiesAllowed = new ArrayList();
        Long securityProductStructurePK = 0L;

        if (productStructures != null && productStructures.length > 0)
        {
            securityProductStructurePK = ProductStructure.checkForSecurityStructure(productStructures);

            companiesAllowed = ProductStructure.checkForAuthorizedCompanies(productStructures);
        }

        if (securityProductStructurePK > 0)
        {
            Operator operator = Operator.findByOperatorName(userSession.getUsername());

            if (userSession.userLoggedIn())
            {
            viewAllAgents = operator.checkViewAllAuthorization(securityProductStructurePK, "Agents");
        }
            else
            {
                viewAllAgents = true;
            }
        }

        String[] companies = null;

        if (viewAllAgents)
        {
            companies = Company.find_All_CompanyNamesForProductType();
        }
        else
        {
            companies = (String[]) companiesAllowed.toArray(new String[companiesAllowed.size()]);
        }

         appReqBlock.getHttpServletRequest().setAttribute("companies", companies);
    }

    private AgentContract[] checkForAuthorization(AppReqBlock appReqBlock, AgentContract[] agentContracts)
    {
        boolean viewAllAgents = false;

        ProductStructure[] productStructures = appReqBlock.getUserSession().getProductStucturesForUser();

        Long securityProductStructurePK = ProductStructure.checkForSecurityStructure(productStructures);

        List companiesAllowed = ProductStructure.checkForAuthorizedCompanies(productStructures);

        if (securityProductStructurePK > 0)
        {
            UserSession userSession = appReqBlock.getUserSession();

            Operator operator = Operator.findByOperatorName(userSession.getUsername());

            if (userSession.userLoggedIn())
            {
            viewAllAgents = operator.checkViewAllAuthorization(securityProductStructurePK, "Agents");
        }
            else
            {
                viewAllAgents = true;
            }
        }

        List filteredAgentList = new ArrayList();

        for (int i = 0; i < agentContracts.length; i++)
        {
            agent.Agent agent = agentContracts[i].getAgent();

            Company company = Company.findByPK(agent.getCompanyFK());

            String companyName = company.getCompanyName();

            if (companiesAllowed.contains(companyName) || viewAllAgents)
            {
                filteredAgentList.add(agentContracts[i]);
            }
        }

        return (AgentContract[]) filteredAgentList.toArray(new AgentContract[filteredAgentList.size()]);
    }


}
