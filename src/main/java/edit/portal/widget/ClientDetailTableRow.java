/*
 * User: sprasad
 * Date: May 24, 2005
 * Time: 1:35:01 PM
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package edit.portal.widget;

import contract.*;
import edit.portal.widgettoolkit.*;
import fission.utility.*;


public class ClientDetailTableRow extends TableRow
{
    private ContractClient contractClient;

    public ClientDetailTableRow(ContractClient contractClient)
    {
        super();

        this.contractClient = contractClient;

        populateCellValues();
    }

    /**
     * Maps the values of ClientRole to the TableRow.
     */
    private void populateCellValues()
    {
        Segment segment = contractClient.getSegment();

        Payout payout = segment.getPayout();

        getCellValues().put(ClientDetailTableModel.COLUMN_CONTRACT_NUMBER, segment.getContractNumber());
        getCellValues().put(ClientDetailTableModel.COLUMN_QUAL_NONQUAL, segment.getQualNonQualCT());

        String effectiveDate = segment.getEffectiveDate() == null ? "" : DateTimeUtil.formatEDITDateAsMMDDYYYY(segment.getEffectiveDate());
        getCellValues().put(ClientDetailTableModel.COLUMN_EFFECTIVE_DATE, effectiveDate);
        getCellValues().put(ClientDetailTableModel.COLUMN_STATUS, segment.getStatus());

        getCellValues().put(ClientDetailTableModel.COLUMN_ISSUE_STATE, segment.getIssueStateCT());
        getCellValues().put(ClientDetailTableModel.COLUMN_ROLE_TYPE_CT, this.contractClient.getClientRole().getRoleTypeCT());
        getCellValues().put(ClientDetailTableModel.COLUMN_SEGMENT_NAME_CT, segment.getSegmentNameCT());

        String incomeDate = "";

        if (payout != null)
        {
            incomeDate = payout.getNextPaymentDate() == null ? "" : DateTimeUtil.formatEDITDateAsMMDDYYYY(payout.getNextPaymentDate());
        }

        getCellValues().put(ClientDetailTableModel.COLUMN_INCOME_DATE, incomeDate);
    }


    /**
     * @see edit.portal.widgettoolkit.TableRow#getRowId()
     * @return
     */
    public String getRowId()
    {
        return contractClient.getSegment().getSegmentPK().toString();
    }
}
