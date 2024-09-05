/*
 * User: cgleason
 * Date: Mar 18, 2008
 * Time: 12:46:14 PM
 *
 * (c) 2000-2008 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package edit.portal.widget;

import edit.portal.widgettoolkit.*;
import group.*;


public class EnrollmentStateTableRow extends TableRow
{

    private EnrollmentState enrollmentState;


    public EnrollmentStateTableRow(EnrollmentState enrollmentState)
    {
        super();

        this.enrollmentState = enrollmentState;

        populateCellValues();
    }

    /**
     * Maps the values of Master to the TableRow.
     */
    private void populateCellValues()
    {
        getCellValues().put(EnrollmentStateTableModel.COLUMN_STATE, enrollmentState.getStateCT());
    }

    /**
     * @see edit.portal.widgettoolkit.TableRow#getRowId()
     * @return
     */
    public String getRowId()
    {
        return enrollmentState.getEnrollmentStatePK().toString();
    }
}
