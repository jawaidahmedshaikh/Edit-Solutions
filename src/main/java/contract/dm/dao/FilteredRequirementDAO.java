/*
 * Version 1.1  03/15/2002
 *
 * (c) 2000-2004 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package contract.dm.dao;

import edit.common.vo.FilteredRequirementVO;
import edit.services.db.ConnectionFactory;
import edit.services.db.DAO;
import edit.services.db.DBTable;

import java.util.List;


public class FilteredRequirementDAO extends DAO
{
    private final String POOLNAME;
    private final DBTable DBTABLE;
    private final String TABLENAME;


    public FilteredRequirementDAO()
    {
        POOLNAME  = ConnectionFactory.EDITSOLUTIONS_POOL;
        DBTABLE   = DBTable.getDBTableForTable("FilteredRequirement");
        TABLENAME = DBTABLE.getFullyQualifiedTableName();
    }

    public FilteredRequirementVO[] findProductStructureFK(long productStructureFK, boolean includeChildVOs, List voExclusionList)
    {
        String productStructureFKCol = DBTABLE.getDBColumn("ProductStructureFK").getFullyQualifiedColumnName();

        String sql = "SELECT * FROM " + TABLENAME +
                     " WHERE " + productStructureFKCol + " = "  + productStructureFK;

        return (FilteredRequirementVO[]) executeQuery(FilteredRequirementVO.class,
                                                       sql,
                                                        POOLNAME,
                                                         includeChildVOs,
                                                          voExclusionList);
    }

    public FilteredRequirementVO[] findProductStructureFKAndRequirementPK(long productStructureFK,
                                                                           long requirementPK,
                                                                            boolean includeChildVOs,
                                                                             List voExclusionList)
    {
        String productStructureFKCol = DBTABLE.getDBColumn("ProductStructureFK").getFullyQualifiedColumnName();
        String requirementFK         = DBTABLE.getDBColumn("RequirementFK").getFullyQualifiedColumnName();

        String sql = "SELECT * FROM " + TABLENAME +
                     " WHERE " + productStructureFKCol + " = " + productStructureFK +
                     " AND " + requirementFK + " = " + requirementPK;

        return (FilteredRequirementVO[]) executeQuery(FilteredRequirementVO.class,
                                                       sql,
                                                        POOLNAME,
                                                         includeChildVOs,
                                                          voExclusionList);
    }

    public FilteredRequirementVO[] findByPK(long filteredRequirementPK, boolean includeChildVOs, List voExclusionList)
    {
        String filteredRequirementPKCol = DBTABLE.getDBColumn("FilteredRequirementPK").getFullyQualifiedColumnName();

        String sql = "SELECT * FROM " + TABLENAME +
                     " WHERE " + filteredRequirementPKCol + " = " + filteredRequirementPK;

        return (FilteredRequirementVO[]) executeQuery(FilteredRequirementVO.class,
                                                       sql,
                                                        POOLNAME,
                                                         includeChildVOs,
                                                          voExclusionList);
    }

    public FilteredRequirementVO[] findProductStructureAndManualInd(long productStructureFK,
                                                                     boolean includeChildVOs,
                                                                      List voExclusionList)
    {
        DBTable requirementDBTable = DBTable.getDBTableForTable("Requirement");

        String requirementTable = requirementDBTable.getFullyQualifiedTableName();

        String productStructureFKCol = DBTABLE.getDBColumn("ProductStructureFK").getFullyQualifiedColumnName();
        String requirementFKCol      = DBTABLE.getDBColumn("RequirementFK").getFullyQualifiedColumnName();

        String manualIndCol     = requirementDBTable.getDBColumn("ManualInd").getFullyQualifiedColumnName();
        String requirementPKCol = requirementDBTable.getDBColumn("RequirementPK").getFullyQualifiedColumnName();

        String sql = "SELECT " + TABLENAME + ".* FROM " + TABLENAME + ", " + requirementTable +
                     " WHERE " + productStructureFKCol + " = " + productStructureFK +
                     " AND " + manualIndCol + " = 'N'" +
                     " AND " + requirementFKCol + " = " + requirementPKCol;

        return (FilteredRequirementVO[]) executeQuery(FilteredRequirementVO.class,
                                                       sql,
                                                        POOLNAME,
                                                         includeChildVOs,
                                                          voExclusionList);
    }

    public FilteredRequirementVO[] findByProductStructurePK_AND_RequirementId(long productStructurePK,
                                                                            String requirementId)
    {
        DBTable requirementDBTable = DBTable.getDBTableForTable("Requirement");
        String requirementTable = requirementDBTable.getFullyQualifiedTableName();

        String productStructureFKCol = DBTABLE.getDBColumn("ProductStructureFK").getFullyQualifiedColumnName();
        String requirementIdCol = requirementDBTable.getDBColumn("RequirementId").getFullyQualifiedColumnName();

        String requirementPKCol = requirementDBTable.getDBColumn("RequirementPK").getFullyQualifiedColumnName();
        String requirementFKCol = DBTABLE.getDBColumn("RequirementFK").getFullyQualifiedColumnName();

        String sql = " SELECT " + TABLENAME + ".* FROM " + TABLENAME +
                     " INNER JOIN " + requirementTable +
                     " ON " +  requirementPKCol + " = " + requirementFKCol +
                     " WHERE " + productStructureFKCol + " = " + productStructurePK +
                     " AND " + requirementIdCol + " = '" + requirementId + "'";

        return (FilteredRequirementVO[]) executeQuery(FilteredRequirementVO.class,
                                                       sql,
                                                        POOLNAME,
                                                         false,
                                                          null);
    }
}