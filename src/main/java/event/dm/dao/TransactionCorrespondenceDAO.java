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

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import edit.common.vo.TransactionCorrespondenceVO;
import edit.services.db.ConnectionFactory;
import edit.services.db.DAO;
import edit.services.db.DBTable;
import fission.utility.Util;


public class TransactionCorrespondenceDAO extends DAO
{
    private final String POOLNAME;
    private final DBTable DBTABLE;
    private final String TABLENAME;


    public TransactionCorrespondenceDAO()
    {
        POOLNAME  = ConnectionFactory.EDITSOLUTIONS_POOL;
        DBTABLE   = DBTable.getDBTableForTable("TransactionCorrespondence");
        TABLENAME = DBTABLE.getFullyQualifiedTableName();
    }

    public TransactionCorrespondenceVO[] findAll()
    {
        String sql = "SELECT * FROM " + TABLENAME;

        return (TransactionCorrespondenceVO[]) executeQuery(TransactionCorrespondenceVO.class,
                                                             sql,
                                                              POOLNAME,
                                                               false,
                                                                null);
    }
    
    public String[] findAllCorrespondenceTypes() throws Exception
    {
    	Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        String CorrespondenceTypeCT = DBTABLE.getDBColumn("CorrespondenceTypeCT").getFullyQualifiedColumnName();
        
        String sql = "SELECT DISTINCT " + CorrespondenceTypeCT + " FROM " + TABLENAME;

        List<String> correspondenceTypes = new ArrayList<>();

        try
        {
            conn = ConnectionFactory.getSingleton().getConnection(POOLNAME);

            ps = conn.prepareStatement(sql);

            rs = ps.executeQuery();

            while (rs.next())
            {
            	correspondenceTypes.add(new String(rs.getString("CorrespondenceTypeCT")));
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

        if (correspondenceTypes.size() == 0)
        {
            return null;
        }
        else
        {
            return (String[]) correspondenceTypes.toArray(new String[correspondenceTypes.size()]);
        }
    }

    public TransactionCorrespondenceVO[] findByPK(long transactionCorrespondencePK)
    {
        String transactionCorrespondencePKCol = DBTABLE.getDBColumn("TransactionCorrespondencePK").getFullyQualifiedColumnName();

        String sql = "SELECT * FROM " + TABLENAME +
                     " WHERE " + transactionCorrespondencePKCol + " = " + transactionCorrespondencePK;

        return (TransactionCorrespondenceVO[]) executeQuery(TransactionCorrespondenceVO.class,
                                                             sql,
                                                              POOLNAME,
                                                               false,
                                                                null);
    }

    public TransactionCorrespondenceVO[] findByTransactionPriorityPK(long transactionPriorityPK)
    {
        String transactionPriorityFKCol = DBTABLE.getDBColumn("TransactionPriorityFK").getFullyQualifiedColumnName();

        String sql = "SELECT * FROM " + TABLENAME +
                     " WHERE " + transactionPriorityFKCol + " = " + transactionPriorityPK;

        return (TransactionCorrespondenceVO[]) executeQuery(TransactionCorrespondenceVO.class,
                                                             sql,
                                                              POOLNAME,
                                                               false,
                                                                null);
    }

    public TransactionCorrespondenceVO[] findByTransactionPriorityPK_TrxTypeQualifier(long transactionPriorityPK,
                                                                                      String trxTypeQualifier)
    {
        String transactionPriorityFKCol = DBTABLE.getDBColumn("TransactionPriorityFK").getFullyQualifiedColumnName();
        String trxTypeQualifierCTCol = DBTABLE.getDBColumn("TransactionTypeQualifierCT").getFullyQualifiedColumnName();

        String sql = "SELECT * FROM " + TABLENAME +
                     " WHERE " + transactionPriorityFKCol + " = " + transactionPriorityPK +
                     " AND " + trxTypeQualifierCTCol + " = '" + trxTypeQualifier + "'";

        return (TransactionCorrespondenceVO[]) executeQuery(TransactionCorrespondenceVO.class,
                                                            sql,
                                                            POOLNAME,
                                                            false,
                                                            null);
    }

   public TransactionCorrespondenceVO[] findByTransactionType(String transactionType)
   {
       DBTable transactionPriorityDBTable = DBTable.getDBTableForTable("TransactionPriority");

       String transactionPriorityTable = transactionPriorityDBTable.getFullyQualifiedTableName();

       String transactionTypeCTCol     = transactionPriorityDBTable.getDBColumn("TransactionTypeCT").getFullyQualifiedColumnName();
       String transactionPriorityPKCol = transactionPriorityDBTable.getDBColumn("TransactionPriorityPK").getFullyQualifiedColumnName();

       String transactionPriorityFKCol = DBTABLE.getDBColumn("TransactionPriorityFK").getFullyQualifiedColumnName();

       String sql = "SELECT " + TABLENAME + ".* FROM " + TABLENAME + ", " + transactionPriorityTable +
                    " WHERE " + transactionTypeCTCol + " = '" + transactionType + "'" +
                    " AND " + transactionPriorityPKCol + " = " + transactionPriorityFKCol;

        return (TransactionCorrespondenceVO[]) executeQuery(TransactionCorrespondenceVO.class,
                                                             sql,
                                                              POOLNAME,
                                                               false,
                                                                null);
    }
}
