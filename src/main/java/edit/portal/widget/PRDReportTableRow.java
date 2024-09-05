/*
 * User: dlataille
 * Date: May 2, 2007
 * Time: 10:59:15 AM
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
import contract.Segment;

public class PRDReportTableRow extends TableRow
{
    public ContractGroup groupContractGroup;
    public PayrollDeduction pd;

    public PRDReportTableRow(PayrollDeduction pd)
    {
        super();

        this.pd = pd;

        populateCellValues();
    }

    /**
     * Maps the values of payrollDeduction and groupContractGroup to the TableRow.
     */
    private void populateCellValues()
    {
        Segment segment = pd.getSegment();
        String contractNumber = segment.getContractNumber();

        EDITDate extDate = pd.getPRDExtractDate();

        String extractDate = "";

        if (extDate != null)
        {
            extractDate = DateTimeUtil.formatEDITDateAsMMDDYYYY(extDate);
        }

        String deductionAmount = pd.getDeductionAmount().toString();
        String cellValueForDeductionAmount = "<script>document.write(formatAsCurrency(" + deductionAmount + "))</script>";

        getCellValues().put(PRDReportTableModel.COLUMN_CONTRACT_NUMBER, contractNumber);

        getCellValues().put(PRDReportTableModel.COLUMN_EXTRACT_DATE, extractDate);

        getCellValues().put(PRDReportTableModel.COLUMN_DEDUCTION_AMOUNT, cellValueForDeductionAmount);
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