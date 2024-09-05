/*
 * User: cgleason
 * Date: Sep 20, 2006
 * Time: 11:32:48
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package edit.services.db;

import com.javaunderground.jdbc.*;

import java.util.Calendar;
import java.sql.*;

import edit.common.*;

/**
 * Specialized formatter to help debug PreparedStatements.  It is used by DebuggableStatement (from javaunderground lib)
 * to format the dates and times in the format expected for EDITSolutions.  The result is a string that can be
 * copy/pasted into a SQL analyzer.
 * <P>
 * The code originated from javaunderground's OracleSqlFormatter and was modified for our needs.
 */
public class EDITSolutionsSqlFormatter extends SqlFormatter{

  /**
   * Format of Oracle date: 'YYYY-MM-DD HH24:MI:SS.#'
   */
  final String ymd24="'YYYY-MM-DD HH24:MI:SS.#'";

  /**
   * Formats Calendar object into Oracle TO_DATE String.
   * @param cal Calendar to be formatted
   * @return formatted TO_DATE function
   */
  private String format(Calendar cal){
    return "TO_DATE('" + new java.sql.Timestamp(cal.getTime().getTime()) + "',"+ymd24+")";
  }

  /**
   * Formats Date object into Oracle TO_DATE String.
   * @param date Date to be formatted
   * @return formatted TO_DATE function
   */
  private String format(java.sql.Date date)
  {
      String dateString = date.toString();
      dateString = dateString.replaceAll("-", EDITDate.DATE_DELIMITER);

      return ("'" + dateString + "'");
//    return "TO_DATE('" + new java.sql.Timestamp(date.getTime()) + "',"+ymd24+")";
  }

  /**
   * Formats Time object into Oracle TO_DATE String.
   * @param time Time to be formatted
   * @return formatted TO_DATE function
   */
  private String format(java.sql.Time time){
    Calendar cal = Calendar.getInstance();
    cal.setTime(new java.util.Date(time.getTime()));
    return "TO_DATE('" + cal.get(Calendar.HOUR_OF_DAY) + ":" +
      cal.get(Calendar.MINUTE) + ":" + cal.get(Calendar.SECOND) + "." +
      cal.get(Calendar.MILLISECOND) + "','HH24:MI:SS.#')";
  }

  /**
   * Formats Timestamp object into Oracle TO_DATE String.
   * @param timestamp Timestamp to be formatted
   * @return formatted TO_DATE function
   */
  private String format(java.sql.Timestamp timestamp)
  {
      String timestampString = timestamp.toString();
      timestampString = timestampString.replaceAll("-", EDITDate.DATE_DELIMITER);

      return ("'" + timestampString + "'");

//    return "TO_DATE('" + timestamp.toString() + "',"+ymd24+")";
  }


  /**
   * Formats object to an Oracle specific formatted function.
   * @param o Object to be formatted.
   * @return formatted Oracle function or "NULL" if o is null.
   * @exception SqlException
   */
  public String format(Object o) throws SQLException{
    if (o == null)               return "NULL";
    if (o instanceof Calendar)   return format((Calendar)o);
    if (o instanceof Date)       return format((Date)o);
    if (o instanceof Time)       return format((Time)o);
    if (o instanceof Timestamp)  return format((Timestamp)o);
    //if object not in one of our overridden methods, send to super class
    return super.format(o);

  } }