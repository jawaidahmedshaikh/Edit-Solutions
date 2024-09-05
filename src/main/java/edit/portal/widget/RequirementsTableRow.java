/*
 * User: sprasad
 * Date: Jun 2, 2005
 * Time: 12:54:15 PM
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package edit.portal.widget;

import edit.portal.widgettoolkit.TableRow;
import edit.common.EDITDate;

import contract.Requirement;
import casetracking.CaseRequirement;
import fission.utility.*;



public class RequirementsTableRow extends TableRow
{
    private Requirement requirement;

    private CaseRequirement caseRequirement;

    public RequirementsTableRow(Requirement requirement, CaseRequirement caseRequirement)
    {
        super();

        this.requirement = requirement;

        this.caseRequirement = caseRequirement;

        populateCellValues();
    }

    /**
     * Maps the values of requirement and caseRequirement to the TableRow.
     */
    private void populateCellValues()
    {
        getCellValues().put(RequirementsTableModel.COLUMN_REQUIREMENT_ID, requirement.getRequirementId());

        getCellValues().put(RequirementsTableModel.COLUMN_REQUIREMNT_DESC, requirement.getRequirementDescription() + "&nbsp;");

        String requirementStatus = "";

        EDITDate receivedDate = null;

        EDITDate effectiveDate = null;

        if (caseRequirement != null)
        {
            requirementStatus = caseRequirement.getRequirementStatusCT();

            effectiveDate = caseRequirement.getEffectiveDate();

            receivedDate = caseRequirement.getReceivedDate();
        }

        getCellValues().put(RequirementsTableModel.COLUMN_STATUS, requirementStatus);

        getCellValues().put(RequirementsTableModel.COLUMN_RECEIVED_DATE, receivedDate == null ? "" : DateTimeUtil.formatEDITDateAsMMDDYYYY(receivedDate));

        getCellValues().put(RequirementsTableModel.COLUMN_EFFECTIVE_DATE, effectiveDate == null ? "" : DateTimeUtil.formatEDITDateAsMMDDYYYY(effectiveDate));
    }

    /**
     * @see edit.portal.widgettoolkit.TableRow#getRowId()
     * @return
     */
    public String getRowId()
    {
        return caseRequirement.getCaseRequirementPK().toString();
    }
}