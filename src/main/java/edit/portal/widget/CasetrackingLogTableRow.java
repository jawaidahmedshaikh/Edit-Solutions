/*
 * User: sprasad
 * Date: Jun 24, 2005
 * Time: 1:04:51 PM
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package edit.portal.widget;

import edit.portal.widgettoolkit.TableRow;
import edit.common.EDITDate;
import edit.common.EDITBigDecimal;

import casetracking.CasetrackingLog;
import fission.utility.*;



public class CasetrackingLogTableRow extends TableRow
{
    private CasetrackingLog casetrackingLog;

    public CasetrackingLogTableRow(CasetrackingLog casetrackingLog)
    {
        super();

        this.casetrackingLog = casetrackingLog;

        populateCellValues();
    }

    /**
     * Maps the values of ContractClient to the TableRow.
     */
    private void populateCellValues()
    {
        getCellValues().put(CasetrackingLogTableModel.COLUMN_PROCESS, casetrackingLog.getCaseTrackingProcess());
        getCellValues().put(CasetrackingLogTableModel.COLUMN_TRXTYPE, casetrackingLog.getCaseTrackingEvent());
        getCellValues().put(CasetrackingLogTableModel.COLUMN_POLICY_NUMBER, casetrackingLog.getContractNumber());

        EDITDate effectiveDate = casetrackingLog.getEffectiveDate();
        EDITDate processDate = casetrackingLog.getProcessDate();

        getCellValues().put(CasetrackingLogTableModel.COLUMN_EFF_DATE, effectiveDate == null ? null : DateTimeUtil.formatEDITDateAsMMDDYYYY(effectiveDate));
        getCellValues().put(CasetrackingLogTableModel.COLUMN_PROCESS_DATE, processDate == null ? null : DateTimeUtil.formatEDITDateAsMMDDYYYY(processDate));
        getCellValues().put(CasetrackingLogTableModel.COLUMN_NAME, casetrackingLog.getClientName());

        EDITBigDecimal allocationPercent = casetrackingLog.getAllocationPercent();

        getCellValues().put(CasetrackingLogTableModel.COLUMN_ALLOC_PERCENT, allocationPercent == null ? null : allocationPercent.trim());
        getCellValues().put(CasetrackingLogTableModel.COLUMN_NEW_POLICY_NUMBER, casetrackingLog.getNewContractNumber());
    }

    /**
     * @see TableRow#getRowId()
     * @return
     */
    public String getRowId()
    {
        return casetrackingLog.getCasetrackingLogPK().toString();
    }
}
