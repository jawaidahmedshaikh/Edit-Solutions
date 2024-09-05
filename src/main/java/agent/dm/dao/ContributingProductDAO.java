/**
 * Created by IntelliJ IDEA.
 * User: gfrosti
 * Date: Dec 29, 2004
 * Time: 11:50:09 AM
 * To change this template use File | Settings | File Templates.
 */
package agent.dm.dao;

import edit.common.vo.ContributingProductVO;
import edit.services.db.ConnectionFactory;
import edit.services.db.DAO;
import edit.services.db.DBTable;


public class ContributingProductDAO extends DAO
{
    private final String POOLNAME;
    private final DBTable DBTABLE;
    private final String TABLENAME;

    public ContributingProductDAO()
    {
        POOLNAME = ConnectionFactory.EDITSOLUTIONS_POOL;
        DBTABLE = DBTable.getDBTableForTable("ContributingProduct");
        TABLENAME = DBTABLE.getFullyQualifiedTableName();
    }

    public ContributingProductVO[] findAll()
    {
        String sql = " SELECT * FROM " + TABLENAME;

        return (ContributingProductVO[]) executeQuery(ContributingProductVO.class, sql, POOLNAME, false, null);
    }

    /**
     * Finder.
     * @param contributingProductPK
     * @return
     */
    public ContributingProductVO[] findByPK(long contributingProductPK)
    {
        String contributingProductPKCol = DBTABLE.getDBColumn("ContributingProductPK").getFullyQualifiedColumnName();

        String sql = " SELECT * FROM " + TABLENAME + " WHERE " + contributingProductPKCol + " = " + contributingProductPK;

        return (ContributingProductVO[]) executeQuery(ContributingProductVO.class, sql, POOLNAME, false, null);
    }

    /**
     * Finder.
     * @param productStructurePK
     * @param bonusProgramPK
     * @return
     */
    public ContributingProductVO[] findBy_ProductStructurePK_BonusProgramPK(long productStructurePK, long bonusProgramPK)
    {
        String productStructureFKCol = DBTABLE.getDBColumn("ProductStructureFK").getFullyQualifiedColumnName();
        String bonusProgramFKCol = DBTABLE.getDBColumn("BonusProgramFK").getFullyQualifiedColumnName();

        String sql = " SELECT * FROM " + TABLENAME + " WHERE " + productStructureFKCol + " = " + productStructurePK + " AND " + bonusProgramFKCol + " = " + bonusProgramPK;

        return (ContributingProductVO[]) executeQuery(ContributingProductVO.class, sql, POOLNAME, false, null);
    }

    /**
     * Finder.
     * @param bonusProgramPK
     * @return
     */
    public ContributingProductVO[] findBy_BonusProgramPK(long bonusProgramPK)
    {
        String bonusProgramFKCol = DBTABLE.getDBColumn("BonusProgramFK").getFullyQualifiedColumnName();

        String sql;
        sql = " SELECT * " + " FROM " + TABLENAME + " WHERE " + bonusProgramFKCol + " = " + bonusProgramPK;

        return (ContributingProductVO[]) executeQuery(ContributingProductVO.class, sql, POOLNAME, false, null);
    }
}
