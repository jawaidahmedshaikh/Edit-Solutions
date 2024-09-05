/*
 * Created by IntelliJ IDEA.
 * User: cgleason
 * Date: Sep 3, 2003
 * Time: 4:25:16 PM
 *
 * (c) 2000-2004 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package event.dm.dao;

import edit.common.vo.InvestmentAllocationOverrideVO;
import edit.services.db.ConnectionFactory;
import edit.services.db.DAO;
import edit.services.db.DBTable;

public class InvestmentAllocationOverrideDAO extends DAO
{
    private final String POOLNAME;
    private final DBTable DBTABLE;
    private final String TABLENAME;


    public InvestmentAllocationOverrideDAO()
    {
        POOLNAME  = ConnectionFactory.EDITSOLUTIONS_POOL;
        DBTABLE   = DBTable.getDBTableForTable("InvestmentAllocationOverride");
        TABLENAME = DBTABLE.getFullyQualifiedTableName();


    }

    public InvestmentAllocationOverrideVO[] findByContractSetupPK(long contractSetupPK)
    {
        String contractSetupFKCol = DBTABLE.getDBColumn("ContractSetupFK").getFullyQualifiedColumnName();
        String toFromStatusCol    = DBTABLE.getDBColumn("ToFromStatus").getFullyQualifiedColumnName();

        String sql = "SELECT * FROM " + TABLENAME +
                     " WHERE " + contractSetupFKCol + " = " + contractSetupPK +
                     " ORDER BY " + toFromStatusCol + " ASC";

        return (InvestmentAllocationOverrideVO[]) executeQuery(InvestmentAllocationOverrideVO.class,
                                                                sql,
                                                                 POOLNAME,
                                                                  false,
                                                                   null);
    }

    public InvestmentAllocationOverrideVO[] findByInvestmentAllocationOverridePK(long invAllocOvrdPK)
    {
        String investmentAllocationOverridePK = DBTABLE.getDBColumn("InvestmentAllocationOverridePK").getFullyQualifiedColumnName();

        String sql = "SELECT * FROM " + TABLENAME +
                     " WHERE " + investmentAllocationOverridePK + " = " + invAllocOvrdPK;

        return (InvestmentAllocationOverrideVO[]) executeQuery(InvestmentAllocationOverrideVO.class,
                                                                sql,
                                                                 POOLNAME,
                                                                  false,
                                                                   null);
    }

    public InvestmentAllocationOverrideVO[] findByEDITTrxPK(long editTrxPK)
    {
        DBTable EDITTRX_DBTABLE        = DBTable.getDBTableForTable("EDITTrx");
        DBTable CLIENT_SETUP_DBTABLE   = DBTable.getDBTableForTable("ClientSetup");
        DBTable CONTRACT_SETUP_DBTABLE = DBTable.getDBTableForTable("ContractSetup");

        String EDITTRX_TABLENAME        = EDITTRX_DBTABLE.getFullyQualifiedTableName();
        String CLIENT_SETUP_TABLENAME   = CLIENT_SETUP_DBTABLE.getFullyQualifiedTableName();
        String CONTRACT_SETUP_TABLENAME = CONTRACT_SETUP_DBTABLE.getFullyQualifiedTableName();

        String editTrxPKCol   = EDITTRX_DBTABLE.getDBColumn("EDITTrxPK").getFullyQualifiedColumnName();
        String clientSetupFKCol   = EDITTRX_DBTABLE.getDBColumn("ClientSetupFK").getFullyQualifiedColumnName();
        String clientSetupPKCol   = CLIENT_SETUP_DBTABLE.getDBColumn("ClientSetupPK").getFullyQualifiedColumnName();
        String clientContractSetupFKCol = CLIENT_SETUP_DBTABLE.getDBColumn("ContractSetupFK").getFullyQualifiedColumnName();
        String contractSetupPKCol = CONTRACT_SETUP_DBTABLE.getDBColumn("ContractSetupPK").getFullyQualifiedColumnName();
        String contractSetupFKCol = DBTABLE.getDBColumn("ContractSetupFK").getFullyQualifiedColumnName();

        String sql =  "SELECT " + TABLENAME + ".*  FROM " + EDITTRX_TABLENAME +
                      " INNER JOIN " +  CLIENT_SETUP_TABLENAME + " ON " +
                       clientSetupFKCol  + " = " +  clientSetupPKCol  +
                      " INNER JOIN " + CONTRACT_SETUP_TABLENAME + " ON " +
                       clientContractSetupFKCol +  " = " + contractSetupPKCol +
                      " INNER JOIN "  + TABLENAME +  " ON " + contractSetupPKCol +  " = "  + contractSetupFKCol +
                      " WHERE " + editTrxPKCol + " = " + editTrxPK;

        return (InvestmentAllocationOverrideVO[]) executeQuery(InvestmentAllocationOverrideVO.class,
                                                                sql,
                                                                 POOLNAME,
                                                                  false,
                                                                   null);
    }
}
