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

import edit.common.vo.OutSuspenseVO;
import edit.services.db.CRUD;
import edit.services.db.ConnectionFactory;
import edit.services.db.DAO;
import edit.services.db.DBTable;
import edit.services.db.VOClass;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class OutSuspenseDAO extends DAO
{
    private final String POOLNAME;
    private final DBTable DBTABLE;
    private final String TABLENAME;


    public OutSuspenseDAO()
    {
        POOLNAME  = ConnectionFactory.EDITSOLUTIONS_POOL;
        DBTABLE   = DBTable.getDBTableForTable("OutSuspense");
        TABLENAME = DBTABLE.getFullyQualifiedTableName();
    }

    public OutSuspenseVO[] findBySuspensePK(long suspensePK)
    {
        String suspenseFKCol = DBTABLE.getDBColumn("SuspenseFK").getFullyQualifiedColumnName();

        String sql = "SELECT * FROM " + TABLENAME +
                     " WHERE " + suspenseFKCol + " = " + suspensePK;

        return (OutSuspenseVO[]) executeQuery(OutSuspenseVO.class,
                                               sql,
                                                POOLNAME,
                                                 false,
                                                  null);
    }

    public OutSuspenseVO[] findByContractSetupPK(long contractSetupPK)
    {
        String contractSetupFKCol = DBTABLE.getDBColumn("ContractSetupFK").getFullyQualifiedColumnName();

        String sql = "SELECT * FROM " + TABLENAME +
                     " WHERE " + contractSetupFKCol + " = " + contractSetupPK;

        return (OutSuspenseVO[]) executeQuery(OutSuspenseVO.class,
                                               sql,
                                                POOLNAME,
                                                 false,
                                                  null);
    }
    
    public OutSuspenseVO[] findByContractSetupPK(long contractSetupPK, CRUD crud)
    {
        String contractSetupFKCol = DBTABLE.getDBColumn("ContractSetupFK").getFullyQualifiedColumnName();

        String sql = "SELECT * FROM " + TABLENAME +
                     " WHERE " + contractSetupFKCol + " = " + contractSetupPK;
        
        Connection conn = null;
        Statement s = null;
        ResultSet rs = null;
        
        List outSuspenseVOList = new ArrayList();
        OutSuspenseVO outSuspenseVO = null;
        
        try
        {
            conn = crud.getCrudConn();
            s = conn.createStatement();
            rs = s.executeQuery(sql);
            
            while (rs.next())
            {
                outSuspenseVO = (OutSuspenseVO) CRUD.populateVOFromResultSetRow(rs, VOClass.getVOClassMetaData(OutSuspenseVO.class));
                
                outSuspenseVOList.add(outSuspenseVO);
            }
        } 
        catch (Exception e)
        {
            System.out.println(e);
            e.printStackTrace();
            throw new RuntimeException (e);
        }
        finally
        {
            try
            {
                if (rs != null) rs.close();
                if (s != null) s.close();
                // DO NOT CLOSE THE CONNECTION THAT IS PASSED-IN
            }
            catch (SQLException e)
            {
                System.out.println(e);
                e.printStackTrace();
                throw new RuntimeException (e);
            }
        }

        return (OutSuspenseVO[]) outSuspenseVOList.toArray(new OutSuspenseVO[outSuspenseVOList.size()]);
    }
}
