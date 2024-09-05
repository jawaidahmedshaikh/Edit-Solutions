/*
 * User: dlataill
 * Date: Jun 5, 2006
 * Time: 3:32:15 PM
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */

package edit.portal.widget;

import edit.portal.widgettoolkit.TableRow;
import edit.common.EDITDate;

import java.util.Map;
import java.util.HashMap;

import contract.Requirement;
import agent.AgentRequirement;
import fission.utility.*;

public class AgentRequirementsTableRow extends TableRow
{
    private Map columnValues;

    private String rowStatus = TableRow.ROW_STATUS_DEFAULT;

    private Requirement requirement;

    private AgentRequirement agentRequirement;

    public AgentRequirementsTableRow(Requirement requirement, AgentRequirement agentRequirement)
    {
        this.requirement = requirement;

        this.agentRequirement = agentRequirement;

        columnValues = new HashMap();

        mapColumnValues();
    }

    /**
     * Maps the values of requirement and caseRequirement to the TableRow.
     */
    private void mapColumnValues()
    {
        columnValues.put(RequirementsTableModel.COLUMN_REQUIREMENT_ID, requirement.getRequirementId());

        columnValues.put(RequirementsTableModel.COLUMN_REQUIREMNT_DESC, requirement.getRequirementDescription() + "&nbsp;");

        String requirementStatus = "";

        EDITDate receivedDate = null;

        EDITDate effectiveDate = null;

        if (agentRequirement != null)
        {
            requirementStatus = agentRequirement.getRequirementStatusCT();

            effectiveDate = agentRequirement.getEffectiveDate();

            receivedDate = agentRequirement.getReceivedDate();
        }

        columnValues.put(RequirementsTableModel.COLUMN_STATUS, requirementStatus);

        columnValues.put(RequirementsTableModel.COLUMN_RECEIVED_DATE, receivedDate == null ? "" : DateTimeUtil.formatEDITDateAsMMDDYYYY(receivedDate));

        columnValues.put(RequirementsTableModel.COLUMN_EFFECTIVE_DATE, effectiveDate == null ? "" : DateTimeUtil.formatEDITDateAsMMDDYYYY(effectiveDate));
    }

    /**
     * @see edit.portal.widgettoolkit.TableRow#getRowId()
     * @return
     */
    public String getRowId()
    {
        return agentRequirement.getAgentRequirementPK().toString();
    }

    /**
     * The cell value for the cell column.
     * @param columnName
     * @return
     */
    public Object getCellValue(String columnName)
    {
        return columnValues.get(columnName);
    }

    /**
     * @see edit.portal.widgettoolkit.TableRow#setRowStatus(String)
     */
    public void setRowStatus(String rowStatus)
    {
        this.rowStatus = rowStatus;
    }

    /**
     * @see edit.portal.widgettoolkit.TableRow#getRowStatus()
     * @return
     */
    public String getRowStatus()
    {
        return rowStatus;
    }
}