/*
 * CodeTableDefDAO.java      Version 1.1  07/26/2001
 *
 *
 * (c) 2000-2004 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
*/
package codetable.dm.dao;

import edit.common.vo.CodeTableDefVO;
import edit.services.db.ConnectionFactory;
import edit.services.db.DAO;
import edit.services.db.DBTable;


public class CodeTableDefDAO extends DAO
{
    private final String POOLNAME;
    private final DBTable CODETABLEDEF_DBTABLE;
    private final String CODETABLEDEF_TABLENAME;


    public CodeTableDefDAO()
    {
        POOLNAME               = ConnectionFactory.EDITSOLUTIONS_POOL;
        CODETABLEDEF_DBTABLE   = DBTable.getDBTableForTable("CodeTableDef");
        CODETABLEDEF_TABLENAME = CODETABLEDEF_DBTABLE.getFullyQualifiedTableName();
    }

    public CodeTableDefVO[] findAll()
    {
        String sql = " SELECT * FROM " + CODETABLEDEF_TABLENAME;

        return (CodeTableDefVO[]) executeQuery(CodeTableDefVO.class,
                                                sql,
                                                  POOLNAME,
                                                  true,
                                                   null);
    }

    public CodeTableDefVO[] findAllCodeTableDefs()
    {
        String codeTableNameCol = CODETABLEDEF_DBTABLE.getDBColumn("CodeTableName").getFullyQualifiedColumnName();

        String sql = " SELECT * FROM " + CODETABLEDEF_TABLENAME +
                     " ORDER BY " + codeTableNameCol;

        return (CodeTableDefVO[]) executeQuery(CodeTableDefVO.class,
                                                sql,
                                                 POOLNAME,
                                                  false,
                                                   null);
    }

    public CodeTableDefVO[] findByCodeTableDefPK(long codeTableDefPK)
    {
        String codeTableDefPKCol = CODETABLEDEF_DBTABLE.getDBColumn("CodeTableDefPK").getFullyQualifiedColumnName();

        String sql = " SELECT * FROM " + CODETABLEDEF_TABLENAME +
                     " WHERE " + codeTableDefPKCol + " = " + codeTableDefPK;

        return (CodeTableDefVO[]) executeQuery(CodeTableDefVO.class,
                                                sql,
                                                 POOLNAME,
                                                  false,
                                                   null);
    }
}