/*
 * InterestRateDAO.java      Version 1.1  03/25/2002 .
 *
 * (c) 2000-2004 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */

package engine.dm.dao;


import edit.common.vo.InterestRateVO;
import edit.services.db.ConnectionFactory;
import edit.services.db.DAO;
import edit.services.db.DBTable;
import edit.services.db.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InterestRateDAO extends DAO
{
    private final String POOLNAME;
    private final DBTable DBTABLE;
    private final String TABLENAME;


    public InterestRateDAO()
    {
        POOLNAME  = ConnectionFactory.ENGINE_POOL;
        DBTABLE   = DBTable.getDBTableForTable("InterestRate");
        TABLENAME = DBTABLE.getFullyQualifiedTableName();
    }

    public InterestRateVO[] findInterestRateById(long interestRateParametersId)
    {
        String interestRateParametersFKCol = DBTABLE.getDBColumn("InterestRateParametersFK").getFullyQualifiedColumnName();

        String sql = "SELECT * FROM " + TABLENAME +
                     " WHERE " + interestRateParametersFKCol + " = " + interestRateParametersId;

        return (InterestRateVO[]) executeQuery(InterestRateVO.class,
                                                sql,
                                                 POOLNAME,
                                                  false,
                                                   null);
    }

    public InterestRateVO[] findInterestRateByIdAndEffectiveDate(long interestRateParametersId, String effectiveDate) throws Exception
    {
        InterestRateVO[] interestRateVOs = null;

        String interestRateParametersFKCol = DBTABLE.getDBColumn("InterestRateParametersFK").getFullyQualifiedColumnName();
        String effectiveDateCol = DBTABLE.getDBColumn("EffectiveDate").getFullyQualifiedColumnName();

        String sql = "SELECT * FROM " + TABLENAME +
                        " WHERE " + interestRateParametersFKCol + " = ?" +
                        " AND " + effectiveDateCol + " = (SELECT MAX(" + effectiveDateCol + ")" +
                        " FROM " + TABLENAME + " " +
                        " WHERE " + interestRateParametersFKCol + " = ?" +
                        " AND " + effectiveDateCol + " <= ?)";

        Connection conn = null;

        PreparedStatement ps = null;

        try
        {
            conn = ConnectionFactory.getSingleton().getConnection(POOLNAME);

            ps = conn.prepareStatement(sql);

            ps.setLong(1, interestRateParametersId);

            ps.setLong(2, interestRateParametersId);

            ps.setDate(3, DBUtil.convertStringToDate(effectiveDate));


            interestRateVOs = (InterestRateVO[]) executeQuery(InterestRateVO.class, // Q7
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

        return interestRateVOs;
    }
}