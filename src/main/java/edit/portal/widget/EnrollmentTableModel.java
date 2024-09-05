/*
 * User: dlataille
 * Date: July 19, 2007
 * Time: 12:48:25 PM
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

import group.Enrollment;

public class EnrollmentTableModel extends TableModel
{
    public static final String COLUMN_BEGINNING_POLICY_DATE = "Beginning Policy Date";
    public static final String COLUMN_NUMBER_ELIGIBILE = "Number Eligible";

    private static final String[] COLUMN_NAMES = {COLUMN_BEGINNING_POLICY_DATE, COLUMN_NUMBER_ELIGIBILE};

    public EnrollmentTableModel(AppReqBlock appReqBlock)
    {
        super(appReqBlock);

        getColumnNames().addAll(Arrays.asList(COLUMN_NAMES));
    }

    /**
     * Builds the superset TableModel
     */
    protected void buildTableRows()
    {
        String caseContractGroupPK = Util.initString((String) this.getAppReqBlock().getUserSession().getParameter("activeCasePK"), null);
        if (caseContractGroupPK != null)
        {
            Enrollment[] enrollment = Enrollment.findByContractGroup(new Long(caseContractGroupPK));
            for (int i = 0; i < enrollment.length; i++)
            {
                EnrollmentTableRow tableRow = new EnrollmentTableRow(enrollment[i]);

                if (tableRow.getRowId().equals(this.getSelectedRowId()))
                {
                    tableRow.setRowStatus(TableRow.ROW_STATUS_SELECTED);
                }
                
                super.addRow(tableRow);
            }
        }
    }
}
