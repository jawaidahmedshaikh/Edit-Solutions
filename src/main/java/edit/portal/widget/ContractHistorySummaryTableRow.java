/*
 * User: cgleason
 * Date: Feb 22, 2006
 * Time: 9:28:31 AM
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package edit.portal.widget;

import edit.portal.widgettoolkit.*;
import edit.common.CodeTableWrapper;

import fission.utility.*;
import contract.*;

public class ContractHistorySummaryTableRow extends TableRow
{
    HistoryFilterRow historyFilterRow;


    public ContractHistorySummaryTableRow(HistoryFilterRow historyFilterRow)
    {
        this.historyFilterRow = historyFilterRow;

        populateCellValues();
    }

   /**
     * Maps the values of History Filter to the TableRow.
     */
    private void populateCellValues()
    {
        String acctPendingInd = Util.initString(historyFilterRow.getAccountingPendingStatus(), "N");
        getCellValues().put(ContractHistorySummaryTableModel.COLUMN_ACCT_PENDING, acctPendingInd);

        String effectiveDate = historyFilterRow.getEffectiveDate() == null ? "" : DateTimeUtil.formatEDITDateAsMMDDYYYY(historyFilterRow.getEffectiveDate());
        getCellValues().put(ContractHistorySummaryTableModel.COLUMN_EFFECTIVE_DATE, effectiveDate);

        String processDate = historyFilterRow.getProcessDate() == null ? "" : DateTimeUtil.formatEDITDateAsMMDDYYYY(historyFilterRow.getProcessDate());
        getCellValues().put(ContractHistorySummaryTableModel.COLUMN_PROCESS_DATE, processDate);

        String grossAmount = historyFilterRow.getGrossAmount().toString();
        String cellValueForGrossAmount = "<script>document.write(formatAsCurrency(" + grossAmount + "))</script>";
        String checkAmount = historyFilterRow.getCheckAmount().toString();
        String cellValueForCheckAmount = "<script>document.write(formatAsCurrency(" + checkAmount + "))</script>";

        getCellValues().put(ContractHistorySummaryTableModel.COLUMN_TRAN_TYPE, CodeTableWrapper.getSingleton().getCodeDescByCodeTableNameAndCode("TRXTYPE", historyFilterRow.getTransactionTypeCT()));
        getCellValues().put(ContractHistorySummaryTableModel.COLUMN_SEQUENCE, historyFilterRow.getSequenceNumber() + "");
        getCellValues().put(ContractHistorySummaryTableModel.COLUMN_STATUS, Util.initString(historyFilterRow.getStatus(), ""));
        getCellValues().put(ContractHistorySummaryTableModel.COLUMN_GROSS_AMOUNT, cellValueForGrossAmount);
        getCellValues().put(ContractHistorySummaryTableModel.COLUMN_CHECK_AMOUNT, cellValueForCheckAmount);
        getCellValues().put(ContractHistorySummaryTableModel.COLUMN_OPTION, historyFilterRow.getOptionCodeCT());
    }


    /**
     * @see edit.portal.widgettoolkit.TableRow#getRowId()
     * @return
     */
    public String getRowId()
    {
        return historyFilterRow.getKey();
    }
}
