/*
 * User: gfrosti
 * Date: Nov 18, 2004
 * Time: 8:17:31 AM
 *
 * (c) 2000-2004 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package reinsurance.dm.dao;

import edit.common.vo.*;

import edit.services.db.*;


public class ReinsurerDAO extends DAO
{
    private final DBTable DBTABLE;
    private final String POOLNAME;
    private final String TABLENAME;

    /************************************** Constructor Methods **************************************/
    public ReinsurerDAO()
    {
        POOLNAME = ConnectionFactory.EDITSOLUTIONS_POOL;
        DBTABLE = DBTable.getDBTableForTable("Reinsurer");
        TABLENAME = DBTABLE.getFullyQualifiedTableName();
    }

    /************************************** Public Methods **************************************/
    /**
     * Finder.
     * @param reinsurerNumber
     * @return
     */
    public ReinsurerVO[] findBy_ReinsurerNumber(String reinsurerNumber)
    {
        String reinsurerNumberCol = DBTABLE.getDBColumn("ReinsurerNumber").getFullyQualifiedColumnName();

        String sql;
        sql =   " SELECT *" +
                " FROM " + TABLENAME +
                " WHERE UPPER(" + reinsurerNumberCol + ") = UPPER('" + reinsurerNumber + "')";

        return (ReinsurerVO[]) executeQuery(ReinsurerVO.class, sql, POOLNAME, false, null);
    }

    /**
     * Finder.
     * @param partialCorporateName
     * @return
     */
    public ReinsurerVO[] findBy_PartialCorporateName(String partialCorporateName)
    {
        String clientDetailFKCol = DBTABLE.getDBColumn("ClientDetailFK").getFullyQualifiedColumnName();

        DBTable clientDetailDBTable = DBTable.getDBTableForTable("ClientDetail");
        String clientDetailTable = clientDetailDBTable.getFullyQualifiedTableName();
        String clientDetailPKCol = clientDetailDBTable.getDBColumn("ClientDetailPK").getFullyQualifiedColumnName();
        String corporateNameCol = clientDetailDBTable.getDBColumn("CorporateName").getFullyQualifiedColumnName();

        String sql;
        sql =   " SELECT * " +
                " FROM " + TABLENAME +
                " INNER JOIN " + clientDetailTable +
                " ON " + clientDetailFKCol + " = " + clientDetailPKCol +
                " WHERE UPPER(" + corporateNameCol + ") LIKE UPPER ('" + partialCorporateName + "%')";

        return (ReinsurerVO[]) executeQuery(ReinsurerVO.class, sql, POOLNAME, false, null);
    }

    /**
     * Finder.
     * @param taxIdentification
     * @return
     */
    public ReinsurerVO[] findBy_TaxIdentification(String taxIdentification)
    {
        String clientDetailFKCol = DBTABLE.getDBColumn("ClientDetailFK").getFullyQualifiedColumnName();

        DBTable clientDetailDBTable = DBTable.getDBTableForTable("ClientDetail");
        String clientDetailTable = clientDetailDBTable.getFullyQualifiedTableName();
        String clientDetailPKCol = clientDetailDBTable.getDBColumn("ClientDetailPK").getFullyQualifiedColumnName();
        String taxIdentificationCol = clientDetailDBTable.getDBColumn("TaxIdentification").getFullyQualifiedColumnName();

        String sql;
        sql =   " SELECT * " +
                " FROM " + TABLENAME +
                " INNER JOIN " + clientDetailTable +
                " ON " + clientDetailFKCol + " = " + clientDetailPKCol +
                " WHERE " + taxIdentificationCol + " = '" + taxIdentification + "'";

        return (ReinsurerVO[]) executeQuery(ReinsurerVO.class, sql, POOLNAME, false, null);
    }

    public ReinsurerVO[] findBy_ReinsuranceHistoryPK(long reinsuranceHistoryPK)
    {
        DBTable reinsuranceHistoryDBTable = DBTable.getDBTableForTable("ReinsuranceHistory");
        String reinsuranceHistoryTable = reinsuranceHistoryDBTable.getFullyQualifiedTableName();
        String reinsuranceHistoryPKCol = reinsuranceHistoryDBTable.getDBColumn("ReinsuranceHistoryPK").getFullyQualifiedColumnName();

        DBTable treatyDBTable = DBTable.getDBTableForTable("Treaty");
        String treatyTable = treatyDBTable.getFullyQualifiedTableName();
        String treatyPKCol = treatyDBTable.getDBColumn("TreatyPK").getFullyQualifiedColumnName();
        String reinsurerFKCol = treatyDBTable.getDBColumn("ReinsurerFK").getFullyQualifiedColumnName();

        DBTable contractTreatyDBTable = DBTable.getDBTableForTable("ContractTreaty");
        String contractTreatyTable = contractTreatyDBTable.getFullyQualifiedTableName();
        String contractTreatyPKCol = contractTreatyDBTable.getDBColumn("ContractTreatyPK").getFullyQualifiedColumnName();
        String treatyFKCol = contractTreatyDBTable.getDBColumn("TreatyFK").getFullyQualifiedColumnName();

        String reinsurerPKCol = DBTABLE.getDBColumn("ReinsurerPK").getFullyQualifiedColumnName();

        String sql;
        sql =   " SELECT " + TABLENAME + ".* " +
                " FROM " + TABLENAME +
                " INNER JOIN " + treatyTable +
                " ON " + reinsurerPKCol + " = " + reinsurerFKCol +
                " INNER JOIN " + contractTreatyTable +
                " ON " + treatyPKCol + " = " + treatyFKCol +
                " INNER JOIN " + reinsuranceHistoryTable +
                " ON " + contractTreatyPKCol + " = " + reinsuranceHistoryPKCol +
                " WHERE " + reinsuranceHistoryPKCol + " = " + reinsuranceHistoryPK;

        return (ReinsurerVO[]) executeQuery(ReinsurerVO.class, sql, POOLNAME, false, null);
    }

    /**
      * Finder.
      * @param reinsurerPK
      * @return
      */
     public ReinsurerVO[] findBy_PK(long reinsurerPK)
     {
         String reinsurerPKCol = DBTABLE.getDBColumn("ReinsurerPK").getFullyQualifiedColumnName();

         String sql;
         sql = " SELECT " + TABLENAME + ".*" +
                 " FROM " + TABLENAME +
                 " WHERE " + reinsurerPKCol + " = " + reinsurerPK;

         return (ReinsurerVO[]) executeQuery(ReinsurerVO.class, sql, POOLNAME, false, null);
     }

    /**
     * Finder.
     * @return
     */
    public ReinsurerVO[] findAllReinsurers()
    {
        String sql = " SELECT " + TABLENAME + ".*" + " FROM " + TABLENAME;

        return (ReinsurerVO[]) executeQuery(ReinsurerVO.class, sql, POOLNAME, false, null);
    }
}
