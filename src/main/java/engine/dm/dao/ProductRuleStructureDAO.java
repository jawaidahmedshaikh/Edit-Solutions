/*
 * ProductRuleStructureDAO.java      Version 1.1  07/26/2001
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package engine.dm.dao;

import edit.common.vo.*;
import edit.services.db.ConnectionFactory;
import edit.services.db.DAO;
import edit.services.db.DBTable;

import java.util.List;


public class ProductRuleStructureDAO extends DAO
{
    private final String POOLNAME;
    private final DBTable DBTABLE;
    private final String TABLENAME;


    public ProductRuleStructureDAO()
    {
        POOLNAME  = ConnectionFactory.ENGINE_POOL;
        DBTABLE   = DBTable.getDBTableForTable("ProductRuleStructure");
        TABLENAME = DBTABLE.getFullyQualifiedTableName();
    }

    public ProductRuleStructureVO[] findByRulesPK(long rulesPK, boolean includeChildVOs, List voExclusionList)
    {
        String rulesFKCol = DBTABLE.getDBColumn("RulesFK").getFullyQualifiedColumnName();

        String sql = " SELECT * FROM " + TABLENAME +
                     " WHERE " + rulesFKCol + " = " + rulesPK;

        return (ProductRuleStructureVO[]) executeQuery(ProductRuleStructureVO.class,
                                                       sql,
                                                       POOLNAME,
                                                       includeChildVOs,
                                                       voExclusionList);
    }


    public ProductRuleStructureVO[] findAllByRulePK(long rulesPK)
    {
        String rulesFKCol = DBTABLE.getDBColumn("RulesFK").getFullyQualifiedColumnName();

        String sql = " SELECT * FROM " + TABLENAME +
                     " WHERE " + rulesFKCol + " = " + rulesPK;

        return (ProductRuleStructureVO[]) executeQuery(ProductRuleStructureVO.class,
                                                       sql,
                                                       POOLNAME,
                                                       false,
                                                       null);
    }

    public ProductRuleStructureVO[] findByExtendedKey(long productStructurePK, long rulesPK)
    {
        String productStructureFKCol = DBTABLE.getDBColumn("ProductStructureFK").getFullyQualifiedColumnName();
        String rulesFKCol            = DBTABLE.getDBColumn("RulesFK").getFullyQualifiedColumnName();

        String sql = " SELECT * FROM " + TABLENAME +
                     " WHERE " + productStructureFKCol + " = " + productStructurePK +
                     " AND " + rulesFKCol + " = " + rulesPK;

        return (ProductRuleStructureVO[]) executeQuery(ProductRuleStructureVO.class,
                                                       sql,
                                                       POOLNAME,
                                                       false,
                                                       null);
    }

    public ProductRuleStructureVO[] findAll(boolean includeChildVOs, List voExclusionList)
    {
        String sql = " SELECT * FROM " + TABLENAME;

        return (ProductRuleStructureVO[]) executeQuery(ProductRuleStructureVO.class,
                                                       sql.toString(),
                                                       POOLNAME,
                                                       includeChildVOs,
                                                       voExclusionList);
    }

    public ProductRuleStructureVO[] findAll()
    {
        String sql = " SELECT * FROM " + TABLENAME;

        return (ProductRuleStructureVO[]) executeQuery(ProductRuleStructureVO.class,
                                                       sql.toString(),
                                                       POOLNAME,
                                                       false,
                                                       null);
    }

    public ProductRuleStructureVO[] findAllRulePKsByProductStructurePK(long productStructurePK)
    {
        String productStructureFKCol = DBTABLE.getDBColumn("ProductStructureFK").getFullyQualifiedColumnName();

        String sql = " SELECT * FROM " + TABLENAME +
                     " WHERE " + productStructureFKCol + " = " + productStructurePK;

        return (ProductRuleStructureVO[]) executeQuery(ProductRuleStructureVO.class,
                                                       sql,
                                                       POOLNAME,
                                                       false,
                                                       null);
    }

    public ProductRuleStructureVO[] findByRuleId(long[] ruleIds)
    {
        String rulesFKCol = DBTABLE.getDBColumn("RulesFK").getFullyQualifiedColumnName();

        String sql = " SELECT * FROM " + TABLENAME +
                     " WHERE " + rulesFKCol + " IN(";

        for (int i = 0; i < ruleIds.length; i++) {

            if (i < ruleIds.length - 1) {

                sql += ruleIds[i];
                sql += ", ";
            }

            else {

                sql += ruleIds[i];
            }
        }
        sql += ")";

        return (ProductRuleStructureVO[]) executeQuery(ProductRuleStructureVO.class,
                                                       sql.toString(),
                                                       POOLNAME,
                                                       false,
                                                       null);
    }

    public ProductRuleStructureVO[] findByProductStructurePKAndRulesPK(long productStructurePK, long rulesPK,
                                                                       boolean includeChildVOs, List voExclusionList)
    {
        String rulesFKCol            = DBTABLE.getDBColumn("RulesFK").getFullyQualifiedColumnName();
        String productStructureFKCol = DBTABLE.getDBColumn("ProductStructureFK").getFullyQualifiedColumnName();

        String sql = " SELECT * FROM " + TABLENAME +
                     " WHERE " + rulesFKCol + " = " + rulesPK +
                     " AND " + productStructureFKCol + " = " + productStructurePK;

        return (ProductRuleStructureVO[]) executeQuery(ProductRuleStructureVO.class,
                                                       sql,
                                                       POOLNAME,
                                                       includeChildVOs,
                                                       voExclusionList);
    }

    public ProductRuleStructureVO[] findByProductStructurePK(long productStructurePK, boolean includeChildVOs, List voExclusionList)
    {
        String productStructureFKCol = DBTABLE.getDBColumn("ProductStructureFK").getFullyQualifiedColumnName();

        String sql = " SELECT * FROM " + TABLENAME +
                     " WHERE " + productStructureFKCol + " = " + productStructurePK;

        return (ProductRuleStructureVO[]) executeQuery(ProductRuleStructureVO.class,
                                                       sql,
                                                       POOLNAME,
                                                       includeChildVOs,
                                                       voExclusionList);
    }
}