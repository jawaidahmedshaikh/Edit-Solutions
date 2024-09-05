/*
 * User: sprasad
 * Date: May 19, 2005
 * Time: 9:46:51 AM
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



public class ProspectiveBeneficiariesTableRow extends TableRow
{
    private ClientDetail clientDetail;

    public ProspectiveBeneficiariesTableRow(ClientDetail clientDetail)
    {
        super();

        this.clientDetail = clientDetail;

        populateCellValues();
    }

    /**
     * Maps the values of ContractClient to the TableRow.
     */
    private void populateCellValues()
    {
        String taxId = clientDetail.getTaxIdentification();

        String name = null;

        if (clientDetail.isCorporate())
        {
            name = clientDetail.getCorporateName();
        }
        else
        {
            name = clientDetail.getLastName() + ", " + clientDetail.getFirstName();
        }

        EDITDate birthDate = clientDetail.getBirthDate();

        getCellValues().put(ProspectiveBeneficiariesTableModel.COLUMN_TAX_ID, taxId);
        getCellValues().put(ProspectiveBeneficiariesTableModel.COLUMN_NAME, name);
        getCellValues().put(ProspectiveBeneficiariesTableModel.COLUMN_DOB, birthDate == null ? "" : DateTimeUtil.formatEDITDateAsMMDDYYYY(birthDate));
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
