/*
 * User: dlataill
 * Date: Jul 6, 2005
 * Time: 7:21:09 AM
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */

package engine;

import edit.services.db.hibernate.*;

import java.util.*;


public class ProductFilteredFundStructure extends HibernateEntity
{
    public static String DATABASE = SessionHelper.ENGINE;

    private Long productFilteredFundStructurePK;
    private Long productStructureFK;
    private Long filteredFundFK;

    private ControlBalance controlBalance;
    private Set controlBalances = new HashSet();

    /**
     * Setter.
     * @param productFilteredFundStructurePK
     */
    public void setProductFilteredFundStructurePK(Long productFilteredFundStructurePK)
    {
        this.productFilteredFundStructurePK = productFilteredFundStructurePK;
    }

    /**
     * Getter.
     * @return
     */
    public Long getProductFilteredFundStructurePK()
    {
        return productFilteredFundStructurePK;
    }

    /**
     * Setter.
     * @param productStructureFK
     */
    public void setProductStructureFK(Long productStructureFK)
    {
        this.productStructureFK = productStructureFK;
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
     * Setter.
     * @param filteredFundFK
     */
    public void setFilteredFundFK(Long filteredFundFK)
    {
        this.filteredFundFK = filteredFundFK;
    }

    /**
     * Getter.
     * @return
     */
    public Long getFilteredFundFK()
    {
        return filteredFundFK;
    }

    /**
     * Save the entity using Hibernate
     */
    public void hSave()
    {
        SessionHelper.saveOrUpdate(this, ProductFilteredFundStructure.DATABASE);
    }

    /**
     * Delete the entity using Hibernate
     */
    public void hDelete()
    {
        SessionHelper.delete(this, ProductFilteredFundStructure.DATABASE);
    }

    /**
     * Finder method by filteredFundPK.
     * @param filteredFundPK
     * @return
     */
    public static final ProductFilteredFundStructure[] findByFilteredFundFK(Long filteredFundFK)
    {
        String hql = "select cffs from ProductFilteredFundStructure cffs " +
                     " where cffs.FilteredFundFK = :filteredFundFK";

        Map params = new HashMap();
        params.put("filteredFundFK", filteredFundFK);

        List results = SessionHelper.executeHQL(hql, params, ProductFilteredFundStructure.DATABASE);

        results = SessionHelper.makeUnique(results);

        return (ProductFilteredFundStructure[]) results.toArray(new ProductFilteredFundStructure[results.size()]);
    }

    /**
     * Finder by PK.
     * @param filteredFundPK
     * @return
     */
    public static final ProductFilteredFundStructure findByPK(Long productFilteredFundStructurePK)
    {
        return (ProductFilteredFundStructure) SessionHelper.get(ProductFilteredFundStructure.class, productFilteredFundStructurePK, ProductFilteredFundStructure.DATABASE);
    }

    /**
      * setter
      */
     public void setControlBalance(ControlBalance controlBalance)
     {
         this.controlBalance = controlBalance;
     }

    /**
      * Getter.
      * @return
      */
     public Set getControlBalances()
     {
         return controlBalances;
     }

     /**
      * Setter.
      * @param tableKeys
      */
     public void setControlBalances(Set controlBalances)
     {
         this.controlBalances = controlBalances;
     }

    public void addControlBalance(ControlBalance controlBalance)
    {
        getControlBalances().add(controlBalance);

        controlBalance.setProductFilteredFundStructure(this);
    }

    /**
     * Gets the database this object belongs to
     *
     * @return  string containing the hibernate database name for this object
     */
    public String getDatabase()
    {
        return ProductFilteredFundStructure.DATABASE;
    }
}
