/*
 * User: dlataille
 * Date: May 2, 2008
 * Time: 9:19:24 AM
 *
 * (c) 2000-2008 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package edit.portal.widget;

import edit.portal.widgettoolkit.*;
import edit.common.*;


import fission.global.*;
import fission.beans.*;
import fission.utility.*;

import javax.servlet.http.*;
import java.util.*;

import contract.*;
import contract.ChangeHistory;


public class ClientHistorySummaryTableModel extends TableModel
{
    public static final String COLUMN_EFFECTIVE_DATE = "Eff Date";
    public static final String COLUMN_PROCESS_DATE   = "Proc Date";
    public static final String COLUMN_TRAN_TYPE      = "Tran Type";
    public static final String COLUMN_FIELD_NAME     = "Field Name";
    public static final String COLUMN_BEFORE_CHANGE_VALUE = "Before Change Value";
    public static final String COLUMN_AFTER_CHANGE_VALUE = "After Change Value";

    private static final String[] COLUMN_NAMES = {COLUMN_EFFECTIVE_DATE, COLUMN_PROCESS_DATE,
                                                  COLUMN_TRAN_TYPE, COLUMN_FIELD_NAME,
                                                  COLUMN_BEFORE_CHANGE_VALUE, COLUMN_AFTER_CHANGE_VALUE};

    private HistoryFilterRow historyFilterRow;

    public  ClientHistorySummaryTableModel(AppReqBlock appReqBlock)
    {
        super(appReqBlock);

        getColumnNames().addAll(Arrays.asList(COLUMN_NAMES));
    }

    public  ClientHistorySummaryTableModel(HistoryFilterRow historyFilterRow, AppReqBlock appReqBlock)
    {
        super(appReqBlock);

        this.historyFilterRow = historyFilterRow;

        getColumnNames().addAll(Arrays.asList(COLUMN_NAMES));
    }

    /**
     * Builds the superset TableModel driven by the Client
     */
    protected void buildTableRows()
    {
        AppReqBlock appReqBlock = this.getAppReqBlock();

        PageBean clientDetail = appReqBlock.getSessionBean("clientDetailSessionBean").getPageBean("pageBean");
        String clientDetailPK = clientDetail.getValue("clientDetailPK");

        ChangeHistory[] changeHistories = ChangeHistory.findForClient(new Long(clientDetailPK));

        populateClientHistorySummaryRows(changeHistories);
    }

    private void populateClientHistorySummaryRows(ChangeHistory[] changeHistories)
    {
        if (changeHistories != null)
        {
            for (int i = 0; i < changeHistories.length; i++)
            {
                TableRow tableRow = new ClientHistorySummaryTableRow(changeHistories[i]);

                if (tableRow.getRowId().equals(this.getSelectedRowId()))
                {
                    tableRow.setRowStatus(TableRow.ROW_STATUS_SELECTED);
                }

                super.addRow(tableRow);
            }
        }
    }
}
