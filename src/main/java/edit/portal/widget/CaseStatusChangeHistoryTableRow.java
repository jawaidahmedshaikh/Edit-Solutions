/*
 * User: dlataille
 * Date: July 18, 2007
 * Time: 12:22:14 PM
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package edit.portal.widget;

import edit.portal.widgettoolkit.*;
import edit.common.EDITDate;
import edit.common.EDITDateTime;
import group.CaseStatusChangeHistory;


public class
        CaseStatusChangeHistoryTableRow extends TableRow
{

    private CaseStatusChangeHistory caseStatusChangeHistory;


    public CaseStatusChangeHistoryTableRow(CaseStatusChangeHistory caseStatusChangeHistory)
    {
        super();

        this.caseStatusChangeHistory = caseStatusChangeHistory;

        populateCellValues();
    }

    /**
     * Maps the values of Master to the TableRow.
     */
    private void populateCellValues()
    {
        String statusCT = caseStatusChangeHistory.getStatusCT();

        String priorStatusCT = caseStatusChangeHistory.getPriorStatusCT();

        EDITDate changeEffectiveDate = caseStatusChangeHistory.getChangeEffectiveDate();

        String operator = caseStatusChangeHistory.getOperator();

        EDITDateTime maintDateTime = caseStatusChangeHistory.getMaintDateTime();

        getCellValues().put(CaseStatusChangeHistoryTableModel.COLUMN_STATUS, statusCT);

        getCellValues().put(CaseStatusChangeHistoryTableModel.COLUMN_PRIOR_STATUS, priorStatusCT);

        getCellValues().put(CaseStatusChangeHistoryTableModel.COLUMN_CHG_EFF_DATE, changeEffectiveDate.getFormattedDate());

        getCellValues().put(CaseStatusChangeHistoryTableModel.COLUMN_OPERATOR, operator);

        getCellValues().put(CaseStatusChangeHistoryTableModel.COLUMN_DATE_TIME, maintDateTime.getFormattedDateTime());
    }

    /**
     * @see edit.portal.widgettoolkit.TableRow#getRowId()
     * @return
     */
    public String getRowId()
    {
        return caseStatusChangeHistory.getCaseStatusChangeHistoryPK().toString();
    }
}
