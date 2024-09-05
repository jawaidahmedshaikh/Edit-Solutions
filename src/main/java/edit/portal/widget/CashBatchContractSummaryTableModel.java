/*
 * User: dlataill
 * Date: Aug 2, 2005
 * Time: 8:33:30 AM
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package edit.portal.widget;

import edit.common.EDITBigDecimal;
import edit.portal.common.session.UserSession;
import edit.portal.widgettoolkit.TableModel;
import edit.portal.widgettoolkit.TableRow;
import event.CashBatchContract;
import event.Suspense;
import fission.global.AppReqBlock;
import fission.utility.Util;

import java.util.ArrayList;
import java.util.*;

import fission.global.AppReqBlock;
import fission.utility.Util;
import event.CashBatchContract;
import event.Suspense;


public class CashBatchContractSummaryTableModel extends TableModel
{
    public static final String COLUMN_POLICY_NUMBER = "Policy Number";
    public static final String COLUMN_LAST_NAME = "Name";
    public static final String COLUMN_AMOUNT = "Amount";
    public static final String COLUMN_PENDING_AMOUNT = "Pending Amount";
    public static final String COLUMN_REFUND_AMOUNT = "Refund Amount";

    private CashBatchContract cashBatchContract;

    private static final String[] COLUMN_NAMES = {COLUMN_POLICY_NUMBER, COLUMN_LAST_NAME, COLUMN_AMOUNT, COLUMN_PENDING_AMOUNT, COLUMN_REFUND_AMOUNT};

    public CashBatchContractSummaryTableModel(AppReqBlock appReqBlock)
    {
        super(appReqBlock);

        getColumnNames().addAll(Arrays.asList(COLUMN_NAMES));
    }

    public CashBatchContractSummaryTableModel(CashBatchContract cashBatchContract, AppReqBlock appReqBlock)
    {
        super(appReqBlock);

        this.cashBatchContract = cashBatchContract;

        getColumnNames().addAll(Arrays.asList(COLUMN_NAMES));
    }

    /**
     * Builds the superset TableModel
     */
    public void buildTableRows()
    {
        EDITBigDecimal batchRemaining = new EDITBigDecimal();

        if (cashBatchContract == null)
        {
            String cashBatchContractFK = Util.initString(getAppReqBlock().getReqParm("activeCashBatchContractPK"), "0");
            UserSession userSession = getAppReqBlock().getUserSession();
            this.cashBatchContract = CashBatchContract.findByPK(new Long(cashBatchContractFK));
        }

        if (cashBatchContract != null)
        {
            Suspense[] suspenses = Suspense.findAllByCashBatchContract(cashBatchContract);

            suspenses = (Suspense[]) Util.sortObjects(suspenses, new String[] {"getUserDefNumber"});

            for (int i = 0; i < suspenses.length; i++)
            {
                Suspense suspense = suspenses[i];

                batchRemaining = batchRemaining.addEditBigDecimal(suspense.getSuspenseAmount());

                TableRow tableRow = new CashBatchContractSummaryTableRow(suspense);

                if (tableRow.getRowId().equals(this.getSelectedRowId()))
                {
                    tableRow.setRowStatus(TableRow.ROW_STATUS_SELECTED);
                }

                super.addRow(tableRow);
            }
        }

        this.getAppReqBlock().getHttpServletRequest().setAttribute("batchRemaining", batchRemaining);
    }
}
