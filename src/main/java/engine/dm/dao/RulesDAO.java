/*
 * RulesDAO.java      Version 1.1  07/26/2001
 *
 * (c) 2000-2004 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package engine.dm.dao;

import edit.common.vo.RulesVO;
import edit.services.db.ConnectionFactory;
import edit.services.db.DAO;
import edit.services.db.DBTable;

import java.util.List;


public class RulesDAO extends DAO
{
    private final String POOLNAME;
    private final DBTable DBTABLE;
    private final String TABLENAME;


    public RulesDAO()
    {
        POOLNAME  = ConnectionFactory.ENGINE_POOL;
        DBTABLE   = DBTable.getDBTableForTable("Rules");
        TABLENAME = DBTABLE.getFullyQualifiedTableName();
    }

	public RulesVO[] findByRuleNameAndRSId(String ruleName, long ruleStructureId)
    {
        String ruleStructureFKCol = DBTABLE.getDBColumn("RuleStructureFK ").getFullyQualifiedColumnName();
        String ruleNameCol        = DBTABLE.getDBColumn("RuleName").getFullyQualifiedColumnName();

        String sql = " SELECT *  FROM " + TABLENAME +
                     " WHERE " + ruleStructureFKCol + " = " + ruleStructureId +
                     " AND " + ruleNameCol + " = '" + ruleName + "'";

        return (RulesVO[]) executeQuery(RulesVO.class,
                                         sql,
                                          POOLNAME,
                                           false,
                                            null);
	}

    public RulesVO[] findByRuleName(String ruleName)
    {
        String ruleNameCol = DBTABLE.getDBColumn("RuleName").getFullyQualifiedColumnName();

        String sql = " SELECT *  FROM " + TABLENAME +
                     " WHERE " + ruleNameCol + " = '" + ruleName + "'";

        return (RulesVO[]) executeQuery(RulesVO.class,
                                         sql,
                                          POOLNAME,
                                           false,
                                            null);
	}

    public RulesVO[] findAllRulesByCSId(long productStructurePK)
    {
        DBTable productRuleStructureDBTable = DBTable.getDBTableForTable("ProductRuleStructure");

        String productRuleStructureTable = productRuleStructureDBTable.getFullyQualifiedTableName();

        String rulesPKCol  = DBTABLE.getDBColumn("RulesPK").getFullyQualifiedColumnName();
        String ruleNameCol = DBTABLE.getDBColumn("RuleName").getFullyQualifiedColumnName();

        String rulesFKCol            = productRuleStructureDBTable.getDBColumn("RulesFK").getFullyQualifiedColumnName();
        String productStructureFKCol = productRuleStructureDBTable.getDBColumn("ProductStructureFK").getFullyQualifiedColumnName();

        String sql = " SELECT * FROM " + TABLENAME +
                     " WHERE " + rulesPKCol + " IN (SELECT " + rulesFKCol +
                     " FROM " + productRuleStructureTable +
                     " WHERE " + productStructureFKCol + " = " + productStructurePK + ")" +
                     " ORDER BY " + ruleNameCol;

        return (RulesVO[]) executeQuery(RulesVO.class,
                                         sql,
                                          POOLNAME,
                                           false,
                                            null);
	}

    public RulesVO[] findByProductStructurePK(long productStructurePK, boolean includeChildVOs, List voExclusionList)
    {
        DBTable productRuleStructureDBTable = DBTable.getDBTableForTable("ProductRuleStructure");

        String productRuleStructureTable = productRuleStructureDBTable.getFullyQualifiedTableName();


        String rulesPKCol = DBTABLE.getDBColumn("RulesPK").getFullyQualifiedColumnName();

        String rulesFKCol            = productRuleStructureDBTable.getDBColumn("RulesFK").getFullyQualifiedColumnName();
        String productStructureFKCol = productRuleStructureDBTable.getDBColumn("ProductStructureFK").getFullyQualifiedColumnName();


        String sql = " SELECT * FROM " + TABLENAME +
                     " WHERE " + rulesPKCol + " IN (SELECT " + rulesFKCol +
                     " FROM " + productRuleStructureTable +
                     " WHERE " + productStructureFKCol + " = " + productStructurePK + ")";

        return (RulesVO[]) executeQuery(RulesVO.class,
                                         sql,
                                          POOLNAME,
                                           includeChildVOs,
                                            voExclusionList);
    }

	public RulesVO[] findAll(boolean includeChildVOs, List voExclusionList)
    {
        String sql = " SELECT * FROM " + TABLENAME;

        return (RulesVO[]) executeQuery(RulesVO.class,
                                         sql,
                                          POOLNAME,
                                           includeChildVOs,
                                            voExclusionList);
	}

	public RulesVO[] findAllRules()
    {
        String ruleNameCol = DBTABLE.getDBColumn("RuleName").getFullyQualifiedColumnName();

        String sql = " SELECT * FROM " +  TABLENAME + " ORDER BY " + ruleNameCol;

        return (RulesVO[]) executeQuery(RulesVO.class,
                                         sql,
                                          POOLNAME,
                                           false,
                                            null);
	}

	public RulesVO[] findByPK(long rulesPK, boolean includeChildVOs, List voExclusionList)
    {
        String rulesPKCol = DBTABLE.getDBColumn("RulesPK").getFullyQualifiedColumnName();

        String sql = " SELECT * FROM " + TABLENAME +
                     " WHERE " + rulesPKCol + " = " + rulesPK;

        return (RulesVO[]) executeQuery(RulesVO.class,
                                         sql,
                                          POOLNAME,
                                           includeChildVOs,
                                            voExclusionList);
	}

	public RulesVO[] findByRuleId(long ruleId)
    {
        String rulesPKCol = DBTABLE.getDBColumn("RulesPK").getFullyQualifiedColumnName();

        String sql = " SELECT * FROM " + TABLENAME +
                     " WHERE " + rulesPKCol + " = " + ruleId;

        return (RulesVO[]) executeQuery(RulesVO.class,
                                         sql,
                                          POOLNAME,
                                           false,
                                            null);
	}

	public RulesVO[] findByExtendedKey(long ruleStructureId, long scriptId, long tableDefId, String ruleName)
    {
        String ruleStructureFKCol = DBTABLE.getDBColumn("RuleStructureFK").getFullyQualifiedColumnName();
        String scriptFKCol        = DBTABLE.getDBColumn("ScriptFK").getFullyQualifiedColumnName();
        String tableDefFKCol      = DBTABLE.getDBColumn("TableDefFK").getFullyQualifiedColumnName();
        String ruleNameCol        = DBTABLE.getDBColumn("RuleName").getFullyQualifiedColumnName();

        String sql = " SELECT * FROM " + TABLENAME +
                     " WHERE " + ruleStructureFKCol + " = " + ruleStructureId +
                     " AND " + scriptFKCol + " = " + scriptId +
                     " AND " + tableDefFKCol + " = " + tableDefId +
                     " AND " + ruleNameCol + " = '" + ruleName + "'";

        return (RulesVO[]) executeQuery(RulesVO.class,
                                         sql,
                                          POOLNAME,
                                           false,
                                            null);
 }



	public RulesVO[] findAllRulesByScriptId(long scriptId)
    {
        String scriptFKCol = DBTABLE.getDBColumn("ScriptFK").getFullyQualifiedColumnName();

        String sql = " SELECT * FROM " + TABLENAME +
                     " WHERE " + scriptFKCol + " = " + scriptId;

        return (RulesVO[]) executeQuery(RulesVO.class,
                                         sql,
                                          POOLNAME,
                                           false,
                                            null);
	}

	public RulesVO[] findAllRulesByTableDefId(long tableDefId)
    {
        String tableDefFKCol = DBTABLE.getDBColumn("TableDefFK").getFullyQualifiedColumnName();

        String sql = " SELECT * FROM " + TABLENAME +
                     " WHERE " + tableDefFKCol + " = " + tableDefId;

        return (RulesVO[]) executeQuery(RulesVO.class,
                                         sql,
                                          POOLNAME,
                                           false,
                                            null);
    }

	public RulesVO[] findByRuleStructurePKRuleNameProductStructurePK(long ruleStructurePK, String ruleName,
                                                                            long productStructurePK)
    {
        DBTable productRuleStructureDBTable = DBTable.getDBTableForTable("ProductRuleStructure");

        String productRuleStructureTable = productRuleStructureDBTable.getFullyQualifiedTableName();

        String rulesPKCol  = DBTABLE.getDBColumn("RulesPK").getFullyQualifiedColumnName();
        String ruleNameCol = DBTABLE.getDBColumn("RuleName").getFullyQualifiedColumnName();

        String rulesFKCol            = productRuleStructureDBTable.getDBColumn("RulesFK").getFullyQualifiedColumnName();
        String productStructureFKCol = productRuleStructureDBTable.getDBColumn("ProductStructureFK").getFullyQualifiedColumnName();

        String sql = " SELECT * FROM " + TABLENAME + ", " + productRuleStructureTable +
                     " WHERE " + rulesPKCol + " = " + rulesFKCol +
                     " AND " + ruleNameCol + " = '" + ruleName + "'" +
                     " AND " + productStructureFKCol + " = " + productStructurePK;

        return (RulesVO[]) executeQuery(RulesVO.class,
                                         sql,
                                          POOLNAME,
                                           false,
                                            null);
    }

	public RulesVO[] findByRuleNameProductStructurePK(String ruleName, long productStructurePK)
    {
        DBTable productRuleStructureDBTable = DBTable.getDBTableForTable("ProductRuleStructure");

        String productRuleStructureTable = productRuleStructureDBTable.getFullyQualifiedTableName();

        String rulesPKCol  = DBTABLE.getDBColumn("RulesPK").getFullyQualifiedColumnName();
        String ruleNameCol = DBTABLE.getDBColumn("RuleName").getFullyQualifiedColumnName();

        String rulesFKCol            = productRuleStructureDBTable.getDBColumn("RulesFK").getFullyQualifiedColumnName();
        String productStructureFKCol = productRuleStructureDBTable.getDBColumn("ProductStructureFK").getFullyQualifiedColumnName();

        String sql = " SELECT * FROM " + TABLENAME + ", " + productRuleStructureTable +
                     " WHERE " + rulesPKCol + " = " + rulesFKCol +
                     " AND " + ruleNameCol + " = '" + ruleName + "'" +
                     " AND " + productStructureFKCol + " = " + productStructurePK;

        return (RulesVO[]) executeQuery(RulesVO.class,
                                         sql,
                                          POOLNAME,
                                           false,
                                            null);
    }

	public RulesVO[] findByTableDefPK(long tableDefPK, boolean includeChildVOs, List voExclusionList)
    {
        String tableDefFKCol = DBTABLE.getDBColumn("TableDefFK").getFullyQualifiedColumnName();

        String sql = " SELECT * FROM " + TABLENAME +
                     " WHERE " + tableDefFKCol + " = " + tableDefPK;

        return (RulesVO[]) executeQuery(RulesVO.class,
                                         sql,
                                          POOLNAME,
                                           includeChildVOs,
                                            voExclusionList);
	}

    public RulesVO[] findByScriptPK(long scriptPK, boolean includeChildVOs, List voExclusionList)
    {
        String scriptFKCol = DBTABLE.getDBColumn("ScriptFK").getFullyQualifiedColumnName();

        String sql = " SELECT * FROM " + TABLENAME +
                     " WHERE " + scriptFKCol + " = " + scriptPK;

        return (RulesVO[]) executeQuery(RulesVO.class,
                                         sql,
                                          POOLNAME,
                                           includeChildVOs,
                                            voExclusionList);
	}
}
