/*
 * User: cgleason
 * Date: Jan 6, 2006
 * Time: 11:21:30 AM
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement. 
 */
package edit.portal.widget;

import edit.portal.widgettoolkit.*;
import client.*;


public class CaseClientSearchTableRow extends TableRow
{
    private ClientDetail clientDetail;

    public CaseClientSearchTableRow(ClientDetail clientDetail)
    {
        super();

        this.clientDetail = clientDetail;

        populateCellValues();
    }

    /**
     * Builds the Map of cell values with cell name/value pairs.
     */
    private void populateCellValues()
    {
        String name = null;

        if (clientDetail.isCorporate())
        {
            name = clientDetail.getCorporateName();
        }
        else
        {
            name = clientDetail.getLastName() + ", " + clientDetail.getFirstName();
        }

        String clientStatus = clientDetail.getStatusCT();

        String taxId = clientDetail.getTaxIdentification();

        getCellValues().put(CaseClientSearchTableModel.COLUMN_NAME, name);
        getCellValues().put(CaseClientSearchTableModel.COLUMN_TAX_ID, taxId);
        getCellValues().put(CaseClientSearchTableModel.COLUMN_CLIENT_STATUS, clientStatus);
    }

    /**
     * @see edit.portal.widgettoolkit.TableRow#getRowId()
     * @return
     */
    public String getRowId()
    {
        return clientDetail.getClientDetailPK().toString();
    }
}
