/*
 * User: dlataille
 * Date: May 1, 2007
 * Time: 9:39:14 AM
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package edit.portal.widget;

import edit.portal.widgettoolkit.*;
import contract.*;
import group.ContractGroup;
import fission.utility.*;


public class GroupPRDTableRow extends TableRow
{

    private ChangeHistory changeHistory;


    public GroupPRDTableRow(ChangeHistory prdNonFinancials)
    {
        super();

        this.changeHistory = prdNonFinancials;

        populateCellValues();
    }

   /**
      * Maps the values of Master to the TableRow.
      */
     private void populateCellValues()
     {
         String effectiveDate = DateTimeUtil.formatEDITDateAsMMDDYYYY(changeHistory.getEffectiveDate());

         String processDate = DateTimeUtil.formatEDITDateAsMMDDYYYY(changeHistory.getProcessDate());

         String maintDateTime = DateTimeUtil.formatEDITDateTimeAsMMDDYYYY(changeHistory.getMaintDateTime());

         getCellValues().put(GroupBillingTableModel.COLUMN_EFFECTIVE_DATE, effectiveDate);
         getCellValues().put(GroupBillingTableModel.COLUMN_PROCESS_DATE, processDate);
         getCellValues().put(GroupBillingTableModel.COLUMN_FIELD, changeHistory.getFieldName());

         getCellValues().put(GroupBillingTableModel.COLUMN_PRIOR_VALUE, changeHistory.getBeforeValue());
         getCellValues().put(GroupBillingTableModel.COLUMN_NEW_VALUE, changeHistory.getAfterValue());
         getCellValues().put(GroupBillingTableModel.COLUMN_OPERATOR, changeHistory.getOperator());
         getCellValues().put(GroupBillingTableModel.COLUMN_DATE_TIME, maintDateTime);
     }

    /**
     * @see edit.portal.widgettoolkit.TableRow#getRowId()
     * @return
     */
    public String getRowId()
    {
        return changeHistory.getChangeHistoryPK().toString();
    }
}
