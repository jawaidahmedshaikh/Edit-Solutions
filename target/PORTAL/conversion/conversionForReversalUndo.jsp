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
    int SHtransactionCount = 0;
    int BHtransactionCount = 0;

    if (startProcess.equals("true"))
    {
        Connection conn = getConnection(request);
        conn.setAutoCommit(false);

        try
        {
            List editTrxHistoryList = getEDITTrxHistories(conn);

            if (editTrxHistoryList != null)
            {
                showSuccess(out, ("Processing/Creating " + editTrxHistoryList.size() + " SegmentHistory Records"));
            }
            else
            {
                throw new Exception("No EDITTrxHistory records found, job ended");
            }

            int testSize = 15000;


            CRUD crud = CRUDFactory.getSingleton().getCRUD(ConnectionFactory.EDITSOLUTIONS_POOL);

            List voInclusionList = new ArrayList();
            voInclusionList.add(EDITTrxVO.class);
            voInclusionList.add(ClientSetupVO.class);
            voInclusionList.add(ContractSetupVO.class);
            voInclusionList.add(SegmentVO.class);
            voInclusionList.add(BucketHistoryVO.class);
            voInclusionList.add(BucketVO.class);
            voInclusionList.add(SegmentHistoryVO.class);

            EDITTrxHistoryVO editTrxHistoryVO = null;
            EDITTrxVO editTrxVO = null;

//            for (int i = 0; i < testSize; i++)
            for (int i = 0; i < editTrxHistoryList.size(); i++)
            {
                long editTrxHistoryPK = ((Long) editTrxHistoryList.get(i)).longValue();

                editTrxHistoryVO = new event.dm.composer.EDITTrxHistoryComposer(voInclusionList).compose(editTrxHistoryPK);
                editTrxVO = (EDITTrxVO)editTrxHistoryVO.getParentVO(EDITTrxVO.class);

                ContractSetupVO contractSetupVO = (ContractSetupVO) editTrxVO.getParentVO(ClientSetupVO.class).getParentVO(ContractSetupVO.class);
                SegmentVO segmentVO = (SegmentVO) contractSetupVO.getParentVO(SegmentVO.class);

                //create the segmentHistory
                SegmentHistoryVO segmentHistoryVO = editTrxHistoryVO.getSegmentHistoryVO(0);
                String transactionType = editTrxVO.getTransactionTypeCT();

                if (segmentHistoryVO != null)
                {
                    if (transactionType.equalsIgnoreCase("PE"))
                    {
                        setDate(segmentHistoryVO, segmentVO, editTrxVO, conn);
                    }

                    crud.createOrUpdateVOInDB(segmentHistoryVO);
                    SHtransactionCount++;
                }
                
                if (transactionType.equalsIgnoreCase("PE") || transactionType.equalsIgnoreCase("RN") ||
                    transactionType.equalsIgnoreCase("PY") || transactionType.equalsIgnoreCase("TF"))
                {
                    updateBucketHistory(editTrxHistoryVO, BHtransactionCount, crud);
                }
            }

            showSuccess(out, "Number of SegmentHistory records processed = " + SHtransactionCount);
            showSuccess(out, "Number of BucketHistory records processed = " + BHtransactionCount);

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
    *  Get the EDITTrxHistory Records
    */
    private List getEDITTrxHistories(Connection conn)  throws Exception
    {
        Statement s = conn.createStatement();

        List editTrxHistoryList = new ArrayList();

        String editTrxHistorySql = "SELECT EDITTrxHistoryPK FROM EDITTrxHistory, SegmentHistory, EDITTrx, ClientSetup, ContractSetup "  +
                                   "WHERE EDITTrxHistory.EDITTrxHistoryPK = SegmentHistory.EDITTrxHistoryFK "+
                                   "AND EDITTrxHistory.EDITTrxFK = EDITTrx.EDITTrxPK " +
                                   "AND EDITTrx.PendingStatus = 'H' " +
                                   "AND EDITTrx.ClientSetupFK = ClientSetup.ClientSetupPK " +
                                   "AND ClientSetup.ContractSetupFK = ContractSetup.ContractSetupPK " +
                                   "AND ContractSetup.SegmentFK IS NOT NULL";

        ResultSet rs = s.executeQuery(editTrxHistorySql);

        while (rs.next())
        {
            Long editTrxHistoryPK = new Long(rs.getLong("EDITTrxHistoryPK"));

            editTrxHistoryList.add(editTrxHistoryPK);
        }

        s.close();
        rs.close();

        return editTrxHistoryList;

    }


    
    private void setDate(SegmentHistoryVO segmentHistoryVO, SegmentVO segmentVO, EDITTrxVO editTrxVO, Connection conn) throws Exception
    {
        EDITDate effectiveDate = new EDITDate(segmentVO.getEffectiveDate());
        EDITDate lastAnniversaryDate = new EDITDate(segmentVO.getLastAnniversaryDate());

        if (effectiveDate.equals(lastAnniversaryDate))
        {
            segmentHistoryVO.setPrevLastAnniversaryDate(null);
        }

        if (effectiveDate.before(lastAnniversaryDate))
        {
            EDITDate PEEffectiveDate = new EDITDate(editTrxVO.getEffectiveDate());
            PEEffectiveDate = PEEffectiveDate.subtractYears(1);

            segmentHistoryVO.setPrevLastAnniversaryDate(PEEffectiveDate.getFormattedDay());
        }
    }
    
    private void updateBucketHistory(EDITTrxHistoryVO editTrxHistoryVO, int BHtransactionCount, CRUD crud)
    {
        BucketHistoryVO[] bucketHistoryVOs = editTrxHistoryVO.getBucketHistoryVO();

        for (int i = 0; i < bucketHistoryVOs.length; i++)
        {
            BucketVO bucketVO = (BucketVO)bucketHistoryVOs[i].getParentVO(BucketVO.class);
            if (bucketVO != null && bucketVO.getLastRenewalDate() != null)
            {
                bucketHistoryVOs[i].setPreviousLastRenewalDate(bucketVO.getLastRenewalDate());

                crud.createOrUpdateVOInDB(bucketHistoryVOs[i]);
                BHtransactionCount++;
            }
        }
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

        <h1>Update SegmentHistory from Segment and update BucketHistory from Bucket</h1>

        <p>
        This process will:
        <br>
        <br>1. Get all the EDITTrxHistory that has SegmentHistory.
        <br>2. For each of these rows:
        <br>&nbsp;&nbsp;&nbsp;a. Update the SegmentHistory with PrevLastAnniversaryDate From Segment fields EffectiveDate .
        <br>&nbsp;&nbsp;&nbsp;   or LastAnniversaryDate.
        <br>3. Update new fields in BucketHistory, PreviousLastRenewalDate with Bucket field, LastRenewalDate.
        </p>
        <p>
        Before this job can run successfuly run, the sql to update the SegmentHistory table must be applied to the database.
        Please backup the EDITSOLUTIONS data base to be used for this process before starting, in case a restore is
        needed.  The processing is done under a single transaction, so the records are committed as each row is processed.
        If there are errors, the process is aborted, only the offending row will be rollbacked.  You are notified of the
        offending issue. The database will have to be restored to its prior state if an error occurs
        and the job rerun from the beginning.
        <br>
        <br>Please change the connection information below in order to attach the correct table to this process.
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
