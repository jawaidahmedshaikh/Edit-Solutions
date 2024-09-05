package edit.portal.widget;

import client.ClientDetail;
import contract.ContractClient;
import edit.portal.widgettoolkit.DoubleTableModel;
import edit.portal.widgettoolkit.TableModel;
import edit.portal.widgettoolkit.TableRow;
import fission.utility.Util;
import fission.global.AppReqBlock;

import java.util.*;


/**
 * Created by IntelliJ IDEA.
 * User: gfrosti
 * Date: Mar 22, 2005
 * Time: 1:23:55 PM
 * To change this template use File | Settings | File Templates.
 */
public class ContractClientDoubleTableModel extends DoubleTableModel
{
    public static final String COLUMN_CONTRACT_NUMBER = "Contract #";
    public static final String COLUMN_SEGMENTSTATUSCT = "Status";
    public static final String COLUMN_ROLETYPECT = "Role";
    public static final String COLUMN_SEGMENTNAMECT = "Segment";

    private ContractClientTableModel fromTableModel;
    private ContractClientTableModel toTableModel;
    private static final String[] COLUMN_NAMES = {COLUMN_CONTRACT_NUMBER, COLUMN_SEGMENTSTATUSCT, COLUMN_ROLETYPECT, COLUMN_SEGMENTNAMECT};

    private ClientDetail clientDetail;
    private AppReqBlock appReqBlock;

    public ContractClientDoubleTableModel(ClientDetail clientDetail, AppReqBlock appReqBlock, String trxType)
    {
        this.clientDetail = clientDetail;

        this.appReqBlock = appReqBlock;

        ContractClient[] contractClients = null;
        if (trxType != null)
        {
            contractClients = ContractClient.findBy_ClientDetail_Roles_ForDeath(clientDetail);
        }
        else
        {
            contractClients = ContractClient.findBy_ClientDetail_Roles(clientDetail);
        }

        if (isExistsInSessionScope())
        {
            this.fromTableModel = (ContractClientTableModel) getDoubleTableModelFromSessionScope().getTableModel(DoubleTableModel.FROM_TABLEMODEL);

            this.toTableModel = (ContractClientTableModel) getDoubleTableModelFromSessionScope().getTableModel(DoubleTableModel.TO_TABLEMODEL);

        }
        else
        {
            this.fromTableModel = new ContractClientTableModel(appReqBlock, COLUMN_NAMES, contractClients);

            this.toTableModel = new ContractClientTableModel(appReqBlock,  COLUMN_NAMES, contractClients);

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
        //Set the switch first or the from table model will place the rows twice
        fromTableModel.setRowsPopulated(true);

        fromTableModel.buildTableRows();

    }

    /**
     * @see DoubleTableModel#getTableModel(int)
     * @param tableModelNumber
     * @return
     */
    public TableModel getTableModel(int tableModelNumber)
    {
        ContractClientTableModel tableModel = null;

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
        ContractClientTableModel fromTable = (ContractClientTableModel) getTableModel(fromTableModelNumber);

        ContractClientTableModel toTable = (ContractClientTableModel) getTableModel((fromTableModelNumber == FROM_TABLEMODEL) ? TO_TABLEMODEL : FROM_TABLEMODEL);

        TableRow tableRow = fromTable.removeRow(fromRowId);

        toTable.addRow(tableRow);
    }

    /**
     * @see DoubleTableModel#updateState()
     */
    public void updateState()
    {
        String responseMessage = null;

        String[] selectedIds = Util.fastTokenizer(appReqBlock.getHttpServletRequest().getParameter("selectedIds"), ",");

        ContractClientTableModel fromTable = (ContractClientTableModel) getTableModel(FROM_TABLEMODEL);

        ContractClientTableModel toTable = (ContractClientTableModel) getTableModel(TO_TABLEMODEL);

        List currentlyAddedContractNumbers = new ArrayList();

        for (int i = 0; i < selectedIds.length; i++)
        {
            if (fromTable.getRow(selectedIds[i]) == null)
            {
                moveRow(DoubleTableModel.TO_TABLEMODEL, selectedIds[i]);
            }
            else if (toTable.getRow(selectedIds[i]) == null)
            {
                TableRow selTableRow = fromTableModel.getRow(selectedIds[i]);

                String selContractNumber = (String) selTableRow.getCellValue(ContractClientDoubleTableModel.COLUMN_CONTRACT_NUMBER);

                if (verifyIfContractNumberExists(selectedIds[i]))
                {
                    boolean isContractJustAdded = false;

                    for (int j = 0; j < currentlyAddedContractNumbers.size(); j++)
                    {
                        if (selContractNumber.equals((String) currentlyAddedContractNumbers.get(j)));
                        {
                            isContractJustAdded = true;
                            break;
                        }
                    }

                    if (!isContractJustAdded)
                    {
                        if (responseMessage == null)
                        {
                            responseMessage = "The following Contract Numbers have already been selected ";
                        }

                        if (responseMessage.indexOf(selContractNumber) < 0)
                        {
                            responseMessage += selContractNumber + ", ";
                        }
                    }
                }
                else
                {
                    moveRow(DoubleTableModel.FROM_TABLEMODEL, selectedIds[i]);

                    currentlyAddedContractNumbers.add(selContractNumber);
                }
            }
        }

        if (responseMessage != null)
        {
            responseMessage = responseMessage.substring(0, responseMessage.length()-2);
        }

        placeDoubleTableModelInSessionScope();

        appReqBlock.getHttpServletRequest().setAttribute("responseMessage", responseMessage);
    }

    /**
     * Finder method to verify the selected row's contract number has already been moved to right side table.
     * @param selectedId
     * @return
     */
    private boolean verifyIfContractNumberExists(String selectedId)
    {
        ContractClientTableModel toTable = (ContractClientTableModel) getTableModel(TO_TABLEMODEL);

        List toTableRows = toTable.getRows();

        TableRow selTableRow = fromTableModel.getRow(selectedId);

        String selContractNumber = (String) selTableRow.getCellValue(ContractClientDoubleTableModel.COLUMN_CONTRACT_NUMBER);
        boolean isExists = false;

        for (Iterator iterator = toTableRows.iterator(); iterator.hasNext();)
        {
            TableRow tableRow = (TableRow) iterator.next();

            String curContractNumber = (String) tableRow.getCellValue(ContractClientDoubleTableModel.COLUMN_CONTRACT_NUMBER);

            // if exists in the toTable.
            if (curContractNumber.equals(selContractNumber))
            {
                isExists = true;
                break;
            }
        }

        return isExists;
    }

    /**
     * Getter.
     * @return
     */
    protected AppReqBlock getAppReqBlock()
    {
        return appReqBlock;
    }
}
