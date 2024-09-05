/*
 * User: dlataille
 * Date: July 10, 2007
 * Time: 9:26:14 AM
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


public class ContractBillingTableRow extends TableRow
{

    private ChangeHistory changeHistory;


    public ContractBillingTableRow(ChangeHistory changeHistory)
    {
        super();

        this.changeHistory = changeHistory;

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

         getCellValues().put(ContractBillingTableModel.COLUMN_EFFECTIVE_DATE, effectiveDate);
         getCellValues().put(ContractBillingTableModel.COLUMN_PROCESS_DATE, processDate);
         getCellValues().put(ContractBillingTableModel.COLUMN_FIELD, changeHistory.getFieldName());

         getCellValues().put(ContractBillingTableModel.COLUMN_PRIOR_VALUE, changeHistory.getBeforeValue());
         getCellValues().put(ContractBillingTableModel.COLUMN_NEW_VALUE, changeHistory.getAfterValue());
         getCellValues().put(ContractBillingTableModel.COLUMN_OPERATOR, changeHistory.getOperator());
         getCellValues().put(ContractBillingTableModel.COLUMN_DATE_TIME, maintDateTime);
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
