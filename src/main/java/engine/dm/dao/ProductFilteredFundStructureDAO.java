/*
 * Created by IntelliJ IDEA.
 * User: cgleason
 * Date: Mar 14, 2003
 * Time: 1:38:44 PM
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

import java.util.ArrayList;
import java.util.List;


public class ProductFilteredFundStructureDAO extends DAO
{
    private final String POOLNAME;
    private final DBTable DBTABLE;
    private final String TABLENAME;


    public ProductFilteredFundStructureDAO()
    {
        POOLNAME  = ConnectionFactory.ENGINE_POOL;
        DBTABLE   = DBTable.getDBTableForTable("ProductFilteredFundStructure");
        TABLENAME = DBTABLE.getFullyQualifiedTableName();
    }

    public ProductFilteredFundStructureVO[] findByProductStructurePK(long productStructurePK, boolean includeChildVOs, List voExclusionList) {

        String productStructureFKCol = DBTABLE.getDBColumn("ProductStructureFK").getFullyQualifiedColumnName();

        String sql = " SELECT * FROM " + TABLENAME +
                     " WHERE " + productStructureFKCol + " = " + productStructurePK;

        return (ProductFilteredFundStructureVO[]) executeQuery(ProductFilteredFundStructureVO.class,
                                                                sql,
                                                                 POOLNAME,
                                                                  includeChildVOs,
                                                                   voExclusionList);
	}

    /**
     * Retrieves all ProductFilteredFundStructure records whose ProductStructureFK and FilteredFundFK values match
     * the productStructurePK and filteredFundFK parameter values
     * @param productStructurePK
     * @param filteredFundFK
     * @return
     */
    public ProductFilteredFundStructureVO[] findByCoStructFKAndFltrdFundFK(long productStructurePK, long filteredFundFK)
    {
        String productStructureFKCol = DBTABLE.getDBColumn("ProductStructureFK").getFullyQualifiedColumnName();
        String filteredFundFKCol = DBTABLE.getDBColumn("FilteredFundFK").getFullyQualifiedColumnName();

        String sql = " SELECT * FROM " + TABLENAME +
                     " WHERE " + productStructureFKCol + " = " + productStructurePK +
                     " AND " + filteredFundFKCol + " = " + filteredFundFK;

        return (ProductFilteredFundStructureVO[]) executeQuery(ProductFilteredFundStructureVO.class,
                                                                sql,
                                                                 POOLNAME,
                                                                  false,
                                                                   null);
	}

    public ProductFilteredFundStructureVO[] findByNaturalKey(long productStructurePK, long filteredFundPK,
                                                             boolean includeChildVOs, List voClassExclusionList)
    {
        String productStructureFKCol = DBTABLE.getDBColumn("ProductStructureFK").getFullyQualifiedColumnName();
        String filteredFundFKCol     = DBTABLE.getDBColumn("FilteredFundFK").getFullyQualifiedColumnName();

        String sql = " SELECT * FROM " + TABLENAME +
                     " WHERE " + productStructureFKCol + " = " + productStructurePK +
                     " AND " + filteredFundFKCol + " = " + filteredFundPK;

        return (ProductFilteredFundStructureVO[]) executeQuery(ProductFilteredFundStructureVO.class,
                                                                sql,
                                                                 POOLNAME,
                                                                  includeChildVOs,
                                                                   voClassExclusionList);
	}

    public ProductFilteredFundStructureVO[] findByPK(long productFilteredFundStructureId)
    {
        String productFilteredFundStructurePKCol = DBTABLE.getDBColumn("ProductFilteredFundStructurePK").getFullyQualifiedColumnName();

        String sql = " SELECT * FROM " + TABLENAME +
                     " WHERE " + productFilteredFundStructurePKCol + " = " + productFilteredFundStructureId;

        List voExclusionList = new ArrayList();
        voExclusionList.add(ProductStructureVO.class);
        voExclusionList.add(FilteredFundVO.class);

        return (ProductFilteredFundStructureVO[]) executeQuery(ProductFilteredFundStructureVO.class,
                                                                sql,
                                                                 POOLNAME,
                                                                  false,
                                                                   voExclusionList);
	}

    public ProductFilteredFundStructureVO[] findByFilteredFundFK(long filteredFundFK)
    {
        String filteredFundFKCol = DBTABLE.getDBColumn("FilteredFundFK").getFullyQualifiedColumnName();

        String sql = " SELECT * FROM " + TABLENAME +
                     " WHERE " + filteredFundFKCol + " = " + filteredFundFK;

        return (ProductFilteredFundStructureVO[]) executeQuery(ProductFilteredFundStructureVO.class,
                                                                sql,
                                                                 POOLNAME,
                                                                  false,
                                                                   new ArrayList());
	}

    public ProductFilteredFundStructureVO[] findByCoStructFKAndFundFK(long productStructureFK, long fundFK)
    {
        DBTable FilteredFundDBTable = DBTable.getDBTableForTable("FilteredFund");
        String FilteredFundTableName =  FilteredFundDBTable.getFullyQualifiedTableName();
        String filteredFundPKCol = FilteredFundDBTable.getDBColumn("FilteredFundPK").getFullyQualifiedColumnName();
        String fundFKCol = FilteredFundDBTable.getDBColumn("FundFK").getFullyQualifiedColumnName();

        DBTable FundDBTable = DBTable.getDBTableForTable("Fund");
        String FundTableName = FundDBTable.getFullyQualifiedTableName();
        String fundPKCol = FundDBTable.getDBColumn("FundPK").getFullyQualifiedColumnName();

        String productStructureFKCol = DBTABLE.getDBColumn("ProductStructureFK").getFullyQualifiedColumnName();
        String filteredFundFKCol = DBTABLE.getDBColumn("FilteredFundFK").getFullyQualifiedColumnName();

        String sql = "SELECT * FROM " + TABLENAME +
                     " INNER JOIN  " + FilteredFundTableName +
                     " ON " + filteredFundFKCol + " =  " + filteredFundPKCol +
                     " INNER JOIN " +  FundTableName + " ON " + fundPKCol + " =  " + fundFKCol +
                     " WHERE " + productStructureFKCol + " =  " + productStructureFK +
                     " AND " + fundFKCol + " =  " + fundFK;

        return (ProductFilteredFundStructureVO[]) executeQuery(ProductFilteredFundStructureVO.class,
                                                                sql,
                                                                 POOLNAME,
                                                                  false,
                                                                   null);
    }
}