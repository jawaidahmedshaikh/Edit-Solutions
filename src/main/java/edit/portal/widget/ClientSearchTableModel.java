/*
 * User: sprasad
 * Date: Jun 2, 2005
 * Time: 9:38:45 AM
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package edit.portal.widget;

import client.ClientDetail;
import edit.portal.widgettoolkit.TableModel;
import edit.portal.widgettoolkit.TableRow;
import fission.global.AppReqBlock;
import fission.utility.Util;
import role.ClientRole;

import java.util.*;



public class ClientSearchTableModel extends TableModel
{
    public static final String COLUMN_NAME = "Name";
    public static final String COLUMN_TAX_ID = "Tax Id";
    public static final String COLUMN_DATE_OF_BIRTH = "Date Of Birth";
    public static final String COLUMN_CLIENT_STATUS = "Client Status";
    public static final String COLUMN_DATE_OF_DEATH = "Date Of Death";

    private static final String[] COLUMN_NAMES = {COLUMN_NAME, COLUMN_TAX_ID, COLUMN_DATE_OF_BIRTH,
                                                  COLUMN_CLIENT_STATUS, COLUMN_DATE_OF_DEATH};

    public ClientSearchTableModel(AppReqBlock appReqBlock)
    {
        super(appReqBlock);

        getColumnNames().addAll(Arrays.asList(COLUMN_NAMES));
    }

    /**
     * Builds the superset TableModel
     */
    protected void buildTableRows()
    {
        String name = Util.initString((String) getAppReqBlock().getHttpServletRequest().getParameter("name"), null);
        String taxId = Util.initString((String) getAppReqBlock().getHttpServletRequest().getParameter("taxId"), null);

        ClientDetail[] clientDetails = null;

        if (name != null)
        {
            clientDetails = ClientDetail.findBy_PartialName_Not_Like_RoleTypeCT(name, ClientRole.ROLETYPECT_AGENT);
        }
        else if (taxId != null)
        {
            clientDetails = ClientDetail.findBy_TaxId_Not_Like_RoleTypeCT(taxId, ClientRole.ROLETYPECT_AGENT);
        }

        clientDetails = (ClientDetail[]) Util.sortObjects(clientDetails, new String [] {"getLastName", "getCorporateName"});

        if (clientDetails != null)
        {
            for (int i = 0; i < clientDetails.length; i++)
            {
                ClientDetail clientDetail = clientDetails[i];

                TableRow tableRow = new ClientSearchTableRow(clientDetail);

                if (tableRow.getRowId().equals(this.getSelectedRowId()))
                {
                    tableRow.setRowStatus(TableRow.ROW_STATUS_SELECTED);
                }

                super.addRow(tableRow);
            }
        }
    }
}
