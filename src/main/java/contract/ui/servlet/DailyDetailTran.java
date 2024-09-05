/*
 * User: unknown
 * Date: Jan 1, 2000
 * Time: unknown
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package contract.ui.servlet;

import accounting.business.Accounting;

import batch.business.Batch;

import batch.component.BatchComponent;
import batch.component.BatchUseCaseComponent;

import contract.Segment;

import contract.business.Contract;

import contract.dm.dao.FastDAO;

import contract.interfaces.PayoutTrxRptInterfaceCmd;
import contract.interfaces.WithholdingRptInterfaceCmd;

import contract.report.TransferMemorandum;

import edit.common.EDITDate;
import edit.common.exceptions.EDITCaseException;
import edit.common.vo.AccountingDetailVO;
import edit.common.vo.BatchStatusVO;
import edit.common.vo.ClientSetupVO;
import edit.common.vo.ContractSetupVO;
import edit.common.vo.EDITServicesConfig;
import edit.common.vo.EDITTrxHistoryVO;
import edit.common.vo.EDITTrxVO;
import edit.common.vo.FilteredFundVO;
import edit.common.vo.FundVO;
import edit.common.vo.ProductStructureVO;
import edit.common.vo.SegmentVO;
import edit.common.vo.SeparateAccountValuesReportVO;
import edit.common.vo.SeparateAcctValByCaseVO;

import edit.portal.common.session.UserSession;
import edit.portal.common.transactions.Transaction;
import edit.portal.widget.FundActivityFundNameTableModel;
import edit.portal.widget.FundActivityFundNameDoubleTableModel;

import edit.services.config.ServicesConfig;
import edit.services.db.ConnectionFactory;

import engine.Company;
import engine.Fund;
import engine.ProductStructure;
import engine.UnitValues;

import engine.business.Analyzer;

import event.batch.EquityIndexHedgeProcessor;

import event.component.*;
import event.EDITTrxHistory;
import event.batch.ActivityFileExportProcessor;

import event.business.Event;

import event.component.EventComponent;

import fission.beans.FormBean;
import fission.beans.PageBean;
import fission.beans.SessionBean;

import fission.global.AppReqBlock;

import fission.utility.DateTimeUtil;
import fission.utility.Util;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import engine.UnitValues;
import engine.Fund;
import businesscalendar.business.BusinessCalendar;
import businesscalendar.component.BusinessCalendarComponent;
import reporting.batch.ReportsProcessor;


public class DailyDetailTran extends Transaction
{

    private static final String POOLNAME = ConnectionFactory.EDITSOLUTIONS_POOL;

    //These are the Actions of the Screen
    private static final String SHOW_BATCH_RUN = "showBatchRun";
    private static final String PROCESS_BATCH = "processBatch";
    private static final String SHOW_BATCH_STATUS = "showBatchStatus";
    private static final String BATCH_COMPLETED = "batchCompleted";
    private static final String SHOW_DAILY_SELECTION = "showDailySelection";
    private static final String RUN_ACCOUNTING = "runAccounting";
    private static final String SHOW_SELECT_ACCOUNTING_TO_RUN = "showSelectAccountingToRun";
    private static final String SHOW_BANK = "showBank";
    private static final String SHOW_BANK_FOR_NACHA = "showBankForNACHA";
    private static final String CREATE_BANK_EXTRACTS = "createBankExtracts";
    private static final String CREATE_BANK_FOR_NACHA = "createBankForNACHA";
    private static final String SHOW_SELECT_CONFIRMS_TO_RUN = "showSelectConfirmsToRun";
    private static final String RUN_CONFIRMS = "runConfirms";
    private static final String SHOW_REPORTS = "showReports";
    private static final String SHOW_ACCOUNTING_DETAIL_RUN = "showAccountingDetailRun";
    private static final String SHOW_FINANCIAL_ACTIVITY_RUN = "showFinancialActivityRun";
    private static final String SHOW_CONTROLS_AND_BALANCES_RUN = "showControlsAndBalancesRun";
    private static final String SHOW_SELECT_PAYOUT_TRX_RPT_PARAMS = "showSelectPayoutTrxReportParams";
    private static final String SHOW_WITHHOLDING_RPT_PARAMS = "showWithholdingRptParamSelection";
    private static final String RUN_ACCOUNTING_DETAIL_RPT = "runAccountingDetailReport";
    private static final String RUN_FINANCIAL_ACTIVITY_RPT = "runFinancialActivityReport";
    private static final String RUN_PAYOUT_TRANSACTION_RPT = "runPayoutTransactionReport";
    private static final String RUN_CONTROLS_AND_BALANCES_RPT = "runControlsAndBalancesReport";
    private static final String RUN_CHART_OF_ACCOUNTS_RPT = "runChartOfAccountsReport";
    private static final String RUN_WITHHOLDING_RPT = "runWithholdingReport";
    private static final String SHOW_CORRESPONDENCE = "showCorrespondence";
    private static final String CREATE_CORRESPONDENCE = "createCorrespondence";
    private static final String SHOW_EQUITY_INDEX_HEDGE = "showEquityIndexHedge";
    private static final String RUN_EQUITY_INDEX_HEDGE = "runEquityIndexHedge";
    private static final String SHOW_GAAP_PREMIUM_EXTRACT = "showGAAPPremiumExtract";
    private static final String RUN_GAAP_PREMIUM_EXTRACT = "runGAAPPremiumExtract";
    private static final String RUN_UV_UPDATE = "runUVUpdate";
    private static final String SHOW_HEDGE_FUND_NOTIFICATION = "showHedgeFundNotification";
    private static final String RUN_HEDGE_FUND_NOTIFICATION = "runHedgeFundNotification";
    private static final String IMPORT_UNIT_VALUES = "importUnitValues";
    private static final String SHOW_IMPORT_UNIT_VALUES_PARAMS = "showImportUnitValuesParams";
    private static final String SHOW_ACT_FILE_PARAMS = "showACTFileParams";
    private static final String CREATE_ACTIVITY_FILE = "createActivityFile";
    private static final String SHOW_SEP_ACCT_VAL_BY_DIV = "showSepAcctValByDivision";
    private static final String RUN_SEP_ACCT_VAL_BY_DIV = "runSepAcctValByDivision";
    private static final String SHOW_SEP_ACCT_VAL_BY_CASE = "showSepAcctValByCase";
    private static final String RUN_SEP_ACCT_VAL_BY_CASE = "runSepAcctValByCase";
    private static final String SHOW_CASH_CLEARANCE_IMPORT = "showCashClearanceImport";
    private static final String IMPORT_CASH_CLEARANCE = "importCashClearance";
    private static final String SHOW_CASE_MANAGER_REVIEW_PARAMS = "showCaseManagerReviewParams";
    private static final String FILTER_FUNDS_FOR_MARKETING_PACKAGE = "filterFundsForMarketingPackage";
    private static final String RUN_CASE_MANAGER_REVIEW = "runCaseManagerReview";
    private static final String SHOW_FUND_ACTIVITY_REPORT_PARAMS = "showFundActivityReportParams";
    private static final String RUN_FUND_ACTIVITY_REPORT = "runFundActivityReport";
    private static final String SHOW_ASSET_LIABILITIES_REPORT_PARAMS = "showAssetLiabilitiesReportParams";
    private static final String RUN_ASSET_LIABILITIES_REPORT = "runAssetLiabilitiesReport";
    private static final String SHOW_MONEY_MOVE_REPORT_PARAMS = "showMoneyMoveReportParams";
    private static final String RUN_MONEY_MOVE_REPORT = "runMoneyMoveReport";
    private static final String ANALYZE_JOB_TO_RUN = "analyzeJobToRun";
    private static final String SHOW_ANALYZER = "showAnalyzer";
    private static final String SHOW_CB_ALREADY_RUN_DIALOG = "showCBAlreadyRunDialog";
    private static final String SHOW_HEDGE_FUND_PRICING_REPORT = "showHedgeFundPricingReport";
    private static final String SHOW_FUND_ACTIVITY_EXPORT_PARAMS = "showFundActivityExportParams";
    private static final String RUN_FUND_ACTIVITY_EXPORT = "runFundActivityExport";
    private static final String SHOW_TRANSFER_MEMORANDUM_PARAM = "showTransferMemorandumParam";
    private static final String RUN_TRANSFER_MEMORANDUM = "runTransferMemorandum";
    private static final String UPDATE_FUND_ACTIVITY_FUND_NAME_DOUBLE_TABLE_MODEL = "updateFundActivityFundNameDoubleTableModel";
    private static final String SHOW_FUND_ACTIVITY_EXPORT_DATE_FIELDS = "showFundActivityExportDateFields";
    private static final String SHOW_PRD_PARAMS = "showPRDParams";
    private static final String SHOW_PRD_COMPARE_PARAMS = "showPRDCompareParams";
    private static final String SHOW_MONTHLY_VALUATION_PARAMS = "showMonthlyValuationParams";
    private static final String SHOW_ANNUAL_STATEMENTS_PARAMS = "showAnnualStatementsParams";
    private static final String SHOW_POLICY_PAGES_PARAMS = "showPolicyPagesParams";
    private static final String SHOW_PRD_COMPARE_EXTRACT_PARAMS = "showPRDCompareExtractParams";
    private static final String CREATE_PRD_EXTRACT = "createPRDExtract";
    private static final String RUN_PRD_COMPARE = "runPRDCompare";
    private static final String RUN_MONTHLY_VALUATION = "runMonthlyValuation";
    private static final String RUN_ANNUAL_STATEMENTS = "runAnnualStatements";
    private static final String RUN_POLICY_PAGES = "runPolicyPages";
    private static final String RUN_PRD_COMPARE_EXTRACT = "runPRDCompareExtract";
    private static final String SHOW_LIST_BILL_PARAMS = "showListBillParams";
    private static final String CREATE_LIST_BILL = "createListBill";
    private static final String SHOW_CLIENT_ACCOUNTING_EXTRACT_PARAMS = "showClientAccountingExtractParams";
    private static final String CREATE_CLIENT_ACCOUNTING_EXTRACT = "createClientAccountingExtract";
    private static final String SHOW_CHECK_NUMBER_IMPORT_PARAMS = "showCheckNumberImportParams";
    private static final String RUN_CHECK_NUMBER_IMPORT = "runCheckNumberImport";

    //Pages that the Tran will return
    private static final String DAILY_SELECTION = "/daily/jsp/dailySelection.jsp";
    private static final String BATCH_RUN = "/daily/jsp/batchRun.jsp";
    private static final String BATCH_RESPONSE = "/daily/jsp/batchReponse.jsp";
    private static final String BATCH_STATUS = "/daily/jsp/batchStatus.jsp";
    private static final String SELECT_ACCOUNTING_TO_RUN = "/daily/jsp/accountingRun.jsp";
    private static final String ACCOUNTING_COMPLETED = "/daily/jsp/accountingCompleted.jsp";
    private static final String ACCOUNTING_NOT_SUBMITTED = "/daily/jsp/accountingNotSubmitted.jsp";
    private static final String BANK_RUN = "/daily/jsp/bankRun.jsp";
    private static final String BANK_NOT_RUN = "/daily/jsp/bankNotRun.jsp";
    private static final String BANK_COMPLETED = "/daily/jsp/bankCompleted.jsp";
    private static final String SELECT_CONFIRMS_TO_RUN = "/daily/jsp/confirmsRun.jsp";
    private static final String CONFIRMS_COMPLETED = "/daily/jsp/confirmsCompleted.jsp";
    private static final String CONFIRMS_NOT_SUBMITTED = "/daily/jsp/confirmsNotSubmitted.jsp";
    private static final String SELECT_REPORT = "/daily/jsp/selectReport.jsp";
    private static final String SELECT_ACCTG_DETAIL_PARAMS = "/daily/jsp/selectAcctgDetailParams.jsp";
    private static final String SELECT_FINANCIAL_ACTIVITY_PARAMS = "/daily/jsp/selectFinancialActivityParams.jsp";
    private static final String ACCOUNTING_DETAIL_REPORT = "/daily/jsp/accountingDetailReport.jsp";
    private static final String FINANCIAL_ACTIVITY_REPORT = "/daily/jsp/financialActivityReport.jsp";
    private static final String SELECT_CONTROLS_AND_BALANCES_PARAMS = "/daily/jsp/selectControlsAndBalancesParams.jsp";
    private static final String CONTROLS_AND_BALANCES_ERROR_PAGE = "/daily/jsp/controlsAndBalancesError.jsp";
    private static final String COA_REPORT_GENERATED = "/daily/jsp/chartOfAccountsRptComplete.jsp";
    private static final String SELECT_WITHHOLDING_RPT_PARAMS = "/daily/jsp/selectWithholdingRptParams.jsp";
    private static final String WITHHOLDING_REPORT = "/daily/jsp/withholdingReport.jsp";
    private static final String SELECT_PAYOUT_TRX_RPT_PARAMS = "/daily/jsp/selectPayoutTrxReportParams.jsp";
    private static final String NO_ACCOUNTING_DETAIL_TO_RUN = "/daily/jsp/noAcctngDetailToRun.jsp";
    private static final String CORRESPONDENCE_RUN = "/daily/jsp/correspondenceRun.jsp";
    private static final String EQUITY_INDEX_HEDGE_TO_RUN = "/daily/jsp/equityIndexHedgeToRun.jsp";
    private static final String GAAP_PREMIUM_EXTRACT_TO_RUN = "/daily/jsp/gaapPremiumExtractToRun.jsp";
    private static final String UV_UPDATE_COMPLETE = "/daily/jsp/uvUpdateComplete.jsp";
    private static final String UV_UPDATE_ERRORED = "/daily/jsp/uvUpdateErrored.jsp";
    private static final String HEDGE_FUND_NOTIFICATION_PARAMS = "/daily/jsp/hedgeFundNotificationParams.jsp";
    private static final String HEDGE_FUND_NOTIFICATION_COMPLETE = "/daily/jsp/hedgeFundNotificationComplete.jsp";
    private static final String HEDGE_FUND_NOTIFICATION_ERRORED = "/daily/jsp/hedgeFundNotificationErrored.jsp";
    private static final String UNIT_VALUE_IMPORT_PARAMS = "/daily/jsp/unitValueImportParams.jsp";
    private static final String IMPORT_UNIT_VALUES_COMPLETE = "/daily/jsp/importUnitValuesComplete.jsp";
    private static final String ACT_FILE_PARAMS = "/daily/jsp/activityFileParams.jsp";
    private static final String ACT_FILE_CREATION_COMPLETE = "/daily/jsp/activityFileCreationComplete.jsp";
    private static final String SEP_ACCT_VAL_BY_DIV_PARAMS = "/daily/jsp/sepAcctValByDivisionParams.jsp";
    private static final String SEP_ACCT_VAL_BY_DIVISION = "/daily/jsp/sepAcctValByDivision.jsp";
    private static final String SEP_ACCT_VAL_BY_DIVISION_ERROR = "/daily/jsp/sepAcctValByDivisionError.jsp";
    private static final String SEP_ACCT_VAL_BY_CASE_PARAMS = "/daily/jsp/sepAcctValByCaseParams.jsp";
    private static final String SEP_ACCT_VAL_BY_CASE = "/daily/jsp/sepAcctValByCase.jsp";
    private static final String CASH_CLEARANCE_IMPORT_PARAMS = "/daily/jsp/cashClearanceImportParams.jsp";
    private static final String CASH_CLEARANCE_IMPORT_RESULT = "/daily/jsp/cashClearanceImportResult.jsp";
    private static final String CASE_MANAGER_REVIEW_PARAMS = "/daily/jsp/caseManagerReviewParams.jsp";
    private static final String CASE_MANAGER_REVIEW_RPT = "/daily/jsp/caseManagerReviewRpt.jsp";
    private static final String CASE_MANAGER_REVIEW_ERROR = "/daily/jsp/caseManagerReviewError.jsp";
    private static final String FUND_ACTIVITY_REPORT_PARAMS = "/daily/jsp/fundActivityReportParams.jsp";
    private static final String FUND_ACTIVITY_REPORT = "/daily/jsp/fundActivityReport.jsp";
    private static final String FUND_ACTIVITY_REPORT_ERROR = "/daily/jsp/fundActivityReportError.jsp";
    private static final String ASSET_LIABILITIES_REPORT_PARAMS = "/daily/jsp/assetLiabilitiesReportParams.jsp";
    private static final String ASSET_LIABILITIES_REPORT = "/daily/jsp/assetLiabilitiesReport.jsp";
    private static final String ASSET_LIABILITIES_REPORT_ERROR = "/daily/jsp/assetLiabilitiesReportError.jsp";
    private static final String MONEY_MOVE_REPORT_PARAMS = "/daily/jsp/moneyMoveReportParams.jsp";
    private static final String MONEY_MOVE_REPORT = "/daily/jsp/moneyMoveReport.jsp";
    private static final String MONEY_MOVE_REPORT_ERROR = "/daily/jsp/moneyMoveReportError.jsp";
    private static final String ANALYZER_DIALOG = "/engine/jsp/dbugmain.jsp";
    private static final String CB_ALREADY_RUN_DIALOG = "/daily/jsp/cbAlreadyRunDialog.jsp";
    private static final String HEDGE_FUND_PRICING_REPORT = "/daily/jsp/hedgeFundPricingReport.jsp";
    private static final String FUND_ACTIVITY_EXPORT_PARAMS = "/daily/jsp/fundActivityExportParams.jsp";
    private static final String FUND_ACTIVITY_EXPORT = "/daily/jsp/fundActivityExport.jsp";
    private static final String TRANSFER_MEMORANDUM_PARAM = "/daily/jsp/transferMemorandumParam.jsp";
    private static final String TRANSFER_MEMORANDUM = "/daily/jsp/transferMemorandum.jsp";
    private static final String TRANSFER_MEMORANDUM_ERROR = "/daily/jsp/transferMemorandumError.jsp";
    private static final String PRD_PARAMETERS = "/daily/jsp/prdParameters.jsp";
    private static final String PRD_COMPARE_PARAMETERS = "/daily/jsp/prdCompareParameters.jsp";
    private static final String MONTHLY_VALUATION_PARAMETERS = "/daily/jsp/monthlyValuationParameters.jsp";
    private static final String ANNUAL_STATEMENTS_PARAMETERS = "/daily/jsp/annualStatementsParameters.jsp";
    private static final String POLICY_PAGES_PARAMETERS = "/daily/jsp/policyPagesParameters.jsp";
    private static final String PRD_COMPARE_EXTRACT_PARAMETERS = "/daily/jsp/prdCompareExtractParameters.jsp";
    private static final String LIST_BILL_PARAMETERS = "/daily/jsp/listBillParameters.jsp";
    private static final String CLIENT_ACCOUNTING_EXTRACT_PARAMETERS = "/daily/jsp/clientAccountingExtractParameters.jsp";
    private static final String CHECK_NUMBER_IMPORT_PARAMS = "/daily/jsp/checkNumberImportParams.jsp";
    private static final String BANK_RUN_FOR_NACHA = "/daily/jsp/bankRunForNACHA.jsp";

    private PayoutTrxRptInterfaceCmd payoutTrxRptInterfaceCmd = null;
    private WithholdingRptInterfaceCmd withholdingRptInterfaceCmd = null;
    private StringBuffer trxFileData = null;
    private static final String EQUITY_JOB_NAME = "EquityIndexHedge";

    private static final String REPORTING_MAIN = "/reporting/jsp/reportingSelection.jsp";

    /**
     * NOTE: CompanyStructure and ProductStructure are used interchangeably
     * @param appReqBlock
     * @return
     * @throws Exception
     */
    public String execute(AppReqBlock appReqBlock) throws Exception
    {
        String action = appReqBlock.getReqParm("action");

        if (action.equals(SHOW_SELECT_ACCOUNTING_TO_RUN))
        {
            return showSelectAccountingToRun(appReqBlock);
        }
        else if (action.equals(RUN_ACCOUNTING))
        {
            return runAccounting(appReqBlock);
        }
        else if (action.equals(SHOW_DAILY_SELECTION))
        {
            return showDailySelection(appReqBlock);
        }
        else if (action.equals(SHOW_BATCH_RUN))
        {
            return showBatchRun(appReqBlock);
        }
        else if (action.equals(PROCESS_BATCH))
        {
            return processBatch(appReqBlock);
        }

        //		else if (action.equals(SHOW_BATCH_STATUS)) {
        //
        //			return showBatchStatus(appReqBlock);
        //		}
        //		else if (action.equals(BATCH_COMPLETED)) {
        //
        //			return batchCompleted(appReqBlock);
        //		}
        else if (action.equals(SHOW_BANK))
        {
            return showBank(appReqBlock);
        }
        else if (action.equals(CREATE_BANK_EXTRACTS))
        {
            return createBankExtracts(appReqBlock);
        }
        else if (action.equals(SHOW_BANK_FOR_NACHA))
        {
            return showBankForNACHA(appReqBlock);
        }
        else if (action.equals(CREATE_BANK_FOR_NACHA))
        {
            return createBankForNACHA(appReqBlock);
        }
        else if (action.equals(SHOW_SELECT_CONFIRMS_TO_RUN))
        {
            return showSelectConfirmsToRun(appReqBlock);
        }

        //        else if (action.equals(RUN_CONFIRMS)) {
        //
        //            return runConfirms(appReqBlock);
        //        }
        else if (action.equals(SHOW_REPORTS))
        {
            return showReports(appReqBlock);
        }
        else if (action.equals(SHOW_ACCOUNTING_DETAIL_RUN))
        {
            return showAccountingDetailRun(appReqBlock);
        }
        else if (action.equals(RUN_ACCOUNTING_DETAIL_RPT))
        {
            return runAccountingDetailReport(appReqBlock);
        }
        else if (action.equals(SHOW_FINANCIAL_ACTIVITY_RUN))
        {
            return showFinancialActivityRun(appReqBlock);
        }
        else if (action.equals(RUN_FINANCIAL_ACTIVITY_RPT))
        {
            return runFinancialActivityReport(appReqBlock);
        }
        else if (action.equals(SHOW_SELECT_PAYOUT_TRX_RPT_PARAMS))
        {
            return showSelectPayoutTrxReportParams(appReqBlock);
        }
        else if (action.equals(RUN_PAYOUT_TRANSACTION_RPT)) {

            return runPayoutTransactionReport(appReqBlock);
        }
        else if (action.equals(SHOW_CONTROLS_AND_BALANCES_RUN))
        {
            return showControlsAndBalancesRun(appReqBlock);
        }
        else if (action.equals(RUN_CONTROLS_AND_BALANCES_RPT))
        {
            return runControlsAndBalancesReport(appReqBlock);
        }
        else if (action.equals(RUN_CHART_OF_ACCOUNTS_RPT))
        {
            return runChartOfAccountsReport(appReqBlock);
        }
        else if (action.equals(SHOW_WITHHOLDING_RPT_PARAMS))
        {
            return showWithholdingRptParams(appReqBlock);
        }
        else if (action.equals(SHOW_CORRESPONDENCE))
        {
            return showCorrespondence(appReqBlock);
        }
        else if (action.equals(CREATE_CORRESPONDENCE))
        {
            return createCorrespondence(appReqBlock);
        }
        else if (action.equals(SHOW_EQUITY_INDEX_HEDGE))
        {
            return showEquityIndexHedge(appReqBlock);
        }
        else if (action.equals(RUN_EQUITY_INDEX_HEDGE))
        {
            return runEquityIndexHedge(appReqBlock);
        }
        else if (action.equals(SHOW_GAAP_PREMIUM_EXTRACT))
        {
            return showGAAPPremiumExtract(appReqBlock);
        }
        else if (action.equals(RUN_GAAP_PREMIUM_EXTRACT))
        {
            return runGAAPPremiumExtract(appReqBlock);
        }
        else if (action.equals(RUN_UV_UPDATE))
        {
            return runUVUpdate(appReqBlock);
        }
        else if (action.equals(SHOW_HEDGE_FUND_NOTIFICATION))
        {
            return showHedgeFundNotification(appReqBlock);
        }
        else if (action.equals(RUN_HEDGE_FUND_NOTIFICATION))
        {
            return runHedgeFundNotification(appReqBlock);
        }
        else if (action.equals(SHOW_IMPORT_UNIT_VALUES_PARAMS))
        {
            return showImportUnitValuesParams(appReqBlock);
        }
        else if (action.equals(IMPORT_UNIT_VALUES))
        {
            return importUnitValues(appReqBlock);
        }
        else if (action.equals(SHOW_ACT_FILE_PARAMS))
        {
            return showActivityFileParams(appReqBlock);
        }
        else if (action.equals(CREATE_ACTIVITY_FILE))
        {
            return createActivityFile(appReqBlock);
        }
        else if (action.equals(SHOW_SEP_ACCT_VAL_BY_DIV))
        {
            return showSepAcctValByDiv(appReqBlock);
        }
        else if (action.equals(RUN_SEP_ACCT_VAL_BY_DIV))
        {
            return runSepAcctValByDiv(appReqBlock);
        }
        else if (action.equals(SHOW_SEP_ACCT_VAL_BY_CASE))
        {
            return showSepAcctValByCase(appReqBlock);
        }
        else if (action.equals(RUN_SEP_ACCT_VAL_BY_CASE))
        {
            return runSepAcctValByCase(appReqBlock);
        }
        else if (action.equals(SHOW_CASE_MANAGER_REVIEW_PARAMS))
        {
            return showCaseManagerReviewParams(appReqBlock);
        }
        else if (action.equals(FILTER_FUNDS_FOR_MARKETING_PACKAGE))
        {
            return filterFundsForMarketingPackage(appReqBlock);
        }
        else if (action.equals(RUN_CASE_MANAGER_REVIEW))
        {
            return runCaseManagerReview(appReqBlock);
        }
        else if (action.equals(SHOW_FUND_ACTIVITY_REPORT_PARAMS))
        {
            return showFundActivityReportParams(appReqBlock);
        }
        else if (action.equals(RUN_FUND_ACTIVITY_REPORT))
        {
            return runFundActivityReport(appReqBlock);
        }
        else if (action.equals(SHOW_ASSET_LIABILITIES_REPORT_PARAMS))
        {
            return showAssetLiabilitiesReportParams(appReqBlock);
        }
        else if (action.equals(RUN_ASSET_LIABILITIES_REPORT))
        {
            return runAssetLiabilitiesReport(appReqBlock);
        }
        else if (action.equals(SHOW_MONEY_MOVE_REPORT_PARAMS))
        {
            return showMoneyMoveReportParams(appReqBlock);
        }
        else if (action.equals(RUN_MONEY_MOVE_REPORT))
        {
            return runMoneyMoveReport(appReqBlock);
        }

        //        else if (action.equals(RUN_WITHHOLDING_RPT)) {
        //
        //            return runWithholdingReport(appReqBlock);
        //        }
        //		else {
        //
        //			return "error.jsp";
        //		}
        else if (action.equals(SHOW_CASH_CLEARANCE_IMPORT))
        {
            return showCashClearanceImport(appReqBlock);
        }
        else if (action.equals(IMPORT_CASH_CLEARANCE))
        {
            return runCashClearanceImport(appReqBlock);
        }
        else if (action.equals(ANALYZE_JOB_TO_RUN))
        {
            return analyzeJobToRun(appReqBlock);
        }
        else if (action.equals(SHOW_ANALYZER))
        {
            return showAnalyzer(appReqBlock);
        }
        else if (action.equals(SHOW_CB_ALREADY_RUN_DIALOG))
        {
            return showCBAlreadyRunDialog(appReqBlock);
        }
        else if (action.equals(SHOW_HEDGE_FUND_PRICING_REPORT))
        {
            return showHedgeFundPricingReport(appReqBlock);
        }
        else if (action.equals(SHOW_FUND_ACTIVITY_EXPORT_PARAMS))
        {
            return showFundActivityExportParams(appReqBlock);
        }
        else if (action.equals(RUN_FUND_ACTIVITY_EXPORT))
        {
            return runFundActivityExport(appReqBlock);
        }
        else if (action.equals(SHOW_TRANSFER_MEMORANDUM_PARAM))
        {
            return showTransferMemorandumParam(appReqBlock);
        }
        else if (action.equals(RUN_TRANSFER_MEMORANDUM))
        {
            return runTransferMemorandum(appReqBlock);
        }
        else if (action.equals(UPDATE_FUND_ACTIVITY_FUND_NAME_DOUBLE_TABLE_MODEL))
        {
            return updateFundActivityFundNameDoubleTableModel(appReqBlock);
        }
        else if (action.equals(SHOW_FUND_ACTIVITY_EXPORT_DATE_FIELDS))
        {
            return showFundActivityExportDateFields(appReqBlock);
        }
        else if (action.equals(SHOW_PRD_PARAMS))
        {
            return showPRDParams(appReqBlock);
        }
        else if (action.equals(SHOW_PRD_COMPARE_PARAMS))
        {
            return showPRDCompareParams(appReqBlock);
        }
        else if (action.equals(SHOW_MONTHLY_VALUATION_PARAMS))
        {
            return showMonthlyValuationParams(appReqBlock);
        }
        else if (action.equals(SHOW_ANNUAL_STATEMENTS_PARAMS))
        {
            return showAnnualStatementsParams(appReqBlock);
        }
        else if (action.equals(SHOW_POLICY_PAGES_PARAMS))
        {
            return showPolicyPagesParams(appReqBlock);
        }
        else if (action.equals(SHOW_PRD_COMPARE_EXTRACT_PARAMS))
        {
            return showPRDCompareExtractParams(appReqBlock);
        }
        else if (action.equals(SHOW_LIST_BILL_PARAMS))
        {
           return showListBillParams(appReqBlock);
        }
        else if (action.equals(CREATE_PRD_EXTRACT))
        {
            return createPRDExtract(appReqBlock);
        }
        else if (action.equals(RUN_PRD_COMPARE))
        {
            return runPRDCompare(appReqBlock);
        }
        else if (action.equals(RUN_MONTHLY_VALUATION))
        {
            return runMonthlyValuation(appReqBlock);
        }
        else if (action.equals(RUN_ANNUAL_STATEMENTS))
        {
            return runAnnualStatements(appReqBlock);
        }
        else if (action.equals(RUN_POLICY_PAGES))
        {
            return runPolicyPages(appReqBlock);
        }
        else if (action.equals(RUN_PRD_COMPARE_EXTRACT))
        {
            return runPRDCompareExtract(appReqBlock);
        }
        else if (action.equals(CREATE_LIST_BILL))
        {
            return createListBill(appReqBlock);
        }
        else if (action.equals(SHOW_CLIENT_ACCOUNTING_EXTRACT_PARAMS))
        {
            return showClientAccountingExtractParams(appReqBlock);
        }
        else if (action.equals(CREATE_CLIENT_ACCOUNTING_EXTRACT))
        {
            return createClientAccountingExtract(appReqBlock);
        }
        else if (action.equalsIgnoreCase(SHOW_CHECK_NUMBER_IMPORT_PARAMS))
        {
            return showCheckNumberImportParams(appReqBlock);
        }
        else if (action.equalsIgnoreCase(RUN_CHECK_NUMBER_IMPORT))
        {
            return runCheckNumberImport(appReqBlock);
        }
        else
        {
            throw new Exception("DailyDetailTran: Invalid action " + action);
        }
    }



    protected String showDailySelection(AppReqBlock appReqBlock) throws Exception
    {
        return DAILY_SELECTION;
    }

    /**
     * Runs the Unit Value Update for Hedge Fund Processing (see UnitValueUpdateProcessor.java for details)
     * @param appReqBlock
     * @return
     * @throws Exception
     */
    protected String runUVUpdate(AppReqBlock appReqBlock) throws Exception
    {
        Batch batch = new BatchComponent();
        
        batch.unitValueUpdate(null, null, Batch.ASYNCHRONOUS);

        String responseMessage = Util.getResourceMessage("batch.job.in.progress");

        appReqBlock.getHttpServletRequest().setAttribute("responseMessage", responseMessage);

        return DAILY_SELECTION;
    }

    protected String showBatchRun(AppReqBlock appReqBlock) throws Exception
    {
        new BatchUseCaseComponent().runBatch();

        PageBean batchRunPageBean = getCompanyNames(appReqBlock);

        appReqBlock.getHttpServletRequest().setAttribute("batchRunPageBean", batchRunPageBean);

        return BATCH_RUN;
    }

    public String processBatch(AppReqBlock appReqBlock) throws Exception
    {
        FormBean formBean = appReqBlock.getFormBean();

        String companyName = formBean.getValue("companyName");

        String runAsBatch = (String) appReqBlock.getHttpServletRequest().getAttribute("runAsBatch");

        if (runAsBatch == null)
        {
            runAsBatch = "false"; // Assume false unless otherwise specified.
        }

        EDITDate processDate = DateTimeUtil.getEDITDateFromMMDDYYYY(formBean.getValue("processDate"));

        String operator = appReqBlock.getUserSession().getUsername();

        Batch batch = new BatchComponent();

        batch.processDailyBatch(processDate.getFormattedDate(), companyName, operator, null, null, Batch.ASYNCHRONOUS);

        String responseMessage = Util.getResourceMessage("batch.job.in.progress");

        appReqBlock.getHttpServletRequest().setAttribute("responseMessage", responseMessage);

        return DAILY_SELECTION;
    }

    /**
     * Displays the HedgeFundNotification parameter page
     * @param appReqBlock
     * @return
     * @throws Exception
     */
    protected String showHedgeFundNotification(AppReqBlock appReqBlock) throws Exception
    {
        return HEDGE_FUND_NOTIFICATION_PARAMS;
    }

    /**
     * Runs the Hedge Fund Notification job (see HedgeFundNotificationProcessor.java for details)
     * @param appReqBlock
     * @return
     * @throws Exception
     */
    protected String runHedgeFundNotification(AppReqBlock appReqBlock) throws Exception
    {
        String notifyMonth = appReqBlock.getReqParm("notifyMonth");
        String notifyDay = appReqBlock.getReqParm("notifyDay");
        String notifyYear = appReqBlock.getReqParm("notifyYear");

        String notifyCorrDate = DateTimeUtil.initDate(notifyMonth, notifyDay, notifyYear, null);

        event.business.Event eventComponent = new event.component.EventComponent();
       
        Batch batch = new BatchComponent();
        
        batch.runHedgeFundNotification(notifyCorrDate, null, null, Batch.ASYNCHRONOUS);

        String responseMessage = Util.getResourceMessage("batch.job.in.progress");

        appReqBlock.getHttpServletRequest().setAttribute("responseMessage", responseMessage);

        return DAILY_SELECTION;
    }

    protected String showImportUnitValuesParams(AppReqBlock appReqBlock) throws Exception
    {
        return UNIT_VALUE_IMPORT_PARAMS;
    }

    protected String importUnitValues(AppReqBlock appReqBlock) throws Exception
    {
        EDITDate importDate = DateTimeUtil.getEDITDateFromMMDDYYYY(appReqBlock.getReqParm("unitValueImportDate"));

        Batch batch = new BatchComponent();

        batch.importUnitValues(importDate.getFormattedDate(), null, null, Batch.ASYNCHRONOUS);

        String responseMessage = Util.getResourceMessage("batch.job.in.progress");

        appReqBlock.getHttpServletRequest().setAttribute("responseMessage", responseMessage);

        return DAILY_SELECTION;
    }

    protected String showBatchStatus(AppReqBlock appReqBlock) throws Exception
    {
        Contract contractComp = new contract.component.ContractComponent();

        BatchStatusVO[] batchStatusVO = (BatchStatusVO[]) contractComp.getBatchStatus();

        appReqBlock.getHttpServletRequest().setAttribute("batchesUpdateData", batchStatusVO);

        return BATCH_STATUS;
    }

    protected String batchCompleted(AppReqBlock appReqBlock) throws Exception
    {
        String completedBatchId = appReqBlock.getReqParm("completedBatchId");

        Contract contractComp = new contract.component.ContractComponent();

        contractComp.updateBatchList(completedBatchId);

        BatchStatusVO[] batchStatusVO = (BatchStatusVO[]) contractComp.getBatchStatus();

        appReqBlock.getHttpServletRequest().setAttribute("batchesUpdateData", batchStatusVO);

        return BATCH_STATUS;
    }

    protected String showSelectAccountingToRun(AppReqBlock appReqBlock) throws Exception
    {
        new BatchUseCaseComponent().runAccounting();

        PageBean accountingRunPageBean = getCompanyNames(appReqBlock);

        appReqBlock.getHttpServletRequest().setAttribute("accountingRunPageBean", accountingRunPageBean);

        return SELECT_ACCOUNTING_TO_RUN;
    }

    public String runAccounting(AppReqBlock appReqBlock) throws Exception
    {
        String runAsBatch = (String) appReqBlock.getHttpServletRequest().getAttribute("runAsBatch");

        if (runAsBatch == null)
        {
            runAsBatch = "false"; // Assume false unless otherwise specified.
        }

        FormBean formBean = appReqBlock.getFormBean();

        EDITDate processDate = DateTimeUtil.getEDITDateFromMMDDYYYY(formBean.getValue("processDate"));

        String suppressExtractInd = formBean.getValue("suppressExtractIndStatus");

        String suppressExtract = "false";

        if (suppressExtractInd.equalsIgnoreCase("Checked"))
        {
            suppressExtract = "true";
        }

        String outputFileType = Util.initString(formBean.getValue("outputFileType"), "XML");

        Batch batch = new BatchComponent();

        batch.createAccountingExtract_XML(processDate.getFormattedDate(), suppressExtract, outputFileType, null, null, Batch.ASYNCHRONOUS);

        String responseMessage = Util.getResourceMessage("batch.job.in.progress");

        appReqBlock.putInRequestScope("responseMessage", responseMessage);

        return DAILY_SELECTION;
    }

    protected String showSelectConfirmsToRun(AppReqBlock appReqBlock) throws Exception
    {
        new BatchUseCaseComponent().runConfirms();

        PageBean confirmsRunPageBean = getCompanyNames(appReqBlock);

        appReqBlock.getHttpServletRequest().setAttribute("confirmsRunPageBean", confirmsRunPageBean);

        return SELECT_CONFIRMS_TO_RUN;
    }

    protected String showSelectPayoutTrxReportParams(AppReqBlock appReqBlock) throws Exception
    {
        new BatchUseCaseComponent().runPayoutTransactionReport();

        return SELECT_PAYOUT_TRX_RPT_PARAMS;
    }

    protected String runConfirms(AppReqBlock appReqBlock) throws Exception
    {
        //        CodeTableWrapper codeTableWrapper = CodeTableWrapper.getSingleton();
        //
        //        Contract contractComp = (Contract) appReqBlock.getWebService("contract-service");
        //
        //        String companyName = appReqBlock.getReqParm("companyName");
        //        long correspondenceTypeCT = Long.parseLong(appReqBlock.getReqParm("correspondenceTypeCT"));
        //        CodeTableVO corrTypeCTVO = codeTableWrapper.getCodeTableEntry(correspondenceTypeCT);
        //        String correspondenceType = corrTypeCTVO.getCode();
        //
        //        String[] confirms = contractComp.generateConfirms(companyName, correspondenceType);
        //
        //        if (confirms.length == 0) {
        //
        //            return CONFIRMS_NOT_SUBMITTED;
        //        }
        //
        //        else {
        //
        //            appReqBlock.getHttpServletRequest().setAttribute("confirms", confirms);
        //
        //            return CONFIRMS_COMPLETED;
        //        }
        return null;
    }

    protected String showReports(AppReqBlock appReqBlock) throws Exception
    {
        return SELECT_REPORT;
    }

    protected String showAccountingDetailRun(AppReqBlock appReqBlock) throws Exception
    {
        new BatchUseCaseComponent().runAccountingDetailReport();

        PageBean acctgDetailRptPageBean = getCompanyNames(appReqBlock);

        appReqBlock.getHttpServletRequest().setAttribute("acctgDetailRptPageBean", acctgDetailRptPageBean);

        return SELECT_ACCTG_DETAIL_PARAMS;
    }

    protected String runAccountingDetailReport(AppReqBlock appReqBlock) throws Exception
    {
        FormBean formBean = appReqBlock.getFormBean();

        String companyName = formBean.getValue("companyName");
        String dateType = formBean.getValue("dateType");

        EDITDate fromDate = DateTimeUtil.getEDITDateFromMMDDYYYY(formBean.getValue("fromDate"));
        EDITDate toDate   = DateTimeUtil.getEDITDateFromMMDDYYYY(formBean.getValue("toDate"));

        String fromDateString = fromDate.getFormattedDate();
        String toDateString = toDate.getFormattedDate();

        if (dateType.startsWith("Accounting"))
        {
            fromDateString = fromDate.getFormattedYearAndMonth();
            toDateString = toDate.getFormattedYearAndMonth();
        }

        accounting.business.Lookup accountingLookup = new accounting.component.LookupComponent();

        AccountingDetailVO[] accountingDetailVOs = accountingLookup.getAccountingDetailByCoAndFromToDates(companyName, fromDateString, toDateString, dateType);

        if (accountingDetailVOs == null)
        {
            return NO_ACCOUNTING_DETAIL_TO_RUN;
        }
        else
        {
            appReqBlock.getHttpServletRequest().setAttribute("accountingDetailVOs", accountingDetailVOs);
            appReqBlock.getHttpServletRequest().setAttribute("fromDate", DateTimeUtil.formatEDITDateAsMMDDYYYY(fromDate));
            appReqBlock.getHttpServletRequest().setAttribute("toDate", DateTimeUtil.formatEDITDateAsMMDDYYYY(toDate));
            appReqBlock.getHttpServletRequest().setAttribute("dateType", dateType);

            return ACCOUNTING_DETAIL_REPORT;
        }
    }

    protected String showFinancialActivityRun(AppReqBlock appReqBlock) throws Exception
    {
        PageBean finActivityRptPageBean = getUserCompanyNames(appReqBlock);

        appReqBlock.getHttpServletRequest().setAttribute("finActivityRptPageBean", finActivityRptPageBean);

        return SELECT_FINANCIAL_ACTIVITY_PARAMS;
    }

    protected String runFinancialActivityReport(AppReqBlock appReqBlock) throws Exception
    {
        new BatchUseCaseComponent().runFinancialActivityReport();

        FormBean formBean = appReqBlock.getFormBean();

        String companyName = formBean.getValue("companyName");

        engine.business.Lookup calcLookup = new engine.component.LookupComponent();

        ProductStructureVO[] productStructureVOs = null;

        if (companyName.equalsIgnoreCase("All"))
        {
            productStructureVOs = calcLookup.getAllProductStructures();
        }
        else
        {
            productStructureVOs = calcLookup.getAllProductStructuresByCoName(companyName);
        }

        appReqBlock.getHttpServletRequest().setAttribute("companyStructureVOs", productStructureVOs);

        List csIds = new ArrayList();

        for (int i = 0; i < productStructureVOs.length; i++)
        {
            csIds.add(productStructureVOs[i].getProductStructurePK() + "");
        }

        long[] companyKeys = new long[csIds.size()];

        for (int i = 0; i < csIds.size(); i++)
        {
            companyKeys[i] = Long.parseLong((String) csIds.get(i));
        }

        List fundVOInclusionList = new ArrayList();
        fundVOInclusionList.add(FilteredFundVO.class);

        FundVO[] fundVOs = calcLookup.composeAllFundVOs(fundVOInclusionList);

        appReqBlock.getHttpServletRequest().setAttribute("fundVOs", fundVOs);

        EDITDate fromDate = DateTimeUtil.getEDITDateFromMMDDYYYY(formBean.getValue("fromDate"));

        EDITDate toDate = DateTimeUtil.getEDITDateFromMMDDYYYY(formBean.getValue("toDate"));

        long[] segmentFKs = new FastDAO().findBaseSegmentPKsByProductStructureFK(companyKeys);

            List voInclusionList = new ArrayList();
            voInclusionList.add(EDITTrxVO.class);
            voInclusionList.add(ClientSetupVO.class);
            voInclusionList.add(ContractSetupVO.class);
            voInclusionList.add(SegmentVO.class);

        //Oracle error occures when an excessive amount of parameters are in the query - need to eliminate
        //segmentPKs from the query when this happens
            event.business.Event eventComponent = new event.component.EventComponent();
        EDITTrxHistoryVO[] editTrxHistoryVOs = null;
        if (segmentFKs != null && segmentFKs.length > 0  && segmentFKs.length <= 900)
        {            
            editTrxHistoryVOs = eventComponent.composeEDITTrxHistoryBySegmentFKAndCycleDate(segmentFKs, fromDate.getFormattedDate(), toDate.getFormattedDate(), voInclusionList);

            editTrxHistoryVOs = eventComponent.composeEDITTrxHistoryBySegmentFKAndCycleDate(
                    segmentFKs, fromDate.getFormattedDate(), toDate.getFormattedDate(), voInclusionList);
            if (editTrxHistoryVOs != null)
            {
                EDITTrxHistoryVO[] sortedHistoryVOs = executeSortOfHistoryRecord(editTrxHistoryVOs);
                appReqBlock.getHttpServletRequest().setAttribute("editTrxHistoryVOs", sortedHistoryVOs);
            }
        }
        else
        {

            for (int i = 0; i < productStructureVOs.length; i++)
            {
                EDITTrxHistoryVO[] storedEDITTrxHistoryVOs = (EDITTrxHistoryVO[])appReqBlock.getHttpServletRequest().getAttribute("editTrxHistoryVOs");

                editTrxHistoryVOs = eventComponent.composeEDITTrxHistoryByDatesForCompanyStructure(productStructureVOs[i].getProductStructurePK(), fromDate.getFormattedDate(), toDate.getFormattedDate(), voInclusionList);
            if (editTrxHistoryVOs != null)
            {
                    EDITTrxHistoryVO[] sortedHistoryVOs = executeSortOfHistoryRecord(editTrxHistoryVOs);

                    sortedHistoryVOs = (EDITTrxHistoryVO[])Util.joinArrays(storedEDITTrxHistoryVOs, sortedHistoryVOs, EDITTrxHistoryVO.class);

                    appReqBlock.getHttpServletRequest().setAttribute("editTrxHistoryVOs", sortedHistoryVOs);
                }

            }
        }

        appReqBlock.getHttpServletRequest().setAttribute("fromDate", DateTimeUtil.formatEDITDateAsMMDDYYYY(fromDate));
        appReqBlock.getHttpServletRequest().setAttribute("toDate", DateTimeUtil.formatEDITDateAsMMDDYYYY(toDate));

        return FINANCIAL_ACTIVITY_REPORT;
    }

    private EDITTrxHistoryVO[] executeSortOfHistoryRecord(EDITTrxHistoryVO[] editTrxHistoryVOs) throws Exception
    {
                Map sortedHistories = sortHistoryByTransactionType(editTrxHistoryVOs);
                EDITTrxHistoryVO[] sortedHistoryVOs = new EDITTrxHistoryVO[sortedHistories.size()];
                Iterator it = sortedHistories.values().iterator();
                int h = 0;

                while (it.hasNext())
                {
                    sortedHistoryVOs[h] = (EDITTrxHistoryVO) it.next();
                    h += 1;
                }

        return sortedHistoryVOs;
        }

    private TreeMap sortHistoryByTransactionType(EDITTrxHistoryVO[] editTrxHistoryVOs) throws Exception
    {
        TreeMap sortedHistories = new TreeMap();

        for (int s = 0; s < editTrxHistoryVOs.length; s++)
        {
            EDITTrxVO editTrxVO = (EDITTrxVO) editTrxHistoryVOs[s].getParentVO(EDITTrxVO.class);
            String trxType = editTrxVO.getTransactionTypeCT();
            String effectiveDate = editTrxVO.getEffectiveDate();
            ContractSetupVO contractSetupVO = (ContractSetupVO) editTrxVO.getParentVO(ClientSetupVO.class).getParentVO(ContractSetupVO.class);

            if (contractSetupVO.getParentVOs() != null)
            {
                SegmentVO segmentVO = (SegmentVO) contractSetupVO.getParentVO(SegmentVO.class);

                String contractNumber = null;

                if (segmentVO != null)
                {
                    contractNumber = segmentVO.getContractNumber();
                }

                sortedHistories.put(trxType + contractNumber + effectiveDate, editTrxHistoryVOs[s]);
            }
        }

        return sortedHistories;
    }

    protected String showControlsAndBalancesRun(AppReqBlock appReqBlock) throws Exception
    {
        new BatchUseCaseComponent().runControlsAndBalancesReport();

        PageBean cntlsAndBalsRptPageBean = getCompanyNames(appReqBlock);

        appReqBlock.getHttpServletRequest().setAttribute("cntlsAndBalsRptPageBean", cntlsAndBalsRptPageBean);

        return SELECT_CONTROLS_AND_BALANCES_PARAMS;
    }

    protected String runControlsAndBalancesReport(AppReqBlock appReqBlock) throws Exception
    {
        Event eventComponent = new EventComponent();

        FormBean formBean = appReqBlock.getFormBean();

        String companyName = formBean.getValue("companyName");

        EDITDate cycleDate = DateTimeUtil.getEDITDateFromMMDDYYYY(formBean.getValue("cycleDate"));

        return eventComponent.runControlsAndBalancesReport(companyName, cycleDate.getFormattedDate());
    }

    protected String showBank(AppReqBlock appReqBlock) throws Exception
    {
        new BatchUseCaseComponent().runBanking();

        PageBean bankRunPageBean = getCompanyNames(appReqBlock);

        appReqBlock.getHttpServletRequest().setAttribute("bankRunPageBean", bankRunPageBean);

        return BANK_RUN;
    }

    public String createBankExtracts(AppReqBlock appReqBlock) throws Exception
    {
        String runAsBatch = (String) appReqBlock.getHttpServletRequest().getAttribute("runAsBatch");

        if (runAsBatch == null)
        {
            runAsBatch = "false"; // Assume false unless otherwise specified.
        }

        FormBean formBean = appReqBlock.getFormBean();

        String companyName = formBean.getValue("companyName");

        String contractId = Util.initString(formBean.getValue("contractId"), null);

        String outputFileType = Util.initString(formBean.getValue(("outputFileType")), "XML");

        Batch batch = new BatchComponent();

        batch.createBankExtracts(companyName, contractId, outputFileType, null, null, Batch.ASYNCHRONOUS);

        String responseMessage = Util.getResourceMessage("batch.job.in.progress");

        appReqBlock.putInRequestScope("responseMessage", responseMessage);

        return DAILY_SELECTION;
    }

    protected String showCorrespondence(AppReqBlock appReqBlock) throws Exception
    {
        new BatchUseCaseComponent().runCorrespondence();

        PageBean correspondenceRunPageBean = getCompanyNames(appReqBlock);

        appReqBlock.getHttpServletRequest().setAttribute("correspondenceRunPageBean", correspondenceRunPageBean);

        return CORRESPONDENCE_RUN;
    }

    public String createCorrespondence(AppReqBlock appReqBlock) throws Exception
    {
        FormBean formBean = appReqBlock.getFormBean();

        String companyName = formBean.getValue("companyName");

        Batch batch = new BatchComponent();

        batch.createCorrespondenceExtracts(companyName, null, null, Batch.ASYNCHRONOUS);

        String responseMessage = Util.getResourceMessage("batch.job.in.progress");

        appReqBlock.putInRequestScope("responseMessage", responseMessage);

        return DAILY_SELECTION;
    }

    protected String showEquityIndexHedge(AppReqBlock appReqBlock) throws Exception
    {
        new BatchUseCaseComponent().runEquityIndexHedge();

        Company[] companys = Company.find_All_WithProductStructure();

        appReqBlock.getHttpServletRequest().setAttribute("companyStructures", companys);

        return EQUITY_INDEX_HEDGE_TO_RUN;
    }

    private String runEquityIndexHedge(AppReqBlock appReqBlock) throws Exception
    {
        String productStructure = appReqBlock.getReqParm("selectedCompanyStructure");
        EDITDate runDate = DateTimeUtil.getEDITDateFromMMDDYYYY(appReqBlock.getReqParm("runDate"));
        String createSubBucketInd = Util.initString(appReqBlock.getReqParm("createSubBucketsInd"), "");

        if (createSubBucketInd.equalsIgnoreCase("on"))
        {
            createSubBucketInd = "Y";
        }
        else
        {
            createSubBucketInd = "N";
        }

        Batch batch = new BatchComponent();

        batch.createEquityIndexHedgeExtract(productStructure, runDate.getFormattedDate(), createSubBucketInd, null, null, Batch.ASYNCHRONOUS);

        String responseMessage = Util.getResourceMessage("batch.job.in.progress");

        appReqBlock.putInRequestScope("responseMessage", responseMessage);

        return DAILY_SELECTION;
    }

    /**
     * From the daily selection menu, a request was made to run the GAAP PRemium Extract, this method displays the
     * parameter page.
     * @param appReqBlock
     * @return gaapPremiumExtractToRun
     * @throws Exception
     */
    private String showGAAPPremiumExtract(AppReqBlock appReqBlock) throws Exception
    {
        new BatchUseCaseComponent().runGAAPPremiumExtract();

        engine.business.Lookup calcLookup = new engine.component.LookupComponent();

        ProductStructureVO[] productStructureVOs = calcLookup.getAllProductStructures();

        appReqBlock.getHttpServletRequest().setAttribute("companyStructureVOs", productStructureVOs);

        return GAAP_PREMIUM_EXTRACT_TO_RUN;
    }

    /**
     * Get the selected product structure, startDate and endDate to execute the GAAP Premium Extract
     * @param appReqBlock
     * @return  gaapPremiumExtractToRun.jsp
     * @throws Exception
     */
    private String runGAAPPremiumExtract(AppReqBlock appReqBlock) throws Exception
    {
        String productStructure = appReqBlock.getReqParm("selectedCompanyStructure");

        EDITDate startDate = DateTimeUtil.getEDITDateFromMMDDYYYY(appReqBlock.getReqParm("startDate"));

        EDITDate endDate = DateTimeUtil.getEDITDateFromMMDDYYYY(appReqBlock.getReqParm("endDate"));

        Batch batch = new BatchComponent();

        batch.createPremiumReservesExtract(startDate.getFormattedDate(), endDate.getFormattedDate(), productStructure, null, null, Batch.ASYNCHRONOUS);

        String responseMessage = Util.getResourceMessage("batch.job.in.progress");

        appReqBlock.getHttpServletRequest().setAttribute("responseMessage", responseMessage);

        return DAILY_SELECTION;
    }


    protected String runPayoutTransactionReport(AppReqBlock appReqBlock) throws Exception {

        FormBean formBean = appReqBlock.getFormBean();

        String dateOption = formBean.getValue("selectedDateOption");
        String fromMonth  = formBean.getValue("fromMonth");
        String fromDay    = formBean.getValue("fromDay");
        String fromYear   = formBean.getValue("fromYear");
        String fromDate = DateTimeUtil.initDate(fromMonth, fromDay, fromYear, EDITDate.DEFAULT_MIN_DATE);

        String toMonth    = formBean.getValue("toMonth");
        String toDay      = formBean.getValue("toDay");
        String toYear     = formBean.getValue("toYear");
        String toDate = DateTimeUtil.initDate(toMonth, toDay, toYear, EDITDate.DEFAULT_MAX_DATE);

        Batch batch = new BatchComponent();

        batch.createPayoutTrxExtract(new EDITDate(fromDate), new EDITDate(toDate), dateOption, Batch.ASYNCHRONOUS);

        String responseMessage = Util.getResourceMessage("batch.job.in.progress");

        appReqBlock.putInRequestScope("responseMessage", responseMessage);

        return DAILY_SELECTION;
    }


    protected String runChartOfAccountsReport(AppReqBlock appReqBlock) throws Exception
    {
        new BatchUseCaseComponent().runChartOfAccountsReport();

        Accounting accountingComp = new accounting.component.AccountingComponent();

        accountingComp.runChartOfAccountsReport();

        return COA_REPORT_GENERATED;
    }

    protected String showWithholdingRptParams(AppReqBlock appReqBlock) throws Exception
    {
        new BatchUseCaseComponent().runWithholdingReport();

        PageBean withholdingRptPageBean = getCompanyNames(appReqBlock);

        appReqBlock.getHttpServletRequest().setAttribute("withholdingRptPageBean", withholdingRptPageBean);

        return SELECT_WITHHOLDING_RPT_PARAMS;
    }

    protected String showActivityFileParams(AppReqBlock appReqblock) throws Exception
    {
        return ACT_FILE_PARAMS;
    }

    protected String showCBAlreadyRunDialog(AppReqBlock appReqBlock) throws Exception
    {
        String cycleMonth = appReqBlock.getReqParm("cycleMonth");
        String cycleDay = appReqBlock.getReqParm("cycleDay");
        String cycleYear = appReqBlock.getReqParm("cycleYear");

        appReqBlock.getHttpServletRequest().setAttribute("cycleMonth", cycleMonth);
        appReqBlock.getHttpServletRequest().setAttribute("cycleDay", cycleDay);
        appReqBlock.getHttpServletRequest().setAttribute("cycleYear", cycleYear);

        return CB_ALREADY_RUN_DIALOG;
    }

    protected String createActivityFile(AppReqBlock appReqBlock) throws Exception
    {
        String okayToContinue = Util.initString(appReqBlock.getReqParm("okayToContinue"), "N");

        EDITDate cycleDate = DateTimeUtil.getEDITDateFromMMDDYYYY(appReqBlock.getReqParm("cycleDate"));

        EDITDate edCurrentDate = new EDITDate();

        if (cycleDate.after(edCurrentDate))
        {
            appReqBlock.getHttpServletRequest().setAttribute("ErrorMessage", "Cycle Date Cannot Be Greater Than The Current System Date");

            return ACT_FILE_PARAMS;
        }
        else
        {
            String lastCBRunDate = new engine.dm.dao.FastDAO().getLastControlBalanceDate();

            if (lastCBRunDate != null && !lastCBRunDate.equals(""))
            {
                EDITDate prevRunDate = new EDITDate(lastCBRunDate);

                if ((cycleDate.before(prevRunDate) || cycleDate.equals(prevRunDate))  && !okayToContinue.equalsIgnoreCase("Y"))
                {
                    appReqBlock.getHttpServletRequest().setAttribute("ErrorMessage", "Already Run");
                    appReqBlock.getHttpServletRequest().setAttribute("cycleDate", DateTimeUtil.formatEDITDateAsMMDDYYYY(cycleDate));

                    return ACT_FILE_PARAMS;
                }
                else
                {
                    event.business.Event eventComponent = new event.component.EventComponent();

                    eventComponent.createActivityFile(cycleDate.getFormattedDate());

                    return ACT_FILE_CREATION_COMPLETE;
                }
            }
            else
            {
                event.business.Event eventComponent = new event.component.EventComponent();

                eventComponent.createActivityFile(cycleDate.getFormattedDate());

                return ACT_FILE_CREATION_COMPLETE;
            }
        }
    }

    protected String showSepAcctValByDiv(AppReqBlock appReqBlock) throws Exception
    {
        new BatchUseCaseComponent().runSepAcctValByDiv();

        return SEP_ACCT_VAL_BY_DIV_PARAMS;
    }

    protected String runSepAcctValByDiv(AppReqBlock appReqBlock) throws Exception
    {
        EDITDate runDate = DateTimeUtil.getEDITDateFromMMDDYYYY(appReqBlock.getReqParm("runDate"));

        ReportsProcessor reportsProcessor = new reporting.batch.ReportsProcessor();

        appReqBlock.getHttpServletRequest().setAttribute("runDate", DateTimeUtil.formatEDITDateAsMMDDYYYY(runDate));

        EDITServicesConfig editServicesConfig = ServicesConfig.getEditServicesConfig();
        String reportCompanyName = editServicesConfig.getReportCompanyName();

        if (reportCompanyName != null)
        {
            appReqBlock.getHttpServletRequest().setAttribute("CompanyName", reportCompanyName);
        }

        try
        {
            SeparateAccountValuesReportVO sepAcctValReportVO = reportsProcessor.getInfoForSepAcctReport(runDate);

            if (sepAcctValReportVO.getSeparateAccountValueDetailsVOCount() == 0)
            {
                appReqBlock.getHttpServletRequest().setAttribute("ReportMessage", "Report Not Created - No Data Found");

                return SEP_ACCT_VAL_BY_DIVISION_ERROR;
            }
            else
            {
                appReqBlock.getHttpServletRequest().setAttribute("SeparateAccountValuesReportVO", sepAcctValReportVO);

                return SEP_ACCT_VAL_BY_DIVISION;
            }
        }
        catch (Exception e)
        {
            appReqBlock.getHttpServletRequest().setAttribute("ReportMessage", "Error - Report Could Not Be Created - " + e.getMessage());

            return SEP_ACCT_VAL_BY_DIVISION_ERROR;
        }
    }

    protected String showSepAcctValByCase(AppReqBlock appReqBlock) throws Exception
    {
        new BatchUseCaseComponent().runSepAcctValByCase();

        return SEP_ACCT_VAL_BY_CASE_PARAMS;
    }

    protected String runSepAcctValByCase(AppReqBlock appReqBlock) throws Exception
    {
        EDITDate runDate = DateTimeUtil.getEDITDateFromMMDDYYYY(appReqBlock.getReqParm("runDate"));

        // New field was added to QuoteVO with the introduction of loan quotes. The quote processing needs to know
        // what kind of quote is it requesting for.
        String quoteTypeCT = "CashTerminationValue";

        UserSession userSession = appReqBlock.getUserSession();

        SeparateAcctValByCaseVO sepAcctValByCaseVO = new ReportsProcessor().getSepAcctValuesByCase(runDate.getFormattedDate(), quoteTypeCT, userSession);

        if (sepAcctValByCaseVO.getSeparateAcctValDetailByCaseVOCount() == 0)
        {
            appReqBlock.getHttpServletRequest().setAttribute("ReportMessage", "Report Not Created - No Data Found");
        }
        else
        {
            appReqBlock.getHttpServletRequest().setAttribute("ReportMessage", "Separate Account Values By Case File Has Been Created");
        }

        return SEP_ACCT_VAL_BY_CASE;
    }

    protected String showCaseManagerReviewParams(AppReqBlock appReqBlock) throws Exception
    {
        new BatchUseCaseComponent().runCaseManagerReview();

        this.getMarketingPackageNames(appReqBlock);

        return CASE_MANAGER_REVIEW_PARAMS;
    }

    protected String showFundActivityReportParams(AppReqBlock appReqBlock) throws Exception
    {
        new BatchUseCaseComponent().runFundActivityReport();

        this.getMarketingPackageNames(appReqBlock);

        return FUND_ACTIVITY_REPORT_PARAMS;
    }

    protected String showFundActivityExportParams(AppReqBlock appReqBlock) throws Exception
    {
        new BatchUseCaseComponent().runFundActivityExport();

        this.getMarketingPackageNames(appReqBlock);

        new FundActivityFundNameDoubleTableModel(appReqBlock);

        return FUND_ACTIVITY_EXPORT_PARAMS;
    }

    protected String showAssetLiabilitiesReportParams(AppReqBlock appReqBlock) throws Exception
    {
        new BatchUseCaseComponent().runAssetLiabilitiesReport();

        return ASSET_LIABILITIES_REPORT_PARAMS;
    }

    protected String showPRDParams(AppReqBlock appReqBlock) throws Exception
    {
        new BatchUseCaseComponent().runPRDExtract();

        return PRD_PARAMETERS;
    }

    protected String showPRDCompareParams(AppReqBlock appReqBlock) throws Exception
    {
        new BatchUseCaseComponent().runPRDExtract();

        return PRD_COMPARE_PARAMETERS;
    }

    protected String showMonthlyValuationParams(AppReqBlock appReqBlock) throws Exception
    {
        new BatchUseCaseComponent().runMonthlyValuation();

        return MONTHLY_VALUATION_PARAMETERS;
    }

    protected String showAnnualStatementsParams(AppReqBlock appReqBlock) throws Exception
    {
        new BatchUseCaseComponent().runAnnualStatements();

        return ANNUAL_STATEMENTS_PARAMETERS;
    }

    protected String showPolicyPagesParams(AppReqBlock appReqBlock) throws Exception
    {
        new BatchUseCaseComponent().runPolicyPages();

        return POLICY_PAGES_PARAMETERS;
    }

    protected String runPRDCompareExtract(AppReqBlock appReqBlock) throws Exception
    {
        EDITDate extractDate = DateTimeUtil.getEDITDateFromMMDDYYYY(appReqBlock.getReqParm("extractDate"));
        String extractType = appReqBlock.getReqParm("extractType");

        Batch batchComponent = new BatchComponent();

        try
        {
            batchComponent.runPRDCompareExtract(extractDate.getFormattedDate(), extractType, null, null, Batch.ASYNCHRONOUS);

            String responseMessage = Util.getResourceMessage("batch.job.in.progress");

            appReqBlock.getHttpServletRequest().setAttribute("responseMessage", responseMessage);
        }
        catch (EDITCaseException e)
        {
            System.out.println(e);

            e.printStackTrace();

            appReqBlock.getHttpServletRequest().setAttribute("exportMessage", "Error - PRD Staging Extract Failed - " + e.getMessage());
        }

        return DAILY_SELECTION;
    }

    protected String showPRDCompareExtractParams(AppReqBlock appReqBlock) throws Exception
    {
        new BatchUseCaseComponent().runPRDCompareExtract();

        return PRD_COMPARE_EXTRACT_PARAMETERS;
    }


    protected String runMonthlyValuation(AppReqBlock appReqBlock) throws Exception
    {
        EDITDate extractDate = DateTimeUtil.getEDITDateFromMMDDYYYY(appReqBlock.getReqParm("extractDate"));
        String isAsync = appReqBlock.getReqParm("isAsync");
        String isYev = appReqBlock.getReqParm("isYev");
        String mevContractNumber = appReqBlock.getReqParm("mevContractNumber");

        Batch batchComponent = new BatchComponent();

        try
        {
            batchComponent.runMonthlyValuation(extractDate.getFormattedDate(), isAsync, isYev, mevContractNumber,  null, null, Batch.ASYNCHRONOUS);

            String responseMessage = Util.getResourceMessage("batch.job.in.progress");

            appReqBlock.getHttpServletRequest().setAttribute("responseMessage", responseMessage);
        }
        catch (EDITCaseException e)
        {
            System.out.println(e);

            e.printStackTrace();

            appReqBlock.getHttpServletRequest().setAttribute("exportMessage", "Error - Monthly Valuation Failed - " + e.getMessage());
        }

        return DAILY_SELECTION;
    }
    

    protected String runAnnualStatements(AppReqBlock appReqBlock) throws Exception
    {
        EDITDate extractDate = DateTimeUtil.getEDITDateFromMMDDYYYY(appReqBlock.getReqParm("extractDate"));

        Batch batchComponent = new BatchComponent();

        try
        {
            batchComponent.runAnnualStatements(extractDate.getFormattedDate(), null, null, Batch.ASYNCHRONOUS);

            String responseMessage = Util.getResourceMessage("batch.job.in.progress");

            appReqBlock.getHttpServletRequest().setAttribute("responseMessage", responseMessage);
        }
        catch (EDITCaseException e)
        {
            System.out.println(e);

            e.printStackTrace();

            appReqBlock.getHttpServletRequest().setAttribute("exportMessage", "Error - Annual Statements Failed - " + e.getMessage());
        }

        return DAILY_SELECTION;
    }
    
    
    protected String runPolicyPages(AppReqBlock appReqBlock) throws Exception
    {
        EDITDate extractDate = DateTimeUtil.getEDITDateFromMMDDYYYY(appReqBlock.getReqParm("extractDate"));

        Batch batchComponent = new BatchComponent();

        try
        {
            batchComponent.runPolicyPages(extractDate.getFormattedDate(), null, null, Batch.ASYNCHRONOUS);

            String responseMessage = Util.getResourceMessage("batch.job.in.progress");

            appReqBlock.getHttpServletRequest().setAttribute("responseMessage", responseMessage);
        }
        catch (EDITCaseException e)
        {
            System.out.println(e);

            e.printStackTrace();

            appReqBlock.getHttpServletRequest().setAttribute("exportMessage", "Error - Policy Pages Failed - " + e.getMessage());
        }

        return DAILY_SELECTION;
    }

    protected String runPRDCompare(AppReqBlock appReqBlock) throws Exception
    {
        EDITDate extractDate = DateTimeUtil.getEDITDateFromMMDDYYYY(appReqBlock.getReqParm("extractDate"));

        Batch batchComponent = new BatchComponent();

        try
        {
            batchComponent.runPRDCompare(extractDate.getFormattedDate(), null, null, Batch.ASYNCHRONOUS);

            String responseMessage = Util.getResourceMessage("batch.job.in.progress");

            appReqBlock.getHttpServletRequest().setAttribute("responseMessage", responseMessage);
        }
        catch (EDITCaseException e)
        {
            System.out.println(e);

            e.printStackTrace();

            appReqBlock.getHttpServletRequest().setAttribute("exportMessage", "Error - PRD Compare Failed - " + e.getMessage());
        }

        return DAILY_SELECTION;
    }

    protected String createPRDExtract(AppReqBlock appReqBlock) throws Exception
    {
        EDITDate extractDate = DateTimeUtil.getEDITDateFromMMDDYYYY(appReqBlock.getReqParm("extractDate"));

        Batch batchComponent = new BatchComponent();

        try
        {
        	batchComponent.createPRDExtract(extractDate.getFormattedDate(), null, null, Batch.ASYNCHRONOUS);

            String responseMessage = Util.getResourceMessage("batch.job.in.progress");

            appReqBlock.getHttpServletRequest().setAttribute("responseMessage", responseMessage);
        }
        catch (EDITCaseException e)
        {
            System.out.println(e);

            e.printStackTrace();

            appReqBlock.getHttpServletRequest().setAttribute("exportMessage", "Error - PRD Extract Failed - " + e.getMessage());
        }

        return DAILY_SELECTION;
    }

    protected String showListBillParams(AppReqBlock appReqBlock) throws Exception
    {
        new BatchUseCaseComponent().runListBill();

        return LIST_BILL_PARAMETERS;
    }

    protected String createListBill(AppReqBlock appReqBlock) throws Exception
    {
        EDITDate listBillDate = DateTimeUtil.getEDITDateFromMMDDYYYY(appReqBlock.getReqParm("billDate"));

        Batch batchComponent = new BatchComponent();

        try
        {
            batchComponent.createListBillExtract(listBillDate.getFormattedDate(), null, null, Batch.ASYNCHRONOUS);

            String responseMessage = Util.getResourceMessage("batch.job.in.progress");

            appReqBlock.getHttpServletRequest().setAttribute("responseMessage", responseMessage);
        }
        catch (EDITCaseException e)
        {
            System.out.println(e);

            e.printStackTrace();

            appReqBlock.getHttpServletRequest().setAttribute("exportMessage", "Error - List Bill Extract Failed - " + e.getMessage());
        }

        return DAILY_SELECTION;
    }

    protected String filterFundsForMarketingPackage(AppReqBlock appReqBlock) throws Exception
    {
        getAndSetRequestParamsForFundActivityExportPage(appReqBlock);

        String returnPage = appReqBlock.getReqParm("returnPage");

        this.getMarketingPackageNames(appReqBlock);

        new FundActivityFundNameDoubleTableModel(appReqBlock);

        return returnPage;
    }

    protected String updateFundActivityFundNameDoubleTableModel(AppReqBlock appReqBlock) throws Exception
        {
        getAndSetRequestParamsForFundActivityExportPage(appReqBlock);

        String returnPage = appReqBlock.getReqParm("returnPage");

        getMarketingPackageNames(appReqBlock);

        new FundActivityFundNameDoubleTableModel(appReqBlock).updateState();

        return returnPage;
        }

    protected String showFundActivityExportDateFields(AppReqBlock appReqBlock) throws Exception
        {
        getAndSetRequestParamsForFundActivityExportPage(appReqBlock);

        String returnPage = appReqBlock.getReqParm("returnPage");

        getMarketingPackageNames(appReqBlock);

        new FundActivityFundNameDoubleTableModel(appReqBlock);

        return returnPage;
        }

    private void getAndSetRequestParamsForFundActivityExportPage(AppReqBlock appReqBlock)
        {
        String selectedMarketingPackage = appReqBlock.getReqParm("marketingPackage");

        appReqBlock.getHttpServletRequest().setAttribute("selectedMarketingPackage", selectedMarketingPackage);

        String selectedDateType = appReqBlock.getReqParm("dateType");

        appReqBlock.getHttpServletRequest().setAttribute("selectedDateType", selectedDateType);

        String startDate = appReqBlock.getReqParm("startDate");

        appReqBlock.getHttpServletRequest().setAttribute("startDate", startDate);

        String endDate = appReqBlock.getReqParm("endDate");

        appReqBlock.getHttpServletRequest().setAttribute("endDate", endDate);        
    }

    protected String runCaseManagerReview(AppReqBlock appReqBlock) throws Exception
    {
        String selectedMarketingPackage = appReqBlock.getReqParm("selectedMarketingPackage");
        String selectedFund = appReqBlock.getReqParm("selectedFund");
        EDITDate startDate = DateTimeUtil.getEDITDateFromMMDDYYYY(appReqBlock.getReqParm("startDate"));
        EDITDate endDate = DateTimeUtil.getEDITDateFromMMDDYYYY(appReqBlock.getReqParm("endDate"));
        String dateType = appReqBlock.getReqParm("dateType");

        appReqBlock.getHttpServletRequest().setAttribute("dateType", dateType);
        appReqBlock.getHttpServletRequest().setAttribute("startDate", DateTimeUtil.formatEDITDateAsMMDDYYYY(startDate));
        appReqBlock.getHttpServletRequest().setAttribute("endDate", DateTimeUtil.formatEDITDateAsMMDDYYYY(endDate));

        try
        {
            ReportsProcessor reportsProcessor = new reporting.batch.ReportsProcessor();

            Hashtable caseManagerReviewInfo = reportsProcessor.getFeeAndTrxInfoForReport(selectedMarketingPackage,
                    selectedFund, startDate.getFormattedDate(), endDate.getFormattedDate(), dateType);

            if (caseManagerReviewInfo.size() > 0)
            {
                appReqBlock.getHttpServletRequest().setAttribute("caseManagerReviewInfo", caseManagerReviewInfo);

                return CASE_MANAGER_REVIEW_RPT;
            }
            else
            {
                appReqBlock.getHttpServletRequest().setAttribute("ReportMessage", "Report Not Created - No Data Found");

                return CASE_MANAGER_REVIEW_ERROR;
            }
        }
        catch (Exception e)
        {
            appReqBlock.getHttpServletRequest().setAttribute("ReportMessage", "Error - Report Could Not Be Created - " + e.getMessage());

            return CASE_MANAGER_REVIEW_ERROR;
        }
    }

    protected String runFundActivityReport(AppReqBlock appReqBlock) throws Exception
    {
        String selectedMarketingPackage = appReqBlock.getReqParm("selectedMarketingPackage");
        String selectedFund = appReqBlock.getReqParm("selectedFund");
        EDITDate startDate = DateTimeUtil.getEDITDateFromMMDDYYYY(appReqBlock.getReqParm("startDate"));
        EDITDate endDate = DateTimeUtil.getEDITDateFromMMDDYYYY(appReqBlock.getReqParm("endDate"));
        String dateType = appReqBlock.getReqParm("dateType");

        appReqBlock.getHttpServletRequest().setAttribute("dateType", dateType);
        appReqBlock.getHttpServletRequest().setAttribute("startDate", DateTimeUtil.formatEDITDateAsMMDDYYYY(startDate));
        appReqBlock.getHttpServletRequest().setAttribute("endDate", DateTimeUtil.formatEDITDateAsMMDDYYYY(endDate));
        appReqBlock.getHttpServletRequest().setAttribute("marketingPackage", selectedMarketingPackage);

        try
        {
            ReportsProcessor reportsProcessor = new reporting.batch.ReportsProcessor();

            Hashtable fundActivityReportInfo = reportsProcessor.getFeeAndTrxInfoForReport(selectedMarketingPackage,
                    selectedFund, startDate.getFormattedDate(), endDate.getFormattedDate(), dateType);

            if (fundActivityReportInfo.size() > 0)
            {
                appReqBlock.getHttpServletRequest().setAttribute("fundActivityReportInfo", fundActivityReportInfo);

                return FUND_ACTIVITY_REPORT;
            }
            else
            {
                appReqBlock.getHttpServletRequest().setAttribute("ReportMessage", "Report Not Created - No Data Found");

                return FUND_ACTIVITY_REPORT_ERROR;
            }
        }
        catch (Exception e)
        {
            appReqBlock.getHttpServletRequest().setAttribute("ReportMessage", "Error - Report Could Not Be Created - " + e.getMessage());

            return FUND_ACTIVITY_REPORT_ERROR;
        }
    }

    protected String runFundActivityExport(AppReqBlock appReqBlock) throws Exception
    {
        String returnPage = FUND_ACTIVITY_EXPORT;

        String selectedMarketingPackage = appReqBlock.getReqParm("marketingPackage");

        String[] selectedFunds = new FundActivityFundNameDoubleTableModel(appReqBlock).getSelectedRowIds();

        String selectedDateType = appReqBlock.getReqParm("dateType");

        String startDate = appReqBlock.getReqParm("startDate");

        String endDate = appReqBlock.getReqParm("endDate");

        BusinessCalendar businessCalendarComponent = new BusinessCalendarComponent();

        boolean areBusinessDates = false;

        // If DateType selected is ProcessDate then check if dates entered are business dates.
        if (selectedDateType.equals(ActivityFileExportProcessor.DATE_TYPE_VALUES[1]))
        {
            if (!businessCalendarComponent.isBusinessDay(new EDITDate(startDate)))
            {
                appReqBlock.getHttpServletRequest().setAttribute("responseMessage", "Start Date is not a Business Day.");

                getMarketingPackageNames(appReqBlock);

                getAndSetRequestParamsForFundActivityExportPage(appReqBlock);

                returnPage = FUND_ACTIVITY_EXPORT_PARAMS;
            }
            else if (!businessCalendarComponent.isBusinessDay(new EDITDate(endDate)))
            {
                appReqBlock.getHttpServletRequest().setAttribute("responseMessage", "End Date is not a Business Day");

                getMarketingPackageNames(appReqBlock);

                getAndSetRequestParamsForFundActivityExportPage(appReqBlock);

                returnPage = FUND_ACTIVITY_EXPORT_PARAMS;
            }
            else
            {
                areBusinessDates = true;
            }
        }
        else
        {
            areBusinessDates = true;
        }

        if (areBusinessDates)
        {
            String selectedFundPKsAsString = Util.concatenateStrings(selectedFunds, ",");
        
            Batch batchComponent = new BatchComponent();
    
            try
            {
                batchComponent.runFundActivityExport(selectedMarketingPackage, selectedFundPKsAsString, selectedDateType, startDate, endDate, null, null, Batch.ASYNCHRONOUS);
    
                appReqBlock.getHttpServletRequest().setAttribute("exportMessage", "Export Successfully Created.");
            }
            catch (Exception e)
            {
                System.out.println(e);
    
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
    
                appReqBlock.getHttpServletRequest().setAttribute("exportMessage", "Error - Export Failed - " + e.getMessage());
            }
        }

        return returnPage;
    }

    protected String runAssetLiabilitiesReport(AppReqBlock appReqBlock) throws Exception
    {
        EDITDate date = DateTimeUtil.getEDITDateFromMMDDYYYY(appReqBlock.getReqParm("date"));

        appReqBlock.getHttpServletRequest().setAttribute("date", DateTimeUtil.formatEDITDateAsMMDDYYYY(date));

        try
        {
            ReportsProcessor reportsProcessor = new reporting.batch.ReportsProcessor();

            Hashtable assetsLiabilitiesInfo = reportsProcessor.getInfoForAssetsLiabilitiesReport(date.getFormattedDate());

            if (assetsLiabilitiesInfo.size() > 0)
            {
                appReqBlock.getHttpServletRequest().setAttribute("assetsLiabilitiesReportInfo", assetsLiabilitiesInfo);

                return ASSET_LIABILITIES_REPORT;
            }
            else
            {
                appReqBlock.getHttpServletRequest().setAttribute("ReportMessage", "Report Not Created - No Data Found");

                return ASSET_LIABILITIES_REPORT_ERROR;
            }
        }
        catch (Exception e)
        {
            appReqBlock.getHttpServletRequest().setAttribute("ReportMessage", "Error - Report Could Not Be Created - " + e.getMessage());

            return ASSET_LIABILITIES_REPORT_ERROR;
        }
    }

    protected String showMoneyMoveReportParams(AppReqBlock appReqBlock) throws Exception
    {
        return MONEY_MOVE_REPORT_PARAMS;
    }

    protected String runMoneyMoveReport(AppReqBlock appReqBlock) throws Exception
    {
        String date = DateTimeUtil.initDate(appReqBlock.getReqParm("month"), appReqBlock.getReqParm("day"), appReqBlock.getReqParm("year"), null);

        appReqBlock.getHttpServletRequest().setAttribute("date", date);

        EDITServicesConfig editServicesConfig = ServicesConfig.getEditServicesConfig();
        String reportCompanyName = editServicesConfig.getReportCompanyName();

        if (reportCompanyName != null)
        {
            appReqBlock.getHttpServletRequest().setAttribute("companyName", reportCompanyName);
        }

        try
        {
            ReportsProcessor reportsProcessor = new reporting.batch.ReportsProcessor();

            Hashtable moneyMoveInfo = reportsProcessor.getInfoForMoneyMoveReport(date, "Effective");
            Hashtable cashTransfers = (Hashtable) moneyMoveInfo.get("cashTransfers");
            Hashtable accrualTransfers = (Hashtable) moneyMoveInfo.get("accrualTransfers");

            if ((cashTransfers.size() > 0) || (accrualTransfers.size() > 0))
            {
                appReqBlock.getHttpServletRequest().setAttribute("moneyMoveReportInfo", moneyMoveInfo);

                return MONEY_MOVE_REPORT;
            }
            else
            {
                appReqBlock.getHttpServletRequest().setAttribute("ReportMessage", "Report Not Created - No Data Found");

                return MONEY_MOVE_REPORT_ERROR;
            }
        }
        catch (Exception e)
        {
            appReqBlock.getHttpServletRequest().setAttribute("ReportMessage", "Error - Report Could Not Be Created - " + e.getMessage());

            return MONEY_MOVE_REPORT_ERROR;
        }
    }

    protected String showTransferMemorandumParam(AppReqBlock appReqBlock) throws Exception
    {
        return TRANSFER_MEMORANDUM_PARAM;
    }

    protected String runTransferMemorandum(AppReqBlock appReqBlock) throws Exception
    {
        String date = appReqBlock.getReqParm("date");

        appReqBlock.getHttpServletRequest().setAttribute("date", date);

        EDITServicesConfig editServicesConfig = ServicesConfig.getEditServicesConfig();
        String reportCompanyName = editServicesConfig.getReportCompanyName();

        if (reportCompanyName != null)
        {
            appReqBlock.getHttpServletRequest().setAttribute("companyName", reportCompanyName);
        }

        try
        {
            TransferMemorandum transferMemorandum = new TransferMemorandum(new EDITDate(date));

            transferMemorandum.generateReport();

            if (!transferMemorandum.isReportEmpty())
            {
                appReqBlock.getHttpServletRequest().setAttribute("transferMemorandum", transferMemorandum);

                return TRANSFER_MEMORANDUM;
            }
            else
            {
                appReqBlock.getHttpServletRequest().setAttribute("ReportMessage", "Transfer Memorandum Not Created - No Data Found");

                return TRANSFER_MEMORANDUM_ERROR;
            }
        }
        catch (Exception e)
        {
            appReqBlock.getHttpServletRequest().setAttribute("ReportMessage", "Error - Transfer Memorandum Could Not Be Created - " + e.getMessage());

            return TRANSFER_MEMORANDUM_ERROR;
        }
    }

    //
    //    protected String runWithholdingReport(AppReqBlock appReqBlock) throws Exception {
    //
    //        Contract contractComp = (Contract) appReqBlock.getWebService("contract-service");
    //
    //        FormBean formBean = appReqBlock.getFormBean();
    //
    //        String companyName = formBean.getValue("companyName");
    //
    //        String fromDate    = DateFormatter.
    //                                buildFormattedDateString(formBean.getValue("fromYear"),
    //                                                          formBean.getValue("fromMonth"),
    //                                                           formBean.getValue("fromDay"));
    //
    //        String toDate      = DateFormatter.
    //								buildFormattedDateString(formBean.getValue("toYear"),
    //										                  formBean.getValue("toMonth"),
    //														   formBean.getValue("toDay"));
    //
    //        return contractComp.runWithholdingReport(companyName, fromDate, toDate);
    //    }
    //
    protected PageBean getCompanyNames(AppReqBlock appReqBlock) throws Exception
    {
        String[] companyNames = Company.find_All_CompanyNames();

        PageBean pageBean = new PageBean();

        pageBean.putValues("companyNames", companyNames);

        return pageBean;
    }

    protected void getMarketingPackageNames(AppReqBlock appReqBlock) throws Exception
    {
        engine.business.Lookup calcLookup = new engine.component.LookupComponent();

        ProductStructureVO[] productStructureVOs = calcLookup.getAllProductStructures();

        boolean marketingPackageFound;
        List mktgPkgNamesVector = new ArrayList();
        List productStructVector = new ArrayList();
        String marketingPackageName = null;

        for (int i = 0; i < productStructureVOs.length; i++)
        {
            marketingPackageFound = false;
            marketingPackageName = productStructureVOs[i].getMarketingPackageName();

            for (int j = 0; j < mktgPkgNamesVector.size(); j++)
            {
                if (((String) mktgPkgNamesVector.get(j)).equalsIgnoreCase(marketingPackageName))
                {
                    marketingPackageFound = true;
                    j = mktgPkgNamesVector.size();
                }
            }

            if (!marketingPackageFound)
            {
                mktgPkgNamesVector.add(marketingPackageName);
                productStructVector.add(productStructureVOs[i]);
            }
        }

        appReqBlock.getHttpServletRequest().setAttribute("marketingPackageNames", (String[]) mktgPkgNamesVector.toArray(new String[mktgPkgNamesVector.size()]));
    }

    protected PageBean getUserCompanyNames(AppReqBlock appReqBlock) throws Exception
    {
        String[] companyNames = Company.find_All_CompanyNamesForProductType();

        PageBean pageBean = new PageBean();

        pageBean.putValues("companyNames", companyNames);


        return pageBean;
    }

    protected String showCashClearanceImport(AppReqBlock appReqBlock)
    {
        return CASH_CLEARANCE_IMPORT_PARAMS;
    }

    protected String showHedgeFundPricingReport(AppReqBlock appReqBlock)
    {
        UnitValues[] unitValuesWithHedge = UnitValues.findByUpdateStatus_And_FundType(UnitValues.UPDATESTATUS_HEDGE, Fund.FUNDTYPE_HEDGE);

        UnitValues[] unitValuesWithoutHedge = UnitValues.findByFundType_And_RollingPrevious12Month(Fund.FUNDTYPE_HEDGE);

        appReqBlock.putInRequestScope("unitValuesWithHedge", unitValuesWithHedge);

        appReqBlock.putInRequestScope("unitValuesWithoutHedge", unitValuesWithoutHedge);

        return HEDGE_FUND_PRICING_REPORT;
    }

    protected String runCashClearanceImport(AppReqBlock appReqBlock)
    {
        String ccImportMonth = Util.initString(appReqBlock.getReqParm("ccImportMonth"), null);
        String ccImportDay = Util.initString(appReqBlock.getReqParm("ccImportDay"), null);
        String ccImportYear = Util.initString(appReqBlock.getReqParm("ccImportYear"), null);

        String importDate = DateTimeUtil.initDate(ccImportMonth, ccImportDay, ccImportYear, null);

        Batch batch = new BatchComponent();

        batch.importCashClearanceValues(importDate, null, null, Batch.ASYNCHRONOUS);

        String responseMessage = Util.getResourceMessage("batch.job.in.progress");

        appReqBlock.getHttpServletRequest().setAttribute("responseMessage", responseMessage);

        return DAILY_SELECTION;
    }

     private String analyzeJobToRun(AppReqBlock appReqBlock)
    {
        String returnPage = "";

        String jobName = appReqBlock.getReqParm("jobName");
        if (jobName.equalsIgnoreCase(EQUITY_JOB_NAME));
        {
            returnPage = setupForEquityIndexHedgeAnaylze(appReqBlock);
        }

        ProductStructure[] productStructures = ProductStructure.find_All();

        appReqBlock.getHttpServletRequest().setAttribute("companyStructureVOs", productStructures);

        return returnPage;
    }

    private String setupForEquityIndexHedgeAnaylze(AppReqBlock appReqBlock)
    {
        EDITDate runDate = DateTimeUtil.getEDITDateFromMMDDYYYY(appReqBlock.getReqParm("runDate"));

        String createSubBucketInd = Util.initString(appReqBlock.getReqParm("createSubBucketsInd"), "");
        if (createSubBucketInd.equalsIgnoreCase("on"))
        {
            createSubBucketInd = "Y";
        }
        else
        {
            createSubBucketInd = "N";
        }

        String contractNumber = appReqBlock.getReqParm("contractNumber");
        Segment segment = Segment.findByContractNumber(contractNumber);

        if (segment != null)
        {
            EquityIndexHedgeProcessor equityIndexHedgeProcessor = new EquityIndexHedgeProcessor();

            Analyzer analyzer = equityIndexHedgeProcessor.analyzeEquityHedgeRequest(contractNumber, runDate, createSubBucketInd);

            setupForDebugPage(appReqBlock, analyzer);
        }
        else
        {
            appReqBlock.getHttpServletRequest().setAttribute("errorMessage", "Contract Number Not Found");

        }

        return EQUITY_INDEX_HEDGE_TO_RUN;
    }

    private void setupForDebugPage(AppReqBlock appReqBlock, Analyzer analyzer)
    {
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
        analyzer.resetScriptProcessor();

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
        appReqBlock.getHttpServletRequest().setAttribute("analyzeJobToRun", "true");
    }

    protected String showClientAccountingExtractParams(AppReqBlock appReqBlock) throws Exception
    {
        new BatchUseCaseComponent().runClientAccountingExtract();

        return CLIENT_ACCOUNTING_EXTRACT_PARAMETERS;
    }

    protected String createClientAccountingExtract(AppReqBlock appReqBlock) throws Exception
    {
        EDITDate extractDate = DateTimeUtil.getEDITDateFromMMDDYYYY(appReqBlock.getReqParm("extractDate"));
        String companyName = appReqBlock.getReqParm("companyName");

        Batch batchComponent = new BatchComponent();

        batchComponent.createClientAccountingExtract(extractDate.getFormattedDate(), companyName, null, null, Batch.ASYNCHRONOUS);

        String responseMessage = Util.getResourceMessage("batch.job.in.progress");

        appReqBlock.putInRequestScope("responseMessage", responseMessage);

        return DAILY_SELECTION;
    }

    protected String showCheckNumberImportParams(AppReqBlock appReqBlock) throws Exception
    {
        new BatchUseCaseComponent().runCheckNumberImport();

        PageBean checkNumberImportPageBean = getCompanyNames(appReqBlock);

        appReqBlock.getHttpServletRequest().setAttribute("checkNumberImportPageBean", checkNumberImportPageBean);

        return CHECK_NUMBER_IMPORT_PARAMS;
    }

    protected String runCheckNumberImport(AppReqBlock appReqBlock) throws Exception
    {
        String parameterDate = appReqBlock.getReqParm("parameterDate");
        String companyName = appReqBlock.getReqParm("companyName");

        Batch batchComponent = new BatchComponent();

        batchComponent.runCheckNumberImport(new EDITDate(parameterDate).getFormattedDate(), companyName, null, null, Batch.ASYNCHRONOUS);

        String responseMessage = Util.getResourceMessage("batch.job.in.progress");

        appReqBlock.putInRequestScope("responseMessage", responseMessage);

        return DAILY_SELECTION;
    }

    protected String showAnalyzer(AppReqBlock appReqBlock) throws Exception
    {
        return ANALYZER_DIALOG;
    }

    protected String showBankForNACHA(AppReqBlock appReqBlock) throws Exception
    {
        new BatchUseCaseComponent().runBankingForNACHA();

        String[] companyNames = Company.find_All_CompanyNamesForProductType();

        PageBean pageBean = new PageBean();

        pageBean.putValues("companyNames", companyNames);

        appReqBlock.getHttpServletRequest().setAttribute("bankRunPageBean", pageBean);

        return BANK_RUN_FOR_NACHA;
    }

    protected String createBankForNACHA(AppReqBlock appReqBlock)  throws Exception
    {
        FormBean formBean = appReqBlock.getFormBean();

        String companyName = formBean.getValue("companyName");
        
       
        String isIndividual =  "true";
        String createCashBatchFile = "false";

        if (formBean.getValue("isListBill").equals("on")) {
            isIndividual = "false";
        } else {
        	isIndividual = "true";
        }

        if (formBean.getValue("createCashBatchFile").equals("on")) {
        	createCashBatchFile = "true";
        } else {
        	createCashBatchFile = "false";
        }

        String runAsBatch = (String) appReqBlock.getHttpServletRequest().getAttribute("runAsBatch");

        if (runAsBatch == null)
        {
            runAsBatch = "false"; // Assume false unless otherwise specified.
        }

        EDITDate processDate = DateTimeUtil.getEDITDateFromMMDDYYYY(formBean.getValue("processDate"));

        Batch batch = new BatchComponent();

        batch.createBankForNACHA(companyName, processDate.getFormattedDate(), isIndividual, createCashBatchFile, null, null, Batch.ASYNCHRONOUS);

        String responseMessage = Util.getResourceMessage("batch.job.in.progress");

        appReqBlock.putInRequestScope("responseMessage", responseMessage);

        return DAILY_SELECTION;
    }
}
