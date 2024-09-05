/*
 * User: sprasad
 * Date: Jun 10, 2005
 * Time: 4:02:14 PM
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package edit.portal.widget;

import edit.portal.widgettoolkit.TableRow;

import casetracking.CasetrackingNote;



public class CasetrackingNoteTableRow extends TableRow
{
    private CasetrackingNote casetrackingNote;

    public CasetrackingNoteTableRow(CasetrackingNote casetrackingNote)
    {
        super();

        this.casetrackingNote = casetrackingNote;

        populateCellValues();
    }

    /**
     * Maps the values of Fund to the TableRow.
     */
    private void populateCellValues()
    {
        getCellValues().put(CasetrackingNoteTableModel.COLUMN_TYPE, casetrackingNote.getNoteTypeCT());
        getCellValues().put(CasetrackingNoteTableModel.COLUMN_SEQUENCE, casetrackingNote.getSequence()+"");
        getCellValues().put(CasetrackingNoteTableModel.COLUMN_QUALIFIER, casetrackingNote.getNoteQualifierCT());
        getCellValues().put(CasetrackingNoteTableModel.COLUMN_NOTES, casetrackingNote.getNote());
    }

    /**
     * Getter.
     * @return
     */
    public String getRowId()
    {
        return casetrackingNote.getCasetrackingNotePK().toString();
    }
}