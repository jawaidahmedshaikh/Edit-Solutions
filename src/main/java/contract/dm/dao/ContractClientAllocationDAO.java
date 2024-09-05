/*
 * Version 1.1  03/15/2002
 *
 * (c) 2000-2004 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package contract.dm.dao;

import edit.common.vo.ContractClientAllocationVO;
import edit.services.db.ConnectionFactory;
import edit.services.db.DAO;
import edit.services.db.DBTable;

import java.util.List;



public class ContractClientAllocationDAO extends DAO
{
    private final String POOLNAME;
    private final DBTable DBTABLE;
    private final String TABLENAME;


    public ContractClientAllocationDAO()
    {
        POOLNAME  = ConnectionFactory.EDITSOLUTIONS_POOL;
        DBTABLE   = DBTable.getDBTableForTable("ContractClientAllocation");
        TABLENAME = DBTABLE.getFullyQualifiedTableName();
    }

    public ContractClientAllocationVO[] findByContractClientAllocationPK(long contractClientAllocationPK,
                                                                          boolean includeChildVOs,
                                                                           List voExclusionList)
    {
        String contractClientAllocationPKCol = DBTABLE.getDBColumn("ContractClientAllocationPK").getFullyQualifiedColumnName();

        String sql = "SELECT * FROM " + TABLENAME +
                     " WHERE " + contractClientAllocationPKCol + " = " + contractClientAllocationPK;

        return (ContractClientAllocationVO[]) executeQuery(ContractClientAllocationVO.class,
                                                            sql,
                                                             POOLNAME,
                                                              includeChildVOs,
                                                               voExclusionList);
     }

    public ContractClientAllocationVO[] findByContractClientPKAndOverrideStatus(long contractClientPK,
                                                                                 String overrideStatus,
                                                                                  boolean includeChildVOs,
                                                                                   List voExclusionList)
    {
        String contractClientFKCol = DBTABLE.getDBColumn("ContractClientFK").getFullyQualifiedColumnName();
        String overrideStatusCol   = DBTABLE.getDBColumn("OverrideStatus").getFullyQualifiedColumnName();

        String sql = "SELECT * FROM " + TABLENAME +
                     " WHERE " + contractClientFKCol + " = " + contractClientPK +
                     " AND " + overrideStatusCol + " = '" + overrideStatus + "'";

        return (ContractClientAllocationVO[]) executeQuery(ContractClientAllocationVO.class,
                                                            sql,
                                                             POOLNAME,
                                                              includeChildVOs,
                                                               voExclusionList);
    }

    public ContractClientAllocationVO[] findByContractClientPKAndAllocPct(long contractClientPK,
                                                                           double allocationPct)
    {
        String contractClientFKCol  = DBTABLE.getDBColumn("ContractClientFK").getFullyQualifiedColumnName();
        String allocationPercentCol = DBTABLE.getDBColumn("AllocationPercent").getFullyQualifiedColumnName();

        String sql = "SELECT * FROM " + TABLENAME +
                     " WHERE " + contractClientFKCol + " = " + contractClientPK +
                     " AND " + allocationPercentCol + " = " + allocationPct;

        return (ContractClientAllocationVO[]) executeQuery(ContractClientAllocationVO.class,
                                                            sql,
                                                             POOLNAME,
                                                              false,
                                                                null);
    }

   public ContractClientAllocationVO[] findByPK(long contractClientAllocationPK)
    {
        String contractClientAllocationPKCol = DBTABLE.getDBColumn("ContractClientAllocationPK").getFullyQualifiedColumnName();

        String sql = "SELECT * FROM " + TABLENAME +
                     " WHERE " + contractClientAllocationPKCol + " = " + contractClientAllocationPK;

        return (ContractClientAllocationVO[]) executeQuery(ContractClientAllocationVO.class,
                                                            sql,
                                                             POOLNAME,
                                                              false,
                                                               null);
     }
}