/*
 * User: gfrosti
 * Date: Jun 25, 2003
 * Time: 10:41:47 AM
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package event.dm.dao;

import edit.common.vo.EDITTrxVO;
import edit.common.*;
import edit.services.db.*;

import java.sql.*;
import java.util.*;

import event.EDITTrx;
import event.financial.client.trx.ClientTrx;
import engine.ProductStructure;

public class EDITTrxDAO extends DAO
{
    private final String POOLNAME;

    private final DBTable EDITTRX_DBTABLE;
    private final DBTable CLIENT_SETUP_DBTABLE;
    private final DBTable CONTRACT_SETUP_DBTABLE;
    private final DBTable TRANSACTION_PRIORITY_DBTABLE;
    private final DBTable SEGMENT_DBTABLE;
    private final DBTable SEGMENTHISTORY_DBTABLE;
    private final DBTable EDITTRXHISTORY_DBTABLE;
    private final DBTable COMMISSIONABLEPREMIUMHISTORY_DBTABLE;

    private final String EDITTRX_TABLENAME;
    private final String CLIENT_SETUP_TABLENAME;
    private final String CONTRACT_SETUP_TABLENAME;
    private final String TRANSACTION_PRIORITY_TABLENAME;
    private final String SEGMENT_TABLENAME;
    private final String SEGMENTHISTORY_TABLENAME;
    private final String EDITTRXHISTORY_TABLENAME;
    private final String COMMISSIONABLEPREMIUMHISTORY_TABLENAME;

    public EDITTrxDAO()
    {
        POOLNAME  = ConnectionFactory.EDITSOLUTIONS_POOL;

        EDITTRX_DBTABLE        = DBTable.getDBTableForTable("EDITTrx");
        CLIENT_SETUP_DBTABLE   = DBTable.getDBTableForTable("ClientSetup");
        CONTRACT_SETUP_DBTABLE = DBTable.getDBTableForTable("ContractSetup");
        SEGMENT_DBTABLE = DBTable.getDBTableForTable("Segment");
        SEGMENTHISTORY_DBTABLE = DBTable.getDBTableForTable("SegmentHistory");
        TRANSACTION_PRIORITY_DBTABLE = DBTable.getDBTableForTable("TransactionPriority");
        EDITTRXHISTORY_DBTABLE        = DBTable.getDBTableForTable("EDITTrxHistory");
        COMMISSIONABLEPREMIUMHISTORY_DBTABLE        = DBTable.getDBTableForTable("CommissionablePremiumHistory");

        EDITTRX_TABLENAME        = EDITTRX_DBTABLE.getFullyQualifiedTableName();
        CLIENT_SETUP_TABLENAME   = CLIENT_SETUP_DBTABLE.getFullyQualifiedTableName();
        CONTRACT_SETUP_TABLENAME = CONTRACT_SETUP_DBTABLE.getFullyQualifiedTableName();
        SEGMENT_TABLENAME = SEGMENT_DBTABLE.getFullyQualifiedTableName();
        SEGMENTHISTORY_TABLENAME = SEGMENTHISTORY_DBTABLE.getFullyQualifiedTableName();
        TRANSACTION_PRIORITY_TABLENAME = TRANSACTION_PRIORITY_DBTABLE.getFullyQualifiedTableName();
        EDITTRXHISTORY_TABLENAME        = EDITTRXHISTORY_DBTABLE.getFullyQualifiedTableName();
        COMMISSIONABLEPREMIUMHISTORY_TABLENAME        = COMMISSIONABLEPREMIUMHISTORY_DBTABLE.getFullyQualifiedTableName();
    }

    public EDITTrxVO[] findFirstSortedBy_EffectiveDate_AND_PendingStatus(String[] pendingStatus) throws Exception
    {
        String effectiveDateCol     = EDITTRX_DBTABLE.getDBColumn("EffectiveDate").getFullyQualifiedColumnName();
        String pendingStatusCol     = EDITTRX_DBTABLE.getDBColumn("PendingStatus").getFullyQualifiedColumnName();
        String transactionTypeCTCol = EDITTRX_DBTABLE.getDBColumn("TransactionTypeCT").getFullyQualifiedColumnName();

        String sqlIn = "";

        for (int i = 0; i < pendingStatus.length; i++)
        {
            if (i < pendingStatus.length - 1)
            {
                sqlIn = sqlIn + pendingStatus[i] + "', '";
            }
            else
            {
                sqlIn = sqlIn + pendingStatus[i] + "')";
            }
        }

        String sql = "SELECT * FROM "+ EDITTRX_TABLENAME +
                     " WHERE " + effectiveDateCol + " = (SELECT MIN(" + effectiveDateCol + ") FROM " + EDITTRX_TABLENAME +
                     " WHERE " + pendingStatusCol + " IN ('" + sqlIn +
                     " AND (" + transactionTypeCTCol + " NOT IN('CK', 'RCK')))" +
                     " AND " + pendingStatusCol + " IN ('" + sqlIn +
                     " AND (" + transactionTypeCTCol + " NOT IN('CK', 'RCK'))";

        return (EDITTrxVO[])executeQuery(EDITTrxVO.class,
                                          sql,
                                           POOLNAME,
                                            false,
                                              null);
    }

    public EDITTrxVO[] findAllBySegmentPK_AND_EffectiveDate_GTE_AND_LTE(long segmentPK, String effectiveDate, 
                                                                        String cycleDate, int executionMode,
                                                                        String productType)
    {
        EDITTrxVO[] editTrxVOs = null;

        String clientSetupFKCol   = EDITTRX_DBTABLE.getDBColumn("ClientSetupFK").getFullyQualifiedColumnName();
        String effectiveDateCol   = EDITTRX_DBTABLE.getDBColumn("EffectiveDate").getFullyQualifiedColumnName();
        String pendingStatusCol   = EDITTRX_DBTABLE.getDBColumn("PendingStatus").getFullyQualifiedColumnName();
        String statusCol          = EDITTRX_DBTABLE.getDBColumn("Status").getFullyQualifiedColumnName();

        String clientSetupPKCol   = CLIENT_SETUP_DBTABLE.getDBColumn("ClientSetupPK").getFullyQualifiedColumnName();
        String contractSetupFKCol = CLIENT_SETUP_DBTABLE.getDBColumn("ContractSetupFK").getFullyQualifiedColumnName();

        String contractSetupPKCol = CONTRACT_SETUP_DBTABLE.getDBColumn("ContractSetupPK").getFullyQualifiedColumnName();
        String segmentFKCol       = CONTRACT_SETUP_DBTABLE.getDBColumn("SegmentFK").getFullyQualifiedColumnName();

        // Pending transactions < currentDate are selected for realtime execution
        // and pending transaction <= are selected for batch execution.

        String pendingTrxOperator = "<";
        if ((executionMode == ClientTrx.REALTIME_MODE && productType.equalsIgnoreCase(ProductStructure.FIXED_PRODUCT)) ||
            (executionMode == ClientTrx.BATCH_MODE))
        {
            pendingTrxOperator = "<=";
        }

        String sql = "SELECT " + EDITTRX_TABLENAME + ".* FROM " + EDITTRX_TABLENAME + ", " + CLIENT_SETUP_TABLENAME + ", " + CONTRACT_SETUP_TABLENAME +
                     " WHERE " + clientSetupFKCol + " = " + clientSetupPKCol +
                     " AND " + contractSetupFKCol + " = " + contractSetupPKCol +
                     " AND " + segmentFKCol + " = ?" +
                     " AND (" + effectiveDateCol + ">= ?" +
                     " AND (" + effectiveDateCol + "<= ?" +
                     " AND " + pendingStatusCol + " IN ('H', 'L', 'S', 'C', 'F', 'B', 'M', 'O') AND NOT " + statusCol + "='U')" +
                     " OR (" + effectiveDateCol + pendingTrxOperator + "?" +
                     " AND " + pendingStatusCol + "='P'))";

        Connection conn = null;

        PreparedStatement ps = null;

        try
        {
            conn = ConnectionFactory.getSingleton().getConnection(POOLNAME);

            ps = conn.prepareStatement(sql);

            ps.setLong(1, segmentPK);
            ps.setDate(2, DBUtil.convertStringToDate(effectiveDate));
            ps.setDate(3, DBUtil.convertStringToDate(cycleDate));
            ps.setDate(4, DBUtil.convertStringToDate(cycleDate));

            editTrxVOs = (EDITTrxVO[]) executeQuery(EDITTrxVO.class,
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

        return editTrxVOs;
    }
    

    public EDITTrxVO[] findAllBySegmentPK_AND_EffectiveDate_GTE_AND_LTE_NA_STATUS(long segmentPK, String effectiveDate, 
                                                                        String cycleDate, int executionMode,
                                                                        String productType)
    {
        EDITTrxVO[] editTrxVOs = null;

        String clientSetupFKCol   = EDITTRX_DBTABLE.getDBColumn("ClientSetupFK").getFullyQualifiedColumnName();
        String effectiveDateCol   = EDITTRX_DBTABLE.getDBColumn("EffectiveDate").getFullyQualifiedColumnName();
        String pendingStatusCol   = EDITTRX_DBTABLE.getDBColumn("PendingStatus").getFullyQualifiedColumnName();
        String statusCol          = EDITTRX_DBTABLE.getDBColumn("Status").getFullyQualifiedColumnName();

        String clientSetupPKCol   = CLIENT_SETUP_DBTABLE.getDBColumn("ClientSetupPK").getFullyQualifiedColumnName();
        String contractSetupFKCol = CLIENT_SETUP_DBTABLE.getDBColumn("ContractSetupFK").getFullyQualifiedColumnName();

        String contractSetupPKCol = CONTRACT_SETUP_DBTABLE.getDBColumn("ContractSetupPK").getFullyQualifiedColumnName();
        String segmentFKCol       = CONTRACT_SETUP_DBTABLE.getDBColumn("SegmentFK").getFullyQualifiedColumnName();

        // Pending transactions < currentDate are selected for realtime execution
        // and pending transaction <= are selected for batch execution.

        String pendingTrxOperator = "<";
        if ((executionMode == ClientTrx.REALTIME_MODE && productType.equalsIgnoreCase(ProductStructure.FIXED_PRODUCT)) ||
            (executionMode == ClientTrx.BATCH_MODE))
        {
            pendingTrxOperator = "<=";
        }

        String sql = "SELECT " + EDITTRX_TABLENAME + ".* FROM " + EDITTRX_TABLENAME + ", " + CLIENT_SETUP_TABLENAME + ", " + CONTRACT_SETUP_TABLENAME +
                     " WHERE " + clientSetupFKCol + " = " + clientSetupPKCol +
                     " AND " + contractSetupFKCol + " = " + contractSetupPKCol +
                     " AND " + segmentFKCol + " = ?" +
                     " AND " + statusCol + "IN ('N', 'A')" +
                     " AND (" + effectiveDateCol + ">= ?" +
                     " AND (" + effectiveDateCol + "<= ?" +
                     " AND " + pendingStatusCol + " IN ('H', 'L', 'S', 'C', 'F', 'B', 'M', 'O'))" +
                     " OR (" + effectiveDateCol + pendingTrxOperator + "?" +
                     " AND " + pendingStatusCol + "='P'))";

        Connection conn = null;

        PreparedStatement ps = null;

        try
        {
            conn = ConnectionFactory.getSingleton().getConnection(POOLNAME);

            ps = conn.prepareStatement(sql);

            ps.setLong(1, segmentPK);
            ps.setDate(2, DBUtil.convertStringToDate(effectiveDate));
            ps.setDate(3, DBUtil.convertStringToDate(cycleDate));
            ps.setDate(4, DBUtil.convertStringToDate(cycleDate));

            editTrxVOs = (EDITTrxVO[]) executeQuery(EDITTrxVO.class,
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

        return editTrxVOs;
    }

    public EDITTrxVO[] findAllAssociatedUndoTrx(long segmentPK, long editTrxPK, String effectiveDate, String cycleDate, int priority)
    {
    	EDITTrxVO[] editTrxVOs = null;

    	String editTrxPKCol   	  = EDITTRX_DBTABLE.getDBColumn("EDITTrxPK").getFullyQualifiedColumnName();
    	String clientSetupFKCol   = EDITTRX_DBTABLE.getDBColumn("ClientSetupFK").getFullyQualifiedColumnName();
    	String effectiveDateCol   = EDITTRX_DBTABLE.getDBColumn("EffectiveDate").getFullyQualifiedColumnName();
    	String pendingStatusCol   = EDITTRX_DBTABLE.getDBColumn("PendingStatus").getFullyQualifiedColumnName();
    	String statusCol          = EDITTRX_DBTABLE.getDBColumn("Status").getFullyQualifiedColumnName();
    	String edTransactionType  = EDITTRX_DBTABLE.getDBColumn("TransactionTypeCT").getFullyQualifiedColumnName();

    	String clientSetupPKCol   = CLIENT_SETUP_DBTABLE.getDBColumn("ClientSetupPK").getFullyQualifiedColumnName();
    	String contractSetupFKCol = CLIENT_SETUP_DBTABLE.getDBColumn("ContractSetupFK").getFullyQualifiedColumnName();

    	String contractSetupPKCol = CONTRACT_SETUP_DBTABLE.getDBColumn("ContractSetupPK").getFullyQualifiedColumnName();
    	String segmentFKCol       = CONTRACT_SETUP_DBTABLE.getDBColumn("SegmentFK").getFullyQualifiedColumnName();
    	
    	String tpTransactionType  = TRANSACTION_PRIORITY_DBTABLE.getDBColumn("TransactionTypeCT").getFullyQualifiedColumnName();
    	String priorityCol 		  = TRANSACTION_PRIORITY_DBTABLE.getDBColumn("Priority").getFullyQualifiedColumnName();

    	
    	String sql = "SELECT " + EDITTRX_TABLENAME + ".* FROM " + EDITTRX_TABLENAME + ", " + CLIENT_SETUP_TABLENAME + ", " + 
    			CONTRACT_SETUP_TABLENAME + ", " + TRANSACTION_PRIORITY_TABLENAME +
    			" WHERE " + clientSetupFKCol + " = " + clientSetupPKCol +
    			" AND " + contractSetupFKCol + " = " + contractSetupPKCol +
    			" AND " + tpTransactionType +" = " + edTransactionType +
    			" AND " + segmentFKCol + " = ?" +
    			" AND " + editTrxPKCol + " != ?" +
    			" AND " + effectiveDateCol + ">= ?" +
    			" AND ((" + effectiveDateCol + " < ? AND " + pendingStatusCol + " IN ('H', 'L', 'S', 'B', 'F', 'O') AND " + statusCol + " IN ('N','A'))" +
    				" OR (" + effectiveDateCol + " = ? AND " + pendingStatusCol + " IN ('H', 'L', 'S', 'B', 'F', 'O') AND " + statusCol + " IN ('N','A') AND " + priorityCol + " > ? )" +
					" OR (" + effectiveDateCol + " < ? AND " + pendingStatusCol + " IN ('P') AND " + statusCol + " IN ('U'))" +
					" OR (" + effectiveDateCol + " = ? AND " + pendingStatusCol + " IN ('P') AND " + statusCol + " IN ('U') AND " + priorityCol + " > ? )" +
					" OR (" + effectiveDateCol + " <= ? AND " + pendingStatusCol + " IN ('C')))" +
    			" ORDER BY " + effectiveDateCol;
    	
    	Connection conn = null;

    	PreparedStatement ps = null;

    	try
    	{
    		conn = ConnectionFactory.getSingleton().getConnection(POOLNAME);

    		ps = conn.prepareStatement(sql);

    		ps.setLong(1, segmentPK);
    		ps.setLong(2, editTrxPK);
    		ps.setDate(3, DBUtil.convertStringToDate(effectiveDate));
    		ps.setDate(4, DBUtil.convertStringToDate(effectiveDate));
    		ps.setDate(5, DBUtil.convertStringToDate(effectiveDate));
    		ps.setInt(6, priority);
    		ps.setDate(7, DBUtil.convertStringToDate(effectiveDate));
    		ps.setDate(8, DBUtil.convertStringToDate(effectiveDate));
    		ps.setInt(9, priority);
    		ps.setDate(10, DBUtil.convertStringToDate(effectiveDate));

    		editTrxVOs = (EDITTrxVO[]) executeQuery(EDITTrxVO.class,
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

    	return editTrxVOs;
    }
    
    public EDITTrxVO[] findAll_MonthlyValTrx(long segmentPK, String cycleDate, int priority)
    {
    	EDITTrxVO[] editTrxVOs = null;

    	String editTrxPKCol   	  = EDITTRX_DBTABLE.getDBColumn("EDITTrxPK").getFullyQualifiedColumnName();
    	String clientSetupFKCol   = EDITTRX_DBTABLE.getDBColumn("ClientSetupFK").getFullyQualifiedColumnName();
    	String effectiveDateCol   = EDITTRX_DBTABLE.getDBColumn("EffectiveDate").getFullyQualifiedColumnName();
    	String pendingStatusCol   = EDITTRX_DBTABLE.getDBColumn("PendingStatus").getFullyQualifiedColumnName();
    	String statusCol          = EDITTRX_DBTABLE.getDBColumn("Status").getFullyQualifiedColumnName();
    	String edTransactionType  = EDITTRX_DBTABLE.getDBColumn("TransactionTypeCT").getFullyQualifiedColumnName();

    	String clientSetupPKCol   = CLIENT_SETUP_DBTABLE.getDBColumn("ClientSetupPK").getFullyQualifiedColumnName();
    	String contractSetupFKCol = CLIENT_SETUP_DBTABLE.getDBColumn("ContractSetupFK").getFullyQualifiedColumnName();

    	String contractSetupPKCol = CONTRACT_SETUP_DBTABLE.getDBColumn("ContractSetupPK").getFullyQualifiedColumnName();
    	String segmentFKCol       = CONTRACT_SETUP_DBTABLE.getDBColumn("SegmentFK").getFullyQualifiedColumnName();
    	
    	String tpTransactionType  = TRANSACTION_PRIORITY_DBTABLE.getDBColumn("TransactionTypeCT").getFullyQualifiedColumnName();
    	String priorityCol 		  = TRANSACTION_PRIORITY_DBTABLE.getDBColumn("Priority").getFullyQualifiedColumnName();

    	
    	String sql = "SELECT " + EDITTRX_TABLENAME + ".* FROM " + EDITTRX_TABLENAME + ", " + CLIENT_SETUP_TABLENAME + ", " + 
    			CONTRACT_SETUP_TABLENAME + ", " + TRANSACTION_PRIORITY_TABLENAME +
    			" WHERE " + clientSetupFKCol + " = " + clientSetupPKCol +
    			" AND " + contractSetupFKCol + " = " + contractSetupPKCol +
    			" AND " + tpTransactionType +" = " + edTransactionType +
    			" AND " + segmentFKCol + " = ?" +
    			" AND " + pendingStatusCol + "= 'H'" +
    			" AND " + statusCol + " IN ('N', 'A') " +
    			" AND (" + effectiveDateCol + " > ? " +
    				" OR (" + effectiveDateCol + " = ? AND " + priorityCol + " > ? )" +
					" )" +
    			" ORDER BY " + effectiveDateCol;
    	
    	Connection conn = null;

    	PreparedStatement ps = null;

    	try
    	{
    		conn = ConnectionFactory.getSingleton().getConnection(POOLNAME);

    		ps = conn.prepareStatement(sql);

    		ps.setLong(1, segmentPK);
    		ps.setDate(2, DBUtil.convertStringToDate(cycleDate));
    		ps.setDate(3, DBUtil.convertStringToDate(cycleDate));
    		ps.setInt(4, priority);
    		
    		editTrxVOs = (EDITTrxVO[]) executeQuery(EDITTrxVO.class,
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

    	return editTrxVOs;
    }

    public EDITTrxVO[] findAllBySegmentPK(long segmentPK) throws Exception
    {
        String clientSetupFKCol   = EDITTRX_DBTABLE.getDBColumn("ClientSetupFK").getFullyQualifiedColumnName();
        String pendingStatusCol   = EDITTRX_DBTABLE.getDBColumn("PendingStatus").getFullyQualifiedColumnName();
        String effectiveDateCol   = EDITTRX_DBTABLE.getDBColumn("EffectiveDate").getFullyQualifiedColumnName();

        String clientSetupPKCol   = CLIENT_SETUP_DBTABLE.getDBColumn("ClientSetupPK").getFullyQualifiedColumnName();
        String contractSetupFKCol = CLIENT_SETUP_DBTABLE.getDBColumn("ContractSetupFK").getFullyQualifiedColumnName();

        String contractSetupPKCol = CONTRACT_SETUP_DBTABLE.getDBColumn("ContractSetupPK").getFullyQualifiedColumnName();
        String segmentFKCol       = CONTRACT_SETUP_DBTABLE.getDBColumn("SegmentFK").getFullyQualifiedColumnName();

        String sql = "SELECT " + EDITTRX_TABLENAME + ".* FROM " + EDITTRX_TABLENAME + ", " + CLIENT_SETUP_TABLENAME + ", " + CONTRACT_SETUP_TABLENAME +
                     " WHERE " + clientSetupFKCol + " = " + clientSetupPKCol +
                     " AND " + contractSetupFKCol + "=" + contractSetupPKCol +
                     " AND " + segmentFKCol + " = " + segmentPK +
                     " AND " + pendingStatusCol + " = 'H'" +
                     " ORDER BY " + effectiveDateCol + " DESC";

        return (EDITTrxVO[]) executeQuery(EDITTrxVO.class,
                                           sql,
                                            POOLNAME,
                                             false,
                                              null);
    }

    /**
     * This returns back all EDITTrxVO for a segmentPK where the pending status is not H
     * and the transTypeCT is of a particular type.
     * @param segmentPK
     * @param transactionTypeCT
     * @return
     * @throws Exception
     */
    public EDITTrxVO[] findAllNotExecutedBySegmentPK(long segmentPK, String transactionTypeCT) throws Exception
    {
        String clientSetupFKCol = EDITTRX_DBTABLE.getDBColumn("ClientSetupFK").getFullyQualifiedColumnName();
        String pendingStatusCol = EDITTRX_DBTABLE.getDBColumn("PendingStatus").getFullyQualifiedColumnName();
        String effectiveDateCol = EDITTRX_DBTABLE.getDBColumn("EffectiveDate").getFullyQualifiedColumnName();
        String transTypeCtCol = EDITTRX_DBTABLE.getDBColumn("TransactionTypeCT").getFullyQualifiedColumnName();

        String clientSetupPKCol = CLIENT_SETUP_DBTABLE.getDBColumn("ClientSetupPK").getFullyQualifiedColumnName();
        String contractSetupFKCol = CLIENT_SETUP_DBTABLE.getDBColumn("ContractSetupFK").getFullyQualifiedColumnName();

        String contractSetupPKCol = CONTRACT_SETUP_DBTABLE.getDBColumn("ContractSetupPK").getFullyQualifiedColumnName();
        String segmentFKCol = CONTRACT_SETUP_DBTABLE.getDBColumn("SegmentFK").getFullyQualifiedColumnName();

        String sql = "SELECT " + EDITTRX_TABLENAME + ".* FROM " +
                EDITTRX_TABLENAME + ", " +
                CLIENT_SETUP_TABLENAME + ", " +
                CONTRACT_SETUP_TABLENAME +
                " WHERE " + clientSetupFKCol + " = " + clientSetupPKCol +
                " AND " + contractSetupFKCol + "=" + contractSetupPKCol +
                " AND " + segmentFKCol + " = " + segmentPK +
                " AND " + pendingStatusCol + " <> 'H'" +      // SO IT HASN'T EXECUTED
                " AND " + transTypeCtCol + " = '" + transactionTypeCT + "'" +
                " ORDER BY " + effectiveDateCol + " DESC";

        return (EDITTrxVO[]) executeQuery(EDITTrxVO.class,
                sql,
                POOLNAME,
                false,
                null);
    }

    /**
     * Retrieve all EDITTrx records for a given contract and transaction type where the transaction is
     * waiting for a unit value update (pending status = "L" or "S")
     * @param segmentPK
     * @param transactionType
     * @return
     * @throws Exception
     */
    public EDITTrxVO[] findAllBySegmentPKAndTrxType(long segmentPK, String[] transactionTypes) throws Exception
    {
        String clientSetupFKCol   = EDITTRX_DBTABLE.getDBColumn("ClientSetupFK").getFullyQualifiedColumnName();
        String clientSetupPKCol   = CLIENT_SETUP_DBTABLE.getDBColumn("ClientSetupPK").getFullyQualifiedColumnName();
        String contractSetupFKCol = CLIENT_SETUP_DBTABLE.getDBColumn("ContractSetupFK").getFullyQualifiedColumnName();
        String contractSetupPKCol = CONTRACT_SETUP_DBTABLE.getDBColumn("ContractSetupPK").getFullyQualifiedColumnName();
        String segmentFKCol       = CONTRACT_SETUP_DBTABLE.getDBColumn("SegmentFK").getFullyQualifiedColumnName();
        String transactionTypeCTCol = EDITTRX_DBTABLE.getDBColumn("TransactionTypeCT").getFullyQualifiedColumnName();
        String pendingStatusCol = EDITTRX_DBTABLE.getDBColumn("PendingStatus").getFullyQualifiedColumnName();
        String statusCol = EDITTRX_DBTABLE.getDBColumn("Status").getFullyQualifiedColumnName();

        String sqlIn = "";

        for (int i = 0; i < transactionTypes.length; i++)
        {
            if (i < transactionTypes.length - 1)
            {
                sqlIn = sqlIn + "'" + transactionTypes[i] + "', ";
            }
            else
            {
                sqlIn = sqlIn + "'" + transactionTypes[i] + "'";
            }
        }

        String sql = "SELECT " + EDITTRX_TABLENAME + ".* FROM " + EDITTRX_TABLENAME + ", " + CLIENT_SETUP_TABLENAME + ", " + CONTRACT_SETUP_TABLENAME +
                     " WHERE " + clientSetupFKCol + " = " + clientSetupPKCol +
                     " AND " + contractSetupFKCol + " = " + contractSetupPKCol +
                     " AND " + segmentFKCol + " = " + segmentPK +
                     " AND " + transactionTypeCTCol + " IN (" + sqlIn + ")" +
                     " AND " + pendingStatusCol + " IN ('L', 'H')" +
                     " AND " + statusCol + " IN ('N', 'A')";

        return (EDITTrxVO[]) executeQuery(EDITTrxVO.class,
                                           sql,
                                            POOLNAME,
                                             false,
                                              null);
    }
    
    public EDITTrxVO[] findAllBySegmentPKAndProcessedOrderByEffectiveDateDesc(long segmentPK) throws Exception
    {
        String clientSetupFKCol   = EDITTRX_DBTABLE.getDBColumn("ClientSetupFK").getFullyQualifiedColumnName();
        String clientSetupPKCol   = CLIENT_SETUP_DBTABLE.getDBColumn("ClientSetupPK").getFullyQualifiedColumnName();
        String contractSetupFKCol = CLIENT_SETUP_DBTABLE.getDBColumn("ContractSetupFK").getFullyQualifiedColumnName();
        String contractSetupPKCol = CONTRACT_SETUP_DBTABLE.getDBColumn("ContractSetupPK").getFullyQualifiedColumnName();
        String segmentFKCol       = CONTRACT_SETUP_DBTABLE.getDBColumn("SegmentFK").getFullyQualifiedColumnName();
        String transactionTypeCTCol = EDITTRX_DBTABLE.getDBColumn("TransactionTypeCT").getFullyQualifiedColumnName();
        String pendingStatusCol = EDITTRX_DBTABLE.getDBColumn("PendingStatus").getFullyQualifiedColumnName();
        String statusCol = EDITTRX_DBTABLE.getDBColumn("Status").getFullyQualifiedColumnName();
        String effectiveDateCol   = EDITTRX_DBTABLE.getDBColumn("EffectiveDate").getFullyQualifiedColumnName();
        String sql = "SELECT " + EDITTRX_TABLENAME + ".* FROM " + EDITTRX_TABLENAME + ", " + CLIENT_SETUP_TABLENAME + ", " + CONTRACT_SETUP_TABLENAME +
                     " WHERE " + clientSetupFKCol + " = " + clientSetupPKCol +
                     " AND " + contractSetupFKCol + " = " + contractSetupPKCol +
                     " AND " + segmentFKCol + " = " + segmentPK +
                     " AND " + pendingStatusCol + " = 'H'" +
                     " AND " + statusCol + " IN ('N', 'A') " +
                     " ORDER BY " + effectiveDateCol + " desc";
        return (EDITTrxVO[]) executeQuery(EDITTrxVO.class,
                                           sql,
                                            POOLNAME,
                                             false,
                                              null);
    }
    /**
     * Retrieve all EDITTrx records for a given contract and already processed
     * @param segmentPK
     * @param transactionType
     * @return
     * @throws Exception
     */
    public EDITTrxVO[] findAllBySegmentPKAndProcessed(long segmentPK) throws Exception
    {
        String clientSetupFKCol   = EDITTRX_DBTABLE.getDBColumn("ClientSetupFK").getFullyQualifiedColumnName();
        String clientSetupPKCol   = CLIENT_SETUP_DBTABLE.getDBColumn("ClientSetupPK").getFullyQualifiedColumnName();
        String contractSetupFKCol = CLIENT_SETUP_DBTABLE.getDBColumn("ContractSetupFK").getFullyQualifiedColumnName();
        String contractSetupPKCol = CONTRACT_SETUP_DBTABLE.getDBColumn("ContractSetupPK").getFullyQualifiedColumnName();
        String segmentFKCol       = CONTRACT_SETUP_DBTABLE.getDBColumn("SegmentFK").getFullyQualifiedColumnName();
        String transactionTypeCTCol = EDITTRX_DBTABLE.getDBColumn("TransactionTypeCT").getFullyQualifiedColumnName();
        String pendingStatusCol = EDITTRX_DBTABLE.getDBColumn("PendingStatus").getFullyQualifiedColumnName();
        String statusCol = EDITTRX_DBTABLE.getDBColumn("Status").getFullyQualifiedColumnName();

        String sql = "SELECT " + EDITTRX_TABLENAME + ".* FROM " + EDITTRX_TABLENAME + ", " + CLIENT_SETUP_TABLENAME + ", " + CONTRACT_SETUP_TABLENAME +
                     " WHERE " + clientSetupFKCol + " = " + clientSetupPKCol +
                     " AND " + contractSetupFKCol + " = " + contractSetupPKCol +
                     " AND " + segmentFKCol + " = " + segmentPK +
                     " AND " + pendingStatusCol + " = 'H'" +
                     " AND " + statusCol + " IN ('N', 'A') " +
                     " ORDER BY " + segmentFKCol + " desc";

        return (EDITTrxVO[]) executeQuery(EDITTrxVO.class,
                                           sql,
                                            POOLNAME,
                                             false,
                                              null);
    }

    /**
     * Retrieve all EDITTrx records for a given contract and transaction type where the transaction is
     * waiting for a unit value update (pending status = "L" or "S")
     * @param segmentPK
     * @param transactionType
     * @return
     * @throws Exception
     */
    public EDITTrxVO[] findAllPendingBySegmentPKAndTrxType(long segmentPK, String[] transactionTypes) throws Exception
    {
        String clientSetupFKCol   = EDITTRX_DBTABLE.getDBColumn("ClientSetupFK").getFullyQualifiedColumnName();
        String clientSetupPKCol   = CLIENT_SETUP_DBTABLE.getDBColumn("ClientSetupPK").getFullyQualifiedColumnName();
        String contractSetupFKCol = CLIENT_SETUP_DBTABLE.getDBColumn("ContractSetupFK").getFullyQualifiedColumnName();
        String contractSetupPKCol = CONTRACT_SETUP_DBTABLE.getDBColumn("ContractSetupPK").getFullyQualifiedColumnName();
        String segmentFKCol       = CONTRACT_SETUP_DBTABLE.getDBColumn("SegmentFK").getFullyQualifiedColumnName();
        String transactionTypeCTCol = EDITTRX_DBTABLE.getDBColumn("TransactionTypeCT").getFullyQualifiedColumnName();
        String pendingStatusCol = EDITTRX_DBTABLE.getDBColumn("PendingStatus").getFullyQualifiedColumnName();
        String statusCol = EDITTRX_DBTABLE.getDBColumn("Status").getFullyQualifiedColumnName();

        String sqlIn = "";

        for (int i = 0; i < transactionTypes.length; i++)
        {
            if (i < transactionTypes.length - 1)
            {
                sqlIn = sqlIn + "'" + transactionTypes[i] + "', ";
            }
            else
            {
                sqlIn = sqlIn + "'" + transactionTypes[i] + "'";
            }
        }

        String sql = "SELECT " + EDITTRX_TABLENAME + ".* FROM " + EDITTRX_TABLENAME + ", " + CLIENT_SETUP_TABLENAME + ", " + CONTRACT_SETUP_TABLENAME +
                     " WHERE " + clientSetupFKCol + " = " + clientSetupPKCol +
                     " AND " + contractSetupFKCol + " = " + contractSetupPKCol +
                     " AND " + segmentFKCol + " = " + segmentPK +
                     " AND " + transactionTypeCTCol + " IN (" + sqlIn + ")" +
                     " AND " + pendingStatusCol + " = 'P'" +
                     " AND " + statusCol + " IN ('N', 'A')";

        return (EDITTrxVO[]) executeQuery(EDITTrxVO.class,
                                           sql,
                                            POOLNAME,
                                             false,
                                              null);
    }

    /**
     * Retrieve all pending EDITTrx records for a given contract  
     * @param segmentPK
     * @return
     * @throws Exception
     */
    public EDITTrxVO[] findAllPendingByContractNumber(String contractNumber) throws Exception
    {
    	EDITTrxVO[] editTrxVOs = null;

        String clientSetupFKCol = EDITTRX_DBTABLE.getDBColumn("ClientSetupFK").getFullyQualifiedColumnName();
        String pendingStatusCol = EDITTRX_DBTABLE.getDBColumn("PendingStatus").getFullyQualifiedColumnName();

        String clientSetupPKCol = CLIENT_SETUP_DBTABLE.getDBColumn("ClientSetupPK").getFullyQualifiedColumnName();
        String contractSetupFKCol = CLIENT_SETUP_DBTABLE.getDBColumn("ContractSetupFK").getFullyQualifiedColumnName();
        
        String contractSetupPKCol = CONTRACT_SETUP_DBTABLE.getDBColumn("ContractSetupPK").getFullyQualifiedColumnName();
        String segmentFKCol = CONTRACT_SETUP_DBTABLE.getDBColumn("SegmentFK").getFullyQualifiedColumnName();
        
        String contractNumberCol  = SEGMENT_DBTABLE.getDBColumn("ContractNumber").getFullyQualifiedColumnName();
        String segmentPKCol = SEGMENT_DBTABLE.getDBColumn("SegmentPK").getFullyQualifiedColumnName();

        String sql = "SELECT " + EDITTRX_TABLENAME + ".* FROM " + EDITTRX_TABLENAME + ", " + CLIENT_SETUP_TABLENAME + ", " + 
        			CONTRACT_SETUP_TABLENAME + ", " + SEGMENT_TABLENAME +
                     " WHERE " + clientSetupFKCol + " = " + clientSetupPKCol +
                     " AND " + contractSetupFKCol + " = " + contractSetupPKCol +
                     " AND " + segmentFKCol + " = " + segmentPKCol +
                     " AND " + contractNumberCol + " = ?" +
                     " AND " + pendingStatusCol + " = 'P'";
        
        Connection conn = null;

        PreparedStatement ps = null;

        try
        {
            conn = ConnectionFactory.getSingleton().getConnection(POOLNAME);

            ps = conn.prepareStatement(sql); 

            ps.setString(1, contractNumber);

            editTrxVOs = (EDITTrxVO[]) executeQuery(EDITTrxVO.class,
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

        return editTrxVOs;
    }

    public EDITTrxVO[] findByEDITTrxPK(long editTrxPK)
    {
        String editTrxPKCol = EDITTRX_DBTABLE.getDBColumn("EDITTrxPK").getFullyQualifiedColumnName();

        String sql = "SELECT * FROM " + EDITTRX_TABLENAME +
                     " WHERE " + editTrxPKCol + " = " + editTrxPK;

        return (EDITTrxVO[]) executeQuery(EDITTrxVO.class,
                                           sql,
                                            POOLNAME,
                                             false,
                                              null);
    }

    public EDITTrxVO[] findByEDITTrxPK(long editTrxPK, boolean includeChildrenVOs, List voExclusionList) throws Exception
    {
        String editTrxPKCol = EDITTRX_DBTABLE.getDBColumn("EDITTrxPK").getFullyQualifiedColumnName();

        String sql = "SELECT * FROM " + EDITTRX_TABLENAME +
                     " WHERE " + editTrxPKCol + " = " + editTrxPK;

        return (EDITTrxVO[]) executeQuery(EDITTrxVO.class,
                                           sql,
                                            POOLNAME,
                                             includeChildrenVOs,
                                              voExclusionList);
    }       

    public EDITTrxVO[] findByCommissionHistoryPK(long commissionHistoryPK) throws Exception
    {
        DBTable editTrxHistoryDBTable    = DBTable.getDBTableForTable("EDITTrxHistory");
        DBTable commissionHistoryDBTable = DBTable.getDBTableForTable("CommissionHistory");

        String editTrxHistoryTable    = editTrxHistoryDBTable.getFullyQualifiedTableName();
        String commissionHistoryTable = commissionHistoryDBTable.getFullyQualifiedTableName();

        String editTrxPKCol           = EDITTRX_DBTABLE.getDBColumn("EDITTrxPK").getFullyQualifiedColumnName();

        String editTrxHistoryPKCol    = editTrxHistoryDBTable.getDBColumn("EDITTrxHistoryPK").getFullyQualifiedColumnName();
        String editTrxFKCol           = editTrxHistoryDBTable.getDBColumn("EDITTrxFK").getFullyQualifiedColumnName();

        String editTrxHistoryFKCol    = commissionHistoryDBTable.getDBColumn("EDITTrxHistoryFK").getFullyQualifiedColumnName();
        String commissionHistoryPKCol = commissionHistoryDBTable.getDBColumn("CommissionHistoryPK").getFullyQualifiedColumnName();

        String sql = "SELECT * FROM " + EDITTRX_TABLENAME + ", " + editTrxHistoryTable + ", " + commissionHistoryTable +
                     " WHERE " + editTrxHistoryFKCol + " = " + editTrxHistoryPKCol +
                     " AND " + editTrxFKCol + " = " + editTrxPKCol +
                     " AND " + commissionHistoryPKCol + " = " + commissionHistoryPK;

        return (EDITTrxVO[]) executeQuery(EDITTrxVO.class,
                                           sql,
                                            POOLNAME,
                                             false,
                                              null);
    }

    public EDITTrxVO[] findByClientSetupPK(long clientSetupPK) throws Exception
    {
        String clientSetupFKCol = EDITTRX_DBTABLE.getDBColumn("ClientSetupFK").getFullyQualifiedColumnName();

        String sql = "SELECT * FROM " + EDITTRX_TABLENAME +
                     " WHERE " + clientSetupFKCol + " = " + clientSetupPK;

        return (EDITTrxVO[]) executeQuery(EDITTrxVO.class,
                                           sql,
                                            POOLNAME,
                                             false,
                                              null);
    }

    public EDITTrxVO[] findBySegmentPK_AND_PendingStatus(List segmentPKList, String[] pendingStatuses) throws Exception
    {
        String clientSetupFKCol   = EDITTRX_DBTABLE.getDBColumn("ClientSetupFK").getFullyQualifiedColumnName();
        String pendingStatusCol   = EDITTRX_DBTABLE.getDBColumn("PendingStatus").getFullyQualifiedColumnName();

        String clientSetupPKCol   = CLIENT_SETUP_DBTABLE.getDBColumn("ClientSetupPK").getFullyQualifiedColumnName();
        String contractSetupFKCol = CLIENT_SETUP_DBTABLE.getDBColumn("ContractSetupFK").getFullyQualifiedColumnName();

        String contractSetupPKCol = CONTRACT_SETUP_DBTABLE.getDBColumn("ContractSetupPK").getFullyQualifiedColumnName();
        String segmentFKCol       = CONTRACT_SETUP_DBTABLE.getDBColumn("SegmentFK").getFullyQualifiedColumnName();

        String segmentPKIn = "";
        String sqlIn = "";

        int i = 0;

        for (i = 0; i < segmentPKList.size(); i++)
        {
            if (i < segmentPKList.size() - 1)
            {
                segmentPKIn = segmentPKIn + segmentPKList.get(i) + ", ";
            }
            else
            {
                segmentPKIn = segmentPKIn + segmentPKList.get(i) + ")";
            }
        }

        for (i = 0; i < pendingStatuses.length; i++)
        {
            if (i < pendingStatuses.length - 1)
            {
                sqlIn = sqlIn + pendingStatuses[i] + "', '";
            }
            else
            {
                sqlIn = sqlIn + pendingStatuses[i] + "')";
            }
        }

        String sql = "SELECT " + EDITTRX_TABLENAME + ".* FROM " + EDITTRX_TABLENAME + ", " + CLIENT_SETUP_TABLENAME + ", " + CONTRACT_SETUP_TABLENAME +
                     " WHERE " + clientSetupFKCol + " = " + clientSetupPKCol +
                     " AND " + contractSetupFKCol + " = " + contractSetupPKCol +
                     " AND " + segmentFKCol + " IN (" + segmentPKIn +
                     " AND " + pendingStatusCol +" IN ('" + sqlIn;

        return (EDITTrxVO[]) executeQuery(EDITTrxVO.class,
                                           sql,
                                            POOLNAME,
                                             false,
                                              null);
    }

    public EDITTrxVO[] findBySegmentPK_AND_PendingStatus_AND_NotTransactionType(List segmentPKList, String[] pendingStatuses, String[] transactionTypes) throws Exception
    {
        String clientSetupFKCol   = EDITTRX_DBTABLE.getDBColumn("ClientSetupFK").getFullyQualifiedColumnName();
        String pendingStatusCol   = EDITTRX_DBTABLE.getDBColumn("PendingStatus").getFullyQualifiedColumnName();
        String transactionTypeCTCol = EDITTRX_DBTABLE.getDBColumn("TransactionTypeCT").getFullyQualifiedColumnName();

        String clientSetupPKCol   = CLIENT_SETUP_DBTABLE.getDBColumn("ClientSetupPK").getFullyQualifiedColumnName();
        String contractSetupFKCol = CLIENT_SETUP_DBTABLE.getDBColumn("ContractSetupFK").getFullyQualifiedColumnName();

        String contractSetupPKCol = CONTRACT_SETUP_DBTABLE.getDBColumn("ContractSetupPK").getFullyQualifiedColumnName();
        String segmentFKCol       = CONTRACT_SETUP_DBTABLE.getDBColumn("SegmentFK").getFullyQualifiedColumnName();

        String segmentPKIn = "";
        String sqlIn = "";
        String sqlNotIn = "";

        int i = 0;

        for (i = 0; i < segmentPKList.size(); i++)
        {
            if (i < segmentPKList.size() - 1)
            {
                segmentPKIn = segmentPKIn + segmentPKList.get(i) + ", ";
            }
            else
            {
                segmentPKIn = segmentPKIn + segmentPKList.get(i) + ")";
            }
        }

        for (i = 0; i < pendingStatuses.length; i++)
        {
            if (i < pendingStatuses.length - 1)
            {
                sqlIn = sqlIn + pendingStatuses[i] + "', '";
            }
            else
            {
                sqlIn = sqlIn + pendingStatuses[i] + "')";
            }
        }
        
        for (i = 0; i < transactionTypes.length; i++)
        {
            if (i < transactionTypes.length - 1)
            {
                sqlNotIn = sqlNotIn + transactionTypes[i] + "', '";
            }
            else
            {
                sqlNotIn = sqlNotIn + transactionTypes[i] + "')";
            }
        }

        String sql = "SELECT " + EDITTRX_TABLENAME + ".* FROM " + EDITTRX_TABLENAME + ", " + CLIENT_SETUP_TABLENAME + ", " + CONTRACT_SETUP_TABLENAME +
                     " WHERE " + clientSetupFKCol + " = " + clientSetupPKCol +
                     " AND " + contractSetupFKCol + " = " + contractSetupPKCol +
                     " AND " + segmentFKCol + " IN (" + segmentPKIn +
                     " AND " + pendingStatusCol +" IN ('" + sqlIn +
                     " AND " + transactionTypeCTCol + " NOT IN ('" + sqlNotIn;

        return (EDITTrxVO[]) executeQuery(EDITTrxVO.class,
                                           sql,
                                            POOLNAME,
                                             false,
                                              null);
    }
    
    public EDITTrxVO[] findBySegmentPK_AND_EffectiveDateLTE_AND_PendingStatus(long segmentPK, 
    		String effectiveDate, String[] pendingStatuses)
    {
        EDITTrxVO[] editTrxVOs = null;

        String clientSetupFKCol   = EDITTRX_DBTABLE.getDBColumn("ClientSetupFK").getFullyQualifiedColumnName();
        String pendingStatusCol   = EDITTRX_DBTABLE.getDBColumn("PendingStatus").getFullyQualifiedColumnName();
        String effectiveDateCol   = EDITTRX_DBTABLE.getDBColumn("EffectiveDate").getFullyQualifiedColumnName();
        String statusCol = EDITTRX_DBTABLE.getDBColumn("Status").getFullyQualifiedColumnName();

        String clientSetupPKCol   = CLIENT_SETUP_DBTABLE.getDBColumn("ClientSetupPK").getFullyQualifiedColumnName();
        String contractSetupFKCol = CLIENT_SETUP_DBTABLE.getDBColumn("ContractSetupFK").getFullyQualifiedColumnName();

        String contractSetupPKCol = CONTRACT_SETUP_DBTABLE.getDBColumn("ContractSetupPK").getFullyQualifiedColumnName();
        String segmentFKCol       = CONTRACT_SETUP_DBTABLE.getDBColumn("SegmentFK").getFullyQualifiedColumnName();

        String sqlIn = "";

        for (int i = 0; i < pendingStatuses.length; i++)
        {
            if (i < pendingStatuses.length - 1)
            {
                sqlIn = sqlIn + pendingStatuses[i] + "', '";
            }
            else
            {
                sqlIn = sqlIn + pendingStatuses[i] + "')";
            }
        }

        String sql = "SELECT " + EDITTRX_TABLENAME + ".* FROM " + EDITTRX_TABLENAME + ", " + CLIENT_SETUP_TABLENAME + ", " + CONTRACT_SETUP_TABLENAME +
                     " WHERE " + clientSetupFKCol + " = " + clientSetupPKCol +
                     " AND " + contractSetupFKCol + " = " + contractSetupPKCol +
                     " AND " + segmentFKCol + " = " + segmentPK +
                     " AND " + pendingStatusCol + " IN ('" + sqlIn +
                     " AND " + statusCol + " IN ('N', 'A')" +
                     " AND " + effectiveDateCol + " <= ?";

        Connection conn = null;

        PreparedStatement ps = null;

        try
        {
            conn = ConnectionFactory.getSingleton().getConnection(POOLNAME);

            ps = conn.prepareStatement(sql);

            ps.setDate(1, DBUtil.convertStringToDate(effectiveDate));

            editTrxVOs = (EDITTrxVO[]) executeQuery(EDITTrxVO.class,
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

        return editTrxVOs;
    }

    public EDITTrxVO findBySegmentPK_LastProcessedTrx(long segmentPK)
    {
        EDITTrxVO[] editTrxVOs = null;

        String clientSetupFKCol   = EDITTRX_DBTABLE.getDBColumn("ClientSetupFK").getFullyQualifiedColumnName();
        String pendingStatusCol   = EDITTRX_DBTABLE.getDBColumn("PendingStatus").getFullyQualifiedColumnName();
        String maintDateCol   	  = EDITTRX_DBTABLE.getDBColumn("MaintDateTime").getFullyQualifiedColumnName();
        String statusCol 		  = EDITTRX_DBTABLE.getDBColumn("Status").getFullyQualifiedColumnName();

        String clientSetupPKCol   = CLIENT_SETUP_DBTABLE.getDBColumn("ClientSetupPK").getFullyQualifiedColumnName();
        String contractSetupFKCol = CLIENT_SETUP_DBTABLE.getDBColumn("ContractSetupFK").getFullyQualifiedColumnName();

        String contractSetupPKCol = CONTRACT_SETUP_DBTABLE.getDBColumn("ContractSetupPK").getFullyQualifiedColumnName();
        String segmentFKCol       = CONTRACT_SETUP_DBTABLE.getDBColumn("SegmentFK").getFullyQualifiedColumnName();

        String sql = "SELECT " + EDITTRX_TABLENAME + ".* FROM " + EDITTRX_TABLENAME + ", " + CLIENT_SETUP_TABLENAME + ", " + CONTRACT_SETUP_TABLENAME +
                     " WHERE " + clientSetupFKCol + " = " + clientSetupPKCol +
                     " AND " + contractSetupFKCol + " = " + contractSetupPKCol +
                     " AND " + segmentFKCol + " = " + segmentPK +
                     " AND " + pendingStatusCol + " = ('H')" +
                    // " AND " + statusCol + " IN ('N', 'A')" +
                     " ORDER BY " + maintDateCol + " desc";

        Connection conn = null;

        PreparedStatement ps = null;

        try
        {
            conn = ConnectionFactory.getSingleton().getConnection(POOLNAME);

            ps = conn.prepareStatement(sql);

            editTrxVOs = (EDITTrxVO[]) executeQuery(EDITTrxVO.class,
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
        
        if (editTrxVOs != null)
        {
        	return editTrxVOs[0];
        }
        else
        {
        	return null;
        }
    }

    public EDITTrxVO[] findByTransactionTypeCT_AND_EffectiveDateLTE_AND_PendingStatus(String transactionTypeCT, String effectiveDate, String pendingStatus) throws Exception
    {
        EDITTrxVO[] editTrxVOs = null;

        String transactionTypeCTCol = EDITTRX_DBTABLE.getDBColumn("TransactionTypeCT").getFullyQualifiedColumnName();
        String pendingStatusCol     = EDITTRX_DBTABLE.getDBColumn("PendingStatus").getFullyQualifiedColumnName();
        String effectiveDateCol     = EDITTRX_DBTABLE.getDBColumn("EffectiveDate").getFullyQualifiedColumnName();

        String sql = "SELECT " + EDITTRX_TABLENAME + ".* FROM " + EDITTRX_TABLENAME +
                     " WHERE " + transactionTypeCTCol + " = '" + transactionTypeCT + "'" +
                     " AND " + pendingStatusCol + " = '" + pendingStatus + "'" +
                     " AND " + effectiveDateCol + " <= ?";

        Connection conn = null;

        PreparedStatement ps = null;

        try
        {
            conn = ConnectionFactory.getSingleton().getConnection(POOLNAME);

            ps = conn.prepareStatement(sql);

            ps.setDate(1, DBUtil.convertStringToDate(effectiveDate));

            editTrxVOs = (EDITTrxVO[]) executeQuery(EDITTrxVO.class,
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

        return editTrxVOs;
    }
    
    public EDITTrxVO[] findByTransactionTypeCT_AND_EffectiveDateLTE_AND_PendingStatus_Descending(
    		String transactionTypeCT, String effectiveDate, String pendingStatus) throws Exception
    {
        EDITTrxVO[] editTrxVOs = null;

        String transactionTypeCTCol = EDITTRX_DBTABLE.getDBColumn("TransactionTypeCT").getFullyQualifiedColumnName();
        String pendingStatusCol     = EDITTRX_DBTABLE.getDBColumn("PendingStatus").getFullyQualifiedColumnName();
        String effectiveDateCol     = EDITTRX_DBTABLE.getDBColumn("EffectiveDate").getFullyQualifiedColumnName();

        String sql = "SELECT " + EDITTRX_TABLENAME + ".* FROM " + EDITTRX_TABLENAME +
                     " WHERE " + transactionTypeCTCol + " = '" + transactionTypeCT + "'" +
                     " AND " + pendingStatusCol + " = '" + pendingStatus + "'" +
                     " AND " + effectiveDateCol + " <= ?" +
                     " ORDER BY " + effectiveDateCol + " DESC";


        Connection conn = null;

        PreparedStatement ps = null;

        try
        {
            conn = ConnectionFactory.getSingleton().getConnection(POOLNAME);

            ps = conn.prepareStatement(sql);

            ps.setDate(1, DBUtil.convertStringToDate(effectiveDate));

            editTrxVOs = (EDITTrxVO[]) executeQuery(EDITTrxVO.class,
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

        return editTrxVOs;
    }
    
    public EDITTrxVO[] findActiveBySegment_TransactionTypeCT_BeforeEffectiveDate_AND_PendingStatus(long segmentPK,
                                                                                       String transactionTypeCT,
                                                                                       String toDate,
                                                                                       String pendingStatus) 
    {
        String contractSetupPKCol = CONTRACT_SETUP_DBTABLE.getDBColumn("ContractSetupPK").getFullyQualifiedColumnName();
        String segmentFKCol = CONTRACT_SETUP_DBTABLE.getDBColumn("SegmentFK").getFullyQualifiedColumnName();
        String statusCol = EDITTRX_DBTABLE.getDBColumn("Status").getFullyQualifiedColumnName();
        String clientSetupPKCol = CLIENT_SETUP_DBTABLE.getDBColumn("ClientSetupPK").getFullyQualifiedColumnName();
        String contractSetupFKCol = CLIENT_SETUP_DBTABLE.getDBColumn("ContractSetupFK").getFullyQualifiedColumnName();
        String clientSetupFKCol = EDITTRX_DBTABLE.getDBColumn("ClientSetupFK").getFullyQualifiedColumnName();
        String transactionTypeCTCol = EDITTRX_DBTABLE.getDBColumn("TransactionTypeCT").getFullyQualifiedColumnName();
        String pendingStatusCol     = EDITTRX_DBTABLE.getDBColumn("PendingStatus").getFullyQualifiedColumnName();
        String effectiveDateCol     = EDITTRX_DBTABLE.getDBColumn("EffectiveDate").getFullyQualifiedColumnName();
        String sql = "SELECT " + EDITTRX_TABLENAME + ".* FROM " + EDITTRX_TABLENAME + ", " +
                     CLIENT_SETUP_TABLENAME + ", " + CONTRACT_SETUP_TABLENAME +
                     " WHERE " + segmentFKCol + " = ?" +
                     " AND " + contractSetupPKCol + " = " + contractSetupFKCol +
                     " AND " + clientSetupPKCol + " = " + clientSetupFKCol +
                     " AND " + transactionTypeCTCol + " = ?" +
                     " AND " + pendingStatusCol + " = ?" +
                     " AND " + effectiveDateCol + " < ?" +
                     " AND " + statusCol + " in ('N', 'A')" + // OR " + statusCol + " = 'A')" +
                     " ORDER BY " + effectiveDateCol + " DESC";
        EDITTrxVO[] editTrxVOs = null;
        Connection conn = null;
        PreparedStatement ps = null;
        try
        {
            conn = ConnectionFactory.getSingleton().getConnection(POOLNAME);
            ps = conn.prepareStatement(sql);
            ps.setLong(1, segmentPK);
            ps.setString(2, transactionTypeCT);
            ps.setString(3, pendingStatus);
            ps.setDate(4, DBUtil.convertStringToDate(toDate));
            editTrxVOs = (EDITTrxVO[]) executeQuery(EDITTrxVO.class,
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
        return editTrxVOs;
    }
    

    public EDITTrxVO[] findActiveBySegment_TransactionTypeCT_AND_PendingStatus(long segmentPK,
                                                                                       String transactionTypeCT,
                                                                                       String pendingStatus) 
    {
        String contractSetupPKCol = CONTRACT_SETUP_DBTABLE.getDBColumn("ContractSetupPK").getFullyQualifiedColumnName();
        String segmentFKCol = CONTRACT_SETUP_DBTABLE.getDBColumn("SegmentFK").getFullyQualifiedColumnName();
        String statusCol = EDITTRX_DBTABLE.getDBColumn("Status").getFullyQualifiedColumnName();
        String clientSetupPKCol = CLIENT_SETUP_DBTABLE.getDBColumn("ClientSetupPK").getFullyQualifiedColumnName();
        String contractSetupFKCol = CLIENT_SETUP_DBTABLE.getDBColumn("ContractSetupFK").getFullyQualifiedColumnName();
        String clientSetupFKCol = EDITTRX_DBTABLE.getDBColumn("ClientSetupFK").getFullyQualifiedColumnName();
        String transactionTypeCTCol = EDITTRX_DBTABLE.getDBColumn("TransactionTypeCT").getFullyQualifiedColumnName();
        String pendingStatusCol     = EDITTRX_DBTABLE.getDBColumn("PendingStatus").getFullyQualifiedColumnName();
        String effectiveDateCol     = EDITTRX_DBTABLE.getDBColumn("EffectiveDate").getFullyQualifiedColumnName();
        String sql = "SELECT " + EDITTRX_TABLENAME + ".* FROM " + EDITTRX_TABLENAME + ", " +
                     CLIENT_SETUP_TABLENAME + ", " + CONTRACT_SETUP_TABLENAME +
                     " WHERE " + segmentFKCol + " = ?" +
                     " AND " + contractSetupPKCol + " = " + contractSetupFKCol +
                     " AND " + clientSetupPKCol + " = " + clientSetupFKCol +
                     " AND " + transactionTypeCTCol + " = ?" +
                     " AND " + pendingStatusCol + " = ?" +
                     " AND " + statusCol + " in ('N', 'A')" + 
                     " ORDER BY " + effectiveDateCol + " DESC";
        
        EDITTrxVO[] editTrxVOs = null;
        Connection conn = null;
        PreparedStatement ps = null;
        try
        {
            conn = ConnectionFactory.getSingleton().getConnection(POOLNAME);
            ps = conn.prepareStatement(sql);
            ps.setLong(1, segmentPK);
            ps.setString(2, transactionTypeCT);
            ps.setString(3, pendingStatus);
            editTrxVOs = (EDITTrxVO[]) executeQuery(EDITTrxVO.class,
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
        return editTrxVOs;
    }
    
    public EDITTrxVO[] findBySegment_TransactionTypeCT_BeforeEffectiveDate_AND_PendingStatus(long segmentPK,
                                                                                       String transactionTypeCT,
                                                                                       String toDate,
                                                                                       String pendingStatus) 
    {
        String contractSetupPKCol = CONTRACT_SETUP_DBTABLE.getDBColumn("ContractSetupPK").getFullyQualifiedColumnName();
        String segmentFKCol = CONTRACT_SETUP_DBTABLE.getDBColumn("SegmentFK").getFullyQualifiedColumnName();

        String clientSetupPKCol = CLIENT_SETUP_DBTABLE.getDBColumn("ClientSetupPK").getFullyQualifiedColumnName();
        String contractSetupFKCol = CLIENT_SETUP_DBTABLE.getDBColumn("ContractSetupFK").getFullyQualifiedColumnName();

        String clientSetupFKCol = EDITTRX_DBTABLE.getDBColumn("ClientSetupFK").getFullyQualifiedColumnName();
        String transactionTypeCTCol = EDITTRX_DBTABLE.getDBColumn("TransactionTypeCT").getFullyQualifiedColumnName();
        String pendingStatusCol     = EDITTRX_DBTABLE.getDBColumn("PendingStatus").getFullyQualifiedColumnName();
        String effectiveDateCol     = EDITTRX_DBTABLE.getDBColumn("EffectiveDate").getFullyQualifiedColumnName();

        String sql = "SELECT " + EDITTRX_TABLENAME + ".* FROM " + EDITTRX_TABLENAME + ", " +
                     CLIENT_SETUP_TABLENAME + ", " + CONTRACT_SETUP_TABLENAME +
                     " WHERE " + segmentFKCol + " = ?" +
                     " AND " + contractSetupPKCol + " = " + contractSetupFKCol +
                     " AND " + clientSetupPKCol + " = " + clientSetupFKCol +
                     " AND " + transactionTypeCTCol + " = ?" +
                     " AND " + pendingStatusCol + " = ?" +
                     " AND " + effectiveDateCol + " < ?" +
                     " ORDER BY " + effectiveDateCol + " DESC";

        EDITTrxVO[] editTrxVOs = null;
        Connection conn = null;
        PreparedStatement ps = null;

        try
        {
            conn = ConnectionFactory.getSingleton().getConnection(POOLNAME);

            ps = conn.prepareStatement(sql);

            ps.setLong(1, segmentPK);
            ps.setString(2, transactionTypeCT);
            ps.setString(3, pendingStatus);
            ps.setDate(4, DBUtil.convertStringToDate(toDate));

            editTrxVOs = (EDITTrxVO[]) executeQuery(EDITTrxVO.class,
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

        return editTrxVOs;
    }
    
    

    public EDITTrxVO[] findBySegment_TransactionTypeCT_EffectiveDate_AND_PendingStatus(long segmentPK,
                                                                                       String transactionTypeCT,
                                                                                       String fromDate,
                                                                                       String toDate,
                                                                                       String pendingStatus) 
    {
        String contractSetupPKCol = CONTRACT_SETUP_DBTABLE.getDBColumn("ContractSetupPK").getFullyQualifiedColumnName();
        String segmentFKCol = CONTRACT_SETUP_DBTABLE.getDBColumn("SegmentFK").getFullyQualifiedColumnName();

        String clientSetupPKCol = CLIENT_SETUP_DBTABLE.getDBColumn("ClientSetupPK").getFullyQualifiedColumnName();
        String contractSetupFKCol = CLIENT_SETUP_DBTABLE.getDBColumn("ContractSetupFK").getFullyQualifiedColumnName();

        String clientSetupFKCol = EDITTRX_DBTABLE.getDBColumn("ClientSetupFK").getFullyQualifiedColumnName();
        String transactionTypeCTCol = EDITTRX_DBTABLE.getDBColumn("TransactionTypeCT").getFullyQualifiedColumnName();
        String pendingStatusCol     = EDITTRX_DBTABLE.getDBColumn("PendingStatus").getFullyQualifiedColumnName();
        String effectiveDateCol     = EDITTRX_DBTABLE.getDBColumn("EffectiveDate").getFullyQualifiedColumnName();

        String sql = "SELECT " + EDITTRX_TABLENAME + ".* FROM " + EDITTRX_TABLENAME + ", " +
                     CLIENT_SETUP_TABLENAME + ", " + CONTRACT_SETUP_TABLENAME +
                     " WHERE " + segmentFKCol + " = ?" +
                     " AND " + contractSetupPKCol + " = " + contractSetupFKCol +
                     " AND " + clientSetupPKCol + " = " + clientSetupFKCol +
                     " AND " + transactionTypeCTCol + " = ?" +
                     " AND " + pendingStatusCol + " = ?" +
                     " AND " + effectiveDateCol + " >= ?" +
                     " AND " + effectiveDateCol + " < ?";

        EDITTrxVO[] editTrxVOs = null;
        Connection conn = null;
        PreparedStatement ps = null;

        try
        {
            conn = ConnectionFactory.getSingleton().getConnection(POOLNAME);

            ps = conn.prepareStatement(sql);

            ps.setLong(1, segmentPK);
            ps.setString(2, transactionTypeCT);
            ps.setString(3, pendingStatus);
            ps.setDate(4, DBUtil.convertStringToDate(fromDate));
            ps.setDate(5, DBUtil.convertStringToDate(toDate));

            editTrxVOs = (EDITTrxVO[]) executeQuery(EDITTrxVO.class,
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

        return editTrxVOs;
    }
    
    

    public EDITTrxVO[] findByCycleDateLT(String cycleDate) throws Exception
    {
        EDITTrxVO[] editTrxVOs = null;

        String effectiveDateCol = EDITTRX_DBTABLE.getDBColumn("EffectiveDate").getFullyQualifiedColumnName();
        String pendingStatusCol = EDITTRX_DBTABLE.getDBColumn("PendingStatus").getFullyQualifiedColumnName();

        String sql = "SELECT * FROM " + EDITTRX_TABLENAME +
                     " WHERE " + effectiveDateCol + " <= ?" +
                     " AND " + pendingStatusCol + " = 'P'";

        Connection conn = null;

        PreparedStatement ps = null;

        try
        {
            conn = ConnectionFactory.getSingleton().getConnection(POOLNAME);

            ps = conn.prepareStatement(sql);

            ps.setDate(1, DBUtil.convertStringToDate(cycleDate));

            editTrxVOs = (EDITTrxVO[]) executeQuery(EDITTrxVO.class,
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

        return editTrxVOs;
    }

    public EDITTrxVO[] findBySegmentPKsAndCycleDate(long[] segmentKeys, String cycleDate) throws Exception
    {
        EDITTrxVO[] editTrxVOs = null;

        String clientSetupFKCol   = EDITTRX_DBTABLE.getDBColumn("ClientSetupFK").getFullyQualifiedColumnName();
        String effectiveDateCol   = EDITTRX_DBTABLE.getDBColumn("EffectiveDate").getFullyQualifiedColumnName();
        String pendingStatusCol   = EDITTRX_DBTABLE.getDBColumn("PendingStatus").getFullyQualifiedColumnName();

        String clientSetupPKCol   = CLIENT_SETUP_DBTABLE.getDBColumn("ClientSetupPK").getFullyQualifiedColumnName();
        String contractSetupFKCol = CLIENT_SETUP_DBTABLE.getDBColumn("ContractSetupFK").getFullyQualifiedColumnName();

        String contractSetupPKCol = CONTRACT_SETUP_DBTABLE.getDBColumn("ContractSetupPK").getFullyQualifiedColumnName();
        String segmentFKCol       = CONTRACT_SETUP_DBTABLE.getDBColumn("SegmentFK").getFullyQualifiedColumnName();

        String sqlIn = "";

        for (int i = 0; i < segmentKeys.length; i++)
        {
            if (i < segmentKeys.length - 1)
            {
                sqlIn = sqlIn + segmentKeys[i] + ", ";
            }
            else
            {
                sqlIn = sqlIn + segmentKeys[i];
            }
        }

        String sql = "SELECT " + EDITTRX_TABLENAME + ".* FROM " + EDITTRX_TABLENAME + ", " + CLIENT_SETUP_TABLENAME + ", " + CONTRACT_SETUP_TABLENAME +
                     " WHERE " + clientSetupFKCol + " = " + clientSetupPKCol +
                     " AND " + contractSetupFKCol + " = " + contractSetupPKCol +
                     " AND " + effectiveDateCol + " <= ?" +
                     " AND " + pendingStatusCol + " = 'P'" +
                     " AND " + segmentFKCol + " IN (" + sqlIn + ")";

        Connection conn = null;

        PreparedStatement ps = null;

        try
        {
            conn = ConnectionFactory.getSingleton().getConnection(POOLNAME);

            ps = conn.prepareStatement(sql);

            ps.setDate(1, DBUtil.convertStringToDate(cycleDate));

            editTrxVOs = (EDITTrxVO[]) executeQuery(EDITTrxVO.class,
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

        return editTrxVOs;
    }

    public EDITTrxVO[] findBySegmentPK_AND_EffectiveDateGT(long segmentPK, String effectiveDate) throws Exception
    {
        EDITTrxVO[] editTrxVOs = null;

        String clientSetupFKCol   = EDITTRX_DBTABLE.getDBColumn("ClientSetupFK").getFullyQualifiedColumnName();
        String effectiveDateCol   = EDITTRX_DBTABLE.getDBColumn("EffectiveDate").getFullyQualifiedColumnName();
        String pendingStatusCol   = EDITTRX_DBTABLE.getDBColumn("PendingStatus").getFullyQualifiedColumnName();

        String clientSetupPKCol   = CLIENT_SETUP_DBTABLE.getDBColumn("ClientSetupPK").getFullyQualifiedColumnName();
        String contractSetupFKCol = CLIENT_SETUP_DBTABLE.getDBColumn("ContractSetupFK").getFullyQualifiedColumnName();

        String contractSetupPKCol = CONTRACT_SETUP_DBTABLE.getDBColumn("ContractSetupPK").getFullyQualifiedColumnName();
        String segmentFKCol       = CONTRACT_SETUP_DBTABLE.getDBColumn("SegmentFK").getFullyQualifiedColumnName();

        String sql = "SELECT " + EDITTRX_TABLENAME + ".* FROM " + EDITTRX_TABLENAME + ", " + CLIENT_SETUP_TABLENAME + ", " + CONTRACT_SETUP_TABLENAME +
                     " WHERE " + clientSetupFKCol + " = " + clientSetupPKCol +
                     " AND " + contractSetupFKCol + " = " + contractSetupPKCol +
                     " AND " + segmentFKCol + " = " + segmentPK +
                     " AND " + effectiveDateCol + " >= ?" +
                     " AND " + pendingStatusCol + " IN ('H', 'L')";

        Connection conn = null;

        PreparedStatement ps = null;

        try
        {
            conn = ConnectionFactory.getSingleton().getConnection(POOLNAME);

            ps = conn.prepareStatement(sql);

            ps.setDate(1, DBUtil.convertStringToDate(effectiveDate));

            editTrxVOs = (EDITTrxVO[]) executeQuery(EDITTrxVO.class,
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

        return editTrxVOs;
    }
    
    public EDITTrxVO[] findBySegmentPK_AND_EffectiveDateGT_AND_NotTransactionType(long segmentPK, String effectiveDate, String transactionType) throws Exception
    {
        EDITTrxVO[] editTrxVOs = null;

        String clientSetupFKCol   = EDITTRX_DBTABLE.getDBColumn("ClientSetupFK").getFullyQualifiedColumnName();
        String effectiveDateCol   = EDITTRX_DBTABLE.getDBColumn("EffectiveDate").getFullyQualifiedColumnName();
        String pendingStatusCol   = EDITTRX_DBTABLE.getDBColumn("PendingStatus").getFullyQualifiedColumnName();
        String transactionTypeCTCol   = EDITTRX_DBTABLE.getDBColumn("TransactionTypeCT").getFullyQualifiedColumnName();

        String clientSetupPKCol   = CLIENT_SETUP_DBTABLE.getDBColumn("ClientSetupPK").getFullyQualifiedColumnName();
        String contractSetupFKCol = CLIENT_SETUP_DBTABLE.getDBColumn("ContractSetupFK").getFullyQualifiedColumnName();

        String contractSetupPKCol = CONTRACT_SETUP_DBTABLE.getDBColumn("ContractSetupPK").getFullyQualifiedColumnName();
        String segmentFKCol       = CONTRACT_SETUP_DBTABLE.getDBColumn("SegmentFK").getFullyQualifiedColumnName();

        String sql = "SELECT " + EDITTRX_TABLENAME + ".* FROM " + EDITTRX_TABLENAME + ", " + CLIENT_SETUP_TABLENAME + ", " + CONTRACT_SETUP_TABLENAME +
                     " WHERE " + clientSetupFKCol + " = " + clientSetupPKCol +
                     " AND " + contractSetupFKCol + " = " + contractSetupPKCol +
                     " AND " + segmentFKCol + " = " + segmentPK +
                     " AND " + effectiveDateCol + " >= ?" +
                     " AND " + pendingStatusCol + " IN ('H', 'L')" +
                     " AND " + transactionTypeCTCol + " <> '" + transactionType +"'";

        Connection conn = null;

        PreparedStatement ps = null;

        try
        {
            conn = ConnectionFactory.getSingleton().getConnection(POOLNAME);

            ps = conn.prepareStatement(sql);

            ps.setDate(1, DBUtil.convertStringToDate(effectiveDate));

            editTrxVOs = (EDITTrxVO[]) executeQuery(EDITTrxVO.class,
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

                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.

                throw new RuntimeException(e);
            }
        }

        return editTrxVOs;
    }
    
    public EDITTrxVO[] findBySegmentPK_AND_EffectiveDateGT_AND_NotTransactionType_IncludeTs(long segmentPK, String effectiveDate, String transactionType) throws Exception
    {
        EDITTrxVO[] editTrxVOs = null;

        String clientSetupFKCol   = EDITTRX_DBTABLE.getDBColumn("ClientSetupFK").getFullyQualifiedColumnName();
        String effectiveDateCol   = EDITTRX_DBTABLE.getDBColumn("EffectiveDate").getFullyQualifiedColumnName();
        String pendingStatusCol   = EDITTRX_DBTABLE.getDBColumn("PendingStatus").getFullyQualifiedColumnName();
        String transactionTypeCTCol   = EDITTRX_DBTABLE.getDBColumn("TransactionTypeCT").getFullyQualifiedColumnName();

        String clientSetupPKCol   = CLIENT_SETUP_DBTABLE.getDBColumn("ClientSetupPK").getFullyQualifiedColumnName();
        String contractSetupFKCol = CLIENT_SETUP_DBTABLE.getDBColumn("ContractSetupFK").getFullyQualifiedColumnName();

        String contractSetupPKCol = CONTRACT_SETUP_DBTABLE.getDBColumn("ContractSetupPK").getFullyQualifiedColumnName();
        String segmentFKCol       = CONTRACT_SETUP_DBTABLE.getDBColumn("SegmentFK").getFullyQualifiedColumnName();

        String sql = "SELECT " + EDITTRX_TABLENAME + ".* FROM " + EDITTRX_TABLENAME + ", " + CLIENT_SETUP_TABLENAME + ", " + CONTRACT_SETUP_TABLENAME +
                     " WHERE " + clientSetupFKCol + " = " + clientSetupPKCol +
                     " AND " + contractSetupFKCol + " = " + contractSetupPKCol +
                     " AND " + segmentFKCol + " = " + segmentPK +
                     " AND " + effectiveDateCol + " >= ?" +
                     " AND " + pendingStatusCol + " IN ('H', 'L', 'T')" +
                     " AND " + transactionTypeCTCol + " <> '" + transactionType +"'";

        Connection conn = null;

        PreparedStatement ps = null;

        try
        {
            conn = ConnectionFactory.getSingleton().getConnection(POOLNAME);

            ps = conn.prepareStatement(sql);

            ps.setDate(1, DBUtil.convertStringToDate(effectiveDate));

            editTrxVOs = (EDITTrxVO[]) executeQuery(EDITTrxVO.class,
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

                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.

                throw new RuntimeException(e);
            }
        }

        return editTrxVOs;
    }
    
    /*public String getMinEffectiveDate(long segmentPK, List<Long> pks) throws Exception
    {
        String[] minEffectiveDate = null;

        String editTrxPKCol   = EDITTRX_DBTABLE.getDBColumn("EDITTrxPK").getFullyQualifiedColumnName();
        String clientSetupFKCol   = EDITTRX_DBTABLE.getDBColumn("ClientSetupFK").getFullyQualifiedColumnName();
        String effectiveDateCol   = EDITTRX_DBTABLE.getDBColumn("EffectiveDate").getFullyQualifiedColumnName();

        String clientSetupPKCol   = CLIENT_SETUP_DBTABLE.getDBColumn("ClientSetupPK").getFullyQualifiedColumnName();
        String contractSetupFKCol = CLIENT_SETUP_DBTABLE.getDBColumn("ContractSetupFK").getFullyQualifiedColumnName();

        String contractSetupPKCol = CONTRACT_SETUP_DBTABLE.getDBColumn("ContractSetupPK").getFullyQualifiedColumnName();
        String segmentFKCol       = CONTRACT_SETUP_DBTABLE.getDBColumn("SegmentFK").getFullyQualifiedColumnName();

        String sql = "SELECT MIN(" + effectiveDateCol + ") FROM " + EDITTRX_TABLENAME + ", " + CLIENT_SETUP_TABLENAME + ", " + CONTRACT_SETUP_TABLENAME +
                     " WHERE " + clientSetupFKCol + " = " + clientSetupPKCol +
                     " AND " + contractSetupFKCol + " = " + contractSetupPKCol +
                     " AND " + segmentFKCol + " = ?" +
                     " AND " + editTrxPKCol + " IN (?)";

        Connection conn = null;

        PreparedStatement ps = null;

        try
        {
            conn = ConnectionFactory.getSingleton().getConnection(POOLNAME);

            ps = conn.prepareStatement(sql);

            ps.setLong(0, segmentPK);
            
            Array array = conn.createArrayOf("VARCHAR", pks.toArray());
            ps.setArray(1, array);

            minEffectiveDate = (String[]) executeQuery(String.class,
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

                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.

                throw new RuntimeException(e);
            }
        }

        if (minEffectiveDate != null && minEffectiveDate.length > 0) {
        	return minEffectiveDate[0];
        } else {
        	return null;
        }
    }*/
        
    public Long getMinEditTrxPK(long segmentPK, List<Long> pks) throws Exception
    {
    	EDITTrxVO[] minPK = null;

        String editTrxPKCol   = EDITTRX_DBTABLE.getDBColumn("EDITTrxPK").getFullyQualifiedColumnName();
        String clientSetupFKCol   = EDITTRX_DBTABLE.getDBColumn("ClientSetupFK").getFullyQualifiedColumnName();
        String effectiveDateCol   = EDITTRX_DBTABLE.getDBColumn("EffectiveDate").getFullyQualifiedColumnName();
        String transactionTypeCTCol   = EDITTRX_DBTABLE.getDBColumn("TransactionTypeCT").getFullyQualifiedColumnName();
        String sequenceNumberCol   = EDITTRX_DBTABLE.getDBColumn("SequenceNumber").getFullyQualifiedColumnName();

        String clientSetupPKCol   = CLIENT_SETUP_DBTABLE.getDBColumn("ClientSetupPK").getFullyQualifiedColumnName();
        String contractSetupFKCol = CLIENT_SETUP_DBTABLE.getDBColumn("ContractSetupFK").getFullyQualifiedColumnName();

        String contractSetupPKCol = CONTRACT_SETUP_DBTABLE.getDBColumn("ContractSetupPK").getFullyQualifiedColumnName();
        String segmentFKCol       = CONTRACT_SETUP_DBTABLE.getDBColumn("SegmentFK").getFullyQualifiedColumnName();

        String transactionPriorityTransactionTypeCol = TRANSACTION_PRIORITY_DBTABLE.getDBColumn("TransactionTypeCT").getFullyQualifiedColumnName();
        String priorityCol = TRANSACTION_PRIORITY_DBTABLE.getDBColumn("Priority").getFullyQualifiedColumnName();

        String pkIn = "";

        for (int i = 0; i < pks.size(); i++) {
            if (i < pks.size() - 1)  {
            	pkIn += pks.get(i) + ", ";
            } else {
            	pkIn += pks.get(i) + ")";
            }
        }
        
        String sql = "SELECT TOP 1 " +  EDITTRX_TABLENAME + ".* FROM " + EDITTRX_TABLENAME + ", " + CLIENT_SETUP_TABLENAME + ", " + CONTRACT_SETUP_TABLENAME +
        			", " + TRANSACTION_PRIORITY_TABLENAME +
                     " WHERE " + clientSetupFKCol + " = " + clientSetupPKCol +
                     " AND " + contractSetupFKCol + " = " + contractSetupPKCol + 
                     " AND " + transactionTypeCTCol + " = " + transactionPriorityTransactionTypeCol +
                     " AND " + segmentFKCol + " = ?" +
                     " AND " + editTrxPKCol + " IN (" + pkIn +
                     " ORDER BY " + effectiveDateCol + " ASC, " + priorityCol + " ASC, " + sequenceNumberCol + " ASC";
    
        Connection conn = null;

        PreparedStatement ps = null;

        try
        {
            conn = ConnectionFactory.getSingleton().getConnection(POOLNAME);

            ps = conn.prepareStatement(sql); 

            ps.setLong(1, segmentPK); 
            
            minPK = (EDITTrxVO[]) executeQuery(EDITTrxVO.class,
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

        if (minPK != null && minPK.length > 0) {
        	return minPK[0].getEDITTrxPK();
        } else {
        	return null;
        }
    }
    
    public EDITTrxVO[] getAllTrxHistory_ByReverseOrder(String contractNumber) throws Exception
    {
    	EDITTrxVO[] editTrxVOs = null;

        String statusCol   = EDITTRX_DBTABLE.getDBColumn("Status").getFullyQualifiedColumnName();
        String clientSetupFKCol   = EDITTRX_DBTABLE.getDBColumn("ClientSetupFK").getFullyQualifiedColumnName();
        String effectiveDateCol   = EDITTRX_DBTABLE.getDBColumn("EffectiveDate").getFullyQualifiedColumnName();
        String transactionTypeCTCol   = EDITTRX_DBTABLE.getDBColumn("TransactionTypeCT").getFullyQualifiedColumnName();
        String sequenceNumberCol   = EDITTRX_DBTABLE.getDBColumn("SequenceNumber").getFullyQualifiedColumnName();
        String pendingStatusCol   = EDITTRX_DBTABLE.getDBColumn("PendingStatus").getFullyQualifiedColumnName();

        String clientSetupPKCol   = CLIENT_SETUP_DBTABLE.getDBColumn("ClientSetupPK").getFullyQualifiedColumnName();
        String contractSetupFKCol = CLIENT_SETUP_DBTABLE.getDBColumn("ContractSetupFK").getFullyQualifiedColumnName();

        String contractSetupPKCol = CONTRACT_SETUP_DBTABLE.getDBColumn("ContractSetupPK").getFullyQualifiedColumnName();
        String segmentFKCol       = CONTRACT_SETUP_DBTABLE.getDBColumn("SegmentFK").getFullyQualifiedColumnName();

        String transactionPriorityTransactionTypeCol = TRANSACTION_PRIORITY_DBTABLE.getDBColumn("TransactionTypeCT").getFullyQualifiedColumnName();
        String priorityCol = TRANSACTION_PRIORITY_DBTABLE.getDBColumn("Priority").getFullyQualifiedColumnName();
        
        String segmentPKCol = SEGMENT_DBTABLE.getDBColumn("SegmentPK").getFullyQualifiedColumnName();
        String contractNumberCol  = SEGMENT_DBTABLE.getDBColumn("ContractNumber").getFullyQualifiedColumnName();

        String sql = "SELECT " +  EDITTRX_TABLENAME + ".* FROM " + EDITTRX_TABLENAME + ", " + CLIENT_SETUP_TABLENAME + ", " + CONTRACT_SETUP_TABLENAME +
        			", " + TRANSACTION_PRIORITY_TABLENAME + ", " + SEGMENT_TABLENAME +
                     " WHERE " + clientSetupFKCol + " = " + clientSetupPKCol +
                     " AND " + contractSetupFKCol + " = " + contractSetupPKCol + 
                     " AND " + transactionTypeCTCol + " = " + transactionPriorityTransactionTypeCol +
                     " AND " + segmentFKCol + " = " + segmentPKCol + 
                     " AND " + contractNumberCol + " = ?" + 
                     " AND " + statusCol + " IN ('N', 'A')" +
                     " AND " + pendingStatusCol + " IN ('H', 'T')" +
                     " ORDER BY " + effectiveDateCol + " DESC, " + priorityCol + " DESC, " + sequenceNumberCol + " DESC";
    
        Connection conn = null;

        PreparedStatement ps = null;

        try
        {
            conn = ConnectionFactory.getSingleton().getConnection(POOLNAME);

            ps = conn.prepareStatement(sql); 

            // ps.setLong(1, segmentPK); 
            ps.setString(1, contractNumber);

            editTrxVOs = (EDITTrxVO[]) executeQuery(EDITTrxVO.class,
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

        return editTrxVOs;
    }
    
    public EDITTrxVO[] getAllTrxHistory_EffectiveDateGTE_ByReverseOrder_ForIssueReversal(String contractNumber, String startDate) throws Exception
    {
    	EDITTrxVO[] editTrxVOs = null;

        String statusCol   = EDITTRX_DBTABLE.getDBColumn("Status").getFullyQualifiedColumnName();
        String clientSetupFKCol   = EDITTRX_DBTABLE.getDBColumn("ClientSetupFK").getFullyQualifiedColumnName();
        String effectiveDateCol   = EDITTRX_DBTABLE.getDBColumn("EffectiveDate").getFullyQualifiedColumnName();
        String transactionTypeCTCol   = EDITTRX_DBTABLE.getDBColumn("TransactionTypeCT").getFullyQualifiedColumnName();
        String sequenceNumberCol   = EDITTRX_DBTABLE.getDBColumn("SequenceNumber").getFullyQualifiedColumnName();
        String pendingStatusCol   = EDITTRX_DBTABLE.getDBColumn("PendingStatus").getFullyQualifiedColumnName();

        String clientSetupPKCol   = CLIENT_SETUP_DBTABLE.getDBColumn("ClientSetupPK").getFullyQualifiedColumnName();
        String contractSetupFKCol = CLIENT_SETUP_DBTABLE.getDBColumn("ContractSetupFK").getFullyQualifiedColumnName();

        String contractSetupPKCol = CONTRACT_SETUP_DBTABLE.getDBColumn("ContractSetupPK").getFullyQualifiedColumnName();
        String segmentFKCol       = CONTRACT_SETUP_DBTABLE.getDBColumn("SegmentFK").getFullyQualifiedColumnName();

        String transactionPriorityTransactionTypeCol = TRANSACTION_PRIORITY_DBTABLE.getDBColumn("TransactionTypeCT").getFullyQualifiedColumnName();
        String priorityCol = TRANSACTION_PRIORITY_DBTABLE.getDBColumn("Priority").getFullyQualifiedColumnName();
        
        String segmentPKCol = SEGMENT_DBTABLE.getDBColumn("SegmentPK").getFullyQualifiedColumnName();
        String contractNumberCol  = SEGMENT_DBTABLE.getDBColumn("ContractNumber").getFullyQualifiedColumnName();

        String sql = "SELECT " +  EDITTRX_TABLENAME + ".* FROM " + EDITTRX_TABLENAME + ", " + CLIENT_SETUP_TABLENAME + ", " + CONTRACT_SETUP_TABLENAME +
        			", " + TRANSACTION_PRIORITY_TABLENAME + ", " + SEGMENT_TABLENAME +
                     " WHERE " + clientSetupFKCol + " = " + clientSetupPKCol +
                     " AND " + contractSetupFKCol + " = " + contractSetupPKCol + 
                     " AND " + transactionTypeCTCol + " = " + transactionPriorityTransactionTypeCol +
                     " AND " + segmentFKCol + " = " + segmentPKCol +
                     " AND " + contractNumberCol + " = ?" +
                     " AND " + effectiveDateCol + " >= ?" +
                     " AND " + statusCol + " IN ('N', 'A')" +
                     " AND (" + pendingStatusCol + " = 'H'" +
               			" OR " + pendingStatusCol + " = 'T')" +
                     " ORDER BY " + effectiveDateCol + " DESC, " + priorityCol + " DESC, " + sequenceNumberCol + " DESC";
    
        Connection conn = null;

        PreparedStatement ps = null;

        try
        {
            conn = ConnectionFactory.getSingleton().getConnection(POOLNAME);

            ps = conn.prepareStatement(sql); 

            // ps.setLong(1, segmentPK); 
            ps.setString(1, contractNumber);
            ps.setDate(2, DBUtil.convertStringToDate(startDate));
            
            editTrxVOs = (EDITTrxVO[]) executeQuery(EDITTrxVO.class,
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

        return editTrxVOs;
    }
    

    public EDITTrxVO[] getAllTrxHistory_EffectiveDateGTE_ByReverseOrder_ForNonIssueReversal(String contractNumber, String startDate) throws Exception
    {
    	EDITTrxVO[] editTrxVOs = null;

        String statusCol   = EDITTRX_DBTABLE.getDBColumn("Status").getFullyQualifiedColumnName();
        String clientSetupFKCol   = EDITTRX_DBTABLE.getDBColumn("ClientSetupFK").getFullyQualifiedColumnName();
        String effectiveDateCol   = EDITTRX_DBTABLE.getDBColumn("EffectiveDate").getFullyQualifiedColumnName();
        String transactionTypeCTCol   = EDITTRX_DBTABLE.getDBColumn("TransactionTypeCT").getFullyQualifiedColumnName();
        String sequenceNumberCol   = EDITTRX_DBTABLE.getDBColumn("SequenceNumber").getFullyQualifiedColumnName();
        String pendingStatusCol   = EDITTRX_DBTABLE.getDBColumn("PendingStatus").getFullyQualifiedColumnName();

        String clientSetupPKCol   = CLIENT_SETUP_DBTABLE.getDBColumn("ClientSetupPK").getFullyQualifiedColumnName();
        String contractSetupFKCol = CLIENT_SETUP_DBTABLE.getDBColumn("ContractSetupFK").getFullyQualifiedColumnName();

        String contractSetupPKCol = CONTRACT_SETUP_DBTABLE.getDBColumn("ContractSetupPK").getFullyQualifiedColumnName();
        String segmentFKCol       = CONTRACT_SETUP_DBTABLE.getDBColumn("SegmentFK").getFullyQualifiedColumnName();

        String transactionPriorityTransactionTypeCol = TRANSACTION_PRIORITY_DBTABLE.getDBColumn("TransactionTypeCT").getFullyQualifiedColumnName();
        String priorityCol = TRANSACTION_PRIORITY_DBTABLE.getDBColumn("Priority").getFullyQualifiedColumnName();
        
        String segmentPKCol = SEGMENT_DBTABLE.getDBColumn("SegmentPK").getFullyQualifiedColumnName();
        String contractNumberCol  = SEGMENT_DBTABLE.getDBColumn("ContractNumber").getFullyQualifiedColumnName();

        String sql = "SELECT " +  EDITTRX_TABLENAME + ".* FROM " + EDITTRX_TABLENAME + ", " + CLIENT_SETUP_TABLENAME + ", " + CONTRACT_SETUP_TABLENAME +
        			", " + TRANSACTION_PRIORITY_TABLENAME + ", " + SEGMENT_TABLENAME +
                     " WHERE " + clientSetupFKCol + " = " + clientSetupPKCol +
                     " AND " + contractSetupFKCol + " = " + contractSetupPKCol + 
                     " AND " + transactionTypeCTCol + " = " + transactionPriorityTransactionTypeCol +
                     " AND " + segmentFKCol + " = " + segmentPKCol +
                     " AND " + contractNumberCol + " = ?" + 
                     " AND " + effectiveDateCol + " >= ?" +
                     " AND " + statusCol + " IN ('N', 'A')" +
                     " AND ((" + pendingStatusCol + " = 'H'" +
                     	" AND " + transactionTypeCTCol + " NOT IN ('ADC', 'BC', 'CC', 'FI', 'LB', 'LC', 'LP', 'MA', 'MI', 'MV',  'PE', 'ST')) " +
               			" OR " + pendingStatusCol + " = 'T')" +
                     " ORDER BY " + effectiveDateCol + " DESC, " + priorityCol + " DESC, " + sequenceNumberCol + " DESC";
    
        Connection conn = null;

        PreparedStatement ps = null;

        try
        {
            conn = ConnectionFactory.getSingleton().getConnection(POOLNAME);

            ps = conn.prepareStatement(sql); 

            // ps.setLong(1, segmentPK); 
            ps.setString(1, contractNumber);
            ps.setDate(2, DBUtil.convertStringToDate(startDate));
            
            editTrxVOs = (EDITTrxVO[]) executeQuery(EDITTrxVO.class,
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

        return editTrxVOs;
    }
    
    public EDITTrxVO[] getAllTrxHistory_TrxGTE_ByReverseOrder(String contractNumber, long earliestKey, String effectiveDate, int transactionPriority,
    		int sequence, List<Long> pksToReverse) throws Exception
    {
    	EDITTrxVO[] editTrxVOs = null;

        String editTrxPKCol   = EDITTRX_DBTABLE.getDBColumn("EDITTrxPK").getFullyQualifiedColumnName();
        String statusCol   = EDITTRX_DBTABLE.getDBColumn("Status").getFullyQualifiedColumnName();
        String clientSetupFKCol   = EDITTRX_DBTABLE.getDBColumn("ClientSetupFK").getFullyQualifiedColumnName();
        String effectiveDateCol   = EDITTRX_DBTABLE.getDBColumn("EffectiveDate").getFullyQualifiedColumnName();
        String transactionTypeCTCol   = EDITTRX_DBTABLE.getDBColumn("TransactionTypeCT").getFullyQualifiedColumnName();
        String sequenceNumberCol   = EDITTRX_DBTABLE.getDBColumn("SequenceNumber").getFullyQualifiedColumnName();
        String pendingStatusCol   = EDITTRX_DBTABLE.getDBColumn("PendingStatus").getFullyQualifiedColumnName();
        String terminationTrxFKCol   = EDITTRX_DBTABLE.getDBColumn("TerminationTrxFK").getFullyQualifiedColumnName();
        String originatingTrxFKCol   = EDITTRX_DBTABLE.getDBColumn("OriginatingTrxFK").getFullyQualifiedColumnName();

        String clientSetupPKCol   = CLIENT_SETUP_DBTABLE.getDBColumn("ClientSetupPK").getFullyQualifiedColumnName();
        String contractSetupFKCol = CLIENT_SETUP_DBTABLE.getDBColumn("ContractSetupFK").getFullyQualifiedColumnName();

        String contractSetupPKCol = CONTRACT_SETUP_DBTABLE.getDBColumn("ContractSetupPK").getFullyQualifiedColumnName();
        String segmentFKCol       = CONTRACT_SETUP_DBTABLE.getDBColumn("SegmentFK").getFullyQualifiedColumnName();

        String transactionPriorityTransactionTypeCol = TRANSACTION_PRIORITY_DBTABLE.getDBColumn("TransactionTypeCT").getFullyQualifiedColumnName();
        String priorityCol = TRANSACTION_PRIORITY_DBTABLE.getDBColumn("Priority").getFullyQualifiedColumnName();
        
        String contractNumberCol  = SEGMENT_DBTABLE.getDBColumn("ContractNumber").getFullyQualifiedColumnName();
        String segmentPKCol = SEGMENT_DBTABLE.getDBColumn("SegmentPK").getFullyQualifiedColumnName();

        String pkIn = "";
        for (int i = 0; i < pksToReverse.size(); i++) {
            if (i < pksToReverse.size() - 1)  {
            	pkIn += pksToReverse.get(i) + ", ";
            } else {
            	pkIn += pksToReverse.get(i) + ")";
            }
        }
        
        String sql = "SELECT " +  EDITTRX_TABLENAME + ".* FROM " + EDITTRX_TABLENAME + ", " + CLIENT_SETUP_TABLENAME + ", " + CONTRACT_SETUP_TABLENAME +
        			", " + TRANSACTION_PRIORITY_TABLENAME + ", " + SEGMENT_TABLENAME +
                     " WHERE " + clientSetupFKCol + " = " + clientSetupPKCol +
                     " AND " + contractSetupFKCol + " = " + contractSetupPKCol + 
                     " AND " + transactionTypeCTCol + " = " + transactionPriorityTransactionTypeCol +
                     " AND " + segmentFKCol + " = " + segmentPKCol +
                     " AND " + contractNumberCol + " = ?" +
                     " AND (" + editTrxPKCol + " = ? " +
	                     " OR (" + effectiveDateCol + " > ? OR (" +
	                     		effectiveDateCol + " = ? AND " + priorityCol + " > ? ) OR (" + 
	                     		effectiveDateCol + " = ? AND " + priorityCol + " = ? AND " + sequenceNumberCol + " > ? )))" +
                     " AND " + statusCol + " IN ('N', 'A')" +
                     " AND " + transactionTypeCTCol + "<> 'ADC'" +
                     " AND (" + pendingStatusCol + " = 'H'" +
                     	" OR (" + pendingStatusCol + " = 'T'" +
                     		" AND " + terminationTrxFKCol + " IN (" + pkIn + ")" + 
                     	" OR (" + pendingStatusCol + " = 'P'" +
                     		" AND " + originatingTrxFKCol + " IN (" + pkIn + "))" + 
                     " ORDER BY " + effectiveDateCol + " DESC, " + priorityCol + " DESC, " + sequenceNumberCol + " DESC";
    
        Connection conn = null;

        PreparedStatement ps = null;

        try
        {
            conn = ConnectionFactory.getSingleton().getConnection(POOLNAME);

            ps = conn.prepareStatement(sql); 

            ps.setString(1, contractNumber);
            ps.setLong(2, earliestKey);
            ps.setDate(3, DBUtil.convertStringToDate(effectiveDate));
            ps.setDate(4, DBUtil.convertStringToDate(effectiveDate));
            ps.setInt(5, transactionPriority);
            ps.setDate(6, DBUtil.convertStringToDate(effectiveDate));
            ps.setInt(7, transactionPriority);
            ps.setInt(8, sequence);

            editTrxVOs = (EDITTrxVO[]) executeQuery(EDITTrxVO.class,
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

        return editTrxVOs;
    }
    
    public EDITTrxVO findEarliestBCWithCPHInStrategyChain(String contractNumber, String effectiveDate, int transactionPriority,
    		int sequence) throws Exception
    {
    	EDITTrxVO editTrxVO = null;

        String editTrxPKCol   = EDITTRX_DBTABLE.getDBColumn("EDITTrxPK").getFullyQualifiedColumnName();
        String statusCol   = EDITTRX_DBTABLE.getDBColumn("Status").getFullyQualifiedColumnName();
        String clientSetupFKCol   = EDITTRX_DBTABLE.getDBColumn("ClientSetupFK").getFullyQualifiedColumnName();
        String effectiveDateCol   = EDITTRX_DBTABLE.getDBColumn("EffectiveDate").getFullyQualifiedColumnName();
        String transactionTypeCTCol   = EDITTRX_DBTABLE.getDBColumn("TransactionTypeCT").getFullyQualifiedColumnName();
        String sequenceNumberCol   = EDITTRX_DBTABLE.getDBColumn("SequenceNumber").getFullyQualifiedColumnName();
        String pendingStatusCol   = EDITTRX_DBTABLE.getDBColumn("PendingStatus").getFullyQualifiedColumnName();
        // String terminationTrxFKCol   = EDITTRX_DBTABLE.getDBColumn("TerminationTrxFK").getFullyQualifiedColumnName();
        // String originatingTrxFKCol   = EDITTRX_DBTABLE.getDBColumn("OriginatingTrxFK").getFullyQualifiedColumnName();

        String clientSetupPKCol   = CLIENT_SETUP_DBTABLE.getDBColumn("ClientSetupPK").getFullyQualifiedColumnName();
        String contractSetupFKCol = CLIENT_SETUP_DBTABLE.getDBColumn("ContractSetupFK").getFullyQualifiedColumnName();

        String contractSetupPKCol = CONTRACT_SETUP_DBTABLE.getDBColumn("ContractSetupPK").getFullyQualifiedColumnName();
        String segmentFKCol       = CONTRACT_SETUP_DBTABLE.getDBColumn("SegmentFK").getFullyQualifiedColumnName();

        String transactionPriorityTransactionTypeCol = TRANSACTION_PRIORITY_DBTABLE.getDBColumn("TransactionTypeCT").getFullyQualifiedColumnName();
        String priorityCol = TRANSACTION_PRIORITY_DBTABLE.getDBColumn("Priority").getFullyQualifiedColumnName();
        
        String contractNumberCol  = SEGMENT_DBTABLE.getDBColumn("ContractNumber").getFullyQualifiedColumnName();
        String segmentPKCol = SEGMENT_DBTABLE.getDBColumn("SegmentPK").getFullyQualifiedColumnName();
        
        String editTrxHistoryPKCol   = EDITTRXHISTORY_DBTABLE.getDBColumn("EDITTrxHistoryPK").getFullyQualifiedColumnName();
        String editTrxFKCol   = EDITTRXHISTORY_DBTABLE.getDBColumn("EDITTrxFK").getFullyQualifiedColumnName();

        String editTrxHistoryFKCol   = COMMISSIONABLEPREMIUMHISTORY_DBTABLE.getDBColumn("EDITTrxHistoryFK").getFullyQualifiedColumnName();

        String sql = "SELECT " +  EDITTRX_TABLENAME + ".* FROM " + EDITTRX_TABLENAME + ", " + CLIENT_SETUP_TABLENAME + ", " + CONTRACT_SETUP_TABLENAME +
        			", " + TRANSACTION_PRIORITY_TABLENAME + ", " + SEGMENT_TABLENAME + ", " + EDITTRXHISTORY_TABLENAME + ", " + COMMISSIONABLEPREMIUMHISTORY_TABLENAME +
                     " WHERE " + clientSetupFKCol + " = " + clientSetupPKCol +
                     " AND " + contractSetupFKCol + " = " + contractSetupPKCol + 
                     " AND " + transactionTypeCTCol + " = " + transactionPriorityTransactionTypeCol +
                     " AND " + segmentFKCol + " = " + segmentPKCol +
                     " AND " + editTrxPKCol + " = " + editTrxFKCol +
                     " AND " + editTrxHistoryPKCol + " = " + editTrxHistoryFKCol +
                     " AND " + contractNumberCol + " = ?" +
                     " AND (" + effectiveDateCol + " > ? OR (" +
                     		effectiveDateCol + " = ? AND " + priorityCol + " > ? ) OR (" + 
                     		effectiveDateCol + " = ? AND " + priorityCol + " = ? AND " + sequenceNumberCol + " > ? ))" +
                     " AND " + statusCol + " IN ('N', 'A')" +
                     " AND " + transactionTypeCTCol + "= 'BC'" +
                     " AND " + pendingStatusCol + " = 'H'" +
                     	/*" OR (" + pendingStatusCol + " = 'T'" +
                     		" AND " + terminationTrxFKCol + " IN (" + pkIn + ")" + 
                     	" OR (" + pendingStatusCol + " = 'P'" +
                     		" AND " + originatingTrxFKCol + " IN (" + pkIn + "))" + */
                     " ORDER BY " + effectiveDateCol + " ASC, " + priorityCol + " ASC, " + sequenceNumberCol + " ASC";
    
        Connection conn = null;

        PreparedStatement ps = null;

        try
        {
            conn = ConnectionFactory.getSingleton().getConnection(POOLNAME);

            ps = conn.prepareStatement(sql); 

            ps.setString(1, contractNumber);
            ps.setDate(2, DBUtil.convertStringToDate(effectiveDate));
            ps.setDate(3, DBUtil.convertStringToDate(effectiveDate));
            ps.setInt(4, transactionPriority);
            ps.setDate(5, DBUtil.convertStringToDate(effectiveDate));
            ps.setInt(6, transactionPriority);
            ps.setInt(7, sequence);

        	EDITTrxVO[] editTrxVOs = (EDITTrxVO[]) executeQuery(EDITTrxVO.class,
                                                     ps,
                                                      POOLNAME,
                                                       false,
                                                        null);
        	
            if (editTrxVOs != null && editTrxVOs.length > 0) {
            	editTrxVO = editTrxVOs[0];
            }
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

        return editTrxVO;
    }
    
    public EDITTrxVO findLatestPYWithPriorPaidToDateBeforeDate(String contractNumber, String date) throws Exception
    {
    	EDITTrxVO editTrxVO = null;

        String editTrxPKCol   = EDITTRX_DBTABLE.getDBColumn("EDITTrxPK").getFullyQualifiedColumnName();
        String statusCol   = EDITTRX_DBTABLE.getDBColumn("Status").getFullyQualifiedColumnName();
        String clientSetupFKCol   = EDITTRX_DBTABLE.getDBColumn("ClientSetupFK").getFullyQualifiedColumnName();
        String effectiveDateCol   = EDITTRX_DBTABLE.getDBColumn("EffectiveDate").getFullyQualifiedColumnName();
        String transactionTypeCTCol   = EDITTRX_DBTABLE.getDBColumn("TransactionTypeCT").getFullyQualifiedColumnName();
        // String sequenceNumberCol   = EDITTRX_DBTABLE.getDBColumn("SequenceNumber").getFullyQualifiedColumnName();
        String pendingStatusCol   = EDITTRX_DBTABLE.getDBColumn("PendingStatus").getFullyQualifiedColumnName();
        // String terminationTrxFKCol   = EDITTRX_DBTABLE.getDBColumn("TerminationTrxFK").getFullyQualifiedColumnName();
        // String originatingTrxFKCol   = EDITTRX_DBTABLE.getDBColumn("OriginatingTrxFK").getFullyQualifiedColumnName();

        String clientSetupPKCol   = CLIENT_SETUP_DBTABLE.getDBColumn("ClientSetupPK").getFullyQualifiedColumnName();
        String contractSetupFKCol = CLIENT_SETUP_DBTABLE.getDBColumn("ContractSetupFK").getFullyQualifiedColumnName();

        String contractSetupPKCol = CONTRACT_SETUP_DBTABLE.getDBColumn("ContractSetupPK").getFullyQualifiedColumnName();
        String segmentFKCol       = CONTRACT_SETUP_DBTABLE.getDBColumn("SegmentFK").getFullyQualifiedColumnName();

        //String transactionPriorityTransactionTypeCol = TRANSACTION_PRIORITY_DBTABLE.getDBColumn("TransactionTypeCT").getFullyQualifiedColumnName();
        //String priorityCol = TRANSACTION_PRIORITY_DBTABLE.getDBColumn("Priority").getFullyQualifiedColumnName();
        
        String contractNumberCol  = SEGMENT_DBTABLE.getDBColumn("ContractNumber").getFullyQualifiedColumnName();
        String segmentPKCol = SEGMENT_DBTABLE.getDBColumn("SegmentPK").getFullyQualifiedColumnName();
        
        String editTrxHistoryPKCol   = EDITTRXHISTORY_DBTABLE.getDBColumn("EDITTrxHistoryPK").getFullyQualifiedColumnName();
        String editTrxFKCol   = EDITTRXHISTORY_DBTABLE.getDBColumn("EDITTrxFK").getFullyQualifiedColumnName();

        String editTrxHistoryFKCol = SEGMENTHISTORY_DBTABLE.getDBColumn("EDITTrxHistoryFK").getFullyQualifiedColumnName();
        String priorPaidToDateCol = SEGMENTHISTORY_DBTABLE.getDBColumn("PriorPaidToDate").getFullyQualifiedColumnName();

        String sql = "SELECT " +  EDITTRX_TABLENAME + ".* FROM " + EDITTRX_TABLENAME + ", " + CLIENT_SETUP_TABLENAME + ", " + CONTRACT_SETUP_TABLENAME +
        			", " + SEGMENT_TABLENAME + ", " + EDITTRXHISTORY_TABLENAME + ", " + SEGMENTHISTORY_TABLENAME +
                     " WHERE " + clientSetupFKCol + " = " + clientSetupPKCol +
                     " AND " + contractSetupFKCol + " = " + contractSetupPKCol + 
                     " AND " + segmentFKCol + " = " + segmentPKCol +
                     " AND " + editTrxPKCol + " = " + editTrxFKCol +
                     " AND " + editTrxHistoryPKCol + " = " + editTrxHistoryFKCol +
                     " AND " + contractNumberCol + " = ?" +
                     " AND " + priorPaidToDateCol + " <= ? " +
                     " AND " + statusCol + " IN ('N', 'A')" +
                     " AND " + transactionTypeCTCol + " = 'PY'" +
                     " AND " + pendingStatusCol + " = 'H'" +
                     " ORDER BY " + effectiveDateCol + " DESC, " + editTrxPKCol + " DESC ";
    
        Connection conn = null;

        PreparedStatement ps = null;

        try
        {
            conn = ConnectionFactory.getSingleton().getConnection(POOLNAME);

            ps = conn.prepareStatement(sql); 

            ps.setString(1, contractNumber);
            ps.setDate(2, DBUtil.convertStringToDate(date));

        	EDITTrxVO[] editTrxVOs = (EDITTrxVO[]) executeQuery(EDITTrxVO.class,
                                                     ps,
                                                      POOLNAME,
                                                       false,
                                                        null);
        	
            if (editTrxVOs != null && editTrxVOs.length > 0) {
            	editTrxVO = editTrxVOs[0];
            }
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

        return editTrxVO;
    }
    
    public EDITTrxVO[] getAllTrx_TrxGTE_ByReverseOrder(long segmentPK, long editTrxPK) throws Exception
    {
    	EDITTrxVO[] editTrxVOs = null;

        String editTrxPKCol   = EDITTRX_DBTABLE.getDBColumn("EDITTrxPK").getFullyQualifiedColumnName();
        String statusCol   = EDITTRX_DBTABLE.getDBColumn("Status").getFullyQualifiedColumnName();
        String clientSetupFKCol   = EDITTRX_DBTABLE.getDBColumn("ClientSetupFK").getFullyQualifiedColumnName();
        String effectiveDateCol   = EDITTRX_DBTABLE.getDBColumn("EffectiveDate").getFullyQualifiedColumnName();
        String transactionTypeCTCol   = EDITTRX_DBTABLE.getDBColumn("TransactionTypeCT").getFullyQualifiedColumnName();
        String sequenceNumberCol   = EDITTRX_DBTABLE.getDBColumn("SequenceNumber").getFullyQualifiedColumnName();
        String pendingStatusCol   = EDITTRX_DBTABLE.getDBColumn("PendingStatus").getFullyQualifiedColumnName();

        String clientSetupPKCol   = CLIENT_SETUP_DBTABLE.getDBColumn("ClientSetupPK").getFullyQualifiedColumnName();
        String contractSetupFKCol = CLIENT_SETUP_DBTABLE.getDBColumn("ContractSetupFK").getFullyQualifiedColumnName();

        String contractSetupPKCol = CONTRACT_SETUP_DBTABLE.getDBColumn("ContractSetupPK").getFullyQualifiedColumnName();
        String segmentFKCol       = CONTRACT_SETUP_DBTABLE.getDBColumn("SegmentFK").getFullyQualifiedColumnName();

        String transactionPriorityTransactionTypeCol = TRANSACTION_PRIORITY_DBTABLE.getDBColumn("TransactionTypeCT").getFullyQualifiedColumnName();
        String priorityCol = TRANSACTION_PRIORITY_DBTABLE.getDBColumn("Priority").getFullyQualifiedColumnName();
        
        String sql = "SELECT " +  EDITTRX_TABLENAME + ".* FROM " + EDITTRX_TABLENAME + ", " + CLIENT_SETUP_TABLENAME + ", " + CONTRACT_SETUP_TABLENAME +
        			", " + TRANSACTION_PRIORITY_TABLENAME +
                     " WHERE " + clientSetupFKCol + " = " + clientSetupPKCol +
                     " AND " + contractSetupFKCol + " = " + contractSetupPKCol + 
                     " AND " + transactionTypeCTCol + " = " + transactionPriorityTransactionTypeCol +
                     " AND " + segmentFKCol + " = ?" +
                     " AND " + editTrxPKCol + " >= ?" +
                     " AND " + statusCol + " IN ('N', 'A')" +
                     " AND " + pendingStatusCol + " = 'H'" +
                     " ORDER BY " + effectiveDateCol + " DESC, " + priorityCol + " DESC, " + sequenceNumberCol + " DESC";
    
        Connection conn = null;

        PreparedStatement ps = null;

        try
        {
            conn = ConnectionFactory.getSingleton().getConnection(POOLNAME);

            ps = conn.prepareStatement(sql); 

            ps.setLong(1, segmentPK); 
            ps.setLong(2, editTrxPK);
            
            editTrxVOs = (EDITTrxVO[]) executeQuery(EDITTrxVO.class,
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

        return editTrxVOs;
    }
    
    public EDITTrxVO[] getLinkedPYTrx(long segmentPK) throws Exception
    {
    	EDITTrxVO[] editTrxVOs = null; 

        String statusCol   = EDITTRX_DBTABLE.getDBColumn("Status").getFullyQualifiedColumnName();
        String clientSetupFKCol   = EDITTRX_DBTABLE.getDBColumn("ClientSetupFK").getFullyQualifiedColumnName();
        String originatingTrxFKCol   = EDITTRX_DBTABLE.getDBColumn("OriginatingTrxFK").getFullyQualifiedColumnName();
        String transactionTypeCTCol   = EDITTRX_DBTABLE.getDBColumn("TransactionTypeCT").getFullyQualifiedColumnName();
        String pendingStatusCol   = EDITTRX_DBTABLE.getDBColumn("PendingStatus").getFullyQualifiedColumnName();

        String clientSetupPKCol   = CLIENT_SETUP_DBTABLE.getDBColumn("ClientSetupPK").getFullyQualifiedColumnName();
        String contractSetupFKCol = CLIENT_SETUP_DBTABLE.getDBColumn("ContractSetupFK").getFullyQualifiedColumnName();

        String contractSetupPKCol = CONTRACT_SETUP_DBTABLE.getDBColumn("ContractSetupPK").getFullyQualifiedColumnName();
        String segmentFKCol       = CONTRACT_SETUP_DBTABLE.getDBColumn("SegmentFK").getFullyQualifiedColumnName();

        
        String sql = "SELECT " +  EDITTRX_TABLENAME + ".* FROM " + EDITTRX_TABLENAME + ", " + CLIENT_SETUP_TABLENAME + ", " + CONTRACT_SETUP_TABLENAME +
                     " WHERE " + clientSetupFKCol + " = " + clientSetupPKCol +
                     " AND " + contractSetupFKCol + " = " + contractSetupPKCol + 
                     " AND " + segmentFKCol + " = ?" +
                     " AND " + statusCol + " IN ('N', 'A')" +
                     " AND " + pendingStatusCol + " = 'H'" +
                     " AND " + transactionTypeCTCol + " = 'PY'" +
                     " AND " + originatingTrxFKCol + " IS NOT NULL ";
    
        Connection conn = null;

        PreparedStatement ps = null;

        try
        {
            conn = ConnectionFactory.getSingleton().getConnection(POOLNAME);

            ps = conn.prepareStatement(sql); 

            ps.setLong(1, segmentPK); 
            
            editTrxVOs = (EDITTrxVO[]) executeQuery(EDITTrxVO.class,
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

        return editTrxVOs;
    }
    
    public EDITTrxVO[] findBySegmentPK_AND_EffectiveDateGT_For_InforceQuote(long segmentPK, String effectiveDate) throws Exception
    {
        EDITTrxVO[] editTrxVOs = null;

        String clientSetupFKCol   = EDITTRX_DBTABLE.getDBColumn("ClientSetupFK").getFullyQualifiedColumnName();
        String effectiveDateCol   = EDITTRX_DBTABLE.getDBColumn("EffectiveDate").getFullyQualifiedColumnName();
        String pendingStatusCol   = EDITTRX_DBTABLE.getDBColumn("PendingStatus").getFullyQualifiedColumnName();
        String statusCol          = EDITTRX_DBTABLE.getDBColumn("Status").getFullyQualifiedColumnName();

        String clientSetupPKCol   = CLIENT_SETUP_DBTABLE.getDBColumn("ClientSetupPK").getFullyQualifiedColumnName();
        String contractSetupFKCol = CLIENT_SETUP_DBTABLE.getDBColumn("ContractSetupFK").getFullyQualifiedColumnName();

        String contractSetupPKCol = CONTRACT_SETUP_DBTABLE.getDBColumn("ContractSetupPK").getFullyQualifiedColumnName();
        String segmentFKCol       = CONTRACT_SETUP_DBTABLE.getDBColumn("SegmentFK").getFullyQualifiedColumnName();

        String sql = " SELECT " + EDITTRX_TABLENAME + ".* FROM " + EDITTRX_TABLENAME + ", " + CLIENT_SETUP_TABLENAME + ", " + CONTRACT_SETUP_TABLENAME +
                     " WHERE " + clientSetupFKCol + " = " + clientSetupPKCol +
                     " AND " + contractSetupFKCol + " = " + contractSetupPKCol +
                     " AND " + segmentFKCol + " = " + segmentPK +
                     " AND " + effectiveDateCol + " > ?" +
                     " AND " + pendingStatusCol + " IN ('H', 'L')" +
                     " AND " + statusCol + " IN ('N', 'A')";

        Connection conn = null;

        PreparedStatement ps = null;

        try
        {
            conn = ConnectionFactory.getSingleton().getConnection(POOLNAME);

            ps = conn.prepareStatement(sql);

            ps.setDate(1, DBUtil.convertStringToDate(effectiveDate));

            editTrxVOs = (EDITTrxVO[]) executeQuery(EDITTrxVO.class,
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

        return editTrxVOs;
    }

    public EDITTrxVO[] findByEffectiveDateAndTrxTypeCT(String effectiveDate, String transactionTypeCT, long segmentPK)
    {
        EDITTrxVO[] editTrxVOs = null;

        String clientSetupFKCol     = EDITTRX_DBTABLE.getDBColumn("ClientSetupFK").getFullyQualifiedColumnName();
        String effectiveDateCol     = EDITTRX_DBTABLE.getDBColumn("EffectiveDate").getFullyQualifiedColumnName();
        String transactionTypeCTCol = EDITTRX_DBTABLE.getDBColumn("TransactionTypeCT").getFullyQualifiedColumnName();

        String contractSetupFKCol   = CLIENT_SETUP_DBTABLE.getDBColumn("ContractSetupFK").getFullyQualifiedColumnName();
        String clientSetupPKCol     = CLIENT_SETUP_DBTABLE.getDBColumn("ClientSetupPK").getFullyQualifiedColumnName();

        String segmentFKCol         = CONTRACT_SETUP_DBTABLE.getDBColumn("SegmentFK").getFullyQualifiedColumnName();
        String contractSetupPKCol   = CONTRACT_SETUP_DBTABLE.getDBColumn("ContractSetupPK").getFullyQualifiedColumnName();

        String sql = "SELECT " + EDITTRX_TABLENAME + ".* FROM " + EDITTRX_TABLENAME + ", " + CLIENT_SETUP_TABLENAME + ", " + CONTRACT_SETUP_TABLENAME +
                     " WHERE " + segmentFKCol + " = " + segmentPK +
                     " AND " + contractSetupFKCol + " = " + contractSetupPKCol +
                     " AND " + clientSetupFKCol + " = " + clientSetupPKCol +
                     " AND " + effectiveDateCol + " = ?" +
                     " AND " + transactionTypeCTCol + " = '" + transactionTypeCT + "'";

        Connection conn = null;

        PreparedStatement ps = null;

        try
        {
            conn = ConnectionFactory.getSingleton().getConnection(POOLNAME);

            ps = conn.prepareStatement(sql);

            ps.setDate(1, DBUtil.convertStringToDate(effectiveDate));

            editTrxVOs = (EDITTrxVO[]) executeQuery(EDITTrxVO.class,
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

        return editTrxVOs;
    }

    public EDITTrxVO[] findRenewalByOriginatingPK(long editTrxPK) throws Exception
    {
        String originatingTrxFKCol   = EDITTRX_DBTABLE.getDBColumn("OriginatingTrxFK").getFullyQualifiedColumnName();
        String transactionTypeCTCol  = EDITTRX_DBTABLE.getDBColumn("TransactionTypeCT").getFullyQualifiedColumnName();

        String sql = "SELECT * FROM " + EDITTRX_TABLENAME +
                     " WHERE " + originatingTrxFKCol + " = " + editTrxPK +
                     " AND " + transactionTypeCTCol + " = 'RN'";

        return (EDITTrxVO[]) executeQuery(EDITTrxVO.class,
                                           sql,
                                            POOLNAME,
                                             false,
                                              null);
    }

    public EDITTrxVO[] findIssueTrx(long segmentPK) throws Exception
    {
        String clientSetupFKCol     = EDITTRX_DBTABLE.getDBColumn("ClientSetupFK").getFullyQualifiedColumnName();
        String transactionTypeCTCol = EDITTRX_DBTABLE.getDBColumn("TransactionTypeCT").getFullyQualifiedColumnName();
        String statusCol            = EDITTRX_DBTABLE.getDBColumn("Status").getFullyQualifiedColumnName();

        String clientSetupPKCol     = CLIENT_SETUP_DBTABLE.getDBColumn("ClientSetupPK").getFullyQualifiedColumnName();
        String contractSetupFKCol   = CLIENT_SETUP_DBTABLE.getDBColumn("ContractSetupFK").getFullyQualifiedColumnName();

        String contractSetupPKCol   = CONTRACT_SETUP_DBTABLE.getDBColumn("ContractSetupPK").getFullyQualifiedColumnName();
        String segmentFKCol         = CONTRACT_SETUP_DBTABLE.getDBColumn("SegmentFK").getFullyQualifiedColumnName();

        String sql = "SELECT " + EDITTRX_TABLENAME + ".* FROM " + EDITTRX_TABLENAME + ", " + CLIENT_SETUP_TABLENAME + ", " + CONTRACT_SETUP_TABLENAME +
                     " WHERE " + clientSetupFKCol + " = " + clientSetupPKCol +
                     " AND " + contractSetupFKCol + " = " + contractSetupPKCol +
                     " AND " + segmentFKCol + " = " + segmentPK +
                     " AND " + transactionTypeCTCol + " = 'IS'" +
                     " AND " + statusCol + " IN ('G', 'N')";

        return (EDITTrxVO[]) executeQuery(EDITTrxVO.class,
                                           sql,
                                            POOLNAME,
                                             false,
                                              null);
    }

    public EDITTrxVO[] findAllForStatement(long segmentPK, String startingEffDate, String endingEffDate, int drivingTrxPriority)
            throws Exception
    {
        EDITTrxVO[] editTrxVOs = null;

        String clientSetupFKCol   = EDITTRX_DBTABLE.getDBColumn("ClientSetupFK").getFullyQualifiedColumnName();
        String effectiveDateCol   = EDITTRX_DBTABLE.getDBColumn("EffectiveDate").getFullyQualifiedColumnName();
        String pendingStatusCol   = EDITTRX_DBTABLE.getDBColumn("PendingStatus").getFullyQualifiedColumnName();

        String clientSetupPKCol   = CLIENT_SETUP_DBTABLE.getDBColumn("ClientSetupPK").getFullyQualifiedColumnName();
        String contractSetupFKCol = CLIENT_SETUP_DBTABLE.getDBColumn("ContractSetupFK").getFullyQualifiedColumnName();

        String contractSetupPKCol = CONTRACT_SETUP_DBTABLE.getDBColumn("ContractSetupPK").getFullyQualifiedColumnName();
        String segmentFKCol       = CONTRACT_SETUP_DBTABLE.getDBColumn("SegmentFK").getFullyQualifiedColumnName();

        String sql = "SELECT " + EDITTRX_TABLENAME + ".* FROM " + EDITTRX_TABLENAME + ", " + CLIENT_SETUP_TABLENAME + ", " + CONTRACT_SETUP_TABLENAME +
                     " WHERE " + clientSetupFKCol + " = " + clientSetupPKCol +
                     " AND " + contractSetupFKCol + " = " + contractSetupPKCol +
                     " AND " + segmentFKCol + " = ?" +
                     " AND " + effectiveDateCol + " >= ?" +
                     " AND " + effectiveDateCol + " <= ?" +
                     " AND " + pendingStatusCol + " = 'H'";

        Connection conn = null;

        PreparedStatement ps = null;

        try
        {
            conn = ConnectionFactory.getSingleton().getConnection(POOLNAME);

            ps = conn.prepareStatement(sql);

            ps.setLong(1, segmentPK);
            ps.setDate(2, DBUtil.convertStringToDate(startingEffDate));
            ps.setDate(3, DBUtil.convertStringToDate(endingEffDate));

            editTrxVOs = (EDITTrxVO[]) executeQuery(EDITTrxVO.class,
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

        return editTrxVOs;
    }
    
    public EDITTrxVO[] findByPendingStatus(String pendingStatus) throws Exception
    {
        String pendingStatusCol = EDITTRX_DBTABLE.getDBColumn("PendingStatus").getFullyQualifiedColumnName();
        String effectiveDateCol = EDITTRX_DBTABLE.getDBColumn("EffectiveDate").getFullyQualifiedColumnName();

        String sql = "SELECT " + EDITTRX_TABLENAME + ".* FROM " + EDITTRX_TABLENAME +
                     " WHERE " + pendingStatusCol + " = '" + pendingStatus + "'" +
                     " ORDER BY " + effectiveDateCol + " ASC";

        return (EDITTrxVO[]) executeQuery(EDITTrxVO.class,
                                           sql,
                                            POOLNAME,
                                             false,
                                              null);
    }
    
    public EDITTrxVO[] findByPendingStatusAndTransactionType(long segmentFK, String pendingStatus, String transactionType) throws Exception
    {
        String pendingStatusCol = EDITTRX_DBTABLE.getDBColumn("PendingStatus").getFullyQualifiedColumnName();
        String effectiveDateCol = EDITTRX_DBTABLE.getDBColumn("EffectiveDate").getFullyQualifiedColumnName();
        String transactionTypeCol = EDITTRX_DBTABLE.getDBColumn("TransactionTypeCT").getFullyQualifiedColumnName();
        String clientSetupFKCol   = EDITTRX_DBTABLE.getDBColumn("ClientSetupFK").getFullyQualifiedColumnName();
        String clientSetupPKCol   = CLIENT_SETUP_DBTABLE.getDBColumn("ClientSetupPK").getFullyQualifiedColumnName();
        String contractSetupFKCol = CLIENT_SETUP_DBTABLE.getDBColumn("ContractSetupFK").getFullyQualifiedColumnName();
        String contractSetupPKCol = CONTRACT_SETUP_DBTABLE.getDBColumn("ContractSetupPK").getFullyQualifiedColumnName();
        String segmentFKCol       = CONTRACT_SETUP_DBTABLE.getDBColumn("SegmentFK").getFullyQualifiedColumnName();
        
        String sql = "SELECT " + EDITTRX_TABLENAME + ".* FROM " + EDITTRX_TABLENAME + ", " + CLIENT_SETUP_TABLENAME + ", " + CONTRACT_SETUP_TABLENAME +
        		" WHERE " + clientSetupFKCol + " = " + clientSetupPKCol +
                " AND " + contractSetupFKCol + " = " + contractSetupPKCol +  
                " AND " + segmentFKCol + " = " + segmentFK +
        		" AND " + pendingStatusCol + " = '" + pendingStatus + "'" +
                " AND " + transactionTypeCol + " = '" + transactionType + "'" +
                " ORDER BY " + effectiveDateCol + " ASC";

        return (EDITTrxVO[]) executeQuery(EDITTrxVO.class,
                                           sql,
                                            POOLNAME,
                                             false,
                                              null);
    }

    public EDITTrxVO[] findByPendingStatusSortBySegmentAndEffDate(String pendingStatus) throws Exception
    {
        String clientSetupFKCol   = EDITTRX_DBTABLE.getDBColumn("ClientSetupFK").getFullyQualifiedColumnName();
        String clientSetupPKCol   = CLIENT_SETUP_DBTABLE.getDBColumn("ClientSetupPK").getFullyQualifiedColumnName();
        String contractSetupFKCol = CLIENT_SETUP_DBTABLE.getDBColumn("ContractSetupFK").getFullyQualifiedColumnName();
        String contractSetupPKCol = CONTRACT_SETUP_DBTABLE.getDBColumn("ContractSetupPK").getFullyQualifiedColumnName();
        String segmentFKCol       = CONTRACT_SETUP_DBTABLE.getDBColumn("SegmentFK").getFullyQualifiedColumnName();
        String effectiveDateCol   = EDITTRX_DBTABLE.getDBColumn("EffectiveDate").getFullyQualifiedColumnName();
        String pendingStatusCol   = EDITTRX_DBTABLE.getDBColumn("PendingStatus").getFullyQualifiedColumnName();
        String statusCol          = EDITTRX_DBTABLE.getDBColumn("Status").getFullyQualifiedColumnName();

        String sql = "SELECT " + EDITTRX_TABLENAME + ".* FROM " + EDITTRX_TABLENAME + ", " + CLIENT_SETUP_TABLENAME + ", " + CONTRACT_SETUP_TABLENAME +
                     " WHERE " + clientSetupFKCol + " = " + clientSetupPKCol +
                     " AND " + contractSetupFKCol + " = " + contractSetupPKCol +
                     " AND " + pendingStatusCol + " = '" + pendingStatus + "'" +
                     " AND " + statusCol + " IN ('N','A')" +
                     " ORDER BY " + segmentFKCol + ", " + effectiveDateCol + " ASC";

        return (EDITTrxVO[]) executeQuery(EDITTrxVO.class,
                                           sql,
                                            POOLNAME,
                                             false,
                                              null);
    }

    public EDITTrxVO[] findBySegmentPKPendingStatusAndEffDateGTE(long segmentFK, String[] pendingStatus, String drivingEffDate)
    {
        EDITTrxVO[] editTrxVOs = null;

        String clientSetupFKCol   = EDITTRX_DBTABLE.getDBColumn("ClientSetupFK").getFullyQualifiedColumnName();
        String clientSetupPKCol   = CLIENT_SETUP_DBTABLE.getDBColumn("ClientSetupPK").getFullyQualifiedColumnName();
        String contractSetupFKCol = CLIENT_SETUP_DBTABLE.getDBColumn("ContractSetupFK").getFullyQualifiedColumnName();
        String contractSetupPKCol = CONTRACT_SETUP_DBTABLE.getDBColumn("ContractSetupPK").getFullyQualifiedColumnName();
        String segmentFKCol       = CONTRACT_SETUP_DBTABLE.getDBColumn("SegmentFK").getFullyQualifiedColumnName();
        String effectiveDateCol   = EDITTRX_DBTABLE.getDBColumn("EffectiveDate").getFullyQualifiedColumnName();
        String pendingStatusCol   = EDITTRX_DBTABLE.getDBColumn("PendingStatus").getFullyQualifiedColumnName();
        String statusCol = EDITTRX_DBTABLE.getDBColumn("Status").getFullyQualifiedColumnName();

        String sqlIn = "";

        for (int i = 0; i < pendingStatus.length; i++)
        {
            if (i < pendingStatus.length - 1)
            {
                sqlIn = sqlIn + "'" + pendingStatus[i] + "', ";
            }
            else
            {
                sqlIn = sqlIn + "'" + pendingStatus[i] + "')";
            }
        }

        String sql = "SELECT " + EDITTRX_TABLENAME + ".* FROM " + EDITTRX_TABLENAME + ", " + CLIENT_SETUP_TABLENAME + ", " + CONTRACT_SETUP_TABLENAME +
                     " WHERE " + clientSetupFKCol + " = " + clientSetupPKCol +
                     " AND " + contractSetupFKCol + " = " + contractSetupPKCol +
                     " AND " + segmentFKCol + " = ?" +
                     " AND " + pendingStatusCol + " IN (" + sqlIn +
                     " AND " + effectiveDateCol + " >= ?" +
                     " AND " + statusCol + " IN ('A', 'N')" +
                     " ORDER BY " + effectiveDateCol + " ASC";

        Connection conn = null;

        PreparedStatement ps = null;

        try
        {
            conn = ConnectionFactory.getSingleton().getConnection(POOLNAME);

            ps = conn.prepareStatement(sql);

            ps.setLong(1, segmentFK);
            ps.setDate(2, DBUtil.convertStringToDate(drivingEffDate));

            editTrxVOs = (EDITTrxVO[]) executeQuery(EDITTrxVO.class,
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

        return editTrxVOs;
    }
    
    public EDITTrxVO[] findAllByTrxTypeAndFilteredFundFK(long filteredFundFK) throws Exception
    {
        DBTable investmentAllocOvrdDBTable    = DBTable.getDBTableForTable("InvestmentAllocationOverride");
        DBTable investmentDBTable = DBTable.getDBTableForTable("Investment");

        String investmentAllocationOverrideTable    = investmentAllocOvrdDBTable.getFullyQualifiedTableName();
        String investmentTable = investmentDBTable.getFullyQualifiedTableName();

        String transactionTypeCT = EDITTRX_DBTABLE.getDBColumn("TransactionTypeCT").getFullyQualifiedColumnName();
        String clientSetupFKCol   = EDITTRX_DBTABLE.getDBColumn("ClientSetupFK").getFullyQualifiedColumnName();
        String clientSetupPKCol   = CLIENT_SETUP_DBTABLE.getDBColumn("ClientSetupPK").getFullyQualifiedColumnName();
        String clientContractSetupFKCol = CLIENT_SETUP_DBTABLE.getDBColumn("ContractSetupFK").getFullyQualifiedColumnName();
        String contractSetupPKCol = CONTRACT_SETUP_DBTABLE.getDBColumn("ContractSetupPK").getFullyQualifiedColumnName();
        String iaoContractSetupFKCol = investmentAllocOvrdDBTable.getDBColumn("ContractSetupFK").getFullyQualifiedColumnName();
        String investmentAllocationOverridePKCol = investmentAllocOvrdDBTable.getDBColumn("InvestmentAllocationOverridePK").getFullyQualifiedColumnName();
        String investmentFKCol = investmentAllocOvrdDBTable.getDBColumn("InvestmentFK").getFullyQualifiedColumnName();
        String investmentPKCol = investmentDBTable.getDBColumn("InvestmentPK").getFullyQualifiedColumnName();
        String filteredFundFKCol = investmentDBTable.getDBColumn("FilteredFundFK").getFullyQualifiedColumnName();
        String toFromStatusCol = investmentAllocOvrdDBTable.getDBColumn("ToFromStatus").getFullyQualifiedColumnName();
        String pendingStatusCol = EDITTRX_DBTABLE.getDBColumn("PendingStatus").getFullyQualifiedColumnName();
        String statusCol = EDITTRX_DBTABLE.getDBColumn("Status").getFullyQualifiedColumnName();

        String sql = "SELECT " + EDITTRX_TABLENAME + ".* FROM " + EDITTRX_TABLENAME + ", " + CLIENT_SETUP_TABLENAME +
                     ", " + CONTRACT_SETUP_TABLENAME + ", " + investmentAllocationOverrideTable + ", " + investmentTable +
                     " WHERE " + transactionTypeCT + " IN ('HFTA', 'HFTP')" +
                     " AND " + clientSetupFKCol + " = " + clientSetupPKCol +
                     " AND " + clientContractSetupFKCol + " = " + contractSetupPKCol +
                     " AND " + iaoContractSetupFKCol + " = " + contractSetupPKCol +
                     " AND " + pendingStatusCol + " IN ('B','F','L','H')" +
                     " AND " + statusCol + " IN ('A', 'N')" +
                     " AND " + investmentAllocationOverridePKCol + " IN (SELECT " + investmentAllocationOverridePKCol +
                     " FROM " + investmentAllocationOverrideTable +
                     " WHERE " + toFromStatusCol + " = 'F'" +
                     " AND " + investmentFKCol + " = " + investmentPKCol +
                     " AND " + filteredFundFKCol + " = " + filteredFundFK + ")";

        return (EDITTrxVO[]) executeQuery(EDITTrxVO.class,
                                           sql,
                                            POOLNAME,
                                             false,
                                              null);
    }

    /**
     * Get the MD trx for a Life contract
     * @param segmentPK
     * @param trxType
     * @param currentDate
     * @return  EDITTrxVO[]
     */
    public EDITTrxVO[] findBySegmentPK_AND_TrxType_AND_Date(long segmentPK, String trxType, String currentDate)
    {
        String clientSetupFKCol     = EDITTRX_DBTABLE.getDBColumn("ClientSetupFK").getFullyQualifiedColumnName();
        String clientSetupPKCol     = CLIENT_SETUP_DBTABLE.getDBColumn( "ClientSetupPK").getFullyQualifiedColumnName();
        String contractSetupFKCol   = CLIENT_SETUP_DBTABLE.getDBColumn( "ContractSetupFK").getFullyQualifiedColumnName();
        String contractSetupPKCol   = CONTRACT_SETUP_DBTABLE.getDBColumn( "ContractSetupPK").getFullyQualifiedColumnName();
        String segmentFKCol         = CONTRACT_SETUP_DBTABLE.getDBColumn( "SegmentFK").getFullyQualifiedColumnName();
        String effectiveDateCol     = EDITTRX_DBTABLE.getDBColumn( "EffectiveDate").getFullyQualifiedColumnName();
        String pendingStatusCol     = EDITTRX_DBTABLE.getDBColumn( "PendingStatus").getFullyQualifiedColumnName();
        String transactionTypeCTCol = EDITTRX_DBTABLE.getDBColumn( "TransactionTypeCT").getFullyQualifiedColumnName();


        String sql = "SELECT " + EDITTRX_TABLENAME + ".* FROM " + EDITTRX_TABLENAME + "," + CLIENT_SETUP_TABLENAME + "," + CONTRACT_SETUP_TABLENAME;
        sql = sql + " WHERE " + clientSetupFKCol + "= " + clientSetupPKCol + " AND " + contractSetupFKCol + "= " + contractSetupPKCol;
        sql = sql + " AND "   + segmentFKCol     + "= ?" + " AND " + effectiveDateCol + ">= ?";
        sql = sql + " AND "   + transactionTypeCTCol + "= ? AND " + pendingStatusCol + " = 'P'";

        EDITTrxVO[] editTrxVOs = null;
        Connection conn = null;
        PreparedStatement ps = null;

        try
        {
            conn = ConnectionFactory.getSingleton().getConnection(POOLNAME);

            ps = conn.prepareStatement(sql);

            ps.setLong(1, segmentPK);
            ps.setDate(2, DBUtil.convertStringToDate(currentDate));
            ps.setString(3, trxType);

            editTrxVOs = (EDITTrxVO[]) executeQuery(EDITTrxVO.class,
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

        return editTrxVOs;
    }

    /**
     * Finds the SPAWNING transaction specified by the given param value
     * @param originatingTrxFK
     * @return
     */
    public EDITTrxVO[] findByOriginatingEditTrxFK(long originatingTrxFK)
    {
        String editTrxPKCol = EDITTRX_DBTABLE.getDBColumn("EDITTrxPK").getFullyQualifiedColumnName();

        String sql = "SELECT * FROM " + EDITTRX_TABLENAME +
                    " WHERE " + editTrxPKCol + " = " + originatingTrxFK;

        return (EDITTrxVO[]) executeQuery(EDITTrxVO.class,
                                          sql.toString(),
                                          POOLNAME,
                                          false,
                                          null);

    }

    /**
     * Finds the EDITTrx record whose ReapplyEDITTrxFK is equal to the reapplyEDITTrxFK parameter
     * @param reapplyEDITTrxFK
     * @return
     */
    public EDITTrxVO[] findByReapplyEDITTrxFK(long reapplyEDITTrxFK)
    {
        String reapplyEditTrxFKCol = EDITTRX_DBTABLE.getDBColumn("ReapplyEDITTrxFK").getFullyQualifiedColumnName();

        String sql = "SELECT * FROM " + EDITTRX_TABLENAME +
                    " WHERE " + reapplyEditTrxFKCol + " = " + reapplyEDITTrxFK;

        return (EDITTrxVO[]) executeQuery(EDITTrxVO.class,
                                          sql.toString(),
                                          POOLNAME,
                                          false,
                                          null);
    }

    /**
     * Finder.
     * @param reinsuranceHistoryPK
     * @return
     */
    public EDITTrxVO[] findBy_ReinsuranceHistoryPK(long reinsuranceHistoryPK)
    {
        String editTrxPKCol = EDITTRX_DBTABLE.getDBColumn("EDITTrxPK").getFullyQualifiedColumnName();

        DBTable editTrxHistoryDBTable = DBTable.getDBTableForTable("EDITTrxHistory");
        String editTrxHistoryTable = editTrxHistoryDBTable.getFullyQualifiedTableName();
        String editTrxFKCol = editTrxHistoryDBTable.getDBColumn("EDITTrxFK").getFullyQualifiedColumnName();
        String editTrxHistoryPKCol = editTrxHistoryDBTable.getDBColumn("EDITTrxHistoryPK").getFullyQualifiedColumnName();

        DBTable reinsuranceHistoryDBTable = DBTable.getDBTableForTable("ReinsuranceHistory");
        String reinsuranceHistoryTable = reinsuranceHistoryDBTable.getFullyQualifiedTableName();
        String editTrxHistoryFKCol = reinsuranceHistoryDBTable.getDBColumn("EDITTrxHistoryFK").getFullyQualifiedColumnName();
        String reinsuranceHistoryPKCol = reinsuranceHistoryDBTable.getDBColumn("ReinsuranceHistoryPK").getFullyQualifiedColumnName();

        String sql;
        sql =   " SELECT " + EDITTRX_TABLENAME + ".*" +
                " FROM " + EDITTRX_TABLENAME +
                " INNER JOIN " + editTrxHistoryTable +
                " ON " + editTrxPKCol + " = " + editTrxFKCol +
                " INNER JOIN " +  reinsuranceHistoryTable +
                " ON " + editTrxHistoryPKCol + " = " + editTrxHistoryFKCol +
                " WHERE " + reinsuranceHistoryPKCol + " = " +  reinsuranceHistoryPK;

        return (EDITTrxVO[]) executeQuery(EDITTrxVO.class,
                                          sql.toString(),
                                          POOLNAME,
                                          false,
                                          null);
    }

    /**
     * Finds the editTrx that was spawned by the specified trx
     * @param originatingTrxFK
     * @return
     */
    public EDITTrxVO[] findSpawnedTrx(long originatingTrxFK)
    {
        String originatingTrxFKCol = EDITTRX_DBTABLE.getDBColumn("OriginatingTrxFK").getFullyQualifiedColumnName();

        String sql = "SELECT * FROM " + EDITTRX_TABLENAME +
                    " WHERE " + originatingTrxFKCol + " = " + originatingTrxFK;

        return (EDITTrxVO[]) executeQuery(EDITTrxVO.class,
                                          sql.toString(),
                                          POOLNAME,
                                          false,
                                          null);

    }

    public EDITTrxVO[] findTerminatedTrxForContract(long segmentPK)
    {
        String pendingStatusCol = EDITTRX_DBTABLE.getDBColumn("PendingStatus").getFullyQualifiedColumnName();
        String clientSetupFKCol   = EDITTRX_DBTABLE.getDBColumn("ClientSetupFK").getFullyQualifiedColumnName();

        String clientSetupPKCol   = CLIENT_SETUP_DBTABLE.getDBColumn("ClientSetupPK").getFullyQualifiedColumnName();
        String contractSetupFKCol = CLIENT_SETUP_DBTABLE.getDBColumn("ContractSetupFK").getFullyQualifiedColumnName();

        String contractSetupPKCol = CONTRACT_SETUP_DBTABLE.getDBColumn("ContractSetupPK").getFullyQualifiedColumnName();
        String segmentFKCol       = CONTRACT_SETUP_DBTABLE.getDBColumn("SegmentFK").getFullyQualifiedColumnName();

        DBTable SEGMENT_DBTABLE = DBTable.getDBTableForTable("Segment");
        String SEGMENT_TABLENAME = SEGMENT_DBTABLE.getFullyQualifiedTableName();
        String segmentPKCol = SEGMENT_DBTABLE.getDBColumn("SegmentPK").getFullyQualifiedColumnName();
        String segmentFKColumn = SEGMENT_DBTABLE.getDBColumn("SegmentFK").getFullyQualifiedColumnName();

        String sql = "SELECT " + EDITTRX_TABLENAME + ".* FROM " + EDITTRX_TABLENAME +
                     " INNER JOIN " + CLIENT_SETUP_TABLENAME + " ON " + clientSetupFKCol + " = " + clientSetupPKCol +
                     " INNER JOIN " + CONTRACT_SETUP_TABLENAME + " ON " + contractSetupFKCol + " = " + contractSetupPKCol +
                     " INNER JOIN " + SEGMENT_TABLENAME + " ON " + segmentFKCol + " = " + segmentPKCol +
                     " WHERE  (" + segmentPKCol + " = " + segmentPK +
                     " OR " + segmentFKColumn + " = " + segmentPK +
                     ") AND " + pendingStatusCol + " = 'T'";

        return (EDITTrxVO[]) executeQuery(EDITTrxVO.class,
                                          sql.toString(),
                                          POOLNAME,
                                          false,
                                          null);
//        return null;
    }
    
    public EDITTrxVO[] findTerminatedTrxForTransaction (long segmentPK, long terminationTrxFK)
    {
        String pendingStatusCol = EDITTRX_DBTABLE.getDBColumn("PendingStatus").getFullyQualifiedColumnName();
        String clientSetupFKCol   = EDITTRX_DBTABLE.getDBColumn("ClientSetupFK").getFullyQualifiedColumnName();
        String terminationTrxFKCol   = EDITTRX_DBTABLE.getDBColumn("TerminationTrxFK").getFullyQualifiedColumnName();
        String transactionTypeCTCol   = EDITTRX_DBTABLE.getDBColumn("TransactionTypeCT").getFullyQualifiedColumnName();

        String clientSetupPKCol   = CLIENT_SETUP_DBTABLE.getDBColumn("ClientSetupPK").getFullyQualifiedColumnName();
        String contractSetupFKCol = CLIENT_SETUP_DBTABLE.getDBColumn("ContractSetupFK").getFullyQualifiedColumnName();

        String contractSetupPKCol = CONTRACT_SETUP_DBTABLE.getDBColumn("ContractSetupPK").getFullyQualifiedColumnName();
        String segmentFKCol       = CONTRACT_SETUP_DBTABLE.getDBColumn("SegmentFK").getFullyQualifiedColumnName();

        DBTable SEGMENT_DBTABLE = DBTable.getDBTableForTable("Segment");
        String SEGMENT_TABLENAME = SEGMENT_DBTABLE.getFullyQualifiedTableName();
        String segmentPKCol = SEGMENT_DBTABLE.getDBColumn("SegmentPK").getFullyQualifiedColumnName();
        String segmentFKColumn = SEGMENT_DBTABLE.getDBColumn("SegmentFK").getFullyQualifiedColumnName();

        String sql = "SELECT " + EDITTRX_TABLENAME + ".* FROM " + EDITTRX_TABLENAME +
                     " INNER JOIN " + CLIENT_SETUP_TABLENAME + " ON " + clientSetupFKCol + " = " + clientSetupPKCol +
                     " INNER JOIN " + CONTRACT_SETUP_TABLENAME + " ON " + contractSetupFKCol + " = " + contractSetupPKCol +
                     " INNER JOIN " + SEGMENT_TABLENAME + " ON " + segmentFKCol + " = " + segmentPKCol +
                     " WHERE  (" + segmentPKCol + " = " + segmentPK +
                     " OR " + segmentFKColumn + " = " + segmentPK +
                     ") AND " + pendingStatusCol + " = 'T'" +
                     " AND " + transactionTypeCTCol + " <> 'PY'" +
                     " AND " + terminationTrxFKCol + " = " + terminationTrxFK;

        return (EDITTrxVO[]) executeQuery(EDITTrxVO.class,
                                          sql.toString(),
                                          POOLNAME,
                                          false,
                                          null);
    }

    public EDITTrxVO[] findPreviousActiveTrx(long segmentPK, EDITTrxVO currentEDITTrx, int currentPriority)
    {
        EDITTrxVO[] editTrxVOs = null;

        String pendingStatusCol = EDITTRX_DBTABLE.getDBColumn("PendingStatus").getFullyQualifiedColumnName();
        String statusCol = EDITTRX_DBTABLE.getDBColumn("Status").getFullyQualifiedColumnName();
        String effectiveDateCol = EDITTRX_DBTABLE.getDBColumn("EffectiveDate").getFullyQualifiedColumnName();
        String editTrxTransactionTypeCol = EDITTRX_DBTABLE.getDBColumn("TransactionTypeCT").getFullyQualifiedColumnName();
        String clientSetupFKCol   = EDITTRX_DBTABLE.getDBColumn("ClientSetupFK").getFullyQualifiedColumnName();
        String trxSequenceCol   = EDITTRX_DBTABLE.getDBColumn("SequenceNumber").getFullyQualifiedColumnName();

        String clientSetupPKCol = CLIENT_SETUP_DBTABLE.getDBColumn("ClientSetupPK").getFullyQualifiedColumnName();
        String contractSetupFKCol = CLIENT_SETUP_DBTABLE.getDBColumn("ContractSetupFK").getFullyQualifiedColumnName();
        String contractSetupPKCol = CONTRACT_SETUP_DBTABLE.getDBColumn("ContractSetupPK").getFullyQualifiedColumnName();
        String segmentFKCol  = CONTRACT_SETUP_DBTABLE.getDBColumn("SegmentFK").getFullyQualifiedColumnName();
        String complexChangeTypeCol  = CONTRACT_SETUP_DBTABLE.getDBColumn("ComplexChangeTypeCT").getFullyQualifiedColumnName();

        String transactionPriorityTransactionTypeCol = TRANSACTION_PRIORITY_DBTABLE.getDBColumn("TransactionTypeCT").getFullyQualifiedColumnName();
        String priorityCol = TRANSACTION_PRIORITY_DBTABLE.getDBColumn("Priority").getFullyQualifiedColumnName();

        DBTable SEGMENT_DBTABLE = DBTable.getDBTableForTable("Segment");
        String SEGMENT_TABLENAME = SEGMENT_DBTABLE.getFullyQualifiedTableName();
        String segmentPKCol = SEGMENT_DBTABLE.getDBColumn("SegmentPK").getFullyQualifiedColumnName();

        String sql = "SELECT " + EDITTRX_TABLENAME + ".* FROM " + EDITTRX_TABLENAME +
                     " INNER JOIN " + CLIENT_SETUP_TABLENAME + " ON " + clientSetupFKCol + " = " + clientSetupPKCol +
                     " INNER JOIN " + CONTRACT_SETUP_TABLENAME + " ON " + contractSetupFKCol + " = " + contractSetupPKCol +
                     " INNER JOIN " + SEGMENT_TABLENAME + " ON " + segmentFKCol + " = " + segmentPKCol +
                     " INNER JOIN " + TRANSACTION_PRIORITY_TABLENAME + " ON " + editTrxTransactionTypeCol + " = " + transactionPriorityTransactionTypeCol +
                     " WHERE " + segmentPKCol + " = ? " +
                     // " AND (" + editTrxTransactionTypeCol + " <> 'BC' " +
                     // 	" OR (" + editTrxTransactionTypeCol + " = 'BC' AND " + complexChangeTypeCol + " <> 'Batch')) " +
                     " AND (" + complexChangeTypeCol + " is NULL OR " + complexChangeTypeCol + " <> 'Batch') " +
                     " AND " + statusCol + " in ('N', 'A')" + // OR " + statusCol + " = 'A')" +
                     " AND " + pendingStatusCol + " = 'H'" + 
                     " AND (" + effectiveDateCol + " < ? "  +
                     	" OR (" + effectiveDateCol + " = ? " +
                     		" AND (" + priorityCol + " < ? " + 
                     			" OR (" + priorityCol + " = ? " + " AND " + trxSequenceCol + " < ? ) " +
                     		" ) " +
                     	" ) " +
                     " ) " +
                     " ORDER BY " + effectiveDateCol + " DESC, " + priorityCol + " DESC, " + trxSequenceCol + " DESC ";
                
        Connection conn = null;

        PreparedStatement ps = null;

        try
        {
            conn = ConnectionFactory.getSingleton().getConnection(POOLNAME);

            ps = conn.prepareStatement(sql);
            
            ps.setLong(1, segmentPK);
            ps.setDate(2, DBUtil.convertStringToDate(currentEDITTrx.getEffectiveDate()));
            ps.setDate(3, DBUtil.convertStringToDate(currentEDITTrx.getEffectiveDate()));
            ps.setInt(4, currentPriority);
            ps.setInt(5, currentPriority);
            ps.setInt(6, currentEDITTrx.getSequenceNumber());

            editTrxVOs = (EDITTrxVO[]) executeQuery(EDITTrxVO.class,
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

                e.printStackTrace();

                throw new RuntimeException(e);
            }
        }

        return editTrxVOs;
    }

    public EDITTrxVO[] findPendingBy_GroupSetupPK(long groupSetupPK)
    {
        DBTable GROUPSETUP_DBTABLE  =  DBTable.getDBTableForTable("GroupSetup");
        String GROUPSETUP_TABLENAME        = GROUPSETUP_DBTABLE.getFullyQualifiedTableName();

        String pendingStatusCol = EDITTRX_DBTABLE.getDBColumn("PendingStatus").getFullyQualifiedColumnName();
        String clientSetupFKCol   = EDITTRX_DBTABLE.getDBColumn("ClientSetupFK").getFullyQualifiedColumnName();

        String clientSetupPKCol   = CLIENT_SETUP_DBTABLE.getDBColumn("ClientSetupPK").getFullyQualifiedColumnName();
        String contractSetupFKCol = CLIENT_SETUP_DBTABLE.getDBColumn("ContractSetupFK").getFullyQualifiedColumnName();

        String contractSetupPKCol = CONTRACT_SETUP_DBTABLE.getDBColumn("ContractSetupPK").getFullyQualifiedColumnName();

        String groupSetupPKCol    = GROUPSETUP_DBTABLE.getDBColumn("GroupSetupPK").getFullyQualifiedColumnName();

        String sql = "SELECT " + EDITTRX_TABLENAME + ".* FROM " + EDITTRX_TABLENAME + ", " + CLIENT_SETUP_TABLENAME + ", " +
                      CONTRACT_SETUP_TABLENAME + ", " + GROUPSETUP_TABLENAME +
                     " WHERE " + groupSetupPKCol + "= " + groupSetupPK +
                     " AND " + contractSetupPKCol + " = " + contractSetupFKCol +
                     " AND " + clientSetupPKCol + " = " + clientSetupFKCol +
                     " AND " + pendingStatusCol + " = 'P'";

        return (EDITTrxVO[]) executeQuery(EDITTrxVO.class,
                                          sql.toString(),
                                          POOLNAME,
                                          false,
                                          null);

    }

    /**
     * Find all Issue and ComplexChange transactions and their Premiums for a given segmentPK and effectiveDate.
     * @param segmentPK
     * @param effectiveDate
     * @return
     */
    public EDITTrxVO[] findAllTamraRetest(long segmentPK, EDITDate effectiveDate)
    {
        EDITTrxVO[] editTrxVOs = null;

        DBTable groupSetupDBTable = DBTable.getDBTableForTable("GroupSetup");
        DBTable segmentDBTable = DBTable.getDBTableForTable("Segment");
        DBTable lifeDBTable = DBTable.getDBTableForTable("Life");

        String groupSetupTableName = groupSetupDBTable.getFullyQualifiedTableName();
        String segmentTableName = segmentDBTable.getFullyQualifiedTableName();
//        String lifeTableName = lifeDBTable.getFullyQualifiedTableName();


        String clientSetupFKCol   = EDITTRX_DBTABLE.getDBColumn("ClientSetupFK").getFullyQualifiedColumnName();
        String effectiveDateCol   = EDITTRX_DBTABLE.getDBColumn("EffectiveDate").getFullyQualifiedColumnName();
        String pendingStatusCol   = EDITTRX_DBTABLE.getDBColumn("PendingStatus").getFullyQualifiedColumnName();
        String transactionTypeCTCol = EDITTRX_DBTABLE.getDBColumn("TransactionTypeCT").getFullyQualifiedColumnName();
        String statusCol = EDITTRX_DBTABLE.getDBColumn("Status").getFullyQualifiedColumnName();

        String clientSetupPKCol   = CLIENT_SETUP_DBTABLE.getDBColumn("ClientSetupPK").getFullyQualifiedColumnName();
        String contractSetupFKCol = CLIENT_SETUP_DBTABLE.getDBColumn("ContractSetupFK").getFullyQualifiedColumnName();

        String contractSetupPKCol = CONTRACT_SETUP_DBTABLE.getDBColumn("ContractSetupPK").getFullyQualifiedColumnName();
        String segmentFKCol       = CONTRACT_SETUP_DBTABLE.getDBColumn("SegmentFK").getFullyQualifiedColumnName();
        String groupSetupFKCol    = CONTRACT_SETUP_DBTABLE.getDBColumn("GroupSetupFK").getFullyQualifiedColumnName();
        String complexChangeTypeCTCol = CONTRACT_SETUP_DBTABLE.getDBColumn("ComplexChangeTypeCT").getFullyQualifiedColumnName();

        String groupSetupPKCol    = groupSetupDBTable.getDBColumn("GroupSetupPK").getFullyQualifiedColumnName();
        String premiumTypeCTCol   = groupSetupDBTable.getDBColumn("PremiumTypeCT").getFullyQualifiedColumnName();

        String segmentPKCol       = segmentDBTable.getDBColumn("SegmentPK").getFullyQualifiedColumnName();

//        String lifeSegmentFKCol   = lifeDBTable.getDBColumn("SegmentFK").getFullyQualifiedColumnName();
//        String startNew7PayIndicatorCol = lifeDBTable.getDBColumn("StartNew7PayIndicator").getFullyQualifiedColumnName();


        String sql = "SELECT " + EDITTRX_TABLENAME + ".* FROM " + EDITTRX_TABLENAME + ", " + CLIENT_SETUP_TABLENAME + ", " +
                     CONTRACT_SETUP_TABLENAME + ", " + groupSetupTableName + ", " + segmentTableName  +
                     " WHERE " + clientSetupFKCol + " = " + clientSetupPKCol +                      // join
                     " AND " + contractSetupFKCol + " = " + contractSetupPKCol +                    // join
                     " AND " + groupSetupFKCol + " = " + groupSetupPKCol +                          // join
                     " AND " + segmentFKCol + " = " + segmentPKCol +                                // join
                     " AND " + segmentPKCol + " = ?" +
                     " AND " + pendingStatusCol + " = 'H'" +
                     " AND " + effectiveDateCol + " <= ?" +
                     " AND " + statusCol + "IN ('N', 'A')" +
                     " AND  (" + transactionTypeCTCol + " IN ('IS', 'WI')  OR (" +
                     transactionTypeCTCol + " = 'PY' " +
                     " AND " + premiumTypeCTCol + " != 'Exchange') OR (" +
                     transactionTypeCTCol + " = 'CC'" +
                     " AND " + complexChangeTypeCTCol + " = 'FaceIncrease'))" +
//                     " AND " + startNew7PayIndicatorCol + " = 'Y'))" +
                     " ORDER BY " + effectiveDateCol;

        Connection conn = null;

        PreparedStatement ps = null;

        try
        {
            conn = ConnectionFactory.getSingleton().getConnection(POOLNAME);

            ps = conn.prepareStatement(sql);

            ps.setLong(1, segmentPK);
            ps.setDate(2, DBUtil.convertStringToDate(effectiveDate.getFormattedDate()));

            editTrxVOs = (EDITTrxVO[]) executeQuery(EDITTrxVO.class,
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

        return editTrxVOs;
    }

    /**
     * Find all ComplexChange transactions and their Premiums for a given segmentPK and effectiveDate.
     * @param segmentPK
     * @param effectiveDate
     * @return
     */
    public EDITTrxVO[] findPartialTamraRetest(long segmentPK, EDITDate ccEffectiveDate, EDITDate effectiveDate)
    {
        EDITTrxVO[] editTrxVOs = null;

        DBTable groupSetupDBTable = DBTable.getDBTableForTable("GroupSetup");
        DBTable segmentDBTable = DBTable.getDBTableForTable("Segment");
        DBTable lifeDBTable = DBTable.getDBTableForTable("Life");

        String groupSetupTableName = groupSetupDBTable.getFullyQualifiedTableName();
        String segmentTableName = segmentDBTable.getFullyQualifiedTableName();

        String clientSetupFKCol   = EDITTRX_DBTABLE.getDBColumn("ClientSetupFK").getFullyQualifiedColumnName();
        String effectiveDateCol   = EDITTRX_DBTABLE.getDBColumn("EffectiveDate").getFullyQualifiedColumnName();
        String pendingStatusCol   = EDITTRX_DBTABLE.getDBColumn("PendingStatus").getFullyQualifiedColumnName();
        String transactionTypeCTCol = EDITTRX_DBTABLE.getDBColumn("TransactionTypeCT").getFullyQualifiedColumnName();

        String clientSetupPKCol   = CLIENT_SETUP_DBTABLE.getDBColumn("ClientSetupPK").getFullyQualifiedColumnName();
        String contractSetupFKCol = CLIENT_SETUP_DBTABLE.getDBColumn("ContractSetupFK").getFullyQualifiedColumnName();

        String contractSetupPKCol = CONTRACT_SETUP_DBTABLE.getDBColumn("ContractSetupPK").getFullyQualifiedColumnName();
        String segmentFKCol       = CONTRACT_SETUP_DBTABLE.getDBColumn("SegmentFK").getFullyQualifiedColumnName();
        String groupSetupFKCol    = CONTRACT_SETUP_DBTABLE.getDBColumn("GroupSetupFK").getFullyQualifiedColumnName();

        String groupSetupPKCol    = groupSetupDBTable.getDBColumn("GroupSetupPK").getFullyQualifiedColumnName();
        String premiumTypeCTCol   = groupSetupDBTable.getDBColumn("PremiumTypeCT").getFullyQualifiedColumnName();

        String segmentPKCol       = segmentDBTable.getDBColumn("SegmentPK").getFullyQualifiedColumnName();

        String sql = "SELECT " + EDITTRX_TABLENAME + ".* FROM " + EDITTRX_TABLENAME + ", " + CLIENT_SETUP_TABLENAME + ", " +
                     CONTRACT_SETUP_TABLENAME + ", " + groupSetupTableName + ", " + segmentTableName +
                     " WHERE " + clientSetupFKCol + " = " + clientSetupPKCol +                      // join
                     " AND " + contractSetupFKCol + " = " + contractSetupPKCol +                    // join
                     " AND " + groupSetupFKCol + " = " + groupSetupPKCol +                          // join
                     " AND " + segmentFKCol + " = " + segmentPKCol +                                // join
                     " AND " + segmentPKCol + " = ?" +
                     " AND " + pendingStatusCol + " = 'H' AND (("  +
                     transactionTypeCTCol + " = 'PY' " +
                     " AND " + premiumTypeCTCol + " != 'Exchange') OR " +
                     transactionTypeCTCol + " = 'WI')" +
                     " AND (" + effectiveDateCol + ">= ?" +
                     " AND " + effectiveDateCol + "<= ?)" +
                     " ORDER BY " + effectiveDateCol;

        Connection conn = null;

        PreparedStatement ps = null;

        try
        {
            conn = ConnectionFactory.getSingleton().getConnection(POOLNAME);

            ps = conn.prepareStatement(sql);

            ps.setLong(1, segmentPK);
            ps.setDate(2, DBUtil.convertStringToDate(ccEffectiveDate.getFormattedDate()));
            ps.setDate(3, DBUtil.convertStringToDate(effectiveDate.getFormattedDate()));

            editTrxVOs = (EDITTrxVO[]) executeQuery(EDITTrxVO.class,
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

                e.printStackTrace();

                throw new RuntimeException(e);
            }
        }

        return editTrxVOs;
    }

    public EDITTrxVO[] findComplexChangeTrx(long segmentPK, EDITDate effectiveDate)
    {
        EDITTrxVO[] editTrxVOs = null;

        DBTable groupSetupDBTable = DBTable.getDBTableForTable("GroupSetup");
        DBTable segmentDBTable = DBTable.getDBTableForTable("Segment");
        DBTable lifeDBTable = DBTable.getDBTableForTable("Life");

        String groupSetupTableName = groupSetupDBTable.getFullyQualifiedTableName();
        String segmentTableName = segmentDBTable.getFullyQualifiedTableName();
        String lifeTableName = lifeDBTable.getFullyQualifiedTableName();


        String clientSetupFKCol   = EDITTRX_DBTABLE.getDBColumn("ClientSetupFK").getFullyQualifiedColumnName();
        String effectiveDateCol   = EDITTRX_DBTABLE.getDBColumn("EffectiveDate").getFullyQualifiedColumnName();
        String pendingStatusCol   = EDITTRX_DBTABLE.getDBColumn("PendingStatus").getFullyQualifiedColumnName();
        String transactionTypeCTCol = EDITTRX_DBTABLE.getDBColumn("TransactionTypeCT").getFullyQualifiedColumnName();

        String clientSetupPKCol   = CLIENT_SETUP_DBTABLE.getDBColumn("ClientSetupPK").getFullyQualifiedColumnName();
        String contractSetupFKCol = CLIENT_SETUP_DBTABLE.getDBColumn("ContractSetupFK").getFullyQualifiedColumnName();

        String contractSetupPKCol = CONTRACT_SETUP_DBTABLE.getDBColumn("ContractSetupPK").getFullyQualifiedColumnName();
        String segmentFKCol       = CONTRACT_SETUP_DBTABLE.getDBColumn("SegmentFK").getFullyQualifiedColumnName();
        String complexChangeTypeCTCol = CONTRACT_SETUP_DBTABLE.getDBColumn("ComplexChangeTypeCT").getFullyQualifiedColumnName();

        String segmentPKCol       = segmentDBTable.getDBColumn("SegmentPK").getFullyQualifiedColumnName();

        String sql = "SELECT " + EDITTRX_TABLENAME + ".* FROM " + EDITTRX_TABLENAME + ", " + CLIENT_SETUP_TABLENAME + ", " +
                      CONTRACT_SETUP_TABLENAME  + ", " + segmentTableName +
                     " WHERE " + clientSetupFKCol + " = " + clientSetupPKCol +                      // join
                     " AND " + contractSetupFKCol + " = " + contractSetupPKCol +                    // join
                     " AND " + segmentFKCol + " = " + segmentPKCol +                                // join
                     " AND " + segmentPKCol + " = ?" +
                     " AND " + pendingStatusCol + " = 'H' " +
                     " AND (" +  transactionTypeCTCol + " = 'CC'" +
                     " AND " + complexChangeTypeCTCol + " = 'FaceIncrease')" +
                     " AND " + effectiveDateCol + " =  (SELECT MAX (" + effectiveDateCol +
                     ") FROM " + EDITTRX_TABLENAME + ", " + CLIENT_SETUP_TABLENAME + ", " +
                      CONTRACT_SETUP_TABLENAME + ", " + segmentTableName +
                     " WHERE " + clientSetupFKCol + " = " + clientSetupPKCol +                      // join
                     " AND " + contractSetupFKCol + " = " + contractSetupPKCol +                    // join
                     " AND " + segmentFKCol + " = " + segmentPKCol +                                // join
                     " AND " + segmentPKCol + " = ?" +
                     " AND " + pendingStatusCol + " = 'H' " +
                     " AND (" +  transactionTypeCTCol + " = 'CC'" +
                     " AND " + complexChangeTypeCTCol + " = 'FaceIncrease')" +
                     " AND " + effectiveDateCol + "<= ?)";

        Connection conn = null;

        PreparedStatement ps = null;

        try
        {
            conn = ConnectionFactory.getSingleton().getConnection(POOLNAME);

            ps = conn.prepareStatement(sql);

            ps.setLong(1, segmentPK);
            ps.setLong(2, segmentPK);
            ps.setDate(3, DBUtil.convertStringToDate(effectiveDate.getFormattedDate()));

            editTrxVOs = (EDITTrxVO[]) executeQuery(EDITTrxVO.class,
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

        return editTrxVOs;
    }


    public EDITTrxVO[] findBySegmentPK_TrxTypes_Status(long segmentPK, String[] transactionTypes, boolean includeChildren, List voExclusionList)
    {
        String clientSetupFKCol   = EDITTRX_DBTABLE.getDBColumn("ClientSetupFK").getFullyQualifiedColumnName();
           String clientSetupPKCol   = CLIENT_SETUP_DBTABLE.getDBColumn("ClientSetupPK").getFullyQualifiedColumnName();
           String contractSetupFKCol = CLIENT_SETUP_DBTABLE.getDBColumn("ContractSetupFK").getFullyQualifiedColumnName();
           String contractSetupPKCol = CONTRACT_SETUP_DBTABLE.getDBColumn("ContractSetupPK").getFullyQualifiedColumnName();
           String segmentFKCol       = CONTRACT_SETUP_DBTABLE.getDBColumn("SegmentFK").getFullyQualifiedColumnName();
           String transactionTypeCTCol = EDITTRX_DBTABLE.getDBColumn("TransactionTypeCT").getFullyQualifiedColumnName();
           String pendingStatusCol = EDITTRX_DBTABLE.getDBColumn("PendingStatus").getFullyQualifiedColumnName();
           String statusCol = EDITTRX_DBTABLE.getDBColumn("Status").getFullyQualifiedColumnName();

           String sqlIn = "";

           for (int i = 0; i < transactionTypes.length; i++)
           {
               if (i < transactionTypes.length - 1)
               {
                   sqlIn = sqlIn + "'" + transactionTypes[i] + "', ";
               }
               else
               {
                   sqlIn = sqlIn + "'" + transactionTypes[i] + "'";
               }
           }

           String sql = "SELECT " + EDITTRX_TABLENAME + ".* FROM " + EDITTRX_TABLENAME + ", " + CLIENT_SETUP_TABLENAME + ", " + CONTRACT_SETUP_TABLENAME +
                        " WHERE " + clientSetupFKCol + " = " + clientSetupPKCol +
                        " AND " + contractSetupFKCol + " = " + contractSetupPKCol +
                        " AND " + segmentFKCol + " = " + segmentPK +
                        " AND " + transactionTypeCTCol + " IN (" + sqlIn + ")" +
                        " AND " + pendingStatusCol + " IN ('L', 'H')" +
                        " AND " + statusCol + " IN ('N', 'A')";

        
           return (EDITTrxVO[]) executeQuery(EDITTrxVO.class,
                                              sql,
                                               POOLNAME,
                                                includeChildren,
                                                 voExclusionList);
       }

    public EDITTrxVO[] findGreatestBySegmentPK_TrxType_Status(long segmentPK, boolean includeChildren, List voExclusionList)
    {
        String clientSetupFKCol = EDITTRX_DBTABLE.getDBColumn("ClientSetupFK").getFullyQualifiedColumnName();
        String pendingStatusCol = EDITTRX_DBTABLE.getDBColumn("PendingStatus").getFullyQualifiedColumnName();
        String trxTypeCol = EDITTRX_DBTABLE.getDBColumn("TransactionTypeCT").getFullyQualifiedColumnName();
        String effectiveDateCol = EDITTRX_DBTABLE.getDBColumn("EffectiveDate").getFullyQualifiedColumnName();
        String statusCol = EDITTRX_DBTABLE.getDBColumn("Status").getFullyQualifiedColumnName();

        String clientSetupPKCol = CLIENT_SETUP_DBTABLE.getDBColumn("ClientSetupPK").getFullyQualifiedColumnName();
        String contractSetupFKCol = CLIENT_SETUP_DBTABLE.getDBColumn("ContractSetupFK").getFullyQualifiedColumnName();

        String contractSetupPKCol = CONTRACT_SETUP_DBTABLE.getDBColumn("ContractSetupPK").getFullyQualifiedColumnName();
        String segmentFKCol = CONTRACT_SETUP_DBTABLE.getDBColumn("SegmentFK").getFullyQualifiedColumnName();

        String sql = "SELECT " + EDITTRX_TABLENAME + ".* FROM " + EDITTRX_TABLENAME + ", " +
                     CLIENT_SETUP_TABLENAME + ", " + CONTRACT_SETUP_TABLENAME +
                     " WHERE " + clientSetupFKCol + " = " + clientSetupPKCol +
                     " AND " + contractSetupFKCol + " = " + contractSetupPKCol +
                     " AND " + segmentFKCol + " = " + segmentPK +
                     " AND " + pendingStatusCol + " = 'H' AND " + statusCol + "IN ('N','A')" +
                     " AND " + trxTypeCol + " = 'LC'" +
                     " AND " + effectiveDateCol + " = (SELECT MAX( " + effectiveDateCol + ") From " + EDITTRX_TABLENAME + ", " +
                     CLIENT_SETUP_TABLENAME + ", " + CONTRACT_SETUP_TABLENAME +
                     " WHERE " + clientSetupFKCol + " = " + clientSetupPKCol +
                     " AND " + contractSetupFKCol + " = " + contractSetupPKCol +
                     " AND " + segmentFKCol + " = " + segmentPK +
                     " AND " + pendingStatusCol + " = 'H' AND " + statusCol + "IN ('N','A')" +
                     " AND " + trxTypeCol + " = 'LC')";

        return (EDITTrxVO[]) executeQuery(EDITTrxVO.class,
                                          sql,
                                           POOLNAME,
                                            includeChildren,
                                             voExclusionList);
    }
    
    public EDITTrxVO[] findBySegmentPK_AND_PendingStatus(long segmentPK, String[] pendingStatuses) throws Exception
    {
        String clientSetupFKCol   = EDITTRX_DBTABLE.getDBColumn("ClientSetupFK").getFullyQualifiedColumnName();
        String pendingStatusCol   = EDITTRX_DBTABLE.getDBColumn("PendingStatus").getFullyQualifiedColumnName();

        String clientSetupPKCol   = CLIENT_SETUP_DBTABLE.getDBColumn("ClientSetupPK").getFullyQualifiedColumnName();
        String contractSetupFKCol = CLIENT_SETUP_DBTABLE.getDBColumn("ContractSetupFK").getFullyQualifiedColumnName();

        String contractSetupPKCol = CONTRACT_SETUP_DBTABLE.getDBColumn("ContractSetupPK").getFullyQualifiedColumnName();
        String segmentFKCol       = CONTRACT_SETUP_DBTABLE.getDBColumn("SegmentFK").getFullyQualifiedColumnName();

        String sqlIn = "";

        for (int i = 0; i < pendingStatuses.length; i++)
        {
            if (i < pendingStatuses.length - 1)
            {
                sqlIn = sqlIn + pendingStatuses[i] + "', '";
            }
            else
            {
                sqlIn = sqlIn + pendingStatuses[i] + "')";
            }
        }

        String sql = "SELECT " + EDITTRX_TABLENAME + ".* FROM " + EDITTRX_TABLENAME + ", " + CLIENT_SETUP_TABLENAME + ", " + CONTRACT_SETUP_TABLENAME +
                     " WHERE " + clientSetupFKCol + " = " + clientSetupPKCol +
                     " AND " + contractSetupFKCol + " = " + contractSetupPKCol +
                     " AND " + segmentFKCol + " = " + segmentPK +
                     " AND " + pendingStatusCol +" IN ('" + sqlIn;

        return (EDITTrxVO[]) executeQuery(EDITTrxVO.class,
                                           sql,
                                            POOLNAME,
                                             false,
                                              null);
    }
}
