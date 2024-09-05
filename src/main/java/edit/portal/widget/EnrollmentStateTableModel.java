/*
 * User: cgleason
 * Date: Mar 18, 2008
 * Time: 12:48:25 PM
 *
 * (c) 2000-2008 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package edit.portal.widget;

import edit.portal.widgettoolkit.*;
import fission.global.*;
import fission.utility.*;

import java.util.*;

import group.*;

public class EnrollmentStateTableModel extends TableModel
{
    public static final String COLUMN_STATE = "State";

    private static final String[] COLUMN_NAMES = {COLUMN_STATE};

    public EnrollmentStateTableModel(AppReqBlock appReqBlock)
    {
        super(appReqBlock);

        getColumnNames().addAll(Arrays.asList(COLUMN_NAMES));
    }

    /**
     * Builds the superset TableModel
     */
    protected void buildTableRows()
    {
        String enrollmentPK = Util.initString((String) this.getAppReqBlock().getUserSession().getParameter("activeEnrollmentPK"), null);
        if (enrollmentPK != null)
        {
            EnrollmentState[] enrollmentState = EnrollmentState.findByEnrollemntFK(new Long(enrollmentPK));
            for (int i = 0; i < enrollmentState.length; i++)
            {
                EnrollmentStateTableRow tableRow = new EnrollmentStateTableRow(enrollmentState[i]);

                if (tableRow.getRowId().equals(this.getSelectedRowId()))
                {
                    tableRow.setRowStatus(TableRow.ROW_STATUS_SELECTED);
                }
                
                super.addRow(tableRow);
            }
        }
    }
}
