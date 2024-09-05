/*
 * User: gfrosti
 * Date: Feb 12, 2003
 * Time: 1:16:36 PM
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package event.ui.servlet;

import contract.Deposits;
import contract.Segment;
import edit.common.CodeTableWrapper;
import edit.common.EDITDate;
import edit.common.EDITBigDecimal;
import edit.common.EDITDateTime;
import edit.common.exceptions.EDITEngineException;
import edit.common.vo.*;
import edit.portal.common.transactions.Transaction;
import edit.portal.common.session.*;
import edit.portal.widget.CashBatchSummaryTableModel;
import edit.portal.widget.CashBatchContractSummaryTableModel;
import edit.portal.widget.CashBatchDetailTableModel;
import event.business.Event;
import event.business.EventUseCase;
import event.component.EventComponent;
import event.component.EventUseCaseComponent;
import event.dm.dao.DAOFactory;
import event.*;
import fission.global.AppReqBlock;
import fission.utility.*;

import java.text.DecimalFormat;
import java.util.*;

import engine.business.Lookup;
import engine.Fee;
import engine.ProductStructure;
import engine.Company;
import client.ClientDetail;

import edit.common.exceptions.EDITDeleteException;
import security.Operator;


public class EventAdminTran extends Transaction
{
    // Pages
    private static final String TRANSACTION_PRIORITY_SUMMARY            = "/event/jsp/transactionPrioritySummary.jsp";
    private static final String TRANSACTION_CORRESPONDENCE_DIALOG       = "/event/jsp/transactionCorrespondenceDialog.jsp";
    private static final String CASH_BATCH_SUMMARY                      = "/event/jsp/cashBatchSummary.jsp";
    private static final String CASH_BATCH_ENTRY_DIALOG                 = "/event/jsp/cashBatchEntryDialog.jsp";
    private static final String CASH_BATCH_CONTRACT_SUMMARY_DIALOG      = "/event/jsp/cashBatchContractSummaryDialog.jsp";
    private static final String CASH_BATCH_CONTRACT_DETAIL_DIALOG       = "/event/jsp/cashBatchContractDetailDialog.jsp";
    private static final String CASH_BATCH_FILTER_DIALOG                = "/event/jsp/cashBatchFilterDialog.jsp";
    private static final String HEDGE_FUND_REDEMPTION                   = "/event/jsp/hedgeFundRedemption.jsp";
    private static final String DIVISION_LEVEL_FEES_DIALOG              = "/event/jsp/divisionLevelFeesDialog.jsp";

    // Actions
    private static final String SHOW_TRANSACTION_PRIORITY_SUMMARY       = "showTransactionPrioritySummary";
    private static final String SHOW_TRANSACTION_PRIORITY_DETAILS       = "showTransactionPriorityDetails";
    private static final String ADD_TRANSACTION_PRIORITY                = "addTransactionPriority";
    private static final String SAVE_TRANSACTION_PRIORITY               = "saveTransactionPriority";
    private static final String CANCEL_TRANSACTION_PRIORITY_EDITS       = "cancelTransactionPriorityEdits";
    private static final String DELETE_TRANSACTION_PRIORITY             = "deleteTransactionPriority";
    private static final String SHOW_TRANSACTION_CORRESPONDENCE_DIALOG  = "showTransactionCorrespondenceDialog";
    private static final String SHOW_TRANSACTION_CORRESPONDENCE_DETAILS = "showTransactionCorrespondenceDetails";
    private static final String ADD_TRANSACTION_CORRESPONDENCE          = "addTransactionCorrespondence";
    private static final String SAVE_TRANSACTION_CORRESPONDENCE         = "saveTransactionCorrespondence";
    private static final String CANCEL_TRANSACTION_CORRESPONDENCE_EDITS = "cancelTransactionCorrespondenceEdits";
    private static final String DELETE_TRANSACTION_CORRESPONDENCE       = "deleteTransactionCorrespondence";
    private static final String CLOSE_TRANSACTION_CORRESPONDENCE_DIALOG = "closeTransactionCorrespondenceDialog";
    private static final String SHOW_CASH_BATCH_SUMMARY                 = "showCashBatchSummary";
    private static final String SHOW_SELECTED_CASH_BATCH                = "showSelectedCashBatch";
    private static final String SHOW_HEDGE_FUND_REDEMPTION              = "showHedgeFundRedemption";
    private static final String RETRIEVE_SUSPENSE_AND_TRANSACTIONS_FOR_HEDGE_FUND = "retrieveSuspenseAndTransactionsForHedgeFund";
    private static final String SHOW_SELECTED_HEDGE_SUSPENSE            = "showSelectedHedgeSuspense";
    private static final String SHOW_SELECTED_EDIT_TRX_FOR_REDEMPTION   = "showSelectedEditTrxForRedemption";
    private static final String GENERATE_REDEMPTION_TRANSACTION         = "generateRedemptionTransaction";
    private static final String SHOW_DIVISION_LEVEL_FEES_DIALOG         = "showDivisionLevelFeesDialog";
    private static final String SAVE_FEE_AMOUNT_RECEIVED                = "saveFeeAmountReceived";
    private static final String SAVE_FEE_TRX_AMOUNTS                    = "saveFeeTrxAmounts";
    private static final String CLEAR_FEE_SESSION_PARAMS                = "clearFeeSessionParams";
    private static final String VOID_SELECTED_CASH_BATCH                = "voidSelectedCashBatch";
    private static final String ADD_CASH_BATCH_ENTRY                    = "addCashBatchEntry";
    private static final String SHOW_CASH_BATCH_ENTRY                   = "showCashBatchEntry";
    private static final String SHOW_CASH_BATCH_FILTER                  = "showCashBatchFilter";
    private static final String FILTER_CASH_BATCH                       = "filterCashBatch";
    private static final String CREATE_CASH_BATCH                       = "createCashBatch";
    private static final String SHOW_CASH_BATCH_CONTRACT_SUMMARY        = "showCashBatchContractSummary";
    private static final String CANCEL_CASH_BATCH_CONTRACT_SUMMARY      = "cancelCashBatchContractSummary";
    private static final String VIEW_CASH_BATCH_CONTRACT_SUMMARY        = "viewCashBatchContractSummary";
    private static final String RELEASE_CASH_BATCH                      = "releaseCashBatch";
    private static final String SHOW_CASH_BATCH_CONTRACT_DETAIL         = "showCashBatchContractDetail";
    private static final String ADD_CASH_BATCH_CONTRACT_DETAIL          = "addCashBatchContractDetail";
    private static final String VIEW_CASH_BATCH_CONTRACT_DETAIL         = "viewCashBatchContractDetail";
    private static final String CANCEL_CASH_BATCH_DETAIL                = "cancelCashBatchDetail";
    private static final String CLOSE_CASH_BATCH_DETAIL                 = "closeCashBatchDetail";
    private static final String SAVE_SUSPENSE_FOR_CASH_BATCH            = "saveSuspenseForCashBatch";
    private static final String GET_CONTRACT_INFO_FOR_SUSPENSE          = "getContractInfoForSuspense";
    private static final String SELECT_EXCHANGE_INFORMATION             = "selectExchangeInformation";
    private static final String DELETE_SELECTED_SUSPENSE                = "deleteSelectedSuspense";

    public String execute(AppReqBlock appReqBlock) throws Exception
    {
        String action = appReqBlock.getHttpServletRequest().getParameter("action");

        String nextPage = null;

        if (action.equals(SHOW_TRANSACTION_PRIORITY_SUMMARY))
        {
            nextPage = showTransactionPrioritySummary(appReqBlock);
        }
        else if (action.equals(SHOW_TRANSACTION_PRIORITY_DETAILS))
        {
            nextPage = showTransactionPriorityDetails(appReqBlock);
        }
        else if (action.equals(ADD_TRANSACTION_PRIORITY))
        {
            nextPage = addTransactionPriority(appReqBlock);
        }
        else if (action.equals(SAVE_TRANSACTION_PRIORITY))
        {
            nextPage = saveTransactionPriority(appReqBlock);
        }
        else if (action.equals(CANCEL_TRANSACTION_PRIORITY_EDITS))
        {
            nextPage = cancelTransactionPriorityEdits(appReqBlock);
        }
        else if (action.equals(DELETE_TRANSACTION_PRIORITY))
        {
            nextPage = deleteTransactionPriority(appReqBlock);
        }
        else if (action.equals(SHOW_TRANSACTION_CORRESPONDENCE_DIALOG))
        {
            nextPage = showTransactionCorrespondenceDialog(appReqBlock);
        }
        else if (action.equals(SHOW_TRANSACTION_CORRESPONDENCE_DETAILS))
        {
            nextPage = showTransactionCorrespondenceDetails(appReqBlock);
        }
        else if (action.equals(ADD_TRANSACTION_CORRESPONDENCE))
        {
            nextPage = addTransactionCorrespondence(appReqBlock);
        }
        else if (action.equals(SAVE_TRANSACTION_CORRESPONDENCE))
        {
            nextPage = saveTransactionCorrespondence(appReqBlock);
        }
        else if (action.equals(CANCEL_TRANSACTION_CORRESPONDENCE_EDITS))
        {
            nextPage = cancelTransactionCorrespondenceEdits(appReqBlock);
        }
        else if (action.equals(DELETE_TRANSACTION_CORRESPONDENCE))
        {
            nextPage = deleteTransactionCorrespondence(appReqBlock);
        }
        else if (action.equals(CLOSE_TRANSACTION_CORRESPONDENCE_DIALOG))
        {
            nextPage = closeTransactionCorrespondenceDialog(appReqBlock);
        }
        else if (action.equals(SHOW_CASH_BATCH_SUMMARY))
        {
            nextPage = showCashBatchSummary(appReqBlock);
        }
        else if (action.equals(VOID_SELECTED_CASH_BATCH))
        {
            nextPage = voidSelectedCashBatch(appReqBlock);
        }
        else if (action.equals(ADD_CASH_BATCH_ENTRY))
        {
            nextPage = addCashBatchEntry(appReqBlock);
        }
        else if (action.equals(SHOW_CASH_BATCH_ENTRY))
        {
            nextPage = showCashBatchEntry(appReqBlock);
        }
        else if (action.equals(SHOW_CASH_BATCH_FILTER))
        {
            nextPage = showCashBatchFilter(appReqBlock);
        }
        else if (action.equals(FILTER_CASH_BATCH))
        {
            nextPage = filterCashBatch(appReqBlock);
        }
        else if (action.equals(CREATE_CASH_BATCH))
        {
            nextPage = createCashBatch(appReqBlock);
        }
        else if (action.equals(SHOW_CASH_BATCH_CONTRACT_SUMMARY))
        {
            nextPage = showCashBatchContractSummary(appReqBlock);
        }
        else if (action.equals(CANCEL_CASH_BATCH_CONTRACT_SUMMARY))
        {
            nextPage = cancelCashBatchContractSummary(appReqBlock);
        }
        else if (action.equals(VIEW_CASH_BATCH_CONTRACT_SUMMARY))
        {
            nextPage = viewCashBatchContractSummary(appReqBlock);
        }
        else if (action.equals(DELETE_SELECTED_SUSPENSE))
        {
            nextPage = deleteSelectedSuspense(appReqBlock);
        }
        else if (action.equals(RELEASE_CASH_BATCH))
        {
            nextPage = releaseCashBatch(appReqBlock);
        }
        else if (action.equals(SHOW_CASH_BATCH_CONTRACT_DETAIL))
        {
            nextPage = showCashBatchContractDetail(appReqBlock);
        }
        else if (action.equals(ADD_CASH_BATCH_CONTRACT_DETAIL))
        {
            nextPage = addCashBatchContractDetail(appReqBlock);
        }
        else if (action.equals(VIEW_CASH_BATCH_CONTRACT_DETAIL))
        {
            nextPage = viewCashBatchContractDetail(appReqBlock);
        }
        else if (action.equals(GET_CONTRACT_INFO_FOR_SUSPENSE))
        {
            nextPage = getContractInfoForSuspense(appReqBlock);
        }
        else if (action.equals(SELECT_EXCHANGE_INFORMATION))
        {
            nextPage = selectExchangeInformation(appReqBlock);
        }
        else if (action.equals(CANCEL_CASH_BATCH_DETAIL))
        {
            nextPage = cancelCashBatchDetail(appReqBlock);
        }
        else if (action.equals(CLOSE_CASH_BATCH_DETAIL))
        {
            nextPage = closeCashBatchDetail(appReqBlock);
        }
        else if (action.equals(SAVE_SUSPENSE_FOR_CASH_BATCH))
        {
            nextPage = saveSuspenseForCashBatch(appReqBlock);
        }
        else if (action.equals(SHOW_SELECTED_CASH_BATCH))
        {
            nextPage = showSelectedCashBatch(appReqBlock);
        }
        else if (action.equals(SHOW_HEDGE_FUND_REDEMPTION))
        {
            nextPage = showHedgeFundRedemption(appReqBlock);
        }
        else if (action.equals(RETRIEVE_SUSPENSE_AND_TRANSACTIONS_FOR_HEDGE_FUND))
        {
            nextPage = retrieveSuspenseAndTransactionsForHedgeFund(appReqBlock);
        }
        else if (action.equals(SHOW_SELECTED_HEDGE_SUSPENSE))
        {
            nextPage = showSelectedHedgeSuspense(appReqBlock);
        }
        else if (action.equals(SHOW_SELECTED_EDIT_TRX_FOR_REDEMPTION))
        {
            nextPage = showSelectedEditTrxForRedemption(appReqBlock);
        }
        else if (action.equals(GENERATE_REDEMPTION_TRANSACTION))
        {
            nextPage = generateRedemptionTransaction(appReqBlock);
        }
        else if (action.equals(SHOW_DIVISION_LEVEL_FEES_DIALOG))
        {
            nextPage = showDivisionLevelFeesDialog(appReqBlock);
        }
        else if (action.equals(SAVE_FEE_AMOUNT_RECEIVED))
        {
            nextPage = saveFeeAmountReceivedToCloud(appReqBlock);
        }
        else if (action.equals(SAVE_FEE_TRX_AMOUNTS))
        {
            nextPage = createDFCASHTransactionAndUpdateFees(appReqBlock);
        }
        else if (action.equals(CLEAR_FEE_SESSION_PARAMS))
        {
            nextPage = clearFeeSessionParams(appReqBlock);
        }
        else
        {
            throw new Exception("Invalid Action/Transaction");
        }

        return nextPage;
    }

    protected String showTransactionPrioritySummary(AppReqBlock appReqBlock)
    {
        TransactionPriorityVO[] uiTransactionPriorityVOs = null;

        try
        {
            uiTransactionPriorityVOs = DAOFactory.getTransactionPriorityDAO().findAll();
        }
        catch (Exception e)
        {
          System.out.println(e);

            e.printStackTrace();
        }

        appReqBlock.getHttpServletRequest().setAttribute("uiTransactionPriorityVOs", uiTransactionPriorityVOs);

        return TRANSACTION_PRIORITY_SUMMARY;
    }

    protected String showTransactionPriorityDetails(AppReqBlock appReqBlock) throws Exception
    {
        String transactionPriorityPK = initParam(appReqBlock.getReqParm("transactionPriorityPK"), "0");

        Event eventComponent = new EventComponent();

        List voInclusionList = new ArrayList();
        voInclusionList.add(TransactionCorrespondenceVO.class);

        TransactionPriorityVO transactionPriorityVO = eventComponent.composeTransactionPriorityVOByPK(Long.parseLong(transactionPriorityPK), voInclusionList);

        appReqBlock.getHttpServletRequest().setAttribute("transactionPriorityVO", transactionPriorityVO);
        appReqBlock.getHttpServletRequest().setAttribute("pageMode", "ADD");

        return showTransactionPrioritySummary(appReqBlock);
    }

    protected String addTransactionPriority(AppReqBlock appReqBlock) throws Exception
    {
        appReqBlock.getHttpServletRequest().setAttribute("pageMode", "ADD");

        return showTransactionPrioritySummary(appReqBlock);
    }

    protected String saveTransactionPriority(AppReqBlock appReqBlock) throws Exception
    {
        String transactionTypePK = initParam(appReqBlock.getReqParm("transactionTypePK"), "0");
        String confirmPK         = initParam(appReqBlock.getReqParm("confirmPK"), "0");
        String commissionablePK  = initParam(appReqBlock.getReqParm("commissionablePK"), "0");
        String reinsurablePK     = initParam(appReqBlock.getReqParm("reinsurable"), "0");
        String bonusChargebackPK = initParam(appReqBlock.getReqParm("bonusChargebackPK"), "0");

        String transactionPriorityPK = initParam(appReqBlock.getReqParm("transactionPriorityPK"), "0");

        String effectiveMonth = initParam(appReqBlock.getReqParm("effectiveMonth"), null);
        String effectiveDay   = initParam(appReqBlock.getReqParm("effectiveDay"), null);
        String effectiveYear  = initParam(appReqBlock.getReqParm("effectiveYear"), null);

        String priority = initParam(appReqBlock.getReqParm("priority"), null);

        TransactionPriorityVO transactionPriorityVO = new TransactionPriorityVO();

        transactionPriorityVO.setTransactionPriorityPK(Long.parseLong(transactionPriorityPK));

        transactionPriorityVO.setEffectiveDate(DateTimeUtil.initDate(effectiveMonth, effectiveDay, effectiveYear, null));

        transactionPriorityVO.setPriority(Integer.parseInt(priority));

        CodeTableVO codeTableVO = CodeTableWrapper.getSingleton().getCodeTableEntry(Long.parseLong(transactionTypePK));
        transactionPriorityVO.setTransactionTypeCT(codeTableVO.getCode());

        if (Util.isANumber(commissionablePK) && !commissionablePK.equals("0"))
        {
            codeTableVO = CodeTableWrapper.getSingleton().getCodeTableEntry(Long.parseLong(commissionablePK));
            transactionPriorityVO.setCommissionableEventInd(codeTableVO.getCode());
        }

        if (Util.isANumber(reinsurablePK) && !reinsurablePK.equals("0"))
        {
            codeTableVO = CodeTableWrapper.getSingleton().getCodeTableEntry(Long.parseLong(reinsurablePK));
            transactionPriorityVO.setReinsuranceInd(codeTableVO.getCode());
        }

        if (Util.isANumber(confirmPK) && !confirmPK.equals("0"))
        {
            codeTableVO = CodeTableWrapper.getSingleton().getCodeTableEntry(Long.parseLong(confirmPK));
            transactionPriorityVO.setConfirmEventInd(codeTableVO.getCode());
        }
        
        if (Util.isANumber(bonusChargebackPK) && !bonusChargebackPK.equals("0"))
        {
            codeTableVO = CodeTableWrapper.getSingleton().getCodeTableEntry(Long.parseLong(bonusChargebackPK));
            transactionPriorityVO.setBonusChargebackCT(codeTableVO.getCode());
        }        

        // Save to database
        Event eventComponent = new EventComponent();
        eventComponent.saveTransactionPriority(transactionPriorityVO);

        appReqBlock.getHttpServletRequest().setAttribute("pageMode", "VIEW");

        return showTransactionPrioritySummary(appReqBlock);
    }

    protected String cancelTransactionPriorityEdits(AppReqBlock appReqBlock) throws Exception
    {
        appReqBlock.getHttpServletRequest().setAttribute("pageMode", "VIEW");

        return showTransactionPrioritySummary(appReqBlock);
    }

    protected String deleteTransactionPriority(AppReqBlock appReqBlock) throws Exception
    {
        String transactionPriorityPK = initParam(appReqBlock.getReqParm("transactionPriorityPK"), "0");

        if (transactionPriorityPK.equals("0"))
        {
            String message = "A Transaction Priority Must Be Selected";

            appReqBlock.getHttpServletRequest().setAttribute("message", message);
        }
        else
        {
            Event eventComponent = new EventComponent();

            try
            {
                eventComponent.deleteTransactionPriority(Long.parseLong(transactionPriorityPK));
                String message = "Transaction Priority Successfully Deleted";

                appReqBlock.getHttpServletRequest().setAttribute("message", message);
            }
            catch (EDITDeleteException e)
            {
                TransactionPriorityVO transactionPriorityVO = DAOFactory.getTransactionPriorityDAO().findByPK(Long.parseLong(transactionPriorityPK))[0];

                appReqBlock.getHttpServletRequest().setAttribute("message", e.getMessage());
                appReqBlock.getHttpServletRequest().setAttribute("transactionPriorityVO", transactionPriorityVO);
            }
        }

        appReqBlock.getHttpServletRequest().setAttribute("pageMode", "VIEW");

        return showTransactionPrioritySummary(appReqBlock);
    }

    protected String showTransactionCorrespondenceDialog(AppReqBlock appReqBlock)
    {
        String transactionPriorityPK = initParam(appReqBlock.getReqParm("transactionPriorityPK"), "0");

        TransactionCorrespondenceVO[] uiTransactionCorrespondenceVOs = null;

        if (transactionPriorityPK.equals("0"))
        {
            String message = "A Transaction Priority Must Be Selected";

            appReqBlock.getHttpServletRequest().setAttribute("message", message);
        }
        else
        {
            try
            {
                uiTransactionCorrespondenceVOs =
                        DAOFactory.getTransactionCorrespondenceDAO().findByTransactionPriorityPK(Long.parseLong(transactionPriorityPK));
            }
            catch (Exception e)
            {
                appReqBlock.getHttpServletRequest().setAttribute("message", e.getMessage());
            }
        }

        appReqBlock.getHttpServletRequest().setAttribute("uiTransactionCorrespondenceVOs", uiTransactionCorrespondenceVOs);
        appReqBlock.getHttpServletRequest().setAttribute("transactionPriorityPK", transactionPriorityPK);

        return TRANSACTION_CORRESPONDENCE_DIALOG;
    }


    protected String showTransactionCorrespondenceDetails(AppReqBlock appReqBlock) throws Exception
    {
        String transactionCorrespondencePK = initParam(appReqBlock.getReqParm("transactionCorrespondencePK"), "0");

        TransactionCorrespondenceVO transactionCorrespondenceVO =
                DAOFactory.getTransactionCorrespondenceDAO().findByPK(Long.parseLong(transactionCorrespondencePK))[0];

        appReqBlock.getHttpServletRequest().setAttribute("transactionCorrespondenceVO", transactionCorrespondenceVO);
        appReqBlock.getHttpServletRequest().setAttribute("pageMode", "VIEW");

        return showTransactionCorrespondenceDialog(appReqBlock);
    }

    protected String addTransactionCorrespondence(AppReqBlock appReqBlock)
    {
        appReqBlock.getHttpServletRequest().setAttribute("pageMode", "ADD");

        return showTransactionCorrespondenceDialog(appReqBlock);
    }

    protected String saveTransactionCorrespondence(AppReqBlock appReqBlock) throws Exception
    {
        String transactionPriorityPK = initParam(appReqBlock.getReqParm("transactionPriorityPK"), "0");
        String transactionCorrespondencePK = initParam(appReqBlock.getReqParm("transactionCorrespondencePK"), "0");
        String correspondenceTypePK = initParam(appReqBlock.getReqParm("correspondenceTypePK"), "0");
        String numberOfDays         = initParam(appReqBlock.getReqParm("numberOfDays"), "0");
        String priorPostPK          = initParam(appReqBlock.getReqParm("priorPostPK"), "0");
        String trxTypeQualifierPK = initParam(appReqBlock.getReqParm("trxTypeQualifierPK"), "");
        String includeHistoryIndicator = initParam(appReqBlock.getReqParm("includeHistoryIndicator"), "N");
        String startDate = initParam(appReqBlock.getReqParm("startDate"), null);
        String stopDate = initParam(appReqBlock.getReqParm("stopDate"), null);

        TransactionCorrespondenceVO transactionCorrespondenceVO = new TransactionCorrespondenceVO();

        transactionCorrespondenceVO.setTransactionCorrespondencePK(Long.parseLong(transactionCorrespondencePK));
        transactionCorrespondenceVO.setTransactionPriorityFK(Long.parseLong(transactionPriorityPK));
        transactionCorrespondenceVO.setNumberOfDays(Integer.parseInt(numberOfDays));

        String correspondenceTypeCT = CodeTableWrapper.getSingleton().getCodeTableEntry(Long.parseLong(correspondenceTypePK)).getCode();
        transactionCorrespondenceVO.setCorrespondenceTypeCT(correspondenceTypeCT);

        String priorPostCT = CodeTableWrapper.getSingleton().getCodeTableEntry(Long.parseLong(priorPostPK)).getCode();
        transactionCorrespondenceVO.setPriorPostCT(priorPostCT);

        if (Util.isANumber(trxTypeQualifierPK)  && !trxTypeQualifierPK.equals("0"))
        {
            String trxTypeQualifierCT = CodeTableWrapper.getSingleton().getCodeTableEntry(Long.parseLong(trxTypeQualifierPK)).getCode();
            transactionCorrespondenceVO.setTransactionTypeQualifierCT(trxTypeQualifierCT);
        }

        transactionCorrespondenceVO.setIncludeHistoryIndicator(includeHistoryIndicator);

        EDITDate startDateED = null;
        EDITDate stopDateED = null;
        String message = null;

        if (startDate != null || stopDate != null)
        {
            if (startDate == null)
            {
                startDateED = new EDITDate(EDITDate.DEFAULT_MIN_DATE);
            }
            else
            {
                startDateED = new EDITDate(startDate);
            }

            if (stopDate == null)
            {
                stopDateED = new EDITDate(EDITDate.DEFAULT_MAX_DATE);
                if (startDate != null)
                {
                    stopDate = EDITDate.DEFAULT_MAX_DATE;
                }
            }
            else
            {
                stopDateED = new EDITDate(stopDate);
            }

            if (stopDateED.before(startDateED))
            {
                message = "Stop Date is greater than Start Date";
                appReqBlock.getHttpServletRequest().setAttribute("message", message);
            }
            else
            {
                transactionCorrespondenceVO.setStartDate(startDate);
                transactionCorrespondenceVO.setStopDate(stopDate);

                // Save to database
                Event eventComponent = new EventComponent();
                eventComponent.createOrUpdateVO(transactionCorrespondenceVO, true);
            }
        }
        appReqBlock.getHttpServletRequest().setAttribute("pageMode", "VIEW");

        return showTransactionCorrespondenceDialog(appReqBlock);
    }

    protected String cancelTransactionCorrespondenceEdits(AppReqBlock appReqBlock)
    {
        appReqBlock.getHttpServletRequest().setAttribute("pageMode", "VIEW");

        return showTransactionCorrespondenceDialog(appReqBlock);
    }

    protected String deleteTransactionCorrespondence(AppReqBlock appReqBlock) throws Exception
    {
        String transactionCorrespondencePK = initParam(appReqBlock.getReqParm("transactionCorrespondencePK"), "0");

        if (transactionCorrespondencePK.equals("0"))
        {
            String message = "A Transaction Correspondence Must Be Selected";

            appReqBlock.getHttpServletRequest().setAttribute("message", message);
        }
        else
        {
            Event eventComponent = new EventComponent();

            try
            {
                String message = "";
                EDITTrxCorrespondenceVO[] editTrxCorrVO = eventComponent.composeEDITTrxCorrespondenceVOByTrxCorrPK(Long.parseLong(transactionCorrespondencePK));
                if (editTrxCorrVO != null && editTrxCorrVO.length > 0)
                {
                    message = "Transaction Correspondence Cannot Be Deleted - Correspondence Already Generated";
                }
                else
                {
                    eventComponent.deleteVO(TransactionCorrespondenceVO.class,  Long.parseLong(transactionCorrespondencePK), true);

                    message = "Transaction Correspondence Successfully Deleted";
                }

                appReqBlock.getHttpServletRequest().setAttribute("message", message);
            }
            catch (EDITDeleteException e)
            {
                TransactionCorrespondenceVO transactionCorrespondenceVO = DAOFactory.getTransactionCorrespondenceDAO().findByPK(Long.parseLong(transactionCorrespondencePK))[0];

                appReqBlock.getHttpServletRequest().setAttribute("message", e.getMessage());
                appReqBlock.getHttpServletRequest().setAttribute("transactionCorrespondenceVO", transactionCorrespondenceVO);
            }
        }

        appReqBlock.getHttpServletRequest().setAttribute("pageMode", "VIEW");

        return showTransactionCorrespondenceDialog(appReqBlock);
    }

    protected String closeTransactionCorrespondenceDialog(AppReqBlock appReqBlock)
    {
       appReqBlock.getHttpServletRequest().setAttribute("pageMode", "VIEW");

       return showTransactionPrioritySummary(appReqBlock);
    }

    protected String showCashBatchSummary(AppReqBlock appReqBlock) throws Exception
    {
        new CashBatchSummaryTableModel(appReqBlock);

        setFilterValuesInRequest(appReqBlock);

        return CASH_BATCH_SUMMARY;
    }

    protected String voidSelectedCashBatch(AppReqBlock appReqBlock) throws Exception
    {
        String cashBatchContractPK = new CashBatchSummaryTableModel(appReqBlock).getSelectedRowId();

        EventUseCase eventUseCaseComponent = new EventUseCaseComponent();

        UserSession userSession = appReqBlock.getUserSession();

        CashBatchContract cashBatchContract = CashBatchContract.findByPK(new Long(cashBatchContractPK));

        String responseMessage = eventUseCaseComponent.voidCashBatchContract(cashBatchContract);

        appReqBlock.getHttpServletRequest().setAttribute("responseMessage", responseMessage);

        return showCashBatchSummary(appReqBlock);
    }

    /**
     * Displays the Cash Batch Summary Page and invokes the Cash Batch Entry page
     * @param appReqBlock
     * @return
     * @throws Exception
     */
    protected String addCashBatchEntry(AppReqBlock appReqBlock) throws Exception
    {
        appReqBlock.getHttpServletRequest().setAttribute("action", "add");

        new CashBatchSummaryTableModel(appReqBlock).resetAllRows();

        setFilterValuesInRequest(appReqBlock);

        return CASH_BATCH_SUMMARY;
    }

    /**
     * Displays the CashBatchEntry page - used to add a new cash batch entry to the system.
     * @param appReqBlock
     * @return
     * @throws Exception
     */
    protected String showCashBatchEntry(AppReqBlock appReqBlock) throws Exception
    {
        ProductStructure[] productStructures = appReqBlock.getUserSession().getProductStucturesForUser();

        boolean viewAllSuspense = false;
        List companiesAllowed = new ArrayList();
        Long securityProductStructurePK = 0L;

        if (productStructures != null && productStructures.length > 0)
        {
            securityProductStructurePK = ProductStructure.checkForSecurityStructure(productStructures);

            companiesAllowed = ProductStructure.checkForAuthorizedCompanies(productStructures);
        }

        UserSession userSession = appReqBlock.getUserSession();
        if (securityProductStructurePK > 0)
        {
            Operator operator = Operator.findByOperatorName(userSession.getUsername());

            if (userSession.userLoggedIn())
            {
                viewAllSuspense = operator.checkViewAllAuthorization(securityProductStructurePK, "Suspense");
            }
            else
            {
                viewAllSuspense = true;
            }
        }

        String[] companies = null;

        if (viewAllSuspense)
        {
            companies = Company.find_All_CompanyNamesForProductType();
        }
        else
        {
            companies = (String[]) companiesAllowed.toArray(new String[companiesAllowed.size()]);
        }

        appReqBlock.getHttpServletRequest().setAttribute("companies", companies);

        String batchId = appReqBlock.getReqParm("batchId");

        CashBatchContract[] cashBatchContract = CashBatchContract.findByBatchId(batchId);

        if (cashBatchContract.length > 0)
        {
            appReqBlock.getHttpServletRequest().setAttribute("activeCashBatchContract", cashBatchContract);
        }

        new CashBatchDetailTableModel(null, null, appReqBlock);

        setFilterValuesInRequest(appReqBlock);

        return CASH_BATCH_ENTRY_DIALOG;
    }

    protected String showCashBatchFilter(AppReqBlock appReqBlock) throws Exception
    {
        setFilterValuesInRequest(appReqBlock);

        return CASH_BATCH_FILTER_DIALOG;
    }

    protected String filterCashBatch(AppReqBlock appReqBlock) throws Exception
    {
        new CashBatchSummaryTableModel(appReqBlock);

        setFilterValuesInRequest(appReqBlock);

        return CASH_BATCH_SUMMARY;
    }

    protected String createCashBatch(AppReqBlock appReqBlock) throws Exception
    {
        String company = appReqBlock.getReqParm("company");
        String groupNumber = appReqBlock.getReqParm("groupNumber");
        String dueDateString = appReqBlock.getReqParm("dueDate");
        String batchDateString = appReqBlock.getReqParm("batchDate");

        EDITDate dueDate = DateTimeUtil.getEDITDateFromMMDDYYYY(dueDateString);
        EDITDate batchDate = DateTimeUtil.getEDITDateFromMMDDYYYY(batchDateString);

        EDITBigDecimal batchAmount = new EDITBigDecimal(appReqBlock.getReqParm("amount"));

        appReqBlock.getHttpServletRequest().setAttribute("batchDate", batchDate);
        appReqBlock.getHttpServletRequest().setAttribute("batchAmount", batchAmount);

        EventUseCase eventUseCase = new event.component.EventUseCaseComponent();

        CashBatchContract cashBatchContract = eventUseCase.createCashBatch(batchDate, batchAmount,
                appReqBlock.getUserSession().getUsername(), company, groupNumber, dueDate);

        appReqBlock.getHttpServletRequest().setAttribute("activeCashBatchContract", cashBatchContract);
        appReqBlock.getHttpServletRequest().setAttribute("action", "create");
//
//        new CashBatchContractSummaryTableModel(appReqBlock);
//
//        setFilterValuesInRequest(appReqBlock);

        return showCashBatchSummary(appReqBlock);
    }

    protected String showCashBatchContractSummary(AppReqBlock appReqBlock) throws Exception
    {

        String selectedCashBatchContractPK = new CashBatchSummaryTableModel(appReqBlock).getSelectedRowId();
        if (selectedCashBatchContractPK == null)
        {
            selectedCashBatchContractPK = Util.initString((String)appReqBlock.getFormBean().getValue("cashBatchContractFK"), null);
        }

        appReqBlock.getHttpServletRequest().setAttribute("cashBatchContractFK", selectedCashBatchContractPK);

        String batchId = appReqBlock.getReqParm("batchId");

        UserSession userSession = appReqBlock.getUserSession();

        CashBatchContract[] cashBatchContract = CashBatchContract.findByBatchId(batchId);

        appReqBlock.getHttpServletRequest().setAttribute("activeCashBatchContract", cashBatchContract[0]);

        new CashBatchContractSummaryTableModel(appReqBlock);

        setFilterValuesInRequest(appReqBlock);

        appReqBlock.getHttpServletRequest().setAttribute("balanceRemaining", new EDITBigDecimal(appReqBlock.getFormBean().getValue("balanceRemaining")));

        return CASH_BATCH_CONTRACT_SUMMARY_DIALOG;
    }

    protected String cancelCashBatchContractSummary(AppReqBlock appReqBlock) throws Exception
    {
        return showCashBatchSummary(appReqBlock);
    }

    protected String viewCashBatchContractSummary(AppReqBlock appReqBlock) throws Exception
    {
        String batchId = appReqBlock.getReqParm("batchId");

        UserSession userSession = appReqBlock.getUserSession();

        CashBatchContract[] cashBatchContract = CashBatchContract.findByBatchId(batchId);

        appReqBlock.getHttpServletRequest().setAttribute("activeCashBatchContract", cashBatchContract[0]);

        appReqBlock.getHttpServletRequest().setAttribute("balanceRemaining", new EDITBigDecimal(appReqBlock.getReqParm("balancehRemaining")));
        new CashBatchContractSummaryTableModel(appReqBlock);

        setFilterValuesInRequest(appReqBlock);

        appReqBlock.getHttpServletRequest().setAttribute("viewMode", "view");

        return CASH_BATCH_CONTRACT_SUMMARY_DIALOG;
    }

    protected String deleteSelectedSuspense(AppReqBlock appReqBlock) throws Exception
    {
        String batchId = appReqBlock.getReqParm("batchId");
        String policyNumber = Util.initString(appReqBlock.getReqParm("policyNumber"), "");

        UserSession userSession = appReqBlock.getUserSession();

        CashBatchContract[] cashBatchContract = CashBatchContract.findByBatchId(batchId);

        appReqBlock.getHttpServletRequest().setAttribute("activeCashBatchContract", cashBatchContract[0]);
        appReqBlock.getHttpServletRequest().setAttribute("policyNumber", policyNumber);
        appReqBlock.setReqParm("activeCashBatchContractPK", cashBatchContract[0].getCashBatchContractPK().toString());

        String suspensePK = new CashBatchContractSummaryTableModel(cashBatchContract[0], appReqBlock).getSelectedRowId();

        Suspense suspense = null;

        if (suspensePK != null && Util.isANumber(suspensePK))
        {
            suspense = Suspense.findByPK(new Long(suspensePK));
        }

        EventUseCase eventUseCase = new event.component.EventUseCaseComponent();

        String responseMessage = eventUseCase.deleteCashBatchSuspense(suspense);

        appReqBlock.getHttpServletRequest().setAttribute("responseMessage", responseMessage);

        return showCashBatchContractSummary(appReqBlock);
    }

    protected String releaseCashBatch(AppReqBlock appReqBlock) throws Exception
    {
        String batchId = appReqBlock.getReqParm("batchId");

        UserSession userSession = appReqBlock.getUserSession();

        CashBatchContract[] cashBatchContract = CashBatchContract.findByBatchId(batchId);

        EventUseCase eventUseCase = new event.component.EventUseCaseComponent();

        String responseMessage = eventUseCase.releaseCashBatch(cashBatchContract[0]);

        appReqBlock.getHttpServletRequest().setAttribute("responseMessage", responseMessage);
        appReqBlock.setReqParm("activeCashBatchContractPK", cashBatchContract[0].getCashBatchContractPK().toString());

        if (responseMessage.endsWith("Released"))
        {
            appReqBlock.getHttpServletRequest().setAttribute("viewMode", "view");
        }

        return showCashBatchContractSummary(appReqBlock);
    }

    /**
     * Displays default CashBatchContractDetail page
     * @param appReqBlock
     * @return
     * @throws Exception
     */
    protected String addCashBatchContractDetail(AppReqBlock appReqBlock) throws Exception
    {
        String batchId = appReqBlock.getReqParm("batchId");

        UserSession userSession = appReqBlock.getUserSession();

        CashBatchContract[] cashBatchContract = CashBatchContract.findByBatchId(batchId);

        appReqBlock.getHttpServletRequest().setAttribute("activeCashBatchContract", cashBatchContract[0]);

        new CashBatchDetailTableModel(null, null, appReqBlock);

        getHedgeFilteredFunds(appReqBlock);

        setFilterValuesInRequest(appReqBlock);

        appReqBlock.getHttpServletRequest().setAttribute("balanceRemaining", new EDITBigDecimal(appReqBlock.getReqParm("balanceRemaining")));

        return CASH_BATCH_CONTRACT_DETAIL_DIALOG;
    }

    protected String showCashBatchContractDetail(AppReqBlock appReqBlock) throws Exception
    {
        String batchId = appReqBlock.getReqParm("batchId");
        String policyNumber = Util.initString(appReqBlock.getReqParm("policyNumber"), "");
        UserSession userSession = appReqBlock.getUserSession();

        CashBatchContract[] cashBatchContract = CashBatchContract.findByBatchId(batchId);

        appReqBlock.getHttpServletRequest().setAttribute("activeCashBatchContract", cashBatchContract[0]);
        appReqBlock.getHttpServletRequest().setAttribute("policyNumber", policyNumber);

        String suspensePK = new CashBatchContractSummaryTableModel(cashBatchContract[0], appReqBlock).getSelectedRowId();

        Suspense suspense = null;

        if (suspensePK != null && Util.isANumber(suspensePK))
        {
            suspense = Suspense.findByPK(new Long(suspensePK));
            appReqBlock.getHttpServletRequest().setAttribute("activeSuspense", suspense);
            Deposits[] deposits = Deposits.findBySuspenseFK(new Long(suspensePK));
            if (deposits != null && deposits.length > 0)
            {
                appReqBlock.getHttpServletRequest().setAttribute("depositAttached", "checked");
                appReqBlock.getHttpServletRequest().setAttribute("activeDeposit", deposits[0]);
            }
        }

        new CashBatchDetailTableModel(suspense, policyNumber, appReqBlock);

        getHedgeFilteredFunds(appReqBlock);

        setFilterValuesInRequest(appReqBlock);

        return CASH_BATCH_CONTRACT_DETAIL_DIALOG;
    }

    protected String viewCashBatchContractDetail(AppReqBlock appReqBlock) throws Exception
    {
        String batchId = appReqBlock.getReqParm("batchId");
        String policyNumber = Util.initString(appReqBlock.getReqParm("policyNumber"), "");
        UserSession userSession = appReqBlock.getUserSession();

        CashBatchContract[] cashBatchContract = CashBatchContract.findByBatchId(batchId);

        appReqBlock.getHttpServletRequest().setAttribute("activeCashBatchContract", cashBatchContract[0]);
        appReqBlock.getHttpServletRequest().setAttribute("policyNumber", policyNumber);

        String suspensePK = new CashBatchContractSummaryTableModel(cashBatchContract[0], appReqBlock).getSelectedRowId();

        Suspense suspense = null;

        if (suspensePK != null && Util.isANumber(suspensePK))
        {
            suspense = Suspense.findByPK(new Long(suspensePK));
            appReqBlock.getHttpServletRequest().setAttribute("activeSuspense", suspense);
            Deposits[] deposits = Deposits.findBySuspenseFK(new Long(suspensePK));
            if (deposits != null && deposits.length > 0)
            {
                appReqBlock.getHttpServletRequest().setAttribute("depositAttached", "checked");
                appReqBlock.getHttpServletRequest().setAttribute("activeDeposit", deposits[0]);
            }
        }

        new CashBatchDetailTableModel(suspense, policyNumber, appReqBlock);

        getHedgeFilteredFunds(appReqBlock);

        setFilterValuesInRequest(appReqBlock);

        appReqBlock.getHttpServletRequest().setAttribute("viewMode", "view");

        return CASH_BATCH_CONTRACT_DETAIL_DIALOG;
    }

    protected String getContractInfoForSuspense(AppReqBlock appReqBlock) throws Exception
    {
        String policyNumber = Util.initString(appReqBlock.getReqParm("policyNumber"), "");

        Segment segment = Segment.findByContractNumber(policyNumber);
        if (segment != null)
        {
            ClientDetail clientDetail = ClientDetail.findBy_Segment_RoleType(segment, "OWN");
            if (clientDetail != null)
            {
                if (clientDetail.getLastName() != null)
                {
                    appReqBlock.getHttpServletRequest().setAttribute("lastName", clientDetail.getLastName());
                    appReqBlock.getHttpServletRequest().setAttribute("firstName", clientDetail.getFirstName());
                }
                else if (clientDetail.getCorporateName() != null)
                {
                    appReqBlock.getHttpServletRequest().setAttribute("corporateName", clientDetail.getCorporateName());
                }
            }
        }
        appReqBlock.getHttpServletRequest().setAttribute("balanceRemaining", new EDITBigDecimal(appReqBlock.getReqParm("balanceRemaining")));

        return showCashBatchContractDetail(appReqBlock);
    }

    protected String selectExchangeInformation(AppReqBlock appReqBlock) throws Exception
    {
        String batchId = appReqBlock.getReqParm("batchId");
        String policyNumber = Util.initString(appReqBlock.getReqParm("policyNumber"), "");
        UserSession userSession = appReqBlock.getUserSession();

        CashBatchContract[] cashBatchContract = CashBatchContract.findByBatchId(batchId);

        appReqBlock.getHttpServletRequest().setAttribute("activeCashBatchContract", cashBatchContract[0]);
        appReqBlock.getHttpServletRequest().setAttribute("policyNumber", policyNumber);

        String suspensePK = new CashBatchContractSummaryTableModel(cashBatchContract[0], appReqBlock).getSelectedRowId();

        Suspense suspense = null;

        if (suspensePK != null && Util.isANumber(suspensePK))
        {
            suspense = Suspense.findByPK(new Long(suspensePK));
            appReqBlock.getHttpServletRequest().setAttribute("activeSuspense", suspense);
        }

        String depositsPK = new CashBatchDetailTableModel(suspense, policyNumber, appReqBlock).getSelectedRowId();

        Deposits deposits = null;

        if (Util.isANumber(depositsPK))
        {
            deposits = Deposits.findByPK(new Long(depositsPK));
            appReqBlock.getHttpServletRequest().setAttribute("activeDeposit", deposits);
        }

        getHedgeFilteredFunds(appReqBlock);

        setCashBatchDetailFieldsInAppReqBlock(appReqBlock);

        setFilterValuesInRequest(appReqBlock);

        return CASH_BATCH_CONTRACT_DETAIL_DIALOG;
    }

    protected String cancelCashBatchDetail(AppReqBlock appReqBlock) throws Exception
    {
        String cashBatchContractFK = appReqBlock.getReqParm("cashBatchContractFK");
        appReqBlock.setReqParm("activeCashBatchContractPK", cashBatchContractFK.toString());

        String viewMode = appReqBlock.getReqParm("viewMode");
        appReqBlock.getHttpServletRequest().setAttribute("viewMode", viewMode);

        appReqBlock.setReqParm("policyNumber", "");
        appReqBlock.getHttpServletRequest().setAttribute("balanceRemaining", new EDITBigDecimal(appReqBlock.getFormBean().getValue("balanceRemaining")));

        return showCashBatchContractDetail(appReqBlock);
    }

    protected String closeCashBatchDetail(AppReqBlock appReqBlock) throws Exception
    {
        String cashBatchContractFK = appReqBlock.getReqParm("cashBatchContractFK");
        appReqBlock.setReqParm("activeCashBatchContractPK", cashBatchContractFK.toString());

        String viewMode = appReqBlock.getReqParm("viewMode");
        appReqBlock.getHttpServletRequest().setAttribute("viewMode", viewMode);

        return showCashBatchContractSummary(appReqBlock);
    }

    protected String saveSuspenseForCashBatch(AppReqBlock appReqBlock) throws Exception
    {
        Long cashBatchContractFK = new Long(appReqBlock.getReqParm("cashBatchContractFK"));

        CashBatchContract cashBatchContract = CashBatchContract.findByPK(cashBatchContractFK);

        Long suspensePK = new Long(appReqBlock.getReqParm("suspensePK"));
        Long depositsPK = new Long(appReqBlock.getReqParm("depositsPK"));
        String batchId = appReqBlock.getReqParm("batchId");
        String policyNumber = Util.initString(appReqBlock.getReqParm("policyNumber"), appReqBlock.getReqParm("policyNumberWhenDisabled"));

        String effectiveMonth = appReqBlock.getReqParm("effectiveMonth");
        String effectiveDay = appReqBlock.getReqParm("effectiveDay");
        String effectiveYear = appReqBlock.getReqParm("effectiveYear");
        String effectiveDate = DateTimeUtil.initDate(effectiveMonth, effectiveDay, effectiveYear, null);

        String taxYear = appReqBlock.getReqParm("taxYear");
        String checkNumber = appReqBlock.getReqParm("checkNumber");
        String plannedUnplanned = appReqBlock.getReqParm("plannedUnplanned");
        if (Util.isANumber(plannedUnplanned))
        {
            plannedUnplanned = CodeTableWrapper.getSingleton().getCodeTableEntry(Long.parseLong(plannedUnplanned)).getCode();
        }
        else
        {
            plannedUnplanned = null;
        }

        String firstName = appReqBlock.getReqParm("firstName");
        String lastName = appReqBlock.getReqParm("lastName");
        String corporateName = appReqBlock.getReqParm("corporateName");
        String premiumType = appReqBlock.getReqParm("premiumType");
        if (Util.isANumber(premiumType))
        {
            premiumType = CodeTableWrapper.getSingleton().getCodeTableEntry(Long.parseLong(premiumType)).getCode();
        }
        else
        {
            premiumType = null;
        }

        String depositType = appReqBlock.getReqParm("depositType");
        if (Util.isANumber(depositType))
        {
            depositType = CodeTableWrapper.getSingleton().getCodeTableEntry(Long.parseLong(depositType)).getCode();
        }
        else
        {
            depositType = null;
        }

        String filteredFundFK = appReqBlock.getReqParm("filteredFundFK");
        String exchangeCompany = appReqBlock.getReqParm("exchangeCompany");
        String exchangePolicy = appReqBlock.getReqParm("exchangePolicy");

        EDITBigDecimal suspenseAmount = new EDITBigDecimal();
        EDITBigDecimal grossAmount = new EDITBigDecimal();
        EDITBigDecimal costBasis = new EDITBigDecimal();

        if (appReqBlock.getReqParm("suspenseAmount") != null && Util.isANumber(appReqBlock.getReqParm("suspenseAmount")))
        {
            suspenseAmount = new EDITBigDecimal(appReqBlock.getReqParm("suspenseAmount"));
        }
        else
        {
            suspenseAmount = new EDITBigDecimal(appReqBlock.getReqParm("suspenseAmountWhenDisabled"));
        }

        if (Util.isANumber(appReqBlock.getReqParm("grossAmount")))
        {
            grossAmount = new EDITBigDecimal(appReqBlock.getReqParm("grossAmount"));
        }

        if (Util.isANumber(appReqBlock.getReqParm("costBasis")))
        {
            costBasis = new EDITBigDecimal(appReqBlock.getReqParm("costBasis"));
        }

        Suspense suspense = Suspense.findByPK(suspensePK);
        if (suspense == null)
        {
            suspense = new Suspense();
        }
        suspense.setSuspensePK(suspensePK);
        suspense.setCashBatchContractFK(cashBatchContractFK);
        suspense.setUserDefNumber(policyNumber);
        suspense.setOriginalContractNumber(batchId);
        suspense.setEffectiveDate(new EDITDate(effectiveDate));
        suspense.setSuspenseAmount(suspenseAmount);
        suspense.setOriginalAmount(suspenseAmount);
        suspense.setTaxYear(Integer.parseInt(taxYear));
        if (plannedUnplanned != null)
        {
            suspense.setPlannedIndCT(plannedUnplanned);
        }

        if (premiumType != null)
        {
            suspense.setPremiumTypeCT(premiumType);
        }

        if (grossAmount.isGT(new EDITBigDecimal()))
        {
            suspense.setGrossAmount(grossAmount);
        }

        if (costBasis.isGT(new EDITBigDecimal()))
        {
            suspense.setCostBasis(costBasis);
        }

        if (!checkNumber.equals(""))
        {
            suspense.setCheckNumber(checkNumber);
        }

        if (!firstName.equals(""))
        {
            suspense.setFirstName(firstName);
        }

        if (!lastName.equals(""))
        {
            suspense.setLastName(lastName);
        }

        if (!exchangeCompany.equals(""))
        {
            suspense.setExchangeCompany(exchangeCompany);
        }

        if (!exchangePolicy.equals(""))
        {
            suspense.setExchangePolicy(exchangePolicy);
        }

        if (depositType != null)
        {
            suspense.setDepositTypeCT(depositType);
        }

        if (!corporateName.equals(""))
        {
            suspense.setCorporateName(corporateName);
        }

        if (Util.isANumber(filteredFundFK) && !filteredFundFK.equals("0"))
        {
            suspense.setFilteredFundFK(new Long(filteredFundFK));
        }

        UserSession userSession = appReqBlock.getUserSession();

        suspense.setOperator(userSession.getUsername());
        suspense.setMaintenanceInd("M");
        suspense.setMaintDateTime(new EDITDateTime());
        suspense.setSuspenseType(Suspense.SUSPENSETYPE_CONTRACT);
        if (Util.isANumber(filteredFundFK) && Long.parseLong(filteredFundFK) > 0)
        {
            suspense.setSuspenseType(Suspense.SUSPENSETYPE_REDEMPTION);
        }
        suspense.setDirectionCT("Apply");
        suspense.setAccountingPendingInd("N");
        suspense.setContractPlacedFrom("Batch");
        suspense.setCompanyFK(cashBatchContract.getCompanyFK());

        suspense.setCashBatchContract(cashBatchContract);

        EventUseCase eventUseCase = new event.component.EventUseCaseComponent();

        String responseMessage = eventUseCase.saveSuspenseForCashBatch(suspense, depositsPK);

        appReqBlock.setReqParm("activeCashBatchContractPK", cashBatchContractFK.toString());
        appReqBlock.getHttpServletRequest().setAttribute("responseMessage", responseMessage);
        appReqBlock.setReqParm("policyNumber", "");

        EDITBigDecimal balanceRemaining = new EDITBigDecimal(appReqBlock.getReqParm("balanceRemaining"));
        balanceRemaining = balanceRemaining.addEditBigDecimal(suspenseAmount);
        appReqBlock.getHttpServletRequest().setAttribute("balanceRemaining", balanceRemaining);

        return showCashBatchContractDetail(appReqBlock);
    }

    private double performRoundingOnBatchTotal(double batchTotal) throws Exception
    {
        DecimalFormat twoDecPlcFormatter = new DecimalFormat("0.##");

        String roundedField = new String(twoDecPlcFormatter.format(batchTotal));

        return Double.parseDouble(roundedField);
    }

    protected String showSelectedCashBatch(AppReqBlock appReqBlock) throws Exception
    {
        String selectedCashBatchContractPK = new CashBatchSummaryTableModel(appReqBlock).getSelectedRowId();

        UserSession userSession = appReqBlock.getUserSession();

        CashBatchContract cashBatchContract = CashBatchContract.findByPK(new Long(selectedCashBatchContractPK));

        appReqBlock.getHttpServletRequest().setAttribute("activeCashBatchContract", cashBatchContract);

        setFilterValuesInRequest(appReqBlock);

        return CASH_BATCH_SUMMARY;
    }

    private SuspenseVO[] getCashBatchSuspenseVOs() throws Exception
    {
        event.business.Event eventComponent = new event.component.EventComponent();

        List voInclusionList = new ArrayList();
        voInclusionList.add(CashBatchContractVO.class);

        return eventComponent.composeCashBatchSuspenseVO(voInclusionList);
    }

    /**
     * Displays the Hedge Fund Redemption Page
     * @param appReqBlock
     * @return
     * @throws Exception
     */
    protected String showHedgeFundRedemption(AppReqBlock appReqBlock) throws Exception
    {
        appReqBlock.getHttpSession().removeAttribute("hedgeSuspenseVOs");
        appReqBlock.getHttpSession().removeAttribute("hedgeEditTrxVOs");

        engine.business.Lookup engineLookup = new engine.component.LookupComponent();
        FilteredFundVO[] filteredFundVO =  engineLookup.getAllHedgeFunds();
        if (filteredFundVO != null)
        {
            appReqBlock.getHttpSession().setAttribute("hedgeFilteredFundVOs", filteredFundVO);
        }

        return HEDGE_FUND_REDEMPTION;
    }

    /**
     * Retrieves the Suspense and EDITTrx records that utilize the selected FilteredFund
     * @param appReqBlock
     * @return
     * @throws Exception
     */
    protected String retrieveSuspenseAndTransactionsForHedgeFund(AppReqBlock appReqBlock) throws Exception
    {
        String filteredFundFK = appReqBlock.getFormBean().getValue("filteredFundFK");
        if (!Util.isANumber(filteredFundFK))
        {
            filteredFundFK = "0";
        }

        appReqBlock.getHttpServletRequest().setAttribute("selectedFilteredFundFK", filteredFundFK);

        event.business.Event eventComponent = new event.component.EventComponent();

        appReqBlock.getHttpSession().removeAttribute("hedgeSuspenseVOs");
        //retrieve all suspense records with filteredFundFK = filteredFund selected on redemption page
        SuspenseVO[] suspenseVOs = eventComponent.composeSuspenseVOByFilteredFundFK(Long.parseLong(filteredFundFK), new ArrayList());
        if (suspenseVOs != null)
        {
            appReqBlock.getHttpSession().setAttribute("hedgeSuspenseVOs", suspenseVOs);
        }

        List voInclusionList = new ArrayList();
        voInclusionList.add(ClientSetupVO.class);
        voInclusionList.add(ContractSetupVO.class);
        voInclusionList.add(SegmentVO.class);

        appReqBlock.getHttpSession().removeAttribute("hedgeEditTrxVOs");
        //retrieve all HFTA/HFTP records with overrides that utilize the filteredFund selected on the redemption page
        EDITTrxVO[] editTrxVOs = eventComponent.composeEditTrxVOByTrxTypeAndFilteredFundFK(Long.parseLong(filteredFundFK), voInclusionList);
        if (editTrxVOs != null)
        {
            appReqBlock.getHttpSession().setAttribute("hedgeEditTrxVOs", editTrxVOs);
        }

        appReqBlock.getHttpSession().setAttribute("feeAmountsMapByFeeType", new HashMap());
        appReqBlock.getHttpSession().setAttribute("feeAmountsMapByFeePK", new HashMap());
        appReqBlock.getHttpSession().setAttribute("isFeeAmountsEntered", new Boolean(false));

        return HEDGE_FUND_REDEMPTION;
    }

    /**
     * Populates the Hedge Fund Redemption Page (detail portion) with information from the selected suspense record
     * @param appReqBlock
     * @return
     * @throws Exception
     */
    protected String showSelectedHedgeSuspense(AppReqBlock appReqBlock) throws Exception
    {
        String filteredFundFK = appReqBlock.getFormBean().getValue("filteredFundFK");
        appReqBlock.getHttpServletRequest().setAttribute("selectedFilteredFundFK", filteredFundFK);

        String selectedSuspensePK = appReqBlock.getReqParm("selectedSuspensePK");
        appReqBlock.getHttpServletRequest().setAttribute("selectedSuspensePK", selectedSuspensePK);

        String selectedOrigTrxFK = appReqBlock.getReqParm("selectedOrigTrxFK");
        appReqBlock.getHttpServletRequest().setAttribute("selectedOrigTrxFK", selectedOrigTrxFK);

        String selectedEditTrxPK = appReqBlock.getReqParm("selectedEditTrxPK");
        if (!selectedEditTrxPK.equals(""))
        {
            appReqBlock.getHttpServletRequest().setAttribute("selectedEditTrxPK", selectedEditTrxPK);
        }

        return HEDGE_FUND_REDEMPTION;
    }

    /**
     * Populates the Hedge Fund Redemption Page (detail portion) with information from the selected transaction
     * @param appReqBlock
     * @return
     * @throws Exception
     */
    protected String showSelectedEditTrxForRedemption(AppReqBlock appReqBlock) throws Exception
    {
        String filteredFundFK = appReqBlock.getFormBean().getValue("filteredFundFK");
        appReqBlock.getHttpServletRequest().setAttribute("selectedFilteredFundFK", filteredFundFK);

        String selectedEditTrxPK = appReqBlock.getReqParm("selectedEditTrxPK");
        appReqBlock.getHttpServletRequest().setAttribute("selectedEditTrxPK", selectedEditTrxPK);

        String selectedOrigTrxFK = appReqBlock.getReqParm("selectedOrigTrxFK");
        appReqBlock.getHttpServletRequest().setAttribute("selectedOrigTrxFK", selectedOrigTrxFK);

        String selectedSuspensePK = appReqBlock.getReqParm("selectedSuspensePK");
        if (!selectedSuspensePK.equals(""))
        {
            appReqBlock.getHttpServletRequest().setAttribute("selectedSuspensePK", selectedSuspensePK);
        }

        return HEDGE_FUND_REDEMPTION;
    }

    /**
     * Generates the appropriate HDTH, HFTA, or HREM transaction (what is generated is based on the
     * originating transaction
     * @param appReqBlock
     * @return
     * @throws Exception
     */
    protected String generateRedemptionTransaction(AppReqBlock appReqBlock) throws Exception
    {
        String selectedEditTrxPK = appReqBlock.getReqParm("selectedEditTrxPK");

        //Retrieve the HFTA/HFTP trx so we can edit the entered effective date (it cannot be < the HFTA/HFTP date)
        EDITTrx selectedEditTrx = EDITTrx.findByPK(new Long(selectedEditTrxPK));

        String effectiveMonth = appReqBlock.getReqParm("effectiveMonth");
        String effectiveDay = appReqBlock.getReqParm("effectiveDay");
        String effectiveYear = appReqBlock.getReqParm("effectiveYear");

        EDITDate effectiveDate = null;

        String trxAmount = appReqBlock.getReqParm("trxAmount");

        if (effectiveMonth.equals("") ||
            effectiveDay.equals("") ||
            effectiveYear.equals(""))
        {
            effectiveDate = new EDITDate();
        }
        else
        {
            effectiveDate = new EDITDate(effectiveYear, effectiveMonth, effectiveDay);
        }

        String errorMessage = "";

        EDITDate redemptionEffDate = new EDITDate(effectiveDate);
        EDITDate hftEffDate = selectedEditTrx.getEffectiveDate();
        if (redemptionEffDate.before(hftEffDate))
        {
            errorMessage = "Entered Effective Date Cannot Be Less Than HFT Date";
        }

        if (errorMessage.equals(""))
        {
            String selectedSuspensePK = appReqBlock.getReqParm("selectedSuspensePK");
            Suspense suspense = new Suspense(Long.parseLong(selectedSuspensePK));

            EDITBigDecimal suspenseAmount = suspense.getSuspenseAmount();
            EDITBigDecimal pendingAmount = suspense.getPendingSuspenseAmount();
            EDITBigDecimal availableAmount = suspenseAmount.subtractEditBigDecimal(pendingAmount);

            if (new EDITBigDecimal(trxAmount).isGT(availableAmount))
            {
                errorMessage = "Not Enough Available Suspense for Entered Transaction Amount";
            }

            if (errorMessage.equals(""))
            {
                String selectedFilteredFundPK = appReqBlock.getFormBean().getValue("filteredFundFK");
                String selectedOrigTrxFK = appReqBlock.getReqParm("selectedOrigTrxFK");
                String operator = (String) appReqBlock.getHttpSession().getAttribute("userId");

                EDITTrx editTrx = new EDITTrx(Long.parseLong(selectedOrigTrxFK), Long.parseLong(selectedEditTrxPK), operator);

                editTrx.generateRedemptionTrx(redemptionEffDate, trxAmount, Long.parseLong(selectedSuspensePK),
                                              Long.parseLong(selectedFilteredFundPK), hftEffDate);
            }
        }

        if (!errorMessage.equals(""))
        {
            appReqBlock.getHttpServletRequest().setAttribute("errorMessage", errorMessage);
            appReqBlock.getHttpServletRequest().setAttribute("selectedFilteredFundFK", appReqBlock.getReqParm("selectedFilteredFundFK"));
            appReqBlock.getHttpServletRequest().setAttribute("selectedSuspensePK", appReqBlock.getReqParm("selectedSuspensePK"));
            appReqBlock.getHttpServletRequest().setAttribute("selectedEditTrxPK", selectedEditTrxPK);
            appReqBlock.getHttpServletRequest().setAttribute("selectedOrigTrxFK", appReqBlock.getReqParm("selectedOrigTrxFK"));
            appReqBlock.getHttpServletRequest().setAttribute("trxAmount", trxAmount);
            appReqBlock.getHttpServletRequest().setAttribute("effectiveMonth", effectiveMonth);
            appReqBlock.getHttpServletRequest().setAttribute("effectiveDay", effectiveDay);
            appReqBlock.getHttpServletRequest().setAttribute("effectiveYear", effectiveYear);
        }

        return retrieveSuspenseAndTransactionsForHedgeFund(appReqBlock);
    }

    /**
     * Displays the Division Level Fees Dialog (from the Hedge Fund Redemption page)
     * @param appReqBlock
     * @return
     */
    protected String showDivisionLevelFeesDialog(AppReqBlock appReqBlock)
    {
        String selectedFilteredFundFK = appReqBlock.getReqParm("selectedFilteredFundFK");
        String selectedFeeTrxPK = appReqBlock.getReqParm("selectedFeeTrxPK");
        String selectedSuspensePK = appReqBlock.getReqParm("selectedSuspensePK");

        String effectiveMonth = Util.initString(appReqBlock.getReqParm("effectiveMonth"), null);
        String effectiveDay = Util.initString(appReqBlock.getReqParm("effectiveDay"), null);
        String effectiveYear = Util.initString(appReqBlock.getReqParm("effectiveYear"), null);
        String effectiveDateStr = DateTimeUtil.initDate(effectiveMonth, effectiveDay, effectiveYear, null);

        engine.business.Lookup engineLookup = new engine.component.LookupComponent();

        FeeVO feeVO = null;
        if (selectedFeeTrxPK != null)
        {
            feeVO = engineLookup.findFeeBy_FeePK(Long.parseLong(selectedFeeTrxPK));
        }

        FeeVO[] feeVOs = engineLookup.findFeeBy_FilteredFundPK_And_PricingTypeCT_And_FeeRedemption_And_TrxTypeCT
                (Long.parseLong(selectedFilteredFundFK), Fee.HEDGEFUND_PRICING_TYPE, "Y", Fee.DIVISION_FEE_ACCRUAL_TRX_TYPE);

        appReqBlock.getHttpServletRequest().setAttribute("feeVO", feeVO);
        appReqBlock.getHttpServletRequest().setAttribute("hedgeFeeTrxVOs", feeVOs);
        appReqBlock.getHttpServletRequest().setAttribute("selectedFilteredFundFK", selectedFilteredFundFK);
        appReqBlock.getHttpServletRequest().setAttribute("selectedSuspensePK", selectedSuspensePK);
        appReqBlock.getHttpServletRequest().setAttribute("dfcashTrxEffectiveDate", effectiveDateStr);

        return DIVISION_LEVEL_FEES_DIALOG;
    }

    protected String saveFeeAmountReceivedToCloud(AppReqBlock appReqBlock)
    {
        String responseMessage = null;

        String feeAmount = Util.initString(appReqBlock.getReqParm("amount"), null);
        String selectedFeeDescriptionFK = appReqBlock.getReqParm("selectedFeeDescriptionFK");
        String selectedFeeTrxPK = appReqBlock.getReqParm("selectedFeeTrxPK");
        String chargeCodeFK = appReqBlock.getReqParm("selectedChargeCodeFK");

        Lookup engineLookup = new engine.component.LookupComponent();

        FeeDescriptionVO feeDescriptionVO = engineLookup.
                findFeeDescriptionBy_FeeDescriptionPK(Long.parseLong(selectedFeeDescriptionFK));

        String feeTypeCT = null;
        if (feeDescriptionVO != null)
        {
            feeTypeCT = feeDescriptionVO.getFeeTypeCT();
        }

        // To hold the values of feeAmounts by feeType.
        Map feeAmountsMapByFeeType = (HashMap) appReqBlock.getHttpSession().getAttribute("feeAmountsMapByFeeType");

        String key = feeTypeCT + "|" + chargeCodeFK;

        if (feeTypeCT != null)
        {
            if (feeAmountsMapByFeeType.containsKey(key))
            {
                EDITBigDecimal currentFeeAmount = (EDITBigDecimal) feeAmountsMapByFeeType.get(key);
                EDITBigDecimal updatedFeeAmount = currentFeeAmount.addEditBigDecimal(feeAmount);
                feeAmountsMapByFeeType.put(key, updatedFeeAmount);
            }
            else
            {
                feeAmountsMapByFeeType.put(key, new EDITBigDecimal(feeAmount));
            }
            appReqBlock.getHttpSession().setAttribute("isFeeAmountsEntered", new Boolean(true));
        }

        // To hold the values of fee amounts by feePK.
        Map feeAmountsMapByFeePK = (HashMap) appReqBlock.getHttpSession().getAttribute("feeAmountsMapByFeePK");
        feeAmountsMapByFeePK.put(selectedFeeTrxPK+"", new EDITBigDecimal(feeAmount));

        appReqBlock.getHttpServletRequest().setAttribute("responseMessage", responseMessage);

        appReqBlock.getHttpSession().setAttribute("feeAmountsMapByFeeType", feeAmountsMapByFeeType);
        appReqBlock.getHttpSession().setAttribute("feeAmountsMapByFeePK", feeAmountsMapByFeePK);

        appReqBlock.setReqParm("selectedFeeTrxPK", null);

        return showDivisionLevelFeesDialog(appReqBlock);
    }

    protected String createDFCASHTransactionAndUpdateFees(AppReqBlock appReqBlock) throws Exception
    {
        String effectiveMonth = Util.initString(appReqBlock.getReqParm("effectiveMonth"), null);
        String effectiveDay = Util.initString(appReqBlock.getReqParm("effectiveDay"), null);
        String effectiveYear = Util.initString(appReqBlock.getReqParm("effectiveYear"), null);
        EDITDate effectiveDate = DateTimeUtil.initDate(effectiveMonth, effectiveDay, effectiveYear);

        Map feeAmountsMapByFeeType = (HashMap) appReqBlock.getHttpSession().getAttribute("feeAmountsMapByFeeType");
        Map feeAmountsMapByFeePK = (HashMap) appReqBlock.getHttpSession().getAttribute("feeAmountsMapByFeePK");

        EDITBigDecimal trxAmount = new EDITBigDecimal();

        if (feeAmountsMapByFeeType != null)
        {
            String key = null;

            Iterator iterator = feeAmountsMapByFeeType.keySet().iterator();
            while(iterator.hasNext())
            {
                key = (String) iterator.next();
                trxAmount = trxAmount.addEditBigDecimal((EDITBigDecimal) feeAmountsMapByFeeType.get(key));
            }
        }

        String selectedFilteredFundFK = appReqBlock.getReqParm("selectedFilteredFundFK");
        String selectedSuspensePK = appReqBlock.getReqParm("selectedSuspensePK");
        Suspense suspense = new Suspense(Long.parseLong(selectedSuspensePK));

        EDITBigDecimal suspenseAmount = suspense.getSuspenseAmount();
        EDITBigDecimal pendingAmount = suspense.getPendingSuspenseAmount();
        EDITBigDecimal availableAmount = suspenseAmount.subtractEditBigDecimal(pendingAmount);

        String errorMessage = null;

        if (trxAmount.isGT(availableAmount))
        {
            errorMessage = "Not Enough Available Suspense for Entered Transaction Amount";
        }

        if (errorMessage == null)
        {
            engine.business.Calculator calculatorComponent = new engine.component.CalculatorComponent();
            engine.business.Lookup lookupComponent = new engine.component.LookupComponent();

            List feeVOList = new ArrayList();

            if (feeAmountsMapByFeeType != null)
            {
                FeeVO feeVO = null;
                String feeTypeCT = null;
                String chargeCodeFK = null;
                EDITBigDecimal feeAmount = new EDITBigDecimal();
                FeeDescriptionVO feeDescriptionVO = null;
                String key = null;

                Iterator iterator = feeAmountsMapByFeeType.keySet().iterator();
                while(iterator.hasNext())
                {
                    feeVO = new FeeVO();
                    key = (String) iterator.next();
                    feeTypeCT = Util.fastTokenizer(key, "|")[0];
                    chargeCodeFK = Util.fastTokenizer(key, "|")[1];
                    feeDescriptionVO = lookupComponent.
                            findFeeDescriptionBy_FilteredFundPK_And_FeeTypeCT(Long.parseLong(selectedFilteredFundFK), feeTypeCT);

                    feeAmount = (EDITBigDecimal) feeAmountsMapByFeeType.get(key);
                    feeVO.setFeeDescriptionFK(feeDescriptionVO.getFeeDescriptionPK());
                    feeVO.setEffectiveDate(effectiveDate.getFormattedDate());
                    feeVO.setTrxAmount(feeAmount.getBigDecimal());
                    feeVO.setChargeCodeFK(Long.parseLong(chargeCodeFK));
                    feeVO.setToFromInd("F");
                    feeVOList.add(feeVO);
                }
            }

            FeeVO[] feeVOs = (FeeVO[]) feeVOList.toArray(new FeeVO[feeVOList.size()]);

            try
            {
                calculatorComponent.createDFCASHFeeTransactionAndUpdateFees(feeVOs,
                                                                            feeAmountsMapByFeePK,
                                                                            Long.parseLong(selectedFilteredFundFK),
                                                                            Long.parseLong(selectedSuspensePK));
            }
            catch (EDITEngineException e)
            {
                System.out.println(e);

                e.printStackTrace();
            }

            appReqBlock.getHttpSession().setAttribute("feeAmountsMapByFeeType", feeAmountsMapByFeeType);
            appReqBlock.getHttpSession().setAttribute("feeAmountsMapByFeePK", feeAmountsMapByFeePK);
            appReqBlock.getHttpSession().setAttribute("isFeeAmountsEntered", new Boolean(false));

            appReqBlock.setReqParm("selectedFeeTrxPK", null);
        }
        else
        {
            appReqBlock.getHttpServletRequest().setAttribute("errorMessage", errorMessage);
            appReqBlock.getHttpServletRequest().setAttribute("dfcashTrxEffectiveDate", effectiveDate.getFormattedDate());
        }

        feeAmountsMapByFeeType.clear();
        feeAmountsMapByFeePK.clear();

        return showDivisionLevelFeesDialog(appReqBlock);
    }

    protected String clearFeeSessionParams(AppReqBlock appReqBlock) throws Exception
    {
        String filteredFundFK = appReqBlock.getReqParm("selectedFilteredFundFK");

        appReqBlock.getFormBean().putValue("filteredFundFK", filteredFundFK);

        return retrieveSuspenseAndTransactionsForHedgeFund(appReqBlock);
    }

    private void getHedgeFilteredFunds(AppReqBlock appReqBlock) throws Exception
    {
        engine.business.Lookup engineLookup = new engine.component.LookupComponent();
        FilteredFundVO[] filteredFundVO =  engineLookup.getAllHedgeFunds();
        if (filteredFundVO != null)
        {
            appReqBlock.getHttpSession().setAttribute("hedgeFilteredFundVOs", filteredFundVO);
        }
    }

    private void setCashBatchDetailFieldsInAppReqBlock(AppReqBlock appReqBlock)
    {
        Long suspensePK = new Long(appReqBlock.getReqParm("suspensePK"));
        String policyNumber = appReqBlock.getReqParm("policyNumber");
        String effectiveMonth = appReqBlock.getReqParm("effectiveMonth");
        String effectiveDay = appReqBlock.getReqParm("effectiveDay");
        String effectiveYear = appReqBlock.getReqParm("effectiveYear");
        String taxYear = appReqBlock.getReqParm("taxYear");
        String checkNumber = appReqBlock.getReqParm("checkNumber");
        String plannedUnplanned = appReqBlock.getReqParm("plannedUnplanned");
        if (Util.isANumber(plannedUnplanned))
        {
            plannedUnplanned = CodeTableWrapper.getSingleton().getCodeTableEntry(Long.parseLong(plannedUnplanned)).getCode();
        }
        else
        {
            plannedUnplanned = null;
        }

        String firstName = appReqBlock.getReqParm("firstName");
        String lastName = appReqBlock.getReqParm("lastName");
        String corporateName = appReqBlock.getReqParm("corporateName");
        String premiumType = appReqBlock.getReqParm("premiumType");
        if (Util.isANumber(premiumType))
        {
            premiumType = CodeTableWrapper.getSingleton().getCodeTableEntry(Long.parseLong(premiumType)).getCode();
        }
        else
        {
            premiumType = null;
        }

        String depositType = appReqBlock.getReqParm("depositType");
        if (Util.isANumber(depositType))
        {
            depositType = CodeTableWrapper.getSingleton().getCodeTableEntry(Long.parseLong(depositType)).getCode();
        }
        else
        {
            depositType = null;
        }

        String exchangeCompany = appReqBlock.getReqParm("exchangeCompany");
        String exchangePolicy = appReqBlock.getReqParm("exchangePolicy");

        EDITBigDecimal suspenseAmount = new EDITBigDecimal();
        EDITBigDecimal grossAmount = new EDITBigDecimal();
        EDITBigDecimal costBasis = new EDITBigDecimal();

        if (Util.isANumber(appReqBlock.getReqParm("suspenseAmount")))
        {
            suspenseAmount = new EDITBigDecimal(appReqBlock.getReqParm("suspenseAmount"));
        }

        if (Util.isANumber(appReqBlock.getReqParm("grossAmount")))
        {
            grossAmount = new EDITBigDecimal(appReqBlock.getReqParm("grossAmount"));
        }

        if (Util.isANumber(appReqBlock.getReqParm("costBasis")))
        {
            costBasis = new EDITBigDecimal(appReqBlock.getReqParm("costBasis"));
        }

        appReqBlock.getHttpServletRequest().setAttribute("suspensePK", suspensePK.toString());
        appReqBlock.getHttpServletRequest().setAttribute("policyNumber", policyNumber);
        appReqBlock.getHttpServletRequest().setAttribute("effectiveMonth", effectiveMonth);
        appReqBlock.getHttpServletRequest().setAttribute("effectiveDay", effectiveDay);
        appReqBlock.getHttpServletRequest().setAttribute("effectiveYear", effectiveYear);
        appReqBlock.getHttpServletRequest().setAttribute("taxYear", taxYear);
        appReqBlock.getHttpServletRequest().setAttribute("checkNumber", checkNumber);
        appReqBlock.getHttpServletRequest().setAttribute("plannedUnplanned", Util.initString(plannedUnplanned, ""));
        appReqBlock.getHttpServletRequest().setAttribute("firstName", firstName);
        appReqBlock.getHttpServletRequest().setAttribute("lastName", lastName);
        appReqBlock.getHttpServletRequest().setAttribute("corporateName", corporateName);
        appReqBlock.getHttpServletRequest().setAttribute("premiumType", Util.initString(premiumType, ""));
        appReqBlock.getHttpServletRequest().setAttribute("depositType", Util.initString(depositType, ""));
        appReqBlock.getHttpServletRequest().setAttribute("exchangeCompany", exchangeCompany);
        appReqBlock.getHttpServletRequest().setAttribute("exchangePolicy", exchangePolicy);
        appReqBlock.getHttpServletRequest().setAttribute("suspenseAmount", suspenseAmount.toString());
        appReqBlock.getHttpServletRequest().setAttribute("grossAmount", grossAmount.toString());
        appReqBlock.getHttpServletRequest().setAttribute("costBasis", costBasis.toString());
    }

    private void setFilterValuesInRequest(AppReqBlock appReqBlock)
    {
        String status = Util.initString(appReqBlock.getReqParm("status"), "");
        String month = Util.initString(appReqBlock.getReqParm("month"), "");
        String day = Util.initString(appReqBlock.getReqParm("day"), "");
        String year = Util.initString(appReqBlock.getReqParm("year"), "");
        String filterAmount = Util.initString(appReqBlock.getReqParm("filterAmount"), "");
        String filterOperator = Util.initString(appReqBlock.getReqParm("filterOperator"), "");

        appReqBlock.getHttpServletRequest().setAttribute("status", status);
        appReqBlock.getHttpServletRequest().setAttribute("month", month);
        appReqBlock.getHttpServletRequest().setAttribute("day", day);
        appReqBlock.getHttpServletRequest().setAttribute("year", year);
        appReqBlock.getHttpServletRequest().setAttribute("filterAmount", filterAmount);
        appReqBlock.getHttpServletRequest().setAttribute("filterOperator", filterOperator);
    }
}
