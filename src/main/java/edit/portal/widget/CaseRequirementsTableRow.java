/*
 * User: dlataille
 * Date: April 30, 2007
 * Time: 1:55:15 PM
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

import group.ContractGroupRequirement;

import fission.utility.*;
import group.*;



public class CaseRequirementsTableRow extends TableRow
{
    /**
     * The associated ContractGroupRequirement to render in the table row.
     */
    private ContractGroupRequirement contractGroupRequirement;

    public CaseRequirementsTableRow(ContractGroupRequirement contractGroupRequirement)
    {
        super();

        this.contractGroupRequirement = contractGroupRequirement;

        populateCellValues();
    }

    /**
     * Maps the values of requirement and caseRequirement to the TableRow.
     */
    private void populateCellValues()
    {
        getCellValues().put(CaseRequirementsTableModel.COLUMN_BATCH_ID, getContractGroupRequirement().getBatchContractSetup().getBatchID());

        getCellValues().put(CaseRequirementsTableModel.COLUMN_REQUIREMENT_ID, getContractGroupRequirement().getFilteredRequirement().getRequirement().getRequirementId());

        getCellValues().put(CaseRequirementsTableModel.COLUMN_REQUIREMNT_DESC, getContractGroupRequirement().getFilteredRequirement().getRequirement().getRequirementDescription());

        getCellValues().put(CaseRequirementsTableModel.COLUMN_STATUS, getContractGroupRequirement().getRequirementStatusCT());

        getCellValues().put(CaseRequirementsTableModel.COLUMN_PRODUCT_STRUCTURE, getContractGroupRequirement().getFilteredRequirement().getProductStructure().toString());

        String effectiveDate = DateTimeUtil.formatEDITDateAsMMDDYYYY(getContractGroupRequirement().getEffectiveDate());
        getCellValues().put(CaseRequirementsTableModel.COLUMN_EFFECTIVE_DATE, effectiveDate);
    }

    /**
     * @see edit.portal.widgettoolkit.TableRow#getRowId()
     * @return
     */
    public String getRowId()
    {
        return getContractGroupRequirement().getContractGroupRequirementPK().toString();
    }

    /**
     * @see #contractGroupRequirement
     * @return
     */
    public ContractGroupRequirement getContractGroupRequirement()
    {
        return contractGroupRequirement;
    }
}
