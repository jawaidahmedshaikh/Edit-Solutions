package edit.portal.widget;

import edit.portal.widgettoolkit.TableModel;

import edit.portal.widgettoolkit.TableRow;

import fission.global.AppReqBlock;

import java.math.BigDecimal;


import logging.Log;

/**
 * Represents the Log table in the DB.
 */
public class LogTableModel extends TableModel
{

    public static final String COLUMN_LOG_NAME = "Log Name";

    public static final String COLUMN_LOG_DESCRIPTION = "Log Description";

    public static final String COLUMN_ACTIVE = "Active";

    /**
     * A default, editable TableRow when adding a new LogTableRow.
     */
    private LogTableRow emptyLogTableRow;

    private LogTableModel(AppReqBlock appReqBlock)
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
        Log[] logs = Log.findAll_V1();

        for (int i = 0; i < logs.length; i++)
        {
            LogTableRow row = new LogTableRow(logs[i], i);

            if (getSelectedRowId() != null && row.getRowId().equals(getSelectedRowId()))
            {
                row.setRowStatus(TableRow.ROW_STATUS_SELECTED);
            }

            row.populateCellValues();

            super.getRows().add(row);
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
        super.getColumnNames().add(COLUMN_LOG_NAME);

        super.getColumnNames().add(COLUMN_LOG_DESCRIPTION);

        super.getColumnNames().add(COLUMN_ACTIVE);
    }

    /**
     * The requested widths of each cell in pixels.
     *
     * @see TableModel#hasCellWidthOverrides
     */
    private void populateColumnWidths()
    {
        super.getCellWidths().put(LogTableModel.COLUMN_LOG_NAME, new BigDecimal(35));

        super.getCellWidths().put(LogTableModel.COLUMN_LOG_DESCRIPTION, new BigDecimal(50));

        super.getCellWidths().put(LogTableModel.COLUMN_ACTIVE, new BigDecimal(15));
    }

    /**
     * When in active mode, the columns should be rendered as a text field.
     */
    private void populateColumnRenderings()
    {
        getCellRenderings().put(COLUMN_LOG_NAME, RENDER_CELL_AS_TEXTFIELD);

        getCellRenderings().put(COLUMN_LOG_DESCRIPTION, RENDER_CELL_AS_TEXTFIELD);

        getCellRenderings().put(COLUMN_ACTIVE, RENDER_CELL_AS_TEXTFIELD);
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
        if (emptyLogTableRow == null)
        {
            emptyLogTableRow = new LogTableRow("", "", "");
        }

        emptyLogTableRow.setRowStatus(TableRow.ROW_STATUS_SELECTED);

        emptyLogTableRow.populateCellValues();

        return emptyLogTableRow;
    }

    /**
     * Sets a default editable TableLogRow based on the specified Log.
     *
     * @param log
     */
    public void requestEmptyTableRow(Log log)
    {
        emptyLogTableRow = new LogTableRow(log.getLogName(), log.getLogDescription(), log.getActive());

        super.requestEmptyTableRow();
    }

    /**
     * Convenience method to return the associated Log of the selected TableRow.
     *
     * @return
     */
    public Log getLog()
    {
        Log log = null;

        String selectedTableRowId = getSelectedRowId();

        if (selectedTableRowId != null)
        {
            log = Log.findByPK_V1(new Long(selectedTableRowId));
        }

        return log;
    }
}
