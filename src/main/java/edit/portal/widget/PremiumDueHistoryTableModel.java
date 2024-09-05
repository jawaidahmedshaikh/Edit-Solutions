/*
 * User: cgleason
 * Date: Apr 09, 2008
 * Time: 9:07:24 AM
 *
 * (c) 2000-2008 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package edit.portal.widget;

import edit.portal.widgettoolkit.*;
import edit.common.vo.*;


import fission.global.*;
import fission.utility.*;

import java.util.*;



public class PremiumDueHistoryTableModel extends TableModel
{
    public static final String COLUMN_BILL_AMOUNT               = "Bill Amt";
    public static final String COLUMN_DEDUCTION_AMOUNT          = "Deduction Amt";
    public static final String COLUMN_NUMBER_OF_DEDUCTIONS      = "# Of Deductions";
    public static final String COLUMN_EFFECTIVE_DATE            = "Effective Date";
    public static final String COLUMN_STATUS                    = "Status";
    public static final String COLUMN_MEP                       = "MEP";
    public static final String COLUMN_BILL_AMOUNT_OVERRIDE      = "Bill Amt Ovrd";
    public static final String COLUMN_DEDUCTION_AMOUNT_OVERRIDE = "Ded Amt Ovrd";

    private static final String[] COLUMN_NAMES = {COLUMN_BILL_AMOUNT, COLUMN_DEDUCTION_AMOUNT, COLUMN_NUMBER_OF_DEDUCTIONS,
                                                  COLUMN_EFFECTIVE_DATE, COLUMN_STATUS, COLUMN_MEP, COLUMN_BILL_AMOUNT_OVERRIDE,
                                                  COLUMN_DEDUCTION_AMOUNT_OVERRIDE};



    public  PremiumDueHistoryTableModel(AppReqBlock appReqBlock)
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

        PremiumDueVO[] premiumDueVOs = (PremiumDueVO[])appReqBlock.getHttpSession().getAttribute("premiumDueVOs");

        premiumDueVOs = (PremiumDueVO[])Util.sortObjects(premiumDueVOs, new String[] {"getEffectiveDate"});

        populateSuspenseHistoryRows(premiumDueVOs);
    }

    private void populateSuspenseHistoryRows(PremiumDueVO[] premiumDueVOs)
    {
        if (premiumDueVOs != null)
        {
            for (int i = 0; i < premiumDueVOs.length; i++)
            {
                TableRow tableRow = new PremiumDueHistoryTableRow(premiumDueVOs[i]);

                if (tableRow.getRowId().equals(this.getSelectedRowId()))
                {
                    tableRow.setRowStatus(TableRow.ROW_STATUS_SELECTED);
                }

                super.addRow(tableRow);
            }
        }
    }
}
