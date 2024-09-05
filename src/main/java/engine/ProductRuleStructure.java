/*
 * User: dlataill
 * Date: Jul 5, 2005
 * Time: 2:53:15 PM
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package engine;

import edit.services.db.hibernate.*;


import edit.services.db.hibernate.*;

public class ProductRuleStructure extends HibernateEntity
{
    private Long productRuleStructurePK;
    private ProductStructure productStructure;
    private Rules rules;

    /**
     * Target database to be used for lookups, etc.
     */
    public static String DATABASE = SessionHelper.ENGINE;


    /**
     * Setter.
     * @param productRuleStructurePK
     */
    public void setProductRuleStructurePK(Long productRuleStructurePK)
    {
        this.productRuleStructurePK = productRuleStructurePK;
    }

    /**
     * Getter.
     * @return
     */
    public Long getProductRuleStructurePK()
    {
        return productRuleStructurePK;
    }

    /**
     * Setter.
     * @param productStructure
     */
    public void setProductStructure(ProductStructure productStructure)
    {
        this.productStructure = productStructure;
    }

    /**
     * Getter.
     * @return
     */
    public ProductStructure getProductStructure()
    {
        return productStructure;
    }

    /**
     * Setter.
     * @param rules
     */
    public void setRules(Rules rules)
    {
        this.rules = rules;
    }

    /**
     * Getter.
     * @return
     */
    public Rules getRules()
    {
        return rules;
    }

    /**
     * Save the entity using Hibernate
     */
    public void hSave()
    {
        SessionHelper.saveOrUpdate(this, ProductRuleStructure.DATABASE);
    }

    /**
     * Delete the entity using Hibernate
     */
    public void hDelete()
    {
        SessionHelper.delete(this, ProductRuleStructure.DATABASE);
    }

    /**
     * Gets the database this object belongs to
     *
     * @return  string containing the hibernate database name for this object
     */
    public String getDatabase()
    {
        return ProductRuleStructure.DATABASE;
    }
}
