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

import edit.services.db.hibernate.HibernateEntity;
import edit.services.db.hibernate.SessionHelper;

/**
 * Maintains a running record of all logging history as defined by the Log and LogColumn tables.
 * Information is repeated from LogColumnHistory so that changes to the LogColumn table (e.g. changing a DBColumnName)
 * does not have a propagating effect into the LogColumnHistory.       ****** ????????
 */
public class LogColumnEntry extends HibernateEntity
{
    private LogEntry logEntry;
    private LogColumn logColumn;

    private Long logColumnFK;
    private Long logEntryFK;

    /**
     * A duplication of:
     *
     * @see LogColumn#columnName
     */
    private String columnName;

    /**
     * The message of the associated LogColumn within the associated LogEntry.
     */
    private String logColumnValue;

    /**
     * The unqique identity for this persisted entity.
     */
    private Long logColumnEntryPK;

    /**
     * Target database to be used for lookups, etc.
     */
    public static String DATABASE = SessionHelper.EDITSOLUTIONS;



    public LogColumnEntry()
    {
    }

    private LogColumnEntry(String columnName, String columnValue)
    {
        this.columnName = columnName;
        this.logColumnValue = columnValue;

        // Temporary until callback works.
        onCreate();
    }

    public void setLogColumnFK(Long logColumnFK)
    {
        this.logColumnFK = logColumnFK;
    }

    public Long getLogColumnFK()
    {
        return logColumnFK;
    }

    public void setLogEntryFK(Long logEntryFK)
    {
        this.logEntryFK = logEntryFK;
    }
    public Long getLogEntryFK()
    {
        return logEntryFK;
    }

    public void setLogColumnValue(String logColumnMessage)
    {
        this.logColumnValue = logColumnMessage;
    }

    public String getLogColumnValue()
    {
        return logColumnValue;
    }

    public void setLogColumn(LogColumn logColumn)
    {
        this.logColumn = logColumn;
    }

    public LogColumn getLogColumn()
    {
        return logColumn;
    }

    public void setLogEntry(LogEntry logEntry)
    {
        this.logEntry = logEntry;
    }

    public LogEntry getLogEntry()
    {
        return logEntry;
    }

    public void setColumnName(String columnName)
    {
        this.columnName = columnName;
    }

    public String getColumnName()
    {
        return columnName;
    }

    public void setLogColumnEntryPK(Long logColumnEntryPK)
    {
        this.logColumnEntryPK = logColumnEntryPK;
    }

    public Long getLogColumnEntryPK()
    {
        return logColumnEntryPK;
    }


    public void onCreate()
    {
        // Since we do want to seperate logging from other stuff... do not put log realted objects in underlying (thread) session.
        // SessionHelper.saveOrUpdate(this, LogColumnEntry.DATABASE);
    }

    /**
     * Gets the database this object belongs to
     *
     * @return  string containing the hibernate database name for this object
     */
    public String getDatabase()
    {
        return LogColumnEntry.DATABASE;
    }
}
