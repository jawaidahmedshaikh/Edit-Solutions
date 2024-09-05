/*
 * User: cgleason
 * Date: Feb 22, 2006
 * Time: 9:28:31 AM
 *
 * (c) 2000-2008 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package edit.portal.widget;

import edit.portal.widgettoolkit.*;

import fission.utility.*;
import contract.*;


public class CommissionPhaseHistoryTableRow extends TableRow
{
    CommissionPhase commissionPhase;
    String agentNumber;

    public CommissionPhaseHistoryTableRow(CommissionPhase commissionPhase, String agentNumber)
    {
        this.commissionPhase = commissionPhase;
        this.agentNumber = agentNumber;

        populateCellValues();
    }

   /**
     * Maps the values of CommissionPhase to the TableRow.
     */
    private void populateCellValues()
    {
        String commissionPhaseId = commissionPhase.getCommissionPhaseID() + "";
        getCellValues().put(CommissionPhaseHistoryTableModel.COLUMN_COMMISSION_PHASE_ID, commissionPhaseId);

        String effectiveDate = DateTimeUtil.formatYYYYMMDDToMMDDYYYY(commissionPhase.getEffectiveDate().getFormattedDate());
        getCellValues().put(CommissionPhaseHistoryTableModel.COLUMN_EFFECTIVE_DATE, effectiveDate);

        String expectedMonthlyPremium = commissionPhase.getExpectedMonthlyPremium().toString();
        String cellValueForEMP = "<script>document.write(formatAsCurrency(" + expectedMonthlyPremium + "))</script>";
        getCellValues().put(CommissionPhaseHistoryTableModel.COLUMN_EXPECTED_MONTHLY_PREMIUM, cellValueForEMP);

        getCellValues().put(CommissionPhaseHistoryTableModel.COLUMN_AGENT_NUMBER, agentNumber);
    }

    /**
     * @see edit.portal.widgettoolkit.TableRow#getRowId()
     * @return
     */
    public String getRowId()
    {
        return commissionPhase.getCommissionPhasePK() + "";
    }
}
