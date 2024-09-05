/*
 * User: sprasad
 * Date: May 19, 2005
 * Time: 9:46:34 AM
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package edit.portal.widget;

import client.ClientDetail;
import edit.portal.widgettoolkit.TableModel;
import edit.portal.widgettoolkit.TableRow;

import java.util.*;

import fission.utility.Util;
import role.ClientRole;
import fission.global.AppReqBlock;


public class ProspectiveBeneficiariesTableModel extends TableModel
{
    public static final String COLUMN_TAX_ID = "Tax ID";
    public static final String COLUMN_NAME= "Name";
    public static final String COLUMN_DOB = "DOB";

    private static final String[] COLUMN_NAMES = {COLUMN_TAX_ID, COLUMN_NAME, COLUMN_DOB};


    public ProspectiveBeneficiariesTableModel(AppReqBlock appReqBlock)
    {
        super(appReqBlock);

        getColumnNames().addAll(Arrays.asList(COLUMN_NAMES));
    }

    /**
     * Builds the superset TableModel
     */
    public void buildTableRows()
    {
        String name = Util.initString((String) this.getAppReqBlock().getHttpServletRequest().getParameter("name"), null);
        String taxId = Util.initString((String) this.getAppReqBlock().getHttpServletRequest().getParameter("taxId"), null);

        ClientDetail[] clientDetails = null;

        if (name != null)
        {
            clientDetails = ClientDetail.findBy_PartialName_Not_Like_RoleTypeCT(name, ClientRole.ROLETYPECT_AGENT);
        }
        else if (taxId != null)
        {
            clientDetails = ClientDetail.findBy_TaxId_Not_Like_RoleTypeCT(taxId, ClientRole.ROLETYPECT_AGENT);
        }

        clientDetails = (ClientDetail[]) Util.sortObjects(clientDetails, new String [] {"getClientDetailPK"});

        if (clientDetails != null)
        {
            for (int i = 0; i < clientDetails.length; i++)
            {
                ClientDetail clientDetail = clientDetails[i];

                TableRow tableRow = new ProspectiveBeneficiariesTableRow(clientDetail);

                if (tableRow.getRowId().equals(this.getSelectedRowId()))
                {
                    tableRow.setRowStatus(TableRow.ROW_STATUS_SELECTED);
                }

                super.addRow(tableRow);
            }
        }
    }
}
