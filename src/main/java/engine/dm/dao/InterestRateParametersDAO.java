/*
 * InterestRateParamDAO.java   Version 1.1   03/27/2002
 *
 * (c) 2000-2004 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */

package engine.dm.dao;

import edit.common.vo.InterestRateParametersVO;
import edit.services.db.ConnectionFactory;
import edit.services.db.DAO;
import edit.services.db.DBTable;
import edit.services.db.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class InterestRateParametersDAO extends DAO
{
    private final String POOLNAME;
    private final DBTable DBTABLE;
    private final String TABLENAME;


    public InterestRateParametersDAO()
    {
        POOLNAME  = ConnectionFactory.ENGINE_POOL;
        DBTABLE   = DBTable.getDBTableForTable("InterestRateParameters");
        TABLENAME = DBTABLE.getFullyQualifiedTableName();
    }

    public InterestRateParametersVO[] findAllInterestRateParamsAndInterestRates()
    {
        String sql =" SELECT * FROM " + TABLENAME;

        return (InterestRateParametersVO[]) executeQuery(InterestRateParametersVO.class,
                                                          sql,
                                                           POOLNAME,
                                                            true,
                                                             null);
    }

	public InterestRateParametersVO[] findAllInterestRateParameters()
    {
	    String sql =" SELECT * FROM " + TABLENAME;

        return (InterestRateParametersVO[]) executeQuery(InterestRateParametersVO.class,
                                                          sql,
                                                           POOLNAME,
                                                            false,
                                                             null);
	}

	public InterestRateParametersVO[] findInterestRatesByOriginalDateOptionFund(String originalDate, String option,
                                                                                 long filteredFundId)
    {
        Connection conn = ConnectionFactory.getSingleton().getConnection(POOLNAME);

        PreparedStatement ps = null;

        InterestRateParametersVO[] interestRateParametersVOs = null;

        String filteredFundFKCol = DBTABLE.getDBColumn("FilteredFundFK").getFullyQualifiedColumnName();
        String optionCTCol       = DBTABLE.getDBColumn("OptionCT").getFullyQualifiedColumnName();
        String originalDateCol   = DBTABLE.getDBColumn("OriginalDate").getFullyQualifiedColumnName();

        String sql = " SELECT * FROM " + TABLENAME +
                     " WHERE " + filteredFundFKCol + " = ?" +
                     " AND (" + optionCTCol + " = ?" +
                     " OR " + optionCTCol + " = '*') AND " + originalDateCol +
                     " = (SELECT MAX(" + originalDateCol + ") FROM " + TABLENAME +
                     " WHERE " + originalDateCol + " <= ?" +
                     " AND " + filteredFundFKCol + " = ?" +
                     " AND (" + optionCTCol + " = ?" +
                     " OR " + optionCTCol + " = '*'))";

        try
        {
            ps = conn.prepareStatement(sql);

            ps.setLong(1, filteredFundId);
            ps.setString(2, option);
            ps.setDate(3, DBUtil.convertStringToDate(originalDate));
            ps.setLong(4, filteredFundId);
            ps.setString(5, option);

            interestRateParametersVOs = (InterestRateParametersVO[]) executeQuery(InterestRateParametersVO.class,
                                                            ps,
                                                             POOLNAME,
                                                              true,
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

        return interestRateParametersVOs;
	}
}