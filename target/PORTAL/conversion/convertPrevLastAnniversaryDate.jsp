<!--
 * User: sdorman
 * Date: Mar 1, 2007
 * Time: 12:29:02 PM
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement. 
 -->
 
 <%@page contentType="text/html"%>
 <%@page pageEncoding="UTF-8"%>

 <%@page import="fission.utility.*, java.sql.*, edit.services.db.hibernate.*, java.io.*, edit.services.db.*, edit.common.*,
                 edit.common.vo.*,
                 java.util.*,
                 event.dm.dao.*,
                 event.*,
                 client.*,
                 contract.*,
                 agent.*,
                 java.sql.Date" %>

 <%@ include file="commonConversionMethods.jsp" %>

<%--
Moves the PrevLastAnniversaryDate from FinancialHistory to SegmentHistory.
Loops through all SegmentHistory rows.  If the PrevLastAnniversaryDate is null, gets the value from FinancialHistory.
Drops PrevLastAnniversaryDate column from FinancialHistory
--%>
 <%
     String financialHistoryTableName = "FinancialHistory";
     String segmentHistoryTableName = "SegmentHistory";
     String prevLastAnniversaryDateFieldName = "PrevLastAnniversaryDate";   // field name is the same on both tables
     String editTrxHistoryFKFieldName = "EDITTrxHistoryFK";     // field name is the same on both tables

     // Get fully qualified table and column names for query
     // FinancialHistory table
     DBTable financialHistoryDBTable = DBTable.getDBTableForTable(financialHistoryTableName);
     String financialHistoryFullyQualifiedTableName = financialHistoryDBTable.getFullyQualifiedTableName();
     String fhPrevLastAnniversaryDateCol = financialHistoryDBTable.getDBColumn(prevLastAnniversaryDateFieldName).getFullyQualifiedColumnName();
     String fhEditTrxHistoryFKCol = financialHistoryDBTable.getDBColumn(editTrxHistoryFKFieldName).getFullyQualifiedColumnName();

     // SegmentHistory table
     DBTable segmentHistoryDBTable = DBTable.getDBTableForTable(segmentHistoryTableName);
     String segmentHistoryFullyQualifiedTableName = segmentHistoryDBTable.getFullyQualifiedTableName();
     String shPrevLastAnniversaryDateCol = segmentHistoryDBTable.getDBColumn(prevLastAnniversaryDateFieldName).getFullyQualifiedColumnName();
     String shEditTrxHistoryFKCol = segmentHistoryDBTable.getDBColumn(editTrxHistoryFKFieldName).getFullyQualifiedColumnName();
     String segmentHistoryPKCol = segmentHistoryDBTable.getDBColumn("SegmentHistoryPK").getFullyQualifiedColumnName();


     String startProcess = Util.initString(request.getParameter("startProcess"), "false");
     int transactionCount = 0;

     if (startProcess.equals("true"))
     {
         Connection conn = null;

         try
         {
             conn = ConnectionFactory.getSingleton().getConnection(ConnectionFactory.EDITSOLUTIONS_POOL);
             conn.setAutoCommit(false);


             Statement s = conn.createStatement();

             String sql = "SELECT " + segmentHistoryFullyQualifiedTableName + ".*, " + financialHistoryFullyQualifiedTableName + ".*, " +
                                        fhPrevLastAnniversaryDateCol + " AS FHANNIVDATE" +
                          " FROM " + segmentHistoryFullyQualifiedTableName + ", " + financialHistoryFullyQualifiedTableName +
                          " WHERE " + shEditTrxHistoryFKCol + " = " + fhEditTrxHistoryFKCol +
                          " AND " + shPrevLastAnniversaryDateCol + " IS NULL";


             ResultSet rs = s.executeQuery(sql);

             while (rs.next())
             {
                 String segmentHistoryPK = rs.getString("SegmentHistoryPK");

                 Date fhPrevLastAnniversaryDate = rs.getDate("FHANNIVDATE");

                 String financialHistoryPK = rs.getString("FinancialHistoryPK");

                 updatePrevLastAnniversaryField(conn, segmentHistoryPK, fhPrevLastAnniversaryDate, segmentHistoryFullyQualifiedTableName, shPrevLastAnniversaryDateCol, segmentHistoryPKCol);

                 conn.commit();
                 transactionCount++;

              }  // end while

              showSuccess(out, ("Number of " + segmentHistoryTableName + " records processed: " + transactionCount));
              s.close();
              rs.close();

              if (dropDatabaseField(financialHistoryFullyQualifiedTableName, DBUtil.quote(prevLastAnniversaryDateFieldName), out, conn))
              {
                  showSuccess(out, "Dropped " + prevLastAnniversaryDateFieldName + " field from " + financialHistoryTableName + " that was moved to " + segmentHistoryTableName);
              }
              else
              {
                  throw new Exception("No " + financialHistoryTableName + " records found, job ended");
              }
         }
         catch (Exception e)
         {
             if (conn != null) conn.rollback();

             showError(out, e);
         }
         finally
         {
             if (conn != null) conn.commit();

             if (conn != null) conn.close();
         }
     }
 %>

 <%!
     private void updatePrevLastAnniversaryField(Connection conn, String segmentHistoryPK, Date fhPrevLastAnniversaryDate,
                         String segmentHistoryFullyQualifiedTableName, String shPrevLastAnniversaryDateCol,
                         String segmentHistoryPKCol) throws SQLException
     {
         String sql = " UPDATE " + segmentHistoryFullyQualifiedTableName +
                      " SET " + shPrevLastAnniversaryDateCol + " =  ?" +
                      " WHERE " + segmentHistoryPKCol + " = " + segmentHistoryPK;


         PreparedStatement ps = null;

         try
         {
             ps = conn.prepareStatement(sql);

             ps.setDate(1, fhPrevLastAnniversaryDate);
             ps.executeUpdate();
         }
         catch (SQLException e)
         {
             System.out.println(e);
             e.printStackTrace();
             throw e;
         }
         finally
         {
             if (ps != null) ps.close();
         }
     }


 %>


 <html>


     <head>
         <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
         <title>JSP Page</title>
     </head>
     <body>

         <h1>Move PrevLastAnniversaryDate from FinancialHistory to SegmentHistory.</h1>

         <p>
         This process will:
         <br>
         <br>1. Get all the SegmentHistory rows whose PrevLastAnniversaryDate is null.
         <br>2. For each of these rows, replace the PrevLastAnniversaryDate with the corresponding FinancialHistory's PrevLastAnniversaryDate
         <br>3. Drop the PrevLastAnniversaryDate column from the FinancialHistory table in the database.
         </p>
         <p>
         Please backup the EDITSOLUTIONS data base to be used for this process before starting, in case a restore is
         needed.  The processing is done under a single transaction, so the records are committed as each row is processed.
         If there are errors, the process is aborted, only the offending row will be rollbacked.  You are notified of the
         offending issue. The database will have to be restored to its prior state if an error occurs
         and the job rerun from the beginning.
         </p>

         <form name="theForm">
             <hr>
             <table border="0">
                 <thead>

                 </thead>
                 <tbody>
                     <tr>
                         <td colspan="2" align="center"><input type="submit" value="Start Process" name="btnStartProcess" onClick="theForm.startProcess.value='true'"/></td>
                     </tr>
                 </tbody>
             </table>

             <input type="hidden" name="startProcess" value="false" />

         </form>

     </body>
 </html>

