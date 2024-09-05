/*
 * User: gfrosti
 * Date: Feb 14, 2002
 * Time: 9:25:12 AM
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package edit.portal.common.transactions;

import accounting.component.*;
import agent.component.*;
import agent.BonusProgram;
import batch.component.*;
import client.component.*;
import client.*;
import contract.component.*;
import contract.*;
import edit.portal.common.session.*;
import edit.portal.widget.AgentBonusPremiumLevelTableModel;
import event.component.*;
import fission.beans.*;
import fission.global.*;
import fission.utility.*;
import productbuild.component.*;
import reinsurance.component.*;
import reinsurance.ui.*;
import security.component.*;
import security.ui.servlet.*;
import casetracking.usecase.*;
import edit.portal.widget.AgentMoveFromTableModel;
import edit.portal.widget.AgentMoveSelectedTableModel;
import edit.portal.widget.AgentMoveToTableModel;

import edit.portal.widget.LogColumnTableModel;
import edit.portal.widget.LogTableModel;
import edit.portal.widget.LogIndexTableModel;
import edit.portal.widgettoolkit.TableModel;
import group.*;
import role.*;

public class PortalLoginTran extends Transaction
{
    //These are the Actions of the Screen
    private static final String SHOW_INDIVIDUALS = "showIndividuals";
//    private static final String QUICK_ADD_CLIENT = "quickAddClient";
    private static final String SHOW_QUOTE = "showQuote";
    private static final String SHOW_CONTRACT = "showContract";
    private static final String SHOW_DAILY_SELECTION = "showDailySelection";
    private static final String SHOW_PRODUCT_PROFESSIONALS = "showProductProfessionals";
    private static final String DO_LOG_OUT = "doLogOut";
    private static final String SHOW_EDITING = "showEditing";
    private static final String SHOW_ACCOUNTING = "showAccounting";
    private static final String SHOW_REPORTS = "showReports";
    private static final String SHOW_REPORTS_ADMIN = "showReportsAdmin";
    private static final String SHOW_SECURITY_ADMIN = "showSecurityAdmin";
    private static final String SHOW_ROLE_ADMIN = "showRoleAdmin";
    private static final String SHOW_LOGGING_ADMIN = "showLoggingAdmin";
    private static final String SHOW_COMMISSION_CONTRACT = "showCommissionContract";
    private static final String SHOW_AGENT_DETAIL = "showAgentDetail";
    private static final String SHOW_AGENT_HIERARCHY = "showAgentHierarchy";
    private static final String SHOW_REQUIREMENT_TABLE = "showRequirementTable";
    private static final String SHOW_TRANSACTION_TABLE = "showTransactionTable";
    private static final String SHOW_JOURNAL_ADJUSTMENT = "showJournalAdjustment";
    private static final String SHOW_CASH_BATCH = "showCashBatch";
    private static final String SHOW_CODE_TABLE_DEF_SUMMARY = "showCodeTableDefSummary";
    private static final Object SHOW_ONLINE_REPORT_SUMMARY = "showOnlineReportSummary";
    private static final Object SHOW_HEDGE_FUND_REDEMPTION = "showHedgeFundRedemption";
    private static final String GO_TO_MAIN = "goToMain";
    private static final String SHOW_HELP = "showHelp";
    private static final String SHOW_REINSURER_DETAILS = "showReinsurerDetails";
    private static final String SHOW_TREATY_RELATIONS = "showTreatyRelations";
    private static final String SHOW_CONTRACT_TREATY_RELATIONS = "showContractTreatyRelations";
    private static final String SHOW_BUSINESS_CALENDAR = "showBusinessCalendar";
    private static final String SHOW_IMPORT_NEW_BUSINESS = "showImportNewBusiness";
    private static final String SHOW_AGENT_BONUS_PROGRAM = "showAgentBonusProgram";
    private static final String SHOW_CASETRACKING = "showCasetracking";
    private static final String SHOW_CASE = "showCase";
    private static final String SHOW_AGENT_GROUP = "showAgentGroup";
    private static final String SHOW_AGENT_MOVE = "showAgentMove";
    private static final String SHOW_LOG_SETUP = "showLoggingSetup";
    private static final String SHOW_LOG_RESULTS = "showLoggingResults";
    private static final String SHOW_EXPORT_SELECTION = "showExportSelection";
    private static final String SHOW_BILLING = "showBilling";
    private static final String SHOW_BATCH_CONTRACT = "showBatchContract";
    private static final String SHOW_PRD_SYSTEM = "showPRDSystem";
    private static final String SHOW_CONVERSION = "showConversion";
    private static final String SHOW_QUERY = "showQuery";

    //Pages that the Tran will return
    private static final String CLIENT_MAIN_FRAMESET = "/client/jsp/mainframeset.jsp";
//    private static final String CLIENT_QUICK_ADD_MAIN_FRAMESET = "/client/jsp/quickAddMainframeset.jsp";
    private static final String QUOTE_MAIN_FRAMESET = "/quote/jsp/mainframeset.jsp";
    private static final String REQUIREMENT_RELATION = "/quote/jsp/requirementRelation.jsp";
    private static final String TRANSACTION_PRIORITY_SUMMARY = "/event/jsp/transactionPrioritySummary.jsp";
    private static final String CONTRACT_MAIN_FRAMESET = "/contract/jsp/mainframeset.jsp";
    private static final String CASE_MAIN_FRAMESET = "/contract/jsp/caseMainFrameset.jsp";
    private static final String PRD_SYSTEM_PAGE = "/PRDApplication/PRDApplication.jsp";
    private static final String ROLE_MAIN_FRAMESET = "/role/jsp/mainframeset.jsp";
    private static final String DAILY_SELECTION = "/daily/jsp/mainframeset.jsp";
    private static final String PORTAL = "/common/jsp/portal.jsp";
    private static final String PRASE_MAIN_FRAMESET = "/engine/jsp/mainframeset.jsp";
    private static final String EDITING_MAIN_FRAMESET = "/editing/jsp/mainframeset.jsp";
    private static final String ACCOUNTING_MAIN_FRAMESET = "/accounting/jsp/accountingMainFrameset.jsp";
    private static final String REPORTING_MAIN_FRAMESET = "/reporting/jsp/mainframeset.jsp";
    private static final String REPORTING_MAIN_ADMIN_FRAMESET = "/reportingadmin/jsp/mainframeset.jsp";
    private static final String SECURITY_MAIN_FRAMESET = "/security/jsp/mainframeset.jsp";
    private static final String LOGGING_MAIN_FRAMESET = "/logging/jsp/mainframeset.jsp";
    private static final String COMMISSION_PROFILE_MAIN_FRAMESET = "/agent/jsp/commissionProfileMainframeset.jsp";
    private static final String AGENT_DETAIL_MAIN_FRAMESET = "/agent/jsp/agentDetailMainframeset.jsp";
    private static final String AGENT_HIERARCHY_FRAMESET = "/agent/jsp/agentHierarchyMainFrameset.jsp";
    private static final String JOURNAL_MAIN_FRAMESET = "/accounting/jsp/journalMainframeset.jsp";
    private static final String CASH_BATCH_MAIN_FRAMESET = "/event/jsp/cashBatchMainframeset.jsp";
    private static final String CODE_TABLE_SUMMARY = "/codetable/jsp/codeTableSummary.jsp";
    private static final String ONLINE_REPORT_SUMMARY_MAINFRAMESET = "/codetable/jsp/onlineReportSummaryMainframeset.jsp";
    private static final String HEDGE_FUND_REDEMPTION_MAINFRAMESET = "/event/jsp/hedgeFundRedemptionMainframeset.jsp";
    private static final String REINSURER_MAINFRAMESET = "/reinsurance/jsp/reinsurerMainFrameset.jsp";
    private static final String TREATY_RELATIONS_MAINFRAMESET = "/reinsurance/jsp/treatyRelationsMainFrameset.jsp";
    private static final String CONTRACT_TREATY_MAINFRAMESET = "/reinsurance/jsp/contractTreatyMainFrameset.jsp";
    private static final String BUSINESS_CALENDAR_MAINFRAMESET = "/businesscalendar/jsp/businessCalendarMainFrameset.jsp";
    private static final String IMPORT_NEW_BUSINESS_MAINFRAMESET = "/quote/jsp/importNewBusinessMainframeset.jsp";
    private static final String CASETRACKING_MAINFRAMESET = "/casetracking/jsp/casetrackingMainFrameset.jsp";
    private static final String EXPORT_SELECTION = "/engine/jsp/exportSelection.jsp";
    private static final String BILLING = "/flex/BillingApplication.jsp";
    private static final String BATCH_CONTRACT = "/flex/BatchContractApplication.jsp";
    private static final String CONVERSION = "/flex/ConversionApplication.jsp";
    private static final String QUERY = "/flex/QueryApplication.jsp";

    private static final String LOGGING_TOOLBAR = "/logging/jsp/loggingToolBar.jsp";
    private static final String LOGGING_ADMIN = "/logging/jsp/loggingMain.jsp";
    private static final String TEMPLATE_TOOLBAR_MAIN = "/common/jsp/template/template-toolbar-main.jsp";
    private static final String AGENT_BONUS_PROGRAM = "/agent/jsp/agentBonusProgram.jsp";
    private static final String AGENT_BONUS_TOOLBAR = "/agent/jsp/agentBonusToolbar.jsp";
	private static final String AGENT_MOVE = "/agent/jsp/agentMove.jsp";
    private static final String AGENT_MOVE_TOOLBAR = "/agent/jsp/agentMoveToolbar.jsp";
    private static final String LOGGING_INDEX = "/logging/jsp/logIndex.jsp";
    private static final String EXPORT_TOOLBAR = "/engine/jsp/exportToolBar.jsp";
    private static final String HELP = "/common/jsp/help.jsp";
    private static final String LOGIN_JSP = "/security/jsp/login.jsp";


    public String execute(AppReqBlock appReqBlock) throws Throwable
    {

        String action = appReqBlock.getReqParm("action");

//        if (!action.equals(QUICK_ADD_CLIENT))
//        {

            initUserSession(appReqBlock, action);
//        }

        if (action.equals(SHOW_INDIVIDUALS))
        {

            return showIndividuals(appReqBlock);
        }
//        else if (action.equals(QUICK_ADD_CLIENT))
//        {
//
//            return quickAddClient(appReqBlock);
//        }
        else if (action.equals(SHOW_QUOTE))
        {

            return showQuote(appReqBlock);
        }
        else if (action.equals(SHOW_CONTRACT))
        {

            return showContract(appReqBlock);
        }
        else if (action.equals(SHOW_ROLE_ADMIN))
        {

            return showRoleAdmin(appReqBlock);
        }
        else if (action.equals(SHOW_DAILY_SELECTION))
        {

            return showDailySelection(appReqBlock);
        }
        else if (action.equals(SHOW_PRODUCT_PROFESSIONALS))
        {

            return showPRASESelection(appReqBlock);
        }
        else if (action.equals(SHOW_ACCOUNTING))
        {

            return showAccountingSelection(appReqBlock);
        }
        else if (action.equals(DO_LOG_OUT))
        {

            return doLogOut(appReqBlock);
        }
        else if (action.equals(GO_TO_MAIN))
        {

            return goToMain(appReqBlock);
        }
        else if (action.equals(SHOW_HELP))
        {
            return showHelp(appReqBlock);
        }
        else if (action.equals(SHOW_EDITING))
        {

            return showEditing(appReqBlock);
        }
        else if (action.equals(SHOW_REPORTS))
        {

            return showReports(appReqBlock);
        }
        else if (action.equals(SHOW_REPORTS_ADMIN))
        {

            return showReportsAdmin(appReqBlock);
        }
        else if (action.equals(SHOW_SECURITY_ADMIN))
        {

            return showSecurityAdmin(appReqBlock);
        }
        else if (action.equals(SHOW_LOGGING_ADMIN))
        {
            return showLoggingAdmin(appReqBlock);
        }
        else if (action.equals(SHOW_COMMISSION_CONTRACT))
        {

            return showCommissionContract(appReqBlock);
        }
        else if (action.equals(SHOW_AGENT_DETAIL))
        {

            return showAgentDetail(appReqBlock);
        }
        else if (action.equals(SHOW_REQUIREMENT_TABLE))
        {

            return showRequirementTable(appReqBlock);
        }
        else if (action.equals(SHOW_TRANSACTION_TABLE))
        {

            return showTransactionTable(appReqBlock);
        }
        else if (action.equals(SHOW_JOURNAL_ADJUSTMENT))
        {
            return showJournalAdjustment(appReqBlock);
        }
        else if (action.equals(SHOW_CASH_BATCH))
        {
            return showCashBatch(appReqBlock);
        }
        else if (action.equals(SHOW_AGENT_HIERARCHY))
        {
            return showAgentHierarchy(appReqBlock);
        }
        else if (action.equals(SHOW_CODE_TABLE_DEF_SUMMARY))
        {
            return showCodeTableDefSummary(appReqBlock);
        }
        else if (action.equals(SHOW_ONLINE_REPORT_SUMMARY))
        {
            return showOnlineReportSummary(appReqBlock);
        }
        else if (action.equals(SHOW_HEDGE_FUND_REDEMPTION))
        {
            return showHedgeFundRedemption(appReqBlock);
        }
        else if (action.equals(SHOW_REINSURER_DETAILS))
        {
            return showReinsurerDetails(appReqBlock);
        }
        else if (action.equals(SHOW_TREATY_RELATIONS))
        {
            return showTreatyRelations(appReqBlock);
        }
        else if (action.equals(SHOW_CONTRACT_TREATY_RELATIONS))
        {
            return showContractTreatyRelations(appReqBlock);
        }
        else if (action.equals(SHOW_BUSINESS_CALENDAR))
        {
            return showBusinessCalendar(appReqBlock);
        }
        else if (action.equals(SHOW_IMPORT_NEW_BUSINESS))
        {
            return showImportNewBusiness(appReqBlock);
        }
        else if (action.equals(SHOW_AGENT_BONUS_PROGRAM))
        {
            return showAgentBonusProgram(appReqBlock);
        }
        else if (action.equals(SHOW_CASETRACKING))
        {
            return showCaseTracking(appReqBlock);
        }
        else if (action.equals(SHOW_CASE))
        {
            return showCaseMain(appReqBlock);
        }
        else if (action.equals(SHOW_AGENT_GROUP))
        {
            return showAgentGroup(appReqBlock);
        }
        else if (action.equals(SHOW_AGENT_MOVE))
        {
            return showAgentMove(appReqBlock);
		}
        else if (action.equals(SHOW_LOG_SETUP))
        {
            return showLoggingSetup(appReqBlock);
        }
        else if (action.equals(SHOW_LOG_RESULTS))
        {
            return showLoggingResults(appReqBlock);
        }
        else if (action.equals(SHOW_EXPORT_SELECTION))
        {
            return showExportSelection(appReqBlock);
        }
        else if (action.equals(SHOW_BILLING))
        {
            return showBilling(appReqBlock);
        }
        else if (action.equals(SHOW_BATCH_CONTRACT))
        {
            return showBatchContract(appReqBlock);
        }
        else if (action.equals(SHOW_PRD_SYSTEM))
        {
            return showPRDSystem(appReqBlock);
        }
        else if (action.equals(SHOW_CONVERSION))
        {
            return showConversion(appReqBlock);
        }
        else if (action.equals(SHOW_QUERY))
        {
            return showQuery(appReqBlock);
        }
        else
        {
            throw new Exception("Page Not Found");
        }
    }

    /**
     * The flex Conversion framework.
     * @param appReqBlock
     * @return
     */
    private String showConversion(AppReqBlock appReqBlock)
    {
        // Check for authorization
        new ProductBuildUseCaseComponent().accessPRASE();

        return CONVERSION;
    }

    /**
     * The flex Query application
     *
     * @param appReqBlock
     *
     * @return
     */
    private String showQuery(AppReqBlock appReqBlock)
    {
        // Check for authorization
        new ProductBuildUseCaseComponent().accessPRASE();

        return QUERY;
    }

    /**
     * The flex List-Bill page.
     * @param appReqBlock
     * @return
     */
    private String showBatchContract(AppReqBlock appReqBlock)
    {
        // Check for authorization
        new NewBusinessUseCaseComponent().accessNewBusiness();

        return BATCH_CONTRACT;
    }


    /**
     * The flex List-Bill page.
     * @param appReqBlock
     * @return
     */
    private String showBilling(AppReqBlock appReqBlock)
    {
        // Check for authorization
        new CaseUseCaseComponent().accessCase();

        return BILLING;
    }

    private String showExportSelection(AppReqBlock appReqBlock)
    {
        appReqBlock.putInRequestScope("toolbar", EXPORT_TOOLBAR);

        appReqBlock.putInRequestScope("main", EXPORT_SELECTION);

        // Check for authorization
        new ProductBuildUseCaseComponent().accessExport();

        return TEMPLATE_TOOLBAR_MAIN;

    }

    /**
     * Shows the logging setup page (logging main page)
     *
     * @param appReqBlock
     *
     * @return
     */
    private String showLoggingSetup(AppReqBlock appReqBlock)
    {
        // new AgentUseCaseComponent().accessAgentHierarchy(); // Currently no security restriction on logging.

        TableModel.get(appReqBlock, LogTableModel.class);

        TableModel.get(appReqBlock, LogColumnTableModel.class);

        appReqBlock.putInRequestScope("toolbar", LOGGING_TOOLBAR);

        appReqBlock.putInRequestScope("main", LOGGING_ADMIN);

        return TEMPLATE_TOOLBAR_MAIN;
    }

    /**
     * Shows the logging results page that displays the index of logs
     *
     * @param appReqBlock
     *
     * @return
     */
    private String showLoggingResults(AppReqBlock appReqBlock)
    {
        TableModel.get(appReqBlock, LogIndexTableModel.class);

        appReqBlock.putInRequestScope("toolbar", LOGGING_TOOLBAR);

        appReqBlock.putInRequestScope("main", LOGGING_INDEX);

        return TEMPLATE_TOOLBAR_MAIN;
    }

    /**
     * Renders the broker/dealer arragement page.
     *
     * @param appReqBlock
     *
     * @return
     */
    private String showAgentGroup(AppReqBlock appReqBlock)
    {
//        new AgentUseCaseComponent().accessAgentHierarchy();
//
//        new AgentGroupTableModel(appReqBlock);
//
//        appReqBlock.putInRequestScope("toolbar", AGENT_GROUP_TOOLBAR);
//
//        appReqBlock.putInRequestScope("main", BROKER_DEALER_ARRANGEMENT);

        return TEMPLATE_TOOLBAR_MAIN;
    }


    /**
     * Returns the Import New Business main frameset.
     *
     * @param appReqBlock
     *
     * @return
     */
    private String showImportNewBusiness(AppReqBlock appReqBlock)
    {
        new NewBusinessUseCaseComponent().accessImportNewBusiness();

        return IMPORT_NEW_BUSINESS_MAINFRAMESET;
    }

    /**
     * Returns the Business Calendar main frameset.
     *
     * @param appReqBlock
     *
     * @return
     */
    private String showBusinessCalendar(AppReqBlock appReqBlock)
    {
        new ProductBuildUseCaseComponent().accessBusinessCalendar();

        return BUSINESS_CALENDAR_MAINFRAMESET;
    }

    /**
     * Returns the Contract Treaty Relations main frameset.
     *
     * @param appReqBlock
     *
     * @return
     */
    private String showContractTreatyRelations(AppReqBlock appReqBlock)
    {
        new ReinsuranceUseCaseComponent().accessContractTreatyRelations();

        return CONTRACT_TREATY_MAINFRAMESET;
    }

    /**
     * Returns the Treaty Relationships Main Frameset
     *
     * @param appReqBlock
     *
     * @return
     */
    private String showTreatyRelations(AppReqBlock appReqBlock)
    {
        new ReinsuranceUseCaseComponent().accessTreatyRelations();

        return TREATY_RELATIONS_MAINFRAMESET;
    }

    /**
     * Returns the Reinsurer Main Frameset
     *
     * @param appReqBlock
     *
     * @return
     */
    private String showReinsurerDetails(AppReqBlock appReqBlock)
    {
        new ReinsuranceUseCaseComponent().accessReinsurerInformation();

        ReinsuranceCloud cloud = (ReinsuranceCloud) appReqBlock.getCloud(reinsurance.ui.ReinsuranceCloud.class);

        cloud.clearCloudState();

        UserSession userSession = appReqBlock.getUserSession();

        userSession.unlock();

        return REINSURER_MAINFRAMESET;
    }

    private String showCodeTableDefSummary(AppReqBlock appReqBlock) throws Exception
    {
        // Check for authorization
        new ProductBuildUseCaseComponent().accessCodeTable();

        return CODE_TABLE_SUMMARY;
    }

    private String showOnlineReportSummary(AppReqBlock appReqBlock) throws Exception
    {
        // Check for authorization
        new ProductBuildUseCaseComponent().accessOnlineReport();

        return ONLINE_REPORT_SUMMARY_MAINFRAMESET;
    }

    private String showCaseTracking(AppReqBlock appReqBlock)
    {
        // new ProductBuildUseCaseComponent().accessCaseTracking();
        String pageToShow = appReqBlock.getReqParm("pageToShow");
        appReqBlock.getHttpServletRequest().setAttribute("pageToShow", pageToShow);

        // to store selectedClientDetailPK in the search page.
        appReqBlock.getHttpSession().removeAttribute("casetracking.selectedClientDetailPK");
        // to store doubleTableModel.
        appReqBlock.getHttpSession().removeAttribute("doubleTableModel");
        // to store the process on clientDetail page.
        appReqBlock.getHttpSession().removeAttribute("casetracking.process");

        // Check for authorization
        new CasetrackingUseCaseImpl().accessCaseTracking();

        return CASETRACKING_MAINFRAMESET;
    }

    /**
     * Returns the Case Main Frameset
     *
     * @param appReqBlock
     *
     * @return
     */
    private String showPRDSystem(AppReqBlock appReqBlock) {
        // Check for authorization
        new CaseUseCaseComponent().accessPRDSystem();

        return PRD_SYSTEM_PAGE;

    }

    private String showCaseMain(AppReqBlock appReqBlock)
    {
        UserSession userSession = (UserSession) appReqBlock.getHttpSession().getAttribute("userSession");

        Long segmentPK = userSession.getSegmentPK();

        userSession.unlock();       // remove all locks

        // Check for authorization
        new CaseUseCaseComponent().accessCase();


        if (segmentPK > 0)
        {
            Segment segment = Segment.findByPK(new Long(segmentPK));
            Long contractGroupFK = segment.getContractGroupFK();
            if (contractGroupFK != null)
            {
                ContractGroup contractGroup = ContractGroup.findBy_ContractGroupFK(contractGroupFK);
                if (contractGroup != null)
                {
                    userSession.setParameter("activeCasePK", contractGroup.getContractGroupPK().toString());
                }
            }
        }

        return CASE_MAIN_FRAMESET;
    }


    protected String showAgentHierarchy(AppReqBlock appReqBlock) throws Exception
    {
        appReqBlock.getHttpSession().removeAttribute("agentVO");
        appReqBlock.getHttpSession().removeAttribute("clientDetailVO");
        appReqBlock.getHttpSession().removeAttribute("commissionHistoryVOs");
        appReqBlock.getHttpSession().removeAttribute("agentExtractVOs");
        appReqBlock.getHttpSession().removeAttribute("searchValue");

        UserSession userSession = (UserSession) appReqBlock.getHttpSession().getAttribute("userSession");

        userSession.unlock();       // remove all locks

        // Check for authorization
        new AgentUseCaseComponent().accessAgentHierarchy();

        return AGENT_HIERARCHY_FRAMESET;
    }

    protected String showLoggingAdmin(AppReqBlock appReqBlock) throws Exception
    {
        // There is no "Logging-Component" as of this entry. Therefore, no security check.

        return LOGGING_MAIN_FRAMESET;
//         TableModel.get(appReqBlock, LogTableModel.class);
//
//         appReqBlock.putInRequestScope("toolbar", LOGGING_TOOLBAR);
//
//         appReqBlock.putInRequestScope("main", LOGGING_ADMIN);
//
//         return TEMPLATE_TOOLBAR_MAIN;
    }

    protected String showSecurityAdmin(AppReqBlock appReqBlock) throws Exception
    {
        // Check for authorization
        new SecurityUseCaseComponent().accessSecurity();

        return SECURITY_MAIN_FRAMESET;
    }

    protected String showReportsAdmin(AppReqBlock appReqBlock) throws Exception
    {
        // Check for authorization

        return REPORTING_MAIN_ADMIN_FRAMESET;
    }

    protected String showEditing(AppReqBlock appReqBlock) throws Exception
    {
        // Check for authorization
        new ProductBuildUseCaseComponent().accessEditing();

        return EDITING_MAIN_FRAMESET;
    }

    protected String showReports(AppReqBlock appReqBlock) throws Exception
    {
        // Check for authorization
        new BatchUseCaseComponent().accessOnRequestBatch();

        return REPORTING_MAIN_FRAMESET;
    }

    protected String showIndividuals(AppReqBlock appReqBlock) throws Exception
    {

        UserSession userSession = (UserSession) appReqBlock.getHttpSession().getAttribute("userSession");

        String clientRoleFK = Util.initString(userSession.getParameter("selectedClientRoleFK"), "");

        userSession.unlock();       // remove all locks
        userSession.clearParameter("selectedClientRoleFK");

        if (!clientRoleFK.equals(""))
        {
            ClientRole clientRole = ClientRole.findByPK(new Long(clientRoleFK));

            String clientDetailPK = clientRole.getClientDetailFK() + "";
            appReqBlock.getHttpSession().setAttribute("searchValue", clientDetailPK);
            userSession.setClientDetailPK(Long.parseLong(clientDetailPK));
        }

        // Check for authorization
        new ClientUseCaseComponent().accessClient();

        return CLIENT_MAIN_FRAMESET;
    }

//    protected String quickAddClient(AppReqBlock appReqBlock) throws Exception
//    {
//
//        // Check for authorization
//        new ClientUseCaseComponent().accessClient();
//
//        return CLIENT_QUICK_ADD_MAIN_FRAMESET;
//    }

    protected String showQuote(AppReqBlock appReqBlock) throws Exception
    {
        UserSession userSession = (UserSession) appReqBlock.getHttpSession().getAttribute("userSession");

        userSession.unlock();       // remove all locks

        // Check for authorization
        new NewBusinessUseCaseComponent().accessNewBusiness();

        return QUOTE_MAIN_FRAMESET;
    }

    protected String showContract(AppReqBlock appReqBlock) throws Exception
    {
        UserSession userSession = (UserSession) appReqBlock.getHttpSession().getAttribute("userSession");

        userSession.unlock();       // remove all locks

        // Check for authorization
        new InforceUseCaseComponent().accessInforceContract();

        return CONTRACT_MAIN_FRAMESET;
    }

    protected String showRoleAdmin(AppReqBlock appReqBlock) throws Exception
    {
        UserSession userSession = (UserSession) appReqBlock.getHttpSession().getAttribute("userSession");

        userSession.unlock();       // remove all locks

        // Check for authorization

        return ROLE_MAIN_FRAMESET;
    }

    protected String showDailySelection(AppReqBlock appReqBlock) throws Exception
    {
        // Check for authorization
        new BatchUseCaseComponent().accessDailyBatch();

        return DAILY_SELECTION;
    }

    protected String showPRASESelection(AppReqBlock appReqBlock) throws Exception
    {
        UserSession userSession = (UserSession) appReqBlock.getHttpSession().getAttribute("userSession");

        userSession.unlock();       // remove all locks

        // Check for authorization
        new ProductBuildUseCaseComponent().accessPRASE();

        return PRASE_MAIN_FRAMESET;
    }

    protected String showAccountingSelection(AppReqBlock appReqBlock) throws Exception
    {
        // Check for authorization
        new AccountingUseCaseComponent().accessAccounting();

        return ACCOUNTING_MAIN_FRAMESET;
    }

    protected String showCommissionContract(AppReqBlock appReqBlock) throws Exception
    {
        UserSession userSession = (UserSession) appReqBlock.getHttpSession().getAttribute("userSession");

        userSession.unlock();       // remove all locks

        // Check for authorization
        new ProductBuildUseCaseComponent().accessCommissionContract();

        return COMMISSION_PROFILE_MAIN_FRAMESET;
    }

    protected String showAgentDetail(AppReqBlock appReqBlock) throws Exception
    {
        UserSession userSession = (UserSession) appReqBlock.getHttpSession().getAttribute("userSession");

        userSession.unlock();       // remove all locks

        // Check for authorization
        new AgentUseCaseComponent().accessAgentDetail();

        return AGENT_DETAIL_MAIN_FRAMESET;
    }

    protected String showRequirementTable(AppReqBlock appReqBlock) throws Exception
    {
        // Check for authorization
        new ProductBuildUseCaseComponent().accessRequirementTable();

        return REQUIREMENT_RELATION;
    }

    protected String showTransactionTable(AppReqBlock appReqBlock) throws Exception
    {
        // Check for authorization
        new ProductBuildUseCaseComponent().accessTransactionTable();

        return TRANSACTION_PRIORITY_SUMMARY;
    }

    protected String showJournalAdjustment(AppReqBlock appReqBlock) throws Exception
    {
        // Check for authorization
        new AccountingUseCaseComponent().accessJournalAdjustment();

        appReqBlock.getHttpSession().setAttribute("AccountingDetail.JournalAdjustment.AccountSummary", null);

        return JOURNAL_MAIN_FRAMESET;
    }

    protected String showCashBatch(AppReqBlock appReqBlock) throws Exception
    {
        // Check for authorization
        new NewBusinessUseCaseComponent().accessCashBatch();

        return CASH_BATCH_MAIN_FRAMESET;
    }

    protected String showHedgeFundRedemption(AppReqBlock appReqBlock) throws Exception
    {
        //Check for authorization
        new EventUseCaseComponent().accessHedgeFundRedemption();

        return HEDGE_FUND_REDEMPTION_MAINFRAMESET;
    }

    protected String doLogOut(AppReqBlock appReqBlock) throws Throwable
    {
        //  The following is a quick fix.  We needed to change all doLogOuts to call the security's logout.  Should
        //  eventually change all jsps to go directly to security instead of through this method.

//        appReqBlock.getHttpSession().invalidate();

        new SecurityAdminTran().logout(appReqBlock);

        return LOGIN_JSP;
    }

    protected String goToMain(AppReqBlock appReqBlock) throws Exception
    {
        appReqBlock.getHttpSession().removeAttribute("searchValue");

        UserSession userSession = (UserSession) appReqBlock.getHttpSession().getAttribute("userSession");

        userSession.clearParameters();

        userSession.unlock();       // remove all locks

        //  Don't log out, just return to main page
        return PORTAL;
    }

    /**
     * Displays the help dialog
     * @param appReqBlock
     * @return
     * @throws Exception
     */
    protected String showHelp(AppReqBlock appReqBlock) throws Exception
    {
        return HELP;
    }

    //******************************
    //*    Private Methods         *
    //******************************
    public void initUserSession(AppReqBlock appReqBlock, String action) throws Exception
    {
        // Build Session Beans
        buildQuoteBeans(appReqBlock);
        buildContractBeans(appReqBlock);
        buildPraseBeans(appReqBlock);
        buildAccountingBeans(appReqBlock);
        buildClientBeans(appReqBlock);

        appReqBlock.getHttpSession().removeAttribute("uiAgentHierarchyVOs");
        appReqBlock.getHttpSession().removeAttribute("contractRequirementVO");
        appReqBlock.getHttpSession().removeAttribute("selectedContractRequirementVO");
        appReqBlock.getHttpSession().removeAttribute("depositsVOs");
        appReqBlock.getHttpSession().removeAttribute("currentDepositVO");
        appReqBlock.getHttpSession().removeAttribute("contractSuspenseVOs");
    }

    private void buildQuoteBeans(AppReqBlock appReqBlock) throws Exception
    {
        SessionBean quoteMainSessionBean = new SessionBean();
        SessionBean quoteNotesSessionBean = new SessionBean();
        SessionBean quoteTaxesSessionBean = new SessionBean();

        SessionBean contractClientAddSessionBean = new SessionBean();

        SessionBean clients = new SessionBean();
        SessionBean riders = new SessionBean();
        SessionBean agents = new SessionBean();
        SessionBean funds = new SessionBean();
        SessionBean transactions = new SessionBean();
        SessionBean payeeOverrides = new SessionBean();
        SessionBean investmentOverrides = new SessionBean();
        SessionBean chargeOverrides = new SessionBean();
        SessionBean quoteNotes = new SessionBean();

        SessionBean stateBean = new SessionBean();

        appReqBlock.addSessionBean("quoteMainSessionBean", quoteMainSessionBean);
        appReqBlock.addSessionBean("quoteNotesSessionBean", quoteNotesSessionBean);
        appReqBlock.addSessionBean("quoteTaxesSessionBean", quoteTaxesSessionBean);

        appReqBlock.addSessionBean("contractClientAddSessionBean", contractClientAddSessionBean);

        appReqBlock.addSessionBean("quoteClients", clients);
        appReqBlock.addSessionBean("quoteRiders", riders);
        appReqBlock.addSessionBean("quoteStateBean", stateBean);
        appReqBlock.addSessionBean("quoteAgents", agents);
        appReqBlock.addSessionBean("quoteFunds", funds);
        appReqBlock.addSessionBean("quoteTransactions", transactions);
        appReqBlock.addSessionBean("quotePayeeOverrides", payeeOverrides);
        appReqBlock.addSessionBean("quoteInvestmentOverrides", investmentOverrides);
        appReqBlock.addSessionBean("quoteChargeOverrides", chargeOverrides);
        appReqBlock.addSessionBean("quoteNotes", quoteNotes);
    }

    private void buildContractBeans(AppReqBlock appReqBlock) throws Exception
    {
        SessionBean contractMainSessionBean = new SessionBean();
        SessionBean contractTransactionSessionBean = new SessionBean();
        SessionBean contractNotesSessionBean = new SessionBean();
        SessionBean contractHistoryChargesSessionBean = new SessionBean();
        SessionBean contractTaxesSessionBean = new SessionBean();
        SessionBean contractDepositBucketsBean = new SessionBean();

        SessionBean contractClientAddSessionBean = new SessionBean();

        SessionBean base = new SessionBean();
        SessionBean clients = new SessionBean();
        SessionBean riders = new SessionBean();
        SessionBean agents = new SessionBean();
        SessionBean funds = new SessionBean();
        SessionBean fundActivities = new SessionBean();
        SessionBean histories = new SessionBean();
        SessionBean transactions = new SessionBean();
        SessionBean payeeOverrides = new SessionBean();
        SessionBean investmentOverrides = new SessionBean();
        SessionBean chargeOverrides = new SessionBean();
        SessionBean realTimeLogBean = new SessionBean();
        SessionBean suspenseBean = new SessionBean();
        SessionBean transSuspenseBean = new SessionBean();
        SessionBean transactionsClone = new SessionBean();
        SessionBean notes = new SessionBean();

        SessionBean stateBean = new SessionBean();

        appReqBlock.addSessionBean("contractMainSessionBean", contractMainSessionBean);
        appReqBlock.addSessionBean("contractTransactionSessionBean", contractTransactionSessionBean);
        appReqBlock.addSessionBean("contractNotesSessionBean", contractNotesSessionBean);
        appReqBlock.addSessionBean("contractHistoryChargesSessionBean", contractHistoryChargesSessionBean);
        appReqBlock.addSessionBean("contractTaxesSessionBean", contractTaxesSessionBean);
        appReqBlock.addSessionBean("contractDepositBucketsBean", contractDepositBucketsBean);

        appReqBlock.addSessionBean("contractClientAddSessionBean", contractClientAddSessionBean);

        appReqBlock.addSessionBean("contractBase", base);
        appReqBlock.addSessionBean("contractClients", clients);
        appReqBlock.addSessionBean("contractRiders", riders);
        appReqBlock.addSessionBean("contractStateBean", stateBean);
        appReqBlock.addSessionBean("contractAgents", agents);
        appReqBlock.addSessionBean("contractFunds", funds);
        appReqBlock.addSessionBean("contractFundActivities", fundActivities);
        appReqBlock.addSessionBean("contractHistories", histories);
        appReqBlock.addSessionBean("contractTransactions", transactions);
        appReqBlock.addSessionBean("contractPayeeOverrides", payeeOverrides);
        appReqBlock.addSessionBean("contractInvestmentOverrides", investmentOverrides);
        appReqBlock.addSessionBean("contractChargeOverrides", chargeOverrides);
        appReqBlock.addSessionBean("contractRealTimeLogBean", realTimeLogBean);
        appReqBlock.addSessionBean("contractSuspense", suspenseBean);
        appReqBlock.addSessionBean("contractTransSuspense", transSuspenseBean);
        appReqBlock.addSessionBean("contractTransactionsClone", transactionsClone);
        appReqBlock.addSessionBean("contractNotes", notes);
    }

    public void buildPraseBeans(AppReqBlock aAppReqBlock)
    {
        SessionBean tableBean = new SessionBean();
        aAppReqBlock.addSessionBean("tableBean", tableBean);

        SessionBean paramBean = new SessionBean();
        aAppReqBlock.addSessionBean("paramBean", paramBean);

        SessionBean unitValueSessionBean = new SessionBean();
        aAppReqBlock.addSessionBean("unitValueSessionBean", unitValueSessionBean);

        SessionBean unitValues = new SessionBean();
        aAppReqBlock.addSessionBean("unitValues", unitValues);

        SessionBean fundSummaries = new SessionBean();
        aAppReqBlock.addSessionBean("fundSummaries", fundSummaries);

        SessionBean fundInterestSummaries = new SessionBean();
        aAppReqBlock.addSessionBean("fundInterestSummaries", fundInterestSummaries);

        SessionBean interestRateSessionBean = new SessionBean();
        aAppReqBlock.addSessionBean("interestRateSessionBean", interestRateSessionBean);

        SessionBean interestRates = new SessionBean();
        aAppReqBlock.addSessionBean("interestRates", interestRates);

        setAvailableRiders(aAppReqBlock);

    }

    private void setAvailableRiders(AppReqBlock aAppReqBlock)
    {
        SessionBean availableRidersBean = new SessionBean();

        String[] riders = new String[]{"Accidental Death Benefit",
                                       "Waiver of Monthly Deduction",
                                       "Waiver of Premium",
                                       "Waiver Of Specified Amount",
                                       "Increase", "Child Term",
                                       "Spouse Term", "Primary Term"};

        String[] riderIds = new String[]{"ADB", "WAIVEM", "WAIVEP",
                                         "WAIVES", "INCREASE",
                                         "TERMC", "TERMS", "TERMP"};

        availableRidersBean.putValues("riders",
                riders,
                new String[]{"toString"},
                ",");
        availableRidersBean.putValues("riderIds",
                riderIds,
                new String[]{"toString"},
                ",");

        aAppReqBlock.addSessionBean("availableRidersBean", availableRidersBean);
    }

    public void buildAccountingBeans(AppReqBlock appReqBlock) throws Exception
    {
        // Add Necessary Page SessionBeans
        SessionBean accountingSummarySessionBean = new SessionBean();
        appReqBlock.addSessionBean("accountingSummarySessionBean", accountingSummarySessionBean);
    }

    public void buildClientBeans(AppReqBlock appReqBlock) throws Exception
    {
        // Add Necessary Page SessionBeans
        SessionBean clientDetailSessionBean = new SessionBean();
        //SessionBean clientDetailOwnerSessionBean = new SessionBean();
        SessionBean clientAddressSessionBean = new SessionBean();
        SessionBean contactInfoSessionBean = new SessionBean();
        SessionBean bankAccountSessionBean = new SessionBean();
        SessionBean taxInformationSessionBean = new SessionBean();
        SessionBean substandardSessionBean = new SessionBean();
        SessionBean preferenceSessionBean = new SessionBean();
        SessionBean profileSessionBean = new SessionBean();
        SessionBean roleSessionBean = new SessionBean();
        SessionBean stateBean = new SessionBean();
        appReqBlock.addSessionBean("clientDetailSessionBean", clientDetailSessionBean);
        //appReqBlock.addSessionBean("clientDetailOwnerSessionBean", clientDetailOwnerSessionBean);
        appReqBlock.addSessionBean("clientAddressSessionBean", clientAddressSessionBean);
        appReqBlock.addSessionBean("contactInfoSessionBean", contactInfoSessionBean);
        appReqBlock.addSessionBean("bankAccountSessionBean", bankAccountSessionBean);
        appReqBlock.addSessionBean("taxInformationSessionBean", taxInformationSessionBean);
        appReqBlock.addSessionBean("substandardSessionBean", substandardSessionBean);
        appReqBlock.addSessionBean("preferenceSessionBean", preferenceSessionBean);
        appReqBlock.addSessionBean("profileSessionBean", profileSessionBean);
        appReqBlock.addSessionBean("roleSessionBean", roleSessionBean);
        appReqBlock.addSessionBean("clientStateBean", stateBean);
    }

    public void clearUserSessionBean(AppReqBlock appReqBlock) throws Exception
    {
        SessionBean userBean = appReqBlock.getSessionBean("userBean");
        if (userBean != null)
        {

            userBean.clearState();
        }
    }

    public void initSessionBeans(AppReqBlock appReqBlock) throws Exception
    {
        // Building the beans from scratch will re-init their state.
        buildQuoteBeans(appReqBlock);
        buildContractBeans(appReqBlock);
        buildPraseBeans(appReqBlock);
        buildAccountingBeans(appReqBlock);
    }

    /**
     * Renders the main page for setting-up a Bonus Program.
     */
    private String showAgentBonusProgram(AppReqBlock appReqBlock)
    {
        new AgentUseCaseComponent().accessAgentBonusProgram();

        BonusProgram[] bonusPrograms = BonusProgram.findAll();

        appReqBlock.putInRequestScope("bonusPrograms", bonusPrograms);

        new AgentBonusPremiumLevelTableModel(appReqBlock);

        appReqBlock.putInRequestScope("toolbar", AGENT_BONUS_TOOLBAR);

        appReqBlock.putInRequestScope("main", AGENT_BONUS_PROGRAM);

        return TEMPLATE_TOOLBAR_MAIN;
    }

    /**
     * Renders the initial page that allows one Agent/Situation to be moved under
     * another Agent/Situation.
     */
    private String showAgentMove(AppReqBlock appReqBlock)
    {
        new AgentUseCaseComponent().accessAgentHierarchy();

        new AgentMoveFromTableModel(appReqBlock, null, null, null);

        new AgentMoveToTableModel(appReqBlock, null, null, null);

        new AgentMoveSelectedTableModel(appReqBlock, null, null);

        appReqBlock.putInRequestScope("toolbar", AGENT_MOVE_TOOLBAR);

        appReqBlock.putInRequestScope("main", AGENT_MOVE);

        return TEMPLATE_TOOLBAR_MAIN;
    }
}
