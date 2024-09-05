package edit.portal.widget;

import edit.portal.widgettoolkit.TableRow;
import group.CaseProductUnderwriting;
import group.DepartmentLocation;

/**
 * Created by IntelliJ IDEA.
 * User: dlataille
 * Date: Jul 24, 2007
 * Time: 11:58:47 AM
 * To change this template use File | Settings | File Templates.
 */
public class RiderUnderwritingTableRow extends TableRow
{
    private CaseProductUnderwriting caseProductUnderwriting;

    public RiderUnderwritingTableRow(CaseProductUnderwriting caseProductUnderwriting)
    {
        this.caseProductUnderwriting = caseProductUnderwriting;

        populateCellValues();
    }

    /**
     * Builds the Map of cell values with cell name/value pairs.
     */
    private void populateCellValues()
    {
        getCellValues().put(RiderUnderwritingTableModel.COLUMN_FIELD, caseProductUnderwriting.getField());

        getCellValues().put(RiderUnderwritingTableModel.COLUMN_QUALIFIER, caseProductUnderwriting.getQualifier());

        getCellValues().put(RiderUnderwritingTableModel.COLUMN_VALUE, caseProductUnderwriting.getValue());

        getCellValues().put(RiderUnderwritingTableModel.COLUMN_REL_TO_EE, caseProductUnderwriting.getRelationshipToEmployeeCT());

        if (caseProductUnderwriting.getDepartmentLocationFK() != null)
        {
            DepartmentLocation departmentLocation = DepartmentLocation.findBy_DepartmentLocationPK(caseProductUnderwriting.getDepartmentLocationFK());

            getCellValues().put(RiderUnderwritingTableModel.COLUMN_DEPT_LOC, departmentLocation.getDeptLocCode());
        }

        if (caseProductUnderwriting.getRequiredOptionalCT() != null)
        {
            getCellValues().put(RiderUnderwritingTableModel.COLUMN_INCL_OPT, caseProductUnderwriting.getRequiredOptionalCT());
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
