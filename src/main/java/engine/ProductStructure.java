/*
 * User: cgleason
 * Date: Apr 30, 2007
 * Time: 11:10:46 AM
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package engine;

import agent.ContributingProduct;
import codetable.FilteredCodeTable;
import contract.FilteredRequirement;
import contract.Requirement;

import edit.common.EDITDate;
import edit.common.EDITDateTime;
import edit.common.EDITMap;
import edit.common.exceptions.EDITEngineException;
import edit.common.exceptions.EDITSecurityException;
import edit.common.vo.ProductStructureVO;
import edit.common.vo.*;
import edit.services.config.ServicesConfig;
import edit.services.db.CRUDEntity;
import edit.services.db.CRUDEntityImpl;
import edit.services.db.hibernate.*;
import engine.dm.dao.ProductStructureDAO;
import engine.dm.dao.DAOFactory;
import fission.global.AppReqBlock;
import fission.utility.Util;
import security.*;

import java.util.*;

import org.dom4j.*;
import org.hibernate.Session;


public class ProductStructure extends HibernateEntity implements CRUDEntity
{
    public static final String COMPANYSTRUCTURE_DEFAULT_DELIMITER = ",";

    public static final String TYPECODECT_PRODUCT = "Product";

    public static final String TYPECODECT_SYSTEM = "System";

    public static final String FIXED_PRODUCT = "Fixed";
    public static final String VARIABLE_PRODUCT = "Variable";

    public static final String DATABASE = SessionHelper.ENGINE;

    private ProductStructureImpl productStructureImpl;

    private ProductStructureVO productStructureVO;

    private Set filteredFunds; 
    private Set areaValues;
    private Set rules;
    private Company company;


    ////////////////////////////////////////////////////////////////////////////
    //////////////////////////// CONSTRUCTORS //////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////

    public ProductStructure()
    {
        this.productStructureImpl = new ProductStructureImpl();
        this.productStructureVO = new ProductStructureVO();
    }

    public ProductStructure(long productStructurePK)
    {
        this();
        productStructureImpl.load(this, productStructurePK);
    }

    public ProductStructure(ProductStructureVO productStructureVO)
    {
        this();
        this.productStructureVO = productStructureVO;
    }

    ////////////////////////////////////////////////////////////////////////////
    //////////////////////////// ACCESSORS //////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////

    /**
     * Getter.
     * @return
     */
    public String getAccountingClosePeriod()
    {
        return productStructureVO.getAccountingClosePeriod();
    } //-- java.lang.String getAccountingClosePeriod()

    /**
     * Getter.
     * @return
     */
    public String getAreaName()
    {
        return productStructureVO.getAreaName();
    } //-- java.lang.String getAreaName()

    /**
     * Getter.
     * @return
     */
    public String getBusinessContractName()
    {
        return productStructureVO.getBusinessContractName();
    } //-- java.lang.String getBusinessContractName()


    /**
     * Getter.
     * @return
     */
    public String getCompanyName()
    {
        Company company = Company.findByPK(new Long(productStructureVO.getCompanyFK()));

        return company.getCompanyName();
    } //-- java.lang.String getCompanyName()

    /**
     * Getter.
     * @return
     */
    public String getGroupProductName()
    {
        return productStructureVO.getGroupProductName();
    } //-- java.lang.String getGroupProductName()


    /**
     * Getter
     * @return
     */
    public String getGroupTypeCT()
    {
        return productStructureVO.getGroupTypeCT();
    } //-- java.lang.String getGroupTypeCT()


    /**
     * Getter.
     * @return
     */
    public String get_MaintDateTime()
    {
        return productStructureVO.getMaintDateTime();
    } //-- java.lang.String getMaintDateTime()

    /**
     * Setter.
     * @param maintDateTime
     */
    public void setMaintDateTime(EDITDateTime maintDateTime)
    {
        this.productStructureVO.setMaintDateTime(SessionHelper.getEDITDateTime(maintDateTime));
    }

    /**
     * Getter
     * @return
     */
    public EDITDateTime getMaintDateTime()
    {
        return SessionHelper.getEDITDateTime(productStructureVO.getMaintDateTime());
    }

    /**
     * Getter.
     * @return
     */
    public String getMarketingPackageName()
    {
        return productStructureVO.getMarketingPackageName();
    } //-- java.lang.String getMarketingPackageName()

    /**
     * Getter.
     * @return
     */
    public String getOperator()
    {
        return productStructureVO.getOperator();
    } //-- java.lang.String getOperator()

    /**
     * Getter.
     * @return
     */
    public String getTypeCodeCT()
    {
        return productStructureVO.getTypeCodeCT();
    } //-- java.lang.String getTypeCodeCT()

    /**
     * Getter.
     * @return
     */
    public Long getProductStructurePK()
    {
        return SessionHelper.getPKValue(productStructureVO.getProductStructurePK());
    } //-- long getProductStructurePK()

    /**
     * Getter.
     * @return
     */
    public Long getHedgeFundInterimAccountFK()
    {
        return SessionHelper.getPKValue(productStructureVO.getHedgeFundInterimAccountFK());
    }

    /**
     * Getter.
     * @return
     */
    public String getProductTypeCT()
    {
        return this.productStructureVO.getProductTypeCT();
    }

    /**
     * Getter.
     * @return
     */
    public Long getCompanyFK()
    {
        return SessionHelper.getPKValue(productStructureVO.getCompanyFK());
    }

    /**
     * Setter.
     * @param areaName
     */
    public void setAreaName(String areaName)
    {
        productStructureVO.setAreaName(areaName);
    } //-- void setAreaName(java.lang.String)

    /**
     * Setter.
     * @param accountingClosePeriod
     */
    public void setAccountingClosePeriod(String accountingClosePeriod)
    {
        productStructureVO.setAccountingClosePeriod(accountingClosePeriod);
    } //-- void setAccountingClosePeriod(java.lang.String)

    /**
     * Setter.
     * @param businessContractName
     */
    public void setBusinessContractName(String businessContractName)
    {
        productStructureVO.setBusinessContractName(businessContractName);
    } //-- void setBusinessContractName(java.lang.String)

    /**
     * Setter.
     * @param productStructurePK
     */
    public void setProductStructurePK(Long productStructurePK)
    {
        productStructureVO.setProductStructurePK(SessionHelper.getPKValue(productStructurePK));
    } //-- void setProductStructurePK(long)

    /**
     * Setter.
     * @param hedgeFundInterimAccountFK
     */
    public void setHedgeFundInterimAccountFK(Long hedgeFundInterimAccountFK)
    {
        productStructureVO.setHedgeFundInterimAccountFK(SessionHelper.getPKValue(hedgeFundInterimAccountFK));
    }


   /**
     * Setter.
     * @param companyFK
     */
    public void setCompanyFK(Long companyFK)
    {
        productStructureVO.setCompanyFK(SessionHelper.getPKValue(companyFK));
    }
    /**
     * Setter.
     * @param groupProductName
     */
    public void setGroupProductName(String groupProductName)
    {
        productStructureVO.setGroupProductName(groupProductName);
    } //-- void setGroupProductName(java.lang.String)

    /**
     * Setter.
     * @param marketingPackageName
     */
    public void setMarketingPackageName(String marketingPackageName)
    {
        productStructureVO.setMarketingPackageName(marketingPackageName);
    } //-- void setMarketingPackageName(java.lang.String)

    /**
     * Setter.
     * @param operator
     */
    public void setOperator(String operator)
    {
        productStructureVO.setOperator(operator);
    } //-- void setOperator(java.lang.String)

    /**
     * Setter.
     * @param typeCodeCT
     */
    public void setTypeCodeCT(String typeCodeCT)
    {
        productStructureVO.setTypeCodeCT(typeCodeCT);
    } //-- void setTypeCodeCT(java.lang.String)

    /**
     * Setter
     * @param groupTypeCT
     */
    public void setGroupTypeCT(String groupTypeCT)
    {
        productStructureVO.setGroupTypeCT(groupTypeCT);
    } //-- void setGroupTypeCT(java.lang.String)

    /**
     * Setter.
     * @param productTypeCT
     */
    public void setProductTypeCT(String productTypeCT)
    {
        productStructureVO.setProductTypeCT(productTypeCT);
    }


    /**
     * Getter.
     * @return
     */
    public String getExternalProductName()
    {
        return productStructureVO.getExternalProductName();
    }

    /**
     * Setter.
     * @param externalProductName
     */
    public void setExternalProductName(String externalProductName)
    {
        productStructureVO.setExternalProductName(externalProductName);
    }

    /**
     * Getter
     * @return  set of filteredFunds
     */
    public Set getFilteredFunds()
    {
        return filteredFunds;
    }

    /**
     * Setter
     * @param filteredFunds      set of filteredFunds
     */
    public void setFilteredFunds(Set filteredFunds)
    {
        this.filteredFunds = filteredFunds;
    }

    /**
     * Adds a FilteredFund to the set of children
     * @param filteredFund
     */
    public void addFilteredFund(FilteredFund filteredFund)
    {
        this.getFilteredFunds().add(filteredFund);

        filteredFund.addProductStructure(this);

        SessionHelper.saveOrUpdate(filteredFund, SessionHelper.EDITSOLUTIONS);
    }

    /**
     * Removes a FilteredFund from the set of children
     * @param filteredFund
     */
    public void removeFilteredFund(FilteredFund filteredFund)
    {
        this.getFilteredFunds().remove(filteredFund);

        filteredFund.removeProductStructure(null);

        SessionHelper.saveOrUpdate(filteredFund, SessionHelper.EDITSOLUTIONS);
    }

    /**
     * Getter
     * @return  set of areaValues
     */
    public Set getAreaValues()
    {
        return areaValues;
    }

    /**
     * Setter
     * @param areaValues      set of areaValues
     */
    public void setAreaValues(Set areaValues)
    {
        this.areaValues = areaValues;
    }

    /**
     * Adds a AreaValue to the set of children
     * @param areaValue
     */
    public void addAreaValue(AreaValue areaValue)
    {
        this.getAreaValues().add(areaValue);

        areaValue.addProductStructure(this);

        SessionHelper.saveOrUpdate(areaValue, SessionHelper.EDITSOLUTIONS);
    }

    /**
     * Removes a AreaValue from the set of children
     * @param areaValue
     */
    public void removeAreaValue(AreaValue areaValue)
    {
        this.getAreaValues().remove(areaValue);

        areaValue.removeProductStructure(null);

        SessionHelper.saveOrUpdate(areaValue, SessionHelper.EDITSOLUTIONS);
    }

    /**
     * Getter
     * @return  set of rules
     */
    public Set getRules()
    {
        return rules;
    }

    /**
     * Setter
     * @param rules      set of rules
     */
    public void setRules(Set rules)
    {
        this.rules = rules;
    }

    /**
     * Adds a Rule to the set of children
     * @param rule
     */
    public void addRule(Rules rules)
    {
        this.getRules().add(rules);

        rules.addProductStructure(this);

        SessionHelper.saveOrUpdate(rules, SessionHelper.EDITSOLUTIONS);
    }

    /**
     * Removes a Rule from the set of children
     * @param rule
     */
    public void removeRule(Rules rules)
    {
        this.getRules().remove(rules);

        rules.removeProductStructure(this);

        SessionHelper.saveOrUpdate(rules, SessionHelper.EDITSOLUTIONS);
    }

    /**
     * Getter
     * @return
     */
    public Company getCompany()
    {
        return company;
    }

    /**
     * Getter
     * @param company
     */
    public void setCompany(Company company)
    {
        this.company = company;
    }

    public void save()
    {
        productStructureImpl.save(this);
    }

    public void delete() throws Exception
    {
       productStructureImpl.delete(this);
    }

    public VOObject getVO()
    {
        return productStructureVO;
    }

    public void setVO(VOObject voObject)
    {
        this.productStructureVO = (ProductStructureVO) voObject;
    }

    public boolean isNew()
    {
        return (productStructureVO.getProductStructurePK() == 0);
    }

    public CRUDEntity cloneCRUDEntity()
    {
        return productStructureImpl.cloneCRUDEntity(this);
    }

    public long getPK()
    {
        return productStructureVO.getProductStructurePK();
    }


    ////////////////////////////////////////////////////////////////////////////
    //////////////////////////// BEHAVIOR //////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////

    /**
     * True if the ProductStructure is the wildcard product structure.
     * @return
     */
    public boolean isWildcard()
    {
        ProductStructureVO compVO1 = (ProductStructureVO) getVO();
        Company company = Company.findByPK(new Long(compVO1.getCompanyFK()));
        this.setCompany(company);

        if (compVO1 == null)
        {
            return false;
        }

        boolean ret =
            (compVO1.getAreaName() == null) ?
                false :
                compVO1.getAreaName().equals("*")
            && (compVO1.getAccountingClosePeriod() == null) ?
                false :
                compVO1.getAccountingClosePeriod().equals("*")
            && (compVO1.getBusinessContractName() == null) ?
                false :
                compVO1.getBusinessContractName().equals("*")
            && (company.getCompanyName() == null) ?
                false :
                company.getCompanyName().equals("*")
            && (compVO1.getMarketingPackageName() == null) ?
                false :
                compVO1.getMarketingPackageName().equals("*")
            && (compVO1.getTypeCodeCT() == null) ?
                false :
                compVO1.getTypeCodeCT().equalsIgnoreCase("Product");

        return ret;

    }

    /**
     * Clones the set of AreaValues of this ProductStructure to the clone-to ProductStructure.
     * @param cloneTo
     */
    public void cloneAreaValues(ProductStructure cloneTo) throws EDITEngineException
    {
        AreaValue[] associatedAreaValues = get_AreaValues();

        if (associatedAreaValues != null)
        {
            for (int i = 0; i < associatedAreaValues.length; i++)
            {
                FilteredAreaValue filteredAreaValue = new FilteredAreaValue(cloneTo, associatedAreaValues[i]);

                filteredAreaValue.save();
            }
        }
    }

    /**
     * Returns the set of AreaValues that have been mapped to this ProductStructure.
     * @return
     */
    public AreaValue[] get_AreaValues()
    {
        return AreaValue.findBy_ProductStructurePK(getPK());
    }

    /**
     * Create a default ProductStructure.  Used if none exist during
     * security initialization.
     * @return
     */
    public static ProductStructure createDefaultForProduct()
    {
        ProductStructureVO productStructureVO = new ProductStructureVO();

        // product name from edit services config reporting name
        EDITServicesConfig editServicesConfig = ServicesConfig.getEditServicesConfig();
        String companyName = editServicesConfig.getReportCompanyName();
        if (companyName == null || companyName.length()==0)
        {
            companyName = "Default";
        }
        Company company = (Company) SessionHelper.newInstance(Company.class, ProductStructure.DATABASE);
        company.setCompanyName(companyName);
        company.hSave();

        productStructureVO.setMarketingPackageName("*");

        productStructureVO.setGroupProductName("*");

        productStructureVO.setAreaName("*");

        productStructureVO.setBusinessContractName("Default");

        productStructureVO.setTypeCodeCT("Product");

        ProductStructure productStructure = new ProductStructure(productStructureVO);

        productStructure.save();

        productStructure.setCompany(company);

        return productStructure;
    }

    /**
     * Clone all product security from this product structure into the
     * product structure toProductStructure.
     * @param toProductStructure
     */
    public void cloneAllSecurity(ProductStructure toProductStructure)
                throws EDITSecurityException
    {
        FilteredRole[] filteredRoles =
                FilteredRole.findByProductStructure(new Long(getPK()));

        if (filteredRoles == null)
        {
            return;
        }

        // These are all of the FilteredRoles for this product structure.

        // For each of these - clone the FilteredRole with the new product
        // structure and save (the cloning method does the save because FilteredRole is in a different database
        // from ProductStructure)
        for (int i = 0; i < filteredRoles.length; i++)
        {
            filteredRoles[i].cloneFilteredRole(toProductStructure);
        }
   }
    
    /**
     * Gives the free look fund for this product.
     * @param areaCT
     * @param effectiveDate
     * @return
     */
    public FilteredFund getFreeLookFund(String areaCT, EDITDate effectiveDate)
    {
        String grouping = "FREELOOKPROCESS";
        String field = "FREELOOKFUND";
        
        Area area = new Area(this.getProductStructurePK(), areaCT, grouping, effectiveDate, null);

        AreaValue areaValue = area.getAreaValue(field);

        String fundNumber = areaValue.getAreaValue();
        
        return FilteredFund.findByFundNumber(fundNumber);
    }

    ////////////////////////////////////////////////////////////////////////////
    //////////////////////////// FINDERS ///////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////

    /**
     * Find all "Product" type product structures.  Convenience method.
     * @return
     */
    public static ProductStructure[] findAllProductType()
    {
       ProductStructureVO[] productStructureVOs =
                engine.dm.dao.DAOFactory.
                getProductStructureDAO().
                findByTypeCode("Product", false, null);

        return ProductStructure.mapVOToEntity(productStructureVOs);
    }

   /**
     * Find all "Product" type product structures and default Security structure.  Convenience method.
     * @return
     */
    public static ProductStructure[] findByTypeCodeforSecurity()
    {
       ProductStructureVO[] productStructureVOs =
                engine.dm.dao.DAOFactory.
                getProductStructureDAO().
                findByTypeCodeforSecurity("Product", false, null);

        return ProductStructure.mapVOToEntity(productStructureVOs);
    }

    public static ProductStructure[] findAllProductTypeForRole(Role aRole)
    {
       ProductStructureVO[] productStructureVOs =
                engine.dm.dao.DAOFactory.
                getProductStructureDAO().findProductTypeForRole(aRole.getRolePK().longValue());

        return ProductStructure.mapVOToEntity(productStructureVOs);
    }

    /**
     * Get both direct and implied distinct productstructures that this operator
     * can access.  Note - this does not mean that all security for each of those
     * product structures is doable by this operator; only that s/he can do at
     * least one thing for each.
     * @param operator
     * @return
     */
    public static ProductStructure[] findAllProductTypeForOperator(Operator operator)
    {

        List listOfRoles = new ArrayList();

        Role[] roles = Role.findByOperatorPK(operator.getOperatorPK());

        for (int i = 0; i < roles.length; i++)
        {
            Role role = roles[i];
            listOfRoles.add(role);

            ImpliedRole[] impliedRoles = ImpliedRole.findByRoleFK(role.getRolePK());

            if (impliedRoles != null)
            {
                for (int j = 0; j < impliedRoles.length; j++)
                {
                    ImpliedRole impliedRole = impliedRoles[j];
                    Role roleForImplied = new Role();
                    roleForImplied.setRolePK(impliedRole.getImpliedRoleFK());
                    listOfRoles.add(roleForImplied);
                }
            }
        }

        Role[] implicitAndDirectRoles = (Role[]) listOfRoles.toArray(new Role[0]);

        // Now go get the distinct product structures for these

        ProductStructureVO[] productStructureVOs =
                        engine.dm.dao.DAOFactory.
                        getProductStructureDAO().findProductTypeByRoles(implicitAndDirectRoles);

        return ProductStructure.mapVOToEntity(productStructureVOs);
    }

    /**
      * Finder. Finds the ProductStructure mapped to the AreaValue (if there is one).
      * @param productStructurePK
      * @param areaValuePK
      * @return
      */
     public static ProductStructure findBy_ProductStructurePK_AreaValuePK(long productStructurePK, long areaValuePK)
     {
         ProductStructure productStructure = null;

         ProductStructureVO[] productStructureVOs = DAOFactory.getProductStructureDAO().findBy_ProductStructurePK_AreaValuePK(productStructurePK, areaValuePK);

         if (productStructureVOs != null)
         {
             productStructure = new ProductStructure(productStructureVOs[0]);
         }

         return productStructure;
     }

     /**
      * Finder.
      * @param productStructurePK
      * @param treatyGroupPK
      * @return
      */
     public static ProductStructure findBy_ProductStructurePK_TreatyGroupPK(long productStructurePK, long treatyGroupPK)
     {
         ProductStructure productStructure = null;

         ProductStructureVO[] productStructureVOs = DAOFactory.getProductStructureDAO().findBy_ProductStructurePK_TreatyGroupPK(productStructurePK, treatyGroupPK);

         if (productStructureVOs != null)
         {
             productStructure = new ProductStructure(productStructureVOs[0]);
         }

         return productStructure;
     }

     /**
      * Finder.
      * @param productStructurePK
      * @return
      */
     public static ProductStructure findByPK(long productStructurePK)
     {
         ProductStructure productStructure = null;

         ProductStructureVO[] productStructureVOs = new ProductStructureDAO().findByPK(productStructurePK, false, null);

         if (productStructureVOs != null)
         {
             productStructure = new ProductStructure(productStructureVOs[0]);
         }

         return productStructure;
     }


    ////////////////////////////////////////////////////////////////////////////
    //////////////////////////// UTILITY ///////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////

     /**
     * Helper method for Tran code to make setting current product structure easy.
     * Takes the form variable value in the request and sets the appropriate
     * product structure in the HttpSession's UserSession.
      * <p>
      * If the form variable is null or "0", it leaves the current product
      * structure setting as-is.
     * @param appReqBlock
     * @param nameOfFormVariable
     */
     public static void setSecurityCurrentProdStructInSession(AppReqBlock appReqBlock,
                                                              String nameOfFormVariable)
     {
         String productStructurePKStr =
                 Util.initString(appReqBlock.getHttpServletRequest().getParameter(nameOfFormVariable), null);

         if (productStructurePKStr == null)
         {
             return;     // don't change current product structure
         }
         appReqBlock.getUserSession().setCurrentProductStructurePK(Long.parseLong(productStructurePKStr));
     }

    public static void setSecurityCurrentProdStructInSession(AppReqBlock appReqBlock,
                                                             long productStructurePK)
    {
        appReqBlock.getUserSession().setCurrentProductStructurePK(productStructurePK);
    }

    public static ProductStructure[] mapVOToEntity(ProductStructureVO[] productStructureVOs)
    {
        ProductStructure[] productStructures = null;

        if (productStructureVOs != null)
        {
            productStructures = new ProductStructure[productStructureVOs.length];

            for (int i = 0; i < productStructureVOs.length; i++)
            {
                productStructures[i] = new ProductStructure(productStructureVOs[i]);
            }
        }

        return productStructures;
    }

    public static ProductStructureVO[] mapEntityToVO(ProductStructure[] productStructures)
    {
        ProductStructureVO[] productStructureVOs = null;

        if (productStructures != null)
        {
            productStructureVOs = new ProductStructureVO[productStructures.length];

            for (int i = 0; i < productStructures.length; i++)
            {
                productStructureVOs[i] = (ProductStructureVO) productStructures[i].getVO();
            }
        }

        return productStructureVOs;
    }

    ////////////////////////////////////////////////////////////////////////////
    //////////////////////////// OBJECT ////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////

    /**
     * Do a comparison on the ProductStructureVO's attributes.
     * @param obj
     * @return
     */
    public boolean equals(Object obj)
    {
        if (this == obj)
        {
            return true;
        }

        if (!(obj instanceof ProductStructure))
        {
            return false;   // this handles null case
        }

        ProductStructure productStructure = (ProductStructure) obj;

        ProductStructureVO compVO2 = (ProductStructureVO) productStructure.getVO();

        ProductStructureVO compVO1 = (ProductStructureVO) getVO();

        Company company02 = Company.findByPK(new Long(compVO2.getCompanyFK()));
        Company company01 = Company.findByPK(new Long(compVO1.getCompanyFK()));

        if (compVO1 == null || compVO2 == null)
        {
            return false;   // even if both are null, it should be false
        }

        boolean ret =
            (compVO1.getAreaName() == null) ?
                compVO2.getAreaName() == null :
                compVO1.getAreaName().equals(compVO2.getAreaName())
            && (compVO1.getAccountingClosePeriod() == null) ?
                compVO2.getAccountingClosePeriod() == null :
                compVO1.getAccountingClosePeriod().equals(compVO2.getAccountingClosePeriod())
            && (compVO1.getBusinessContractName() == null) ?
                compVO2.getBusinessContractName() == null :
                compVO1.getBusinessContractName().equals(compVO2.getBusinessContractName())
            && (company01.getCompanyName() == null) ?
                company02.getCompanyName() == null :
                company01.getCompanyName().equals(company02.getCompanyName())
            && (compVO1.getMarketingPackageName() == null) ?
                compVO2.getMarketingPackageName() == null :
                compVO1.getMarketingPackageName().equals(compVO2.getMarketingPackageName())
            && (compVO1.getTypeCodeCT() == null) ?
                compVO2.getTypeCodeCT() == null :
                compVO1.getTypeCodeCT().equals(compVO2.getTypeCodeCT());

        return ret;
    }

    /**
     * Returns Product Structure string in the format of <ProductName>, <MarketingPacakageName>, <GroupProductName>,
     * <AreaName>, <BusinessContractName>
     * @return String representing all the names in the product structure.
     */
    public String toString()
    {
        return new StringBuffer(getCompanyName()).append(COMPANYSTRUCTURE_DEFAULT_DELIMITER).append(
                                getMarketingPackageName()).append(COMPANYSTRUCTURE_DEFAULT_DELIMITER).append(
                                getGroupProductName()).append(COMPANYSTRUCTURE_DEFAULT_DELIMITER).append(
                                getAreaName()).append(COMPANYSTRUCTURE_DEFAULT_DELIMITER).append(
                                getBusinessContractName()).toString();
    }

    /**
     * Finder.
     * @return
     */
    public static ProductStructure[] findAll()
    {
        ProductStructureVO[] productStructureVOs = new ProductStructureDAO().findAllProductStructures();

        return (ProductStructure[]) CRUDEntityImpl.mapVOToEntity(productStructureVOs, ProductStructure.class);
    }

    /**
     * Finder. Hibernate.
     * @return
     */
    public static ProductStructure[] find_All()
    {
        String hql = "select distinct ps from ProductStructure ps, Company company " +
                     "join fetch ps.Company where ps.CompanyFK = company.CompanyPK";

        List results = SessionHelper.executeHQL(hql, null, ProductStructure.DATABASE);

        ProductStructure[] productStructures = null;
        if (!results.isEmpty())
        {
            productStructures = (ProductStructure[]) results.toArray(new ProductStructure[results.size()]);
        }
        return productStructures;
    }

    /**
     * Finder.
     * @param productStructurePK
     * @return
     */
    public static ProductStructure findByPK(Long productStructurePK)
    {
        return (ProductStructure) SessionHelper.get(ProductStructure.class, productStructurePK, ProductStructure.DATABASE);
    }

    /**
     * Finder by names.
     * @param companyName
     * @param marketingPackageName
     * @param groupProductName
     * @param areaName
     * @param businessContractName
     * @return
     */
    public static final ProductStructure findByNames(String companyName,
                                                     String marketingPackageName,
                                                     String groupProductName,
                                                     String areaName,
                                                     String businessContractName)
    {
        if (companyName == null || marketingPackageName == null || groupProductName == null ||
                areaName == null || businessContractName == null)
        {
            return null;
        }

        ProductStructure productStructure = null;

        String hql = "select ps from ProductStructure ps, Company company  " +
                     "join fetch ps.Company where company.CompanyName = :companyName" +
                     " and ps.MarketingPackageName = :marketingPackageName" +
                     " and ps.GroupProductName = :groupProductName" +
                     " and ps.AreaName = :areaName" +
                     " and ps.BusinessContractName = :businessContractName" +
                     " and ps.CompanyFK = company.CompanyPK";

        Map params = new HashMap();

        params.put("companyName", companyName);
        params.put("marketingPackageName", marketingPackageName);
        params.put("groupProductName", groupProductName);
        params.put("areaName", areaName);
        params.put("businessContractName", businessContractName);

        List results = SessionHelper.executeHQL(hql, params, ProductStructure.DATABASE);

        if (! results.isEmpty())
        {
            productStructure = (ProductStructure) results.get(0);
        }

        return productStructure;
    }

    /** Finder By typeCodeCT
     * @param typeCodeCT
     * @return
     */
    public static final ProductStructure[] findByTypeCodeCT(String typeCodeCT)
    {
        String hql = "select distinct ps from ProductStructure ps, Company company" +
                     " join fetch ps.Company where ps.CompanyFK = company.CompanyPK" +
                     " and ps.TypeCodeCT = :typeCodeCT";

        Map params = new HashMap();

        params.put("typeCodeCT", typeCodeCT);

        List results = SessionHelper.executeHQL(hql, params, ProductStructure.DATABASE);

        return (ProductStructure[]) results.toArray(new ProductStructure[results.size()]);
    }

    /**
     * Finder. Hibernate. No sorting.
     * @return
     */
    public static ProductStructure[] find_All_V1()
    {
        String hql = "from ProductStructure";

        List results = SessionHelper.executeHQL(hql, null, ProductStructure.DATABASE);

        return (ProductStructure[]) results.toArray(new ProductStructure[results.size()]);
    }

    /**
     * Finder by marketing package name.
     * @param marketingPackageName
     * @return
     */
    public static final ProductStructure[] findByMarketingPackage(String marketingPackageName)
    {
        String hql = "select ps from ProductStructure ps where ps.MarketingPackageName = :marketingPackageName";

        Map params = new HashMap();

        params.put("marketingPackageName", marketingPackageName);

        List results = SessionHelper.executeHQL(hql, params, ProductStructure.DATABASE);

        return (ProductStructure[]) results.toArray(new ProductStructure[results.size()]);
    }

    /**
     * Finder. Hibernate. Sorts by the business ordering of the ProductStructure.
     * @return
     */
    public static ProductStructure[] find_All_V2()
    {
        //Not converted to use Company for companyName - currently not used.
        String hql = "from ProductStructure ps order by  ps.MarketingPackageName, ps.GroupProductName, ps.AreaName, ps.BusinessContractName asc";

        List results = SessionHelper.executeHQL(hql, null, ProductStructure.DATABASE);

        return (ProductStructure[]) results.toArray(new ProductStructure[results.size()]);
    }
    
    /**
     * Finder. Hibernate. Includes the associated Company and is sorted by:
     * CompanyName, MarketingPackageName, GroupProductName, AreaName, BusinessContractName asc.
     * @return
     */
    public static ProductStructure[] find_All_V3()
    {
        String hql = " select productStructure" +
                    " from ProductStructure productStructure" +
                    " join fetch productStructure.Company";
        
        List<ProductStructure> results = SessionHelper.executeHQL(hql, null, SessionHelper.ENGINE);
        
        return results.toArray(new ProductStructure[results.size()]);
    }
    
    /**
   * Finds all ProductStructures fetched with their associated Company.
   * @return
   */
    public static ProductStructure[] findBy_ProductStructurePKs(Long[] productStructurePKs)
    {
      String hql = " select productStructure" +
                  " from ProductStructure productStructure" +
                  " join fetch productStructure.Company" +
                  " where productStructure.ProductStructurePK in (";
                  
                  for (int i = 0; i < productStructurePKs.length; i++)
                  {
                    hql += productStructurePKs[i] ;
                    
                    if ( i < (productStructurePKs.length - 1))
                    {
                      hql += ", ";
                    }
                  }
      
                  hql += ")" +
                  " order by  productStructure.MarketingPackageName, productStructure.GroupProductName, productStructure.AreaName, productStructure.BusinessContractName asc";
                  
      List<ProductStructure> productStructures = SessionHelper.executeHQL(hql, null, ProductStructure.DATABASE);
      
      return productStructures.toArray(new ProductStructure[productStructures.size()]);
    }


    /**
     * Originally from FastDAO.findProductStructurePKsByProductName
     * @param productName
     * @return
     */
    public static ProductStructure[] findBy_CompanyName(String companyName)
    {
        String hql = "select productStructure from ProductStructure productStructure" +
                     " join fetch productStructure.Company company" +
                     " where company.CompanyName = :companyName";

        Map params = new HashMap();

        params.put("companyName", companyName);

        List results = SessionHelper.executeHQL(hql, params, ProductStructure.DATABASE);

        return (ProductStructure[]) results.toArray(new ProductStructure[results.size()]);
    }
    
  /**
   * Originally from FastDAO.findProductStructurePKsByProductName
   * @param businessContractName
   * @return
   */
  public static ProductStructure[] findBy_BusinessContractName(String businessContractName)
  {
      String hql = "select productStructure from ProductStructure productStructure" +
                  " where productStructure.BusinessContractName = :businessContractName";

      Map params = new HashMap();

      params.put("businessContractName", businessContractName);

      List results = SessionHelper.executeHQL(hql, params, ProductStructure.DATABASE);

      return (ProductStructure[]) results.toArray(new ProductStructure[results.size()]);
  }    

    /**
     * Clones all filtered relations of Area, Rules, Requirements, and Codetable from the specified ProductStructure
     * to this ProductStructure.
     * @param fromProductStructure
     */
    public void cloneFilteredRelations(ProductStructure fromProductStructure) throws Exception
    {
        Set associatedAreaValues = fromProductStructure.getAreaValues();
        cloneAreaRelations(associatedAreaValues);

        Set associatedRules = fromProductStructure.getRules();
        cloneRuleRelations(associatedRules);

        FilteredCodeTable[] filteredCodeTables = new FilteredCodeTable().findByProductStructure(fromProductStructure);
        cloneFilteredCodeTables(filteredCodeTables);

        Requirement[] requirements = new Requirement().findBy_ProductStructure(fromProductStructure);
//        FilteredRequirement[] filteredRequirements = new FilteredRequirement().findBy_ProductStructure(fromProductStructure);
        cloneFilteredRequirements(requirements);
    }

    private void cloneAreaRelations(Set associatedAreaValues)
    {
        Iterator it = associatedAreaValues.iterator();

        while (it.hasNext())
        {
            AreaValue areaValue = (AreaValue) it.next();

            FilteredAreaValue newFilteredAreaValue = new FilteredAreaValue();
            newFilteredAreaValue.setProductStructure(this);
            newFilteredAreaValue.setAreaValue(areaValue);

            SessionHelper.saveOrUpdate(newFilteredAreaValue, ProductStructure.DATABASE);
        }
    }

    private void cloneRuleRelations(Set associatedRules)
    {
        Iterator it = associatedRules.iterator();

        while (it.hasNext())
        {
            Rules rules = (Rules) it.next();

            ProductRuleStructure newProductRuleStructure = new ProductRuleStructure();
            newProductRuleStructure.setProductStructure(this);
            newProductRuleStructure.setRules(rules);

            SessionHelper.saveOrUpdate(newProductRuleStructure, ProductStructure.DATABASE);
        }
    }

    /**
     * Duplicates the filteredCodeTable entries for this productStructure.
     * @param filteredCodeTable
     * @throws Exception
     */
    private void cloneFilteredCodeTables(FilteredCodeTable[] filteredCodeTables) throws Exception
    {
        for (int i = 0; i < filteredCodeTables.length; i++)
        {
            FilteredCodeTable filteredCodeTable = filteredCodeTables[i];

            FilteredCodeTable newFilteredCodeTable = new FilteredCodeTable(filteredCodeTable.getCodeTableFK(), filteredCodeTable.getCodeDescription(), this);

            SessionHelper.saveOrUpdate(newFilteredCodeTable, SessionHelper.EDITSOLUTIONS);
        }
    }

    /**
     * Duplicates the filteredRequirements table entries for this product structure.
     * @param filteredRequirements
     * @throws Exception
     */
    private void cloneFilteredRequirements(Requirement[] requirements) throws Exception
    {
        for (int i = 0; i < requirements.length; i++)
        {
            Requirement requirement = requirements[i];

            FilteredRequirement newFilteredRequirement = new FilteredRequirement(this);
            newFilteredRequirement.setRequirement(requirement);

            SessionHelper.saveOrUpdate(newFilteredRequirement, SessionHelper.EDITSOLUTIONS);
        }
    }

    /**
     * Save the entity using Hibernate
     */
    public void hSave()
    {
        SessionHelper.saveOrUpdate(this, ProductStructure.DATABASE);
    }

    /**
     * Delete the entity using Hibernate
     */
    public void hDelete()
    {
        SessionHelper.delete(this, ProductStructure.DATABASE);
    }

   /**
     * Checks to see if the specified ContributingProduct is associated with this
     * ProductStructure.
     * @param contributingProduct
     * @return
     */
    public boolean isAssociated(ContributingProduct contributingProduct)
    {
        Long productStructureFK = contributingProduct.getProductStructureFK();

        return (productStructureFK.equals(getProductStructurePK()));
    }

    public void add(ContributingProduct contributingProduct)
    {
        
	}

    public static boolean isCaseStructure(Long productStructurePK)
    {
        boolean productIsCase = false;

        ProductStructure productStructure = ProductStructure.findByPK(productStructurePK);

        if (productStructure != null && productStructure.getGroupTypeCT() != null)
        {
            if (productStructure.getGroupTypeCT().equalsIgnoreCase("case"))
            {
                productIsCase = true;
            }
        }

        return productIsCase;
    }

    public static Long checkForSecurityStructure(ProductStructure[] productStructures)
    {
        Long securityProductStructurePK = 0L;

        for (int i = 0; i < productStructures.length; i++)
        {
            ProductStructure productStructure = productStructures[i];
            String name = productStructure.getBusinessContractName();
            if (name.equals("*"))
            {
                securityProductStructurePK = productStructure.getProductStructurePK();
            }
        }

        return securityProductStructurePK;
    }

    public static List checkForAuthorizedCompanies(ProductStructure[] productStructures)
    {
        List companiesAllowed = new ArrayList();

        for (int i = 0; i < productStructures.length; i++)
        {
            ProductStructure productStructure = productStructures[i];

            String companyName = productStructure.getCompanyName();

            if (!companyName.equalsIgnoreCase("Security") &&
                !companiesAllowed.contains(companyName))
            {
                companiesAllowed.add(companyName);
            }
        }

        return companiesAllowed;
    }

    public static Set checkForAuthorizedStructures(ProductStructure[] productStructures)
    {
        Set productStructuresAllowed = new HashSet();

        for (int i = 0; i < productStructures.length; i++)
        {
            ProductStructure productStructure = productStructures[i];
            String name = productStructure.getBusinessContractName();
            if (!name.equals("*"))
            {
                productStructuresAllowed.add(name);
            }
        }

        return productStructuresAllowed;
    }

  /**
   * Finder (note the abbreviations are spelled-out by the parameter list.
   * @param companyName
   * @param marketingPackageName
   * @param groupProductName
   * @param areaName
   * @param businessContractName
   * @return
   */
  public static ProductStructure findBy_CompanyName_MPN_GPN_AN_BCN(String companyName, String marketingPackageName, String groupProductName, String areaName, String businessContractName)
  {
    ProductStructure productStructure = null;
  
    String hql = " select productStructure" + 
                " from ProductStructure productStructure" +
                " join productStructure.Company company" +
                " where company.CompanyName = :companyName" +
                " and productStructure.MarketingPackageName = :marketingPackageName" +
                " and productStructure.GroupProductName = :groupProductName" +
                " and productStructure.AreaName = :areaName" +
                " and productStructure.BusinessContractName = :businessContractName";
                
    EDITMap params = new EDITMap("companyName", companyName)
                    .put("marketingPackageName", marketingPackageName)
                    .put("groupProductName", groupProductName)
                    .put("areaName", areaName)                    
                    .put("businessContractName", businessContractName);
                    
    List<ProductStructure> results = SessionHelper.executeHQL(hql, params, ProductStructure.DATABASE);
    
    if (!results.isEmpty())
    {
      productStructure = results.get(0);
    }
    
    return productStructure;
  }

    public String getDatabase()
    {
        return ProductStructure.DATABASE;
    }

    /**
     * Finder.
     * 
     * Returns a pre-fetched, composite structure that resembles:
     * 
     * ProductStructure.Company.
     * 
     * @param productStructurePK
     * @return
     */
    public static ProductStructure findBy_ProductStructurePK_V1(Long productStructurePK)
    {
        ProductStructure productStructure = null;
    
        String hql = " select productStructure" +
                    " from ProductStructure productStructure" +
                    " join fetch productStructure.Company company" +
                    " where productStructure.ProductStructurePK = :productStructurePK";
                    
        EDITMap params = new EDITMap("productStructurePK", productStructurePK);
        
        List<ProductStructure> results = SessionHelper.executeHQL(hql, params, SessionHelper.ENGINE);
        
        if (!results.isEmpty())
        {
            productStructure = results.get(0);
        }
        
        return productStructure;
    }
    
    /**
     * Finder that uses separate Hibernate Session.
     * @param productStructurePK
     * @return
     */
    public static ProductStructure findSeparateBy_ProductStructurePK(Long productStructurePK)
    {
        ProductStructure productStructure = null;
        
        Session session = null;

        try
        {
            session = SessionHelper.getSeparateSession(ProductStructure.DATABASE);

            productStructure = (ProductStructure) session.get(ProductStructure.class, productStructurePK);
        }
        finally
        {
            if (session != null) session.close();
        }

        return productStructure;         
    }     
    
    /**
     * Normally, we think that a Company can have more than one associated ProductStructure.
     * In the case of a ProductStructure.TypeCodeCT = 'Syste' - this is not true - there
     * can only be one ProductStructure. This finder looks for ProductStructure.TypeCodeCT = 'System'
     * for the Company of the specified companyName.
     * @param companyName
     * @return
     */
    public static ProductStructure findBy_CompanyName_V2(String companyName)
    {
        ProductStructure productStructure = null;
        
        String hql = " select productStructure" +
                    " from ProductStructure productStructure" +
                    " join productStructure.Company company" + 
                    " where productStructure.TypeCodeCT = '" + ProductStructure.TYPECODECT_SYSTEM + "'" +
                    " and company.CompanyName = :companyName";
        
        Map params = new HashMap();
        
        params.put("companyName", companyName);
        
        List results = SessionHelper.executeHQL(hql, params, DATABASE);
        
        if (!results.isEmpty())
        {
            productStructure = (ProductStructure) results.get(0);
        }
        
        return productStructure;            
    }
}
