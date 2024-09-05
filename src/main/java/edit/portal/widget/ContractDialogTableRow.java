/*
 * User: sprasad
 * Date: May 16, 2005
 * Time: 12:51:09 PM
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package edit.portal.widget;

import client.ClientDetail;
import contract.ContractClient;
import contract.ContractClientAllocation;
import edit.common.EDITBigDecimal;
import edit.portal.widgettoolkit.TableRow;
import role.ClientRole;



public class ContractDialogTableRow extends TableRow
{
    private ContractClient contractClient;


    public ContractDialogTableRow(ContractClient contractClient)
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

//        String firstName = clientDetail.getFirstName();
//
//        String lastName = clientDetail.getLastName();

        String name = null;
        if (clientDetail.getLastName() == null)
        {
            name = clientDetail.getCorporateName();
        }
        else
        {
            name = clientDetail.getLastName() + ", " + clientDetail.getFirstName();
        }

        String roleTypeCT = clientRole.getRoleTypeCT();

        String relationship = contractClient.getRelationshipToInsuredCT();

        String clientStatus = clientDetail.getStatusCT();

        ContractClientAllocation contractClientAllocation = contractClient.getContractClientAllocation();

        EDITBigDecimal allocationPercent = null;

        if (contractClientAllocation != null)
        {
            allocationPercent = contractClientAllocation.getAllocationPercent();
        }

//        columnValues.put(ContractDialogTableModel.COLUMN_FIRST_NAME, firstName);
//        columnValues.put(ContractDialogTableModel.COLUMN_LAST_NAME, lastName);
        getCellValues().put(ContractDialogTableModel.COLUMN_NAME, name);
        getCellValues().put(ContractDialogTableModel.COLUMN_ROLE_TYPE_CT, roleTypeCT);
        getCellValues().put(ContractDialogTableModel.COLUMN_RELATIONSHIP, relationship);
        getCellValues().put(ContractDialogTableModel.COLUMN_CLIENT_STATUS, clientStatus);
        getCellValues().put(ContractDialogTableModel.COLUMN_ALLOC_PERCENT, allocationPercent == null ? "" : allocationPercent.trim());
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
