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



public class CaseNoteTableRow extends TableRow
{
    /**
     * The associated ContractGroupRequirement to render in the table row.
     */
    private ContractGroupNote contractGroupNote;

    public CaseNoteTableRow(ContractGroupNote contractGroupNote)
    {
        super();

        this.contractGroupNote = contractGroupNote;

        populateCellValues();
    }

    /**
     * Maps the values of requirement and caseRequirement to the TableRow.
     */
    private void populateCellValues()
    {
        getCellValues().put(CaseNoteTableModel.COLUMN_TYPE, getContractGroupNote().getNoteTypeCT());

        String sequence = (((Integer)getContractGroupNote().getSequence()).toString());
        getCellValues().put(CaseNoteTableModel.COLUMN_SEQUENCE, sequence);

        getCellValues().put(CaseNoteTableModel.COLUMN_QUALIFIER, getContractGroupNote().getNoteQualifierCT());

        String note = getContractGroupNote().getNote();
        if (note.length() > 20)
        {
            note = note.substring(0, 20);
        }
        getCellValues().put(CaseNoteTableModel.COLUMN_NOTE, note);

    }

    /**
     * @see edit.portal.widgettoolkit.TableRow#getRowId()
     * @return
     */
    public String getRowId()
    {
        return getContractGroupNote().getContractGroupNotePK().toString();
    }

    /**
     * @see #contractGroupRequirement
     * @return
     */
    public ContractGroupNote getContractGroupNote()
    {
        return contractGroupNote;
    }
}
