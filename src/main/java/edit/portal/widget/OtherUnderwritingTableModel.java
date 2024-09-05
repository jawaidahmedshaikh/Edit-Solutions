package edit.portal.widget;

import edit.portal.widgettoolkit.TableModel;

import fission.global.AppReqBlock;
import fission.utility.*;

import java.util.Arrays;

import group.CaseProductUnderwriting;

/**
 * Products available to a Case are manually mapped from the universe of
 * all available products. This class renders the universe of available
 * ProductStructures.
 */
public class OtherUnderwritingTableModel extends TableModel
{
    public static final String COLUMN_FIELD = "Field";

    public static final String COLUMN_QUALIFIER = "Qualifier";

    public static final String COLUMN_VALUE = "Value";

    private static final String[] COLUMN_NAMES = {COLUMN_FIELD, COLUMN_QUALIFIER, COLUMN_VALUE};

    private static String AREAGROUP = "CASEOTHER";

    private CaseProductUnderwriting[] caseProductUnderwriting = null;

    public OtherUnderwritingTableModel(AppReqBlock appReqBlock, CaseProductUnderwriting[] caseProductUnderwriting)
    {
        super(appReqBlock);

        getColumnNames().addAll(Arrays.asList(COLUMN_NAMES));

        this.caseProductUnderwriting = caseProductUnderwriting;
    }

    /**
     * Renders all the rows in the ProductStructure table [except] for those ProductStructures
     * that have already been assigned to the current Case via FilteredProduct.
     */
    protected void buildTableRows()
    {
        if (caseProductUnderwriting != null)
        {
            caseProductUnderwriting = (CaseProductUnderwriting[]) Util.sortObjects(caseProductUnderwriting, new String [] {"getField"});

            for (int i = 0; i < caseProductUnderwriting.length; i++)
            {
                if (caseProductUnderwriting[i].getGrouping().equalsIgnoreCase(AREAGROUP))
                {
                    OtherUnderwritingTableRow tableRow = new OtherUnderwritingTableRow(caseProductUnderwriting[i]);

                    super.addRow(tableRow);
                }
            }
        }
    }
}
