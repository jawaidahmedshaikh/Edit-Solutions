/*
 * Version 1.1  03/15/2002
 *
 * (c) 2000-2004 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
*/
package contract.dm.dao;

import edit.common.vo.RequirementVO;
import edit.services.db.ConnectionFactory;
import edit.services.db.DAO;
import edit.services.db.DBTable;

import java.util.List;


public class RequirementDAO extends DAO
{
    private final String POOLNAME;
    private final DBTable DBTABLE;
    private final String TABLENAME;


    public RequirementDAO()
    {
        POOLNAME  = ConnectionFactory.EDITSOLUTIONS_POOL;
        DBTABLE   = DBTable.getDBTableForTable("Requirement");
        TABLENAME = DBTABLE.getFullyQualifiedTableName();
    }

    public RequirementVO[] findByPK(long requirementPK, boolean includeChildVOs, List voExclusionList)
    {
        String requirementPKCol = DBTABLE.getDBColumn("RequirementPK").getFullyQualifiedColumnName();

        String sql = "SELECT * FROM " + TABLENAME +
                     " WHERE " + requirementPKCol + " = " + requirementPK;

        return (RequirementVO[]) executeQuery(RequirementVO.class,
                                               sql,
                                                POOLNAME,
                                                 includeChildVOs,
                                                  voExclusionList);
    }

    public RequirementVO[] findByManualInd(String manualInd, boolean includeChildVOs, List voExclusionList)
    {
        String manualIndCol = DBTABLE.getDBColumn("ManualInd").getFullyQualifiedColumnName();

        String sql = "SELECT * FROM " + TABLENAME +
                     " WHERE " + manualIndCol + " = '" + manualInd + "'";

        return (RequirementVO[]) executeQuery(RequirementVO.class,
                                               sql,
                                                POOLNAME,
                                                 includeChildVOs,
                                                  voExclusionList);
    }

    public RequirementVO[] findAllRequirements(boolean includeChildVOs, List voExclusionList)
    {
        String sql = "SELECT * FROM " + TABLENAME;

        return (RequirementVO[]) executeQuery(RequirementVO.class,
                                               sql,
                                                POOLNAME,
                                                 includeChildVOs,
                                                  voExclusionList);
    }

    public RequirementVO[] findByProductStructure(long productStructureFK, boolean includeChildVOs, List voExclusionList)
    {
        DBTable filteredRequirementDBTable = DBTable.getDBTableForTable("FilteredRequirement");

        String filteredRequirementTable = filteredRequirementDBTable.getFullyQualifiedTableName();


        String productStructureFKCol = filteredRequirementDBTable.getDBColumn("ProductStructureFK").getFullyQualifiedColumnName();
        String requirementFKCol      = filteredRequirementDBTable.getDBColumn("RequirementFK").getFullyQualifiedColumnName();

        String requirementPKCol = DBTABLE.getDBColumn("RequirementPK").getFullyQualifiedColumnName();


        String sql = "SELECT " + TABLENAME + ".* FROM " + TABLENAME + ", " + filteredRequirementTable +
                     " WHERE " + productStructureFKCol + " = " + productStructureFK +
                     " AND " + requirementFKCol +  " = " + requirementPKCol;

        return (RequirementVO[]) executeQuery(RequirementVO.class,
                                               sql,
                                                POOLNAME,
                                                 includeChildVOs,
                                                  voExclusionList);
    }

    public RequirementVO[] findByFilteredRequirementPK(long filteredRequirementPK, boolean includeChildVOs, List voExclusionList)
    {
        DBTable filteredRequirementDBTable = DBTable.getDBTableForTable("FilteredRequirement");

        String filteredRequirementTable = filteredRequirementDBTable.getFullyQualifiedTableName();


        String filteredRequirementPKCol = filteredRequirementDBTable.getDBColumn("FilteredRequirementPK").getFullyQualifiedColumnName();
        String requirementFKCol         = filteredRequirementDBTable.getDBColumn("RequirementFK").getFullyQualifiedColumnName();

        String requirementPKCol = DBTABLE.getDBColumn("RequirementPK").getFullyQualifiedColumnName();


        String sql = "SELECT " + TABLENAME + ".* FROM " + TABLENAME + ", " + filteredRequirementTable +
                     " WHERE " + filteredRequirementPKCol + " = " + filteredRequirementPK +
                     " AND " + requirementFKCol + " = " + requirementPKCol;


        return (RequirementVO[]) executeQuery(RequirementVO.class,
                                               sql,
                                                POOLNAME,
                                                 includeChildVOs,
                                                  voExclusionList);
    }

    public RequirementVO[] findByProductStructurePKAndManualInd(long productStructurePK,
                                                                 String manualInd,
                                                                  boolean includeChildVOs,
                                                                   List voExclusionList)
    {
        DBTable filteredRequirementDBTable = DBTable.getDBTableForTable("FilteredRequirement");

        String filteredRequirementTable = filteredRequirementDBTable.getFullyQualifiedTableName();


        String manualIndCol     = DBTABLE.getDBColumn("ManualInd").getFullyQualifiedColumnName();
        String requirementPKCol = DBTABLE.getDBColumn("RequirementPK").getFullyQualifiedColumnName();

        String requirementFKCol      = filteredRequirementDBTable.getDBColumn("RequirementFK").getFullyQualifiedColumnName();
        String productStructureFKCol = filteredRequirementDBTable.getDBColumn("ProductStructureFK").getFullyQualifiedColumnName();


        String sql = "SELECT * FROM " + TABLENAME +
                     " WHERE " + manualIndCol + " = '" + manualInd + "'" +
                     " AND " + requirementPKCol + " IN (SELECT " + requirementFKCol + " FROM " + filteredRequirementTable +
                     " WHERE " + productStructureFKCol + " = " + productStructurePK + ")";

        return (RequirementVO[]) executeQuery(RequirementVO.class,
                                               sql,
                                                POOLNAME,
                                                 includeChildVOs,
                                                  voExclusionList);
    }

    public RequirementVO[] findByRequirementId(String requirementId)
    {
        String requirementIdCol = DBTABLE.getDBColumn("RequirementId").getFullyQualifiedColumnName();

        String sql = "SELECT * FROM " + TABLENAME +
                     " WHERE " + requirementIdCol + " = '" + requirementId + "'";

        return (RequirementVO[]) executeQuery(RequirementVO.class,
                                               sql,
                                                POOLNAME,
                                                 false,
                                                  null);
    }

    public RequirementVO[] findBySegmentPK_And_RequirementId(long segmentPK, String requirementId)
    {
        DBTable filteredRequirementDBTable = DBTable.getDBTableForTable("FilteredRequirement");
        String filteredRequirementTable = filteredRequirementDBTable.getFullyQualifiedTableName();

        DBTable contractRequirementDBTable = DBTable.getDBTableForTable("ContractRequirement");
        String contractRequirementTable = contractRequirementDBTable.getFullyQualifiedTableName();

        String requirementPKCol = DBTABLE.getDBColumn("RequirementPK").getFullyQualifiedColumnName();
        String requirementFKCol = filteredRequirementDBTable.getDBColumn("RequirementFK").getFullyQualifiedColumnName();

        String filteredRequirementPKCol = filteredRequirementDBTable.getDBColumn("FilteredRequirementPK").getFullyQualifiedColumnName();
        String filteredRequirementFKCol = contractRequirementDBTable.getDBColumn("FilteredRequirementFK").getFullyQualifiedColumnName();

        String requirementIdCol = DBTABLE.getDBColumn("RequirementId").getFullyQualifiedColumnName();
        String segmentFKCol = contractRequirementDBTable.getDBColumn("SegmentFK").getFullyQualifiedColumnName();

        String sql = " SELECT " + TABLENAME + ".* FROM " + TABLENAME +
                     " INNER JOIN " + filteredRequirementTable +
                     " ON " + requirementPKCol + " = " + requirementFKCol +
                     " INNER JOIN " + contractRequirementTable +
                     " ON " + filteredRequirementPKCol + " = " + filteredRequirementFKCol +
                     " WHERE " + requirementIdCol + " = '" + requirementId + "'" +
                     " AND " + segmentFKCol + " = " + segmentPK;

        return (RequirementVO[]) executeQuery(RequirementVO.class,
                                               sql,
                                                POOLNAME,
                                                 false,
                                                  null);
    }
}