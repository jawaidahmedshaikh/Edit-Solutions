<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>

<%@page import="fission.utility.*, java.sql.*, edit.services.db.hibernate.*, java.io.*, edit.services.db.*, edit.common.*,
                edit.common.vo.*,
                java.util.*,
                event.dm.dao.*,
                event.*,
                client.*,
                conversion.*,
                engine.*,
                engine.dm.*,
                engine.RateTable" %>

<%
    String startProcess = Util.initString(request.getParameter("startProcess"), "false");

    if (startProcess.equals("true"))
    {
        Connection conn = getConnection(request);
        conn.setAutoCommit(false);

        KeyGenerator.setOverrideKeyGenerator(true);

        HashMap countTable = new HashMap();

        try
        {

            countTable = convertCompanyStructure(conn);
            showSuccess(out, "Number of CompanyStructure records in = " + countTable.get("countIn") + " out = " + countTable.get("countOut"));

            countTable = convertTableDef(conn);
            showSuccess(out, "Number of TableDef records in = " + countTable.get("countIn") + " out = " + countTable.get("countOut"));
            showSuccess(out, "Number of TableKeys records in = " + countTable.get("TKcountIn") + " out = " + countTable.get("TKcountOut"));
            showSuccess(out, "Number of RateTable records in = " + countTable.get("RTcountIn") + " out = " + countTable.get("RTcountOut"));

            countTable = convertFund(conn);
            showSuccess(out, "Number of Fund records in = " + countTable.get("countIn") + " out = " + countTable.get("countOut"));
            showSuccess(out, "Number of FilteredFund records in = " + countTable.get("FFcountIn") + " out = " + countTable.get("FFcountOut"));


            countTable = convertCompanyFilteredFundStructure(conn);
            showSuccess(out, "Number of CompanyFilteredFundStructure records in = " + countTable.get("countIn") + " out = " + countTable.get("countOut"));

            countTable = createControlBalance(conn);
            showSuccess(out, "Number of ControlBalance records in = " + countTable.get("countIn") + " out = " + countTable.get("countOut"));

            SessionHelper.clearSessions();
            KeyGenerator.setOverrideKeyGenerator(false);

            countTable = convertUnitValues(conn, countTable);
            showSuccess(out, "Number of UnitValues records in = " + countTable.get("UVcountIn") + " out = " + countTable.get("UVcountOut"));

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
            SessionHelper.clearSessions();

            KeyGenerator.setOverrideKeyGenerator(false);

        }
    }
%>

<%!
   /**
    *  Get the ENGINE DATA
    */
    private HashMap convertCompanyStructure(Connection conn) throws Exception
    {
        HashMap countTable = new HashMap();
        int countIn = 0;
        int countOut = 0;
        ProductStructure companyStructure = null;

        Statement s = conn.createStatement();

        String sql = "SELECT * FROM ProductStructure";

        ResultSet rs = s.executeQuery(sql);

        SessionHelper.beginTransaction(SessionHelper.ENGINE);

        while (rs.next())
        {

            countIn++;
            companyStructure = new ProductStructure();
            companyStructure.setProductStructurePK(new Long(rs.getLong("ProductStructurePK")));
//            companyStructure.setCompanyName(Util.initString((String)rs.getString("CompanyName"), null));
            companyStructure.setMarketingPackageName(Util.initString((String)rs.getString("MarketingPackageName"), null));
            companyStructure.setGroupProductName(Util.initString((String)rs.getString("GroupProductName"), null));
            companyStructure.setAreaName(Util.initString((String)rs.getString("AreaName"), null));
            companyStructure.setBusinessContractName(Util.initString((String)rs.getString("BusinessContractName"), null));

            String operator = Util.initString((String)rs.getString("Operator"), null);
            if (operator != null && operator.equals(""))
            {
                operator = null;
            }
            companyStructure.setOperator(operator);

            companyStructure.setMaintDateTime(new EDITDateTime(Util.initString((String)rs.getString("MaintDateTime"), null)));
            companyStructure.setTypeCodeCT("Product");

            SessionHelper.save(companyStructure, SessionHelper.ENGINE);

            countOut++;
        }

        SessionHelper.commitTransaction(SessionHelper.ENGINE);

        s.close();
        rs.close();

        countTable.put("countIn", countIn);
        countTable.put("countOut", countOut);

        return countTable;
    }

    private HashMap convertFund(Connection conn) throws Exception
    {
        HashMap countTable = new HashMap();
        int countIn = 0;
        int countOut = 0;

        countTable.put("FFcountIn", countIn);
        countTable.put("FFcountOut", countOut);
        countTable.put("UVcountIn", countIn);
        countTable.put("UVcountOut", countOut);

        Fund fund = null;

        Statement s = conn.createStatement();

        String sql = "SELECT * From Fund";

        ResultSet rs = s.executeQuery(sql);


        while (rs.next())
        {
            countIn++;
            fund = new Fund();
            fund.setFundPK(new Long(rs.getLong("FundPK")));

            Integer codeTableKey = new Integer(rs.getInt("FundTypeCT"));
            String fundType = CommonDatabaseConversionFunctions.getCodeTableValue(codeTableKey);
            fund.setFundType(fundType);
            fund.setTypeCodeCT("Product");
            
            fund.setName(Util.initString((String)rs.getString("Name"), null));
            fund.setPortfolioNewMoneyStatusCT("Portfolio");

            SessionHelper.save(fund, SessionHelper.ENGINE);

            countTable = convertFilteredFund(conn, fund, countTable);


            SessionHelper.commitTransaction(SessionHelper.ENGINE);
            countOut++;

        }


        s.close();
        rs.close();

        countTable.put("countIn", countIn);
        countTable.put("countOut", countOut);

        return countTable;
    }

    private HashMap convertFilteredFund(Connection conn, Fund fund, HashMap countTable) throws Exception
    {
        int countIn = (Integer)countTable.get("FFcountIn");
        int countOut = (Integer)countTable.get("FFcountOut");

        FilteredFund filteredFund = null;

        Statement s = conn.createStatement();

        String sql = "SELECT * From FilteredFund WHERE FundFK = " + fund.getFundPK().longValue();

        ResultSet rs = s.executeQuery(sql);

        while (rs.next())
        {
            countIn++;
            filteredFund = new FilteredFund();
            filteredFund.setFilteredFundPK(new Long(rs.getLong("FilteredFundPK")));
            filteredFund.setFundFK(new Long(rs.getLong("FundFK")));
            filteredFund.setFundNumber(Util.initString((String)rs.getString("Number"), null));

            String effectiveDate = Util.initString((String)rs.getString("EffectiveDate"), null);
            if (effectiveDate != null)
            {
                effectiveDate = CommonDatabaseConversionFunctions.convertDate(effectiveDate);
                filteredFund.setEffectiveDate(new EDITDate(effectiveDate));
            }

            String terminationDate = Util.initString((String)rs.getString("TerminationDate"), null);
            if (terminationDate != null)
            {
                terminationDate = CommonDatabaseConversionFunctions.convertDate(terminationDate);
                filteredFund.setTerminationDate(new EDITDate(terminationDate));
            }

            Integer pricingDirectionKey = rs.getInt("PricingDirectionCT");
            String codeValue = CommonDatabaseConversionFunctions.getCodeTableValue(pricingDirectionKey);
            if (codeValue == null)
            {
                codeValue = "Backward";
            }
            filteredFund.setPricingDirection(codeValue);

            fund.addFilteredFund(filteredFund);
            SessionHelper.save(filteredFund, SessionHelper.ENGINE);

            countOut++;
        }


        s.close();
        rs.close();

        countTable.put("FFcountIn", countIn);
        countTable.put("FFcountOut", countOut);

        return countTable;
    }

    private HashMap convertUnitValues(Connection conn, HashMap countTable) throws Exception
    {
        int countIn = 0;
        int countOut = 0;
        int commitCount = 0;

        CRUD crud = CRUDFactory.getSingleton().getCRUD(ConnectionFactory.ENGINE_POOL);
        //Use this boolean to Insert rows with Keys
        crud.setRestoringRealTime(true);

        UnitValuesVO unitValuesVO = null;

        Statement s = conn.createStatement();

        String sql = "SELECT * From UnitValues";

        ResultSet rs = s.executeQuery(sql);

        while (rs.next())
        {
            crud.startTransaction();

            countIn++;
            unitValuesVO = new UnitValuesVO();
            long unitValuePK = new Long(rs.getLong("UnitValuesPK"));
            unitValuesVO.setUnitValuesPK(unitValuePK);
            long filteredFundFK = new Long(rs.getLong("FilteredFundFK"));
            unitValuesVO.setFilteredFundFK(filteredFundFK);

            String effectiveDate = Util.initString((String)rs.getString("EffectiveDate"), null);
            if (effectiveDate != null)
            {
                effectiveDate = CommonDatabaseConversionFunctions.convertDate(effectiveDate);
                if (effectiveDate != null)
                {
                    if (effectiveDate.equals("05/10/21"))
                    {
                         effectiveDate = "2005/10/21";
                    }
                    else if (effectiveDate.equals("06/9/6"))
                    {
                         effectiveDate = "2006/09/06";
                    }
                    else if (effectiveDate.equals("06/9/7"))
                    {
                         effectiveDate = "2006/09/07";
                    }
                    else
                    {
                        EDITDate effDate = new EDITDate(effectiveDate);
                        EDITDate minDate = new EDITDate("1753", "01", "01");
                        EDITDate maxDate = new EDITDate ("9999", "12", "31");
                        if (effDate.before(minDate) || effDate.after(maxDate))
                        {
                            System.out.println("UV INVALID EFF DATE = " + effectiveDate + "KEY = " + unitValuePK);
                            effectiveDate = null;
                        }
                    }
                }
                unitValuesVO.setEffectiveDate(effectiveDate);
            }

            unitValuesVO.setUnitValue(rs.getBigDecimal("UnitValue"));
            unitValuesVO.setAnnuityUnitValue(rs.getBigDecimal("AnnuityUnitValue"));

            crud.createOrUpdateVOInDB(unitValuesVO);

            countOut++;
            commitCount++;

            crud.commitTransaction();
        }

        crud.setRestoringRealTime(false);

        s.close();
        rs.close();
        crud.close();

        countTable.put("UVcountIn", countIn);
        countTable.put("UVcountOut", countOut);

        return countTable;
    }


    private HashMap convertCompanyFilteredFundStructure(Connection conn) throws Exception
    {
        HashMap countTable = new HashMap();
        int countIn = 0;
        int countOut = 0;
        ProductFilteredFundStructure companyFilteredFundStructure = null;

        Statement s = conn.createStatement();

        String sql = "SELECT * From ProductFilteredFundStructure";

        ResultSet rs = s.executeQuery(sql);

        SessionHelper.beginTransaction(SessionHelper.ENGINE);

        while (rs.next())
        {
            countIn++;
            companyFilteredFundStructure = new ProductFilteredFundStructure();
            long companyFilteredFundStructurePK = new Long(rs.getLong("ProductFilteredFundStructurePK"));
            companyFilteredFundStructure.setProductFilteredFundStructurePK(new Long(companyFilteredFundStructurePK));
            Long companyStructurePK = new Long(rs.getLong("ProductStructureFK"));
            companyFilteredFundStructure.setProductStructureFK(companyStructurePK);

            Long filteredFundPK = new Long(rs.getLong("FilteredFundFK"));
            companyFilteredFundStructure.setFilteredFundFK(filteredFundPK);

            SessionHelper.save(companyFilteredFundStructure, SessionHelper.ENGINE);


            countOut++;
        }

        SessionHelper.commitTransaction(SessionHelper.ENGINE);

        s.close();
        rs.close();

        countTable.put("countIn", countIn);
        countTable.put("countOut", countOut);

        return countTable;
    }

    private HashMap createControlBalance(Connection conn) throws Exception
    {
        HashMap countTable = new HashMap();
        ControlBalanceVO controlBalanceVO = null;

        int countIn = 0;
        int countOut = 0;

        Statement s = conn.createStatement();

        String sql = "SELECT * From ProductFilteredFundStructure";

        ResultSet rs = s.executeQuery(sql);

        CRUD crud = CRUDFactory.getSingleton().getCRUD(ConnectionFactory.ENGINE_POOL);
        crud.startTransaction();

        while (rs.next())
        {
            countIn++;
            controlBalanceVO = new ControlBalanceVO();
            long companyFilteredFundStructurePK = new Long(rs.getLong("ProductFilteredFundStructurePK"));
            controlBalanceVO.setControlBalancePK(0);

            controlBalanceVO.setProductFilteredFundStructureFK(new Long(companyFilteredFundStructurePK));

            controlBalanceVO.setEndingDollarBalance(rs.getBigDecimal("EndingDollarBalance"));
            controlBalanceVO.setEndingUnitBalance(rs.getBigDecimal("EndingUnitBalance"));

            String endingCycleDate = Util.initString((String)rs.getString("EndingBalanceCycleDate"), null);
            if (endingCycleDate != null)
            {
                endingCycleDate = CommonDatabaseConversionFunctions.convertDate(endingCycleDate);
                controlBalanceVO.setEndingBalanceCycleDate(endingCycleDate);
            }

            crud.createOrUpdateVOInDB(controlBalanceVO);
            countOut++;
         }

         crud.commitTransaction();

         s.close();
         rs.close();
         crud.close();

         countTable.put("countIn", countIn);
         countTable.put("countOut", countOut);

         return countTable;
    }

    private HashMap convertInterestRateParameters(Connection conn, CRUD crud)  throws Exception
    {
        HashMap countTable = new HashMap();
        int countIn = 0;
        int countOut = 0;
        InterestRateParametersVO interestRateParametersVO = null;

        Statement s = conn.createStatement();

        String sql = "SELECT * From InterestRateParameters";

        ResultSet rs = s.executeQuery(sql);

        while (rs.next())
        {
            countIn++;
            interestRateParametersVO = new InterestRateParametersVO();
            interestRateParametersVO.setInterestRateParametersPK(new Long(rs.getLong("InterestRateParametersPK")));
            interestRateParametersVO.setFilteredFundFK(new Long(rs.getLong("FilteredFundFK")));
            String originalDate = Util.initString((String)rs.getString("OriginalDate"), null);
            if (originalDate.equals(CommonDatabaseConversionFunctions.MIN_DEFAULT_DATE))
            {
                originalDate = null;
            }

            if (originalDate.equals(CommonDatabaseConversionFunctions.MAX_DEFAULT_DATE))
            {
                originalDate = EDITDate.DEFAULT_MAX_DATE;
            }
            interestRateParametersVO.setOriginalDate(originalDate);

            Integer codeTableKey = rs.getInt("OptionCT");
            String codeValue = CommonDatabaseConversionFunctions.getCodeTableValue(codeTableKey);
            interestRateParametersVO.setOptionCT(codeValue);

            crud.createOrUpdateVOInDB(interestRateParametersVO);
            countOut++;
        }

        s.close();
        rs.close();

        countTable.put("countIn", countIn);
        countTable.put("countOut", countOut);

        return countTable;
    }

    private HashMap convertInterestRate(Connection conn, CRUD crud) throws Exception
    {
        HashMap countTable = new HashMap();
        int countIn = 0;
        int countOut = 0;
        InterestRateVO interestRateVO = null;

        Statement s = conn.createStatement();

        String sql = "SELECT * From InterestRate";

        ResultSet rs = s.executeQuery(sql);

        while (rs.next())
        {
            countIn++;
            interestRateVO = new InterestRateVO();
            interestRateVO.setInterestRatePK(new Long(rs.getLong("InterestRatePK")));
            interestRateVO.setInterestRateParametersFK(new Long(rs.getLong("InterestRateParametersFK")));
            String effectiveDate = Util.initString((String)rs.getString("EffectiveDate"), null);
            if (effectiveDate.equalsIgnoreCase(CommonDatabaseConversionFunctions.MIN_DEFAULT_DATE))
            {
                effectiveDate = null;
            }
            interestRateVO.setEffectiveDate(effectiveDate);

            interestRateVO.setGuaranteeDuration(Util.initString((String)rs.getString("GuaranteeDuration"), null));
            interestRateVO.setRate(rs.getBigDecimal("Rate"));

            crud.createOrUpdateVOInDB(interestRateVO);
            countOut++;
        }

        s.close();
        rs.close();

        countTable.put("countIn", countIn);
        countTable.put("countOut", countOut);

        return countTable;
    }


    private HashMap convertTableDef(Connection conn)  throws Exception
    {
        HashMap countTable = new HashMap();
        int countIn = 0;
        int countOut = 0;
        countTable.put("TKcountIn", countIn);
        countTable.put("TKcountOut", countOut);
        countTable.put("RTcountIn", countIn);
        countTable.put("RTcountOut", countOut);

        TableDef tableDef = null;

        Statement s = conn.createStatement();

        String sql = "SELECT * From TableDef WHERE NOT TableDefPK = 0";

        ResultSet rs = s.executeQuery(sql);


        while (rs.next())
        {
         SessionHelper.beginTransaction(SessionHelper.ENGINE);
            countIn++;
            tableDef = new TableDef();
            tableDef.setTableDefPK(new Long(rs.getLong("TableDefPK")));
            tableDef.setTableName(Util.initString((String)rs.getString("TableName"), null));
            tableDef.setAccessType(Util.initString((String)rs.getString("AccessType"), null));
            tableDef.setOperator(Util.initString((String)rs.getString("Operator"), null));
            tableDef.setLockDateTime(new EDITDateTime(Util.initString((String)rs.getString("LockDateTime"), null)));
            tableDef.setMaintDateTime(new EDITDateTime(Util.initString((String)rs.getString("MaintDateTime"), null)));

            SessionHelper.save(tableDef, SessionHelper.ENGINE);
            countTable = convertTableKeys(conn, tableDef, countTable);


            countOut++;
         SessionHelper.commitTransaction(SessionHelper.ENGINE);
        }


        s.close();
        rs.close();

        countTable.put("countIn", countIn);
        countTable.put("countOut", countOut);

        return countTable;
    }

    private HashMap convertTableKeys(Connection conn, TableDef tableDef, HashMap countTable) throws Exception
    {
        int countIn = (Integer)countTable.get("TKcountIn");
        int countOut = (Integer)countTable.get("TKcountOut");

        CodeTableWrapper codeTableWrapper = CodeTableWrapper.getSingleton();

        TableKeys tableKeys = null;

        Statement s = conn.createStatement();

        String sql = "SELECT * From TableKeys WHERE TableDefFK = " + tableDef.getTableDefPK().longValue();

        ResultSet rs = s.executeQuery(sql);

        while (rs.next())
        {
            countIn++;
            tableKeys = new TableKeys();
            tableKeys.setTableKeysPK(new Long(rs.getLong("TableKeysPK")));
            tableKeys.setTableDefFK(new Long(rs.getLong("TableDefFK")));

            String effectiveDate = Util.initString((String)rs.getString("EffectiveDate"), null);
            if (effectiveDate != null)
            {
                effectiveDate = CommonDatabaseConversionFunctions.convertDate(effectiveDate);
                tableKeys.setEffectiveDate(new EDITDate(effectiveDate));
            }

            tableKeys.setBandAmount(new EDITBigDecimal(rs.getBigDecimal("BandAmount")));
            tableKeys.setUserKey(Util.initString((String)rs.getString("UserKey"), null));

            String codeValue = codeTableWrapper.getCodeTableEntry(rs.getInt("GenderCT")).getCode();
            tableKeys.setGender(codeValue);

            codeValue = codeTableWrapper.getCodeTableEntry(rs.getInt("ClassCT")).getCode();
            tableKeys.setClassType(codeValue);

            codeValue = codeTableWrapper.getCodeTableEntry(rs.getInt("StateCT")).getCode();
            tableKeys.setState(codeValue);

            codeValue = codeTableWrapper.getCodeTableEntry(rs.getInt("TableTypeCT")).getCode();
            tableKeys.setTableType(codeValue);

            tableDef.add(tableKeys);
            SessionHelper.save(tableKeys, SessionHelper.ENGINE);

            countOut++;

            countTable = convertRateTable(conn, tableKeys, countTable);
        }


        s.close();
        rs.close();

        countTable.put("TKcountIn", countIn);
        countTable.put("TKcountOut", countOut);

        return countTable;
    }

    private HashMap convertRateTable(Connection conn, TableKeys tableKeys, HashMap countTable) throws Exception
    {
        int countIn = (Integer)countTable.get("RTcountIn");
        int countOut = (Integer)countTable.get("RTcountOut");
        RateTable rateTable = null;

        Statement s = conn.createStatement();

        String sql = "SELECT * From RateTable WHERE TableKeysFK = " + tableKeys.getTableKeysPK().longValue();

        ResultSet rs = s.executeQuery(sql);

        while (rs.next())
        {
            countIn++;
            rateTable = new RateTable();
            rateTable.setRateTablePK(new Long(rs.getLong("RateTablePK")));
            rateTable.setTableKeysFK(new Long(rs.getLong("TableKeysFK")));
            rateTable.setAge(rs.getInt("Age"));
            rateTable.setDuration(rs.getInt("Duration"));
            rateTable.setRate(new EDITBigDecimal(rs.getBigDecimal("Rate")));

            tableKeys.add(rateTable);
            SessionHelper.save(rateTable, SessionHelper.ENGINE);

            countOut++;
        }


        s.close();
        rs.close();

        countTable.put("RTcountIn", countIn);
        countTable.put("RTcountOut", countOut);

        return countTable;
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

        <h1>Convert Prior Version of ENGINE to the NEW ENGINE </h1>

        <p>
        This process will:
        <br>
        <br>1. Read each row of the Prior Version of a defined ENGINE table.
        <br>2. For each of these rows of each table:
        <br>&nbsp;&nbsp;&nbsp;a. Set the like fields into the "VO" definition of the table.
        <br>&nbsp;&nbsp;&nbsp;b. Convert codetable keys to the current code values.
        <br>&nbsp;&nbsp;&nbsp;c. Convert Minimum and Maximum Default date to valid dates. The database requires valid dates.
        <br>&nbsp;&nbsp;&nbsp;d. Insert this data into the current ENGINE database table.
        </p>
        <p>
        Please backup the NEW ENGINE data base to be used for this process before starting, in case a restore is
        needed.
        If there are errors, the process is aborted, only the offending row will be rollbacked.  You are notified of the
        offending issue. The database will have to be restored to its prior state if an error occurs
        and the job rerun from the beginning.
        Your Environment is connected to the NEW ENGINE that is being populated, through the Config File.  It has been loaded with only the
        ScriptInstruction table.  This job will populate the following tables: ProductStructure, Fund, FilteredFund,
        ProductFilteredFundStructure, RateTable, TableDef, TableKeys and UnitValues.
        <br>
        <br>Please change the connection information below in order to attach the Prior ENGINE table to this process.
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
<%--                        <td><input type="text" name="url" value="jdbc:jtds:sqlserver://SEG-DATABASE:1433/ENGINE_RX" size="100" /></td>--%>
                         <td><input type="text" name="url" value="jdbc:jtds:sqlserver://localhost:1433;DatabaseName=ENGINE" size="100" /></td>
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
