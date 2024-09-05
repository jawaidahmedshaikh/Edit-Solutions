/**
 * User: dlataill
 * Date: May 3, 2006
 * Time: 11:16:27 AM
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package edit.portal.widget;

import edit.portal.widgettoolkit.TableRow;
import edit.common.vo.*;
import fission.utility.*;

public class QuoteScheduledEventTableRow  extends TableRow
{
    private EDITTrxVO editTrxVO;
    private String coverage;

    public QuoteScheduledEventTableRow(EDITTrxVO editTrxVO, String coverage)
    {
        this.editTrxVO = editTrxVO;
        this.coverage = coverage;

        populateCellValues();
    }

    /**
     * Maps the values of the given transaction (editTrxVO) to the TableRow
     */
    private void populateCellValues()
    {
        ClientSetupVO clientSetupVO = (ClientSetupVO) editTrxVO.getParentVO(ClientSetupVO.class);
        ContractSetupVO contractSetupVO = (ContractSetupVO) clientSetupVO.getParentVO(ContractSetupVO.class);
        GroupSetupVO groupSetupVO = (GroupSetupVO) contractSetupVO.getParentVO(GroupSetupVO.class);

        String transaction = editTrxVO.getTransactionTypeCT();
        String sequence = editTrxVO.getSequenceNumber() + "";
        String amount = editTrxVO.getTrxAmount().toString();
        String cellValueForAmount = "<script>document.write(formatAsCurrency(" + amount + "))</script>";

        String frequency = "";
        String startDate = "";
        String stopDate = "";

        if (groupSetupVO.getScheduledEventVOCount() > 0)
        {
            ScheduledEventVO scheduledEventVO = groupSetupVO.getScheduledEventVO(0);

            frequency = Util.initString(scheduledEventVO.getFrequencyCT(), "");
            startDate = DateTimeUtil.formatYYYYMMDDToMMDDYYYY(scheduledEventVO.getStartDate());
            stopDate = DateTimeUtil.formatYYYYMMDDToMMDDYYYY(scheduledEventVO.getStopDate());
        }

        getCellValues().put(QuoteScheduledEventTableModel.COLUMN_TRANSACTION, transaction);
        getCellValues().put(QuoteScheduledEventTableModel.COLUMN_FREQUENCY, frequency);
        getCellValues().put(QuoteScheduledEventTableModel.COLUMN_SEQUENCE, sequence);
        getCellValues().put(QuoteScheduledEventTableModel.COLUMN_AMT_PCT, cellValueForAmount);
        getCellValues().put(QuoteScheduledEventTableModel.COLUMN_START_DATE, startDate);
        getCellValues().put(QuoteScheduledEventTableModel.COLUMN_STOP_DATE, stopDate);
        getCellValues().put(QuoteScheduledEventTableModel.COLUMN_COVERAGE, coverage);
    }

    /**
     * @see edit.portal.widgettoolkit.TableRow#getRowId()
     * @return
     */
    public String getRowId()
    {
        return editTrxVO.getEDITTrxPK() + "";
    }
}
