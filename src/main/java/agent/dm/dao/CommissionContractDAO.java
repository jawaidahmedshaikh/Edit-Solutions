/*
 * Created by IntelliJ IDEA.
 * User: dlataill
 * Date: Oct 17, 2003
 * Time: 12:07:13 PM
 *
 * (c) 2000-2004 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */

package agent.dm.dao;

import edit.common.vo.CommissionContractVO;
import edit.services.db.ConnectionFactory;
import edit.services.db.DAO;
import edit.services.db.DBTable;

public class CommissionContractDAO extends DAO
{
    private final String POOLNAME;
    private final DBTable DBTABLE;
    private final String TABLENAME;



    public CommissionContractDAO()
    {
        POOLNAME = ConnectionFactory.EDITSOLUTIONS_POOL;
        DBTABLE = DBTable.getDBTableForTable("CommissionContract");
        TABLENAME = DBTABLE.getFullyQualifiedTableName();
    }

    public CommissionContractVO[] getAllCommissionContracts()
    {
        String sql = "SELECT * FROM " + TABLENAME;

        return (CommissionContractVO[]) executeQuery(CommissionContractVO.class,
                                                      sql,
                                                       POOLNAME,
                                                        false,
                                                         null);
    }

    public CommissionContractVO[] getCommissionContractByPK(long commContractPK)
    {
        String commissionContractPKCol = DBTABLE.getDBColumn("CommissionContractPK").getFullyQualifiedColumnName();

        String sql = "SELECT * FROM " + TABLENAME +
                     " WHERE " + commissionContractPKCol + " = " + commContractPK;

        return (CommissionContractVO[]) executeQuery(CommissionContractVO.class,
                                                      sql,
                                                       POOLNAME,
                                                        false,
                                                         null);
    }

    public CommissionContractVO[] findByContractCode(String contractCode)
    {
        String contractCodeCol = DBTABLE.getDBColumn("ContractCode").getFullyQualifiedColumnName();

        String sql = "SELECT * FROM " + TABLENAME +
                     " WHERE " + contractCodeCol + " = '" + contractCode + "'";

        return (CommissionContractVO[]) executeQuery(CommissionContractVO.class,
                                                      sql,
                                                       POOLNAME,
                                                        false,
                                                         null);
    }
}
