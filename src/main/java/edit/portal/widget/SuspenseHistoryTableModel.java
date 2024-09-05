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
import edit.common.*;


import fission.global.*;
import fission.beans.*;
import fission.utility.*;

import javax.servlet.http.*;
import java.util.*;

import contract.*;
import event.*;
import engine.*;
import security.*;


public class SuspenseHistoryTableModel extends TableModel
{
    public static final String COLUMN_EFFECTIVE_DATE = "Eff Date";
    public static final String COLUMN_AMOUNT         = "OrigAmt";
    public static final String COLUMN_PROCESS_DATE   = "OrigPrcssDte ";
    public static final String COLUMN_OPERATOR       = "Operator";
    public static final String COLUMN_BATCH_ID       = "BatchId";
    public static final String COLUMN_GROUP_NUMBER   = "Group#";
    public static final String COLUMN_TRX_TYPE       = "TrxType";
    public static final String COLUMN_STATUS         = "Status";
    public static final String COLUMN_REASON_CODE    = "RsnCde";
    public static final String COLUMN_REMOVAL_REASON = "RmvlRsn";
    public static final String COLUMN_DATE_APPL_REM  = "DteApp/Rem";

    private static final String[] COLUMN_NAMES = {COLUMN_EFFECTIVE_DATE, COLUMN_AMOUNT, COLUMN_PROCESS_DATE, COLUMN_OPERATOR,
                                                  COLUMN_BATCH_ID, COLUMN_GROUP_NUMBER, COLUMN_TRX_TYPE, COLUMN_STATUS,
                                                  COLUMN_REASON_CODE, COLUMN_REMOVAL_REASON, COLUMN_DATE_APPL_REM};



    public  SuspenseHistoryTableModel(AppReqBlock appReqBlock)
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
        HttpServletRequest request = appReqBlock.getHttpServletRequest();

        SessionBean contractMainSessionBean = appReqBlock.getSessionBean("contractMainSessionBean");
        String contractNumber = contractMainSessionBean.getValue("contractId");

        Suspense[] suspenseHistoryRows = null;

        suspenseHistoryRows = Suspense.findSuspenseHistory(contractNumber);

        populateSuspenseHistoryRows(suspenseHistoryRows);
    }

    private void populateSuspenseHistoryRows(Suspense[] suspenseHistoryRows)
    {
        if (suspenseHistoryRows != null)
        {
            for (int i = 0; i < suspenseHistoryRows.length; i++)
            {
                TableRow tableRow = new SuspenseHistoryTableRow(suspenseHistoryRows[i]);

                if (tableRow.getRowId().equals(this.getSelectedRowId()))
                {
                    tableRow.setRowStatus(TableRow.ROW_STATUS_SELECTED);
                }

                super.addRow(tableRow);
            }
        }
    }
}
