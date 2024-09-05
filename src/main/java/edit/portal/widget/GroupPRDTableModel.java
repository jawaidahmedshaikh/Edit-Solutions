/*
 * User: dlataille
 * Date: May 1, 2007
 * Time: 9:38:25 AM
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package edit.portal.widget;

import edit.portal.widgettoolkit.*;
import fission.global.*;
import fission.utility.*;

import java.util.*;

import contract.*;
import group.*;


public class GroupPRDTableModel extends TableModel
{
    public static final String COLUMN_EFFECTIVE_DATE = "Effective Date";
    public static final String COLUMN_PROCESS_DATE = "Process Date";
    public static final String COLUMN_FIELD = "Field";
    public static final String COLUMN_PRIOR_VALUE = "Prior Value";
    public static final String COLUMN_NEW_VALUE = "New Value";
    public static final String COLUMN_OPERATOR = "Operator";
    public static final String COLUMN_DATE_TIME = "Date/Time";

    private static final String[] COLUMN_NAMES = {COLUMN_EFFECTIVE_DATE, COLUMN_PROCESS_DATE, COLUMN_FIELD, COLUMN_PRIOR_VALUE, COLUMN_NEW_VALUE, COLUMN_OPERATOR, COLUMN_DATE_TIME};

    public GroupPRDTableModel(AppReqBlock appReqBlock)
    {
        super(appReqBlock);

        getColumnNames().addAll(Arrays.asList(COLUMN_NAMES));
    }

    /**
     * Builds the superset TableModel
     */
    protected void buildTableRows()
    {
        PayrollDeductionSchedule payrollDeductionSchedule = (PayrollDeductionSchedule)this.getAppReqBlock().getHttpServletRequest().getAttribute("payrollDeductionSchedule");

        ChangeHistory[]  prdNonFinancials = null;

        if (payrollDeductionSchedule != null)
        {
            prdNonFinancials = ChangeHistory.getByModifiedKey(payrollDeductionSchedule.getPayrollDeductionSchedulePK());

            if (prdNonFinancials != null)
            {
                prdNonFinancials = (ChangeHistory[]) Util.sortObjects(prdNonFinancials, new String [] {"getChangeHistoryPK"});

                for (int i = prdNonFinancials.length-1; i >= 0; i--)
                {
                    ChangeHistory prdNonFinancial = prdNonFinancials[i];

                    TableRow tableRow = new GroupPRDTableRow(prdNonFinancial);

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
