/*
 * User: sprasad
 * Date: Jan 10, 2005
 * Time: 5:05:21 PM
 *
 * (c) 2000-2005 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package engine.dm.dao;

import edit.common.vo.FeeDescriptionVO;

import edit.services.db.ConnectionFactory;
import edit.services.db.DAO;
import edit.services.db.DBTable;

import java.util.List;


public class FeeDescriptionDAO extends DAO
{
    private final String POOLNAME;
    private final DBTable DBTABLE;
    private final String TABLENAME;

    public FeeDescriptionDAO()
    {
        POOLNAME = ConnectionFactory.ENGINE_POOL;
        DBTABLE = DBTable.getDBTableForTable("FeeDescription");
        TABLENAME = DBTABLE.getFullyQualifiedTableName();
    }

    /**
     * Finder.
     */
    public FeeDescriptionVO[] findAll()
    {
        String sql = " SELECT * FROM " + TABLENAME;

        return (FeeDescriptionVO[]) executeQuery(FeeDescriptionVO.class,
                                                 sql,
                                                 POOLNAME,
                                                 false,
                                                 null);
    }

    /**
     * Finder method by PK.
     *
     * @param feeDescriptionPK
     */
    public FeeDescriptionVO[] findByPK(long feeDescriptionPK)
    {
        String feeDescriptionPKCol = DBTABLE.getDBColumn("FeeDescriptionPK").getFullyQualifiedColumnName();

        String sql = " SELECT * FROM " + TABLENAME +
                     " WHERE " + feeDescriptionPKCol + " = " + feeDescriptionPK;

        return (FeeDescriptionVO[]) executeQuery(FeeDescriptionVO.class, sql, POOLNAME, false, null);
    }

    /**
     * Finder method by filteredFundPK.
     * @param filteredFundPK
     * @return
     */
    public FeeDescriptionVO[] findByFilteredFundPK(long filteredFundPK)
    {
        String filteredFundFKCol = DBTABLE.getDBColumn("FilteredFundFK").getFullyQualifiedColumnName();

        String sql = " SELECT  * FROM " + TABLENAME +
                     " WHERE " + filteredFundFKCol + " = " + filteredFundPK;

        return (FeeDescriptionVO[]) executeQuery(FeeDescriptionVO.class, sql, POOLNAME, false, null);
    }

    /**
     * Finder method by filteredFundPK and feeTypeCT.
     * @param filteredFundPK
     * @param feeTypeCT
     * @return
     */
    public FeeDescriptionVO[] findByFilteredFundPK_And_FeeTypeCT(long filteredFundPK, String feeTypeCT)
    {
        String filteredFundFKCol = DBTABLE.getDBColumn("FilteredFundFK").getFullyQualifiedColumnName();
        String feeTypeCTCol = DBTABLE.getDBColumn("FeeTypeCT").getFullyQualifiedColumnName();

        String sql = " SELECT  * FROM " + TABLENAME +
                     " WHERE " + filteredFundFKCol + " = " + filteredFundPK +
                     " AND " + feeTypeCTCol + " = '" + feeTypeCT + "'";

        return (FeeDescriptionVO[]) executeQuery(FeeDescriptionVO.class, sql, POOLNAME, false, null);
    }
}