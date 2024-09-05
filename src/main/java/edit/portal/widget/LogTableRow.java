package edit.portal.widget;

import edit.portal.widgettoolkit.TableRow;

import fission.utility.Util;

import java.math.BigDecimal;

import logging.Log;

public class LogTableRow extends TableRow
{
  /**
   * The Log's PK.
   */
  Long logPK;

  /**
   * The Log's name.
   */
  String logName;
  
  /**
   * The Log's description;
   */
  String logDescription;
  
  /**
   * The Log's active status.
   */
  String active;
  
  /**
   * This (this) LogTableRows row position in the set of rows for this Log (0-indexed).
   */
  int rowNumber;


  public LogTableRow(Log log, int rowNumber)
  {
    this.logPK = log.getLogPK();
  
    this.logName = log.getLogName();
    
    this.logDescription = log.getLogDescription();
    
    this.active = log.getActive();
    
    this.rowNumber = rowNumber;
  }
  
  /**
   * For a manually generated Log entry (most likely to allow a "new" Log to be created).
   * @param logName
   * @param logDescription
   * @param active
   */
  public LogTableRow(String logName, String logDescription, String active)
  {
    this.logName = logName;
    
    this.logDescription = logDescription;
    
    this.active = active;
    
    this.logPK = TableRow.DUMMY_TABLEROW_PK;
    
    populateCellValues();
  }

  /**
   * The Log.LogPK
   * @return Log.LogPK
   */
  public String getRowId()
  {
    return getLogPK().toString();
  }

/**
 * Sets values for this LogTableRow for:
 * Log.LogName
 * Log.LogDescription
 * Log.Active.
 * @see Log#getLogName
 * @see Log#getLogDescription
 * @see Log#getActive
 */
  void populateCellValues()
  {
    super.getCellValues().put(LogTableModel.COLUMN_LOG_NAME, getLogName());
    
    super.getCellValues().put(LogTableModel.COLUMN_LOG_DESCRIPTION, getLogDescription());
    
    super.getCellValues().put(LogTableModel.COLUMN_ACTIVE, getActive());
  }


  public void setRowNumber(int rowNumber)
  {
    this.rowNumber = rowNumber;
  }

  public int getRowNumber()
  {
    return rowNumber;
  }

  public Long getLogPK()
  {
    return logPK;
  }

  public String getLogName()
  {
    return logName;
  }

  public String getLogDescription()
  {
    return logDescription;
  }

  public String getActive()
  {
    return active;
  }
}
