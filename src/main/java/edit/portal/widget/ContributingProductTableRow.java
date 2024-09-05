/*
 * User: gfrosti
 * Date: Jan 11, 2006
 * Time: 2:00:51 PM
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package edit.portal.widget;

import agent.ContributingProduct;
import edit.portal.widgettoolkit.TableRow;
import engine.ProductStructure;
import fission.utility.*;


public class ContributingProductTableRow extends TableRow implements Comparable
{
    private ContributingProduct contributingProduct;

    /**
     * Constructor. Renders the ProductStructure, StartDate, StopDate of the specified ContributingProduct.
     * @param productStructure
     * @param contributingProduct
     */
    public ContributingProductTableRow(ContributingProduct contributingProduct)
    {
        this.contributingProduct = contributingProduct;

        populateCellValues();
    }

    /**
     * Helper method that establishes the ProductStructure (toString()), StartDate and StopDate for this
     * ContributingProduct.
     */
    private void populateCellValues()
    {
        ProductStructure productStructure = contributingProduct.getProductStructure();

        String productStructureStr = productStructure.toString();

        getCellValues().put(ContributingProductTableModel.COLUMN_COMPANY_STRUCTURE, productStructureStr);

        getCellValues().put(ContributingProductTableModel.COLUMN_START_DATE, DateTimeUtil.formatEDITDateAsMMDDYYYY(contributingProduct.getStartDate()));

        getCellValues().put(ContributingProductTableModel.COLUMN_STOP_DATE, DateTimeUtil.formatEDITDateAsMMDDYYYY(contributingProduct.getStopDate()));
    }

    public String getRowId()
    {
        return contributingProduct.getContributingProductPK().toString();
    }

    /**
     * Sorts by the ProductStructure String.
     * @param o
     * @return
     */
    public int compareTo(Object o)
    {
        String thisProductStructureString = (String) getCellValue(ContributingProductTableModel.COLUMN_COMPANY_STRUCTURE);

        String visitingProductStructureString = (String) ((TableRow) o).getCellValue(ContributingProductTableModel.COLUMN_COMPANY_STRUCTURE);

        return thisProductStructureString.compareTo(visitingProductStructureString);
    }
}
