/*
 * User: gfrosti
 * Date: Apr 12, 2005
 * Time: 10:43:34 AM
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package event.financial.client.trx;

import agent.*;
import edit.common.*;
import event.*;
import event.financial.group.trx.*;
import role.*;

/**
 * Created in EDITTrx to represent the Agent Bonus Check.
 */
public class BonusCheckTrx
{
    private EDITTrx editTrx;

    private ParticipatingAgent participatingAgent;

    /**
     * @param participatingAgent
     * @param bonusAmount
     * @param operator
     * @param bonusCommissionAmount
     * @param excessBonusCommissionAmount
     */
    public BonusCheckTrx(ParticipatingAgent participatingAgent, EDITBigDecimal bonusAmount, String operator, EDITBigDecimal bonusCommissionAmount, EDITBigDecimal excessBonusCommissionAmount)
    {
        this.participatingAgent = participatingAgent;

        setupTransaction(bonusAmount, operator, bonusCommissionAmount, excessBonusCommissionAmount);
    }

    /**
     * Builds a EDITTrx via GroupTrx.
     * @param bonusAmount
     * @param operator
     * @param bonusCommissionAmount
     * @param excessBonusCommissionAmount
     */
    private void setupTransaction(EDITBigDecimal bonusAmount, String operator, EDITBigDecimal bonusCommissionAmount, EDITBigDecimal excessBonusCommissionAmount)
    {
        ClientRole clientRole = participatingAgent.getPlacedAgent().getClientRole();

        editTrx = new GroupTrx().buildCheckTransactionGroup("BCK", clientRole.getPK(), ClientSetup.TYPE_CLIENT_ROLE,
                bonusAmount, operator, new EDITDate().getFormattedDate(), bonusCommissionAmount, excessBonusCommissionAmount, null);
    }

    /**
     * Executes the transaction, but NOT under the context of a Strategy Chain.
     *
     */
    public void execute(EDITDate processDate)
    {
        // EDITTrxHistory
        EDITTrxHistory editTrxHistory = generateEDITTrxHistory(processDate);

        editTrxHistory.save();

        // FinancialHistory
        FinancialHistory financialHistory = editTrxHistory.generateFinancialHistory(editTrx.getTrxAmount(), editTrx.getTrxAmount(), editTrx.getTrxAmount(), FinancialHistory.DISBURSEMENTSOURCECT_CHECK);

        financialHistory.save();

        // CommissionHistory
        CommissionHistory commissionHistory = editTrxHistory.generateCommissionHistory(financialHistory.getGrossAmount(), "H", "Y", CommissionHistory.COMMISSIONTYPECT_BONUSCHECK, "Y", "Y", editTrx.getOperator());

        commissionHistory.associate(participatingAgent.getPlacedAgent());

        commissionHistory.save();

         // BonusCommissionHistory
        BonusCommissionHistory bonusCommissionHistory = new BonusCommissionHistory(BonusCommissionHistory.ACCOUNTING_PENDING_STATUS_YES);
        
        bonusCommissionHistory.setTransactionTypeCT(EDITTrx.TRANSACTIONTYPECT_BONUSCHECK);

        bonusCommissionHistory.associate(commissionHistory);

        bonusCommissionHistory.associate(participatingAgent);
        
        bonusCommissionHistory.setAmount(editTrx.getTrxAmount());

        bonusCommissionHistory.save();

        // Suspense
        Suspense suspense = new Suspense();

        suspense.setUserDefNumber(participatingAgent.getPlacedAgent().get_AgentContract().get_Agent().getAgentNumber());

        suspense.setEffectiveDate(editTrx.getEffectiveDate());

        suspense.setProcessDate(processDate);

        suspense.setDirectionCT("Remove");

        suspense.set_Status("N");

        suspense.setSuspenseAmount(financialHistory.getCheckAmount());

        suspense.setOriginalAmount(financialHistory.getCheckAmount());

        suspense.setOperator(editTrx.getOperator());

        suspense.setAccountingPendingInd("N");

        suspense.setMaintenanceInd("M");

        suspense.setMaintDateTime(new EDITDateTime());

        suspense.save();

        // InSuspense
        InSuspense inSuspense = new InSuspense();

        inSuspense.setAmount(financialHistory.getCheckAmount());

        inSuspense.associate(editTrxHistory, suspense);

        inSuspense.save();

        editTrx.setPendingStatus("H");

        editTrx.saveNonRecursively();
    }

    /**
     * Generates the corresponding EDITTrxHistory for this BonusCheckTrx 'BCK'
     * @param processDate
     * @return
     */
    private EDITTrxHistory generateEDITTrxHistory(EDITDate processDate)
    {
        EDITTrxHistory editTrxHistory = new EDITTrxHistory(editTrx);

        editTrxHistory.setCycleDate(processDate);

        editTrxHistory.setOriginalProcessDateTime(new EDITDateTime(processDate.getFormattedDate()));

        editTrxHistory.setAccountingPendingStatus(EDITTrxHistory.ACCOUNTINGPENDINGSTATUS_YES);

        editTrxHistory.setCorrespondenceTypeCT(EDITTrxHistory.CORRESPONDENCETYPECT_CONFIRM);

        editTrxHistory.setRealTimeInd("Y");

        editTrxHistory.setAddressTypeCT(participatingAgent.getPlacedAgent().get_AgentContract().get_Agent().getDisbursementAddressType());

        editTrxHistory.setProcessDateTime(new EDITDateTime());

        return editTrxHistory;
    }
}
