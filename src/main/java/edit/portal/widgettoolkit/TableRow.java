/*
 * User: gfrosti
 * Date: Mar 22, 2005
 * Time: 9:25:52 AM
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package edit.portal.widgettoolkit;

import java.util.*;


public abstract class TableRow
{
  public static final String ROW_STATUS_DEFAULT = "default";
  public static final String ROW_STATUS_SELECTED = "selected";
  public static final String ROW_STATUS_ASSOCIATED = "associated";
  
  public static final Long DUMMY_TABLEROW_PK = new Long(0);

  private String rowStatus = TableRow.ROW_STATUS_DEFAULT;

  private Map cellValues = new HashMap();
  


  public TableRow()
  {
  }

  /**
     * Returns the unique identifier for this row.
     *
     * @return  unique identifier for this row.
     */
  public abstract String getRowId();

  /**
     * Returns the value for the columnName with this TableRow.
     *
     * @param columnName        name of the column within the row
     *
     * @return  value of the column within the row
     */
  public Object getCellValue(String columnName)
  {
    return getCellValues().get(columnName);
  }

  /**
     * Returns the value for the columnName with this TableRow.
     *
     * @param columnName        name of the column within the row
     *
     * @return  value of the column within the row
     */
  public void setCellValue(String columnName, String value)
  {
    Map cellValues = getCellValues();

    if (cellValues.containsKey(columnName))
    {
      cellValues.remove(columnName);
    }

    cellValues.put(columnName, value);
  }

  /**
     * Setter.
     */
  public void setRowStatus(String rowStatus)
  {
    this.rowStatus = rowStatus;
  }

  /**
     * Getter.
     * @return
     */
  public String getRowStatus()
  {
    return rowStatus;
  }

  /**
     * The mapping of column_name/value pairings. A subclass of this class is expected to populate this Map as necessary.
     * For example:
     * getCellValues().put("ColumnName", "ColumnValue");
     * @return
     */
  public Map getCellValues()
  {
    return cellValues;
  }
  
  /**
   * True if this TableRow is currently selected.
   * @see #ROW_STATUS_SELECTED
   * @return
   */
  public boolean isSelected()
  {
    boolean isSelected = false;
    
    if (getRowStatus() != null && getRowStatus().equals(TableRow.ROW_STATUS_SELECTED))
    {
      isSelected = true;
    }
    
    return isSelected;
  }
}
