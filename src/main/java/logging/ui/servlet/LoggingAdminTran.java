/*
 * User: gfrosti
 * Date: Feb 12, 2003
 * Time: 1:16:36 PM
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package logging.ui.servlet;

import edit.common.exceptions.EDITDeleteException;
import edit.common.exceptions.EDITSaveException;
import edit.common.vo.LogEntryCollectionVO;
import edit.common.vo.LogEntryVO;
import edit.portal.common.transactions.Transaction;
import edit.portal.widget.LogColumnTableModel;
import edit.portal.widget.LogTableModel;
import edit.portal.widget.LogDetailTableModel;

import edit.portal.widgettoolkit.TableModel;

import edit.services.config.ServicesConfig;
import edit.services.db.hibernate.SessionHelper;
import edit.services.logging.Logging;
import fission.global.AppReqBlock;
import fission.utility.*;
import org.w3c.tidy.Tidy;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import logging.Log;

import logging.LogColumn;

import logging.component.LoggingComponent;


public class LoggingAdminTran extends Transaction
{
    // Pages
    private static final String LOG_SUMMARY_JSP = "/logging/jsp/logSummary.jsp";
    private static final String CLIENT_SAVE_VALIDATION = "/logging/jsp/clientSaveValidation.jsp";
    private static final String TRANSACTION_SAVE_VALIDATION = "/logging/jsp/transactionSaveValidation.jsp";
    private static final String SCRIPT_PROCESSOR = "/logging/jsp/scriptProcessor.jsp";
    private static final String CLIENT_TRANSACTION = "/logging/jsp/clientTransaction.jsp";
    private static final String GENERAL_EXCEPTION = "/logging/jsp/generalException.jsp";
    private static final String CONTRACT_SAVE_VALIDATION = "/logging/jsp/contractSaveValidation.jsp";
    private static final String AGENT_SAVE_VALIDATION = "/logging/jsp/agentSaveValidation.jsp";
    private static final String VALIDATE_UNSPECIFIED = "/logging/jsp/validateUnspecified.jsp";
    private static final String BATCH_JOB = "/logging/jsp/batchJob.jsp";
    private static final String SCRIPT = "/logging/jsp/script.jsp";
    private static final String ACCOUNTING = "/logging/jsp/accounting.jsp";

    private static final String LOGGING_TOOLBAR = "/logging/jsp/loggingToolBar.jsp";
    private static final String LOGGING_ADMIN = "/logging/jsp/loggingMain.jsp";
    private static final String TEMPLATE_TOOLBAR_MAIN = "/common/jsp/template/template-toolbar-main.jsp";
    private static final String TEMPLATE_MAIN = "/common/jsp/template/template-main.jsp";
    private static final String LOG_DETAIL = "/logging/jsp/logDetailDialog.jsp";
    private static final String GENERAL_EXCEPTIONS_LOG_DISPLAY = "/logging/jsp/logGeneralExceptionsDialog.jsp";

    // Actions
    private static final String SHOW_LOG_SUMMARY = "showLogSummary";
    private static final String SHOW_LOG_DETAIL = "showLogDetail";
    private static final String SHOW_MESSAGE_DETAIL = "showMessageDetail";
    private static final String SHOW_MESSAGE_LIST = "showMessageList";
    private static final String SHOW_STACK_LIST = "showStackList";
    private static final String SHOW_TRACE = "showTrace";
    private static final String SHOW_SCRIPT = "showScript";
    private static final String SHOW_LOGGING_ADMIN = "showLoggingAdmin";
    private static final String ADD_NEW_LOG = "addNewLog";
    private static final String CANCEL_LOG = "cancelLog";
    private static final String SELECT_LOG_ROW = "selectLogRow";
    private static final String SAVE_LOG = "saveLog";
    private static final String CANCEL_LOG_COLUMN = "cancelLogColumn";
    private static final String SAVE_LOG_COLUMN = "saveLogColumn";
    private static final String ADD_NEW_LOG_COLUMN = "addNewLogColumn";
    private static final String DELETE_LOG = "deleteLog";
    private static final String DELETE_LOG_COLUMN = "deleteLogColumn";
    private static final String SHOW_ASSOCIATED_COLUMNS = "showAssociatedColumns";
    private static final String SHOW_GENERAL_EXCEPTIONS_LOG = "showGeneralExceptionsLog";

    private static final String SHOW_LOG_ENTRIES = "showLogEntries";

    public String execute(AppReqBlock appReqBlock) throws Exception
    {
        String action = appReqBlock.getHttpServletRequest().getParameter("action");

        String nextPage = null;

        if (action.equalsIgnoreCase(SHOW_LOG_SUMMARY))
        {
            nextPage = showLogSummary(appReqBlock);
        }
        else if (action.equals(SHOW_LOG_DETAIL))
        {
            nextPage = showLogDetail(appReqBlock);
        }
        else if (action.equals(SHOW_SCRIPT))
        {
            nextPage = showScript(appReqBlock);
        }
        else if (action.equals(SHOW_LOGGING_ADMIN))
        {
            nextPage = showLogging(appReqBlock);
        }
        else if (action.equals(ADD_NEW_LOG))
        {
            nextPage = addNewLog(appReqBlock);
        }
        else if (action.equals(CANCEL_LOG))
        {
            nextPage = cancelLog(appReqBlock);
        }
        else if (action.equals(SELECT_LOG_ROW))
        {
            nextPage = selectLogRow(appReqBlock);
        }
        else if (action.equals(SAVE_LOG))
        {
            return saveLog(appReqBlock);
        }
        else if (action.equals(CANCEL_LOG_COLUMN))
        {
            return cancelLogColumn(appReqBlock);
        }
        else if (action.equals(SAVE_LOG_COLUMN))
        {
            return saveLogColumn(appReqBlock);
        }
        else if (action.equals(ADD_NEW_LOG_COLUMN))
        {
            return addNewLogColumn(appReqBlock);
        }
        else if (action.equals(DELETE_LOG))
        {
            return deleteLog(appReqBlock);
        }
        else if (action.equals(DELETE_LOG_COLUMN))
        {
            return deleteLogColumn(appReqBlock);
        }
        else if (action.equals(SHOW_ASSOCIATED_COLUMNS))
        {
            return showAssociatedColumns(appReqBlock);
        }
        else if (action.equals(SHOW_LOG_ENTRIES))
        {
            return showLogEntries(appReqBlock);
        }
        else if (action.equals(SHOW_GENERAL_EXCEPTIONS_LOG))
        {
            return showGeneralExceptionsLog(appReqBlock);
        }
        else
        {
            throw new Exception("Invalid Action/Transaction");
        }

        return nextPage;
    }

    /**
     * Shows the Script Lines of the specified Script.
     *
     * @param appReqBlock
     *
     * @return
     */
    private String showScript(AppReqBlock appReqBlock) throws Exception
    {
        String logEntryId = appReqBlock.getReqParm("logEntryId");

        String fileName = ServicesConfig.getEDITLogByLogName(Logging.EXECUTE_SCRIPT).getFile();

        LogEntryCollectionVO logEntryCollectionVO = getLogEntryCollectionVO(new File(fileName));

        appReqBlock.getHttpServletRequest().setAttribute("logEntryId", logEntryId);

        appReqBlock.getHttpServletRequest().setAttribute("logEntryCollectionVO", logEntryCollectionVO);

        appReqBlock.getHttpServletRequest().setAttribute("logName", Logging.EXECUTE_SCRIPT);

        return SCRIPT;
    }

    /**
     * Locates a specific ordered LogEntryVO within the specified log file.
     *
     * @param fileName
     * @param logEntryId
     *
     * @return
     *
     * @throws Exception
     */
    private LogEntryVO getLogEntryVO(String fileName, String logEntryId) throws Exception
    {
        LogEntryVO logEntryVO = null;

        LogEntryCollectionVO logEntryCollectionVO = getLogEntryCollectionVO(new File(fileName));

        LogEntryVO[] logEntryVOs = logEntryCollectionVO.getLogEntryVO();

        for (int j = 0; j < logEntryVOs.length; j++)
        {
            LogEntryVO currentLogEntryVO = logEntryVOs[j];

            if (String.valueOf(j).equals(logEntryId))
            {
                logEntryVO = currentLogEntryVO;

                break;
            }
        }

        return logEntryVO;
    }

    /**
     * Returns the appropriate log page based on the log name as configured in EDITServicesConfig.xml
     *
     * @param appReqBlock
     *
     * @return
     *
     * @throws Exception
     */
    protected String showLogDetail(AppReqBlock appReqBlock) throws Exception
    {
        String returnPage = null;

        String logName = initParam(appReqBlock.getReqParm("logName"), null);

        String fileName = ServicesConfig.getEDITLogByLogName(logName).getFile();

        LogEntryCollectionVO logEntryCollectionVO = getLogEntryCollectionVO(new File(fileName));

        appReqBlock.getHttpServletRequest().setAttribute("logEntryCollectionVO", logEntryCollectionVO);

        appReqBlock.getHttpServletRequest().setAttribute("logName", logName);

        if (logName.equals(Logging.EXECUTE_SCRIPT))
        {
            returnPage = SCRIPT_PROCESSOR;
        }
        else if (logName.equals(Logging.EXECUTE_TRANSACTION))
        {
            returnPage = CLIENT_TRANSACTION;
        }
        else if (logName.equals(Logging.GENERAL_EXCEPTION))
        {
            returnPage = GENERAL_EXCEPTION;
        }
        else if (logName.equals(Logging.VALIDATE_AGENT_SAVE))
        {
            returnPage = AGENT_SAVE_VALIDATION;
        }
        else if (logName.equals(Logging.VALIDATE_CLIENT_SAVE))
        {
            returnPage = CLIENT_SAVE_VALIDATION;
        }
        else if (logName.equals(Logging.VALIDATE_CONTRACT_SAVE))
        {
            returnPage = CONTRACT_SAVE_VALIDATION;
        }
        else if (logName.equals(Logging.VALIDATE_TRANSACTION_SAVE))
        {
            returnPage = TRANSACTION_SAVE_VALIDATION;
        }
        else if (logName.equals(Logging.VALIDATE_UNSPECIFIED))
        {
            returnPage = VALIDATE_UNSPECIFIED;
        }
        else if (logName.equals(Logging.BATCH_JOB))
        {
            returnPage = BATCH_JOB;
        }
        else if (logName.equals(Logging.ACCOUNTING))
        {
            returnPage = ACCOUNTING;
        }

        return returnPage;
    }

    private StringBuffer getFileLines(File file) throws IOException
    {
        StringBuffer logLines = new StringBuffer();
        BufferedReader reader = new BufferedReader(new FileReader(file));

        String logLine = null;

        while ((logLine = reader.readLine()) != null)
        {
            logLines.append(logLine);
        }

        reader.close();

        return logLines;
    }

    protected String showLogSummary(AppReqBlock appReqBlock) throws Exception
    {
        File[] logFiles = getLogFiles(appReqBlock);

        appReqBlock.getHttpServletRequest().setAttribute("logFiles", logFiles);

        return LoggingAdminTran.LOG_SUMMARY_JSP;
    }

    private File[] getLogFiles(AppReqBlock appReqBlock)
    {
        List allLogFiles = new ArrayList();

        ServletContext context = appReqBlock.getServletContext();

        HttpServletRequest req = appReqBlock.getHttpServletRequest();

        String portalDir = context.getRealPath(UtilFile.DIRECTORY_DELIMITER);

        if (!portalDir.endsWith(UtilFile.DIRECTORY_DELIMITER))
        {
            portalDir = portalDir + UtilFile.DIRECTORY_DELIMITER;
        }

        File logsDir = new File(portalDir + "logs");

        File[] logFiles = logsDir.listFiles();

        for (int i = 0; i < logFiles.length; i++)
        {
            if (logFiles[i].isFile() && logFiles[i].getName().indexOf(".log") >= 0)
            {
                allLogFiles.add(logFiles[i]);
            }
        }

        Collections.sort(allLogFiles);

        return (File[]) allLogFiles.toArray(new File[allLogFiles.size()]);
    }

    private LogEntryCollectionVO getLogEntryCollectionVO(File file) throws Exception
    {
        StringBuffer logLines = getFileLines(file);

        logLines.insert(0, "<LogEntryCollectionVO>");
        logLines.insert(logLines.length(), "</LogEntryCollectionVO");

        Tidy tidy = new Tidy();
        tidy.setXmlOut(true);
        tidy.setXmlTags(true);
        tidy.setShowWarnings(false);
        tidy.setQuiet(true);
        tidy.setWraplen(0);
        tidy.setNumEntities(true);

        ByteArrayInputStream bytesIn = new ByteArrayInputStream(logLines.toString().getBytes());
        ByteArrayOutputStream bytesOut = new ByteArrayOutputStream();
        tidy.parse(bytesIn, bytesOut);

        String logEntryCollectionVOAsXML = new String(bytesOut.toByteArray());

        LogEntryCollectionVO logEntryCollectionVO = (LogEntryCollectionVO) Util.unmarshalVO(LogEntryCollectionVO.class, logEntryCollectionVOAsXML);

        return logEntryCollectionVO;
    }
    
//  /**
//   * Renders the logginAdmin.jsp
//   * @param appReqBlock
//   * @return
//   */
//  private String showLoggingAdmin(AppReqBlock appReqBlock)
//  {
//    new LogTableModel(appReqBlock);
//  
//    appReqBlock.putInRequestScope("toolbar", LOGGING_TOOLBAR); 
//
//    appReqBlock.putInRequestScope("main", LOGGING_ADMIN); 
//    
//    return TEMPLATE_TOOLBAR_MAIN;
//  }

    /**
     * Main logging page for new logging
     *
     * @param appReqBlock
     *
     * @return
     */
    private String showLogging(AppReqBlock appReqBlock)
    {
        LogTableModel logTableModel = (LogTableModel) TableModel.get(appReqBlock, LogTableModel.class);

        LogColumnTableModel logColumnTableModel = (LogColumnTableModel) TableModel.get(appReqBlock, LogColumnTableModel.class);

        logColumnTableModel.setLog(logTableModel.getLog());

        appReqBlock.putInRequestScope("toolbar", LOGGING_TOOLBAR);

        appReqBlock.putInRequestScope("main", LOGGING_ADMIN);

        return TEMPLATE_TOOLBAR_MAIN;
    }

    /**
     * Adds a temporary dummy entry to the Log TableModel so that the user may
     * add a new Log.
     *
     * @param appReqBlock
     *
     * @return
     */
    private String addNewLog(AppReqBlock appReqBlock)
    {
        LogTableModel tableModel = (LogTableModel) TableModel.get(appReqBlock, LogTableModel.class);

        tableModel.requestEmptyTableRow();
    
//    tableModel.resetAllRows();

        return showLogging(appReqBlock);
    }

    /**
     * Cancels current modification activity and renders the logging page
     * in its default state.
     *
     * @param appReqBlock
     *
     * @return
     */
    private String cancelLog(AppReqBlock appReqBlock)
    {
        TableModel.get(appReqBlock, LogTableModel.class).resetAllRows();

        TableModel.get(appReqBlock, LogColumnTableModel.class).resetAllRows();

        return showLogging(appReqBlock);
    }

    /**
     * Makes the targeted Log Row active.
     *
     * @param appReqBlock
     *
     * @return
     */
    private String selectLogRow(AppReqBlock appReqBlock)
    {
        return showLogging(appReqBlock);
    }

    /**
     * Saves the details of the currently selected log.
     *
     * @param appReqBlock
     *
     * @return
     */
    private String saveLog(AppReqBlock appReqBlock)
    {
        Log log = (Log) Util.mapFormDataToHibernateEntity(appReqBlock.getHttpServletRequest(), Log.class, SessionHelper.EDITSOLUTIONS, false);

        LogTableModel logTableModel = (LogTableModel) TableModel.get(appReqBlock, LogTableModel.class);

        try
        {
            logging.business.Logging loggingComponent = new LoggingComponent();

            loggingComponent.saveUpdateLog(log);

            logTableModel.resetAllRows();
        }
        catch (EDITSaveException e)
        {
            logTableModel.requestEmptyTableRow(log);

            appReqBlock.putInRequestScope("pageMessage", e.getMessageList());
        }

        return showLogging(appReqBlock);
    }

    /**
     * Cancels current LogColumn editing activity and preserves
     * the state of the currently selected Log.
     *
     * @param appReqBlock
     *
     * @return
     */
    private String cancelLogColumn(AppReqBlock appReqBlock)
    {
        LogTableModel logTableModel = (LogTableModel) TableModel.get(appReqBlock, LogTableModel.class);

        TableModel.get(appReqBlock, LogColumnTableModel.class).resetAllRows();
        ;

        return showLogging(appReqBlock);
    }

    /**
     * Saves the details of the current LogColumn while preserving the state of
     * the currently selected Log.
     *
     * @param appReqBlock
     *
     * @return
     */
    private String saveLogColumn(AppReqBlock appReqBlock)
    {
        LogTableModel logTableModel = (LogTableModel) TableModel.get(appReqBlock, LogTableModel.class);

        Log log = logTableModel.getLog();

        LogColumn logColumn = (LogColumn) Util.mapFormDataToHibernateEntity(appReqBlock.getHttpServletRequest(), LogColumn.class, SessionHelper.EDITSOLUTIONS, false);

        try
        {
            logging.business.Logging loggingComponent = new LoggingComponent();

            loggingComponent.saveUpdateLogColumn(log, logColumn);

            TableModel.get(appReqBlock, LogColumnTableModel.class).resetAllRows();

        }
        catch (EDITSaveException e)
        {
            LogColumnTableModel logColumnTableModel = (LogColumnTableModel) TableModel.get(appReqBlock, LogColumnTableModel.class);

            logColumnTableModel.setLog(log);

            logColumnTableModel.requestEmptyTableRow(logColumn);

            appReqBlock.putInRequestScope("pageMessage", e.getMessageList());
        }

        return showLogging(appReqBlock);
    }


    /**
     * Adds a temporary dummy entry to the LogColumn TableModel so that the user may
     * add a new LogColumn.
     *
     * @param appReqBlock
     *
     * @return
     */
    private String addNewLogColumn(AppReqBlock appReqBlock)
    {
        LogTableModel logTableModel = (LogTableModel) TableModel.get(appReqBlock, LogTableModel.class);

        LogColumnTableModel logColumnTableModel = (LogColumnTableModel) TableModel.get(appReqBlock, LogColumnTableModel.class);

        logColumnTableModel.setLog(logTableModel.getLog());

        logColumnTableModel.requestEmptyTableRow();

        return showLogging(appReqBlock);
    }

    /**
     * Deletes the selected Log and its associated children.
     *
     * @param appReqBlock
     *
     * @return
     */
    private String deleteLog(AppReqBlock appReqBlock)
    {
        LogTableModel logTableModel = (LogTableModel) TableModel.get(appReqBlock, LogTableModel.class);

        Log selectedLog = logTableModel.getLog();

        try
        {
            logging.business.Logging loggingComponent = new LoggingComponent();

            loggingComponent.deleteLog(selectedLog);
        }
        catch (EDITDeleteException e)
        {
            appReqBlock.putInRequestScope("pageMessage", e.getMessageList());
        }

        return showLogging(appReqBlock);
    }

    /**
     * Deletes the currently selected LogColumn.
     *
     * @param appReqBlock
     *
     * @return
     */
    private String deleteLogColumn(AppReqBlock appReqBlock)
    {
        LogTableModel logTableModel = (LogTableModel) TableModel.get(appReqBlock, LogTableModel.class);

        Log selectedLog = logTableModel.getLog();

        LogColumnTableModel logColumnTableModel = (LogColumnTableModel) TableModel.get(appReqBlock, LogColumnTableModel.class);

        logColumnTableModel.setLog(selectedLog);

        LogColumn selectedLogColumn = logColumnTableModel.getLogColumn();

        try
        {
            logging.business.Logging loggingComponent = new LoggingComponent();

            loggingComponent.deleteLogColumn(selectedLogColumn);
        }
        catch (EDITDeleteException e)
        {
            appReqBlock.putInRequestScope("pageMessage", e.getMessageList());
        }

        return showLogging(appReqBlock);
    }

    /**
     * Shows the set of columns for the currently selected Table.
     *
     * @param appReqBlock
     *
     * @return
     */
    private String showAssociatedColumns(AppReqBlock appReqBlock)
    {
        LogTableModel logTableModel = (LogTableModel) TableModel.get(appReqBlock, LogTableModel.class);

        Log log = logTableModel.getLog();

        LogColumnTableModel logColumnTableModel = (LogColumnTableModel) TableModel.get(appReqBlock, LogColumnTableModel.class);

        logColumnTableModel.setLog(log);

        String columnName = appReqBlock.getReqParm("columnName");

        String sequence = appReqBlock.getReqParm("sequence");

        logColumnTableModel.requestEmptyTableRow(columnName, "", "", sequence);

        return showLogging(appReqBlock);
    }

    /**
     * Displays associated log entries for selected log (new logging)
     * @param appReqBlock
     * @return
     */
    protected String showLogEntries(AppReqBlock appReqBlock)
    {
        LogDetailTableModel logDetailTableModel = (LogDetailTableModel) TableModel.get(appReqBlock, LogDetailTableModel.class);

        appReqBlock.putInRequestScope("main", LOG_DETAIL);

        return TEMPLATE_MAIN;
    }

    protected String showGeneralExceptionsLog(AppReqBlock appReqBlock)
    {
        appReqBlock.putInRequestScope("main", GENERAL_EXCEPTIONS_LOG_DISPLAY);

        return TEMPLATE_MAIN;
    }
}

