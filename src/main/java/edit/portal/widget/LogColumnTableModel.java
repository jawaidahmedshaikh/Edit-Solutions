/*
 * User: gfrosti
 * Date: May 24, 2006
 * Time: 12:45:19 PM
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package edit.portal.widget;

import edit.portal.widgettoolkit.TableModel;

import edit.portal.widgettoolkit.TableRow;

import fission.global.AppReqBlock;

import java.math.BigDecimal;

import logging.Log;

import logging.LogColumn;

public class LogColumnTableModel extends TableModel
{
    public static final String COLUMN_COLUMNNAME = "Column Name";

    public static final String COLUMN_COLUMNLABEL = "Column Label";

    public static final String COLUMN_COLUMNDESCRIPTION = "Column Description";

    public static final String COLUMN_SEQUENCE = "Sequence";

    /**
     * The associated Log.
     */
    private Log log;

    /**
     * The default TableRow to use when adding a new LogColumnTableRow.
     */
    private LogColumnTableRow emptyTableRow;

    private LogColumnTableModel(AppReqBlock appReqBlock)
    {
        super(appReqBlock);

        populateColumnNames();

        populateColumnWidths();

        populateColumnRenderings();

        setEditable(true);
    }

    /**
     * Builds TableRow entries for every Log found in the Log table.
     */
    protected void buildTableRows()
    {
        if (log != null)
        {
            LogColumn[] logColumns = LogColumn.findBy_Log_V1(log);

            String selectedRowId = getSelectedRowId();

            for (int i = 0; i < logColumns.length; i++)
            {
                LogColumn logColumn = logColumns[i];

                LogColumnTableRow logColumnTableRow = new LogColumnTableRow(logColumn);

                if (logColumn.getLogColumnPK().toString().equals(selectedRowId))
                {
                    logColumnTableRow.setRowStatus(TableRow.ROW_STATUS_SELECTED);
                }

                logColumnTableRow.populateCellValues();

                super.getRows().add(logColumnTableRow);
            }
        }
    }

    /**
     * The set of columns for the TableModel:
     * Log Name
     * Log Description
     * Active
     */
    private void populateColumnNames()
    {
        super.getColumnNames().add(COLUMN_COLUMNNAME);

        super.getColumnNames().add(COLUMN_COLUMNLABEL);

        super.getColumnNames().add(COLUMN_COLUMNDESCRIPTION);

        super.getColumnNames().add(COLUMN_SEQUENCE);
    }

    /**
     * The requested widths of each cell in pixels.
     *
     * @see TableModel#hasCellWidthOverrides
     */
    private void populateColumnWidths()
    {
        super.getCellWidths().put(LogColumnTableModel.COLUMN_COLUMNNAME, new BigDecimal(30));

        super.getCellWidths().put(LogColumnTableModel.COLUMN_COLUMNLABEL, new BigDecimal(30));

        super.getCellWidths().put(LogColumnTableModel.COLUMN_COLUMNDESCRIPTION, new BigDecimal(30));

        super.getCellWidths().put(LogColumnTableModel.COLUMN_SEQUENCE, new BigDecimal(10));
    }

    /**
     * When in active mode, the columns should be rendered as a text field.
     */
    private void populateColumnRenderings()
    {
        getCellRenderings().put(COLUMN_COLUMNNAME, RENDER_CELL_AS_TEXTFIELD);

        getCellRenderings().put(COLUMN_COLUMNLABEL, RENDER_CELL_AS_TEXTFIELD);

        getCellRenderings().put(COLUMN_COLUMNDESCRIPTION, RENDER_CELL_AS_TEXTFIELD);

        getCellRenderings().put(COLUMN_SEQUENCE, RENDER_CELL_AS_TEXTFIELD);
    }


    /**
     * A dummy row.
     *
     * @return
     *
     * @see TableModel#getEmptyTableRow
     */
    protected TableRow getEmptyTableRow()
    {
        if (emptyTableRow == null)
        {
            emptyTableRow = new LogColumnTableRow("", "", "", "");
        }

        emptyTableRow.setRowStatus(TableRow.ROW_STATUS_SELECTED);

        emptyTableRow.populateCellValues();

        return emptyTableRow;
    }

    /**
     * The associated Log.
     *
     * @return
     */
    public Log getLog()
    {
        return log;
    }

    /**
     * Builds an empty TableRow based on the data supplied from the LogColumn.
     *
     * @param logColumn
     */
    public void requestEmptyTableRow(LogColumn logColumn)
    {
        this.emptyTableRow = new LogColumnTableRow(logColumn.getColumnName(), logColumn.getColumnLabel(),
                logColumn.getColumnDescription(), String.valueOf(logColumn.getSequence()));

        super.requestEmptyTableRow();
    }

    /**
     * Builds an empty TableRow based on specified params.
     *
     * @param dbTableName
     * @param dbColumnName
     * @param sequence
     */
    public void requestEmptyTableRow(String columnName, String columnLabel, String columnDescription, String sequence)
    {
        this.emptyTableRow = new LogColumnTableRow(columnName, columnLabel, columnDescription, sequence);

        super.requestEmptyTableRow();
    }

    public void setLog(Log log)
    {
        this.log = log;
    }

    /**
     * Convenience method to get the currently selected LogColumn.
     *
     * @return
     */
    public LogColumn getLogColumn()
    {
        LogColumn logColumn = null;

        String selectedTableRowId = getSelectedRowId();

        if (selectedTableRowId != null)
        {
            logColumn = LogColumn.findBy_LogColumnPK(new Long(selectedTableRowId));
        }

        return logColumn;
    }
}
