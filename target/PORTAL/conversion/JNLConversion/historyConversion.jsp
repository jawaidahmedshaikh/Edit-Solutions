<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>

<%@page import="fission.utility.*, java.sql.*, edit.services.db.hibernate.*, java.io.*, edit.services.db.*, edit.common.*,
                edit.common.vo.*,
                java.util.*,
                event.dm.dao.*,
                event.*,
                client.*,
                conversion.*" %>

<%
    String startProcess = Util.initString(request.getParameter("startProcess"), "false");

    if (startProcess.equals("true"))
    {
        Connection conn = getConnection(request);
        conn.setAutoCommit(false);

        CRUD crud = CRUDFactory.getSingleton().getCRUD(ConnectionFactory.EDITSOLUTIONS_POOL);

        HashMap countTable = new HashMap();

        try
        {
            System.out.println("************** CONVERSION MESSAGES START ************************");

            ConvertSegmentActivityHistory convertHistory = new ConvertSegmentActivityHistory(conn, crud);

            convertHistory.convertSegmentActivtyHistory(countTable);
            
             System.out.println("************** CONVERSION MESSAGES END ************************");

            showSuccess(out, "Number of EDITTrxHistory(SegmentActivityHistory) records in = " + countTable.get("ETcountIn") + " out = " + countTable.get("ETcountOut"));
            showSuccess(out, "Number of FinancialHistory records  out = " + countTable.get("FIcountOut"));
            showSuccess(out, "Number of WithholdingHistory records  out = " + countTable.get("WIcountOut"));
            showSuccess(out, "Number of InvestmentHistory records in = " + countTable.get("INcountIn") + " out = " + countTable.get("INcountOut"));
            showSuccess(out, "Number of BucketHistory records in = " + countTable.get("BUcountIn") + " out = " + countTable.get("BUcountOut"));
            showSuccess(out, "Number of ChargeHistory records in = " + countTable.get("CHcountIn") + " out = " + countTable.get("CHcountOut"));
            showSuccess(out, "Number of Suspense records in = " + countTable.get("SUcountIn") + " out = " + countTable.get("SUcountOut"));

        }
        catch (Exception e)
        {
            if (conn != null) conn.rollback();

            if (crud != null) crud.rollbackTransaction();

            showError(out, e);

        }
        finally
        {
            if (conn != null) conn.commit();

            if (conn != null) conn.close();

            if (crud != null) crud.setRestoringRealTime(false);

            if (crud != null) crud.commitTransaction();

            if (crud != null) crud.close();
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

        System.out.println("");
        System.out.println(message);
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

        System.out.println("");
        System.out.println(e.getMessage());
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

        System.out.println("");
        System.out.println(message);
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

        <h1>Convert CONTRACT Database to the matching tables in EDITSolutions 3RD Pass for History</h1>

        <p>
        This process will:
        <br>
        <br>1. Read each row in the CONTRACT DB, of the SegmentActivityHistory table.
        <br>2. For each of these rows:
        <br>&nbsp;&nbsp;&nbsp;a. Set the like fields into the data definition of the table.
        <br>&nbsp;&nbsp;&nbsp;b. Convert codetable keys to the current code values.
        <br>&nbsp;&nbsp;&nbsp;c. Convert Minimum and Maximum Default date to valid dates. The database requires valid dates.
        <br>&nbsp;&nbsp;&nbsp;d. Insert the table data into the EDITSolutions database table.
        <br>&nbsp;&nbsp;&nbsp;e. There is a new chain of parent tables for each history transaction going to the EDITTrxHistory table.  The GroupSetup, ContractSetup,
        <br>&nbsp;&nbsp;&nbsp;   ClientSetup and EDITTrx are the parents to the EDITTrxHistory table.  They are built at the same time and all are save together.
        <br>&nbsp;&nbsp;&nbsp;f. Suspense, FinancialHistory, BucketHistory, InvestmentHistory, ChargeHistory and WithholdingHistory are created
        <br>&nbsp;&nbsp;&nbsp;   as children to the EDITTrxHistory table.  They are also built at the same time and are save together, except for Suspense(save separatley).
        <br>&nbsp;&nbsp;&nbsp;g. The ContractSetup will be attached to the proper Segment and ContractClient for the transaction type.
        <br>&nbsp;&nbsp;&nbsp;h. MESSAGES WILL BE PUT INTO YOUR WEB ENVIRONMENT FOR SOME DATE CONVERSIONS.
        <br>&nbsp;&nbsp;&nbsp;&nbsp; PLEASE CHECK THE CONVERSION ON THESE DATES.  THE RECORD KEY IS PART OF THE MESSAGE.
        </p>
        <p>
        You should backup the EDITSolutions data base to be used for this process before starting, but a restore is not necessary.
        This job because of the amount of data can be restarted without restoring. The duplicate keys will be ignored.
        This job will run long and possibly reach an out memory error.  If this error occurs, stop and restart the environment
        and begin this conversion again.  The suspense conversion will occur only once if the job is restarted.
        If there are errors, the process will continue, only the offending row will be rollbacked.  You are notified of the
        offending issue.
        Your Environment is connected to the NEW ENGINE that has been populated and the New EDITSolutions, through the Config File.  This EDITSolutions
        has been loaded with all the prior conversions of Accounting, Client and Contract, pass #1 and Contract pass #2.
        <br>
        <br>Please change the connection information below in order to attach the CONTRACT table to this process.
        The JDBC Driver, URL, Schema Name, Username and Password must be changed for your database.
        </p>

        <form name="theForm">
            <hr>
            <table border="0">
                <thead>

                </thead>
                <tbody>
                    <tr>
                        <td>JDBCDriver:</td>
                        <td><input type="text" name="driverClassName" value="net.sourceforge.jtds.jdbc.Driver" size="100"/></td>
                    </tr>
                    <tr>
                        <td>JDBCUrl:</td>
                        <td><input type="text" name="url" value="jdbc:jtds:sqlserver://SEG-DATABASE:1433/CONTRACT_RX" size="100" /></td>
<%--                         <td><input type="text" name="url" value="jdbc:jtds:sqlserver://localhost:1433;DatabaseName=CONTRACT" size="100" /></td>--%>
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
