/*
 * User: cgleason
 * Date: Dec 13, 2005
 * Time: 2:33:14 PM
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package edit.portal.widget;

import edit.portal.widgettoolkit.*;
import contract.*;

import fission.utility.DateTimeUtil;


public class ProductSummaryTableRow extends TableRow
{
    /**
     * The entity whose column values are displayed.
     */
    private FilteredProduct filteredProduct;

    public ProductSummaryTableRow(FilteredProduct filteredProduct)
    {
        super();

        this.filteredProduct = filteredProduct;

        populateCellValues();
    }

    /**
     * Maps values to the Table row.
     */
    private void populateCellValues()
    {
        String productKey = getFilteredProduct().getProductKey();
        
        getCellValues().put(ProductSummaryTableModel.COLUMN_PRODUCT_KEY, productKey);
       
    }

    /**
     * @see edit.portal.widgettoolkit.TableRow#getRowId()
     * @return
     */
    public String getRowId()
    {
        return getFilteredProduct().getFilteredProductPK().toString();
    }
    
    /**
     * @see #filteredProduct
     * @return
     */
    private FilteredProduct getFilteredProduct()
    {
        return filteredProduct;        
    }
}
