/*
 * User: sprasad
 * Date: May 6, 2005
 * Time: 2:34:48 PM
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package edit.portal.widget;

import contract.ContractClient;
import edit.portal.widgettoolkit.TableRow;
import edit.portal.widgettoolkit.TableModel;

import fission.global.AppReqBlock;

import java.util.*;



public class ContractDetailTableModel extends TableModel
{

    private ContractClient[] contractClients;


    public ContractDetailTableModel(AppReqBlock appReqBlock, String[] columnNames, ContractClient[] contractClients)
    {
        super(appReqBlock);

        this.contractClients = contractClients;

        getColumnNames().addAll(Arrays.asList(columnNames));
    }

    /**
     * Setter.
     * @param contractClients
     */
    public void buildTableRows()
    {
        for (int i = 0; i < contractClients.length; i++)
        {
            ContractClient contractClient = contractClients[i];

            TableRow tableRow = new ContractDetailTableRow(contractClient);

            super.addRow(tableRow);
        }
    }
}
