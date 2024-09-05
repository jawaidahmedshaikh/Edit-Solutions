/*
 * User: unknown
 * Date: Jan 1, 2000
 * Time: unknown
 *
 * (c) 2000-2009 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */

package contract.ui.servlet;

import agent.Agent;
import agent.AgentContract;
import agent.PlacedAgent;
import agent.component.AgentComponent;
import billing.Bill;
import com.oreilly.servlet.multipart.FilePart;
import com.oreilly.servlet.multipart.MultipartParser;
import com.oreilly.servlet.multipart.ParamPart;
import com.oreilly.servlet.multipart.Part;
import group.*;
import contract.*;
import contract.business.*;
import contract.component.*;
import contract.ui.ImportNewBusinessResponse;
import contract.util.*;
import edit.common.CodeTableWrapper;
import edit.common.EDITBigDecimal;
import edit.common.EDITDate;
import edit.common.EDITDateTime;
import edit.common.exceptions.*;
import edit.common.ui.ExcelUtil;
import edit.common.vo.*;
import edit.portal.common.session.UserSession;
import edit.portal.common.transactions.Transaction;
import edit.portal.widget.*;
import edit.portal.exceptions.*;
import edit.services.db.hibernate.SessionHelper;
import engine.business.*;
import engine.component.*;
import engine.sp.*;
import engine.*;
import event.*;
import event.financial.contract.trx.*;
import event.business.Event;
import event.component.EventComponent;
import fission.beans.FormBean;
import fission.beans.PageBean;
import fission.beans.SessionBean;
import fission.global.AppReqBlock;
import fission.utility.DateTimeUtil;
import fission.utility.Util;
import java.io.InputStream;
import java.rmi.dgc.VMID;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.math.BigDecimal;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import security.Operator;
import billing.BillSchedule;
import client.*;
import client.component.ClientUseCaseComponent;
import codetable.component.*;
import javax.servlet.http.*;
import role.*;
import businesscalendar.BusinessCalendar;
import businesscalendar.component.BusinessCalendarComponent;
import businesscalendar.BusinessDay;

public class QuoteDetailTran extends AbstractContractTran
{
    private static final String TRADITIONAL = "Traditional";
    private static final String NON_TRAD_LIFE = "NonTradLife";
    private static final String DEFERRED_ANNUITY = "DFA";
    private static final String PAYOUT = "Payout";
    private static final String AH = "A&H";

    private static final String[] TRADITIONAL_PRODUCTS = new  String[] {"Traditional"};
    private static final String[] UNIVERSAL_LIFE_PRODUCTS = new  String[] {"UL"};
    private static final String[] NON_TRADITIONAL_LIFE_PRODUCTS = new String[] {"VL", "TL"};
    private static final String[] DEFERRED_ANNUITY_PRODUCTS = new String[] {"DFA"};
    private static final String[] PAYOUT_PRODUCTS = new String[] {"AMC", "INR", "INT", "JSA", "JPC", "LCR", "LPC", "LOA", "PCA"};

    private static final String[] CONTRACT_STATUS_TO_DELETE_CLIENT =
            new String[] {"SubmitPend", "Submitted", "InitialUW", "Pending", "Approved", "ReinstatementNB", "Quote", ""};

    //These are the Actions of the Screen

    //These actions are for showing the tabs of the quote system
    private static final String SHOW_QUOTE_MAIN_DEFAULT = "showQuoteMainDefault";

    private static final String SHOW_QUOTE_MAIN_CONTENT = "showQuoteMainContent";
    private static final String SHOW_QUOTE_RIDERS = "showQuoteRiders";
    private static final String SHOW_QUOTE_INVESTMENTS = "showQuoteInvestments";
    private static final String SHOW_QUOTE_NON_PAYEE_OR_PAYEE = "showQuoteNonPayeeOrPayee";
    private static final String SHOW_QUOTE_AGENTS = "showQuoteAgents";
    private static final String SHOW_CLIENT_DETAIL_SUMMARY = "showClientDetailSummary";
    private static final String SHOW_QUOTE_SCHED_EVENTS = "showQuoteSchedEvents";
    private static final String SHOW_TRANSACTION_TYPE_DIALOG = "showTransactionTypeDialog";
    private static final String SHOW_TRANSACTION_DEFAULT = "showTransactionDefault";
    private static final String SHOW_TRANS_DETAIL_SUMMARY = "showTransactionDetailSummary";
    private static final String SHOW_NOTES_DIALOG = "showNotesDialog";
    private static final String SHOW_NOTES_DETAIL_SUMMARY = "showNotesDetailSummary";
    private static final String SHOW_PAYEE_DIALOG = "showPayeeDialog";
    private static final String CLOSE_PAYEE_SHOW_SCHED_EVENT = "closePayeeShowSchedEvent";
    private static final String SHOW_DATES_DIALOG = "showDatesDialog";
    private static final String SHOW_AGENT_HIERARCHY_DIALOG = "showAgentHierarchyDialog";
//    private static final String SHOW_ADVANCE_DIALOG = "showAdvanceDialog";
    private static final String SHOW_SELECTED_HIERARCHY_ROW = "showSelectedHierarchyRow";
    private static final String SELECT_COMM_PROFILE_FOR_AGENT = "selectCommProfileForAgent";
    private static final String SAVE_AGENT_HIERARCHY = "saveAgentHierarchy";
    private static final String CLOSE_AGENT_HIERARCHY = "closeAgentHierarchy";
    private static final String SAVE_ADVANCE = "saveAdvance";
    private static final String SHOW_COMMISSION_OVERRIDES = "showCommissionOverrides";
    private static final String SAVE_COMMISSION_OVERRIDES = "saveCommissionOverrides";
    private static final String DELETE_COMMISSION_OVERRIDES = "deleteCommissionOverrides";
    private static final String CANCEL_COMMISSION_OVERRIDES = "cancelCommissionOverrides";
    private static final String SHOW_AGENT_SELECTION_DIALOG = "showAgentSelectionDialog";
    private static final String SHOW_REPORT_TO_AGENT = "showReportToAgent";
    private static final String CLEAR_QUOTE_COMMIT_AGENT_FORM = "clearQuoteCommitAgentForm";
    private static final String SHOW_QUOTE_REQUIREMENTS = "showQuoteRequirements";
    private static final String SHOW_REQUIREMENT_DETAIL = "showRequirementDetail";
    private static final String SHOW_MANUAL_REQUIREMENT_SELECTION_DIALOG = "showManualRequirementSelectionDialog";
    private static final String SHOW_MANUAL_REQUIREMENT_DESCRIPTION = "showManualRequirementDescription";
    private static final String SAVE_MANUAL_REQUIREMENT = "saveManualRequirement";
    private static final String SHOW_REQUIREMENT_TABLE = "showRequirementTable";
    private static final String SHOW_REQUIREMENT_DETAIL_SUMMARY = "showRequirementDetailSummary";
    private static final String SHOW_BUILD_REQUIREMENT_DIALOG = "showBuildRequirementDialog";
    private static final String CLONE_REQUIREMENTS = "cloneRequirements";
    private static final String CLEAR_REQUIREMENT_FORM_FOR_ADD_OR_CANCEL = "clearRequirementFormForAddOrCancel";
    private static final String UPDATE_REQUIREMENT = "updateRequirement";
    private static final String DELETE_REQUIREMENT = "deleteRequirement";
    private static final String SHOW_REQUIREMENT_RELATION_PAGE = "showRequirementRelationPage";
    private static final String SHOW_RELATION = "showRelation";
    private static final String ATTACH_COMPANY_AND_REQUIREMENT = "attachCompanyAndRequirement";
    private static final String DETACH_COMPANY_AND_REQUIREMENT = "detachCompanyAndRequirement";
    private static final String CANCEL_RELATION = "cancelRelation";
    private static final String SHOW_DEPOSIT_DIALOG = "showDepositDialog";
    private static final String SHOW_DEPOSIT_DETAIL_SUMMARY = "showDepositDetailSummary";
    private static final String SAVE_DEPOSIT_TO_SUMMARY = "saveDepositToSummary";
    private static final String CLEAR_DEPOSIT_FORM_FOR_ADD_OR_CANCEL = "clearDepositFormForAddOrCancel";
    private static final String DELETE_SELECTED_DEPOSIT = "deleteSelectedDeposit";
    private static final String SHOW_HISTORY = "showHistory";
    private static final String SHOW_HISTORY_DETAIL_SUMMARY = "showHistoryDetailSummary";
    private static final String SHOW_CHARGES_DIALOG = "showChargesDialog";
    private static final String SHOW_CORRESPONDENCE_DIALOG = "showCorrespondenceDialog";
    private static final String SHOW_CONTRACT_SUSPENSE_CREATION = "showContractSuspenseCreation";
    private static final String SHOW_SELECTED_CASH_BATCH = "showSelectedCashBatch";
    private static final String SHOW_SELECTED_CASH_BATCH_CONTRACT = "showSelectedCashBatchContract";
    private static final String SHOW_NEW_BUSINESS_SUSPENSE = "showNewBusinessSuspense";
    private static final String SHOW_SELECTED_NEW_BUSINESS_SUSPENSE = "showSelectedNewBusinessSuspense";
    private static final String SHOW_ORIGINAL_SUSPENSE_INFO = "showOriginalSuspenseInfo";
    private static final String CLOSE_CONTRACT_SUSPENSE_CREATION = "closeContractSuspenseCreation";
    private static final String SHOW_TRANSACTION_DETAIL = "showTransactionDetail";
    private static final String SHOW_PREFERENCES = "showPreferences";
    private static final String SHOW_SELECTED_PREFERENCE = "showSelectedPreference";
    private static final String CLEAR_PREFERENCE_FOR_ADD = "clearPreferenceForAdd";
    private static final String SHOW_CONTRACT_AGENT_INFO = "showContractAgentInfo";
    private static final String SAVE_PREFERENCE = "savePreference";
    private static final String SELECT_PREFERENCE_FOR_CLIENT = "selectPreferenceForClient";
    private static final String SHOW_PREMIUM_DUE_HISTORY_DIALOG = "showPremiumDueHistoryDialog";
     private static final String SHOW_CONTRACT_BILLS_HISTORY_DIALOG = "showContractBillHistoryDialog";
    private static final String SHOW_CHANGE_BATCHID_DIALOG = "showChangeBatchIDDialog";
    private static final String SAVE_BATCHID_CHANGE = "saveBatchIDChange";

    private static final String ADD_NEW_QUOTE = "addNewQuote";

    private static final String SAVE_BANK_INFO = "saveBankInfo";
    private static final String UPDATE_CLIENT_INFO = "updateClientInfo";
    private static final String SAVE_AGENT_SELECTION = "saveAgentSelection";
    private static final String CLOSE_AGENT_SELECTION_DIALOG = "closeAgentSelectionDialog";
    private static final String SAVE_AGENT_TO_SUMMARY = "saveAgentToSummary";
    private static final String SAVE_FUND_TO_SUMMARY = "saveFundToSummary";
    private static final String SAVE_QUOTE_DETAIL = "saveQuoteDetail";
    private static final String SAVE_QUOTE = "saveQuote";
    private static final String SAVE_TRANSACTION_TO_SUMMARY = "saveTransactionToSummary";
    private static final String SAVE_NOTE_TO_SUMMARY = "saveNoteToSummary";
    private static final String SAVE_NOTES = "saveNotes";
    private static final String SAVE_TAXES = "saveTaxes";
    private static final String SAVE_SCHEDULED_PREMIUM = "saveScheduledPremium";
    private static final String SAVE_REQUIREMENT_TO_SUMMARY = "saveRequirementToSummary";
    private static final String GET_QUOTE = "getQuote";
    private static final String ANALYZE_QUOTE = "analyzeQuote";
    private static final String SHOW_ANALYZER = "showAnalyzer";
    private static final String COMMIT_CONTRACT = "commitContract";
    private static final String GENERATE_ISSUE_REPORT = "generateIssueReport";
    private static final String COMMIT_CONTRACT_DETAIL = "commitContractDetail";
    private static final String CANCEL_QUOTE = "cancelQuote";
    private static final String CANCEL_QUOTE_NON_PAYEE_OR_PAYEE = "cancelQuoteNonPayeeOrPayee";
    private static final String CANCEL_TRANSACTION = "cancelTransaction";
    private static final String CANCEL_NOTES = "cancelNotes";
    private static final String CLEAR_INVESTMENTS_FOR_ADD_OR_CANCEL = "clearInvestmentsForAddOrCancel";
    private static final String CLEAR_NOTES_FOR_ADD_OR_CANCEL = "clearNotesForAddOrCancel";
    private static final String CANCEL_REQUIREMENT = "cancelRequirement";

    private static final String DELETE_QUOTE = "deleteQuote";
    private static final String DELETE_SELECTED_CLIENT = "deleteSelectedClient";
    private static final String DELETE_SELECTED_AGENT = "deleteSelectedAgent";
    private static final String DELETE_SELECTED_FUND = "deleteSelectedFund";
    private static final String DELETE_SELECTED_RIDER = "deleteSelectedRider";
    private static final String DELETE_CURRENT_NOTE = "deleteCurrentNote";
    private static final String DELETE_SELECTED_REQUIREMENT = "deleteSelectedRequirement";

    private static final String FIND_CLIENTS_BY_NAME_DOB = "findClientsByNameDOB";
    private static final String FIND_CLIENTS = "findClients";
//    private static final String FIND_CLIENT_BY_TAX_ID = "findClientByTaxId";
    private static final String BUILD_ROLES_AND_CONTRACT_CLIENTS = "buildRolesAndContractClients";

    private static final String SHOW_CALCULATED_VALUES_ADD      = "showCalculatedValuesAdd";
    private static final String SHOW_UL_CALCULATED_VALUES_ADD   = "showUniversalLifeCalculatedValuesAdd";
    private static final String SHOW_CONTRACT_CLIENT_ADD_DIALOG = "showContractClientAddDialog";
    private static final String SHOW_AGENT_DETAIL_SUMMARY       = "showAgentDetailSummary";
    private static final String SHOW_FUND_DETAIL_SUMMARY        = "showFundDetailSummary";
    private static final String SHOW_RIDER_DETAIL               = "showRiderDetail";
    private static final String SAVE_RIDER                      = "saveRider";
    private static final String AUTO_GENERATE_CONTRACT_NUMBER   = "autoGenerateContractNumber";
    private static final String AUTO_GEN_CONTRACT_NBR_FOR_ISSUE = "autoGenContractNbrForIssue";
    private static final String LOAD_QUOTE                      = "loadQuote";
    private static final String SHOW_EDITING_EXCEPTION_DIALOG   = "showEditingExceptionDialog";
    private static final String LOAD_QUOTE_AFTER_SEARCH         = "loadQuoteAfterSearch";
    private static final String SHOW_CANCEL_QUOTE_CONFIRMATION_DIALOG = "showCancelQuoteConfirmationDialog";
    private static final String SHOW_SELECT_COVERAGE_DIALOG     = "showSelectCoverageDialog";
    private static final String LOCK_QUOTE                      = "lockQuote";
    private static final String SHOW_JUMP_TO_DIALOG             = "showJumpToDialog";
    private static final String CONFIRM_QUOTE_DELETE            = "confirmQuoteDelete";
    private static final String UPDATE_PAYEE_OVERRIDE           = "updatePayeeOverride";
    private static final String SHOW_PAYEE_DETAIL_SUMMARY       = "showPayeeDialogDetailSummary";
    private static final String CANCEL_PAYEE_OVERRIDE           = "cancelPayeeOverride";
    private static final String DELETE_PAYEE_OVERRIDE           = "deletePayeeOverride";
    private static final String ANALYZER_DIALOG                 = "/engine/jsp/dbugmain.jsp";
    private static final String SHOW_VO_EDIT_EXCEPTION_DIALOG   = "showVOEditExceptionDialog";
    private static final String SHOW_DEPOSIT_DELETION_MESSAGE   = "showDepositDeletionMessage";
    private static final String SHOW_CHANGE_COVERAGE_DIALOG     = "showChangeCoverageDialog";
    private static final String CHANGE_COMPANY_STRUCTURE_COVERAGE_ = "changeCompanyStructureCoverage";
    private static final String SAVE_NOTES_ONLY                 = "saveNotesOnly";
    private static final String DELETE_CURRENT_NOTE_ONLY        = "deleteCurrentNoteOnly";
    private static final String CLOSE_ONLINE_REPORT             = "closeOnlineReport";
    private static final String SELECT_SUSPENSE_FOR_ADJUSTMENT  = "selectSuspenseForAdjustment";
    private static final String SHOW_TAX_ADJUSTMENT_DIALOG      = "showTaxAdjustmentDialog";
    private static final String SAVE_TAX_ADJUSTMENT             = "saveTaxAdjustment";

    private static final String IMPORT_NEW_BUSINESS             = "importNewBusiness";
    private static final String COMPLETE_NEW_BUSINESS_IMPORT    = "completeNewBusinessImport";
    private static final String SHOW_PROPOSAL_DIALOG = "showPropsalDialog";
    private static final String SHOW_QUESTIONNAIRE_RESPONSE_DIALOG = "showQuestionnaireResponseDialog";
//    private static final String LOAD_TRANSACTIONS                  = "loadTransactions";
    private static final String SAVE_DATES_DIALOG               = "saveDatesDialog";

    private static final String SHOW_QUOTE_BILLING_DIALOG = "showQuoteBillingDialog";
    private static final String SHOW_QUOTE_SCHEDULED_PREMIUM_DIALOG = "showQuoteScheduledPremiumDialog";
    private static final String SAVE_BILLING_CHANGE = "saveBillingChange";
    private static final String CHANGE_TO_INDIVIDUAL_BILL = "changeToIndividualBill";
    private static final String CHANGE_TO_LIST_BILL = "changeToListBill";
    private static final String SAVE_CHANGE_TO_INDIVIDUAL_BILL = "saveChangeToIndividualBill";
    private static final String SAVE_CHANGE_TO_LIST_BILL = "saveChangeToListBill";
    private static final String RUN_REVERSAL = "runReversal";
    private static final String ADD_WITHDRAWAL_TRANSACTION = "addWithdrawalTransaction";
    private static final String SAVE_WITHDRAWAL_TRANSACTION = "saveWithdrawalTransaction";
    private static final String CANCEL_WITHDRAWAL_TRANSACTION = "cancelWithdrawalTransaction";
    private static final String DELETE_WITHDRAWAL_TRANSACTION = "deleteWithdrawalTransaction";
    private static final String ANALYZE_TRANSACTION = "analyzeTransaction";
    private static final String FIND_DEPARTMENT_LOCATIONS = "findDepartmentLocations";
    private static final String FIND_BATCH_CONTRACT_SETUPS = "findBatchContractSetups";
    private static final String SHOW_CLASS_GENDER_RATINGS_DIALOG = "showClassGenderRatingsDialog";
    private static final String SAVE_CLASS_GENDER_RATINGS_DIALOG = "saveClassGenderRatingsDialog";
    private static final String SHOW_ENROLLMENTLEADSERVICEAGENT_INFO = "showEnrollmentLeadServiceAgentInfo";
    private static final String SHOW_RIDER_COVERAGE_SELECTION_DIALOG = "showRiderCoverageSelectionDialog";
    private static final Object SAVE_RIDER_COVERAGE =  "saveRiderCoverage";


    //Pages that the Tran will return

    private static final String MAIN_FRAMESET = "/quote/jsp/mainframeset.jsp";

    public static final String QUOTE_COMMIT_MAIN = "/quote/jsp/quoteCommitMain.jsp";
    public static final String QUOTE_DEFERRED_ANNUITY_MAIN = "/quote/jsp/quoteCommitDeferredAnnuityMain.jsp";
    public static final String QUOTE_LIFE_MAIN = "/quote/jsp/quoteCommitLifeMain.jsp";
    public static final String QUOTE_TRAD_MAIN = "/quote/jsp/quoteCommitTradMain.jsp";
    public static final String QUOTE_AH_MAIN = "/quote/jsp/quoteCommitA&HMain.jsp";
    public static final String QUOTE_UNIVERSAL_LIFE_MAIN = "/quote/jsp/quoteCommitUniversalLifeMain.jsp";
    private static final String QUOTE_COMMIT_RIDER = "/quote/jsp/quoteCommitRider.jsp";
    private static final String QUOTE_COMMIT_LIFE_RIDER = "/quote/jsp/quoteCommitLifeRider.jsp";
    private static final String QUOTE_COMMIT_INVESTMENTS = "/quote/jsp/quoteCommitInvestments.jsp";
    private static final String QUOTE_COMMIT_INSURED = "/quote/jsp/quoteCommitInsured.jsp";
    private static final String QUOTE_COMMIT_NON_PAYEE = "/quote/jsp/quoteCommitNonPayee.jsp";
    private static final String QUOTE_COMMIT_PAYEE = "/quote/jsp/quoteCommitPayee.jsp";
    private static final String QUOTE_COMMIT_AGENT = "/quote/jsp/quoteCommitAgent.jsp";
    private static final String QUOTE_SCHED_EVENTS = "/quote/jsp/quoteTransScheduledEvent.jsp";
    private static final String QUOTE_REQUIREMENTS = "/quote/jsp/quoteRequirements.jsp";
    private static final String BUILD_REQUIREMENT_DIALOG = "/quote/jsp/buildRequirementDialog.jsp";
    private static final String QUOTE_TRANS_TYPE_DIALOG = "/quote/jsp/quoteTransactionTypeDialog.jsp";
    private static final String QUOTE_SCHEDULED_PREMIUM_DIALOG = "/quote/jsp/quoteScheduledPremiumDialog.jsp";
    private static final String QUOTE_COMMIT_PAYEE_DIALOG = "/quote/jsp/quoteCommitPayeeDialog.jsp";
    private static final String REQUIREMENT_TABLE = "/quote/jsp/requirementTable.jsp";
    private static final String REQUIREMENT_RELATION_PAGE = "/quote/jsp/requirementRelationPage.jsp";
    private static final String JUMP_TO_DIALOG = "/common/jsp/jumpToDialog.jsp";
    private static final String QUOTE_COMMIT_HISTORY = "/quote/jsp/quoteCommitHistoryDetail.jsp";
    private static final String QUOTE_COMMIT_CHANGE_HISTORY = "/quote/jsp/quoteCommitChangeHistoryDetail.jsp";

//    private static final String IMPORT_NEW_BUSINESS_PAGE         = "/quote/jsp/importNewBusiness.jsp";
    private static final String IMPORT_NEW_BUSINESS_RESPONSE_DIALOG = "/quote/jsp/importNewBusinessResponseDialog.jsp";

    // Dialog boxes that the Tran will return
    private static final String QUOTE_COMMIT_CALCULATED_VALUE_DIALOG = "/quote/jsp/quoteCommitCalculatedValueDialog.jsp";
    private static final String QUOTE_COMMIT_UL_CALCULATED_VALUE_DIALOG = "/quote/jsp/quoteCommitUniversalLifeCalculatedValueDialog.jsp";
    private static final String CONTRACT_CLIENT_ADD_DIALOG = "/quote/jsp/contractClientAdd.jsp";
    private static final String QUOTE_COMMIT_CONTRACT_NUMBER_DIALOG = "/quote/jsp/quoteCommitContractNumberDialog.jsp";
    private static final String ISSUE_CONTRACT_DIALOG = "/quote/jsp/issueContractDialog.jsp";
    public static final String QUOTE_COMMIT_NOTES_DIALOG = "/quote/jsp/quoteCommitNotesDialog.jsp";
//    private static final String QUOTE_COMMIT_TAXES_DIALOG = "/quote/jsp/quoteCommitTaxesDialog.jsp";
    private static final String EDITING_EXCEPTION_DIALOG = "/common/jsp/editingExceptionDialog.jsp";
    private static final String CANCEL_QUOTE_CONFIRMATION_DIALOG = "/quote/jsp/cancelQuoteConfirmationDialog.jsp";
    private static final String SELECT_COVERAGE_DIALOG = "/quote/jsp/selectCoverageDialog.jsp";
    private static final String CONFIRM_QUOTE_DELETE_DIALOG = "/quote/jsp/confirmQuoteDeleteDialog.jsp";
    private static final String VO_EDIT_EXCEPTION_DIALOG = "/common/jsp/VOEditExceptionDialog.jsp";
    private static final String AGENT_HIERARCHY_DIALOG = "/quote/jsp/agentHierarchyDialog.jsp";      // OBSOLETE ??
//    private static final String ADVANCE_DIALOG = "/quote/jsp/advanceDialog.jsp";
    private static final String COMMISSION_OVERRIDES_DIALOG = "/quote/jsp/commissionOverridesDialog.jsp";
    private static final String AGENT_SELECTION_DIALOG = "/quote/jsp/agentSelectionDialog.jsp";
    private static final String MANUAL_REQUIREMENT_SELECTION_DIALOG = "/quote/jsp/manualRequirementSelectionDialog.jsp";
    private static final String DEPOSIT_DIALOG = "/quote/jsp/depositDialog.jsp";
    private static final String CHARGES_DIALOG = "/quote/jsp/chargesDialog.jsp";
    private static final String CORRESPONDENCE_DIALOG = "/contract/jsp/correspondenceDialog.jsp";
    private static final String CONTRACT_SUSPENSE_CREATION = "/quote/jsp/contractSuspenseCreation.jsp";
    private static final String NEW_BUSINESS_SUSPENSE = "/quote/jsp/newBusinessSuspense.jsp";
    private static final String ORIGINAL_SUSPENSE_INFO = "/quote/jsp/originalSuspenseInfo.jsp";
    private static final String DEPOSIT_DELETION_MESSAGE_DIALOG = "/quote/jsp/depositDeletionMessageDialog.jsp";
    private static final String CHANGE_COVERAGE_DIALOG = "/quote/jsp/changeCoverageDialog.jsp";
    private static final String TAX_ADJUSTMENT_DIALOG = "/quote/jsp/taxAdjustmentDialog.jsp";

    private static final String PROPOSAL_DIALOG               = "/quote/jsp/proposalDialog.jsp";
    private static final String QUESTIONNAIRE_RESPONSE_DIALOG = "/common/jsp/questionnaireResponseDialog.jsp";
    private static final String QUOTE_TRANS_WITHDRAWAL        = "/quote/jsp/quoteTransWithdrawal.jsp";
    private static final String QUOTE_COMMIT_DATES_DIALOG = "/quote/jsp/datesDialog.jsp";

    private static final String QUOTE_LIST_BILLING_DIALOG = "/quote/jsp/quoteListBillingDialog.jsp";
    private static final String QUOTE_INDIVIDUAL_BILLING_DIALOG = "/quote/jsp/quoteIndividualBillingDialog.jsp";
    private static final String QUOTE_LIST_UL_BILLING_DIALOG = "/quote/jsp/quoteListUniversalLifeBillingDialog.jsp";
    private static final String QUOTE_INDIVIDUAL_UL_BILLING_DIALOG = "/quote/jsp/quoteIndividualUniversalLifeBillingDialog.jsp";
    private static final String CHANGE_TO_INDIVIDUAL_BILL_DIALOG = "/quote/jsp/changeToIndividualBillDialog.jsp";
    private static final String CHANGE_TO_LIST_BILL_DIALOG = "/quote/jsp/changeToListBillDialog.jsp";
    private static final String CLASS_GENDER_RATINGS_DIALOG = "/quote/jsp/classGenderRatingsDialog.jsp";

    private static final String PREFERENCES_DIALOG = "/quote/jsp/preferencesDialog.jsp";
    private static final String CONTRACT_AGENT_INFO_DIALOG = "/quote/jsp/contractAgentInformationDialog.jsp";
    private static final String PREMIUM_DUE_HISTORY_DIALOG = "/quote/jsp/premiumDueHistoryDialog.jsp";//
     private static final String CONTRACT_BILLS_HISTORY = "/quote/jsp/contractBillHistoryDialog.jsp";
    
    private static final String ENROLLMENT_LEAD_SERVICE_AGENT_DIALOG = "/contract/jsp/enrollmentLeadServiceAgentInfoDialog.jsp";
    private static final String TEMPLATE_DIALOG = "/common/jsp/template/template-dialog.jsp";
    private static final String CHANGE_BATCHID_DIALOG = "/quote/jsp/changeBatchIDDialog.jsp";
    private static final String RIDER_COVERAGE_SELECTION_DIALOG = "/quote/jsp/quoteCommitRiderCoverageSelectionDialog.jsp";


    /**
     * NOTE: CompanyStructure and ProductStructure are used interchangeably
     * @param appReqBlock
     * @return
     * @throws Exception
     */
    public String execute(AppReqBlock appReqBlock) throws Exception
    {
        String action = appReqBlock.getReqParm("action");
        String returnPage = null;

        preProcessRequest(appReqBlock);

        try
        {
            if (action.equals(SHOW_QUOTE_MAIN_DEFAULT))
            {
                returnPage = showQuoteMainDefault(appReqBlock);
            }
            else if (action.equals(SHOW_QUOTE_MAIN_CONTENT))
            {
                returnPage = showQuoteMainContent(appReqBlock);
            }
            else if (action.equals(SHOW_QUOTE_RIDERS))
            {
                returnPage = showQuoteRiders(appReqBlock);
            }
            else if (action.equals(SHOW_QUOTE_INVESTMENTS))
            {
                returnPage = showQuoteInvestments(appReqBlock);
            }
            else if (action.equals(CLEAR_INVESTMENTS_FOR_ADD_OR_CANCEL))
            {
                returnPage = clearInvestmentsForAddOrCancel(appReqBlock);
            }
            else if (action.equals(CLEAR_NOTES_FOR_ADD_OR_CANCEL))
            {
                returnPage = clearNotesForAddOrCancel(appReqBlock);
            }
            else if (action.equals(SHOW_QUOTE_NON_PAYEE_OR_PAYEE))
            {
                returnPage = showQuoteNonPayeeOrPayee(appReqBlock);
            }
            else if (action.equals(SHOW_QUOTE_AGENTS))
            {
                returnPage = showQuoteAgents(appReqBlock);
            }
            else if (action.equals(GET_QUOTE))
            {
                returnPage = getQuote(appReqBlock);
            }
            else if (action.equals(ANALYZE_QUOTE))
            {
                returnPage = analyzeQuote(appReqBlock);
            }
            else if (action.equals(UPDATE_CLIENT_INFO))
            {
                returnPage = updateClientInfo(appReqBlock);
            }
            else if (action.equals(DELETE_QUOTE))
            {
                returnPage = deleteQuote(appReqBlock);
            }
            else if (action.equals(CANCEL_QUOTE))
            {
                returnPage = cancelQuote(appReqBlock);
            }
            else if (action.equals(SHOW_CALCULATED_VALUES_ADD))
            {
                returnPage = showCalculatedValuesAdd(appReqBlock);
            }
            else if (action.equals(SHOW_UL_CALCULATED_VALUES_ADD))
            {
                returnPage = showUniversalLifeCalculatedValuesAdd(appReqBlock);
            }
            else if (action.equals(SHOW_ANALYZER))
            {
                returnPage = showAnalyzer(appReqBlock);
            }
            else if (action.equals(SHOW_PAYEE_DIALOG))
            {
                returnPage = showPayeeDialog(appReqBlock);
            }
            else if (action.equals(CLOSE_PAYEE_SHOW_SCHED_EVENT))
            {
                returnPage = closePayeeShowSchedEvent(appReqBlock);
            }
            else if (action.equals(SHOW_CONTRACT_CLIENT_ADD_DIALOG))
            {
                returnPage = showContractClientAddDialog(appReqBlock);
            }
            else if (action.equals(SAVE_BANK_INFO))
            {
                returnPage = saveBankInfo(appReqBlock);
            }
            else if (action.equals(SHOW_CLIENT_DETAIL_SUMMARY))
            {
                returnPage = showClientDetailSummary(appReqBlock);
            }
            else if (action.equals(DELETE_SELECTED_CLIENT))
            {
                returnPage = deleteSelectedClient(appReqBlock);
            }
            else if (action.equals(DELETE_SELECTED_AGENT))
            {
                returnPage = deleteSelectedAgent(appReqBlock);
            }
            else if (action.equals(DELETE_SELECTED_FUND))
            {
                returnPage = deleteSelectedFund(appReqBlock);
            }
            else if (action.equals(DELETE_SELECTED_RIDER))
            {
                returnPage = deleteSelectedRider(appReqBlock);
            }
            else if (action.equals(CANCEL_QUOTE_NON_PAYEE_OR_PAYEE))
            {
                returnPage = cancelQuoteNonPayeeOrPayee(appReqBlock);
            }
            else if (action.equals(CLOSE_AGENT_SELECTION_DIALOG))
            {
                returnPage = closeAgentSelectionDialog(appReqBlock);
            }
            else if (action.equals(SAVE_AGENT_SELECTION))
            {
                returnPage = saveAgentSelection(appReqBlock);
            }
            else if (action.equals(SAVE_AGENT_TO_SUMMARY))
            {
                returnPage = saveAgentToSummary(appReqBlock);
            }
            else if (action.equals(SAVE_FUND_TO_SUMMARY))
            {
                returnPage = saveFundToSummary(appReqBlock);
            }
            else if (action.equals(CLEAR_QUOTE_COMMIT_AGENT_FORM))
            {
                returnPage = clearQuoteCommitAgentForm(appReqBlock);
            }
            else if (action.equals(SHOW_AGENT_DETAIL_SUMMARY))
            {
                returnPage = showAgentDetailSummary(appReqBlock);
            }
            else if (action.equals(SHOW_AGENT_HIERARCHY_DIALOG))
            {
                returnPage = showAgentHierarchyDialog(appReqBlock);
            }
//            else if (action.equals(SHOW_ADVANCE_DIALOG))
//            {
//                returnPage = showAdvanceDialog(appReqBlock);
//            }
            else if (action.equals(SAVE_ADVANCE))
            {
                returnPage = saveAdvance(appReqBlock);
            }
            else if (action.equals(SHOW_SELECTED_HIERARCHY_ROW))
            {
                returnPage = showSelectedHierarchyRow(appReqBlock);
            }
            else if (action.equals(SELECT_COMM_PROFILE_FOR_AGENT))
            {
                returnPage = selectCommProfileForAgent(appReqBlock);
            }
            else if (action.equals(SAVE_AGENT_HIERARCHY))
            {
                returnPage = saveAgentHierarchy(appReqBlock);
            }
            else if (action.equals(CLOSE_AGENT_HIERARCHY))
            {
                returnPage = closeAgentHierarchy(appReqBlock);
            }
            else if (action.equals(SHOW_COMMISSION_OVERRIDES))
            {
                returnPage = showCommissionOverrides(appReqBlock);
            }
            else if (action.equals(SAVE_COMMISSION_OVERRIDES))
            {
                returnPage = saveCommissionOverrides(appReqBlock);
            }
            else if (action.equals(DELETE_COMMISSION_OVERRIDES))
            {
                returnPage = deleteCommissionOverrides(appReqBlock);
            }
            else if (action.equals(CANCEL_COMMISSION_OVERRIDES))
            {
                returnPage = cancelCommissionOverrides(appReqBlock);
            }
            else if (action.equals(SHOW_AGENT_SELECTION_DIALOG))
            {
                returnPage = showAgentSelectionDialog(appReqBlock);
            }
            else if (action.equals(SHOW_REPORT_TO_AGENT))
            {
                returnPage = showReportToAgent(appReqBlock);
            }
            else if (action.equals(SHOW_FUND_DETAIL_SUMMARY))
            {
                returnPage = showFundDetailSummary(appReqBlock);
            }
            else if (action.equals(SAVE_RIDER))
            {
                returnPage = saveRider(appReqBlock);
            }
            else if (action.equals(SHOW_RIDER_DETAIL))
            {
                returnPage = showRiderDetail(appReqBlock);
            }
            else if (action.equals(ADD_NEW_QUOTE))
            {
                returnPage = addNewQuote(appReqBlock);
            }
            else if (action.equals(AUTO_GENERATE_CONTRACT_NUMBER))
            {
                returnPage = autoGenerateContractNumber(appReqBlock);
            }
            else if (action.equals(AUTO_GEN_CONTRACT_NBR_FOR_ISSUE))
            {
                returnPage = autoGenContractNbrForIssue(appReqBlock);
            }
            else if (action.equals(SAVE_QUOTE_DETAIL))
            {
                returnPage = saveQuoteDetail(appReqBlock);
            }
            else if (action.equals(SAVE_QUOTE))
            {
                boolean returnToUser = true;
                returnPage = saveQuote(appReqBlock, returnToUser);
            }
            else if (action.equals(COMMIT_CONTRACT))
            {
                returnPage = commitContract(appReqBlock);
            }
            else if (action.equals(GENERATE_ISSUE_REPORT))
            {
                returnPage = generateIssueReport(appReqBlock);
            }
            else if (action.equals(COMMIT_CONTRACT_DETAIL))
            {
                returnPage = commitContractDetail(appReqBlock);
            }
            else if (action.equals(LOAD_QUOTE))
            {
                returnPage = loadQuote(appReqBlock);
            }
            else if (action.equals(SHOW_QUOTE_SCHED_EVENTS))
            {
                returnPage = showQuoteSchedEvents(appReqBlock);
            }
            else if (action.equals(SHOW_TRANSACTION_TYPE_DIALOG))
            {
                returnPage = showTransactionTypeDialog(appReqBlock);
            }
            else if (action.equals(SHOW_TRANSACTION_DEFAULT))
            {
                returnPage = showTransactionDefault(appReqBlock);
            }
            else if (action.equals(SAVE_TRANSACTION_TO_SUMMARY))
            {
                returnPage = saveTransactionToSummary(appReqBlock);
            }
            else if (action.equals(SHOW_TRANS_DETAIL_SUMMARY))
            {
                returnPage = showTransactionDetailSummary(appReqBlock);
            }
            else if (action.equals(CANCEL_TRANSACTION))
            {
                returnPage = cancelTransaction(appReqBlock);
            }
            else if (action.equals(SHOW_EDITING_EXCEPTION_DIALOG))
            {
                returnPage = showEditingExceptionDialog(appReqBlock);
            }
            else if (action.equals(LOAD_QUOTE_AFTER_SEARCH))
            {
                returnPage = loadQuoteAfterSearch(appReqBlock);
            }
            else if (action.equals(SHOW_CANCEL_QUOTE_CONFIRMATION_DIALOG))
            {
                returnPage = showCancelQuoteConfirmationDialog(appReqBlock);
            }
            else if (action.equals(SHOW_SELECT_COVERAGE_DIALOG))
            {
                returnPage = showSelectCoverageDialog(appReqBlock);
            }
            else if (action.equals(LOCK_QUOTE))
            {
                returnPage = lockQuote(appReqBlock);
            }
            else if (action.equals(SHOW_JUMP_TO_DIALOG))
            {
                returnPage = showJumpToDialog(appReqBlock);
            }
            else if (action.equals(SHOW_DATES_DIALOG))
            {
                returnPage = showDatesDialog(appReqBlock);
            }
            else if (action.equals(SAVE_TAXES))
            {
                returnPage = saveTaxes(appReqBlock);
            }
            else if (action.equals(SAVE_SCHEDULED_PREMIUM))
            {
                returnPage = saveScheduledPremium(appReqBlock);
            }
            else if (action.equals(SHOW_NOTES_DIALOG))
            {
                returnPage = showNotesDialog(appReqBlock);
            }
            else if (action.equals(SHOW_NOTES_DETAIL_SUMMARY))
            {
                returnPage = showNotesDetailSummary(appReqBlock);
            }
            else if (action.equals(CANCEL_NOTES))
            {
                returnPage = cancelNotes(appReqBlock);
            }
            else if (action.equals(SAVE_NOTE_TO_SUMMARY))
            {
                returnPage = saveNoteToSummary(appReqBlock);
            }
            else if (action.equals(SAVE_NOTES))
            {
                returnPage = saveNotes(appReqBlock);
            }
            else if (action.equals(DELETE_CURRENT_NOTE))
            {
                returnPage = deleteCurrentNote(appReqBlock);
            }
            else if (action.equals(CONFIRM_QUOTE_DELETE))
            {
                returnPage = confirmQuoteDelete();
            }
            else if (action.equals(UPDATE_PAYEE_OVERRIDE))
            {
                returnPage = updatePayeeOverride(appReqBlock);
            }
            else if (action.equals(SHOW_PAYEE_DETAIL_SUMMARY))
            {
                returnPage = showPayeeDetailSummary(appReqBlock);
            }
            else if (action.equals(CANCEL_PAYEE_OVERRIDE))
            {
                returnPage = cancelPayeeOverride(appReqBlock);
            }
            else if (action.equals(DELETE_PAYEE_OVERRIDE))
            {
                returnPage = deletePayeeOverride(appReqBlock);
            }
            else if (action.equals(FIND_CLIENTS_BY_NAME_DOB))
            {
                returnPage = findClientsByNameDOB(appReqBlock);
            }
            else if (action.equals(FIND_CLIENTS))
            {
                returnPage = findClients(appReqBlock);
            }
            else if (action.equals(BUILD_ROLES_AND_CONTRACT_CLIENTS))
            {
                returnPage = buildRolesAndContractClients(appReqBlock);
            }
            else if (action.equals(SHOW_DEPOSIT_DIALOG))
            {
                returnPage = showDepositDialog(appReqBlock);
            }
            else if (action.equals(SHOW_DEPOSIT_DETAIL_SUMMARY))
            {
                returnPage = showDepositDetailSummary(appReqBlock);
            }
            else if (action.equals(SAVE_DEPOSIT_TO_SUMMARY))
            {
                returnPage = saveDepositToSummary(appReqBlock);
            }
            else if (action.equals(CLEAR_DEPOSIT_FORM_FOR_ADD_OR_CANCEL))
            {
                returnPage = clearDepositFormForAddOrCancel(appReqBlock);
            }
            else if (action.equals(SHOW_DEPOSIT_DELETION_MESSAGE))
            {
                returnPage = showDepositDeletionMessage(appReqBlock);
            }
            else if (action.equals(DELETE_SELECTED_DEPOSIT))
            {
                returnPage = deleteSelectedDeposit(appReqBlock);
            }
            else if (action.equals(SHOW_QUOTE_REQUIREMENTS))
            {
                returnPage = showQuoteRequirements(appReqBlock);
            }
            else if (action.equals(SHOW_REQUIREMENT_DETAIL))
            {
                returnPage = showRequirementDetail(appReqBlock);
            }
            else if (action.equals(SAVE_REQUIREMENT_TO_SUMMARY))
            {
                returnPage = saveRequirementToSummary(appReqBlock);
            }
            else if (action.equals(SHOW_BUILD_REQUIREMENT_DIALOG))
            {
                returnPage = showBuildRequirementDialog(appReqBlock);
            }
            else if (action.equals(CLONE_REQUIREMENTS))
            {
                returnPage = cloneRequirements(appReqBlock);
            }
            else if (action.equals(DELETE_SELECTED_REQUIREMENT))
            {
                returnPage = deleteSelectedRequirement(appReqBlock);
            }
            else if (action.equals(CANCEL_REQUIREMENT))
            {
                returnPage = cancelRequirement(appReqBlock);
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
            else if (action.equals(SHOW_REQUIREMENT_TABLE))
            {
                returnPage = showRequirementTable(appReqBlock);
            }
            else if (action.equals(SHOW_REQUIREMENT_DETAIL_SUMMARY))
            {
                returnPage = showRequirementDetailSummary(appReqBlock);
            }
            else if (action.equals(CLEAR_REQUIREMENT_FORM_FOR_ADD_OR_CANCEL))
            {
                returnPage = clearRequirementFormForAddOrCancel(appReqBlock);
            }
            else if (action.equals(UPDATE_REQUIREMENT))
            {
                returnPage = updateRequirement(appReqBlock);
            }
            else if (action.equals(DELETE_REQUIREMENT))
            {
                returnPage = deleteRequirement(appReqBlock);
            }
            else if (action.equals(SHOW_REQUIREMENT_RELATION_PAGE))
            {
                returnPage = showRequirementRelationPage(appReqBlock);
            }
            else if (action.equals(SHOW_RELATION))
            {
                returnPage = showRelation(appReqBlock);
            }
            else if (action.equals(ATTACH_COMPANY_AND_REQUIREMENT))
            {
                returnPage = attachCompanyAndRequirement(appReqBlock);
            }
            else if (action.equals(DETACH_COMPANY_AND_REQUIREMENT))
            {
                returnPage = detachCompanyAndRequirement(appReqBlock);
            }
            else if (action.equals(CANCEL_RELATION))
            {
                returnPage = cancelRelation(appReqBlock);
            }
            else if (action.equals(SHOW_HISTORY))
            {
                returnPage = showHistory();
            }
            else if (action.equals(SHOW_HISTORY_DETAIL_SUMMARY))
            {
                returnPage = showHistoryDetailSummary(appReqBlock);
            }
            else if (action.equals(SHOW_CHARGES_DIALOG))
            {
                returnPage = showChargesDialog(appReqBlock);
            }
            else if (action.equals(SHOW_CORRESPONDENCE_DIALOG))
            {
                returnPage = showCorrespondenceDialog(appReqBlock);
            }
            else if (action.equals(SHOW_CONTRACT_SUSPENSE_CREATION))
            {
                returnPage = showContractSuspenseCreation(appReqBlock);
            }
            else if (action.equals(SHOW_SELECTED_CASH_BATCH))
            {
                returnPage = showSelectedCashBatch(appReqBlock);
            }
            else if (action.equals(SHOW_SELECTED_CASH_BATCH_CONTRACT))
            {
                returnPage = showSelectedCashBatchContract(appReqBlock);
            }
            else if (action.equals(CLOSE_CONTRACT_SUSPENSE_CREATION))
            {
                returnPage = closeContractSuspenseCreation(appReqBlock);
            }
            else if (action.equals(SHOW_NEW_BUSINESS_SUSPENSE))
            {
                returnPage = showNewBusinessSuspense(appReqBlock);
            }
            else if (action.equals(SHOW_SELECTED_NEW_BUSINESS_SUSPENSE))
            {
                returnPage = showSelectedNewBusinessSuspense(appReqBlock);
            }
            else if (action.equals(SHOW_ORIGINAL_SUSPENSE_INFO))
            {
                returnPage = showOriginalSuspenseInfo(appReqBlock);
            }
            else if (action.equals(SHOW_CHANGE_COVERAGE_DIALOG))
            {
                returnPage = showChangeCoverageDialog(appReqBlock);
            }
            else if (action.equals(CHANGE_COMPANY_STRUCTURE_COVERAGE_))
            {
                returnPage = changeCompanyStructureCoverage(appReqBlock);
            }
            else if (action.equals(SHOW_VO_EDIT_EXCEPTION_DIALOG))
            {
                returnPage = showVOEditExceptionDialog(appReqBlock);
            }
            else if (action.equals(SAVE_NOTES_ONLY))
            {
               returnPage =  NewBusinessNotesUtil.saveNotesOnly(appReqBlock);
            }
            else if (action.equals(DELETE_CURRENT_NOTE_ONLY))
            {
                new NewBusinessUseCaseComponent().updateNewBusinessNotes();
                returnPage = NewBusinessNotesUtil.deleteCurrentNote(appReqBlock);
            }
            else if (action.equals(CLOSE_ONLINE_REPORT))
            {
                returnPage = closeOnlineReport(appReqBlock);
            }
            else if (action.equals(IMPORT_NEW_BUSINESS))
            {
                returnPage = importNewBusiness(appReqBlock);
            }
            else if (action.equals(COMPLETE_NEW_BUSINESS_IMPORT))
            {
                returnPage = completeNewBusinessImport(appReqBlock);
            }
            else if (action.equals(COMPLETE_NEW_BUSINESS_IMPORT))
            {
                returnPage = completeNewBusinessImport(appReqBlock);
            }
            else if (action.equals(SELECT_SUSPENSE_FOR_ADJUSTMENT))
            {
                returnPage = selectSuspenseForAdjustment(appReqBlock);
            }
            else if (action.equals(SHOW_TAX_ADJUSTMENT_DIALOG))
            {
                returnPage = showTaxAdjustmentDialog(appReqBlock);
            }
            else if (action.equals(SAVE_TAX_ADJUSTMENT))
            {
                returnPage = saveTaxAdjustment(appReqBlock);
            }
            else if (action.equals(SHOW_PROPOSAL_DIALOG))
            {
                returnPage = showProposalDialog(appReqBlock);
            }
            else if (action.equals(SHOW_QUESTIONNAIRE_RESPONSE_DIALOG))
            {
                returnPage = showQuestionnaireResponseDialog(appReqBlock);
            }
            else if (action.equals(SHOW_TRANSACTION_DETAIL))
            {
                returnPage = showTransactionDetail(appReqBlock);
            }
            else if (action.equals(SAVE_DATES_DIALOG))
            {
                returnPage = saveDatesDialog(appReqBlock);
            }
            else if (action.equals(SHOW_QUOTE_BILLING_DIALOG))
            {
                returnPage = showQuoteBillingDialog(appReqBlock);
            }
            else if (action.equals(SHOW_QUOTE_SCHEDULED_PREMIUM_DIALOG))
            {
                returnPage = showQuoteScheduledPremiumDialog(appReqBlock);
            }
            else if (action.equals(SAVE_BILLING_CHANGE))
            {
                returnPage = saveBillingChange(appReqBlock);
            }
            else if (action.equals(CHANGE_TO_INDIVIDUAL_BILL))
            {
                returnPage = changeToIndividualBill(appReqBlock);
            }
            else if (action.equals(CHANGE_TO_LIST_BILL))
            {
                returnPage = changeToListBill(appReqBlock);
            }
            else if (action.equals(SAVE_CHANGE_TO_INDIVIDUAL_BILL))
            {
                returnPage = saveChangeToIndividualBill(appReqBlock);
            }
            else if (action.equals(SAVE_CHANGE_TO_LIST_BILL))
            {
                returnPage = saveChangeToListBill(appReqBlock);
            }
            else if (action.equals(RUN_REVERSAL))
            {
                returnPage = runReversal(appReqBlock);
            }
            else if (action.equals(ADD_WITHDRAWAL_TRANSACTION))
            {
                returnPage = addWithdrawalTransaction(appReqBlock);
            }
            else if (action.equals(SAVE_WITHDRAWAL_TRANSACTION))
            {
                returnPage = saveWithdrawalTransaction(appReqBlock);
            }
            else if (action.equals(CANCEL_WITHDRAWAL_TRANSACTION))
            {
                returnPage = cancelWithdrawalTransaction(appReqBlock);
            }
            else if (action.equals(DELETE_WITHDRAWAL_TRANSACTION))
            {
                returnPage = deleteWithdrawalTransaction(appReqBlock);
            }
            else if (action.equals(ANALYZE_TRANSACTION))
            {
                returnPage = analyzeTransaction(appReqBlock);
            }
            else if (action.equals(FIND_DEPARTMENT_LOCATIONS))
            {
                returnPage = findDepartmentLocations(appReqBlock);
            }
            else if (action.equals(FIND_BATCH_CONTRACT_SETUPS))
            {
                returnPage = findBatchContractSetups(appReqBlock);
            }
            else if (action.equals(SHOW_CLASS_GENDER_RATINGS_DIALOG))
            {
                returnPage = showClassGenderRatingsDialog(appReqBlock);
            }
            else if (action.equals(SAVE_CLASS_GENDER_RATINGS_DIALOG))
            {
                returnPage = saveClassGenderRatingsDialog(appReqBlock);
            }
            else if (action.equals(SHOW_PREFERENCES))
            {
                returnPage = showPreferences(appReqBlock);
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
            else if (action.equalsIgnoreCase(SELECT_PREFERENCE_FOR_CLIENT))
            {
                return selectPreferenceForClient(appReqBlock);
            }
            else if (action.equals(SHOW_PREMIUM_DUE_HISTORY_DIALOG))
            {
                returnPage = showPremiumDueHistoryDialog(appReqBlock);
            }
            else if (action.equals(SHOW_ENROLLMENTLEADSERVICEAGENT_INFO))
            {
                returnPage = showEnrollmentLeadServiceAgentInfo(appReqBlock);
            }
            else if (action.equals(SHOW_CHANGE_BATCHID_DIALOG))
            {
                returnPage = showChangeBatchIDDialog(appReqBlock);
            }
            else if (action.equals(SAVE_BATCHID_CHANGE))
            {
                returnPage = saveBatchIDChange(appReqBlock);
            }
            else if (action.equals(SHOW_CONTRACT_BILLS_HISTORY_DIALOG))
            {
                returnPage = showContractBillHistoryDialog(appReqBlock);
            }
            else if (action.equals(SHOW_RIDER_COVERAGE_SELECTION_DIALOG))
            {
                returnPage = showRiderCoverageSelectionDialog(appReqBlock);
            }
            else if (action.equals(SAVE_RIDER_COVERAGE))
            {
                returnPage = saveRiderCoverageSelection(appReqBlock);
            }

            else
            {
                throw new Exception("QuoteDetailTran: Invalid action " + action);
            }

            if (!returnPage.equals(QuoteDetailTran.EDITING_EXCEPTION_DIALOG) &&
                !returnPage.equals(VO_EDIT_EXCEPTION_DIALOG) &&
                !returnPage.equals(QUOTE_COMMIT_CONTRACT_NUMBER_DIALOG))
            {
                SessionBean stateBean = appReqBlock.getSessionBean("quoteStateBean");

                String previousPage = stateBean.getValue("currentPage");

                stateBean.putValue("previousPage", previousPage);
                stateBean.putValue("currentPage", returnPage);
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
     * Shows the EnrollmentLeadServiceAgent info for the specified Segment via
     * EnrollmentLeadServiceAgent.Enrollment.BatchContractSetup.Segment.
     */    
    private String showEnrollmentLeadServiceAgentInfo(AppReqBlock appReqBlock) 
    {
//        EnrollmentLeadServiceAgentTableModel enrollmentLeadServiceAgentTableModel = 
        new EnrollmentLeadServiceAgentTableModel(appReqBlock);
        
        appReqBlock.putInRequestScope("main", ENROLLMENT_LEAD_SERVICE_AGENT_DIALOG);
        
        appReqBlock.putInRequestScope("pageTitle", "Lead/Servicing Agent Info");
        
        return TEMPLATE_DIALOG;                                  
    }

    protected String showJumpToDialog(AppReqBlock appReqBlock) throws Exception
    {
        String jumpToTarget = appReqBlock.getReqParm("jumpToTarget");
        appReqBlock.getHttpServletRequest().setAttribute("jumpToTarget", jumpToTarget);

        return JUMP_TO_DIALOG;
    }

    protected String lockQuote(AppReqBlock appReqBlock) throws Exception
    {
    	PageBean quoteMainFormBean = appReqBlock.getSessionBean("quoteMainSessionBean").
                getPageBean("formBean");

        String optionId = quoteMainFormBean.getValue("optionId");

        String segmentPKAsStr = quoteMainFormBean.getValue("segmentPK");
        long segmentPK = (Util.isANumber(segmentPKAsStr)) ? Long.parseLong(segmentPKAsStr) : 0;

        UserSession userSession = appReqBlock.getUserSession();

    	// reload beans to ensure post-submit updates
        //  Get baseSegment
        contract.business.Lookup lookup =  new contract.component.LookupComponent();

        List voInclusionList = setListForSegmentVO();

        SegmentVO baseSegmentVO = lookup.composeSegmentVO(segmentPK, voInclusionList);

        //  Get optionCode
        String optionCode = Util.initString(baseSegmentVO.getOptionCodeCT(), "");

        clearAllQuoteSessions(appReqBlock);

        loadMainPage(appReqBlock, baseSegmentVO, optionCode, segmentPK + "");

        loadNotes(appReqBlock, baseSegmentVO);

        loadRiders(appReqBlock, baseSegmentVO);

        loadClients(appReqBlock, baseSegmentVO);

        loadAgentHierarchy(appReqBlock, baseSegmentVO);

        loadInvestment(appReqBlock, baseSegmentVO);

        loadHistory(appReqBlock, baseSegmentVO);

        loadSuspense(appReqBlock, baseSegmentVO);

        loadTransactions(appReqBlock, baseSegmentVO);

        String productType = checkProductType(optionCode);
        
        appReqBlock.getHttpSession().setAttribute("reloadHeader", "true");
        
        // Check for authorization
        new NewBusinessUseCaseComponent().updateNewBusinessContract();

        try
        {
            if (!userSession.getSegmentIsLocked())
            {
                userSession.lockSegment(segmentPK);
            }
        }
        catch (EDITLockException e)
        {
            appReqBlock.getHttpServletRequest().setAttribute("errorMessage", e.getMessage());
        }

        return getMainReturnPage(productType);
    }

    protected String showCancelQuoteConfirmationDialog(AppReqBlock appReqBlock) throws Exception
    {
        return CANCEL_QUOTE_CONFIRMATION_DIALOG;
    }

    protected String showSelectCoverageDialog(AppReqBlock appReqBlock) throws Exception
    {
        //new NewBusinessUseCaseComponent().addNewBusinessContract();
        // Note: the security check is now checked after they have selected
        // a product structure.  This way the addNew
        // see: addNewBusinessContract security is checked for the right product.

        return SELECT_COVERAGE_DIALOG;
    }

    protected void preProcessRequest(AppReqBlock appReqBlock) throws Exception
    {
    }

    protected void postProcessRequest(AppReqBlock appReqBlock) throws Exception
    {
    }

    protected String saveQuote(AppReqBlock appReqBlock, boolean returnToUser) throws Exception
    {
        String recordPRASEEvents = appReqBlock.getSessionBean("quoteMainSessionBean").getPageBean("formBean").getValue("recordPRASEEvents");
        UtilitiesForTran.setupRecordPRASEEvents(appReqBlock, recordPRASEEvents);

        // Delete agentHierarchy from DB when user choose to delete an agent from a contract
        UIAgentHierarchyVO[] uIAgentHierarchyVOsForDelete = (UIAgentHierarchyVO[])appReqBlock.getHttpSession().getAttribute("uiAgentHierarchyVOsDeleted");
        if(uIAgentHierarchyVOsForDelete != null)
        {
            deleteAgentFromContract(uIAgentHierarchyVOsForDelete);
            appReqBlock.getHttpSession().setAttribute("uiAgentHierarchyVOsDeleted", null);
        }
        
        String operator = appReqBlock.getUserSession().getUsername();

        String message = "";

        String ignoreEditWarnings = appReqBlock.getReqParm("ignoreEditWarnings");
        ignoreEditWarnings = (ignoreEditWarnings == null) ? "" : ignoreEditWarnings;

        QuoteVO quoteVO = buildQuoteVO(appReqBlock);
        
        String contractId = appReqBlock.getReqParm("contractNumber");
        if (contractId == null) {
        	contractId = appReqBlock.getSessionBean("quoteMainSessionBean").getPageBean("formBean").getValue("contractNumber");
        }
        String previousStatus = Segment.findBy_ContractNumber(contractId)[0].getSegmentStatusCT();
        String newStatus = quoteVO.getSegmentVO(0).getSegmentStatusCT();
        
        boolean effectiveDateChange = false;
        EDITDate previousEffDate = Segment.findBy_ContractNumber(contractId)[0].getEffectiveDate();
        EDITDate newEffDate = new EDITDate(quoteVO.getSegmentVO(0).getEffectiveDate());
        
        if (!previousEffDate.equals(newEffDate))
        {
        	effectiveDateChange = true;
        }
        
        // Check for change to frozen status - If so, freeze the policy and exit processing
        if (newStatus != null && newStatus.equalsIgnoreCase(Segment.SEGMENTSTATUSCT_FROZEN))
        {
        	// Contract must be in SubmitPend status to freeze
        	if (previousStatus != null && (!previousStatus.equalsIgnoreCase(Segment.SEGMENTSTATUSCT_FROZEN) && !previousStatus.equalsIgnoreCase(Segment.SEGMENTSTATUSCT_SUBMIT_PEND)))
        	{
        		message = "Can only freeze a contract in SubmitPend status via this method!";
        		appReqBlock.getSessionBean("quoteMainSessionBean").putValue("quoteMessage", message);
        	}
        	else if (previousStatus != null && previousStatus.equalsIgnoreCase(Segment.SEGMENTSTATUSCT_SUBMIT_PEND))
        	{
        		try
        		{
	        		SessionHelper.beginTransaction(SessionHelper.EDITSOLUTIONS);
	        		
	        		SegmentVO segmentVO = quoteVO.getSegmentVO()[0];
	        		
	        		Segment segment = (Segment) SessionHelper.map(segmentVO, SessionHelper.EDITSOLUTIONS);
	                segmentVO.setSegmentPK(segment.getSegmentPK().longValue());
	                segment.setVO(segmentVO);
	        		
	        		segment.setSegmentStatusCT(Segment.SEGMENTSTATUSCT_FROZEN);
	        		// Use hSave to maintain FKs
	        		segment.hSave();

	        		Segment[] riders = segment.getRiders();
	        		if (riders != null)
	                {
	        			for (Segment rider : riders)
	        			{
	        				rider.setSegmentStatusCT(Segment.SEGMENTSTATUSCT_FROZEN);
	        				rider.hSave();
	        			}
	                }
        		
	        		SessionHelper.commitTransaction(SessionHelper.EDITSOLUTIONS);
	        		
	        		SessionBean stateBean = appReqBlock.getSessionBean("quoteStateBean");
	        		String optionId = segment.getOptionCodeCT();
	                String productType = checkProductType(optionId);
	
	                String mainReturnPage = getMainReturnPage(productType);
	                stateBean.putValue("currentPage", mainReturnPage);
	
	                message = "Policy has been frozen";
	        		appReqBlock.getSessionBean("quoteMainSessionBean").putValue("quoteMessage", message);
	        		
	        		UserSession userSession = appReqBlock.getUserSession();
                    userSession.unlockSegment();
                    
	        		return mainReturnPage;
        		
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
        }
        
        if (appReqBlock.getHttpServletRequest().getAttribute("quoteMessage") != null)
        {
            message = (String) appReqBlock.getHttpServletRequest().getAttribute("quoteMessage");
        }

        PremiumDueVO[] premiumDueVOs = (PremiumDueVO[]) appReqBlock.getHttpSession().getAttribute("premiumDueVOs");

        SegmentVO segmentVO = quoteVO.getSegmentVO()[0];
        if (premiumDueVOs != null)
        {
            segmentVO.setPremiumDueVO(premiumDueVOs);
        }

        String creationDate = segmentVO.getCreationDate();

        if (creationDate == null)
        {
            segmentVO.setCreationDate(new EDITDate().getFormattedDate());
            segmentVO.setCreationOperator(operator);
        }

        DepositsVO[] depositsVOs = segmentVO.getDepositsVO();

        if (depositsVOs != null)
        {
            resetNegativeKeysToZero(depositsVOs);
        }

        AgentHierarchyVO[] agentHierarchyVOs = segmentVO.getAgentHierarchyVO();
        //new UtilitiesForTran().resetNegativeKeysToZero(agentHierarchyVOs);

        String agentMessage = "";

        if (agentHierarchyVOs != null && agentHierarchyVOs.length > 0)
        {
            for (int i = 0; i < agentHierarchyVOs.length; i++)
            {
                if (agentHierarchyVOs[i].getAgentSnapshotVOCount() > 0)
                {
                    AgentSnapshotVO[] agentSnapshotVO = agentHierarchyVOs[i].getAgentSnapshotVO();
                    for (int j = 0; j < agentSnapshotVO.length; j++)
                    {
                        EDITBigDecimal advancePercent = new EDITBigDecimal(agentSnapshotVO[j].getAdvancePercent());
                        EDITBigDecimal recoveryPercent = new EDITBigDecimal(agentSnapshotVO[j].getRecoveryPercent());
                        agentMessage = new UtilitiesForTran().editCommissionProfile(new Long(agentSnapshotVO[j].getCommissionProfileFK()), advancePercent, recoveryPercent, agentSnapshotVO[j].getPlacedAgentFK());
                    }
                }
            }
        }

        if (agentMessage != null && !agentMessage.equals(""))
        {
            appReqBlock.getHttpServletRequest().setAttribute("agentMessage", agentMessage);
        }

        String effectiveDate = segmentVO.getEffectiveDate();
        
        if (effectiveDateChange && segmentVO.getSegmentStatusCT().equalsIgnoreCase(Segment.SEGMENTSTATUSCT_SUBMIT_PEND))
        {
        	segmentVO.setDeductionAmountEffectiveDate(effectiveDate);
        }
        
        SegmentVO[] riders = segmentVO.getSegmentVO();

        if (riders != null)
        {
            for (int r = 0; r < riders.length; r++)
            {
                AgentHierarchyVO[] riderAgentHierarchyVOs = riders[r].getAgentHierarchyVO();
                new UtilitiesForTran().resetNegativeKeysToZero(riderAgentHierarchyVOs);
                
                // Match rider dates to traditional segment and reset termination date for recalculation if there is an effective date change
                if (effectiveDateChange && segmentVO.getSegmentStatusCT().equalsIgnoreCase(Segment.SEGMENTSTATUSCT_SUBMIT_PEND))
                {
                	riders[r].setEffectiveDate(effectiveDate);
                	riders[r].setTerminationDate(EDITDate.DEFAULT_MAX_DATE);
                }
            }
        }

        long segmentPK = segmentVO.getSegmentPK();
        String optionId = segmentVO.getOptionCodeCT();
        String processName = "NewBusSave";

        if (agentMessage == null || agentMessage.equals(""))
        {
            if (!ignoreEditWarnings.equalsIgnoreCase("true"))
            {
                try
                {
                    // With the possibility of errors being displayed, contract number needs to be saved after entered
                    // String contractId = appReqBlock.getFormBean().getValue("contractNumber");
                    contractId = segmentVO.getContractNumber();

                    contract.business.Lookup contractLookup = new contract.component.LookupComponent();

                    SegmentVO[] existingSegmentVOs = contractLookup.getSegmentByContractNumber(contractId, false, new ArrayList());

                    if (existingSegmentVOs != null && existingSegmentVOs[0].getSegmentPK() != segmentPK)
                    {
                        message = "Contract Number Already Exists";

                        appReqBlock.getSessionBean("quoteMainSessionBean").putValue("quoteMessage", message);
                    }

                    boolean clientsAttached = checkClientsAttachedToPolicy(appReqBlock);

                    if (! clientsAttached)
                    {
                        message = "Clients Must Be Entered";

                        appReqBlock.getSessionBean("quoteMainSessionBean").putValue("quoteMessage", "Clients Must Be Entered");
                    }
                    else
                    {
                        appReqBlock.getSessionBean("quoteStateBean").putValue("contractNumber", contractId);

                        appReqBlock.getFormBean().putValue("contractId", contractId);

                        try
                        {
                            validateQuote(quoteVO, processName);

                        } catch (PortalEditingException e)
                        {
                            System.out.println(e);

                            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.

                            throw e;
                        }
                    }
                }
                catch (PortalEditingException e)
                {
                    throw e;
                }
                catch (Exception e)
                {
                    System.out.println(e);

                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.

                    message = e.getMessage();

                    appReqBlock.getHttpServletRequest().setAttribute("errorMessage", e.getMessage());
                }
            }
        }

        if ((!message.equals("")) || (agentMessage != null && !agentMessage.equals("")))
        {
            SessionBean stateBean = appReqBlock.getSessionBean("quoteStateBean");

            String productType = checkProductType(optionId);

            String mainReturnPage = getMainReturnPage(productType);

            stateBean.putValue("currentPage", mainReturnPage);

            return mainReturnPage;
        }
        else
        {
            // Save clients
            client.business.Client client =  new client.component.ClientComponent();

            ClientDetailVO[] clientDetailVOs = quoteVO.getClientDetailVO();

            for (int i = 0; i < clientDetailVOs.length; i++)
            {
                long clientPK = clientDetailVOs[i].getClientDetailPK();

                if (clientPK == 0)
                {

                    client.saveOrUpdateClient(clientDetailVOs[i], true);
                }
            }

             
            // Save contract
            contract.business.Contract contract = new contract.component.ContractComponent();

            BillScheduleVO billScheduleVO = quoteVO.getBillScheduleVO();

            if (billScheduleVO != null)
            {
                if (billScheduleVO.getBillSchedulePK() == 0)
                {
                    Long billSchedulePK = contract.saveBillSchedule(quoteVO.getBillScheduleVO());  // save before the segment so pk will exist
                    segmentVO.setBillScheduleFK(billSchedulePK.longValue());
                }
            }

//            segmentPK = contract.saveSegmentForNewBusiness(quoteVO.getSegmentVO()[0], false, null);
            Segment segment = (Segment) SessionHelper.map(segmentVO, SessionHelper.EDITSOLUTIONS);
            segmentVO.setSegmentPK(segment.getSegmentPK().longValue());
            segment.setOperator(operator);
            segment.setVO(segmentVO);
            
            boolean nonFinancialEditsExist = false;
            
            try 
            {
                segmentPK = contract.saveSegmentForNBWithHibernate(segment, "");
                
                buildAndSaveTransactions(appReqBlock.getSessionBean("quoteTransactions"));

                appReqBlock.getSessionBean("quoteStateBean").
                        putValue("contractNumber", quoteVO.getSegmentVO()[0].getContractNumber());
                appReqBlock.getFormBean().putValue("contractId", quoteVO.getSegmentVO()[0].getContractNumber());
                appReqBlock.getFormBean().putValue("segmentPK", quoteVO.getSegmentVO()[0].getSegmentPK() + "");                
            }
            catch (EDITNonFinancialException e) 
            {
                nonFinancialEditsExist = true;                
            }

            try
            {
                if (returnToUser)
                {
                    // Only unlock if there weren't any errors from the NF Framework.
                    if (!nonFinancialEditsExist)
                    {
                        UserSession userSession = appReqBlock.getUserSession();
                        userSession.setDepositsVO(null);
                        userSession.unlockSegment();
                    }
                    
                    if (message == "")
                    {
                    	message = "Transaction Complete";
                    	appReqBlock.getHttpServletRequest().setAttribute("errorMessage", message);
                    }
                    
                    appReqBlock.getFormBean().putValue("segmentPK", String.valueOf(segmentPK));
                    
                    return loadQuote(appReqBlock);
                }
                else
                {
                    return "";
                }
            }
            finally
            {
                new UtilitiesForTran().resetNegativeKeysToZero(agentHierarchyVOs);
            }
        }
    }

/*
 *when user choose to delete an agent form a contract,
 * this removes the parent-child relation and deletes the specific children
 * for detail refer AgentHierarchy.hDelete() 
*/
    private void deleteAgentFromContract(UIAgentHierarchyVO[] uIAgentHierarchyVOs)
    {
        contract.component.ContractComponent contractComponent = new contract.component.ContractComponent();
        try
        {
            for(int i =0;i<uIAgentHierarchyVOs.length;i++)
            {

                AgentHierarchyVO agentHierarchyVO = uIAgentHierarchyVOs[i].getAgentHierarchyVO();
                AgentHierarchy agentHierarchy = new AgentHierarchy(agentHierarchyVO);
                //this removes the parent-child relation and deletes the specific children..for detail refer agentHierarchy.hDelete() 
                contractComponent.deleteContractAgentHierarchy(agentHierarchy);
            }
               
        }
        catch (Exception e)
        {
            System.out.println(e);
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    private void buildAndSaveTransactions(SessionBean quoteTransactions)
    {
        Map transactionFormBeans = quoteTransactions.getPageBeans();
        Set beanKeys = transactionFormBeans.keySet();
        Iterator it = beanKeys.iterator();

        while (it.hasNext())
        {
//            String key = (String) it.next();

//            PageBean trxFormBean = (PageBean) transactionFormBeans.get(key);

//            GroupSetup groupSetup = new GroupSetup();
        }
    }

    protected String commitContract(AppReqBlock appReqBlock) throws Exception
    {
        PageBean quoteMainFormBean = appReqBlock.getSessionBean("quoteMainSessionBean").getPageBean("formBean");
        PageBean formBean = appReqBlock.getFormBean();

        SessionBean stateBean = appReqBlock.getSessionBean("quoteStateBean");
        String currentPage = stateBean.getValue("currentPage");

        UtilitiesForTran.setupRecordPRASEEvents(appReqBlock, quoteMainFormBean.getValue("recordPRASEEvents"));

        savePreviousPageFormBean(appReqBlock, currentPage);

        String segmentPK = quoteMainFormBean.getValue("segmentPK");
//        String optionCode = quoteMainFormBean.getValue("optionId");
        String lastDayOfMonthInd = quoteMainFormBean.getValue("lastDayOfMonthIndStatus");

        if (lastDayOfMonthInd.equalsIgnoreCase("checked"))
        {
            lastDayOfMonthInd = "Y";
        }
        else
        {
            lastDayOfMonthInd = "N";
        }

        String operator = appReqBlock.getUserSession().getUsername();

        String returnPage = performPageEditing(appReqBlock);

        String message = "";

        String contractId = formBean.getValue("contractNumber");
        String issueDate = DateTimeUtil.formatMMDDYYYYToYYYYMMDD(formBean.getValue("issueDate"));
        String status = formBean.getValue("terminationStatus");
        String suppressPolicyPages = formBean.getValue("suppressPolicyPagesStatus");

        String ignoreEditWarnings = appReqBlock.getReqParm("ignoreEditWarnings");
        ignoreEditWarnings = (ignoreEditWarnings == null) ? "" : ignoreEditWarnings;

        // with editting errors lossing status and issueDate they are now stored with the main page
        if (ignoreEditWarnings.equalsIgnoreCase("true"))
        {
            issueDate = DateTimeUtil.formatMMDDYYYYToYYYYMMDD(quoteMainFormBean.getValue("issueDate"));
            status = quoteMainFormBean.getValue("terminatedStatus");
            contractId = quoteMainFormBean.getValue("contractNumber");
        }
        else
        {
            quoteMainFormBean.putValue("issueDate", DateTimeUtil.formatYYYYMMDDToMMDDYYYY(issueDate));
            quoteMainFormBean.putValue("terminatedStatus", status);
            quoteMainFormBean.putValue("contractNumber", contractId);
            appReqBlock.getSessionBean("quoteMainSessionBean").putPageBean("formBean", quoteMainFormBean);
        }

        if (!status.equalsIgnoreCase("Please Select") && returnPage.equals(""))
        {
            QuoteVO quoteVO = buildQuoteVO(appReqBlock);

            PremiumDueVO[] premiumDueVOs = (PremiumDueVO[]) appReqBlock.getHttpSession().getAttribute("premiumDueVOs");

            SegmentVO segmentVO = quoteVO.getSegmentVO()[0];
            if (premiumDueVOs != null)
            {
                segmentVO.setPremiumDueVO(premiumDueVOs);
            }
            
            segmentVO.setStatusChangeDate(issueDate);

            DepositsVO[] depositsVOs = segmentVO.getDepositsVO();

            resetNegativeKeysToZero(depositsVOs);

            if (depositsVOs != null)
            {
                for (int i = 0; i < depositsVOs.length; i++)
                {
                    if (depositsVOs[i].getSuspenseFK() > 0)
                    {
                        event.business.Event eventComponent = new event.component.EventComponent();
                        SuspenseVO suspenseVO = eventComponent.composeSuspenseVO(depositsVOs[i].getSuspenseFK(), new ArrayList());
                        if (suspenseVO != null)
                        {
                            EDITBigDecimal pendingAmt = Util.roundToNearestCent(suspenseVO.getPendingSuspenseAmount());
                            pendingAmt =  pendingAmt.subtractEditBigDecimal(Util.roundToNearestCent(depositsVOs[i].getAmountReceived()));
                            suspenseVO.setPendingSuspenseAmount(Util.roundToNearestCent(pendingAmt).getBigDecimal());
                            eventComponent.saveSuspenseNonRecursively(suspenseVO);
                            depositsVOs[i].setSuspenseFK(0);
                            depositsVOs[i].setCashBatchContractFK(0);
                            depositsVOs[i].setAmountReceived(new EDITBigDecimal().getBigDecimal());
                        }
                    }
                }
            }

            String creationDate = segmentVO.getCreationDate();

            if (creationDate == null)
            {
                segmentVO.setCreationDate(new EDITDate().getFormattedDate());
                segmentVO.setCreationOperator(operator);
            }

            CodeTableVO codeTableVO = CodeTableWrapper.getSingleton().getCodeTableEntry(Long.parseLong(status));
            AgentHierarchyVO[] agentHierarchyVOs = segmentVO.getAgentHierarchyVO();
            new UtilitiesForTran().resetNegativeKeysToZero(agentHierarchyVOs);
            SegmentVO[] riders = segmentVO.getSegmentVO();

            if (riders != null)
            {
                for (int r = 0; r < riders.length; r++)
                {
                    riders[r].setSegmentStatusCT(codeTableVO.getCode());
                    AgentHierarchyVO[] riderAgentHierarchyVOs = riders[r].getAgentHierarchyVO();
                    new UtilitiesForTran().resetNegativeKeysToZero(riderAgentHierarchyVOs);
                }
            }

            segmentVO.setSegmentStatusCT(codeTableVO.getCode());
            if (!segmentVO.getSegmentStatusCT().equalsIgnoreCase(Segment.SEGMENTSTATUSCT_REOPEN))
            {
                segmentVO.setWorksheetTypeCT(Segment.WORKSHEETTYPECT_FINAL);
            }
            else
            {
                segmentVO.setWorksheetTypeCT(Segment.WORKSHEETTYPECT_CORRECTION);
            }

            contract.business.Contract contractComponent = new contract.component.ContractComponent();

            Segment segment = (Segment) SessionHelper.map(segmentVO, SessionHelper.EDITSOLUTIONS);
            segment.setOperator(operator);
            segment.setVO(segmentVO);
            segmentPK = contractComponent.saveSegmentForNBWithHibernate(segment, "") + "";

            //Terminated Contracts must have suspense pending refunded if it exists
            deletePremiumTrx(new Long(segmentPK));

            UserSession userSession = appReqBlock.getUserSession();

            userSession.unlockSegment();

//            if (segmentVO.getSegmentStatusCT().equalsIgnoreCase(Segment.SEGMENTSTATUSCT_REOPEN))
//            {
//                /*
//                Since this contract is now being "reopened", we need to reverse the BC that was generated
//                when the contract was terminated
//                */
//
//                EDITTrx[] editTrxs = EDITTrx.findBy_SegmentPK_PendingStatus_TransactionType(segment.getSegmentPK(), EDITTrx.PENDINGSTATUS_HISTORY);
//                for (int i = 0; i < editTrxs.length; i++)
//                {
//                    if (editTrxs[i].getTransactionTypeCT().equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_BILLING_CHANGE))
//                    {
//                        ClientSetup clientSetup = editTrxs[i].getClientSetup();
//                        ContractSetup contractSetup = clientSetup.getContractSetup();
//                        if (complexChangeTypeIsTerminationType(contractSetup.getComplexChangeTypeCT()))
//                        {
//                            event.business.Event eventComponent = new event.component.EventComponent();
//
//                            String reversalReasonCode = "Reopen";
//
//                            try
//                            {
//                                eventComponent.reverseClientTrx(editTrxs[i].getEDITTrxPK().longValue(), operator, reversalReasonCode);
//                            }
//                            catch (EDITEventException e)
//                            {
//                                e.printStackTrace();
//                            }
//                        }
//                    }
//                }
//
//                segment = Segment.findByPK(new Long(segmentPK));
//                segment.setSegmentStatusCT(codeTableVO.getCode());
//                contractComponent.saveSegmentForNBWithHibernate(segment);
//            }

            clearAllQuoteSessions(appReqBlock);

            appReqBlock.getFormBean().putValue("segmentPK", segmentPK);

            loadQuote(appReqBlock);
        }
        else
        {
            if (!returnPage.equals(""))
            {
                return returnPage;
            }
            else
            {
                QuoteVO quoteVO = buildQuoteVO(appReqBlock);

                PremiumDueVO[] premiumDueVOs = (PremiumDueVO[]) appReqBlock.getHttpSession().getAttribute("premiumDueVOs");

                SegmentVO segmentVO = quoteVO.getSegmentVO()[0];
                if (premiumDueVOs != null)
                {
                    segmentVO.setPremiumDueVO(premiumDueVOs);
                }

                long segmentId = segmentVO.getSegmentPK();
                String creationDate = segmentVO.getCreationDate();

                if (creationDate == null)
                {
                    segmentVO.setCreationDate(new EDITDate().getFormattedDate());
                    segmentVO.setCreationOperator(operator);
                }

                AgentHierarchyVO[] agentHierarchyVOs = segmentVO.getAgentHierarchyVO();
                SegmentVO[] riders = segmentVO.getSegmentVO();

                if (riders != null)
                {
                    for (int r = 0; r < riders.length; r++)
                    {
                        AgentHierarchyVO[] riderAgentHierarchyVOs = riders[r].getAgentHierarchyVO();
                        new UtilitiesForTran().resetNegativeKeysToZero(riderAgentHierarchyVOs);
                    }
                }

                String agentMessage = "";

                if (agentHierarchyVOs != null && agentHierarchyVOs.length > 0)
                {
                    //This edit is now done in scripts

                        EDITBigDecimal agentSplitPercent = AgentHierarchy.getTotalAgentSplitPercent(agentHierarchyVOs);

                        if (!Util.roundAllocation(agentSplitPercent).isEQ("1"))
                        {
                            agentMessage = "Agent Split Percent Must Equal 100%(1.0)";
                        }

                    for (int i = 0; i < agentHierarchyVOs.length; i++)
                    {
                        if (agentHierarchyVOs[i].getAgentSnapshotVOCount() > 0)
                        {
                            AgentSnapshotVO[] agentSnapshotVO = agentHierarchyVOs[i].getAgentSnapshotVO();
                            for (int j = 0; j < agentSnapshotVO.length; j++)
                            {
                                EDITBigDecimal advancePercent = new EDITBigDecimal(agentSnapshotVO[j].getAdvancePercent());
                                EDITBigDecimal recoveryPercent = new EDITBigDecimal(agentSnapshotVO[j].getRecoveryPercent());
                                agentMessage = new UtilitiesForTran().editCommissionProfile(new Long(agentSnapshotVO[j].getCommissionProfileFK()), advancePercent, recoveryPercent, agentSnapshotVO[j].getPlacedAgentFK());
                            }
                        }
                    }
                }

                if (agentMessage != null && !agentMessage.equals(""))
                {
                    appReqBlock.getHttpServletRequest().setAttribute("agentMessage", agentMessage);
                }
                else
                {
                    new UtilitiesForTran().resetNegativeKeysToZero(agentHierarchyVOs);

                    contract.business.Lookup lookup = new contract.component.LookupComponent();
                    event.business.Event eventComponent = new event.component.EventComponent();

                    if (contractId.equals(""))
                    {
                        message = "Contract Id must be entered.";
                        appReqBlock.getHttpServletRequest().setAttribute("contractNumberMessage", message);
                    }
                    else
                    {
                        SegmentVO[] segmentVOs = lookup.getSegmentByContractNumber(contractId, false, new ArrayList());

                        if (segmentVOs != null && segmentId == 0)
                        {
                            message = "Contract number already exists.";
                            appReqBlock.getHttpServletRequest().setAttribute("contractNumberMessage", message);
                        }
                        else
                        {
                            SegmentVO baseSegmentVO = quoteVO.getSegmentVO()[0];
                            baseSegmentVO.setIssueDate(issueDate);
                            String segmentName = baseSegmentVO.getSegmentNameCT();

                            String cashWithAppInd = baseSegmentVO.getCashWithAppInd();
                            DepositsVO[] depositsVO = baseSegmentVO.getDepositsVO();
                            resetNegativeKeysToZero(depositsVO);

                            ContractClientVO[] contractClientVOs = baseSegmentVO.getContractClientVO();
                            SegmentVO[] riderSegments = baseSegmentVO.getSegmentVO();

                            long productStructurePK = baseSegmentVO.getProductStructureFK();
                            engine.business.Lookup engineLookup = new engine.component.LookupComponent();
                            ProductStructureVO[] productStructureVO = engineLookup.findProductStructureVOByPK(productStructurePK, false, null);

                            boolean effectiveDateMissing = false;
                            String newEffectiveDate = baseSegmentVO.getEffectiveDate();
                            String incomeMaturityDate = null;

                            if (cashWithAppInd.equalsIgnoreCase("N"))
                            {
                                if (!segmentName.equalsIgnoreCase("Life"))
                                {
                                    DateCalcsForPremium dateCalcsForPremium = new DateCalcsForPremium();
                                    String editMessage = dateCalcsForPremium.calcEffectiveDateForIssue(baseSegmentVO, productStructureVO[0]);
                                    if (editMessage != null)
                                    {
                                        appReqBlock.getSessionBean("quoteMainSessionBean").putValue("quoteMessage", editMessage);
                                    }
                                    else
                                    {
                                        incomeMaturityDate = dateCalcsForPremium.getIncomeMaturityDate();
                                        newEffectiveDate   = dateCalcsForPremium.getDateCalculated();
                                        if (newEffectiveDate == null)
                                        {
                                            newEffectiveDate = baseSegmentVO.getEffectiveDate();
                                        }

                                        baseSegmentVO.setEffectiveDate(newEffectiveDate);
                                        baseSegmentVO.setLastAnniversaryDate(newEffectiveDate);                                        }
                                }
                                else
                                {
                                    newEffectiveDate = baseSegmentVO.getEffectiveDate();
                                }

                                if (baseSegmentVO.getEffectiveDate() == null)
                                {
                                    message = "Cannot Issue.  Effective Date Must Be Entered.";
                                    appReqBlock.getSessionBean("quoteMainSessionBean").putValue("quoteMessage", message);
                                    effectiveDateMissing = true;
                                }
                            }
                            else
                            {
                                if (baseSegmentVO.getEffectiveDate() == null)
                                {
                                    message = "Cannot Issue.  Effective Date Must Be Entered.";
                                    appReqBlock.getSessionBean("quoteMainSessionBean").putValue("quoteMessage", message);
                                    effectiveDateMissing = true;
                                }
                            }

                            if (!effectiveDateMissing)
                            {
                                try
                                {
                                    calculateIssueAge(contractClientVOs, newEffectiveDate, segmentName);

                                    if (riderSegments != null)
                                    {
                                        for (int s = 0; s < riderSegments.length; s++)
                                        {
                                            ContractClientVO[] riderContractClientVOs = riderSegments[s].getContractClientVO();
                                            riderSegments[s].setIssueDate(issueDate);

                                            EDITDate riderEffectiveDate = null;
                                            EDITDate effectiveDate = new EDITDate(newEffectiveDate);
                                            String riderEffectiveDateStr = riderSegments[s].getEffectiveDate();
                                            if (riderEffectiveDateStr != null && !riderEffectiveDateStr.equals("")) {
                                            	riderEffectiveDate = new EDITDate(riderEffectiveDateStr);
                                            }
                                            
                                            if (riderEffectiveDate == null || riderEffectiveDate.before(effectiveDate)) {
                                            	calculateIssueAge(riderContractClientVOs, newEffectiveDate, segmentVO.getSegmentNameCT());
                                                riderSegments[s].setEffectiveDate(newEffectiveDate);
                                            } else {
                                            	calculateIssueAge(riderContractClientVOs, riderEffectiveDateStr, segmentVO.getSegmentNameCT());
                                            }
                                            
                                        }
                                    }

                                    //  Now that we have a valid effectiveDate, set all the agentHierarchyAllocations' startDates to the effectiveDate
                                    setAgentHierarchyAllocationStartDates(baseSegmentVO);

                                    String processName = "NewBusIssue";

                                    if (!ignoreEditWarnings.equalsIgnoreCase("true"))
                                    {
                                        try
                                        {
                                            baseSegmentVO = validateQuote(quoteVO, processName);
                                            contract.business.Contract contractComponent = new contract.component.ContractComponent();
                                            contractComponent.saveSegmentNonRecursively(baseSegmentVO, false, operator);
                                        }
                                        catch (PortalEditingException e)
                                        {
                                            System.out.println(e);

                                            e.printStackTrace();

                                            throw e;
                                        }
                                    }

                                    boolean nonFinancialEditsExist = false;

                                    try 
                                    {
                                        eventComponent.commitContract(baseSegmentVO, operator, lastDayOfMonthInd, incomeMaturityDate, suppressPolicyPages);
                                    }
                                    catch (EDITNonFinancialException e) 
                                    {
                                        nonFinancialEditsExist = true;
                                    }
                                    
                                    baseSegmentVO = (SegmentVO)Segment.findByPK(baseSegmentVO.getSegmentPK()).getVO();

//                                    if (baseSegmentVO.getSegmentStatusCT().equalsIgnoreCase("IssuePendingPremium") ||
//                                        baseSegmentVO.getSegmentStatusCT().equalsIgnoreCase("IssuedPendingReq"))
                                    if (baseSegmentVO.getPostIssueStatusCT() !=  null && !nonFinancialEditsExist)
                                    {
                                        clearAllQuoteSessions(appReqBlock);
                                        message = "Contract Issued with No Errors";
                                        appReqBlock.getHttpServletRequest().setAttribute("errorMessage", message);
                                    }
                                    //current dated Issue trx will not change the status following error no longer required as of 02/25/08
                                    else if (!nonFinancialEditsExist)
                                    {
                                        segmentPK = baseSegmentVO.getSegmentPK() + "";
                                        String newStatus = baseSegmentVO.getSegmentStatusCT();
                                        quoteMainFormBean.putValue("segmentPK", segmentPK);
                                        appReqBlock.getSessionBean("quoteMainSessionBean").putPageBean("formBean", quoteMainFormBean);
                                        loadQuote(appReqBlock);
                                        message = newStatus + " Req Outstanding";
                                        appReqBlock.getHttpServletRequest().setAttribute("errorMessage", message);
                                    }
                                    else if (nonFinancialEditsExist) 
                                    {
                                        segmentPK = baseSegmentVO.getSegmentPK() + "";
                                        quoteMainFormBean.putValue("segmentPK", segmentPK);
                                        appReqBlock.getSessionBean("quoteMainSessionBean").putPageBean("formBean", quoteMainFormBean);
                                        loadQuote(appReqBlock);
                                    }
                                }
                                catch (PortalEditingException e)
                                {
                                    throw e;
                                }
                                catch (Exception e)
                                {
                                    System.out.println(e);

                                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.

                                    message = e.getMessage();

                                    appReqBlock.getHttpServletRequest().setAttribute("errorMessage", e.getMessage());
                                }
                                finally
                                {
                                    if (message != null &&
                                        (message.equalsIgnoreCase("Contract Issued with No Errors") ||
                                         message.equalsIgnoreCase("dummySetting") ||
                                         message.equalsIgnoreCase("Contract Issued - Transaction Errored")))
                                    {
                                        UserSession userSession = appReqBlock.getUserSession();

                                        userSession.unlockSegment();
                                    }
                                }
                            }
                            else if (effectiveDateMissing)
                            {
                                message = "Cannot Issue.  Effective Date Must Be Entered.";
                                appReqBlock.getSessionBean("quoteMainSessionBean").putValue("quoteMessage", message);
                            }
//                            else if (!moneyReceived)
//                            {
//                                message = "Deposit Money Not Yet Received.  Cannot Issue.";
//                                appReqBlock.getSessionBean("quoteMainSessionBean").putValue("quoteMessage", message);
//                            }
                        }
                    }
                }
            }
        }
        
        appReqBlock.getFormBean().putValue("segmentPK", segmentPK);

        return loadQuote(appReqBlock);
    }

    private void deletePremiumTrx(Long segmentPK)  throws Exception
    {
        EDITTrx[] editTrxs = EDITTrx.findBy_TransactionType_SegmentPK_PendingStatus("PY", segmentPK, "P");

        if (editTrxs != null)
        {
            event.business.Event eventComponent = new event.component.EventComponent();
            for (int i = 0; i < editTrxs.length; i++)
            {
                EDITTrx editTrx = editTrxs[i];
                eventComponent.deleteClientTrx(editTrx.getEDITTrxPK(), editTrx.getOperator());
            }
        }
    }

    /**
     * This method will call the reportingadmin component to retrieve the appropriate values and the appropriate jsp
     * to be displayed (jsp page is based on product structure) for the issue verification report
     * @param appReqBlock
     * @return
     * @throws Exception
     */
    private String generateIssueReport(AppReqBlock appReqBlock) throws Exception
    {
        String returnPage = "";
        String message = appReqBlock.getReqParm("message");

        savePreviousPageFormBean(appReqBlock, ISSUE_CONTRACT_DIALOG);

        boolean returnToUser = false;

        PageBean quoteMainFormBean = appReqBlock.getSessionBean("quoteMainSessionBean").getPageBean("formBean");

        String contractNumber = (String) appReqBlock.getFormBean().getValue("contractNumber");
        String issueDate = DateTimeUtil.formatMMDDYYYYToYYYYMMDD(appReqBlock.getFormBean().getValue("issueDate"));
        String autoAssignNumber = (String) appReqBlock.getFormBean().getValue("checkBoxStatus");
        String suppressPolicyPages = (String) appReqBlock.getFormBean().getValue("suppressPolicyPagesStatus");

        quoteMainFormBean.putValue("contractNumber", contractNumber);
        quoteMainFormBean.putValue("checkBoxStatus", autoAssignNumber);
        quoteMainFormBean.putValue("suppressPolicyPages", suppressPolicyPages);
        quoteMainFormBean.putValue("issueDate", DateTimeUtil.formatYYYYMMDDToMMDDYYYY(issueDate));

        appReqBlock.getSessionBean("quoteMainSessionBean").putPageBean("formBean", quoteMainFormBean);

        if (message.equals("Generate"))
        {
            returnPage = saveQuote(appReqBlock, returnToUser);

            String segmentKey = appReqBlock.getFormBean().getValue("segmentPK");
            long segmentPK = 0;
            if (Util.isANumber(segmentKey))
            {
                segmentPK = Long.parseLong((String) appReqBlock.getFormBean().getValue("segmentPK"));
            }

            if (segmentPK != 0)
            {
                quoteMainFormBean.putValue("segmentPK", segmentPK + "");
            }

            reportingadmin.business.ReportingAdmin reportingAdmin = new reportingadmin.component.ReportingAdminComponent();
            IssueReportVO issueReportVO = reportingAdmin.generateIssueReport(segmentPK, issueDate);

            appReqBlock.getHttpServletRequest().setAttribute("issueDocumentVO", issueReportVO.getIssueDocumentVO());
            appReqBlock.getSessionBean("quoteMainSessionBean").putPageBean("formBean", quoteMainFormBean);
            returnPage = issueReportVO.getFileName();

            return returnPage;
        }
        else
        {
            try
            {
//                returnPage = saveQuote(appReqBlock, returnToUser);
                appReqBlock.getHttpServletRequest().setAttribute("message", "Generate");
            }
            catch (Exception e)
            {
                appReqBlock.getHttpServletRequest().setAttribute("message", "Errored");
            }
            finally
            {
                getSuspenseInformation(appReqBlock);

                return ISSUE_CONTRACT_DIALOG;
            }
        }
    }

//    private TreeMap sortDepositsByReceivedDate(DepositsVO[] depositsVOs)
//    {
//        TreeMap sortedDeposits = new TreeMap();
//
//        if (depositsVOs != null)
//        {
//            for (int e = 0; e < depositsVOs.length; e++)
//            {
//                sortedDeposits.put(depositsVOs[e].getDateReceived() + depositsVOs[e].getOldPolicyNumber(), depositsVOs[e]);
//            }
//        }
//
//        return sortedDeposits;
//    }

//    private String checkForWritingAgent(AgentHierarchyVO[] agentHierarchyVOs, SegmentVO segmentVO) throws Exception
//    {
//        String productType = segmentVO.getSegmentNameCT();
//
//        String enrollmentMethod = null;
//
//        long batchContractSetupFK = segmentVO.getBatchContractSetupFK();
//        if (batchContractSetupFK > 0)
//        {
//            BatchContractSetup batchContractSetup = BatchContractSetup.findByPK(new Long(batchContractSetupFK));
//            if (batchContractSetup != null)
//            {
//                enrollmentMethod = batchContractSetup.getEnrollmentMethodCT();
//            }
//        }
//
//        String message = "";
//
//        try
//        {
//            for (int h = 0; h < agentHierarchyVOs.length; h++)
//            {
//                long agentFK = agentHierarchyVOs[h].getAgentFK();
//                Agent agent = new Agent(agentFK);
//                agent.validateAgentLicense(segmentVO.getApplicationSignedDate(), segmentVO.getIssueStateCT(), segmentVO.getProductStructureFK(), enrollmentMethod, productType);
//            }
//        }
//        catch(EDITValidationException e)
//        {
//            message = e.getMessage();
//        }
//
//        return message;
//    }

    private void resetNegativeKeysToZero(DepositsVO[] depositsVOs) throws Exception
    {
        for (int e = 0; e < depositsVOs.length; e++)
        {
            if (depositsVOs[e].getDepositsPK() < 0)
            {
                depositsVOs[e].setDepositsPK(0);
            }
        }
    }

    protected String loadQuoteAfterSearch(AppReqBlock appReqBlock) throws Exception
    {
        clearAllQuoteSessions(appReqBlock);

        appReqBlock.getHttpSession().setAttribute("searchValue", appReqBlock.getReqParm("searchValue"));

        contract.business.Lookup lookup =  new contract.component.LookupComponent();

        //The value selected off the search page may have been a rider.  If so get the base.
        SegmentVO baseSegmentVO = null;
        Segment segment = Segment.findByPK(new Long(appReqBlock.getReqParm("searchValue")));
        if (segment.getSegmentFK() != null)
        {
            baseSegmentVO = lookup.composeSegmentVO(segment.getSegmentFK().longValue(), new ArrayList());

        }
        else
        {
            baseSegmentVO = lookup.composeSegmentVO(Long.parseLong(appReqBlock.getReqParm("searchValue")), new ArrayList());

        }

        String optionCode = baseSegmentVO.getOptionCodeCT();
        String status = baseSegmentVO.getSegmentStatusCT();
        appReqBlock.getHttpSession().setAttribute("optionCode", optionCode);
        appReqBlock.getHttpSession().setAttribute("status", status);

        UserSession userSession = appReqBlock.getUserSession();

        userSession.setSegmentPK(Long.parseLong(appReqBlock.getReqParm("searchValue")));
        if (segment.getSegmentFK() != null)
        {
            userSession.setSegmentPK(segment.getSegmentFK().longValue());
        }

        userSession.addSearchHistory(baseSegmentVO.getContractNumber());
        
        appReqBlock.getHttpSession().setAttribute("reloadHeader", "true");
        
        return MAIN_FRAMESET;
    }

    /**
     * Loads information from persistence to the QuoteVO and beans for display on the screen
     *
     * @param appReqBlock
     *
     * @return Next page to be displayed
     *
     * @throws Exception
     */
    protected String loadQuote(AppReqBlock appReqBlock) throws Exception
    {
        UserSession userSession = appReqBlock.getUserSession();
        //  Get segmentPK
        long segmentPK  = userSession.getSegmentPK();
        if (segmentPK == 0 )
        {
            segmentPK = Long.parseLong(Util.initString(appReqBlock.getFormBean().getValue("segmentPK"), "0"));
        }

        if (segmentPK == 0)
        {
            PageBean quoteMainFormBean = appReqBlock.getSessionBean("quoteMainSessionBean").getPageBean("formBean");
            segmentPK = Long.parseLong(Util.initString(quoteMainFormBean.getValue("segmentPK"), "0"));
        }

        //  Get baseSegment
        contract.business.Lookup lookup =  new contract.component.LookupComponent();

        List voInclusionList = setListForSegmentVO();

        SegmentVO baseSegmentVO = lookup.composeSegmentVO(segmentPK, voInclusionList);

        //  Get optionCode
        String optionCode = Util.initString(baseSegmentVO.getOptionCodeCT(), "");

        clearAllQuoteSessions(appReqBlock);

        loadMainPage(appReqBlock, baseSegmentVO, optionCode, segmentPK + "");

        loadNotes(appReqBlock, baseSegmentVO);

        loadRiders(appReqBlock, baseSegmentVO);

        loadClients(appReqBlock, baseSegmentVO);

        loadAgentHierarchy(appReqBlock, baseSegmentVO);

        loadInvestment(appReqBlock, baseSegmentVO);

        loadHistory(appReqBlock, baseSegmentVO);

        loadSuspense(appReqBlock, baseSegmentVO);

        loadTransactions(appReqBlock, baseSegmentVO);

        String productType = checkProductType(optionCode);
        
        appReqBlock.getHttpSession().setAttribute("reloadHeader", "true");

        return getMainReturnPage(productType);
    }

    /**
     * Loads the main page information to the bean for display on the screen
     *
     * @param appReqBlock
     * @param segmentVO
     * @param optionCode
     * @param segmentPK
     *
     * @throws Exception
     */
    private void loadMainPage(AppReqBlock appReqBlock, SegmentVO segmentVO, String optionCode, String segmentPK) throws Exception
    {
        String ratedGenderCT =null;
        String underwritingClass =null;
        String groupPlan =null;
        String quoteMessage = appReqBlock.getSessionBean("quoteMainSessionBean").getValue("quoteMessage");

        PayoutVO payoutVO = null;
        if (segmentVO.getPayoutVOCount() > 0)
        {
            payoutVO = segmentVO.getPayoutVO(0);
        }

        LifeVO[] lifeVOs = segmentVO.getLifeVO();

        PremiumDueVO[] premiumDueVOs = segmentVO.getPremiumDueVO();

        SessionBean quoteMainSessionBean = appReqBlock.getSessionBean("quoteMainSessionBean");

        if (quoteMessage != null)
        {
            quoteMainSessionBean.putValue("quoteMessage", quoteMessage);
        }
        
        String paidToDate = null;
        if ((lifeVOs != null) && (lifeVOs.length > 0)) {
            paidToDate = DateTimeUtil.formatYYYYMMDDToMMDDYYYY(lifeVOs[0].getPaidToDate());
        }
        quoteMainSessionBean.putValue("paidToDate", paidToDate);

        PageBean formBean = quoteMainSessionBean.getPageBean("formBean");

        SessionBean quoteTaxesSessionBean = appReqBlock.getSessionBean("quoteTaxesSessionBean");

        String brandingCompany = null;
        MasterContract masterContract = MasterContract.findByPK(segmentVO.getMasterContractFK());
        if (masterContract != null) {
        	String brandingCode = masterContract.getBrandingCompanyCT();
        	if (brandingCode != null) {        	
                CodeTableWrapper codeTableWrapper = CodeTableWrapper.getSingleton();
                brandingCompany = codeTableWrapper.getCodeDescByCodeTableNameAndCode("BRANDINGCOMPANY", brandingCode);
        	}
        }
        quoteMainSessionBean.putValue("brandingCompany", brandingCompany);

        if (premiumDueVOs != null)
        {
            appReqBlock.getHttpSession().setAttribute("premiumDueVOs", premiumDueVOs);
            formBean.putValue("premiumDueHistory", "checked");
        }
        else
        {
            formBean.putValue("premiumDueHistory", "unchecked");
        }
        
       //contract Bill history
       Bill[] bills = Bill.findBillHistoryBySegmentFK(new Long(segmentVO.getSegmentPK()));
        if (bills.length >0)
        {
            appReqBlock.getHttpSession().setAttribute("bills", bills);
            formBean.putValue("contractBillsInd", "checked");
        }
        else
        {
            formBean.putValue("contractBillsInd", "unchecked");
            appReqBlock.getHttpSession().setAttribute("bills", bills);
        }
        
        //get the group number for display if the policy group exists
        formBean.putValue("segmentPK", segmentVO.getSegmentPK() + "");
        formBean.putValue("contractNumber", segmentVO.getContractNumber());

        long contractGroupFK = segmentVO.getContractGroupFK();
        formBean.putValue("contractGroupFK", contractGroupFK + "");

        long billScheduleFK = segmentVO.getBillScheduleFK();
        formBean.putValue("billScheduleFK", billScheduleFK + "");
        BillScheduleVO billScheduleVO = null;
        if (billScheduleFK != 0)
        {
            billScheduleVO = (BillScheduleVO) segmentVO.getParentVO(BillScheduleVO.class);
        }
        
        String billingSchedule = null;
        if (billScheduleVO != null) {
            billingSchedule = billScheduleVO.getBillMethodCT();
        }
        
        quoteMainSessionBean.putValue("billingSchedule", billingSchedule);

        appReqBlock.getHttpSession().setAttribute("BillScheduleVO", billScheduleVO);

        long departmentLocationFK = segmentVO.getDepartmentLocationFK();
        formBean.putValue("departmentLocationFK", departmentLocationFK + "");

        long priorContractGroupFK = segmentVO.getPriorContractGroupFK();
        formBean.putValue("priorContractGroupFK", priorContractGroupFK + "");

        formBean.putValue("priorPRDDue", segmentVO.getPriorPRDDue());

        long batchContractSetupFK = segmentVO.getBatchContractSetupFK();
        formBean.putValue("batchContractSetupFK", batchContractSetupFK + "");
        if (batchContractSetupFK != 0)
        {
            BatchContractSetup batchContractSetup = BatchContractSetup.findByPK(new Long(batchContractSetupFK));
            appReqBlock.getHttpSession().setAttribute("batchContractSetup", batchContractSetup);
        }

        long masterContractFK = segmentVO.getMasterContractFK();
        formBean.putValue("masterContractFK", masterContractFK + "");

        String groupNumber = "";
        if (contractGroupFK != 0)
        {
            groupNumber = Util.initString(ContractGroup.findBy_ContractGroupPK(new Long(contractGroupFK)).getContractGroupNumber(), "");
        }
        String groupName = "";
        if (groupNumber != null) {
        	ContractGroup contractGroup = ContractGroup.findBy_ContractGroupPK(new Long(contractGroupFK));
            if (contractGroup != null) {
                groupNumber = contractGroup.getContractGroupNumber();
                ClientRole[] clientRoles = ClientRole.findBy_RoleType_ReferenceID(ClientRole.ROLETYPECT_GROUP, groupNumber);
                for (int c = 0; c < clientRoles.length; c ++) {
                    groupName = ClientDetail.findBy_ClientDetailPK(clientRoles[c].getClientDetailFK()).getCorporateName();	
                }
            }
        }
        appReqBlock.getSessionBean("quoteMainSessionBean").putValue("groupName", groupName);
        formBean.putValue("groupNumber", groupNumber);

        String dividendOptionCT = Util.initString(segmentVO.getDividendOptionCT(), "");
        formBean.putValue("dividendOptionCT", dividendOptionCT);

        //  Format the date to be mm/dd/yyyy for the jsp page
        String effectiveDate = DateTimeUtil.formatYYYYMMDDToMMDDYYYY(segmentVO.getEffectiveDate());

        String applicationSignedDate = DateTimeUtil.formatYYYYMMDDToMMDDYYYY(segmentVO.getApplicationSignedDate());
        
        String applicationReceivedDate = DateTimeUtil.formatYYYYMMDDToMMDDYYYY(segmentVO.getApplicationReceivedDate());
        
        String quoteDate = DateTimeUtil.formatYYYYMMDDToMMDDYYYY(segmentVO.getQuoteDate());

        String conversionDate = DateTimeUtil.formatYYYYMMDDToMMDDYYYY(segmentVO.getConversionDate());
        
        
        formBean.putValue("effectiveDate", effectiveDate);
        formBean.putValue("applicationSignedDate", applicationSignedDate);
        formBean.putValue("applicationReceivedDate", applicationReceivedDate);
        formBean.putValue("quoteDate", quoteDate);
        formBean.putValue("conversionDate", conversionDate);
        formBean.putValue("terminationDate", DateTimeUtil.formatYYYYMMDDToMMDDYYYY(segmentVO.getTerminationDate()));
        

        String creationDate = DateTimeUtil.formatYYYYMMDDToMMDDYYYY(segmentVO.getCreationDate());

        String creationOperator = Util.initString(segmentVO.getCreationOperator(), "");

        formBean.putValue("creationDate", creationDate);
        formBean.putValue("creationOperator", creationOperator);

        String cashWithAppIndStatus = segmentVO.getCashWithAppInd();

        if (cashWithAppIndStatus != null && cashWithAppIndStatus.equalsIgnoreCase("Y"))
        {
            cashWithAppIndStatus = "checked";
        }
        else
        {
            cashWithAppIndStatus = "unchecked";
        }

        formBean.putValue("cashWithAppIndStatus", cashWithAppIndStatus);

        String segmentStatus = Util.initString(segmentVO.getSegmentStatusCT(), "");

        formBean.putValue("statusCode", segmentStatus);

        formBean.putValue("optionId", optionCode);
        quoteMainSessionBean.putValue("option", optionCode);



        formBean.putValue("premiumTaxes", Util.formatDecimal("########0.00", segmentVO.getCharges()));
        formBean.putValue("frontEndLoads", Util.formatDecimal("########0.00", segmentVO.getLoads()));
        formBean.putValue("fees", Util.formatDecimal("########0.00", segmentVO.getFees()));
        formBean.putValue("savingsPercent", Util.formatDecimal("0.0##", segmentVO.getSavingsPercent()));

        String dateInEffectDate = DateTimeUtil.formatYYYYMMDDToMMDDYYYY(segmentVO.getDateInEffect());
        formBean.putValue("dateInEffect", dateInEffectDate);

        String issueStateORInd = Util.initString(segmentVO.getIssueStateORInd(), "");
        String issueStateORIndStatus = "";

        if (issueStateORInd.equalsIgnoreCase("Y"))
        {
            issueStateORIndStatus = "checked";
        }

        formBean.putValue("issueStateORIndStatus", issueStateORIndStatus);

        String waiverInEffect = Util.initString(segmentVO.getWaiverInEffect(), "");
        String waiverInEffectStatus = "";

        if (waiverInEffect.equalsIgnoreCase("Y"))
        {
            waiverInEffectStatus = "checked";
        }

        formBean.putValue("waiverInEffectStatus", waiverInEffectStatus);

        String dialableSalesLoadPct = (new EDITBigDecimal(segmentVO.getDialableSalesLoadPercentage())).toString();
        if (dialableSalesLoadPct.equalsIgnoreCase("null"))
        {
            dialableSalesLoadPct = "";
        }
        formBean.putValue("dialableSalesLoadPct", dialableSalesLoadPct);

        String chargeDeductDivisionIndStatus = Util.initString(segmentVO.getChargeDeductDivisionInd(), "");
        if (chargeDeductDivisionIndStatus.equalsIgnoreCase("Y"))
        {
            chargeDeductDivisionIndStatus = "checked";
        }

        String pointInScaleIndStatus = Util.initString(segmentVO.getPointInScaleIndicator(), "");
        if (pointInScaleIndStatus.equalsIgnoreCase("Y"))
        {
            pointInScaleIndStatus = "checked";
        }

        if (segmentVO.getBillScheduleFK() > 0)
        {
            formBean.putValue("billingIndStatus", "checked");
        }
        else
        {
            formBean.putValue("billingIndStatus", "unchecked");
        }

        if (segmentVO.getEstateOfTheInsured() != null && segmentVO.getEstateOfTheInsured().equalsIgnoreCase(Segment.INDICATOR_YES))
        {
            formBean.putValue("estateOfTheInsuredIndStatus", "checked");
        }
        else
        {
            formBean.putValue("estateOfTheInsuredIndStatus", "unchecked");
        }


        formBean.putValue("chargeDeductDivisionIndStatus", chargeDeductDivisionIndStatus);
        formBean.putValue("pointInScaleIndStatus", pointInScaleIndStatus);

        String premiumTaxSitusOverride = Util.initString(segmentVO.getPremiumTaxSitusOverrideCT(), "");
        formBean.putValue("premiumTaxSitusOverride", premiumTaxSitusOverride);                

        formBean.putValue("suppOriginalContractNumber", Util.initString(segmentVO.getSuppOriginalContractNumber(), ""));
        formBean.putValue("casetrackingOptionCT", Util.initString(segmentVO.getCasetrackingOptionCT(), ""));

        if (payoutVO != null)
        {
            formBean.putValue("payoutPK", payoutVO.getPayoutPK() + "");

            String startDate = DateTimeUtil.formatYYYYMMDDToMMDDYYYY(payoutVO.getPaymentStartDate());
            formBean.putValue("startDate", startDate);

            String frequency = payoutVO.getPaymentFrequencyCT();

            formBean.putValue("frequencyId", frequency);
            formBean.putValue("certainDuration", payoutVO.getCertainDuration() + "");
            formBean.putValue("reduce1", (new EDITBigDecimal(payoutVO.getReducePercent1())).toString());
            formBean.putValue("reduce2", (new EDITBigDecimal(payoutVO.getReducePercent2())).toString());
            formBean.putValue("exclusionRatio", (new EDITBigDecimal(payoutVO.getExclusionRatio())).toString());
            formBean.putValue("yearlyTaxableBenefit", Util.formatDecimal("########0.00", payoutVO.getYearlyTaxableBenefit()));
            formBean.putValue("paymentAmount", Util.formatDecimal("########0.00", payoutVO.getPaymentAmount()));
//            formBean.putValue("mrdAmount", Util.formatDecimal("########0.00", payoutVO.getMRDAmount()));

            String postJune1986Investment = payoutVO.getPostJune1986Investment();

            if (postJune1986Investment != null && postJune1986Investment.equalsIgnoreCase("Y"))
            {
                formBean.putValue("postJune1986InvestmentIndStatus", "checked");
            }

           // if ((segmentVO.getAmount() > 0) && payoutVO.getPaymentAmount() > 0)
            if (( new EDITBigDecimal(segmentVO.getAmount()).isGT("0"))
                    && new EDITBigDecimal(payoutVO.getPaymentAmount()).isGT("0"))
            {
                formBean.putValue("calculatedValuesIndStatus", "checked");
            }

            formBean.putValue("lastDayOfMonthIndStatus", "unchecked");

            formBean.putValue("finalDistributionAmount", Util.formatDecimal("########0.00", payoutVO.getFinalDistributionAmount()));
            formBean.putValue("totalProjectedAnnuity", Util.formatDecimal("########0.00", payoutVO.getTotalExpectedReturnAmount()));
            formBean.putValue("nextPaymentDate", DateTimeUtil.formatYYYYMMDDToMMDDYYYY(payoutVO.getNextPaymentDate()));
            String certainPeriodEndDate = (payoutVO.getCertainPeriodEndDate() != null) ? payoutVO.getCertainPeriodEndDate() : null;
            quoteMainSessionBean.putValue("certainPeriodEndDate", DateTimeUtil.formatYYYYMMDDToMMDDYYYY(certainPeriodEndDate));
           

            // If optionCode is 'Amount Certain' - store these for when building the QuoteVo

            if (optionCode != null && optionCode.equalsIgnoreCase("AMC"))
            {
                String finalPaymentDate = (payoutVO.getFinalPaymentDate() != null) ? payoutVO.getFinalPaymentDate() : null;
                String terminationDate = certainPeriodEndDate;

                quoteMainSessionBean.putValue("finalPaymentDate", DateTimeUtil.formatYYYYMMDDToMMDDYYYY(finalPaymentDate));
                quoteMainSessionBean.putValue("terminationDate", DateTimeUtil.formatYYYYMMDDToMMDDYYYY(terminationDate));
            }
        }
        
        String exchangeInd = Util.initString(segmentVO.getExchangeInd(), "");

        if (exchangeInd != null && exchangeInd.equalsIgnoreCase("Y"))
        {
            quoteTaxesSessionBean.putValue("exchangeIndStatus", "checked");
        }
        else
        {
            quoteTaxesSessionBean.putValue("exchangeIndStatus", "unchecked");
        }

        String qualNonQual = segmentVO.getQualNonQualCT();
        formBean.putValue("qualNonQual", qualNonQual);

        String qualifiedType = segmentVO.getQualifiedTypeCT();
        formBean.putValue("qualifiedType", qualifiedType);
        String taxReportingGroup = segmentVO.getTaxReportingGroup();

        if (taxReportingGroup == null)
        {
            taxReportingGroup = "";
        }

        quoteTaxesSessionBean.putValue("taxReportingGroup", taxReportingGroup);

//        if (exchangeInd.equalsIgnoreCase("Y") || !taxReportingGroup.equals(""))
//        {
//            formBean.putValue("taxesIndStatus", "checked");
//        }
//        else
//        {
//            formBean.putValue("taxesIndStatus", "unchecked");
//        }

        String issueState = segmentVO.getIssueStateCT();
        formBean.putValue("areaId", issueState);

        formBean.putValue("originalStateCT", segmentVO.getOriginalStateCT());
        quoteMainSessionBean.putValue("originalStateCT", segmentVO.getOriginalStateCT());

        formBean.putValue("location", segmentVO.getLocation());
        quoteMainSessionBean.putValue("location", segmentVO.getLocation());

        formBean.putValue("sequence", segmentVO.getSequence());
        quoteMainSessionBean.putValue("sequence", segmentVO.getSequence());

        formBean.putValue("indivAnnPremium", Util.formatDecimal("########0.00", segmentVO.getIndivAnnPremium()));
        quoteMainSessionBean.putValue("indivAnnPremium", Util.formatDecimal("########0.00", segmentVO.getIndivAnnPremium()));

        ratedGenderCT = segmentVO.getRatedGenderCT();
        formBean.putValue("ratedGenderCT", ratedGenderCT);
        quoteMainSessionBean.putValue("ratedGenderCT", ratedGenderCT);
        
        underwritingClass = segmentVO.getUnderwritingClassCT();
        formBean.putValue("underwritingClass", underwritingClass);
        quoteMainSessionBean.putValue("underwritingClass", underwritingClass);
        
        groupPlan = segmentVO.getGroupPlan();
        formBean.putValue("groupPlan", groupPlan);
        quoteMainSessionBean.putValue("groupPlan", groupPlan);
        
        
        formBean.putValue("costBasis", Util.formatDecimal("########0.00", segmentVO.getCostBasis()));
        String purchaseAmount = Util.formatDecimal("#############0.00", segmentVO.getAmount());
        formBean.putValue("purchaseAmount", purchaseAmount);

        String dismembermentPercent = segmentVO.getDismembermentPercent() + "";
        formBean.putValue("dismembermentPercent", dismembermentPercent);

        String waiveFreeLookIndicator = segmentVO.getWaiveFreeLookIndicator();

        if (waiveFreeLookIndicator != null && waiveFreeLookIndicator.equalsIgnoreCase("Y"))
        {
            waiveFreeLookIndicator = "checked";
        }
        else
        {
            waiveFreeLookIndicator = "";
        }

        formBean.putValue("waiveFreeLookIndicatorStatus", waiveFreeLookIndicator);

        String freeLookDaysOverride = segmentVO.getFreeLookDaysOverride()+"";

        formBean.putValue("freeLookDaysOverride", freeLookDaysOverride);

        String commitmentIndicatorStatus = Util.initString(segmentVO.getCommitmentIndicator(), "");
        if (commitmentIndicatorStatus.equalsIgnoreCase("Y"))
        {
            commitmentIndicatorStatus = "checked";
        }

        String commitmentAmount = Util.formatDecimal("#############0.00", segmentVO.getCommitmentAmount());

        formBean.putValue("commitmentIndicatorStatus", commitmentIndicatorStatus);
        formBean.putValue("commitmentAmount", commitmentAmount);

        formBean.putValue("contractType", segmentVO.getContractTypeCT());

        formBean.putValue("units", Util.initBigDecimal(segmentVO, "Units", new BigDecimal("0")).toString());
        formBean.putValue("commissionPhaseID", segmentVO.getCommissionPhaseID()+ "");
        formBean.putValue("commissionPhaseOverride", Util.initString(segmentVO.getCommissionPhaseOverride(), ""));
        formBean.putValue("totalFaceAmount", Util.initBigDecimal(segmentVO, "TotalFaceAmount", new BigDecimal("0")).toString());
        formBean.putValue("annualPremium", Util.initBigDecimal(segmentVO, "AnnualPremium", new BigDecimal("0")).toString());
        formBean.putValue("applicationState", Util.initString(segmentVO.getApplicationSignedStateCT(), ""));
        formBean.putValue("originalState", Util.initString(segmentVO.getOriginalStateCT(), ""));
        formBean.putValue("firstNotifyDate", DateTimeUtil.formatYYYYMMDDToMMDDYYYY(segmentVO.getFirstNotifyDate()));
        formBean.putValue("previousNotifyDate", DateTimeUtil.formatYYYYMMDDToMMDDYYYY(segmentVO.getPreviousNotifyDate()));
        formBean.putValue("finalNotifyDate", DateTimeUtil.formatYYYYMMDDToMMDDYYYY(segmentVO.getFinalNotifyDate()));
        formBean.putValue("advanceFinalNotify", segmentVO.getAdvanceFinalNotify());
        formBean.putValue("ageAtIssue", segmentVO.getAgeAtIssue() + "");
        formBean.putValue("originalStateCT", segmentVO.getOriginalStateCT() + "");
        formBean.putValue("location", segmentVO.getLocation() + "");
        formBean.putValue("sequence", segmentVO.getSequence() + "");
        formBean.putValue("indivAnnPremium", Util.initBigDecimal(segmentVO, "indivAnnPremium", new BigDecimal("0")).toString());
        formBean.putValue("amount", Util.initBigDecimal(segmentVO, "amount", new BigDecimal("0")).toString());
        formBean.putValue("worksheetTypeCT", segmentVO.getWorksheetTypeCT());
        formBean.putValue("requirementEffectiveDate", DateTimeUtil.formatYYYYMMDDToMMDDYYYY(segmentVO.getRequirementEffectiveDate()));
        formBean.putValue("deductionAmountOverride", Util.initBigDecimal(segmentVO, "deductionAmountOverride", new BigDecimal("0")).toString());
        formBean.putValue("deductionAmountEffectiveDate", DateTimeUtil.formatYYYYMMDDToMMDDYYYY(segmentVO.getDeductionAmountEffectiveDate()));

        String scheduledPremiumAmount = "";
    	String billTypeCT = Util.initString(billScheduleVO.getBillTypeCT(), "");
    	PremiumDue premiumDue = null; 

    	if (billScheduleVO != null) {
    		premiumDue = PremiumDue.findBySegmentPK_maxEffectiveDate(new Long(segmentPK), new EDITDate(billScheduleVO.getNextBillDueDate()));
    	} else {
    		premiumDue = PremiumDue.findBySegmentPK_LatestEffectiveDate(new Long(segmentPK));
    	}
    	
    	scheduledPremiumAmount = (premiumDue != null ? premiumDue.getScheduledPremiumAmount(billTypeCT) : "");    	
    	formBean.putValue("scheduledPremiumAmount", scheduledPremiumAmount);

        String dateIndStatus = "unchecked";
        if (segmentVO.getFirstNotifyDate() != null || segmentVO.getPreviousNotifyDate() != null ||
            segmentVO.getFinalNotifyDate() != null)
        {
            dateIndStatus = "checked";
        }
        formBean.putValue("datesIndStatus", dateIndStatus);

        //This table will only exist for Life contracts
        if (lifeVOs != null && lifeVOs.length > 0)
        {
            loadLifeVO(lifeVOs[0], formBean);
        }

        long productStructurePK = segmentVO.getProductStructureFK();

//        engine.business.Lookup engineLookup = new engine.component.LookupComponent();
       // String RatedGend = Segment.findRatedGenderByProdStructPK(productStructurePK);
        ProductStructure productStructure = ProductStructure.findByPK(productStructurePK);
        Company company = Company.findByPK(productStructure.getCompanyFK());

        String companyName = company.getCompanyName();
        String marketingPackage = productStructure.getMarketingPackageName();
        String groupProduct = productStructure.getGroupProductName();
        String areaName = productStructure.getAreaName();
        String businessName = productStructure.getBusinessContractName();

        formBean.putValue("companyStructureId", productStructurePK + "");
        formBean.putValue("companyStructure", companyName + "," +
                                       marketingPackage + "," + groupProduct + "," +
                                        areaName + "," + businessName);

        quoteMainSessionBean.putValue("contractId", segmentVO.getContractNumber());
        
        appReqBlock.getUserSession().setContractNumber(segmentVO.getContractNumber());
        quoteMainSessionBean.putValue("segmentPK", segmentPK + "");
        quoteMainSessionBean.putValue("companyStructure", companyName + "," +
                                       marketingPackage + "," + groupProduct + "," +
                                        areaName + "," + businessName);
        quoteMainSessionBean.putValue("status", segmentStatus);
        quoteMainSessionBean.putValue("companyStructurePK", productStructurePK + "");
        quoteMainSessionBean.putValue("companyName", companyName);
        quoteMainSessionBean.putValue("product", businessName);
        quoteMainSessionBean.putValue("productStructurePK", productStructure.getProductStructurePK() + "");

        if (segmentVO.getContractGroupFK() > 0)
        {
            ContractGroup contractGroup = ContractGroup.findBy_ContractGroupPK(new Long(segmentVO.getContractGroupFK()));
            quoteMainSessionBean.putValue("contractGroup", contractGroup.getContractGroupNumber());
        }
        quoteMainSessionBean.putValue("clientUpdate", Util.initString(segmentVO.getClientUpdate(), ""));

        ContractRequirementVO[] contractRequirementVO = segmentVO.getContractRequirementVO();
        appReqBlock.getHttpSession().setAttribute("contractRequirementVO", contractRequirementVO);

        DepositsVO[] depositsVOs = segmentVO.getDepositsVO();

        for (int i = 0; i < depositsVOs.length; i++)
        {
            depositsVOs[i].setVoChanged(false);
        }

        appReqBlock.getHttpSession().setAttribute("depositsVOs", depositsVOs);
        appReqBlock.getHttpSession().setAttribute("quoteMainSessionBean", quoteMainSessionBean);

        //This sets up Suspense tab processing using the ContractDetailTran
        SessionBean contractMainSessionBean = appReqBlock.getSessionBean("contractMainSessionBean");
        contractMainSessionBean.putValue("contractId", segmentVO.getContractNumber());
        contractMainSessionBean.putValue("companyStructureId", segmentVO.getProductStructureFK() + "");
        appReqBlock.getHttpSession().setAttribute("contractMainSessionBean", contractMainSessionBean);

    }

    /**
     * Put the Life table fields into the page bean for display
     * @param lifeVO
     * @param formBean
     */
    private void loadLifeVO(LifeVO lifeVO, PageBean formBean)
    {
        String lifePK = lifeVO.getLifePK() + "";
        String nonForfeitureOption = Util.initString(lifeVO.getNonForfeitureOptionCT(), "");
        String deathBeneOption = Util.initString(lifeVO.getDeathBenefitOptionCT(), "");
        String option7702 = Util.initString(lifeVO.getOption7702CT(), "");
        String faceAmount = Util.initString(lifeVO.getFaceAmount() + "", "");
        String term = Util.initString(lifeVO.getTerm() + "", "");
        String MAPEndDate = DateTimeUtil.formatYYYYMMDDToMMDDYYYY(lifeVO.getMAPEndDate());
        String tamraInitAdjValue = Util.formatDecimal("#############0.00", lifeVO.getTamraInitAdjValue());

        String guidelineSinglePrem = lifeVO.getGuidelineSinglePremium() + "";
        String guidelineLevelPrem = Util.formatDecimal("#############0.00", lifeVO.getGuidelineLevelPremium());
        String tamra = Util.formatDecimal("#############0.00", lifeVO.getTamra());
        String mecGuidelineSinglePremium = Util.formatDecimal("#############0.00", lifeVO.getMECGuidelineSinglePremium());
        String mecGuidelineLevelPremium = Util.formatDecimal("#############0.00", lifeVO.getMECGuidelineLevelPremium());
        String cumGuidelineLevelPremium = Util.formatDecimal("#############0.00", lifeVO.getCumGuidelineLevelPremium());
        String MECDate = DateTimeUtil.formatYYYYMMDDToMMDDYYYY(lifeVO.getMECDate());
        String pendingDBOChangeIndStatus = Util.initString(lifeVO.getPendingDBOChangeInd(), "");
        String startNew7PayIndicator = Util.initString(lifeVO.getStartNew7PayIndicator(), "");
        String mecStatus = Util.initString(lifeVO.getMECStatusCT(), "");
        String maxNetAmountAtRisk = Util.formatDecimal("#############0.00", lifeVO.getMaximumNetAmountAtRisk());
        String paidToDate = DateTimeUtil.formatYYYYMMDDToMMDDYYYY(lifeVO.getPaidToDate());
        String lapsePendingDate = DateTimeUtil.formatYYYYMMDDToMMDDYYYY(lifeVO.getLapsePendingDate());
        String lapseDate = DateTimeUtil.formatYYYYMMDDToMMDDYYYY(lifeVO.getLapseDate());
        String tamraStartDate = DateTimeUtil.formatYYYYMMDDToMMDDYYYY(lifeVO.getTamraStartDate());

        
        formBean.putValue("lifePK", lifePK);
        formBean.putValue("deathBeneOption", deathBeneOption);
        formBean.putValue("nonForfeitureOption", nonForfeitureOption);
        formBean.putValue("option7702", option7702);
        formBean.putValue("faceAmount", faceAmount);
        formBean.putValue("term", term);
        formBean.putValue("MAPEndDate", MAPEndDate);
        formBean.putValue("tamraInitAdjValue", tamraInitAdjValue);
        
        formBean.putValue("guidelineSinglePrem", guidelineSinglePrem);
        formBean.putValue("guidelineLevelPrem", guidelineLevelPrem);
        formBean.putValue("tamra", tamra);
        formBean.putValue("MECDate", MECDate);
        formBean.putValue("pendingDBOChangeIndStatus", pendingDBOChangeIndStatus);
        formBean.putValue("tamraStartDate", tamraStartDate);
        formBean.putValue("mecGuidelineSinglePremium", mecGuidelineSinglePremium);
        formBean.putValue("mecGuidelineLevelPremium", mecGuidelineLevelPremium);
        formBean.putValue("cumGuidelineLevelPremium", cumGuidelineLevelPremium);
        formBean.putValue("startNew7PayIndicatorStatus", startNew7PayIndicator);
        formBean.putValue("mecStatus", mecStatus);
        formBean.putValue("maxNetAmountAtRisk", maxNetAmountAtRisk);
        formBean.putValue("paidToDate", paidToDate);
        formBean.putValue("lapsePendingDate", lapsePendingDate);
        formBean.putValue("lapseDate", lapseDate);
        formBean.putValue("currentDeathBenefit", lifeVO.getCurrentDeathBenefit() + "");
        formBean.putValue("guarPaidUpTerm", lifeVO.getGuarPaidUpTerm() + "");
        formBean.putValue("nonGuarPaidUpTerm", lifeVO.getNonGuarPaidUpTerm() + "");
        formBean.putValue("mortalityCredit", lifeVO.getMortalityCredit() + "");
        formBean.putValue("endowmentCredit", lifeVO.getEndowmentCredit() + "");
        formBean.putValue("excessInterestCredit", lifeVO.getExcessInterestCredit() + "");
    }

    /**
     * Loads NoteReminder information to the beans for display on the screen
     *
     * @param appReqBlock
     * @param segmentVO
     */
    private void loadNotes(AppReqBlock appReqBlock, SegmentVO segmentVO)
    {
        SessionBean quoteMainSessionBean = appReqBlock.getSessionBean("quoteMainSessionBean");

        PageBean formBean = quoteMainSessionBean.getPageBean("formBean");

        NoteReminderVO[] noteReminderVOs = segmentVO.getNoteReminderVO();

        if (noteReminderVOs != null)
        {
            SessionBean quoteNotesSessionBean = appReqBlock.getSessionBean("quoteNotesSessionBean");

            if (noteReminderVOs.length > 0)
            {
                formBean.putValue("notesIndStatus", "checked");
            }

            for (int n = 0; n < noteReminderVOs.length; n++)
            {
                PageBean noteBean = new PageBean();

                String noteReminderPK = noteReminderVOs[n].getNoteReminderPK() + "";
                String noteTypeId = noteReminderVOs[n].getNoteTypeCT();
                String sequence = noteReminderVOs[n].getSequence() + "";
                String noteQualifierId = noteReminderVOs[n].getNoteQualifierCT();
                String key = noteReminderPK + sequence + noteTypeId + noteQualifierId;
                String notes = noteReminderVOs[n].getNote();

                noteBean.putValue("noteReminderPK", noteReminderPK);
                noteBean.putValue("segmentFK", noteReminderVOs[n].getSegmentFK() + "");
                noteBean.putValue("noteTypeId", noteTypeId);
                noteBean.putValue("sequence", sequence);
                noteBean.putValue("noteQualifierId", noteQualifierId);
                noteBean.putValue("note", notes);
                noteBean.putValue("maintDate", noteReminderVOs[n].getMaintDateTime());
                noteBean.putValue("operator", noteReminderVOs[n].getOperator());
                noteBean.putValue("key", key);

                quoteNotesSessionBean.putPageBean(key, noteBean);
            }
        }
    }

    /**
     * Loads client information to the beans for display on the screen
     *
     * @param appReqBlock
     * @param segmentVO
     *
     * @throws Exception
     */
    private void loadClients(AppReqBlock appReqBlock, SegmentVO segmentVO) throws Exception
    {
        ContractClientVO[] contractClientVOs = segmentVO.getContractClientVO();

        PageBean clientPageBean = null;

        populateClientBean(contractClientVOs, segmentVO, segmentVO.getOptionCodeCT(),
                segmentVO.getRiderNumber(), clientPageBean, appReqBlock);

        SegmentVO[] riderVOs = segmentVO.getSegmentVO();

        for (int i = 0; i < riderVOs.length; i++)
        {
            ContractClientVO[] riderContractClients = riderVOs[i].getContractClientVO();
            if (riderContractClients != null)
            {
                populateClientBean(riderContractClients, segmentVO,
                        riderVOs[i].getOptionCodeCT(), riderVOs[i].getRiderNumber(), clientPageBean, appReqBlock);
            }
        }
    }

    /**
     * Loads AgentHierarchy information from persistence to the beans for display on the screen
     *
     * @param appReqBlock
     * @param segmentVO
     *
     * @throws Exception
     */
    private void loadAgentHierarchy(AppReqBlock appReqBlock, SegmentVO segmentVO) throws Exception
    {
        List uiAgentHierarchies = new ArrayList();

        agent.business.Agent agentComponent = new agent.component.AgentComponent();

        AgentHierarchyAllocation[] agentHierarchyAllocations = AgentHierarchyAllocation.findAllBySegmentFK(new Long(segmentVO.getSegmentPK()));

        for (int i = 0; i < agentHierarchyAllocations.length; i++)
        {
            UIAgentHierarchyVO uiAgentHierarchyVO = new UIAgentHierarchyVO();

            uiAgentHierarchyVO.setAgentHierarchyAllocationVO(agentHierarchyAllocations[i].getAsVO());

            //  Need to get the fully composed AgentHierarchy and Agent from the fully composed Segment since pages need PlacedAgents, etc.
            AgentHierarchyVO[] agentHierarchyVOs = segmentVO.getAgentHierarchyVO();

            if (agentHierarchyVOs != null)
            {
                for (int j = 0; j < agentHierarchyVOs.length; j++)
                {
                    if (agentHierarchyVOs[j].getAgentHierarchyPK() == agentHierarchyAllocations[i].getAgentHierarchyFK().longValue())
                    {
                        //  This is the AgentHierarchy that goes with the AgentHierarchyAllocation, attach the allocation
                        // (for cloud land saves) and put it in the UI object
                        agentHierarchyVOs[j].addAgentHierarchyAllocationVO(agentHierarchyAllocations[i].getAsVO());

                        //  Get the composed Agent for this hierarchy and put in UI object
                        List voInclusionList = new ArrayList();
                        voInclusionList.add(CommissionProfileVO.class);
                        voInclusionList.add(AgentContractVO.class);
                        voInclusionList.add(PlacedAgentVO.class);
                        voInclusionList.add(ClientRoleVO.class);
                        voInclusionList.add(ClientDetailVO.class);

                        long agentFK = agentHierarchyVOs[j].getAgentFK();

                        AgentVO agentVO = agentComponent.composeAgentVO(agentFK, voInclusionList);

                        AgentSnapshotVO[] agentSnapshotVOs = agentHierarchyVOs[j].getAgentSnapshotVO();

                        voInclusionList.clear();
                        voInclusionList.add(AgentVO.class);
                        voInclusionList.add(AgentContractVO.class);
                        voInclusionList.add(ClientRoleVO.class);
                        voInclusionList.add(ClientDetailVO.class);

//                        List commVOInclusionList = new ArrayList();

                        for (int s = 0; s < agentSnapshotVOs.length; s++)
                        {
                            long placedAgentFK = agentSnapshotVOs[s].getPlacedAgentFK();
                            PlacedAgentVO placedAgentVO = agentComponent.composePlacedAgentVOByPlacedAgentPK(placedAgentFK, voInclusionList);
                            agentSnapshotVOs[s].setParentVO(PlacedAgentVO.class, placedAgentVO);

//                            long commissionProfileFK = agentSnapshotVOs[s].getCommissionProfileFK();
//                            CommissionProfileVO commissionProfileVO = agentComponent.composeCommissionProfileVOByCommissionProfilePK(commissionProfileFK, commVOInclusionList);
//                            agentSnapshotVOs[s].setParentVO(CommissionProfileVO.class, commissionProfileVO);
                        }

                        uiAgentHierarchyVO.setAgentHierarchyVO(agentHierarchyVOs[j]);
                        uiAgentHierarchyVO.setAgentVO(agentVO);

                        break;
                    }
                }
            }

            uiAgentHierarchyVO.setCoverage(segmentVO.getOptionCodeCT());

            uiAgentHierarchies.add(uiAgentHierarchyVO);
        }

        if (uiAgentHierarchies.size() > 0)
        {
            UIAgentHierarchyVO[] uiAgentHierarchyVOs = (UIAgentHierarchyVO[]) uiAgentHierarchies.toArray(new UIAgentHierarchyVO[uiAgentHierarchies.size()]);

            appReqBlock.getHttpSession().setAttribute("uiAgentHierarchyVOs", uiAgentHierarchyVOs);
        }
    }

    /**
     * Loads Investment information from persistence to the beans for display on the screen
     *
     * @param appReqBlock
     * @param segmentVO
     *
     * @throws Exception
     */
    private void loadInvestment(AppReqBlock appReqBlock, SegmentVO segmentVO) throws Exception
    {
        SessionBean funds = appReqBlock.getSessionBean("quoteFunds");

        InvestmentVO[] investmentVOs = segmentVO.getInvestmentVO();

        String optionCode = segmentVO.getOptionCodeCT();

        engine.business.Lookup engineLookup = new engine.component.LookupComponent();

        if (investmentVOs != null)
        {
            for (int y = 0; y < investmentVOs.length; y++)
            {
                    //All investments must remain in session in order not to delete any on crud save
                String status = investmentVOs[y].getStatus();
//                if (status == null || status.equals(""))
//                {
                    String investmentPK = investmentVOs[y].getInvestmentPK() + "";
                    String segmentFK = investmentVOs[y].getSegmentFK() + "";
                    long filteredFundFK = investmentVOs[y].getFilteredFundFK();
                    String excessIntCalcDate = DateTimeUtil.formatYYYYMMDDToMMDDYYYY(investmentVOs[y].getExcessInterestCalculationDate());
                    String excessIntPymtDate = DateTimeUtil.formatYYYYMMDDToMMDDYYYY(investmentVOs[y].getExcessInterestPaymentDate());
                    String excessInterest = investmentVOs[y].getExcessInterest() + "";
                    String excessIntMethod = investmentVOs[y].getExcessInterestMethod();
                    String excessIntStartDate = DateTimeUtil.formatYYYYMMDDToMMDDYYYY(investmentVOs[y].getExcessInterestStartDate());
                    String air = investmentVOs[y].getAssumedInvestmentReturn() + "";

                    FundVO fundVO = engineLookup.composeFundVOByFilteredFundPK(filteredFundFK, new ArrayList());
                    String fundName = null;
                    String fundType = null;
                    if (fundVO != null)
                    {
                        fundName = fundVO.getName();
                        fundType = fundVO.getFundType();
                    }

                    String allocationPercent = "0";
                    String dollars = "";
                    String units = "";
                    String investmentAllocationPK = "";
                    InvestmentAllocationVO[] investmentAllocationVOs = investmentVOs[y].getInvestmentAllocationVO();

                    if (investmentAllocationVOs != null)
                    {
                        for (int v = 0; v < investmentAllocationVOs.length; v++)
                        {
                            if (investmentAllocationVOs[v].getOverrideStatus().equalsIgnoreCase("P"))
                            {
                                allocationPercent = investmentAllocationVOs[v].getAllocationPercent() + "";
                                dollars = Util.formatDecimal("########0.00", investmentAllocationVOs[v].getDollars());
                                units = investmentAllocationVOs[v].getUnits() + "";
                                investmentAllocationPK = investmentAllocationVOs[v].getInvestmentAllocationPK() + "";
                                break;
                            }
                        }
                    }

                    PageBean fundRelationshipPageBean = null;
                    String key = optionCode + filteredFundFK;

                    if (!funds.pageBeanExists(key))
                    {
                        fundRelationshipPageBean = new PageBean();
                        funds.putPageBean(key, fundRelationshipPageBean);
                    }
                    else
                    {
                        fundRelationshipPageBean = funds.getPageBean(key);
                    }

                    fundRelationshipPageBean.putValue("investmentPK", investmentPK);
                    fundRelationshipPageBean.putValue("filteredFundFK", filteredFundFK + "");
                    fundRelationshipPageBean.putValue("fundName", fundName);
                    fundRelationshipPageBean.putValue("fundType", fundType);
                    fundRelationshipPageBean.putValue("segmentFK", segmentFK);
                    fundRelationshipPageBean.putValue("allocationPercent", allocationPercent);
                    fundRelationshipPageBean.putValue("optionId", optionCode);
                    fundRelationshipPageBean.putValue("dollars", dollars);
                    fundRelationshipPageBean.putValue("units", units);
                    fundRelationshipPageBean.putValue("excessIntCalcDate", excessIntCalcDate);
                    fundRelationshipPageBean.putValue("excessIntPymtDate", excessIntPymtDate);
                    fundRelationshipPageBean.putValue("excessInterest", excessInterest);
                    fundRelationshipPageBean.putValue("excessIntMethod", excessIntMethod);
                    fundRelationshipPageBean.putValue("excessIntStartDate", excessIntStartDate);
                    fundRelationshipPageBean.putValue("air", air);
                    fundRelationshipPageBean.putValue("investmentAllocationPK", investmentAllocationPK);

                    if (status != null)
                    {
                        fundRelationshipPageBean.putValue("status", status);
                    }
//                }
            }
        }
    }

    /**
     * Loads history information to the beans for display on the screen
     *
     * @param appReqBlock
     * @param segmentVO
     *
     * @throws Exception
     */
    private void loadHistory(AppReqBlock appReqBlock, SegmentVO segmentVO) throws Exception
    {
        // Loops for Financial History VO
        event.business.Event eventComponent = new event.component.EventComponent();
        List voInclusionList = new ArrayList();
        voInclusionList.add(EDITTrxCorrespondenceVO.class);
        voInclusionList.add(EDITTrxHistoryVO.class);
        voInclusionList.add(WithholdingHistoryVO.class);
        voInclusionList.add(ChargeHistoryVO.class);
        voInclusionList.add(FinancialHistoryVO.class);
        voInclusionList.add(GroupSetupVO.class);
        voInclusionList.add(ContractSetupVO.class);
        voInclusionList.add(ClientSetupVO.class);
        voInclusionList.add(BucketHistoryVO.class);
        voInclusionList.add(BucketVO.class);
        voInclusionList.add(InvestmentVO.class);
        voInclusionList.add(FilteredFundVO.class);
        voInclusionList.add(FundVO.class);
        voInclusionList.add(SegmentVO.class);
        voInclusionList.add(TransactionCorrespondenceVO.class);

        String[] pendingStatuses = new String[] {"H", "L"};

        List segmentPKList = new ArrayList();

        segmentPKList.add(new Long(segmentVO.getSegmentPK()));

        contract.business.Lookup contractLookup = new contract.component.LookupComponent();

        SegmentVO[] riderSegmentVOs = contractLookup.findRiderSegmentsBy_SegmentPK(segmentVO.getSegmentPK());
        if (riderSegmentVOs != null && riderSegmentVOs.length > 0)
        {
            for (int i = 0; i < riderSegmentVOs.length; i++)
            {
                segmentPKList.add(new Long(riderSegmentVOs[i].getSegmentPK()));
            }
        }

        EDITTrxVO[] editTrxVOs =
                eventComponent.composeEDITTrxVOBySegmentPKs_AND_PendingStatus(segmentPKList, pendingStatuses, voInclusionList);

        if (editTrxVOs != null)
        {
            appReqBlock.getHttpSession().setAttribute("editTrxVOs", editTrxVOs);
        } // end if (EDIT Trx)
    }

    /**
     * Loads pending transaction information
     *
     * @param appReqBlock
     * @param segmentVO
     *
     * @throws Exception
     */
    private void loadTransactions(AppReqBlock appReqBlock, SegmentVO segmentVO) throws Exception
    {
        SessionBean quoteTransactions = appReqBlock.getSessionBean("quoteTransactions");
        quoteTransactions.clearState();

        event.business.Event eventComponent = new event.component.EventComponent();
        List voInclusionList = new ArrayList();

        voInclusionList.add(GroupSetupVO.class);
        voInclusionList.add(ContractSetupVO.class);
        voInclusionList.add(ClientSetupVO.class);
        voInclusionList.add(ChargeVO.class);
        voInclusionList.add(InvestmentAllocationOverride.class);
        voInclusionList.add(ContractClientAllocationOvrd.class);
        voInclusionList.add(ScheduledEvent.class);
        voInclusionList.add(SegmentVO.class);

        String[] pendingStatuses = new String[] {"P"};

        List segmentPKList = new ArrayList();

        segmentPKList.add(new Long(segmentVO.getSegmentPK()));

        contract.business.Lookup contractLookup = new contract.component.LookupComponent();

        SegmentVO[] riderSegmentVOs = contractLookup.findRiderSegmentsBy_SegmentPK(segmentVO.getSegmentPK());
        if (riderSegmentVOs != null && riderSegmentVOs.length > 0)
        {
            for (int i = 0; i < riderSegmentVOs.length; i++)
            {
                segmentPKList.add(new Long(riderSegmentVOs[i].getSegmentPK()));
            }
        }

        EDITTrxVO[] editTrxVOs =
                eventComponent.composeEDITTrxVOBySegmentPKs_AND_PendingStatus(segmentPKList, pendingStatuses, voInclusionList);

        if (editTrxVOs != null)
        {
            appReqBlock.getHttpSession().setAttribute("pendingTransactions", editTrxVOs);
        }
    }

    /**
     * Loads Suspense information from persistence to thebeans for display on the screen
     *
     * @param appReqBlock
     * @param segmentVO
     *
     * @throws Exception
     */
    private void loadSuspense(AppReqBlock appReqBlock, SegmentVO segmentVO) throws Exception
    {
        event.business.Event eventComponent = new event.component.EventComponent();

        SuspenseVO[] contractSuspenseVOs = eventComponent.composeSuspenseVOByUserDefNumber(segmentVO.getContractNumber(), new ArrayList());

        if (contractSuspenseVOs != null)
        {
            appReqBlock.getHttpSession().setAttribute("contractSuspenseVOs", contractSuspenseVOs);
        }
    }

    private void populateClientBean(ContractClientVO[] contractClientVOs, SegmentVO segmentVO, String optionCode, int riderNumber,
                                    PageBean clientPageBean, AppReqBlock appReqBlock) throws Exception
    {
        client.business.Lookup clientLookup = new client.component.LookupComponent();
        role.business.Lookup roleLookup = new role.component.LookupComponent();

        for (int x = 0; x < contractClientVOs.length; x++)
        {
            long clientRoleFK = contractClientVOs[x].getClientRoleFK();
            String taxId = "";
            String firstName = "";
            String middleName = "";
            String lastName = "";
            String corporateName = "";
            String dob = "";
            String genderId = "";
            String citizenshipIndStatus = "";
            String newIssuesEligibilityStatus = "";
            String newIssuesStartDate = "";
            String newIssuesStopDate = "";
            String prefix = "";
            String suffix = "";
            String operatorContractClient = "";
            String maintDateTime = "";

//            String groupName = "";

                        
            if (clientRoleFK > 0)
            {
            	// ECK
                ClientRoleVO[] clientRoleVO = roleLookup.getRoleByClientRolePK(clientRoleFK);
                long clientDetailFK = clientRoleVO[0].getClientDetailFK();
                long preferenceFK = clientRoleVO[0].getPreferenceFK();
                List voExclusionList = new ArrayList();
                voExclusionList.add(ClientAddressVO.class);
                voExclusionList.add(ClientRoleVO.class);

                ClientDetailVO[] clientDetailVO = clientLookup.findClientDetailByClientPK(clientDetailFK, true, voExclusionList);

                taxId = Util.initString(clientDetailVO[0].getTaxIdentification(), null);
                firstName = Util.initString(clientDetailVO[0].getFirstName(), "");
                lastName = Util.initString(clientDetailVO[0].getLastName(), "");
                middleName = Util.initString(clientDetailVO[0].getMiddleName(), "");
                prefix = Util.initString(clientDetailVO[0].getNamePrefix(), "");
                suffix = Util.initString(clientDetailVO[0].getNameSuffix(), "");
                corporateName = clientDetailVO[0].getCorporateName();
                dob = DateTimeUtil.formatYYYYMMDDToMMDDYYYY(clientDetailVO[0].getBirthDate());
                newIssuesEligibilityStatus = Util.initString(clientRoleVO[0].getNewIssuesEligibilityStatusCT(), "");
                newIssuesStartDate = DateTimeUtil.formatYYYYMMDDToMMDDYYYY(clientRoleVO[0].getNewIssuesEligibilityStartDate());

                genderId = clientDetailVO[0].getGenderCT();

                TaxInformationVO[] taxInformationVO = clientDetailVO[0].getTaxInformationVO();

                if (taxInformationVO != null && taxInformationVO.length > 0)
                {
                    citizenshipIndStatus = taxInformationVO[0].getCitizenshipIndCT();
                    if (citizenshipIndStatus != null && citizenshipIndStatus.equalsIgnoreCase("Y"))
                    {
                        citizenshipIndStatus = "checked";
                    }

                    else
                    {
                        citizenshipIndStatus = "unchecked";
                    }
                }

                String clientRelationship = Util.initString(clientRoleVO[0].getRoleTypeCT(), "");

                String clientContractId = segmentVO.getContractNumber();
                String clientIssueAge = contractClientVOs[x].getIssueAge() + "";
                String relationToIns = contractClientVOs[x].getRelationshipToInsuredCT();
                String relationToEmp = contractClientVOs[x].getRelationshipToEmployeeCT();
                String phoneAuth = contractClientVOs[x].getTelephoneAuthorizationCT();
                String classType = Util.initString(contractClientVOs[x].getClassCT(), "");
                String flatExtra = Util.initString(contractClientVOs[x].getFlatExtra().toString(),  "");
                String flatExtraAge = Util.initString(contractClientVOs[x].getFlatExtraAge() + "", "");
                String flatExtraDur = Util.initString(contractClientVOs[x].getFlatExtraDur() + "", "");
                String percentExtra = (Util.initEDITBigDecimal(contractClientVOs[x].getPercentExtra().toString(), new EDITBigDecimal())).toString();
                String originalClassCT = contractClientVOs[x].getOriginalClassCT();

                String percentExtraAge = Util.initString(contractClientVOs[x].getPercentExtraAge() + "", "");
                String percentExtraDur = Util.initString(contractClientVOs[x].getPercentExtraDur() + "", "");
                String tableRating = Util.initString(contractClientVOs[x].getTableRatingCT(), "");
                String payorOf = Util.initString(contractClientVOs[x].getPayorOfCT(), "");
                String ratedGender = Util.initString(contractClientVOs[x].getRatedGenderCT(), "");
                String underwritingClass = Util.initString(contractClientVOs[x].getUnderwritingClassCT(), "");
                String overrideStatus = Util.initString(contractClientVOs[x].getOverrideStatus(), "");
                String contractClientPK = contractClientVOs[x].getContractClientPK() + "";
                String employeeIdentification = Util.initString(contractClientVOs[x].getEmployeeIdentification(), "");
                String clientIdKey = taxId + (riderNumber + "") + optionCode + clientRelationship + contractClientPK + clientRoleFK;
                operatorContractClient = Util.initString(contractClientVOs[x].getOperator(), "");
                maintDateTime = Util.initString(contractClientVOs[x].getMaintDateTime(), "");

                SessionBean clients = appReqBlock.getSessionBean("quoteClients");

                if (clients.pageBeanExists(clientIdKey))
                {
                    clientPageBean = clients.getPageBean(clientIdKey);
                }
                else
                {
                    clientPageBean = new PageBean();

                    clients.putPageBean(clientIdKey, clientPageBean);
                }

                clientPageBean.putValue("contractClientPK", contractClientPK);
                clientPageBean.putValue("clientRoleFK", clientRoleFK + "");
                clientPageBean.putValue("taxId", taxId);
                clientPageBean.putValue("contractId", clientContractId);
                clientPageBean.putValue("issueAge", clientIssueAge);
                clientPageBean.putValue("relationshipInd", clientRelationship);
                clientPageBean.putValue("relationToIns", relationToIns);
                clientPageBean.putValue("relationToEmp", relationToEmp);
                clientPageBean.putValue("phoneAuth", phoneAuth);
                clientPageBean.putValue("classType", classType);
                clientPageBean.putValue("flatExtra", flatExtra);
                clientPageBean.putValue("flatExtraAge", flatExtraAge);
                clientPageBean.putValue("flatExtraDur", flatExtraDur);
                clientPageBean.putValue("percentExtra", percentExtra);
                clientPageBean.putValue("percentExtraAge", percentExtraAge);
                clientPageBean.putValue("percentExtraDur", percentExtraDur);
                clientPageBean.putValue("tableRating", tableRating);
                clientPageBean.putValue("payorOf", payorOf);
                clientPageBean.putValue("ratedGender", ratedGender);
                clientPageBean.putValue("operator", operatorContractClient);
                clientPageBean.putValue("maintDateTime", maintDateTime);
                clientPageBean.putValue("underwritingClass", underwritingClass);
                clientPageBean.putValue("overrideStatus", overrideStatus);
                clientPageBean.putValue("employeeIdentification", employeeIdentification);
                clientPageBean.putValue("beneRelationshipToIns", Util.initString(contractClientVOs[x].getBeneRelationshipToInsured(), ""));
                clientPageBean.putValue("riderNumber", riderNumber + "");
                clientPageBean.putValue("originalClassCT", originalClassCT);

                String clientEffDate = DateTimeUtil.formatYYYYMMDDToMMDDYYYY(contractClientVOs[x].getEffectiveDate());

                String terminateDate = DateTimeUtil.formatYYYYMMDDToMMDDYYYY(contractClientVOs[x].getTerminationDate());

                clientPageBean.putValue("effectiveDate", clientEffDate);
                clientPageBean.putValue("terminationDate", terminateDate);

                ContractClientAllocationVO[] contractClientAllocationVO = contractClientVOs[x].getContractClientAllocationVO();

                if (contractClientAllocationVO != null)
                {
                    for (int a = 0; a < contractClientAllocationVO.length; a++)
                    {
                        if (contractClientAllocationVO[a].getOverrideStatus().equalsIgnoreCase("P"))
                        {
                            String contractClientAllocPK = contractClientAllocationVO[a].getContractClientAllocationPK() + "";
                            String allocationPct = new EDITBigDecimal(contractClientAllocationVO[a].getAllocationPercent()).toString();
                            String splitEqualInd = Util.initString(contractClientAllocationVO[a].getSplitEqual(), "N");
                            String allocationAmount = new EDITBigDecimal(contractClientAllocationVO[a].getAllocationDollars()).toString();

                            clientPageBean.putValue("contractClientAllocationPK", contractClientAllocPK);
                            clientPageBean.putValue("allocationPercent", allocationPct);
                            clientPageBean.putValue("splitEqualInd", splitEqualInd);
                            clientPageBean.putValue("allocationDollars", allocationAmount);

                            break;
                        }
                    }
                }

                //  BillLapse
//                loadBillLapse(clientPageBean, contractClientVOs[x]);

                if (clientRelationship.equalsIgnoreCase("pay"))
                {
                    PreferenceVO[] preferenceVO = clientDetailVO[0].getPreferenceVO();

                    if (preferenceVO != null)
                    {
                        for (int p = 0; p < preferenceVO.length; p++)
                        {
                            long preferencePK = preferenceVO[p].getPreferencePK();
                            if (preferencePK == preferenceFK)
                            {
                                clientPageBean.putValue("disbursementSource", preferenceVO[p].getDisbursementSourceCT());
                                clientPageBean.putValue("printAs", preferenceVO[p].getPrintAs());
                                clientPageBean.putValue("printAs2", preferenceVO[p].getPrintAs2());
                                break;
                            }
                        }
                    }
                }

                // ECK
                if (clientRelationship.equalsIgnoreCase("own"))
                {
                    appReqBlock.getSessionBean("quoteMainSessionBean").
                            getPageBean("formBean").putValue("ownerId", taxId);
                    appReqBlock.getSessionBean("quoteMainSessionBean").putValue("policyOwnerName", lastName + ", " + firstName);
                    appReqBlock.getSessionBean("quoteMainSessionBean").putValue("ownerTaxId", taxId);
                    LifeVO[] lifeVO = segmentVO.getLifeVO();
                    String paidToDate = "";
                    if ((lifeVO != null) && (lifeVO.length > 0)) {
                        paidToDate = DateTimeUtil.formatYYYYMMDDToMMDDYYYY(lifeVO[0].getPaidToDate());
                    }
                    appReqBlock.getSessionBean("quoteMainSessionBean").putValue("paidToDate", paidToDate);
                }

                if (clientRelationship.equalsIgnoreCase("sow"))
                {
                    appReqBlock.getSessionBean("quoteMainSessionBean").
                            getPageBean("formBean").putValue("jointOwnerId", taxId);
                }

                clientPageBean.putValue("firstName", firstName);
                clientPageBean.putValue("lastName", lastName);
                clientPageBean.putValue("middleName", middleName);
                clientPageBean.putValue("prefix", prefix);
                clientPageBean.putValue("suffix", suffix);
                clientPageBean.putValue("corporateName", corporateName);
                clientPageBean.putValue("dob", dob);
                clientPageBean.putValue("genderId", genderId);
                clientPageBean.putValue("usCitizenIndStatus", citizenshipIndStatus);
                clientPageBean.putValue("optionId", optionCode);
                clientPageBean.putValue("newIssuesEligibilityStatus", newIssuesEligibilityStatus);
                clientPageBean.putValue("newIssuesStartDate", newIssuesStartDate);
                clientPageBean.putValue("newIssuesStopDate", newIssuesStopDate);

                if (clientRelationship.equalsIgnoreCase("insured"))
                {
                    String selectedClientName = null;
                    if (!lastName.equals(""))
                    {
                        selectedClientName = lastName + ", " + firstName;
                        if (!middleName.equals(""))
                        {
                            selectedClientName = selectedClientName + ", " + middleName;
                        }
                    }

                    appReqBlock.getSessionBean("quoteMainSessionBean").
                            putValue("selectedClientName", selectedClientName);

                    appReqBlock.getSessionBean("quoteMainSessionBean").
                            putValue("insuredRelationToEmp", relationToEmp);
                }
            }
        }
    }

//    /**
//     * Loads BillLapse information to the beans for display on the screen
//     * @param clientPageBean
//     * @param contractClientVO
//     */
//    private void loadBillLapse(PageBean clientPageBean, ContractClientVO contractClientVO)
//    {
//        BillLapseVO[] billLapseVOs = contractClientVO.getBillLapseVO();
//
//        if (contractClientVO.getBillLapseVOCount() > 0)
//        {
//            for (int i = 0; i < billLapseVOs.length; i++)
//            {
//                String billLapsePK      = billLapseVOs[i].getBillLapsePK() + "";
//                String billingFK        = billLapseVOs[i].getBillingFK() + "";
//                String insuranceAmount  = billLapseVOs[i].getInsuranceAmount() + "";
//                String investmentAmount = billLapseVOs[i].getInvestmentAmount() + "";
//                String extractDate      = Util.initString(billLapseVOs[i].getExtractDate(), "");
//                String dueDate          = Util.initString(billLapseVOs[i].getDueDate(), "");
//                String fixedAmount      = billLapseVOs[i].getFixedAmount() + "";
//
//                clientPageBean.addToValues("billLapsePKs", billLapsePK);
//                clientPageBean.addToValues("billingFKs", billingFK);
//                clientPageBean.addToValues("insuranceAmounts", insuranceAmount);
//                clientPageBean.addToValues("investmentAmounts", investmentAmount);
//                clientPageBean.addToValues("extractDates", extractDate);
//                clientPageBean.addToValues("dueDates", dueDate);
//                clientPageBean.addToValues("fixedAmounts", fixedAmount);
//
//                String[] dueDates = clientPageBean.getValues("dueDates");
//                if( dueDates != null && dueDates.length > 0 ){
////                   clientPageBean.putValue("dueDateMonth",(dueDates[0]).substring(5,7));
////                   clientPageBean.putValue("dueDateYear",(dueDates[0]).substring(0,4));
////                   clientPageBean.putValue("dueDateDate",(dueDates[0]).substring(8));
//                    clientPageBean.putValue("dueDate",dueDates[0]);
//                }
//
//                String[] extractDates = clientPageBean.getValues("extractDates");
//                if( extractDates != null && extractDates.length > 0 ){
//                   clientPageBean.putValue("extractDate",extractDates[0]);
//                }
//                String[] paidToDates  = clientPageBean.getValues("paidToDates");
//                if(paidToDates != null && paidToDates.length > 0 ){
//                   clientPageBean.putValue("paidToDate",paidToDates[0]);
//                }
//                String[] lapsePendingDates  = clientPageBean.getValues("lapsePendingDates");
//                if(lapsePendingDates != null && lapsePendingDates.length > 0 ){
//                   clientPageBean.putValue("lapsePendingDate",lapsePendingDates[0]);
//                }
//
//                String[] lapseDates  = clientPageBean.getValues("lapseDates");
//                if(lapseDates != null && lapseDates.length > 0){
//                   clientPageBean.putValue("lapseDate",lapseDates[0]);
//                }
//
//                String[] fixedAmounts = clientPageBean.getValues("fixedAmounts");
//                if(fixedAmounts != null && fixedAmounts.length > 0){
//                   clientPageBean.putValue("fixedAmount",fixedAmounts[0]);
//                }
//
//                String[] insuranceAmounts = clientPageBean.getValues("insuranceAmounts");
//                if(insuranceAmounts != null && insuranceAmounts.length > 0){
//                   clientPageBean.putValue("insuranceAmount",insuranceAmounts[0]);
//                }
//
//                String[] investmentAmounts = clientPageBean.getValues("investmentAmounts");
//                if(investmentAmounts != null && investmentAmounts.length > 0){
//                   clientPageBean.putValue("investmentAmount",investmentAmounts[0]);
//                }
//
//
//                Billing billingObj = new Billing(billLapseVOs[i].getBillingFK());
//                (billLapseVOs[i]).setParentVO(BillingVO.class,billingObj.getVO());
//
//                BillingVO billingVO = (BillingVO)billingObj.getVO();
//
//                clientPageBean.putValue("billingPK",    String.valueOf(billingVO.getBillingPK()));
//                clientPageBean.putValue("segmentFK", String.valueOf(billingVO.getSegmentFK()));
//                clientPageBean.putValue("billDay", String.valueOf(billingVO.getBillDay()));
//                clientPageBean.putValue("lastDayOfMonth", String.valueOf(billingVO.getLastDayOfMonth()));
//                clientPageBean.putValue("billingMode", billingVO.getModeCT());
//                clientPageBean.putValue("billingMethod", String.valueOf(billingVO.getMethodCT()));
//
//
//            }
//        }
//    }

    private void loadRiders(AppReqBlock appReqBlock, SegmentVO segmentVO) throws Exception
    {
        SessionBean riders = appReqBlock.getSessionBean("quoteRiders");

        SegmentVO[] riderVOs = segmentVO.getSegmentVO();
        
        if (riderVOs != null)
        {
            for (int i = 0; i < riderVOs.length; i++)
            {
                PremiumDueVO[] riderPremiumDueVOs = riderVOs[i].getPremiumDueVO();
                appReqBlock.getHttpSession().setAttribute("riderPremiumDueVOs_" + riderVOs[i].getOptionCodeCT(), riderPremiumDueVOs);

                //  Get info from the riderVO
                String segmentNameCT = riderVOs[i].getSegmentNameCT();
                String optionCodeCT = riderVOs[i].getOptionCodeCT();
                String ratedGenderCT = riderVOs[i].getRatedGenderCT();
                String underwritingClass = riderVOs[i].getUnderwritingClassCT();
                String groupPlan = riderVOs[i].getGroupPlan();
                String effectiveDate = DateTimeUtil.formatYYYYMMDDToMMDDYYYY(riderVOs[i].getEffectiveDate());
                String terminationDate = DateTimeUtil.formatYYYYMMDDToMMDDYYYY(riderVOs[i].getTerminationDate());

                if (terminationDate == null)
                {
                    terminationDate = EDITDate.DEFAULT_MAX_DATE;
                }

                String riderSegmentPK = riderVOs[i].getSegmentPK() + "";
                String riderStatus = riderVOs[i].getSegmentStatusCT();
                int sequenceNumber = riderVOs[i].getRiderNumber();
                String units = Util.initBigDecimal(riderVOs[i], "Units", new BigDecimal("0")).toString();
                String commissionPhaseID  = riderVOs[i].getCommissionPhaseID() + "";
                String commissionPhaseOverride = Util.initString(riderVOs[i].getCommissionPhaseOverride(), "");

                //  Change into expected "format"
                String segmentNamePK = CodeTableWrapper.getSingleton().getCodeTablePKByCodeTableNameAndCode("SEGMENTNAME", segmentNameCT) + "";
                String optionCodePK = CodeTableWrapper.getSingleton().getCodeTablePKByCodeTableNameAndCode("RIDERNAME", optionCodeCT) + "";
                
                String ageAtIssue = riderVOs[i].getAgeAtIssue() + "";
                String originalStateCT = riderVOs[i].getOriginalStateCT();
                String location = riderVOs[i].getLocation();
                String sequence = riderVOs[i].getSequence();
                String indivAnnPremium = riderVOs[i].getIndivAnnPremium().toString();
                String amount = riderVOs[i].getAmount().toString();
                                
                //Get rider child of contractclient to get the clientdetail and the insured attached to the rider
                String insuredName = null;
                ContractClientVO[] contractClientVOs = riderVOs[i].getContractClientVO();
                if (contractClientVOs != null && contractClientVOs.length > 0)
                {
                    ClientDetail clientDetail = ClientDetail.findBy_SegmentPK_ClientRoleFK(new Long(riderSegmentPK), new Long(contractClientVOs[0].getClientRoleFK()));
                    if (clientDetail != null)
                    {
                        if (clientDetail.getLastName() !=  null)
                        {
                            insuredName = clientDetail.getLastName() + ", " + clientDetail.getFirstName();
                            if (clientDetail.getMiddleName() != null)
                            {
                                insuredName = insuredName + ", " + clientDetail.getMiddleName();
                            }
                        }
                    }
                }


                //  Place into page bean
                PageBean riderPageBean = new PageBean();
                riderPageBean.putValue("segmentNamePK", segmentNamePK);
                riderPageBean.putValue("coverage", optionCodeCT);
                riderPageBean.putValue("optionCodePK", optionCodePK);
                riderPageBean.putValue("effectiveDate", effectiveDate);
                riderPageBean.putValue("terminationDate", terminationDate);
                riderPageBean.putValue("originalStateCT", originalStateCT);
                riderPageBean.putValue("location", location);
                riderPageBean.putValue("sequence", sequence);
                riderPageBean.putValue("indivAnnPremium", indivAnnPremium);
                riderPageBean.putValue("ratedGenderCT", ratedGenderCT);
                riderPageBean.putValue("underwritingClass", underwritingClass);
                riderPageBean.putValue("groupPlan", groupPlan);
                riderPageBean.putValue("maturityDate", terminationDate);
                riderPageBean.putValue("endDate", terminationDate);
                riderPageBean.putValue("riderSegmentPK", riderSegmentPK);
                riderPageBean.putValue("riderStatus", riderStatus);
                riderPageBean.putValue("riderNumber", sequenceNumber + "");
                riderPageBean.putValue("units", units);
                riderPageBean.putValue("commissionPhaseID", commissionPhaseID);
                riderPageBean.putValue("commissionPhaseOverride", commissionPhaseOverride);
                riderPageBean.putValue("ageAtIssue", ageAtIssue);
                riderPageBean.putValue("originalStateCT", originalStateCT);
                riderPageBean.putValue("amount", amount);
                riderPageBean.putValue("multiple", (riderVOs[i].getEOBMultiple() + ""));
                riderPageBean.putValue("gioOption", Util.initString(riderVOs[i].getGIOOption(), ""));
                riderPageBean.putValue("insuredName", Util.initString(insuredName, ""));

                LifeVO riderLifeVO = null;
                String faceAmount = "";
                String startNew7PayIndicator = "";
                String startNew7PayIndicatorStatus = "unchecked";
                String lifePK = "";

                if (riderVOs[i].getLifeVOCount() > 0)
                {
                    riderLifeVO = riderVOs[i].getLifeVO(0);
                    lifePK = riderLifeVO.getLifePK() + "";
                    faceAmount = riderLifeVO.getFaceAmount().toString();
                    startNew7PayIndicator = Util.initString(riderLifeVO.getStartNew7PayIndicator(), "");

                    if (startNew7PayIndicator.equals("Y"))
                    {
                        startNew7PayIndicatorStatus = "checked";
                    }
                }

                riderPageBean.putValue("faceAmount", faceAmount);
                riderPageBean.putValue("startNew7PayIndicatorStatus", startNew7PayIndicatorStatus);
                riderPageBean.putValue("lifePK", lifePK);

//                if (! riders.pageBeanExists(optionCodeCT))
//                {
//                    riders.putPageBean(optionCodeCT, riderPageBean);
//                }

                //put into session all other rider fields to preserve data
                riderPageBean.putValue("contractNumber", riderVOs[i].getContractNumber());
                riderPageBean.putValue("exchangeInd", riderVOs[i].getExchangeInd());
                riderPageBean.putValue("issueState", riderVOs[i].getIssueStateCT());
                riderPageBean.putValue("quoteDate", DateTimeUtil.formatYYYYMMDDToMMDDYYYY(riderVOs[i].getQuoteDate()));
                riderPageBean.putValue("creationDate", DateTimeUtil.formatYYYYMMDDToMMDDYYYY(riderVOs[i].getCreationDate()));
                riderPageBean.putValue("creationOperator", riderVOs[i].getCreationOperator());
                riderPageBean.putValue("applicationSignedDate", DateTimeUtil.formatYYYYMMDDToMMDDYYYY(riderVOs[i].getApplicationSignedDate()));
                riderPageBean.putValue("waiveFreeLookInd", riderVOs[i].getWaiveFreeLookIndicator());
                riderPageBean.putValue("annualPremium", riderVOs[i].getAnnualPremium().toString());

                riders.putPageBean(sequenceNumber + "" + "_" + optionCodeCT, riderPageBean);
            }

            SessionBean quoteMainSessionBean = appReqBlock.getSessionBean("quoteMainSessionBean");
            quoteMainSessionBean.putValue("totalRiders", riderVOs.length + "");
            appReqBlock.getHttpSession().setAttribute("quoteMainSessionBean", quoteMainSessionBean);

        }
    }


    protected String getQuote(AppReqBlock appReqBlock) throws Exception
    {
        // Check for authorization
        new NewBusinessUseCaseComponent().performNewBusinessQuote();

        SessionBean stateBean = appReqBlock.getSessionBean("quoteStateBean");
        String currentPage = stateBean.getValue("currentPage");

        savePreviousPageFormBean(appReqBlock, currentPage);

        String returnPage = performPageEditing(appReqBlock);

        if (!returnPage.equals(""))
        {
            return returnPage;
        }
        else
        {
            appReqBlock.getSessionBean("quoteMainSessionBean").putValue("quoteMessage", "");

            String ignoreEditWarnings = appReqBlock.getReqParm("ignoreEditWarnings");
            ignoreEditWarnings = (ignoreEditWarnings == null) ? "" : ignoreEditWarnings;

            QuoteVO quoteVoOut = buildQuoteVO(appReqBlock);

            if (!ignoreEditWarnings.equalsIgnoreCase("true"))
            {
                // Identify the state change (error structure)
//                ErrorStructureVO quoteErrorStructure = new ErrorStructureVO();
//                quoteErrorStructure.setProcessName("Quote");
//                quoteErrorStructure.setErrorName("Quote");
//
//                validateQuote(quoteVoOut, quoteErrorStructure, appReqBlock);
            }

            Event eventComponent = new EventComponent();
            QuoteVO quoteVoIn = eventComponent.performNewBusinessQuote(quoteVoOut);

            PageBean formBean = appReqBlock.getSessionBean("quoteMainSessionBean").getPageBean("formBean");

            SegmentVO[] segmentVO = quoteVoIn.getSegmentVO();
            PayoutVO payoutVO = segmentVO[0].getPayoutVO(0);

            // If optionCode is 'Amount Certain' - store these for when building the QuoteVo
            if (segmentVO[0].getOptionCodeCT().equalsIgnoreCase("AMC"))
            {
                PageBean quoteMainSessionBean = appReqBlock.getSessionBean("quoteMainSessionBean");

                String certainPeriodEndDate = DateTimeUtil.formatYYYYMMDDToMMDDYYYY(payoutVO.getCertainPeriodEndDate());
                String finalPaymentDate = DateTimeUtil.formatYYYYMMDDToMMDDYYYY(payoutVO.getFinalPaymentDate());
                String terminationDate = certainPeriodEndDate;

                quoteMainSessionBean.putValue("certainPeriodEndDate", certainPeriodEndDate);
                quoteMainSessionBean.putValue("finalPaymentDate", finalPaymentDate);
                quoteMainSessionBean.putValue("terminationDate", terminationDate);
            }

            formBean.putValue("certainDuration", payoutVO.getCertainDuration() + "");
            formBean.putValue("finalDistributionAmount", payoutVO.getFinalDistributionAmount() + "");
            formBean.putValue("premiumTaxes", segmentVO[0].getCharges() + "");
            formBean.putValue("frontEndLoads", segmentVO[0].getLoads() + "");
            formBean.putValue("totalProjectedAnnuity", Util.formatDecimal("#############0.00",
                                                                           payoutVO.getTotalExpectedReturnAmount()));
            formBean.putValue("exclusionRatio", Util.formatDecimal("########0.000000000", payoutVO.getExclusionRatio()));
            formBean.putValue("yearlyTaxableBenefit", Util.formatDecimal("#############0.00",
                                                                          payoutVO.getYearlyTaxableBenefit()));
            formBean.putValue("fees", segmentVO[0].getFees() + "");
//            formBean.putValue("mrdAmount", payoutVO.getMRDAmount() + "");
            //formBean.putValue("commutedValue", Util.formatDecimal("############0.00", quoteVoIn.getCommutedValue()));
            formBean.putValue("commutedValue", Util.formatDecimal("############0.00", quoteVoIn.getCommutedValue()));
            formBean.putValue("paymentAmount", Util.formatDecimal("#############0.00", payoutVO.getPaymentAmount()));
            formBean.putValue("purchaseAmount", Util.formatDecimal("#############0.00", segmentVO[0].getAmount()));
//            formBean.putValue("faceAmount", Util.formatDecimal("#############0.00", segmentVO[0].getFaceAmount()));
            formBean.putValue("savingsPercent", Util.formatDecimal("0.0##", segmentVO[0].getSavingsPercent()));
            formBean.putValue("calculatedValuesIndStatus", "checked");
            formBean.putValue("dismembermentPercent", segmentVO[0].getDismembermentPercent() + "");

            appReqBlock.getHttpServletRequest().setAttribute("showQuoteResults", "true");
            appReqBlock.getHttpServletRequest().setAttribute("analyzeQuote", "false");

            String productType = checkProductType(segmentVO[0].getOptionCodeCT());

            return getMainReturnPage(productType);
        }
    }

    protected String analyzeQuote(AppReqBlock appReqBlock) throws Exception
    {
        SessionBean stateBean = appReqBlock.getSessionBean("quoteStateBean");
        String currentPage = stateBean.getValue("currentPage");
        String previousPage = stateBean.getValue("previousPage");

        if (currentPage.equals(ANALYZER_DIALOG))
        {
            currentPage = previousPage;
        }

        savePreviousPageFormBean(appReqBlock, currentPage);

        String returnPage = performPageEditing(appReqBlock);

        if (!returnPage.equals(""))
        {
            return returnPage;
        }
        else
        {
            appReqBlock.getSessionBean("quoteMainSessionBean").putValue("quoteMessage", "");

            String ignoreEditWarnings = appReqBlock.getReqParm("ignoreEditWarnings");
            ignoreEditWarnings = (ignoreEditWarnings == null) ? "" : ignoreEditWarnings;

            QuoteVO quoteVoOut = buildQuoteVO(appReqBlock);
            SegmentVO segmentVO = quoteVoOut.getSegmentVO()[0];
            String optionId = segmentVO.getOptionCodeCT();

            Segment segment = new Segment(segmentVO);
//            String eventType = segment.setEventTypeForDriverScript();
   

            Analyzer analyzer = new AnalyzerComponent();
            
            analyzer.resetScriptProcessor();
            

            // Parameter isAnalyzeTrnasaction - when not analyzing transaction the value is false.
//            analyzer.loadScriptAndParameters("QuoteVO", quoteVoOut, "Quote", "*", eventType, segmentVO.getQuoteDate(), segmentVO.getProductStructureFK(), false);

            analyzer.loadScriptAndParameters(segment.getSegmentPK());
            
            appReqBlock.getHttpSession().setAttribute("analyzerComponent", analyzer);

            SessionBean paramBean = appReqBlock.getSessionBean("paramBean");
            paramBean.clearState();

            PageBean debugScriptBean = paramBean.getPageBean("debugScriptBean");
            String[] scriptLines = analyzer.getScriptLines();
            debugScriptBean.putValues("scriptLines", scriptLines, new String[]
                {
                    "toString"
                }, null);

            String scriptName = analyzer.getScriptName();
            debugScriptBean.putValue("scriptName", scriptName);

            String[] stringArray = analyzer.getDataStack();
            debugScriptBean.putValues("dataStackValues", stringArray, new String[]
                {
                    "toString"
                }, null);

            appReqBlock.getHttpSession().setAttribute("workingStorage", analyzer.getWS());
            stringArray = analyzer.getFunctions();
            debugScriptBean.putValues("functionTables", stringArray, new String[]
                {
                    "toString"
                }, null);
            stringArray = analyzer.getFunctionEntry("");
            debugScriptBean.putValues("functionEntries", stringArray, new String[]
                {
                    "toString"
                }, null);
            stringArray = debugScriptBean.getValues("breakPoints");
            debugScriptBean.putValues("breakPoints", stringArray, new String[]
                {
                    "toString"
                }, null);
            debugScriptBean.putValue("instPtr", analyzer.getInstPtr());
            debugScriptBean.putValue("lastInstPtr", analyzer.getLastInstPtr());
            debugScriptBean.putValue("currentRow", analyzer.getCurrentRow());
            appReqBlock.getHttpServletRequest().setAttribute("pageBean", debugScriptBean);
            appReqBlock.getHttpServletRequest().setAttribute("showQuoteResults", "false");
            appReqBlock.getHttpServletRequest().setAttribute("analyzeQuote", "true");

            String productType = checkProductType(optionId);

            return getMainReturnPage(productType);
        }
    }

    protected String showQuoteMainDefault(AppReqBlock appReqBlock) throws Exception
    {
        return QUOTE_COMMIT_MAIN;
    }

    protected String addNewQuote(AppReqBlock appReqBlock) throws Exception
    {
        clearAllQuoteSessions(appReqBlock);

        String coverage = appReqBlock.getFormBean().getValue("optionId");
        String ratedGenderCT = Util.initString(appReqBlock.getFormBean().getValue("ratedGenderCT"), null);
        String originalStateCT = Util.initString(appReqBlock.getFormBean().getValue("originalStateCT"), null);
        String location = Util.initString(appReqBlock.getFormBean().getValue("location"), null);
        String sequence = Util.initString(appReqBlock.getFormBean().getValue("sequence"), null);
        String indivAnnPremium = Util.initString(appReqBlock.getFormBean().getValue("indivAnnPremium"), null);
        String underwritingClass = Util.initString(appReqBlock.getFormBean().getValue("underwritingClass"), null);
        String groupPlan = Util.initString(appReqBlock.getFormBean().getValue("groupPlan"), null);
        String productStructure = appReqBlock.getFormBean().getValue("companyStructure");
        String productStructureId = Util.initString(appReqBlock.getFormBean().getValue("companyStructureId"), null);
        String contractNumber = appReqBlock.getFormBean().getValue("contractNumber");

        // set the found contract's product structure in the session as the current one
        if (productStructureId != null)
        {

            ProductStructure currentProductStructure =
                    appReqBlock.getUserSession().getCurrentProductStructure();

            // set the new one
            ProductStructure.setSecurityCurrentProdStructInSession(
                            appReqBlock,
                            Long.parseLong(productStructureId));

            try
            {
                // check for security now that product has been chosen
                new NewBusinessUseCaseComponent().addNewBusinessContract();
            }
            catch(Exception ex)
            {
                // security exception - reset the product structure back to what it was
                // since they were able to get into add with it.
                appReqBlock.getUserSession().setCurrentProductStructure(currentProductStructure);
                throw ex;
            }

        }

        PageBean quoteMainFormBean = appReqBlock.getSessionBean("quoteMainSessionBean").getPageBean("formBean");
        String segmentPKAsStr = quoteMainFormBean.getValue("segmentPK");
        long segmentPK = (Util.isANumber(segmentPKAsStr)) ? Long.parseLong(segmentPKAsStr) : 0;

        String message = null;
        contract.business.Lookup contractLookup = new contract.component.LookupComponent();
        SegmentVO[] existingContract = contractLookup.getSegmentByContractNumber(contractNumber, false, new ArrayList());

        if (existingContract != null && segmentPK == 0)
        {
            message = "Contract number already exists.";
            appReqBlock.getHttpServletRequest().setAttribute("contractNumberMessage", message);
        }

        if (message == null)
        {
            UserSession userSession = appReqBlock.getUserSession();
            userSession.lockSegment(segmentPK);
            userSession.setContractNumber(contractNumber);

            quoteMainFormBean.putValue("optionId", coverage);
            quoteMainFormBean.putValue("companyStructure", productStructure);
            quoteMainFormBean.putValue("companyStructureId", productStructureId);
            quoteMainFormBean.putValue("contractNumber", contractNumber);
            quoteMainFormBean.putValue("originalStateCT", originalStateCT);
            quoteMainFormBean.putValue("location", location);
            quoteMainFormBean.putValue("sequence", sequence);
            quoteMainFormBean.putValue("indivAnnPremium", indivAnnPremium);
            quoteMainFormBean.putValue("ratedGenderCT", ratedGenderCT);
            quoteMainFormBean.putValue("underwritingClass", underwritingClass);
            quoteMainFormBean.putValue("groupPlan", groupPlan);
            
            SessionBean quoteMainSessionBean = appReqBlock.getSessionBean("quoteMainSessionBean");
            quoteMainSessionBean.putValue("option", coverage);
            quoteMainSessionBean.putValue("originalStateCT", originalStateCT);
            quoteMainSessionBean.putValue("location", location);
            quoteMainSessionBean.putValue("sequence", sequence);
            quoteMainSessionBean.putValue("indivAnnPremium", indivAnnPremium);
            quoteMainSessionBean.putValue("ratedGenderCT", ratedGenderCT);
            quoteMainSessionBean.putValue("underwritingClass", underwritingClass);
            quoteMainSessionBean.putValue("groupPlan", groupPlan);
            quoteMainSessionBean.putValue("contractId", contractNumber);
            quoteMainSessionBean.putValue("companyStructure", productStructure);
            // the initial state of the segment 'Pending'
            quoteMainSessionBean.putValue("status", "Pending");
        }

        
        if (coverage.equalsIgnoreCase("Traditional"))
        {
            return QUOTE_TRAD_MAIN;
        }
        else if (coverage.equalsIgnoreCase("UL"))
        {
            return QUOTE_UNIVERSAL_LIFE_MAIN;
        }
        else if (Segment.OPTIONCODES_AH.contains(coverage.toUpperCase())) 
        {
        	return QUOTE_AH_MAIN;
        }
        else if (coverage.equalsIgnoreCase("DFA") || coverage.equalsIgnoreCase("Waiver"))
        {
            return QUOTE_DEFERRED_ANNUITY_MAIN;
        }
        else if (coverage.equalsIgnoreCase("Life"))
        {
            return QUOTE_LIFE_MAIN;
        }
        else
        {
            return QUOTE_COMMIT_MAIN;
        }
    }

    protected String updateClientInfo(AppReqBlock appReqBlock) throws Exception
    {
        SessionBean stateBean = appReqBlock.getSessionBean("quoteStateBean");
        String currentPage = stateBean.getValue("currentPage");

        savePreviousPageFormBean(appReqBlock, currentPage);

        PageBean formBean = appReqBlock.getSessionBean("quoteMainSessionBean").getPageBean("clientFormBean");
        SessionBean clients = appReqBlock.getSessionBean("quoteClients");

        String taxId = formBean.getValue("taxId");
        String employeeIdentification = formBean.getValue("employeeIdentification");
        String optionId = formBean.getValue("optionId");
        String relationship = formBean.getValue("relationshipInd");
        String clientRoleFK = formBean.getValue("clientRoleFK");
        String contractClientPK = formBean.getValue("contractClientPK");
        String riderNumber = Util.initString(formBean.getValue("riderNumber"), "0");

        String oldOptionId = formBean.getValue("oldOptionId");
        String oldRelationship = formBean.getValue("oldRelationship");

        String lastName = formBean.getValue("lastName");
        String firstName = formBean.getValue("firstName");
        String middleName = formBean.getValue("middleName");
        String corporateName = formBean.getValue("corporateName");
        String dob = formBean.getValue("dob");
        String genderId = formBean.getValue("genderId");
        String issueAge = formBean.getValue("issueAge");
        String usCitizenIndStatus = formBean.getValue("usCitizenIndStatus");
        String relationToInsured = formBean.getValue("relationToIns");
        String relationToEmp = formBean.getValue("relationToEmp");
        String phoneAuth = formBean.getValue("phoneAuth");
        String classType = formBean.getValue("classType");
        String disbAddressType = formBean.getValue("disbAddressType");
        String corrAddressType = formBean.getValue("corrAddressType");
        String flatExtra = formBean.getValue("flatExtra");
        String flatExtraAge = formBean.getValue("flatExtraAge");
        String flatExtraDur = formBean.getValue("flatExtraDur");
        String percentExtra = formBean.getValue("percentExtra");
        String percentExtraAge = formBean.getValue("percentExtraAge");
        String percentExtraDur = formBean.getValue("percentExtraDur");
        String tableRating = formBean.getValue("tableRating");
        String payorOf = formBean.getValue("payorOf");
        String effectiveDate = formBean.getValue("effectiveDate");
        String ratedGender = formBean.getValue("ratedGender");
        String underwritingClass = formBean.getValue("underwritingClass");
        String overrideStatus = formBean.getValue("overrideStatus");
        String newIssuesEligibilityStatus = formBean.getValue("newIssuesEligibilityStatus");
        String newIssuesStartDate = formBean.getValue("newIssuesStartDate");
        String newIssuesStopDate = formBean.getValue("newIssuesStopDate");
        String originalClassCT = formBean.getValue("originalClassCT");

        String oldClientKey = this.convertEmptyToNull(taxId) + riderNumber + oldOptionId + oldRelationship + contractClientPK + clientRoleFK;

        String clientIdKey = this.convertEmptyToNull(taxId) + riderNumber + optionId + relationship + contractClientPK + clientRoleFK;

        PageBean clientPageBean = formBean;

        clientPageBean.putValue("taxId", taxId);
        clientPageBean.putValue("employeeIdentification", employeeIdentification);
        clientPageBean.putValue("optionId", optionId);
        clientPageBean.putValue("lastName", lastName);
        clientPageBean.putValue("firstName", firstName);
        clientPageBean.putValue("middleName", middleName);
        clientPageBean.putValue("corporateName", corporateName);
        clientPageBean.putValue("dob", dob);
        clientPageBean.putValue("genderId", genderId);
        clientPageBean.putValue("issueAge", issueAge);
        clientPageBean.putValue("usCitizenIndStatus", usCitizenIndStatus);
        clientPageBean.putValue("issueAge", "0");
        clientPageBean.putValue("relationToIns", relationToInsured);
        clientPageBean.putValue("relationToEmp", relationToEmp);
        clientPageBean.putValue("phoneAuth", phoneAuth);
        clientPageBean.putValue("classType", classType);
        clientPageBean.putValue("flatExtra", flatExtra);
        clientPageBean.putValue("flatExtraAge", flatExtraAge);
        clientPageBean.putValue("flatExtraDur", flatExtraDur);
        clientPageBean.putValue("percentExtra", percentExtra);
        clientPageBean.putValue("percentExtraAge", percentExtraAge);
        clientPageBean.putValue("percentExtraDur", percentExtraDur);
        clientPageBean.putValue("tableRating", tableRating);
        clientPageBean.putValue("disbAddressType", disbAddressType);
        clientPageBean.putValue("corrAddressType", corrAddressType);
        clientPageBean.putValue("effectiveDate", effectiveDate);
        clientPageBean.putValue("payorOf", payorOf);
        clientPageBean.putValue("ratedGender", ratedGender);
        clientPageBean.putValue("underwritingClass", underwritingClass);
        clientPageBean.putValue("overrideStatus", overrideStatus);
        clientPageBean.putValue("contractClientPK", contractClientPK);
        clientPageBean.putValue("newIssuesEligibilityStatus", newIssuesEligibilityStatus);
        clientPageBean.putValue("newIssuesStartDate", newIssuesStartDate);
        clientPageBean.putValue("newIssuesStopDate", newIssuesStopDate);
        clientPageBean.putValue("beneRelationshipToIns", formBean.getValue("beneRelationshipToIns"));
        clientPageBean.putValue("riderNumber", riderNumber);
        clientPageBean.putValue("originalClassCT", originalClassCT);

        if (relationship.equalsIgnoreCase("PAY") || relationship.equalsIgnoreCase("PBE") ||
            relationship.equalsIgnoreCase("CBE") || relationship.equalsIgnoreCase("POR"))
        {
            String ccAllocPK = clientPageBean.getValue("contractClientAllocationPK");
            String allocationPercent = Util.initString(formBean.getValue("allocationPercent"), "0");
            String splitEqualInd = Util.initString(formBean.getValue("splitEqualInd"), "N");
            clientPageBean.putValue("allocationPercent", allocationPercent);
            clientPageBean.putValue("splitEqualInd", splitEqualInd);
            clientPageBean.putValue("contractClientAllocationPK", ccAllocPK);
            if (!Util.isANumber(allocationPercent))
            {
                appReqBlock.getHttpServletRequest().setAttribute("clientMessage", "Allocation Percent is Invalid - Please Re-enter.");
            }
        }

        if (relationship.equalsIgnoreCase("PAY"))
        {
            String disbursementSource = formBean.getValue("disbursementSource");
            String printAs = formBean.getValue("printAs");
            String printAs2 = formBean.getValue("printAs2");

            clientPageBean.putValue("effectiveDate", formBean.getValue("effectiveDate"));
            clientPageBean.putValue("terminationDay", formBean.getValue("terminateDate"));
            clientPageBean.putValue("disbursementSource", disbursementSource);
            clientPageBean.putValue("printAs", printAs);
            clientPageBean.putValue("printAs2", printAs2);
        }

        if (relationship.equalsIgnoreCase("TermInsured"))
        {
            PageBean baseFormBean = appReqBlock.getSessionBean("quoteMainSessionBean").getPageBean("formBean");

            String batchContractSetupFK = Util.initString(baseFormBean.getValue("batchContractSetupFK"), "0");
            getCaseUnderwritingForClient(appReqBlock, batchContractSetupFK);
        }

        if ((String) appReqBlock.getHttpServletRequest().getAttribute("clientMessage") == null)
        {
            clients.removePageBean(oldClientKey);
            clients.removePageBean(clientIdKey);
            clients.putPageBean(clientIdKey, clientPageBean);
        }

        appReqBlock.getSessionBean("quoteMainSessionBean").putPageBean("clientFormBean", clientPageBean);

        return appReqBlock.getSessionBean("quoteStateBean").getValue("currentPage");
    }

//    protected void loadBillingLapseDateToBean(PageBean clientPageBean,PageBean formBean,
//                                              String Month,String Date,String Year, String arrayName) {
//
//        String month = formBean.getValue(Month);
//        String date  = formBean.getValue(Date);
//        String year  = formBean.getValue(Year);
//
//        String[] dates = clientPageBean.getValues(arrayName);
//        if(dates != null )
//        {
////            dates[0] = year+month+date;
//            clientPageBean.putValue(Month,month);
//            clientPageBean.putValue(Year,year);
//            clientPageBean.putValue(Date,date);
//
//            clientPageBean.removeValue(arrayName);
//
//            if( year != null && !year.trim().equals("") &&
//                month != null && !month.trim().equals("") &&
//                date != null && !date.trim().equals(""))
//            {
//                EDITDate editDate = new EDITDate(year, month, date);
//                clientPageBean.addToValues(arrayName, editDate.getFormattedDate());
//                clientPageBean.putValue(arrayName.substring(0,arrayName.length()-1), editDate.getFormattedDate());
//            }
//            else
//            {
//                clientPageBean.addToValues(arrayName,"");
//                clientPageBean.putValue(arrayName.substring(0,arrayName.length()-1),"");
//            }
//         }
//
//    }

    protected String saveAgentToSummary(AppReqBlock appReqBlock) throws Exception
    {
        String splitPercent = appReqBlock.getFormBean().getValue("splitPercent");
        String stopDate = DateTimeUtil.formatMMDDYYYYToYYYYMMDD(appReqBlock.getFormBean().getValue("stopDate"));
        
        String selectedAgentHierarchyPK = appReqBlock.getFormBean().getValue("selectedAgentHierarchyPK");

        UIAgentHierarchyVO[] uiAgentHierarchyVOs = (UIAgentHierarchyVO[]) appReqBlock.getHttpSession().getAttribute("uiAgentHierarchyVOs");

        for (int i = 0; i < uiAgentHierarchyVOs.length; i++)
        {
            AgentHierarchyVO agentHierarchyVO = uiAgentHierarchyVOs[i].getAgentHierarchyVO();

            if ((agentHierarchyVO.getAgentHierarchyPK() + "").equals(selectedAgentHierarchyPK))
            {
                AgentHierarchyAllocationVO agentHierarchyAllocationVO = uiAgentHierarchyVOs[i].getAgentHierarchyAllocationVO();

                agentHierarchyAllocationVO.setAllocationPercent(new EDITBigDecimal(splitPercent).getBigDecimal());
                agentHierarchyAllocationVO.setStopDate(stopDate);

                break;
            }
        }

        return QUOTE_COMMIT_AGENT;
    }

    protected String saveFundToSummary(AppReqBlock appReqBlock) throws Exception
    {
        String investmentMessage = "";

        CodeTableWrapper codeTableWrapper = CodeTableWrapper.getSingleton();

        PageBean formBean = appReqBlock.getFormBean();
        SessionBean funds = appReqBlock.getSessionBean("quoteFunds");

        String optionId = formBean.getValue("optionId");

        if (!optionId.equalsIgnoreCase("Please Select"))
        {
            if (Util.isANumber(optionId))
            {
                CodeTableVO codeTableVO = codeTableWrapper.getCodeTableEntry(Long.parseLong(optionId));
                optionId = codeTableVO.getCode();
            }
        }

        formBean.putValue("optionId", optionId);

        String fundId = formBean.getValue("fundId");
        formBean.putValue("filteredFundFK", fundId);
        String air = formBean.getValue("air");
        String allocationPercent = formBean.getValue("allocationPercent");
        PageBean fundPageBean = null;

        if (!Util.isANumber(allocationPercent))
        {
            investmentMessage = "Invalid Allocation Percent - Please Re-enter.";
        }
        else if(new EDITBigDecimal(allocationPercent).isLT(EDITBigDecimal.EDIT_DECIMAL_ZILCH_STR))
        {
        	investmentMessage = "Negative Allocation Percent - Please Re-enter.";
        }
        else
        {
            String key = optionId + fundId;

            if (!funds.pageBeanExists(key))
            {
                engine.business.Lookup engineLookup = new engine.component.LookupComponent();

                FundVO[] fundVO = engineLookup.getFundByFilteredFundFK(Long.parseLong(fundId), false, null);

                String fundName = fundVO[0].getName();
                String fundType = fundVO[0].getFundType();

                if (!fundType.equalsIgnoreCase("LOAN"))
                {
                    fundPageBean = new PageBean();

                    fundPageBean.putValue("investmentPK", "0");
                    fundPageBean.putValue("optionId", optionId);
                    fundPageBean.putValue("filteredFundFK", fundId);
                    fundPageBean.putValue("fundName", fundName);
                    fundPageBean.putValue("fundType", fundType);
                    fundPageBean.putValue("allocationPercent", allocationPercent);
                    fundPageBean.putValue("air", air);
                    fundPageBean.putValue("investmentAllocationPK", fundPageBean.getValue("investmentAllocationPK"));

                    funds.putPageBean(key, fundPageBean);
                }
                else
                {
                    investmentMessage = "Cannot Select Loan Fund for Contract";
                }
            }
            else
            {
                fundPageBean = funds.getPageBean(key);
                fundPageBean.putValue("allocationPercent", allocationPercent);
                fundPageBean.putValue("air", air);
            }
        }

        PageBean quoteFormBean = appReqBlock.getSessionBean("quoteMainSessionBean").getPageBean("formBean");

        String productStructureId = quoteFormBean.getValue("companyStructureId");

        if (!productStructureId.equals("") && !productStructureId.equalsIgnoreCase("Please Select"))
        {
            UIFilteredFundVO[] uiFilteredFundVOs = new UtilitiesForTran().buildUIFilteredFundVO(appReqBlock, productStructureId);
            appReqBlock.getHttpServletRequest().setAttribute("uiFilteredFundVOs", uiFilteredFundVOs);
        }

        if (fundPageBean != null)
        {
            appReqBlock.getSessionBean("quoteMainSessionBean").putPageBean("investmentFormBean", fundPageBean);
        }
        else
        {
            appReqBlock.getSessionBean("quoteMainSessionBean").putPageBean("investmentFormBean", formBean);
        }

        appReqBlock.getHttpServletRequest().setAttribute("investmentMessage", investmentMessage);

        return QUOTE_COMMIT_INVESTMENTS;
    }

    protected String showQuoteMainContent(AppReqBlock appReqBlock) throws Exception
    {
        SessionBean quoteMainSessionBean = appReqBlock.getSessionBean("quoteMainSessionBean");
        PageBean formBean = quoteMainSessionBean.getPageBean("formBean");
        String optionId = formBean.getValue("optionId");
        String contractNumber = formBean.getValue("contractNumber");

        CodeTableWrapper codeTableWrapper = CodeTableWrapper.getSingleton();

        if (Util.isANumber(optionId))
        {
            optionId = codeTableWrapper.getCodeTableEntry(Long.parseLong(optionId)).getCode();
            formBean.putValue("optionId", optionId);
        }

        SessionBean stateBean = appReqBlock.getSessionBean("quoteStateBean");
        String currentPage = stateBean.getValue("currentPage");
//        String batchContractSetupFK = Util.initString(formBean.getValue("batchContractSetupFK"), "0");
        savePreviousPageFormBean(appReqBlock, currentPage);
        
        appReqBlock.getHttpSession().removeAttribute("currentDepositVO");

        event.business.Event eventComponent = new event.component.EventComponent();
        SuspenseVO[] contractSuspenseVOs = eventComponent.composeSuspenseVOByUserDefNumber(contractNumber, new ArrayList());

        if (contractSuspenseVOs != null)
        {
            appReqBlock.getHttpSession().setAttribute("contractSuspenseVOs", contractSuspenseVOs);
        }

        String productType = checkProductType(optionId);

        return getMainReturnPage(productType);
    }

    protected String showQuoteRiders(AppReqBlock appReqBlock) throws Exception
    {
        SessionBean stateBean = appReqBlock.getSessionBean("quoteStateBean");
        String currentPage = stateBean.getValue("currentPage");

        savePreviousPageFormBean(appReqBlock, currentPage);
        
        
        //Long batchContractSetupFK = (Long) appReqBlock.getHttpSession().getAttribute("batchContactSetup"); 
         
        
        PageBean formBean = appReqBlock.getSessionBean("quoteMainSessionBean").getPageBean("formBean");
        
//        String batchContractSetupFK = Util.initString(formBean.getValue("batchContractSetupFK"), "0");  
        
        formBean.putValue("pageMode", "add");
        appReqBlock.getSessionBean("quoteMainSessionBean").putPageBean("riderFormBean", new PageBean());
        
        if (formBean.getValue("optionId").equalsIgnoreCase(QuoteDetailTran.TRADITIONAL) ||
        	formBean.getValue("optionId").equalsIgnoreCase(QuoteDetailTran.UNIVERSAL_LIFE) ||
            formBean.getValue("optionId").equalsIgnoreCase("Life") ||
            Segment.OPTIONCODES_AH.contains(formBean.getValue("optionId").toUpperCase()))
        {
            return QUOTE_COMMIT_LIFE_RIDER;
        }
        else
        {
            return QUOTE_COMMIT_RIDER;
        }
    }

    protected String showQuoteInvestments(AppReqBlock appReqBlock) throws Exception
    {
        SessionBean stateBean = appReqBlock.getSessionBean("quoteStateBean");
        String currentPage = stateBean.getValue("currentPage");

        savePreviousPageFormBean(appReqBlock, currentPage);

        PageBean formBean = appReqBlock.getSessionBean("quoteMainSessionBean").getPageBean("formBean");

        String productStructureId = formBean.getValue("companyStructureId");

        if (!productStructureId.equals("") && !productStructureId.equalsIgnoreCase("Please Select"))
        {
            UIFilteredFundVO[] uiFilteredFundVOs = new UtilitiesForTran().buildUIFilteredFundVO(appReqBlock, productStructureId);
            appReqBlock.getHttpServletRequest().setAttribute("uiFilteredFundVOs", uiFilteredFundVOs);
        }

        return QUOTE_COMMIT_INVESTMENTS;
    }

    protected String clearInvestmentsForAddOrCancel(AppReqBlock appReqBlock) throws Exception
    {
        PageBean formBean = appReqBlock.getSessionBean("quoteMainSessionBean").getPageBean("formBean");

        String productStructureId = formBean.getValue("companyStructureId");

        if (!productStructureId.equals("") && !productStructureId.equalsIgnoreCase("Please Select"))
        {
            UIFilteredFundVO[] uiFilteredFundVOs = new UtilitiesForTran().buildUIFilteredFundVO(appReqBlock, productStructureId);
            appReqBlock.getHttpServletRequest().setAttribute("uiFilteredFundVOs", uiFilteredFundVOs);
        }

        appReqBlock.getSessionBean("quoteMainSessionBean").putPageBean("investmentFormBean", new PageBean());

        return QUOTE_COMMIT_INVESTMENTS;
    }

    protected String clearNotesForAddOrCancel(AppReqBlock appReqBlock) throws Exception
    {
        appReqBlock.getSessionBean("quoteNotesSessionBean").putPageBean("formBean", new PageBean());

        return QUOTE_COMMIT_NOTES_DIALOG;
    }

    protected String showQuoteNonPayeeOrPayee(AppReqBlock appReqBlock) throws Exception
    {
        SessionBean stateBean = appReqBlock.getSessionBean("quoteStateBean");

        String returnPage = null;
        String currentPage = stateBean.getValue("currentPage");

        // Prepare the correct currentPage
        String relationship = appReqBlock.getSessionBean("quoteMainSessionBean")
                .getPageBean("clientFormBean").getValue("relationshipInd");

        if (relationship.equalsIgnoreCase("pay"))
        {
            returnPage = QUOTE_COMMIT_PAYEE;
        }
        else if (relationship.equalsIgnoreCase("insured"))
        {
            returnPage = QUOTE_COMMIT_INSURED;
        }
        else
        {
            returnPage = QUOTE_COMMIT_NON_PAYEE;
        }

        // Saves formBean in the appropriate SessionBean
        savePreviousPageFormBean(appReqBlock, currentPage);

        return returnPage;
    }

    protected String showQuoteAgents(AppReqBlock appReqBlock) throws Exception
    {
        //Check for Authorization
        new NewBusinessUseCaseComponent().accessAgent();        
        
        SessionBean stateBean = appReqBlock.getSessionBean("quoteStateBean");
        String currentPage = stateBean.getValue("currentPage");

        savePreviousPageFormBean(appReqBlock, currentPage);

        return QUOTE_COMMIT_AGENT;
    }

    protected String showAgentSelectionDialog(AppReqBlock appReqBlock) throws Exception
    {
        appReqBlock.getHttpSession().removeAttribute("selectedAgentVO");
        appReqBlock.getHttpSession().removeAttribute("placedAgentBranchVOs");

        return AGENT_SELECTION_DIALOG;
    }

    protected String closeAgentSelectionDialog(AppReqBlock appReqBlock) throws Exception
    {
        appReqBlock.getHttpSession().removeAttribute("selectedAgentVO");
        appReqBlock.getHttpSession().removeAttribute("placedAgentBranchVOs");

        return QUOTE_COMMIT_AGENT;
    }

    protected String saveAgentSelection(AppReqBlock appReqBlock) throws Exception
    {
        CodeTableWrapper codeTableWrapper = CodeTableWrapper.getSingleton();

        String agentMessage = ""; 
        
        boolean caseAgentFound = false;
        int placedAgentCount = 0;
        int commissionOverrideCount = 0;
        
        String contractGroupNumber = appReqBlock.getSessionBean("quoteMainSessionBean").getValue("contractGroup");
        ContractGroup caseContractGroup = null;
        
        if (contractGroupNumber != null && !contractGroupNumber.equals(""))
        {
        	// Get the group ContractGroup and then case ContractGroup
        	ContractGroup groupContractGroup = ContractGroup.findBy_ContractGroupNumber(contractGroupNumber);
        	
        	Long contractGroupCasePK = groupContractGroup.getContractGroupPK();
        	caseContractGroup = ContractGroup.findBy_ContractGroupFK(contractGroupCasePK);

        }
        else
        {
        	// Get the SegmentVO to enable looking up the associated case hierarchies currently in the system
            String segmentPK = appReqBlock.getSessionBean("quoteMainSessionBean").getValue("segmentPK");
            contract.business.Lookup contractLookup = new contract.component.LookupComponent();
            SegmentVO segmentVO = contractLookup.composeSegmentVO(Long.parseLong(segmentPK), new ArrayList<VOObject>());

            // Get the case ContractGroup
            Long groupContractGroupPK = segmentVO.getContractGroupFK();
            caseContractGroup = ContractGroup.findBy_ContractGroupFK(groupContractGroupPK);  
            
        }
        
        String selectedPlacedAgentPK = appReqBlock.getFormBean().getValue("selectedPlacedAgentPK");
        String coverage = appReqBlock.getFormBean().getValue("coverage");

        // Get the default start date - use effective date if set, otherwise use application signed date
        String startDateString = getDefaultAllocationStartDate(appReqBlock);

        if (Util.isANumber(coverage))
        {
            coverage = codeTableWrapper.getCodeTableEntry(Long.parseLong(coverage)).getCode();
        }

        AgentVO agentVO = (AgentVO) appReqBlock.getHttpSession().getAttribute("selectedAgentVO");
        PlacedAgentBranchVO[] placedAgentBranchVOs = (PlacedAgentBranchVO[]) appReqBlock.getHttpSession().getAttribute("placedAgentBranchVOs");

        contract.business.Contract contractComponent = new contract.component.ContractComponent();

        AgentHierarchyVO agentHierarchyVO = new AgentHierarchyVO();
        agentHierarchyVO.setAgentHierarchyPK(contractComponent.getNextAvailableKey() * -1);
        agentHierarchyVO.setSegmentFK(0);
        agentHierarchyVO.setAgentFK(agentVO.getAgentPK());

        AgentHierarchyAllocationVO agentHierarchyAllocationVO = new AgentHierarchyAllocationVO();

        agentHierarchyAllocationVO.setAgentHierarchyAllocationPK(contractComponent.getNextAvailableKey() * -1);
        agentHierarchyAllocationVO.setAllocationPercent(new EDITBigDecimal("1").getBigDecimal());
        agentHierarchyAllocationVO.setStartDate(startDateString);
        agentHierarchyAllocationVO.setStopDate(EDITDate.DEFAULT_MAX_DATE);
        agentHierarchyAllocationVO.setAgentHierarchyFK(agentHierarchyVO.getAgentHierarchyPK());

        agentHierarchyVO.addAgentHierarchyAllocationVO(agentHierarchyAllocationVO);

        UIAgentHierarchyVO[] existingUIAgentHierarchyVOs =
                (UIAgentHierarchyVO[]) appReqBlock.getHttpSession().getAttribute("uiAgentHierarchyVOs");

        UIAgentHierarchyVO uiAgentHierarchyVO = new UIAgentHierarchyVO();
        uiAgentHierarchyVO.setAgentHierarchyAllocationVO(agentHierarchyAllocationVO);
        uiAgentHierarchyVO.setAgentHierarchyVO(agentHierarchyVO);
        uiAgentHierarchyVO.setCoverage(coverage);
        uiAgentHierarchyVO.setAgentVO(agentVO);

        for (int p = 0; p < placedAgentBranchVOs.length; p++)
        {
            int paCount = placedAgentBranchVOs[p].getPlacedAgentVOCount();
            int paIndex = paCount - 1; // We want the writing agent which is the last PlacedAgent in the branch

            if ((placedAgentBranchVOs[p].getPlacedAgentVO(paIndex).getPlacedAgentPK() + "").equals(selectedPlacedAgentPK))
            {
                PlacedAgentVO[] placedAgentVOs = placedAgentBranchVOs[p].getPlacedAgentVO(); 
                                
                // Get the case agent snapshots corresponding with the lowest-level placed agent
                placedAgentCount = placedAgentVOs.length;
                int lowestPlacedAgentIndex = placedAgentCount - 1; 
                
            	String agentNumber = ClientRole.findBy_ClientRolePK((Long)placedAgentVOs[lowestPlacedAgentIndex].getClientRoleFK()).getReferenceID();
                String situationCode = placedAgentVOs[lowestPlacedAgentIndex].getSituationCode();

                // Get the corresponding agent snapshots that are tied to this contract's case so that we can pull in advance and recovery percents 
                AgentSnapshot[] caseAgentSnapshots = caseContractGroup.getAgentSnaphots_FindByAgentNameSitCode(agentNumber, situationCode);
                
                // If caseAgentSnapshot was not located, we know this agent is not associated with the case
                if (caseAgentSnapshots != null)
                {
                	caseAgentFound = true; 
                	
                	// Build each agentSnapshot and set commission overrides
	                for (int z = 0; z < placedAgentVOs.length; z++)
	                {
	                    AgentSnapshotVO agentSnapshotVO = new AgentSnapshotVO();
	                    agentSnapshotVO.setAgentSnapshotPK(contractComponent.getNextAvailableKey() * -1);
	                    agentSnapshotVO.setAgentHierarchyFK(agentHierarchyVO.getAgentHierarchyPK());
	                    long placedAgentFK = placedAgentVOs[z].getPlacedAgentPK();
	                    agentSnapshotVO.setPlacedAgentFK(placedAgentFK);
	                    agentSnapshotVO.setHierarchyLevel(placedAgentVOs[z].getHierarchyLevel());
	                    agentSnapshotVO.setServicingAgentIndicator("N");
	
	                    agentSnapshotVO.setParentVO(PlacedAgentVO.class, placedAgentVOs[z]);
	
	                    String newPlacedAgentNumber = ClientRole.findBy_ClientRolePK(placedAgentVOs[z].getClientRoleFK()).getReferenceID();
                    	
	                    for (AgentSnapshot caseAgentSnapshot : caseAgentSnapshots)
	                    {
	                    	PlacedAgent tempPlacedAgent = PlacedAgent.findByPK(caseAgentSnapshot.getPlacedAgentFK());
	                    	String caseAgentNumber = tempPlacedAgent.getClientRole().getReferenceID();
	                    	int caseAgentLevel = tempPlacedAgent.getHierarchyLevel();
	                    	
	                    	if (caseAgentNumber.equalsIgnoreCase(newPlacedAgentNumber) && caseAgentLevel==agentSnapshotVO.getHierarchyLevel())
	                    	{                    		
	                    		agentSnapshotVO.setAdvancePercent(caseAgentSnapshot.getAdvancePercent().getBigDecimal());
	                    		agentSnapshotVO.setRecoveryPercent(caseAgentSnapshot.getRecoveryPercent().getBigDecimal());
	                    		agentSnapshotVO.setServicingAgentIndicator(caseAgentSnapshot.getServicingAgentIndicator());
	                    		commissionOverrideCount++;
	                    	}
	                    }
	                    
	                    CommissionProfileVO commissionProfileVO = (CommissionProfileVO) placedAgentVOs[z].getParentVO(CommissionProfileVO.class);
	                    agentSnapshotVO.setParentVO(CommissionProfileVO.class, commissionProfileVO);

	                    agentHierarchyVO.addAgentSnapshotVO(agentSnapshotVO);         
                    }
                }
                else
                {
                	caseAgentFound = false;       	
                }

                // Quit forLoop once matching placedAgent has been found
                break;
            }
        }

        if (caseAgentFound)
        {
	        UIAgentHierarchyVO[] updatedUIAgentHierarchyVOs = null;
	
	        List uiAgentHierarchyVOs = new ArrayList();
	
	        if (existingUIAgentHierarchyVOs != null)
	        {
	            for (int e = 0; e < existingUIAgentHierarchyVOs.length; e++)
	            {
	                uiAgentHierarchyVOs.add(existingUIAgentHierarchyVOs[e]);
	            }
	        }
	
	        uiAgentHierarchyVOs.add(uiAgentHierarchyVO);
	
	        updatedUIAgentHierarchyVOs = (UIAgentHierarchyVO[]) uiAgentHierarchyVOs.toArray(new UIAgentHierarchyVO[uiAgentHierarchyVOs.size()]);
	
	        appReqBlock.getHttpServletRequest().setAttribute("selectedAgentHierarchyPK", agentHierarchyVO.getAgentHierarchyPK() + "");
	        appReqBlock.getHttpSession().setAttribute("uiAgentHierarchyVOs", updatedUIAgentHierarchyVOs);
	        appReqBlock.getHttpSession().removeAttribute("selectedAgentVO");
	        appReqBlock.getHttpSession().removeAttribute("placedAgentBranchVOs");
	        
	        // Verify that commissionOverrides were set for every AgentSnapshot
	        if (commissionOverrideCount != placedAgentCount)
            {
            	agentMessage = "It looks like we may have missed something.  Please verify that commission overrides are correct and set them manually as needed.";
            }
        }
        else
        {
        	agentMessage = "Selected agent was not found in this contract's case. Please select an associated agent.";
        }
        
        if (agentMessage != null && !agentMessage.equals(""))
        {
            appReqBlock.getHttpServletRequest().setAttribute("agentMessage", agentMessage);
        }
        
        return QUOTE_COMMIT_AGENT;
    }

    protected String showReportToAgent(AppReqBlock appReqBlock) throws Exception
    {
        String agentNumber = appReqBlock.getFormBean().getValue("agentId");

        SessionBean quoteMainSessionBean = appReqBlock.getSessionBean("quoteMainSessionBean");

        String appReceivedDate = DateTimeUtil.formatMMDDYYYYToYYYYMMDD(quoteMainSessionBean.getPageBean("formBean").getValue("applicationReceivedDate"));

        agent.business.Agent agentComponent = new AgentComponent();

        PlacedAgentBranchVO[] placedAgentBranchVOs = agentComponent.getBranchesByAgentNumberAndExpirationDate(agentNumber, appReceivedDate);

        if (placedAgentBranchVOs != null && placedAgentBranchVOs.length > 0)
        {
            long agentContractFK = placedAgentBranchVOs[0].getPlacedAgentVO(0).getAgentContractFK();
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

        return AGENT_SELECTION_DIALOG;
    }

    protected String showAgentHierarchyDialog(AppReqBlock appReqBlock) throws Exception
    {
        String selectedAgentHierarchyPK = appReqBlock.getFormBean().getValue("selectedAgentHierarchyPK");
        appReqBlock.getHttpServletRequest().setAttribute("selectedAgentHierarchyPK", selectedAgentHierarchyPK);

        return AGENT_HIERARCHY_DIALOG;
    }

//    protected String showAdvanceDialog(AppReqBlock appReqBlock) throws Exception
//    {
//        String selectedAgentHierarchyPK = appReqBlock.getFormBean().getValue("selectedAgentHierarchyPK");
//        appReqBlock.getHttpServletRequest().setAttribute("selectedAgentHierarchyPK", selectedAgentHierarchyPK);
//
//        return ADVANCE_DIALOG;
//    }

    protected String saveAdvance(AppReqBlock appReqBlock) throws Exception
    {
        String selectedAgentHierarchyPK = appReqBlock.getFormBean().getValue("selectedAgentHierarchyPK");
        appReqBlock.getHttpServletRequest().setAttribute("selectedAgentHierarchyPK", selectedAgentHierarchyPK);

        String advancePremium = appReqBlock.getFormBean().getValue("advancePremium");
//        String advancePercent = appReqBlock.getFormBean().getValue("advancePercent");

        AgentHierarchyVO[] agentHierarchyVOs = new UtilitiesForTran().getUniqueAgentHierarchyVOsFromUIObjects(appReqBlock);

        for (int i = 0; i < agentHierarchyVOs.length; i++)
        {
            AgentHierarchyVO agentHierarchyVO = agentHierarchyVOs[i];

            

            if ((agentHierarchyVO.getAgentHierarchyPK() + "").equals(selectedAgentHierarchyPK))
            {
                if (Util.isANumber(advancePremium))
                {
                    agentHierarchyVO.setAdvancePremium(new EDITBigDecimal(advancePremium).getBigDecimal());
                }

//                if (Util.isANumber(advancePercent))
//                {
//                    agentHierarchyVO.setAdvancePercent(new EDITBigDecimal(advancePercent).getBigDecimal());
//                }
            }
        }

        return QUOTE_COMMIT_AGENT;
    }

    /*  OBSOLETE???? */
    protected String showSelectedHierarchyRow(AppReqBlock appReqBlock) throws Exception
    {
        String contractCodeCT = Util.initString("contractCodeCT", null);

        String selectedAgentHierarchyAllocationPK = appReqBlock.getFormBean().getValue("selectedAgentHierarchyAllocationPK");
        appReqBlock.getHttpServletRequest().setAttribute("selectedAgentHierarchyAllocationPK", selectedAgentHierarchyAllocationPK);

        String selectedAgentSnapshotPK = appReqBlock.getFormBean().getValue("selectedAgentSnapshotPK");
        appReqBlock.getHttpServletRequest().setAttribute("selectedAgentSnapshotPK", selectedAgentSnapshotPK);

        UIAgentHierarchyVO[] uiAgentHierarchyVOs = (UIAgentHierarchyVO[]) appReqBlock.getHttpSession().getAttribute("uiAgentHierarchyVOs");

        for (int u = 0; u < uiAgentHierarchyVOs.length; u++)
        {
            AgentHierarchyVO agentHierarchyVO = uiAgentHierarchyVOs[u].getAgentHierarchyVO();
            AgentSnapshotVO[] agentSnapshotVOs = agentHierarchyVO.getAgentSnapshotVO();

            for (int s = 0; s < agentSnapshotVOs.length; s++)
            {
                if ((agentSnapshotVOs[s].getAgentSnapshotPK() + "").equals(selectedAgentSnapshotPK))
                {
                    List voInclusionList = new ArrayList();
                    agent.business.Agent agentComponent = new agent.component.AgentComponent();
                    CommissionProfileVO[] commissionProfileVOs = agentComponent.composeCommissionProfileVOByContractCodeCT(contractCodeCT, voInclusionList);

                    if (commissionProfileVOs != null)
                    {
                        appReqBlock.getHttpSession().setAttribute("commissionProfileVOs", commissionProfileVOs);
                    }
                }
            }
        }

        return AGENT_HIERARCHY_DIALOG;
    }

    protected String selectCommProfileForAgent(AppReqBlock appReqBlock) throws Exception
    {
        String selectedAgentHierarchyPK = appReqBlock.getFormBean().getValue("selectedAgentHierarchyPK");
        String selectedAgentSnapshotPK = appReqBlock.getFormBean().getValue("selectedAgentSnapshotPK");
        String selectedCommProfileFK = appReqBlock.getFormBean().getValue("selectedCommProfileFK");

        appReqBlock.getHttpServletRequest().setAttribute("selectedAgentHierarchyPK", selectedAgentHierarchyPK);
        appReqBlock.getHttpServletRequest().setAttribute("selectedAgentSnapshotPK", selectedAgentSnapshotPK);
        appReqBlock.getHttpServletRequest().setAttribute("selectedCommProfileFK", selectedCommProfileFK);

        return AGENT_HIERARCHY_DIALOG;
    }

    /*    OBSOLETE ??? */
    protected String saveAgentHierarchy(AppReqBlock appReqBlock) throws Exception
    {
        String selectedAgentHierarchyAllocationPK = appReqBlock.getFormBean().getValue("selectedAgentHierarchyAllocationPK");
        appReqBlock.getHttpServletRequest().setAttribute("selectedAgentHierarchyAllocationPK", selectedAgentHierarchyAllocationPK);

        String selectedAgentSnapshotPK = appReqBlock.getFormBean().getValue("selectedAgentSnapshotPK");
        String selectedCommProfileFK = appReqBlock.getFormBean().getValue("selectedCommProfileFK");

        CommissionProfileVO[] commProfileVOs = (CommissionProfileVO[]) appReqBlock.getHttpSession().getAttribute("commissionProfileVOs");
        UIAgentHierarchyVO[] uiAgentHierarchyVOs = (UIAgentHierarchyVO[]) appReqBlock.getHttpSession().getAttribute("uiAgentHierarchyVOs");

        for (int u = 0; u < uiAgentHierarchyVOs.length; u++)
        {
            AgentHierarchyVO agentHierarchyVO = uiAgentHierarchyVOs[u].getAgentHierarchyVO();
            AgentSnapshotVO[] agentSnapshotVOs = agentHierarchyVO.getAgentSnapshotVO();

            for (int s = 0; s < agentSnapshotVOs.length; s++)
            {
                if ((agentSnapshotVOs[s].getAgentSnapshotPK() + "").equals(selectedAgentSnapshotPK))
                {
                    for (int c = 0; c < commProfileVOs.length; c++)
                    {
                        if ((commProfileVOs[c].getCommissionProfilePK() + "").equals(selectedCommProfileFK))
                        {
                            agentSnapshotVOs[s].setParentVO(CommissionProfileVO.class, commProfileVOs[c]);
                            break;
                        }
                    }

                    break;
                }
            }
        }

        appReqBlock.getHttpSession().removeAttribute("commissionProfileVOs");

        return AGENT_HIERARCHY_DIALOG;
    }

    protected String closeAgentHierarchy(AppReqBlock appReqBlock) throws Exception
    {
        String selectedAgentHierarchyPK = appReqBlock.getFormBean().getValue("selectedAgentHierarchyPK");
        appReqBlock.getHttpServletRequest().setAttribute("selectedAgentHierarchyPK", selectedAgentHierarchyPK);

        appReqBlock.getHttpSession().removeAttribute("commissionProfileVOs");

        return QUOTE_COMMIT_AGENT;
    }

    protected String showCommissionOverrides(AppReqBlock appReqBlock) throws Exception
    {
        PageBean formBean = appReqBlock.getFormBean();

        String selectedAgentHierarchyPK = formBean.getValue("selectedAgentHierarchyPK");
        String selectedAgentSnapshotPK = formBean.getValue("selectedAgentSnapshotPK");
        String selectedCommProfileFK = formBean.getValue("selectedCommProfileFK");

        appReqBlock.getHttpServletRequest().setAttribute("selectedAgentHierarchyPK", selectedAgentHierarchyPK);
        appReqBlock.getHttpServletRequest().setAttribute("selectedAgentSnapshotPK", selectedAgentSnapshotPK);
        appReqBlock.getHttpServletRequest().setAttribute("selectedCommProfileFK", selectedCommProfileFK);

        return COMMISSION_OVERRIDES_DIALOG;
    }

    protected String saveCommissionOverrides(AppReqBlock appReqBlock) throws Exception
    {
        PageBean formBean = appReqBlock.getFormBean();

        String selectedAgentHierarchyPK = formBean.getValue("selectedAgentHierarchyPK");
        String selectedAgentSnapshotPK = formBean.getValue("selectedAgentSnapshotPK");
        String selectedCommProfileFK = Util.initString(formBean.getValue("selectedCommProfileFK"), null);
        String servicingAgentIndicator = formBean.getValue("servicingAgentIndicator");
        String advancePercent = Util.initString(appReqBlock.getFormBean().getValue("advancePercent"), "0.0");
        String recoveryPercent = Util.initString(appReqBlock.getFormBean().getValue("recoveryPercent"), "0.0");

        appReqBlock.getHttpServletRequest().setAttribute("selectedAgentHierarchyPK", selectedAgentHierarchyPK);
        appReqBlock.getHttpServletRequest().setAttribute("selectedAgentSnapshotPK", selectedAgentSnapshotPK);
        appReqBlock.getHttpServletRequest().setAttribute("selectedCommProfileFK", selectedCommProfileFK);

        AgentHierarchyVO[] agentHierarchyVOs = new UtilitiesForTran().getUniqueAgentHierarchyVOsFromUIObjects(appReqBlock);

        for (int i = 0; i < agentHierarchyVOs.length; i++)
        {
            AgentHierarchyVO agentHierarchyVO = agentHierarchyVOs[i];

            AgentSnapshotVO[] agentSnapshotVOs = agentHierarchyVO.getAgentSnapshotVO();

            for (int s = 0; s < agentSnapshotVOs.length; s++)
            {
                if ((agentSnapshotVOs[s].getAgentSnapshotPK() + "").equals(selectedAgentSnapshotPK))
                {
                    agentSnapshotVOs[s].setServicingAgentIndicator(servicingAgentIndicator);
                    agentSnapshotVOs[s].setAdvancePercent(new EDITBigDecimal(advancePercent).getBigDecimal());
                    agentSnapshotVOs[s].setRecoveryPercent(new EDITBigDecimal(recoveryPercent).getBigDecimal());
                    break;
                }
            }
        }

        return QUOTE_COMMIT_AGENT;
    }

    /**
     * Resets the commission percent and amount fields back to zero for the targeted AgentSnapshot.
     * @param appReqBlock
     * @return
     */
    protected String deleteCommissionOverrides(AppReqBlock appReqBlock)
    {
        PageBean formBean = appReqBlock.getFormBean();

        String selectedAgentHierarchyPK = formBean.getValue("selectedAgentHierarchyPK");
        String selectedAgentSnapshotPK = formBean.getValue("selectedAgentSnapshotPK");
        String selectedCommProfileFK = formBean.getValue("selectedCommProfileFK");

        AgentHierarchyVO[] agentHierarchyVOs = new UtilitiesForTran().getUniqueAgentHierarchyVOsFromUIObjects(appReqBlock);

        for (int i = 0; i < agentHierarchyVOs.length; i++)
        {
            AgentHierarchyVO agentHierarchyVO = agentHierarchyVOs[i];

            AgentSnapshotVO[] agentSnapshotVOs = agentHierarchyVO.getAgentSnapshotVO();

            for (int j = 0; j < agentSnapshotVOs.length; j++)
            {
                if ((agentSnapshotVOs[j].getAgentSnapshotPK() + "").equals(selectedAgentSnapshotPK))
                {
                    agentSnapshotVOs[j].setServicingAgentIndicator("N");
                }
            }
        }

        appReqBlock.getHttpServletRequest().setAttribute("selectedAgentHierarchyPK", selectedAgentHierarchyPK);
        appReqBlock.getHttpServletRequest().setAttribute("selectedAgentSnapshotPK", selectedAgentSnapshotPK);
        appReqBlock.getHttpServletRequest().setAttribute("selectedCommProfileFK", selectedCommProfileFK);

        return QUOTE_COMMIT_AGENT;
    }

    protected String cancelCommissionOverrides(AppReqBlock appReqBlock) throws Exception
    {
        PageBean formBean = appReqBlock.getFormBean();

        String selectedAgentHierarchyPK = formBean.getValue("selectedAgentHierarchyPK");
        String selectedAgentSnapshotPK = formBean.getValue("selectedAgentSnapshotPK");
        String selectedCommProfileFK = formBean.getValue("selectedCommProfileFK");

        appReqBlock.getHttpServletRequest().setAttribute("selectedAgentHierarchyPK", selectedAgentHierarchyPK);
        appReqBlock.getHttpServletRequest().setAttribute("selectedAgentSnapshotPK", selectedAgentSnapshotPK);
        appReqBlock.getHttpServletRequest().setAttribute("selectedCommProfileFK", selectedCommProfileFK);

        return AGENT_HIERARCHY_DIALOG;
    }

    protected String saveQuoteDetail(AppReqBlock appReqBlock) throws Exception
    {
        SessionBean stateBean = appReqBlock.getSessionBean("quoteStateBean");
        String currentPage = stateBean.getValue("currentPage");

        savePreviousPageFormBean(appReqBlock, currentPage);

//        appReqBlock.getHttpServletRequest().setAttribute("contractTarget", "saveQuote");
//
//        boolean returnToUser = true;
//
//        return saveQuote(appReqBlock, returnToUser);
//
        appReqBlock.getHttpServletRequest().setAttribute("contractTarget", "saveQuote");

        return QUOTE_COMMIT_CONTRACT_NUMBER_DIALOG;
    }

    protected String commitContractDetail(AppReqBlock appReqBlock) throws Exception
    {
        // Check for authorization
        new NewBusinessUseCaseComponent().issueNewBusinessContract();

        SessionBean quoteMainSessionBean = appReqBlock.getSessionBean("quoteMainSessionBean");
        PageBean formBean = quoteMainSessionBean.getPageBean("formBean");
        String optionId = formBean.getValue("optionId");

        SessionBean stateBean = appReqBlock.getSessionBean("quoteStateBean");
        String currentPage = stateBean.getValue("currentPage");

        if (currentPage.equals(EDITING_EXCEPTION_DIALOG))
        {
            String productType = checkProductType(optionId);

            return getMainReturnPage(productType);
        }

        savePreviousPageFormBean(appReqBlock, currentPage);

        appReqBlock.getHttpServletRequest().setAttribute("contractTarget", "commitContract");

        getSuspenseInformation(appReqBlock);
        
        CodeTableWrapper codeTableWrapper = CodeTableWrapper.getSingleton();

        String effectiveDate = appReqBlock.getReqParm("effectiveDate");
        EDITDate effDate = null;
        if (effectiveDate != null && !effectiveDate.equals("")) {
        	effDate = new EDITDate(effectiveDate);
        }
        
        SessionBean riders = appReqBlock.getSessionBean("quoteRiders");
        Map<String, PageBean> ridersMap = riders.getPageBeans();
        String riderInfo = "";
        
        boolean dateMismatchDetected = false;
        for (PageBean pageBean : ridersMap.values()) {
            String riderEffectiveDate = pageBean.getValue("effectiveDate");
            String riderTerminationDate = pageBean.getValue("terminationDate");

            String riderOptionCode = "";
            String riderOptionCodeKey = pageBean.getValue("optionCodePK");
        	if (riderOptionCodeKey != null && !riderOptionCodeKey.equals("")) {
                riderOptionCode = codeTableWrapper.getCodeTableEntry(Long.parseLong(riderOptionCodeKey)).getCode();
        	}
        	
        	riderInfo += "\\n " + riderOptionCode + " - " + riderEffectiveDate;
        	
        	EDITDate riderEffDate = null;
        	EDITDate riderTermDate = null;
        			
        	if (riderEffectiveDate != null && !riderEffectiveDate.equals("")) {
	        	riderEffDate = new EDITDate(riderEffectiveDate);
	        	
	        	if (riderTerminationDate != null && !riderTerminationDate.equals("")) {
	        		riderTermDate = new EDITDate(riderTerminationDate);
	        	}
	        	
	        	if (riderEffDate != null && riderEffDate.after(effDate) && (riderTermDate == null || !riderEffDate.equals(riderTermDate))) {
	        		dateMismatchDetected = true;
	        	}
        	}
        }

    	String dateMessage = "";
    	if (dateMismatchDetected) {
    		dateMessage = "The coverages being issued do not all have the same effective date: \\n";
            String optionCode = quoteMainSessionBean.getValue("option");
            dateMessage += "\\n " + optionCode + " - " + effectiveDate + riderInfo + "\\n \\n Do you want to continue processing?";
    	}
    	
        appReqBlock.getHttpServletRequest().setAttribute("dateMessage", dateMessage);
    	
        return ISSUE_CONTRACT_DIALOG;
    }

    protected String saveRider(AppReqBlock appReqBlock) throws Exception
    {
        CodeTableWrapper codeTableWrapper = CodeTableWrapper.getSingleton();

        PageBean quoteMainFormBean = appReqBlock.getSessionBean("quoteMainSessionBean").getPageBean("formBean");
        SessionBean riders = appReqBlock.getSessionBean("quoteRiders");
        PageBean formBean  = appReqBlock.getSessionBean("quoteMainSessionBean").getPageBean("riderFormBean");
        quoteMainFormBean.putValue("pageMode", "");

        String riderSegmentPK = "0";  // New rider

        if (! appReqBlock.getReqParm("optionCodePK").equals(""))
        {
            //  Existing rider
            riderSegmentPK = Util.initString(formBean.getValue("riderSegmentPK"), "0");  // this variable is not in the page, just in the bean
        }

        String segmentNamePK        = appReqBlock.getReqParm("selectedPullDownSegmentNamePK");
        String segmentName          = codeTableWrapper.getCodeTableEntry(Long.parseLong(segmentNamePK)).getCode();
        String optionCodePK         = appReqBlock.getReqParm("selectedPullDownOptionCodePK");
        String optionCode           = codeTableWrapper.getCodeTableEntry(Long.parseLong(optionCodePK)).getCode();
        String units                = appReqBlock.getReqParm("units");
        String amount               = appReqBlock.getReqParm("amount");
        String commissionPhaseID    = appReqBlock.getReqParm("commissionPhaseID");
        String commissionPhaseOverride = appReqBlock.getReqParm("commissionPhaseOverride");

        if (segmentName.equalsIgnoreCase("Waiver") && !optionCode.equalsIgnoreCase("NursingHomeWaiver"))
        {
            appReqBlock.getHttpServletRequest().setAttribute("riderMessage", "Invalid Coverage For Rider Type");
            appReqBlock.getHttpServletRequest().setAttribute("errorRiderType", segmentNamePK);
            appReqBlock.getHttpServletRequest().setAttribute("errorCoverage", optionCodePK);
        }
        else
        {
            String effectiveDate        = appReqBlock.getReqParm("effectiveDate");

            if (effectiveDate == null || effectiveDate.equals(""))
            {
                effectiveDate = quoteMainFormBean.getValue("effectiveDate");
            }

            String terminationDate = appReqBlock.getReqParm("terminationDate");

             if (terminationDate == null || terminationDate.equals(""))
            {
                terminationDate = DateTimeUtil.formatYYYYMMDDToMMDDYYYY(EDITDate.DEFAULT_MAX_DATE);
            }

            String originalStateCT = Util.initString(appReqBlock.getFormBean().getValue("originalStateCT"), null);
            String location = Util.initString(appReqBlock.getFormBean().getValue("location"), null);
            String sequence = Util.initString(appReqBlock.getFormBean().getValue("sequence"), null);
            String indivAnnPremium = Util.initString(appReqBlock.getFormBean().getValue("indivAnnPremium"), null);
            String ratedGenderCT = Util.initString(appReqBlock.getFormBean().getValue("ratedGenderCT"), null);
            String underwritingClass = Util.initString(appReqBlock.getFormBean().getValue("underwritingClass"), null);
            String groupPlan = Util.initString(appReqBlock.getFormBean().getValue("groupPlan"), null);
            String creationOperator = "";
            String creationDate = "";
            String riderNumber = "";
            SessionBean quoteMainSessionBean = appReqBlock.getSessionBean("quoteMainSessionBean");
            int totalRiders = Integer.parseInt(Util.initString(quoteMainSessionBean.getValue("totalRiders"), "0"));

            if (riderSegmentPK.equals("0"))//new rider
            {
                creationOperator = appReqBlock.getUserSession().getUsername();
                creationDate = DateTimeUtil.formatEDITDateAsMMDDYYYY(new EDITDate());
                
                //elementool[1106]: iterate over the riders and determine the 
                //highest riderNumber. this will be used to determine the next 
                //number that should be used.
                int maxRiderNumber = 0;
                int currentRiderNumber = 0;
                Map<String, PageBean> ridersMap = riders.getPageBeans();
                for (PageBean pageBean : ridersMap.values()) {
                	if (pageBean.getValue("riderNumber") != null) {
                		currentRiderNumber = Integer.valueOf(pageBean.getValue("riderNumber"));
	                	if (currentRiderNumber > maxRiderNumber) {
	                		maxRiderNumber = currentRiderNumber;
	                	}
                	}
                }
                riderNumber          = (maxRiderNumber + 1) + "";
                totalRiders          = totalRiders + 1;
            }
            else
            {
                riderNumber          = appReqBlock.getReqParm("riderNumber");
                creationOperator = formBean.getValue("creationOperator");
                creationDate = formBean.getValue("creationDate");
            }


            
            String key = riderNumber + "_" + optionCode;

            PageBean riderBean = null;

            if (!riders.pageBeanExists(key))
            {
                riderBean = new PageBean();
            }
            else
            {
                riderBean = riders.getPageBean(key);
            }


            riderBean.putValue("segmentNamePK", segmentNamePK);
            riderBean.putValue("optionCodePK", optionCodePK);
            riderBean.putValue("effectiveDate", effectiveDate);
            riderBean.putValue("terminationDate", terminationDate);
            riderBean.putValue("originalStateCT", originalStateCT);
            riderBean.putValue("location", location);
            riderBean.putValue("sequence", sequence);
            riderBean.putValue("indivAnnPremium", indivAnnPremium);
            riderBean.putValue("ratedGenderCT", ratedGenderCT);
            riderBean.putValue("underwritingClass", underwritingClass);
            riderBean.putValue("groupPlan", groupPlan);
            riderBean.putValue("maturityDate", terminationDate);
            riderBean.putValue("riderSegmentPK", riderSegmentPK);
            riderBean.putValue("units", units);
            riderBean.putValue("amount", amount);
            riderBean.putValue("commissionPhaseID", commissionPhaseID);
            riderBean.putValue("commissionPhaseOverride", commissionPhaseOverride);
            riderBean.putValue("creationOperator", creationOperator);
            riderBean.putValue("creationDate", creationDate);
            riderBean.putValue("riderNumber", riderNumber);
            riderBean.putValue("gioOption", Util.initString(appReqBlock.getReqParm("gioOption"), ""));
            riderBean.putValue("multiple", Util.initString(appReqBlock.getReqParm("multiple"), ""));
            riders.putPageBean(key, riderBean);
     
            appReqBlock.getSessionBean("quoteMainSessionBean").putPageBean("riderFormBean", riderBean);

            quoteMainSessionBean.putValue("totalRiders", totalRiders + "");
            appReqBlock.getHttpSession().setAttribute("quoteMainSessionBean", quoteMainSessionBean);

        }

        PageBean baseFormBean = appReqBlock.getSessionBean("quoteMainSessionBean").getPageBean("formBean");

        if (baseFormBean.getValue("optionId").equalsIgnoreCase(QuoteDetailTran.TRADITIONAL) ||
        	baseFormBean.getValue("optionId").equalsIgnoreCase(QuoteDetailTran.UNIVERSAL_LIFE) ||
            baseFormBean.getValue("optionId").equalsIgnoreCase("Life") ||
            Segment.OPTIONCODES_AH.contains(baseFormBean.getValue("optionId").toUpperCase()))
        {
            return QUOTE_COMMIT_LIFE_RIDER;
        }
        else
        {
            return QUOTE_COMMIT_RIDER;
        }
    }

    protected String deleteQuote(AppReqBlock appReqBlock) throws Exception
    {
        String contractNumber = appReqBlock.getSessionBean("quoteMainSessionBean").
                getPageBean("formBean").getValue("contractNumber");

        String optionId = appReqBlock.getSessionBean("quoteMainSessionBean").
                getPageBean("formBean").getValue("optionId");

        contract.business.Lookup lookup = new contract.component.LookupComponent();
        contract.business.Contract contract = new contract.component.ContractComponent();

        if (!contractNumber.equals(""))
        {
            SegmentVO[] segmentVO = lookup.getSegmentByContractNumber(contractNumber, true, new ArrayList());

            String productType = checkProductType(optionId);

            if (segmentVO[0].getSegmentStatusCT().equalsIgnoreCase("Quote") ||
                segmentVO[0].getSegmentStatusCT().equalsIgnoreCase("Pending"))
            {
                event.business.Event event = new event.component.EventComponent();

                ContractSetupVO[] contractSetupVOs = event.composeContractSetupBySegmentFK(segmentVO[0].getSegmentPK());
                if (contractSetupVOs != null && contractSetupVOs.length > 0)
                {
                    appReqBlock.getHttpServletRequest().setAttribute("contractNumberMessage",
                                           "Transaction History Exists - Contract Not Deleted");

                    return getMainReturnPage(productType);
                }
                else
                {
                    contract.deleteSegment(segmentVO[0]);

                    clearAllQuoteSessions(appReqBlock);

                    return QUOTE_COMMIT_MAIN;
                }
            }
            else
            {
                appReqBlock.getHttpServletRequest().setAttribute("contractNumberMessage",
                        "Contract Not Deleted - Must Be In Quote Status");

                return getMainReturnPage(productType);
            }
        }
        else
        {
            clearAllQuoteSessions(appReqBlock);

            return QUOTE_COMMIT_MAIN;
        }
    }

    protected String deleteSelectedClient(AppReqBlock appReqBlock) throws Exception
    {
        SessionBean quoteMainSessionBean = appReqBlock.getSessionBean("quoteMainSessionBean");        
        PageBean quoteMainFormBean = quoteMainSessionBean.getPageBean("formBean");
        
        String segmentStatus = Util.initString(quoteMainFormBean.getValue("statusCode"), "");        
        String segmentPK = Util.initString(quoteMainFormBean.getValue("segmentPK"), "0");

        //ANY ROLE CAN BE DELETED WHILE SEGMENT STATUS IS PENDING, QUOTE, etc
        boolean allowToDeleteClient = checkContractStatus(segmentStatus);

        if (allowToDeleteClient)
        {
            String selectedTaxId = appReqBlock.getFormBean().getValue("selectedTaxId");
            String selectedOptionId = appReqBlock.getFormBean().getValue("selectedOptionId");
            String selectedRelationship = appReqBlock.getFormBean().getValue("selectedRelationship");
            String selectedClientRoleFK = appReqBlock.getFormBean().getValue("selectedClientRoleFK");
            String selectedContractClientPK = appReqBlock.getFormBean().getValue("selectedContractClientPK");
            String riderNumber = appReqBlock.getFormBean().getValue("selectedRiderNumber");

            //  If the taxId does not exist, it comes back from the page as an empty string but the pageBeans were
            //  loaded with "null" for that portion of the key.  Trim all whitespace from the taxId and if the length
            //  of the string is zero, it is an empty string.  Set to null to make the key match
            selectedTaxId = this.convertEmptyToNull(selectedTaxId); 

            String clientKey = selectedTaxId + riderNumber + selectedOptionId + selectedRelationship + selectedContractClientPK + selectedClientRoleFK;

            PageBean clientPageBean = appReqBlock.getSessionBean("quoteClients").getPageBean(clientKey);

            SessionBean deletedContractClients = appReqBlock.getSessionBean("quoteDeletedContractClients");

            if (deletedContractClients == null)
            {
                deletedContractClients = new SessionBean();
                appReqBlock.addSessionBean("quoteDeletedContractClients", deletedContractClients);
            }

            if (selectedContractClientPK != null && Util.isANumber(selectedContractClientPK))
            {
                // When quote is not yet saved to database, do not bother to keep track of the clients
                if (Long.parseLong(segmentPK) == 0 )
                {
                    appReqBlock.getSessionBean("quoteClients").removePageBean(clientKey);
                }
                else
                {
                    //As per Tom, all deleted clients should be marked as "D", business may need to keep account for those clients

                    //event.business.Event eventComponent = new event.component.EventComponent();

                    //ClientSetupVO[] clientSetupVOs = eventComponent.composeClientSetupByContractClientFK(Long.parseLong(selectedContractClientPK));

                    //if (clientSetupVOs != null && clientSetupVOs.length > 0)
                    //{
                        clientPageBean.putValue("overrideStatus", "D");
                        appReqBlock.getSessionBean("quoteClients").putPageBean(clientKey, clientPageBean);
                    //}
                    //else
                    //{
                    //    appReqBlock.getSessionBean("quoteMainSessionBean").putPageBean("clientFormBean", new PageBean());

                    //  appReqBlock.getSessionBean("quoteClients").removePageBean(clientKey);
                    //}

                    //  Put deleted contractClients into a bean.  We will need to know which ones were deleted by the user
                    //  when saving the segment.
                    deletedContractClients.putPageBean(clientKey, clientPageBean);
                }
            }
            else
            {
                appReqBlock.getSessionBean("quoteMainSessionBean").putPageBean("clientFormBean", new PageBean());

                appReqBlock.getSessionBean("quoteClients").removePageBean(clientKey);
            }
        }
        else
        {
            appReqBlock.getHttpServletRequest().setAttribute("clientMessage", "Invalid Policy Status for Client Delete");
        }

        return QUOTE_COMMIT_NON_PAYEE;
    }

    private boolean checkContractStatus(String segmentStatus)
    {
        boolean allowToDeleteClient = false;
        for (int i = 0; i < CONTRACT_STATUS_TO_DELETE_CLIENT.length; i++)
        {
            if (segmentStatus.equalsIgnoreCase(CONTRACT_STATUS_TO_DELETE_CLIENT[i]))
            {
                allowToDeleteClient = true;
                break;
            }
        }

        return allowToDeleteClient;
    }

    protected String deleteSelectedAgent(AppReqBlock appReqBlock) throws Exception
    {
        String selectedAgentHierarchyPK = appReqBlock.getReqParm("selectedAgentHierarchyPK");
        UIAgentHierarchyVO[] uiAgentHierarchyVOs = (UIAgentHierarchyVO[]) appReqBlock.getHttpSession().getAttribute("uiAgentHierarchyVOs");

        List hierarchyVector = new ArrayList();
        List hierarchyDeleted = new ArrayList();

        for (int i = 0; i < uiAgentHierarchyVOs.length; i++)
        {
            String agentHierarchyPK = uiAgentHierarchyVOs[i].getAgentHierarchyVO().getAgentHierarchyPK() + "";

            if (!agentHierarchyPK.equals(selectedAgentHierarchyPK))
            {
                hierarchyVector.add(uiAgentHierarchyVOs[i]);
            }
            else
            {
                hierarchyDeleted.add(uiAgentHierarchyVOs[i]);
            }
        }

        UIAgentHierarchyVO[] newUIAgentHierarchyVOs = (UIAgentHierarchyVO[])
                hierarchyVector.toArray(new UIAgentHierarchyVO[hierarchyVector.size()]);

        appReqBlock.getHttpSession().setAttribute("uiAgentHierarchyVOs", newUIAgentHierarchyVOs);

        // Maintain the deleted hierarchy; it will be deleted from DB at the time of Quote/segment save
        UIAgentHierarchyVO[] newUIAgentHierarchyVOsDeleted = (UIAgentHierarchyVO[])
                hierarchyDeleted.toArray(new UIAgentHierarchyVO[hierarchyDeleted.size()]);

        appReqBlock.getHttpSession().setAttribute("uiAgentHierarchyVOsDeleted", newUIAgentHierarchyVOsDeleted);
        
        return QUOTE_COMMIT_AGENT;
    }

    protected String deleteSelectedFund(AppReqBlock appReqBlock) throws Exception
    {
        PageBean formBean = appReqBlock.getSessionBean("quoteMainSessionBean").getPageBean("formBean");

        String selectedFundId = appReqBlock.getFormBean().getValue("selectedFundId");
        String selectedOptionId = appReqBlock.getFormBean().getValue("selectedOptionId");
        String investmentPK = appReqBlock.getFormBean().getValue("investmentPK");

        boolean shouldBeDeleted = true;

//        UserSession userSession = appReqBlock.getUserSession();

        if (Util.isANumber(investmentPK))
        {
            InvestmentHistory[] investmentHistory = InvestmentHistory.findByInvestmentFK(new Long(investmentPK));
            if (investmentHistory != null)
            {
                shouldBeDeleted = false;
            }
        }

        String key = selectedOptionId + selectedFundId;

        SessionBean funds = appReqBlock.getSessionBean("quoteFunds");

        if (shouldBeDeleted)
        {
            funds.removePageBean(key);
        }
        else
        {
            funds.getPageBean(key).putValue("status", "D");
        }

        appReqBlock.getSessionBean("quoteMainSessionBean").putPageBean("investmentFormBean", new PageBean());

        String productStructureId = formBean.getValue("companyStructureId");

        if (!productStructureId.equals("") && !productStructureId.equalsIgnoreCase("Please Select"))
        {
            UIFilteredFundVO[] uiFilteredFundVOs = new UtilitiesForTran().buildUIFilteredFundVO(appReqBlock, productStructureId);
            appReqBlock.getHttpServletRequest().setAttribute("uiFilteredFundVOs", uiFilteredFundVOs);
        }

        return QUOTE_COMMIT_INVESTMENTS;
    }

    protected String deleteSelectedRider(AppReqBlock appReqBlock) throws Exception
    {
        String optionCodePK = appReqBlock.getFormBean().getValue("optionCodePK");
        String riderNumber = appReqBlock.getFormBean().getValue("riderNumber");

        String optionCode = CodeTableWrapper.getSingleton().getCodeTableEntry(Long.parseLong(optionCodePK)).getCode();

        String key = riderNumber + "_" + optionCode;

        SessionBean riders = appReqBlock.getSessionBean("quoteRiders");
        SessionBean deletedRiders = appReqBlock.getSessionBean("quoteDeletedRiders");
        if (deletedRiders == null)
        {
            deletedRiders = new SessionBean();
            appReqBlock.addSessionBean("quoteDeletedRiders", deletedRiders);
        }

        PageBean riderPageBean = riders.getPageBean(key);

        riders.removePageBean(key);
        deletedRiders.putPageBean(key, riderPageBean);

        appReqBlock.getSessionBean("quoteMainSessionBean").putPageBean("riderFormBean", new PageBean());

        PageBean baseFormBean = appReqBlock.getSessionBean("quoteMainSessionBean").getPageBean("formBean");

        if (baseFormBean.getValue("optionId").equalsIgnoreCase(QuoteDetailTran.TRADITIONAL) ||
        	baseFormBean.getValue("optionId").equalsIgnoreCase(QuoteDetailTran.UNIVERSAL_LIFE) ||
            baseFormBean.getValue("optionId").equalsIgnoreCase("Life") ||
        	Segment.OPTIONCODES_AH.contains(baseFormBean.getValue("optionId").toUpperCase()))
        {
            return QUOTE_COMMIT_LIFE_RIDER;
        }
        else
        {
            return QUOTE_COMMIT_RIDER;
        }
    }

    protected String cancelQuote(AppReqBlock appReqBlock) throws Exception
    {
        DepositsVO[] depositsVOs = (DepositsVO[]) appReqBlock.getHttpSession().getAttribute("depositsVOs");

        if (depositsVOs != null)
        {
            for (int i = 0; i < depositsVOs.length; i++)
            {
                if (depositsVOs[i].getDepositsPK() < 0)
                {
                    event.business.Event eventComponent = new event.component.EventComponent();

                    if (depositsVOs[i].getSuspenseFK() > 0)
                    {
                        SuspenseVO suspenseVO = eventComponent.composeSuspenseVO(depositsVOs[i].getSuspenseFK(), new ArrayList());

                        if (suspenseVO != null)
                        {
                            EDITBigDecimal  pendingAmt = Util.roundToNearestCent(suspenseVO.getPendingSuspenseAmount());
                            pendingAmt = pendingAmt.subtractEditBigDecimal(Util.roundToNearestCent(depositsVOs[i].getAmountReceived()));
                            suspenseVO.setPendingSuspenseAmount(Util.roundToNearestCent(pendingAmt).getBigDecimal());
                            eventComponent.saveSuspenseNonRecursively(suspenseVO);
                        }
                    }
                }
            }
        }

        clearAllQuoteSessions(appReqBlock);

        UserSession userSession = appReqBlock.getUserSession();

        appReqBlock.getFormBean().putValue("segmentPK", String.valueOf(userSession.getSegmentPK()));
        
        userSession.unlockSegment();

        return loadQuote(appReqBlock);
    }

    protected String showCalculatedValuesAdd(AppReqBlock appReqBlock) throws Exception
    {
        return QUOTE_COMMIT_CALCULATED_VALUE_DIALOG;
    }

    protected String showUniversalLifeCalculatedValuesAdd(AppReqBlock appReqBlock) throws Exception
    {
        return QUOTE_COMMIT_UL_CALCULATED_VALUE_DIALOG;
    }

    protected String showAnalyzer(AppReqBlock appReqBlock) throws Exception
    {
        return ANALYZER_DIALOG;
    }

    protected String saveBankInfo(AppReqBlock appReqBlock) throws Exception
    {
        PageBean formBean = appReqBlock.getFormBean();
        PageBean clientBank = new PageBean();
        SessionBean quoteMainSessionBean = appReqBlock.getSessionBean("quoteMainSessionBean");

        clientBank.putValue("bankAccountNumber", formBean.getValue("bankAccountNumber"));
        clientBank.putValue("bankRoutingNumber", formBean.getValue("bankRoutingNumber"));
        clientBank.putValue("preNoteDate", formBean.getValue("preNoteDate"));

        quoteMainSessionBean.putPageBean("clientBank", clientBank);

        return null;
    }

    protected String showContractClientAddDialog(AppReqBlock appReqBlock) throws Exception
    {
        SessionBean contractClientAddSessionBean = appReqBlock.getSessionBean("contractClientAddSessionBean");

        contractClientAddSessionBean.clearState();

        return CONTRACT_CLIENT_ADD_DIALOG;
    }

    //This method should be invoked Payee and Non Payee
    protected String showClientDetailSummary(AppReqBlock appReqBlock) throws Exception
    {
        PageBean formBean = appReqBlock.getFormBean();
        String taxId = formBean.getValue("selectedTaxId");
        String optionId = formBean.getValue("selectedOptionId");
        String relationship = formBean.getValue("selectedRelationship");
        String clientRoleFK = formBean.getValue("selectedClientRoleFK");
        String contractClientPK = formBean.getValue("selectedContractClientPK");
        String riderNumber = formBean.getValue("selectedRiderNumber");

        appReqBlock.getHttpServletRequest().setAttribute("selectedTaxId", taxId);
        appReqBlock.getHttpServletRequest().setAttribute("selectedOptionId", optionId);
        appReqBlock.getHttpServletRequest().setAttribute("selectedRelationship", relationship);
        appReqBlock.getHttpServletRequest().setAttribute("selectedClientRoleFK", clientRoleFK);
        appReqBlock.getHttpServletRequest().setAttribute("selectedContractClientPK", contractClientPK);
        appReqBlock.getHttpServletRequest().setAttribute("selectedRiderNumber", riderNumber);

        //This paramter will be used to link the contractClient selected to show client detail
        appReqBlock.getUserSession().setParameter("selectedClientRoleFK", clientRoleFK);

        PageBean clientBean = null;

        //  If the taxId does not exist, it comes back from the page as an empty string but the pageBeans were
        //  loaded with "null" for that portion of the key.  Trim all whitespace from the taxId and if the length
        //  of the string is zero, it is an empty string.  Set to null to make the key match
        taxId = this.convertEmptyToNull(taxId);
        
        clientBean = appReqBlock.getSessionBean("quoteClients").getPageBean(taxId + riderNumber +
                                                                             optionId +
                                                                              relationship +
                                                                               contractClientPK +
                                                                                clientRoleFK);

        clientBean.putValue("oldOptionId", optionId);
        clientBean.putValue("oldRelationship", relationship);
        clientBean.putValue("riderNumber", riderNumber);

        appReqBlock.getSessionBean("quoteMainSessionBean").putPageBean("clientFormBean", clientBean);

        if (relationship.equalsIgnoreCase("TermInsured"))
        {
            PageBean baseFormBean = appReqBlock.getSessionBean("quoteMainSessionBean").getPageBean("formBean");

            String batchContractSetupFK = Util.initString(baseFormBean.getValue("batchContractSetupFK"), "0");
            getCaseUnderwritingForClient(appReqBlock, batchContractSetupFK);
        }

        if (relationship.equalsIgnoreCase("pay"))
        {
            return QUOTE_COMMIT_PAYEE;
        }
        else
        {
            if (relationship.equalsIgnoreCase("insured") ||
                relationship.equalsIgnoreCase("terminsured"))
            {
                return QUOTE_COMMIT_INSURED;
            }
            else
            {
                return QUOTE_COMMIT_NON_PAYEE;
            }
        }
    }

    protected String clearQuoteCommitAgentForm(AppReqBlock appReqBlock) throws Exception
    {
        return QUOTE_COMMIT_AGENT;
    }

    protected String showAgentDetailSummary(AppReqBlock appReqBlock) throws Exception
    {
        String selectedAgentHierarchyPK = appReqBlock.getFormBean().getValue("selectedAgentHierarchyPK");
        appReqBlock.getHttpServletRequest().setAttribute("selectedAgentHierarchyPK", selectedAgentHierarchyPK);

        return QUOTE_COMMIT_AGENT;
    }

    protected String showFundDetailSummary(AppReqBlock appReqBlock) throws Exception
    {
        PageBean formBean = appReqBlock.getFormBean();

        String optionId = formBean.getValue("selectedOptionId");
        String fundId = formBean.getValue("selectedFundId");

        String key = optionId + fundId;

        PageBean fundBean = appReqBlock.getSessionBean("quoteFunds").getPageBean(key);

        appReqBlock.getSessionBean("quoteMainSessionBean").putPageBean("investmentFormBean", fundBean);

        PageBean quoteMainFormBean = appReqBlock.getSessionBean("quoteMainSessionBean").getPageBean("formBean");

        String productStructureId = quoteMainFormBean.getValue("companyStructureId");

        if (!productStructureId.equals("") && !productStructureId.equalsIgnoreCase("Please Select"))
        {
            UIFilteredFundVO[] uiFilteredFundVOs = new UtilitiesForTran().buildUIFilteredFundVO(appReqBlock, productStructureId);
            appReqBlock.getHttpServletRequest().setAttribute("uiFilteredFundVOs", uiFilteredFundVOs);
        }

        return QUOTE_COMMIT_INVESTMENTS;
    }

    protected String showRiderDetail(AppReqBlock appReqBlock)throws Exception
    {
        PageBean formBean = appReqBlock.getFormBean();

        String optionCodePK = formBean.getValue("optionCodePK");
        String riderNumber = Util.initString(formBean.getValue("riderNumber"), "1");

        String optionCode = CodeTableWrapper.getSingleton().getCodeTableEntry(Long.parseLong(optionCodePK)).getCode();

        String key = riderNumber + "_" + optionCode;

        PageBean riderBean = appReqBlock.getSessionBean("quoteRiders").getPageBean(key);

        appReqBlock.getSessionBean("quoteMainSessionBean").putPageBean("riderFormBean", riderBean);

        PageBean baseFormBean = appReqBlock.getSessionBean("quoteMainSessionBean").getPageBean("formBean");
        baseFormBean.putValue("pageMode", "detail");

        String batchContractSetupFK = Util.initString(baseFormBean.getValue("batchContractSetupFK"), "0");
        String insuredRelationToEmp = Util.initString(appReqBlock.getSessionBean("quoteMainSessionBean").getValue("insuredRelationToEmp"), null);
        String increaseOptionStatus = getCaseProductUnderwriting(appReqBlock, batchContractSetupFK, optionCode, insuredRelationToEmp);
    
        baseFormBean.putValue("increaseOptionStatus", increaseOptionStatus);

        if (baseFormBean.getValue("optionId").equalsIgnoreCase(QuoteDetailTran.TRADITIONAL) ||
        	baseFormBean.getValue("optionId").equalsIgnoreCase(QuoteDetailTran.UNIVERSAL_LIFE) ||
            baseFormBean.getValue("optionId").equalsIgnoreCase("Life") ||
            Segment.OPTIONCODES_AH.contains(formBean.getValue("optionId").toUpperCase()))
        {
            return QUOTE_COMMIT_LIFE_RIDER;
        }
        else
        {
            return QUOTE_COMMIT_RIDER;
        }
    }

    protected String cancelQuoteNonPayeeOrPayee(AppReqBlock appReqBlock)
    {
        appReqBlock.getSessionBean("quoteMainSessionBean").putPageBean("clientFormBean", new PageBean());

        return QUOTE_COMMIT_NON_PAYEE;
    }

    private ClientDetailVO checkClientExistence(long clientDetailPK) throws Exception
    {
        client.business.Lookup clientLookup = new client.component.LookupComponent();

        List voExclusionList = new ArrayList();
        voExclusionList.add(ClientRoleVO.class);
//        voExclusionList.add(PreferenceVO.class);
//        voExclusionList.add(ClientAddressVO.class);

        ClientDetailVO[] clientDetailVOs = clientLookup.findByClientPK(clientDetailPK, true, voExclusionList);


        if (clientDetailVOs != null)
        {
            return clientDetailVOs[0];
        }
        else
        {
            return null;
        }
    }

    // Returns a pageBean populated form the dB (if it exists), or null
    // if the client does not exist.
    // The PageBean returned does NOT have an optionId or relationship
    // associated with it.
//    private PageBean getClientInfoFromDB(AppReqBlock appReqBlock, String taxId) throws Exception
//    {
//        PageBean pageBean = new PageBean();
//
//        long clientRolePK = Long.parseLong(pageBean.getValue("clientRoleFK"));
//
//        role.business.Lookup roleLookup = new role.component.LookupComponent();
//
//        ClientRoleVO clientRoleVO = roleLookup.findClientRoleVOByClientRolePK(clientRolePK, false, null)[0];
//
//        long clientDetailPK = clientRoleVO.getClientDetailFK();
//
//        ClientDetailVO clientDetailVO = checkClientExistence(appReqBlock, clientDetailPK);
//
//        if (clientDetailVO != null)
//        {
//            pageBean.putValue("taxId", taxId);
//            pageBean.putValue("lastName", clientDetailVO.getLastName());
//            pageBean.putValue("firstName", clientDetailVO.getFirstName());
//            pageBean.putValue("middleName", clientDetailVO.getMiddleName());
//            pageBean.putValue("corporateName", clientDetailVO.getCorporateName());
//            pageBean.putValue("prefix", clientDetailVO.getNamePrefix());
//            pageBean.putValue("newIssuesEligibilityStatus", Util.initString(clientRoleVO.getNewIssuesEligibilityStatusCT(), ""));
//            pageBean.putValue("newIssuesStartDate", Util.initString(clientRoleVO.getNewIssuesEligibilityStartDate(), ""));
//
//            String dateOfBirth = clientDetailVO.getBirthDate();
//
//            if (dateOfBirth != null)
//            {
//                EDITDate editDateOfBirth = new EDITDate(dateOfBirth);
//
//                pageBean.putValue("dobYear", editDateOfBirth.getFormattedYear());
//                pageBean.putValue("dobMonth", editDateOfBirth.getFormattedMonth());
//                pageBean.putValue("dobDay", editDateOfBirth.getFormattedDay());
//            }
//
//            String usCitizenInd = "N";
//            TaxInformationVO[] taxInformationVO = clientDetailVO.getTaxInformationVO();
//
//            if (taxInformationVO != null && taxInformationVO.length > 0)
//            {
//                String citizenshipInd = taxInformationVO[0].getCitizenshipIndCT();
//
//                if (citizenshipInd != null)
//                {
//                    usCitizenInd = citizenshipInd;
//                }
//
//                if (usCitizenInd.equalsIgnoreCase("Y"))
//                {
//                    pageBean.putValue("usCitizenIndStatus", "checked");
//                }
//                else
//                {
//                    pageBean.putValue("usCitizenIndStatus", "unchecked");
//                }
//            }
//            else
//            {
//                pageBean.putValue("usCitizenIndStatus", "unchecked");
//            }
//
//            String genderId = clientDetailVO.getGenderCT();
//            pageBean.putValue("genderId", genderId);
//
//            return pageBean;
//        }
//        else
//        {
//            return null;
//        }
//    }

    private void savePreviousPageFormBean(AppReqBlock appReqBlock, String previousPage) throws Exception
    {
        CodeTableWrapper codeTableWrapper = CodeTableWrapper.getSingleton();
        CodeTableVO codeTableVO = null;

        PageBean formBean = appReqBlock.getFormBean();

        if (previousPage.equals(QUOTE_COMMIT_MAIN) ||
                previousPage.equals(QUOTE_TRAD_MAIN) ||
                previousPage.equals(QUOTE_UNIVERSAL_LIFE_MAIN) ||
                previousPage.equals(QUOTE_AH_MAIN) ||
                previousPage.equals(QUOTE_LIFE_MAIN) ||
                previousPage.equals(QUOTE_DEFERRED_ANNUITY_MAIN) ||
                previousPage.equals(QUOTE_COMMIT_UL_CALCULATED_VALUE_DIALOG) ||
                previousPage.equals(QUOTE_COMMIT_CALCULATED_VALUE_DIALOG))
        {
            String productStructureId = formBean.getValue("companyStructureId");
            String productStructure = formBean.getValue("companyStructure");
            String optionId = formBean.getValue("optionId");
            String deathBeneOption = formBean.getValue("deathBeneOption");
            String nonForfeitureOption = formBean.getValue("nonForfeitureOption");
            String option7702 = formBean.getValue("option7702");
            String term = formBean.getValue("term");

            SessionBean quoteMainSessionBean = appReqBlock.getSessionBean("quoteMainSessionBean");

            String purchaseAmount = formBean.getValue("purchaseAmount");
            String faceAmount = formBean.getValue("faceAmount");
            String savingsPercent = formBean.getValue("savingsPercent");
            String dismembermentPercent = formBean.getValue("dismembermentPercent");
            String deductionAmountOverride = formBean.getValue("deductionAmountOverride");
            String deductionAmountEffectiveDate = formBean.getValue("deductionAmountEffectiveDate");

            if (optionId != null && !optionId.equals("") && !optionId.equals("Please Select"))
            {
                if (optionId.equalsIgnoreCase("Deferred Annuity"))
                {
                    optionId = quoteMainSessionBean.getValue("option");
                }
                else
                {
                   codeTableVO = codeTableWrapper.getCodeTableEntry(Long.parseLong(optionId));
                   optionId = codeTableVO.getCode();
                }
            }
            else
            {
                optionId = "";
            }

            String productType = checkProductType(optionId);

            if (productType.equalsIgnoreCase(NON_TRAD_LIFE) ||
            		productType.equalsIgnoreCase(UNIVERSAL_LIFE) ||
                    productType.equalsIgnoreCase(TRADITIONAL))
            {
                if (Util.isANumber(deathBeneOption))
                {
                    deathBeneOption = codeTableWrapper.getCodeTableEntry(Long.parseLong(deathBeneOption)).getCode();
                }
                else
                {
                    deathBeneOption = "";
                }

                if ((productType.equalsIgnoreCase(NON_TRAD_LIFE) || productType.equalsIgnoreCase(UNIVERSAL_LIFE)) && Util.isANumber(option7702))
                {
                    option7702 = codeTableWrapper.getCodeTableEntry(Long.parseLong(option7702)).getCode();
                }
                else if (option7702 == null)
                {
                    option7702 = "";
                }
            }

            String issueState = formBean.getValue("areaId");

            if (issueState == null || issueState.equals("") || issueState.equals("Please Select"))
            {
                issueState = "";
            }
            
            String premiumTaxSitusOverride = formBean.getValue("premiumTaxSitusOverride");
            if (Util.isANumber(premiumTaxSitusOverride))
            {
                premiumTaxSitusOverride = codeTableWrapper.getCodeTableEntry(Long.parseLong(premiumTaxSitusOverride)).getCode();
            }
            else
            {
                premiumTaxSitusOverride = "";
            }

            String qualifiedType = Util.initString(formBean.getValue("qualifiedType"), "");

            if (Util.isANumber(qualifiedType))
            {
                qualifiedType = codeTableWrapper.getCodeTableEntry(Long.parseLong(qualifiedType)).getCode();
            }
            else
            {
                if (qualifiedType.equalsIgnoreCase("Please Select"))
                {
                    qualifiedType = "";
                }
            }

            String qualNonQual = Util.initString(formBean.getValue("qualNonQual"), "");

            if (Util.isANumber(qualNonQual))
            {
                qualNonQual = codeTableWrapper.getCodeTableEntry(Long.parseLong(qualNonQual)).getCode();
            }
            else
            {
                if (qualNonQual.equalsIgnoreCase("Please Select"))
                {
                    qualNonQual = "";
                }
            }

            String frequency = Util.initString(formBean.getValue("frequencyId"), "");

            if (frequency != null && !frequency.equals("") && !frequency.equals("Please Select"))
            {
                frequency = codeTableWrapper.getCodeTableEntry(Long.parseLong(frequency)).getCode();
            }

            String effectiveDate = formBean.getValue("effectiveDate");
            String terminationDate = formBean.getValue("terminationDate");
            
            String originalStateCT = Util.initString(formBean.getValue("originalStateCT"), null);
            String location = Util.initString(formBean.getValue("location"), null);
            String sequence = Util.initString(formBean.getValue("sequence"), null);
            String indivAnnPremium = Util.initString(formBean.getValue("indivAnnPremium"), null);
            String ratedGenderCT = Util.initString(formBean.getValue("ratedGenderCT"), null);
            String underwritingClass = Util.initString(formBean.getValue("underwritingClass"), null);
            String groupPlan = Util.initString(formBean.getValue("groupPlan"), null);
            String applicationSignedDate = formBean.getValue("applicationSignedDate");
            String applicationReceivedDate = formBean.getValue("applicationReceivedDate");
            String waiveFreeLookIndicatorStatus = formBean.getValue("waiveFreeLookIndicatorStatus");
            String freeLookDaysOverride = formBean.getValue("freeLookDaysOverride");
            String dialableSalesLoadPct = formBean.getValue("dialableSalesLoadPct");

            formBean.putValue("purchaseAmount", purchaseAmount);
            formBean.putValue("faceAmount", faceAmount);
            formBean.putValue("savingsPercent", savingsPercent);
            formBean.putValue("dismembermentPercent", dismembermentPercent);
            formBean.putValue("deductionAmountOverride", deductionAmountOverride);
            formBean.putValue("deductionAmountEffectiveDate", deductionAmountEffectiveDate);
            formBean.putValue("optionId", optionId);
            formBean.putValue("areaId", issueState);
            formBean.putValue("qualifiedType", qualifiedType);
            formBean.putValue("qualNonQual", qualNonQual);
            formBean.putValue("frequencyId", frequency);
            formBean.putValue("effectiveDate", effectiveDate);
            formBean.putValue("originalStateCT", originalStateCT);
            formBean.putValue("location", location);
            formBean.putValue("sequence", sequence);
            formBean.putValue("indivAnnPremium", indivAnnPremium);
            formBean.putValue("ratedGenderCT", ratedGenderCT);
            formBean.putValue("groupPlan", groupPlan);
            formBean.putValue("underwritingClass", underwritingClass);
            formBean.putValue("terminationDate", terminationDate);
            formBean.putValue("applicationSignedDate", applicationSignedDate);
            formBean.putValue("applicationReceivedDate", applicationReceivedDate);
            formBean.putValue("companyStructure", productStructure);
            formBean.putValue("companyStructureId", productStructureId);
            formBean.putValue("deathBeneOption", deathBeneOption);
            formBean.putValue("nonForfeitureOption", nonForfeitureOption);
            formBean.putValue("option7702", option7702);
            formBean.putValue("term", term);
            formBean.putValue("waiveFreeLookIndicatorStatus", waiveFreeLookIndicatorStatus);
            formBean.putValue("freeLookDaysOverride", freeLookDaysOverride);
            formBean.putValue("dialableSalesLoadPct", dialableSalesLoadPct);
            formBean.putValue("chargeDeductDivisionIndStatus", formBean.getValue("chargeDeductDivisionIndStatus"));
            formBean.putValue("commitmentIndicatorStatus", formBean.getValue("commitmentIndicatorStatus"));
            formBean.putValue("commitmentAmount", formBean.getValue("commitmentAmount"));
            formBean.putValue("pointInScaleIndStatus", formBean.getValue("pointInScaleIndStatus"));
            formBean.putValue("contractType", formBean.getValue("contractType"));
            formBean.putValue("totalFaceAmount", formBean.getValue("totalFaceAmount"));
            formBean.putValue("annualPremium", formBean.getValue("annualPremium"));
            formBean.putValue("applicationSignedState", formBean.getValue("applicationSignedState"));
            formBean.putValue("departmentLocationPK", formBean.getValue("departmentLocationPK"));
            formBean.putValue("contractGroupFK", formBean.getValue("contractGroupFK"));

            formBean.putValue("guidelineSinglePrem", formBean.getValue("guidelineSinglePrem"));
            formBean.putValue("guidelineLevelPrem", formBean.getValue("guidelineLevelPrem"));
            formBean.putValue("tamra", formBean.getValue("tamra"));
            formBean.putValue("MECDate", formBean.getValue("MECDate"));
            formBean.putValue("mecStatus", formBean.getValue("mecStatus"));
            formBean.putValue("mecGuidelineSinglePremium", formBean.getValue("mecGuidelineSinglePremium"));
            formBean.putValue("mecGuidelineLevelPremium", formBean.getValue("mecGuidelineLevelPremium"));
            formBean.putValue("cumGuidelineLevelPremium", formBean.getValue("cumGuidelineLevelPremium"));
            formBean.putValue("startNew7PayIndStatus", formBean.getValue("startNew7PayIndStatus"));
            formBean.putValue("pendingDBOChangeIndStatus", formBean.getValue("pendingDBOChangeIndStatus"));
            formBean.putValue("maxNetAmountAtRisk", formBean.getValue("maxNetAmountAtRisk"));
            formBean.putValue("tamraStartDate", formBean.getValue("tamraStartDate"));
            formBean.putValue("paidToDate", formBean.getValue("paidToDate"));
            formBean.putValue("lapsePendingDate", formBean.getValue("lapsePendingDate"));
            formBean.putValue("lapseDate", formBean.getValue("lapseDate"));
            formBean.putValue("currentDeathBenefit", formBean.getValue("currentDeathBenefit"));
            formBean.putValue("MAPEndDate", formBean.getValue("MAPEndDate"));
            formBean.putValue("tamraInitAdjValue", formBean.getValue("tamraInitAdjValue"));

            if (productType.equalsIgnoreCase(TRADITIONAL) || productType.equalsIgnoreCase(UNIVERSAL_LIFE))
            {
                formBean.putValue("units", formBean.getValue("units"));
                formBean.putValue("commissionPhaseID", formBean.getValue("commissionPhaseID"));
                formBean.putValue("commissionPhaseOverride", formBean.getValue("commissionPhaseOverride"));
                if (!formBean.getValue("segmentStatus").equals("Please Select"))
                {
                    formBean.putValue("statusCode", formBean.getValue("segmentStatus"));
                } else {
                    formBean.putValue("statusCode", formBean.getValue("statusCode"));
                }
            } else if (productType.equalsIgnoreCase(AH)) {
            	
            	formBean.putValue("units", formBean.getValue("units"));
                if (!formBean.getValue("segmentStatus").equals("Please Select"))
                {
                    formBean.putValue("statusCode", formBean.getValue("segmentStatus"));
                } else {
                    formBean.putValue("statusCode", formBean.getValue("statusCode"));
                }
            }

            appReqBlock.getSessionBean("quoteMainSessionBean").putPageBean("formBean", formBean);
        }
        else if (previousPage == QUOTE_COMMIT_RIDER ||
                 previousPage == QUOTE_COMMIT_LIFE_RIDER)
        {
            appReqBlock.getSessionBean("quoteMainSessionBean").putPageBean("riderFormBean", appReqBlock.getFormBean());
        }
        else if (previousPage.equals(QUOTE_COMMIT_NON_PAYEE) ||
                 previousPage.equals(QUOTE_COMMIT_PAYEE) ||
                 previousPage.equals(QUOTE_COMMIT_INSURED)
                )
        {
            String clientRoleFK = formBean.getValue("clientRoleFK");
            String relationToIns = formBean.getValue("relationToIns");
            String relationToEmp = formBean.getValue("relationToEmp");
            String phoneAuth = formBean.getValue("phoneAuth");
            String classType = Util.initString(formBean.getValue("classType"), "");
            String payorOf = formBean.getValue("payorOf");
            String disbAddressType = formBean.getValue("disbAddressType");
            String corrAddressType = formBean.getValue("corrAddressType");

            if (Util.isANumber(relationToIns))
            {
                relationToIns = codeTableWrapper.getCodeTableEntry(Long.parseLong(relationToIns)).getCode();
            }
            else
            {
                relationToIns = "";
            }

            if (Util.isANumber(relationToEmp))
            {
                relationToEmp = codeTableWrapper.getCodeTableEntry(Long.parseLong(relationToEmp)).getCode();
            }
            else
            {
                relationToEmp = "";
            }

            if (Util.isANumber(phoneAuth))
            {
                phoneAuth = codeTableWrapper.getCodeTableEntry(Long.parseLong(phoneAuth)).getCode();
            }
            else
            {
                phoneAuth = "";
            }

            if (Util.isANumber(classType))
            {
                classType = codeTableWrapper.getCodeTableEntry(Long.parseLong(classType)).getCode();
            }
            else if (classType.equalsIgnoreCase("please select"))
            {
                classType = "";
            }

            if (Util.isANumber(disbAddressType))
            {
                disbAddressType = codeTableWrapper.getCodeTableEntry(Long.parseLong(disbAddressType)).getCode();
            }
            else
            {
                disbAddressType = "";
            }

            if (Util.isANumber(corrAddressType))
            {
                corrAddressType = codeTableWrapper.getCodeTableEntry(Long.parseLong(corrAddressType)).getCode();
            }
            else
            {
                corrAddressType = "";
            }

            if (Util.isANumber(payorOf))
            {
                payorOf = codeTableWrapper.getCodeTableEntry(Long.parseLong(payorOf)).getCode();
            }
            else
            {
                payorOf = "";
            }

            String ratedGender =Util.initString(formBean.getValue("ratedGender"), "");
            if (Util.isANumber(ratedGender))
            {
                ratedGender = codeTableWrapper.getCodeTableEntry(Long.parseLong(ratedGender)).getCode();
            }
            else if (ratedGender.equalsIgnoreCase("please select"))
            {
                ratedGender = "";
            }

            String underwritingClass = Util.initString(formBean.getValue(("underwritingClass")), "");
            if (Util.isANumber(underwritingClass))
            {
                underwritingClass = codeTableWrapper.getCodeTableEntry(Long.parseLong(underwritingClass)).getCode();
            }
            else if (underwritingClass.equalsIgnoreCase("please select"))
            {
                underwritingClass = "";
            }

            formBean.putValue("relationToIns", relationToIns);
            formBean.putValue("relationToEmp", relationToEmp);
            formBean.putValue("phoneAuth", phoneAuth);
            formBean.putValue("classType", classType);
            formBean.putValue("disbAddressType", disbAddressType);
            formBean.putValue("corrAddressType", corrAddressType);
            formBean.putValue("payorOf", payorOf);
            formBean.putValue("ratedGender", ratedGender);
            formBean.putValue("underwritingClass", underwritingClass);

            boolean disbAddressTypeFound = false;
            boolean corrAddressTypeFound = false;

            if (!disbAddressType.equals("") || !corrAddressType.equals(""))
            {
                role.business.Lookup roleLookup = new role.component.LookupComponent();
                List clientVOInclusionList = new ArrayList();
                clientVOInclusionList.add(ClientDetailVO.class);
                clientVOInclusionList.add(ClientAddressVO.class);
                ClientRoleVO clientRoleVO = roleLookup.composeClientRoleVO(Long.parseLong(clientRoleFK), clientVOInclusionList);
                ClientAddressVO[] clientAddresses = ((ClientDetailVO) clientRoleVO.getParentVO(ClientDetailVO.class)).getClientAddressVO();

                if (clientAddresses != null)
                {
                    if (!disbAddressType.equals(""))
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
                    else
                    {
                        disbAddressTypeFound = true;
                    }

                    if (corrAddressType != null && !corrAddressType.equals(""))
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
                    else
                    {
                        corrAddressTypeFound = true;
                    }

                    if (!disbAddressTypeFound)
                    {
                        appReqBlock.getHttpServletRequest().setAttribute("clientMessage", "Disbursement Address Type Not Found For Agent");
                    }

                    else if (!corrAddressTypeFound)
                    {
                        appReqBlock.getHttpServletRequest().setAttribute("clientMessage", "Correspondence Address Type Not Found For Agent");
                    }
                }
            }

            appReqBlock.getSessionBean("quoteMainSessionBean").putPageBean("clientFormBean", formBean);
        }
        else if (previousPage == QUOTE_COMMIT_INVESTMENTS)
        {
            String optionId = formBean.getValue("optionId");
            if (optionId != null &&
                    !optionId.equals("") &&
                    !optionId.equals("Please Select"))
            {
                optionId = codeTableWrapper.getCodeTableEntry(Long.parseLong(optionId)).getCode();
            }
            else
            {
                optionId = "";
            }

            formBean.putValue("optionId", optionId);
            String filteredFundFK = formBean.getValue("fundId");
            formBean.putValue("filteredFundFK", filteredFundFK);
            appReqBlock.getSessionBean("quoteMainSessionBean").putPageBean("investmentFormBean", formBean);
        }
        else if (previousPage == QUOTE_SCHED_EVENTS)
        {
            SessionBean quoteMainSessionBean = appReqBlock.getSessionBean("quoteMainSessionBean");
            String productStructureId = Util.initString(quoteMainSessionBean.getValue("companyStructurePK"), "0");
            CodeTableVO[] trxTypes = codeTableWrapper.getCodeTableEntries("TRXTYPE", Long.parseLong(productStructureId));

            String transactionType = formBean.getValue("transactionType");

            for (int t = 0; t < trxTypes.length; t++)
            {
                if (transactionType.equalsIgnoreCase(trxTypes[t].getCodeDesc()))
                {
                    transactionType = trxTypes[t].getCode();
                    break;
                }
            }

            String optionId = formBean.getValue("optionId");

            if (optionId != null && !optionId.equals("") && !optionId.equals("Please Select"))
            {
                optionId = codeTableWrapper.getCodeTableEntry(Long.parseLong(optionId)).getCode();
            }
            else
            {
                optionId = "";
            }

            String distributionId = formBean.getValue("distributionId");

            if (distributionId != null && !distributionId.equals("") && !distributionId.equals("Please Select"))
            {
                distributionId = codeTableWrapper.getCodeTableEntry(Long.parseLong(distributionId)).getCode();
            }
            else
            {
                distributionId = "";
            }

            String lifeContingentId = formBean.getValue("lifeContingentId");

            if (lifeContingentId != null && !lifeContingentId.equals("") && !lifeContingentId.equals("Please Select"))
            {
                lifeContingentId = codeTableWrapper.getCodeTableEntry(Long.parseLong(lifeContingentId)).getCode();
            }
            else
            {
                lifeContingentId = "";
            }

            String frequency = formBean.getValue("frequencyId");

            if (frequency != null && !frequency.equals("") && !frequency.equals("Please Select"))
            {
                frequency = codeTableWrapper.getCodeTableEntry(Long.parseLong(frequency)).getCode();
            }
            else
            {
                frequency = "";
            }

            formBean.putValue("transactionType", transactionType);
            formBean.putValue("optionId", optionId);
            formBean.putValue("distributionId", distributionId);
            formBean.putValue("lifeContingentId", lifeContingentId);
            formBean.putValue("frequencyId", frequency);
            appReqBlock.getSessionBean("quoteMainSessionBean").putPageBean("transactionFormBean", formBean);
        }
    }

    protected String showQuoteSchedEvents(AppReqBlock appReqBlock) throws Exception
    {
        SessionBean stateBean = appReqBlock.getSessionBean("quoteStateBean");
        String currentPage = stateBean.getValue("currentPage");

        savePreviousPageFormBean(appReqBlock, currentPage);

        new QuoteScheduledEventTableModel(appReqBlock);

        return QUOTE_SCHED_EVENTS;
    }

    protected String showTransactionTypeDialog(AppReqBlock appReqBlock) throws Exception
    {
        return QUOTE_TRANS_TYPE_DIALOG;
    }

    protected String showTransactionDefault(AppReqBlock appReqBlock) throws Exception
    {
        PageBean quoteMainFormBean = appReqBlock.getSessionBean("quoteMainSessionBean").getPageBean("formBean");
        String segmentPK = quoteMainFormBean.getValue("segmentPK");
        Segment segment = Segment.findByPK(new Long(segmentPK));
//        String returnPage = null;

        new QuoteTransactionTableModel(segment, appReqBlock);

        return QUOTE_TRANS_WITHDRAWAL;
    }

    protected String showTransactionDetail(AppReqBlock appReqBlock)
    {
        String key = appReqBlock.getReqParm("selectedRowIds_QuoteTransactionTableModel");

        PageBean quoteMainFormBean = appReqBlock.getSessionBean("quoteMainSessionBean").getPageBean("formBean");
        String segmentPK = quoteMainFormBean.getValue("segmentPK");
        Segment segment = Segment.findByPK(new Long(segmentPK));

        new QuoteTransactionTableModel(segment, appReqBlock);

        EDITTrx editTrx = EDITTrx.findBy_PK(new Long(key));
        appReqBlock.getHttpServletRequest().setAttribute("selectedTransaction", editTrx);

//        SessionHelper.evict(editTrx, SessionHelper.EDITSOLUTIONS);

        return QUOTE_TRANS_WITHDRAWAL;
    }

    protected String saveDatesDialog(AppReqBlock appReqBlock)
    {
        PageBean quoteMainFormBean = appReqBlock.getSessionBean("quoteMainSessionBean").getPageBean("formBean");
        quoteMainFormBean.putValue("advanceFinalNotify", appReqBlock.getFormBean().getValue("advanceFinalNotify"));

        return QUOTE_TRAD_MAIN;
    }

    protected String saveTransactionToSummary(AppReqBlock appReqBlock) throws Exception
    {
        CodeTableWrapper codeTableWrapper = CodeTableWrapper.getSingleton();

        PageBean quoteMainFormBean = appReqBlock.getSessionBean("quoteMainSessionBean").
                getPageBean("formBean");

        long segmentFK = Long.parseLong(quoteMainFormBean.getValue("segmentPK"));

        FormBean formBean = appReqBlock.getFormBean();

        String transactionPK = formBean.getValue("transactionPK");

        String transactionType = formBean.getValue("transactionType");
//        String coverage = formBean.getValue("optionId");
        String costOfLivingInd = formBean.getValue("costOfLivingIndStatus");
        String sequenceNumber = Util.initString(formBean.getValue("sequenceNumber"), "1");
        String taxYear = formBean.getValue("taxYear");
        String amount = formBean.getValue("amount");

        String startDate = DateTimeUtil.formatMMDDYYYYToYYYYMMDD(formBean.getValue("startDate"));

        String stopDate = DateTimeUtil.formatMMDDYYYYToYYYYMMDD(formBean.getValue("stopDate"));

        if (stopDate == null)
        {
            stopDate = EDITDate.DEFAULT_MAX_DATE;
        }

        String frequency = formBean.getValue("frequencyId");
        if (Util.isANumber(frequency))
        {
            frequency = codeTableWrapper.getCodeTableEntry(Long.parseLong(frequency)).getCode();
        }
        else
        {
            frequency = null;
        }

        String distributionCode = formBean.getValue("distributionId");
        if (Util.isANumber(distributionCode))
        {
            distributionCode = codeTableWrapper.getCodeTableEntry(Long.parseLong(distributionCode)).getCode();
        }
        else
        {
            distributionCode = null;
        }

        String lifeContingent = formBean.getValue("lifeContingentId");
        if (Util.isANumber(lifeContingent))
        {
            lifeContingent = codeTableWrapper.getCodeTableEntry(Long.parseLong(lifeContingent)).getCode();
        }
        else
        {
            lifeContingent = null;
        }

        long editTrxPK = 0;

        EDITTrxVO[] pendingTransactions = (EDITTrxVO[]) appReqBlock.getHttpSession().getAttribute("pendingTransactions");
        EDITTrxVO editTrxVO = null;

        if (!Util.isANumber(transactionPK))
        {
            contract.business.Contract contractComponent = new contract.component.ContractComponent();
            editTrxPK = contractComponent.getNextAvailableKey() * -1;
        }
        else
        {
            editTrxPK = Long.parseLong(transactionPK);
            editTrxVO = findExistingEDITTrx(editTrxPK, pendingTransactions);
        }

        GroupSetupVO groupSetupVO = null;
        ContractSetupVO contractSetupVO = null;
        ClientSetupVO clientSetupVO = null;
        ScheduledEventVO scheduledEventVO = null;

        if (editTrxVO != null)
        {
            clientSetupVO = (ClientSetupVO) editTrxVO.getParentVO(ClientSetupVO.class);
            contractSetupVO = (ContractSetupVO) clientSetupVO.getParentVO(ContractSetupVO.class);
            groupSetupVO = (GroupSetupVO) contractSetupVO.getParentVO(GroupSetupVO.class);
            if (groupSetupVO.getScheduledEventVOCount() > 0)
            {
                scheduledEventVO = groupSetupVO.getScheduledEventVO(0);
            }
        }

        editTrxVO = createOrUpdateEDITTrx(editTrxPK, transactionType, startDate, sequenceNumber, taxYear, amount, editTrxVO);
        clientSetupVO = createOrUpdateClientSetup(clientSetupVO, editTrxVO);
        scheduledEventVO = createOrUpdateScheduledEventVO(startDate, stopDate, frequency, costOfLivingInd, lifeContingent, scheduledEventVO);
        contractSetupVO = createOrUpdateContractSetup(segmentFK, contractSetupVO, clientSetupVO);
        groupSetupVO = createOrUpdateGroupSetup(distributionCode, groupSetupVO, contractSetupVO, scheduledEventVO);

        editTrxVO.setParentVO(ClientSetupVO.class, clientSetupVO);
        clientSetupVO.setParentVO(ContractSetupVO.class,  contractSetupVO);
        contractSetupVO.setParentVO(GroupSetupVO.class, groupSetupVO);
        scheduledEventVO.setParentVO(GroupSetupVO.class, groupSetupVO);

        updatePendingTransactionList(pendingTransactions, editTrxVO, appReqBlock);

        PageBean mainFormBean = appReqBlock.getSessionBean("quoteMainSessionBean").getPageBean("formBean");

        mainFormBean.putValue("scheduledEvents", "checked");
        
        appReqBlock.getHttpSession().setAttribute("reloadHeader", "true");

        new QuoteScheduledEventTableModel(appReqBlock);

        return QUOTE_SCHED_EVENTS;
    }

    private EDITTrxVO findExistingEDITTrx(long editTrxPK, EDITTrxVO[] pendingTransactions)
    {
        EDITTrxVO editTrxVO = null;

        for (int i = 0; i < pendingTransactions.length; i++)
        {
            if (pendingTransactions[i].getEDITTrxPK() == editTrxPK)
            {
                editTrxVO = pendingTransactions[i];
                break;
            }
        }

        return editTrxVO;
    }

    private GroupSetupVO createOrUpdateGroupSetup(String distributionCode, GroupSetupVO groupSetupVO,
                                                  ContractSetupVO contractSetupVO, ScheduledEventVO scheduledEventVO)
    {
        if (groupSetupVO == null)
        {
            groupSetupVO = new GroupSetupVO();
            groupSetupVO.setGroupSetupPK(0);

            groupSetupVO.addContractSetupVO(contractSetupVO);
            groupSetupVO.addScheduledEventVO(scheduledEventVO);
        }

        groupSetupVO.setDistributionCodeCT(distributionCode);

        return groupSetupVO;
    }

    private ContractSetupVO createOrUpdateContractSetup(long segmentFK, ContractSetupVO contractSetupVO, ClientSetupVO clientSetupVO)
    {
        if (contractSetupVO == null)
        {
            contractSetupVO = new ContractSetupVO();
            contractSetupVO.setContractSetupPK(0);
            contractSetupVO.setGroupSetupFK(0);

            contractSetupVO.addClientSetupVO(clientSetupVO);
        }

        contractSetupVO.setSegmentFK(segmentFK);

        return contractSetupVO;
    }

    private ClientSetupVO createOrUpdateClientSetup(ClientSetupVO clientSetupVO, EDITTrxVO editTrxVO)
    {
        if (clientSetupVO == null)
        {
            clientSetupVO = new ClientSetupVO();
            clientSetupVO.setClientSetupPK(0);
            clientSetupVO.setContractSetupFK(0);

            clientSetupVO.addEDITTrxVO(editTrxVO);
        }

        return clientSetupVO;
    }

    private ScheduledEventVO createOrUpdateScheduledEventVO(String startDate, String stopDate, String frequency,
                                                            String costOfLivingInd, String lifeContingent,
                                                            ScheduledEventVO scheduledEventVO)
    {
        if (scheduledEventVO == null)
        {
            scheduledEventVO = new ScheduledEventVO();
            scheduledEventVO.setScheduledEventPK(0);
            scheduledEventVO.setGroupSetupFK(0);
        }

        scheduledEventVO.setCostOfLivingInd(costOfLivingInd);
        scheduledEventVO.setFrequencyCT(frequency);
        scheduledEventVO.setLifeContingentCT(lifeContingent);
        scheduledEventVO.setStartDate(startDate);
        scheduledEventVO.setStopDate(stopDate);

        return scheduledEventVO;
    }

    private EDITTrxVO createOrUpdateEDITTrx(long editTrxPK, String transactionType, String startDate,
                                            String sequenceNumber, String taxYear, String amount, EDITTrxVO editTrxVO)
    {
        if (editTrxVO == null)
        {
            editTrxVO = new EDITTrxVO();
            editTrxVO.setEDITTrxPK(editTrxPK);
            editTrxVO.setClientSetupFK(0);
        }

        editTrxVO.setTransactionTypeCT(transactionType);
        editTrxVO.setEffectiveDate(startDate);
        editTrxVO.setDueDate(startDate);
        editTrxVO.setSequenceNumber(Integer.parseInt(sequenceNumber));
        if (Util.isANumber(taxYear))
        {
            editTrxVO.setTaxYear(Integer.parseInt(taxYear));
        }
        else
        {
            editTrxVO.setTaxYear(Integer.parseInt(startDate.substring(0, 4)));
        }

        if (Util.isANumber(amount))
        {
            editTrxVO.setTrxAmount(new EDITBigDecimal(amount).getBigDecimal());
        }

        return editTrxVO;
    }

    private void updatePendingTransactionList(EDITTrxVO[] pendingTransactions, EDITTrxVO editTrxVO, AppReqBlock appReqBlock)
    {
        List updatedPendingTransactions = new ArrayList();

        boolean trxFound = false;

        if (pendingTransactions != null)
        {
            for (int i = 0; i < pendingTransactions.length; i++)
            {
                if (pendingTransactions[i].getEDITTrxPK() == editTrxVO.getEDITTrxPK())
                {
                    updatedPendingTransactions.add(editTrxVO);
                    trxFound = true;
                }
                else
                {
                    updatedPendingTransactions.add(pendingTransactions[i]);
                }
            }
        }

        if (!trxFound)
        {
            updatedPendingTransactions.add(editTrxVO);
        }

        EDITTrxVO[] editTrxVOs = (EDITTrxVO[]) updatedPendingTransactions.toArray(new EDITTrxVO[updatedPendingTransactions.size()]);

        appReqBlock.getHttpSession().setAttribute("pendingTransactions", editTrxVOs);
    }

    protected String showTransactionDetailSummary(AppReqBlock appReqBlock) throws Exception
    {
        SessionBean stateBean = appReqBlock.getSessionBean("quoteStateBean");
        String currentPage = stateBean.getValue("currentPage");

        savePreviousPageFormBean(appReqBlock, currentPage);

        FormBean formBean = appReqBlock.getFormBean();

        String selectedTransaction = formBean.getValue("selectedTransactionId");
        String selectedOption = formBean.getValue("selectedOptionId");
        String selectedFrequency = formBean.getValue("selectedFrequencyId");
        String selectedEffectiveDate = formBean.getValue("selectedEffectiveDate");

        String key = selectedTransaction + selectedOption + selectedFrequency + selectedEffectiveDate;

        PageBean txBean = appReqBlock.getSessionBean("quoteTransactions").getPageBean(key);
        txBean.putValue("oldOptionId", selectedOption);
        txBean.putValue("oldFrequencyId", selectedFrequency);
        txBean.putValue("oldStartDate", selectedEffectiveDate);

        appReqBlock.getSessionBean("quoteMainSessionBean").putPageBean("transactionFormBean", txBean);

        new QuoteScheduledEventTableModel(appReqBlock);

        return QUOTE_SCHED_EVENTS;
    }

    protected String cancelTransaction(AppReqBlock appReqBlock) throws Exception
    {
        String transactionId = appReqBlock.getFormBean().getValue("transactionId");

        PageBean transactionBean = appReqBlock.getSessionBean("quoteTransactions").getPageBean(transactionId);

        appReqBlock.getSessionBean("quoteMainSessionBean").putPageBean("transactionFormBean", transactionBean);

        new QuoteScheduledEventTableModel(appReqBlock);

        return QUOTE_SCHED_EVENTS;
    }

    protected String showDatesDialog(AppReqBlock appReqBlock) throws Exception
    {
        SessionBean stateBean = appReqBlock.getSessionBean("quoteStateBean");
        String currentPage = stateBean.getValue("currentPage");
        savePreviousPageFormBean(appReqBlock, currentPage);

        return QUOTE_COMMIT_DATES_DIALOG;
    }

    protected String saveTaxes(AppReqBlock appReqBlock) throws Exception
    {
        SessionBean quoteTaxesSessionBean = appReqBlock.getSessionBean("quoteTaxesSessionBean");
        PageBean quoteMainFormBean = appReqBlock.getSessionBean("quoteMainSessionBean").getPageBean("formBean");
        String optionId = quoteMainFormBean.getValue("optionId");
        PageBean formBean = appReqBlock.getFormBean();

        String exchangeInd = formBean.getValue("exchangeIndStatus");
        String taxReportingGroup = formBean.getValue("taxReportingGroup");

        quoteTaxesSessionBean.putValue("exchangeIndStatus", exchangeInd);
        quoteTaxesSessionBean.putValue("taxReportingGroup", taxReportingGroup);

        if (exchangeInd.equalsIgnoreCase("Y") || !taxReportingGroup.equals(""))
        {
            quoteMainFormBean.putValue("taxesIndStatus", "checked");
        }
        else
        {
            quoteMainFormBean.putValue("taxesIndStatus", "unchecked");
        }

        String productType = checkProductType(optionId);

        return getMainReturnPage(productType);
    }

    protected String updateScheduledPremium(AppReqBlock appReqBlock) throws Exception
    {
        // PageBean quoteMainFormBean = appReqBlock.getSessionBean("quoteMainSessionBean").getPageBean("formBean");
        PageBean formBean = appReqBlock.getFormBean();

        EDITBigDecimal scheduledPremiumAmount = new EDITBigDecimal(formBean.getValue("scheduledPremiumAmount"));
        EDITDate effectiveDate = new EDITDate(formBean.getValue("effectiveDate"));
    	String conversionValue = "SchedPrem";
        String operator = appReqBlock.getUserSession().getUsername();
    	        
        QuoteVO quoteVO = buildQuoteVO(appReqBlock);
        SegmentVO segmentVO = quoteVO.getSegmentVO(0);
        Segment segment = (Segment) SessionHelper.map(segmentVO, SessionHelper.EDITSOLUTIONS);
        
        // test these to see if all are needed ??? :
        segment.setOperator(operator);
        segmentVO.setSegmentPK(segment.getSegmentPK().longValue());
        segment.setVO(segmentVO);
        
        // do we need this? ... I think the changes will be abandoned on re-load so this is probably unnecessary??
        // cancelQuote(appReqBlock);
        
        EDITTrx.createBillingChangeTrxGroupSetup(segment, operator, conversionValue, effectiveDate, scheduledPremiumAmount);
        
        UserSession userSession = appReqBlock.getUserSession();
        userSession.setDepositsVO(null); // not sure if this is needed?
        userSession.unlockSegment();
        
        appReqBlock.getSessionBean("quoteMainSessionBean").putValue("quoteMessage", "Transaction Complete");
        appReqBlock.getFormBean().putValue("segmentPK", String.valueOf(segmentVO.getSegmentPK()));
        
        return loadQuote(appReqBlock);
    }
    
    protected String saveScheduledPremium(AppReqBlock appReqBlock) throws Exception {

	    PageBean formBean = appReqBlock.getFormBean();
	
	    EDITBigDecimal scheduledPremium = new EDITBigDecimal(formBean.getValue("scheduledPremiumAmount"));
	    EDITDate effectiveDate = new EDITDate(formBean.getValue("effectiveDate"));
	    String operator = appReqBlock.getUserSession().getUsername();
		        
	    QuoteVO quoteVO = buildQuoteVO(appReqBlock); 
    
	    PremiumDue premiumDue = new PremiumDue();
    
		CommissionPhase commissionPhase = new CommissionPhase();
		commissionPhase.setCommissionPhaseID(1);
		commissionPhase.setCommissionTarget(new EDITBigDecimal("0.00"));
		commissionPhase.setEffectiveDate(effectiveDate);
		commissionPhase.setExpectedMonthlyPremium(new EDITBigDecimal("0.00"));
		commissionPhase.setPriorExpectedMonthlyPremium(new EDITBigDecimal("0.00"));
	
		premiumDue.addCommissionPhase(commissionPhase);
		premiumDue.setEffectiveDate(effectiveDate);
		premiumDue.setPendingExtractIndicator(PremiumDue.PENDING_EXTRACT_P);
		
		BillScheduleVO billScheduleVO = quoteVO.getBillScheduleVO();
		String billTypeCT = billScheduleVO.getBillTypeCT();
		String billingModeCT = billScheduleVO.getBillingModeCT();
		String deductionFrequencyCT = billScheduleVO.getDeductionFrequencyCT();
	
		if (billTypeCT.equals("GRP")) {
			if (billingModeCT.equals("Annual") || billingModeCT.equals("SemiAnn") || billingModeCT.equals("Quart")) {
				premiumDue.setBillAmount(scheduledPremium);
				premiumDue.setDeductionAmount(scheduledPremium);
				premiumDue.setNumberOfDeductions(1);
			} else if (billingModeCT.equals("Month")) {
				if (deductionFrequencyCT.equals("09") || deductionFrequencyCT.equals("10")
						|| deductionFrequencyCT.equals("12")) {
					premiumDue.setBillAmount(scheduledPremium);
					premiumDue.setDeductionAmount(scheduledPremium);
					premiumDue.setNumberOfDeductions(1);
				} else if (deductionFrequencyCT.equals("18") || deductionFrequencyCT.equals("20")
						|| deductionFrequencyCT.equals("24")) {
					premiumDue.setBillAmount(scheduledPremium.multiplyEditBigDecimal(new BigDecimal(2.00)));
					premiumDue.setDeductionAmount(scheduledPremium);
					premiumDue.setNumberOfDeductions(2);
				} else if (deductionFrequencyCT.equals("36") || deductionFrequencyCT.equals("40")
						|| deductionFrequencyCT.equals("48")) {
					premiumDue.setBillAmount(scheduledPremium.multiplyEditBigDecimal(new BigDecimal(4.00)));
					premiumDue.setDeductionAmount(scheduledPremium);
					premiumDue.setNumberOfDeductions(4);
				}
			} else if (billingModeCT.equals("VarMonth")) {
				premiumDue.setBillAmount(new EDITBigDecimal("0.00"));
				premiumDue.setDeductionAmount(scheduledPremium);
				premiumDue.setNumberOfDeductions(0);
			} else if (billingModeCT.equals("13thly")) {
				if (deductionFrequencyCT.equals("26")) {
					premiumDue.setBillAmount(scheduledPremium.multiplyEditBigDecimal(new BigDecimal(2.00)));
					premiumDue.setDeductionAmount(scheduledPremium);
					premiumDue.setNumberOfDeductions(2);
				} else if (deductionFrequencyCT.equals("52")) {
					premiumDue.setBillAmount(scheduledPremium.multiplyEditBigDecimal(new BigDecimal(4.00)));
					premiumDue.setDeductionAmount(scheduledPremium);
					premiumDue.setNumberOfDeductions(4);
				}
			}
		} else if (billTypeCT.equals("INDIV")) {
			premiumDue.setBillAmount(scheduledPremium);
			premiumDue.setDeductionAmount(new EDITBigDecimal("0.00"));
			premiumDue.setNumberOfDeductions(0);
		}
		
		commissionPhase.setPremiumDue(premiumDue);
		
		try {
			SessionHelper.beginTransaction(SessionHelper.EDITSOLUTIONS);
	
			PremiumDue[] premiumDues = PremiumDue.findBySegmentPK(quoteVO.getSegmentVO(0).getSegmentPK());
	        if (premiumDues != null) {
                // reverse all existing PDs
                for (PremiumDue premDue : premiumDues)  {
                	premDue.setPendingExtractIndicator("R");
                	SessionHelper.saveOrUpdate(premDue, SessionHelper.EDITSOLUTIONS);
                }
	        }
	        
	        Segment segment = Segment.findByPK(new Long(quoteVO.getSegmentVO(0).getSegmentPK()));
			segment.addPremiumDue(premiumDue);
			
			SessionHelper.saveOrUpdate(segment, SessionHelper.EDITSOLUTIONS);
  
			SessionHelper.commitTransaction(SessionHelper.EDITSOLUTIONS);
	
		} catch (Exception e) {
		    System.out.println(e);
		
		    e.printStackTrace();
		
		    SessionHelper.rollbackTransaction(SessionHelper.EDITSOLUTIONS);
		} finally {
		    SessionHelper.clearSessions();
		}
		
        return loadQuote(appReqBlock);
	}

    protected String showNotesDialog(AppReqBlock appReqBlock) throws Exception
    {
        SessionBean stateBean = appReqBlock.getSessionBean("quoteStateBean");
        String currentPage = stateBean.getValue("currentPage");
        savePreviousPageFormBean(appReqBlock, currentPage);

        SessionBean quoteNotesSessionBean = appReqBlock.getSessionBean("quoteNotesSessionBean");
        appReqBlock.addSessionBean("quoteNotes", (SessionBean) Util.deepClone(quoteNotesSessionBean));

        return QUOTE_COMMIT_NOTES_DIALOG;
    }

    protected String showNotesDetailSummary(AppReqBlock appReqBlock) throws Exception
    {
        SessionBean quoteNotesSessionBean = appReqBlock.getSessionBean("quoteNotesSessionBean");
        PageBean formBean = appReqBlock.getFormBean();

        String key = formBean.getValue("key");

        PageBean noteBean = quoteNotesSessionBean.getPageBean(key);

        quoteNotesSessionBean.putPageBean("formBean", noteBean);

        return QUOTE_COMMIT_NOTES_DIALOG;
    }

    protected String cancelNotes(AppReqBlock appReqBlock) throws Exception
    {
        PageBean quoteMainFormBean = appReqBlock.getSessionBean("quoteMainSessionBean").getPageBean("formBean");
        String optionId = quoteMainFormBean.getValue("optionId");

        SessionBean quoteNotes = appReqBlock.getSessionBean("quoteNotes");
        appReqBlock.addSessionBean("quoteNotesSessionBean", quoteNotes);

        NewBusinessNotesUtil.clearNotesDeleteSessionBean(appReqBlock);
        NewBusinessNotesUtil.setNotesIndicator(appReqBlock);

        String productType = checkProductType(optionId);

        return getMainReturnPage(productType);
    }

    protected String saveNoteToSummary(AppReqBlock appReqBlock) throws Exception
    {
        new NewBusinessUseCaseComponent().updateNewBusinessNotes();
        CodeTableWrapper codeTableWrapper = CodeTableWrapper.getSingleton();

        SessionBean quoteNotesSessionBean = appReqBlock.getSessionBean("quoteNotesSessionBean");
        PageBean formBean = appReqBlock.getFormBean();

        String key = formBean.getValue("key");
        String noteTypeId = Util.initString(formBean.getValue("noteTypeId"), "");
        String noteQualifierId = Util.initString(formBean.getValue("noteQualifierId"), "");
        String sequence = formBean.getValue("sequence");
        String noteReminderPK = formBean.getValue("noteReminderPK");
        String segmentFK = formBean.getValue("segmentFK");

        if (noteReminderPK.equals("0") || noteReminderPK.equals(""))
        {
            contract.business.Contract contractComponent = new contract.component.ContractComponent();
            noteReminderPK = (contractComponent.getNextAvailableKey() * -1) + "";
        }

        if (Util.isANumber(noteTypeId))
        {
            noteTypeId = codeTableWrapper.getCodeTableEntry(Long.parseLong(noteTypeId)).getCode();
        }

        if (Util.isANumber(noteQualifierId))
        {
            noteQualifierId = codeTableWrapper.getCodeTableEntry(Long.parseLong(noteQualifierId)).getCode();
        }

        String operator = appReqBlock.getUserSession().getUsername();

        formBean.putValue("operator", operator);
        formBean.putValue("noteTypeId", noteTypeId);
        formBean.putValue("noteQualifierId", noteQualifierId);
        if (!Util.isANumber(sequence))
        {
            sequence = setNoteReminderSequenceNumber(quoteNotesSessionBean);
        }

        formBean.putValue("sequence", sequence);

        EDITDateTime maintDateTime = new EDITDateTime();

        formBean.putValue("maintDate", maintDateTime.getFormattedDateTime());

        if (key.equals(""))
        {
            key = noteReminderPK + sequence + noteTypeId + noteQualifierId;
            formBean.putValue("key", key);
        }

        formBean.putValue("isNoteReminderChanged", "true");
        formBean.putValue("segmentFK", segmentFK);

        quoteNotesSessionBean.putPageBean(key, formBean);
        quoteNotesSessionBean.putPageBean("formBean", formBean);

        PageBean quoteMainFormBean = appReqBlock.getSessionBean("quoteMainSessionBean").getPageBean("formBean");
        quoteMainFormBean.putValue("notesIndStatus", "checked");

        return QUOTE_COMMIT_NOTES_DIALOG;
    }

    protected String saveNotes(AppReqBlock appReqBlock) throws Exception
    {
        PageBean quoteMainFormBean = appReqBlock.getSessionBean("quoteMainSessionBean").getPageBean("formBean");
        String optionId = quoteMainFormBean.getValue("optionId");

        SessionBean quoteNotesSessionBean = appReqBlock.getSessionBean("quoteNotesSessionBean");

        quoteNotesSessionBean.removePageBean("formBean");

        String productType = checkProductType(optionId);

        return getMainReturnPage(productType);
    }

    protected String deleteCurrentNote(AppReqBlock appReqBlock) throws Exception
    {
        new NewBusinessUseCaseComponent().updateNewBusinessNotes();
        SessionBean quoteNotesSessionBean = appReqBlock.getSessionBean("quoteNotesSessionBean");
        PageBean formBean = appReqBlock.getFormBean();

        String key = formBean.getValue("key");

        quoteNotesSessionBean.removePageBean(key);

        quoteNotesSessionBean.putPageBean("formBean", new PageBean());

        return QUOTE_COMMIT_NOTES_DIALOG;
    }

    protected String autoGenerateContractNumber(AppReqBlock appReqBlock) throws Exception
    {
        String optionId = appReqBlock.getFormBean().getValue("optionId");

        String productStructureId = Util.initString(appReqBlock.getFormBean().getValue("companyStructureId"), null);
        String productStructure = appReqBlock.getFormBean().getValue("companyStructure");

        // set the found contract's product structure in the session as the current one
        if (productStructureId != null)
        {
            ProductStructure.setSecurityCurrentProdStructInSession(
                            appReqBlock,
                            Long.parseLong(productStructureId));
        }

        autoGenerate(appReqBlock);

        appReqBlock.getFormBean().putValue("optionId", optionId);
        appReqBlock.getFormBean().putValue("companyStructure", productStructure);
        appReqBlock.getFormBean().putValue("companyStructureId", productStructureId);

        return SELECT_COVERAGE_DIALOG;
    }

    protected String autoGenContractNbrForIssue(AppReqBlock appReqBlock) throws Exception
    {
        autoGenerate(appReqBlock);

        getSuspenseInformation(appReqBlock);

        return ISSUE_CONTRACT_DIALOG;
    }

    private void autoGenerate(AppReqBlock appReqBlock) throws Exception
    {
        contract.business.Contract contract = new contract.component.ContractComponent();
        PageBean formBean = appReqBlock.getFormBean();
        String target = formBean.getValue("contractTarget");

        String productStructure = appReqBlock.getSessionBean("quoteMainSessionBean").
                getPageBean("formBean").getValue("companyStructure");

        StringTokenizer st = new StringTokenizer(productStructure);

        String businessContract = "";

        // Grab the last token by just looping through to the end
        while (st.hasMoreTokens())
        {
            businessContract = st.nextToken();
        }

        String contractNumber = contract.autoGenerateContractNumber(businessContract);

        formBean.putValue("contractNumber", contractNumber);

        appReqBlock.getHttpServletRequest().setAttribute("formBean", formBean);
        appReqBlock.getHttpServletRequest().setAttribute("contractTarget", target);
    }


    protected String showEditingExceptionDialog(AppReqBlock appReqBlock) throws Exception
    {
        PortalEditingException editingException = (PortalEditingException) appReqBlock.getHttpSession().getAttribute("portalEditingException");

        // Remove editingException from Session (to clear it), and move it to request scope.
        appReqBlock.getHttpSession().removeAttribute("portalEditingException");

        appReqBlock.getHttpServletRequest().setAttribute("portalEditingException", editingException);

        return EDITING_EXCEPTION_DIALOG;
    }

    /**
     * Builds the QuoteVO (which contains "everything") based on information from the screen
     *
     * This is when enter is pressed on quoteCommitContractNumberDialog
     *
     * @param appReqBlock
     *
     * @return Built VO
     *
     * @throws Exception
     */
    private QuoteVO buildQuoteVO(AppReqBlock appReqBlock) throws Exception
    {
        QuoteVO quoteVO = new QuoteVO();

        quoteVO.setOperator(appReqBlock.getUserSession().getUsername());

        //  Get optionCode
        String optionCode = getOptionCodeForBuild(appReqBlock);

        // *********************************************** //
        // A. Build all the VOs without attaching them     //
        // *********************************************** //

        SegmentVO baseSegmentVO   = buildBaseSegment(appReqBlock, optionCode, quoteVO);

        Map riderVOs              = buildRiders(appReqBlock, baseSegmentVO);

        Map clientRelationshipVOs = buildClientRelationships(appReqBlock, baseSegmentVO, riderVOs);

        Map fundVOs               = buildInvestments(appReqBlock, optionCode);

        Map notesVOs              = contract.util.NewBusinessNotesUtil.loadNoteReminderVOsFromSession(appReqBlock,false);

        // *********************************************** //
        // B. Now attach the VOs to each other as necessary //
        // *********************************************** //
        attachVOsForBuild(appReqBlock, baseSegmentVO, optionCode, clientRelationshipVOs, riderVOs, fundVOs, notesVOs);

        //***********************************************//
        // C. Build ClientDetailVOs                      //
        //***********************************************//
        buildClientDetails(appReqBlock, quoteVO);

        //  Add Segment to quoteVO
        quoteVO.addSegmentVO(baseSegmentVO);

         //  Get the BillScheduleVO from session.  Update the Segment to point to this VO.  Set the BillScheduleVO in the quoteVO
        //  Only group-based contracts will have a BillSchedule
        BillScheduleVO billScheduleVO = (BillScheduleVO) appReqBlock.getHttpSession().getAttribute("BillScheduleVO");

        if (billScheduleVO != null)
        {
            if (billScheduleVO.getBillSchedulePK() == baseSegmentVO.getBillScheduleFK())
            {
                baseSegmentVO.setBillScheduleFK(billScheduleVO.getBillSchedulePK());
                quoteVO.setBillScheduleVO(billScheduleVO);
            }
        }

        return quoteVO;
    }

    /**
     * Gets the option code from persistence that corresponds to the option id on the screen
     *
     * @param appReqBlock
     *
     * @return option code
     */
    private String getOptionCodeForBuild(AppReqBlock appReqBlock)
    {
        PageBean quoteMainFormBean = appReqBlock.getSessionBean("quoteMainSessionBean").getPageBean("formBean");

        String optionCode = quoteMainFormBean.getValue("optionId");

        CodeTableWrapper codeTableWrapper = CodeTableWrapper.getSingleton();

        if (Util.isANumber(optionCode))
        {
            optionCode = codeTableWrapper.getCodeTableEntry(Long.parseLong(optionCode)).getCode();
        }

        return optionCode;
    }

    /**
     * Builds the base SegmentVO based on information from the screen
     *
     * @param appReqBlock
     * @param optionCode
     * @param quoteVO
     *
     * @return Built VO
     */
    private SegmentVO buildBaseSegment(AppReqBlock appReqBlock, String optionCode, QuoteVO quoteVO)
    {
        PageBean quoteMainFormBean = appReqBlock.getSessionBean("quoteMainSessionBean").getPageBean("formBean");
        SessionBean quoteTaxesSessionBean = appReqBlock.getSessionBean("quoteTaxesSessionBean");

        String segmentPK = quoteMainFormBean.getValue("segmentPK");
        String contractGroupFK = Util.initString(quoteMainFormBean.getValue("contractGroupFK"), "0");
        String billScheduleFK = Util.initString(quoteMainFormBean.getValue("billScheduleFK"), "0");
        String departmentLocationFK = Util.initString(quoteMainFormBean.getValue("departmentLocationFK"), "0");
        String priorContractGroupFK = Util.initString(quoteMainFormBean.getValue("priorContractGroupFK"), "0");
        String batchContractSetupFK = Util.initString(quoteMainFormBean.getValue("batchContractSetupFK"), "0");
        String masterContractFK = Util.initString(quoteMainFormBean.getValue("masterContractFK"), "0");
        String priorPRDDue = Util.initString(quoteMainFormBean.getValue("priorPRDDue"), null);
        String billScheduleChangeType = Util.initString(quoteMainFormBean.getValue("billScheduleChangeType"), null);
//        String groupNumber = Util.initString(quoteMainFormBean.getValue("groupNumber"), null);

        //Since the contractId is entered on Add of NewBusiness but can be changed on Save and Issue, get
        //the contract number from AppReqBlock
        String contractId = appReqBlock.getReqParm("contractNumber");

        if (contractId != null && contractId.equals(""))
        {
            contractId = appReqBlock.getSessionBean("quoteStateBean").getValue("contractNumber");
        }

        String effectiveDate = quoteMainFormBean.getValue("effectiveDate");
        effectiveDate = DateTimeUtil.formatMMDDYYYYToYYYYMMDD(effectiveDate);//change the format back to yyyy/mm/dd

        String statusCode = quoteMainFormBean.getValue("statusCode");
        String productStructureId = quoteMainFormBean.getValue("companyStructureId");
        
        String appSignedDate = quoteMainFormBean.getValue("applicationSignedDate");
        appSignedDate = DateTimeUtil.formatMMDDYYYYToYYYYMMDD(appSignedDate);//change the format back to yyyy/mm/dd
        
        String appReceivedDate = quoteMainFormBean.getValue("applicationReceivedDate");
        appReceivedDate = DateTimeUtil.formatMMDDYYYYToYYYYMMDD(appReceivedDate);//change the format back to yyyy/mm/dd

        String creationDate = DateTimeUtil.formatMMDDYYYYToYYYYMMDD(quoteMainFormBean.getValue("creationDate"));
        String creationOperator = quoteMainFormBean.getValue("creationOperator");

        String expiryDate = quoteMainFormBean.getValue("expiryDate");
        expiryDate = DateTimeUtil.formatMMDDYYYYToYYYYMMDD(expiryDate);//change the format back to yyyy/mm/dd
        
        
        String dividendOptionCT = Util.initString(quoteMainFormBean.getValue("dividendOptionCT"), null);
        if (dividendOptionCT != null && dividendOptionCT.equalsIgnoreCase("Please Select"))
        {
            dividendOptionCT = null;
        }

        String cashWithAppIndStatus = quoteMainFormBean.getValue("cashWithAppIndStatus");

        if (cashWithAppIndStatus.equalsIgnoreCase("checked"))
        {
            cashWithAppIndStatus = "Y";
        }
        else
        {
            cashWithAppIndStatus = "N";
        }

        String quoteDate = DateTimeUtil.formatMMDDYYYYToYYYYMMDD(quoteMainFormBean.getValue("quoteDate"));

         if (quoteDate == null)
        {
            //  use today's date
            quoteDate = new EDITDate().getFormattedDate();

            quoteMainFormBean.putValue("quoteDate", DateTimeUtil.formatYYYYMMDDToMMDDYYYY(quoteDate));
        }


        String issueState = quoteMainFormBean.getValue("areaId");
        String ratedGenderCT = Util.initString(quoteMainFormBean.getValue("ratedGenderCT"), null);
        String underwritingClass = Util.initString(quoteMainFormBean.getValue("underwritingClass"), null);
        String groupPlan = Util.initString(quoteMainFormBean.getValue("groupPlan"), null);
        String costBasis = Util.initString(quoteMainFormBean.getValue("costBasis"), "0");
        String purchaseAmount = Util.initString(quoteMainFormBean.getValue("purchaseAmount"), "0");
        String savingsPercent = Util.initString(quoteMainFormBean.getValue("savingsPercent"), "0");
        String dismembermentPercent = Util.initString(quoteMainFormBean.getValue("dismembermentPercent"), "0");
        String premiumTaxes = Util.initString(quoteMainFormBean.getValue("premiumTaxes"), "0");
        String frontEndLoads = Util.initString(quoteMainFormBean.getValue("frontEndLoads"), "0");
        String fees = Util.initString(quoteMainFormBean.getValue("fees"), "0");
        String qualifiedType = quoteMainFormBean.getValue("qualifiedType");
        String taxReportingGroup = quoteTaxesSessionBean.getValue("taxReportingGroup");
        String dateInEffectDate     = quoteMainFormBean.getValue("dateInEffect");
        dateInEffectDate = DateTimeUtil.formatMMDDYYYYToYYYYMMDD(dateInEffectDate);//change the format back to yyyy/mm/dd

        String issueStateORIndStatus = quoteMainFormBean.getValue("issueStateORIndStatus");
        String issueStateORInd = "N";

        if (issueStateORIndStatus.equalsIgnoreCase("checked"))
        {
            issueStateORInd = "Y";
        }

        String waiverInEffectStatus = quoteMainFormBean.getValue("waiverInEffectStatus");
        String waiverInEffect = "N";

        if (waiverInEffectStatus.equalsIgnoreCase("checked"))
        {
            waiverInEffect = "Y";
        }

        //CheckBox Ind
        String qualNonQual = quoteMainFormBean.getValue("qualNonQual");
        String exchangeInd = quoteTaxesSessionBean.getValue("exchangeIndStatus");

        if (exchangeInd.equals("checked"))
        {
            exchangeInd = "Y";
        }
        else
        {
            exchangeInd = "N";
        }

        if (statusCode.equals(""))
        {
            statusCode = "Pending";
        }


        String waiveFreeLookIndicator = quoteMainFormBean.getValue("waiveFreeLookIndicatorStatus");

        if ( waiveFreeLookIndicator.equalsIgnoreCase("checked") )
        {
            waiveFreeLookIndicator = "Y";
        }
        else
        {
            waiveFreeLookIndicator = "N";
        }

        String freeLookDaysOverride = quoteMainFormBean.getValue("freeLookDaysOverride");
        String dialableSalesLoadPct = Util.initString(quoteMainFormBean.getValue("dialableSalesLoadPct"), "0");
        String chargeDeductDivisionIndStatus = Util.initString(quoteMainFormBean.getValue("chargeDeductDivisionIndStatus"), null);
        String pointInScaleIndStatus = Util.initString(quoteMainFormBean.getValue("pointInScaleIndStatus"), null);
        String commitmentIndicatorStatus = Util.initString(quoteMainFormBean.getValue("commitmentIndicatorStatus"), null);
        String commitmentAmount = Util.initString(quoteMainFormBean.getValue("commitmentAmount"), "0");
        String premiumTaxSitusOverride = Util.initString(quoteMainFormBean.getValue("premiumTaxSitusOverride"), null);
        String suppOriginalContractNumber = Util.initString(quoteMainFormBean.getValue("suppOriginalContractNumber"), null);
        String casetrackingOptionCT = Util.initString(quoteMainFormBean.getValue("casetrackingOptionCT"), null);
        String contractTypeCT = Util.initString(quoteMainFormBean.getValue("contractType"), null);

        String units = Util.initString(quoteMainFormBean.getValue("units"), "0");
        String commissionPhaseID = Util.initString(quoteMainFormBean.getValue("commissionPhaseID"), "1");
        String commissionPhaseOverride = Util.initString(quoteMainFormBean.getValue("commissionPhaseOverride"), null);

        String totalFaceAmount = Util.initString(quoteMainFormBean.getValue("totalFaceAmount"), "0");
        String annualPremium = Util.initString(quoteMainFormBean.getValue("annualPremium"), "0");
        String applicationState = Util.initString(quoteMainFormBean.getValue("applicationState"), null);
        String firstNotifyDate = DateTimeUtil.formatMMDDYYYYToYYYYMMDD(quoteMainFormBean.getValue("firstNotifyDate"));
        String ageAtIssue = Util.initString(quoteMainFormBean.getValue("ageAtIssue"), "0");
        String originalStateCT = Util.initString(quoteMainFormBean.getValue("originalStateCT"), null);
        String sequence = quoteMainFormBean.getValue("sequence");
        String location = quoteMainFormBean.getValue("location");
        BigDecimal indivAnnPremium = null;
        if ((quoteMainFormBean.getValue("indivAnnPremium")) != null || (!quoteMainFormBean.getValue("indivAnnPremium").isEmpty())) {
            indivAnnPremium = new EDITBigDecimal(quoteMainFormBean.getValue("indivAnnPremium")).getBigDecimal();
        }
//        String amount = Util.initString(quoteMainFormBean.getValue("amount"), "0");
        String worksheetTypeCT = quoteMainFormBean.getValue("worksheetTypeCT");
        if(worksheetTypeCT == null || worksheetTypeCT.equals(""))
        {
            worksheetTypeCT = "Correction";
        }
        String previousNotifyDate = DateTimeUtil.formatMMDDYYYYToYYYYMMDD(quoteMainFormBean.getValue("previousNotifyDate"));
        String finalNotifyDate = DateTimeUtil.formatMMDDYYYYToYYYYMMDD(quoteMainFormBean.getValue("finalNotifyDate"));
        String advanceFinalNotify = Util.initString(quoteMainFormBean.getValue("advanceFinalNotify"), null);
        String requirementEffectiveDate = DateTimeUtil.formatMMDDYYYYToYYYYMMDD(quoteMainFormBean.getValue("requirementEffectiveDate"));

        String deductionAmountOverride = Util.initString(quoteMainFormBean.getValue("deductionAmountOverride"), "0");
        String deductionAmountEffectiveDate = Util.initString(quoteMainFormBean.getValue("deductionAmountEffectiveDate"), null);
        
        
        deductionAmountEffectiveDate = DateTimeUtil.formatMMDDYYYYToYYYYMMDD(deductionAmountEffectiveDate);

        SegmentVO segmentVO = new SegmentVO();

        if (!segmentPK.equals(""))
        {
            segmentVO.setSegmentPK(Long.parseLong(segmentPK));
        }
        else
        {
            segmentVO.setSegmentPK(0);
        }

//        if (!masterFK.equals("") && !masterFK.equals("0"))
//        {
//            segmentVO.setMasterFK(Long.parseLong(masterFK));
//            contract.business.Lookup contractLookup = new contract.component.LookupComponent();
//            MasterVO[] masterVO = contractLookup.findMasterByMasterPK(Long.parseLong(masterFK));
//            segmentVO.setParentVO(MasterVO.class, masterVO[0]);
//        }

        if (!contractGroupFK.equals("0"))
        {
            segmentVO.setContractGroupFK(Long.parseLong(contractGroupFK));
        }

        if (!billScheduleFK.equals("0"))
        {
            segmentVO.setBillScheduleFK(Long.parseLong(billScheduleFK));
        }

        if (!batchContractSetupFK.equals("0"))
        {
            segmentVO.setBatchContractSetupFK(Long.parseLong(batchContractSetupFK));
        }

        if (!masterContractFK.equals("0"))
        {
            segmentVO.setMasterContractFK(Long.parseLong(masterContractFK));
        }

        if (!departmentLocationFK.equals("0"))
        {
            segmentVO.setDepartmentLocationFK(Long.parseLong(departmentLocationFK));
        }

        if (!priorContractGroupFK.equals("0"))
        {
            segmentVO.setPriorContractGroupFK(Long.parseLong(priorContractGroupFK));
        }

        segmentVO.setPriorPRDDue(priorPRDDue);
        segmentVO.setBillScheduleChangeType(billScheduleChangeType);

        segmentVO.setContractNumber( Util.initString(contractId, null) );
        segmentVO.setProductStructureFK(Long.parseLong(productStructureId));

        String productType = checkProductType(optionCode);

        if (productType.equalsIgnoreCase(DEFERRED_ANNUITY))
        {
            segmentVO.setSegmentNameCT("DFA");
        }
        else if (productType.equalsIgnoreCase(NON_TRAD_LIFE))
        {
            segmentVO.setSegmentNameCT("Life");
        }
        else if (productType.equalsIgnoreCase(TRADITIONAL))
        {
            segmentVO.setSegmentNameCT("Traditional");
        }
        else if (productType.equalsIgnoreCase(UNIVERSAL_LIFE))
        {
            segmentVO.setSegmentNameCT("UL");
        }
        else if (productType.equalsIgnoreCase(AH)) 
        {
        	segmentVO.setSegmentNameCT("A&H");
        }
        else
        {
            segmentVO.setSegmentNameCT("Payout");
        }

        if (!statusCode.equalsIgnoreCase("Please Select"))
        {
            segmentVO.setSegmentStatusCT( Util.initString(statusCode, null) );
        }
        else
        {
            segmentVO.setSegmentStatusCT(appReqBlock.getSessionBean("quoteMainSessionBean").getValue("status"));
        }
        segmentVO.setEffectiveDate(effectiveDate);
        segmentVO.setLastAnniversaryDate(effectiveDate);
        segmentVO.setApplicationSignedDate(appSignedDate);
        segmentVO.setApplicationReceivedDate(appReceivedDate);
        segmentVO.setCashWithAppInd(cashWithAppIndStatus);
        segmentVO.setOptionCodeCT( Util.initString(optionCode, null) );
        segmentVO.setDividendOptionCT(dividendOptionCT);
        segmentVO.setRatedGenderCT(ratedGenderCT);
        segmentVO.setUnderwritingClassCT(underwritingClass);
        segmentVO.setGroupPlan(groupPlan);
        segmentVO.setExpiryDate(expiryDate);
        segmentVO.setSequence(Util.initString(sequence, null));
        segmentVO.setLocation(Util.initString(location, null));
        segmentVO.setIndivAnnPremium(indivAnnPremium);
        
        if (!purchaseAmount.equals("0"))
        {
            //double doubleAmount = Double.parseDouble(purchaseAmount);
            EDITBigDecimal doubleAmount = new EDITBigDecimal(purchaseAmount);
            segmentVO.setAmount(doubleAmount.getBigDecimal());
        }
        else
        {
            segmentVO.setAmount(new EDITBigDecimal().getBigDecimal());
        }

        if (Util.isANumber(savingsPercent))
        {
            EDITBigDecimal doubleAmount = new EDITBigDecimal(savingsPercent);
            segmentVO.setSavingsPercent(doubleAmount.getBigDecimal());
        }
        else
        {
            segmentVO.setSavingsPercent(new EDITBigDecimal().getBigDecimal());
        }

        if (Util.isANumber(dismembermentPercent))
        {
            EDITBigDecimal doubleAmount = new EDITBigDecimal(dismembermentPercent);
            segmentVO.setDismembermentPercent(doubleAmount.getBigDecimal());
        }
        else
        {
            segmentVO.setDismembermentPercent(new EDITBigDecimal().getBigDecimal());
        }

        segmentVO.setIssueStateCT( Util.initString(issueState, null) );
        segmentVO.setQualNonQualCT( Util.initString(qualNonQual, null) );
        segmentVO.setExchangeInd(exchangeInd);

        if (!costBasis.equals(""))
        {
            //segmentVO.setCostBasis(Double.parseDouble(costBasis));
            segmentVO.setCostBasis(new EDITBigDecimal(costBasis).getBigDecimal());
        }
        else
        {
            segmentVO.setCostBasis(new EDITBigDecimal().getBigDecimal());
        }

        segmentVO.setRecoveredCostBasis(new EDITBigDecimal().getBigDecimal());
        segmentVO.setTerminationDate(EDITDate.DEFAULT_MAX_DATE);
        segmentVO.setQualifiedTypeCT( Util.initString(qualifiedType, null) );
        segmentVO.setTaxReportingGroup( Util.initString(taxReportingGroup, null) );
        segmentVO.setQuoteDate(quoteDate);
        quoteVO.setQuoteDate(quoteDate);

        segmentVO.setDateInEffect(dateInEffectDate);
        segmentVO.setWaiverInEffect(waiverInEffect);
        segmentVO.setIssueStateORInd(issueStateORInd);

        if (creationDate != null)
        {
            segmentVO.setCreationDate(creationDate);
            segmentVO.setCreationOperator(creationOperator);
        }

        if (!premiumTaxes.equals(""))
        {
            segmentVO.setCharges(new EDITBigDecimal(premiumTaxes).getBigDecimal());
        }
        else
        {
            segmentVO.setCharges(new EDITBigDecimal().getBigDecimal());
        }

        if (!frontEndLoads.equals(""))
        {
            segmentVO.setLoads(new EDITBigDecimal(frontEndLoads).getBigDecimal());
        }
        else
        {
            segmentVO.setLoads(new EDITBigDecimal().getBigDecimal());
        }

        if (!fees.equals(""))
        {
            segmentVO.setFees(new EDITBigDecimal(fees).getBigDecimal());
        }
        else
        {
            segmentVO.setFees(new EDITBigDecimal().getBigDecimal());
        }

        segmentVO.setFreeAmount(new EDITBigDecimal().getBigDecimal());
        segmentVO.setFreeAmountRemaining(new EDITBigDecimal().getBigDecimal());

        segmentVO.setWaiveFreeLookIndicator(waiveFreeLookIndicator);
        segmentVO.setFreeLookDaysOverride(Integer.parseInt( Util.initString(freeLookDaysOverride, "0") ) );
        segmentVO.setDialableSalesLoadPercentage(new EDITBigDecimal(dialableSalesLoadPct).getBigDecimal());

        if (chargeDeductDivisionIndStatus != null && chargeDeductDivisionIndStatus.equalsIgnoreCase("checked"))
        {
            segmentVO.setChargeDeductDivisionInd("Y");
        }
        if (pointInScaleIndStatus != null && pointInScaleIndStatus.equalsIgnoreCase("checked"))
        {
            segmentVO.setPointInScaleIndicator("Y");
        }

        if (commitmentIndicatorStatus != null && commitmentIndicatorStatus.equalsIgnoreCase("checked"))
        {
            segmentVO.setCommitmentIndicator("Y");
        }

        segmentVO.setCommitmentAmount(new EDITBigDecimal(commitmentAmount).getBigDecimal());
        
        segmentVO.setPremiumTaxSitusOverrideCT(premiumTaxSitusOverride);

        if (suppOriginalContractNumber != null && !suppOriginalContractNumber.equalsIgnoreCase(""))
        {
            segmentVO.setSuppOriginalContractNumber(suppOriginalContractNumber);
        }

        if (casetrackingOptionCT != null && !casetrackingOptionCT.equalsIgnoreCase(""))
        {
            segmentVO.setCasetrackingOptionCT(casetrackingOptionCT);
        }

        segmentVO.setContractTypeCT(contractTypeCT);

        segmentVO.setUnits(new EDITBigDecimal(units).getBigDecimal());
        segmentVO.setCommissionPhaseID(Integer.parseInt(commissionPhaseID));
        segmentVO.setCommissionPhaseOverride(commissionPhaseOverride);
        segmentVO.setTotalFaceAmount(new EDITBigDecimal(totalFaceAmount).getBigDecimal());
        segmentVO.setAnnualPremium(new EDITBigDecimal(annualPremium).getBigDecimal());
        segmentVO.setApplicationSignedStateCT(applicationState);
        segmentVO.setFirstNotifyDate(firstNotifyDate);
        segmentVO.setPreviousNotifyDate(previousNotifyDate);
        if (finalNotifyDate != null &&
            advanceFinalNotify != null && advanceFinalNotify.equalsIgnoreCase("Y"))
        {
            int advanceDays = getAdvanceFinalNotifyDays(segmentVO.getProductStructureFK());
            EDITDate edFinalNotifyDate = new EDITDate(finalNotifyDate);
            edFinalNotifyDate = edFinalNotifyDate.addDays(advanceDays);
            finalNotifyDate = edFinalNotifyDate.getFormattedDate();
            advanceFinalNotify = "C";
        }
        else if (advanceFinalNotify != null && advanceFinalNotify.equals(""))
        {
            advanceFinalNotify = null;
        }

        segmentVO.setFinalNotifyDate(finalNotifyDate);
        segmentVO.setAdvanceFinalNotify(advanceFinalNotify);
        segmentVO.setAgeAtIssue(Integer.parseInt(ageAtIssue));
        segmentVO.setOriginalStateCT(originalStateCT);
        segmentVO.setLocation(location);
        segmentVO.setSequence(sequence);
        segmentVO.setIndivAnnPremium(indivAnnPremium);
        segmentVO.setWorksheetTypeCT(worksheetTypeCT);
        segmentVO.setRequirementEffectiveDate(requirementEffectiveDate);
        segmentVO.setClientUpdate(Util.initString(appReqBlock.getSessionBean("quoteMainSessionBean").getValue("clientUpdate"), null));

        segmentVO.setDeductionAmountOverride(new EDITBigDecimal(deductionAmountOverride).getBigDecimal());
        segmentVO.setDeductionAmountEffectiveDate(deductionAmountEffectiveDate);

        String estateOfTheInsuredIndStatus = quoteMainFormBean.getValue("estateOfTheInsuredIndStatus");

        if (estateOfTheInsuredIndStatus.equalsIgnoreCase("checked"))
        {
            segmentVO.setEstateOfTheInsured(Segment.INDICATOR_YES);
        }
        else
        {
            segmentVO.setEstateOfTheInsured(Segment.INDICATOR_NO);
        }

        if (segmentVO.getSegmentNameCT().equalsIgnoreCase("Life") ||
            segmentVO.getSegmentNameCT().equalsIgnoreCase("Traditional") ||
            segmentVO.getSegmentNameCT().equalsIgnoreCase("UL") ||
            segmentVO.getSegmentNameCT().equalsIgnoreCase("A&H"))
        {
            buildLifeVO(appReqBlock, segmentVO);
        }
        else
        {
            buildPayouts(appReqBlock, segmentVO);
        }

        return segmentVO;
    }

    private int getAdvanceFinalNotifyDays(long productStructureFK)
    {
        int advanceFinalNotifyDays = 0;

        AreaKey areaKey = AreaKey.findByGrouping_AND_Field(AreaKey.GROUPING_PENDINGREQUIREMENTS, AreaKey.FIELD_ADVANCEFINALNOTIFY);

        if (areaKey != null)
        {
            AreaValue[] areaValues = AreaValue.findBy_AreaKeyPK(areaKey.getPK());

            if (areaValues != null && areaValues.length > 0)
            {
                FilteredAreaValue filteredAreaValue = FilteredAreaValue.findBy_ProductStructurePK_AreaValuePK(productStructureFK, areaValues[0].getPK());

                if (filteredAreaValue != null)
                {
                    advanceFinalNotifyDays = Integer.parseInt(areaValues[0].getAreaValue());
                }
            }
        }

        return advanceFinalNotifyDays;
    }

    /**
     * Builds the PayoutVOs based on information from the screen
     *
     * @param appReqBlock
     * @param segmentVO
     */
    private void buildPayouts(AppReqBlock appReqBlock, SegmentVO segmentVO)
    {
        PageBean quoteMainFormBean = appReqBlock.getSessionBean("quoteMainSessionBean").getPageBean("formBean");
        PageBean quoteMainSessionBean = appReqBlock.getSessionBean("quoteMainSessionBean");

        String payoutPK = quoteMainFormBean.getValue("payoutPK");

        String exclusionRatio = quoteMainFormBean.getValue("exclusionRatio");
        String nextPaymentDate = DateTimeUtil.formatMMDDYYYYToYYYYMMDD(quoteMainFormBean.getValue("nextPaymentDate"));
        String yearlyTaxableBenefit = quoteMainFormBean.getValue("yearlyTaxableBenefit");
        String finalDistributionAmt = quoteMainFormBean.getValue("finalDistributionAmount");
        String totalExpectedReturnAmt = quoteMainFormBean.getValue("totalProjectedAnnuity");
        String reducePercent1 = quoteMainFormBean.getValue("reduce1");
        String reducePercent2 = quoteMainFormBean.getValue("reduce2");
        String postJune1986Investment = quoteMainFormBean.getValue("postJune1986InvestmentIndStatus");
        String paymentFrequency = quoteMainFormBean.getValue("frequencyId");
        String paymentAmount = quoteMainFormBean.getValue("paymentAmount");
        String certainDuration = quoteMainFormBean.getValue("certainDuration");
        String lastDayOfMonthInd = quoteMainFormBean.getValue("lastDayOfMonthIndStatus");
        String ratedGenderCT = quoteMainFormBean.getValue("ratedGenderCT");
        String originalStateCT = quoteMainFormBean.getValue("originalStateCT");
        String location = quoteMainFormBean.getValue("location");
        String sequence = quoteMainFormBean.getValue("sequence");
        String indivAnnPremium = quoteMainFormBean.getValue("indivAnnPremium");
        String underwritingClass = quoteMainFormBean.getValue("underwritingClass");
        String groupPlan = quoteMainFormBean.getValue("groupPlan");

        if (lastDayOfMonthInd.equalsIgnoreCase("checked"))
        {
            lastDayOfMonthInd = "Y";
        }
        else
        {
            lastDayOfMonthInd = "N";
        }

        String terminationDate = DateTimeUtil.formatMMDDYYYYToYYYYMMDD(quoteMainSessionBean.getValue("terminationDate"));

        String finalPaymentDate = DateTimeUtil.formatMMDDYYYYToYYYYMMDD(quoteMainSessionBean.getValue("finalPaymentDate"));
        String certainPeriodEndDate = DateTimeUtil.formatMMDDYYYYToYYYYMMDD(quoteMainSessionBean.getValue("certainPeriodEndDate"));

        String startDate = DateTimeUtil.formatMMDDYYYYToYYYYMMDD(quoteMainFormBean.getValue("startDate"));

        String paymentStartDate = Util.initString(startDate, EDITDate.DEFAULT_MAX_DATE);


        if (paymentStartDate.equals(EDITDate.DEFAULT_MAX_DATE) && segmentVO.getSegmentNameCT().equalsIgnoreCase("Payout"))
        {
            EDITDate effDate = new EDITDate(segmentVO.getEffectiveDate());
            effDate.addMode(paymentFrequency);
            paymentStartDate = effDate.getFormattedDate();
        }

        if (postJune1986Investment.equals("checked"))
        {
            postJune1986Investment = "Y";
        }
        else
        {
            postJune1986Investment = "N";
        }


        PayoutVO payoutVO = new PayoutVO();

        if (!payoutPK.equals(""))
        {
            payoutVO.setPayoutPK(Long.parseLong(payoutPK));
        }
        else
        {
            payoutVO.setPayoutPK(0);
        }

        payoutVO.setSegmentFK(segmentVO.getSegmentPK());
        payoutVO.setPaymentStartDate(paymentStartDate);
        payoutVO.setPaymentFrequencyCT( Util.initString(paymentFrequency, null) );
        payoutVO.setLastDayOfMonthInd(lastDayOfMonthInd);

        if (!certainDuration.equals(""))
        {
            payoutVO.setCertainDuration(Integer.parseInt(certainDuration));
        }
        else
        {
            payoutVO.setCertainDuration(0);
        }

        payoutVO.setPostJune1986Investment(postJune1986Investment);

        if (!paymentAmount.equals(""))
        {
            payoutVO.setPaymentAmount(new EDITBigDecimal(paymentAmount).getBigDecimal());
        }
        else
        {
            payoutVO.setPaymentAmount(new EDITBigDecimal().getBigDecimal());
        }

        if (!reducePercent1.equals(""))
        {
            payoutVO.setReducePercent1(new EDITBigDecimal(reducePercent1).getBigDecimal());
        }
        else
        {
            payoutVO.setReducePercent1(new EDITBigDecimal().getBigDecimal());
        }

        if (!reducePercent2.equals(""))
        {
            payoutVO.setReducePercent2(new EDITBigDecimal(reducePercent2).getBigDecimal());
        }
        else
        {
            payoutVO.setReducePercent2(new EDITBigDecimal().getBigDecimal());
        }

        if (!totalExpectedReturnAmt.equals(""))
        {
            payoutVO.setTotalExpectedReturnAmount(new EDITBigDecimal(totalExpectedReturnAmt).getBigDecimal());
        }
        else
        {
            payoutVO.setTotalExpectedReturnAmount(new EDITBigDecimal().getBigDecimal());
        }

        if (!finalDistributionAmt.equals(""))
        {
            payoutVO.setFinalDistributionAmount(new EDITBigDecimal(finalDistributionAmt).getBigDecimal());
        }
        else
        {
            payoutVO.setFinalDistributionAmount(new EDITBigDecimal().getBigDecimal());
        }

        if (!exclusionRatio.equals(""))
        {
            payoutVO.setExclusionRatio(new EDITBigDecimal(exclusionRatio).getBigDecimal());
        }
        else
        {
            payoutVO.setExclusionRatio(new EDITBigDecimal().getBigDecimal());
        }

        if (!yearlyTaxableBenefit.equals(""))
        {
            payoutVO.setYearlyTaxableBenefit(new EDITBigDecimal(yearlyTaxableBenefit).getBigDecimal());
        }
        else
        {
            payoutVO.setYearlyTaxableBenefit(new EDITBigDecimal().getBigDecimal());
        }

        if (finalPaymentDate != null)
        {
            payoutVO.setFinalPaymentDate(finalPaymentDate);
        }
        else
        {
            payoutVO.setFinalPaymentDate(EDITDate.DEFAULT_MAX_DATE);
        }

        if (certainPeriodEndDate != null)
        {
            payoutVO.setCertainPeriodEndDate(certainPeriodEndDate);
        }
        else
        {
            payoutVO.setCertainPeriodEndDate(EDITDate.DEFAULT_MAX_DATE);
        }

        if (terminationDate != null)
        {
            segmentVO.setTerminationDate(terminationDate);
        }
         if (underwritingClass != null)
        {
            segmentVO.setUnderwritingClassCT(underwritingClass);
        }
        if (originalStateCT != null)
        {
            segmentVO.setOriginalStateCT(originalStateCT);
        }
        if (location != null)
        {
            segmentVO.setLocation(location);
        }
        if (sequence != null)
        {
            segmentVO.setSequence(sequence);
        }
        if (indivAnnPremium != null)
        {
            segmentVO.setIndivAnnPremium(new EDITBigDecimal(indivAnnPremium).getBigDecimal());
        }
        if (ratedGenderCT != null)
        {
            segmentVO.setRatedGenderCT(ratedGenderCT);
        }
        if (groupPlan != null)
        {
            segmentVO.setGroupPlan(groupPlan);
        }
        if (nextPaymentDate != null)
        {
            payoutVO.setNextPaymentDate(nextPaymentDate);
        }

        payoutVO.setExclusionAmount(new EDITBigDecimal().getBigDecimal());

        segmentVO.addPayoutVO(payoutVO);
    }

    /**
     * Builds the LifeVO based on information from the screen
     * @param appReqBlock
     * @param segmentVO
     */
    private  void buildLifeVO(AppReqBlock appReqBlock, SegmentVO segmentVO)
    {
        PageBean quoteMainFormBean = appReqBlock.getSessionBean("quoteMainSessionBean").getPageBean("formBean");

        LifeVO lifeVO = new LifeVO();

        String lifePK = Util.initString(quoteMainFormBean.getValue("lifePK"), "0");
        String deathBeneOption = quoteMainFormBean.getValue("deathBeneOption");
        String nonForfeitureOption = quoteMainFormBean.getValue("nonForfeitureOption");
        String option7702 = quoteMainFormBean.getValue("option7702");
        String faceAmount = Util.initString(quoteMainFormBean.getValue("faceAmount"), "0");
        String term = Util.initString(quoteMainFormBean.getValue("term"), "0");

        String guidelineSinglePrem = Util.initString(quoteMainFormBean.getValue("guidelineSinglePrem"), "0");
        String guidelineLevelPrem = Util.initString(quoteMainFormBean.getValue("guidelineLevelPrem"), "0");
        String tamra = Util.initString(quoteMainFormBean.getValue("tamra"), "0");
        String MECDate = DateTimeUtil.formatMMDDYYYYToYYYYMMDD(quoteMainFormBean.getValue("MECDate"));
        String mecGuidelineSinglePremium = Util.initString(quoteMainFormBean.getValue("mecGuidelineSinglePremium"), "0");
        String mecGuidelineLevelPremium = Util.initString(quoteMainFormBean.getValue("mecGuidelineLevelPremium"), "0");
        String cumGuidelineLevelPremium = Util.initString(quoteMainFormBean.getValue("cumGuidelineLevelPremium"), "0");
        String startNew7PayIndStatus = Util.initString(quoteMainFormBean.getValue("startNew7PayIndicatorStatus"), null);
        String pendingDBOChangeIndStatus = Util.initString(quoteMainFormBean.getValue("pendingDBOChangeIndStatus"), null);
        String mecStatus = Util.initString(quoteMainFormBean.getValue("mecStatus"), null);
        String maxNetAmountAtRisk = Util.initString(quoteMainFormBean.getValue("maxNetAmountAtRisk"), "0");
        String tamraStartDate = DateTimeUtil.formatMMDDYYYYToYYYYMMDD(quoteMainFormBean.getValue("tamraStartDate"));
        String paidToDate = DateTimeUtil.formatMMDDYYYYToYYYYMMDD(quoteMainFormBean.getValue("paidToDate"));
        String lapsePendingDate = DateTimeUtil.formatMMDDYYYYToYYYYMMDD(quoteMainFormBean.getValue("lapsePendingDate"));
        String lapseDate = DateTimeUtil.formatMMDDYYYYToYYYYMMDD(quoteMainFormBean.getValue("lapseDate"));
        String currentDeathBenefit = Util.initString(quoteMainFormBean.getValue("currentDeathBenefit"), "0");
        String MAPEndDate = DateTimeUtil.formatMMDDYYYYToYYYYMMDD(quoteMainFormBean.getValue("MAPEndDate"));
        String tamraInitAdjValue = Util.initString(quoteMainFormBean.getValue("tamraInitAdjValue"), "0");
        
        if (nonForfeitureOption.equalsIgnoreCase("Please Select"))
        {
            nonForfeitureOption = null;
        }
        
        lifeVO.setLifePK(Long.parseLong(lifePK));
        lifeVO.setSegmentFK(segmentVO.getSegmentPK());
        lifeVO.setDeathBenefitOptionCT(Util.initString(deathBeneOption, null));
        lifeVO.setNonForfeitureOptionCT(Util.initString(nonForfeitureOption, null));
        lifeVO.setOption7702CT(Util.initString(option7702, null));
        lifeVO.setFaceAmount(new EDITBigDecimal(faceAmount).getBigDecimal());
        lifeVO.setTerm(Integer.parseInt(term));

        lifeVO.setGuidelineSinglePremium(new EDITBigDecimal(guidelineSinglePrem).getBigDecimal());
        lifeVO.setGuidelineLevelPremium(new EDITBigDecimal(guidelineLevelPrem).getBigDecimal());
        lifeVO.setTamra(new EDITBigDecimal(tamra).getBigDecimal());

        lifeVO.setMECDate(MECDate);

        lifeVO.setMECStatusCT(mecStatus);
        lifeVO.setMECGuidelineSinglePremium(new EDITBigDecimal(mecGuidelineSinglePremium).getBigDecimal());
        lifeVO.setMECGuidelineLevelPremium(new EDITBigDecimal(mecGuidelineLevelPremium).getBigDecimal());
        lifeVO.setCumGuidelineLevelPremium(new EDITBigDecimal(cumGuidelineLevelPremium).getBigDecimal());
        lifeVO.setMaximumNetAmountAtRisk(new EDITBigDecimal(maxNetAmountAtRisk).getBigDecimal());
        lifeVO.setTamraStartDate(tamraStartDate);

        lifeVO.setPaidToDate(paidToDate);
        lifeVO.setLapsePendingDate(lapsePendingDate);
        lifeVO.setLapseDate(lapseDate);
        lifeVO.setCurrentDeathBenefit(new EDITBigDecimal(currentDeathBenefit).getBigDecimal());
        lifeVO.setGuarPaidUpTerm(new EDITBigDecimal(Util.initString(quoteMainFormBean.getValue("guarPaidUpTerm"), "0")).getBigDecimal());
        lifeVO.setNonGuarPaidUpTerm(new EDITBigDecimal(Util.initString(quoteMainFormBean.getValue("nonGuarPaidUpTerm"), "0")).getBigDecimal());
        lifeVO.setMortalityCredit(new EDITBigDecimal(Util.initString(quoteMainFormBean.getValue("mortalityCredit"), "0")).getBigDecimal());
        lifeVO.setEndowmentCredit(new EDITBigDecimal(Util.initString(quoteMainFormBean.getValue("endowmentCredit"), "0")).getBigDecimal());
        lifeVO.setExcessInterestCredit(new EDITBigDecimal(Util.initString(quoteMainFormBean.getValue("excessInterestCredit"), "0")).getBigDecimal());
        lifeVO.setMAPEndDate(MAPEndDate);
        lifeVO.setTamraInitAdjValue(new EDITBigDecimal(tamraInitAdjValue).getBigDecimal());
        
        
        
        segmentVO.addLifeVO(lifeVO);
    }


    /**
     * Builds the ClientRelationshipVOs based on information from the screen
     *
     * @param appReqBlock
     * @param segmentVO
     * @param riderVOs
     *
     * @return Built VOs
     */
    private Map buildClientRelationships(AppReqBlock appReqBlock, SegmentVO segmentVO, Map riderVOs)
    {
        Map clientRelationshipVOs = new HashMap();

        SessionBean clients = appReqBlock.getSessionBean("quoteClients");

        Map clientPageBeans = clients.getPageBeans();

        Iterator it2 = clientPageBeans.values().iterator();

        while (it2.hasNext())
        {
            PageBean clientBean = (PageBean) it2.next();

            String contractClientPK = clientBean.getValue("contractClientPK");
            String taxId = clientBean.getValue("taxId");
            String clientRoleFK = clientBean.getValue("clientRoleFK");
            String clientRole = clientBean.getValue("relationshipInd");
            String optionId = clientBean.getValue("optionId");
            String riderNumber = clientBean.getValue("riderNumber");

            CodeTableWrapper codeTableWrapper = CodeTableWrapper.getSingleton();

            if (Util.isANumber(optionId))
            {
                optionId = codeTableWrapper.getCodeTableEntry(Long.parseLong(optionId)).getCode();
            }

            String issueAge = clientBean.getValue("issueAge");
            String relationToIns = clientBean.getValue("relationToIns");
            String relationToEmp = clientBean.getValue("relationToEmp");
            String phoneAuth = clientBean.getValue("phoneAuth");
            String classType = clientBean.getValue("classType");
            String flatExtra = Util.initString(clientBean.getValue("flatExtra"), "0");
            String flatExtraAge = clientBean.getValue("flatExtraAge");
            String flatExtraDur = clientBean.getValue("flatExtraDur");
            String percentExtra = Util.initString(clientBean.getValue("percentExtra"), "0");
            String percentExtraAge = clientBean.getValue("percentExtraAge");
            String percentExtraDur = clientBean.getValue("percentExtraDur");
            String tableRating = clientBean.getValue("tableRating");
            String payorOf = clientBean.getValue("payorOf");
            String disbAddressType = clientBean.getValue("disbAddressType");
            String corrAddressType = clientBean.getValue("corrAddressType");
            String ratedGender = clientBean.getValue("ratedGender");
            String underwritingClass = clientBean.getValue("underwritingClass");
            String overrideStatus = clientBean.getValue("overrideStatus");
            String employeeIdentification = clientBean.getValue("employeeIdentification");
            String originalClassCT = clientBean.getValue("originalClassCT");

            String contractClientEffDate = DateTimeUtil.formatMMDDYYYYToYYYYMMDD(clientBean.getValue("effectiveDate"));

            String terminateDate = DateTimeUtil.formatMMDDYYYYToYYYYMMDD(clientBean.getValue("terminationDate"));

            if (terminateDate == null)
            {
                terminateDate = EDITDate.DEFAULT_MAX_DATE;
            }

            ContractClientVO contractClientVO = new ContractClientVO();

            if (!contractClientPK.equals(""))
            {
                contractClientVO.setContractClientPK(Long.parseLong(contractClientPK));

                if (contractClientWasDeleted(contractClientPK, appReqBlock))
                {
                    contractClientVO.setVoShouldBeDeleted(true);
                }
            }
            else
            {
                contractClientVO.setContractClientPK(0);
            }

            if (contractClientVO.getContractClientPK() > 0)
            {
                QuestionnaireResponse[] questionnaireResponses = QuestionnaireResponse.findBy_ContractClientFK(new Long(contractClientVO.getContractClientPK()));
                QuestionnaireResponseVO questionnaireResponseVO = null;
                for (int i = 0; i < questionnaireResponses.length; i++)
                {
                    questionnaireResponseVO = (QuestionnaireResponseVO) SessionHelper.map(questionnaireResponses[i], SessionHelper.EDITSOLUTIONS);
                    contractClientVO.addQuestionnaireResponseVO(questionnaireResponseVO);
                }
            }

            if (riderNumber.equals("0"))
            {
                contractClientVO.setSegmentFK(segmentVO.getSegmentPK());
            }
            else
            {
                SegmentVO riderSegment = (SegmentVO) riderVOs.get(riderNumber + "_" + optionId);
                contractClientVO.setSegmentFK(riderSegment.getSegmentPK());
            }

            contractClientVO.setClientRoleFK(Long.parseLong(clientRoleFK));
            contractClientVO.setIssueAge(Integer.parseInt(issueAge));
            contractClientVO.setEffectiveDate(contractClientEffDate);
            contractClientVO.setTerminationDate(terminateDate);
            contractClientVO.setRelationshipToInsuredCT(Util.initString(relationToIns, null));
            contractClientVO.setRelationshipToEmployeeCT(Util.initString(relationToEmp, null));
            contractClientVO.setTelephoneAuthorizationCT(Util.initString(phoneAuth, null));
            contractClientVO.setClassCT(Util.initString(classType, null));
            contractClientVO.setOriginalClassCT(Util.initString(originalClassCT, null));

            if (Util.isANumber(flatExtra))
            {
                contractClientVO.setFlatExtra(new EDITBigDecimal(flatExtra).getBigDecimal());
            }
            if (Util.isANumber(flatExtraAge))
            {
                contractClientVO.setFlatExtraAge(Integer.parseInt(flatExtraAge));
            }
            if (Util.isANumber(flatExtraDur))
            {
                contractClientVO.setFlatExtraDur(Integer.parseInt(flatExtraDur));
            }
            if (Util.isANumber(percentExtra))
            {
                contractClientVO.setPercentExtra(new EDITBigDecimal(percentExtra).getBigDecimal());
            }
            if (Util.isANumber(percentExtraAge))
            {
                contractClientVO.setPercentExtraAge(Integer.parseInt(percentExtraAge));
            }
            if (Util.isANumber(percentExtraDur))
            {
                contractClientVO.setPercentExtraDur(Integer.parseInt(percentExtraDur));
            }

            contractClientVO.setTableRatingCT(Util.initString(tableRating, null) );
            contractClientVO.setDisbursementAddressTypeCT(Util.initString(disbAddressType, null) );
            contractClientVO.setCorrespondenceAddressTypeCT(Util.initString(corrAddressType, null) );
            contractClientVO.setPayorOfCT(Util.initString(payorOf, null) );
            contractClientVO.setRatedGenderCT(Util.initString(ratedGender, null));
            contractClientVO.setUnderwritingClassCT(Util.initString(underwritingClass, null));
            contractClientVO.setOverrideStatus(Util.initString(overrideStatus, "P"));//??hardcoding the status,its actually D when deleted
            contractClientVO.setEmployeeIdentification(Util.initString(employeeIdentification, null));
            contractClientVO.setBeneRelationshipToInsured(Util.initString(clientBean.getValue("beneRelationshipToIns"), null));
            if (contractClientVO.getContractClientPK() == 0)
            {
                contractClientVO.setOperator(appReqBlock.getUserSession().getUsername());
                EDITDateTime maintDateTime = new EDITDateTime();
                contractClientVO.setMaintDateTime(maintDateTime.getFormattedDateTime());
            }
            else
            {
                contractClientVO.setOperator(Util.initString(clientBean.getValue("operator"), null));
                contractClientVO.setMaintDateTime(Util.initString(clientBean.getValue("maintDateTime"), null));
            }

            ClientRole clientRoleClass = new ClientRole();
            clientRoleClass.setRoleTypeCT(clientRole);

            if (clientRole.equalsIgnoreCase("PAY") ||  clientRole.equalsIgnoreCase("POR") ||
                clientRoleClass.isBeneficiary())
            {
                String contractClientAllocPK = Util.initString(clientBean.getValue("contractClientAllocationPK"), "0");
                String allocationPct = clientBean.getValue("allocationPercent");
                String splitEqualInd = Util.initString(clientBean.getValue("splitEqualInd"), "N");
                String allocationAmount = Util.initString(clientBean.getValue("allocationDollars"), "0");

                ContractClientAllocationVO contractClientAllocVO = new ContractClientAllocationVO();

                contractClientAllocVO.setContractClientAllocationPK(Long.parseLong(contractClientAllocPK));
                contractClientAllocVO.setContractClientFK(contractClientVO.getContractClientPK());

                if (Util.isANumber(allocationPct))
                {
                    contractClientAllocVO.setAllocationPercent(new EDITBigDecimal(allocationPct).getBigDecimal());
                }
                else
                {
                    contractClientAllocVO.setAllocationPercent(new EDITBigDecimal().getBigDecimal());
                }

                contractClientAllocVO.setOverrideStatus("P");
                contractClientAllocVO.setSplitEqual(splitEqualInd);
                contractClientAllocVO.setAllocationDollars(new EDITBigDecimal(allocationAmount).getBigDecimal());
                contractClientVO.addContractClientAllocationVO(contractClientAllocVO);

                //  BillLapse
                //buildBillLapse(contractClientVO, clientBean);

            } // end relationships for

            String key = taxId  + riderNumber+ optionId + clientRole + contractClientPK + clientRoleFK;
            clientRelationshipVOs.put(key, contractClientVO);
        } // end clientPageBeans while

        return clientRelationshipVOs;
    }

    /**
     * Builds the BillLapseVOs based on information from the screen
     *
     * @param contractClientVO
     * @param clientBean
     */
//    private void buildBillLapse(ContractClientVO contractClientVO, PageBean clientBean)
//    {
//        String[] billLapsePKs = clientBean.getValues("billLapsePKs");
//        String[] billingFKs = clientBean.getValues("billingFKs");
//        String[] insuranceAmounts = clientBean.getValues("insuranceAmounts");
//        String[] investmentAmounts = clientBean.getValues("investmentAmounts");
//        String[] extractDates = clientBean.getValues("extractDates");
//        String[] dueDates = clientBean.getValues("dueDates");
//        String[] fixedAmounts = clientBean.getValues("fixedAmounts");
//
//        String billDay          = clientBean.getValue("billDay");
//        String lastDayOfMonth   = clientBean.getValue("lastDayOfMonth");
//        String billingMode      = clientBean.getValue("billingMode");
//        String billingMethod    = clientBean.getValue("billingMethod");
//
//
//        if (billLapsePKs != null && billLapsePKs.length > 0)
//        {
//            for (int j = 0; j < billLapsePKs.length; j++)
//            {
//                String billLapsePK      = billLapsePKs[j];
//                String billingFK        = billingFKs[j];
//                String insuranceAmount  = insuranceAmounts[j];
//                String investmentAmount = investmentAmounts[j];
//                String extractDate      = extractDates[j];
//                String dueDate          = dueDates[j];
//                String fixedAmount      = fixedAmounts[j];
//
//                BillLapseVO billLapseVO = new BillLapseVO();
//
//                if (! billLapsePK.equals(""))
//                {
//                    billLapseVO.setBillLapsePK(Long.parseLong(billLapsePK));
//                }
//                else
//                {
//                    billLapseVO.setBillLapsePK(0);
//                }
//
//                if (! billingFK.equals("")){
//                    billLapseVO.setBillingFK(Long.parseLong(billingFK));
//                }
//                else{
//                    billLapseVO.setBillingFK(0);
//                }
//
//                billLapseVO.setContractClientFK(contractClientVO.getContractClientPK());
//
//                if (Util.isANumber(insuranceAmount))
//                {
//                    billLapseVO.setInsuranceAmount(new EDITBigDecimal(insuranceAmount).getBigDecimal());
//                }
//
//                if (Util.isANumber(investmentAmount))
//                {
//                    billLapseVO.setInvestmentAmount(new EDITBigDecimal(investmentAmount).getBigDecimal());
//                }
//
//                if (extractDate != null && !extractDate.trim().equals(""))
//                    billLapseVO.setExtractDate(extractDate);
//                if (dueDate != null && !dueDate.trim().equals(""))
//                    billLapseVO.setDueDate(dueDate);
//
//                if (Util.isANumber(fixedAmount))
//                {
//                    billLapseVO.setFixedAmount(new EDITBigDecimal(fixedAmount).getBigDecimal());
//                }
//
//                 BillingVO billingVO = new BillingVO();
//
//                 if (! billingFK.equals("")){
//                     billingVO.setBillingPK(Long.parseLong(billingFK));
//                 }
//                 else{
//                     billingVO.setBillingPK(0);
//                 }
//
//                 billingVO.setBillDay(Integer.parseInt(billDay));
//                 billingVO.setLastDayOfMonth(lastDayOfMonth);
//                 billingVO.setMethodCT(billingMethod);
//                 billingVO.setModeCT(billingMode);
//                 (billLapseVO).setParentVO(BillingVO.class,billingVO);
//
//                 contractClientVO.addBillLapseVO(billLapseVO);
//                 break;  //sramamurthy assumption that there'll be only one billing record
//                         //for a contract client
//            }
//        }
//    }

    /**
     * Builds the InvestmentVOs based on information from the screen
     *
     * @param appReqBlock
     * @param optionCode
     *
     * @return Built VOs
     */
    private Map buildInvestments(AppReqBlock appReqBlock, String optionCode)
    {
        Map fundVOs = new HashMap();

        // 4. ***  Start the allocationVOs ***
        SessionBean funds = appReqBlock.getSessionBean("quoteFunds");

        Map fundPageBeans = funds.getPageBeans();

        Iterator it5 = fundPageBeans.values().iterator();

        while (it5.hasNext())
        {
            PageBean fundBean = (PageBean) it5.next();

            optionCode = fundBean.getValue("optionId");

            String investmentPK = fundBean.getValue("investmentPK");
            String segmentFK = fundBean.getValue("segmentFK");
            String filteredFundFK = fundBean.getValue("filteredFundFK");
            String allocationPct = fundBean.getValue("allocationPercent");
            String air = fundBean.getValue("air");
            String status = fundBean.getValue("status");
            String investmentAllocationPK = Util.initString(fundBean.getValue("investmentAllocationPK"), "0");
            String key = optionCode + filteredFundFK;

            InvestmentVO investmentVO = new InvestmentVO();

            if (!investmentPK.equals(""))
            {
                investmentVO.setInvestmentPK(Long.parseLong(investmentPK));
            }
            else
            {
                investmentVO.setInvestmentPK(0);
            }

            investmentVO.setFilteredFundFK(Long.parseLong(filteredFundFK));

            if (!segmentFK.equals(""))
            {
                investmentVO.setSegmentFK(Long.parseLong(segmentFK));
            }
            else
            {
                investmentVO.setSegmentFK(0);
            }

//            investmentVO.setExcessInterestCalculationDate("0000/00/00");
//            investmentVO.setExcessInterestPaymentDate("0000/00/00");
            investmentVO.setExcessInterest(new EDITBigDecimal().getBigDecimal());
            investmentVO.setExcessInterestMethod(null);
//            investmentVO.setExcessInterestStartDate("0000/00/00");

            if (!air.equals(""))
            {
                investmentVO.setAssumedInvestmentReturn(new EDITBigDecimal(air).getBigDecimal());
            }
            else
            {
                investmentVO.setAssumedInvestmentReturn(new EDITBigDecimal().getBigDecimal());
            }

            if (status != null && !status.equals(""))
            {
                investmentVO.setStatus(status);
            }

            InvestmentAllocationVO investmentAllocVO = null;
            if (investmentAllocationPK.equals("0"))
            {
                investmentAllocVO = new InvestmentAllocationVO();
                investmentAllocVO.setInvestmentAllocationPK(0);
            }
            else
            {
                investmentAllocVO = InvestmentAllocation.findByPK_UsingCRUD(Long.parseLong(investmentAllocationPK));
            }
            investmentAllocVO.setInvestmentFK(investmentVO.getInvestmentPK());
            investmentAllocVO.setAllocationPercent(new EDITBigDecimal(allocationPct).getBigDecimal());
            investmentAllocVO.setDollars(new EDITBigDecimal().getBigDecimal());
            investmentAllocVO.setUnits(new EDITBigDecimal().getBigDecimal());
            investmentAllocVO.setOverrideStatus("P");
            investmentVO.addInvestmentAllocationVO(investmentAllocVO);

            fundVOs.put(key, investmentVO);
        } // end funds while
        // 4. ***  End the fundVOs ***

        return fundVOs;
    }

    /**
     * Builds the ClientDetailVOs based on information from the screen
     *
     * @param appReqBlock
     * @param segmentVO
     * @param quoteVO
     *
     * @throws Exception
     */
    private void buildClientDetails(AppReqBlock appReqBlock, QuoteVO quoteVO) throws Exception
    {
        SessionBean clients = appReqBlock.getSessionBean("quoteClients");

        Iterator it9 = clients.getPageBeans().values().iterator();

        List clientDetailVOs = new ArrayList();

        while (it9.hasNext())
        {
            PageBean clientBean2 = (PageBean) it9.next();

            long clientRolePK = Long.parseLong(clientBean2.getValue("clientRoleFK"));

            role.business.Lookup roleLookup = new role.component.LookupComponent();

            ClientRoleVO clientRoleVO = roleLookup.findClientRoleVOByClientRolePK(clientRolePK, false, null)[0];

            long clientDetailPK = clientRoleVO.getClientDetailFK();

            ClientDetailVO clientDetailVO = checkClientExistence(clientDetailPK);

            if (clientRoleVO != null)
            {
                clientDetailVO.addClientRoleVO(clientRoleVO);
            }
            clientDetailVOs.add(clientDetailVO);
        }

        ClientDetailVO[] clientDetailArray = (ClientDetailVO[]) clientDetailVOs.
                toArray(new ClientDetailVO[clientDetailVOs.size()]);

        quoteVO.setClientDetailVO(clientDetailArray);
    }

    /**
     * Attaches the VOs to each other and to the SegmentVO as necessary for the build
     *
     * @param appReqBlock
     * @param baseSegmentVO
     * @param optionCode
     * @param clientRelationshipVOs
     * @param riderVOs
     * @param fundVOs
     * @param notesVOs
     *
     * @throws Exception
     */
    private void attachVOsForBuild(AppReqBlock appReqBlock, SegmentVO baseSegmentVO, String optionCode,
                                   Map clientRelationshipVOs, Map riderVOs, Map fundVOs, Map notesVOs) throws Exception
    {
        attachContractClientsForBuild(appReqBlock, baseSegmentVO, riderVOs, clientRelationshipVOs);

        new UtilitiesForTran().attachAgentHierarchysForBuild(appReqBlock, baseSegmentVO, riderVOs);

        attachFundsForBuild(appReqBlock, baseSegmentVO, riderVOs, optionCode, fundVOs);

        attachNoteRemindersForBuild(appReqBlock, baseSegmentVO, notesVOs);

        attachContractRequirementsForBuild(appReqBlock, baseSegmentVO);

        attachDepositsForBuild(appReqBlock, baseSegmentVO);

        attachRidersForBuild(baseSegmentVO, riderVOs);
    }

    /**
     * Adds ContractClients to the SegmentVO upon build
     *
     * @param appReqBlock
     * @param baseSegmentVO
     * @param riderVOs
     * @param clientRelationshipVOs
     */
    private void attachContractClientsForBuild(AppReqBlock appReqBlock, SegmentVO baseSegmentVO, Map riderVOs, Map clientRelationshipVOs)
    {
        // Loop through all the clientRelationships.
        // IF the coverage of the client (from the clientRelationship) match the annuityOption from
        // the main page, then attach the clientRelationshipVO to the contractVO.
        // ELSE, we need to attach the clientRelationshipVO to a riderVO. We determine the appropriate riderVO by
        // comparing the coverage to the riderVOs riderType.

        SessionBean clients = appReqBlock.getSessionBean("quoteClients");

        Map clientPageBeans2 = clients.getPageBeans();

        Iterator it6 = clientPageBeans2.values().iterator();

        while (it6.hasNext())
        {
            PageBean clientPB = (PageBean) it6.next();

            String taxId = clientPB.getValue("taxId");
            String clientRole = clientPB.getValue("relationshipInd");
            String clientOption = clientPB.getValue("optionId");
            String clientRoleFK = clientPB.getValue("clientRoleFK");
            String contractClientPK = clientPB.getValue("contractClientPK");
            String riderNumber = clientPB.getValue("riderNumber");

            String ccKey = taxId + riderNumber + clientOption + clientRole + contractClientPK + clientRoleFK;

            ContractClientVO contractClientVO = (ContractClientVO) clientRelationshipVOs.get(ccKey);

            if (riderNumber.equals("0"))
            {
                baseSegmentVO.addContractClientVO(contractClientVO);
            }
            else
            {
                SegmentVO riderSegment = (SegmentVO) riderVOs.get(riderNumber + "_" + clientOption);
                riderSegment.addContractClientVO(contractClientVO);
            }
        }
    }

    /**
     * Adds Funds (Investments) to the SegmentVO upon build
     *
     * @param appReqBlock
     * @param baseSegmentVO
     * @param riderVOs
     * @param optionCode
     * @param fundVOs
     *
     * @throws Exception
     */
    private void attachFundsForBuild(AppReqBlock appReqBlock, SegmentVO baseSegmentVO, Map riderVOs, String optionCode,
                                     Map fundVOs) throws Exception
    {
        SessionBean funds = appReqBlock.getSessionBean("quoteFunds");

        contract.business.Lookup contractLookup = new contract.component.LookupComponent();

        Iterator it7 = funds.getPageBeans().values().iterator();

        while (it7.hasNext())
        {
            PageBean fundPageBean = (PageBean) it7.next();

            optionCode = fundPageBean.getValue("optionId");

            String filteredFundId = fundPageBean.getValue("filteredFundFK");
            String key = optionCode + filteredFundId;

            InvestmentVO investmentVO = (InvestmentVO) fundVOs.get(key);

            List voExclusionList = new ArrayList();
            voExclusionList.add(InvestmentAllocationOverrideVO.class);
            voExclusionList.add(BucketHistoryVO.class);

            BucketVO[] bucketVOs = contractLookup.getBucketsByInvestmentFK(investmentVO.getInvestmentPK(), true, voExclusionList);

            if (bucketVOs != null)
            {
                investmentVO.setBucketVO(bucketVOs);
            }

            SegmentVO riderVO = (SegmentVO) riderVOs.get(optionCode + "");

            if (riderVO != null)
            {
                riderVO.addInvestmentVO(investmentVO);
            }
            else
            {
                baseSegmentVO.addInvestmentVO(investmentVO);
            }
        }
    }

    /**
     * Adds NoteReminders to the SegmentVO upon build
     *
     * @param appReqBlock
     * @param baseSegmentVO
     * @param notesVOs
     *
     * @throws Exception
     */
    private void attachNoteRemindersForBuild(AppReqBlock appReqBlock, SegmentVO baseSegmentVO, Map notesVOs) throws Exception
    {
        SessionBean quoteNotesSessionBean = appReqBlock.getSessionBean("quoteNotesSessionBean");

        Iterator itC = quoteNotesSessionBean.getPageBeans().values().iterator();

        while (itC.hasNext())
        {
            PageBean notePageBean = (PageBean) itC.next();

            String key = notePageBean.getValue("key");

            NoteReminderVO noteReminderVO = (NoteReminderVO) notesVOs.get(key);

            baseSegmentVO.addNoteReminderVO(noteReminderVO);
        }
    }

    /**
     * Adds ContractRequirements to the SegmentVO upon build
     *
     * @param appReqBlock
     * @param baseSegmentVO
     * @throws Exception
     */
    private void attachContractRequirementsForBuild(AppReqBlock appReqBlock, SegmentVO baseSegmentVO) throws Exception
    {
        ContractRequirementVO[] contractRequirementVO = null;

        //get requirements
        contractRequirementVO = getRequirements(appReqBlock);

        if (contractRequirementVO != null && contractRequirementVO.length != 0)
        {
            appReqBlock.getHttpSession().setAttribute("contractRequirementVO", contractRequirementVO);

            //Set the FinalNotifyDate to the earliest requirement FollowupDate only if the FinalNotifyDate is null
            if (baseSegmentVO.getFinalNotifyDate() == null)
            {
                EDITDate earliestFollowupDate = null;
                EDITDate currentFollowupDate = null;

                for (int i = 0; i < contractRequirementVO.length; i++)
                {
                    currentFollowupDate = new EDITDate(contractRequirementVO[i].getFollowupDate());

                    if (earliestFollowupDate == null || currentFollowupDate.before(earliestFollowupDate))
                    {
                        earliestFollowupDate = currentFollowupDate;
                    }
                }

                baseSegmentVO.setFinalNotifyDate(earliestFollowupDate.getFormattedDate());
            }
            else
            {
                for (int i = 0; i < contractRequirementVO.length; i++)
                {
                    FilteredRequirementVO filteredRequirementVO = (FilteredRequirementVO) FilteredRequirement.findByPK(contractRequirementVO[i].getFilteredRequirementFK()).getVO();
                    RequirementVO requirementVO = (RequirementVO) Requirement.findByPK(filteredRequirementVO.getRequirementFK()).getVO();
                    if (!requirementVO.getRequirementId().startsWith("MIB"))
                    {
                        contractRequirementVO[i].setFollowupDate(baseSegmentVO.getFinalNotifyDate());
                    }
                }
            }

            for (int i = 0; i < contractRequirementVO.length; i++)
            {
                if (contractRequirementVO[i].getContractRequirementPK() < 0)
                {
                    contractRequirementVO[i].setContractRequirementPK(0);
                }
            }

            baseSegmentVO.setContractRequirementVO(contractRequirementVO);
        }
    }

    /**
     * Adds Deposits to the SegmentVO upon build
     *
     * @param appReqBlock
     * @param baseSegmentVO
     *
     * @throws Exception
     */
    private void attachDepositsForBuild(AppReqBlock appReqBlock, SegmentVO baseSegmentVO) throws Exception
    {
        DepositsVO[] depositsVOs = (DepositsVO[]) appReqBlock.getHttpSession().getAttribute("depositsVOs");

        if (depositsVOs != null)
        {
            for (int i = 0; i < depositsVOs.length; i++)
            {
                if (!depositsVOs[i].getVoShouldBeDeleted())
                {
                    baseSegmentVO.addDepositsVO(depositsVOs[i]);
                }
            }
        }
    }

    /**
     * Adds rider to the SegmentVO upon build
     *
     * @param baseSegmentVO
     * @param riderVOs
     *
     * @throws Exception
     */
    private void attachRidersForBuild(SegmentVO baseSegmentVO, Map riderVOs) throws Exception
    {
        Iterator it8 = riderVOs.values().iterator();

        while (it8.hasNext())
        {
            SegmentVO riderVO = (SegmentVO) it8.next();
            baseSegmentVO.addSegmentVO(riderVO);
        }
    }

    private String setNoteReminderSequenceNumber(SessionBean quoteNotesSessionBean) throws Exception
    {
        int highestSeq = 0;

        Map noteBeans = quoteNotesSessionBean.getPageBeans();
        Set keys = noteBeans.keySet();

        Iterator it = keys.iterator();

        while (it.hasNext())
        {
            String key = (String) it.next();

            PageBean pb = (PageBean) noteBeans.get(key);

            String sequence = pb.getValue("sequence");

            if (Util.isANumber(sequence))
            {
                int sequenceNumber = Integer.parseInt(sequence);
                if (sequenceNumber > highestSeq)
                {
                    highestSeq = sequenceNumber;
                }
            }
        }

        return (highestSeq += 1) + "";
    }

    /**
     * Builds the rider SegmentVOs based on information from the screen
     *
     * @param appReqBlock
     * @param baseSegmentVO
     *
     * @return Built VOs
     */
    private Map buildRiders(AppReqBlock appReqBlock, SegmentVO baseSegmentVO)
    {
        Map riderVOs = new HashMap();

        SessionBean riders = appReqBlock.getSessionBean("quoteRiders");
        SessionBean deletedRiders = appReqBlock.getSessionBean("quoteDeletedRiders");

        Map riderPageBeans = riders.getPageBeans();

        Iterator it = riderPageBeans.values().iterator();

        while (it.hasNext())
        {
            PageBean rider = (PageBean) it.next();

            SegmentVO riderSegmentVO = buildRiderSegmentVO(rider, baseSegmentVO);

            PremiumDueVO[] riderPremiumDueVOs = (PremiumDueVO[]) appReqBlock.getHttpSession().getAttribute("riderPremiumDueVOs_" + riderSegmentVO.getOptionCodeCT());
            if (riderPremiumDueVOs != null)
            {
                riderSegmentVO.setPremiumDueVO(riderPremiumDueVOs);
            }

            Life life = Life.findBy_SegmentPK(riderSegmentVO.getSegmentPK());
            if (life != null)
            {
                LifeVO[] lifeVOs = new LifeVO[1];
                lifeVOs[0] = (LifeVO) life.getVO();
                riderSegmentVO.setLifeVO(lifeVOs);
            }

            String key = (riderSegmentVO.getRiderNumber() + "") + "_" + riderSegmentVO.getOptionCodeCT();

            riderVOs.put(key, riderSegmentVO);
        }

        //Add deleted riders to the vo in order for them to get removed from the database
        if (deletedRiders != null)
        {
            Map deletedRiderPageBeans = deletedRiders.getPageBeans();

            Iterator it2 = deletedRiderPageBeans.values().iterator();

            while (it2.hasNext())
            {
                PageBean rider = (PageBean) it2.next();

                SegmentVO riderSegmentVO = buildRiderSegmentVO(rider, baseSegmentVO);

                PremiumDueVO[] riderPremiumDueVOs = (PremiumDueVO[]) appReqBlock.getHttpSession().getAttribute("riderPremiumDueVOs_" + riderSegmentVO.getOptionCodeCT());
                if (riderPremiumDueVOs != null)
                {
                    riderSegmentVO.setPremiumDueVO(riderPremiumDueVOs);
                }

                String key = (riderSegmentVO.getRiderNumber() + "") + "_" + riderSegmentVO.getOptionCodeCT();

                riderSegmentVO.setVoShouldBeDeleted(true);
                //This status is for script processing, the boolean cannot be accessed
                riderSegmentVO.setSegmentStatusCT("Deleted");
                riderVOs.put(key, riderSegmentVO);
            }
        }

        return riderVOs;
    }

    private SegmentVO buildRiderSegmentVO(PageBean rider, SegmentVO baseSegmentVO)
    {
        SegmentVO riderSegmentVO = new SegmentVO();

        String segmentNamePK        = rider.getValue("segmentNamePK");
        String optionCodePK         = rider.getValue("optionCodePK");
        String effectiveDate        = DateTimeUtil.formatMMDDYYYYToYYYYMMDD(rider.getValue("effectiveDate"));
        String terminationDate      = DateTimeUtil.formatMMDDYYYYToYYYYMMDD(rider.getValue("terminationDate"));
        String expiryDate      = DateTimeUtil.formatMMDDYYYYToYYYYMMDD(rider.getValue("expiryDate"));
        String sequence				= rider.getValue("sequence");
        String location				= rider.getValue("location");
        String riderSegmentPK       = Util.initString(rider.getValue("riderSegmentPK"), "0");
        String ageAtIssue           = Util.initString(rider.getValue("ageAtIssue"), "0");
        String originalStateCT      = Util.initString(rider.getValue("originalStateCT"), null);
        String indivAnnPremium               = Util.initString(rider.getValue("indivAnnPremium"), "0");
        String amount               = Util.initString(rider.getValue("amount"), "0");
        String ratedGenderCT = Util.initString(rider.getValue("ratedGenderCT"), null);
        String underwritingClass = Util.initString(rider.getValue("underwritingClass"), null);
        String groupPlan = Util.initString(rider.getValue("groupPlan"), null);
        
        String units = Util.initString(rider.getValue("units"), "0");
        String commissionPhaseID = Util.initString(rider.getValue("commissionPhaseID"), "1");
        String commissionPhaseOverride = Util.initString(rider.getValue("commissionPhaseOverride"), null);

        String segmentName = CodeTableWrapper.getSingleton().getCodeTableEntry(Long.parseLong(segmentNamePK)).getCode();
        String optionCode  = CodeTableWrapper.getSingleton().getCodeTableEntry(Long.parseLong(optionCodePK)).getCode();

        riderSegmentVO.setSegmentPK(Long.parseLong(riderSegmentPK));
        riderSegmentVO.setSegmentFK(baseSegmentVO.getSegmentPK());
        riderSegmentVO.setContractNumber( Util.initString(rider.getValue("contractNumber"), baseSegmentVO.getContractNumber()) );
        riderSegmentVO.setProductStructureFK(baseSegmentVO.getProductStructureFK());
        riderSegmentVO.setEffectiveDate(effectiveDate);
        riderSegmentVO.setRatedGenderCT(ratedGenderCT);
        riderSegmentVO.setUnderwritingClassCT(underwritingClass);
        riderSegmentVO.setGroupPlan(groupPlan);
        String applicationSignedDate = Util.initString(rider.getValue("applicationSignedDate"), null);
        if (applicationSignedDate != null)
        {
            riderSegmentVO.setApplicationSignedDate(DateTimeUtil.formatMMDDYYYYToYYYYMMDD(rider.getValue("applicationSignedDate")));
        }
        riderSegmentVO.setAmount(new EDITBigDecimal(amount).getBigDecimal());
        riderSegmentVO.setExchangeInd(Util.initString(rider.getValue("exchangeInd"), null));
        riderSegmentVO.setCostBasis(new EDITBigDecimal().getBigDecimal());
        riderSegmentVO.setRecoveredCostBasis(new EDITBigDecimal().getBigDecimal());
        riderSegmentVO.setTerminationDate(terminationDate);
        riderSegmentVO.setExpiryDate(expiryDate);
        riderSegmentVO.setSegmentNameCT( Util.initString(segmentName, null) );
        riderSegmentVO.setSegmentStatusCT( Util.initString(baseSegmentVO.getSegmentStatusCT(), null) );
        riderSegmentVO.setOptionCodeCT( Util.initString(optionCode, null) );
        riderSegmentVO.setIssueStateCT( Util.initString(rider.getValue("issueState"), null) );
        riderSegmentVO.setQuoteDate(DateTimeUtil.formatMMDDYYYYToYYYYMMDD(rider.getValue("quoteDate")));
        riderSegmentVO.setCharges(new EDITBigDecimal().getBigDecimal());
        riderSegmentVO.setLoads(new EDITBigDecimal().getBigDecimal());
        riderSegmentVO.setFees(new EDITBigDecimal().getBigDecimal());
        riderSegmentVO.setTaxReportingGroup( Util.initString(baseSegmentVO.getTaxReportingGroup(), null) );
        riderSegmentVO.setIssueDate(baseSegmentVO.getIssueDate());
        riderSegmentVO.setCashWithAppInd(baseSegmentVO.getCashWithAppInd());
        riderSegmentVO.setWaiverInEffect("N");  // default to 'N'
        riderSegmentVO.setFreeAmountRemaining(baseSegmentVO.getFreeAmountRemaining());
        riderSegmentVO.setFreeAmount(baseSegmentVO.getFreeAmount());
        riderSegmentVO.setUnits(new EDITBigDecimal(units).getBigDecimal());
        riderSegmentVO.setCommissionPhaseID(Integer.parseInt(commissionPhaseID));
        riderSegmentVO.setCommissionPhaseOverride(commissionPhaseOverride);
        String creationDate = Util.initString(rider.getValue("creationDate"), null);
        if (creationDate != null)
        {
            riderSegmentVO.setCreationDate(DateTimeUtil.formatMMDDYYYYToYYYYMMDD(creationDate));
        }
        riderSegmentVO.setCreationOperator(rider.getValue("creationOperator"));
        riderSegmentVO.setWaiveFreeLookIndicator(Util.initString(rider.getValue("waiveFreeLookInd"), "N"));
        riderSegmentVO.setAnnualPremium(new EDITBigDecimal(Util.initString(rider.getValue("annualPremium"), "0")).getBigDecimal());
        riderSegmentVO.setAgeAtIssue(Integer.parseInt(ageAtIssue));
        riderSegmentVO.setOriginalStateCT(originalStateCT);
        riderSegmentVO.setSequence(sequence);
        riderSegmentVO.setLocation(location);
        riderSegmentVO.setIndivAnnPremium(new EDITBigDecimal(indivAnnPremium).getBigDecimal());
        
        String multiple = Util.initString(rider.getValue("multiple"), "0");
        if (multiple.equalsIgnoreCase("Please Select"))
        {
        	multiple = "0";
        }
        riderSegmentVO.setEOBMultiple(Integer.parseInt(multiple));

        String gioOption = Util.initString(rider.getValue("gioOption"), null);
        if (gioOption != null && gioOption.equalsIgnoreCase("Please Select"))
        {
            gioOption = null;
        }
        riderSegmentVO.setGIOOption(gioOption);

        riderSegmentVO.setRiderNumber(Integer.parseInt(rider.getValue("riderNumber")));

        return riderSegmentVO;
    }

    private void clearAllQuoteSessions(AppReqBlock appReqBlock) throws Exception
    {
        appReqBlock.getHttpSession().removeAttribute("editTrxVOs");
        appReqBlock.getHttpSession().removeAttribute("changeHistoryVOs");
        appReqBlock.getHttpSession().removeAttribute("pendingTransactions");
        appReqBlock.getSessionBean("quoteMainSessionBean").clearState();
        appReqBlock.getSessionBean("quoteNotesSessionBean").clearState();
        appReqBlock.getSessionBean("quoteTaxesSessionBean").clearState();

        appReqBlock.getSessionBean("quoteClients").clearState();
        appReqBlock.getSessionBean("contractMainSessionBean").clearState();

        SessionBean riders = appReqBlock.getSessionBean("quoteRiders");

        Map riderPageBeans = riders.getPageBeans();

        Iterator it = riderPageBeans.values().iterator();

        while (it.hasNext())
        {
            PageBean rider = (PageBean) it.next();
            String optionCodePK         = rider.getValue("optionCodePK");
            if (!optionCodePK.equals(""))
            {
                String optionCode  = CodeTableWrapper.getSingleton().getCodeTableEntry(Long.parseLong(optionCodePK)).getCode();

                appReqBlock.getHttpSession().removeAttribute("riderPremiumDueVOs_" + optionCode);
            }
        }
        
        appReqBlock.getSessionBean("quoteRiders").clearState();
        if (appReqBlock.getSessionBean("quoteDeletedRiders") != null)
        {
            appReqBlock.getSessionBean("quoteDeletedRiders").clearState();
        }
        appReqBlock.getSessionBean("quoteStateBean").clearState();
        appReqBlock.getSessionBean("quoteAgents").clearState();
        appReqBlock.getSessionBean("quoteFunds").clearState();
        appReqBlock.getSessionBean("quoteTransactions").clearState();
        appReqBlock.getSessionBean("quoteNotes").clearState();

        appReqBlock.getSessionBean("contractClientAddSessionBean").clearState();

        appReqBlock.getSessionBean("quoteStateBean").clearState();

        appReqBlock.getSessionBean("quoteStateBean").putValue("currentPage", null);
        appReqBlock.getSessionBean("quoteStateBean").putValue("previousPage", null);

        appReqBlock.getHttpSession().removeAttribute("uiAgentHierarchyVOs");
        appReqBlock.getHttpSession().removeAttribute("contractRequirementVO");
        appReqBlock.getHttpSession().removeAttribute("selectedContractRequirementVO");
        appReqBlock.getHttpSession().removeAttribute("depositsVOs");
        appReqBlock.getHttpSession().removeAttribute("currentDepositVO");
        appReqBlock.getHttpSession().removeAttribute("contractSuspenseVOs");
        appReqBlock.getHttpSession().removeAttribute("premiumDueVOs");
        appReqBlock.getHttpSession().removeAttribute("batchContractSetup");

        UserSession userSession = appReqBlock.getUserSession();
        userSession.setDepositsVO(null);
    }

    private void calculateIssueAge(ContractClientVO[] contractClientVOs, String newEffectiveDate, String segmentName) throws Exception
    {
        int annuitantIssueAge = 0;
        boolean ownerIssueAgeMissing = false;
        int ownerIndex = 0;

        for (int i = 0; i < contractClientVOs.length; i++)
        {
            if (contractClientVOs[i].getIssueAge() == 0)
            {
                int issueAge = 0;

                if (contractClientVOs[i].getEffectiveDate() == (null))
                {
                    contractClientVOs[i].setEffectiveDate(newEffectiveDate);
                }

                role.business.Lookup roleLookup = new role.component.LookupComponent();

                List voInclusionList = new ArrayList();
                voInclusionList.add(ClientDetailVO.class);

                ClientRoleVO clientRoleVO = roleLookup.composeClientRoleVO(contractClientVOs[i].getClientRoleFK(), voInclusionList);

                ClientDetailVO clientDetailVO = (ClientDetailVO) clientRoleVO.getParentVO(ClientDetailVO.class);

                String effectiveDate = contractClientVOs[i].getEffectiveDate();

                String birthDate = clientDetailVO.getBirthDate();

                if ((birthDate != null) && (effectiveDate != null))
                {
                    if (! birthDate.equals(EDITDate.DEFAULT_MAX_DATE))
                    {
                        try
                        {
                            Contract contractComponent = new ContractComponent();

                            if (segmentName.equalsIgnoreCase("Life"))
                            {
                                issueAge = contractComponent.calculateIssueAgeForLifeContracts(birthDate, effectiveDate);
                            }
                            else
                            {
                                issueAge = contractComponent.calculateIssueAge(birthDate, effectiveDate);
                            }
                        }
                        catch (EDITContractException e)
                        {
                            issueAge = 0;
                        }
                    }
                }

                contractClientVOs[i].setIssueAge(issueAge);

                if (clientRoleVO.getRoleTypeCT().equalsIgnoreCase("ANN"))
                {
                    annuitantIssueAge = issueAge;
                }

                if (issueAge == 0 && clientRoleVO.getRoleTypeCT().equalsIgnoreCase("OWN"))
                {
                    ownerIndex = i;
                    ownerIssueAgeMissing = true;
                }
            }
        }

        if (ownerIssueAgeMissing)
        {
            contractClientVOs[ownerIndex].setIssueAge(annuitantIssueAge);
        }
    }

    private String performPageEditing(AppReqBlock appReqBlock) throws Exception
    {
        CodeTableWrapper codeTableWrapper = CodeTableWrapper.getSingleton();

        PageBean quoteMainFormBean = appReqBlock.getSessionBean("quoteMainSessionBean").getPageBean("formBean");

        String productStructureId = quoteMainFormBean.getValue("companyStructureId");
        String optionId = quoteMainFormBean.getValue("optionId");

        SessionBean funds = appReqBlock.getSessionBean("quoteFunds");
        Map allocationPctHT = new HashMap();
        Map fundPageBeans = funds.getPageBeans();
        boolean fundsFound = false;

        Iterator it = fundPageBeans.values().iterator();

        while (it.hasNext())
        {
            fundsFound = true;
            PageBean fundBean = (PageBean) it.next();

            if (fundBean.getValue("status") != null)
            {
                String coverage = fundBean.getValue("optionId");

                EDITBigDecimal allocationPct = new EDITBigDecimal(fundBean.getValue("allocationPercent"));
                if (allocationPctHT.containsKey(coverage))
                {
                    EDITBigDecimal coverageAllocPct = Util.roundAllocation( (EDITBigDecimal) allocationPctHT.get(coverage) );
                    coverageAllocPct = coverageAllocPct.addEditBigDecimal( Util.roundAllocation(allocationPct) );
                    allocationPctHT.put( coverage, Util.roundAllocation(coverageAllocPct) );
                }
                else
                {
                    allocationPctHT.put(coverage, allocationPct);
                }
            }
        }

        Iterator htKeys = allocationPctHT.keySet().iterator();
        boolean totalFundAllocation100 = true;
        String allocationCoverage = "";

        while (htKeys.hasNext())
        {
            String coverage = (String) htKeys.next();

            EDITBigDecimal totalAllocation = (EDITBigDecimal) allocationPctHT.get(coverage);

            if ( !totalAllocation.isEQ("1") )
            {
                totalFundAllocation100 = false;

                if (Util.isANumber(coverage))
                {
                    allocationCoverage = codeTableWrapper.getCodeTableEntry(Long.parseLong(coverage)).getCode();
                }
                else
                {
                    allocationCoverage = coverage;
                }
            }
        }

        SessionBean clients = appReqBlock.getSessionBean("quoteClients");
        Map payeeAllocPctHT = new HashMap();
        Map cbeAllocPctHT = new HashMap();
        Map cbeAllocDollHT = new HashMap();
        Map clientPageBeans = clients.getPageBeans();
        boolean annuitantFound = false;
        boolean ownerFound = false;
        boolean insuredFound = false;

        boolean clientsAttached = checkClientsAttachedToPolicy(appReqBlock);

        if (! clientsAttached)
        {
            appReqBlock.getSessionBean("quoteMainSessionBean").putValue("quoteMessage", "Clients Must Be Entered");

            String productType = checkProductType(optionId);

            return getMainReturnPage(productType);
        }

        Iterator clientIT = clientPageBeans.values().iterator();

        while (clientIT.hasNext())
        {
            PageBean clientBean = (PageBean) clientIT.next();

            String relationshipType = clientBean.getValue("relationshipInd");
            String optionCode = clientBean.getValue("optionId");
            String allocationPct = clientBean.getValue("allocationPercent");
            String allocationDollars = clientBean.getValue("allocationDollars");
            String splitEqualInd = Util.initString(clientBean.getValue("splitEqualInd"), "N");

            String overrideStatus = clientBean.getValue("overrideStatus");
            if (!overrideStatus.equalsIgnoreCase("D"))
            {
                if (relationshipType.equalsIgnoreCase("PAY"))
                {
                    if (!allocationPct.equals(""))
                    {
                        if (payeeAllocPctHT.containsKey(optionCode))
                        {
                            EDITBigDecimal coverageAllocPct = Util.roundAllocation(new EDITBigDecimal((String) payeeAllocPctHT.get(optionCode)));
                            coverageAllocPct = coverageAllocPct.addEditBigDecimal(Util.roundAllocation(new EDITBigDecimal(allocationPct)));
                            payeeAllocPctHT.put(optionCode, Util.roundAllocation(coverageAllocPct) + "");
                        }
                        else
                        {
                            payeeAllocPctHT.put(optionCode, allocationPct);
                        }
                    }
                }

                if (relationshipType.equalsIgnoreCase("CBE"))
                {
                    if (((!allocationPct.equals("") && new EDITBigDecimal(allocationPct).isGT("0")) ||
                         (!allocationDollars.equals("") && new EDITBigDecimal(allocationDollars).isGT("0"))) &&
                        splitEqualInd.equalsIgnoreCase("N"))
                    {
                        if (!allocationPct.equals("") && new EDITBigDecimal(allocationPct).isGT("0"))
                        {
                            if (cbeAllocPctHT.containsKey(optionCode))
                            {
                                EDITBigDecimal coverageAllocPct = Util.roundAllocation(new EDITBigDecimal((String) cbeAllocPctHT.get(optionCode)));
                                coverageAllocPct = coverageAllocPct.addEditBigDecimal(Util.roundAllocation(new EDITBigDecimal(allocationPct)));
                                cbeAllocPctHT.put(optionCode, Util.roundAllocation(coverageAllocPct) + "");
                            }
                            else
                            {
                                cbeAllocPctHT.put(optionCode, allocationPct);
                            }
                        }
                        else if (!allocationDollars.equals("") && new EDITBigDecimal(allocationDollars).isGT("0"))
                        {
                            if (cbeAllocDollHT.containsKey(optionCode))
                            {
                                EDITBigDecimal coverageAllocDoll = new EDITBigDecimal((String) cbeAllocDollHT.get(optionCode));
                                coverageAllocDoll = coverageAllocDoll.addEditBigDecimal(new EDITBigDecimal(allocationDollars));
                                cbeAllocDollHT.put(optionCode, coverageAllocDoll.toString());
                            }
                            else
                            {
                                cbeAllocDollHT.put(optionCode, allocationDollars);
                            }
                        }
                    }
                }

                if (relationshipType.equalsIgnoreCase("ANN"))
                {
                    annuitantFound = true;
                }
                if (relationshipType.equalsIgnoreCase("OWN"))
                {
                    ownerFound = true;
                }
                if (relationshipType.equalsIgnoreCase("Insured"))
                {
                    insuredFound = true;
                }
            }
        }

        String productType = checkProductType(optionId);

        String mainReturnPage = getMainReturnPage(productType);

        if (productType == null)
        {
            if (!annuitantFound || !ownerFound)
            {
                appReqBlock.getSessionBean("quoteMainSessionBean").
                                putValue("quoteMessage", "Client Roles Of Annuitant and Owner Must Be Entered");

                return mainReturnPage;
            }
        }
        else if (productType.equalsIgnoreCase(DEFERRED_ANNUITY))
        {
            if (!annuitantFound || !ownerFound)
            {
                appReqBlock.getSessionBean("quoteMainSessionBean").
                                   putValue("quoteMessage", "Client Roles Of Annuitant and Owner Must Be Entered");

                return mainReturnPage;
            }
        }
        else if (productType.equalsIgnoreCase(NON_TRAD_LIFE) || productType.equalsIgnoreCase(TRADITIONAL) || productType.equalsIgnoreCase(UNIVERSAL_LIFE) ||
        		productType.equalsIgnoreCase(AH))
        {
            if (!insuredFound || !ownerFound)
            {
                appReqBlock.getSessionBean("quoteMainSessionBean").
                                   putValue("quoteMessage", "Client Roles Of Insured and Owner Must Be Entered");

                return mainReturnPage;
            }
        }
        else if (!annuitantFound || !ownerFound)
        {
            appReqBlock.getSessionBean("quoteMainSessionBean").
                            putValue("quoteMessage", "Client Roles Of Annuitant and Owner Must Be Entered");

            return mainReturnPage;
        }

        Iterator payeeHTKeys = payeeAllocPctHT.keySet().iterator();
        Iterator cbeHTKeys = cbeAllocPctHT.keySet().iterator();
        Iterator cbeDollarHTKeys = cbeAllocDollHT.keySet().iterator();
        boolean totalPayeeCoverageAllocation100 = true;
        boolean totalCBECoverageAllocation100 = true;
        String payeeAllocationCoverage = "";
        String cbeAllocationCoverage = "";

        while (payeeHTKeys.hasNext())
        {
            String coverage = (String) payeeHTKeys.next();

            double totalAllocation = Double.parseDouble((String) payeeAllocPctHT.get(coverage));

            if (totalAllocation != 1)
            {
                totalPayeeCoverageAllocation100 = false;

                if (Util.isANumber(coverage))
                {
                    payeeAllocationCoverage = codeTableWrapper.getCodeTableEntry(Long.parseLong(coverage)).getCode();
                }
                else
                {
                    payeeAllocationCoverage = coverage;
                }
            }
        }

        while (cbeHTKeys.hasNext())
        {
            String coverage = (String) cbeHTKeys.next();

            double totalAllocation = Double.parseDouble((String) cbeAllocPctHT.get(coverage));

            if (totalAllocation != 1)
            {
                totalCBECoverageAllocation100 = false;

                if (Util.isANumber(coverage))
                {
                    cbeAllocationCoverage = codeTableWrapper.getCodeTableEntry(Long.parseLong(coverage)).getCode();
                }
                else
                {
                    cbeAllocationCoverage = coverage;
                }
            }
        }

        EDITBigDecimal totalCBEDollarAlloc = new EDITBigDecimal();
        while (cbeDollarHTKeys.hasNext())
        {
            String coverage = (String) cbeDollarHTKeys.next();

            totalCBEDollarAlloc = totalCBEDollarAlloc.addEditBigDecimal(new EDITBigDecimal((String) cbeAllocDollHT.get(coverage)));
        }

        if (productStructureId.equals("") || productStructureId.equalsIgnoreCase("Please Select"))
        {
            appReqBlock.getSessionBean("quoteMainSessionBean").
                    putValue("quoteMessage", "Company Structure Must Be Selected When Performing A Quote");

            return mainReturnPage;
        }
//        COMMENTED OUT.  OLD CODE HAD DATE FIELDS AS ZEROES WHEN THE CONTRACT LOADED SO THIS SECTION OF CODE
//        WAS NEVER EXECUTED.  WE NO LONGER HAVE ZERO DATES AND WE DON'T WANT THIS CHECK ANY MORE (WELL, WEREN'T NOT
//        SURE YET WHICH IS WHY IT IS COMMENTED OUT AND NOT DELETED
//        else if (effectiveMonth.equals("") || effectiveDay.equals("") || effectiveYear.equals(""))
//        {
//            appReqBlock.getSessionBean("quoteMainSessionBean").
//                    putValue("quoteMessage", "Effective Date Must Be Entered");
//
//            if (optionId.equalsIgnoreCase("DFA"))
//            {
//                return QUOTE_DEFERRED_ANNUITY_MAIN;
//            }
//            else if (optionId.equalsIgnoreCase("VL") || optionId.equalsIgnoreCase("UL") || optionId.equalsIgnoreCase("TL"))
//            {
//                return QUOTE_LIFE_MAIN;
//            }
//            else
//            {
//                return QUOTE_COMMIT_MAIN;
//            }
//        }
//        THIS SECTION WAS COMMENTED OUT TOO.  THE CHECK FOR START DATE BEING BEFORE EFFECTIVE DATE IS ONLY VALID FOR
//        PAYOUTS.  THIS CODE NEVER CHECKS FOR THE CONTRACT TYPE SO SUSPICIOUS THAT THIS IS NOT USED ANY MORE AND THE
//        REAL CHECK IS ELSEWHERE IN THE SYSTEM.
//        else if (ceStartDate != null && ceStartDate.before(ceEffDate))
//        {
//            appReqBlock.getSessionBean("quoteMainSessionBean").
//                    putValue("quoteMessage", "Start Date Cannot Be Less Than Effective Date");
//
//            if (optionId.equalsIgnoreCase("DFA"))
//            {
//                return QUOTE_DEFERRED_ANNUITY_MAIN;
//            }
//            else if (optionId.equalsIgnoreCase("VL") || optionId.equalsIgnoreCase("UL") || optionId.equalsIgnoreCase("TL"))
//            {
//                return QUOTE_LIFE_MAIN;
//            }
//            else
//            {
//                return QUOTE_COMMIT_MAIN;
//            }
//        }
        else if (!fundsFound && optionId.equalsIgnoreCase("VL"))
        {
            appReqBlock.getHttpServletRequest().
                    setAttribute("investmentMessage", "At Least One Fund Must Be Entered For The Contract.");

            PageBean formBean = appReqBlock.getSessionBean("quoteMainSessionBean").getPageBean("formBean");

            String coStructId = formBean.getValue("companyStructureId");

            if (!coStructId.equals("") && !coStructId.equalsIgnoreCase("Please Select"))
            {
                UIFilteredFundVO[] uiFilteredFundVOs = new UtilitiesForTran().buildUIFilteredFundVO(appReqBlock, coStructId);
                appReqBlock.getHttpServletRequest().setAttribute("uiFilteredFundVOs", uiFilteredFundVOs);
            }

            return QUOTE_COMMIT_INVESTMENTS;
        }
        else if (!totalFundAllocation100)
        {
            appReqBlock.getHttpServletRequest().
                    setAttribute("investmentMessage", "Total Fund Allocation for " + allocationCoverage + " Must Be 100%(1.0)");

            PageBean formBean = appReqBlock.getSessionBean("quoteMainSessionBean").getPageBean("formBean");

            String coStructId = formBean.getValue("companyStructureId");

            if (!coStructId.equals("") && !coStructId.equalsIgnoreCase("Please Select"))
            {
                UIFilteredFundVO[] uiFilteredFundVOs = new UtilitiesForTran().buildUIFilteredFundVO(appReqBlock, coStructId);
                appReqBlock.getHttpServletRequest().setAttribute("uiFilteredFundVOs", uiFilteredFundVOs);
            }

            return QUOTE_COMMIT_INVESTMENTS;
        }
        else if (!totalPayeeCoverageAllocation100)
        {
            appReqBlock.getHttpServletRequest().
                    setAttribute("payeeMessage", "Total Payee Allocation for " + payeeAllocationCoverage + " Must Be 100%(1.0)");

            return QUOTE_COMMIT_PAYEE;
        }
        else if (!totalCBECoverageAllocation100 && totalCBEDollarAlloc.equals(new EDITBigDecimal()))
        {
            appReqBlock.getHttpServletRequest().
                    setAttribute("beneMessage",  "Total Contingent Bene Allocation for " + cbeAllocationCoverage + " Must Be 100%(1.0)");

            return QUOTE_COMMIT_NON_PAYEE;
        }
        else
        {
            return "";
        }
    }

    private String confirmQuoteDelete() throws Exception
    {
        // Check for authorization
        new NewBusinessUseCaseComponent().deleteNewBusinessContract();

        return CONFIRM_QUOTE_DELETE_DIALOG;
    }

    protected String showPayeeDialog(AppReqBlock appReqBlock) throws Exception
    {
        // The current page will have the transactionId
        SessionBean stateBean = appReqBlock.getSessionBean("quoteStateBean");
        String currentPage = stateBean.getValue("currentPage");

        savePreviousPageFormBean(appReqBlock, currentPage);

        String transactionId = appReqBlock.getFormBean().getValue("transactionId");

        appReqBlock.getHttpServletRequest().setAttribute("transactionId", transactionId);

        return QUOTE_COMMIT_PAYEE_DIALOG;
    }

    protected String closePayeeShowSchedEvent(AppReqBlock appReqBlock) throws Exception
    {
        new QuoteScheduledEventTableModel(appReqBlock);

        return QUOTE_SCHED_EVENTS;
    }

    protected String updatePayeeOverride(AppReqBlock appReqBlock) throws Exception
    {
        CodeTableWrapper codeTableWrapper = CodeTableWrapper.getSingleton();

        VMID vmid = new VMID();

        PageBean formBean = appReqBlock.getFormBean();
        PageBean payeeBean = null;

        String key = formBean.getValue("key");
        String transactionId = formBean.getValue("transactionId");
        String payeeClientId = formBean.getValue("payeeClientId");

        if (payeeClientId.length() > 0)
        {
            if (key.equals(""))
            {
                payeeBean = new PageBean();

                String uniqueId = vmid.toString();
                key = transactionId + "_" + uniqueId;

                payeeBean.putValue("status", "new");
            }
            else
            {
                payeeBean = appReqBlock.getSessionBean("quotePayeeOverrides").getPageBean(key);
                payeeBean.putValue("status", "modified");
            }

            String trxPayeeOverridePK = formBean.getValue("trxPayeeOverridePK");
            String trxBankOverridePK = formBean.getValue("trxBankOverridePK");
            String trxWithholdingOverridePK = formBean.getValue("trxWithholdingOverridePK");
            String percent = formBean.getValue("percent");
            String dollars = formBean.getValue("dollars");

            String bankAcctNumberOvrd = formBean.getValue("payeeBankAcctNumberOvrd");
            String bankRoutingNbrOvrd = formBean.getValue("payeeBankRoutingNbrOvrd");
            String bankAcctTypeOvrd = formBean.getValue("payeeBankAcctTypeOvrd");

            if (bankAcctTypeOvrd.equalsIgnoreCase("Please Select") || bankAcctTypeOvrd.equals(""))
            {
                bankAcctTypeOvrd = "";
            }
            else
            {
                if (Util.isANumber(bankAcctTypeOvrd))
                {
                    bankAcctTypeOvrd = codeTableWrapper.getCodeTableEntry(Long.parseLong(bankAcctTypeOvrd)).getCode();
                }
            }

            String preNoteDate = formBean.getValue("payeePreNoteDate");

            String stateWithholding = formBean.getValue("stateWithholding");
            String stateWithholdingPct = formBean.getValue("stateWithholdingPct");
            String federalWithholding = formBean.getValue("federalWithholding");
            String federalWithholdingPct = formBean.getValue("federalWithholdingPct");
            String cityWithholding = formBean.getValue("cityWithholding");
            String countyWithholding = formBean.getValue("countyWithholding");
            String disbursementSource = formBean.getValue("payeeDisbursementSource");

            if (disbursementSource.equalsIgnoreCase("Please Select") || disbursementSource.equals(""))
            {
                disbursementSource = "";
            }
            else
            {
                if (Util.isANumber(disbursementSource))
                {
                    disbursementSource = codeTableWrapper.getCodeTableEntry(Long.parseLong(disbursementSource)).getCode();
                }
            }

            String federalWithholdingIndStatus = formBean.getValue("federalWithholdingIndStatus");
            String stateWithholdingIndStatus = formBean.getValue("stateWithholdingIndStatus");
            String countyWithholdingIndStatus = formBean.getValue("countyWithholdingIndStatus");
            String cityWithholdingIndStatus = formBean.getValue("cityWithholdingIndStatus");

            payeeBean.putValue("trxPayeeOverridePK", trxPayeeOverridePK);
            payeeBean.putValue("trxBankOverridePK", trxBankOverridePK);
            payeeBean.putValue("trxWithholdingOverridePK", trxWithholdingOverridePK);
            payeeBean.putValue("payeeClientId", payeeClientId);
            payeeBean.putValue("payeePercent", percent);
            payeeBean.putValue("payeeDollars", dollars);
            payeeBean.putValue("payeeBankAcctNumberOvrd", bankAcctNumberOvrd);
            payeeBean.putValue("payeeBankRoutingNbrOvrd", bankRoutingNbrOvrd);
            payeeBean.putValue("payeeBankAcctTypeOvrd", bankAcctTypeOvrd);
            payeeBean.putValue("payeePreNoteDate", preNoteDate);
            payeeBean.putValue("payeeStateWithholding", stateWithholding);
            payeeBean.putValue("payeeStateWithholdingPct", stateWithholdingPct);
            payeeBean.putValue("payeeFederalWithholding", federalWithholding);
            payeeBean.putValue("payeeFederalWithholdingPct", federalWithholdingPct);
            payeeBean.putValue("payeeCityWithholding", cityWithholding);
            payeeBean.putValue("payeeCountyWithholding", countyWithholding);
            payeeBean.putValue("payeeDisbursementSource", disbursementSource);
            payeeBean.putValue("federalWithholdingIndStatus", federalWithholdingIndStatus);
            payeeBean.putValue("stateWithholdingIndStatus", stateWithholdingIndStatus);
            payeeBean.putValue("countyWithholdingIndStatus", countyWithholdingIndStatus);
            payeeBean.putValue("cityWithholdingIndStatus", cityWithholdingIndStatus);
            payeeBean.putValue("key", key);
            payeeBean.putValue("transactionId", transactionId);

            appReqBlock.getSessionBean("quotePayeeOverrides").putPageBean(key, payeeBean);
            appReqBlock.getHttpServletRequest().setAttribute("formBean", payeeBean);
            appReqBlock.getHttpServletRequest().setAttribute("transactionId", transactionId);
        }

        return QUOTE_COMMIT_PAYEE_DIALOG;
    }

    protected String showPayeeDetailSummary(AppReqBlock appReqBlock) throws Exception
    {
        String key = appReqBlock.getFormBean().getValue("key");

        String transactionId = appReqBlock.getFormBean().getValue("transactionId");

        SessionBean payeeOverrides = appReqBlock.getSessionBean("quotePayeeOverrides");

        PageBean payeePageBean = payeeOverrides.getPageBean(key);

        appReqBlock.getHttpServletRequest().setAttribute("formBean", payeePageBean);

        appReqBlock.getHttpServletRequest().setAttribute("transactionId", transactionId);

        return QUOTE_COMMIT_PAYEE_DIALOG;
    }

    protected String cancelPayeeOverride(AppReqBlock appReqBlock) throws Exception
    {
        PageBean formBean = appReqBlock.getFormBean();
        String key = formBean.getValue("key");
        String transactionId = formBean.getValue("transactionId");

        PageBean payeeBean = appReqBlock.getSessionBean("quotePayeeOverrides").getPageBean(key);

        appReqBlock.getHttpServletRequest().setAttribute("transactionId", transactionId);
        appReqBlock.getHttpServletRequest().setAttribute("formBean", payeeBean);

        return QUOTE_COMMIT_PAYEE_DIALOG;
    }

    protected String deletePayeeOverride(AppReqBlock appReqBlock) throws Exception
    {
        PageBean formBean = appReqBlock.getFormBean();
        String key = formBean.getValue("key");
        String transactionId = formBean.getValue("transactionId");

        SessionBean payeeOverrides = appReqBlock.getSessionBean("quotePayeeOverrides");

        PageBean payeeBean = payeeOverrides.getPageBean(key);

        payeeBean.putValue("status", "deleted");

        Map payeeBeans = payeeOverrides.getPageBeans();

        Iterator it = payeeBeans.values().iterator();
        boolean payeeOverrideExists = false;

        while (it.hasNext())
        {
            PageBean iBean = (PageBean) it.next();

            if (!iBean.getValue("status").equals("deleted"))
            {
                payeeOverrideExists = true;
                break;
            }
        }

        if (payeeOverrideExists)
        {
            appReqBlock.getSessionBean("quoteTransactions").getPageBean(transactionId).putValue("payeeIndStatus", "checked");
        }
        else
        {
            appReqBlock.getSessionBean("quoteTransactions").getPageBean(transactionId).putValue("payeeIndStatus", "");
        }

        appReqBlock.getHttpServletRequest().setAttribute("transactionId", transactionId);

        return QUOTE_COMMIT_PAYEE_DIALOG;
    }

    private String findClientsByNameDOB(AppReqBlock appReqBlock) throws Exception
    {
        PageBean formBean = appReqBlock.getFormBean();
        String dob = formBean.getValue("dob");

        appReqBlock.setReqParm("searchType", search.business.Lookup.FIND_BY_CLIENT_NAME);

        findClients(appReqBlock);

        SessionBean contractClientAddSessionBean = appReqBlock.getSessionBean("contractClientAddSessionBean");

        List beansToRemove = new ArrayList();

        if (contractClientAddSessionBean.hasPageBeans())
        {
            Map clientBeans = contractClientAddSessionBean.getPageBeans();
            Set clientBeanKeys = clientBeans.keySet();
            Iterator it = clientBeanKeys.iterator();
            while (it.hasNext())
            {
                String clientDetailPK = (String) it.next();
                PageBean clientDetailPageBean = (PageBean) clientBeans.get(clientDetailPK);
                String clientDetailDOB = DateTimeUtil.formatMMDDYYYYToYYYYMMDD(clientDetailPageBean.getValue("dateOfBirth"));

                if (clientDetailDOB != null)
                {
                    EDITDate edClientDetailDOB = new EDITDate(clientDetailDOB);

                    if (!edClientDetailDOB.equals(new EDITDate(dob)))
                    {
                        beansToRemove.add(clientDetailPK);
                    }
                }
            }
        }

        for (int i = 0; i < beansToRemove.size(); i++)
        {
            contractClientAddSessionBean.removePageBean((String) beansToRemove.get(i));
        }

        if (!contractClientAddSessionBean.hasPageBeans())
        {
            contractClientAddSessionBean.putValue("searchStatus", "noData");
        }

        return CONTRACT_CLIENT_ADD_DIALOG;
    }

    private String findClients(AppReqBlock appReqBlock) throws Exception
    {
        SessionBean contractClientAddSessionBean = appReqBlock.getSessionBean("contractClientAddSessionBean");
        contractClientAddSessionBean.clearState();

        search.business.Lookup searchLookup = new search.component.LookupComponent();

        ClientDetailVO[] clientDetailVOs = null;

        SearchResponseVO[] searchResponseVOs = searchLookup.searchForClients(appReqBlock);
        if (searchResponseVOs != null)
        {
            searchResponseVOs = filterForAuthorization(appReqBlock, searchResponseVOs);

            if (searchResponseVOs != null)
            {
                clientDetailVOs = getClientDetailsAfterSearch(searchResponseVOs);
            }
        }

        if (clientDetailVOs != null)
        {
            for (int c = 0; c < clientDetailVOs.length; c++)
            {
                PageBean clientDetailPageBean = new PageBean();

                String clientDetailPK = clientDetailVOs[c].getClientDetailPK() + "";

                clientDetailPageBean.putValue("clientDetailPK", clientDetailPK);
                clientDetailPageBean.putValue("lastName", clientDetailVOs[c].getLastName());
                clientDetailPageBean.putValue("firstName", clientDetailVOs[c].getFirstName());
                clientDetailPageBean.putValue("middleName", clientDetailVOs[c].getMiddleName());
                clientDetailPageBean.putValue("namePrefix", clientDetailVOs[c].getNamePrefix());
                clientDetailPageBean.putValue("nameSuffix", clientDetailVOs[c].getNameSuffix());
                clientDetailPageBean.putValue("corporateName", clientDetailVOs[c].getCorporateName());
                clientDetailPageBean.putValue("taxIdNumber", clientDetailVOs[c].getTaxIdentification());
                clientDetailPageBean.putValue("dateOfBirth", DateTimeUtil.formatYYYYMMDDToMMDDYYYY(clientDetailVOs[c].getBirthDate()));
                clientDetailPageBean.putValue("clientStatus", Util.initString(clientDetailVOs[c].getStatusCT(), ""));
                ClientAddressVO[] clientAddresses = clientDetailVOs[c].getClientAddressVO();

                if (clientAddresses != null)
                {
                    for (int a = 0; a < clientAddresses.length; a++)
                    {
                        String addressType = clientAddresses[a].getAddressTypeCT();

                        if (addressType.equalsIgnoreCase("PrimaryAddress"))
                        {
                            String termDate = clientAddresses[a].getTerminationDate();

                            if (termDate == null || termDate.equals(EDITDate.DEFAULT_MAX_DATE))
                            {
                                clientDetailPageBean.putValue("city", clientAddresses[a].getCity());
                                clientDetailPageBean.putValue("state", clientAddresses[a].getStateCT());
                                clientDetailPageBean.putValue("addressLine1", clientAddresses[a].getAddressLine1());
                                clientDetailPageBean.putValue("zipCode", clientAddresses[a].getZipCode());

                                break;
                            }
                        }
                    }
                }

                contractClientAddSessionBean.putPageBean(clientDetailPK, clientDetailPageBean);
                contractClientAddSessionBean.putValue("searchStatus", "");
            }
        }
        else
        {
            contractClientAddSessionBean.putValue("searchStatus", "noData");
        }

        return CONTRACT_CLIENT_ADD_DIALOG;
    }

    private String showQuoteRequirements(AppReqBlock appReqBlock) throws Exception
    {
        SessionBean stateBean = appReqBlock.getSessionBean("quoteStateBean");
        String currentPage = stateBean.getValue("currentPage");

        savePreviousPageFormBean(appReqBlock, currentPage);

        // clear top part of page
        appReqBlock.getHttpSession().removeAttribute("selectedContractRequirementVO");

        ContractRequirementVO[] contractRequirementVO = getRequirements(appReqBlock);

        if (contractRequirementVO != null)
        {
            appReqBlock.getHttpSession().setAttribute("contractRequirementVO", contractRequirementVO);
        }

        return QUOTE_REQUIREMENTS;
    }

    private String showRequirementDetail(AppReqBlock appReqBlock) throws Exception
    {
        PageBean formBean = appReqBlock.getFormBean();
//        String filteredRequirementPK = formBean.getValue("selectedFilteredRequirementPK");
        String contractRequirementPK = formBean.getValue("selectedContractRequirementPK");
        ContractRequirementVO[] contractRequirementVOs = (ContractRequirementVO[]) appReqBlock.getHttpSession().getAttribute("contractRequirementVO");
        ContractRequirementVO contractRequirementVO = null;

        for (int i = 0; i < contractRequirementVOs.length; i++)
        {
            if (contractRequirementPK.equals(contractRequirementVOs[i].getContractRequirementPK() + ""))
            {
                contractRequirementVO = contractRequirementVOs[i];
            }
        }

        appReqBlock.getHttpSession().setAttribute("selectedContractRequirementVO", contractRequirementVO);

        return QUOTE_REQUIREMENTS;
    }

    private String saveRequirementToSummary(AppReqBlock appReqBlock) throws Exception
    {
        new NewBusinessUseCaseComponent().updateRequirements();

        PageBean formBean = appReqBlock.getFormBean();
        ContractRequirementVO singleContractRequirementVO = (ContractRequirementVO)appReqBlock.getHttpSession().getAttribute("selectedContractRequirementVO");

        ContractRequirementVO[] contractRequirementVO = (ContractRequirementVO[]) appReqBlock.getHttpSession().getAttribute("contractRequirementVO");

        String effectiveDate = DateTimeUtil.formatMMDDYYYYToYYYYMMDD(formBean.getValue("effectiveDate"));
        String status = formBean.getValue("status");
        CodeTableVO codeTableVO = CodeTableWrapper.getSingleton().getCodeTableEntry(Long.parseLong(status));

        String receivedDate = DateTimeUtil.formatMMDDYYYYToYYYYMMDD(formBean.getValue("receivedDate"));
        String executedDate = DateTimeUtil.formatMMDDYYYYToYYYYMMDD(formBean.getValue("executedDate"));
        String requirementInformation = Util.initString(formBean.getValue("requirementInfo"), null);

        singleContractRequirementVO.setEffectiveDate(effectiveDate);
        singleContractRequirementVO.setRequirementStatusCT(codeTableVO.getCode());
        singleContractRequirementVO.setReceivedDate(receivedDate);
        singleContractRequirementVO.setExecutedDate(executedDate);
        singleContractRequirementVO.setRequirementInformation(requirementInformation);

        int followupDays = 0;

        for (int i = 0; i < contractRequirementVO.length; i++)
        {
            FilteredRequirement filteredRequirement = FilteredRequirement.findByPK(new Long(contractRequirementVO[i].getFilteredRequirementFK()));
            if (singleContractRequirementVO.getContractRequirementPK() == contractRequirementVO[i].getContractRequirementPK())
            {
                contractRequirementVO[i] = singleContractRequirementVO;
                followupDays = filteredRequirement.getRequirement().getFollowupDays();
                break;
            }
        }

        appReqBlock.getHttpSession().setAttribute("contractRequirementVO", contractRequirementVO);

        setRequirementDates(appReqBlock, effectiveDate, followupDays);

        return QUOTE_REQUIREMENTS;
    }

    private String showBuildRequirementDialog(AppReqBlock appReqBlock) throws Exception
    {
        String activeProductStructurePK = Util.initString(appReqBlock.getReqParm("activeCompanyStructurePK"), null);

        engine.business.Lookup calculatorLookup = new engine.component.LookupComponent();

        ProductStructureVO cloneFromCompanyStructureVO = calculatorLookup.findProductStructureVOByPK(Long.parseLong(activeProductStructurePK), false, null)[0];

        appReqBlock.getHttpServletRequest().setAttribute("cloneFromCompanyStructureVO", cloneFromCompanyStructureVO);

        populateRequestWithCompanyRequirementRelationsSummaryVOs(appReqBlock);

        return BUILD_REQUIREMENT_DIALOG;
    }

    /**
     * Clones the set of Requirements from one ProductStructure to another CompanyStructure.
     * @param appReqBlock
     * @return
     */
    private String cloneRequirements(AppReqBlock appReqBlock) throws Exception
    {
        String activeCompanyStructurePK = Util.initString(appReqBlock.getReqParm("activeCompanyStructurePK"), null);

        String cloneToCompanyStructurePK = Util.initString(appReqBlock.getReqParm("cloneToCompanyStructurePK"), null);

        contract.business.Contract contractComponent = new contract.component.ContractComponent();

        String responseMessage = null;

        try
        {
            contractComponent.cloneRequirements(Long.parseLong(activeCompanyStructurePK), Long.parseLong(cloneToCompanyStructurePK));

            appReqBlock.setReqParm("activeCompanyStructurePK", cloneToCompanyStructurePK);

            responseMessage = "Requirement(s) Successfully Cloned";
        }
        catch (Exception e)
        {
            appReqBlock.setReqParm("activeCompanyStructurePK", activeCompanyStructurePK);

            responseMessage = e.getMessage();
        }
        finally
        {
            appReqBlock.getHttpServletRequest().setAttribute("responseMessage", responseMessage);
        }

        return showBuildRequirementDialog(appReqBlock);
    }

    protected String cancelRequirement(AppReqBlock appReqBlock) throws Exception
    {
//        PageBean quoteMainFormBean = appReqBlock.getSessionBean("quoteMainSessionBean").getPageBean("formBean");

        appReqBlock.getHttpSession().removeAttribute("selectedContractRequirementVO");

        return QUOTE_REQUIREMENTS;
    }

    private String deleteSelectedRequirement(AppReqBlock appReqBlock) throws Exception
    {
        PageBean formBean = appReqBlock.getFormBean();
//        String filteredRequirementPK = formBean.getValue("selectedFilteredRequirementPK");
        String contractRequirementPK = formBean.getValue("selectedContractRequirementPK");

        ContractRequirementVO[] contractRequirementVOs = (ContractRequirementVO[]) appReqBlock.getHttpSession().getAttribute("contractRequirementVO");

        for (int i = 0; i < contractRequirementVOs.length; i++)
        {
            if (contractRequirementPK.equals(contractRequirementVOs[i].getContractRequirementPK() + ""))
            {
                contractRequirementVOs[i].setVoShouldBeDeleted(true);
            }
        }

        appReqBlock.getHttpSession().removeAttribute("selectedContractRequirementVO");
        return QUOTE_REQUIREMENTS;
    }

    private String showManualRequirementSelectionDialog(AppReqBlock appReqBlock) throws Exception
    {
        contract.business.Lookup contractLookup = new contract.component.LookupComponent();
        String productStructurePK = appReqBlock.getSessionBean("quoteMainSessionBean").getPageBean("formBean").getValue("companyStructureId");

        RequirementVO[] requirementVOs = contractLookup.findRequirementByProductStructurePKAndManualInd(Long.parseLong(productStructurePK), "Y", false, null);

        if (requirementVOs != null)
        {
            appReqBlock.getHttpSession().setAttribute("manualRequirementVOs", requirementVOs);
        }

        return MANUAL_REQUIREMENT_SELECTION_DIALOG;
    }

    private String showManualRequirementDescription(AppReqBlock appReqBlock) throws Exception
    {
        String selectedRequirementPK = appReqBlock.getFormBean().getValue("requirementPK");
        appReqBlock.getHttpServletRequest().setAttribute("selectedRequirementPK", selectedRequirementPK);

        return MANUAL_REQUIREMENT_SELECTION_DIALOG;
    }

    private String saveManualRequirement(AppReqBlock appReqBlock) throws Exception
    {
        String selectedRequirementPK = appReqBlock.getFormBean().getValue("selectedRequirementPK");
        String description = appReqBlock.getFormBean().getValue("description");
        String requirementInfo = appReqBlock.getFormBean().getValue("requirementInfo");
        String productStructurePK = appReqBlock.getSessionBean("quoteMainSessionBean").getPageBean("formBean").getValue("companyStructureId");

        contract.business.Lookup contractLookup = new contract.component.LookupComponent();
        RequirementVO[] requirementVOs = (RequirementVO[]) appReqBlock.getHttpSession().getAttribute("manualRequirementVOs");

        ContractRequirementVO[] contractRequirementVOs =
                (ContractRequirementVO[]) appReqBlock.getHttpSession().getAttribute("contractRequirementVO");

        int followupDays = 0;
        String requirementId = null;
        contract.business.Contract contractComponent = new contract.component.ContractComponent();

        for (int r = 0; r < requirementVOs.length; r++)
        {
            if ((requirementVOs[r].getRequirementPK() + "").equals(selectedRequirementPK))
            {
                followupDays = requirementVOs[r].getFollowupDays();
                requirementId = requirementVOs[r].getRequirementId();
                break;
            }
        }

        FilteredRequirementVO[] filteredRequirementVO =
                contractLookup.findFilteredRequirementByProductStructureAndRequirement(Long.parseLong(productStructurePK),
                        Long.parseLong(selectedRequirementPK), false, null);

        ContractRequirementVO contractRequirementVO = new ContractRequirementVO();
        contractRequirementVO.setFilteredRequirementFK(filteredRequirementVO[0].getFilteredRequirementPK());
        contractRequirementVO.setContractRequirementPK(contractComponent.getNextAvailableKey() * -1);
        contractRequirementVO.setRequirementInformation(Util.initString(requirementInfo, null));

        if (contractRequirementVOs != null && contractRequirementVOs.length > 0)
        {
            contractRequirementVO.setSegmentFK(contractRequirementVOs[0].getSegmentFK());
        }
        else
        {
            String segmentPK = appReqBlock.getSessionBean("quoteMainSessionBean").getPageBean("formBean").getValue("segmentPK");
            if (Util.isANumber(segmentPK))

            {
                contractRequirementVO.setSegmentFK(Long.parseLong(segmentPK));
            }
            else
            {
                contractRequirementVO.setSegmentFK(0);
            }
        }

        contractRequirementVO.setRequirementStatusCT("Outstanding");
        
        EDITDate effectiveDate = new EDITDate();
        
        BusinessCalendar businessCalendar = new BusinessCalendar();
        BusinessCalendarComponent busCalComp = new BusinessCalendarComponent();
        
        if(!busCalComp.isBusinessDay(effectiveDate))
        {
        	BusinessDay businessDay = businessCalendar.findNextBusinessDay(effectiveDate, 1);
        	effectiveDate = businessDay.getBusinessDate();
        }
        
        contractRequirementVO.setEffectiveDate(effectiveDate.getFormattedDate());
        EDITDate followupDate = effectiveDate.addDays(followupDays);
        contractRequirementVO.setFollowupDate(followupDate.getFormattedDate());

        //  If the requirementId of the selected Requirement is Requirement.REQUIREMENT_ID_TEXT, the user was allowed
        //  to set it on the page.  It gets stored in the FreeFormDescription of the ContractRequirement
        if (requirementId != null)
        {
            if (requirementId.equals(Requirement.REQUIREMENT_ID_TEXT))
            {
                contractRequirementVO.setFreeFormDescription(description);
            }
        }

        setRequirementDates(appReqBlock, contractRequirementVO.getEffectiveDate(), followupDays);

        List voInclusionList = new ArrayList();

        voInclusionList.add(FilteredRequirementVO.class);
        voInclusionList.add(RequirementVO.class);

        contractRequirementVO = contractLookup.composeContractRequirementVO(contractRequirementVO, voInclusionList);

        List contractRequirements = new ArrayList();

        if (contractRequirementVOs != null)
        {
            for (int i = 0; i < contractRequirementVOs.length; i++)
            {
                contractRequirements.add(contractRequirementVOs[i]);
            }
        }

        contractRequirements.add(contractRequirementVO);

        ContractRequirementVO[] updatedContractRequirementVOs = (ContractRequirementVO[]) contractRequirements.toArray(new ContractRequirementVO[contractRequirements.size()]);
        appReqBlock.getHttpSession().setAttribute("contractRequirementVO", updatedContractRequirementVOs);

        return QUOTE_REQUIREMENTS;
    }

    private String showRequirementTable(AppReqBlock appReqBlock) throws Exception
    {
        new NewBusinessUseCaseComponent().accessRequirements();

        contract.business.Lookup contractLookup = new contract.component.LookupComponent();

        RequirementVO[] requirementVOs = contractLookup.composeRequirementVOs(new ArrayList());

        if (requirementVOs != null)
        {
            appReqBlock.getHttpSession().setAttribute("requirementVOs", requirementVOs);
        }

        return REQUIREMENT_TABLE;
    }

    private String showRequirementDetailSummary(AppReqBlock appReqBlock) throws Exception
    {
        PageBean formBean = appReqBlock.getFormBean();

        String selectedRequirementPK = formBean.getValue("selectedRequirementPK");
        appReqBlock.getHttpServletRequest().setAttribute("selectedRequirementPK", selectedRequirementPK);

        populateRequestWithCompanyRequirementRelationsSummaryVOs(appReqBlock);

        return REQUIREMENT_TABLE;
    }

    private String clearRequirementFormForAddOrCancel(AppReqBlock appReqBlock) throws Exception
    {
        PageBean formBean = appReqBlock.getFormBean();

        formBean.putValue("selectedRequirementPK", "");

        return showRequirementDetailSummary(appReqBlock);
    }

    private String updateRequirement(AppReqBlock appReqBlock) throws Exception
    {
        CodeTableWrapper codeTableWrapper = CodeTableWrapper.getSingleton();

        PageBean formBean = appReqBlock.getFormBean();
        String requirementPK = formBean.getValue("requirementPK");
        String requirementId = formBean.getValue("requirementId");
        String description = formBean.getValue("description");
        String allowableStatus = formBean.getValue("allowableStatus");

        if (Util.isANumber(allowableStatus))
        {
            allowableStatus = codeTableWrapper.getCodeTableEntry(Long.parseLong(allowableStatus)).getCode();
        }

        String followupDays = formBean.getValue("followupDays");
        String manualInd = formBean.getValue("manualIndStatus");

        if (manualInd.equalsIgnoreCase("checked"))
        {
            manualInd = "Y";
        }
        else
        {
            manualInd = "N";
        }

        String agentViewInd = formBean.getValue("agentViewIndStatus");

        if (agentViewInd.equalsIgnoreCase("checked"))
        {
            agentViewInd = "Y";
        }
        else
        {
            agentViewInd = "N";
        }

        String updatePolicyDeliveryDateInd = formBean.getValue("updatePolicyDeliveryDateIndStatus");

        if (updatePolicyDeliveryDateInd.equals("checked"))
        {
            updatePolicyDeliveryDateInd = "Y";
        }
        else
        {
            updatePolicyDeliveryDateInd = "N";
        }

        String autoReceipt = formBean.getValue("autoReceiptIndStatus");

        if (autoReceipt.equals("checked"))
        {
            autoReceipt = "Y";
        }
        else
        {
            autoReceipt = "N";
        }

        String finalStatus = formBean.getValue("finalStatus");

        if (Util.isANumber(finalStatus))
        {
            finalStatus = codeTableWrapper.getCodeTableEntry(Long.parseLong(finalStatus)).getCode();
        }
        else
        {
            finalStatus = null;
        }

        RequirementVO requirementVO = new RequirementVO();
        requirementVO.setRequirementPK(Long.parseLong(requirementPK));
        requirementVO.setRequirementId(requirementId);
        requirementVO.setRequirementDescription(description);
        requirementVO.setAllowableStatusCT(allowableStatus);

        if (Util.isANumber(followupDays))
        {
            requirementVO.setFollowupDays(Integer.parseInt(followupDays));
        }
        else
        {
            requirementVO.setFollowupDays(0);
        }

        requirementVO.setManualInd(manualInd);
        requirementVO.setAgentViewInd(agentViewInd);
        requirementVO.setUpdatePolicyDeliveryDateInd(updatePolicyDeliveryDateInd);
        requirementVO.setFinalStatusCT(finalStatus);
        requirementVO.setAutoReceipt(autoReceipt);

        contract.business.Contract contractComponent = new contract.component.ContractComponent();
        contractComponent.saveRequirementVO(requirementVO);

        contract.business.Lookup contractLookup = new contract.component.LookupComponent();

        RequirementVO[] requirementVOs = contractLookup.composeRequirementVOs(new ArrayList());

        if (requirementVOs != null)
        {
            appReqBlock.getHttpSession().setAttribute("requirementVOs", requirementVOs);
        }

        formBean.putValue("selectedRequirementPK", "");

        return showRequirementDetailSummary(appReqBlock);
    }

    private String deleteRequirement(AppReqBlock appReqBlock) throws Exception
    {
        new NewBusinessUseCaseComponent().deleteRequirements();

        PageBean formBean = appReqBlock.getFormBean();

        String requirementPK = formBean.getValue("requirementPK");

        contract.business.Contract contractComponent = new contract.component.ContractComponent();
        RequirementVO[] requirementVOs = (RequirementVO[]) appReqBlock.getHttpSession().getAttribute("requirementVOs");

        if (requirementVOs != null)
        {
            for (int r = 0; r < requirementVOs.length; r++)
            {
                if ((requirementVOs[r].getRequirementPK() + "").equals(requirementPK))
                {
                    contractComponent.deleteVO(RequirementVO.class, requirementVOs[r].getRequirementPK(), false);
                    break;
                }
            }
        }

        contract.business.Lookup contractLookup = new contract.component.LookupComponent();

        requirementVOs = contractLookup.composeRequirementVOs(new ArrayList());

        if (requirementVOs != null)
        {
            appReqBlock.getHttpSession().setAttribute("requirementVOs", requirementVOs);
        }
        else
        {
            appReqBlock.getHttpSession().removeAttribute("requirementVOs");
        }

        formBean.putValue("selectedRequirementPK", "");

        return showRequirementDetailSummary(appReqBlock);
    }

    private String showRequirementRelationPage(AppReqBlock appReqBlock) throws Exception
    {
        populateRequestWithCompanyRequirementRelationsSummaryVOs(appReqBlock);

        return REQUIREMENT_RELATION_PAGE;
    }

    private String showRelation(AppReqBlock appReqBlock) throws Exception
    {
        String activeCompanyStructurePK = appReqBlock.getReqParm("activeCompanyStructurePK");

        populateRequestWithCompanyRequirementRelationsSummaryVOs(appReqBlock);

        appReqBlock.getHttpServletRequest().setAttribute("activeCompanyStructurePK", activeCompanyStructurePK);

        return REQUIREMENT_RELATION_PAGE;
    }

    protected String attachCompanyAndRequirement(AppReqBlock appReqBlock) throws Exception
    {
        contract.business.Contract contractComponent = new contract.component.ContractComponent();

        String activeCompanyStructurePK = appReqBlock.getReqParm("activeCompanyStructurePK");
        String selectedRequirementPKs = appReqBlock.getReqParm("selectedRequirementPKs");

        String[] requirementsToAttachTokens = Util.fastTokenizer(selectedRequirementPKs, ",");

        List requirementsToAttach = new ArrayList();

        for (int i = 0; i < requirementsToAttachTokens.length; i++)
        {
            if (Util.isANumber(requirementsToAttachTokens[i]))
            {
                requirementsToAttach.add(new Long(requirementsToAttachTokens[i]));
            }
        }

        String responseMessage = "";

        try
        {
            //attach product and rule
            contractComponent.attachRequirementsToProductStructure(Long.parseLong(activeCompanyStructurePK),
                    Util.convertLongToPrim((Long[]) requirementsToAttach.toArray(new Long[requirementsToAttach.size()])));
            responseMessage = "Requirement(s) Successfully Attached";
        }
        catch (Exception e)
        {
            responseMessage = e.getMessage();
        }
        finally
        {
            appReqBlock.getHttpServletRequest().setAttribute("responseMessage", responseMessage);

            appReqBlock.getHttpServletRequest().setAttribute("activeCompanyStructurePK", activeCompanyStructurePK);

            return showBuildRequirementDialog(appReqBlock);
        }
    }

    protected String detachCompanyAndRequirement(AppReqBlock appReqBlock) throws Exception
    {
        contract.business.Contract contractComponent = new contract.component.ContractComponent();

        String activeCompanyStructurePK = appReqBlock.getReqParm("activeCompanyStructurePK");
        String selectedRequirementPKs = appReqBlock.getReqParm("selectedRequirementPKs");

        String[] requirementsToDetachTokens = Util.fastTokenizer(selectedRequirementPKs, ",");

        List requirementsToDetach = new ArrayList();

        for (int i = 0; i < requirementsToDetachTokens.length; i++)
        {
            if (Util.isANumber(requirementsToDetachTokens[i]))
            {
                requirementsToDetach.add(new Long(requirementsToDetachTokens[i]));
            }
        }

        String responseMessage = "";

        try
        {
            //detach product and requirement
            contractComponent.detachRequirementsFromProductStructure(Long.parseLong(activeCompanyStructurePK),
                    Util.convertLongToPrim((Long[]) requirementsToDetach.toArray(new Long[requirementsToDetach.size()])));
            responseMessage = "Requirement(s) Successfully Detached";
        }
        catch(Exception e)
        {
            responseMessage = e.getMessage();
        }
        finally
        {
            appReqBlock.getHttpServletRequest().setAttribute("responseMessage", responseMessage);
            appReqBlock.getHttpServletRequest().setAttribute("activeCompanyStructurePK", activeCompanyStructurePK);

            return showBuildRequirementDialog(appReqBlock);
        }
    }

    private String cancelRelation(AppReqBlock appReqBlock) throws Exception
    {
        String selectedCompanyStructurePK = appReqBlock.getReqParm("selectedCompanyStructurePK");

        populateRequestWithCompanyRequirementRelationsSummaryVOs(appReqBlock);

        appReqBlock.getHttpServletRequest().setAttribute("selectedCompanyStructurePK", selectedCompanyStructurePK);

        return REQUIREMENT_RELATION_PAGE;
    }

    private String showHistory() throws Exception
    {
        return QUOTE_COMMIT_HISTORY;
    }

    private String showHistoryDetailSummary(AppReqBlock appReqBlock) throws Exception
    {
        PageBean quoteMainFormBean = appReqBlock.getSessionBean("quoteMainSessionBean").getPageBean("formBean");
        PageBean formBean = appReqBlock.getFormBean();

        String segmentPK = quoteMainFormBean.getValue("segmentPK");

        if (segmentPK.equals(""))
        {
            segmentPK = formBean.getValue("segmentPK");
        }

        String historyKey = appReqBlock.getFormBean().getValue("historyKey");

        if (historyKey.startsWith("ch_"))
        {
            historyKey = historyKey.substring(3);
            String idName = "";

            contract.business.Lookup contractLookup = new contract.component.LookupComponent();
            List voInclusionList = new ArrayList();
//            voInclusionList.add(ChangeHistoryVO.class);
            voInclusionList.add(InvestmentVO.class);
            voInclusionList.add(ContractClientVO.class);
            voInclusionList.add(ContractClientAllocationVO.class);

            SegmentVO segmentVO = contractLookup.composeSegmentVO(Long.parseLong(segmentPK), voInclusionList);
//            ChangeHistoryVO[] changeHistoryVOs = segmentVO.getChangeHistoryVO();
            ChangeHistoryVO[] changeHistoryVOs = contractLookup.getChangeHistoryForContract(segmentVO.getSegmentPK());

            for (int j = 0; j < changeHistoryVOs.length; j++)
            {
                if ((changeHistoryVOs[j].getChangeHistoryPK() + "").equals(historyKey))
                {
                    String fieldName = changeHistoryVOs[j].getFieldName();
                    String tableName = changeHistoryVOs[j].getTableName();
                    long modifiedTableKey = changeHistoryVOs[j].getModifiedRecordFK();

                    if (tableName != null)
                    {
                        if (tableName.equalsIgnoreCase("Investment"))
                        {
                            idName = getFundNames(appReqBlock, segmentVO, modifiedTableKey);

                            if (fieldName.equalsIgnoreCase("FilteredFundFK"))
                            {
                                changeHistoryVOs[j] = setChangeValues(changeHistoryVOs[j], idName);
                            }
                        }

                        if (tableName.equalsIgnoreCase("ContractClient") ||
                            tableName.equalsIgnoreCase("ContractClientAllocation"))
                        {
                            idName = findClient(tableName, modifiedTableKey, segmentVO, appReqBlock);

                            if (fieldName.equalsIgnoreCase("ClientRoleFK"))
                            {
                                changeHistoryVOs[j] = getClientIds(changeHistoryVOs[j], appReqBlock);
                            }
                        }
//                        else if (tableName.equalsIgnoreCase("Segment"))
//                                  {
//                                      if (changeHistoryVOs[j].getSegmentFK() != modifiedTableKey)
//                                      {
//                                            idName = getRiderName(modifiedTableKey);
//                                      }
//                  }            
                    }

                    break;
                }
            }
            appReqBlock.getHttpServletRequest().setAttribute("idName", idName);
            appReqBlock.getHttpServletRequest().setAttribute("selectedChangeHistoryPK", historyKey);

            return QUOTE_COMMIT_CHANGE_HISTORY;
        }
        else
        {
            appReqBlock.getHttpServletRequest().setAttribute("selectedEDITTrxHistoryPK", historyKey.substring(3));

            return QUOTE_COMMIT_HISTORY;
        }
    }
    /**
     * Get rider name for display on history page
     * @param modifiedTableKey
     * @return  name
     * @throws Exception
     */
//    private String getRiderName(long modifiedTableKey)  throws Exception
//    {
//        contract.business.Lookup contractLookup = new contract.component.LookupComponent();
//
//        SegmentVO[] segmentVO = contractLookup.findBySegmentPK(modifiedTableKey, false, null);
//
//        String riderName = segmentVO[0].getSegmentNameCT();
//
//        return riderName;
//    }

    protected String showChargesDialog(AppReqBlock appReqBlock) throws Exception {

        String historyKey = appReqBlock.getFormBean().getValue("historyKey");

        appReqBlock.getHttpServletRequest().setAttribute("editTrxHistoryPK", historyKey);

        return CHARGES_DIALOG;
    }

    protected String showCorrespondenceDialog(AppReqBlock appReqBlock) throws Exception
    {
        String editTrxPK = Util.initString(appReqBlock.getReqParm("editTrxPK"), null);
        EDITTrxVO[] editTrxVOs = (EDITTrxVO[]) appReqBlock.getHttpSession().getAttribute("editTrxVOs");
        EDITTrxCorrespondenceVO[] editTrxCorrespondenceVOs = null;

        if (editTrxPK != null && editTrxVOs != null)
        {
            for (int e = 0; e < editTrxVOs.length; e++)
            {
                if ((editTrxVOs[e].getEDITTrxPK() + "").equals(editTrxPK))
                {
                    editTrxCorrespondenceVOs = editTrxVOs[e].getEDITTrxCorrespondenceVO();
                    appReqBlock.getHttpServletRequest().setAttribute("editTrxCorrespondenceVOs", editTrxCorrespondenceVOs);
                }
            }
        }

        return CORRESPONDENCE_DIALOG;
    }

    protected String showContractSuspenseCreation(AppReqBlock appReqBlock) throws Exception
    {
        SessionBean stateBean = appReqBlock.getSessionBean("quoteStateBean");

        String previousPage = stateBean.getValue("currentPage");
        String lastPage = previousPage;
        if (previousPage.equals(DEPOSIT_DIALOG))
        {
            CodeTableWrapper codeTableWrapper = CodeTableWrapper.getSingleton();
            PageBean formBean = appReqBlock.getFormBean();
            String selectedDepositsPK = formBean.getValue("selectedDepositsPK");
            String depositType = formBean.getValue("depositType");
            String oldCompany = formBean.getValue("oldCompany");
            String oldPolicyNumber = formBean.getValue("oldPolicyNumber");
            String exchangePolicyEffectiveDate = DateTimeUtil.formatMMDDYYYYToYYYYMMDD(formBean.getValue("exchangePolicyEffectiveDate"));
            String exchangeIssueAge = formBean.getValue("exchangeIssueAge");
            String exchangeDuration = formBean.getValue("exchangeDuration");
            String anticipatedAmount = formBean.getValue("anticipatedAmount");
            String amountReceived = formBean.getValue("amountReceived");
            String taxYear = formBean.getValue("taxYear");
            String costBasis = formBean.getValue("costBasis");
            String dateReceivedDate = DateTimeUtil.formatMMDDYYYYToYYYYMMDD(formBean.getValue("dateReceivedDate"));

            DepositsVO currentDepositVO = new DepositsVO();

            if (Util.isANumber(selectedDepositsPK))
            {
                currentDepositVO.setDepositsPK(Long.parseLong(selectedDepositsPK));
            }
            else
            {
                currentDepositVO.setDepositsPK(0);
            }

            if (Util.isANumber(depositType))
            {
                depositType = codeTableWrapper.getCodeTableEntry(Long.parseLong(depositType)).getCode();
            }
            else
            {
                depositType = "";
            }

            currentDepositVO.setDepositTypeCT(depositType);
            currentDepositVO.setOldCompany(oldCompany);
            currentDepositVO.setOldPolicyNumber(oldPolicyNumber);

            currentDepositVO.setExchangePolicyEffectiveDate(exchangePolicyEffectiveDate);

            if (Util.isANumber(exchangeIssueAge))
            {
                currentDepositVO.setExchangeIssueAge(Integer.parseInt(exchangeIssueAge));
            }

            if (Util.isANumber(exchangeDuration))
            {
                currentDepositVO.setExchangeDuration(Integer.parseInt(exchangeDuration));
            }

            if (Util.isANumber(costBasis))
            {
                currentDepositVO.setCostBasis(new EDITBigDecimal(costBasis).getBigDecimal());
            }
            else
            {
                currentDepositVO.setCostBasis(new EDITBigDecimal().getBigDecimal());
            }

            if (Util.isANumber(anticipatedAmount))
            {
                currentDepositVO.setAnticipatedAmount(new EDITBigDecimal(anticipatedAmount).getBigDecimal());
            }
            else
            {
                currentDepositVO.setAnticipatedAmount(new EDITBigDecimal().getBigDecimal());
            }

            if (Util.isANumber(amountReceived))
            {
                currentDepositVO.setAmountReceived(new EDITBigDecimal(amountReceived).getBigDecimal());
            }
            else
            {
                currentDepositVO.setAmountReceived(new EDITBigDecimal().getBigDecimal());
            }

            currentDepositVO.setDateReceived(dateReceivedDate);

            if (taxYear.equals("") && dateReceivedDate != null)
            {
                taxYear = new EDITDate(dateReceivedDate).getFormattedYear();
            }

            currentDepositVO.setTaxYear(Integer.parseInt(taxYear));

            appReqBlock.getHttpSession().setAttribute("currentDepositVO", currentDepositVO);
        }
        else
        {
            savePreviousPageFormBean(appReqBlock, previousPage);
        }

        getSuspenseVOs(appReqBlock);
        appReqBlock.getHttpSession().setAttribute("lastPage", lastPage);

        return CONTRACT_SUSPENSE_CREATION;
    }

    protected String showSelectedCashBatch(AppReqBlock appReqBlock) throws Exception
    {
        UserSession userSession = appReqBlock.getUserSession();

        if (userSession.getSuspenseIsLocked())
        {
            userSession.unlockSuspense();
        }
        else if (userSession.getCashBatchContractIsLocked())
        {
            userSession.unlockCashBatchContract();
        }

        String selectedSuspensePK = appReqBlock.getReqParm("selectedSuspensePK");
        appReqBlock.getHttpServletRequest().setAttribute("selectedSuspensePK", selectedSuspensePK);

        event.business.Event eventComponent = new event.component.EventComponent();

        SuspenseVO selectedSuspenseVO = eventComponent.composeSuspenseVO(Long.parseLong(selectedSuspensePK), new ArrayList());

        if (selectedSuspenseVO.getSuspenseType().equalsIgnoreCase("Contract"))
        {
            try
            {
                userSession.lockSuspense(Long.parseLong(selectedSuspensePK));
            }
            catch(EDITLockException l)
            {
                appReqBlock.getHttpServletRequest().setAttribute("suspenseMessage", l.getMessage());
            }
        }

        getSuspenseVOs(appReqBlock);

        return CONTRACT_SUSPENSE_CREATION;
    }

    protected String showSelectedCashBatchContract(AppReqBlock appReqBlock) throws Exception
    {
        String selectedSuspensePK = appReqBlock.getReqParm("selectedSuspensePK");
        appReqBlock.getHttpServletRequest().setAttribute("selectedSuspensePK", selectedSuspensePK);
        String selectedCashBatchContractPK = appReqBlock.getReqParm("selectedCashBatchContractPK");
        appReqBlock.getHttpServletRequest().setAttribute("selectedCashBatchContractPK", selectedCashBatchContractPK);

        try
        {
            UserSession userSession = appReqBlock.getUserSession();
            if (userSession.getSuspenseIsLocked())
            {
                userSession.unlockSuspense();
            }
            else if (userSession.getCashBatchContractIsLocked())
            {
                userSession.unlockCashBatchContract();
            }

            userSession.lockCashBatchContract(Long.parseLong(selectedCashBatchContractPK));
        }
        catch(EDITLockException l)
        {
            appReqBlock.getHttpServletRequest().setAttribute("suspenseMessage", l.getMessage());
        }

        getSuspenseVOs(appReqBlock);

        return CONTRACT_SUSPENSE_CREATION;
    }

    protected String closeContractSuspenseCreation(AppReqBlock appReqBlock) throws Exception
    {
        String lastPage = (String) appReqBlock.getHttpSession().getAttribute("lastPage");
        appReqBlock.getHttpSession().removeAttribute("lastPage");

        UserSession userSession = appReqBlock.getUserSession();

        if (userSession.getCashBatchContractIsLocked())
        {
            userSession.unlockCashBatchContract();
        }
        else if (userSession.getSuspenseIsLocked())
        {
            userSession.unlockSuspense();
        }

        return lastPage;
    }

    protected String showNewBusinessSuspense(AppReqBlock appReqBlock) throws Exception
    {
        SessionBean stateBean = appReqBlock.getSessionBean("quoteStateBean");

        String previousPage = stateBean.getValue("currentPage");

        savePreviousPageFormBean(appReqBlock, previousPage);

        return NEW_BUSINESS_SUSPENSE;
    }

    protected String showSelectedNewBusinessSuspense(AppReqBlock appReqBlock) throws Exception
    {
        String selectedContractSuspensePK = appReqBlock.getReqParm("selectedContractSuspensePK");
        appReqBlock.getHttpServletRequest().setAttribute("selectedContractSuspensePK", selectedContractSuspensePK);

        return NEW_BUSINESS_SUSPENSE;
    }

    protected String showOriginalSuspenseInfo(AppReqBlock appReqBlock) throws Exception
    {
        PageBean formBean = appReqBlock.getFormBean();
        String originalContractNumber = formBean.getValue("originalContractNumber");
        String originalAmount = formBean.getValue("originalAmount");
        String originalMemoCode = formBean.getValue("originalMemoCode");

        appReqBlock.getHttpServletRequest().setAttribute("originalContractNumber", originalContractNumber);
        appReqBlock.getHttpServletRequest().setAttribute("originalAmount", originalAmount);
        appReqBlock.getHttpServletRequest().setAttribute("originalMemoCode", originalMemoCode);

        return ORIGINAL_SUSPENSE_INFO;
    }

    private void getSuspenseVOs(AppReqBlock appReqBlock) throws Exception
    {
        event.business.Event eventComponent = new event.component.EventComponent();

        List voInclusionList = new ArrayList();
        voInclusionList.add(CashBatchContractVO.class);

        SuspenseVO[] suspenseVOs = eventComponent.composeSuspenseVOByDirection("Apply", voInclusionList);
        appReqBlock.getHttpServletRequest().setAttribute("suspenseVOs", suspenseVOs);
    }

    private void populateRequestWithCompanyRequirementRelationsSummaryVOs(AppReqBlock appReqBlock) throws Exception
    {
        engine.business.Lookup engineLookup = new engine.component.LookupComponent();
        contract.business.Lookup contractLookup = new contract.component.LookupComponent();

        ProductStructureVO[] productStructureVOs = engineLookup.findByTypeCode("Product",false, null);

        //get [three - nope][make that four!] system types of ProductStructures for relationships
        List casetrackingStructures = new ArrayList();
        ProductStructureVO claimCS = engineLookup.getAllProductStructuresByCoName("Claims")[0];
        casetrackingStructures.add(claimCS);

        ProductStructureVO annuitizationCS = engineLookup.getAllProductStructuresByCoName("Annuitization")[0];
        casetrackingStructures.add(annuitizationCS);

        ProductStructureVO agentCS = engineLookup.getAllProductStructuresByCoName("Agent")[0];
        casetrackingStructures.add(agentCS);
        
        ProductStructureVO caseCS = engineLookup.getAllProductStructuresByCoName("Case")[0];
        casetrackingStructures.add(caseCS);        

        Object[] array = Util.joinArrays(productStructureVOs, (ProductStructureVO[])casetrackingStructures.toArray(new ProductStructureVO[casetrackingStructures.size()]), ProductStructureVO.class);
        productStructureVOs = (ProductStructureVO[])array;

        RequirementVO[] requirementVOs = contractLookup.composeRequirementVOs(new ArrayList());

        String activeCompanyStructurePK = appReqBlock.getReqParm("activeCompanyStructurePK");

        if (activeCompanyStructurePK != null && activeCompanyStructurePK.length() > 0 && !activeCompanyStructurePK.equals("0"))
        {
            RequirementVO[] attachedRequirementVOs =
                    contractLookup.getAllRequirementVOsByProductStructure(Long.parseLong(activeCompanyStructurePK), false, null);

            if (attachedRequirementVOs != null)
            {
                appReqBlock.getHttpServletRequest().setAttribute("attachedRequirementVOs", attachedRequirementVOs);
            }
        }

        if (productStructureVOs != null)
        {
            appReqBlock.getHttpServletRequest().setAttribute("companyStructureVOs", productStructureVOs);
        }

        if (requirementVOs != null)
        {
            appReqBlock.getHttpServletRequest().setAttribute("requirementVOs", requirementVOs);
        }
    }

    private String buildRolesAndContractClients(AppReqBlock appReqBlock) throws Exception
    {
        PageBean quoteMain = appReqBlock.getSessionBean("quoteMainSessionBean").getPageBean("formBean");

        String effectiveDate = DateTimeUtil.formatMMDDYYYYToYYYYMMDD(quoteMain.getValue("effectiveDate"));

        CodeTableWrapper codeTableWrapper = CodeTableWrapper.getSingleton();

//        role.business.Lookup roleLookup = new role.component.LookupComponent();
        role.business.Role roleComponent = new role.component.RoleComponent();
        client.business.Lookup clientLookup = new client.component.LookupComponent();

        PageBean formBean = appReqBlock.getFormBean();
        String selectedRoles = formBean.getValue("selectedRoles");
        String[] selectedRolesST = Util.fastTokenizer(selectedRoles, ",");
        String selectedClientDetailPK = formBean.getValue("selectedClientDetailPK");
        long clientDetailPK = Long.parseLong(selectedClientDetailPK);
        String optionId = Util.initString(formBean.getValue("selectedOptionId"), "Traditional");
        String riderNumber = Util.initString(formBean.getValue("selectedRiderNumber"), "0");

        effectiveDate = DateTimeUtil.formatMMDDYYYYToYYYYMMDD(formBean.getValue("effectiveDate"));

        List voExclusionVector = new ArrayList();
        voExclusionVector.add(ClientAddressVO.class);
        voExclusionVector.add(ClientRoleVO.class);

        String contractNumber = appReqBlock.getSessionBean("quoteMainSessionBean").getPageBean("formBean").getValue("contractNumber");

        for (int r = 0; r < selectedRolesST.length; r++)
        {
            PageBean contractClientPageBean = new PageBean();

            if (Util.isANumber(selectedRolesST[r]))
            {
                selectedRolesST[r] = codeTableWrapper.getCodeTableEntry(Long.parseLong(selectedRolesST[r])).getCode();

                long clientRolePK = 0;
                long preferenceFK = 0;

                ClientDetailVO[] clientDetailVOs = clientLookup.findClientDetailByClientPK(clientDetailPK, true, voExclusionVector);

                PreferenceVO[] preferences = clientDetailVOs[0].getPreferenceVO();

                String taxId = clientDetailVOs[0].getTaxIdentification();

                if (clientDetailVOs != null)
                {
                    int i = 0;

                    if (preferences != null && preferences.length > 0)
                    {
                        for (i = 0; i < preferences.length; i++)
                        {
                            if (preferences[i].getOverrideStatus().equalsIgnoreCase("P"))
                            {
                                preferenceFK = preferences[i].getPreferencePK();
                                contractClientPageBean.putValue("disbursementSource", preferences[i].getDisbursementSourceCT());
                                contractClientPageBean.putValue("printAs", preferences[i].getPrintAs());
                                contractClientPageBean.putValue("printAs2", preferences[i].getPrintAs2());
                                break;
                            }
                        }
                    }
                }

                //  Always create a new ClientRole for a new ContractClient
                ClientRoleVO clientRoleVO = new ClientRoleVO();

                clientRoleVO.setClientRolePK(0);
                clientRoleVO.setClientDetailFK(Long.parseLong(selectedClientDetailPK));
                clientRoleVO.setPreferenceFK(preferenceFK);
                clientRoleVO.setRoleTypeCT(selectedRolesST[r]);
                clientRoleVO.setOverrideStatus("P");
                clientRoleVO.setReferenceID(contractNumber);

                clientRolePK = roleComponent.saveOrUpdateClientRole(clientRoleVO);

                contractClientPageBean.putValue("contractClientPK", "0");
                contractClientPageBean.putValue("clientRoleFK", clientRolePK + "");
                contractClientPageBean.putValue("relationshipInd", selectedRolesST[r]);
                contractClientPageBean.putValue("optionId", optionId);
                contractClientPageBean.putValue("taxId", taxId);
                contractClientPageBean.putValue("effectiveDate", DateTimeUtil.formatYYYYMMDDToMMDDYYYY(effectiveDate));
                contractClientPageBean.putValue("terminationDate", DateTimeUtil.formatYYYYMMDDToMMDDYYYY(EDITDate.DEFAULT_MAX_DATE));
                contractClientPageBean.putValue("overrideStatus", "");
                contractClientPageBean.putValue("riderNumber", riderNumber);

                EDITDate dateOfBirth = null;
                if (clientDetailVOs[0].getBirthDate() != null)
                {
                    dateOfBirth = new EDITDate(clientDetailVOs[0].getBirthDate());

                    contractClientPageBean.putValue("dob", DateTimeUtil.formatYYYYMMDDToMMDDYYYY(dateOfBirth.getFormattedDate()));
                }

                contractClientPageBean.putValue("firstName", clientDetailVOs[0].getFirstName());
                contractClientPageBean.putValue("middleName", clientDetailVOs[0].getMiddleName());
                contractClientPageBean.putValue("lastName", clientDetailVOs[0].getLastName());
                contractClientPageBean.putValue("corporateName", clientDetailVOs[0].getCorporateName());
                contractClientPageBean.putValue("genderId", clientDetailVOs[0].getGenderCT());

                String defaultSpaces = "";
                contractClientPageBean.putValue("newIssuesEligibilityStatus", defaultSpaces);
                contractClientPageBean.putValue("newIssuesStartDate", defaultSpaces);

                String usCitizenshipIndStatus = "unchecked";
                TaxInformationVO[] taxInformationVO = clientDetailVOs[0].getTaxInformationVO();

                if (taxInformationVO != null && taxInformationVO.length > 0)
                {
                    String citizenShipIndStatus  = taxInformationVO[0].getCitizenshipIndCT();
                    if (citizenShipIndStatus != null &&
                         citizenShipIndStatus.equalsIgnoreCase("Y")){
                        usCitizenshipIndStatus = "checked";
                    }
                }

                contractClientPageBean.putValue("usCitizenIndStatus", usCitizenshipIndStatus);
                contractClientPageBean.putValue("contractClientAllocationPK", "0");
                contractClientPageBean.putValue("allocationPercent", "0");
                contractClientPageBean.putValue("splitEqualInd", "N");
                contractClientPageBean.putValue("issueAge", "0");

                appReqBlock.getSessionBean("quoteClients").putPageBean(taxId + riderNumber + optionId + selectedRolesST[r] + "0" + clientRolePK,
                        contractClientPageBean);
            }
        }

        return QUOTE_COMMIT_NON_PAYEE;
    }

    private String showDepositDialog(AppReqBlock appReqBlock) throws Exception
    {
        SessionBean stateBean = appReqBlock.getSessionBean("quoteStateBean");
        String currentPage = stateBean.getValue("currentPage");

        savePreviousPageFormBean(appReqBlock, currentPage);

        SessionBean quoteMainSessionBean = appReqBlock.getSessionBean("quoteMainSessionBean");

        String contractNumber = quoteMainSessionBean.getPageBean("formBean").getValue("contractNumber");
        if (contractNumber.equals(""))
        {
            contractNumber = quoteMainSessionBean.getValue("contractId");
        }

        new NewBusDepositSuspenseTableModel(contractNumber, appReqBlock);

        return DEPOSIT_DIALOG;
    }

    private String showDepositDetailSummary(AppReqBlock appReqBlock) throws Exception
    {
        String selectedDepositsPK = appReqBlock.getFormBean().getValue("selectedDepositsPK");
        appReqBlock.getHttpServletRequest().setAttribute("selectedDepositsPK", selectedDepositsPK);

        SessionBean quoteMainSessionBean = appReqBlock.getSessionBean("quoteMainSessionBean");

        String contractNumber = quoteMainSessionBean.getPageBean("formBean").getValue("contractNumber");
        if (contractNumber.equals(""))
        {
            contractNumber = quoteMainSessionBean.getValue("contractId");
        }

        new NewBusDepositSuspenseTableModel(contractNumber, appReqBlock);

        return DEPOSIT_DIALOG;
    }

    private String saveDepositToSummary(AppReqBlock appReqBlock) throws Exception
    {
        CodeTableWrapper codeTableWrapper = CodeTableWrapper.getSingleton();

        PageBean formBean = appReqBlock.getFormBean();
        String selectedDepositsPK = formBean.getValue("selectedDepositsPK");
        String cashBatchContractFK = formBean.getValue("cashBatchContractFK");
        String suspenseFK = formBean.getValue("suspenseFK");
        String taxYear = formBean.getValue("taxYear");

        DepositsVO[] depositsVOs = (DepositsVO[]) appReqBlock.getHttpSession().getAttribute("depositsVOs");
        DepositsVO depositsVO = null;
        boolean depositExisted = false;

        if (depositsVOs != null)
        {
            for (int e = 0; e < depositsVOs.length; e++)
            {
                if ((depositsVOs[e].getDepositsPK() + "").equals(selectedDepositsPK))
                {
                    depositsVO = depositsVOs[e];
                    depositExisted = true;
                    break;
                }
            }
        }

        if (!depositExisted)
        {
            depositsVO = new DepositsVO();
            contract.business.Contract contractComponent = new contract.component.ContractComponent();
            depositsVO.setDepositsPK(contractComponent.getNextAvailableKey() * -1);
            depositsVO.setSegmentFK(0);
        }

        String depositType = formBean.getValue("depositType");

        if (Util.isANumber(depositType))
        {
            depositType = codeTableWrapper.getCodeTableEntry(Long.parseLong(depositType)).getCode();
        }
        else
        {
            depositType = "";
        }
        depositsVO.setDepositTypeCT(depositType);
        depositsVO.setOldCompany(formBean.getValue("oldCompany"));
        depositsVO.setOldPolicyNumber(formBean.getValue("oldPolicyNumber"));
        String anticipatedAmount = formBean.getValue("anticipatedAmount");

        if (Util.isANumber(anticipatedAmount))
        {
             depositsVO.setAnticipatedAmount(new EDITBigDecimal(anticipatedAmount).getBigDecimal());
        }
        else
        {
            depositsVO.setAnticipatedAmount(new EDITBigDecimal().getBigDecimal());
        }

        String amountReceived = formBean.getValue("amountReceived");

        if (Util.isANumber(amountReceived))
        {
            depositsVO.setAmountReceived(new EDITBigDecimal(amountReceived).getBigDecimal());
        }
        else
        {
            depositsVO.setAmountReceived(new EDITBigDecimal().getBigDecimal());
        }

        String costBasis = formBean.getValue("costBasis");

        if (Util.isANumber(costBasis))
        {
            depositsVO.setCostBasis(new EDITBigDecimal(costBasis).getBigDecimal());
        }
        else
        {
            depositsVO.setCostBasis(new EDITBigDecimal().getBigDecimal());
        }

        String dateReceivedDate = DateTimeUtil.formatMMDDYYYYToYYYYMMDD(formBean.getValue("dateReceivedDate"));

        depositsVO.setDateReceived(dateReceivedDate);

        if (Util.isANumber(suspenseFK))
        {
            depositsVO.setSuspenseFK(Long.parseLong(suspenseFK));
        }
        else
        {
            depositsVO.setSuspenseFK(0);
        }

        if (taxYear.equals("") && dateReceivedDate != null)
        {
            taxYear = new EDITDate(dateReceivedDate).getFormattedYear();
        }
        else if (taxYear.equals("") && dateReceivedDate == null)
        {
            EDITDate currentDate = new EDITDate();
            taxYear = currentDate.getFormattedYear();
        }

        depositsVO.setTaxYear(Integer.parseInt(taxYear));
        depositsVO.setCashBatchContractFK(Long.parseLong(cashBatchContractFK));

        String exchangePolicyEffectiveDate = formBean.getValue("exchangePolicyEffectiveDate");
        exchangePolicyEffectiveDate = DateTimeUtil.formatMMDDYYYYToYYYYMMDD(exchangePolicyEffectiveDate);

        depositsVO.setExchangePolicyEffectiveDate(exchangePolicyEffectiveDate);
        depositsVO.setExchangeIssueAge( Integer.parseInt( Util.initString( formBean.getValue("exchangeIssueAge"), "0") ) );
        depositsVO.setExchangeDuration( Integer.parseInt( Util.initString( formBean.getValue("exchangeDuration"), "0") ) );

        String preTEFRAGain = Util.initString(formBean.getValue("preTEFRAGain"), "0");
        String preTEFRAAmount = Util.initString(formBean.getValue("preTEFRAAmount"), "0");
        String postTEFRAGain = Util.initString(formBean.getValue("postTEFRAGain"), "0");
        String postTEFRAAmount = Util.initString(formBean.getValue("postTEFRAAmount"), "0");

        depositsVO.setPreTEFRAGain(new EDITBigDecimal(preTEFRAGain).getBigDecimal());
        depositsVO.setPreTEFRAAmount(new EDITBigDecimal(preTEFRAAmount).getBigDecimal());
        depositsVO.setPostTEFRAGain(new EDITBigDecimal(postTEFRAGain).getBigDecimal());
        depositsVO.setPostTEFRAAmount(new EDITBigDecimal(postTEFRAAmount).getBigDecimal());
        depositsVO.setExchangeLoanAmount(new EDITBigDecimal(Util.initString(formBean.getValue("exchangeLoanAmount"), "0")).getBigDecimal());

        if (!depositExisted)
        {
            List depositVector = new ArrayList();
            if (depositsVOs != null)
            {
                for (int j = 0; j < depositsVOs.length; j++)
                {
                    depositVector.add(depositsVOs[j]);
                }
            }

            depositVector.add(depositsVO);

            appReqBlock.getHttpSession().
                    setAttribute("depositsVOs",
                                  (DepositsVO[]) depositVector.toArray(new DepositsVO[depositVector.size()]));

            UserSession userSession = appReqBlock.getUserSession();

            userSession.setDepositsVO((DepositsVO[]) depositVector.toArray(new DepositsVO[depositVector.size()]));
        }

        appReqBlock.getHttpSession().removeAttribute("currentDepositVO");

        SessionBean quoteMainSessionBean = appReqBlock.getSessionBean("quoteMainSessionBean");

        String contractNumber = quoteMainSessionBean.getPageBean("formBean").getValue("contractNumber");
        if (contractNumber.equals(""))
        {
            contractNumber = quoteMainSessionBean.getValue("contractId");
        }

        new NewBusDepositSuspenseTableModel(contractNumber, appReqBlock);

        return DEPOSIT_DIALOG;
    }

    private String clearDepositFormForAddOrCancel(AppReqBlock appReqBlock) throws Exception
    {
        appReqBlock.getHttpSession().removeAttribute("currentDepositVO");

        SessionBean quoteMainSessionBean = appReqBlock.getSessionBean("quoteMainSessionBean");

        String contractNumber = quoteMainSessionBean.getPageBean("formBean").getValue("contractNumber");
        if (contractNumber.equals(""))
        {
            contractNumber = quoteMainSessionBean.getValue("contractId");
        }

        new NewBusDepositSuspenseTableModel(contractNumber, appReqBlock);

        return DEPOSIT_DIALOG;
    }

    private String showDepositDeletionMessage(AppReqBlock appReqBlock) throws Exception
    {
        PageBean formBean = appReqBlock.getFormBean();
        String selectedDepositsPK = formBean.getValue("selectedDepositsPK");
        String suspenseFK = formBean.getValue("suspenseFK");
        appReqBlock.getHttpServletRequest().setAttribute("selectedDepositsPK", selectedDepositsPK);
        appReqBlock.getHttpServletRequest().setAttribute("suspenseFK", suspenseFK);

        return DEPOSIT_DELETION_MESSAGE_DIALOG;
    }

    private String deleteSelectedDeposit(AppReqBlock appReqBlock) throws Exception
    {
        PageBean formBean = appReqBlock.getFormBean();
        String selectedDepositsPK = formBean.getValue("selectedDepositsPK");

        SessionBean quoteMainSessionBean = appReqBlock.getSessionBean("quoteMainSessionBean");
        String segmentStatus = quoteMainSessionBean.getPageBean("formBean").getValue("statusCode");

        if (segmentStatus.equalsIgnoreCase("Pending") || segmentStatus.equalsIgnoreCase("Quote") || segmentStatus.equalsIgnoreCase(""))
        {
            DepositsVO[] depositsVOs = (DepositsVO[]) appReqBlock.getHttpSession().getAttribute("depositsVOs");
            List updatedDeposits = new ArrayList();

            for (int i = 0; i < depositsVOs.length; i++)
            {
                if (!(depositsVOs[i].getDepositsPK() + "").equals(selectedDepositsPK))
                {
                    updatedDeposits.add(depositsVOs[i]);
                }
                else
                {
                    event.business.Event eventComponent = new event.component.EventComponent();

                    if (depositsVOs[i].getSuspenseFK() != 0)
                    {
                        SuspenseVO suspenseVO = eventComponent.composeSuspenseVO(depositsVOs[i].getSuspenseFK(), new ArrayList());

                        if (suspenseVO != null)
                        {
                            EDITBigDecimal pendingAmt = Util.roundToNearestCent(suspenseVO.getPendingSuspenseAmount());
                            //pendingAmt -= Util.roundToNearestCent(depositsVOs[i].getAmountReceived());
                            pendingAmt = pendingAmt.subtractEditBigDecimal(Util.roundToNearestCent(depositsVOs[i].getAmountReceived()));
                            suspenseVO.setPendingSuspenseAmount(Util.roundToNearestCent(pendingAmt).getBigDecimal());
                            eventComponent.saveSuspenseNonRecursively(suspenseVO);
                        }
                    }

                    new ContractComponent().deleteVO(DepositsVO.class, depositsVOs[i].getDepositsPK(), false);
                }
            }

            if (updatedDeposits.size() > 0)
            {
                appReqBlock.getHttpSession().setAttribute("depositsVOs", (DepositsVO[]) updatedDeposits.toArray(new DepositsVO[updatedDeposits.size()]));
            }
            else
            {
                appReqBlock.getHttpSession().removeAttribute("depositsVOs");
            }

            appReqBlock.getHttpSession().setAttribute("selectedDepositsPK", "");
        }
        else
        {
            appReqBlock.getHttpServletRequest().setAttribute("depositMessage", "Contract Status Invalid for Deposit Delete");
        }

        String contractNumber = quoteMainSessionBean.getPageBean("formBean").getValue("contractNumber");
        if (contractNumber.equals(""))
        {
            contractNumber = quoteMainSessionBean.getValue("contractId");
        }

        new NewBusDepositSuspenseTableModel(contractNumber, appReqBlock);

        return DEPOSIT_DIALOG;
    }

    protected String showVOEditExceptionDialog(AppReqBlock appReqBlock) throws Exception
    {
        VOEditException voEditException = (VOEditException) appReqBlock.getHttpSession().getAttribute("VOEditException");

        // Remove voEditException from Session (to clear it), and move it to request scope.
        appReqBlock.getHttpSession().removeAttribute("VOEditException");

        appReqBlock.getHttpServletRequest().setAttribute("VOEditException", voEditException);

        return VO_EDIT_EXCEPTION_DIALOG;
    }

    protected String closeOnlineReport(AppReqBlock appReqBlock) throws Exception
    {
        SessionBean stateBean = appReqBlock.getSessionBean("quoteStateBean");

        String previousPage = stateBean.getValue("currentPage");

        stateBean.putValue("previousPage", previousPage);
        stateBean.putValue("currentPage", ISSUE_CONTRACT_DIALOG);

        getSuspenseInformation(appReqBlock);

        return ISSUE_CONTRACT_DIALOG;
    }

    protected String importNewBusiness(AppReqBlock appReqBlock) throws Exception
    {
        ImportNewBusinessResponse[] importResponses = null;

        MultipartParser mp = new MultipartParser(appReqBlock.getHttpServletRequest(), 1024 * 10000);

        Part part = null;

        HSSFWorkbook workbook = null;
        String productStructureId = null;

        //  Process each file in selection box
        while ((part = mp.readNextPart()) != null)
        {
            if (part instanceof FilePart)
            {
                // Get the import file as an InputStream
                // ** Can't use filenames because file could be on windows and server on Sun - paths are not compatible
                FilePart fp = (FilePart) part;

                InputStream fpis = fp.getInputStream();

                //  Read the file in as an Excel Workbook
                workbook = ExcelUtil.readWorkbook(fpis);
            }
            else if (part instanceof ParamPart)
            {
                ParamPart pp = (ParamPart) part;
                if (pp.getName().equalsIgnoreCase("companyStructureId"))
                {
                    productStructureId  = pp.getStringValue();
                }
            }
        }

        //  Process workbook and save as new business
        Contract contractComponent = new ContractComponent();

        String operator = ((UserSession) appReqBlock.getHttpSession().getAttribute("userSession")).getUsername();

        importResponses = contractComponent.importNewBusiness(workbook, productStructureId, operator);

        //  Display dialog with resultMessages and stay on import page
        appReqBlock.getHttpServletRequest().setAttribute("importResponses", importResponses);
        appReqBlock.getHttpSession().setAttribute("workbook", workbook);
        appReqBlock.getHttpServletRequest().setAttribute("companyStructureId", productStructureId);

        return IMPORT_NEW_BUSINESS_RESPONSE_DIALOG;
    }

    private String completeNewBusinessImport(AppReqBlock appReqBlock) throws Exception
    {
        String importValues = appReqBlock.getReqParm("importValues");
        String productStructureId = appReqBlock.getReqParm("companyStructureId");
        HSSFWorkbook workbook = (HSSFWorkbook) appReqBlock.getHttpSession().getAttribute("workbook");

        ImportNewBusinessResponse[] importResponses = null;

        //  Process workbook for the specified importValues (error overrides) and save as new business
        Contract contractComponent = new ContractComponent();

        String operator = ((UserSession) appReqBlock.getHttpSession().getAttribute("userSession")).getUsername();

        importResponses = contractComponent.importNewBusiness(workbook, importValues, productStructureId, operator);

        //  Display dialog with resultMessages and stay on import page
        appReqBlock.getHttpServletRequest().setAttribute("importResponses", importResponses);

        return IMPORT_NEW_BUSINESS_RESPONSE_DIALOG;
    }

    private ContractRequirementVO[] getRequirements(AppReqBlock appReqBlock) throws Exception
    {
        PageBean quoteMainFormBean = appReqBlock.getSessionBean("quoteMainSessionBean").getPageBean("formBean");
        PageBean formBean = appReqBlock.getFormBean();

        String segmentPK = quoteMainFormBean.getValue("segmentPK");

        if (segmentPK.equals(""))
        {
            segmentPK = formBean.getValue("segmentPK");
        }

        String productStructurePK = quoteMainFormBean.getValue("companyStructureId");

        if (productStructurePK.equals(""))
        {
            productStructurePK = formBean.getValue("companyStructureId");
        }

        ContractRequirementVO[] contractRequirementVO = null;

        if (!productStructurePK.equals("") && (!productStructurePK.equalsIgnoreCase("Please Select")))
        {
            contract.business.Lookup contractLookup = new contract.component.LookupComponent();

            contractRequirementVO = (ContractRequirementVO[]) appReqBlock.getHttpSession().getAttribute("contractRequirementVO");

            if (contractRequirementVO == null || contractRequirementVO.length == 0)
            {
                    contractRequirementVO = contractLookup.buildContractRequirements(Long.parseLong(productStructurePK));
            }
            else
            {
                FilteredRequirementVO filteredRequirementVO = (FilteredRequirementVO) contractRequirementVO[0].getParentVO(FilteredRequirementVO.class);

                if (filteredRequirementVO == null)
                {
                    List voInclusionList = new ArrayList();

                    voInclusionList.add(FilteredRequirementVO.class);
                    voInclusionList.add(RequirementVO.class);

                    for (int i = 0; i < contractRequirementVO.length; i++)
                    {
                        contractRequirementVO[i] = contractLookup.composeContractRequirementVO(contractRequirementVO[i], voInclusionList);
                    }
                }
            }
        }

        return contractRequirementVO;
    }

    public String getFundNames(AppReqBlock appReqBlock, SegmentVO segmentVO, long modifiedTableKey) throws Exception
    {
        engine.business.Lookup engineLookup = new engine.component.LookupComponent();
        FundVO[] fundNameVOs = null;

        if (fundNameVOs == null)
        {
            fundNameVOs =  engineLookup.findAllFundVOs(false, null);
        }

        InvestmentVO[] investmentVOs = segmentVO.getInvestmentVO();
        String fundName = null;
        long filteredFundFK = 0;

        for (int i = 0; i < investmentVOs.length; i++)
        {
            if (modifiedTableKey == investmentVOs[i].getInvestmentPK())
            {
                filteredFundFK = investmentVOs[i].getFilteredFundFK();

                FilteredFundVO[] filteredFundVO = engineLookup.findFilteredFundVOsByPK(filteredFundFK, false, null);
                long fundFK = filteredFundVO[0].getFundFK();

                for (int j = 0; j < fundNameVOs.length; j++)
                {
                    if (fundFK == fundNameVOs[j].getFundPK())
                    {
                        fundName = fundNameVOs[j].getName();
                        break;
                    }
                }
                break;
            }
        }

        return fundName;
    }

    public ChangeHistoryVO setChangeValues(ChangeHistoryVO changeHistoryVO, String fundName) throws Exception
    {
        String beforeValue = changeHistoryVO.getBeforeValue();
        String afterValue  = changeHistoryVO.getAfterValue();

        if (!beforeValue.equals(""))
        {
           changeHistoryVO.setBeforeValue(fundName);
        }

        if (!afterValue.equals(""))
        {
           changeHistoryVO.setAfterValue(fundName);
        }

        return changeHistoryVO;
    }

    public String findClient(String tableName, long modifiedTableKey, SegmentVO segmentVO, AppReqBlock appReqBlock) throws Exception
    {
        String clientRoleFK = "";
        String taxId = "";
        ContractClientVO[] contractClientVO = segmentVO.getContractClientVO();

        if (tableName.equals("ContractClient"))
        {
            for (int i = 0; i < contractClientVO.length; i++)
            {
                if (modifiedTableKey == contractClientVO[i].getContractClientPK())
                {
                    clientRoleFK = contractClientVO[i].getClientRoleFK() + "";
                    break;
                }
            }
        }

        if (tableName.equals("ContractClientAllocation"))
        {
            for (int i = 0; i < contractClientVO.length; i++)
            {
                ContractClientAllocationVO[] contractClientAllocVO = contractClientVO[i].getContractClientAllocationVO();

                for (int j = 0; j < contractClientAllocVO.length; j++)
                {
                    if (modifiedTableKey == contractClientAllocVO[j].getContractClientAllocationPK())
                    {
                        clientRoleFK = contractClientVO[i].getClientRoleFK() + "";
                        break;
                    }
                } //end inner for
            }
        }

        ClientDetailVO[] clientDetailVO = null;

        client.business.Lookup clientLookup = new client.component.LookupComponent();

        role.business.Lookup roleLookup =  new role.component.LookupComponent();

        if (!clientRoleFK.equals(""))
        {
            ClientRoleVO[] clientRoleVO = roleLookup.getRoleByClientRolePK(Long.parseLong(clientRoleFK));

            clientDetailVO = clientLookup.findClientDetailByClientPK(clientRoleVO[0].getClientDetailFK(),
                                                                      false, null);
            if (clientDetailVO != null)
            {
                taxId = clientDetailVO[0].getTaxIdentification();
            }
        }

        return taxId;
    }

    public ChangeHistoryVO getClientIds(ChangeHistoryVO changeHistoryVO, AppReqBlock appReqBlock) throws Exception
    {
        String beforeValue = changeHistoryVO.getBeforeValue();
        String afterValue  = changeHistoryVO.getAfterValue();

        ClientDetailVO[] clientDetailVO = null;

        client.business.Lookup clientLookup =  new client.component.LookupComponent();
        role.business.Lookup roleLookup = new role.component.LookupComponent();

        List voExclusionList = new ArrayList();
        voExclusionList.add(ClientRoleVO.class);

        if (beforeValue != null && !beforeValue.equals(""))
        {
            ClientRoleVO[] clientRoleVO = roleLookup.getRoleByClientRolePK(Long.parseLong(beforeValue));

            clientDetailVO = clientLookup.findByClientPK(clientRoleVO[0].getClientDetailFK(), true, voExclusionList);

            if (clientDetailVO != null)
            {
                changeHistoryVO.setBeforeValue(clientDetailVO[0].getTaxIdentification());
            }
        }
        else
        {
            changeHistoryVO.setBeforeValue("");
        }

        if (afterValue != null && !afterValue.equals(""))
        {
            ClientRoleVO[] clientRoleVO = roleLookup.getRoleByClientRolePK(Long.parseLong(afterValue));

            clientDetailVO = clientLookup.findByClientPK(clientRoleVO[0].getClientDetailFK(), true, voExclusionList);

            if (clientDetailVO != null)
            {
                changeHistoryVO.setAfterValue(clientDetailVO[0].getTaxIdentification());
            }
        }
        else
        {
            changeHistoryVO.setAfterValue("");
        }

        return changeHistoryVO;
    }

    private boolean checkClientsAttachedToPolicy(AppReqBlock appReqBlock)
    {
        SessionBean clients = appReqBlock.getSessionBean("quoteClients");
        Map clientPageBeans = clients.getPageBeans();

        return ! clientPageBeans.isEmpty();
    }

    protected String showChangeCoverageDialog(AppReqBlock appReqBlock) throws Exception
    {
        // Check for authorization
        new NewBusinessUseCaseComponent().changeKeyInNewBusiness();

        return CHANGE_COVERAGE_DIALOG;
    }

    /**
     * Change the product structure of a contract and/or its coverage type.
     * For the productStructure selected, generate new requirements.
     * @param appReqBlock
     * @return  appReqBlock
     * @throws Exception
     */
    protected String changeCompanyStructureCoverage(AppReqBlock appReqBlock) throws Exception
    {
        String optionPK = appReqBlock.getFormBean().getValue("optionId");
        String productStructure = appReqBlock.getFormBean().getValue("companyStructure");
        String productStructureId = appReqBlock.getFormBean().getValue("companyStructureId");
        CodeTableWrapper codeTableWrapper = CodeTableWrapper.getSingleton();
        CodeTableVO codeTableVO = codeTableWrapper.getCodeTableEntry(Long.parseLong(optionPK));
        String coverage = codeTableVO.getCode();

        PageBean quoteMainFormBean = appReqBlock.getSessionBean("quoteMainSessionBean").
                getPageBean("formBean");
        String segmentPKAsStr = quoteMainFormBean.getValue("segmentPK");
        long segmentPK = (Util.isANumber(segmentPKAsStr)) ? Long.parseLong(segmentPKAsStr) : 0;

        UserSession userSession = appReqBlock.getUserSession();

        if (!userSession.getSegmentIsLocked() && segmentPK != 0)
        {
            userSession.lockSegment(segmentPK);
        }

        String currentCompanyStructure = quoteMainFormBean.getValue("companyStructureId");
        String currentOption = quoteMainFormBean.getValue("optionId");

        boolean companyStructureChanged = false;

        if (!currentCompanyStructure.equals(productStructureId))
        {
            companyStructureChanged = true;
            quoteMainFormBean.putValue("companyStructure", productStructure);
            quoteMainFormBean.putValue("companyStructureId", productStructureId);
            SessionBean quoteMainSessionBean = appReqBlock.getSessionBean("quoteMainSessionBean");
            quoteMainSessionBean.putValue("companyStructure", productStructure);
        }

        if (!currentOption.equals(coverage))
        {
            quoteMainFormBean.putValue("optionId", coverage);
            SessionBean quoteMainSessionBean = appReqBlock.getSessionBean("quoteMainSessionBean");
            quoteMainSessionBean.putValue("option", coverage);
        }

        if (companyStructureChanged)
        {
            appReqBlock.getHttpSession().removeAttribute("contractRequirementVO");
        }

        
        if (coverage.equalsIgnoreCase("Traditional"))
        {
            return QUOTE_LIFE_MAIN;
        }
        else if (coverage.equalsIgnoreCase("UL"))
        {
            return QUOTE_UNIVERSAL_LIFE_MAIN;
        }
        else if (Segment.OPTIONCODES_AH.contains(coverage.toUpperCase())) 
        {
        	return QUOTE_AH_MAIN;
        }
        else if (coverage.equalsIgnoreCase("DFA") || coverage.equalsIgnoreCase("Waiver"))
        {
            return QUOTE_DEFERRED_ANNUITY_MAIN;
        }
        else if (coverage.equalsIgnoreCase("Life"))
        {
            return QUOTE_LIFE_MAIN;
        }
        else
        {
            return QUOTE_COMMIT_MAIN;
        }
    }

    private String selectSuspenseForAdjustment(AppReqBlock appReqBlock)
    {
        SessionBean quoteMainSessionBean = appReqBlock.getSessionBean("quoteMainSessionBean");

        String contractNumber = quoteMainSessionBean.getPageBean("formBean").getValue("contractNumber");

        String suspensePK = new NewBusDepositSuspenseTableModel(contractNumber, appReqBlock).getSelectedRowId();

        appReqBlock.getHttpServletRequest().setAttribute("selectedSuspensePK", suspensePK);

        return DEPOSIT_DIALOG;
    }

    private String showTaxAdjustmentDialog(AppReqBlock appReqBlock)
    {
        SessionBean quoteMainSessionBean = appReqBlock.getSessionBean("quoteMainSessionBean");

        String contractNumber = quoteMainSessionBean.getPageBean("formBean").getValue("contractNumber");

        String suspensePK = new NewBusDepositSuspenseTableModel(contractNumber, appReqBlock).getSelectedRowId();

        Suspense suspense = Suspense.findByPK(new Long(suspensePK));
        appReqBlock.getHttpServletRequest().setAttribute("suspense", suspense);

        return TAX_ADJUSTMENT_DIALOG;
    }

    private String saveTaxAdjustment(AppReqBlock appReqBlock) throws Exception
    {
        String suspensePK = appReqBlock.getReqParm("suspensePK");
        String costBasis = appReqBlock.getReqParm("costBasis");
        String taxYear = appReqBlock.getReqParm("taxYear");
        String depositType = appReqBlock.getReqParm("depositType");

        Suspense suspense = Suspense.findByPK(new Long(suspensePK));
        if (Util.isANumber(costBasis))
        {
            suspense.setCostBasis(new EDITBigDecimal(costBasis));
        }

        suspense.setTaxYear(Integer.parseInt(taxYear));

        if (Util.isANumber(depositType))
        {
            depositType = CodeTableWrapper.getSingleton().getCodeTableEntry(Long.parseLong(depositType)).getCode();
            suspense.setDepositTypeCT(depositType);
        }

        NewBusinessUseCase newBusinessUseCase = new NewBusinessUseCaseComponent();
        newBusinessUseCase.adjustSuspense(suspense);

        return showDepositDialog(appReqBlock);
    }

    private String showProposalDialog(AppReqBlock appReqBlock) throws Exception
    {
        // Check for authorization
        new NewBusinessUseCaseComponent().performProposal();

        SessionBean stateBean = appReqBlock.getSessionBean("quoteStateBean");
        String currentPage = stateBean.getValue("currentPage");

        savePreviousPageFormBean(appReqBlock, currentPage);

        String returnPage = performPageEditing(appReqBlock);

        if (!returnPage.equals(""))
        {
            return returnPage;
        }
        else
        {
            return PROPOSAL_DIALOG;
        }
    }

    private String showQuestionnaireResponseDialog(AppReqBlock appReqBlock) throws Exception
    {
        // Check for authorization
        new NewBusinessUseCaseComponent().accessQuestionnaireResponse();

        SessionBean stateBean = appReqBlock.getSessionBean("quoteStateBean");
        String currentPage = stateBean.getValue("currentPage");

        savePreviousPageFormBean(appReqBlock, currentPage);

        String returnPage = performPageEditing(appReqBlock);

        new QuestionnaireResponseTableModel(appReqBlock);

        if (!returnPage.equals(""))
        {
            return returnPage;
        }
        else
        {
            return QUESTIONNAIRE_RESPONSE_DIALOG;
        }
    }

    /**
     * Displays the ChangeBatchIDDialog.  Clears the batchContractSetups first.
     * @param appReqBlock
     * @return
     * @throws Exception
     */
    protected String showChangeBatchIDDialog(AppReqBlock appReqBlock) throws Exception
    {
        //  Clear out the contents before displaying
        appReqBlock.getHttpSession().setAttribute("BatchContractSetups", null);

        return CHANGE_BATCHID_DIALOG;
    }

    /**
     * Saves the batch ID change by putting the selected batchContractSetup and contractGroup FKs in cloudland.  They
     * will be set on the Segment in the "big save".
     * Note: This method is called from the quoteCommitTradMain.jsp even though the info is coming from changeBatchIDDialog.jsp.
     * The dialog has the main's form submitted so the info will not be lost on the main page.
     *
     * @param appReqBlock
     *
     * @return
     */
    protected String saveBatchIDChange(AppReqBlock appReqBlock) throws Exception
    {
        FormBean formBean = appReqBlock.getFormBean();

        String contractGroupNumber = formBean.getValue("contractGroupNumber");
        String selectedBatchContractSetupPKString = Util.initString(formBean.getValue("selectedBatchContractSetupPK"), "0");

        //  Get the objects associated with the user's new selections
        Long batchContractSetupPK = new Long(selectedBatchContractSetupPKString);

        ContractGroup contractGroup = ContractGroup.findBy_ContractGroupNumber(contractGroupNumber);

        BatchContractSetup batchContractSetup = BatchContractSetup.findByPK(batchContractSetupPK);

        Long productStructureFK = batchContractSetup.getFilteredProduct().getProductStructureFK();

        ProductStructure productStructure = ProductStructure.findByPK(productStructureFK);

        //  Changes need to be made to Segment but the SegmentVO is not stored in the beans, the segmentPK is.
        //  When saving the contract, the SegmentVO is built using the individual fields stored in the beans.
        //  That means we need to modify the individual fields in the beans or they will be lost on the save.
        SessionBean quoteMainSessionBean = appReqBlock.getSessionBean("quoteMainSessionBean");
        PageBean quoteMainFormBean = quoteMainSessionBean.getPageBean("formBean");

        quoteMainFormBean.putValue("contractGroupFK", String.valueOf(Util.initLong(contractGroup, "contractGroupPK", 0L)));
        quoteMainFormBean.putValue("billScheduleFK", String.valueOf(Util.initLong(contractGroup, "billScheduleFK", 0L)));
        quoteMainFormBean.putValue("batchContractSetupFK", selectedBatchContractSetupPKString);
        quoteMainFormBean.putValue("companyStructureId", String.valueOf(Util.initLong(String.valueOf(productStructureFK), 0L)));

        //  Be sure to update bean values for the quoteInfoHeader.jsp to get updated
        quoteMainSessionBean.putValue("contractGroup", contractGroupNumber);
        quoteMainSessionBean.putValue("companyStructure", productStructure.toString());

        //  Load the new selections into the main page
        appReqBlock.getHttpSession().setAttribute("batchContractSetup", batchContractSetup);
        quoteMainFormBean.putValue("companyStructure", productStructure.toString());

        //  Determine which page to return
        String optionCode = quoteMainFormBean.getValue("optionId");

        String productType = checkProductType(optionCode);

        return getMainReturnPage(productType);
    }

    /**
     * Finds the batchContractSetups for a Group ContractGroup with the input contractGroupNumber.  Sends them
     * back to the changeBatchIDDialog.
     *
     * @param appReqBlock
     * @return
     */
    protected String findBatchContractSetups(AppReqBlock appReqBlock)
    {
       BatchContractSetup[] batchContractSetups = null;

       FormBean formBean = appReqBlock.getFormBean();

       String contractGroupNumber = formBean.getValue("contractGroupNumber");

       ContractGroup groupContractGroup = ContractGroup.findBy_ContractGroupNumber_ContractGroupTypeCT(contractGroupNumber, ContractGroup.CONTRACTGROUPTYPECT_GROUP);

       if (groupContractGroup == null)
       {
           appReqBlock.getHttpServletRequest().setAttribute("responseMessage", "Group Number " + contractGroupNumber + " does not exist.");
       }
       else
       {
           batchContractSetups = BatchContractSetup.findBy_ContractGroupNumber(contractGroupNumber);
       }

       appReqBlock.getHttpSession().setAttribute("BatchContractSetups", batchContractSetups);

       return CHANGE_BATCHID_DIALOG;
    }

//    private String performProposal(AppReqBlock appReqBlock) throws Exception
//    {
//        String proposalMonth = appReqBlock.getReqParm("proposalMonth");
//        String proposalDay = appReqBlock.getReqParm("proposalDay");
//        String proposalYear = appReqBlock.getReqParm("proposalYear");
//
//        String proposalDate = DateTimeUtil.initDate(proposalMonth, proposalDay, proposalYear, null);
//
//        QuoteVO quoteVoOut = buildQuoteVO(appReqBlock);
//
//        SegmentVO segmentVO = quoteVoOut.getSegmentVO(0);
//
//        Proposal proposal = new Proposal(segmentVO, proposalDate);
//
//        ProposalVO proposalVO = proposal.getProposal();
//
//        appReqBlock.getHttpSession().setAttribute("proposalVO", proposalVO);
//
//        return PROPOSAL_DIALOG;
//    }

    private void getSuspenseInformation(AppReqBlock appReqBlock)
    {
        String contractId = appReqBlock.getSessionBean("quoteMainSessionBean").getValue("contractId");

        Suspense[] suspenses = Suspense.findByUserDefNumberForIssue(contractId);

        appReqBlock.getHttpServletRequest().setAttribute("suspenses", suspenses);
    }

    /**
     * Builds list of objects needed from the database
     * @return  ArrayList
     */
    private List setListForSegmentVO()
    {
        List voInclusionList = new ArrayList();
        voInclusionList.add(SegmentVO.class);
        voInclusionList.add(InvestmentVO.class);
        voInclusionList.add(InvestmentAllocationVO.class);
        voInclusionList.add(ContractClientVO.class);
        voInclusionList.add(ContractClientAllocationVO.class);
        voInclusionList.add(PayoutVO.class);
        voInclusionList.add(LifeVO.class);
        voInclusionList.add(NoteReminderVO.class);
        voInclusionList.add(BucketVO.class);
        voInclusionList.add(BucketAllocationVO.class);
        voInclusionList.add(DepositsVO.class);
        voInclusionList.add(InherentRiderVO.class);
        voInclusionList.add(ContractRequirementVO.class);
        voInclusionList.add(AgentHierarchyVO.class);
        voInclusionList.add(AgentSnapshotVO.class);
        voInclusionList.add(BillScheduleVO.class); 
        voInclusionList.add(PremiumDueVO.class);
        voInclusionList.add(CommissionPhaseVO.class);

        return voInclusionList;
    }

    private SegmentVO validateQuote(QuoteVO quoteVO, String processName) throws SPException, PortalEditingException
    {
        SegmentVO baseSegmentVO = quoteVO.getSegmentVO()[0];

        String option = baseSegmentVO.getOptionCodeCT();

        PortalEditingException editingException = null;

        ValidationVO[] validationVOs = null;
        SPOutputVO spOutputVO        = null;

        //  Call the editing scripts to validate
         try
         {
             Contract contractComponent = new ContractComponent();
             spOutputVO = contractComponent.validateQuote(quoteVO, processName);
         }
         catch (SPException e)
         {
             System.out.println(e);

             e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.

             throw e;
         }
         catch (EDITValidationException e)
         {
             System.out.println(e);

             e.printStackTrace();

             throw new PortalEditingException(e.getMessage());
         }


        VOObject[] voObjects = spOutputVO.getVOObject();
        for (int i = 0; i < voObjects.length; i++)
        {
            VOObject voObject = voObjects[i];

            if (voObject instanceof SegmentVO)
            {
            	SegmentVO segmentVO = (SegmentVO) voObject;
            	if (segmentVO.getOptionCodeCT() != null && (segmentVO.getOptionCodeCT().equalsIgnoreCase(Segment.OPTIONCODECT_TRADITIONAL_LIFE) ||
            			Segment.OPTIONCODES_AH.contains(segmentVO.getOptionCodeCT().toUpperCase())))
            	{
            		if (segmentVO.getMasterContractFK() != 0)
            		{
            			baseSegmentVO.setMasterContractFK(segmentVO.getMasterContractFK());
            		}
            	}
            }
        }


        //  Set up PortalEditingException
        validationVOs = spOutputVO.getValidationVO();

        if (spOutputVO.getValidationVOCount() > 0)
        {
            editingException = new PortalEditingException();
            editingException.setValidationVOs(validationVOs);

            String productType = checkProductType(option);

            String mainReturnPage = getMainReturnPage(productType);

            editingException.setReturnPage(mainReturnPage);

            throw editingException;
        }

        return baseSegmentVO;
   }

    /**
     * Determines the default start date for a new AgentHierarchyAllocation.  Uses the effectiveDate if set, otherwise,
     * uses the application signed date
     * @param appReqBlock
     * @return
     */
    private String getDefaultAllocationStartDate(AppReqBlock appReqBlock)
    {
        SessionBean quoteMainSessionBean = appReqBlock.getSessionBean("quoteMainSessionBean");

        PageBean formBean = quoteMainSessionBean.getPageBean("formBean");
        String effectiveDate = DateTimeUtil.formatMMDDYYYYToYYYYMMDD(formBean.getValue("effectiveDate"));
        String appSignedDate = DateTimeUtil.formatMMDDYYYYToYYYYMMDD(formBean.getValue("applicationSignedDate"));

        String startDate = null;

        if (effectiveDate == null)
        {
            // Effective date is not set, use applicationSigned Date
            startDate = appSignedDate;
        }
        else
        {
            // Effective date is set, use it
            startDate = effectiveDate;
        }

        return startDate;
    }

    /**
     * Set the start dates for all of the agentHierarchyAllocations to the segment's effective date
     * @param segmentVO
     */
    private void setAgentHierarchyAllocationStartDates(SegmentVO segmentVO)
    {
        String effectiveDate = segmentVO.getEffectiveDate();

        AgentHierarchyVO[] agentHierarchyVOs = segmentVO.getAgentHierarchyVO();

        for (int i = 0; i < agentHierarchyVOs.length; i++)
        {
            AgentHierarchyAllocationVO[] agentHierarchyAllocationVOs = agentHierarchyVOs[i].getAgentHierarchyAllocationVO();

            for (int j = 0; j < agentHierarchyAllocationVOs.length; j++)
            {
                EDITDate agtHierAllocStartDate = new EDITDate(agentHierarchyAllocationVOs[j].getStartDate());
                if (!agtHierAllocStartDate.after(new EDITDate(EDITDate.DEFAULT_MIN_DATE)))
                {
                    agentHierarchyAllocationVOs[j].setStartDate(effectiveDate);
                }
            }
        }
    }

    private String checkProductType(String optionId)
    {
        String productType = null;
        for (int i = 0; i < TRADITIONAL_PRODUCTS.length; i++)
        {
            if (optionId.equalsIgnoreCase(TRADITIONAL_PRODUCTS[i]))
            {
                productType = TRADITIONAL;
                break;
            }
        }

        if (productType == null)
		{
	        for (int i = 0; i < UNIVERSAL_LIFE_PRODUCTS.length; i++)
	        {
	            if (optionId.equalsIgnoreCase(UNIVERSAL_LIFE_PRODUCTS[i]))
	            {
	                productType = UNIVERSAL_LIFE;
	                break;
	            }
	        }
		}

		if (productType == null)
		{
			if (Segment.OPTIONCODES_AH.contains(optionId.toUpperCase())) {
				productType = AH;
			}
		}
		
        if (productType == null)
        {
            for (int i = 0; i < NON_TRADITIONAL_LIFE_PRODUCTS.length; i++)
            {
                if (optionId.equalsIgnoreCase(NON_TRADITIONAL_LIFE_PRODUCTS[i]))
                {
                    productType = NON_TRAD_LIFE;
                    break;
                }
            }
        }

        if (productType == null)
        {
            for (int i = 0; i < DEFERRED_ANNUITY_PRODUCTS.length; i++)
            {
                if (optionId.equalsIgnoreCase(DEFERRED_ANNUITY_PRODUCTS[i]))
                {
                    productType = DEFERRED_ANNUITY;
                    break;
                }
            }
        }

        if (productType == null)
        {
            for (int i = 0; i < PAYOUT_PRODUCTS.length; i++)
            {
                if (optionId.equalsIgnoreCase(PAYOUT_PRODUCTS[i]))
                {
                    productType = PAYOUT;
                    break;
                }
            }
        }

        return productType;
    }

    private String getMainReturnPage(String productType)
    {
        if (productType == null)
        {
            return QUOTE_COMMIT_MAIN;
        }
        else if (productType.equalsIgnoreCase(UNIVERSAL_LIFE))
        {
            return QUOTE_UNIVERSAL_LIFE_MAIN;
        }
        else if (productType.equalsIgnoreCase(TRADITIONAL))
        {
            return QUOTE_TRAD_MAIN;
        }
        else if (productType.equalsIgnoreCase(AH))
        {
        	return QUOTE_AH_MAIN;
        }
        else if(productType.equalsIgnoreCase(DEFERRED_ANNUITY))
        {
            return QUOTE_DEFERRED_ANNUITY_MAIN;
        }
        else if (productType.equalsIgnoreCase(NON_TRAD_LIFE))
        {
            return QUOTE_LIFE_MAIN;
        }
        else
        {
            return QUOTE_COMMIT_MAIN;
        }
    }

    private SearchResponseVO[] filterForAuthorization(AppReqBlock appReqBlock, SearchResponseVO[] searchResponseVOs)
    {
        boolean viewAllClients = false;

        ProductStructure[] productStructures = appReqBlock.getUserSession().getProductStucturesForUser();

        if (productStructures == null || productStructures.length == 0)
        {
            return null;
        }

        // we will filter the results by productStructures allowed
        // Make a Set by business contract name for quick checks
        Long securityProductStructurePK = ProductStructure.checkForSecurityStructure(productStructures);

        Set productStructuresAllowedSet = ProductStructure.checkForAuthorizedStructures(productStructures);

        if (securityProductStructurePK > 0L)
        {
            UserSession userSession = appReqBlock.getUserSession();
            if (userSession.userLoggedIn())
            {
                Operator operator = Operator.findByOperatorName(userSession.getUsername());

                viewAllClients = operator.checkViewAllAuthorization(securityProductStructurePK, "Clients");
            }
            else
            {
                viewAllClients = true;
            }
        }

        List filteredClientList = new ArrayList();
        List filteredContractList = new ArrayList();

        for (int i = 0; i < searchResponseVOs.length; i++)
        {
            filteredContractList.clear();
            SearchResponseVO searchResponseVO = searchResponseVOs[i];

            if (searchResponseVO.getSearchResponseContractInfoCount() > 0)
            {
                SearchResponseContractInfo[] searchRespContractInfo = searchResponseVO.getSearchResponseContractInfo();
                for (int j = 0; j < searchRespContractInfo.length; j++)
                {
                    SearchResponseContractInfo contractInfo = searchRespContractInfo[j];

                    String businessContractName = contractInfo.getBusinessContractName();

                    if (productStructuresAllowedSet.contains(businessContractName))
                    {
                        filteredContractList.add(contractInfo);
                    }
                }

                searchResponseVO.removeAllSearchResponseContractInfo();

                if (filteredContractList.size() > 0)
                {
                    searchRespContractInfo = (SearchResponseContractInfo[]) filteredContractList.toArray(new SearchResponseContractInfo[filteredContractList.size()]);
                    searchResponseVO.setSearchResponseContractInfo(searchRespContractInfo);
                    filteredClientList.add(searchResponseVO);
                }
                else if (viewAllClients)
                {
                    filteredClientList.add(searchResponseVO);
                }
            }
            else
            {
                if (viewAllClients)
                {
                    filteredClientList.add(searchResponseVO);
                    continue;
                }
            }
        }

        if (filteredClientList.size() == 0)
        {
            return null;
        }

        return (SearchResponseVO[]) filteredClientList.toArray(new SearchResponseVO[filteredClientList.size()]);
    }

    private ClientDetailVO[] getClientDetailsAfterSearch(SearchResponseVO[] searchResponseVOs) throws Exception
    {
        client.business.Lookup clientLookup = new client.component.LookupComponent();

        List clientDetails = new ArrayList();

        List voInclusionList = new ArrayList();
        voInclusionList.add(ClientAddressVO.class);

        for (int i = 0; i < searchResponseVOs.length; i++)
        {
            long clientDetailFK = searchResponseVOs[i].getClientDetailFK();

            ClientDetailVO clientDetailVO = clientLookup.composeClientDetailVO(clientDetailFK, voInclusionList);

            clientDetails.add(clientDetailVO);
        }

        return (ClientDetailVO[]) clientDetails.toArray(new ClientDetailVO[clientDetails.size()]);
    }

     private String showQuoteBillingDialog(AppReqBlock appReqBlock) throws Exception
    {
        SessionBean stateBean = appReqBlock.getSessionBean("contractStateBean");
        String currentPage = stateBean.getValue("currentPage");

        savePreviousPageFormBean(appReqBlock, currentPage);

        return loadContractBilling(appReqBlock);
    }

    private String showQuoteScheduledPremiumDialog(AppReqBlock appReqBlock) throws Exception
    {
//        SessionBean stateBean = appReqBlock.getSessionBean("contractStateBean");
//        String currentPage = stateBean.getValue("currentPage");
//
//        savePreviousPageFormBean(appReqBlock, currentPage);

        return QUOTE_SCHEDULED_PREMIUM_DIALOG;
    }

    private String saveBillingChange(AppReqBlock appReqBlock)
    {
        BillScheduleVO billScheduleVO = (BillScheduleVO) Util.mapFormDataToVO(appReqBlock.getHttpServletRequest(), BillScheduleVO.class, false);

        String operator = appReqBlock.getUserSession().getUsername();

        //  Convert the dates to the back-end format (gets converted to front-end format in page)
        DateTimeUtil.convertDatesToYYYYMMDD(billScheduleVO);

        if (billScheduleVO.getCreationOperator() == null)
        {
            billScheduleVO.setCreationOperator(operator);
            billScheduleVO.setCreationDate(new EDITDate().getFormattedDate());
            billScheduleVO.setBillTypeCT(BillSchedule.BILL_TYPE_INDIVUAL);
        }

        appReqBlock.getHttpSession().setAttribute("BillScheduleVO", billScheduleVO);

        return loadContractBilling(appReqBlock);
    }

    /**
     * From the quoteListBillingDialog, this brings up the changeToIndividualBillDialog
     * @param appReqBlock
     * @return
     */
    private String changeToIndividualBill(AppReqBlock appReqBlock)
    {
        return CHANGE_TO_INDIVIDUAL_BILL_DIALOG;
    }

    /**
     * From the quoteBillingDialog (Individual or List), this brings up the changeToListBillDialog
     * @param appReqBlock
     * @return
     */
    private String changeToListBill(AppReqBlock appReqBlock)
    {
        FormBean formBean = appReqBlock.getFormBean();
        appReqBlock.getHttpSession().setAttribute("DepartmentLocations", null);
        String segmentPKString = appReqBlock.getSessionBean("quoteMainSessionBean").getPageBean("formBean").getValue("segmentPK");
        Segment segment = Segment.findByPK(Long.parseLong(segmentPKString));
        ContractGroup contractGroup = ContractGroup.findByPK(segment.getContractGroupFK()) ;
        appReqBlock.getHttpSession().setAttribute("groupNumber", contractGroup.getContractGroupNumber());

        return CHANGE_TO_LIST_BILL_DIALOG;
    }

    /**
     * From the changeToIndividualBillDialog, this method gets called when hit save.  It will create a new individual
     * BillSchedule using the same info from the existing BillSchedule.  The new BillSchedule is what gets attached
     * to the Segment before the Segment save.
     *
     * @param appReqBlock
     *
     * @return
     */
    private String saveChangeToIndividualBill(AppReqBlock appReqBlock)
    {
        FormBean formBean = appReqBlock.getFormBean();

        String billMethodCT = formBean.getValue("billMethodCT");
        String operator = appReqBlock.getUserSession().getUsername();

        String segmentPK = appReqBlock.getSessionBean("quoteMainSessionBean").getPageBean("formBean").getValue("segmentPK");

        String returnPage = null;

        try
        {
            if (segmentPK != null)
            {
                Segment segment = Segment.findByPK(new Long(segmentPK));

                String areaValue = getAreaValueForIndividualBillingChange(segment);

                boolean changedBilling = segment.changeToIndividualBilling(billMethodCT, operator, areaValue);

                BillSchedule billSchedule = segment.getBillSchedule();

                BillScheduleVO billScheduleVO = (BillScheduleVO) SessionHelper.map(billSchedule, SessionHelper.EDITSOLUTIONS);

                //  When the segment's billing was changed, a new BillSchedule was created.
                //  The BillSchedule has a PK value.  But we are using VOs.
                //  The pk needs to be set to zero on the VO so it can be saved to the database on the big segment save
                if (changedBilling)
                {
                    billScheduleVO.setBillSchedulePK(0L);
                }

                //  Send the BillScheduleVO back to the page for display
                appReqBlock.getHttpSession().setAttribute("BillScheduleVO", billScheduleVO);

                //  Changes were made to the Segment's relationships and fields when converting the billing.
                //  The changes were made to a Hibernate entity.  Would be nice if we could just map that entity to
                //  a SegmentVO but the SegmentVO is not stored in the beans, the segmentPK is.  When saving the
                //  contract, the SegmentVO is built using the individual fields stored in the beans.  That means for
                //  every individual field we just modified, we have to set them in the beans or they will be lost on
                //  the save.  Ain't cloud land great?
                SessionBean quoteMainSessionBean = appReqBlock.getSessionBean("quoteMainSessionBean");
                PageBean quoteMainFormBean = quoteMainSessionBean.getPageBean("formBean");

                quoteMainFormBean.putValue("contractGroupFK", String.valueOf(Util.initLong(segment.getContractGroup(), "contractGroupPK", 0L)));
                quoteMainFormBean.putValue("billScheduleFK", String.valueOf(Util.initLong(billSchedule, "billSchedulePK", 0L)));
                quoteMainFormBean.putValue("departmentLocationFK", String.valueOf(Util.initLong(segment.getDepartmentLocation(), "departmentLocationPK", 0L)));
                quoteMainFormBean.putValue("priorContractGroupFK", String.valueOf(Util.initLong(segment.getPriorContractGroup(), "contractGroupPK", 0L)));
                quoteMainFormBean.putValue("billScheduleChangeType", Util.initString(segment.getBillScheduleChangeType(), ""));
                quoteMainFormBean.putValue("priorPRDDue", (String) Util.initObject(segment, "priorPRDDue", ""));
                // set as empty string because null won't get added to bean. Hopefully, saving of the VO will "magically" set empty strings to null before going to db

                returnPage = this.determineBillingDialogToDisplay(billScheduleVO.getBillMethodCT(), checkProductType(quoteMainFormBean.getValue("optionId")));
            }
        }
        catch (EDITEventException e)
        {
            System.out.println(e);

            e.printStackTrace();

            appReqBlock.getHttpServletRequest().setAttribute("responseMessage", "Error changing to individual billing: " + e.getMessage());
        }

        new ContractBillingTableModel(appReqBlock);

        return returnPage;
    }

    /**
     * Looks up the AreaValue for an individual billing change based on the grouping, field, productStructure, and issueState.
     *
     * @return  AreaValue matching the criteria
     */
    private String getAreaValueForIndividualBillingChange(Segment segment)
    {
    	
    	// 1. get areaValueVO for BILLCHANGE and USEISSUESTATE
    	// 2. if value of USEISSUESTATE = 'Y' then use segment.getIssueStateCT() to retrieve CONTCONV value
    	// 3. if value of USEISSUESTATE = 'N' then use segment.getOriginalStateCT() to retrieve CONTCONV value
        String grouping = "BILLCHANGE";
        String field = "CONTCONV";
        String qualifier = "*";
        EDITDate effectiveDate = new EDITDate();
        
        String state;
        if (getAreaValueForIndividualBillingChangeUseIssueState(segment)) {
            state = segment.getIssueStateCT();	
        } else {
        	state = segment.getOriginalStateCT();
        }

        engine.business.Lookup engineLookup = new engine.component.LookupComponent();

        AreaValueVO areaValueVO = engineLookup.getAreaValue(segment.getProductStructureFK().longValue(), state,
                grouping, effectiveDate, field, qualifier);

        return areaValueVO.getAreaValue();
    }

    private boolean getAreaValueForIndividualBillingChangeUseIssueState(Segment segment)
    {
    	
        String grouping = "BILLCHANGE";
        String field = "USEISSUESTATE";
        String qualifier = "*";
        EDITDate effectiveDate = new EDITDate();

        engine.business.Lookup engineLookup = new engine.component.LookupComponent();

        AreaValueVO areaValueVO = engineLookup.getAreaValue(segment.getProductStructureFK().longValue(), segment.getOriginalStateCT(),
                grouping, effectiveDate, field, qualifier);

        return (areaValueVO.getAreaValue().equals("Y")) ? true : false;
    }

    /**
     * From the changeToListBillDialog, this method gets called when hit save.  It will change the Segment's BillScheduleFK
     * to the specified Group's BillSchedule
     *
     * @param appReqBlock
     *
     * @return
     */
    private String saveChangeToListBill(AppReqBlock appReqBlock)
    {
        FormBean formBean = appReqBlock.getFormBean();

        String contractGroupNumber = formBean.getValue("contractGroupNumber");
        String departmentLocationPKString = Util.initString(formBean.getValue("departmentLocationPK"), "0");
        Long departmentLocationPK = new Long(departmentLocationPKString);

        String segmentPK = appReqBlock.getSessionBean("quoteMainSessionBean").getPageBean("formBean").getValue("segmentPK");

        String returnPage = null;

        try
        {
            if (segmentPK != null)
            {
                Segment segment = Segment.findByPK(new Long(segmentPK));

                segment.changeToListBilling(contractGroupNumber, departmentLocationPK);

                BillSchedule billSchedule = segment.getBillSchedule();

                //  Send the Segment, BillSchedule, ContractGroup, and ClientDetail back to the page for display
                BillScheduleVO billScheduleVO = (BillScheduleVO) SessionHelper.map(billSchedule, SessionHelper.EDITSOLUTIONS);

                ContractGroup groupContractGroup = segment.getContractGroup();

                if (groupContractGroup != null)
                {
                    ClientDetail clientDetail = ClientDetail.findBy_ContractGroup(groupContractGroup);

                    appReqBlock.putInRequestScope("clientDetail", clientDetail);
                }

                appReqBlock.getHttpSession().setAttribute("BillScheduleVO", billScheduleVO);

                //  Changes were made to the Segment's relationships and fields when converting the billing.
                //  The changes were made to a Hibernate entity.  Would be nice if we could just map that entity to
                //  a SegmentVO but the SegmentVO is not stored in the beans, the segmentPK is.  When saving the
                //  contract, the SegmentVO is built using the individual fields stored in the beans.  That means for
                //  every individual field we just modified, we have to set them in the beans or they will be lost on
                //  the save.  Ain't cloud land great?
                SessionBean quoteMainSessionBean = appReqBlock.getSessionBean("quoteMainSessionBean");
                PageBean quoteMainFormBean = quoteMainSessionBean.getPageBean("formBean");

                quoteMainFormBean.putValue("contractGroupFK", String.valueOf(Util.initLong(segment.getContractGroup(), "contractGroupPK", 0L)));
                quoteMainFormBean.putValue("billScheduleFK", String.valueOf(Util.initLong(billSchedule, "billSchedulePK", 0L)));
                quoteMainFormBean.putValue("departmentLocationFK", String.valueOf(Util.initLong(segment.getDepartmentLocation(), "departmentLocationPK", 0L)));
                quoteMainFormBean.putValue("priorContractGroupFK", String.valueOf(Util.initLong(segment.getPriorContractGroup(), "contractGroupPK", 0L)));
                quoteMainFormBean.putValue("billScheduleChangeType", Util.initString(segment.getBillScheduleChangeType(), ""));
                quoteMainFormBean.putValue("priorPRDDue", (String) Util.initObject(segment, "priorPRDDue", ""));
                // set as empty string because null won't get added to bean. Hopefully, saving of the VO will "magically" set empty strings to null before going to db

                returnPage = this.determineBillingDialogToDisplay(billScheduleVO.getBillMethodCT(), checkProductType(quoteMainFormBean.getValue("optionId")));
            }
        }
        catch (EDITEventException e)
        {
            System.out.println(e);

            e.printStackTrace();

            appReqBlock.getHttpServletRequest().setAttribute("responseMessage", "Error changing to list billing: " + e.getMessage());
        }

        new ContractBillingTableModel(appReqBlock);

        return returnPage;
    }

    /**
     * Helper method to load the contract billing information into the contractBillingDialog
     * @param appReqBlock
     * @return
     */
    private String loadContractBilling(AppReqBlock appReqBlock)
    {
        PageBean formBean = appReqBlock.getSessionBean("quoteMainSessionBean").getPageBean("formBean");

//        String segmentPK = formBean.getValue("segmentPK");
        String contractGroupFK = formBean.getValue("contractGroupFK");

        BillScheduleVO billScheduleVO = (BillScheduleVO) appReqBlock.getHttpSession().getAttribute("BillScheduleVO");

        if (contractGroupFK != null)
        {
            ContractGroup groupContractGroup = ContractGroup.findBy_ContractGroupPK(new Long(contractGroupFK));

            //  Get the ClientDetail that corresponds to the Group ContractGroup and put it into requestScope for display
            ClientDetail clientDetail = ClientDetail.findBy_ContractGroup(groupContractGroup);

            appReqBlock.putInRequestScope("clientDetail", clientDetail);
        }

        new ContractBillingTableModel(appReqBlock);

        return determineBillingDialogToDisplay(billScheduleVO.getBillMethodCT(), checkProductType(formBean.getValue("optionId")));
    }

    public String runReversal(AppReqBlock appReqBlock)  throws Exception
    {
        String operator = appReqBlock.getUserSession().getUsername();

        PageBean quoteMainFormBean = appReqBlock.getSessionBean("quoteMainSessionBean").getPageBean("formBean");
        PageBean formBean = appReqBlock.getFormBean();

        quoteMainFormBean.putValue("companyStructureId", quoteMainFormBean.getValue("companyStructureId"));

        String reversalReasonCode = formBean.getValue("reversalReasonCode");
        if (reversalReasonCode.equalsIgnoreCase("Please Select"))
        {
            reversalReasonCode = null;
        }
        String segmentPK = quoteMainFormBean.getValue("segmentPK");

        if (segmentPK.equals(""))
        {
            segmentPK = formBean.getValue("segmentPK");
        }

        String editTrxPK = (String)appReqBlock.getReqParm("editTrxPK");

       event.business.Event eventComponent = new event.component.EventComponent();



        EDITTrxVO editTrxVO = EDITTrx.findByPK_UsingCRUD(Long.valueOf(editTrxPK).longValue());
        String transactionTypeCT = editTrxVO.getTransactionTypeCT();

        UserSession userSession = appReqBlock.getUserSession();

        String message = "Transaction Complete";
                    if (transactionTypeCT.equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_SUBMIT)) {
                //Lookup list of transactions and see if there's an issue dated before the submit.
                HistoryFilterRow[] historyFilterRows = null;
                historyFilterRows = HistoryFilter.findHistoryFilterRows(Long.parseLong(segmentPK), "AllPeriods", null, false);
                HistoryFilterRow issueRow = null;
                for (int x = 0; x <= historyFilterRows.length - 1; x++) {
                    if (historyFilterRows[x].getTransactionTypeCT().equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_ISSUE)) {

                        issueRow = historyFilterRows[x];
                        if (!issueRow.getStatus().equalsIgnoreCase(EDITTrx.STATUS_REVERSAL)) {

                            message= "Cannot reverse submit until Issue is reversed.";
                            appReqBlock.getHttpServletRequest().setAttribute("errorMessage", message);
                            appReqBlock.setReqParm("searchValue", segmentPK);

                            userSession.unlockSegment();
                            clearAllQuoteSessions(appReqBlock);
                            SessionHelper.clearSessions();

                            appReqBlock.getHttpSession().setAttribute("reloadHeader", "true");

//                            return quoteDetailTran.loadQuoteAfterSearch(appReqBlock);
        	                return loadQuote(appReqBlock);
                        }
                    }
                }
            }

       eventComponent.reverseClientTrx(Long.parseLong(editTrxPK), operator, reversalReasonCode);


        userSession.unlockSegment();
        clearAllQuoteSessions(appReqBlock);
         

        appReqBlock.getHttpServletRequest().setAttribute("errorMessage", message);
        
        appReqBlock.getFormBean().putValue("segmentPK", segmentPK);

        return loadQuote(appReqBlock);
    }

    public String addWithdrawalTransaction(AppReqBlock appReqBlock)
    {
        PageBean quoteMainFormBean = appReqBlock.getSessionBean("quoteMainSessionBean").getPageBean("formBean");
        String segmentPK = quoteMainFormBean.getValue("segmentPK");
        Segment segment = Segment.findByPK(new Long(segmentPK));

        quoteMainFormBean.putValue("defaultTrxCode",  "Submit");
        appReqBlock.getSessionBean("quoteMainSessionBean").putPageBean("formBean", quoteMainFormBean);

        new QuoteTransactionTableModel(segment, appReqBlock);

        return QUOTE_TRANS_WITHDRAWAL;
    }

    private String cancelWithdrawalTransaction(AppReqBlock appReqBlock)
    {
        PageBean quoteMainFormBean = appReqBlock.getSessionBean("quoteMainSessionBean").getPageBean("formBean");
        String segmentPK = quoteMainFormBean.getValue("segmentPK");
        Segment segment = Segment.findByPK(new Long(segmentPK));

        quoteMainFormBean.putValue("defaultTrxCode",  "Submit");
        appReqBlock.getSessionBean("quoteMainSessionBean").putPageBean("formBean", quoteMainFormBean);

        new QuoteTransactionTableModel(segment, appReqBlock);

        return QUOTE_TRANS_WITHDRAWAL;
    }

    private String deleteWithdrawalTransaction(AppReqBlock appReqBlock) throws Exception
    {
        String key = appReqBlock.getReqParm("selectedRowIds_QuoteTransactionTableModel");

        PageBean quoteMainFormBean = appReqBlock.getSessionBean("quoteMainSessionBean").getPageBean("formBean");
        String segmentPK = quoteMainFormBean.getValue("segmentPK");
        Segment segment = Segment.findByPK(new Long(segmentPK));

        EDITTrx editTrx = EDITTrx.findBy_PK(new Long(key));
        event.business.Event eventComponent = new event.component.EventComponent();

        String responseMessage = null;
        if (editTrx != null)
        {
            String operator = appReqBlock.getUserSession().getUsername();

            eventComponent.deleteClientTrx(Long.parseLong(key), operator);

            responseMessage = "Transaction was successfully deleted.";
        }

        new QuoteTransactionTableModel(segment, appReqBlock);

        if (!responseMessage.equals(""))
        {
            appReqBlock.getHttpServletRequest().setAttribute("responseMessage", responseMessage);
        }

        return QUOTE_TRANS_WITHDRAWAL;
    }

    private String saveWithdrawalTransaction(AppReqBlock appReqBlock)   throws Exception
    {
        event.business.Event eventComponent = new event.component.EventComponent();
        PortalEditingException editingException = null;

        PageBean quoteMainFormBean = appReqBlock.getSessionBean("quoteMainSessionBean").getPageBean("formBean");
        String segmentPK = quoteMainFormBean.getValue("segmentPK");
        Segment segment = Segment.findByPK(new Long(segmentPK));
        String ignoreEditWarnings = appReqBlock.getReqParm("ignoreEditWarnings");

        GroupSetupVO groupSetupVO = buildGroupSetup(appReqBlock);
        EDITTrxVO editTrxVO = buildTransaction(appReqBlock);
        long clientSetupPK = editTrxVO.getClientSetupFK();
        if (clientSetupPK != 0)
        {
            ClientSetupVO clientSetupVO = ClientSetup.findByPK(clientSetupPK);
            groupSetupVO.getContractSetupVO(0).addClientSetupVO(clientSetupVO);
        }

        try
        {
            eventComponent.saveGroupSetup(groupSetupVO, editTrxVO, (SegmentVO)segment.getVO(), editTrxVO.getTransactionTypeCT(), segment.getOptionCodeCT(), segment.getProductStructureFK(), ignoreEditWarnings, null);

            SessionHelper.clearSessions();
            //Update the worksheetType field on the segment after submit transaction saved and process - requirement of worksheet
            if (segment.getWorksheetTypeCT() == null)
            {
                //must get the segment again because the session has been cleared prior to this.
                Segment segmentNew = Segment.findByPK(segment.getSegmentPK());
                segmentNew.updateWorksheetType(Segment.WORKSHEETTYPECT_CORRECTION);
            }
        }
        catch (EDITEventException e)
        {
          System.out.println(e);

            e.printStackTrace();

            String message = "Error Saving Transaction [" + e.getMessage() + "]";

            appReqBlock.getHttpServletRequest().setAttribute("message", message);

            ValidationVO[] validationVOs = e.getValidationVO();

            if (validationVOs != null)
            {
                if (validationVOs.length > 0)
                {
                    editingException = new PortalEditingException();

                    editingException.setValidationVOs(validationVOs);
                }
            }
            else
            {
                throw e;
            }
        }
        catch (PortalEditingException e)
        {
            editingException = e;
        }
        catch (EDITValidationException e)
        {
            editingException = new PortalEditingException(e.getMessage());
        }
        catch (RuntimeException e)
        {
            String message = e.getMessage();
            appReqBlock.getHttpServletRequest().setAttribute("message", message);
        }

        SessionHelper.clearSessions();
        
        appReqBlock.getHttpSession().setAttribute("reloadHeader", "true");

        new QuoteTransactionTableModel(segment, appReqBlock);

        return QUOTE_TRANS_WITHDRAWAL;
    }

    protected String analyzeTransaction(AppReqBlock appReqBlock) throws Exception
    {
        PageBean quoteMainFormBean = appReqBlock.getSessionBean("quoteMainSessionBean").getPageBean("formBean");
        String segmentPK = quoteMainFormBean.getValue("segmentPK");
        Segment segment = Segment.findByPK(new Long(segmentPK));
        String trxDesc = appReqBlock.getReqParm("transactionType");

        GroupSetupVO groupSetupVO = buildGroupSetup(appReqBlock);
        EDITTrxVO editTrxVO = buildTransaction(appReqBlock);
        long clientSetupPK = editTrxVO.getClientSetupFK();
        if (clientSetupPK != 0)
        {
            ClientSetupVO clientSetupVO = ClientSetup.findByPK(clientSetupPK);
            groupSetupVO.getContractSetupVO(0).addClientSetupVO(clientSetupVO);
        }
//        editTrxVO.setEDITTrxPK(new Long(0));

        CodeTableComponent codeTableComponent = new CodeTableComponent();

        Analyzer analyzer = new AnalyzerComponent();
        HttpSession session = appReqBlock.getHttpSession();
        session.setAttribute("analyzerComponent", analyzer);
        VOObject document = null;
        String rootName = null;

        String transactionType = editTrxVO.getTransactionTypeCT();
        long productStructureFK = segment.getProductStructureFK().longValue();
        String eventType = segment.setEventTypeForDriverScript();

        String statusInd = editTrxVO.getStatus();
        if (statusInd.equalsIgnoreCase("A"))
        {
            RedoDocVO redoDocVO = codeTableComponent.buildRedoDocument(groupSetupVO.getContractSetupVO(0).getClientSetupVO(0).getEDITTrxVO(0));
            document = redoDocVO;
            rootName = "RedoDocVO";
        }
        else
        {
            NaturalDocVO naturalDocVO = codeTableComponent.buildNaturalDocForAnalyzer(groupSetupVO, editTrxVO, transactionType, segment.getOptionCodeCT(), productStructureFK);
            document = naturalDocVO;
            rootName = "NaturalDocVO";
        }

        // Parameter isAnalyzeTrnasaction - when analyzing transaction the value is true.
        analyzer.loadScriptAndParameters(rootName, document, trxDesc, statusInd, eventType, editTrxVO.getEffectiveDate(), productStructureFK, true);

        SessionBean paramBean = appReqBlock.getSessionBean("paramBean");
        paramBean.clearState();

        PageBean debugScriptBean = paramBean.getPageBean("debugScriptBean");
        String[] scriptLines = analyzer.getScriptLines();
        debugScriptBean.putValues("scriptLines", scriptLines, new String[]
            {
                "toString"
            }, null);

        String scriptName = analyzer.getScriptName();
        debugScriptBean.putValue("scriptName", scriptName);
        // No need to reset the script processor.
//                        analyzer.resetScriptProcessor();

        String[] stringArray = analyzer.getDataStack();
        debugScriptBean.putValues("dataStackValues", stringArray, new String[]
            {
                "toString"
            }, null);

        appReqBlock.getHttpSession().setAttribute("workingStorage", analyzer.getWS());
        stringArray = analyzer.getFunctions();
        debugScriptBean.putValues("functionTables", stringArray, new String[]
            {
                "toString"
            }, null);
        stringArray = analyzer.getFunctionEntry("");
        debugScriptBean.putValues("functionEntries", stringArray, new String[]
            {
                "toString"
            }, null);
        stringArray = debugScriptBean.getValues("breakPoints");
        debugScriptBean.putValues("breakPoints", stringArray, new String[]
            {
                "toString"
            }, null);
        debugScriptBean.putValue("instPtr", analyzer.getInstPtr());
        debugScriptBean.putValue("lastInstPtr", analyzer.getLastInstPtr());
        debugScriptBean.putValue("currentRow", analyzer.getCurrentRow());
        appReqBlock.getHttpServletRequest().setAttribute("pageBean", debugScriptBean);
        appReqBlock.getHttpServletRequest().setAttribute("analyzeTransaction", "true");
        appReqBlock.getHttpServletRequest().setAttribute("effectiveDate", appReqBlock.getFormBean().getValue("effectiveDate"));
        appReqBlock.getHttpServletRequest().setAttribute("sequenceNumber", appReqBlock.getFormBean().getValue("sequenceNumber"));
        appReqBlock.getHttpServletRequest().setAttribute("taxYear", appReqBlock.getFormBean().getValue("taxYear"));
        appReqBlock.getHttpServletRequest().setAttribute("amount", appReqBlock.getFormBean().getValue("amount"));
        appReqBlock.getHttpServletRequest().setAttribute("optionId", appReqBlock.getFormBean().getValue("optionId"));
        appReqBlock.getHttpServletRequest().setAttribute("ratedGenderCT", appReqBlock.getFormBean().getValue("ratedGenderCT"));
        appReqBlock.getHttpServletRequest().setAttribute("originalStateCT", appReqBlock.getFormBean().getValue("originalStateCT"));
        appReqBlock.getHttpServletRequest().setAttribute("location", appReqBlock.getFormBean().getValue("location"));
        appReqBlock.getHttpServletRequest().setAttribute("sequence", appReqBlock.getFormBean().getValue("sequence"));
        appReqBlock.getHttpServletRequest().setAttribute("indivAnnPremium", appReqBlock.getFormBean().getValue("indivAnnPremium"));
        appReqBlock.getHttpServletRequest().setAttribute("underwritingClass", appReqBlock.getFormBean().getValue("underwritingClass"));
        appReqBlock.getHttpServletRequest().setAttribute("groupPlan", appReqBlock.getFormBean().getValue("groupPlan"));
        appReqBlock.getHttpServletRequest().setAttribute("percent", appReqBlock.getFormBean().getValue("percent"));
        appReqBlock.getHttpServletRequest().setAttribute("status", appReqBlock.getFormBean().getValue("status"));
        appReqBlock.getHttpServletRequest().setAttribute("pendingStatus", appReqBlock.getFormBean().getValue("pendingStatus"));
        appReqBlock.getHttpServletRequest().setAttribute("operator", appReqBlock.getFormBean().getValue("operator"));
        appReqBlock.getHttpServletRequest().setAttribute("dateTime", appReqBlock.getFormBean().getValue("dateTime"));

        new QuoteTransactionTableModel(segment, appReqBlock);

        return QUOTE_TRANS_WITHDRAWAL;
    }

    private GroupSetupVO buildGroupSetup(AppReqBlock appReqBlock)
    {
        String key = Util.initString(appReqBlock.getReqParm("selectedRowIds_QuoteTransactionTableModel"), "0");

        PageBean formBean = appReqBlock.getFormBean();
        String segmentPK = appReqBlock.getSessionBean("quoteMainSessionBean").getPageBean("formBean").getValue("segmentPK");
        Segment segment = Segment.findByPK(new Long(segmentPK));

        long groupSetupPK = 0;
        long contractSetupPK = 0;
//        long editTrxPK = 0;
        EDITTrx editTrx = null;

        if (!key.equals("0"))
        {
            editTrx = EDITTrx.findBy_PK(new Long(key));
//            editTrxPK = editTrx.getPK();
            groupSetupPK = editTrx.getClientSetup().getContractSetup().getGroupSetupFK();
            contractSetupPK = editTrx.getClientSetup().getContractSetupFK();
        }

        GroupSetupVO groupSetupVO = null;
        ContractSetupVO contractSetupVO = null;

        if (groupSetupPK == 0)
        {
            groupSetupVO = new ContractEvent().buildDefaultGroupSetup(new Segment[] { segment });
            groupSetupVO.setGroupSetupPK(groupSetupPK);
            contractSetupVO = groupSetupVO.getContractSetupVO(0);
            contractSetupVO.setContractSetupPK(contractSetupPK);
        }
        else
        {
            groupSetupVO = GroupSetup.findByPK(new Long(groupSetupPK)).getAsVO();
        }

        String amount = Util.initString(formBean.getValue("amount"), "0");
        groupSetupVO.setGroupAmount(new EDITBigDecimal(amount).getBigDecimal());
        groupSetupVO.setGroupPercent(new EDITBigDecimal(Util.initString(formBean.getValue("percent"), "0")).getBigDecimal());

        if (contractSetupPK != 0)
        {
            contractSetupVO = ContractSetup.findByPK(contractSetupPK);
            groupSetupVO.addContractSetupVO(contractSetupVO);
        }

        contractSetupVO.setGroupSetupFK(groupSetupPK);
        contractSetupVO.setPolicyAmount(new EDITBigDecimal(amount).getBigDecimal());

       return groupSetupVO;
    }

    private EDITTrxVO buildTransaction(AppReqBlock appReqBlock)
    {
        String key = Util.initString(appReqBlock.getReqParm("selectedRowIds_QuoteTransactionTableModel"), "0");

        PageBean formBean = appReqBlock.getFormBean();
//        String segmentPK = appReqBlock.getSessionBean("quoteMainSessionBean").getPageBean("formBean").getValue("segmentPK");
//        Segment segment = Segment.findByPK(new Long(segmentPK));

        long clientSetupPK = 0;
        long editTrxPK = 0;
        EDITTrx editTrx = null;
//        ClientSetupVO clientSetupVO = null;

        if (!key.equals("0"))
        {
            editTrx = EDITTrx.findBy_PK(new Long(key));
            editTrxPK = editTrx.getPK();
            clientSetupPK = editTrx.getClientSetupFK();
        }

        appReqBlock.getHttpServletRequest().removeAttribute("selectedTransaction");

        String transactionType = formBean.getValue("transactionType");
        String codeValue = CodeTableWrapper.getSingleton().getCodeByCodeTableNameAndCodeDesc("TRXTYPE", transactionType);

        String trxEffDate = DateTimeUtil.formatMMDDYYYYToYYYYMMDD(formBean.getValue("effectiveDate"));

        EDITDate effectiveDate = null;
        int taxYear = 0;

        if (trxEffDate != null)
        {
            effectiveDate = new EDITDate(trxEffDate);
            taxYear = effectiveDate.getYear();
        }

        editTrx = EDITTrx.buildDefaultEDITTrx(codeValue, effectiveDate, taxYear, appReqBlock.getUserSession().getUsername());

        EDITTrxVO editTrxVO = editTrx.getAsVO();
        editTrxVO.setEDITTrxPK(editTrxPK);
        editTrxVO.setClientSetupFK(clientSetupPK);

        String amount = Util.initString(formBean.getValue("amount"), "0");
        editTrxVO.setTrxAmount(new EDITBigDecimal(amount).getBigDecimal());
        editTrxVO.setTrxPercent(new EDITBigDecimal(Util.initString(formBean.getValue("percent"), "0")).getBigDecimal());

        //Now rebuild trx if edit being resaved
        if (editTrx != null)
        {
            EDITDate dueDate = editTrx.getDueDate();

            if (dueDate != null)
            {
                editTrxVO.setDueDate(dueDate.getFormattedDate());
            }

            editTrxVO.setLookBackInd(editTrx.getLookBackInd());
            editTrxVO.setNoCorrespondenceInd(editTrx.getNoCorrespondenceInd());
            editTrxVO.setNoAccountingInd(editTrx.getNoAccountingInd());
            editTrxVO.setNoCommissionInd(editTrx.getNoCommissionInd());
            editTrxVO.setZeroLoadInd(editTrx.getZeroLoadInd());
            editTrxVO.setNoCheckEFT(editTrx.getNoCheckEFT());
            editTrxVO.setReinsuranceStatus(editTrx.getReinsuranceStatus());
            editTrxVO.setNotificationAmount(editTrx.getNotificationAmount().getBigDecimal());
            editTrxVO.setNotificationAmountReceived(editTrx.getNotificationAmountReceived().getBigDecimal());
            editTrxVO.setAccountingPeriod(editTrx.getAccountingPeriod());
            editTrxVO.setOriginalAccountingPeriod(editTrx.getOriginalAccountingPeriod());
            EDITDate dateContributionExcess = editTrx.getDateContributionExcess();
            if (dateContributionExcess != null)
            {
                editTrxVO.setDateContributionExcess(dateContributionExcess.getFormattedDate());
            }
            editTrxVO.setTrxIsRescheduledInd(editTrx.getTrxIsRescheduledInd());
            Long reapplyEDITTrxFK = editTrx.getReapplyEDITTrxFK();
            if (reapplyEDITTrxFK != null)
            {
                editTrxVO.setReapplyEDITTrxFK(reapplyEDITTrxFK.longValue());
            }
            editTrxVO.setCommissionStatus(editTrx.getCommissionStatus());

            Long originatingTrxFK = editTrx.getOriginatingTrxFK();
            if (originatingTrxFK != null)
            {
                editTrxVO.setOriginatingTrxFK(originatingTrxFK);
            }
            editTrxVO.setInterestProceedsOverride(editTrx.getInterestProceedsOverride().getBigDecimal());
            editTrxVO.setTransferTypeCT(editTrx.getTransferTypeCT());
            editTrxVO.setAuthorizedSignatureCT(editTrx.getAuthorizedSignatureCT());
            editTrxVO.setNewPolicyNumber(editTrx.getNewPolicyNumber());
            editTrxVO.setAdvanceNotificationOverride(editTrx.getAdvanceNotificationOverride());
            editTrxVO.setBonusCommissionAmount(editTrx.getBonusCommissionAmount().getBigDecimal());
            editTrxVO.setExcessBonusCommissionAmount(editTrx.getExcessBonusCommissionAmount().getBigDecimal());
            editTrxVO.setTransferUnitsType(editTrx.getTransferUnitsType());
            editTrxVO.setReversalReasonCodeCT(editTrx.getReversalReasonCodeCT());
            Long checkAdjustmentFK = editTrx.getCheckAdjustmentFK();
            if (checkAdjustmentFK != null)
            {
                editTrxVO.setCheckAdjustmentFK(checkAdjustmentFK);
            }
        }

       return editTrxVO;
    }

    /**
     * Finds the departmentLocations for a Group ContractGroup with the input contractGroupNumber.  Sends them
     * back to the changeToListBillDialog.
     *
     * @param appReqBlock
     * @return
     */
    public String findDepartmentLocations(AppReqBlock appReqBlock)
    {
        DepartmentLocation[] departmentLocations = null;

        FormBean formBean = appReqBlock.getFormBean();

        String contractGroupNumber = formBean.getValue("contractGroupNumber");

        ContractGroup groupContractGroup = ContractGroup.findBy_ContractGroupNumber_ContractGroupTypeCT(contractGroupNumber, ContractGroup.CONTRACTGROUPTYPECT_GROUP);

        if (groupContractGroup == null)
        {
            appReqBlock.getHttpServletRequest().setAttribute("responseMessage", "Group Number " + contractGroupNumber + " does not exist.");
        }
        else
        {
            departmentLocations = DepartmentLocation.findBy_ContractGroupNumber(contractGroupNumber);
        }

        appReqBlock.getHttpSession().setAttribute("DepartmentLocations", departmentLocations);

        return CHANGE_TO_LIST_BILL_DIALOG;
    }

    private String showClassGenderRatingsDialog(AppReqBlock appReqBlock) throws Exception
    {
        new NewBusinessUseCaseComponent().accessClassGenderRatings();

        SessionBean stateBean = appReqBlock.getSessionBean("quoteStateBean");
//        PageBean formBean = appReqBlock.getFormBean();
        PageBean clientFormBean = appReqBlock.getSessionBean("quoteMainSessionBean").getPageBean("clientFormBean");

        String currentPage = stateBean.getValue("currentPage");

        savePreviousPageFormBean(appReqBlock, currentPage);

        appReqBlock.getSessionBean("quoteMainSessionBean").putPageBean("clientFormBean", clientFormBean);

        return CLASS_GENDER_RATINGS_DIALOG;
    }

    private String saveClassGenderRatingsDialog(AppReqBlock appReqBlock)
    {
        new NewBusinessUseCaseComponent().updateClassGenderRatings();

        PageBean formBean = appReqBlock.getFormBean();
        PageBean clientFormBean = appReqBlock.getSessionBean("quoteMainSessionBean").getPageBean("clientFormBean");
        SessionBean clients = appReqBlock.getSessionBean("quoteClients");

        String taxId = Util.initString(clientFormBean.getValue("taxId"), null);
        String riderNumber = clientFormBean.getValue("riderNumber");
        String optionId = clientFormBean.getValue("optionId");
        String relationship = clientFormBean.getValue("relationshipInd");
        String clientRoleFK = clientFormBean.getValue("clientRoleFK");
        String contractClientPK = clientFormBean.getValue("contractClientPK");
        String clientIdKey = taxId + riderNumber + optionId + relationship + contractClientPK + clientRoleFK;

        PageBean clientPageBean = clients.getPageBean(clientIdKey);

        String classType = Util.initString(formBean.getValue("classType"), "");
        String tableRating = Util.initString(formBean.getValue("tableRating"), "");
        String ratedGender = Util.initString(formBean.getValue("ratedGender"), "");
        String underwritingClass =  Util.initString(formBean.getValue("underwritingClass"), "");

        clientPageBean.putValue("classType", classType);
        clientPageBean.putValue("tableRating", tableRating);
        clientPageBean.putValue("ratedGender", ratedGender);
        clientPageBean.putValue("underwritingClass", underwritingClass);
        clientPageBean.putValue("flatExtra", Util.initString(formBean.getValue("flatExtra"), ""));
        clientPageBean.putValue("flatExtraAge", Util.initString(formBean.getValue("flatExtraAge"), ""));
        clientPageBean.putValue("flatExtraDur", Util.initString(formBean.getValue("flatExtraDur"), ""));
        clientPageBean.putValue("percentExtra", Util.initString(formBean.getValue("percentExtra"), ""));
        clientPageBean.putValue("percentExtraAge", Util.initString(formBean.getValue("percentExtraAge"), ""));
        clientPageBean.putValue("percentExtraDur", Util.initString(formBean.getValue("percentExtraDur"), ""));
//        clientPageBean.putValue("taxId", taxId);
//        clientPageBean.putValue("optionId", optionId);
//        clientPageBean.putValue("relationship", relationship);
//        clientPageBean.putValue("clientRoleFK", clientRoleFK);
//        clientPageBean.putValue("contractClientPK", contractClientPK);

        clients.putPageBean(clientIdKey, clientPageBean);
        appReqBlock.getSessionBean("quoteMainSessionBean").putPageBean("clientFormBean", clientPageBean);

        return QUOTE_COMMIT_INSURED;
    }

//    private boolean complexChangeTypeIsTerminationType(String complexChangeType)
//    {
//        boolean isTerminationType = false;
//        for (int i = 0; i < ContractSetup.TERMINATION_COMPLEXCHANGETYPES.length; i++)
//        {
//            if (complexChangeType.equalsIgnoreCase(ContractSetup.TERMINATION_COMPLEXCHANGETYPES[i]))
//            {
//                isTerminationType = true;
//                break;
//            }
//        }
//
//        return isTerminationType;
//    }

    protected String showPreferences(AppReqBlock appReqBlock) throws Exception
    {
        SessionBean stateBean = appReqBlock.getSessionBean("quoteStateBean");
        String currentPage = stateBean.getValue("currentPage");
        String returnPage = currentPage;

        savePreviousPageFormBean(appReqBlock, currentPage);

        SessionBean preferenceSessionBean = new SessionBean();
        String selectedClientRoleFK = appReqBlock.getFormBean().getValue("selectedClientRoleFK");
        ClientRole clientRole = ClientRole.findByPK(new Long(selectedClientRoleFK));
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
        appReqBlock.getHttpServletRequest().setAttribute("selectedClientRoleFK", selectedClientRoleFK);
        appReqBlock.getHttpServletRequest().setAttribute("returnPage", returnPage);

        return PREFERENCES_DIALOG;
    }

    private String showSelectedPreference(AppReqBlock appReqBlock)
    {
        PageBean formBean = appReqBlock.getFormBean();
        String preferencePK = formBean.getValue("preferencePK");
        String selectedClientRoleFK = formBean.getValue("selectedClientRoleFK");
        String returnPage = formBean.getValue("returnPage");

        PageBean pageBean = appReqBlock.getSessionBean("preferenceSessionBean").getPageBean(preferencePK);

        appReqBlock.getHttpServletRequest().setAttribute("pageBean", pageBean);
        appReqBlock.getHttpServletRequest().setAttribute("selectedClientRoleFK", selectedClientRoleFK);
        appReqBlock.getHttpServletRequest().setAttribute("returnPage", returnPage);

        return PREFERENCES_DIALOG;
    }

    private String clearPreferenceForAdd(AppReqBlock appReqBlock)
    {
        PageBean formBean = appReqBlock.getFormBean();

        String selectedClientRoleFK = formBean.getValue("selectedClientRoleFK");
        String returnPage = formBean.getValue("returnPage");

        appReqBlock.getHttpServletRequest().setAttribute("selectedClientRoleFK", selectedClientRoleFK);
        appReqBlock.getHttpServletRequest().setAttribute("returnPage", returnPage);

        return PREFERENCES_DIALOG;
    }

    private String showContractAgentInfo(AppReqBlock appReqBlock)
    {
        String preferencePK = appReqBlock.getFormBean().getValue("preferencePK");
        String selectedClientRoleFK = appReqBlock.getFormBean().getValue("selectedClientRoleFK");
        String returnPage = appReqBlock.getFormBean().getValue("returnPage");

        appReqBlock.getHttpServletRequest().setAttribute("preferencePK", preferencePK);
        appReqBlock.getHttpServletRequest().setAttribute("selectedClientRoleFK", selectedClientRoleFK);
        appReqBlock.getHttpServletRequest().setAttribute("returnPage", returnPage);

        return CONTRACT_AGENT_INFO_DIALOG;
    }

    private String savePreference(AppReqBlock appReqBlock)
    {
        PageBean formBean = appReqBlock.getFormBean();

        String selectedClientRoleFK = formBean.getValue("selectedClientRoleFK");
        String returnPage = formBean.getValue("returnPage");
        appReqBlock.getHttpServletRequest().setAttribute("selectedClientRoleFK", selectedClientRoleFK);
        appReqBlock.getHttpServletRequest().setAttribute("returnPage", returnPage);

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
                ClientRole clientRole = ClientRole.findByPK(new Long(selectedClientRoleFK));
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

    protected String selectPreferenceForClient(AppReqBlock appReqBlock)
    {
        PageBean formBean = appReqBlock.getFormBean();
        String selectedClientRoleFK = formBean.getValue("selectedClientRoleFK");
        String preferencePK = formBean.getValue("preferencePK");
        String returnPage = formBean.getValue("returnPage");

        try
        {
            Preference preference = Preference.findByPK(new Long(preferencePK));
            Preference.updateExistingPreference(preference, new Long(selectedClientRoleFK));
            appReqBlock.getHttpServletRequest().setAttribute("clientMessage", "Preference Updated");
        }
        catch(Exception e)
        {
            appReqBlock.getHttpServletRequest().setAttribute("clientMessage", "Preference Update Failed");
        }

        appReqBlock.getHttpSession().removeAttribute("preferenceSessionBean");
        appReqBlock.getHttpServletRequest().setAttribute("selectedClientRoleFK", selectedClientRoleFK);

        SessionBean stateBean = appReqBlock.getSessionBean("contractStateBean");
        stateBean.putValue("previousPage", stateBean.getValue("currentPage"));
        stateBean.putValue("currentPage", returnPage);

        return returnPage;
    }

//    private boolean complexChangeTypeIsTerminationType(String complexChangeType)
//    {
//        boolean isTerminationType = false;
//        for (int i = 0; i < ContractSetup.TERMINATION_COMPLEXCHANGETYPES.length; i++)
//        {
//            if (complexChangeType.equalsIgnoreCase(ContractSetup.TERMINATION_COMPLEXCHANGETYPES[i]))
//            {
//                isTerminationType = true;
//                break;
//            }
//        }
//
//        return isTerminationType;
//    }

    /**
     *
     * @param appReqBlock
     * @param effectiveDate                 In the format yyyy/mm/dd
     * @param followupDays
     */
    private void setRequirementDates(AppReqBlock appReqBlock, String effectiveDate, int followupDays)
    {
        // Setting RequirementEffectiveDate for Segment
        PageBean quoteMainFormBean = appReqBlock.getSessionBean("quoteMainSessionBean").getPageBean("formBean");

        String finalNotifyDate = DateTimeUtil.formatMMDDYYYYToYYYYMMDD(quoteMainFormBean.getValue("finalNotifyDate"));
        String requirementEffectiveDate = DateTimeUtil.formatMMDDYYYYToYYYYMMDD(quoteMainFormBean.getValue("requirementEffectiveDate"));
        
        // deck - Fix for MIB pending requirement handling
        if ((finalNotifyDate == null || finalNotifyDate.equals("")) || 
        		(((requirementEffectiveDate != null) && (finalNotifyDate != null)) && 
        		requirementEffectiveDate.equals(finalNotifyDate)))
        {
            quoteMainFormBean.putValue("requirementEffectiveDate", DateTimeUtil.formatYYYYMMDDToMMDDYYYY(effectiveDate));

            EDITDate edFinalNotifyDate = new EDITDate(effectiveDate);
            edFinalNotifyDate = edFinalNotifyDate.addDays(followupDays);
            quoteMainFormBean.putValue("finalNotifyDate", DateTimeUtil.formatYYYYMMDDToMMDDYYYY(edFinalNotifyDate.getFormattedDate()));

            appReqBlock.getSessionBean("quoteMainSessionBean").putPageBean("formBean", quoteMainFormBean);
        }
        else
        {
            if (requirementEffectiveDate.equals(effectiveDate)) 
            {
                EDITDate edFinalNotifyDate = new EDITDate(finalNotifyDate);
                EDITDate edReqEffDate = new EDITDate(requirementEffectiveDate);
                edReqEffDate = edReqEffDate.addDays(followupDays);
                if (edReqEffDate.after(edFinalNotifyDate))
                {
                    quoteMainFormBean.putValue("finalNotifyDate", DateTimeUtil.formatYYYYMMDDToMMDDYYYY(edReqEffDate.getFormattedDate()));

                    appReqBlock.getSessionBean("quoteMainSessionBean").putPageBean("formBean", quoteMainFormBean);
                }
            }
        }
    }

    private String showPremiumDueHistoryDialog(AppReqBlock appReqBlock)
    {
        new PremiumDueHistoryTableModel(appReqBlock);

        return PREMIUM_DUE_HISTORY_DIALOG;
	}

    private String showContractBillHistoryDialog(AppReqBlock appReqBlock)
    {
        new ContractBillHistoryTableModel(appReqBlock);

        return CONTRACT_BILLS_HISTORY;
    }
        
    /**
     * Determines if the contractClient was deleted by the user or not.  Compares the contractClientPK to the pk in
     * the deletedContractClientPageBeans.
     *
     * @param contractClientPK
     * @param appReqBlock
     *
     * @return  true if the ContractClient with a pk of contractClientPK was deleted, false otherwise
     */
    private boolean contractClientWasDeleted(String contractClientPK, AppReqBlock appReqBlock)
    {
        boolean contractClientWasDeleted = false;

        SessionBean deletedContractClients = appReqBlock.getSessionBean("quoteDeletedContractClients");

        if (deletedContractClients != null)
        {
            Map deletedContractClientPageBeans = deletedContractClients.getPageBeans();

            Iterator iterator = deletedContractClientPageBeans.values().iterator();

            while (iterator.hasNext())
            {
                PageBean deletedContractClientBean = (PageBean) iterator.next();

                String beanContractClientPK = deletedContractClientBean.getValue("contractClientPK");

                if (beanContractClientPK.equalsIgnoreCase(contractClientPK))
                {
                    contractClientWasDeleted = true;
                    break;
                }
            }
        }

        return contractClientWasDeleted;
    }

    private String showRiderCoverageSelectionDialog(AppReqBlock appReqBlock)
    {
        return RIDER_COVERAGE_SELECTION_DIALOG;
    }

    private String saveRiderCoverageSelection(AppReqBlock appReqBlock)
    {
        PageBean baseFormBean = appReqBlock.getSessionBean("quoteMainSessionBean").getPageBean("formBean");
        String insuredRelationToEmp = Util.initString(appReqBlock.getSessionBean("quoteMainSessionBean").getValue("insuredRelationToEmp"), null);

        String riderCoveragePK = Util.initString(appReqBlock.getReqParm("selectedCoverage"), "");
        CodeTableVO codeTableVO = CodeTableWrapper.getSingleton().getCodeTableEntry(Long.parseLong(riderCoveragePK));
        String optionCodeDesc   = codeTableVO.getCodeDesc();
        String optionCode       = codeTableVO.getCode();

        //Check the option code for display of increaseOption
        String batchContractSetupFK = Util.initString(baseFormBean.getValue("batchContractSetupFK"), "0");
        String increaseOptionStatus = getCaseProductUnderwriting(appReqBlock, batchContractSetupFK, optionCode, insuredRelationToEmp);

        baseFormBean.putValue("increaseOptionStatus", increaseOptionStatus);
        baseFormBean.putValue("selectedCoveragePK", riderCoveragePK);
        baseFormBean.putValue("riderOption", optionCodeDesc);
        baseFormBean.putValue("pageMode", "detail");

        appReqBlock.getSessionBean("quoteMainSessionBean").putPageBean("riderFormBean", new PageBean());

        if (baseFormBean.getValue("optionId").equalsIgnoreCase(QuoteDetailTran.TRADITIONAL) ||
        	baseFormBean.getValue("optionId").equalsIgnoreCase(QuoteDetailTran.UNIVERSAL_LIFE) ||
            baseFormBean.getValue("optionId").equalsIgnoreCase("Life") ||
            Segment.OPTIONCODES_AH.contains(baseFormBean.getValue("optionId").toUpperCase()))
        {
            return QUOTE_COMMIT_LIFE_RIDER;
        }
        else
        {
            return QUOTE_COMMIT_RIDER;
        }
    }

    private String getCaseProductUnderwriting(AppReqBlock appReqBlock, String batchContractSetupFK, String optionCode, String insuredRelationToEmp)
    {
        String increaseOptionStatus = "";

        if (optionCode.equalsIgnoreCase(Segment.OPTIONCODECT_RIDER_GUARANTEED_INSURANCE_OPTION) ||
            optionCode.equalsIgnoreCase(Segment.OPTIONCODECT_RIDER_LEVEL_TERM_GUARANTEED_INSURANCE_OPTION))
        {
            increaseOptionStatus = "display";
            //get CaseProductUnderwriting
            if (!batchContractSetupFK.equals("0"))
            {
                BatchContractSetup batchContractSetup = BatchContractSetup.findByPK(Long.parseLong(batchContractSetupFK));
                CaseProductUnderwriting[] caseProductUnderwritings = CaseProductUnderwriting.findByEnrollmentFK_FilteredProductFK_Grouping_Field_RelationshipToEmployeeCT(batchContractSetup.getEnrollmentFK(),
                        batchContractSetup.getFilteredProductFK(), CaseProductUnderwriting.GROUPING_CASERIDERS, CaseProductUnderwriting.FIELD_GIOOPTDATES, insuredRelationToEmp);

                if (caseProductUnderwritings != null && caseProductUnderwritings.length > 0)
                {
                    appReqBlock.getHttpServletRequest().setAttribute("caseProductUnderwriting", caseProductUnderwritings);
                }
            }
        }

        return increaseOptionStatus;
    }

    private void getCaseUnderwritingForClient(AppReqBlock appReqBlock, String batchContractSetupFK)
    {
        if (!batchContractSetupFK.equals("0"))
        {
            BatchContractSetup batchContractSetup = BatchContractSetup.findByPK(Long.parseLong(batchContractSetupFK));
            CaseProductUnderwriting[] ratedGenders = CaseProductUnderwriting.findByEnrollmentFK_FilteredProductFK_Grouping_Field_RelationshipToEmployeeCT(batchContractSetup.getEnrollmentFK(),
                    batchContractSetup.getFilteredProductFK(), CaseProductUnderwriting.GROUPING_CASEOTHER, CaseProductUnderwriting.FIELD_RATEDGENDER, null);

            if (ratedGenders != null && ratedGenders.length > 0)
            {
                appReqBlock.getHttpServletRequest().setAttribute("ratedGenders", ratedGenders);
            }

            CaseProductUnderwriting[] underwritingClasses = CaseProductUnderwriting.findByEnrollmentFK_FilteredProductFK_Grouping_Field_RelationshipToEmployeeCT(batchContractSetup.getEnrollmentFK(),
                    batchContractSetup.getFilteredProductFK(), CaseProductUnderwriting.GROUPING_CASEOTHER, CaseProductUnderwriting.FIELD_UNDERWRITING_CLASS, null);

            if (underwritingClasses != null && underwritingClasses.length > 0)
            {
                appReqBlock.getHttpServletRequest().setAttribute("underwritingClasses", underwritingClasses);
            }
        }
    }
    
      private void getCaseUnderwritingForContract(AppReqBlock appReqBlock, String batchContractSetupFK)
    {
        if (!batchContractSetupFK.equals("0"))
        {
            BatchContractSetup batchContractSetup = BatchContractSetup.findByPK(Long.parseLong(batchContractSetupFK));
            CaseProductUnderwriting[] ratedGenders = CaseProductUnderwriting.findByEnrollmentFK_FilteredProductFK_Grouping_Field_RelationshipToEmployeeCT(batchContractSetup.getEnrollmentFK(),
                    batchContractSetup.getFilteredProductFK(), CaseProductUnderwriting.GROUPING_CASEOTHER, CaseProductUnderwriting.FIELD_RATEDGENDER, null);

            if (ratedGenders != null && ratedGenders.length > 0)
            {
                appReqBlock.getHttpServletRequest().setAttribute("ratedGenders", ratedGenders);
            }
            
            CaseProductUnderwriting[] underwritingClasses = CaseProductUnderwriting.findByEnrollmentFK_FilteredProductFK_Grouping_Field_RelationshipToEmployeeCT(batchContractSetup.getEnrollmentFK(),
                    batchContractSetup.getFilteredProductFK(), CaseProductUnderwriting.GROUPING_CASEOTHER, CaseProductUnderwriting.FIELD_UNDERWRITING_CLASS, null);

            if (underwritingClasses != null && underwritingClasses.length > 0)
            {
                appReqBlock.getHttpServletRequest().setAttribute("underwritingClasses", underwritingClasses);
            }

        }
    }
      
      private String convertEmptyToNull(String input) {
          if (input == null || input.trim().length() == 0)
          {
              return null;
          }
          return input;
      }
      
  	@Override
  	protected String getListBillingDialog() { return QUOTE_LIST_BILLING_DIALOG; }
  	@Override
  	protected String getListULBillingDialog() { return QUOTE_LIST_UL_BILLING_DIALOG; }
  	@Override
  	protected String getIndividualBillingDialog() { return QUOTE_INDIVIDUAL_BILLING_DIALOG; }
  	@Override
  	protected String getIndividualULBillingDialog() { return QUOTE_INDIVIDUAL_UL_BILLING_DIALOG; }
}
