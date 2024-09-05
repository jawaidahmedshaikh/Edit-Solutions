/*
 * User: dlataill
 * Date: Mar 4, 2008
 * Time: 1:25:18 PM
 *
 * (c) 2000-2008 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package staging;

import edit.services.db.hibernate.HibernateEntity;
import edit.services.db.hibernate.SessionHelper;
import edit.common.EDITDate;

import java.util.Set;
import java.util.HashSet;


public class FilteredProduct extends HibernateEntity
{
    private Long filteredProductPK;
    private Long caseFK;
    private String companyName;
    private String marketingPackage;
    private String groupName;
    private String area;
    private String businessContract;
    

    private Case stagingCase;

    //  Children
    private Set<CaseProductUnderwriting> caseProductUnderwritings;

    /**
     * Target database to be used for lookups, etc.
     */
    public static String DATABASE = SessionHelper.STAGING;


    public FilteredProduct()
    {
        caseProductUnderwritings = new HashSet<CaseProductUnderwriting>();
    }

    /**
     * Getter.
     * @return
     */
    public Long getFilteredProductPK()
    {
        return filteredProductPK;
    }

    /**
     * Setter.
     * @param filteredProductPK
     */
    public void setFilteredProductPK(Long filteredProductPK)
    {
        this.filteredProductPK = filteredProductPK;
    }

    /**
     * Getter.
     * @return
     */
    public Long getCaseFK()
    {
        return caseFK;
    }

    /**
     * Setter.
     * @param caseFK
     */
    public void setCaseFK(Long caseFK)
    {
        this.caseFK = caseFK;
    }

    /**
     * Getter.
     * @return
     */
    public Case getCase()
    {
        return stagingCase;
    }

    /**
     * Setter.
     * @param case
     */
    public void setCase(Case stagingCase)
    {
        this.stagingCase = stagingCase;
    }

    /**
     * Getter.
     * @return
     */
    public String getCompanyName()
    {
        return companyName;
    }

    /**
     * Setter.
     * @param companyName
     */
    public void setCompanyName(String companyName)
    {
        this.companyName = companyName;
    }

    /**
    * Getter.
    * @return
    */
    public String getMarketingPackage()
    {
        return marketingPackage;
    }

    /**
     * Setter.
     * @param marketingPackage
     */
    public void setMarketingPackage(String marketingPackage)
    {
        this.marketingPackage = marketingPackage;
    }

    /**
    * Getter.
    * @return
    */
    public String getGroupName()
    {
        return groupName;
    }

    /**
     * Setter.
     * @param groupName
     */
    public void setGroupName(String groupName)
    {
        this.groupName = groupName;
    }

    /**
    * Getter.
    * @return
    */
    public String getArea()
    {
        return area;
    }

    /**
     * Setter.
     * @param area
     */
    public void setArea(String area)
    {
        this.area = area;
    }

    /**
    * Getter.
    * @return
    */
    public String getBusinessContract()
    {
        return businessContract;
    }

    /**
     * Setter.
     * @param businessContract
     */
    public void setBusinessContract(String businessContract)
    {
        this.businessContract = businessContract;
    }

    /**
     * Getter.
     * @return
     */
    public Set<CaseProductUnderwriting> getCaseProductUnderwritings()
    {
        return caseProductUnderwritings;
    }

    /**
     * Setter.
     * @param caseProductUnderwritings
     */
    public void setCaseProductUnderwritings(Set<CaseProductUnderwriting> caseProductUnderwritings)
    {
        this.caseProductUnderwritings = caseProductUnderwritings;
    }

    public void addCaseProductUnderwriting(CaseProductUnderwriting caseProductUnderwriting)
    {
        this.caseProductUnderwritings.add(caseProductUnderwriting);

        caseProductUnderwriting.setFilteredProduct(this);
    }

    public String getDatabase()
    {
        return FilteredProduct.DATABASE;
    }
}
