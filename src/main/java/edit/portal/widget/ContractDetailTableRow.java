/*
 * User: sprasad
 * Date: May 6, 2005
 * Time: 2:35:05 PM
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package edit.portal.widget;

import client.ClientDetail;
import contract.ContractClient;
import contract.ContractClientAllocation;
import edit.common.EDITBigDecimal;
import edit.common.EDITDate;
import edit.portal.widgettoolkit.TableRow;
import role.ClientRole;
import fission.utility.*;




public class ContractDetailTableRow extends TableRow
{
    private ContractClient contractClient;


    public ContractDetailTableRow(ContractClient contractClient)
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
        ClientRole clientRole = ClientRole.findBy_ContractClient(contractClient);

        ClientDetail clientDetail = clientRole.getClientDetail();

        String taxId = clientDetail.getTaxIdentification();

        String name = null;

        if (clientDetail.isCorporate())
        {
            name = clientDetail.getCorporateName();

            if (name.length() > 20)
            {
                name = name.substring(0, 20);
            }
        }
        else
        {
            name = clientDetail.getLastName() + ", " + clientDetail.getFirstName();
        }

        EDITDate birthDate = clientDetail.getBirthDate();

        String roleTypeCT = clientRole.getRoleTypeCT();

        ContractClientAllocation contractClientAllocation = contractClient.getContractClientAllocation();

        EDITBigDecimal allocationPercent = null;

        if (contractClientAllocation != null)
        {
            allocationPercent = contractClientAllocation.getAllocationPercent();
        }

        getCellValues().put(ContractDetailDoubleTableModel.COLUMN_TAX_ID, taxId);
        getCellValues().put(ContractDetailDoubleTableModel.COLUMN_NAME, name);
        getCellValues().put(ContractDetailDoubleTableModel.COLUMN_BIRTHDATE, (birthDate == null ? "" : DateTimeUtil.formatEDITDateAsMMDDYYYY(birthDate)));
        getCellValues().put(ContractDetailDoubleTableModel.COLUMN_ROLETYPECT, roleTypeCT);
        getCellValues().put(ContractDetailDoubleTableModel.COLUMN_ALLOC_PERCENT, (allocationPercent == null ? "" : allocationPercent.trim()));
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
