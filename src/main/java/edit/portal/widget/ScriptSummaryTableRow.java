package edit.portal.widget;

import edit.portal.widgettoolkit.*;
import edit.common.vo.*;
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
public class ScriptSummaryTableRow extends TableRow implements Comparable
{
    private String scriptName;
    private String scriptType;
    private String scriptStatus;
//    private String rowStatus;
    private Long scriptPK;

    public ScriptSummaryTableRow(ScriptVO script)
    {
        populateRowData(script);
    }

   /**
     * Maps the script and its relative data to this TableRow.
     * @param script
     */
    private void populateRowData(ScriptVO script)
    {
        scriptName = script.getScriptName();

        scriptPK = new Long(script.getScriptPK());

        scriptType = script.getScriptTypeCT();

        scriptStatus = script.getScriptStatusCT();
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

        if (columnName.equals(ScriptSummaryTableModel.COLUMN_SCRIPT_NAME))
        {
            cellValue = scriptName;
        }
        else if (columnName.equals(ScriptSummaryTableModel.COLUMN_SCRIPT_TYPE))
        {
            cellValue = scriptType;
        }
        else if (columnName.equals(ScriptSummaryTableModel.COLUMN_SCRIPT_STATUS))
        {
            cellValue = scriptStatus;
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
        ScriptSummaryTableRow inScriptSummaryTableRow = (ScriptSummaryTableRow) o;

        String inScriptName = inScriptSummaryTableRow.getScriptName();

        String thisScriptName = getScriptName();

        return thisScriptName.compareTo(inScriptName);
    }
}
