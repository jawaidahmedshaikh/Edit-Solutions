/*
 * User: cgleason
 * Date: Apr 30, 2007
 * Time: 11:01:46 AM
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package engine;

import edit.common.vo.*;
import edit.common.exceptions.*;
import edit.services.db.hibernate.*;

import java.util.*;

import org.dom4j.*;


public class Company extends HibernateEntity
{

//    private CompanyVO companyVO;

    private Long companyPK;
    private String companyName;
    private String policyNumberPrefix;
    private String policyNumberSuffix;
    private int policyNumberSequenceNumber;
    private int policySequenceLength;
    private String billingCompanyNumber;

    private Set productStructures;

    public static final String DATABASE = SessionHelper.ENGINE;


    public Company()
    {
        if (productStructures == null)
        {
            productStructures = new HashSet();
        }
    }

     /**
     * Getter.
     * @return
     */
    public Long getCompanyPK()
    {
        return this.companyPK;
    }

  /**
     * Setter.
     * @param companyPK
     */
    public void setCompanyPK(Long companyPK)
    {
        this.companyPK = companyPK;
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
    public String getPolicyNumberPrefix()
    {
        return policyNumberPrefix;
    }
    
    /**
     * Getter.
     * @return
     */
    public String getPolicyNumberSuffix()
    {
        return policyNumberSuffix;
    }

    /**
     * Setter.
     * @param policyNumberPrefix
     */
    public void setPolicyNumberPrefix(String policyNumberPrefix)
    {
        this.policyNumberPrefix = policyNumberPrefix;
    }
    
    /**
     * Setter.
     * @param policyNumberSuffix
     */
    public void setPolicyNumberSuffix(String policyNumberSuffix)
    {
        this.policyNumberSuffix = policyNumberSuffix;
    }

    /**
     * Getter.
     * @return
     */
    public int getPolicyNumberSequenceNumber()
    {
        return policyNumberSequenceNumber;
    }

    /**
     * Setter.
     * @param policyNumberSequenceNumber
     */
    public void setPolicyNumberSequenceNumber(int policyNumberSequenceNumber)
    {
        this.policyNumberSequenceNumber = policyNumberSequenceNumber;
    }

    /**
     * Getter.
     * @return
     */
    public int getPolicySequenceLength()
    {
        return policySequenceLength;
    }

    /**
     * Setter.
     * @param policySequenceLength
     */
    public void setPolicySequenceLength(int policySequenceLength)
    {
        this.policySequenceLength = policySequenceLength;
    }

    /**
     * Getter.
     * @return
     */
    public String getBillingCompanyNumber()
    {
        return billingCompanyNumber;
    }

    /**
     * Setter.
     * @param billingCompanyNumber
     */
    public void setBillingCompanyNumber(String billingCompanyNumber)
    {
        this.billingCompanyNumber = billingCompanyNumber;
    }

    /**
     * Getter
     * @return  set of productStructures
     */
    public Set getProductStructures()
    {
        return productStructures;
    }

    /**
     * Setter
     * @param productStructures      set of productStructures
     */
    public void setProductStructures(Set productStructures)
    {
        this.productStructures = productStructures;
    }

       /**
     * Adds a productStructure to the set of ProductStructures associated with this Company.
     * @param contractClient
     */
    public void addProductStructures(ProductStructure productStructure)
    {
        getProductStructures().add(productStructure);

        productStructure.setCompany(this);
    }

    public String getDatabase()
    {
        return Company.DATABASE;
    }

    /**
     * Finder. Hibernate.
     * @return
     */
    public static Company[] find_All()
    {
        String hql = "from Company";

        List results = SessionHelper.executeHQL(hql, null, Company.DATABASE);

        return (Company[]) results.toArray(new Company[results.size()]);
    }

    /**
     * Finder. Hibernate.
     * @return
     */
    public static Company[] find_All_ProductLevel()
    {
        String hql = "select company from Company company " +
                      "join company.ProductStructures ps " +
                      "where ps.TypeCodeCT = :productType";

        Map params = new HashMap();

        params.put("productType", "Product");

        List results = SessionHelper.executeHQL(hql, params, Company.DATABASE);

        return (Company[]) results.toArray(new Company[results.size()]);
    }

     /**
     * Finder. Hibernate.
     * @return
     */
    public static Company[] find_All_WithProductStructure()
    {
        String hql = "select distinct company from Company company, ProductStructure ps " +
                     "join fetch company.ProductStructures";

        List results = SessionHelper.executeHQL(hql, null, Company.DATABASE);

        return (Company[]) results.toArray(new Company[results.size()]);
    }

     /**
     * Finder. Hibernate.
     * @return
     */
    public static String[] find_All_CompanyNames()
    {
        String hql = "select company.CompanyName from Company company";

        List results = SessionHelper.executeHQL(hql, null, Company.DATABASE);

        return (String[]) results.toArray(new String[results.size()]);
    }

    /**
     * Finder Hibernate
     * @return
     */
    public static String[] find_All_CompanyNamesForProductType()
    {
       String hql = "select distinct company.CompanyName from Company company " +
                     "join company.ProductStructures ps " +
                     "where ps.TypeCodeCT = :productType";

        Map params = new HashMap();

        params.put("productType", "Product");

        List results = SessionHelper.executeHQL(hql, params, Company.DATABASE);

        return (String[]) results.toArray(new String[results.size()]);
    }

    /**
     * Finder.
     * @param companyPK
     * @return
     */
    public static Company findByPK(Long companyPK)
    {
        return (Company) SessionHelper.get(Company.class, companyPK, Company.DATABASE);
    }


    /**
     *
     * @param companyName
     * @return
     */
    public static Company findBy_PolicyNumberPrefix(String policyNumber)
    {
    	String prefix = policyNumber.substring(0, 2);
        String hql = "select company from Company company" +
                     " where company.PolicyNumberPrefix like :prefix";

        Map params = new HashMap();

        params.put("prefix", prefix + '%');

        List results = SessionHelper.executeHQL(hql, params, Company.DATABASE);

        Company company = null;
        if (!results.isEmpty())
        {
            company = (Company) results.get(0);
        }
        return company;
    }

    /**
     *
     * @param companyName
     * @return
     */
    public static Company findBy_CompanyName(String companyName)
    {
        String hql = "select company from Company company" +
                     " where company.CompanyName = :companyName";

        Map params = new HashMap();

        params.put("companyName", companyName);

        List results = SessionHelper.executeHQL(hql, params, Company.DATABASE);

        Company company = null;
        if (!results.isEmpty())
        {
            company = (Company) results.get(0);
        }
        return company;
    }

   public static Company findByProductStructurePK(Long productStructureFK)
    {
        Company company = null;

        String hql = "select distinct company from Company company, ProductStructure productStructure" +
                     " join fetch company.ProductStructures" +
                     " where productStructure.ProductStructurePK = :productStructureFK" +
                     " and productStructure.CompanyFK = company.CompanyPK";

        Map params = new HashMap();

        params.put("productStructureFK", productStructureFK);

        List results = SessionHelper.executeHQL(hql, params, Company.DATABASE);

        if (!results.isEmpty())
        {
            company = (Company) results.get(0);
        }
        
        return company;
    }


    /**
     * Save the entity using Hibernate
     */
    public void hSave()
    {
        SessionHelper.saveOrUpdate(this, Company.DATABASE);
    }

    /**
     * Delete the entity using Hibernate
     */
    public void hDelete()
    {
        SessionHelper.delete(this, Company.DATABASE);
    }

    public CompanyVO createCompanyVO()
    {
        CompanyVO companyVO = new CompanyVO();
        companyVO.setCompanyPK(this.getCompanyPK().longValue());
        companyVO.setCompanyName(this.getCompanyName());

        return companyVO;
    }

    /**
     * Determines the latest policyNumberSequenceNumber.  At this time, it is simply increased from the value
     * currently in the database.  The new value is set on the Company because it will be saved once the contractNumber
     * is determined to not already exist.
     */
    public int determineLatestPolicyNumberSequenceNumber()
    {
        int sequenceNumber = this.getPolicyNumberSequenceNumber();

        ++sequenceNumber;

        this.setPolicyNumberSequenceNumber(sequenceNumber);

        return sequenceNumber;
    }

    /**
     * Saves the Company to the database right away.  This method is needed because Company is in a different database
     * than the caller.  It needs its own transaction.  We also need to save it immediately because the sequenceNumber
     * may have been changed and we don't want other users picking up a stale sequenceNumber (which is used when
     * auto generating contractNumbers).
     *
     * @throws EDITCaseException
     */
    public void saveToDBImmediately() throws EDITCaseException
    {
        try
        {
            SessionHelper.beginTransaction(Company.DATABASE);

            //  SAVE
            SessionHelper.saveOrUpdate(this, Company.DATABASE);

            SessionHelper.commitTransaction(Company.DATABASE);
        }
        catch (Exception e)
        {
            System.out.println(e);

            e.printStackTrace();

            SessionHelper.rollbackTransaction(Company.DATABASE);

            throw new EDITCaseException(e.toString());
        }
    }

    public static Company[] find_All_CompaniesForProductType()
    {
        String hql = "select distinct company from Company company " +
                      "join company.ProductStructures ps " +
                      "where ps.TypeCodeCT = :productType";

         Map params = new HashMap();

         params.put("productType", "Product");

         List results = SessionHelper.executeHQL(hql, params, Company.DATABASE);

         return (Company[]) results.toArray(new Company[results.size()]);

    }
}
