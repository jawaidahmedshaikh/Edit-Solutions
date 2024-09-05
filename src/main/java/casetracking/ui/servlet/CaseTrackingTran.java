/*
 * User: sprasad
 * Date: Mar 17, 2005
 * Time: 3:41:20 PM
 *
 * (c) 2000-2008 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package casetracking.ui.servlet;

import casetracking.CaseRequirement;
import casetracking.CasetrackingLog;
import casetracking.CasetrackingNote;

import casetracking.usecase.CasetrackingUseCase;
import casetracking.usecase.CasetrackingUseCaseImpl;

import client.ClientDetail;

import contract.*;

import edit.common.EDITBigDecimal;
import edit.common.EDITDate;
import edit.common.CodeTableWrapper;
import edit.common.exceptions.EDITEventException;

import edit.common.vo.*;

import edit.portal.common.session.UserSession;
import edit.portal.common.transactions.Transaction;

import edit.portal.widget.*;

import edit.portal.widgettoolkit.DoubleTableModel;
import edit.portal.widgettoolkit.TableModel;
import edit.portal.widgettoolkit.TableRow;
import edit.portal.exceptions.*;

import edit.services.db.hibernate.SessionHelper;

import engine.ProductStructure;

import fission.global.AppReqBlock;

import fission.utility.*;

import java.util.*;

import event.Charge;
import event.EDITTrx;
import role.*;



public class CaseTrackingTran extends Transaction
{
    public static String CASETRACKING_OPTION_LUMP_SUM_PROCEEDS = "LumpSumProceeds";
    public static String CASETRACKING_OPTION_SUPPLEMENTAL_CONTRACT = "SupplementalContract";
    public static String CASETRACKING_OPTION_SPOUSAL_CONTINUATION = "SpousalContinuation";
    public static String CASETRACKING_OPTIONS_OPEN_CLAIM = "OpenClaim";
    public static String CASETRACKING_OPTION_STRETCH_IRA = "StretchIRA";
    public static String CASETRACKING_OPTION_RIDER_CLAIM = "RiderClaim";
    private String SHOW_CASETRACKING_CLIENT = "showCasetrackingClient";
    private String SHOW_CASETRACKING_REQUIREMENT = "showCasetrackingRequirement";
    private String SHOW_CASETRACKING_LOG = "showCasetrackingLog";
    private String SHOW_CLIENT_SEARCH_DIALOG = "showClientSearchDialog";
    private String SHOW_NOTES_DIALOG = "showNotesDialog";
    private String SHOW_DEATHTRANSACTION_DIALOG = "showDeathTransactionDialog";
    private String SHOW_CONTRACT_DIALOG = "showContractDialog";
    private String SHOW_BENEFICIARIES_DIALOG = "showBeneficiariesDialog";
    private String SHOW_TRANSACTIONS_DIALOG = "showTransactionsDialog";
    private String SEARCH_CLIENTS = "searchClients";
    private String SHOW_CLIENT_AFTER_SEARCH = "showClientAfterSearch";
    private String SHOW_SELECTED_TRANSACTION = "showSelectedTransaction";
    private String SHOW_NOTE_DETAIL = "showNoteDetail";
    private String SAVE_OR_UPDATE_NOTE = "saveOrUpdateNote";
    private String ADD_NOTE = "addNote";
    private String CANCEL_NOTE = "cancelNote";
    private String DELETE_NOTE = "deleteNote";
    private String UPDATE_DEATH_TRAN_DOUBLE_TABLE = "updateDeathTranDoubleTable";
    private String SAVE_TRANSACTION_PAGE = "saveDeathTransactionPage";
    private String SEARCH_FOR_NEW_BENEFICIARIES = "searchForNewBeneficiaries";
    private String SHOW_BENEFICIARY_DETAIL = "showBeneficiaryDetail";
    private String SHOW_PROSPECTIVE_BENE_DETAIL = "showProspectiveBeneDetail";
    private String SAVE_BENEFICIARY_DETAILS = "saveBeneficiaryDetails";
    private String PERFORM_DEATH_QUOTE = "performDeathQuote";
    private String SAVE_LUMP_SUM_TRX = "saveLumpSumTrx";
    private String SAVE_SPOUSAL_CONTINUATION = "saveSpousalContinuation";
    private String SAVE_RIDER_CLAIM = "saveRiderClaim";
    private String PERFORM_PAYOUT_QUOTE = "performPayoutQuote";
    private String SAVE_TO_FUND_SELECTIONS = "saveToFundSelections";
    private String SAVE_SUPPLEMENTAL_CONTRACT_OPEN_CLAIM = "saveSupplementalContractOpenClaim";
    private String UPDATE_CASETRACKING_OPTION_DOUBLE_TABLE = "updateCasetrackingOptionDoubleTable";
    private String UPDATE_RIDER_DETAIL_DOUBLE_TABLE = "updateRiderDetailDoubleTable";
    private String SHOW_REQUIREMENT_DETAIL = "showRequirementDetail";
    private String ADD_CASE_REQUIREMENT = "addCaseRequirement";
    private String SAVE_CASE_REQUIREMENT = "saveCaseRequirement";
    private String CANCEL_CASE_REQUIREMENT = "cancelCaseRequirement";
    private String DELETE_CASE_REQUIREMENT = "deleteCaseRequirement";
    private String DELETE_WITHHOLDING_OVERRIDE = "deleteWithholdingOverride";
    private String SAVE_CLIENT_PROCESS = "saveClientProcess";
    private String SAVE_WITHHOLDING_OVERRIDE = "saveWithholdingOverride";
    private String SHOW_CONTRACT_DETAIL = "showContractDetail";
    private String SHOW_FUND_DETAILS = "showFundDetails";
    private String SHOW_WITHHOLDING_OVERRIDE_DIALOG = "showWithholdingOverrideDialog";
    private String SAVE_MANUAL_REQUIREMENT = "saveManualRequirement";
    private String SHOW_REQUIREMENT_DESCRIPTION = "showRequirementDescription";
    private String SHOW_STRETCH_IRA_BENE_DIALOG = "showStretchIRABeneDialog";
    private String SEARCH_FOR_NEW_STRETCH_IRA_BENEFICIARIES = "searchForNewStretchIRABeneficiaries";
    private String SHOW_STRETCH_IRA_BENEFICIARY_DETAIL = "showStretchIRABeneficiaryDetail";
    private String SHOW_PROSPECTIVE_STRETCH_IRA_BENE_DETAIL = "showProspectiveStretchIRABeneDetail";
    private String SAVE_STRETCH_IRA_BENEFICIARY_DETAILS = "saveStretchIRABeneficiaryDetails";
    private String SAVE_STRETCH_IRA = "saveStretchIRA";
    private String SHOW_CHARGE_OVERRIDES_DIALOG = "showChargeOverridesDialog";
    private String SHOW_CHARGE_DETAIL_SUMMARY = "showChargeDetailSummary";
    private String UPDATE_CHARGE_OVERRIDE = "updateChargeOverride";
    private String DELETE_CHARGE_OVERRIDE = "deleteChargeOverride";
    private String CASETRACKING_CLIENT = "/casetracking/jsp/casetrackingClientDetail.jsp";
    private String CASETRACKING_REQUIREMENT = "/casetracking/jsp/casetrackingRequirement.jsp";
    private String CASETRACKING_LOG = "/casetracking/jsp/casetrackingLog.jsp";
    private String CASETRACKING_SEARCH_DIALOG = "/casetracking/jsp/casetrackingClientSearchDialog.jsp";
    private String CASETRACKING_NOTES_DIALOG = "/casetracking/jsp/casetrackingNotesDialog.jsp";
    private String CASETRACKING_DEATHTRANSACTIONS_DIALOG = "/casetracking/jsp/casetrackingDeathTransactionsDialog.jsp";
    private String CASETRACKING_CONTRACT_DIALOG = "/casetracking/jsp/casetrackingContractDialog.jsp";
    private String CASETRACKING_BENEFICIARIES_DIALOG = "/casetracking/jsp/casetrackingBeneficiariesDialog.jsp";
    private String CASETRACKING_TRANSACTIONS_DIALOG = "/casetracking/jsp/casetrackingTransactionsDialog.jsp";
    private String CASETRACKING_LUMP_SUM_PROCEEDS_DIALOG = "/casetracking/jsp/casetrackingLumpSumProceedsDialog.jsp";
    private String CASETRACKING_SUPPLEMENTAL_CONTRACT_DIALOG = "/casetracking/jsp/casetrackingSupplementalContractDialog.jsp";
    private String CASETRACKING_SPOUSAL_CONTINUATION_DIALOG = "/casetracking/jsp/casetrackingSpousalContinuationDialog.jsp";
    private String CASETRACKING_STRETCH_IRA_DIALOG = "/casetracking/jsp/casetrackingStretchIRADialog.jsp";
    private String CASETRACKING_STRETCH_IRA_BENE_DIALOG = "/casetracking/jsp/casetrackingStretchIRABeneDialog.jsp";
    private String CASETRACKING_WITHHOLDING_OVERRIDES_DIALOG = "/casetracking/jsp/casetrackingWithholdingOverrides.jsp";
    private String GET_TEST = "getTest";
    private String CASETRACKING_MANUAL_REQUIREMENT_DIALOG = "/casetracking/jsp/casetrackingManualRequirementDialog.jsp";
    private String CASETRACKING_CHARGE_OVERRIDES_DIALOG = "/casetracking/jsp/casetrackingChargeOverridesDialog.jsp";
    private String CASETRACKING_RIDER_CLAIM_DIALOG = "/casetracking/jsp/casetrackingRiderClaimDialog.jsp";

    public String execute(AppReqBlock appReqBlock) throws Exception
    {
        String action = appReqBlock.getReqParm("action");

        String returnPage = null;

        if (action.equals(SHOW_CASETRACKING_CLIENT))
        {
            returnPage = showCasetrackingClient(appReqBlock);
        }
        else if (action.equals(SHOW_CASETRACKING_REQUIREMENT))
        {
            returnPage = showCasetrackingRequirement(appReqBlock);
        }
        else if (action.equals(SHOW_CASETRACKING_LOG))
        {
            returnPage = showCasetrackingLog(appReqBlock);
        }
        else if (action.equals(SHOW_CLIENT_SEARCH_DIALOG))
        {
            returnPage = showClientSearchDialog(appReqBlock);
        }
        else if (action.equals(SHOW_NOTES_DIALOG))
        {
            returnPage = showNotesDialog(appReqBlock);
        }
        else if (action.equals(SHOW_DEATHTRANSACTION_DIALOG))
        {
            returnPage = showDeathTransactionDialog(appReqBlock);
        }
        else if (action.equals(SHOW_CONTRACT_DIALOG))
        {
            returnPage = showContractDialog(appReqBlock);
        }
        else if (action.equals(SHOW_BENEFICIARIES_DIALOG))
        {
            returnPage = showBeneficiariesDialog(appReqBlock);
        }
        else if (action.equals(SHOW_TRANSACTIONS_DIALOG))
        {
            returnPage = showTransactionsDialog(appReqBlock);
        }
        else if (action.equals(SEARCH_CLIENTS))
        {
            returnPage = searchClients(appReqBlock);
        }
        else if (action.equals(SHOW_CLIENT_AFTER_SEARCH))
        {
            returnPage = showClientAfterSearch(appReqBlock);
        }
        else if (action.equals(SHOW_SELECTED_TRANSACTION))
        {
            returnPage = showSelectedTransaction(appReqBlock);
        }
        else if (action.equals(SAVE_OR_UPDATE_NOTE))
        {
            returnPage = saveOrUpdateNote(appReqBlock);
        }
        else if (action.equals(SHOW_NOTE_DETAIL))
        {
            returnPage = showNoteDetail(appReqBlock);
        }
        else if (action.equals(ADD_NOTE))
        {
            returnPage = addNote(appReqBlock);
        }
        else if (action.equals(CANCEL_NOTE))
        {
            returnPage = cancelNote(appReqBlock);
        }
        else if (action.equals(DELETE_NOTE))
        {
            returnPage = deleteNote(appReqBlock);
        }
        else if (action.equals(UPDATE_DEATH_TRAN_DOUBLE_TABLE))
        {
            returnPage = updateDeathTranDoubleTable(appReqBlock);
        }
        else if (action.equals(SAVE_TRANSACTION_PAGE))
        {
            returnPage = saveDeathTransactionPage(appReqBlock);
        }
        else if (action.equals(SEARCH_FOR_NEW_BENEFICIARIES))
        {
            returnPage = searchForNewBeneficiaries(appReqBlock);
        }
        else if (action.equals(SHOW_BENEFICIARY_DETAIL))
        {
            returnPage = showBeneficiaryDetail(appReqBlock);
        }
        else if (action.equals(SHOW_PROSPECTIVE_BENE_DETAIL))
        {
            returnPage = showProspectiveBeneDetail(appReqBlock);
        }
        else if (action.equals(UPDATE_CASETRACKING_OPTION_DOUBLE_TABLE))
        {
            returnPage = updateCasetrackingOptionDoubleTable(appReqBlock);
        }
        else if (action.equals(UPDATE_RIDER_DETAIL_DOUBLE_TABLE))
        {
            returnPage = updateRiderDetailDoubleTable(appReqBlock);
        }
        else if (action.equals(SAVE_BENEFICIARY_DETAILS))
        {
            returnPage = saveBeneficiaryDetails(appReqBlock);
        }
        else if (action.equals(PERFORM_DEATH_QUOTE))
        {
            returnPage = performDeathQuote(appReqBlock);
        }
        else if (action.equals(SAVE_TO_FUND_SELECTIONS))
        {
            returnPage = saveToFundSelections(appReqBlock);
        }
        else if (action.equals(SAVE_LUMP_SUM_TRX))
        {
            returnPage = saveLumpSumTrx(appReqBlock);
        }
        else if (action.equals(SAVE_SPOUSAL_CONTINUATION))
        {
            returnPage = saveSpousalContinuation(appReqBlock);
        }
        else if (action.equalsIgnoreCase(SAVE_RIDER_CLAIM))
        {
            returnPage = saveRiderClaim(appReqBlock);
        }
        else if (action.equals(PERFORM_PAYOUT_QUOTE))
        {
            returnPage = performPayoutQuote(appReqBlock);
        }
        else if (action.equals(SAVE_SUPPLEMENTAL_CONTRACT_OPEN_CLAIM))
        {
            returnPage = saveSupplementalContractOrOpenClaim(appReqBlock);
        }
        else if (action.equals(SHOW_REQUIREMENT_DETAIL))
        {
            returnPage = showRequirementDetail(appReqBlock);
        }
        else if (action.equals(ADD_CASE_REQUIREMENT))
        {
            returnPage = addCaseRequirement(appReqBlock);
        }
        else if (action.equals(SAVE_CASE_REQUIREMENT))
        {
            returnPage = saveCaseRequirement(appReqBlock);
        }
        else if (action.equals(CANCEL_CASE_REQUIREMENT))
        {
            returnPage = cancelCaseRequirement(appReqBlock);
        }
        else if (action.equals(DELETE_CASE_REQUIREMENT))
        {
            returnPage = deleteCaseRequirement(appReqBlock);
        }
        else if (action.equals(GET_TEST))
        {
            return getTest(appReqBlock);
        }
        else if (action.equals(SHOW_FUND_DETAILS))
        {
            return showFundDetails(appReqBlock);
        }
        else if (action.equals(SAVE_CLIENT_PROCESS))
        {
            return saveClientProcess(appReqBlock);
        }
        else if (action.equals(SHOW_CONTRACT_DETAIL))
        {
            return showContractDetail(appReqBlock);
        }
        else if (action.equals(SHOW_WITHHOLDING_OVERRIDE_DIALOG))
        {
            return showWithholdingOverrideDialog(appReqBlock);
        }
        else if (action.equals(SAVE_WITHHOLDING_OVERRIDE))
        {
            return saveWithholdingOverride(appReqBlock);
        }
        else if (action.equals(DELETE_WITHHOLDING_OVERRIDE))
        {
            return deleteWithholdingOverride(appReqBlock);
        }
        else if (action.equals(ADD_CASE_REQUIREMENT))
        {
            return addCaseRequirement(appReqBlock);
        }
        else if (action.equals(SAVE_MANUAL_REQUIREMENT))
        {
            return saveManualRequirement(appReqBlock);
        }
        else if (action.equals(SHOW_REQUIREMENT_DESCRIPTION))
        {
            return showRequirementDescription(appReqBlock);
        }
        else if (action.equals(SHOW_STRETCH_IRA_BENE_DIALOG))
        {
            return showStretchIRABeneDialog(appReqBlock);
        }
        else if (action.equals(SEARCH_FOR_NEW_STRETCH_IRA_BENEFICIARIES))
        {
            return searchForNewStretchIRABeneficiaries(appReqBlock);
        }
        else if (action.equals(SHOW_STRETCH_IRA_BENEFICIARY_DETAIL))
        {
            return showStretchIRABeneficiaryDetail(appReqBlock);
        }
        else if (action.equals(SHOW_PROSPECTIVE_STRETCH_IRA_BENE_DETAIL))
        {
            return showProspectiveStretchIRABeneDetail(appReqBlock);
        }
        else if (action.equals(SAVE_STRETCH_IRA_BENEFICIARY_DETAILS))
        {
            return saveStretchIRABeneficiaryDetails(appReqBlock);
        }
        else if (action.equals(SAVE_STRETCH_IRA))
        {
            return saveStretchIRA(appReqBlock);
        }
        else if (action.equals(SHOW_CHARGE_OVERRIDES_DIALOG))
        {
            return showChargeOverridesDialog(appReqBlock);
        }
        else if (action.equals(SHOW_CHARGE_DETAIL_SUMMARY))
        {
            return showChargeDetailSummary(appReqBlock);
        }
        else if (action.equals(UPDATE_CHARGE_OVERRIDE))
        {
            return updateChargeOverride(appReqBlock);
        }
        else if (action.equals(DELETE_CHARGE_OVERRIDE))
        {
            return deleteChargeOverride(appReqBlock);
        }
         else
        {
            String transaction = appReqBlock.getReqParm("transaction");

            throw new RuntimeException("Unrecognized Transaction/Action [" + transaction + " / " + action + "]");
        }

        return returnPage;
    }


    private String getTest(AppReqBlock appReqBlock)
    {
        return "/test.jsp";
    }

    /**
     * Displays casetracking Client Detail Page.
     * @param appReqBlock
     * @return
     */
    private String showCasetrackingClient(AppReqBlock appReqBlock)
    {
        Long clientDetailPK = (Long) appReqBlock.getHttpSession().getAttribute("casetracking.selectedClientDetailPK");

        ClientDetail clientDetail = null;

        if (clientDetailPK != null)
        {
            clientDetail = ClientDetail.findByPK(clientDetailPK);
        }

        new ClientDetailTableModel(clientDetail, appReqBlock);

        new ContractClientDoubleTableModel(clientDetail, appReqBlock, null).removeDoubleTableModelFromSessionScope();

        //can not use this in 'showContractDialog' method that is used while showing the contractclient page because
        // after quote calculations are performed ('performDeathQuote' method) 'showContractDialog' method is called
        // which is clearing out the following session attribute.
        appReqBlock.getHttpSession().setAttribute("caseTrackingQuoteVO", null);

        return CASETRACKING_CLIENT;
    }

    /**
     * Displays Case Tracking Requirement page.
     * @param appReqBlock
     * @return
     */
    private String showCasetrackingRequirement(AppReqBlock appReqBlock)
    {
        String process = appReqBlock.getReqParm("caseTrackingProcess");

        if (process == null)
        {
            process = (String) appReqBlock.getHttpSession().getAttribute("casetracking.process");
        }

        ProductStructure productStructure = ProductStructure.findByNames(process, "*", "*", "*", "*");

        Long clientDetailPK = (Long) appReqBlock.getHttpSession().getAttribute("casetracking.selectedClientDetailPK");

        ClientDetail clientDetail = null;

        if (clientDetailPK != null)
        {
            clientDetail = ClientDetail.findByPK(clientDetailPK);
        }

        new RequirementsTableModel(productStructure, clientDetail, appReqBlock);

        appReqBlock.getHttpSession().setAttribute("casetracking.process", process);

        return CASETRACKING_REQUIREMENT;
    }

    /**
     * Displays Casetracking Log page.
     * @param appReqBlock
     * @return
     */
    private String showCasetrackingLog(AppReqBlock appReqBlock)
    {
        Long clientDetailPK = (Long) appReqBlock.getHttpSession().getAttribute("casetracking.selectedClientDetailPK");

        ClientDetail clientDetail = null;

        if (clientDetailPK != null)
        {
            clientDetail = ClientDetail.findByPK(clientDetailPK);
        }

        new CasetrackingLogTableModel(clientDetail, appReqBlock);

        return CASETRACKING_LOG;
    }

    /**
     * Displays Client Search Dialog.
     * @param appReqBlock
     * @return
     */
    private String showClientSearchDialog(AppReqBlock appReqBlock)
    {
        new ClientSearchTableModel(appReqBlock);

        return CASETRACKING_SEARCH_DIALOG;
    }

    /**
     * Displays Case Tracking Notes Dialog.
     * @param appReqBlock
     * @return
     */
    private String showNotesDialog(AppReqBlock appReqBlock)
    {
        Long clientDetailPK = (Long) appReqBlock.getHttpSession().getAttribute("casetracking.selectedClientDetailPK");

        new CasetrackingNoteTableModel(clientDetailPK, appReqBlock);

        return CASETRACKING_NOTES_DIALOG;
    }

    /**
     * Dispalys Casetracking Beneficiaries Dialog.
     * @param appReqBlock
     * @return
     */
    private String showBeneficiariesDialog(AppReqBlock appReqBlock)
    {
        String segmentPK = appReqBlock.getReqParm("segmentPK");

        Segment segment = Segment.findByPK(new Long(segmentPK));

        ExistingBeneficiariesTableModel existingBeneTableModel = new ExistingBeneficiariesTableModel(segment, appReqBlock);

        //determine if total allocation percent is valid
        existingBeneTableModel.editAllocations();

        new ProspectiveBeneficiariesTableModel(appReqBlock);

        appReqBlock.getHttpServletRequest().setAttribute("segmentPK", segmentPK);
        appReqBlock.getHttpServletRequest().setAttribute("segment", segment);

        appReqBlock.getHttpSession().setAttribute("caseTrackingQuoteVO", null);

        return CASETRACKING_BENEFICIARIES_DIALOG;
    }

    /**
     * Displays Castracking Transaction Dialog.
     * @param appReqBlock
     * @return
     */
    private String showTransactionsDialog(AppReqBlock appReqBlock)
    {
        String segmentPK = appReqBlock.getReqParm("segmentPK");

        Segment segment = Segment.findByPK(new Long(segmentPK));

        appReqBlock.getHttpServletRequest().setAttribute("segmentPK", segmentPK);
        appReqBlock.getHttpSession().setAttribute("caseTrackingQuoteVO", null);

        new ContractDetailDoubleTableModel(segment, appReqBlock).removeDoubleTableModelFromSessionScope();
        new RiderDetailDoubleTableModel(segment, appReqBlock).removeDoubleTableModelFromSessionScope();

        new FundTableModel(appReqBlock).clearFundMap();

        return CASETRACKING_TRANSACTIONS_DIALOG;
    }

    /**
     * Displays Death Transaction Dialog.
     * @param appReqBlock
     * @return
     */
    private String showDeathTransactionDialog(AppReqBlock appReqBlock)
    {
        Long clientDetailPK = (Long) appReqBlock.getHttpSession().getAttribute("casetracking.selectedClientDetailPK");

        ClientDetail clientDetail = ClientDetail.findByPK(clientDetailPK);

        new ContractClientDoubleTableModel(clientDetail, appReqBlock, "Death");

        return CASETRACKING_DEATHTRANSACTIONS_DIALOG;
    }

    /**
     * Dispalys Case Tracking Contract Dialog Page.
     * @param appReqBlock
     * @return
     */
    private String showContractDialog(AppReqBlock appReqBlock)
    {
        Long clientDetailPK = (Long) appReqBlock.getHttpSession().getAttribute("casetracking.selectedClientDetailPK");

        ClientDetail clientDetail = ClientDetail.findByPK(clientDetailPK);

        String segmentPK = new ClientDetailTableModel(clientDetail, appReqBlock).getSelectedRowId();

        // when the page flow is coming from beneficiaries dialog.
        if (segmentPK == null)
        {
            segmentPK = appReqBlock.getReqParm("segmentPK");
        }

        Segment segment = Segment.findByPK(new Long(segmentPK));

        new ContractDialogTableModel(segment, appReqBlock);

        String caseTrackingProcess = appReqBlock.getReqParm("caseTrackingProcess");

        // when summary area in contract dialog is selected then there there is no caseTrackingProcess hence it will be null,
        // do not set the value when caseTrackingProcess is null.
        if (caseTrackingProcess != null)
        {
            appReqBlock.getHttpSession().setAttribute("casetracking.process", caseTrackingProcess);
        }

        appReqBlock.getHttpServletRequest().setAttribute("segmentPK", segmentPK);

        return CASETRACKING_CONTRACT_DIALOG;
    }

    /**
     * Displays Search Results.
     * @param appReqBlock
     * @return
     */
    private String searchClients(AppReqBlock appReqBlock)
    {
        new ClientSearchTableModel(appReqBlock);

        return CASETRACKING_SEARCH_DIALOG;
    }

    /**
     * Populates Client Information with the Client selected from Search Results.
     * @param appReqBlock
     * @return
     */
    private String showClientAfterSearch(AppReqBlock appReqBlock)
    {
        String clientDetailPK = appReqBlock.getReqParm("clientDetailPK");

        appReqBlock.getHttpSession().setAttribute("casetracking.selectedClientDetailPK", new Long(clientDetailPK));

        ClientDetail clientDetail = ClientDetail.findByPK(new Long(clientDetailPK));

        new ContractClientDoubleTableModel(clientDetail, appReqBlock, null).removeDoubleTableModelFromSessionScope();

        appReqBlock.getHttpSession().setAttribute("casetracking.process", null);

        return showCasetrackingClient(appReqBlock);
    }

    /**
     * Displays LumpSum Proceeds or Supplemental Contract/ Open Claim or Spousal Continuation Dialog up on the selection
     * of process in the Transaction Dialog page.
     * @param appReqBlock
     * @return
     */
    private String showSelectedTransaction(AppReqBlock appReqBlock)
    {
        String returnPage = null;

        String casetrackingOption = appReqBlock.getReqParm("casetrackingOption");

        String segmentPK = appReqBlock.getReqParm("segmentPK");

        Segment segment = Segment.findByPK(new Long(segmentPK));

        new ContractDetailDoubleTableModel(segment, appReqBlock);

        if (CASETRACKING_OPTION_LUMP_SUM_PROCEEDS.equalsIgnoreCase(casetrackingOption))
        {
            returnPage = showLumpSumTransaction(appReqBlock);
        }
        else if (CASETRACKING_OPTION_SUPPLEMENTAL_CONTRACT.equalsIgnoreCase(casetrackingOption))
        {
            returnPage = showSupplementalContract(appReqBlock);
        }
        else if (CASETRACKING_OPTION_SPOUSAL_CONTINUATION.equalsIgnoreCase(casetrackingOption))
        {
            returnPage = CASETRACKING_SPOUSAL_CONTINUATION_DIALOG;
        }
        else if (CASETRACKING_OPTIONS_OPEN_CLAIM.equalsIgnoreCase(casetrackingOption))
        {
            returnPage = showSupplementalContract(appReqBlock);
        }
        else if (CASETRACKING_OPTION_STRETCH_IRA.equalsIgnoreCase(casetrackingOption))
        {
            returnPage = showStretchIRA(appReqBlock);
        }
        else if (CASETRACKING_OPTION_RIDER_CLAIM.equalsIgnoreCase(casetrackingOption))
        {
            returnPage = showRiderClaim(appReqBlock);
        }

        appReqBlock.getHttpServletRequest().setAttribute("segmentPK", segmentPK);
        appReqBlock.getHttpServletRequest().setAttribute("casetrackingOption", casetrackingOption);

        return returnPage;
    }

    private String showStretchIRA(AppReqBlock appReqBlock)
    {

        String segmentPK = Util.initString(appReqBlock.getReqParm("segmentPK"), "");
        String casetrackingOption = Util.initString(appReqBlock.getReqParm("casetrackingOption"), "");

//        String newPolicyIndStatus = Util.initString(appReqBlock.getReqParm("newPolicyIndStatus"), "");
//        String newPolicyNumber = Util.initString(appReqBlock.getReqParm("newPolicyNumber"), "");
//        String beneficiaryStatus = Util.initString(appReqBlock.getReqParm("beneficiaryStatus"), "");
//        String frequencyCT = Util.initString(appReqBlock.getReqParm("frequencyCT"), "");
//        String rmdStartMonth = Util.initString(appReqBlock.getReqParm("rmdStartMonth"), "");
//        String rmdStartDay = Util.initString(appReqBlock.getReqParm("rmdStartDay"), "");
//        String rmdStartYear = Util.initString(appReqBlock.getReqParm("rmdStartYear"), "");
//        String printLine1 = Util.initString(appReqBlock.getReqParm("printLine"), "");
//        String printLine2 = Util.initString(appReqBlock.getReqParm("printLine2"), "");
//
        appReqBlock.getHttpServletRequest().setAttribute("segmentPK", segmentPK);
        appReqBlock.getHttpServletRequest().setAttribute("castrackingOption", casetrackingOption);
//        appReqBlock.getHttpServletRequest().setAttribute("newPolicyIndStatus", newPolicyIndStatus);
//        appReqBlock.getHttpServletRequest().setAttribute("newPolicyNumber", newPolicyNumber);
//
//        if (Util.isANumber(beneficiaryStatus))
//        {
//            beneficiaryStatus = codeTableWrapper.getCodeTableEntry(Long.parseLong(beneficiaryStatus)).getCode();
//            appReqBlock.getHttpServletRequest().setAttribute("beneficiaryStatus", beneficiaryStatus);
//        }
//
//        if (Util.isANumber(frequencyCT))
//        {
//            frequencyCT = codeTableWrapper.getCodeTableEntry(Long.parseLong(frequencyCT)).getCode();
//            appReqBlock.getHttpServletRequest().setAttribute("frequencyCT", frequencyCT);
//        }
//
//        appReqBlock.getHttpServletRequest().setAttribute("rmdStartMonth", rmdStartMonth);
//        appReqBlock.getHttpServletRequest().setAttribute("rmdStartDay", rmdStartDay);
//        appReqBlock.getHttpServletRequest().setAttribute("rmdStartYear", rmdStartYear);
//        appReqBlock.getHttpServletRequest().setAttribute("printLine1", printLine1);
//        appReqBlock.getHttpServletRequest().setAttribute("printLine2", printLine2);

        return CASETRACKING_STRETCH_IRA_DIALOG;
    }

    private String showRiderClaim(AppReqBlock appReqBlock)
    {
        String segmentPK = Util.initString(appReqBlock.getReqParm("segmentPK"), "");
        String casetrackingOption = Util.initString(appReqBlock.getReqParm("casetrackingOption"), "");

        appReqBlock.getHttpServletRequest().setAttribute("segmentPK", segmentPK);
        appReqBlock.getHttpServletRequest().setAttribute("castrackingOption", casetrackingOption);

        Segment segment = Segment.findByPK(new Long(segmentPK));
        if (segment.getSegmentFK() != null)
        {
            segment = Segment.findByPK(segment.getSegmentFK());
        }
        
        new RiderDetailDoubleTableModel(segment, appReqBlock);

        return CASETRACKING_RIDER_CLAIM_DIALOG;
    }

    /**
     * Displays Casetracking Note Details upon the selection of the note in the summary area.
     * @param appReqBlock
     * @return
     */
    private String showNoteDetail(AppReqBlock appReqBlock)
    {
        Long clientDetailPK = (Long) appReqBlock.getHttpSession().getAttribute("casetracking.selectedClientDetailPK");

        String casetrackingNotePK = new CasetrackingNoteTableModel(clientDetailPK, appReqBlock).getSelectedRowId();

        CasetrackingNote casetrackingNote = CasetrackingNote.findByPK(new Long(casetrackingNotePK));

        appReqBlock.getHttpServletRequest().setAttribute("activeCasetrackingNote", casetrackingNote);

        return showNotesDialog(appReqBlock);
    }

    /**
     * Saves new note or Updates existing note.
     * @param appReqBlock
     * @return
     */
    private String saveOrUpdateNote(AppReqBlock appReqBlock)
    {
        CasetrackingNote casetrackingNote = (CasetrackingNote) Util.mapFormDataToHibernateEntity(appReqBlock.getHttpServletRequest(),
                                                                                                 CasetrackingNote.class,
                                                                                                 SessionHelper.EDITSOLUTIONS, true);

        Long clientDetailPK = (Long) appReqBlock.getHttpSession().getAttribute("casetracking.selectedClientDetailPK");

        UserSession userSession = appReqBlock.getUserSession();

        casetrackingNote.setOperator(userSession.getUsername());

        CasetrackingUseCase casetrackingNoteComponent = new CasetrackingUseCaseImpl();

        casetrackingNoteComponent.saveOrUpdateNote(casetrackingNote, clientDetailPK);

        new CasetrackingNoteTableModel(clientDetailPK, appReqBlock).resetAllRows();

        return showNotesDialog(appReqBlock);
    }

    /**
     * Allows to enter new Note information. Gets the Next Sequence number and displays it.
     * @param appReqBlock
     * @return
     */
    private String addNote(AppReqBlock appReqBlock)
    {
        Long clientDetailPK = (Long) appReqBlock.getFromSessionScope("casetracking.selectedClientDetailPK");

        ClientDetail clientDetail = ClientDetail.findByPK(clientDetailPK);

        int nextSequenceNumber = clientDetail.getNextSequenceNumberForNote();

        CasetrackingNote casetrackingNote = new CasetrackingNote();

        casetrackingNote.setSequence(nextSequenceNumber);

        appReqBlock.getHttpServletRequest().setAttribute("activeCasetrackingNote", casetrackingNote);

        new CasetrackingNoteTableModel(clientDetailPK, appReqBlock).resetAllRows();

        return showNotesDialog(appReqBlock);
    }

    /**
     * Unselects the Case Tracking Note.
     * @param appReqBlock
     * @return
     */
    private String cancelNote(AppReqBlock appReqBlock)
    {
        Long clientDetailPK = (Long) appReqBlock.getHttpSession().getAttribute("casetracking.selectedClientDetailPK");

        new CasetrackingNoteTableModel(clientDetailPK, appReqBlock).resetAllRows();

        return showNotesDialog(appReqBlock);
    }

    /**
     * Deletes selected Case Tracking Note.
     * @param appReqBlock
     * @return
     */
    private String deleteNote(AppReqBlock appReqBlock)
    {
        Long clientDetailPK = (Long) appReqBlock.getHttpSession().getAttribute("casetracking.selectedClientDetailPK");

        String casetrackingNotePK = new CasetrackingNoteTableModel(clientDetailPK, appReqBlock).getSelectedRowId();

        CasetrackingUseCase casetrackingNoteComponent = new CasetrackingUseCaseImpl();

        casetrackingNoteComponent.deleteNote(new Long(casetrackingNotePK));

        new CasetrackingNoteTableModel(clientDetailPK, appReqBlock).resetAllRows();

        return showNotesDialog(appReqBlock);
    }

    /**
     * Updates the DoubleTable displayed on Death Transaction page i.e. moves selected entries from one table to the other.
     * @param appReqBlock
     * @return
     */
    private String updateDeathTranDoubleTable(AppReqBlock appReqBlock)
    {
        Long clientDetailPK = (Long) appReqBlock.getHttpSession().getAttribute("casetracking.selectedClientDetailPK");

        ClientDetail clientDetail = ClientDetail.findByPK(clientDetailPK);

        new ContractClientDoubleTableModel(clientDetail, appReqBlock, "Death").updateState();

        return showDeathTransactionDialog(appReqBlock);
    }

    /**
     * Update the selection of the CasetrackingProcess field on the clientDetail.
     * @param appReqBlock
     * @return
     */
    private String saveClientProcess(AppReqBlock appReqBlock)
    {
        CasetrackingUseCase casetrackingComponent = new CasetrackingUseCaseImpl();

        ClientDetail clientDetail = (ClientDetail) Util.mapFormDataToHibernateEntity(appReqBlock.getHttpServletRequest(), ClientDetail.class, SessionHelper.EDITSOLUTIONS, true);

        appReqBlock.getHttpSession().setAttribute("casetracking.process", clientDetail.getCaseTrackingProcess());

        casetrackingComponent.updateClientProcess(clientDetail);

        return showClientAfterSearch(appReqBlock);
    }

    /**
     * From the death transaction page, 1) client info for death fields can be updated
     * 2) process the trx selected.  For each contractClient selected, perform
     * the trx processing for the transaction selection made.
     * @param appReqBlock
     * @return
     */
    private String saveDeathTransactionPage(AppReqBlock appReqBlock) throws Exception
    {
        CasetrackingUseCase casetrackingComponent = new CasetrackingUseCaseImpl();

        ClientDetail clientDetail = (ClientDetail) Util.mapFormDataToHibernateEntity(appReqBlock.getHttpServletRequest(), ClientDetail.class, SessionHelper.EDITSOLUTIONS, true);
        String transactionType = Util.initString(appReqBlock.getReqParm("transactionType"), "");
        String condition = Util.initString(appReqBlock.getReqParm("conditionCT"), null);

        DoubleTableModel model = new ContractClientDoubleTableModel(clientDetail, appReqBlock, "Death");

        TableModel tableModel = (ContractClientTableModel) model.getTableModel(DoubleTableModel.TO_TABLEMODEL);

        int count = tableModel.getRowCount();

        for (int i = 0; i < count; i++)
        {
            //first save the clientDetail and only save once
            if (i == 0)
            {
                clientDetail = casetrackingComponent.updateClientDeathStatus(clientDetail, transactionType);
            }

            TableRow tableRow = (ContractClientTableRow) tableModel.getRow(i);

            String contractClientPK = tableRow.getRowId();

            String contractNumber = (String) tableRow.getCellValue(ContractClientDoubleTableModel.COLUMN_CONTRACT_NUMBER);
            EDITBigDecimal allocationPct = new EDITBigDecimal("0");
            CasetrackingLog casetrackingLog = setupLogEntry(appReqBlock, transactionType, allocationPct);
            casetrackingLog.setContractNumber(contractNumber);

            try
            {
                casetrackingComponent.deathTransactionProcess(casetrackingLog, new Long(contractClientPK), condition);
            }
            catch (Exception e)
            {
                System.out.println(e);

                e.printStackTrace();
  
                throw (e);
            }
        }

        // need to call evict method because need to remove the clientDetail from the hibernate session.
        // otherwise the entered fields are not cleared even when nothing is selected from the DoubleTableModel.
//        SessionHelper.evict(clientDetail, SessionHelper.EDITSOLUTIONS); GF - Do SessionHelper changes fix this?

        new ContractClientDoubleTableModel(clientDetail, appReqBlock, "Death").removeDoubleTableModelFromSessionScope();

        return showDeathTransactionDialog(appReqBlock);
    }

    /**
     * Returns search results for the criteria entered on beneficiaries page and displays them.
     * @param appReqBlock
     * @return
     */
    private String searchForNewBeneficiaries(AppReqBlock appReqBlock)
    {
        new ProspectiveBeneficiariesTableModel(appReqBlock);

        String segmentPK = appReqBlock.getReqParm("segmentPK");

        Segment segment = Segment.findByPK(new Long(segmentPK));

        new ExistingBeneficiariesTableModel(segment, appReqBlock).resetAllRows();

        return showBeneficiariesDialog(appReqBlock);
    }

    /**
     * Returns search results for the criteria entered on Stretch IRA beneficiaries page and displays them.
     * @param appReqBlock
     * @return
     */
    private String searchForNewStretchIRABeneficiaries(AppReqBlock appReqBlock)
    {
        new ProspectiveBeneficiariesTableModel(appReqBlock);

        String segmentPK = appReqBlock.getReqParm("segmentPK");

        Segment segment = Segment.findByPK(new Long(segmentPK));

        new ExistingBeneficiariesTableModel(segment, appReqBlock).resetAllRows();

        return showStretchIRABeneDialog(appReqBlock);
    }

    /**
     * Displays the details of the Beneficiary selected on beneficiaries page.
     * @param appReqBlock
     * @return
     */
    private String showBeneficiaryDetail(AppReqBlock appReqBlock)
    {
        String segmentPK = appReqBlock.getReqParm("segmentPK");

        Segment segment = Segment.findByPK(new Long(segmentPK));

        String selectedBeneficiaryPK = new ExistingBeneficiariesTableModel(segment, appReqBlock).getSelectedRowId();

        ContractClient contractClient = ContractClient.findByPK(new Long(selectedBeneficiaryPK));

        appReqBlock.getHttpServletRequest().setAttribute("selectedBene", contractClient);

        return showBeneficiariesDialog(appReqBlock);
    }

    /**
     * Displays the details of Client selected in the search results. (Case Tracking Beneficiaries dialog)
     * @param appReqBlock
     * @return
     */
    private String showProspectiveBeneDetail(AppReqBlock appReqBlock)
    {
        String selectedProspectiveBenePK = new ProspectiveBeneficiariesTableModel(appReqBlock).getSelectedRowId();

        ClientDetail clientDetail = ClientDetail.findByPK(new Long(selectedProspectiveBenePK));

        appReqBlock.getHttpServletRequest().setAttribute("selectedProspectiveBene", clientDetail);

        String segmentPK = appReqBlock.getReqParm("segmentPK");

        Segment segment = Segment.findByPK(new Long(segmentPK));

        new ExistingBeneficiariesTableModel(segment, appReqBlock).resetAllRows();

        return showBeneficiariesDialog(appReqBlock);
    }

    /**
     * New or Existing Beneficiary details will be saved.
     * @param appReqBlock
     * @return
     */
    private String saveBeneficiaryDetails(AppReqBlock appReqBlock)
    {
        String selectedBenePK = Util.initString(appReqBlock.getReqParm("selectedBenePK"), "0");
        String prospectiveBeneClientPK = Util.initString(appReqBlock.getReqParm("selectedProspectiveBenePK"), "0");
        String beneRole = Util.initString(appReqBlock.getReqParm("beneficiaryRole"), null);
        String allocationPct = Util.initString(appReqBlock.getReqParm("allocationPercent"), "0");
        String allocationAmount = Util.initString(appReqBlock.getReqParm("allocationAmount"), "0");
        String splitEqualInd = Util.initString(appReqBlock.getReqParm("splitEqual"), "N");

        if (splitEqualInd.equalsIgnoreCase("on"))
        {
            splitEqualInd = "Y";
        }
        else
        {
            splitEqualInd = "N";
        }

        String segmentPK = (String) appReqBlock.getReqParm("segmentPK");

        CasetrackingLog casetrackingLog = setupLogEntry(appReqBlock, null, new EDITBigDecimal(allocationPct, 10));

        CasetrackingUseCase casetrackingComponent = new CasetrackingUseCaseImpl();

        if (selectedBenePK.equals("0"))
        {
            ContractClient contractClient = (ContractClient) Util.mapFormDataToHibernateEntity(appReqBlock.getHttpServletRequest(), ContractClient.class, SessionHelper.EDITSOLUTIONS, true);
            casetrackingComponent.createNewBeneficiary(new Long(prospectiveBeneClientPK), new Long(segmentPK), beneRole, contractClient, casetrackingLog, splitEqualInd, new EDITBigDecimal(allocationAmount));
        }
        else
        {
            String relationship = Util.initString(appReqBlock.getReqParm("relationshipToInsuredCT"), null);
            String termDate = DateTimeUtil.formatMMDDYYYYToYYYYMMDD(appReqBlock.getReqParm("terminationDate"));
            if (termDate == null)
            {
                termDate = EDITDate.DEFAULT_MAX_DATE;
            }

            EDITDate terminationDate = new EDITDate(termDate);
            String terminationReason = Util.initString(appReqBlock.getReqParm("terminationReasonCT"), null);

            casetrackingComponent.updateExistingBeneficiary(new Long(selectedBenePK), relationship, terminationDate, terminationReason, casetrackingLog, splitEqualInd, new EDITBigDecimal(allocationAmount));
        }

        return showBeneficiariesDialog(appReqBlock);
    }

    /**
     * Updates the DoubleTable displayed on LumpSum Proceeds or
     * @param appReqBlock
     * @return
     */
    private String updateCasetrackingOptionDoubleTable(AppReqBlock appReqBlock)
    {
        String segmentPK = appReqBlock.getReqParm("segmentPK");

        Segment segment = Segment.findByPK(new Long(segmentPK));

        new ContractDetailDoubleTableModel(segment, appReqBlock).updateState();

        return showSelectedTransaction(appReqBlock);
    }

    private String updateRiderDetailDoubleTable(AppReqBlock appReqBlock)
    {
        String segmentPK = appReqBlock.getReqParm("segmentPK");

        Segment segment = Segment.findByPK(new Long(segmentPK));

        new RiderDetailDoubleTableModel(segment, appReqBlock).updateState();

        return showSelectedTransaction(appReqBlock);
    }

    /**
     * Calculate the cash value of the policy for the date entered for the death selected
     * @param appReqBlock
     * @return
     */
    private String performDeathQuote(AppReqBlock appReqBlock)
    {
        CasetrackingUseCase casetrackingComponent = new CasetrackingUseCaseImpl();
        String disbursementDateString = DateTimeUtil.formatMMDDYYYYToYYYYMMDD(appReqBlock.getReqParm("disbursementDate"));

        EDITDate disbursementDate = null;

        if (disbursementDateString != null)
        {
            disbursementDate = new EDITDate(disbursementDateString);
        }

        EDITBigDecimal beneAllocationPct = new EDITBigDecimal(Util.initString(appReqBlock.getReqParm("beneAllocPercent"), "0"), 10);
        String segmentPK = appReqBlock.getReqParm("segmentPK");
        Segment segment = Segment.findByPK(new Long(segmentPK));

        //        ContractClient activeContractClient = (ContractClient) appReqBlock.getHttpSession().getAttribute("activeContractClient");
        //        Long clientDetailPK = (Long) appReqBlock.getHttpSession().getAttribute("casetracking.selectedClientDetailPK");

        CaseTrackingQuoteVO quoteVO = casetrackingComponent.performDeathQuote(disbursementDate, beneAllocationPct, new Long(segmentPK));

        appReqBlock.getHttpSession().setAttribute("caseTrackingQuoteVO", quoteVO);

        new ContractDialogTableModel(segment, appReqBlock).resetAllRows();

        return showContractDialog(appReqBlock);
    }

    /**
     * Create a LumpSum trx for the selected active beneficiary ContractClient
     * @param appReqBlock
     * @return
     */
    private String saveLumpSumTrx(AppReqBlock appReqBlock) throws EDITEventException, PortalEditingException, Exception
    {
        String responseMessage = null;

        CasetrackingUseCase casetrackingComponent = new CasetrackingUseCaseImpl();

        String taxYearEntered = Util.initString(appReqBlock.getReqParm("taxYear"), "");
        String effDate = DateTimeUtil.formatMMDDYYYYToYYYYMMDD(appReqBlock.getReqParm("effectiveDate"));
        EDITBigDecimal amountOverride = new EDITBigDecimal(Util.initString(appReqBlock.getReqParm("amountOverride"), "0"));
        EDITBigDecimal interestOverride = new EDITBigDecimal(Util.initString(appReqBlock.getReqParm("interestOverride"), "0"));
        String zeroInterestIndicator = Util.initString(appReqBlock.getReqParm("zeroInterestIndicator"), "unchecked");

        if (zeroInterestIndicator.equalsIgnoreCase("checked"))
        {
            zeroInterestIndicator = "Y";
        }
        else
        {
            zeroInterestIndicator = "N";
        }

        int taxYear = 0;
        EDITDate effectiveDate = new EDITDate();

        if (effDate != null)
        {
            effectiveDate = new EDITDate(effDate);
            taxYear = effectiveDate.getYear();
        }

        if (!taxYearEntered.equals(""))
        {
            if (Util.isANumber(taxYearEntered))
            {
                taxYear = Integer.parseInt(taxYearEntered);
            }
        }

        String segmentPK = appReqBlock.getReqParm("segmentPK");

        Segment segment = Segment.findByPK(new Long(segmentPK));

        Withholding withholding = (Withholding) appReqBlock.getHttpSession().getAttribute("withholdingOverride");

        Charge[] charges = (Charge[]) appReqBlock.getHttpSession().getAttribute("charges");

        DoubleTableModel model = new ContractDetailDoubleTableModel(segment, appReqBlock);

        TableModel tableModel = (TableModel) model.getTableModel(DoubleTableModel.TO_TABLEMODEL);

        int count = tableModel.getRowCount();

        for (int i = 0; i < count; i++)
        {
            ContractDetailTableRow tableRow = (ContractDetailTableRow) tableModel.getRow(i);
            String contractClientPK = tableRow.getRowId();
            String allocPct = Util.initString((String) tableRow.getCellValue(ContractDetailDoubleTableModel.COLUMN_ALLOC_PERCENT), "");

            //If mutiple transactions, the first one could have updated the segment, this will get the updated segment
            segment = Segment.findByPK(new Long(segmentPK));

            if (Util.isANumber(allocPct))
            {
                EDITBigDecimal allocationPct = new EDITBigDecimal(allocPct, 10);

                CasetrackingLog casetrackingLog = setupLogEntry(appReqBlock, CASETRACKING_OPTION_LUMP_SUM_PROCEEDS, allocationPct);

                try
                {
                    casetrackingComponent.saveLumpSumTransaction(casetrackingLog, new Long(contractClientPK), taxYear, amountOverride,  interestOverride, zeroInterestIndicator, withholding, charges, segment, false, (i + 1));
                }
                catch (EDITEventException e)
                {
                    if (e != null && e.getErrorNumber() == EDITEventException.CONSTANT_NO_DATA_FOUND)
                    {
                        responseMessage = e.getMessage();
                        appReqBlock.getHttpServletRequest().setAttribute("responseMessage", responseMessage);
                        new ContractDetailDoubleTableModel(segment, appReqBlock).removeDoubleTableModelFromSessionScope();
                    }
                }
            }
        }

        //If mutiple transactions, lazy error will occur if the segment is not accessed again
        segment = Segment.findByPK(new Long(segmentPK));

        new ContractDetailDoubleTableModel(segment, appReqBlock);

        appReqBlock.setReqParm("transactionType", CASETRACKING_OPTION_LUMP_SUM_PROCEEDS);

        appReqBlock.getHttpSession().removeAttribute("withholdingOverride");
        appReqBlock.getHttpSession().removeAttribute("charges");

        return showSelectedTransaction(appReqBlock);
    }

    /**
     * Spousal Continuation- terminate the current owner  and set spouse as the new owner.
     * @param appReqBlock
     * @return
     */
    private String saveSpousalContinuation(AppReqBlock appReqBlock)
    {
        CasetrackingUseCase casetrackingComponent = new CasetrackingUseCaseImpl();

        String contractClientPK = null;

        String segmentPK = appReqBlock.getReqParm("segmentPK");

        Segment segment = Segment.findByPK(new Long(segmentPK));

        DoubleTableModel model = new ContractDetailDoubleTableModel(segment, appReqBlock);

        if (model != null)
        {
            TableModel tableModel = (TableModel) model.getTableModel(DoubleTableModel.TO_TABLEMODEL);

            //only one selection can be made
            TableRow rightTableRow = (ContractDetailTableRow) tableModel.getRow(0);
            contractClientPK = rightTableRow.getRowId();

            String allocPct = Util.initString((String) rightTableRow.getCellValue(ContractDetailDoubleTableModel.COLUMN_ALLOC_PERCENT), "0");
            EDITBigDecimal allocationPct = new EDITBigDecimal(allocPct, 10);

            CasetrackingLog casetrackingLog = setupLogEntry(appReqBlock, CASETRACKING_OPTION_SPOUSAL_CONTINUATION, allocationPct);

            casetrackingComponent.processSpousalContinuation(casetrackingLog, new Long(contractClientPK));
        }

        appReqBlock.setReqParm("transactionType", CASETRACKING_OPTION_SPOUSAL_CONTINUATION);

        return showSelectedTransaction(appReqBlock);
    }

    private String saveRiderClaim(AppReqBlock appReqBlock)
    {
        CasetrackingUseCase casetrackingComponent = new CasetrackingUseCaseImpl();
        ClientDetail clientDetail = (ClientDetail) Util.mapFormDataToHibernateEntity(appReqBlock.getHttpServletRequest(), ClientDetail.class, SessionHelper.EDITSOLUTIONS, true);
        casetrackingComponent.updateClientProcess(clientDetail);

        String riderPK = null;

        String segmentPK = appReqBlock.getReqParm("segmentPK");
        String careType = Util.initString(appReqBlock.getReqParm("careTypeCT"), null);

        String claimType = appReqBlock.getReqParm("claimType");
        String conditionCT = Util.initString(appReqBlock.getReqParm("conditionCT"), null);
        String authorizedSignatureCT = Util.initString(appReqBlock.getReqParm("authorizedSignatureCT"), null);

        String dateOfDeath = DateTimeUtil.formatMMDDYYYYToYYYYMMDD(appReqBlock.getReqParm("dateOfDeath"));

        EDITDate edDateOfDeath = null;
        if (dateOfDeath != null)
        {
            edDateOfDeath = new EDITDate(dateOfDeath);
        }

        EDITBigDecimal amountOverride = new EDITBigDecimal(Util.initString(appReqBlock.getReqParm("amountOverride"), "0"));
        EDITBigDecimal interestOverride = new EDITBigDecimal(Util.initString(appReqBlock.getReqParm("interestOverride"), "0"));

        Segment segment = Segment.findByPK(new Long(segmentPK));

        DoubleTableModel model = new RiderDetailDoubleTableModel(segment, appReqBlock);

        if (model != null)
        {
            TableModel tableModel = (TableModel) model.getTableModel(DoubleTableModel.TO_TABLEMODEL);

            //only one selection can be made
            TableRow rightTableRow = (RiderDetailTableRow) tableModel.getRow(0);
            riderPK = rightTableRow.getRowId();


            Segment rider = Segment.findByPK(new Long(riderPK));

            boolean errorMessage = false;

            if (rider.getOptionCodeCT().equalsIgnoreCase(Segment.OPTIONCODECT_RIDER_LTC)||
                rider.getOptionCodeCT().equalsIgnoreCase(Segment.OPTIONCODECT_RIDER_LTCTI) ||
                rider.getOptionCodeCT().equalsIgnoreCase(Segment.OPTIONCODECT_RIDER_TI))
            {
                if (careType == null)
                {
                    appReqBlock.getHttpServletRequest().setAttribute("responseMessage", "Care Type Must Be Selected");
                    errorMessage = true;
                }

                if (rider.getOptionCodeCT().equalsIgnoreCase(Segment.OPTIONCODECT_RIDER_LTCTI))
                {
                    if (claimType == null)
                    {
                        appReqBlock.getHttpServletRequest().setAttribute("responseMessage", "Claim Type Must Be Selected For for LTCTI Claim");
                        errorMessage = true;
                    }
                }

                if (conditionCT == null)
                {
                    appReqBlock.getHttpServletRequest().setAttribute("responseMessage", "Condition Must Be Selected For LTC and LTCTI Claim");
                    errorMessage = true;
                }
            }

            if (!errorMessage)
            {
                CasetrackingLog casetrackingLog = setupLogEntry(appReqBlock, CASETRACKING_OPTION_RIDER_CLAIM, new EDITBigDecimal());

                casetrackingComponent.processRiderClaim(casetrackingLog, new Long(riderPK), careType, edDateOfDeath, claimType, conditionCT, authorizedSignatureCT, amountOverride, interestOverride);
            }
            else
            {
                appReqBlock.getHttpServletRequest().setAttribute("claimType", claimType);
                appReqBlock.getHttpServletRequest().setAttribute("conditionCT", conditionCT);
                appReqBlock.getHttpServletRequest().setAttribute("effectiveDate", appReqBlock.getReqParm("effectiveDate"));
                appReqBlock.getHttpServletRequest().setAttribute("authorizedSignatureCT", authorizedSignatureCT);
                appReqBlock.getHttpServletRequest().setAttribute("amountOverride", amountOverride.toString());
                appReqBlock.getHttpServletRequest().setAttribute("interestOverride", interestOverride.toString());
            }
        }

        appReqBlock.setReqParm("transactionType", CASETRACKING_OPTION_RIDER_CLAIM);

        return showSelectedTransaction(appReqBlock);
    }

    /**
     * Set of Investment entities will be saved in clouldland and saved on submit
     * @param appReqBlock
     * @return
     */
    private String saveToFundSelections(AppReqBlock appReqBlock)
    {
        FundTableModel fundTableModel = new FundTableModel(appReqBlock);

        fundTableModel.updateFundTable();

        fundTableModel.resetAllRows();

        appReqBlock.setReqParm("transactionType", appReqBlock.getReqParm("casetrackingOption"));

        setEffectiveDateInRequestScope(appReqBlock);

        return showSelectedTransaction(appReqBlock);
    }

    /**
     * Displays the Fund details selected in the summary area. (SupplementalContract/OpenClain dialog)
     * @param appReqBlock
     * @return
     */
    private String showFundDetails(AppReqBlock appReqBlock)
    {
        new FundTableModel(appReqBlock).showFundDetails();

        setEffectiveDateInRequestScope(appReqBlock);

        return showSelectedTransaction(appReqBlock);
    }

    /**
     * Puts the effectiveDate in Request scope so that the user need not enter repeatedly.
     * @param appReqBlock
     */
    private void setEffectiveDateInRequestScope(AppReqBlock appReqBlock)
    {
        // this is only purpose of displaying the value entered after it is submitted to save ToFunds.
        String effectiveDateStr = DateTimeUtil.formatMMDDYYYYToYYYYMMDD((String) appReqBlock.getReqParm("effectiveDate"));
        EDITDate effectiveDate = null;

        if (effectiveDateStr != null)
        {
            effectiveDate = new EDITDate(effectiveDateStr);
        }

        appReqBlock.getHttpServletRequest().setAttribute("effectiveDate", effectiveDate);      // sending in an EDITDate, no conversion needed
    }

    /**
     * Execute Payout Quote then display results back to the page.
     * @param appReqBlock
     * @return
     */
    private String performPayoutQuote(AppReqBlock appReqBlock)
    {
        CasetrackingUseCase casetrackingComponent = new CasetrackingUseCaseImpl();

        String casetrackingOption = appReqBlock.getReqParm("casetrackingOption");

        Payout payout = (Payout) Util.mapFormDataToHibernateEntity(appReqBlock.getHttpServletRequest(), Payout.class, SessionHelper.EDITSOLUTIONS, true);

        String segmentPK = appReqBlock.getReqParm("segmentPK");

        DoubleTableModel model = (ContractDetailDoubleTableModel) appReqBlock.getHttpSession().getAttribute("doubleTableModel");

        TableModel tableModel = (TableModel) model.getTableModel(2);

        ContractDetailTableRow tableRow = null;
        String contractClientPK = null;

        if (tableRow != null)
        {
            tableRow = (ContractDetailTableRow) tableModel.getRow(0);
            contractClientPK = tableRow.getRowId();
        }

        CaseTrackingQuoteVO quoteVO = casetrackingComponent.performPayoutQuote(payout, new Long(segmentPK), new Long(contractClientPK));

        appReqBlock.setReqParm("transactionType", casetrackingOption);
        appReqBlock.getHttpSession().setAttribute("caseTrackingQuoteVO", quoteVO);

        return showSelectedTransaction(appReqBlock);
    }

    /**
      * For the active bene selcted, either create a new contract or update an existing one, with
      * the data entered.
      * @param appReqBlock
      * @return
      */
    private String saveSupplementalContractOrOpenClaim(AppReqBlock appReqBlock) throws EDITEventException, PortalEditingException, Exception
    {
        String responseMessage = null;

        CasetrackingUseCase casetrackingComponent = new CasetrackingUseCaseImpl();

        String annuityOption = Util.initString(appReqBlock.getReqParm("annuityOption"), null);
        Payout payout = null;
        if (annuityOption != null)
        {
            payout = (Payout) Util.mapFormDataToHibernateEntity(appReqBlock.getHttpServletRequest(), Payout.class, SessionHelper.EDITSOLUTIONS, true);
        }

        String casetrackingOption = appReqBlock.getReqParm("casetrackingOption");
        String createNewPolicyInd = Util.initString(appReqBlock.getReqParm("createNewPolicy"), "");
        String contractClientPK = "0";
        Map fundMap = (Map) appReqBlock.getHttpSession().getAttribute("casetracking.supplementalContract.fundMap");

        String segmentPK = appReqBlock.getReqParm("segmentPK");

        Segment segment = Segment.findByPK(new Long(segmentPK));

        DoubleTableModel model = new ContractDetailDoubleTableModel(segment, appReqBlock);
        EDITBigDecimal allocationPct = new EDITBigDecimal("0");

        if (model != null)
        {
            TableModel tableModel = (TableModel) model.getTableModel(DoubleTableModel.TO_TABLEMODEL);

            ContractDetailTableRow tableRow = (ContractDetailTableRow) tableModel.getRow(0);
            contractClientPK = tableRow.getRowId();

            String allocPct = Util.initString((String) tableRow.getCellValue(ContractDetailDoubleTableModel.COLUMN_ALLOC_PERCENT), "");

            if (Util.isANumber(allocPct))
            {
                allocationPct = new EDITBigDecimal(allocPct, 10);
            }
        }

        boolean openClaim = false;

        if (casetrackingOption.equalsIgnoreCase(CASETRACKING_OPTIONS_OPEN_CLAIM))
        {
            openClaim = true;
        }

        CasetrackingLog casetrackingLog = setupLogEntry(appReqBlock, casetrackingOption, allocationPct);

        //todo -  Annuitization
        if (createNewPolicyInd.equalsIgnoreCase("on"))
        {
            Segment newSegment = setNewSegmentFields(appReqBlock, segment);
            if (payout != null)
            {
                newSegment.setPayout(payout);
            }

            casetrackingLog.setNewContractNumber(newSegment.getContractNumber());

            try
            {
                casetrackingComponent.createNewContract(newSegment, new Long(contractClientPK), openClaim, casetrackingLog, fundMap);
            }
            catch (EDITEventException e)
            {
                if (e != null && e.getErrorNumber() == EDITEventException.CONSTANT_NO_DATA_FOUND)
                {
                    responseMessage = e.getMessage();
                    appReqBlock.getHttpServletRequest().setAttribute("responseMessage", responseMessage);
                }
            }

            fundMap = null;
            appReqBlock.getHttpSession().setAttribute("casetracking.supplementalContract.fundMap", fundMap);
            new ContractDetailDoubleTableModel(segment, appReqBlock).removeDoubleTableModelFromSessionScope();
            appReqBlock.setReqParm("companyStructurePK", null);
        }

        appReqBlock.setReqParm("transactionType", casetrackingOption);

        return showSelectedTransaction(appReqBlock);
    }

    /**
     * Capture the contract information entered for the Supplemental Contract/Open Claim process
     * @param appReqBlock
     * @return
     */
    private Segment setNewSegmentFields(AppReqBlock appReqBlock, Segment oldSegment)
    {
        Segment segment = new Segment();
        segment.setContractNumber(Util.initString((appReqBlock.getReqParm("newPolicyNumber")), null));
        String optionCode = Util.initString((appReqBlock.getReqParm("annuityOption")), null);
        if (optionCode == null)
        {
            optionCode = oldSegment.getOptionCodeCT();
        }
        segment.setOptionCodeCT(optionCode);

        segment.setIssueStateCT(Util.initString((appReqBlock.getReqParm("issueState")), null));

        String effectiveDateString = DateTimeUtil.formatMMDDYYYYToYYYYMMDD(appReqBlock.getReqParm("effectiveDate"));
        EDITDate effectiveDate = null;
        if (effectiveDateString != null)
        {
            effectiveDate = new EDITDate(effectiveDateString);
        }
        segment.setEffectiveDate(effectiveDate);


        segment.setProductStructureFK(new Long(appReqBlock.getReqParm("companyStructurePK")));
        segment.setSuppOriginalContractNumber(appReqBlock.getReqParm("contractNumber"));
        segment.setAmount(new EDITBigDecimal(Util.initString(appReqBlock.getReqParm("purchaseAmount"), "0")));

        UserSession userSession = appReqBlock.getUserSession();
        segment.setOperator(userSession.getUsername());

        return segment;
    }

    /**
     * Displays the details of the Case Requirement selected.
     * @param appReqBlock
     * @return
     */
    private String showRequirementDetail(AppReqBlock appReqBlock)
    {
        String process = (String) appReqBlock.getHttpSession().getAttribute("casetracking.process");

        ProductStructure productStructure = ProductStructure.findByNames(process, "*", "*", "*", "*");

        Long clientDetailPK = (Long) appReqBlock.getHttpSession().getAttribute("casetracking.selectedClientDetailPK");

        ClientDetail clientDetail = ClientDetail.findByPK(clientDetailPK);

        String caseRequirementPK = new RequirementsTableModel(productStructure, clientDetail, appReqBlock).getSelectedRowId();

        CaseRequirement caseRequirement = CaseRequirement.findByPK(new Long(caseRequirementPK));

        appReqBlock.getHttpServletRequest().setAttribute("activeCaseRequirement", caseRequirement);

        return showCasetrackingRequirement(appReqBlock);
    }

    /**
     *
     * @param appReqBlock
     * @return
     */
    private String addCaseRequirement(AppReqBlock appReqBlock)
    {
        return showManualRequirementDialog(appReqBlock);
    }

    /**
     * Updates the Case Requirement.
     * @param appReqBlock
     * @return
     */
    private String saveCaseRequirement(AppReqBlock appReqBlock)
    {
        CaseRequirement caseRequirement = (CaseRequirement) Util.mapFormDataToHibernateEntity(appReqBlock.getHttpServletRequest(), CaseRequirement.class, SessionHelper.EDITSOLUTIONS, true);

        String receivedDate = DateTimeUtil.formatMMDDYYYYToYYYYMMDD(appReqBlock.getReqParm("receivedDate"));
        if (receivedDate == null)
        {
            caseRequirement.setReceivedDate(null);
        }

        CasetrackingUseCase caseRequirementComponent = new CasetrackingUseCaseImpl();

        caseRequirementComponent.updateCaseRequirement(caseRequirement);

        String process = (String) appReqBlock.getHttpSession().getAttribute("casetracking.process");

        ProductStructure productStructure = ProductStructure.findByNames(process, "*", "*", "*", "*");

        Long clientDetailPK = (Long) appReqBlock.getHttpSession().getAttribute("casetracking.selectedClientDetailPK");

        ClientDetail clientDetail = ClientDetail.findByPK(clientDetailPK);

        new RequirementsTableModel(productStructure, clientDetail, appReqBlock).resetAllRows();

        return showCasetrackingRequirement(appReqBlock);
    }

    /**
     * Unselectes the Case Requirement.
     * @param appReqBlock
     * @return
     */
    private String cancelCaseRequirement(AppReqBlock appReqBlock)
    {
        String process = (String) appReqBlock.getHttpSession().getAttribute("casetracking.process");

        ProductStructure productStructure = ProductStructure.findByNames(process, "*", "*", "*", "*");

        Long clientDetailPK = (Long) appReqBlock.getHttpSession().getAttribute("casetracking.selectedClientDetailPK");

        ClientDetail clientDetail = ClientDetail.findByPK(clientDetailPK);

        new RequirementsTableModel(productStructure, clientDetail, appReqBlock).resetAllRows();

        return showCasetrackingRequirement(appReqBlock);
    }

    /**
     * Deletes selected Case Requirement
     * @param appReqBlock
     * @return
     */
    private String deleteCaseRequirement(AppReqBlock appReqBlock)
    {
        String caseRequirementPK = appReqBlock.getReqParm("caseRequirementPK");

        CasetrackingUseCase caseRequirementComponent = new CasetrackingUseCaseImpl();

        CaseRequirement caseRequirement = CaseRequirement.findByPK(new Long(caseRequirementPK));

        caseRequirementComponent.deleteCaseRequirement(caseRequirement);

        return showCasetrackingRequirement(appReqBlock);
    }

    /**
     * Initialize a log record from the data in the session
     * @param appReqBlock
     * @param event
     * @param allocationPct
     * @return
     */
    private CasetrackingLog setupLogEntry(AppReqBlock appReqBlock, String event, EDITBigDecimal allocationPct)
    {
        CasetrackingLog casetrackingLog = new CasetrackingLog();

        String effectiveDateString = DateTimeUtil.formatMMDDYYYYToYYYYMMDD(appReqBlock.getHttpServletRequest().getParameter("effectiveDate"));

        EDITDate effectiveDate = null;

        if (effectiveDateString != null)
        {
            effectiveDate = new EDITDate(effectiveDateString);
        }

        if (event != null &&
            (!event.equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_DEATH) &&
             !event.equalsIgnoreCase("DP") && !event.equalsIgnoreCase("DPR")))
        {
            casetrackingLog.setEffectiveDate(effectiveDate);
        }

        UserSession userSession = appReqBlock.getUserSession();
        casetrackingLog.setOperator(userSession.getUsername());
        casetrackingLog.setCaseTrackingEvent(event);
        casetrackingLog.setAllocationPercent(allocationPct);

        String casetrackingProcess = (String) appReqBlock.getHttpSession().getAttribute("casetracking.process");
        casetrackingLog.setCaseTrackingProcess(casetrackingProcess);

        Long clientDetailPK = (Long) appReqBlock.getHttpSession().getAttribute("casetracking.selectedClientDetailPK");
        ClientDetail clientDetail = ClientDetail.findByPK(clientDetailPK);
        casetrackingLog.setClientDetail(clientDetail);

        return casetrackingLog;
    }

    /**
     * Displays the Bene Alloc % upon the selection of an entry in the contract dialog summary.
     * @param appReqBlock
     * @return
     */
    private String showContractDetail(AppReqBlock appReqBlock)
    {
        String segmentPK = appReqBlock.getReqParm("segmentPK");

        Segment segment = Segment.findByPK(new Long(segmentPK));

        String contractClientPK = new ContractDialogTableModel(segment, appReqBlock).getSelectedRowId();

        ContractClient contractClient = ContractClient.findByPK(new Long(contractClientPK));

        appReqBlock.getHttpServletRequest().setAttribute("activeContractClient", contractClient);
        appReqBlock.getHttpSession().setAttribute("caseTrackingQuoteVO", null);

        return showContractDialog(appReqBlock);
    }

    /**
     *
     * @param appReqBlock
     * @return
     */
    private String showSupplementalContract(AppReqBlock appReqBlock)
    {
        String companyStructurePK = appReqBlock.getReqParm("companyStructurePK");

        new FundTableModel(appReqBlock);

        appReqBlock.getHttpServletRequest().setAttribute("companyStructurePK", companyStructurePK);

        return CASETRACKING_SUPPLEMENTAL_CONTRACT_DIALOG;
    }

    /**
     * Displays withholding overrides dialog
     * @param appReqBlock
     * @return
     */
    private String showWithholdingOverrideDialog(AppReqBlock appReqBlock)
    {
        String segmentPK = appReqBlock.getReqParm("segmentPK");

        Segment segment = Segment.findByPK(new Long(segmentPK));

        Long productStructurePK = segment.getProductStructureFK();

        appReqBlock.getHttpServletRequest().setAttribute("companyStructurePK", productStructurePK);
        appReqBlock.getHttpServletRequest().setAttribute("segmentPK", segmentPK);

        return CASETRACKING_WITHHOLDING_OVERRIDES_DIALOG;
    }

    /**
     * saves withholding override.
     * @param appReqBlock
     * @return
     */
    private String saveWithholdingOverride(AppReqBlock appReqBlock)
    {
        Withholding withholding = new Withholding();

        withholding.setFederalWithholdingTypeCT((String) Util.initString(appReqBlock.getReqParm("federalWithholdingTypeCT"), null));
        withholding.setFederalWithholdingAmount((EDITBigDecimal) new EDITBigDecimal(Util.initString(appReqBlock.getReqParm("federalWithholdingAmount"), "0")));
        withholding.setFederalWithholdingPercent((EDITBigDecimal) new EDITBigDecimal(Util.initString(appReqBlock.getReqParm("federalWithholdingPercent"), "0")));
        withholding.setStateWithholdingTypeCT((String) Util.initString(appReqBlock.getReqParm("stateWithholdingTypeCT"), null));
        withholding.setStateWithholdingAmount((EDITBigDecimal) new EDITBigDecimal(Util.initString(appReqBlock.getReqParm("stateWithholdingAmount"), "0")));
        withholding.setStateWithholdingPercent((EDITBigDecimal) new EDITBigDecimal(Util.initString(appReqBlock.getReqParm("stateWithholdingPercent"), "0")));
        withholding.setCityWithholdingTypeCT((String) Util.initString(appReqBlock.getReqParm("cityWithholdingTypeCT"), null));
        withholding.setCityWithholdingAmount((EDITBigDecimal) new EDITBigDecimal(Util.initString(appReqBlock.getReqParm("cityWithholdingAmount"), "0")));
        withholding.setCityWithholdingPercent((EDITBigDecimal) new EDITBigDecimal(Util.initString(appReqBlock.getReqParm("cityWithholdingPercent"), "0")));
        withholding.setCountyWithholdingTypeCT((String) Util.initString(appReqBlock.getReqParm("countyWithholdingTypeCT"), null));
        withholding.setCountyWithholdingAmount((EDITBigDecimal) new EDITBigDecimal(Util.initString(appReqBlock.getReqParm("countyWithholdingAmount"), "0")));
        withholding.setCountyWithholdingPercent((EDITBigDecimal) new EDITBigDecimal(Util.initString(appReqBlock.getReqParm("countyWithholdingPercent"), "0")));

        appReqBlock.getHttpSession().setAttribute("withholdingOverride", withholding);

        return showWithholdingOverrideDialog(appReqBlock);
    }

    /**
    * Deletes selected withholding override.
    * @param appReqBlock
    * @return
    */
    private String deleteWithholdingOverride(AppReqBlock appReqBlock)
    {
        appReqBlock.getHttpSession().setAttribute("withholdingOverride", null);

        return showWithholdingOverrideDialog(appReqBlock);
    }

    /**
     * Displays Lump Sum transaction dialog.
     * @param appReqBlock
     * @return
     */
    private String showLumpSumTransaction(AppReqBlock appReqBlock)
    {
        appReqBlock.getHttpSession().setAttribute("withholdingOverride", null);

        return CASETRACKING_LUMP_SUM_PROCEEDS_DIALOG;
    }

    /**
     * Displays Manual Requirement Dialog.
     * @param appReqBlock
     * @return
     */
    private String showManualRequirementDialog(AppReqBlock appReqBlock)
    {
        String process = (String) appReqBlock.getHttpSession().getAttribute("casetracking.process");

        ProductStructure productStructure = ProductStructure.findByNames(process, "*", "*", "*", "*");

        Requirement[] requirements = Requirement.findBy_ProductStructure_And_ManualInd(productStructure, "Y");

        appReqBlock.getHttpServletRequest().setAttribute("requirements", requirements);

        return CASETRACKING_MANUAL_REQUIREMENT_DIALOG;
    }

    /**
     * Saves the manual requirement to the database.
     * @param appReqBlock
     * @return
     */
    private String saveManualRequirement(AppReqBlock appReqBlock)
    {
        CasetrackingUseCase caseRequirementComponent = new CasetrackingUseCaseImpl();

        String requirementId = appReqBlock.getReqParm("requirementId");

        Requirement requirement = Requirement.findBy_RequirementId(requirementId);

        String process = (String) appReqBlock.getHttpSession().getAttribute("casetracking.process");

        ProductStructure productStructure = ProductStructure.findByNames(process, "*", "*", "*", "*");

        FilteredRequirement filteredRequirement = FilteredRequirement.findBy_ProductStructure_And_Requirement(productStructure, requirement);

        Long clientDetailPK = (Long) appReqBlock.getHttpSession().getAttribute("casetracking.selectedClientDetailPK");

        ClientDetail clientDetail = ClientDetail.findByPK(clientDetailPK);

        CaseRequirement caseRequirement = CaseRequirement.findBy_ClientDetail_And_FilteredRequirement(clientDetail, filteredRequirement);

        String responseMessage = null;

        if (caseRequirement == null)
        {
            caseRequirementComponent.associateFilteredRequirementToClient(clientDetail, filteredRequirement);
        }
        else
        {
            responseMessage = "This Requirment is already associated with the Client.";

            appReqBlock.getHttpServletRequest().setAttribute("responseMessage", responseMessage);
        }

        return showCasetrackingRequirement(appReqBlock);
    }

    /**
     * Displays the Requirement Description in the Add Requirement Dialog.
     * @return
     */
    private String showRequirementDescription(AppReqBlock appReqBlock)
    {
        String requirementId = appReqBlock.getReqParm("requirementId");

        Requirement requirement = Requirement.findBy_RequirementId(requirementId);

        appReqBlock.getHttpServletRequest().setAttribute("requirement", requirement);

        return showManualRequirementDialog(appReqBlock);
    }

    /**
     * Displays the Beneficiaries Dialog for the Stretch IRA transaction
     * @param appReqBlock
     * @return
     */
    private String showStretchIRABeneDialog(AppReqBlock appReqBlock)
    {
        CodeTableWrapper codeTableWrapper = CodeTableWrapper.getSingleton();

        String segmentPK = appReqBlock.getReqParm("segmentPK");
        String casetrackingOption = appReqBlock.getReqParm("casetrackingOption");
        String newPolicyIndStatus = appReqBlock.getReqParm("newPolicyIndStatus");
        String newPolicyNumber = appReqBlock.getReqParm("newPolicyNumber");
        String beneficiaryStatus = appReqBlock.getReqParm("beneficiaryStatus");
        String frequencyCT = appReqBlock.getReqParm("frequencyCT");
        String rmdStartDate = appReqBlock.getReqParm("rmdStartDate");
        String printLine1 = appReqBlock.getReqParm("printLine");
        String printLine2 = appReqBlock.getReqParm("printLine2");

        appReqBlock.getHttpServletRequest().setAttribute("segmentPK", segmentPK);
        appReqBlock.getHttpServletRequest().setAttribute("casetrackingOption", casetrackingOption);
        appReqBlock.getHttpServletRequest().setAttribute("newPolicyIndStatus", newPolicyIndStatus);
        appReqBlock.getHttpServletRequest().setAttribute("newPolicyNumber", newPolicyNumber);

        if (beneficiaryStatus != null && Util.isANumber(beneficiaryStatus))
        {
            beneficiaryStatus = codeTableWrapper.getCodeTableEntry(Long.parseLong(beneficiaryStatus)).getCode();
            appReqBlock.getHttpServletRequest().setAttribute("beneficiaryStatus", beneficiaryStatus);
        }

        if (frequencyCT != null && Util.isANumber(frequencyCT))
        {
            frequencyCT = codeTableWrapper.getCodeTableEntry(Long.parseLong(frequencyCT)).getCode();
            appReqBlock.getHttpServletRequest().setAttribute("frequencyCT", frequencyCT);
        }

        appReqBlock.getHttpServletRequest().setAttribute("rmdStartDate", rmdStartDate);
        appReqBlock.getHttpServletRequest().setAttribute("printLine1", printLine1);
        appReqBlock.getHttpServletRequest().setAttribute("printLine2", printLine2);

        Segment segment = Segment.findByPK(new Long(segmentPK));
        new ExistingBeneficiariesTableModel(segment, appReqBlock);
        new ProspectiveBeneficiariesTableModel(appReqBlock);

        return CASETRACKING_STRETCH_IRA_BENE_DIALOG;
    }

    /**
     * Displays the details of the Stretch IRA Beneficiary selected on Stretch IRA Beneficiaries page.
     * @param appReqBlock
     * @return
     */
    private String showStretchIRABeneficiaryDetail(AppReqBlock appReqBlock)
    {
        String segmentPK = appReqBlock.getReqParm("segmentPK");

        Segment segment = Segment.findByPK(new Long(segmentPK));

        String selectedBeneficiaryPK = new ExistingBeneficiariesTableModel(segment, appReqBlock).getSelectedRowId();

        ContractClient contractClient = ContractClient.findByPK(new Long(selectedBeneficiaryPK));

        appReqBlock.getHttpServletRequest().setAttribute("selectedBene", contractClient);

        return showStretchIRABeneDialog(appReqBlock);
    }

    /**
     * Displays the details of Client selected in the search results. (Case Tracking Stretch IRA Beneficiaries dialog)
     * @param appReqBlock
     * @return
     */
    private String showProspectiveStretchIRABeneDetail(AppReqBlock appReqBlock)
    {
        String selectedProspectiveBenePK = new ProspectiveBeneficiariesTableModel(appReqBlock).getSelectedRowId();

        ClientDetail clientDetail = ClientDetail.findByPK(new Long(selectedProspectiveBenePK));

        appReqBlock.getHttpServletRequest().setAttribute("selectedProspectiveBene", clientDetail);

        String segmentPK = appReqBlock.getReqParm("segmentPK");

        Segment segment = Segment.findByPK(new Long(segmentPK));

        new ExistingBeneficiariesTableModel(segment, appReqBlock).resetAllRows();

        return showStretchIRABeneDialog(appReqBlock);
    }

    /**
     * New or Existing Stretch IRA Beneficiary details will be saved.
     * @param appReqBlock
     * @return
     */
    private String saveStretchIRABeneficiaryDetails(AppReqBlock appReqBlock)
    {
        //todo - only allow new benes for the new policy, page change required
        String selectedBenePK = Util.initString(appReqBlock.getReqParm("selectedBenePK"), "0");
        String prospectiveBeneClientPK = Util.initString(appReqBlock.getReqParm("selectedProspectiveBenePK"), "0");
        String beneRole = Util.initString(appReqBlock.getReqParm("beneficiaryRole"), null);
        String allocationPct = Util.initString(appReqBlock.getReqParm("allocationPercent"), "0");

        String segmentPK = (String) appReqBlock.getReqParm("segmentPK");
        Set contractClients = (Set)appReqBlock.getHttpSession().getAttribute("newBeneContractClient");
        if (contractClients == null)
        {
            contractClients = new HashSet();
        }

        if (!prospectiveBeneClientPK.equals("0"))
         {
             ContractClient contractClient = (ContractClient) Util.mapFormDataToHibernateEntity(appReqBlock.getHttpServletRequest(), ContractClient.class, SessionHelper.EDITSOLUTIONS, true);
             ClientRole clientRole = (ClientRole)Util.mapFormDataToHibernateEntity(appReqBlock.getHttpServletRequest(), ClientRole.class, SessionHelper.EDITSOLUTIONS, true);
             clientRole.setRoleTypeCT(beneRole);
             clientRole.addContractClient(contractClient);

             ContractClientAllocation contractClientAllocation = new ContractClientAllocation();
             contractClientAllocation.setAllocationPercent(new EDITBigDecimal(allocationPct));
             contractClientAllocation.setOverrideStatus("P");
             contractClientAllocation.setContractClient(contractClient);

             contractClient.setClientRole(clientRole);
             contractClient.addContractClientAllocation(contractClientAllocation);

             contractClients.add(contractClient);
         }

        appReqBlock.getHttpSession().setAttribute("newBeneContractClient", contractClients);

        return showStretchIRABeneDialog(appReqBlock);
    }

    /**
      * For the active bene selcted, create a new contract with the data entered.
      * @param appReqBlock
      * @return
      */
    private String saveStretchIRA(AppReqBlock appReqBlock)
    {
        CasetrackingUseCase casetrackingComponent = new CasetrackingUseCaseImpl();

        String casetrackingOption = appReqBlock.getReqParm("casetrackingOption");
        String beneContractClientPK = "0";

        String segmentPK = appReqBlock.getReqParm("segmentPK");
        Segment originalSegment = Segment.findByPK(new Long(segmentPK));

        DoubleTableModel model = new ContractDetailDoubleTableModel(originalSegment, appReqBlock);
        EDITBigDecimal allocationPct = new EDITBigDecimal("0");

        if (model != null)
        {
            TableModel tableModel = (TableModel) model.getTableModel(DoubleTableModel.TO_TABLEMODEL);

            ContractDetailTableRow tableRow = (ContractDetailTableRow) tableModel.getRow(0);
            beneContractClientPK = tableRow.getRowId();

            String allocPct = Util.initString((String) tableRow.getCellValue(ContractDetailDoubleTableModel.COLUMN_ALLOC_PERCENT), "");

            if (Util.isANumber(allocPct))
            {
                allocationPct = new EDITBigDecimal(allocPct, 10);
            }
        }

        String event = CASETRACKING_OPTION_STRETCH_IRA;

        CasetrackingLog casetrackingLog = setupLogEntry(appReqBlock, event, allocationPct);
        casetrackingLog.setContractNumber(originalSegment.getContractNumber());

        String beneStatus = Util.initString(appReqBlock.getReqParm("beneficiaryStatusCT"), null);
        String frequency = Util.initString(appReqBlock.getReqParm("frequencyCT"), null);
        String rmdStartDate = DateTimeUtil.formatMMDDYYYYToYYYYMMDD(appReqBlock.getReqParm("rmdStartDate"));
        RequiredMinDistribution rmd = null;
        if (beneStatus != null || frequency != null || rmdStartDate != null)
        {
            //cannot map new instance if the original contract does not have RMD, these can be an erroroneus entry
            rmd = new  RequiredMinDistribution();
            rmd.setBeneficiaryStatusCT(beneStatus);
            rmd.setFrequencyCT(frequency);

//            rmd = (RequiredMinDistribution) Util.mapFormDataToHibernateEntity(appReqBlock.getHttpServletRequest(), RequiredMinDistribution.class, SessionHelper.EDITSOLUTIONS);
            if (rmdStartDate != null)
            {
                rmd.setRMDStartDate(new EDITDate(rmdStartDate));
            }
        }

        //capture page data for the new contract
        Set beneficiaries = (Set)appReqBlock.getHttpSession().getAttribute("newBeneContractClient");
        casetrackingLog.setNewContractNumber(Util.initString((appReqBlock.getReqParm("newPolicyNumber")), null));

        ContractClient beneContractClient = ContractClient.findByPK(new Long(beneContractClientPK));
        String printLine1 = Util.initString(appReqBlock.getReqParm("printLine1"), null);
        String printLine2 = Util.initString(appReqBlock.getReqParm("printLine2"), null);

        casetrackingComponent.processStretchIRA(originalSegment, beneContractClient, casetrackingLog, rmd, beneficiaries, printLine1, printLine2);

        appReqBlock.setReqParm("transactionType", casetrackingOption);

        return showSelectedTransaction(appReqBlock);
    }

    private String showChargeOverridesDialog(AppReqBlock appReqBlock)
    {
        String segmentPK = appReqBlock.getReqParm("segmentPK");

        Segment segment = Segment.findByPK(new Long(segmentPK));

        Long productStructurePK = segment.getProductStructureFK();

        appReqBlock.getHttpServletRequest().setAttribute("companyStructurePK", productStructurePK);
        appReqBlock.getHttpServletRequest().setAttribute("segmentPK", segmentPK);

        return CASETRACKING_CHARGE_OVERRIDES_DIALOG;
    }

    private String showChargeDetailSummary(AppReqBlock appReqBlock)
    {
        String chargePKToUpdate = appReqBlock.getReqParm("chargePK");
        String chargeTypeCTToUpdate = appReqBlock.getReqParm("chargeTypeCT");

        Charge[] charges = (Charge[]) appReqBlock.getHttpSession().getAttribute("charges");
        if (charges != null)
        {
            for (int i = 0; i < charges.length; i++)
            {
                String chargePK = Util.initObject(charges[i], "ChargePK", new Long(0)).toString();
                String chargeTypeCT = Util.initString(charges[i].getChargeTypeCT(), "");
                if (!chargePK.equals(chargePKToUpdate) && !chargeTypeCT.equalsIgnoreCase(chargeTypeCTToUpdate))
                {
                    appReqBlock.getHttpSession().setAttribute("chargeOverride", charges[i]);
                    break;
                }
            }
        }

        return showChargeOverridesDialog(appReqBlock);
    }

    private String updateChargeOverride(AppReqBlock appReqBlock)
    {
        String chargePKToUpdate = appReqBlock.getReqParm("chargePK");
        String chargeTypeCTToUpdate = appReqBlock.getReqParm("chargeTypeCT");
        String chargeAmount = appReqBlock.getReqParm("chargeAmount");
        if (Util.isANumber(chargeTypeCTToUpdate))
        {
            chargeTypeCTToUpdate = CodeTableWrapper.getSingleton().getCodeTableEntry(Long.parseLong(chargeTypeCTToUpdate)).getCode();
        }

        Charge[] charges = (Charge[]) appReqBlock.getHttpSession().getAttribute("charges");

        boolean chargeFound = false;

        if (charges != null)
        {
            for (int i = 0; i < charges.length; i++)
            {
                String chargePK = Util.initObject(charges[i], "ChargePK", new Long(0)).toString();
                String chargeTypeCT = Util.initString(charges[i].getChargeTypeCT(), "");
                if (!chargePK.equals(chargePKToUpdate) && !chargeTypeCT.equalsIgnoreCase(chargeTypeCTToUpdate))
                {
                    charges[i].setChargeAmount(new EDITBigDecimal(chargeAmount));
                    chargeFound = true;
                    break;
                }
            }
        }

        List chargesList = new ArrayList();

        if (!chargeFound)
        {

            Charge newCharge = new Charge();
            newCharge.setChargeTypeCT(chargeTypeCTToUpdate);
            newCharge.setChargeAmount(new EDITBigDecimal(chargeAmount));

            chargesList.add(newCharge);
        }

        if (charges != null)
        {
            for (int i = 0; i < charges.length; i++)
            {
                chargesList.add(charges[i]);
            }
        }

        Charge[] updatedCharges = (Charge[]) chargesList.toArray(new Charge[chargesList.size()]);
        appReqBlock.getHttpSession().setAttribute("charges", updatedCharges);

        return showChargeOverridesDialog(appReqBlock);
    }

    private String deleteChargeOverride(AppReqBlock appReqBlock)
    {
        String chargePKToDelete = appReqBlock.getReqParm("chargePK");
        String chargeTypeCTToDelete = appReqBlock.getReqParm("chargeTypeCT");
        if (Util.isANumber(chargeTypeCTToDelete))
        {
            chargeTypeCTToDelete = CodeTableWrapper.getSingleton().getCodeTableEntry(Long.parseLong(chargeTypeCTToDelete)).getCode();
        }

        Charge[] charges = (Charge[]) appReqBlock.getHttpSession().getAttribute("charges");
        Charge[] updatedCharges = null;

        if (charges != null)
        {
            List chargesToKeep = new ArrayList();

            for (int i = 0; i < charges.length; i++)
            {
                String chargePK = Util.initObject(charges[i], "ChargePK", new Long(0)).toString();
                String chargeTypeCT = Util.initString(charges[i].getChargeTypeCT(), "");
                if (!chargePK.equals(chargePKToDelete) && !chargeTypeCT.equalsIgnoreCase(chargeTypeCTToDelete))
                {
                    chargesToKeep.add(charges[i]);
                }
            }

            if (chargesToKeep.size() > 0)
            {
                updatedCharges = (Charge[]) chargesToKeep.toArray(new Charge[chargesToKeep.size()]);
                appReqBlock.getHttpSession().setAttribute("charges", updatedCharges);
            }
            else
            {
                appReqBlock.getHttpSession().removeAttribute("charges");
            }
        }

        return showChargeOverridesDialog(appReqBlock);
    }
}
