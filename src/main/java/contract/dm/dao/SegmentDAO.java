/*
 * User: unknown
 * Date: Mar 15, 2002
 * Time: unknown
 *
 * (c) 2000-2008 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */

package contract.dm.dao;

import edit.common.vo.*;
import edit.common.*;
import edit.services.db.*;

import java.util.*;
import java.sql.*;


public class SegmentDAO extends DAO
{
    private final String POOLNAME;
    private final DBTable DBTABLE;
    private final String TABLENAME;


    public SegmentDAO()
    {
        POOLNAME  = ConnectionFactory.EDITSOLUTIONS_POOL;
        DBTABLE   = DBTable.getDBTableForTable("Segment");
        TABLENAME = DBTABLE.getFullyQualifiedTableName();
    }

    public SegmentVO[] findByProductStructureContractNumber(long productStructrurePK, String contractNumber,
                                                            boolean includeChildVOs, List voExclusionList)
    {
        String productStructureFKCol = DBTABLE.getDBColumn("ProductStructureFK").getFullyQualifiedColumnName();
        String contractNumberCol     = DBTABLE.getDBColumn("ContractNumber").getFullyQualifiedColumnName();

        String sql = " SELECT * FROM " + TABLENAME +
                     " WHERE " + productStructureFKCol + " = " + productStructrurePK +
                     " AND " + contractNumberCol + " = '" + contractNumber + "'";

        return (SegmentVO[]) executeQuery(SegmentVO.class,
                sql,
                POOLNAME,
                includeChildVOs,
                voExclusionList);
    }

    public SegmentVO[] findByProductStructurePK(long productStructrurePK, boolean includeChildVOs, List voExclusionList)
    {
        String productStructureFKCol = DBTABLE.getDBColumn("ProductStructureFK").getFullyQualifiedColumnName();


        String sql = " SELECT * FROM " + TABLENAME +
                     " WHERE " + productStructureFKCol + " = " + productStructrurePK;

        return (SegmentVO[]) executeQuery(SegmentVO.class,
                sql,
                POOLNAME,
                includeChildVOs,
                voExclusionList);
    }

    public SegmentVO[] findByClientRoleFK(long clientRoleFK, boolean includeChildVOs, List voExclusionList)
    {
        DBTable contractClientDBTable = DBTable.getDBTableForTable("ContractClient");

        String contractClientTable = contractClientDBTable.getFullyQualifiedTableName();


        String segmentPKCol = DBTABLE.getDBColumn("SegmentPK").getFullyQualifiedColumnName();

        String segmentFKCol    = contractClientDBTable.getDBColumn("SegmentFK").getFullyQualifiedColumnName();
        String clientRoleFKCol = contractClientDBTable.getDBColumn("ClientRoleFK").getFullyQualifiedColumnName();


        String sql = " SELECT DISTINCT * FROM " + TABLENAME + ", " + contractClientTable +
                     " WHERE " + segmentPKCol + " = " + segmentFKCol +
                     " AND " + clientRoleFKCol + " = " + clientRoleFK;

        return (SegmentVO[]) executeQuery(SegmentVO.class,
                sql,
                POOLNAME,
                includeChildVOs,
                voExclusionList);
    }

    public SegmentVO[] findSegmentByContractNumber(String contractNumber, boolean includeChildren, List voExclusionList)
    {
        String contractNumberCol = DBTABLE.getDBColumn("ContractNumber").getFullyQualifiedColumnName();
        String segmentFKCol      = DBTABLE.getDBColumn("SegmentFK").getFullyQualifiedColumnName();

        String sql = " SELECT * FROM " + TABLENAME +
                     " WHERE UPPER(" + contractNumberCol + ") = UPPER('" + contractNumber + "')" +
                     " AND " + segmentFKCol + " IS NULL";

        return (SegmentVO[]) executeQuery(SegmentVO.class,
                sql,
                POOLNAME,
                includeChildren,
                voExclusionList);
    }

    public SegmentVO[] findSegmentByContractNumberAndRiderNumber(String contractNumber, int riderNumber, boolean includeChildren, List voExclusionList)
    {
        String contractNumberCol = DBTABLE.getDBColumn("ContractNumber").getFullyQualifiedColumnName();
        String riderNumberCol      = DBTABLE.getDBColumn("RiderNumber").getFullyQualifiedColumnName();

        String sql = " SELECT * FROM " + TABLENAME +
                     " WHERE UPPER(" + contractNumberCol + ") = UPPER('" + contractNumber + "')" +
                     " AND " + riderNumberCol + " = " + riderNumber;

        return (SegmentVO[]) executeQuery(SegmentVO.class,
                sql,
                POOLNAME,
                includeChildren,
                voExclusionList);
    }

    public SegmentVO[] findSegmentsByContractNumber(String contractNumber, boolean includeChildren, List voExclusionList)
    {
        String contractNumberCol = DBTABLE.getDBColumn("ContractNumber").getFullyQualifiedColumnName();
        String segmentFKCol      = DBTABLE.getDBColumn("SegmentFK").getFullyQualifiedColumnName();

        String sql = " SELECT * FROM " + TABLENAME +
                     " WHERE UPPER(" + contractNumberCol + ") = UPPER('" + contractNumber + "')";

        return (SegmentVO[]) executeQuery(SegmentVO.class,
                sql,
                POOLNAME,
                includeChildren,
                voExclusionList);
    }

    public SegmentVO[] findSegmentByFund(long filteredFundFK)
    {
        DBTable investmentDBTable = DBTable.getDBTableForTable("Investment");

        String investmentTable = investmentDBTable.getFullyQualifiedTableName();

        String filteredFundFKCol = investmentDBTable.getDBColumn("FilteredFundFK").getFullyQualifiedColumnName();
        String invSegmentFKCol = investmentDBTable.getDBColumn("SegmentFK").getFullyQualifiedColumnName();
        String investmentPKCol = investmentDBTable.getDBColumn("InvestmentPK").getFullyQualifiedColumnName();
        String segmentFKCol = DBTABLE.getDBColumn("SegmentFK").getFullyQualifiedColumnName();
        String segmentPKCol = DBTABLE.getDBColumn("SegmentPK").getFullyQualifiedColumnName();

        String sql = " SELECT " + TABLENAME + ".* FROM " + TABLENAME + ", " + investmentTable +
                     " WHERE " + segmentPKCol + " = " + invSegmentFKCol +
                     " AND " + investmentPKCol + " IN (SELECT " + investmentPKCol + "FROM " + investmentTable +
                     " WHERE " + filteredFundFKCol + " = " + filteredFundFK + ")" +
                     " AND " + segmentFKCol + " IS NULL";

        return (SegmentVO[]) executeQuery(SegmentVO.class,
                                          sql,
                                          POOLNAME,
                                          false,
                                          null);
    }

    public SegmentVO[] findBaseAndRidersBySegmentPK(long segmentPK)
    {
        String segmentPKCol = DBTABLE.getDBColumn("SegmentPK").getFullyQualifiedColumnName();

        String sql = " SELECT * FROM " + TABLENAME +
                     " WHERE " + segmentPKCol + " = " + segmentPK;

        return (SegmentVO[]) executeQuery(SegmentVO.class,
                sql,
                POOLNAME,
                true,
                null);
    }

    public SegmentVO[] findByPK(long primaryKey)
    {
        String segmentPKCol = DBTABLE.getDBColumn("SegmentPK").getFullyQualifiedColumnName();

        String sql = " SELECT * FROM " + TABLENAME +
                     " WHERE " + segmentPKCol + " = " + primaryKey;

        return (SegmentVO[]) executeQuery(SegmentVO.class,
                sql,
                POOLNAME,
                true,
                null);
    }

    public SegmentVO[] findNonRecursivelyByPK(long primaryKey)
    {
        String segmentPKCol = DBTABLE.getDBColumn("SegmentPK").getFullyQualifiedColumnName();

        String sql = " SELECT * FROM " + TABLENAME +
                     " WHERE " + segmentPKCol + " = " + primaryKey;

        return (SegmentVO[]) executeQuery(SegmentVO.class,
                sql,
                POOLNAME,
                false,
                null);
    }

    public SegmentVO[] findAllSegments()
    {
        String segmentFKCol      = DBTABLE.getDBColumn("SegmentFK").getFullyQualifiedColumnName();
        String contractNumberCol = DBTABLE.getDBColumn("ContractNumber").getFullyQualifiedColumnName();

        String sql = " SELECT * FROM " + TABLENAME +
                     " WHERE " + segmentFKCol + " IS NULL" +
                     " ORDER BY " + contractNumberCol + " ASC";

        return (SegmentVO[]) executeQuery(SegmentVO.class,
                sql,
                POOLNAME,
                false,
                null);
    }

    public SegmentVO[] findAllActiveByCSId(long[] productStructureFK,
                                           String[] statusCodes)
    {
        String segmentStatusCTCol    = DBTABLE.getDBColumn("SegmentStatusCT").getFullyQualifiedColumnName();
        String productStructureFKCol = DBTABLE.getDBColumn("ProductStructureFK").getFullyQualifiedColumnName();

        String sql = " SELECT * FROM " + TABLENAME +
                     " WHERE " + segmentStatusCTCol + " IN ('";

        for (int s = 0; s < statusCodes.length; s++)
        {
            if (s < statusCodes.length - 1)
            {
                sql += statusCodes[s];
                sql += "', '";
            }
            else
            {
                sql += statusCodes[s];
            }
        }

        sql += "') " +
                " AND " + productStructureFKCol + " IN (";

        for (int i = 0; i < productStructureFK.length; i++)
        {
            if (i < productStructureFK.length - 1)
            {
                sql += productStructureFK[i];
                sql += ", ";
            }
            else
            {
                sql += productStructureFK[i];
            }
        }

        sql += ") ";

        return (SegmentVO[]) executeQuery(SegmentVO.class,
                sql,
                POOLNAME,
                true,
                null);
    }

    public SegmentVO[] findAllByCSId(long[] productStructureFK)
    {
        String productStructureFKCol = DBTABLE.getDBColumn("ProductStructureFK").getFullyQualifiedColumnName();

        String sql = " SELECT DISTINCT * FROM " + TABLENAME +
                     " WHERE " + productStructureFKCol + " IN (";

        for (int i = 0; i < productStructureFK.length; i++)
        {
            if (i < productStructureFK.length - 1)
            {
                sql += productStructureFK[i];
                sql += ", ";
            }
            else
            {
                sql += productStructureFK[i];
            }
        }

        sql += ")";

        return (SegmentVO[]) executeQuery(SegmentVO.class,
                sql,
                POOLNAME,
                true,
                null);
    }

    public SegmentVO[] findBySegmentPK(long segmentPK,
                                       boolean includeChildVOs,
                                       List voExclusionList)
    {
        String segmentPKCol = DBTABLE.getDBColumn("SegmentPK").getFullyQualifiedColumnName();

        String sql = " SELECT * FROM " + TABLENAME +
                     " WHERE " + segmentPKCol + " = " + segmentPK;

        return (SegmentVO[]) executeQuery(SegmentVO.class,
                sql,
                POOLNAME,
                includeChildVOs,
                voExclusionList);
    }

    public SegmentVO[] findAllSegmentsForValuation(String valuationDate, long[] productStructureIds)
    {
        String effectiveDateCol      = DBTABLE.getDBColumn("EffectiveDate").getFullyQualifiedColumnName();
        String productStructureFKCol = DBTABLE.getDBColumn("ProductStructureFK").getFullyQualifiedColumnName();


        String sql = " SELECT * FROM " + TABLENAME +
                     " WHERE (" + effectiveDateCol + " < '" + valuationDate + "'" +
                     " OR " + effectiveDateCol + " = ?)" +
                     " AND " + productStructureFKCol + " IN (";

        for (int i = 0; i < productStructureIds.length; i++)
        {
            if (i < productStructureIds.length - 1)
            {
                sql += productStructureIds[i];
                sql += ", ";
            }
            else
            {
                sql += productStructureIds[i];
            }
        }

        sql += ")";

        SegmentVO[] segmentVOs = null;
        Connection conn = null;

        PreparedStatement ps = null;

        try
        {
            conn = ConnectionFactory.getSingleton().getConnection(POOLNAME);

            ps = conn.prepareStatement(sql);

            ps.setDate(1, DBUtil.convertStringToDate(valuationDate));

            segmentVOs = (SegmentVO[]) executeQuery(SegmentVO.class,
                                                      ps,
                                                      POOLNAME,
                                                      false,   // used to be true but need an exclusion list now
                                                      null);
        }
        catch (Exception e)
        {
            System.out.println(e);
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        finally
        {
            try
            {
              if (ps != null) ps.close();
              ps = null;
              if (conn != null) conn.close();
              conn = null;
            }
            catch (SQLException e)
            {
                System.out.println(e);
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
        return segmentVOs;
    }

    public SegmentVO[] findAllSegmentsForBenefitExtract(String valuationDate,
                                                        String optionCode,
                                                        String transactionType,
                                                        long[] productStructureIds)
    {
        DBTable transactionsDBTable = DBTable.getDBTableForTable("EDITTrx");

        String transactionsTable = transactionsDBTable.getFullyQualifiedTableName();

        String effectiveDateCol      = DBTABLE.getDBColumn("EffectiveDate").getFullyQualifiedColumnName();
        String optionCodeCTCol       = DBTABLE.getDBColumn("OptionCodeCT").getFullyQualifiedColumnName();
        String segmentPKCol          = DBTABLE.getDBColumn("SegmentPK").getFullyQualifiedColumnName();
        String productStructureFKCol = DBTABLE.getDBColumn("ProductStructureFK").getFullyQualifiedColumnName();


        String segmentFKCol         = transactionsDBTable.getDBColumn("SegmentFK").getFullyQualifiedColumnName();
        String transactionTypeCTCol = transactionsDBTable.getDBColumn("TransactionTypeCT").getFullyQualifiedColumnName();


        String sql = " SELECT DISTINCT * FROM " + TABLENAME + ", " + transactionsTable +
                     " WHERE " + effectiveDateCol + " <= ?" +
                     " AND (" + optionCodeCTCol + " = ?" +
                     " OR (" + segmentPKCol + " = " + segmentFKCol +
                     " AND " + transactionTypeCTCol + " = ?))" +
                     " AND " + productStructureFKCol + " IN (";

        for (int i = 0; i < productStructureIds.length; i++)
        {
            if (i < productStructureIds.length - 1)
            {
                sql += productStructureIds[i];
                sql += ", ";
            }
            else
            {
                sql += productStructureIds[i];
            }
        }

        sql += ")";

        SegmentVO[] segmentVOs = null;
        Connection conn = null;

        PreparedStatement ps = null;

        try
        {
            conn = ConnectionFactory.getSingleton().getConnection(POOLNAME);

            ps = conn.prepareStatement(sql);

            ps.setDate(1, DBUtil.convertStringToDate(valuationDate));
            ps.setString(2, optionCode);
            ps.setString(3, transactionType);

            segmentVOs = (SegmentVO[]) executeQuery(SegmentVO.class,
                                                      ps,
                                                      POOLNAME,
                                                      false,   // used to be true but need an exclusion list now
                                                       null);
        }
        catch (Exception e)
        {
            System.out.println(e);
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        finally
        {
            try
            {
              if (ps != null) ps.close();
              ps = null;
              if (conn != null) conn.close();
              conn = null;
            }
            catch (SQLException e)
            {
                System.out.println(e);
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
        return segmentVOs;
    }

    public long[] findAllSegmentVOsByProductStructure(long[] productStructurePKs, String[] nonActiveStatuses)
    {
        String productStructureFKCol = DBTABLE.getDBColumn("ProductStructureFK").getFullyQualifiedColumnName();
        String segmentStatusCTCol    = DBTABLE.getDBColumn("SegmentStatusCT").getFullyQualifiedColumnName();

        String sql = " SELECT * FROM " + TABLENAME +
                     " WHERE " + productStructureFKCol + " IN (";

        for (int i = 0; i < productStructurePKs.length; i++)
        {
            if (i < productStructurePKs.length - 1)
            {
                sql += productStructurePKs[i];

                sql += ", ";
            }
            else
            {
                sql += productStructurePKs[i];
            }
        }

        sql += ")" +

        " AND (NOT " + segmentStatusCTCol + "= '";

        for (int i = 0; i < nonActiveStatuses.length; i++)
        {
            if (i < nonActiveStatuses.length - 1)
            {
                sql += nonActiveStatuses[i];
                sql += "' AND NOT " + segmentStatusCTCol + " = '";
            }
            else
            {
                sql += nonActiveStatuses[i];
                sql += "')";
            }
        }

        SegmentVO[] segmentVO = (SegmentVO[]) executeQuery(SegmentVO.class,
                sql,
                POOLNAME,
                false,
                null);

        int count = segmentVO.length;

        long[] segmentKeys = new long[count];

        for (int i = 0; i < count; i++)
        {
            segmentKeys[i] = segmentVO[i].getSegmentPK();
        }

        return segmentKeys;
    }

    public SegmentVO[] findRidersBySegmentPK(long segmentPK)
    {
        String segmentFKCol = DBTABLE.getDBColumn("SegmentFK").getFullyQualifiedColumnName();

        String sql = " SELECT * FROM " + TABLENAME +
                     " WHERE " + segmentFKCol + " = " + segmentPK;

        return (SegmentVO[]) executeQuery(SegmentVO.class,
                sql,
                POOLNAME,
                false,
                null);
    }

    /**
     * Retrieves all segments whose SegmentStatusCT is in the array of statuses passed in params
     * @param segmentStatus
     * @return
     */
    public SegmentVO[] findBySegmentStatus(String[] segmentStatus)
    {
        String segmentStatusCTCol = DBTABLE.getDBColumn("SegmentStatusCT").getFullyQualifiedColumnName();

        String sqlIn = "";

        for (int i = 0; i < segmentStatus.length; i++)
        {
            if (i < segmentStatus.length - 1)
            {
                sqlIn += "'" + segmentStatus[i] + "', ";
            }
            else
            {
                sqlIn += "'" + segmentStatus[i] + "')";
            }
        }

        String sql = "SELECT * FROM " + TABLENAME +
                     " WHERE " + segmentStatusCTCol + " IN (" + sqlIn ;

        return (SegmentVO[]) executeQuery(SegmentVO.class,
                                          sql,
                                          POOLNAME,
                                          false,
                                          null);
    }

    public SegmentVO[] findByEDITTrxPK(long editTrxPK)
    {
        DBTable clientSetupDBTable   = DBTable.getDBTableForTable("ClientSetup");
        DBTable contractSetupDBTable = DBTable.getDBTableForTable("ContractSetup");
        DBTable editTrxDBTable       = DBTable.getDBTableForTable("EDITTrx");

        String clientSetupTable = clientSetupDBTable.getFullyQualifiedTableName();
        String contractSetupTable = contractSetupDBTable.getFullyQualifiedTableName();
        String editTrxTable = editTrxDBTable.getFullyQualifiedTableName();

        String segmentPKCol = DBTABLE.getDBColumn("SegmentPK").getFullyQualifiedColumnName();

        String segmentFKCol       = contractSetupDBTable.getDBColumn("SegmentFK").getFullyQualifiedColumnName();
        String contractSetupPKCol = contractSetupDBTable.getDBColumn("ContractSetupPK").getFullyQualifiedColumnName();

        String contractSetupFKCol = clientSetupDBTable.getDBColumn("ContractSetupFK").getFullyQualifiedColumnName();
        String clientSetupPKCol   = clientSetupDBTable.getDBColumn("ClientSetupPK").getFullyQualifiedColumnName();

        String clientSetupFKCol = editTrxDBTable.getDBColumn("ClientSetupFK").getFullyQualifiedColumnName();
        String editTrxPKCol     = editTrxDBTable.getDBColumn("EDITTrxPK").getFullyQualifiedColumnName();

        String sql = "SELECT " + TABLENAME + ".* FROM " + TABLENAME + ", " + contractSetupTable + ", " + clientSetupTable + ", " + editTrxTable +
                     " WHERE " +  segmentPKCol + " = " + segmentFKCol +
                     " AND " + contractSetupPKCol + " = " + contractSetupFKCol +
                     " AND " + clientSetupPKCol + " = " + clientSetupFKCol +
                     " AND " + editTrxPKCol + " = " + editTrxPK;

        return (SegmentVO[]) executeQuery(SegmentVO.class,
                                           sql,
                                            POOLNAME,
                                             false,
                                              null);
    }

    /**
     * Finder.
     * @param partialCorporateName
     * @return
     */
    public SegmentVO[] findSegmentsBy_PartialCorporateName(String partialCorporateName)
    {
        DBTable contractClientDBTable = DBTable.getDBTableForTable("ContractClient");
        DBTable clientRoleDBTable = DBTable.getDBTableForTable("ClientRole");
        DBTable clientDetailDBTable = DBTable.getDBTableForTable("ClientDetail");

        String contractClientTable = contractClientDBTable.getFullyQualifiedTableName();
        String clientRoleTable = clientRoleDBTable.getFullyQualifiedTableName();
        String clientDetailTable = clientDetailDBTable.getFullyQualifiedTableName();

        String segmentPKCol = DBTABLE.getDBColumn("SegmentPK").getFullyQualifiedColumnName();
        String contractClientSegmentFKCol = contractClientDBTable.getDBColumn("SegmentFK").getFullyQualifiedColumnName();
        String clientRoleFKCol = contractClientDBTable.getDBColumn("ClientRoleFK").getFullyQualifiedColumnName();
        String clientRolePKCol = clientRoleDBTable.getDBColumn("ClientRolePK").getFullyQualifiedColumnName();
        String clientDetailFKCol = clientRoleDBTable.getDBColumn("ClientDetailFK").getFullyQualifiedColumnName();
        String clientDetailPKCol = clientDetailDBTable.getDBColumn("ClientDetailPK").getFullyQualifiedColumnName();
        String corporateNameCol = clientDetailDBTable.getDBColumn("CorporateName").getFullyQualifiedColumnName();

        String sql;
        sql =   " SELECT DISTINCT * " +
                " FROM " + TABLENAME +
                " INNER JOIN " + contractClientTable +
                " ON " + segmentPKCol + " = " + contractClientSegmentFKCol +
                " INNER JOIN " + clientRoleTable +
                " ON " + clientRoleFKCol + " = " + clientRolePKCol +
                " INNER JOIN " + clientDetailTable +
                " ON " + clientDetailFKCol + " = " + clientDetailPKCol +
                " WHERE " + corporateNameCol + " LIKE '" + partialCorporateName + "%'";

        return (SegmentVO[]) executeQuery(SegmentVO.class,
                sql,
                POOLNAME,
                false,
                null);
    }

    /**
     * Finder.
     * @param reinsuranceHistoryPK
     * @return
     */
    public SegmentVO[] findBy_ReinsuranceHistoryPK(long reinsuranceHistoryPK)
    {
        String segmentPKCol = DBTABLE.getDBColumn("SegmentPK").getFullyQualifiedColumnName();

        DBTable contractTreatyDBTable = DBTable.getDBTableForTable("ContractTreaty");
        String contractTreatyTable = contractTreatyDBTable.getFullyQualifiedTableName();
        String contractTreatyPKCol = contractTreatyDBTable.getDBColumn("ContractTreatyPK").getFullyQualifiedColumnName();
        String segmentFKCol = contractTreatyDBTable.getDBColumn("SegmentFK").getFullyQualifiedColumnName();

        DBTable reinsuranceHistoryDBTable = DBTable.getDBTableForTable("ReinsuranceHistory");
        String reinsuranceHistoryTable = reinsuranceHistoryDBTable.getFullyQualifiedTableName();
        String contractTreatyFKCol = reinsuranceHistoryDBTable.getDBColumn("ContractTreatyFK").getFullyQualifiedColumnName();
        String reinsuranceHistoryPKCol = reinsuranceHistoryDBTable.getDBColumn("ReinsuranceHistoryPK").getFullyQualifiedColumnName();

        String sql;
        sql =   " SELECT " + TABLENAME + ".*" +
                " FROM " + TABLENAME +
                " INNER JOIN " + contractTreatyTable +
                " ON " + segmentPKCol + " = " + segmentFKCol +
                " INNER JOIN " + reinsuranceHistoryTable +
                " ON " + contractTreatyPKCol + " = " + contractTreatyFKCol +
                " WHERE " + reinsuranceHistoryPKCol + " = " + reinsuranceHistoryPK;

        return (SegmentVO[]) executeQuery(SegmentVO.class,
                sql,
                POOLNAME,
                false,
                null);
    }

    /**
     * Finders a life rider Segment, if any.
     * @param segmentPK
     * @return
     */
    public SegmentVO[] findLifeSegmentBy_SegmentPK(long segmentPK)
    {
        // Segment
        String segmentPKCol = DBTABLE.getDBColumn("SegmentPK").getFullyQualifiedColumnName();

        // Life
        DBTable lifeDBTable = DBTable.getDBTableForTable("Life");
        String lifeTable = lifeDBTable.getFullyQualifiedTableName();
        String lifeSegmentFKCol = lifeDBTable.getDBColumn("SegmentFK").getFullyQualifiedColumnName();

        String sql;
        sql =   " SELECT " + TABLENAME + ".*" +
                " FROM " + TABLENAME +
                " INNER JOIN " + lifeTable +
                " ON " + segmentPKCol + " = " + lifeSegmentFKCol +
                " WHERE " + lifeSegmentFKCol + " = " + segmentPK;

        return (SegmentVO[]) executeQuery(SegmentVO.class,
                sql,
                POOLNAME,
                false,
                null);
    }

    /**
     * Finder. Returns only Base Segments.
     * @param date
     * @param segmentStatusCT
     * @return
     */
    public SegmentVO[] findBy_CreationDateGTE_SegmentStatusCT_AND_SegmentFKISNULL(String date, String segmentStatusCT)
    {
        // Segment
        String creationDateCol = DBTABLE.getDBColumn("CreationDate").getFullyQualifiedColumnName();
        String segmentFKCol = DBTABLE.getDBColumn("SegmentFK").getFullyQualifiedColumnName();
        String segmentStatusCTCol = DBTABLE.getDBColumn("SegmentStatusCT").getFullyQualifiedColumnName();

        String sql;
        sql =   " SELECT *" +
                " FROM " + TABLENAME +
                " WHERE " + creationDateCol + " >= ?" +
                " AND " + segmentFKCol + " IS NULL" +
                " AND " + segmentStatusCTCol + " = ?";

        SegmentVO[] segmentVOs = null;
        Connection conn = null;

        PreparedStatement ps = null;

        try
        {
            conn = ConnectionFactory.getSingleton().getConnection(POOLNAME);

            ps = conn.prepareStatement(sql);

            ps.setDate(1, DBUtil.convertStringToDate(date));
            ps.setString(2, segmentStatusCT);

            segmentVOs = (SegmentVO[]) executeQuery(SegmentVO.class,
                                                      ps,
                                                      POOLNAME,
                                                      false,
                                                      null);
        }
        catch (Exception e)
        {
            System.out.println(e);
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        finally
        {
            try
            {
              if (ps != null) ps.close();
              ps = null;
              if (conn != null) conn.close();
              conn = null;
            }
            catch (SQLException e)
            {
                System.out.println(e);
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
        return segmentVOs;
    }

    /**
     * Finder. Returns only Base Segments >= than terminationDate.
     * @param date
     * @param segmentStatusCT
     * @return
     */
    public List<Long> findPKsBy_GTE_TerminationDate_SegmentNameCT_SegmentStatusCT_AND_SegmentFKISNULL
    (String segmentNameCT, String[] segmentStatus, EDITDate terminationDate)
    {
        // Segment
        String segmentNameCol = DBTABLE.getDBColumn("SegmentNameCT").getFullyQualifiedColumnName();
        String segmentFKCol = DBTABLE.getDBColumn("SegmentFK").getFullyQualifiedColumnName();
        String segmentStatusCTCol = DBTABLE.getDBColumn("SegmentStatusCT").getFullyQualifiedColumnName();
        String segmentPKCol = DBTABLE.getDBColumn("SegmentPK").getFullyQualifiedColumnName();
        //String terminationDateCol = DBTABLE.getDBColumn("TerminationDate").getFullyQualifiedColumnName();

        String sqlIn = "";

        for (int i = 0; i < segmentStatus.length; i++)
        {
            if (i < segmentStatus.length - 1)
            {
                sqlIn += "'" + segmentStatus[i] + "', ";
            }
            else
            {
                sqlIn += "'" + segmentStatus[i] + "')";
            }
        }
        
        String sql =   " SELECT DISTINCT " + segmentPKCol +
                " FROM " + TABLENAME +
                " WHERE " + segmentNameCol + " = ?" +
                " AND " + segmentFKCol + " IS NULL" +
                //" AND " + terminationDateCol + " >= ? " +
                " AND " + segmentStatusCTCol + " IN (" + sqlIn ;

        List<Long> segmentPKs = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try
        {
            conn = ConnectionFactory.getSingleton().getConnection(POOLNAME);

            ps = conn.prepareStatement(sql);

            ps.setString(1, segmentNameCT);
         //   ps.setDate(2, DBUtil.convertStringToDate(terminationDate.getFormattedDate()));
            
            rs = ps.executeQuery();
            
            while (rs.next())
            {
            	segmentPKs.add(new Long(rs.getLong("SegmentPK")));
            }
        }
        catch (Exception e)
        {
            System.out.println(e);
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        finally
        {
            try
            {
              if (ps != null) ps.close();
              ps = null;
              if (conn != null) conn.close();
              conn = null;
            }
            catch (SQLException e)
            {
                System.out.println(e);
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
        return segmentPKs;
    }

    
    /**
     * Finder. Returns only Base Segments.
     * @param date
     * @param segmentStatusCT
     * @return
     */
    public List<Long> findPKsBy_SegmentNameCT_SegmentStatusCT_AND_SegmentFKISNULL(String segmentNameCT, String[] segmentStatus)
    {
        // Segment
        String segmentNameCol = DBTABLE.getDBColumn("SegmentNameCT").getFullyQualifiedColumnName();
        String segmentFKCol = DBTABLE.getDBColumn("SegmentFK").getFullyQualifiedColumnName();
        String segmentStatusCTCol = DBTABLE.getDBColumn("SegmentStatusCT").getFullyQualifiedColumnName();
        String segmentPKCol = DBTABLE.getDBColumn("SegmentPK").getFullyQualifiedColumnName();

        String sqlIn = "";

        for (int i = 0; i < segmentStatus.length; i++)
        {
            if (i < segmentStatus.length - 1)
            {
                sqlIn += "'" + segmentStatus[i] + "', ";
            }
            else
            {
                sqlIn += "'" + segmentStatus[i] + "')";
            }
        }
        
        String sql =   " SELECT DISTINCT " + segmentPKCol +
                " FROM " + TABLENAME +
                " WHERE " + segmentNameCol + " = ?" +
                " AND " + segmentFKCol + " IS NULL" +
                " AND " + segmentStatusCTCol + " IN (" + sqlIn ;

        List<Long> segmentPKs = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try
        {
            conn = ConnectionFactory.getSingleton().getConnection(POOLNAME);

            ps = conn.prepareStatement(sql);

            ps.setString(1, segmentNameCT);
            
            rs = ps.executeQuery();
            
            while (rs.next())
            {
            	segmentPKs.add(new Long(rs.getLong("SegmentPK")));
            }
        }
        catch (Exception e)
        {
            System.out.println(e);
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        finally
        {
            try
            {
              if (ps != null) ps.close();
              ps = null;
              if (conn != null) conn.close();
              conn = null;
            }
            catch (SQLException e)
            {
                System.out.println(e);
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
        return segmentPKs;
    }

    /**
     * Finder.
     * @param placedAgentPK
     * @return
     */
    public SegmentVO[] findBy_PlacedAgentPK(long placedAgentPK)
    {
        // AgentSnapshot
        DBTable agentSnapshotDBTable = DBTable.getDBTableForTable("AgentSnapshot");
        String agentSnapshotTable = agentSnapshotDBTable.getFullyQualifiedTableName();
        String placedAgentFKCol = agentSnapshotDBTable.getDBColumn("PlacedAgentFK").getFullyQualifiedColumnName();
        String agentHierarchyFKCol = agentSnapshotDBTable.getDBColumn("AgentHierarchyFK").getFullyQualifiedColumnName();

        // AgentHierarchy
        DBTable agentHierarchyDBTable = DBTable.getDBTableForTable("AgentHierarchy");
        String agentHierachyTable = agentHierarchyDBTable.getFullyQualifiedTableName();
        String agentHierarchyPKCol = agentHierarchyDBTable.getDBColumn("AgentHierarchyPK").getFullyQualifiedColumnName();
        String segmentFKCol = agentHierarchyDBTable.getDBColumn("SegmentFK").getFullyQualifiedColumnName();

        // Segment
        String segmentPKCol = DBTABLE.getDBColumn("SegmentPK").getFullyQualifiedColumnName();

        String sql;
        sql =   " SELECT " + TABLENAME + ".*" +
                " FROM " + agentSnapshotTable +

                " INNER JOIN " + agentHierachyTable +
                " ON " + agentHierarchyFKCol + " = " + agentHierarchyPKCol +

                " INNER JOIN " + TABLENAME +
                " ON " + segmentFKCol + " = " + segmentPKCol +

                " WHERE " + placedAgentFKCol + " = " + placedAgentPK;

        return (SegmentVO[]) executeQuery(SegmentVO.class,
                sql,
                POOLNAME,
                false,
                null);
    }

   public SegmentVO[] findByClientDetailFK(long clientDetailFK, boolean includeChildVOs, List voExclusionList)
    {
        DBTable contractClientDBTable = DBTable.getDBTableForTable("ContractClient");
        String contractClientTable = contractClientDBTable.getFullyQualifiedTableName();

        DBTable clientRoleDBTable = DBTable.getDBTableForTable("ClientRole");
        String clientRoleTable =  clientRoleDBTable.getFullyQualifiedTableName();

        String segmentPKCol = DBTABLE.getDBColumn("SegmentPK").getFullyQualifiedColumnName();

        String clientRolePKCol = clientRoleDBTable.getDBColumn("ClientRolePK").getFullyQualifiedColumnName();
        String clientDetailFKCol = clientRoleDBTable.getDBColumn("ClientDetailFK").getFullyQualifiedColumnName();
        String roleTypeCTCol = clientRoleDBTable.getDBColumn("RoleTypeCT").getFullyQualifiedColumnName();
        String segmentFKCol    = contractClientDBTable.getDBColumn("SegmentFK").getFullyQualifiedColumnName();
        String clientRoleFKCol = contractClientDBTable.getDBColumn("ClientRoleFK").getFullyQualifiedColumnName();
        String terminationDateCol = contractClientDBTable.getDBColumn("TerminationDate").getFullyQualifiedColumnName();
        EDITDate currentDate = new EDITDate();

        String sql = " SELECT " + TABLENAME + ".*" + " FROM " + clientRoleTable +
                     " INNER JOIN " + contractClientTable + " ON " + clientRolePKCol + " = " +  clientRoleFKCol +
                     " INNER JOIN " + TABLENAME + " ON " +  segmentFKCol + " = " + segmentPKCol +
                     " WHERE " + clientDetailFKCol + " = ?" +
                     " AND " + roleTypeCTCol + " = 'OWN'" +
                     " AND " + terminationDateCol + " >= ?";

        SegmentVO[] segmentVOs = null;
        Connection conn = null;

        PreparedStatement ps = null;

        try
        {
            conn = ConnectionFactory.getSingleton().getConnection(POOLNAME);

            ps = conn.prepareStatement(sql);

            ps.setLong(1, clientDetailFK);
            ps.setDate(2, DBUtil.convertStringToDate(currentDate.getFormattedDate()));

            segmentVOs = (SegmentVO[]) executeQuery(SegmentVO.class,
                                                      ps,
                                                      POOLNAME,
                                                      includeChildVOs,
                                                      voExclusionList);
        }
        catch (Exception e)
        {
            System.out.println(e);
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        finally
        {
            try
            {
              if (ps != null) ps.close();
              ps = null;
              if (conn != null) conn.close();
              conn = null;
            }
            catch (SQLException e)
            {
                System.out.println(e);
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
        return segmentVOs;
    }

    public SegmentVO[] findBySuppOriginalContractNumber(String contractNumber, boolean includeChildren, List voExclusionList)
    {
        String suppOriginalContractNumberCol = DBTABLE.getDBColumn("SuppOriginalContractNumber").getFullyQualifiedColumnName();
        String segmentFKCol      = DBTABLE.getDBColumn("SegmentFK").getFullyQualifiedColumnName();

        String sql = " SELECT * FROM " + TABLENAME +
                     " WHERE UPPER(" + suppOriginalContractNumberCol + ") = UPPER('" + contractNumber + "')" +
                     " AND " + segmentFKCol + " IS NULL";

        return (SegmentVO[]) executeQuery(SegmentVO.class,
                sql,
                POOLNAME,
                includeChildren,
                voExclusionList);
    }

    /**
     * Finds all distinct SegmentVOs filtered by the given criteria.
     * @param productStructurePKs
     * @param lapsePendingDate
     * @return
     */
    public SegmentVO[] findBy_ProductStructurePKs_LapsePendingDate(long[] productStructurePKs, EDITDate lapsePendingDate)
    {
        SegmentVO[] segmentVOs = null;

        // Establish the IN part of the clause since it is used twice in the query below.
        String sqlIn = this.createProductStructurePKList(productStructurePKs);

        DBTable lifeDBTable  = DBTable.getDBTableForTable("Life");

        String lifeTable = lifeDBTable.getFullyQualifiedTableName();

        String segmentPKCol          = DBTABLE.getDBColumn("SegmentPK").getFullyQualifiedColumnName();
        String productStructureFKCol = DBTABLE.getDBColumn("ProductStructureFK").getFullyQualifiedColumnName();
        String segmentNameCol        = DBTABLE.getDBColumn("SegmentNameCT").getFullyQualifiedColumnName();

        String lapsePendingDateCol = lifeDBTable.getDBColumn("LapsePendingDate").getFullyQualifiedColumnName();
        String paidToDateCol = lifeDBTable.getDBColumn("PaidToDate").getFullyQualifiedColumnName();
        String lifeSegmentFKCol    = lifeDBTable.getDBColumn("SegmentFK").getFullyQualifiedColumnName();


        String sql = "SELECT DISTINCT " + TABLENAME + ".* FROM " + TABLENAME + ", " + lifeTable +
                     " WHERE " + segmentPKCol + " = " + lifeSegmentFKCol +
                     " AND " + lapsePendingDateCol + " <= ?" +
                     " AND (" + lapsePendingDateCol + " >= " + paidToDateCol +
                     	" OR " + segmentNameCol + " = 'UL')" +
                     " AND " + productStructureFKCol + " IN (" + sqlIn + ")";

        Connection conn = null;

        PreparedStatement ps = null;

        try
        {
            conn = ConnectionFactory.getSingleton().getConnection(POOLNAME);

            ps = conn.prepareStatement(sql);

            ps.setDate(1, DBUtil.convertStringToDate(lapsePendingDate.getFormattedDate()));

            segmentVOs = (SegmentVO[]) executeQuery(SegmentVO.class, ps, POOLNAME, false, null);
        }
        catch (Exception e)
        {
            System.out.println(e);

            e.printStackTrace(); //To change body of catch statement use File | Settings | File Templates.

            throw new RuntimeException(e);
        }
        finally
        {
            try
            {
                if (ps != null)
                {
                    ps.close();
                    
                    ps = null;
                }
            
                if (conn != null)
                {
                    conn.close();
                    
                    conn = null;
                }
            }
            catch (SQLException e)
            {
                System.out.println(e);

                e.printStackTrace(); //To change body of catch statement use File | Settings | File Templates.

                throw new RuntimeException(e);
            }
        }

        return segmentVOs;
    }

    /**
     * Finds all distinct SegmentVOs filtered by the given criteria.
     * @param productStructurePKs
     * @param lapseDate
     * @return
     */
    public SegmentVO[] findBy_ProductStructurePKs_LapseDate(long[] productStructurePKs, EDITDate lapseDate)
    {
        SegmentVO[] segmentVOs = null;

        // Establish the IN part of the clause since it is used twice in the query below.
        String sqlIn = this.createProductStructurePKList(productStructurePKs);

        DBTable lifeDBTable = DBTable.getDBTableForTable("Life");

        String lifeTable    = lifeDBTable.getFullyQualifiedTableName();

        String segmentPKCol          = DBTABLE.getDBColumn("SegmentPK").getFullyQualifiedColumnName();
        String productStructureFKCol = DBTABLE.getDBColumn("ProductStructureFK").getFullyQualifiedColumnName();

        String lapseDateCol        = lifeDBTable.getDBColumn("LapseDate").getFullyQualifiedColumnName();
        String lifeSegmentFKCol    = lifeDBTable.getDBColumn("SegmentFK").getFullyQualifiedColumnName();


        String sql = "SELECT DISTINCT " + TABLENAME + ".* FROM " + TABLENAME + ", " + lifeTable +
                     " WHERE " + segmentPKCol + " = " + lifeSegmentFKCol +
                     " AND " + lapseDateCol + " <= ?" +
                     " AND " + productStructureFKCol + " IN (" + sqlIn + ")";

        Connection conn = null;

        PreparedStatement ps = null;

        try
        {
            conn = ConnectionFactory.getSingleton().getConnection(POOLNAME);

            ps = conn.prepareStatement(sql);

            ps.setDate(1, DBUtil.convertStringToDate(lapseDate.getFormattedDate()));

            segmentVOs = (SegmentVO[]) executeQuery(SegmentVO.class, ps, POOLNAME, false, null);
        }
        catch (Exception e)
        {
            System.out.println(e);

            e.printStackTrace(); //To change body of catch statement use File | Settings | File Templates.

            throw new RuntimeException(e);
        }
        finally
        {
            try
            {
                if (ps != null)
                {
                    ps.close();
                    
                    ps = null;
                }            
                
                if (conn != null)
                {
                    conn.close();
                    
                     conn = null;
                }
            }
            catch (SQLException e)
            {
                System.out.println(e);

                e.printStackTrace(); //To change body of catch statement use File | Settings | File Templates.

                throw new RuntimeException(e);
            }
        }

        return segmentVOs;
    }

    private String createProductStructurePKList(long[] productStructurePKs)
    {
        String sqlIn = new String();

        int size = productStructurePKs.length;

        for (int i = 0; i < size; i++)
        {
            sqlIn = sqlIn + productStructurePKs[i];

            if (i < (size - 1))
            {
                sqlIn = sqlIn + ", ";
            }
        }

        return sqlIn;
    }

}
