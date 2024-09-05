/*
 * User: gfrosti
 * Date: May 18, 2007
 * Time: 10:10:10 AM
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */

package contract;

import edit.common.EDITDate;
import edit.common.EDITMap;

import edit.common.exceptions.EDITContractException;

import edit.services.db.hibernate.HibernateEntity;
import edit.services.db.hibernate.SessionHelper;

import engine.ProductStructure;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import group.*;
import fission.utility.DateTimeUtil;
import staging.IStaging;
import staging.StagingContext;

/**
 * Like so many  other places in our system, it is necessary to define the set
 * of allowable entities by "Product". In this case, we are defining the set of
 * actual Products that are allowed to a ContractGroup. This is another form of 
 * product setup. A Segment that is being added to a ContractGroup should only 
 * be associated to a Product that supports this mapping.
 * @author gfrosti
 */
public class FilteredProduct extends HibernateEntity implements IStaging
{
    /**
     * The PK.
     */
    private Long filteredProductPK;

    /**
     * The grouping instance (a ContractGroup) for which
     * this FilteredProduct defines an allowable Product.
     */
    private Long contractGroupFK;

    /**
     * The associated ContractGroup.
     */
    private ContractGroup contractGroup;

    /**
     * The allowable Product association.  ProductStructure is in a different database so we only have the FK here, not the parent object
     */
    private Long productStructureFK;

    /**
     * The start date for which this product association is valid.
     */
    private EDITDate effectiveDate;

    /**
     * The end date for which this product association becomes invalid.
     */
    private EDITDate terminationDate;

    /**
     * Informational number only.
     */
    private String masterContractNumber;

    private String operator;
    private EDITDate creationDate;
    private String masterContractName;

    private Set<MasterContract> masterContracts;

    /**
     * Database to be used for this object
     */
    public static final String DATABASE = SessionHelper.EDITSOLUTIONS;



    /** Creates a new instance of FilteredProduct */
    public FilteredProduct()
    {
    }

    /**
     * @see #filteredProductPK
     * @return Long
     */
    public Long getFilteredProductPK()
    {
        return filteredProductPK;
    }

    /**
     * @param filteredProductPK
     * @see #filteredProductPK
     */
    public void setFilteredProductPK(Long filteredProductPK)
    {
        this.filteredProductPK = filteredProductPK;
    }

    /**
     * @see #contractGroupFK
     * @return Long
     */
    public Long getContractGroupFK()
    {
        return contractGroupFK;
    }

    /**
     * @see #contractGroupFK
     * @param contractGroupFK
     */
    public void setContractGroupFK(Long contractGroupFK)
    {
        this.contractGroupFK = contractGroupFK;
    }

    /**
     * @see #contractGroup
     * @param contractGroup
     */
    public void setContractGroup(ContractGroup contractGroup)
    {
        this.contractGroup = contractGroup;
    }

    /**
     * @see #contractGroup
     * @return ContractGroup
     */
    public ContractGroup getContractGroup()
    {
        return contractGroup;
    }

    /**
     * @see #productStructureFK
     * @return Long
     */
    public Long getProductStructureFK()
    {
        return productStructureFK;
    }

    /**
     * @see #productStructureFK
     * @param productStructureFK
     */
    public void setProductStructureFK(Long productStructureFK)
    {
        this.productStructureFK = productStructureFK;
    }

    /**
     * Gets the parent object by looking up via its key.  ProductStructure is in a different database so can't have
     * the parent object as part of this object.
     *
     * @see #productStructure
     * @return productStructure
     */
    public ProductStructure getProductStructure()
    {
        return ProductStructure.findByPK(this.getProductStructureFK());
    }

    /**
     * @see #effectiveDate
     * @return
     */
    public EDITDate getEffectiveDate()
    {
        return effectiveDate;
    }

    /**
     * @see #effectiveDate
     * @param effectiveDate
     */
    public void setEffectiveDate(EDITDate effectiveDate)
    {
        this.effectiveDate = effectiveDate;
    }

    /**
     * @see #terminationDate
     * @return EDITDate
     */
    public EDITDate getTerminationDate()
    {
        return terminationDate;
    }

    /**
     * @see #terminationDate
     * @param terminationDate
     */
    public void setTerminationDate(EDITDate terminationDate)
    {
        this.terminationDate = terminationDate;
    }

    /**
     * @see #masterContractNumber
     * @return the masterContractNumber
     */
    public String getMasterContractNumber()
    {
        return masterContractNumber;
    }

    /**
     * @param masterContractNumber
     * @see #masterContractNumber
     */
    public void setMasterContractNumber(String masterContractNumber)
    {
        this.masterContractNumber = masterContractNumber;
    }

    /**
     * @param operator
     */
    public void setOperator(String operator)
    {
        this.operator = operator;
    }

    /**
     * @return the operator
     */
    public String getOperator()
    {
        return operator;
    }

    /**
     * @param creationDate
     */
    public void setCreationDate(EDITDate creationDate)
    {
        this.creationDate = creationDate;
    }

    /**
     * @return the creationDate
     */
    public EDITDate getCreationDate()
    {
        return creationDate;
    }

    /**
     * Convenience (bean) method to support UI's need for dd/mm/yyyy format.
     * @return
     */
    public String getUIFPEffectiveDate()
    {
        String date = null;

        if (getEffectiveDate() != null)
        {
            date = DateTimeUtil.formatEDITDateAsMMDDYYYY(this.getEffectiveDate());
        }

        return date;
    }

    /**
     * Convenience method to support UI's need for mm/dd/yyyy.
     */
    public void setUIFPEffectiveDate(String uiEffectiveDate)
    {
        if (uiEffectiveDate != null)
        {
            setEffectiveDate(DateTimeUtil.getEDITDateFromMMDDYYYY(uiEffectiveDate));
        }
    }

    /**
     * Convenience (bean) method to support UI's need for dd/mm/yyyy format.
     * @return
     */
    public String getUITerminationDate()
    {
        String date = null;

        if (getTerminationDate() != null)
        {
            date = DateTimeUtil.formatEDITDateAsMMDDYYYY(this.getTerminationDate());
        }

        return date;
    }

    /**
     * Convenience method to support UI's need for mm/dd/yyyy.
     */
    public void setUITerminationDate(String uiTerminationDate)
    {
        if (uiTerminationDate != null)
        {
            setTerminationDate(DateTimeUtil.getEDITDateFromMMDDYYYY(uiTerminationDate));
        }
    }

    /**
     * Convenience (bean) method to support UI's need for dd/mm/yyyy format.
     * @return
     */
    public String getUICreationDate()
    {
        String date = null;

        if (getCreationDate() != null)
        {
            date = DateTimeUtil.formatEDITDateAsMMDDYYYY(this.getCreationDate());
        }

        return date;
    }

    /**
     * Convenience method to support UI's need for mm/dd/yyyy.
     */
    public void setUICreationDate(String uiCreationDate)
    {
        if (uiCreationDate != null)
        {
            setCreationDate(DateTimeUtil.getEDITDateFromMMDDYYYY(uiCreationDate));
        }
    }

    /**
     * Getter.
     * @return
     */
    public String getMasterContractName()
    {
        return masterContractName;
    }

    /**
     * Setter.
     * @param masterContractName
     */
    public void setMasterContractName(String masterContractName)
    {
        this.masterContractName = masterContractName;
    }

    /**
     * @see #masterContracts
     * @param masterContracts
     */
    public void setMasterContracts(Set<MasterContract> masterContracts)
    {
        this.masterContracts = masterContracts;
    }

    /**
     * @see #masterContracts
     * @return
     */
    public Set<MasterContract> getMasterContracts()
    {
        return masterContracts;
    }

    /**
     * A complex finder that first finds all "candidate" ProductStructurePKs that have a matching businessContractName. From
     * this list, the set of associated FilteredProducts by ContractGroup are found. This complex approach is necessary since
     * the targeted tables are in multiple databases.
     * @param contractGroupPK
     * @return
     */
    public static FilteredProduct[] findBy_ContractGroupPK_BusinessContractName(Long contractGroupPK, String businessContractName)
    {
        List<FilteredProduct> filteredProducts = null;

        String engineHql = " from ProductStructure productStructure" + " where upper(productStructure.businessContractName) = upper(:businessContractName)";

        EDITMap engineParams = new EDITMap("businessContractName", businessContractName);

        List<ProductStructure> engineResults = SessionHelper.executeHQL(engineHql, engineParams, SessionHelper.ENGINE);

        if (!engineResults.isEmpty())
        {
            String contractHql = " from FilteredProduct filteredProduct" + " where filteredProduct.ContractGroupFK = :contractGroupFK" + " and filteredProduct.ProductStructureFK in (";

            for (int i = 0; i < engineResults.size(); i++)
            {
                if (i < (engineResults.size() - 1))
                {
                    contractHql += engineResults.get(i).getProductStructurePK().toString() + ", ";
                }
            }

            contractHql += ")";

            EDITMap contractParams = new EDITMap("contractGroupFK", contractGroupPK);

            filteredProducts = SessionHelper.executeHQL(contractHql, contractParams, FilteredProduct.DATABASE);
        }
        else
        {
            filteredProducts = new ArrayList<FilteredProduct>();
        }

        return filteredProducts.toArray(new FilteredProduct[filteredProducts.size()]);
    }

    /**
     * A complex finder that first finds all "candidate" ProductStructurePKs that have a matching companyName (via the associated Company). From
     * this list, the set of associated FilteredProducts by ContractGroup are found. This complex approach is necessary since
     * the targeted tables are in multiple databases.
     * @param contractGroupPK
     * @return
     */
    public static FilteredProduct[] findBy_ContractGroupPK(Long contractGroupPK)
    {
        String hql = " from FilteredProduct filteredProduct" + " where filteredProduct.ContractGroupFK = :contractGroupFK";

        EDITMap params = new EDITMap("contractGroupFK", contractGroupPK);

        List<FilteredProduct> filteredProducts = SessionHelper.executeHQL(hql, params, FilteredProduct.DATABASE);

        return filteredProducts.toArray(new FilteredProduct[filteredProducts.size()]);
    }

    public static FilteredProduct[] findBy_ContractGroupPK_CompanyName(Long contractGroupPK, String companyName)
    {
        List<FilteredProduct> filteredProducts = null;

        String engineHql = " select productStructure" + " from ProductStructure productStructure" + " join productStructure.Company company" + " where upper(company.CompanyName) = upper(:companyName)";

        EDITMap engineParams = new EDITMap("companyName", companyName);

        List<ProductStructure> engineResults = SessionHelper.executeHQL(engineHql, engineParams, SessionHelper.ENGINE);

        if (!engineResults.isEmpty())
        {
            String contractHql = " from FilteredProduct filteredProduct" + " where filteredProduct.ContractGroupFK = :contractGroupFK" + " and filteredProduct.ProductStructureFK in (";

            for (int i = 0; i < engineResults.size(); i++)
            {
                if (i < (engineResults.size() - 1))
                {
                    contractHql += engineResults.get(i).getProductStructurePK().toString() + ", ";
                }
            }

            contractHql += ")";

            EDITMap contractParams = new EDITMap("contractGroupFK", contractGroupPK);

            filteredProducts = SessionHelper.executeHQL(contractHql, contractParams, SessionHelper.EDITSOLUTIONS);
        }
        else
        {
            filteredProducts = new ArrayList<FilteredProduct>();
        }

        return filteredProducts.toArray(new FilteredProduct[filteredProducts.size()]);
    }

    /**
     * Builds a new FilteredProduct entity from the specified ProductStructure and ContractGroup
     * if and only if the association doesn't already exist.
     * @param productStructurePK
     * @param contractGroup
     * @param effectiveDate
     * @param masterContractNumber
     * @param operator
     * @throws EDITContractException if the association already exists (avoiding a duplicate association)
     */
     public static void build(Long productStructurePK, ContractGroup contractGroup ) throws EDITContractException
    {
        if (!filteredProductExists(productStructurePK, contractGroup))
        {
            FilteredProduct filteredProduct = (FilteredProduct) SessionHelper.newInstance(FilteredProduct.class, SessionHelper.EDITSOLUTIONS);

            filteredProduct.setProductStructureFK(productStructurePK);

            filteredProduct.setContractGroup(contractGroup);
        }
        else
        {
            throw new EDITContractException("Duplicate FilteredProduct!");
        }
    }

    /**
     * The the existence of a FilteredProduct as defigned by its unique productStructure/contractGroup associations.
     * @param productStructurePK
     * @param contractGroup
     * @return
     */
    private static boolean filteredProductExists(Long productStructurePK, ContractGroup contractGroup)
    {
        boolean filteredProductExists = false;

        FilteredProduct filteredProduct = FilteredProduct.findBy_ProductStructurePK_ContractGroup(productStructurePK, contractGroup);

        if (filteredProduct != null)
        {
            filteredProductExists = true;
        }

        return filteredProductExists;
    }
    
    /**
     * The the existence of a FilteredProduct as defigned by its unique productStructure/contractGroup associations.
     * @param productStructurePK
     * @param contractGroupPK
     * @return
     */
    public static boolean filteredProductExists(Long productStructurePK, Long contractGroupPK)
    {
        boolean filteredProductExists = false;

        FilteredProduct filteredProduct = FilteredProduct.findBy_ProductStructurePK_ContractGroupPK(productStructurePK, contractGroupPK);

        if (filteredProduct != null)
        {
            filteredProductExists = true;
        }

        return filteredProductExists;
    }

    public static FilteredProduct findByPK(Long filteredProductPK)
    {
        FilteredProduct filteredProduct = null;

        String hql = " from FilteredProduct filteredProduct" +
                     " where filteredProduct.FilteredProductPK = :filteredProductPK";

        EDITMap params = new EDITMap();
        params.put("filteredProductPK", filteredProductPK);

        List<FilteredProduct> results = SessionHelper.executeHQL(hql, params, FilteredProduct.DATABASE);

        if (!results.isEmpty())
        {
            filteredProduct = results.get(0);
        }

        return filteredProduct;
    }

    /**
     * Finder
     * @param productStructurePK
     * @param contractGroup
     * @return
     */
    public static FilteredProduct findBy_ProductStructurePK_ContractGroup(Long productStructurePK, ContractGroup contractGroup)
    {
        FilteredProduct filteredProduct = null;

        String hql = " from FilteredProduct filteredProduct" +
                     " where filteredProduct.ProductStructureFK = :productStructureFK" + 
                     " and filteredProduct.ContractGroup = :contractGroup";

        EDITMap params = new EDITMap("productStructureFK", productStructurePK).put("contractGroup", contractGroup);

        List<FilteredProduct> results = SessionHelper.executeHQL(hql, params, FilteredProduct.DATABASE);

        if (!results.isEmpty())
        {
            filteredProduct = results.get(0);
        }

        return filteredProduct;
    }
    
    /**
     * Finder
     * @param productStructurePK
     * @param contractGroupPK
     * @return
     */
    public static FilteredProduct findBy_ProductStructurePK_ContractGroupPK(Long productStructurePK, Long contractGroupPK)
    {
        FilteredProduct filteredProduct = null;

        String hql = "select filteredProduct from FilteredProduct filteredProduct" +
                     " where filteredProduct.ProductStructureFK = :productStructureFK" +
                     " and filteredProduct.ContractGroupFK = :contractGroupFK";

        EDITMap params = new EDITMap("productStructureFK", productStructurePK).put("contractGroupFK", contractGroupPK);

        List<FilteredProduct> results = SessionHelper.executeHQL(hql, params, FilteredProduct.DATABASE);

        if (!results.isEmpty())
        {
            filteredProduct = results.get(0);
        }

        return filteredProduct;
    }    

    /**
     * A convenience method that returns the String version of the CompanyName + ProductStructure(names).
     * @return
     */
    public String getProductKey()
    {
        ProductStructure productStructure = ProductStructure.findByPK(getProductStructureFK());
        
        return productStructure.toString();
    }

    /**
     * Finder.
     * @param batchContractSetupPK
     * @return
     */
    public static FilteredProduct[] findBy_BatchContractSetupPK(Long batchContractSetupPK)
    {
        String hql = " select filteredProduct" +
                     " from FilteredProduct filteredProduct" +
                     " join filteredProduct.ContractGroup caseContractGroup" +
                     " join caseContractGroup.ContractGroups groupContractGroup" +
                     " join groupContractGroup.BatchContractSetups batchContractSetup" +
                     " where batchContractSetup.BatchContractSetupPK = :batchContractSetupPK";
                     
        EDITMap params = new EDITMap("batchContractSetupPK", batchContractSetupPK);                     
                     
        List<FilteredProduct> results = SessionHelper.executeHQL(hql, params, SessionHelper.EDITSOLUTIONS);                     
        
        return results.toArray(new FilteredProduct[results.size()]);
    }
    
	public String getDatabase()
    {
        return FilteredProduct.DATABASE;
	}
        /**
         * Finder method
         *
         * @param groupNumber
         * @return
         */


    public static FilteredProduct findBy_GroupNumber(String groupNumber)
    {

        String hql = " select filteredProduct from FilteredProduct filteredProduct"
                + " join filteredProduct.ContractGroup caseContractGroup"
                + " join caseContractGroup.ContractGroups contractGroup "
                + " where contractGroup.ContractGroupTypeCT = :contractGroupTypeCT "
                + " and contractGroup.ContractGroupNumber = :groupNumber";


        EDITMap params = new EDITMap();
        params.put("contractGroupTypeCT", ContractGroup.CONTRACTGROUPTYPECT_GROUP);
        params.put("groupNumber", groupNumber);

        List<FilteredProduct> results = SessionHelper.executeHQL(hql, params, FilteredProduct.DATABASE);

        if (results.size() > 0)
        {
            return (FilteredProduct) results.get(0);
        } else
        {
            return null;
        }

    }

    public StagingContext stage(StagingContext stagingContext, String database)
    {
        staging.FilteredProduct stagingFilteredProduct = new staging.FilteredProduct();

        ProductStructure productStructure = this.getProductStructure();
        stagingFilteredProduct.setArea(productStructure.getAreaName());
        stagingFilteredProduct.setBusinessContract(productStructure.getBusinessContractName());
        stagingFilteredProduct.setCompanyName(productStructure.getCompanyName());
        stagingFilteredProduct.setGroupName(productStructure.getGroupProductName());
        stagingFilteredProduct.setMarketingPackage(productStructure.getMarketingPackageName());
        //stagingFilteredProduct.setMasterContractEffectiveDate(this.getEffectiveDate());
        //stagingFilteredProduct.setMasterContractName(this.getMasterContractName());
        //stagingFilteredProduct.setMasterContractNumber(this.getMasterContractNumber());

        stagingContext.setCurrentFilteredProduct(stagingFilteredProduct);

        SessionHelper.saveOrUpdate(stagingFilteredProduct, database);

        return stagingContext;
    }
}
