/*
 * User: dlataille
 * Date: May 2, 2007
 * Time: 8:43:15 PM
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package edit.portal.widget;

import edit.portal.widgettoolkit.TableRow;
import edit.common.EDITDate;

import fission.utility.*;
import group.ContractGroup;
import group.PayrollDeduction;

public class CaseHistoryTableRow extends TableRow
{
    public ContractGroup groupContractGroup;
    public PayrollDeduction pd;

    public CaseHistoryTableRow(PayrollDeduction pd, ContractGroup groupContractGroup)
    {
        super();

        this.pd = pd;

        this.groupContractGroup = groupContractGroup;

        populateCellValues();
    }

    /**
     * Maps the values of payrollDeduction and groupContractGroup to the TableRow.
     */
    private void populateCellValues()
    {
        EDITDate effDate = pd.getPRDExtractDate();

        String effectiveDate = "";

        if (effDate != null)
        {
            effectiveDate = DateTimeUtil.formatEDITDateAsMMDDYYYY(effDate);
        }

        String groupName = groupContractGroup.getOwnerClient().getName();

        getCellValues().put(CaseHistoryTableModel.COLUMN_EFFECTIVE_DATE, effectiveDate);

        getCellValues().put(CaseHistoryTableModel.COLUMN_TRANSACTION_TYPE, "PRD");

        getCellValues().put(GroupSummaryTableModel.COLUMN_GROUP_NAME, groupName);
    }

    /**
     * @see edit.portal.widgettoolkit.TableRow#getRowId()
     * @return
     */
    public String getRowId()
    {
        return pd.getPayrollDeductionPK().toString();
    }
}