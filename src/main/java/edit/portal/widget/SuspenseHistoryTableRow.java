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
import event.*;

public class SuspenseHistoryTableRow extends TableRow
{
      Suspense suspense;

    public SuspenseHistoryTableRow(Suspense suspense)
    {
        this.suspense = suspense;
        populateCellValues();
    }

   /**
     * Maps the values of Suspense to the TableRow.
     */
    private void populateCellValues()
    {
        CashBatchContract   cashBatchContract = suspense.getCashBatchContract();
        String batchId = "";
        String groupNumber = "";
        if (cashBatchContract != null)
        {
            batchId = Util.initString(cashBatchContract.getBatchID(), "");
            groupNumber = Util.initString(cashBatchContract.getGroupNumber(), "");
        }

        String trxCode = null;
        if(suspense.getDirectionCT().equalsIgnoreCase("apply"))
        {
            trxCode = Util.initString(suspense.getContractPlacedFrom(), "");
        }
        else if (suspense.getDirectionCT().equalsIgnoreCase("remove"))
        {
            EDITTrx editTrx = EDITTrx.findBy_SuspensePK(suspense.getSuspensePK());
            if (editTrx != null)
            {
                trxCode = editTrx.getTransactionTypeCT();
            }
        }

        String suspenseAmount = suspense.getOriginalAmount().toString();
        String cellValueForSuspenseAmount = "<script>document.write(formatAsCurrency(" + suspenseAmount + "))</script>";
        getCellValues().put(SuspenseHistoryTableModel.COLUMN_AMOUNT, cellValueForSuspenseAmount);

        String effectiveDate = suspense.getEffectiveDate() == null ? "" : DateTimeUtil.formatEDITDateAsMMDDYYYY(suspense.getEffectiveDate());
        getCellValues().put(SuspenseHistoryTableModel.COLUMN_EFFECTIVE_DATE, effectiveDate);

        String processDate = suspense.getMaintDateTime() == null ? "" : DateTimeUtil.formatEDITDateTimeAsMMDDYYYY(suspense.getMaintDateTime());
        processDate = processDate.substring(0, 10);
        getCellValues().put(SuspenseHistoryTableModel.COLUMN_PROCESS_DATE, processDate);

        getCellValues().put(SuspenseHistoryTableModel.COLUMN_OPERATOR, Util.initString(suspense.getOperator(), ""));
        getCellValues().put(SuspenseHistoryTableModel.COLUMN_BATCH_ID, batchId);
        getCellValues().put(SuspenseHistoryTableModel.COLUMN_GROUP_NUMBER, groupNumber);
        getCellValues().put(SuspenseHistoryTableModel.COLUMN_TRX_TYPE, trxCode);
        getCellValues().put(SuspenseHistoryTableModel.COLUMN_STATUS, Util.initString(suspense.getStatus(), ""));
        getCellValues().put(SuspenseHistoryTableModel.COLUMN_REASON_CODE, Util.initString(suspense.getReasonCodeCT(), ""));
        getCellValues().put(SuspenseHistoryTableModel.COLUMN_REMOVAL_REASON, Util.initString(suspense.getRemovalReason(), ""));

        String appliedRemovedDate = suspense.getDateAppliedRemoved() == null ? "" : DateTimeUtil.formatEDITDateAsMMDDYYYY(suspense.getDateAppliedRemoved());
        getCellValues().put(SuspenseHistoryTableModel.COLUMN_DATE_APPL_REM, appliedRemovedDate);
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
