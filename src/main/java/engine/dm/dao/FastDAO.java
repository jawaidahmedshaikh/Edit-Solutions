/*
 * Created by IntelliJ IDEA.
 * User: gfrosti
 * Date: Feb 26, 2003
 * Time: 12:23:29 PM
 *
 * (c) 2000-2004 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */

package engine.dm.dao;

import edit.common.vo.*;
import edit.services.db.*;
import fission.utility.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.math.*;



public class FastDAO extends AbstractFastDAO
{
    private final String POOLNAME;

    private final DBTable RULES_DBTABLE;
    private final DBTable PRODUCT_RULE_STRUCTURE_DBTABLE;
    private final DBTable PRODUCT_STRUCTURE_DBTABLE;
    private final DBTable TABLE_KEYS_DBTABLE;
    private final DBTable RATE_TABLE_DBTABLE;
    private final DBTable FUND_DBTABLE;
    private final DBTable COMPANY_DBTABLE;

    private final String RULES_TABLENAME;
    private final String PRODUCT_RULE_STRUCTURE_TABLENAME;
    private final String PRODUCT_STRUCTURE_TABLENAME;
    private final String TABLE_KEYS_TABLENAME;
    private final String RATE_TABLE_TABLENAME;
    private final String FUND_TABLENAME;
    private final String COMPANY_TABLENAME;


    public FastDAO()
    {
        POOLNAME  = ConnectionFactory.ENGINE_POOL;

        RULES_DBTABLE                  = DBTable.getDBTableForTable("Rules");
        PRODUCT_RULE_STRUCTURE_DBTABLE = DBTable.getDBTableForTable("ProductRuleStructure");
        PRODUCT_STRUCTURE_DBTABLE      = DBTable.getDBTableForTable("ProductStructure");
        TABLE_KEYS_DBTABLE             = DBTable.getDBTableForTable("TableKeys");
        RATE_TABLE_DBTABLE             = DBTable.getDBTableForTable("RateTable");
        FUND_DBTABLE                   = DBTable.getDBTableForTable("Fund");
        COMPANY_DBTABLE                = DBTable.getDBTableForTable("Company");


        RULES_TABLENAME                  = RULES_DBTABLE.getFullyQualifiedTableName();
        PRODUCT_RULE_STRUCTURE_TABLENAME = PRODUCT_RULE_STRUCTURE_DBTABLE.getFullyQualifiedTableName();
        PRODUCT_STRUCTURE_TABLENAME      = PRODUCT_STRUCTURE_DBTABLE.getFullyQualifiedTableName();
        TABLE_KEYS_TABLENAME             = TABLE_KEYS_DBTABLE.getFullyQualifiedTableName();
        RATE_TABLE_TABLENAME             = RATE_TABLE_DBTABLE.getFullyQualifiedTableName();
        FUND_TABLENAME                   = FUND_DBTABLE.getFullyQualifiedTableName();
        COMPANY_TABLENAME                = COMPANY_DBTABLE.getFullyQualifiedTableName();
    }

    public RulesVO[] findByProductStructureEffectiveDate(long productStructurePK, String effectiveDate)
    {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        String rulesPKCol       = RULES_DBTABLE.getDBColumn("RulesPK").getFullyQualifiedColumnName();
        String scriptFKCol      = RULES_DBTABLE.getDBColumn("ScriptFK").getFullyQualifiedColumnName();
        String tableDefFKCol    = RULES_DBTABLE.getDBColumn("TableDefFK").getFullyQualifiedColumnName();
        String ruleNameCol      = RULES_DBTABLE.getDBColumn("RuleName").getFullyQualifiedColumnName();
        String processNameCol   = RULES_DBTABLE.getDBColumn("ProcessName").getFullyQualifiedColumnName();
        String eventNameCol     = RULES_DBTABLE.getDBColumn("EventName").getFullyQualifiedColumnName();
        String eventTypeNameCol = RULES_DBTABLE.getDBColumn("EventTypeName").getFullyQualifiedColumnName();
        String effectiveDateCol = RULES_DBTABLE.getDBColumn("EffectiveDate").getFullyQualifiedColumnName();


        String rulesFKCol            = PRODUCT_RULE_STRUCTURE_DBTABLE.getDBColumn("RulesFK").getFullyQualifiedColumnName();
        String productStructureFKCol = PRODUCT_RULE_STRUCTURE_DBTABLE.getDBColumn("ProductStructureFK").getFullyQualifiedColumnName();

        String sql = " SELECT DISTINCT " + rulesPKCol + ", " + scriptFKCol + ", " + tableDefFKCol +
                     ", " + ruleNameCol + ", " + processNameCol + ", " + eventNameCol + ", " + eventTypeNameCol +
                     " FROM " + RULES_TABLENAME + ", " + PRODUCT_RULE_STRUCTURE_TABLENAME +
                     " WHERE " + rulesPKCol + " = " + rulesFKCol +
                     " AND " + effectiveDateCol + " <= ?" +
                     " AND " + productStructureFKCol + " = ?";

        List bestMatchVOs = new ArrayList();

        try
        {
            conn = ConnectionFactory.getSingleton().getConnection(this.POOLNAME);

            ps = conn.prepareStatement(sql);

            ps.setDate(1, DBUtil.convertStringToDate(effectiveDate));

            ps.setLong(2, productStructurePK);

            rs = ps.executeQuery();

            //  Use the following debug statement to print the contents of the PreparedStatement after field substitution
//            System.out.println(" debuggable statement= " + ps.toString());

            while (rs.next())
            {
                RulesVO rulesVO = new RulesVO();

                long rulesPK = rs.getLong("RulesPK");
                long scriptFK = rs.getLong("ScriptFK");
                long tableDefFK = rs.getLong("TableDefFK");
                String ruleName = rs.getString("RuleName");
                String processName = rs.getString("ProcessName");
                String eventName = rs.getString("EventName");
                String eventTypeName = rs.getString("EventTypeName");

                rulesVO.setRulesPK(rulesPK);
                rulesVO.setScriptFK(scriptFK);
                rulesVO.setTableDefFK(tableDefFK);
                rulesVO.setProcessName(processName);
                rulesVO.setEventName(eventName);
                rulesVO.setEventTypeName(eventTypeName);
                rulesVO.setRuleName(ruleName);

                bestMatchVOs.add(rulesVO);
            }
        }
        catch(Exception e)
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

        if (bestMatchVOs.size() == 0)
        {
            return null;
        }
        else
        {
            return (RulesVO[]) bestMatchVOs.toArray(new RulesVO[bestMatchVOs.size()]);
        }
    }
    
  public RulesVO[] findByCompanyStructureEffectiveDateRuleName(long companyStructurePK, String effectiveDate, String ruleName)
  {
      Connection conn = null;
      PreparedStatement ps = null;
      ResultSet rs = null;

      String rulesPKCol       = RULES_DBTABLE.getDBColumn("RulesPK").getFullyQualifiedColumnName();
      String scriptFKCol      = RULES_DBTABLE.getDBColumn("ScriptFK").getFullyQualifiedColumnName();
      String tableDefFKCol    = RULES_DBTABLE.getDBColumn("TableDefFK").getFullyQualifiedColumnName();
      String ruleNameCol      = RULES_DBTABLE.getDBColumn("RuleName").getFullyQualifiedColumnName();
      String processNameCol   = RULES_DBTABLE.getDBColumn("ProcessName").getFullyQualifiedColumnName();
      String eventNameCol     = RULES_DBTABLE.getDBColumn("EventName").getFullyQualifiedColumnName();
      String eventTypeNameCol = RULES_DBTABLE.getDBColumn("EventTypeName").getFullyQualifiedColumnName();
      String effectiveDateCol = RULES_DBTABLE.getDBColumn("EffectiveDate").getFullyQualifiedColumnName();

      String rulesFKCol            = PRODUCT_RULE_STRUCTURE_DBTABLE.getDBColumn("RulesFK").getFullyQualifiedColumnName();
      String companyStructureFKCol = PRODUCT_RULE_STRUCTURE_DBTABLE.getDBColumn("CompanyStructureFK").getFullyQualifiedColumnName();

        String sql = " SELECT DISTINCT " + rulesPKCol + ", " + scriptFKCol + ", " + tableDefFKCol + ", " + ruleNameCol +
                     ", " + processNameCol + ", " + eventNameCol + ", " + eventTypeNameCol + ", " + effectiveDateCol + 
                   " FROM " + RULES_TABLENAME + ", " + PRODUCT_RULE_STRUCTURE_TABLENAME +
                     " WHERE " + rulesPKCol + " = " + rulesFKCol +
                     " AND " + effectiveDateCol + " <= ?" +
                     " AND " + companyStructureFKCol + " = ?" +
                     " AND " + ruleNameCol + " = ?" +
                     " ORDER BY " + effectiveDateCol + " ASC";						

        List bestMatchVOs = new ArrayList();

        try
        {
            conn = ConnectionFactory.getSingleton().getConnection(this.POOLNAME);

            ps = conn.prepareStatement(sql);

            ps.setDate(1, DBUtil.convertStringToDate(effectiveDate));

            ps.setLong(2, companyStructurePK);

          ps.setString(3, ruleName);

            rs = ps.executeQuery();

          //  Use the following debug statement to print the contents of the PreparedStatement after field substitution
  //            System.out.println(" debuggable statement= " + ps.toString());

            while (rs.next())
            {
                RulesVO rulesVO = new RulesVO();

                long rulesPK = rs.getLong("RulesPK");
                long scriptFK = rs.getLong("ScriptFK");
                long tableDefFK = rs.getLong("TableDefFK");
//              String ruleName = rs.getString("RuleName");
                String processName = rs.getString("ProcessName");
                String eventName = rs.getString("EventName");
                String eventTypeName = rs.getString("EventTypeName");

                rulesVO.setRulesPK(rulesPK);
                rulesVO.setScriptFK(scriptFK);
                rulesVO.setTableDefFK(tableDefFK);
                rulesVO.setProcessName(processName);
                rulesVO.setEventName(eventName);
                rulesVO.setEventTypeName(eventTypeName);
                rulesVO.setRuleName(ruleName);

                bestMatchVOs.add(rulesVO);
            }
        }
        catch(Exception e)
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

        if (bestMatchVOs.size() == 0)
        {
            return null;
        }
        else
        {
            return (RulesVO[]) bestMatchVOs.toArray(new RulesVO[bestMatchVOs.size()]);
        }
    }

    public TableKeysVO[] findTableKeysByAllColumns(long tableDefPK, String effectiveDate, String bandAmountStart,
                                                      String bandAmountEnd, String userKey, String genderId,
                                                        String classId, String area, String tableType)
    {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;


        String tableDefFKCol    = TABLE_KEYS_DBTABLE.getDBColumn("TableDefFK").getFullyQualifiedColumnName();
        String bandAmountCol    = TABLE_KEYS_DBTABLE.getDBColumn("BandAmount").getFullyQualifiedColumnName();
        String userKeyCol       = TABLE_KEYS_DBTABLE.getDBColumn("UserKey").getFullyQualifiedColumnName();
        String genderCol        = TABLE_KEYS_DBTABLE.getDBColumn("Gender").getFullyQualifiedColumnName();
        String classTypeCol     = TABLE_KEYS_DBTABLE.getDBColumn("ClassType").getFullyQualifiedColumnName();
        String stateCol         = TABLE_KEYS_DBTABLE.getDBColumn("State").getFullyQualifiedColumnName();
        String tableTypeCol     = TABLE_KEYS_DBTABLE.getDBColumn("TableType").getFullyQualifiedColumnName();
        String effectiveDateCol = TABLE_KEYS_DBTABLE.getDBColumn("EffectiveDate").getFullyQualifiedColumnName();

        String sql = " SELECT * FROM " + TABLE_KEYS_TABLENAME +
                     " WHERE " + tableDefFKCol + " = ?" +
                     " AND " + bandAmountCol + " BETWEEN ? AND ?" +
                     " AND " + userKeyCol + " = ?" +
                     " AND " + genderCol + " = ?" +
                     " AND " + classTypeCol + " = ?" +
                     " AND " + stateCol + " = ?" +
                     " AND " + tableTypeCol + " = ?" +
                     " AND " + effectiveDateCol + " <= ?";

        List vos = new ArrayList();

        try
        {
            conn = ConnectionFactory.getSingleton().getConnection(this.POOLNAME);

            ps = conn.prepareStatement(sql);

            ps.setLong(1, tableDefPK);
            ps.setBigDecimal(2, new BigDecimal(bandAmountStart));
            ps.setBigDecimal(3, new BigDecimal(bandAmountEnd));
            ps.setString(4, userKey);
            ps.setString(5, genderId);
            ps.setString(6, classId);
            ps.setString(7, area);
            ps.setString(8, tableType);
            ps.setDate(9, DBUtil.convertStringToDate(effectiveDate));

            rs = ps.executeQuery();

            while (rs.next())
            {
                TableKeysVO tableKeysVO = new TableKeysVO();
                tableKeysVO.setBandAmount(rs.getBigDecimal("BandAmount"));
                tableKeysVO.setClassType(rs.getString("ClassType"));
                tableKeysVO.setEffectiveDate(DBUtil.readAndConvertDate(rs, "EffectiveDate"));
                tableKeysVO.setGender(rs.getString("Gender"));
                tableKeysVO.setState(rs.getString("State"));
                tableKeysVO.setTableType(rs.getString("TableType"));
                tableKeysVO.setUserKey(rs.getString("UserKey"));
                tableKeysVO.setTableKeysPK(rs.getLong("TableKeysPK"));
                tableKeysVO.setTableDefFK(tableDefPK);

                vos.add(tableKeysVO);
            }
        }
        catch(Exception e)
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
                if (ps != null) ps.close();
                if (conn != null) conn.close();
            }
            catch (SQLException e)
            {
                System.out.println(e);

                e.printStackTrace();

                throw new RuntimeException(e);
            }
        }

        if (vos.size() == 0)
        {
            return null;
        }
        else
        {
            return (TableKeysVO[]) vos.toArray(new TableKeysVO[vos.size()]);
        }
    }

    public TableKeysVO[] findTableKeysByTableDefPKAndEffectiveDate(long tableDefPK, String effectiveDate)
    {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        String tableDefFKCol    = TABLE_KEYS_DBTABLE.getDBColumn("TableDefFK").getFullyQualifiedColumnName();
        String effectiveDateCol = TABLE_KEYS_DBTABLE.getDBColumn("EffectiveDate").getFullyQualifiedColumnName();
        String bandAmountCol = TABLE_KEYS_DBTABLE.getDBColumn("BandAmount").getFullyQualifiedColumnName();

        String sql = " SELECT * FROM " + TABLE_KEYS_TABLENAME +
                     " WHERE " + tableDefFKCol + " = ?" +
                     " AND " + effectiveDateCol +  " <= ?" +
                     " ORDER BY " + effectiveDateCol + ", " + bandAmountCol + " ASC";

        List vos = new ArrayList();

        try
        {
            conn = ConnectionFactory.getSingleton().getConnection(this.POOLNAME);

            ps = conn.prepareStatement(sql);

            ps.setLong(1, tableDefPK);

            ps.setDate(2, DBUtil.convertStringToDate(effectiveDate));

            rs = ps.executeQuery();

            while (rs.next())
            {
                TableKeysVO tableKeysVO = new TableKeysVO();
                tableKeysVO.setBandAmount(rs.getBigDecimal("BandAmount"));
                tableKeysVO.setClassType(rs.getString("ClassType"));
                tableKeysVO.setEffectiveDate(DBUtil.readAndConvertDate(rs, "EffectiveDate"));
                tableKeysVO.setGender(rs.getString("Gender"));
                tableKeysVO.setState(rs.getString("State"));
                tableKeysVO.setTableType(rs.getString("TableType"));
                tableKeysVO.setUserKey(rs.getString("UserKey"));
                tableKeysVO.setTableKeysPK(rs.getLong("TableKeysPK"));
                tableKeysVO.setTableDefFK(tableDefPK);

                vos.add(tableKeysVO);
            }
        }
        catch(Exception e)
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
                if (ps != null) ps.close();
                if (conn != null) conn.close();
            }
            catch (SQLException e)
            {
                System.out.println(e);

                e.printStackTrace();

                throw new RuntimeException(e);
            }
        }

        if (vos.size() == 0)
        {
            return null;
        }
        else
        {
            return (TableKeysVO[]) vos.toArray(new TableKeysVO[vos.size()]);
        }
    }

    public RateTableVO[] findAttainRates(long tableKeyId, int age) throws Exception
    {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        String tableKeysFKCol = RATE_TABLE_DBTABLE.getDBColumn("TableKeysFK").getFullyQualifiedColumnName();
        String ageCol         = RATE_TABLE_DBTABLE.getDBColumn("Age").getFullyQualifiedColumnName();
        String durationCol    = RATE_TABLE_DBTABLE.getDBColumn("Duration").getFullyQualifiedColumnName();

        String sql = " SELECT * FROM " + RATE_TABLE_TABLENAME +
                     " WHERE " + tableKeysFKCol  + "= ?" +
                     " AND " + ageCol + " >= ?" +
                     " ORDER BY " + ageCol + ", " + durationCol;

        List vos = new ArrayList();

        try
        {
            conn = ConnectionFactory.getSingleton().getConnection(this.POOLNAME);

            ps = conn.prepareStatement(sql);

            ps.setLong(1, tableKeyId);
            ps.setInt(2, age);

            rs = ps.executeQuery();

            while (rs.next())
            {
                RateTableVO rateTableVO = new RateTableVO();
                rateTableVO.setAge(rs.getInt("Age"));
                rateTableVO.setDuration(rs.getInt("Duration"));
                rateTableVO.setRate(rs.getBigDecimal("Rate"));
                rateTableVO.setRateTablePK(rs.getLong("RateTablePK"));
                rateTableVO.setTableKeysFK(tableKeyId);

                vos.add(rateTableVO);
            }
        }
        catch(Exception e)
        {
            System.out.println(e);

            e.printStackTrace();

            throw e;
        }
        finally
        {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            if (conn != null) conn.close();
        }

        if (vos.size() == 0)
        {
            return null;
        }
        else
        {
            return (RateTableVO[]) vos.toArray(new RateTableVO[vos.size()]);
        }
    }

    public RateTableVO[] findAttainRatesWithDuration(long tableKeyId, int age, int duration) throws Exception
    {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        String tableKeysFKCol = RATE_TABLE_DBTABLE.getDBColumn("TableKeysFK").getFullyQualifiedColumnName();
        String ageCol         = RATE_TABLE_DBTABLE.getDBColumn("Age").getFullyQualifiedColumnName();
        String durationCol    = RATE_TABLE_DBTABLE.getDBColumn("Duration").getFullyQualifiedColumnName();

        String sql = " SELECT * FROM " + RATE_TABLE_TABLENAME +
                     " WHERE " + tableKeysFKCol  + "= ?" +
                     " AND " + ageCol + " >= ?" +
                     " AND " + durationCol + " = (SELECT MIN(" + durationCol + ") FROM " + RATE_TABLE_TABLENAME +
                     	" WHERE " + tableKeysFKCol + "= ?" +
                     	" AND " + ageCol + " >= ?" +
                     	" AND " + durationCol + " >= ?)" +
                     " ORDER BY " + ageCol + ", " + durationCol;

        List vos = new ArrayList();
        
        try
        {
            conn = ConnectionFactory.getSingleton().getConnection(this.POOLNAME);

            ps = conn.prepareStatement(sql);

            ps.setLong(1, tableKeyId);
            ps.setInt(2, age);
            ps.setLong(3, tableKeyId);
            ps.setInt(4, age);
            ps.setInt(5, duration);

            rs = ps.executeQuery();

            while (rs.next())
            {
                RateTableVO rateTableVO = new RateTableVO();
                rateTableVO.setAge(rs.getInt("Age"));
                rateTableVO.setDuration(rs.getInt("Duration"));
                rateTableVO.setRate(rs.getBigDecimal("Rate"));
                rateTableVO.setRateTablePK(rs.getLong("RateTablePK"));
                rateTableVO.setTableKeysFK(tableKeyId);

                vos.add(rateTableVO);
            }
        }
        catch(Exception e)
        {
            System.out.println(e);

            e.printStackTrace();

            throw e;
        }
        finally
        {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            if (conn != null) conn.close();
        }

        if (vos.size() == 0)
        {
            return null;
        }
        else
        {
            return (RateTableVO[]) vos.toArray(new RateTableVO[vos.size()]);
        }
    }

    public RateTableVO[] findIssueRates(long tableKeyId, int age)
    {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        String tableKeysFKCol = RATE_TABLE_DBTABLE.getDBColumn("TableKeysFK").getFullyQualifiedColumnName();
        String ageCol         = RATE_TABLE_DBTABLE.getDBColumn("Age").getFullyQualifiedColumnName();
        String durationCol    = RATE_TABLE_DBTABLE.getDBColumn("Duration").getFullyQualifiedColumnName();

        String sql = " SELECT * FROM " + RATE_TABLE_TABLENAME +
                     " WHERE " + tableKeysFKCol + " = ?" +
                     " AND " + ageCol + " = " +  "(SELECT MIN(" + ageCol + ") " +
                     " FROM " + RATE_TABLE_TABLENAME + " WHERE " + tableKeysFKCol + " = ?" +
		             " AND " + ageCol + " >= ? )" +
                     " ORDER BY " + ageCol + ", " + durationCol;

        List vos = new ArrayList();

        try
        {
            conn = ConnectionFactory.getSingleton().getConnection(this.POOLNAME);

            ps = conn.prepareStatement(sql);

            ps.setLong(1, tableKeyId);

            ps.setLong(2, tableKeyId);

            ps.setLong(3, age);

            rs = ps.executeQuery();

            while (rs.next())
            {
                RateTableVO rateTableVO = new RateTableVO();
                rateTableVO.setAge(rs.getInt("Age"));
                rateTableVO.setDuration(rs.getInt("Duration"));
                rateTableVO.setRate(rs.getBigDecimal("Rate"));
                rateTableVO.setRateTablePK(rs.getLong("RateTablePK"));
                rateTableVO.setTableKeysFK(tableKeyId);


                vos.add(rateTableVO);
            }
        }
        catch(Exception e)
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
                if (ps != null) ps.close();
                if (conn != null) conn.close();
            }
            catch (SQLException e)
            {
              System.out.println(e);

                e.printStackTrace();

                throw new RuntimeException(e);
            }
        }

        if (vos.size() == 0)
        {
            return null;
        }
        else
        {
            return (RateTableVO[]) vos.toArray(new RateTableVO[vos.size()]);
        }
    }

    public RuleNameVO findRuleNameVO() throws Exception
    {
        Connection conn = null;
        Statement s = null;
        ResultSet rs = null;

        String sql = null;

        RuleNameVO ruleNameVO = new RuleNameVO();

        String processNameCol   = RULES_DBTABLE.getDBColumn("ProcessName").getFullyQualifiedColumnName();
        String eventNameCol     = RULES_DBTABLE.getDBColumn("EventName").getFullyQualifiedColumnName();
        String eventTypeNameCol = RULES_DBTABLE.getDBColumn("EventTypeName").getFullyQualifiedColumnName();
        String ruleNameCol      = RULES_DBTABLE.getDBColumn("RuleName").getFullyQualifiedColumnName();

        try
        {
            conn= ConnectionFactory.getSingleton().getConnection(this.POOLNAME);

            s = conn.createStatement();

            // Process
            sql = "SELECT DISTINCT " + processNameCol + " FROM " + RULES_TABLENAME + " ORDER BY " + processNameCol;
            rs = s.executeQuery(sql);
            while (rs.next())
            {
                ruleNameVO.addProcessName(rs.getString("ProcessName"));
            }
            rs.close();

            // Event
            sql = "SELECT DISTINCT " + eventNameCol + " FROM " + RULES_TABLENAME + " ORDER BY " + eventNameCol;
            rs = s.executeQuery(sql);
            while (rs.next())
            {
                ruleNameVO.addEventName(rs.getString("EventName"));
            }
            rs.close();

            // Event Type
            sql = "SELECT DISTINCT " + eventTypeNameCol + " FROM " + RULES_TABLENAME + " ORDER BY " + eventTypeNameCol;
            rs = s.executeQuery(sql);
            while (rs.next())
            {
                ruleNameVO.addEventTypeName(rs.getString("EventTypeName"));
            }
            rs.close();

            // Rule Name
            sql = "SELECT DISTINCT " + ruleNameCol + " FROM " + RULES_TABLENAME + " ORDER BY " + ruleNameCol;
            rs = s.executeQuery(sql);
            while (rs.next())
            {
                ruleNameVO.addRuleName(rs.getString("RuleName"));
            }
            rs.close();
        }
        catch(Exception e)
        {
            System.out.println(e);

            e.printStackTrace();

            throw e;
        }
        finally
        {
            if (rs != null) rs.close();
            if (s != null) s.close();
            if (conn != null) conn.close();
        }

        return ruleNameVO;
    }

    public CompanyStructureNameVO findCompanyStructureNameVO() throws Exception
    {
        Connection conn = null;
        Statement s = null;
        ResultSet rs = null;

        String sql = null;

        CompanyStructureNameVO companyStructureNameVO = new CompanyStructureNameVO();

        String companyNameCol          = COMPANY_DBTABLE.getDBColumn("CompanyName").getFullyQualifiedColumnName();
        String marketingPackageNameCol = PRODUCT_STRUCTURE_DBTABLE.getDBColumn("MarketingPackageName").getFullyQualifiedColumnName();
        String groupProductNameCol     = PRODUCT_STRUCTURE_DBTABLE.getDBColumn("GroupProductName").getFullyQualifiedColumnName();
        String areaNameCol             = PRODUCT_STRUCTURE_DBTABLE.getDBColumn("AreaName").getFullyQualifiedColumnName();
        String businessContractNameCol = PRODUCT_STRUCTURE_DBTABLE.getDBColumn("BusinessContractName").getFullyQualifiedColumnName();

        try
        {
            conn = ConnectionFactory.getSingleton().getConnection(this.POOLNAME);

            s = conn.createStatement();

            // CompanyName
            sql = "SELECT DISTINCT " + companyNameCol + " FROM " + COMPANY_TABLENAME;
            rs = s.executeQuery(sql);
            while (rs.next())
            {
                companyStructureNameVO.addCompanyName(rs.getString("CompanyName"));
            }
            rs.close();

            // MarketingPackageName
            sql = "SELECT DISTINCT " + marketingPackageNameCol + " FROM " + PRODUCT_STRUCTURE_TABLENAME;
            rs = s.executeQuery(sql);
            while (rs.next())
            {
                companyStructureNameVO.addMarketingPackageName(rs.getString("MarketingPackageName"));
            }
            rs.close();

            // GroupProductName
            sql = "SELECT DISTINCT " + groupProductNameCol + " FROM " + PRODUCT_STRUCTURE_TABLENAME;
            rs = s.executeQuery(sql);
            while (rs.next())
            {
                companyStructureNameVO.addGroupProductName(rs.getString("GroupProductName"));
            }
            rs.close();

            // AreaName
            sql = "SELECT DISTINCT " + areaNameCol + " FROM " + PRODUCT_STRUCTURE_TABLENAME;
            rs = s.executeQuery(sql);
            while (rs.next())
            {
                companyStructureNameVO.addAreaName(rs.getString("AreaName"));
            }
            rs.close();

            // BusinessContractName
            sql = "SELECT DISTINCT " + businessContractNameCol + " FROM " + PRODUCT_STRUCTURE_TABLENAME;
            rs = s.executeQuery(sql);
            while (rs.next())
            {
                companyStructureNameVO.addBusinessContractName(rs.getString("BusinessContractName"));
            }
            rs.close();
        }
        catch(Exception e)
        {
            System.out.println(e);

            e.printStackTrace();

            throw e;
        }
        finally
        {
            if (rs != null) rs.close();
            if (s != null) s.close();
            if (conn != null) conn.close();
        }

        return companyStructureNameVO;
    }

    public long[] findRulesPKsByProductStructurePK(long productStructurePK) throws Exception
    {
        Connection conn = null;
        Statement s = null;
        ResultSet rs = null;

        String productStructureFKCol = PRODUCT_RULE_STRUCTURE_DBTABLE.getDBColumn("ProductStructureFK").getFullyQualifiedColumnName();

        String sql = " SELECT * FROM " + PRODUCT_RULE_STRUCTURE_TABLENAME +
                     " WHERE " + productStructureFKCol + " = " + productStructurePK;

        List rulesPKs = new ArrayList();

        try
        {
            conn = ConnectionFactory.getSingleton().getConnection(this.POOLNAME);

            s = conn.createStatement();

            rs = s.executeQuery(sql);

            while (rs.next())
            {
                rulesPKs.add(new Long(rs.getLong("RulesFK")));
            }
        }
        catch(Exception e)
        {
            System.out.println(e);

            e.printStackTrace();

            throw e;
        }
        finally
        {
            if (rs != null) rs.close();
            if (s != null) s.close();
            if (conn != null) conn.close();
        }

        if (rulesPKs.size() == 0)
        {
            return null;
        }
        else
        {
            return Util.convertLongToPrim((Long[]) rulesPKs.toArray(new Long[rulesPKs.size()]));
        }
    }

    public long[] findAttachedProductStructurePKs() throws Exception
    {
        Connection conn = null;
        Statement s = null;
        ResultSet rs = null;

        String productStructureFKCol = PRODUCT_RULE_STRUCTURE_DBTABLE.getDBColumn("ProductStructureFK").getFullyQualifiedColumnName();

        String sql = " SELECT DISTINCT " + productStructureFKCol +
                     " FROM " + PRODUCT_RULE_STRUCTURE_TABLENAME;

        List productStructurePKs = new ArrayList();

        try
        {
            conn = ConnectionFactory.getSingleton().getConnection(this.POOLNAME);

            s = conn.createStatement();

            rs = s.executeQuery(sql);

            while (rs.next())
            {
                productStructurePKs.add(new Long(rs.getLong("ProductStructureFK")));
            }
        }
        catch(Exception e)
        {
            System.out.println(e);

            e.printStackTrace();

            throw e;
        }
        finally
        {
            if (rs != null) rs.close();
            if (s != null) s.close();
            if (conn != null) conn.close();
        }

        if (productStructurePKs.size() == 0)
        {
            return null;
        }
        else
        {
            return Util.convertLongToPrim((Long[]) productStructurePKs.toArray(new Long[productStructurePKs.size()]));
        }
    }

    public long[] findAttachedTableDefPKs() throws Exception
    {
        Connection conn = null;
        Statement s = null;
        ResultSet rs = null;

        String tableDefCol = RULES_DBTABLE.getDBColumn("TableDefFK").getFullyQualifiedColumnName();

        String sql = " SELECT DISTINCT " + tableDefCol + " FROM " + RULES_TABLENAME;

        List tableDefPKs = new ArrayList();

        try
        {
            conn = ConnectionFactory.getSingleton().getConnection(this.POOLNAME);

            s = conn.createStatement();

            rs = s.executeQuery(sql);

            while (rs.next())
            {
                tableDefPKs.add(new Long(rs.getLong("TableDefFK")));
            }
        }
        catch(Exception e)
        {
            System.out.println(e);

            e.printStackTrace();

            throw e;
        }
        finally
        {
            if (rs != null) rs.close();
            if (s != null) s.close();
            if (conn != null) conn.close();
        }

        if (tableDefPKs.size() == 0)
        {
            return null;
        }
        else
        {
            return Util.convertLongToPrim((Long[]) tableDefPKs.toArray(new Long[tableDefPKs.size()]));
        }
    }

    public long[] findAttachedScriptPKs() throws Exception
    {
        Connection conn = null;
        Statement s = null;
        ResultSet rs = null;

        String scriptFKCol = RULES_DBTABLE.getDBColumn("ScriptFK").getFullyQualifiedColumnName();

        String sql = " SELECT DISTINCT " + scriptFKCol + " FROM " + RULES_TABLENAME;

        List scriptPKs = new ArrayList();

        try {

            conn = ConnectionFactory.getSingleton().getConnection(this.POOLNAME);
            s = conn.createStatement();
            rs = s.executeQuery(sql);

            while (rs.next()) {

                scriptPKs.add(new Long(rs.getLong("ScriptFK")));
            }
        }
        catch(Exception e) {

            throw e;
        }
        finally {

            if (rs != null) rs.close();
            if (s != null) s.close();
            if (conn != null) conn.close();
        }

        if (scriptPKs.size() == 0) {

            return null;
        }
        else {

            return Util.convertLongToPrim((Long[]) scriptPKs.toArray(new Long[scriptPKs.size()]));
        }
    }

    public long[] findAttachedRulesPKs() throws Exception
    {
        Connection conn = null;
        Statement s = null;
        ResultSet rs = null;

        String rulesFKCol = PRODUCT_RULE_STRUCTURE_DBTABLE.getDBColumn("RulesFK").getFullyQualifiedColumnName();

        String sql = " SELECT DISTINCT " + rulesFKCol + " FROM " + PRODUCT_RULE_STRUCTURE_TABLENAME;

        List rulesPKs = new ArrayList();

        try
        {
            conn = ConnectionFactory.getSingleton().getConnection(this.POOLNAME);

            s = conn.createStatement();

            rs = s.executeQuery(sql);

            while (rs.next())
            {
                rulesPKs.add(new Long(rs.getLong("RulesFK")));
            }
        }
        catch(Exception e)
        {
            System.out.println(e);

            e.printStackTrace();

            throw e;
        }
        finally
        {
            if (rs != null) rs.close();
            if (s != null) s.close();
            if (conn != null) conn.close();
        }

        if (rulesPKs.size() == 0)
        {
            return null;
        }
        else
        {
            return Util.convertLongToPrim((Long[]) rulesPKs.toArray(new Long[rulesPKs.size()]));
        }
    }

    public long[] findProductStructurePKsByCompanyName(String companyName)
    {
        Connection conn = null;
        Statement s = null;
        ResultSet rs = null;

        String productStructurePKCol = PRODUCT_STRUCTURE_DBTABLE.getDBColumn("ProductStructurePK").getFullyQualifiedColumnName();
        String companyFKCol          = PRODUCT_STRUCTURE_DBTABLE.getDBColumn("CompanyFK").getFullyQualifiedColumnName();

        String companyNameCol        = COMPANY_DBTABLE.getDBColumn("CompanyName").getFullyQualifiedColumnName();
        String companyPKCol          = COMPANY_DBTABLE.getDBColumn("CompanyPK").getFullyQualifiedColumnName();

        String sql = " SELECT " + productStructurePKCol +
                     " FROM " + PRODUCT_STRUCTURE_TABLENAME + ", " + COMPANY_TABLENAME +
                     " WHERE " + companyNameCol + " = '" + companyName + "'" +
                     " AND " + companyPKCol + " = " + companyFKCol;

        List productStructurePKs = new ArrayList();

        try
        {
            conn = ConnectionFactory.getSingleton().getConnection(this.POOLNAME);

            s = conn.createStatement();

            rs = s.executeQuery(sql);

            while (rs.next())
            {
                productStructurePKs.add(new Long(rs.getLong("ProductStructurePK")));
            }
        }
        catch(Exception e)
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
                if (s != null) s.close();
                if (conn != null) conn.close();
            }
            catch (SQLException e)
            {
                System.out.println(e);

                e.printStackTrace();

                throw new RuntimeException(e);
            }
        }

        if (productStructurePKs.size() == 0)
        {
            return null;
        }
        else
        {
            return Util.convertLongToPrim((Long[]) productStructurePKs.toArray(new Long[productStructurePKs.size()]));
        }
    }

    public long[] findAllProductStructurePKs()
    {
        Connection conn = null;
        Statement s = null;
        ResultSet rs = null;

        String productStructurePKCol = PRODUCT_STRUCTURE_DBTABLE.getDBColumn("ProductStructurePK").getFullyQualifiedColumnName();

        String sql = "SELECT " + productStructurePKCol + " FROM " + PRODUCT_STRUCTURE_TABLENAME;

        List productStructurePKs = new ArrayList();

        try
        {
            conn = ConnectionFactory.getSingleton().getConnection(this.POOLNAME);

            s = conn.createStatement();

            rs = s.executeQuery(sql);

            while (rs.next())
            {
                productStructurePKs.add(new Long(rs.getLong("ProductStructurePK")));
            }
        }
        catch(Exception e)
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
                if (s != null) s.close();
                if (conn != null) conn.close();
            }
            catch (SQLException e)
            {
              System.out.println(e);

                e.printStackTrace();

                throw new RuntimeException(e);
            }
        }

        if (productStructurePKs.size() == 0)
        {
            return null;
        }
        else
        {
            return Util.convertLongToPrim((Long[]) productStructurePKs.toArray(new Long[productStructurePKs.size()]));
        }
    }

    public static void main(String[] args) throws Exception
    {
        FastDAO fastDAO = new FastDAO();
        fastDAO.findTableKeysByAllColumns(60, "2004/01/01", "0", "0", "Single", "NotApplicable", "NotApplicable", "NotApplicable", "NotApplicable");
    }

    public FundVO findFundVOBy_FundPK(long fundPK)
    {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        String fundPKCol = FUND_DBTABLE.getDBColumn("FundPK").getFullyQualifiedColumnName();

        String sql = " SELECT *" +
                    " FROM " + FUND_TABLENAME +
                     " WHERE " + fundPKCol + " = ?";

        FundVO fundVO = null;

        try
        {
            conn = ConnectionFactory.getSingleton().getConnection(this.POOLNAME);

            ps = conn.prepareStatement(sql);

            ps.setLong(1, fundPK);

            rs = ps.executeQuery();

            if (rs.next())
            {
                fundVO = new FundVO();

                fundVO.setFundPK(rs.getLong("FundPK"));
                fundVO.setName(rs.getString("Name"));
                fundVO.setFundType(rs.getString("FundType"));
                fundVO.setPortfolioNewMoneyStatusCT(rs.getString("PortfolioNewMoneyStatusCT"));
                fundVO.setShortName(rs.getString("ShortName"));
                fundVO.setExcludeFromActivityFileInd(rs.getString("ExcludeFromActivityFileInd"));
                fundVO.setTypeCodeCT(rs.getString("TypeCodeCT"));
                fundVO.setReportingFundName(rs.getString("ReportingFundName"));
            }
        }
        catch(Exception e)
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
                if (ps != null) ps.close();
                if (conn != null) conn.close();
            }
            catch (SQLException e)
            {
                e.printStackTrace();

                throw new RuntimeException(e);
            }
        }

        return fundVO;
    }

    public InterestRateParametersVO findInterestRatesByOriginalDateOptionFund(String intRateDate, String optionCT,
                                                                                 long filteredFundId)
    {
        DBTable interestRateParametersDBTable = DBTable.getDBTableForTable("InterestRateParameters");
        String interestRateParametersTable = interestRateParametersDBTable.getFullyQualifiedTableName();
        String interestRateParametersPKCol = interestRateParametersDBTable.getDBColumn("InterestRateParametersPK").getFullyQualifiedColumnName();

        DBTable interestRateDBTable = DBTable.getDBTableForTable("InterestRate");
        String interestRateTable = interestRateDBTable.getFullyQualifiedTableName();
        String interestRateParametersFKCol = interestRateDBTable.getDBColumn("InterestRateParametersFK").getFullyQualifiedColumnName();

        String filteredFundFKCol = interestRateParametersDBTable.getDBColumn("FilteredFundFK").getFullyQualifiedColumnName();
        String optionCTCol       = interestRateParametersDBTable.getDBColumn("OptionCT").getFullyQualifiedColumnName();
        String originalDateCol   = interestRateParametersDBTable.getDBColumn("OriginalDate").getFullyQualifiedColumnName();

        String interestRateEffectiveDateCol = interestRateDBTable.getDBColumn("EffectiveDate").getFullyQualifiedColumnName();
        
        DBTable filteredFundDBTable = DBTable.getDBTableForTable("FilteredFund");
        String filteredFundTable = filteredFundDBTable.getFullyQualifiedTableName();
        String filteredFundPKCol = filteredFundDBTable.getDBColumn("FilteredFundPK").getFullyQualifiedColumnName();
        
        String sql = " SELECT " + interestRateTable + ".*, " + interestRateParametersTable + ".*" +
                " FROM " + interestRateTable +
                " INNER JOIN " + interestRateParametersTable +
                " ON " + interestRateParametersFKCol + " = " + interestRateParametersPKCol +
                " INNER JOIN " + filteredFundTable +
                " ON " + filteredFundFKCol + " = " + filteredFundPKCol +
                     " WHERE " + filteredFundFKCol + " = ?" +
                " AND " + optionCTCol + " IN (?, '*')" +
                " AND " + originalDateCol + " = " +
                
                " (SELECT MAX(" + originalDateCol + ") " +
                " FROM " + interestRateParametersTable +
                     " WHERE " + originalDateCol + " <= ?" +
                " AND " + filteredFundFKCol + " = ?" +
                " AND " + optionCTCol + " IN (?, '*')" +
                ")" +
                " ORDER BY InterestRate.Rate ASC";

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;


        InterestRateParametersVO interestRateParametersVO = null;

        try
        {
            conn = ConnectionFactory.getSingleton().getConnection(this.POOLNAME);

            ps = conn.prepareStatement(sql);

            ps.setLong(1, filteredFundId);

            ps.setString(2, optionCT);

            ps.setDate(3, DBUtil.convertStringToDate(intRateDate));

            ps.setLong(4, filteredFundId);

            ps.setString(5, optionCT);
            

            rs = ps.executeQuery();

            if (rs.next())
            {
                if (interestRateParametersVO == null)
                {
                    interestRateParametersVO = new InterestRateParametersVO();

                    interestRateParametersVO.setInterestRateParametersPK(rs.getLong("InterestRateParametersPK"));
                    interestRateParametersVO.setFilteredFundFK(rs.getLong("FilteredFundFK"));
                    interestRateParametersVO.setOriginalDate(DBUtil.convertDateToString(rs.getDate("OriginalDate")));
                    interestRateParametersVO.setOptionCT(rs.getString("OptionCT"));
                    interestRateParametersVO.setStopDate(DBUtil.convertDateToString(rs.getDate("StopDate")));
                }

                InterestRateVO interestRateVO = new InterestRateVO();
                interestRateVO.setInterestRatePK(rs.getLong("InterestRatePK"));
                interestRateVO.setInterestRateParametersFK(rs.getLong("InterestRateParametersFK"));
                interestRateVO.setEffectiveDate(DBUtil.convertDateToString(rs.getDate("EffectiveDate")));
                interestRateVO.setGuaranteeDuration(rs.getString("GuaranteeDuration"));
                interestRateVO.setRate(rs.getBigDecimal("Rate"));

                interestRateParametersVO.addInterestRateVO(interestRateVO);
            }
        }
        catch(Exception e)
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
                if (ps != null) ps.close();
                if (conn != null) conn.close();
            }
            catch (SQLException e)
            {
                e.printStackTrace();

                throw new RuntimeException(e);
            }
        }

        return interestRateParametersVO;
	}

    public String getLastControlBalanceDate()
    {
        Connection conn = null;
        Statement s = null;
        ResultSet rs = null;

        DBTable controlBalanceDBTable = DBTable.getDBTableForTable("ControlBalance");
        String controlBalanceTable = controlBalanceDBTable.getFullyQualifiedTableName();
        String endingBalanceCycleDateCol = controlBalanceDBTable.getDBColumn("EndingBalanceCycleDate").getFullyQualifiedColumnName();

        String sql = " SELECT MAX(" + endingBalanceCycleDateCol + ") as EndingBalanceCycleDate FROM " + controlBalanceTable;

        String lastCBRunDate = "";

        try
        {
            conn = ConnectionFactory.getSingleton().getConnection(this.POOLNAME);

            s = conn.createStatement();

            rs = s.executeQuery(sql);

            while (rs.next())
            {
                lastCBRunDate = DBUtil.readAndConvertDate(rs, "EndingBalanceCycleDate");
            }

        }
        catch(Exception e)
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
                if (s != null) s.close();
                if (conn != null) conn.close();
            }
            catch (SQLException e)
            {
              System.out.println(e);

                e.printStackTrace();

                throw new RuntimeException(e);
            }
        }

        return lastCBRunDate;
	}

    ///***************************** OLD ***************************

//    public InterestRateParametersVO findInterestRatesByOriginalDateOptionFund(String originalDate, String option,
//                                                                                 long filteredFundId)
//    {
//        DBTable interestRateParametersDBTable = DBTable.getDBTableForTable("InterestRateParameters");
//        String interestRateParametersTable = interestRateParametersDBTable.getFullyQualifiedTableName();
//        String interestRateParametersPKCol = interestRateParametersDBTable.getDBColumn("InterestRateParametersPK").getFullyQualifiedColumnName();
//
//        DBTable interestRateDBTable = DBTable.getDBTableForTable("InterestRate");
//        String interestRateTable = interestRateDBTable.getFullyQualifiedTableName();
//        String interestRateParametersFKCol = interestRateDBTable.getDBColumn("InterestRateParametersFK").getFullyQualifiedColumnName();
//
//        String filteredFundFKCol = interestRateParametersDBTable.getDBColumn("FilteredFundFK").getFullyQualifiedColumnName();
//        String optionCTCol       = interestRateParametersDBTable.getDBColumn("OptionCT").getFullyQualifiedColumnName();
//        String originalDateCol   = interestRateParametersDBTable.getDBColumn("OriginalDate").getFullyQualifiedColumnName();
//
//        String sql = " SELECT * FROM " + interestRateParametersTable +
//                     " INNER JOIN " + interestRateTable +
//                     " ON " + interestRateParametersPKCol + " = " + interestRateParametersFKCol +
//                     " WHERE " + filteredFundFKCol + " = ?" +
//                     " AND (" + optionCTCol + " = ?" +
//                     " OR " + optionCTCol + " = '*') AND " + originalDateCol +
//                     " = (SELECT MAX(" + originalDateCol + ") FROM " + interestRateParametersTable +
//                " WHERE " + originalDateCol + " <= ?" +
//                     " AND " + filteredFundFKCol + " = ?"+
//                     " AND (" + optionCTCol + " = ?" +
//                     " OR " + optionCTCol + " = '*'))";
//
//        Connection conn = null;
//        PreparedStatement ps = null;
//        ResultSet rs = null;
//
//        InterestRateParametersVO interestRateParametersVO = null;
//
//        try
//        {
//            conn = ConnectionFactory.getSingleton().getConnection(this.POOLNAME);
//
//            ps = conn.prepareStatement(sql);
//
//            ps.setLong(1, filteredFundId);
//
//            ps.setString(2, option);
//
//            ps.setDate(3, DBUtil.convertStringToDate(originalDate));
//
//            ps.setLong(4, filteredFundId);
//
//            ps.setString(5, option);
//
//            rs = ps.executeQuery();
//
//            if (rs.next())
//            {
//                if (interestRateParametersVO == null)
//                {
//                    interestRateParametersVO = new InterestRateParametersVO();
//
//                    interestRateParametersVO.setInterestRateParametersPK(rs.getLong("InterestRateParametersPK"));
//                    interestRateParametersVO.setFilteredFundFK(rs.getLong("FilteredFundFK"));
//                    interestRateParametersVO.setOriginalDate(DBUtil.convertDateToString(rs.getDate("OriginalDate")));
//                    interestRateParametersVO.setOptionCT(rs.getString("OptionCT"));
//                    interestRateParametersVO.setStopDate(DBUtil.convertDateToString(rs.getDate("StopDate")));
//                }
//
//                InterestRateVO interestRateVO = new InterestRateVO();
//                interestRateVO.setInterestRatePK(rs.getLong("InterestRatePK"));
//                interestRateVO.setInterestRateParametersFK(rs.getLong("InterestRateParametersFK"));
//                interestRateVO.setEffectiveDate(DBUtil.convertDateToString(rs.getDate("EffectiveDate")));
//                interestRateVO.setGuaranteeDuration(rs.getString("GuaranteeDuration"));
//                interestRateVO.setRate(rs.getBigDecimal("Rate"));
//
//                interestRateParametersVO.addInterestRateVO(interestRateVO);
//            }
//        }
//        catch(Exception e)
//        {
//            System.out.println(e);
//
//            e.printStackTrace();
//
//            throw new RuntimeException(e);
//        }
//        finally
//        {
//            try
//            {
//                if (rs != null) rs.close();
//                if (ps != null) ps.close();
//                if (conn != null) conn.close();
//            }
//            catch (SQLException e)
//            {
//                e.printStackTrace();
//
//                throw new RuntimeException(e);
//            }
//        }
//
//        return interestRateParametersVO;
//    }
}


