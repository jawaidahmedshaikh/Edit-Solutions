package edit.portal.widget;

import contract.FilteredProduct;

import edit.portal.widgettoolkit.TableModel;

import engine.ProductStructure;

import fission.global.AppReqBlock;

import fission.utility.Util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Products available to a Case are manually mapped from the universe of
 * all available products. This class renders the universe of available
 * ProductStructures.
 */
public class CandidateProductStructureTableModel extends TableModel
{
    public static final String COLUMN_COMPANY = "CY";

    public static final String COLUMN_MARKETING_PACKAGE = "MP";

    public static final String COLUMN_GROUP_PRODUCT = "GP";

    public static final String COLUMN_AREA = "AR";

    public static final String COLUMN_BUSINESS_CONTRACT = "BC";

    private static final String[] COLUMN_NAMES =
    { COLUMN_COMPANY, COLUMN_MARKETING_PACKAGE, COLUMN_GROUP_PRODUCT, COLUMN_AREA, COLUMN_BUSINESS_CONTRACT };

    /**
     * Candidate Products will be filtered by companyName or businessContractName (always).
     */
    private String companyName;

    /**
     * Candidate Products will be filtered by companyName or businessContractName (always).
     */
    private String businessContractName;

    /**
     * The driving ContractGroup for the FilteredProducts.
     */
    private Long contractGroupPK;

    public CandidateProductStructureTableModel(AppReqBlock appReqBlock, int scope)
    {
        super(appReqBlock);

        getColumnNames().addAll(Arrays.asList(COLUMN_NAMES));

        String contractGroupPKStr = Util.initString(appReqBlock.getUserSession().getParameter("activeCasePK"), null);

        setCompanyName(Util.initString(appReqBlock.getReqParm("companyName"), null));

        setBusinessContractName(Util.initString(appReqBlock.getReqParm("businessContractName"), null));

        if (contractGroupPKStr != null)
        {
            setContractGroupPK(new Long(contractGroupPKStr));
        }
    }

    /**
     * True if a ContractGroup has been selected and available to 
     * this TableModel.
     * @return
     */
    public boolean contractGroupExists()
    {
        return (getContractGroupPK() != null);
    }

    /**
     * Getter.
     * @see #contractGroupPK
     * @return
     */
    private Long getContractGroupPK()
    {
        return contractGroupPK;
    }

    /**
     * Renders all the rows in the ProductStructure table [except] for those ProductStructures
     * that have already been assigned to the current Case via FilteredProduct.
     */
    protected void buildTableRows()
    {
        ProductStructure[] productStructures = getProductStructures();

        if (contractGroupExists())
        {
            for (int i = 0; i < productStructures.length; i++)
            {
                if (!associatedWithCase(productStructures[i]))
                {
                    CandidateProductStructureTableRow tableRow = new CandidateProductStructureTableRow(productStructures[i]);

                    super.addRow(tableRow);
                }
            }
        }
    }

    /**
     * Finds:
     * 1. All ProductStructures if no CompanyName/BusinessContractName is specified.
     * 2. A subset of all ProductStructures by CompanyName (if exists).
     * 3. A subset of all ProductStructures by BusinesContractName (if exists).
     * @return
     */
    private ProductStructure[] getProductStructures()
    {
        ProductStructure[] productStructures = null;
        
        if ((getCompanyName() == null) && (getBusinessContractName() == null))
        {
            productStructures = ProductStructure.findByTypeCodeCT(ProductStructure.TYPECODECT_PRODUCT);
        }
        else if (getCompanyName() != null)
        {
            productStructures = ProductStructure.findBy_CompanyName(getCompanyName());
        }
        else if (getBusinessContractName() != null)
        {
            productStructures = ProductStructure.findBy_BusinessContractName(getBusinessContractName());
        }
        
        return productStructures;
    }

    /**
     * Extracts the set of ProductStructurePKs from the specified filteredProducts.
     * @param filteredProducts
     */
    private Long[] getProductStructurePKs(FilteredProduct[] filteredProducts)
    {
        List<Long> productStructurePKs = new ArrayList<Long>();

        for (FilteredProduct filteredProduct: filteredProducts)
        {
            productStructurePKs.add(filteredProduct.getProductStructureFK());
        }

        return productStructurePKs.toArray(new Long[productStructurePKs.size()]);
    }

    /**
     * Setter.
     * @see #contractGroupPK
     * @param contractGroupPK
     */
    private void setContractGroupPK(Long contractGroupPK)
    {
        this.contractGroupPK = contractGroupPK;
    }

    /**
     * True if the specified ProductStructure is [not] in the set of ProductStructures associated with the
     * current ContractGroup.
     * @param productStructure
     * @return
     */
    private boolean associatedWithCase(ProductStructure productStructure)
    {
        Long productStructurePK = productStructure.getProductStructurePK();

        boolean associatedWithCase = FilteredProduct.filteredProductExists(productStructure.getProductStructurePK(), getContractGroupPK());

        return associatedWithCase;
    }

    /**
     * @see #companyName
     * @param companyName
     */
    public void setCompanyName(String companyName)
    {
        this.companyName = companyName;
    }

    /**
     * @see #companyName
     * @return
     */
    public String getCompanyName()
    {
        return companyName;
    }

    /**
     * @see #businessContractName
     * @param businessContractName
     */
    public void setBusinessContractName(String businessContractName)
    {
        this.businessContractName = businessContractName;
    }

    /**
     * @see #businessContractName
     * @return
     */
    public String getBusinessContractName()
    {
        return businessContractName;
    }

    /**
     * True if the companyName is not null.
     * @return
     */
    private boolean companyNameExists()
    {
        return (getCompanyName() != null);
    }

    /**
     * True if the businessContractName is not null.
     * @return
     */
    private boolean businessContractNameExists()
    {
        return (getBusinessContractName() != null);
    }
}
