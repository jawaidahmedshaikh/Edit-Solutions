/*
 * User: gfrosti
 * Date: Dec 29, 2004
 * Time: 11:41:46 AM
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package agent;

import edit.common.EDITBigDecimal;
import edit.services.db.hibernate.HibernateEntity;
import edit.services.db.hibernate.SessionHelper;

import java.util.*;


public class PremiumLevel extends HibernateEntity
{
    private BonusProgram bonusProgram;
    private ParticipatingAgent participatingAgent;
    private Long premiumLevelPK;
    private EDITBigDecimal issuePremiumLevel;
    private EDITBigDecimal productLevelIncreasePercent;
    private EDITBigDecimal productLevelIncreaseAmount;
    private EDITBigDecimal increaseStopAmount;
    private Set bonusCriterias;
    private Set appliedPremiumLevels;

    /**
     * Target database to be used for lookups, etc.
     */
    public static String DATABASE = SessionHelper.EDITSOLUTIONS;


    public PremiumLevel()
    {
        this.bonusCriterias = new HashSet();

        this.appliedPremiumLevels = new HashSet();
    }

    public Set getAppliedPremiumLevels()
    {
        return appliedPremiumLevels;
    }

    public void setAppliedPremiumLevels(Set appliedPremiumLevels)
    {
        this.appliedPremiumLevels = appliedPremiumLevels;
    }

    /**
     * Adder.
     * @param appliedPremiumLevel
     */
    public void addAppliedPremiumLevel(AppliedPremiumLevel appliedPremiumLevel)
    {
        getAppliedPremiumLevels().add(appliedPremiumLevel);

        appliedPremiumLevel.setPremiumLevel(this);
    }

    /**
     * Setter.
     * @param premiumLevelPK
     */
    public void setPremiumLevelPK(Long premiumLevelPK)
    {
        this.premiumLevelPK = premiumLevelPK;
    }

    /**
     * Setter.
     * @param bonusProgram
     */
    public void setBonusProgram(BonusProgram bonusProgram)
    {
        this.bonusProgram = bonusProgram;
    }

    /**
     * Setter.
     * @param participatingAgent
     */
    public void setParticipatingAgent(ParticipatingAgent participatingAgent)
    {
        this.participatingAgent = participatingAgent;
    }

    /**
     * Setter.
     * @param issuePremiumLevel
     */
    public void setIssuePremiumLevel(EDITBigDecimal issuePremiumLevel)
    {
        this.issuePremiumLevel = issuePremiumLevel;
    }

    /**
     * Setter.
     * @param productLevelIncreasePercent
     */
    public void setProductLevelIncreasePercent(EDITBigDecimal productLevelIncreasePercent)
    {
        this.productLevelIncreasePercent = productLevelIncreasePercent;
    }

    /**
     * Setter.
     * @param productLevelIncreaseAmount
     */
    public void setProductLevelIncreaseAmount(EDITBigDecimal productLevelIncreaseAmount)
    {
        this.productLevelIncreaseAmount = productLevelIncreaseAmount;
    }

    /**
     * Setter.
     * @param increaseStopAmount
     */
    public void setIncreaseStopAmount(EDITBigDecimal increaseStopAmount)
    {
        this.increaseStopAmount = increaseStopAmount;
    }

    /**
     * Setter.
     * @param bonusCriterias
     */
    public void setBonusCriterias(Set bonusCriterias)
    {
        this.bonusCriterias = bonusCriterias;
    }

    /**
     * Getter.
     * @return
     */
    public Long getPremiumLevelPK()
    {
        return premiumLevelPK;
    }

    /**
     * Getter.
     * @return
     */
    public BonusProgram getBonusProgram()
    {
        return bonusProgram;
    }

    /**
     * Getter.
     * @return
     */
    public ParticipatingAgent getParticipatingAgent()
    {
        return participatingAgent;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getIssuePremiumLevel()
    {
        return issuePremiumLevel;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getProductLevelIncreasePercent()
    {
        return productLevelIncreasePercent;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getProductLevelIncreaseAmount()
    {
        return productLevelIncreaseAmount;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getIncreaseStopAmount()
    {
        return increaseStopAmount;
    }

    /**
     * Getter.
     * @return
     */
    public Set getBonusCriterias()
    {
        return bonusCriterias;
    }

    /**
     * Adder.
     * @param bonusCriteria
     */
    public void addBonusCriteria(BonusCriteria bonusCriteria)
    {
        getBonusCriterias().add(bonusCriteria);

        bonusCriteria.setPremiumLevel(this);

        SessionHelper.saveOrUpdate(this, PremiumLevel.DATABASE);
    }

    /**
     * Find the appropriate PremiumLevel by the given PremiumLevelPK
     * @param premiumLevelPK
     * @return
     */
    public static final PremiumLevel findByPK(Long premiumLevelPK)
    {
        return (PremiumLevel) SessionHelper.get(PremiumLevel.class, premiumLevelPK, PremiumLevel.DATABASE);
    }

    /**
     * Find All PremiumLevels associated with the given BonusProgram
     * @param bonusProgram
     * @return
     */
    public static PremiumLevel[] findAllByBonusProgram(BonusProgram bonusProgram)
    {
        String hql = "select pl from PremiumLevel pl where pl.BonusProgram = :bonusProgram";

        Map params = new HashMap();

        params.put("bonusProgram", bonusProgram);

        List results = SessionHelper.executeHQL(hql, params, PremiumLevel.DATABASE);

        return (PremiumLevel[]) results.toArray(new PremiumLevel[results.size()]);
    }

    /**
     * Find All PremiumLevels associated with the given BonusProgram and ParticipatingAgent
     * @param bonusProgram
     * @param participatingAgent
     * @return
     */
    public static PremiumLevel[] findAllByBonusProgram_ParticipatingAgent(BonusProgram bonusProgram, ParticipatingAgent participatingAgent)
    {
        String hql = "select pl from PremiumLevel pl where pl.BonusProgram = :bonusProgram " +
                     "and pl.ParticipatingAgent =:participatingAgent";

        Map params = new HashMap();

        params.put("bonusProgram", bonusProgram);
        params.put("participatingAgent", participatingAgent);

        List results = SessionHelper.executeHQL(hql, params, PremiumLevel.DATABASE);

        return (PremiumLevel[]) results.toArray(new PremiumLevel[results.size()]);
    }

    /**
     * @see interface#hSave()
     */
    public void hSave()
    {
        SessionHelper.saveOrUpdate(this, PremiumLevel.DATABASE);
    }

    /**
     * Deletes this ContributingProduct and removes all associations with all parents.
     */
    public void hDelete()
    {
        BonusProgram bonusProgram = getBonusProgram();

        if (bonusProgram != null)
        {
            bonusProgram.getPremiumLevels().remove(this);

            setBonusProgram(null);

            SessionHelper.saveOrUpdate(bonusProgram, PremiumLevel.DATABASE);
        }

        ParticipatingAgent participatingAgent = getParticipatingAgent();

        if (participatingAgent != null)
        {
            participatingAgent.getPremiumLevels().remove(this);

            setParticipatingAgent(null);

            SessionHelper.saveOrUpdate(participatingAgent, PremiumLevel.DATABASE);
        }

        SessionHelper.delete(this, PremiumLevel.DATABASE);
    }

    public void onCreate()
    {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Gets the database this object belongs to
     *
     * @return  string containing the hibernate database name for this object
     */
    public String getDatabase()
    {
        return PremiumLevel.DATABASE;
    }

    public static PremiumLevel[] findBy_ParticipatingAgent_LastCheckDateTime_LastStatementDateTime_TrxTypeCT(ParticipatingAgent participatingAgent, String trxType)
    {
        String statusValue = "B";
        String hql = "select premiumLevel from PremiumLevel premiumLevel " +
                     " join premiumLevel.ParticipatingAgent participatingAgent " +
                     " join fetch premiumLevel.BonusCriteria bonusCriteria" +
                     " join bonusCriteria.BonusContributingProduct bonusContributingProduct " +
                     " join premiumLevel.AppliedPremiumLevel appliedPremiumLevel " +
                     " join fetch appliedPremiumLevel.BonusCommissionHistory bonusCommissionHistory " +
                     " join fetch bonusCommissionHistory.CommissionHistory commissionHistory " +
                     " join fetch commissionHistory.EDITTrxHistory editTrxHistory " +
                     " join fetch editTrxHistory.FinancialHistory " +
                     " join fetch editTrxHistory.EDITTrx editTrx " +
                     " join editTrx.ClientSetup clientSetup " +
                     " join clientSetup.ContractSetup contractSetup" +
                     " join contractSetup.Segment segment" +
                     " where editTrx.TransactionTypeCT = :trxType " +
                     " and bonusContributingProduct.ProductStructureFK = segment.ProductStructureFK " +
                     " and participatingAgent = :participatingAgent" +
                     " and (bonusCommissionHistory.BonusUpdateDateTime > participatingAgent.LastStatementDateTime " +
                     " or participatingAgent.LastStatementDateTime is null) " +
                     " and (bonusCommissionHistory.BonusUpdateDateTime <= participatingAgent.LastCheckDateTime) " +
                     " and bonusCommissionHistory.BonusUpdateStatus = :statusValue";

        Map params = new HashMap();

        params.put("trxType", trxType);
        params.put("participatingAgent", participatingAgent);
        params.put("statusValue", statusValue);

        List results = SessionHelper.executeHQL(hql, params, PremiumLevel.DATABASE);

        return (PremiumLevel[])results.toArray(new PremiumLevel[results.size()]);
    }
}
