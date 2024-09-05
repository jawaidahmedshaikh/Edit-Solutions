/*
 * Created by IntelliJ IDEA.
 * User: gfrosti
 * Date: Dec 31, 2002
 * Time: 4:06:26 PM
 *
 * (c) 2000-2004 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package security.dm.dao;

import edit.common.vo.PasswordMaskVO;
import edit.services.db.ConnectionFactory;
import edit.services.db.DAO;
import edit.services.db.DBTable;

import java.util.List;


public class PasswordMaskDAO extends DAO
{
    private final String POOLNAME;
    private final DBTable DBTABLE;
    private final String TABLENAME;


    public PasswordMaskDAO()
    {
        POOLNAME  = ConnectionFactory.SECURITY_POOL;
        DBTABLE   = DBTable.getDBTableForTable("PasswordMask");
        TABLENAME = DBTABLE.getFullyQualifiedTableName();
    }

    public PasswordMaskVO[] findBySecurityProfileFK(long securityProfileFK, boolean includeChildVOs, List voExclusionList)
    {
        DBTable maskDBTable = DBTable.getDBTableForTable("Mask");

        String maskTable = maskDBTable.getFullyQualifiedTableName();

        String securityProfileFKCol = maskDBTable.getDBColumn("SecurityProfileFK").getFullyQualifiedColumnName();
        String maskTypeCTCol        = maskDBTable.getDBColumn("MaskTypeCT").getFullyQualifiedColumnName();
        String maskPKCol            = maskDBTable.getDBColumn("MaskPK").getFullyQualifiedColumnName();
        String maskFKCol            = DBTABLE.getDBColumn("MaskFK").getFullyQualifiedColumnName();

        String sql = " SELECT * FROM " + TABLENAME + ", " + maskTable +
                     " WHERE " + securityProfileFKCol + " = '" + securityProfileFK + "'" +
                     " AND " + maskTypeCTCol + " = 'Password'" +
                     " AND " + maskFKCol + " = " + maskPKCol;

        return (PasswordMaskVO[]) executeQuery(PasswordMaskVO.class,
                sql,
                POOLNAME,
                includeChildVOs,
                voExclusionList);
    }
}