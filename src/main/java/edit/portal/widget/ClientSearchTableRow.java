/*
 * User: sprasad
 * Date: Jun 2, 2005
 * Time: 9:39:15 AM
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package edit.portal.widget;

import edit.portal.widgettoolkit.TableRow;
import edit.common.EDITDate;
import client.ClientDetail;
import fission.utility.*;


public class ClientSearchTableRow extends TableRow
{
    private ClientDetail clientDetail;


    public ClientSearchTableRow(ClientDetail clientDetail)
    {
        super();

        this.clientDetail = clientDetail;

        populateCellValues();
    }

    /**
     * Maps the values of Client to the TableRow.
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
            String middleName = Util.initString(clientDetail.getMiddleName(), "");
            String namePrefix = Util.initString(clientDetail.getNamePrefix(), "");
            String nameSuffix = Util.initString(clientDetail.getNameSuffix(), "");
            if (!middleName.equals(""))
            {
                name = name + ", "  + middleName;
            }
            if (!namePrefix.equals(""))
            {
                name = name + ", " + namePrefix;
            }
            if (!nameSuffix.equals(""))
            {
                name = name + ", " + nameSuffix;
            }
        }

        String cellValueForName = "<a href =\"javascript:showClientAfterSearch('" + getRowId() + "')\">" + name + "</a>";

        EDITDate birthDate = clientDetail.getBirthDate();

        String clientStatus = clientDetail.getStatusCT();

        EDITDate deathDate = clientDetail.getDateOfDeath();

        String taxId = clientDetail.getTaxIdentification();

        getCellValues().put(ClientSearchTableModel.COLUMN_NAME, cellValueForName);
        getCellValues().put(ClientSearchTableModel.COLUMN_TAX_ID, taxId);
        getCellValues().put(ClientSearchTableModel.COLUMN_DATE_OF_BIRTH, birthDate == null ? null : DateTimeUtil.formatEDITDateAsMMDDYYYY(birthDate));
        getCellValues().put(ClientSearchTableModel.COLUMN_CLIENT_STATUS, clientStatus);
        getCellValues().put(ClientSearchTableModel.COLUMN_DATE_OF_DEATH, deathDate == null ? null : DateTimeUtil.formatEDITDateAsMMDDYYYY(deathDate));
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
