package edit.portal.widget;

import edit.portal.widgettoolkit.TableRow;
import engine.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: gfrosti
 * Date: Feb 2, 2006
 * Time: 10:08:47 AM
 * To change this template use File | Settings | File Templates.
 */
public class FilteredProductTableRow extends TableRow
{
    private ProductStructure productStructure;

    public FilteredProductTableRow(ProductStructure productStructure)
    {
        this.productStructure = productStructure;

        populateCellValues();
    }

    /**
     * Builds the Map of cell values with cell name/value pairs.
     */
    private void populateCellValues()
    {
        Company company = productStructure.getCompany();
        
        getCellValues().put(FilteredProductTableModel.COLUMN_COMPANY, company.getCompanyName());

        getCellValues().put(FilteredProductTableModel.COLUMN_MARKETING_PACKAGE, productStructure.getMarketingPackageName());

        getCellValues().put(FilteredProductTableModel.COLUMN_GROUP_PRODUCT, productStructure.getGroupProductName());

        getCellValues().put(FilteredProductTableModel.COLUMN_AREA, productStructure.getAreaName());

        getCellValues().put(FilteredProductTableModel.COLUMN_BUSINESS_CONTRACT, productStructure.getBusinessContractName());
    }

    public String getRowId()
    {
        return productStructure.getProductStructurePK().toString();
    }

    /**
     * Getter.
     * @return
     */
    public ProductStructure getProductStructure()
    {
        return productStructure;
    }
}
