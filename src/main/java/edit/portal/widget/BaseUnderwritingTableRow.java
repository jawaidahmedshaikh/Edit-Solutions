package edit.portal.widget;

import edit.portal.widgettoolkit.TableRow;
import group.CaseProductUnderwriting;
import group.DepartmentLocation;

/**
 * Created by IntelliJ IDEA.
 * User: dlataille
 * Date: Jul 24, 2007
 * Time: 10:00:47 AM
 * To change this template use File | Settings | File Templates.
 */
public class BaseUnderwritingTableRow extends TableRow
{
    private CaseProductUnderwriting caseProductUnderwriting;

    public BaseUnderwritingTableRow(CaseProductUnderwriting caseProductUnderwriting)
    {
        this.caseProductUnderwriting = caseProductUnderwriting;

        populateCellValues();
    }

    /**
     * Builds the Map of cell values with cell name/value pairs.
     */
    private void populateCellValues()
    {
        getCellValues().put(BaseUnderwritingTableModel.COLUMN_FIELD, caseProductUnderwriting.getField());

        getCellValues().put(BaseUnderwritingTableModel.COLUMN_QUALIFIER, caseProductUnderwriting.getQualifier());

        getCellValues().put(BaseUnderwritingTableModel.COLUMN_VALUE, caseProductUnderwriting.getValue());

        getCellValues().put(BaseUnderwritingTableModel.COLUMN_REL_TO_EE, caseProductUnderwriting.getRelationshipToEmployeeCT());

        if (caseProductUnderwriting.getDepartmentLocationFK() != null)
        {
            DepartmentLocation departmentLocation = DepartmentLocation.findBy_DepartmentLocationPK(caseProductUnderwriting.getDepartmentLocationFK());

            getCellValues().put(BaseUnderwritingTableModel.COLUMN_DEPT_LOC, departmentLocation.getDeptLocCode());
        }
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
