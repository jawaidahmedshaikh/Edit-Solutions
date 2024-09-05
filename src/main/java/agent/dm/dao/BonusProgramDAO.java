/**
 * Created by IntelliJ IDEA.
 * User: gfrosti
 * Date: Jan 4, 2005
 * Time: 1:27:19 PM
 * To change this template use File | Settings | File Templates.
 */

package agent.dm.dao;

import edit.common.vo.BonusProgramVO;
import edit.services.db.ConnectionFactory;
import edit.services.db.DAO;
import edit.services.db.DBTable;


public class BonusProgramDAO extends DAO
{
    private final String POOLNAME;
    private final DBTable DBTABLE;
    private final String TABLENAME;

    public BonusProgramDAO()
    {
        POOLNAME = ConnectionFactory.EDITSOLUTIONS_POOL;
        DBTABLE = DBTable.getDBTableForTable("BonusProgram");
        TABLENAME = DBTABLE.getFullyQualifiedTableName();
    }

    /**
     * Finder.
     */
    public BonusProgramVO[] findAll()
    {
        String sql = " SELECT * FROM " + TABLENAME;

        return (BonusProgramVO[]) executeQuery(BonusProgramVO.class,
                sql,
                POOLNAME,
                false,
                null);
    }

    /**
     * Finder.
     *
     * @param bonusProgramPK
     */
    public BonusProgramVO[] findByPK(long bonusProgramPK)
    {
        String bonusProgramPKCol = DBTABLE.getDBColumn("BonusProgramPK").getFullyQualifiedColumnName();

        String sql = " SELECT * FROM " + TABLENAME +
                " WHERE " + bonusProgramPKCol + " = " + bonusProgramPK;

        return (BonusProgramVO[]) executeQuery(BonusProgramVO.class, sql, POOLNAME, false, null);
    }

    /**
     * Finder.
     * @param bonusName
     * @return
     */
    public BonusProgramVO[] findBy_BonusNameCT(String bonusName)
    {
        String bonusNameCol = DBTABLE.getDBColumn("BonusName").getFullyQualifiedColumnName();

        String sql = " SELECT * FROM " + TABLENAME +
                " WHERE " + bonusNameCol + " = '" + bonusName + "'";

        return (BonusProgramVO[]) executeQuery(BonusProgramVO.class, sql, POOLNAME, false, null);
    }
}