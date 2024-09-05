/*
 * User: dlataille
 * Date: April 30, 2007
 * Time: 1:53:00 PM
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package edit.portal.widget;

import casetracking.CaseRequirement;
import casetracking.usecase.CasetrackingUseCase;
import casetracking.usecase.CasetrackingUseCaseImpl;
import client.ClientDetail;

import group.*;
import contract.FilteredRequirement;
import contract.Requirement;
import edit.portal.widgettoolkit.TableModel;
import edit.portal.widgettoolkit.TableRow;
import engine.ProductStructure;
import fission.global.AppReqBlock;

import fission.utility.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;



public class CaseNoteTableModel extends TableModel
{
    public static final String COLUMN_TYPE        = "Type";
    public static final String COLUMN_SEQUENCE    = "Sequence";
    public static final String COLUMN_QUALIFIER   = "Qualifier";
    public static final String COLUMN_NOTE        = "Notes";

    private static final String[] COLUMN_NAMES = {COLUMN_TYPE, COLUMN_SEQUENCE, COLUMN_QUALIFIER, COLUMN_NOTE};

    private ProductStructure productStructure = null;
    


    public CaseNoteTableModel(AppReqBlock appReqBlock)
    {
        super(appReqBlock);

        getColumnNames().addAll(Arrays.asList(COLUMN_NAMES));
    }

    /**
     * Builds the superset TableModel
     */
    protected void buildTableRows()
    {
        String activeCasePK = getAppReqBlock().getUserSession().getParameter("activeCasePK");
        
        String selectedRowId = Util.initString(getSelectedRowId(), "");
        
        if (activeCasePK != null)
        {
            ContractGroupNote[] contractGroupNotes = ContractGroupNote.findBy_ContractGroupPK(new Long(activeCasePK));
            
            for (ContractGroupNote contractGroupNote:contractGroupNotes)
            {
                CaseNoteTableRow row = new CaseNoteTableRow(contractGroupNote);  
                
                if (row.getRowId().equals(this.getSelectedRowId()))
                {
                    row.setRowStatus(TableRow.ROW_STATUS_SELECTED);
                }

                super.addRow(row);
            }
        }
    }
}
