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

public class FilterSuspenseTableRow extends TableRow
{
      Suspense suspense;

    public FilterSuspenseTableRow(Suspense suspense)
    {
        this.suspense = suspense;
        populateCellValues();
    }

   /**
     * Maps the values of Suspense to the TableRow.
     */
    private void populateCellValues()
    {
        String effectiveDate = suspense.getEffectiveDate() == null ? "" : DateTimeUtil.formatEDITDateAsMMDDYYYY(suspense.getEffectiveDate());
        getCellValues().put(FilterSuspenseTableModel.COLUMN_EFFECTIVE_DATE, effectiveDate);

        String suspenseAmount = suspense.getSuspenseAmount().toString();
        String cellValueForSuspenseAmount = "<script>document.write(formatAsCurrency(" + suspenseAmount + "))</script>";

        String trxCode = null;
        EDITTrx editTrx = EDITTrx.findBy_SuspensePK(suspense.getSuspensePK());
        String status  = null;
        if (editTrx != null)
        {
            status = editTrx.getStatus();
        }

       if (suspense.getDirectionCT() != null)
       {
            if(suspense.getDirectionCT().equalsIgnoreCase("apply"))
            {
                trxCode = Util.initString(suspense.getContractPlacedFrom(), "");
            }
            else if (suspense.getDirectionCT().equalsIgnoreCase("remove"))
            {
                if (editTrx != null)
                {
                    trxCode = editTrx.getTransactionTypeCT();
                }
            }
       }
       else
       {
           trxCode = Util.initString(suspense.getContractPlacedFrom(), "");
       }

        CashBatchContract   cashBatchContract = suspense.getCashBatchContract();
        String batchId = "";
        String groupNumber = "";
        if (cashBatchContract != null)
        {
            batchId = Util.initString(cashBatchContract.getBatchID(), "");
            groupNumber = Util.initString(cashBatchContract.getGroupNumber(), "");
        }

        getCellValues().put(FilterSuspenseTableModel.COLUMN_USER_NUMBER, Util.initString(suspense.getUserDefNumber(), ""));
        getCellValues().put(FilterSuspenseTableModel.COLUMN_LAST_NAME, Util.initString(suspense.getLastName(), ""));
        getCellValues().put(FilterSuspenseTableModel.COLUMN_AMOUNT, cellValueForSuspenseAmount);
        getCellValues().put(FilterSuspenseTableModel.COLUMN_DIRECTION, Util.initString(suspense.getDirectionCT(), ""));
        getCellValues().put(FilterSuspenseTableModel.COLUMN_OPERATOR, Util.initString(suspense.getOperator(), ""));
        getCellValues().put(FilterSuspenseTableModel.COLUMN_BATCH_ID, batchId);
        getCellValues().put(FilterSuspenseTableModel.COLUMN_GROUPNUMBER, groupNumber);
        getCellValues().put(FilterSuspenseTableModel.COLUMN_TRX_TYPE, trxCode);
        getCellValues().put(FilterSuspenseTableModel.COLUMN_STATUS, Util.initString(status, ""));
        getCellValues().put(FilterSuspenseTableModel.COLUMN_REASON_CODE, Util.initString(suspense.getReasonCodeCT(), ""));
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
