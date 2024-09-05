/*
 * User: sprasad
 * Date: Jun 10, 2005
 * Time: 4:02:00 PM
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package edit.portal.widget;

import casetracking.CasetrackingNote;
import client.ClientDetail;
import edit.portal.widgettoolkit.TableModel;
import edit.portal.widgettoolkit.TableRow;
import fission.global.AppReqBlock;
import fission.utility.Util;

import java.util.ArrayList;
import java.util.*;



public class CasetrackingNoteTableModel extends TableModel
{
    public static final String COLUMN_TYPE = "Type";
    public static final String COLUMN_SEQUENCE = "Sequence";
    public static final String COLUMN_QUALIFIER = "Qualifier";
    public static final String COLUMN_NOTES = "Notes";

    private static final String[] COLUMN_NAMES = {COLUMN_TYPE, COLUMN_SEQUENCE, COLUMN_QUALIFIER, COLUMN_NOTES};

    private ClientDetail clientDetail;


    public CasetrackingNoteTableModel(Long clientDetailPK, AppReqBlock appReqBlock)
    {
        super(appReqBlock);

        getColumnNames().addAll(Arrays.asList(COLUMN_NAMES));

        this.clientDetail = ClientDetail.findByPK(clientDetailPK);
    }

    /**
     * Builds the superset TableModel
     */
    protected void buildTableRows()
    {
        Set casetrackingNotesSet = clientDetail.getCasetrackingNotes();

        CasetrackingNote[] casetrackingNotes = (CasetrackingNote[]) casetrackingNotesSet.toArray(new CasetrackingNote[casetrackingNotesSet.size()]);

        casetrackingNotes = (CasetrackingNote[]) Util.sortObjects(casetrackingNotes, new String [] {"getCasetrackingNotePK"});

        for (int i = 0; i < casetrackingNotes.length; i++)
        {
            TableRow casetrackingNoteTableRow = new CasetrackingNoteTableRow(casetrackingNotes[i]);

            if (casetrackingNoteTableRow.getRowId().equals(this.getSelectedRowId()))
            {
                casetrackingNoteTableRow.setRowStatus(TableRow.ROW_STATUS_SELECTED);
            }

            super.addRow(casetrackingNoteTableRow);
        }
    }
}
