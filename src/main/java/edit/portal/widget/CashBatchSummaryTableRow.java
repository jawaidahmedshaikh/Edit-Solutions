/*
 * User: dlataill
 * Date: Jul 19, 2005
 * Time: 8:29:59 AM
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement
 */
package edit.portal.widget;

import edit.portal.widgettoolkit.TableRow;
import event.CashBatchContract;
import fission.utility.*;



public class CashBatchSummaryTableRow extends TableRow
{
    private CashBatchContract cashBatchContract;

    private String lockedIcon = "<img src=\"/PORTAL/common/images/grnball.gif\">";
    private String unlockedIcon = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";

    public CashBatchSummaryTableRow(CashBatchContract cashBatchContract)
    {
        this.cashBatchContract = cashBatchContract;

        populateCellValues();
    }

    /**
     * Maps the values of CashBatchContract to the TableRow
     */
    private void populateCellValues()
    {
        String batchId = cashBatchContract.getBatchID();

        String creationDate = DateTimeUtil.formatEDITDateAsMMDDYYYY(cashBatchContract.getCreationDate());

        String amount = cashBatchContract.getAmount().toString();
        String cellValueForAmount = "<script>document.write(formatAsCurrency(" + amount + "))</script>";

        String totalNumberOfItems = cashBatchContract.getSuspenseCount() + "";

        String operator = cashBatchContract.getCreationOperator();

        if (cashBatchContract.getReleaseIndicator().equalsIgnoreCase(CashBatchContract.RELEASE_INDICATOR_RELEASE))
        {
            getCellValues().put(CashBatchSummaryTableModel.COLUMN_BATCH_ID, lockedIcon + " " + batchId);
        }
        else
        {
            getCellValues().put(CashBatchSummaryTableModel.COLUMN_BATCH_ID, unlockedIcon + batchId);
        }
        getCellValues().put(CashBatchSummaryTableModel.COLUMN_CREATION_DATE, creationDate);
        getCellValues().put(CashBatchSummaryTableModel.COLUMN_AMOUNT, cellValueForAmount);
        getCellValues().put(CashBatchSummaryTableModel.COLUMN_NUMBER_OF_ITEMS, totalNumberOfItems);
        getCellValues().put(CashBatchSummaryTableModel.COLUMN_OPERATOR, operator);
        getCellValues().put(CashBatchSummaryTableModel.COLUMN_GROUP_NUMBER, cashBatchContract.getGroupNumber());
        getCellValues().put(CashBatchSummaryTableModel.COLUMN_DUE_DATE, DateTimeUtil.formatEDITDateAsMMDDYYYY(cashBatchContract.getDueDate()));
    }

    /**
     * @see edit.portal.widgettoolkit.TableRow#getRowId()
     * @return
     */
    public String getRowId()
    {
        return cashBatchContract.getCashBatchContractPK().toString();
    }
}
