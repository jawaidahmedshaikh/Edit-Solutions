/*
* RequestManager.java      Version 2.00  06/04/2001
*
* Copyright (c) 2000-2004 Systems Engineering Group, LLC. All Rights Reserved.
*
* This program is the confidential and proprietary information of
* Systems Engineering Group, LLC and may not be copied in whole or in part
* without the written permission of Systems Engineering Group, LLC.
*/
package fission.ui.servlet;

import accounting.ui.servlet.AccountingDetailTran;

import agent.ui.servlet.AgentBonusTran;
import agent.ui.servlet.AgentDetailTran;

import businesscalendar.ui.servlet.BusinessCalendarTran;

import casetracking.ui.servlet.CaseTrackingTran;

import client.ui.servlet.ClientDetailTran;

import codetable.ui.servlet.CodeTableTran;

import contract.ui.servlet.CaseDetailTran;
import contract.ui.servlet.ContractDetailTran;
import contract.ui.servlet.DailyDetailTran;
import contract.ui.servlet.QuoteDetailTran;

import edit.common.exceptions.EDITSecurityAccessException;
import edit.common.exceptions.VOEditException;

import edit.portal.common.session.UserSession;
import edit.portal.common.transactions.AutoSchedulerTran;
import edit.portal.common.transactions.PortalLoginTran;
import edit.portal.common.transactions.Transaction;
import edit.portal.exceptions.PortalEditingException;

import engine.ui.servlet.AppletScriptTran;
import engine.ui.servlet.AreaTran;
import engine.ui.servlet.ProductRuleStructureTran;
import engine.ui.servlet.ProductStructureTran;
import engine.ui.servlet.FileExportTran;
import engine.ui.servlet.FundTran;
import engine.ui.servlet.ParamTran;
import engine.ui.servlet.ScriptTran;
import engine.ui.servlet.TableTran;

import event.ui.servlet.EventAdminTran;

import fission.beans.DataResultBean;
import fission.beans.FormBean;
import fission.beans.SessionBean;

import fission.global.AppReqBlock;

import java.io.IOException;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import logging.ui.servlet.LoggingAdminTran;

import reinsurance.ui.servlet.ReinsuranceTran;

import reporting.ui.servlet.ReportingDetailTran;

import role.ui.servlet.RoleTran;

import search.ui.servlet.SearchTran;

import security.ui.servlet.SecurityAdminTran;
import productbuild.ui.servlet.*;

/**
 * This class is the main controller for the EDITSolutions System. All EDITSolutions screen requests are sent to
 * this servlet and then redirected to the appropriate areas.
 *
 * The class was modelled using the Model View Controller (MVC) design pattern.
 */
public class RequestManager extends BaseRequestManager
{
    // Constants
    private static final String AUTHORIZATION_ERROR_PAGE = "/security/jsp/authorizationError.jsp";
    private static final String EXPIRED_SESSION_PAGE = "/security/jsp/expiredSessionError.jsp";
    private static final String LOGIN_PAGE = "/security/jsp/login.jsp";
    private static final String EXPIRED_PASSWORD_PAGE = "/security/jsp/expiredPasswordError.jsp";
    private static final String COMPANY_STRUCTURE_NOT_SET_ERROR_PAGE = "/security/jsp/productStructureNotSetError.jsp";
    private static final String TERMINATED_OPERATOR_ERROR_PAGE = "/security/jsp/terminatedOperatorError.jsp";

    // Member variables
    private boolean debug = false;
    private Map transactionPool;

    public void init(ServletConfig sc) throws ServletException
    {
        super.init(sc);

        try
        {
            loadTransactionObjects();
        }
        catch (Exception e)
        {
            System.out.println(e);

            e.printStackTrace();

            throw new ServletException(e);
        }
    }

    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException
    {
        processRequest(req, res);
    }

    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException
    {
        processRequest(req, res);
    }

    private void processRequest(HttpServletRequest req, HttpServletResponse res) throws ServletException
    {
        AppReqBlock appReqBlock = null;
        Transaction aTran = null;
        String tranName = null;
        String actionName = null;
        String nextPage = null;

        try
        {
            if (req.getSession().getAttribute("userSession") == null)
            {
                UserSession userSession = new UserSession("SEGDev", req.getSession());

                req.getSession().setAttribute("userSession", userSession);
            }

            tranName = req.getParameter("transaction");

            actionName = req.getParameter("action");

            FormBean formBean = populateFormBean(req);

            appReqBlock = new AppReqBlock(req, res, getServletContext(), formBean, null);

            aTran = (edit.portal.common.transactions.Transaction) transactionPool.get(tranName);

            nextPage = aTran.execute(appReqBlock);

            if (debug)
            {
                System.out.println("------------------------------------");
                System.out.println("transaction: " + tranName);
                System.out.println("action: " + actionName);
                System.out.println("nextPage: " + nextPage);
            }
        }
        catch (EDITSecurityAccessException e)
        {
            if ((e.getErrorType() == EDITSecurityAccessException.LOGIN_ERROR) || (e.getErrorType() == EDITSecurityAccessException.SESSION_TIMEOUT_ERROR) ||
                 (e.getErrorType() == EDITSecurityAccessException.USER_LOCKED_EXCEPTION))
            {
                String targetTransaction = appReqBlock.getReqParm("targetTransaction");

                String targetAction = appReqBlock.getReqParm("targetAction");

                if ((targetTransaction == null) && (targetAction == null))
                {
                    targetTransaction = tranName;

                    targetAction = actionName;
                }

                appReqBlock.getHttpServletRequest().setAttribute("targetTransaction", targetTransaction);

                appReqBlock.getHttpServletRequest().setAttribute("targetAction", targetAction);

                UserSession userSession = (UserSession) req.getSession().getAttribute("userSession");

                userSession.logout();

                nextPage = LOGIN_PAGE;
            }

            else if (e.getErrorType() == EDITSecurityAccessException.SESSION_TIMEOUT_ERROR)
            {
                UserSession userSession = (UserSession) req.getSession().getAttribute("userSession");

                userSession.logout();

                nextPage = EXPIRED_SESSION_PAGE;
            }

            else if (e.getErrorType() == EDITSecurityAccessException.AUTHORIZATION_ERROR)
            {
                nextPage = AUTHORIZATION_ERROR_PAGE;
            }
            else if (e.getErrorType() == EDITSecurityAccessException.COMPANY_STRUCTURE_NOT_SET_EXCEPTION)
            {
                UserSession userSession = (UserSession) req.getSession().getAttribute("userSession");

                userSession.logout();

                nextPage = COMPANY_STRUCTURE_NOT_SET_ERROR_PAGE;
            }
            else if (e.getErrorType() == EDITSecurityAccessException.EXPIRED_PASSWORD_EXCEPTION)
            {
                UserSession userSession = (UserSession) req.getSession().getAttribute("userSession");

                userSession.logout();

                nextPage = EXPIRED_PASSWORD_PAGE;
            }
            else if (e.getErrorType() == EDITSecurityAccessException.TERMINATED_OPERATOR_EXCEPTION)
            {
                UserSession userSession = (UserSession) req.getSession().getAttribute("userSession");

                userSession.logout();

                nextPage = TERMINATED_OPERATOR_ERROR_PAGE;
            }

            req.setAttribute("message", e.getMessage());
            req.getSession().setAttribute("LastRequestErrorMesssage", e.getMessage());
        }
        catch (PortalEditingException e)
        {
            e.setTransaction(tranName);
            e.setAction(appReqBlock.getReqParm("action"));
            req.getSession().setAttribute("portalEditingException", e);

            nextPage = e.getReturnPage();

            SessionBean stateBean = appReqBlock.getSessionBean("quoteStateBean");
            stateBean.putValue("currentPage", nextPage);
        }
        catch (VOEditException e)
        {
            e.setTransaction(tranName);
            e.setAction(appReqBlock.getReqParm("action"));
            req.getSession().setAttribute("VOEditException", e);

            nextPage = e.getReturnPage();

            SessionBean stateBean = appReqBlock.getSessionBean("quoteStateBean");
            stateBean.putValue("currentPage", nextPage);
        }
        catch (Throwable e)
        {
            System.out.println(e);

            e.printStackTrace();

            appReqBlock.getHttpServletRequest().setAttribute("except", e);

            nextPage = "/common/jsp/error.jsp";
        }

        // Forward request to present the next page
        try
        {
            if (nextPage.equals("appletData"))
            {
                writeData(req, res, appReqBlock);
            }
            else
            {
                RequestDispatcher rd = getServletContext().getRequestDispatcher(nextPage);

                rd.forward(req, res);
            }
        }
        catch (Throwable e)
        {
            System.out.println(e);

            e.printStackTrace(); //To change body of catch statement use File | Settings | File Templates.

            appReqBlock.getHttpServletRequest().setAttribute("except", e);

            throw new ServletException(e);
        }
    }

    // Populates FormBean with Request name/value Pairs
    private FormBean populateFormBean(HttpServletRequest req)
    {
        FormBean formBean = new FormBean();

        java.util.Enumeration names = req.getParameterNames();

        while (names.hasMoreElements())
        {
            String name = (String) names.nextElement();

            formBean.putValue(name, req.getParameter(name));
        }

        return formBean;
    }

    private void writeData(HttpServletRequest req, HttpServletResponse res, AppReqBlock aAppReqBlock) throws IOException
    {
        DataResultBean dataBean = (DataResultBean) aAppReqBlock.getHttpServletRequest().getAttribute("dataBean");

        java.io.ObjectOutputStream objectOutputStream = new java.io.ObjectOutputStream(res.getOutputStream());

        objectOutputStream.writeObject(dataBean);
        objectOutputStream.flush();
        objectOutputStream.close();
    }

    /**
     * Preloads all the transaction objects into the transaction pool
     */
    protected void loadTransactionObjects()
    {
        transactionPool = new HashMap();

        PortalLoginTran portalLoginTran = new PortalLoginTran();
        transactionPool.put("PortalLoginTran", portalLoginTran);

        ClientDetailTran clientDetailTran = new ClientDetailTran();
        transactionPool.put("ClientDetailTran", clientDetailTran);

        QuoteDetailTran quoteDetailTran = new QuoteDetailTran();
        transactionPool.put("QuoteDetailTran", quoteDetailTran);

        ContractDetailTran contractDetailTran = new ContractDetailTran();
        transactionPool.put("ContractDetailTran", contractDetailTran);

        DailyDetailTran dailyDetailTran = new DailyDetailTran();
        transactionPool.put("DailyDetailTran", dailyDetailTran);

        RoleTran roleTran = new RoleTran();
        transactionPool.put("RoleTran", roleTran);

        ReportingDetailTran reportingDetailTran = new ReportingDetailTran();
        transactionPool.put("ReportingDetailTran", reportingDetailTran);

        ScriptTran scriptTran = new ScriptTran();
        transactionPool.put("ScriptTran", scriptTran);

        TableTran tableTran = new TableTran();
        transactionPool.put("TableTran", tableTran);

        ParamTran paramTran = new ParamTran();
        transactionPool.put("ParamTran", paramTran);

        ProductRuleStructureTran productRuleStructureTran = new ProductRuleStructureTran();
        transactionPool.put("ProductRuleStructureTran", productRuleStructureTran);

        ProductStructureTran productStructureTran = new ProductStructureTran();
        transactionPool.put("ProductStructureTran", productStructureTran);

        AppletScriptTran appletScriptTran = new AppletScriptTran();
        transactionPool.put("AppletScriptTran", appletScriptTran);

        AccountingDetailTran accountingDetailTran = new AccountingDetailTran();
        transactionPool.put("AccountingDetailTran", accountingDetailTran);

        SearchTran searchTran = new SearchTran();
        transactionPool.put("SearchTran", searchTran);

        FundTran fundTran = new FundTran();
        transactionPool.put("FundTran", fundTran);

        AutoSchedulerTran autoSchedulerTran = new AutoSchedulerTran();
        transactionPool.put("AutoSchedulerTran", autoSchedulerTran);

        SecurityAdminTran securityAdminTran = new SecurityAdminTran();
        transactionPool.put("SecurityAdminTran", securityAdminTran);

        EventAdminTran eventAdminTran = new EventAdminTran();
        transactionPool.put("EventAdminTran", eventAdminTran);

        LoggingAdminTran loggingAdminTran = new LoggingAdminTran();
        transactionPool.put("LoggingAdminTran", loggingAdminTran);

        AgentDetailTran agentDetailTran = new AgentDetailTran();
        transactionPool.put("AgentDetailTran", agentDetailTran);

        CodeTableTran codeTableTran = new CodeTableTran();
        transactionPool.put("CodeTableTran", codeTableTran);

        AreaTran areaTran = new AreaTran();
        transactionPool.put("AreaTran", areaTran);

        ReinsuranceTran reinsuranceTran = new ReinsuranceTran();
        transactionPool.put("ReinsuranceTran", reinsuranceTran);

        BusinessCalendarTran businessCalendarTran = new BusinessCalendarTran();
        transactionPool.put("BusinessCalendarTran", businessCalendarTran);

        AgentBonusTran agentBonusTran = new AgentBonusTran();
        transactionPool.put("AgentBonusTran", agentBonusTran);

        CaseTrackingTran caseTrackingTran = new CaseTrackingTran();
        transactionPool.put("CaseTrackingTran", caseTrackingTran);

        CaseDetailTran caseDetailTran = new CaseDetailTran();
        transactionPool.put("CaseDetailTran", caseDetailTran);
  
        FileExportTran fileExportTran = new FileExportTran();
        transactionPool.put("FileExportTran", fileExportTran);

        ProductBuildTran productBuildTran = new ProductBuildTran();
        transactionPool.put("ProductBuildTran", productBuildTran);        
  }
}
