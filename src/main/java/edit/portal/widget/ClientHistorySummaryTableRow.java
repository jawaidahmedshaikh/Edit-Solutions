/*
 * User: dlataille
 * Date: May 2, 2008
 * Time: 9:21:31 AM
 *
 * (c) 2000-2008 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package edit.portal.widget;

import edit.portal.widgettoolkit.*;
import edit.common.CodeTableWrapper;

import fission.utility.*;
import contract.*;

public class ClientHistorySummaryTableRow extends TableRow
{
    ChangeHistory changeHistory;

    public ClientHistorySummaryTableRow(ChangeHistory changeHistory)
    {
        this.changeHistory = changeHistory;

        populateCellValues();
    }

   /**
     * Maps the values of ChangeHistory for the Client to the TableRow.
     */
    private void populateCellValues()
    {
       String effectiveDate = changeHistory.getEffectiveDate() == null ? "" : DateTimeUtil.formatEDITDateAsMMDDYYYY(changeHistory.getEffectiveDate());
       getCellValues().put(ClientHistorySummaryTableModel.COLUMN_EFFECTIVE_DATE, effectiveDate);

       String processDate = changeHistory.getProcessDate() == null ? "" : DateTimeUtil.formatEDITDateAsMMDDYYYY(changeHistory.getProcessDate());
       getCellValues().put(ClientHistorySummaryTableModel.COLUMN_PROCESS_DATE, processDate);

       getCellValues().put(ClientHistorySummaryTableModel.COLUMN_TRAN_TYPE, "NF");
       getCellValues().put(ClientHistorySummaryTableModel.COLUMN_FIELD_NAME, changeHistory.getFieldName());
       getCellValues().put(ClientHistorySummaryTableModel.COLUMN_BEFORE_CHANGE_VALUE, changeHistory.getBeforeValue());
       getCellValues().put(ClientHistorySummaryTableModel.COLUMN_AFTER_CHANGE_VALUE, changeHistory.getAfterValue());
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
