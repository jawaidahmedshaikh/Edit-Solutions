/*
 * User: cgleason
 * Date: Dec 13, 2005
 * Time: 2:33:14 PM
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package edit.portal.widget;

import edit.portal.widgettoolkit.*;
import edit.common.EDITDate;
import contract.*;
import group.*;
import fission.utility.DateTimeUtil;


public class GroupSummaryTableRow extends TableRow
{

    private ContractGroup groupContractGroup;


    public GroupSummaryTableRow(ContractGroup groupContractGroup)
    {
        super();

        this.groupContractGroup = groupContractGroup;

        populateCellValues();
    }

   /**
      * Maps the values of Master to the TableRow.
      */
     private void populateCellValues()
     {
         String groupName = groupContractGroup.getOwnerClient().getName();

         String groupNumber = groupContractGroup.getContractGroupNumber();

         EDITDate effDate = groupContractGroup.getEffectiveDate();

         String effectiveDate = "";

         if (effDate != null)
         {
             effectiveDate = DateTimeUtil.formatEDITDateAsMMDDYYYY(groupContractGroup.getEffectiveDate());
         }

         String terminationDate = DateTimeUtil.formatEDITDateAsMMDDYYYY(groupContractGroup.getTerminationDate());

         getCellValues().put(GroupSummaryTableModel.COLUMN_GROUP_NUMBER, groupNumber);
         
         getCellValues().put(GroupSummaryTableModel.COLUMN_GROUP_NAME, groupName);

         getCellValues().put(GroupSummaryTableModel.COLUMN_EFFECTIVE_DATE, effectiveDate);

         getCellValues().put(GroupSummaryTableModel.COLUMN_TERMINATION_DATE, terminationDate);
     }

    /**
     * @see edit.portal.widgettoolkit.TableRow#getRowId()
     * @return
     */
    public String getRowId()
    {
        return groupContractGroup.getContractGroupPK().toString();
    }
}
