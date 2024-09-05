/*
 * User: dlataill
 * Date: Mar 22, 2002
 * Time: 2:48:11 PM
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package reporting.ui.servlet;

import agent.business.*;

import agent.component.*;

import batch.business.*;

import batch.component.*;
import batch.component.BatchUseCaseComponent;

import edit.common.*;

import edit.common.vo.*;

import edit.portal.common.transactions.Transaction;
import edit.portal.common.session.*;

import fission.beans.*;

import fission.global.*;

import fission.utility.*;

import role.business.*;

import role.component.*;

import java.io.*;

import java.util.*;

import engine.*;
import reporting.component.*;


public class ReportingDetailTran extends Transaction
{
    //These are the Actions of the Dialog Screen
    private static final String SHOW_RESERVES = "showReserves";
    private static final String SHOW_YE_TAX_REPORTING = "showYETaxReporting";
    private static final String SHOW_REPORTING_MAIN = "showReportingMain";
    private static final String PROCESS_RESERVES = "processReserves";
    private static final String PROCESS_YE_TAX_REPORTS = "processYETaxReports";
    private static final String SHOW_VALUATION_PARAMS = "showValuationParamsSelectionPage";
    private static final String RUN_VALUATION = "runValuation";
    private static final String SHOW_AGENT_UPDATE = "showAgentUpdate";
    private static final String SHOW_BONUS_COMMISSIONS = "showBonusCommissions";
    private static final String SHOW_COMMISSION_CHECKS = "showCommissionChecks";
    private static final String SHOW_COMMISSION_EFT = "showCommissionEFT";
    private static final String SHOW_PROCESS_CHECKS = "showProcessChecks";
    private static final String RUN_AGENT_UPDATE = "runAgentUpdate";
    private static final String RUN_BONUS_COMMISSIONS = "runBonusCommissions";
    private static final String CREATE_COMMISSION_CHECK_CK = "createCommissionCheckCK";
    private static final String CREATE_COMMISSION_EFT_CK = "createCommissionEFTCK";
    private static final String PROCESS_COMMISSION_CHECKS = "processCommissionChecks";
    private static final String SHOW_COMMISSION_STATEMENTS = "showCommissionStatements";
    private static final String RUN_COMMISSION_STATEMENTS = "runCommissionStatements";
    private static final String SHOW_YEAR_END_CLIENT_BALANCE = "showYearEndClientBalance";
    private static final String RUN_YEAR_END_CLIENT_BALANCE = "runYearEndClientBalance";
    private static final String SHOW_CLOSE_ACCOUNTING = "showCloseAccounting";
    private static final String CLOSE_ACCOUNTING = "closeAccounting";
    private static final String SHOW_ACCOUNTING_EXTRACT_PARAMS = "showAccountingExtractParams";
    private static final String RUN_ACCOUNTING_EXTRACT = "runAccountingExtract";
    private static final String SHOW_RMD_NOTIFICATIONS = "showRmdNotifications";
    private static final String RUN_RMD_NOTIFICATION = "runRmdNotification";
    private static final String SHOW_COI_REPLENISHMENT = "showCoiReplenishment";
    private static final String RUN_COI_REPLENISHMENT = "runCoiReplenishment";
    private static final String RUN_TAX_EXTRACT = "runTaxExtract";
    private static final String UPDATE_AGENT_BONUSES = "updateAgentBonuses";
    private static final String SHOW_UPDATE_AGENT_BONUSES = "showUpdateAgentBonuses";
    private static final String SHOW_RUN_AGENT_BONUS_CHECKS = "showRunAgentBonusChecks";
    private static final String RUN_AGENT_BONUS_CHECKS = "runAgentBonusChecks";
    private static final String SHOW_BONUS_COMMISSION_STATEMENTS = "showBonusCommissionStatements";
    private static final String RUN_BONUS_COMMISSION_STATEMENTS = "runBonusCommissionStatements";
    private static final String SHOW_RUN_REINSURANCE_CHECKS = "showRunReinsuranceChecks";
    private static final String PROCESS_REINSURANCE_CHECKS = "processReinsuranceChecks";
    private static final String SHOW_RUN_ALPHA_EXTRACT = "showRunAlphaExtract";
    private static final String RUN_ALPHA_EXTRACT = "runAlphaExtract";
    private static final String SHOW_PENDING_REQ_EXTRACT_PARAMS = "showPendingReqExtractParam";
    private static final String RUN_PENDING_REQUIREMENTS_EXTRACT = "runPendingRequirementsExtract";
    private static final String SHOW_CASH_BATCH_IMPORT = "showCashBatchImport";
    private static final String IMPORT_CASH_BATCH = "importCashBatch";
    private static final String SHOW_DATA_WAREHOUSE_PARAMS = "showDataWarehouseParams";
    private static final String RUN_DATA_WAREHOUSE = "runDataWarehouse";
    private static final String SHOW_MANUAL_ACCOUNTING_IMPORT_PARAMS = "showManualAccountingImportParams";
    private static final String RUN_MANUAL_ACCOUNTING_IMPORT = "runManualAccountingImport";
    private static final String SHOW_WORKSHEET_PARAMS = "showWorksheetParams";
    private static final String RUN_WORKSHEET = "runWorksheet";

    private static final String REPORTING_MAIN = "/reporting/jsp/reportingSelection.jsp";
    private static final String RUN_RESERVES = "/reporting/jsp/reserves.jsp";
    private static final String YE_TAX_REPORTING = "/reporting/jsp/YETaxReportingRun.jsp";
    private static final String YEAR_END_TAX_OUTPUT_DIALOG = "/reporting/jsp/yearEndTaxOutputDialog.jsp";
    private static final String VALUATION_PARAMS_SELECTION_PAGE = "/reporting/jsp/valuationParamsSelection.jsp";
    private static final String VALUATION_COMPLETE = "/reporting/jsp/valuationComplete.jsp";
    private static final String AGENT_UPDATE = "/reporting/jsp/agentUpdate.jsp";
    private static final String BONUS_COMMISSIONS = "/reporting/jsp/bonusCommissions.jsp";
    private static final String COMMISSION_CHECKS = "/reporting/jsp/commissionChecks.jsp";
    private static final String COMMISSION_EFT = "/reporting/jsp/commissionEFT.jsp";
    private static final String PROCESS_CHECKS = "/reporting/jsp/processChecks.jsp";
    private static final String COMMISSION_STATEMENTS = "/reporting/jsp/commissionStatements.jsp";
    private static final String YEAR_END_CLIENT_BALANCE = "/reporting/jsp/yearEndClientBalance.jsp";
    private static final String CLOSE_ACCOUNTING_PARAMS = "/reporting/jsp/closeAccountingParams.jsp";
    private static final String CLOSE_ACCOUNTING_COMPLETE = "/reporting/jsp/closeAccountingComplete.jsp";
    private static final String ACCOUNTING_EXTRACT_PARAMS = "/reporting/jsp/accountingExtractParams.jsp";
    private static final String ACCOUNTING_EXTRACT_COMPLETE = "/reporting/jsp/accountingExtractComplete.jsp";
    private static final String RMD_NOTIFICATION_PARAMS = "/reporting/jsp/rmdNotificationParams.jsp";
    private static final String RMD_NOTIFICATION_COMPLETE = "/reporting/jsp/rmdNotificationComplete.jsp";
    private static final String COI_REPLENISHMENT_PARAMS = "/reporting/jsp/coiReplenishmentParams.jsp";
    private static final String COI_REPLENISHMENT_COMPLETE = "/reporting/jsp/coiReplenishmentComplete.jsp";
    private static final String STAGE_TABLES_JOB = "/reporting/jsp/stageTablesJob.jsp";
    private static final String UPDATE_AGENT_BONUSES_JOB = "/reporting/jsp/updateAgentBonusesJob.jsp";
    private static final String RUN_AGENT_BONUS_CHECKS_JSP = "/reporting/jsp/runAgentBonusChecks.jsp";
    private static final String BONUS_COMMISSION_STATEMENTS_JSP = "/reporting/jsp/bonusCommissionStatements.jsp";
    private static final String PROCESS_REINSURANCE_CHECKS_JSP = "/reporting/jsp/processReinsuranceChecks.jsp";
    private static final String ALPHA_EXTRACT_PARAM_JSP = "/reporting/jsp/alphaExtractParam.jsp";
    private static final String CASH_BATCH_IMPORT = "/reporting/jsp/cashBatchImport.jsp";
    private static final String DATA_WAREHOUSE_PARAMS_JSP = "/reporting/jsp/dataWarehouseParameters.jsp";
    private static final String PENDING_REQ_EXTRACT_PARAMS_JSP = "/reporting/jsp/pendingReqExtractParams.jsp";
    private static final String MANUAL_ACCOUNTING_IMPORT_PARAMS_JSP = "/reporting/jsp/manualAccountingImportParams.jsp";
    private static final String WORKSHEET_PARAMS_JSP = "/reporting/jsp/worksheetParameters.jsp";

    public String execute(AppReqBlock appReqBlock) throws Exception
    {
        String action = appReqBlock.getReqParm("action");

        if (action.equals(SHOW_RESERVES))
        {
            return showReserves(appReqBlock);
        }
        else if (action.equals(SHOW_YE_TAX_REPORTING))
        {
            return showYETaxReporting(appReqBlock);
        }
        else if (action.equals(SHOW_REPORTING_MAIN))
        {
            return showReportingMain(appReqBlock);
        }
        else if (action.equals(PROCESS_RESERVES))
        {
            return processReserves(appReqBlock);
        }
        else if (action.equals(PROCESS_YE_TAX_REPORTS))
        {
            return processYETaxReports(appReqBlock);
        }
        else if (action.equals(SHOW_VALUATION_PARAMS))
        {
            return showValuationParamsSelectionPage(appReqBlock);
        }
        else if (action.equals(RUN_VALUATION))
        {
            return runValuation(appReqBlock);
        }
        else if (action.equals(SHOW_AGENT_UPDATE))
        {
            return showAgentUpdate(appReqBlock);
        }
        else if (action.equals(SHOW_BONUS_COMMISSIONS))
        {
            return showBonusCommissions(appReqBlock);
        }
        else if (action.equals(SHOW_COMMISSION_CHECKS))
        {
            return showCommissionChecks(appReqBlock);
        }
        else if (action.equals(SHOW_COMMISSION_EFT))
        {
            return showCommissionEFT(appReqBlock);
        }
        else if (action.equals(SHOW_PROCESS_CHECKS))
        {
            return showProcessChecks(appReqBlock);
        }
        else if (action.equals(RUN_AGENT_UPDATE))
        {
            return runAgentUpdate(appReqBlock);
        }
        else if (action.equals(RUN_BONUS_COMMISSIONS))
        {
            return runBonusCommissions(appReqBlock);
        }
        else if (action.equals(CREATE_COMMISSION_CHECK_CK))
        {
            return createCommissionCheckCK(appReqBlock);
        }
        else if (action.equals(CREATE_COMMISSION_EFT_CK))
        {
            return createCommissionEFTCK(appReqBlock);
        }
        else if (action.equals(PROCESS_COMMISSION_CHECKS))
        {
            return processCommissionChecks(appReqBlock);
        }
        else if (action.equals(SHOW_COMMISSION_STATEMENTS))
        {
            return showCommissionStatements(appReqBlock);
        }
        else if (action.equals(RUN_COMMISSION_STATEMENTS))
        {
            return runCommissionStatements(appReqBlock);
        }
        else if (action.equals(SHOW_YEAR_END_CLIENT_BALANCE))
        {
            return showYearEndClientBalance(appReqBlock);
        }
        else if (action.equals(RUN_YEAR_END_CLIENT_BALANCE))
        {
            return runYearEndClientBalance(appReqBlock);
        }
        else if (action.equals(SHOW_CLOSE_ACCOUNTING))
        {
            return showCloseAccounting(appReqBlock);
        }
        else if (action.equals(CLOSE_ACCOUNTING))
        {
            return closeAccounting(appReqBlock);
        }
        else if (action.equals(SHOW_ACCOUNTING_EXTRACT_PARAMS))
        {
            return showAccountingExtractParams();
        }
        else if (action.equals(RUN_ACCOUNTING_EXTRACT))
        {
            return runAccountingExtract(appReqBlock);
        }
        else if (action.equals(SHOW_RMD_NOTIFICATIONS))
        {
            return showRmdNotifications(appReqBlock);
        }
        else if (action.equals(RUN_RMD_NOTIFICATION))
        {
            return runRmdNotification(appReqBlock);
        }
        else if (action.equals(SHOW_COI_REPLENISHMENT))
        {
            return showCoiReplenishment(appReqBlock);
        }
        else if (action.equals(RUN_COI_REPLENISHMENT))
        {
            return runCoiReplenishment(appReqBlock);
        }
        else if (action.equals(RUN_TAX_EXTRACT))
        {
            return runTaxExtract(appReqBlock);
        }
        else if (action.equals(UPDATE_AGENT_BONUSES))
        {
            return updateAgentBonuses(appReqBlock);
        }
        else if (action.equals(SHOW_UPDATE_AGENT_BONUSES))
        {
            return showUpdateAgentBonuses(appReqBlock);
        }
        else if (action.equals(SHOW_RUN_AGENT_BONUS_CHECKS))
        {
            return showRunAgentBonusChecks(appReqBlock);
        }
        else if (action.equals(RUN_AGENT_BONUS_CHECKS))
        {
            return runAgentBonusChecks(appReqBlock);
        }
        else if (action.equals(SHOW_BONUS_COMMISSION_STATEMENTS))
        {
            return showBonusCommissionStatements(appReqBlock);
        }
        else if (action.equals(RUN_BONUS_COMMISSION_STATEMENTS))
        {
            return runBonusCommissionStatements(appReqBlock);
        }
        else if (action.equals(SHOW_RUN_REINSURANCE_CHECKS))
        {
            return showRunReinsuranceChecks(appReqBlock);
        }
        else if (action.equals(PROCESS_REINSURANCE_CHECKS))
        {
            return processReinsuranceChecks(appReqBlock);
        }
        else if (action.equals(SHOW_RUN_ALPHA_EXTRACT))
        {
            return showRunAlphaExtract(appReqBlock);
        }
        else if (action.equals(RUN_ALPHA_EXTRACT))
        {
            return runAlphaExtract(appReqBlock);
        }
        else if (action.equals(SHOW_PENDING_REQ_EXTRACT_PARAMS))
        {
            return showPendingRequirementsExtractParams(appReqBlock);
        }
        else if (action.equals(RUN_PENDING_REQUIREMENTS_EXTRACT))
        {
            return runPendingRequirementsExtract(appReqBlock);
        }
        else if (action.equals(SHOW_CASH_BATCH_IMPORT))
        {
            return showCashBatchImport(appReqBlock);
        }
        else if (action.equals(IMPORT_CASH_BATCH))
        {
            return importCashBatch(appReqBlock);
        }
        else if (action.equals(SHOW_DATA_WAREHOUSE_PARAMS))
        {
            return showDataWarehouseParams(appReqBlock);
        }
        else if (action.equalsIgnoreCase(RUN_DATA_WAREHOUSE))
        {
            return runDataWarehouse(appReqBlock);
        }
        else if (action.equalsIgnoreCase(SHOW_MANUAL_ACCOUNTING_IMPORT_PARAMS))
        {
            return showManualAccountingImportParams(appReqBlock);
        }
        else if (action.equalsIgnoreCase(RUN_MANUAL_ACCOUNTING_IMPORT))
        {
            return runManualAccountingImport(appReqBlock);
        }
        else if (action.equalsIgnoreCase(SHOW_WORKSHEET_PARAMS))
        {
            return showWorksheetParams(appReqBlock);
        }
        else if (action.equalsIgnoreCase(RUN_WORKSHEET))
        {
            return runWorksheet(appReqBlock);
        }
        else
        {
            return REPORTING_MAIN;
        }
    }

    /**
     * Initiates the process to build a bonus commission report for all ParticipatingAgents.
     * @param appReqBlock
     * @return
     */
    private String runBonusCommissionStatements(AppReqBlock appReqBlock) throws Exception
    {
        new BatchUseCaseComponent().accessOnRequestBatch();

        String contractCodeCT = Util.initString(appReqBlock.getReqParm("companyName"), null);

//        String processDate = appReqBlock.getReqParm("processDate");
        EDITDate processDate = DateTimeUtil.getEDITDateFromMMDDYYYY(appReqBlock.getReqParm("processDate"));

        String mode = appReqBlock.getReqParm("mode");

        Batch batch = new BatchComponent();

        batch.generateBonusCommissionStatements(contractCodeCT, processDate.getFormattedDate(), mode, null, null, Batch.ASYNCHRONOUS);

        String responseMessage = Util.getResourceMessage("batch.job.in.progress");

        appReqBlock.getHttpServletRequest().setAttribute("responseMessage", responseMessage);

        return REPORTING_MAIN;
    }

    /**
     * Renders the kick-off screen for the Bonus Commission Statements.
     * @param appReqBlock
     * @return
     */
    private String showBonusCommissionStatements(AppReqBlock appReqBlock)
    {
        return BONUS_COMMISSION_STATEMENTS_JSP;
    }

    /**
     * Kicks-off the process that builds Agent Bonus Check transactions and executes them if real time.
     * @param appReqBlock
     * @return
     */
    private String runAgentBonusChecks(AppReqBlock appReqBlock)
    {
        new BatchUseCaseComponent().accessOnRequestBatch();

//        String processDate = appReqBlock.getReqParm("processDate");
        EDITDate processDate = DateTimeUtil.getEDITDateFromMMDDYYYY(appReqBlock.getReqParm("processDate"));

        String frequencyCT = appReqBlock.getReqParm("frequencyCT");

        String username = appReqBlock.getUserSession().getUsername();

        Batch batch = new BatchComponent();

        batch.processAgentBonusChecks(processDate.getFormattedDate(), frequencyCT, username, null, null, Batch.ASYNCHRONOUS);

        String responseMessage = Util.getResourceMessage("batch.job.in.progress");

        appReqBlock.putInRequestScope("responseMessage", responseMessage);

        return REPORTING_MAIN;
    }

    /**
     * Returns the page that kicks-off the Agent Bonus Checks job.
     * @param appReqBlock
     * @return
     */
    private String showRunAgentBonusChecks(AppReqBlock appReqBlock)
    {
        return RUN_AGENT_BONUS_CHECKS_JSP;
    }

    /**
     *
     * @param appReqBlock
     * @return
     */
    private String updateAgentBonuses(AppReqBlock appReqBlock)
    {
        Batch batch = new BatchComponent();

        batch.updateAgentBonuses(null, null, Batch.ASYNCHRONOUS);

        String responseMessage = Util.getResourceMessage("batch.job.in.progress");

        appReqBlock.putInRequestScope("responseMessage", responseMessage);

        return REPORTING_MAIN;
    }

    /**
     * Returns the kick-off page for the Agent Bonus Update process.
     * @param appReqBlock
     * @return
     */
    private String showUpdateAgentBonuses(AppReqBlock appReqBlock)
    {
        new BatchUseCaseComponent().accessOnRequestBatch();

        return UPDATE_AGENT_BONUSES_JOB;
    }

    /**
     * Returns the kick-off page for the staging process.
     * @param appReqBlock
     * @return
     */
    private String showStageTables(AppReqBlock appReqBlock)
    {
        return STAGE_TABLES_JOB;
    }

    protected String runYearEndClientBalance(AppReqBlock appReqBlock) throws Exception
    {
        String message = null;

        try
        {
            Role roleComponent = new RoleComponent();

            roleComponent.runYearEndClientBalance();

            message = "Year-End Client Balance Successfully Completed";
        }
        catch (Exception e)
        {
          System.out.println(e);

            e.printStackTrace(); //To change body of catch statement use Options | File Templates.

            throw e;
        }

        appReqBlock.getHttpServletRequest().setAttribute("message", message);

        return showYearEndClientBalance(appReqBlock);
    }

    protected String showYearEndClientBalance(AppReqBlock appReqBlock) throws Exception
    {
        return YEAR_END_CLIENT_BALANCE;
    }

    protected String runCommissionStatements(AppReqBlock appReqBlock)
    {
        String contractCodeCT = appReqBlock.getReqParm("contractCodeCT");

        EDITDate processDate = DateTimeUtil.getEDITDateFromMMDDYYYY(appReqBlock.getReqParm("processDate"));

        String paymentModeCT = appReqBlock.getReqParm("paymentModeCT");

        String outputFileType = Util.initString(appReqBlock.getReqParm(("outputFileType")), "XML");

        Batch batch = new BatchComponent();

        batch.generateCommissionStatements(contractCodeCT, paymentModeCT, processDate.getFormattedDate(), outputFileType, null, null, Batch.ASYNCHRONOUS);

        String responseMessage = Util.getResourceMessage("batch.job.in.progress");

        appReqBlock.getHttpServletRequest().setAttribute("responseMessage", responseMessage);

        return REPORTING_MAIN;
    }

    protected String showCommissionStatements(AppReqBlock appReqBlock) throws Exception
    {
        new BatchUseCaseComponent().runCommissionStatements();

        appReqBlock.getHttpServletRequest().setAttribute("companyNames", findAllProductStructureVOs(false, null));


        return COMMISSION_STATEMENTS;
    }

    protected String createCommissionCheckCK(AppReqBlock appReqBlock) throws Exception
    {
        String paymentModeCT = appReqBlock.getReqParm("paymentModeCT");
        String forceOutMinBal = appReqBlock.getReqParm("forceOutMinBal");

        String operator = appReqBlock.getUserSession().getUsername();

        Batch batch = new BatchComponent();

        batch.setupAgentCommissionChecksCK(paymentModeCT, forceOutMinBal, operator, null, null, Batch.ASYNCHRONOUS);

        String responseMessage = Util.getResourceMessage("batch.job.in.progress");

        appReqBlock.getHttpServletRequest().setAttribute("responseMessage", responseMessage);

        return REPORTING_MAIN;
    }

    protected String createCommissionEFTCK(AppReqBlock appReqBlock) throws Exception
    {
        String paymentModeCT = appReqBlock.getReqParm("paymentModeCT");
        String forceOutMinBal = appReqBlock.getReqParm("forceOutMinBal");

        String operator = appReqBlock.getUserSession().getUsername();

        Batch batch = new BatchComponent();

        batch.setupAgentCommissionChecksEFT(paymentModeCT, forceOutMinBal, operator, null, null, Batch.ASYNCHRONOUS);

        String responseMessage = Util.getResourceMessage("batch.job.in.progress");

        appReqBlock.getHttpServletRequest().setAttribute("responseMessage", responseMessage);

        return REPORTING_MAIN;
    }

    protected String processCommissionChecks(AppReqBlock appReqBlock) throws Exception
    {
        PageBean formBean = appReqBlock.getFormBean();

        EDITDate processDate = DateTimeUtil.getEDITDateFromMMDDYYYY(formBean.getValue("processDate"));

        Batch batch = new BatchComponent();

        batch.processCommissionChecks(processDate.getFormattedDate(), null, null, Batch.ASYNCHRONOUS);

        String responseMessage = Util.getResourceMessage("batch.job.in.progress");

        appReqBlock.getHttpServletRequest().setAttribute("responseMessage", responseMessage);

        return REPORTING_MAIN;
    }

    protected String runBonusCommissions(AppReqBlock appReqBlock) throws Exception
    {
        String message = null;

        String companyName = appReqBlock.getReqParm("companyName");

//        String fromMonth = appReqBlock.getReqParm("fromMonth");
//        String fromDay = appReqBlock.getReqParm("fromDay");
//        String fromYear = appReqBlock.getReqParm("fromYear");
//        EDITDate fromDate = new EDITDate(fromYear, fromMonth, fromDay);
        EDITDate fromDate = DateTimeUtil.getEDITDateFromMMDDYYYY(appReqBlock.getReqParm("fromDate"));

//        String toMonth = appReqBlock.getReqParm("toMonth");
//        String toDay = appReqBlock.getReqParm("toDay");
//        String toYear = appReqBlock.getReqParm("toYear");
//        EDITDate toDate = new EDITDate(toYear, toMonth, toDay);
        EDITDate toDate = DateTimeUtil.getEDITDateFromMMDDYYYY(appReqBlock.getReqParm("toDate"));

        try
        {
            Agent agent = new AgentComponent();

            agent.updateBonusCommissions(companyName, fromDate.getFormattedDate(), toDate.getFormattedDate());

            message = "Bonus Commission Updates Were Completed";

            appReqBlock.getHttpServletRequest().setAttribute("message", message);
        }
        catch (Exception e)
        {
          System.out.println(e);

            e.printStackTrace();
            throw e;
        }

        return BONUS_COMMISSIONS;
    }

    protected String runAgentUpdate(AppReqBlock appReqBlock) throws Exception
    {
        String companyName = appReqBlock.getReqParm("companyName");

        Batch batch = new BatchComponent();

        batch.updateAgentCommissions(companyName, null, null, Batch.ASYNCHRONOUS);

        String responseMessage = Util.getResourceMessage("batch.job.in.progress");

        appReqBlock.getHttpServletRequest().setAttribute("responseMessage", responseMessage);

        return REPORTING_MAIN;
    }

    protected String showCommissionChecks(AppReqBlock appReqBlock) throws Exception
    {
        new BatchUseCaseComponent().runCheckTransactionCreation();

        appReqBlock.getHttpServletRequest().setAttribute("companyNames", findAllProductStructureVOs(false, null));

        return COMMISSION_CHECKS;
    }

    protected String showCommissionEFT(AppReqBlock appReqBlock) throws Exception
    {
        new BatchUseCaseComponent().runCheckTransactionCreation();

        appReqBlock.getHttpServletRequest().setAttribute("companyNames", findAllProductStructureVOs(false, null));

        return COMMISSION_EFT;
    }

    protected String showProcessChecks(AppReqBlock appReqBlock) throws Exception
    {
        new BatchUseCaseComponent().runProcessChecks();

        return PROCESS_CHECKS;
    }

    protected String showBonusCommissions(AppReqBlock appReqBlock) throws Exception
    {
        new BatchUseCaseComponent().runBonusCommissions();

        appReqBlock.getHttpServletRequest().setAttribute("companyNames", findAllProductStructureVOs(false, null));

        return BONUS_COMMISSIONS;
    }

    protected String showAgentUpdate(AppReqBlock appReqBlock) throws Exception
    {
        new BatchUseCaseComponent().runAgentUpdate();

        appReqBlock.getHttpServletRequest().setAttribute("companyNames", findAllProductStructureVOs(false, null));

        return AGENT_UPDATE;
    }

    protected String showReserves(AppReqBlock appReqBlock) throws Exception
    {
        engine.business.Lookup engineLookup = new engine.component.LookupComponent();

        ProductStructureVO[] productStructureVO = engineLookup.getAllProductStructureNames();

        if (productStructureVO != null)
        {
            for (int i = 0; i < productStructureVO.length; i++)
            {
            }
        }


        ProductStructureVO[] productStructureVOs = engineLookup.getAllProductStructures();

        boolean companyNameFound;
        List companyNamesVector = new ArrayList();
        List productStructVector = new ArrayList();
        String companyName = null;

        for (int i = 0; i < productStructureVOs.length; i++)
        {
            companyNameFound = false;
            Company company = Company.findByPK(new Long(productStructureVOs[i].getCompanyFK()));
            companyName = company.getCompanyName();

            for (int j = 0; j < companyNamesVector.size(); j++)
            {
                if (((String) companyNamesVector.get(j)).equalsIgnoreCase(companyName))
                {
                    companyNameFound = true;
                    j = companyNamesVector.size();
                }
            }

            if (!companyNameFound)
            {
                companyNamesVector.add(companyName);
                productStructVector.add(productStructureVOs[i]);
            }
        }

        productStructureVOs = null;
        productStructureVOs = (ProductStructureVO[]) productStructVector.toArray(new ProductStructureVO[productStructVector.size()]);

        PageBean reservesPageBean = new PageBean();

        reservesPageBean.putValues("companyNames", productStructureVOs, new String[] { "getCompanyName" }, null);

        appReqBlock.getHttpServletRequest().setAttribute("reservesPageBean", reservesPageBean);

        return RUN_RESERVES;
    }

    protected String showYETaxReporting(AppReqBlock appReqBlock) throws Exception
    {
        new BatchUseCaseComponent().runYearEndTaxReporting();

        engine.business.Lookup engineLookup = (engine.component.LookupComponent) appReqBlock.getWebService("engine-lookup");

        ProductStructureVO[] productStructureVOs = engineLookup.getAllProductStructureNames();

        boolean companyNameFound;
        List companyNamesVector = new ArrayList();
        List productStructVector = new ArrayList();
        String companyName = null;

        for (int i = 0; i < productStructureVOs.length; i++)
        {
            companyNameFound = false;
            Company company = Company.findByPK(new Long(productStructureVOs[i].getCompanyFK()));
            companyName = company.getCompanyName();

            for (int j = 0; j < companyNamesVector.size(); j++)
            {
                if (((String) companyNamesVector.get(j)).equalsIgnoreCase(companyName))
                {
                    companyNameFound = true;
                    j = companyNamesVector.size();
                }
            }

            if (!companyNameFound)
            {
                companyNamesVector.add(companyName);
                productStructVector.add(productStructureVOs[i]);
            }
        }

        productStructureVOs = null;
        productStructureVOs = (ProductStructureVO[]) productStructVector.toArray(new ProductStructureVO[productStructVector.size()]);

        engine.business.Lookup calcLookup = new engine.component.LookupComponent();

        ProductStructureVO[] productStructureVOss = calcLookup.getAllProductStructures();
        appReqBlock.getHttpServletRequest().setAttribute("productStructureVOs", productStructureVOss);

        return YE_TAX_REPORTING;
    }

    /**
     * Get the selected product structure, startDate and endDate to execute the GAAP Premium Extract
     * @param appReqBlock
     * @return  gaapPremiumExtractToRun.jsp
     * @throws Exception
     */
    private String runTaxExtract(AppReqBlock appReqBlock) throws Exception
    {
        String productStructure = appReqBlock.getReqParm("productStructure");
        String taxReportType = appReqBlock.getReqParm("taxReportType");
        String taxYear = appReqBlock.getReqParm("taxYear");

//        String startMonth = appReqBlock.getReqParm("startMonth");
//        String startDay = appReqBlock.getReqParm("startDay");
//        String startYear = appReqBlock.getReqParm("startYear");
//        String startDate = DateTimeUtil.initDate(startMonth, startDay, startYear, null);
//
//        String endMonth = appReqBlock.getReqParm("endMonth");
//        String endDay = appReqBlock.getReqParm("endDay");
//        String endYear = appReqBlock.getReqParm("endYear");
//        String endDate = DateTimeUtil.initDate(endMonth, endDay, endYear, null);
        EDITDate startDate = DateTimeUtil.getEDITDateFromMMDDYYYY(appReqBlock.getReqParm("startDate"));

        EDITDate endDate = DateTimeUtil.getEDITDateFromMMDDYYYY(appReqBlock.getReqParm("endDate"));
        String fileType = appReqBlock.getReqParm("fileType");

        Batch batch = new BatchComponent();

        batch.createTaxReservesExtract(startDate.getFormattedDate(), endDate.getFormattedDate(), productStructure, taxReportType, taxYear, fileType, null, null, Batch.ASYNCHRONOUS);

        String responseMessage = Util.getResourceMessage("batch.job.in.progress");

        appReqBlock.putInRequestScope("responseMessage", responseMessage);

        return REPORTING_MAIN;
    }

    protected String showReportingMain(AppReqBlock appReqBlock) throws Exception
    {
        return REPORTING_MAIN;
    }

    protected String processReserves(AppReqBlock appReqBlock) throws Exception
    {
        CodeTableWrapper codeTableWrapper = CodeTableWrapper.getSingleton();

        FormBean formBean = appReqBlock.getFormBean();

        String companyName = formBean.getValue("companyName");

        String effectiveDate = DateTimeUtil.initDate(formBean.getValue("effectiveMonth"), formBean.getValue("effectiveDay"), formBean.getValue("effectiveYear"), null);

        engine.business.Lookup engineLookup = (engine.component.LookupComponent) appReqBlock.getWebService("engine-lookup");

        ProductStructureVO[] productStructureVOs = engineLookup.getAllProductStructuresByCoName(companyName);

        List csIds = new ArrayList();

        for (int i = 0; i < productStructureVOs.length; i++)
        {
            Company company = Company.findByPK(new Long(productStructureVOs[i].getCompanyFK()));
            if (companyName.equals(company.getCompanyName()))
            {
                csIds.add(new Long(productStructureVOs[i].getProductStructurePK()));
            }
        }

        long[] productStructureIds = new long[csIds.size()];

        for (int l = 0; l < csIds.size(); l++)
        {
            productStructureIds[l] = ((Long) csIds.get(l)).longValue();
        }

        contract.business.Lookup contractLookup = (contract.component.LookupComponent) appReqBlock.getWebService("contract-lookup");

        String[] statusCodes = new String[7];

        statusCodes[0] = "Active";
        statusCodes[1] = "Commit";
        statusCodes[2] = "DeathCertain";
        statusCodes[3] = "DeathPending";
        statusCodes[4] = "Frozen";
        statusCodes[5] = "JointDeathPrimary";
        statusCodes[6] = "JointDeathSecondary";

        SegmentVO[] segmentVOs = contractLookup.getAllActiveSegmentsByCSId(productStructureIds, statusCodes);

        reporting.business.Reporting reportingComp = (reporting.component.ReportingComponent) appReqBlock.getWebService("reporting-service");

        reportingComp.createReservesExtracts(appReqBlock, segmentVOs, effectiveDate);

        return REPORTING_MAIN;
    }

    protected String processYETaxReports(AppReqBlock appReqBlock) throws Exception
    {
        FormBean formBean = appReqBlock.getFormBean();

        String companyName = formBean.getValue("companyName");
        String contractId = formBean.getValue("contractId");
//        String fromDate = DateTimeUtil.initDate(formBean.getValue("fromMonth"), formBean.getValue("fromDay"), formBean.getValue("fromYear"), null);
        EDITDate fromDate = DateTimeUtil.getEDITDateFromMMDDYYYY(formBean.getValue("fromDate"));

        EDITDate toDate = DateTimeUtil.getEDITDateFromMMDDYYYY(formBean.getValue("toDate"));
//        String toDate = DateTimeUtil.initDate(formBean.getValue("toMonth"), formBean.getValue("toDay"), formBean.getValue("toYear"), null);

        String taxYear = formBean.getValue("taxYear");

        engine.business.Lookup engineLookup = (engine.component.LookupComponent) appReqBlock.getWebService("engine-lookup");

        ProductStructureVO[] productStructureVOs = engineLookup.getAllProductStructuresByCoName(companyName);

        List csIds = new ArrayList();

        for (int i = 0; i < productStructureVOs.length; i++)
        {
            Company company = Company.findByPK(new Long(productStructureVOs[i].getCompanyFK()));
            if (companyName.equals(company.getCompanyName()))
            {
                csIds.add(new Long(productStructureVOs[i].getProductStructurePK()));
            }
        }

        long[] productStructureIds = new long[csIds.size()];

        for (int l = 0; l < csIds.size(); l++)
        {
            productStructureIds[l] = ((Long) csIds.get(l)).longValue();
        }

        contract.business.Lookup contractLookup = (contract.component.LookupComponent) appReqBlock.getWebService("contract-lookup");

        SegmentVO[] segmentVOs = null;

        if (!contractId.equals(""))
        {
            ByteArrayOutputStream bytesOut = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(bytesOut);

            segmentVOs = contractLookup.getSegmentByContractNumber(contractId, true, new ArrayList());
        }

        else
        {
            segmentVOs = contractLookup.getAllSegmentsByCSId(productStructureIds);
        }

        reporting.business.Reporting reportingComp = (reporting.component.ReportingComponent) appReqBlock.getWebService("reporting-service");

        YearEndTaxVO[] yeTaxVO = reportingComp.processTaxes(segmentVOs, fromDate.getFormattedDate(), toDate.getFormattedDate(), taxYear, productStructureVOs);

        appReqBlock.getHttpServletRequest().setAttribute("yeTaxVO", yeTaxVO);

        return YEAR_END_TAX_OUTPUT_DIALOG;
    }

    protected String showValuationParamsSelectionPage(AppReqBlock appReqBlock) throws Exception
    {
        new BatchUseCaseComponent().runValuation();

        PageBean valuationParamsPageBean = getCompanyNames(appReqBlock);

        appReqBlock.getHttpServletRequest().setAttribute("valuationParamsPageBean", valuationParamsPageBean);

        return VALUATION_PARAMS_SELECTION_PAGE;
    }

    protected String runValuation(AppReqBlock appReqBlock) throws Exception
    {
        FormBean formBean = appReqBlock.getFormBean();

        String companyName = formBean.getValue("companyName");

//        String valuationDate = DateTimeUtil.initDate(formBean.getValue("valuationMonth"), formBean.getValue("valuationDay"), formBean.getValue("valuationYear"), null);
        EDITDate valuationDate = DateTimeUtil.getEDITDateFromMMDDYYYY(formBean.getValue("valuationDate"));

        Batch batch = new BatchComponent();

        batch.createValuationExtracts(companyName, valuationDate.getFormattedDate(), null, null, Batch.ASYNCHRONOUS);

        String responseMessage = Util.getResourceMessage("batch.job.in.progress");

        appReqBlock.putInRequestScope("responseMessage", responseMessage);

        return REPORTING_MAIN;
    }

    protected String showCloseAccounting(AppReqBlock appReqBlock) throws Exception
    {
        engine.business.Lookup engineLookup = new engine.component.LookupComponent();

        ProductStructureVO[] productStructureVOs = engineLookup.getAllProductStructures();

        boolean marketingPackageNameFound;
        List marketingPackageNames = new ArrayList();
        List productStructVector = new ArrayList();
        String marketingPackageName = null;

        for (int i = 0; i < productStructureVOs.length; i++)
        {
            marketingPackageNameFound = false;
            marketingPackageName = productStructureVOs[i].getMarketingPackageName();

            for (int j = 0; j < marketingPackageNames.size(); j++)
            {
                if (((String) marketingPackageNames.get(j)).equalsIgnoreCase(marketingPackageName))
                {
                    marketingPackageNameFound = true;
                    j = marketingPackageNames.size();
                }
            }

            if (!marketingPackageNameFound)
            {
                marketingPackageNames.add(marketingPackageName);
                productStructVector.add(productStructureVOs[i]);
            }
        }

        productStructureVOs = (ProductStructureVO[]) productStructVector.toArray(new ProductStructureVO[productStructVector.size()]);

        PageBean pageBean = new PageBean();

        pageBean.putValues("marketingPackageNames", productStructureVOs, new String[] { "getMarketingPackageName" }, null);

        appReqBlock.getHttpServletRequest().setAttribute("closeAccountingParamsPageBean", pageBean);

        return CLOSE_ACCOUNTING_PARAMS;
    }

    protected String closeAccounting(AppReqBlock appReqBlock) throws Exception
    {
        String marketingPackageName = appReqBlock.getReqParm("marketingPackageName");
        String accountingPeriodMonth = appReqBlock.getReqParm("accountingPeriodMonth");
        String accountingPeriodYear = appReqBlock.getReqParm("accountingPeriodYear");
        String accountingPeriod = DateTimeUtil.buildAccountingPeriodAsMMYYYY(accountingPeriodMonth, accountingPeriodYear);

        Batch batch = new BatchComponent();

        batch.closeAccounting(marketingPackageName, accountingPeriod, null, null, Batch.ASYNCHRONOUS);

        String responseMessage = Util.getResourceMessage("batch.job.in.progress");

        appReqBlock.putInRequestScope("responseMessage", responseMessage);

        return REPORTING_MAIN;
    }

    protected String showAccountingExtractParams() throws Exception
    {
        return ACCOUNTING_EXTRACT_PARAMS;
    }

    protected String runAccountingExtract(AppReqBlock appReqBlock) throws Exception
    {
        String accountingPeriodMonth = appReqBlock.getReqParm("accountingPeriodMonth");
        String accountingPeriodYear = appReqBlock.getReqParm("accountingPeriodYear");
        String accountingPeriod = DateTimeUtil.buildAccountingPeriod(accountingPeriodYear, accountingPeriodMonth);

        Batch batch = new BatchComponent();

        batch.createAccountingExtract_FLAT(accountingPeriod, null, null, Batch.ASYNCHRONOUS);

        String responseMessage = Util.getResourceMessage("batch.job.in.progress");

        appReqBlock.putInRequestScope("responseMessage", responseMessage);

        return REPORTING_MAIN;
    }

    /**
     * Returns the RMD Notification parameter page
     * @param appReqBlock
     * @return
     * @throws Exception
     */
    protected String showRmdNotifications(AppReqBlock appReqBlock) throws Exception
    {
        ProductStructureVO[] productStructureVOs = getProductStructures();

        appReqBlock.getHttpServletRequest().setAttribute("productStructureVOs", productStructureVOs);

        return RMD_NOTIFICATION_PARAMS;
    }

    /**
     * Runs the RMD Notifications and returns the completion page
     * @param appReqBlock
     * @return
     * @throws Exception
     */
    protected String runRmdNotification(AppReqBlock appReqBlock) throws Exception
    {
        String productStructure = appReqBlock.getReqParm("productStructure");
//        String startRmdAnnualMonth = appReqBlock.getReqParm("startRmdAnnualMonth");
//        String startRmdAnnualDay = appReqBlock.getReqParm("startRmdAnnualDay");
//        String startRmdAnnualYear = appReqBlock.getReqParm("startRmdAnnualYear");
//        String endRmdAnnualMonth = appReqBlock.getReqParm("endRmdAnnualMonth");
//        String endRmdAnnualDay = appReqBlock.getReqParm("endRmdAnnualDay");
//        String endRmdAnnualYear = appReqBlock.getReqParm("endRmdAnnualYear");

//        String startRmdAnnualDate = DateTimeUtil.initDate(startRmdAnnualMonth, startRmdAnnualDay, startRmdAnnualYear, null);
//        String endRmdAnnualDate = DateTimeUtil.initDate(endRmdAnnualMonth, endRmdAnnualDay, endRmdAnnualYear, null);
        EDITDate startRmdAnnualDate = DateTimeUtil.getEDITDateFromMMDDYYYY(appReqBlock.getReqParm("startDate"));

        EDITDate endRmdAnnualDate = DateTimeUtil.getEDITDateFromMMDDYYYY(appReqBlock.getReqParm("endDate"));
        Batch batch = new BatchComponent();

        batch.generateRmdNotifications(productStructure, startRmdAnnualDate.getFormattedDate(), endRmdAnnualDate.getFormattedDate(), null, null, Batch.ASYNCHRONOUS);

        String responseMessage = Util.getResourceMessage("batch.job.in.progress");

        appReqBlock.getHttpServletRequest().setAttribute("responseMessage", responseMessage);

        return REPORTING_MAIN;
    }

    /**
     * Returns the COI Replenishment parameter page
     * @param appReqBlock
     * @return
     * @throws Exception
     */
    protected String showCoiReplenishment(AppReqBlock appReqBlock) throws Exception
    {
        ProductStructureVO[] productStructureVOs = getProductStructures();

        appReqBlock.getHttpServletRequest().setAttribute("productStructureVOs", productStructureVOs);

        return COI_REPLENISHMENT_PARAMS;
    }

    /**
     * Runs the COI Replenishment and returns the completion page
     * @param appReqBlock
     * @return
     * @throws Exception
     */
    protected String runCoiReplenishment(AppReqBlock appReqBlock) throws Exception
    {
        String productStructure = appReqBlock.getReqParm("productStructure");
//        String runMonth = appReqBlock.getReqParm("runMonth");
//        String runDay = appReqBlock.getReqParm("runDay");
//        String runYear = appReqBlock.getReqParm("runYear");
//        String runDate = DateTimeUtil.initDate(runMonth, runDay, runYear, null);
        EDITDate runDate = DateTimeUtil.getEDITDateFromMMDDYYYY(appReqBlock.getReqParm("processDate"));
        String contractNumber = appReqBlock.getReqParm("contractNumber");

        Batch batch = new BatchComponent();

        batch.processCoiReplenishment(productStructure, runDate.getFormattedDate(), contractNumber, null, null, Batch.ASYNCHRONOUS);

        String responseMessage = Util.getResourceMessage("batch.job.in.progress");

        appReqBlock.getHttpServletRequest().setAttribute("responseMessage", responseMessage);

        return REPORTING_MAIN;
    }

    /**
     * Retrieves all ProductStructureVOs from PRASE(Engine)
     * @return
     * @throws Exception
     */
    protected ProductStructureVO[] getProductStructures() throws Exception
    {
        engine.business.Lookup engineLookup = new engine.component.LookupComponent();

        ProductStructureVO[] productStructureVOs = engineLookup.getAllProductStructures();

        return productStructureVOs;
    }

    protected PageBean getCompanyNames(AppReqBlock appReqBlock) throws Exception
    {
        engine.business.Lookup calcLookup = (engine.component.LookupComponent) appReqBlock.getWebService("engine-lookup");

        ProductStructureVO[] productStructureVOs = calcLookup.getAllProductStructures();

        boolean companyNameFound;
        List companyNamesVector = new ArrayList();
        List productStructVector = new ArrayList();
        String companyName = null;

        for (int i = 0; i < productStructureVOs.length; i++)
        {
            if (!productStructureVOs[i].getTypeCodeCT().equalsIgnoreCase("System"))
            {
                companyNameFound = false;
                Company company = Company.findByPK(new Long(productStructureVOs[i].getCompanyFK()));
                companyName = company.getCompanyName();

                for (int j = 0; j < companyNamesVector.size(); j++)
                {
                    if (((String) companyNamesVector.get(j)).equalsIgnoreCase(companyName))
                    {
                        companyNameFound = true;
                        j = companyNamesVector.size();
                    }
                }

                if (!companyNameFound)
                {
                    companyNamesVector.add(company.getCompanyName());
                    productStructVector.add(productStructureVOs[i]);
                }
            }
        }

        productStructureVOs = null;
        productStructureVOs = (ProductStructureVO[]) productStructVector.toArray(new ProductStructureVO[productStructVector.size()]);

        String[] companyNames = (String[]) companyNamesVector.toArray(new String[companyNamesVector.size()]);

        PageBean pageBean = new PageBean();

        pageBean.putValues("companyNames", companyNames);

        return pageBean;
    }

    private String[] findAllProductStructureVOs(boolean includeChildVOs, String[] voClassExclusionList) throws Exception
    {
        engine.business.Lookup engineLookup = new engine.component.LookupComponent();

        ProductStructure[] productStructures = ProductStructure.find_All();

        Set uniqueCompanyNames = new HashSet();

        if (productStructures != null)
        {
            for (int i = 0; i < productStructures.length; i++)
            {
                Company company = productStructures[i].getCompany();
                String companyName = company.getCompanyName();

                uniqueCompanyNames.add(companyName);
            }
        }

        Collections.sort(new ArrayList(uniqueCompanyNames));

        return (String[]) uniqueCompanyNames.toArray(new String[uniqueCompanyNames.size()]);
    }

    protected String showRunReinsuranceChecks(AppReqBlock appReqBlock) throws Exception
    {
        new BatchUseCaseComponent().runReinsuranceChecks();

        return PROCESS_REINSURANCE_CHECKS_JSP;
    }

    protected String processReinsuranceChecks(AppReqBlock appReqBlock) throws Exception
    {
        PageBean formBean = appReqBlock.getFormBean();

//        String formattedDate = DateTimeUtil.initDate(formBean.getValue("processMonth"), formBean.getValue("processDay"), formBean.getValue("processYear"), null);
        EDITDate processDate = DateTimeUtil.getEDITDateFromMMDDYYYY(formBean.getValue("processDate"));

        Batch batch = new BatchComponent();

        batch.processReinsuranceChecks(processDate.getFormattedDate(), null, null, Batch.ASYNCHRONOUS);

        String responseMessage = Util.getResourceMessage("batch.job.in.progress");

        appReqBlock.getHttpServletRequest().setAttribute("responseMessage", responseMessage);

        return REPORTING_MAIN;
    }

    protected String showRunAlphaExtract(AppReqBlock appReqBlock) throws Exception
    {
        new BatchUseCaseComponent().runAlphaExtract();

        return ALPHA_EXTRACT_PARAM_JSP;
    }

    protected String runAlphaExtract(AppReqBlock appReqBlock) throws Exception
    {
        PageBean formBean = appReqBlock.getFormBean();

        Batch batch = new BatchComponent();
        batch.runAlphaExtract(new EDITDate().getFormattedDate(), null, null, Batch.ASYNCHRONOUS);

        String responseMessage = Util.getResourceMessage("batch.job.in.progress");

        appReqBlock.getHttpServletRequest().setAttribute("responseMessage", responseMessage);

        return REPORTING_MAIN;
    }

    protected String showPendingRequirementsExtractParams(AppReqBlock appReqBlock) throws Exception
    {
        new BatchUseCaseComponent().runPendingRequirementsExtract();

        return PENDING_REQ_EXTRACT_PARAMS_JSP;
    }

    protected String runPendingRequirementsExtract(AppReqBlock appReqBlock) throws Exception
    {
        PageBean formBean = appReqBlock.getFormBean();

        String paramDate = formBean.getValue("paramDate");

        Batch batch = new BatchComponent();
        batch.runPendingRequirementsExtract(new EDITDate(paramDate).getFormattedDate(), null, null, Batch.ASYNCHRONOUS);

        String responseMessage = Util.getResourceMessage("batch.job.in.progress");

        appReqBlock.getHttpServletRequest().setAttribute("responseMessage", responseMessage);

        return REPORTING_MAIN;
    }

    protected String showDataWarehouseParams(AppReqBlock appReqBlock) throws Exception
    {
        new BatchUseCaseComponent().runDataWarehouse();

        PageBean dataWarehouseParamsPageBean = getCompanyNames(appReqBlock);

        appReqBlock.getHttpServletRequest().setAttribute("dataWarehouseParamsPageBean", dataWarehouseParamsPageBean);

        return DATA_WAREHOUSE_PARAMS_JSP;
    }

    protected String runDataWarehouse(AppReqBlock appReqBlock) throws Exception
    {
        PageBean formBean = appReqBlock.getFormBean();

        String dataWarehouseDate = formBean.getValue("dataWarehouseDate");
        String companyName = formBean.getValue("companyName");
        String caseNumber = formBean.getValue("caseNumber");
        String groupNumber = formBean.getValue("groupNumber");

        Batch batch = new BatchComponent();
        batch.runDataWarehouse(new EDITDate(dataWarehouseDate).getFormattedDate(), companyName, caseNumber, groupNumber, null, null, Batch.ASYNCHRONOUS);

        String responseMessage = Util.getResourceMessage("batch.job.in.progress");

        appReqBlock.getHttpServletRequest().setAttribute("responseMessage", responseMessage);

        return REPORTING_MAIN;
    }

    protected String showWorksheetParams(AppReqBlock appReqBlock) throws Exception
    {
        new BatchUseCaseComponent().runWorksheet();

        return WORKSHEET_PARAMS_JSP;
    }

    protected String runWorksheet(AppReqBlock appReqBlock) throws Exception
    {
        String batchId = appReqBlock.getFormBean().getValue("batchId");

        Batch batch = new BatchComponent();
        batch.runWorksheet(batchId, null, null, Batch.ASYNCHRONOUS);

        String responseMessage = Util.getResourceMessage("batch.job.in.progress");

        appReqBlock.getHttpServletRequest().setAttribute("responseMessage", responseMessage);

        return REPORTING_MAIN;
    }

    protected String showCashBatchImport(AppReqBlock appReqBlock) throws Exception
    {
        return CASH_BATCH_IMPORT;
    }

    /**
     * Imports information for cash batch.  The file that is read has a standard naming convention with a date.  The
     * user selects the date from the page.
     * @param appReqBlock
     * @return
     * @throws Exception
     */
    protected String importCashBatch(AppReqBlock appReqBlock) throws Exception
    {
        String importDate = appReqBlock.getFormBean().getValue("importDate");
        String filename = appReqBlock.getFormBean().getValue("filename");
        
        UserSession userSession = (UserSession) appReqBlock.getHttpSession().getAttribute("userSession");
        String operator = userSession.getUsername();

        new BatchComponent().importCashBatch(importDate, operator, filename, null, null, Batch.ASYNCHRONOUS);

        String responseMessage = Util.getResourceMessage("batch.job.in.progress");

        appReqBlock.putInRequestScope("responseMessage", responseMessage);

        return CASH_BATCH_IMPORT;
    }

    protected String showManualAccountingImportParams(AppReqBlock appReqBlock) throws Exception
    {
        new BatchUseCaseComponent().runManualAccountingImport();

        return MANUAL_ACCOUNTING_IMPORT_PARAMS_JSP;
    }

    protected String runManualAccountingImport(AppReqBlock appReqBlock) throws Exception
    {
        PageBean formBean = appReqBlock.getFormBean();

        String manualAccountingDate = formBean.getValue("parameterDate");

        Batch batch = new BatchComponent();
        batch.runManualAccountingImport(new EDITDate(manualAccountingDate).getFormattedDate(), null, null, Batch.ASYNCHRONOUS);

        String responseMessage = Util.getResourceMessage("batch.job.in.progress");

        appReqBlock.getHttpServletRequest().setAttribute("responseMessage", responseMessage);

        return REPORTING_MAIN;
    }
}
