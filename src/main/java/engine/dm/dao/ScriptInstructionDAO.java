/*
 * ScriptInstructionDAO.java      Version 1.1  07/26/2001 .
 * 
 * (c) 2000-2004 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */

package engine.dm.dao;


import edit.common.vo.ScriptInstructionVO;
import edit.services.db.ConnectionFactory;
import edit.services.db.DAO;
import edit.services.db.DBTable;


public class ScriptInstructionDAO extends DAO
{
    private final String POOLNAME;
    private final DBTable DBTABLE;
    private final String TABLENAME;


    public ScriptInstructionDAO()
    {
        POOLNAME  = ConnectionFactory.ENGINE_POOL;
        DBTABLE   = DBTable.getDBTableForTable("ScriptInstruction");
        TABLENAME = DBTABLE.getFullyQualifiedTableName();
    }

	public ScriptInstructionVO[] findAllScriptInstructions()
    {
        String instructionCol = DBTABLE.getDBColumn("Instruction").getFullyQualifiedColumnName();

		String sql = " SELECT * FROM " + TABLENAME +
                     " ORDER BY " + instructionCol;

        return (ScriptInstructionVO[]) executeQuery(ScriptInstructionVO.class,
                                                     sql,
                                                      POOLNAME,
                                                       false,
                                                        null);
	}
}
