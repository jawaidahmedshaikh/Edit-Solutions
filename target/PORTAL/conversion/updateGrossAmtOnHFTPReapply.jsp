<%--
 *
 * User: dlataill
 * Date: Oct 9, 2006
 * Time: 10:10:30 AM
 *
 * (c) 2000 - 2006 Systems Engineering Group, LLC.  All rights Reserved
 *  Systems Engineering Group, LLC Propietary and confidential.  Any use
 *  subject to the license agreement.
--%>

<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@ page import="fission.utility.Util,
                 java.util.List,
                 java.util.ArrayList,
                 edit.common.EDITDate,
                 edit.common.vo.*,
                 java.util.Hashtable,
                 java.util.Enumeration,
                 edit.services.db.*,
                 java.lang.reflect.Array,
                 java.io.*,
                 edit.common.EDITBigDecimal,
                 event.dm.dao.DAOFactory,
                 contract.Segment,
                 event.FinancialHistory,
                 event.EDITTrxHistory,
                 java.sql.*"%>
<%
    String startProcess = Util.initString(request.getParameter("startProcess"), "false");

    if (startProcess.equals("true"))
    {
        CRUD crud = CRUDFactory.getSingleton().getCRUD(ConnectionFactory.EDITSOLUTIONS_POOL);

        List voInclusionList = new ArrayList();
        voInclusionList.add(EDITTrxHistoryVO.class);
        voInclusionList.add(FinancialHistoryVO.class);
        voInclusionList.add(InvestmentHistoryVO.class);

        int financialHistoryUpdateCount = 0;

        Connection conn = ConnectionFactory.getSingleton().getConnection(ConnectionFactory.EDITSOLUTIONS_POOL);
        conn.setAutoCommit(false);

        event.business.Event eventComponent = new event.component.EventComponent();

        List editTrxPKs = retrieveHFTPTransactions(conn);

        EDITTrxVO editTrxVO = null;
        EDITTrxHistoryVO editTrxHistoryVO = null;
        InvestmentHistoryVO[] investmentHistoryVOs = null;
        FinancialHistoryVO financialHistoryVO = null;

        for (int i = 0; i < editTrxPKs.size(); i++)
        {
            editTrxVO = eventComponent.composeEDITTrxVOByEDITTrxPK(Long.parseLong((String) editTrxPKs.get(i)), voInclusionList);
            editTrxHistoryVO = editTrxVO.getEDITTrxHistoryVO(0);
            financialHistoryVO = editTrxHistoryVO.getFinancialHistoryVO(0);
            investmentHistoryVOs = editTrxHistoryVO.getInvestmentHistoryVO();

            EDITDate highestValDate = null;
            EDITBigDecimal grossAmount = null;
            EDITDate currValDate = null;

            for (int j = 0; j < investmentHistoryVOs.length; j++)
            {
                if (investmentHistoryVOs[j].getToFromStatus() != null &&
                    investmentHistoryVOs[j].getToFromStatus().equalsIgnoreCase("F"))
                {
                    currValDate = new EDITDate(investmentHistoryVOs[j].getValuationDate());
                    if (highestValDate == null || currValDate.after(highestValDate))
                    {
                        highestValDate = currValDate;
                        grossAmount = new EDITBigDecimal(investmentHistoryVOs[j].getInvestmentDollars());
                    }
                }
            }

            if (grossAmount != null)
            {
                financialHistoryVO.setGrossAmount(grossAmount.getBigDecimal());
                financialHistoryUpdateCount += 1;
                crud.createOrUpdateVOInDB(financialHistoryVO);
            }
        }
        
        if (crud != null) crud.close();

        showSuccess(out, "HFTP Gross Amount Update Complete - " + financialHistoryUpdateCount + " FinancialHistory records updated");
    }
%>

<%!
    private List retrieveHFTPTransactions(Connection conn) throws Exception
    {
        DBTable EDITTRX_DBTABLE = DBTable.getDBTableForTable("EDITTrx");

        String EDITTRX_TABLENAME = EDITTRX_DBTABLE.getFullyQualifiedTableName();
        String transactionTypeCTCol = EDITTRX_DBTABLE.getDBColumn("TransactionTypeCT").getFullyQualifiedColumnName();
        String pendingStatusCol = EDITTRX_DBTABLE.getDBColumn("PendingStatus").getFullyQualifiedColumnName();
        String statusCol = EDITTRX_DBTABLE.getDBColumn("Status").getFullyQualifiedColumnName();
        String editTrxPKCol = EDITTRX_DBTABLE.getDBColumn("EDITTrxPK").getFullyQualifiedColumnName();

        String sql = "SELECT " + editTrxPKCol + " FROM " + EDITTRX_TABLENAME +
                     " WHERE " + transactionTypeCTCol + " = 'HFTP'" +
                     " AND " + pendingStatusCol + " IN ('L', 'H')" +
                     " AND " + statusCol + " IN ('A')";
    
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        List editTrxPKs = new ArrayList();

        try
        {
            ps = conn.prepareStatement(sql);
            
            rs = ps.executeQuery();
            while (rs.next())
            {
                editTrxPKs.add(rs.getLong("EDITTrxPK") + "");
            }
        }
        finally
        {
            if (ps != null) ps.close();
            if (rs != null) rs.close();
        }

        return editTrxPKs;
    }

    private void showSuccess(Writer out, String message) throws Exception
    {
        out.write("<span style='background-color:lightskyblue; width:100%'>");

        out.write("<hr>");

        out.write("<font face='' color='red'>");

        out.write(message);

        out.write("</font>");

        out.write("<hr>");

        out.write("</span>");
    }
%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>

        <h1>HFTP Gross Amount Update</h1>

        <p>
        This process will:
        <br>
        <br>1. Get all reapplied HFTP transactions
        <br>2. For each of these HFTPs, the GrossAmount on the FinancialHistory table will be set to the InvestmentDollars of the 'From' fund
        <br>&nbsp;&nbsp;&nbsp;from the InvestmentHistory table associated with the HFTP transaction with the most recent valuation date.
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
