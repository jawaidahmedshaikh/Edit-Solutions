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

import edit.common.EDITDateTime;
import edit.common.EDITMap;
import edit.common.exceptions.EDITDeleteException;
import edit.common.exceptions.EDITException;
import edit.common.exceptions.EDITSaveException;

import edit.services.db.hibernate.HibernateEntity;
import edit.services.db.hibernate.SessionHelper;

import fission.utility.Util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.HibernateException;


/**
 * Defines an instance of Log. With this Log, a set of LogColumns will be associated which define which parameters
 * are to be logged. Once defined (A Log and its associated LogColumns), a running history will be maintained
 * for any business exceptions that target this Log.
 */
public class Log extends HibernateEntity
{
	private static final long serialVersionUID = 1L;
	public static final String ACTIVE_YES = "Y";
    public static final String ACTIVE_NO = "N";

    public static final String VALIDATE_UNSPECIFIED = "VALIDATE.UNSPECIFIED";

    public static final String EXECUTE_SCRIPT = "EXECUTE.SCRIPT";
    public static final String EXECUTE_TRANSACTION = "EXECUTE.TRANSACTION";
    public static final String DB_RECORD_DELETION = "DB.RECORD.DELETION";
    public static final String GENERAL_EXCEPTION = "GENERAL.EXCEPTION";
    public static final String ACCOUNTING = "ACCOUNTING";
    public static final String BANK = "BANK";
    public static final String CORRESPONDENCE = "CORRESPONDENCE";
    public static final String EQUITY_INDEX_HEDGE = "EQUITY.INDEX.HEDGE";
    public static final String PREMIUM_EXTRACT_RESERVES_FILE = "PREMIUM.EXTRACT.RESERVES.FILE";
    public static final String AGENT_UPDATE = "AGENT.UPDATE";
    public static final String BUILD_AGENT_CHECK_CK = "BUILD.AGENT.CHECK.CK";
    public static final String BUILD_AGENT_EFT_CK = "BUILD.AGENT.EFT.CK";
    public static final String RUN_AGENT_CK = "RUN.AGENT.CK";
    public static final String COMMISSION_STATEMENTS = "COMMISSION.STATEMENTS";
    public static final String YEAR_END_TAX_REPORTING = "YEAR.END.TAX.REPORTING";
    public static final String REINSURANCE_UPDATE = "REINSURANCE.UPDATE";
    public static final String BUILD_REINSURANCE_CK = "BUILD.REINSURANCE.CK";
    public static final String UPDATE_AGENT_BONUSES = "UPDATE.AGENT.BONUSES";
    public static final String RMD_NOTIFICATIONS = "RMD.NOTIFICATIONS";
    public static final String RUN_AGENT_BONUS_CHECKS = "RUN.AGENT.BONUS.CHECKS";
    public static final String BONUS_COMMISSION_STATEMENTS = "BONUS.COMMISSION.STATEMENTS";
    public static final String BILLING = "BILLING";
    public static final String PRD_Extract ="PRD.EXTRACT";
    public static final String WORKSHEET ="WORKSHEET";
    public static final String ALPHA_EXPORT ="ALPHA.EXPORT";
    public static final String PENDING_REQUIREMENTS ="PENDING.REQUIREMENTS";
    public static final String DATAWAREHOUSE ="DATAWAREHOUSE";
    public static final String NEW_BUSINESS_IMPORT ="NEWBUSINESS.IMPORT";
    public static final String CASH_BATCH = "CASH.BATCH";
    public static final String PRD_COMPARE = "PRD.BATCH";
    public static final String MAINTENANCE_COMMISSION_PROFILE = "MAINTENANCE.COMMISSION.PROFILE";

    //  The following logs get created in ScriptProcessorImpl.  The scripts pass back the log name, error message,
    //  and list of name-value pairs to be logged.
//    public static final String VALIDATE_CLIENT_SAVE = "VALIDATE.CLIENT.SAVE";
    public static final String VALIDATE_TRANSACTION_SAVE = "VALIDATE.TRANSACTION.SAVE";
    public static final String VALIDATE_CONTRACT_SAVE = "VALIDATE.CONTRACT.SAVE";
//    public static final String VALIDATE_AGENT_SAVE = "VALIDATE.AGENT.SAVE";

    //  The following logs are expected to go away.
//    public static final String OFAC = "OFAC";
//    public static final String BATCH_JOB = "BATCH.JOB";


    private static Map<String, Boolean> activeLogs = new HashMap<>();

    /**
     * @associates <{logging.LogColumn}>
     */
    private Set<LogColumn> logColumns;

    /**
     * A fixed alias for this Log. This alias, once defined, should never be changed
     * since developers will be dependent on this alias when specifying which log
     * to target.
     */
    private String logName;

    /**
     * An updateable description of this Log.
     */
    private String logDescription;

    /**
     * The unique identifier for this persisted entity.
     */
    private Long logPK;

    /**
     * True if this Log is active.
     */
    private String active;

    /**
     * @associates <{logging.LogEntry}>
     */
    private Set<LogEntry> logEntries;

    /**
     * Target database to be used for lookups, etc.
     */
    public static String DATABASE = SessionHelper.EDITSOLUTIONS;



    public Log()
    {
    }

    public void setLogColumns(Set<LogColumn> logColumns)
    {
        this.logColumns = logColumns;
    }

    public Set<LogColumn> getLogColumns()
    {
        return logColumns;
    }

    public void setLogEntries(Set<LogEntry> logEntries)
    {
        this.logEntries = logEntries;
    }

    public Set<LogEntry> getLogEntries()
    {
        return logEntries;
    }

    public void setLogName(String name)
    {
        this.logName = name.toUpperCase();
    }

    public String getLogName()
    {
        return logName;
    }

    public void setLogDescription(String logDescription)
    {
        this.logDescription = logDescription;
    }

    public String getLogDescription()
    {
        return logDescription;
    }

    public void setLogPK(Long logPK)
    {
        this.logPK = logPK;
    }

    public Long getLogPK()
    {
        return logPK;
    }

    /**
     * Returns all logs in the Log table - initially sorted by LogName.
     *
     * @return
     */
    public static Log[] findAll_V1()
    {
        String hql = " from Log log" + " order by log.LogName asc";

        List<Log> results = SessionHelper.executeHQL(hql, null, Log.DATABASE);

        return (Log[]) results.toArray(new Log[results.size()]);
    }

    public void setActive(String active)
    {
        this.active = active.toUpperCase();
    }

    public String getActive()
    {
        return active;
    }

    /**
     * Determines if the log is active or not.  This is a static method that looks up the log based on the specified
     * log name.
     *
     * @param logName               name of the log to check for active status
     *
     * @return true if the specified log is active, false otherwise
     */
    public static boolean isActive(String logName)
    {
        Boolean isActive = null;
        
        isActive = (Boolean) activeLogs.get(logName);
        
        if (isActive == null)
        {
          Log log = Log.findBy_LogName_V2(logName);

          if (log != null)
          {
              if (log.getActive().equals(Log.ACTIVE_YES))
              {
                  isActive = true;
              }
              else
              {
                isActive = false;
              }
              
              // update the cache
              activeLogs.put(logName, isActive);
          }
          else 
          {
              isActive = false;
              
              activeLogs.put(logName, isActive);
          }
        }

        return isActive;
    }

    /**
     * Convenience method to return the latest CreationDateTime for this log
     *
     * @return the latest CreationDateTime for this log
     */
    public EDITDateTime getLatestCreationDateTime()
    {
        return LogEntry.findLatestCreationDateTime_ByLogFK(this.getLogPK());
    }

    /**
     * Basic validations before saving this entity.
     */
    public void onSave() throws EDITSaveException
    {
        new LogValidator().validate();
        
        Log.activeLogs.remove(getLogName());
    }

    /**
     * Finder. Includes the child LogColumns as part of a single request for
     * performance reasons.
     *
     * @param logName
     *
     * @return
     */
    public static Log findBy_LogName_V1(String logName)
    {
        Log log = null;

        String hql = " from Log log" +
                " join fetch log.LogColumns logColumn" +
                " where log.LogName = :logName";

        EDITMap params = new EDITMap("logName", logName);

        List<Log> results = SessionHelper.executeHQL(hql, params, Log.DATABASE);

        if (!results.isEmpty())
        {
            log = (Log) results.get(0);
        }

        return log;
    }

    /**
     * Finder.
     *
     * @param logName
     *
     * @return
     */
    public static Log findBy_LogName_V2(String logName) {
        Log log = null;

        String hql = " from Log log" + " where log.LogName = :logName";

        Map<String, String> params = new HashMap<>();

        params.put("logName", logName);

        List<Log> results = SessionHelper.executeHQL(hql, params, Log.DATABASE);

        if (!results.isEmpty())
        {
            log = (Log) results.get(0);
        }

        return log;
    }

    /**
     * Finder.
     * Gets the Log entity (only).
     * @param logPK
     *
     * @return
     */
    public static Log findByPK_V1(Long logPK)
    {
        return (Log) SessionHelper.get(Log.class, logPK, Log.DATABASE);
    }
    
  /**
   * Finder for the Log and join/fetches the LogEntry, LogColumnEntry, and LogColumn
   * in this single call. This could get quite large.
   * If there are no LogEntries for the specified Log, then only the Log itself
   * is returned.
   * @param logPK
   * @return
   */
    public static Log findByPK_V2(Long logPK)
    {
      Log log = null;
    
      String hql = "select log from Log log" +
                  " join log.LogEntries logEntry" +
                  " join logEntry.LogColumnEntries logColumnEntry" +
                  " join logColumnEntry.LogColumn" +
                  " where log.LogPK = :logPK";
                  
      EDITMap params = new EDITMap().put("logPK", logPK);                  
                  
      List<Log> results = SessionHelper.makeUnique(SessionHelper.executeHQL(hql, params, Log.DATABASE));
      
      if (!results.isEmpty())
      {
        log = (Log) results.get(0); 
      }
      else
      {
        log = findByPK_V1(logPK);
      }
      
      return log;
    }

    /**
     * Delete this entity and the associated children.
     */
    public void hDelete() throws EDITDeleteException
    {
        SessionHelper.delete(this, Log.DATABASE);
    }

    /**
     * Gets the database this object belongs to
     *
     * @return  string containing the hibernate database name for this object
     */
    public String getDatabase()
    {
        return Log.DATABASE;
    }

    /**
     * Logs the information to the database
     *
     * @param logName               name of the log
     * @param message               message to be stored in the log
     * @param columnInfo            map of column names and values to be logged
     */
    public static void logToDatabase(String logName, String message, EDITMap columnInfo) {
    	// max length of message is 4000.  check length to avoid db truncation error.
    	if (message == null) {
    		message = "";
    		//System.out.println("####################  message is null");
    	} else if (message.length() > 3000) {
    		message = message.substring(0,2999);
    		//System.out.println("####################  message larger than 3000.");
    	}

        if (Log.isActive(logName))
        {
            Session session = null;

            Transaction transaction = null;

            try
            {
                session = SessionHelper.getSeparateSession(Log.DATABASE);

                LogEntry logEntry = new LogEntry();
                if (message.length() >= 4000) {
                    message = message.substring(0, 3999);
                }

                logEntry.createLogEntry(logName, message, columnInfo);

                transaction = session.beginTransaction();

                session.saveOrUpdate(logEntry);

                transaction.commit();
            }
            catch (HibernateException e)
            {
                if (transaction != null)
                {
                    transaction.rollback();
                }

                System.out.println("logName: " + logName);
                System.out.println("message length: " + message.length());
                System.out.println(e);

                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.

                // Intentionally did not throw neither RuntimeException nor Checked exception.
            }
            finally
            {
                if (session != null) session.close();
            }
        }
    }

    /**
     * Convenience method to log a general exception.  Items going into the GeneralException log occur many times
     * throughout the system.  It was more convenient to have a method in this class.
     * <P>
     * The stack trace can be long and exceed the 4000 character limit in the database.  As a result, the stack trace
     * is trimmed before being saved.
     *
     * @param e                 exception to be logged
     */
    public static void logGeneralExceptionToDatabase(String message, Throwable throwableObject)
    {
        String stackTrace = Util.truncatateString(EDITException.getStackTrace(throwableObject), 4000);

        EDITMap columnInfo = new EDITMap("StackTrace", message + ": " + stackTrace);

        if (message == null)
        {
            message = throwableObject.getMessage();
        }

        Log.logToDatabase(Log.GENERAL_EXCEPTION, message, columnInfo);
    }

    /**
     * Determines if this log is the General Exception log or not
     *
     * @return  true if this log is the General Exception log, false otherwise
     */
    public boolean isGeneralExceptionLog()
    {
        if (this.getLogName().equals(Log.GENERAL_EXCEPTION))
        {
            return true;
        }
        else
        {
            return false;
        }
    }


    // ================================= CLASS LogValidator ===================================
    /**
     * Validates the containing Log before saving.
     */
    private class LogValidator
    {
        private void validate() throws EDITSaveException
        {
            EDITSaveException e = new EDITSaveException();

            validateLogNameUnique(e);

            validateRequiredFields(e);

            validateFieldValues(e);

            if (!e.getMessageList().isEmpty())
                throw e;
        }

        /**
         * The following are required:
         *
         * @param e
         */
        private void validateRequiredFields(EDITSaveException e) throws EDITSaveException
        {
            if (getLogName() == null)
                e.getMessageList().addTo("The field [LogName] is required.");

            if (getLogDescription() == null)
                e.getMessageList().addTo("The field [LogDescription] is required.");

            if (getActive() == null)
                e.getMessageList().addTo("The field [Active] is required.");
        }

        /**
         * The field 'Active' must be either 'Y' or 'N'.
         *
         * @param e
         */
        private void validateFieldValues(EDITSaveException e) throws EDITSaveException
        {
            String active = Util.initString(getActive(), null);

            if (active != null)
            {
                active = active.toUpperCase();

                if (active.equals(Log.ACTIVE_NO) || active.equals(Log.ACTIVE_YES))
                {
                    setActive(active); // make sure it's upper case
                }
                else
                {
                    e.getMessageList().addTo("The field [Active] must be Y/N.");
                }
            }
        }

        /**
         * Duplicate LogNames are not allowed.
         *
         * @param e
         */
        private void validateLogNameUnique(EDITSaveException e) throws EDITSaveException
        {
            Log oldLog = Log.findBy_LogName_V2(getLogName());

            if (oldLog != null) // if it's null, then the name has not been used
            {
                Long thisLogPK = getLogPK();

                Long oldLogPK = oldLog.getLogPK();

                if (!thisLogPK.equals(oldLogPK))
                {
                    String thisLogName = getLogName().toUpperCase();

                    String oldLogName = oldLog.getLogName().toUpperCase();

                    if (thisLogName.equals(oldLogName))
                    {
                        e.getMessageList().addTo("LogNames must be unique.");
                    }
                }
            }
        }
    }
    // ========================== END CLASS LogValidator =====================================================
}
