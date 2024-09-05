/*
 * User: dlataille
 * Date: Feb 6, 2006
 * Time: 2:22:46 PM
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package agent;

import edit.services.db.hibernate.HibernateEntity;
import edit.services.db.hibernate.SessionHelper;

import java.util.Map;
import java.util.HashMap;
import java.util.List;


public class BonusContributingProduct extends HibernateEntity
{
    private BonusCriteria bonusCriteria;
    private Long bonusContributingProductPK;
    private Long productStructureFK;

    /**
     * Target database to be used for lookups, etc.
     */
    public static String DATABASE = SessionHelper.EDITSOLUTIONS;


    /**
     * Setter.
     * @param bonusContributingProductPK
     */
    public void setBonusContributingProductPK(Long bonusContributingProductPK)
    {
        this.bonusContributingProductPK = bonusContributingProductPK;
    }

    /**
     * Setter.
     * @param productStructureFK
     */
    public void setProductStructureFK(Long productStructureFK)
    {
        this.productStructureFK = productStructureFK;
    }

    public void setBonusCriteria(BonusCriteria bonusCriteria)
    {
        this.bonusCriteria = bonusCriteria;
    }

    /**
     * Getter.
     * @return
     */
    public Long getBonusContributingProductPK()
    {
        return bonusContributingProductPK;
    }

    /**
     * Getter.
     * @return
     */
    public Long getProductStructureFK()
    {
        return productStructureFK;
    }

    /**
     * Getter.
     * @return
     */
    public BonusCriteria getBonusCriteria()
    {
        return bonusCriteria;
    }

    /**
     * @see interface#hSave()
     */
    public void hSave()
    {
        SessionHelper.saveOrUpdate(this, BonusContributingProduct.DATABASE);
    }

    /**
     * Deletes this ContributingProduct and removes all associations with all parents.
     */
    public void hDelete()
    {
        BonusCriteria bonusCriteria = getBonusCriteria();

        bonusCriteria.getBonusContributingProducts().remove(this);

        setBonusCriteria(null);

        SessionHelper.saveOrUpdate(bonusCriteria, BonusContributingProduct.DATABASE);

        SessionHelper.delete(this, BonusContributingProduct.DATABASE);
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
        return BonusContributingProduct.DATABASE;
    }

/***************************************** Static Methods *****************************************/

    /**
     * Find All BonusContributingProducts associated with the given productStructureFK
     * @param productStructureFK
     * @return
     */
    public static BonusContributingProduct[] findAllByProductStructureFK(Long productStructureFK)
    {
        String hql = "select bcp from BonusContributingProduct bcp where bcp.ProductStructureFK = :productStructureFK";

        Map params = new HashMap();

        params.put("productStructureFK", productStructureFK);

        List results = SessionHelper.executeHQL(hql, params, BonusContributingProduct.DATABASE);

        return (BonusContributingProduct[]) results.toArray(new BonusContributingProduct[results.size()]);
    }

    /**
     * Find the BonusContributingProduct associated with the given BonusCriteria and ProductStructureFK
     * @param productStructureFK
     * @param bonusCriteria
     * @return
     */
    public static BonusContributingProduct findByProductStructureFK_BonusCriteria(Long productStructureFK, BonusCriteria bonusCriteria)
    {
        String hql = "select bcp from BonusContributingProduct bcp where bcp.BonusCriteria = :bonusCriteria " +
                     "and bcp.ProductStructureFK =:productStructureFK";

        Map params = new HashMap();

        params.put("bonusCriteria", bonusCriteria);
        params.put("productStructureFK", productStructureFK);

        List results = SessionHelper.executeHQL(hql, params, BonusContributingProduct.DATABASE);

        BonusContributingProduct bonusContributingProduct = null;

        if (results != null && results.size() > 0)
        {
            bonusContributingProduct = (BonusContributingProduct) results.get(0);
        }

        return bonusContributingProduct;
    }
}