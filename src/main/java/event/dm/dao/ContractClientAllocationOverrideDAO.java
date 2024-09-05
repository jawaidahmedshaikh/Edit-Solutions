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

import edit.common.vo.ContractClientAllocationOvrdVO;
import edit.services.db.ConnectionFactory;
import edit.services.db.DAO;
import edit.services.db.DBTable;


public class ContractClientAllocationOverrideDAO extends DAO
{
    private final String POOLNAME;
    private final DBTable DBTABLE;
    private final String TABLENAME;


    public ContractClientAllocationOverrideDAO()
    {
        POOLNAME  = ConnectionFactory.EDITSOLUTIONS_POOL;
        DBTABLE   = DBTable.getDBTableForTable("ContractClientAllocationOvrd");
        TABLENAME = DBTABLE.getFullyQualifiedTableName();
    }

    public ContractClientAllocationOvrdVO[] findByClientSetupPK(long clientSetupPK) throws Exception
    {
        String clientSetupFKCol = DBTABLE.getDBColumn("ClientSetupFK").getFullyQualifiedColumnName();

        String sql = "SELECT * FROM " + TABLENAME +
                     " WHERE " + clientSetupFKCol + " = " + clientSetupPK;

        return (ContractClientAllocationOvrdVO[]) executeQuery(ContractClientAllocationOvrdVO.class,
                                                                sql,
                                                                 POOLNAME,
                                                                  false,
                                                                   null);
    }
}
