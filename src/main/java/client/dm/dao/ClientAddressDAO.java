/*
 * Created by IntelliJ IDEA.
 * User: gfrosti
 * Date: Jul 17, 2003
 * Time: 3:29:36 PM
 *
 * (c) 2000-2004 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package client.dm.dao;

import edit.common.vo.ClientAddressVO;
import edit.common.vo.EDITTrxVO;
import edit.common.*;
import edit.services.db.ConnectionFactory;
import edit.services.db.DAO;
import edit.services.db.DBTable;
import edit.services.db.DBUtil;

import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class ClientAddressDAO extends DAO
{
    private final String POOLNAME;
    private final DBTable DBTABLE;
    private final String TABLENAME;


    public ClientAddressDAO()
    {
        POOLNAME  = ConnectionFactory.EDITSOLUTIONS_POOL;
        DBTABLE   = DBTable.getDBTableForTable("ClientAddress");
        TABLENAME = DBTABLE.getFullyQualifiedTableName();
    }

    public ClientAddressVO[] findByClientDetailPK_AND_AddressTypeCT(long clientDetailPK,
                                                                     String addressTypeCT,
                                                                      boolean includeChildVOs,
                                                                       List voExclusionList)
    {
        String clientDetailFKCol = DBTABLE.getDBColumn("ClientDetailFK").getFullyQualifiedColumnName();
        String addressTypeCTCol  = DBTABLE.getDBColumn("AddressTypeCT").getFullyQualifiedColumnName();

        String sql = "SELECT * FROM " + TABLENAME +
                     " WHERE " + clientDetailFKCol + " = " + clientDetailPK +
                     " AND " + addressTypeCTCol + " = '" + addressTypeCT + "'";

        return (ClientAddressVO[]) executeQuery(ClientAddressVO.class,
                                                 sql,
                                                  POOLNAME,
                                                   includeChildVOs,
                                                    voExclusionList);
    }

    public ClientAddressVO[] findAllActiveByClientDetailPK(long clientDetailPK)
    {
        ClientAddressVO[] clientAddressVOs = null;

        String clientDetailFKCol = DBTABLE.getDBColumn("ClientDetailFK").getFullyQualifiedColumnName();
        String terminationDateCol = DBTABLE.getDBColumn("TerminationDate").getFullyQualifiedColumnName();

        String sql = "SELECT * FROM " + TABLENAME +
                     " WHERE " + clientDetailFKCol + " = " + clientDetailPK +
                     " AND " + terminationDateCol + " = ?";

        Connection conn = null;

        PreparedStatement ps = null;

        try
        {
            conn = ConnectionFactory.getSingleton().getConnection(POOLNAME);

            ps = conn.prepareStatement(sql);

            ps.setDate(1, DBUtil.convertStringToDate(EDITDate.DEFAULT_MAX_DATE));

            clientAddressVOs = (ClientAddressVO[]) executeQuery(ClientAddressVO.class,
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

        return clientAddressVOs;
    }

    public ClientAddressVO[] findByPK(long clientAddressPK, boolean includeChildVOs, List voExclusionList)
    {
        String clientAddressPKCol = DBTABLE.getDBColumn("ClientAddressPK").getFullyQualifiedColumnName();

        String sql = "SELECT * FROM " + TABLENAME +
                     " WHERE " + clientAddressPKCol + " = " + clientAddressPK;

        return (ClientAddressVO[]) executeQuery(ClientAddressVO.class,
                                                 sql,
                                                  POOLNAME,
                                                   includeChildVOs,
                                                    voExclusionList);
    }
}