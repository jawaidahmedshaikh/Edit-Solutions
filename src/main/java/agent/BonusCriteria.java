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

import org.dom4j.Element;

import java.util.*;


public class BonusCriteria extends HibernateEntity
{
    private PremiumLevel premiumLevel;
    private Long bonusCriteriaPK;
    private EDITBigDecimal bonusAmount;
    private EDITBigDecimal excessBonusAmount;
    private EDITBigDecimal bonusBasisPoint;
    private EDITBigDecimal excessBonusBasisPoint;
    private EDITBigDecimal excessPremiumLevel;
    private Set bonusContributingProducts;
    private Long premiumLevelFK;

    /**
     * Target database to be used for lookups, etc.
     */
    public static String DATABASE = SessionHelper.EDITSOLUTIONS;


    /**
     * Getter.
     *
     * @return
     */
    public Long getPremiumLevelFK()
    {
        return premiumLevelFK;
    }

    /**
     * Setter.
     *
     * @param premiumLevelFK
     */
    public void setPremiumLevelFK(Long premiumLevelFK)
    {
        this.premiumLevelFK = premiumLevelFK;
    }

    public BonusCriteria()
    {
        bonusContributingProducts = new HashSet();
    }

    /**
     * Setter.
     *
     * @param bonusCriteriaPK
     */
    public void setBonusCriteriaPK(Long bonusCriteriaPK)
    {
        this.bonusCriteriaPK = bonusCriteriaPK;
    }

    /**
     * Setter.
     *
     * @param premiumLevel
     */
    public void setPremiumLevel(PremiumLevel premiumLevel)
    {
        this.premiumLevel = premiumLevel;
    }

    /**
     * Setter.
     *
     * @param bonusAmount
     */
    public void setBonusAmount(EDITBigDecimal bonusAmount)
    {
        this.bonusAmount = bonusAmount;
    }

    /**
     * Setter.
     *
     * @param excessBonusAmount
     */
    public void setExcessBonusAmount(EDITBigDecimal excessBonusAmount)
    {
        this.excessBonusAmount = excessBonusAmount;
    }

    /**
     * Setter.
     *
     * @param bonusBasisPoint
     */
    public void setBonusBasisPoint(EDITBigDecimal bonusBasisPoint)
    {
        this.bonusBasisPoint = bonusBasisPoint;
    }

    /**
     * Setter.
     *
     * @param excessBonusBasisPoint
     */
    public void setExcessBonusBasisPoint(EDITBigDecimal excessBonusBasisPoint)
    {
        this.excessBonusBasisPoint = excessBonusBasisPoint;
    }

    /**
     * Setter.
     *
     * @param excessPremiumLevel
     */
    public void setExcessPremiumLevel(EDITBigDecimal excessPremiumLevel)
    {
        this.excessPremiumLevel = excessPremiumLevel;
    }

    /**
     * Setter.
     *
     * @param bonusContributingProducts
     */
    public void setBonusContributingProducts(Set bonusContributingProducts)
    {
        this.bonusContributingProducts = bonusContributingProducts;
    }

    /**
     * Getter.
     *
     * @return
     */
    public Long getBonusCriteriaPK()
    {
        return this.bonusCriteriaPK;
    }

    /**
     * Getter.
     *
     * @return
     */
    public PremiumLevel getPremiumLevel()
    {
        return premiumLevel;
    }

    /**
     * Getter.
     *
     * @return
     */
    public EDITBigDecimal getBonusAmount()
    {
        return bonusAmount;
    }

    /**
     * Getter.
     *
     * @return
     */
    public EDITBigDecimal getExcessBonusAmount()
    {
        return excessBonusAmount;
    }

    /**
     * Getter.
     *
     * @return
     */
    public EDITBigDecimal getBonusBasisPoint()
    {
        return bonusBasisPoint;
    }

    /**
     * Getter.
     *
     * @return
     */
    public EDITBigDecimal getExcessBonusBasisPoint()
    {
        return excessBonusBasisPoint;
    }

    /**
     * Getter.
     *
     * @return
     */
    public EDITBigDecimal getExcessPremiumLevel()
    {
        return excessPremiumLevel;
    }

    /**
     * Getter.
     *
     * @return
     */
    public Set getBonusContributingProducts()
    {
        return bonusContributingProducts;
    }

    /**
     * Adder.
     *
     * @param bonusContributingProduct
     */
    public void addBonusContributingProduct(BonusContributingProduct bonusContributingProduct)
    {
        getBonusContributingProducts().add(bonusContributingProduct);

        bonusContributingProduct.setBonusCriteria(this);

        SessionHelper.saveOrUpdate(this, BonusCriteria.DATABASE);
    }

    /**
     * @return
     */
    public Element getAsElement()
    {
        return null; //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * @see interface#hSave()
     */
    public void hSave()
    {
        SessionHelper.saveOrUpdate(this, BonusCriteria.DATABASE);
    }

    /**
     * Deletes this BonusCriteria and removes all associations with all parents.
     */
    public void hDelete()
    {
        PremiumLevel premiumLevel = getPremiumLevel();

        premiumLevel.getBonusCriterias().remove(this);

        setPremiumLevel(null);

        SessionHelper.saveOrUpdate(premiumLevel, BonusCriteria.DATABASE);

        SessionHelper.delete(this, BonusCriteria.DATABASE);
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
        return BonusCriteria.DATABASE;
    }

    /******************************************* Static Methods *************************************************/
    /**
     * Find All BonusCriterias associated with the given PremiumLevel
     *
     * @param premiumLevel
     * @return
     */
    public static BonusCriteria[] findAllByPremiumLevel(PremiumLevel premiumLevel)
    {
        String hql = "select bc from BonusCriteria bc where bc.PremiumLevel = :premiumLevel";

        Map params = new HashMap();

        params.put("premiumLevel", premiumLevel);

        List results = SessionHelper.executeHQL(hql, params, BonusCriteria.DATABASE);

        return (BonusCriteria[]) results.toArray(new BonusCriteria[results.size()]);
    }

    /**
     * Finder.
     *
     * @param bonusCriteriaPK
     */
    public static final BonusCriteria findByPK(Long bonusCriteriaPK)
    {
        return (BonusCriteria) SessionHelper.get(BonusCriteria.class, bonusCriteriaPK, BonusCriteria.DATABASE);
    }

    /**
     * Finder.
     *
     * @param premiumLevelPK
     * @param productStructurePK
     * @return
     */
    public static BonusCriteria findBy_PremiumLevelPK_AND_ProductStructurePK(Long premiumLevelPK, Long productStructurePK)
    {
        BonusCriteria bonusCriteria = null;

        String hql = " select bonusCriteria from BonusCriteria bonusCriteria" +
                " join bonusCriteria.BonusContributingProducts bonusContributingProduct" +
                " where bonusContributingProduct.ProductStructureFK = :productStructureFK" +
                " and bonusCriteria.PremiumLevelFK = :premiumLevelFK";

        Map params = new HashMap();

        params.put("productStructureFK", productStructurePK);

        params.put("premiumLevelFK", premiumLevelPK);

        List results = SessionHelper.executeHQL(hql, params, BonusCriteria.DATABASE);

        if (!results.isEmpty())
        {
            bonusCriteria = (BonusCriteria) results.get(0);
        }

        return bonusCriteria;
    }
}
