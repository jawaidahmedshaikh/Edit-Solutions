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

import edit.common.exceptions.EDITDeleteException;
import edit.common.exceptions.EDITSaveException;

import edit.services.db.hibernate.HibernateEntity;
import edit.services.db.hibernate.SessionHelper;

import fission.utility.Util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * Detailed information for each column that will be logged.
 * It is expected that a Business Analyst will define the columns that they wished to be logged.
 */
public class LogColumn extends HibernateEntity
{
    /**
     * The containing Log.
     */
    private Log log;

    /**
     * @associates <{logging.LogColumnEntry}>
     */
    private Set logColumnEntries;

    /**
     * The name of the column that will be logged.  This name should have no spaces and is not directly related to
     * a database column.
     */
    private String columnName;

    /**
     * The label for the column that will be displayed to the user.
     */
    private String columnLabel;

    /**
     * The description of the column name.
     */
    private String columnDescription;

    /**
     * Identifies the order of this LogColumn in the set of LogColumns associated with the parent Log.
     */
    private int sequence;

    /**
     * The unique identifier for this persisted entity.
     */
    private Long logColumnPK;

    /**
     * Column which does not relate to any available table.
     */
    public static final String SPECIAL_COLUMN_NONE = "NONE";

    public static final String SPECIAL_COLUMN_OPERATOR_DATE_TIME = "OPERATOR";

    public static final String SPECIAL_COLUMN_PROCESS_DATE = "PROCESS DATE";

    public static final String SPECIAL_COLUMN_PROCESS_DATE_TIME = "PROCESS DATE TIME";

    public static final String SPECIAL_COLUMN_SEVERITY = "SEVERITY";

    public static final String SPECIAL_COLUMN_TAX_REPORT_NUMBER = "REPORT NUMBER";

    public static final String[] SPECIAL_COLUMNS = {SPECIAL_COLUMN_NONE, SPECIAL_COLUMN_OPERATOR_DATE_TIME, SPECIAL_COLUMN_PROCESS_DATE, SPECIAL_COLUMN_SEVERITY, SPECIAL_COLUMN_TAX_REPORT_NUMBER};

    public static final String COLUMNNAME_SEVERITY = "Severity";

    /**
     * Target database to be used for lookups, etc.
     */
    public static String DATABASE = SessionHelper.EDITSOLUTIONS;


    public LogColumn()
    {
    }

    public void setLogColumnEntries(Set logElements)
    {
        this.logColumnEntries = logElements;
    }

    public Set getLogColumnEntries()
    {
        return logColumnEntries;
    }

    public void setSequence(int sequence)
    {
        this.sequence = sequence;
    }

    public int getSequence()
    {
        return sequence;
    }

    public void setLog(Log log)
    {
        this.log = log;
    }

    public Log getLog()
    {
        return log;
    }

    public void setColumnName(String columnName)
    {
        this.columnName = columnName;
    }

    public String getColumnName()
    {
        return columnName;
    }

    public void setColumnLabel(String columnLabel)
    {
        this.columnLabel = columnLabel;
    }

    public String getColumnLabel()
    {
        return columnLabel;
    }

    public void setColumnDescription(String columnDescription)
    {
        this.columnDescription = columnDescription;
    }

    public String getColumnDescription()
    {
        return columnDescription;
    }

    public void setLogColumnPK(Long logColumnPK)
    {
        this.logColumnPK = logColumnPK;
    }

    public Long getLogColumnPK()
    {
        return logColumnPK;
    }

    /**
     * Gets the database this object belongs to
     *
     * @return  string containing the hibernate database name for this object
     */
    public String getDatabase()
    {
        return LogColumn.DATABASE;
    }

    /**
     * Finds all the LogColumns associated with the Log specified by its name
     *
     * @param logName               name field of the Log table
     *
     * @return array of LogColumn objects
     */
    public static LogColumn[] findBy_LogName(String logName)
    {
        String hql = "select logColumn from LogColumn logColumn" +
                " join logColumn.Log log" +
                " where log.LogName = :logName";

        Map params = new HashMap();

        params.put("logName", logName);

        List results = SessionHelper.executeHQL(hql, params, LogColumn.DATABASE);

        return (LogColumn[]) results.toArray(new LogColumn[results.size()]);
    }

    /**
     * LogColumns are sorted ascending by Sequence.
     *
     * @param log
     */
    public static LogColumn[] findBy_Log_V1(Log log)
    {
        String hql = " from LogColumn logColumn" +
                " where logColumn.Log = :log" +
                " order by logColumn.Sequence asc";

        Map params = new HashMap();

        params.put("log", log);

        List results = SessionHelper.executeHQL(hql, params, LogColumn.DATABASE);

        return (LogColumn[]) results.toArray(new LogColumn[results.size()]);
    }


    public void onSave() throws EDITSaveException
    {
        new LogColumnValidator().validate();
    }

    /**
     * Finder.
     *
     * @param log
     * @param sequence
     *
     * @return
     */
    public static LogColumn findBy_Log_Sequence(Log log, int sequence)
    {
        LogColumn logColumn = null;

        String hql = " from LogColumn logColumn" +
                " where logColumn.Log = :log" +
                " and logColumn.Sequence = :sequence";

        Map params = new HashMap();

        params.put("log", log);

        params.put("sequence", new Integer(sequence));

        List results = SessionHelper.executeHQL(hql, params, LogColumn.DATABASE);

        if (!results.isEmpty())
        {
            logColumn = (LogColumn) results.get(0);
        }

        return logColumn;
    }

    /**
     * Finder.
     *
     * @param logColumnPK
     *
     * @return
     */
    public static LogColumn findBy_LogColumnPK(Long logColumnPK)
    {
        LogColumn logColumn = null;

        String hql = " from LogColumn logColumn" +
                " where logColumn.LogColumnPK = :logColumnPK";

        Map params = new HashMap();

        params.put("logColumnPK", logColumnPK);

        List results = SessionHelper.executeHQL(hql, params, LogColumn.DATABASE);

        if (!results.isEmpty())
        {
            logColumn = (LogColumn) results.get(0);
        }

        return logColumn;
    }

    /**
     * Dissaciates itself from its parent Log and then deletes.
     *
     * @throws EDITDeleteException
     */
    public void hDelete() throws EDITDeleteException
    {
        getLog().getLogColumns().remove(this);

        setLog(null);

        SessionHelper.delete(this, LogColumn.DATABASE);
    }

    // ======================= START CLASS LogColumnValidator =======================================================

    /**
     * Static business rules for this LogColumn entity.
     */
    private class LogColumnValidator
    {
        public void validate() throws EDITSaveException
        {
            EDITSaveException e = new EDITSaveException();

            validateSequenceUnique(e);

            validateRequiredFields(e);

            validateFieldFormat(e);

            if (!e.getMessageList().isEmpty())
            {
                throw e;
            }
        }

        /**
         * ColumnName, ColumnLabel, and Sequence are required.
         *
         * @param e
         */
        private void validateRequiredFields(EDITSaveException e)
        {
            if (getColumnName() == null)
            {
                e.getMessageList().addTo("The field [ColumnName] is required.");
            }

            if (getColumnLabel() == null)
            {
                e.getMessageList().addTo("The field [ColumnLabel] is required.");
            }
        }

        /**
         * Validates the format of the fields.
         *
         * ColumnName cannot have any spaces
         *
         * @param e
         */
        private void validateFieldFormat(EDITSaveException e)
        {
            if (Util.hasSpace(getColumnName()))
            {
                e.getMessageList().addTo("The field [ColumnName] cannot contain spaces");
            }
        }

        /**
         * The sequence must be unique within the set of LogColumns for a Log.
         *
         * @param e
         */
        private void validateSequenceUnique(EDITSaveException e)
        {
            LogColumn logColumn = LogColumn.findBy_Log_Sequence(getLog(), getSequence());

            if (logColumn != null && !logColumn.getLogColumnPK().equals(getLogColumnPK()))
            {
                e.getMessageList().addTo("The field [Sequence] must be unique with the set of LogColumns for this Log.");
            }
        }
    }

    // ======================= END CLASS LogColumnValidator =======================================================
}
