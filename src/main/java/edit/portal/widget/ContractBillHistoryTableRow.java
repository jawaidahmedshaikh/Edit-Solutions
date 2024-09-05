/*
 * User: Tapas M
 * Date: Ug 04, 2008
 * Time: 9:28:31 AM
 *
 * (c) 2000-2008 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package edit.portal.widget;

import billing.Bill;

import edit.portal.widgettoolkit.*;
import edit.common.vo.*;
import edit.common.EDITDate;

import fission.utility.*;


public class ContractBillHistoryTableRow extends TableRow
{
      Bill bill;

    public ContractBillHistoryTableRow(Bill bill)
    {
        this.bill = bill;
        populateCellValues();
    }

   /**
     * Maps the values of Suspense to the TableRow.
     */
    private void populateCellValues()
    {
        EDITDate billDueDate = bill.getBillGroup().getDueDate();
        getCellValues().put(ContractBillHistoryTableModel.COLUMN_BILL_DUE_DATE, (billDueDate == null ? "" : DateTimeUtil.formatEDITDateAsMMDDYYYY(billDueDate)));

        String billAmount = bill.getBilledAmount().toString();
        String cellValueForBillAmount = "<script>document.write(formatAsCurrency(" + billAmount + "))</script>";
        getCellValues().put(ContractBillHistoryTableModel.COLUMN_BILLED_AMOUNT, cellValueForBillAmount);


        String paidAmount = bill.getPaidAmount().toString();
        String cellValueForPaidAmount = "<script>document.write(formatAsCurrency(" + paidAmount + "))</script>";
        getCellValues().put(ContractBillHistoryTableModel.COLUMN_PAID_AMOUNT, cellValueForPaidAmount);
        
        String billType = bill.getBillGroup().getBillSchedule().getBillTypeCT();
        getCellValues().put(ContractBillHistoryTableModel.COLUMN_BILL_TYPE, billType);
    }

    /**
     * @see edit.portal.widgettoolkit.TableRow#getRowId()
     * @return
     */
    public String getRowId()
    {
        return bill.getBillPK() + "";
    }
}
