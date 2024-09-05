/*
 * Version 1.1  03/15/2002
 *
 * (c) 2000-2004 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package contract.dm.dao;

import edit.common.vo.InvestmentAllocationVO;
import edit.services.db.ConnectionFactory;
import edit.services.db.DAO;
import edit.services.db.DBTable;

import java.util.List;


public class InvestmentAllocationDAO extends DAO
{
    private final String POOLNAME;
    private final DBTable DBTABLE;
    private final String TABLENAME;


    public InvestmentAllocationDAO()
    {
        POOLNAME  = ConnectionFactory.EDITSOLUTIONS_POOL;
        DBTABLE   = DBTable.getDBTableForTable("InvestmentAllocation");
        TABLENAME = DBTABLE.getFullyQualifiedTableName();
    }

    public InvestmentAllocationVO[] findByInvestmentAllocationPK(long investmentAllocationPK,
                                                                  boolean includeChildVOs,
                                                                   List voExclusionList)
    {
        String investmentAllocationPKCol = DBTABLE.getDBColumn("InvestmentAllocationPK").getFullyQualifiedColumnName();

        String sql = "SELECT * FROM " + TABLENAME +
                     " WHERE " + investmentAllocationPKCol + " = " + investmentAllocationPK;

        return (InvestmentAllocationVO[]) executeQuery(InvestmentAllocationVO.class,
                                                        sql,
                                                         POOLNAME,
                                                          includeChildVOs,
                                                           voExclusionList);
    }

    public InvestmentAllocationVO[] findByInvestmentPK(long investmentFK, boolean includeChildVOs, List voExclusionList)
    {
        String investmentFKCol = DBTABLE.getDBColumn("InvestmentFK").getFullyQualifiedColumnName();

        String sql = "SELECT * FROM " + TABLENAME +
                     " WHERE " + investmentFKCol + " = " + investmentFK;

        return (InvestmentAllocationVO[]) executeQuery(InvestmentAllocationVO.class,
                                                        sql,
                                                         POOLNAME,
                                                          includeChildVOs,
                                                           voExclusionList);
    }
}