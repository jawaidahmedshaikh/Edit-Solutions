/*
 * Version 1.1  03/15/2002
 *
 * (c) 2000-2004 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package contract.dm.dao;

import edit.common.vo.ContractRequirementVO;
import edit.services.db.ConnectionFactory;
import edit.services.db.DAO;
import edit.services.db.DBTable;

import java.util.List;


public class ContractRequirementDAO extends DAO
{
    private final String POOLNAME;
    private final DBTable DBTABLE;
    private final String TABLENAME;


    public ContractRequirementDAO()
    {
        POOLNAME  = ConnectionFactory.EDITSOLUTIONS_POOL;
        DBTABLE   = DBTable.getDBTableForTable("ContractRequirement");
        TABLENAME = DBTABLE.getFullyQualifiedTableName();
    }

    public ContractRequirementVO[] findBySegmentFK(long segmentFK, boolean includeChildVOs, List voExclusionList)
    {
        String segmentFKCol = DBTABLE.getDBColumn("SegmentFK").getFullyQualifiedColumnName();

        String sql = "SELECT * FROM " + TABLENAME +
                     " WHERE " + segmentFKCol + " = " + segmentFK;


        return (ContractRequirementVO[]) executeQuery(ContractRequirementVO.class,
                                                       sql,
                                                        POOLNAME,
                                                         includeChildVOs,
                                                          voExclusionList);
    }
}