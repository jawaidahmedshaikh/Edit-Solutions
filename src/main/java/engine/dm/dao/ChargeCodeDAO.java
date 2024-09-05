/*
 * User: sprasad
 * Date: Mar 3, 2005
 * Time: 9:33:51 AM
 *
 * (c) 2000-2005 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package engine.dm.dao;

import edit.common.vo.ChargeCodeVO;
import edit.common.EDITBigDecimal;

import edit.services.db.ConnectionFactory;
import edit.services.db.DAO;
import edit.services.db.DBTable;

import java.util.List;
import java.math.BigDecimal;

import engine.ChargeCode;


public class ChargeCodeDAO extends DAO
{
    private final String POOLNAME;
    private final DBTable DBTABLE;
    private final String TABLENAME;

    public ChargeCodeDAO()
    {
        POOLNAME = ConnectionFactory.ENGINE_POOL;
        DBTABLE = DBTable.getDBTableForTable("ChargeCode");
        TABLENAME = DBTABLE.getFullyQualifiedTableName();
    }

    /**
     * Finder.
     */
    public ChargeCodeVO[] findAll()
    {
        String sql = " SELECT * FROM " + TABLENAME;

        return (ChargeCodeVO[]) executeQuery(ChargeCodeVO.class,
                                             sql,
                                             POOLNAME,
                                             false,
                                             null);
    }

    /**
     * Finder.
     *
     * @param chargeCodePK
     */
    public ChargeCodeVO[] findByPK(long chargeCodePK)
    {
        String chargeCodePKCol = DBTABLE.getDBColumn("ChargeCodePK").getFullyQualifiedColumnName();

        String sql = " SELECT * FROM " + TABLENAME +
                " WHERE " + chargeCodePKCol + " = " + chargeCodePK;

        return (ChargeCodeVO[]) executeQuery(ChargeCodeVO.class, sql, POOLNAME, false, null);
    }

    public ChargeCodeVO[] findByFilteredFundPK(long filteredFundPK)
    {
        String filteredFundPKCol = DBTABLE.getDBColumn("FilteredFundFK").getFullyQualifiedColumnName();

        String sql = " SELECT * FROM " + TABLENAME +
                " WHERE " + filteredFundPKCol + " = " + filteredFundPK;

        return (ChargeCodeVO[]) executeQuery(ChargeCodeVO.class, sql, POOLNAME, false, null);
    }

    /**
     * Return back all ChageCode VOs order by the accum premium.
     * Logic to pick the smallest meeting criteria is in the ChargeCode CRUDEntity.
     * @param filteredFundPK
     * @return
     */
  public ChargeCodeVO[] findByFilteredFunds(long filteredFundPK)
    {

        String filteredFundPKCol = DBTABLE.getDBColumn("FilteredFundFK").getFullyQualifiedColumnName();


        String sql = " SELECT * FROM " + TABLENAME +
                " WHERE " + filteredFundPKCol + " = " + filteredFundPK;

        ChargeCodeVO[] chargeCodeVOs =
                (ChargeCodeVO[]) executeQuery(ChargeCodeVO.class, sql, POOLNAME, false, null);

        return chargeCodeVOs;

    }

    /**
     * Get the array of ChargeCodeVOs for an array of filtered funds.
     * @param fundNames  fundFKs an Array of fund Names
     * @return  array of ChargeCodeVO objects
     */
    public ChargeCodeVO[] findByFilteredFundNames(String[] fundNames)
    {
        String filteredFundFKCol = DBTABLE.getDBColumn("FilteredFundFK").
                getFullyQualifiedColumnName();

        DBTable DBTABLE_FOR_FFUND = DBTable.getDBTableForTable("FilteredFund");
        String TABLENAME_FOR_FFUND = DBTABLE_FOR_FFUND.getFullyQualifiedTableName();

        String filteredFundPKCol =  DBTABLE_FOR_FFUND.getDBColumn("FilteredFundPK").
                getFullyQualifiedColumnName();

        String filteredFundNumberCol =  DBTABLE_FOR_FFUND.getDBColumn("FundNumber").
                getFullyQualifiedColumnName();

        StringBuffer sb = new StringBuffer(500);
        sb.append("(");

        for (int i = 0; i < fundNames.length; i++)
        {
            if (i != 0) {
                sb.append(",");
            }

            String fundName = fundNames[i];

            sb.append("'").append(fundName).append("'");
        }

        sb.append(")");

        String inClauseListOfFundNames = sb.toString();

        String sql = " SELECT * FROM " + TABLENAME
                + " WHERE " + filteredFundFKCol + " IN "
                    + " (SELECT "
                        + filteredFundPKCol
                        + " FROM "
                        + TABLENAME_FOR_FFUND
                        + " WHERE "
                        +  filteredFundNumberCol
                        + " IN "
                        +  inClauseListOfFundNames
                    + " ) ";

        System.out.println(" SQL IS " + sql);



        ChargeCodeVO[] chargeCodeVOs =
                (ChargeCodeVO[]) executeQuery(ChargeCodeVO.class, sql, POOLNAME, false, null);


        System.out.println("number found is " + chargeCodeVOs.length);

        return chargeCodeVOs;

    }

    public ChargeCodeVO[] findByFilteredFundPK_ChargeCode(String chargeCode, long filteredFundPK)
     {
         String filteredFundPKCol = DBTABLE.getDBColumn("FilteredFundFK").getFullyQualifiedColumnName();
         String chargeCodeCol = DBTABLE.getDBColumn("ChargeCode").getFullyQualifiedColumnName();

         String sql = " SELECT * FROM " + TABLENAME +
                 " WHERE " + filteredFundPKCol + " = " + filteredFundPK +
                 " AND " + chargeCodeCol + " = " + chargeCode;

         return (ChargeCodeVO[]) executeQuery(ChargeCodeVO.class, sql, POOLNAME, false, null);
     }

    public ChargeCodeVO[] findByFilteredFundsOrderedOnAccumPremium(long filteredFundPK)
    {

        String filteredFundPKCol = DBTABLE.getDBColumn("FilteredFundFK").getFullyQualifiedColumnName();

        String accumpremiumCol = DBTABLE.getDBColumn("AccumulatedPremium").getFullyQualifiedColumnName();

        String sql = " SELECT * FROM " + TABLENAME +
                " WHERE " + filteredFundPKCol + " = " + filteredFundPK +
                " ORDER BY " + accumpremiumCol;

        ChargeCodeVO[] chargeCodeVOs =
                (ChargeCodeVO[]) executeQuery(ChargeCodeVO.class, sql, POOLNAME, false, null);

        return chargeCodeVOs;

    }
}