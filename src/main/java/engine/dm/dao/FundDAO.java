/*
 * FundDAO.java      Version 1.1  03/15/2002
 *
 * (c) 2000-2004 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */

package engine.dm.dao;

import edit.common.vo.FundVO;
import edit.common.vo.InterestRateParametersVO;
import edit.common.vo.UnitValuesVO;
import edit.common.vo.FilteredFundVO;
import edit.services.db.ConnectionFactory;
import edit.services.db.DAO;
import edit.services.db.DBTable;

import java.util.ArrayList;
import java.util.List;


public class FundDAO extends DAO
{
    private final String POOLNAME;
    private final DBTable DBTABLE;
    private final String TABLENAME;


    public FundDAO()
    {
        POOLNAME  = ConnectionFactory.ENGINE_POOL;
        DBTABLE   = DBTable.getDBTableForTable("Fund");
        TABLENAME = DBTABLE.getFullyQualifiedTableName();
    }

    public FundVO[] findAll(boolean includeChildVOs, List voExclusionList)
    {
		String sql = " SELECT * FROM " + TABLENAME;

        return (FundVO[]) executeQuery(FundVO.class,
                                        sql,
                                         POOLNAME,
                                          includeChildVOs,
                                           voExclusionList);
    }

    public FundVO[] findAllFundsNonRecursively()
    {
		String sql = " SELECT * FROM " + TABLENAME;

        return (FundVO[]) executeQuery(FundVO.class,
                                        sql,
                                         POOLNAME,
                                          false,
                                           null);
	}

	public FundVO[] findFundsByCSId(long productStructureFK)
    {
        DBTable filteredFundDBTable                 = DBTable.getDBTableForTable("FilteredFund");
        DBTable productFilteredFundStructureDBTable = DBTable.getDBTableForTable("ProductFilteredFundStructure");

        String filteredFundTable                 = filteredFundDBTable.getFullyQualifiedTableName();
        String productFilteredFundStructureTable = productFilteredFundStructureDBTable.getFullyQualifiedTableName();


        String fundPKCol = DBTABLE.getDBColumn("FundPK").getFullyQualifiedColumnName();

        String filteredFundPKCol = filteredFundDBTable.getDBColumn("FilteredFundPK").getFullyQualifiedColumnName();
        String fundFKCol         = filteredFundDBTable.getDBColumn("FundFK").getFullyQualifiedColumnName();

        String filteredFundFKCol     = productFilteredFundStructureDBTable.getDBColumn("FilteredFundFK").getFullyQualifiedColumnName();
        String productStructureFKCol = productFilteredFundStructureDBTable.getDBColumn("ProductStructureFK").getFullyQualifiedColumnName();

        //returns fundvo only
        String sql = " SELECT DISTINCT " + TABLENAME + ".* FROM " + TABLENAME +
                     ", " + filteredFundTable + ", " + productFilteredFundStructureTable +
                     " WHERE " + productStructureFKCol + " = " + productStructureFK +
                     " AND " + filteredFundPKCol + " = " + filteredFundFKCol +
                     " AND " + fundPKCol + " = " + fundFKCol;

        return (FundVO[]) executeQuery(FundVO.class,
                                        sql,
                                         POOLNAME,
                                          false,
                                           null);
	}

    public FundVO[] findFundsByMarketingPackage(String marketingPackageName)
    {
        DBTable filteredFundDBTable                 = DBTable.getDBTableForTable("FilteredFund");
        DBTable productFilteredFundStructureDBTable = DBTable.getDBTableForTable("ProductFilteredFundStructure");
        DBTable productStructureDBTable = DBTable.getDBTableForTable("ProductStructure");

        String filteredFundTable                 = filteredFundDBTable.getFullyQualifiedTableName();
        String productFilteredFundStructureTable = productFilteredFundStructureDBTable.getFullyQualifiedTableName();
        String productStructureTable = productStructureDBTable.getFullyQualifiedTableName();

        String fundPKCol = DBTABLE.getDBColumn("FundPK").getFullyQualifiedColumnName();

        String filteredFundPKCol = filteredFundDBTable.getDBColumn("FilteredFundPK").getFullyQualifiedColumnName();
        String fundFKCol         = filteredFundDBTable.getDBColumn("FundFK").getFullyQualifiedColumnName();

        String filteredFundFKCol     = productFilteredFundStructureDBTable.getDBColumn("FilteredFundFK").getFullyQualifiedColumnName();
        String productStructureFKCol = productFilteredFundStructureDBTable.getDBColumn("ProductStructureFK").getFullyQualifiedColumnName();

        String productStructurePKCol = productStructureDBTable.getDBColumn("ProductStructurePK").getFullyQualifiedColumnName();
        String marketingPackageNameCol = productStructureDBTable.getDBColumn("MarketingPackageName").getFullyQualifiedColumnName();

        //returns fundvo only
        String sql = " SELECT DISTINCT " + TABLENAME + ".* FROM " + TABLENAME +
                     ", " + filteredFundTable + ", " + productFilteredFundStructureTable +
                     " WHERE " + productStructureFKCol + " IN (SELECT " + productStructurePKCol + " FROM " +
                     productStructureTable + " WHERE " + marketingPackageNameCol + " = '" + marketingPackageName + "')" +
                     " AND " + filteredFundPKCol + " = " + filteredFundFKCol +
                     " AND " + fundPKCol + " = " + fundFKCol;

        return (FundVO[]) executeQuery(FundVO.class,
                                        sql,
                                         POOLNAME,
                                          false,
                                           null);
    }

    public FundVO[] findFundsByCSIdAndFundPKWithExlusions(long productStructurePK, long fundPK)
    {
        DBTable filteredFundDBTable                 = DBTable.getDBTableForTable("FilteredFund");
        DBTable productFilteredFundStructureDBTable = DBTable.getDBTableForTable("ProductFilteredFundStructure");

        String filteredFundTable                 = filteredFundDBTable.getFullyQualifiedTableName();
        String productFilteredFundStructureTable = productFilteredFundStructureDBTable.getFullyQualifiedTableName();


        String fundPKCol = DBTABLE.getDBColumn("FundPK").getFullyQualifiedColumnName();

        String filteredFundPKCol = filteredFundDBTable.getDBColumn("FilteredFundPK").getFullyQualifiedColumnName();
        String fundFKCol         = filteredFundDBTable.getDBColumn("FundFK").getFullyQualifiedColumnName();

        String filteredFundFKCol     = productFilteredFundStructureDBTable.getDBColumn("FilteredFundFK").getFullyQualifiedColumnName();
        String productStructureFKCol = productFilteredFundStructureDBTable.getDBColumn("ProductStructureFK").getFullyQualifiedColumnName();


        String sql = " SELECT * FROM " + TABLENAME + ", " + filteredFundTable +
                     ", " + productFilteredFundStructureTable +
                     " WHERE " + fundFKCol + " = " + fundPKCol +
                     " AND " + productStructureFKCol + " = " + productStructurePK +
                     " AND " + filteredFundPKCol + " = " + filteredFundFKCol +
                     " AND " + fundPKCol + " = " + fundPK;

        List voExclusionList = new ArrayList();
        voExclusionList.add(UnitValuesVO.class);
        voExclusionList.add(InterestRateParametersVO.class);

        return (FundVO[]) executeQuery(FundVO.class,
                                        sql,
                                         POOLNAME,
                                          true,
                                           voExclusionList);

    }

    public FundVO[] findFundByPK(long fundPK)
    {
        String fundPKCol = DBTABLE.getDBColumn("FundPK").getFullyQualifiedColumnName();

        String sql = " SELECT * FROM " + TABLENAME +
                     " WHERE " + fundPKCol + " = " + fundPK;

        return (FundVO[]) executeQuery(FundVO.class,
                                        sql,
                                         POOLNAME,
                                          true,
                                           null);

	}

    public FundVO[] findFundByPK(long fundPK, boolean includeChildVOs, List voExclusionList)
    {
        String fundPKCol = DBTABLE.getDBColumn("FundPK").getFullyQualifiedColumnName();

        String sql = " SELECT * FROM " + TABLENAME +
                     " WHERE " + fundPKCol + " = " + fundPK;

        return (FundVO[]) executeQuery(FundVO.class,
                                        sql,
                                         POOLNAME,
                                          includeChildVOs,
                                           voExclusionList);
	}

    public FundVO[] findFundByFilteredFundFK(long filteredFundPK, boolean includeChildVOs, List voExclusionList)
    {
        DBTable filteredFundDBTable = DBTable.getDBTableForTable("FilteredFund");

        String filteredFundTable = filteredFundDBTable.getFullyQualifiedTableName();


        String fundPKCol = DBTABLE.getDBColumn("FundPK").getFullyQualifiedColumnName();

        String fundFKCol         = filteredFundDBTable.getDBColumn("FundFK").getFullyQualifiedColumnName();
        String filteredFundPKCol = filteredFundDBTable.getDBColumn("FilteredFundPK").getFullyQualifiedColumnName();

        String sql = " SELECT * FROM " + TABLENAME +
                     " WHERE " + fundPKCol + " = " +
                     " (SELECT " + fundFKCol + " FROM " + filteredFundTable +
                     " WHERE " + filteredFundPKCol + " = " + filteredFundPK + ")";

        return (FundVO[]) executeQuery(FundVO.class,
                                        sql,
                                         POOLNAME,
                                          includeChildVOs,
                                           voExclusionList);

	}

    public FundVO[] findAllFixedFunds()
    {
        String fundTypeCol = DBTABLE.getDBColumn("FundType").getFullyQualifiedColumnName();

        String sql = " SELECT * FROM " + TABLENAME +
                     " WHERE (" + fundTypeCol + " = 'Fixed' " +
                     " OR " + fundTypeCol + " = 'MVA')";

        List voExclusionList = new ArrayList();
        voExclusionList.add(UnitValuesVO.class);

        return (FundVO[]) executeQuery(FundVO.class,
                                        sql,
                                         POOLNAME,
                                          true,
                                           voExclusionList);
	}

    public FundVO[] findAllVariableFunds()
    {
        String fundTypeCol = DBTABLE.getDBColumn("FundType").getFullyQualifiedColumnName();

        String sql = " SELECT * FROM " + TABLENAME +
                     " WHERE (" + fundTypeCol + " = 'Variable' " +
                     " OR " + fundTypeCol + " = 'EquityIndex' " +
                     " OR " + fundTypeCol + " = 'Hedge')";

        return (FundVO[]) executeQuery(FundVO.class,
                                        sql,
                                         POOLNAME,
                                          false,
                                           null);
 	}

    public FundVO[] findFundbyFilteredFundPK(long filteredFundPK, boolean includeChildVOs, List voExclusionList)
    {
        DBTable filteredFundDBTable = DBTable.getDBTableForTable("FilteredFund");

        String filteredFundTable = filteredFundDBTable.getFullyQualifiedTableName();


        String fundPKCol = DBTABLE.getDBColumn("FundPK").getFullyQualifiedColumnName();

        String filteredFundPKCol = filteredFundDBTable.getDBColumn("FilteredFundPK").getFullyQualifiedColumnName();
        String fundFKCol         = filteredFundDBTable.getDBColumn("FundFK").getFullyQualifiedColumnName();

        String sql = " SELECT " + TABLENAME + ".* FROM " + filteredFundTable +
                     ", " + TABLENAME + " WHERE " + filteredFundPKCol +
                     " = " + filteredFundPK + " AND " + fundPKCol +
                     " = " + fundFKCol;


        return (FundVO[]) executeQuery(FundVO.class,
                                         sql,
                                           POOLNAME,
                                            includeChildVOs,
                                              voExclusionList);
    }

    public FundVO[] findAllFundsByActivityFileInd(String activityFileInd)
    {
        String excludeFromActivityFileIndCol = DBTABLE.getDBColumn("ExcludeFromActivityFileInd").getFullyQualifiedColumnName();

        String whereClause = " WHERE " + excludeFromActivityFileIndCol + " = '" + activityFileInd + "'";
        if (activityFileInd.equalsIgnoreCase("N"))
        {
            whereClause = whereClause + " OR " + excludeFromActivityFileIndCol + " IS NULL";
        }

        String sql = " SELECT * FROM " + TABLENAME + whereClause;

        return (FundVO[]) executeQuery(FundVO.class,
                                       sql,
                                       POOLNAME,
                                       false,
                                       null);
	}
}
