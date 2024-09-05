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
import edit.common.vo.*;
import edit.common.*;

import fission.utility.*;


public class PremiumDueHistoryTableRow extends TableRow
{
      PremiumDueVO premiumDueVO;

    public PremiumDueHistoryTableRow(PremiumDueVO premiumDueVO)
    {
        this.premiumDueVO = premiumDueVO;
        populateCellValues();
    }

   /**
     * Maps the values of Suspense to the TableRow.
     */
    private void populateCellValues()
    {
        EDITBigDecimal totalMEP = new EDITBigDecimal();
        if (premiumDueVO.getCommissionPhaseVOCount() > 0)
        {
            CommissionPhaseVO[] commissionPhaseVOs = premiumDueVO.getCommissionPhaseVO();
            if (commissionPhaseVOs != null)
            {
                for (int i = 0; i < commissionPhaseVOs.length; i++)
                {
                    totalMEP = totalMEP.addEditBigDecimal(commissionPhaseVOs[i].getExpectedMonthlyPremium());
                }
            }
        }

        String billAmount = premiumDueVO.getBillAmount().toString();
        String cellValueForBillAmount = "<script>document.write(formatAsCurrency(" + billAmount + "))</script>";
        getCellValues().put(PremiumDueHistoryTableModel.COLUMN_BILL_AMOUNT, cellValueForBillAmount);

        String deductionAmount = premiumDueVO.getDeductionAmount().toString();
        String cellValueForDeductionAmount = "<script>document.write(formatAsCurrency(" + deductionAmount + "))</script>";
        getCellValues().put(PremiumDueHistoryTableModel.COLUMN_DEDUCTION_AMOUNT, cellValueForDeductionAmount);

        String numberOfDeductions = premiumDueVO.getNumberOfDeductions() + "";
        getCellValues().put(PremiumDueHistoryTableModel.COLUMN_NUMBER_OF_DEDUCTIONS, numberOfDeductions);

        String effectiveDate = DateTimeUtil.formatYYYYMMDDToMMDDYYYY(premiumDueVO.getEffectiveDate());
        getCellValues().put(PremiumDueHistoryTableModel.COLUMN_EFFECTIVE_DATE, effectiveDate);

        getCellValues().put(PremiumDueHistoryTableModel.COLUMN_STATUS, Util.initString(premiumDueVO.getPendingExtractIndicator(), ""));

        String totalMEPString = Util.initString(totalMEP.toString(), "");
        String cellValueForMEP = "<script>document.write(formatAsCurrency(" + totalMEPString + "))</script>";
        getCellValues().put(PremiumDueHistoryTableModel.COLUMN_MEP, cellValueForMEP);

        String billAmountOverride = premiumDueVO.getBillAmountOverride().toString();
        String cellValueForBillAmountOverride = "<script>document.write(formatAsCurrency(" + billAmountOverride + "))</script>";
        getCellValues().put(PremiumDueHistoryTableModel.COLUMN_BILL_AMOUNT_OVERRIDE, cellValueForBillAmountOverride);

        String deductionAmountOverride = premiumDueVO.getDeductionAmountOverride().toString();
        String cellValueForDeductionAmountOverride = "<script>document.write(formatAsCurrency(" + deductionAmountOverride + "))</script>";
        getCellValues().put(PremiumDueHistoryTableModel.COLUMN_DEDUCTION_AMOUNT_OVERRIDE, cellValueForDeductionAmountOverride);
    }

    /**
     * @see edit.portal.widgettoolkit.TableRow#getRowId()
     * @return
     */
    public String getRowId()
    {
        return premiumDueVO.getPremiumDuePK() + "";
    }
}
