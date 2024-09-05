package batch.business;

import edit.services.component.IUseCase;

/**
 * Created by IntelliJ IDEA.
 * User: gfrosti
 * Date: Mar 24, 2004
 * Time: 12:20:37 PM
 * To change this template use File | Settings | File Templates.
 */
public interface BatchUseCase extends IUseCase
{
    // Below are releated to Access Daily Batch

    public void accessDailyBatch();

    public void runBanking();

    public void runAccounting();

    public void runConfirms();

    public void runCorrespondence();

    public void runBatch();

    public void runChartOfAccountsReport();

    public void runAccountingDetailReport();

    public void runFinancialActivityReport();

    public void runPayoutTransactionReport();

    public void runControlsAndBalancesReport();

    public void runWithholdingReport();

    public void runEquityIndexHedge();

    // The below are related to Access On Request Batch

    public void accessOnRequestBatch();

    public void runAgentUpdate();

    public void runCheckTransactionCreation();

    public void runProcessChecks();

    public void runCommissionStatements();

    public void runBonusCommissions();

    public void runValuation();

    public void runYearEndTaxReporting();
    
    public void runCaseManagerReview();

    public void runFundActivityReport();

    public void runFundActivityExport();

    public void runAssetLiabilitiesReport();

    public void runMoneyMoveReport();

    public void runSepAcctValByDiv();

    public void runSepAcctValByCase();

    public void runReinsuranceChecks();

    public void runPRDExtract();

    public void runListBill();

    public void runAlphaExtract();

    public void runPendingRequirementsExtract();

    public void runClientAccountingExtract();

    public void runDataWarehouse();

    public void runCheckNumberImport();

    public void runManualAccountingImport();

    public void runWorksheet();

    public void runBankingForNACHA();
}
