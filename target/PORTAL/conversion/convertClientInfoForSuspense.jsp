<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>

<%@page import="fission.utility.*, java.sql.*, edit.services.db.hibernate.*, java.io.*, edit.services.db.*, edit.common.*,
                edit.common.vo.*,
                java.util.*,
                event.dm.dao.*,
                event.*,
                client.*" %>

<%
    String startProcess = Util.initString(request.getParameter("startProcess"), "false");
    int transactionCount = 0;

    if (startProcess.equals("true"))
    {
        Connection conn = null;

        try
        {
            conn = getConnection(request);
            conn.setAutoCommit(false);

            Statement s = conn.createStatement();

            String sql = "SELECT * FROM SuspenseClientInformation";

            ResultSet rs = s.executeQuery(sql);

            if (!rs.wasNull())
            {
                 while (rs.next())
                 {

                     ClientDetail clientDetail = new ClientDetail();
                     clientDetail.setLastName(Util.initString(rs.getString("LastName"), null));
                     clientDetail.setFirstName(Util.initString(rs.getString("FirstName"), null));
                     clientDetail.setMiddleName(Util.initString(rs.getString("MiddleName"), null));
                     clientDetail.setNamePrefix(Util.initString(rs.getString("NamePrefix"), null));
                     clientDetail.setNameSuffix(Util.initString(rs.getString("NameSuffix"), null));
                     clientDetail.setCorporateName(Util.initString(rs.getString("CorporateName"), null));
                     clientDetail.setDateOfDeath(new EDITDate(EDITDate.DEFAULT_MAX_DATE));
                     clientDetail.setProofOfDeathReceivedDate(new EDITDate(EDITDate.DEFAULT_MAX_DATE));
                     clientDetail.setOperator("System");
                     clientDetail.setOverrideStatus("O");

                     ClientAddress clientAddress = new ClientAddress();
                     clientAddress.setAddressLine1(Util.initString(rs.getString("AddressLine1"), null));
                     clientAddress.setAddressLine2(Util.initString(rs.getString("AddressLine2"), null));
                     clientAddress.setAddressLine3(Util.initString(rs.getString("AddressLine3"), null));
                     clientAddress.setAddressLine4(Util.initString(rs.getString("AddressLine4"), null));
                     clientAddress.setCity(Util.initString(rs.getString("City"), null));
                     clientAddress.setCounty(Util.initString(rs.getString("County"), null));
                     clientAddress.setStateCT(Util.initString(rs.getString("StateCT"), null));
                     clientAddress.setCountryCT(Util.initString(rs.getString("CountryCT"), null));
                     clientAddress.setZipCode(Util.initString(rs.getString("ZipCode"), null));
                     clientAddress.setZipCodePlacementCT(Util.initString(rs.getString("ZipCodePlacementCT"), null));
                     clientAddress.setSequenceNumber(1);
                     clientAddress.setAddressTypeCT("PrimaryAddress");
                     clientAddress.setTerminationDate(new EDITDate(EDITDate.DEFAULT_MAX_DATE));
                     clientAddress.setStartDate(ClientAddress.defaultStartDate());
                     clientAddress.setStopDate(ClientAddress.defaultStopDate());
                     clientAddress.setOverrideStatus("O");

                     Preference preference = null;
                     String printAs = Util.initString(rs.getString("PrintAs"), null);
                     String printAs2 = Util.initString(rs.getString("PrintAs2"), null);

                     if(printAs != null || printAs2 != null)
                     {
                         preference = new Preference();
                         preference.setPrintAs(printAs);
                         preference.setPrintAs2(printAs2);
                         preference.setOverrideStatus("O");
                     }

                     Suspense suspense = Suspense.findByPK(rs.getLong("SuspenseFK"));
                     clientDetail.addSuspense(suspense);
                     clientDetail.addClientAddress(clientAddress);
                     clientAddress.addSuspense(suspense);

                     if (preference != null)
                     {
                         preference.addSuspense(suspense);
                         clientDetail.addPreference(preference);
                     }

                     SessionHelper.beginTransaction(SessionHelper.EDITSOLUTIONS);
                     SessionHelper.saveOrUpdate(clientDetail, SessionHelper.EDITSOLUTIONS);
                     SessionHelper.saveOrUpdate(suspense, SessionHelper.EDITSOLUTIONS);
                     SessionHelper.commitTransaction(SessionHelper.EDITSOLUTIONS);
                     transactionCount++;
                 }

                 showSuccess(out, ("Number of SuspenseClientInformation records processed: " + transactionCount));
                 s.close();
                 rs.close();

             if (dropDatabaseTable(out, conn)) showSuccess(out, "Dropped SuspenseClientInformation Table");

            }
            else
            {
                throw new Exception("No SuspenseClientInformation records found, job ended");
            }
        }
        catch (Exception e)
        {
            if (conn != null) conn.rollback();

            SessionHelper.rollbackTransaction(SessionHelper.EDITSOLUTIONS);

            showError(out, e);
        }
        finally
        {
            SessionHelper.closeSession(SessionHelper.EDITSOLUTIONS);

            if (conn != null) conn.commit();

            if (conn != null) conn.close();
        }
    }
%>

<%!
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

        out.write(e.getMessage());

        out.write("</font>");

        out.write("<hr>");

        out.write("</span>");
    }

    private boolean dropDatabaseTable(Writer out, Connection conn)  throws Exception
    {
        String removeSuspenseClientInformation = "DROP TABLE dbo.SuspenseClientInformation";

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

%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>

        <h1>Convert SuspenseClientInformation in order to remove the table</h1>

        <p>
        This process will:
        <br>
        <br>1. Get all the SuspenseClientInformation rows.
        <br>2. For each of these rows:
        <br>&nbsp;&nbsp;&nbsp;a. Create a ClientDetail in an OverrideStatus of 'O'.
        <br>&nbsp;&nbsp;&nbsp;b. Create a ClientAddress in an OverrideStatus of 'O'.
        <br>&nbsp;&nbsp;&nbsp;c. Create a Preference in an OverrideStatus of 'O', if necessary.
        <br>&nbsp;&nbsp;&nbsp;c. Insert these rows into the database attached to the Suspense.
        <br>&nbsp;&nbsp;&nbsp;d. Delete the SuspenseClientInformation row.
        <br>3. Drop the SuspenseClientInformation table from the data base.
        </p>
        <p>
        Please backup the EDITSOLUTIONS data base to be used for this process before starting, in case a restore is
        needed.  The processing is done under a single transaction, so the records are committed as each row is processed.
        If there are errors, the process is aborted, only the offending row will be rollbacked.  You are notified of the
        offending issue. The database will have to be restored to its prior state if an error occurs
        and the job rerun from the beginning.
        <br>
        <br>The last step is the Dropping of the SuspenseClientInformation table.  Please change the connection
        information below in order to find the correct table and to automatically delete it.  The JDBC Driver, URL,
        Schema Name, Username and Password must be changed for your database.
        </p>

        <form name="theForm">
            <hr>
            <table border="0">
                <thead>

                </thead>
                <tbody>
                    <tr>
                        <td>JDBCDriver:</td>
<%--                        <td><input type="text" name="driverClassName" value="net.sourceforge.jtds.jdbc.Driver" size="100"/></td>--%>
                        <td><input type="text" name="driverClassName" value="com.microsoft.jdbc.sqlserver.SQLServerDriver" size="100"/></td>
                    </tr>
                    <tr>
                        <td>JDBCUrl:</td>
<%--                        <td><input type="text" name="url" value="jdbc:jtds:sqlserver://SEG-DATABASE:1433/EDIT_SOLUTIONS_DEV" size="100" /></td>--%>
                         <td><input type="text" name="url" value="jdbc:microsoft:sqlserver://localhost:1433;DatabaseName=EDIT_SOLUTIONS_BASE" size="100" /></td>
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
                        <td colspan="2" align="center"><input type="submit" value="Start Process" name="btnStartProcess" onClick="theForm.startProcess.value='true'"/></td>
                    </tr>
                </tbody>
            </table>

            <input type="hidden" name="startProcess" value="false" />

        </form>

    </body>
</html>
