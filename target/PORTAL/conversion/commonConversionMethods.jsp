<!--
 * User: sdorman
 * Date: Nov 15, 2006
 * Time: 12:29:02 PM
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement. 
 -->
 
 <%@page import="java.sql.*,
                 java.io.*" %>

 <%!
     /**
      * Convenience method - Displays any successes from the conversion.
      *
      * @param out
      * @param message
      * @throws Exception
      */
     public void showSuccess(Writer out, String message) throws Exception
     {
         out.write("<span style='background-color:lightskyblue; width:100%'>");
 
         out.write("<hr>");
 
         out.write("The conversion was successful in completing the following:<br><br>");
 
         out.write("<font face='' color='blue'>");
 
         out.write(message);
 
         out.write("</font>");
 
         out.write("<hr>");
 
         out.write("</span>");
     }
 
 
     /**
      * Convenience method - Displays any errors from the conversion.
      *
      * @param out
      * @param e
      * @throws Exception
      */
     public void showError(Writer out, Exception e) throws Exception
     {
         out.write("<span style='background-color:yellow; width:100%'>");
 
         out.write("<hr>");
 
         out.write("The conversion was aborted for the following reason(s):<br><br>");
 
         out.write("<font face='' color='red'>");
 
         out.write(e.getMessage());
 
         out.write("</font>");
 
         out.write("<hr>");
 
         out.write("</span>");
     }

     /**
      * Drops a database table with the specified name.  The table name must be the fully qualified name.
      * Ex. dbo.SuspenseClientInformation (SQL Server) or EDITSOLUTIONS.SuspenseClientInformation (Oracle)
      *
      * @param tableName
      * @param out
      * @param conn
      * @return
      * @throws Exception
      */
     public boolean dropDatabaseTable(String tableName, Writer out, Connection conn)  throws Exception
     {
         String removeSuspenseClientInformation = "DROP TABLE " + tableName;
 
         Statement st = null;
 
         try
         {
             st = conn.createStatement();
 
             st.addBatch(removeSuspenseClientInformation);
 
             attempting(out, removeSuspenseClientInformation);
 
             st.executeBatch();
         }
         finally
         {
             if (st != null) st.close();
         }
 
         return true;
     }

     /**
      * Drops a single database field with the given fieldName and tableName
      *
      * @param tableName
      * @param fieldName
      * @param out
      * @param conn
      * @return
      * @throws Exception
      */
     public boolean dropDatabaseField(String tableName, String fieldName, Writer out, Connection conn)  throws Exception
     {
         String drop = "ALTER TABLE " + tableName + " DROP COLUMN " + fieldName;

         Statement s = null;

         try
         {
             s = conn.createStatement();

             s.addBatch(drop);
             attempting(out, drop);

             s.executeBatch();
         }
         finally
         {
             if (s != null) s.close();
         }

         return true;
     }


     /**
      * Convenience method to display conversion status.
      *
      * @param out
      * @param message
      * @throws Exception
      */
     private void attempting(Writer out, String message) throws Exception
     {
         out.write("<font face='' color='blue'>");
 
         out.write("Attempting...<br>");
 
         out.write("</font>");
 
         out.write(message);
 
         out.write("<br><br>");
     }

     /**
      * Establishes a JDBC Connection from the specified JDBC parameters.
      *
      * @param request
      * @return
      * @throws Exception
      */
     private Connection getConnection(HttpServletRequest request) throws Exception
     {
         String driverClassName = request.getParameter("driverClassName");
 
         String url = request.getParameter("url");
 
         String schemaName = request.getParameter("schemaName");
 
         String username = request.getParameter("username");
 
         String password = request.getParameter("password");
 
         Class.forName(driverClassName);
 
         Connection conn = DriverManager.getConnection(url, username, password);
 
         return conn;
     }
 
 %>


