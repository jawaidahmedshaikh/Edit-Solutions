/*
 * User: gfrosti
 * Date: Mar 22, 2005
 * Time: 8:41:37 AM
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package edit.portal.widgettoolkit;

import fission.global.AppReqBlock;
import fission.utility.Util;

import java.util.*;


public abstract class DoubleTableModel
{
    public static final int FROM_TABLEMODEL = 1;
    public static final int TO_TABLEMODEL = 2;

    public static final String TABLE_ID = "tableId";

    public static final String PROPERTY_MULTIPLE_ROW_SELECT = "multipleRowSelect";

    public static final String PROPERTY_MULTIPLE_ROW_SELECT_VALUE_FALSE = "false";
    public static final String PROPERTY_MULTIPLE_ROW_SELECT_VALUE_TRUE = "true";

    /**
     * The specified TableModel.
     * @see TableModel
     * @see DoubleTableModel#TO_TABLEMODEL
     * @see DoubleTableModel#FROM_TABLEMODEL
     * @param tableModelNumber
     * @return
     */
    public abstract TableModel getTableModel(int tableModelNumber);

    /**
     * Moves the ith DoubleListElement from the specified list to the other implied list.
     * @param fromTableModelNumber
     * @param fromRowId
     */
    public abstract void moveRow(int fromTableModelNumber, String fromRowId);

    /**
     * Changes the state of the DoubleTableModel i.e. moving rows from one table to another etc...
     */
    public abstract void updateState();

    /**
     * Returns appReqBlock.
     * @return AppReqBlock
     */
    protected abstract AppReqBlock getAppReqBlock();

    /**
     * Puts DoubleTableModel in User Session.
     */
    protected void placeDoubleTableModelInSessionScope()
    {
        String httpSessionIdentifier = Util.getClassName(Util.getClassName(Util.getFullyQualifiedClassName(this.getClass())));

        getAppReqBlock().getHttpSession().setAttribute(httpSessionIdentifier, this);
    }

    /**
     * Returns DoubleTableModel that is in User Session.
     * @return DoubleTableModel
     */
    protected DoubleTableModel getDoubleTableModelFromSessionScope()
    {
        String httpSessionIdentifier = Util.getClassName(Util.getClassName(Util.getFullyQualifiedClassName(this.getClass())));

        return (DoubleTableModel) getAppReqBlock().getHttpSession().getAttribute(httpSessionIdentifier);
    }

    /**
     * Verifies DoubleTableModel exists in the User Session.
     * @return
     */
    protected boolean isExistsInSessionScope()
    {
        String httpSessionIdentifier = Util.getClassName(Util.getClassName(Util.getFullyQualifiedClassName(this.getClass())));

        return getAppReqBlock().getHttpSession().getAttribute(httpSessionIdentifier) != null;
    }

    /**
     * Removes DoubleTableModel from User Session.
     */
    public void removeDoubleTableModelFromSessionScope()
    {
        String httpSessionIdentifier = Util.getClassName(Util.getClassName(Util.getFullyQualifiedClassName(this.getClass())));

        getAppReqBlock().getHttpSession().removeAttribute(httpSessionIdentifier);
    }

    /**
     * Returns the selected row Ids in the DoubleTableModel.
     * @return string array containing
     */
    public String[] getSelectedRowIds()
    {
        List selectedRowIds = new ArrayList();

        TableModel tableModel = getTableModel(DoubleTableModel.TO_TABLEMODEL);

        List tableRows = tableModel.getRows();

        for (Iterator iterator = tableRows.iterator(); iterator.hasNext();)
        {
            TableRow tableRow = (TableRow) iterator.next();

            selectedRowIds.add(tableRow.getRowId());
        }

        return (String[]) selectedRowIds.toArray(new String[selectedRowIds.size()]);
    }
}
