package edit.portal.widget;

import contract.FilteredProduct;

import edit.portal.widgettoolkit.TableModel;

import engine.ProductStructure;
import engine.AreaKey;
import engine.AreaValue;
import engine.FilteredAreaValue;

import fission.global.AppReqBlock;
import fission.utility.*;

import java.util.Arrays;

/**
 * Products available to a Case are manually mapped from the universe of
 * all available products. This class renders the universe of available
 * ProductStructures.
 */
public class CandidateBaseUnderwritingTableModel extends TableModel
{
    public static final String COLUMN_FIELD = "Field";

    public static final String COLUMN_QUALIFIER = "Qualifier";

    public static final String COLUMN_VALUE = "Value";

    private static final String[] COLUMN_NAMES = {COLUMN_FIELD, COLUMN_QUALIFIER, COLUMN_VALUE};

    private FilteredProduct filteredProduct;

    /**
     * Candidate Base Area Values will be filtered by ProductStructure and AreaGroup(always = CASEBASE for this table).
     */
    private ProductStructure productStructure;

    private static String AREAGROUP = "CASEBASE";

    public CandidateBaseUnderwritingTableModel(AppReqBlock appReqBlock, String filteredProductPK)
    {
        super(appReqBlock);

        getColumnNames().addAll(Arrays.asList(COLUMN_NAMES));

        if (filteredProductPK != null)
        {
            setProductStructure(filteredProductPK);
        }
    }

    /**
     * Renders all the rows in the ProductStructure table [except] for those ProductStructures
     * that have already been assigned to the current Case via FilteredProduct.
     */
    protected void buildTableRows()
    {
        if (productStructure != null)
        {
            AreaKey[] areaKey = AreaKey.findBy_Grouping(AREAGROUP);
            areaKey = (AreaKey[]) Util.sortObjects(areaKey, new String [] {"getField"});

            if (areaKey != null)
            {
                for (int i = 0; i < areaKey.length; i++)
                {
                    AreaValue[] areaValues = AreaValue.findBy_AreaKeyPK(areaKey[i].getPK());

                    if (areaValues != null)
                    {
                        for (int j = 0; j < areaValues.length; j++)
                        {
                            FilteredAreaValue filteredAreaValue = FilteredAreaValue.findBy_ProductStructurePK_AreaValuePK(productStructure.getPK(), areaValues[j].getPK());
                            if (filteredAreaValue != null)
                            {
                                CandidateBaseUnderwritingTableRow tableRow = new CandidateBaseUnderwritingTableRow(areaValues[j]);

                                super.addRow(tableRow);
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * @see #productStructure
     * @param filteredProductPK - the user selected product for the active case
     */
    public void setProductStructure(String filteredProductPK)
    {
        this.filteredProduct = FilteredProduct.findByPK(new Long(filteredProductPK));

        this.productStructure = filteredProduct.getProductStructure();
    }
}
