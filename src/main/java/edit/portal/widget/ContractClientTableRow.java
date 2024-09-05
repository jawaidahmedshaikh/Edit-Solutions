/*
 * User: gfrosti
 * Date: Mar 22, 2005
 * Time: 3:42:44 PM
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package edit.portal.widget;

import contract.ContractClient;
import contract.Segment;
import edit.portal.widgettoolkit.TableRow;
import role.ClientRole;



public class ContractClientTableRow extends TableRow
{
    private ContractClient contractClient;


    public ContractClientTableRow(ContractClient contractClient)
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
        Segment segment = Segment.findBy_ContractClient(contractClient);

        ClientRole clientRole = ClientRole.findBy_ContractClient(contractClient);

        String contractNumber = segment.getContractNumber();

        String segmentNameCT = segment.getSegmentNameCT();

        String segmentStatusCT = segment.getSegmentStatusCT();

        String roleTypeCT = clientRole.getRoleTypeCT();

        getCellValues().put(ContractClientDoubleTableModel.COLUMN_CONTRACT_NUMBER, contractNumber);
        getCellValues().put(ContractClientDoubleTableModel.COLUMN_SEGMENTSTATUSCT, segmentStatusCT);
        getCellValues().put(ContractClientDoubleTableModel.COLUMN_ROLETYPECT, roleTypeCT);
        getCellValues().put(ContractClientDoubleTableModel.COLUMN_SEGMENTNAMECT, segmentNameCT);
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
