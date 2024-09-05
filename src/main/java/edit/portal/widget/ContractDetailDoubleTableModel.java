/*
 * User: sprasad
 * Date: May 6, 2005
 * Time: 2:33:02 PM
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
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


public class ContractDetailDoubleTableModel extends DoubleTableModel
{
    public static final String COLUMN_TAX_ID = "Tax ID";
    public static final String COLUMN_NAME= "Name";
    public static final String COLUMN_BIRTHDATE = "DOB";
    public static final String COLUMN_ROLETYPECT = "Role";
    public static final String COLUMN_ALLOC_PERCENT = "Alloc%";

    private ContractDetailTableModel fromTableModel;
    private ContractDetailTableModel toTableModel;
    private static final String[] COLUMN_NAMES = {COLUMN_TAX_ID, COLUMN_NAME, COLUMN_BIRTHDATE, COLUMN_ROLETYPECT, COLUMN_ALLOC_PERCENT};

    private Segment segment;
    private AppReqBlock appReqBlock;

    public ContractDetailDoubleTableModel(Segment segment, AppReqBlock appReqBlock)
    {
        this.segment = segment;

        this.appReqBlock = appReqBlock;

        ContractClient[] beneficiaries = segment.getBeneficiaryContractClients();
        beneficiaries = (ContractClient[]) Util.sortObjects(beneficiaries, new String [] {"getContractClientPK"});

        if (isExistsInSessionScope())
        {
            this.fromTableModel = (ContractDetailTableModel) getDoubleTableModelFromSessionScope().getTableModel(DoubleTableModel.FROM_TABLEMODEL);

            this.toTableModel = (ContractDetailTableModel) getDoubleTableModelFromSessionScope().getTableModel(DoubleTableModel.TO_TABLEMODEL);
        }
        else
        {
            this.fromTableModel = new ContractDetailTableModel(appReqBlock, COLUMN_NAMES, beneficiaries);

            this.toTableModel = new ContractDetailTableModel(appReqBlock, COLUMN_NAMES, beneficiaries);

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
        ContractDetailTableModel tableModel = null;

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
        ContractDetailTableModel fromTable = (ContractDetailTableModel) getTableModel(fromTableModelNumber);

        ContractDetailTableModel toTable = (ContractDetailTableModel) getTableModel((fromTableModelNumber == FROM_TABLEMODEL) ? TO_TABLEMODEL : FROM_TABLEMODEL);

        TableRow tableRow = fromTable.removeRow(fromRowId);

        toTable.addRow(tableRow);
    }

    /**
     * @see DoubleTableModel#updateState()
     */
    public void updateState()
    {
        String[] selectedIds = Util.fastTokenizer(appReqBlock.getHttpServletRequest().getParameter("selectedIds"), ",");

        ContractDetailTableModel fromTable = (ContractDetailTableModel) getTableModel(FROM_TABLEMODEL);

        ContractDetailTableModel toTable = (ContractDetailTableModel) getTableModel(TO_TABLEMODEL);

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
