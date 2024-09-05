package edit.portal.widget;

import edit.portal.widgettoolkit.*;
import engine.*;
import fission.global.AppReqBlock;

import java.util.Arrays;

/**
 * Created by IntelliJ IDEA.
 * User: gfrosti
 * Date: Feb 2, 2006
 * Time: 10:07:56 AM
 * To change this template use File | Settings | File Templates.
 */
public class ProductStructureTableModel extends TableModel
{
    public static final String COLUMN_COMPANY = "Company";

    public static final String COLUMN_MARKETING_PACKAGE = "Marketing Package";

    public static final String COLUMN_GROUP_PRODUCT = "Group Product";

    public static final String COLUMN_AREA = "Area";

    public static final String COLUMN_BUSINESS_CONTRACT = "Business Contract";

    private static final String[] COLUMN_NAMES = {COLUMN_COMPANY, COLUMN_MARKETING_PACKAGE, COLUMN_GROUP_PRODUCT, COLUMN_AREA, COLUMN_BUSINESS_CONTRACT};

    public ProductStructureTableModel(AppReqBlock appReqBlock)
    {
        super(appReqBlock);

        getColumnNames().addAll(Arrays.asList(COLUMN_NAMES));
    }

    protected void buildTableRows()
    {
        ProductStructure[] productStructures = ProductStructure.findByTypeCodeCT(ProductStructure.TYPECODECT_PRODUCT);

        for (int i = 0; i < productStructures.length; i++)
        {
            ProductStructure currentProductStructure = productStructures[i];

            ProductStructureTableRow tableRow = new ProductStructureTableRow(currentProductStructure);

            if (tableRow.getRowId().equals(this.getSelectedRowId()))
            {
                tableRow.setRowStatus(TableRow.ROW_STATUS_SELECTED);
            }

            super.addRow(tableRow);
        }
    }
}
