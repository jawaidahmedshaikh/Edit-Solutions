/*
 * User: dlataille
 * Date: May 2, 2007
 * Time: 8:01:14 AM
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package edit.portal.widget;

import edit.portal.widgettoolkit.*;
import edit.common.EDITDate;
import group.DepartmentLocation;
import fission.utility.DateTimeUtil;


public class GroupDeptLocTableRow extends TableRow
{

    private DepartmentLocation departmentLocation;


    public GroupDeptLocTableRow(DepartmentLocation departmentLocation)
    {
        super();

        this.departmentLocation = departmentLocation;

        populateCellValues();
    }

   /**
      * Maps the values of Master to the TableRow.
      */
     private void populateCellValues()
     {
       String deptLocCode = departmentLocation.getDeptLocCode();

       String deptLocName = departmentLocation.getDeptLocName();

       EDITDate edEffDate = departmentLocation.getEffectiveDate();

       String effectiveDate = "";

       if (edEffDate != null)
       {
          effectiveDate = DateTimeUtil.formatEDITDateAsMMDDYYYY(edEffDate);
       }

       EDITDate edTermDate = departmentLocation.getTerminationDate();

       String terminationDate = "";
       if (edTermDate != null)
       {
          terminationDate = DateTimeUtil.formatEDITDateAsMMDDYYYY(edTermDate);
       }

       getCellValues().put(GroupDeptLocTableColumns.COLUMN_DEPT_LOC_CODE.getDisplayText(), deptLocCode);

       getCellValues().put(GroupDeptLocTableColumns.COLUMN_DEPT_LOC_NAME.getDisplayText(), deptLocName);

       getCellValues().put(GroupDeptLocTableColumns.COLUMN_EFFECTIVE_DATE.getDisplayText(), effectiveDate);

       getCellValues().put(GroupDeptLocTableColumns.COLUMN_TERMINATION_DATE.getDisplayText(), terminationDate);
     }

    /**
     * @see edit.portal.widgettoolkit.TableRow#getRowId()
     * @return
     */
    public String getRowId()
    {
        return departmentLocation.getDepartmentLocationPK().toString();
    }
}
