/*
 * User: gfrosti
 * Date: Mar 10, 2004
 * Time: 9:23:55 AM
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package event.component;

import event.business.EventUseCase;
import event.CashBatchContract;
import event.Suspense;
import edit.common.EDITDate;
import edit.common.EDITBigDecimal;
import edit.services.db.hibernate.SessionHelper;
import edit.services.logging.Logging;

import java.util.Set;
import java.util.Iterator;

import logging.*;
import contract.Deposits;
import engine.Company;


public class EventUseCaseComponent implements EventUseCase
{
    public void accessTransaction()
    {
    }

    public void addAdjustDownTransaction()
    {
    }

    public void updateAdjustDownTransaction()
    {
    }

    public void reverseAdjustDownTransaction()
    {
    }

    public void adjustAdjustDownTaxableHistory()
    {
    }

    public void addAdjustUpTransaction()
    {
    }

    public void updateAdjustUpTransaction()
    {
    }

    public void reverseAdjustUpTransaction()
    {
    }

    public void adjustAdjustUpTaxableHistory()
    {
    }

    public void addAssetAllocationTransaction()
    {
    }

    public void updateAssetAllocationTransaction()
    {
    }

    public void reverseAssetAllocationTransaction()
    {
    }

    public void adjustAssetAllocationTaxableHistory()
    {
    }

    public void addDeathTransaction()
    {
    }

    public void updateDeathTransaction()
    {
    }

    public void reverseDeathTransaction()
    {
    }

    public void adjustDeathTaxableHistory()
    {
    }

    public void addDecreaseTransaction()
    {
    }

    public void updateDecreaseTransaction()
    {
    }

    public void reverseDecreaseTransaction()
    {
    }

    public void adjustDecreaseTaxableHistory()
    {
    }

    public void addForcedPayoutTransaction()
    {
    }

    public void updateForcedPayoutTransaction()
    {
    }

    public void reverseForcedPayoutTransaction()
    {
    }

    public void adjustForcedPayoutTaxableHistory()
    {
    }

    public void addFullSurrenderTransaction()
    {
    }

    public void updateFullSurrenderTransaction()
    {
    }

    public void reverseFullSurrenderTransaction()
    {
    }

    public void adjustFullSurrenderTaxableHistory()
    {
    }

    public void addSurrenderOverloanTransaction()
    {
    }

    public void updateSurrenderOverloanTransaction()
    {
    }

    public void reverseSurrenderOverloanTransaction()
    {
    }

    public void adjustSurrenderOverloanTaxableHistory()
    {
    }

    public void addIncreaseTransaction()
    {
    }

    public void updateIncreaseTransaction()
    {
    }

    public void reverseIncreaseTransaction()
    {
    }

    public void adjustIncreaseTaxableHistory()
    {
    }

    public void addLoanTransaction()
    {
    }

    public void updateLoanTransaction()
    {
    }

    public void reverseLoanTransaction()
    {
    }

    public void adjustLoanTaxableHistory()
    {
    }

    public void addLoanRepaymentTransaction()
    {
    }

    public void updateLoanRepaymentTransaction()
    {
    }

    public void reverseLoanRepaymentTransaction()
    {
    }

    public void adjustLoanRepaymentTaxableHistory()
    {
    }

    public void addLumpSumTransaction()
    {
    }

    public void updateLumpSumTransaction()
    {
    }

    public void reverseLumpSumTransaction()
    {
    }

    public void adjustLumpSumTaxableHistory()
    {
    }

    public void addNotTakenTransaction()
    {
    }

    public void updateNotTakenTransaction()
    {
    }

    public void reverseNotTakenTransaction()
    {
    }

    public void adjustNotTakenTaxableHistory()
    {
    }

    public void addPayoutTransaction()
    {
    }

    public void updatePayoutTransaction()
    {
    }

    public void reversePayoutTransaction()
    {
    }

    public void adjustPayoutTaxableHistory()
    {
    }

    public void addPortfolioRebalancingTransaction()
    {
    }

    public void updatePortfolioRebalancingTransaction()
    {
    }

    public void reversePortfolioRebalancingTransaction()
    {
    }

    public void adjustPortfolioRebalancingTaxableHistory()
    {
    }

    public void addPremiumTransaction()
    {
    }

    public void updatePremiumTransaction()
    {
    }

    public void reversePremiumTransaction()
    {
    }

    public void adjustPremiumTaxableHistory()
    {
    }

    public void addSystematicWithdrawalTransaction()
    {
    }

    public void updateSystematicWithdrawalTransaction()
    {
    }

    public void reverseSystematicWithdrawalTransaction()
    {
    }

    public void adjustSystematicWithdrawalTaxableHistory()
    {
    }

    public void addTransferTransaction()
    {
    }

    public void updateTransferTransaction()
    {
    }

    public void reverseTransferTransaction()
    {
    }

    public void adjustTransferTaxableHistory()
    {
    }

    public void addWithdrawalTransaction()
    {
    }

    public void updateWithdrawalTransaction()
    {
    }

    public void reverseWithdrawalTransaction()
    {
    }

    public void adjustWithdrawalTaxableHistory()
    {
    }

    public void adjustCommissionHistory()
    {
    }

    public void accessHedgeFundRedemption()
    {
    }

    public void reversePremiumLoanTransaction()
    {
    }

    public void adjustPremiumLoanTaxableHistory()
    {
    }

    public void addPremiumLoanTransaction()
    {

    }

    public void updatePremiumLoanTransaction()
    {
    }

    public void reverseClaimPayoutTransaction()
    {
    }

    public void adjustClaimPayoutTaxableHistory()
    {
    }

    public void updateClaimPayoutTransaction()
    {
    }

    /**
     * Create a new CashBatchContract Record using the given date, amount and operator
     * @param batchDate
     * @param batchAmount
     * @param operator
     * @return
     */
    public CashBatchContract createCashBatch(EDITDate batchDate, EDITBigDecimal batchAmount,
                                             String operator, String companyName, String groupNumber, EDITDate dueDate)
    {
        Company company = Company.findBy_CompanyName(companyName);

        SessionHelper.beginTransaction(SessionHelper.EDITSOLUTIONS);

        CashBatchContract cashBatchContract = new CashBatchContract();
        cashBatchContract.setAmount(batchAmount);
        cashBatchContract.setCreationDate(batchDate);
        cashBatchContract.setCreationOperator(operator);
        cashBatchContract.setTotalBatchItems(new Integer(0));
        cashBatchContract.setReleaseIndicator(CashBatchContract.RELEASE_INDICATOR_PENDING);
        cashBatchContract.setAccountingPendingIndicator("Y");
        cashBatchContract.setGroupNumber(groupNumber);
        cashBatchContract.setDueDate(dueDate);
        cashBatchContract.setCompanyFK(company.getCompanyPK());

        SessionHelper.saveOrUpdate(cashBatchContract, SessionHelper.EDITSOLUTIONS);

        //Now get newly generated PK to set the BatchId.
        cashBatchContract.setBatchID("B" + cashBatchContract.getCashBatchContractPK());
        SessionHelper.saveOrUpdate(cashBatchContract, SessionHelper.EDITSOLUTIONS);

        SessionHelper.commitTransaction(SessionHelper.EDITSOLUTIONS);

        SessionHelper.clearSessions();

        return cashBatchContract;
    }

    /**
     * Sets the CashBatchContract record to a released status ("R") and sets
     * the accountingPendingIndicatory to "Y".  The CashBatch is ready for use.
     * @param cashBatchContract
     *
     */

    /**
     * Sets the CashBatchContract record to a released status ("R") and sets
     * the accountingPendingIndicatory to "Y".  The CashBatch is ready for use.
     * @param cashBatchContract
     * @return responseMessage (was the release of the CashBatchContract successful?)
     */
    public String releaseCashBatch(CashBatchContract cashBatchContract)
    {
        String responseMessage = "Batch Number " + cashBatchContract.getBatchID() + " Has Been Successfully Released";

        // Edit to make sure the suspense amount(s) entered total to match the cash batch amount
        EDITBigDecimal batchAmount = cashBatchContract.getAmount();
        EDITBigDecimal suspenseTotal = new EDITBigDecimal();
        Set suspenseSet = cashBatchContract.getSuspenses();

        Iterator it = suspenseSet.iterator();
        while (it.hasNext())
        {
            Suspense suspense = (Suspense) it.next();
            suspenseTotal = suspenseTotal.addEditBigDecimal(suspense.getSuspenseAmount());
        }

        try
        {
            if (!suspenseTotal.isEQ(batchAmount))
            {
                responseMessage = "Batch Number " + cashBatchContract.getBatchID() + " Cannot Be Released - Suspense Entry Total Does Not Match Cash Batch Total";
            }
            else
            {
                cashBatchContract.setReleaseIndicator(CashBatchContract.RELEASE_INDICATOR_RELEASE);

                SessionHelper.beginTransaction(SessionHelper.EDITSOLUTIONS);

                SessionHelper.saveOrUpdate(cashBatchContract, SessionHelper.EDITSOLUTIONS);

                Iterator it2 = suspenseSet.iterator();

                while (it2.hasNext())
                {
                    Suspense suspense = (Suspense) it2.next();
                    suspense.setAccountingPendingInd("Y");
                    suspense.setProcessDate(new EDITDate());

                    SessionHelper.saveOrUpdate(suspense, SessionHelper.EDITSOLUTIONS);
                }

                SessionHelper.commitTransaction(SessionHelper.EDITSOLUTIONS);
            }
        }
        catch(Exception e)
        {
            SessionHelper.rollbackTransaction(SessionHelper.EDITSOLUTIONS);

            responseMessage = "Batch Number " + cashBatchContract.getBatchID() + " Was Not Saved - See Loggging For Error Information";

            Logging.getLogger(Logging.GENERAL_EXCEPTION).error(new LogEvent(e));

            Log.logGeneralExceptionToDatabase(responseMessage, e);
        }
        finally
        {
            SessionHelper.clearSessions();

            return responseMessage;
        }
    }

    public String saveSuspenseForCashBatch(Suspense suspense, Long depositsPK)
    {
        String responseMessage = "Suspense Entry Save was Successful";

        try
        {
            Long cashBatchContractFK = suspense.getCashBatchContractFK();

            // A valid CashBatch should be existing at this point.
            CashBatchContract cashBatchContract = CashBatchContract.findByPK(cashBatchContractFK);

            String releaseIndicator = cashBatchContract.getReleaseIndicator();

            if (releaseIndicator != null && !releaseIndicator.equalsIgnoreCase(CashBatchContract.RELEASE_INDICATOR_PENDING))
            {
                responseMessage = "Change Not Allowed";

                return responseMessage;
            }

            Deposits deposits = Deposits.findByPK(depositsPK);
            if (deposits != null)
            {
                suspense.setTaxYear(deposits.getTaxYear());
            }


            if (deposits != null)
            {
                suspense.setTaxYear(deposits.getTaxYear());
            }

            SessionHelper.beginTransaction(SessionHelper.EDITSOLUTIONS);

            SessionHelper.saveOrUpdate(suspense, SessionHelper.EDITSOLUTIONS);
            
            if (deposits != null)
            {
                deposits.setSuspense(suspense);
                deposits.setAmountReceived(suspense.getSuspenseAmount());
                deposits.setDateReceived(suspense.getEffectiveDate());
                SessionHelper.saveOrUpdate(deposits, SessionHelper.EDITSOLUTIONS);
            }

            SessionHelper.commitTransaction(SessionHelper.EDITSOLUTIONS);
        }
        catch(Exception e)
        {
            SessionHelper.rollbackTransaction(SessionHelper.EDITSOLUTIONS);

            responseMessage = "Suspense Entry Save Errored - See Logging For Error Information";

            Logging.getLogger(Logging.GENERAL_EXCEPTION).error(new LogEvent(e));

            Log.logGeneralExceptionToDatabase(responseMessage, e);
        }
        finally
        {
            SessionHelper.clearSessions();

            return responseMessage;
        }
    }

    /**
     * Delete the CashBatchContract Record
     * @param cashBatchContract
     */
    public String voidCashBatchContract(CashBatchContract cashBatchContract)
    {
        String responseMessage = "Cash Batch Sucessfully Voided";

        try
        {
            if (cashBatchContract.getReleaseIndicator().equalsIgnoreCase(CashBatchContract.RELEASE_INDICATOR_RELEASE))
            {
                responseMessage = "Cash Batch Has Already Been Released - Cannot Void";
            }
            else
            {
                SessionHelper.beginTransaction(SessionHelper.EDITSOLUTIONS);

                Suspense[] suspenses = Suspense.findAllByCashBatchContract(cashBatchContract);

                for (int i = 0; i < suspenses.length; i++)
                {
                    suspenses[i].setMaintenanceInd("V");
                    SessionHelper.saveOrUpdate(suspenses[i], SessionHelper.EDITSOLUTIONS);

                    Deposits[] deposits = Deposits.findBySuspenseFK(suspenses[i].getSuspensePK());

                    for (int j = 0; j < deposits.length; j++)
                    {
                        deposits[j].setAmountReceived(new EDITBigDecimal());
                        deposits[j].setDateReceived(null);
                        deposits[j].setSuspenseFK(null);

                        SessionHelper.saveOrUpdate(deposits[j], SessionHelper.EDITSOLUTIONS);
                    }
                }


                if (cashBatchContract.getAccountingPendingIndicator().equalsIgnoreCase("N"))
                {
                    cashBatchContract.setAccountingPendingIndicator("Y");
                    cashBatchContract.setReleaseIndicator(CashBatchContract.RELEASE_INDICATOR_VOID);
                }
                else
                {
                    cashBatchContract.setAccountingPendingIndicator("N");
                    cashBatchContract.setReleaseIndicator(CashBatchContract.RELEASE_INDICATOR_VOID);
                }

                SessionHelper.saveOrUpdate(cashBatchContract, SessionHelper.EDITSOLUTIONS);

                SessionHelper.commitTransaction(SessionHelper.EDITSOLUTIONS);
            }
        }
        catch(Exception e)
        {
            SessionHelper.rollbackTransaction(SessionHelper.EDITSOLUTIONS);

            responseMessage = "Cash Batch Void Errored - See Logging For Error Information";

            Logging.getLogger(Logging.GENERAL_EXCEPTION).error(new LogEvent(e));

            Log.logGeneralExceptionToDatabase(responseMessage, e);
        }
        finally
        {
            return responseMessage;
        }
    }

    public String deleteCashBatchSuspense(Suspense suspense)
    {
        String responseMessage = "Suspense Entry Successfully Deleted";

        try
        {
            SessionHelper.beginTransaction(SessionHelper.EDITSOLUTIONS);

            CashBatchContract cashBatchContract = suspense.getCashBatchContract();
            cashBatchContract.deleteSuspense(suspense);

            SessionHelper.delete(suspense, SessionHelper.EDITSOLUTIONS);

            Deposits[] deposits = Deposits.findBySuspenseFK(suspense.getSuspensePK());

            if (deposits != null)
            {
                for (int i = 0; i < deposits.length; i++)
                {
                    deposits[i].setAmountReceived(new EDITBigDecimal());
                    deposits[i].setDateReceived(null);
                    deposits[i].setSuspenseFK(null);

                    SessionHelper.saveOrUpdate(deposits[i], SessionHelper.EDITSOLUTIONS);
                }
            }

            SessionHelper.commitTransaction(SessionHelper.EDITSOLUTIONS);
        }
        catch(Exception e)
        {
            SessionHelper.rollbackTransaction(SessionHelper.EDITSOLUTIONS);

            responseMessage = "Suspense Entry Deletion Errored - See Logging For Error Information";

            Logging.getLogger(Logging.GENERAL_EXCEPTION).error(new LogEvent(e));

            Log.logGeneralExceptionToDatabase(responseMessage, e);
        }
        finally
        {
            return responseMessage;
        }
    }

    /**
     * Voids the specified suspense entry - will no longer be available for use
     * @param suspense
     * @return
     */
    public String voidSuspense(Suspense suspense)
    {
        String responseMessage = "Suspense Successfully Voided";

        try
        {
            if (suspense.getSuspensePK() != null)
            {
                Suspense existingSuspense = Suspense.findByPK(suspense.getSuspensePK());

                CashBatchContract cashBatchContract = existingSuspense.getCashBatchContract();

                if (existingSuspense.getPendingSuspenseAmount().isGT(EDITBigDecimal.EDIT_DECIMAL_ZILCH_STR))
                {
                    responseMessage = "Suspense Cannot Be Voided - Transaction Pending";
                }
                else if (existingSuspense.getSuspenseAmount().isEQ(EDITBigDecimal.EDIT_DECIMAL_ZILCH_STR))
                {
                    responseMessage = "Suspense Cannot Be Voided - Suspense Amount Is Zero";
                }
                else if (cashBatchContract != null && cashBatchContract.getReleaseIndicator().equalsIgnoreCase(CashBatchContract.RELEASE_INDICATOR_PENDING))
                {
                    responseMessage = "Suspense Should Be Deleted, Not Voided - Cash Batch Has Not Been Released Yet";
                }
                else if (cashBatchContract == null &&
                         existingSuspense.getAccountingPendingInd().equalsIgnoreCase("Y") &&
                         existingSuspense.getPendingSuspenseAmount().isEQ(EDITBigDecimal.EDIT_DECIMAL_ZILCH_STR))
                {
                    SessionHelper.beginTransaction(SessionHelper.EDITSOLUTIONS);

                    SessionHelper.delete(existingSuspense, SessionHelper.EDITSOLUTIONS);

                    SessionHelper.commitTransaction(SessionHelper.EDITSOLUTIONS);

                    responseMessage = "Suspense Entry Was Deleted Not Voided - Suspense Was Never Accounted For Or Applied";
                }
                else
                {
                    SessionHelper.beginTransaction(SessionHelper.EDITSOLUTIONS);

                    existingSuspense.setVoidDefaults(existingSuspense);

                    SessionHelper.saveOrUpdate(existingSuspense, SessionHelper.EDITSOLUTIONS);

                    SessionHelper.commitTransaction(SessionHelper.EDITSOLUTIONS);
                }
            }
            else
            {
                responseMessage = "Please Select Suspense Entry For Void";
            }
        }
        catch(Exception e)
        {
            SessionHelper.rollbackTransaction(SessionHelper.EDITSOLUTIONS);

            responseMessage = "Suspense Entry Void Errored - See Logging For Error Information";

            Logging.getLogger(Logging.GENERAL_EXCEPTION).error(new LogEvent(e));

            Log.logGeneralExceptionToDatabase(responseMessage, e);
        }
        finally
        {
            SessionHelper.clearSessions();
            
            return responseMessage;
        }
    }



    public String refundSuspense(Suspense originalSuspense, Suspense refundSuspense)
    {
        String responseMessage = "Suspense Successfully Refunded";

        try
        {
            SessionHelper.beginTransaction(SessionHelper.EDITSOLUTIONS);

            if (originalSuspense.getSuspenseAmount().isEQ(EDITBigDecimal.EDIT_DECIMAL_ZILCH_STR))
            {
                originalSuspense.setMaintenanceInd("R");
            }

            SessionHelper.saveOrUpdate(originalSuspense, SessionHelper.EDITSOLUTIONS);
            SessionHelper.saveOrUpdate(refundSuspense, SessionHelper.EDITSOLUTIONS);

            SessionHelper.commitTransaction(SessionHelper.EDITSOLUTIONS);
        }
        catch(Exception e)
        {
            SessionHelper.rollbackTransaction(SessionHelper.EDITSOLUTIONS);

            responseMessage = "Suspense Refund Errored - See Logging For Error Information";

            Logging.getLogger(Logging.GENERAL_EXCEPTION).error(new LogEvent(e));

            Log.logGeneralExceptionToDatabase(responseMessage, e);
        }
        finally
        {
            SessionHelper.clearSessions();

            return responseMessage;
        }
    }

    public String saveSuspense(Suspense suspense)
    {
        String responseMessage = "Suspense Successfully Saved";

        try
        {
            SessionHelper.beginTransaction(SessionHelper.EDITSOLUTIONS);

            SessionHelper.saveOrUpdate(suspense, SessionHelper.EDITSOLUTIONS);

            SessionHelper.commitTransaction(SessionHelper.EDITSOLUTIONS);
        }
        catch(Exception e)
        {
            SessionHelper.rollbackTransaction(SessionHelper.EDITSOLUTIONS);

            responseMessage = "Suspense Save Errored - See Logging For Error Information";

            Logging.getLogger(Logging.GENERAL_EXCEPTION).error(new LogEvent(e));

            Log.logGeneralExceptionToDatabase(responseMessage, e);
        }
        finally
        {
            SessionHelper.clearSessions();
            SessionHelper.flushSessions();

            return responseMessage;
        }
    }

    /**
     * @see event.business.EventUseCase#transferSuspense()
     */
    public void transferSuspense()
    {
    }

    /**
     * @see event.business.EventUseCase#accessTransactionProcess()
     */
    public void accessTransactionProcess()
    {
    }

    /**
     * @see event.business.EventUseCase#accessTransactionEmployerEmployee() 
     */
    public void accessTransactionEmployerEmployee()
    {
    }

    /**
     * @see event.business.EventUseCase#accessBillAmtEditOverride()
     */
    public void accessBillAmtEditOverride()
    {
    }
}
