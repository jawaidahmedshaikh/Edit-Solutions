/*
 * User: cgleason
 * Date: Dec 8, 2005
 * Time: 3:28:25 PM
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package edit.portal.widget;

import edit.portal.widgettoolkit.*;
import fission.global.*;
import fission.utility.*;

import java.util.*;

import contract.*;


public class ProductSummaryTableModel extends TableModel
{
    public static final String COLUMN_PRODUCT_KEY = "Product Key";
   
    private static final String[] COLUMN_NAMES = {COLUMN_PRODUCT_KEY};

    /**
     * The targeted ContractGroup.
     */
    private Long contractGroupPK;
    
    public ProductSummaryTableModel(AppReqBlock appReqBlock)
    {
        super(appReqBlock);

        getColumnNames().addAll(Arrays.asList(COLUMN_NAMES));
        
        String contractGroupPKStr = Util.initString(appReqBlock.getUserSession().getParameter("activeCasePK"), null);

        if (contractGroupPKStr != null)
        {
          setContractGroupPK(new Long(contractGroupPKStr));
        }        
    }

    /**
     * Builds the superset TableModel
     */
    protected void buildTableRows()
    {
        if (contractGroupIsPresent())
        {
            FilteredProduct[] filteredProducts = FilteredProduct.findBy_ContractGroupPK(getContractGroupPK());
            
            for (FilteredProduct filteredProduct:filteredProducts)
            {
                ProductSummaryTableRow tableRow = new ProductSummaryTableRow(filteredProduct);

                if (tableRow.getRowId().equals(this.getSelectedRowId()))
                {
                    tableRow.setRowStatus(TableRow.ROW_STATUS_SELECTED);
                }

                super.addRow(tableRow);
            }
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
}
