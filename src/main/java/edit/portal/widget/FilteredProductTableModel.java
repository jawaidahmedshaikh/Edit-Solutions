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
 * ProductStructures that are associated to a ContractGroup are done so 
 * via the FilteredProduct table. This model represents the state of 
 * those relationships for the current ContractGroup.
 */
public class FilteredProductTableModel extends TableModel
{
  public static final String COLUMN_COMPANY = "CY";

  public static final String COLUMN_MARKETING_PACKAGE = "MP";

  public static final String COLUMN_GROUP_PRODUCT = "GP";

  public static final String COLUMN_AREA = "AR";

  public static final String COLUMN_BUSINESS_CONTRACT = "BC";

  private static final String[] COLUMN_NAMES =
  { COLUMN_COMPANY, COLUMN_MARKETING_PACKAGE, COLUMN_GROUP_PRODUCT, COLUMN_AREA, COLUMN_BUSINESS_CONTRACT };

  /**
   * The targeted ContractGroup.
   */
  private Long contractGroupPK;

  public FilteredProductTableModel(AppReqBlock appReqBlock, int scope)
  {
      super(appReqBlock);

      getColumnNames().addAll(Arrays.asList(COLUMN_NAMES));

      String contractGroupPKStr = Util.initString(appReqBlock.getReqParm("contractGroupPK"), null);

      if (contractGroupPKStr != null)
      {
          setContractGroupPK(new Long(contractGroupPKStr));
      }
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
   * Getter.
   * @see #contractGroupPK
   * @return
   */
  private Long getContractGroupPK()
  {
    return contractGroupPK;
  }

  /**
   * True if a ContractGroup has been selected and available to 
   * this TableModel.
   * @return
   */
  public boolean contractGroupIsPresent()
  {
    return (getContractGroupPK() != null);
  }

  protected void buildTableRows()
  {
    FilteredProduct[] filteredProducts = null;

    if (contractGroupIsPresent())
    {
      filteredProducts = FilteredProduct.findBy_ContractGroupPK(getContractGroupPK());

      if (filteredProducts.length > 0)
      {
        ProductStructure[] productStructures = ProductStructure.findBy_ProductStructurePKs(getProductStructurePKs(filteredProducts));

        for (int i = 0; i < productStructures.length; i++)
        {
            ProductStructure currentProductStructure = productStructures[i];

            FilteredProductTableRow tableRow = new FilteredProductTableRow(currentProductStructure);

            super.addRow(tableRow);
        }
      }
    }
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
}
