/*
 * User: gfrosti
 * Date: Mar 7, 2005
 * Time: 10:04:58 AM
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package agent;

import edit.common.EDITBigDecimal;
import edit.common.EDITDate;
import edit.services.db.hibernate.SessionHelper;
import engine.ProductStructure;
import event.BonusCommissionHistory;

import event.EDITTrx;

import java.util.HashMap;
import java.util.Map;
import java.util.List;


public class AgentBonusStrategy
{
    private ParticipatingAgent participatingAgent;

    private EDITDate processDate;

    public AgentBonusStrategy(ParticipatingAgent participatingAgent, EDITDate processDate)
    {
        this.participatingAgent = participatingAgent;

        this.processDate = processDate;
    }

    /**
     * A complicated decision process to generate the BonusAmount, BonusCommissionAmount, and ExcessBonusCommissionAmount.
     *
     * @return
     */
    public BonusAmount calculateBonus()
    {
        BonusAmount bonusAmount = new BonusAmount();

        PremiumLevel validPremiumLevel = getValidPremiumLevel(processDate);
        
        boolean premiumLevelIsValid;

        BonusCommissionHistory[] applicableBonusCommissionHistories = getApplicableBonusCommissionHistories();

        if (validPremiumLevel != null)
        {
            premiumLevelIsValid = true;
        
            bonusAmount = generateBonusAmount(validPremiumLevel);

            buildAppliedPremiumLevels(validPremiumLevel,  applicableBonusCommissionHistories);

            updateBonusUpdateStatus(applicableBonusCommissionHistories, premiumLevelIsValid);
        }
        else
        {
            premiumLevelIsValid = false;
            
            updateBonusUpdateStatus(applicableBonusCommissionHistories, premiumLevelIsValid);
        }

        return bonusAmount;
    }

    /**
     * Sets the specified bonusUpdateStatus on each of the specified applicationBonusCommmissionHistories.
     * The rules as to what to set the BonusCommissionHistory.BonusUpdateStatus are as follows:
     * 1> if BonusCommissionHistory.TransactionTypeCT = 'PY'
     * 1.a> if BonusCommissionHistory.Amount > 0.00
     * 1.a.i> BonusCommissionHistory.BonusUpdateStatus = 'B' (PremiumLevel was valid) or 'N' (PremiumLevel was not valid)
     * 1.b> if BonusCommissionHistory.Amount < 0.00
     * 1.b.i> BonusCommissionHistory.BonusUpdateStatus = 'C'.
     * 
     * 2> if BonusCommissionHistory.TransactionTypeCT != 'PY' && BonusCommissionHistory.TransactionTypeCT != 'BCK'
     * 2.a> BonusCommissionHistory.BonusUpdateStatus = 'C'.
     * 
     * @param applicableBonusCommissionHistories
     * @param premiumLevelIsValid
     */
    private void updateBonusUpdateStatus(BonusCommissionHistory[] applicableBonusCommissionHistories, boolean premiumLevelIsValid)
    {
        String bonusUpdateStatus = null;
    
        for (int i = 0; i < applicableBonusCommissionHistories.length; i++)
        {
            BonusCommissionHistory applicableBonusCommissionHistory = applicableBonusCommissionHistories[i];
          
            String transactionTypeCT = applicableBonusCommissionHistory.getTransactionTypeCT();
            
            EDITBigDecimal amount = applicableBonusCommissionHistory.getAmount();
          
            if (transactionTypeCT.equals(EDITTrx.TRANSACTIONTYPECT_PREMIUM))
            {
              if (amount.isGT("0.00"))
              {
                if (premiumLevelIsValid)
                {
                  bonusUpdateStatus = BonusCommissionHistory.BONUSUPDATESTATUS_BONUSED;
                }
                else
                {
                  bonusUpdateStatus = BonusCommissionHistory.BONUSUPDATESTATUS_NOT_BONUSED;
                }
              }
              else if (amount.isLT("0.00"))
              {
                bonusUpdateStatus = BonusCommissionHistory.BONUSUPDATESTATUS_CHARGEBACK;
              }
            }
            else if (!transactionTypeCT.equals(EDITTrx.TRANSACTIONTYPECT_BONUSCHECK))
            {
              bonusUpdateStatus = BonusCommissionHistory.BONUSUPDATESTATUS_CHARGEBACK;
            }

            applicableBonusCommissionHistory.setBonusUpdateStatus(bonusUpdateStatus);
            
            applicableBonusCommissionHistory.updateBonusUpdateDateTime();
        }
    }

    /**
     * BonusCommissionHistories that can be applied to a PremiumLevel need to be associated so that they can:
     * 1) Establish a history of which BonusCommissionHistories were applied, and
     * 2) Be sure that they (the BonusCommissionHistories) are NOT applied again.
     * @param validPremiumLevel
     * @param applicableBonusCommissionHistories
     */
    private void buildAppliedPremiumLevels(PremiumLevel validPremiumLevel, BonusCommissionHistory[] applicableBonusCommissionHistories)
    {
        for (int i = 0; i < applicableBonusCommissionHistories.length; i++)
        {
            BonusCommissionHistory applicableBonusCommissionHistory = applicableBonusCommissionHistories[i];

            AppliedPremiumLevel appliedPremiumLevel = new AppliedPremiumLevel();

            validPremiumLevel.addAppliedPremiumLevel(appliedPremiumLevel);

            applicableBonusCommissionHistory.addAppliedPremiumLevel(appliedPremiumLevel);
        }
    }

    /**
     * The set of applicable BonusCommissionHistories that have NOT yet been applied to a valid PremiumLevel.
     * @return the applicable BonusCommissionHistories
     */
    private BonusCommissionHistory[] getApplicableBonusCommissionHistories()
    {
        String hql = " select bonusCommissionHistory" +
                    " from ParticipatingAgent participatingAgent" +
                    " join participatingAgent.BonusCommissionHistories bonusCommissionHistory" +
                    " where bonusCommissionHistory.BonusUpdateStatus is null" +
                    " and participatingAgent = :participatingAgent";

        Map params = new HashMap();

        params.put("participatingAgent", participatingAgent);

        List results = SessionHelper.executeHQL(hql, params, SessionHelper.EDITSOLUTIONS);

        return (BonusCommissionHistory[]) results.toArray(new BonusCommissionHistory[results.size()]);
    }

    /**
     * A bonus can be applied if there exists a valid PremiumLevel.
     *
     * Uses the sum of FinancialHistory.GrossAmount to compare to the set of PremiumLevels associated
     * with thie ParticipatingAgent. The candidate PremiumLevel is the 1st one whose PremiumLevel.IssueLevelPremium >=
     * the generated sum.
     *
     * @param processDate
     */
    private PremiumLevel getValidPremiumLevel(EDITDate processDate)
    {
        PremiumLevel targetPremiumLevel = null;

        EDITBigDecimal cumulativeGrossAmount = participatingAgent.getCumulativeGrossAmount();

        PremiumLevel[] premiumLevels = participatingAgent.getBizPremiumLevels();

        for (int i = 0; i < premiumLevels.length; i++)
        {
            PremiumLevel currentPremiumLevel = premiumLevels[i];

            EDITBigDecimal issueLevelPremium = currentPremiumLevel.getIssuePremiumLevel();

            if (cumulativeGrossAmount.isGTE(issueLevelPremium))
            {
                targetPremiumLevel = currentPremiumLevel;
            }
        }

        if (targetPremiumLevel != null)
        {
            boolean premiumLevelIsValid = validateCandidatePremiumLevel(targetPremiumLevel, processDate, cumulativeGrossAmount);

            if (!premiumLevelIsValid)
            {
                targetPremiumLevel = null; // PremiumLevel is no good, so return null.
            }
        }

        return targetPremiumLevel;
    }

    /**
     * Sums the contributing ProductStructureBonusBalances to generate the final bonus amount.
     *
     * @param premiumLevel
     * @return
     */
    private BonusAmount generateBonusAmount(PremiumLevel premiumLevel)
    {
        BonusAmount totalBonusAmount = new BonusAmount();

        ProductStructure[] ProductStructures = participatingAgent.getProductStructures();

        for (int i = 0; i < ProductStructures.length; i++)
        {
            ProductStructure productStructure = ProductStructures[i];

            EDITBigDecimal bonusPremiumBalance = participatingAgent.getCumulativeGrossAmount(productStructure);

            BonusCriteria bonusCriteria = BonusCriteria.findBy_PremiumLevelPK_AND_ProductStructurePK(premiumLevel.getPremiumLevelPK(), productStructure.getProductStructurePK());

            EDITBigDecimal excessPremiumLevel = bonusCriteria.getExcessPremiumLevel();

            EDITBigDecimal excessBonusBasisPoint = bonusCriteria.getExcessBonusBasisPoint();

            EDITBigDecimal excessBonusAmount = bonusCriteria.getExcessBonusAmount();

            EDITBigDecimal bonusBasisPoint = bonusCriteria.getBonusBasisPoint();

            EDITBigDecimal bonusAmount = bonusCriteria.getBonusAmount();

            if (excessBonusAmount.isGTE("0"))
            {
                if (bonusPremiumBalance.isLTE(excessPremiumLevel))
                {
                    // BonusCommissionAmount
                    EDITBigDecimal amount1 = bonusPremiumBalance.multiplyEditBigDecimal(bonusBasisPoint.divideEditBigDecimal("1000"));

                    EDITBigDecimal amount2 = bonusAmount.addEditBigDecimal(amount1);

                    totalBonusAmount.addToTotalBonusCommissionAmount(amount2);
                }
                else
                {
                    // BonusCommissionAmount
                    EDITBigDecimal amount1 = bonusPremiumBalance.multiplyEditBigDecimal(bonusBasisPoint.divideEditBigDecimal("1000"));

                    EDITBigDecimal amount2 = amount1.addEditBigDecimal(bonusAmount);

                    // ExcessBonusCommisionAmount
                    EDITBigDecimal amount3 = bonusPremiumBalance.subtractEditBigDecimal(excessPremiumLevel);

                    EDITBigDecimal amount4 = excessBonusBasisPoint.divideEditBigDecimal("1000");

                    EDITBigDecimal amount5 = amount3.multiplyEditBigDecimal(amount4);

                    EDITBigDecimal amount6 = amount5.addEditBigDecimal(excessBonusAmount);

                    totalBonusAmount.addToTotalBonusCommissionAmount(amount2);

                    totalBonusAmount.addToTotalExcessBonusCommissionAmount(amount6);
                }
            }
            else if (excessBonusAmount.isEQ("0"))
            {
                // BonusCommissionAmount
                EDITBigDecimal amount1 = bonusPremiumBalance.multiplyEditBigDecimal(bonusBasisPoint.divideEditBigDecimal("1000"));

                EDITBigDecimal amount2 = bonusAmount.addEditBigDecimal(amount1);

                totalBonusAmount.addToTotalBonusCommissionAmount(amount2);
            }
        }

        return totalBonusAmount;
    }

    /**
     * A suite of tests that verify the specified PremiumLevel has valid premium amounts.
     *
     * @param candidatePremiumLevel
     * @param processDate
     * @return true if all tests pass
     */
    private boolean validateCandidatePremiumLevel(PremiumLevel candidatePremiumLevel, EDITDate processDate, EDITBigDecimal cumulativeGrossAmount)
    {
        boolean premiumLevelIsValid = true;

        if (candidatePremiumLevel.getProductLevelIncreaseAmount().isGT("0") || candidatePremiumLevel.getProductLevelIncreasePercent().isGT("0"))
        {
            int numberOfModes = getModes(processDate);

            if (numberOfModes > 0)
            {
                EDITBigDecimal adjustedIssueLevelPremium = getAdjustedIssueLevelPremium(candidatePremiumLevel, numberOfModes);

                // Check # 1
                if (candidatePremiumLevel.getIncreaseStopAmount().isGT("0"))
                {
                    EDITBigDecimal increaseStopAmount = candidatePremiumLevel.getIncreaseStopAmount();

                    EDITBigDecimal lesserValue = EDITBigDecimal.min(adjustedIssueLevelPremium, increaseStopAmount);

                    premiumLevelIsValid = (cumulativeGrossAmount.isGTE(lesserValue));
                }

                // Check # 2
                if (premiumLevelIsValid && candidatePremiumLevel.getIncreaseStopAmount().isEQ("0"))
                {
                    premiumLevelIsValid = (cumulativeGrossAmount.isGTE(adjustedIssueLevelPremium));
                }
            }
        }

        return premiumLevelIsValid;
    }

    /**
     * Generates a mode if the specified PremiumLevel.ProductLevelIncreasePercent (or Amount) != 0.
     *
     * @param EDITDate processDate
     * @return
     */
    private int getModes(EDITDate processDate)
    {
        int numberOfModes = 0;

        EDITDate startDate = getStartDate();

        double numberOfMonths = processDate.getElapsedMonths(startDate);

        int mode = BonusProgram.getMode(participatingAgent.getBonusProgram().getFrequencyCT());

        numberOfModes = (int) (Math.ceil((numberOfMonths + 1) / mode) - 1); // round-up, then subtract 1.

        return numberOfModes;
    }

    /**
     * Generates an Adjusted Issue Level Premium (AILP) as follows (assuming the number of modes > 0):
     * 1. If PremiumLevel.ProductLevelIncreaseAmount !=0, then:
     * AILP = PremiumLevel.IssueLevelPremium + (PremiumLevel.ProductLevelIncreaseAmount * numberOfModes).
     * 2. If PremiumLevel.ProductLevelIncreasePercent != 0, then:
     * AILP = PremiumLevel.IssueLevelPremium * PremiumLevel.ProductLevelIncreasePercent (when numberOfModes = 1), or
     * AILP *= PremiumLevel.ProductLevelIncreasePercent (for each numberOfModes > 1).
     *
     * @param bestMatchPremiumLevel
     * @param numberOfModes
     * @return
     */
    private EDITBigDecimal getAdjustedIssueLevelPremium(PremiumLevel premiumLevel, int numberOfModes)
    {
        EDITBigDecimal adjustedIssueLevelPremium = new EDITBigDecimal("0.00");

        EDITBigDecimal issueLevelPremium = premiumLevel.getIssuePremiumLevel();

        if (premiumLevel.getProductLevelIncreaseAmount().isGT("0"))
        {
            EDITBigDecimal productLevelIncreaseAmount = premiumLevel.getProductLevelIncreaseAmount();

            adjustedIssueLevelPremium = issueLevelPremium.addEditBigDecimal(productLevelIncreaseAmount.multiplyEditBigDecimal(String.valueOf(numberOfModes)));
        }
        else if (premiumLevel.getProductLevelIncreasePercent().isGT("0"))
        {
            EDITBigDecimal productLevelIncreasePercent = premiumLevel.getProductLevelIncreasePercent();

            adjustedIssueLevelPremium = issueLevelPremium.addEditBigDecimal(issueLevelPremium.multiplyEditBigDecimal(productLevelIncreasePercent));

            // Compound for every numberOfModes >= 2.
            for (int i = 2; i <= numberOfModes; i++)
            {
                EDITBigDecimal compoundedAmount = adjustedIssueLevelPremium.multiplyEditBigDecimal(productLevelIncreasePercent);

                adjustedIssueLevelPremium = adjustedIssueLevelPremium.addEditBigDecimal(compoundedAmount);
            }
        }

        return adjustedIssueLevelPremium;
    }

    /**
     * The greater of Agent.HireDate or BonusProgram.BonusStartDate.
     *
     * @return
     */
    private EDITDate getStartDate()
    {
        EDITDate startDate = null;

        EDITDate hireDate = participatingAgent.getAgent().getHireDate();

        EDITDate bonusStartDate = participatingAgent.getBonusProgram().getBonusStartDate();

        if (hireDate.after(bonusStartDate) || hireDate.equals(bonusStartDate))
        {
            startDate = hireDate;
        }
        else if (hireDate.before(bonusStartDate))
        {
            startDate = bonusStartDate;
        }

        return startDate;
    }
}
