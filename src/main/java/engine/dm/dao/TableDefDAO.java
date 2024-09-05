/*
 * TableDefDAO.java      Version 1.1  07/26/2001 .
 * 
 * (c) 2000-2004 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */

package engine.dm.dao;

import edit.common.vo.RulesVO;
import edit.common.vo.TableDefVO;
import edit.services.db.ConnectionFactory;
import edit.services.db.DAO;
import edit.services.db.DBTable;

import java.util.ArrayList;
import java.util.List;



public class TableDefDAO extends DAO
{
    private final String POOLNAME;
    private final DBTable DBTABLE;
    private final String TABLENAME;


    public TableDefDAO()
    {
        POOLNAME  = ConnectionFactory.ENGINE_POOL;
        DBTABLE   = DBTable.getDBTableForTable("TableDef");
        TABLENAME = DBTABLE.getFullyQualifiedTableName();
    }

	public TableDefVO[] findTableIdByName(String tableName)
    {
        String tableNameCol = DBTABLE.getDBColumn("TableName").getFullyQualifiedColumnName();

        String sql = " SELECT * FROM " + TABLENAME +
                     " WHERE " + tableNameCol + " = '" + tableName + "'";

        return (TableDefVO[]) executeQuery(TableDefVO.class,
                                            sql,
                                             POOLNAME,
                                              false,
                                               null);
	}
    
	public TableDefVO[] findTableNameById(long tableId)
    {
        String tableDefPKCol = DBTABLE.getDBColumn("TableDefPK").getFullyQualifiedColumnName();

        String sql = " SELECT * FROM " + TABLENAME +
                     " WHERE " + tableDefPKCol + " = " + tableId;

        return (TableDefVO[]) executeQuery(TableDefVO.class,
                                            sql,
                                             POOLNAME,
                                              false,
                                               null);
	}

	public TableDefVO[] findByTableDefPK(long tableDefPK, boolean includeChildVOs, List voExclusionList)
    {
        String tableDefPKCol = DBTABLE.getDBColumn("TableDefPK").getFullyQualifiedColumnName();

        String sql = " SELECT * FROM " + TABLENAME +
                     " WHERE " + tableDefPKCol + " = " + tableDefPK;

        return (TableDefVO[]) executeQuery(TableDefVO.class,
                                            sql,
                                             POOLNAME,
                                              includeChildVOs,
                                               voExclusionList);
	}

	public TableDefVO[] findAll(boolean includeChildVOs, List voExclusionList)
    {
        String sql = " SELECT * FROM " + TABLENAME;

        return (TableDefVO[]) executeQuery(TableDefVO.class,
                                            sql,
                                             POOLNAME,
                                              includeChildVOs,
                                               voExclusionList);
	}

	public TableDefVO[] findAllTableNames()
    {
        String tableNameCol = DBTABLE.getDBColumn("TableName").getFullyQualifiedColumnName();

        String sql = " SELECT * FROM " + TABLENAME +
                     " ORDER BY " + tableNameCol;

        return (TableDefVO[]) executeQuery(TableDefVO.class,
                                            sql,
                                             POOLNAME,
                                              false,
                                               null);
	}

	public TableDefVO[] findTableNameByIdRecursively(long tableId)
    {
        String tableDefPKCol = DBTABLE.getDBColumn("TableDefPK").getFullyQualifiedColumnName();

        String sql = " SELECT * FROM " + TABLENAME +
                     " WHERE " + tableDefPKCol + " = " + tableId;

        List exclusionList = new ArrayList();
        exclusionList.add(RulesVO.class);

        return (TableDefVO[]) executeQuery(TableDefVO.class,
                                            sql,
                                             POOLNAME,
                                              true,
                                               exclusionList);
	}

    public TableDefVO[] findTableIdByName(String tableName, boolean includeChildren, List voExclusionList)
    {
        String tableNameCol = DBTABLE.getDBColumn("TableName").getFullyQualifiedColumnName();

        String sql = " SELECT * FROM " + TABLENAME +
                     " WHERE " + tableNameCol + " = '" + tableName + "'";

        return (TableDefVO[]) executeQuery(TableDefVO.class,
                                            sql,
                                             POOLNAME,
                                              includeChildren,
                                               voExclusionList);
    }
}