/*
 * Created by IntelliJ IDEA.
 * User: dlataill
 * Date: Jun 3, 2004
 * Time: 12:34:44 PM
 *
 * (c) 2000-2004 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package event.dm.dao;

import edit.services.db.DAO;
import edit.services.db.ConnectionFactory;
import edit.services.db.DBTable;
import edit.services.db.DBUtil;
import edit.services.db.hibernate.SessionHelper;
import event.CashBatchContract;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import edit.common.EDITBigDecimal;
import edit.common.vo.CashBatchContractVO;


public class CashBatchContractDAO extends DAO
{
    private final String POOLNAME;
    private final DBTable DBTABLE;
    private final String TABLENAME;
    private final DBTable SUSPENSE_DBTABLE;
    private final String SUSPENSE_TABLENAME;

    public CashBatchContractDAO()
    {
        POOLNAME  = ConnectionFactory.EDITSOLUTIONS_POOL;
        DBTABLE   = DBTable.getDBTableForTable("CashBatchContract");
        TABLENAME = DBTABLE.getFullyQualifiedTableName();
        
        SUSPENSE_DBTABLE = DBTable.getDBTableForTable("Suspense");
        SUSPENSE_TABLENAME = SUSPENSE_DBTABLE.getFullyQualifiedTableName();
    }

    public CashBatchContractVO[] findCashBatchContractByPK(long cashBatchContractPK)
    {
        String cashBatchContractPKCol = DBTABLE.getDBColumn("CashBatchContractPK").getFullyQualifiedColumnName();


        String sql = " SELECT * FROM " + TABLENAME +
                     " WHERE " + cashBatchContractPKCol + " = " + cashBatchContractPK;

        return (CashBatchContractVO[]) executeQuery(CashBatchContractVO.class,
                                                     sql,
                                                      POOLNAME,
                                                       false,
                                                        null);
    }
    
    public String getCashBatchContractSuspenseType(long cashBatchContractPK) throws Exception
    {
    	Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String suspenseType = null;

        String cashBatchContractPKCol = DBTABLE.getDBColumn("CashBatchContractPK").getFullyQualifiedColumnName();
        String cashBatchContractFKCol = SUSPENSE_DBTABLE.getDBColumn("CashBatchContractFK").getFullyQualifiedColumnName();
        String suspenseTypeCol = SUSPENSE_DBTABLE.getDBColumn("SuspenseType").getFullyQualifiedColumnName();
        
        String sql = "select top 1 " + suspenseTypeCol + " from " + TABLENAME +
                " INNER JOIN " + SUSPENSE_TABLENAME + " ON " + cashBatchContractFKCol + " = " + cashBatchContractPKCol +
                " WHERE " + cashBatchContractPKCol + " = ? ";

        try
        {
            conn = ConnectionFactory.getSingleton().getConnection(POOLNAME);

            ps = conn.prepareStatement(sql);

            ps.setLong(1, cashBatchContractPK);

            rs = ps.executeQuery();

            while (rs.next())
            {
            	suspenseType = rs.getString("SuspenseType");
            }
        }
        catch(Exception e)
        {
            System.out.println(e);
            e.printStackTrace();
            throw e;
        }
        finally
        {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            if (conn != null) conn.close();
        }

        return suspenseType;
    }
}