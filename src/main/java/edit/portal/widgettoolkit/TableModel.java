/*
 * User: gfrosti
 * Date: Mar 22, 2005
 * Time: 9:25:16 AM
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package edit.portal.widgettoolkit;

import fission.global.AppReqBlock;

import fission.utility.Util;

import java.lang.reflect.Constructor;

import java.util.*;


public abstract class TableModel
{
    /**
     * jsp:include parameter tableHeight
     */
    public static final String PROPERTY_TABLE_HEIGHT = "tableHeight";

    /**
     * jsp:include parameter tableWidth
     */
    public static final String PROPERTY_TABLE_WIDTH = "tableWidth";

    /**
     * jsp:include parameter mulipleRowSelect
     */
    public static final String PROPERTY_MULTIPLE_ROW_SELECT = "multipleRowSelect";

    /**
     * jsp:include parameter singleOrDoubleClick
     */
    public static final String PROPERTY_SINGLE_OR_DOUBLE_CLICK = "singleOrDoubleClick";


    public static final String PROPERTY_MULTIPLE_ROW_SELECT_VALUE_FALSE = "false";
    public static final String PROPERTY_MULTIPLE_ROW_SELECT_VALUE_TRUE = "true";
    public static final String PROPERTY_TABLE_HEIGHT_VALUE_DEFAULT = "100";
    public static final String PROPERTY_TABLE_WIDTH_VALUE_DEFAULT = "100";
    public static final String PROPERTY_SINGLE_OR_DOUBLE_CLICK_VALUE_SINGLE = "single";
    public static final String PROPERTY_SINGLE_OR_DOUBLE_CLICK_VALUE_DOUBLE = "double";
    public static final String PROPERTY_SINGLE_OR_DOUBLE_CLICK_VALUE_NONE = "none";


    /**
     * If editable = true, then the rendered TableModel can have its cell
     * directly edited. The TableModel will be rendered with a radio button
     * to select the row to edit. The fields will be rendered as some
     * form of editable form element depending on the "cellRenderings" method
     * to be found in TableRow.
     */
    private boolean editable = false;

    /**
     * The hidden element name in tableWidget.jsp
     */
    public static final String SELECTED_ROW_IDS = "selectedRowIds_";

    /**
     * The map containing all tableModels in the request scope
     */
    public static final String TABLE_MODELS = "tableModels";

    /**
     * The jsp:include parameter tableId
     */
    public static final String TABLE_ID = "tableId";

    /**
     * Identifies that the TableModel will be placed in Request scope.
     */
    public static final int SCOPE_REQUEST = 0;

    /**
     * Identifies that the TableModel will be placed in Session scope.
     */
    public static final int SCOPE_SESSION = 1;

    /**
     * A voluntary specification of the width of each cell (in % as BigDecimal). If this
     * is not specified, the cells are evenly dispursed within their containing
     * TableRow.
     */
    private Map cellWidths = new HashMap();

    /**
     * Stores the type of element the associated column
     * is to be rendered as (e.g. text field or select in the case of HTML).
     * If this Map is not populated, it is assumed that
     * the column will be rendered as basic text.
     */
    private Map cellRenderings = new HashMap();

    /**
     * Render the column as basic text.
     */
    public static final String RENDER_CELL_AS_TEXT = "TEXT";

    /**
     * The column will be rendered with as an editable text field.
     */
    public static final String RENDER_CELL_AS_TEXTFIELD = "TEXTFIELD";

    /**
     * The column will be rendered with as a radio select button.
     */
    public static final String RENDER_CELL_AS_RADIO = "RADIO";

    /**
     * The column will be rendered as a select list. In such a
     * case, the returned value if the column is to be a List.
     */
    public static final String RENDER_CELL_AS_SELECT = "SELECT";

    /**
     * True if a new "dummy" row should be added when rendering this TableModel.
     */
    private boolean emptyTableRowRequested;

    /**
     * There is a risk of adding an empty row more than once, so we flag it
     * once it is done the first time.
     */
    private boolean emptyTableRowAdded;

    private List tableRows;
    private String selectedRowId;
    private AppReqBlock appReqBlock;
    private List columnNames = new ArrayList();
    private boolean rowsPopulated;


    public TableModel(AppReqBlock appReqBlock)
    {
        tableRows = new ArrayList();

        this.appReqBlock = appReqBlock;

        placeTableModelInScope(appReqBlock, this, SCOPE_REQUEST);

        setSelectedRowId(getSelectedRowIdFromRequestScope());
    }

    /**
     * Builds the superset TableModel
     */
    protected abstract void buildTableRows();

    /**
     * The set of columns names for this Table. The columns are assumed ordered. Each column name be used to access
     * the value of a column cell for an nth row.
     *
     * @return List of column names
     */
    public List getColumnNames()
    {
        return columnNames;
    }

    /**
     * Returns the number of columns for this Table.
     *
     * @return the number of columns in this table.
     */
    public int getColumnCount()
    {
        return getColumnNames().size();
    }

    /**
     * Returns the total number of data rows (excludes header) in the Table.
     *
     * @return total number of data rows
     */
    public int getRowCount()
    {
        return getRows().size();
    }


    /**
     * Returns the row specified by the given row number
     *
     * @param rowNumber nth row in the set of rows
     *
     * @return nth row
     */
    public TableRow getRow(int rowNumber)
    {
        return (TableRow) getRows().get(rowNumber);
    }


    /**
     * Returns the TableRow with the specified rowId.
     *
     * @param rowId
     *
     * @return
     */
    public TableRow getRow(String rowId)
    {
        List rows = getRows();

        TableRow targetRow = null;

        for (int i = 0; i < rows.size(); i++)
        {
            TableRow transferTableRow = (TableRow) rows.get(i);

            if (transferTableRow.getRowId().equals(rowId))
            {
                targetRow = transferTableRow;

                break;
            }
        }

        return targetRow;
    }

    /**
     * Returns all the rows belonging to the table.
     *
     * @return all rows in the table.
     */
    public List getRows()
    {
        if (!isRowsPopulated())
        {
            setRowsPopulated(true);

            buildTableRows();

            checkEditableRowRequested();
        }

        return tableRows;
    }


    /**
     * Adds a TableRow to the set of rows represented by this TableModel.
     *
     * @param tableRow
     */
    public void addRow(TableRow tableRow)
    {
        getRows().add(tableRow);
    }

    /**
     * Removes the TableRow with the specified Id from the set of TableRows from this TableModel.
     *
     * @param rowId the TableRow with the specified rowId, null otherwise
     *
     * @return the removed TableRow
     */
    public TableRow removeRow(String rowId)
    {
        TableRow tableRow = getRow(rowId);

        getRows().remove(tableRow);

        return tableRow;
    }

    /**
     * Returns appReqBlock
     *
     * @return appReqBlock
     */
    protected AppReqBlock getAppReqBlock()
    {
        return appReqBlock;
    }

    /**
     * Returns selected row ids of the table from HTML hidden parameter of the table.
     *
     * @return string of ids seperated with ',' (comma)
     */
    public String[] getSelectedRowIdsFromRequestScope()
    {
        String hiddenParameterName = SELECTED_ROW_IDS + Util.getClassName(Util.getFullyQualifiedClassName(this.getClass()));

        String strSelectedRowIds = Util.initString(getAppReqBlock().getReqParm(hiddenParameterName), null);

        String[] selectedRowIds = null;

        if (strSelectedRowIds != null)
        {
            selectedRowIds = Util.fastTokenizer(strSelectedRowIds, ",");
        }

        return selectedRowIds;
    }

    /**
     * Prints selected row ids of the table if the table has feature of selecting multiple rows.
     *
     * @return string of selected ids seperated by ',' (comma)
     */
    public String printSelectedRowIds()
    {
        StringBuffer selectedRowIds = new StringBuffer();

        List tableRows = getRows();

        for (int i = 0; i < tableRows.size(); i++)
        {
            TableRow currentTableRow = (TableRow) tableRows.get(i);

            if (TableRow.ROW_STATUS_SELECTED.equalsIgnoreCase(currentTableRow.getRowStatus()))
            {
                selectedRowIds.append(currentTableRow.getRowId()).append(",");
            }
        }

        if (selectedRowIds.length() > 0)
        {
            selectedRowIds.deleteCharAt(selectedRowIds.length() - 1);
        }

        return selectedRowIds.toString();
    }

    /**
     * Returns the selected row id of the table.
     *
     * @return string or null if it does not have any
     */
    public String getSelectedRowIdFromRequestScope()
    {
        return (getSelectedRowIdsFromRequestScope() != null) ? Util.initString(getSelectedRowIdsFromRequestScope()[0], null) : null;
    }

    /**
     * Puts the table in the specified scope.
     *
     * @see #SCOPE_REQUEST
     * @see #SCOPE_SESSION
     */
    private static void placeTableModelInScope(AppReqBlock appReqBlock, TableModel tableModel, int scope)
    {
        Map tableModels = null;

        if (scope == TableModel.SCOPE_REQUEST)
        {
            tableModels = (Map) appReqBlock.getHttpServletRequest().getAttribute(TableModel.TABLE_MODELS);
        }
        else if (scope == TableModel.SCOPE_SESSION)
        {
            tableModels = (Map) appReqBlock.getHttpSession().getAttribute(TableModel.TABLE_MODELS);
        }
        else
        {
            throw new RuntimeException("Table Model " + tableModel.getTableId() +
                    " must be placed in either Request scope or Session scope");
        }

        if (tableModels == null)
        {
            tableModels = new HashMap();
        }

        tableModels.put(tableModel.getTableId(), tableModel);

        if (scope == TableModel.SCOPE_REQUEST)
        {
            appReqBlock.getHttpServletRequest().setAttribute(TableModel.TABLE_MODELS, tableModels);
        }
        else if (scope == TableModel.SCOPE_SESSION)
        {
            appReqBlock.getHttpSession().setAttribute(TableModel.TABLE_MODELS, tableModels);
        }
    }

    /**
     * The fully qualified name of this TableModel class.
     *
     * @return
     */
    public String getTableId()
    {
        return getTableId(this.getClass());
    }

    /**
     * The tableId of the specified tableModelClass.
     */
    public static String getTableId(Class tableModelClass)
    {
        return Util.getClassName(Util.getFullyQualifiedClassName(tableModelClass));
    }

    /**
     * Unselects all rows in a table model.
     */
    public void resetAllRows()
    {
        String hiddenParameterName = SELECTED_ROW_IDS + Util.getClassName(Util.getFullyQualifiedClassName(this.getClass()));

        getAppReqBlock().setReqParm(hiddenParameterName, null);

        setSelectedRowId(null);

        // This will force a fresh rebuilding of the rows.
        setRowsPopulated(false);
    }

    /**
     * Checks to see if the TableRows have already been built.
     *
     * @eturn true if the TableRows have been built
     */
    public boolean isRowsPopulated()
    {
        return rowsPopulated;
    }

    /**
     * Set when the rows have been populated. This is called when any effort to access the TableRows shows that
     * the rows have not been populated.
     *
     * @param rowsPopulated true if the rows have been previously populated
     */
    public void setRowsPopulated(boolean rowsPopulated)
    {
        this.rowsPopulated = rowsPopulated;
    }

    /**
     * Setter.
     *
     * @param selectedRowId
     */
    protected void setSelectedRowId(String selectedRowId)
    {
        this.selectedRowId = selectedRowId;
    }

    /**
     * Getter.
     *
     * @return
     */
    public String getSelectedRowId()
    {
        return selectedRowId;
    }

    /**
     * Returns the selected TableRow
     *
     * @return
     */
    public TableRow getSelectedRow()
    {
        return this.getRow(selectedRowId);
    }

    /**
     * The width of each cell in percent as a BigDecimal. It is not mandatory to specify these
     * values. If they are not specified, then the columns are evenly spaced
     * throughout the containing TableRow.
     *
     * @return
     */
    public Map getCellWidths()
    {
        return cellWidths;
    }

    /**
     * True if the default cell widths are supplied/overriden.
     *
     * @return
     */
    public boolean hasCellWidthOverrides()
    {
        return !getCellWidths().isEmpty();
    }

    public void setEditable(boolean editable)
    {
        this.editable = editable;
    }

    /**
     * @return
     *
     * @see #editable
     */
    public boolean isEditable()
    {
        return editable;
    }

    /**
     * The mappings that determine how a particular cell should be rendered.
     *
     * @return the cell's render-as mappings
     *
     * @see #RENDER_CELL_AS_TEXT
     * @see #RENDER_CELL_AS_TEXTFIELD
     * @see #RENDER_CELL_AS_RADIO
     * @see #RENDER_CELL_AS_SELECT
     */
    protected Map getCellRenderings()
    {
        return cellRenderings;
    }

    /**
     * The manner in which the table cell should be rendered (radio, text, select, etc.)
     *
     * @param columnName
     *
     * @return
     */
    public String getRenderCellAs(String columnName)
    {
        String renderCellAs = RENDER_CELL_AS_TEXT;

        if (cellRenderings.containsKey(columnName))
        {
            renderCellAs = (String) getCellRenderings().get(columnName);
        }

        return renderCellAs;
    }

    /**
     * If an editable row has been requested, then a dummy editable row is
     * added to the list of current rows.
     */
    private void checkEditableRowRequested()
    {
        if (isEmptyTableRowRequested() && !emptyTableRowAdded)
        {
            List rows = getRows();

            // Disable any current row status so that 2 rows are not selected.
            for (int i = 0; i < rows.size(); i++)
            {
                TableRow tableRow = (TableRow) rows.get(i);

                tableRow.setRowStatus(TableRow.ROW_STATUS_DEFAULT);
            }

            TableRow emptyTableRow = getEmptyTableRow();

            emptyTableRow.setRowStatus(TableRow.ROW_STATUS_SELECTED);

            rows.add(0, emptyTableRow);

            emptyTableRowAdded = true;
        }
    }

    /**
     * True if this TableModel should render an empty editable TableRow.
     *
     * @return
     */
    private boolean isEmptyTableRowRequested()
    {
        return emptyTableRowRequested;
    }

    /**
     * Flags this TableModel to render a default empty editable TableRow.
     */
    public void requestEmptyTableRow()
    {
        setEmptyTableRowRequested(true);
    }

    /**
     * In the case of an editable TableModel, this method is expected to
     * be overridden in order to supply a default empty TableRow. This default
     * TableRow should have:
     * 1) A dummy pk of 0.
     * 2) Appropriate defaulted values for a selected TableRow.
     *
     * @return
     *
     * @see TableRow#DUMMY_TABLEROW_PK
     */
    protected TableRow getEmptyTableRow()
    {
        return null;
    }

    private void setEmptyTableRowRequested(boolean emptyTableRowRequested)
    {
        this.emptyTableRowRequested = emptyTableRowRequested;
    }

    /**
     * True if one of this TableModel's rows is actively selected.
     */
    public boolean isRowSelected()
    {
        boolean isRowSelected = false;

        List rows = getRows();

        for (int i = 0; i < rows.size(); i++)
        {
            TableRow tableRow = (TableRow) rows.get(i);

            if (tableRow.isSelected())
            {
                isRowSelected = true;

                break;
            }
        }

        return isRowSelected;
    }

    /**
     * Finds the specified TableModel in the request-scope.
     * If it is not found, then the specified TableModel is instantiated
     * using a constructor that takes the appReqBlock and is then placed in
     * request-scope. This guarantees that within a single request, the same
     * TableModel is used throughout that request.
     */
    public static TableModel get(AppReqBlock appReqBlock, Class tableModelClass)
    {
        TableModel tableModel = null;

        Map tableModels = (Map) appReqBlock.getHttpServletRequest().getAttribute(TableModel.TABLE_MODELS);

        if (tableModels != null)
        {
            tableModel = (TableModel) tableModels.get(getTableId(tableModelClass));
        }

        if (tableModel == null)
        {
            try
            {
                Constructor tableModelConstructor = tableModelClass.getDeclaredConstructor(new Class[]
                {AppReqBlock.class});

                tableModelConstructor.setAccessible(true);

                tableModel = (TableModel) tableModelConstructor.newInstance(new Object[]
                {appReqBlock});
            }
            catch (Exception e)
            {
                System.out.println(e);

                e.printStackTrace();

                throw new RuntimeException(e);
            }
        }

        placeTableModelInScope(appReqBlock, tableModel, SCOPE_REQUEST);

        return tableModel;
    }
}
