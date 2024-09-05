/**
 * 
 * User: cgleason
 * Date: Jan 11, 2005
 * Time: 8:58:39 AM
 * 
 * (c) 2000 - 2005 Systems Engineering Group, LLC.  All rights Reserved
 *  Systems Engineering Group, LLC Propietary and confidential.  Any use
 *  subject to the license agreement.
 */

package contract.dm.dao;

import edit.common.vo.RequiredMinDistributionVO;

import edit.services.db.ConnectionFactory;
import edit.services.db.DAO;
import edit.services.db.DBTable;

import java.util.List;


public class RequiredMinDistributionDAO extends DAO
{
    private final String POOLNAME;
    private final DBTable DBTABLE;
    private final String TABLENAME;

    public RequiredMinDistributionDAO()
    {
        POOLNAME = ConnectionFactory.EDITSOLUTIONS_POOL;
        DBTABLE = DBTable.getDBTableForTable("RequiredMinDistribution");
        TABLENAME = DBTABLE.getFullyQualifiedTableName();
    }

    /**
     * Finder.
     */
    public RequiredMinDistributionVO[] findAll()
    {
        String sql = " SELECT * FROM " + TABLENAME;

        return (RequiredMinDistributionVO[]) executeQuery(RequiredMinDistributionVO.class,
                sql,
                POOLNAME,
                false,
                null);
    }

    /**
     * Finder.
     *
     * @param requiredMinDistributionPK
     */
    public RequiredMinDistributionVO[] findByPK(long requiredMinDistributionPK)
    {
        String requiredMinDistributionPKCol = DBTABLE.getDBColumn("RequiredMinDistributionPK").getFullyQualifiedColumnName();

        String sql = " SELECT * FROM " + TABLENAME +
                " WHERE " + requiredMinDistributionPKCol + " = " + requiredMinDistributionPK;

        return (RequiredMinDistributionVO[]) executeQuery(RequiredMinDistributionVO.class, sql, POOLNAME, false, null);
    }
}