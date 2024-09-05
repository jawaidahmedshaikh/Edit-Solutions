/*
 * (c) 2000-2004 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package client.dm.dao;

import edit.services.db.AbstractFastDAO;
import edit.services.db.ConnectionFactory;
import edit.services.db.DBTable;
import fission.utility.Util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class FastDAO extends AbstractFastDAO
{
    private final String POOLNAME;
    private final DBTable CLIENTDETAIL_DBTABLE;
    private final String CLIENTDETAIL_TABLENAME;


    public FastDAO()
    {
        POOLNAME               = ConnectionFactory.EDITSOLUTIONS_POOL;
        CLIENTDETAIL_DBTABLE   = DBTable.getDBTableForTable("ClientDetail");
        CLIENTDETAIL_TABLENAME = CLIENTDETAIL_DBTABLE.getFullyQualifiedTableName();
    }

    /**
     * Method added by sramamurthy 07/28/2004 OFAC-SOAP call
     * @return long[] of clientDetailPKs
     * @throws Exception
     */
    public long[] findALLClientDetailPK() throws Exception
    {
        Connection conn = null;
        Statement s = null;
        ResultSet rs = null;

        String clientDetailPKCol = CLIENTDETAIL_DBTABLE.getDBColumn("ClientDetailPK").getFullyQualifiedColumnName();

        String sql = "SELECT " + clientDetailPKCol + " FROM " + CLIENTDETAIL_TABLENAME;

        List clientDetailPKs = new ArrayList();

        try
        {
            conn = ConnectionFactory.getSingleton().getConnection(POOLNAME);

            s = conn.createStatement();

            rs = s.executeQuery(sql);

            while (rs.next())
            {
                clientDetailPKs.add(new Long(rs.getLong("ClientDetailPK")));
            }
        }
        catch (Exception e)
        {
            System.out.println(e);

            e.printStackTrace();

            throw e;
        }
        finally
        {
            if (rs != null) rs.close();
            if (s != null) s.close();
            if (conn != null) conn.close();
        }

        if (clientDetailPKs.size() == 0)
        {
            return null;
        }
        else
        {
            return Util.convertLongToPrim((Long[]) clientDetailPKs.toArray(new Long[clientDetailPKs.size()]));
        }
    }
}
