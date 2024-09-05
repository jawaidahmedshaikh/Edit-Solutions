/*
 * CodeTableDefDAO.java      Version 1.1  07/26/2001
 *
 * (c) 2000-2004 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
*/
package codetable.dm.dao;

import edit.common.vo.FilteredCodeTableVO;
import edit.services.db.ConnectionFactory;
import edit.services.db.DAO;
import edit.services.db.DBTable;


public class FilteredCodeTableDAO extends DAO
{
    private final String POOLNAME;
    private final DBTable DBTABLE;
    private final String TABLENAME;


    public FilteredCodeTableDAO()
    {
        POOLNAME  = ConnectionFactory.EDITSOLUTIONS_POOL;
        DBTABLE   = DBTable.getDBTableForTable("FilteredCodeTable");
        TABLENAME = DBTABLE.getFullyQualifiedTableName();
    }

    public FilteredCodeTableVO[] findPKByCodeTablePKAndProductStructure(long codeTablePK, long productStructureFK)
    {
        String codeTableFKCol        = DBTABLE.getDBColumn("CodeTableFK").getFullyQualifiedColumnName();
        String productStructureFKCol = DBTABLE.getDBColumn("ProductStructureFK").getFullyQualifiedColumnName();

        String sql = " SELECT * FROM " + TABLENAME +
                     " WHERE " + codeTableFKCol + " = " + codeTablePK +
                     " AND " + productStructureFKCol + " = " + productStructureFK;

        return (FilteredCodeTableVO[]) executeQuery(FilteredCodeTableVO.class,
                sql,
                POOLNAME,
                false,
                null);
    }

    public FilteredCodeTableVO[] findByPK(long filteredCodeTablePK)
    {
        String filteredCodeTablePKCol = DBTABLE.getDBColumn("FilteredCodeTablePK").getFullyQualifiedColumnName();

        String sql = " SELECT * FROM " + TABLENAME +
                     " WHERE " + filteredCodeTablePKCol + " = " + filteredCodeTablePK;

        return (FilteredCodeTableVO[]) executeQuery(FilteredCodeTableVO.class,
                sql,
                POOLNAME,
                false,
                null);
    }

    public FilteredCodeTableVO[] findByProductStructure(long cloneFromProductStructurePK)
    {
        String productStructureFKCol = DBTABLE.getDBColumn("ProductStructureFK").getFullyQualifiedColumnName();

        String sql = " SELECT * FROM " + TABLENAME +
                     " WHERE " + productStructureFKCol + " = " + cloneFromProductStructurePK;

        return (FilteredCodeTableVO[]) executeQuery(FilteredCodeTableVO.class,
                sql,
                POOLNAME,
                false,
                null);
    }

    public FilteredCodeTableVO[] findByCodeTablePK(long codeTablePK)
    {
        String codeTableFKCol = DBTABLE.getDBColumn("CodeTableFK").getFullyQualifiedColumnName();

        String sql = " SELECT * FROM " + TABLENAME +
                     " WHERE " + codeTableFKCol + " = " + codeTablePK;

        return (FilteredCodeTableVO[]) executeQuery(FilteredCodeTableVO.class,
                sql,
                POOLNAME,
                false,
                null);
    }

    public FilteredCodeTableVO[] getAllFilteredCodeTableVOs()
    {
        String sql =   " SELECT * FROM " + TABLENAME;

        return (FilteredCodeTableVO[]) executeQuery(FilteredCodeTableVO.class,
                sql,
                POOLNAME,
                false,
                null);
    }

  /**
   * Finder.
   * @param codeTableDefPK
   * @param productStructurePK
   * @return
   */
  public FilteredCodeTableVO[] findBy_CodeTableDefPK_ProductStructurePK(long codeTableDefPK, long productStructurePK)
  {
        //  CodeTable
        DBTable codeTableTable = DBTable.getDBTableForTable("CodeTable");
        String codeTableName = codeTableTable.getFullyQualifiedTableName();
        String codeTablePKCol = codeTableTable.getDBColumn("CodeTablePK").getFullyQualifiedColumnName();
        String codeTableDefFKCol = codeTableTable.getDBColumn("CodeTableDefFK").getFullyQualifiedColumnName();
        
        // FilteredCodeTable
        String codeTableFKCol        = DBTABLE.getDBColumn("CodeTableFK").getFullyQualifiedColumnName();
        String productStructureFKCol = DBTABLE.getDBColumn("ProductStructureFK").getFullyQualifiedColumnName();

        String sql = " SELECT * FROM " + codeTableName +
                     " INNER JOIN " + TABLENAME +
                     " ON " + codeTablePKCol + " = " + codeTableFKCol + 
                     " WHERE " + codeTableDefFKCol + " = " + codeTableDefPK +
                     " AND " + productStructureFKCol + " = " + productStructurePK;

        return (FilteredCodeTableVO[]) executeQuery(FilteredCodeTableVO.class,
                sql,
                POOLNAME,
                false,
                null);  
  }
}
