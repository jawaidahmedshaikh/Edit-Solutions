/*
 * User: dlataill
 * Date: Aug 3, 2005
 * Time: 2:05:27 PM
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement
 */
package edit.portal.widget;

import contract.Deposits;
import edit.portal.widgettoolkit.TableModel;
import edit.portal.widgettoolkit.TableRow;
import event.Suspense;
import fission.global.AppReqBlock;
import fission.utility.Util;

import java.util.ArrayList;
import fission.global.AppReqBlock;
import fission.utility.Util;
import event.Suspense;
import contract.Deposits;

import java.util.*;


public class CashBatchDetailTableModel extends TableModel
{
    public static final String COLUMN_EXCHANGE_COMPANY = "Exchange Company";
    public static final String COLUMN_EXCHANGE_POLICY = "Exchange Policy";
    public static final String COLUMN_ANTICIPATED_AMOUNT = "Anticipated Amount";

    private static final String[] COLUMN_NAMES = {COLUMN_EXCHANGE_COMPANY, COLUMN_EXCHANGE_POLICY, COLUMN_ANTICIPATED_AMOUNT};

    private String policyNumber;


    public CashBatchDetailTableModel(Suspense suspense, String policyNumber, AppReqBlock appReqBlock)
    {
        super(appReqBlock);

        this.policyNumber = Util.initString(policyNumber, "");

        getColumnNames().addAll(Arrays.asList(COLUMN_NAMES));
    }

    /**
     * Builds the superset TableModel
     */
    public void buildTableRows()
    {
        Deposits[] deposits = null;

        if (!policyNumber.equals(""))
        {
            deposits = Deposits.findAllByContractNumber_AmountReceivedGTZero(policyNumber);

            if (deposits != null)
            {
                for (int i = 0; i < deposits.length; i++)
                {
                    Deposits deposit = deposits[i];

                    TableRow tableRow = new CashBatchDetailTableRow(deposit);

                    if (tableRow.getRowId().equals(this.getSelectedRowId()))
                    {
                        tableRow.setRowStatus(TableRow.ROW_STATUS_SELECTED);
                    }

                    super.addRow(tableRow);
                }
            }
        }
    }
}
