/*
 * User: dlataille
 * Date: July 19, 2007
 * Time: 12:57:25 PM
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

import group.ProjectedBusinessByMonth;

public class ProjectedBusinessTableModel extends TableModel
{
    public static final String COLUMN_DATE = "Date";
    public static final String COLUMN_PCT_EXPECTED = "% Expected";
    public static final String COLUMN_CLOSED_UNPAID_PERCENT = "Closed % ";
    public static final String COLUMN_NUMBER_ELIGIBLE = "# Eligible";
    public static final String COLUMN_ENROLLMENT_STATUS = "Enroll Status";
    public static final String COLUMN_DATE_CLOSED = "Date Closed";

    private static final String[] COLUMN_NAMES = {COLUMN_DATE, COLUMN_PCT_EXPECTED, COLUMN_CLOSED_UNPAID_PERCENT, COLUMN_NUMBER_ELIGIBLE, COLUMN_ENROLLMENT_STATUS, COLUMN_DATE_CLOSED};

    public ProjectedBusinessTableModel(AppReqBlock appReqBlock)
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
            ProjectedBusinessByMonth[] projectedBusinessByMonth = ProjectedBusinessByMonth.findByEnrollment(new Long(enrollmentPK));
            for (int i = 0; i < projectedBusinessByMonth .length; i++)
            {
                ProjectedBusinessTableRow tableRow = new ProjectedBusinessTableRow(projectedBusinessByMonth[i]);

                if (tableRow.getRowId().equals(this.getSelectedRowId()))
                {
                    tableRow.setRowStatus(TableRow.ROW_STATUS_SELECTED);
                }

                super.addRow(tableRow);
            }
        }
    }
}
