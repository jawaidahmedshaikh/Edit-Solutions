/*
 * User: gfrosti
 * Date: May 24, 2006
 * Time: 11:07:36 AM
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
 package logging;

import edit.common.*;

import edit.common.exceptions.EDITRuntimeException;

import edit.services.db.hibernate.HibernateEntity;

import edit.services.db.hibernate.SessionHelper;

import java.util.*;

import staging.*;
import contract.Life;
import engine.ProductStructure;
import engine.Company;
import group.BatchContractSetup;
import group.DepartmentLocation;


/**
 * Represents an instance of a Log's entry (it's creation date/time and the information used to satisfy its associated
 * LogColumns.
 */
public class LogEntry extends HibernateEntity implements IStaging
{
    /**
     * The containing Log.
     */
    private Log log;
    private Long logFK;

    /**
     * @associates <{logging.LogColumnEntry}>
     */
    private Set logColumnEntries;

    /**
     * The point-in-time that this entry was created. It is likely that this field will be the driving force in
     * determining which LogEntries are deleted or archived.
     */
    private EDITDateTime creationDateTime;

    /**
     * This LogEntry's message.
     */
    private String logMessage;

    /**
     * Unique identifier for the persisted entity.
     */
    private Long logEntryPK;

    /**
     * Target database to be used for lookups, etc.
     */
    public static String DATABASE = SessionHelper.EDITSOLUTIONS;



    public LogEntry()
    {
        init();
    }

    /**
     * Convenience constructor that locates the appropriate Log (by log name), and
     * matches the specified name/values to the LogColumns as setup for the parent Log.
     * If the complete set of LogColumns are not identified, then a Runtime exception
     * is thrown. If LogColumns match, then this LogEntry with its associated LogColumnEntry
     * entry is created.
     *
     * @param logName                   name of the Log
     * @param logMessage                message for the Log
     * @param columnNameValues          map of name-value pairs for the columns
     */
    private LogEntry(String logName, String logMessage, EDITMap columnNameValues)
    {
        init();

        onCreate();

        setLogMessage(logMessage);

        createLogEntry(logName, logMessage, columnNameValues);
    }

    /**
     * Initialize the object
     */
    private void init()
    {
        logColumnEntries = new HashSet();

        this.creationDateTime = new EDITDateTime();
    }

    public void setCreationDateTime(EDITDateTime creationDateTime)
    {
        this.creationDateTime = creationDateTime;
    }

    public EDITDateTime getCreationDateTime()
    {
        return creationDateTime;
    }

    public void setLogColumnEntries(Set logElements)
    {
        this.logColumnEntries = logElements;
    }

    public Set getLogColumnEntries()
    {
        return logColumnEntries;
    }

    public void setLog(Log log)
    {
        this.log = log;
    }

    public Log getLog()
    {
        return log;
    }

    public void setLogFK(Long logFK)
    {
        this.logFK = logFK;
    }

    public Long getLogFK()
    {
        return logFK;
    }

    public void setLogEntryPK(Long logEntryPK)
    {
        this.logEntryPK = logEntryPK;
    }

    public Long getLogEntryPK()
    {
        return logEntryPK;
    }

    public void setLogMessage(String logMessage)
    {
        this.logMessage = logMessage;
    }

    public String getLogMessage()
    {
        return logMessage;
    }

    /**
     * Gets the database this object belongs to
     *
     * @return  string containing the hibernate database name for this object
     */
    public String getDatabase()
    {
        return LogEntry.DATABASE;
    }

    public void addLogColumnEntry(LogColumnEntry logColumnEntry)
    {
        getLogColumnEntries().add(logColumnEntry);
        
        logColumnEntry.setLogEntry(this);
    }

    /**
     * Creates a LogEntry with its associated LogColumnEntry entries.
     *
     * @param logName               name of the Log
     * @param logMessage            message for the Log
     * @param nameValuePairs        name-value pairs of the columns
     */
    public void createLogEntry(String logName, String logMessage, EDITMap nameValuePairs)
    {
        setLogMessage(logMessage);
    
        Log log = Log.findBy_LogName_V1(logName);

        setLog(log);

        Object[] names = nameValuePairs.keys();

        for (int i = 0; i < names.length; i++)
        {
            String columnName = (String) names[i];

            LogColumn logColumn = getLogColumn(log, columnName);

            if (logColumn == null)
            {
               // throw new EDITRuntimeException("The Log Entry Does Not Match The Required Log Columns");
            	logColumn = new LogColumn();
            	logColumn.setColumnDescription("Unknown");
            	logColumn.setColumnLabel("Unknown");
            	logColumn.setColumnName("Unknown");
            }
            else
            {
            	String columnValue;
            	if (nameValuePairs.get(columnName) instanceof EDITDate) {
                    EDITDate date = (EDITDate)nameValuePairs.get(columnName);
                    columnValue = date.getFormattedDate();
            	} else {
                    columnValue = (String) nameValuePairs.get(columnName);
            	}

                LogColumnEntry logColumnEntry = new LogColumnEntry();

                logColumnEntry.setColumnName(columnName);
                
                if (columnValue.length() > 4000) {
                	columnValue = columnValue.substring(0, 3999);
                }
                logColumnEntry.setLogColumnValue(columnValue);

                logColumnEntry.setLogColumn(logColumn);
                
                this.addLogColumnEntry(logColumnEntry);
            }
        }
    }

    public void onCreate()
    {
        // Until the callback mechanism is in place, do this manually since we want to use the overloaded constructor(s).
        // Since we do want to seperate logging from other stuff... do not put log realted objects in underlying (thread) session.
        // SessionHelper.saveOrUpdate(this, LogEntry.DATABASE);
    }

    /**
     * Verifies that the log message has the proper LogColumn names required in the Logging setup.
     *
     * @param log
     * @param nameValues
     */
    private void validateLogColumns(Log log, EDITMap nameValues)
    {
        Object[] names = nameValues.keys();

        for (int i = 0; i < names.length; i++)
        {
//            String[] columnInfo = Util.fastTokenizer((String) names[i], ".");
//            String columnName = columnInfo[0];

            String columnName = (String) names[i];

            LogColumn logColumn = getLogColumn(log, columnName);

            if (logColumn == null)
            {
                throw new EDITRuntimeException("The Log Entry Does Not Match The Required Log Columns");
            }
        }
    }

    /**
     * Finds the LogColumn from the specified Log containing the specified columnName.
     *
     * @param log
     * @param columnName
     *
     * @return null if a LogColumn with the specified columnName does not exist
     */
    private LogColumn getLogColumn(Log log, String columnName)
    {
        LogColumn logColumn = null;

        Set logColumns = log.getLogColumns();

        for (Iterator i = logColumns.iterator(); i.hasNext();)
        {
            LogColumn currentLogColumn = (LogColumn) i.next();

            String currentColumnName = currentLogColumn.getColumnName();

            if (currentColumnName.equals(columnName))
            {
                logColumn = currentLogColumn;

                break;
            }
        }

        return logColumn;
    }

    /**
     * Find by PK
     *
     * @param logEntryPK
     *
     * @return
     */
    public static LogEntry findByPK(Long logEntryPK)
    {
        return (LogEntry) SessionHelper.get(LogEntry.class, logEntryPK, LogEntry.DATABASE);
    }

    /**
     * Finds all the LogEntrys for a given logFK.  The results are sorted by creation date/time in descending order.
     * The results are a composite of LogEntry.LogColumnEntry.LogColumn.
     * 
     * @param logFK
     * @param page the starting page from which to read the results
     * @param pageSize the number of records to display within a page
     *
     * @return
     */
    public static LogEntry[] findBy_LogFK_V1(Long logFK, int page, int pageSize)
    {
        String hql = " select logEntry " + 
                " from LogEntry logEntry" +
               // " join fetch logEntry.LogColumnEntries logColumnEntry" +
               // " join fetch logColumnEntry.LogColumn" +
                " where logEntry.LogFK = :logFK" +
                " order by logEntry.CreationDateTime desc";

        EDITMap params = new EDITMap("logFK", logFK);

        List results = SessionHelper.makeUnique(SessionHelper.executeHQLPage(hql, params, LogEntry.DATABASE, page, pageSize));

        return (LogEntry[]) results.toArray(new LogEntry[results.size()]);
    }
    
    /**
     * Finds all the LogEntrys for a given logFK.  The results are sorted by creation date/time in descending order.
     *
     * @param logFK
     *
     * @return
     */
    public static LogEntry[] findBy_LogFK(Long logFK)
    {
        String hql = "select logEntry from LogEntry logEntry" +
                " where logEntry.LogFK = :logFK" +
                " order by logEntry.CreationDateTime desc";

        EDITMap params = new EDITMap("logFK", logFK);

        List results = SessionHelper.executeHQL(hql, params, LogEntry.DATABASE);

        return (LogEntry[]) results.toArray(new LogEntry[results.size()]);
    }    

    /**
     * Finds all the LogEntrys for a given contractNumber, greater than or equal to a certain CreationDateTime.  
     * The results are sorted by creation date/time.
     *
     * @param logName, contractNumber, dateTime 
     *
     * @return
     */
    public static LogEntry[] findBy_LogNameContractNumberDateTime(String logName, String contractNumber, EDITDateTime dateTime)
    {
        String hql = "select logEntry from LogEntry logEntry" +
        		" join logEntry.LogColumnEntries logColumnEntry" +
                " join logColumnEntry.LogColumn logColumn" +
                " join logEntry.Log log" +
                " where log.LogName = :logName" +
                " and logColumnEntry.LogColumnValue = :contractNumber" +
                " and logEntry.CreationDateTime >= :dateTime" +
                " order by logEntry.CreationDateTime, logEntry.LogEntryPK";

        EDITMap params = new EDITMap("logName", logName);
        params.put("contractNumber", contractNumber);
        params.put("dateTime", dateTime);

        List results = SessionHelper.executeHQL(hql, params, LogEntry.DATABASE);

        return (LogEntry[]) results.toArray(new LogEntry[results.size()]);
    }

    /**
     * Finds the latest CreationDateTime for any LogEntry that has a LogFK of the given logFK
     *
     * @param logFK                     the logFK for which to find the latest CreationDateTime
     *
     * @return the latest CreationDateTime
     */
    public static EDITDateTime findLatestCreationDateTime_ByLogFK(Long logFK)
    {
        EDITDateTime latestCreationDateTime = null;

        String hql = "select max(logEntry.CreationDateTime) from LogEntry logEntry" +
                " where logEntry.LogFK = :logFK";

        EDITMap params = new EDITMap("logFK", logFK);

        List results = SessionHelper.executeHQL(hql, params, LogEntry.DATABASE);

        if (results.get(0) != null)
        {
            latestCreationDateTime = (EDITDateTime) results.get(0);
        }

        return latestCreationDateTime;
    }

    /**
     * Finds all the LATEST Log Entries for the specified contract
     *
     * @param contractNumber
     *
     * @return LogEntry[]
     */
    public static LogEntry[] findLatestCreationDateTime_ByContractNumber(String contractNumber)
    {
        EDITDateTime latestCreationDateTime = null;

        String hqla = "select max(logEntry.CreationDateTime) from LogEntry logEntry" +
                      " where logEntry.LogEntryPK in (select LogEntryFK from LogColumnEntry logColumnEntry" +
                      " where logColumnEntry.LogColumnValue = :contractNumber)";

        EDITMap paramsA = new EDITMap("contractNumber", contractNumber);

        List resultsA = SessionHelper.executeHQL(hqla, paramsA, LogEntry.DATABASE);

        if (resultsA.get(0) != null)
        {
            latestCreationDateTime = (EDITDateTime) resultsA.get(0);
        }

        if (latestCreationDateTime != null)
        {
            EDITDate latestCreationDate = latestCreationDateTime.getEDITDate();

            latestCreationDateTime = new EDITDateTime(latestCreationDate, EDITDateTime.DEFAULT_MIN_TIME);

            String hql = "select logEntry from LogEntry logEntry" +
                         " where logEntry.LogEntryPK in (select LogEntryFK from LogColumnEntry logColumnEntry" +
                         " where logColumnEntry.LogColumnValue = :contractNumber)" +
                         " and logEntry.CreationDateTime >= :creationDateTime";

            EDITMap params = new EDITMap("contractNumber", contractNumber);
            params.put("creationDateTime", latestCreationDateTime);

            List results = SessionHelper.executeHQL(hql, params, LogEntry.DATABASE);

            return (LogEntry[]) results.toArray(new LogEntry[results.size()]);
        }
        else
        {
            return new LogEntry[0];
        }
    }

    public StagingContext stage(StagingContext stagingContext, String database)
    {
        ErrorLog errorLog = new ErrorLog();
        errorLog.setSegmentBase(stagingContext.getCurrentSegmentBase());
        errorLog.setCreationDateTime(this.getCreationDateTime());
        errorLog.setLogMessage(this.getLogMessage());

        Set<LogColumnEntry> logColumnEntries = this.getLogColumnEntries();
        Iterator it = logColumnEntries.iterator();
        while (it.hasNext())
        {
            LogColumnEntry logColumnEntry = (LogColumnEntry) it.next();
            LogColumn logColumn = logColumnEntry.getLogColumn();
            if (logColumn.getColumnName().equalsIgnoreCase(LogColumn.COLUMNNAME_SEVERITY))
            {
                errorLog.setSeverity(logColumnEntry.getLogColumnValue());
            }
        }

        stagingContext.getCurrentSegmentBase().addErrorLog(errorLog);

        SessionHelper.saveOrUpdate(errorLog, database);

        return stagingContext;
    }
}