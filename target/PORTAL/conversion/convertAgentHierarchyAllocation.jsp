<!--
 * User: sdorman
 * Date: Nov 15, 2006
 * Time: 12:29:02 PM
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
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
                 agent.*" %>

 <%@ include file="commonConversionMethods.jsp" %>

<%--
The allocationPercent is being moved from the AgentHierachy to the new AgentHierarchyAllocation.  Since the code for
the AgentHierarchy entity and VO have already been changed to no longer have the allocationPercent, we can not use
CRUD or Hibernate to get AgentHierarchy table information.  Straight SQL must be used.  In addition, Hibernate does
not allow us to set an FK field on a Hibernate entity (it wants object relationships) which means that we can not use
Hibernate for AgentHierarchyAllocation (can not set agentHierarchyFK).  We must use straight SQL to save the
AgentHierarchyAllocation.
--%>
 <%
     String agentHierarchyTableName = "AgentHierarchy";
     String agentHierarchyAllocationTableName = "AgentHierarchyAllocation";
     String allocationPercentFieldName = "AllocationPercent";

     // Get fully qualified table and column names for query
     // AgentHierarchy table
     DBTable agentHierarchyDBTable = DBTable.getDBTableForTable(agentHierarchyTableName);
     String agentHierarchyFullyQualifiedTableName = agentHierarchyDBTable.getFullyQualifiedTableName();
     String allocationPercentCol = agentHierarchyDBTable.getDBColumn(allocationPercentFieldName).getFullyQualifiedColumnName();
     String segmentFKCol = agentHierarchyDBTable.getDBColumn("SegmentFK").getFullyQualifiedColumnName();
     String agentFKCol = agentHierarchyDBTable.getDBColumn("AgentFK").getFullyQualifiedColumnName();

     // Segment table
     DBTable segmentDBTable = DBTable.getDBTableForTable("Segment");
     String segmentFullyQualifiedTableName = segmentDBTable.getFullyQualifiedTableName();
     String effectiveDateCol = segmentDBTable.getDBColumn("EffectiveDate").getFullyQualifiedColumnName();
     String contractNumberCol = segmentDBTable.getDBColumn("ContractNumber").getFullyQualifiedColumnName();
     String segmentPKCol = segmentDBTable.getDBColumn("SegmentPK").getFullyQualifiedColumnName();

     // Agent table
     DBTable agentDBTable = DBTable.getDBTableForTable("Agent");
     String agentFullyQualifiedTableName = agentDBTable.getFullyQualifiedTableName();
     String agentNumberCol = agentDBTable.getDBColumn("AgentNumber").getFullyQualifiedColumnName();
     String agentPKCol = agentDBTable.getDBColumn("AgentPK").getFullyQualifiedColumnName();


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

             String sql = "SELECT " + agentHierarchyFullyQualifiedTableName + ".*, " + effectiveDateCol + " AS EFFECTIVEDATE, " + contractNumberCol + " AS CONTRACTNUMBER, " + agentNumberCol + " AS AGENTNUMBER" +
                          " FROM " + agentHierarchyFullyQualifiedTableName +
                          " INNER JOIN " + segmentFullyQualifiedTableName + " ON " + segmentFKCol + " = " + segmentPKCol +
                          " INNER JOIN " + agentFullyQualifiedTableName + " ON " + agentFKCol + " = " + agentPKCol +
                          " ORDER BY " + agentFKCol + ", " + segmentFKCol;

             ResultSet rs = s.executeQuery(sql);

             EDITBigDecimal zero  = new EDITBigDecimal(EDITBigDecimal.EDIT_DECIMAL_ZILCH_STR);
             EDITBigDecimal one   = new EDITBigDecimal("1");
             EDITDate april202005 = new EDITDate(2005, 4, 20);
             EDITDate april192005 = new EDITDate(2005, 4, 19);
             EDITDate march152005 = new EDITDate(2005, 3, 15);
             EDITDate jan162006   = new EDITDate(2006, 1, 16);
             EDITDate jan172006   = new EDITDate(2006, 1, 17);

             while (rs.next())
             {
                  Long agentHierarchyPK = new Long(rs.getLong("AgentHierarchyPK"));
                  long segmentFK = rs.getLong("SegmentFK");
                  long agentFK = rs.getLong("AgentFK");
                  String agentNumber = rs.getString("AGENTNUMBER");
                  String contractNumber = rs.getString("CONTRACTNUMBER");
                  EDITBigDecimal allocationPercent = new EDITBigDecimal(rs.getBigDecimal("AllocationPercent"));
                  String effectiveDateString = DBUtil.readAndConvertDate(rs, "EFFECTIVEDATE");

                  EDITDate effectiveDate = null;

                  if (effectiveDateString == null)
                  {
                      // Contract is probably still pending so the effective date doesn't exist yet, default to today's date for the start date
                      // The start date will get overwritten when the contract is issued
                      effectiveDate = new EDITDate();
                  }
                  else
                  {
                      effectiveDate = new EDITDate(effectiveDateString);
                  }

                  EDITDate maxDate = new EDITDate(EDITDate.DEFAULT_MAX_DATE);

                  if (agentNumber.equals("40014") && contractNumber.equals("IR02000001") && allocationPercent.isEQ(zero))
                  {
                      saveAgentHierarchyAllocation(conn, agentHierarchyAllocationTableName, agentHierarchyPK, effectiveDate, march152005, one);
                  }
                  else if (agentNumber.equals("40015") && contractNumber.equals("IR02000001") && allocationPercent.isEQ(zero))
                  {
                      saveAgentHierarchyAllocation(conn, agentHierarchyAllocationTableName, agentHierarchyPK, effectiveDate, jan162006, one);
                  }
                  else if (agentNumber.equals("40016") && contractNumber.equals("IR02000001"))
                  {
                      //  This is the active agent on this contract since 40014 and 40015 have been inactivated
                      saveAgentHierarchyAllocation(conn, agentHierarchyAllocationTableName, agentHierarchyPK, jan172006, maxDate, allocationPercent);
                  }
                  else if (agentNumber.equals("00629") && allocationPercent.isEQ(zero) && contractIsFromAgent00629(contractNumber))
                  {
                      saveAgentHierarchyAllocation(conn, agentHierarchyAllocationTableName, agentHierarchyPK, effectiveDate, april192005, one);
                  }
                  else if (agentNumber.equals("40010"))
                  {
                        if (countPerAgentAndContract(conn, agentHierarchyFullyQualifiedTableName, agentFKCol, segmentFKCol, agentFK, segmentFK) > 1)
                        {
                            if (allocationPercent.isGT(zero))
                            {
                                saveAgentHierarchyAllocation(conn, agentHierarchyAllocationTableName, agentHierarchyPK, april202005, maxDate, allocationPercent );
                            }
                            else if (allocationPercent.isEQ(zero))
                            {
                                saveAgentHierarchyAllocation(conn, agentHierarchyAllocationTableName, agentHierarchyPK, effectiveDate, april192005, one);
//                                saveAgentHierarchyAllocation(conn, agentHierarchyAllocationTableName, agentHierarchyPK, april202005, maxDate, zero);
                            }
                        }
                        else if (agentNumber.equals("40010") && contractIsFromAgent00629(contractNumber))
                        {
                            //  This is the active agent on this contract since 00629 was inactivated
                            saveAgentHierarchyAllocation(conn, agentHierarchyAllocationTableName, agentHierarchyPK, april202005, maxDate, allocationPercent);
                        }
                        else
                        {
                            //  Do the standard stuff
                            saveAgentHierarchyAllocation(conn, agentHierarchyAllocationTableName, agentHierarchyPK, effectiveDate, maxDate, allocationPercent);
                        }
                  }
                  else
                  {
                      saveAgentHierarchyAllocation(conn, agentHierarchyAllocationTableName, agentHierarchyPK, effectiveDate, maxDate, allocationPercent);
                  }

                  conn.commit();
                  transactionCount++;

              }  // end while

              showSuccess(out, ("Number of " + agentHierarchyAllocationTableName + " records processed: " + transactionCount));
              s.close();
              rs.close();

              if (dropDatabaseField(agentHierarchyFullyQualifiedTableName, DBUtil.quote(allocationPercentFieldName), out, conn))
              {
                  showSuccess(out, "Dropped " + allocationPercentFieldName + " field from " + agentHierarchyTableName + " that was moved to " + agentHierarchyAllocationTableName);
              }
              else
              {
                  throw new Exception("No " + agentHierarchyTableName + " records found, job ended");
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
     /**
      * Determines how many entries there are for a given agentFK and segmentFK
      * @param conn
      * @param agentHierarchyTableName
      * @param agentFK
      * @param segmentFK
      * @return
      * @throws SQLException
      */
     private int countPerAgentAndContract(Connection conn, String agentHierarchyFullyQualifiedTableName, String agentFKCol,
                                          String segmentFKCol, long agentFK, long segmentFK) throws SQLException
     {
         int count = 0;

         Statement s = conn.createStatement();

         String sql = "SELECT COUNT(*) AS Count FROM " + agentHierarchyFullyQualifiedTableName +
                      " WHERE " + agentFKCol + " = " + agentFK +
                      " AND " + segmentFKCol + " = " + segmentFK +
                      " GROUP BY " + agentFKCol + ", " + segmentFKCol;

         ResultSet rs = s.executeQuery(sql);

         if (rs.next())
         {
             count = rs.getInt("Count");
         }

         return count;
     }

     /**
      * Creates the agentHierarchyAllocation with the specified settings
      *
      * @param agentHierarchyPK
      * @param startDate
      * @param stopDate
      * @param allocationPercent
      * @return
      */
     private AgentHierarchyAllocation createAgentHierarchyAllocation(Long agentHierarchyPK, EDITDate startDate, EDITDate stopDate,
                                                                             EDITBigDecimal allocationPercent)
     {
         AgentHierarchyAllocation agentHierarchyAllocation = new AgentHierarchyAllocation();

         agentHierarchyAllocation.setAgentHierarchyFK(agentHierarchyPK);
         agentHierarchyAllocation.setStartDate(startDate);
         agentHierarchyAllocation.setStopDate(stopDate);
         agentHierarchyAllocation.setAllocationPercent(allocationPercent);

         return agentHierarchyAllocation;
     }

     private void saveAgentHierarchyAllocation(Connection conn, String agentHierarchyAllocationTableName,
                                                  Long agentHierarchyPK, EDITDate startDate,
                                                  EDITDate stopDate, EDITBigDecimal allocationPercent) throws Exception
     {
        DBTable agentHierarchyAllocationDBTable = DBTable.getDBTableForTable(agentHierarchyAllocationTableName);
        String agentHierarchyAllocationFullyQualifiedTableName = agentHierarchyAllocationDBTable.getFullyQualifiedTableName();

        long agentHierarchyAllocationPK = CRUD.getNextAvailableKey();

        String sql = "INSERT INTO " + agentHierarchyAllocationFullyQualifiedTableName +
                     " VALUES (?, ?, ?, ?, ?)";

        PreparedStatement ps = null;

        try
        {
            ps = conn.prepareStatement(sql);

            ps.setLong(1, agentHierarchyAllocationPK);
            ps.setLong(2, agentHierarchyPK.longValue());
            ps.setDate(3, DBUtil.convertStringToDate(startDate.getFormattedDate()));
            ps.setDate(4, DBUtil.convertStringToDate(stopDate.getFormattedDate()));
            ps.setBigDecimal(5, allocationPercent.getBigDecimal());

            ps.execute();
        }
        catch(Exception e)
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

     /**
      * Determines if the contract number is one of the contracts that had agent 00629 on it with a zero allocationPercent
      * @param contractNumber
      * @return
      */
     private boolean contractIsFromAgent00629(String contractNumber)
     {
         boolean contractIsInRange = false;
         
         if (contractNumber.equals("IE05200001") ||
             contractNumber.equals("IE05300001") ||
             contractNumber.equals("IE05400001") ||
             contractNumber.equals("IE05500001") ||
             contractNumber.equals("IE05600001") ||
             contractNumber.equals("IE05700001") ||
             contractNumber.equals("IE05800001") ||
             contractNumber.equals("IE05900001"))
         {
            contractIsInRange = true;
         }

         return contractIsInRange;
     }
 %>


 <html>


     <head>
         <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
         <title>JSP Page</title>
     </head>
     <body>

         <h1>Move AllocationPercent from AgentHierarchy to AgentHierarchyAllocation.</h1>

         <p>
         This process will:
         <br>
         <br>1. Get all the AgentHierarchy rows.
         <br>2. For each of these rows:
         <br>&nbsp;&nbsp;&nbsp;a. If the AgentNumber is 40010
         <br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;If there is more than 1 AgentHierarchy for an Agent on a Contract
         <br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;If the allocationPercent is > 0
         <br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Creates an AgentHierarchyAllocation with the following settings:
         <br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;StartDate = 4/20/2005
         <br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;StopDate = 12/31/9999
         <br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;AllocationPercent = current allocationPercent from AgentHierarchy
         <br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;If the allocationPercent is = 0
         <br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Creates an AgentHierarchyAllocations with the following settings:
         <br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;StartDate = Segment.EffectiveDate
         <br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;StopDate = 4/19/2005
         <br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;AllocationPercent = 1
         <br>
<%--         <br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;StartDate = 4/20/2005--%>
<%--         <br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;StopDate = 12/31/9999--%>
<%--         <br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;AllocationPercent = 0--%>
         <br>&nbsp;&nbsp;&nbsp;b. If the ContractNumber is IR02000001
         <br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;If the AgentNumber is 40014 and the allocationPercent = 0
         <br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Creates an AgentHierarchyAllocations with the following settings:
         <br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;StartDate = Segment.EffectiveDate
         <br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;StopDate = 3/15/2005
         <br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;AllocationPercent = 1

         <br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;If the AgentNumber is 40015 and the allocationPercent = 0
         <br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Creates an AgentHierarchyAllocations with the following settings:
         <br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;StartDate = Segment.EffectiveDate
         <br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;StopDate = 1/16/2006
         <br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;AllocationPercent = 1
         <br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;If the AgentNumber is 40016, this is the active agent on this contract
         <br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Creates an AgentHierarchyAllocations with the following settings:
         <br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;StartDate = 1/17/2006
         <br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;StopDate = 12/31/9999
         <br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;AllocationPercent = allocationPercent from the AgentHierarchy

         <br>&nbsp;&nbsp;&nbsp;c. If the ContractNumber is IE05200001, IE05300001, IE05400001, IE05500001, IE05600001, IE05700001, IE05800001, or IE05900001
         <br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;If the AgentNumber is 00629 and the allocationPercent = 0
         <br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Creates an AgentHierarchyAllocations with the following settings:
         <br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;StartDate = Segment.EffectiveDate
         <br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;StopDate = 4/19/2005
         <br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;AllocationPercent = 1
         <br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;If the AgentNumber is 40010
         <br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Creates an AgentHierarchyAllocation with the following settings:
         <br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;StartDate = 4/20/2005
         <br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;StopDate = 12/31/9999
         <br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;AllocationPercent = allocationPercent from AgentHierarchy

         <br>&nbsp;&nbsp;&nbsp;d. Else Create an AgentHierarchyAllocation with a startDate of the contract effective date, a stop date of 12/31/9999, and an allocationPercent using the value from AgentHierarchy.
         <br>&nbsp;&nbsp;&nbsp;e. Insert the newly created AgentHierarchyAllocations into the database attached to the AgentHierarchy.
         <br>3. Drop the AllocationPercent column from the AgentHierarchy table in the database.
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

