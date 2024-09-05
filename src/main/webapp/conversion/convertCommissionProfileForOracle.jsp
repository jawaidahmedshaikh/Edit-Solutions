<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>

<%@page import="fission.utility.*, java.sql.*, edit.services.db.hibernate.*, java.io.*, java.sql.*, edit.services.db.*, edit.common.*" %>

<%
    String startConversion = Util.initString(request.getParameter("startConversion"), "false");

    if (startConversion.equals("true"))
    {
        Connection conn = null;

        try
        {
            conn = getConnection(request);

            conn.setAutoCommit(false);

            if (fixPlacedAgentStartStopDates(out, conn)) showSuccess(out, "Fixed PlacedAgent Start/Stop Dates");
            
            if (alterDatabaseBefore(out, conn)) showSuccess(out, "Altered the database.");

            if (mapAgentSnapshotToPlacedAgentCommissionProfile(out, conn)) showSuccess(out, "Mapped AgentSnapshot to PlacedAgentCommissionProfile (FKs).");

            if (alterDatabaseAfter(out, conn)) showSuccess(out, "Dropped AgentSnapshot.CommissionProfileFK.");
        }
        catch (Exception e)
        {
            if (conn != null) conn.rollback();
            
            System.out.println(e);
            
            e.printStackTrace();

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

  public static String dbSchema = null;
   
   /**
    * PlacedAgent.StopDate(s) < PlacedAgent.StartDate(s) were found in the data and need to be fixed (the underlying
    * cause was the PlacedAgent Copy which didn't do a check on the final date calcs. This has been fixed).
    * We search for all PlacedAgent.StopDate(s) < PlacedAgent.StartDate(s) and set them =.
    */
   private boolean fixPlacedAgentStartStopDates(Writer out, Connection conn) throws Exception
   {        
        String sql1 = " update EDITSOLUTIONS.\"PlacedAgent\"\n" + 
    " set EDITSOLUTIONS.\"PlacedAgent\".\"StopDate\" = EDITSOLUTIONS.\"PlacedAgent\".\"StartDate\"\n" + 
    " where EDITSOLUTIONS.\"PlacedAgent\".\"StopDate\" < EDITSOLUTIONS.\"PlacedAgent\".\"StartDate\"";
    
      sql1 = substituteSchemaName(sql1);
    
        PreparedStatement ps1 = null;
        
        try
        {
            ps1 = conn.prepareStatement(sql1);
            
            ps1.executeUpdate();
        }
        finally
        {
            if (ps1 != null) ps1.close();
        }
        
        return true;
   }
   
   /**
    * Searches the specified sql and replaces "EDITSOLUTIONS." with
    * the configured db schema.
    */
   private String substituteSchemaName(String sql)
   {
    sql = sql.replaceAll("EDITSOLUTIONS.", dbSchema + ".");
    
    return sql;
   }

    /**
         * Once the PlacedAgentCommissionProfiles have been created, the AgentSnapshot needs to have its
      * PlacedAgentFK and CommissionProfileFK removed.
      * 1. Drop the AgentSnapshot.CommissionProfileFK.
      * 2. Drop the PlacedAgent.CommissionProfileFK.
      */
    private boolean alterDatabaseAfter(Writer out, Connection conn) throws Exception
    {
        Statement s = null;
       
        // Step 1
        String dropAgentSnapshotCommissionProfileFK = "ALTER TABLE EDITSOLUTIONS.\"AgentSnapshot\" DROP COLUMN \"CommissionProfileFK\"";        

        dropAgentSnapshotCommissionProfileFK = substituteSchemaName(dropAgentSnapshotCommissionProfileFK);
        
        // Step 2
        String dropCommissionProfileFK = "ALTER TABLE EDITSOLUTIONS.\"PlacedAgent\" DROP COLUMN \"CommissionProfileFK\"";        

        dropCommissionProfileFK = substituteSchemaName(dropCommissionProfileFK);

        try
        {
            s = conn.createStatement();
            
            // Step 1
            s.addBatch(dropAgentSnapshotCommissionProfileFK);
            
            attempting(out, dropAgentSnapshotCommissionProfileFK);  
            
            // Step 2
            s.addBatch(dropCommissionProfileFK);
            
            attempting(out, dropCommissionProfileFK);              
            
            s.executeBatch();
        }
        finally
        {
            if (s != null) s.close();
        }

        return true;
    }

    /**
                        * Establishes a JDBC Connection from the specified JDBC parameters.
                        */
    private Connection getConnection(HttpServletRequest request) throws Exception
    {
        String driverClassName = request.getParameter("driverClassName");

        String url = request.getParameter("url");

        String schemaName = request.getParameter("schemaName");
        
        dbSchema = schemaName;

        String username = request.getParameter("username");

        String password = request.getParameter("password");

        //Class theClass = oracle.jdbc.driver.OracleDriver.class;

        Class.forName(driverClassName);

        Connection conn = DriverManager.getConnection(url, username, password);

        return conn;
    }


    /**
                                  * For every unique AgentSnapshot.PlacedAgentFK/AgentSnapshot.CommissionProfileFK, and new
                                  * PlacedAgentCommissionProfile entry is added and associated with its respective
                                  * PlacedAgent and CommissionProfile.
                                  */
    private boolean mapAgentSnapshotToPlacedAgentCommissionProfile(Writer out, Connection conn) throws Exception
    {
        // Step 1 - Find all unique PlacedAgentPKs associated with an AgentSnapshot through EDITTrxHistory of TrxTypeCT of type 'IS'.
        // We are trying to find the AgentSnapshot.ComissionProfile associated with the 'IS' TrxType with the greatest
        // EDITTrxHistory.OriginalProcessDateTime
        
        String sql1 = " SELECT DISTINCT EDITSOLUTIONS.\"PlacedAgent\".\"PlacedAgentPK\" \n" + 
    " FROM EDITSOLUTIONS.\"EDITTrxHistory\" INNER JOIN EDITSOLUTIONS.\"EDITTrx\"\n" + 
    " ON EDITSOLUTIONS.\"EDITTrxHistory\".\"EDITTrxFK\" = EDITSOLUTIONS.\"EDITTrx\".\"EDITTrxPK\"\n" + 
    " inner join EDITSOLUTIONS.\"ClientSetup\"\n" + 
    " ON EDITSOLUTIONS.\"EDITTrx\".\"ClientSetupFK\" = EDITSOLUTIONS.\"ClientSetup\".\"ClientSetupPK\"\n" + 
    " inner join EDITSOLUTIONS.\"ContractClient\"\n" + 
    " on EDITSOLUTIONS.\"ClientSetup\".\"ContractClientFK\" = EDITSOLUTIONS.\"ContractClient\".\"ContractClientPK\"\n" + 
    " inner join EDITSOLUTIONS.\"Segment\"\n" + 
    " on EDITSOLUTIONS.\"ContractClient\".\"SegmentFK\" = EDITSOLUTIONS.\"Segment\".\"SegmentPK\"\n" + 
    " inner join EDITSOLUTIONS.\"AgentHierarchy\"\n" + 
    " on EDITSOLUTIONS.\"Segment\".\"SegmentPK\" = EDITSOLUTIONS.\"AgentHierarchy\".\"SegmentFK\"\n" + 
    " inner join EDITSOLUTIONS.\"AgentSnapshot\"\n" + 
    " on EDITSOLUTIONS.\"AgentHierarchy\".\"AgentHierarchyPK\" = EDITSOLUTIONS.\"AgentSnapshot\".\"AgentHierarchyFK\"\n" + 
    " inner join EDITSOLUTIONS.\"PlacedAgent\"\n" + 
    " on EDITSOLUTIONS.\"AgentSnapshot\".\"PlacedAgentFK\" = EDITSOLUTIONS.\"PlacedAgent\".\"PlacedAgentPK\"\n" + 
    " where EDITSOLUTIONS.\"EDITTrx\".\"TransactionTypeCT\" = 'IS'";
    
       sql1 = substituteSchemaName(sql1);
    
        
        // Step 2 - Once we find the PlacedAgentPKs, we need to find its associated AgentSnapshot with the CommissionProfile
        // associated with the most recent EDITTrxHistory.OriginalProcessDateTime
        String sql2 = " SELECT EDITSOLUTIONS.\"AgentSnapshot\".\"CommissionProfileFK\", EDITSOLUTIONS.\"PlacedAgent\".\"StartDate\"\n" + 
    " FROM EDITSOLUTIONS.\"EDITTrxHistory\" INNER JOIN EDITSOLUTIONS.\"EDITTrx\"\n" + 
    " ON EDITSOLUTIONS.\"EDITTrxHistory\".\"EDITTrxFK\" = EDITSOLUTIONS.\"EDITTrx\".\"EDITTrxPK\"\n" + 
    " inner join EDITSOLUTIONS.\"ClientSetup\"\n" + 
    " ON EDITSOLUTIONS.\"EDITTrx\".\"ClientSetupFK\" = EDITSOLUTIONS.\"ClientSetup\".\"ClientSetupPK\"\n" + 
    " inner join EDITSOLUTIONS.\"ContractClient\"\n" + 
    " on EDITSOLUTIONS.\"ClientSetup\".\"ContractClientFK\" = EDITSOLUTIONS.\"ContractClient\".\"ContractClientPK\"\n" + 
    " inner join EDITSOLUTIONS.\"Segment\"\n" + 
    " on EDITSOLUTIONS.\"ContractClient\".\"SegmentFK\" = EDITSOLUTIONS.\"Segment\".\"SegmentPK\"\n" + 
    " inner join EDITSOLUTIONS.\"AgentHierarchy\"\n" + 
    " on EDITSOLUTIONS.\"Segment\".\"SegmentPK\" = EDITSOLUTIONS.\"AgentHierarchy\".\"SegmentFK\"\n" + 
    " inner join EDITSOLUTIONS.\"AgentSnapshot\"\n" + 
    " on EDITSOLUTIONS.\"AgentHierarchy\".\"AgentHierarchyPK\" = EDITSOLUTIONS.\"AgentSnapshot\".\"AgentHierarchyFK\"\n" + 
    " inner join EDITSOLUTIONS.\"PlacedAgent\"\n" + 
    " on EDITSOLUTIONS.\"AgentSnapshot\".\"PlacedAgentFK\" = EDITSOLUTIONS.\"PlacedAgent\".\"PlacedAgentPK\"\n" + 
    " where EDITSOLUTIONS.\"EDITTrx\".\"TransactionTypeCT\" = 'IS'\n" + 
    " and EDITSOLUTIONS.\"PlacedAgent\".\"PlacedAgentPK\" = ?\n" + 
    " order by EDITSOLUTIONS.\"EDITTrxHistory\".\"ProcessDateTime\" desc";
    
       sql2 = substituteSchemaName(sql2);
    
        
        // Insert the PlacedAgentCommissionProfile with the just found CommissionProfileFK and the Start/Stop dates of the PlacedAgent.
        String sql3 = " insert into EDITSOLUTIONS.\"PlacedAgentCommissionProfile\"\n" + 
    " (\"PlacedAgentCommissionProfilePK\", \"CommissionProfileFK\", \"PlacedAgentFK\", \"StartDate\", \"StopDate\")\n" + 
    " values (?, ?, ?, ?, ?)";
        
       sql3 = substituteSchemaName(sql3);

        // There still will be PlacedAgents that were never associated with an AgentSnapshot. These will simply be associated
        // to its PlacedAgentCommissionProfile with its PlacedAgent.StartDate and EDITTrx.DefaultMaxDate.
        String sql4 = " select EDITSOLUTIONS.\"PlacedAgent\".\"PlacedAgentPK\", EDITSOLUTIONS.\"PlacedAgent\".\"StartDate\", EDITSOLUTIONS.\"PlacedAgent\".\"CommissionProfileFK\"\n" + 
    " from EDITSOLUTIONS.\"PlacedAgent\"\n" + 
    " where EDITSOLUTIONS.\"PlacedAgent\".\"PlacedAgentPK\" not in (\n" + 
    " select EDITSOLUTIONS.\"AgentSnapshot\".\"PlacedAgentFK\" from EDITSOLUTIONS.\"AgentSnapshot\")";
        
       sql4 = substituteSchemaName(sql4);

        
        // Some PlacedAgent ARE associated with AgentSnapshots, but their associated Segments
        // have never been issued. In those cases, we take the current CommissionProfile.
        String sql5 = " SELECT DISTINCT EDITSOLUTIONS.\"PlacedAgent\".\"PlacedAgentPK\", EDITSOLUTIONS.\"PlacedAgent\".\"CommissionProfileFK\", EDITSOLUTIONS.\"PlacedAgent\".\"StartDate\"\n" + 
    " FROM EDITSOLUTIONS.\"PlacedAgent\" INNER JOIN EDITSOLUTIONS.\"AgentSnapshot\"\n" + 
    " ON EDITSOLUTIONS.\"PlacedAgent\".\"PlacedAgentPK\" = EDITSOLUTIONS.\"AgentSnapshot\".\"PlacedAgentFK\"\n" + 
    " WHERE EDITSOLUTIONS.\"PlacedAgent\".\"PlacedAgentPK\" NOT IN\n" + 
    " (               \n" + 
    " SELECT DISTINCT EDITSOLUTIONS.\"PlacedAgent\".\"PlacedAgentPK\"\n" + 
    " FROM EDITSOLUTIONS.\"EDITTrxHistory\" INNER JOIN EDITSOLUTIONS.\"EDITTrx\"\n" + 
    " ON EDITSOLUTIONS.\"EDITTrxHistory\".\"EDITTrxFK\" = EDITSOLUTIONS.\"EDITTrx\".\"EDITTrxPK\"\n" + 
    " inner join EDITSOLUTIONS.\"ClientSetup\"\n" + 
    " ON EDITSOLUTIONS.\"EDITTrx\".\"ClientSetupFK\" = EDITSOLUTIONS.\"ClientSetup\".\"ClientSetupPK\"\n" + 
    " inner join EDITSOLUTIONS.\"ContractClient\"\n" + 
    " on EDITSOLUTIONS.\"ClientSetup\".\"ContractClientFK\" = EDITSOLUTIONS.\"ContractClient\".\"ContractClientPK\"\n" + 
    " inner join EDITSOLUTIONS.\"Segment\"\n" + 
    " on EDITSOLUTIONS.\"ContractClient\".\"SegmentFK\" = EDITSOLUTIONS.\"Segment\".\"SegmentPK\"\n" + 
    " inner join EDITSOLUTIONS.\"AgentHierarchy\"\n" + 
    " on EDITSOLUTIONS.\"Segment\".\"SegmentPK\" = EDITSOLUTIONS.\"AgentHierarchy\".\"SegmentFK\"\n" + 
    " inner join EDITSOLUTIONS.\"AgentSnapshot\"\n" + 
    " on EDITSOLUTIONS.\"AgentHierarchy\".\"AgentHierarchyPK\" = EDITSOLUTIONS.\"AgentSnapshot\".\"AgentHierarchyFK\"\n" + 
    " inner join EDITSOLUTIONS.\"PlacedAgent\"\n" + 
    " on EDITSOLUTIONS.\"AgentSnapshot\".\"PlacedAgentFK\" = EDITSOLUTIONS.\"PlacedAgent\".\"PlacedAgentPK\"\n" + 
    " where EDITSOLUTIONS.\"EDITTrx\".\"TransactionTypeCT\" = 'IS'\n" + 
    " )";
        
       sql5 = substituteSchemaName(sql5);
        
        
        Statement s1 = null;
        
        ResultSet rs1 = null;
        
        PreparedStatement ps1 = null;
        
        ResultSet rs2 = null;
        
        PreparedStatement ps2 = null;
        
        Statement s2 = null;
        
        ResultSet rs3 = null;
        
        Statement s3 = null;
        
        ResultSet rs4 = null;
        
        try
        {         
            s1 = conn.createStatement();

            rs1 = s1.executeQuery(sql1);
            
            while (rs1.next())
            {
                long placedAgentPK = rs1.getLong("PlacedAgentPK");
                
                if (ps1 == null)
                {
                    ps1 = conn.prepareStatement(sql2);
                }
                    
                ps1.setLong(1, placedAgentPK);

                rs2 = ps1.executeQuery();

                // Records are descending by EDITTrxHistory.ProcessDateTime - we just need the first one
                rs2.next();
                
                long maxCommissionProfilePK = rs2.getLong("CommissionProfileFK");
                
                Date startDate = rs2.getDate("StartDate");
                
                Date stopDate = new Date(new EDITDate(EDITDate.DEFAULT_MAX_DATE).getTimeInMilliseconds());
                
                if (ps2 == null)
                {
                    ps2 = conn.prepareStatement(sql3);
                }
                
                ps2.setLong(1, CRUD.getNextAvailableKey());
                
                ps2.setLong(2, maxCommissionProfilePK);
                
                ps2.setLong(3, placedAgentPK);
                
                ps2.setDate(4, startDate);
                
                ps2.setDate(5, stopDate);

                ps2.executeUpdate();
            }
            
            // Now deal with all PlacedAgents that were never mapped to an AgentSnapshot
            s2 = conn.createStatement();
            
            rs3 = s2.executeQuery(sql4);
            
            while (rs3.next())
            {
                long placedAgentPK = rs3.getLong("PlacedAgentPK");
                
                long commissionProfileFK = rs3.getLong("CommissionProfileFK");
                
                Date startDate = rs3.getDate("StartDate");
                
                Date stopDate = new Date(new EDITDate(EDITDate.DEFAULT_MAX_DATE).getTimeInMilliseconds());
                
                if (ps2 == null)
                {
                    ps2 = conn.prepareStatement(sql3);
                }
                
                ps2.setLong(1, CRUD.getNextAvailableKey());
                
                ps2.setLong(2, commissionProfileFK);
                
                ps2.setLong(3, placedAgentPK);
                
                ps2.setDate(4, startDate);
                
                ps2.setDate(5, stopDate);
                
                ps2.executeUpdate();
            }

             // Now deal with the PlacedAgents that are associated with AgentSnapshots associated with
            // Segments that have never been issued.
            
            s3 = conn.createStatement();
            
            rs4 = s3.executeQuery(sql5);
            
            while (rs4.next())
            {
                long placedAgentPK = rs4.getLong("PlacedAgentPK");
                
                long commissionProfileFK = rs4.getLong("CommissionProfileFK");
                
                Date startDate = rs4.getDate("StartDate");
                
                Date stopDate = new Date(new EDITDate(EDITDate.DEFAULT_MAX_DATE).getTimeInMilliseconds());
                
                if (ps2 == null)
                {
                    ps2 = conn.prepareStatement(sql3);
                }
                
                ps2.setLong(1, CRUD.getNextAvailableKey());
                
                ps2.setLong(2, commissionProfileFK);
                
                ps2.setLong(3, placedAgentPK);
                
                ps2.setDate(4, startDate);
                
                ps2.setDate(5, stopDate);
                
                ps2.executeUpdate();                
            }
        }
        finally
        {
            if (rs1 != null) rs1.close();
            if (s1 != null) s1.close();
            
            if (rs2 != null) rs2.close();
            if (ps1 != null) ps1.close();
            
            if (ps2 != null) ps2.close();
            
            if (rs3 != null) rs3.close();
            if (s2 != null) s2.close();
        }

        return true;
    }

    /**
                                   * Convenience method - Displays any successes from the conversion.
                                   */
    private void showSuccess(Writer out, String message) throws Exception
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
                                   */
    private void showError(Writer out, Exception e) throws Exception
    {
        out.write("<span style='background-color:yellow; width:100%'>");

        out.write("<hr>");

        out.write("The conversion was aborted for the following reason(s):<br><br>");

        out.write("<font face='' color='red'>");
     
        e.printStackTrace(new java.io.PrintWriter(out));
        
        out.write("</font>");

        out.write("<hr>");

        out.write("</span>");
    }

    /**
                                   * Convenience method to display conversion status.
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
                                  * The database needs to be physically modified before the data conversion:
                                  * 1. Add the new PlacedAgentCommissionProfile table.
                                  * 2. A new PlacedAgentCommissionProfile/CommissionProfile constraint is added.
                                  * 3. A new PlacedAgentCommissionProfile/PlacedAgent constraint is added.
                                  * 4. Add PlacedAgentCommissionProfilePK as a PK.
                                  * @return false if no updates were made signifying that this update process has been previously performed
                                  */
    private boolean alterDatabaseBefore(Writer out, Connection conn) throws Exception
    {
        // Step 1
        String addPlacedAgentCommissionProfileTable = "CREATE TABLE EDITSOLUTIONS.\"PlacedAgentCommissionProfile\" (\"PlacedAgentCommissionProfilePK\" number NOT NULL, \"CommissionProfileFK\" number NULL, \"PlacedAgentFK\" number NULL, \"StartDate\" date NULL, \"StopDate\" date NULL)";

        addPlacedAgentCommissionProfileTable = substituteSchemaName(addPlacedAgentCommissionProfileTable);


        // Step 2
        String addPlacedAgentCommissionProfileCommissionProfileConstraint = "ALTER TABLE EDITSOLUTIONS.\"PlacedAgentCommissionProfile\" ADD CONSTRAINT \"FK_PlcdAgntCommProf_CommProf\" FOREIGN KEY (\"CommissionProfileFK\") REFERENCES EDITSOLUTIONS.\"CommissionProfile\" (\"CommissionProfilePK\")";

        addPlacedAgentCommissionProfileCommissionProfileConstraint = substituteSchemaName(addPlacedAgentCommissionProfileCommissionProfileConstraint);


        // Step 3
        String addPlacedAgentCommissionProfilePlacedAgentConstraint = "ALTER TABLE EDITSOLUTIONS.\"PlacedAgentCommissionProfile\" ADD CONSTRAINT \"FK_PlcdAgntCommProf_PlcdAgnt\" FOREIGN KEY(\"PlacedAgentFK\") REFERENCES EDITSOLUTIONS.\"PlacedAgent\"(\"PlacedAgentPK\")";

        addPlacedAgentCommissionProfilePlacedAgentConstraint = substituteSchemaName(addPlacedAgentCommissionProfilePlacedAgentConstraint);


        // Step 4
        String addPlacedAgentCommissionProfileAsPK = "ALTER TABLE EDITSOLUTIONS.\"PlacedAgentCommissionProfile\" ADD CONSTRAINT \"PK_PlacedAgentCommissionProf\" PRIMARY KEY (\"PlacedAgentCommissionProfilePK\")";

        addPlacedAgentCommissionProfileAsPK = substituteSchemaName(addPlacedAgentCommissionProfileAsPK);

        Statement s = null;

        try
        {
            s = conn.createStatement();

            // Step 1
            s.addBatch(addPlacedAgentCommissionProfileTable);

            attempting(out, addPlacedAgentCommissionProfileTable);
            
            s.executeBatch();
            
            s.clearBatch();

            // Step 2
            s.addBatch(addPlacedAgentCommissionProfileCommissionProfileConstraint);

            attempting(out, addPlacedAgentCommissionProfileCommissionProfileConstraint);

            s.executeBatch();
            
            s.clearBatch();

            // Step 3
            s.addBatch(addPlacedAgentCommissionProfilePlacedAgentConstraint);

            attempting(out, addPlacedAgentCommissionProfilePlacedAgentConstraint);

            s.executeBatch();
            
            s.clearBatch();
            
            // Step 4
            s.addBatch(addPlacedAgentCommissionProfileAsPK);

            attempting(out, addPlacedAgentCommissionProfileAsPK);

            s.executeBatch();
            
            s.clearBatch();
        }
        finally
        {
            if (s != null) s.close();
        }

        return true;
    }
%>

<%--
The taglib directive below imports the JSTL library. If you uncomment it,
you must also add the JSTL library to the project. The Add Library... action
on Libraries node in Projects view can be used to add the JSTL 1.1 library.
--%>
<%--
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
--%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>

        <h1>Convert Commission Profile</h1>
        
        <p>
        This conversion will:
        <br>
        <br>1. Correct any PlacedAgent.StopDate(s) < PlacedAgent.StartDate(s).
        <br>
        <br>2. Alter the DB to:
        <br>&nbsp;&nbsp;&nbsp;a. Add a new PlacedAgentCommissionProfile table.
        <br>&nbsp;&nbsp;&nbsp;b. Add a new AgentSnapshot.PlacedAgentCommissionProfileFK.
        <br>
        <br>3. Map the AgentSnapshot's PlacedAgentFK and CommissionProfileFK to this new table via the new PlacedAgentCommissionProfileFK.
        <br>
        <br>4. Drop the AgentSnapshot.CommissionProfileFK.
        </p><p>
            5. Drop the PlacedAgent.CommissionProfileFK.
        </p><p>
            <font color="#ff0000">
                <strong>IMPORTANT:</strong>
            </font>
             Because AgentSnapshot.CommissionProfileFK and
            PlacedAgent.CommissionProfileFK are to be dropped, please 
            <strong><font color="#0000ff">
                    manually drop
                </font></strong>
             all 
            <u>
                indexes
            </u>
             and 
            <u>
                constraints
            </u>
             associated with these two fields 
            <font color="#0000ff">
                <strong>before</strong>
            </font>
             running this conversion.
        </p><p>
        This process will take several minutes. Everything is done under a single transaction, so it
        is an "all-or-nothing" process. If there are errors, the process is aborted, everything is rolled-back,
        and you are notified of the offending issue.
        </p>

        <form name="theForm">
            <hr>
            <table border="0">
                <thead>
                    <tr>
                        <th colspan="2">
                            JDBC Configuration
                        </th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td>JDBCDriver:</td>
                        <td><input type="text" name="driverClassName"
                                   value="oracle.jdbc.driver.OracleDriver" size="100"/></td>
                    </tr>
                    <tr>
                        <td>JDBCUrl:</td>
                        <td><input type="text" name="url" value="jdbc:oracle:thin:@192.168.0.176:1521:orcl" size="100" /></td>
                    </tr>
                    <tr>
                        <td>SchemaName:</td>
                        <td><input type="text" name="schemaName"
                                   value="EDITSOLUTIONS_UNITTEST" size="100" /></td>
                    </tr>
                    <tr>
                        <td>Username:</td>
                        <td><input type="text" name="username" value="SEGADMIN" size="100" /></td>
                    </tr>
                    <tr>
                        <td>Password:</td>
                        <td><input type="text" name="password" value="segllc" size="100" /></td>
                    </tr>
                    <tr>
                        <td colspan="2" align="center"><input type="submit" value="Start Conversion" name="btnStartConversion" onClick="theForm.startConversion.value='true'"/></td>
                    </tr>
                </tbody>
            </table>

            <input type="hidden" name="startConversion" value="false" />

        </form>
    
    
    </body>
</html>
