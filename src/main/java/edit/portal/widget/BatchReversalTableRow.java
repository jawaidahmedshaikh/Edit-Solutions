package edit.portal.widget;

import edit.portal.widgettoolkit.*;
import edit.common.CodeTableWrapper;

import fission.utility.*;
import contract.*;

public class BatchReversalTableRow extends TableRow
{
    HistoryFilterRow historyFilterRow;


    public BatchReversalTableRow(HistoryFilterRow historyFilterRow)
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
        getCellValues().put(BatchReversalTableModel.COLUMN_ACCT_PENDING, acctPendingInd);

        String linkedInd = Util.initString(historyFilterRow.getLinkedInd(), "");
        getCellValues().put(BatchReversalTableModel.COLUMN_LINKED_TRX, linkedInd);

        String effectiveDate = historyFilterRow.getEffectiveDate() == null ? "" : DateTimeUtil.formatEDITDateAsMMDDYYYY(historyFilterRow.getEffectiveDate());
        getCellValues().put(BatchReversalTableModel.COLUMN_EFFECTIVE_DATE, effectiveDate);

        String processDate = historyFilterRow.getProcessDate() == null ? "" : DateTimeUtil.formatEDITDateAsMMDDYYYY(historyFilterRow.getProcessDate());
        getCellValues().put(BatchReversalTableModel.COLUMN_PROCESS_DATE, processDate);

        String grossAmount = historyFilterRow.getGrossAmount().toString();
        String cellValueForGrossAmount = "<script>document.write(formatAsCurrency(" + grossAmount + "))</script>";
        String checkAmount = historyFilterRow.getCheckAmount().toString();
        String cellValueForCheckAmount = "<script>document.write(formatAsCurrency(" + checkAmount + "))</script>";

        getCellValues().put(BatchReversalTableModel.COLUMN_TRAN_TYPE, CodeTableWrapper.getSingleton().getCodeDescByCodeTableNameAndCode("TRXTYPE", historyFilterRow.getTransactionTypeCT()));
        getCellValues().put(BatchReversalTableModel.COLUMN_SEQUENCE, historyFilterRow.getSequenceNumber() + "");
        getCellValues().put(BatchReversalTableModel.COLUMN_STATUS, Util.initString(historyFilterRow.getStatus(), ""));
        getCellValues().put(BatchReversalTableModel.COLUMN_GROSS_AMOUNT, cellValueForGrossAmount);
        getCellValues().put(BatchReversalTableModel.COLUMN_CHECK_AMOUNT, cellValueForCheckAmount);
        getCellValues().put(BatchReversalTableModel.COLUMN_OPTION, historyFilterRow.getOptionCodeCT());
    }


    /**
     * @see edit.portal.widgettoolkit.TableRow#getRowId()
     * @return
     */
    public String getRowId()
    {
        return String.valueOf(historyFilterRow.getEDITTrxPK());
    }
}
