/*
 * User: dlataille
 * Date: Sept 19, 2007
 * Time: 11:38:02 AM
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package edit.portal.widget;

import contract.ContractClient;
import contract.Segment;
import edit.portal.widgettoolkit.DoubleTableModel;
import edit.portal.widgettoolkit.TableModel;
import edit.portal.widgettoolkit.TableRow;

import fission.utility.Util;
import fission.global.AppReqBlock;

import java.util.List;
import java.util.ArrayList;


public class RiderDetailDoubleTableModel extends DoubleTableModel
{
    public static final String COLUMN_RIDER_NAME = "Rider Name";

    private RiderDetailTableModel fromTableModel;
    private RiderDetailTableModel toTableModel;
    private static final String[] COLUMN_NAMES = {COLUMN_RIDER_NAME};

    private Segment segment;
    private AppReqBlock appReqBlock;

    public RiderDetailDoubleTableModel(Segment segment, AppReqBlock appReqBlock)
    {
        this.segment = segment;

        this.appReqBlock = appReqBlock;

        Segment[] riders = segment.getRiders();
        riders = (Segment[]) Util.sortObjects(riders, new String [] {"getOptionCodeCT"});

        if (isExistsInSessionScope())
        {
            this.fromTableModel = (RiderDetailTableModel) getDoubleTableModelFromSessionScope().getTableModel(DoubleTableModel.FROM_TABLEMODEL);

            this.toTableModel = (RiderDetailTableModel) getDoubleTableModelFromSessionScope().getTableModel(DoubleTableModel.TO_TABLEMODEL);
        }
        else
        {
            this.fromTableModel = new RiderDetailTableModel(appReqBlock, COLUMN_NAMES, riders);

            this.toTableModel = new RiderDetailTableModel(appReqBlock, COLUMN_NAMES, riders);

            //The toTableModel is never built from the input data only the selections of the fromTableModel
            this.toTableModel.setRowsPopulated(true);

            populateFromTableModel();

            placeDoubleTableModelInSessionScope();
        }
    }

    /**
     * Builds the superset TableModel driven by the Client
     */
    private void populateFromTableModel()
    {
        String selectedCasetrackingOption = Util.initString(appReqBlock.getReqParm("casetrackingOption"), "");
        segment.setSelectedCasetrackingOption(selectedCasetrackingOption);

        //Set the switch first or the from table model will place the rows twice
        fromTableModel.setRowsPopulated(true);

        fromTableModel.buildTableRows();
    }

    /**
     * @see edit.portal.widgettoolkit.DoubleTableModel#getTableModel(int)
     * @param tableModelNumber
     * @return
     */
    public TableModel getTableModel(int tableModelNumber)
    {
        RiderDetailTableModel tableModel = null;

        switch (tableModelNumber)
        {
        case DoubleTableModel.FROM_TABLEMODEL:
            tableModel = fromTableModel;
            break;

        case DoubleTableModel.TO_TABLEMODEL:
            tableModel = toTableModel;
            break;
        }

        return tableModel;
    }

    /**
     * @see DoubleTableModel#moveRow(int, String)
     * @param fromTableModelNumber
     * @param fromRowId
     */
    public void moveRow(int fromTableModelNumber, String fromRowId)
    {
        RiderDetailTableModel fromTable = (RiderDetailTableModel) getTableModel(fromTableModelNumber);

        RiderDetailTableModel toTable = (RiderDetailTableModel) getTableModel((fromTableModelNumber == FROM_TABLEMODEL) ? TO_TABLEMODEL : FROM_TABLEMODEL);
        TableRow tableRow = fromTable.removeRow(fromRowId);

        toTable.addRow(tableRow);
    }

    /**
     * @see DoubleTableModel#updateState()
     */
    public void updateState()
    {
        String[] selectedIds = Util.fastTokenizer(appReqBlock.getHttpServletRequest().getParameter("selectedIds"), ",");

        RiderDetailTableModel fromTable = (RiderDetailTableModel) getTableModel(FROM_TABLEMODEL);

        RiderDetailTableModel toTable = (RiderDetailTableModel) getTableModel(TO_TABLEMODEL);

        for (int i = 0; i < selectedIds.length; i++)
        {
            if (fromTable.getRow(selectedIds[i]) == null)
            {
                moveRow(DoubleTableModel.TO_TABLEMODEL, selectedIds[i]);
            }
            else if (toTable.getRow(selectedIds[i]) == null)
            {
                moveRow(DoubleTableModel.FROM_TABLEMODEL, selectedIds[i]);
            }
        }

        placeDoubleTableModelInSessionScope();
    }

    protected AppReqBlock getAppReqBlock()
    {
        return appReqBlock;
    }
}
