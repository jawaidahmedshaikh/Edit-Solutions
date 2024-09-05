/*
 * User: gfrosti
 * Date: Mar 10, 2004
 * Time: 9:23:46 AM
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package event.business;

import edit.services.component.IUseCase;
import edit.common.EDITDate;
import edit.common.EDITBigDecimal;
import event.CashBatchContract;
import event.Suspense;


public interface EventUseCase extends IUseCase
{
    public void accessTransaction();

    public void addAdjustDownTransaction();
    public void updateAdjustDownTransaction();
    public void reverseAdjustDownTransaction();
    public void adjustAdjustDownTaxableHistory();

    public void addAdjustUpTransaction();
    public void updateAdjustUpTransaction();
    public void reverseAdjustUpTransaction();
    public void adjustAdjustUpTaxableHistory();

    public void addAssetAllocationTransaction();
    public void updateAssetAllocationTransaction();
    public void reverseAssetAllocationTransaction();
    public void adjustAssetAllocationTaxableHistory();

    public void addDeathTransaction();
    public void updateDeathTransaction();
    public void reverseDeathTransaction();
    public void adjustDeathTaxableHistory();

    public void addDecreaseTransaction();
    public void updateDecreaseTransaction();
    public void reverseDecreaseTransaction();
    public void adjustDecreaseTaxableHistory();

    public void addForcedPayoutTransaction();
    public void updateForcedPayoutTransaction();
    public void reverseForcedPayoutTransaction();
    public void adjustForcedPayoutTaxableHistory();

    public void addFullSurrenderTransaction();
    public void updateFullSurrenderTransaction();
    public void reverseFullSurrenderTransaction();
    public void adjustFullSurrenderTaxableHistory();

    public void addIncreaseTransaction();
    public void updateIncreaseTransaction();
    public void reverseIncreaseTransaction();
    public void adjustIncreaseTaxableHistory();

    public void addLoanTransaction();
    public void updateLoanTransaction();
    public void reverseLoanTransaction();
    public void adjustLoanTaxableHistory();

    public void addLoanRepaymentTransaction();
    public void updateLoanRepaymentTransaction();
    public void reverseLoanRepaymentTransaction();
    public void adjustLoanRepaymentTaxableHistory();

    public void addLumpSumTransaction();
    public void updateLumpSumTransaction();
    public void reverseLumpSumTransaction();
    public void adjustLumpSumTaxableHistory();

    public void addNotTakenTransaction();
    public void updateNotTakenTransaction();
    public void reverseNotTakenTransaction();
    public void adjustNotTakenTaxableHistory();

    public void addPayoutTransaction();
    public void updatePayoutTransaction();
    public void reversePayoutTransaction();
    public void adjustPayoutTaxableHistory();

    public void addPortfolioRebalancingTransaction();
    public void updatePortfolioRebalancingTransaction();
    public void reversePortfolioRebalancingTransaction();
    public void adjustPortfolioRebalancingTaxableHistory();

    public void addPremiumTransaction();
    public void updatePremiumTransaction();
    public void reversePremiumTransaction();
    public void adjustPremiumTaxableHistory();

    public void addSystematicWithdrawalTransaction();
    public void updateSystematicWithdrawalTransaction();
    public void reverseSystematicWithdrawalTransaction();
    public void adjustSystematicWithdrawalTaxableHistory();

    public void addTransferTransaction();
    public void updateTransferTransaction();
    public void reverseTransferTransaction();
    public void adjustTransferTaxableHistory();

    public void addWithdrawalTransaction();
    public void updateWithdrawalTransaction();
    public void reverseWithdrawalTransaction();
    public void adjustWithdrawalTaxableHistory();

    public void addPremiumLoanTransaction();
    public void updatePremiumLoanTransaction();
    public void reversePremiumLoanTransaction();
    public void adjustPremiumLoanTaxableHistory();

    public void updateClaimPayoutTransaction();
    public void reverseClaimPayoutTransaction();
    public void adjustClaimPayoutTaxableHistory();

    public void adjustCommissionHistory();

    public void accessHedgeFundRedemption();

    public CashBatchContract createCashBatch(EDITDate batchDate, EDITBigDecimal batchAmount,
                                             String operator, String companyName, String groupNumber, EDITDate dueDate);

    public String releaseCashBatch(CashBatchContract cashBatchContract);

    public String saveSuspenseForCashBatch(Suspense suspense, Long depositsPK);

    public String voidCashBatchContract(CashBatchContract cashBatchContract);

    public String deleteCashBatchSuspense(Suspense suspense);

    public String voidSuspense(Suspense suspense);

    public String refundSuspense(Suspense originalSuspense, Suspense refundSuspense);

    public String saveSuspense(Suspense suspense);

    public void transferSuspense();
    /**
     * Use Case security check-point.
     */
    public void accessTransactionProcess();

    /**
     * Use Case security check-point.
     */
    public void accessTransactionEmployerEmployee();

    /**
     * Use Case security check-point.
     */
    public void accessBillAmtEditOverride();
}
