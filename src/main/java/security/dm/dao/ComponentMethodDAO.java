/*
 * Created by IntelliJ IDEA.
 * User: gfrosti
 * Date: Jan 20, 2004
 * Time: 11:45:26 AM
 *
 * (c) 2000-2004 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */

package security.dm.dao;

import edit.common.vo.ComponentMethodVO;
import edit.services.db.ConnectionFactory;
import edit.services.db.DAO;
import edit.services.db.DBTable;


public class ComponentMethodDAO extends DAO
{
    private final String POOLNAME;
    private final DBTable DBTABLE;
    private final String TABLENAME;

    public ComponentMethodDAO()
    {
        POOLNAME  = ConnectionFactory.SECURITY_POOL;
        DBTABLE   = DBTable.getDBTableForTable("ComponentMethod");
        TABLENAME = DBTABLE.getFullyQualifiedTableName();
    }


    public ComponentMethodVO[] findByComponentNameCT(String componentNameCT)
    {
        String componentNameCTCol = DBTABLE.getDBColumn("ComponentNameCT").getFullyQualifiedColumnName();

        String sql = " SELECT * FROM " + TABLENAME +
                     " WHERE " + componentNameCTCol + " = '" + componentNameCT + "'";

        return (ComponentMethodVO[]) executeQuery(ComponentMethodVO.class,
                sql,
                POOLNAME,
                false,
                null);
    }

    public ComponentMethodVO[] findByComponentClassNameAndMethodName(String className, String methodName)
    {
        String componentClassNameCol = DBTABLE.getDBColumn("ComponentClassName").getFullyQualifiedColumnName();
        String methodNameCol         = DBTABLE.getDBColumn("MethodName").getFullyQualifiedColumnName();

        String sql = " SELECT * FROM " + TABLENAME +
                     " WHERE " + componentClassNameCol + " = '" + className + "'" +
                     " AND " + methodNameCol + " = '" + methodName + "'";

        return (ComponentMethodVO[]) executeQuery(ComponentMethodVO.class,
                                                sql,
                                                 POOLNAME,
                                                  false,
                                                   null);
    }
}