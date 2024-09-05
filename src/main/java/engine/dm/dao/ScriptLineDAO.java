/*
 * ScriptLineDAO.java      Version 1.1  07/26/2001 .
 * 
 * (c) 2000-2004 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */

package engine.dm.dao;

import edit.common.vo.ScriptLineVO;
import edit.services.db.ConnectionFactory;
import edit.services.db.DAO;
import edit.services.db.DBTable;


public class ScriptLineDAO extends DAO
{
    private final String POOLNAME;
    private final DBTable DBTABLE;
    private final String TABLENAME;


    public ScriptLineDAO()
    {
        POOLNAME  = ConnectionFactory.ENGINE_POOL;
        DBTABLE   = DBTable.getDBTableForTable("ScriptLine");
        TABLENAME = DBTABLE.getFullyQualifiedTableName();
    }

	public ScriptLineVO[] findScriptLinesById(long scriptId)
    {
        String scriptFKCol   = DBTABLE.getDBColumn("ScriptFK").getFullyQualifiedColumnName();
        String lineNumberCol = DBTABLE.getDBColumn("LineNumber").getFullyQualifiedColumnName();

        String sql = " SELECT * FROM " + TABLENAME +
                     " WHERE " + scriptFKCol + " = " + scriptId +
                     " ORDER BY " + lineNumberCol;

        return (ScriptLineVO[]) executeQuery(ScriptLineVO.class,
                                              sql,
                                               POOLNAME,
                                                false,
                                                 null);
	}
}