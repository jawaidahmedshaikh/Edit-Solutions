/*
 * User: cgleason
 * Date: Jan 12, 2006
 * Time: 11:40:36 AM
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package edit.portal.widget;

import edit.portal.widgettoolkit.*;
import client.*;

import contract.*;
import group.*;


public class CaseSearchTableRow extends TableRow
{
    private ContractGroup caseDetail;
    private ClientDetail clientDetail;


    public CaseSearchTableRow(ContractGroup caseDetail, ClientDetail clientDetail)
    {
        super();

        this.caseDetail = caseDetail;
        
        this.clientDetail = clientDetail;

        populateCellValues();
    }

    /**
     * Maps the values of Client to the TableRow.
     */
    private void populateCellValues()
    {
        String caseNumber = caseDetail.getContractGroupNumber();
        
        String name = null;

        if (clientDetail.isCorporate())
        {
            name = clientDetail.getCorporateName();
        }
        else
        {
            name = clientDetail.getLastName() + ", " + clientDetail.getFirstName();
        }

        getCellValues().put(CaseSearchTableModel.COLUMN_NAME, name);
        
        getCellValues().put(CaseSearchTableModel.COLUMN_CASE_NUMBER, caseNumber);
    }

    /**
     * @see edit.portal.widgettoolkit.TableRow#getRowId()
     * @return
     */
    public String getRowId()
    {
        return caseDetail.getContractGroupPK().toString();
    }
}
