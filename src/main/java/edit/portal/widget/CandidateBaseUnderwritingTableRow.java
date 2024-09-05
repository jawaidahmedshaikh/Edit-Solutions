package edit.portal.widget;

import edit.portal.widgettoolkit.TableRow;
import engine.*;

/**
 * Created by IntelliJ IDEA.
 * User: dlataille
 * Date: Jul 23, 2007
 * Time: 3:14:47 PM
 * To change this template use File | Settings | File Templates.
 */
public class CandidateBaseUnderwritingTableRow extends TableRow
{
    private AreaValue areaValue;

    public CandidateBaseUnderwritingTableRow(AreaValue areaValue)
    {
        this.areaValue = areaValue;

        populateCellValues();
    }

    /**
     * Builds the Map of cell values with cell name/value pairs.
     */
    private void populateCellValues()
    {
        getCellValues().put(CandidateBaseUnderwritingTableModel.COLUMN_FIELD, areaValue.getAreaKey().getField());

        getCellValues().put(CandidateBaseUnderwritingTableModel.COLUMN_QUALIFIER, areaValue.getQualifierCT());

        getCellValues().put(CandidateBaseUnderwritingTableModel.COLUMN_VALUE, areaValue.getAreaValue());
    }

    public String getRowId()
    {
        return areaValue.getAreaValuePK().toString();
    }

    /**
     * Getter.
     * @return
     */
    public AreaValue getAreaValue()
    {
        return areaValue;
    }
}
