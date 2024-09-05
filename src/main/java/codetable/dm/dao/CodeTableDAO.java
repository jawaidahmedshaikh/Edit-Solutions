/*
 * CodeTableDefDAO.java      Version 1.1  07/26/2001
 *
 * (c) 2000-2004 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
*/
package codetable.dm.dao;

import edit.common.vo.CodeTableVO;
import edit.services.db.ConnectionFactory;
import edit.services.db.DAO;
import edit.services.db.DBTable;


public class CodeTableDAO extends DAO
{
    private final String POOLNAME;

    private final DBTable CODETABLE_DBTABLE;
    private final DBTable CODETABLEDEF_DBTABLE;

    private final String CODETABLE_TABLENAME;
    private final String CODETABLEDEF_TABLENAME;


    public CodeTableDAO()
    {
        POOLNAME               = ConnectionFactory.EDITSOLUTIONS_POOL;

        CODETABLE_DBTABLE      = DBTable.getDBTableForTable("CodeTable");
        CODETABLEDEF_DBTABLE   = DBTable.getDBTableForTable("CodeTableDef");

        CODETABLE_TABLENAME    = CODETABLE_DBTABLE.getFullyQualifiedTableName();
        CODETABLEDEF_TABLENAME = CODETABLEDEF_DBTABLE.getFullyQualifiedTableName();
    }

    public CodeTableVO[] findSelectedCodeTableEntries(long codeTableDefPK)
    {
        String codeTableDefFKCol = CODETABLE_DBTABLE.getDBColumn("CodeTableDefFK").getFullyQualifiedColumnName();
        String codeCol           = CODETABLE_DBTABLE.getDBColumn("Code").getFullyQualifiedColumnName();

        String sql = " SELECT * FROM " + CODETABLE_TABLENAME +
                     " WHERE " + codeTableDefFKCol + " = " + codeTableDefPK +
                     " ORDER BY " + codeCol;

        return (CodeTableVO[]) executeQuery(CodeTableVO.class,
                sql,
                POOLNAME,
                false,
                null);
    }

    public CodeTableVO[] findSpecificCodeTableEntry(long codeTablePK)
    {
        String codeTablePKCol = CODETABLE_DBTABLE.getDBColumn("CodeTablePK").getFullyQualifiedColumnName();

        String sql =   " SELECT * FROM " + CODETABLE_TABLENAME +
                       " WHERE " + codeTablePKCol + " = " + codeTablePK;

        return (CodeTableVO[]) executeQuery(CodeTableVO.class,
                sql,
                POOLNAME,
                false,
                null);
    }

    public CodeTableVO[] findCodeTableByTableName(String codeTableName)
    {
        String codeTableNameCol  = CODETABLEDEF_DBTABLE.getDBColumn("CodeTableName").getFullyQualifiedColumnName();
        String codeTableDefPKCol = CODETABLEDEF_DBTABLE.getDBColumn("CodeTableDefPK").getFullyQualifiedColumnName();

        String codeTableDefFKCol = CODETABLE_DBTABLE.getDBColumn("CodeTableDefFK").getFullyQualifiedColumnName();
        String codeDescCol       = CODETABLE_DBTABLE.getDBColumn("CodeDesc").getFullyQualifiedColumnName();

        String sql =   " SELECT " + CODETABLE_TABLENAME + ".* FROM " + CODETABLE_TABLENAME + ", " + CODETABLEDEF_TABLENAME +
                       " WHERE " + codeTableNameCol + " = '" + codeTableName +
                       " AND " + codeTableDefPKCol + " = " + codeTableDefFKCol +
                       " ORDER BY " + codeDescCol;

        return (CodeTableVO[]) executeQuery(CodeTableVO.class,
                sql,
                POOLNAME,
                true,
                null);
    }

    public CodeTableVO[] findByTableNameAndCode(String codeTableName, String code)
    {
        String codeTableNameCol  = CODETABLEDEF_DBTABLE.getDBColumn("CodeTableName").getFullyQualifiedColumnName();
        String codeTableDefPKCol = CODETABLEDEF_DBTABLE.getDBColumn("CodeTableDefPK").getFullyQualifiedColumnName();

        String codeTableDefFKCol = CODETABLE_DBTABLE.getDBColumn("CodeTableDefFK").getFullyQualifiedColumnName();
        String codeCol           = CODETABLE_DBTABLE.getDBColumn("Code").getFullyQualifiedColumnName();

        String sql =   " SELECT " + CODETABLE_TABLENAME + ".* FROM " + CODETABLE_TABLENAME + ", " + CODETABLEDEF_TABLENAME +
                       " WHERE " + codeTableNameCol + " = '" + codeTableName + "'" +
                       " AND " + codeCol + " = '" + code + "'" +
                       " AND " + codeTableDefPKCol + " = " + codeTableDefFKCol;

        return (CodeTableVO[]) executeQuery(CodeTableVO.class,
                sql,
                POOLNAME,
                true,
                null);
    }

    public CodeTableVO[] findByTableNameAndCodeDesc(String codeTableName, String codeDesc)
    {
        String codeTableNameCol  = CODETABLEDEF_DBTABLE.getDBColumn("CodeTableName").getFullyQualifiedColumnName();
        String codeTableDefPKCol = CODETABLEDEF_DBTABLE.getDBColumn("CodeTableDefPK").getFullyQualifiedColumnName();

        String codeTableDefFKCol = CODETABLE_DBTABLE.getDBColumn("CodeTableDefFK").getFullyQualifiedColumnName();
        String codeDescCol       = CODETABLE_DBTABLE.getDBColumn("CodeDesc").getFullyQualifiedColumnName();

        String sql =   " SELECT " + CODETABLE_TABLENAME + ".* FROM " + CODETABLE_TABLENAME + ", " + CODETABLEDEF_TABLENAME +
                       " WHERE " + codeTableNameCol + " = '" + codeTableName +
                       " AND " + codeDescCol + " = '" + codeDesc + "'" +
                       " AND " + codeTableDefPKCol + " = " + codeTableDefFKCol;

        return (CodeTableVO[]) executeQuery(CodeTableVO.class,
                sql,
                POOLNAME,
                true,
                null);
    }
}