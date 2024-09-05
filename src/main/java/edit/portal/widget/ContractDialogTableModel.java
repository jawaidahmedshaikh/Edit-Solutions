/*
 * User: sprasad
 * Date: May 16, 2005
 * Time: 12:27:55 PM
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package edit.portal.widget;

import contract.ContractClient;
import contract.Segment;
import edit.portal.widgettoolkit.TableModel;
import edit.portal.widgettoolkit.TableRow;
import fission.global.AppReqBlock;
import fission.utility.Util;
import java.util.*;

public class ContractDialogTableModel extends TableModel
{
//    public static final String COLUMN_LAST_NAME = "Last Name";
//    public static final String COLUMN_FIRST_NAME= "First Name";
    public static final String COLUMN_NAME = "Name";
    public static final String COLUMN_ROLE_TYPE_CT = "Role";
    public static final String COLUMN_RELATIONSHIP = "Relationship";
    public static final String COLUMN_CLIENT_STATUS = "Client Status";
    public static final String COLUMN_ALLOC_PERCENT = "Bene Alloc%";

    private static final String[] COLUMN_NAMES = {COLUMN_NAME, COLUMN_ROLE_TYPE_CT, COLUMN_RELATIONSHIP,
                                                  COLUMN_CLIENT_STATUS, COLUMN_ALLOC_PERCENT};

    private Segment segment;


    public ContractDialogTableModel(Segment segment, AppReqBlock appReqBlock)
    {
        super(appReqBlock);

        getColumnNames().addAll(Arrays.asList(COLUMN_NAMES));

        this.segment = segment;
    }

    /**
     * @return
     * Builds the superset TableModel driven by the Segment
     */
    protected void buildTableRows()
    {
        Set<ContractClient> contractClientSet = segment.getContractClients();

        ContractClient[] contractClients = (ContractClient[]) contractClientSet.toArray(new ContractClient[contractClientSet.size()]);

        contractClients = (ContractClient[]) Util.sortObjects(contractClients, new String []{"getContractClientPK"});

        for (int i = 0; i < contractClients.length; i++)
        {
            ContractClient contractClient = contractClients[i];

            TableRow tableRow = new ContractDialogTableRow(contractClient);
            
            // Add current records (status 'P') to table
            String overrideStatus = contractClient.getOverrideStatus();
            if (overrideStatus.equalsIgnoreCase("P")){
	            if (tableRow.getRowId().equals(this.getSelectedRowId()))
	            {
	                tableRow.setRowStatus(TableRow.ROW_STATUS_SELECTED);
	            }
	            
	            super.addRow(tableRow);
            
            }
        }
    }
}
