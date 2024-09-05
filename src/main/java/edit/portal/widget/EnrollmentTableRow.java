/*
 * User: dlataille
 * Date: July 19, 2007
 * Time: 12:46:14 PM
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package edit.portal.widget;

import edit.portal.widgettoolkit.*;
import edit.common.EDITDate;
import group.Enrollment;
import fission.utility.*;


public class EnrollmentTableRow extends TableRow
{

    private Enrollment enrollment;


    public EnrollmentTableRow(Enrollment enrollment)
    {
        super();

        this.enrollment = enrollment;

        populateCellValues();
    }

    /**
     * Maps the values of Master to the TableRow.
     */
    private void populateCellValues()
    {
        EDITDate beginningPolicyDate = enrollment.getBeginningPolicyDate();

        int numberEligible = enrollment.getNumberOfEligibles();

        if (beginningPolicyDate != null)
        {
            getCellValues().put(EnrollmentTableModel.COLUMN_BEGINNING_POLICY_DATE, DateTimeUtil.formatEDITDateAsMMDDYYYY(beginningPolicyDate));
        }

        getCellValues().put(EnrollmentTableModel.COLUMN_NUMBER_ELIGIBILE, numberEligible);
    }

    /**
     * @see edit.portal.widgettoolkit.TableRow#getRowId()
     * @return
     */
    public String getRowId()
    {
        return enrollment.getEnrollmentPK().toString();
    }
}
