/*
 * Version 1.1  02/01/2002
 *
 * (c) 2000-2004 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */

package engine.dm.dao;

import edit.common.vo.*;
import edit.services.db.ConnectionFactory;
import edit.services.db.DAO;
import edit.services.db.DBTable;
import edit.services.db.DBUtil;

import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class FilteredFundDAO extends DAO
{
    private final String POOLNAME;
    private final DBTable DBTABLE;
    private final String TABLENAME;

    public FilteredFundDAO()
    {
        POOLNAME  = ConnectionFactory.ENGINE_POOL;
        DBTABLE   = DBTable.getDBTableForTable("FilteredFund");
        TABLENAME = DBTABLE.getFullyQualifiedTableName();
    }

	public FilteredFundVO[] findUnitValuesByCSIdFundId(long productStructureId, long fundId)
    {
        DBTable productFilteredFundStructureDBTable = DBTable.getDBTableForTable("ProductFilteredFundStructure");

        String productFilteredFundStructureTable = productFilteredFundStructureDBTable.getFullyQualifiedTableName();

        String filteredFundPKCol = DBTABLE.getDBColumn("FilteredFundPK").getFullyQualifiedColumnName();
        String fundFKCol         = DBTABLE.getDBColumn("FundFK").getFullyQualifiedColumnName();


        String productStructureFKCol = productFilteredFundStructureDBTable.getDBColumn("ProductStructureFK").getFullyQualifiedColumnName();
        String filteredFundFKCol     = productFilteredFundStructureDBTable.getDBColumn("FilteredFundFK").getFullyQualifiedColumnName();

        String sql = " SELECT " + TABLENAME + ".*" + " FROM " + TABLENAME + ", " + productFilteredFundStructureTable +
                     " WHERE " + productStructureFKCol + " = " + productStructureId +
                     " AND " + filteredFundPKCol + " = " + filteredFundFKCol +
                     " AND " + fundFKCol + " = " + fundId;

        return (FilteredFundVO[]) executeQuery(FilteredFundVO.class,
                                                sql,
                                                  POOLNAME,
                                                   true,
                                                    null);
	}

	public FilteredFundVO[] findByFundId(long fundId)
    {
        String fundFKCol = DBTABLE.getDBColumn("FundFK").getFullyQualifiedColumnName();

        String sql = " SELECT * FROM " + TABLENAME +
                     " WHERE " + fundFKCol + " = " + fundId;

        List voExclusionList = new ArrayList();
        voExclusionList.add(UnitValuesVO.class);
        voExclusionList.add(InterestRateParametersVO.class);
        voExclusionList.add(ProductFilteredFundStructureVO.class);
        voExclusionList.add(FeeVO.class);
        voExclusionList.add(FeeDescriptionVO.class);
        voExclusionList.add(ChargeCodeVO.class);

        return (FilteredFundVO[]) executeQuery(FilteredFundVO.class,
                                                sql,
                                                  POOLNAME,
                                                    true,
                                                     voExclusionList);
	}

//	public FilteredFundVO[] findSpecificUnitValueRecursively(long productStructureId,
//                                                              long fundId,
//                                                               String effectiveDate)
//                                                             throws Exception {
//
//        FilteredFundVO[] filteredFundVO = null;
//        StringBuffer sql = null;
//        List voExclusionList = new ArrayList();
//        voExclusionList.add(InterestRateParametersVO.class);
//
//		sql = new StringBuffer();
//		sql.append("SELECT FilteredFundPK FROM FilteredFund WHERE ProductStructureFK = ");
//        sql.append(productStructureId);
//		sql.append(" AND FundFK = ");
//        sql.append(fundId);
//		sql.append(" AND EffectiveDate =  '");
//        sql.append(effectiveDate);
//        sql.append("'");
//
//        List tableNames = new ArrayList();
//        tableNames.add("FilteredFund");
//
//        filteredFundVO = (FilteredFundVO[]) executeQuery(FilteredFundVO.class,
//                                                sql.toString(),
//                                                  POOLNAME,
//                                                    true,
//                                                     voExclusionList,
//                                                        tableNames);
//
//		if (filteredFundVO == null)  {
//
//            sql = new StringBuffer();
//            sql.append("SELECT FilteredFundPK FROM FilteredFund WHERE ProductStructureFK = ");
//            sql.append(productStructureId);
//            sql.append(" AND FundFK = ");
//            sql.append(fundId);
//            sql.append(" AND EffectiveDate = (");
//            sql.append("SELECT MIN(EffectiveDate) FROM FilteredFund ");
//            sql.append("WHERE EffectiveDate <=  '");
//            sql.append(effectiveDate);
//            sql.append("')");
//
//            filteredFundVO = (FilteredFundVO[]) executeQuery(FilteredFundVO.class,
//                                                              sql.toString(),
//                                                               POOLNAME,
//                                                                true,
//                                                                 voExclusionList,
//                                                                    tableNames);
//        }
//
//        return filteredFundVO;
//	}

	public FilteredFundVO[] findByProductStructureId(long productStructureId, boolean includeChildVOs, List voExclusionList)
    {
        DBTable productFilteredFundStructureDBTable = DBTable.getDBTableForTable("ProductFilteredFundStructure");

        String productFilteredFundStructureTable = productFilteredFundStructureDBTable.getFullyQualifiedTableName();

        String filteredFundPKCol = DBTABLE.getDBColumn("FilteredFundPK").getFullyQualifiedColumnName();

        String filteredFundFKCol     = productFilteredFundStructureDBTable.getDBColumn("FilteredFundFK").getFullyQualifiedColumnName();
        String productStructureFKCol = productFilteredFundStructureDBTable.getDBColumn("ProductStructureFK").getFullyQualifiedColumnName();

        String sql = " SELECT * FROM " + TABLENAME +
                     " WHERE " + filteredFundPKCol + " IN (" +
                     " SELECT " + filteredFundFKCol + " FROM "+ productFilteredFundStructureTable +
                     " WHERE " + productStructureFKCol + " = " + productStructureId + ")";

        return (FilteredFundVO[]) executeQuery(FilteredFundVO.class,
                                                sql,
                                                 POOLNAME,
                                                  includeChildVOs,
                                                   voExclusionList);
	}

    public FilteredFundVO[] findByMarketingPackage(String marketingPackageName, boolean includeChildVOs, List voExclusionList)
    {
        DBTable productFilteredFundStructureDBTable = DBTable.getDBTableForTable("ProductFilteredFundStructure");
        DBTable productStructureDBTable = DBTable.getDBTableForTable("ProductStructure");

        String productFilteredFundStructureTable = productFilteredFundStructureDBTable.getFullyQualifiedTableName();
        String productStructureTable = productStructureDBTable.getFullyQualifiedTableName();

        String filteredFundPKCol = DBTABLE.getDBColumn("FilteredFundPK").getFullyQualifiedColumnName();

        String filteredFundFKCol     = productFilteredFundStructureDBTable.getDBColumn("FilteredFundFK").getFullyQualifiedColumnName();
        String productStructureFKCol = productFilteredFundStructureDBTable.getDBColumn("ProductStructureFK").getFullyQualifiedColumnName();

        String productStructurePKCol = productStructureDBTable.getDBColumn("ProductStructurePK").getFullyQualifiedColumnName();
        String marketingPackageNameCol = productStructureDBTable.getDBColumn("MarketingPackageName").getFullyQualifiedColumnName();

        String sql = " SELECT * FROM " + TABLENAME +
                     " WHERE " + filteredFundPKCol + " IN (" +
                     " SELECT " + filteredFundFKCol + " FROM "+ productFilteredFundStructureTable + ", " + productStructureTable +
                     " WHERE " + productStructureFKCol + " IN (SELECT " + productStructurePKCol + " FROM " + productStructureTable +
                     " WHERE " + marketingPackageNameCol + " = '" + marketingPackageName + "'))";

        return (FilteredFundVO[]) executeQuery(FilteredFundVO.class,
                                                sql,
                                                 POOLNAME,
                                                  includeChildVOs,
                                                   voExclusionList);
    }

    public FilteredFundVO[] findByProductStructureId_AND_FundType(long productStructureId, String fundType,
                                                                    boolean includeChildVOs, List voExclusionList)
    {
        DBTable fundDBTable                         = DBTable.getDBTableForTable("Fund");
        DBTable productFilteredFundStructureDBTable = DBTable.getDBTableForTable("ProductFilteredFundStructure");

        String fundTable                         = fundDBTable.getFullyQualifiedTableName();
        String productFilteredFundStructureTable = productFilteredFundStructureDBTable.getFullyQualifiedTableName();


        String fundFKCol         = DBTABLE.getDBColumn("FundFK").getFullyQualifiedColumnName();
        String filteredFundPKCol = DBTABLE.getDBColumn("FilteredFundPK").getFullyQualifiedColumnName();

        String fundPKCol   = fundDBTable.getDBColumn("FundPK").getFullyQualifiedColumnName();
        String fundTypeCol = fundDBTable.getDBColumn("FundType").getFullyQualifiedColumnName();

        String filteredFundFKCol     = productFilteredFundStructureDBTable.getDBColumn("FilteredFundFK").getFullyQualifiedColumnName();
        String productStructureFKCol = productFilteredFundStructureDBTable.getDBColumn("ProductStructureFK").getFullyQualifiedColumnName();


        String sql = " SELECT * FROM " + TABLENAME +
                     " WHERE " + fundFKCol + " IN (SELECT " + fundPKCol +
                     " FROM " + fundTable + " WHERE " + fundTypeCol + " = '" + fundType + "')" +
                     " AND " + filteredFundPKCol + " IN (SELECT " + filteredFundFKCol +
                     " FROM " + productFilteredFundStructureTable + " WHERE " + productStructureFKCol +
                     " = " + productStructureId + ")";

        return (FilteredFundVO[]) executeQuery(FilteredFundVO.class,
                                                sql,
                                                 POOLNAME,
                                                  includeChildVOs,
                                                   voExclusionList);
	}

 	public FilteredFundVO[] findSpecificUnitValue(long productStructureId, long fundId, String effectiveDate)
     {
         Connection conn = ConnectionFactory.getSingleton().getConnection(POOLNAME);

         PreparedStatement ps = null;

         FilteredFundVO[] filteredFundVOs = null;

         DBTable productFilteredFundStructureDBTable = DBTable.getDBTableForTable("ProductFilteredFundStructure");

         String productFilteredFundStructureTable = productFilteredFundStructureDBTable.getFullyQualifiedTableName();

         String fundFKCol         = DBTABLE.getDBColumn("FundFK").getFullyQualifiedColumnName();
         String filteredFundPKCol = DBTABLE.getDBColumn("FilteredFundPK").getFullyQualifiedColumnName();
         String effectiveDateCol  = DBTABLE.getDBColumn("EffectiveDate").getFullyQualifiedColumnName();

         String productStructureFKCol = productFilteredFundStructureDBTable.getDBColumn("ProductStructureFK").getFullyQualifiedColumnName();
         String filteredFundFKCol     = productFilteredFundStructureDBTable.getDBColumn("FilteredFundFK").getFullyQualifiedColumnName();

         String sql = " SELECT " + TABLENAME + ".* FROM " + TABLENAME +
                     ", " + productFilteredFundStructureTable +
                     " WHERE " + productStructureFKCol + " = ?" +
                     " AND " + fundFKCol + " = ?" +
                     " AND " + filteredFundPKCol + " = " + filteredFundFKCol +
                     " AND " + effectiveDateCol + " = (" +
                     " SELECT MAX(" + effectiveDateCol + ") FROM " + TABLENAME +
                     ", " + productFilteredFundStructureTable + " WHERE " + effectiveDateCol +
                     " <= ?" + " AND " + productStructureFKCol + " = ?" +
                     " AND " + fundFKCol + " = ?" +
                     " AND " + filteredFundPKCol + " = " + filteredFundFKCol + ")";

         List voExclusionList = new ArrayList();
         voExclusionList.add(InterestRateParametersVO.class);
         voExclusionList.add(UnitValuesVO.class);

         try
         {
             ps = conn.prepareStatement(sql);

             ps.setLong(1, productStructureId);
             ps.setLong(2, fundId);
             ps.setDate(3, DBUtil.convertStringToDate(effectiveDate));
             ps.setLong(4, productStructureId);
             ps.setLong(5, fundId);

             filteredFundVOs = (FilteredFundVO[]) executeQuery(FilteredFundVO.class,
                                                                ps,
                                                                 POOLNAME,
                                                                  true,
                                                                   voExclusionList);
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

               ps = null;

               if (conn != null) conn.close();

               conn = null;
             }
             catch (SQLException e)
             {
                 System.out.println(e);

                 e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.

                 throw new RuntimeException(e);
             }
         }

         return filteredFundVOs;
    }

    public FilteredFundVO[] findByCSIdFundId(long productStructureId, long fundId)
    {
        DBTable cffsDBTable = DBTable.getDBTableForTable("ProductFilteredFundStructure");
        String cffsTableName = cffsDBTable.getFullyQualifiedTableName();

        String productStructureFKCol = cffsDBTable.getDBColumn("ProductStructureFK").getFullyQualifiedColumnName();
        String filteredFundFKCol = cffsDBTable.getDBColumn("FilteredFundFK").getFullyQualifiedColumnName();

        String fundFKCol = DBTABLE.getDBColumn("FundFK").getFullyQualifiedColumnName();
        String filteredFundPKCol = DBTABLE.getDBColumn("FilteredFundPK").getFullyQualifiedColumnName();

		String sql = "SELECT " + TABLENAME + ".* FROM " + TABLENAME + ", " + cffsTableName +
                     " WHERE " + productStructureFKCol + " = " + productStructureId +
                     " AND " + fundFKCol + " = " + fundId +
                     " AND " + filteredFundPKCol + " = " + filteredFundFKCol;

        List voExclusionList = new ArrayList();
        voExclusionList.add(UnitValuesVO.class);
        voExclusionList.add(InterestRateParametersVO.class);

        return (FilteredFundVO[]) executeQuery(FilteredFundVO.class,
                                                sql.toString(),
                                                  POOLNAME,
                                                   false,
                                                    voExclusionList);
	}

    public FilteredFundVO[] findByMarketingPackageFundId(String marketingPackageName, long fundId)
    {
        DBTable cffsDBTable = DBTable.getDBTableForTable("ProductFilteredFundStructure");
        String cffsTableName = cffsDBTable.getFullyQualifiedTableName();

        DBTable productStructureDBTable = DBTable.getDBTableForTable("ProductStructure");
        String productStructureTableName = productStructureDBTable.getFullyQualifiedTableName();

        String productStructureFKCol = cffsDBTable.getDBColumn("ProductStructureFK").getFullyQualifiedColumnName();
        String filteredFundFKCol = cffsDBTable.getDBColumn("FilteredFundFK").getFullyQualifiedColumnName();

        String fundFKCol = DBTABLE.getDBColumn("FundFK").getFullyQualifiedColumnName();
        String filteredFundPKCol = DBTABLE.getDBColumn("FilteredFundPK").getFullyQualifiedColumnName();

        String productStructurePKCol = productStructureDBTable.getDBColumn("ProductStructurePK").getFullyQualifiedColumnName();
        String marketingPackageNameCol = productStructureDBTable.getDBColumn("MarketingPackageName").getFullyQualifiedColumnName();

		String sql = "SELECT " + TABLENAME + ".* FROM " + TABLENAME + ", " + cffsTableName +
                     " WHERE " + productStructureFKCol + " IN (SELECT " + productStructurePKCol + " FROM "
                     + productStructureTableName + " WHERE " + marketingPackageNameCol + " = '" + marketingPackageName + "')" +
                     " AND " + fundFKCol + " = " + fundId +
                     " AND " + filteredFundPKCol + " = " + filteredFundFKCol;

        List voExclusionList = new ArrayList();
        voExclusionList.add(UnitValuesVO.class);
        voExclusionList.add(InterestRateParametersVO.class);

        return (FilteredFundVO[]) executeQuery(FilteredFundVO.class,
                                                sql.toString(),
                                                  POOLNAME,
                                                   false,
                                                    voExclusionList);
	}

    public FilteredFundVO[] findByFilteredFundPK(long filteredFundPK)
    {
		String filteredFundPKCol = DBTABLE.getDBColumn("FilteredFundPK").getFullyQualifiedColumnName();

        String sql = " SELECT * FROM " + TABLENAME +
                     " WHERE " + filteredFundPKCol + " = " + filteredFundPK;

        return (FilteredFundVO[]) executeQuery(FilteredFundVO.class,
                                                sql,
                                                 POOLNAME,
                                                  false,
                                                   null);
	}

    public FilteredFundVO[] findByFilteredFundPK(long filteredFundPK, boolean includeChildVOs, List voClassExclusionList)
    {
        String filteredFundPKCol = DBTABLE.getDBColumn("FilteredFundPK").getFullyQualifiedColumnName();

        String sql = " SELECT * FROM " + TABLENAME +
                     " WHERE " + filteredFundPKCol + " = " + filteredFundPK;

        return (FilteredFundVO[]) executeQuery(FilteredFundVO.class,
                                                sql,
                                                 POOLNAME,
                                                  includeChildVOs,
                                                   voClassExclusionList);
	}

	public FilteredFundVO[] findByFundNumber(String fundNumber)
    {
        String fundNumberCol = DBTABLE.getDBColumn("FundNumber").getFullyQualifiedColumnName();

        String sql = " SELECT * FROM " + TABLENAME +
                     " WHERE " + fundNumberCol + " = '" + fundNumber + "'";

        return (FilteredFundVO[]) executeQuery(FilteredFundVO.class,
                                                sql,
                                                  POOLNAME,
                                                    false,
                                                     null);
	}

    public FilteredFundVO[] findAllFilteredFunds()
    {
        String sql = " SELECT * FROM " + TABLENAME;

        return (FilteredFundVO[]) executeQuery(FilteredFundVO.class,
                                                sql,
                                                 POOLNAME,
                                                  false,
                                                   null);
	}

    public FilteredFundVO[] findAttached(long productStructurePK, boolean includeChildVOs, List voExclusionList) throws Exception
    {
        DBTable productFilteredFundStructureDBTable = DBTable.getDBTableForTable("ProductFilteredFundStructure");

        String productFilteredFundStructureTable = productFilteredFundStructureDBTable.getFullyQualifiedTableName();

        String filteredFundPKCol = DBTABLE.getDBColumn("FilteredFundPK").getFullyQualifiedColumnName();

        String productStructureFKCol = productFilteredFundStructureDBTable.getDBColumn("ProductStructureFK").getFullyQualifiedColumnName();
        String filteredFundFKCol     = productFilteredFundStructureDBTable.getDBColumn("FilteredFundFK").getFullyQualifiedColumnName();

        String sql = " SELECT " + TABLENAME + ".* FROM " + TABLENAME +
                     ", " + productFilteredFundStructureTable +
                     " WHERE " + productStructureFKCol + " = " + productStructurePK +
                     " AND " + filteredFundFKCol + " = " + filteredFundPKCol;

        return (FilteredFundVO[]) executeQuery(FilteredFundVO.class,
                                                sql,
                                                 POOLNAME,
                                                  includeChildVOs,
                                                   voExclusionList);
	}

   public FilteredFundVO[] findAllFilteredFunds(boolean includeChildVOs, List voExclusionList)
   {
        String sql = "SELECT * FROM " + TABLENAME;

        return (FilteredFundVO[]) executeQuery(FilteredFundVO.class,
                                                sql,
                                                 POOLNAME,
                                                  includeChildVOs,
                                                   voExclusionList);
	}

    public FilteredFundVO[] findFilteredFundbyPKAndFundType(long filteredFundPK, boolean includeChildVOs,
                                                             List voExclusionList)
    {
        DBTable fundDBTable = DBTable.getDBTableForTable("Fund");

        String fundTable = fundDBTable.getFullyQualifiedTableName();


        String filteredFundPKCol = DBTABLE.getDBColumn("FilteredFundPK").getFullyQualifiedColumnName();
        String fundFKCol         = DBTABLE.getDBColumn("FundFK").getFullyQualifiedColumnName();

        String fundPKCol   = fundDBTable.getDBColumn("FundPK").getFullyQualifiedColumnName();
        String fundTypeCol = fundDBTable.getDBColumn("FundType").getFullyQualifiedColumnName();


        String sql = " SELECT " + TABLENAME + ".* FROM " + TABLENAME +
                     ", " + fundTable + " WHERE " + filteredFundPKCol + " = " + filteredFundPK +
                     " AND " + fundFKCol + " = " + fundPKCol +
                     " AND " + fundTypeCol + " = 'MVA'";

        return (FilteredFundVO[]) executeQuery(FilteredFundVO.class,
                                               sql,
                                                POOLNAME,
                                                 includeChildVOs,
                                                  voExclusionList);
    }

    /**
     * Retrieves all FilteredFund records whose parent Fund's FundType = 'Hedge'
     * @return
     */
    public FilteredFundVO[] findAllHedgeFunds()
    {
        DBTable fundDBTable = DBTable.getDBTableForTable("Fund");

        String fundTable = fundDBTable.getFullyQualifiedTableName();

        String fundTypeCol = fundDBTable.getDBColumn("FundType").getFullyQualifiedColumnName();
        String fundPKCol = fundDBTable.getDBColumn("FundPK").getFullyQualifiedColumnName();
        String fundFKCol = DBTABLE.getDBColumn("FundFK").getFullyQualifiedColumnName();

        String sql = " SELECT " + TABLENAME + ".* FROM " + TABLENAME + ", " + fundTable +
                     " WHERE " + fundTypeCol + " = 'Hedge'" +
                     " AND " + fundPKCol + " = " + fundFKCol;

         return (FilteredFundVO[]) executeQuery(FilteredFundVO.class,
                                                 sql,
                                                  POOLNAME,
                                                   false,
                                                    new ArrayList());
    }
}
