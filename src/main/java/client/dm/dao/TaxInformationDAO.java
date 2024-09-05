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

import edit.common.vo.TaxInformationVO;
import edit.services.db.ConnectionFactory;
import edit.services.db.DAO;
import edit.services.db.DBTable;

import java.util.List;


public class TaxInformationDAO extends DAO
{
    private final String POOLNAME;
    private final DBTable DBTABLE;
    private final String TABLENAME;


    public TaxInformationDAO()
    {
        POOLNAME  = ConnectionFactory.EDITSOLUTIONS_POOL;
        DBTABLE   = DBTable.getDBTableForTable("TaxInformation");
        TABLENAME = DBTABLE.getFullyQualifiedTableName();
    }

    public TaxInformationVO[] findByTaxProfilePK(long taxProfilePK, boolean includeChildVOs, List voExclusionList)
    {
        DBTable taxProfileDBTable = DBTable.getDBTableForTable("TaxProfile");

        String taxProfileTable = taxProfileDBTable.getFullyQualifiedTableName();

        String taxInformationPKCol = DBTABLE.getDBColumn("TaxInformationPK").getFullyQualifiedColumnName();

        String taxInformationFKCol = taxProfileDBTable.getDBColumn("TaxInformationFK").getFullyQualifiedColumnName();
        String taxProfilePKCol     = taxProfileDBTable.getDBColumn("TaxProfilePK").getFullyQualifiedColumnName();

        String sql = "SELECT " + TABLENAME + ".* FROM " + TABLENAME + ", " + taxProfileTable +
                     " WHERE " + taxInformationPKCol + " = " + taxInformationFKCol +
                     " AND " + taxProfilePKCol + " = " + taxProfilePK;

        return (TaxInformationVO[]) executeQuery(TaxInformationVO.class,
                                                  sql,
                                                   POOLNAME,
                                                    includeChildVOs,
                                                     voExclusionList);
    }

    public TaxInformationVO[] findByPK(long taxInformationPK)
    {
        String taxInformationPKCol = DBTABLE.getDBColumn("TaxInforationPK").getFullyQualifiedColumnName();

        String sql = "SELECT * FROM " + TABLENAME +
                     " WHERE " + taxInformationPKCol + " = " + taxInformationPK;

        return (TaxInformationVO[]) executeQuery(TaxInformationVO.class,
                                              sql,
                                               POOLNAME,
                                                false,
                                                 null);
    }
}