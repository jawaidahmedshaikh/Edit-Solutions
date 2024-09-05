/*
 * User: sprasad
 * Date: May 17, 2005
 * Time: 2:14:13 PM
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package edit.portal.widget;

import client.ClientDetail;
import contract.ContractClient;
import edit.common.EDITDate;
import edit.portal.widgettoolkit.TableRow;
import role.ClientRole;
import fission.utility.*;



public class ExistingBeneficiariesTableRow extends TableRow
{
    private ContractClient contractClient;


    public ExistingBeneficiariesTableRow(ContractClient contractClient)
    {
        super();

        this.contractClient = contractClient;

        populateCellValues();
    }

    /**
     * Maps the values of ContractClient to the TableRow.
     */
    private void populateCellValues()
    {
        ClientRole clientRole = contractClient.getClientRole();

        ClientDetail clientDetail = clientRole.getClientDetail();

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

        String roleTypeCT = clientRole.getRoleTypeCT();

        getCellValues().put(ExistingBeneficiariesTableModel.COLUMN_TAX_ID, taxId);
        getCellValues().put(ExistingBeneficiariesTableModel.COLUMN_NAME, name);
        getCellValues().put(ExistingBeneficiariesTableModel.COLUMN_DOB, birthDate == null ? "" : DateTimeUtil.formatEDITDateAsMMDDYYYY(birthDate));
        getCellValues().put(ExistingBeneficiariesTableModel.COLUMN_ROLE, roleTypeCT);
    }

    /**
     * @see edit.portal.widgettoolkit.TableRow#getRowId()
     * @return
     */
    public String getRowId()
    {
        return contractClient.getContractClientPK().toString();
    }
}
