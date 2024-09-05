/*
 * Created by IntelliJ IDEA.
 * User: gfrosti
 * Date: Jun 25, 2003
 * Time: 10:41:47 AM
 *
 * (c) 2000-2004 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package event.dm.dao;

import edit.common.vo.TransactionPriorityVO;
import edit.services.db.*;

import java.sql.*;


public class TransactionPriorityDAO extends DAO
{
    private final String POOLNAME;
    private final DBTable DBTABLE;
    private final String TABLENAME;


    public TransactionPriorityDAO()
    {
        POOLNAME  = ConnectionFactory.EDITSOLUTIONS_POOL;
        DBTABLE   = DBTable.getDBTableForTable("TransactionPriority");
        TABLENAME = DBTABLE.getFullyQualifiedTableName();
    }

    public TransactionPriorityVO[] findAll()
    {
        String priorityCol = DBTABLE.getDBColumn("Priority").getFullyQualifiedColumnName();

        String sql = "SELECT * FROM " + TABLENAME +
                     " ORDER BY " + priorityCol + " ASC";

        return (TransactionPriorityVO[]) executeQuery(TransactionPriorityVO.class,
                                                       sql,
                                                        POOLNAME,
                                                         false,
                                                          null);
    }

    public TransactionPriorityVO[] findByTrxType(String trxType)
    {
        String transactionTypeCTCol = DBTABLE.getDBColumn("TransactionTypeCT").getFullyQualifiedColumnName();

        String sql = "SELECT * FROM " + TABLENAME +
                     " WHERE " + transactionTypeCTCol + " = '" + trxType + "'";

        return (TransactionPriorityVO[]) executeQuery(TransactionPriorityVO.class,
                                                       sql,
                                                        POOLNAME,
                                                         false,
                                                          null);
    }

    public TransactionPriorityVO[] findByTrxType(String trxType, CRUD crud)  throws Exception
    {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        String transactionTypeCTCol = DBTABLE.getDBColumn("TransactionTypeCT").getFullyQualifiedColumnName();

        String sql = "SELECT * FROM " + TABLENAME +
                     " WHERE " + transactionTypeCTCol + " = ?";

        TransactionPriorityVO[] transactionPriorityVOs = null;
        
        try
        {
            conn = crud.getCrudConn();

            ps = conn.prepareStatement(sql);

            ps.setString(1, trxType);

            transactionPriorityVOs = (TransactionPriorityVO[]) executeQuery(TransactionPriorityVO.class,
                                                                             ps,
                                                                              POOLNAME,
                                                                               false,
                                                                                null);
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
            //DO NOT CLOSE THE CONNECTION PASSED IN
            if (crud == null)
            {
                if (conn != null) conn.close();
            }
        }

        return transactionPriorityVOs;
    }




    public TransactionPriorityVO[] findByPK(long transactionPriorityPK)
    {
        String transactionPriorityPKCol = DBTABLE.getDBColumn("TransactionPriorityPK").getFullyQualifiedColumnName();

        String sql = "SELECT * FROM " + TABLENAME +
                     " WHERE " + transactionPriorityPKCol + " = " + transactionPriorityPK;

        return (TransactionPriorityVO[]) executeQuery(TransactionPriorityVO.class,
                                                       sql,
                                                        POOLNAME,
                                                         false,
                                                          null);
    }
}