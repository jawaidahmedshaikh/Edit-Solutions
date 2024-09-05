/*
 * TableKeysDAO.java      Version 1.1  07/26/2001 .
 *
 * (c) 2000-2004 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */

package engine.dm.dao;

import edit.common.vo.TableKeysVO;
import edit.services.db.ConnectionFactory;
import edit.services.db.DAO;
import edit.services.db.DBTable;

import java.util.List;


public class TableKeysDAO extends DAO
{
    private final String POOLNAME;
    private final DBTable DBTABLE;
    private final String TABLENAME;


    public TableKeysDAO()
    {
        POOLNAME  = ConnectionFactory.ENGINE_POOL;
        DBTABLE   = DBTable.getDBTableForTable("TableKeys");
        TABLENAME = DBTABLE.getFullyQualifiedTableName();
    }

    public TableKeysVO[] findAll()
    {
        String sql =   " SELECT * FROM " + TABLENAME;

        return (TableKeysVO[]) executeQuery(TableKeysVO.class,
                                             sql,
                                              POOLNAME,
                                               false,
                                                null);
	}

	public TableKeysVO[] findFirstTableKeysByTableKeyId(long tableKeyId)
    {
        String tableKeysPKCol = DBTABLE.getDBColumn("TableKeysPK").getFullyQualifiedColumnName();

        String sql = " SELECT * FROM " + TABLENAME +
                     " WHERE " + tableKeysPKCol + " = " + tableKeyId;

        return (TableKeysVO[]) executeQuery(TableKeysVO.class,
                                              sql,
                                                POOLNAME,
                                                 false,
                                                  null);
	}

	public TableKeysVO[] findTableKeysById(long tableDefId)
    {
        String tableDefFKCol = DBTABLE.getDBColumn("TableDefFK").getFullyQualifiedColumnName();

        String sql = " SELECT * FROM " + TABLENAME +
                     " WHERE " + tableDefFKCol + " = " + tableDefId;

        return (TableKeysVO[]) executeQuery(TableKeysVO.class,
                                              sql,
                                                POOLNAME,
                                                 false,
                                                  null);
	}

	public TableKeysVO[] findByPK(long tableKeysPK, boolean includeChildVOs, List voExclusionList)  {

        String tableKeysPKCol = DBTABLE.getDBColumn("TableKeysPK").getFullyQualifiedColumnName();

        String sql = " SELECT * FROM " + TABLENAME +
                     " WHERE " + tableKeysPKCol + " = " + tableKeysPK;

        return (TableKeysVO[]) executeQuery(TableKeysVO.class,
                                              sql,
                                                POOLNAME,
                                                 includeChildVOs,
                                                  voExclusionList);
	}
}