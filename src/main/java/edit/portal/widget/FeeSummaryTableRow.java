/*
 * User: dlataill
 * Date: Sep 8, 2006
 * Time: 12:32:17 PM
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement
 */
package edit.portal.widget;

import engine.FeeFilterRow;
import engine.ChargeCode;

import edit.portal.widgettoolkit.TableRow;
import edit.common.vo.ChargeCodeVO;
import fission.utility.*;

public class FeeSummaryTableRow extends TableRow
{
    FeeFilterRow feeFilterRow;

    public FeeSummaryTableRow(FeeFilterRow feeFilterRow)
    {
        this.feeFilterRow = feeFilterRow;

        populateCellValues();
    }

    /**
      * Maps the values of History Filter to the TableRow.
      */
    private void populateCellValues()
    {
        String acctPendingInd = Util.initString(feeFilterRow.getAccountingPendingStatus(), "N");
        getCellValues().put(FeeSummaryTableModel.COLUMN_ACCT_PENDING, acctPendingInd);

        String effectiveDate = feeFilterRow.getEffectiveDate() == null ? "" : DateTimeUtil.formatEDITDateAsMMDDYYYY(feeFilterRow.getEffectiveDate());
        getCellValues().put(FeeSummaryTableModel.COLUMN_EFFECTIVE_DATE, effectiveDate);

        String processDate = feeFilterRow.getProcessDate() == null ? "" : DateTimeUtil.formatEDITDateAsMMDDYYYY(feeFilterRow.getProcessDate());
        getCellValues().put(FeeSummaryTableModel.COLUMN_PROCESS_DATE, processDate);

        String releaseDate = feeFilterRow.getReleaseDate() == null ? "" : DateTimeUtil.formatEDITDateAsMMDDYYYY(feeFilterRow.getReleaseDate());
        getCellValues().put(FeeSummaryTableModel.COLUMN_RELEASE_DATE, releaseDate);

        String amount = feeFilterRow.getAmount().toString();
        String cellValueForAmount = "<script>document.write(formatAsCurrency(" + amount + "))</script>";
        String chargeCode = getChargeCode(feeFilterRow.getChargeCode());

        getCellValues().put(FeeSummaryTableModel.COLUMN_TRAN_TYPE, feeFilterRow.getTransactionTypeCT());
        getCellValues().put(FeeSummaryTableModel.COLUMN_STATUS, Util.initString(feeFilterRow.getStatus(), ""));
        getCellValues().put(FeeSummaryTableModel.COLUMN_AMOUNT, cellValueForAmount);
        getCellValues().put(FeeSummaryTableModel.COLUMN_CHARGE_CODE, chargeCode);
    }

    private String getChargeCode(String chargeCodeFK)
    {
        ChargeCode chargeCodeEntity = ChargeCode.findByPK(Long.parseLong(chargeCodeFK));
        ChargeCodeVO chargeCodeVO = null;
        if (chargeCodeEntity != null)
        {
            chargeCodeVO = (ChargeCodeVO) chargeCodeEntity.getVO();
        }

        String chargeCode = "";

        if (chargeCodeVO != null)
        {
            chargeCode = chargeCodeVO.getChargeCode();
        }

        return chargeCode;
    }

    /**
     * @see edit.portal.widgettoolkit.TableRow#getRowId()
     * @return
     */
    public String getRowId()
    {
        return feeFilterRow.getKey();
    }
}
