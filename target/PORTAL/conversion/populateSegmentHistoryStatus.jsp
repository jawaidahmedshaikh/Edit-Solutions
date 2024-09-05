<!--
 * User: cgleason
 * Date: Jan 9, 2008
 * Time: 12:29:02 PM
 *
 * (c) 2000-2008 Systems Engineering Group, LLC.  All Rights Reserved
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
     String segmentTableName = "Segment";
     String segmentHistoryTableName = "SegmentHistory";

     // Get fully qualified table and column names for query
     // FinancialHistory table
     DBTable segmentDBTable = DBTable.getDBTableForTable(segmentTableName);
     String segmentFullyQualifiedTableName = segmentDBTable.getFullyQualifiedTableName();
     String segmentStatusCTCol = segmentDBTable.getDBColumn("SegmentStatusCT").getFullyQualifiedColumnName();
     String segmentPKCol = segmentDBTable.getDBColumn("SegmentPK").getFullyQualifiedColumnName();

     // SegmentHistory table
     DBTable segmentHistoryDBTable = DBTable.getDBTableForTable(segmentHistoryTableName);
     String segmentHistoryFullyQualifiedTableName = segmentHistoryDBTable.getFullyQualifiedTableName();
     String prevSegemtnStatusCol = segmentHistoryDBTable.getDBColumn("PrevSegmentStatus").getFullyQualifiedColumnName();
     String segmentFKCol = segmentHistoryDBTable.getDBColumn("SegmentFK").getFullyQualifiedColumnName();
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

             String sql = "SELECT " + segmentFullyQualifiedTableName + ".*" +
                          " FROM " + segmentFullyQualifiedTableName;

             ResultSet rs = s.executeQuery(sql);

             while (rs.next())
             {
                 long segmentPK = rs.getLong("SegmentPK");
                 String segmentStatus = rs.getString("SegmentStatusCT");

                 SegmentHistory[] segmentHistories =  SegmentHistory.findBySegmentPK(new Long(segmentPK));

                 if (segmentHistories != null)
                 {
                     updateSegmentStatus(segmentHistories, segmentStatus);
                 }

//                 conn.commit();
                 transactionCount++;

              }  // end while

              showSuccess(out, ("Number of " + segmentTableName + " records processed: " + transactionCount));
              s.close();
              rs.close();

         }
         catch (Exception e)
         {
             if (conn != null) conn.rollback();
             SessionHelper.rollbackTransaction(SessionHelper.EDITSOLUTIONS);

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
 private void updateSegmentStatus(SegmentHistory[] segmentHistories, String segmentStatus)
 {
    SessionHelper.beginTransaction(SessionHelper.EDITSOLUTIONS);

    int j = segmentHistories.length - 1;

    SegmentHistory[] cloneSegHistories = segmentHistories.clone();
    for (int i = 0; i < segmentHistories.length; i++)
    {
        SegmentHistory segmentHistory = segmentHistories[i];
        if (j == i)
        {
            segmentHistory.setStatusCT(segmentStatus);
        }
        else
        {
//            int j = i + 1;
            SegmentHistory cloneSegHist = cloneSegHistories[i + 1];
            segmentHistory.setStatusCT(cloneSegHist.getPrevSegmentStatus());
        }

        SessionHelper.saveOrUpdate(segmentHistory, SessionHelper.EDITSOLUTIONS);
    }

     SessionHelper.commitTransaction(SessionHelper.EDITSOLUTIONS);
 }

 %>


 <html>


     <head>
         <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
         <title>JSP Page</title>
     </head>
     <body>

         <h1>Populate StatusCT on SegmentHistory.</h1>

         <p>
         This process will:
         <br>
         <br>1. Get all the Segment rows.
         <br>1. Get all the SegmentHistory rows whose PrevLastAnniversaryDate is null.
         <br>2. For each of these rows, get all the SegmentHistory processed for that Segment.
         <br>3. Update each of the SegmentHistory rows with the next PrevSegmentStatus with the last one being updated from
                the Segment Status.
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

