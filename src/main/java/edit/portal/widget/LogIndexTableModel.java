/*
 * User: sprasad
 * Date: Jun 7, 2006
 * Time: 10:34:49 AM
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package edit.portal.widget;

import edit.portal.widgettoolkit.TableModel;
import edit.portal.widgettoolkit.TableRow;
import fission.global.AppReqBlock;
import logging.Log;

import java.math.BigDecimal;

/**
 * Front-end helper class to display logIndex.jsp page.
 */
public class LogIndexTableModel extends TableModel
{
    public static final String COLUMN_LOG_NAME = "Name";

    public static final String COLUMN_LAST_MODIFIED = "Last Modified";

    public static final String COLUMN_ACTIVE = "Active";

    public static final String COLUMN_LOG_DESCRIPTION = "Description";


    /**
    * Constructor.
    * @param appReqBlock
    */
    public LogIndexTableModel(AppReqBlock appReqBlock)
    {
        super(appReqBlock);

        setColumnNames();

        setColumnWidths();

        setColumnRenderings();
    }

    /**
     * Builds the table rows for this table model.
     * @see edit.portal.widgettoolkit.TableModel#buildTableRows()
     */
    protected void buildTableRows()
    {
        Log[] logs = Log.findAll_V1();

        for (int i = 0; i < logs.length; i++)
        {
            Log log = logs[i];

            if (! log.isGeneralExceptionLog())      // don't display General Exception logs with the other logs
            {
                TableRow logIndexTableRow = new LogIndexTableRow(log);

                super.getRows().add(logIndexTableRow);
            }
        }
    }

    /**
     * Sets Columns names for this table model.
     */
    private void setColumnNames()
    {
        super.getColumnNames().add(LogIndexTableModel.COLUMN_LOG_NAME);

        super.getColumnNames().add(LogIndexTableModel.COLUMN_LAST_MODIFIED);

        super.getColumnNames().add(LogIndexTableModel.COLUMN_ACTIVE);

        super.getColumnNames().add(LogIndexTableModel.COLUMN_LOG_DESCRIPTION);
    }

    /**
     * Sets column widths to be allocated.
     */
    private void setColumnWidths()
    {
        //  Based on a width of 110% specified in the jsp
        super.getCellWidths().put(LogIndexTableModel.COLUMN_LOG_NAME, new BigDecimal(30));

        super.getCellWidths().put(LogIndexTableModel.COLUMN_LAST_MODIFIED, new BigDecimal(25));

        super.getCellWidths().put(LogIndexTableModel.COLUMN_ACTIVE, new BigDecimal(15));

        super.getCellWidths().put(LogIndexTableModel.COLUMN_LOG_DESCRIPTION, new BigDecimal(40));
    }

    /**
     * Sets how each column should be displayed.
     */
    private void setColumnRenderings()
    {
        super.getCellRenderings().put(LogIndexTableModel.COLUMN_LOG_NAME, TableModel.RENDER_CELL_AS_TEXT);

        super.getCellRenderings().put(LogIndexTableModel.COLUMN_LAST_MODIFIED, TableModel.RENDER_CELL_AS_TEXT);

        super.getCellRenderings().put(LogIndexTableModel.COLUMN_ACTIVE, TableModel.RENDER_CELL_AS_TEXT);

        super.getCellRenderings().put(LogIndexTableModel.COLUMN_LOG_DESCRIPTION, TableModel.RENDER_CELL_AS_TEXT);
    }
}
