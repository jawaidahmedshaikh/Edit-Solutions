<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>

<%@page import="fission.utility.*, java.sql.*, edit.services.db.hibernate.*, java.io.*, java.sql.*, edit.services.db.*, edit.common.*" %>

<%
    /**
     * Note: Before running this conversion jsp, need to run the sql (Change_Needed_For_CommissionProfile_Conversion.sql)
     * located in perforce path BaseChangesFixes\Database\UpdateScriptsForHLPPData to break relation ship between
     * AgentSnapshot and CommissionProfile if the constranint exists. The EQ database does not have this constraint.
     * If the sql errors that means the constraint does not exist ne need to bother.
     */

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
    * PlacedAgent.StopDate(s) < PlacedAgent.StartDate(s) were found in the data and need to be fixed (the underlying
    * cause was the PlacedAgent Copy which didn't do a check on the final date calcs. This has been fixed).
    * We search for all PlacedAgent.StopDate(s) < PlacedAgent.StartDate(s) and set them =.
    */
   private boolean fixPlacedAgentStartStopDates(Writer out, Connection conn) throws Exception
   {        
        String sql1 = " update PlacedAgent" +
                       " set PlacedAgent.StopDate = PlacedAgent.StartDate" +
                       " where PlacedAgent.StopDate < PlacedAgent.StartDate"; 
    
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
         * Once the PlacedAgentCommissionProfiles have been created, the AgentSnapshot needs to have its
      * PlacedAgentFK and CommissionProfileFK removed.
      * 2. Drop the AgentSnapshot2 Index.
      * 3. Drop the CommissionProfileFK.
      */
    private boolean alterDatabaseAfter(Writer out, Connection conn) throws Exception
    {
        Statement s = null;
        
        // Step 1
        String dropAgentSnapshotIndex1 = "DROP INDEX dbo.AgentSnapshot.IX_AgentSnapshot_2";
        
       
        // Step 3
        String dropAgentSnapshotCommissionProfileFK = "ALTER TABLE dbo.AgentSnapshot DROP COLUMN CommissionProfileFK";        
        
        // Step 5
        String dropCommissionProfileFKIndex = "DROP INDEX dbo.PlacedAgent.IX_PlacedAgent_1";

        // Step 6
        String dropCommissionProfileFK = "ALTER TABLE dbo.PlacedAgent DROP COLUMN CommissionProfileFK";        

        try
        {
            s = conn.createStatement();
            
            // Step 1
            s.addBatch(dropAgentSnapshotIndex1);
            
            attempting(out, dropAgentSnapshotIndex1);
            
            // Step 3
            s.addBatch(dropAgentSnapshotCommissionProfileFK);
            
            attempting(out, dropAgentSnapshotCommissionProfileFK);  
            
            // Step 5
            s.addBatch(dropCommissionProfileFKIndex);
            
            attempting(out, dropCommissionProfileFKIndex);  
            
            // Step 6
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

        String username = request.getParameter("username");

        String password = request.getParameter("password");

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
        
        String sql1 = " SELECT DISTINCT PlacedAgent.PlacedAgentPK" + 
                    " FROM EDITTrxHistory INNER JOIN EDITTrx" +
                    " ON EDITTrxHistory.EDITTrxFK = EDITTrx.EDITTrxPK" +
                    " inner join ClientSetup" +
                    " ON EDITTrx.ClientSetupFK = ClientSetup.ClientSetupPK" +
                    " inner join ContractClient" +
                    " on ClientSetup.ContractClientFK = ContractClient.ContractClientPK" +
                    " inner join Segment" +
                    " on ContractClient.SegmentFK = Segment.SegmentPK" +
                    " inner join AgentHierarchy" +
                    " on Segment.SegmentPK = AgentHierarchy.SegmentFK" +
                    " inner join AgentSnapshot" +
                    " on AgentHierarchy.AgentHierarchyPK = AgentSnapshot.AgentHierarchyFK" +
                    " inner join PlacedAgent" +
                    " on AgentSnapshot.PlacedAgentFK = PlacedAgent.PlacedAgentPK" +
                    " where EDITTrx.TransactionTypeCT = 'IS'";
        
        // Step 2 - Once we find the PlacedAgentPKs, we need to find its associated AgentSnapshot with the CommissionProfile
        // associated with the most recent EDITTrxHistory.OriginalProcessDateTime
        String sql2 = " SELECT AgentSnapshot.CommissionProfileFK, PlacedAgent.StartDate" + 
                    " FROM EDITTrxHistory INNER JOIN EDITTrx" +
                    " ON EDITTrxHistory.EDITTrxFK = EDITTrx.EDITTrxPK" +
                    " inner join ClientSetup" +
                    " ON EDITTrx.ClientSetupFK = ClientSetup.ClientSetupPK" +
                    " inner join ContractClient" +
                    " on ClientSetup.ContractClientFK = ContractClient.ContractClientPK" +
                    " inner join Segment" +
                    " on ContractClient.SegmentFK = Segment.SegmentPK" +
                    " inner join AgentHierarchy" +
                    " on Segment.SegmentPK = AgentHierarchy.SegmentFK" +
                    " inner join AgentSnapshot" +
                    " on AgentHierarchy.AgentHierarchyPK = AgentSnapshot.AgentHierarchyFK" +
                    " inner join PlacedAgent" +
                    " on AgentSnapshot.PlacedAgentFK = PlacedAgent.PlacedAgentPK" +
                    " where EDITTrx.TransactionTypeCT = 'IS'" +
                    " and PlacedAgent.PlacedAgentPK = ?" +
                    " order by EDITTrxHistory.ProcessDateTime desc";
        
        // Insert the PlacedAgentCommissionProfile with the just found CommissionProfileFK and the Start/Stop dates of the PlacedAgent.
        String sql3 = " insert into PlacedAgentCommissionProfile" +
                        " (PlacedAgentCommissionProfilePK, CommissionProfileFK, PlacedAgentFK, StartDate, StopDate)" +
                        " values (?,?,?,?,?)";
        
        
        // There still will be PlacedAgents that were never associated with an AgentSnapshot. These will simply be associated
        // to its PlacedAgentCommissionProfile with its PlacedAgent.StartDate and EDITTrx.DefaultMaxDate.
        String sql4 = " select PlacedAgent.PlacedAgentPK, PlacedAgent.StartDate, PlacedAgent.CommissionProfileFK " +
                        " from PlacedAgent" +
                        " where PlacedAgent.PlacedAgentPK not in (" +
                        " select AgentSnapshot.PlacedAgentFK" +
                        " from AgentSnapshot)";
        
        // Some PlacedAgent ARE associated with AgentSnapshots, but their associated Segments
        // have never been issued. In those cases, we take the current CommissionProfile.
        String sql5 = " SELECT DISTINCT PlacedAgent.PlacedAgentPK, PlacedAgent.CommissionProfileFK, PlacedAgent.StartDate" +
                    " FROM PlacedAgent INNER JOIN AgentSnapshot" +
                    " ON PlacedAgent.PlacedAgentPK = AgentSnapshot.PlacedAgentFK" +
                    " WHERE PlacedAgent.PlacedAgentPK NOT IN" +
                    " (" +               
                
                    " SELECT DISTINCT PlacedAgent.PlacedAgentPK" + 
                    " FROM EDITTrxHistory INNER JOIN EDITTrx" +
                    " ON EDITTrxHistory.EDITTrxFK = EDITTrx.EDITTrxPK" +
                    " inner join ClientSetup" +
                    " ON EDITTrx.ClientSetupFK = ClientSetup.ClientSetupPK" +
                    " inner join ContractClient" +
                    " on ClientSetup.ContractClientFK = ContractClient.ContractClientPK" +
                    " inner join Segment" +
                    " on ContractClient.SegmentFK = Segment.SegmentPK" +
                    " inner join AgentHierarchy" +
                    " on Segment.SegmentPK = AgentHierarchy.SegmentFK" +
                    " inner join AgentSnapshot" +
                    " on AgentHierarchy.AgentHierarchyPK = AgentSnapshot.AgentHierarchyFK" +
                    " inner join PlacedAgent" +
                    " on AgentSnapshot.PlacedAgentFK = PlacedAgent.PlacedAgentPK" +
                    " where EDITTrx.TransactionTypeCT = 'IS'" +
                
                    " )";
        
        
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
                
                String startDate = rs2.getString("StartDate");
                
                String stopDate = EDITDate.DEFAULT_MAX_DATE;
              
                if (ps2 == null)
                {
                    ps2 = conn.prepareStatement(sql3);
                }
                
                ps2.setLong(1, CRUD.getNextAvailableKey());
                
                ps2.setLong(2, maxCommissionProfilePK);
                
                ps2.setLong(3, placedAgentPK);
                
                ps2.setString(4, startDate);
                
                ps2.setString(5, stopDate);
                
                ps2.executeUpdate();
            }
            
            // Now deal with all PlacedAgents that were never mapped to an AgentSnapshot
            s2 = conn.createStatement();
            
            rs3 = s2.executeQuery(sql4);
            
            while (rs3.next())
            {
                long placedAgentPK = rs3.getLong("PlacedAgentPK");
                
                long commissionProfileFK = rs3.getLong("CommissionProfileFK");
                
                String startDate = rs3.getString("StartDate");
                
                String stopDate = EDITDate.DEFAULT_MAX_DATE;
                
                if (ps2 == null)
                {
                    ps2 = conn.prepareStatement(sql3);
                }
                
                ps2.setLong(1, CRUD.getNextAvailableKey());
                
                ps2.setLong(2, commissionProfileFK);
                
                ps2.setLong(3, placedAgentPK);
                
                ps2.setString(4, startDate);
                
                ps2.setString(5, stopDate);
                
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
                
                String startDate = rs4.getString("StartDate");
                
                String stopDate = EDITDate.DEFAULT_MAX_DATE;
                
                if (ps2 == null)
                {
                    ps2 = conn.prepareStatement(sql3);
                }
                
                ps2.setLong(1, CRUD.getNextAvailableKey());
                
                ps2.setLong(2, commissionProfileFK);
                
                ps2.setLong(3, placedAgentPK);
                
                ps2.setString(4, startDate);
                
                ps2.setString(5, stopDate);
                
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
                                  * 1. Remove the existing constraint between PlacedAgent and CommissionProfile.
                                  * 2. Add the new PlacedAgentCommissionProfile table.
                                  * 3. A new PlacedAgentCommissionProfile/CommissionProfile constraint is added.
                                  * 4. A new PlacedAgentCommissionProfile/PlacedAgent constraint is added.
                                  * 5. Remove the index on the about-to-be-removed CommissionProfileFK
                                  * 6. Remove the existing CommissionProfileFK from PlacedAgent.
                                  * 8. Add PlacedAgentCommissionProfilePK as a PK.
                                  * @return false if no updates were made signifying that this update process has been previously performed
                                  */
    private boolean alterDatabaseBefore(Writer out, Connection conn) throws Exception
    {
        // Step 1
        String removePlacedAgentConstraint = "ALTER TABLE dbo.PlacedAgent DROP CONSTRAINT FK_PlacedAgent_CommissionProfile";

        // Step 2
        String addPlacedAgentCommissionProfileTable = "CREATE TABLE dbo.PlacedAgentCommissionProfile (PlacedAgentCommissionProfilePK bigint NOT NULL,CommissionProfileFK bigint NULL,PlacedAgentFK bigint NULL,	StartDate datetime NULL,StopDate datetime NULL)  ON [PRIMARY]";

        // Step 3
        String addPlacedAgentCommissionProfileCommissionProfileConstraint = "ALTER TABLE dbo.PlacedAgentCommissionProfile ADD CONSTRAINT FK_PlacedAgentCommissionProfile_CommissionProfile FOREIGN KEY(CommissionProfileFK) REFERENCES dbo.CommissionProfile(CommissionProfilePK)";

        // Step 4
        String addPlacedAgentCommissionProfilePlacedAgentConstraint = "ALTER TABLE dbo.PlacedAgentCommissionProfile ADD CONSTRAINT FK_PlacedAgentCommissionProfile_PlacedAgent FOREIGN KEY(PlacedAgentFK) REFERENCES dbo.PlacedAgent(PlacedAgentPK)";

        // Step 8
        String addPlacedAgentCommissionProfileAsPK = "ALTER TABLE dbo.PlacedAgentCommissionProfile ADD CONSTRAINT PK_PlacedAgentCommissionProfile PRIMARY KEY CLUSTERED (PlacedAgentCommissionProfilePK	) ON [PRIMARY]";

        Statement s = null;

        try
        {
            s = conn.createStatement();

            // Step 1
            s.addBatch(removePlacedAgentConstraint);

            attempting(out, removePlacedAgentConstraint);

            // Step 2
            s.addBatch(addPlacedAgentCommissionProfileTable);

            attempting(out, addPlacedAgentCommissionProfileTable);

            // Step 3
            s.addBatch(addPlacedAgentCommissionProfileCommissionProfileConstraint);

            attempting(out, addPlacedAgentCommissionProfileCommissionProfileConstraint);

            // Step 4
            s.addBatch(addPlacedAgentCommissionProfilePlacedAgentConstraint);

            attempting(out, addPlacedAgentCommissionProfilePlacedAgentConstraint);

            // Step 8
            s.addBatch(addPlacedAgentCommissionProfileAsPK);

            attempting(out, addPlacedAgentCommissionProfileAsPK);

            s.executeBatch();
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
        <br>0. Corrent any PlacedAgent.StopDate(s) < PlacedAgent.StartDate(s).
        <br>
        <br>1. Alter the DB to:
        <br>&nbsp;&nbsp;&nbsp;a. Add a new PlacedAgentCommissionProfile table.
        <br>&nbsp;&nbsp;&nbsp;b. Add a new AgentSnapshot.PlacedAgentCommissionProfileFK.
        <br>&nbsp;&nbsp;&nbsp;c. Add/Drop the required indexes and contraints.
        <br>
        <br>2. Map the AgentSnapshot's PlacedAgentFK and CommissionProfileFK to this new table via the new PlacedAgentCommissionProfileFK.
        <br>
        <br>3. Remove the AgentSnapshot's CommissionProfileFK and its indexes.
        </p>
        <p>
        This process will take several minutes. Everything is done under a single transaction, so it
        is an "all-or-nothing" process. If there are errors, the process is aborted, everything is rolled-back,
        and you are notified of the offending issue. Since the name(s) of indexes/contraints are driven by the originating Schema; they may be different on [your] db.
        The scripts in this JSP should be modified as necessary.
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
                        <td><input type="text" name="driverClassName" value="net.sourceforge.jtds.jdbc.Driver" size="100"/></td>
                    </tr>
                    <tr>
                        <td>JDBCUrl:</td>
                        <td><input type="text" name="url" value="jdbc:jtds:sqlserver://LOCALHOST:1433/BASE_EQ" size="100" /></td>
                    </tr>
                    <tr>
                        <td>SchemaName:</td>
                        <td><input type="text" name="schemaName" value="dbo" size="100" /></td>
                    </tr>
                    <tr>
                        <td>Username:</td>
                        <td><input type="text" name="username" value="sa" size="100" /></td>
                    </tr>
                    <tr>
                        <td>Password:</td>
                        <td><input type="text" name="password" value="06109" size="100" /></td>
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
