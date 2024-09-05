/*
 * Created by IntelliJ IDEA.
 * User: gfrosti
 * Date: Jul 17, 2003
 * Time: 3:29:36 PM
 *
 * (c) 2000-2004 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package client.dm.dao;

import edit.common.vo.TaxProfileVO;
import edit.services.db.ConnectionFactory;
import edit.services.db.DAO;
import edit.services.db.DBTable;

import java.util.List;


public class TaxProfileDAO extends DAO
{
    private final String POOLNAME;
    private final DBTable DBTABLE;
    private final String TABLENAME;


    public TaxProfileDAO()
    {
        POOLNAME  = ConnectionFactory.EDITSOLUTIONS_POOL;
        DBTABLE   = DBTable.getDBTableForTable("TaxProfile");
        TABLENAME = DBTABLE.getFullyQualifiedTableName();
    }

    public TaxProfileVO[] findByTaxProfilePK(long taxProfilePK, boolean includeChildVOs, List voExclusionList)
    {
        String taxProfilePKCol = DBTABLE.getDBColumn("TaxProfilePK").getFullyQualifiedColumnName();

        String sql = "SELECT * FROM " + TABLENAME +
                     " WHERE " + taxProfilePKCol + " = " + taxProfilePK;

        return (TaxProfileVO[]) executeQuery(TaxProfileVO.class,
                                              sql,
                                               POOLNAME,
                                                includeChildVOs,
                                                 voExclusionList);
    }

    public TaxProfileVO[] findPrimaryByTaxInformationPK(long taxInformationPK, boolean includeChildVOs, List voExclusionList)
    {
        String taxInformationFKCol = DBTABLE.getDBColumn("TaxInformationFK").getFullyQualifiedColumnName();
        String overrideStatusCol   = DBTABLE.getDBColumn("OverrideStatus").getFullyQualifiedColumnName();

        String sql = "SELECT * FROM " + TABLENAME +
                     " WHERE " + taxInformationFKCol + " = " + taxInformationPK +
                     " AND " + overrideStatusCol + " = 'P'";

        return (TaxProfileVO[]) executeQuery(TaxProfileVO.class,
                                              sql,
                                               POOLNAME,
                                                includeChildVOs,
                                                 voExclusionList);
    }

    public TaxProfileVO[] findByPK(long taxProfilePK)
    {
        String taxProfilePKCol = DBTABLE.getDBColumn("TaxProfilePK").getFullyQualifiedColumnName();

        String sql = "SELECT * FROM " + TABLENAME +
                     " WHERE " + taxProfilePKCol + " = " + taxProfilePK;

        return (TaxProfileVO[]) executeQuery(TaxProfileVO.class,
                                              sql,
                                               POOLNAME,
                                                false,
                                                 null);
    }
}