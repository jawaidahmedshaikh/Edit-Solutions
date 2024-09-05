/*
 * RateTableDAO.java      Version 1.1  07/26/2001 .
 *
 * (c) 2000-2004 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */

package engine.dm.dao;

import edit.common.vo.RateTableVO;
import edit.services.db.ConnectionFactory;
import edit.services.db.DAO;
import edit.services.db.DBTable;

import java.util.List;



public class RateTableDAO extends DAO
{
    private final String POOLNAME;
    private final DBTable DBTABLE;
    private final String TABLENAME;


    public RateTableDAO()
    {
        POOLNAME  = ConnectionFactory.ENGINE_POOL;
        DBTABLE   = DBTable.getDBTableForTable("RateTable");
        TABLENAME = DBTABLE.getFullyQualifiedTableName();
    }

	public RateTableVO[] findAllRatesById(long tableId)
    {
        String tableKeysFKCol = DBTABLE.getDBColumn("TableKeysFK").getFullyQualifiedColumnName();
        String ageCol         = DBTABLE.getDBColumn("Age").getFullyQualifiedColumnName();
        String durationCol    = DBTABLE.getDBColumn("Duration").getFullyQualifiedColumnName();

        String sql = " SELECT * FROM " + TABLENAME +
                     " WHERE " + tableKeysFKCol + " = " + tableId +
                     " ORDER BY " + ageCol + ", " + durationCol;

        return (RateTableVO[]) executeQuery(RateTableVO.class,
                                             sql,
                                              POOLNAME,
                                               false,
                                                null);
	}

	public RateTableVO[] findAllRatesByIdAndOrderByAccessType(long tableId, String orderByAccessType)
    {
		String tableKeysFKCol = DBTABLE.getDBColumn("TableKeysFK").getFullyQualifiedColumnName();
        String ageCol         = DBTABLE.getDBColumn("Age").getFullyQualifiedColumnName();
        String durationCol    = DBTABLE.getDBColumn("Duration").getFullyQualifiedColumnName();

        String sql = null;

		if (orderByAccessType.equalsIgnoreCase("ISSUE"))
        {
            sql = " SELECT * FROM " + TABLENAME +
                  " WHERE " + tableKeysFKCol + " = " + tableId +
                  " ORDER BY " + ageCol + ", " + durationCol;
		}
		else if (orderByAccessType.equalsIgnoreCase("ATTAIN"))
        {
            sql = " SELECT * FROM " + TABLENAME +
                  " WHERE " + tableKeysFKCol + " = " + tableId +
                  " ORDER BY " + ageCol + ", " + durationCol;
		}
		else
        {
            sql = " SELECT * FROM " + TABLENAME +
                  " WHERE " + tableKeysFKCol + " = " + tableId;
		}

         return (RateTableVO[]) executeQuery(RateTableVO.class,
                                             sql,
                                              POOLNAME,
                                               false,
                                                null);
	}

	public RateTableVO[] findByTableKeysPK(long tableKeysPK, boolean includeChildVOs, List voExclusionList)
    {
        String tableKeysFKCol = DBTABLE.getDBColumn("TableKeysFK").getFullyQualifiedColumnName();

        String sql = " SELECT * FROM " + TABLENAME +
                     " WHERE " + tableKeysFKCol + " = " + tableKeysPK;


        return (RateTableVO[]) executeQuery(RateTableVO.class,
                                             sql,
                                              POOLNAME,
                                               includeChildVOs,
                                                voExclusionList);
	}
}