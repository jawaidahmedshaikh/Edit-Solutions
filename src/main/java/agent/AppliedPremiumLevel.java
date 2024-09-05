/*
 * Created by IntelliJ IDEA.
 * User: gfrosti
 * Date: Mar 20, 2006
 * Time: 9:20:14 AM
 * To change this template use File | Settings | File Templates.
 */
package agent;

import event.BonusCommissionHistory;

import java.util.*;

import edit.services.db.hibernate.*;


/**
 * Currently, a simple association entity to tie BonnusBommissionHistory(s) that have
 * been associated to a PremiumLevel. The PremiumLevel used for the association was chosen
 * because the sum of BonusCommissionHistory.CommissionHistory.EDITTrxHistory.FinancialHistory.GrossAmount
 * met the PremiumLevel.IssuePremiumLevel.
 */
public class AppliedPremiumLevel
{
    private BonusCommissionHistory bonusCommissionHistory;
    private PremiumLevel premiumLevel;
    private Long appliedPremiumLevelPK;
    private Long premiumLevelFK;
    private Long bonusCommissionHistoryFK;

    public AppliedPremiumLevel()
    {
    }

    public Long getAppliedPremiumLevelPK()
    {
        return appliedPremiumLevelPK;
    }

    public void setAppliedPremiumLevelPK(Long appliedPremiumLevelPK)
    {
        this.appliedPremiumLevelPK = appliedPremiumLevelPK;
    }

    public BonusCommissionHistory getBonusCommissionHistory()
    {
        return bonusCommissionHistory;
    }

    /**
     * One of the BonusCommissionHistory(s) (of a likely set of them) whose
     * BonusCommissionHistory.CommissionHistory.EDITTrxHistory.FinancialHistory.GrossAmount contributed to the sum
     * total that match a ParticpatingAgent's PremiumLevel.
     *
     * @param bonusCommissionHistory one of a set of contributing BonusCommissionHistory(s)
     */
    public void setBonusCommissionHistory(BonusCommissionHistory bonusCommissionHistory)
    {
        this.bonusCommissionHistory = bonusCommissionHistory;
    }

    public PremiumLevel getPremiumLevel()
    {
        return premiumLevel;
    }

    /**
     * The PremiumLevel whose PremiumLevel.IssuePremiumLevel matched the sum total of a
     * ParticipatingAgent's BonusCommissionHistory.CommissionHistory.EDITTrxHistory.FinancialHistory.GrossAmount.
     *
     * @param premiumLevel the PremiumLevel with a match PremiumLevel.IssuePremiumLevel
     */
    public void setPremiumLevel(PremiumLevel premiumLevel)
    {
        this.premiumLevel = premiumLevel;
    }

    /**
     * Getter
     * @return
     */
    public Long getPremiumLevelFK()
    {
        return premiumLevelFK;
    }

    /**
     * Getter
     * @return
     */
    public Long getBonusCommissionHistoryFK()
    {
        return bonusCommissionHistoryFK;
    }

    /**
     * Setter
     * @param premiumLevelFK
     */
    public void setPremiumLevelFK(Long premiumLevelFK)
    {
        this.premiumLevelFK = premiumLevelFK;
    }

    /**
     * Setter
     * @param bonusCommissionHistoryFK
     */
    public void setBonusCommissionHistoryFK(Long bonusCommissionHistoryFK)
    {
        this.bonusCommissionHistoryFK = bonusCommissionHistoryFK;
    }


    public static AppliedPremiumLevel[] findByParticipatingAgent_PremiumLevel(ParticipatingAgent participatingAgent, PremiumLevel premiumLevel)
    {
       String hql = "select appliedPremiumLevel from AppliedPremiumLevel appliedPremiumLevel " +
                     " join fetch appliedPremiumLevel.BonusCommissionHistory bonusCommissionHistory " +
                     " join fetch bonusCommissionHistory.ParticipatingAgent participatingAgent " +
                     " join fetch appliedPremiumLevel.PremiumLevel premiumLevel " +
                     " where premiumLevel = :premiumLevel" +
                     " and participatingAgent = :participatingAgent" +
                     " and (bonusCommissionHistory.BonusUpdateDateTime > participatingAgent.LastStatementDateTime " +
                     " or participatingAgent.LastStatementDateTime is null) " +
                     " and bonusCommissionHistory.BonusUpdateStatus in ('B', 'C')";

         Map params = new HashMap();


         params.put("premiumLevel", premiumLevel);
         params.put("participatingAgent", participatingAgent);

        List results = SessionHelper.executeHQL(hql, params, SessionHelper.EDITSOLUTIONS);

        return (AppliedPremiumLevel[]) results.toArray(new AppliedPremiumLevel[results.size()]);
    }
}
