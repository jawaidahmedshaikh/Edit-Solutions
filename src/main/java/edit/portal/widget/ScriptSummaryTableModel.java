package edit.portal.widget;

import fission.global.*;
import fission.utility.*;
import edit.portal.widgettoolkit.*;
import edit.common.vo.*;

import java.util.*;
import java.sql.*;

import engine.unittest.*;

/**
 * User: cgleason
 * Date: Mar 3, 2006
 * Time: 3:13:08 PM
 * <p/>
 * (c) 2000 - 2006 Systems Engineering Group, LLC.  All rights Reserved
 * Systems Engineering Group, LLC Propietary and confidential.  Any use
 * subject to the license agreement.
 */
public class ScriptSummaryTableModel extends TableModel
{

    public static final String COLUMN_SCRIPT_NAME   = "Script Name";
    public static final String COLUMN_SCRIPT_TYPE   = "Script Type";
    public static final String COLUMN_SCRIPT_STATUS = "Script Status";

    private String selectedScriptPK;
    private Connection conn;

    public ScriptSummaryTableModel(AppReqBlock appReqBlock, Connection conn)
    {
        super(appReqBlock);
        this.conn = conn;
        getColumnNames().add(COLUMN_SCRIPT_NAME);
    }

    protected void buildTableRows()
    {

        ScriptVO[] scriptVOs = new ScriptFastDAO().findAllScripts(conn);

        for (int i = 0; i < scriptVOs.length; i++)
        {
            ScriptVO scriptVO = scriptVOs[i];

            ScriptSummaryTableRow summaryTableRow = new ScriptSummaryTableRow(scriptVO);

            if (summaryTableRow.getRowId().equals(getSelectedScriptPK()))
            {
                summaryTableRow.setRowStatus(TableRow.ROW_STATUS_SELECTED);
            }

            getRows().add(summaryTableRow);
        }

        Collections.sort(getRows());
    }

    private String getSelectedScriptPK()
    {
        return Util.initString(selectedScriptPK, null);
    }

   public void setSelectedScriptPK(String scriptPK)
    {
        this.selectedScriptPK = scriptPK;
    }
}
