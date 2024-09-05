package edit.portal.widget;

import fission.global.*;
import fission.utility.*;
import edit.portal.widgettoolkit.*;
import edit.common.vo.*;

import java.util.*;
import java.sql.*;

import engine.unittest.*;
import engine.*;

/**
 * User: cgleason
 * Date: Mar 3, 2006
 * Time: 3:13:08 PM
 * <p/>
 * (c) 2000 - 2006 Systems Engineering Group, LLC.  All rights Reserved
 * Systems Engineering Group, LLC Propietary and confidential.  Any use
 * subject to the license agreement.
 */
public class ScriptExportSummaryTableModel extends TableModel
{

    public static final String COLUMN_SCRIPT_NAME   = "Script Name";
    public static final String COLUMN_SCRIPT_TYPE   = "Script Type";
    public static final String COLUMN_SCRIPT_STATUS = "Status";
    public static final String COLUMN_SCRIPT_LAST_MAINT_DATE = "Last Maint Date";
    public static final String COLUMN_SCRIPT_OPERATOR = "Operator";

    private String selectedScriptPK;
    private Connection conn;

    public ScriptExportSummaryTableModel(AppReqBlock appReqBlock)
    {
        super(appReqBlock);

        getColumnNames().add(COLUMN_SCRIPT_NAME);
        getColumnNames().add(COLUMN_SCRIPT_TYPE);
        getColumnNames().add(COLUMN_SCRIPT_STATUS);
        getColumnNames().add(COLUMN_SCRIPT_LAST_MAINT_DATE);
        getColumnNames().add(COLUMN_SCRIPT_OPERATOR);
    }

    protected void buildTableRows()
    {
        Script[] scripts = Script.findAll();
        //need to sort

        for (int i = 0; i < scripts.length; i++)
        {
            Script script = scripts[i];

            ScriptExportSummaryTableRow summaryTableRow = new ScriptExportSummaryTableRow(script);

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
