/*
 * User: cgleason
 * Date: Jan 6, 2006
 * Time: 11:18:03 AM
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

import java.util.Arrays;


public class CaseClientSearchTableModel extends TableModel
{
    public static final String COLUMN_NAME = "Name";
    public static final String COLUMN_TAX_ID = "Tax Id";
    public static final String COLUMN_CLIENT_STATUS = "Client Status";

    private static final String[] COLUMN_NAMES = {COLUMN_NAME, COLUMN_TAX_ID, COLUMN_CLIENT_STATUS};


    public CaseClientSearchTableModel(AppReqBlock appReqBlock)
    {
        super(appReqBlock);

        getColumnNames().addAll(Arrays.asList(COLUMN_NAMES));
    }

    /**
     * Builds the superset TableModel
     */
    protected void buildTableRows()
    {
        String name = Util.initString((String) getAppReqBlock().getReqParm("caseName"), null);
        String taxId = Util.initString((String) getAppReqBlock().getReqParm("taxId"), null);
        ClientDetail savedQuickAddClientDetail = (ClientDetail) getAppReqBlock().getFromRequestScope("savedQuickAddClientDetail");

        ClientDetail[] clientDetails = null;

        if (savedQuickAddClientDetail != null)
        {
            //  Show the client just added via Quick Add
            clientDetails = new ClientDetail[] {savedQuickAddClientDetail};
        }
        else if (name != null)
        {
            //  Show the clients matching the name entered in the search field
            clientDetails = ClientDetail.findBy_PartialName(name);
        }
        else if (taxId != null)
        {
            //  Show the clients matching the taxId entered in the search field
            clientDetails = ClientDetail.findBy_TaxIdentification(taxId);
        }


        clientDetails = (ClientDetail[]) Util.sortObjects(clientDetails, new String [] {"getClientDetailPK"});

        if (clientDetails != null)
        {
            for (int i = 0; i < clientDetails.length; i++)
            {
                ClientDetail clientDetail = clientDetails[i];

                TableRow tableRow = new CaseClientSearchTableRow(clientDetail);

                if (tableRow.getRowId().equals(this.getSelectedRowId()))
                {
                    tableRow.setRowStatus(TableRow.ROW_STATUS_SELECTED);
                }

                super.addRow(tableRow);
            }
        }
    }
}
