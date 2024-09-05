package edit.portal.widget;

import edit.portal.widgettoolkit.TableRow;
import group.CaseProductUnderwriting;
import group.DepartmentLocation;

/**
 * Created by IntelliJ IDEA.
 * User: dlataille
 * Date: Jul 24, 2007
 * Time: 12:55:47 PM
 * To change this template use File | Settings | File Templates.
 */
public class OtherUnderwritingTableRow extends TableRow
{
    private CaseProductUnderwriting caseProductUnderwriting;

    public OtherUnderwritingTableRow(CaseProductUnderwriting caseProductUnderwriting)
    {
        this.caseProductUnderwriting = caseProductUnderwriting;

        populateCellValues();
    }

    /**
     * Builds the Map of cell values with cell name/value pairs.
     */
    private void populateCellValues()
    {
        getCellValues().put(OtherUnderwritingTableModel.COLUMN_FIELD, caseProductUnderwriting.getField());

        getCellValues().put(OtherUnderwritingTableModel.COLUMN_QUALIFIER, caseProductUnderwriting.getQualifier());

        getCellValues().put(OtherUnderwritingTableModel.COLUMN_VALUE, caseProductUnderwriting.getValue());
    }

    public String getRowId()
    {
        return caseProductUnderwriting.getCaseProductUnderwritingPK().toString();
    }

    /**
     * Getter.
     * @return
     */
    public CaseProductUnderwriting getCaseProductUnderwriting()
    {
        return caseProductUnderwriting;
    }
}
