package edit.portal.widget;

import edit.portal.widgettoolkit.TableRow;
import engine.*;

/**
 * Created by IntelliJ IDEA.
 * User: dlataille
 * Date: Jul 24, 2007
 * Time: 11:57:47 AM
 * To change this template use File | Settings | File Templates.
 */
public class CandidateOtherUnderwritingTableRow extends TableRow
{
    private AreaValue areaValue;

    public CandidateOtherUnderwritingTableRow(AreaValue areaValue)
    {
        this.areaValue = areaValue;

        populateCellValues();
    }

    /**
     * Builds the Map of cell values with cell name/value pairs.
     */
    private void populateCellValues()
    {
        getCellValues().put(CandidateOtherUnderwritingTableModel.COLUMN_FIELD, areaValue.getAreaKey().getField());

        getCellValues().put(CandidateOtherUnderwritingTableModel.COLUMN_QUALIFIER, areaValue.getQualifierCT());

        getCellValues().put(CandidateOtherUnderwritingTableModel.COLUMN_VALUE, areaValue.getAreaValue());
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