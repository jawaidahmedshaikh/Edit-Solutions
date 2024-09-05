/*
 * User: gfrosti
 * Date: Mar 24, 2005
 * Time: 12:23:50 PM
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package edit.portal.widget;

import contract.ContractClient;
import edit.portal.widgettoolkit.TableModel;
import edit.portal.widgettoolkit.TableRow;
import fission.global.AppReqBlock;

import java.util.*;

import fission.global.AppReqBlock;


public class ContractClientTableModel extends TableModel
{
    private ContractClient[] contractClients;


    public ContractClientTableModel(AppReqBlock appReqBlock, String[] columnNames, ContractClient[] contractClients)
    {
        super(appReqBlock);

        getColumnNames().addAll(Arrays.asList(columnNames));

        this.contractClients = contractClients;
    }

    public ContractClientTableModel(AppReqBlock appReqBlock, String[] columnNames)
    {
        super(appReqBlock);

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

            TableRow tableRow = new ContractClientTableRow(contractClient);

            super.addRow(tableRow);
        }
    }
}
