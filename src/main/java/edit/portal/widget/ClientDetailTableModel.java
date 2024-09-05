/*
 * User: sprasad
 * Date: May 24, 2005
 * Time: 1:34:44 PM
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package edit.portal.widget;

import client.ClientDetail;
import contract.ContractClient;
import edit.portal.widgettoolkit.TableModel;
import edit.portal.widgettoolkit.TableRow;
import fission.global.AppReqBlock;
import role.ClientRole;
import java.util.*;

public class ClientDetailTableModel extends TableModel
{
    public static final String COLUMN_CONTRACT_NUMBER = "Contract Number";
    public static final String COLUMN_QUAL_NONQUAL = "&nbsp;Qual/NonQual&nbsp;";
    public static final String COLUMN_EFFECTIVE_DATE= "Effective Date";
    public static final String COLUMN_STATUS = "Status";
    public static final String COLUMN_ISSUE_STATE = "Issue State";
    public static final String COLUMN_ROLE_TYPE_CT = "Role";
    public static final String COLUMN_SEGMENT_NAME_CT = "Segment";
    public static final String COLUMN_INCOME_DATE = "Income Date";

    private ClientDetail clientDetail;

    private static final String[] COLUMN_NAMES = {COLUMN_CONTRACT_NUMBER, COLUMN_QUAL_NONQUAL, COLUMN_EFFECTIVE_DATE,
                                                  COLUMN_STATUS, COLUMN_ISSUE_STATE, COLUMN_ROLE_TYPE_CT, 
                                                  COLUMN_SEGMENT_NAME_CT, COLUMN_INCOME_DATE};


    public ClientDetailTableModel(ClientDetail clientDetail, AppReqBlock appReqBlock)
    {
        super(appReqBlock);

        this.clientDetail = clientDetail;

        getColumnNames().addAll(Arrays.asList(COLUMN_NAMES));
    }

    /**
     * Builds the superset TableModel driven by the Segment
     */
    protected void buildTableRows()
    {
        ClientRole[] clientRoles = new role.ClientRole[0];
        if (clientDetail != null)
        {
            clientRoles = ClientRole.findBy_ClientDetail_Not_Like_RoleTypeCT(clientDetail, ClientRole.ROLETYPECT_AGENT);
        }

        for (int i = 0; i < clientRoles.length; i++)
        {
            ClientRole clientRole = clientRoles[i];

            ContractClient[] contractClients = ContractClient.findByClientRole(clientRole);

            for (int j = 0; j < contractClients.length; j++)
            {
                TableRow tableRow = new ClientDetailTableRow(contractClients[j]);
                
                // Add current records (status 'P') to table
                String overrideStatus = contractClients[j].getOverrideStatus();
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
}
