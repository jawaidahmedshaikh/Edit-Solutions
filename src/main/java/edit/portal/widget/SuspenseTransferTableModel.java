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


public class SuspenseTransferTableModel extends TableModel
{
    public static final String COLUMN_CONTRACT_NUMBER    = "Contract Number";
    public static final String COLUMN_AMOUNT             = "Amount";

    private static final String[] COLUMN_NAMES = {COLUMN_CONTRACT_NUMBER, COLUMN_AMOUNT, };



    public  SuspenseTransferTableModel(AppReqBlock appReqBlock)
    {
        super(appReqBlock);

        getColumnNames().addAll(Arrays.asList(COLUMN_NAMES));
    }

    /**
     * Builds the superset TableModel driven by the Segment
     */
    protected void buildTableRows()
    {
        AppReqBlock appReqBlock = this.getAppReqBlock();
        HttpServletRequest request = appReqBlock.getHttpServletRequest();

        List suspenseTransferRows = (ArrayList)appReqBlock.getHttpSession().getAttribute("suspenseTransferRows");

        if (suspenseTransferRows != null)
        {
            for (int i = 0; i < suspenseTransferRows.size(); i++)
            {
                TableRow tableRow = new SuspenseTransferTableRow((String)suspenseTransferRows.get(i));

                if (tableRow.getRowId().equals(this.getSelectedRowId()))
                {
                    tableRow.setRowStatus(TableRow.ROW_STATUS_SELECTED);
                }

                super.addRow(tableRow);
            }
        }

    }
}
