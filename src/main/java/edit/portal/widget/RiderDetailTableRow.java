/*
 * User: dlataille
 * Date: Sept 19, 2007
 * Time: 11:42:05 AM
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package edit.portal.widget;

import contract.Segment;
import edit.common.CodeTableWrapper;
import edit.portal.widgettoolkit.TableRow;

public class RiderDetailTableRow extends TableRow
{
    private Segment rider;


    public RiderDetailTableRow(Segment rider)
    {
        super();

        this.rider = rider;

        populateCellValues();
    }

    /**
     * Maps the values of Segment (which in this case is a rider) to the TableRow.
     */
    private void populateCellValues()
    {
        String riderName = rider.getOptionCodeCT();
        riderName = CodeTableWrapper.getSingleton().getCodeDescByCodeTableNameAndCode("RIDERNAME", riderName);
        getCellValues().put(RiderDetailDoubleTableModel.COLUMN_RIDER_NAME, riderName);
    }

    /**
     * @see edit.portal.widgettoolkit.TableRow#getRowId()
     * @return
     */
    public String getRowId()
    {
        return rider.getSegmentPK().toString();
    }
}
