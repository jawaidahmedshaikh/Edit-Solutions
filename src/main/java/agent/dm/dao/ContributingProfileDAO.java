/**
 * Created by IntelliJ IDEA.
 * User: gfrosti
 * Date: Dec 31, 2004
 * Time: 1:16:34 PM
 * To change this template use File | Settings | File Templates.
 */
package agent.dm.dao;

import edit.common.vo.ContributingProfileVO;
import edit.services.db.ConnectionFactory;
import edit.services.db.DAO;
import edit.services.db.DBTable;


public class ContributingProfileDAO extends DAO
{
    private final String POOLNAME;
    private final DBTable DBTABLE;
    private final String TABLENAME;

    public ContributingProfileDAO()
    {
        POOLNAME = ConnectionFactory.EDITSOLUTIONS_POOL;
        DBTABLE = DBTable.getDBTableForTable("ContributingProfile");
        TABLENAME = DBTABLE.getFullyQualifiedTableName();
    }

    /**
     * Finder.
     * @return
     */
    public ContributingProfileVO[] findAll()
    {
        String sql = " SELECT * FROM " + TABLENAME;

        return (ContributingProfileVO[]) executeQuery(ContributingProfileVO.class, sql, POOLNAME, false, null);
    }

    /**
     * Finder.
     * @param contributingProfilePK
     * @return
     */
    public ContributingProfileVO[] findByPK(long contributingProfilePK)
    {
        String contributingProfilePKCol = DBTABLE.getDBColumn("ContributingProfilePK").getFullyQualifiedColumnName();

        String sql = " SELECT * FROM " + TABLENAME + " WHERE " + contributingProfilePKCol + " = " + contributingProfilePK;

        return (ContributingProfileVO[]) executeQuery(ContributingProfileVO.class, sql, POOLNAME, false, null);
    }

    /**
     * Finder.
     * @param commissionProfilePK
     * @param bonusProgramPK
     * @return
     */
    public ContributingProfileVO[] findBy_CommissionProfilePK_BonusProgramPK(long commissionProfilePK, long bonusProgramPK)
    {
        String commissionProfileFKCol = DBTABLE.getDBColumn("CommissionProfileFK").getFullyQualifiedColumnName();
        String bonusProgramFKCol = DBTABLE.getDBColumn("BonusProgramFK").getFullyQualifiedColumnName();

        String sql = " SELECT * FROM " + TABLENAME + " WHERE " + commissionProfileFKCol + " = " + commissionProfilePK + " AND " + bonusProgramFKCol + " = " + bonusProgramPK;

        return (ContributingProfileVO[]) executeQuery(ContributingProfileVO.class, sql, POOLNAME, false, null);
    }

    /**
     * Finder.
     * @param bonusProgramPK
     * @return
     */
    public ContributingProfileVO[] findBy_BonusProgramPK(long bonusProgramPK)
    {
        String bonusProgramFKCol = DBTABLE.getDBColumn("BonusProgramFK").getFullyQualifiedColumnName();

        String sql = " SELECT * FROM " + TABLENAME + " WHERE " + bonusProgramFKCol + " = " + bonusProgramPK;

        return (ContributingProfileVO[]) executeQuery(ContributingProfileVO.class, sql, POOLNAME, false, null);
    }
}
