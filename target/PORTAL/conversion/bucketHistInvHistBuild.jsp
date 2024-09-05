<%--
 *
 * User: dlataill
 * Date: Jul 31, 2006
 * Time: 9:01:30 AM
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
                 java.sql.Connection,
                 java.sql.PreparedStatement,
                 java.sql.SQLException,
                 java.sql.ResultSet,
                 edit.services.db.*,
                 java.lang.reflect.Array,
                 java.io.*,
                 edit.common.EDITBigDecimal,
                 event.dm.dao.DAOFactory"%>
<%
    String startProcess = Util.initString(request.getParameter("startProcess"), "false");

    CRUD crud = CRUDFactory.getSingleton().getCRUD(ConnectionFactory.EDITSOLUTIONS_POOL);
    
    if (startProcess.equals("true"))
    {
        List voInclusionList = new ArrayList();
        voInclusionList.add(ChargeCodeVO.class);
        
        List contractVOInclusionList = new ArrayList();
        contractVOInclusionList.add(InvestmentVO.class);
        contractVOInclusionList.add(BucketVO.class);
        
        engine.business.Lookup engineLookup = new engine.component.LookupComponent();
        contract.business.Lookup contractLookup = new contract.component.LookupComponent();
        
        FilteredFundVO[] filteredFundVOs = engineLookup.getAllHedgeFunds();                         

        Hashtable segmentHT = new Hashtable();
        
        for (int i = 0; i < filteredFundVOs.length; i++)
        {
            long filteredFundPK = filteredFundVOs[i].getFilteredFundPK();

            SegmentVO[] segmentVOs = contractLookup.composeSegmentVOByFund(filteredFundPK, contractVOInclusionList);

            if (segmentVOs != null)
            {
                for (int j = 0; j < segmentVOs.length; j++)
                {
                    InvestmentVO hfInvestmentVO = getHFInvestmentVO(segmentVOs[j], filteredFundPK);

                    if (!segmentHT.containsKey(segmentVOs[j].getSegmentPK() + ""))
                    {
                        segmentVOs[j].removeAllInvestmentVO();
                        segmentVOs[j].addInvestmentVO(hfInvestmentVO);
                        segmentHT.put(segmentVOs[j].getSegmentPK() + "", segmentVOs[j]);
                    }
                    else
                    {
                        SegmentVO segmentVO = (SegmentVO) segmentHT.get(segmentVOs[j].getSegmentPK() + "");
                        segmentVO.addInvestmentVO(hfInvestmentVO);
                        segmentHT.put(segmentVO.getSegmentPK() + "", segmentVO);
                    }
                }
            }
        }

        if (segmentHT.size() > 0)
        {
            Enumeration enum1 = segmentHT.keys();

            while (enum1.hasMoreElements())
            {
                String segmentPK = (String) enum1.nextElement();
                SegmentVO segmentVO = (SegmentVO) segmentHT.get(segmentPK);
                InvestmentVO[] investmentVOs = segmentVO.getInvestmentVO();
                for (int i = 0; i < investmentVOs.length; i++)
                {
                    long filteredFundFK = investmentVOs[i].getFilteredFundFK();
                    long chargeCodeFK = investmentVOs[i].getChargeCodeFK();

                    UnitValuesVO[] unitValuesVO = engineLookup.getUnitValuesByFilteredFundIdDateChargeCode(filteredFundFK,
                                                                                                           new EDITDate().getFormattedDate(),
                                                                                                           "Hedge",
                                                                                                           chargeCodeFK);

                    if (unitValuesVO != null)
                    {
                        EDITTrxVO[] editTrxVOs = getHistoryForSegment_Investment(Long.parseLong(segmentPK), unitValuesVO[0].getEffectiveDate());

                        if (editTrxVOs != null)
                        {
                            BucketVO[] bucketVOs = investmentVOs[i].getBucketVO();
                            generateHistory(bucketVOs, editTrxVOs, unitValuesVO[0].getEffectiveDate(), chargeCodeFK, crud, segmentVO.getContractNumber(), out);
                        }
                    }
                }
            }
        }
        
        if (crud != null) crud.close();

        showSuccess(out, "History Generation Complete");
    }
%>

<%!
    private InvestmentVO getHFInvestmentVO(SegmentVO segmentVO, long filteredFundFK)
    {
        InvestmentVO[] investmentVOs = segmentVO.getInvestmentVO();
        
        InvestmentVO hfInvestmentVO = null;
        
        for (int i = 0; i < investmentVOs.length; i++)
        {
            if (investmentVOs[i].getFilteredFundFK() == filteredFundFK)
            {
                hfInvestmentVO = investmentVOs[i];
                break;
            }
        }
        
        return hfInvestmentVO;
    }
    
    private void generateHistory(BucketVO[] bucketVOs, 
                                 EDITTrxVO[] editTrxVOs, 
                                 String uvEffDate, 
                                 long chargeCodeFK,
                                 CRUD crud,
                                 String contractNumber,
                                 Writer out) throws Exception
    {
        boolean investmentHistoryCreated = false;

        for (int i = 0; i < editTrxVOs.length; i++)
        {
            EDITDate trxEffDate = new EDITDate(editTrxVOs[i].getEffectiveDate());

            investmentHistoryCreated = false;

            for (int j = 0; j < bucketVOs.length; j++)
            {
                EDITDate depositDate = new EDITDate(bucketVOs[j].getDepositDate());

                if ((depositDate.before(trxEffDate) || depositDate.equals(trxEffDate)) &&
                    new EDITBigDecimal(bucketVOs[j].getCumUnits()).isGT("0"))
                {
                    try
                    {
                        crud.startTransaction();

                        EDITTrxHistoryVO[] editTrxHistoryVO = DAOFactory.getEDITTrxHistoryDAO().findByEditTrxPK(editTrxVOs[i].getEDITTrxPK());

                        if (!investmentHistoryCreated)
                        {
                            InvestmentHistoryVO invHistVO = new InvestmentHistoryVO();
                            invHistVO.setInvestmentHistoryPK(0);
                            invHistVO.setEDITTrxHistoryFK(editTrxHistoryVO[0].getEDITTrxHistoryPK());
                            invHistVO.setInvestmentFK(bucketVOs[j].getInvestmentFK());
                            invHistVO.setInvestmentDollars(new EDITBigDecimal("0").getBigDecimal());
                            invHistVO.setInvestmentUnits(new EDITBigDecimal("0").getBigDecimal());
                            invHistVO.setToFromStatus("F");
                            invHistVO.setValuationDate(uvEffDate);
                            invHistVO.setPreviousChargeCodeFK(chargeCodeFK);
                            invHistVO.setChargeCodeFK(chargeCodeFK);

                            crud.createOrUpdateVOInDB(invHistVO);

                            investmentHistoryCreated = true;
                        }

                        List prevInfoList = findPriorBucketInfoByBucketFK(bucketVOs[j].getBucketPK(), editTrxVOs[i].getEffectiveDate());

                        EDITBigDecimal prevValue = new EDITBigDecimal();
                        String prevValuationDate = null;

                        if (prevInfoList.size() > 0)
                        {
                            prevValue = (EDITBigDecimal) prevInfoList.get(0);
                            prevValuationDate = (String) prevInfoList.get(1);
                        }

                        BucketHistoryVO bucketHistVO = new BucketHistoryVO();
                        bucketHistVO.setBucketHistoryPK(0);
                        bucketHistVO.setBucketFK(bucketVOs[j].getBucketPK());
                        bucketHistVO.setEDITTrxHistoryFK(editTrxHistoryVO[0].getEDITTrxHistoryPK());
                        bucketHistVO.setDollars(new EDITBigDecimal("0").getBigDecimal());
                        bucketHistVO.setUnits(new EDITBigDecimal("0").getBigDecimal());
                        bucketHistVO.setCumUnits(prevValue.getBigDecimal());
                        bucketHistVO.setToFromStatus("F");
                        bucketHistVO.setPreviousValue(prevValue.getBigDecimal());
                        bucketHistVO.setPreviousValuationDate(prevValuationDate);

                        crud.createOrUpdateVOInDB(bucketHistVO);

                        crud.commitTransaction();
                    }
                    catch (Exception e)
                    {
                        if (crud != null) crud.rollbackTransaction();

                        showError(out, contractNumber, editTrxVOs[i].getEffectiveDate(), e); 
                    }
                }
            }
        }
    }

    private List findPriorBucketInfoByBucketFK(long bucketFK, String trxEffectiveDate)
    {
        int mdPriority = 270;

        List priorBucketInfo = new ArrayList();

        DBTable BUCKET_HISTORY_DBTABLE = DBTable.getDBTableForTable("BucketHistory");
        String BUCKET_HISTORY_TABLENAME = BUCKET_HISTORY_DBTABLE.getFullyQualifiedTableName();

        DBTable EDITTRXHISTORY_DBTABLE = DBTable.getDBTableForTable("EDITTrxHistory");
        String EDITTRXHISTORY_TABLENAME = EDITTRXHISTORY_DBTABLE.getFullyQualifiedTableName();

        DBTable EDITTRX_DBTABLE = DBTable.getDBTableForTable("EDITTrx");
        String EDITTRX_TABLENAME = EDITTRX_DBTABLE.getFullyQualifiedTableName();

        DBTable TRXPRIORITY_DBTABLE = DBTable.getDBTableForTable("TransactionPriority");
        String TRXPRIORITY_TABLENAME = TRXPRIORITY_DBTABLE.getFullyQualifiedTableName();

        String bucketFKCol = BUCKET_HISTORY_DBTABLE.getDBColumn("BucketFK").getFullyQualifiedColumnName();
        String editTrxHistoryFKCol = BUCKET_HISTORY_DBTABLE.getDBColumn("EDITTrxHistoryFK").getFullyQualifiedColumnName();
        String cumUnitsCol = BUCKET_HISTORY_DBTABLE.getDBColumn("CumUnits").getFullyQualifiedColumnName();
        String previousValDateCol = BUCKET_HISTORY_DBTABLE.getDBColumn("PreviousValuationDate").getFullyQualifiedColumnName();

        String editTrxHistoryPKCol = EDITTRXHISTORY_DBTABLE.getDBColumn("EDITTrxHistoryPK").getFullyQualifiedColumnName();
        String editTrxFKCol = EDITTRXHISTORY_DBTABLE.getDBColumn("EDITTrxFK").getFullyQualifiedColumnName();

        String editTrxPKCol = EDITTRX_DBTABLE.getDBColumn("EDITTrxPK").getFullyQualifiedColumnName();
        String effectiveDateCol = EDITTRX_DBTABLE.getDBColumn("EffectiveDate").getFullyQualifiedColumnName();
        String transactionTypeCTCol = EDITTRX_DBTABLE.getDBColumn("TransactionTypeCT").getFullyQualifiedColumnName();

        String trxPriority_TrxTypeCTCol = TRXPRIORITY_DBTABLE.getDBColumn("TransactionTypeCT").getFullyQualifiedColumnName();
        String priorityCol = TRXPRIORITY_DBTABLE.getDBColumn("Priority").getFullyQualifiedColumnName();

        String sql = " SELECT " + cumUnitsCol + ", " + effectiveDateCol + ", " + priorityCol + ", " +
                     editTrxHistoryPKCol + ", " + previousValDateCol +
                     " FROM " + BUCKET_HISTORY_TABLENAME +
                     " JOIN " + EDITTRXHISTORY_TABLENAME +
                     " ON " + editTrxHistoryFKCol + " = " + editTrxHistoryPKCol +
                     " JOIN " + EDITTRX_TABLENAME +
                     " ON " + editTrxFKCol + " = " + editTrxPKCol +
                     " JOIN " + TRXPRIORITY_TABLENAME +
                     " ON " + transactionTypeCTCol + " = " + trxPriority_TrxTypeCTCol +
                     " WHERE " + bucketFKCol + " = " + bucketFK +
                     " AND (" + effectiveDateCol + " < ?" +
                     " OR (" + effectiveDateCol + " = ?" +
                     " AND " + priorityCol + " < " + mdPriority + "))" +
                     " ORDER BY " + previousValDateCol + "DESC, " + editTrxHistoryPKCol + " DESC";

        Connection conn = null;

        PreparedStatement ps = null;

        ResultSet rs = null;

        try
        {
            conn = ConnectionFactory.getSingleton().getConnection(ConnectionFactory.EDITSOLUTIONS_POOL);

            ps = conn.prepareStatement(sql);

            ps.setDate(1, DBUtil.convertStringToDate(trxEffectiveDate));
            ps.setDate(2, DBUtil.convertStringToDate(trxEffectiveDate));

            rs = ps.executeQuery();

            int i = 1;

            while (i == 1)
            {
                if (rs.next())
                {
                    priorBucketInfo.add(0, new EDITBigDecimal(rs.getBigDecimal("CumUnits")));
                    priorBucketInfo.add(1, DBUtil.readAndConvertDate(rs, "EffectiveDate"));
                    i = 0;
                }
            }
        }
        catch (Exception e)
        {
            System.out.println(e);

            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.

            throw new RuntimeException(e);
        }
        finally
        {
            try
            {
                if (ps != null) ps.close();
                if (conn != null) conn.close();
                if (rs != null) rs.close();
            }
            catch (SQLException e)
            {
                System.out.println(e);

                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.

                throw new RuntimeException(e);
            }
        }

        return priorBucketInfo;
    }

    private EDITTrxVO[] getHistoryForSegment_Investment(long segmentFK, String drivingEffDate)
    {
        EDITTrxVO[] editTrxVOs = null;
        
        DBTable EDITTRX_DBTABLE = DBTable.getDBTableForTable("EDITTrx");
        DBTable CLIENT_SETUP_DBTABLE = DBTable.getDBTableForTable("ClientSetup");
        DBTable CONTRACT_SETUP_DBTABLE = DBTable.getDBTableForTable("ContractSetup");
    
        String EDITTRX_TABLENAME = EDITTRX_DBTABLE.getFullyQualifiedTableName();
        String CLIENT_SETUP_TABLENAME = CLIENT_SETUP_DBTABLE.getFullyQualifiedTableName();
        String CONTRACT_SETUP_TABLENAME = CONTRACT_SETUP_DBTABLE.getFullyQualifiedTableName();
        
        String clientSetupFKCol = EDITTRX_DBTABLE.getDBColumn("ClientSetupFK").getFullyQualifiedColumnName();
        String effectiveDateCol = EDITTRX_DBTABLE.getDBColumn("EffectiveDate").getFullyQualifiedColumnName();
        String pendingStatusCol = EDITTRX_DBTABLE.getDBColumn("PendingStatus").getFullyQualifiedColumnName();
        String statusCol = EDITTRX_DBTABLE.getDBColumn("Status").getFullyQualifiedColumnName();
        String transactionTypeCTCol = EDITTRX_DBTABLE.getDBColumn("TransactionTypeCT").getFullyQualifiedColumnName();
        
        String clientSetupPKCol = CLIENT_SETUP_DBTABLE.getDBColumn("ClientSetupPK").getFullyQualifiedColumnName();
        String contractSetupFKCol = CLIENT_SETUP_DBTABLE.getDBColumn("ContractSetupFK").getFullyQualifiedColumnName();
        
        String contractSetupPKCol = CONTRACT_SETUP_DBTABLE.getDBColumn("ContractSetupPK").getFullyQualifiedColumnName();
        String segmentFKCol = CONTRACT_SETUP_DBTABLE.getDBColumn("SegmentFK").getFullyQualifiedColumnName();
        
        String sql = "SELECT " + EDITTRX_TABLENAME + ".* FROM " + EDITTRX_TABLENAME + ", " +
                     CLIENT_SETUP_TABLENAME + ", " + CONTRACT_SETUP_TABLENAME +
                     " WHERE " + clientSetupFKCol + " = " + clientSetupPKCol +
                     " AND " + contractSetupFKCol + " = " + contractSetupPKCol +
                     " AND " + segmentFKCol + " = ?" +
                     " AND " + pendingStatusCol + " = 'H'" +
                     " AND " + effectiveDateCol + " > ?" +
                     " AND " + statusCol + " IN ('A', 'N')" +
                     " AND " + transactionTypeCTCol + " = 'MD'";
    
        Connection conn = null;
    
        PreparedStatement ps = null;
    
        try
        {
            conn = ConnectionFactory.getSingleton().getConnection(ConnectionFactory.EDITSOLUTIONS_POOL);
    
            ps = conn.prepareStatement(sql);
    
            ps.setLong(1, segmentFK);
            ps.setDate(2, DBUtil.convertStringToDate(drivingEffDate));
    
            editTrxVOs = (EDITTrxVO[]) executeQuery(EDITTrxVO.class,
                                                    ps,
                                                    ConnectionFactory.EDITSOLUTIONS_POOL,
                                                    false,
                                                    null);
        }
        catch (Exception e)
        {
            System.out.println(e);
    
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
    
            throw new RuntimeException(e);
        }
        finally
        {
            try
            {
                if (ps != null) ps.close();
                if (conn != null) conn.close();
            }
            catch (SQLException e)
            {
                System.out.println(e);
    
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
    
                throw new RuntimeException(e);
            }
        }
    
        return editTrxVOs;
    }
    
    private Object[] executeQuery(Class targetVOClass,
                                  PreparedStatement ps,
                                  String poolName,
                                  boolean includeChildVOs,
                                  List childVOsExclusionList)
    {
        Object[] valueObjects = null;
    
        ResultSet rs = null;
    
        try
        {
    
            Clock clock1 = new Clock();
            // if Clock.KEEP_STATISTICS is false
            // the start and stop will have little impact
    
            rs = ps.executeQuery();
    
            valueObjects = processResultSet(targetVOClass, rs, poolName, includeChildVOs, childVOsExclusionList);
    
            clock1.stop(valueObjects);
    
        }
        catch (SQLException e)
        {
            System.out.println(e);
    
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
    
            throw new RuntimeException(e);
        }
        finally
        {
            try
            {
                if (ps != null) ps.close();
            }
            catch (SQLException e)
            {
                System.out.println(e);
    
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
    
                throw new RuntimeException(e);
            }
        }
    
        return valueObjects;
    }
    
    private Object[] processResultSet(Class targetVOClass, 
                                      ResultSet rs, 
                                      String poolName, 
                                      boolean includeChildVOs, 
                                      List childVOsExclusionList)
    {
        Object valueObjects = null;
    
        List results = new ArrayList();
    
        CRUD crud = null;
    
        try
        {
            if (includeChildVOs)
            {
                crud = CRUDFactory.getSingleton().getCRUD(poolName);
            }
    
            while (rs.next())
            {
                Object parentVO = CRUD.populateVOFromResultSetRow(rs, VOClass.getVOClassMetaData(targetVOClass));
    
                if (includeChildVOs)
                {
                    crud.retrieveVOFromDBRecursively(parentVO, childVOsExclusionList, false);
    
                    results.add(parentVO);
                }
                else
                {
                    results.add(parentVO);
                }
            }
    
            if (results.size() == 0)
            {
                valueObjects = null;
            }
            else
            {
                valueObjects = Array.newInstance(targetVOClass, results.size());
    
                int resultsSize = results.size();
    
                for (int i = 0; i < resultsSize; i++)
                {
                    Array.set(valueObjects, i, results.get(i));
                }
            }
        }
        catch (Exception e)
        {
            System.out.println(e);
    
            e.printStackTrace();
    
            throw new RuntimeException(e);
        }
        finally
        {
            try
            {
                if (rs != null) rs.close();
            }
            catch (SQLException e)
            {
                System.out.println(e);
    
                e.printStackTrace();  //To change body of catch statement use Options | File Templates.
    
                throw new RuntimeException(e);
            }
    
            if (crud != null) crud.close();
        }
    
        if (valueObjects != null)
        {
            return (Object[]) valueObjects;
        }
        else
        {
            return null;
        }
    }

    private void showError(Writer out, String contractNumber, String effectiveDate, Exception e) throws Exception
    {
        out.write("<span style='background-color:yellow; width:100%'>");

        out.write("<hr>");

        out.write("Investment/Bucket History could not be created for MD effective " + effectiveDate + " on contract " + contractNumber + " for the following reason(s):<br><br>");

        out.write("<font face='' color='red'>");

        out.write(e.getMessage());

        out.write("</font>");

        out.write("<hr>");

        out.write("</span>");
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

        <h1>Build Hedge Fund Bucket History and Investment History for MD's</h1>

        <p>
        This process will:
        <br>
        <br>1. Get all the MD transactions that meet the following criteria:
        <br>&nbsp;&nbsp;&nbsp;a. PendingStatus is 'H'.
        <br>&nbsp;&nbsp;&nbsp;b. Hedge fund is defined for contract on which the MD was processed.
        <br>&nbsp;&nbsp;&nbsp;c. Effective date is greater than LAST UV effective date for hedge fund.
        <br>2. For each of these MDs:
        <br>&nbsp;&nbsp;&nbsp;a. Create an investment history for each hedge fund defined on the contract.
        <br>&nbsp;&nbsp;&nbsp;b. Create a bucket history for each bucket where cum units is greater than zero and the
        <br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;bucket deposit date is less than or equal to the MD effective date.
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
