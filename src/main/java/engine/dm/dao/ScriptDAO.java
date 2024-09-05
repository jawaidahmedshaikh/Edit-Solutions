/*
 * ScriptDAO.java      Version 1.1  07/26/2001 .
 *
 * (c) 2000-2004 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */

package engine.dm.dao;

import edit.common.vo.ScriptVO;
import edit.services.db.ConnectionFactory;
import edit.services.db.DAO;
import edit.services.db.DBTable;

import java.util.List;



public class ScriptDAO extends DAO
{
    private final String POOLNAME;
    private final DBTable DBTABLE;
    private final String TABLENAME;


    public ScriptDAO()
    {
        POOLNAME  = ConnectionFactory.ENGINE_POOL;
        DBTABLE   = DBTable.getDBTableForTable("Script");
        TABLENAME = DBTABLE.getFullyQualifiedTableName();
    }

	public ScriptVO[] findScriptByName(String scriptName)
    {
        String scriptNameCol = DBTABLE.getDBColumn("ScriptName").getFullyQualifiedColumnName();

        String sql = " SELECT * FROM " + TABLENAME +
                     " WHERE " + scriptNameCol + " = '" + scriptName + "'";

        return (ScriptVO[]) executeQuery(ScriptVO.class,
                                          sql,
                                           POOLNAME,
                                            true,
                                             null);
    }

	public ScriptVO[] findScriptById(long scriptId)
    {
        String scriptPKCol = DBTABLE.getDBColumn("ScriptPK").getFullyQualifiedColumnName();

        String sql = " SELECT * FROM " + TABLENAME +
                     " WHERE " + scriptPKCol + " = " + scriptId;

        return (ScriptVO[]) executeQuery(ScriptVO.class,
                                          sql,
                                           POOLNAME,
                                            false,
                                             null);
	}

	public ScriptVO[] findScriptByIdRecursively(long scriptId)
    {
		String scriptPKCol = DBTABLE.getDBColumn("ScriptPK").getFullyQualifiedColumnName();

        String sql = " SELECT * FROM " + TABLENAME +
                     " WHERE " + scriptPKCol + " = " + scriptId;

        return (ScriptVO[]) executeQuery(ScriptVO.class,
                                          sql,
                                           POOLNAME,
                                            true,
                                             null);
	}

	public ScriptVO[] findByScriptPK(long scriptPK, boolean includeChildVOs, List voExclusionList)
    {
        String scriptPKCol = DBTABLE.getDBColumn("ScriptPK").getFullyQualifiedColumnName();

        String sql = " SELECT * FROM " + TABLENAME +
                     " WHERE " + scriptPKCol + " = " + scriptPK;

        return (ScriptVO[]) executeQuery(ScriptVO.class,
                                          sql,
                                           POOLNAME,
                                            includeChildVOs,
                                             voExclusionList);
	}

	public ScriptVO[] findAll(boolean includeChildVOs, List voExclusionList)
    {
        String scriptNameCol = DBTABLE.getDBColumn("ScriptName").getFullyQualifiedColumnName();

        String sql = " SELECT * FROM " + TABLENAME + " ORDER BY " + scriptNameCol;

        return (ScriptVO[]) executeQuery(ScriptVO.class,
                                          sql,
                                           POOLNAME,
                                            includeChildVOs,
                                             voExclusionList);
	}

	public ScriptVO[] findAllScriptNames()
    {
        String scriptNameCol = DBTABLE.getDBColumn("ScriptName").getFullyQualifiedColumnName();

        String sql = "SELECT * FROM " + TABLENAME + " ORDER BY " + scriptNameCol;

        return (ScriptVO[]) executeQuery(ScriptVO.class,
                                          sql,
                                           POOLNAME,
                                            false,
                                             null);
	}

    public ScriptVO[] findScriptByNameRecursively(String scriptName)
    {
        String scriptNameCol = DBTABLE.getDBColumn("ScriptName").getFullyQualifiedColumnName();

        String sql = " SELECT * FROM " + TABLENAME +
                     " WHERE " + scriptNameCol + " = '" + scriptName + "'";

        return (ScriptVO[]) executeQuery(ScriptVO.class,
                                          sql,
                                           POOLNAME,
                                            true,
                                             null);
    }

    public ScriptVO[] findByScriptName(String scriptName)
    {
        String scriptNameCol = DBTABLE.getDBColumn( "ScriptName").getFullyQualifiedColumnName();

        String sql = " SELECT * FROM " + TABLENAME +
                     " WHERE " + scriptNameCol + " = '" + scriptName + "'";

        return (ScriptVO[]) executeQuery(ScriptVO.class,
                                          sql,
                                           POOLNAME,
                                            false,
                                             null);
    }

    public ScriptVO[] findByPartialScriptName(String scriptFilter)
    {
        String scriptNameCol = DBTABLE.getDBColumn( "ScriptName").getFullyQualifiedColumnName();

        String sql = " SELECT * FROM " + TABLENAME +
                     " WHERE UPPER(" + scriptNameCol + ") LIKE ('" + scriptFilter.toUpperCase() + "%')" +
                     "ORDER BY " + scriptNameCol;
        
        return (ScriptVO[]) executeQuery(ScriptVO.class,
                                          sql,
                                           POOLNAME,
                                            false,
                                             null);
    }
}