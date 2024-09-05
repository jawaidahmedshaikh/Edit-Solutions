/*
 * Created by IntelliJ IDEA.
 * User: dlataill
 * Date: Feb 6, 2002
 * Time: 4:37:32 PM
 *
 * (c) 2000-2004 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package accounting.dm.dao;

import edit.common.vo.ElementStructureVO;
import edit.services.db.ConnectionFactory;
import edit.services.db.DAO;
import edit.services.db.DBTable;


public class ElementStructureDAO extends DAO
{
    private final String POOLNAME;
    private final DBTable DBTABLE;
    private final String TABLENAME;


    public ElementStructureDAO()
    {
        POOLNAME  = ConnectionFactory.EDITSOLUTIONS_POOL;
        DBTABLE   = DBTable.getDBTableForTable("ElementStructure");
        TABLENAME = DBTABLE.getFullyQualifiedTableName();
    }

    public ElementStructureVO[] findStructureByElementStructureId(long elementStructureId)
    {
        String elementStructurePKCol = DBTABLE.getDBColumn("ElementStructurePK").getFullyQualifiedColumnName();

        String sql = "SELECT * FROM " + TABLENAME +
                     " WHERE " + elementStructurePKCol + " = " + elementStructureId;

        return (ElementStructureVO[]) executeQuery(ElementStructureVO.class,
                                                    sql,
                                                     POOLNAME,
                                                      false,
                                                       null);
    }

    public ElementStructureVO[] findStructureByNames(long elementId,
                                                      String memoCode,
                                                       int certainPeriod,
                                                        String qualNonQual,
                                                         long fundId,
                                                           long chargeCodeFK)
    {
        String elementFKCol     = DBTABLE.getDBColumn("ElementFK").getFullyQualifiedColumnName();
        String memoCodeCol      = DBTABLE.getDBColumn("MemoCode").getFullyQualifiedColumnName();
        String certainPeriodCol = DBTABLE.getDBColumn("CertainPeriod").getFullyQualifiedColumnName();
        String qualNonQualCTCol = DBTABLE.getDBColumn("QualNonQualCT").getFullyQualifiedColumnName();
        String fundFKCol        = DBTABLE.getDBColumn("FundFK").getFullyQualifiedColumnName();
        String chargeCodeFKCol  = DBTABLE.getDBColumn("ChargeCodeFK").getFullyQualifiedColumnName();

        String sql = "SELECT * FROM " + TABLENAME +
                     " WHERE " + elementFKCol + " = " + elementId +
                     " AND " + memoCodeCol + " = '" + memoCode + "'" +
                     " AND " + certainPeriodCol + " = " + certainPeriod +
                     " AND " + qualNonQualCTCol + " = '" + qualNonQual + "'";


        if (fundId == 0)
        {
            sql = sql + " AND " + fundFKCol + " IS NULL";
        }
        else
        {
            sql = sql + " AND " + fundFKCol + " = " + fundId;
        }

        if (chargeCodeFK == 0)
        {
            sql = sql + " AND " + chargeCodeFKCol + " IS NULL";
        }
        else
        {
            sql = sql + " AND " + chargeCodeFKCol + " = " +  chargeCodeFK;
        }

        return (ElementStructureVO[]) executeQuery(ElementStructureVO.class,
                                                    sql,
                                                     POOLNAME,
                                                      false,
                                                       null);
    }

    public ElementStructureVO[] findAllNames()
    {
        String sql = "SELECT * FROM " + TABLENAME;

        return (ElementStructureVO[]) executeQuery(ElementStructureVO.class,
                                                    sql,
                                                     POOLNAME,
                                                      false,
                                                       null);

    }

    public ElementStructureVO[] findStructureByElementPK(long elementPK)
    {
        String elementFKCol = DBTABLE.getDBColumn("ElementFK").getFullyQualifiedColumnName();

        String sql = "SELECT * FROM " + TABLENAME +
                     " WHERE " + elementFKCol + " = " + elementPK;

        return (ElementStructureVO[]) executeQuery(ElementStructureVO.class,
                                                    sql,
                                                     POOLNAME,
                                                      false,
                                                       null);
    }
}