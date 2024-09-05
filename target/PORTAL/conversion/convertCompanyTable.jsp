<!--
 * User: cgleason
 * Date: Apr 26, 2007
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
                 contract.*,
                 engine.*" %>

 <%@ include file="commonConversionMethods.jsp" %>

<%--
Moves the CompanyName from ProductStructure to Company.
Loops through all ProductStructure rows.  
--%>
 <%
     String companyTableName = "Company";
     String companyNameField = "CompanyName";

     // Get fully qualified table and column names for query

     // ProductStructure table
     DBTable productSructureDBTable = DBTable.getDBTableForTable("ProductStructure");
     String productStructureFullyQualifiedTableName = productSructureDBTable.getFullyQualifiedTableName();

     String startProcess = Util.initString(request.getParameter("startProcess"), "false");
     int transactionCount = 0;
     int companyCount = 0;

     if (startProcess.equals("true"))
     {
         Connection conn = null;

         try
         {
             conn = ConnectionFactory.getSingleton().getConnection(ConnectionFactory.ENGINE_POOL);
             conn.setAutoCommit(false);

             CRUD crud = CRUDFactory.getSingleton().getCRUD(ConnectionFactory.ENGINE_POOL);

             Statement s = conn.createStatement();

             String sql = "SELECT * FROM " + productStructureFullyQualifiedTableName;

             ResultSet rs = s.executeQuery(sql);

             while (rs.next())
             {
                 String companyName = rs.getString("CompanyName");
                 long productStructurePK = rs.getLong("ProductStructurePK");

                 CompanyVO existingCompanyVO = getCompanyName(companyName, crud);

                 long companyPK = 0;
                 if (existingCompanyVO == null)
                 {
                     CompanyVO companyVO = new CompanyVO();
                     companyVO.setCompanyPK(0);
                     companyVO.setCompanyName(companyName);

                     companyPK = crud.createOrUpdateVOInDB(companyVO);
                     companyCount++;
                 }
                 else
                 {
                     companyPK = existingCompanyVO.getCompanyPK();
                 }

                 updateProductStructure(conn, companyPK, productStructurePK);

                 conn.commit();
                 transactionCount++;


              }  // end while

              showSuccess(out, ("Number of ProductStructure records processed: " + transactionCount));
              showSuccess(out, ("Number of Company records created: ") + companyCount);
              s.close();
              rs.close();


              if (dropDatabaseField(productStructureFullyQualifiedTableName, DBUtil.quote(companyNameField), out, conn))
              {
                  showSuccess(out, "Dropped " + companyNameField + " field from ProductStructure that was moved to " + companyTableName);
              }
              else
              {
                  throw new Exception("No ProductStructure records found, job ended");
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
     private void updateProductStructure(Connection conn, long companyPK, long productStructurePK) throws SQLException
     {
         DBTable productStructureDBTable = DBTable.getDBTableForTable("ProductStructure");
         String productStructureFullyQualifiedTableName = productStructureDBTable.getFullyQualifiedTableName();
         String companyFKCol = productStructureDBTable.getDBColumn("CompanyFK").getFullyQualifiedColumnName();
         String productStructurePKCol = productStructureDBTable.getDBColumn("ProductStructurePK").getFullyQualifiedColumnName();


         String sql = " UPDATE " + productStructureFullyQualifiedTableName +
                      " SET " + companyFKCol + " =  ?" +
                      " WHERE " + productStructurePKCol + " =  ?";


         PreparedStatement ps = null;

         try
         {
             ps = conn.prepareStatement(sql);

             ps.setLong(1, companyPK);
             ps.setLong(2, productStructurePK);
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


     public CompanyVO getCompanyName(String companyName, CRUD crud) throws SQLException
     {
         DBTable companyDBTable = DBTable.getDBTableForTable("Company");
         String companyFullyQualifiedTableName = companyDBTable.getFullyQualifiedTableName();
         String companyPKCol = companyDBTable.getDBColumn("CompanyPK").getFullyQualifiedColumnName();
         String companyNameCol = companyDBTable.getDBColumn("CompanyName").getFullyQualifiedColumnName();


         String sql = " SELECT * FROM " + companyFullyQualifiedTableName +
                      " WHERE " + companyNameCol + " =  ?";

         Connection conn = crud.getCrudConn();
         PreparedStatement ps = null;
         ResultSet rs = null;
         CompanyVO companyVO = null;

         try
         {

             ps = conn.prepareStatement(sql);

             ps.setString(1, companyName);

             rs = ps.executeQuery();

             while (rs.next())
            {
                companyVO = new CompanyVO();
                companyVO.setCompanyPK(rs.getLong("CompanyPK"));
                companyVO.setCompanyName(rs.getString("CompanyName"));
            }
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

         return companyVO;
     }
 %>


 <html>


     <head>
         <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
         <title>JSP Page</title>
     </head>
     <body>

         <h1>Move CompanyName from ProductStructure to Company.</h1>

         <p>
         This process will:
         <br>
         <br>1. Get all the ProductStructure rows.
         <br>2. For each of these rows, create a Company record with the CompanyName of the ProductStructure, save it, update ProductStructure with the new CompanyPK.
         <br>3. Drop the CompanyName column from the ProductStructure table in the database.
         </p>
         <p>
         Please backup the ENGINE data base to be used for this process before starting, in case a restore is
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

