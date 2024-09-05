/*
 * User: dlataille
 * Date: July 10, 2007
 * Time: 9:26:25 AM
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package edit.portal.widget;

import edit.portal.widgettoolkit.*;
import edit.common.vo.*;
import edit.services.db.hibernate.*;
import fission.global.*;
import fission.utility.*;

import java.util.*;

import contract.*;
import billing.*;


public class ContractBillingTableModel extends TableModel
{
    public static final String COLUMN_EFFECTIVE_DATE = "Effective Date";
    public static final String COLUMN_PROCESS_DATE = "Process Date";
    public static final String COLUMN_FIELD = "Field";
    public static final String COLUMN_PRIOR_VALUE = "Prior Value";
    public static final String COLUMN_NEW_VALUE = "New Value";
    public static final String COLUMN_OPERATOR = "Operator";
    public static final String COLUMN_DATE_TIME = "Date/Time";

    private static final String[] COLUMN_NAMES = {COLUMN_EFFECTIVE_DATE, COLUMN_PROCESS_DATE, COLUMN_FIELD, COLUMN_PRIOR_VALUE, COLUMN_NEW_VALUE, COLUMN_OPERATOR, COLUMN_DATE_TIME};

    public ContractBillingTableModel(AppReqBlock appReqBlock)
    {
        super(appReqBlock);

        getColumnNames().addAll(Arrays.asList(COLUMN_NAMES));
    }

    /**
     * Builds the superset TableModel
     */
    protected void buildTableRows()
    {
//        String segmentPK = Util.initString((String) this.getAppReqBlock().getHttpServletRequest().getParameter("segmentPK"), null);
        BillScheduleVO billScheduleVO = (BillScheduleVO) this.getAppReqBlock().getHttpSession().getAttribute("BillScheduleVO");
        ChangeHistory[] billNonFinancials = null;


        if (billScheduleVO != null)
        {
            BillSchedule billSchedule = (BillSchedule)SessionHelper.map(billScheduleVO, SessionHelper.EDITSOLUTIONS);

            billNonFinancials = ChangeHistory.getByModifiedKey(billSchedule.getBillSchedulePK());

            if (billNonFinancials != null)
            {
                billNonFinancials = (ChangeHistory[]) Util.sortObjects(billNonFinancials, new String [] {"getChangeHistoryPK"});

                for (int i = billNonFinancials.length-1; i >= 0 ; i--)
                {
                    ChangeHistory billScheduleNonFinancial = billNonFinancials[i];

                    TableRow tableRow = new ContractBillingTableRow(billScheduleNonFinancial);

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
