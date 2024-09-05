/*
 * User: sprasad
 * Date: Jun 8, 2006
 * Time: 12:15:34 PM
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package edit.portal.widget;

import edit.portal.widgettoolkit.TableRow;
import logging.LogEntry;
import logging.LogColumnEntry;

import java.util.Set;
import java.util.Iterator;

public class LogDetailTableRow extends TableRow
{
    private LogEntry logEntry;

    public LogDetailTableRow(LogEntry logEntry)
    {
        this.logEntry = logEntry;

        populateCellValues();
    }

    public String getRowId()
    {
        return this.logEntry.getLogEntryPK().toString();
    }

    private void populateCellValues()
    {
        super.getCellValues().put(LogDetailTableModel.COLUMN_CREATION_DATE_TIME, logEntry.getCreationDateTime());

        super.getCellValues().put(LogDetailTableModel.COLUMN_LOG_MESSAGE, logEntry.getLogMessage());

        Set logColumnEntries = logEntry.getLogColumnEntries();

        for (Iterator iterator = logColumnEntries.iterator(); iterator.hasNext();)
        {
            LogColumnEntry logColumnEntry = (LogColumnEntry) iterator.next();

            String logColumnLabel  = (logColumnEntry.getLogColumn()).getColumnLabel();

            super.getCellValues().put(logColumnLabel, logColumnEntry.getLogColumnValue());
        }
    }
}
