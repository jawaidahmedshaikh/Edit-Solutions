/*
 * User: sprasad
 * Date: Jan 10, 2005
 * Time: 5:06:15 PM
 *
 * (c) 2000-2005 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package engine.dm.dao;

import edit.common.vo.FeeCorrespondenceVO;
import edit.common.vo.EDITTrxVO;

import edit.services.db.ConnectionFactory;
import edit.services.db.DAO;
import edit.services.db.DBTable;
import edit.services.db.DBUtil;

import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class FeeCorrespondenceDAO extends DAO
{
    private final String POOLNAME;
    private final DBTable DBTABLE;
    private final String TABLENAME;

    public FeeCorrespondenceDAO()
    {
        POOLNAME = ConnectionFactory.ENGINE_POOL;
        DBTABLE = DBTable.getDBTableForTable("FeeCorrespondence");
        TABLENAME = DBTABLE.getFullyQualifiedTableName();
    }

    /**
     * Finder.
     */
    public FeeCorrespondenceVO[] findAll()
    {
        String sql = " SELECT * FROM " + TABLENAME;

        return (FeeCorrespondenceVO[]) executeQuery(FeeCorrespondenceVO.class,
                                                    sql,
                                                    POOLNAME,
                                                    false,
                                                    null);
    }

    /**
     * Finder.
     *
     * @param feeCorrespondencePK
     */
    public FeeCorrespondenceVO[] findByPK(long feeCorrespondencePK)
    {
        String feeCorrespondencePKCol = DBTABLE.getDBColumn("FeeCorrespondencePK").getFullyQualifiedColumnName();

        String sql = " SELECT * FROM " + TABLENAME +
                " WHERE " + feeCorrespondencePKCol + " = " + feeCorrespondencePK;

        return (FeeCorrespondenceVO[]) executeQuery(FeeCorrespondenceVO.class, sql, POOLNAME, false, null);
    }

    /**
     * Finder method by feePK.
     * @param feePK
     * @return
     */
    public FeeCorrespondenceVO[] findByFeePK(long feePK)
    {
        String feeFKCol = DBTABLE.getDBColumn("FeeFK").getFullyQualifiedColumnName();

        String sql = " SELECT * FROM " + TABLENAME +
                " WHERE " + feeFKCol + " = " + feePK;

        return (FeeCorrespondenceVO[]) executeQuery(FeeCorrespondenceVO.class, sql, POOLNAME, false, null);
    }

    public FeeCorrespondenceVO[] findByDateLTE(String notifyCorrDate)
    {
        FeeCorrespondenceVO[] feeCorrespondenceVOs = null;

        String correspondenceDateCol = DBTABLE.getDBColumn("CorrespondenceDate").getFullyQualifiedColumnName();
        String statusCTCol = DBTABLE.getDBColumn("StatusCT").getFullyQualifiedColumnName();

        String sql = "SELECT * FROM " + TABLENAME +
                     " WHERE " + correspondenceDateCol + " <= ?" +
                     " AND " + statusCTCol + " = 'P'";

        Connection conn = null;

        PreparedStatement ps = null;

        try
        {
            conn = ConnectionFactory.getSingleton().getConnection(POOLNAME);

            ps = conn.prepareStatement(sql);

            ps.setDate(1, DBUtil.convertStringToDate(notifyCorrDate));

            feeCorrespondenceVOs = (FeeCorrespondenceVO[]) executeQuery(FeeCorrespondenceVO.class,
                                                                        ps,
                                                                        POOLNAME,
                                                                        false,
                                                                        null);
        }
        catch (Exception e)
        {
            System.out.println(e);

            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.

            throw new RuntimeException(e);
        }
        finally
        {
            try
            {
                if (ps != null) ps.close();

                ps = null;

                if (conn != null) conn.close();
                
                conn = null;
            }
            catch (SQLException e)
            {
                System.out.println(e);

                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.

                throw new RuntimeException(e);
            }
        }

        return feeCorrespondenceVOs;
    }
}