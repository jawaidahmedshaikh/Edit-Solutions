/*
 * User: Tapas M
 * Date: Aug 04, 2008
 * Time: 9:07:24 AM
 *
 * (c) 2000-2008 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package edit.portal.widget;

import billing.Bill;

import edit.common.vo.BillVO;
import edit.common.vo.PremiumDueVO;

import edit.portal.widgettoolkit.TableModel;
import edit.portal.widgettoolkit.TableRow;

import fission.global.AppReqBlock;

import java.util.Arrays;


public class ContractBillHistoryTableModel extends TableModel
{
    public static final String COLUMN_BILL_DUE_DATE = "Bill Due Date";
    public static final String COLUMN_BILLED_AMOUNT           = "Billed Amount";
    public static final String COLUMN_PAID_AMOUNT      = "Paid Amount ";
    public static final String COLUMN_BILL_TYPE  = "Bill Type";

    private static final String[] COLUMN_NAMES = {COLUMN_BILL_DUE_DATE, COLUMN_BILLED_AMOUNT, COLUMN_PAID_AMOUNT, COLUMN_BILL_TYPE};


    public  ContractBillHistoryTableModel(AppReqBlock appReqBlock)
    {
        super(appReqBlock);

        getColumnNames().addAll(Arrays.asList(COLUMN_NAMES));
    }

    /**
     * Suspense summary rows
     */
    protected void buildTableRows()
    {
        AppReqBlock appReqBlock = this.getAppReqBlock();

        Bill[] bills = (Bill[])appReqBlock.getHttpSession().getAttribute("bills");

        populateSuspenseHistoryRows(bills);
    }

    private void populateSuspenseHistoryRows(Bill[] bills)
    {
        if (bills != null)
        {
            for (int i = bills.length - 1; i >= 0 ; i--)
            {
                TableRow tableRow = new ContractBillHistoryTableRow(bills[i]);

                if (tableRow.getRowId().equals(this.getSelectedRowId()))
                {
                    tableRow.setRowStatus(TableRow.ROW_STATUS_SELECTED);
                }

                super.addRow(tableRow);
            }
        }
    }
}
