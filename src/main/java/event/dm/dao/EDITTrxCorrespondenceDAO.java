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

import edit.common.vo.EDITTrxCorrespondenceVO;
import edit.common.vo.EDITTrxVO;
import edit.services.db.*;

import java.sql.*;

public class EDITTrxCorrespondenceDAO extends DAO
{
    private final String POOLNAME;
    private final DBTable DBTABLE;
    private final String TABLENAME;


    public EDITTrxCorrespondenceDAO()
    {
        POOLNAME  = ConnectionFactory.EDITSOLUTIONS_POOL;
        DBTABLE   = DBTable.getDBTableForTable("EDITTrxCorrespondence");
        TABLENAME = DBTABLE.getFullyQualifiedTableName();
    }

    public EDITTrxCorrespondenceVO[] findByEDITTrxPK(long editTrxPK)
    {
        String editTrxFKCol = DBTABLE.getDBColumn("EDITTrxFK").getFullyQualifiedColumnName();

        String sql = "SELECT * FROM " + TABLENAME +
                     " WHERE " + editTrxFKCol + " = " + editTrxPK;

        return (EDITTrxCorrespondenceVO[]) executeQuery(EDITTrxCorrespondenceVO.class,
                                                         sql,
                                                          POOLNAME,
                                                           false,
                                                            null);
    }

    public EDITTrxCorrespondenceVO[] findByEDITTrxPK(long editTrxPK, CRUD crud)  throws Exception
    {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        String editTrxFKCol = DBTABLE.getDBColumn("EDITTrxFK").getFullyQualifiedColumnName();

        String sql = "SELECT * FROM " + TABLENAME +
                     " WHERE " + editTrxFKCol + " = ?";

        EDITTrxCorrespondenceVO[] editTrxCorrespondenceVOS = null;
        try
        {
            conn = crud.getCrudConn();

            ps = conn.prepareStatement(sql);

            ps.setLong(1, editTrxPK);

            editTrxCorrespondenceVOS = (EDITTrxCorrespondenceVO[]) executeQuery(EDITTrxCorrespondenceVO.class,
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

        return editTrxCorrespondenceVOS;
    }


    public EDITTrxCorrespondenceVO[] findCorrespondenceByStatusLTDate(String currentDate) throws Exception
    {
        String correspondenceDateCol = DBTABLE.getDBColumn("CorrespondenceDate").getFullyQualifiedColumnName();
        String statusCol             = DBTABLE.getDBColumn("Status").getFullyQualifiedColumnName();

        String sql = "SELECT * FROM "+ TABLENAME +
                    " WHERE " + correspondenceDateCol + " <= ?" +
                    " AND " + statusCol + " = 'P'";

        EDITTrxCorrespondenceVO[] editTrxCorrespondenceVOs = null;
        Connection conn = null;

        PreparedStatement ps = null;

        try
        {
            conn = ConnectionFactory.getSingleton().getConnection(POOLNAME);

            ps = conn.prepareStatement(sql);

            ps.setDate(1, DBUtil.convertStringToDate(currentDate));

            editTrxCorrespondenceVOs = (EDITTrxCorrespondenceVO[]) executeQuery(EDITTrxCorrespondenceVO.class,
                                                                  ps,
                                                                  POOLNAME,
                                                                  false,
                                                                  null);
        }
        catch (Exception e)
        {
            System.out.println(e);
            e.printStackTrace();
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
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }

        return editTrxCorrespondenceVOs;
    }

    public EDITTrxCorrespondenceVO[] findByPK(long editTrxCorrespondencePK) throws Exception
    {
        String editTrxCorrespondencePKCol = DBTABLE.getDBColumn("EDITTrxCorrespondencePK").getFullyQualifiedColumnName();

        String sql = "SELECT * FROM " + TABLENAME +
                     " WHERE " + editTrxCorrespondencePKCol + " = " + editTrxCorrespondencePK;

        return (EDITTrxCorrespondenceVO[]) executeQuery(EDITTrxCorrespondenceVO.class,
                                                         sql,
                                                          POOLNAME,
                                                           false,
                                                            null);
    }

    /**
     * Retrieves all pending EDITTrxCorrespondence records whose CorrespondenceDate <= the notifyCorrDate parameter
     * value, and whose TransactionCorrespondence.CorrespondenceTypeCT matches the correspondenceType paramenter value
     * @param notifyCorrDate
     * @param correspondenceType
     * @return
     * @throws Exception
     */
    public EDITTrxCorrespondenceVO[] findPendingByTypeAndDate(String notifyCorrDate, String[] correspondenceTypes)
    {
        EDITTrxCorrespondenceVO[] editTrxCorrespondenceVOs = null;

        DBTable transactionCorrespondenceDBTable = DBTable.getDBTableForTable("TransactionCorrespondence");
        DBTable editTrxDBTable = DBTable.getDBTableForTable("EDITTrx");

        String transactionCorrespondenceTable   = transactionCorrespondenceDBTable.getFullyQualifiedTableName();
        String editTrxTable = editTrxDBTable.getFullyQualifiedTableName();

        String statusCol = DBTABLE.getDBColumn("Status").getFullyQualifiedColumnName();
        String correspondenceDateCol = DBTABLE.getDBColumn("CorrespondenceDate").getFullyQualifiedColumnName();
        String transactionCorrespondenceFKCol = DBTABLE.getDBColumn("TransactionCorrespondenceFK").getFullyQualifiedColumnName();
        String editTrxFKCol = DBTABLE.getDBColumn("EDITTrxFK").getFullyQualifiedColumnName();

        String transactionCorrespondencePKCol = transactionCorrespondenceDBTable.getDBColumn("TransactionCorrespondencePK").getFullyQualifiedColumnName();
        String correspondenceTypeCTCol = transactionCorrespondenceDBTable.getDBColumn("CorrespondenceTypeCT").getFullyQualifiedColumnName();

        String editTrxPKCol = editTrxDBTable.getDBColumn("EDITTrxPK").getFullyQualifiedColumnName();
        String trxStatusCol = editTrxDBTable.getDBColumn("Status").getFullyQualifiedColumnName();

        String correspondenceTypeSql = "";
        for (int i = 0; i < correspondenceTypes.length; i++)
        {
            if (i < correspondenceTypes.length - 1)
            {
                correspondenceTypeSql += "'" + correspondenceTypes[i] + "'";
                correspondenceTypeSql += ", ";
            }
            else
            {
                correspondenceTypeSql += "'" + correspondenceTypes[i] + "'";
            }
        }

        String sql = "SELECT " + TABLENAME + ".*" +
                     " FROM " + TABLENAME + ", " + transactionCorrespondenceTable + ", " + editTrxTable +
                     " WHERE " + correspondenceTypeCTCol + " IN(" + correspondenceTypeSql + ")" +
                     " AND " + transactionCorrespondenceFKCol + " = " + transactionCorrespondencePKCol +
                     " AND " + editTrxFKCol + " = " + editTrxPKCol +
                     " AND " + correspondenceDateCol + " <= ?" +
                     " AND " + statusCol + " = 'P'" +
                     " AND " + trxStatusCol + " IN ('N', 'A')";

        Connection conn = null;

        PreparedStatement ps = null;

        try
        {
            conn = ConnectionFactory.getSingleton().getConnection(POOLNAME);

            ps = conn.prepareStatement(sql);

            ps.setDate(1, DBUtil.convertStringToDate(notifyCorrDate));

            editTrxCorrespondenceVOs = (EDITTrxCorrespondenceVO[]) executeQuery(EDITTrxCorrespondenceVO.class,
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

        return editTrxCorrespondenceVOs;
    }

    /**
     * Get EDITTrxCorrespondence records by the requested date, correspondence type, and Status = "P"
     * @param currentDate
     * @return  array of EDITTrxCorrespondence keys
     * @throws Exception
     */
    public EDITTrxCorrespondenceVO[] findCorrespondenceByStatusTypeLEDate(String currentDate, String correspondenceType)  throws Exception
    {
        EDITTrxCorrespondenceVO[] editTrxCorrespondenceVOs = null;

        String correspondenceDateCol = DBTABLE.getDBColumn("CorrespondenceDate").getFullyQualifiedColumnName();
        String statusCol = DBTABLE.getDBColumn("Status").getFullyQualifiedColumnName();
        String correspondenceTypeCTCol = DBTABLE.getDBColumn("CorrespondenceTypeCT").getFullyQualifiedColumnName();

        String sql = "SELECT * FROM " + TABLENAME +
                     " WHERE " + correspondenceDateCol + " <= ?" +
                     " AND " + correspondenceTypeCTCol + " = '" + correspondenceType + "'" +
                     " AND " + statusCol + " = 'P'";

        Connection conn = null;

        PreparedStatement ps = null;

        try
        {
            conn = ConnectionFactory.getSingleton().getConnection(POOLNAME);

            ps = conn.prepareStatement(sql);

            ps.setDate(1, DBUtil.convertStringToDate(currentDate));

            editTrxCorrespondenceVOs = (EDITTrxCorrespondenceVO[]) executeQuery(EDITTrxCorrespondenceVO.class,
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

        return editTrxCorrespondenceVOs;
    }

    /**
     * Retrieves all EDITTrxCorrespondence records whose TransactionCorrespondenceFK = trxCorrPK parameter
     * @param notifyCorrDate
     * @param correspondenceType
     * @return
     * @throws Exception
     */
    public EDITTrxCorrespondenceVO[] findByTrxCorrFK(long trxCorrPK) 
    {
        String transactionCorrespondenceFKCol = DBTABLE.getDBColumn("TransactionCorrespondenceFK").getFullyQualifiedColumnName();

        String sql = "SELECT * FROM " + TABLENAME +
                     " WHERE " + transactionCorrespondenceFKCol + " = " + trxCorrPK;

        return (EDITTrxCorrespondenceVO[]) executeQuery(EDITTrxCorrespondenceVO.class,
                                                        sql,
                                                        POOLNAME,
                                                        false,
                                                        null);
    }
}
