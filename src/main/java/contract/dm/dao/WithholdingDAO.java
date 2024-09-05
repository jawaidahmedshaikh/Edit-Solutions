/*
 * Version 1.1  03/15/2002
 *
 * (c) 2000-2004 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package contract.dm.dao;

import edit.common.vo.WithholdingVO;
import edit.services.db.ConnectionFactory;
import edit.services.db.DAO;
import edit.services.db.DBTable;

import java.util.List;


public class WithholdingDAO extends DAO
{
    private final String POOLNAME;
    private final DBTable DBTABLE;
    private final String TABLENAME;


    public WithholdingDAO()
    {
        POOLNAME  = ConnectionFactory.EDITSOLUTIONS_POOL;
        DBTABLE   = DBTable.getDBTableForTable("Withholding");
        TABLENAME = DBTABLE.getFullyQualifiedTableName();
    }

    public WithholdingVO[] findByWithholdingPK(long withholdingPK, boolean includeChildVOs, List voExclusionList)
    {
        String withholdingPKCol = DBTABLE.getDBColumn("WithholdingPK").getFullyQualifiedColumnName();

        String sql = " SELECT * FROM " + TABLENAME +
                     " WHERE " + withholdingPKCol + " = " + withholdingPK;

        return (WithholdingVO[]) executeQuery(WithholdingVO.class,
                                          sql,
                                           POOLNAME,
                                            false,
                                                null);
     }

    public WithholdingVO[] findByContractClientPK(long contractClientPK, boolean includeChildVOs, List voExclusionList)
    {
        String contractClientFKCol = DBTABLE.getDBColumn("ContractClientFK").getFullyQualifiedColumnName();

        String sql = " SELECT * FROM " + TABLENAME +
                     " WHERE " + contractClientFKCol + " = " + contractClientPK;

        return (WithholdingVO[]) executeQuery(WithholdingVO.class,
                                          sql,
                                           POOLNAME,
                                            false,
                                                null);
     }
    public WithholdingVO[] findByPK(long withholdingPK)
    {
        String withholdingPKCol = DBTABLE.getDBColumn("WithholdingPK").getFullyQualifiedColumnName();

        String sql = " SELECT * FROM " + TABLENAME +
                     " WHERE " + withholdingPKCol + " = " + withholdingPK;

        return (WithholdingVO[]) executeQuery(WithholdingVO.class,
                                          sql,
                                           POOLNAME,
                                            false,
                                                null);
     }
}