/*
 * User: cgleason
 * Date: Dec 14, 2005
 * Time: 11:04:45 AM
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package contract.ui.servlet;


import agent.Agent;
import agent.AgentContract;
import agent.CommissionProfile;
import agent.PlacedAgent;
import agent.PlacedAgentCommissionProfile;
import agent.component.AgentComponent;
import billing.BillSchedule;
import billing.component.BillingComponent;
import client.*;
import contract.*;
import contract.util.*;
import contract.component.CaseUseCaseComponent;
import contract.util.UtilitiesForTran;
import edit.common.CodeTableWrapper;
import edit.common.EDITBigDecimal;
import edit.common.EDITDate;
import edit.common.EDITDateTime;
import edit.common.EDITList;
import edit.common.exceptions.EDITCaseException;
import edit.common.exceptions.EDITContractException;
import edit.common.exceptions.EDITLockException;
import edit.common.exceptions.EDITValidationException;
import edit.common.vo.*;
import edit.portal.common.session.UserSession;
import edit.portal.common.transactions.Transaction;
import edit.portal.exceptions.PortalEditingException;
import edit.portal.widget.*;
import edit.portal.widgettoolkit.TableModel;
import edit.portal.widgettoolkit.TableRow;
import edit.services.db.hibernate.HibernateEntity;
import edit.services.db.hibernate.SessionHelper;
import engine.ProductStructure;
import engine.component.CalculatorComponent;
import engine.sp.SPException;
import engine.sp.SPOutput;
import event.EDITTrx;
import event.SegmentHistory;
import fission.beans.FormBean;
import fission.beans.PageBean;
import fission.global.AppReqBlock;
import fission.utility.DateTimeUtil;
import fission.utility.Util;
import group.*;
import group.business.Group;
import group.component.GroupComponent;
import contract.Segment;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.tree.DefaultDocument;

import role.ClientRole;
import search.ui.servlet.SearchTran;


public class CaseDetailTran  extends Transaction
{
    private static final String SHOW_CASE_MAIN                   = "showCaseMain";
    private static final String SHOW_GROUP_SUMMARY               = "showGroupSummary";
    private static final String SHOW_ADD_CASE_ENTRY              = "showAddCaseEntry";
    private static final String SEARCH_CLIENTS                   = "searchClients";
    private static final String SAVE_ADD_DIALOG                  = "saveAddDialog";
    private static final String SHOW_GROUP_DETAIL                = "showGroupDetail";
    private static final String SHOW_CASE_SEARCH_DIALOG          = "showCaseSearchDialog";
    private static final String LOCK_CURRENT_TAB                 = "lockCurrentTab";
    private static final String CANCEL_CASE_ENTRY                = "cancelCaseEntry";
    private static final String SHOW_CASE_HISTORY                = "showCaseHistory";
    private static final String SHOW_CASE_HISTORY_FILTER         = "showCaseHistoryFilter";
    private static final String FILTER_CASE_HISTORY              = "filterCaseHistory";
    private static final String SHOW_PRD_DETAIL                  = "showPRDDetail";
    private static final String SHOW_PRD_REPORT                  = "showPRDReport";
    private static final String SHOW_CASE_TRANSACTION_DIALOG     = "showCaseTransactionDialog";
    private static final String SHOW_TRANSACTION_DETAIL          = "showTransactionDetail";
    private static final String SHOW_TRANSFER_TRANSACTION_DETAIL = "showTransferTransactionDetail";
    private static final String ADD_TRANSFER                     = "addTransfer";
    private static final String SAVE_TRANSFER                    = "saveTransfer";
    private static final String DELETE_TRANSFER                  = "deleteTransfer";
    private static final String SHOW_CASE_PRODUCT_ADD            = "showCaseProductAdd";
    private static final String SHOW_CASE_REQUIREMENTS           = "showCaseRequirements";
    private static final String SHOW_CASE_AGENTS                 = "showCaseAgents";
    private static final String SHOW_AGENT_DETAIL_SUMMARY        = "showAgentDetailSummary";
    private static final String SHOW_COMMISSION_OVERRIDES        = "showCommissionOverrides";
    private static final String SHOW_AGENT_SELECTION_DIALOG      = "showAgentSelectionDialog";
    private static final String SHOW_REPORT_TO_AGENT             = "showReportToAgent";
    private static final String SAVE_AGENT_SELECTION             = "saveAgentSelection";
    private static final String CLOSE_AGENT_SELECTION_DIALOG     = "closeAgentSelectionDialog";
    private static final String CLEAR_AGENT_FORM                 = "clearAgentForm";
    private static final String DELETE_SELECTED_AGENT            = "deleteSelectedAgent";
    private static final String SAVE_AGENT_TO_SUMMARY            = "saveAgentToSummary";
    private static final String SAVE_COMMISSION_OVERRIDES        = "saveCommissionOverrides";
    private static final String DELETE_COMMISSION_OVERRIDES      = "deleteCommissionOverrides";
    private static final String SHOW_GROUP_ADD                   = "showGroupAdd";
    private static final String SHOW_GROUP_BILLING_DIALOG        = "showGroupBillingDialog";
    private static final String SAVE_GROUP_BILLING_CHANGE        = "saveGroupBillingChange";
    private static final String SHOW_GROUP_PRD_DIALOG            = "showGroupPRDDialog";
    private static final String SAVE_PRD_CHANGE                  = "savePRDChange";
    private static final String SHOW_GROUP_DEPT_LOC_DIALOG       = "showGroupDeptLocDialog";
    private static final String REFRESH_GROUP_DEPT_LOC_DIALOG       = "refreshGroupDeptLocDialog";
    private static final String SHOW_DEPT_LOC_DETAIL             = "showDeptLocDetail";
    private static final String SAVE_DEPT_LOC_CHANGE             = "saveDeptLocChange";
    private static final String CLEAR_DEPT_LOC                   = "clearDeptLoc";
    private static final String ADD_CLIENT_TROUGH_QUICK_ADD      = "addClientThroughQuickAdd";
    private static final String FIND_PRODUCT_KEYS_BY_COMPANY_NAME =   "findProductKeysByCompanyName";
    private static final String FIND_PRODUCT_KEYS_BY_BUSINESS_CONTRACT_NAME = "findProductKeysByBusinessContractName";
    private static final String SAVE_CURRENT_PAGE = "saveCurrentPage";
    private static final String DELETE_GROUP                     = "deleteGroup";
    private static final String CANCEL_GROUP_ENTRY               = "cancelGroupEntry";
    private static final String SHOW_CASE_AFTER_SEARCH = "showCaseAfterSearch";
    private static final String ADD_PRODUCT_TO_CONTRACT_GROUP = "addProductToContractGroup";
    private static final String REMOVE_PRODUCT_FROM_CONTRACT_GROUP = "removeProductFromContractGroup";
    private static final String SHOW_MANUAL_REQUIREMENT_SELECTION_DIALOG = "showManualRequirementSelectionDialog";
    private static final String SHOW_MANUAL_REQUIREMENT_DESCRIPTION = "showManualRequirementDescription";
    private static final String SHOW_CONTRACTGROUPREQUIREMENT_DETAIL = "showContractGroupRequirementDetail";
    private static final String SAVE_MANUAL_REQUIREMENT = "saveManualRequirement";;
    private static final String DELETE_SELECTED_REQUIREMENT = "deleteSelectedRequirement";
    private static final String SAVE_REQUIREMENT_TO_SUMMARY = "saveRequirementToSummary";
    private static final String CANCEL_REQUIREMENT = "cancelRequirement";
    private static final String SHOW_CONTACT_INFORMATION_DIALOG = "showContactInformationDialog";
    private static final String SHOW_CASE_STATUS_HISTORY_DIALOG = "showCaseStatusHistoryDialog";
    private static final String SHOW_ENROLLMENT_DIALOG = "showEnrollmentDialog";
    private static final String SAVE_ENROLLMENT = "saveEnrollment";
    private static final String SAVE_PROJECTED_BUSINESS = "saveProjectedBusiness";
    private static final String SHOW_ENROLLMENT_DETAIL = "showEnrollmentDetail";
    private static final String SHOW_PROJECTED_BUSINESS_DETAIL = "showProjectedBusinessDetail";
    private static final String SHOW_PRODUCT_UNDERWRITING_DIALOG = "showProductUnderwritingDialog";
    private static final String GET_UNDERWRITING_INFORMATION = "getUnderwritingInformation";
    private static final String ADD_BASE_UNDERWRITING = "addBaseUnderwriting";
    private static final String ADD_RIDER_UNDERWRITING = "addRiderUnderwriting";
    private static final String ADD_OTHER_UNDERWRITING = "addOtherUnderwriting";
    private static final String REMOVE_BASE_UNDERWRITING = "removeBaseUnderwriting";
    private static final String REMOVE_RIDER_UNDERWRITING = "removeRiderUnderwriting";
    private static final String REMOVE_OTHER_UNDERWRITING = "removeOtherUnderwriting";
    private static final String SHOW_MASTER_CONTRACT_INFORMATION = "showMasterContractInformation";
    private static final String SAVE_MASTER_CONTRACT_INFO_CHANGE = "saveMasterContractInfoChange";
    private static final String REMOVE_MASTERCONTRACT_PRODUCT = "removeMasterContractFromProduct";
    private static final String SHOW_MASTER_CONTRACT_DETAIL = "showMasterContractDetail";
    private static final String RESET_SELECTED_MASTER_CONTRACT_DETAIL = "resetSelectedMasterContractDetail";
    private static final String SELECT_MASTER_CONTRACT_DETAIL = "selectMasterContractDetail";
    private static final String UPDATE_MASTER_CONTRACT_INFO_CHANGE = "updateMasaterContractInfoChange";
    private static final String SHOW_PRDCALENDAR = "showPRDCalendar";
    private static final String SHOW_EDITING_EXCEPTION_DIALOG = "showEditingExceptionDialog";
    private static final String SHOW_CASE_NOTES_DIALOG  = "showCaseNotesDialog";
    private static final String SHOW_CASE_NOTE_DETAIL   = "showCaseNoteDetail";
    private static final String SAVE_CASE_NOTE_DETAIL   = "saveCaseNoteDetail";
    private static final String DELETE_CASE_NOTE        = "deleteCaseNote";
    private static final String CANCEL_CASE_NOTE        = "cancelCaseNote";
    private static final String DELETE_ENROLLMENT       = "deleteEnrollment";
    private static final Object DELETE_PROJECTED_BUSINESS = "deleteProjectedBusiness";
    private static final String SHOW_ENROLLMENT_STATE_DIALOG = "showEnrollmentStateDialog";
    private static final String SAVE_ENROLLMENT_STATE   = "saveEnrollmentState";
    private static final String SHOW_ENROLLMENT_STATE_DETAIL   = "showEnrollmentStateDetail";
    private static final String DELETE_ENROLLMENT_STATE  = "deleteEnrollmentState";
    private static final String ADD_ENROLLMENT           = "addEnrollment";
    private static final String ADD_PROJECTED_BUSINESS   = "addProjectedBusiness";
    private static final String SHOW_ENROLLMENT_LEAD_SERVICE_AGENTS = "showEnrollmentLeadServiceAgents";
    private static final String SAVE_ENROLLMENT_LEAD_SERVICE_AGENT = "saveEnrollmentLeadServiceAgent";
    private static final String ADD_ENROLLMENT_LEAD_SERVICE_AGENT = "addEnrollmentLeadServiceAgent";
    private static final String SHOW_ENROLLMENT_LEAD_SERVICE_AGENT_DETAILS = "showEnrollmentLeadServiceAgentDetails";
    private static final String CANCEL_ENROLLMENT_LEAD_SERVICE_AGENT = "cancelEnrollmentLeadServiceAgent";
    private static final String DELETE_ENROLLMENT_LEAD_SERVICE_AGENT = "deleteEnrollmentLeadServiceAgent";
    private static final String SHOW_ENROLLMENT_LEAD_SERVICE_AGENTS_AFTER_SEARCH = "showEnrollmentLeadServiceAgentsAfterSearch";
    private static final String UPDATE_ENROLLMENT_LEAD_SERVICE_AGENT = "updateEnrollmentLeadServiceAgent";
    private static final String DELETE_CASE_ENTRY = "deleteCaseEntry";
    private static final String SHOW_CASE_SETUP = "showCaseSetup";
    private static final String SHOW_CASE_SETUP_DETAIL = "showCaseSetupDetail";
    private static final String SAVE_CASE_SETUP = "saveCaseSetup";
    private static final String DELETE_CASE_SETUP = "deleteCaseSetup";
    private static final String CANCEL_CASE_SETUP = "cancelCaseSetup";


    private static final String CASE_MAIN                   = "/contract/jsp/caseMain.jsp";
    private static final String CASE_GROUP_SUMMARY          = "/contract/jsp/caseGroupSummaryDialog.jsp";
    private static final String CASE_CHANGE_DIALOG          = "/contract/jsp/caseChangeDialog.jsp";
    private static final String ADD_CONTRACT_GROUP_DIALOG   = "/contract/jsp/addContractGroupDialog.jsp";
    private static final String CASE_SEARCH_DIALOG          = "/contract/jsp/caseSearchDialog.jsp";
    private static final String CASE_TRANSACTION_DIALOG     = "/contract/jsp/caseTransactionDialog.jsp";
    private static final String CASE_PRODUCT_ADD_DIALOG     = "/contract/jsp/caseProductAddDialog.jsp";
    private static final String GROUP_DETAIL_SUMMARY        = "/contract/jsp/groupDetailSummary.jsp";
    private static final String CASE_REQUIREMENTS           = "/contract/jsp/caseRequirements.jsp";
    private static final String CASE_AGENT                  = "/contract/jsp/caseAgent.jsp";
    private static final String CASE_AGT_SELECTION_DIALOG   = "/contract/jsp/caseAgentSelectionDialog.jsp";
    private static final String CASE_COMMISSION_OVERRIDES_DIALOG = "/contract/jsp/caseCommissionOverridesDialog.jsp";
    private static final String ADD_GROUP_DIALOG            = "/contract/jsp/addGroupDialog.jsp";
    private static final String GROUP_BILLING_DIALOG        = "/contract/jsp/groupBillingDialog.jsp";
    private static final String GROUP_PRD_DIALOG            = "/contract/jsp/groupPRDDialog.jsp";
    private static final String GROUP_DEPT_LOC_DIALOG       = "/contract/jsp/groupDeptLocDialog.jsp";
    private static final String CASE_HISTORY                = "/contract/jsp/caseHistory.jsp";
    private static final String CASE_HISTORY_FILTER_DIALOG  = "/contract/jsp/caseHistoryFilterDialog.jsp";
    private static final String PRD_REPORT_DIALOG           = "/contract/jsp/prdReportDialog.jsp";
    private static final String CASE_CLIENT_QUICK_ADD_DIALOG = "/contract/jsp/caseClientQuickAddDialog.jsp";
    private static final String CASE_MANUAL_REQUIREMENT_DIALOG = "/contract/jsp/caseManualRequirementSelectionDialog.jsp";
    private static final String CONTACT_INFORMATION_DIALOG = "/contract/jsp/contactInformationDialog.jsp";
    private static final String CASE_STATUS_CHANGE_HISTORY_DIALOG = "/contract/jsp/caseStatusChangeHistoryDialog.jsp";
    private static final String CASE_ENROLLMENT_DIALOG = "/contract/jsp/enrollmentDialog.jsp";
    private static final String PRODUCT_UNDERWRITING_DIALOG = "/contract/jsp/productUnderwritingDialog.jsp";
    private static final String MASTER_CONTRACT_INFO_DIALOG = "/contract/jsp/masterContractInfoDialog.jsp";
    private static final String PRD_CALENDAR = "/flex/PayrollDeductionCalendarApplication.jsp";
    private static final String EDITING_EXCEPTION_DIALOG = "/common/jsp/editingExceptionDialog.jsp";
    private static final String CASE_NOTES_DIALOG = "/contract/jsp/caseNotesDialog.jsp";
    private static final String ENROLLMENT_STATE_DIALOG = "/contract/jsp/enrollmentStateDialog.jsp";
    private static final String ENROLLMENT_LEAD_SERVICE_AGENT_DIALOG = "/contract/jsp/enrollmentLeadServiceAgentDialog.jsp";
    private static final String AGENT_SEARCH = "/search/jsp/agentSearch.jsp";
    private static final String CASE_SETUP_DIALOG = "/contract/jsp/caseSetupDialog.jsp";

    //  Transaction Include files
//    private static final String TRANSFER_TRANSACTION_INCLUDE = "/contract/jsp/caseTransactionTransferInclude.jsp";

    //  Template files
    private static final String TEMPLATE_MAIN = "/common/jsp/template/template-main.jsp";
    private static final String TEMPLATE_DIALOG = "/common/jsp/template/template-dialog.jsp";


    /**
     * NOTE: CompanyStructure and ProductStructure are used interchangeably
     */
    public CaseDetailTran()
    {

    }

    public String execute(AppReqBlock appReqBlock) throws Throwable
    {
        String action = appReqBlock.getReqParm("action");

        String returnPage = null;

        if (action.equals(SHOW_CASE_MAIN))
        {
            returnPage = showCaseMain(appReqBlock);
        }
        else if (action.equals(SHOW_ADD_CASE_ENTRY))
        {
            returnPage = showAddCaseEntry(appReqBlock);
        }
        else if (action.equals(SHOW_CASE_SEARCH_DIALOG))
        {
            returnPage = showCaseSearchDialog(appReqBlock);
        }
        else if (action.equals(SEARCH_CLIENTS))
        {
            returnPage = searchClients(appReqBlock);
        }
        else if (action.equals(SAVE_ADD_DIALOG))
        {
            returnPage = saveAddDialog(appReqBlock);
        }
        else if (action.equals(SHOW_CASE_PRODUCT_ADD))
        {
            returnPage = showCaseProductAdd(appReqBlock);
        }
        else if (action.equals(SHOW_GROUP_SUMMARY))
        {
            returnPage = showGroupSummary(appReqBlock);
        }
        else if (action.equals(SHOW_GROUP_DETAIL))
        {
            returnPage = showGroupDetail(appReqBlock);
        }
        else if (action.equals(LOCK_CURRENT_TAB))
        {
            returnPage = lockCurrentTab(appReqBlock);
        }
        else if (action.equals(CANCEL_CASE_ENTRY))
        {
            returnPage = cancelCaseEntry(appReqBlock);
        }
        else if (action.equals(SHOW_CASE_HISTORY))
        {
            returnPage = showCaseHistory(appReqBlock);
        }
        else if (action.equals(SHOW_CASE_HISTORY_FILTER))
        {
            returnPage = showCaseHistoryFilter(appReqBlock);
        }
        else if (action.equals(FILTER_CASE_HISTORY))
        {
            returnPage = filterCaseHistory(appReqBlock);
        }
        else if (action.equals(SHOW_PRD_DETAIL))
        {
            returnPage = showPRDDetail(appReqBlock);
        }
        else if (action.equals(SHOW_PRD_REPORT))
        {
            returnPage = showPRDReport(appReqBlock);
        }
        else if (action.equals(SHOW_CASE_TRANSACTION_DIALOG))
        {
            returnPage = showCaseTransactionDialog(appReqBlock);
        }
        else if (action.equals(SHOW_TRANSACTION_DETAIL))
        {
            returnPage = showTransactionDetail(appReqBlock);
        }
        else if (action.equals(SHOW_TRANSFER_TRANSACTION_DETAIL))
        {
            returnPage = showTransferTransactionDetail(appReqBlock);
        }
        else if (action.equals(ADD_TRANSFER))
        {
            returnPage = addTransfer(appReqBlock);
        }
        else if (action.equals(SAVE_TRANSFER))
        {
            returnPage = saveTransfer(appReqBlock);
        }
        else if (action.equals(DELETE_TRANSFER))
        {
            returnPage = deleteTransfer(appReqBlock);
        }
        else if (action.equals(SHOW_CASE_REQUIREMENTS))
        {
            returnPage=showCaseRequirements(appReqBlock);
        }
        else if (action.equals(SHOW_CASE_AGENTS))
        {
            returnPage=showCaseAgents(appReqBlock);
        }
        else if (action.equals(SHOW_AGENT_DETAIL_SUMMARY))
        {
            returnPage=showAgentDetailSummary(appReqBlock);
        }
        else if (action.equals(SHOW_COMMISSION_OVERRIDES))
        {
            returnPage=showCommissionOverrides(appReqBlock);
        }
        else if (action.equals(SHOW_AGENT_SELECTION_DIALOG))
        {
            returnPage=showAgentSelectionDialog(appReqBlock);
        }
        else if (action.equals(SHOW_REPORT_TO_AGENT))
        {
            returnPage=showReportToAgent(appReqBlock);
        }
        else if (action.equals(SAVE_AGENT_SELECTION))
        {
            returnPage=saveAgentSelection(appReqBlock);
        }
        else if (action.equals(CLOSE_AGENT_SELECTION_DIALOG))
        {
            returnPage=closeAgentSelectionDialog(appReqBlock);
        }
        else if (action.equals(CLEAR_AGENT_FORM))
        {
            returnPage=clearAgentForm(appReqBlock);
        }
        else if (action.equals(DELETE_SELECTED_AGENT))
        {
            returnPage=deleteSelectedAgent(appReqBlock);
        }
        else if (action.equals(SAVE_AGENT_TO_SUMMARY))
        {
            returnPage=saveAgentToSummary(appReqBlock);
        }
        else if (action.equals(SAVE_COMMISSION_OVERRIDES))
        {
            returnPage=saveCommissionOverrides(appReqBlock);
        }
        else if (action.equals(DELETE_COMMISSION_OVERRIDES))
        {
            returnPage=deleteCommissionOverrides(appReqBlock);
        }
        else if (action.equals(SHOW_GROUP_ADD))
        {
            returnPage=showGroupAdd(appReqBlock);
        }
        else if (action.equals(SHOW_GROUP_BILLING_DIALOG))
        {
            returnPage=showGroupBillingDialog(appReqBlock);
        }
        else if (action.equals(SAVE_GROUP_BILLING_CHANGE))
        {
            returnPage=saveGroupBillingChange(appReqBlock);
        }
        else if (action.equals(SHOW_GROUP_PRD_DIALOG))
        {
            returnPage=showGroupPRDDialog(appReqBlock);
        }
        else if (action.equals(SAVE_PRD_CHANGE))
        {
            returnPage = savePRDChange(appReqBlock);
        }
        else if (action.equals(SHOW_GROUP_DEPT_LOC_DIALOG))
        {
            returnPage=showGroupDeptLocDialog(appReqBlock);
        }
        else if (action.equals(REFRESH_GROUP_DEPT_LOC_DIALOG))
        {
            returnPage=refreshGroupDeptLocDialog(appReqBlock);
        }
        else if (action.equals(SHOW_DEPT_LOC_DETAIL))
        {
            returnPage=showDeptLocDetail(appReqBlock);
        }
        else if (action.equals(SAVE_DEPT_LOC_CHANGE))
        {
            returnPage=saveDeptLocChange(appReqBlock);
        }
        else if (action.equals(CLEAR_DEPT_LOC))
        {
            returnPage=clearDeptLoc(appReqBlock);
        }
        else if (action.equals(ADD_CLIENT_TROUGH_QUICK_ADD))
        {
          returnPage = addClientThroughQuickAdd(appReqBlock);
        }
        else if (action.equals(FIND_PRODUCT_KEYS_BY_COMPANY_NAME))
        {
          returnPage = findProductKeysByCompanyName(appReqBlock);
        }
        else if (action.equals(FIND_PRODUCT_KEYS_BY_BUSINESS_CONTRACT_NAME))
        {
          returnPage = findProductKeysByBusinessContractName(appReqBlock);
        }
        else if (action.equals(SAVE_CURRENT_PAGE))
        {
            returnPage = saveCurrentPage(appReqBlock);
        }
        else if (action.equals(SHOW_CASE_AFTER_SEARCH))
        {
          returnPage = showCaseAfterSearch(appReqBlock);
        }
        else if (action.equals(ADD_PRODUCT_TO_CONTRACT_GROUP))
        {
          returnPage = addProductToContractGroup(appReqBlock);
        }
        else if (action.equals(REMOVE_PRODUCT_FROM_CONTRACT_GROUP))
        {
            returnPage = removeProductFromContractGroup(appReqBlock);
        }
        else if (action.equals(SHOW_MANUAL_REQUIREMENT_SELECTION_DIALOG))
        {
            returnPage = showManualRequirementSelectionDialog(appReqBlock);
        }
        else if (action.equals(SHOW_MANUAL_REQUIREMENT_DESCRIPTION))
        {
            returnPage = showManualRequirementDescription(appReqBlock);
        }
        else if (action.equals(SAVE_MANUAL_REQUIREMENT))
        {
            returnPage = saveManualRequirement(appReqBlock);
        }
        else if (action.equals(DELETE_SELECTED_REQUIREMENT))
        {
            returnPage = deleteSelectedRequirement(appReqBlock);
        }
        else if (action.equals(SAVE_REQUIREMENT_TO_SUMMARY))
        {
            returnPage = saveRequirementToSummary(appReqBlock);
        }
        else if (action.equals(CANCEL_REQUIREMENT))
        {
             returnPage = cancelRequirement(appReqBlock);
        }
        else if (action.equals(SHOW_CONTRACTGROUPREQUIREMENT_DETAIL))
        {
            returnPage = showContractGroupRequirementDetail(appReqBlock);
        }
        else if (action.equals(DELETE_GROUP))
        {
            returnPage = deleteGroup(appReqBlock);
        }
        else if (action.equals(SHOW_CONTACT_INFORMATION_DIALOG))
        {
            returnPage = showContactInformationDialog(appReqBlock);
        }
        else if (action.equals(SHOW_CASE_STATUS_HISTORY_DIALOG))
        {
            returnPage = showCaseStatusChangeHistoryDialog(appReqBlock);
        }
        else if (action.equals(SHOW_ENROLLMENT_DIALOG))
        {
            returnPage = showEnrollmentDialog(appReqBlock);
        }
        else if (action.equals(SAVE_ENROLLMENT))
        {
            returnPage = saveEnrollment(appReqBlock);
        }
        else if (action.equals(SAVE_PROJECTED_BUSINESS))
        {
            returnPage = saveProjectedBusiness(appReqBlock);
        }
        else if (action.equals(SHOW_ENROLLMENT_DETAIL))
        {
            returnPage = showEnrollmentDetail(appReqBlock);
        }
        else if (action.equals(SHOW_PROJECTED_BUSINESS_DETAIL))
        {
            returnPage = showProjectedBusinessDetail(appReqBlock);
        }
        else if (action.equals(SHOW_PRODUCT_UNDERWRITING_DIALOG))
        {
            returnPage = showProductUnderwritingDialog(appReqBlock);
        }
        else if (action.equals(GET_UNDERWRITING_INFORMATION))
        {
            returnPage = getUnderwritingInformation(appReqBlock);
        }
        else if (action.equals(ADD_BASE_UNDERWRITING))
        {
            returnPage = addBaseUnderwriting(appReqBlock);
        }
        else if (action.equals(ADD_RIDER_UNDERWRITING))
        {
            returnPage = addRiderUnderwriting(appReqBlock);
        }
        else if (action.equals(ADD_OTHER_UNDERWRITING))
        {
            returnPage = addOtherUnderwriting(appReqBlock);
        }
        else if (action.equals(REMOVE_BASE_UNDERWRITING))
        {
            returnPage = removeBaseUnderwriting(appReqBlock);
        }
        else if (action.equals(REMOVE_RIDER_UNDERWRITING))
        {
            returnPage = removeRiderUnderwriting(appReqBlock);
        }
        else if (action.equals(REMOVE_OTHER_UNDERWRITING))
        {
            returnPage = removeOtherUnderwriting(appReqBlock);
        }
        else if (action.equals(SHOW_MASTER_CONTRACT_INFORMATION))
        {
            returnPage = showMasterContractInformation(appReqBlock);
        }
        else if (action.equals(SAVE_MASTER_CONTRACT_INFO_CHANGE))
        {
            returnPage = saveMasterContractInfoChange(appReqBlock);
        }
        else if (action.equals(SHOW_MASTER_CONTRACT_DETAIL))
        {
            returnPage = showMasterContractDetail(appReqBlock);
        }
        else if (action.equals(RESET_SELECTED_MASTER_CONTRACT_DETAIL))
        {
            returnPage = resetSelectedMasterContractDetail(appReqBlock);
        }
        else if (action.equals(SELECT_MASTER_CONTRACT_DETAIL))
        {
            returnPage = selectMasterContractDetail(appReqBlock);
        }
        else if (action.equals(REMOVE_MASTERCONTRACT_PRODUCT))
        {
            returnPage = removeMasterContractFromProduct(appReqBlock);
        }
        else if (action.equals(UPDATE_MASTER_CONTRACT_INFO_CHANGE))
        {
            returnPage = updateMasaterContractInfoChange(appReqBlock);
        }
        else if (action.equals(SHOW_PRDCALENDAR))
        {
            returnPage = showPRDCalendar(appReqBlock);
        }
        else if (action.equals(SHOW_EDITING_EXCEPTION_DIALOG))
        {
            returnPage = showEditingExceptionDialog(appReqBlock);
        }
        else if (action.equals(CANCEL_GROUP_ENTRY))
        {
            returnPage = cancelGroupEntry(appReqBlock);
        }
        else if (action.equals(SHOW_CASE_NOTES_DIALOG))
        {
            returnPage = showCaseNotes(appReqBlock);
        }
        else if (action.equals(SHOW_CASE_NOTE_DETAIL))
        {
            returnPage = showCaseNoteDetail(appReqBlock);
        }
        else if (action.equals(SAVE_CASE_NOTE_DETAIL))
        {
            returnPage = saveCaseNoteDetail(appReqBlock);
        }
        else if (action.equals(CANCEL_CASE_NOTE))
        {
            returnPage = cancelCaseNote(appReqBlock);
        }
        else if (action.equals(DELETE_CASE_NOTE))
        {
            returnPage = deleteCaseNote(appReqBlock);
        }
        else if (action.equals(ADD_ENROLLMENT))
        {
            returnPage = addEnrollment(appReqBlock);
        }
        else if (action.equals(DELETE_ENROLLMENT))
        {
            returnPage = deleteEnrollment(appReqBlock);
        }
        else if (action.equals(ADD_PROJECTED_BUSINESS))
        {
            returnPage = addProjectedBusiness(appReqBlock);
        }
        else if (action.equals(DELETE_PROJECTED_BUSINESS))
        {
            returnPage = deleteProjectedBusiness(appReqBlock);
        }
        else if (action.equals(SHOW_ENROLLMENT_STATE_DIALOG))
        {
            returnPage = showEnrollmentStateDialog(appReqBlock);
        }
        else if (action.equals(SAVE_ENROLLMENT_STATE))
        {
            returnPage = saveEnrollmentState(appReqBlock);
        }
        else if (action.equals(SHOW_ENROLLMENT_STATE_DETAIL))
        {
            returnPage = showEnrollmentStateDetail(appReqBlock);
        }
        else if (action.equals(DELETE_ENROLLMENT_STATE))
        {
            returnPage = deleteEnrollmentState(appReqBlock);
        }
        else if (action.equals(SHOW_ENROLLMENT_LEAD_SERVICE_AGENTS)) 
        {
            returnPage = showEnrollmentLeadServiceAgents(appReqBlock);    
        }
        else if (action.equals(SAVE_ENROLLMENT_LEAD_SERVICE_AGENT)) 
        {
            returnPage = saveEnrollmentLeadServiceAgent(appReqBlock);
        }
        else if(action.equals(ADD_ENROLLMENT_LEAD_SERVICE_AGENT)) 
        {
            returnPage = addEnrollmentLeadServiceAgent(appReqBlock);    
        }
        else if (action.equals(SHOW_ENROLLMENT_LEAD_SERVICE_AGENT_DETAILS)) 
        {
            returnPage = showEnrollmentLeadServiceAgentDetails(appReqBlock);    
        }
        else if (action.equals(CANCEL_ENROLLMENT_LEAD_SERVICE_AGENT)) 
        {
            returnPage = cancelEnrollmentLeadServiceAgent(appReqBlock);
        }
        else if (action.equals(DELETE_ENROLLMENT_LEAD_SERVICE_AGENT)) 
        {
            returnPage = deleteEnrollmentLeadServiceAgent(appReqBlock);    
        }
        else if (action.equals(SHOW_ENROLLMENT_LEAD_SERVICE_AGENTS_AFTER_SEARCH)) 
        {
            returnPage = showEnrollmentLeadServiceAgentsAfterSearch(appReqBlock);
        }
        else if (action.equals(UPDATE_ENROLLMENT_LEAD_SERVICE_AGENT))
        {
            returnPage = updateEnrollmentLeadServiceAgent(appReqBlock);
        }
        else if (action.equals(DELETE_CASE_ENTRY))
        {
            returnPage = deleteCaseEntry(appReqBlock);
        }
        else if (action.equals(SHOW_CASE_SETUP))
        {
            returnPage = showCaseSetup(appReqBlock);
        }
        else if (action.equals(SHOW_CASE_SETUP_DETAIL))
        {
            returnPage = showCaseSetupDetail(appReqBlock);
        }
        else if (action.equals(SAVE_CASE_SETUP))
        {
            returnPage = saveCaseSetup(appReqBlock);
        }
        else if (action.equals(DELETE_CASE_SETUP))
        {
            returnPage = deleteCaseSetup(appReqBlock);
        }
        else if (action.equals(CANCEL_CASE_SETUP))
        {
            returnPage = cancelCaseSetup(appReqBlock);
        }
        else
        {
            String transaction = appReqBlock.getReqParm("transaction");

            throw new RuntimeException("Unrecognized Transaction/Action [" + transaction + " / " + action + "]");
        }

        return returnPage;
    }


    /**
     * User is updating the data of the currently selected EnrollmentLeadServiceAgent.
     * @param appReqBlock
     * @return
     */
    private String updateEnrollmentLeadServiceAgent(AppReqBlock appReqBlock) 
    {
        String enrollmentLeadServiceAgentPK = appReqBlock.getReqParm("enrollmentLeadServiceAgentPK");
    
        String regionCT = Util.initString(appReqBlock.getReqParm("regionCT"), null); // could be null
        
        EDITDate effectiveDate = DateTimeUtil.getEDITDateFromMMDDYYYY(appReqBlock.getReqParm("uIEffectiveDate")); // required field
        
        EDITDate terminationDate = (Util.initString(appReqBlock.getReqParm("uITerminationDate"), null) != null)?DateTimeUtil.getEDITDateFromMMDDYYYY(appReqBlock.getReqParm("uITerminationDate")):null;
        
        try 
        {
        
            new EnrollmentLeadServiceAgentTableModel(appReqBlock).resetAllRows();
            
            new GroupComponent().updateEnrollmentLeadServiceAgent(new Long(enrollmentLeadServiceAgentPK), regionCT, effectiveDate, terminationDate);
            
            appReqBlock.putInRequestScope("pageMessage", new EDITList("Update of Enrollment Lead Service Agent was Successful / Changes to Role are Ignored"));
            
            appReqBlock.putInRequestScope("pageCommand", "resetForm");
        }
        catch (Exception e) 
        {
            EDITList messages = new EDITList("Unable to Update Enrollment Lead Service Agent [" + e.getMessage() + "]");
        
            appReqBlock.putInRequestScope("pageMessage", messages);                
        }
        
        return showEnrollmentLeadServiceAgents(appReqBlock);                        
    }
    
    /**
     * User has just used the agentSearch.jsp to identify the Agent and AgentNumber. The combination of
     * Agent, AgentNumber and RoleTypeCT = "Agent" (this is assumed), we know enough to identify a ClientRole.
     * @param appReqBlock
     * @return
     */
    private String showEnrollmentLeadServiceAgentsAfterSearch(AppReqBlock appReqBlock) 
    {
        String agentPK = appReqBlock.getReqParm("agentPK");
        
        String agentNumber = appReqBlock.getReqParm("agentNumber");
        
        String roleTypeCT = ClientRole.ROLETYPECT_AGENT; // assumed
        
        ClientRole clientRole = ClientRole.findBy_AgentPK_RoleTypeCT_ReferenceID(new Long(agentPK), roleTypeCT, agentNumber);
        
        ClientDetail clientDetail = clientRole.getClientDetail();
        
        appReqBlock.putInRequestScope("clientRole", clientRole);
        
        appReqBlock.putInRequestScope("clientDetail", clientDetail);
        
        return showEnrollmentLeadServiceAgents(appReqBlock);
    }

    /**
     * Deletes the EnrollmentLeadServiceAgent determined by the currently selected TableRow.
     * @param appReqBlock
     * @return
     */
    private String deleteEnrollmentLeadServiceAgent(AppReqBlock appReqBlock) 
    {
        String enrollmentLeadServiceAgentPK = new EnrollmentLeadServiceAgentTableModel(appReqBlock).getSelectedRowId();
        
        try 
        {
            new GroupComponent().deleteEnrollmentLeadServiceAgent(new Long(enrollmentLeadServiceAgentPK));
            
            appReqBlock.putInRequestScope("pageCommand", "resetForm");
            
            appReqBlock.putInRequestScope("pageMessage", new EDITList("Successfully Deleted Enrollment Lead Service Agent"));
        }
        catch (Exception e) 
        {
            EDITList messages = new EDITList("Unable to Delete Enrollment Lead Service Agent [" + e.getMessage() + "]");
            
            appReqBlock.putInRequestScope("pageMessage", messages);   
        }
        
        return showEnrollmentLeadServiceAgents(appReqBlock);
    }
    
    /**
     * Resets the dialog of any-user entered data.
     */
    private String cancelEnrollmentLeadServiceAgent(AppReqBlock appReqBlock) 
    {
        new EnrollmentLeadServiceAgentTableModel(appReqBlock).resetAllRows();
        
        appReqBlock.putInRequestScope("pageCommand", "resetForm");
        
        return showEnrollmentLeadServiceAgents(appReqBlock);
    }
    
    /**
     * Renders the details of the just-selected EnrollmentLeadServiceAgent.
     * @param appReqBlock
     * @return
     */
    private String showEnrollmentLeadServiceAgentDetails(AppReqBlock appReqBlock) 
    {
        EnrollmentLeadServiceAgentTableModel enrollmentLeadServiceAgentTableModel  = new EnrollmentLeadServiceAgentTableModel(appReqBlock);
        
        EnrollmentLeadServiceAgent enrollmentLeadServiceAgent = ((EnrollmentLeadServiceAgentTableRow) enrollmentLeadServiceAgentTableModel.getSelectedRow()).getEnrollmentLeadServiceAgent();
        
        ClientRole clientRole = enrollmentLeadServiceAgent.getClientRole();
        
        ClientDetail clientDetail = clientRole.getClientDetail();
    
        appReqBlock.putInRequestScope("enrollmentLeadServiceAgent", enrollmentLeadServiceAgent);
        
        appReqBlock.putInRequestScope("clientRole", clientRole);
        
        appReqBlock.putInRequestScope("clientDetail", clientDetail);
    
        return showEnrollmentLeadServiceAgents(appReqBlock);  
    }
    
    /**
     * Pops-up the same dialog as is used for Agent-Search (BA's wanted that same dialog and I didn't want to copy/paste).
     * I put a "flag" in the dialog so that the ultimate selection knows to target the existing Agent (like it normally did)
     * or the LeadServiceAgent they are trying to add (new stuff). GF
     * @param appReqBlock
     * @return
     */
    private String addEnrollmentLeadServiceAgent(AppReqBlock appReqBlock) 
    {
        appReqBlock.putInRequestScope("pageTarget", "EnrollmentLeadServiceAgent");
    
        return AGENT_SEARCH;       
    }
    
    /**
     * 
     * @param appReqBlock
     * @return
     */
    private String saveEnrollmentLeadServiceAgent(AppReqBlock appReqBlock) 
    {   
        Long enrollmentPK = new Long(appReqBlock.getReqParm("enrollmentPK"));
        
        Long clientRolePK = new Long(appReqBlock.getReqParm("clientRolePK"));
        
        Long agentPK = ClientRole.findByPK(clientRolePK).getAgent().getAgentPK();
        
        Long clientDetailPK = ClientRole.findByPK(clientRolePK).getClientDetail().getClientDetailPK();
        
        String roleTypeCT = appReqBlock.getReqParm("roleTypeCT");
        
        String regionCT = Util.initString(appReqBlock.getReqParm("regionCT"), null); // could be null
        
        String referenceID = Util.initString(appReqBlock.getReqParm("referenceID"), null); // could be null 
        
        EDITDate effectiveDate = DateTimeUtil.getEDITDateFromMMDDYYYY(appReqBlock.getReqParm("uIEffectiveDate")); // required field
        
        EDITDate terminationDate = (Util.initString(appReqBlock.getReqParm("uITerminationDate"), null) != null)?DateTimeUtil.getEDITDateFromMMDDYYYY(appReqBlock.getReqParm("uITerminationDate")):null;
        
        try 
        {
            new GroupComponent().saveEnrollmentLeadServiceAgent(regionCT, effectiveDate, terminationDate, enrollmentPK, agentPK, clientDetailPK, roleTypeCT, referenceID);
            
            appReqBlock.putInRequestScope("pageCommand", "resetForm");
            
            appReqBlock.putInRequestScope("pageMessage", new EDITList("Save of Enrollment Lead Service Agent was Successful"));
        }
        catch (EDITCaseException e) 
        {
            EDITList messages = new EDITList("Unable to Save Enrollment Lead Service Agent [" + e.getMessage() + "]");
        
            appReqBlock.putInRequestScope("pageMessage", messages);                
        }
        
        return showEnrollmentLeadServiceAgents(appReqBlock);
    }

    /**
     * Renders EnrollmentLeadServiceAgents for the currently selected Enrollment.
     * @param appReqBlock
     * @return
     */
    private String showEnrollmentLeadServiceAgents(AppReqBlock appReqBlock) 
    {
        EnrollmentLeadServiceAgentTableModel enrollmentLeadServiceAgentTableModel = new EnrollmentLeadServiceAgentTableModel(appReqBlock);
        
        appReqBlock.putInRequestScope("main", ENROLLMENT_LEAD_SERVICE_AGENT_DIALOG);
        
        appReqBlock.putInRequestScope("pageTitle", "Lead Service Agents");
    
        return TEMPLATE_DIALOG;        
    }

    /**
     * Renders the Flex popup for the PRD Calendar management.
     * @param appReqBlock
     * @return
     */
    private String showPRDCalendar(AppReqBlock appReqBlock)
    {
        return PRD_CALENDAR;         
    }
    
    /**
     * Renders the detail of a selected ContractGroupRequirement.
     * @param appReqBlock
     * @return
     */
    private String showContractGroupRequirementDetail(AppReqBlock appReqBlock)
    {
        return showCaseRequirements(appReqBlock);        
    }
    
    /**
     * Presents the dialog that allows the actor to add (additional) manual requirements in addition
     * to any automatically requirements previously auto-generated.
     * @param appReqBlock
     * @return
     */
    private String showManualRequirementSelectionDialog(AppReqBlock appReqBlock)
    {
        ProductStructure[] productStructure = ProductStructure.findBy_CompanyName("Case");

        Requirement[] requirements = Requirement.findBy_ProductStructure_And_ManualInd(productStructure[0], "Y");

        if (requirements != null)
        {
            appReqBlock.getHttpServletRequest().setAttribute("manualRequirements", requirements);
        }

        return CASE_MANUAL_REQUIREMENT_DIALOG;
    }

    private String showManualRequirementDescription(AppReqBlock appReqBlock)
    {
        String selectedRequirementPK = appReqBlock.getFormBean().getValue("requirementPK");
        appReqBlock.getHttpServletRequest().setAttribute("selectedRequirementPK", selectedRequirementPK);

        return showManualRequirementSelectionDialog(appReqBlock);
    }

    private String saveManualRequirement(AppReqBlock appReqBlock)
    {

        String activeCasePK = appReqBlock.getUserSession().getParameter("activeCasePK");

        String selectedRequirementPK = appReqBlock.getFormBean().getValue("selectedRequirementPK");
        ProductStructure[] productStructure = ProductStructure.findBy_CompanyName("Case");

        Requirement requirement = Requirement.findByPK(Long.parseLong(selectedRequirementPK));

        FilteredRequirement filteredRequirement = FilteredRequirement.findBy_ProductStructure_And_Requirement(productStructure[0], requirement);

        try
        {
            new GroupComponent().addRequirementToContractGroup(new Long(activeCasePK), requirement, filteredRequirement);
        }
        catch(EDITCaseException e)
        {
            appReqBlock.putInRequestScope("responseMessage", new EDITList(e.getMessage()));
        }

        return showCaseRequirements(appReqBlock);
    }

    private String deleteSelectedRequirement(AppReqBlock appReqBlock)
    {
        String activeCasePK = appReqBlock.getUserSession().getParameter("activeCasePK");

        TableModel model = new CaseRequirementsTableModel(appReqBlock);

        String selectedRequirementPK = Util.initString(model.getSelectedRowId(), null);

        try
        {
            new GroupComponent().deleteRequirementFromContractGroup(new Long(activeCasePK), new Long(selectedRequirementPK));

            appReqBlock.putInRequestScope("responseMessage", "Requirement was successfully deleted.");
        }
        catch(EDITCaseException e)
        {
            appReqBlock.putInRequestScope("responseMessage", new EDITList(e.getMessage()));
        }

        appReqBlock.putInRequestScope("resetForm", "YES");

        new CaseRequirementsTableModel(appReqBlock);

        return CASE_REQUIREMENTS;
    }

    private String saveRequirementToSummary(AppReqBlock appReqBlock)
    {
        ContractGroupRequirement contractGroupRequirement = (ContractGroupRequirement) Util.mapFormDataToHibernateEntity(appReqBlock.getHttpServletRequest(), ContractGroupRequirement.class, SessionHelper.EDITSOLUTIONS, false);

        try
        {
            new GroupComponent().saveRequirement(contractGroupRequirement);

            appReqBlock.putInRequestScope("responseMessage", "Save of Requirement was successful.");
        }
        catch (EDITCaseException e)
        {
            appReqBlock.putInRequestScope("responseMessage", e.toString());
        }

        appReqBlock.putInRequestScope("resetForm", "YES");

        UserSession usersession = appReqBlock.getUserSession();
        usersession.unlockCaseRequirement();

        new CaseRequirementsTableModel(appReqBlock);

        return CASE_REQUIREMENTS;
    }

    private String cancelRequirement(AppReqBlock appReqBlock)
    {
        appReqBlock.putInRequestScope("resetForm", "YES");
        appReqBlock.putInRequestScope("pageFunction", "");

        UserSession usersession = appReqBlock.getUserSession();
        usersession.unlockCaseRequirement();

        return showCaseRequirements(appReqBlock);
    }

    /**
     * Presents the dialog that allows the user to view contact information for the specified Case
     * @param appReqBlock
     * @return
     */
    private String showContactInformationDialog(AppReqBlock appReqBlock)
    {
        new ContactInformationTableModel(appReqBlock);

        return CONTACT_INFORMATION_DIALOG;
    }

    /**
     * Presents the dialog that allows the user to view status change history for the specified Case
     * @param appReqBlock
     * @return
     */
    private String showCaseStatusChangeHistoryDialog(AppReqBlock appReqBlock)
    {
        new CaseStatusChangeHistoryTableModel(appReqBlock);

        return CASE_STATUS_CHANGE_HISTORY_DIALOG;
    }

    /**
     * Presents the dialog that allows the user to view/update enrollment information for the specified Case
     * @param appReqBlock
     * @return
     */
    private String showEnrollmentDialog(AppReqBlock appReqBlock)
    {
        String activeEnrollmentPK = appReqBlock.getUserSession().getParameter("activeEnrollmentPK");

        if (activeEnrollmentPK != null) // An existing Enrollment
        {
            Enrollment enrollment = Enrollment.findByPK(new Long(activeEnrollmentPK));

            appReqBlock.putInRequestScope("enrollment", enrollment);

            String activeProjectedBusinessPK = (String) appReqBlock.getHttpServletRequest().getAttribute("activeProjectedBusinessPK");

            if (activeProjectedBusinessPK != null)
            {
                ProjectedBusinessByMonth projBusByMonth = ProjectedBusinessByMonth.findByPK(new Long(activeProjectedBusinessPK));

                appReqBlock.putInRequestScope("projectedBusinessByMonth", projBusByMonth);
            }
        }

        new EnrollmentTableModel(appReqBlock);

        new ProjectedBusinessTableModel(appReqBlock);

        return CASE_ENROLLMENT_DIALOG;
    }

    /**
     * Saves the current enrollment information for the specified Case
     * @param appReqBlock
     * @return
     */
    private String saveEnrollment(AppReqBlock appReqBlock)
    {
        Enrollment enrollment = (Enrollment) Util.mapFormDataToHibernateEntity(appReqBlock.getHttpServletRequest(), Enrollment.class, SessionHelper.EDITSOLUTIONS, false);

        String activeCasePK = appReqBlock.getUserSession().getParameter("activeCasePK");

        try
        {
            new GroupComponent().saveEnrollment(enrollment, new Long(activeCasePK));

            appReqBlock.putInRequestScope("responseMessage", "Save of Enrollment was successful.");
            appReqBlock.putInRequestScope("pageCommand", "resetForm");

        }
        catch (EDITCaseException e)
        {
            appReqBlock.putInRequestScope("responseMessage", e.toString());
        }

        return showEnrollmentDialog(appReqBlock);
    }

    /**
     * Show the enrollment selected from the summary in the detail portion of the page.
     * @param appReqBlock
     * @return
     */
    private String showEnrollmentDetail(AppReqBlock appReqBlock)
    {
        String activeEnrollmentPK = new EnrollmentTableModel(appReqBlock).getSelectedRowId();

        appReqBlock.getUserSession().setParameter("activeEnrollmentPK", activeEnrollmentPK);

        return showEnrollmentDialog(appReqBlock);
    }

    /**
     * Saves the currenct projected business by month information for the specified Case/Enrollment
     * @param appReqBlock
     * @return
     */
    private String saveProjectedBusiness(AppReqBlock appReqBlock)
    {
        ProjectedBusinessByMonth projectedBusByMonth = (ProjectedBusinessByMonth) Util.mapFormDataToHibernateEntity(appReqBlock.getHttpServletRequest(), ProjectedBusinessByMonth.class, SessionHelper.EDITSOLUTIONS, false);

        String activeEnrollmentPK = appReqBlock.getUserSession().getParameter("activeEnrollmentPK");

        try
        {
           //reset the projectBusiness field of NumberOfEligibles
            projectedBusByMonth.setProjBusNumberOfEligibles(projectedBusByMonth.getNumberOfEligibles());

            boolean duplicateDateFound = checkForDuplicateDate(projectedBusByMonth, activeEnrollmentPK);
            if (duplicateDateFound)
            {
                appReqBlock.putInRequestScope("responseMessage", "Duplicate Date Found, Add Not Allowed");
                projectedBusByMonth.setProjectedBusinessByMonthPK(new Long(0));
            }
            else
            {
                new GroupComponent().saveProjectedBusinessByMonth(projectedBusByMonth, new Long(activeEnrollmentPK));

                appReqBlock.putInRequestScope("responseMessage", "Save of Projected Business was successful");
                new ProjectedBusinessTableModel(appReqBlock).resetAllRows();
                appReqBlock.getHttpServletRequest().setAttribute("clearProjectedFields", "true");
            }
        }
        catch (EDITCaseException e)
        {
            appReqBlock.putInRequestScope("responseMessage", e.toString());
        }

        appReqBlock.putInRequestScope("projectedBusinessByMonth", projectedBusByMonth);

        return showEnrollmentDialog(appReqBlock);
    }

    private boolean checkForDuplicateDate(ProjectedBusinessByMonth projectedBusByMonth, String activeEnrollmentPK)
    {
        boolean duplicateDateFound = false;

        ProjectedBusinessByMonth[] dupProjectedBusinesses = ProjectedBusinessByMonth.findByEnrollment_Date(new Long(activeEnrollmentPK), projectedBusByMonth.getDate());
        Long projectedBusinessByMonthPK = projectedBusByMonth.getProjectedBusinessByMonthPK();

        if (dupProjectedBusinesses != null)
        {
            for (int i = 0; i < dupProjectedBusinesses.length; i++)
            {
                if (dupProjectedBusinesses[i].getProjectedBusinessByMonthPK() != projectedBusinessByMonthPK)
                {
                    duplicateDateFound = true;
                }
            }
        }
        return duplicateDateFound;
    }

    /**
     * Show the enrollment selected from the summary in the detail portion of the page.
     * @param appReqBlock
     * @return
     */
    private String showProjectedBusinessDetail(AppReqBlock appReqBlock)
    {
        String activeProjectedBusinessPK = new ProjectedBusinessTableModel(appReqBlock).getSelectedRowId();

        appReqBlock.getHttpServletRequest().setAttribute("activeProjectedBusinessPK", activeProjectedBusinessPK);

        return showEnrollmentDialog(appReqBlock);
    }

    /**
     * Presents the dialog that allows the user to view/update productUnderwriting information for the specified Case/Enrollment
     * @param appReqBlock
     * @return
     */
    private String showProductUnderwritingDialog(AppReqBlock appReqBlock)
    {
        String activeEnrollmentPK = appReqBlock.getUserSession().getParameter("activeEnrollmentPK");
        String filteredProductPK = (String) appReqBlock.getHttpServletRequest().getAttribute("filteredProductPK");

        appReqBlock.getHttpServletRequest().setAttribute("filteredProductPK", filteredProductPK);
        appReqBlock.getHttpServletRequest().setAttribute("activeEnrollmentPK", activeEnrollmentPK);

        getFilteredProducts(appReqBlock);

        getDepartmentLocations(appReqBlock);

        CaseProductUnderwriting[] caseProductUnderwriting = null;


        if (activeEnrollmentPK != null) // An existing Enrollment
        {
            Enrollment enrollment = Enrollment.findByPK(new Long(activeEnrollmentPK));

            appReqBlock.putInRequestScope("enrollment", enrollment);

            if (filteredProductPK != null)
            {
                caseProductUnderwriting = CaseProductUnderwriting.findByFilteredProductFK_EnrollmentFK(new Long(filteredProductPK), new Long(activeEnrollmentPK));
            }
        }

        new CandidateBaseUnderwritingTableModel(appReqBlock, filteredProductPK);

        new BaseUnderwritingTableModel(appReqBlock, caseProductUnderwriting);

        new CandidateRiderUnderwritingTableModel(appReqBlock, filteredProductPK);

        new RiderUnderwritingTableModel(appReqBlock, caseProductUnderwriting);

        new CandidateOtherUnderwritingTableModel(appReqBlock, filteredProductPK);

        new OtherUnderwritingTableModel(appReqBlock, caseProductUnderwriting);

        return PRODUCT_UNDERWRITING_DIALOG;
    }

    private String getUnderwritingInformation(AppReqBlock appReqBlock)
    {
        String selectedProductStructure = appReqBlock.getReqParm("selectedProductStructure");

        appReqBlock.getHttpServletRequest().setAttribute("filteredProductPK", selectedProductStructure);

        return showProductUnderwritingDialog(appReqBlock);
    }

    private void getFilteredProducts(AppReqBlock appReqBlock)
    {
        Hashtable filteredProductHT = new Hashtable();

        String activeCasePK = appReqBlock.getUserSession().getParameter("activeCasePK");

        FilteredProduct[] filteredProducts = FilteredProduct.findBy_ContractGroupPK(new Long(activeCasePK));

        if (filteredProducts != null)
        {
            for (int i = 0; i < filteredProducts.length; i++)
            {
                ProductStructure productStructure = filteredProducts[i].getProductStructure();

                filteredProductHT.put(filteredProducts[i].getFilteredProductPK().toString(), productStructure);
            }
        }

        appReqBlock.getHttpServletRequest().setAttribute("filteredProducts", filteredProductHT);
    }

    private void getDepartmentLocations(AppReqBlock appReqBlock)
    {
        String activeCasePK = appReqBlock.getUserSession().getParameter("activeCasePK");

        ContractGroup[] contractGroups = ContractGroup.findBy_ContractGroupFK_ContractGroupTypeCT(new Long(activeCasePK), ContractGroup.CONTRACTGROUPTYPECT_GROUP);

        Hashtable deptLocHT = new Hashtable();

        for (int i = 0; i < contractGroups.length; i++)
        {
            Set<DepartmentLocation> deptLocs = contractGroups[i].getDepartmentLocations();

            if (!deptLocs.isEmpty())
            {
                Iterator it = deptLocs.iterator();

                while (it.hasNext())
                {
                    DepartmentLocation deptLoc = (DepartmentLocation) it.next();

                    Long deptLocPK = deptLoc.getDepartmentLocationPK();

                    String groupNumber = contractGroups[i].getContractGroupNumber();

                    String deptLocCode_Name = deptLoc.getDeptLocCode() + "," + deptLoc.getDeptLocName();

                    deptLocHT.put(deptLocPK.toString(), groupNumber + " - " + deptLocCode_Name);
                }
            }
        }

        appReqBlock.getHttpServletRequest().setAttribute("deptLocs", deptLocHT);
    }

    private String addBaseUnderwriting(AppReqBlock appReqBlock)
    {
        String activeEnrollmentPK = appReqBlock.getUserSession().getParameter("activeEnrollmentPK");

        String filteredProductPK = appReqBlock.getReqParm("filteredProductPK");
        appReqBlock.getHttpServletRequest().setAttribute("filteredProductPK", filteredProductPK);

        CandidateBaseUnderwritingTableModel candidateBaseUnderwritingTableModel = new CandidateBaseUnderwritingTableModel(appReqBlock, filteredProductPK);

        String[] selectedPKs = candidateBaseUnderwritingTableModel.getSelectedRowIdsFromRequestScope();

        String relToEE = (String) appReqBlock.getReqParm("baseRelToEE");
        if (Util.isANumber(relToEE))
        {
            relToEE = CodeTableWrapper.getSingleton().getCodeTableEntry(Long.parseLong(relToEE)).getCode();
        }
        else
        {
            relToEE = null;
        }

        String deptLocFK = (String) appReqBlock.getReqParm("baseDeptLoc");
        if (!Util.isANumber(deptLocFK))
        {
            deptLocFK = null;
        }

        String value = (String) appReqBlock.getReqParm("baseValue");

        String includeOptionalCT = null;

        Group groupComponent = new GroupComponent();

        try
        {
            if (selectedPKs == null)
            {
                throw new EDITCaseException("Please Select Base Underwriting Value to Add");
            }
            for (String selectedAreaValuePK:selectedPKs)
            {
                groupComponent.addUnderwriting(new Long(selectedAreaValuePK), new Long(activeEnrollmentPK),
                                               new Long(filteredProductPK), relToEE, deptLocFK, value, includeOptionalCT);
            }
        }
        catch(EDITCaseException e)
        {
            appReqBlock.putInRequestScope("responseMessage", e.getMessage());
        }

        return showProductUnderwritingDialog(appReqBlock);
    }

    private String addRiderUnderwriting(AppReqBlock appReqBlock)
    {
        String activeEnrollmentPK = appReqBlock.getUserSession().getParameter("activeEnrollmentPK");

        String filteredProductPK = appReqBlock.getReqParm("filteredProductPK");
        appReqBlock.getHttpServletRequest().setAttribute("filteredProductPK", filteredProductPK);

        CandidateRiderUnderwritingTableModel candidateRiderUnderwritingTableModel = new CandidateRiderUnderwritingTableModel(appReqBlock, filteredProductPK);

        String[] selectedPKs = candidateRiderUnderwritingTableModel.getSelectedRowIdsFromRequestScope();

        String relToEE = (String) appReqBlock.getReqParm("riderRelToEE");
        if (Util.isANumber(relToEE))
        {
            relToEE = CodeTableWrapper.getSingleton().getCodeTableEntry(Long.parseLong(relToEE)).getCode();
        }
        else
        {
            relToEE = null;
        }

        String deptLocFK = (String) appReqBlock.getReqParm("riderDeptLoc");
        if (!Util.isANumber(deptLocFK))
        {
            deptLocFK = null;
        }

        String value = (String) appReqBlock.getReqParm("riderValue");

        String includeOptionalCT = (String) appReqBlock.getReqParm("riderInclOpt");
        if (Util.isANumber(includeOptionalCT))
        {
            includeOptionalCT = CodeTableWrapper.getSingleton().getCodeTableEntry(Long.parseLong(includeOptionalCT)).getCode();
        }
        else
        {
            includeOptionalCT = null;
        }

        Group groupComponent = new GroupComponent();

        try
        {
            if (selectedPKs == null)
            {
                throw new EDITCaseException("Please Select Rider Underwriting Value to Add");
            }
            for (String selectedAreaValuePK:selectedPKs)
            {
                groupComponent.addUnderwriting(new Long(selectedAreaValuePK), new Long(activeEnrollmentPK),
                                               new Long(filteredProductPK), relToEE, deptLocFK, value, includeOptionalCT);
            }
        }
        catch(EDITCaseException e)
        {
            appReqBlock.putInRequestScope("responseMessage", e.getMessage());
        }

        return showProductUnderwritingDialog(appReqBlock);
    }

    private String addOtherUnderwriting(AppReqBlock appReqBlock)
    {
        String activeEnrollmentPK = appReqBlock.getUserSession().getParameter("activeEnrollmentPK");

        String filteredProductPK = appReqBlock.getReqParm("filteredProductPK");
        appReqBlock.getHttpServletRequest().setAttribute("filteredProductPK", filteredProductPK);

        CandidateOtherUnderwritingTableModel candidateOtherUnderwritingTableModel = new CandidateOtherUnderwritingTableModel(appReqBlock, filteredProductPK);

        String[] selectedPKs = candidateOtherUnderwritingTableModel.getSelectedRowIdsFromRequestScope();

        String relToEE = null;

        String deptLocFK = null;

        String value = (String) appReqBlock.getReqParm("otherValue");

        String includeOptionalCT = null;

        Group groupComponent = new GroupComponent();

        try
        {
            if (selectedPKs == null)
            {
                throw new EDITCaseException("Please Select Other Underwriting Value to Add");
            }
            for (String selectedAreaValuePK:selectedPKs)
            {
                groupComponent.addUnderwriting(new Long(selectedAreaValuePK), new Long(activeEnrollmentPK),
                                               new Long(filteredProductPK), relToEE, deptLocFK, value, includeOptionalCT);
            }
        }
        catch(EDITCaseException e)
        {
            appReqBlock.putInRequestScope("responseMessage", e.getMessage());
        }

        return showProductUnderwritingDialog(appReqBlock);
    }

    private String removeBaseUnderwriting(AppReqBlock appReqBlock)
    {
        String filteredProductPK = appReqBlock.getReqParm("filteredProductPK");
        appReqBlock.getHttpServletRequest().setAttribute("filteredProductPK", filteredProductPK);

        String activeEnrollmentPK = appReqBlock.getReqParm("activeEnrollmentPK");
        appReqBlock.getHttpServletRequest().setAttribute("activeEnrollmentPK", activeEnrollmentPK);

        CaseProductUnderwriting[] caseProductUnderwriting = CaseProductUnderwriting.findByFilteredProductFK_EnrollmentFK(new Long(filteredProductPK), new Long(activeEnrollmentPK));

        BaseUnderwritingTableModel baseUnderwritingTableModel = new BaseUnderwritingTableModel(appReqBlock, caseProductUnderwriting);

        String[] selectedPKs = baseUnderwritingTableModel.getSelectedRowIdsFromRequestScope();

        removeUnderwriting(appReqBlock, selectedPKs);

        return showProductUnderwritingDialog(appReqBlock);
    }

    private String removeRiderUnderwriting(AppReqBlock appReqBlock)
    {
        String filteredProductPK = appReqBlock.getReqParm("filteredProductPK");
        appReqBlock.getHttpServletRequest().setAttribute("filteredProductPK", filteredProductPK);

        String activeEnrollmentPK = appReqBlock.getReqParm("activeEnrollmentPK");
        appReqBlock.getHttpServletRequest().setAttribute("activeEnrollmentPK", activeEnrollmentPK);

        CaseProductUnderwriting[] caseProductUnderwriting = CaseProductUnderwriting.findByFilteredProductFK_EnrollmentFK(new Long(filteredProductPK), new Long(activeEnrollmentPK));

        RiderUnderwritingTableModel riderUnderwritingTableModel = new RiderUnderwritingTableModel(appReqBlock, caseProductUnderwriting);

        String[] selectedPKs = riderUnderwritingTableModel.getSelectedRowIdsFromRequestScope();

        removeUnderwriting(appReqBlock, selectedPKs);

        return showProductUnderwritingDialog(appReqBlock);
    }

    private String removeOtherUnderwriting(AppReqBlock appReqBlock)
    {
        String filteredProductPK = appReqBlock.getReqParm("filteredProductPK");
        appReqBlock.getHttpServletRequest().setAttribute("filteredProductPK", filteredProductPK);

        String activeEnrollmentPK = appReqBlock.getReqParm("activeEnrollmentPK");
        appReqBlock.getHttpServletRequest().setAttribute("activeEnrollmentPK", activeEnrollmentPK);

        CaseProductUnderwriting[] caseProductUnderwriting = CaseProductUnderwriting.findByFilteredProductFK_EnrollmentFK(new Long(filteredProductPK), new Long(activeEnrollmentPK));

        OtherUnderwritingTableModel otherUnderwritingTableModel = new OtherUnderwritingTableModel(appReqBlock, caseProductUnderwriting);

        String[] selectedPKs = otherUnderwritingTableModel.getSelectedRowIdsFromRequestScope();

        removeUnderwriting(appReqBlock, selectedPKs);

        return showProductUnderwritingDialog(appReqBlock);
    }

    private void removeUnderwriting(AppReqBlock appReqBlock, String[] selectedPKs)
    {
        Group groupComponent = new GroupComponent();

        try
        {
            if (selectedPKs == null)
            {
                throw new EDITCaseException("Please Select Underwriting Value to Remove");
            }
            groupComponent.removeUnderwriting(selectedPKs);
        }
        catch(EDITCaseException e)
        {
            appReqBlock.putInRequestScope("responseMessage", e.getMessage());
        }
    }

    /**
    * Removes the selected ProductStructure from the current ContractGrouop
    * via the FilteredProduct association.
    * a FilteredProduct entry.
    * @param appReqBlock
    * @return
    */
    private String removeProductFromContractGroup(AppReqBlock appReqBlock)
    {
      FilteredProductTableModel filteredProductTableModel = new FilteredProductTableModel(appReqBlock, TableModel.SCOPE_REQUEST);
    
      String[] selectedProductsStructurePKs = filteredProductTableModel.getSelectedRowIdsFromRequestScope();
      
      String contractGroupPK = appReqBlock.getReqParm("contractGroupPK");
      
      Group groupComponent = new GroupComponent();
      
      try
      {
          for (String selectedProductStructurePK:selectedProductsStructurePKs)
          {
            groupComponent.removeProductStructureFromContractGroup(new Long(selectedProductStructurePK), new Long(contractGroupPK));
          }
      }
      catch (EDITContractException e)
      {
          appReqBlock.putInRequestScope("pageMessage", new EDITList(e.getMessage())); 
      }
      
      return showCaseProductAdd(appReqBlock);    
    }

    /**
   * Adds the selected ProductStructure to the current ContractGroup to create
   * a FilteredProduct entry.
   * @param appReqBlock
   * @return
   */
    private String addProductToContractGroup(AppReqBlock appReqBlock)
    {
          CandidateProductStructureTableModel  candidateProductStructureTableModel = new CandidateProductStructureTableModel(appReqBlock, TableModel.SCOPE_REQUEST);

          String[] selectedProductsStructurePKs = candidateProductStructureTableModel.getSelectedRowIdsFromRequestScope();

          String contractGroupPK = appReqBlock.getReqParm("contractGroupPK");

          String effDate = Util.initString(appReqBlock.getReqParm("uIFPEffectiveDate"), null);
            EDITDate effectiveDate = null;
            if (effDate != null)
            {
                effectiveDate = DateTimeUtil.getEDITDateFromMMDDYYYY(effDate);
            }

          String operator = appReqBlock.getUserSession().getUsername();

          Group groupComponent = new GroupComponent();

          try
          {
              for (String selectedProductStructurePK:selectedProductsStructurePKs)
              {
                groupComponent.addProductStructureToContractGroup(new Long(selectedProductStructurePK), new Long(contractGroupPK), effectiveDate, operator);
              }
          }
          catch (EDITContractException e)
          {
              appReqBlock.putInRequestScope("pageMessage", new EDITList(e.getMessage()));
          }

          appReqBlock.putInRequestScope("effectiveDate", "");
          appReqBlock.putInRequestScope("filteredProduct", null);
          appReqBlock.putInRequestScope("resetForm", "YES");

          return showCaseProductAdd(appReqBlock);
    }
    
    /**
   * Loads the main case page after searching for an existing case.
   * @param appReqBlock
   * @return
   */
    private String showCaseAfterSearch(AppReqBlock appReqBlock)
    {
      String activeCasePK = new CaseSearchTableModel(appReqBlock).getSelectedRowId(); 
      
      appReqBlock.getUserSession().setParameter("activeCasePK", activeCasePK);
      
      return showCaseMain(appReqBlock);
    }
    
    /**
   * Each tab in the tab set can be saved bypassing any validation rules. 
   * This determines the page being saved be examining the "pageName"
   * request parameter coming in. This "pageName" is assumed to be imbedded
   * as a hidden variable on each JSP page participating.
   * @param appReqBlock
   * @return
   */
    private String saveCurrentPage(AppReqBlock appReqBlock) throws SPException, PortalEditingException
    {    
      String returnPage = null;
    
      String pageName = appReqBlock.getReqParm("pageName");
      
      if (pageName.equals("caseMain"))
      {
        saveCase(appReqBlock);
        
        returnPage = showCaseMain(appReqBlock);
      }
      else if (pageName.equals("group"))
      {
        saveGroup(appReqBlock);  
        
        returnPage = showGroupSummary(appReqBlock);
      }
      else if (pageName.equals("requirement"))
      {
        saveRequirementToSummary(appReqBlock);

        returnPage = showCaseRequirements(appReqBlock);
      }
      else if (pageName.equals("caseAgent"))
      {
        saveAgentToSummary(appReqBlock);

        returnPage = showCaseAgents(appReqBlock);
      }

      return returnPage;
    }
    
    /**
    * Saves the state of the Group page without validation.
    * From the perspective of this page, ContractGroup as "Group" should also
    * contain an association with a ClientDetail - specifically, an
    * existing ClientDetail.
    *
    * @param appReqBlock
    */
    private void saveGroup(AppReqBlock appReqBlock) throws SPException, PortalEditingException
    {
        ContractGroup contractGroup = (ContractGroup) Util.mapFormDataToHibernateEntity(appReqBlock.getHttpServletRequest(), ContractGroup.class, SessionHelper.EDITSOLUTIONS, false);

        String contractGroupNumber = contractGroup.getContractGroupNumber();
        if (contractGroupNumber == null)
        {
            contractGroup.setContractGroupNumber(appReqBlock.getReqParm("contractGroupNUmber"));
        }

        String clientDetailPK = appReqBlock.getUserSession().getParameter("activeGroupClientDetailPK");
        
        String activeCasePK = appReqBlock.getUserSession().getParameter("activeCasePK");

        String operator = appReqBlock.getUserSession().getUsername();
        
        String pageFunction = Util.initString((String)appReqBlock.getReqParm("pageFunction"), null);
        appReqBlock.putInRequestScope("pageFunction", pageFunction);

        String ignoreEditWarnings = appReqBlock.getReqParm("ignoreEditWarnings");
        ignoreEditWarnings = (ignoreEditWarnings == null) ? "" : ignoreEditWarnings;

        if (contractGroup.getCreationOperator() == null)
        {
            contractGroup.setCreationOperator(operator);
            contractGroup.setCreationDate(new EDITDate());
        }

        EDITDate effDate = contractGroup.getEffectiveDate();
        if (effDate == null)
        {
            appReqBlock.putInRequestScope("responseMessage", "Please enter the Effective Date.");
            appReqBlock.putInRequestScope("pageFunction", pageFunction);
            appReqBlock.putInRequestScope("contractGroupNumber", contractGroupNumber);
        }
        else
        {
            if (!ignoreEditWarnings.equalsIgnoreCase("true"))
            {
                try
                {
                    validateGroup(contractGroup, null);
                }
                catch (PortalEditingException e)
                {
                    e.setReturnPage(CASE_MAIN);

                    throw e;
                }
            }

            try
            {
              new GroupComponent().saveGroup(contractGroup, new Long(activeCasePK), new Long(clientDetailPK));

              appReqBlock.getUserSession().setParameter("activeGroupPK", contractGroup.getContractGroupPK().toString());
              appReqBlock.getUserSession().unlockGroup();
              appReqBlock.putInRequestScope("pageFunction", "");
                
              appReqBlock.putInRequestScope("responseMessage", "Save of Group was successful.");
            }
            catch (EDITCaseException e)
            {
              appReqBlock.putInRequestScope("responseMessage", e.toString());
            }
        }
    }

    /**
    * Delete the specified group.
    *
    * @param appReqBlock
    */
    private String deleteGroup(AppReqBlock appReqBlock)
    {
        new CaseUseCaseComponent().deleteGroup();

        String activeGroupPK = appReqBlock.getUserSession().getParameter("activeGroupPK");

        String message = null;
        if (ContractGroup.doesGroupHaveBatchContracts(new Long(activeGroupPK)))
        {
            message = "Group Delete Not Allowed, Group Has Associated Batch Contracts";
            appReqBlock.putInRequestScope("responseMessage", message);
        }
        else if (ContractGroup.doesGroupHavePolicies(new Long(activeGroupPK))) 
        {
            message = "Group Delete Not Allowed, Group Has Associated Policies";
            appReqBlock.putInRequestScope("responseMessage", message);
        }
        else
        {
            try
            {
                ContractGroup contractGroup = (ContractGroup) Util.mapFormDataToHibernateEntity(appReqBlock.getHttpServletRequest(), ContractGroup.class, SessionHelper.EDITSOLUTIONS, false);
                message = new GroupComponent().deleteGroup(contractGroup);
                appReqBlock.putInRequestScope("responseMessage", message);
                appReqBlock.getUserSession().clearParameter("activeGroupPK");
                appReqBlock.getUserSession().clearParameter("activeGroupClientDetailPK");
            }
            catch (EDITCaseException e)
            {
                appReqBlock.putInRequestScope("responseMessage", e.toString());
            }
        }

        return showGroupSummary(appReqBlock);
    }

    private String cancelGroupEntry(AppReqBlock appReqBlock)
    {
        appReqBlock.putInRequestScope("resetForm", "YES");
        appReqBlock.putInRequestScope("pageFunction", "");

        UserSession usersession = appReqBlock.getUserSession();
        usersession.unlockGroup();

        return showGroupSummary(appReqBlock);
    }


    /**
   * Saves the state of the CaseMain page without validation.
   * From the perspective of this page, ContractGroup as "Case" should also
   * contain an association with a ClientDetail - specifically, an
   * existing ClientDetail.
   * 
   * @param appReqBlock
   */
    private void saveCase(AppReqBlock appReqBlock) throws SPException, PortalEditingException
    {
        ContractGroup contractGroup = (ContractGroup) Util.mapFormDataToHibernateEntity(appReqBlock.getHttpServletRequest(), ContractGroup.class, SessionHelper.EDITSOLUTIONS, false, false);

        appReqBlock.putInRequestScope("contractGroup", contractGroup);

        String contractGroupNumber = contractGroup.getContractGroupNumber();

        String clientDetailPK = appReqBlock.getUserSession().getParameter("activeCaseClientDetailPK");

        String operator = appReqBlock.getUserSession().getUsername();

        String statusChangeEffectiveDate = null;
        String origCaseStatus = appReqBlock.getHttpServletRequest().getParameter("origCaseStatus");
        appReqBlock.putInRequestScope("origCaseStatus", origCaseStatus);

        String pageFunction = Util.initString((String)appReqBlock.getReqParm("pageFunction"), null);
        appReqBlock.putInRequestScope("pageFunction", pageFunction);

        String ignoreEditWarnings = appReqBlock.getReqParm("ignoreEditWarnings");
        ignoreEditWarnings = (ignoreEditWarnings == null) ? "" : ignoreEditWarnings;

        if (contractGroup.getCreationOperator() == null)
        {
            contractGroup.setCreationOperator(operator);
            contractGroup.setCreationDate(new EDITDate());
        }

        boolean missingStatusChangeEffectiveDate = false;

        if (pageFunction != null && pageFunction.equalsIgnoreCase("change"))
        {
            if (contractGroup.getCaseStatusCT() != null && !origCaseStatus.equalsIgnoreCase(contractGroup.getCaseStatusCT()))
            {
                statusChangeEffectiveDate = appReqBlock.getReqParm("statusChangeEffectiveDate");
                statusChangeEffectiveDate = DateTimeUtil.formatMMDDYYYYToYYYYMMDD(statusChangeEffectiveDate);

                if (statusChangeEffectiveDate == null || statusChangeEffectiveDate.equals(""))
                {
                    missingStatusChangeEffectiveDate = true;
                }
            }
        }

        EDITDate effDate = contractGroup.getEffectiveDate();
        if (effDate == null)
        {
            appReqBlock.putInRequestScope("responseMessage", "Please enter the Effective Date.");
            appReqBlock.putInRequestScope("pageFunction", pageFunction);
            appReqBlock.putInRequestScope("contractGroupNumber", contractGroupNumber);
        }
        else if (missingStatusChangeEffectiveDate)
        {
            appReqBlock.putInRequestScope("responseMessage", "Please enter the Status Change Effective Date.");
            appReqBlock.putInRequestScope("pageFunction", pageFunction);
            appReqBlock.putInRequestScope("contractGroupNumber", contractGroupNumber);
        }
        else
        {
            if (!ignoreEditWarnings.equalsIgnoreCase("true"))
            {
                try
                {
                    validateCase(contractGroup);    
                }
                catch (PortalEditingException e)
                {
                    e.setReturnPage(CASE_MAIN);
                    new ProductSummaryTableModel(appReqBlock);

                    String activeCaseClientDetailPK = Util.initString(appReqBlock.getUserSession().getParameter("activeCaseClientDetailPK"), null);
                    ClientDetail clientDetail = ClientDetail.findByPK(new Long(activeCaseClientDetailPK));
                    ClientAddress clientAddress = clientDetail.getPrimaryAddress();
                    if (clientAddress == null)
                    {
                        clientAddress = clientDetail.getBusinessAddress();
                    }

                    appReqBlock.putInRequestScope("clientDetail", clientDetail);
                    appReqBlock.putInRequestScope("clientAddress", clientAddress);

                    throw e;
                }                
            }
        
            try
            {
                new GroupComponent().saveCase(contractGroup, new Long(clientDetailPK), statusChangeEffectiveDate, origCaseStatus, operator);

                appReqBlock.getUserSession().setParameter("activeCasePK", contractGroup.getContractGroupPK().toString());
                appReqBlock.getUserSession().unlockCase();

                appReqBlock.putInRequestScope("responseMessage", "Save of Case was successful.");
                appReqBlock.getHttpServletRequest().setAttribute("resetForm", "YES");
                appReqBlock.putInRequestScope("pageFunction", "");
                appReqBlock.putInRequestScope("origCaseStatus", "");

            }
            catch (EDITCaseException e)
            {
              appReqBlock.putInRequestScope("responseMessage", e.toString());
            }
        }
    }
    
    private void validateCase(ContractGroup contractGroup) throws SPException, PortalEditingException
    {
        Element contractGroupElement = contractGroup.getAsElement();

        Document document = new DefaultDocument("CaseDocVO");

        document.setRootElement(contractGroupElement);

        String effectiveDate = new EDITDate().getFormattedDate();
        
        ProductStructureVO[] productStructureVOs = new engine.component.LookupComponent().findProductStructureByNames("Case", "*", "*", "*", "*");
        long productKey = productStructureVOs[0].getProductStructurePK();
        
        SPOutput spOutput = new CalculatorComponent().processScriptWithDocument("ContractGroupVO", document, "CaseSave", "*", "*", effectiveDate, productKey, false);

        ValidationVO[] validationVOs = spOutput.getSPOutputVO().getValidationVO();

        if (spOutput.hasValidationOutputs())
        {
            PortalEditingException editingException = new PortalEditingException();

            editingException.setValidationVOs(validationVOs);

            throw editingException;
        }
    }
    
    private void validateGroup(ContractGroup contractGroup, HibernateEntity hibernateEntity) throws SPException, PortalEditingException
    {
        Element contractGroupElement = contractGroup.getAsElement();
        
        if (hibernateEntity != null)
        {
            Element hibernateEntityAsElement = hibernateEntity.getAsElement();
            
            contractGroupElement.add(hibernateEntityAsElement);
        }

        Document document = new DefaultDocument("GroupDocVO");

        document.setRootElement(contractGroupElement);

        String effectiveDate = new EDITDate().getFormattedDate();
        
        ProductStructureVO[] productStructureVOs = new engine.component.LookupComponent().findProductStructureByNames("Case", "*", "*", "*", "*");
        long productKey = productStructureVOs[0].getProductStructurePK();
        
        SPOutput spOutput = new CalculatorComponent().processScriptWithDocument("ContractGroupVO", document, "GroupSave", "*", "*", effectiveDate, productKey, false);

        ValidationVO[] validationVOs = spOutput.getSPOutputVO().getValidationVO();

        if (spOutput.hasValidationOutputs())
        {
            PortalEditingException editingException = new PortalEditingException();

            editingException.setValidationVOs(validationVOs);

            throw editingException;
        }
    }
    
    private String showEditingExceptionDialog(AppReqBlock appReqBlock)
    {
        PortalEditingException editingException = (PortalEditingException) appReqBlock.getHttpSession().getAttribute("portalEditingException");

        // Remove editingException from Session (to clear it), and move it to request scope.
        appReqBlock.getHttpSession().removeAttribute("portalEditingException");

        appReqBlock.getHttpServletRequest().setAttribute("portalEditingException", editingException);

        return EDITING_EXCEPTION_DIALOG;
    }
    
    /**
   * Leaving or going to a page is marked with an alias - not the actual physical
   * page. This maps the alias to the physical page.
   * @param pageAlias
   * @return
   */
    private String getTargetPage(String pageAlias)
    {
      String targetPage = null;
      
      if (CASE_MAIN.indexOf(pageAlias) >= 0)
      {
        targetPage = CASE_MAIN;
      }
      else if (CASE_GROUP_SUMMARY.indexOf(pageAlias) >= 0)
      {
        targetPage = CASE_GROUP_SUMMARY;
      }
      else if (CASE_REQUIREMENTS.indexOf(pageAlias) >= 0)
      {
        targetPage = CASE_REQUIREMENTS;
      }
      else if (CASE_AGENT.indexOf(pageAlias) >= 0)
      {
        targetPage = CASE_AGENT;
      }
      else if (CASE_HISTORY.indexOf(pageAlias) >= 0)
      {
        targetPage = CASE_HISTORY;
      }      
      
      return targetPage;
    }
    
  /**
   * After the "current" page is saved sans validation, the next page has to be determined.
   * This is done via some hard-coded mappings, and is limited to the set of tabs
   * in the Case work (i.e. CaseMain, GroupSummary, CaseRequirements, CaseAgents, CaseHistory).
   * 
   * @param pageToShow the alias for the fully qualified page to render next
   * @return
   */
    private String forwardPageToShow(AppReqBlock appReqBlock, String pageToShow)
    {
      if (CASE_MAIN.indexOf(pageToShow) >= 0)
      {
        return showCaseMain(appReqBlock);
      }
      else if (CASE_GROUP_SUMMARY.indexOf(pageToShow) >= 0)
      {
        return showGroupSummary(appReqBlock);
      }
      else if (CASE_REQUIREMENTS.indexOf(pageToShow) >= 0)
      {
        return showCaseRequirements(appReqBlock);
      }
      else if (CASE_AGENT.indexOf(pageToShow) >= 0)
      {
        return showCaseAgents(appReqBlock);
      }
      else if (CASE_HISTORY.indexOf(pageToShow) >= 0)
      {
        return showCaseHistory(appReqBlock);
      }
      
      return null;
    }
    
  /**
   * Finds the set of ProductStructures by the specified CompanyName. 
   * @param appReqBlock
   * @return
   */
    private String  findProductKeysByCompanyName(AppReqBlock appReqBlock)
    {
      return showCaseProductAdd(appReqBlock);
    }
    
    /**
   * Finds the set of ProductStructures by the specified BusinessContractName.
   * @param appReqBlock
   * @return
   */
    private String findProductKeysByBusinessContractName(AppReqBlock appReqBlock)
    {
      return showCaseProductAdd(appReqBlock);
    }
    
    /**
     * The user may be in the process of adding a Case, but the desired Client is not present.
     * The client information provided by the user will create a new Client record.  The Client information will be
     * validated via scripts.  If the validation is successful, the client will be persisted, otherwise, the script
     * messages will be displayed to the user
     *
     * @param appReqBlock
     *
     * @return
     *
     * @throws SPException
     * @throws PortalEditingException
     */
    private String addClientThroughQuickAdd(AppReqBlock appReqBlock) throws SPException, PortalEditingException
    {
        FormBean formBean = appReqBlock.getFormBean();

        String corporateName = formBean.getValue("corporateName");
        String taxId = Util.initString(formBean.getValue("quickAddTaxId"), null);
        String taxTypeId = Util.initString(formBean.getValue("taxTypeId"), null);
        String trustTypeId = Util.initString(formBean.getValue("trustTypeId"), null);

        String addressLine1 = formBean.getValue("addressLine1");
        String addressLine2 = formBean.getValue("addressLine2");
        String addressLine3 = formBean.getValue("addressLine3");
        String addressLine4 = formBean.getValue("addressLine4");
        String city = formBean.getValue("city");
        String state = Util.initString(formBean.getValue("areaId"), null);
        String zipCode = formBean.getValue("zipCode");

        String contactName = formBean.getValue("contactName");
        String contactTypeId = Util.initString(formBean.getValue("contactTypeId"), null);
        String phoneEmail = formBean.getValue("phoneEmail");
        String sicCodeId = Util.initString(formBean.getValue("sicCodeId"), null);

        SessionHelper.beginTransaction(ClientDetail.DATABASE);

        //  ClientDetail
        ClientDetail clientDetail = new ClientDetail();

        clientDetail.setDefaults();

        clientDetail.setCorporateName(corporateName);
        clientDetail.setName(corporateName);
        clientDetail.setTaxIdentification(taxId);
        clientDetail.setTrustTypeCT(trustTypeId);
        clientDetail.setSICCodeCT(sicCodeId);

        //  Tax Information
        if (taxTypeId != null)
        {
            TaxInformation taxInformation = new TaxInformation();

            clientDetail.setDefaultTaxes(taxInformation);

            taxInformation.setTaxIdTypeCT(taxTypeId);

            clientDetail.addTaxInformation(taxInformation);
        }

        //  ContractInformation
        if (contactTypeId != null)
        {
            ContactInformation contactInformation = new ContactInformation();
            contactInformation.setContactTypeCT(contactTypeId);
            contactInformation.setName(contactName);
            contactInformation.setPhoneEmail(phoneEmail);

            clientDetail.addContactInformation(contactInformation);
        }

        //  ClientAddress
        if (!addressLine1.equals(""))
        {
            ClientAddress clientAddress = new ClientAddress();

            clientAddress.setAddressDefaults(clientAddress.getClientAddressPK());

            clientAddress.setAddressTypeCT(ClientAddress.CLIENT_PRIMARY_ADDRESS);
            clientAddress.setAddressLine1(addressLine1);
            clientAddress.setAddressLine2(addressLine2);
            clientAddress.setAddressLine3(addressLine3);
            clientAddress.setAddressLine4(addressLine4);
            clientAddress.setCity(city);
            clientAddress.setStateCT(state);
            clientAddress.setZipCode(zipCode);

            clientDetail.addClientAddress(clientAddress);
        }

        //  Validate
        try
        {
            validateClient(clientDetail);
        }
        catch (PortalEditingException e)
        {
            String returnPage = displayQuickAddClient(appReqBlock, clientDetail, true);

            e.setReturnPage(returnPage);

            throw e;
        }
        
        //  Validation passed, persist the client
        clientDetail.hSave();
        SessionHelper.commitTransaction(ClientDetail.DATABASE);


        //  Look up the saved client and show is search results
        return displayQuickAddClient(appReqBlock, clientDetail, false);
    }

    /**
     * After a change if cancel is detected, the record will be unlocked
     * @param appReqBlock
     * @return
     */
    private String cancelCaseEntry(AppReqBlock appReqBlock)
    {
        appReqBlock.getHttpSession().removeAttribute("casePK");
        appReqBlock.getHttpServletRequest().removeAttribute("clientDetailPK");
        appReqBlock.getHttpServletRequest().removeAttribute("companyStructureId");
        appReqBlock.putInRequestScope("pageFunction", "");
        appReqBlock.getHttpServletRequest().removeAttribute("contractGroup");

        UserSession userSession = appReqBlock.getUserSession();

        userSession.unlockCase();

        return showCaseMain(appReqBlock);
    }

    /**
     *
     * @param appReqBlock
     * @return
     * @throws Exception
     */
    private String lockCurrentTab(AppReqBlock appReqBlock) throws Exception
    {
        String returnPage = null;

        String casePK = Util.initString(appReqBlock.getUserSession().getParameter("activeCasePK"), null);
        String activeGroupPK = Util.initString(appReqBlock.getUserSession().getParameter("activeGroupPK"), null);
        String pageName = appReqBlock.getReqParm("pageName");
        appReqBlock.setReqParm("casePK", casePK);
        appReqBlock.setReqParm("groupPK", activeGroupPK);
        appReqBlock.putInRequestScope("pageFunction", "change");

        UserSession userSession = appReqBlock.getUserSession();

       // security check
        if (pageName.equals("caseMain"))
        {
            new CaseUseCaseComponent().updateCaseDetail();

            if (casePK != null)
            {
                try
                {
                    userSession.lockCase(Long.parseLong(casePK));

                    returnPage = showCaseMain(appReqBlock);
                }
                catch (EDITLockException e)
                {
                    appReqBlock.getHttpServletRequest().setAttribute("responseMessage", e.getMessage());
                    returnPage = showCaseMain(appReqBlock);
                }
            }
            else
            {
                String message = "Case Selection Required";
                appReqBlock.getHttpServletRequest().setAttribute("responseMessage", message);
                returnPage = showCaseMain(appReqBlock);
            }
        }
        else if (pageName.equals("group"))
        {
            new CaseUseCaseComponent().updateGroup();
            if (activeGroupPK != null)
            {
                try
                {
                    userSession.lockGroup(Long.parseLong(activeGroupPK));

                    returnPage = showGroupSummary(appReqBlock);
                }
                catch (EDITLockException e)
                {
                    appReqBlock.getHttpServletRequest().setAttribute("responseMessage", e.getMessage());
                }
            }
            else
            {
                String message = "Group Selection Required";
                appReqBlock.getHttpServletRequest().setAttribute("responseMessage", message);
                returnPage = showGroupSummary(appReqBlock);
            }
        }
        else if (pageName.equals("requirement"))
        {
            new CaseUseCaseComponent().updateRequirement();

            String activeRequirementPK = new CaseRequirementsTableModel(appReqBlock).getSelectedRowId();
            if (activeRequirementPK != null)
            {
                try
                {
                    userSession.lockCaseRequirement(Long.parseLong(activeRequirementPK));

                    returnPage = showCaseRequirements(appReqBlock);
                }
                catch (EDITLockException e)
                {
                    appReqBlock.getHttpServletRequest().setAttribute("responseMessage", e.getMessage());
                }
            }
            else
            {
                String message = "Case Requirement Selection Required";
                appReqBlock.getHttpServletRequest().setAttribute("responseMessage", message);
                returnPage = showCaseRequirements(appReqBlock);
            }
        }
        else if (pageName.equals("caseAgent"))
        {
            String selectedAgentHierarchyPK = Util.initString(appReqBlock.getFormBean().getValue("selectedAgentHierarchyPK"), null);
            appReqBlock.getHttpServletRequest().setAttribute("selectedAgentHierarchyPK", selectedAgentHierarchyPK);

            if (selectedAgentHierarchyPK != null)
            {
                try
                {
                    userSession.lockCaseAgent(Long.parseLong(selectedAgentHierarchyPK));

                    returnPage = showCaseAgents(appReqBlock);
                }
                catch (EDITLockException e)
                {
                    appReqBlock.getHttpServletRequest().setAttribute("responseMessage", e.getMessage());
                }
            }
            else
            {
                String message = "Agent Selection Required";
                appReqBlock.getHttpServletRequest().setAttribute("responseMessage", message);
                returnPage = showCaseAgents(appReqBlock);
            }
        }

        return returnPage;
    }


    /**
     * Search button displays this page, caseSearchDialog.  Show list of Cases for the number or
     * name entered.  A partial name may be entered.
     * @param appReqBlock
     * @return
     */
    private String showCaseSearchDialog(AppReqBlock appReqBlock)
    {
        removeDataFromSession(appReqBlock);

        CaseSearchTableModel caseSearchTableModel = new CaseSearchTableModel(appReqBlock);

        return CASE_SEARCH_DIALOG;
    }

    /**
     * Case Main page is initially diplayed blank. The Add button on the toolbar can be used to add new Cases.
     * The Search button can be used to select an existing case.   Once a selection is made the detail is displayed.
     * Changes can be made to the existing data.  The Save button will create or update to Case table.
     * @param appReqBlock
     * @return
     */
    private String showCaseMain(AppReqBlock appReqBlock)
    {
        String activeCasePK = appReqBlock.getUserSession().getParameter("activeCasePK");
        
        String activeCaseClientDetailPK = Util.initString(appReqBlock.getUserSession().getParameter("activeCaseClientDetailPK"), null);
        
        if (activeCasePK != null) // An existing ContractGroup
        {
            ContractGroup caseContractGroup = ContractGroup.findBy_ContractGroupPK(new Long(activeCasePK));
          
            ClientDetail clientDetail = ClientDetail.findBy_ContractGroup(caseContractGroup);
          
            ClientAddress clientAddress = clientDetail.getPrimaryAddress();
            if (clientAddress == null)
            {
                clientAddress = clientDetail.getBusinessAddress();
            }

            appReqBlock.putInRequestScope("contractGroup", caseContractGroup);
            appReqBlock.putInRequestScope("contractGroupNumber" , caseContractGroup.getContractGroupNumber());
            appReqBlock.putInRequestScope("clientDetail", clientDetail);
          
            appReqBlock.putInRequestScope("clientAddress", clientAddress);
          
            activeCaseClientDetailPK = clientDetail.getClientDetailPK().toString();
        }
        
        if (activeCaseClientDetailPK != null) // The Client could have "just" been selected from a search dialog.
        {
            ClientDetail clientDetail = ClientDetail.findByPK(new Long(activeCaseClientDetailPK));
            clientDetail.setName(clientDetail.getCorporateName());

            ClientAddress clientAddress = clientDetail.getPrimaryAddress();
            if (clientAddress == null)
            {
                clientAddress = clientDetail.getBusinessAddress();
            }

            appReqBlock.putInRequestScope("clientDetail", clientDetail);
          
            appReqBlock.putInRequestScope("clientAddress", clientAddress);
          
            activeCaseClientDetailPK = clientDetail.getClientDetailPK().toString();
        }
        
        appReqBlock.getUserSession().setParameter("activeCaseClientDetailPK", activeCaseClientDetailPK);
        appReqBlock.getUserSession().clearParameter("activeGroupClientDetailPK");
        appReqBlock.getUserSession().clearParameter("activeGroupPK");
        appReqBlock.getUserSession().clearParameter("activeEnrollmentPK");

        new ProductSummaryTableModel(appReqBlock);

        return CASE_MAIN;
    }

    /**
     * The main Add Button activates this method.  Required Case/Group entry information is entered on this page.
     * @param appReqBlock
     * @return
     */
    private String showAddCaseEntry(AppReqBlock appReqBlock)
    {
        new CaseUseCaseComponent().addCaseDetail();

        removeDataFromSession(appReqBlock);

        new CaseClientSearchTableModel(appReqBlock);

        return ADD_CONTRACT_GROUP_DIALOG;
    }

    /**
     * During add of case/group, a client must be selected, this method gets Search Results for the Name or taxId entered.
     * @param appReqBlock
     * @return
     */
    private String searchClients(AppReqBlock appReqBlock)
    {
        CaseClientSearchTableModel model = new CaseClientSearchTableModel(appReqBlock);

       int rowCount = model.getRowCount();

       if (rowCount == 0)
       {
            appReqBlock.getHttpServletRequest().setAttribute("responseMessage", "No clients found matching search criteria.");
       }

        return ADD_CONTRACT_GROUP_DIALOG;
    }

    /**
     * Displays the client that was just saved via quick add for easy selection.  If the savedQuickAddClientDetail is
     * null, there was an error during validation and the client wasn't saved.  In that case, a parameter is set in
     * appReqBlock so the table model knows to display nothing.
     *
     * @param appReqBlock
     * @param savedQuickAddClientDetail         ClientDetail just saved using quick add.
     *
     * @return add dialog
     */

    private String displayQuickAddClient(AppReqBlock appReqBlock, ClientDetail clientDetail, boolean validationErrored)
    {
        if (validationErrored)
        {
            //  The validation errored, re-populate the fields on the dialog so the user may change his entries
            FormBean formBean = appReqBlock.getFormBean();

            appReqBlock.putInRequestScope("corporateName", formBean.getValue("corporateName"));
            appReqBlock.putInRequestScope("quickAddTaxId", formBean.getValue("quickAddTaxId"));
            appReqBlock.putInRequestScope("taxTypeId", formBean.getValue("taxTypeId"));
            appReqBlock.putInRequestScope("trustTypeId", formBean.getValue("trustTypeId"));

            appReqBlock.putInRequestScope("addressLine1", formBean.getValue("addressLine1"));
            appReqBlock.putInRequestScope("addressLine2", formBean.getValue("addressLine2"));
            appReqBlock.putInRequestScope("addressLine3", formBean.getValue("addressLine3"));
            appReqBlock.putInRequestScope("addressLine4", formBean.getValue("addressLine4"));
            appReqBlock.putInRequestScope("city", formBean.getValue("city"));
            appReqBlock.putInRequestScope("areaId", formBean.getValue("areaId"));
            appReqBlock.putInRequestScope("zipCode", formBean.getValue("zipCode"));

            appReqBlock.putInRequestScope("contactName", formBean.getValue("contactName"));
            appReqBlock.putInRequestScope("contactTypeId", formBean.getValue("contactTypeId"));
            appReqBlock.putInRequestScope("phoneEmail", formBean.getValue("phoneEmail"));
            appReqBlock.putInRequestScope("sicCodeId", formBean.getValue("sicCodeId"));
        }
        else
        {
            //  In the search results table, display the clientDetail that was just saved via quick add
            ClientDetail savedClientDetail = ClientDetail.findByPK(clientDetail.getClientDetailPK());

            appReqBlock.getHttpServletRequest().setAttribute("savedQuickAddClientDetail", savedClientDetail);
//            appReqBlock.getHttpServletRequest().setAttribute("validationErrored", validationErrored);
        }

        CaseClientSearchTableModel model = new CaseClientSearchTableModel(appReqBlock);
       
        return ADD_CONTRACT_GROUP_DIALOG;
    }

   /**
     * Identify the client to be used for the Case or Group. The Group
     * may choose to use the same client as was selected (previously) for
     * the Case.
     * @param appReqBlock
     * @return
     */
    private String saveAddDialog(AppReqBlock appReqBlock)
    {
        String pageName = appReqBlock.getReqParm("pageName");

       // security check
        if (pageName.equals("caseMain"))
        {
            new CaseUseCaseComponent().addCaseDetail();
        }
        else if (pageName.equals("group"))
        {
            new CaseUseCaseComponent().addGroupDetail();
        }

        String defaultClientFromCase = Util.initString(appReqBlock.getReqParm("defaultClientFromCase"), "off");
        
        String returnPage = null;

        String clientDetailPK = null; 
        
        // Use the same client as is on the Case.
        if (defaultClientFromCase.equals("on"))
        {
            String activeCasePK = appReqBlock.getUserSession().getParameter("activeCasePK");
        
            ContractGroup activeCase = ContractGroup.findBy_ContractGroupPK(new Long(activeCasePK));

            clientDetailPK = activeCase.getOwnerClient().getClientDetailPK().toString();
        }
        else
        {
            // Use the one just selected
             clientDetailPK = new CaseClientSearchTableModel(appReqBlock).getSelectedRowId();
        }

        String contractGroupNumber = Util.initString(appReqBlock.getReqParm("contractGroupNumber"), null);
        
        if (new GroupComponent().contractGroupExists(contractGroupNumber))
        {
          appReqBlock.putInRequestScope("responseMessage", "A Case/Group by the number [" + contractGroupNumber + "] already exists. Please add again.");
          
          // Abort the process by cancelling the Client just selected.
          clientDetailPK = null;
          
          if (pageName.equals("caseMain"))
          {
              appReqBlock.putInRequestScope("resetForm", "YES");
              
              appReqBlock.getUserSession().clearParameter("activeCasePK");
              
              appReqBlock.getUserSession().clearParameter("activeCaseClientDetailPK");              
          }
          else if (pageName.equals("group"))
          {
              appReqBlock.putInRequestScope("resetForm", "YES");
              
              appReqBlock.getUserSession().clearParameter("activeGroupPK");
              
              appReqBlock.getUserSession().clearParameter("activeGroupClientDetailPK");   
          }
        }

        if (pageName.equals("caseMain"))
        {
            appReqBlock.getUserSession().clearParameter("activeCasePK");
            
            appReqBlock.getUserSession().setParameter("activeCaseClientDetailPK", clientDetailPK);

            appReqBlock.getUserSession().setParameter("contractGroupNumber", contractGroupNumber);

            appReqBlock.putInRequestScope("pageFunction", "add");

            appReqBlock.putInRequestScope("contractGroupNumber", contractGroupNumber);

            returnPage = showCaseMain(appReqBlock);
        }
        else if (pageName.equals("group"))
        {
            appReqBlock.getUserSession().clearParameter("activeGroupPK");

            appReqBlock.getUserSession().setParameter("activeGroupClientDetailPK", clientDetailPK);

            appReqBlock.putInRequestScope("contractGroupNumber", contractGroupNumber);

            appReqBlock.putInRequestScope("pageFunction", "add");

            returnPage = showGroupSummary(appReqBlock);
        }

        
        return returnPage;
    }

    /**
   * The pop-up to map ProductStructures to the current Case (ContractGroup).
   * @param appReqBlock
   * @return
   * @throws EDITCaseException
   * @throws Exception
   */
    private String showCaseProductAdd(AppReqBlock appReqBlock)
    {
        CandidateProductStructureTableModel candidateProductStructureTableModel = new CandidateProductStructureTableModel(appReqBlock, TableModel.SCOPE_REQUEST);

        new FilteredProductTableModel(appReqBlock, TableModel.SCOPE_REQUEST);

        appReqBlock.putInRequestScope("main", CASE_PRODUCT_ADD_DIALOG);

        if (!candidateProductStructureTableModel.contractGroupExists()) {
            appReqBlock.putInRequestScope("pageMessage", new EDITList("There is no active Case present."));
        }

        return TEMPLATE_MAIN;
    }


    private String showMasterContractInformation(AppReqBlock appReqBlock) {
        ProductSummaryTableModel productSummaryTableModel = new ProductSummaryTableModel(appReqBlock);
        String selectedProduct = productSummaryTableModel.getSelectedRowId();
        FilteredProduct filteredProduct = new FilteredProduct();
        if (selectedProduct != null) {
            filteredProduct = FilteredProduct.findByPK(new Long(selectedProduct));
            appReqBlock.putInRequestScope("filteredProduct", filteredProduct);
            appReqBlock.getUserSession().setParameter("filteredProductPK", filteredProduct.getFilteredProductPK().toString());
            appReqBlock.putInRequestScope("productKey", filteredProduct.getProductKey());
            appReqBlock.getUserSession().setParameter("productKey",filteredProduct.getProductKey());
        }
       
        MasterContractTableModel tableModel = new MasterContractTableModel(appReqBlock);
        String selectedContract = tableModel.getSelectedRowId();
        if(selectedContract != null) {
	        MasterContract masterContract = MasterContract.findByPK(new Long(selectedContract));
	        appReqBlock.putInRequestScope("masterContract", masterContract);
        }

        return MASTER_CONTRACT_INFO_DIALOG;
    }

    private String saveMasterContractInfoChange(AppReqBlock appReqBlock) {
        String filteredProductPK = appReqBlock.getReqParm("filteredProductPK");
        String masterContractNumber = appReqBlock.getReqParm("masterContractNumber");
        String masterContractName = appReqBlock.getReqParm("masterContractName");
        String contractGroupPK = appReqBlock.getReqParm("contractGroupPK");
        String stateCT = appReqBlock.getReqParm("stateCT");
        String monthWindowString = Util.initString(appReqBlock.getReqParm("monthWindow"), "");
        String creationOperator = appReqBlock.getUserSession().getUsername();
        boolean noInterimCoverage = appReqBlock.getReqParm("noInterimCoverage") != null && 
        		appReqBlock.getReqParm("noInterimCoverage").equals("on");
        
        int monthWindow = 0;
        String all = "*";
        EDITDate masterContractEffectiveDate = null;
        EDITDate masterContractTerminationDate = null;
        FilteredProduct filteredProduct = null;
        MasterContract masterContract = null;
        
        if(stateCT == "")
        {
            stateCT = all;
        }
        if (Util.isANumber(monthWindowString)) {
            monthWindow = Integer.parseInt(monthWindowString);
        }

        String mcDate = Util.initString(appReqBlock.getReqParm("uIMCEffectiveDate"), null);
        if (mcDate != null) {
            masterContractEffectiveDate = DateTimeUtil.getEDITDateFromMMDDYYYY(mcDate);
        }

        String mcTDate = Util.initString(appReqBlock.getReqParm("uIMCTerminationDate"), null);
        if (mcTDate != null) {
            masterContractTerminationDate = DateTimeUtil.getEDITDateFromMMDDYYYY(mcTDate);
        }
        try {
            filteredProduct = new GroupComponent().saveFilteredProductInformation(new Long(filteredProductPK), monthWindow);
            masterContract = new GroupComponent().saveMasterContractInformationNew(new Long(filteredProductPK), new Long(contractGroupPK), masterContractNumber, 
            		masterContractName, masterContractEffectiveDate, masterContractTerminationDate, stateCT, noInterimCoverage, creationOperator);

            appReqBlock.putInRequestScope("filteredProduct", filteredProduct);
            appReqBlock.putInRequestScope("masterContract", masterContract);
            appReqBlock.putInRequestScope("masterContractEffectiveDate", masterContractEffectiveDate);
            appReqBlock.putInRequestScope("masterContractTerminationDate", masterContractTerminationDate);
            appReqBlock.putInRequestScope("creationDate", masterContract.getCreationDate());
            appReqBlock.putInRequestScope("creationOperator", creationOperator);
            appReqBlock.putInRequestScope("productKey", filteredProduct.getProductKey());
        } catch (EDITCaseException e) {
            appReqBlock.putInRequestScope("responseMessage", e.getMessage());
        }
        return showMasterContractInformation(appReqBlock);
    }

    private String updateMasaterContractInfoChange(AppReqBlock appReqBlock) throws EDITContractException {

        String masterContractPK = new MasterContractTableModel(appReqBlock).getSelectedRowId();
        if (masterContractPK != null) {
            MasterContract masterContract = new MasterContract().findByPK(Long.parseLong(masterContractPK));
            masterContract.setMasterContractName(appReqBlock.getReqParm("masterContractName"));
            masterContract.setMasterContractNumber(appReqBlock.getReqParm("masterContractNumber"));
            masterContract.setStateCT(appReqBlock.getReqParm("stateCT"));
            
            String brandingCompany = appReqBlock.getReqParm("brandingCompanyCT");
            if (brandingCompany != null && (brandingCompany.equals("") || brandingCompany.equalsIgnoreCase("Please Select"))) {
            	brandingCompany = null;
            }
            masterContract.setBrandingCompanyCT(brandingCompany);

            boolean noInterimCoverage = appReqBlock.getReqParm("noInterimCoverage") != null && 
            		appReqBlock.getReqParm("noInterimCoverage").equals("on");
            masterContract.setNoInterimCoverage(noInterimCoverage);
            FilteredProduct filteredProduct = new FilteredProduct().findByPK(masterContract.getFilteredProductFK());
            appReqBlock.getUserSession().setParameter("productKey", filteredProduct.getProductKey());

            if (!appReqBlock.getReqParm("uIMCEffectiveDate").equals("")) {
                masterContract.setMasterContractEffectiveDate(new EDITDate(DateTimeUtil.formatMMDDYYYYToYYYYMMDD(appReqBlock.getReqParm("uIMCEffectiveDate"))));
            }
            if (!appReqBlock.getReqParm("uIMCTerminationDate").equals("")) {
                masterContract.setMasterContractTerminationDate(new EDITDate(DateTimeUtil.formatMMDDYYYYToYYYYMMDD(appReqBlock.getReqParm("uIMCTerminationDate"))));
            }

            try {
                SessionHelper.beginTransaction(SessionHelper.EDITSOLUTIONS);

                SessionHelper.saveOrUpdate(masterContract, SessionHelper.EDITSOLUTIONS);
                
                SessionHelper.commitTransaction(SessionHelper.EDITSOLUTIONS);
                appReqBlock.putInRequestScope("productKey", filteredProduct.getProductKey());
            } catch (Exception e) {
                System.out.println(e);

                e.printStackTrace();

                SessionHelper.rollbackTransaction(SessionHelper.EDITSOLUTIONS);

                throw new EDITContractException(e.getMessage());
            } finally {
                SessionHelper.clearSessions();
            }


        } else {
            saveMasterContractInfoChange(appReqBlock);
        }
        return showMasterContractInformation(appReqBlock);
    }


    private String removeMasterContractFromProduct(AppReqBlock appReqBlock) {
        String masterContractPK = new MasterContractTableModel(appReqBlock).getSelectedRowId();
        String filteredProductPK = appReqBlock.getReqParm("filteredProductPK");
        appReqBlock.getUserSession().setParameter("masterContractPK", masterContractPK);
        FilteredProduct filteredProduct = new FilteredProduct().findByPK(Long.parseLong(filteredProductPK));
        
        MasterContract masterContract = new MasterContract();
        Segment[] segments = Segment.findByMasterContractFK(Long.parseLong(masterContractPK));
                
        try {
            if (masterContractPK != null && (segments == null)) {
                masterContract.removeSelectedMasterContract(Long.parseLong(masterContractPK)); 
           }
            else if(segments!= null){
                appReqBlock.putInRequestScope("shouldShowDeleteAlert", "true");
            }
             appReqBlock.getUserSession().setParameter("productKey", filteredProduct.getProductKey());
             appReqBlock.putInRequestScope("productKey", filteredProduct.getProductKey());
        } catch (EDITContractException e) {
            appReqBlock.putInRequestScope("pageMessage", new EDITList(e.getMessage()));
        }
        return showMasterContractInformation(appReqBlock);
    }

    private String showMasterContractDetail(AppReqBlock appReqBlock) {

        String activeCasePK = appReqBlock.getUserSession().getParameter("activeCasePK");

        FilteredProductTableModel filteredproductTableModel = new FilteredProductTableModel(appReqBlock, TableModel.SCOPE_REQUEST);

        String selectedProduct = filteredproductTableModel.getSelectedRowId();

        FilteredProduct filteredProduct = FilteredProduct.findBy_ProductStructurePK_ContractGroupPK(new Long(selectedProduct), new Long(activeCasePK));

        appReqBlock.putInRequestScope("filteredProduct", filteredProduct);

        return showCaseProductAdd(appReqBlock);
    }
    
    private String resetSelectedMasterContractDetail(AppReqBlock appReqBlock) {

        new MasterContractTableModel(appReqBlock).resetAllRows();
        
        String productKey = appReqBlock.getUserSession().getParameter("productKey");
        appReqBlock.putInRequestScope("productKey", productKey);
        return MASTER_CONTRACT_INFO_DIALOG;
    }
    private String selectMasterContractDetail(AppReqBlock appReqBlock) {

        String masterContractPK = new MasterContractTableModel(appReqBlock).getSelectedRowId();

        MasterContract masterContract = new MasterContract().findByPK(Long.parseLong(masterContractPK));
        FilteredProduct filteredProduct = FilteredProduct.findByPK(masterContract.getFilteredProductFK());
        if (masterContractPK != null) {
            appReqBlock.putInRequestScope("masterContract", masterContract);
            appReqBlock.putInRequestScope("productKey", filteredProduct.getProductKey());
        }
        return MASTER_CONTRACT_INFO_DIALOG;
    }

    /**
     * Remove from session at the start of the add or search processing
     * @param appReqBlock
     */
    private void removeDataFromSession(AppReqBlock appReqBlock)
    {
        appReqBlock.getHttpSession().removeAttribute("casePK");
    }

    /**
     * Show the Group Summary Dialog for the Case selected on the main page.  
     * Groups can be added, changed and deleted here.
     * @param appReqBlock
     * @return
     */
    private String showGroupSummary(AppReqBlock appReqBlock)
    {
        String activeCasePK = appReqBlock.getUserSession().getParameter("activeCasePK");

        String activeGroupClientDetailPK = null;

        if (activeCasePK != null)
        {
            String activeGroupPK = appReqBlock.getUserSession().getParameter("activeGroupPK");

            activeGroupClientDetailPK = Util.initString(appReqBlock.getUserSession().getParameter("activeGroupClientDetailPK"), null);

            if (activeGroupPK != null) // An existing ContractGroup
            {
                ContractGroup groupContractGroup = ContractGroup.findBy_ContractGroupPK(new Long(activeGroupPK));

                ClientDetail clientDetail = ClientDetail.findBy_ContractGroup(groupContractGroup);
              
                ClientAddress clientAddress = clientDetail.getPrimaryAddress();
                if (clientAddress == null)
                {
                    clientAddress = clientDetail.getBusinessAddress();
                }

                activeGroupClientDetailPK = clientDetail.getClientDetailPK().toString();

                appReqBlock.putInRequestScope("contractGroup", groupContractGroup);
                appReqBlock.putInRequestScope("contractGroupNumber", groupContractGroup.getContractGroupNumber());

                appReqBlock.putInRequestScope("clientDetail", clientDetail);
              
                appReqBlock.putInRequestScope("clientAddress", clientAddress);
            }
            else if (activeGroupClientDetailPK != null) // The Client could have "just" been selected from a search dialog.
            {
                ClientDetail clientDetail = ClientDetail.findByPK(new Long(activeGroupClientDetailPK));
              
                ClientAddress clientAddress = clientDetail.getPrimaryAddress();
                if (clientAddress == null)
                {
                    clientAddress = clientDetail.getBusinessAddress();
                }

                appReqBlock.putInRequestScope("clientDetail", clientDetail);
              
                appReqBlock.putInRequestScope("clientAddress", clientAddress);
            }
        }
        else
        {
            appReqBlock.putInRequestScope("responseMessage", "There is no active Case loaded.");
        }

//        appReqBlock.putInRequestScope("contractGroupNumber", appReqBlock.getReqParm("contractGroupNumber"));
        appReqBlock.getUserSession().setParameter("activeGroupClientDetailPK", activeGroupClientDetailPK);

        new GroupSummaryTableModel(appReqBlock);

        return GROUP_DETAIL_SUMMARY;
    }

    /**
     * Show the group selected from the summary in the detail protion of the page.
     * @param appReqBlock
     * @return
     */
    private String showGroupDetail(AppReqBlock appReqBlock)
    {
        String activeGroupPK = new GroupSummaryTableModel(appReqBlock).getSelectedRowId();

        appReqBlock.getUserSession().setParameter("activeGroupPK", activeGroupPK);

        boolean groupLocked = appReqBlock.getUserSession().getGroupIsLocked();
        if (groupLocked)
        {
            appReqBlock.getUserSession().unlockGroup();   
        }

        return showGroupSummary(appReqBlock);
    }

    /**
     * Displays the case transaction page for the first time.
     * @param appReqBlock
     * @return
     */
    private String showCaseTransactionDialog(AppReqBlock appReqBlock)
    {
        String transactionTypeCT = "TF";        // default to Transfer transaction

//      String caseNumber = appReqBlock.getHttpServletRequest().setAttribute("caseNumber", appReqBlock.getReqParm("caseNumber"));
        String caseNumber = "66666";     // dummy to provide for testing

        new CaseTransactionTableModel(appReqBlock);

        String returnPage = setCaseTransactionPageInfo(appReqBlock, caseNumber, transactionTypeCT, null);

        return returnPage;
    }

    /**
     * Displays the case transaction page when a user selects a transaction in the summary.  The transaction's detailed
     * information is displayed.
     *
     * @param appReqBlock
     * @return
     */
    private String showTransactionDetail(AppReqBlock appReqBlock)
    {
        String selectedEDITTrxPK = new CaseTransactionTableModel(appReqBlock).getSelectedRowId();

        EDITTrx editTrx = EDITTrx.findBy_PK(new Long(selectedEDITTrxPK));

        String caseNumber = appReqBlock.getReqParm("caseNumber");

        String returnPage = setCaseTransactionPageInfo(appReqBlock, caseNumber, editTrx.getTransactionTypeCT(), editTrx);

        return returnPage;
    }

    private String showTransferTransactionDetail(AppReqBlock appReqBlock)
    {
        TableRow selectedTableRow = new CaseTransactionTransferTableModel(appReqBlock).getSelectedRow();

        appReqBlock.putInRequestScope("selectedTableRow", selectedTableRow);

        String selectedEDITTrxPK = new CaseTransactionTableModel(appReqBlock).getSelectedRowId();

        EDITTrx editTrx = EDITTrx.findBy_PK(new Long(selectedEDITTrxPK));

        String caseNumber = appReqBlock.getReqParm("caseNumber");

        String returnPage = setCaseTransactionPageInfo(appReqBlock, caseNumber, editTrx.getTransactionTypeCT(), editTrx);

        return returnPage;
    }

    /**
     * Sets all the necessary variables into scope for the case transaction page.
     *
     * @param appReqBlock               request variable which will be modified to hold the case transaction info
     * @param caseNumber                number of the case to be looked up in persistence and passed to the page
     * @param transactionTypeCT         specific type of transaction which determines what gets displayed in the page
     * @param editTrx                   transaction whose info gets displayed once selected
     *
     * @return  the name of the page to be displayed
     */
    private String setCaseTransactionPageInfo(AppReqBlock appReqBlock, String caseNumber, String transactionTypeCT, EDITTrx editTrx)
    {
        String pageTitle = "Case Transaction";  // main page title
//
//        Case caseDetail = Case.findBy_CaseNumber(caseNumber);
//
//        setAppropriateTransactionDetailPageInfo(appReqBlock, transactionTypeCT, pageTitle);
//
//        //  Set objects to display
//        appReqBlock.putInRequestScope("caseDetail", caseDetail);
//        appReqBlock.putInRequestScope("editTrx", editTrx);          // null the first time in
//
//        //  Set appropriate page to display
//        appReqBlock.putInRequestScope("main", CASE_TRANSACTION_DIALOG);
//        appReqBlock.putInRequestScope("caseTransactionTableModel", "CaseTransactionTableModel");

        return TEMPLATE_MAIN;
    }

    /**
     * Determines which transaction include page and table model to display based on the transaction type.  The include
     * page is the transaction specific information and is included in the overall case transaction page.
     * <P>
     * At this time, only Transfer (TF) transaction types are supported.
     *
     * @param appReqBlock               request variable which will be modified to hold the transaction detail info
     * @param transactionTypeCT         specific transaction type which determines which page and table model will be displayed
     * @param pageTitle                 title for the overall case transaction page.  The transaction type is appended.
     * @param fund                      fund whose detail is to be displayed
     */
//    private void setAppropriateTransactionDetailPageInfo(AppReqBlock appReqBlock, String transactionTypeCT, String pageTitle)
//    {
//        if (transactionTypeCT.equals("TF"))
//        {
//            new CaseTransactionTransferTableModel(appReqBlock);
//            appReqBlock.getHttpServletRequest().setAttribute("transactionTypeCT", transactionTypeCT);
//            appReqBlock.getHttpServletRequest().setAttribute("transactionIncludePage", TRANSFER_TRANSACTION_INCLUDE);
//            appReqBlock.getHttpServletRequest().setAttribute("transactionTableModel", "CaseTransactionTransferTableModel");
//            appReqBlock.getHttpServletRequest().setAttribute("pageTitle", pageTitle + " - Transfer");
//        }
//    }

    private String addTransfer(AppReqBlock appReqBlock)
    {
        //  Set info for page and display
        String selectedEDITTrxPK = new CaseTransactionTableModel(appReqBlock).getSelectedRowId();

        EDITTrx editTrx = EDITTrx.findBy_PK(new Long(selectedEDITTrxPK));

        String caseNumber = appReqBlock.getReqParm("caseNumber");

        String returnPage = setCaseTransactionPageInfo(appReqBlock, caseNumber, editTrx.getTransactionTypeCT(), editTrx);

        return returnPage;
    }

    private String saveTransfer(AppReqBlock appReqBlock)
    {
        //  Get entered values
        String fundID = (String) appReqBlock.getFormBean().getDisplayValues().get("fundID");
        String fromTo = (String) appReqBlock.getFormBean().getDisplayValues().get("fromTo");
        String percent = (String) appReqBlock.getFormBean().getDisplayValues().get("percent");
        String dollars = (String) appReqBlock.getFormBean().getDisplayValues().get("dollars");
        String units = (String) appReqBlock.getFormBean().getDisplayValues().get("units");

        //  Build objects and persist


        //  Set info for page and display
        String selectedEDITTrxPK = new CaseTransactionTableModel(appReqBlock).getSelectedRowId();

        EDITTrx editTrx = EDITTrx.findBy_PK(new Long(selectedEDITTrxPK));

        String caseNumber = appReqBlock.getReqParm("caseNumber");

        String returnPage = setCaseTransactionPageInfo(appReqBlock, caseNumber, editTrx.getTransactionTypeCT(), editTrx);

        return returnPage;
    }

    private String deleteTransfer(AppReqBlock appReqBlock)
    {
        //  Get selected row
        TableRow selectedTableRow = new CaseTransactionTransferTableModel(appReqBlock).getSelectedRow();

        //  Delete objects from persistence

        //  Remove row from table

        //  Set info for page and display
        String selectedEDITTrxPK = new CaseTransactionTableModel(appReqBlock).getSelectedRowId();

        EDITTrx editTrx = EDITTrx.findBy_PK(new Long(selectedEDITTrxPK));

        String caseNumber = appReqBlock.getReqParm("caseNumber");

        String returnPage = setCaseTransactionPageInfo(appReqBlock, caseNumber, editTrx.getTransactionTypeCT(), editTrx);

        return returnPage;
    }

    /**
     * Renders the requirements tab for the current Case.
     * @param appReqBlock
     * @return
     */
    private String showCaseRequirements(AppReqBlock appReqBlock)
    {
        TableModel model = new CaseRequirementsTableModel(appReqBlock);
        
        String contractGroupRequirementPKStr = Util.initString(model.getSelectedRowId(), null);
        
        if (contractGroupRequirementPKStr != null)
        {
            ContractGroupRequirement contractGroupRequirement = ContractGroupRequirement.findBy_ContractGroupRequirementPK(new Long(contractGroupRequirementPKStr));
            
            Requirement requirement = contractGroupRequirement.getFilteredRequirement().getRequirement();
            
            appReqBlock.putInRequestScope("contractGroupRequirement", contractGroupRequirement);
            
            appReqBlock.putInRequestScope("requirement", requirement);

            appReqBlock.putInRequestScope("batchID", contractGroupRequirement.getBatchContractSetup().getBatchID());
        }

        return CASE_REQUIREMENTS;
    }

    private String showCaseAgents(AppReqBlock appReqBlock)
    {
        try
        {
            //remove old hierarchies from session
            appReqBlock.getHttpSession().setAttribute("uiAgentHierarchyVOs", null);
            loadAgentHierarchies(appReqBlock);
        }
        catch (Exception e)
        {
            appReqBlock.putInRequestScope("responseMessage", e.toString());
        }

        return CASE_AGENT;
    }

    private String showAgentDetailSummary(AppReqBlock appReqBlock)
    {
        String selectedAgentHierarchyPK = appReqBlock.getFormBean().getValue("selectedAgentHierarchyPK");
        appReqBlock.getHttpServletRequest().setAttribute("selectedAgentHierarchyPK", selectedAgentHierarchyPK);

        return showCaseAgents(appReqBlock);
    }

    private String showCommissionOverrides(AppReqBlock appReqBlock)
    {
        PageBean formBean = appReqBlock.getFormBean();

        String selectedAgentHierarchyPK = formBean.getValue("selectedAgentHierarchyPK");
        String selectedAgentSnapshotPK = formBean.getValue("selectedAgentSnapshotPK");
        String selectedCommProfileFK = formBean.getValue("selectedCommProfileFK");
        String selectedCommProfileOvrdFK = Util.initString(formBean.getValue("selectedCommProfileOvrdFK"), null);

        appReqBlock.getHttpServletRequest().setAttribute("selectedAgentHierarchyPK", selectedAgentHierarchyPK);
        appReqBlock.getHttpServletRequest().setAttribute("selectedAgentSnapshotPK", selectedAgentSnapshotPK);
        appReqBlock.getHttpServletRequest().setAttribute("selectedCommProfileFK", selectedCommProfileFK);
        appReqBlock.getHttpServletRequest().setAttribute("selectedCommProfileOvrdFK", selectedCommProfileOvrdFK);

        AgentSnapshot agentSnapshot = AgentSnapshot.findBy_PK(new Long(selectedAgentSnapshotPK));
        PlacedAgent placedAgent = agentSnapshot.getPlacedAgent();
        AgentContract agentContract = placedAgent.getAgentContract();

        CommissionProfile[] commissionProfiles = CommissionProfile.findBy_ContractCodeCT(agentContract.getContractCodeCT());

        appReqBlock.getHttpServletRequest().setAttribute("commissionProfiles", commissionProfiles);

        return CASE_COMMISSION_OVERRIDES_DIALOG;
    }

    private String showAgentSelectionDialog(AppReqBlock appReqBlock)
    {
        appReqBlock.getHttpSession().removeAttribute("selectedAgentVO");
        appReqBlock.getHttpSession().removeAttribute("placedAgentBranchVOs");

        return CASE_AGT_SELECTION_DIALOG;
    }

    private String showReportToAgent(AppReqBlock appReqBlock) throws Exception
    {
        String agentNumber = appReqBlock.getFormBean().getValue("agentId");
        String activeCasePK = appReqBlock.getUserSession().getParameter("activeCasePK");

        ContractGroup caseContractGroup = ContractGroup.findBy_ContractGroupPK(new Long(activeCasePK));

        PlacedAgentBranchVO[] placedAgentBranchVOs = getPlacedAgentBranches(agentNumber, caseContractGroup.getEffectiveDate().toString());

        if (placedAgentBranchVOs != null && placedAgentBranchVOs.length > 0)
        {
            long agentContractFK = placedAgentBranchVOs[0].getPlacedAgentVO(placedAgentBranchVOs[0].getPlacedAgentVOCount()-1).getAgentContractFK();

            AgentContract agentContract = AgentContract.findBy_AgentContractPK(new Long(agentContractFK));
            Agent agent = agentContract.getAgent();
            AgentVO agentVO = (AgentVO) agent.getVO();

            appReqBlock.getHttpSession().setAttribute("selectedAgentVO", agentVO);
            appReqBlock.getHttpSession().setAttribute("placedAgentBranchVOs", placedAgentBranchVOs);
        }
        else
        {
            appReqBlock.getHttpServletRequest().setAttribute("agentMessage", "Selected Agent Not Available [Verify PlacedAgent?]");
        }

        return CASE_AGT_SELECTION_DIALOG;
    }

    private PlacedAgentBranchVO[] getPlacedAgentBranches(String agentNumber, String effectiveDate) throws Exception
    {
        agent.business.Agent agentComponent = new AgentComponent();

        if(agentNumber != null)
        {
            return agentComponent.getBranchesByAgentNumberAndExpirationDate(agentNumber, effectiveDate);
        }
        
        return null;
    }

    private String saveAgentSelection(AppReqBlock appReqBlock)
    {
        String selectedPlacedAgentPK = appReqBlock.getFormBean().getValue("selectedPlacedAgentPK");

        AgentVO agentVO = (AgentVO) appReqBlock.getHttpSession().getAttribute("selectedAgentVO");
        PlacedAgentBranchVO[] placedAgentBranchVOs = (PlacedAgentBranchVO[]) appReqBlock.getHttpSession().getAttribute("placedAgentBranchVOs");
        String message = checkCommissionProfileOption(placedAgentBranchVOs, selectedPlacedAgentPK);

        String activeCasePK = appReqBlock.getUserSession().getParameter("activeCasePK");

        ContractGroup contractGroup = ContractGroup.findBy_ContractGroupPK(new Long(activeCasePK));

        try
        {
            new GroupComponent().createCaseAgentHierarchy(contractGroup, agentVO, placedAgentBranchVOs, selectedPlacedAgentPK);

            if (message == null)
            {
                message = "Selected Agent was added to Case";
            }

            appReqBlock.putInRequestScope("responseMessage", message);
        }
        catch (EDITCaseException e)
        {
            appReqBlock.putInRequestScope("responseMessage", e.toString());
        }

        return showCaseAgents(appReqBlock);
    }

    private String checkCommissionProfileOption(PlacedAgentBranchVO[] placedAgentBranchVOs, String selectedPlacedAgentPK)
    {
        boolean optionFound = false;
        for (int i = 0; i < placedAgentBranchVOs.length; i++)
        {
            int paCount = placedAgentBranchVOs[i].getPlacedAgentVOCount();
            int paIndex =  paCount - 1; // we want the Writing agent which is the last PlacedAgent in the branch.
//            if (paCount > 1)
//            {
//                paIndex = paCount - 2;
//            }
//            else
//            {
//                paIndex = paCount - 1;
//            }
            if ((placedAgentBranchVOs[i].getPlacedAgentVO(paIndex).getPlacedAgentPK() + "").equals(selectedPlacedAgentPK))
            {
                PlacedAgentVO[] placedAgentVOs = (PlacedAgentVO[])placedAgentBranchVOs[i].getPlacedAgentVO();
                for (int j = 0; j < placedAgentVOs.length; j++)
                {
                    PlacedAgent placedAgent = (PlacedAgent)SessionHelper.map(placedAgentVOs[j], SessionHelper.EDITSOLUTIONS);
                    PlacedAgentCommissionProfile placedAgentCommissionProfile = PlacedAgentCommissionProfile.findBy_PlacedAgent_Date(placedAgent, new EDITDate());
                    CommissionProfile commissionProfile = CommissionProfile.findBy_PK(placedAgentCommissionProfile.getCommissionProfileFK());
                    String commissionOption = Util.initString(commissionProfile.getCommissionOptionCT(), "");
                    if (commissionOption.equalsIgnoreCase("SB") ||
                        commissionOption.equalsIgnoreCase("IS") ||
                        commissionOption.equalsIgnoreCase("PY"))
                    {
                        optionFound = true;
                        break;
                    }
                }
            }
        }

        String message = null;

        if (optionFound)
        {
            message = "Selected Agent was added to Case. Enter Advance/Recovery % for option 'SB', 'IS', 'PY'.";
        }

        return message;
    }

    private String closeAgentSelectionDialog(AppReqBlock appReqBlock) throws Exception
    {
        appReqBlock.getHttpSession().removeAttribute("selectedAgentVO");
        appReqBlock.getHttpSession().removeAttribute("placedAgentBranchVOs");

        return showCaseAgents(appReqBlock);
    }

    private String clearAgentForm(AppReqBlock appReqBlock)
    {
       UserSession usersession = appReqBlock.getUserSession();
       usersession.unlockCaseAgent();
        appReqBlock.putInRequestScope("pageFunction", "");

       return showCaseAgents(appReqBlock);
    }

    private String deleteSelectedAgent(AppReqBlock appReqBlock)
    {

        String message ="";

        String selectedAgentHierarchyPK = appReqBlock.getReqParm("selectedAgentHierarchyPK");

        String activeCasePK = appReqBlock.getUserSession().getParameter("activeCasePK");

        SelectedAgentHierarchy[] selectedAgentHierarchy = SelectedAgentHierarchy.findByAgentHierarchyFK(new Long(selectedAgentHierarchyPK));

        ContractGroup contractGroup = ContractGroup.findBy_ContractGroupPK(new Long(activeCasePK));



        if (selectedAgentHierarchy == null)
        {
            try
            {
                new GroupComponent().deleteCaseAgentHierarchy(contractGroup, selectedAgentHierarchyPK);

                message = "Selected Agent was deleted from Case.";

                appReqBlock.putInRequestScope("responseMessage",message );
            }

            catch (EDITCaseException e)
            {
                appReqBlock.putInRequestScope("responseMessage",  e.toString());
            }
        }
        else
        {
            message = "Agent Cannot be Deleted";

            appReqBlock.putInRequestScope("responseMessage",message );
        }


        return showCaseAgents(appReqBlock);
    }

    private String saveAgentToSummary(AppReqBlock appReqBlock)
    {

        String splitPercent = appReqBlock.getFormBean().getValue("splitPercent");
        String selectedAgentHierarchyPK = appReqBlock.getFormBean().getValue("selectedAgentHierarchyPK");
        String region = Util.initString(appReqBlock.getFormBean().getValue("region"), null);
        String stopDate = Util.initString(appReqBlock.getFormBean().getValue("stopDate"), null);

        AgentHierarchy agentHierarchy = AgentHierarchy.findByPK(new Long(selectedAgentHierarchyPK));

        agentHierarchy.setRegion(region);

        Set<AgentHierarchyAllocation> agtHierAllocs = agentHierarchy.getAgentHierarchyAllocations();

        if (!agtHierAllocs.isEmpty())
        {
            Iterator it = agtHierAllocs.iterator();

            AgentHierarchyAllocation agentHierarchyAllocation = (AgentHierarchyAllocation) it.next();

            agentHierarchyAllocation.setAllocationPercent(new EDITBigDecimal(splitPercent));
            
            agentHierarchyAllocation.setStopDate(DateTimeUtil.getEDITDateFromMMDDYYYY(stopDate));
        }

        Set<AgentSnapshot> agentSnapshots = agentHierarchy.getAgentSnapshots();
        String errorMessage = null;
        if (!agentSnapshots.isEmpty())
        {
            for (Iterator iterator = agentSnapshots.iterator(); iterator.hasNext();)
            {
                AgentSnapshot agentSnapshot = (AgentSnapshot) iterator.next();
                PlacedAgent placedAgent = PlacedAgent.findBy_PK_V1(agentSnapshot.getPlacedAgentFK());
                CommissionProfile commissionProfile = placedAgent.getActivePlacedAgentCommissionProfile().getCommissionProfile();
                errorMessage = new UtilitiesForTran().editCommissionProfile(commissionProfile.getCommissionProfilePK(), agentSnapshot.getAdvancePercent(), agentSnapshot.getRecoveryPercent(), null);
            }
        }

        if (errorMessage == null)
        {
            try
            {
                new GroupComponent().saveCaseAgentHierarchyUpdate(agentHierarchy);

                appReqBlock.putInRequestScope("responseMessage", "Agent change was saved successfully.");

                UserSession usersession = appReqBlock.getUserSession();
                usersession.unlockCaseAgent();
            }
            catch (EDITCaseException e)
            {
                appReqBlock.putInRequestScope("responseMessage", e.toString());
            }
        }
        else
        {
            appReqBlock.putInRequestScope("responseMessage", errorMessage);
        }

        return showCaseAgents(appReqBlock);
    }

    private String saveCommissionOverrides(AppReqBlock appReqBlock)
    {
        PageBean formBean = appReqBlock.getFormBean();

        UIAgentHierarchyVO[] uiAgentHierarchyVOs = (UIAgentHierarchyVO[])appReqBlock.getHttpSession().getAttribute("uiAgentHierarchyVOs");
        String selectedAgentHierarchyPK = formBean.getValue("selectedAgentHierarchyPK");
        String selectedAgentSnapshotPK = formBean.getValue("selectedAgentSnapshotPK");
        String selectedCommProfileFK = formBean.getValue("selectedCommProfileFK");
        String selectedCommProfileOvrdFK = Util.initString(formBean.getValue("selectedCommProfileOvrdFK"), null);
        String servicingAgentIndicator = formBean.getValue("servicingAgentIndicator");
        String advancePercent = Util.initString(appReqBlock.getFormBean().getValue("advancePercent"), null);
        String recoveryPercent = Util.initString(appReqBlock.getFormBean().getValue("recoveryPercent"), null);

        String errorMessage = null;
        EDITBigDecimal advPercent = new EDITBigDecimal("0");
        EDITBigDecimal recPercent = new EDITBigDecimal("0");
        if (advancePercent != null)
        {
            advPercent= new EDITBigDecimal(advancePercent);
        }
        if (recoveryPercent != null)
        {
            recPercent = new EDITBigDecimal(recoveryPercent);
        }

        if (selectedCommProfileOvrdFK != null)
        {


            errorMessage = new UtilitiesForTran().editCommissionProfile(new Long(selectedCommProfileOvrdFK), advPercent, recPercent, null);
        }
        else
        {
            AgentSnapshot agentSnapshot = AgentSnapshot.findBy_PK(new Long(selectedAgentSnapshotPK));
            PlacedAgent placedAgent = PlacedAgent.findBy_PK_V1(agentSnapshot.getPlacedAgentFK());
            CommissionProfile commissionProfile = placedAgent.getActivePlacedAgentCommissionProfile().getCommissionProfile();
            errorMessage = new UtilitiesForTran().editCommissionProfile(commissionProfile.getCommissionProfilePK(), advPercent, recPercent, null);
        }

        if (errorMessage == null)
        {
            appReqBlock.getHttpServletRequest().setAttribute("selectedAgentHierarchyPK", selectedAgentHierarchyPK);
            appReqBlock.getHttpServletRequest().setAttribute("selectedAgentSnapshotPK", selectedAgentSnapshotPK);
            appReqBlock.getHttpServletRequest().setAttribute("selectedCommProfileFK", selectedCommProfileFK);
            appReqBlock.getHttpServletRequest().setAttribute("selectedCommProfileOvrdFK", selectedCommProfileOvrdFK);

            AgentSnapshot agentSnapshot = AgentSnapshot.findBy_PK(new Long(selectedAgentSnapshotPK));

            agentSnapshot.setServicingAgentIndicator(servicingAgentIndicator);
            agentSnapshot.setAdvancePercent(new EDITBigDecimal(advancePercent));
            agentSnapshot.setRecoveryPercent(new EDITBigDecimal(recoveryPercent));

            if (selectedCommProfileOvrdFK != null && Long.parseLong(selectedCommProfileOvrdFK) > 0)
            {
                agentSnapshot.setCommissionProfileFK(Long.parseLong(selectedCommProfileOvrdFK));
            }
            else
            {
                agentSnapshot.setCommissionProfileFK(null);
            }

            for (int i = 0; i < uiAgentHierarchyVOs.length; i++)
            {
                AgentHierarchyVO agentHierarchyVO = uiAgentHierarchyVOs[i].getAgentHierarchyVO();
                AgentSnapshotVO[] agentSnapshotVOs = agentHierarchyVO.getAgentSnapshotVO();
                for (int j = 0; j < agentSnapshotVOs.length; j++)
                {
                    if (agentSnapshotVOs[j].getAgentSnapshotPK() == (new Long(selectedAgentSnapshotPK)))
                    {
                        //setting this occurance with the entity values as a vo didnot work
                        agentSnapshotVOs[j].setAdvancePercent(new EDITBigDecimal(advancePercent).getBigDecimal());
                        agentSnapshotVOs[j].setRecoveryPercent(new EDITBigDecimal(recoveryPercent).getBigDecimal());
                        agentSnapshotVOs[j].setServicingAgentIndicator(servicingAgentIndicator);
                    }
                }
            }

            appReqBlock.getHttpSession().setAttribute("uiAgentHierarchyVOs", uiAgentHierarchyVOs);

            try
            {
                new GroupComponent().saveCaseAgentSnapshotUpdate(agentSnapshot);

                appReqBlock.putInRequestScope("responseMessage", "Commission Override was saved successfully.");
            }
            catch (EDITCaseException e)
            {
                appReqBlock.putInRequestScope("responseMessage", e.toString());
            }
        }
        else
        {
            appReqBlock.putInRequestScope("responseMessage", errorMessage);
        }

        return showCommissionOverrides(appReqBlock);
    }

    private String deleteCommissionOverrides(AppReqBlock appReqBlock)
    {
        PageBean formBean = appReqBlock.getFormBean();

        String selectedAgentHierarchyPK = formBean.getValue("selectedAgentHierarchyPK");
        String selectedAgentSnapshotPK = formBean.getValue("selectedAgentSnapshotPK");
        String selectedCommProfileFK = formBean.getValue("selectedCommProfileFK");

        AgentSnapshot agentSnapshot = AgentSnapshot.findBy_PK(new Long(selectedAgentSnapshotPK));

        agentSnapshot.setServicingAgentIndicator("N");
        agentSnapshot.setCommissionProfileFK(null);

        try
        {
            new GroupComponent().saveCaseAgentSnapshotUpdate(agentSnapshot);

            appReqBlock.putInRequestScope("responseMessage", "Commission Override was deleted successfully.");
        }
        catch (EDITCaseException e)
        {
            appReqBlock.putInRequestScope("responseMessage", e.toString());
        }

        appReqBlock.getHttpServletRequest().setAttribute("selectedAgentHierarchyPK", selectedAgentHierarchyPK);
        appReqBlock.getHttpServletRequest().setAttribute("selectedAgentSnapshotPK", selectedAgentSnapshotPK);
        appReqBlock.getHttpServletRequest().setAttribute("selectedCommProfileFK", selectedCommProfileFK);

        return showCaseAgents(appReqBlock);
    }

    /**
     * Add Button on groupDetailSummary activates this method.
     * @param appReqBlock
     * @return
     */
    private String showGroupAdd(AppReqBlock appReqBlock)
    {
        new CaseUseCaseComponent().addGroupDetail();

        removeDataFromSession(appReqBlock);

        new CaseClientSearchTableModel(appReqBlock);

        return ADD_GROUP_DIALOG;
    }

    private String showGroupBillingDialog(AppReqBlock appReqBlock)
    {
        new CaseUseCaseComponent().accessGroupBilling();

        String activeGroupPK = appReqBlock.getUserSession().getParameter("activeGroupPK");

        if (activeGroupPK != null)
        {
            ContractGroup groupContractGroup = ContractGroup.findByPK(new Long(activeGroupPK));

            appReqBlock.putInRequestScope("contractGroup", groupContractGroup);

            ClientDetail clientDetail = ClientDetail.findBy_ContractGroup(groupContractGroup);

            appReqBlock.putInRequestScope("clientDetail", clientDetail);

            BillSchedule billSchedule = groupContractGroup.getBillSchedule();

            if (billSchedule != null)
            {
                appReqBlock.putInRequestScope("billSchedule", billSchedule);
            }
        }

        new GroupBillingTableModel(appReqBlock);

        return GROUP_BILLING_DIALOG;
    }

    private String saveGroupBillingChange(AppReqBlock appReqBlock) throws SPException, PortalEditingException
    {
    	boolean billScheduleIsNew = false; 

    	BillSchedule newBillSchedule = null;

    	new CaseUseCaseComponent().updateGroupBilling();

    	BillSchedule billSchedule = (BillSchedule) Util.mapFormDataToHibernateEntity(appReqBlock.getHttpServletRequest(), BillSchedule.class, SessionHelper.EDITSOLUTIONS, false);

    	Long activeGroupPK = new Long(appReqBlock.getUserSession().getParameter("activeGroupPK"));

    	ContractGroup contractGroup = ContractGroup.findBy_ContractGroupPK(activeGroupPK);

    	String operator = appReqBlock.getUserSession().getUsername();

    	billSchedule.setBillMethodCT("List");
    	billSchedule.setBillTypeCT("GRP");

    	if (billSchedule.getCreationDate() == null)
    	{
    		billSchedule.setCreationOperator(operator);
    		billSchedule.setCreationDate(new EDITDate());
    		billSchedule.setEffectiveDate(new EDITDate());
    		billSchedule.setTerminationDate(new EDITDate(EDITDate.DEFAULT_MAX_DATE));
    		billSchedule.setStatusCT(BillSchedule.BILLSCHEDULE_STATUS_ACTIVE);
    	}

    	try
    	{

    		String strExistingBSPK = appReqBlock.getFormBean().getValue("billSchedulePK");
    		
    		Long existingBillSchedulePK = null;
    		
    		if (!strExistingBSPK.equals("") && strExistingBSPK != null)
    		{
    			existingBillSchedulePK = Long.parseLong(strExistingBSPK);
    		}
    		
    		if (existingBillSchedulePK != null && existingBillSchedulePK != 0)
    		{
    			BillScheduleVO[] existingBillSchedule = billing.dm.dao.DAOFactory.getBillScheduleDAO().findByBillSchedulePK(existingBillSchedulePK);

    			if (existingBillSchedule != null && existingBillSchedule.length > 0 && billSchedule.requiresNewBillScheduleRecord(existingBillSchedule[0]))
    			{
    				try {

    					billScheduleIsNew = true;
    					
    					billSchedule.setChangeEffectiveDate(new EDITDate());

    					// Evict billSchedule to prevent UI form changes from persisting to the db on this record
    					SessionHelper.evict(billSchedule, SessionHelper.EDITSOLUTIONS);

    					// Create copy of existing db BillSchedule record and write to db for persistence after update
    					SessionHelper.beginTransaction(SessionHelper.EDITSOLUTIONS);

    					newBillSchedule = new BillSchedule();

    					newBillSchedule.copyBillScheduleFields(existingBillSchedule[0]);

    					SessionHelper.save(newBillSchedule, SessionHelper.EDITSOLUTIONS);

    					SessionHelper.commitTransaction(SessionHelper.EDITSOLUTIONS);

    					// Set ContractGroup and Segments to new BillSchedule
    					contractGroup.setBillScheduleFK(newBillSchedule.getBillSchedulePK());
    					contractGroup.setBillSchedule(newBillSchedule);

    					Segment[] segments = Segment.findBy_BillScheduleFK(existingBillSchedulePK);

    					if (segments != null && segments.length > 0)
    					{	                	
    						for (Segment segment : segments)
    						{
    							segment.setBillSchedule(newBillSchedule);
    							segment.setBillScheduleFK(newBillSchedule.getBillSchedulePK());
    							
    							Segment[] riderSegments = segment.getRiders();
    							if (riderSegments != null && riderSegments.length > 0) 
    							{
    								for (Segment riderSegment : riderSegments)
    								{
    									riderSegment.setBillSchedule(newBillSchedule);
    									riderSegment.setBillScheduleFK(newBillSchedule.getBillSchedulePK());
    								}
    							}
    						}
    					}

    				} catch (Exception e) {
    					System.out.println(e);

    					e.printStackTrace();

    					SessionHelper.rollbackTransaction(SessionHelper.EDITSOLUTIONS);

    					throw e;
    				}
    			}
    		} 

    		EDITDate changeEffectiveDate = null;

    		if (billScheduleIsNew)
    		{            	
    			// Grab the BillSchedule updates from the UI form to write to the new BillSchedule record
    			newBillSchedule.copyBillScheduleFields(billSchedule);

    			EDITDate newEffectiveDate = new EDITDate();
    			
    			newBillSchedule.setEffectiveDate(newEffectiveDate);

    			newBillSchedule.setTerminationDate(new EDITDate(EDITDate.DEFAULT_MAX_DATE));

    			validateGroup(contractGroup, newBillSchedule);

    			changeEffectiveDate = newBillSchedule.getChangeEffectiveDate();

    			new BillingComponent().saveBillScheduleUpdate(newBillSchedule, contractGroup, changeEffectiveDate);

    			// Terminate the old BillSchedule if there are no segments attached
    			Segment[] segments = Segment.findBy_BillScheduleFK(existingBillSchedulePK);
    			
    			try {

    				BillSchedule formerBillSchedule = BillSchedule.findBy_BillSchedulePK(existingBillSchedulePK);

    				if (segments == null || segments.length == 0)
    				{
    					SessionHelper.beginTransaction(SessionHelper.EDITSOLUTIONS);

    					formerBillSchedule.setTerminationDate(newEffectiveDate);

    					SessionHelper.save(formerBillSchedule, SessionHelper.EDITSOLUTIONS);
    				}

    				ChangeHistory changeHistory = new ChangeHistory();
    				changeHistory.setModifiedRecordFK(activeGroupPK);
    				changeHistory.setTableName("group.ContractGroup");
    				changeHistory.setEffectiveDate(newEffectiveDate);
    				changeHistory.setProcessDate(new EDITDate());
    				changeHistory.setFieldName("BillScheduleFK");
    				changeHistory.setBeforeValue(formerBillSchedule.getBillSchedulePK().toString());
    				changeHistory.setAfterValue(newBillSchedule.getBillSchedulePK().toString());
    				changeHistory.setOperator(appReqBlock.getUserSession().getUsername());
    				changeHistory.setMaintDateTime(new EDITDateTime());
    				changeHistory.setPendingStatus(ChangeHistory.PENDING_STATUS_HISTORY);
    				
    				SessionHelper.save(changeHistory, SessionHelper.EDITSOLUTIONS);
    				
    				SessionHelper.commitTransaction(SessionHelper.EDITSOLUTIONS);

    			} catch (Exception e) {
    				System.out.println(e);

    				e.printStackTrace();

    				SessionHelper.rollbackTransaction(SessionHelper.EDITSOLUTIONS);

    				throw e;
    			}
    		}
    		else
    		{
    			validateGroup(contractGroup, billSchedule);

    			changeEffectiveDate = billSchedule.getChangeEffectiveDate();

    			new BillingComponent().saveBillScheduleUpdate(billSchedule, contractGroup, changeEffectiveDate);
    		}

    		appReqBlock.putInRequestScope("responseMessage", "Save of Bill Schedule was successful!");
    	}
    	catch (EDITCaseException e)
    	{
    		appReqBlock.putInRequestScope("responseMessage", e.toString());
    	}
    	catch (SPException e)
    	{
    		System.out.println(e);

    		e.printStackTrace();  

    		throw e;
    	}
    	catch (PortalEditingException e)
    	{
    		e.setReturnPage(GROUP_BILLING_DIALOG);

    		billSchedule.setBillSchedulePK(new Long(0));
    		appReqBlock.putInRequestScope("billSchedule", billSchedule);

    		new GroupBillingTableModel(appReqBlock);

    		appReqBlock.putInRequestScope("contractGroup", contractGroup);
    		ClientDetail clientDetail = ClientDetail.findByPK(contractGroup.getClientRole().getClientDetailFK());
    		appReqBlock.putInRequestScope("clientDetail", clientDetail);

    		throw e;
    	}
    	catch (Exception e) {
    		System.out.println(e);

    		e.printStackTrace();

    		SessionHelper.rollbackTransaction(SessionHelper.EDITSOLUTIONS);

    		throw e;
    	}
    	finally
    	{
    		SessionHelper.clearSessions();
    	}

    	return showGroupBillingDialog(appReqBlock);
    }

    private String showGroupPRDDialog(AppReqBlock appReqBlock)
    {
        new CaseUseCaseComponent().accessPRD();

        String activeGroupPK = appReqBlock.getUserSession().getParameter("activeGroupPK");

        if (activeGroupPK != null)
        {
            ContractGroup groupContractGroup = ContractGroup.findByPK(new Long(activeGroupPK));

            appReqBlock.putInRequestScope("contractGroup", groupContractGroup);

            ClientDetail clientDetail = ClientDetail.findBy_ContractGroup(groupContractGroup);

            appReqBlock.putInRequestScope("clientDetail", clientDetail);

            BillSchedule billSchedule = groupContractGroup.getBillSchedule();

            appReqBlock.putInRequestScope("billSchedule", billSchedule);

            Set<PayrollDeductionSchedule> payrollDeductionSchedules = groupContractGroup.getPayrollDeductionSchedules();

            if (!payrollDeductionSchedules.isEmpty())
            {
                appReqBlock.putInRequestScope("payrollDeductionSchedule", (PayrollDeductionSchedule) payrollDeductionSchedules.iterator().next());
            }
        }

        new GroupPRDTableModel(appReqBlock);

        return GROUP_PRD_DIALOG;
    }

    private String savePRDChange(AppReqBlock appReqBlock) throws SPException, PortalEditingException
    {
        new CaseUseCaseComponent().updatePRD();

        PayrollDeductionSchedule payrollDeductionSchedule = (PayrollDeductionSchedule) Util.mapFormDataToHibernateEntity(appReqBlock.getHttpServletRequest(), PayrollDeductionSchedule.class, SessionHelper.EDITSOLUTIONS, false);

        Long activeGroupPK = new Long(appReqBlock.getUserSession().getParameter("activeGroupPK"));
        
        ContractGroup contractGroup = ContractGroup.findBy_ContractGroupPK(activeGroupPK);

        String operator = appReqBlock.getUserSession().getUsername();

        String ignoreEditWarnings = appReqBlock.getReqParm("ignoreEditWarnings");
        ignoreEditWarnings = (ignoreEditWarnings == null) ? "" : ignoreEditWarnings;

        if (payrollDeductionSchedule.getCreationDate() == null)
        {
            payrollDeductionSchedule.setCreationOperator(operator);
            payrollDeductionSchedule.setCreationDate(new EDITDate());
        }

        if (!ignoreEditWarnings.equalsIgnoreCase("true"))
        {
            try
            {
                validateGroup(contractGroup, payrollDeductionSchedule);
            }
            catch (PortalEditingException e)
            {
                e.setReturnPage(CASE_MAIN);
                
                throw e;
            }                
        }

        try
        {
            EDITDate changeEffectiveDate = null; 
              
            String changeEffectiveDateStr = Util.initString(appReqBlock.getReqParm("changeEffectiveDate"), null);
              
            if (changeEffectiveDateStr != null)
            {
                changeEffectiveDate = DateTimeUtil.getEDITDateFromMMDDYYYY(changeEffectiveDateStr);
            }               
            
            new GroupComponent().savePayrollDeductionScheduleUpdate(payrollDeductionSchedule, contractGroup, changeEffectiveDate);
            
            // Test for essential values and alert user if they are set to NULL
            EDITDate nextPRDDueDate = payrollDeductionSchedule.getNextPRDDueDate();
            EDITDate nextPRDExtractDate = payrollDeductionSchedule.getNextPRDExtractDate();
            
            if (nextPRDDueDate==null && nextPRDExtractDate==null)
            {
            	appReqBlock.putInRequestScope("responseMessage", "Please Contact Helpdesk - Saved successfully but the following values need to be populated: NextPRDDueDate & NextPRDExtractDate");
            }
            else if (nextPRDDueDate==null && nextPRDExtractDate!=null)
            {
            	appReqBlock.putInRequestScope("responseMessage", "Please Contact Helpdesk - Saved successfully but the following value is need to be populated: NextPRDDueDate");
            }
            else if (nextPRDDueDate!=null && nextPRDExtractDate==null)
            {
            	appReqBlock.putInRequestScope("responseMessage", "Please Contact Helpdesk - Saved successfully but the following value is need to be populated: NextPRDExtractDate");
            }
            else
            {
            	appReqBlock.putInRequestScope("responseMessage", "Save of Payroll Deduction Schedule was successful.");
            }
        }
        catch (EDITCaseException e)
        {
            appReqBlock.putInRequestScope("responseMessage", e.toString());
        }

        return showGroupPRDDialog(appReqBlock);
    }

    private String showGroupDeptLocDialog(AppReqBlock appReqBlock)
    {
        String activeGroupPK = appReqBlock.getUserSession().getParameter("activeGroupPK");

        if (activeGroupPK != null)
        {
            ContractGroup groupContractGroup = ContractGroup.findByPK(new Long(activeGroupPK));

            appReqBlock.putInRequestScope("contractGroup", groupContractGroup);

            ClientDetail clientDetail = ClientDetail.findBy_ContractGroup(groupContractGroup);

            appReqBlock.putInRequestScope("clientDetail", clientDetail);

            String activeDeptLocPK = (String) appReqBlock.getHttpServletRequest().getAttribute("activeDeptLocPK");

            if (activeDeptLocPK != null)
            {
                DepartmentLocation departmentLocation = DepartmentLocation.findBy_DepartmentLocationPK(new Long(activeDeptLocPK));

                if (departmentLocation != null)
                {
                    appReqBlock.putInRequestScope("departmentLocation", departmentLocation);
                }
            }
        }

        new GroupDeptLocTableModel(appReqBlock);

        return GROUP_DEPT_LOC_DIALOG;
    }

    /**
     * Show the dept/loc selected from the summary in the detail protion of the page.
     * @param appReqBlock
     * @return
     */
    private String showDeptLocDetail(AppReqBlock appReqBlock)
    {
        String activeDeptLocPK = new GroupDeptLocTableModel(appReqBlock).getSelectedRowId();

        appReqBlock.putInRequestScope("activeDeptLocPK", activeDeptLocPK);

        return showGroupDeptLocDialog(appReqBlock);
    }

    private String saveDeptLocChange(AppReqBlock appReqBlock)
    {
        DepartmentLocation departmentLocation = (DepartmentLocation) Util.mapFormDataToHibernateEntity(appReqBlock.getHttpServletRequest(), DepartmentLocation.class, SessionHelper.EDITSOLUTIONS, false);

        String activeGroupPK = appReqBlock.getUserSession().getParameter("activeGroupPK");

        try
        {
          new GroupComponent().saveDepartmentLocation(departmentLocation, new Long(activeGroupPK));

          appReqBlock.putInRequestScope("responseMessage", "Save of Dept/Loc was successful.");
        }
        catch (EDITCaseException e)
        {
          appReqBlock.putInRequestScope("responseMessage", e.toString());
        }

        return showGroupDeptLocDialog(appReqBlock);
    }

    private String refreshGroupDeptLocDialog(AppReqBlock appReqBlock)
    {
        String filterTerminated = (String) appReqBlock.getFormBean().getValue("filterTerminated");
        appReqBlock.getHttpSession().setAttribute("filterTerminated", filterTerminated);

        String orderByColumn = (String) appReqBlock.getFormBean().getValue("orderByColumn");
        appReqBlock.getHttpSession().setAttribute("orderByColumn", orderByColumn);

        String descInd = (String) appReqBlock.getFormBean().getValue("descInd");
        appReqBlock.getHttpSession().setAttribute("descInd", descInd);

        new GroupDeptLocTableModel(appReqBlock);

        return GROUP_DEPT_LOC_DIALOG;
	}

    private String clearDeptLoc(AppReqBlock appReqBlock)
    {
        appReqBlock.setReqParm("activeDeptLocPK", null);

        appReqBlock.putInRequestScope("resetForm", "YES");
        
        new GroupDeptLocTableModel(appReqBlock).resetAllRows();

        return showGroupDeptLocDialog(appReqBlock);
    }

    private String showCaseHistory(AppReqBlock appReqBlock)
    {
        new CaseHistoryTableModel(appReqBlock);

        return CASE_HISTORY;
    }

    private String showCaseHistoryFilter(AppReqBlock appReqBlock)
    {
        String groupNumber = Util.initString(appReqBlock.getFormBean().getValue("groupNumber"), "");
        String fromDate = Util.initString(appReqBlock.getFormBean().getValue("fromDate"), "");
        String toDate = Util.initString(appReqBlock.getFormBean().getValue("toDate"), "");
        String selectedPDPK = Util.initString(appReqBlock.getFormBean().getValue("selectedPDPK"), "");

        appReqBlock.getHttpServletRequest().setAttribute("groupNumber", groupNumber);
        appReqBlock.getHttpServletRequest().setAttribute("fromDate", fromDate);
        appReqBlock.getHttpServletRequest().setAttribute("toDate", toDate);
        appReqBlock.getHttpServletRequest().setAttribute("selectedPDPK", selectedPDPK);

        String activeCasePK = appReqBlock.getUserSession().getParameter("activeCasePK");

        String[] groupNumbers = null;

        if (activeCasePK != null)
        {
            ContractGroup[] contractGroups = ContractGroup.findBy_ContractGroupFK_ContractGroupTypeCT(new Long(activeCasePK), ContractGroup.CONTRACTGROUPTYPECT_GROUP);

            groupNumbers = new String[contractGroups.length];

            for (int i = 0; i < contractGroups.length; i++)
            {
                groupNumbers[i] = contractGroups[i].getContractGroupNumber();
            }
        }
        else
        {
            groupNumbers = new String[0];
        }

        appReqBlock.getHttpServletRequest().setAttribute("groupNumbers", groupNumbers);

        return CASE_HISTORY_FILTER_DIALOG;
    }

    private String filterCaseHistory(AppReqBlock appReqBlock)
    {
        String fromDate = appReqBlock.getReqParm("fromDate");
        fromDate = DateTimeUtil.formatMMDDYYYYToYYYYMMDD(fromDate);
        String toDate = appReqBlock.getReqParm("toDate");
        toDate = DateTimeUtil.formatMMDDYYYYToYYYYMMDD(toDate);
        String selectedGroupNumber = appReqBlock.getReqParm("selectedGroupNumber");

        appReqBlock.getHttpServletRequest().setAttribute("fromDate", fromDate);
        appReqBlock.getHttpServletRequest().setAttribute("toDate", toDate);
        appReqBlock.getHttpServletRequest().setAttribute("selectedGroupNumber", selectedGroupNumber);

        return showCaseHistory(appReqBlock);
    }

    private String showPRDDetail(AppReqBlock appReqBlock)
    {
        String selectedPDPK = new CaseHistoryTableModel(appReqBlock).getSelectedRowId();
        if (selectedPDPK == null || selectedPDPK.equals(""))
        {
            selectedPDPK = appReqBlock.getReqParm("selectedPDPK");
        }

        String selectedGroupNumber = appReqBlock.getReqParm("selectedGroupNumber");
        String fromDate = appReqBlock.getReqParm("fromDate");
        String toDate = appReqBlock.getReqParm("toDate");

        appReqBlock.getHttpServletRequest().setAttribute("selectedPDPK", selectedPDPK);
        appReqBlock.getHttpServletRequest().setAttribute("selectedGroupNumber", selectedGroupNumber);
        appReqBlock.getHttpServletRequest().setAttribute("fromDate", fromDate);
        appReqBlock.getHttpServletRequest().setAttribute("toDate", toDate);

        if (selectedPDPK != null && !selectedPDPK.equals("") && !selectedPDPK.equals("null"))
        {
            PayrollDeduction payrollDeduction = PayrollDeduction.findByPK(new Long(selectedPDPK));

            PayrollDeductionSchedule pds = payrollDeduction.getPayrollDeductionSchedule();

            ContractGroup groupContractGroup = pds.getContractGroup();

            appReqBlock.putInRequestScope("contractGroup", groupContractGroup);

            appReqBlock.putInRequestScope("payrollDeduction", payrollDeduction);
        }

        return showCaseHistory(appReqBlock);
    }

    private String showPRDReport(AppReqBlock appReqBlock)
    {
        String selectedPDPK = appReqBlock.getFormBean().getValue("selectedPDPK");
        String groupNumber = appReqBlock.getFormBean().getValue("selectedGroupNumber");
        String fromDate = appReqBlock.getFormBean().getValue("fromDate");
        String toDate = appReqBlock.getFormBean().getValue("toDate");

        appReqBlock.getHttpServletRequest().setAttribute("selectedGroupNumber", groupNumber);
        appReqBlock.getHttpServletRequest().setAttribute("fromDate", fromDate);
        appReqBlock.getHttpServletRequest().setAttribute("toDate", toDate);
        appReqBlock.getHttpServletRequest().setAttribute("selectedPDPK", selectedPDPK);

        new PRDReportTableModel(appReqBlock);

        return PRD_REPORT_DIALOG;
    }

    /**
     * Loads AgentHierarchy information from persistence to the beans for display on the screen
     *
     * @param appReqBlock
     * @param segmentVO
     *
     * @throws Exception
     */
    private void loadAgentHierarchies(AppReqBlock appReqBlock) throws Exception
    {
        agent.business.Agent agentComponent = new agent.component.AgentComponent();

        String activeCasePK = appReqBlock.getUserSession().getParameter("activeCasePK");

        if (activeCasePK != null)
        {
            ContractGroup contractGroup = ContractGroup.findBy_ContractGroupPK(new Long(activeCasePK));

            List uiAgentHierarchies = new ArrayList();

            //  Need to get all of the AgentHierarchies for the active Case.
            Set<AgentHierarchy> agentHierarchies = contractGroup.getAgentHierarchies();

            if (!agentHierarchies.isEmpty())
            {
                Iterator it = agentHierarchies.iterator();

                while (it.hasNext())
                {
                    AgentHierarchy agentHierarchy = (AgentHierarchy) it.next();

                    AgentHierarchyVO agentHierarchyVO = (AgentHierarchyVO) agentHierarchy.getVO();

                    UIAgentHierarchyVO uiAgentHierarchyVO = new UIAgentHierarchyVO();

                    Long agentFK = agentHierarchy.getAgentFK();
                    //  Get the composed Agent for this hierarchy and put in UI object
                    List voInclusionList = new ArrayList();
                    voInclusionList.add(CommissionProfileVO.class);
                    voInclusionList.add(AgentContractVO.class);
                    voInclusionList.add(PlacedAgentVO.class);
                    voInclusionList.add(ClientRoleVO.class);
                    voInclusionList.add(ClientDetailVO.class);

                    Agent agent = agentHierarchy.getAgent();

                    Set<AgentSnapshot> agentSnapshots = agentHierarchy.getAgentSnapshots();

                    Iterator it2 = agentSnapshots.iterator();

                    while (it2.hasNext())
                    {
                        AgentSnapshot agentSnapshot = (AgentSnapshot) it2.next();

                        agentHierarchyVO.addAgentSnapshotVO((AgentSnapshotVO) agentSnapshot.getVO());
                    }

                    uiAgentHierarchyVO.setAgentHierarchyVO(agentHierarchyVO);
                    uiAgentHierarchyVO.setAgentVO((AgentVO) agent.getVO());

                    Set<AgentHierarchyAllocation> agtHierAllocs = agentHierarchy.getAgentHierarchyAllocations();

                    it2 = agtHierAllocs.iterator();

                    while (it2.hasNext())
                    {
                        AgentHierarchyAllocation agtHierAlloc = (AgentHierarchyAllocation) it2.next();

                        uiAgentHierarchyVO.setAgentHierarchyAllocationVO(agtHierAlloc.getAsVO());
                    }

                    uiAgentHierarchies.add(uiAgentHierarchyVO);
                }
            }

            if (uiAgentHierarchies.size() > 0)
            {
                UIAgentHierarchyVO[] uiAgentHierarchyVOs = (UIAgentHierarchyVO[]) uiAgentHierarchies.toArray(new UIAgentHierarchyVO[uiAgentHierarchies.size()]);

                appReqBlock.getHttpSession().setAttribute("uiAgentHierarchyVOs", uiAgentHierarchyVOs);
            }
        }
    }

    /**
     * Presents the dialog that allows the user to view/update note information for the specified Case
     * @param appReqBlock
     * @return
     */
    private String showCaseNotes(AppReqBlock appReqBlock)
    {
        CaseNoteTableModel caseNoteTableModel =  new CaseNoteTableModel(appReqBlock);

        return CASE_NOTES_DIALOG;
    }

    /**
     * Saves the current note information for the specified Case
     * @param appReqBlock
     * @return
     */
    private String saveCaseNoteDetail(AppReqBlock appReqBlock)
    {
        ContractGroupNote contractGroupNote = (ContractGroupNote) Util.mapFormDataToHibernateEntity(appReqBlock.getHttpServletRequest(), ContractGroupNote.class, SessionHelper.EDITSOLUTIONS, false);

        String activeCasePK = appReqBlock.getUserSession().getParameter("activeCasePK");
        String operator = appReqBlock.getUserSession().getUsername();
        contractGroupNote.setOperator(operator);

        try
        {
            new GroupComponent().saveContractGroupNote(contractGroupNote, new Long(activeCasePK));

            appReqBlock.putInRequestScope("responseMessage", "Save of Note was successful.");

            appReqBlock.getUserSession().setParameter("activeCaseNotePK", null);

            appReqBlock.putInRequestScope("pageCommand", "resetPage");
        }
        catch (EDITCaseException e)
        {
            appReqBlock.putInRequestScope("responseMessage", e.toString());
        }

        return showCaseNotes(appReqBlock);
    }

    /**
     * Show the note selected from the summary in the detail portion of the page.
     * @param appReqBlock
     * @return
     */
    private String showCaseNoteDetail(AppReqBlock appReqBlock)
    {
        String activeCaseNotePK = new CaseNoteTableModel(appReqBlock).getSelectedRowId();

        appReqBlock.getUserSession().setParameter("activeCaseNotePK", activeCaseNotePK);

        //String activeCaseNotePK = appReqBlock.getUserSession().getParameter("activeCaseNotePK");

        if (activeCaseNotePK != null)
        {
            ContractGroupNote contractGroupNote = ContractGroupNote.findByPK(new Long(activeCaseNotePK));

            appReqBlock.putInRequestScope("contractGroupNote", contractGroupNote);
        }

        CaseNoteTableModel caseNoteTableModel =  new CaseNoteTableModel(appReqBlock);
        return CASE_NOTES_DIALOG;
        //return showCaseNotes(appReqBlock);
    }

    private String cancelCaseNote(AppReqBlock appReqBlock)
    {
        appReqBlock.getUserSession().setParameter("activeCaseNotePK", null);

        appReqBlock.putInRequestScope("pageCommand", "");

        return showCaseNotes(appReqBlock);
    }

    /**
     * Delete the selected Note
     * @param appReqBlock
     * @return
     */
    private String deleteCaseNote(AppReqBlock appReqBlock)
    {
        String activeCaseNotePK = new CaseNoteTableModel(appReqBlock).getSelectedRowId();

        try
        {
            new GroupComponent().deleteContractGroupNote(new Long(activeCaseNotePK));

            appReqBlock.putInRequestScope("responseMessage", "Delete of Note was successful.");

            appReqBlock.getUserSession().setParameter("activeCaseNotePK", null);

            appReqBlock.putInRequestScope("pageCommand", "resetForm");
        }
        catch (EDITCaseException e)
        {
            appReqBlock.putInRequestScope("responseMessage", e.toString());
        }

        return showCaseNotes(appReqBlock);
    }

   
    private String addEnrollment(AppReqBlock appReqBlock)
    {
        appReqBlock.setReqParm("activeEnrollmentStatePK", "");
        appReqBlock.getUserSession().setParameter("activeEnrollmentPK", null);

        appReqBlock.getHttpServletRequest().setAttribute("activeProjectedBusinessPK", "");

        appReqBlock.putInRequestScope("pageCommand", "resetForm");

        new EnrollmentTableModel(appReqBlock).resetAllRows();
        new ProjectedBusinessTableModel(appReqBlock).resetAllRows();

        return showEnrollmentDialog(appReqBlock);
    }

    /**
     * Delete enrollment if not being used by BatchContractSetup
     * @param appReqBlock
     * @return
     */
    private String deleteEnrollment(AppReqBlock appReqBlock)
    {
        String activeEnrollmentPK = new EnrollmentTableModel(appReqBlock).getSelectedRowId();

        try
        {
            String responseMessage = new GroupComponent().deleteEnrollment(new Long(activeEnrollmentPK));

            appReqBlock.putInRequestScope("responseMessage", responseMessage);

            if (!responseMessage.startsWith("Delete not allowed"))
            {
                appReqBlock.getUserSession().setParameter("activeEnrollmentPK", null);
                appReqBlock.putInRequestScope("pageCommand", "resetForm");
            }
        }
        catch (EDITCaseException e)
        {
            appReqBlock.putInRequestScope("responseMessage", e.toString());
        }

        return showEnrollmentDialog(appReqBlock);
    }

    private String addProjectedBusiness(AppReqBlock appReqBlock)
    {
        appReqBlock.getHttpServletRequest().setAttribute("activeProjectedBusinessPK", null);
        appReqBlock.getHttpServletRequest().setAttribute("projectedBusinessByMonth", null);
        appReqBlock.putInRequestScope("projectedBusinessByMonthPK", "");

        new ProjectedBusinessTableModel(appReqBlock).resetAllRows();

        return showEnrollmentDialog(appReqBlock);
    }

    /**
     * Delete projectedBusiness
     * @param appReqBlock
     * @return
     */
    private String deleteProjectedBusiness(AppReqBlock appReqBlock)
    {
        String activeProjectedBusinessPK = new ProjectedBusinessTableModel(appReqBlock).getSelectedRowId();

        try
        {
            new GroupComponent().deleteProjectedBusiness(new Long(activeProjectedBusinessPK));

            appReqBlock.putInRequestScope("responseMessage", "ProjectedBusinessByMonth Successsfully Deleted");

            appReqBlock.getUserSession().setParameter("activeProjectedBusinessPK", null);
        }
        catch (EDITCaseException e)
        {
            appReqBlock.putInRequestScope("responseMessage", e.toString());
        }

        return showEnrollmentDialog(appReqBlock);
    }

    /**
     * Populate dialog page of enrollment states for the enrollment selected
     * @param appReqBlock
     * @return
     */
    private String showEnrollmentStateDialog(AppReqBlock appReqBlock)
    {
        String activeEnrollmentStatePK = Util.initString(appReqBlock.getUserSession().getParameter("activeEnrollmentStatePK"), null);

        if (activeEnrollmentStatePK != null) // An existing Enrollment
        {
            EnrollmentState enrollmentState = EnrollmentState.findByPK(new Long(activeEnrollmentStatePK));

            appReqBlock.putInRequestScope("enrollmentState", enrollmentState);
        }

        new EnrollmentStateTableModel(appReqBlock);

        return ENROLLMENT_STATE_DIALOG;
    }

   /**
     * Show the enrollmentState selected from the summary in the detail portion of the page.
     * @param appReqBlock
     * @return
     */
    private String showEnrollmentStateDetail(AppReqBlock appReqBlock)
    {
        String activeEnrollmentStatePK = new EnrollmentStateTableModel(appReqBlock).getSelectedRowId();

        appReqBlock.getUserSession().setParameter("activeEnrollmentStatePK", activeEnrollmentStatePK);

        return showEnrollmentStateDialog(appReqBlock);
    }

    private String saveEnrollmentState(AppReqBlock appReqBlock)
    {
        EnrollmentState enrollmentState = (EnrollmentState) Util.mapFormDataToHibernateEntity(appReqBlock.getHttpServletRequest(), EnrollmentState.class, SessionHelper.EDITSOLUTIONS, false);

        String activeEnrollmentPK = appReqBlock.getUserSession().getParameter("activeEnrollmentPK");

        try
        {
            new GroupComponent().saveEnrollmentState(enrollmentState, new Long(activeEnrollmentPK));

            appReqBlock.putInRequestScope("responseMessage", "Save of Enrollment State was successful.");
            appReqBlock.putInRequestScope("pageCommand", "resetForm");
            appReqBlock.getUserSession().setParameter("activeEnrollmentStatePK", "");

        }
        catch (EDITCaseException e)
        {
            appReqBlock.putInRequestScope("responseMessage", e.toString());
        }

        return showEnrollmentStateDialog(appReqBlock);
    }

    /**
     * Delete enrollment if not being used by BatchContractSetup
     * @param appReqBlock
     * @return
     */
    private String deleteEnrollmentState(AppReqBlock appReqBlock)
    {
        String activeEnrollmentStatePK = new EnrollmentStateTableModel(appReqBlock).getSelectedRowId();

        try
        {
            String responseMessage = new GroupComponent().deleteEnrollmentState(new Long(activeEnrollmentStatePK));

            appReqBlock.putInRequestScope("responseMessage", responseMessage);

            appReqBlock.putInRequestScope("pageCommand", "resetForm");
            appReqBlock.getUserSession().setParameter("activeEnrollmentStatePK", "");
        }
        catch (EDITCaseException e)
        {
            appReqBlock.putInRequestScope("responseMessage", e.toString());
        }

        return showEnrollmentStateDialog(appReqBlock);
    }

    /**
     * Validates the client information before saving using scripts
     *
     * @param clientDetail
     *
     * @throws SPException
     * @throws PortalEditingException
     */
    private void validateClient(ClientDetail clientDetail) throws SPException, PortalEditingException
    {
        SPOutput spOutput = clientDetail.validateClient();

        ValidationVO[] validationVOs = spOutput.getSPOutputVO().getValidationVO();

        if (spOutput.hasValidationOutputs())
        {
            PortalEditingException editingException = new PortalEditingException();

            editingException.setValidationVOs(validationVOs);

            throw editingException;
        }
    }

    private String deleteCaseEntry(AppReqBlock appReqBlock)
    {
        String activeCasePK = appReqBlock.getUserSession().getParameter("activeCasePK");

        boolean groupExists = ContractGroup.doesCaseHaveGroups(new Long(activeCasePK));

        String message = null;
        if (groupExists)
        {
            message = "Case Delete Not Allowed, Case Has associated Groups";
            appReqBlock.putInRequestScope("responseMessage", message);
        }
        else
        {
            try
            {
                message = new GroupComponent().deleteCaseAndChildren(new Long(activeCasePK));
                appReqBlock.putInRequestScope("responseMessage", message);
                appReqBlock.getUserSession().setParameter("activeCasePK", null);
                appReqBlock.getUserSession().setParameter("activeCaseClientDetailPK", null);
            }
            catch (EDITCaseException e)
            {
                appReqBlock.putInRequestScope("responseMessage", e.toString());
            }
        }

        return showCaseMain(appReqBlock);
	}

    private String showCaseSetup(AppReqBlock appReqBlock)
    {
        new CaseUseCaseComponent().accessCaseSetup();

        new CaseSetupTableModel(appReqBlock);

        return CASE_SETUP_DIALOG;
    }

    private String showCaseSetupDetail(AppReqBlock appReqBlock)
    {
        String activeCaseSetupPK = new CaseSetupTableModel(appReqBlock).getSelectedRowId();

        appReqBlock.getUserSession().setParameter("activeCaseSetupPK", activeCaseSetupPK);

        if (activeCaseSetupPK != null)
        {
            CaseSetup caseSetup = CaseSetup.findByCaseSetupPK(new Long(activeCaseSetupPK));

            appReqBlock.putInRequestScope("caseSetup", caseSetup);
        }

        return CASE_SETUP_DIALOG;
    }

    private String saveCaseSetup(AppReqBlock appReqBlock)
    {
        CaseSetup caseSetup = (CaseSetup) Util.mapFormDataToHibernateEntity(appReqBlock.getHttpServletRequest(), CaseSetup.class, SessionHelper.EDITSOLUTIONS, true);

        String activeCasePK = appReqBlock.getUserSession().getParameter("activeCasePK");

        try
        {
            new GroupComponent().saveCaseSetup(caseSetup, new Long(activeCasePK));

            appReqBlock.putInRequestScope("responseMessage", "Save of CaseSetup was successful.");

            appReqBlock.getUserSession().setParameter("activeCaseSetupPK", null);

            appReqBlock.putInRequestScope("pageCommand", "resetPage");
        }
        catch (EDITCaseException e)
        {
            appReqBlock.putInRequestScope("responseMessage", e.toString());
        }

        return showCaseSetup(appReqBlock);
    }

    private String deleteCaseSetup(AppReqBlock appReqBlock)
    {
        String activeCaseSetupPK = new CaseSetupTableModel(appReqBlock).getSelectedRowId();

        try
        {
            new GroupComponent().deleteCaseSetup(new Long(activeCaseSetupPK));

            appReqBlock.putInRequestScope("responseMessage", "Delete of CaseSetup was successful.");

            appReqBlock.getUserSession().setParameter("activeCaseSetupPK", null);

            appReqBlock.putInRequestScope("pageCommand", "resetForm");
        }
        catch (EDITCaseException e)
        {
            appReqBlock.putInRequestScope("responseMessage", e.toString());
        }

        return showCaseSetup(appReqBlock);
    }

    private String cancelCaseSetup(AppReqBlock appReqBlock)
    {
        appReqBlock.getUserSession().setParameter("activeCaseSetupPK", null);

        appReqBlock.putInRequestScope("pageCommand", "");

        return showCaseSetup(appReqBlock);
    }
}
