package edit.portal.widget;

import edit.portal.widgettoolkit.*;
import edit.common.CodeTableWrapper;

import fission.utility.*;
import contract.*;
import event.*;

public class FinancialHistoryTableRow extends TableRow
{
      FinancialHistory financialHistory;

    public FinancialHistoryTableRow(FinancialHistory financialHistory)
    {
        this.financialHistory = financialHistory;
        populateCellValues();
    }

   /**
     * Maps the values of Financial History to the TableRow.
     */
    private void populateCellValues()
    {
    	String trxCode = "";
    	String effectiveDate = "";
    	String status = "";
    	
    	EDITTrx editTrx = EDITTrx.findBy_FinancialHistoryPK(financialHistory.getFinancialHistoryPK());
        if (editTrx != null)
        {
            trxCode = editTrx.getTransactionTypeCT();
            effectiveDate = editTrx.getEffectiveDate().getFormattedDate();
            status = editTrx.getStatus();
        }
        
        getCellValues().put(FinancialHistoryTableModel.COLUMN_EFFECTIVE_DATE, Util.initString(effectiveDate, ""));
        getCellValues().put(FinancialHistoryTableModel.COLUMN_TRANSACTION_TYPE, Util.initString(trxCode, ""));
        getCellValues().put(FinancialHistoryTableModel.COLUMN_STATUS, Util.initString(status, ""));
        getCellValues().put(FinancialHistoryTableModel.COLUMN_GROSS_AMOUNT, financialHistory.getGrossAmount());
        getCellValues().put(FinancialHistoryTableModel.COLUMN_NET_AMOUNT, financialHistory.getNetAmount());
        getCellValues().put(FinancialHistoryTableModel.COLUMN_ACCUMULATED_VALUE, financialHistory.getAccumulatedValue());
        getCellValues().put(FinancialHistoryTableModel.COLUMN_SURRENDER_CHARGE, financialHistory.getSurrenderCharge());
        getCellValues().put(FinancialHistoryTableModel.COLUMN_SURRENDER_VALUE, financialHistory.getSurrenderValue());

    }

    /**
     * @see edit.portal.widgettoolkit.TableRow#getRowId()
     * @return
     */
    public String getRowId()
    {
        return financialHistory.getFinancialHistoryPK().toString();
    }
}
