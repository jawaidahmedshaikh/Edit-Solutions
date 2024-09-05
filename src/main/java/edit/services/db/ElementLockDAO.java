/*
 * Created by IntelliJ IDEA.
 * User: gfrosti
 * Date: Nov 11, 2002
 * Time: 12:45:38 PM
 *
 * (c) 2000-2004 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package edit.services.db;


import edit.common.vo.ElementLockVO;

import java.util.List;



public class ElementLockDAO extends DAO
{
    private final String POOLNAME;
    private final DBTable DBTABLE;
    private final String TABLENAME;


    public ElementLockDAO()
    {
        POOLNAME  = ConnectionFactory.EDITSOLUTIONS_POOL;
        DBTABLE   = DBTable.getDBTableForTable("ElementLock");
        TABLENAME = DBTABLE.getFullyQualifiedTableName();
    }

    public ElementLockVO[] findByElementPK(long elementPK, boolean includeChildVOs, List voExclusionList)
    {
        String elementFKCol = DBTABLE.getDBColumn("ElementFK").getFullyQualifiedColumnName();

        String sql = " SELECT * FROM " + TABLENAME +
                     " WHERE " + elementFKCol + " = " + elementPK;

        return (ElementLockVO[]) executeQuery(ElementLockVO.class,
                                           sql,
                                            POOLNAME,
                                             includeChildVOs,
                                              voExclusionList);
    }

    public ElementLockVO[] findByElementLockPK(long elementLockPK, boolean includeChildVOs, List voExclusionList)
    {
        String elementLockPKCol = DBTABLE.getDBColumn("ElementLockPK").getFullyQualifiedColumnName();

        String sql = " SELECT * FROM " + TABLENAME +
                     " WHERE " + elementLockPKCol + " = " + elementLockPK;

        return (ElementLockVO[]) executeQuery(ElementLockVO.class,
                                           sql,
                                            POOLNAME,
                                             includeChildVOs,
                                              voExclusionList);
    }

    public ElementLockVO[] findByUsername(String username, boolean includeChildVOs, List voExclusionList)
    {
        String userNameCol = DBTABLE.getDBColumn("Username").getFullyQualifiedColumnName();

        String sql = " SELECT * FROM " + TABLENAME +
                     " WHERE " + userNameCol + " = '" + username + "'";

        return (ElementLockVO[]) executeQuery(ElementLockVO.class,
                                           sql,
                                            POOLNAME,
                                             includeChildVOs,
                                              voExclusionList);
    }
}