/*
 * User: dlataill
 * Date: Aug 9, 2005
 * Time: 8:33:55 AM
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package edit.portal.widget;

import edit.portal.widgettoolkit.TableModel;
import edit.portal.widgettoolkit.TableRow;
import event.Suspense;
import fission.global.AppReqBlock;

import java.util.ArrayList;
import java.util.*;


public class NewBusDepositSuspenseTableModel extends TableModel
{
    public static final String COLUMN_EXCHANGE_COMPANY = "Exchange Company";
    public static final String COLUMN_EXCHANGE_POLICY = "Exchange Policy";
    public static final String COLUMN_SUSPENSE_AMOUNT = "Suspense Amount";
    public static final String COLUMN_EFFECTIVE_DATE = "Effective Date";
    public static final String COLUMN_RELEASE_IND = "Release Indicator";

    private static final String[] COLUMN_NAMES = {COLUMN_EXCHANGE_COMPANY, COLUMN_EXCHANGE_POLICY, COLUMN_SUSPENSE_AMOUNT,
                                                  COLUMN_EFFECTIVE_DATE, COLUMN_RELEASE_IND};

    private String contractNumber;


    public NewBusDepositSuspenseTableModel(String contractNumber, AppReqBlock appReqBlock)
    {
        super(appReqBlock);

        this.contractNumber = contractNumber;

        getColumnNames().addAll(Arrays.asList(COLUMN_NAMES));

    }

    /**
     * Builds the superset TableModel
     */
    public void buildTableRows()
    {
        if (contractNumber != null)
        {
            Suspense[] suspenses = Suspense.findByUserDefNumberForIssue(contractNumber);

            for (int i = 0; i < suspenses.length; i++)
            {
                Suspense suspense = suspenses[i];

                TableRow tableRow = new NewBusDepositSuspenseTableRow(suspense);

                if (tableRow.getRowId().equals(this.getSelectedRowId()))
                {
                    tableRow.setRowStatus(TableRow.ROW_STATUS_SELECTED);
                }

                super.addRow(tableRow);
            }
        }
    }
}
