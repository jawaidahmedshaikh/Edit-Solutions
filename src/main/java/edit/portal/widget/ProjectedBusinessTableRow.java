/*
 * User: dlataille
 * Date: July 19, 2007
 * Time: 12:59:14 PM
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package edit.portal.widget;

import edit.portal.widgettoolkit.*;
import edit.common.EDITDate;
import edit.common.EDITBigDecimal;
import group.ProjectedBusinessByMonth;
import fission.utility.*;


public class ProjectedBusinessTableRow extends TableRow
{

    private ProjectedBusinessByMonth projectedBusinessByMonth;


    public ProjectedBusinessTableRow(ProjectedBusinessByMonth projectedBusinessByMonth)
    {
        super();

        this.projectedBusinessByMonth= projectedBusinessByMonth;

        populateCellValues();
    }

    /**
     * Maps the values of Master to the TableRow.
     */
    private void populateCellValues()
    {
        EDITDate date = projectedBusinessByMonth.getDate();

        EDITBigDecimal percentExpected = projectedBusinessByMonth.getPercentExpected();

        int numberEligible = projectedBusinessByMonth.getNumberOfEligibles();
        projectedBusinessByMonth.setProjBusNumberOfEligibles(numberEligible);
        
        String enrollmentStatus = projectedBusinessByMonth.getEnrollmentStatusCT();

        EDITDate closedDate = projectedBusinessByMonth.getClosedDate();

        EDITBigDecimal closedUnpaidPercent = projectedBusinessByMonth.getClosedUnpaidPercent();

        getCellValues().put(ProjectedBusinessTableModel.COLUMN_DATE, DateTimeUtil.formatEDITDateAsMMDDYYYY(date));

        getCellValues().put(ProjectedBusinessTableModel.COLUMN_PCT_EXPECTED, percentExpected.toString());

        getCellValues().put(ProjectedBusinessTableModel.COLUMN_CLOSED_UNPAID_PERCENT, closedUnpaidPercent.toString());

        getCellValues().put(ProjectedBusinessTableModel.COLUMN_NUMBER_ELIGIBLE, new Integer(numberEligible).toString());

        getCellValues().put(ProjectedBusinessTableModel.COLUMN_ENROLLMENT_STATUS, enrollmentStatus);

        getCellValues().put(ProjectedBusinessTableModel.COLUMN_DATE_CLOSED, DateTimeUtil.formatEDITDateAsMMDDYYYY(closedDate));
    }

    /**
     * @see edit.portal.widgettoolkit.TableRow#getRowId()
     * @return
     */
    public String getRowId()
    {
        return projectedBusinessByMonth.getProjectedBusinessByMonthPK().toString();
    }
}
