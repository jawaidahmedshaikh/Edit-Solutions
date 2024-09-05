/*
 * User: dlataill
 * Date: Aug 3, 2005
 * Time: 2:15:47 PM
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement
 */
package edit.portal.widget;

import edit.portal.widgettoolkit.TableRow;

import contract.Deposits;


public class CashBatchDetailTableRow extends TableRow
{
    private Deposits deposits;

    public CashBatchDetailTableRow(Deposits deposits)
    {
        super();

        this.deposits = deposits;

        populateCellValues();
    }

    /**
     * Maps the values of Deposits to the TableRow
     */
    private void populateCellValues()
    {
        String exchangeCompany = deposits.getOldCompany();

        String exchangePolicy = deposits.getOldPolicyNumber();

        String anticipatedAmount = deposits.getAnticipatedAmount().toString();
        String cellValueForAmount = "<script>document.write(formatAsCurrency(" + anticipatedAmount + "))</script>";

        getCellValues().put(CashBatchDetailTableModel.COLUMN_EXCHANGE_COMPANY, exchangeCompany);
        getCellValues().put(CashBatchDetailTableModel.COLUMN_EXCHANGE_POLICY, exchangePolicy);
        getCellValues().put(CashBatchDetailTableModel.COLUMN_ANTICIPATED_AMOUNT, cellValueForAmount);
    }

    /**
     * @see edit.portal.widgettoolkit.TableRow#getRowId()
     * @return
     */
    public String getRowId()
    {
        return deposits.getDepositsPK().toString();
    }
}
