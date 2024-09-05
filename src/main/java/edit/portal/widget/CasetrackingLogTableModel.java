/*
 * User: sprasad
 * Date: Jun 24, 2005
 * Time: 1:04:34 PM
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package edit.portal.widget;

import casetracking.CasetrackingLog;
import client.ClientDetail;
import edit.portal.widgettoolkit.TableModel;
import edit.portal.widgettoolkit.TableRow;
import fission.global.AppReqBlock;

import fission.global.AppReqBlock;
import client.ClientDetail;
import casetracking.CasetrackingLog;

import java.util.*;


public class CasetrackingLogTableModel extends TableModel
{
    public static final String COLUMN_PROCESS = "Process";
    public static final String COLUMN_TRXTYPE = "Trx Type";
    public static final String COLUMN_POLICY_NUMBER = "Policy #";
    public static final String COLUMN_EFF_DATE = "Effective Date";
    public static final String COLUMN_PROCESS_DATE = "Process Date";
    public static final String COLUMN_NAME = "Name";
    public static final String COLUMN_ALLOC_PERCENT = "Allocation %";
    public static final String COLUMN_NEW_POLICY_NUMBER = "New Pol #";

    private static final String[] COLUMN_NAMES = {COLUMN_PROCESS, COLUMN_TRXTYPE, COLUMN_POLICY_NUMBER, COLUMN_EFF_DATE,
                                                  COLUMN_PROCESS_DATE, COLUMN_NAME, COLUMN_ALLOC_PERCENT, COLUMN_NEW_POLICY_NUMBER};

    private ClientDetail clientDetail;



    public CasetrackingLogTableModel(ClientDetail clientDetail, AppReqBlock appReqBlock)
    {
        super(appReqBlock);

        getColumnNames().addAll(Arrays.asList(COLUMN_NAMES));

        this.clientDetail = clientDetail;
    }


    /**
     * Builds the superset TableModel
     */
    protected void buildTableRows()
    {
        CasetrackingLog[] casetrackingLogs = CasetrackingLog.findBy_ClientDetail(clientDetail);

        for (int i = 0; i < casetrackingLogs.length; i++)
        {
            TableRow tableRow = new CasetrackingLogTableRow(casetrackingLogs[i]);

            if (tableRow.getRowId().equals(this.getSelectedRowId()))
            {
                tableRow.setRowStatus(TableRow.ROW_STATUS_SELECTED);
            }

            super.addRow(tableRow);
        }
    }
}
