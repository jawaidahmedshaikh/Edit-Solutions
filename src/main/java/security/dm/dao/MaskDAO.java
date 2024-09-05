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

import edit.common.vo.MaskVO;
import edit.services.db.ConnectionFactory;
import edit.services.db.DAO;
import edit.services.db.DBTable;
import security.Mask;

import java.util.List;


public class MaskDAO extends DAO
{
    private final String POOLNAME;
    private final DBTable DBTABLE;
    private final String TABLENAME;


    public MaskDAO()
    {
        POOLNAME  = ConnectionFactory.SECURITY_POOL;
        DBTABLE   = DBTable.getDBTableForTable("Mask");
        TABLENAME = DBTABLE.getFullyQualifiedTableName();
    }

    public MaskVO[] findByPK(long pk, boolean includeChildVOs, List voExclusionList)
    {
        String maskPK = DBTABLE.getDBColumn("MaskPK").getFullyQualifiedColumnName();

        String sql = " SELECT * FROM " + TABLENAME +
                     " WHERE " + maskPK + " = '" + pk + "'";

        return (MaskVO[]) executeQuery(MaskVO.class,
                sql,
                POOLNAME,
                includeChildVOs,
                voExclusionList);
    }

    public MaskVO[] findBySecurityProfileFK(long securityProfileFK, boolean includeChildVOs, List voExclusionList)
    {
        String SecurityProfileFKCol = DBTABLE.getDBColumn("SecurityProfileFK").getFullyQualifiedColumnName();

        String sql = " SELECT * FROM " + TABLENAME +
                     " WHERE " + SecurityProfileFKCol + " = '" + securityProfileFK + "'";

        return (MaskVO[]) executeQuery(MaskVO.class,
                sql,
                POOLNAME,
                includeChildVOs,
                voExclusionList);
    }

    /**
     * Finds a Mask based on the mask type and the security profile key.
     * @param maskTypeCT
     * @param securityProfileFK
     * @param includeChildVOs
     * @param voExclusionList
     * @return  MaskVO[]
     */
    public MaskVO[] findByMaskTypeCT_AND_SecurityProfileFK(String maskTypeCT, long securityProfileFK,
                                                           boolean includeChildVOs, List voExclusionList)
    {
        String securityProfileFKCol = DBTABLE.getDBColumn("SecurityProfileFK").getFullyQualifiedColumnName();
        String maskTypeCTCol        = DBTABLE.getDBColumn("MaskTypeCT").getFullyQualifiedColumnName();

        String sql = " SELECT * FROM " + TABLENAME +
                     " WHERE " + securityProfileFKCol + " = '" + securityProfileFK + "'" +
                     " AND " + maskTypeCTCol + " = '" + maskTypeCT + "'";

        return (MaskVO[]) executeQuery(MaskVO.class,
                sql,
                POOLNAME,
                includeChildVOs,
                voExclusionList);
    }

    /**
     * Finds a Mask whose mask type is Operator for a given security profile key.
     * @param securityProfileFK
     * @param includeChildVOs
     * @param voExclusionList
     * @return  MaskVO[]
     */
    public MaskVO[] findOperatorMaskBySecurityProfileFK(long securityProfileFK, boolean includeChildVOs, List voExclusionList)
    {
        String securityProfileFKCol = DBTABLE.getDBColumn("SecurityProfileFK").getFullyQualifiedColumnName();
        String maskTypeCTCol        = DBTABLE.getDBColumn("MaskTypeCT").getFullyQualifiedColumnName();


        String sql = " SELECT * FROM " + TABLENAME +
                     " WHERE " + securityProfileFKCol + " = '" + securityProfileFK + "'" +
                     " AND " + maskTypeCTCol + " = '" + Mask.OPERATOR_MASKTYPE + "'";

        return (MaskVO[]) executeQuery(MaskVO.class,
                sql,
                POOLNAME,
                includeChildVOs,
                voExclusionList);
    }

    /**
     * Finds a Mask whose mask type is Password for a given security profile key.
     * @param securityProfileFK
     * @param includeChildVOs
     * @param voExclusionList
     * @return MaskVO[]
     */
    public MaskVO[] findPasswordMaskBySecurityProfileFK(long securityProfileFK, boolean includeChildVOs, List voExclusionList)
    {
        String securityProfileFKCol = DBTABLE.getDBColumn("SecurityProfileFK").getFullyQualifiedColumnName();
        String maskTypeCTCol        = DBTABLE.getDBColumn("MaskTypeCT").getFullyQualifiedColumnName();

        String sql = " SELECT * FROM " + TABLENAME +
                     " WHERE " + securityProfileFKCol + " = '" + securityProfileFK + "'" +
                     " AND " + maskTypeCTCol + " = '" + Mask.PASSWORD_MASKTYPE + "'";

        return (MaskVO[]) executeQuery(MaskVO.class,
                sql,
                POOLNAME,
                includeChildVOs,
                voExclusionList);
    }
}