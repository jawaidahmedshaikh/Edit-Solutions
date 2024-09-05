/*
 * User: sprasad
 * Date: Jun 7, 2006
 * Time: 1:17:27 PM
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package edit.portal.widget;

import edit.portal.widgettoolkit.TableRow;
import edit.common.*;
import logging.Log;

/**
 * Represents each row in LogIndexTableModel
 */
public class LogIndexTableRow extends TableRow
{
    private Log log;

    /**
     * Constructor.
     * @param log
     */
    public LogIndexTableRow(Log log)
    {
        this.log = log;

        populateCellValues();
    }

    /**
     * Maps column values to column names.
     */
    private void populateCellValues()
    {
        String logNameAsLink = "<a href=\"javascript:showLogDetail('" + getRowId() + "')\">" + this.log.getLogName() + "</a>";

        super.getCellValues().put(LogIndexTableModel.COLUMN_LOG_NAME, logNameAsLink);

        EDITDateTime latestCreationDateTime = this.log.getLatestCreationDateTime();

        if (latestCreationDateTime != null)
        {
            super.getCellValues().put(LogIndexTableModel.COLUMN_LAST_MODIFIED, latestCreationDateTime.getFormattedDateTime());
        }
        else
        {
            super.getCellValues().put(LogIndexTableModel.COLUMN_LAST_MODIFIED, "");
        }

        super.getCellValues().put(LogIndexTableModel.COLUMN_ACTIVE, this.log.getActive());

        super.getCellValues().put(LogIndexTableModel.COLUMN_LOG_DESCRIPTION, this.log.getLogDescription());
    }

    /**
     * Returns the Id of the table row.
     * @return
     */
    public String getRowId()
    {
        return this.log.getLogPK().toString();
    }
}