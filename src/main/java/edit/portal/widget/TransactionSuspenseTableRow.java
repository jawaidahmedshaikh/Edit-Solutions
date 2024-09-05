/*
 * User: dlataill
 * Date: Aug 9, 2005
 * Time: 8:44:10 AM
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package edit.portal.widget;

import edit.portal.widgettoolkit.TableRow;

import event.Suspense;
import event.CashBatchContract;
import fission.utility.*;


public class TransactionSuspenseTableRow extends TableRow
{
    private Suspense suspense;

    public TransactionSuspenseTableRow(Suspense suspense)
    {
        super();

        this.suspense = suspense;

        populateCellValues();
    }

    /**
     * Maps the values of Deposits to the TableRow
     */
    private void populateCellValues()
    {
        CashBatchContract cashBatchContract = suspense.getCashBatchContract();
        String releaseInd = "";
        if (cashBatchContract != null)
        {
            releaseInd = cashBatchContract.getReleaseIndicator();
        }

        String exchangeCompany = Util.initString(suspense.getExchangeCompany(), "");

        String exchangePolicy = Util.initString(suspense.getExchangePolicy(), "");

        String suspenseAmount = suspense.getSuspenseAmount().toString();
        String cellValueForAmount = "<script>document.write(formatAsCurrency(" + suspenseAmount + "))</script>";

        String effectiveDate = DateTimeUtil.formatEDITDateAsMMDDYYYY(suspense.getEffectiveDate());

        getCellValues().put(TransactionSuspenseTableModel.COLUMN_EXCHANGE_COMPANY, exchangeCompany);
        getCellValues().put(TransactionSuspenseTableModel.COLUMN_EXCHANGE_POLICY, exchangePolicy);
        getCellValues().put(TransactionSuspenseTableModel.COLUMN_SUSPENSE_AMOUNT, cellValueForAmount);
        getCellValues().put(TransactionSuspenseTableModel.COLUMN_EFFECTIVE_DATE, effectiveDate);
        getCellValues().put(TransactionSuspenseTableModel.COLUMN_RELEASE_IND, releaseInd);
    }

    /**
     * @see edit.portal.widgettoolkit.TableRow#getRowId()
     * @return
     */
    public String getRowId()
    {
        return suspense.getSuspensePK().toString();
    }
}
