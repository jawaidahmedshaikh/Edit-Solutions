/*
 * 
 * User: cgleason
 * Date: Oct 16, 2007
 * Time: 9:25:36 AM
 * 
 * (c) 2000 - 2007 Systems Engineering Group, LLC.  All rights Reserved
 *  Systems Engineering Group, LLC Propietary and confidential.  Any use is
 *  subject to the license agreement.
 */
package edit.portal.widget;

import fission.global.*;
import fission.beans.*;
import fission.utility.*;

import java.util.*;

import edit.portal.widgettoolkit.*;
import edit.common.*;

import javax.servlet.http.*;

import event.*;
import contract.*;

public class LoanSummaryTableModel extends TableModel
{
    public static final String COLUMN_LOAN_SOURCE         = "Loan Source";
    public static final String COLUMN_EFFECTIVE_DATE      = "Effective Date";
    public static final String COLUMN_PRINCIPAL_REMAINING = "Principal Remaining";

    private Long segmentPK;

    private static final String[] COLUMN_NAMES = {COLUMN_LOAN_SOURCE, COLUMN_EFFECTIVE_DATE, COLUMN_PRINCIPAL_REMAINING};


    public LoanSummaryTableModel(AppReqBlock appReqBlock)
    {
        super(appReqBlock);

        getColumnNames().addAll(Arrays.asList(COLUMN_NAMES));

    }

    public  LoanSummaryTableModel(Long segmentPK, AppReqBlock appReqBlock)
    {
        super(appReqBlock);

        this.segmentPK = segmentPK;

        getColumnNames().addAll(Arrays.asList(COLUMN_NAMES));

    }

    /**
     * Builds the superset TableModel driven by the Segment
     */
    protected void buildTableRows()
    {
        AppReqBlock appReqBlock = this.getAppReqBlock();
        HttpServletRequest request = appReqBlock.getHttpServletRequest();

        Bucket[] loanSummaryTableRows = null;

        loanSummaryTableRows = Bucket.findBy_SegmentPK_(segmentPK);

        populateRows(loanSummaryTableRows);
    }

    private void populateRows(Bucket[] loanSummaryTableRows)
    {
        if (loanSummaryTableRows != null)
        {
            for (int i = 0; i < loanSummaryTableRows.length; i++)
            {
                TableRow tableRow = new LoanSummaryTableRow(loanSummaryTableRows[i]);

                if (tableRow.getRowId().equals(this.getSelectedRowId()))
                {
                    tableRow.setRowStatus(TableRow.ROW_STATUS_SELECTED);
                }

                super.addRow(tableRow);
            }
        }
    }
}
