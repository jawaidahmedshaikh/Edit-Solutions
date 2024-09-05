package edit.portal.widget;

import edit.portal.widgettoolkit.*;
import edit.common.vo.*;
import edit.common.*;
import engine.*;

/**
 * User: cgleason
 * Date: Mar 3, 2006
 * Time: 3:28:19 PM
 * <p/>
 * (c) 2000 - 2006 Systems Engineering Group, LLC.  All rights Reserved
 * Systems Engineering Group, LLC Propietary and confidential.  Any use
 * subject to the license agreement.
 */
public class ScriptExportSummaryTableRow extends TableRow implements Comparable
{
    private String scriptName;
    private String scriptType;
    private String scriptStatus;
    private Long scriptPK;
    private EDITDateTime lastMaintenanceDate;
    private String operator;

    public ScriptExportSummaryTableRow(Script script)
    {
        populateRowData(script);
    }

   /**
     * Maps the script and its relative data to this TableRow.
     * @param script
     */
    private void populateRowData(Script script)
    {
        scriptName = script.getScriptName();

        scriptPK = script.getScriptPK();

        scriptType = script.getScriptTypeCT();

        scriptStatus = script.getScriptStatusCT();

        lastMaintenanceDate = script.getMaintDateTime();

        operator = script.getOperator();
    }

   /**
     * Getter.
     * @return
     */
    public String getRowId()
    {
        return scriptPK.toString();
    }

   /**
     * Getter.
     * @param columnName
     * @return
     */
    public Object getCellValue(String columnName)
    {
        String cellValue = null;

        if (columnName.equals(ScriptExportSummaryTableModel.COLUMN_SCRIPT_NAME))
        {
            cellValue = scriptName;
        }
        else if (columnName.equals(ScriptExportSummaryTableModel.COLUMN_SCRIPT_TYPE))
        {
            cellValue = scriptType;
        }
        else if (columnName.equals(ScriptExportSummaryTableModel.COLUMN_SCRIPT_STATUS))
        {
            cellValue = scriptStatus;
        }
        else if (columnName.equals(ScriptExportSummaryTableModel.COLUMN_SCRIPT_LAST_MAINT_DATE))
        {
            cellValue = lastMaintenanceDate.getFormattedDateTime();
        }
        else if (columnName.equals((ScriptExportSummaryTableModel.COLUMN_SCRIPT_OPERATOR)))
        {
            cellValue = operator;
        }

        return cellValue;
    }

   /**
    * getter
    * @return
    */
    public String getScriptName()
    {
        return scriptName;

    }


   /**
     * Compares by ScriptName.
     * @param o
     * @return
     */
    public int compareTo(Object o)
    {
        ScriptExportSummaryTableRow inScriptSummaryTableRow = (ScriptExportSummaryTableRow) o;

        String inScriptName = inScriptSummaryTableRow.getScriptName();

        String thisScriptName = getScriptName();

        return thisScriptName.compareTo(inScriptName);
    }
}
